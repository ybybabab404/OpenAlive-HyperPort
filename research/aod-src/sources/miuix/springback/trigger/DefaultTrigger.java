package miuix.springback.trigger;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.miui.aod.components.view.BaseStyleSelectView;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.animation.controller.AnimState;
import miuix.animation.property.ViewProperty;
import miuix.animation.utils.EaseManager;
import miuix.springback.view.SpringBackLayout;

/* JADX INFO: loaded from: classes.dex */
public class DefaultTrigger extends CustomTrigger {
    public static int mIndeterminateTop;
    public ProgressBar mLoadingIndicator;
    public View mTrackingIndicator;
    public int mTrackingIndicatorBottom;
    public TextView mTrackingIndicatorLabel;
    public int mTrackingIndicatorLabelBottom;
    public int mTrackingIndicatorLabelTop;
    public ProgressBar mUpLoadingIndicator;
    public ViewGroup mUpTrackingContainer;
    public View mUpTrackingIndicator;
    public TextView mUpTrackingIndicatorLabel;

    /* JADX INFO: renamed from: miuix.springback.trigger.DefaultTrigger$1, reason: invalid class name */
    public class AnonymousClass1 implements CustomTrigger.OnIndeterminateActionDataListener {
    }

    /* JADX INFO: renamed from: miuix.springback.trigger.DefaultTrigger$2, reason: invalid class name */
    public class AnonymousClass2 implements CustomTrigger.OnIndeterminateUpActionDataListener {
    }

    /* JADX INFO: renamed from: miuix.springback.trigger.DefaultTrigger$3, reason: invalid class name */
    public class AnonymousClass3 implements BaseTrigger.IndeterminateAction.OnIndeterminateActionViewListener {
        public final /* synthetic */ DefaultTrigger this$0;

        @Override // miuix.springback.trigger.BaseTrigger.IndeterminateAction.OnIndeterminateActionViewListener
        public float getViewRestartOffsetPoint() {
            return -1.0f;
        }

        @Override // miuix.springback.trigger.BaseTrigger.IndeterminateAction.OnIndeterminateActionViewListener
        public void onViewActivated(int i) {
        }

        @Override // miuix.springback.trigger.BaseTrigger.IndeterminateAction.OnIndeterminateActionViewListener
        public void onViewActivating(int i) {
        }

        @Override // miuix.springback.trigger.BaseTrigger.IndeterminateAction.OnIndeterminateActionViewListener
        public void onViewEntered(int i) {
            this.this$0.mTrackingIndicator.setVisibility(0);
            this.this$0.mTrackingIndicatorLabel.setVisibility(0);
            if (this.this$0.isExitSimpleAction()) {
                this.this$0.getIndicatorContainer().setVisibility(8);
            }
        }

        @Override // miuix.springback.trigger.BaseTrigger.IndeterminateAction.OnIndeterminateActionViewListener
        public void onViewEntering(int i) {
        }

        @Override // miuix.springback.trigger.BaseTrigger.IndeterminateAction.OnIndeterminateActionViewListener
        public void onViewExit(int i) {
        }

        @Override // miuix.springback.trigger.BaseTrigger.IndeterminateAction.OnIndeterminateActionViewListener
        public void onViewFinished(int i) {
        }

        @Override // miuix.springback.trigger.BaseTrigger.IndeterminateAction.OnIndeterminateActionViewListener
        public void onViewStart(int i) {
            this.this$0.mLoadingIndicator.setVisibility(8);
            this.this$0.mTrackingIndicator.setVisibility(0);
            this.this$0.mTrackingIndicatorLabel.setVisibility(0);
            BaseTrigger.IndeterminateAction indeterminateAction = this.this$0.getIndeterminateAction();
            if (indeterminateAction != null) {
                this.this$0.mTrackingIndicatorLabel.setText(indeterminateAction.mTriggerTexts[0]);
            }
        }

        @Override // miuix.springback.trigger.BaseTrigger.IndeterminateAction.OnIndeterminateActionViewListener
        public void onViewStarting(int i) {
        }

        @Override // miuix.springback.trigger.BaseTrigger.IndeterminateAction.OnIndeterminateActionViewListener
        public void onViewTriggered(int i) {
            this.this$0.mLoadingIndicator.setVisibility(0);
            this.this$0.mTrackingIndicator.setVisibility(0);
            this.this$0.mTrackingIndicatorLabel.setVisibility(0);
            BaseTrigger.IndeterminateAction indeterminateAction = this.this$0.getIndeterminateAction();
            if (indeterminateAction != null) {
                this.this$0.mTrackingIndicatorLabel.setText(indeterminateAction.mTriggerTexts[2]);
            }
            if (this.this$0.mLoadingIndicator.getVisibility() == 0) {
                this.this$0.mLoadingIndicator.setAlpha(1.0f);
                this.this$0.mLoadingIndicator.setScaleX(1.0f);
                this.this$0.mLoadingIndicator.setScaleY(1.0f);
            }
        }
    }

