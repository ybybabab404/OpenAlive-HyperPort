package com.miui.miwallpaper.opengl.moru;

import com.miui.miwallpaper.opengl.GlassAnimatorProgram;
import com.miui.miwallpaper.opengl.WallpaperScenarioRecognition;
import java.util.Collection;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;

/* JADX INFO: loaded from: classes2.dex */
public class MoruGlassAnimatorProgram extends GlassAnimatorProgram {
    public final float HIDE_IOR;
    public final float SHOW_IOR;
    public final String TAG;
    public IStateStyle allFolmeAnim;
    public final MoruGlassAnimGLProgram mMoruGlassAnimGLProgram;
    public WallpaperScenarioRecognition mWallpaperScenarioRecognition;
    public MoruGlassFolmeParam.MoruGlassFolme moruGlassFolme;
    public volatile float progress;
    public final TransitionListener transitionListener;
    public final TransitionListener transitionListenerNoRefresh;
    public volatile float uNormalOffsetX;

    public MoruGlassAnimatorProgram(MoruGlassAnimGLProgram moruGlassAnimGLProgram) {
        super(moruGlassAnimGLProgram);
        this.TAG = getClass().getSimpleName();
        this.HIDE_IOR = 0.0f;
        this.SHOW_IOR = 1.0f;
        this.progress = 1.0f;
        TransitionListener transitionListener = new TransitionListener() { // from class: com.miui.miwallpaper.opengl.moru.MoruGlassAnimatorProgram.1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                super.onBegin(obj);
                MoruGlassAnimatorProgram.this.isFolmeAnimIsRuning = true;
                MoruGlassAnimatorProgram.this.mWallpaperScenarioRecognition.setScenarioState(428L, true);
                MoruGlassAnimatorProgram.this.mWallpaperScenarioRecognition.notifyScenarioChanged(2);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                MoruGlassAnimatorProgram.this.mWallpaperScenarioRecognition.setScenarioState(428L, false);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                MoruGlassAnimatorProgram.this.isFolmeAnimIsRuning = false;
                MoruGlassAnimatorProgram.this.mWallpaperScenarioRecognition.setScenarioState(428L, false);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                if (UpdateInfo.findByName(collection, "uNormalOffsetX") != null) {
                    if (UpdateInfo.findByName(collection, "uNormalOffsetX").getFloatValue() > 2.1474836E9f) {
                        return;
                    }
                    MoruGlassAnimatorProgram.this.uNormalOffsetX = UpdateInfo.findByName(collection, "uNormalOffsetX").getFloatValue();
                    MoruGlassAnimatorProgram.this.mMoruGlassAnimGLProgram.animateMoruGlassOffsetX(MoruGlassAnimatorProgram.this.uNormalOffsetX);
                }
                if (UpdateInfo.findByName(collection, "progress") != null) {
                    if (UpdateInfo.findByName(collection, "progress").getFloatValue() > 1.0f || UpdateInfo.findByName(collection, "progress").getFloatValue() < 0.0f) {
                        return;
                    }
                    MoruGlassAnimatorProgram.this.progress = UpdateInfo.findByName(collection, "progress").getFloatValue();
                    MoruGlassAnimatorProgram.this.mMoruGlassAnimGLProgram.animateMoruGlassIOR(MoruGlassAnimatorProgram.this.progress);
                }
                MoruGlassAnimatorProgram.this.refresh();
            }
        };
        this.transitionListener = transitionListener;
        this.transitionListenerNoRefresh = new TransitionListener() { // from class: com.miui.miwallpaper.opengl.moru.MoruGlassAnimatorProgram.2
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                super.onBegin(obj);
                MoruGlassAnimatorProgram.this.isFolmeAnimIsRuning = true;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                super.onComplete(obj);
                MoruGlassAnimatorProgram.this.isFolmeAnimIsRuning = false;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                if (UpdateInfo.findByName(collection, "uNormalOffsetX" + MoruGlassAnimatorProgram.this.mHashCode) != null) {
                    if (UpdateInfo.findByName(collection, "uNormalOffsetX" + MoruGlassAnimatorProgram.this.mHashCode).getFloatValue() > 2.1474836E9f) {
                        return;
                    }
                    MoruGlassAnimatorProgram.this.uNormalOffsetX = UpdateInfo.findByName(collection, "uNormalOffsetX" + MoruGlassAnimatorProgram.this.mHashCode).getFloatValue();
                    MoruGlassAnimatorProgram.this.mMoruGlassAnimGLProgram.animateMoruGlassOffsetX(MoruGlassAnimatorProgram.this.uNormalOffsetX);
                }
                if (UpdateInfo.findByName(collection, "progress" + MoruGlassAnimatorProgram.this.mHashCode) != null) {
                    if (UpdateInfo.findByName(collection, "progress" + MoruGlassAnimatorProgram.this.mHashCode).getFloatValue() > 1.0f) {
                        return;
                    }
                    if (UpdateInfo.findByName(collection, "progress" + MoruGlassAnimatorProgram.this.mHashCode).getFloatValue() < 0.0f) {
                        return;
                    }
                    MoruGlassAnimatorProgram.this.progress = UpdateInfo.findByName(collection, "progress" + MoruGlassAnimatorProgram.this.mHashCode).getFloatValue();
                    MoruGlassAnimatorProgram.this.mMoruGlassAnimGLProgram.animateMoruGlassIOR(MoruGlassAnimatorProgram.this.progress);
                }
                if (UpdateInfo.findByName(collection, "moruTexScale" + MoruGlassAnimatorProgram.this.mHashCode) != null) {
                    if (UpdateInfo.findByName(collection, "moruTexScale" + MoruGlassAnimatorProgram.this.mHashCode).getFloatValue() > 2.0f) {
                        return;
                    }
                    if (UpdateInfo.findByName(collection, "moruTexScale" + MoruGlassAnimatorProgram.this.mHashCode).getFloatValue() < 0.0f) {
                        return;
                    }
                    MoruGlassAnimatorProgram.this.mMoruGlassAnimGLProgram.animateMoruGlassTexScale(UpdateInfo.findByName(collection, "moruTexScale" + MoruGlassAnimatorProgram.this.mHashCode).getFloatValue());
                }
                if (UpdateInfo.findByName(collection, "moruSaturn") == null || UpdateInfo.findByName(collection, "moruSaturn").getFloatValue() > 2.0f || UpdateInfo.findByName(collection, "moruSaturn").getFloatValue() < 0.0f) {
                    return;
                }
                MoruGlassAnimatorProgram.this.mMoruGlassAnimGLProgram.animateMoruGlassSaturn(UpdateInfo.findByName(collection, "moruSaturn").getFloatValue());
            }
        };
        this.mWallpaperScenarioRecognition = WallpaperScenarioRecognition.getInstance();
        this.mMoruGlassAnimGLProgram = moruGlassAnimGLProgram;
        this.allFolmeAnim = Folme.useValue(this);
        this.moruGlassFolme = new MoruGlassFolmeParam().getMoruGlassFolmeParamByGlassId(moruGlassAnimGLProgram.mGlassId);
        this.uNormalOffsetX = MoruGlassParam.getValueByIndex(getGlassId()).offsetX;
        this.allFolmeAnim.setTo(this.moruGlassFolme.animScreenOnStartState);
        this.moruGlassFolme.lightScreenConfig.addListeners(transitionListener);
        this.moruGlassFolme.showConfig.addListeners(transitionListener);
        this.moruGlassFolme.hideConfig.addListeners(transitionListener);
        this.moruGlassFolme.nextConfig.addListeners(transitionListener);
        this.moruGlassFolme.fastConfig.addListeners(transitionListener);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimatorProgram
    public void diffEffectHideAnim() {
        IStateStyle iStateStyle = this.allFolmeAnim;
        MoruGlassFolmeParam.MoruGlassFolme moruGlassFolme = this.moruGlassFolme;
        iStateStyle.to(moruGlassFolme.animHideEndState, moruGlassFolme.hideConfig);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimatorProgram
    public void diffEffectShowAnim() {
        resetGlassAnim();
        IStateStyle to = this.allFolmeAnim.setTo(this.moruGlassFolme.animShowStartState);
        MoruGlassFolmeParam.MoruGlassFolme moruGlassFolme = this.moruGlassFolme;
        to.to(moruGlassFolme.animShowEndState, moruGlassFolme.showConfig);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimatorProgram
    public void resetGlassAnim() {
        this.allFolmeAnim.cancel();
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimatorProgram
    public void resetHideGlassStatus() {
        this.mMoruGlassAnimGLProgram.animateMoruGlassIOR(0.0f);
    }
}
