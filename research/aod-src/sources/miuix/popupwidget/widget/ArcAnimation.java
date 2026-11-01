package miuix.popupwidget.widget;

import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import com.miui.aod.components.view.BaseStyleSelectView;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.util.ArrayList;
import java.util.List;
import miuix.animation.Folme;
import miuix.animation.FolmeEase;
import miuix.animation.FolmeObject;
import miuix.animation.IFolme;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ValueProperty;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;
import miuix.core.util.HyperMaterialUtils;
import miuix.core.util.MiuiBlurUtils;
import miuix.popupwidget.R;

/* JADX INFO: loaded from: classes2.dex */
public class ArcAnimation implements FolmeObject, IPopupAnim {
    public static final ValueProperty POPUP_BLUR;
    public static final ValueProperty POPUP_FRACTION = new ValueProperty<ArcAnimation>("fraction", 0.001f) { // from class: miuix.popupwidget.widget.ArcAnimation.1
        @Override // miuix.animation.property.ValueProperty, miuix.animation.property.FloatProperty
        public float getValue(ArcAnimation arcAnimation) {
            return arcAnimation.mFraction;
        }

        @Override // miuix.animation.property.ValueProperty, miuix.animation.property.FloatProperty
        public void setValue(ArcAnimation arcAnimation, float f) {
            arcAnimation.mFraction = f;
        }
    };
    public static final ValueProperty POPUP_POSITION;
    public final AnimConfig mAnimConfig;
    public final View mAnimContainerView;
    public float mBlur;
    public View mBlurView;
    public final View mContentView;
    public final AnimConfig mEnterAlphaConfig;
    public Folme.ObjectFolmeImpl mFolmeAnimator;
    public float mFraction;
    public final View mOffsetLayer;
    public float mPosition;
    public final View mRootView;
    public ArcAnimationScaleListener mScaleListener;
    public boolean mInAnimation = false;
    public boolean mIsShowAnim = false;
    public int mAnimGravity = 0;
    public final List mPopupListeners = new ArrayList();
    public boolean mBlurEnabled = true;

    static {
        float f = 0.1f;
        POPUP_BLUR = new ValueProperty<ArcAnimation>("popupBlur", f) { // from class: miuix.popupwidget.widget.ArcAnimation.2
            @Override // miuix.animation.property.ValueProperty, miuix.animation.property.FloatProperty
            public float getValue(ArcAnimation arcAnimation) {
                return arcAnimation.mBlur;
            }

            @Override // miuix.animation.property.ValueProperty, miuix.animation.property.FloatProperty
            public void setValue(ArcAnimation arcAnimation, float f2) {
                arcAnimation.mBlur = f2;
            }
        };
        POPUP_POSITION = new ValueProperty<ArcAnimation>("position", f) { // from class: miuix.popupwidget.widget.ArcAnimation.3
            @Override // miuix.animation.property.ValueProperty, miuix.animation.property.FloatProperty
            public float getValue(ArcAnimation arcAnimation) {
                return arcAnimation.mPosition;
            }

            @Override // miuix.animation.property.ValueProperty, miuix.animation.property.FloatProperty
            public void setValue(ArcAnimation arcAnimation, float f2) {
                arcAnimation.mPosition = f2;
            }
        };
    }

