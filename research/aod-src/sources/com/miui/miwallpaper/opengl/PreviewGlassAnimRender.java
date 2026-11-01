package com.miui.miwallpaper.opengl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import com.miui.miwallpaper.opengl.depth.GradualDepthBlurShaderAnimatorProgram;
import com.miui.miwallpaper.opengl.depth.GradualDepthBlurShaderProgram;
import com.miui.miwallpaper.opengl.geo.GeometryAnimGLProgram;
import com.miui.miwallpaper.opengl.geo.GeometryAnimatorProgram;
import com.miui.miwallpaper.opengl.gradient2D.Gradient2DAnimGLProgram;
import com.miui.miwallpaper.opengl.gradient2D.Gradient2DAnimatorProgram;
import com.miui.miwallpaper.opengl.gradient3D.Gradient3DAnimGLProgram;
import com.miui.miwallpaper.opengl.gradient3D.Gradient3DAnimatorProgram;
import com.miui.miwallpaper.opengl.gradient3d2.Gradient3D2AnimGLProgram;
import com.miui.miwallpaper.opengl.gradient3d2.Gradient3D2AnimatorProgram;
import com.miui.miwallpaper.opengl.guass.BlurGlassAnimGLProgram;
import com.miui.miwallpaper.opengl.guass.BlurGlassAnimatorProgram;
import com.miui.miwallpaper.opengl.moru.MoruGlassAnimGLProgram;
import com.miui.miwallpaper.opengl.moru.MoruGlassAnimatorProgram;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram;
import com.miui.miwallpaper.opengl.ordinary.AnimatorProgram;
import com.miui.miwallpaper.opengl.rain.RainAnimGLProgram;
import com.miui.miwallpaper.opengl.rain.RainAnimatorProgram;
import com.miui.miwallpaper.opengl.ripple.RippleAnimGLProgram;
import com.miui.miwallpaper.opengl.ripple.RippleAnimatorProgram;
import com.miui.miwallpaper.opengl.wave.WaveAnimGLProgram;
import com.miui.miwallpaper.opengl.wave.WaveAnimatorProgram;
import com.miui.miwallpaper.util.BitmapUtils;
import miuix.animation.listener.TransitionListener;

/* JADX INFO: loaded from: classes2.dex */
public class PreviewGlassAnimRender extends AnimImageWallpaperRenderer {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final String TAG;
    public AnimatorProgram mAnimatorProgram;
    public Bitmap mBitmap;
    public AnimatorProgram mCacheAnimatorProgram;
    public int mDarken;
    public Bitmap mDepthBitmap;
    public int mEffectType;
    public int mGlassId;
    public boolean mIsTurnOnBlur;

    public PreviewGlassAnimRender(Context context, Bitmap bitmap, int i) {
        super(context);
        String simpleName = getClass().getSimpleName();
        this.TAG = simpleName;
        this.mBitmap = bitmap;
        this.mEffectType = getEffectTypeByEffectId(i);
        this.mGlassId = getShaderIdByEffectId(i);
        this.mAnimatorProgram = getAnimatorProgram(context);
        Log.d(simpleName, "PreviewGlassAnimRender: effectType=" + this.mEffectType + " mGlassId=" + this.mGlassId);
    }

    public void cancelGlassAnim() {
        AnimatorProgram animatorProgram = this.mAnimatorProgram;
        if (animatorProgram != null) {
            animatorProgram.resetGlassAnim();
        }
    }

