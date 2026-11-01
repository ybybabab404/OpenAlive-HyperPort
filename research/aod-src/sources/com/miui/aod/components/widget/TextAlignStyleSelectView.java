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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.miui.aod.AODApplication;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.components.DrawableGravityData;
import com.miui.aod.util.HapticUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class TextAlignStyleSelectView extends RecyclerView implements View.OnClickListener {
    private final androidx.recyclerview.widget.RecyclerView.Adapter<TextStyleHolder> mAdapter;
    private int mCurrentSelection;
    private final List<DrawableGravityData> mDrawables;
    private OnItemClickListener mItemClickListener;
    LinearLayoutManager mLayoutManager;
    androidx.recyclerview.widget.RecyclerView.SmoothScroller mSmoothScroller;
    private SpacesItemDecoration mSpacesItemDecoration;

    public interface OnItemClickListener {
        void onItemClick(int i, DrawableGravityData drawableGravityData);
    }

    public class SpacesItemDecoration extends androidx.recyclerview.widget.RecyclerView.ItemDecoration {
        private final int space;

        public SpacesItemDecoration(int i) {
            this.space = i;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(@NonNull Rect rect, @NonNull View view, @NonNull androidx.recyclerview.widget.RecyclerView recyclerView, @NonNull androidx.recyclerview.widget.RecyclerView.State state) {
            rect.left = this.space;
            int dimensionPixelSize = TextAlignStyleSelectView.this.getResources().getDimensionPixelSize(R.dimen.style_select_panel_padding_start);
            if (recyclerView.getChildLayoutPosition(view) == 0) {
                if (Utils.isLayoutRtl()) {
                    rect.right = dimensionPixelSize;
                    return;
                } else {
                    rect.left = dimensionPixelSize;
                    return;
                }
            }
            if (recyclerView.getChildLayoutPosition(view) == TextAlignStyleSelectView.this.mAdapter.getItemCount() - 1) {
                if (Utils.isLayoutRtl()) {
                    rect.left = dimensionPixelSize;
                } else {
                    rect.right = dimensionPixelSize;
                }
            }
        }
    }

    public static class TextStyleHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final ImageView mIcon;

        public TextStyleHolder(View view) {
            super(view);
            this.mIcon = (ImageView) view;
            Folme.useAt(view).touch().handleTouchOf(view, new AnimConfig[0]);
        }

        public void bindDrawable(DrawableGravityData drawableGravityData, boolean z) {
            this.mIcon.setSelected(z);
            this.mIcon.setImageResource(drawableGravityData.mResId);
            this.mIcon.setContentDescription(AODApplication.sInstance.getResources().getString(drawableGravityData.mDescriptionId));
        }
    }

    public class TextStyleSelectAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<TextStyleHolder> {
        private TextStyleSelectAdapter() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return TextAlignStyleSelectView.this.mDrawables.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(@NonNull TextStyleHolder textStyleHolder, int i) {
            if (i < 0 || i >= TextAlignStyleSelectView.this.mDrawables.size()) {
                return;
            }
            textStyleHolder.bindDrawable((DrawableGravityData) TextAlignStyleSelectView.this.mDrawables.get(i), i == TextAlignStyleSelectView.this.mCurrentSelection);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        @NonNull
        public TextStyleHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_style_select_item, viewGroup, false);
            viewInflate.setOnClickListener(TextAlignStyleSelectView.this);
            return new TextStyleHolder(viewInflate);
        }
    }

    public TextAlignStyleSelectView(Context context) {
        this(context, null);
    }

    public TextAlignStyleSelectView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public TextAlignStyleSelectView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDrawables = new ArrayList();
        TextStyleSelectAdapter textStyleSelectAdapter = new TextStyleSelectAdapter();
        this.mAdapter = textStyleSelectAdapter;
        this.mSmoothScroller = new LinearSmoothScroller(getContext()) { // from class: com.miui.aod.components.widget.TextAlignStyleSelectView.1
            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            public int calculateDtToFit(int i2, int i3, int i4, int i5, int i6) {
                return super.calculateDtToFit(i2, i3, i4, i5, i6) + TextAlignStyleSelectView.this.getResources().getDimensionPixelSize(R.dimen.rect_image_select_view_margin_start);
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
        androidx.recyclerview.widget.RecyclerView.ItemAnimator itemAnimator = getItemAnimator();
        if (itemAnimator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) itemAnimator).setSupportsChangeAnimations(false);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        this.mLayoutManager = linearLayoutManager;
        linearLayoutManager.setOrientation(0);
        setLayoutManager(this.mLayoutManager);
        setAdapter(textStyleSelectAdapter);
        setDivider(Utils.dp2Px(16));
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

    public void setDrawableData(Collection<DrawableGravityData> collection) {
        this.mDrawables.clear();
        this.mDrawables.addAll(collection);
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
