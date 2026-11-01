package com.miui.keyguard.editor.homepage.view.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/* JADX INFO: loaded from: classes2.dex */
public class TouchLayout extends FrameLayout {
    public FloorHotspotDelegate floorHotspotDelegate;
    public boolean isSelected;

    public TouchLayout(Context context) {
        super(context);
        this.isSelected = false;
    }

    public TouchLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.isSelected = false;
    }

    public TouchLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isSelected = false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        if (this.isSelected) {
            return super.dispatchTouchEvent(motionEvent);
        }
        Log.i("Keyguard-Editor-TouchLayout", "dispatchTouchEvent: isSelected = false");
        return false;
    }

    public void init() {
        post(new Runnable() { // from class: com.miui.keyguard.editor.homepage.view.viewpager.TouchLayout.1
            @Override // java.lang.Runnable
            public void run() {
                if (TouchLayout.this.getChildCount() > 0) {
                    TouchLayout touchLayout = TouchLayout.this;
                    touchLayout.floorHotspotDelegate = new FloorHotspotDelegate(touchLayout.getChildAt(0));
                }
            }
        });
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.floorHotspotDelegate == null && getChildCount() > 0) {
            this.floorHotspotDelegate = new FloorHotspotDelegate(getChildAt(0));
        }
        FloorHotspotDelegate floorHotspotDelegate = this.floorHotspotDelegate;
        return floorHotspotDelegate != null ? floorHotspotDelegate.onTouchEvent(motionEvent) : super.onTouchEvent(motionEvent);
    }

    @Override // android.view.View
    public boolean performAccessibilityAction(int i, Bundle bundle) {
        if (i == 1 || i == 64) {
            return false;
        }
        return super.performAccessibilityAction(i, bundle);
    }

    @Override // android.view.View
    public void setSelected(boolean z) {
        this.isSelected = z;
    }
}
