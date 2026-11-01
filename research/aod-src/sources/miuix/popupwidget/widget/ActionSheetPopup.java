package miuix.popupwidget.widget;

import androidx.core.view.AccessibilityDelegateCompat;

/* JADX INFO: loaded from: classes2.dex */
public class ActionSheetPopup {

    public interface AccessibilityDelegateProvider {
        AccessibilityDelegateCompat getAccessibilityDelegate();
    }

    public enum ActionSheetItemType {
        PRIMARY_ITEM,
        ERROR_ITEM
    }

    public interface ActionSheetPopListener {
    }

    public interface OnItemClickListener {
        void onItemClick(int i);
    }
}
