package com.miui.aod.widget;

import android.view.View;
import android.widget.LinearLayout;
import com.miui.aod.R;
import com.miui.aod.common.StyleInfo;

/* JADX INFO: loaded from: classes.dex */
public class PaintVerticalClock extends VerticalClock {
    public PaintVerticalClock(int i, boolean z) {
        super(i, z);
    }

    @Override // com.miui.aod.widget.VerticalClock, com.miui.aod.widget.HorizontalClock, com.miui.aod.widget.IAodClock
    public void bindView(View view) {
        super.bindView(view);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mClockView.getLayoutParams();
        if (((VerticalClock) this).mSize == 2) {
            layoutParams.topMargin = this.mContext.getResources().getDimensionPixelOffset(R.dimen.paint_vertical_clock_top_margin_s);
        } else {
            layoutParams.topMargin = this.mContext.getResources().getDimensionPixelOffset(R.dimen.paint_vertical_clock_top_margin);
        }
        this.mClockView.setLayoutParams(layoutParams);
    }

    @Override // com.miui.aod.widget.HorizontalClock, com.miui.aod.components.view.IPreAodView
    public void update(StyleInfo styleInfo, int i) {
        this.mStyleInfo = styleInfo;
        if (styleInfo == null || !styleInfo.isForAodNotification()) {
            return;
        }
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mClockView.getLayoutParams();
        layoutParams.topMargin = this.mContext.getResources().getDimensionPixelOffset(R.dimen.paint_vertical_clock_top_margin_n);
        this.mClockView.setLayoutParams(layoutParams);
    }
}
