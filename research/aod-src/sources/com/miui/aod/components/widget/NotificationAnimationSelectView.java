package com.miui.aod.components.widget;

import android.content.Context;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.SimpleItemAnimator;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.components.DrawableData;
import com.miui.aod.resource.DrawableCache;
import com.miui.aod.util.MiAnimUtils;
import com.miui.aod.util.ToastUtils;
import com.miui.aod.widget.notification.NotificationStyleManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import miuix.internal.util.ViewUtils;
import miuix.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class NotificationAnimationSelectView extends RecyclerView implements View.OnClickListener {
    private final androidx.recyclerview.widget.RecyclerView.Adapter<ColorHolder> mAdapter;
    private int mCurrentSelection;
    private final List<DrawableData> mDrawables;
    private OnItemClickListener mItemClickListener;
    LinearLayoutManager mLayoutManager;
    private SpacesItemDecoration mSpacesItemDecoration;
    private final ToastUtils mToastUtils;

    public static class ColorHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final ImageView mIcon;
        private final View mIconContainer;
        private final ToastUtils mToast;

        public ColorHolder(View view, ToastUtils toastUtils) {
            super(view);
            MiAnimUtils.applyPressedTintAndScaleStyle(view);
            this.mIconContainer = view.findViewById(R.id.icon_container);
            ImageView imageView = (ImageView) view.findViewById(R.id.rect_icon);
            this.mIcon = imageView;
            if (imageView instanceof RoundCornerImageView) {
                ((RoundCornerImageView) imageView).setRectRoundRadius(imageView.getResources().getDimensionPixelSize(R.dimen.select_drawable_corner_radius));
            }
            this.mToast = toastUtils;
        }

        public void bindDrawable(DrawableData drawableData, boolean z) {
            this.mIcon.setImageResource(drawableData.mResId);
            if ("none".equals(drawableData.mName) || NotificationStyleManager.STYLE_NAME_SCREEN_ON.equals(drawableData.mName)) {
                this.mIcon.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            } else {
                this.mIcon.setScaleType(ImageView.ScaleType.FIT_XY);
            }
            if (z) {
                this.itemView.setForeground(DrawableCache.getInstance(this.mIcon.getContext()).getDrawable(R.drawable.aod_selected_rect));
            } else {
                this.itemView.setForeground(null);
            }
            View view = this.itemView;
            view.setContentDescription(NotificationStyleManager.getDrawableTitle(view.getContext(), drawableData));
            String drawableTitle = NotificationStyleManager.getDrawableTitle(this.itemView.getContext(), drawableData);
            int i = z ? R.string.mtrl_checkbox_state_description_checked : R.string.mtrl_checkbox_state_description_unchecked;
            this.itemView.setContentDescription(drawableTitle + "，" + this.itemView.getContext().getString(i));
        }
    }

    public class ColorSelectAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<ColorHolder> {
        private ColorSelectAdapter() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return NotificationAnimationSelectView.this.mDrawables.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(@NonNull ColorHolder colorHolder, int i) {
            if (i < 0 || i >= NotificationAnimationSelectView.this.mDrawables.size()) {
                return;
            }
            colorHolder.bindDrawable((DrawableData) NotificationAnimationSelectView.this.mDrawables.get(i), i == NotificationAnimationSelectView.this.mCurrentSelection);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        @NonNull
        public ColorHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_animation_drawable_item, viewGroup, false);
            viewInflate.setOnClickListener(NotificationAnimationSelectView.this);
            return new ColorHolder(viewInflate, NotificationAnimationSelectView.this.mToastUtils);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int i, DrawableData drawableData);
    }

    public class SpacesItemDecoration extends androidx.recyclerview.widget.RecyclerView.ItemDecoration {
        private final int space;

        public SpacesItemDecoration(int i) {
            this.space = i;
        }

        @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
        public void getItemOffsets(@NonNull Rect rect, @NonNull View view, @NonNull androidx.recyclerview.widget.RecyclerView recyclerView, @NonNull androidx.recyclerview.widget.RecyclerView.State state) {
            rect.left = this.space;
            int dimensionPixelSize = NotificationAnimationSelectView.this.getResources().getDimensionPixelSize(R.dimen.notification_animation_image_select_view_margin_start);
            if (recyclerView.getChildLayoutPosition(view) == 0) {
                rect.left = dimensionPixelSize;
            } else if (recyclerView.getChildLayoutPosition(view) == NotificationAnimationSelectView.this.mAdapter.getItemCount() - 1) {
                rect.right = dimensionPixelSize;
            }
            if (ViewUtils.isLayoutRtl(NotificationAnimationSelectView.this)) {
                int i = rect.left;
                rect.left = rect.right;
                rect.right = i;
            }
        }
    }

    public NotificationAnimationSelectView(Context context) {
        this(context, null);
    }

    public NotificationAnimationSelectView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public NotificationAnimationSelectView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDrawables = new ArrayList();
        ColorSelectAdapter colorSelectAdapter = new ColorSelectAdapter();
        this.mAdapter = colorSelectAdapter;
        this.mToastUtils = new ToastUtils();
        androidx.recyclerview.widget.RecyclerView.ItemAnimator itemAnimator = getItemAnimator();
        if (itemAnimator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) itemAnimator).setSupportsChangeAnimations(false);
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        this.mLayoutManager = linearLayoutManager;
        linearLayoutManager.setOrientation(0);
        setLayoutManager(this.mLayoutManager);
        setAdapter(colorSelectAdapter);
    }

    private void setItemSelected(int i) {
        int i2;
        if (i < 0 || i >= this.mAdapter.getItemCount() || i == (i2 = this.mCurrentSelection)) {
            return;
        }
        this.mCurrentSelection = i;
        this.mAdapter.notifyItemChanged(i2);
        this.mAdapter.notifyItemChanged(i);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        int childAdapterPosition = getChildAdapterPosition(view);
        if (Utils.isAodEnable(view.getContext()) && Utils.isFsAodSameToLock(view.getContext()) && childAdapterPosition >= 0 && childAdapterPosition < this.mDrawables.size() && NotificationStyleManager.isWaveDrawable(this.mDrawables.get(childAdapterPosition))) {
            this.mToastUtils.showToast(view.getContext(), view.getContext().getResources().getString(R.string.full_aod_not_support_toast_notification), 0);
            return;
        }
        setItemSelected(childAdapterPosition);
        OnItemClickListener onItemClickListener = this.mItemClickListener;
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(childAdapterPosition, this.mDrawables.get(childAdapterPosition));
        }
        view.announceForAccessibility(view.getContext().getString(R.string.mtrl_checkbox_state_description_checked));
    }

    public void onDestroyView() {
        ToastUtils toastUtils = this.mToastUtils;
        if (toastUtils != null) {
            toastUtils.cancelToast();
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
        setDivider(0);
        this.mAdapter.notifyDataSetChanged();
    }

    public void setItemSelected(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        int size = this.mDrawables.size();
        for (int i = 0; i < size; i++) {
            if (this.mDrawables.get(i).mName.equals(str)) {
                if (this.mCurrentSelection != i) {
                    setItemSelected(i);
                    smoothScrollToPosition(i);
                    return;
                }
                return;
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView
    public void smoothScrollToPosition(int i) {
        if (i < 0 || i >= this.mAdapter.getItemCount()) {
            return;
        }
        scrollToPosition(i);
    }
}
