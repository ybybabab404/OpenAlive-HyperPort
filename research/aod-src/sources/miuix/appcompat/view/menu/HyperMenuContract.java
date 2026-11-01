package miuix.appcompat.view.menu;

/* JADX INFO: loaded from: classes2.dex */
public class HyperMenuContract {

    public enum CheckableType {
        NON_SUPPORT,
        NOT_CHECKED,
        CHECKED
    }

    public static class HyperMenuDivider extends HyperMenuItem {
        public HyperMenuDivider() {
            this(null, -100);
        }

        public HyperMenuDivider(miuix.appcompat.internal.view.menu.MenuItemImpl menuItemImpl, int i) {
            super(menuItemImpl, i);
        }
    }

    public static class HyperMenuItem {
        public final int itemId;
        public final miuix.appcompat.internal.view.menu.MenuItemImpl menuItem;

        public HyperMenuItem(miuix.appcompat.internal.view.menu.MenuItemImpl menuItemImpl) {
            this.menuItem = menuItemImpl;
            if (menuItemImpl != null) {
                this.itemId = menuItemImpl.getItemId();
            } else {
                this.itemId = -200;
            }
        }

        public HyperMenuItem(miuix.appcompat.internal.view.menu.MenuItemImpl menuItemImpl, int i) {
            this.menuItem = menuItemImpl;
            this.itemId = i;
        }

        public int getItemId() {
            return this.itemId;
        }

        public miuix.appcompat.internal.view.menu.MenuItemImpl getMenuItem() {
            return this.menuItem;
        }
    }

    public static class HyperMenuTextItem extends HyperMenuItem {
        public CheckableType checkStatus;
        public boolean isExpandable;
        public boolean isHeaderItem;
        public boolean isMultiSelect;
        public int itemForeignKey;
        public boolean showSubtitle;
        public CharSequence subtitle;

        public HyperMenuTextItem(miuix.appcompat.internal.view.menu.MenuItemImpl menuItemImpl) {
            super(menuItemImpl);
            this.checkStatus = CheckableType.NON_SUPPORT;
            this.itemForeignKey = -1;
            this.isMultiSelect = false;
            this.showSubtitle = true;
        }

        public CharSequence getSubtitle() {
            return this.subtitle;
        }

        public boolean isChecked() {
            return this.checkStatus == CheckableType.CHECKED;
        }

        public void setSubtitle(CharSequence charSequence) {
            this.subtitle = charSequence;
        }
    }
}
