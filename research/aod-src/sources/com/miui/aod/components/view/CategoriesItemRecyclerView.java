package com.miui.aod.components.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import miuix.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class CategoriesItemRecyclerView extends RecyclerView {
    private int mScrollPointerId;
    private int mStartX;
    private int mStartY;
    private int mTouchSlop;

    public CategoriesItemRecyclerView(@NonNull Context context) {
        super(context);
        this.mStartX = 0;
        this.mStartY = 0;
        this.mTouchSlop = 0;
        this.mScrollPointerId = -1;
    }

    public CategoriesItemRecyclerView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mStartX = 0;
        this.mStartY = 0;
        this.mTouchSlop = 0;
        this.mScrollPointerId = -1;
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
    }

    private void onPointerUp(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.mScrollPointerId) {
            int i = actionIndex == 0 ? 1 : 0;
            this.mScrollPointerId = motionEvent.getPointerId(i);
            this.mStartX = (int) (motionEvent.getX(i) + 0.5f);
            this.mStartY = (int) (motionEvent.getY(i) + 0.5f);
        }
    }

    @Override // androidx.recyclerview.widget.SpringRecyclerView, androidx.recyclerview.widget.RemixRecyclerView, androidx.recyclerview.widget.RecyclerView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        int actionIndex = motionEvent.getActionIndex();
        if (action == 0) {
            this.mScrollPointerId = motionEvent.getPointerId(0);
            this.mStartX = (int) (motionEvent.getX() + 0.5f);
            this.mStartY = (int) (motionEvent.getY() + 0.5f);
        } else if (action == 2) {
            int iFindPointerIndex = motionEvent.findPointerIndex(this.mScrollPointerId);
            int x = (int) (motionEvent.getX(iFindPointerIndex) + 0.5f);
            int y = (int) (motionEvent.getY(iFindPointerIndex) + 0.5f);
            float fAbs = Math.abs(x - this.mStartX);
            float fAbs2 = Math.abs(y - this.mStartY);
            if (fAbs > this.mTouchSlop && fAbs > fAbs2) {
                return false;
            }
        } else if (action == 5) {
            this.mScrollPointerId = motionEvent.getPointerId(actionIndex);
            this.mStartX = (int) (motionEvent.getX(actionIndex) + 0.5f);
            this.mStartY = (int) (motionEvent.getY(actionIndex) + 0.5f);
        } else if (action == 6) {
            onPointerUp(motionEvent);
        }
        return super.onInterceptTouchEvent(motionEvent);
    }
}
