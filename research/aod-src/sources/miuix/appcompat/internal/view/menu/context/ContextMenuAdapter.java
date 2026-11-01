package miuix.appcompat.internal.view.menu.context;

import android.view.MenuItem;
import miuix.internal.widget.PopupMenuAdapter;

/* JADX INFO: loaded from: classes2.dex */
class ContextMenuAdapter extends PopupMenuAdapter {
    public MenuItem mLastCategorySystemOrderMenuItem;

    @Override // miuix.internal.widget.PopupMenuAdapter
    public boolean checkMenuItem(MenuItem menuItem) {
        boolean zCheckMenuItem = super.checkMenuItem(menuItem);
        if (!zCheckMenuItem || menuItem.getOrder() != 131072) {
            return zCheckMenuItem;
        }
        if (this.mLastCategorySystemOrderMenuItem != null) {
            throw new IllegalStateException("Only one menu item is allowed to have CATEGORY_SYSTEM order!");
        }
        this.mLastCategorySystemOrderMenuItem = menuItem;
        return false;
    }
}
