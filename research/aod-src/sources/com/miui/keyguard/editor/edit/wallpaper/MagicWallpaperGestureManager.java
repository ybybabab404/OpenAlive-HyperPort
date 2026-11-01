package com.miui.keyguard.editor.edit.wallpaper;

import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import com.miui.keyguard.editor.data.bean.WallpaperPositionInfo;
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
/* JADX INFO: compiled from: MagicWallpaperGestureManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class MagicWallpaperGestureManager extends GestureManager {
    public final GestureDetector gestureDetector;
    public final RectF mDisplayBounds;
    public ValueAnimator mImageAnimator;
    public final Matrix mMaxMatrix;
    public final RectF mOriginBitmapBounds;
    public PropertyValuesHolder mTransValues;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MagicWallpaperGestureManager(Context context, View targetView, GestureListener gestureListener, Drawable srcDrawAble, WallpaperPositionInfo wallpaperPositionInfo) {
        super(context, targetView, gestureListener, srcDrawAble, wallpaperPositionInfo);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(targetView, "targetView");
        Intrinsics.checkNotNullParameter(gestureListener, "gestureListener");
        Intrinsics.checkNotNullParameter(srcDrawAble, "srcDrawAble");
        this.mOriginBitmapBounds = new RectF();
        this.mDisplayBounds = new RectF();
        this.gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() { // from class: com.miui.keyguard.editor.edit.wallpaper.MagicWallpaperGestureManager$gestureDetector$1
            @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float f, float f2) {
                Intrinsics.checkNotNullParameter(e1, "e1");
                Intrinsics.checkNotNullParameter(e2, "e2");
                if (!this.this$0.canScale()) {
                    return false;
                }
                this.this$0.onTranslationImage(-f, -f2);
                return true;
            }
        });
        this.mMaxMatrix = new Matrix();
    }

    public static final void setupImageAnimator$lambda$1$lambda$0(MagicWallpaperGestureManager magicWallpaperGestureManager, ValueAnimator valueAnimator) {
        ValueAnimator valueAnimator2 = magicWallpaperGestureManager.mImageAnimator;
        Intrinsics.checkNotNull(valueAnimator2);
        Object animatedValue = valueAnimator2.getAnimatedValue();
        Intrinsics.checkNotNull(animatedValue, "null cannot be cast to non-null type android.graphics.Matrix");
        magicWallpaperGestureManager.getMScaleMatrix().set((Matrix) animatedValue);
        GestureManager.removeBorderAndTranslationCenter$default(magicWallpaperGestureManager, null, 1, null);
        GestureListener.DefaultImpls.onApply$default(magicWallpaperGestureManager.getGestureListener(), magicWallpaperGestureManager.getMScaleMatrix(), null, null, 6, null);
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.GestureManager
    public GestureDetector getGestureDetector() {
        return this.gestureDetector;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.GestureManager
    public void onTranslationImage(float f, float f2) {
        Matrix matrix = new Matrix();
        matrix.set(getMScaleMatrix());
        if (getTargetView() instanceof GLTextureView) {
            float f3 = 2;
            matrix.postScale(1 / (getScale() / getScaleY()), 1.0f, (getScreenWidth() * 1.0f) / f3, (getScreenHeight() * 1.0f) / f3);
        }
        matrix.mapRect(this.mDisplayBounds, this.mOriginBitmapBounds);
        if ((this.mDisplayBounds.right + f <= getTargetView().getWidth() && f < 0.0f) || (this.mDisplayBounds.left + f > 0.0f && f > 0.0f)) {
            f = 0.0f;
        }
        RectF rectF = this.mDisplayBounds;
        if ((rectF.top + f2 > 0.0f && f2 > 0.0f) || (rectF.bottom + f2 <= getTargetView().getBottom() && f2 < 0.0f)) {
            f2 = 0.0f;
        }
        getMScaleMatrix().postTranslate(f, f2);
        GestureManager.removeBorderAndTranslationCenter$default(this, null, 1, null);
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.GestureManager
    public void removeBorderAndTranslationCenter(Matrix matrix) {
        getMScaleMatrix().mapRect(this.mDisplayBounds, this.mOriginBitmapBounds);
        GestureListener.DefaultImpls.onApply$default(getGestureListener(), getMScaleMatrix(), null, null, 6, null);
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.GestureManager
    public void scaleEnd(ScaleGestureDetector detector) {
        float maxscale;
        float mInitScale;
        Intrinsics.checkNotNullParameter(detector, "detector");
        super.removeBorderAndTranslationCenter(null);
        float scale = getScale() * detector.getScaleFactor();
        float mMaxScale = getMMaxScale();
        if (getTargetView() instanceof GLTextureView) {
            mInitScale = getMInitScale() * getScaleXYRatio();
            maxscale = getMAXSCALE() * mInitScale;
        } else {
            maxscale = mMaxScale;
            mInitScale = scale;
        }
        if (scale >= mInitScale) {
            if (scale > maxscale) {
                float f = maxscale / scale;
                this.mMaxMatrix.set(getMScaleMatrix());
                this.mMaxMatrix.postScale(f, f, getScaleFocusX(), getScaleFocusY());
                super.removeBorderAndTranslationCenter(this.mMaxMatrix);
                setupImageAnimator(getMScaleMatrix(), this.mMaxMatrix).start();
                return;
            }
            return;
        }
        if (!(getTargetView() instanceof GLTextureView)) {
            setupImageAnimator(getMScaleMatrix(), getMInitMatrix()).start();
            return;
        }
        Matrix matrix = new Matrix();
        matrix.set(getMInitMatrix());
        float f2 = 2;
        matrix.postScale(getScaleXYRatio(), 1.0f, (getScreenWidth() * 1.0f) / f2, (getScreenHeight() * 1.0f) / f2);
        setupImageAnimator(getMScaleMatrix(), matrix).start();
    }

    public final ValueAnimator setupImageAnimator(Matrix matrix, Matrix matrix2) {
        if (this.mImageAnimator == null) {
            this.mTransValues = PropertyValuesHolder.ofObject("matrix", new TranslateEvaluator(), matrix, matrix2);
            ValueAnimator valueAnimator = new ValueAnimator();
            valueAnimator.setObjectValues(this.mTransValues);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.keyguard.editor.edit.wallpaper.MagicWallpaperGestureManager$$ExternalSyntheticLambda0
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    MagicWallpaperGestureManager.setupImageAnimator$lambda$1$lambda$0(this.f$0, valueAnimator2);
                }
            });
            valueAnimator.addListener(getAnimEndListener());
            valueAnimator.setDuration(300L);
            valueAnimator.setValues(this.mTransValues);
            this.mImageAnimator = valueAnimator;
        }
        PropertyValuesHolder propertyValuesHolder = this.mTransValues;
        Intrinsics.checkNotNull(propertyValuesHolder);
        propertyValuesHolder.setObjectValues(matrix, matrix2);
        ValueAnimator valueAnimator2 = this.mImageAnimator;
        Intrinsics.checkNotNull(valueAnimator2);
        return valueAnimator2;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.GestureManager
    public void targetViewFitSrcDrawable(String str) {
        if (getFirstLoad()) {
            this.mOriginBitmapBounds.set(0.0f, 0.0f, getSrcDrawAble().getIntrinsicWidth(), getSrcDrawAble().getIntrinsicHeight());
        }
        super.targetViewFitSrcDrawable(str);
    }
}
