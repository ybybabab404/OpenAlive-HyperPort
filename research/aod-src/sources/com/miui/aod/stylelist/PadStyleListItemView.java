package com.miui.aod.stylelist;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import com.google.gson.Gson;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.BigTimeCategoryInfo;
import com.miui.aod.category.CategoryConstants;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.components.widget.ITimeUpdate;
import com.miui.aod.util.DeviceInfo;
import java.util.List;
import miuix.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class PadStyleListItemView extends LinearLayout implements ITimeUpdate, PadStyleCategoryAdapter.OnAodItemClickListener {
    public int mLastSelect;
    public GridLayoutManager mLayoutManager;
    public RecyclerView mRecyclerView;

    public PadStyleListItemView(Context context) {
        this(context, null);
    }

    public PadStyleListItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PadStyleListItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mLastSelect = 0;
    }

    @Override // com.miui.aod.components.widget.ITimeUpdate
    public void handleUpdateTime(boolean z) {
        int childCount = this.mRecyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            KeyEvent.Callback childAt = this.mRecyclerView.getChildAt(i);
            if (childAt instanceof ITimeUpdate) {
                ((ITimeUpdate) childAt).handleUpdateTime(z);
            }
        }
    }

    @Override // com.miui.aod.stylelist.PadStyleCategoryAdapter.OnAodItemClickListener
    public void onAodItemClick(int i) {
        View viewFindViewByPosition;
        int i2 = this.mLastSelect;
        if (i == i2) {
            return;
        }
        GridLayoutManager gridLayoutManager = this.mLayoutManager;
        if (gridLayoutManager != null && (viewFindViewByPosition = gridLayoutManager.findViewByPosition(i2)) != null) {
            viewFindViewByPosition.setBackground(null);
        }
        this.mLastSelect = i;
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mRecyclerView = (RecyclerView) findViewById(R.id.category_items);
        if (DeviceInfo.isLowMemDevice()) {
            this.mRecyclerView.getRecycledViewPool().setMaxRecycledViews(23, 0);
        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
        this.mLayoutManager = gridLayoutManager;
        gridLayoutManager.setOrientation(1);
        this.mRecyclerView.setLayoutManager(this.mLayoutManager);
    }

    public void setAdapter(androidx.recyclerview.widget.RecyclerView.Adapter<?> adapter) {
        StyleInfo styleInfo;
        PadStyleCategoryAdapter padStyleCategoryAdapter = (PadStyleCategoryAdapter) adapter;
        padStyleCategoryAdapter.setAodItemClickListener(this);
        List data = padStyleCategoryAdapter.getData();
        BigTimeCategoryInfo bigTimeCategoryInfo = (BigTimeCategoryInfo) new Gson().fromJson(Utils.getAodUsingCategoryParam(getContext(), CategoryConstants.CATEGORY_BIG_TIME), BigTimeCategoryInfo.class);
        if (bigTimeCategoryInfo.getLabel() == null) {
            bigTimeCategoryInfo = (BigTimeCategoryInfo) ((StyleCategoryAdapter.CategoryData) data.get(0)).mProductsBean;
            bigTimeCategoryInfo.switchOnNotificationIcon(true);
            bigTimeCategoryInfo.switchOnBatteryIcon(true);
            bigTimeCategoryInfo.switchOnDateAndTime(true);
            String json = new Gson().toJson(bigTimeCategoryInfo);
            Utils.setAodUsingCategoryName(getContext(), CategoryConstants.CATEGORY_BIG_TIME);
            Utils.setAodUsingCategory(getContext(), CategoryConstants.CATEGORY_BIG_TIME, json);
        }
        String label = bigTimeCategoryInfo.getLabel();
        padStyleCategoryAdapter.setSelect(label);
        for (int i = 0; i < data.size(); i++) {
            StyleCategoryAdapter.CategoryData categoryData = (StyleCategoryAdapter.CategoryData) data.get(i);
            if (categoryData != null && (styleInfo = categoryData.mProductsBean) != null && styleInfo.getLabel().equals(label)) {
                this.mLastSelect = i;
            }
        }
        this.mRecyclerView.setAdapter(adapter);
    }

    public void setAnimator(androidx.recyclerview.widget.RecyclerView.ItemAnimator itemAnimator) {
        this.mRecyclerView.setItemAnimator(itemAnimator);
    }
}
