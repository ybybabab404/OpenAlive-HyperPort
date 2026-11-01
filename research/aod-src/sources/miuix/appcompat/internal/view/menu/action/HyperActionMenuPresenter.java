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
public class HyperActionMenuPresenter extends EndActionMenuPresenter {
    public Map mHyperMenuPrimaryCheckedMap;
    public Map mHyperMenuSecondaryCheckedMap;
    public boolean mSavePrimaryStatusByIdEnabled;

    public class HyperPopupOverflowMenu extends HyperPopupHelper implements ActionMenuPresenter.OverflowMenu {
        public HyperPopupOverflowMenu(Context context, MenuBuilder menuBuilder, View view, View view2, boolean z) {
            super(context, menuBuilder, view, view2, z);
            TypedValue typedValueResolveTypedValue = AttributeResolver.resolveTypedValue(context, R.attr.overflowMenuMaxHeight);
            int dimensionPixelSize = (typedValueResolveTypedValue == null || typedValueResolveTypedValue.type != 5) ? 0 : typedValueResolveTypedValue.resourceId > 0 ? context.getResources().getDimensionPixelSize(typedValueResolveTypedValue.resourceId) : TypedValue.complexToDimensionPixelSize(typedValueResolveTypedValue.data, context.getResources().getDisplayMetrics());
            if (dimensionPixelSize > 0) {
                setPopupMaxHeight(dimensionPixelSize);
            }
            setCallback(HyperActionMenuPresenter.this.mPopupPresenterCallback);
        }

        @Override // miuix.appcompat.internal.view.menu.HyperPopupHelper, miuix.appcompat.internal.view.menu.action.ActionMenuPresenter.OverflowMenu
        public void dismiss(boolean z) {
            super.dismiss(z);
            View view = HyperActionMenuPresenter.this.mOverflowButton;
            if (view != null) {
                view.setSelected(false);
            }
        }

        @Override // miuix.appcompat.internal.view.menu.HyperPopupHelper, android.widget.PopupWindow.OnDismissListener
        public void onDismiss() {
            super.onDismiss();
            HyperActionMenuPresenter.this.mMenu.close();
            HyperActionMenuPresenter.this.mOverflowMenu = null;
        }

        @Override // miuix.appcompat.internal.view.menu.action.ActionMenuPresenter.OverflowMenu
        public void update(MenuBuilder menuBuilder) {
        }
    }

    public HyperActionMenuPresenter(Context context, ActionBarOverlayLayout actionBarOverlayLayout, int i, int i2, int i3, int i4) {
        super(context, actionBarOverlayLayout, i, i2, i3, i4);
        this.mHyperMenuPrimaryCheckedMap = new HashMap();
        this.mHyperMenuSecondaryCheckedMap = new HashMap();
        this.mSavePrimaryStatusByIdEnabled = false;
    }

    public Map getHyperPrimaryCheckedData() {
        return this.mHyperMenuPrimaryCheckedMap;
    }

    public Map getHyperSecondaryCheckedData() {
        return this.mHyperMenuSecondaryCheckedMap;
    }

    @Override // miuix.appcompat.internal.view.menu.action.ActionMenuPresenter
    public ActionMenuPresenter.OverflowMenu getOverflowMenu() {
        if (!shouldShowPopupOverflow()) {
            return super.getOverflowMenu();
        }
        HyperPopupOverflowMenu hyperPopupOverflowMenu = new HyperPopupOverflowMenu(this.mContext, this.mMenu, this.mOverflowButton, this.mDecorView, true);
        hyperPopupOverflowMenu.restoreHyperMenuPrimaryCheckedData(this.mHyperMenuPrimaryCheckedMap);
        hyperPopupOverflowMenu.restoreHyperMenuSecondaryCheckedData(this.mHyperMenuSecondaryCheckedMap);
        hyperPopupOverflowMenu.setSaveStatusByIdEnabled(this.mSavePrimaryStatusByIdEnabled);
        return hyperPopupOverflowMenu;
    }

    public void setHyperMenuSaveStatusByIdEnabled(boolean z) {
        this.mSavePrimaryStatusByIdEnabled = z;
    }
}
