package com.miui.clock.tiny.pets.interactive;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.tiny.R;
import com.miui.clock.tiny.utils.FontUtils;

/* JADX INFO: loaded from: classes.dex */
public class InteractivePetsClockTimeView extends InteractivePetsClockTimeBase {
    public InteractivePetsClockTimeView(Context context) {
        super(context);
    }

    public InteractivePetsClockTimeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public InteractivePetsClockTimeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockTimeBase
    public void initColor() {
        InteractivePetClockInfoBase interactivePetClockInfoBase = this.mInfo;
        this.mInfo.setPrimaryColor(interactivePetClockInfoBase.getClockColorStyleInfo(interactivePetClockInfoBase.getRotation()).getPrimaryColor());
        this.weekToday.setTextColor(this.mInfo.getPrimaryColor());
        this.mMonth.setTextColor(-1);
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockTimeBase
    public void initDate() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mMonth.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = -2;
        ((ViewGroup.MarginLayoutParams) layoutParams).height = -2;
        layoutParams.topToTop = getId();
        layoutParams.leftToLeft = this.weekBottom.getId();
        layoutParams.rightToRight = this.weekBottom.getId();
        layoutParams.setMargins(0, getDimen(R.dimen.tiny_interactive_pets_date_margin_top), 0, 0);
        this.mMonth.setTextSize(0, getDimen(R.dimen.tiny_interactive_pets_date_text_size_180));
        this.mMonth.setLayoutParams(layoutParams);
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.weekBottom.getLayoutParams();
        layoutParams2.topToTop = getId();
        layoutParams2.leftToLeft = this.timeMinuteLeft.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = getDimen(R.dimen.tiny_interactive_pets_week_width);
        ((ViewGroup.MarginLayoutParams) layoutParams2).height = getDimen(R.dimen.tiny_interactive_pets_week_height);
        layoutParams2.setMargins(getDimen(R.dimen.tiny_interactive_pets_date_bottom_left), getDimen(R.dimen.tiny_interactive_pets_date_bottom_margin_top), 0, 0);
        this.weekBottom.setLayoutParams(layoutParams2);
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockTimeBase
    public void initTime() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.timeDot.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.timeHourLeft.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.timeHourRight.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) this.timeMinuteLeft.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams5 = (ConstraintLayout.LayoutParams) this.timeMinuteRight.getLayoutParams();
        layoutParams.leftToLeft = getId();
        layoutParams.topToTop = this.clockSpace.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(R.dimen.tiny_pets_dot_width);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(R.dimen.tiny_pets_dot_height);
        layoutParams.setMargins(getDimen(R.dimen.tiny_pets_dot_margin_left), getDimen(R.dimen.tiny_pets_dot_margin_top), 0, 0);
        layoutParams2.leftToLeft = getId();
        layoutParams2.topToTop = this.clockSpace.getId();
        int i = R.dimen.tiny_pets_time_width;
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = getDimen(i);
        int i2 = R.dimen.tiny_pets_time_height;
        ((ViewGroup.MarginLayoutParams) layoutParams2).height = getDimen(i2);
        layoutParams3.leftToLeft = this.timeHourLeft.getId();
        layoutParams3.topToTop = this.timeHourLeft.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams3).width = getDimen(i);
        ((ViewGroup.MarginLayoutParams) layoutParams3).height = getDimen(i2);
        layoutParams3.setMargins(getDimen(R.dimen.tiny_pets_hour_right_margin_left), 0, 0, 0);
        layoutParams4.leftToLeft = getId();
        layoutParams4.topToTop = getId();
        ((ViewGroup.MarginLayoutParams) layoutParams4).width = getDimen(i);
        ((ViewGroup.MarginLayoutParams) layoutParams4).height = getDimen(i2);
        layoutParams4.setMargins(getDimen(R.dimen.tiny_pets_minute_margin_left), getDimen(R.dimen.tiny_pets_minute_left_margin_top), 0, 0);
        layoutParams5.leftToLeft = this.timeMinuteLeft.getId();
        layoutParams5.topToTop = getId();
        ((ViewGroup.MarginLayoutParams) layoutParams5).width = getDimen(i);
        ((ViewGroup.MarginLayoutParams) layoutParams5).height = getDimen(i2);
        layoutParams5.setMargins(getDimen(R.dimen.tiny_pets_minute_right_margin_left), getDimen(R.dimen.tiny_pets_minute_right_margin_top), 0, 0);
        this.timeDot.setLayoutParams(layoutParams);
        this.timeHourRight.setLayoutParams(layoutParams3);
        this.timeHourLeft.setLayoutParams(layoutParams2);
        this.timeMinuteLeft.setLayoutParams(layoutParams4);
        this.timeMinuteRight.setLayoutParams(layoutParams5);
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockTimeBase
    public void initWeek() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.weekToday.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = -2;
        ((ViewGroup.MarginLayoutParams) layoutParams).height = -2;
        layoutParams.topToTop = this.timeHourLeft.getId();
        layoutParams.leftToLeft = this.timeHourLeft.getId();
        boolean zIsTibetan = FontUtils.isTibetan(getContext());
        layoutParams.setMargins(getDimen(R.dimen.tiny_interactive_pets_week_margin_left), getDimen(zIsTibetan ? R.dimen.tiny_interactive_pets_week_tibetan_margin_top : R.dimen.tiny_interactive_pets_week_margin_top), 0, 0);
        this.weekToday.setTextSize(0, getDimen(zIsTibetan ? R.dimen.tiny_interactive_pets_tibetan_week_text_size : R.dimen.tiny_interactive_pets_week_text_size));
        this.weekToday.setLayoutParams(layoutParams);
    }
}
