package miuix.appcompat.internal.view.menu.action;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import miuix.appcompat.R;
import miuix.appcompat.internal.view.menu.MenuBuilder;
import miuix.appcompat.internal.view.menu.MenuItemImpl;
import miuix.appcompat.internal.view.menu.MenuView;
import miuix.core.util.MiuixUIUtils;

/* JADX INFO: loaded from: classes2.dex */
public class ActionMenuItemView extends LinearLayout implements MenuView.ItemView {
    public final ActionMenuItemViewChildren mChildren;
    public boolean mIsCheckable;
    public MenuItemImpl mItemData;
    public MenuBuilder.ItemInvoker mItemInvoker;
    public boolean mResponsiveViewSuspendEnabled;
    public Rect mTextPaddings;
    public boolean mTextVerticalTruncatedDealEnabled;

    public ActionMenuItemView(Context context) {
        this(context, null, 0);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActionMenuItemView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        boolean z = false;
        this.mTextVerticalTruncatedDealEnabled = false;
        this.mTextPaddings = new Rect(0, 0, 0, 0);
        this.mResponsiveViewSuspendEnabled = false;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ActionMenuItemView, R.attr.actionButtonStyle, 0);
        if (typedArrayObtainStyledAttributes.getBoolean(R.styleable.ActionMenuItemView_largeFontAdaptationEnabled, true) && MiuixUIUtils.getFontLevel(context) == 2) {
            z = true;
        }
        typedArrayObtainStyledAttributes.recycle();
        ActionMenuItemViewChildren actionMenuItemViewChildren = new ActionMenuItemViewChildren(this);
        this.mChildren = actionMenuItemViewChildren;
        actionMenuItemViewChildren.setLargeFontEnabled(z);
    }

    @Override // miuix.appcompat.internal.view.menu.MenuView.ItemView
    public MenuItemImpl getItemData() {
        return this.mItemData;
    }

    @Override // miuix.appcompat.internal.view.menu.MenuView.ItemView
    public void initialize(MenuItemImpl menuItemImpl, int i) {
        this.mItemData = menuItemImpl;
        setSelected(false);
        setTitle(menuItemImpl.getTitle());
        setIcon(menuItemImpl.getIcon());
        setCheckable(menuItemImpl.isCheckable());
        setChecked(menuItemImpl.isChecked());
        setEnabled(menuItemImpl.isEnabled());
        setClickable(true);
        this.mChildren.setContentDescription(menuItemImpl.getContentDescription());
    }

    public boolean isVerticalTextTruncated() {
        ActionMenuItemViewChildren actionMenuItemViewChildren = this.mChildren;
        return actionMenuItemViewChildren != null && actionMenuItemViewChildren.adjustTextVerticalTruncated();
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mChildren.onConfigurationChanged(configuration);
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (this.mTextVerticalTruncatedDealEnabled) {
            this.mChildren.handleVerticalTextTruncated(this.mTextPaddings);
        }
        if (!this.mResponsiveViewSuspendEnabled) {
            this.mChildren.resetTextSizeAndPaddings(this.mTextPaddings);
        }
        this.mChildren.updateTouchRadius();
    }

    @Override // android.view.View
    public boolean performClick() {
        if (super.performClick()) {
            return true;
        }
        MenuBuilder.ItemInvoker itemInvoker = this.mItemInvoker;
        if (itemInvoker == null || !itemInvoker.invokeItem(this.mItemData, 0)) {
            return false;
        }
        playSoundEffect(0);
        return true;
    }

    @Override // miuix.appcompat.internal.view.menu.MenuView.ItemView
    public boolean prefersCondensedTitle() {
        return false;
    }

    public void setCheckable(boolean z) {
        this.mIsCheckable = z;
    }

    public void setChecked(boolean z) {
        if (this.mIsCheckable) {
            setSelected(z);
        }
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        super.setEnabled(z);
        this.mChildren.setEnabled(z);
    }

    public void setIcon(Drawable drawable) {
        this.mChildren.setIcon(drawable);
    }

    @Override // miuix.appcompat.internal.view.menu.MenuView.ItemView
    public void setItemInvoker(MenuBuilder.ItemInvoker itemInvoker) {
        this.mItemInvoker = itemInvoker;
    }

    public void setResponsiveViewSuspendEnabled(boolean z) {
        this.mResponsiveViewSuspendEnabled = z;
    }

    public void setShortcut(boolean z, char c) {
    }

    public void setTextPaddings(Rect rect) {
        if (rect == null) {
            return;
        }
        this.mTextPaddings = rect;
    }

    public void setTextVerticalTruncatedDealEnabled(boolean z) {
        this.mTextVerticalTruncatedDealEnabled = z;
    }

    public void setTitle(CharSequence charSequence) {
        this.mChildren.setText(charSequence);
    }

    public void setUseBackgroundPressEffect(boolean z) {
        this.mChildren.setUseBackgroundPressEffect(z);
    }
}