    /* JADX WARN: Code duplicated, block: B:31:0x00d8  */
    @Override // com.miui.miwallpaper.opengl.ImageWallpaperRenderer
    public AnimatorProgram getAnimatorProgram(Context context) {
        int effectType = getEffectType();
        float[] scaleXY = new float[2];
        float[] translateXY = new float[2];
        AnimatorProgram animatorProgram = this.mAnimatorProgram;
        if (animatorProgram != null) {
            scaleXY = animatorProgram.getScaleXY();
            translateXY = this.mAnimatorProgram.getTranslateXY();
            this.mIsTurnOnBlur = this.mAnimatorProgram.getIsTurnOnBlur();
        }
        if (effectType == 1) {
            MoruGlassAnimGLProgram moruGlassAnimGLProgram = new MoruGlassAnimGLProgram(context);
            moruGlassAnimGLProgram.mGlassId = this.mGlassId;
            this.mAnimatorProgram = new MoruGlassAnimatorProgram(moruGlassAnimGLProgram);
        } else if (effectType != 3) {
            switch (effectType) {
                case 5:
                    GradualDepthBlurShaderProgram gradualDepthBlurShaderProgram = new GradualDepthBlurShaderProgram(context);
                    gradualDepthBlurShaderProgram.setAssistTexture(this.mDepthBitmap);
                    gradualDepthBlurShaderProgram.mGlassId = this.mGlassId;
                    this.mAnimatorProgram = new GradualDepthBlurShaderAnimatorProgram(gradualDepthBlurShaderProgram);
                    break;
                case 6:
                    this.mAnimatorProgram = new GeometryAnimatorProgram(new GeometryAnimGLProgram(context));
                    break;
                case 7:
                    int i = this.mGlassId;
                    if (i == 0) {
                        this.mAnimatorProgram = new WaveAnimatorProgram(new WaveAnimGLProgram(context));
                        break;
                    } else {
                        if (i == 1) {
                            this.mAnimatorProgram = new RippleAnimatorProgram(new RippleAnimGLProgram(context));
                        }
                        break;
                    }
                case 9:
                    int i2 = this.mGlassId;
                    if (i2 == 0) {
                        this.mAnimatorProgram = new Gradient2DAnimatorProgram(new Gradient2DAnimGLProgram(context));
                        break;
                    } else {
                        if (i2 == 1) {
                            Gradient3DAnimGLProgram gradient3DAnimGLProgram = new Gradient3DAnimGLProgram(context);
                            gradient3DAnimGLProgram.mGlassId = 1;
                            this.mAnimator = new Gradient3DAnimatorProgram(gradient3DAnimGLProgram, getBitmap(), getBitmap());
                        } else if (i2 == 2) {
                            this.mAnimatorProgram = new Gradient3D2AnimatorProgram(new Gradient3D2AnimGLProgram(context));
                        }
                        break;
                    }
                case 8:
                    this.mAnimatorProgram = new RainAnimatorProgram(new RainAnimGLProgram(context));
                    break;
                default:
                    if (!this.mIsTurnOnBlur) {
                        AnimImageGLProgram animImageGLProgram = new AnimImageGLProgram(context);
                        animImageGLProgram.mGlassId = this.mGlassId;
                        this.mAnimatorProgram = new AnimatorProgram(animImageGLProgram);
                    } else {
                        BlurGlassAnimGLProgram blurGlassAnimGLProgram = new BlurGlassAnimGLProgram(context, 0, 0, true);
                        blurGlassAnimGLProgram.mGlassId = this.mGlassId;
                        this.mAnimatorProgram = new BlurGlassAnimatorProgram(blurGlassAnimGLProgram);
                    }
                    break;
            }
        } else if (this.mGlassId == 0) {
            this.mAnimatorProgram = new BlurGlassAnimatorProgram(new BlurGlassAnimGLProgram(context, 0, 0, true));
        } else {
            GradualDepthBlurShaderProgram gradualDepthBlurShaderProgram2 = new GradualDepthBlurShaderProgram(context);
            gradualDepthBlurShaderProgram2.setAssistTexture(this.mDepthBitmap);
            gradualDepthBlurShaderProgram2.mGlassId = this.mGlassId;
            this.mAnimatorProgram = new GradualDepthBlurShaderAnimatorProgram(gradualDepthBlurShaderProgram2);
        }
        this.mAnimatorProgram.setScale(scaleXY[0], scaleXY[1]);
        this.mAnimatorProgram.setTranslate(translateXY[0], translateXY[1]);
        this.mAnimatorProgram.mProgram.mDarken = this.mDarken;
        this.mAnimatorProgram.setIsTurnOnBlur(this.mIsTurnOnBlur);
        return this.mAnimatorProgram;
    }

    @Override // com.miui.miwallpaper.opengl.AnimImageWallpaperRenderer
    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public float getCurrentInitScaleX(int i) {
        return getInitScaleX(getShaderIdByEffectId(i));
    }

    public int getEffectType() {
        return this.mEffectType;
    }

    public final int getEffectTypeByEffectId(int i) {
        return i / 10000;
    }

    public float getInitScaleX(int i) {
        if (!(this.mAnimatorProgram instanceof MoruGlassAnimatorProgram)) {
            return 1.0f;
        }
        if (i == 0) {
            return 1.08f;
        }
        if (i == 1) {
            return 1.28f;
        }
        if (i != 2) {
            return (i == 3 || i == 4) ? 1.14f : 1.0f;
        }
        return 1.08f;
    }

    public float[] getMatrix(Bitmap bitmap, int i, int i2) {
        return this.mAnimatorProgram.getMatrix(bitmap, i, i2);
    }

    public float getNowInitScaleX() {
        return getInitScaleX(this.mGlassId);
    }

    public final int getShaderIdByEffectId(int i) {
        return i % 10000;
    }

