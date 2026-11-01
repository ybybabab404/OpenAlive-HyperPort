package miuix.miuixbasewidget.widget;

import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;
import java.lang.reflect.Field;

/* JADX INFO: loaded from: classes2.dex */
public class ScrollViewScrollBarAdapter implements ScrollableView {
    public int lastScrollY;
    public ScrollableView.OnActionDownListener onActionDownListener;
    public ScrollableView.OnActionUpListener onActionUpListener;
    public ScrollableView.OnScrollListener scrollListener;
    public ScrollView scrollView;

    /* JADX INFO: renamed from: miuix.miuixbasewidget.widget.ScrollViewScrollBarAdapter$1, reason: invalid class name */
    class AnonymousClass1 implements ViewTreeObserver.OnScrollChangedListener {
        public final /* synthetic */ ScrollViewScrollBarAdapter this$0;

        @Override // android.view.ViewTreeObserver.OnScrollChangedListener
        public void onScrollChanged() {
            if (this.this$0.scrollListener != null) {
                int scrollY = this.this$0.getScrollY();
                if (this.this$0.lastScrollY == -1 || Math.abs(scrollY - this.this$0.lastScrollY) > 0) {
                    this.this$0.scrollListener.onScroll(scrollY, this.this$0.getScrollRange());
                    this.this$0.lastScrollY = scrollY;
                }
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
        View childAt = this.scrollView.getChildAt(0);
        if (childAt != null) {
            return Math.max(0, childAt.getHeight() - this.scrollView.getHeight());
        }
        return 0;
    }

    @Override // miuix.miuixbasewidget.widget.ScrollableView
    public int getScrollY() {
        return this.scrollView.getScrollY();
    }

    @Override // miuix.miuixbasewidget.widget.ScrollableView
    public void scrollTo(int i) {
        stopFling();
        this.scrollView.scrollTo(0, i);
    }

    @Override // miuix.miuixbasewidget.widget.ScrollableView
    public void setOnScrollListener(ScrollableView.OnScrollListener onScrollListener) {
        this.scrollListener = onScrollListener;
    }

    public final void stopFling() {
        try {
            Field declaredField = ScrollView.class.getDeclaredField("mScroller");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(this.scrollView);
            if (obj != null) {
                obj.getClass().getMethod("abortAnimation", new Class[0]).invoke(obj, new Object[0]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
