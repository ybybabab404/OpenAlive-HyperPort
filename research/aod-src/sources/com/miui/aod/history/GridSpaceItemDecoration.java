package com.miui.aod.history;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
public class GridSpaceItemDecoration extends RecyclerView.ItemDecoration {
    public final boolean includeEdge;
    public final int spacing;
    public final int spanCount;

    public GridSpaceItemDecoration(int i, int i2, boolean z) {
        this.spanCount = i;
        this.spacing = i2;
        this.includeEdge = z;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
        int i = this.spanCount;
        int i2 = childAdapterPosition % i;
        if (this.includeEdge) {
            int i3 = this.spacing;
            rect.left = i3 - ((i2 * i3) / i);
            rect.right = ((i2 + 1) * i3) / i;
            if (childAdapterPosition < i) {
                rect.top = i3;
            }
            rect.bottom = i3;
        } else {
            int i4 = this.spacing;
            rect.left = (i2 * i4) / i;
            rect.right = i4 - (((i2 + 1) * i4) / i);
            if (childAdapterPosition >= i) {
                rect.top = i4;
            }
        }
        if (recyclerView.getAdapter() == null || getLineCount(childAdapterPosition + 1) != getLineCount(recyclerView.getAdapter().getItemCount())) {
            return;
        }
        rect.bottom = 210;
    }

    public final int getLineCount(int i) {
        return (int) Math.ceil((((double) i) * 1.0d) / ((double) this.spanCount));
    }
}
