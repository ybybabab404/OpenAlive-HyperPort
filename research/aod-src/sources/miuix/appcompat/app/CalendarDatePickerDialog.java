package miuix.appcompat.app;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.text.format.DateFormat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.RecyclerView;
import miuix.appcompat.R;
import miuix.appcompat.internal.widget.DialogParentPanel2;
import miuix.appcompat.widget.Spinner;
import miuix.core.widget.NestedScrollView;
import miuix.pickerwidget.date.Calendar;
import miuix.pickerwidget.widget.Calendar.CalendarDatePicker;
import miuix.pickerwidget.widget.TimePicker;
import miuix.slidingwidget.widget.SlidingButton;

/* JADX INFO: loaded from: classes2.dex */
public class CalendarDatePickerDialog extends AlertDialog {
    public Calendar mCalendar;
    public CalendarDatePicker mCalendarDatePicker;
    public NestedScrollView mContentView;
    public Drawable mCustomPositiveButtonBg;
    public View mDateTimePanel;
    public TextView mDateView;
    public DialogParentPanel2 mDialogParentPanel;
    public View mDivider;
    public int mDividerMarginTop;
    public View mLunarModePanel;
    public SlidingButton mLunarModeSwitchButton;
    public CalendarDatePickerDialogCallBack mOnDataChangeCallback;
    public OnDataChangeListener mOnDataChangeListener;
    public CalendarDatePickerDialogCallBack mOnDataConfirmCallback;
    public OnDataConfirmListener mOnDataConfirmListener;
    public Spinner mSpinner;
    public CharSequence[] mSpinnerEntries;
    public View mSpinnerPanel;
    public TextView mSpinnerPanelTitleView;
    public int mSpinnerPosition;
    public TimePicker mTimePicker;
    public TextView mTimeView;

    /* JADX INFO: renamed from: miuix.appcompat.app.CalendarDatePickerDialog$1, reason: invalid class name */
    class AnonymousClass1 implements CalendarDatePicker.OnDateChangedListener {
        public final /* synthetic */ CalendarDatePickerDialog this$0;

        @Override // miuix.pickerwidget.widget.Calendar.CalendarDatePicker.OnDateChangedListener
        public void onDateChanged(CalendarDatePicker calendarDatePicker, int i, int i2, int i3, String str) {
            if (this.this$0.mOnDataConfirmCallback != null) {
                this.this$0.mOnDataConfirmCallback.setDate(i, i2, i3);
                this.this$0.mOnDataConfirmCallback.setLunarMessage(str);
            }
            if (this.this$0.mOnDataChangeCallback != null) {
                this.this$0.mOnDataChangeCallback.setDate(i, i2, i3);
                this.this$0.mOnDataChangeCallback.setLunarMessage(str);
            }
            this.this$0.onDataChange();
            this.this$0.updateDateView();
        }
    }

