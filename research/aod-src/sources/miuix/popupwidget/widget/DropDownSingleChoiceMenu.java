package miuix.popupwidget.widget;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import miuix.popupwidget.R;

/* JADX INFO: loaded from: classes2.dex */
public class DropDownSingleChoiceMenu {
    public OnMenuListener mListener;
    public PopupWindow mPopupWindow;
    public int mSelectedItem;

    /* JADX INFO: renamed from: miuix.popupwidget.widget.DropDownSingleChoiceMenu$1, reason: invalid class name */
    class AnonymousClass1 implements AdapterView.OnItemClickListener {
        public final /* synthetic */ DropDownSingleChoiceMenu this$0;

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView adapterView, View view, int i, long j) {
            this.this$0.mSelectedItem = i;
            if (this.this$0.mListener != null) {
                this.this$0.mListener.onItemSelected(this.this$0, i);
            }
            this.this$0.dismiss();
        }
    }

    /* JADX INFO: renamed from: miuix.popupwidget.widget.DropDownSingleChoiceMenu$2, reason: invalid class name */
    class AnonymousClass2 implements android.widget.PopupWindow.OnDismissListener {
        public final /* synthetic */ DropDownSingleChoiceMenu this$0;

        @Override // android.widget.PopupWindow.OnDismissListener
        public void onDismiss() {
            this.this$0.internalDismiss();
        }
    }

    /* JADX INFO: renamed from: miuix.popupwidget.widget.DropDownSingleChoiceMenu$3, reason: invalid class name */
    class AnonymousClass3 extends View.AccessibilityDelegate {
        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view, accessibilityEvent);
            accessibilityEvent.setClassName(Spinner.class.getName());
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            accessibilityNodeInfo.setClassName(Spinner.class.getName());
        }
    }

    public interface OnMenuListener {
        void onDismiss();

        void onItemSelected(DropDownSingleChoiceMenu dropDownSingleChoiceMenu, int i);
    }

    public static class SingleChoiceMenuAdapter extends ArrayAdapter<String> {
        public int mSelectedItem;

        public static /* synthetic */ boolean $r8$lambda$SIvgZdEsE4tGzGPK4bUrFbT8sso(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == 9) {
                view.setHovered(true);
                return false;
            }
            if (motionEvent.getAction() == 10) {
                view.setHovered(false);
            }
            return false;
        }

        @Override // android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int i, View view, ViewGroup viewGroup) {
            View view2 = super.getView(i, view, viewGroup);
            view2.setActivated(i == this.mSelectedItem);
            if (view == null) {
                view2.setForeground(view2.getContext().getDrawable(R.drawable.miuix_popup_window_list_item_fg));
                if (!view2.isClickable()) {
                    view2.setOnHoverListener(new View.OnHoverListener() { // from class: miuix.popupwidget.widget.DropDownSingleChoiceMenu$SingleChoiceMenuAdapter$$ExternalSyntheticLambda0
                        @Override // android.view.View.OnHoverListener
                        public final boolean onHover(View view3, MotionEvent motionEvent) {
                            return DropDownSingleChoiceMenu.SingleChoiceMenuAdapter.$r8$lambda$SIvgZdEsE4tGzGPK4bUrFbT8sso(view3, motionEvent);
                        }
                    });
                }
            }
            return view2;
        }
    }

    public void dismiss() {
        PopupWindow popupWindow = this.mPopupWindow;
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    public final void internalDismiss() {
        OnMenuListener onMenuListener = this.mListener;
        if (onMenuListener != null) {
            onMenuListener.onDismiss();
        }
        this.mPopupWindow = null;
    }
}
