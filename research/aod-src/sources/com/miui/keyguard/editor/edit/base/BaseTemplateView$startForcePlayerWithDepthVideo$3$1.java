package com.miui.keyguard.editor.edit.base;

import android.content.Context;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer;
import com.miui.keyguard.editor.edit.wallpaper.VideoTextureView;
import com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
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
/* JADX INFO: compiled from: BaseTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class BaseTemplateView$startForcePlayerWithDepthVideo$3$1 implements View.OnLayoutChangeListener {
    public final /* synthetic */ String $depthVideoPath;
    public final /* synthetic */ boolean $isStartPlayer;
    public final /* synthetic */ View $it;
    public final /* synthetic */ boolean $showForeground;
    public final /* synthetic */ String $tag;
    public final /* synthetic */ BaseTemplateView this$0;

    public BaseTemplateView$startForcePlayerWithDepthVideo$3$1(View view, BaseTemplateView baseTemplateView, String str, boolean z, String str2, boolean z2) {
        this.$it = view;
        this.this$0 = baseTemplateView;
        this.$depthVideoPath = str;
        this.$isStartPlayer = z;
        this.$tag = str2;
        this.$showForeground = z2;
    }

    public static final void onLayoutChange$lambda$3(final BaseTemplateView baseTemplateView, BaseTemplateView$startForcePlayerWithDepthVideo$3$1 baseTemplateView$startForcePlayerWithDepthVideo$3$1, final String str, final boolean z, final String str2, final boolean z2) {
        final Surface videoSurface;
        IWallpaperLayer wallpaperLayer = baseTemplateView.getWallpaperLayer();
        if (wallpaperLayer == null || (videoSurface = wallpaperLayer.getVideoSurface()) == null) {
            Log.e("Keyguard-Editor-BaseTemplateView", "startForcePlayerWithDepthVideo: background wallpaper=" + baseTemplateView.getWallpaperLayer());
            try {
                KeyguardEditorMiSightUtil.reportWallpaperNotDisplayed$default(KeyguardEditorMiSightUtil.INSTANCE, "BaseTemplateView.startForcePlayerWithDepthVideo_bg_surface_null", null, 2, null);
                return;
            } catch (Exception unused) {
                return;
            }
        }
        IWallpaperLayer iWallpaperLayerM733getDepthVideoForegroundLayer = baseTemplateView.m733getDepthVideoForegroundLayer();
        View viewWallpaperLayerView = iWallpaperLayerM733getDepthVideoForegroundLayer != null ? iWallpaperLayerM733getDepthVideoForegroundLayer.wallpaperLayerView() : null;
        final VideoTextureView videoTextureView = viewWallpaperLayerView instanceof VideoTextureView ? (VideoTextureView) viewWallpaperLayerView : null;
        if (videoTextureView == null) {
            Log.e("Keyguard-Editor-BaseTemplateView", "startForcePlayerWithDepthVideo: fgLayer is not VideoTextureView");
            return;
        }
        final Function1 function1 = new Function1() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$startForcePlayerWithDepthVideo$3$1$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return BaseTemplateView$startForcePlayerWithDepthVideo$3$1.onLayoutChange$lambda$3$lambda$1(baseTemplateView, videoSurface, str, z, str2, z2, (Surface) obj);
            }
        };
        if (!videoTextureView.isAvailable()) {
            videoTextureView.setSurfaceCallback(new VideoTextureView.SurfaceCallback() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$startForcePlayerWithDepthVideo$3$1$$ExternalSyntheticLambda2
                @Override // com.miui.keyguard.editor.edit.wallpaper.VideoTextureView.SurfaceCallback
                public final void surfaceCreated(Surface surface) {
                    BaseTemplateView$startForcePlayerWithDepthVideo$3$1.onLayoutChange$lambda$3$lambda$2(videoTextureView, function1, surface);
                }
            });
            return;
        }
        Surface videoSurface2 = videoTextureView.getVideoSurface();
        if (videoSurface2 != null) {
            function1.invoke(videoSurface2);
        } else {
            Log.e("Keyguard-Editor-BaseTemplateView", "startForcePlayerWithDepthVideo: isAvailable but videoSurface=null");
        }
    }

    public static final Unit onLayoutChange$lambda$3$lambda$1(BaseTemplateView baseTemplateView, Surface surface, String str, boolean z, String str2, boolean z2, Surface fgSurface) {
        Intrinsics.checkNotNullParameter(fgSurface, "fgSurface");
        baseTemplateView.setDepthVideoFirstFrameReady$keyguardeditor_release(false);
        FastPlayerManager fastPlayerManager = baseTemplateView.getFastPlayerManager();
        Context context = baseTemplateView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        Intrinsics.checkNotNull(str);
        fastPlayerManager.startDepthVideo(context, fgSurface, surface, str, baseTemplateView.depthVideoFirstFrameCallBack, z, str2);
        baseTemplateView.getFastPlayerManager().setOpenGLSurfaceVisible(z2);
        baseTemplateView.startCheckDepthVideoAvoid();
        return Unit.INSTANCE;
    }

    public static final void onLayoutChange$lambda$3$lambda$2(VideoTextureView videoTextureView, Function1 function1, Surface surface) {
        videoTextureView.setSurfaceCallback(null);
        if (surface == null || !surface.isValid()) {
            Log.e("Keyguard-Editor-BaseTemplateView", "startForcePlayerWithDepthVideo: SurfaceCallback got invalid surface");
        } else {
            function1.invoke(surface);
        }
    }

    @Override // android.view.View.OnLayoutChangeListener
    public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        Log.d("Keyguard-Editor-BaseTemplateView", "startForcePlayerWithDepthVideo: " + view + " layout change");
        this.$it.removeOnLayoutChangeListener(this);
        final BaseTemplateView baseTemplateView = this.this$0;
        final String str = this.$depthVideoPath;
        final boolean z = this.$isStartPlayer;
        final String str2 = this.$tag;
        final boolean z2 = this.$showForeground;
        baseTemplateView.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$startForcePlayerWithDepthVideo$3$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BaseTemplateView$startForcePlayerWithDepthVideo$3$1.onLayoutChange$lambda$3(baseTemplateView, this, str, z, str2, z2);
            }
        });
    }
}