    /* JADX INFO: renamed from: miuix.appcompat.app.CalendarDatePickerDialog$10, reason: invalid class name */
    class AnonymousClass10 implements DialogInterface.OnClickListener {
        public final /* synthetic */ CalendarDatePickerDialog this$0;

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            if (i == -1) {
                this.this$0.onDataConfirm();
            }
        }
    }

    /* JADX INFO: renamed from: miuix.appcompat.app.CalendarDatePickerDialog$11, reason: invalid class name */
    class AnonymousClass11 implements DialogInterface.OnClickListener {
        public final /* synthetic */ CalendarDatePickerDialog this$0;
        public final /* synthetic */ DialogInterface.OnClickListener val$listener;

        @Override // android.content.DialogInterface.OnClickListener
        public void onClick(DialogInterface dialogInterface, int i) {
            DialogInterface.OnClickListener onClickListener = this.val$listener;
            if (onClickListener != null) {
                onClickListener.onClick(dialogInterface, i);
            }
            if (i == -1) {
                this.this$0.onDataConfirm();
            }
        }
    }

    /* JADX INFO: renamed from: miuix.appcompat.app.CalendarDatePickerDialog$2, reason: invalid class name */
    class AnonymousClass2 extends RecyclerView.OnScrollListener {
        public final /* synthetic */ CalendarDatePickerDialog this$0;
        public final /* synthetic */ boolean val$originalCanceledOnTouchOutside;

        @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
            this.this$0.setCanceledOnTouchOutside(i == 0 ? this.val$originalCanceledOnTouchOutside : false);
        }
    }

    /* JADX INFO: renamed from: miuix.appcompat.app.CalendarDatePickerDialog$3, reason: invalid class name */
    class AnonymousClass3 implements CompoundButton.OnCheckedChangeListener {
        public final /* synthetic */ CalendarDatePickerDialog this$0;

        @Override // android.widget.CompoundButton.OnCheckedChangeListener
        public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
            this.this$0.mCalendarDatePicker.setLunarMode(z);
            this.this$0.onDataChange();
        }
    }

    /* JADX INFO: renamed from: miuix.appcompat.app.CalendarDatePickerDialog$4, reason: invalid class name */
    class AnonymousClass4 extends AccessibilityDelegateCompat {
        public final /* synthetic */ CalendarDatePickerDialog this$0;

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.setCheckable(true);
            accessibilityNodeInfoCompat.setClickable(true);
            accessibilityNodeInfoCompat.setClassName(Switch.class.getName());
            if (this.this$0.mLunarModeSwitchButton != null) {
                accessibilityNodeInfoCompat.setChecked(this.this$0.mLunarModeSwitchButton.isChecked());
                accessibilityNodeInfoCompat.setContentDescription(this.this$0.mLunarModeSwitchButton.getContentDescription());
            }
        }
    }

    /* JADX INFO: renamed from: miuix.appcompat.app.CalendarDatePickerDialog$5, reason: invalid class name */
    class AnonymousClass5 implements TimePicker.OnTimeChangedListener {
        public final /* synthetic */ CalendarDatePickerDialog this$0;

        @Override // miuix.pickerwidget.widget.TimePicker.OnTimeChangedListener
        public void onTimeChanged(TimePicker timePicker, int i, int i2) {
            if (this.this$0.mOnDataConfirmCallback != null) {
                this.this$0.mOnDataConfirmCallback.setTime(i, i2);
            }
            if (this.this$0.mOnDataChangeCallback != null) {
                this.this$0.mOnDataChangeCallback.setTime(i, i2);
            }
            this.this$0.onDataChange();
            this.this$0.updateTimeView();
        }
    }

    /* JADX INFO: renamed from: miuix.appcompat.app.CalendarDatePickerDialog$6, reason: invalid class name */
    class AnonymousClass6 implements AdapterView.OnItemClickListener {
        public final /* synthetic */ CalendarDatePickerDialog this$0;

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView adapterView, View view, int i, long j) {
            if (this.this$0.mOnDataConfirmCallback != null) {
                this.this$0.mOnDataConfirmCallback.setSpinnerSelection(i);
            }
            if (this.this$0.mOnDataChangeCallback != null) {
                this.this$0.mOnDataChangeCallback.setSpinnerSelection(i);
            }
            this.this$0.mSpinnerPosition = i;
            this.this$0.onDataChange();
        }
    }

    /* JADX INFO: renamed from: miuix.appcompat.app.CalendarDatePickerDialog$7, reason: invalid class name */
    class AnonymousClass7 extends AccessibilityDelegateCompat {
        public final /* synthetic */ CalendarDatePickerDialog this$0;

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.setClickable(true);
            accessibilityNodeInfoCompat.setClassName(android.widget.Spinner.class.getName());
            if (this.this$0.mSpinner == null || this.this$0.mSpinnerPanelTitleView == null) {
                return;
            }
            accessibilityNodeInfoCompat.setContentDescription(this.this$0.mSpinnerPanelTitleView.getText().toString() + this.this$0.mSpinnerEntries[this.this$0.mSpinnerPosition].toString());
        }
    }

    /* JADX INFO: renamed from: miuix.appcompat.app.CalendarDatePickerDialog$8, reason: invalid class name */
    class AnonymousClass8 implements View.OnClickListener {
        public final /* synthetic */ CalendarDatePickerDialog this$0;

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            this.this$0.mDateView.setSelected(true);
            this.this$0.mTimeView.setSelected(false);
            this.this$0.mCalendarDatePicker.setVisibility(0);
            this.this$0.mTimePicker.setVisibility(8);
        }
    }

    /* JADX INFO: renamed from: miuix.appcompat.app.CalendarDatePickerDialog$9, reason: invalid class name */
    class AnonymousClass9 implements View.OnClickListener {
        public final /* synthetic */ CalendarDatePickerDialog this$0;

        @Override // android.view.View.OnClickListener
        public void onClick(View view) {
            this.this$0.mDateView.setSelected(false);
            this.this$0.mTimeView.setSelected(true);
            this.this$0.mCalendarDatePicker.setVisibility(8);
            this.this$0.mTimePicker.setVisibility(0);
        }
    }

    public interface OnDataChangeListener {
        void onDataChange(int i, int i2, int i3, int i4, int i5, int i6, String str);
    }

    public interface OnDataConfirmListener {
        void onDataConfirm(int i, int i2, int i3, int i4, int i5, int i6, String str);
    }

    /* JADX INFO: renamed from: $r8$lambda$9Ja9TXjsxlRMTPLdLFHAdoP-QO8, reason: not valid java name */
    public static /* synthetic */ void m1299$r8$lambda$9Ja9TXjsxlRMTPLdLFHAdoPQO8(CalendarDatePickerDialog calendarDatePickerDialog) {
        int measuredHeight;
        int measuredHeight2 = calendarDatePickerDialog.mDialogParentPanel.getMeasuredHeight();
        int panelMaxLimitHeight = calendarDatePickerDialog.mDialogParentPanel.getPanelMaxLimitHeight();
        View childAt = calendarDatePickerDialog.mContentView.getChildAt(0);
        if (childAt != null && (measuredHeight = childAt.getMeasuredHeight() - calendarDatePickerDialog.mContentView.getMeasuredHeight()) > 0) {
            calendarDatePickerDialog.mDialogParentPanel.setPanelFixedHeight(Math.min(measuredHeight2 + measuredHeight, panelMaxLimitHeight));
            calendarDatePickerDialog.mDialogParentPanel.requestLayout();
        }
    }

    public static String formatDate(Context context, int i, int i2, int i3) {
        char[] dateFormatOrder = DateFormat.getDateFormatOrder(context);
        StringBuilder sb = new StringBuilder(10);
        for (int i4 = 0; i4 < dateFormatOrder.length; i4++) {
            if (i4 > 0) {
                sb.append('/');
            }
            char c = dateFormatOrder[i4];
            if (c == 'M') {
                sb.append(String.format("%02d", Integer.valueOf(i2 + 1)));
            } else if (c == 'd') {
                sb.append(String.format("%02d", Integer.valueOf(i3)));
            } else if (c == 'y') {
                sb.append(String.format("%04d", Integer.valueOf(i)));
            }
        }
        return sb.toString();
    }

    public final void applyCustomPositiveButtonBg() {
        Button button = getButton(-1);
        if (this.mCustomPositiveButtonBg == null || button == null) {
            return;
        }
        Drawable background = button.getBackground();
        Drawable drawable = this.mCustomPositiveButtonBg;
        if (background != drawable) {
            button.setBackground(drawable);
        }
    }

    public final void determineDividerMarginTop() {
        ViewGroup.LayoutParams layoutParams = this.mDivider.getLayoutParams();
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
            marginLayoutParams.topMargin = (this.mLunarModePanel == null && this.mSpinnerPanel == null && this.mDateTimePanel == null) ? 0 : this.mDividerMarginTop;
            this.mDivider.setLayoutParams(marginLayoutParams);
        }
    }

    public CalendarDatePicker getCalendarDatePicker() {
        return this.mCalendarDatePicker;
    }

    public int getDayOfMonth() {
        return getCalendarDatePicker().getDayOfMonth();
    }

    public int getHourOfDay() {
        return getTimePicker() != null ? getTimePicker().getCurrentHour().intValue() : this.mCalendar.get(18);
    }

    public int getMinute() {
        return getTimePicker() != null ? getTimePicker().getCurrentMinute().intValue() : this.mCalendar.get(20);
    }

    public int getMonth() {
        return getCalendarDatePicker().getMonth();
    }

    public TimePicker getTimePicker() {
        return this.mTimePicker;
    }

    public int getYear() {
        return getCalendarDatePicker().getYear();
    }

    @Override // miuix.appcompat.app.AlertDialog, android.app.Dialog, android.view.Window.Callback
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mDialogParentPanel == null) {
            DialogParentPanel2 dialogParentPanel2 = (DialogParentPanel2) findViewById(R.id.parentPanel);
            this.mDialogParentPanel = dialogParentPanel2;
            dialogParentPanel2.setFenceEnabled(true);
        }
    }

    public final void onDataChange() {
        if (this.mOnDataChangeListener == null || this.mOnDataChangeCallback == null) {
            return;
        }
        SlidingButton slidingButton = this.mLunarModeSwitchButton;
        this.mOnDataChangeListener.onDataChange(this.mOnDataChangeCallback.getYear(), this.mOnDataChangeCallback.getMonth(), this.mOnDataChangeCallback.getDayOfMonth(), this.mOnDataChangeCallback.getHourOfDay(), this.mOnDataChangeCallback.getMinute(), this.mOnDataChangeCallback.getSpinnerSelection(), slidingButton != null && slidingButton.isChecked() ? this.mOnDataChangeCallback.getLunarMessage() : null);
    }

    public final void onDataConfirm() {
        if (this.mOnDataConfirmListener == null || this.mOnDataConfirmCallback == null) {
            return;
        }
        SlidingButton slidingButton = this.mLunarModeSwitchButton;
        this.mOnDataConfirmListener.onDataConfirm(this.mOnDataConfirmCallback.getYear(), this.mOnDataConfirmCallback.getMonth(), this.mOnDataConfirmCallback.getDayOfMonth(), this.mOnDataConfirmCallback.getHourOfDay(), this.mOnDataConfirmCallback.getMinute(), this.mOnDataConfirmCallback.getSpinnerSelection(), slidingButton != null && slidingButton.isChecked() ? this.mOnDataConfirmCallback.getLunarMessage() : null);
    }

    public final void postCalculateDialogHeight() {
        this.mCalendarDatePicker.post(new Runnable() { // from class: miuix.appcompat.app.CalendarDatePickerDialog$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CalendarDatePickerDialog.m1299$r8$lambda$9Ja9TXjsxlRMTPLdLFHAdoPQO8(this.f$0);
            }
        });
    }

    @Override // miuix.appcompat.app.AlertDialog, android.app.Dialog
    public void show() {
        super.show();
        onDataChange();
        applyCustomPositiveButtonBg();
        postCalculateDialogHeight();
        determineDividerMarginTop();
    }

    public final void updateDateView() {
        if (this.mDateView != null) {
            int year = getYear();
            int month = getMonth();
            this.mDateView.setText(formatDate(getContext(), year, month, Math.min(getDayOfMonth(), this.mCalendar.daysInMonth(year, month))));
        }
    }

    public final void updateTimeView() {
        if (this.mTimeView != null) {
            String string = Integer.toString(getHourOfDay());
            String string2 = Integer.toString(getMinute());
            if (string.length() == 1) {
                string = "0" + string;
            }
            if (string2.length() == 1) {
                string2 = "0" + string2;
            }
            this.mTimeView.setText(string + ":" + string2);
        }
    }
}
