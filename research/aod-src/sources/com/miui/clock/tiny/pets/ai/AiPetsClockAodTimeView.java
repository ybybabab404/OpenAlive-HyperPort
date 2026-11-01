package com.miui.clock.tiny.pets.ai;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import com.miui.clock.tiny.R;
import com.miui.clock.tiny.pets.interactive.InteractivePetClockInfoBase;
import com.miui.clock.tiny.pets.interactive.InteractivePetsClockTimeBase;
import com.miui.clock.tiny.widget.BatteryView;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public class AiPetsClockAodTimeView extends InteractivePetsClockTimeBase {
    public BatteryView mBatteryView;
    public ConstraintLayout mBatteryViewLayout;

    public AiPetsClockAodTimeView(Context context) {
        super(context);
    }

    public AiPetsClockAodTimeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AiPetsClockAodTimeView(Context context, AttributeSet attributeSet, int i) {
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
        Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(-2, -2);
        layoutParams.topToTop = this.timeHourLeft.getId();
        layoutParams.leftToLeft = this.timeHourLeft.getId();
        layoutParams.setMargins(getDimen(R.dimen.tiny_aod_ai_pets_date_margin_left), getDimen(R.dimen.tiny_aod_ai_pets_date_margin_top), 0, 0);
        this.mMonth.setTextSize(0, getDimen(R.dimen.tiny_aod_ai_pets_date_text_size));
        this.mMonth.setLayoutParams(layoutParams);
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockTimeBase
    public void initTime() {
        Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(0, 0);
        Constraints.LayoutParams layoutParams2 = new Constraints.LayoutParams(0, 0);
        Constraints.LayoutParams layoutParams3 = new Constraints.LayoutParams(0, 0);
        Constraints.LayoutParams layoutParams4 = new Constraints.LayoutParams(0, 0);
        Constraints.LayoutParams layoutParams5 = new Constraints.LayoutParams(0, 0);
        int dimen = getDimen(R.dimen.tiny_aod_ai_pets_time_width);
        int dimen2 = getDimen(R.dimen.tiny_aod_ai_pets_time_height);
        layoutParams.leftToLeft = getId();
        layoutParams.topToTop = this.clockSpace.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(R.dimen.tiny_aod_ai_pets_dot_width);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(R.dimen.tiny_aod_ai_pets_dot_height);
        layoutParams.setMargins(getDimen(R.dimen.tiny_aod_ai_pets_dot_margin_left), getDimen(R.dimen.tiny_aod_ai_pets_dot_margin_top), 0, 0);
        layoutParams2.leftToLeft = getId();
        layoutParams2.topToTop = this.clockSpace.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = dimen;
        ((ViewGroup.MarginLayoutParams) layoutParams2).height = dimen2;
        layoutParams2.setMargins(0, getDimen(R.dimen.tiny_aod_ai_pets_hour_left_margin_top), 0, 0);
        layoutParams3.leftToLeft = this.timeHourLeft.getId();
        layoutParams3.topToTop = this.timeHourLeft.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams3).width = dimen;
        ((ViewGroup.MarginLayoutParams) layoutParams3).height = dimen2;
        layoutParams3.setMargins(getDimen(R.dimen.tiny_aod_ai_pets_hour_right_margin_left), 0, 0, 0);
        layoutParams4.leftToLeft = this.timeHourLeft.getId();
        layoutParams4.topToTop = this.timeHourLeft.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams4).width = dimen;
        ((ViewGroup.MarginLayoutParams) layoutParams4).height = dimen2;
        layoutParams4.setMargins(getDimen(R.dimen.tiny_aod_ai_pets_minute_margin_left), getDimen(R.dimen.tiny_aod_ai_pets_minute_left_margin_top), 0, 0);
        layoutParams5.leftToLeft = this.timeMinuteLeft.getId();
        layoutParams5.topToTop = this.timeMinuteLeft.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams5).width = dimen;
        ((ViewGroup.MarginLayoutParams) layoutParams5).height = dimen2;
        layoutParams5.setMargins(getDimen(R.dimen.tiny_aod_ai_pets_minute_right_margin_left), getDimen(R.dimen.tiny_aod_ai_pets_minute_right_margin_top), 0, 0);
        this.timeDot.setLayoutParams(layoutParams);
        this.timeHourRight.setLayoutParams(layoutParams3);
        this.timeHourLeft.setLayoutParams(layoutParams2);
        this.timeMinuteLeft.setLayoutParams(layoutParams4);
        this.timeMinuteRight.setLayoutParams(layoutParams5);
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockTimeBase
    public void initWeek() {
        Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(0, 0);
        Constraints.LayoutParams layoutParams2 = new Constraints.LayoutParams(-2, -2);
        layoutParams.topToTop = getId();
        layoutParams.leftToLeft = this.timeMinuteLeft.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(R.dimen.tiny_aod_ai_pets_week_width);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(R.dimen.tiny_aod_ai_pets_week_height);
        int i = R.dimen.tiny_aod_ai_pets_week_margin_left;
        int dimen = getDimen(i);
        int i2 = R.dimen.tiny_aod_ai_pets_week_margin_top;
        layoutParams.setMargins(dimen, getDimen(i2), 0, 0);
        layoutParams2.topToTop = getId();
        layoutParams2.leftToLeft = this.timeMinuteLeft.getId();
        layoutParams2.setMargins(getDimen(i), getDimen(i2), 0, 0);
        this.weekToday.setTextSize(0, getDimen(R.dimen.tiny_aod_ai_pets_week_text_size));
        this.weekBottom.setLayoutParams(layoutParams);
        this.weekToday.setLayoutParams(layoutParams2);
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockTimeBase, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mBatteryView = (BatteryView) findViewById(R.id.battery);
        this.mBatteryViewLayout = (ConstraintLayout) findViewById(R.id.pets_battery);
    }
}
