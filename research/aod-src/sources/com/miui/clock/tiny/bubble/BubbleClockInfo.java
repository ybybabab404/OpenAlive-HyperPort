package com.miui.clock.tiny.bubble;

import com.miui.clock.tiny.R;
import com.miui.clock.tiny.model.TinyClockBean;

/* JADX INFO: loaded from: classes.dex */
public class BubbleClockInfo extends BubbleClockBaseInfo {
    public BubbleClockInfo(TinyClockBean tinyClockBean, int i) {
        super(tinyClockBean, i);
    }

    @Override // com.miui.clock.tiny.model.TinyClockInfo
    public int getLayoutId(int i) {
        return i != 2 ? R.layout.layout_bubble_clock_view_left : R.layout.layout_bubble_clock_view_right;
    }
}
