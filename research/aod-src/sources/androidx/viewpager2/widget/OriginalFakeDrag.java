package androidx.viewpager2.widget;

import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes.dex */
final class OriginalFakeDrag {
    public final RecyclerView mRecyclerView;
    public final OriginalScrollEventAdapter mScrollEventAdapter;
    public final OriginalViewPager2 mViewPager;

    public OriginalFakeDrag(OriginalViewPager2 originalViewPager2, OriginalScrollEventAdapter originalScrollEventAdapter, RecyclerView recyclerView) {
        this.mViewPager = originalViewPager2;
        this.mScrollEventAdapter = originalScrollEventAdapter;
        this.mRecyclerView = recyclerView;
    }

    public boolean isFakeDragging() {
        return this.mScrollEventAdapter.isFakeDragging();
    }
}
