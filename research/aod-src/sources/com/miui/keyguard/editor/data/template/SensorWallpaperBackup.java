package com.miui.keyguard.editor.data.template;

import android.content.Context;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.SensorWallpaperPrams;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.utils.FileUtil;
import kotlin.Metadata;
import kotlin.Pair;
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
public final class SensorWallpaperBackup extends BaseWallpaperBackup {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SensorWallpaperBackup(Context context, TemplateFilePathGenerator filePathGenerator) {
        super(context, filePathGenerator);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(filePathGenerator, "filePathGenerator");
    }

    @Override // com.miui.keyguard.editor.data.template.BaseWallpaperBackup
    public void backup(boolean z) {
        String strGenerateTemplateBackupFilePath = getFilePathGenerator().generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.WALLPAPER_VIDEO);
        WallpaperController.Companion companion = WallpaperController.Companion;
        Pair sensorPrams = companion.getInstance(getContext()).getSensorPrams(getContext(), z);
        if (sensorPrams == null) {
            Log.w("Keyguard-Editor-BackupManager", "SensorWallpaperBackup::backup: getSensorPrams returned null,isLock = " + z);
            return;
        }
        String str = (String) sensorPrams.getFirst();
        Log.i("Keyguard-Editor-BackupManager", "SensorWallpaperBackup::backup: backup sensor wallpaper,path is " + str + ",videoWallpaperBackupPath is " + strGenerateTemplateBackupFilePath);
        FileUtil.copyFile(str, strGenerateTemplateBackupFilePath);
        String strGenerateTemplateBackupFilePath2 = getFilePathGenerator().generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.SENSOR_WALLPAPER_PREVIEW);
        SensorWallpaperPrams sensorWallpaperPrams = (SensorWallpaperPrams) sensorPrams.getSecond();
        String outerVideoPath = null;
        Log.i("Keyguard-Editor-BackupManager", "SensorWallpaperBackup::backup: backup sensor wallpaper,thumbnailPath is " + (sensorWallpaperPrams != null ? sensorWallpaperPrams.getThumbnailPath() : null) + ",sensorThumbnailBackupPath is " + strGenerateTemplateBackupFilePath2);
        FileUtil.copyFile(sensorWallpaperPrams != null ? sensorWallpaperPrams.getThumbnailPath() : null, strGenerateTemplateBackupFilePath2);
        String strGenerateTemplateBackupFilePath3 = getFilePathGenerator().generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.SENSOR_WALLPAPER_PREVIEW_SMALL_SCREEN);
        Log.i("Keyguard-Editor-BackupManager", "SensorWallpaperBackup::backup: backup sensor wallpaper,smallThumbnailPath is " + (sensorWallpaperPrams != null ? sensorWallpaperPrams.getSmallThumbnailPath() : null) + ",sensorSmallThumbnailBackupPath is " + strGenerateTemplateBackupFilePath3);
        FileUtil.copyFile(sensorWallpaperPrams != null ? sensorWallpaperPrams.getSmallThumbnailPath() : null, strGenerateTemplateBackupFilePath3);
        if (!companion.shouldApplyOuterVideoWallpaper("sensor", str, sensorWallpaperPrams, new String[0])) {
            Log.i("Keyguard-Editor-BackupManager", "SensorWallpaperBackup: skip Q18 outer backup, path=" + str + ", outerVideoPath=" + (sensorWallpaperPrams != null ? sensorWallpaperPrams.getOuterVideoPath() : null) + ", outerVideoPreViewPath=" + (sensorWallpaperPrams != null ? sensorWallpaperPrams.getOuterVideoPreViewPath() : null));
            return;
        }
        String strGenerateCurrentTemplateFilePath = getFilePathGenerator().generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.OUTER_VIDEO_WALLPAPER);
        String strGenerateTemplateBackupFilePath4 = getFilePathGenerator().generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.OUTER_VIDEO_WALLPAPER_BACKUP);
        if (FileUtil.isFileExist(strGenerateCurrentTemplateFilePath)) {
            outerVideoPath = strGenerateCurrentTemplateFilePath;
        } else if (sensorWallpaperPrams != null) {
            outerVideoPath = sensorWallpaperPrams.getOuterVideoPath();
        }
        if (outerVideoPath == null || outerVideoPath.length() == 0 || !FileUtil.isFileExist(outerVideoPath)) {
            return;
        }
        FileUtil.copyFile(outerVideoPath, strGenerateTemplateBackupFilePath4);
        Log.i("Keyguard-Editor-BackupManager", "SensorWallpaperBackup: Q18 outer video backup: " + outerVideoPath + " -> " + strGenerateTemplateBackupFilePath4);
    }
}
