package miuix.internal.widget;

import android.view.View;
import miuix.popupwidget.widget.PopupWindow;

/* JADX INFO: loaded from: classes2.dex */
public class PopupMenuWindow extends PopupWindow implements IPopupMenuWidget {
    public View mLastAnchor;

    @Override // miuix.popupwidget.widget.PopupWindow, android.widget.PopupWindow
    public void showAsDropDown(View view) {
        this.mLastAnchor = view;
        if (prepareShow(view)) {
            super.showAsDropDown(view);
        }
    }

    @Override // miuix.popupwidget.widget.PopupWindow, android.widget.PopupWindow
    public void showAtLocation(View view, int i, int i2, int i3) {
        if (prepareShow(view)) {
            super.showAtLocation(view.getRootView(), i, i2, i3);
        }
    }
}
