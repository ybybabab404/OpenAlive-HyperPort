package com.miui.aod.stylelist;

import android.content.Context;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.aod.history.HistorySubViewModel;
import com.miui.aod.history.MyStyleAdapter;
import com.miui.maml.R;

/* JADX INFO: loaded from: classes.dex */
public class MyStyleListType implements StyleListViewModel.StyleListType {
    public HistorySubViewModel.HistorySubData mHistorySubData;
    public MyStyleAdapter myStyleAdapter;

    @Override // com.miui.aod.stylelist.StyleListViewModel.StyleListType
    public RecyclerView.Adapter createAdapter(Context context, LifecycleOwner lifecycleOwner) {
        MyStyleAdapter myStyleAdapter = new MyStyleAdapter(context);
        this.myStyleAdapter = myStyleAdapter;
        myStyleAdapter.onChanged(this.mHistorySubData);
        return this.myStyleAdapter;
    }

    @Override // com.miui.aod.stylelist.StyleListViewModel.StyleListType
    public String getListType() {
        return "my_aod_style";
    }

    @Override // com.miui.aod.stylelist.StyleListViewModel.StyleListType
    public String getValueRight() {
        HistorySubViewModel.HistorySubData historySubData = this.mHistorySubData;
        return historySubData == null ? "0" : String.valueOf(historySubData.mCount);
    }

    @Override // com.miui.aod.stylelist.StyleListViewModel.StyleListType
    public int getViewType() {
        return R.styleable.AppCompatTheme_windowFixedWidthMinor;
    }

    @Override // com.miui.aod.stylelist.StyleListViewModel.StyleListType
    public boolean isClickEnable() {
        HistorySubViewModel.HistorySubData historySubData = this.mHistorySubData;
        return historySubData != null && historySubData.mCount > 1;
    }

    public void updateData(HistorySubViewModel.HistorySubData historySubData) {
        this.mHistorySubData = historySubData;
        MyStyleAdapter myStyleAdapter = this.myStyleAdapter;
        if (myStyleAdapter == null || historySubData == null) {
            return;
        }
        myStyleAdapter.onChanged(historySubData);
    }
}
