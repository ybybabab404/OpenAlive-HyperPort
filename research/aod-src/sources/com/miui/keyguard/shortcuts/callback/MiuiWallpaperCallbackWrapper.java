package com.miui.keyguard.shortcuts.callback;

import android.app.WallpaperColors;
import com.miui.miwallpaper.IMiuiWallpaperManagerCallback;
import java.util.Map;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: MiuiWallpaperCallbackWrapper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class MiuiWallpaperCallbackWrapper extends IMiuiWallpaperManagerCallback.Stub {
    public WallpaperCallback weakChangeCallback;

    /* JADX INFO: compiled from: MiuiWallpaperCallbackWrapper.kt */
    @Metadata
    public interface WallpaperCallback {

        /* JADX INFO: compiled from: MiuiWallpaperCallbackWrapper.kt */
        @Metadata
        public static final class DefaultImpls {
        }

        void onWallpaperChanged(WallpaperColors wallpaperColors, String str, int i);
    }

    @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
    public void onDrawFrameEnd() {
    }

    @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
    public void onPartColorComputeComplete(Map map, Map map2, int i) {
    }

    @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
    public void onWallpaperChanged(WallpaperColors wallpaperColors, String type, int i) {
        Intrinsics.checkNotNullParameter(wallpaperColors, "wallpaperColors");
        Intrinsics.checkNotNullParameter(type, "type");
        WallpaperCallback wallpaperCallback = this.weakChangeCallback;
        if (wallpaperCallback != null) {
            wallpaperCallback.onWallpaperChanged(wallpaperColors, type, i);
        }
    }

    @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
    public void onWallpaperFirstFrameRendered(int i) {
    }

    public final void setCallback(WallpaperCallback wallpaperCallback) {
        this.weakChangeCallback = wallpaperCallback;
    }
}
