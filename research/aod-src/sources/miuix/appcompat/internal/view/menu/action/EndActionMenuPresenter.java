package miuix.appcompat.internal.view.menu.action;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import miuix.appcompat.R;
import miuix.appcompat.internal.app.widget.ActionBarOverlayLayout;
import miuix.appcompat.internal.view.menu.BaseMenuPresenter;
import miuix.appcompat.internal.view.menu.MenuBuilder;
import miuix.appcompat.internal.view.menu.MenuItemImpl;
import miuix.appcompat.internal.view.menu.MenuPopupHelper;
import miuix.appcompat.internal.view.menu.MenuView;
import miuix.appcompat.internal.view.menu.SubMenuBuilder;

/* JADX INFO: loaded from: classes2.dex */
public class EndActionMenuPresenter extends ActionMenuPresenter {
    public MenuItemImpl mMoreButtonItem;

    public class PopupSubMenu extends MenuPopupHelper {
        public PopupSubMenu(Context context, MenuBuilder menuBuilder, View view, View view2, boolean z) {
            super(context, menuBuilder, view, view2, z);
            setCallback(EndActionMenuPresenter.this.mPopupPresenterCallback);
            setMenuItemLayout(R.layout.miuix_appcompat_overflow_popup_menu_item_layout);
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPopupHelper, miuix.appcompat.internal.view.menu.action.ActionMenuPresenter.OverflowMenu
        public void dismiss(boolean z) {
            super.dismiss(z);
            View view = EndActionMenuPresenter.this.mOverflowButton;
            if (view != null) {
                view.setSelected(false);
            }
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPopupHelper, miuix.popupwidget.widget.PopupView.OnDismissListener
        public void onDismiss() {
            super.onDismiss();
            EndActionMenuPresenter.this.mMenu.close();
        }
    }

    public static /* synthetic */ boolean $r8$lambda$hmK2IHmKxP_XTPvb8q6kllOqJ1c(EndActionMenuPresenter endActionMenuPresenter, MenuItem menuItem) {
        MenuBuilder menuBuilder = endActionMenuPresenter.mMenu;
        if (menuBuilder != null) {
            BaseMenuPresenter.dispatchMenuItemSelected(menuBuilder, menuBuilder.getRootMenu(), endActionMenuPresenter.getOverflowMenuItem());
        }
        if (endActionMenuPresenter.mOverflowButton.isSelected()) {
            endActionMenuPresenter.hideOverflowMenu(true);
            return true;
        }
        endActionMenuPresenter.showOverflowMenu();
        return true;
    }

    public EndActionMenuPresenter(Context context, ActionBarOverlayLayout actionBarOverlayLayout, int i, int i2, int i3, int i4) {
        super(context, actionBarOverlayLayout, i, i2, i3, i4);
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuPresenter
    public View createOverflowMenuButton(Context context) {
        ViewGroup viewGroup = (ViewGroup) this.mMenuView;
        if (viewGroup == null) {
            return null;
        }
        MenuBuilder menuBuilder = this.mMenu;
        int i = R.id.more;
        MenuItemImpl menuItemImplCreateMenuItemImpl = BaseMenuPresenter.createMenuItemImpl(menuBuilder, 0, i, 0, 0, context.getString(R.string.more), 2);
        this.mMenu.stopDispatchingItemsChanged();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(new int[]{R.attr.endActionMoreButtonIcon});
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(0);
        typedArrayObtainStyledAttributes.recycle();
        menuItemImplCreateMenuItemImpl.setIcon(drawable);
        menuItemImplCreateMenuItemImpl.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() { // from class: miuix.appcompat.internal.view.menu.action.EndActionMenuPresenter$$ExternalSyntheticLambda0
            @Override // android.view.MenuItem.OnMenuItemClickListener
            public final boolean onMenuItemClick(MenuItem menuItem) {
                return EndActionMenuPresenter.$r8$lambda$hmK2IHmKxP_XTPvb8q6kllOqJ1c(this.f$0, menuItem);
            }
        });
        this.mMenu.setPreventDispatchingItemsChanged(false);
        View itemView = getItemView(menuItemImplCreateMenuItemImpl, null, viewGroup);
        itemView.setId(i);
        this.mMoreButtonItem = menuItemImplCreateMenuItemImpl;
        menuItemImplCreateMenuItemImpl.setView(itemView);
        return itemView;
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuPresenter
    public int getDefaultMaxItemCount() {
        Context context = this.mContext;
        if (context != null) {
            return context.getResources().getInteger(R.integer.action_bar_end_menu_max_item_count);
        }
        return 5;
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuPresenter
    public boolean hideOverflowMenu(boolean z) {
        boolean zHideOverflowMenu = super.hideOverflowMenu(z);
        MenuView menuView = this.mMenuView;
        if (menuView instanceof ResponsiveActionMenuView) {
            ((ResponsiveActionMenuView) menuView).hideOverflowSelectedBackground();
        }
        return zHideOverflowMenu;
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuPresenter
    public boolean isConvertViewTypeAllowed(View view) {
        if (view != null) {
            MenuItemImpl menuItemImpl = this.mMoreButtonItem;
            boolean z = menuItemImpl != null && menuItemImpl.getView() == view;
            if ((view instanceof EndActionMenuItemView) && !z) {
                return true;
            }
        }
        return false;
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuPresenter, miuix.appcompat.internal.view.menu.BaseMenuPresenter, miuix.appcompat.internal.view.menu.MenuPresenter
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        if (!subMenuBuilder.hasVisibleItems()) {
            return false;
        }
        new PopupSubMenu(this.mContext, subMenuBuilder, this.mOverflowButton, this.mDecorView, true).tryShow();
        return true;
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuPresenter
    public boolean showOverflowMenu() {
        boolean zShowOverflowMenu = super.showOverflowMenu();
        if (zShowOverflowMenu) {
            MenuView menuView = this.mMenuView;
            if (menuView instanceof ResponsiveActionMenuView) {
                ((ResponsiveActionMenuView) menuView).showOverflowSelectedBackground(this.mOverflowButton);
            }
        }
        return zShowOverflowMenu;
    }

    public void updateBadgeOnMoreButton() {
        MenuItemImpl menuItemImpl = this.mMoreButtonItem;
        if (menuItemImpl == null) {
            return;
        }
        menuItemImpl.setHandleExtraOffset(true);
        this.mMoreButtonItem.updateBadgeDrawable();
    }
}
