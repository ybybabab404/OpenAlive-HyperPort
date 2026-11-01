package com.miui.keyguard.editor.homepage.view.viewpager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/* JADX INFO: loaded from: classes2.dex */
public class SpringLayout extends FrameLayout {
    public SpringTouchManager springTouchManager;

    public SpringLayout(Context context) {
        this(context, null);
    }

    public SpringLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        this.springTouchManager.onTouchEventDispatch(motionEvent);
        return super.dispatchTouchEvent(motionEvent);
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean zIsInterceptTouchEvent = this.springTouchManager.isInterceptTouchEvent();
        requestDisallowInterceptTouchEvent(zIsInterceptTouchEvent);
        return zIsInterceptTouchEvent;
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.springTouchManager.onTouchEvent(motionEvent);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z) {
        if (this.springTouchManager.isInterceptTouchEvent() && z) {
            getParent().requestDisallowInterceptTouchEvent(true);
        } else {
            super.requestDisallowInterceptTouchEvent(z);
        }
    }

    public void setSpringTouchManager(SpringTouchManager springTouchManager) {
        this.springTouchManager = springTouchManager;
    }
}
