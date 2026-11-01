package com.miui.aod.stylelist;

import android.content.Context;
import android.text.format.DateFormat;
import android.view.DisplayCutout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.Gson;
import com.miui.aod.AODStyleController;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.aodannotation.JsonStringBinder;
import com.miui.aod.category.BigTimeCategoryInfo;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.view.AodContainerView;
import com.miui.aod.util.MiAnimUtils;
import com.miui.aod.util.WindowUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PadStyleCategoryAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public AODStyleController mAODStyleController;
    public final Context mContext;
    public int mItemHeight;
    public int mItemWidth;
    public OnAodItemClickListener mListener;
    public String mSelectLabel = "";
    public final List mStyleInfoList;

    public interface OnAodItemClickListener {
        void onAodItemClick(int i);
    }

    public class ViewHolder extends BaseViewHolder implements View.OnClickListener {
        public AodContainerView aodContainerView;
        public View rootView;

        public ViewHolder(View view) {
            super(view);
            this.rootView = view.findViewById(R.id.item_container);
            AodContainerView aodContainerView = (AodContainerView) view.findViewById(R.id.aod_style_item);
            this.aodContainerView = aodContainerView;
            MiAnimUtils.applyScaleStyle(aodContainerView);
            AodContainerView aodContainerView2 = this.aodContainerView;
            if (aodContainerView2 != null) {
                aodContainerView2.setNeedDrawEdge(true);
                this.aodContainerView.setOnClickListener(this);
            }
        }

        private void setStyleInfo(StyleInfo styleInfo) {
            BigTimeCategoryInfo bigTimeCategoryInfo = (BigTimeCategoryInfo) new Gson().fromJson(Utils.getAodUsingCategoryParam(PadStyleCategoryAdapter.this.mContext, CategoryConstants.CATEGORY_BIG_TIME), BigTimeCategoryInfo.class);
            if (bigTimeCategoryInfo != null) {
                styleInfo.switchOnNotificationIcon(bigTimeCategoryInfo.isNotificationSwitchOn());
                styleInfo.switchOnBatteryIcon(bigTimeCategoryInfo.isBatterySwitchOn());
                styleInfo.switchOnDateAndTime(bigTimeCategoryInfo.isDateAndTimeSwitchOn());
                styleInfo.setLunarSwitchOn(bigTimeCategoryInfo.isLunarSwitchOn(PadStyleCategoryAdapter.this.mContext));
                String json = new Gson().toJson(styleInfo);
                Utils.setAodUsingCategoryName(PadStyleCategoryAdapter.this.mContext, CategoryConstants.CATEGORY_BIG_TIME);
                Utils.setAodUsingCategory(PadStyleCategoryAdapter.this.mContext, CategoryConstants.CATEGORY_BIG_TIME, json);
            }
        }

        public void bind(StyleInfo styleInfo) {
            if (PadStyleCategoryAdapter.this.mAODStyleController == null) {
                PadStyleCategoryAdapter.this.mAODStyleController = new AODStyleController(2);
            }
            if (styleInfo.getLabel().equals(PadStyleCategoryAdapter.this.mSelectLabel)) {
                if (Utils.isAodEnable(PadStyleCategoryAdapter.this.mContext)) {
                    this.rootView.setBackgroundResource(R.drawable.bg_aod_item_pad);
                } else {
                    this.rootView.setBackgroundResource(R.drawable.bg_aod_item_pad_alpha);
                }
            }
            if (styleInfo instanceof BigTimeCategoryInfo) {
                this.aodContainerView.setContentDescription(((BigTimeCategoryInfo) styleInfo).getTitle());
            }
            PadStyleCategoryAdapter.this.mAODStyleController.inflateView(this.aodContainerView, styleInfo);
            PadStyleCategoryAdapter.this.mAODStyleController.handleUpdateTime(DateFormat.is24HourFormat(this.aodContainerView.getContext()));
            PadStyleCategoryAdapter.this.mAODStyleController.updateAodBgGravitiy();
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            if (Utils.isAodSettingsEnabled(PadStyleCategoryAdapter.this.mContext)) {
                this.rootView.setBackgroundResource(R.drawable.bg_aod_item_pad);
                int bindingAdapterPosition = getBindingAdapterPosition();
                if (bindingAdapterPosition < 0 || bindingAdapterPosition >= PadStyleCategoryAdapter.this.mStyleInfoList.size()) {
                    return;
                }
                setStyleInfo((BigTimeCategoryInfo) ((StyleCategoryAdapter.CategoryData) PadStyleCategoryAdapter.this.mStyleInfoList.get(bindingAdapterPosition)).mProductsBean);
                if (PadStyleCategoryAdapter.this.mListener != null) {
                    PadStyleCategoryAdapter.this.mListener.onAodItemClick(bindingAdapterPosition);
                }
            }
        }
    }

    public PadStyleCategoryAdapter(Context context, List list) {
        JsonStringBinder.bind(this);
        this.mStyleInfoList = new ArrayList();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            this.mStyleInfoList.add(new StyleCategoryAdapter.CategoryData((StyleInfo) it.next()));
        }
        this.mContext = context;
        setItemWidth();
    }

    private void setItemWidth() {
        int widthPixel = WindowUtils.INSTANCE.getWidthPixel(this.mContext);
        DisplayCutout cutout = this.mContext.getDisplay().getCutout();
        if (cutout != null && this.mContext.getDisplay().getRotation() == 0) {
            widthPixel -= cutout.getBoundingRectRight().width();
        }
        int dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.aod_setting_padding_horizontal_pad_vertical);
        this.mItemHeight = this.mContext.getResources().getDimensionPixelSize(R.dimen.aod_setting_category_item_height_vertical);
        if (2 == this.mContext.getResources().getConfiguration().orientation) {
            dimensionPixelSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.aod_setting_padding_horizontal_pad);
            this.mItemHeight = this.mContext.getResources().getDimensionPixelSize(R.dimen.aod_setting_category_item_height);
        }
        this.mItemWidth = (widthPixel - (dimensionPixelSize * 2)) / 3;
    }

    public List getData() {
        return this.mStyleInfoList;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mStyleInfoList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return 23;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BaseViewHolder baseViewHolder, int i) {
        ((ViewHolder) baseViewHolder).bind(((StyleCategoryAdapter.CategoryData) this.mStyleInfoList.get(i)).mProductsBean);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BaseViewHolder baseViewHolder, int i, List list) {
        if (list.isEmpty()) {
            onBindViewHolder(baseViewHolder, i);
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public BaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(viewGroup.getContext());
        if (i != 23) {
            throw new IllegalArgumentException("wrong viewType");
        }
        View viewInflate = layoutInflaterFrom.inflate(R.layout.aod_style_category_item_pad, viewGroup, false);
        viewInflate.getLayoutParams().width = this.mItemWidth;
        viewInflate.getLayoutParams().height = this.mItemHeight;
        return new ViewHolder(viewInflate);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(BaseViewHolder baseViewHolder) {
        View view = baseViewHolder.itemView;
        if (view instanceof AodContainerView) {
            ((AodContainerView) view).releaseDrawable();
        }
        super.onViewRecycled(baseViewHolder);
    }

    public void setAodItemClickListener(OnAodItemClickListener onAodItemClickListener) {
        this.mListener = onAodItemClickListener;
    }

    public void setSelect(String str) {
        this.mSelectLabel = str;
    }
}
