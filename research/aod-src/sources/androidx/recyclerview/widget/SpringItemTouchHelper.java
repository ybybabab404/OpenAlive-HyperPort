package androidx.recyclerview.widget;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewParent;
import miuix.animation.Folme;
import miuix.animation.utils.EaseManager;
import miuix.reflect.Reflects;

/* JADX INFO: loaded from: classes.dex */
public class SpringItemTouchHelper extends ItemTouchHelper {
    public static final TimeInterpolator INTERPOLATOR = new EaseManager.SpringInterpolator().setDamping(0.95f).setResponse(0.3f);
    public boolean mSpringEnabled;
    public final float[] mTmpPosition;
    public final boolean mUseFolmeRecoverAnimation;

    public static boolean hitTest(View view, float f, float f2, float f3, float f4) {
        return f >= f3 && f <= f3 + ((float) view.getWidth()) && f2 >= f4 && f2 <= f4 + ((float) view.getHeight());
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper
    public View findChildView(MotionEvent motionEvent) {
        if (this.mSelected != null) {
            return super.findChildView(motionEvent);
        }
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        for (int size = this.mRecoverAnimations.size() - 1; size >= 0; size--) {
            ItemTouchHelper.RecoverAnimation recoverAnimation = (ItemTouchHelper.RecoverAnimation) this.mRecoverAnimations.get(size);
            View view = recoverAnimation.mViewHolder.itemView;
            if (hitTest(view, x, y, view.getX() + recoverAnimation.mX, view.getY() + recoverAnimation.mY)) {
                return view;
            }
        }
        return this.mRecyclerView.findChildViewUnder(x, y);
    }

    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView instanceof miuix.recyclerview.widget.RecyclerView) {
            this.mSpringEnabled = ((miuix.recyclerview.widget.RecyclerView) recyclerView).getSpringEnabled();
            ((miuix.recyclerview.widget.RecyclerView) this.mRecyclerView).setSpringEnabled(false);
        }
    }

    public void onStopDrag(RecyclerView.ViewHolder viewHolder) {
        RecyclerView recyclerView = this.mRecyclerView;
        if (recyclerView instanceof miuix.recyclerview.widget.RecyclerView) {
            ((miuix.recyclerview.widget.RecyclerView) recyclerView).setSpringEnabled(this.mSpringEnabled);
        }
    }

    public final void releaseVelocityTracker() {
        VelocityTracker velocityTracker = this.mVelocityTracker;
        if (velocityTracker != null) {
            velocityTracker.recycle();
            this.mVelocityTracker = null;
        }
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper
    public void select(RecyclerView.ViewHolder viewHolder, int i) {
        boolean z = false;
        if (i == 2) {
            if (viewHolder != null && !Folme.isInDraggingState(this.mRecyclerView)) {
                Folme.setDraggingState(this.mRecyclerView, true);
                onStartDrag(viewHolder);
            }
        } else if (i == 0 && Folme.isInDraggingState(this.mRecyclerView)) {
            Folme.setDraggingState(this.mRecyclerView, false);
            RecyclerView.ViewHolder viewHolder2 = this.mSelected;
            if (viewHolder2 != null) {
                onStopDrag(viewHolder2);
                z = this.mUseFolmeRecoverAnimation;
            }
        }
        if (z) {
            super_select_overwrite();
        } else {
            super.select(viewHolder, i);
        }
    }

    public final void super_select_overwrite() {
        boolean z;
        Reflects.set(this, Reflects.getDeclaredField(ItemTouchHelper.class, "mDragScrollStartTimeInMs"), Long.MIN_VALUE);
        endRecoverAnimation(null, true);
        Reflects.set(this, Reflects.getDeclaredField(ItemTouchHelper.class, "mActionState"), 0);
        final RecyclerView.ViewHolder viewHolder = this.mSelected;
        if (viewHolder.itemView.getParent() != null) {
            releaseVelocityTracker();
            Reflects.invoke(this, Reflects.getDeclaredMethod(ItemTouchHelper.class, "getSelectedDxDy", float[].class), this.mTmpPosition);
            float[] fArr = this.mTmpPosition;
            ItemTouchHelper.RecoverAnimation recoverAnimation = new ItemTouchHelper.RecoverAnimation(viewHolder, 8, 2, fArr[0], fArr[1], 0.0f, 0.0f) { // from class: androidx.recyclerview.widget.SpringItemTouchHelper.1
                @Override // androidx.recyclerview.widget.ItemTouchHelper.RecoverAnimation, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    super.onAnimationEnd(animator);
                    if (this.mOverridden) {
                        return;
                    }
                    SpringItemTouchHelper springItemTouchHelper = SpringItemTouchHelper.this;
                    springItemTouchHelper.mCallback.clearView(springItemTouchHelper.mRecyclerView, viewHolder);
                    SpringItemTouchHelper springItemTouchHelper2 = SpringItemTouchHelper.this;
                    View view = springItemTouchHelper2.mOverdrawChild;
                    View view2 = viewHolder.itemView;
                    if (view == view2) {
                        springItemTouchHelper2.removeChildDrawingOrderCallbackIfNecessary(view2);
                    }
                }
            };
            recoverAnimation.mValueAnimator.setInterpolator(INTERPOLATOR);
            this.mRecoverAnimations.add(recoverAnimation);
            recoverAnimation.start();
            z = true;
        } else {
            removeChildDrawingOrderCallbackIfNecessary(viewHolder.itemView);
            this.mCallback.clearView(this.mRecyclerView, viewHolder);
            z = false;
        }
        this.mSelected = null;
        ViewParent parent = this.mRecyclerView.getParent();
        if (parent != null) {
            parent.requestDisallowInterceptTouchEvent(this.mSelected != null);
        }
        if (!z) {
            this.mRecyclerView.getLayoutManager().requestSimpleAnimationsInNextLayout();
        }
        this.mCallback.onSelectedChanged(this.mSelected, 0);
        this.mRecyclerView.invalidate();
    }
}
