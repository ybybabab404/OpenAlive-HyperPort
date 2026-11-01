package com.miui.keyguard.editor.data.template;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.utils.BitmapUtil;
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
public final class ImageWallpaperBackup extends BaseWallpaperBackup {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ImageWallpaperBackup(Context context, TemplateFilePathGenerator filePathGenerator) {
        super(context, filePathGenerator);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(filePathGenerator, "filePathGenerator");
    }

    @Override // com.miui.keyguard.editor.data.template.BaseWallpaperBackup
    public void backup(boolean z) {
        WallpaperController companion = WallpaperController.Companion.getInstance(getContext());
        Context context = getContext();
        Bitmap lockWallpaperPreview = z ? companion.getLockWallpaperPreview(context) : companion.getHomeWallpaperPreview(context);
        String strGenerateTemplateBackupFilePath = z ? getFilePathGenerator().generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.WALLPAPER) : getFilePathGenerator().generateTemplateBackupFilePath(TemplateFilePathGenerator.TemplateBackupFileType.WALLPAPER_HOME);
        if (lockWallpaperPreview == null) {
            Log.w("Keyguard-Editor-BackupManager", "ImageWallpaperBackup::backup: backup wallpaper is null,isLock is " + z);
            return;
        }
        Log.i("Keyguard-Editor-BackupManager", "ImageWallpaperBackup::backup: backup wallpaper,isLock is " + z + ",wallpaperBackupPath is " + strGenerateTemplateBackupFilePath);
        BitmapUtil.toWebp$default(BitmapUtil.INSTANCE, lockWallpaperPreview, strGenerateTemplateBackupFilePath, false, 4, null);
    }
}