    /* JADX INFO: renamed from: miuix.springback.trigger.DefaultTrigger$4, reason: invalid class name */
    public class AnonymousClass4 implements BaseTrigger.SimpleAction.OnSimpleActionViewListener {
        public final /* synthetic */ DefaultTrigger this$0;

        @Override // miuix.springback.trigger.BaseTrigger.SimpleAction.OnSimpleActionViewListener
        public float getViewRestartOffsetPoint() {
            return -1.0f;
        }

        @Override // miuix.springback.trigger.BaseTrigger.SimpleAction.OnSimpleActionViewListener
        public void onViewActivated(int i) {
        }

        @Override // miuix.springback.trigger.BaseTrigger.SimpleAction.OnSimpleActionViewListener
        public void onViewActivating(int i) {
        }

        @Override // miuix.springback.trigger.BaseTrigger.SimpleAction.OnSimpleActionViewListener
        public void onViewEntered(int i) {
            DefaultTrigger defaultTrigger = this.this$0;
            defaultTrigger.viewShow(defaultTrigger.getIndicatorContainer());
            if (this.this$0.isExitIndeterminateAction()) {
                this.this$0.mTrackingIndicator.setVisibility(8);
                this.this$0.mTrackingIndicatorLabel.setVisibility(8);
            }
        }

        @Override // miuix.springback.trigger.BaseTrigger.SimpleAction.OnSimpleActionViewListener
        public void onViewEntering(int i) {
        }

        @Override // miuix.springback.trigger.BaseTrigger.SimpleAction.OnSimpleActionViewListener
        public void onViewExit(int i) {
        }

        @Override // miuix.springback.trigger.BaseTrigger.SimpleAction.OnSimpleActionViewListener
        public void onViewFinished(int i) {
        }

        @Override // miuix.springback.trigger.BaseTrigger.SimpleAction.OnSimpleActionViewListener
        public void onViewStart(int i) {
            this.this$0.getIndicatorContainer().setVisibility(0);
        }

        @Override // miuix.springback.trigger.BaseTrigger.SimpleAction.OnSimpleActionViewListener
        public void onViewStarting(int i) {
        }

        @Override // miuix.springback.trigger.BaseTrigger.SimpleAction.OnSimpleActionViewListener
        public void onViewTriggered(int i) {
        }
    }

    /* JADX INFO: renamed from: miuix.springback.trigger.DefaultTrigger$5, reason: invalid class name */
    public class AnonymousClass5 implements BaseTrigger.IndeterminateUpAction.OnIndeterminateUpActionViewListener {
        public final /* synthetic */ DefaultTrigger this$0;

        @Override // miuix.springback.trigger.BaseTrigger.IndeterminateUpAction.OnIndeterminateUpActionViewListener
        public float getViewRestartOffsetPoint() {
            return -1.0f;
        }

        @Override // miuix.springback.trigger.BaseTrigger.IndeterminateUpAction.OnIndeterminateUpActionViewListener
        public void onViewActivated(int i) {
        }

        @Override // miuix.springback.trigger.BaseTrigger.IndeterminateUpAction.OnIndeterminateUpActionViewListener
        public void onViewActivating(int i) {
        }

        @Override // miuix.springback.trigger.BaseTrigger.IndeterminateUpAction.OnIndeterminateUpActionViewListener
        public void onViewEntered(int i) {
        }

        @Override // miuix.springback.trigger.BaseTrigger.IndeterminateUpAction.OnIndeterminateUpActionViewListener
        public void onViewEntering(int i) {
        }

        @Override // miuix.springback.trigger.BaseTrigger.IndeterminateUpAction.OnIndeterminateUpActionViewListener
        public void onViewExit(int i) {
        }

        @Override // miuix.springback.trigger.BaseTrigger.IndeterminateUpAction.OnIndeterminateUpActionViewListener
        public void onViewFinished(int i) {
        }

        @Override // miuix.springback.trigger.BaseTrigger.IndeterminateUpAction.OnIndeterminateUpActionViewListener
        public void onViewStart(int i) {
            this.this$0.mUpTrackingContainer.setVisibility(0);
            BaseTrigger.IndeterminateUpAction indeterminateUpAction = this.this$0.getIndeterminateUpAction();
            if (indeterminateUpAction == null || !indeterminateUpAction.isNoData()) {
                if (indeterminateUpAction != null) {
                    this.this$0.mUpTrackingIndicator.setVisibility(0);
                    this.this$0.mUpLoadingIndicator.setVisibility(0);
                    this.this$0.mUpTrackingIndicatorLabel.setVisibility(0);
                    this.this$0.mUpTrackingIndicatorLabel.setText(indeterminateUpAction.mTriggerTexts[0]);
                    return;
                }
                return;
            }
            this.this$0.mUpTrackingIndicator.setVisibility(8);
            this.this$0.mUpLoadingIndicator.setVisibility(8);
            if (indeterminateUpAction.getCountNoData() < 3) {
                this.this$0.mUpTrackingIndicatorLabel.setText(indeterminateUpAction.mTriggerTexts[2]);
            } else {
                this.this$0.mUpTrackingIndicatorLabel.setText(indeterminateUpAction.mTriggerTexts[3]);
            }
        }

