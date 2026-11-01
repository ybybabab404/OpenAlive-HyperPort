package com.miui.keyguard.editor.data.template;

import android.content.Context;
import android.util.Log;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: CurrentWallpaperBackupManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class LockBackupManager extends BaseWallpaperBackup {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LockBackupManager(Context context, TemplateFilePathGenerator filePathGenerator) {
        super(context, filePathGenerator);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(filePathGenerator, "filePathGenerator");
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0080  */
    @Override // com.miui.keyguard.editor.data.template.BaseWallpaperBackup
    public void backup(boolean z) {
        BaseWallpaperBackup sensorWallpaperBackup;
        String wallpaperType = CurrentWallpaperBackupManagerKt.getWallpaperType(WallpaperController.Companion.getInstance(getContext()), z);
        Log.w("Keyguard-Editor-BackupManager", "LockBackupManager::backup: lockWallpaperType is " + wallpaperType);
        if (wallpaperType == null) {
            KeyguardEditorMiSightUtil.INSTANCE.reportBackupWallpaperNullType();
            sensorWallpaperBackup = null;
        } else {
            int iHashCode = wallpaperType.hashCode();
            if (iHashCode != -905948230) {
                if (iHashCode != 100313435) {
                    if (iHashCode == 112202875 && wallpaperType.equals("video")) {
                        sensorWallpaperBackup = new VideoWallpaperBackup(getContext(), getFilePathGenerator());
                    } else {
                        KeyguardEditorMiSightUtil.INSTANCE.reportBackupWallpaperNullType();
                        sensorWallpaperBackup = null;
                    }
                } else if (wallpaperType.equals("image")) {
                    sensorWallpaperBackup = new ImageWallpaperBackup(getContext(), getFilePathGenerator());
                } else {
                    KeyguardEditorMiSightUtil.INSTANCE.reportBackupWallpaperNullType();
                    sensorWallpaperBackup = null;
                }
            } else if (wallpaperType.equals("sensor")) {
                sensorWallpaperBackup = new SensorWallpaperBackup(getContext(), getFilePathGenerator());
            } else {
                KeyguardEditorMiSightUtil.INSTANCE.reportBackupWallpaperNullType();
                sensorWallpaperBackup = null;
            }
        }
        if (sensorWallpaperBackup != null) {
            sensorWallpaperBackup.backup(z);
        }
    }
}
