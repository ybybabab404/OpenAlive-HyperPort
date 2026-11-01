package com.miui.aod.util;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import com.miui.aod.R;

/* JADX INFO: loaded from: classes.dex */
public class DoubleTapHelper {
    public boolean mActivated;
    public final ActivationListener mActivationListener;
    public float mActivationX;
    public float mActivationY;
    public final DoubleTapListener mDoubleTapListener;
    public final DoubleTapLogListener mDoubleTapLogListener;
    public final float mDoubleTapSlop;
    public final long mDoubleTapTimeOutMs;
    public float mDownX;
    public float mDownY;
    public final SlideBackListener mSlideBackListener;
    public final Runnable mTapTimeoutRunnable = new Runnable() { // from class: com.miui.aod.util.DoubleTapHelper$$ExternalSyntheticLambda0
        @Override // java.lang.Runnable
        public final void run() {
            this.f$0.makeInactive();
        }
    };
    public final float mTouchSlop;
    public boolean mTrackTouch;
    public final View mView;

    public interface ActivationListener {
        void onActiveChanged(boolean z);
    }

    public interface DoubleTapListener {
        boolean onDoubleTap();
    }

    public interface DoubleTapLogListener {
        void onDoubleTapLog(boolean z, float f, float f2);
    }

    public interface SlideBackListener {
        boolean onSlideBack();
    }

    public DoubleTapHelper(View view, long j, ActivationListener activationListener, DoubleTapListener doubleTapListener, SlideBackListener slideBackListener, DoubleTapLogListener doubleTapLogListener) {
        this.mTouchSlop = ViewConfiguration.get(view.getContext()).getScaledTouchSlop();
        this.mDoubleTapSlop = view.getResources().getDimension(R.dimen.double_tap_slop);
        this.mView = view;
        this.mDoubleTapTimeOutMs = j;
        this.mActivationListener = activationListener;
        this.mDoubleTapListener = doubleTapListener;
        this.mSlideBackListener = slideBackListener;
        this.mDoubleTapLogListener = doubleTapLogListener;
    }

    public final boolean isWithinDoubleTapSlop(MotionEvent motionEvent) {
        if (this.mActivated) {
            return Math.abs(motionEvent.getX() - this.mActivationX) < this.mDoubleTapSlop && Math.abs(motionEvent.getY() - this.mActivationY) < this.mDoubleTapSlop;
        }
        return true;
    }

    public final boolean isWithinTouchSlop(MotionEvent motionEvent) {
        return Math.abs(motionEvent.getX() - this.mDownX) < this.mTouchSlop && Math.abs(motionEvent.getY() - this.mDownY) < this.mTouchSlop;
    }

    public final void makeActive() {
        if (this.mActivated) {
            return;
        }
        this.mActivated = true;
        this.mActivationListener.onActiveChanged(true);
    }

    public final void makeInactive() {
        if (this.mActivated) {
            this.mActivated = false;
            this.mActivationListener.onActiveChanged(false);
        }
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return onTouchEvent(motionEvent, Integer.MAX_VALUE);
    }

    public boolean onTouchEvent(MotionEvent motionEvent, int i) {
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            this.mDownX = motionEvent.getX();
            float y = motionEvent.getY();
            this.mDownY = y;
            this.mTrackTouch = true;
            if (y > i) {
                this.mTrackTouch = false;
            }
        } else if (actionMasked != 1) {
            if (actionMasked != 2) {
                if (actionMasked == 3) {
                    makeInactive();
                    this.mTrackTouch = false;
                }
            } else if (!isWithinTouchSlop(motionEvent)) {
                makeInactive();
                this.mTrackTouch = false;
            }
        } else if (isWithinTouchSlop(motionEvent)) {
            SlideBackListener slideBackListener = this.mSlideBackListener;
            if (slideBackListener != null && slideBackListener.onSlideBack()) {
                return true;
            }
            if (this.mActivated) {
                boolean zIsWithinDoubleTapSlop = isWithinDoubleTapSlop(motionEvent);
                DoubleTapLogListener doubleTapLogListener = this.mDoubleTapLogListener;
                if (doubleTapLogListener != null) {
                    doubleTapLogListener.onDoubleTapLog(zIsWithinDoubleTapSlop, motionEvent.getX() - this.mActivationX, motionEvent.getY() - this.mActivationY);
                }
                if (!zIsWithinDoubleTapSlop) {
                    makeInactive();
                    this.mTrackTouch = false;
                } else if (!this.mDoubleTapListener.onDoubleTap()) {
                    return false;
                }
            } else {
                makeActive();
                this.mView.postDelayed(this.mTapTimeoutRunnable, this.mDoubleTapTimeOutMs);
                this.mActivationX = motionEvent.getX();
                this.mActivationY = motionEvent.getY();
            }
        } else {
            makeInactive();
            this.mTrackTouch = false;
        }
        return this.mTrackTouch;
    }
}