    public void hideGlassIOR() {
        AnimatorProgram animatorProgram = this.mAnimatorProgram;
        if (animatorProgram != null) {
            animatorProgram.resetHideGlassStatus();
        }
    }

    @Override // com.miui.miwallpaper.opengl.AnimImageWallpaperRenderer, com.miui.miwallpaper.opengl.ImageWallpaperRenderer
    public void onSurfaceChanged(int i, int i2) {
        super.onSurfaceChanged(i, i2);
        this.mAnimator.mProgram.onSurfaceChanged(i, i2);
    }

    public void resetGlassState() {
        AnimatorProgram animatorProgram = this.mAnimatorProgram;
        if (animatorProgram instanceof BlurGlassAnimatorProgram) {
            this.mCacheAnimatorProgram = animatorProgram;
        } else {
            disposeTexture();
        }
        this.mEffectType = 0;
        this.mGlassId = 0;
        this.mAnimatorProgram.setIsTurnOnBlur(false);
        onSurfaceCreated();
    }

    public void setBitmap(Bitmap bitmap, boolean... zArr) {
        if (zArr.length == 0 || !zArr[0]) {
            BitmapUtils.recycleBitmap(this.mBitmap);
        }
        this.mBitmap = bitmap;
        if (bitmap == null) {
            throw new NullPointerException("bitmap is null");
        }
        disposeMainTexture();
        updateTexture(bitmap);
        this.mTexture.updateDimensions(new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
    }

    public void setCallBack(AnimImageWallpaperRenderer.CallBack callBack, TransitionListener transitionListener) {
        this.mCallback = callBack;
        AnimatorProgram animatorProgram = this.mAnimatorProgram;
        if (animatorProgram != null) {
            animatorProgram.setCallBack(callBack);
            this.mAnimatorProgram.addTransitionListener(transitionListener);
        }
    }

    public void setDarken(boolean z) {
        this.mDarken = z ? 1 : 0;
    }

    public void setEffectType(int i) {
        Log.d(this.TAG, "setEffectType: typeId=" + i);
        this.mEffectType = getEffectTypeByEffectId(i);
        this.mGlassId = getShaderIdByEffectId(i);
        disposeTexture();
        onSurfaceCreated();
    }

    public void setGlassWithBlur(boolean z) {
        Log.d(this.TAG, "setGlassWithBlur: isTurnOnBlur=" + z);
        this.mAnimatorProgram.setIsTurnOnBlur(z);
        disposeTexture();
        onSurfaceCreated();
    }

    public void setScale(float f, float f2) {
        this.mAnimatorProgram.setScale(f, f2);
    }

    public void setTranslate(float f, float f2) {
        this.mAnimatorProgram.setTranslate(f, f2);
    }

    public void startHideGlassAnim() {
        AnimatorProgram animatorProgram = this.mAnimatorProgram;
        if (animatorProgram != null) {
            animatorProgram.diffEffectHideAnim();
        }
    }

    public void startShowGlassAnim() {
        AnimatorProgram animatorProgram = this.mAnimatorProgram;
        if (animatorProgram != null) {
            animatorProgram.diffEffectShowAnim();
        }
    }

    public void updateGlassEffectAndBlurState(int i, boolean z) {
        if (this.mCacheAnimatorProgram == null) {
            disposeTexture();
            this.mEffectType = getEffectTypeByEffectId(i);
            this.mGlassId = getShaderIdByEffectId(i);
            this.mAnimatorProgram.setIsTurnOnBlur(z);
            onSurfaceCreated();
            return;
        }
        float[] scaleXY = new float[2];
        float[] translateXY = new float[2];
        AnimatorProgram animatorProgram = this.mAnimatorProgram;
        if (animatorProgram != null) {
            scaleXY = animatorProgram.getScaleXY();
            translateXY = this.mAnimatorProgram.getTranslateXY();
            this.mAnimatorProgram.disposeTexture();
        }
        this.mAnimatorProgram = this.mCacheAnimatorProgram;
        this.mEffectType = getEffectTypeByEffectId(i);
        this.mGlassId = getShaderIdByEffectId(i);
        this.mAnimatorProgram.setIsTurnOnBlur(z);
        this.mAnimatorProgram.setScale(scaleXY[0], scaleXY[1]);
        this.mAnimatorProgram.setTranslate(translateXY[0], translateXY[1]);
        this.mCacheAnimatorProgram = null;
        AnimatorProgram animatorProgram2 = this.mAnimatorProgram;
        this.mAnimator = animatorProgram2;
        animatorProgram2.setUpUniformAndAttr();
    }

    public final void updateTexture(Bitmap bitmap) {
        this.mAnimatorProgram.mProgram.updateTexture(bitmap, new TextureConfig(0, true));
    }
}
