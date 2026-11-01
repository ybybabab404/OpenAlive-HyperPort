package com.miui.keyguard.editor.data.template;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.ExtraInfo;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.edit.wallpaper.FashionGalleryHelper;
import com.miui.keyguard.editor.edit.wallpaper.IWallpaperController;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.utils.BitmapUtil;
import com.miui.keyguard.editor.utils.FileUtil;
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
public final class VideoWallpaperRestore extends BaseWallpaperRestore {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VideoWallpaperRestore(Context context, WallpaperInfo wallpaperInfo, TemplateFilePathGenerator filePathGenerator) {
        super(context, wallpaperInfo, filePathGenerator);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(wallpaperInfo, "wallpaperInfo");
        Intrinsics.checkNotNullParameter(filePathGenerator, "filePathGenerator");
    }

    public boolean restore(boolean z) {
        WallpaperController companion = WallpaperController.Companion.getInstance(getContext());
        String strGenerateTemplateBackupFilePath = z ? getFilePathGenerator().generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.WALLPAPER_VIDEO) : getFilePathGenerator().generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.WALLPAPER_HOME_VIDEO);
        String strGenerateTemplateBackupFilePath2 = z ? getFilePathGenerator().generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.WALLPAPER) : getFilePathGenerator().generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.WALLPAPER_HOME);
        String strGenerateCurrentTemplateFilePath = z ? getFilePathGenerator().generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER) : getFilePathGenerator().generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER_HOME);
        boolean zIsFileExist = FileUtil.isFileExist(strGenerateCurrentTemplateFilePath);
        boolean zIsFileExist2 = FileUtil.isFileExist(strGenerateTemplateBackupFilePath2);
        boolean zIsFileExist3 = FileUtil.isFileExist(strGenerateTemplateBackupFilePath);
        if (!zIsFileExist3 || (!zIsFileExist2 && !zIsFileExist)) {
            Log.i("Keyguard-Editor-BackupManager", "VideoWallpaperRestore::restore: wallpaper exist " + zIsFileExist + ",wallpaper backup exist " + zIsFileExist2 + ",video wallpaper backup exist " + zIsFileExist3 + ",isLock is " + z + ",wallpaperPath is " + strGenerateTemplateBackupFilePath2 + ",videoWallpaperPath is " + strGenerateTemplateBackupFilePath);
            FileUtil.delete(strGenerateTemplateBackupFilePath);
            return false;
        }
        Bitmap bitmapFromFile$default = BitmapUtil.fromFile$default(BitmapUtil.INSTANCE, zIsFileExist2 ? strGenerateTemplateBackupFilePath2 : strGenerateCurrentTemplateFilePath, 0, 0, null, 14, null);
        if (bitmapFromFile$default == null) {
            Log.w("Keyguard-Editor-BackupManager", "VideoWallpaperRestore::restore: previewBitmap = null,isLock = " + z);
            FileUtil.delete(strGenerateTemplateBackupFilePath);
            return false;
        }
        Log.w("Keyguard-Editor-BackupManager", "VideoWallpaperRestore::restore:videoWallpaperPath = " + strGenerateTemplateBackupFilePath + ",previewBitmap = " + bitmapFromFile$default + ",magicType = " + getWallpaperInfo().getMagicType() + ",isLock = " + z);
        FashionGalleryHelper fashionGalleryHelper = FashionGalleryHelper.INSTANCE;
        Context context = getContext();
        ExtraInfo extraInfo = getWallpaperInfo().getExtraInfo();
        IWallpaperController.DefaultImpls.applyVideoWallpaper$default(companion, strGenerateTemplateBackupFilePath, bitmapFromFile$default, getWallpaperInfo().getMagicType(), false, z, false, false, false, fashionGalleryHelper.isLoop(context, z, extraInfo != null ? extraInfo.getLoopType() : null) ? 1 : getWallpaperInfo().getEnableDoodle(), getWallpaperInfo().isSupportFullScreenAod(), 224, null);
        FileUtil.delete(strGenerateTemplateBackupFilePath);
        FileUtil.delete(strGenerateTemplateBackupFilePath2);
        Log.i("Keyguard-Editor-BackupManager", "VideoWallpaperRestore::restore: restore video wallpaper end,isLock = " + z);
        return true;
    }
}
