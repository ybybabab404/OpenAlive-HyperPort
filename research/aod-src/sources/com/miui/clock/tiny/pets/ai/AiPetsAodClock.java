package com.miui.clock.tiny.pets.ai;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import com.miui.clock.tiny.R;
import com.miui.clock.tiny.Util;
import com.miui.clock.tiny.pets.interactive.InteractivePetsClockBase;
import com.miui.clock.tiny.utils.FontUtils;
import com.miui.clock.tiny.widget.BatteryView;

/* JADX INFO: loaded from: classes.dex */
public class AiPetsAodClock extends InteractivePetsClockBase {
    public BatteryView mBatteryView;
    public ConstraintLayout mBatteryViewLayout;
    public boolean showInAod;

    public AiPetsAodClock(Context context) {
        super(context);
    }

    public AiPetsAodClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public AiPetsAodClock(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private float getSmallScaleMultiple() {
        return (getDimen(R.dimen.tiny_aod_ai_pets_clock_width) * 1.0f) / getDimen(R.dimen.tiny_pets_time_width);
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockBase
    public void initBatteryView() {
        this.mClockInfo.getRotation();
        Constraints.LayoutParams layoutParams = new Constraints.LayoutParams(-2, -2);
        layoutParams.leftToLeft = 0;
        layoutParams.rightToRight = 0;
        layoutParams.topToTop = 0;
        layoutParams.setMargins(0, getDimen(R.dimen.tiny_aod_ai_pets_battery_margin_top), 0, 0);
        this.mBatteryViewLayout.setLayoutParams(layoutParams);
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockBase
    public void initClockView(int i) {
        this.clockView.setPivotX(0.0f);
        this.clockView.setPivotY(0.0f);
        this.clockView.setScaleX(getSmallScaleMultiple());
        this.clockView.setScaleY(getSmallScaleMultiple());
        ((TextView) this.clockView.findViewById(R.id.week)).setTextSize(0, (getDimen(FontUtils.isTibetan(getContext()) ? R.dimen.tiny_aod_two_ai_pets_tibetan_week_text_size : R.dimen.tiny_aod_two_ai_pets_week_text_size) * 1.0f) / getSmallScaleMultiple());
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockBase
    public void initSelf(int i) {
        boolean z = i == 0;
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        int i2 = z ? R.dimen.tiny_aod_ai_pets_margin_top_0 : R.dimen.tiny_aod_ai_pets_margin_top_180;
        setPadding(this.showInAod ? 0 : getDimen(z ? R.dimen.tiny_aod_ai_pets_margin_left_0 : R.dimen.tiny_aod_ai_pets_margin_left_180), getDimen(i2), 0, 0);
        layoutParams.width = getDimen(R.dimen.tiny_aod_ai_pets_width);
        layoutParams.height = getDimen(R.dimen.tiny_aod_ai_pets_height) + getDimen(i2);
    }

    @Override // com.miui.clock.tiny.pets.interactive.InteractivePetsClockBase, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mBatteryView = (BatteryView) findViewById(R.id.battery);
        this.mBatteryViewLayout = (ConstraintLayout) findViewById(R.id.pets_battery);
        this.showInAod = Util.isAOD(getContext());
    }
}
