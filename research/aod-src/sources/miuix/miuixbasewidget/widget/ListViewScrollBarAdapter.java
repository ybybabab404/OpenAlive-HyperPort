package miuix.miuixbasewidget.widget;

import android.util.SparseIntArray;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

/* JADX INFO: loaded from: classes2.dex */
public class ListViewScrollBarAdapter implements ScrollableView {
    public HeightCache heightCache;
    public int lastScrollY;
    public ListView listView;
    public ScrollableView.OnActionDownListener onActionDownListener;
    public ScrollableView.OnActionUpListener onActionUpListener;
    public ScrollPositionCalculator positionCalculator;
    public ScrollableView.OnScrollListener scrollListener;

    public class HeightCache {
        public int cachedTotalHeight;
        public SparseIntArray itemHeightCache;
        public int lastAdapterCount;
        public final /* synthetic */ ListViewScrollBarAdapter this$0;

        public final int calculateTotalHeight(ListAdapter listAdapter) {
            int count = listAdapter.getCount();
            int itemHeight = 0;
            for (int i = 0; i < count; i++) {
                itemHeight += getItemHeight(i, listAdapter);
            }
            return itemHeight;
        }

        public final int getEstimatedHeight() {
            int i = 0;
            if (this.itemHeightCache.size() > 0) {
                int iValueAt = 0;
                while (i < this.itemHeightCache.size()) {
                    iValueAt += this.itemHeightCache.valueAt(i);
                    i++;
                }
                return iValueAt / this.itemHeightCache.size();
            }
            if (this.this$0.listView.getChildCount() <= 0) {
                return 100;
            }
            int i2 = 0;
            int height = 0;
            while (i < this.this$0.listView.getChildCount()) {
                View childAt = this.this$0.listView.getChildAt(i);
                if (childAt != null && childAt.getHeight() > 0) {
                    height += childAt.getHeight();
                    i2++;
                }
                i++;
            }
            if (i2 > 0) {
                return height / i2;
            }
            return 100;
        }

        public final int getHeightFromVisibleView(int i) {
            View childAt;
            int firstVisiblePosition = this.this$0.listView.getFirstVisiblePosition();
            int childCount = (this.this$0.listView.getChildCount() + firstVisiblePosition) - 1;
            if (i < firstVisiblePosition || i > childCount || (childAt = this.this$0.listView.getChildAt(i - firstVisiblePosition)) == null) {
                return 0;
            }
            int height = childAt.getHeight();
            if (height > 0) {
                return height;
            }
            int measuredHeight = childAt.getMeasuredHeight();
            if (measuredHeight > 0) {
                return measuredHeight;
            }
            return 0;
        }

        public int getItemHeight(int i, ListAdapter listAdapter) {
            int i2 = this.itemHeightCache.get(i, -1);
            if (i2 != -1) {
                return i2;
            }
            int heightFromVisibleView = getHeightFromVisibleView(i);
            if (heightFromVisibleView > 0) {
                this.itemHeightCache.put(i, heightFromVisibleView);
                return heightFromVisibleView;
            }
            int iMeasureItemHeight = measureItemHeight(i, listAdapter);
            if (iMeasureItemHeight <= 0) {
                return getEstimatedHeight();
            }
            this.itemHeightCache.put(i, iMeasureItemHeight);
            return iMeasureItemHeight;
        }

        public int getTotalHeight(ListAdapter listAdapter) {
            int count = listAdapter.getCount();
            if (this.cachedTotalHeight == -1 || this.lastAdapterCount != count) {
                this.cachedTotalHeight = calculateTotalHeight(listAdapter);
                this.lastAdapterCount = count;
            }
            return this.cachedTotalHeight;
        }

        public final int measureItemHeight(int i, ListAdapter listAdapter) {
            if (listAdapter != null && i >= 0 && i < listAdapter.getCount()) {
                try {
                    View view = listAdapter.getView(i, null, this.this$0.listView);
                    if (view != null) {
                        view.measure(View.MeasureSpec.makeMeasureSpec(this.this$0.listView.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(0, 0));
                        return view.getMeasuredHeight();
                    }
                } catch (Exception unused) {
                }
            }
            return 0;
        }
    }

    public class ListViewScrollListener implements AbsListView.OnScrollListener {
        public final /* synthetic */ ListViewScrollBarAdapter this$0;

