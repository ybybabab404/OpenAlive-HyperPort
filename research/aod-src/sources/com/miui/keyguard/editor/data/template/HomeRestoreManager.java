package com.miui.keyguard.editor.data.template;

import android.content.Context;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
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
public final class HomeRestoreManager extends BaseWallpaperRestore {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HomeRestoreManager(Context context, WallpaperInfo wallpaperInfo, TemplateFilePathGenerator filePathGenerator) {
        super(context, wallpaperInfo, filePathGenerator);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(wallpaperInfo, "wallpaperInfo");
        Intrinsics.checkNotNullParameter(filePathGenerator, "filePathGenerator");
    }

    public boolean restore(boolean z) {
        Log.w("Keyguard-Editor-BackupManager", "HomeRestoreManager::restore: ");
        if (new VideoWallpaperRestore(getContext(), getWallpaperInfo(), getFilePathGenerator()).restore(z) || new ImageWallpaperRestore(getContext(), getWallpaperInfo(), getFilePathGenerator()).restore(z)) {
            return true;
        }
        KeyguardEditorMiSightUtil.INSTANCE.reportWallpaperRestoreFail("home", "video,image", "all home restore strategies failed");
        return false;
    }
}
