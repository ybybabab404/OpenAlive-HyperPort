package androidx.appcompat.widget;

import android.view.MenuItem;
import android.widget.PopupWindow;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuPopupHelper;
import androidx.appcompat.view.menu.ShowableListMenu;

/* JADX INFO: loaded from: classes.dex */
public class PopupMenu {
    public OnMenuItemClickListener mMenuItemClickListener;
    public OnDismissListener mOnDismissListener;
    public final MenuPopupHelper mPopup;

    /* JADX INFO: renamed from: androidx.appcompat.widget.PopupMenu$1, reason: invalid class name */
    class AnonymousClass1 implements MenuBuilder.Callback {
        public final /* synthetic */ PopupMenu this$0;

        @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
        public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
            OnMenuItemClickListener onMenuItemClickListener = this.this$0.mMenuItemClickListener;
            if (onMenuItemClickListener != null) {
                return onMenuItemClickListener.onMenuItemClick(menuItem);
            }
            return false;
        }

        @Override // androidx.appcompat.view.menu.MenuBuilder.Callback
        public void onMenuModeChange(MenuBuilder menuBuilder) {
        }
    }

    /* JADX INFO: renamed from: androidx.appcompat.widget.PopupMenu$2, reason: invalid class name */
    class AnonymousClass2 implements PopupWindow.OnDismissListener {
        public final /* synthetic */ PopupMenu this$0;

        @Override // android.widget.PopupWindow.OnDismissListener
        public void onDismiss() {
            PopupMenu popupMenu = this.this$0;
            OnDismissListener onDismissListener = popupMenu.mOnDismissListener;
            if (onDismissListener != null) {
                onDismissListener.onDismiss(popupMenu);
            }
        }
    }

    /* JADX INFO: renamed from: androidx.appcompat.widget.PopupMenu$3, reason: invalid class name */
    class AnonymousClass3 extends ForwardingListener {
        public final /* synthetic */ PopupMenu this$0;

        @Override // androidx.appcompat.widget.ForwardingListener
        public ShowableListMenu getPopup() {
            return this.this$0.mPopup.getPopup();
        }

        @Override // androidx.appcompat.widget.ForwardingListener
        public boolean onForwardingStarted() {
            this.this$0.show();
            return true;
        }

        @Override // androidx.appcompat.widget.ForwardingListener
        public boolean onForwardingStopped() {
            this.this$0.dismiss();
            return true;
        }
    }

    public interface OnDismissListener {
        void onDismiss(PopupMenu popupMenu);
    }

    public interface OnMenuItemClickListener {
        boolean onMenuItemClick(MenuItem menuItem);
    }

    public void dismiss() {
        this.mPopup.dismiss();
    }

    public void show() {
        this.mPopup.show();
    }
}
