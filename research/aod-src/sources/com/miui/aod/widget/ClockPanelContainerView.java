package com.miui.aod.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.util.Consumer;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.ClockPanelCategoryInfo;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.DrawableData;
import com.miui.aod.components.view.IPreAodView;
import com.miui.aod.icu.ICUManager;
import com.miui.aod.resource.AodDrawables;
import com.miui.aod.theme.beans.ThemeBean;
import com.miui.aod.util.ShaderUtils;
import com.miui.keyguard.editor.utils.ViewUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;
import java.util.function.Function;
import java.util.stream.Collectors;

/* JADX INFO: loaded from: classes.dex */
public class ClockPanelContainerView extends LinearLayout implements IPreAodView, IAnimatable {
    public GradientTextView mCity;
    public ClockPanelView mClockPanel;
    public StyleInfo mClockPanelCategoryInfo;
    public TextView mDateLunar;
    public TextView mDateView;
    public GradientLinearLayoutForPanel mGradientLinearLayout;
    public Animator mHueAnimator;
    public final List mHueAnimatorViewConfigs;
    public boolean mIsDual;
    public TimeZone mTimeZone;
    public final List mViewNeedAnimate;

    /* JADX INFO: renamed from: $r8$lambda$nfus-7HrvDiZFrD4eR_0niH87Sk, reason: not valid java name */
    public static /* synthetic */ ShaderUtils.ViewConfig m561$r8$lambda$nfus7HrvDiZFrD4eR_0niH87Sk(View view) {
        return new ShaderUtils.ViewConfig(view, 400L, 1500L);
    }

    public ClockPanelContainerView(Context context) {
        super(context);
        this.mViewNeedAnimate = new ArrayList();
        this.mHueAnimatorViewConfigs = new ArrayList();
    }

