package com.miui.keyguard.editor.homepage.view.viewpager;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.miui.keyguard.editor.view.viewpager2.ViewPager2;

/* JADX INFO: loaded from: classes2.dex */
public class HorizontalSpringTouchManager extends SpringTouchManager {
    public FloorHotspotDelegate floorHotspotDelegate;
    public int initialX;
    public int initialY;
    public int scrollFromEdge;

    public HorizontalSpringTouchManager(Context context, ViewGroup viewGroup) {
        super(context, viewGroup);
        View view = this.springLayoutContent;
        if (view != null) {
            this.floorHotspotDelegate = new FloorHotspotDelegate(view);
        }
    }

    private boolean isChildCanScrollHorizontally(int i) {
        ViewPager2.RecyclerViewImpl recyclerViewImpl;
        View view = this.springLayoutContent;
        return (!(view instanceof ViewPager2) || (recyclerViewImpl = ((ViewPager2) view).getRecyclerViewImpl()) == null) ? this.springLayoutContent.canScrollHorizontally(i) : recyclerViewImpl.canScrollHorizontally(i);
    }

    private boolean isLeftEnd(int i) {
        return !isChildCanScrollHorizontally(-1) && i > 0;
    }

    private boolean isRightEnd(int i) {
        return !isChildCanScrollHorizontally(1) && i < 0;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0037  */
    /* JADX WARN: Code duplicated, block: B:21:0x0042  */
    private boolean processTouchEventHorizontal(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 1) {
            if (this.springLayout.getTranslationX() != 0.0f) {
                this.springLayoutHelper.startSpringBackAnimation(this.scrollFromEdge);
            }
            this.scrollFromEdge = 0;
        } else {
            if (action == 2) {
                int x = (int) (motionEvent.getX() - this.initialX);
                boolean zIsLeftEnd = isLeftEnd(x);
                boolean zIsRightEnd = isRightEnd(x);
                if (zIsLeftEnd) {
                    this.scrollFromEdge = 1;
                } else if (zIsRightEnd) {
                    this.scrollFromEdge = 3;
                }
                if (!zIsLeftEnd && !zIsRightEnd) {
                    return false;
                }
                this.springLayoutHelper.springScrollX(this.scrollFromEdge, x);
                return true;
            }
            if (action == 3) {
                if (this.springLayout.getTranslationX() != 0.0f) {
                    this.springLayoutHelper.startSpringBackAnimation(this.scrollFromEdge);
                }
                this.scrollFromEdge = 0;
            }
        }
        return true;
    }

    @Override // com.miui.keyguard.editor.homepage.view.viewpager.ISpringTouch
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (this.floorHotspotDelegate == null) {
            return processTouchEventHorizontal(motionEvent);
        }
        boolean zProcessTouchEventHorizontal = processTouchEventHorizontal(motionEvent);
        return zProcessTouchEventHorizontal || ((motionEvent.getAction() != 2 || !zProcessTouchEventHorizontal) ? this.floorHotspotDelegate.onTouchEvent(motionEvent) : false);
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0049  */
    @Override // com.miui.keyguard.editor.homepage.view.viewpager.ISpringTouch
    public void onTouchEventDispatch(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.initialX = (int) motionEvent.getX();
            this.initialY = (int) motionEvent.getY();
            this.shouldConsumeTouchEventBySelf = false;
        } else if (action == 1) {
            this.shouldConsumeTouchEventBySelf = false;
        } else if (action == 2) {
            int x = (int) (motionEvent.getX() - this.initialX);
            boolean z = Math.abs(x) > Math.abs((int) (motionEvent.getY() - ((float) this.initialY))) && Math.abs(x) > this.touchSlop;
            if ((isLeftEnd(x) || isRightEnd(x)) && z) {
                this.shouldConsumeTouchEventBySelf = true;
            }
        } else if (action == 3) {
            this.shouldConsumeTouchEventBySelf = false;
        }
        if (this.shouldConsumeTouchEventBySelf) {
            this.springLayout.requestDisallowInterceptTouchEvent(false);
        }
    }
}
