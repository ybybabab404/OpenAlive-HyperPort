package miuix.recyclerview.card;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import android.view.animation.AccelerateDecelerateInterpolator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.support.drawable.CardStateDrawable;
import miuix.animation.Folme;
import miuix.animation.styles.DrawableStateEffect;
import miuix.recyclerview.card.base.ViewOutlineHelper;

/* JADX INFO: loaded from: classes2.dex */
public class CardTouchHelperCallback extends ItemTouchHelper.Callback {
    public boolean isInDragState;
    public ValueAnimator mAnimator;
    public BlurMaskFilter mBlurMaskFilter;
    public int mCardGroupRadius;
    public Drawable mDragBackground;
    public boolean mExitAnimation;
    public boolean mIsNeedTriggerAnimation;
    public int mShadowColor;
    public Paint paint;
    public int start;
    public Drawable tempDrawable;
    public boolean tempOutlineClipEnable;
    public ViewOutlineProvider tempOutlineProvider;
    public RecyclerView.ViewHolder tempViewHolder;

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int i;
        super.clearView(recyclerView, viewHolder);
        revertItemViewBackground();
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (this.start < 0 || adapter == null) {
            Log.e("CardTouchHelperCallback", "clearView start < 0 | adapter is null.");
            return;
        }
        int bindingAdapterPosition = viewHolder.getBindingAdapterPosition();
        int i2 = this.start;
        if (i2 < bindingAdapterPosition) {
            if (i2 > 0) {
                i2--;
            }
            int i3 = bindingAdapterPosition - i2;
            i = ((i3 + 1) + i2) + 1 < adapter.getItemCount() ? i3 + 2 : bindingAdapterPosition - this.start;
        } else {
            if (bindingAdapterPosition > 0) {
                bindingAdapterPosition--;
            }
            int i4 = i2 - bindingAdapterPosition;
            i = i4 + 1;
            if (bindingAdapterPosition + i + 1 < adapter.getItemCount()) {
                i = i4 + 2;
            }
            i2 = bindingAdapterPosition;
        }
        if (recyclerView.getScrollState() == 0 && !recyclerView.isComputingLayout()) {
            adapter.notifyItemRangeChanged(i2, i);
        }
        this.start = -1;
        this.isInDragState = false;
        this.mIsNeedTriggerAnimation = false;
        this.mExitAnimation = false;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return ItemTouchHelper.Callback.makeMovementFlags(recyclerView.getLayoutManager() instanceof GridLayoutManager ? 15 : 3, 0);
    }

    public boolean isInDragState() {
        return this.isInDragState;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onChildDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int i, boolean z) {
        super.onChildDrawOver(canvas, recyclerView, viewHolder, f, f2, i, z);
        View view = viewHolder.itemView;
        if (this.mBlurMaskFilter == null) {
            if (this.mCardGroupRadius == -1) {
                this.mCardGroupRadius = LiteUtils.getThemeDimens(view.getContext().getTheme(), view.getResources(), R.attr.cardGroupRadius);
            }
            BlurMaskFilter blurMaskFilter = new BlurMaskFilter(this.mCardGroupRadius, BlurMaskFilter.Blur.OUTER);
            this.mBlurMaskFilter = blurMaskFilter;
            this.paint.setMaskFilter(blurMaskFilter);
        }
        this.paint.setColor(this.mShadowColor);
        float left = viewHolder.itemView.getLeft();
        float y = viewHolder.itemView.getY();
        float right = viewHolder.itemView.getRight();
        float y2 = viewHolder.itemView.getY() + viewHolder.itemView.getHeight();
        int i2 = this.mCardGroupRadius;
        canvas.drawRoundRect(left, y, right, y2, i2, i2, this.paint);
        if (this.mIsNeedTriggerAnimation) {
            this.mIsNeedTriggerAnimation = false;
            startAnimation(view, recyclerView);
        }
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
        return true;
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i) {
        super.onSelectedChanged(viewHolder, i);
        this.mIsNeedTriggerAnimation = true;
        this.mExitAnimation = viewHolder == null;
        StringBuilder sb = new StringBuilder();
        sb.append("onSelectedChanged ");
        sb.append(viewHolder == null ? "null" : Integer.valueOf(viewHolder.getBindingAdapterPosition()));
        sb.append(" actionState=");
        sb.append(i);
        Log.i("CardTouchHelperCallback", sb.toString());
        if (i == 0 || viewHolder == null) {
            return;
        }
        Folme.setDraggingState(viewHolder.itemView, true);
        this.tempViewHolder = viewHolder;
        this.isInDragState = true;
        View view = viewHolder.itemView;
        this.tempDrawable = view.getBackground();
        this.tempOutlineProvider = view.getOutlineProvider();
        this.tempOutlineClipEnable = view.getClipToOutline();
        this.start = viewHolder.getBindingAdapterPosition();
        CardGroupAdapter cardGroupAdapter = viewHolder.getBindingAdapter() instanceof CardGroupAdapter ? (CardGroupAdapter) viewHolder.getBindingAdapter() : null;
        if (cardGroupAdapter == null || cardGroupAdapter.getItemViewGroup(this.start) == Integer.MIN_VALUE) {
            return;
        }
        if (this.mDragBackground == null) {
            this.mDragBackground = LiteUtils.getThemeDrawable(view.getContext(), R.attr.cardGroupItemDragBackground);
        }
        if (this.mCardGroupRadius == -1) {
            this.mCardGroupRadius = LiteUtils.getThemeDimens(view.getContext().getTheme(), view.getResources(), R.attr.cardGroupRadius);
        }
        Drawable foreground = view.getForeground();
        if (foreground instanceof CardStateDrawable) {
            ((CardStateDrawable) foreground).setState(DrawableStateEffect.STATE_ENABLED);
        }
        view.setBackground(this.mDragBackground);
        view.setOutlineProvider(ViewOutlineHelper.obtainCardViewOutlineProvider(1, this.mCardGroupRadius));
        view.setClipToOutline(true);
    }

    @Override // androidx.recyclerview.widget.ItemTouchHelper.Callback
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
    }

    public final void revertItemViewBackground() {
        RecyclerView.ViewHolder viewHolder = this.tempViewHolder;
        if (viewHolder != null) {
            Folme.setDraggingState(viewHolder.itemView, false);
            this.tempViewHolder.itemView.setBackground(this.tempDrawable);
            this.tempViewHolder.itemView.setOutlineProvider(this.tempOutlineProvider);
            this.tempViewHolder.itemView.setClipToOutline(this.tempOutlineClipEnable);
            this.tempViewHolder = null;
            this.tempDrawable = null;
            this.tempOutlineProvider = null;
        }
    }

    public final void startAnimation(final View view, final RecyclerView recyclerView) {
        ValueAnimator valueAnimator = this.mAnimator;
        if (valueAnimator != null && valueAnimator.isRunning()) {
            this.mAnimator.cancel();
        }
        ValueAnimator valueAnimatorOfArgb = ValueAnimator.ofArgb(this.mExitAnimation ? LiteUtils.getThemeColor(view.getContext(), R.attr.cardGroupItemDragShadowBackground) : view.getContext().getResources().getColor(R.color.miuix_color_transparent), this.mExitAnimation ? view.getContext().getResources().getColor(R.color.miuix_color_transparent) : LiteUtils.getThemeColor(view.getContext(), R.attr.cardGroupItemDragShadowBackground));
        this.mAnimator = valueAnimatorOfArgb;
        valueAnimatorOfArgb.setDuration(300L);
        this.mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        this.mAnimator.start();
        this.mAnimator.addListener(new Animator.AnimatorListener() { // from class: miuix.recyclerview.card.CardTouchHelperCallback.1
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                View view2 = view;
                if (view2 == null || recyclerView == null) {
                    return;
                }
                CardTouchHelperCallback.this.mShadowColor = LiteUtils.getThemeColor(view2.getContext(), R.attr.cardGroupItemDragShadowBackground);
                recyclerView.invalidate();
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
            }
        });
        this.mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: miuix.recyclerview.card.CardTouchHelperCallback.2
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator2) {
                if (recyclerView != null) {
                    CardTouchHelperCallback.this.mShadowColor = ((Integer) valueAnimator2.getAnimatedValue()).intValue();
                    recyclerView.invalidate();
                }
            }
        });
    }
}
