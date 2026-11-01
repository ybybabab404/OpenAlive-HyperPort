package miuix.recyclerview.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewPropertyAnimator;

/* JADX INFO: loaded from: classes2.dex */
public class MiuiScaleItemAnimator extends MiuiDefaultItemAnimator {
    public float mScaleDist = Float.NaN;

    @Override // miuix.recyclerview.widget.MiuiDefaultItemAnimator, androidx.recyclerview.widget.SimpleItemAnimator
    public boolean animateAdd(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
        float folmeScale = getFolmeScale(viewHolder);
        resetAnimation(viewHolder);
        viewHolder.itemView.setAlpha(0.0f);
        viewHolder.itemView.setScaleX(folmeScale);
        viewHolder.itemView.setScaleY(folmeScale);
        this.mPendingAdditions.add(viewHolder);
        return true;
    }

    @Override // miuix.recyclerview.widget.MiuiDefaultItemAnimator
    public void animateAddImpl(final androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
        final View view = viewHolder.itemView;
        final ViewPropertyAnimator viewPropertyAnimatorAnimate = view.animate();
        this.mAddAnimations.add(viewHolder);
        viewPropertyAnimatorAnimate.setInterpolator(MiuiDefaultItemAnimator.INTERPOLATOR);
        viewPropertyAnimatorAnimate.scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setDuration(getAddDuration()).setListener(new AnimatorListenerAdapter() { // from class: miuix.recyclerview.widget.MiuiScaleItemAnimator.1
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                view.setAlpha(1.0f);
                view.setScaleX(1.0f);
                view.setScaleY(1.0f);
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                viewPropertyAnimatorAnimate.setListener(null);
                MiuiScaleItemAnimator.this.dispatchAddFinished(viewHolder);
                MiuiScaleItemAnimator.this.mAddAnimations.remove(viewHolder);
                MiuiScaleItemAnimator.this.dispatchFinishedWhenDone();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                MiuiScaleItemAnimator.this.dispatchAddStarting(viewHolder);
            }
        }).start();
    }

    @Override // miuix.recyclerview.widget.MiuiDefaultItemAnimator
    public void animateRemoveImpl(final androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
        final View view = viewHolder.itemView;
        final ViewPropertyAnimator viewPropertyAnimatorAnimate = view.animate();
        float folmeScale = getFolmeScale(viewHolder);
        this.mRemoveAnimations.add(viewHolder);
        viewPropertyAnimatorAnimate.setInterpolator(MiuiDefaultItemAnimator.INTERPOLATOR);
        viewPropertyAnimatorAnimate.setDuration(getRemoveDuration()).scaleX(folmeScale).scaleY(folmeScale).alpha(0.0f).setListener(new AnimatorListenerAdapter() { // from class: miuix.recyclerview.widget.MiuiScaleItemAnimator.2
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                viewPropertyAnimatorAnimate.setListener(null);
                view.setAlpha(1.0f);
                view.setScaleX(1.0f);
                view.setScaleY(1.0f);
                MiuiScaleItemAnimator.this.dispatchRemoveFinished(viewHolder);
                MiuiScaleItemAnimator.this.mRemoveAnimations.remove(viewHolder);
                MiuiScaleItemAnimator.this.dispatchFinishedWhenDone();
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                MiuiScaleItemAnimator.this.dispatchRemoveStarting(viewHolder);
            }
        }).start();
    }

    public final float getFolmeScale(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
        if (Float.isNaN(this.mScaleDist)) {
            this.mScaleDist = TypedValue.applyDimension(1, 20.0f, viewHolder.itemView.getResources().getDisplayMetrics());
        }
        float fMax = Math.max(viewHolder.itemView.getWidth(), viewHolder.itemView.getHeight());
        return Math.max((fMax - this.mScaleDist) / fMax, 0.8f);
    }

    @Override // miuix.recyclerview.widget.MiuiDefaultItemAnimator
    public void onRemoveFromAddition(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
        View view = viewHolder.itemView;
        view.setAlpha(1.0f);
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
    }

    @Override // miuix.recyclerview.widget.MiuiDefaultItemAnimator
    public void onRemoveFromPendingAddition(androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder) {
        View view = viewHolder.itemView;
        view.setAlpha(1.0f);
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
    }
}
