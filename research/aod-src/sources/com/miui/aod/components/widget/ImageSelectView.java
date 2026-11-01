package com.miui.aod.components.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.miui.aod.R;
import com.miui.aod.components.DrawableData;
import com.miui.aod.util.HapticUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ImageSelectView extends RecyclerView implements View.OnClickListener {
    public static final int COUNT_FACTOR = 3;
    private final RecyclerView.Adapter<ColorHolder> mAdapter;
    private int mCurrentSelection;
    private final List<DrawableData> mDrawables;
    private OnItemClickListener mItemClickListener;
    LinearLayoutManager mLayoutManager;
    RecyclerView.SmoothScroller mSmoothScroller;
    private SpacesItemDecoration mSpacesItemDecoration;

    public static class ColorHolder extends RecyclerView.ViewHolder {
        private final ImageView mIcon;

        public ColorHolder(View view) {
            super(view);
            ImageView imageView = (ImageView) view.findViewById(R.id.rect_icon);
            this.mIcon = imageView;
            if (imageView instanceof RoundCornerImageView) {
                ((RoundCornerImageView) imageView).setRectRoundRadius(imageView.getResources().getDimensionPixelSize(R.dimen.select_drawable_corner_radius));
            }
        }

        public void bindDrawable(DrawableData drawableData, boolean z) {
            this.mIcon.setImageResource(drawableData.mResId);
            int i = z ? R.drawable.aod_selected_rect : 0;
            this.itemView.setForeground(i > 0 ? ResourcesCompat.getDrawable(this.itemView.getResources(), i, null) : null);
        }
    }

    public class ColorSelectAdapter extends RecyclerView.Adapter<ColorHolder> {
        private ColorSelectAdapter() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return ImageSelectView.this.mDrawables.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(@NonNull ColorHolder colorHolder, int i) {
            if (i < 0 || i >= ImageSelectView.this.mDrawables.size()) {
                return;
            }
            colorHolder.bindDrawable((DrawableData) ImageSelectView.this.mDrawables.get(i), i == ImageSelectView.this.mCurrentSelection);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        @NonNull
        public ColorHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rect_drawable_item, viewGroup, false);
            if (getItemCount() > 3) {
                ViewGroup.LayoutParams layoutParams = viewInflate.getLayoutParams();
                int dimensionPixelSize = ImageSelectView.this.getResources().getDimensionPixelSize(R.dimen.rect_drawable_item_small_size);
                layoutParams.height = dimensionPixelSize;
                layoutParams.width = dimensionPixelSize;
                viewInflate.setLayoutParams(layoutParams);
            }
            viewInflate.setOnClickListener(ImageSelectView.this);
            return new ColorHolder(viewInflate);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int i, DrawableData drawableData);
    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private final int space;

        public SpacesItemDecoration(int i) {
            this.space = i;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(Rect rect, @NonNull View view, RecyclerView recyclerView, @NonNull RecyclerView.State state) {
            rect.left = this.space;
            int dimensionPixelSize = ImageSelectView.this.getResources().getDimensionPixelSize(R.dimen.rect_image_select_view_margin_start);
            if (recyclerView.getChildLayoutPosition(view) == 0) {
                rect.left = dimensionPixelSize;
            } else if (recyclerView.getChildLayoutPosition(view) == ImageSelectView.this.mAdapter.getItemCount() - 1) {
                rect.right = dimensionPixelSize;
            }
        }
    }

    public ImageSelectView(Context context) {
        this(context, null);
    }

    public ImageSelectView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ImageSelectView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDrawables = new ArrayList();
        ColorSelectAdapter colorSelectAdapter = new ColorSelectAdapter();
        this.mAdapter = colorSelectAdapter;
        this.mSmoothScroller = new LinearSmoothScroller(getContext()) { // from class: com.miui.aod.components.widget.ImageSelectView.1
            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            public int calculateDtToFit(int i2, int i3, int i4, int i5, int i6) {
                return super.calculateDtToFit(i2, i3, i4, i5, i6) + ImageSelectView.this.getResources().getDimensionPixelSize(R.dimen.rect_image_select_view_margin_start);
            }

            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            public float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return 0.16f;
            }

            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            public int getHorizontalSnapPreference() {
                return -1;
            }
        };
        RecyclerView.ItemAnimator itemAnimator = getItemAnimator();
        if (itemAnimator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) itemAnimator).setSupportsChangeAnimations(false);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        this.mLayoutManager = linearLayoutManager;
        linearLayoutManager.setOrientation(0);
        setLayoutManager(this.mLayoutManager);
        setAdapter(colorSelectAdapter);
    }

    public int getSelection() {
        return this.mCurrentSelection;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        HapticUtils.setClickHapticFeedback(view);
        int childAdapterPosition = getChildAdapterPosition(view);
        setItemSelected(childAdapterPosition);
        OnItemClickListener onItemClickListener = this.mItemClickListener;
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(childAdapterPosition, this.mDrawables.get(childAdapterPosition));
        }
    }

    public void setDivider(int i) {
        removeItemDecoration(this.mSpacesItemDecoration);
        this.mSpacesItemDecoration = new SpacesItemDecoration(i);
        addItemDecoration(new SpacesItemDecoration(i));
    }

    public void setDrawableData(Collection<DrawableData> collection) {
        this.mDrawables.clear();
        this.mDrawables.addAll(collection);
        if (this.mDrawables.size() > 3) {
            setDivider(getResources().getDimensionPixelSize(R.dimen.rect_item_margin_small));
        } else {
            setDivider(getResources().getDimensionPixelSize(R.dimen.rect_item_margin));
        }
        this.mAdapter.notifyDataSetChanged();
    }

    public void setItemSelected(int i) {
        int i2;
        if (i < 0 || i >= this.mAdapter.getItemCount() || i == (i2 = this.mCurrentSelection)) {
            return;
        }
        this.mCurrentSelection = i;
        this.mAdapter.notifyItemChanged(i2);
        this.mAdapter.notifyItemChanged(i);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void smoothScrollToPosition(int i) {
        if (i < 0 || i >= this.mAdapter.getItemCount()) {
            return;
        }
        this.mSmoothScroller.setTargetPosition(i);
        this.mLayoutManager.startSmoothScroll(this.mSmoothScroller);
    }
}
