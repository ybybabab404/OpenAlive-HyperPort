package miuix.appcompat.internal.view.menu.action;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.Iterator;
import miuix.appcompat.R;
import miuix.appcompat.internal.app.widget.ActionBarOverlayLayout;
import miuix.appcompat.internal.view.ActionBarPolicy;
import miuix.appcompat.internal.view.menu.BaseMenuPresenter;
import miuix.appcompat.internal.view.menu.ListMenuPresenter;
import miuix.appcompat.internal.view.menu.MenuBuilder;
import miuix.appcompat.internal.view.menu.MenuDialogHelper;
import miuix.appcompat.internal.view.menu.MenuItemImpl;
import miuix.appcompat.internal.view.menu.MenuPopupHelper;
import miuix.appcompat.internal.view.menu.MenuPresenter;
import miuix.appcompat.internal.view.menu.MenuView;
import miuix.appcompat.internal.view.menu.SubMenuBuilder;
import miuix.internal.util.AttributeResolver;
import miuix.popupwidget.internal.strategy.BottomMenuStrategy;

/* JADX INFO: loaded from: classes2.dex */
public class ActionMenuPresenter extends BaseMenuPresenter {
    public boolean isMaxItemCountSet;
    public final SparseBooleanArray mActionButtonGroups;
    public ActionButtonSubMenu mActionButtonPopup;
    public View mBottomMenuCustomView;
    public ActionBarOverlayLayout mDecorView;
    public boolean mExpandedActionViewsExclusive;
    public int mListItemLayoutRes;
    public int mListLayoutRes;
    public OverflowMenu mListOverflowMenu;
    public int mMaxItems;
    public int mOpenSubMenuId;
    public View mOverflowButton;
    public OverflowMenu mOverflowMenu;
    public int mOverflowMenuAttrs;
    public MenuItemImpl mOverflowMenuItem;
    public final PopupPresenterCallback mPopupPresenterCallback;
    public OpenOverflowRunnable mPostedOpenRunnable;
    public boolean mReserveOverflow;
    public boolean mReserveOverflowSet;
    public View mScrapActionButtonView;
    public boolean mStrictWidthLimit;
    public boolean mWidthLimitSet;

    public class ActionButtonSubMenu extends MenuDialogHelper {
        public ActionButtonSubMenu(SubMenuBuilder subMenuBuilder) {
            super(subMenuBuilder);
            ActionMenuPresenter.this.setCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
        }

        @Override // miuix.appcompat.internal.view.menu.MenuDialogHelper, android.content.DialogInterface.OnDismissListener
        public void onDismiss(DialogInterface dialogInterface) {
            super.onDismiss(dialogInterface);
            ActionMenuPresenter.this.mActionButtonPopup = null;
            ActionMenuPresenter.this.mOpenSubMenuId = 0;
        }
    }

    public class ListOverflowMenu implements OverflowMenu {
        public ListMenuPresenter mListMenuPresenter;

        public ListOverflowMenu() {
        }

        @Override // miuix.appcompat.internal.view.menu.action.ActionMenuPresenter.OverflowMenu
        public void dismiss(boolean z) {
            if (ActionMenuPresenter.this.mMenuView instanceof PhoneActionMenuView) {
                ((PhoneActionMenuView) ActionMenuPresenter.this.mMenuView).hideOverflowMenu(ActionMenuPresenter.this.mDecorView);
            }
        }

        public final ListMenuPresenter getListMenuPresenter(MenuBuilder menuBuilder) {
            if (this.mListMenuPresenter == null) {
                this.mListMenuPresenter = new ListMenuPresenter(ActionMenuPresenter.this.mContext, ActionMenuPresenter.this.mListLayoutRes, ActionMenuPresenter.this.mListItemLayoutRes);
            }
            menuBuilder.addMenuPresenter(this.mListMenuPresenter);
            return this.mListMenuPresenter;
        }

        public View getOverflowMenuView(MenuBuilder menuBuilder) {
            if (menuBuilder == null || menuBuilder.getNonActionItems().size() <= 0) {
                return null;
            }
            return (View) getListMenuPresenter(menuBuilder).getMenuView((ViewGroup) ActionMenuPresenter.this.mMenuView);
        }

