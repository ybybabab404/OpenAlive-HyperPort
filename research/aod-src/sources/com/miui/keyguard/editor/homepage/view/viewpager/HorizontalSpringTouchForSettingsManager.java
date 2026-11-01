package com.miui.keyguard.editor.homepage.view.viewpager;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.miui.keyguard.editor.view.viewpager2.ViewPager2;

/* JADX INFO: loaded from: classes2.dex */
public class HorizontalSpringTouchForSettingsManager extends SpringTouchManager {
    public boolean enableTouch;
    public int firstBoundaryDiffX;
    public FloorHotspotForSettingsDelegate floorHotspotDelegate;
    public int initialX;
    public int initialY;
    public boolean isFirstBoundaryMove;
    public int scrollFromEdge;
    public boolean startedFromEdge;

    public HorizontalSpringTouchForSettingsManager(Context context, ViewGroup viewGroup) {
        super(context, viewGroup);
        this.enableTouch = true;
        this.firstBoundaryDiffX = 0;
        this.isFirstBoundaryMove = true;
        this.startedFromEdge = false;
        View view = this.springLayoutContent;
        if (view != null) {
            this.floorHotspotDelegate = new FloorHotspotForSettingsDelegate(view);
        }
    }

    public boolean getEnableTouch() {
        return this.enableTouch;
    }

    public final int getItemWidth() {
        View view = this.springLayoutContent;
        if (view instanceof ViewPager2) {
            ViewPager2 viewPager2 = (ViewPager2) view;
            if (viewPager2.getChildCount() > 0) {
                return viewPager2.getChildAt(0).getWidth();
            }
        }
        return (int) (this.springLayoutContent.getWidth() * 0.8f);
    }

    public final boolean isChildCanScrollHorizontally(int i) {
        ViewPager2.RecyclerViewImpl recyclerViewImpl;
        View view = this.springLayoutContent;
        return (!(view instanceof ViewPager2) || (recyclerViewImpl = ((ViewPager2) view).getRecyclerViewImpl()) == null) ? this.springLayoutContent.canScrollHorizontally(i) : recyclerViewImpl.canScrollHorizontally(i);
    }

    @Override // com.miui.keyguard.editor.homepage.view.viewpager.SpringTouchManager
    public boolean isInterceptTouchEvent() {
        if (this.enableTouch) {
            return super.isInterceptTouchEvent();
        }
        return true;
    }

    public final boolean isLeftEnd(int i) {
        return !isChildCanScrollHorizontally(-1) && i > 0;
    }

    public final boolean isRightEnd(int i) {
        return !isChildCanScrollHorizontally(1) && i < 0;
    }

    @Override // com.miui.keyguard.editor.homepage.view.viewpager.ISpringTouch
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.enableTouch) {
            return false;
        }
        if (this.floorHotspotDelegate == null) {
            return processTouchEventHorizontal(motionEvent);
        }
        boolean zProcessTouchEventHorizontal = processTouchEventHorizontal(motionEvent);
        return zProcessTouchEventHorizontal || ((motionEvent.getAction() != 2 || !zProcessTouchEventHorizontal) ? this.floorHotspotDelegate.onTouchEvent(motionEvent) : false);
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0051  */
    @Override // com.miui.keyguard.editor.homepage.view.viewpager.ISpringTouch
    public void onTouchEventDispatch(MotionEvent motionEvent) {
        ViewGroup viewGroup;
        int action = motionEvent.getAction();
        int i = 3;
        boolean z = true;
        if (action == 0) {
            this.initialX = (int) motionEvent.getX();
            this.initialY = (int) motionEvent.getY();
            boolean zIsChildCanScrollHorizontally = isChildCanScrollHorizontally(-1);
            boolean zIsChildCanScrollHorizontally2 = isChildCanScrollHorizontally(1);
            if (!zIsChildCanScrollHorizontally) {
                i = 1;
            } else if (zIsChildCanScrollHorizontally2) {
                i = 0;
            }
            this.scrollFromEdge = i;
            if (zIsChildCanScrollHorizontally && zIsChildCanScrollHorizontally2) {
                z = false;
            }
            this.startedFromEdge = z;
            this.shouldConsumeTouchEventBySelf = false;
        } else if (action == 1) {
            this.shouldConsumeTouchEventBySelf = false;
        } else if (action == 2) {
            int x = (int) ((motionEvent.getX() - this.initialX) + this.springLayout.getTranslationX());
            boolean z2 = Math.abs(x) > Math.abs((int) (motionEvent.getY() - ((float) this.initialY))) && Math.abs(x) > this.touchSlop;
            if ((isLeftEnd(x) || isRightEnd(x)) && z2) {
                this.shouldConsumeTouchEventBySelf = true;
            }
        } else if (action == 3) {
            this.shouldConsumeTouchEventBySelf = false;
        }
        if (!this.shouldConsumeTouchEventBySelf || (viewGroup = this.springLayout) == null) {
            return;
        }
        viewGroup.requestDisallowInterceptTouchEvent(false);
    }

    /* JADX WARN: Code duplicated, block: B:31:0x006c  */
    /* JADX WARN: Code duplicated, block: B:33:0x007b  */
    public final boolean processTouchEventHorizontal(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 1) {
            this.isFirstBoundaryMove = true;
            this.startedFromEdge = false;
            if (this.springLayout.getTranslationX() != 0.0f) {
                this.springLayoutHelper.startSpringBackAnimation(this.scrollFromEdge);
            }
            this.scrollFromEdge = 0;
        } else {
            if (action == 2) {
                int x = (int) ((motionEvent.getX() - this.initialX) + this.springLayout.getTranslationX());
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
                int itemWidth = getItemWidth();
                if (!this.startedFromEdge) {
                    if (x > itemWidth && zIsLeftEnd) {
                        x -= itemWidth;
                        this.initialX += itemWidth;
                        this.startedFromEdge = true;
                    } else if (x < (-itemWidth) && zIsRightEnd) {
                        x += itemWidth;
                        this.initialX -= itemWidth;
                        this.startedFromEdge = true;
                    }
                }
                if (this.isFirstBoundaryMove) {
                    this.firstBoundaryDiffX = x;
                    this.isFirstBoundaryMove = false;
                }
                this.springLayoutHelper.springScrollX(this.scrollFromEdge, x - this.firstBoundaryDiffX);
                return true;
            }
            if (action == 3) {
                this.isFirstBoundaryMove = true;
                this.startedFromEdge = false;
                if (this.springLayout.getTranslationX() != 0.0f) {
                    this.springLayoutHelper.startSpringBackAnimation(this.scrollFromEdge);
                }
                this.scrollFromEdge = 0;
            }
        }
        return true;
    }

    public void setEnableTouch(boolean z) {
        this.enableTouch = z;
    }

    public boolean shouldSpringBack() {
        return this.springLayout.getTranslationX() != 0.0f;
    }
}
