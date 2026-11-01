package com.miui.keyguard.editor.utils;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.Log;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.appcompat.widget.VectorEnabledTintResources;
import androidx.appcompat.widget.ViewUtils;
import androidx.emoji2.text.EmojiCompat;
import com.miui.clock.MiuiClockView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.edit.base.CheckBoxStyle;
import com.miui.keyguard.editor.edit.base.TemplateEditFrameController;
import com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView;
import com.miui.keyguard.editor.edit.wallpaper.GLES20ContextFactory;
import com.miui.keyguard.editor.edit.wallpaper.GLTextureView;
import com.miui.keyguard.editor.edit.wallpaper.HierarchyImageView;
import com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperImageView;
import com.miui.keyguard.editor.homepage.view.ClickHotAreaView;
import com.miui.keyguard.editor.homepage.view.TemplatePreviewView;
import com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder;
import com.miui.keyguard.editor.homepage.view.adapter.BaseViewHolder;
import com.miui.keyguard.editor.homepage.view.adapter.FloorAdapter;
import com.miui.keyguard.editor.homepage.view.adapter.FloorViewHolder;
import com.miui.keyguard.editor.homepage.view.adapter.MyFloorViewHolder;
import com.miui.keyguard.editor.homepage.view.adapter.MyTemplateAdapter;
import com.miui.keyguard.editor.homepage.view.adapter.MyTemplateViewHolder;
import com.miui.keyguard.editor.homepage.view.adapter.PreviewFloorViewHolder;
import com.miui.keyguard.editor.homepage.view.adapter.PreviewTemplateAdapter;
import com.miui.keyguard.editor.homepage.view.adapter.TemplateAdapter;
import com.miui.keyguard.editor.homepage.view.adapter.TemplatePreviewViewHolder;
import com.miui.keyguard.editor.homepage.view.generator.BaseItemViewFactoryImpl;
import com.miui.keyguard.editor.homepage.view.transformer.HorizontalScaleTransformer;
import com.miui.keyguard.editor.homepage.view.viewpager.FloorHotspotDelegate;
import com.miui.keyguard.editor.homepage.view.viewpager.HorizontalSpringTouchManager;
import com.miui.keyguard.editor.utils.segment.ImageSegmentCache;
import com.miui.keyguard.editor.view.KgFillVisualCheckBox;
import com.miui.keyguard.editor.view.KgFrame;
import com.miui.keyguard.editor.view.KgVisualCheckBox;
import com.miui.miwallpaper.opengl.ImageGLWallpaper;
import com.miui.miwallpaper.opengl.PreviewGlassAnimRender;
import com.miui.miwallpaper.util.EffectSystemSettingUtils;
import java.lang.reflect.Method;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import miuix.animation.ValueTarget;
import miuix.animation.controller.FolmeHover;
import miuix.visual.check.BorderLayout;
import miuix.visual.check.VisualCheckBox;
import miuix.visual.check.VisualCheckedTextView;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ClassPreLoadHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ClassPreLoadHelperKt {
    public static final Lazy MAIN_FRAGMENT_CLASS$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.utils.ClassPreLoadHelperKt$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return ClassPreLoadHelperKt.MAIN_FRAGMENT_CLASS_delegate$lambda$0();
        }
    });
    public static volatile boolean isPreLoadEditFragmentRelateClassSuccess;
    public static volatile boolean isPreLoadMainFragmentRelateClassSuccess;

    public static final KClass[] MAIN_FRAGMENT_CLASS_delegate$lambda$0() {
        return new KClass[]{Reflection.getOrCreateKotlinClass(FloorAdapter.class), Reflection.getOrCreateKotlinClass(BaseViewHolder.class), Reflection.getOrCreateKotlinClass(FloorViewHolder.class), Reflection.getOrCreateKotlinClass(MyFloorViewHolder.class), Reflection.getOrCreateKotlinClass(HorizontalScaleTransformer.class), Reflection.getOrCreateKotlinClass(TemplateAdapter.class), Reflection.getOrCreateKotlinClass(MyTemplateAdapter.class), Reflection.getOrCreateKotlinClass(HorizontalSpringTouchManager.class), Reflection.getOrCreateKotlinClass(FloorHotspotDelegate.class), Reflection.getOrCreateKotlinClass(BaseItemViewFactoryImpl.class), Reflection.getOrCreateKotlinClass(BaseItemViewHolder.class), Reflection.getOrCreateKotlinClass(MyTemplateViewHolder.class), Reflection.getOrCreateKotlinClass(ClickHotAreaView.class), Reflection.getOrCreateKotlinClass(PreviewFloorViewHolder.class), Reflection.getOrCreateKotlinClass(PreviewTemplateAdapter.class), Reflection.getOrCreateKotlinClass(TemplatePreviewViewHolder.class), Reflection.getOrCreateKotlinClass(TemplatePreviewView.class)};
    }

    public static final KClass[] getMAIN_FRAGMENT_CLASS() {
        return (KClass[]) MAIN_FRAGMENT_CLASS$delegate.getValue();
    }

    public static final void preloadEditFragmentRelateRes(Context context) {
        if (isPreLoadEditFragmentRelateClassSuccess) {
            return;
        }
        try {
            CheckBoxStyle checkBoxStyle = CheckBoxStyle.BORDER;
            int i = VisualCheckBox.$r8$clinit;
            KgVisualCheckBox.Companion companion = KgVisualCheckBox.Companion;
            int i2 = KgFillVisualCheckBox.$r8$clinit;
            int i3 = BorderLayout.$r8$clinit;
            int i4 = FolmeHover.$r8$clinit;
            int i5 = AppCompatTextView.$r8$clinit;
            int[] iArr = VisualCheckedTextView.CHECKED_STATE;
            Class.forName("androidx.appcompat.widget.AppCompatEmojiTextHelper");
            Object obj = EmojiCompat.INSTANCE_LOCK;
            Class.forName("androidx.appcompat.widget.AppCompatTextViewAutoSizeHelper");
            int i6 = ValueTarget.$r8$clinit;
            IWallpaperLayer.Companion companion2 = IWallpaperLayer.Companion;
            CombinedWallpaperView.Companion companion3 = CombinedWallpaperView.Companion;
            int i7 = WallpaperImageView.$r8$clinit;
            boolean z = VectorEnabledTintResources.sCompatVectorFromResourcesEnabled;
            PorterDuff.Mode mode = ResourceManagerInternal.DEFAULT_MODE;
            int i8 = MiuiClockView.$r8$clinit;
            HierarchyImageView.Companion companion4 = HierarchyImageView.Companion;
            int i9 = LinearLayoutCompat.$r8$clinit;
            KgFrame.Companion companion5 = KgFrame.Companion;
            ImageSegmentCache.Companion companion6 = ImageSegmentCache.Companion;
            Class.forName("androidx.emoji2.viewsintegration.EmojiInputFilter");
            Class.forName("androidx.appcompat.widget.AppCompatTextHelper");
            Method method = ViewUtils.sComputeFitSystemWindowsMethod;
            boolean z2 = GLTextureView.DEBUG;
            int i10 = GLES20ContextFactory.EGL_CONTEXT_CLIENT_VERSION;
            int i11 = PreviewGlassAnimRender.$r8$clinit;
            Class.forName("com.miui.keyguard.editor.edit.wallpaper.GLTextureView$GLThread");
            Class.forName("com.miui.keyguard.editor.base.ForwardTransitionConsumer");
            TemplateEditFrameController.Companion companion7 = TemplateEditFrameController.Companion;
            boolean z3 = EffectSystemSettingUtils.IS_FOLD_DEVICE;
            String str = ImageGLWallpaper.TAG;
            isPreLoadEditFragmentRelateClassSuccess = true;
            Log.i("Keyguard-Editor-ClassPreLoadHelper", "preloadEditFragmentRelateClass success");
            if (context != null) {
                context.getDrawable(R.drawable.kg_function_button_icon_wallpaper);
            }
            if (context != null) {
                context.getDrawable(R.drawable.kg_function_button_icon_filter);
            }
            if (context != null) {
                context.getDrawable(R.drawable.kg_function_button_border);
            }
        } catch (Exception e) {
            String message = e.getMessage();
            if (message == null) {
                message = "";
            }
            Log.e("Keyguard-Editor-ClassPreLoadHelper", "preloadEditFragmentRelateClass failure " + message);
        }
    }

    public static final void preloadMainFragmentRelateRes() {
        if (isPreLoadMainFragmentRelateClassSuccess) {
            return;
        }
        Log.d("Keyguard-Editor-ClassPreLoadHelper", "preloadMainFragmentRelateRes: ");
        try {
            Log.d("Keyguard-Editor-ClassPreLoadHelper", "preloadMainFragmentRelateRes: lastCls=" + ((KClass) ArraysKt.last(getMAIN_FRAGMENT_CLASS())));
            isPreLoadMainFragmentRelateClassSuccess = true;
        } catch (Exception e) {
            Log.e("Keyguard-Editor-ClassPreLoadHelper", "preloadMainFragmentRelateRes failure " + e);
        }
    }
}
