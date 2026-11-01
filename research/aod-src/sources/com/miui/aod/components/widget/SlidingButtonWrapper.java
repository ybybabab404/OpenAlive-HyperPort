package com.miui.aod.components.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import miuix.slidingwidget.widget.SlidingButton;

/* JADX INFO: loaded from: classes.dex */
public class SlidingButtonWrapper extends SlidingButton {
    public SlidingButtonWrapper(Context context) {
        super(context);
    }

    public SlidingButtonWrapper(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SlidingButtonWrapper(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.view.View
    public boolean dispatchHoverEvent(MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 9) {
            sendAccessibilityEvent(8);
        }
        return super.dispatchHoverEvent(motionEvent);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        setFocusable(true);
        setFocusableInTouchMode(true);
    }
}
