package miuix.appcompat.internal.view.menu.context;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewParent;
import android.widget.PopupWindow;
import miuix.appcompat.R;
import miuix.appcompat.internal.util.MenuBlurUtils;
import miuix.appcompat.internal.view.menu.MenuBuilder;
import miuix.appcompat.widget.HyperPopupWindow;
import miuix.popupwidget.internal.strategy.IPopupWindowStrategy;
import miuix.view.Fence;

/* JADX INFO: loaded from: classes2.dex */
public class HyperContextMenuImpl {
    public HyperContextMenuAdapter mAdapter;
    public final View mAnchor;
    public final Context mContext;
    public final HyperPopupWindow mHyperPopupWindow;
    public final MenuBuilder mMenu;

    public HyperContextMenuImpl(Context context, View view) {
        this(context, view, 0);
    }

    public HyperContextMenuImpl(Context context, View view, int i) {
        if (i == 0) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(null, R.styleable.miuiPopupMenu, R.attr.miuiPopupMenuStyle, 0);
            try {
                int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.miuiPopupMenu_miuiPopupTheme, 0);
                typedArrayObtainStyledAttributes.recycle();
                i = resourceId;
            } catch (Throwable th) {
                typedArrayObtainStyledAttributes.recycle();
                throw th;
            }
        }
        if (i != 0) {
            this.mContext = new ContextThemeWrapper(context, i);
        } else {
            this.mContext = context;
        }
        this.mAnchor = view;
        this.mMenu = new MenuBuilder(this.mContext);
        HyperPopupWindow hyperPopupWindow = new HyperPopupWindow(context);
        this.mHyperPopupWindow = hyperPopupWindow;
        hyperPopupWindow.setWindowAnimationEnabled(false);
        hyperPopupWindow.setClippingEnabled(true);
        hyperPopupWindow.setMaterialEnabled(MenuBlurUtils.isMenuBlurOptionEnabled(this.mContext, 2));
        setAutoDismiss(false);
        setWindowAnimationEnabled(true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static View getFenceView(View view) {
        for (ViewParent parent = view.getParent(); parent != 0; parent = parent.getParent()) {
            if ((parent instanceof Fence) && ((Fence) parent).isFenceEnabled() && (parent instanceof View)) {
                return (View) parent;
            }
        }
        return null;
    }

    public void dismiss() {
        this.mHyperPopupWindow.dismiss();
    }

    public void inflate(MenuBuilder menuBuilder) {
        HyperContextMenuAdapter hyperContextMenuAdapter = new HyperContextMenuAdapter(this.mContext);
        this.mAdapter = hyperContextMenuAdapter;
        hyperContextMenuAdapter.update(menuBuilder);
    }

    public boolean isShowing() {
        return this.mHyperPopupWindow.isShowing();
    }

    public void setAutoDismiss(boolean z) {
        this.mHyperPopupWindow.setAutoDismiss(z);
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mHyperPopupWindow.setOnDismissListener(onDismissListener);
    }

    public void setOnMenuItemClickListener(HyperPopupWindow.OnMenuItemClickListener onMenuItemClickListener) {
        this.mHyperPopupWindow.setOnMenuItemClickListener(onMenuItemClickListener);
    }

    public void setPopupWindowStrategy(IPopupWindowStrategy iPopupWindowStrategy) {
        this.mHyperPopupWindow.setPopupWindowStrategy(iPopupWindowStrategy);
    }

    public void setWindowAnimationEnabled(boolean z) {
        this.mHyperPopupWindow.setWindowAnimationEnabled(z);
    }

    public void show() {
        this.mHyperPopupWindow.setSecondaryMenuEnabled(this.mAdapter.hasSubMenu());
        this.mHyperPopupWindow.setAdapter(this.mAdapter);
        View fenceView = getFenceView(this.mAnchor);
        if (fenceView != null) {
            this.mHyperPopupWindow.setDecorView(fenceView);
        }
        this.mHyperPopupWindow.show(this.mAnchor);
    }

    public void update() {
        this.mHyperPopupWindow.update();
    }
}
