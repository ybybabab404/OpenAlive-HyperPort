package miuix.appcompat.widget;

import android.view.MenuItem;
import miuix.internal.widget.PopupMenuView;
import miuix.internal.widget.PopupMenuWindow;

/* JADX INFO: loaded from: classes2.dex */
public class PopupMenu {
    public OnMenuItemClickListener mMenuItemClickListener;

    /* JADX INFO: renamed from: miuix.appcompat.widget.PopupMenu$1, reason: invalid class name */
    class AnonymousClass1 extends PopupMenuWindow {
    }

    /* JADX INFO: renamed from: miuix.appcompat.widget.PopupMenu$2, reason: invalid class name */
    class AnonymousClass2 extends PopupMenuView {
        public final /* synthetic */ PopupMenu this$0;

        @Override // miuix.internal.widget.PopupMenuView
        public void onMenuItemClick(MenuItem menuItem) {
            if (this.this$0.mMenuItemClickListener != null) {
                this.this$0.mMenuItemClickListener.onMenuItemClick(menuItem);
            }
        }
    }

    public interface OnDismissListener {
    }

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    public interface OnPreDismissListener {
    }
}
