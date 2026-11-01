package miuix.appcompat.internal.view.menu.context;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.util.EventLog;
import android.view.ContextMenu;
import android.view.View;
import com.miui.keyguard.editor.data.bean.MagicType;
import miuix.appcompat.internal.view.menu.MenuBuilder;
import miuix.appcompat.internal.view.menu.MenuDialogHelper;

/* JADX INFO: loaded from: classes2.dex */
public class ContextMenuBuilder extends MenuBuilder implements ContextMenu {
    public ContextMenuPopupWindowHelper mHelper;

    public ContextMenuBuilder(Context context) {
        super(context);
    }

    @Override // miuix.appcompat.internal.view.menu.MenuBuilder, android.view.Menu
    public void close() {
        super.close();
        ContextMenuPopupWindowHelper contextMenuPopupWindowHelper = this.mHelper;
        if (contextMenuPopupWindowHelper != null) {
            contextMenuPopupWindowHelper.dismiss();
            this.mHelper = null;
        }
    }

    public boolean isContextMenuPopupWindowShowing() {
        if (this.mHelper == null) {
            return false;
        }
        return this.mHelper.getContextMenuPopupWindow().isShowing();
    }

    public void refreshContextMenuPopupWindow() {
        if (this.mHelper == null) {
            return;
        }
        this.mHelper.refreshContextMenuPopupWindow();
    }

    @Override // android.view.ContextMenu
    public ContextMenu setHeaderIcon(int i) {
        return (ContextMenu) super.setHeaderIconInt(i);
    }

    @Override // android.view.ContextMenu
    public ContextMenu setHeaderIcon(Drawable drawable) {
        return (ContextMenu) super.setHeaderIconInt(drawable);
    }

    @Override // android.view.ContextMenu
    public ContextMenu setHeaderTitle(int i) {
        return (ContextMenu) super.setHeaderTitleInt(i);
    }

    @Override // android.view.ContextMenu
    public ContextMenu setHeaderTitle(CharSequence charSequence) {
        return (ContextMenu) super.setHeaderTitleInt(charSequence);
    }

    @Override // android.view.ContextMenu
    public ContextMenu setHeaderView(View view) {
        return (ContextMenu) super.setHeaderViewInt(view);
    }

    public MenuDialogHelper show(View view, IBinder iBinder) {
        if (view != null) {
            view.createContextMenu(this);
        }
        if (getVisibleItems().size() <= 0) {
            return null;
        }
        EventLog.writeEvent(MagicType.TYPE_FRONT_BACK_1, 1);
        MenuDialogHelper menuDialogHelper = new MenuDialogHelper(this);
        menuDialogHelper.show(iBinder);
        return menuDialogHelper;
    }

    public ContextMenuPopupWindowHelper show(View view, IBinder iBinder, float f, float f2) {
        view.createContextMenu(this);
        if (getVisibleItems().size() <= 0) {
            return null;
        }
        EventLog.writeEvent(MagicType.TYPE_FRONT_BACK_1, 1);
        ContextMenuPopupWindowHelper contextMenuPopupWindowHelper = new ContextMenuPopupWindowHelper(this);
        this.mHelper = contextMenuPopupWindowHelper;
        contextMenuPopupWindowHelper.show(iBinder, view, f, f2);
        return this.mHelper;
    }
}
