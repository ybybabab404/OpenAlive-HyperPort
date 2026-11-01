package com.miui.aod.backup;

import android.app.backup.FullBackupDataOutput;
import android.content.Context;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.google.gson.Gson;
import com.miui.aod.LifeCycleObserver;
import com.miui.aod.Utils;
import com.miui.aod.util.FileUtils;
import com.miui.aod.util.ZipUtils;
import com.miui.keyguard.editor.data.template.BackupVersionManager;
import com.miui.keyguard.editor.data.template.MatchManager;
import com.miui.keyguard.editor.data.template.SystemParams;
import com.miui.keyguard.editor.data.template.TemplateBackupApi;
import com.miui.keyguard.editor.utils.CommonUtils;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import com.xiaomi.settingsdk.backup.ICloudBackup;
import com.xiaomi.settingsdk.backup.SettingsBackupHelper;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import miui.app.backup.BackupMeta;
import miui.app.backup.FullBackupAgent;
import miui.os.Build;

/* JADX INFO: loaded from: classes.dex */
public class AodFullBackupAgent extends FullBackupAgent {
    public TemplateBackupApi mTemplateBackupApi;

    public static void closeQuietly(Closeable closeable) {
        FileUtils.closeStream(closeable);
    }

    public static String createTargetPath(Context context, String str) {
        if (str == null || !str.contains("com.miui.aod") || str.length() <= 13) {
            return str;
        }
        return context.getDataDir() + File.separator + str.substring(str.indexOf("com.miui.aod") + 13);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v0, types: [android.os.ParcelFileDescriptor] */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v10 */
    /* JADX WARN: Type inference failed for: r5v11 */
    /* JADX WARN: Type inference failed for: r5v12 */
    /* JADX WARN: Type inference failed for: r5v13 */
    /* JADX WARN: Type inference failed for: r5v14 */
    /* JADX WARN: Type inference failed for: r5v15 */
    /* JADX WARN: Type inference failed for: r5v19, types: [java.io.Closeable, java.io.FileOutputStream, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v20 */
    /* JADX WARN: Type inference failed for: r5v21 */
    /* JADX WARN: Type inference failed for: r5v22 */
    /* JADX WARN: Type inference failed for: r5v23 */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v4 */
    /* JADX WARN: Type inference failed for: r5v5, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r5v6 */
    /* JADX WARN: Type inference failed for: r5v7, types: [java.io.Closeable] */
    /* JADX WARN: Type inference failed for: r5v8 */
    public static boolean restoreFile(ParcelFileDescriptor parcelFileDescriptor, String str, String str2) throws Throwable {
        ?? r5;
        ?? r6;
        FileInputStream fileInputStream = null;
        try {
            try {
                FileInputStream fileInputStream2 = new FileInputStream(parcelFileDescriptor.getFileDescriptor());
                try {
                    new File(str2.substring(0, str2.lastIndexOf(File.separator))).mkdirs();
                    parcelFileDescriptor = new FileOutputStream(new File(str2));
                    try {
                        byte[] bArr = new byte[AutoBottomSheetKt.ALIGN_SCREEN_TOP];
                        while (true) {
                            int i = fileInputStream2.read(bArr);
                            if (i <= 0) {
                                parcelFileDescriptor.flush();
                                closeQuietly(fileInputStream2);
                                closeQuietly(parcelFileDescriptor);
                                return true;
                            }
                            parcelFileDescriptor.write(bArr, 0, i);
                        }
                    } catch (FileNotFoundException e) {
                        e = e;
                        fileInputStream = fileInputStream2;
                        r6 = parcelFileDescriptor;
                        Log.e("SettingsBackup", "FileNotFoundException in restoreFiles: " + str, e);
                        parcelFileDescriptor = r6;
                        closeQuietly(fileInputStream);
                        closeQuietly(parcelFileDescriptor);
                        return false;
                    } catch (IOException e2) {
                        e = e2;
                        fileInputStream = fileInputStream2;
                        r5 = parcelFileDescriptor;
                        Log.e("SettingsBackup", "IOException in restoreFiles: " + str, e);
                        parcelFileDescriptor = r5;
                        closeQuietly(fileInputStream);
                        closeQuietly(parcelFileDescriptor);
                        return false;
                    } catch (Throwable th) {
                        th = th;
                        fileInputStream = fileInputStream2;
                        closeQuietly(fileInputStream);
                        closeQuietly(parcelFileDescriptor);
                        throw th;
                    }
                } catch (FileNotFoundException e3) {
                    e = e3;
                    parcelFileDescriptor = 0;
                } catch (IOException e4) {
                    e = e4;
                    parcelFileDescriptor = 0;
                } catch (Throwable th2) {
                    th = th2;
                    parcelFileDescriptor = 0;
                }
            } catch (Throwable th3) {
                th = th3;
            }
        } catch (FileNotFoundException e5) {
            e = e5;
            r6 = 0;
        } catch (IOException e6) {
            e = e6;
            r5 = 0;
        } catch (Throwable th4) {
            th = th4;
            parcelFileDescriptor = 0;
        }
    }

    public void addAttachedFile(String str) {
        super.addAttachedFile(str);
        Log.i("AodFullBackupAgent", "addAttachedFile: " + str);
    }

    public final ICloudBackup getAodBackupImpl() {
        return DeviceUtil.INSTANCE.isPad() ? new AodPadBackupImpl() : new AodBackupImpl(this);
    }

    public final String getBackupMeta(BackupMeta backupMeta) {
        if (backupMeta == null) {
            return "meta";
        }
        try {
            return new Gson().toJson(backupMeta);
        } catch (Exception unused) {
            return "error";
        }
    }

    public final int getDeviceType(BackupMeta backupMeta) {
        String deviceName = backupMeta.getDeviceName();
        Log.i("AodFullBackupAgent", "onAttachRestore: OS2 device name is " + deviceName);
        if (!DeviceUtil.INSTANCE.getPadDeviceNameSet().contains(deviceName)) {
            return 1;
        }
        Log.i("AodFullBackupAgent", "onAttachRestore: OS2 device is pad");
        return 2;
    }

    public int getVersion(int i) {
        Log.d("AodFullBackupAgent", "getVersion()=1, feature=" + i);
        return 1;
    }

    public int onAttachRestore(BackupMeta backupMeta, ParcelFileDescriptor parcelFileDescriptor, String str) {
        Log.i("AodFullBackupAgent", "onAttachRestore: " + str + ",backup meta is " + getBackupMeta(backupMeta));
        if (str == null) {
            Log.w("AodFullBackupAgent", "onAttachRestore: path is null");
            return 0;
        }
        if (!str.endsWith(".aodBackup")) {
            BackupVersionManager backupVersionManager = BackupVersionManager.INSTANCE;
            Integer numExtractVersionFromPath = backupVersionManager.extractVersionFromPath(str);
            if (numExtractVersionFromPath != null) {
                Log.i("AodFullBackupAgent", "onAttachRestore: keyguard backup detected, version=" + numExtractVersionFromPath);
                KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = KeyguardEditorMiSightUtil.INSTANCE;
                int iIntValue = numExtractVersionFromPath.intValue();
                int iDetectType = CommonUtils.INSTANCE.detectType();
                DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
                keyguardEditorMiSightUtil.restoreSessionStart("aod", "os" + numExtractVersionFromPath + "_zip", iIntValue, iDetectType, deviceUtil.osVersion(), getBackupMeta(backupMeta));
                keyguardEditorMiSightUtil.restoreStep1Start();
                if (!backupVersionManager.canCurrentVersionAccept(numExtractVersionFromPath.intValue())) {
                    Log.w("AodFullBackupAgent", "onAttachRestore: REJECT! current=" + deviceUtil.osVersion() + " cannot accept backup version=" + numExtractVersionFromPath);
                    keyguardEditorMiSightUtil.reportVersionReject(numExtractVersionFromPath.intValue(), deviceUtil.osVersion());
                    StringBuilder sb = new StringBuilder();
                    sb.append("version reject: backup=");
                    sb.append(numExtractVersionFromPath);
                    keyguardEditorMiSightUtil.restoreSessionFinish(false, sb.toString());
                    return 0;
                }
                keyguardEditorMiSightUtil.restoreStep1EndSuccess();
                if (numExtractVersionFromPath.intValue() == 2) {
                    restoreOS2Templates(backupMeta, parcelFileDescriptor);
                } else {
                    restoreVersionedBackup(parcelFileDescriptor, numExtractVersionFromPath.intValue());
                }
            } else {
                if (!Utils.isFlipDevice() || Build.IS_INTERNATIONAL_BUILD) {
                    Log.d("AodFullBackupAgent", "onAttachRestore: unrecognized backup file, path=" + str);
                    KeyguardEditorMiSightUtil.INSTANCE.reportVersionUnknown(str);
                    return 0;
                }
                Log.i("AodFullBackupAgent", "onAttachRestore: tiny keyguard backup detected (by device type)");
                TinyKeyguardBackupUtil.restoreTinyKeyguardData(parcelFileDescriptor, str, getApplicationContext());
            }
        } else {
            if (Utils.isFoldDevice()) {
                Log.d("AodFullBackupAgent", "onAttachRestore: skip for specified devices.");
                return 0;
            }
            String strCreateTargetPath = createTargetPath(getApplicationContext(), str);
            Log.i("AodFullBackupAgent", "onAttachRestore: TargetPath " + strCreateTargetPath);
            if (restoreFile(parcelFileDescriptor, str, strCreateTargetPath)) {
                String strSubstring = strCreateTargetPath.substring(0, strCreateTargetPath.indexOf(".aodBackup"));
                FileUtils.deleteFileAll(new File(strSubstring));
                ZipUtils.unzipFile(str, strSubstring);
                FileUtils.deleteFileAll(new File(strCreateTargetPath));
            } else {
                Log.e("AodFullBackupAgent", "onAttachRestore: restoreFile failed " + str);
                KeyguardEditorMiSightUtil.INSTANCE.reportRestoreFileWriteFail(str, null);
            }
        }
        return super.onAttachRestore(backupMeta, parcelFileDescriptor, str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void onCreate() {
        super.onCreate();
        LifeCycleObserver.INSTANCE.getKillScheduler().setBackupOrRestoreInProgress(true);
        this.mTemplateBackupApi = TemplateBackupApi.Companion.getInstance(this);
    }

    public int onDataRestore(BackupMeta backupMeta, ParcelFileDescriptor parcelFileDescriptor) {
        Log.d("AodFullBackupAgent", "onDataRestore()");
        if (Utils.isFoldDevice()) {
            Log.d("AodFullBackupAgent", "onDataRestore: skip for specified devices.");
            return 0;
        }
        if (backupMeta.getVersion() == 1) {
            SettingsBackupHelper.restoreSettings(getApplicationContext(), parcelFileDescriptor, getAodBackupImpl());
        }
        return 0;
    }

    public void onDestroy() {
        LifeCycleObserver.INSTANCE.getKillScheduler().setBackupOrRestoreInProgress(false);
        super.onDestroy();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public int onFullBackup(ParcelFileDescriptor parcelFileDescriptor, int i) {
        Log.d("AodFullBackupAgent", "onFullBackup()");
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        DeviceUtil.ScreenInfo currentScreenInfo = deviceUtil.getCurrentScreenInfo(this);
        int iDetectType = CommonUtils.INSTANCE.detectType();
        int iOsVersion = deviceUtil.osVersion();
        KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = KeyguardEditorMiSightUtil.INSTANCE;
        keyguardEditorMiSightUtil.backupSessionStart("aod", iDetectType, iOsVersion, currentScreenInfo.getWidth(), currentScreenInfo.getHeight(), currentScreenInfo.getDensity());
        if (!Utils.isFoldDevice()) {
            SettingsBackupHelper.backupSettings(getApplicationContext(), parcelFileDescriptor, getAodBackupImpl());
        }
        if (new MatchManager(new SystemParams(iDetectType, iOsVersion, currentScreenInfo.getWidth(), currentScreenInfo.getHeight(), currentScreenInfo.getDensity(), false, "")).isMatchBackup()) {
            this.mTemplateBackupApi.backupAllTemplates(this);
        } else {
            Log.d("AodFullBackupAgent", "onFullBackup: skip for specified devices.");
            keyguardEditorMiSightUtil.reportBackupMatchSkip();
        }
        if (!Utils.isFlipDevice() || Build.IS_INTERNATIONAL_BUILD) {
            return 0;
        }
        TinyKeyguardBackupUtil.backupTinyKeyguardData(this);
        return 0;
    }

    public int onRestoreEnd(BackupMeta backupMeta) {
        Log.d("AodFullBackupAgent", "onRestoreEnd()");
        KeyguardEditorMiSightUtil.INSTANCE.restoreSessionFinish(true, null);
        return super.onRestoreEnd(backupMeta);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void restoreOS2Templates(BackupMeta backupMeta, ParcelFileDescriptor parcelFileDescriptor) {
        Log.i("AodFullBackupAgent", "onAttachRestore: OS2");
        if (backupMeta == null) {
            Log.i("AodFullBackupAgent", "onAttachRestore: cannot obtain OS2 device message.");
            return;
        }
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        DeviceUtil.ScreenInfo currentScreenInfo = deviceUtil.getCurrentScreenInfo(this);
        int deviceType = getDeviceType(backupMeta);
        MatchManager matchManager = new MatchManager(new SystemParams(deviceType, 2, currentScreenInfo.getWidth(), currentScreenInfo.getHeight(), currentScreenInfo.getDensity(), false, ""));
        KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = KeyguardEditorMiSightUtil.INSTANCE;
        keyguardEditorMiSightUtil.restoreStep2Start();
        if (!matchManager.isMatchRestore()) {
            Log.d("AodFullBackupAgent", "onAttachRestore: skip for specified devices OS2.");
            keyguardEditorMiSightUtil.reportDeviceMismatch(deviceType, CommonUtils.INSTANCE.detectType(), 2, deviceUtil.osVersion(), "OS2 device mismatch");
            keyguardEditorMiSightUtil.restoreSessionFinish(false, "OS2 device mismatch");
        } else {
            keyguardEditorMiSightUtil.restoreStep2EndSuccess();
            String oldBackupZipFilePath = this.mTemplateBackupApi.getOldBackupZipFilePath();
            this.mTemplateBackupApi.unzipSettingBackupFile(parcelFileDescriptor, oldBackupZipFilePath);
            Log.i("AodFullBackupAgent", "onAttachRestore: tryRestoreAllTemplates OS2");
            this.mTemplateBackupApi.tryRestoreAllTemplates(oldBackupZipFilePath);
        }
    }

    public final void restoreVersionedBackup(ParcelFileDescriptor parcelFileDescriptor, int i) {
        Log.i("AodFullBackupAgent", "restoreVersionedBackup: version=" + i);
        String backupZipFilePathByVersion = this.mTemplateBackupApi.getBackupZipFilePathByVersion(i);
        this.mTemplateBackupApi.unzipSettingBackupFile(parcelFileDescriptor, backupZipFilePathByVersion);
        KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = KeyguardEditorMiSightUtil.INSTANCE;
        keyguardEditorMiSightUtil.restoreStep2Start();
        SystemParams backupSettings = this.mTemplateBackupApi.getBackupSettings();
        if (!new MatchManager(backupSettings).isMatchRestore()) {
            Log.w("AodFullBackupAgent", "restoreVersionedBackup: version/device not matched, skip restore.");
            keyguardEditorMiSightUtil.reportDeviceMismatch(backupSettings.getDeviceType(), CommonUtils.INSTANCE.detectType(), backupSettings.getOsVersion(), DeviceUtil.INSTANCE.osVersion(), "version/device not matched");
            keyguardEditorMiSightUtil.restoreSessionFinish(false, "device mismatch in restoreVersionedBackup");
            this.mTemplateBackupApi.deleteBackupZipFile(backupZipFilePathByVersion);
            return;
        }
        keyguardEditorMiSightUtil.restoreStep2EndSuccess();
        Log.i("AodFullBackupAgent", "restoreVersionedBackup: tryRestoreAllTemplates, version=" + i);
        this.mTemplateBackupApi.tryRestoreAllTemplates(backupZipFilePathByVersion);
    }

    public int tarAttaches(String str, FullBackupDataOutput fullBackupDataOutput, int i) {
        int iTarAttaches = super.tarAttaches(str, fullBackupDataOutput, i);
        this.mTemplateBackupApi.backupAllTemplatesCompleted();
        if (Utils.isFlipDevice() && !Build.IS_INTERNATIONAL_BUILD) {
            TinyKeyguardBackupUtil.backupTinyKeyguardDataCompleted(getApplicationContext());
        }
        KeyguardEditorMiSightUtil.INSTANCE.backupSessionFinish(true, null);
        return iTarAttaches;
    }
}
