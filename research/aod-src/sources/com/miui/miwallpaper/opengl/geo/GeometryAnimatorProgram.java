package com.miui.miwallpaper.opengl.geo;

import android.graphics.Bitmap;
import android.graphics.Rect;
import com.miui.aod.components.view.BaseStyleSelectView;
import com.miui.miwallpaper.opengl.GlassAnimatorProgram;
import com.miui.miwallpaper.opengl.WallpaperScenarioRecognition;
import java.util.Collection;
import miuix.animation.Folme;
import miuix.animation.IStateStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.ValueProperty;
import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes2.dex */
public class GeometryAnimatorProgram extends GlassAnimatorProgram {
    public final float HIDE_IOR;
    public final float MIN_VISIBLE_CHANGE;
    public final float SHOW_IOR;
    public IStateStyle allEffectLinkAnimTarget;
    public final AnimConfig fastConfig;
    public final AnimConfig hideConfig;
    public final EaseManager.EaseStyle mDefaultEaseStyle;
    public final GeometryAnimGLProgram mGeometryAnimGLProgram;
    public WallpaperScenarioRecognition mWallpaperScenarioRecognition;
    public final AnimConfig nextConfig;
    public float nowPos;
    public float nowProgress;
    public final AnimConfig screenOnConfig;
    public final AnimConfig showConfig;
    public final TransitionListener transitionListener;
    public final TransitionListener transitionListenerNoRefresh;

