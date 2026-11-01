package com.miui.keyguard.editor.edit.base;

import android.content.Context;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer;
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
/* JADX INFO: compiled from: BaseTemplateView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class BaseTemplateView$startForcePlayerWithNormalVideo$2$1 implements View.OnLayoutChangeListener {
    public final /* synthetic */ boolean $isStartPlayer;
    public final /* synthetic */ View $it;
    public final /* synthetic */ String $resourcePath;
    public final /* synthetic */ String $tag;
    public final /* synthetic */ BaseTemplateView this$0;

    public BaseTemplateView$startForcePlayerWithNormalVideo$2$1(View view, BaseTemplateView baseTemplateView, String str, boolean z, String str2) {
        this.$it = view;
        this.this$0 = baseTemplateView;
        this.$resourcePath = str;
        this.$isStartPlayer = z;
        this.$tag = str2;
    }

    public static final void onLayoutChange$lambda$1(BaseTemplateView baseTemplateView, BaseTemplateView$startForcePlayerWithNormalVideo$2$1 baseTemplateView$startForcePlayerWithNormalVideo$2$1, String str, boolean z, String str2) {
        Surface videoSurface;
        IWallpaperLayer wallpaperLayer = baseTemplateView.getWallpaperLayer();
        if (wallpaperLayer != null && (videoSurface = wallpaperLayer.getVideoSurface()) != null) {
            FastPlayerManager fastPlayerManager = baseTemplateView.getFastPlayerManager();
            Context context = baseTemplateView.getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            Intrinsics.checkNotNull(str);
            fastPlayerManager.startNormalVideo(context, videoSurface, str, baseTemplateView.normalVideoFirstFrame, z, str2);
            return;
        }
        Log.e("Keyguard-Editor-BaseTemplateView", "startForcePlayerWithNormalVideo: normal wallpaper=" + baseTemplateView.getWallpaperLayer());
        try {
            KeyguardEditorMiSightUtil.reportWallpaperNotDisplayed$default(KeyguardEditorMiSightUtil.INSTANCE, "BaseTemplateView.startForcePlayerWithNormalVideo_surface_null", null, 2, null);
        } catch (Exception unused) {
        }
    }

    @Override // android.view.View.OnLayoutChangeListener
    public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        Log.d("Keyguard-Editor-BaseTemplateView", "startForcePlayerWithNormalVideo: " + view + " layout change");
        this.$it.removeOnLayoutChangeListener(this);
        final BaseTemplateView baseTemplateView = this.this$0;
        final String str = this.$resourcePath;
        final boolean z = this.$isStartPlayer;
        final String str2 = this.$tag;
        baseTemplateView.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$startForcePlayerWithNormalVideo$2$1$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BaseTemplateView$startForcePlayerWithNormalVideo$2$1.onLayoutChange$lambda$1(baseTemplateView, this, str, z, str2);
            }
        });
    }
}
