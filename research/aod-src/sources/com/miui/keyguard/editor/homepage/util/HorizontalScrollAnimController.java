package com.miui.keyguard.editor.homepage.util;

import android.util.Log;
import android.view.View;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.homepage.view.adapter.BaseItemViewHolder;
import com.miui.keyguard.editor.homepage.view.viewpager.ScrollCallback;
import com.miui.keyguard.editor.homepage.view.viewpager.ScrollHelper;
import com.miui.keyguard.editor.view.viewpager2.ViewPager2;

/* JADX INFO: loaded from: classes2.dex */
public class HorizontalScrollAnimController {
    public boolean isOverlayScrolled;
    public int lastSelectedPosition;
    public OrientationHelper mHorizontalHelper;
    public RecyclerView.LayoutManager mLayoutManager;
    public ViewPager2.RecyclerViewImpl mRecyclerView;
    public ScrollHelper mScrollHelper;

    /* JADX INFO: renamed from: com.miui.keyguard.editor.homepage.util.HorizontalScrollAnimController$1, reason: invalid class name */
    class AnonymousClass1 implements ScrollCallback {
        public final /* synthetic */ HorizontalScrollAnimController this$0;

        @Override // com.miui.keyguard.editor.homepage.view.viewpager.ScrollCallback
        public void onFling(int i, int i2) {
            if (this.this$0.mRecyclerView == null) {
                Log.e("Keyguard-Editor-HorizontalScrollAnimController", "onFling failed: mRecyclerView = null");
                return;
            }
            if (this.this$0.mLayoutManager == null) {
                Log.e("Keyguard-Editor-HorizontalScrollAnimController", "onFling failed: mLayoutManager = null");
                return;
            }
            try {
                HorizontalScrollAnimController horizontalScrollAnimController = this.this$0;
                int iFindTargetSnapPosition = horizontalScrollAnimController.findTargetSnapPosition(horizontalScrollAnimController.mLayoutManager, i, i2);
                if (!this.this$0.isOverlayScrolled) {
                    Log.e("Keyguard-Editor-HorizontalScrollAnimController", "onFling skipped: is not overlay scroll.");
                    return;
                }
                if (!(this.this$0.mRecyclerView.findViewHolderForAdapterPosition(iFindTargetSnapPosition) instanceof BaseItemViewHolder)) {
                    Log.e("Keyguard-Editor-HorizontalScrollAnimController", "onFling failed: no ViewHolder found with targetSnapPosition: " + iFindTargetSnapPosition);
                }
                if (this.this$0.lastSelectedPosition < 0 || (this.this$0.mRecyclerView.findViewHolderForAdapterPosition(this.this$0.lastSelectedPosition) instanceof BaseItemViewHolder)) {
                    return;
                }
                Log.e("Keyguard-Editor-HorizontalScrollAnimController", "onFling failed: no ViewHolder found with lastSelectedPosition: " + this.this$0.lastSelectedPosition);
            } catch (Exception e) {
                Log.e("Keyguard-Editor-HorizontalScrollAnimController", "onFling failed", e);
            }
        }

        @Override // com.miui.keyguard.editor.homepage.view.viewpager.ScrollCallback
        public void onPageScrollStateChanged(int i) {
        }

        @Override // com.miui.keyguard.editor.homepage.view.viewpager.ScrollCallback
        public void onPageScrolled(int i, int i2, int i3) {
        }

        @Override // com.miui.keyguard.editor.homepage.view.viewpager.ScrollCallback
        public void onPageSelectChanged(int i) {
            this.this$0.lastSelectedPosition = i;
        }
    }

    public final int findCenterViewIndex(RecyclerView.LayoutManager layoutManager, OrientationHelper orientationHelper) {
        int childCount = layoutManager.getChildCount();
        if (childCount == 0) {
            return -1;
        }
        int startAfterPadding = orientationHelper.getStartAfterPadding() + (orientationHelper.getTotalSpace() / 2);
        View view = null;
        int i = Integer.MAX_VALUE;
        for (int i2 = 0; i2 < childCount; i2++) {
            View childAt = layoutManager.getChildAt(i2);
            int iAbs = Math.abs((orientationHelper.getDecoratedStart(childAt) + (orientationHelper.getDecoratedMeasurement(childAt) / 2)) - startAfterPadding);
            if (iAbs < i) {
                view = childAt;
                i = iAbs;
            }
        }
        if (view != null) {
            return layoutManager.getPosition(view);
        }
        return -1;
    }

    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int i, int i2) {
        OrientationHelper horizontalHelper;
        int iFindCenterViewIndex;
        if (layoutManager == null || this.mScrollHelper == null || layoutManager.getItemCount() <= 0 || (horizontalHelper = getHorizontalHelper(layoutManager)) == null || (iFindCenterViewIndex = findCenterViewIndex(layoutManager, horizontalHelper)) == -1) {
            return -1;
        }
        int iPredictSnapTargetPosition = this.mScrollHelper.predictSnapTargetPosition(iFindCenterViewIndex, layoutManager);
        this.isOverlayScrolled = iFindCenterViewIndex != iPredictSnapTargetPosition;
        Log.i("Keyguard-Editor-HorizontalScrollAnimController", "pagerSnap -> closestIndex: " + iFindCenterViewIndex + ", findTargetSnapPosition: " + iPredictSnapTargetPosition);
        return iPredictSnapTargetPosition;
    }

    public final OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager layoutManager) {
        OrientationHelper orientationHelper = this.mHorizontalHelper;
        if (orientationHelper == null || orientationHelper.getLayoutManager() != layoutManager) {
            this.mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return this.mHorizontalHelper;
    }
}
