package miuix.springback.trigger;

import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import miuix.animation.utils.VelocityMonitor;
import miuix.core.view.ViewCompatOnScrollChangeListener;
import miuix.springback.view.SpringBackLayout;
import miuix.view.HapticCompat;
import miuix.view.HapticFeedbackConstants;

/* JADX INFO: loaded from: classes.dex */
public abstract class CustomTrigger extends BaseTrigger {
    public final ActionComplete mActionComplete;
    public int mActionIndex;
    public final ActionStart mActionStart;
    public final ActionTriggered mActionTriggered;
    public RelativeLayout mContainer;
    public BaseTrigger.Action mCurrentAction;
    public TriggerState mCurrentState;
    public long mEnterTime;
    public final Idle mIdle;
    public FrameLayout mIndicatorContainer;
    public boolean mIsExitISimpleAction;
    public boolean mIsExitIndeterminateAction;
    public boolean mIsExitIndeterminateUpAction;
    public boolean mIsShowContainer;
    public boolean mIsStartIndeterminateUp;
    public int mLastScrollDistance;
    public SpringBackLayout mLayout;
    public View mLoadingContainer;
    public BaseTrigger.IndeterminateAction.OnIndeterminateActionViewListener mOnIndeterminateActionViewListener;
    public BaseTrigger.IndeterminateUpAction.OnIndeterminateUpActionViewListener mOnIndeterminateUpActionViewListener;
    public BaseTrigger.SimpleAction.OnSimpleActionViewListener mOnSimpleActionViewListener;
    public int mScrollDistance;
    public int mScrollState;
    public boolean mScrollerFinished;
    public View mSimpleActionView;
    public final Tracking mTracking;
    public boolean mUpActionBegin;
    public View mUpContainer;
    public VelocityMonitor mVelocityMonitor;
    public float mVelocityY;
    public final WaitForIndeterminate mWaitForIndeterminate;

