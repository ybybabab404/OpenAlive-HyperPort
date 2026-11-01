package miuix.popupwidget.widget;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import androidx.annotation.RequiresApi;
import com.miui.aod.components.view.BaseStyleSelectView;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import miuix.animation.Folme;
import miuix.animation.FolmeEase;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.listener.TransitionListener;
import miuix.animation.property.ValueProperty;
import miuix.core.util.MiuiBlurUtils;
import miuix.view.GhostView;
import miuix.view.ITransformableView;

/* JADX INFO: loaded from: classes2.dex */
@RequiresApi
public class TransformAnimation implements IPopupAnim {
    public AnimState mEndState;
    public final AnimConfig mForwardAnimConfig;
    public final ITransformableView mFromTransformable;
    public final AnimConfig mReverseAnimConfig;
    public boolean mSimplified;
    public AnimState mStartState;
    public final ITransformableView mToTransformable;
    public final List mPopupListeners = new ArrayList();
    public final int[] loc = new int[2];
    public boolean mCrossWindow = false;
    public boolean mAnimationOptionsLocked = false;
    public final AnimationOptions mOptions = new AnimationOptions();

    /* JADX INFO: renamed from: miuix.popupwidget.widget.TransformAnimation$2, reason: invalid class name */
    public class AnonymousClass2 extends TransitionListener {
        public final /* synthetic */ View val$fromContentView;
        public final /* synthetic */ GhostView val$ghostView;
        public final /* synthetic */ View val$toContainerView;
        public final /* synthetic */ View val$toContentView;

