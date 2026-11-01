package com.miui.aod.stylelist;

import androidx.recyclerview.widget.DiffUtil;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
class StyleListDiffUtilCallback extends DiffUtil.Callback {
    public final List mNewList;
    public final List mOldList;

    public StyleListDiffUtilCallback(List list, List list2) {
        this.mOldList = list == null ? Collections.EMPTY_LIST : list;
        this.mNewList = list2 == null ? Collections.EMPTY_LIST : list2;
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public boolean areContentsTheSame(int i, int i2) {
        StyleListViewModel.StyleListType styleListType = (StyleListViewModel.StyleListType) this.mOldList.get(i);
        return !styleListType.getListType().equals("my_aod_style");
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public boolean areItemsTheSame(int i, int i2) {
        return ((StyleListViewModel.StyleListType) this.mOldList.get(i)).getListType().equals(((StyleListViewModel.StyleListType) this.mNewList.get(i2)).getListType());
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public Object getChangePayload(int i, int i2) {
        if (((StyleListViewModel.StyleListType) this.mOldList.get(i)).getListType().equals("my_aod_style")) {
            return Boolean.TRUE;
        }
        return null;
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
