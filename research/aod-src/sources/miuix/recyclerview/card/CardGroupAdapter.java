package miuix.recyclerview.card;

import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.SparseIntArray;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import miuix.recyclerview.card.base.ViewOutlineHelper;

/* JADX INFO: loaded from: classes2.dex */
public abstract class CardGroupAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    public boolean isNeedItemPressEffect;
    public long mAnimatorDuration;
    public float mCardRadius;
    public int mClickPosition;
    public final RecyclerView.AdapterDataObserver mObserver;
    public RecyclerView mRecyclerView;
    public int mRemovedGroupId;
    public final SparseIntArray mTypeMap;

    /* JADX INFO: renamed from: miuix.recyclerview.card.CardGroupAdapter$1, reason: invalid class name */
    public class AnonymousClass1 extends RecyclerView.AdapterDataObserver {
        public final /* synthetic */ CardGroupAdapter this$0;

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onChanged() {
            super.onChanged();
            this.this$0.updateGroupInfo();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeChanged(int i, int i2) {
            super.onItemRangeChanged(i, i2);
            this.this$0.updateGroupInfo();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeInserted(int i, int i2) {
            super.onItemRangeInserted(i, i2);
            this.this$0.updateGroupInfo();
            this.this$0.notifyCardItemRangeChanged(i, i2);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeMoved(int i, int i2, int i3) {
            super.onItemRangeMoved(i, i2, i3);
            this.this$0.updateGroupInfo();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeRemoved(int i, int i2) {
            super.onItemRangeRemoved(i, i2);
            this.this$0.updateGroupInfo();
            this.this$0.notifyCardItemRangeChanged(i, i2);
        }
    }

    public interface ISameGroupRangeRemove {
    }

    public abstract int getItemViewGroup(int i);

    public int getItemViewGroupType(int i) {
        return this.mTypeMap.get(i);
    }

    public int getRemovedGroupId() {
        return this.mRemovedGroupId;
    }

    public final boolean isSupportLayoutManager() {
        if (this.mRecyclerView.getItemDecorationCount() > 0) {
            RecyclerView.ItemDecoration itemDecorationAt = this.mRecyclerView.getItemDecorationAt(0);
            if (itemDecorationAt instanceof CardItemDecoration) {
                return ((CardItemDecoration) itemDecorationAt).isSupportLayoutManager(this.mRecyclerView.getLayoutManager());
            }
        }
        return false;
    }

    public final void notifyCardItemRangeChanged(int i, int i2) {
        int i3 = i > 0 ? i - 1 : 0;
        notifyItemRangeChanged(i3, ((i + i2) - i3) + 1);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.mRecyclerView = recyclerView;
        this.mCardRadius = recyclerView.getContext().getResources().getDimensionPixelSize(R.dimen.miuix_recyclerview_card_group_radius);
        registerAdapterDataObserver(this.mObserver);
        RecyclerView.ItemAnimator itemAnimator = this.mRecyclerView.getItemAnimator();
        if (itemAnimator != null) {
            this.mAnimatorDuration = itemAnimator.getAddDuration();
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ViewOutlineHelper.setItemCardOutline(viewHolder, getItemViewGroupType(i), isSupportLayoutManager() ? this.mCardRadius : 0.0f, i == this.mClickPosition, this.mAnimatorDuration);
        if (isSupportLayoutManager()) {
            setFirstItemMargin(viewHolder, i);
        }
        if (this.isNeedItemPressEffect) {
            if (viewHolder.itemView.getForeground() != null) {
                viewHolder.itemView.getForeground();
                getItemViewGroupType(i);
            } else {
                TypedArray typedArrayObtainStyledAttributes = viewHolder.itemView.getContext().getTheme().obtainStyledAttributes(new int[]{R.attr.cardGroupItemForegroundEffect});
                viewHolder.itemView.setForeground(typedArrayObtainStyledAttributes.getDrawable(0));
                typedArrayObtainStyledAttributes.recycle();
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        unregisterAdapterDataObserver(this.mObserver);
        this.mRecyclerView = null;
    }

    public final void setFirstItemMargin(RecyclerView.ViewHolder viewHolder, int i) {
        ViewGroup.MarginLayoutParams marginLayoutParams;
        if (i != 0) {
            ViewGroup.LayoutParams layoutParams = viewHolder.itemView.getLayoutParams();
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams marginLayoutParams2 = (ViewGroup.MarginLayoutParams) layoutParams;
                marginLayoutParams2.topMargin = 0;
                marginLayoutParams2.bottomMargin = 0;
                viewHolder.itemView.setLayoutParams(marginLayoutParams2);
                return;
            }
            return;
        }
        if (this.mRecyclerView.getItemDecorationCount() > 0) {
            RecyclerView.ItemDecoration itemDecorationAt = this.mRecyclerView.getItemDecorationAt(0);
            if (itemDecorationAt instanceof CardItemDecoration) {
                Rect rectOffsetsRect = ((CardItemDecoration) itemDecorationAt).offsetsRect(this, i);
                ViewGroup.LayoutParams layoutParams2 = viewHolder.itemView.getLayoutParams();
                if (layoutParams2 instanceof ViewGroup.MarginLayoutParams) {
                    marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams2;
                } else {
                    marginLayoutParams = layoutParams2 != null ? new ViewGroup.MarginLayoutParams(layoutParams2) : new ViewGroup.MarginLayoutParams(new ViewGroup.LayoutParams(-2, -2));
                }
                marginLayoutParams.topMargin = rectOffsetsRect.top;
                marginLayoutParams.bottomMargin = rectOffsetsRect.bottom;
                viewHolder.itemView.setLayoutParams(marginLayoutParams);
            }
        }
    }

    public void updateGroupInfo() {
        int itemCount = getItemCount();
        int i = Integer.MIN_VALUE;
        int i2 = 0;
        while (i2 < itemCount) {
            int itemViewGroup = getItemViewGroup(i2);
            if (itemViewGroup != i) {
                this.mTypeMap.put(i2, 2);
                int i3 = i2 - 1;
                if (i3 >= 0) {
                    int i4 = this.mTypeMap.get(i3);
                    if (i4 == 2) {
                        this.mTypeMap.put(i3, 1);
                    } else if (i4 == 3) {
                        this.mTypeMap.put(i3, 4);
                    }
                }
            } else {
                this.mTypeMap.put(i2, 3);
            }
            if (itemViewGroup == Integer.MIN_VALUE) {
                this.mTypeMap.put(i2, 0);
            }
            i2++;
            i = itemViewGroup;
        }
        int itemCount2 = getItemCount() - 1;
        int i5 = this.mTypeMap.get(itemCount2);
        if (i5 == 2) {
            this.mTypeMap.put(itemCount2, 1);
        } else if (i5 == 3) {
            this.mTypeMap.put(itemCount2, 4);
        }
    }
}
