package com.miui.aod.widget;

import android.view.View;
import android.widget.TextView;
import com.miui.aod.R;
import com.miui.aod.common.StyleInfo;

/* JADX INFO: loaded from: classes.dex */
public class VerticalClock extends HorizontalClock {
    public TextView mClockHourView;
    public TextView mClockMinuteView;
    public VerticalClockView mClockView;
    public int mSize;

    public VerticalClock(int i, boolean z) {
        super(i, z, 17);
        this.mSize = i;
    }

    @Override // com.miui.aod.widget.HorizontalClock, com.miui.aod.widget.IAodClock
    public void bindView(View view) {
        this.mContext = view.getContext();
        VerticalClockView verticalClockView = (VerticalClockView) view.findViewById(R.id.clock);
        this.mClockView = verticalClockView;
        verticalClockView.setSize(this.mSize, this.mIsDual);
        this.mClockHourView = (TextView) view.findViewById(R.id.clock_hour);
        this.mClockMinuteView = (TextView) view.findViewById(R.id.clock_minute);
        this.mDateView = (TextView) view.findViewById(R.id.date);
        this.mDateLunar = (TextView) view.findViewById(R.id.date_lunar);
        this.mClockHorizontal = (TextView) view.findViewById(R.id.clock_horizontal);
        this.mCity = (TextView) view.findViewById(R.id.city);
        this.mGradientLayout = (GradientLinearLayout) view.findViewById(R.id.gradient_layout);
        if (this.mIsDual) {
            this.mDateViewDual = (TextView) view.findViewById(R.id.date_dual);
            this.mCity2 = (TextView) view.findViewById(R.id.city2);
        }
    }

    @Override // com.miui.aod.widget.HorizontalClock, com.miui.aod.widget.IAodClock
    public int getLayoutResource(StyleInfo styleInfo) {
        if (this.mSize == 2) {
            this.mLayoutId = R.layout.aod_content_vertical_small;
        } else if (this.mIsDual) {
            this.mLayoutId = R.layout.aod_content_vertical_dual;
        } else {
            this.mLayoutId = R.layout.aod_content_vertical;
        }
        return this.mLayoutId;
    }

    @Override // com.miui.aod.widget.HorizontalClock, com.miui.aod.widget.IAodClock
    public void updateTime(boolean z) {
        super.updateTime(z);
        TextView textView = this.mClockMinuteView;
        if (textView != null) {
            textView.setText(String.format("%02d", Integer.valueOf(this.mCal.get(20))));
        }
        if (this.mClockHourView != null) {
            int i = this.mCal.get(18);
            if (!z && i > 12) {
                i -= 12;
            }
            this.mClockHourView.setText(String.format("%02d", Integer.valueOf((z || i != 0) ? i : 12)));
        }
        if (!this.mIsDual || this.mDateViewDual == null) {
            return;
        }
        String string = this.mContext.getResources().getString(z ? R.string.aod_dual_clock_date : R.string.aod_dual_clock_date_12);
        if (AODSettings.isSameDate(this.mCal, this.mCalDual)) {
            string = this.mContext.getResources().getString(z ? R.string.aod_dual_togother : R.string.aod_dual_togother_12);
        }
        this.mDateViewDual.setText(this.mCalDual.format(this.mContext, string));
    }
}