        @Override // miuix.appcompat.internal.view.menu.action.ActionMenuPresenter.OverflowMenu
        public boolean isShowing() {
            if (ActionMenuPresenter.this.mMenuView instanceof PhoneActionMenuView) {
                return ((PhoneActionMenuView) ActionMenuPresenter.this.mMenuView).isOverflowMenuShowing();
            }
            return false;
        }

        @Override // miuix.appcompat.internal.view.menu.action.ActionMenuPresenter.OverflowMenu
        public boolean tryShow() {
            if (ActionMenuPresenter.this.mMenuView instanceof PhoneActionMenuView) {
                return ((PhoneActionMenuView) ActionMenuPresenter.this.mMenuView).showOverflowMenu(ActionMenuPresenter.this.mDecorView);
            }
            return false;
        }

        @Override // miuix.appcompat.internal.view.menu.action.ActionMenuPresenter.OverflowMenu
        public void update(MenuBuilder menuBuilder) {
            if (ActionMenuPresenter.this.mMenuView instanceof PhoneActionMenuView) {
                ((PhoneActionMenuView) ActionMenuPresenter.this.mMenuView).setOverflowMenuView(getOverflowMenuView(menuBuilder));
            }
        }
    }

    public class OpenOverflowRunnable implements Runnable {
        public OverflowMenu mPopup;

        public OpenOverflowRunnable(OverflowMenu overflowMenu) {
            this.mPopup = overflowMenu;
        }

        @Override // java.lang.Runnable
        public void run() {
            View view = (View) ActionMenuPresenter.this.mMenuView;
            if (view != null && view.getWindowToken() != null && this.mPopup.tryShow()) {
                ActionMenuPresenter actionMenuPresenter = ActionMenuPresenter.this;
                actionMenuPresenter.mOverflowMenu = this.mPopup;
                actionMenuPresenter.mOverflowButton.setSelected(true);
            }
            ActionMenuPresenter.this.mPostedOpenRunnable = null;
        }
    }

    public interface OverflowMenu {
        void dismiss(boolean z);

        boolean isShowing();

        boolean tryShow();

        void update(MenuBuilder menuBuilder);
    }

    public class PopupOverflowMenu extends MenuPopupHelper implements OverflowMenu {
        public PopupOverflowMenu(Context context, MenuBuilder menuBuilder, View view, View view2, boolean z) {
            super(context, menuBuilder, view, view2, z);
            TypedValue typedValueResolveTypedValue = AttributeResolver.resolveTypedValue(context, R.attr.overflowMenuMaxHeight);
            if (typedValueResolveTypedValue != null && typedValueResolveTypedValue.type == 5) {
                if (typedValueResolveTypedValue.resourceId > 0) {
                    context.getResources().getDimensionPixelSize(typedValueResolveTypedValue.resourceId);
                } else {
                    TypedValue.complexToDimensionPixelSize(typedValueResolveTypedValue.data, context.getResources().getDisplayMetrics());
                }
            }
            if (ActionMenuPresenter.this.mMenuView instanceof ResponsiveActionMenuView) {
                setStrategy(new BottomMenuStrategy(context));
            } else {
                setDropDownGravity(8388661);
            }
            setCallback(ActionMenuPresenter.this.mPopupPresenterCallback);
            setMenuItemLayout(R.layout.miuix_appcompat_overflow_popup_menu_item_layout);
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPopupHelper, miuix.appcompat.internal.view.menu.action.ActionMenuPresenter.OverflowMenu
        public void dismiss(boolean z) {
            super.dismiss(z);
            View view = ActionMenuPresenter.this.mOverflowButton;
            if (view != null) {
                view.setSelected(false);
            }
            if (ActionMenuPresenter.this.mMenuView instanceof ResponsiveActionMenuView) {
                ((ResponsiveActionMenuView) ActionMenuPresenter.this.mMenuView).hideOverflowSelectedBackground();
            }
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPopupHelper, miuix.popupwidget.widget.PopupView.OnDismissListener
        public void onDismiss() {
            super.onDismiss();
            ActionMenuPresenter.this.mMenu.close();
            ActionMenuPresenter.this.mOverflowMenu = null;
        }

        @Override // miuix.appcompat.internal.view.menu.action.ActionMenuPresenter.OverflowMenu
        public void update(MenuBuilder menuBuilder) {
        }
    }

    public class PopupPresenterCallback implements MenuPresenter.Callback {
        public PopupPresenterCallback() {
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPresenter.Callback
        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            if (menuBuilder instanceof SubMenuBuilder) {
                BaseMenuPresenter.close(menuBuilder.getRootMenu(), false);
            }
        }

        @Override // miuix.appcompat.internal.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            if (menuBuilder == null) {
                return false;
            }
            ActionMenuPresenter.this.mOpenSubMenuId = ((SubMenuBuilder) menuBuilder).getItem().getItemId();
            return false;
        }
    }

