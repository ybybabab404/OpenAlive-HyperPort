package com.miui.keyguard.editor.data.template;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.utils.BitmapUtil;
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
public final class ImageWallpaperRestore extends BaseWallpaperRestore {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ImageWallpaperRestore(Context context, WallpaperInfo wallpaperInfo, TemplateFilePathGenerator filePathGenerator) {
        super(context, wallpaperInfo, filePathGenerator);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(wallpaperInfo, "wallpaperInfo");
        Intrinsics.checkNotNullParameter(filePathGenerator, "filePathGenerator");
    }

    public boolean restore(boolean z) {
        String strGenerateTemplateBackupFilePath = z ? getFilePathGenerator().generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.WALLPAPER) : getFilePathGenerator().generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.WALLPAPER_HOME);
        if (!FileUtil.isFileExist(strGenerateTemplateBackupFilePath)) {
            Log.w("Keyguard-Editor-BackupManager", "ImageWallpaperRestore::restore: path is not exist,path is " + strGenerateTemplateBackupFilePath);
            return false;
        }
        Bitmap bitmapFromFile$default = BitmapUtil.fromFile$default(BitmapUtil.INSTANCE, strGenerateTemplateBackupFilePath, 0, 0, null, 14, null);
        if (bitmapFromFile$default == null) {
            Log.w("Keyguard-Editor-BackupManager", "ImageWallpaperRestore::restore: wallpaper = null");
            FileUtil.delete(strGenerateTemplateBackupFilePath);
            return false;
        }
        boolean zIsSupportMatting = TemplateDataUtil.INSTANCE.isSupportMatting(getContext());
        Log.i("Keyguard-Editor-BackupManager", "ImageWallpaperRestore::restore: magicType = " + getWallpaperInfo().getMagicType() + ",isLock = " + z + ",isNeedDark = " + getWallpaperInfo().isNeedDark() + ",enableBlur = " + getWallpaperInfo().getEnableBlur() + ",isSupportMatting = " + zIsSupportMatting + ",enableDoodle = " + getWallpaperInfo().getEnableDoodle() + ",wallpaper.width = " + bitmapFromFile$default.getWidth() + ",wallpaper.height = " + bitmapFromFile$default.getHeight());
        WallpaperController.Companion.getInstance(getContext()).applyImageLockWallpaper(bitmapFromFile$default, getWallpaperInfo().getMagicType(), z, true, getWallpaperInfo().isNeedDark(), getWallpaperInfo().getEnableBlur(), zIsSupportMatting, getWallpaperInfo().getEnableDoodle(), new Pair(Integer.valueOf(bitmapFromFile$default.getWidth()), Integer.valueOf(bitmapFromFile$default.getHeight())), WallpaperApplyParam.Companion.defaultInstance(), false);
        FileUtil.delete(strGenerateTemplateBackupFilePath);
        StringBuilder sb = new StringBuilder();
        sb.append("ImageWallpaperRestore::restore: restore wallpaper end,isLock = ");
        sb.append(z);
        Log.i("Keyguard-Editor-BackupManager", sb.toString());
        return true;
    }
}
