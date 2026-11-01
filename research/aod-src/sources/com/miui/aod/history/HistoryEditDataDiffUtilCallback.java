package com.miui.aod.history;

import android.os.Bundle;
import androidx.recyclerview.widget.DiffUtil;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class HistoryEditDataDiffUtilCallback extends DiffUtil.Callback {
    public final List mNewList;
    public final List mOldList;

    public HistoryEditDataDiffUtilCallback(List list, List list2) {
        this.mOldList = list == null ? Collections.EMPTY_LIST : list;
        this.mNewList = list2 == null ? Collections.EMPTY_LIST : list2;
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public boolean areContentsTheSame(int i, int i2) {
        return ((HistoryEditViewModel.HistoryEditData) this.mOldList.get(i)).isSelected() == ((HistoryEditViewModel.HistoryEditData) this.mNewList.get(i2)).isSelected();
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public boolean areItemsTheSame(int i, int i2) {
        return ((HistoryEditViewModel.HistoryEditData) this.mOldList.get(i)).mEntity.isContentSame(((HistoryEditViewModel.HistoryEditData) this.mNewList.get(i2)).mEntity);
    }

    @Override // androidx.recyclerview.widget.DiffUtil.Callback
    public Object getChangePayload(int i, int i2) {
        return new Bundle();
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