        @Override // miuix.springback.trigger.BaseTrigger.IndeterminateUpAction.OnIndeterminateUpActionViewListener
        public void onViewStarting(int i) {
        }

        @Override // miuix.springback.trigger.BaseTrigger.IndeterminateUpAction.OnIndeterminateUpActionViewListener
        public void onViewTriggered(int i) {
        }
    }

    @Override // miuix.springback.trigger.CustomTrigger
    public void onSpringBackLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
        if (isExitIndeterminateAction()) {
            for (int i9 = 0; i9 < getActions().size(); i9++) {
                BaseTrigger.Action action = (BaseTrigger.Action) getActions().get(i9);
                if (action instanceof BaseTrigger.IndeterminateAction) {
                    BaseTrigger.IndeterminateAction indeterminateAction = (BaseTrigger.IndeterminateAction) action;
                    View view2 = this.mTrackingIndicator;
                    if (view2 != null && mIndeterminateTop >= view2.getTop()) {
                        int i10 = indeterminateAction.mEnterPoint;
                        this.mLoadingIndicator.offsetTopAndBottom(i10);
                        this.mTrackingIndicator.offsetTopAndBottom(i10);
                        this.mTrackingIndicatorLabel.offsetTopAndBottom(i10);
                    }
                }
            }
            View view3 = this.mTrackingIndicator;
            if (view3 != null && view3.getVisibility() == 0 && getCurrentAction() != null && (getCurrentAction() instanceof BaseTrigger.IndeterminateAction)) {
                if (this.mTrackingIndicatorBottom <= 0) {
                    this.mTrackingIndicatorBottom = this.mTrackingIndicator.getBottom();
                }
                TextView textView = this.mTrackingIndicatorLabel;
                if (textView != null && (this.mTrackingIndicatorLabelTop <= 0 || this.mTrackingIndicatorLabelBottom <= 0)) {
                    this.mTrackingIndicatorLabelTop = textView.getTop();
                    this.mTrackingIndicatorLabelBottom = this.mTrackingIndicatorLabel.getBottom();
                }
                if ((this.mLoadingIndicator.getVisibility() == 8 || this.mLoadingIndicator.getVisibility() == 4) && getCurrentState() != this.mActionComplete && getRootContainer().getHeight() > getCurrentAction().mTriggerPoint) {
                    this.mTrackingIndicator.setBottom(this.mTrackingIndicatorBottom + (getRootContainer().getHeight() - getCurrentAction().mTriggerPoint));
                }
            }
        }
        if (isExitSimpleAction() && getIndicatorContainer() != null && getIndicatorContainer().getVisibility() == 0 && getIndicatorContainer().getTop() == 0) {
            getIndicatorContainer().offsetTopAndBottom(this.mScrollDistance - getIndicatorContainer().getMeasuredHeight());
        }
    }

    @Override // miuix.springback.trigger.CustomTrigger
    public void onSpringBackScrolled(SpringBackLayout springBackLayout, int i, int i2, int i3) {
        ViewGroup rootContainer = getRootContainer();
        if (isShowContainer()) {
            if (rootContainer.getVisibility() != 0) {
                rootContainer.setVisibility(0);
            }
            rootContainer.setAlpha(1.0f);
        } else {
            rootContainer.setVisibility(4);
        }
        if (i3 < 0 && isExitIndeterminateUpAction() && getCurrentAction() != null && (getCurrentAction() instanceof BaseTrigger.IndeterminateUpAction)) {
            this.mUpTrackingContainer.setTranslationY(Math.max(getIndeterminateUpView().getHeight() - getIndeterminateUpAction().mTriggerPoint, 0));
        }
        if (isExitIndeterminateAction() && getCurrentAction() != null && (getCurrentAction() instanceof BaseTrigger.IndeterminateAction)) {
            BaseTrigger.IndeterminateAction indeterminateAction = (BaseTrigger.IndeterminateAction) getCurrentAction();
            if (this.mTrackingIndicator.getVisibility() == 0) {
                this.mTrackingIndicatorBottom = this.mTrackingIndicator.getTop() + this.mTrackingIndicator.getWidth();
                this.mTrackingIndicatorLabelTop = this.mTrackingIndicatorLabel.getTop();
                this.mTrackingIndicatorLabelBottom = this.mTrackingIndicatorLabel.getBottom();
                float f = indeterminateAction.mTriggerPoint;
                float fMax = Math.max(0.0f, Math.min(getRootContainer().getHeight() / f, 1.0f));
                float f2 = 0.5f * f;
                float fMax2 = Math.max(0.0f, ((float) getRootContainer().getHeight()) < f2 ? 0.0f : Math.min((getRootContainer().getHeight() - f2) / f2, 1.0f));
                float fMax3 = Math.max(0.0f, ((float) getRootContainer().getHeight()) < f2 ? 0.0f : Math.min((getRootContainer().getHeight() - (0.7f * f)) / (f * 0.3f), 1.0f));
                float f3 = (-this.mTrackingIndicator.getWidth()) * (1.0f - fMax);
                this.mTrackingIndicator.setAlpha(fMax2);
                this.mTrackingIndicator.setScaleX(fMax);
                this.mTrackingIndicator.setScaleY(fMax);
                this.mTrackingIndicatorLabel.setAlpha(fMax3);
                this.mTrackingIndicatorLabel.setTop(this.mTrackingIndicatorLabelTop);
                this.mTrackingIndicatorLabel.setBottom(this.mTrackingIndicatorLabelBottom);
                if (this.mLoadingIndicator.getVisibility() == 0) {
                    this.mLoadingIndicator.setAlpha(fMax2);
                    this.mLoadingIndicator.setScaleX(fMax);
                    this.mLoadingIndicator.setScaleY(fMax);
                }
                if (getRootContainer().getHeight() < indeterminateAction.mTriggerPoint) {
                    if (fMax3 > 0.0f) {
                        this.mTrackingIndicatorLabel.setTranslationY(f3);
                    }
                    if (getCurrentState() == this.mTracking) {
                        this.mTrackingIndicatorLabel.setText(indeterminateAction.mTriggerTexts[0]);
                    }
                    this.mTrackingIndicator.setBottom(this.mTrackingIndicatorBottom);
                } else if (getRootContainer().getHeight() >= indeterminateAction.mTriggerPoint) {
                    int height = this.mTrackingIndicatorBottom + (getRootContainer().getHeight() - indeterminateAction.mTriggerPoint);
                    if (this.mLoadingIndicator.getVisibility() == 0 || getCurrentState() == this.mActionComplete) {
                        this.mTrackingIndicatorLabel.setTranslationY(0.0f);
                    } else {
                        this.mTrackingIndicator.setBottom(height);
                        this.mTrackingIndicatorLabel.setTranslationY(getRootContainer().getHeight() - indeterminateAction.mTriggerPoint);
                    }
                    if (getCurrentState() == this.mTracking) {
                        this.mTrackingIndicatorLabel.setText(indeterminateAction.mTriggerTexts[1]);
                    }
                }
            }
        }
        if (isExitSimpleAction() && getCurrentAction() != null && (getCurrentAction() instanceof BaseTrigger.SimpleAction) && getRootContainer().getHeight() < getCurrentAction().mEnterPoint) {
            getIndicatorContainer().setVisibility(8);
        } else if (isExitSimpleAction() && getCurrentAction() != null && (getCurrentAction() instanceof BaseTrigger.SimpleAction) && getRootContainer().getHeight() >= getCurrentAction().mEnterPoint && getIndicatorContainer().getVisibility() == 8) {
            getIndicatorContainer().setVisibility(0);
            viewShow(getIndicatorContainer());
        }
        if (isExitSimpleAction() && getCurrentAction() != null && getIndicatorContainer().getVisibility() == 0) {
            getIndicatorContainer().offsetTopAndBottom(-i2);
        }
    }

    public final void viewShow(View view) {
        if (view != null) {
            view.setVisibility(0);
            AnimState animState = new AnimState("start");
            ViewProperty viewProperty = ViewProperty.ALPHA;
            AnimState animStateAdd = animState.add(viewProperty, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE);
            ViewProperty viewProperty2 = ViewProperty.TRANSLATION_Y;
            AnimState animStateAdd2 = animStateAdd.add(viewProperty2, -180.0d);
            AnimState animStateAdd3 = new AnimState("show").add(viewProperty, 1.0d).add(viewProperty2, 25.0d);
            Folme.useAt(view).state().setFlags(1L).fromTo(animStateAdd2, animStateAdd3, new AnimConfig().setEase(EaseManager.getStyle(4, 120.0f))).then(new AnimState("hide").add(viewProperty, 1.0d).add(viewProperty2, BaseStyleSelectView.COLLAPSE_SLIDE_VALUE), new AnimConfig().setEase(EaseManager.getStyle(4, 40.0f)));
        }
    }
}
