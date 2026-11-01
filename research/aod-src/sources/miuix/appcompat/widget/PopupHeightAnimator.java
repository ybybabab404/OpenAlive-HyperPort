package miuix.appcompat.widget;

import miuix.animation.Folme;
import miuix.animation.FolmeObject;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.FloatProperty;
import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes2.dex */
public class PopupHeightAnimator implements FolmeObject {
    public static final FloatProperty HEIGHT_PROPERTY = new FloatProperty<PopupHeightAnimator>("popupHeight") { // from class: miuix.appcompat.widget.PopupHeightAnimator.1
        @Override // miuix.animation.property.FloatProperty
        public float getValue(PopupHeightAnimator popupHeightAnimator) {
            return popupHeightAnimator.mCurrentHeight;
        }

        @Override // miuix.animation.property.FloatProperty
        public void setValue(PopupHeightAnimator popupHeightAnimator, float f) {
            popupHeightAnimator.mCurrentHeight = f;
            int iRound = Math.round(f);
            if (popupHeightAnimator.mIAnimationListener != null) {
                popupHeightAnimator.mIAnimationListener.onAnimationHeightUpdate(iRound);
            }
        }
    };
    public float mCurrentHeight;
    public Folme.ObjectFolmeImpl mFolmeAnimator;
    public IHeightAnimationListener mIAnimationListener;
    public final AnimState targetState = new AnimState();
    public final AnimConfig mAnimConfig = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.97f, 0.15f)).addListeners(new TransitionListener() { // from class: miuix.appcompat.widget.PopupHeightAnimator.2
        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            if (PopupHeightAnimator.this.mIAnimationListener != null) {
                PopupHeightAnimator.this.mIAnimationListener.onAnimationEnd();
            }
        }
    });

    public interface IHeightAnimationListener {
        void onAnimationEnd();

        void onAnimationHeightUpdate(int i);
    }

    public void animateTo(int i) {
        if (this.mFolmeAnimator == null) {
            this.mFolmeAnimator = Folme.use((FolmeObject) this);
        }
        this.targetState.add(HEIGHT_PROPERTY, i);
        this.mFolmeAnimator.to(this.targetState, this.mAnimConfig);
    }

    @Override // miuix.animation.FolmeObject
    public Folme.ObjectFolmeImpl folme() {
        return this.mFolmeAnimator;
    }

    public float getCurrentHeight() {
        return this.mCurrentHeight;
    }

    public void setAnimationListener(IHeightAnimationListener iHeightAnimationListener) {
        this.mIAnimationListener = iHeightAnimationListener;
    }

    @Override // miuix.animation.FolmeObject
    public void setFolmeImpl(Folme.ObjectFolmeImpl objectFolmeImpl) {
        this.mFolmeAnimator = objectFolmeImpl;
    }

    public void setHeight(int i) {
        this.mCurrentHeight = i;
    }
}
