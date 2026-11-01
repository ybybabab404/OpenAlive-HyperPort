package com.miui.keyguard.editor.homepage.view.viewpager;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.miui.keyguard.editor.view.viewpager2.ViewPager2;

/* JADX INFO: loaded from: classes2.dex */
public class VerticalSpringTouchManager extends SpringTouchManager {
    public int initialX;
    public int initialY;
    public int scrollFromEdge;

    public VerticalSpringTouchManager(Context context, ViewGroup viewGroup) {
        super(context, viewGroup);
    }

    public final boolean isBottomEnd(int i) {
        return !isChildCanScrollVertically(-1) && i > 0;
    }

    public final boolean isChildCanScrollVertically(int i) {
        ViewPager2.RecyclerViewImpl recyclerViewImpl;
        View view = this.springLayoutContent;
        return (!(view instanceof ViewPager2) || (recyclerViewImpl = ((ViewPager2) view).getRecyclerViewImpl()) == null) ? this.springLayoutContent.canScrollVertically(i) : recyclerViewImpl.canScrollVertically(i);
    }

    public final boolean isTopEnd(int i) {
        return !isChildCanScrollVertically(1) && i < 0;
    }

    @Override // com.miui.keyguard.editor.homepage.view.viewpager.ISpringTouch
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return processTouchEventVertically(motionEvent);
    }

    /* JADX WARN: Code duplicated, block: B:25:0x0051  */
    @Override // com.miui.keyguard.editor.homepage.view.viewpager.ISpringTouch
    public void onTouchEventDispatch(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.initialX = (int) motionEvent.getRawX();
            this.initialY = (int) motionEvent.getRawY();
            this.shouldConsumeTouchEventBySelf = false;
        } else if (action == 1) {
            this.shouldConsumeTouchEventBySelf = false;
        } else if (action == 2) {
            int rawX = (int) (motionEvent.getRawX() - this.initialX);
            int rawY = (int) (motionEvent.getRawY() - this.initialY);
            boolean z = Math.abs(rawY) > Math.abs(rawX) && Math.abs(rawY) > this.touchSlop && Math.abs(rawX) < this.touchSlop;
            if ((isBottomEnd(rawY) || isTopEnd(rawY)) && z) {
                this.shouldConsumeTouchEventBySelf = true;
            }
        } else if (action == 3) {
            this.shouldConsumeTouchEventBySelf = false;
        }
        if (this.shouldConsumeTouchEventBySelf) {
            this.springLayout.requestDisallowInterceptTouchEvent(false);
        }
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0037  */
    /* JADX WARN: Code duplicated, block: B:20:0x0042  */
    public final boolean processTouchEventVertically(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.initialY = (int) motionEvent.getRawY();
        } else if (action == 1) {
            if (this.springLayout.getTranslationY() != 0.0f) {
                this.springLayoutHelper.startSpringBackAnimation(this.scrollFromEdge);
            }
            this.scrollFromEdge = 0;
        } else if (action == 2) {
            int rawY = (int) (motionEvent.getRawY() - this.initialY);
            boolean zIsBottomEnd = isBottomEnd(rawY);
            boolean zIsTopEnd = isTopEnd(rawY);
            if (zIsBottomEnd) {
                this.scrollFromEdge = 2;
            } else if (zIsTopEnd) {
                this.scrollFromEdge = 4;
            }
            if (zIsBottomEnd || zIsTopEnd) {
                this.springLayoutHelper.springScrollY(this.scrollFromEdge, rawY);
            }
        } else if (action == 3) {
            if (this.springLayout.getTranslationY() != 0.0f) {
                this.springLayoutHelper.startSpringBackAnimation(this.scrollFromEdge);
            }
            this.scrollFromEdge = 0;
        }
        return true;
    }
}
