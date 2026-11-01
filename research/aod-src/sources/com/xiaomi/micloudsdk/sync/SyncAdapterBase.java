package com.xiaomi.micloudsdk.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.miui.maml.folme.AnimatedPropertyType;
import com.xiaomi.micloudsdk.data.ExtendedAuthToken;
import com.xiaomi.micloudsdk.exception.CloudServerException;
import com.xiaomi.micloudsdk.exception.SyncLocalException;
import com.xiaomi.micloudsdk.request.utils.SyncRequestParam;
import com.xiaomi.micloudsdk.stat.MiCloudErrorStatReason;
import com.xiaomi.micloudsdk.sync.utils.LegacySyncRecordUtils;
import com.xiaomi.micloudsdk.sync.utils.SyncExceptionUtils;
import com.xiaomi.micloudsdk.sync.utils.SyncTimeUtils;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import com.xiaomi.micloudsdk.utils.ReflectUtils;
import java.io.IOException;
import java.lang.reflect.Field;
import micloud.compat.v18.sync.SyncAdapterBaseInjectorCompat;
import miui.cloud.log.PrivacyFilter;
import miui.cloud.util.SyncAutoSettingUtil;

/* JADX INFO: loaded from: classes2.dex */
public abstract class SyncAdapterBase extends AbstractThreadedSyncAdapter {
    public boolean isForceSync;
    public boolean isIgnoreBackoff;
    public boolean isIgnoreBatteryLow;
    public boolean isIgnoreSettings;
    public boolean isIgnoreTemperature;
    public boolean isIgnoreWifiSettings;
    public boolean isManualSync;
    public Account mAccount;
    public final String mAuthType;
    public String mAuthority;
    public Context mContext;
    public ExtendedAuthToken mExtToken;
    public String mExtTokenStr;
    public SyncLogSender mSyncLogSender;
    public SyncResult mSyncResult;

    public class ExtTokenResult {
        public final Throwable error;
        public final String extTokenStr;

        public ExtTokenResult(String str, Throwable th) {
            this.extTokenStr = str;
            this.error = th;
        }
    }

    public static class ResultMessage {
        public final Object miSyncResult;
        public final Field resultMessageField;

        public ResultMessage(Object obj, Field field) {
            this.miSyncResult = obj;
            this.resultMessageField = field;
        }

        public String get() {
            try {
                return (String) this.resultMessageField.get(this.miSyncResult);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("get resultMessageField: Please file a bug to CloudService!!", e);
            }
        }

        public void set(String str) {
            try {
                this.resultMessageField.set(this.miSyncResult, str);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("set miSyncResult: Please file a bug to CloudService!!", e);
            }
        }
    }

    public static String getExtraErrorMsgForResultOrNullIfFieldNotExists(SyncResult syncResult) {
        ResultMessage resultMessageOrNullIfFieldNotExists = getResultMessageOrNullIfFieldNotExists(syncResult);
        if (resultMessageOrNullIfFieldNotExists == null) {
            return null;
        }
        return resultMessageOrNullIfFieldNotExists.get();
    }

    public static ResultMessage getResultMessageOrNullIfFieldNotExists(SyncResult syncResult) {
        Field field = ReflectUtils.getField(syncResult.getClass(), "miSyncResult");
        if (field == null) {
            return null;
        }
        try {
            Object obj = field.get(syncResult);
            Field field2 = ReflectUtils.getField(obj.getClass(), "resultMessage");
            if (field2 != null) {
                return new ResultMessage(obj, field2);
            }
            throw new RuntimeException("resultMessageField is null: Please file a bug to CloudService!!");
        } catch (IllegalAccessException unused) {
            throw new RuntimeException("get miSyncResultField: Please file a bug to CloudService!!");
        }
    }

    public static void internalSetAuthError(SyncResult syncResult) {
        syncResult.stats.numAuthExceptions++;
    }

    public static void internalSetCTAError(SyncResult syncResult) {
        syncResult.stats.numConflictDetectedExceptions++;
        setExtraErrorMsgForResultIfFieldExists(syncResult, "permission_error");
    }

