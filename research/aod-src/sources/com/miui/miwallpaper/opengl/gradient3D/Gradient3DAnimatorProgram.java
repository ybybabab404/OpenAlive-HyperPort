package com.miui.miwallpaper.opengl.gradient3D;

import android.graphics.Bitmap;
import com.miui.miwallpaper.opengl.GlassAnimatorProgram;
import java.util.Collection;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes2.dex */
public class Gradient3DAnimatorProgram extends GlassAnimatorProgram {
    public final IStateStyle allFolmeAnim;
    public final AnimConfig fastConfig;
    public final AnimConfig hideConfig;
    public float mColorSwatchFactor;
    public final Gradient3DAnimGLProgram mGradient3DAnimGLProgram;
    public float mPattern;
    public float mProgress;
    public float mSameColorSwatchFactor;
    public final AnimConfig nextConfig;
    public final AnimConfig screenOnConfig;
    public final AnimConfig showConfig;
    public final TransitionListener transitionListener;

    public Gradient3DAnimatorProgram(Gradient3DAnimGLProgram gradient3DAnimGLProgram, Bitmap bitmap, Bitmap bitmap2) {
        super(gradient3DAnimGLProgram);
        TransitionListener transitionListener = new TransitionListener() { // from class: com.miui.miwallpaper.opengl.gradient3D.Gradient3DAnimatorProgram.1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                super.onBegin(obj);
                Gradient3DAnimatorProgram.this.isFolmeAnimIsRuning = true;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                Gradient3DAnimatorProgram.this.isFolmeAnimIsRuning = false;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                super.onUpdate(obj, collection);
                if (UpdateInfo.findByName(collection, "progress") != null) {
                    Gradient3DAnimatorProgram.this.mProgress = UpdateInfo.findByName(collection, "progress").getFloatValue();
                    Gradient3DAnimatorProgram.this.mGradient3DAnimGLProgram.animateNoiseProgress(Gradient3DAnimatorProgram.this.mProgress);
                }
                if (UpdateInfo.findByName(collection, "pattern") != null) {
                    Gradient3DAnimatorProgram.this.mPattern = UpdateInfo.findByName(collection, "pattern").getFloatValue();
                    Gradient3DAnimatorProgram.this.mGradient3DAnimGLProgram.animateNoisePattern(Gradient3DAnimatorProgram.this.mPattern);
                }
                if (UpdateInfo.findByName(collection, "colorSwatch") != null) {
                    Gradient3DAnimatorProgram.this.mColorSwatchFactor = UpdateInfo.findByName(collection, "colorSwatch").getFloatValue();
                    Gradient3DAnimatorProgram.this.mGradient3DAnimGLProgram.animateNoiseColorSwatch(Gradient3DAnimatorProgram.this.mPattern);
                }
                if (UpdateInfo.findByName(collection, "sameColorSwatch") != null) {
                    Gradient3DAnimatorProgram.this.mSameColorSwatchFactor = UpdateInfo.findByName(collection, "sameColorSwatch").getFloatValue();
                    Gradient3DAnimatorProgram.this.mGradient3DAnimGLProgram.animateNoiseSameColorSwatch(Gradient3DAnimatorProgram.this.mPattern);
                }
                Gradient3DAnimatorProgram.this.refresh();
            }
        };
        this.transitionListener = transitionListener;
        this.mGradient3DAnimGLProgram = gradient3DAnimGLProgram;
        this.allFolmeAnim = Folme.useValue("allFolmeAnim" + System.currentTimeMillis());
        AnimConfig animConfig = new AnimConfig();
        this.screenOnConfig = animConfig;
        animConfig.setEase(EaseManager.getStyle(-2, 1.0f, 1.8f));
        animConfig.addListeners(transitionListener);
        AnimConfig animConfig2 = new AnimConfig();
        this.showConfig = animConfig2;
        animConfig2.setEase(EaseManager.getStyle(-2, 1.0f, 0.65f));
        animConfig2.addListeners(transitionListener);
        AnimConfig animConfig3 = new AnimConfig();
        this.hideConfig = animConfig3;
        animConfig3.setEase(EaseManager.getStyle(-2, 1.0f, 0.25f));
        animConfig3.addListeners(transitionListener);
        AnimConfig animConfig4 = new AnimConfig();
        this.fastConfig = animConfig4;
        animConfig4.setEase(EaseManager.getStyle(-2, 1.0f, 1.4f));
        animConfig4.addListeners(transitionListener);
        AnimConfig animConfig5 = new AnimConfig();
        this.nextConfig = animConfig5;
        animConfig5.setEase(EaseManager.getStyle(-2, 1.0f, 1.0f));
        animConfig5.addListeners(transitionListener);
        gradient3DAnimGLProgram.setResourceBitmap(bitmap, bitmap2);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimatorProgram
    public void diffEffectHideAnim() {
        this.allFolmeAnim.to(new AnimState().add("progress", 0.0f).add("pattern", this.mPattern).add("colorSwatch", 1.0f), this.hideConfig);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimatorProgram
    public void diffEffectShowAnim() {
        resetGlassAnim();
        this.allFolmeAnim.setTo(new AnimState().add("progress", 0.8f).add("pattern", Math.random() * 10.0d).add("colorSwatch", 0.0f)).to(new AnimState().add("progress", 1.0f).add("pattern", this.mPattern + rnd(0.0f, 1.0f)).add("colorSwatch", 1.0f), this.showConfig);
    }

    public final float rnd(float f, float f2) {
        return (float) (((double) f) + (Math.random() * ((double) (f2 - f))));
    }
}
