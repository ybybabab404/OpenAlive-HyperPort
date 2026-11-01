package com.miui.aod.widget;

import android.view.View;
import com.miui.aod.R;
import com.miui.aod.category.SmartCoverStyleInfo;
import com.miui.aod.common.StyleInfo;

/* JADX INFO: loaded from: classes.dex */
public class SmartCoverClock extends ClockViewProxy {
    public final int mSize;

    public SmartCoverClock(int i) {
        this.mSize = i;
    }

    @Override // com.miui.aod.widget.ClockViewProxy, com.miui.aod.widget.IAodClock
    public void bindView(View view) {
        super.bindView(view.findViewById(R.id.smart_cover_clock_container));
    }

    @Override // com.miui.aod.widget.ClockViewProxy, com.miui.aod.widget.IAodClock
    public int getLayoutResource(StyleInfo styleInfo) {
        boolean z = ((SmartCoverStyleInfo) styleInfo).getStyle() == 0;
        int i = this.mSize;
        boolean z2 = i == 2 || i == 4;
        if (z) {
            return z2 ? R.layout.smart_cover_aodstyle_a_small : R.layout.smart_cover_aodstyle_a;
        }
        return z2 ? R.layout.smart_cover_aodstyle_b_small : R.layout.smart_cover_aodstyle_b;
    }
}