        public AnonymousClass2(View view, GhostView ghostView, View view2, View view3) {
            this.val$toContentView = view;
            this.val$ghostView = ghostView;
            this.val$fromContentView = view2;
            this.val$toContainerView = view3;
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onBegin(Object obj) {
            TransformAnimation.this.notifyReverseStart();
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onCancel(Object obj) {
            View view = this.val$toContentView;
            if (view instanceof ViewGroup) {
                ((ViewGroup) view).suppressLayout(false);
            }
            TransformAnimation.this.mReverseAnimConfig.removeListeners(this);
            this.val$toContentView.setTranslationX(0.0f);
            this.val$toContentView.setTranslationY(0.0f);
            TransformAnimation.this.mAnimationOptionsLocked = false;
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onComplete(Object obj) {
            View view = this.val$toContentView;
            if (view instanceof ViewGroup) {
                ((ViewGroup) view).suppressLayout(false);
            }
            this.val$fromContentView.setVisibility(0);
            TransformAnimation.this.mReverseAnimConfig.removeListeners(this);
            this.val$toContainerView.setTranslationX(0.0f);
            this.val$toContainerView.setTranslationY(0.0f);
            TransformAnimation.this.mFromTransformable.restoreAppearance();
            TransformAnimation.this.mAnimationOptionsLocked = false;
            GhostView ghostView = this.val$ghostView;
            if (ghostView != null) {
                ghostView.removeGhost();
            }
            this.val$toContainerView.setAlpha(0.0f);
            this.val$toContainerView.post(new Runnable() { // from class: miuix.popupwidget.widget.TransformAnimation$2$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    TransformAnimation.this.notifyReverseEnd();
                }
            });
        }

        @Override // miuix.animation.listener.TransitionListener
        public void onUpdate(Object obj, Collection collection) {
            TransformAnimation.this.mOptions.updateToContainer(TransformAnimation.this.mToTransformable);
            TransformAnimation.this.mOptions.updateToContent(this.val$toContentView);
            TransformAnimation.this.mOptions.updateFromContent(this.val$ghostView);
            TransformAnimation.this.mOptions.updateFloatingAlpha(TransformAnimation.this.mFromTransformable, TransformAnimation.this.mToTransformable);
            if (!TransformAnimation.this.mSimplified || TransformAnimation.this.mOptions.fraction > 0.0f) {
                return;
            }
            Folme.use(TransformAnimation.this.mOptions).end();
        }
    }

    public static class AnimationOptions {
        public static final ValueProperty CONTAINER_CENTER_X;
        public static final ValueProperty CONTAINER_CENTER_Y;
        public int bottom;
        public float centerX;
        public float centerY;
        public float containerHeight;
        public float containerWidth;
        public float contentAlpha;
        public int contentBlur;
        public float contentMaterialFraction;
        public float contentScaleX;
        public float contentScaleY;
        public float endCenterX;
        public float endCenterY;
        public int endHeight;
        public int endRadius;
        public int endWidth;
        public float fraction;
        public float fromContentAlpha;
        public int fromContentBlur;
        public float fromContentScaleX;
        public float iconMaterialFraction;
        public int left;
        public int radius;
        public int right;
        public float startCenterX;
        public float startCenterY;
        public int startFromContentBottom;
        public int startFromContentLeft;
        public int startFromContentRight;
        public int startFromContentTop;
        public int startHeight;
        public int startRadius;
        public int startWidth;
        public int top;
        public static final ValueProperty POPUP_FRACTION = new ValueProperty<AnimationOptions>("fraction") { // from class: miuix.popupwidget.widget.TransformAnimation.AnimationOptions.1
            @Override // miuix.animation.property.ValueProperty, miuix.animation.property.FloatProperty
            public float getValue(AnimationOptions animationOptions) {
                return animationOptions.fraction;
            }

            @Override // miuix.animation.property.ValueProperty, miuix.animation.property.FloatProperty
            public void setValue(AnimationOptions animationOptions, float f) {
                animationOptions.fraction = f;
                animationOptions.containerWidth = ((animationOptions.endWidth - animationOptions.startWidth) * f) + animationOptions.startWidth;
                animationOptions.containerHeight = ((animationOptions.endHeight - animationOptions.startHeight) * f) + animationOptions.startHeight;
                animationOptions.radius = (int) (((animationOptions.endRadius - animationOptions.startRadius) * f) + animationOptions.startRadius);
                animationOptions.contentScaleX = animationOptions.containerWidth / animationOptions.endWidth;
                animationOptions.contentScaleY = animationOptions.contentScaleX;
                animationOptions.fromContentScaleX = animationOptions.containerWidth / animationOptions.startWidth;
            }
        };
        public static final ValueProperty ICON_MATERIAL = new ValueProperty<AnimationOptions>("icon_material") { // from class: miuix.popupwidget.widget.TransformAnimation.AnimationOptions.2
            @Override // miuix.animation.property.ValueProperty, miuix.animation.property.FloatProperty
            public float getValue(AnimationOptions animationOptions) {
                return animationOptions.iconMaterialFraction;
            }

            @Override // miuix.animation.property.ValueProperty, miuix.animation.property.FloatProperty
            public void setValue(AnimationOptions animationOptions, float f) {
                animationOptions.iconMaterialFraction = f;
                animationOptions.fromContentAlpha = ((-1.0f) * f) + 1.0f;
                animationOptions.fromContentBlur = (int) ((50 * f) + 0.0f);
            }
        };
        public static final ValueProperty CONTENT_MATERIAL = new ValueProperty<AnimationOptions>("content_material") { // from class: miuix.popupwidget.widget.TransformAnimation.AnimationOptions.3
            @Override // miuix.animation.property.ValueProperty, miuix.animation.property.FloatProperty
            public float getValue(AnimationOptions animationOptions) {
                return animationOptions.contentMaterialFraction;
            }

            @Override // miuix.animation.property.ValueProperty, miuix.animation.property.FloatProperty
            public void setValue(AnimationOptions animationOptions, float f) {
                animationOptions.contentMaterialFraction = f;
                animationOptions.contentAlpha = (1.0f * f) + 0.0f;
                animationOptions.contentBlur = (int) (((-50) * f) + 50.0f);
            }
        };
        public final float startContentAlpha = 0.0f;
        public final float endContentAlpha = 1.0f;
        public final float startFromContentAlpha = 1.0f;
        public final float endFromContentAlpha = 0.0f;
        public final int startContentBlur = 50;
        public final int endContentBlur = 0;
        public final int startFromContentBlur = 0;
        public final int endFromContentBlur = 50;

        static {
            float f = 1.0f;
            CONTAINER_CENTER_X = new ValueProperty<AnimationOptions>("container_center_x", f) { // from class: miuix.popupwidget.widget.TransformAnimation.AnimationOptions.4
                @Override // miuix.animation.property.ValueProperty, miuix.animation.property.FloatProperty
                public float getValue(AnimationOptions animationOptions) {
                    return animationOptions.centerX;
                }

                @Override // miuix.animation.property.ValueProperty, miuix.animation.property.FloatProperty
                public void setValue(AnimationOptions animationOptions, float f2) {
                    animationOptions.centerX = f2;
                }
            };
            CONTAINER_CENTER_Y = new ValueProperty<AnimationOptions>("container_center_y", f) { // from class: miuix.popupwidget.widget.TransformAnimation.AnimationOptions.5
                @Override // miuix.animation.property.ValueProperty, miuix.animation.property.FloatProperty
                public float getValue(AnimationOptions animationOptions) {
                    return animationOptions.centerY;
                }

                @Override // miuix.animation.property.ValueProperty, miuix.animation.property.FloatProperty
                public void setValue(AnimationOptions animationOptions, float f2) {
                    animationOptions.centerY = f2;
                }
            };
        }

        public void updateFloatingAlpha(ITransformableView iTransformableView, ITransformableView iTransformableView2) {
            View containerView = iTransformableView2.getContainerView();
            if (iTransformableView.isFloating()) {
                containerView.setAlpha(1.0f);
            } else {
                containerView.setAlpha(this.iconMaterialFraction);
            }
        }

        public void updateFromContent(GhostView ghostView) {
            if (ghostView == null) {
                return;
            }
            int iRound = Math.round(this.centerX - this.startCenterX);
            int iRound2 = Math.round(this.centerY - this.startCenterY);
            int i = this.startFromContentLeft + iRound;
            int i2 = this.startFromContentTop + iRound2;
            int i3 = this.startFromContentRight + iRound;
            int i4 = this.startFromContentBottom + iRound2;
            ghostView.setTranslationX(i - ghostView.getLeft());
            ghostView.setTranslationY(i2 - ghostView.getTop());
            ghostView.setAlpha(this.fromContentAlpha);
            ghostView.setScaleX(this.fromContentScaleX);
            ghostView.setPivotX((i3 - i) / 2.0f);
            ghostView.setPivotY((i4 - i2) / 2.0f);
            MiuiBlurUtils.setSelfBlur(ghostView, this.fromContentBlur, null);
        }

        public void updateToContainer(ITransformableView iTransformableView) {
            float f = this.centerX - (this.containerWidth / 2.0f);
            float f2 = this.centerY - (this.containerHeight / 2.0f);
            this.left = Math.round(f);
            this.top = Math.round(f2);
            this.right = Math.round(this.left + this.containerWidth);
            this.bottom = Math.round(this.top + this.containerHeight);
            View containerView = iTransformableView.getContainerView();
            View contentView = iTransformableView.getContentView();
            containerView.setLeftTopRightBottom(this.left, this.top, this.right, this.bottom);
            contentView.setTranslationX(f - this.left);
            contentView.setTranslationY(f2 - this.top);
            iTransformableView.setCornerRadius(this.radius);
        }

        public void updateToContent(View view) {
            view.setPivotX(0.0f);
            view.setPivotY(0.0f);
            view.setScaleX(this.contentScaleX);
            view.setScaleY(this.contentScaleY);
            view.setAlpha(this.contentAlpha);
            MiuiBlurUtils.setSelfBlur(view, this.contentBlur, null);
        }
    }

    public TransformAnimation(ITransformableView iTransformableView, ITransformableView iTransformableView2) {
        this.mSimplified = false;
        this.mFromTransformable = iTransformableView;
        this.mToTransformable = iTransformableView2;
        this.mSimplified = iTransformableView.isSimplified() || iTransformableView2.isSimplified();
        this.mForwardAnimConfig = new AnimConfig().setEase(FolmeEase.spring(0.85f, 0.38f)).setSpecial(AnimationOptions.CONTAINER_CENTER_X, FolmeEase.spring(0.8f, 0.25f), new float[0]).setSpecial(AnimationOptions.CONTAINER_CENTER_Y, FolmeEase.spring(0.8f, 0.25f), new float[0]).setSpecial(AnimationOptions.ICON_MATERIAL, FolmeEase.linear(80L), new float[0]).setSpecial(AnimationOptions.CONTENT_MATERIAL, FolmeEase.linear(80L), 50L, new float[0]);
        this.mReverseAnimConfig = new AnimConfig().setEase(FolmeEase.spring(0.8f, 0.28f)).setSpecial(AnimationOptions.CONTAINER_CENTER_X, FolmeEase.spring(0.8f, 0.4f), new float[0]).setSpecial(AnimationOptions.CONTAINER_CENTER_Y, FolmeEase.spring(0.8f, 0.4f), new float[0]).setSpecial(AnimationOptions.ICON_MATERIAL, FolmeEase.linear(80L), 50L, new float[0]).setSpecial(AnimationOptions.CONTENT_MATERIAL, FolmeEase.linear(80L), new float[0]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyForwardCancel() {
        for (IPopupAnimationListener iPopupAnimationListener : new ArrayList(this.mPopupListeners)) {
            if (iPopupAnimationListener != null) {
                iPopupAnimationListener.onForwardAnimationCancel();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyForwardEnd() {
        for (IPopupAnimationListener iPopupAnimationListener : new ArrayList(this.mPopupListeners)) {
            if (iPopupAnimationListener != null) {
                iPopupAnimationListener.onForwardAnimationEnd();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyForwardStart() {
        for (IPopupAnimationListener iPopupAnimationListener : new ArrayList(this.mPopupListeners)) {
            if (iPopupAnimationListener != null) {
                iPopupAnimationListener.onForwardAnimationStart();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyReverseEnd() {
        for (IPopupAnimationListener iPopupAnimationListener : new ArrayList(this.mPopupListeners)) {
            if (iPopupAnimationListener != null) {
                iPopupAnimationListener.onReverseAnimationEnd();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void notifyReverseStart() {
        for (IPopupAnimationListener iPopupAnimationListener : new ArrayList(this.mPopupListeners)) {
            if (iPopupAnimationListener != null) {
                iPopupAnimationListener.onReverseAnimationStart();
            }
        }
    }

    @Override // miuix.popupwidget.widget.IPopupAnim
    public void addPopupAnimationListener(IPopupAnimationListener iPopupAnimationListener) {
        this.mPopupListeners.add(iPopupAnimationListener);
    }

    public final int[] computeCoordinates(View view, View view2, View view3) {
        int i;
        int i2;
        int i3;
        int i4;
        if (this.mCrossWindow) {
            view.getLocationOnScreen(this.loc);
            int[] iArr = this.loc;
            i = iArr[0];
            i2 = iArr[1];
            view2.getLocationOnScreen(iArr);
            int[] iArr2 = this.loc;
            int i5 = iArr2[0];
            int i6 = iArr2[1];
            view3.getLocationOnScreen(iArr2);
            int[] iArr3 = this.loc;
            i3 = iArr3[0];
            i4 = iArr3[1];
            view3.getLocationInWindow(iArr3);
            int[] iArr4 = this.loc;
            int i7 = i3 - iArr4[0];
            int i8 = i4 - iArr4[1];
            this.mOptions.startFromContentLeft = i5 - i7;
            this.mOptions.startFromContentTop = i6 - i8;
            AnimationOptions animationOptions = this.mOptions;
            animationOptions.startFromContentRight = animationOptions.startFromContentLeft + view2.getWidth();
            AnimationOptions animationOptions2 = this.mOptions;
            animationOptions2.startFromContentBottom = animationOptions2.startFromContentTop + view2.getHeight();
        } else {
            view.getLocationInWindow(this.loc);
            int[] iArr5 = this.loc;
            i = iArr5[0];
            i2 = iArr5[1];
            view2.getLocationInWindow(iArr5);
            this.mOptions.startFromContentLeft = this.loc[0];
            this.mOptions.startFromContentTop = this.loc[1];
            this.mOptions.startFromContentRight = this.loc[0] + view2.getWidth();
            this.mOptions.startFromContentBottom = this.loc[1] + view2.getHeight();
            view3.getLocationInWindow(this.loc);
            int[] iArr6 = this.loc;
            i3 = iArr6[0];
            i4 = iArr6[1];
        }
        return new int[]{i, i2, i3, i4};
    }

    @Override // miuix.popupwidget.widget.IPopupAnim
    public void dismissWithAnim() {
        GhostView ghostViewAddGhost;
        View containerView = this.mToTransformable.getContainerView();
        View contentView = this.mToTransformable.getContentView();
        View containerView2 = this.mFromTransformable.getContainerView();
        View contentView2 = this.mFromTransformable.getContentView();
        if (containerView == null || contentView == null || containerView2 == null || contentView2 == null) {
            notifyReverseEnd();
            return;
        }
        if (this.mStartState == null) {
            notifyReverseEnd();
            return;
        }
        this.mAnimationOptionsLocked = true;
        this.mFromTransformable.clearAppearance();
        View elevationView = this.mToTransformable.getElevationView();
        if (elevationView != null) {
            this.mFromTransformable.applyTransformElevation(elevationView);
        }
        if (this.mSimplified) {
            ghostViewAddGhost = null;
        } else {
            ghostViewAddGhost = GhostView.addGhost(contentView2, (ViewGroup) containerView.getRootView());
            contentView2.setVisibility(4);
        }
        this.mReverseAnimConfig.addListeners(new AnonymousClass2(contentView, ghostViewAddGhost, contentView2, containerView));
        if (contentView instanceof ViewGroup) {
            ((ViewGroup) contentView).suppressLayout(true);
        }
        Folme.use(this.mOptions).to(this.mStartState, this.mReverseAnimConfig);
    }

    @Override // miuix.popupwidget.widget.IPopupAnim
    public void removePopupAnimationListener(IPopupAnimationListener iPopupAnimationListener) {
        this.mPopupListeners.remove(iPopupAnimationListener);
    }

    public void resetFromContentView() {
        View contentView = this.mFromTransformable.getContentView();
        if (contentView != null) {
            contentView.setVisibility(0);
        }
        this.mFromTransformable.restoreAppearance();
    }

    public void setCrossWindow(boolean z) {
        this.mCrossWindow = z;
    }

    @Override // miuix.popupwidget.widget.IPopupAnim
    public void showWithAnim() {
        final GhostView ghostViewAddGhost;
        final View containerView = this.mToTransformable.getContainerView();
        final View contentView = this.mToTransformable.getContentView();
        View containerView2 = this.mFromTransformable.getContainerView();
        View contentView2 = this.mFromTransformable.getContentView();
        if (containerView == null || contentView == null || containerView2 == null || contentView2 == null) {
            return;
        }
        this.mFromTransformable.captureAppearance();
        if (this.mSimplified) {
            ghostViewAddGhost = null;
        } else {
            ghostViewAddGhost = GhostView.addGhost(contentView2, (ViewGroup) containerView.getRootView());
            contentView2.setVisibility(4);
        }
        containerView.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() { // from class: miuix.popupwidget.widget.TransformAnimation.1
            @Override // android.view.ViewTreeObserver.OnPreDrawListener
            public boolean onPreDraw() {
                containerView.getViewTreeObserver().removeOnPreDrawListener(this);
                TransformAnimation.this.mFromTransformable.clearAppearance();
                TransformAnimation.this.updateAnimationOptions();
                if (TransformAnimation.this.mOptions.startWidth != 0 && TransformAnimation.this.mOptions.endWidth != 0 && TransformAnimation.this.mOptions.startHeight != 0 && TransformAnimation.this.mOptions.endHeight != 0) {
                    TransformAnimation.this.mAnimationOptionsLocked = true;
                    TransformAnimation.this.mForwardAnimConfig.addListeners(new TransitionListener() { // from class: miuix.popupwidget.widget.TransformAnimation.1.1
                        @Override // miuix.animation.listener.TransitionListener
                        public void onBegin(Object obj) {
                            TransformAnimation.this.notifyForwardStart();
                        }

                        @Override // miuix.animation.listener.TransitionListener
                        public void onCancel(Object obj) {
                            TransformAnimation.this.mForwardAnimConfig.removeListeners(this);
                            contentView.setTranslationX(0.0f);
                            contentView.setTranslationY(0.0f);
                            TransformAnimation.this.mAnimationOptionsLocked = false;
                            TransformAnimation.this.notifyForwardCancel();
                        }

                        @Override // miuix.animation.listener.TransitionListener
                        public void onComplete(Object obj) {
                            TransformAnimation.this.mForwardAnimConfig.removeListeners(this);
                            contentView.setTranslationX(0.0f);
                            contentView.setTranslationY(0.0f);
                            GhostView ghostView = ghostViewAddGhost;
                            if (ghostView != null) {
                                ghostView.removeGhost();
                            }
                            TransformAnimation.this.mAnimationOptionsLocked = false;
                            TransformAnimation.this.notifyForwardEnd();
                        }

                        @Override // miuix.animation.listener.TransitionListener
                        public void onUpdate(Object obj, Collection collection) {
                            TransformAnimation.this.mOptions.updateToContainer(TransformAnimation.this.mToTransformable);
                            TransformAnimation.this.mOptions.updateToContent(contentView);
                            TransformAnimation.this.mOptions.updateFromContent(ghostViewAddGhost);
                            TransformAnimation.this.mOptions.updateFloatingAlpha(TransformAnimation.this.mFromTransformable, TransformAnimation.this.mToTransformable);
                        }
                    });
                    Folme.use(TransformAnimation.this.mOptions).setTo(TransformAnimation.this.mStartState).to(TransformAnimation.this.mEndState, TransformAnimation.this.mForwardAnimConfig);
                }
                return false;
            }
        });
    }

    @Override // miuix.popupwidget.widget.IPopupAnim
    public void update(int i) {
    }

    public final void updateAnimState() {
        this.mStartState = new AnimState().add(AnimationOptions.POPUP_FRACTION, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE).add(AnimationOptions.CONTAINER_CENTER_X, this.mOptions.startCenterX).add(AnimationOptions.CONTAINER_CENTER_Y, this.mOptions.startCenterY).add(AnimationOptions.CONTENT_MATERIAL, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE).add(AnimationOptions.ICON_MATERIAL, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
        this.mEndState = new AnimState().add(AnimationOptions.POPUP_FRACTION, 1.0d).add(AnimationOptions.CONTAINER_CENTER_X, this.mOptions.endCenterX).add(AnimationOptions.CONTAINER_CENTER_Y, this.mOptions.endCenterY).add(AnimationOptions.CONTENT_MATERIAL, 1.0d).add(AnimationOptions.ICON_MATERIAL, 1.0d);
    }

    public void updateAnimationOptions() {
        if (this.mAnimationOptionsLocked) {
            return;
        }
        View containerView = this.mToTransformable.getContainerView();
        View contentView = this.mFromTransformable.getContentView();
        View containerView2 = this.mFromTransformable.getContainerView();
        if (containerView == null || containerView2 == null || contentView == null) {
            return;
        }
        int width = containerView2.getWidth();
        int height = containerView2.getHeight();
        int width2 = containerView.getWidth();
        int height2 = containerView.getHeight();
        int[] iArrComputeCoordinates = computeCoordinates(containerView2, contentView, containerView);
        int i = iArrComputeCoordinates[0];
        int i2 = iArrComputeCoordinates[1];
        int i3 = iArrComputeCoordinates[2] - i;
        int i4 = iArrComputeCoordinates[3] - i2;
        int left = containerView.getLeft();
        int top = containerView.getTop();
        int right = containerView.getRight();
        int bottom = containerView.getBottom();
        int i5 = left - i3;
        int i6 = top - i4;
        float f = (right + left) / 2.0f;
        float f2 = (top + bottom) / 2.0f;
        int cornerRadius = (int) this.mFromTransformable.getCornerRadius();
        int cornerRadius2 = (int) this.mToTransformable.getCornerRadius();
        this.mOptions.startWidth = width;
        this.mOptions.startHeight = height;
        this.mOptions.endWidth = width2;
        this.mOptions.endHeight = height2;
        this.mOptions.startCenterX = ((i5 + width) + i5) / 2.0f;
        this.mOptions.startCenterY = (i6 + (i6 + height)) / 2.0f;
        this.mOptions.endCenterX = f;
        this.mOptions.endCenterY = f2;
        this.mOptions.startRadius = cornerRadius;
        this.mOptions.endRadius = cornerRadius2;
        this.mOptions.containerWidth = width2;
        this.mOptions.containerHeight = height2;
        this.mOptions.centerX = f;
        this.mOptions.centerY = f2;
        this.mOptions.left = left;
        this.mOptions.top = top;
        this.mOptions.right = right;
        this.mOptions.bottom = bottom;
        this.mOptions.radius = cornerRadius2;
        updateAnimState();
    }
}