    public ArcAnimation(final View view) {
        View rootView = view.getRootView();
        this.mRootView = rootView;
        this.mAnimContainerView = rootView.findViewById(R.id.miuix_popupwidget_popup_anim_container);
        this.mOffsetLayer = rootView.findViewById(R.id.miuix_popupwidget_popup_offset_anim_container);
        this.mEnterAlphaConfig = new AnimConfig().setEase(EaseManager.getStyle(1, 50.0f)).enableStartImmediately(true);
        this.mAnimConfig = new AnimConfig().setEase(EaseManager.getStyle(-2, 0.8f, 0.35f)).addListeners(new TransitionListener() { // from class: miuix.popupwidget.widget.ArcAnimation.4
            @Override // miuix.animation.listener.TransitionListener
            public void onBegin(Object obj) {
                if (ArcAnimation.this.mAnimContainerView instanceof ViewGroup) {
                    ((ViewGroup) ArcAnimation.this.mAnimContainerView).suppressLayout(true);
                }
                view.setLayerType(2, null);
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onCancel(Object obj) {
                view.setLayerType(0, null);
                ArcAnimation.this.mInAnimation = false;
            }

            @Override // miuix.animation.listener.TransitionListener
            public void onComplete(Object obj) {
                if (ArcAnimation.this.mAnimContainerView instanceof ViewGroup) {
                    ((ViewGroup) ArcAnimation.this.mAnimContainerView).suppressLayout(false);
                }
                view.setLayerType(0, null);
                ArcAnimation.this.mInAnimation = false;
            }
        });
        this.mContentView = view;
    }

    @Override // miuix.popupwidget.widget.IPopupAnim
    public void addPopupAnimationListener(IPopupAnimationListener iPopupAnimationListener) {
        this.mPopupListeners.add(iPopupAnimationListener);
    }

    @Override // miuix.popupwidget.widget.IPopupAnim
    public void dismissWithAnim() {
        View view = this.mContentView;
        if (view == null || !view.isAttachedToWindow() || this.mContentView.getParent() == null) {
            notifyReverseEnd();
            return;
        }
        this.mInAnimation = true;
        this.mIsShowAnim = false;
        if (this.mContentView instanceof ViewGroup) {
            Folme.use(this.mContentView).to(ViewProperty.ALPHA, Float.valueOf(0.0f), new AnimConfig().setEase(EaseManager.getStyle(1, 200.0f)).addListeners(new TransitionListener() { // from class: miuix.popupwidget.widget.ArcAnimation.6
                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    super.onComplete(obj);
                    ArcAnimation.this.folme().end();
                }
            }));
            AnimState animStateAdd = new AnimState().add(POPUP_FRACTION, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
            ValueProperty valueProperty = POPUP_POSITION;
            AnimState animStateAdd2 = animStateAdd.add(valueProperty, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
            this.mAnimConfig.setEase(EaseManager.getStyle(-2, 0.8f, 0.22f));
            this.mAnimConfig.setSpecial(valueProperty, FolmeEase.spring(0.8f, 0.35f), new float[0]);
            if (isBlurEnabled()) {
                float f = this.mContentView.getContext().getResources().getDisplayMetrics().density;
                MiuiBlurUtils.setMiSelfBlurEnhanceFlag(getBlurView(), 512, 3840);
                if (HyperMaterialUtils.getVersion() < 5) {
                    MiuiBlurUtils.setMiSelfBlurEnhanceFlag(getBlurView(), AutoBottomSheetKt.ALIGN_SCREEN_TOP, AutoBottomSheetKt.ALIGN_SCREEN_TOP);
                }
                ValueProperty valueProperty2 = POPUP_BLUR;
                animStateAdd2.add(valueProperty2, f * 30.0f);
                this.mAnimConfig.setSpecial(valueProperty2, FolmeEase.linear(200L), new float[0]);
            }
            this.mAnimConfig.addListeners(new TransitionListener() { // from class: miuix.popupwidget.widget.ArcAnimation.7
                @Override // miuix.animation.listener.TransitionListener
                public void onBegin(Object obj) {
                    ArcAnimation.this.notifyReverseStart();
                }

                @Override // miuix.animation.listener.TransitionListener
                public void onComplete(Object obj) {
                    ArcAnimation.this.mAnimConfig.setEase(EaseManager.getStyle(-2, 0.8f, 0.35f));
                    ArcAnimation.this.notifyReverseEnd();
                    ArcAnimation.this.mAnimConfig.removeListeners(this);
                }
            });
            Folme.useValue(this).to(animStateAdd2, this.mAnimConfig);
        }
    }

    @Override // miuix.animation.FolmeObject
    public Folme.ObjectFolmeImpl folme() {
        return this.mFolmeAnimator;
    }

    public View getAnimContainerView() {
        return this.mAnimContainerView;
    }

    public int getAnimGravity() {
        return this.mAnimGravity;
    }

    public View getBlurView() {
        View view = this.mBlurView;
        return view != null ? view : this.mContentView;
    }

    public View getContentView() {
        return this.mContentView;
    }

    public boolean getIsShowAnim() {
        return this.mIsShowAnim;
    }

    public View getOffsetLayer() {
        return this.mOffsetLayer;
    }

    public ValueProperty getPopupBlur() {
        return POPUP_BLUR;
    }

    public boolean isBlurEnabled() {
        return HyperMaterialUtils.isEnable() && this.mBlurEnabled;
    }

    public final void notifyForwardCancel() {
        for (IPopupAnimationListener iPopupAnimationListener : new ArrayList(this.mPopupListeners)) {
            if (iPopupAnimationListener != null) {
                iPopupAnimationListener.onForwardAnimationCancel();
            }
        }
    }

    public final void notifyForwardEnd() {
        for (IPopupAnimationListener iPopupAnimationListener : new ArrayList(this.mPopupListeners)) {
            if (iPopupAnimationListener != null) {
                iPopupAnimationListener.onForwardAnimationEnd();
            }
        }
    }

    public final void notifyForwardStart() {
        for (IPopupAnimationListener iPopupAnimationListener : new ArrayList(this.mPopupListeners)) {
            if (iPopupAnimationListener != null) {
                iPopupAnimationListener.onForwardAnimationStart();
            }
        }
    }

    public final void notifyReverseEnd() {
        for (IPopupAnimationListener iPopupAnimationListener : new ArrayList(this.mPopupListeners)) {
            if (iPopupAnimationListener != null) {
                iPopupAnimationListener.onReverseAnimationEnd();
            }
        }
    }

    public final void notifyReverseStart() {
        for (IPopupAnimationListener iPopupAnimationListener : new ArrayList(this.mPopupListeners)) {
            if (iPopupAnimationListener != null) {
                iPopupAnimationListener.onReverseAnimationStart();
            }
        }
    }

    @Override // miuix.popupwidget.widget.IPopupAnim
    public void removePopupAnimationListener(IPopupAnimationListener iPopupAnimationListener) {
        this.mPopupListeners.remove(iPopupAnimationListener);
    }

    public final void resetSelfBlur() {
        if (MiuiBlurUtils.setSelfBlur(getBlurView(), 0, null, 0)) {
            return;
        }
        MiuiBlurUtils.setSelfBlur(getBlurView(), 0, null);
    }

    @Override // miuix.popupwidget.widget.IPopupAnim
    public void setBlurEnabled(boolean z) {
        this.mBlurEnabled = z;
    }

    @Override // miuix.popupwidget.widget.IPopupAnim
    public void setBlurView(View view) {
        this.mBlurView = view;
    }

    @Override // miuix.animation.FolmeObject
    public void setFolmeImpl(Folme.ObjectFolmeImpl objectFolmeImpl) {
        this.mFolmeAnimator = objectFolmeImpl;
    }

    @Override // miuix.popupwidget.widget.IPopupAnim
    public void setGravity(int i) {
        this.mAnimGravity = i;
    }

    @Override // miuix.popupwidget.widget.IPopupAnim
    public void showWithAnim() {
        View view = this.mContentView;
        if (view == null || view.getParent() == null) {
            return;
        }
        this.mInAnimation = true;
        this.mIsShowAnim = true;
        final int layoutDirection = this.mContentView.getLayoutDirection();
        this.mContentView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: miuix.popupwidget.widget.ArcAnimation.5
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                ArcAnimation.this.mContentView.getViewTreeObserver().removeOnPreDrawListener(this);
                if (!ArcAnimation.this.mContentView.isAttachedToWindow() || ArcAnimation.this.mContentView.getWidth() == 0 || ArcAnimation.this.mContentView.getHeight() == 0) {
                    return true;
                }
                Rect rect = new Rect(ArcAnimation.this.mContentView.getLeft(), ArcAnimation.this.mContentView.getTop(), ArcAnimation.this.mContentView.getRight(), ArcAnimation.this.mContentView.getBottom());
                if (ArcAnimation.this.mScaleListener != null) {
                    ArcAnimation.this.mAnimConfig.removeListeners(ArcAnimation.this.mScaleListener);
                }
                ArcAnimation arcAnimation = ArcAnimation.this;
                arcAnimation.mScaleListener = new ArcAnimationScaleListener(arcAnimation, rect, arcAnimation.mAnimGravity, layoutDirection);
                ArcAnimation.this.mAnimConfig.addListeners(ArcAnimation.this.mScaleListener);
                ArcAnimation.this.mAnimConfig.addListeners(new TransitionListener() { // from class: miuix.popupwidget.widget.ArcAnimation.5.1
                    @Override // miuix.animation.listener.TransitionListener
                    public void onBegin(Object obj) {
                        ArcAnimation.this.notifyForwardStart();
                    }

                    @Override // miuix.animation.listener.TransitionListener
                    public void onCancel(Object obj) {
                        ArcAnimation.this.notifyForwardCancel();
                        ArcAnimation.this.mAnimConfig.removeListeners(this);
                    }

                    @Override // miuix.animation.listener.TransitionListener
                    public void onComplete(Object obj) {
                        ArcAnimation.this.notifyForwardEnd();
                        ArcAnimation.this.mAnimConfig.removeListeners(this);
                    }
                });
                IFolme iFolmeUse = Folme.use(ArcAnimation.this.mContentView);
                ViewProperty viewProperty = ViewProperty.ALPHA;
                iFolmeUse.resetTo(viewProperty, Float.valueOf(0.0f)).to(viewProperty, Float.valueOf(1.0f), ArcAnimation.this.mEnterAlphaConfig);
                AnimState animStateAdd = new AnimState().add(ArcAnimation.POPUP_FRACTION, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE).add(ArcAnimation.POPUP_POSITION, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
                AnimState animStateAdd2 = new AnimState("end").add(ArcAnimation.POPUP_FRACTION, 1.0d).add(ArcAnimation.POPUP_POSITION, 1.0d);
                ArcAnimation.this.mAnimConfig.setSpecial(ArcAnimation.POPUP_POSITION, FolmeEase.spring(0.8f, 0.22f), new float[0]);
                if (ArcAnimation.this.isBlurEnabled()) {
                    animStateAdd.add(ArcAnimation.POPUP_BLUR, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
                    ArcAnimation.this.resetSelfBlur();
                }
                ArcAnimation.this.mScaleListener.resetRadius();
                Folme.use((FolmeObject) ArcAnimation.this);
                ArcAnimation.this.folme().resetTo(animStateAdd).to(animStateAdd2, ArcAnimation.this.mAnimConfig);
                return false;
            }
        });
    }

    @Override // miuix.popupwidget.widget.IPopupAnim
    public void update(int i) {
        if (this.mInAnimation) {
            return;
        }
        this.mAnimGravity = i;
        Rect rect = new Rect(this.mContentView.getLeft(), this.mContentView.getTop(), this.mContentView.getRight(), this.mContentView.getBottom());
        ArcAnimationScaleListener arcAnimationScaleListener = this.mScaleListener;
        if (arcAnimationScaleListener != null) {
            arcAnimationScaleListener.updateScaleBounds(rect, i, this.mContentView.getLayoutDirection());
        }
    }
}
