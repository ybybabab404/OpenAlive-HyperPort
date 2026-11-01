package com.miui.aod.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.ClockPanelCategoryInfo;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.view.IPreAodView;
import com.miui.aod.icu.ICUManager;
import com.miui.aod.resource.AodDrawables;
import com.miui.aod.util.ShaderUtils;
import java.text.SimpleDateFormat;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class DualClockPanelContainerView extends LinearLayout implements IPreAodView, IAnimatable {
    public TextView mCity;
    public TextView mCity2;
    public ClockPanelView mClockPanel;
    public StyleInfo mClockPanelCategoryInfo;
    public TextView mDateLunar;
    public TextView mDateView;
    public TextView mDateViewDual;
    public GradientLinearLayoutForPanel mGradientLinearLayout;
    public Animator mHueAnimator;
    public TimeZone mTimeZone;
    public TimeZone mTimeZone2;

    public DualClockPanelContainerView(Context context) {
        super(context);
    }

    public DualClockPanelContainerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public DualClockPanelContainerView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.miui.aod.widget.IAnimatable
    public void clearAnimationView() {
        Animator animator = this.mHueAnimator;
        if (animator != null) {
            animator.cancel();
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mClockPanel = (ClockPanelView) findViewById(R.id.clock_panel);
        this.mDateView = (TextView) findViewById(R.id.date);
        this.mDateViewDual = (TextView) findViewById(R.id.date_dual);
        this.mDateLunar = (TextView) findViewById(R.id.date_lunar);
        this.mCity = (TextView) findViewById(R.id.city);
        this.mCity2 = (TextView) findViewById(R.id.city2);
        this.mGradientLinearLayout = (GradientLinearLayoutForPanel) findViewById(R.id.date_battery_container);
    }

    public void setClockMask(int i, int i2) {
        this.mGradientLinearLayout.setGradientOverlayDrawable(AodDrawables.getDrawableForMask(getContext(), i));
    }

    public void setClockTime(Context context, Calendar calendar, Calendar calendar2, boolean z, StyleInfo styleInfo) {
        new SimpleDateFormat(Utils.getHourMinformat(context)).setTimeZone(this.mTimeZone);
        int i = calendar.get(18);
        if (!z && i > 12) {
            i -= 12;
        }
        int i2 = (z || i != 0) ? i : 12;
        ClockPanelView clockPanelView = this.mClockPanel;
        if (clockPanelView != null) {
            clockPanelView.setTime(i2, calendar.get(20));
        }
        this.mDateView.setText(calendar.format(context, AODSettings.getDateFormat(context, z, false)));
        if (this.mDateLunar != null && styleInfo.isLunarSwitchOn(context)) {
            this.mDateLunar.setText(calendar.format(context, AODSettings.getDateFormat(context, z, true)));
        }
        String string = getResources().getString(z ? R.string.aod_dual_clock_date : R.string.aod_dual_clock_date_12);
        if (AODSettings.isSameDate(calendar, calendar2)) {
            string = getResources().getString(z ? R.string.aod_dual_togother : R.string.aod_dual_togother_12);
        }
        this.mDateViewDual.setText(calendar2.format(getContext(), string));
        ICUManager iCUManager = ICUManager.INSTANCE;
        iCUManager.loadCity(this.mCity, getContext(), TimeZone.getDefault());
        iCUManager.loadCity(this.mCity2, getContext(), this.mTimeZone2);
    }

    public void setTimeZone(TimeZone timeZone) {
        this.mTimeZone = timeZone;
    }

    public void setTimeZone2(TimeZone timeZone) {
        this.mTimeZone2 = timeZone;
    }

    @Override // com.miui.aod.widget.IAnimatable
    public void startAnimation(AnimatorListenerAdapter animatorListenerAdapter) {
        if (this.mClockPanel.isAnimClock() || !(this.mClockPanelCategoryInfo instanceof ClockPanelCategoryInfo)) {
            return;
        }
        this.mHueAnimator = ShaderUtils.hueAnimateIn(animatorListenerAdapter, 1, new ShaderUtils.ViewConfig(this.mCity), new ShaderUtils.ViewConfig(this.mClockPanel.mPanel), new ShaderUtils.ViewConfig(this.mClockPanel.mMinuteHand), new ShaderUtils.ViewConfig(this.mClockPanel.mHourHand), new ShaderUtils.ViewConfig(this.mClockPanel.mCenterDot), new ShaderUtils.ViewConfig(this.mGradientLinearLayout, 400L, 1500L));
    }

    @Override // com.miui.aod.components.view.IPreAodView
    public void update(StyleInfo styleInfo, int i) {
        this.mClockPanelCategoryInfo = styleInfo;
        this.mClockPanel.init(i, true, styleInfo);
        this.mClockPanel.update(styleInfo, i);
    }
}
