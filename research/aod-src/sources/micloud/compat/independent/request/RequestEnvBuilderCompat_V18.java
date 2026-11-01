package micloud.compat.independent.request;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import com.xiaomi.micloudsdk.utils.IXiaomiAccountServiceProxy;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import miui.accounts.ExtraAccountManager;
import miui.cloud.os.SystemProperties;

/* JADX INFO: loaded from: classes2.dex */
class RequestEnvBuilderCompat_V18 extends RequestEnvBuilderCompat_Base {
    private static final long CHECK_AUTH_TOKEN_RESULT_MAX_INTERVAL = 300;
    private static final long CHECK_AUTH_TOKEN_RESULT_MIN_INTERVAL = 1;
    private static final long GET_AUTH_TOKEN_TIMEOUT = 30000;
    private static final int MAX_RETRY_TIME = 3;
    private static final int[] RETRY_INTERVALS = {5000, 10000};
    private static final String SERVICE_MICLOUD = "micloud";
    private static final String TAG = "RequestEvnCompat_V18";
    private ThreadLocal<String> mExtendedAuthToken = new ThreadLocal<>();
    private String mUserAgent;

    /* JADX INFO: Access modifiers changed from: private */
    public static String waitGetAuthToken(AccountManagerFuture<Bundle> accountManagerFuture) throws InterruptedException, TimeoutException, OperationCanceledException, IOException, AuthenticatorException {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        long jMin = 1;
        while (SystemClock.elapsedRealtime() - jElapsedRealtime < GET_AUTH_TOKEN_TIMEOUT) {
            if (accountManagerFuture.isDone()) {
                return accountManagerFuture.getResult().getString("authtoken");
            }
            Thread.sleep(jMin);
            jMin = Math.min(jMin * 2, 300L);
        }
        throw new TimeoutException();
    }

