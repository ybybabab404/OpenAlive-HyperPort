package com.miui.miwallpaper.opengl.ordinary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.animation.Interpolator;
import com.miui.maml.R;
import com.miui.miwallpaper.opengl.AnimImageWallpaperRenderer;
import com.miui.miwallpaper.opengl.IAnimGLProgram;
import com.miui.miwallpaper.opengl.TextureConfig;
import com.miui.miwallpaper.opengl.moru.MoruGlassAnimGLProgram;
import com.miui.miwallpaper.opengl.moru.MoruGlassParam;
import com.miui.miwallpaper.util.ScreenUtils;
import com.miui.miwallpaper.util.SystemUtil;
import miuix.animation.listener.TransitionListener;

/* JADX INFO: loaded from: classes2.dex */
public class AnimatorProgram implements IAnimGLProgram {
    public static final Interpolator CUBIC_EASE_OUT = new AnimImageWallpaperRenderer.CubicEaseOutInterpolator();
    public static String TAG = "MiuiWallpaper-AnimatorProgram";
    public AnimImageWallpaperRenderer.CallBack mCallback;
    public AnimImageGLProgram mProgram;
    public Runnable mResetRevealRunnable;
    public final ValueAnimator mRevealAnimator;
    public final int mInterval = 2;
    public final int HIGH_REFRESH_RATE = R.styleable.AppCompatTheme_windowFixedHeightMajor;
    public int mFrame = 2;
    public final Handler mHandler = new Handler(Looper.getMainLooper());

    /* JADX INFO: renamed from: $r8$lambda$E-tPyoNZedr6jCeYWY9dtJf60tY, reason: not valid java name */
    public static /* synthetic */ void m1024$r8$lambda$EtPyoNZedr6jCeYWY9dtJf60tY(AnimatorProgram animatorProgram, ValueAnimator valueAnimator) {
        animatorProgram.getClass();
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        animatorProgram.mProgram.mRevealValue = fFloatValue;
        if (ScreenUtils.getRefreshRate(animatorProgram.mProgram.mContext) != 120) {
            animatorProgram.refresh();
            return;
        }
        int i = animatorProgram.mFrame;
        if (i % 2 != 0 && fFloatValue != 0.0f) {
            animatorProgram.mFrame = i + 1;
        } else {
            animatorProgram.mFrame = 1;
            animatorProgram.refresh();
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$hQRMJe-DycveOpNVgFVlFbJ5gts, reason: not valid java name */
    public static /* synthetic */ void m1025$r8$lambda$hQRMJeDycveOpNVgFVlFbJ5gts(AnimatorProgram animatorProgram) {
        animatorProgram.mProgram.mRevealValue = 1.0f;
        animatorProgram.refresh(false);
    }

    public AnimatorProgram(AnimImageGLProgram animImageGLProgram) {
        this.mProgram = animImageGLProgram;
        ValueAnimator valueAnimator = new ValueAnimator();
        this.mRevealAnimator = valueAnimator;
        valueAnimator.setDuration(800L);
        valueAnimator.setInterpolator(CUBIC_EASE_OUT);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.miwallpaper.opengl.ordinary.AnimatorProgram$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                AnimatorProgram.m1024$r8$lambda$EtPyoNZedr6jCeYWY9dtJf60tY(this.f$0, valueAnimator2);
            }
        });
        valueAnimator.addListener(new AnimatorListenerAdapter() { // from class: com.miui.miwallpaper.opengl.ordinary.AnimatorProgram.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                Log.d(AnimatorProgram.TAG, "mRevealAnimator end. reveal = " + AnimatorProgram.this.mProgram.mRevealValue);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                Log.d(AnimatorProgram.TAG, "mRevealAnimator start. reveal = " + AnimatorProgram.this.mProgram.mRevealValue);
            }
        });
        if (SystemUtil.IS_REVEAL_OFF_WHEN_SCREEN_OFF) {
            this.mResetRevealRunnable = new Runnable() { // from class: com.miui.miwallpaper.opengl.ordinary.AnimatorProgram$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    AnimatorProgram.m1025$r8$lambda$hQRMJeDycveOpNVgFVlFbJ5gts(this.f$0);
                }
            };
        }
    }

    public void addTransitionListener(TransitionListener transitionListener) {
    }

    public void diffEffectHideAnim() {
    }

    public void diffEffectShowAnim() {
    }

    public void disposeMainTexture() {
        this.mProgram.disposeMainTexture();
    }

    public void disposeTexture() {
        this.mProgram.disposeTexture();
    }

    public int getGlassId() {
        return this.mProgram.mGlassId;
    }

    public boolean getIsTurnOnBlur() {
        return this.mProgram.mIsTurnOnBlur;
    }

    public float[] getMatrix(Bitmap bitmap, int i, int i2) {
        updateMVPMatrix(i, i2, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
        return this.mProgram.mMVPMatrix;
    }

    public float[] getScaleXY() {
        AnimImageGLProgram animImageGLProgram = this.mProgram;
        return new float[]{animImageGLProgram.mScaleX, animImageGLProgram.mScaleY};
    }

    public float[] getTranslateXY() {
        AnimImageGLProgram animImageGLProgram = this.mProgram;
        return new float[]{animImageGLProgram.mTranslateX, animImageGLProgram.mTranslateY};
    }

    public void onDrawFrame() {
        this.mProgram.onDrawFrame();
    }

    public void onSurfaceCreated(Bitmap bitmap) {
        this.mProgram.onSurfaceCreated(bitmap);
    }

    public void refresh() {
        refresh(true);
    }

    public void refresh(boolean z) {
        AnimImageWallpaperRenderer.CallBack callBack = this.mCallback;
        if (callBack != null) {
            callBack.onRefresh(z);
        }
    }

    public void resetGlassAnim() {
    }

    public void resetHideGlassStatus() {
    }

    public void setCallBack(AnimImageWallpaperRenderer.CallBack callBack) {
        this.mCallback = callBack;
    }

    public void setIsTurnOnBlur(boolean z) {
        this.mProgram.mIsTurnOnBlur = z;
    }

    public void setScale(float f, float f2) {
        this.mProgram.setScale(f, f2);
    }

    public void setTranslate(float f, float f2) {
        this.mProgram.setTranslate(f, f2);
    }

    public void setUpUniformAndAttr() {
        AnimImageGLProgram animImageGLProgram = this.mProgram;
        if (animImageGLProgram != null) {
            animImageGLProgram.setUpUniformAndAttr();
        }
    }

    public void setup(Bitmap bitmap, TextureConfig textureConfig) {
        AnimImageGLProgram animImageGLProgram = this.mProgram;
        if (!(animImageGLProgram instanceof MoruGlassAnimGLProgram)) {
            animImageGLProgram.setup(bitmap, textureConfig);
        } else if (animImageGLProgram.mIsTurnOnBlur) {
            animImageGLProgram.makeFrosting(bitmap, MoruGlassParam.getValueByIndex(animImageGLProgram.mGlassId).blurRadius);
        } else {
            animImageGLProgram.setup(bitmap, textureConfig);
        }
    }

    public void updateMVPMatrix(int i, int i2, Rect rect) {
        this.mProgram.updateMVPMatrix(i, i2, rect);
    }

    public void useGLProgram() {
        this.mProgram.useGLProgram();
    }
}
