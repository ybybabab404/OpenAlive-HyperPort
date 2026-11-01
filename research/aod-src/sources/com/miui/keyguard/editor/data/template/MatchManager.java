package com.miui.keyguard.editor.data.template;

import android.util.Log;
import com.miui.keyguard.editor.data.migration.AllInOneClockMigration;
import com.miui.keyguard.editor.utils.CommonUtils;
import com.miui.keyguard.editor.utils.DeviceUtil;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import miuix.device.DeviceUtils;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: MatchManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class MatchManager {
    public final SystemParams oldSystemParams;

    public MatchManager(SystemParams oldSystemParams) {
        Intrinsics.checkNotNullParameter(oldSystemParams, "oldSystemParams");
        this.oldSystemParams = oldSystemParams;
    }

    public final boolean deviceMatch(int i, int i2) {
        CommonUtils commonUtils = CommonUtils.INSTANCE;
        if (commonUtils.isPhoneOrFlipDevice(i) && commonUtils.isPhoneOrFlipDevice(i2)) {
            Log.i("Keyguard-Editor-MatchManager", "deviceMatch: device match by phone or flip");
            return true;
        }
        if (commonUtils.isTabletDevice(i) && commonUtils.isTabletDevice(i2)) {
            Log.i("Keyguard-Editor-MatchManager", "deviceMatch: device match by tablet");
            return true;
        }
        if (commonUtils.isFoldDevice(i) && commonUtils.isFoldDevice(i2)) {
            Log.i("Keyguard-Editor-MatchManager", "deviceMatch: device match by fold");
            return true;
        }
        Log.i("Keyguard-Editor-MatchManager", "deviceMatch: device did not match");
        return false;
    }

    public final boolean isFoldToQ18() {
        return DeviceUtil.INSTANCE.isQ18Device() && CommonUtils.INSTANCE.isFoldDevice(this.oldSystemParams.getDeviceType()) && !isOldDeviceQ18();
    }

    public final boolean isMatchAllInOne(String str, int i) {
        if (str == null || str.length() == 0) {
            return false;
        }
        return Intrinsics.areEqual(str, "all_in_one") || AllInOneClockMigration.INSTANCE.shouldUpgradeToAllInOne(str, i, DeviceUtil.INSTANCE.isQ18Device());
    }

    public final boolean isMatchBackup() {
        int iOsVersion = DeviceUtil.INSTANCE.osVersion();
        Log.i("Keyguard-Editor-MatchManager", "isMatchBackup: currentOsVersion = " + iOsVersion);
        if (iOsVersion >= 4) {
            return true;
        }
        Log.i("Keyguard-Editor-MatchManager", "isMatchBackup: os is old,but apk is new");
        return false;
    }

    public final boolean isMatchRestore() {
        int iOsVersion = DeviceUtil.INSTANCE.osVersion();
        int iDetectType = CommonUtils.INSTANCE.detectType();
        Log.i("Keyguard-Editor-MatchManager", "isMatchRestore: currentOsVersion = " + iOsVersion + ",currentDeviceType = " + iDetectType + ",oldSystemParams.osVersion = " + this.oldSystemParams.getOsVersion() + ",oldSystemParams.deviceType = " + this.oldSystemParams.getDeviceType());
        if (iOsVersion < 3 || iOsVersion < this.oldSystemParams.getOsVersion()) {
            Log.i("Keyguard-Editor-MatchManager", "isMatchRestore: os is old,but apk is new");
            return false;
        }
        if (!deviceMatch(this.oldSystemParams.getDeviceType(), iDetectType)) {
            Log.i("Keyguard-Editor-MatchManager", "isMatchRestore: device did not match");
            return false;
        }
        if (!isVersionBeforeOS4() || !isFoldToQ18()) {
            return true;
        }
        Log.i("Keyguard-Editor-MatchManager", "isMatchRestore: pre-OS4 Fold→Q18, skip all restore");
        return false;
    }

    public final boolean isOldDeviceQ18() {
        return Intrinsics.areEqual(this.oldSystemParams.getDeviceCodename(), "lhasa");
    }

    public final boolean isQ18ToFold() {
        return isOldDeviceQ18() && !DeviceUtil.INSTANCE.isQ18Device() && DeviceUtils.isFoldDevice();
    }

    public final boolean isVersionBeforeOS4() {
        return this.oldSystemParams.getOsVersion() < 4;
    }

    public final boolean shouldFallbackToDefaultAllInOne(String str, int i) {
        if (str == null || str.length() == 0 || !isFoldToQ18() || Intrinsics.areEqual(str, "all_in_one") || AllInOneClockMigration.INSTANCE.getUPGRADABLE_TEMPLATES().contains(str)) {
            return false;
        }
        Log.i("Keyguard-Editor-MatchManager", "shouldFallbackToDefaultAllInOne: templateId=" + str + " not recognized for Q18, fallback to default AllInOne");
        return true;
    }

    public final boolean shouldSkipHistoryTemplateRestore() {
        if (!isFoldToQ18()) {
            if (!isQ18ToFold()) {
                return false;
            }
            Log.i("Keyguard-Editor-MatchManager", "shouldSkipHistoryTemplateRestore: skip, Q18→Fold");
            return true;
        }
        Log.i("Keyguard-Editor-MatchManager", "shouldSkipHistoryTemplateRestore: skip, Fold→Q18, oldCodename=" + this.oldSystemParams.getDeviceCodename());
        return true;
    }
}
