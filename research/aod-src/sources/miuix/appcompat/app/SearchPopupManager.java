package miuix.appcompat.app;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import miuix.appcompat.R;
import miuix.appcompat.internal.app.widget.ActionBarView;
import miuix.appcompat.internal.view.menu.action.EndActionMenuView;

/* JADX INFO: loaded from: classes2.dex */
public class SearchPopupManager {
    public final ActionBar mActionBar;
    public final View mAnchor;
    public int[] mInts;
    public boolean mIsDestroyed;
    public int mKeyboardHeight;
    public ViewTreeObserver.OnGlobalLayoutListener mKeyboardListener;
    public final int mOffsetY;
    public final SearchPopwindow mPopup;
    public final ViewGroup mPopupContainer;

    /* JADX INFO: renamed from: miuix.appcompat.app.SearchPopupManager$1, reason: invalid class name */
    class AnonymousClass1 implements ViewTreeObserver.OnGlobalLayoutListener {
        public int mLastHeight;
        public final /* synthetic */ SearchPopupManager this$0;

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            int height;
            if (this.this$0.mPopup.isShowing() && this.mLastHeight != (height = this.this$0.mPopupContainer.getHeight()) && height > 0) {
                this.mLastHeight = height;
                this.this$0.mPopupContainer.post(new Runnable() { // from class: miuix.appcompat.app.SearchPopupManager$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.this$0.updatePopupSizeDirectly();
                    }
                });
            }
        }
    }

    /* JADX INFO: renamed from: miuix.appcompat.app.SearchPopupManager$2, reason: invalid class name */
    class AnonymousClass2 implements ViewTreeObserver.OnGlobalLayoutListener {
        public int mLastKeyboardHeight;
        public final /* synthetic */ SearchPopupManager this$0;

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            int height;
            View rootView = this.this$0.mAnchor.getRootView();
            if (rootView == null || (height = rootView.getHeight()) == 0) {
                return;
            }
            Rect rect = new Rect();
            rootView.getWindowVisibleDisplayFrame(rect);
            int i = height - (rect.bottom - rect.top);
            if (this.mLastKeyboardHeight != i) {
                this.mLastKeyboardHeight = i;
                this.this$0.mKeyboardHeight = i;
                if (this.this$0.mPopup.isShowing()) {
                    this.this$0.mAnchor.post(new Runnable() { // from class: miuix.appcompat.app.SearchPopupManager$2$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.f$0.this$0.adjustPopupForKeyboard();
                        }
                    });
                }
            }
        }
    }

    /* JADX INFO: renamed from: miuix.appcompat.app.SearchPopupManager$3, reason: invalid class name */
    class AnonymousClass3 implements ActionBarView.OnSearchViewStyleChangeListener {
        public final /* synthetic */ SearchPopupManager this$0;

        @Override // miuix.appcompat.internal.app.widget.ActionBarView.OnSearchViewStyleChangeListener
        public void onStyleChange(boolean z) {
            if (z && this.this$0.isPopupShowing()) {
                this.this$0.dismissPopup();
            }
        }
    }

    /* JADX INFO: renamed from: miuix.appcompat.app.SearchPopupManager$4, reason: invalid class name */
    class AnonymousClass4 implements ViewTreeObserver.OnGlobalLayoutListener {
        public final /* synthetic */ SearchPopupManager this$0;

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            if (this.this$0.mIsDestroyed || !this.this$0.mPopup.isShowing()) {
                return;
            }
            this.this$0.mPopupContainer.post(new Runnable() { // from class: miuix.appcompat.app.SearchPopupManager$4$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    this.f$0.this$0.forceUpdatePopupHeight();
                }
            });
        }
    }

    public final void adjustPopupForKeyboard() {
        if (this.mIsDestroyed || !this.mPopup.isShowing() || this.mAnchor == null || this.mPopupContainer == null) {
            return;
        }
        int popupWidth = getPopupWidth();
        int[] iArr = new int[2];
        this.mAnchor.getLocationOnScreen(iArr);
        int height = iArr[1] + this.mAnchor.getHeight();
        int popupYOffset = getPopupYOffset();
        int height2 = this.mAnchor.getRootView().getHeight();
        int i = this.mKeyboardHeight;
        if (i > 0) {
            height2 -= i;
        }
        this.mPopupContainer.measure(View.MeasureSpec.makeMeasureSpec(popupWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(0, 0));
        int iMin = Math.min(this.mPopupContainer.getMeasuredHeight(), (height2 - height) - popupYOffset);
        if (iMin > 0) {
            this.mPopup.update(this.mAnchor, getPopupXOffset(), popupYOffset, popupWidth, iMin);
        }
    }

    public final int computePopupWidthForSearchActionMode() {
        View viewFindViewById;
        if (!(this.mAnchor.getParent() instanceof View)) {
            return this.mAnchor.getMeasuredWidth();
        }
        View view = (View) this.mAnchor.getParent();
        if (view == null || (viewFindViewById = view.findViewById(R.id.search_text_cancel)) == null || viewFindViewById.getWidth() <= 0) {
            return this.mAnchor.getMeasuredWidth();
        }
        if (this.mInts == null) {
            this.mInts = new int[2];
        }
        boolean z = this.mAnchor.getLayoutDirection() == 1;
        this.mAnchor.getLocationInWindow(this.mInts);
        int i = this.mInts[0];
        int width = this.mAnchor.getWidth() + i;
        viewFindViewById.getLocationInWindow(this.mInts);
        int i2 = this.mInts[0];
        return z ? (width - this.mAnchor.getPaddingRight()) - i2 : (viewFindViewById.getWidth() + i2) - (i + this.mAnchor.getPaddingLeft());
    }

    public final int computePopupWidthWithEndActionMenu() {
        if (this.mInts == null) {
            this.mInts = new int[2];
        }
        View actionBarView = this.mActionBar.getActionBarView();
        if (actionBarView instanceof ActionBarView) {
            View endActionMenuView = ((ActionBarView) actionBarView).getEndActionMenuView();
            if ((endActionMenuView instanceof EndActionMenuView) && this.mAnchor != null) {
                EndActionMenuView endActionMenuView2 = (EndActionMenuView) endActionMenuView;
                int childCount = endActionMenuView2.getChildCount();
                View childAt = endActionMenuView2.getChildAt(childCount - 1);
                if (childAt != null) {
                    this.mAnchor.getLocationInWindow(this.mInts);
                    int paddingLeft = this.mInts[0] + this.mAnchor.getPaddingLeft();
                    int measuredWidth = (this.mInts[0] + this.mAnchor.getMeasuredWidth()) - this.mAnchor.getPaddingRight();
                    childAt.getLocationInWindow(this.mInts);
                    int i = this.mInts[0];
                    int measuredWidth2 = childAt.getMeasuredWidth() + i;
                    int iMin = Math.min(paddingLeft, i);
                    int iMax = Math.max(measuredWidth, measuredWidth2);
                    Integer numValueOf = Integer.valueOf(childCount);
                    Integer numValueOf2 = Integer.valueOf(paddingLeft);
                    Integer numValueOf3 = Integer.valueOf(measuredWidth);
                    Integer numValueOf4 = Integer.valueOf(i);
                    Integer numValueOf5 = Integer.valueOf(measuredWidth2);
                    Integer numValueOf6 = Integer.valueOf(this.mAnchor.getMeasuredWidth());
                    Integer numValueOf7 = Integer.valueOf(childAt.getMeasuredWidth());
                    Integer numValueOf8 = Integer.valueOf(iMin);
                    Integer numValueOf9 = Integer.valueOf(iMax);
                    int i2 = iMax - iMin;
                    Log.d("SearchPopupManager", String.format("        itemCount : %d\n   searchViewLeft : %d\n  searchViewRight : %d\n     itemViewLeft : %d\n    itemViewRight : %d\nsearch view width : %d\n  item view width : %d\n       popup left : %d\n      popup right : %d\n      popup width : %d\n", numValueOf, numValueOf2, numValueOf3, numValueOf4, numValueOf5, numValueOf6, numValueOf7, numValueOf8, numValueOf9, Integer.valueOf(i2)));
                    return i2;
                }
            }
        }
        View view = this.mAnchor;
        if (view != null) {
            return view.getMeasuredWidth();
        }
        return 0;
    }

    public void destroy() {
        View view;
        if (this.mIsDestroyed) {
            return;
        }
        this.mIsDestroyed = true;
        ViewGroup viewGroup = this.mPopupContainer;
        if (viewGroup != null) {
            viewGroup.removeAllViews();
        }
        this.mPopup.dismiss();
        if (this.mKeyboardListener != null && (view = this.mAnchor) != null) {
            ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.removeOnGlobalLayoutListener(this.mKeyboardListener);
            }
            this.mKeyboardListener = null;
        }
        View view2 = this.mAnchor;
        if (view2 instanceof SearchView) {
            ((SearchView) view2).clearPopupManagerInternal();
        }
    }

    public void dismissPopup() {
        if (this.mIsDestroyed) {
            return;
        }
        this.mPopup.dismiss();
    }

    public final void forceUpdatePopupHeight() {
        if (this.mIsDestroyed || !this.mPopup.isShowing()) {
            return;
        }
        adjustPopupForKeyboard();
    }

    public int getPopupWidth() {
        if (this.mAnchor == null) {
            return 0;
        }
        return this.mActionBar != null ? computePopupWidthWithEndActionMenu() : computePopupWidthForSearchActionMode();
    }

    public final int getPopupXOffset() {
        return this.mAnchor.getLayoutDirection() == 1 ? -this.mAnchor.getPaddingRight() : this.mAnchor.getPaddingLeft();
    }

    public final int getPopupYOffset() {
        return this.mOffsetY - this.mAnchor.getPaddingBottom();
    }

    public boolean isPopupShowing() {
        return this.mPopup.isShowing();
    }

    public final void updatePopupSizeDirectly() {
        if (this.mIsDestroyed || !this.mPopup.isShowing()) {
            return;
        }
        adjustPopupForKeyboard();
    }
}
