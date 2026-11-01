package com.miui.clock.tiny.pets.interactive;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.tiny.R;

/* JADX INFO: loaded from: classes.dex */
public class InteractivePetsClockAodTimeView extends InteractivePetsClockTimeBase {
    public InteractivePetsClockAodTimeView(Context context) {
        super(context);
    }

    public InteractivePetsClockAodTimeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public InteractivePetsClockAodTimeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockTimeBase
    public int getDimen(int i) {
        return (int) (super.getDimen(i) * ((super.getDimen(R.dimen.tiny_aod_pets_time_width2) * 1.0f) / super.getDimen(R.dimen.tiny_aod_pets_time_width)));
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockTimeBase
    public void initColor() {
        InteractivePetClockInfoBase interactivePetClockInfoBase = this.mInfo;
        this.mInfo.setPrimaryColor(interactivePetClockInfoBase.getClockColorStyleInfo(interactivePetClockInfoBase.getRotation()).getPrimaryColor());
        int primaryColor = this.mInfo.getPrimaryColor();
        this.weekToday.setTextColor(-1);
        this.mMonth.setTextColor(primaryColor);
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockTimeBase
    public void initDate() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mMonth.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = -2;
        ((ViewGroup.MarginLayoutParams) layoutParams).height = -2;
        layoutParams.bottomToTop = this.weekBottom.getId();
        layoutParams.startToStart = this.weekBottom.getId();
        layoutParams.endToEnd = this.weekBottom.getId();
        this.mMonth.setTextSize(0, getDimen(R.dimen.tiny_aod_interactive_pets_date_text_size));
        this.mMonth.setLayoutParams(layoutParams);
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockTimeBase
    public void initTime() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.timeDot.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.timeHourLeft.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.timeHourRight.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) this.timeMinuteLeft.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams5 = (ConstraintLayout.LayoutParams) this.timeMinuteRight.getLayoutParams();
        int i = R.dimen.tiny_aod_pets_time_width;
        getDimen(i);
        layoutParams.leftToLeft = getId();
        layoutParams.topToTop = this.clockSpace.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(R.dimen.tiny_aod_pets_dot_width);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(R.dimen.tiny_aod_pets_dot_height);
        layoutParams.setMargins(getDimen(R.dimen.tiny_aod_pets_dot_margin_left), getDimen(R.dimen.tiny_aod_pets_dot_margin_top), 0, 0);
        layoutParams2.leftToLeft = getId();
        layoutParams2.topToTop = this.clockSpace.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = getDimen(i);
        int i2 = R.dimen.tiny_aod_pets_time_height;
        ((ViewGroup.MarginLayoutParams) layoutParams2).height = getDimen(i2);
        layoutParams3.leftToLeft = this.timeHourLeft.getId();
        layoutParams3.topToTop = this.timeHourLeft.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams3).width = getDimen(i);
        ((ViewGroup.MarginLayoutParams) layoutParams3).height = getDimen(i2);
        layoutParams3.setMargins(getDimen(R.dimen.tiny_aod_pets_hour_right_margin_left), getDimen(R.dimen.tiny_aod_pets_hour_right_margin_top), 0, 0);
        layoutParams4.leftToLeft = getId();
        layoutParams4.topToTop = getId();
        ((ViewGroup.MarginLayoutParams) layoutParams4).width = getDimen(i);
        ((ViewGroup.MarginLayoutParams) layoutParams4).height = getDimen(i2);
        layoutParams4.setMargins(getDimen(R.dimen.tiny_aod_pets_minute_margin_left), getDimen(R.dimen.tiny_aod_pets_minute_left_margin_top), 0, 0);
        layoutParams5.leftToLeft = this.timeMinuteLeft.getId();
        layoutParams5.topToTop = getId();
        ((ViewGroup.MarginLayoutParams) layoutParams5).width = getDimen(i);
        ((ViewGroup.MarginLayoutParams) layoutParams5).height = getDimen(i2);
        layoutParams5.setMargins(getDimen(R.dimen.tiny_aod_pets_minute_right_margin_left), getDimen(R.dimen.tiny_aod_pets_minute_right_margin_top), 0, 0);
        this.timeDot.setLayoutParams(layoutParams);
        this.timeHourRight.setLayoutParams(layoutParams3);
        this.timeHourLeft.setLayoutParams(layoutParams2);
        this.timeMinuteLeft.setLayoutParams(layoutParams4);
        this.timeMinuteRight.setLayoutParams(layoutParams5);
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockTimeBase
    public void initWeek() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.weekBottom.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.weekToday.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = -2;
        ((ViewGroup.MarginLayoutParams) layoutParams2).height = -2;
        layoutParams.topToTop = getId();
        layoutParams.leftToLeft = getId();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(R.dimen.tiny_aod_interactive_pets_week_width);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(R.dimen.tiny_aod_interactive_pets_week_height);
        int i = R.dimen.tiny_aod_interactive_pets_week_margin_left;
        int dimen = getDimen(i);
        int i2 = R.dimen.tiny_aod_interactive_pets_week_margin_top;
        layoutParams.setMargins(dimen, getDimen(i2), 0, 0);
        layoutParams2.topToTop = getId();
        layoutParams2.leftToLeft = getId();
        layoutParams2.setMargins(getDimen(i), getDimen(i2), 0, 0);
        this.weekToday.setTextSize(0, getDimen(R.dimen.tiny_aod_interactive_pets_week_text_size));
        this.weekBottom.setLayoutParams(layoutParams);
        this.weekToday.setLayoutParams(layoutParams2);
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockTimeBase
    public void updateTime() {
        super.updateTime();
        int primaryColor = this.mInfo.getPrimaryColor();
        this.weekToday.setTextColor(-1);
        this.mMonth.setTextColor(primaryColor);
    }
}
