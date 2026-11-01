package com.miui.aod.stylelist;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.components.widget.ITimeUpdate;
import com.miui.aod.util.DeviceInfo;
import miuix.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class StyleListItemView extends LinearLayout implements ITimeUpdate {
    public ImageView mArrow;
    public TextView mCount;
    public RecyclerView mRecyclerView;
    public TextView mTitle;

    public StyleListItemView(Context context) {
        this(context, null);
    }

    public StyleListItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public StyleListItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public androidx.recyclerview.widget.RecyclerView.Adapter<?> getAdapter() {
        return this.mRecyclerView.getAdapter();
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

    public void notifyUpdateItemDecoration() {
        this.mRecyclerView.invalidateItemDecorations();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        TextView textView = (TextView) findViewById(R.id.title);
        this.mTitle = textView;
        textView.setGravity(Utils.isLayoutRtl() ? 5 : 3);
        this.mCount = (TextView) findViewById(R.id.value_right);
        this.mArrow = (ImageView) findViewById(R.id.arrow_right);
        this.mRecyclerView = (RecyclerView) findViewById(R.id.category_items);
        if (DeviceInfo.isLowMemDevice()) {
            this.mRecyclerView.getRecycledViewPool().setMaxRecycledViews(23, 0);
        }
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(0);
        this.mRecyclerView.setLayoutManager(linearLayoutManager);
        this.mRecyclerView.addOnScrollListener(new androidx.recyclerview.widget.RecyclerView.OnScrollListener() { // from class: com.miui.aod.stylelist.StyleListItemView.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(androidx.recyclerview.widget.RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
                if (i == 0) {
                    int iFindLastCompletelyVisibleItemPosition = linearLayoutManager.findLastCompletelyVisibleItemPosition();
                    for (int iFindFirstCompletelyVisibleItemPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition(); iFindFirstCompletelyVisibleItemPosition <= iFindLastCompletelyVisibleItemPosition; iFindFirstCompletelyVisibleItemPosition++) {
                        androidx.recyclerview.widget.RecyclerView.ViewHolder viewHolderFindViewHolderForAdapterPosition = recyclerView.findViewHolderForAdapterPosition(iFindFirstCompletelyVisibleItemPosition);
                        if (viewHolderFindViewHolderForAdapterPosition != null) {
                            KeyEvent.Callback callback = viewHolderFindViewHolderForAdapterPosition.itemView;
                            if (callback instanceof ITimeUpdate) {
                                ((ITimeUpdate) callback).handleUpdateTime(DateFormat.is24HourFormat(StyleListItemView.this.getContext().getApplicationContext()));
                            }
                        }
                    }
                }
            }
        });
        this.mRecyclerView.addItemDecoration(new SpacesItemDecoration(getContext()));
    }

    public void recycle() {
        if (((View) this.mArrow.getParent()).hasOnClickListeners()) {
            ((View) this.mArrow.getParent()).setOnClickListener(null);
        }
        this.mRecyclerView.setAdapter(null);
    }

    public void setAdapter(androidx.recyclerview.widget.RecyclerView.Adapter<?> adapter) {
        this.mRecyclerView.setAdapter(adapter);
    }

    public void setAnimator(androidx.recyclerview.widget.RecyclerView.ItemAnimator itemAnimator) {
        this.mRecyclerView.setItemAnimator(itemAnimator);
    }

    public void setArrowClickListener(View.OnClickListener onClickListener) {
        this.mArrow.setVisibility(onClickListener == null ? 8 : 0);
        if (onClickListener == null) {
            ((View) this.mArrow.getParent()).setClickable(false);
        } else {
            ((View) this.mArrow.getParent()).setClickable(true);
            ((View) this.mArrow.getParent()).setOnClickListener(onClickListener);
        }
    }

    public void setRightValue(String str) {
        this.mCount.setText(str);
    }

    public void setTitle(String str) {
        this.mTitle.setText(str);
    }
}
