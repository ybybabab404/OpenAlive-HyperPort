package com.miui.keyguard.editor.edit.wallpaper;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;
import android.widget.OverScroller;
import androidx.core.graphics.drawable.DrawableKt;
import com.miui.aod.category.CategoryConstants;
import com.miui.keyguard.editor.data.bean.WallpaperPositionInfo;
import com.miui.keyguard.editor.utils.BitmapUtil;
import com.miui.keyguard.editor.utils.DeviceScreenshotHelper;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.segment.UtilsKt;
import com.miui.keyguard.editor.utils.task.Task;
import com.miui.miwallpaper.opengl.GlassRenderer;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.ArraysKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.math.MathKt;
import kotlin.text.StringsKt;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.BuildersKt__Builders_commonKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.Dispatchers;

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
/* JADX INFO: compiled from: GestureManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public class GestureManager {
    public final int MAXSCALE;
    public final AnimatorListenerAdapter animEndListener;
    public final Context context;
    public boolean firstLoad;
    public String fromWhere;
    public final GestureDetector gestureDetector;
    public final GestureListener gestureListener;
    public boolean isAnimating;
    public boolean isCut;
    public boolean isScaling;
    public final WallpaperPositionInfo lastPositionInfo;
    public WallpaperPositionInfo lastUsePositionInfo;
    public ValueAnimator mAnimator;
    public int mCurrentX;
    public int mCurrentY;
    public final Matrix mInitMatrix;
    public float mInitScale;
    public float mMaxScale;
    public final ScaleGestureDetector mScaleGestureDetector;
    public final Matrix mScaleMatrix;
    public volatile boolean positionInfoChanged;
    public Float renderScaleWhenZoom;
    public float scaleFocusX;
    public float scaleFocusY;
    public float scaleXYRatio;
    public Boolean scaleable;
    public int screenEdgeDiffLen;
    public int screenHeight;
    public int screenOrientation;
    public int screenWidth;
    public OverScroller scroller;
    public final Drawable srcDrawAble;
    public final View targetView;
    public final int touchSlop;
    public final ValueAnimator translationAnimation;

    /* JADX INFO: renamed from: com.miui.keyguard.editor.edit.wallpaper.GestureManager$notifyGestureEnd$1, reason: invalid class name */
    /* JADX INFO: compiled from: GestureManager.kt */
    @Metadata
    @DebugMetadata(c = "com.miui.keyguard.editor.edit.wallpaper.GestureManager$notifyGestureEnd$1", f = "GestureManager.kt", l = {138}, m = "invokeSuspend")
    public static final class AnonymousClass1 extends SuspendLambda implements Function2<CoroutineScope, Continuation<? super Unit>, Object> {
        public int label;

        public AnonymousClass1(Continuation continuation) {
            super(2, continuation);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return GestureManager.this.new AnonymousClass1(continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(CoroutineScope coroutineScope, Continuation continuation) {
            return ((AnonymousClass1) create(coroutineScope, continuation)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                CoroutineDispatcher coroutineDispatcher = Dispatchers.getDefault();
                GestureManager$notifyGestureEnd$1$cropWallpaper$1 gestureManager$notifyGestureEnd$1$cropWallpaper$1 = new GestureManager$notifyGestureEnd$1$cropWallpaper$1(GestureManager.this, null);
                this.label = 1;
                obj = BuildersKt.withContext(coroutineDispatcher, gestureManager$notifyGestureEnd$1$cropWallpaper$1, this);
                if (obj == coroutine_suspended) {
                    return coroutine_suspended;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            Bitmap bitmap = (Bitmap) obj;
            KeyEvent.Callback targetView = GestureManager.this.getTargetView();
            Intrinsics.checkNotNull(targetView, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer");
            WallpaperCallback wallpaperCallback = ((IWallpaperLayer) targetView).getWallpaperCallback();
            if (wallpaperCallback != null) {
                wallpaperCallback.onGestureWallpaperEnd(bitmap);
            }
            return Unit.INSTANCE;
        }
    }

    public GestureManager(Context context, View targetView, GestureListener gestureListener, Drawable srcDrawAble, WallpaperPositionInfo wallpaperPositionInfo) {
        float f;
        int height;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(targetView, "targetView");
        Intrinsics.checkNotNullParameter(gestureListener, "gestureListener");
        Intrinsics.checkNotNullParameter(srcDrawAble, "srcDrawAble");
        this.context = context;
        this.targetView = targetView;
        this.gestureListener = gestureListener;
        this.srcDrawAble = srcDrawAble;
        this.lastPositionInfo = wallpaperPositionInfo;
        this.scroller = new OverScroller(context);
        this.firstLoad = true;
        this.mScaleMatrix = new Matrix();
        this.scaleXYRatio = 1.0f;
        this.MAXSCALE = 8;
        this.touchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.screenOrientation = context.getResources().getConfiguration().orientation;
        this.fromWhere = "Unknown";
        float width = 0.0f;
        if (context instanceof DeviceScreenshotHelper.ScreenshotContext) {
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            if (deviceUtil.isFold() && deviceUtil.isLargeDisplay(context)) {
                DeviceUtil.DisplayInfo screenSizeFromDisplays = deviceUtil.getScreenSizeFromDisplays(context, DeviceUtil.ScreenType.LARGE);
                width = screenSizeFromDisplays.getWidth();
                height = screenSizeFromDisplays.getHeight();
            } else {
                DeviceUtil.DisplayInfo screenSizeFromDisplays2 = deviceUtil.getScreenSizeFromDisplays(context, DeviceUtil.ScreenType.SMALL);
                width = screenSizeFromDisplays2.getWidth();
                height = screenSizeFromDisplays2.getHeight();
            }
            f = height;
        } else {
            f = 0.0f;
        }
        if (context.getResources().getConfiguration().orientation == 2) {
            float f2 = width;
            width = f;
            f = f2;
        }
        setCalculatePositionPrams(context, (int) width, (int) f);
        AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() { // from class: com.miui.keyguard.editor.edit.wallpaper.GestureManager$animEndListener$1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator, boolean z) {
                super.onAnimationEnd(animator, z);
                this.this$0.notifyGestureEnd();
                this.this$0.setAnimating(false);
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator, boolean z) {
                Intrinsics.checkNotNull(animator);
                super.onAnimationStart(animator, z);
                this.this$0.setAnimating(true);
            }
        };
        this.animEndListener = animatorListenerAdapter;
        ValueAnimator valueAnimatorOfFloat = ObjectAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration(500L);
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.keyguard.editor.edit.wallpaper.GestureManager$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                GestureManager.translationAnimation$lambda$1$lambda$0(this.f$0, valueAnimator);
            }
        });
        valueAnimatorOfFloat.addListener(animatorListenerAdapter);
        this.translationAnimation = valueAnimatorOfFloat;
        this.mScaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener() { // from class: com.miui.keyguard.editor.edit.wallpaper.GestureManager$mScaleGestureDetector$1
            @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScale(ScaleGestureDetector detector) {
                Intrinsics.checkNotNullParameter(detector, "detector");
                if (!this.this$0.canScale()) {
                    return false;
                }
                if (!this.this$0.isScaling()) {
                    this.this$0.setScaling(true);
                    this.this$0.setScaleFocusX(detector.getFocusX());
                    this.this$0.setScaleFocusY(detector.getFocusY());
                }
                float scaleFactor = detector.getScaleFactor();
                this.this$0.getMScaleMatrix().postScale(scaleFactor, scaleFactor, this.this$0.getScaleFocusX(), this.this$0.getScaleFocusY());
                GestureManager.removeBorderAndTranslationCenter$default(this.this$0, null, 1, null);
                return true;
            }

            @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
            public boolean onScaleBegin(ScaleGestureDetector detector) {
                Intrinsics.checkNotNullParameter(detector, "detector");
                return super.onScaleBegin(detector);
            }

            @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
            public void onScaleEnd(ScaleGestureDetector detector) {
                Intrinsics.checkNotNullParameter(detector, "detector");
                this.this$0.scaleEnd(detector);
                this.this$0.setScaling(false);
            }
        });
        this.gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() { // from class: com.miui.keyguard.editor.edit.wallpaper.GestureManager$gestureDetector$1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onFling(MotionEvent e1, MotionEvent e2, float f3, float f4) {
                Intrinsics.checkNotNullParameter(e1, "e1");
                Intrinsics.checkNotNullParameter(e2, "e2");
                if (this.this$0.canScale() && !this.this$0.isScaling()) {
                    this.this$0.mCurrentX = (int) e2.getX();
                    this.this$0.mCurrentY = (int) e2.getY();
                    RectF matrixRectF$default = GestureManager.getMatrixRectF$default(this.this$0, null, 1, null);
                    int i = this.this$0.mCurrentX;
                    int i2 = this.this$0.mCurrentY;
                    int i3 = (int) f3;
                    int i4 = (int) f4;
                    int iWidth = (int) matrixRectF$default.width();
                    int iHeight = (int) matrixRectF$default.height();
                    if (i != iWidth || i2 != iHeight) {
                        this.this$0.scroller.fling(i, i2, i3, i4, 0, iWidth, 0, iHeight, iWidth, iHeight);
                    }
                    if (this.this$0.translationAnimation.isStarted()) {
                        this.this$0.translationAnimation.end();
                    }
                    this.this$0.translationAnimation.start();
                }
                return false;
            }

            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float f3, float f4) {
                Intrinsics.checkNotNullParameter(e1, "e1");
                Intrinsics.checkNotNullParameter(e2, "e2");
                if (!this.this$0.canScale()) {
                    return false;
                }
                this.this$0.onTranslationImage(-f3, -f4);
                return true;
            }
        });
        this.scaleable = Boolean.TRUE;
        this.mInitMatrix = new Matrix();
    }

    public static /* synthetic */ Bitmap getCurCropWallpaper$default(GestureManager gestureManager, WallpaperPositionInfo wallpaperPositionInfo, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getCurCropWallpaper");
        }
        if ((i & 1) != 0) {
            wallpaperPositionInfo = null;
        }
        return gestureManager.getCurCropWallpaper(wallpaperPositionInfo);
    }

    public static /* synthetic */ RectF getMatrixRectF$default(GestureManager gestureManager, Matrix matrix, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getMatrixRectF");
        }
        if ((i & 1) != 0) {
            matrix = null;
        }
        return gestureManager.getMatrixRectF(matrix);
    }

    public static /* synthetic */ void removeBorderAndTranslationCenter$default(GestureManager gestureManager, Matrix matrix, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: removeBorderAndTranslationCenter");
        }
        if ((i & 1) != 0) {
            matrix = null;
        }
        gestureManager.removeBorderAndTranslationCenter(matrix);
    }

    public static final void scaleAnimation$lambda$5(GestureManager gestureManager, float f, float f2, ValueAnimator valueAnimator) {
        Object animatedValue = valueAnimator.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type kotlin.Float");
        float fFloatValue = ((Float) animatedValue).floatValue() / gestureManager.getScale();
        gestureManager.mScaleMatrix.postScale(fFloatValue, fFloatValue, f, f2);
        removeBorderAndTranslationCenter$default(gestureManager, null, 1, null);
    }

    public static /* synthetic */ void setCalculatePositionPrams$default(GestureManager gestureManager, Context context, int i, int i2, int i3, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: setCalculatePositionPrams");
        }
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = 0;
        }
        gestureManager.setCalculatePositionPrams(context, i, i2);
    }

    public static final Bitmap targetViewFitSrcDrawable$lambda$2(GestureManager gestureManager) {
        KeyEvent.Callback callback = gestureManager.targetView;
        IWallpaperLayer iWallpaperLayer = callback instanceof IWallpaperLayer ? (IWallpaperLayer) callback : null;
        if (iWallpaperLayer == null || !iWallpaperLayer.getInTemplate()) {
            return null;
        }
        return gestureManager.targetView instanceof VideoTextureView ? DrawableKt.toBitmap$default(gestureManager.srcDrawAble, 0, 0, null, 7, null) : gestureManager.getCurCropWallpaper(gestureManager.lastPositionInfo);
    }

    public static final void targetViewFitSrcDrawable$lambda$3(GestureManager gestureManager, Bitmap bitmap) {
        KeyEvent.Callback callback = gestureManager.targetView;
        Intrinsics.checkNotNull(callback, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer");
        WallpaperCallback wallpaperCallback = ((IWallpaperLayer) callback).getWallpaperCallback();
        if (wallpaperCallback != null) {
            wallpaperCallback.onFirstBindWallpaper(bitmap);
        }
    }

    public static final void translationAnimation$lambda$1$lambda$0(GestureManager gestureManager, ValueAnimator valueAnimator) {
        if (gestureManager.scroller.computeScrollOffset()) {
            int currX = gestureManager.scroller.getCurrX();
            int i = currX - gestureManager.mCurrentX;
            gestureManager.mCurrentX = currX;
            int currY = gestureManager.scroller.getCurrY();
            int i2 = currY - gestureManager.mCurrentY;
            gestureManager.mCurrentY = currY;
            if (i == 0 || i2 == 0) {
                return;
            }
            gestureManager.onTranslationImage(i, i2);
        }
    }

    public final Float[] calculateOffset(float f, float f2, float f3, float f4, float f5) {
        Float[] fArr = {Float.valueOf((f - MathKt.roundToInt(f)) * f5), Float.valueOf((f2 - MathKt.roundToInt(f2)) * f5), Float.valueOf((f3 - MathKt.roundToInt(f3)) * f5), Float.valueOf((f4 - MathKt.roundToInt(f4)) * f5)};
        String str = this.fromWhere;
        String string = Arrays.toString(fArr);
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        Log.w("GestureManager", str + " calculateOffset: rectangle(" + f + ", " + f2 + ", " + f3 + ", " + f4 + "), offsetArrays=" + string);
        return fArr;
    }

    public final boolean canScale() {
        Boolean bool = this.scaleable;
        if (bool == null) {
            return false;
        }
        Intrinsics.checkNotNull(bool);
        return bool.booleanValue();
    }

    public final AnimatorListenerAdapter getAnimEndListener() {
        return this.animEndListener;
    }

    public final Matrix getCenterCropMatrix() {
        int i = this.screenWidth;
        int i2 = this.screenHeight;
        int intrinsicWidth = this.srcDrawAble.getIntrinsicWidth();
        float f = 2;
        float f2 = (i * 1.0f) / f;
        float f3 = (i2 * 1.0f) / f;
        float intrinsicHeight = f3 - (this.srcDrawAble.getIntrinsicHeight() / 2.0f);
        Matrix matrix = new Matrix();
        matrix.postTranslate(f2 - (intrinsicWidth / 2.0f), intrinsicHeight);
        float f4 = this.mInitScale;
        matrix.postScale(f4, f4, f2, f3);
        return matrix;
    }

    public final float getCenterScaleRatio(int i, int i2) {
        int i3 = this.screenWidth;
        int i4 = this.screenHeight;
        if (i < i3 && i2 < i4) {
            return Math.max((i3 * 1.0f) / i, (i4 * 1.0f) / i2);
        }
        if (i >= i3 || i2 < i4) {
            return (i < i3 || i2 >= i4) ? Math.max((i3 * 1.0f) / i, (i4 * 1.0f) / i2) : (i4 * 1.0f) / i2;
        }
        return (i3 * 1.0f) / i;
    }

    public final Matrix getCropMatrix() {
        Matrix matrix = new Matrix();
        matrix.set(this.mScaleMatrix);
        try {
            View view = this.targetView;
            if ((view instanceof GLTextureView) && ((GLTextureView) view).glassRender != null) {
                float f = 1;
                Float f2 = this.renderScaleWhenZoom;
                float f3 = 2;
                matrix.postScale(f / (f2 != null ? f2.floatValue() : ((GLTextureView) view).glassRender.getNowInitScaleX()), 1.0f, (this.screenWidth * 1.0f) / f3, (this.screenHeight * 1.0f) / f3);
                return matrix;
            }
        } catch (Exception e) {
            Log.e("GestureManager", "getCropMatrix error: " + e);
        }
        return matrix;
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0062  */
    public final Bitmap getCurCropWallpaper(WallpaperPositionInfo wallpaperPositionInfo) {
        Bitmap bitmapCropBitmap;
        WallpaperPositionInfo position = wallpaperPositionInfo;
        Drawable drawable = this.srcDrawAble;
        if ((drawable instanceof BitmapDrawable) && ((BitmapDrawable) drawable).getBitmap() == null) {
            return null;
        }
        Bitmap bitmap$default = DrawableKt.toBitmap$default(this.srcDrawAble, 0, 0, null, 7, null);
        if (position != null) {
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            if ((deviceUtil.isFold() || deviceUtil.isPad()) && position.getOrientation() != null) {
                int screenOrientation = getScreenOrientation();
                Integer orientation = position.getOrientation();
                if (orientation != null && screenOrientation == orientation.intValue()) {
                    position = getPosition(bitmap$default);
                } else {
                    Log.d("GestureManager", this.fromWhere + " getCurCropWallpaper: use lastPositionInfo = " + position);
                }
            } else {
                position = getPosition(bitmap$default);
            }
        } else {
            position = getPosition(bitmap$default);
        }
        if (position == null) {
            return null;
        }
        trackPositionInfoChanged(position);
        try {
            bitmapCropBitmap = BitmapUtil.INSTANCE.cropBitmap(bitmap$default, (int) position.getLeft(), (int) position.getRight(), (int) position.getTop(), (int) position.getBottom());
        } catch (Exception unused) {
            bitmapCropBitmap = null;
        }
        String str = this.fromWhere;
        Integer numValueOf = bitmapCropBitmap != null ? Integer.valueOf(bitmapCropBitmap.getWidth()) : null;
        Integer numValueOf2 = bitmapCropBitmap != null ? Integer.valueOf(bitmapCropBitmap.getHeight()) : null;
        Log.d("GestureManager", str + " getCurCropWallpaper: cropped bitmap = " + numValueOf + ", " + numValueOf2 + ", originBitmap = " + bitmap$default.getWidth() + ", " + bitmap$default.getHeight());
        return bitmapCropBitmap;
    }

    public final Pair getCurCropWallpaperAndRecycle() {
        Bitmap curCropWallpaper$default = getCurCropWallpaper$default(this, null, 1, null);
        return new Pair(curCropWallpaper$default, Boolean.valueOf(!Intrinsics.areEqual(curCropWallpaper$default, DrawableKt.toBitmap$default(this.srcDrawAble, 0, 0, null, 7, null))));
    }

    public final RectF getExpandedPositionRect(Bitmap bitmap, float f, float f2, float f3, float f4) {
        float fMin;
        float f5;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float f6 = this.screenWidth / f;
        float f7 = this.screenHeight / f2;
        RectF rectF = new RectF(f3, f4, ((int) f3) + f6, ((int) f4) + f7);
        float f8 = ((this.screenEdgeDiffLen * 1.0f) / f) / 2.0f;
        RectF rectF2 = new RectF();
        float f9 = -1.0f;
        if (this.screenWidth <= this.screenHeight) {
            fMin = rectF.left;
            float f10 = (width - fMin) - f6;
            if (f8 <= fMin && f8 <= f10) {
                fMin = f8;
            } else if (f8 <= fMin && f8 > f10) {
                fMin = f10;
            } else if (f8 <= fMin || f8 > f10) {
                fMin = Math.min(fMin, f10);
            }
            float f11 = (int) fMin;
            rectF2.set((int) (rectF.left - f11), (int) rectF.top, (int) (rectF.right + f11), (int) rectF.bottom);
            f9 = f10;
            f5 = -1.0f;
        } else {
            fMin = rectF.top;
            f5 = (height - fMin) - f7;
            if (f8 <= fMin && f8 <= f5) {
                fMin = f8;
            } else if (f8 <= fMin && f8 > f5) {
                fMin = f5;
            } else if (f8 <= fMin || f8 > f5) {
                fMin = Math.min(f5, fMin);
            }
            float f12 = (int) fMin;
            rectF2.set((int) rectF.left, (int) (rectF.top - f12), (int) rectF.right, (int) (rectF.bottom + f12));
        }
        Log.d("GestureManager", this.fromWhere + ", getExpandedPositionRect: diff=" + fMin + ", halfWidthHeightDiff=" + f8 + ", rightPadding=" + f9 + ", bottomPadding=" + f5 + ", retRect=" + rectF2);
        return rectF2;
    }

    public final boolean getFirstLoad() {
        return this.firstLoad;
    }

    public GestureDetector getGestureDetector() {
        return this.gestureDetector;
    }

    public final GestureListener getGestureListener() {
        return this.gestureListener;
    }

    public final int getMAXSCALE() {
        return this.MAXSCALE;
    }

    public final Matrix getMInitMatrix() {
        return this.mInitMatrix;
    }

    public final float getMInitScale() {
        return this.mInitScale;
    }

    public final float getMMaxScale() {
        return this.mMaxScale;
    }

    public final ScaleGestureDetector getMScaleGestureDetector() {
        return this.mScaleGestureDetector;
    }

    public final Matrix getMScaleMatrix() {
        return this.mScaleMatrix;
    }

    public final Matrix getMatrix() {
        Matrix matrix = new Matrix();
        matrix.set(this.mScaleMatrix);
        return matrix;
    }

    public final RectF getMatrixRectF(Matrix matrix) {
        Drawable drawable = this.srcDrawAble;
        RectF rectF = new RectF(0.0f, 0.0f, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        if (matrix == null) {
            matrix = this.mScaleMatrix;
        }
        matrix.mapRect(rectF);
        return rectF;
    }

    public final WallpaperPositionInfo getPosition(Bitmap bitmap) {
        float f;
        boolean z;
        RectF rectF;
        GlassRenderer glassRenderer;
        GestureManager gestureManager = this;
        if (bitmap == null || bitmap.getWidth() != gestureManager.srcDrawAble.getIntrinsicWidth() || bitmap.getHeight() != gestureManager.srcDrawAble.getIntrinsicHeight()) {
            boolean z2 = bitmap == null;
            Integer numValueOf = bitmap != null ? Integer.valueOf(bitmap.getWidth()) : null;
            Integer numValueOf2 = bitmap != null ? Integer.valueOf(bitmap.getHeight()) : null;
            Log.e("GestureManager", "getPosition error originBitmap=" + z2 + " w=" + numValueOf + " h=" + numValueOf2 + " sdw=" + gestureManager.srcDrawAble.getIntrinsicWidth() + " sdh=" + gestureManager.srcDrawAble.getIntrinsicHeight());
            return null;
        }
        if (gestureManager.targetView.getWidth() == 0 || gestureManager.targetView.getHeight() == 0) {
            Log.e("GestureManager", "getPosition error w=" + gestureManager.targetView.getWidth() + " h=" + gestureManager.targetView.getHeight());
            return null;
        }
        RectF rectF2 = new RectF(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight());
        Matrix matrix = new Matrix();
        matrix.set(gestureManager.getCropMatrix());
        matrix.mapRect(rectF2);
        float scaleY = gestureManager.getScaleY();
        float f2 = (-rectF2.left) / scaleY;
        float f3 = (-rectF2.top) / scaleY;
        float f4 = gestureManager.screenWidth / scaleY;
        float f5 = gestureManager.screenHeight / scaleY;
        float f6 = f2 + f4;
        float f7 = f3 + f5;
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (!deviceUtil.isPhone() || deviceUtil.isFold()) {
            RectF expandedPositionRect = gestureManager.getExpandedPositionRect(bitmap, scaleY, scaleY, f2, f3);
            scaleY = scaleY;
            f = f3;
            gestureManager = this;
            z = true;
            rectF = expandedPositionRect;
        } else {
            rectF = new RectF(f2, f3, f6, f7);
            z = false;
            f = f3;
        }
        Float[] fArrCalculateOffset = gestureManager.calculateOffset(f2, f, f4, f5, scaleY);
        float f8 = scaleY;
        float f9 = f;
        float[] fArr = new float[9];
        gestureManager.mScaleMatrix.getValues(fArr);
        if (MathKt.roundToInt(f2) >= 0 && MathKt.roundToInt(f9) >= 0) {
            return new WallpaperPositionInfo(MathKt.roundToInt(rectF.left), MathKt.roundToInt(rectF.top), MathKt.roundToInt(rectF.right), MathKt.roundToInt(rectF.bottom), fArr, Boolean.valueOf(z), fArrCalculateOffset, Integer.valueOf(gestureManager.getScreenOrientation()));
        }
        String str = gestureManager.fromWhere;
        View view = gestureManager.targetView;
        GLTextureView gLTextureView = view instanceof GLTextureView ? (GLTextureView) view : null;
        Log.d("GestureManager", str + " getPosition: left or top is negative, retRect=" + rectF + ", cropMatrix=" + matrix + " scaleY=" + f8 + ", targetView.glassRender.nowInitScaleX=" + ((gLTextureView == null || (glassRenderer = gLTextureView.glassRender) == null) ? null : Float.valueOf(glassRenderer.getNowInitScaleX())));
        return null;
    }

    public final RectF getPositionRect(Bitmap bitmap) {
        if (bitmap == null) {
            Log.e("GestureManager", "getPosition error bitmap=true");
            return null;
        }
        if (this.targetView.getWidth() == 0 || this.targetView.getHeight() == 0) {
            Log.e("GestureManager", "getPosition error w=" + this.targetView.getWidth() + " h=" + this.targetView.getHeight());
            return null;
        }
        RectF rectF = new RectF(0.0f, 0.0f, bitmap.getWidth(), bitmap.getHeight());
        Matrix matrix = new Matrix();
        matrix.set(getCropMatrix());
        matrix.mapRect(rectF);
        float scale = getScale();
        float scaleY = getScaleY();
        float f = (-rectF.left) / scale;
        float f2 = (-rectF.top) / scaleY;
        return new RectF(f, f2, (this.screenWidth / scale) + f, (this.screenHeight / scaleY) + f2);
    }

    public final Float getRenderScaleWhenZoom() {
        return this.renderScaleWhenZoom;
    }

    public final float getScale() {
        float[] fArr = new float[9];
        this.mScaleMatrix.getValues(fArr);
        return fArr[0];
    }

    public final float getScaleFocusX() {
        return this.scaleFocusX;
    }

    public final float getScaleFocusY() {
        return this.scaleFocusY;
    }

    public final float getScaleXYRatio() {
        return this.scaleXYRatio;
    }

    public final float getScaleY() {
        float[] fArr = new float[9];
        this.mScaleMatrix.getValues(fArr);
        return fArr[4];
    }

    public final int getScreenHeight() {
        return this.screenHeight;
    }

    public final int getScreenOrientation() {
        return this.context.getResources().getConfiguration().orientation;
    }

    public final Point getScreenSize() {
        return new Point(this.screenWidth, this.screenHeight);
    }

    public final int getScreenWidth() {
        return this.screenWidth;
    }

    public final Drawable getSrcDrawAble() {
        return this.srcDrawAble;
    }

    public final View getTargetView() {
        return this.targetView;
    }

    public final boolean isAnimating() {
        return this.isAnimating;
    }

    public final boolean isCut() {
        return this.isCut;
    }

    public final boolean isScaling() {
        return this.isScaling;
    }

    public final void notifyCheckNeedRemoveBackground() {
        KeyEvent.Callback callback = this.targetView;
        Intrinsics.checkNotNull(callback, "null cannot be cast to non-null type com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer");
        WallpaperCallback wallpaperCallback = ((IWallpaperLayer) callback).getWallpaperCallback();
        if (wallpaperCallback != null) {
            wallpaperCallback.checkNeedRemoveBackground();
        }
    }

    public final void notifyGestureEnd() {
        BuildersKt__Builders_commonKt.launch$default(UtilsKt.getDefaultCoroutineScope(), null, null, new AnonymousClass1(null), 3, null);
        this.isCut = true;
        recordRenderScaleWhenZoom(null);
    }

    public final void onConfigurationChanged() {
        this.mScaleMatrix.reset();
        Context context = this.targetView.getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        setCalculatePositionPrams$default(this, context, 0, 0, 6, null);
        this.firstLoad = true;
    }

    public void onTranslationImage(float f, float f2) {
        RectF matrixRectF$default = getMatrixRectF$default(this, null, 1, null);
        if (matrixRectF$default.width() <= this.screenWidth) {
            f = 0.0f;
        }
        if (matrixRectF$default.height() <= this.screenHeight) {
            f2 = 0.0f;
        }
        if (f == 0.0f && f2 == 0.0f) {
            return;
        }
        this.mScaleMatrix.postTranslate(f, f2);
        removeBorderAndTranslationCenter$default(this, null, 1, null);
    }

    public final boolean positionInfoHasChanged() {
        return this.positionInfoChanged;
    }

    public final void recordRenderScaleWhenZoom(Float f) {
        Log.d("GestureManager", "recordRenderScaleWhenZoom: scale=" + f);
        this.renderScaleWhenZoom = f;
    }

    /* JADX WARN: Code duplicated, block: B:7:0x0028 A[PHI: r1
  0x0028: PHI (r1v4 float) = (r1v1 float), (r1v9 float) binds: [B:11:0x0035, B:5:0x0024] A[DONT_GENERATE, DONT_INLINE]] */
    public void removeBorderAndTranslationCenter(Matrix matrix) {
        float f;
        float f2;
        RectF matrixRectF = getMatrixRectF(matrix);
        int i = this.screenWidth;
        int i2 = this.screenHeight;
        float fWidth = matrixRectF.width();
        float fHeight = matrixRectF.height();
        float f3 = matrixRectF.left;
        float f4 = matrixRectF.right;
        float f5 = matrixRectF.top;
        float f6 = matrixRectF.bottom;
        float f7 = 0.0f;
        if (f3 > 0.0f) {
            f = i;
            if (fWidth > f) {
                f2 = -f3;
            } else {
                f2 = ((f * 1.0f) / 2.0f) - ((fWidth / 2.0f) + f3);
            }
        } else {
            f = i;
            if (f4 >= f) {
                f2 = 0.0f;
            } else if (fWidth > f) {
                f2 = f - f4;
            } else {
                f2 = ((f * 1.0f) / 2.0f) - ((fWidth / 2.0f) + f3);
            }
        }
        if (f5 > 0.0f) {
            float f8 = i2;
            f7 = fHeight > f8 ? -f5 : ((f8 * 1.0f) / 2.0f) - (f5 + (fHeight / 2.0f));
        } else {
            float f9 = i2;
            if (f6 < f9) {
                f7 = fHeight > f9 ? f9 - f6 : ((f9 * 1.0f) / 2.0f) - (f5 + (fHeight / 2.0f));
            }
        }
        if (matrix != null) {
            matrix.postTranslate(f2, f7);
        } else {
            this.mScaleMatrix.postTranslate(f2, f7);
            GestureListener.DefaultImpls.onApply$default(this.gestureListener, this.mScaleMatrix, null, null, 6, null);
        }
    }

    public final void resetLastUsePositionInfo() {
        Bitmap bitmap$default;
        WallpaperPositionInfo position = null;
        try {
            Drawable drawable = this.srcDrawAble;
            BitmapDrawable bitmapDrawable = drawable instanceof BitmapDrawable ? (BitmapDrawable) drawable : null;
            if (bitmapDrawable == null || (bitmap$default = bitmapDrawable.getBitmap()) == null) {
                bitmap$default = DrawableKt.toBitmap$default(this.srcDrawAble, 0, 0, null, 7, null);
            }
            position = getPosition(bitmap$default);
        } catch (Throwable unused) {
        }
        if (position == null) {
            position = this.lastPositionInfo;
        }
        this.lastUsePositionInfo = position;
    }

    public final void resetPositionInfoChanged() {
        this.positionInfoChanged = false;
    }

    public final void scaleAnimation(float f, final float f2, final float f3) {
        ValueAnimator valueAnimator = this.mAnimator;
        if (valueAnimator != null) {
            Intrinsics.checkNotNull(valueAnimator);
            if (valueAnimator.isRunning()) {
                return;
            }
        }
        ValueAnimator valueAnimatorOfFloat = ObjectAnimator.ofFloat(getScale(), f);
        this.mAnimator = valueAnimatorOfFloat;
        Intrinsics.checkNotNull(valueAnimatorOfFloat);
        valueAnimatorOfFloat.setDuration(300L);
        ValueAnimator valueAnimator2 = this.mAnimator;
        Intrinsics.checkNotNull(valueAnimator2);
        valueAnimator2.setInterpolator(new AccelerateInterpolator());
        ValueAnimator valueAnimator3 = this.mAnimator;
        Intrinsics.checkNotNull(valueAnimator3);
        valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.keyguard.editor.edit.wallpaper.GestureManager$$ExternalSyntheticLambda3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator4) {
                GestureManager.scaleAnimation$lambda$5(this.f$0, f2, f3, valueAnimator4);
            }
        });
        ValueAnimator valueAnimator4 = this.mAnimator;
        Intrinsics.checkNotNull(valueAnimator4);
        valueAnimator4.addListener(this.animEndListener);
        ValueAnimator valueAnimator5 = this.mAnimator;
        Intrinsics.checkNotNull(valueAnimator5);
        valueAnimator5.start();
    }

    public void scaleEnd(ScaleGestureDetector detector) {
        Intrinsics.checkNotNullParameter(detector, "detector");
        float scale = getScale() * detector.getScaleFactor();
        float f = this.mInitScale;
        if (scale < f) {
            scaleAnimation(f, this.screenWidth / 2.0f, this.screenHeight / 2.0f);
            return;
        }
        float f2 = this.mMaxScale;
        if (scale > f2) {
            scaleAnimation(f2, this.screenWidth / 2.0f, this.screenHeight / 2.0f);
        }
    }

    public final void setAnimating(boolean z) {
        this.isAnimating = z;
    }

    public final void setCalculatePositionPrams(Context context, int i, int i2) {
        this.screenWidth = i > 0 ? i : DeviceUtil.INSTANCE.currentDisplaySize(context).width();
        int iHeight = i2 > 0 ? i2 : DeviceUtil.INSTANCE.currentDisplaySize(context).height();
        this.screenHeight = iHeight;
        int iAbs = Math.abs(this.screenWidth - iHeight);
        this.screenEdgeDiffLen = iAbs;
        Log.d("GestureManager", "setCalculatePositionPrams: sw=" + i + ", sh=" + i2 + ", screenWidth=" + this.screenWidth + ", screenHeight=" + this.screenHeight + ", screenEdgeDiffLen=" + iAbs);
    }

    public final void setFromWhere(String str) {
        if (str == null) {
            str = this.fromWhere;
        }
        this.fromWhere = str;
    }

    public final void setMatrix(Matrix matrix) {
        Intrinsics.checkNotNullParameter(matrix, "matrix");
        this.mScaleMatrix.set(matrix);
    }

    public final void setScaleFocusX(float f) {
        this.scaleFocusX = f;
    }

    public final void setScaleFocusY(float f) {
        this.scaleFocusY = f;
    }

    public final void setScaleXYRatio(int i) {
        View view = this.targetView;
        if (!(view instanceof GLTextureView) || ((GLTextureView) view).glassRender == null) {
            return;
        }
        this.scaleXYRatio = ((GLTextureView) view).glassRender.getCurrentInitScaleX(i);
    }

    public final void setScaleable(Boolean bool) {
        this.scaleable = bool;
    }

    public final void setScaling(boolean z) {
        this.isScaling = z;
    }

    public final void switchGlassFitMatrix(float f, float f2) {
        int i = this.screenWidth;
        float f3 = 2;
        this.mScaleMatrix.postScale(f / f2, 1.0f, (i * 1.0f) / f3, (i * 1.0f) / f3);
    }

    public void targetViewFitSrcDrawable(String str) {
        Float f;
        Float f2;
        GlassRenderer glassRenderer;
        if (this.firstLoad) {
            this.firstLoad = false;
            int i = this.screenWidth;
            int i2 = this.screenHeight;
            int intrinsicWidth = this.srcDrawAble.getIntrinsicWidth();
            int intrinsicHeight = this.srcDrawAble.getIntrinsicHeight();
            float centerScaleRatio = getCenterScaleRatio(intrinsicWidth, intrinsicHeight);
            this.mInitScale = centerScaleRatio;
            this.mMaxScale = centerScaleRatio * this.MAXSCALE;
            Matrix centerCropMatrix = getCenterCropMatrix();
            Log.d("GestureManager", this.fromWhere + " centerCropMatrix=" + centerCropMatrix + " width=" + i + " height=" + i2 + " dw=" + intrinsicWidth + " dh=" + intrinsicHeight + ", lastPositionInfo=" + this.lastPositionInfo);
            WallpaperPositionInfo wallpaperPositionInfo = this.lastPositionInfo;
            this.lastUsePositionInfo = wallpaperPositionInfo;
            float[] matrixArr = wallpaperPositionInfo != null ? wallpaperPositionInfo.getMatrixArr() : null;
            if (matrixArr == null || StringsKt.equals$default(str, CategoryConstants.CATEGORY_SUPER_WALLPAPER, false, 2, null)) {
                Matrix matrix = new Matrix();
                matrix.set(centerCropMatrix);
                View view = this.targetView;
                if (view instanceof GLTextureView) {
                    GlassRenderer glassRenderer2 = ((GLTextureView) view).glassRender;
                    float f3 = 2;
                    matrix.postScale(glassRenderer2 != null ? glassRenderer2.getNowInitScaleX() : 1.0f, 1.0f, (this.screenWidth * 1.0f) / f3, (this.screenHeight * 1.0f) / f3);
                    String str2 = this.fromWhere;
                    GlassRenderer glassRenderer3 = ((GLTextureView) this.targetView).glassRender;
                    Log.d("GestureManager", str2 + " GLTextureView init scaleX=" + (glassRenderer3 != null ? Float.valueOf(glassRenderer3.getNowInitScaleX()) : null));
                }
                this.mScaleMatrix.set(matrix);
                Log.d("GestureManager", this.fromWhere + " new mScaleMatrix=" + this.mScaleMatrix + " ");
            } else {
                Matrix matrix2 = new Matrix();
                float f4 = matrixArr[0];
                View view2 = this.targetView;
                float nowInitScaleX = (!(view2 instanceof GLTextureView) || (glassRenderer = ((GLTextureView) view2).glassRender) == null) ? 1.0f : glassRenderer.getNowInitScaleX();
                matrix2.postScale(f4, f4);
                WallpaperPositionInfo wallpaperPositionInfo2 = this.lastPositionInfo;
                Intrinsics.checkNotNull(wallpaperPositionInfo2);
                float right = (wallpaperPositionInfo2.getRight() - this.lastPositionInfo.getLeft()) * f4;
                Float[] offset = this.lastPositionInfo.getOffset();
                float fFloatValue = 0.0f;
                float fFloatValue2 = right + ((offset == null || (f2 = (Float) ArraysKt.getOrNull(offset, 2)) == null) ? 0.0f : f2.floatValue());
                float bottom = (this.lastPositionInfo.getBottom() - this.lastPositionInfo.getTop()) * f4;
                Float[] offset2 = this.lastPositionInfo.getOffset();
                if (offset2 != null && (f = (Float) ArraysKt.getOrNull(offset2, 3)) != null) {
                    fFloatValue = f.floatValue();
                }
                float f5 = bottom + fFloatValue;
                float centerScaleRatio2 = getCenterScaleRatio((int) Math.rint(fFloatValue2), (int) Math.rint(f5));
                matrix2.postScale(centerScaleRatio2, centerScaleRatio2);
                WallpaperPositionInfo wallpaperPositionInfo3 = this.lastPositionInfo;
                Intrinsics.checkNotNull(wallpaperPositionInfo3);
                matrix2.postTranslate(-(wallpaperPositionInfo3.getLeft() * f4 * centerScaleRatio2), -(this.lastPositionInfo.getTop() * f4 * centerScaleRatio2));
                float f6 = 2;
                matrix2.postTranslate(((i * 1.0f) / f6) - ((fFloatValue2 * centerScaleRatio2) / 2.0f), ((i2 * 1.0f) / f6) - ((centerScaleRatio2 * f5) / 2.0f));
                matrix2.postScale(nowInitScaleX, 1.0f, this.screenWidth / 2.0f, this.screenHeight / 2.0f);
                this.mScaleMatrix.set(matrix2);
                Log.d("GestureManager", this.fromWhere + " reset mScaleMatrix=" + this.mScaleMatrix + " lastViewWidth=" + fFloatValue2 + ", lastViewHeight=" + f5);
            }
            this.mInitMatrix.set(centerCropMatrix);
            this.gestureListener.onApply(this.mScaleMatrix, Boolean.TRUE, centerCropMatrix);
            Task.create(new Supplier() { // from class: com.miui.keyguard.editor.edit.wallpaper.GestureManager$$ExternalSyntheticLambda1
                @Override // java.util.function.Supplier
                public final Object get() {
                    return GestureManager.targetViewFitSrcDrawable$lambda$2(this.f$0);
                }
            }).post(new Consumer() { // from class: com.miui.keyguard.editor.edit.wallpaper.GestureManager$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    GestureManager.targetViewFitSrcDrawable$lambda$3(this.f$0, (Bitmap) obj);
                }
            });
            this.firstLoad = false;
        }
    }

    public final void trackPositionInfoChanged(WallpaperPositionInfo wallpaperPositionInfo) {
        if (wallpaperPositionInfo == null) {
            return;
        }
        WallpaperPositionInfo wallpaperPositionInfo2 = this.lastUsePositionInfo;
        if (wallpaperPositionInfo2 != null && !Intrinsics.areEqual(wallpaperPositionInfo, wallpaperPositionInfo2)) {
            this.positionInfoChanged = true;
        }
        this.lastUsePositionInfo = wallpaperPositionInfo;
    }
}
