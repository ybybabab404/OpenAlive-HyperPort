package com.miui.aod.template;

import android.app.WallpaperColors;
import android.content.Context;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.miwallpaper.IMiuiWallpaperManagerCallback;
import java.lang.ref.WeakReference;
import java.util.Map;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
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
/* JADX INFO: compiled from: FullAodTemplateView.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class MiuiWallpaperManagerListener extends IMiuiWallpaperManagerCallback.Stub {
    public WeakReference templateViewRef;

    public MiuiWallpaperManagerListener(FullAodTemplateView templateView) {
        Intrinsics.checkNotNullParameter(templateView, "templateView");
        this.templateViewRef = new WeakReference(templateView);
    }

    @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
    public void onDrawFrameEnd() {
    }

    @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
    public void onPartColorComputeComplete(Map map, Map map2, int i) {
    }

    @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
    public void onWallpaperChanged(WallpaperColors wallpaperColors, String str, int i) {
        FullAodTemplateView fullAodTemplateView;
        WeakReference weakReference = this.templateViewRef;
        if (weakReference == null || (fullAodTemplateView = (FullAodTemplateView) weakReference.get()) == null) {
            return;
        }
        Function0<Unit> wallpaperChangedCallback = fullAodTemplateView.getWallpaperChangedCallback();
        if (wallpaperChangedCallback != null) {
            wallpaperChangedCallback.invoke();
        }
        WallpaperController.Companion companion = WallpaperController.Companion;
        Context context = fullAodTemplateView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        companion.getInstance(context).unregisterWallpaperChangedListener(this);
    }

    @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
    public void onWallpaperFirstFrameRendered(int i) {
    }
}
