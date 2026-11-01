package com.miui.keyguard.editor.data.template;

import android.content.Context;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.SensorWallpaperPrams;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.utils.BitmapUtil;
import com.miui.keyguard.editor.utils.FileUtil;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

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
@SourceDebugExtension
public final class SensorWallpaperRestore extends BaseWallpaperRestore {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SensorWallpaperRestore(Context context, WallpaperInfo wallpaperInfo, TemplateFilePathGenerator filePathGenerator) {
        super(context, wallpaperInfo, filePathGenerator);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(wallpaperInfo, "wallpaperInfo");
        Intrinsics.checkNotNullParameter(filePathGenerator, "filePathGenerator");
    }

    public boolean restore(boolean z) {
        String str;
        SensorWallpaperPrams sensorWallpaperPrams;
        if (getWallpaperInfo().getSensorWallpaperPrams() == null) {
            Log.i("Keyguard-Editor-BackupManager", "SensorWallpaperRestore::restore: sensorWallpaperPrams = null,isLock = " + z);
            return false;
        }
        SensorWallpaperPrams sensorWallpaperPrams2 = getWallpaperInfo().getSensorWallpaperPrams();
        if (sensorWallpaperPrams2 == null) {
            return false;
        }
        String strGenerateTemplateBackupFilePath = getFilePathGenerator().generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.WALLPAPER_VIDEO);
        sensorWallpaperPrams2.setThumbnailPath(getFilePathGenerator().generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.SENSOR_WALLPAPER_PREVIEW));
        sensorWallpaperPrams2.setSmallThumbnailPath(getFilePathGenerator().generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.SENSOR_WALLPAPER_PREVIEW_SMALL_SCREEN));
        Log.i("Keyguard-Editor-BackupManager", "SensorWallpaperRestore::restore: sensorWallpaperPrams = " + sensorWallpaperPrams2 + ",isLock = " + z);
        boolean z2 = FileUtil.isFileExist(strGenerateTemplateBackupFilePath) && new File(strGenerateTemplateBackupFilePath).length() > 0;
        boolean zIsFileExist = FileUtil.isFileExist(sensorWallpaperPrams2.getThumbnailPath());
        boolean zIsFileExist2 = FileUtil.isFileExist(sensorWallpaperPrams2.getSmallThumbnailPath());
        Log.i("Keyguard-Editor-BackupManager", "SensorWallpaperRestore::restore: videoWallpaperExist = " + z2 + ",thumbnailExist = " + zIsFileExist + ",smallThumbnailExist = " + zIsFileExist2 + ",isLock = " + z);
        if (!z2 || !zIsFileExist || !zIsFileExist2) {
            return false;
        }
        Log.i("Keyguard-Editor-BackupManager", "SensorWallpaperRestore::restore: restore video wallpaper,allFrames = " + sensorWallpaperPrams2.getAllFrames() + ",smallFrames = " + sensorWallpaperPrams2.getSmallFrames() + ",stiffness = " + sensorWallpaperPrams2.getStiffness() + ",magicType = " + getWallpaperInfo().getMagicType() + ",isLock = " + z);
        WallpaperController.Companion companion = WallpaperController.Companion;
        WallpaperController companion2 = companion.getInstance(getContext());
        Context applicationContext = getContext().getApplicationContext();
        Intrinsics.checkNotNullExpressionValue(applicationContext, "getApplicationContext(...)");
        BitmapUtil bitmapUtil = BitmapUtil.INSTANCE;
        companion2.setMiuiSensorWallpaper(applicationContext, strGenerateTemplateBackupFilePath, BitmapUtil.fromFile$default(bitmapUtil, sensorWallpaperPrams2.getThumbnailPath(), 0, 0, null, 14, null), BitmapUtil.fromFile$default(bitmapUtil, sensorWallpaperPrams2.getSmallThumbnailPath(), 0, 0, null, 14, null), sensorWallpaperPrams2.getAllFrames(), sensorWallpaperPrams2.getSmallFrames(), sensorWallpaperPrams2.getStiffness(), sensorWallpaperPrams2.getDuration(), sensorWallpaperPrams2.getFactor(), sensorWallpaperPrams2.getAnimType(), getWallpaperInfo().getMagicType(), true, false, true, z);
        if (companion.shouldApplyOuterVideoWallpaper(getWallpaperInfo(), new String[0])) {
            String strGenerateTemplateBackupFilePath2 = getFilePathGenerator().generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.OUTER_VIDEO_WALLPAPER_BACKUP);
            boolean z3 = FileUtil.isFileExist(strGenerateTemplateBackupFilePath2) && new File(strGenerateTemplateBackupFilePath2).length() > 0;
            str = "Keyguard-Editor-BackupManager";
            Log.i(str, "SensorWallpaperRestore: Q18 outer backup=" + strGenerateTemplateBackupFilePath2 + " exist=" + z3);
            String str2 = null;
            if (z3) {
                String strGenerateCurrentTemplateFilePath = getFilePathGenerator().generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.OUTER_VIDEO_WALLPAPER);
                FileUtil.copyFile(strGenerateTemplateBackupFilePath2, strGenerateCurrentTemplateFilePath);
                sensorWallpaperPrams = sensorWallpaperPrams2;
                sensorWallpaperPrams.setOuterVideoPath(strGenerateCurrentTemplateFilePath);
                if (FileUtil.isFileExist(strGenerateCurrentTemplateFilePath)) {
                    str2 = strGenerateCurrentTemplateFilePath;
                }
            } else {
                sensorWallpaperPrams = sensorWallpaperPrams2;
            }
            if (str2 != null) {
                String outerVideoPreViewPath = sensorWallpaperPrams.getOuterVideoPreViewPath();
                if (outerVideoPreViewPath == null) {
                    outerVideoPreViewPath = sensorWallpaperPrams.getSmallThumbnailPath();
                }
                Log.i(str, "SensorWallpaperRestore: Q18 outer video restore: video=" + str2 + ", preview=" + outerVideoPreViewPath);
                WallpaperController.applyQ18OuterVideo$default(companion.getInstance(getContext()), str2, outerVideoPreViewPath, false, false, 12, null);
            }
            FileUtil.delete(strGenerateTemplateBackupFilePath2);
        } else {
            str = "Keyguard-Editor-BackupManager";
            sensorWallpaperPrams = sensorWallpaperPrams2;
            Log.i(str, "SensorWallpaperRestore: skip Q18 outer restore, isLock=" + z + ", resourceType=" + getWallpaperInfo().getResourceType() + ", source=" + getWallpaperInfo().getSource() + ", origin=" + getWallpaperInfo().getOriginResourcePath() + ", outerVideoPath=" + sensorWallpaperPrams.getOuterVideoPath() + ", outerVideoPreViewPath=" + sensorWallpaperPrams.getOuterVideoPreViewPath());
        }
        FileUtil.delete(strGenerateTemplateBackupFilePath);
        FileUtil.delete(sensorWallpaperPrams.getThumbnailPath());
        FileUtil.delete(sensorWallpaperPrams.getSmallThumbnailPath());
        Log.i(str, "SensorWallpaperRestore::restore: restore video wallpaper end,isLock = " + z);
        return true;
    }
}
