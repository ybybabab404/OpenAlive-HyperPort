package com.miui.aod.widget;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.aod.R;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.resource.AodDrawables;

/* JADX INFO: loaded from: classes.dex */
public class OneLineClock extends HorizontalClock {
    public OnelineClockView mClockView;
    public final boolean mIsDual;
    public final int mSize;

    public OneLineClock(int i, boolean z) {
        super(i, z, 17);
        this.mSize = i;
        this.mIsDual = z;
    }

    @Override // com.miui.aod.widget.HorizontalClock, com.miui.aod.widget.IAodClock
    public void bindView(View view) {
        this.mContext = view.getContext();
        OnelineClockView onelineClockView = (OnelineClockView) view.findViewById(R.id.clock);
        this.mClockView = onelineClockView;
        onelineClockView.setSize(this.mSize);
        this.mDateView = (TextView) view.findViewById(R.id.date);
        this.mDateLunar = (TextView) view.findViewById(R.id.date_lunar);
        this.mClockHorizontal = (TextView) view.findViewById(R.id.clock_horizontal);
        this.mCity = (TextView) view.findViewById(R.id.city);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.gradient_layout);
        this.mGradientLayout = linearLayout;
        int i = this.mSize;
        if (i != 3 && i != 5 && linearLayout != null) {
            linearLayout.setPadding(0, 0, 0, 0);
        }
        if (this.mIsDual) {
            this.mDateViewDual = (TextView) view.findViewById(R.id.date_dual);
            this.mCity2 = (TextView) view.findViewById(R.id.city2);
        }
    }

    @Override // com.miui.aod.widget.HorizontalClock, com.miui.aod.widget.IAodClock
    public int getLayoutResource(StyleInfo styleInfo) {
        if (this.mSize == 2) {
            this.mLayoutId = R.layout.aod_content_oneline_small;
        } else if (this.mIsDual) {
            this.mLayoutId = R.layout.aod_content_oneline_dual;
        } else {
            this.mLayoutId = R.layout.aod_content_oneline;
        }
        return this.mLayoutId;
    }

    @Override // com.miui.aod.widget.HorizontalClock, com.miui.aod.widget.IAodClock
    public void setClockMask(int i, int i2) {
        if (i2 > 0) {
            LinearLayout linearLayout = this.mGradientLayout;
            if (linearLayout instanceof GradientLinearLayout) {
                ((GradientLinearLayout) linearLayout).setGradientOverlayDrawable(AodDrawables.getDrawableForMask(this.mContext, i2));
            }
        }
    }

    @Override // com.miui.aod.widget.HorizontalClock, com.miui.aod.components.view.IPreAodView
    public void update(StyleInfo styleInfo, int i) {
        super.update(styleInfo, i);
        if (this.mClockView == null || styleInfo == null || !styleInfo.isForAodNotification()) {
            return;
        }
        this.mClockView.setStyleInfo(styleInfo);
        this.mClockView.setSize(i);
    }

    @Override // com.miui.aod.widget.HorizontalClock, com.miui.aod.widget.IAodClock
    public void updateTime(boolean z) {
        super.updateTime(z);
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
