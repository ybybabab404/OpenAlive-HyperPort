package com.miui.miwallpaper.opengl.ripple;

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
public class RippleAnimatorProgram extends GlassAnimatorProgram {
    public final IStateStyle allFolmeAnim;
    public final AnimConfig fastConfig;
    public final AnimConfig hideConfig;
    public final AnimState hideIORState;
    public float mProgress;
    public final RippleAnimGLProgram mRippleAnimGLProgram;
    public float mTime;
    public final AnimConfig nextConfig;
    public final AnimConfig screenOnConfig;
    public final AnimConfig showConfig;
    public final AnimState showIORState;
    public final TransitionListener transitionListener;
    public final TransitionListener transitionListenerNoRefresh;

    public RippleAnimatorProgram(RippleAnimGLProgram rippleAnimGLProgram) {
        super(rippleAnimGLProgram);
        this.showIORState = new AnimState().add("progress", 0.0f);
        this.hideIORState = new AnimState().add("progress", 1.0f);
        TransitionListener transitionListener = new TransitionListener() { // from class: com.miui.miwallpaper.opengl.ripple.RippleAnimatorProgram.1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                super.onBegin(obj);
                RippleAnimatorProgram.this.isFolmeAnimIsRuning = true;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                RippleAnimatorProgram.this.isFolmeAnimIsRuning = false;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                super.onUpdate(obj, collection);
                if (UpdateInfo.findByName(collection, "progress") != null) {
                    RippleAnimatorProgram.this.mProgress = UpdateInfo.findByName(collection, "progress").getFloatValue();
                    RippleAnimatorProgram.this.mRippleAnimGLProgram.animateRippleIOR(RippleAnimatorProgram.this.mProgress);
                }
                if (UpdateInfo.findByName(collection, "uTime") != null) {
                    RippleAnimatorProgram.this.mTime = UpdateInfo.findByName(collection, "uTime").getFloatValue();
                    if (RippleAnimatorProgram.this.mTime > 2.1474836E9f) {
                        RippleAnimatorProgram.this.mTime = 0.0f;
                    }
                    RippleAnimatorProgram.this.mTime %= 50.0f;
                    RippleAnimatorProgram.this.mRippleAnimGLProgram.animateRippleMoveSpeed(RippleAnimatorProgram.this.mTime);
                }
                RippleAnimatorProgram.this.refresh();
            }
        };
        this.transitionListener = transitionListener;
        this.transitionListenerNoRefresh = new TransitionListener() { // from class: com.miui.miwallpaper.opengl.ripple.RippleAnimatorProgram.2
            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                super.onUpdate(obj, collection);
                if (UpdateInfo.findByName(collection, "progress" + RippleAnimatorProgram.this.mHashCode) != null) {
                    RippleAnimatorProgram.this.mProgress = UpdateInfo.findByName(collection, "progress" + RippleAnimatorProgram.this.mHashCode).getFloatValue();
                    RippleAnimatorProgram.this.mRippleAnimGLProgram.animateRippleIOR(RippleAnimatorProgram.this.mProgress);
                }
                if (UpdateInfo.findByName(collection, "uTime") != null) {
                    RippleAnimatorProgram.this.mTime = UpdateInfo.findByName(collection, "uTime").getFloatValue();
                    if (RippleAnimatorProgram.this.mTime > 2.1474836E9f) {
                        RippleAnimatorProgram.this.mTime = 0.0f;
                    }
                    RippleAnimatorProgram.this.mTime %= 50.0f;
                    RippleAnimatorProgram.this.mRippleAnimGLProgram.animateRippleMoveSpeed(RippleAnimatorProgram.this.mTime);
                }
            }
        };
        this.mRippleAnimGLProgram = rippleAnimGLProgram;
        this.allFolmeAnim = Folme.useValue("allFolmeAnim" + System.currentTimeMillis());
        AnimConfig animConfig = new AnimConfig();
        this.screenOnConfig = animConfig;
        animConfig.setEase(EaseManager.getStyle(-2, 1.0f, 0.8f));
        animConfig.addListeners(transitionListener);
        AnimConfig animConfig2 = new AnimConfig();
        this.showConfig = animConfig2;
        animConfig2.setEase(EaseManager.getStyle(-2, 1.0f, 0.85f));
        animConfig2.addListeners(transitionListener);
        AnimConfig animConfig3 = new AnimConfig();
        this.hideConfig = animConfig3;
        animConfig3.setEase(EaseManager.getStyle(-2, 1.0f, 0.35f));
        animConfig3.addListeners(transitionListener);
        AnimConfig animConfig4 = new AnimConfig();
        this.fastConfig = animConfig4;
        animConfig4.setEase(EaseManager.getStyle(-2, 1.0f, 1.4f));
        animConfig4.addListeners(transitionListener);
        AnimConfig animConfig5 = new AnimConfig();
        this.nextConfig = animConfig5;
        animConfig5.setEase(EaseManager.getStyle(-2, 1.0f, 0.8f));
        animConfig5.addListeners(transitionListener);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimatorProgram
    public void diffEffectHideAnim() {
        resetGlassAnim();
        this.allFolmeAnim.setTo(new AnimState().add("progress", this.mProgress).add("uTime", this.mTime)).to(this.hideIORState.add("uTime", this.mTime), this.hideConfig);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimatorProgram
    public void diffEffectShowAnim() {
        resetGlassAnim();
        this.allFolmeAnim.setTo(this.hideIORState.add("uTime", this.mTime)).to(this.showIORState.add("uTime", ((double) this.mTime) - 6.0d), this.showConfig);
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
        this.mRippleAnimGLProgram.animateRippleIOR(1.0f);
    }
}
