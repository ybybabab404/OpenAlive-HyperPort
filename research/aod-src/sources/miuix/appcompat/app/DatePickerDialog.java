package miuix.appcompat.app;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import miuix.pickerwidget.date.Calendar;
import miuix.pickerwidget.date.DateUtils;
import miuix.pickerwidget.widget.DatePicker;
import miuix.slidingwidget.widget.SlidingButton;

/* JADX INFO: loaded from: classes2.dex */
public class DatePickerDialog extends AlertDialog {
    public final Calendar mCalendar;
    public final OnDateSetListener mCallBack;
    public final DatePicker mDatePicker;
    public SlidingButton mLunarModeState;
    public boolean mTitleNeedsUpdate;

    /* JADX INFO: renamed from: miuix.appcompat.app.DatePickerDialog$1, reason: invalid class name */
    public class AnonymousClass1 implements DatePicker.OnDateChangedListener {
        public final /* synthetic */ DatePickerDialog this$0;

        @Override // miuix.pickerwidget.widget.DatePicker.OnDateChangedListener
        public void onDateChanged(DatePicker datePicker, int i, int i2, int i3, boolean z) {
            if (this.this$0.mTitleNeedsUpdate) {
                this.this$0.updateTitle(i, i2, i3);
            }
        }
    }

    /* JADX INFO: renamed from: miuix.appcompat.app.DatePickerDialog$2, reason: invalid class name */
    class AnonymousClass2 implements DialogInterface.OnClickListener {
        public final /* synthetic */ DatePickerDialog this$0;

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            this.this$0.tryNotifyDateSet();
        }
    }

    /* JADX INFO: renamed from: miuix.appcompat.app.DatePickerDialog$3, reason: invalid class name */
    class AnonymousClass3 extends AccessibilityDelegateCompat {
        public final /* synthetic */ DatePickerDialog this$0;

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

    public interface OnDateSetListener {
        void onDateSet(DatePicker datePicker, int i, int i2, int i3);
    }

    public static class SelectorWheelAppearance extends DatePicker.SelectorWheelAppearance {
    }

    @Override // android.app.Dialog
    public void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
        DatePicker datePicker = this.mDatePicker;
        if (datePicker != null) {
            updateTitle(datePicker.getYear(), this.mDatePicker.getMonth(), this.mDatePicker.getDayOfMonth());
        }
    }

    @Override // androidx.appcompat.app.AppCompatDialog, android.app.Dialog
    public void setTitle(int i) {
        super.setTitle(i);
        this.mTitleNeedsUpdate = false;
    }

    @Override // miuix.appcompat.app.AlertDialog, androidx.appcompat.app.AppCompatDialog, android.app.Dialog
    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        this.mTitleNeedsUpdate = false;
    }

    public final void tryNotifyDateSet() {
        if (this.mCallBack != null) {
            this.mDatePicker.clearFocus();
            OnDateSetListener onDateSetListener = this.mCallBack;
            DatePicker datePicker = this.mDatePicker;
            onDateSetListener.onDateSet(datePicker, datePicker.getYear(), this.mDatePicker.getMonth(), this.mDatePicker.getDayOfMonth());
        }
    }

    public final void updateTitle(int i, int i2, int i3) {
        this.mCalendar.set(1, i);
        this.mCalendar.set(5, i2);
        this.mCalendar.set(9, i3);
        super.setTitle(DateUtils.formatDateTime(getContext(), this.mCalendar.getTimeInMillis(), 14208));
    }
}
