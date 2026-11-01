package com.miui.keyguard.editor.data.template;

import android.content.Context;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
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
public final class CurrentWallpaperBackupManager {
    public final void backup(Context context, TemplateFilePathGenerator filePathGenerator) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(filePathGenerator, "filePathGenerator");
        new LockBackupManager(context, filePathGenerator).backup(true);
        new HomeBackupManager(context, filePathGenerator).backup(false);
    }

    public final void restoreHome(Context context, WallpaperInfo homeWallpaperInfo, TemplateFilePathGenerator filePathGenerator) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(homeWallpaperInfo, "homeWallpaperInfo");
        Intrinsics.checkNotNullParameter(filePathGenerator, "filePathGenerator");
        new HomeRestoreManager(context, homeWallpaperInfo, filePathGenerator).restore(false);
    }

    public final void restoreLock(Context context, WallpaperInfo lockWallpaperInfo, TemplateFilePathGenerator filePathGenerator) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(lockWallpaperInfo, "lockWallpaperInfo");
        Intrinsics.checkNotNullParameter(filePathGenerator, "filePathGenerator");
        new LockRestoreManager(context, lockWallpaperInfo, filePathGenerator).restore(true);
    }
}
