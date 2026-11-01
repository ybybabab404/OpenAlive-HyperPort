package com.miui.keyguard.editor.homepage.view.viewpager;

import android.content.Context;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes2.dex */
public abstract class SpringTouchManager implements ISpringTouch {
    public Context context;
    public boolean shouldConsumeTouchEventBySelf;
    public ViewGroup springLayout;
    public View springLayoutContent;
    public SpringLayoutHelper springLayoutHelper;
    public int touchSlop;

    public SpringTouchManager(Context context, ViewGroup viewGroup) {
        this.context = context;
        this.springLayout = viewGroup;
        this.springLayoutHelper = new SpringLayoutHelper(context, viewGroup);
        init();
    }

    public final void init() {
        this.touchSlop = ViewConfiguration.get(this.context).getScaledTouchSlop();
        if (this.springLayout.getChildCount() > 0) {
            this.springLayoutContent = this.springLayout.getChildAt(0);
        }
    }

    public boolean isInterceptTouchEvent() {
        return this.shouldConsumeTouchEventBySelf;
    }

    public void setSpringScrollListener(SpringScrollListener springScrollListener) {
        this.springLayoutHelper.setSpringScrollListener(springScrollListener);
    }
}
