package com.miui.aod.components.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import com.miui.aod.R;
import com.miui.aod.components.DrawableData;
import com.miui.aod.util.HapticUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class ColorSelectView extends RecyclerView implements View.OnClickListener {
    private final androidx.recyclerview.widget.RecyclerView.Adapter<ColorHolder> mAdapter;
    private final List<DrawableData> mColors;
    private int mCurrentSelection;
    private OnItemClickListener mItemClickListener;
    private final int mItemMargin;
    private final int mItemMarginStart;
    LinearLayoutManager mLayoutManager;
    androidx.recyclerview.widget.RecyclerView.SmoothScroller mSmoothScroller;

    public static class ColorHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final ImageView mIcon;
        private final ImageView mSelectView;

        public ColorHolder(View view) {
            super(view);
            this.mIcon = (ImageView) view.findViewById(R.id.circle_icon);
            this.mSelectView = (ImageView) view.findViewById(R.id.select_circle);
        }

        public void bindDrawable(DrawableData drawableData, boolean z) {
            this.mIcon.setImageResource(drawableData.mResId);
            this.mSelectView.setVisibility(z ? 0 : 4);
            Folme.useAt(this.itemView).touch().handleTouchOf(this.itemView, new AnimConfig[0]);
            String string = drawableData.mAccessibilityDescriptionResId != 0 ? this.itemView.getContext().getString(drawableData.mAccessibilityDescriptionResId) : drawableData.mName;
            if (!z) {
                this.itemView.setContentDescription(string);
                return;
            }
            this.itemView.setContentDescription(this.itemView.getContext().getString(R.string.mtrl_checkbox_state_description_checked) + "，" + string);
        }
    }

    public class ColorSelectAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<ColorHolder> {
        private ColorSelectAdapter() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return ColorSelectView.this.mColors.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(@NonNull ColorHolder colorHolder, int i) {
            if (i < 0 || i >= ColorSelectView.this.mColors.size()) {
                return;
            }
            colorHolder.bindDrawable((DrawableData) ColorSelectView.this.mColors.get(i), i == ColorSelectView.this.mCurrentSelection);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        @NonNull
        public ColorHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.circle_drawable_item, viewGroup, false);
            viewInflate.setOnClickListener(ColorSelectView.this);
            return new ColorHolder(viewInflate);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int i, DrawableData drawableData);
    }

    public ColorSelectView(Context context) {
        this(context, null);
    }

    public ColorSelectView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ColorSelectView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mColors = new ArrayList();
        ColorSelectAdapter colorSelectAdapter = new ColorSelectAdapter();
        this.mAdapter = colorSelectAdapter;
        this.mSmoothScroller = new LinearSmoothScroller(getContext()) { // from class: com.miui.aod.components.widget.ColorSelectView.1
            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            public int calculateDtToFit(int i2, int i3, int i4, int i5, int i6) {
                return (super.calculateDtToFit(i2, i3, i4, i5, i6) + ColorSelectView.this.mItemMarginStart) - ColorSelectView.this.mItemMargin;
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        this.mLayoutManager = linearLayoutManager;
        linearLayoutManager.setOrientation(0);
        setLayoutManager(this.mLayoutManager);
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.rect_image_select_view_margin_start);
        this.mItemMarginStart = dimensionPixelSize;
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.circle_item_margin);
        this.mItemMargin = dimensionPixelSize2;
        addItemDecoration(new MarginItemDecoration(dimensionPixelSize, dimensionPixelSize2));
        setAdapter(colorSelectAdapter);
    }

    public List<DrawableData> getData() {
        return this.mColors;
    }

    public int getSelection() {
        return this.mCurrentSelection;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (isEnabled()) {
            HapticUtils.setClickHapticFeedback(view);
            int childAdapterPosition = getChildAdapterPosition(view);
            setItemSelected(childAdapterPosition);
            OnItemClickListener onItemClickListener = this.mItemClickListener;
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(childAdapterPosition, this.mColors.get(childAdapterPosition));
            }
        }
    }

    public void setDrawableData(Collection<DrawableData> collection) {
        this.mColors.clear();
        this.mColors.addAll(collection);
        this.mAdapter.notifyDataSetChanged();
    }

    public void setItemSelected(int i) {
        int i2 = this.mCurrentSelection;
        if (i != i2) {
            this.mCurrentSelection = i;
            this.mAdapter.notifyItemChanged(i2);
            this.mAdapter.notifyItemChanged(i);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void smoothScrollToPosition(int i) {
        if (i < 2 || i >= this.mAdapter.getItemCount()) {
            return;
        }
        this.mSmoothScroller.setTargetPosition(i);
        this.mLayoutManager.startSmoothScroll(this.mSmoothScroller);
    }
}
