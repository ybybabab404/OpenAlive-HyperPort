package miuix.popupwidget.internal.strategy;

import miuix.popupwidget.R;

/* JADX INFO: loaded from: classes2.dex */
public interface IPopupWindowStrategy {
    default int getSafeMarginResId() {
        return R.dimen.miuix_popup_window_safe_margin;
    }

    int getXInWindow(PopupWindowSpec popupWindowSpec);

    int getYInWindow(PopupWindowSpec popupWindowSpec);

    boolean isNeedScroll(int i, PopupWindowSpec popupWindowSpec);

    void measureContentSize(PopupWindowSpec popupWindowSpec);
}
