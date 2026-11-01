package com.miui.miwallpaper.opengl.guass;

import android.graphics.Bitmap;
import android.graphics.Rect;
import com.miui.miwallpaper.opengl.GlassAnimatorProgram;
import com.miui.miwallpaper.opengl.WallpaperScenarioRecognition;
import java.util.Collection;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes2.dex */
public class BlurGlassAnimatorProgram extends GlassAnimatorProgram {
    public final float HIDE_RADIUS;
    public final String SHADOW_RATIO;
    public final float SHOW_RADIUS;
    public final String TAG;
    public final String TEXTURE_BLUR;
    public IStateStyle allFolmeAnim;
    public final AnimConfig fastConfig;
    public AnimState hideBlurState;
    public final AnimConfig hideConfig;
    public final BlurGlassAnimGLProgram mBlurGlassAnimGLProgram;
    public WallpaperScenarioRecognition mWallpaperScenarioRecognition;
    public final AnimConfig screenOnConfig;
    public AnimState showBlurState;
    public final AnimConfig showConfig;
    public final TransitionListener transitionListener;
    public final TransitionListener transitionListenerNoRefresh;

    public BlurGlassAnimatorProgram(BlurGlassAnimGLProgram blurGlassAnimGLProgram) {
        super(blurGlassAnimGLProgram);
        this.TAG = getClass().getSimpleName();
        this.TEXTURE_BLUR = "texBlur";
        this.SHADOW_RATIO = "shadowRatio";
        TransitionListener transitionListener = new TransitionListener() { // from class: com.miui.miwallpaper.opengl.guass.BlurGlassAnimatorProgram.1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                super.onBegin(obj);
                BlurGlassAnimatorProgram.this.isFolmeAnimIsRuning = true;
                BlurGlassAnimatorProgram.this.mWallpaperScenarioRecognition.setScenarioState(427L, true);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                BlurGlassAnimatorProgram.this.mWallpaperScenarioRecognition.setScenarioState(427L, false);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                BlurGlassAnimatorProgram.this.isFolmeAnimIsRuning = false;
                BlurGlassAnimatorProgram.this.mWallpaperScenarioRecognition.setScenarioState(427L, false);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                if (UpdateInfo.findByName(collection, "texBlur") != null) {
                    BlurGlassAnimatorProgram.this.mBlurGlassAnimGLProgram.setRadius(UpdateInfo.findByName(collection, "texBlur").getFloatValue());
                }
                if (UpdateInfo.findByName(collection, "shadowRatio") != null) {
                    BlurGlassAnimatorProgram.this.mBlurGlassAnimGLProgram.setShadowRatio(UpdateInfo.findByName(collection, "shadowRatio").getFloatValue());
                }
                BlurGlassAnimatorProgram.this.refresh();
            }
        };
        this.transitionListener = transitionListener;
        this.transitionListenerNoRefresh = new TransitionListener() { // from class: com.miui.miwallpaper.opengl.guass.BlurGlassAnimatorProgram.2
            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                if (UpdateInfo.findByName(collection, "texBlur") != null) {
                    BlurGlassAnimatorProgram.this.mBlurGlassAnimGLProgram.setRadius(UpdateInfo.findByName(collection, "texBlur").getFloatValue());
                }
                if (UpdateInfo.findByName(collection, "shadowRatio") != null) {
                    BlurGlassAnimatorProgram.this.mBlurGlassAnimGLProgram.setShadowRatio(UpdateInfo.findByName(collection, "shadowRatio").getFloatValue());
                }
            }
        };
        this.SHOW_RADIUS = 76.0f;
        this.HIDE_RADIUS = 0.0f;
        this.showBlurState = new AnimState().add("texBlur", 76.0f).add("shadowRatio", 0.0f);
        this.hideBlurState = new AnimState().add("texBlur", 0.0f).add("shadowRatio", 1.0f);
        this.mWallpaperScenarioRecognition = WallpaperScenarioRecognition.getInstance();
        this.mBlurGlassAnimGLProgram = blurGlassAnimGLProgram;
        this.allFolmeAnim = Folme.useValue("allFolmeAnim" + System.currentTimeMillis());
        AnimConfig animConfig = new AnimConfig();
        this.screenOnConfig = animConfig;
        animConfig.setEase(EaseManager.getStyle(-2, 1.0d, 1.4d));
        animConfig.addListeners(transitionListener);
        AnimConfig animConfig2 = new AnimConfig();
        this.showConfig = animConfig2;
        animConfig2.setEase(EaseManager.getStyle(-2, 1.0f, 0.5f));
        animConfig2.addListeners(transitionListener);
        AnimConfig animConfig3 = new AnimConfig();
        this.hideConfig = animConfig3;
        animConfig3.setEase(EaseManager.getStyle(-2, 0.95d, 0.4d));
        animConfig3.addListeners(transitionListener);
        AnimConfig animConfig4 = new AnimConfig();
        this.fastConfig = animConfig4;
        animConfig4.setEase(EaseManager.getStyle(-2, 1.0d, 1.4d));
        animConfig4.addListeners(transitionListener);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimatorProgram
    public void addTransitionListener(TransitionListener transitionListener) {
        AnimConfig animConfig = this.hideConfig;
        if (animConfig == null || this.showConfig == null) {
            return;
        }
        animConfig.addListeners(transitionListener);
        this.showConfig.addListeners(transitionListener);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimatorProgram
    public void diffEffectHideAnim() {
        resetGlassAnim();
        this.allFolmeAnim.setTo(this.showBlurState).to(this.hideBlurState, this.hideConfig);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimatorProgram
    public void diffEffectShowAnim() {
        resetGlassAnim();
        this.allFolmeAnim.setTo(this.hideBlurState).to(this.showBlurState, this.showConfig);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimatorProgram
    public float[] getMatrix(Bitmap bitmap, int i, int i2) {
        this.mBlurGlassAnimGLProgram.updateMVPMatrix(i, i2, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
        return this.mBlurGlassAnimGLProgram.getMVPMatrix();
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimatorProgram
    public float[] getScaleXY() {
        return this.mBlurGlassAnimGLProgram.getScaleXY();
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimatorProgram
    public float[] getTranslateXY() {
        return this.mBlurGlassAnimGLProgram.getTranslateXY();
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimatorProgram
    public void resetGlassAnim() {
        IStateStyle iStateStyle = this.allFolmeAnim;
        if (iStateStyle != null) {
            iStateStyle.cancel();
        }
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimatorProgram
    public void resetHideGlassStatus() {
        this.mBlurGlassAnimGLProgram.setRadius(0.0f);
    }
}
