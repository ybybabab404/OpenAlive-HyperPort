package com.miui.keyguard.editor.edit.wallpaper;

import android.content.Context;
import com.miui.keyguard.editor.data.bean.WallpaperWhich;
import kotlin.Metadata;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: WallpaperController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public interface IWallpaperController {

    /* JADX INFO: compiled from: WallpaperController.kt */
    @Metadata
    public static final class DefaultImpls {
        public static /* synthetic */ void applySuperWallpaper$default(IWallpaperController iWallpaperController, String str, Context context, boolean z, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: applySuperWallpaper");
            }
            if ((i & 4) != 0) {
                z = false;
            }
            iWallpaperController.applySuperWallpaper(str, context, z);
        }

        public static /* synthetic */ void applyVideoWallpaper$default(IWallpaperController iWallpaperController, String str, Object obj, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, int i2, Boolean bool, int i3, Object obj2) {
            if (obj2 != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: applyVideoWallpaper");
            }
            iWallpaperController.applyVideoWallpaper(str, obj, i, z, z2, (i3 & 32) != 0 ? true : z3, (i3 & 64) != 0 ? false : z4, (i3 & 128) != 0 ? false : z5, i2, bool);
        }

        public static /* synthetic */ String getVideoWallpaperOriginPath$default(IWallpaperController iWallpaperController, WallpaperWhich wallpaperWhich, int i, Object obj) {
            if (obj != null) {
                throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getVideoWallpaperOriginPath");
            }
            if ((i & 1) != 0) {
                wallpaperWhich = WallpaperWhich.LockWhich.INSTANCE;
            }
            return iWallpaperController.getVideoWallpaperOriginPath(wallpaperWhich);
        }
    }

    void applySuperWallpaper(String str, Context context, boolean z);

    void applyVideoWallpaper(String str, Object obj, int i, boolean z, boolean z2, boolean z3, boolean z4, boolean z5, int i2, Boolean bool);

    String getVideoWallpaperOriginPath(WallpaperWhich wallpaperWhich);
}
