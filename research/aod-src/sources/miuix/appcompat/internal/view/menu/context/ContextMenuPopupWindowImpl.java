package miuix.appcompat.internal.view.menu.context;

import android.R;
import android.graphics.Rect;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import miuix.appcompat.internal.view.menu.MenuBuilder;
import miuix.internal.util.TaggingDrawableUtil;
import miuix.internal.util.ViewUtils;
import miuix.internal.widget.ListPopup;

/* JADX INFO: loaded from: classes2.dex */
@Deprecated
public class ContextMenuPopupWindowImpl extends ListPopup implements ContextMenuPopupWindow {
    public ContextMenuAdapter mAdapter;
    public View mLastAnchor;
    public int mMarginScreen;
    public int mMaxWidth;
    public MenuBuilder mMenu;
    public View mSeparateItemMenuView;
    public MenuItem mSeparateMenuItem;
    public View mSeparateMenuView;
    public float mX;
    public float mY;

    /* JADX INFO: renamed from: miuix.appcompat.internal.view.menu.context.ContextMenuPopupWindowImpl$1, reason: invalid class name */
    class AnonymousClass1 implements AdapterView.OnItemClickListener {
        public final /* synthetic */ ContextMenuPopupWindowImpl this$0;

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView adapterView, View view, int i, long j) {
            MenuItem item = this.this$0.mAdapter.getItem(i);
            this.this$0.mMenu.performItemAction(item, 0);
            if (item.hasSubMenu()) {
                final SubMenu subMenu = item.getSubMenu();
                this.this$0.setOnDismissListener(new PopupWindow.OnDismissListener() { // from class: miuix.appcompat.internal.view.menu.context.ContextMenuPopupWindowImpl.1.1
                    @Override // android.widget.PopupWindow.OnDismissListener
                    public void onDismiss() {
                        AnonymousClass1.this.this$0.setOnDismissListener(null);
                        AnonymousClass1.this.this$0.update(subMenu);
                        ContextMenuPopupWindowImpl contextMenuPopupWindowImpl = AnonymousClass1.this.this$0;
                        contextMenuPopupWindowImpl.fastShowAsContextMenu(contextMenuPopupWindowImpl.mLastAnchor, AnonymousClass1.this.this$0.mX, AnonymousClass1.this.this$0.mY);
                    }
                });
            }
            this.this$0.dismiss();
        }
    }

    /* JADX INFO: renamed from: miuix.appcompat.internal.view.menu.context.ContextMenuPopupWindowImpl$2, reason: invalid class name */
    class AnonymousClass2 implements View.OnClickListener {
        public final /* synthetic */ ContextMenuPopupWindowImpl this$0;

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            this.this$0.mMenu.performItemAction(this.this$0.mSeparateMenuItem, 0);
            this.this$0.dismiss();
        }
    }

    public final void fastShowAsContextMenu(View view, float f, float f2) {
        View rootView = view.getRootView();
        Rect rect = new Rect();
        ViewUtils.getBoundsInWindow(rootView, rect);
        this.mMaxWidth = checkMaxWidth(rect);
        setWidth(computePopupContentWidth(rect));
        setHeight(-2);
        this.mSeparateMenuView.setVisibility(8);
        showWithAnchor(view, f, f2, rect);
        this.mContentView.forceLayout();
    }

    public final int getListViewHeight() {
        ListView listView = (ListView) this.mContentView.findViewById(R.id.list);
        if (listView == null) {
            this.mContentView.measure(View.MeasureSpec.makeMeasureSpec(0, 0), View.MeasureSpec.makeMeasureSpec(0, 0));
            return this.mContentView.getMeasuredHeight();
        }
        ListAdapter adapter = listView.getAdapter();
        int measuredHeight = 0;
        for (int i = 0; i < adapter.getCount(); i++) {
            View view = adapter.getView(i, null, listView);
            view.measure(View.MeasureSpec.makeMeasureSpec(this.mMaxWidth, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(0, 0));
            measuredHeight += view.getMeasuredHeight();
        }
        return measuredHeight;
    }

    public final int getMultipleChoiceViewHeight() {
        if (this.mSeparateMenuView.getVisibility() != 0) {
            return 0;
        }
        ViewGroup.LayoutParams layoutParams = this.mSeparateMenuView.getLayoutParams();
        int i = (layoutParams == null || !(layoutParams instanceof ViewGroup.MarginLayoutParams)) ? 0 : ((ViewGroup.MarginLayoutParams) this.mSeparateMenuView.getLayoutParams()).topMargin;
        View view = this.mSeparateItemMenuView;
        if (view != null) {
            view.setPadding(view.getPaddingLeft(), 0, this.mSeparateItemMenuView.getPaddingRight(), 0);
        }
        this.mSeparateMenuView.measure(View.MeasureSpec.makeMeasureSpec(this.mMaxWidth, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(0, 0));
        View view2 = this.mSeparateItemMenuView;
        if (view2 != null) {
            TaggingDrawableUtil.updateItemBackground(view2, 0, 1);
            this.mSeparateMenuView.measure(View.MeasureSpec.makeMeasureSpec(this.mMaxWidth, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(0, 0));
        }
        return i + this.mSeparateMenuView.getMeasuredHeight();
    }

    public final void showWithAnchor(View view, float f, float f2, Rect rect) {
        Rect rect2 = new Rect();
        ViewUtils.getBoundsInWindow(view, rect2);
        int iWidth = rect2.left + ((int) f);
        int i = rect2.top + ((int) f2);
        boolean z = iWidth <= getWidth();
        boolean z2 = iWidth >= rect.width() - getWidth();
        int listViewHeight = getListViewHeight();
        float listViewHeight2 = i - (getListViewHeight() / 2);
        if (listViewHeight2 < rect.height() * 0.1f) {
            listViewHeight2 = rect.height() * 0.1f;
        }
        int multipleChoiceViewHeight = listViewHeight + getMultipleChoiceViewHeight();
        setHeight(multipleChoiceViewHeight);
        setContentHeight(multipleChoiceViewHeight);
        float f3 = multipleChoiceViewHeight;
        if (listViewHeight2 + f3 > rect.height() * 0.9f) {
            listViewHeight2 = (rect.height() * 0.9f) - f3;
        }
        if (listViewHeight2 < rect.height() * 0.1f) {
            listViewHeight2 = rect.height() * 0.1f;
            setHeight((int) (rect.height() * 0.79999995f));
        }
        if (z) {
            iWidth = this.mMarginScreen;
        } else if (z2) {
            iWidth = (rect.width() - this.mMarginScreen) - getWidth();
        }
        showAtLocation(view, 0, iWidth, (int) listViewHeight2);
        ListPopup.changeWindowBackground(this.mRootView.getRootView());
    }

    public void update(Menu menu) {
        this.mAdapter.update(menu);
    }
}
