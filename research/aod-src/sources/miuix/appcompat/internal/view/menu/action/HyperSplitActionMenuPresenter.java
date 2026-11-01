package miuix.appcompat.internal.view.menu.action;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import java.util.HashMap;
import java.util.Map;
import miuix.appcompat.R;
import miuix.appcompat.internal.app.widget.ActionBarOverlayLayout;
import miuix.appcompat.internal.view.menu.HyperPopupHelper;
import miuix.appcompat.internal.view.menu.MenuBuilder;
import miuix.internal.util.AttributeResolver;

/* JADX INFO: loaded from: classes2.dex */
public class HyperSplitActionMenuPresenter extends ActionMenuPresenter {
    public Map mHyperSplitMenuPrimaryCheckedMap;
    public Map mHyperSplitMenuSecondaryCheckedMap;
    public boolean mSavePrimaryStatusByIdEnabled;

    public class HyperSplitPopupOverflowMenu extends HyperPopupHelper implements ActionMenuPresenter.OverflowMenu {
        public HyperSplitPopupOverflowMenu(Context context, MenuBuilder menuBuilder, View view, View view2, boolean z) {
            super(context, menuBuilder, view, view2, z);
            TypedValue typedValueResolveTypedValue = AttributeResolver.resolveTypedValue(context, R.attr.overflowMenuMaxHeight);
            int dimensionPixelSize = (typedValueResolveTypedValue == null || typedValueResolveTypedValue.type != 5) ? 0 : typedValueResolveTypedValue.resourceId > 0 ? context.getResources().getDimensionPixelSize(typedValueResolveTypedValue.resourceId) : TypedValue.complexToDimensionPixelSize(typedValueResolveTypedValue.data, context.getResources().getDisplayMetrics());
            if (dimensionPixelSize > 0) {
                setPopupMaxHeight(dimensionPixelSize);
            }
            setCallback(HyperSplitActionMenuPresenter.this.mPopupPresenterCallback);
        }

        @Override // miuix.appcompat.internal.view.menu.HyperPopupHelper, miuix.appcompat.internal.view.menu.action.ActionMenuPresenter.OverflowMenu
        public void dismiss(boolean z) {
            super.dismiss(z);
            View view = HyperSplitActionMenuPresenter.this.mOverflowButton;
            if (view != null) {
                view.setSelected(false);
            }
            if (HyperSplitActionMenuPresenter.this.mMenuView instanceof ResponsiveActionMenuView) {
                ((ResponsiveActionMenuView) HyperSplitActionMenuPresenter.this.mMenuView).hideOverflowSelectedBackground();
            }
        }

        @Override // miuix.appcompat.internal.view.menu.HyperPopupHelper, android.widget.PopupWindow.OnDismissListener
        public void onDismiss() {
            super.onDismiss();
            HyperSplitActionMenuPresenter.this.mMenu.close();
            HyperSplitActionMenuPresenter.this.mOverflowMenu = null;
        }

        @Override // miuix.appcompat.internal.view.menu.action.ActionMenuPresenter.OverflowMenu
        public void update(MenuBuilder menuBuilder) {
        }
    }

    public HyperSplitActionMenuPresenter(Context context, ActionBarOverlayLayout actionBarOverlayLayout, int i, int i2) {
        super(context, actionBarOverlayLayout, i, i2);
        this.mHyperSplitMenuPrimaryCheckedMap = new HashMap();
        this.mHyperSplitMenuSecondaryCheckedMap = new HashMap();
        this.mSavePrimaryStatusByIdEnabled = false;
    }

    public Map getHyperSplitMenuPrimaryCheckedMap() {
        return this.mHyperSplitMenuPrimaryCheckedMap;
    }

    public Map getHyperSplitMenuSecondaryCheckedMap() {
        return this.mHyperSplitMenuSecondaryCheckedMap;
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuPresenter
    public ActionMenuPresenter.OverflowMenu getOverflowMenu() {
        if (!shouldShowPopupOverflow()) {
            return super.getOverflowMenu();
        }
        HyperSplitPopupOverflowMenu hyperSplitPopupOverflowMenu = new HyperSplitPopupOverflowMenu(this.mContext, this.mMenu, this.mOverflowButton, this.mDecorView, true);
        hyperSplitPopupOverflowMenu.restoreHyperMenuPrimaryCheckedData(this.mHyperSplitMenuPrimaryCheckedMap);
        hyperSplitPopupOverflowMenu.restoreHyperMenuSecondaryCheckedData(this.mHyperSplitMenuSecondaryCheckedMap);
        hyperSplitPopupOverflowMenu.setSaveStatusByIdEnabled(this.mSavePrimaryStatusByIdEnabled);
        return hyperSplitPopupOverflowMenu;
    }

    public void setHyperSplitMenuSaveStatusByIdEnabled(boolean z) {
        this.mSavePrimaryStatusByIdEnabled = z;
    }
}