    public static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: miuix.appcompat.internal.view.menu.action.ActionMenuPresenter.SavedState.1
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public int openSubMenuId;

        public SavedState() {
        }

        public SavedState(Parcel parcel) {
            this.openSubMenuId = parcel.readInt();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.openSubMenuId);
        }
    }

    public static /* synthetic */ void $r8$lambda$q5kHayc2GknEb3sBB8QZuSrgcQU(ActionMenuPresenter actionMenuPresenter) {
        MenuBuilder menuBuilder = actionMenuPresenter.mMenu;
        if (menuBuilder != null) {
            BaseMenuPresenter.dispatchMenuItemSelected(menuBuilder, menuBuilder.getRootMenu(), actionMenuPresenter.getOverflowMenuItem());
        }
        if (actionMenuPresenter.mOverflowButton.isSelected()) {
            actionMenuPresenter.hideOverflowMenu(true);
        } else {
            actionMenuPresenter.showOverflowMenu();
        }
    }

    public ActionMenuPresenter(Context context, ActionBarOverlayLayout actionBarOverlayLayout, int i, int i2) {
        this(context, actionBarOverlayLayout, i, i2, 0, 0);
    }

    public ActionMenuPresenter(Context context, ActionBarOverlayLayout actionBarOverlayLayout, int i, int i2, int i3, int i4) {
        super(context, i, i2);
        this.mOverflowMenuAttrs = android.R.attr.actionOverflowButtonStyle;
        this.mActionButtonGroups = new SparseBooleanArray();
        this.mPopupPresenterCallback = new PopupPresenterCallback();
        this.mListLayoutRes = i3;
        this.mListItemLayoutRes = i4;
        this.mDecorView = actionBarOverlayLayout;
    }

    @Override // miuix.appcompat.internal.view.menu.BaseMenuPresenter
    public void bindItemView(MenuItemImpl menuItemImpl, MenuView.ItemView itemView) {
        itemView.initialize(menuItemImpl, 0);
        itemView.setItemInvoker((MenuBuilder.ItemInvoker) this.mMenuView);
    }

    public View createOverflowMenuButton(Context context) {
        OverflowMenuButton overflowMenuButton = new OverflowMenuButton(context, null, this.mOverflowMenuAttrs);
        overflowMenuButton.setOnOverflowMenuButtonClickListener(new OverflowMenuButton.OnOverflowMenuButtonClickListener() { // from class: miuix.appcompat.internal.view.menu.action.ActionMenuPresenter$$ExternalSyntheticLambda0
            @Override // miuix.appcompat.internal.view.menu.action.OverflowMenuButton.OnOverflowMenuButtonClickListener
            public final void onOverflowMenuButtonClick() {
                ActionMenuPresenter.$r8$lambda$q5kHayc2GknEb3sBB8QZuSrgcQU(this.f$0);
            }
        });
        return overflowMenuButton;
    }

    public boolean dismissPopupMenus(boolean z) {
        return hideOverflowMenu(z);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final View findViewForItem(MenuItem menuItem) {
        ViewGroup viewGroup = (ViewGroup) this.mMenuView;
        if (viewGroup == null) {
            return null;
        }
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            if ((childAt instanceof MenuView.ItemView) && ((MenuView.ItemView) childAt).getItemData() == menuItem) {
                return childAt;
            }
        }
        return null;
    }

    @Override // miuix.appcompat.internal.view.menu.BaseMenuPresenter, miuix.appcompat.internal.view.menu.MenuPresenter
    public boolean flagActionItems() {
        ArrayList visibleItems = this.mMenu.getVisibleItems();
        int size = visibleItems.size();
        int i = this.mMaxItems;
        if (i < size) {
            i--;
        }
        int i2 = 0;
        while (true) {
            boolean z = true;
            if (i2 >= size || i <= 0) {
                break;
            }
            MenuItemImpl menuItemImpl = (MenuItemImpl) visibleItems.get(i2);
            if (!menuItemImpl.requestsActionButton() && !menuItemImpl.requiresActionButton()) {
                z = false;
            }
            menuItemImpl.setIsActionButton(z);
            if (z) {
                i--;
            }
            i2++;
        }
        while (i2 < size) {
            ((MenuItemImpl) visibleItems.get(i2)).setIsActionButton(false);
            i2++;
        }
        return true;
    }

    public int getDefaultMaxItemCount() {
        Context context = this.mContext;
        if (context != null) {
            return AttributeResolver.resolveInt(context, R.attr.actionMenuItemLimit, 5);
        }
        return 5;
    }

    @Override // miuix.appcompat.internal.view.menu.BaseMenuPresenter
    public View getItemView(MenuItemImpl menuItemImpl, View view, ViewGroup viewGroup) {
        View actionView = menuItemImpl.getActionView();
        if (actionView == null || menuItemImpl.hasCollapsibleActionView()) {
            if (!isConvertViewTypeAllowed(view)) {
                view = null;
            }
            actionView = super.getItemView(menuItemImpl, view, viewGroup);
        }
        actionView.setVisibility(menuItemImpl.isActionViewExpanded() ? 8 : 0);
        ActionMenuView actionMenuView = (ActionMenuView) viewGroup;
        ViewGroup.LayoutParams layoutParams = actionView.getLayoutParams();
        if (!actionMenuView.checkLayoutParams(layoutParams)) {
            actionView.setLayoutParams(actionMenuView.generateLayoutParams(layoutParams));
        }
        return actionView;
    }

    @Override // miuix.appcompat.internal.view.menu.BaseMenuPresenter
    public MenuView getMenuView(ViewGroup viewGroup) {
        MenuView menuView = super.getMenuView(viewGroup);
        ((ActionMenuView) menuView).setPresenter(this);
        View view = this.mBottomMenuCustomView;
        if (view != null && view.getParent() == null && (menuView instanceof ResponsiveActionMenuView)) {
            ((ResponsiveActionMenuView) menuView).addCustomView(this.mBottomMenuCustomView);
        }
        return menuView;
    }

    public OverflowMenu getOverflowMenu() {
        if (shouldShowPopupOverflow()) {
            return new PopupOverflowMenu(this.mContext, this.mMenu, this.mOverflowButton, this.mDecorView, true);
        }
        if (this.mListOverflowMenu == null) {
            this.mListOverflowMenu = new ListOverflowMenu();
        }
        return this.mListOverflowMenu;
    }

    public MenuItemImpl getOverflowMenuItem() {
        if (this.mOverflowMenuItem == null) {
            this.mOverflowMenuItem = BaseMenuPresenter.createMenuItemImpl(this.mMenu, 0, R.id.more, 0, 0, this.mContext.getString(R.string.more), 0);
        }
        return this.mOverflowMenuItem;
    }

    public boolean hideOverflowMenu(boolean z) {
        if (this.mPostedOpenRunnable == null || this.mMenuView == null) {
            OverflowMenu overflowMenu = this.mOverflowMenu;
            if (overflowMenu == null) {
                return false;
            }
            boolean zIsShowing = overflowMenu.isShowing();
            if (zIsShowing) {
                this.mOverflowButton.setSelected(false);
            }
            this.mOverflowMenu.dismiss(z);
            return zIsShowing;
        }
        this.mOverflowButton.setSelected(false);
        ((View) this.mMenuView).removeCallbacks(this.mPostedOpenRunnable);
        this.mPostedOpenRunnable = null;
        MenuView menuView = this.mMenuView;
        if (!(menuView instanceof ResponsiveActionMenuView)) {
            return true;
        }
        ((ResponsiveActionMenuView) menuView).hideOverflowSelectedBackground();
        return true;
    }

    public boolean hideSubMenus() {
        ActionButtonSubMenu actionButtonSubMenu = this.mActionButtonPopup;
        if (actionButtonSubMenu == null) {
            return false;
        }
        actionButtonSubMenu.dismiss();
        return true;
    }

    @Override // miuix.appcompat.internal.view.menu.BaseMenuPresenter, miuix.appcompat.internal.view.menu.MenuPresenter
    public void initForMenu(Context context, MenuBuilder menuBuilder) {
        super.initForMenu(context, menuBuilder);
        context.getResources();
        ActionBarPolicy actionBarPolicy = ActionBarPolicy.get(context);
        if (!this.mReserveOverflowSet) {
            this.mReserveOverflow = actionBarPolicy.showsOverflowMenuButton();
        }
        if (!this.isMaxItemCountSet) {
            this.mMaxItems = getDefaultMaxItemCount();
        }
        if (!this.mReserveOverflow) {
            this.mOverflowButton = null;
        } else if (this.mOverflowButton == null) {
            this.mOverflowButton = createOverflowMenuButton(this.mSystemContext);
        }
        this.mScrapActionButtonView = null;
    }

    public boolean isConvertViewTypeAllowed(View view) {
        return view instanceof ActionMenuItemView;
    }

    public boolean isOverflowMenuShowing() {
        OverflowMenu overflowMenu = this.mOverflowMenu;
        return overflowMenu != null && overflowMenu.isShowing();
    }

    public boolean isOverflowReserved() {
        return this.mReserveOverflow;
    }

    @Override // miuix.appcompat.internal.view.menu.BaseMenuPresenter, miuix.appcompat.internal.view.menu.MenuPresenter
    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        dismissPopupMenus(true);
        super.onCloseMenu(menuBuilder, z);
    }

    public void onConfigurationChanged(Configuration configuration) {
        if (!this.isMaxItemCountSet && this.mContext != null) {
            this.mMaxItems = getDefaultMaxItemCount();
        }
        MenuBuilder menuBuilder = this.mMenu;
        if (menuBuilder != null) {
            BaseMenuPresenter.notifyItemsChanged(menuBuilder, true);
        }
    }

    @Override // miuix.appcompat.internal.view.menu.MenuPresenter
    public void onRestoreInstanceState(Parcelable parcelable) {
        MenuItem menuItemFindItem;
        int i = ((SavedState) parcelable).openSubMenuId;
        if (i <= 0 || (menuItemFindItem = this.mMenu.findItem(i)) == null) {
            return;
        }
        onSubMenuSelected((SubMenuBuilder) menuItemFindItem.getSubMenu());
    }

    @Override // miuix.appcompat.internal.view.menu.MenuPresenter
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState();
        savedState.openSubMenuId = this.mOpenSubMenuId;
        return savedState;
    }

    @Override // miuix.appcompat.internal.view.menu.BaseMenuPresenter, miuix.appcompat.internal.view.menu.MenuPresenter
    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        if (!subMenuBuilder.hasVisibleItems()) {
            return false;
        }
        SubMenuBuilder subMenuBuilder2 = subMenuBuilder;
        while (subMenuBuilder2.getParentMenu() != this.mMenu) {
            subMenuBuilder2 = (SubMenuBuilder) subMenuBuilder2.getParentMenu();
        }
        if (findViewForItem(subMenuBuilder2.getItem()) == null && this.mOverflowButton == null) {
            return false;
        }
        this.mOpenSubMenuId = subMenuBuilder.getItem().getItemId();
        ActionButtonSubMenu actionButtonSubMenu = new ActionButtonSubMenu(subMenuBuilder);
        this.mActionButtonPopup = actionButtonSubMenu;
        actionButtonSubMenu.show(null);
        super.onSubMenuSelected(subMenuBuilder);
        return true;
    }

    public void removeBottomMenuCustomView() {
        if (this.mBottomMenuCustomView != null) {
            MenuView menuView = this.mMenuView;
            if (menuView instanceof ResponsiveActionMenuView) {
                ((ResponsiveActionMenuView) menuView).removeCustomView();
            }
            this.mBottomMenuCustomView = null;
        }
    }

    public void setActionEditMode(boolean z) {
        if (z) {
            this.mOverflowMenuAttrs = R.attr.actionModeOverflowButtonStyle;
        }
    }

    public void setBottomMenuCustomView(View view) {
        ViewGroup viewGroup;
        View view2 = this.mBottomMenuCustomView;
        if (view2 != null && view2 != view && (viewGroup = (ViewGroup) view2.getParent()) != null) {
            viewGroup.removeView(this.mBottomMenuCustomView);
        }
        this.mBottomMenuCustomView = view;
        if (view.getParent() == null) {
            MenuView menuView = this.mMenuView;
            if (menuView instanceof ResponsiveActionMenuView) {
                ((ResponsiveActionMenuView) menuView).addCustomView(view);
            }
        }
    }

    public void setExpandedActionViewsExclusive(boolean z) {
        this.mExpandedActionViewsExclusive = z;
    }

    public void setItemLimit(int i) {
        this.isMaxItemCountSet = true;
        int i2 = this.mMaxItems;
        this.mMaxItems = i;
        MenuBuilder menuBuilder = this.mMenu;
        if (menuBuilder == null || i2 == i) {
            return;
        }
        menuBuilder.updateVisibleItemCountLimit();
    }

    public void setReserveOverflow(boolean z) {
        this.mReserveOverflow = z;
        this.mReserveOverflowSet = true;
    }

    public void setWidthLimit(int i, boolean z) {
        this.mStrictWidthLimit = z;
        this.mWidthLimitSet = true;
    }

    @Override // miuix.appcompat.internal.view.menu.BaseMenuPresenter
    public boolean shouldIncludeItem(int i, MenuItemImpl menuItemImpl) {
        return menuItemImpl.isActionButton();
    }

    public boolean shouldShowPopupOverflow() {
        View view = this.mOverflowButton;
        return (view == null || view.getParent() == null) ? false : true;
    }

    public boolean showOverflowMenu() {
        if (!this.mReserveOverflow || isOverflowMenuShowing() || this.mMenu == null || this.mMenuView == null || this.mPostedOpenRunnable != null || this.mOverflowButton == null) {
            return false;
        }
        OpenOverflowRunnable openOverflowRunnable = new OpenOverflowRunnable(getOverflowMenu());
        this.mPostedOpenRunnable = openOverflowRunnable;
        ((View) this.mMenuView).post(openOverflowRunnable);
        super.onSubMenuSelected(null);
        MenuView menuView = this.mMenuView;
        if (!(menuView instanceof ResponsiveActionMenuView)) {
            return true;
        }
        ((ResponsiveActionMenuView) menuView).showOverflowSelectedBackground(this.mOverflowButton);
        return true;
    }

    public void updateBadgeOnItemViews() {
        Iterator it = this.mMenu.getVisibleItems().iterator();
        while (it.hasNext()) {
            ((MenuItemImpl) it.next()).updateBadgeDrawable();
        }
    }

    @Override // miuix.appcompat.internal.view.menu.BaseMenuPresenter, miuix.appcompat.internal.view.menu.MenuPresenter
    public void updateMenuView(boolean z) {
        super.updateMenuView(z);
        if (this.mMenuView == null) {
            return;
        }
        MenuBuilder menuBuilder = this.mMenu;
        ArrayList nonActionItems = menuBuilder != null ? menuBuilder.getNonActionItems() : null;
        boolean z2 = false;
        if (this.mReserveOverflow && nonActionItems != null) {
            int size = nonActionItems.size();
            if (size == 1) {
                z2 = !((MenuItemImpl) nonActionItems.get(0)).isActionViewExpanded();
            } else if (size > 0) {
                z2 = true;
            }
        }
        if (z2) {
            View view = this.mOverflowButton;
            if (view == null) {
                this.mOverflowButton = createOverflowMenuButton(this.mSystemContext);
            } else {
                view.setTranslationY(0.0f);
            }
            ViewGroup viewGroup = (ViewGroup) this.mOverflowButton.getParent();
            if (viewGroup != this.mMenuView) {
                if (viewGroup != null) {
                    viewGroup.removeView(this.mOverflowButton);
                }
                ActionMenuView actionMenuView = (ActionMenuView) this.mMenuView;
                View view2 = this.mOverflowButton;
                actionMenuView.addView(view2, actionMenuView.generateOverflowButtonLayoutParams(view2));
            }
        } else {
            View view3 = this.mOverflowButton;
            if (view3 != null) {
                Object parent = view3.getParent();
                Object obj = this.mMenuView;
                if (parent == obj) {
                    ((ViewGroup) obj).removeView(this.mOverflowButton);
                }
            }
        }
        ((ActionMenuView) this.mMenuView).setOverflowReserved(this.mReserveOverflow);
        if (shouldShowPopupOverflow()) {
            return;
        }
        getOverflowMenu().update(this.mMenu);
    }
}
