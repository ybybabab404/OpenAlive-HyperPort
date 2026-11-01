package com.miui.aod.components.widget;

import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.aod.Utils;

/* JADX INFO: loaded from: classes.dex */
public class MarginItemDecoration extends RecyclerView.ItemDecoration {
    private final int mItemMargin;
    private final int mItemMarginStart;

    public MarginItemDecoration(int i, int i2) {
        this.mItemMarginStart = i;
        this.mItemMargin = i2;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(@NonNull Rect rect, @NonNull View view, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.State state) {
        rect.left = this.mItemMargin;
        int i = this.mItemMarginStart;
        if (recyclerView.getChildLayoutPosition(view) == 0) {
            if (Utils.isLayoutRtl()) {
                rect.right = i;
                return;
            } else {
                rect.left = i;
                return;
            }
        }
        if (recyclerView.getAdapter() == null || recyclerView.getChildLayoutPosition(view) != recyclerView.getAdapter().getItemCount() - 1) {
            return;
        }
        if (Utils.isLayoutRtl()) {
            rect.left = i;
        } else {
            rect.right = i;
        }
    }
}