    /* JADX INFO: renamed from: miuix.springback.trigger.CustomTrigger$1, reason: invalid class name */
    public class AnonymousClass1 implements View.OnLayoutChangeListener {
        public final /* synthetic */ CustomTrigger this$0;

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            int paddingTop;
            int paddingBottom;
            SpringBackLayout springBackLayout = (SpringBackLayout) view;
            int springScrollY = springBackLayout.getSpringScrollY();
            int i9 = -springScrollY;
            int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(view.getWidth(), 1073741824);
            int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(i9, 0);
            this.this$0.mContainer.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
            View indeterminateView = this.this$0.getIndeterminateView();
            View indeterminateUpView = this.this$0.getIndeterminateUpView();
            if (indeterminateView != null) {
                indeterminateView.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
            }
            if (springBackLayout.springBackEnable()) {
                this.this$0.mContainer.layout(0, -i9, view.getWidth(), 0);
            } else {
                this.this$0.mContainer.layout(0, 0, view.getWidth(), i9);
            }
            if (this.this$0.mLayout.getTarget() != null) {
                paddingTop = this.this$0.mLayout.getTarget().getPaddingTop();
                paddingBottom = this.this$0.mLayout.getTarget().getPaddingBottom();
            } else {
                paddingTop = 0;
                paddingBottom = 0;
            }
            if (indeterminateUpView != null) {
                indeterminateUpView.measure(iMakeMeasureSpec, View.MeasureSpec.makeMeasureSpec(springScrollY, 0));
                indeterminateUpView.layout(0, this.this$0.mLayout.getHeight() - paddingBottom, view.getWidth(), (this.this$0.mLayout.getHeight() - paddingBottom) + springScrollY);
            }
            if (indeterminateView != null) {
                if (springBackLayout.springBackEnable()) {
                    indeterminateView.layout(0, (-i9) + paddingTop, view.getWidth(), paddingTop);
                } else {
                    indeterminateView.layout(0, paddingTop, view.getWidth(), i9 + paddingTop);
                }
            }
            this.this$0.onSpringBackLayoutChange(view, i, i2, i3, i4, i5, i6, i7, i8);
        }
    }

    /* JADX INFO: renamed from: miuix.springback.trigger.CustomTrigger$2, reason: invalid class name */
    public class AnonymousClass2 implements SpringBackLayout.OnSpringListener {
        public final /* synthetic */ CustomTrigger this$0;

        @Override // miuix.springback.view.SpringBackLayout.OnSpringListener
        public boolean onSpringBack() {
            return this.this$0.mCurrentState.handleSpringBack();
        }
    }

    /* JADX INFO: renamed from: miuix.springback.trigger.CustomTrigger$3, reason: invalid class name */
    public class AnonymousClass3 implements ViewCompatOnScrollChangeListener {
        public final /* synthetic */ CustomTrigger this$0;

        @Override // miuix.core.view.ViewCompatOnScrollChangeListener
        public void onScrollChange(View view, int i, int i2, int i3, int i4) {
            SpringBackLayout springBackLayout = (SpringBackLayout) view;
            int i5 = i2 - i4;
            int i6 = i - i3;
            int springScrollY = springBackLayout.getSpringScrollY();
            CustomTrigger customTrigger = this.this$0;
            customTrigger.mLastScrollDistance = customTrigger.mScrollDistance;
            customTrigger.mScrollDistance = -springScrollY;
            customTrigger.mVelocityMonitor.update(this.this$0.mScrollDistance);
            CustomTrigger customTrigger2 = this.this$0;
            customTrigger2.mVelocityY = customTrigger2.mVelocityMonitor.getVelocity(0);
            if (springBackLayout.springBackEnable()) {
                this.this$0.mContainer.setTop(springScrollY);
            } else {
                this.this$0.mContainer.setTop(0);
            }
            int paddingBottom = this.this$0.mLayout.getTarget() != null ? this.this$0.mLayout.getTarget().getPaddingBottom() : 0;
            if (this.this$0.mUpContainer != null && springScrollY >= 0) {
                this.this$0.mUpContainer.layout(0, this.this$0.mLayout.getHeight() - paddingBottom, view.getWidth(), (this.this$0.mLayout.getHeight() - paddingBottom) + springScrollY);
            }
            CustomTrigger customTrigger3 = this.this$0;
            if (customTrigger3.mScrollDistance < 0 && customTrigger3.mCurrentAction == this.this$0.getIndeterminateUpAction() && this.this$0.getIndeterminateUpAction() != null) {
                CustomTrigger customTrigger4 = this.this$0;
                float fActionRestartOffsetPoint = customTrigger4.actionRestartOffsetPoint(customTrigger4.mCurrentAction);
                if (this.this$0.mScrollState == 1 && (Math.abs(this.this$0.mLastScrollDistance) < fActionRestartOffsetPoint || Math.abs(this.this$0.mScrollDistance) < fActionRestartOffsetPoint)) {
                    TriggerState triggerState = this.this$0.mCurrentState;
                    CustomTrigger customTrigger5 = this.this$0;
                    if (triggerState == customTrigger5.mActionComplete) {
                        customTrigger5.transition(customTrigger5.mTracking);
                    }
                }
            }
            if (this.this$0.mCurrentAction != null && (this.this$0.mCurrentAction instanceof BaseTrigger.IndeterminateAction)) {
                CustomTrigger customTrigger6 = this.this$0;
                float fActionRestartOffsetPoint2 = customTrigger6.actionRestartOffsetPoint(customTrigger6.mCurrentAction);
                if (this.this$0.mScrollState == 1 && (Math.abs(this.this$0.mLastScrollDistance) < fActionRestartOffsetPoint2 || Math.abs(this.this$0.mScrollDistance) < fActionRestartOffsetPoint2)) {
                    TriggerState triggerState2 = this.this$0.mCurrentState;
                    CustomTrigger customTrigger7 = this.this$0;
                    if (triggerState2 == customTrigger7.mActionComplete) {
                        customTrigger7.transition(customTrigger7.mTracking);
                    }
                }
                if (this.this$0.mScrollState == 1) {
                    TriggerState triggerState3 = this.this$0.mCurrentState;
                    CustomTrigger customTrigger8 = this.this$0;
                    if (triggerState3 == customTrigger8.mWaitForIndeterminate && Math.abs(customTrigger8.mLastScrollDistance) > this.this$0.mCurrentAction.mEnterPoint) {
                        CustomTrigger customTrigger9 = this.this$0;
                        customTrigger9.transition(customTrigger9.mTracking);
                    }
                }
            }
            this.this$0.mCurrentState.handleScrolled(i5, springScrollY);
            CustomTrigger customTrigger10 = this.this$0;
            customTrigger10.onSpringBackScrolled(springBackLayout, i6, i5, customTrigger10.mScrollDistance);
        }

        @Override // miuix.core.view.ViewCompatOnScrollChangeListener
        public void onStateChanged(int i, int i2, boolean z) {
            this.this$0.mScrollState = i2;
            this.this$0.mScrollerFinished = z;
            this.this$0.mCurrentState.handleScrollStateChange(i, i2);
            TriggerState triggerState = this.this$0.mCurrentState;
            CustomTrigger customTrigger = this.this$0;
            if (triggerState == customTrigger.mIdle) {
                View indeterminateUpView = customTrigger.getIndeterminateUpView();
                if (this.this$0.mIsStartIndeterminateUp || indeterminateUpView == null || indeterminateUpView.getVisibility() != 0) {
                    return;
                }
                indeterminateUpView.setVisibility(8);
                return;
            }
            if (customTrigger.isShowContainer()) {
                this.this$0.mContainer.setVisibility(0);
            } else {
                this.this$0.mContainer.setVisibility(4);
            }
            View indeterminateUpView2 = this.this$0.getIndeterminateUpView();
            if (!this.this$0.mIsStartIndeterminateUp || indeterminateUpView2 == null || indeterminateUpView2.getVisibility() == 0) {
                return;
            }
            indeterminateUpView2.setVisibility(0);
        }
    }

    /* JADX INFO: renamed from: miuix.springback.trigger.CustomTrigger$4, reason: invalid class name */
    public class AnonymousClass4 implements BaseTrigger.IndeterminateUpAction.OnUpActionDataListener {
    }

    /* JADX INFO: renamed from: miuix.springback.trigger.CustomTrigger$5, reason: invalid class name */
    public class AnonymousClass5 implements BaseTrigger.IndeterminateAction.OnActionCompleteListener {
    }

    public class ActionComplete extends TriggerState {
        public final /* synthetic */ CustomTrigger this$0;

        @Override // miuix.springback.trigger.TriggerState
        public void handleScrollStateChange(int i, int i2) {
            super.handleScrollStateChange(i, i2);
            if (i2 == 0) {
                CustomTrigger customTrigger = this.this$0;
                customTrigger.transition(customTrigger.mIdle);
            }
        }
    }

    public class ActionStart extends TriggerState {
        public final /* synthetic */ CustomTrigger this$0;

        @Override // miuix.springback.trigger.TriggerState
        public void handleScrollStateChange(int i, int i2) {
            super.handleScrollStateChange(i, i2);
        }

        @Override // miuix.springback.trigger.TriggerState
        public void handleScrolled(int i, int i2) {
            super.handleScrolled(i, i2);
        }

        @Override // miuix.springback.trigger.TriggerState
        public boolean handleSpringBack() {
            if (this.this$0.mCurrentAction != null && (this.this$0.mCurrentAction instanceof BaseTrigger.IndeterminateAction)) {
                CustomTrigger customTrigger = this.this$0;
                if (customTrigger.mScrollDistance > customTrigger.mCurrentAction.mTriggerPoint) {
                    CustomTrigger customTrigger2 = this.this$0;
                    customTrigger2.mLayout.smoothScrollTo(0, -customTrigger2.mCurrentAction.mTriggerPoint);
                    return true;
                }
            }
            return super.handleSpringBack();
        }
    }

    public class ActionTriggered extends TriggerState {
        public final /* synthetic */ CustomTrigger this$0;

        @Override // miuix.springback.trigger.TriggerState
        public void handleScrollStateChange(int i, int i2) {
            if (i2 == 0) {
                CustomTrigger customTrigger = this.this$0;
                customTrigger.transition(customTrigger.mIdle);
            }
        }

        @Override // miuix.springback.trigger.TriggerState
        public void handleScrolled(int i, int i2) {
            if (this.this$0.mCurrentAction == null || !(this.this$0.mCurrentAction instanceof BaseTrigger.SimpleAction)) {
                return;
            }
            CustomTrigger customTrigger = this.this$0;
            if (customTrigger.mScrollDistance >= customTrigger.mCurrentAction.mEnterPoint || this.this$0.mScrollState != 1) {
                return;
            }
            this.this$0.mActionIndex = -1;
            CustomTrigger customTrigger2 = this.this$0;
            customTrigger2.transition(customTrigger2.mTracking);
        }
    }

    public class Idle extends TriggerState {
        public final /* synthetic */ CustomTrigger this$0;

        @Override // miuix.springback.trigger.TriggerState
        public void handleScrollStateChange(int i, int i2) {
            if (i == 0) {
                if (i2 == 1 || i2 == 2) {
                    CustomTrigger customTrigger = this.this$0;
                    customTrigger.transition(customTrigger.mTracking);
                }
            }
        }
    }

    public interface OnIndeterminateActionDataListener {
    }

    public interface OnIndeterminateUpActionDataListener {
    }

    public class Tracking extends TriggerState {
        public boolean mLockActivated;
        public boolean mTriggerable;
        public boolean mUpTriggerable;
        public final /* synthetic */ CustomTrigger this$0;

        @Override // miuix.springback.trigger.TriggerState
        public void handleScrollStateChange(int i, int i2) {
            if (i2 == 0) {
                CustomTrigger customTrigger = this.this$0;
                customTrigger.transition(customTrigger.mIdle);
                this.this$0.mLayout.smoothScrollTo(0, 0);
                this.mUpTriggerable = false;
                this.mLockActivated = false;
            }
        }

        @Override // miuix.springback.trigger.TriggerState
        public void handleScrolled(int i, int i2) {
            if (this.this$0.mScrollState == 1 || this.this$0.mScrollState == 2) {
                BaseTrigger.Action action = this.this$0.mCurrentAction;
                CustomTrigger customTrigger = this.this$0;
                if (customTrigger.mScrollDistance < 0) {
                    if (!customTrigger.mUpActionBegin) {
                        this.mUpTriggerable = false;
                    }
                    boolean z = this.mUpTriggerable;
                    BaseTrigger.IndeterminateUpAction indeterminateUpAction = this.this$0.getIndeterminateUpAction();
                    if (indeterminateUpAction != null) {
                        this.this$0.mIsStartIndeterminateUp = true;
                        View indeterminateUpView = this.this$0.getIndeterminateUpView();
                        if (indeterminateUpView != null && indeterminateUpView.getVisibility() != 0) {
                            indeterminateUpView.setVisibility(0);
                        }
                        this.this$0.mCurrentAction = indeterminateUpAction;
                        CustomTrigger customTrigger2 = this.this$0;
                        customTrigger2.notifyViewsStart(customTrigger2.mCurrentAction, action, this.this$0.mLastScrollDistance);
                        if (Math.abs(this.this$0.mScrollDistance) > this.this$0.getIndeterminateUpAction().mEnterPoint && !this.this$0.mUpActionBegin) {
                            this.this$0.mUpActionBegin = true;
                            this.mUpTriggerable = true;
                            this.this$0.mEnterTime = SystemClock.elapsedRealtime();
                            indeterminateUpAction.notifyEntered();
                            CustomTrigger customTrigger3 = this.this$0;
                            customTrigger3.notifyViewsEntered(customTrigger3.mCurrentAction, this.this$0.mScrollDistance);
                        }
                        boolean z2 = this.mUpTriggerable;
                        if (z != z2 && z2) {
                            indeterminateUpAction.notifyActivated();
                            CustomTrigger customTrigger4 = this.this$0;
                            customTrigger4.notifyViewsActivated(customTrigger4.mCurrentAction, this.this$0.mScrollDistance);
                            if (this.this$0.mScrollState == 2) {
                                this.this$0.mLayout.smoothScrollTo(0, indeterminateUpAction.mTriggerPoint);
                                CustomTrigger customTrigger5 = this.this$0;
                                customTrigger5.transition(customTrigger5.mWaitForIndeterminate);
                            }
                        }
                    }
                    CustomTrigger customTrigger6 = this.this$0;
                    customTrigger6.notifyViewsAnimator(customTrigger6.mCurrentAction, action, this.this$0.mScrollDistance);
                    return;
                }
                this.mUpTriggerable = false;
                int i3 = customTrigger.mActionIndex;
                boolean z3 = this.mTriggerable;
                BaseTrigger.Action action2 = this.this$0.mCurrentAction;
                for (int i4 = 0; i4 < this.this$0.getActions().size(); i4++) {
                    CustomTrigger customTrigger7 = this.this$0;
                    if (customTrigger7.mScrollDistance <= ((BaseTrigger.Action) customTrigger7.getActions().get(i4)).mEnterPoint) {
                        break;
                    }
                    this.this$0.mActionIndex = i4;
                }
                if (this.this$0.mActionIndex >= 0) {
                    BaseTrigger.Action action3 = (BaseTrigger.Action) this.this$0.getActions().get(this.this$0.mActionIndex);
                    boolean z4 = action3 != null && (action3 instanceof BaseTrigger.SimpleAction);
                    if (!(z4 && this.this$0.mVelocityY < 1000.0f && this.this$0.mScrollState == 1) && z4) {
                        this.this$0.mActionIndex = i3;
                    } else {
                        this.this$0.mCurrentAction = action3;
                        CustomTrigger customTrigger8 = this.this$0;
                        customTrigger8.notifyViewsStart(customTrigger8.mCurrentAction, action, this.this$0.mLastScrollDistance);
                        CustomTrigger customTrigger9 = this.this$0;
                        this.mTriggerable = customTrigger9.mScrollDistance >= customTrigger9.mCurrentAction.mTriggerPoint;
                    }
                } else {
                    this.this$0.mCurrentAction = null;
                    this.mTriggerable = false;
                }
                if (i3 != this.this$0.mActionIndex) {
                    if (action2 != null) {
                        action2.onExit();
                        if (this.this$0.getSimpleActionView() != null) {
                            this.this$0.getSimpleActionView().setVisibility(8);
                        }
                    }
                    if (this.this$0.mCurrentAction != null) {
                        if (this.this$0.mCurrentAction instanceof BaseTrigger.IndeterminateAction) {
                            if (this.this$0.getSimpleActionView() != null) {
                                this.this$0.getSimpleActionView().setVisibility(8);
                            }
                        } else if ((this.this$0.mCurrentAction instanceof BaseTrigger.SimpleAction) && this.this$0.getSimpleActionView() != null) {
                            this.this$0.getSimpleActionView().setVisibility(0);
                        }
                        this.this$0.mEnterTime = SystemClock.elapsedRealtime();
                        this.this$0.mCurrentAction.notifyEntered();
                        CustomTrigger customTrigger10 = this.this$0;
                        customTrigger10.notifyViewsEntered(customTrigger10.mCurrentAction, this.this$0.mScrollDistance);
                        this.mLockActivated = false;
                        if (this.mTriggerable) {
                            if (this.this$0.mCurrentAction instanceof BaseTrigger.SimpleAction) {
                                this.mLockActivated = true;
                                HapticCompat.performHapticFeedback(this.this$0.mLayout, HapticFeedbackConstants.MIUI_BOUNDARY_SPATIAL, HapticFeedbackConstants.MIUI_SWITCH);
                            }
                            this.this$0.mCurrentAction.notifyActivated();
                            CustomTrigger customTrigger11 = this.this$0;
                            customTrigger11.notifyViewsActivated(customTrigger11.mCurrentAction, this.this$0.mScrollDistance);
                        }
                    } else if (this.this$0.getSimpleActionView() != null) {
                        this.this$0.getSimpleActionView().setVisibility(8);
                    }
                } else if (action2 != null && z3 != this.mTriggerable) {
                    if (z3) {
                        this.this$0.mEnterTime = SystemClock.elapsedRealtime();
                        action2.notifyEntered();
                        CustomTrigger customTrigger12 = this.this$0;
                        customTrigger12.notifyViewsEntered(customTrigger12.mCurrentAction, this.this$0.mScrollDistance);
                        this.mLockActivated = false;
                    } else {
                        if (this.this$0.mCurrentAction instanceof BaseTrigger.SimpleAction) {
                            this.mLockActivated = true;
                        }
                        HapticCompat.performHapticFeedback(this.this$0.mLayout, HapticFeedbackConstants.MIUI_BOUNDARY_SPATIAL, HapticFeedbackConstants.MIUI_MESH_NORMAL);
                        action2.notifyActivated();
                        CustomTrigger customTrigger13 = this.this$0;
                        customTrigger13.notifyViewsActivated(customTrigger13.mCurrentAction, this.this$0.mScrollDistance);
                    }
                }
                CustomTrigger customTrigger14 = this.this$0;
                customTrigger14.notifyViewsAnimator(customTrigger14.mCurrentAction, action, this.this$0.mScrollDistance);
            }
        }

        @Override // miuix.springback.trigger.TriggerState
        public boolean handleSpringBack() {
            if ((!this.mTriggerable || this.this$0.mCurrentAction == null) && this.this$0.mCurrentAction != null && (this.this$0.mCurrentAction instanceof BaseTrigger.SimpleAction) && this.this$0.getSimpleActionView() != null) {
                this.this$0.getSimpleActionView().setVisibility(8);
            }
            if (this.this$0.mCurrentAction == null) {
                return false;
            }
            if (this.this$0.mCurrentAction instanceof BaseTrigger.IndeterminateAction) {
                CustomTrigger customTrigger = this.this$0;
                if (customTrigger.mScrollDistance > customTrigger.mCurrentAction.mEnterPoint) {
                    if (this.mTriggerable) {
                        CustomTrigger customTrigger2 = this.this$0;
                        customTrigger2.mLayout.smoothScrollTo(0, -customTrigger2.mCurrentAction.mTriggerPoint);
                        CustomTrigger customTrigger3 = this.this$0;
                        customTrigger3.transition(customTrigger3.mWaitForIndeterminate);
                    } else {
                        if (Math.abs(this.this$0.mLayout.getScaleY()) < Math.abs(this.this$0.mCurrentAction.mTriggerPoint)) {
                            this.this$0.mCurrentAction.notifyExit();
                            CustomTrigger customTrigger4 = this.this$0;
                            customTrigger4.notifyViewsExit(customTrigger4.mCurrentAction, this.this$0.mScrollDistance);
                        }
                        this.this$0.mLayout.smoothScrollTo(0, 0);
                    }
                    return true;
                }
            }
            if (this.this$0.mCurrentAction instanceof BaseTrigger.IndeterminateUpAction) {
                CustomTrigger customTrigger5 = this.this$0;
                customTrigger5.mLayout.smoothScrollTo(0, customTrigger5.mCurrentAction.mTriggerPoint);
                CustomTrigger customTrigger6 = this.this$0;
                customTrigger6.transition(customTrigger6.mWaitForIndeterminate);
                return true;
            }
            CustomTrigger customTrigger7 = this.this$0;
            customTrigger7.transition(customTrigger7.mActionTriggered);
            if (this.mLockActivated) {
                this.this$0.mCurrentAction.notifyTriggered();
                CustomTrigger customTrigger8 = this.this$0;
                customTrigger8.notifyViewsTriggered(customTrigger8.mCurrentAction, this.this$0.mScrollDistance);
            } else {
                this.this$0.mCurrentAction.notifyExit();
                CustomTrigger customTrigger9 = this.this$0;
                customTrigger9.notifyViewsExit(customTrigger9.mCurrentAction, this.this$0.mScrollDistance);
            }
            if (this.this$0.getSimpleActionView() != null) {
                this.this$0.getSimpleActionView().setVisibility(8);
            }
            return false;
        }
    }

    public class WaitForIndeterminate extends TriggerState {
        public final /* synthetic */ CustomTrigger this$0;

        @Override // miuix.springback.trigger.TriggerState
        public void handleScrollStateChange(int i, int i2) {
            if (i2 == 0) {
                CustomTrigger customTrigger = this.this$0;
                customTrigger.transition(customTrigger.mActionStart);
                if (this.this$0.mCurrentAction != null && (this.this$0.mCurrentAction instanceof BaseTrigger.IndeterminateAction)) {
                    this.this$0.mCurrentAction.notifyTriggered();
                    CustomTrigger customTrigger2 = this.this$0;
                    customTrigger2.notifyViewsTriggered(customTrigger2.mCurrentAction, this.this$0.mScrollDistance);
                } else {
                    if (this.this$0.getIndeterminateUpAction() == null || !(this.this$0.mCurrentAction instanceof BaseTrigger.IndeterminateUpAction)) {
                        return;
                    }
                    this.this$0.getIndeterminateUpAction().notifyTriggered();
                    CustomTrigger customTrigger3 = this.this$0;
                    customTrigger3.notifyViewsTriggered(customTrigger3.mCurrentAction, this.this$0.mScrollDistance);
                }
            }
        }
    }

    public final float actionRestartOffsetPoint(BaseTrigger.Action action) {
        float simpleViewRestartOffsetPoint;
        int i;
        float f;
        if (action != null && (action instanceof BaseTrigger.IndeterminateAction)) {
            simpleViewRestartOffsetPoint = getIndeterminateViewRestartOffsetPoint();
        } else if (action == null || !(action instanceof BaseTrigger.IndeterminateUpAction)) {
            simpleViewRestartOffsetPoint = (action == null || !(action instanceof BaseTrigger.SimpleAction)) ? -1.0f : getSimpleViewRestartOffsetPoint();
        } else {
            simpleViewRestartOffsetPoint = getIndeterminateUpViewRestartOffsetPoint();
        }
        if (simpleViewRestartOffsetPoint < 0.0f) {
            if (this.mScrollDistance >= 0 || action != getIndeterminateUpAction() || getIndeterminateUpAction() == null) {
                BaseTrigger.Action action2 = this.mCurrentAction;
                if (action2 != null && (action instanceof BaseTrigger.IndeterminateAction)) {
                    int i2 = action2.mTriggerPoint;
                    i = action2.mEnterPoint;
                    f = (i2 - i) * 0.25f;
                }
            } else {
                f = (getIndeterminateUpAction().mTriggerPoint - getIndeterminateUpAction().mEnterPoint) * 0.25f;
                i = getIndeterminateUpAction().mEnterPoint;
            }
            return f + i;
        }
        return 0.0f;
    }

    public BaseTrigger.Action getCurrentAction() {
        return this.mCurrentAction;
    }

    public TriggerState getCurrentState() {
        return this.mCurrentState;
    }

    public View getIndeterminateUpView() {
        return this.mUpContainer;
    }

    public final float getIndeterminateUpViewRestartOffsetPoint() {
        BaseTrigger.IndeterminateUpAction.OnIndeterminateUpActionViewListener onIndeterminateUpActionViewListener = this.mOnIndeterminateUpActionViewListener;
        if (onIndeterminateUpActionViewListener != null) {
            return onIndeterminateUpActionViewListener.getViewRestartOffsetPoint();
        }
        return 0.0f;
    }

    public View getIndeterminateView() {
        return this.mLoadingContainer;
    }

    public final float getIndeterminateViewRestartOffsetPoint() {
        BaseTrigger.IndeterminateAction.OnIndeterminateActionViewListener onIndeterminateActionViewListener = this.mOnIndeterminateActionViewListener;
        if (onIndeterminateActionViewListener != null) {
            return onIndeterminateActionViewListener.getViewRestartOffsetPoint();
        }
        return 0.0f;
    }

    public ViewGroup getIndicatorContainer() {
        return this.mIndicatorContainer;
    }

    public ViewGroup getRootContainer() {
        return this.mContainer;
    }

    public View getSimpleActionView() {
        return this.mSimpleActionView;
    }

    public final float getSimpleViewRestartOffsetPoint() {
        BaseTrigger.SimpleAction.OnSimpleActionViewListener onSimpleActionViewListener = this.mOnSimpleActionViewListener;
        if (onSimpleActionViewListener != null) {
            return onSimpleActionViewListener.getViewRestartOffsetPoint();
        }
        return 0.0f;
    }

    public boolean isExitIndeterminateAction() {
        return this.mIsExitIndeterminateAction;
    }

    public boolean isExitIndeterminateUpAction() {
        return this.mIsExitIndeterminateUpAction;
    }

    public boolean isExitSimpleAction() {
        return this.mIsExitISimpleAction;
    }

    public boolean isShowContainer() {
        return this.mIsShowContainer;
    }

    public final void notifyIndeterminateUpViewActivated(int i) {
        BaseTrigger.IndeterminateUpAction.OnIndeterminateUpActionViewListener onIndeterminateUpActionViewListener = this.mOnIndeterminateUpActionViewListener;
        if (onIndeterminateUpActionViewListener != null) {
            onIndeterminateUpActionViewListener.onViewActivated(i);
        }
    }

    public final void notifyIndeterminateUpViewActivating(int i) {
        BaseTrigger.IndeterminateUpAction.OnIndeterminateUpActionViewListener onIndeterminateUpActionViewListener = this.mOnIndeterminateUpActionViewListener;
        if (onIndeterminateUpActionViewListener != null) {
            onIndeterminateUpActionViewListener.onViewActivating(i);
        }
    }

    public final void notifyIndeterminateUpViewEntered(int i) {
        BaseTrigger.IndeterminateUpAction.OnIndeterminateUpActionViewListener onIndeterminateUpActionViewListener = this.mOnIndeterminateUpActionViewListener;
        if (onIndeterminateUpActionViewListener != null) {
            onIndeterminateUpActionViewListener.onViewEntered(i);
        }
    }

    public final void notifyIndeterminateUpViewEntering(int i) {
        BaseTrigger.IndeterminateUpAction.OnIndeterminateUpActionViewListener onIndeterminateUpActionViewListener = this.mOnIndeterminateUpActionViewListener;
        if (onIndeterminateUpActionViewListener != null) {
            onIndeterminateUpActionViewListener.onViewEntering(i);
        }
    }

    public final void notifyIndeterminateUpViewExit(int i) {
        BaseTrigger.IndeterminateUpAction.OnIndeterminateUpActionViewListener onIndeterminateUpActionViewListener = this.mOnIndeterminateUpActionViewListener;
        if (onIndeterminateUpActionViewListener != null) {
            onIndeterminateUpActionViewListener.onViewExit(i);
        }
    }

    public final void notifyIndeterminateUpViewFinished(int i) {
        BaseTrigger.IndeterminateUpAction.OnIndeterminateUpActionViewListener onIndeterminateUpActionViewListener = this.mOnIndeterminateUpActionViewListener;
        if (onIndeterminateUpActionViewListener != null) {
            onIndeterminateUpActionViewListener.onViewFinished(i);
        }
    }

    public final void notifyIndeterminateUpViewStart(int i) {
        BaseTrigger.IndeterminateUpAction.OnIndeterminateUpActionViewListener onIndeterminateUpActionViewListener = this.mOnIndeterminateUpActionViewListener;
        if (onIndeterminateUpActionViewListener != null) {
            onIndeterminateUpActionViewListener.onViewStart(i);
        }
    }

    public final void notifyIndeterminateUpViewStarting(int i) {
        BaseTrigger.IndeterminateUpAction.OnIndeterminateUpActionViewListener onIndeterminateUpActionViewListener = this.mOnIndeterminateUpActionViewListener;
        if (onIndeterminateUpActionViewListener != null) {
            onIndeterminateUpActionViewListener.onViewStarting(i);
        }
    }

    public final void notifyIndeterminateUpViewTriggered(int i) {
        BaseTrigger.IndeterminateUpAction.OnIndeterminateUpActionViewListener onIndeterminateUpActionViewListener = this.mOnIndeterminateUpActionViewListener;
        if (onIndeterminateUpActionViewListener != null) {
            onIndeterminateUpActionViewListener.onViewTriggered(i);
        }
    }

    public final void notifyIndeterminateViewActivated(int i) {
        BaseTrigger.IndeterminateAction.OnIndeterminateActionViewListener onIndeterminateActionViewListener = this.mOnIndeterminateActionViewListener;
        if (onIndeterminateActionViewListener != null) {
            onIndeterminateActionViewListener.onViewActivated(i);
        }
    }

    public final void notifyIndeterminateViewActivating(int i) {
        BaseTrigger.IndeterminateAction.OnIndeterminateActionViewListener onIndeterminateActionViewListener = this.mOnIndeterminateActionViewListener;
        if (onIndeterminateActionViewListener != null) {
            onIndeterminateActionViewListener.onViewActivating(i);
        }
    }

    public final void notifyIndeterminateViewEntered(int i) {
        BaseTrigger.IndeterminateAction.OnIndeterminateActionViewListener onIndeterminateActionViewListener = this.mOnIndeterminateActionViewListener;
        if (onIndeterminateActionViewListener != null) {
            onIndeterminateActionViewListener.onViewEntered(i);
        }
    }

    public final void notifyIndeterminateViewEntering(int i) {
        BaseTrigger.IndeterminateAction.OnIndeterminateActionViewListener onIndeterminateActionViewListener = this.mOnIndeterminateActionViewListener;
        if (onIndeterminateActionViewListener != null) {
            onIndeterminateActionViewListener.onViewEntering(i);
        }
    }

    public final void notifyIndeterminateViewExit(int i) {
        BaseTrigger.IndeterminateAction.OnIndeterminateActionViewListener onIndeterminateActionViewListener = this.mOnIndeterminateActionViewListener;
        if (onIndeterminateActionViewListener != null) {
            onIndeterminateActionViewListener.onViewExit(i);
        }
    }

    public final void notifyIndeterminateViewFinished(int i) {
        BaseTrigger.IndeterminateAction.OnIndeterminateActionViewListener onIndeterminateActionViewListener = this.mOnIndeterminateActionViewListener;
        if (onIndeterminateActionViewListener != null) {
            onIndeterminateActionViewListener.onViewFinished(i);
        }
    }

    public final void notifyIndeterminateViewStart(int i) {
        BaseTrigger.IndeterminateAction.OnIndeterminateActionViewListener onIndeterminateActionViewListener = this.mOnIndeterminateActionViewListener;
        if (onIndeterminateActionViewListener != null) {
            onIndeterminateActionViewListener.onViewStart(i);
        }
    }

    public final void notifyIndeterminateViewStarting(int i) {
        BaseTrigger.IndeterminateAction.OnIndeterminateActionViewListener onIndeterminateActionViewListener = this.mOnIndeterminateActionViewListener;
        if (onIndeterminateActionViewListener != null) {
            onIndeterminateActionViewListener.onViewStarting(i);
        }
    }

    public final void notifyIndeterminateViewTriggered(int i) {
        BaseTrigger.IndeterminateAction.OnIndeterminateActionViewListener onIndeterminateActionViewListener = this.mOnIndeterminateActionViewListener;
        if (onIndeterminateActionViewListener != null) {
            onIndeterminateActionViewListener.onViewTriggered(i);
        }
    }

    public final void notifySimpleViewActivated(int i) {
        BaseTrigger.SimpleAction.OnSimpleActionViewListener onSimpleActionViewListener = this.mOnSimpleActionViewListener;
        if (onSimpleActionViewListener != null) {
            onSimpleActionViewListener.onViewActivated(i);
        }
    }

    public final void notifySimpleViewActivating(int i) {
        BaseTrigger.SimpleAction.OnSimpleActionViewListener onSimpleActionViewListener = this.mOnSimpleActionViewListener;
        if (onSimpleActionViewListener != null) {
            onSimpleActionViewListener.onViewActivating(i);
        }
    }

    public final void notifySimpleViewEntered(int i) {
        BaseTrigger.SimpleAction.OnSimpleActionViewListener onSimpleActionViewListener = this.mOnSimpleActionViewListener;
        if (onSimpleActionViewListener != null) {
            onSimpleActionViewListener.onViewEntered(i);
        }
    }

    public final void notifySimpleViewEntering(int i) {
        BaseTrigger.SimpleAction.OnSimpleActionViewListener onSimpleActionViewListener = this.mOnSimpleActionViewListener;
        if (onSimpleActionViewListener != null) {
            onSimpleActionViewListener.onViewEntering(i);
        }
    }

    public final void notifySimpleViewExit(int i) {
        BaseTrigger.SimpleAction.OnSimpleActionViewListener onSimpleActionViewListener = this.mOnSimpleActionViewListener;
        if (onSimpleActionViewListener != null) {
            onSimpleActionViewListener.onViewExit(i);
        }
    }

    public final void notifySimpleViewFinished(int i) {
        BaseTrigger.SimpleAction.OnSimpleActionViewListener onSimpleActionViewListener = this.mOnSimpleActionViewListener;
        if (onSimpleActionViewListener != null) {
            onSimpleActionViewListener.onViewFinished(i);
        }
    }

    public final void notifySimpleViewStart(int i) {
        BaseTrigger.SimpleAction.OnSimpleActionViewListener onSimpleActionViewListener = this.mOnSimpleActionViewListener;
        if (onSimpleActionViewListener != null) {
            onSimpleActionViewListener.onViewStart(i);
        }
    }

    public final void notifySimpleViewStarting(int i) {
        BaseTrigger.SimpleAction.OnSimpleActionViewListener onSimpleActionViewListener = this.mOnSimpleActionViewListener;
        if (onSimpleActionViewListener != null) {
            onSimpleActionViewListener.onViewStarting(i);
        }
    }

    public final void notifySimpleViewTriggered(int i) {
        BaseTrigger.SimpleAction.OnSimpleActionViewListener onSimpleActionViewListener = this.mOnSimpleActionViewListener;
        if (onSimpleActionViewListener != null) {
            onSimpleActionViewListener.onViewTriggered(i);
        }
    }

    public final void notifyViewsActivated(BaseTrigger.Action action, int i) {
        if (action != null && (action instanceof BaseTrigger.IndeterminateAction)) {
            notifyIndeterminateViewActivated(i);
            return;
        }
        if (action != null && (action instanceof BaseTrigger.SimpleAction)) {
            notifySimpleViewActivated(i);
        } else {
            if (action == null || !(action instanceof BaseTrigger.IndeterminateUpAction)) {
                return;
            }
            notifyIndeterminateUpViewActivated(i);
        }
    }

    public final void notifyViewsAnimator(BaseTrigger.Action action, BaseTrigger.Action action2, int i) {
        if (action != null && (action instanceof BaseTrigger.IndeterminateAction)) {
            if (Math.abs(i) < action.mEnterPoint) {
                notifyIndeterminateViewStarting(i);
            }
            if (Math.abs(i) >= action.mEnterPoint && Math.abs(i) < action.mTriggerPoint) {
                notifyIndeterminateViewEntering(i);
            }
            if (Math.abs(i) >= action.mTriggerPoint) {
                notifyIndeterminateViewActivating(i);
                return;
            }
            return;
        }
        if (action != null && (action instanceof BaseTrigger.SimpleAction)) {
            if (Math.abs(i) < action.mEnterPoint) {
                notifySimpleViewStarting(i);
            }
            if (Math.abs(i) >= action.mEnterPoint && Math.abs(i) < action.mTriggerPoint) {
                notifySimpleViewEntering(i);
            }
            if (Math.abs(i) >= action.mTriggerPoint) {
                notifySimpleViewActivating(i);
                return;
            }
            return;
        }
        if (action == null || !(action instanceof BaseTrigger.IndeterminateUpAction)) {
            return;
        }
        if (Math.abs(i) < action.mEnterPoint) {
            notifyIndeterminateUpViewStarting(i);
        }
        if (Math.abs(i) >= action.mEnterPoint && Math.abs(i) < action.mTriggerPoint) {
            notifyIndeterminateUpViewEntering(i);
        }
        if (Math.abs(i) >= action.mTriggerPoint) {
            notifyIndeterminateUpViewActivating(i);
        }
    }

    public final void notifyViewsEntered(BaseTrigger.Action action, int i) {
        if (action != null && (action instanceof BaseTrigger.IndeterminateAction)) {
            notifyIndeterminateViewEntered(i);
            return;
        }
        if (action != null && (action instanceof BaseTrigger.SimpleAction)) {
            notifySimpleViewEntered(i);
        } else {
            if (action == null || !(action instanceof BaseTrigger.IndeterminateUpAction)) {
                return;
            }
            notifyIndeterminateUpViewEntered(i);
        }
    }

    public final void notifyViewsExit(BaseTrigger.Action action, int i) {
        if (action != null && (action instanceof BaseTrigger.IndeterminateAction)) {
            notifyIndeterminateViewExit(i);
            return;
        }
        if (action != null && (action instanceof BaseTrigger.SimpleAction)) {
            notifySimpleViewExit(i);
        } else {
            if (action == null || !(action instanceof BaseTrigger.IndeterminateUpAction)) {
                return;
            }
            notifyIndeterminateUpViewExit(i);
        }
    }

    public final void notifyViewsStart(BaseTrigger.Action action, BaseTrigger.Action action2, int i) {
        if (action != null && (action instanceof BaseTrigger.IndeterminateAction) && action2 != action) {
            notifyIndeterminateViewStart(i);
            return;
        }
        if (action != null && (action instanceof BaseTrigger.SimpleAction) && action2 != action) {
            notifySimpleViewStart(i);
        } else {
            if (action == null || !(action instanceof BaseTrigger.IndeterminateUpAction) || action2 == action) {
                return;
            }
            notifyIndeterminateUpViewStart(i);
        }
    }

    public final void notifyViewsTriggered(BaseTrigger.Action action, int i) {
        if (action != null && (action instanceof BaseTrigger.IndeterminateAction)) {
            notifyIndeterminateViewTriggered(i);
            return;
        }
        if (action != null && (action instanceof BaseTrigger.SimpleAction)) {
            notifySimpleViewTriggered(i);
        } else {
            if (action == null || !(action instanceof BaseTrigger.IndeterminateUpAction)) {
                return;
            }
            notifyIndeterminateUpViewTriggered(i);
        }
    }

    public abstract void onSpringBackLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);

    public abstract void onSpringBackScrolled(SpringBackLayout springBackLayout, int i, int i2, int i3);

    public void transition(TriggerState triggerState) {
        BaseTrigger.Action action;
        this.mCurrentState = triggerState;
        if (triggerState == this.mIdle) {
            if (this.mScrollerFinished && (action = this.mCurrentAction) != null) {
                action.notifyFinished();
                BaseTrigger.Action action2 = this.mCurrentAction;
                if (action2 instanceof BaseTrigger.IndeterminateAction) {
                    notifyIndeterminateViewFinished(this.mScrollDistance);
                } else if (action2 instanceof BaseTrigger.IndeterminateUpAction) {
                    notifyIndeterminateUpViewFinished(this.mScrollDistance);
                } else if (action2 instanceof BaseTrigger.SimpleAction) {
                    notifySimpleViewFinished(this.mScrollDistance);
                }
            }
            this.mCurrentAction = null;
            this.mActionIndex = -1;
            this.mVelocityMonitor.clear();
        }
    }
}
