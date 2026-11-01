package com.miui.keyguard.editor.homepage.view.viewpager;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.Interpolator;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.miui.keyguard.editor.view.viewpager2.ViewPager2;

/* JADX INFO: loaded from: classes2.dex */
public class DampPagerSnapHelperImpl extends ViewPager2.PagerSnapHelperImpl {
    public Interpolator mFlingInterceptor;
    public OrientationHelper mHorizontalHelper;
    public RecyclerView mRecyclerViewImpl;
    public ScrollHelper mScrollHelper;
    public OrientationHelper mVerticalHelper;

    public DampPagerSnapHelperImpl(ViewPager2 viewPager2) {
        super(viewPager2);
        this.mScrollHelper = new ScrollHelper();
    }

    private OrientationHelper getHorizontalHelper(RecyclerView.LayoutManager layoutManager) {
        OrientationHelper orientationHelper = this.mHorizontalHelper;
        if (orientationHelper == null || orientationHelper.getLayoutManager() != layoutManager) {
            this.mHorizontalHelper = OrientationHelper.createHorizontalHelper(layoutManager);
        }
        return this.mHorizontalHelper;
    }

    private OrientationHelper getOrientationHelper(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager.canScrollVertically()) {
            return getVerticalHelper(layoutManager);
        }
        if (layoutManager.canScrollHorizontally()) {
            return getHorizontalHelper(layoutManager);
        }
        return null;
    }

    private OrientationHelper getVerticalHelper(RecyclerView.LayoutManager layoutManager) {
        OrientationHelper orientationHelper = this.mVerticalHelper;
        if (orientationHelper == null || orientationHelper.getLayoutManager() != layoutManager) {
            this.mVerticalHelper = OrientationHelper.createVerticalHelper(layoutManager);
        }
        return this.mVerticalHelper;
    }

    @Override // com.miui.keyguard.editor.view.viewpager2.SnapHelper
    public void attachToRecyclerView(RecyclerView recyclerView) {
        super.attachToRecyclerView(recyclerView);
        this.mRecyclerViewImpl = recyclerView;
        this.mScrollHelper.attachToViewPager2(getViewPager());
    }

    @Override // com.miui.keyguard.editor.view.viewpager2.PagerSnapHelper, com.miui.keyguard.editor.view.viewpager2.SnapHelper
    public RecyclerView.SmoothScroller createScroller(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof RecyclerView.SmoothScroller.ScrollVectorProvider) {
            return new LinearSmoothScroller(this.mRecyclerViewImpl.getContext()) { // from class: com.miui.keyguard.editor.homepage.view.viewpager.DampPagerSnapHelperImpl.1
                @Override // androidx.recyclerview.widget.LinearSmoothScroller
                public float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    return 100.0f / displayMetrics.densityDpi;
                }

                @Override // androidx.recyclerview.widget.LinearSmoothScroller
                public int calculateTimeForScrolling(int i) {
                    return Math.min(100, super.calculateTimeForScrolling(i));
                }

                public final Interpolator obtainInterceptor() {
                    if (DampPagerSnapHelperImpl.this.mFlingInterceptor == null) {
                        DampPagerSnapHelperImpl.this.mFlingInterceptor = new DampInterceptor(0.95f, 0.78f);
                    }
                    return DampPagerSnapHelperImpl.this.mFlingInterceptor;
                }

                @Override // androidx.recyclerview.widget.LinearSmoothScroller, androidx.recyclerview.widget.RecyclerView.SmoothScroller
                public void onTargetFound(View view, RecyclerView.State state, RecyclerView.SmoothScroller.Action action) {
                    DampPagerSnapHelperImpl dampPagerSnapHelperImpl = DampPagerSnapHelperImpl.this;
                    int[] iArrCalculateDistanceToFinalSnap = dampPagerSnapHelperImpl.calculateDistanceToFinalSnap(dampPagerSnapHelperImpl.mRecyclerViewImpl.getLayoutManager(), view);
                    action.update(iArrCalculateDistanceToFinalSnap[0], iArrCalculateDistanceToFinalSnap[1], 450, obtainInterceptor());
                }
            };
        }
        return null;
    }

    @Override // com.miui.keyguard.editor.view.viewpager2.PagerSnapHelper, com.miui.keyguard.editor.view.viewpager2.SnapHelper
    public int findTargetSnapPosition(RecyclerView.LayoutManager layoutManager, int i, int i2) {
        if (layoutManager == null || this.mScrollHelper == null || layoutManager.getItemCount() <= 0 || getOrientationHelper(layoutManager) == null) {
            return -1;
        }
        int iPredictSnapTargetPosition = this.mScrollHelper.predictSnapTargetPosition(layoutManager);
        Log.i("Keyguard-Editor-DampPagerSnapHelper", "findTargetSnapPosition -> snapTargetPosition = " + iPredictSnapTargetPosition);
        return iPredictSnapTargetPosition;
    }
}
