package com.miui.miwallpaper.opengl.mix;

import com.miui.miwallpaper.opengl.GlassAnimatorProgram;
import java.util.Collection;
import miuix.animation.IStateStyle;
import miuix.animation.listener.TransitionListener;
import miuix.animation.listener.UpdateInfo;

/* JADX INFO: loaded from: classes2.dex */
public class MixTextureAnimator extends GlassAnimatorProgram {
    public IStateStyle blurWallpaperAnimTarget;
    public IStateStyle clearBlurWallpaperAnimTarget;
    public final MixTextureProgram mixTextureProgram;
    public IStateStyle mixWallpaperAnimTarget;
    public volatile float nowBlurRadius;
    public volatile float nowMixFactor;

    /* JADX INFO: renamed from: com.miui.miwallpaper.opengl.mix.MixTextureAnimator$1, reason: invalid class name */
    public class AnonymousClass1 extends TransitionListener {
        public final /* synthetic */ MixTextureAnimator this$0;

        @Override // miuix.animation.listener.TransitionListener
        public void onBegin(Object obj) {
            super.onBegin(obj);
            this.this$0.isFolmeAnimIsRuning = true;
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            this.this$0.isFolmeAnimIsRuning = false;
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onUpdate(Object obj, Collection collection) {
            super.onUpdate(obj, collection);
            if (UpdateInfo.findByName(collection, "mixWallpaperAnimTarget") != null) {
                this.this$0.nowMixFactor = UpdateInfo.findByName(collection, "mixWallpaperAnimTarget").getFloatValue();
                this.this$0.mixTextureProgram.updateMixtextureStatus(this.this$0.nowMixFactor, this.this$0.nowBlurRadius, true);
            }
            if (UpdateInfo.findByName(collection, "blurWallpaperAnimTarget") != null) {
                this.this$0.nowBlurRadius = UpdateInfo.findByName(collection, "blurWallpaperAnimTarget").getFloatValue();
                this.this$0.mixTextureProgram.updateMixtextureStatus(this.this$0.nowMixFactor, this.this$0.nowBlurRadius, true);
            }
            if (UpdateInfo.findByName(collection, "clearBlurWallpaperAnimTarget") != null) {
                this.this$0.nowBlurRadius = UpdateInfo.findByName(collection, "clearBlurWallpaperAnimTarget").getFloatValue();
                this.this$0.mixTextureProgram.updateMixtextureStatus(this.this$0.nowMixFactor, this.this$0.nowBlurRadius, true);
            }
            this.this$0.refresh();
        }
    }

    /* JADX INFO: renamed from: com.miui.miwallpaper.opengl.mix.MixTextureAnimator$2, reason: invalid class name */
    public class AnonymousClass2 extends TransitionListener {
        public final /* synthetic */ MixTextureAnimator this$0;

        @Override // miuix.animation.listener.TransitionListener
        public void onBegin(Object obj) {
            super.onBegin(obj);
            this.this$0.isFolmeAnimIsRuning = true;
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            super.onComplete(obj);
            this.this$0.isFolmeAnimIsRuning = false;
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onUpdate(Object obj, Collection collection) {
            super.onUpdate(obj, collection);
            if (UpdateInfo.findByName(collection, "mixWallpaperAnimTarget") != null) {
                this.this$0.nowMixFactor = UpdateInfo.findByName(collection, "mixWallpaperAnimTarget").getFloatValue();
                this.this$0.mixTextureProgram.updateMixtextureStatus(this.this$0.nowMixFactor, this.this$0.nowBlurRadius, true);
            }
            if (UpdateInfo.findByName(collection, "blurWallpaperAnimTarget") != null) {
                this.this$0.nowBlurRadius = UpdateInfo.findByName(collection, "blurWallpaperAnimTarget").getFloatValue();
                this.this$0.mixTextureProgram.updateMixtextureStatus(this.this$0.nowMixFactor, this.this$0.nowBlurRadius, true);
            }
            if (UpdateInfo.findByName(collection, "clearBlurWallpaperAnimTarget") != null) {
                this.this$0.nowBlurRadius = UpdateInfo.findByName(collection, "clearBlurWallpaperAnimTarget").getFloatValue();
                this.this$0.mixTextureProgram.updateMixtextureStatus(this.this$0.nowMixFactor, this.this$0.nowBlurRadius, true);
            }
        }
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimatorProgram
    public void resetGlassAnim() {
        this.mixWallpaperAnimTarget.cancel();
        this.blurWallpaperAnimTarget.cancel();
        this.clearBlurWallpaperAnimTarget.cancel();
        resetFrameCount();
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimatorProgram
    public void resetHideGlassStatus() {
        this.mixTextureProgram.updateMixtextureStatus(1.0f, 1.0f, false);
    }
}
