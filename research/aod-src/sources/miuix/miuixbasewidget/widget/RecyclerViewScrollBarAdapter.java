package miuix.miuixbasewidget.widget;

import androidx.recyclerview.widget.RecyclerView;

/* JADX INFO: loaded from: classes2.dex */
public class RecyclerViewScrollBarAdapter implements ScrollableView {
    public ScrollableView.OnActionDownListener onActionDownListener;
    public ScrollableView.OnActionUpListener onActionUpListener;
    public RecyclerView.OnScrollListener onScrollListener;
    public RecyclerView recyclerView;
    public ScrollableView.OnScrollListener scrollListener;

    public RecyclerViewScrollBarAdapter(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        initScrollListener();
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
        return this.recyclerView.computeVerticalScrollRange() - this.recyclerView.computeVerticalScrollExtent();
    }

    @Override // miuix.miuixbasewidget.widget.ScrollableView
    public int getScrollY() {
        return this.recyclerView.computeVerticalScrollOffset();
    }

    public final void initScrollListener() {
        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() { // from class: miuix.miuixbasewidget.widget.RecyclerViewScrollBarAdapter.1
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                if ((i == 1 || i == 2) && RecyclerViewScrollBarAdapter.this.scrollListener != null) {
                    RecyclerViewScrollBarAdapter.this.scrollListener.onScroll(RecyclerViewScrollBarAdapter.this.getScrollY(), RecyclerViewScrollBarAdapter.this.getScrollRange());
                }
            }

            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrolled(RecyclerView recyclerView, int i, int i2) {
                if (RecyclerViewScrollBarAdapter.this.scrollListener != null) {
                    RecyclerViewScrollBarAdapter.this.scrollListener.onScroll(RecyclerViewScrollBarAdapter.this.getScrollY(), RecyclerViewScrollBarAdapter.this.getScrollRange());
                }
            }
        };
        this.onScrollListener = onScrollListener;
        this.recyclerView.addOnScrollListener(onScrollListener);
    }

    @Override // miuix.miuixbasewidget.widget.ScrollableView
    public void scrollTo(int i) {
        this.recyclerView.scrollBy(0, i - getScrollY());
    }

    @Override // miuix.miuixbasewidget.widget.ScrollableView
    public void setOnScrollListener(ScrollableView.OnScrollListener onScrollListener) {
        this.scrollListener = onScrollListener;
    }
}
