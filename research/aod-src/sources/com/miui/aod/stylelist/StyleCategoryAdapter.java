package com.miui.aod.stylelist;

import android.content.Context;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.aod.AODStyleController;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.CategoryInfo;
import com.miui.aod.category.OnelineCategoryInfo;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.view.AodContainerView;
import com.miui.aod.util.MiAnimUtils;
import com.miui.keyguard.editor.view.RoundOutlineProvider;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.greenrobot.eventbus.EventBus;

/* JADX INFO: loaded from: classes.dex */
public class StyleCategoryAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public AODStyleController mAODStyleController;
    public final Context mContext;
    public CategoryInfo mImageSelectInfo;
    public int mItemHeight;
    public int mItemWidth;
    public final boolean mNeedPlus;
    public final List mStyleInfoList;

    public static class CategoryData {
        public final StyleInfo mProductsBean;

        public CategoryData(StyleInfo styleInfo) {
            this.mProductsBean = styleInfo;
        }
    }

    public static class ClickEvent {
        public final StyleInfo mData;
        public final int mPosition;
        public String mType;
        public final WeakReference mView;

        public ClickEvent(int i, View view, StyleInfo styleInfo) {
            this(i, view, styleInfo, null);
        }

        public ClickEvent(int i, View view, StyleInfo styleInfo, String str) {
            this.mPosition = i;
            this.mView = new WeakReference(view);
            this.mData = styleInfo;
            this.mType = str;
        }
    }

    public class PlusViewHolder extends ViewHolder {
        public PlusViewHolder(View view) {
            super(view);
            TextView textView = (TextView) view.findViewById(R.id.add_from_album_tip);
            if (Locale.CHINESE.getLanguage().equals(Locale.getDefault().getLanguage())) {
                textView.setMaxWidth((int) (textView.getTextSize() * 3.0f));
            }
        }

        @Override // com.miui.aod.stylelist.StyleCategoryAdapter.ViewHolder
        public void bind(StyleInfo styleInfo) {
        }

        @Override // com.miui.aod.stylelist.StyleCategoryAdapter.ViewHolder, android.view.View.OnClickListener
        public /* bridge */ /* synthetic */ void onClick(View view) {
            super.onClick(view);
        }
    }

    public class ViewHolder extends BaseViewHolder implements View.OnClickListener {
        public ViewHolder(View view) {
            super(view);
            MiAnimUtils.applyScaleStyle(view);
            if (view instanceof AodContainerView) {
                ((AodContainerView) view).setNeedDrawEdge(true);
            }
            view.setOnClickListener(this);
            view.setBackgroundResource(R.drawable.style_category_list_item_bg);
            view.setOutlineProvider(new RoundOutlineProvider(StyleCategoryAdapter.this.mContext.getResources().getDimensionPixelSize(R.dimen.aod_style_bg_radius)));
        }

        public void bind(StyleInfo styleInfo) {
            if (styleInfo == null) {
                return;
            }
            if (StyleCategoryAdapter.this.mAODStyleController == null) {
                StyleCategoryAdapter.this.mAODStyleController = new AODStyleController(2);
            }
            String fullTitle = styleInfo.getFullTitle();
            if (!TextUtils.isEmpty(fullTitle)) {
                this.itemView.setContentDescription(fullTitle);
            }
            StyleCategoryAdapter.this.mAODStyleController.inflateView(this.itemView, styleInfo);
            StyleCategoryAdapter.this.mAODStyleController.handleUpdateTime(DateFormat.is24HourFormat(this.itemView.getContext()));
            StyleCategoryAdapter.this.mAODStyleController.updateAodBgGravitiy();
        }

        public void onClick(View view) {
            int bindingAdapterPosition = getBindingAdapterPosition();
            if (bindingAdapterPosition < 0 || bindingAdapterPosition >= StyleCategoryAdapter.this.mStyleInfoList.size()) {
                return;
            }
            EventBus.getDefault().post(new ClickEvent(bindingAdapterPosition, view, ((CategoryData) StyleCategoryAdapter.this.mStyleInfoList.get(bindingAdapterPosition)).mProductsBean));
        }
    }

    public StyleCategoryAdapter(Context context, List list, boolean z) {
        ArrayList arrayList = new ArrayList();
        this.mStyleInfoList = arrayList;
        this.mNeedPlus = z;
        if (z) {
            OnelineCategoryInfo onelineCategoryInfoNewPlusCategoryInfo = OnelineCategoryInfo.newPlusCategoryInfo();
            this.mImageSelectInfo = onelineCategoryInfoNewPlusCategoryInfo;
            arrayList.add(new CategoryData(onelineCategoryInfoNewPlusCategoryInfo));
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            this.mStyleInfoList.add(new CategoryData((StyleInfo) it.next()));
        }
        this.mContext = context;
        setItemWidth();
    }

    private void setItemWidth() {
        Context context = this.mContext;
        if (context == null) {
            return;
        }
        this.mItemHeight = Utils.getSmallViewHeight(context);
        this.mItemWidth = Utils.getStyleItemWidth(this.mContext);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mStyleInfoList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return (this.mNeedPlus && this.mImageSelectInfo != null && i == 0) ? 22 : 23;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(BaseViewHolder baseViewHolder, int i) {
        ((ViewHolder) baseViewHolder).bind(((CategoryData) this.mStyleInfoList.get(i)).mProductsBean);
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
        if (i == 22) {
            View viewInflate = layoutInflaterFrom.inflate(R.layout.add_from_album, viewGroup, false);
            viewInflate.getLayoutParams().width = this.mItemWidth;
            viewInflate.getLayoutParams().height = this.mItemHeight;
            return new PlusViewHolder(viewInflate);
        }
        if (i != 23) {
            throw new IllegalArgumentException("wrong viewType");
        }
        View viewInflate2 = layoutInflaterFrom.inflate(R.layout.aod_style_category_item, viewGroup, false);
        viewInflate2.getLayoutParams().width = this.mItemWidth;
        viewInflate2.getLayoutParams().height = this.mItemHeight;
        return new ViewHolder(viewInflate2);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(BaseViewHolder baseViewHolder) {
        View view = baseViewHolder.itemView;
        if (view instanceof AodContainerView) {
            ((AodContainerView) view).releaseDrawable();
        }
        super.onViewRecycled(baseViewHolder);
    }
}
