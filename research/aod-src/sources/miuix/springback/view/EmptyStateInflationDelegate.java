package miuix.springback.view;

import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import android.widget.FrameLayout;
import miuix.core.util.EnvStateManager;
import miuix.core.widget.NestedScrollView;

/* JADX INFO: loaded from: classes.dex */
public class EmptyStateInflationDelegate {
    public NestedScrollView mContainer;
    public FrameLayout.LayoutParams mContainerParams;
    public OnDeflateListener mDeflateListener;
    public int mEmptyStateRes;
    public View mEmptyStateView;
    public OnInflateListener mInflateListener;
    public View.OnLayoutChangeListener mOnLayoutChangeListener;
    public SpringBackLayout mSpringBack;
    public View mSpringBackParent;
    public Point mWindowSize = new Point();
    public Rect mGlobalVisibleRect = new Rect();
    public boolean mAttachToRoot = false;
    public boolean mInflated = false;
    public boolean mOverlay = false;
    public int mTopInset = 0;
    public int mBottomInset = 0;

    /* JADX INFO: renamed from: miuix.springback.view.EmptyStateInflationDelegate$1, reason: invalid class name */
    class AnonymousClass1 implements View.OnLayoutChangeListener {
        public final /* synthetic */ EmptyStateInflationDelegate this$0;

        @Override // android.view.View.OnLayoutChangeListener
        public void onLayoutChange(View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            if (this.this$0.mEmptyStateView == null || !this.this$0.isInflated()) {
                return;
            }
            if (this.this$0.mOverlay) {
                this.this$0.layoutEmptyStateOverlay();
            } else {
                this.this$0.layoutEmptyState();
            }
        }
    }

    public interface OnDeflateListener {
    }

    public interface OnInflateListener {
    }

    /* JADX INFO: renamed from: $r8$lambda$mB794Zib9XlpdwE-jdG_DEonxhQ, reason: not valid java name */
    public static /* synthetic */ void m1354$r8$lambda$mB794Zib9XlpdwEjdG_DEonxhQ(EmptyStateInflationDelegate emptyStateInflationDelegate) {
        emptyStateInflationDelegate.mEmptyStateView.setVisibility(0);
        int measuredWidth = emptyStateInflationDelegate.mContainer.getMeasuredWidth();
        int iMeasureContainerHeightInWindowOverlay = emptyStateInflationDelegate.measureContainerHeightInWindowOverlay();
        int measuredWidth2 = emptyStateInflationDelegate.mEmptyStateView.getMeasuredWidth();
        int measuredHeight = emptyStateInflationDelegate.mEmptyStateView.getMeasuredHeight();
        int i = ((int) ((iMeasureContainerHeightInWindowOverlay - measuredHeight) * 0.4f)) + emptyStateInflationDelegate.mTopInset;
        int i2 = (measuredWidth - measuredWidth2) >> 1;
        emptyStateInflationDelegate.mEmptyStateView.layout(i2, i, measuredWidth2 + i2, measuredHeight + i);
    }

    public EmptyStateInflationDelegate(SpringBackLayout springBackLayout, int i) {
        Context context = springBackLayout.getContext();
        this.mEmptyStateRes = i;
        this.mContainer = new NestedScrollView(context);
        this.mContainerParams = new FrameLayout.LayoutParams(-1, -1);
        this.mSpringBack = springBackLayout;
    }

    public View getEmptyState() {
        return this.mEmptyStateView;
    }

    public boolean isInflated() {
        return this.mInflated;
    }

    public final void layoutEmptyState() {
        int measuredWidth = this.mContainer.getMeasuredWidth();
        int iMeasureContainerHeightInWindow = measureContainerHeightInWindow(this.mContainer.getMeasuredHeight());
        int measuredWidth2 = this.mEmptyStateView.getMeasuredWidth();
        int measuredHeight = this.mEmptyStateView.getMeasuredHeight();
        int i = (int) ((iMeasureContainerHeightInWindow - measuredHeight) * 0.4f);
        int i2 = (measuredWidth - measuredWidth2) >> 1;
        this.mEmptyStateView.layout(i2, i, measuredWidth2 + i2, measuredHeight + i);
    }

    public final void layoutEmptyStateOverlay() {
        if (this.mEmptyStateView.getTop() == 0) {
            this.mEmptyStateView.setVisibility(4);
        }
        this.mSpringBack.post(new Runnable() { // from class: miuix.springback.view.EmptyStateInflationDelegate$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                EmptyStateInflationDelegate.m1354$r8$lambda$mB794Zib9XlpdwEjdG_DEonxhQ(this.f$0);
            }
        });
    }

    public final int measureContainerHeightInWindow(int i) {
        this.mContainer.getGlobalVisibleRect(this.mGlobalVisibleRect);
        int i2 = this.mGlobalVisibleRect.top;
        Point windowSize = EnvStateManager.getWindowSize(this.mContainer.getContext());
        this.mWindowSize = windowSize;
        int i3 = windowSize.y;
        int i4 = i2 + i;
        int i5 = this.mBottomInset;
        return i4 <= i3 - i5 ? i : (i3 - i2) - i5;
    }

    public final int measureContainerHeightInWindowOverlay() {
        int i;
        int i2;
        this.mContainer.getGlobalVisibleRect(this.mGlobalVisibleRect);
        int i3 = this.mGlobalVisibleRect.top;
        Point windowSize = EnvStateManager.getWindowSize(this.mContainer.getContext());
        this.mWindowSize = windowSize;
        int i4 = windowSize.y;
        if (i3 > 0) {
            this.mTopInset = 0;
            i = i4 - i3;
            i2 = this.mBottomInset;
        } else {
            i = i4 - this.mTopInset;
            i2 = this.mBottomInset;
        }
        return i - i2;
    }

    public void onDetachedFromWindow() {
        View.OnLayoutChangeListener onLayoutChangeListener;
        View view = this.mSpringBackParent;
        if (view == null || (onLayoutChangeListener = this.mOnLayoutChangeListener) == null) {
            return;
        }
        view.removeOnLayoutChangeListener(onLayoutChangeListener);
        this.mOnLayoutChangeListener = null;
    }

    public void setBottomInset(int i) {
        this.mBottomInset = i;
    }

    public void setOnDeflateListener(OnDeflateListener onDeflateListener) {
        this.mDeflateListener = onDeflateListener;
    }

    public void setOnInflateListener(OnInflateListener onInflateListener) {
        this.mInflateListener = onInflateListener;
    }

    public void setOverlay(boolean z) {
        this.mOverlay = z;
    }

    public void setTopInset(int i) {
        this.mTopInset = i;
    }
}