    public static void internalSetCloudStorageFullError(SyncResult syncResult) {
        syncResult.stats.numConflictDetectedExceptions++;
        setExtraErrorMsgForResultIfFieldExists(syncResult, "cloud_storage_full");
    }

    public static void internalSetGDPRError(SyncResult syncResult) {
        syncResult.stats.numConflictDetectedExceptions++;
        setExtraErrorMsgForResultIfFieldExists(syncResult, "gdpr_error");
    }

    public static void internalSetPauseSyncError(SyncResult syncResult) {
        syncResult.stats.numConflictDetectedExceptions++;
        setExtraErrorMsgForResultIfFieldExists(syncResult, "pause_limit");
    }

    public static void internalSetPermissionLimit(SyncResult syncResult) {
        syncResult.stats.numConflictDetectedExceptions++;
        setExtraErrorMsgForResultIfFieldExists(syncResult, "permission_limit");
    }

    public static void internalSetRequestError(SyncResult syncResult) {
        syncResult.stats.numIoExceptions++;
        setExtraErrorMsgForResultIfFieldExists(syncResult, "request_error");
    }

    public static void internalSetSecureSpaceLimitError(SyncResult syncResult) {
        syncResult.stats.numConflictDetectedExceptions++;
        setExtraErrorMsgForResultIfFieldExists(syncResult, "secure_space_limit");
    }

    public static void internalSetSimActivatedError(SyncResult syncResult) {
        syncResult.stats.numConflictDetectedExceptions++;
        setExtraErrorMsgForResultIfFieldExists(syncResult, "sim_activated_error");
    }

    public static void internalSetSyncSwitchOffError(SyncResult syncResult) {
        syncResult.stats.numConflictDetectedExceptions++;
        setExtraErrorMsgForResultIfFieldExists(syncResult, "switch_off");
    }

    public static void internalSetUnknownError(SyncResult syncResult) {
        syncResult.stats.numConflictDetectedExceptions++;
    }

    public static void internalSetWlanOnlyError(SyncResult syncResult) {
        syncResult.stats.numConflictDetectedExceptions++;
        setExtraErrorMsgForResultIfFieldExists(syncResult, "wlan_only");
    }

    public static void invalidTokenAndReSyncNowIfFirstTimeFailed(Context context, Account account, String str, String str2, Bundle bundle) {
        Bundle bundle2 = new Bundle(bundle);
        if (bundle2.getBoolean("token_invalid_retry", false)) {
            Log.e("MiCloudSyncAdapterBase", "Already retry with invalid token but still failed");
            return;
        }
        bundle2.putBoolean("token_invalid_retry", true);
        Log.w("MiCloudSyncAdapterBase", "AuthToken expired. Invalid and retry now");
        AccountManager.get(context).invalidateAuthToken(account.type, str2);
        ContentResolver.requestSync(account, str, bundle2);
    }

    public static void setExtraErrorMsgForResultIfFieldExists(SyncResult syncResult, String str) {
        ResultMessage resultMessageOrNullIfFieldNotExists = getResultMessageOrNullIfFieldNotExists(syncResult);
        if (resultMessageOrNullIfFieldNotExists == null) {
            return;
        }
        resultMessageOrNullIfFieldNotExists.set(str);
    }

    public static void setSyncResultByLocalException(SyncLocalException syncLocalException, SyncResult syncResult) {
        setSyncResultByLocalExceptionCode(syncLocalException.getErrorCode(), syncResult);
    }

    public static void setSyncResultByLocalExceptionCode(int i, SyncResult syncResult) {
        switch (i) {
            case 1000:
                internalSetCTAError(syncResult);
                break;
            case 1001:
                internalSetSimActivatedError(syncResult);
                break;
            case AnimatedPropertyType.TEXT_SIZE /* 1002 */:
                internalSetPermissionLimit(syncResult);
                break;
            case AnimatedPropertyType.TEXT_COLOR /* 1003 */:
                internalSetSecureSpaceLimitError(syncResult);
                break;
            default:
                switch (i) {
                    case 2000:
                        internalSetGDPRError(syncResult);
                        break;
                    case 2001:
                        internalSetCloudStorageFullError(syncResult);
                        break;
                    case 2002:
                        internalSetWlanOnlyError(syncResult);
                        break;
                    default:
                        internalSetUnknownError(syncResult);
                        break;
                }
                break;
        }
    }

