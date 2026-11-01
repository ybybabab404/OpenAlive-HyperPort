package com.miui.aod.components.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.gson.Gson;
import com.miui.aod.AODStyleController;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.CategoryFactory;
import com.miui.aod.category.LinkageClockCategoryInfo;
import com.miui.aod.category.TextStyleCategoryInfo;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.BaseStyleSelectPresenter;
import com.miui.aod.components.StyleSelectContract;
import com.miui.aod.components.widget.WidgetSwitcher;
import com.miui.aod.components.widget.WidgetSwitcherGroup;
import com.miui.aod.other.step.GoalLiveData;
import com.miui.aod.other.step.StepBean;
import com.miui.aod.other.step.StepLiveData;
import com.miui.aod.other.step.StepUtils;
import com.miui.aod.util.GsonUtils;
import com.miui.aod.util.MiuiPreDrawListener;
import com.miui.aod.util.ViewUtils;
import com.miui.aod.util.ViewUtilsKt;
import com.miui.aod.util.WindowUtils;
import com.miui.aod.widget.HeightLimitedNestedScrollView;
import com.miui.aod.widget.IAodClock;
import java.util.Optional;
import java.util.function.Consumer;
import kotlin.jvm.functions.Function1;
import miuix.animation.Folme;
import miuix.animation.ITouchStyle;
import miuix.animation.base.AnimConfig;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public abstract class BaseStyleSelectView extends BottomSheetBehavior.BottomSheetCallback implements StyleSelectContract.StyleSelectView<BaseStyleSelectPresenter> {
    static final int ANCHOR_BOTTOM = 2;
    static final int ANCHOR_MID = 1;
    static final int ANCHOR_TOP = 0;
    public static final double COLLAPSE_SLIDE_VALUE = 0.0d;
    public static final double EXPAND_SLIDE_VALUE = 1.0d;
    public static final String KEY_STYLE_INFO = "key_style_info";
    public static final double SLIDE_EPSILON = 1.0E-4d;
    protected boolean m24HourFormat;
    private int mAnchorOffset;
    ViewGroup mAodContainerContainer;
    Button mApplyButton;
    private View mBackIcon;
    private BottomSheetBehavior<?> mBottomSheetBehavior;
    String mCateNameSelected;
    protected Context mContext;
    private View mHeadBottomDivider;
    protected LayoutInflater mLayoutInflater;
    private View mPanelContainer;
    private boolean mPendingFreshStepData;
    private boolean mPendingStateOfStepComponentToSet;
    AodContainerView mPreview;
    boolean mPreviewMode;
    protected ViewGroup mRootView;
    private HeightLimitedNestedScrollView mSelectContainerView;
    private BaseStyleSelectPresenter mSelectPresenter;
    private WidgetSwitcherGroup mSlidingViews;
    protected StyleInfo mStyleInfo;
    private boolean mSupportLunarTime;
    TextView mTitleTv;
    private int mTranslation = 1000;
    private final Gson mGson = new Gson();
    private int mAnchorViewId = -1;
    private int mAnchorMode = 1;
    private int initSelectViewHeight = 0;
    private final Animator.AnimatorListener mAnimatorListener = new AnimatorListenerAdapter() { // from class: com.miui.aod.components.view.BaseStyleSelectView.1
        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            BaseStyleSelectView baseStyleSelectView = BaseStyleSelectView.this;
            if (baseStyleSelectView.mPreviewMode) {
                baseStyleSelectView.mPanelContainer.setVisibility(8);
            }
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            BaseStyleSelectView baseStyleSelectView = BaseStyleSelectView.this;
            if (baseStyleSelectView.mPreviewMode) {
                return;
            }
            baseStyleSelectView.mPanelContainer.setVisibility(0);
            EventBus.getDefault().post(new PreviewStateEvent(false));
        }
    };
    private final View.OnTouchListener mOnTouchListener = new View.OnTouchListener() { // from class: com.miui.aod.components.view.BaseStyleSelectView.2
        private long mCurrentMS;
        private float mDownX;
        private float mDownY;

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view, MotionEvent motionEvent) {
            int action = motionEvent.getAction();
            if (action == 0) {
                this.mDownX = motionEvent.getX();
                this.mDownY = motionEvent.getY();
                this.mCurrentMS = System.currentTimeMillis();
            } else if (action == 1) {
                long jCurrentTimeMillis = System.currentTimeMillis() - this.mCurrentMS;
                float x = motionEvent.getX() - this.mDownX;
                float y = motionEvent.getY() - this.mDownY;
                boolean z = y > 0.0f;
                if (jCurrentTimeMillis > 100 && (Math.abs(x) > 20.0f || Math.abs(y) > 20.0f)) {
                    if (z) {
                        if (BaseStyleSelectView.this.mBottomSheetBehavior.getState() == 4) {
                            EventBus.getDefault().post(new ExitSelectViewEvent());
                        }
                        BaseStyleSelectView.this.mBottomSheetBehavior.setState(4);
                    } else {
                        BaseStyleSelectView.this.showPreviewMode(false);
                        BaseStyleSelectView.this.mBottomSheetBehavior.setState(3);
                    }
                    return true;
                }
            }
            return false;
        }
    };
    private final AODStyleController mPreviewStyleController = new AODStyleController(1);

    public @interface ANCHOR_MODE {
    }

    public static class ExitSelectViewEvent {
    }

    public static class PreviewStateEvent {
        public final boolean mIsPreview;

        public PreviewStateEvent(boolean z) {
            this.mIsPreview = z;
        }
    }

    public static /* synthetic */ void $r8$lambda$2VnkStjR6uxKUsMafKnfxfV_yFU(BaseStyleSelectView baseStyleSelectView) {
        StepUtils.startToInstall(baseStyleSelectView.mContext);
        baseStyleSelectView.mPendingFreshStepData = true;
        baseStyleSelectView.mPendingStateOfStepComponentToSet = true;
    }

    /* JADX INFO: renamed from: $r8$lambda$LIIB0o5zA-Nrg4uagwZWI0RCrtc, reason: not valid java name */
    public static /* synthetic */ void m236$r8$lambda$LIIB0o5zANrg4uagwZWI0RCrtc(View view) {
    }

    /* JADX INFO: renamed from: $r8$lambda$WFPN-scaOthYhJYJ0wuSuGUynWk, reason: not valid java name */
    public static /* synthetic */ void m238$r8$lambda$WFPNscaOthYhJYJ0wuSuGUynWk(final BaseStyleSelectView baseStyleSelectView, int i, boolean z) {
        if (i == 2) {
            baseStyleSelectView.mStyleInfo.switchOnBatteryIcon(z);
            baseStyleSelectView.mPreview.showBatteryIcon(z);
        } else if (i == 3) {
            baseStyleSelectView.mStyleInfo.switchOnNotificationIcon(z);
            baseStyleSelectView.mPreview.showNotificationIcon(z);
        } else if (i == 1) {
            baseStyleSelectView.mStyleInfo.switchOnDateAndTime(z);
            baseStyleSelectView.mPreview.showDateAndTime(z);
        } else if (i == 5) {
            baseStyleSelectView.mStyleInfo.setLunarSwitchOn(z);
            baseStyleSelectView.mPreview.showLunarTime(z);
        } else if (i == 4) {
            baseStyleSelectView.mStyleInfo.switchOnStepComponent(z);
            baseStyleSelectView.mPreview.showStepWidget(z);
            if (z) {
                if (StepUtils.isHealthProviderExist(baseStyleSelectView.mContext) && StepUtils.isStepPathValid(baseStyleSelectView.mContext)) {
                    final StepBean stepBean = (StepBean) StepLiveData.getInstance().getValue();
                    if (!StepUtils.stepActive(stepBean)) {
                        StepUtils.dealWithActiveComponent(baseStyleSelectView.mContext, new Runnable() { // from class: com.miui.aod.components.view.BaseStyleSelectView$$ExternalSyntheticLambda8
                            @Override // java.lang.Runnable
                            public final void run() {
                                BaseStyleSelectView.$r8$lambda$__Cuh_roZyIYOtQp2oq3U6lthe0(this.f$0, stepBean);
                            }
                        }, new Runnable() { // from class: com.miui.aod.components.view.BaseStyleSelectView$$ExternalSyntheticLambda9
                            @Override // java.lang.Runnable
                            public final void run() {
                                BaseStyleSelectView baseStyleSelectView2 = this.f$0;
                                baseStyleSelectView2.mPreview.post(new Runnable() { // from class: com.miui.aod.components.view.BaseStyleSelectView$$ExternalSyntheticLambda11
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        BaseStyleSelectView.$r8$lambda$Y0wZ0lMZ5dtt568Iyr3yy_KZags(this.f$0);
                                    }
                                });
                            }
                        });
                    }
                } else {
                    StepUtils.dealWithEnableStepComponent(baseStyleSelectView.mContext, new Runnable() { // from class: com.miui.aod.components.view.BaseStyleSelectView$$ExternalSyntheticLambda6
                        @Override // java.lang.Runnable
                        public final void run() {
                            BaseStyleSelectView.$r8$lambda$2VnkStjR6uxKUsMafKnfxfV_yFU(this.f$0);
                        }
                    }, new Runnable() { // from class: com.miui.aod.components.view.BaseStyleSelectView$$ExternalSyntheticLambda7
                        @Override // java.lang.Runnable
                        public final void run() {
                            BaseStyleSelectView baseStyleSelectView2 = this.f$0;
                            baseStyleSelectView2.mPreview.post(new Runnable() { // from class: com.miui.aod.components.view.BaseStyleSelectView$$ExternalSyntheticLambda4
                                @Override // java.lang.Runnable
                                public final void run() {
                                    BaseStyleSelectView.$r8$lambda$dL3FtwldJmnnrhnW_reQJMUu3hY(this.f$0);
                                }
                            });
                        }
                    });
                }
                baseStyleSelectView.mSelectContainerView.post(new Runnable() { // from class: com.miui.aod.components.view.BaseStyleSelectView$$ExternalSyntheticLambda10
                    @Override // java.lang.Runnable
                    public final void run() {
                        BaseStyleSelectView.$r8$lambda$_K6QpSOSKAjHM_UOcKegdZTR1Cc(this.f$0);
                    }
                });
            }
        }
        if (!z) {
            baseStyleSelectView.getClass();
            return;
        }
        StyleInfo styleInfo = baseStyleSelectView.mStyleInfo;
        if (styleInfo == null || !(styleInfo instanceof TextStyleCategoryInfo)) {
            return;
        }
        baseStyleSelectView.updateStyleInfoForPreview();
    }

    public static /* synthetic */ void $r8$lambda$Y0wZ0lMZ5dtt568Iyr3yy_KZags(final BaseStyleSelectView baseStyleSelectView) {
        baseStyleSelectView.mStyleInfo.switchOnStepComponent(false);
        Optional.ofNullable(baseStyleSelectView.mSlidingViews).ifPresent(new Consumer() { // from class: com.miui.aod.components.view.BaseStyleSelectView$$ExternalSyntheticLambda12
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((WidgetSwitcherGroup) obj).update(this.f$0.mStyleInfo);
            }
        });
    }

    public static /* synthetic */ Boolean $r8$lambda$ZLVtcaMebslnKM07maPkSbqrok0(BaseStyleSelectView baseStyleSelectView, MiuiPreDrawListener miuiPreDrawListener) {
        int top = baseStyleSelectView.mHeadBottomDivider.getTop();
        if (top > 0) {
            baseStyleSelectView.mBottomSheetBehavior.setPeekHeight(top);
            miuiPreDrawListener.removeListener();
        }
        return Boolean.TRUE;
    }

    public static /* synthetic */ void $r8$lambda$_K6QpSOSKAjHM_UOcKegdZTR1Cc(BaseStyleSelectView baseStyleSelectView) {
        WidgetSwitcherGroup widgetSwitcherGroup = baseStyleSelectView.mSlidingViews;
        View childAt = widgetSwitcherGroup.getChildAt(widgetSwitcherGroup.getChildCount() - 1);
        if (childAt == null) {
            baseStyleSelectView.mSelectContainerView.fullScroll(130);
        } else {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) childAt.getLayoutParams();
            baseStyleSelectView.mSelectContainerView.scrollBy(0, childAt.getHeight() + layoutParams.topMargin + layoutParams.bottomMargin);
        }
    }

    public static /* synthetic */ void $r8$lambda$__Cuh_roZyIYOtQp2oq3U6lthe0(BaseStyleSelectView baseStyleSelectView, StepBean stepBean) {
        StepUtils.startSetupUri(baseStyleSelectView.mContext, stepBean);
        baseStyleSelectView.mPendingFreshStepData = true;
        baseStyleSelectView.mPendingStateOfStepComponentToSet = true;
    }

    /* JADX INFO: renamed from: $r8$lambda$avQitkUcJMcGl9Cs_CwqrXSVr-I, reason: not valid java name */
    public static /* synthetic */ void m239$r8$lambda$avQitkUcJMcGl9Cs_CwqrXSVrI(BaseStyleSelectView baseStyleSelectView, StepBean stepBean) {
        if (baseStyleSelectView.mSlidingViews != null) {
            if (!StepUtils.stepActive(stepBean)) {
                baseStyleSelectView.mStyleInfo.switchOnStepComponent(false);
                baseStyleSelectView.mSlidingViews.update(baseStyleSelectView.mStyleInfo);
            } else if (baseStyleSelectView.mPendingStateOfStepComponentToSet) {
                baseStyleSelectView.mStyleInfo.switchOnStepComponent(true);
                baseStyleSelectView.mSlidingViews.update(baseStyleSelectView.mStyleInfo);
                baseStyleSelectView.mPendingStateOfStepComponentToSet = false;
            }
            baseStyleSelectView.mPreview.updateStep(stepBean);
        }
    }

    public static /* synthetic */ void $r8$lambda$dL3FtwldJmnnrhnW_reQJMUu3hY(final BaseStyleSelectView baseStyleSelectView) {
        baseStyleSelectView.mStyleInfo.switchOnStepComponent(false);
        Optional.ofNullable(baseStyleSelectView.mSlidingViews).ifPresent(new Consumer() { // from class: com.miui.aod.components.view.BaseStyleSelectView$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((WidgetSwitcherGroup) obj).update(this.f$0.mStyleInfo);
            }
        });
    }

    public static /* synthetic */ Boolean $r8$lambda$eC2GTWIoni3LWrAXXDSA2AsVHdQ(BaseStyleSelectView baseStyleSelectView, MiuiPreDrawListener miuiPreDrawListener) {
        int top = baseStyleSelectView.mPreview.getTop();
        int height = baseStyleSelectView.mPreview.getHeight();
        int height2 = ((View) baseStyleSelectView.mPreview.getParent()).getHeight();
        if (height2 > 0 && height > 0 && top > 0) {
            baseStyleSelectView.mTranslation = ((height2 - height) / 2) - top;
            miuiPreDrawListener.removeListener();
        }
        return Boolean.TRUE;
    }

    public static /* synthetic */ Boolean $r8$lambda$qrRUsseHLbXxWtdzz2BK3pWNc1E(BaseStyleSelectView baseStyleSelectView, MiuiPreDrawListener miuiPreDrawListener) {
        View viewFindViewById = baseStyleSelectView.mRootView.findViewById(baseStyleSelectView.mAnchorViewId);
        if (viewFindViewById == null) {
            if (baseStyleSelectView.mAnchorViewId != -1 || baseStyleSelectView.mRootView.getHeight() <= 0) {
                return Boolean.TRUE;
            }
            baseStyleSelectView.resetSelectContainerViewHeight();
            miuiPreDrawListener.removeListener();
            return Boolean.FALSE;
        }
        boolean z = true;
        int viewSelfAnchorPadding = 0;
        while (viewFindViewById.getParent().getParent() != baseStyleSelectView.mSelectContainerView) {
            viewSelfAnchorPadding += viewFindViewById.getTop() + (z ? baseStyleSelectView.getViewSelfAnchorPadding(viewFindViewById) : 0) + baseStyleSelectView.getAnchorTopMargin(viewFindViewById);
            viewFindViewById = (View) viewFindViewById.getParent();
            z = false;
        }
        int top = viewFindViewById.getTop();
        if (z) {
            viewSelfAnchorPadding = baseStyleSelectView.getViewSelfAnchorPadding(viewFindViewById);
        }
        baseStyleSelectView.initSelectViewHeight = top + viewSelfAnchorPadding + ((ViewGroup.MarginLayoutParams) baseStyleSelectView.mSelectContainerView.getChildAt(0).getLayoutParams()).topMargin;
        baseStyleSelectView.resetSelectContainerViewHeight();
        baseStyleSelectView.mSelectContainerView.requestLayout();
        miuiPreDrawListener.removeListener();
        return Boolean.FALSE;
    }

    /* JADX INFO: renamed from: $r8$lambda$ymJEVW7QEs-sd0x0rsOQ9y_gBg4, reason: not valid java name */
    public static /* synthetic */ Boolean m241$r8$lambda$ymJEVW7QEssd0x0rsOQ9y_gBg4(BaseStyleSelectView baseStyleSelectView, MiuiPreDrawListener miuiPreDrawListener) {
        if (baseStyleSelectView.mRootView.getHeight() > 0) {
            baseStyleSelectView.resetSelectContainerViewHeight();
            miuiPreDrawListener.removeListener();
        }
        return Boolean.TRUE;
    }

    /* JADX INFO: renamed from: $r8$lambda$zpVIWQYGLkrqNem2LHhgPi1x-PQ, reason: not valid java name */
    public static /* synthetic */ Boolean m242$r8$lambda$zpVIWQYGLkrqNem2LHhgPi1xPQ(BaseStyleSelectView baseStyleSelectView, MiuiPreDrawListener miuiPreDrawListener) {
        baseStyleSelectView.updateAccessibilityForCoveredViews();
        return Boolean.TRUE;
    }

    public BaseStyleSelectView(Context context) {
        this.mContext = context;
        this.m24HourFormat = DateFormat.is24HourFormat(context);
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    private int clampSelectViewHeight(int i) {
        int height = this.mRootView.getHeight();
        if (height > 0) {
            View view = this.mHeadBottomDivider;
            int top = (height / 2) - (view != null ? view.getTop() : 0);
            if (top > 0 && i > top) {
                return top;
            }
        }
        return i;
    }

    private void configSelectView() {
        if ((this.mStyleInfo.supportBatteryIcon() || this.mStyleInfo.supportNotificationIcon() || this.mStyleInfo.supportDateAndTime() || this.mStyleInfo.supportStepComponent() || this.mStyleInfo.supportLunarTime(this.mContext)) && this.mSlidingViews == null) {
            this.mSupportLunarTime = this.mStyleInfo.supportLunarTime(this.mContext);
            LinearLayout linearLayout = (LinearLayout) this.mSelectContainerView.getChildAt(0);
            WidgetSwitcherGroup widgetSwitcherGroup = (WidgetSwitcherGroup) LayoutInflater.from(this.mContext).inflate(R.layout.widget_switcher_group_layout, (ViewGroup) linearLayout, false);
            this.mSlidingViews = widgetSwitcherGroup;
            linearLayout.addView(widgetSwitcherGroup);
            this.mSlidingViews.enableByStyleInfo(this.mStyleInfo);
            this.mSlidingViews.update(this.mStyleInfo);
            this.mSlidingViews.setCheckChangeListener(new WidgetSwitcherGroup.OnCheckedChangeListener() { // from class: com.miui.aod.components.view.BaseStyleSelectView$$ExternalSyntheticLambda5
                @Override // com.miui.aod.components.widget.WidgetSwitcherGroup.OnCheckedChangeListener
                public final void onCheckedChanged(int i, boolean z) {
                    BaseStyleSelectView.m238$r8$lambda$WFPNscaOthYhJYJ0wuSuGUynWk(this.f$0, i, z);
                }
            });
        }
    }

    private int getAnchorTopMargin(View view) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            return ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).topMargin;
        }
        return 0;
    }

    private int getViewSelfAnchorPadding(View view) {
        int height;
        int i = this.mAnchorOffset;
        int i2 = this.mAnchorMode;
        if (i2 == 0) {
            height = 0;
        } else {
            height = i2 == 1 ? view.getHeight() / 2 : view.getHeight();
        }
        return i + height;
    }

    private boolean isNearly(double d, double d2) {
        return Math.abs(d - d2) < 1.0E-4d;
    }

    private IAodClock updateAODStyle(StyleInfo styleInfo) {
        AodContainerView aodContainerView = this.mPreview;
        if (aodContainerView == null) {
            return null;
        }
        IAodClock iAodClockInflateView = this.mPreviewStyleController.inflateView(aodContainerView, styleInfo);
        this.mPreviewStyleController.handleUpdateTime(this.m24HourFormat);
        return iAodClockInflateView;
    }

    private void updateAccessibilityForCoveredViews() {
        View view;
        AodContainerView aodContainerView = this.mPreview;
        if (aodContainerView == null || (view = this.mPanelContainer) == null) {
            return;
        }
        ViewUtils.applyCoverageForAccessibility(view, aodContainerView);
    }

    private void updatePanelWidth() {
        CoordinatorLayout.LayoutParams layoutParams;
        View view = this.mPanelContainer;
        if (view == null || (layoutParams = (CoordinatorLayout.LayoutParams) view.getLayoutParams()) == null) {
            return;
        }
        if (Utils.isFoldDevice()) {
            int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.style_select_panel_max_width);
            Point windowSize = WindowUtils.INSTANCE.getWindowSize(this.mContext);
            int iMin = Math.min(dimensionPixelSize, windowSize.x);
            int i = (windowSize.x - iMin) / 2;
            ((ViewGroup.MarginLayoutParams) layoutParams).width = iMin;
            ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = i;
            ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = i;
        } else {
            ((ViewGroup.MarginLayoutParams) layoutParams).width = -1;
            ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = 0;
            ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin = 0;
        }
        this.mPanelContainer.setLayoutParams(layoutParams);
    }

    public void applyStyle() {
        this.mSelectPresenter.applyStyleParameter(this.mGson.toJson(this.mStyleInfo));
        EventBus.getDefault().post(new BaseStyleSelectPresenter.ApplyStyleEvent());
    }

    public void enableLunarSlingBtn(boolean z) {
        WidgetSwitcher widgetSwitcher;
        configSelectView();
        WidgetSwitcherGroup widgetSwitcherGroup = this.mSlidingViews;
        if (widgetSwitcherGroup == null || (widgetSwitcher = widgetSwitcherGroup.getWidgetSwitcher(5)) == null) {
            return;
        }
        widgetSwitcher.setEnabled(z);
        widgetSwitcher.findViewById(R.id.slide_btn_container).setClickable(z);
        widgetSwitcher.findViewById(R.id.slide_btn).setClickable(z);
    }

    @Override // com.miui.aod.components.StyleSelectContract.StyleSelectView
    public void freshStatusIfNeeded() {
        if (this.mPendingFreshStepData) {
            GoalLiveData.getInstance().freshData();
            StepLiveData.getInstance().freshData();
            this.mPendingFreshStepData = false;
            return;
        }
        if (!StepUtils.stepActive((StepBean) StepLiveData.getInstance().getValue())) {
            this.mStyleInfo.switchOnStepComponent(false);
            WidgetSwitcherGroup widgetSwitcherGroup = this.mSlidingViews;
            if (widgetSwitcherGroup != null) {
                widgetSwitcherGroup.setCheckedByType(4, this.mStyleInfo.isStepComponentSwitchOn());
            }
        }
        boolean zSupportLunarTime = this.mStyleInfo.supportLunarTime(this.mContext);
        if (zSupportLunarTime != this.mSupportLunarTime) {
            this.mSupportLunarTime = zSupportLunarTime;
            WidgetSwitcherGroup widgetSwitcherGroup2 = this.mSlidingViews;
            if (widgetSwitcherGroup2 != null) {
                widgetSwitcherGroup2.inflateWidget(5);
                if (this.mStyleInfo.isLunarSwitchOn(this.mContext)) {
                    this.mSlidingViews.setCheckedByType(5, true);
                }
            }
            setLunarSlingVisibility(this.mSupportLunarTime ? 0 : 8);
            updateStyleInfoForPreview();
        }
    }

    public View getAodContainerContainerView() {
        return this.mAodContainerContainer;
    }

    @Override // com.miui.aod.components.StyleSelectContract.StyleSelectView
    public BaseStyleSelectPresenter getPresenter() {
        return this.mSelectPresenter;
    }

    public int getSelectContainerViewTopLocation() {
        HeightLimitedNestedScrollView heightLimitedNestedScrollView = this.mSelectContainerView;
        if (heightLimitedNestedScrollView == null) {
            return 0;
        }
        int[] iArr = new int[2];
        heightLimitedNestedScrollView.getLocationInWindow(iArr);
        return iArr[1];
    }

    @Override // com.miui.aod.components.StyleSelectContract.StyleSelectView
    public StyleInfo getStyleInfo() {
        return this.mStyleInfo;
    }

    public final void inflate(ViewGroup viewGroup) {
        this.mRootView = viewGroup;
        AodContainerView aodContainerView = (AodContainerView) viewGroup.findViewById(R.id.clock_container);
        this.mPreview = aodContainerView;
        ViewUtilsKt.doOnMiuiPreDraw(aodContainerView, new Function1() { // from class: com.miui.aod.components.view.BaseStyleSelectView$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return BaseStyleSelectView.$r8$lambda$eC2GTWIoni3LWrAXXDSA2AsVHdQ(this.f$0, (MiuiPreDrawListener) obj);
            }
        });
        ViewUtilsKt.doOnMiuiPreDraw(this.mPreview, new Function1() { // from class: com.miui.aod.components.view.BaseStyleSelectView$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return BaseStyleSelectView.$r8$lambda$qrRUsseHLbXxWtdzz2BK3pWNc1E(this.f$0, (MiuiPreDrawListener) obj);
            }
        });
        initPreviewView(this.mPreview);
        ViewGroup viewGroup2 = (ViewGroup) this.mRootView.findViewById(R.id.aod_content);
        this.mAodContainerContainer = viewGroup2;
        viewGroup2.setOnTouchListener(this.mOnTouchListener);
        this.mAodContainerContainer.setOnClickListener(new View.OnClickListener() { // from class: com.miui.aod.components.view.BaseStyleSelectView$$ExternalSyntheticLambda16
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BaseStyleSelectView baseStyleSelectView = this.f$0;
                baseStyleSelectView.showPreviewMode(!baseStyleSelectView.mPreviewMode);
            }
        });
        View viewFindViewById = this.mRootView.findViewById(R.id.style_select_panel_container);
        this.mPanelContainer = viewFindViewById;
        viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.miui.aod.components.view.BaseStyleSelectView$$ExternalSyntheticLambda17
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BaseStyleSelectView.m236$r8$lambda$LIIB0o5zANrg4uagwZWI0RCrtc(view);
            }
        });
        updatePanelWidth();
        this.mHeadBottomDivider = this.mRootView.findViewById(R.id.head_bottom_divider);
        BottomSheetBehavior<?> bottomSheetBehaviorFrom = BottomSheetBehavior.from(this.mPanelContainer);
        this.mBottomSheetBehavior = bottomSheetBehaviorFrom;
        bottomSheetBehaviorFrom.addBottomSheetCallback(this);
        this.mBottomSheetBehavior.setState(3);
        ViewUtilsKt.doOnMiuiPreDraw(this.mPanelContainer, new Function1() { // from class: com.miui.aod.components.view.BaseStyleSelectView$$ExternalSyntheticLambda18
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return BaseStyleSelectView.m242$r8$lambda$zpVIWQYGLkrqNem2LHhgPi1xPQ(this.f$0, (MiuiPreDrawListener) obj);
            }
        });
        this.mTitleTv = (TextView) this.mRootView.findViewById(R.id.style_select_title);
        this.mApplyButton = (Button) this.mRootView.findViewById(R.id.style_select_apply_btn);
        if (this.mSelectPresenter.needApplyBtn()) {
            this.mApplyButton.setVisibility(0);
            ITouchStyle iTouchStyle = Folme.useAt(this.mApplyButton).touch();
            ITouchStyle.TouchType touchType = ITouchStyle.TouchType.DOWN;
            iTouchStyle.setAlpha(0.5f, touchType).setScale(1.0f, touchType).handleTouchOf(this.mApplyButton, new AnimConfig[0]);
            this.mApplyButton.setOnClickListener(new View.OnClickListener() { // from class: com.miui.aod.components.view.BaseStyleSelectView$$ExternalSyntheticLambda19
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    this.f$0.applyStyle();
                }
            });
        } else {
            this.mApplyButton.setVisibility(8);
        }
        this.mBackIcon = this.mRootView.findViewById(R.id.aod_back);
        ViewUtilsKt.doOnMiuiPreDraw(this.mHeadBottomDivider, new Function1() { // from class: com.miui.aod.components.view.BaseStyleSelectView$$ExternalSyntheticLambda20
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return BaseStyleSelectView.$r8$lambda$ZLVtcaMebslnKM07maPkSbqrok0(this.f$0, (MiuiPreDrawListener) obj);
            }
        });
        HeightLimitedNestedScrollView heightLimitedNestedScrollView = (HeightLimitedNestedScrollView) this.mRootView.findViewById(R.id.style_select_container);
        this.mSelectContainerView = heightLimitedNestedScrollView;
        this.initSelectViewHeight = heightLimitedNestedScrollView.getMaxHeight();
        inflateStyleSelectPanelView(this.mSelectContainerView);
    }

    public abstract void inflateStyleSelectPanelView(ViewGroup viewGroup);

    public void initAnchorView(int i, @ANCHOR_MODE int i2, int i3) {
        this.mAnchorViewId = i;
        this.mAnchorOffset = i3;
        this.mAnchorMode = i2;
    }

    public boolean initPreviewView(ViewGroup viewGroup) {
        return false;
    }

    @Override // com.miui.aod.components.StyleSelectContract.StyleSelectView
    public final IAodClock initStyleInfoSelected(String str, String str2, Bundle bundle) {
        StyleInfo styleInfoCreateStyleInfo;
        this.mCateNameSelected = str;
        this.mStyleInfo = CategoryFactory.createStyleInfo(this.mContext, str, str2);
        if (bundle != null) {
            String string = bundle.getString(KEY_STYLE_INFO);
            if (!TextUtils.isEmpty(string) && (styleInfoCreateStyleInfo = CategoryFactory.createStyleInfo(this.mContext, str, string)) != null && styleInfoCreateStyleInfo.getName().equals(str)) {
                this.mStyleInfo = styleInfoCreateStyleInfo;
            }
        }
        String fullTitle = this.mStyleInfo.getFullTitle();
        int titleResId = this.mStyleInfo.getTitleResId();
        if (TextUtils.isEmpty(fullTitle)) {
            fullTitle = titleResId > 0 ? this.mContext.getResources().getString(titleResId) : null;
        }
        if (TextUtils.isEmpty(fullTitle) || !this.mSelectPresenter.needApplyBtn()) {
            this.mTitleTv.setText(this.mContext.getString(R.string.aod_style));
        } else {
            this.mTitleTv.setText(fullTitle);
        }
        View viewFindViewById = this.mRootView.findViewById(R.id.aod_content);
        int previewTopPaddingResId = this.mStyleInfo.getPreviewTopPaddingResId();
        if (previewTopPaddingResId > 0) {
            viewFindViewById.setPadding(0, this.mRootView.getResources().getDimensionPixelSize(previewTopPaddingResId), 0, 0);
        }
        initStyleInfoSelectedInner(this.mStyleInfo);
        configSelectView();
        if (StepUtils.supportStepComponent() && this.mStyleInfo.supportStepComponent()) {
            GoalLiveData.getInstance().observe((LifecycleOwner) this.mContext, new Observer() { // from class: com.miui.aod.components.view.BaseStyleSelectView$$ExternalSyntheticLambda2
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    Optional.ofNullable(this.f$0.mSlidingViews).ifPresent(new Consumer() { // from class: com.miui.aod.components.view.BaseStyleSelectView$$ExternalSyntheticLambda13
                        @Override // java.util.function.Consumer
                        public final void accept(Object obj2) {
                            ((WidgetSwitcherGroup) obj2).updateStepDescState();
                        }
                    });
                }
            });
            StepLiveData.getInstance().observe((LifecycleOwner) this.mContext, new Observer() { // from class: com.miui.aod.components.view.BaseStyleSelectView$$ExternalSyntheticLambda3
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    BaseStyleSelectView.m239$r8$lambda$avQitkUcJMcGl9Cs_CwqrXSVrI(this.f$0, (StepBean) obj);
                }
            });
        }
        return updateStyleInfoForPreview();
    }

    public void initStyleInfoSelectedInner(StyleInfo styleInfo) {
    }

    @Override // com.miui.aod.components.StyleSelectContract.StyleSelectView
    public void notifyConfigurationChanged() {
        updatePanelWidth();
        HeightLimitedNestedScrollView heightLimitedNestedScrollView = this.mSelectContainerView;
        if (heightLimitedNestedScrollView == null) {
            return;
        }
        ViewUtilsKt.doOnMiuiPreDraw(heightLimitedNestedScrollView, new Function1() { // from class: com.miui.aod.components.view.BaseStyleSelectView$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return BaseStyleSelectView.m241$r8$lambda$ymJEVW7QEssd0x0rsOQ9y_gBg4(this.f$0, (MiuiPreDrawListener) obj);
            }
        });
    }

    public void onDestroy() {
        View view = this.mPanelContainer;
        if (view != null) {
            view.clearAnimation();
            this.mPanelContainer.animate().cancel();
        }
    }

    public void onPreViewTranslateY(float f) {
    }

    public void onSaveInstanceState(Bundle bundle) {
        StyleInfo styleInfo = this.mStyleInfo;
        if (styleInfo != null) {
            bundle.putString(KEY_STYLE_INFO, GsonUtils.toJson(styleInfo));
        }
    }

    @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
    public void onSlide(@NonNull View view, float f) {
        int i = (int) ((1.0f - f) * this.mTranslation);
        AodContainerView aodContainerView = this.mPreview;
        if (!(aodContainerView.mStyleInfo instanceof LinkageClockCategoryInfo)) {
            aodContainerView.setTranslationY(i);
        }
        double d = f;
        if (isNearly(d, 1.0d) || isNearly(d, COLLAPSE_SLIDE_VALUE)) {
            updateAccessibilityForCoveredViews();
        }
        onPreViewTranslateY(i);
    }

    @Override // com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
    public void onStateChanged(@NonNull View view, int i) {
    }

    public void resetSelectContainerViewHeight() {
        HeightLimitedNestedScrollView heightLimitedNestedScrollView = this.mSelectContainerView;
        if (heightLimitedNestedScrollView == null) {
            return;
        }
        heightLimitedNestedScrollView.setMaxHeight(clampSelectViewHeight(this.initSelectViewHeight));
    }

    public void setLunarSlingVisibility(int i) {
        WidgetSwitcher widgetSwitcher;
        configSelectView();
        WidgetSwitcherGroup widgetSwitcherGroup = this.mSlidingViews;
        if (widgetSwitcherGroup == null || (widgetSwitcher = widgetSwitcherGroup.getWidgetSwitcher(5)) == null) {
            return;
        }
        widgetSwitcher.setVisibility(i);
    }

    @Override // com.miui.aod.components.StyleSelectContract.StyleSelectView
    public void setPresenter(BaseStyleSelectPresenter baseStyleSelectPresenter) {
        this.mSelectPresenter = baseStyleSelectPresenter;
    }

    public void setSelectContainerViewHeight(int i) {
        if (this.mSelectContainerView == null) {
            return;
        }
        this.mSelectContainerView.setMaxHeight(Math.max(i, this.initSelectViewHeight));
    }

    public void showPreviewMode(boolean z) {
        Log.d("AodStyleSelect", "showPreviewMode: showPreview=" + z);
        this.mBackIcon.setVisibility(z ? 4 : 0);
        this.mPanelContainer.clearAnimation();
        this.mPreviewMode = z;
        if (!z) {
            this.mPanelContainer.animate().alpha(1.0f).setDuration(300L).setListener(this.mAnimatorListener).start();
        } else {
            EventBus.getDefault().post(new PreviewStateEvent(true));
            this.mPanelContainer.animate().alpha(0.0f).setDuration(300L).setListener(this.mAnimatorListener).start();
        }
    }

    @Deprecated
    public IAodClock updateStyleInfoForPreview() {
        IAodClock iAodClockUpdateAODStyle = updateAODStyle(this.mStyleInfo);
        this.mSelectPresenter.setIAodClock(iAodClockUpdateAODStyle);
        return iAodClockUpdateAODStyle;
    }

    @Override // com.miui.aod.components.StyleSelectContract.StyleSelectView
    public void updateTime() {
        this.mPreviewStyleController.handleUpdateTime(this.m24HourFormat);
    }
}
