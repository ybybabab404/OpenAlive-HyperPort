package com.miui.aod.components.widget;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import com.miui.aod.AODStyleController;
import com.miui.aod.R;
import com.miui.aod.category.ClockPanelCategoryInfo;
import com.miui.aod.components.view.AodContainerView;
import com.miui.aod.util.HapticUtils;
import java.util.ArrayList;
import java.util.List;
import miuix.animation.Folme;
import miuix.animation.base.AnimConfig;
import miuix.recyclerview.widget.RecyclerView;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public class ClockPanelSelectView extends RecyclerView implements View.OnClickListener {
    private final androidx.recyclerview.widget.RecyclerView.Adapter<ColorHolder> mAdapter;
    private String mCateName;
    private int mCurrentSelection;
    private final boolean mIs24Format;
    private OnItemClickListener mItemClickListener;
    private final int mItemMargin;
    private final int mItemMarginStart;
    LinearLayoutManager mLayoutManager;
    private final List<String> mParameters;
    androidx.recyclerview.widget.RecyclerView.SmoothScroller mSmoothScroller;

    public class ColorHolder extends androidx.recyclerview.widget.RecyclerView.ViewHolder {
        private final AODStyleController mAodStyleController;

        public ColorHolder(View view) {
            super(view);
            this.mAodStyleController = new AODStyleController(4);
            ImageView imageView = (ImageView) view.findViewById(R.id.rect_icon);
            if (imageView instanceof RoundCornerImageView) {
                ((RoundCornerImageView) imageView).setRectRoundRadius(imageView.getResources().getDimensionPixelSize(R.dimen.select_drawable_corner_radius));
            }
        }

        private String extractPanelStyle(String str) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("is_vertical")) {
                    return "is_vertical";
                }
                if (jSONObject.has("is_left_gravity")) {
                    return "is_left_gravity";
                }
                if (!jSONObject.has("style")) {
                    return jSONObject.optString("panel", "");
                }
                return "style_" + jSONObject.getString("style");
            } catch (JSONException unused) {
                return "";
            }
        }

        private void hideViewById(View view, int i) {
            View viewFindViewById;
            if (view == null || (viewFindViewById = view.findViewById(i)) == null) {
                return;
            }
            viewFindViewById.setVisibility(8);
        }

        public void bindDrawable(String str, boolean z) {
            String string = this.itemView.getContext().getString(ClockPanelCategoryInfo.ACCESSIBILITY_NAME_MAP.getOrDefault(extractPanelStyle(str), Integer.valueOf(R.string.default_clock_style)).intValue());
            if (z) {
                string = string + this.itemView.getContext().getString(R.string.mtrl_checkbox_state_description_checked);
            }
            this.itemView.setContentDescription(string);
            Log.d("cong", "parameter " + str + " selected " + z);
            this.mAodStyleController.inflateView(this.itemView, ClockPanelSelectView.this.mCateName, str);
            this.mAodStyleController.handleUpdateTime(ClockPanelSelectView.this.mIs24Format);
            hideViewById(this.itemView, R.id.city);
            hideViewById(this.itemView, R.id.date);
            hideViewById(this.itemView, R.id.date_lunar);
            ((AodContainerView) this.itemView).showBatteryIcon(false);
            int i = z ? R.drawable.aod_selected_rect : 0;
            this.itemView.setForeground(i > 0 ? ResourcesCompat.getDrawable(this.itemView.getResources(), i, null) : null);
            Folme.useAt(this.itemView).touch().handleTouchOf(this.itemView, new AnimConfig[0]);
        }

        public void updateTime(boolean z) {
            this.mAodStyleController.handleUpdateTime(z);
        }
    }

    public class ColorSelectAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<ColorHolder> {
        private ColorSelectAdapter() {
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return ClockPanelSelectView.this.mParameters.size();
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public /* bridge */ /* synthetic */ void onBindViewHolder(@NonNull androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolder, int i, @NonNull List list) {
            onBindViewHolder((ColorHolder) viewHolder, i, (List<Object>) list);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public void onBindViewHolder(@NonNull ColorHolder colorHolder, int i) {
            if (i < 0 || i >= ClockPanelSelectView.this.mParameters.size()) {
                return;
            }
            colorHolder.bindDrawable((String) ClockPanelSelectView.this.mParameters.get(i), i == ClockPanelSelectView.this.mCurrentSelection);
        }

        public void onBindViewHolder(@NonNull ColorHolder colorHolder, int i, @NonNull List<Object> list) {
            if (list.isEmpty()) {
                onBindViewHolder(colorHolder, i);
                return;
            }
            for (Object obj : list) {
                if (obj instanceof Boolean) {
                    colorHolder.updateTime(((Boolean) obj).booleanValue());
                    return;
                }
            }
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        @NonNull
        public ColorHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View viewInflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.clock_panel_item_layout, viewGroup, false);
            viewInflate.setOnClickListener(ClockPanelSelectView.this);
            return ClockPanelSelectView.this.new ColorHolder(viewInflate);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int i, String str, String str2);
    }

    public ClockPanelSelectView(Context context) {
        this(context, null);
    }

    public ClockPanelSelectView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ClockPanelSelectView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mParameters = new ArrayList();
        ColorSelectAdapter colorSelectAdapter = new ColorSelectAdapter();
        this.mAdapter = colorSelectAdapter;
        this.mSmoothScroller = new LinearSmoothScroller(getContext()) { // from class: com.miui.aod.components.widget.ClockPanelSelectView.1
            @Override // androidx.recyclerview.widget.LinearSmoothScroller
            public int calculateDtToFit(int i2, int i3, int i4, int i5, int i6) {
                return (super.calculateDtToFit(i2, i3, i4, i5, i6) + ClockPanelSelectView.this.mItemMarginStart) - ClockPanelSelectView.this.mItemMargin;
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
        int dimensionPixelSize = getResources().getDimensionPixelSize(R.dimen.rect_image_select_view_margin_start);
        this.mItemMarginStart = dimensionPixelSize;
        int dimensionPixelSize2 = getResources().getDimensionPixelSize(R.dimen.rect_item_margin);
        this.mItemMargin = dimensionPixelSize2;
        this.mIs24Format = DateFormat.is24HourFormat(context);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        this.mLayoutManager = linearLayoutManager;
        linearLayoutManager.setOrientation(0);
        setLayoutManager(this.mLayoutManager);
        addItemDecoration(new MarginItemDecoration(dimensionPixelSize, dimensionPixelSize2));
        setAdapter(colorSelectAdapter);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        HapticUtils.setClickHapticFeedback(view);
        int childAdapterPosition = getChildAdapterPosition(view);
        setItemSelected(childAdapterPosition);
        OnItemClickListener onItemClickListener = this.mItemClickListener;
        if (onItemClickListener != null) {
            onItemClickListener.onItemClick(childAdapterPosition, this.mCateName, this.mParameters.get(childAdapterPosition));
        }
    }

    public void setDate(String str, List<String> list) {
        this.mCateName = str;
        this.mParameters.addAll(list);
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
        if (i <= 2 || i >= this.mAdapter.getItemCount()) {
            return;
        }
        this.mSmoothScroller.setTargetPosition(i);
        this.mLayoutManager.startSmoothScroll(this.mSmoothScroller);
    }

    public void updateTime() {
        androidx.recyclerview.widget.RecyclerView.Adapter<ColorHolder> adapter = this.mAdapter;
        adapter.notifyItemRangeChanged(0, adapter.getItemCount(), Boolean.valueOf(this.mIs24Format));
    }
}