    public ClockPanelContainerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mViewNeedAnimate = new ArrayList();
        this.mHueAnimatorViewConfigs = new ArrayList();
    }

    public ClockPanelContainerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mViewNeedAnimate = new ArrayList();
        this.mHueAnimatorViewConfigs = new ArrayList();
    }

    @Override // com.miui.aod.widget.IAnimatable
    public void clearAnimationView() {
        Animator animator = this.mHueAnimator;
        if (animator != null) {
            animator.cancel();
        }
        if (this.mClockPanel.isAnimClock() || !(this.mClockPanelCategoryInfo instanceof ClockPanelCategoryInfo)) {
            return;
        }
        resetForAnimation();
        this.mHueAnimatorViewConfigs.clear();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        clearAnimationView();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mClockPanel = (ClockPanelView) findViewById(R.id.clock_panel);
        this.mDateView = (TextView) findViewById(R.id.date);
        this.mDateLunar = (TextView) findViewById(R.id.date_lunar);
        this.mCity = (GradientTextView) findViewById(R.id.city);
        this.mGradientLinearLayout = (GradientLinearLayoutForPanel) findViewById(R.id.date_battery_container);
    }

    public final void resetForAnimation() {
        Iterator it = this.mHueAnimatorViewConfigs.iterator();
        while (it.hasNext()) {
            View view = ((ShaderUtils.ViewConfig) it.next()).view;
            if (view != null) {
                view.setAlpha(0.0f);
            }
        }
    }

    public void setClockMask(final int i, int i2) {
        if (this.mGradientLinearLayout != null) {
            ThemeBean themeBean = this.mClockPanelCategoryInfo.getThemeBean();
            if (themeBean != null && !TextUtils.isEmpty(themeBean.mDateAndBatteryColor)) {
                if (this.mGradientLinearLayout != null) {
                    this.mGradientLinearLayout.setGradientOverlayDrawable(new ColorDrawable(Color.parseColor(themeBean.mDateAndBatteryColor)));
                    return;
                }
                return;
            }
            ClockPanelView clockPanelView = this.mClockPanel;
            if (clockPanelView != null && !clockPanelView.isAnimClock()) {
                StyleInfo styleInfo = this.mClockPanelCategoryInfo;
                if (styleInfo instanceof ClockPanelCategoryInfo) {
                    if (this.mGradientLinearLayout != null) {
                        String secondaryColor = ((ClockPanelCategoryInfo) styleInfo).getSecondaryColor();
                        this.mGradientLinearLayout.setGradientOverlayDrawable(AodDrawables.getDrawableForMask(getContext(), AodDrawables.getDrawableDataByName(secondaryColor) != null ? AodDrawables.getDrawableDataByName(secondaryColor).mResId : ((DrawableData) AodDrawables.sPureDrawableList.get(0)).mResId));
                        for (View view : this.mViewNeedAnimate) {
                            if (view instanceof GradientLinearLayout) {
                                ((GradientLinearLayout) view).setGradientOverlayDrawable(this.mGradientLinearLayout.getGradientOverlayDrawable());
                                view.invalidate();
                            }
                        }
                    }
                    if (this.mCity != null) {
                        DrawableData drawableDataByName = AodDrawables.getDrawableDataByName(this.mClockPanelCategoryInfo.getColorName());
                        if (drawableDataByName == null) {
                            drawableDataByName = (DrawableData) AodDrawables.sPureDrawableList.get(0);
                        }
                        this.mCity.setGradientOverlayDrawable(AodDrawables.getDrawableForMask(getContext(), drawableDataByName.mResId));
                        return;
                    }
                    return;
                }
            }
            ViewUtil.doIfNotNull(this.mGradientLinearLayout, new Consumer() { // from class: com.miui.aod.widget.ClockPanelContainerView$$ExternalSyntheticLambda1
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    GradientLinearLayoutForPanel gradientLinearLayoutForPanel = (GradientLinearLayoutForPanel) obj;
                    gradientLinearLayoutForPanel.setGradientOverlayDrawable(AodDrawables.getDrawableForMask(this.f$0.getContext(), i));
                }
            });
        }
    }

    public void setIsDual(boolean z) {
        this.mIsDual = z;
    }

    public void setTimeZone(TimeZone timeZone) {
        this.mTimeZone = timeZone;
    }

    public void setViewNeedAnimate(List<View> list) {
        this.mViewNeedAnimate.clear();
        this.mViewNeedAnimate.addAll(list);
    }

    @Override // com.miui.aod.widget.IAnimatable
    public void startAnimation(AnimatorListenerAdapter animatorListenerAdapter) {
        if (this.mClockPanel.isAnimClock() || !(this.mClockPanelCategoryInfo instanceof ClockPanelCategoryInfo)) {
            return;
        }
        this.mHueAnimatorViewConfigs.add(new ShaderUtils.ViewConfig(this.mCity));
        this.mHueAnimatorViewConfigs.add(new ShaderUtils.ViewConfig(this.mClockPanel.mPanel));
        this.mHueAnimatorViewConfigs.add(new ShaderUtils.ViewConfig(this.mClockPanel.mMinuteHand));
        this.mHueAnimatorViewConfigs.add(new ShaderUtils.ViewConfig(this.mClockPanel.mHourHand));
        this.mHueAnimatorViewConfigs.add(new ShaderUtils.ViewConfig(this.mClockPanel.mCenterDot));
        this.mHueAnimatorViewConfigs.add(new ShaderUtils.ViewConfig(this.mClockPanel.mCenterDot));
        if (this.mGradientLinearLayout != null) {
            this.mHueAnimatorViewConfigs.add(new ShaderUtils.ViewConfig(this.mGradientLinearLayout, 400L, 1500L));
        }
        List list = this.mViewNeedAnimate;
        if (list != null) {
            this.mHueAnimatorViewConfigs.addAll((Collection) list.stream().map(new Function() { // from class: com.miui.aod.widget.ClockPanelContainerView$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ClockPanelContainerView.m561$r8$lambda$nfus7HrvDiZFrD4eR_0niH87Sk((View) obj);
                }
            }).collect(Collectors.toList()));
        }
        this.mHueAnimator = ShaderUtils.hueAnimateIn(animatorListenerAdapter, 1, (ShaderUtils.ViewConfig[]) this.mHueAnimatorViewConfigs.toArray(new ShaderUtils.ViewConfig[this.mHueAnimatorViewConfigs.size()]));
    }

    @Override // com.miui.aod.components.view.IPreAodView
    public void update(final StyleInfo styleInfo, final int i) {
        int dimensionPixelOffset;
        int dimensionPixelOffset2;
        int i2;
        int dimensionPixelSize;
        final int i3;
        int dimensionPixelSize2;
        this.mClockPanelCategoryInfo = styleInfo;
        ViewUtil.doIfNotNull(this.mClockPanel, new Consumer() { // from class: com.miui.aod.widget.ClockPanelContainerView$$ExternalSyntheticLambda2
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ClockPanelView clockPanelView = (ClockPanelView) obj;
                clockPanelView.init(i, this.f$0.mIsDual, styleInfo);
            }
        });
        int dimensionPixelSize3 = 0;
        if (i == 2 || i == 4) {
            int dimensionPixelSize4 = this.mIsDual ? getResources().getDimensionPixelSize(R.dimen.clock_panel_margin_s) : 0;
            dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.aod_clock_panel_padding_top_s);
            dimensionPixelOffset2 = getResources().getDimensionPixelOffset(R.dimen.clock_panel_margin_s);
            ViewUtil.setVisibility(this.mCity, 8);
            i2 = R.style.Aod_clock_panel_dual_city_s;
            dimensionPixelSize = dimensionPixelSize4;
            i3 = R.style.Aod_date_s;
            dimensionPixelSize2 = 0;
        } else {
            i3 = this.mIsDual ? R.style.Aod_date_dual : R.style.Aod_date_horizontal;
            dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.clock_panel_margin);
            dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.lunar_date_top_margin);
            dimensionPixelOffset = getResources().getDimensionPixelOffset(R.dimen.aod_clock_panel_padding_top);
            dimensionPixelOffset2 = getResources().getDimensionPixelOffset(R.dimen.clock_panel_margin);
            i2 = R.style.Aod_clock_panel_dual_city;
        }
        ViewUtil.setMargins(this.mDateView, 0, dimensionPixelSize, 0, 0);
        ViewUtil.setMargins(this.mDateLunar, 0, dimensionPixelSize2, 0, 0);
        GradientTextView gradientTextView = this.mCity;
        if (gradientTextView != null) {
            gradientTextView.setTextAppearance(i2);
            this.mCity.setPadding(0, dimensionPixelOffset, 0, 0);
            ViewUtil.setMarginBottom(this.mCity, dimensionPixelOffset2);
        }
        if (this.mTimeZone == null || !this.mIsDual) {
            GradientTextView gradientTextView2 = this.mCity;
            if (gradientTextView2 != null) {
                gradientTextView2.setVisibility(Utils.isAodProcess() ? 4 : 8);
                this.mCity.setText((CharSequence) null);
            }
        } else {
            GradientTextView gradientTextView3 = this.mCity;
            if (gradientTextView3 != null) {
                ICUManager.INSTANCE.loadCity(gradientTextView3, getContext(), this.mTimeZone);
            }
            dimensionPixelSize3 = getResources().getDimensionPixelSize(R.dimen.clock_panel_gaps);
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) getLayoutParams();
        layoutParams.leftMargin = dimensionPixelSize3;
        layoutParams.rightMargin = dimensionPixelSize3;
        setLayoutParams(layoutParams);
        View viewFindViewById = findViewById(R.id.battery_step_container);
        if (this.mIsDual && viewFindViewById != null) {
            ((ViewGroup) viewFindViewById.getParent()).removeView(viewFindViewById);
        }
        ViewUtil.doIfNotNull(this.mDateView, new Consumer() { // from class: com.miui.aod.widget.ClockPanelContainerView$$ExternalSyntheticLambda3
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ((TextView) obj).setTextAppearance(i3);
            }
        });
        ViewUtil.doIfNotNull(this.mDateLunar, new Consumer() { // from class: com.miui.aod.widget.ClockPanelContainerView$$ExternalSyntheticLambda4
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ((TextView) obj).setTextAppearance(i3);
            }
        });
        ViewUtil.doIfNotNull(this.mClockPanel, new Consumer() { // from class: com.miui.aod.widget.ClockPanelContainerView$$ExternalSyntheticLambda5
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ((ClockPanelView) obj).update(styleInfo, i);
            }
        });
    }
}
