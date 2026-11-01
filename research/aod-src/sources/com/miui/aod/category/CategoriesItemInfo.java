package com.miui.aod.category;

import android.content.Context;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.google.gson.annotations.SerializedName;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.stylelist.PadStyleCategoryAdapter;
import com.miui.aod.stylelist.StyleCategoryAdapter;
import com.miui.aod.stylelist.StyleListViewModel;
import com.miui.maml.R;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class CategoriesItemInfo implements StyleListViewModel.StyleListType {

    @SerializedName("categories")
    private List<StyleInfo> mCategoryInfos;

    @SerializedName("categoryname")
    private String mCateroryName;

    @SerializedName("needPlus")
    private boolean mNeedPlus;

    @Override // com.miui.aod.stylelist.StyleListViewModel.StyleListType
    public RecyclerView.Adapter<?> createAdapter(Context context, LifecycleOwner lifecycleOwner) {
        return CategoryConstants.CATEGORY_BIG_TIME.equals(this.mCateroryName) ? new PadStyleCategoryAdapter(context, getStyleInfos()) : new StyleCategoryAdapter(context, getStyleInfos(), this.mNeedPlus);
    }

    public String getCateroryName() {
        return this.mCateroryName;
    }

    @Override // com.miui.aod.stylelist.StyleListViewModel.StyleListType
    public /* bridge */ /* synthetic */ String getKey() {
        return super.getKey();
    }

    @Override // com.miui.aod.stylelist.StyleListViewModel.StyleListType
    public String getListType() {
        return this.mCateroryName;
    }

    public List<StyleInfo> getStyleInfos() {
        return this.mCategoryInfos;
    }

    @Override // com.miui.aod.stylelist.StyleListViewModel.StyleListType
    public String getValueRight() {
        List<StyleInfo> list = this.mCategoryInfos;
        return list == null ? "0" : String.valueOf(list.size());
    }

    @Override // com.miui.aod.stylelist.StyleListViewModel.StyleListType
    public int getViewType() {
        return R.styleable.AppCompatTheme_windowFixedWidthMinor;
    }

    @Override // com.miui.aod.stylelist.StyleListViewModel.StyleListType
    public /* bridge */ /* synthetic */ boolean isClickEnable() {
        return super.isClickEnable();
    }
}
