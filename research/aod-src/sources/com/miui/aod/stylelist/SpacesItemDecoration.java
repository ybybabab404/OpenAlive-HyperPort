package com.miui.aod.stylelist;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.aod.R;
import com.miui.aod.Utils;

/* JADX INFO: loaded from: classes.dex */
class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    public final int first_item_space;
    public final int item_space;

    public SpacesItemDecoration(Context context) {
        this.item_space = context.getResources().getDimensionPixelSize(R.dimen.list_margin);
        this.first_item_space = context.getResources().getDimensionPixelSize(R.dimen.content_list_horizontal_margin);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        rect.left = this.item_space;
        if (recyclerView.getChildLayoutPosition(view) == 0) {
            if (Utils.isLayoutRtl()) {
                rect.right = this.first_item_space;
                return;
            } else {
                rect.left = this.first_item_space;
                return;
            }
        }
        if (recyclerView.getChildLayoutPosition(view) == state.getItemCount() - 1) {
            if (Utils.isLayoutRtl()) {
                rect.left = this.first_item_space;
            } else {
                rect.right = this.first_item_space;
            }
        }
    }
}
