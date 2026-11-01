package com.miui.aod.widget;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.aod.R;
import com.miui.aod.common.StyleInfo;

/* JADX INFO: loaded from: classes.dex */
public class ScopeClock extends HorizontalClock {
    public ScopeClockView mClockView;
    public final String mPanel;
    public final int mSize;

    public ScopeClock(int i, boolean z, String str) {
        super(i, z, 17);
        this.mSize = i;
        this.mPanel = str;
    }

    @Override // com.miui.aod.widget.HorizontalClock, com.miui.aod.widget.IAodClock
    public void bindView(View view) {
        this.mContext = view.getContext();
        ScopeClockView scopeClockView = (ScopeClockView) view.findViewById(R.id.clock_panel);
        this.mClockView = scopeClockView;
        scopeClockView.setSize(this.mSize, this.mPanel);
        this.mDateView = (TextView) view.findViewById(R.id.date);
        this.mDateLunar = (TextView) view.findViewById(R.id.date_lunar);
        this.mClockHorizontal = (TextView) view.findViewById(R.id.clock_horizontal);
        this.mCity = (TextView) view.findViewById(R.id.city);
        this.mGradientLayout = (LinearLayout) view.findViewById(R.id.gradient_layout);
        this.mClockHorizontal = (TextView) view.findViewById(R.id.clock_horizontal);
        int i = this.mSize;
        if (i == 3 || i == 5) {
            this.mClockHorizontal = (TextView) view.findViewById(R.id.clock_horizontal);
        } else {
            LinearLayout linearLayout = this.mGradientLayout;
            if (linearLayout != null) {
                linearLayout.setPadding(0, 0, 0, 0);
            }
        }
        if (this.mIsDual) {
            this.mCity2 = (TextView) view.findViewById(R.id.city2);
            this.mDateViewDual = (TextView) view.findViewById(R.id.date_dual);
        }
    }

    @Override // com.miui.aod.widget.HorizontalClock, com.miui.aod.widget.IAodClock
    public int getLayoutResource(StyleInfo styleInfo) {
        if (this.mSize == 2) {
            this.mLayoutId = R.layout.aod_content_scope_small;
        } else if (this.mIsDual) {
            this.mLayoutId = R.layout.aod_content_scope_dual;
        } else {
            this.mLayoutId = R.layout.aod_content_scope;
        }
        return this.mLayoutId;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void onInflateComplete() {
        ScopeClockView scopeClockView = this.mClockView;
        if (scopeClockView != null) {
            scopeClockView.startAnimation();
        }
    }

    @Override // com.miui.aod.widget.HorizontalClock, com.miui.aod.components.view.IPreAodView
    public void update(StyleInfo styleInfo, int i) {
        super.update(styleInfo, i);
        ScopeClockView scopeClockView = this.mClockView;
        if (scopeClockView != null) {
            scopeClockView.setInnerDelay(styleInfo.getInnerStartDelay());
        }
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
