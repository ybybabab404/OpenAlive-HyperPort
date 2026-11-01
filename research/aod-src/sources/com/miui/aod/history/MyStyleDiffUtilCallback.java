package com.miui.aod.history;

import androidx.recyclerview.widget.DiffUtil;
import com.miui.aod.category.CategoryInfo;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class MyStyleDiffUtilCallback extends DiffUtil.Callback {
    public final List mNewList;
    public final List mOldList;

    public MyStyleDiffUtilCallback(List list, List list2) {
        this.mOldList = list == null ? Collections.EMPTY_LIST : list;
        this.mNewList = list2 == null ? Collections.EMPTY_LIST : list2;
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public boolean areContentsTheSame(int i, int i2) {
        return areItemsTheSame(i, i2);
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public boolean areItemsTheSame(int i, int i2) {
        MyStyleAdapter.MyStyleData myStyleData = (MyStyleAdapter.MyStyleData) this.mOldList.get(i);
        MyStyleAdapter.MyStyleData myStyleData2 = (MyStyleAdapter.MyStyleData) this.mNewList.get(i2);
        CategoryInfo categoryInfo = myStyleData.mCateInfo;
        if (categoryInfo == null && myStyleData2.mCateInfo != null) {
            return false;
        }
        if (categoryInfo != null && myStyleData2.mCateInfo == null) {
            return false;
        }
        if (myStyleData.isHistory() && myStyleData2.isHistory()) {
            return myStyleData.mEntity.isContentSame(myStyleData2.mEntity);
        }
        CategoryInfo categoryInfo2 = myStyleData.mCateInfo;
        if (categoryInfo2 == null) {
            return false;
        }
        return categoryInfo2.isSameHistoryRecord(myStyleData2.mCateInfo);
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public int getNewListSize() {
        return this.mNewList.size();
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public int getOldListSize() {
        return this.mOldList.size();
    }
}
