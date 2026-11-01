package com.miui.keyguard.editor.edit.base;

import android.graphics.Matrix;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperChangedListener;
import kotlin.Metadata;

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
public final class BaseTemplateView$wallpaperChangedListener$1 implements WallpaperChangedListener {
    public final /* synthetic */ BaseTemplateView this$0;

    public BaseTemplateView$wallpaperChangedListener$1(BaseTemplateView baseTemplateView) {
        this.this$0 = baseTemplateView;
    }

    public static final void onChanged$lambda$1$lambda$0(BaseTemplateView baseTemplateView, Matrix matrix) {
        FollowSystemListener followSystemListener = baseTemplateView.followSystemListener;
        if (followSystemListener != null) {
            followSystemListener.trigger(1, matrix);
        }
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.WallpaperChangedListener
    public void onChanged(Matrix matrix) {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        BaseTemplateView baseTemplateView = this.this$0;
        CommonConfig templateConfig = baseTemplateView.getTemplateConfig();
        final Matrix matrixObtainGlassMatrix = baseTemplateView.obtainGlassMatrix(matrix, (templateConfig == null || (lockscreenInfo = templateConfig.getLockscreenInfo()) == null || (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) == null) ? 0 : wallpaperInfo.getMagicType());
        WallpaperInfo wallpaperInfo2 = this.this$0.getWallpaperInfo();
        Log.d("Keyguard-Editor-BaseTemplateView", "matrix is " + matrix + ",new matrix is " + matrixObtainGlassMatrix + " " + (wallpaperInfo2 != null ? wallpaperInfo2.getInfoType() : null));
        if (matrixObtainGlassMatrix != null) {
            final BaseTemplateView baseTemplateView2 = this.this$0;
            baseTemplateView2.post(new Runnable() { // from class: com.miui.keyguard.editor.edit.base.BaseTemplateView$wallpaperChangedListener$1$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    BaseTemplateView$wallpaperChangedListener$1.onChanged$lambda$1$lambda$0(baseTemplateView2, matrixObtainGlassMatrix);
                }
            });
        }
    }
}