        public final void notifyScrollListener() {
            if (this.this$0.scrollListener != null) {
                this.this$0.scrollListener.onScroll(this.this$0.getScrollY(), this.this$0.getScrollRange());
            }
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
            int scrollY = this.this$0.getScrollY();
            if (this.this$0.lastScrollY == -1 || Math.abs(scrollY - this.this$0.lastScrollY) > 0) {
                notifyScrollListener();
                this.this$0.lastScrollY = scrollY;
            }
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView absListView, int i) {
            if (i == 1 || i == 2) {
                notifyScrollListener();
            }
        }
    }

    public class ScrollPositionCalculator {
        public final /* synthetic */ ListViewScrollBarAdapter this$0;

        public int calculateScrollY(int i, View view) {
            ListAdapter adapter;
            if (view == null || (adapter = this.this$0.listView.getAdapter()) == null) {
                return 0;
            }
            int itemHeight = 0;
            for (int i2 = 0; i2 < i; i2++) {
                itemHeight += this.this$0.heightCache.getItemHeight(i2, adapter);
            }
            return Math.max(0, itemHeight - view.getTop());
        }

        public final int findPositionByScrollY(int i, ListAdapter listAdapter) {
            int itemHeight = 0;
            for (int i2 = 0; i2 < listAdapter.getCount(); i2++) {
                itemHeight += this.this$0.heightCache.getItemHeight(i2, listAdapter);
                if (itemHeight > i) {
                    return i2;
                }
            }
            return listAdapter.getCount() - 1;
        }

        public final int getScrollYForPosition(int i, ListAdapter listAdapter) {
            int itemHeight = 0;
            for (int i2 = 0; i2 < i; i2++) {
                itemHeight += this.this$0.heightCache.getItemHeight(i2, listAdapter);
            }
            return itemHeight;
        }

        public void scrollToPosition(int i) {
            int iFindPositionByScrollY;
            ListAdapter adapter = this.this$0.listView.getAdapter();
            if (this.this$0.listView != null && adapter != null && (iFindPositionByScrollY = findPositionByScrollY(i, adapter)) >= 0 && iFindPositionByScrollY < adapter.getCount()) {
                int scrollYForPosition = i - getScrollYForPosition(iFindPositionByScrollY, adapter);
                int height = this.this$0.listView.getHeight();
                int i2 = -this.this$0.heightCache.getItemHeight(iFindPositionByScrollY, adapter);
                if (scrollYForPosition < i2) {
                    scrollYForPosition = i2;
                }
                if (scrollYForPosition <= height) {
                    height = scrollYForPosition;
                }
                this.this$0.listView.setSelectionFromTop(iFindPositionByScrollY, -height);
            }
        }
    }

    @Override // miuix.miuixbasewidget.widget.ScrollableView
    public void actionDownEventCallBack() {
        ScrollableView.OnActionDownListener onActionDownListener = this.onActionDownListener;
        if (onActionDownListener != null) {
            onActionDownListener.onActionDown();
        }
    }

    @Override // miuix.miuixbasewidget.widget.ScrollableView
    public void actionUpEventCallBack() {
        ScrollableView.OnActionUpListener onActionUpListener = this.onActionUpListener;
        if (onActionUpListener != null) {
            onActionUpListener.onActionUp();
        }
    }

    @Override // miuix.miuixbasewidget.widget.ScrollableView
    public int getScrollRange() {
        ListAdapter adapter = this.listView.getAdapter();
        if (adapter == null || adapter.getCount() == 0) {
            return 0;
        }
        return Math.max(0, this.heightCache.getTotalHeight(adapter) - this.listView.getHeight());
    }

    @Override // miuix.miuixbasewidget.widget.ScrollableView
    public int getScrollY() {
        if (this.listView.getChildCount() == 0) {
            return 0;
        }
        int firstVisiblePosition = this.listView.getFirstVisiblePosition();
        View childAt = this.listView.getChildAt(0);
        if (childAt == null) {
            return 0;
        }
        return this.positionCalculator.calculateScrollY(firstVisiblePosition, childAt);
    }

    @Override // miuix.miuixbasewidget.widget.ScrollableView
    public void scrollTo(int i) {
        if (this.listView != null && Math.abs(i - getScrollY()) >= 5) {
            this.positionCalculator.scrollToPosition(i);
        }
    }

    @Override // miuix.miuixbasewidget.widget.ScrollableView
    public void setOnScrollListener(ScrollableView.OnScrollListener onScrollListener) {
        this.scrollListener = onScrollListener;
    }
}
