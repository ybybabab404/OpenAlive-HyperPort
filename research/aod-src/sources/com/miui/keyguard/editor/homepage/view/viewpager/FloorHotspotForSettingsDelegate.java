package com.miui.keyguard.editor.homepage.view.viewpager;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/* JADX INFO: loaded from: classes2.dex */
public class FloorHotspotForSettingsDelegate {
    public View child;
    public final int mSlop;
    public float translateX;
    public float translateY;

    public FloorHotspotForSettingsDelegate(View view) {
        this.child = view;
        this.mSlop = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        int width = this.child.getWidth();
        int height = this.child.getHeight();
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        if (motionEvent.getAction() == 0) {
            this.translateX = 0.0f;
            this.translateY = 0.0f;
            float f = width;
            if (x > f) {
                this.translateX = (f - x) - this.mSlop;
            }
            float f2 = height;
            if (y > f2) {
                this.translateY = (f2 - y) - this.mSlop;
            }
        }
        motionEvent.setLocation(x + this.translateX, y + this.translateY);
        return true;
    }
}
