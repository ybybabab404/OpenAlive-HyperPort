package miuix.internal.widget;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import miuix.popupwidget.widget.PopupView;

/* JADX INFO: loaded from: classes2.dex */
public class PopupMenuView implements IPopupMenuWidget {
    public PopupMenuAdapter mAdapter;
    public PopupView mPopupView;

    /* JADX INFO: renamed from: miuix.internal.widget.PopupMenuView$1, reason: invalid class name */
    class AnonymousClass1 implements AdapterView.OnItemClickListener {
        public final /* synthetic */ PopupMenuView this$0;

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView adapterView, View view, int i, long j) {
            this.this$0.onMenuItemClick(this.this$0.mAdapter.getItem(i));
            this.this$0.dismiss();
        }
    }

    public void dismiss() {
        this.mPopupView.dismiss();
    }

    public void onMenuItemClick(MenuItem menuItem) {
    }
}
