package com.miui.aod.stylelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.collection.ArrayMap;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.aod.R;
import com.miui.aod.settings.PadSettingsView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class PadStyleListAdapter extends RecyclerView.Adapter<ViewHolder> {
    public final Context mContext;
    public final LayoutInflater mLayoutInflater;
    public final Map mAdapterMap = new ArrayMap();
    public volatile List mDataList = new ArrayList();

    public class StyleListViewHolder extends ViewHolder {
        public final PadStyleListItemView mItemView;

        public StyleListViewHolder(PadStyleListItemView padStyleListItemView) {
            super(padStyleListItemView);
            this.mItemView = padStyleListItemView;
        }

        @Override // com.miui.aod.stylelist.PadStyleListAdapter.ViewHolder
        public void bind(StyleListViewModel.StyleListType styleListType) {
            this.mItemView.setAdapter(PadStyleListAdapter.this.getAdapter(styleListType));
        }

        public void bindTitleInfo(StyleListViewModel.StyleListType styleListType) {
        }

        public void recycle() {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface VIEW_TYPE {
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View view) {
            super(view);
        }

        public void bind(StyleListViewModel.StyleListType styleListType) {
        }
    }

    public PadStyleListAdapter(Context context) {
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public static PadStyleListAdapter newInstance(Context context) {
        return new PadStyleListAdapter(context);
    }

    public final RecyclerView.Adapter getAdapter(StyleListViewModel.StyleListType styleListType) {
        String listType = styleListType.getListType();
        if (this.mAdapterMap.containsKey(listType)) {
            return (RecyclerView.Adapter) this.mAdapterMap.get(listType);
        }
        RecyclerView.Adapter adapterCreateAdapter = styleListType.createAdapter(this.mContext, null);
        if (adapterCreateAdapter != null) {
            this.mAdapterMap.put(listType, adapterCreateAdapter);
        }
        return adapterCreateAdapter;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mDataList.size();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        if (i < 0 || i >= this.mDataList.size()) {
            return 0;
        }
        return ((StyleListViewModel.StyleListType) this.mDataList.get(i)).getViewType();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        if (i < 0 || i >= this.mDataList.size()) {
            return;
        }
        viewHolder.bind((StyleListViewModel.StyleListType) this.mDataList.get(i));
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i, List list) {
        if (list.isEmpty()) {
            onBindViewHolder(viewHolder, i);
            return;
        }
        Iterator it = list.iterator();
        while (it.hasNext()) {
            if ((it.next() instanceof Boolean) && (viewHolder instanceof StyleListViewHolder) && i >= 0 && i < this.mDataList.size()) {
                ((StyleListViewHolder) viewHolder).bindTitleInfo((StyleListViewModel.StyleListType) this.mDataList.get(i));
            }
        }
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 123) {
            return new StyleListViewHolder((PadStyleListItemView) this.mLayoutInflater.inflate(R.layout.style_list_item_pad, viewGroup, false));
        }
        if (i == 125) {
            return new ViewHolder((PadSettingsView) this.mLayoutInflater.inflate(R.layout.settings_item_pad, viewGroup, false));
        }
        throw new IllegalArgumentException("wrong viewType");
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onViewRecycled(ViewHolder viewHolder) {
        super.onViewRecycled(viewHolder);
        if (viewHolder instanceof StyleListViewHolder) {
            ((StyleListViewHolder) viewHolder).recycle();
        }
    }

    public void setData(List list) {
        this.mDataList = list;
    }
}
