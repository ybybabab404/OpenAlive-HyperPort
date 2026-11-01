package com.miui.aod.stylelist;

import android.content.Context;
import com.google.gson.reflect.TypeToken;
import com.miui.aod.AODApplication;
import com.miui.aod.aodannotation.JsonStringBinder;
import com.miui.aod.category.BigTimeCategoryInfo;
import com.miui.aod.category.CategoriesItemInfo;
import com.miui.aod.category.CategoryFactory;
import com.miui.aod.common.StyleInfo;
import com.miui.maml.R;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class PadStyleListViewModel {
    public String mCateListJson;
    public List mCategoriesInfoList;
    public final SettingsType mSettingsType;
    public List mStyleListData;
    public String[] titleArray;

    public static class SettingsType implements StyleListViewModel.StyleListType {
        @Override // com.miui.aod.stylelist.StyleListViewModel.StyleListType
        public String getListType() {
            return "SettingsType";
        }

        @Override // com.miui.aod.stylelist.StyleListViewModel.StyleListType
        public int getViewType() {
            return R.styleable.AppCompatTheme_windowMinWidthMinor;
        }
    }

    public PadStyleListViewModel(Context context) {
        SettingsType settingsType = new SettingsType();
        this.mSettingsType = settingsType;
        JsonStringBinder.bind(this);
        ArrayList arrayList = new ArrayList();
        this.mStyleListData = arrayList;
        arrayList.add(settingsType);
    }

    public static PadStyleListViewModel newInstance(Context context) {
        return new PadStyleListViewModel(context);
    }

    public List loadStyleListData() {
        if (this.titleArray == null) {
            this.titleArray = AODApplication.sInstance.getResources().getStringArray(com.miui.aod.R.array.big_time_clock_title_array);
        }
        if (this.mCategoriesInfoList == null) {
            List list = (List) CategoryFactory.getCategoriesGson().fromJson(this.mCateListJson, new TypeToken<List<CategoriesItemInfo>>() { // from class: com.miui.aod.stylelist.PadStyleListViewModel.1
            }.getType());
            this.mCategoriesInfoList = list;
            if (list != null && list.size() > 0) {
                CategoriesItemInfo categoriesItemInfo = (CategoriesItemInfo) this.mCategoriesInfoList.get(0);
                if (categoriesItemInfo != null) {
                    List<StyleInfo> styleInfos = categoriesItemInfo.getStyleInfos();
                    String[] strArr = this.titleArray;
                    if (strArr != null && strArr.length > 0 && styleInfos != null && styleInfos.size() > 0) {
                        for (int i = 0; i < this.titleArray.length && i < styleInfos.size(); i++) {
                            ((BigTimeCategoryInfo) styleInfos.get(i)).setTitle(this.titleArray[i]);
                        }
                    }
                }
            }
            this.mStyleListData.addAll(this.mCategoriesInfoList);
        }
        return this.mStyleListData;
    }
}