    @Override // micloud.compat.independent.request.RequestEnvBuilderCompat_Base, micloud.compat.independent.request.IRequestEnvBuilderCompat
    public IRequestEnvBuilderCompat.RequestEnv build() {
        return new IRequestEnvBuilderCompat.RequestEnv() { // from class: micloud.compat.independent.request.RequestEnvBuilderCompat_V18.1
            @Override // micloud.compat.independent.request.IRequestEnvBuilderCompat.RequestEnv
            public long getAutoRetryInterval() {
                return 0L;
            }

            @Override // micloud.compat.independent.request.IRequestEnvBuilderCompat.RequestEnv
            public String getEncryptedUserId(Context context, IBinder iBinder, Account account) throws RemoteException {
                return IXiaomiAccountServiceProxy.getEncryptedUserId(iBinder, account);
            }

            @Override // micloud.compat.independent.request.IRequestEnvBuilderCompat.RequestEnv
            public int getMaxRetryCount() {
                return 0;
            }

            @Override // micloud.compat.independent.request.IRequestEnvBuilderCompat.RequestEnv
            public Account getSystemAccount(Context context) {
                Account xiaomiAccount = ExtraAccountManager.getXiaomiAccount(context);
                if (xiaomiAccount != null) {
                    return xiaomiAccount;
                }
                Log.e(RequestEnvBuilderCompat_V18.TAG, "no account in system");
                return null;
            }

            @Override // micloud.compat.independent.request.IRequestEnvBuilderCompat.RequestEnv
            public synchronized String getUserAgent() {
                if (RequestEnvBuilderCompat_V18.this.mUserAgent == null) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(Build.MODEL);
                    sb.append("; MIUI/");
                    sb.append(Build.VERSION.INCREMENTAL);
                    String str = SystemProperties.get("ro.product.marketname");
                    if (!TextUtils.isEmpty(str)) {
                        sb.append("; ");
                        sb.append(str);
                    }
                    try {
                        try {
                            try {
                                if (((Boolean) Class.forName("miui.os.Build").getField("IS_ALPHA_BUILD").get(null)).booleanValue()) {
                                    sb.append(' ');
                                    sb.append("ALPHA");
                                }
                            } catch (ClassNotFoundException unused) {
                                Log.d(RequestEnvBuilderCompat_V18.TAG, "Not in MIUI in getUserAgent");
                            }
                        } catch (IllegalArgumentException unused2) {
                            Log.d(RequestEnvBuilderCompat_V18.TAG, "Not in MIUI in getUserAgent");
                        }
                    } catch (IllegalAccessException unused3) {
                        Log.d(RequestEnvBuilderCompat_V18.TAG, "Not in MIUI in getUserAgent");
                    } catch (NoSuchFieldException unused4) {
                        Log.d(RequestEnvBuilderCompat_V18.TAG, "Not in MIUI in getUserAgent");
                    }
                    RequestEnvBuilderCompat_V18.this.mUserAgent = sb.toString();
                }
                return RequestEnvBuilderCompat_V18.this.mUserAgent;
            }

            @Override // micloud.compat.independent.request.IRequestEnvBuilderCompat.RequestEnv
            public void invalidateAuthToken(Context context) {
                if (RequestEnvBuilderCompat_V18.this.mExtendedAuthToken.get() != null) {
                    Log.d(RequestEnvBuilderCompat_V18.TAG, "invalidateAutoToken");
                    AccountManager.get(context).invalidateAuthToken("com.xiaomi", (String) RequestEnvBuilderCompat_V18.this.mExtendedAuthToken.get());
                    RequestEnvBuilderCompat_V18.this.mExtendedAuthToken.set(null);
                }
            }

            @Override // micloud.compat.independent.request.IRequestEnvBuilderCompat.RequestEnv
            public String queryAuthToken(Context context) {
                String strWaitGetAuthToken;
                boolean z = false;
                for (int i = 0; i < 3; i++) {
                    try {
                        Account systemAccount = getSystemAccount(context);
                        if (systemAccount == null || (strWaitGetAuthToken = RequestEnvBuilderCompat_V18.waitGetAuthToken(AccountManager.get(context).getAuthToken(systemAccount, RequestEnvBuilderCompat_V18.SERVICE_MICLOUD, true, null, null))) == null) {
                            return null;
                        }
                        RequestEnvBuilderCompat_V18.this.mExtendedAuthToken.set(strWaitGetAuthToken);
                        return (String) RequestEnvBuilderCompat_V18.this.mExtendedAuthToken.get();
                    } catch (AuthenticatorException e) {
                        Log.e(RequestEnvBuilderCompat_V18.TAG, "AuthenticatorException when getting service token", e);
                        if (z) {
                            break;
                        }
                        invalidateAuthToken(context);
                        z = true;
                    } catch (OperationCanceledException e2) {
                        Log.e(RequestEnvBuilderCompat_V18.TAG, "OperationCanceledException when getting service token", e2);
                        return null;
                    } catch (IOException e3) {
                        Log.e(RequestEnvBuilderCompat_V18.TAG, "IOException when getting service token", e3);
                        if (i < 2) {
                            try {
                                Thread.sleep(RequestEnvBuilderCompat_V18.RETRY_INTERVALS[i]);
                            } catch (InterruptedException unused) {
                                Thread.currentThread().interrupt();
                                Log.e(RequestEnvBuilderCompat_V18.TAG, "InterruptedException when sleep", e3);
                            }
                        }
                    } catch (InterruptedException e4) {
                        Thread.currentThread().interrupt();
                        Log.e(RequestEnvBuilderCompat_V18.TAG, "InterruptedException when getting service token", e4);
                        return null;
                    } catch (TimeoutException e5) {
                        Log.e(RequestEnvBuilderCompat_V18.TAG, "TimeoutException when getting service token, retry if needed", e5);
                    }
                }
                return null;
            }

            @Override // micloud.compat.independent.request.IRequestEnvBuilderCompat.RequestEnv
            public boolean shouldUpdateHost() {
                return true;
            }
        };
    }
}