    public GeometryAnimatorProgram(GeometryAnimGLProgram geometryAnimGLProgram) {
        super(geometryAnimGLProgram);
        this.mDefaultEaseStyle = EaseManager.getStyle(-2, 0.95f, 0.5f);
        this.HIDE_IOR = 1.0f;
        this.SHOW_IOR = 0.0f;
        this.MIN_VISIBLE_CHANGE = 1.0E-4f;
        TransitionListener transitionListener = new TransitionListener() { // from class: com.miui.miwallpaper.opengl.geo.GeometryAnimatorProgram.1
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                GeometryAnimatorProgram.this.mWallpaperScenarioRecognition.setScenarioState(428L, true);
                GeometryAnimatorProgram.this.mWallpaperScenarioRecognition.notifyScenarioChanged(2);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                GeometryAnimatorProgram.this.mWallpaperScenarioRecognition.setScenarioState(428L, false);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                GeometryAnimatorProgram.this.mWallpaperScenarioRecognition.setScenarioState(428L, false);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                super.onUpdate(obj, collection);
                if (UpdateInfo.findByName(collection, "posT") != null) {
                    GeometryAnimatorProgram.this.nowPos = UpdateInfo.findByName(collection, "posT").getFloatValue();
                    if (GeometryAnimatorProgram.this.nowPos > 2.1474836E9f) {
                        GeometryAnimatorProgram.this.nowPos = 0.0f;
                    }
                    if (GeometryAnimatorProgram.this.nowPos >= 1.0f) {
                        GeometryAnimatorProgram.this.nowPos -= 1.0f;
                    }
                    GeometryAnimatorProgram.this.mGeometryAnimGLProgram.animateGeoPos(GeometryAnimatorProgram.this.nowPos);
                }
                if (UpdateInfo.findByName(collection, "progress") != null) {
                    GeometryAnimatorProgram.this.nowProgress = UpdateInfo.findByName(collection, "progress").getFloatValue();
                    GeometryAnimatorProgram.this.mGeometryAnimGLProgram.animateGeoIOR(GeometryAnimatorProgram.this.nowProgress);
                }
                GeometryAnimatorProgram.this.refresh();
            }
        };
        this.transitionListener = transitionListener;
        this.transitionListenerNoRefresh = new TransitionListener() { // from class: com.miui.miwallpaper.opengl.geo.GeometryAnimatorProgram.2
            @Override // miuix.animation.listener.TransitionListener
            public void onUpdate(Object obj, Collection collection) {
                super.onUpdate(obj, collection);
                if (UpdateInfo.findByName(collection, "posT") != null) {
                    GeometryAnimatorProgram.this.nowPos = UpdateInfo.findByName(collection, "posT").getFloatValue();
                    if (GeometryAnimatorProgram.this.nowPos > 2.1474836E9f) {
                        GeometryAnimatorProgram.this.nowPos = 0.0f;
                    }
                    if (GeometryAnimatorProgram.this.nowPos >= 1.0f) {
                        GeometryAnimatorProgram.this.nowPos -= 1.0f;
                    }
                    GeometryAnimatorProgram.this.mGeometryAnimGLProgram.animateGeoPos(GeometryAnimatorProgram.this.nowPos);
                }
                if (UpdateInfo.findByName(collection, "progress" + GeometryAnimatorProgram.this.mHashCode) != null) {
                    GeometryAnimatorProgram.this.nowProgress = UpdateInfo.findByName(collection, "progress" + GeometryAnimatorProgram.this.mHashCode).getFloatValue();
                    GeometryAnimatorProgram.this.mGeometryAnimGLProgram.animateGeoIOR(GeometryAnimatorProgram.this.nowProgress);
                }
            }
        };
        this.mWallpaperScenarioRecognition = WallpaperScenarioRecognition.getInstance();
        this.mGeometryAnimGLProgram = geometryAnimGLProgram;
        this.allEffectLinkAnimTarget = Folme.useValue("allEffectLinkAnimTarget" + System.currentTimeMillis());
        AnimConfig animConfig = new AnimConfig();
        this.screenOnConfig = animConfig;
        animConfig.setSpecial("posT", EaseManager.getStyle(-2, 1.0f, 1.6f), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 1.0f, 0.5f), new float[0]);
        animConfig.addListeners(transitionListener);
        AnimConfig animConfig2 = new AnimConfig();
        this.showConfig = animConfig2;
        animConfig2.setSpecial("posT", EaseManager.getStyle(-2, 1.0f, 1.0f), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 1.0f, 0.5f), new float[0]);
        animConfig2.addListeners(transitionListener);
        AnimConfig animConfig3 = new AnimConfig();
        this.hideConfig = animConfig3;
        animConfig3.setSpecial("posT", EaseManager.getStyle(-2, 0.95d, 0.43d), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 1.0f, 0.35f), new float[0]);
        animConfig3.addListeners(transitionListener);
        AnimConfig animConfig4 = new AnimConfig();
        this.fastConfig = animConfig4;
        animConfig4.setSpecial("posT", EaseManager.getStyle(-2, 1.0f, 0.8f), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 1.0f, 0.5f), new float[0]);
        animConfig4.addListeners(transitionListener);
        AnimConfig animConfig5 = new AnimConfig();
        this.nextConfig = animConfig5;
        animConfig5.setSpecial("posT", EaseManager.getStyle(-2, 1.0f, 0.8f), new float[0]).setSpecial("progress", EaseManager.getStyle(-2, 1.0f, 0.8f), new float[0]);
        animConfig5.addListeners(transitionListener);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimatorProgram
    public void diffEffectHideAnim() {
        resetGlassAnim();
        this.allEffectLinkAnimTarget.to(new AnimState().add(new ValueProperty("progress", 1.0E-4f), 1.0d), this.hideConfig);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimatorProgram
    public void diffEffectShowAnim() {
        resetGlassAnim();
        this.allEffectLinkAnimTarget.setTo(new AnimState().add(new ValueProperty("posT", 1.0E-4f), this.nowPos).add(new ValueProperty("progress", 1.0E-4f), 1.0d)).to(new AnimState().add(new ValueProperty("posT", 1.0E-4f), freshNowPos(0.07f)).add(new ValueProperty("progress", 1.0E-4f), BaseStyleSelectView.COLLAPSE_SLIDE_VALUE), this.showConfig);
    }

    public final float freshNowPos(float f) {
        float f2 = this.nowPos;
        if (f2 + f < 1.0f) {
            return f2 + f;
        }
        this.allEffectLinkAnimTarget.setTo(new ValueProperty("posT", 1.0E-4f), Float.valueOf(1.0E-4f));
        return f;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimatorProgram
    public float[] getMatrix(Bitmap bitmap, int i, int i2) {
        this.mGeometryAnimGLProgram.updateMVPMatrix(i, i2, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
        return this.mGeometryAnimGLProgram.getMVPMatrix();
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimatorProgram
    public float[] getScaleXY() {
        return this.mGeometryAnimGLProgram.getScaleXY();
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimatorProgram
    public float[] getTranslateXY() {
        return this.mGeometryAnimGLProgram.getTranslateXY();
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimatorProgram
    public void resetGlassAnim() {
        IStateStyle iStateStyle = this.allEffectLinkAnimTarget;
        if (iStateStyle != null) {
            iStateStyle.cancel();
        }
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimatorProgram
    public void resetHideGlassStatus() {
        this.mGeometryAnimGLProgram.animateGeoIOR(1.0f);
    }
}
