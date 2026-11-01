package com.miui.keyguard.editor.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.HardwareRenderer;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.RenderNode;
import android.os.Looper;
import android.os.Parcelable;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewRootImpl;
import android.view.Window;
import android.widget.FrameLayout;
import androidx.core.content.ContextCompat;
import androidx.core.util.Consumer;
import androidx.lifecycle.LifecycleCoroutineScope;
import androidx.lifecycle.LifecycleOwnerKt;
import androidx.lifecycle.ViewModelKt;
import com.miui.keyguard.editor.EditorActivity;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.edit.base.BaseTemplateView;
import com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView;
import com.miui.keyguard.editor.edit.wallpaper.GLTextureView;
import com.miui.keyguard.editor.edit.wallpaper.HierarchyImageView;
import com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer;
import com.miui.keyguard.editor.edit.wallpaper.VideoTextureView;
import com.miui.keyguard.editor.view.RoundOutlineProvider;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;
import miuix.appcompat.app.AlertDialog;
import miuix.appcompat.app.AppCompatActivity;
import miuix.core.util.HyperMaterialUtils;
import miuix.core.util.MaterialConfig;
import miuix.core.util.MaterialDayNightConfig;
import miuix.core.util.MiuiBlurUtils;
import miuix.core.util.MiuixUIUtils;
import miuix.internal.util.ReflectUtil;
import miuix.theme.token.BloomStrokeToken;
import miuix.theme.token.ColorBlendToken;
import miuix.theme.token.MaterialDayNightToken;
import miuix.theme.token.MaterialToken;

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
/* JADX INFO: compiled from: ViewUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class ViewUtil {
    public static final ViewUtil INSTANCE = new ViewUtil();
    public static final Map actionMap;
    public static Integer backgroundColorForLite;
    public static final MaterialToken customMaterialDark;
    public static final MaterialToken customMaterialLight;
    public static Float defaultBlurRadius;
    public static MaterialToken frostedPuredMaterial;
    public static MaterialToken glassStrokeMaterial;

    /* JADX INFO: renamed from: com.miui.keyguard.editor.utils.ViewUtil$invokeOnScreenshotCaptured$1, reason: invalid class name */
    /* JADX INFO: compiled from: ViewUtil.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.utils.ViewUtil", f = "ViewUtil.kt", l = {300, 313, 340}, m = "invokeOnScreenshotCaptured")
    public static final class AnonymousClass1 extends ContinuationImpl {
        public int I$0;
        public Object L$0;
        public Object L$1;
        public Object L$2;
        public int label;
        public /* synthetic */ Object result;

        public AnonymousClass1(Continuation continuation) {
            super(continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            this.result = obj;
            this.label |= Integer.MIN_VALUE;
            return ViewUtil.this.invokeOnScreenshotCaptured(null, null, null, null, this);
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.utils.ViewUtil$invokeOnTextureUpdated$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ViewUtil.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.utils.ViewUtil$invokeOnTextureUpdated$1", f = "ViewUtil.kt", l = {251}, m = "invokeSuspend")
    public static final class C01441 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Function4 $onScreenshotCaptured;
        public final /* synthetic */ BaseTemplateView $templateView;
        public final /* synthetic */ BaseTemplateView $this_invokeOnTextureUpdated;
        public final /* synthetic */ TextureView $wallpaperLayerView;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01441(TextureView textureView, BaseTemplateView baseTemplateView, BaseTemplateView baseTemplateView2, Function4 function4, Continuation continuation) {
            super(2, continuation);
            this.$wallpaperLayerView = textureView;
            this.$this_invokeOnTextureUpdated = baseTemplateView;
            this.$templateView = baseTemplateView2;
            this.$onScreenshotCaptured = function4;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01441(this.$wallpaperLayerView, this.$this_invokeOnTextureUpdated, this.$templateView, this.$onScreenshotCaptured, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01441) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Bitmap bitmapCreateBitmap$default;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                try {
                    bitmapCreateBitmap$default = BitmapCreator.createBitmap$default(BitmapCreator.INSTANCE, this.$wallpaperLayerView.getWidth(), this.$wallpaperLayerView.getHeight(), (this.$this_invokeOnTextureUpdated.getEditFragment() != null || this.$this_invokeOnTextureUpdated.isEditTransition()) ? Bitmap.Config.RGB_565 : Bitmap.Config.ARGB_8888, false, false, 24, null);
                    this.$wallpaperLayerView.getBitmap(bitmapCreateBitmap$default);
                } catch (Exception e) {
                    Log.e("ViewUtil", "wallpaperLayerView.bitmap error", e);
                    bitmapCreateBitmap$default = null;
                }
                Bitmap bitmap = bitmapCreateBitmap$default;
                ViewUtil viewUtil = ViewUtil.INSTANCE;
                BaseTemplateView baseTemplateView = this.$templateView;
                Function4 function4 = this.$onScreenshotCaptured;
                this.label = 1;
                if (ViewUtil.invokeOnScreenshotCaptured$default(viewUtil, bitmap, baseTemplateView, function4, null, this, 8, null) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.utils.ViewUtil$runOnUIThread$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ViewUtil.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.utils.ViewUtil$runOnUIThread$1", f = "ViewUtil.kt", l = {}, m = "invokeSuspend")
    public static final class C01451 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Runnable $runnable;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01451(Runnable runnable, Continuation continuation) {
            super(2, continuation);
            this.$runnable = runnable;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01451(this.$runnable, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01451) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label != 0) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            ResultKt.throwOnFailure(obj);
            this.$runnable.run();
            return Unit.INSTANCE;
        }
    }

    /* JADX INFO: renamed from: com.miui.keyguard.editor.utils.ViewUtil$takeScreenshot$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: ViewUtil.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.utils.ViewUtil$takeScreenshot$1", f = "ViewUtil.kt", l = {196}, m = "invokeSuspend")
    public static final class C01461 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public final /* synthetic */ Function4 $onScreenshotCaptured;
        public final /* synthetic */ BaseTemplateView $this_takeScreenshot;
        public int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C01461(BaseTemplateView baseTemplateView, Function4 function4, Continuation continuation) {
            super(2, continuation);
            this.$this_takeScreenshot = baseTemplateView;
            this.$onScreenshotCaptured = function4;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C01461(this.$this_takeScreenshot, this.$onScreenshotCaptured, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((C01461) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            BaseTemplateView baseTemplateView;
            CombinedWallpaperView combinedWallpaperView;
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                ViewUtil viewUtil = ViewUtil.INSTANCE;
                Bitmap bitmapDrawToHardwareBitmap = viewUtil.drawToHardwareBitmap(this.$this_takeScreenshot);
                WeakReference currentHomeTemplateWeak = DeviceScreenshotHelper.INSTANCE.getCurrentHomeTemplateWeak();
                Bitmap bitmapDrawToHardwareBitmap2 = null;
                View viewWallpaperLayerView = (currentHomeTemplateWeak == null || (baseTemplateView = (BaseTemplateView) currentHomeTemplateWeak.get()) == null || (combinedWallpaperView = (CombinedWallpaperView) baseTemplateView.getWallpaperLayer()) == null) ? null : combinedWallpaperView.wallpaperLayerView();
                GLTextureView gLTextureView = viewWallpaperLayerView instanceof GLTextureView ? (GLTextureView) viewWallpaperLayerView : null;
                Log.i("ViewUtil", "takeScreenshot: create new wallpaperScreenshot homeWallpaperView=" + gLTextureView);
                if (gLTextureView == null || gLTextureView.getWidth() <= 0 || gLTextureView.getHeight() <= 0) {
                    CombinedWallpaperView combinedWallpaperView2 = (CombinedWallpaperView) this.$this_takeScreenshot.getWallpaperLayer();
                    if (combinedWallpaperView2 != null) {
                        bitmapDrawToHardwareBitmap2 = viewUtil.drawToHardwareBitmap(combinedWallpaperView2);
                    }
                } else {
                    bitmapDrawToHardwareBitmap2 = BitmapCreator.createBitmap$default(BitmapCreator.INSTANCE, gLTextureView.getWidth(), gLTextureView.getHeight(), Bitmap.Config.ARGB_8888, false, false, 24, null);
                    gLTextureView.getBitmap(bitmapDrawToHardwareBitmap2);
                }
                Function4 function4 = this.$onScreenshotCaptured;
                Integer numBoxInt = Boxing.boxInt((bitmapDrawToHardwareBitmap == null || bitmapDrawToHardwareBitmap2 == null) ? 3 : 0);
                this.label = 1;
                if (function4.invoke(bitmapDrawToHardwareBitmap, numBoxInt, bitmapDrawToHardwareBitmap2, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    static {
        MaterialToken materialTokenBuild = new MaterialToken.Builder(30, "button-bloom", "light").setColorBlend(ColorBlendToken.Colored_Extra_Thin_Light).setBloomStroke(BloomStrokeToken.Glass_Stroke_Small_Light).build();
        Intrinsics.checkNotNullExpressionValue(materialTokenBuild, "build(...)");
        glassStrokeMaterial = materialTokenBuild;
        MaterialToken materialTokenBuild2 = new MaterialToken.Builder(30, "button-bloom", "light").setColorBlend(ColorBlendToken.Colored_Extra_Thin_Light).build();
        Intrinsics.checkNotNullExpressionValue(materialTokenBuild2, "build(...)");
        frostedPuredMaterial = materialTokenBuild2;
        customMaterialLight = new MaterialToken.Builder(10, "custom-material", "light").setColorBlend(ColorBlendToken.Pured_Thick_Light).setElementBlur(100).setBloomStroke(BloomStrokeToken.Glass_Stroke_Big_Light).build();
        customMaterialDark = new MaterialToken.Builder(10, "custom-material", "dark").setColorBlend(ColorBlendToken.Pured_Thick_Dark).setElementBlur(100).setBloomStroke(BloomStrokeToken.Glass_Stroke_Big_Dark).build();
        actionMap = MapsKt.mapOf(TuplesKt.to(0, "ACTION_DOWN"), TuplesKt.to(1, "ACTION_UP"), TuplesKt.to(2, "ACTION_MOVE"), TuplesKt.to(3, "ACTION_CANCEL"), TuplesKt.to(4, "ACTION_OUTSIDE"), TuplesKt.to(5, "ACTION_POINTER_DOWN"), TuplesKt.to(6, "ACTION_POINTER_UP"), TuplesKt.to(7, "ACTION_HOVER_MOVE"), TuplesKt.to(8, "ACTION_SCROLL"), TuplesKt.to(9, "ACTION_HOVER_ENTER"), TuplesKt.to(10, "ACTION_HOVER_EXIT"), TuplesKt.to(11, "ACTION_BUTTON_PRESS"), TuplesKt.to(12, "ACTION_BUTTON_RELEASE"), TuplesKt.to(65280, "ACTION_POINTER_INDEX_MASK"), TuplesKt.to(8, "ACTION_POINTER_INDEX_SHIFT"), TuplesKt.to(5, "ACTION_POINTER_1_DOWN"), TuplesKt.to(261, "ACTION_POINTER_2_DOWN"), TuplesKt.to(517, "ACTION_POINTER_3_DOWN"), TuplesKt.to(6, "ACTION_POINTER_1_UP"), TuplesKt.to(262, "ACTION_POINTER_2_UP"), TuplesKt.to(518, "ACTION_POINTER_3_UP"), TuplesKt.to(65280, "ACTION_POINTER_ID_MASK"), TuplesKt.to(8, "ACTION_POINTER_ID_SHIFT"));
    }

    public static final boolean applyPressedState$lambda$15(ITouchStyle iTouchStyle, View view, MotionEvent motionEvent) {
        iTouchStyle.onMotionEvent(motionEvent);
        return false;
    }

    public static /* synthetic */ void decorateAutoBottomPadBackground$default(ViewUtil viewUtil, View view, View view2, float f, BlendColorInfo[] blendColorInfoArr, int i, boolean z, boolean z2, float f2, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            Integer num = backgroundColorForLite;
            if (num != null) {
                i = num.intValue();
            } else {
                i = view.getContext().getColor(R.color.kg_pure_background_color_for_lite);
                backgroundColorForLite = Integer.valueOf(i);
            }
        }
        viewUtil.decorateAutoBottomPadBackground(view, view2, f, blendColorInfoArr, i, (i2 & 16) != 0 ? true : z, (i2 & 32) != 0 ? true : z2, (i2 & 64) != 0 ? f : f2);
    }

    public static /* synthetic */ void decorateAutoBottomPadTopRoundBackground$default(ViewUtil viewUtil, View view, View view2, float f, BlendColorInfo[] blendColorInfoArr, int i, boolean z, boolean z2, float f2, int i2, Object obj) {
        if ((i2 & 8) != 0) {
            Integer num = backgroundColorForLite;
            if (num != null) {
                i = num.intValue();
            } else {
                i = view.getContext().getColor(R.color.kg_pure_background_color_for_lite);
                backgroundColorForLite = Integer.valueOf(i);
            }
        }
        viewUtil.decorateAutoBottomPadTopRoundBackground(view, view2, f, blendColorInfoArr, i, (i2 & 16) != 0 ? true : z, (i2 & 32) != 0 ? true : z2, (i2 & 64) != 0 ? f : f2);
    }

    public static /* synthetic */ void decorateBackground$default(ViewUtil viewUtil, View view, float f, BlendColorInfo[] blendColorInfoArr, int i, boolean z, float f2, int i2, Object obj) {
        if ((i2 & 4) != 0) {
            Integer num = backgroundColorForLite;
            if (num != null) {
                i = num.intValue();
            } else {
                i = view.getContext().getColor(R.color.kg_pure_background_color_for_lite);
                backgroundColorForLite = Integer.valueOf(i);
            }
        }
        int i3 = i;
        if ((i2 & 8) != 0) {
            z = true;
        }
        viewUtil.decorateBackground(view, f, blendColorInfoArr, i3, z, (i2 & 16) != 0 ? f : f2);
    }

    public static /* synthetic */ void decorateBackgroundByDefault$default(ViewUtil viewUtil, View view, int i, int i2, int i3, Integer num, boolean z, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i2 = R.color.kg_function_button_blend_color1;
        }
        int i5 = i2;
        if ((i4 & 4) != 0) {
            i3 = R.color.kg_function_button_blend_color2;
        }
        int i6 = i3;
        if ((i4 & 8) != 0) {
            num = null;
        }
        Integer num2 = num;
        if ((i4 & 16) != 0) {
            z = true;
        }
        viewUtil.decorateBackgroundByDefault(view, i, i5, i6, num2, z);
    }

    public static /* synthetic */ void decorateFrostedPured$default(ViewUtil viewUtil, View view, float f, boolean z, int i, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            z = true;
        }
        if ((i2 & 4) != 0) {
            Integer num = backgroundColorForLite;
            if (num != null) {
                i = num.intValue();
            } else {
                i = view.getContext().getColor(R.color.kg_pure_background_color_for_lite);
                backgroundColorForLite = Integer.valueOf(i);
            }
        }
        viewUtil.decorateFrostedPured(view, f, z, i);
    }

    public static final void doIfNotNull(Object obj, Consumer consumer) {
        Intrinsics.checkNotNullParameter(consumer, "consumer");
        if (obj != null) {
            consumer.accept(obj);
        }
    }

    public static /* synthetic */ void getShowRect$default(ViewUtil viewUtil, View view, RectF rectF, float f, float f2, int i, Object obj) {
        if ((i & 2) != 0) {
            f = view.getScaleX();
        }
        if ((i & 4) != 0) {
            f2 = view.getScaleY();
        }
        viewUtil.getShowRect(view, rectF, f, f2);
    }

    public static /* synthetic */ Object invokeOnScreenshotCaptured$default(ViewUtil viewUtil, Bitmap bitmap, BaseTemplateView baseTemplateView, Function4 function4, Integer num, Continuation continuation, int i, Object obj) {
        if ((i & 8) != 0) {
            num = null;
        }
        return viewUtil.invokeOnScreenshotCaptured(bitmap, baseTemplateView, function4, num, continuation);
    }

    public static /* synthetic */ void setBackgroundBlurModeWhenBlurEnabled$default(ViewUtil viewUtil, View view, float f, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            Float f2 = defaultBlurRadius;
            if (f2 != null) {
                f = f2.floatValue();
            } else {
                f = DensityUtils.dp2Px(60);
                defaultBlurRadius = Float.valueOf(f);
            }
        }
        if ((i2 & 2) != 0) {
            i = BlurMode.MODE1.getMode();
        }
        viewUtil.setBackgroundBlurModeWhenBlurEnabled(view, f, i);
    }

    public static final void setMarginBottom(View view, int i) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = i;
            view.setLayoutParams(layoutParams);
        }
    }

    public static final void setMarginEnd(View view, int i) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) layoutParams).setMarginEnd(i);
            view.setLayoutParams(layoutParams);
        }
    }

    public static final void setMarginTop(View view, int i) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = i;
            view.setLayoutParams(layoutParams);
        }
    }

    public static final void setMargins(View view, int i, int i2, int i3, int i4) {
        if (view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ((ViewGroup.MarginLayoutParams) layoutParams).setMargins(i, i2, i3, i4);
                view.setLayoutParams(layoutParams);
            }
        }
    }

    public static /* synthetic */ void setThrottledClickListener$default(ViewUtil viewUtil, View view, View.OnClickListener onClickListener, long j, int i, Object obj) {
        if ((i & 2) != 0) {
            j = 500;
        }
        viewUtil.setThrottledClickListener(view, onClickListener, j);
    }

    public static final void setThrottledClickListener$lambda$4(Ref.LongRef longRef, long j, View.OnClickListener onClickListener, View view) {
        if (longRef.element != 0 && SystemClock.uptimeMillis() - longRef.element <= j) {
            Log.d("ViewUtil", "click gap is too short");
        } else {
            longRef.element = SystemClock.uptimeMillis();
            onClickListener.onClick(view);
        }
    }

    public static final void setVisibility(View view, int i) {
        if (view != null) {
            view.setVisibility(i);
        }
    }

    public static final void takeScreenshot$lambda$0(BaseTemplateView baseTemplateView, GLTextureView gLTextureView, Function4 function4) {
        Log.i("ViewUtil", "onSurfaceTextureUpdated");
        INSTANCE.invokeOnTextureUpdated(baseTemplateView, gLTextureView, function4);
    }

    public static final void takeScreenshot$lambda$1(BaseTemplateView baseTemplateView, GLTextureView gLTextureView, Function4 function4) {
        Log.i("ViewUtil", "onSurfaceTextureUpdated for screenshot");
        INSTANCE.invokeOnTextureUpdated(baseTemplateView, gLTextureView, function4);
    }

    public final ITouchStyle applyPressedState(View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        final ITouchStyle scale = Folme.useAt(view).touch().setTint(0.08f, 1.0f, 1.0f, 1.0f).setScale(0.9f, new ITouchStyle.TouchType[0]);
        view.setOnTouchListener(new View.OnTouchListener() { // from class: com.miui.keyguard.editor.utils.ViewUtil$$ExternalSyntheticLambda1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return ViewUtil.applyPressedState$lambda$15(scale, view2, motionEvent);
            }
        });
        Intrinsics.checkNotNull(scale);
        return scale;
    }

    public final Object checkHierarchyThenTakeScreenshot(final BaseTemplateView baseTemplateView, final Screenshots screenshots, final boolean z, final Function4 function4, Continuation continuation) {
        baseTemplateView.editingTemplateActionBeforeTakeScreenshot();
        HierarchyImageView hierarchyImageView = (HierarchyImageView) baseTemplateView.getHierarchyLayer();
        HierarchyImageAvoidController avoidController = hierarchyImageView != null ? hierarchyImageView.getAvoidController() : null;
        if (avoidController != null) {
            Object objStartScreenshotCheck = avoidController.startScreenshotCheck(new OnScreenshotTemplateCheckListener() { // from class: com.miui.keyguard.editor.utils.ViewUtil.checkHierarchyThenTakeScreenshot.2
                @Override // com.miui.keyguard.editor.utils.OnScreenshotTemplateCheckListener
                public Object onHierarchyCheckEnd(Map map, Continuation continuation2) {
                    HierarchyImageAvoidController avoidController2;
                    Log.i("ViewUtil", "screenshot template hierarchy check done " + map + " skipScreenshot=" + z);
                    Screenshots screenshots2 = screenshots;
                    for (Map.Entry entry : map.entrySet()) {
                        screenshots2.getHierarchyCheckResult().put((String) entry.getKey(), Boxing.boxBoolean(((Boolean) entry.getValue()).booleanValue()));
                    }
                    Screenshots screenshots3 = screenshots;
                    Context context = baseTemplateView.getContext();
                    if (!screenshots3.getScreenshotAvoidResult(context instanceof DeviceScreenshotHelper.ScreenshotContext ? (DeviceScreenshotHelper.ScreenshotContext) context : null, baseTemplateView.isDualClock())) {
                        baseTemplateView.onEdited(40, null);
                        HierarchyImageView hierarchyImageView2 = (HierarchyImageView) baseTemplateView.getHierarchyLayer();
                        if (hierarchyImageView2 != null && (avoidController2 = hierarchyImageView2.getAvoidController()) != null) {
                            avoidController2.setUserOpenHierarchy(false);
                        }
                    }
                    if (z) {
                        Object objInvoke = function4.invoke(null, Boxing.boxInt(6), null, continuation2);
                        return objInvoke == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objInvoke : Unit.INSTANCE;
                    }
                    ViewUtil.INSTANCE.takeScreenshot(baseTemplateView, function4);
                    return Unit.INSTANCE;
                }
            }, continuation);
            return objStartScreenshotCheck == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objStartScreenshotCheck : Unit.INSTANCE;
        }
        Log.i("ViewUtil", "checkHierarchyThenTakeScreenshot direct invoke takeScreenshot skipScreenshot=" + z);
        if (z) {
            Object objInvoke = function4.invoke(null, Boxing.boxInt(6), null, continuation);
            return objInvoke == IntrinsicsKt.getCOROUTINE_SUSPENDED() ? objInvoke : Unit.INSTANCE;
        }
        takeScreenshot(baseTemplateView, function4);
        return Unit.INSTANCE;
    }

    public final void clearBackgroundBlurModeWhenBlurEnabled(View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        if (ViewStylerKt.canUseBlur()) {
            ViewStylerKt.clearBlurMode(view);
        }
    }

    public final Bitmap createHardwareBitmap(RenderNode renderNode, int i, int i2) {
        Class cls = Integer.TYPE;
        return (Bitmap) ReflectUtil.callStaticObjectMethod(HardwareRenderer.class, Bitmap.class, "createHardwareBitmap", new Class[]{RenderNode.class, cls, cls}, renderNode, Integer.valueOf(i), Integer.valueOf(i2));
    }

    public final void decorateAutoBottomPadBackground(View view, View view2, float f, BlendColorInfo[] blendColors, int i, boolean z, boolean z2, float f2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(view2, "view");
        Intrinsics.checkNotNullParameter(blendColors, "blendColors");
        if (f > 0.0f) {
            view.setClipToOutline(true);
            view.setOutlineProvider(new RoundOutlineProvider(f));
        }
        decorateAutoBottomPadMaterialBackground(view, view2, blendColors, z, z2, f2);
        if (z2 && ViewStylerKt.canUseBlur()) {
            return;
        }
        view.setBackground(DrawablesKt.createRoundDrawable(f, i));
    }

    public final void decorateAutoBottomPadMaterialBackground(View view, View view2, BlendColorInfo[] blendColorInfoArr, boolean z, boolean z2, float f) {
        boolean backgroundBlur;
        MiuiBlurUtils.clearBackgroundBlur(view2);
        if (z2 && ViewStylerKt.canUseBlur()) {
            MaterialDayNightConfig materialDayNightConfigCreate = MaterialDayNightConfig.create(new MaterialDayNightToken(customMaterialLight, customMaterialDark));
            MaterialConfig.BlurConfig blurConfig = materialDayNightConfigCreate != null ? materialDayNightConfigCreate.getBlurConfig(z) : null;
            if (blurConfig != null) {
                backgroundBlur = MiuiBlurUtils.setBackgroundBlur(view2, MiuixUIUtils.dp2px(view2.getContext(), blurConfig.blurRadius));
                Log.d("ViewUtil", "HyperMaterialUtils applyContainer result = " + backgroundBlur);
            } else {
                backgroundBlur = false;
            }
            if (backgroundBlur) {
                HyperMaterialUtils.applyElement(view2, materialDayNightConfigCreate != null ? materialDayNightConfigCreate.get(z) : null);
                return;
            }
            MiuiBlurUtils.clearBackgroundBlur(view2);
            ViewStylerKt.setBackgroundBlurMode$default(view, f, 0, 2, null);
            ViewStylerKt.blurAndBlendBackground(view, blendColorInfoArr);
        }
    }

    public final void decorateAutoBottomPadTopRoundBackground(View view, View view2, float f, BlendColorInfo[] blendColors, int i, boolean z, boolean z2, float f2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(view2, "view");
        Intrinsics.checkNotNullParameter(blendColors, "blendColors");
        if (f > 0.0f) {
            view.setClipToOutline(true);
            view.setOutlineProvider(new RoundOutlineProvider(new float[]{f, f, f, f, 0.0f, 0.0f, 0.0f, 0.0f}));
        }
        decorateAutoBottomPadMaterialBackground(view, view2, blendColors, z, z2, f2);
        if (z2 && ViewStylerKt.canUseBlur()) {
            return;
        }
        view.setBackground(DrawablesKt.createTopRoundDrawable(f, i));
    }

    public final void decorateBackground(View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        MiuiBlurUtils.setBackgroundOnlyBlur(view, (int) ((74 * view.getResources().getDisplayMetrics().density) + 0.5f));
    }

    public final void decorateBackground(View view, float f, BlendColorInfo[] blendColors, int i, boolean z, float f2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(blendColors, "blendColors");
        if (f > 0.0f) {
            view.setClipToOutline(true);
            view.setOutlineProvider(new RoundOutlineProvider(f));
        }
        if (!z || !ViewStylerKt.canUseBlur()) {
            view.setBackground(DrawablesKt.createRoundDrawable(f, i));
        } else {
            ViewStylerKt.setBackgroundBlurMode$default(view, f2, 0, 2, null);
            ViewStylerKt.blurAndBlendBackground(view, blendColors);
        }
    }

    public final void decorateBackgroundByDefault(View view, int i, int i2, int i3, Integer num, boolean z) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        float dimension = i != 0 ? view.getResources().getDimension(i) : 0.0f;
        int color = ContextCompat.getColor(view.getContext(), i2);
        int color2 = ContextCompat.getColor(view.getContext(), i3);
        if (num == null) {
            decorateBackground$default(this, view, dimension, new BlendColorInfo[]{new BlendColorInfo(color, BlendMode.SRC_OVER), new BlendColorInfo(color2, BlendMode.COLOR_BURN)}, 0, z, 0.0f, 20, null);
        } else {
            decorateBackground$default(this, view, dimension, new BlendColorInfo[]{new BlendColorInfo(color, BlendMode.SRC_OVER), new BlendColorInfo(color2, BlendMode.COLOR_BURN)}, num.intValue(), z, 0.0f, 16, null);
        }
    }

    public final boolean decorateCustomButton(View view, boolean z, boolean z2) {
        boolean backgroundBlur;
        Intrinsics.checkNotNullParameter(view, "view");
        MiuiBlurUtils.clearBackgroundBlur(view);
        if (!z) {
            if (z2) {
                view.setBackgroundResource(R.drawable.kg_settings_background_customize_light);
                return true;
            }
            view.setBackgroundResource(R.drawable.kg_settings_background_customize_dark);
            return true;
        }
        MaterialDayNightConfig materialDayNightConfigCreate = MaterialDayNightConfig.create(new MaterialDayNightToken(new MaterialToken.Builder(10, "custom-material", "light").setColorBlend(ColorBlendToken.Colored_Extra_Thin_Light).setElementBlur(40).build(), new MaterialToken.Builder(10, "custom-material", "dark").setColorBlend(ColorBlendToken.Colored_Thin_Dark).setElementBlur(40).build()));
        view.setBackgroundResource(R.drawable.kg_settings_background_customize_button_constrain);
        MaterialConfig.BlurConfig blurConfig = materialDayNightConfigCreate != null ? materialDayNightConfigCreate.getBlurConfig(z2) : null;
        if (blurConfig != null) {
            backgroundBlur = MiuiBlurUtils.setBackgroundBlur(view, MiuixUIUtils.dp2px(view.getContext(), blurConfig.blurRadius));
            Log.d("ViewUtil", "decorateCustomButton applyContainer result = " + backgroundBlur);
        } else {
            backgroundBlur = false;
        }
        if (backgroundBlur) {
            HyperMaterialUtils.applyElement(view, materialDayNightConfigCreate != null ? materialDayNightConfigCreate.get(z2) : null);
            return true;
        }
        MiuiBlurUtils.clearBackgroundBlur(view);
        view.setBackgroundResource(z2 ? R.drawable.kg_settings_background_customize_light : R.drawable.kg_settings_background_customize_dark);
        return false;
    }

    public final void decorateFrostedPured(View view, float f, boolean z, int i) {
        MaterialConfig.ColorBlendConfig colorBlendConfig;
        int[] iArr;
        Intrinsics.checkNotNullParameter(view, "<this>");
        if (f > 0.0f) {
            view.setClipToOutline(true);
            view.setOutlineProvider(new RoundOutlineProvider(f));
        }
        if (!z || !ViewStylerKt.canUseBlur()) {
            view.setBackground(DrawablesKt.createRoundDrawable(f, i));
            return;
        }
        MaterialConfig materialConfigCreate = MaterialConfig.create(frostedPuredMaterial);
        MiuiBlurUtils.setViewBlurMode(view, 1);
        if (materialConfigCreate != null && (colorBlendConfig = materialConfigCreate.getColorBlendConfig()) != null && (iArr = colorBlendConfig.blendColors) != null) {
            MaterialConfig.ColorBlendConfig colorBlendConfig2 = materialConfigCreate.getColorBlendConfig();
            Intrinsics.checkNotNull(colorBlendConfig2);
            MiuiBlurUtils.setBackgroundBlendConfig(view, iArr, colorBlendConfig2.blendModes);
        }
        HyperMaterialUtils.applyViewMaterial(view, materialConfigCreate);
    }

    public final Bitmap drawToBitmap(View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        try {
            Bitmap bitmapCreateBitmap$default = BitmapCreator.createBitmap$default(BitmapCreator.INSTANCE, view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888, false, false, 24, null);
            view.draw(new Canvas(bitmapCreateBitmap$default));
            return bitmapCreateBitmap$default;
        } catch (Exception e) {
            Log.e("ViewUtil", "drawToBitmap error", e);
            return null;
        }
    }

    public final Bitmap drawToHardwareBitmap(View view) {
        try {
            RenderNode renderNode = new RenderNode("drawToHardwareBitmap");
            float f = DeviceUtil.INSTANCE.isLiteDevice() ? 0.6f : 1.0f;
            int width = (int) (view.getWidth() * f);
            int height = (int) (view.getHeight() * f);
            renderNode.setPosition(0, 0, width, height);
            try {
                Log.i("ViewUtil", "drawToHardwareBitmap: begin, renderNode = " + renderNode);
                RecordingCanvas recordingCanvasBeginRecording = renderNode.beginRecording();
                recordingCanvasBeginRecording.scale(f, f);
                view.draw(recordingCanvasBeginRecording);
                return createHardwareBitmap(renderNode, width, height);
            } finally {
                renderNode.endRecording();
                Log.i("ViewUtil", "drawToHardwareBitmap end");
            }
        } catch (Exception e) {
            Log.e("ViewUtil", "drawToHardwareBitmap error", e);
            return null;
        }
    }

    public final int getColor(View view, int i) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return ContextCompat.getColor(view.getContext(), i);
    }

    public final boolean getDarkMode(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return (context.getResources().getConfiguration().uiMode & 48) == 32;
    }

    public final float getScaleRate(BaseTemplateView baseTemplateView) {
        return 1.0f;
    }

    public final void getShowRect(View view, Rect rect) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(rect, "rect");
        RectF rectF = new RectF();
        getShowRect$default(this, view, rectF, 0.0f, 0.0f, 6, null);
        Rect rect2 = new Rect();
        rectF.roundOut(rect2);
        rect.set(rect2);
    }

    public final void getShowRect(View view, RectF rect, float f, float f2) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(rect, "rect");
        int[] iArr = new int[2];
        view.getLocationOnScreen(iArr);
        int i = iArr[0];
        rect.set(i, iArr[1], i + (view.getWidth() * f), iArr[1] + (view.getHeight() * f2));
    }

    public final Parcelable getViewRootImplParentSurfaceControl(View view) {
        try {
            Object objInvoke = View.class.getMethod("getViewRootImpl", new Class[0]).invoke(view, new Object[0]);
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type android.view.ViewRootImpl");
            ViewRootImpl viewRootImpl = (ViewRootImpl) objInvoke;
            Log.i("ViewUtil", "getViewRootImplParentSurfaceControl viewRoot = " + viewRootImpl);
            Method method = ViewRootImpl.class.getMethod("getParentSurfaceControl", new Class[0]);
            method.setAccessible(true);
            Object objInvoke2 = method.invoke(viewRootImpl, new Object[0]);
            Intrinsics.checkNotNull(objInvoke2, "null cannot be cast to non-null type android.view.SurfaceControl");
            SurfaceControl surfaceControl = (SurfaceControl) objInvoke2;
            Log.i("ViewUtil", "getViewRootImplParentSurfaceControl = " + surfaceControl);
            return surfaceControl;
        } catch (Exception e) {
            Log.e("ViewUtil", "reflect get surface control err", e);
            return null;
        }
    }

    public final Parcelable getViewRootImplSurfaceControl(View view) {
        try {
            Object objInvoke = View.class.getMethod("getViewRootImpl", new Class[0]).invoke(view, new Object[0]);
            Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type android.view.ViewRootImpl");
            ViewRootImpl viewRootImpl = (ViewRootImpl) objInvoke;
            Log.i("ViewUtil", "getViewRootImplSurfaceControl viewRoot = " + viewRootImpl);
            Field declaredField = ViewRootImpl.class.getDeclaredField("mSurfaceControl");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(viewRootImpl);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type android.view.SurfaceControl");
            SurfaceControl surfaceControl = (SurfaceControl) obj;
            Log.i("ViewUtil", "getViewRootImplSurfaceControl = " + surfaceControl);
            return surfaceControl;
        } catch (Exception e) {
            Log.e("ViewUtil", "reflect get surface control err", e);
            return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:102:0x0214  */
    /* JADX WARN: Code duplicated, block: B:106:0x0222  */
    /* JADX WARN: Code duplicated, block: B:108:0x022a  */
    /* JADX WARN: Code duplicated, block: B:113:0x023f  */
    /* JADX WARN: Code duplicated, block: B:115:0x0242  */
    /* JADX WARN: Code duplicated, block: B:118:0x0246  */
    /* JADX WARN: Code duplicated, block: B:122:0x024f  */
    /* JADX WARN: Code duplicated, block: B:47:0x00e8 A[Catch: Exception -> 0x00e0, TryCatch #0 {Exception -> 0x00e0, blocks: (B:39:0x00d3, B:41:0x00db, B:45:0x00e4, B:47:0x00e8, B:52:0x00f1, B:57:0x010d, B:65:0x016f, B:67:0x017c, B:69:0x0182, B:71:0x0188, B:73:0x018e, B:76:0x0197, B:77:0x019a, B:78:0x01a0, B:80:0x01ad, B:82:0x01b3, B:84:0x01b9, B:86:0x01bf, B:89:0x01c8, B:90:0x01cb, B:58:0x0119, B:59:0x0157, B:61:0x015d, B:64:0x0169), top: B:133:0x00d3, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:48:0x00eb  */
    /* JADX WARN: Code duplicated, block: B:50:0x00ee  */
    /* JADX WARN: Code duplicated, block: B:51:0x00f0  */
    /* JADX WARN: Code duplicated, block: B:61:0x015d A[Catch: all -> 0x0167, LOOP:0: B:59:0x0157->B:61:0x015d, LOOP_END, TryCatch #1 {all -> 0x0167, blocks: (B:58:0x0119, B:59:0x0157, B:61:0x015d, B:64:0x0169), top: B:134:0x0119, outer: #0 }] */
    /* JADX WARN: Code duplicated, block: B:8:0x001e  */
    /* JADX WARN: Code duplicated, block: B:92:0x01ce  */
    /* JADX WARN: Code duplicated, block: B:93:0x01d4 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:94:0x01d6  */
    /* JADX WARN: Code duplicated, block: B:95:0x01d8  */
    /* JADX WARN: Code duplicated, block: B:99:0x020d  */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x025e, code lost:
    
        if (r2.invoke(r1, r0, r3, r8) == r9) goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x0275, code lost:
    
        if (r19.invoke(null, r0, null, r8) == r9) goto L130;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Object invokeOnScreenshotCaptured(android.graphics.Bitmap r17, com.miui.keyguard.editor.edit.base.BaseTemplateView r18, kotlin.jvm.functions.Function4 r19, java.lang.Integer r20, kotlin.coroutines.Continuation r21) {
        /*
            Method dump skipped, instruction units count: 635
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.keyguard.editor.utils.ViewUtil.invokeOnScreenshotCaptured(android.graphics.Bitmap, com.miui.keyguard.editor.edit.base.BaseTemplateView, kotlin.jvm.functions.Function4, java.lang.Integer, kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final void invokeOnTextureUpdated(BaseTemplateView baseTemplateView, TextureView textureView, Function4 function4) {
        EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
        Context context = baseTemplateView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        BuildersKt__Builders_commonKt.launch$default(companion.getCoroutineScope(context), null, null, new C01441(textureView, baseTemplateView, baseTemplateView, function4, null), 3, null);
    }

    public final boolean isRtl(View view) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        return view.getResources().getConfiguration().getLayoutDirection() == 1;
    }

    public final boolean isUiThread() {
        return Looper.getMainLooper().isCurrentThread();
    }

    public final Boolean readApplyFlag(DialogInterface dialogInterface) {
        Intrinsics.checkNotNullParameter(dialogInterface, "<this>");
        if (dialogInterface instanceof AlertDialog) {
            Window window = ((AlertDialog) dialogInterface).getWindow();
            View decorView = window != null ? window.getDecorView() : null;
            if (decorView != null) {
                int i = R.id.kg_apply_flag;
                Object tag = decorView.getTag(i);
                decorView.setTag(i, null);
                if (tag instanceof Boolean) {
                    return (Boolean) tag;
                }
            }
        } else {
            Log.w("ViewUtil", "readApplyFlag failed: is not AlertDialog.");
        }
        return null;
    }

    public final void runOnUIThread(AppCompatActivity appCompatActivity, CoroutineScope coroutineScope, Runnable runnable) {
        CoroutineScope coroutineScope2;
        LifecycleCoroutineScope lifecycleScope;
        Intrinsics.checkNotNullParameter(runnable, "runnable");
        if (isUiThread()) {
            runnable.run();
            return;
        }
        if (appCompatActivity == null || (lifecycleScope = LifecycleOwnerKt.getLifecycleScope(appCompatActivity)) == null) {
            if (coroutineScope == null) {
                coroutineScope = CoroutineScopeKt.CoroutineScope(Dispatchers.getMain());
            }
            coroutineScope2 = coroutineScope;
        } else {
            coroutineScope2 = lifecycleScope;
        }
        BuildersKt__Builders_commonKt.launch$default(coroutineScope2, null, null, new C01451(runnable, null), 3, null);
    }

    public final void setBackgroundBlurModeWhenBlurEnabled(View view, float f, int i) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        if (ViewStylerKt.canUseBlur()) {
            ViewStylerKt.setBackgroundBlurMode(view, f, i);
        }
    }

    public final void setEnabledAlpha(View view, boolean z) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        view.setAlpha(z ? 1.0f : 0.3f);
    }

    public final void setGravity(View view, int i) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        FrameLayout.LayoutParams layoutParams2 = layoutParams instanceof FrameLayout.LayoutParams ? (FrameLayout.LayoutParams) layoutParams : null;
        if (layoutParams2 == null) {
            return;
        }
        layoutParams2.gravity = i;
        view.setLayoutParams(layoutParams2);
    }

    public final void setHeight(View view, int i) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = i;
        view.setLayoutParams(layoutParams);
    }

    public final void setSmoothCornerEnabled(View view, boolean z) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        try {
            boolean zSupportViewSmoothCorner = supportViewSmoothCorner();
            Log.i("ViewUtil", "setSmoothCornerEnabled -> isViewSmoothCornerSupported = " + zSupportViewSmoothCorner);
            if (zSupportViewSmoothCorner) {
                view.getClass().getMethod("setSmoothCornerEnabled", Boolean.TYPE).invoke(view, Boolean.valueOf(z));
            }
        } catch (Exception e) {
            Log.e("ViewUtil", "setSmoothCornerEnabled failed", e);
        }
    }

    public final void setThrottledClickListener(View view, final View.OnClickListener listener, final long j) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(listener, "listener");
        final Ref.LongRef longRef = new Ref.LongRef();
        view.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.utils.ViewUtil$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ViewUtil.setThrottledClickListener$lambda$4(longRef, j, listener, view2);
            }
        });
    }

    public final void setViewMarginBottom(View view, int i) {
        Intrinsics.checkNotNullParameter(view, "view");
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin = i;
            view.setLayoutParams(layoutParams);
        }
    }

    public final void setWidth(View view, int i) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = i;
        view.setLayoutParams(layoutParams);
    }

    public final boolean supportViewSmoothCorner() {
        try {
            return SystemProperties.getBoolean("persist.sys.support_view_smoothcorner", false);
        } catch (Exception e) {
            Log.e("ViewUtil", "supportViewSmoothCorner failed", e);
            return false;
        }
    }

    public final void takeScreenshot(final BaseTemplateView baseTemplateView, final GLTextureView gLTextureView, final Function4 function4) {
        if (!gLTextureView.isTextureUpdated()) {
            Log.w("ViewUtil", "texture not updated");
            gLTextureView.addTextureUpdatedListener(new GLTextureView.TextureUpdatedListener() { // from class: com.miui.keyguard.editor.utils.ViewUtil$$ExternalSyntheticLambda2
                @Override // com.miui.keyguard.editor.edit.wallpaper.GLTextureView.TextureUpdatedListener
                public final void onTextureUpdated() {
                    ViewUtil.takeScreenshot$lambda$0(baseTemplateView, gLTextureView, function4);
                }
            });
        } else {
            gLTextureView.addNextTextureUpdatedListener(new GLTextureView.TextureUpdatedListener() { // from class: com.miui.keyguard.editor.utils.ViewUtil$$ExternalSyntheticLambda3
                @Override // com.miui.keyguard.editor.edit.wallpaper.GLTextureView.TextureUpdatedListener
                public final void onTextureUpdated() {
                    ViewUtil.takeScreenshot$lambda$1(baseTemplateView, gLTextureView, function4);
                }
            });
            Log.i("ViewUtil", "requestRender for screenshot");
            gLTextureView.requestRender();
        }
    }

    public final void takeScreenshot(BaseTemplateView baseTemplateView, Function4 onScreenshotCaptured) {
        EditorActivityViewModel viewModel;
        CoroutineScope coroutineScope;
        Intrinsics.checkNotNullParameter(baseTemplateView, "<this>");
        Intrinsics.checkNotNullParameter(onScreenshotCaptured, "onScreenshotCaptured");
        IWallpaperLayer wallpaperLayer = baseTemplateView.getWallpaperLayer();
        View viewWallpaperLayerView = wallpaperLayer != null ? wallpaperLayer.wallpaperLayerView() : null;
        if (viewWallpaperLayerView instanceof GLTextureView) {
            Log.i("ViewUtil", "wallpaperLayerView GLTextureView");
            takeScreenshot(baseTemplateView, (GLTextureView) viewWallpaperLayerView, onScreenshotCaptured);
            return;
        }
        if (viewWallpaperLayerView instanceof VideoTextureView) {
            Log.i("ViewUtil", "wallpaperLayerView VideoTextureView");
            invokeOnTextureUpdated(baseTemplateView, (TextureView) viewWallpaperLayerView, onScreenshotCaptured);
            return;
        }
        Log.i("ViewUtil", "wallpaperLayerView ImageView " + (viewWallpaperLayerView == null));
        if (baseTemplateView.getContext() instanceof EditorActivity) {
            EditorActivityViewModel.Companion companion = EditorActivityViewModel.Companion;
            Context context = baseTemplateView.getContext();
            Intrinsics.checkNotNull(context, "null cannot be cast to non-null type com.miui.keyguard.editor.EditorActivity");
            viewModel = companion.getViewModel((EditorActivity) context);
        } else {
            viewModel = null;
        }
        if (viewModel == null || (coroutineScope = ViewModelKt.getViewModelScope(viewModel)) == null) {
            EditorActivityViewModel.Companion companion2 = EditorActivityViewModel.Companion;
            Context context2 = baseTemplateView.getContext();
            Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
            coroutineScope = companion2.getCoroutineScope(context2);
        }
        BuildersKt__Builders_commonKt.launch$default(coroutineScope, null, null, new C01461(baseTemplateView, onScreenshotCaptured, null), 3, null);
    }

    public final void writeApplyFlag(DialogInterface dialogInterface, boolean z) {
        View decorView;
        Intrinsics.checkNotNullParameter(dialogInterface, "<this>");
        if (!(dialogInterface instanceof AlertDialog)) {
            Log.w("ViewUtil", "writeApplyFlag failed: is not AlertDialog.");
            return;
        }
        Window window = ((AlertDialog) dialogInterface).getWindow();
        if (window == null || (decorView = window.getDecorView()) == null) {
            return;
        }
        decorView.setTag(R.id.kg_apply_flag, Boolean.valueOf(z));
        Log.i("ViewUtil", "writeApplyFlag: applyCancel = " + z);
    }
}