    public static void setSyncResultByServerException(CloudServerException cloudServerException, SyncResult syncResult) {
        int i = cloudServerException.statusCode;
        if (i == -10001) {
            setSyncResultByLocalExceptionCode(cloudServerException.code, syncResult);
            return;
        }
        if (i == 403 || i == 406 || i == 400) {
            internalSetRequestError(syncResult);
            return;
        }
        if (i == 401) {
            internalSetAuthError(syncResult);
            return;
        }
        if (cloudServerException.code == 52003) {
            internalSetGDPRError(syncResult);
        } else if (cloudServerException.is5xxServerException()) {
            internalSetRequestError(syncResult);
        } else {
            internalSetUnknownError(syncResult);
        }
    }

    public final void acquireLogger(String str) {
        SyncLogSender syncLogSenderNewSyncLogSender = SyncLogSenderFactory.newSyncLogSender(this.mContext, str);
        this.mSyncLogSender = syncLogSenderNewSyncLogSender;
        syncLogSenderNewSyncLogSender.openSyncLog();
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0045 A[Catch: InterruptedException -> 0x0011, IOException -> 0x004c, AuthenticatorException -> 0x004f, OperationCanceledException -> 0x0052, TryCatch #4 {InterruptedException -> 0x0011, blocks: (B:8:0x000d, B:18:0x0021, B:20:0x003f, B:22:0x0045, B:29:0x0055, B:31:0x005d, B:32:0x0063, B:34:0x006f, B:35:0x0075), top: B:52:0x000d }] */
    /* JADX WARN: Code duplicated, block: B:29:0x0055 A[Catch: InterruptedException -> 0x0011, IOException -> 0x004c, AuthenticatorException -> 0x004f, OperationCanceledException -> 0x0052, TryCatch #4 {InterruptedException -> 0x0011, blocks: (B:8:0x000d, B:18:0x0021, B:20:0x003f, B:22:0x0045, B:29:0x0055, B:31:0x005d, B:32:0x0063, B:34:0x006f, B:35:0x0075), top: B:52:0x000d }] */
    /* JADX WARN: Code duplicated, block: B:31:0x005d A[Catch: InterruptedException -> 0x0011, IOException -> 0x004c, AuthenticatorException -> 0x004f, OperationCanceledException -> 0x0052, TryCatch #4 {InterruptedException -> 0x0011, blocks: (B:8:0x000d, B:18:0x0021, B:20:0x003f, B:22:0x0045, B:29:0x0055, B:31:0x005d, B:32:0x0063, B:34:0x006f, B:35:0x0075), top: B:52:0x000d }] */
    /* JADX WARN: Code duplicated, block: B:32:0x0063 A[Catch: InterruptedException -> 0x0011, IOException -> 0x004c, AuthenticatorException -> 0x004f, OperationCanceledException -> 0x0052, TryCatch #4 {InterruptedException -> 0x0011, blocks: (B:8:0x000d, B:18:0x0021, B:20:0x003f, B:22:0x0045, B:29:0x0055, B:31:0x005d, B:32:0x0063, B:34:0x006f, B:35:0x0075), top: B:52:0x000d }] */
    /* JADX WARN: Code duplicated, block: B:34:0x006f A[Catch: InterruptedException -> 0x0011, IOException -> 0x004c, AuthenticatorException -> 0x004f, OperationCanceledException -> 0x0052, TryCatch #4 {InterruptedException -> 0x0011, blocks: (B:8:0x000d, B:18:0x0021, B:20:0x003f, B:22:0x0045, B:29:0x0055, B:31:0x005d, B:32:0x0063, B:34:0x006f, B:35:0x0075), top: B:52:0x000d }] */
    /* JADX WARN: Code duplicated, block: B:35:0x0075 A[Catch: InterruptedException -> 0x0011, IOException -> 0x004c, AuthenticatorException -> 0x004f, OperationCanceledException -> 0x0052, TRY_LEAVE, TryCatch #4 {InterruptedException -> 0x0011, blocks: (B:8:0x000d, B:18:0x0021, B:20:0x003f, B:22:0x0045, B:29:0x0055, B:31:0x005d, B:32:0x0063, B:34:0x006f, B:35:0x0075), top: B:52:0x000d }] */
    public final ExtTokenResult getExtTokenResult(Context context, Account account, String str) {
        Account account2;
        AccountManagerFuture<Bundle> authToken;
        Bundle result;
        String string;
        int i = 0;
        Throwable e = null;
        while (i < 3) {
            if (i != 0) {
                try {
                    try {
                        Thread.sleep(1000L);
                        try {
                            Log.v("MiCloudSyncAdapterBase", "onPerformSync: getting auth token. authority: " + str);
                            account2 = account;
                            try {
                                authToken = AccountManager.get(context).getAuthToken(account2, this.mAuthType, true, null, null);
                                if (authToken == null) {
                                    Log.e("MiCloudSyncAdapterBase", "onPerformSync: getExtTokenStr null future.");
                                } else {
                                    result = authToken.getResult();
                                    if (result == null) {
                                        Log.e("MiCloudSyncAdapterBase", "onPerformSync: getExtTokenStr null future result.");
                                    } else {
                                        string = result.getString("authtoken");
                                        if (TextUtils.isEmpty(string)) {
                                            return new ExtTokenResult(string, null);
                                        }
                                        Log.e("MiCloudSyncAdapterBase", "onPerformSync: getExtTokenStr future result textEmpty.");
                                    }
                                }
                            } catch (AuthenticatorException e2) {
                                e = e2;
                                Log.e("MiCloudSyncAdapterBase", "onPerformSync getExtTokenStr AuthenticatorException: ", e);
                            } catch (OperationCanceledException e3) {
                                e = e3;
                                Log.e("MiCloudSyncAdapterBase", "onPerformSync getExtTokenStr OperationCanceledException: ", e);
                            } catch (IOException e4) {
                                e = e4;
                                Log.e("MiCloudSyncAdapterBase", "onPerformSync getExtTokenStr IOException: " + PrivacyFilter.filterPrivacyLog(Log.getStackTraceString(e)));
                            }
                        } catch (AuthenticatorException e5) {
                            e = e5;
                            account2 = account;
                        } catch (OperationCanceledException e6) {
                            e = e6;
                            account2 = account;
                        } catch (IOException e7) {
                            e = e7;
                            account2 = account;
                        }
                    } catch (InterruptedException e8) {
                        e = e8;
                        Log.e("MiCloudSyncAdapterBase", "onPerformSync getExtTokenStr InterruptedException: ", e);
                        Thread.currentThread().interrupt();
                    }
                } catch (AuthenticatorException e9) {
                    e = e9;
                    account2 = account;
                    Log.e("MiCloudSyncAdapterBase", "onPerformSync getExtTokenStr AuthenticatorException: ", e);
                    i++;
                    account = account2;
                } catch (OperationCanceledException e10) {
                    e = e10;
                    account2 = account;
                    Log.e("MiCloudSyncAdapterBase", "onPerformSync getExtTokenStr OperationCanceledException: ", e);
                    i++;
                    account = account2;
                } catch (IOException e11) {
                    e = e11;
                    account2 = account;
                    Log.e("MiCloudSyncAdapterBase", "onPerformSync getExtTokenStr IOException: " + PrivacyFilter.filterPrivacyLog(Log.getStackTraceString(e)));
                    i++;
                    account = account2;
                }
            } else {
                Log.v("MiCloudSyncAdapterBase", "onPerformSync: getting auth token. authority: " + str);
                account2 = account;
                authToken = AccountManager.get(context).getAuthToken(account2, this.mAuthType, true, null, null);
                if (authToken == null) {
                    Log.e("MiCloudSyncAdapterBase", "onPerformSync: getExtTokenStr null future.");
                } else {
                    result = authToken.getResult();
                    if (result == null) {
                        Log.e("MiCloudSyncAdapterBase", "onPerformSync: getExtTokenStr null future result.");
                    } else {
                        string = result.getString("authtoken");
                        if (TextUtils.isEmpty(string)) {
                            return new ExtTokenResult(string, null);
                        }
                        Log.e("MiCloudSyncAdapterBase", "onPerformSync: getExtTokenStr future result textEmpty.");
                    }
                }
            }
            i++;
            account = account2;
        }
        if (e == null) {
            e = new SyncLocalException(100);
        }
        return new ExtTokenResult(null, e);
    }

    public final void handleExceptionIfKeyBagRelated(SyncLocalException syncLocalException) {
        if (syncLocalException.getErrorCode() == 3000) {
            this.mContext.sendBroadcast(new Intent().setPackage("com.miui.cloudservice").setAction("com.miui.cloudservice.keybag.UPDATE"));
        } else if (syncLocalException.getErrorCode() == 3001) {
            this.mContext.sendBroadcast(new Intent().setPackage("com.miui.cloudservice").setAction("com.miui.cloudservice.keybag.DOWNLOAD"));
        }
    }

    public abstract void onPerformMiCloudSync(Bundle bundle);

    public void onPerformMiCloudSyncComplete(MiCloudSyncResult miCloudSyncResult) {
    }

    @Override // android.content.AbstractThreadedSyncAdapter
    public void onPerformSync(Account account, Bundle bundle, String str, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        long jMin;
        Log.d("MiCloudSyncAdapterBase", "check_sync_error_refactor");
        this.mAccount = account;
        this.mAuthority = str;
        this.mSyncResult = syncResult;
        long jCurrentTimeMillis = System.currentTimeMillis();
        String str2 = "SyncLog[" + str + "]";
        Log.d("MiCloudSyncAdapterBase", "onPerformSync: ---sync start---");
        Log.d("MiCloudSyncAdapterBase", "authority: " + str + ", extras: " + bundle.toString());
        this.isForceSync = bundle.getBoolean(MiCloudConstants.SYNC.SYNC_EXTRAS_FORCE, false);
        this.isIgnoreTemperature = bundle.getBoolean("micloud_ignore_temperature", false);
        this.isIgnoreWifiSettings = bundle.getBoolean("micloud_ignore_wifi_settings", false);
        this.isIgnoreBatteryLow = bundle.getBoolean("micloud_ignore_battery_low", false);
        this.isManualSync = bundle.getBoolean("force", false);
        this.isIgnoreBackoff = bundle.getBoolean("ignore_backoff", false);
        boolean z = bundle.getBoolean("ignore_settings", false);
        this.isIgnoreSettings = z;
        boolean z2 = this.isForceSync || this.isManualSync || z;
        SyncStatUtil.statOnSyncStart(this.mContext, z2);
        acquireLogger(str);
        syncLog(str2, new SyncLogInfo.SyncStartLogInfo(str, jCurrentTimeMillis, bundle).generateLogInfoString());
        SyncDataInfo syncDataInfoGenerateCurrentSyncDataInfo = SyncDataInfo.generateCurrentSyncDataInfo(this.mContext, str);
        syncLog(str2, "Before Sync: " + syncDataInfoGenerateCurrentSyncDataInfo.toString());
        MiCloudSyncResult miCloudSyncResultCreateSuccessResult = MiCloudSyncResult.createSuccessResult();
        if (!SyncAdapterBaseInjectorCompat.isGdprPermissionGranted(this.mContext, syncResult)) {
            Log.i("MiCloudSyncAdapterBase", "Gdpr Permission deny: " + str);
            internalSetGDPRError(syncResult);
            miCloudSyncResultCreateSuccessResult = MiCloudSyncResult.createFailResult(new SyncLocalException(2000), 2000, "gdpr_deny");
        } else if (!SyncAutoSettingUtil.getXiaomiGlobalSyncAutomatically() || !ContentResolver.getSyncAutomatically(account, str)) {
            Log.e("MiCloudSyncAdapterBase", "onPerformSync: sync switch off. do not sync!!");
            internalSetSyncSwitchOffError(syncResult);
            miCloudSyncResultCreateSuccessResult = MiCloudSyncResult.createFailResult(new SyncLocalException(2003), LegacySyncRecordUtils.getSyncSwitchOffErrorLegacyCode(), "switch_off");
        } else if (MiCloudResolver.isSyncPausing(this.mContext, this.mAccount, this.mAuthority)) {
            Log.e("MiCloudSyncAdapterBase", "onPerformSync: sync is set to pause. do not sync!!");
            internalSetPauseSyncError(syncResult);
            miCloudSyncResultCreateSuccessResult = MiCloudSyncResult.createFailResult(new SyncLocalException(2004), LegacySyncRecordUtils.getPauseLimitErrorLegacyCode(), "pause_limit");
        } else {
            ExtTokenResult extTokenResult = getExtTokenResult(this.mContext, this.mAccount, this.mAuthority);
            this.mExtTokenStr = extTokenResult.extTokenStr;
            if (extTokenResult.error != null) {
                Log.e("MiCloudSyncAdapterBase", "onPerformSync: Tried 3 times, but get null token.");
                internalSetAuthError(syncResult);
                miCloudSyncResultCreateSuccessResult = MiCloudSyncResult.createFailResult(extTokenResult.error, LegacySyncRecordUtils.getAuthTokenErrorLegacyCode(), "auth_token_error");
            } else {
                ExtendedAuthToken extendedAuthToken = ExtendedAuthToken.parse(this.mExtTokenStr);
                this.mExtToken = extendedAuthToken;
                onTransformExtAuthToken(extendedAuthToken);
                if (this.mExtToken == null) {
                    Log.e("MiCloudSyncAdapterBase", "onPerformSync: Cannot parse ext token");
                    internalSetAuthError(syncResult);
                    invalidTokenAndReSyncNowIfFirstTimeFailed(this.mContext, account, str, this.mExtTokenStr, bundle);
                    miCloudSyncResultCreateSuccessResult = MiCloudSyncResult.createFailResult(new SyncLocalException(100), LegacySyncRecordUtils.getAuthTokenErrorLegacyCode(), "auth_token_error");
                } else {
                    boolean z3 = this.isForceSync || this.isManualSync || this.isIgnoreSettings;
                    if (z3) {
                        SyncRequestParam.incrementManualSyncCountAndRecordCurMillis();
                    }
                    try {
                        try {
                            try {
                                try {
                                    onPerformMiCloudSync(bundle);
                                    if (z3) {
                                        SyncRequestParam.decrementManualSyncCount();
                                    }
                                    if (syncResult.hasError()) {
                                        Log.w("MiCloudSyncAdapterBase", "onPerformSync: Error without throw CloudException: " + SyncExceptionUtils.getErrorStringForLog(syncResult));
                                        miCloudSyncResultCreateSuccessResult = MiCloudSyncResult.createFailResult(new SyncLocalException(syncResult.hasHardError() ? 2 : 1), LegacySyncRecordUtils.getErrorCodeBySyncResult(syncResult), MiCloudErrorStatReason.getSyncResultErrorReasonForStats(syncResult));
                                    } else {
                                        Log.d("MiCloudSyncAdapterBase", "onPerformSync: No error found. ---Success---");
                                    }
                                } catch (CloudServerException e) {
                                    setSyncResultByServerException(e, syncResult);
                                    if (e.statusCode == 401) {
                                        invalidTokenAndReSyncNowIfFirstTimeFailed(this.mContext, account, str, this.mExtTokenStr, bundle);
                                    }
                                    jMin = e.is5xxServerException() ? Math.min(e.get5xxServerExceptionRetryTime() / 1000, 1800L) : 0L;
                                    String serverExceptionReasonForStats = MiCloudErrorStatReason.getServerExceptionReasonForStats(e);
                                    Log.e("MiCloudSyncAdapterBase", "onPerformSync server exception, reason: " + serverExceptionReasonForStats);
                                    miCloudSyncResultCreateSuccessResult = MiCloudSyncResult.createFailResult(e, LegacySyncRecordUtils.getErrorCodeByServerException(e), serverExceptionReasonForStats);
                                    if (z3) {
                                        SyncRequestParam.decrementManualSyncCount();
                                    }
                                }
                            } catch (SyncLocalException e2) {
                                setSyncResultByLocalException(e2, syncResult);
                                String localExceptionReasonForStats = MiCloudErrorStatReason.getLocalExceptionReasonForStats(e2);
                                Log.e("MiCloudSyncAdapterBase", "onPerformSync local exception, reason: " + localExceptionReasonForStats);
                                handleExceptionIfKeyBagRelated(e2);
                                miCloudSyncResultCreateSuccessResult = MiCloudSyncResult.createFailResult(e2, LegacySyncRecordUtils.getErrorCodeByLocalException(e2), localExceptionReasonForStats);
                                if (z3) {
                                    SyncRequestParam.decrementManualSyncCount();
                                }
                            }
                        } catch (SecurityException e3) {
                            e3.printStackTrace();
                            syncLog(str2, "SecurityException: " + e3.getMessage());
                            throw e3;
                        }
                    } catch (Throwable th) {
                        if (z3) {
                            SyncRequestParam.decrementManualSyncCount();
                        }
                        throw th;
                    }
                }
            }
        }
        jMin = 0;
        SyncDataInfo syncDataInfoGenerateCurrentSyncDataInfo2 = SyncDataInfo.generateCurrentSyncDataInfo(this.mContext, str);
        syncLog(str2, "After Sync: " + syncDataInfoGenerateCurrentSyncDataInfo2.toString());
        long jCurrentTimeMillis2 = System.currentTimeMillis();
        long j = jMin;
        syncLog(str2, new SyncLogInfo.SyncEndLogInfo(str, miCloudSyncResultCreateSuccessResult.isSuccess, jCurrentTimeMillis, jCurrentTimeMillis2, getExtraErrorMsgForResultOrNullIfFieldNotExists(syncResult)).generateLogInfoString());
        SyncStatUtil.statOnSyncEnd(this.mContext, z2, miCloudSyncResultCreateSuccessResult.isSuccess, miCloudSyncResultCreateSuccessResult.errorReasonForStats, str, syncDataInfoGenerateCurrentSyncDataInfo.getUnSyncedDataCount(), syncDataInfoGenerateCurrentSyncDataInfo2.getUnSyncedDataCount(), jCurrentTimeMillis, jCurrentTimeMillis2);
        if (miCloudSyncResultCreateSuccessResult.isSuccess) {
            LegacySyncRecordUtils.recordSyncResultSuccess(this.mContext, str);
        } else {
            LegacySyncRecordUtils.recordSyncResult(this.mContext, str, miCloudSyncResultCreateSuccessResult.errorCodeLegacy);
        }
        long jMax = Math.max(syncResult.delayUntil, 0L);
        syncResult.delayUntil = jMax;
        long jMax2 = Math.max(jMax, SyncTimeUtils.getGuardDelayUntilInSec(this.mContext, this.mAuthority));
        syncResult.delayUntil = jMax2;
        if (!miCloudSyncResultCreateSuccessResult.isSuccess) {
            syncResult.delayUntil = Math.max(jMax2, j);
        }
        onPerformMiCloudSyncComplete(miCloudSyncResultCreateSuccessResult);
        releaseLogger();
    }

    public void onTransformExtAuthToken(ExtendedAuthToken extendedAuthToken) {
    }

    public final void releaseLogger() {
        this.mSyncLogSender.release();
        this.mSyncLogSender = null;
    }

    public void syncLog(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            throw new IllegalArgumentException("tag or msg is null!");
        }
        this.mSyncLogSender.sendLog(str, str2);
    }
}
