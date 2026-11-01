package com.miui.clock.tiny.bubble;

import com.miui.clock.tiny.R;
import com.miui.clock.tiny.model.TinyClockBean;

/* JADX INFO: loaded from: classes.dex */
public class BubbleAodClockInfo extends BubbleClockBaseInfo {
    public BubbleAodClockInfo(TinyClockBean tinyClockBean, int i) {
        super(tinyClockBean, i);
    }

    @Override // com.miui.clock.tiny.model.TinyClockInfo
    public int getLayoutId(int i) {
        return R.layout.layout_bubble_clock_view_aod;
    }
}
