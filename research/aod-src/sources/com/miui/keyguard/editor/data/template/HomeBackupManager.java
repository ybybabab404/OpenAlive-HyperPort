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
public final class HomeBackupManager extends BaseWallpaperBackup {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HomeBackupManager(Context context, TemplateFilePathGenerator filePathGenerator) {
        super(context, filePathGenerator);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(filePathGenerator, "filePathGenerator");
    }

    @Override // com.miui.keyguard.editor.data.template.BaseWallpaperBackup
    public void backup(boolean z) {
        BaseWallpaperBackup videoWallpaperBackup;
        String wallpaperType = CurrentWallpaperBackupManagerKt.getWallpaperType(WallpaperController.Companion.getInstance(getContext()), z);
        Log.w("Keyguard-Editor-BackupManager", "HomeBackupManager::backup: homeWallpaperType is " + wallpaperType);
        if (Intrinsics.areEqual(wallpaperType, "image")) {
            videoWallpaperBackup = new ImageWallpaperBackup(getContext(), getFilePathGenerator());
        } else if (Intrinsics.areEqual(wallpaperType, "video")) {
            videoWallpaperBackup = new VideoWallpaperBackup(getContext(), getFilePathGenerator());
        } else {
            KeyguardEditorMiSightUtil.INSTANCE.reportBackupWallpaperNullType();
            videoWallpaperBackup = null;
        }
        if (videoWallpaperBackup != null) {
            videoWallpaperBackup.backup(z);
        }
    }
}
