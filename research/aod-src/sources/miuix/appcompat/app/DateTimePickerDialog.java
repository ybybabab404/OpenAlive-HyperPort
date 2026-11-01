package miuix.appcompat.app;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import miuix.pickerwidget.widget.DateTimePicker;
import miuix.slidingwidget.widget.SlidingButton;

/* JADX INFO: loaded from: classes2.dex */
public class DateTimePickerDialog extends AlertDialog {
    public SlidingButton mLunarModeState;
    public OnTimeSetListener mTimeListener;
    public DateTimePicker mTimePicker;

    /* JADX INFO: renamed from: miuix.appcompat.app.DateTimePickerDialog$1, reason: invalid class name */
    class AnonymousClass1 implements DialogInterface.OnClickListener {
        public final /* synthetic */ DateTimePickerDialog this$0;

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            if (this.this$0.mTimeListener != null) {
                OnTimeSetListener onTimeSetListener = this.this$0.mTimeListener;
                DateTimePickerDialog dateTimePickerDialog = this.this$0;
                onTimeSetListener.onTimeSet(dateTimePickerDialog, dateTimePickerDialog.mTimePicker.getTimeInMillis());
            }
        }
    }

    /* JADX INFO: renamed from: miuix.appcompat.app.DateTimePickerDialog$2, reason: invalid class name */
    class AnonymousClass2 extends AccessibilityDelegateCompat {
        public final /* synthetic */ DateTimePickerDialog this$0;

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.setCheckable(true);
            accessibilityNodeInfoCompat.setClickable(true);
            accessibilityNodeInfoCompat.setClassName(Switch.class.getName());
            if (this.this$0.mLunarModeState != null) {
                accessibilityNodeInfoCompat.setChecked(this.this$0.mLunarModeState.isChecked());
                accessibilityNodeInfoCompat.setContentDescription(this.this$0.mLunarModeState.getContentDescription());
            }
        }
    }

    public interface OnTimeSetListener {
        void onTimeSet(DateTimePickerDialog dateTimePickerDialog, long j);
    }

    @Override // android.app.Dialog
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
    }
}
