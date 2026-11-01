package miuix.appcompat.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import java.util.Calendar;
import java.util.Locale;
import miuix.appcompat.R;
import miuix.pickerwidget.widget.Calendar.CalendarDatePicker;
import miuix.pickerwidget.widget.TimePicker;
import miuix.slidingwidget.widget.SlidingButton;

/* JADX INFO: loaded from: classes2.dex */
public class CalendarDateTimePickerPanel extends LinearLayout {
    public LinearLayout calendarContainer;
    public CalendarDatePicker calendarDatePicker;
    public TextView headerCalendar;
    public View headerRoot;
    public TextView headerTime;
    public TextView headerTitle;
    public boolean isCalendarExpanded;
    public boolean isTimeExpanded;
    public int lastExpandedPanel;
    public View lunarSwitchContainer;
    public OnDateChangedListener onDateChangedListener;
    public OnExpandStateChangedListener onExpandStateChangedListener;
    public OnLunarCheckedChangeListener onLunarCheckedChangeListener;
    public OnTimeChangedListener onTimeChangedListener;
    public SlidingButton slidingButton;
    public LinearLayout timeContainer;
    public TimePicker timePicker;

    public interface OnDateChangedListener {
        void onDateChanged(CalendarDateTimePickerPanel calendarDateTimePickerPanel, int i, int i2, int i3, String str);
    }

    public interface OnExpandStateChangedListener {
        void onExpandStateChanged(boolean z, boolean z2);
    }

    public interface OnLunarCheckedChangeListener {
        void onLunarCheckedChanged(boolean z);
    }

    public interface OnTimeChangedListener {
        void onTimeChanged(CalendarDateTimePickerPanel calendarDateTimePickerPanel, int i, int i2);
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: miuix.appcompat.app.CalendarDateTimePickerPanel.SavedState.1
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public int day;
        public int hour;
        public int minute;
        public int month;
        public int year;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.year = parcel.readInt();
            this.month = parcel.readInt();
            this.day = parcel.readInt();
            this.hour = parcel.readInt();
            this.minute = parcel.readInt();
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeInt(this.year);
            parcel.writeInt(this.month);
            parcel.writeInt(this.day);
            parcel.writeInt(this.hour);
            parcel.writeInt(this.minute);
        }
    }

    public static /* synthetic */ void $r8$lambda$6QdMs1XqU5b_07_k1oZK6ctyDuU(CalendarDateTimePickerPanel calendarDateTimePickerPanel, CalendarDatePicker calendarDatePicker, int i, int i2, int i3, String str) {
        String fullLunarMessage = calendarDateTimePickerPanel.slidingButton.isChecked() ? calendarDateTimePickerPanel.getFullLunarMessage(i, i2, i3) : null;
        calendarDateTimePickerPanel.updateHeaderCalendarText(i, i2, i3, fullLunarMessage);
        OnDateChangedListener onDateChangedListener = calendarDateTimePickerPanel.onDateChangedListener;
        if (onDateChangedListener != null) {
            onDateChangedListener.onDateChanged(calendarDateTimePickerPanel, i, i2, i3, fullLunarMessage);
        }
    }

    public static /* synthetic */ void $r8$lambda$KYk_ZhTOugwxn12nMjdfre9H8Qo(CalendarDateTimePickerPanel calendarDateTimePickerPanel, View view) {
        boolean z = calendarDateTimePickerPanel.isCalendarExpanded;
        if (z || calendarDateTimePickerPanel.isTimeExpanded) {
            calendarDateTimePickerPanel.lastExpandedPanel = z ? 1 : 2;
            calendarDateTimePickerPanel.collapseAll();
            return;
        }
        if (calendarDateTimePickerPanel.lastExpandedPanel != 2) {
            if (calendarDateTimePickerPanel.headerCalendar.getVisibility() == 0) {
                calendarDateTimePickerPanel.toggleCalendar();
                return;
            } else {
                if (calendarDateTimePickerPanel.headerTime.getVisibility() == 0) {
                    calendarDateTimePickerPanel.toggleTimePicker();
                    return;
                }
                return;
            }
        }
        if (calendarDateTimePickerPanel.headerTime.getVisibility() == 0) {
            calendarDateTimePickerPanel.toggleTimePicker();
        } else if (calendarDateTimePickerPanel.headerCalendar.getVisibility() == 0) {
            calendarDateTimePickerPanel.toggleCalendar();
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$UEeAXng-BBc1B1AsBOGXB4UTX34, reason: not valid java name */
    public static /* synthetic */ void m1301$r8$lambda$UEeAXngBBc1B1AsBOGXB4UTX34(CalendarDateTimePickerPanel calendarDateTimePickerPanel, TimePicker timePicker, int i, int i2) {
        calendarDateTimePickerPanel.headerTime.setText(String.format(Locale.ROOT, "%02d:%02d", Integer.valueOf(i), Integer.valueOf(i2)));
        OnTimeChangedListener onTimeChangedListener = calendarDateTimePickerPanel.onTimeChangedListener;
        if (onTimeChangedListener != null) {
            onTimeChangedListener.onTimeChanged(calendarDateTimePickerPanel, i, i2);
        }
    }

    public static /* synthetic */ void $r8$lambda$miEXZR4gqj8ZASxyxaJDIgF3QAo(CalendarDateTimePickerPanel calendarDateTimePickerPanel, CompoundButton compoundButton, boolean z) {
        calendarDateTimePickerPanel.calendarDatePicker.setLunarMode(z);
        int year = calendarDateTimePickerPanel.calendarDatePicker.getYear();
        int month = calendarDateTimePickerPanel.calendarDatePicker.getMonth();
        int dayOfMonth = calendarDateTimePickerPanel.calendarDatePicker.getDayOfMonth();
        String fullLunarMessage = z ? calendarDateTimePickerPanel.getFullLunarMessage(year, month, dayOfMonth) : null;
        calendarDateTimePickerPanel.updateHeaderCalendarText(year, month, dayOfMonth, fullLunarMessage);
        OnLunarCheckedChangeListener onLunarCheckedChangeListener = calendarDateTimePickerPanel.onLunarCheckedChangeListener;
        if (onLunarCheckedChangeListener != null) {
            onLunarCheckedChangeListener.onLunarCheckedChanged(z);
        }
        OnDateChangedListener onDateChangedListener = calendarDateTimePickerPanel.onDateChangedListener;
        if (onDateChangedListener != null) {
            onDateChangedListener.onDateChanged(calendarDateTimePickerPanel, year, month, dayOfMonth, fullLunarMessage);
        }
    }

    public CalendarDateTimePickerPanel(Context context) {
        this(context, null);
    }

    public CalendarDateTimePickerPanel(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CalendarDateTimePickerPanel(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isCalendarExpanded = false;
        this.isTimeExpanded = false;
        this.lastExpandedPanel = 0;
        setOrientation(1);
        initView(context);
        initListeners();
        initAttr(context, attributeSet);
        updateDateTimeViews();
    }

    public void collapseAll() {
        if (this.isTimeExpanded && this.timePicker.isScrolling()) {
            this.timePicker.stopScrolling();
        }
        if (this.isCalendarExpanded) {
            this.isCalendarExpanded = false;
            this.headerCalendar.setSelected(false);
            this.calendarContainer.setVisibility(8);
        }
        if (this.isTimeExpanded) {
            this.isTimeExpanded = false;
            this.headerTime.setSelected(false);
            this.timeContainer.setVisibility(8);
        }
        notifyExpandStateChanged();
    }

    public int getDayOfMonth() {
        return this.calendarDatePicker.getDayOfMonth();
    }

    public String getFullLunarMessage(int i, int i2, int i3) {
        return this.calendarDatePicker.getFullLunarMessage(i, i2, i3);
    }

    public int getHour() {
        return this.timePicker.getCurrentHour().intValue();
    }

    public int getMinute() {
        return this.timePicker.getCurrentMinute().intValue();
    }

    public int getMonth() {
        return this.calendarDatePicker.getMonth();
    }

    public int getYear() {
        return this.calendarDatePicker.getYear();
    }

    public final void initAttr(Context context, AttributeSet attributeSet) {
        if (attributeSet == null) {
            return;
        }
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CalendarDatePickerPanel);
        try {
            String string = typedArrayObtainStyledAttributes.getString(R.styleable.CalendarDatePickerPanel_titleText);
            if (!TextUtils.isEmpty(string)) {
                this.headerTitle.setText(string);
            }
            int i = R.styleable.CalendarDatePickerPanel_showLunarSwitch;
            if (typedArrayObtainStyledAttributes.hasValue(i)) {
                setLunarSwitchVisible(typedArrayObtainStyledAttributes.getBoolean(i, true));
            }
            int i2 = R.styleable.CalendarDatePickerPanel_showCalendar;
            if (typedArrayObtainStyledAttributes.hasValue(i2)) {
                setCalendarVisible(typedArrayObtainStyledAttributes.getBoolean(i2, true));
            }
            int i3 = R.styleable.CalendarDatePickerPanel_showTimePicker;
            if (typedArrayObtainStyledAttributes.hasValue(i3)) {
                setTimePickerVisible(typedArrayObtainStyledAttributes.getBoolean(i3, true));
            }
        } finally {
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public final void initListeners() {
        this.headerRoot.setOnClickListener(new View.OnClickListener() { // from class: miuix.appcompat.app.CalendarDateTimePickerPanel$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CalendarDateTimePickerPanel.$r8$lambda$KYk_ZhTOugwxn12nMjdfre9H8Qo(this.f$0, view);
            }
        });
        this.headerCalendar.setOnClickListener(new View.OnClickListener() { // from class: miuix.appcompat.app.CalendarDateTimePickerPanel$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.toggleCalendar();
            }
        });
        this.headerTime.setOnClickListener(new View.OnClickListener() { // from class: miuix.appcompat.app.CalendarDateTimePickerPanel$$ExternalSyntheticLambda2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                this.f$0.toggleTimePicker();
            }
        });
        this.slidingButton.setOnPerformCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() { // from class: miuix.appcompat.app.CalendarDateTimePickerPanel$$ExternalSyntheticLambda3
            @Override // android.widget.CompoundButton.OnCheckedChangeListener
            public final void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                CalendarDateTimePickerPanel.$r8$lambda$miEXZR4gqj8ZASxyxaJDIgF3QAo(this.f$0, compoundButton, z);
            }
        });
        this.calendarDatePicker.setOnDateChangedListener(new CalendarDatePicker.OnDateChangedListener() { // from class: miuix.appcompat.app.CalendarDateTimePickerPanel$$ExternalSyntheticLambda4
            @Override // miuix.pickerwidget.widget.Calendar.CalendarDatePicker.OnDateChangedListener
            public final void onDateChanged(CalendarDatePicker calendarDatePicker, int i, int i2, int i3, String str) {
                CalendarDateTimePickerPanel.$r8$lambda$6QdMs1XqU5b_07_k1oZK6ctyDuU(this.f$0, calendarDatePicker, i, i2, i3, str);
            }
        });
        this.timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() { // from class: miuix.appcompat.app.CalendarDateTimePickerPanel$$ExternalSyntheticLambda5
            @Override // miuix.pickerwidget.widget.TimePicker.OnTimeChangedListener
            public final void onTimeChanged(TimePicker timePicker, int i, int i2) {
                CalendarDateTimePickerPanel.m1301$r8$lambda$UEeAXngBBc1B1AsBOGXB4UTX34(this.f$0, timePicker, i, i2);
            }
        });
    }

    public final void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.miuix_appcompat_calendar_date_time_picker_panel_layout, (ViewGroup) this, true);
        this.headerTitle = (TextView) findViewById(R.id.header_title);
        this.headerCalendar = (TextView) findViewById(R.id.header_calendar);
        this.headerTime = (TextView) findViewById(R.id.header_time);
        this.headerRoot = findViewById(R.id.header_root);
        this.calendarContainer = (LinearLayout) findViewById(R.id.calendar_container);
        this.calendarDatePicker = (CalendarDatePicker) findViewById(R.id.calendar_date_picker);
        this.lunarSwitchContainer = findViewById(R.id.lunar_switch_container);
        this.slidingButton = (SlidingButton) findViewById(R.id.slidingButton);
        this.timeContainer = (LinearLayout) findViewById(R.id.time_container);
        TimePicker timePicker = (TimePicker) findViewById(R.id.time_picker);
        this.timePicker = timePicker;
        timePicker.set24HourView(Boolean.TRUE);
        this.timePicker.setColumnWidthWrapContent(true);
        this.calendarDatePicker.setSaveEnabled(false);
        this.timePicker.setSaveEnabled(false);
        setupAccessibilityDelegate();
    }

    public final void notifyExpandStateChanged() {
        OnExpandStateChangedListener onExpandStateChangedListener = this.onExpandStateChangedListener;
        if (onExpandStateChangedListener != null) {
            onExpandStateChangedListener.onExpandStateChanged(this.isCalendarExpanded, this.isTimeExpanded);
        }
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setDate(savedState.year, savedState.month, savedState.day);
        setTime(savedState.hour, savedState.minute);
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.year = this.calendarDatePicker.getYear();
        savedState.month = this.calendarDatePicker.getMonth();
        savedState.day = this.calendarDatePicker.getDayOfMonth();
        savedState.hour = this.timePicker.getCurrentHour().intValue();
        savedState.minute = this.timePicker.getCurrentMinute().intValue();
        return savedState;
    }

    @Override // android.view.View
    public void onWindowFocusChanged(boolean z) {
        super.onWindowFocusChanged(z);
        if (z) {
            updateDateTimeViews();
        }
    }

    public void resetState() {
        if (this.isCalendarExpanded) {
            this.isCalendarExpanded = false;
            this.headerCalendar.setSelected(false);
            this.calendarContainer.setVisibility(8);
        }
        if (this.isTimeExpanded) {
            this.isTimeExpanded = false;
            this.headerTime.setSelected(false);
            this.timeContainer.setVisibility(8);
        }
        this.lastExpandedPanel = 0;
        if (this.slidingButton.isChecked()) {
            this.slidingButton.setChecked(false);
            this.calendarDatePicker.setLunarMode(false);
        }
    }

    public void restoreExpandState(boolean z, boolean z2) {
        if (z && this.headerCalendar.getVisibility() == 0) {
            this.isCalendarExpanded = true;
            this.headerCalendar.setSelected(true);
            this.calendarContainer.setVisibility(0);
        }
        if (z2 && this.headerTime.getVisibility() == 0) {
            this.isTimeExpanded = true;
            this.headerTime.setSelected(true);
            this.timeContainer.setVisibility(0);
        }
    }

    public void setCalendarFixedWidth(boolean z) {
        CalendarDatePicker calendarDatePicker = this.calendarDatePicker;
        if (calendarDatePicker != null) {
            calendarDatePicker.setFixedWidth(z);
        }
    }

    public void setCalendarVisible(boolean z) {
        this.headerCalendar.setVisibility(z ? 0 : 8);
        if (z || !this.isCalendarExpanded) {
            return;
        }
        this.isCalendarExpanded = false;
        this.headerCalendar.setSelected(false);
        this.calendarContainer.setVisibility(8);
    }

    public void setDate(int i, int i2, int i3) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(i, i2, i3);
        this.calendarDatePicker.setDate(calendar.getTimeInMillis(), false);
        updateHeaderCalendarText(i, i2, i3, this.slidingButton.isChecked() ? getFullLunarMessage(i, i2, i3) : null);
    }

    public void setLunarChecked(boolean z) {
        this.slidingButton.setChecked(z);
        this.calendarDatePicker.setLunarMode(z);
    }

    public void setLunarSwitchVisible(boolean z) {
        this.lunarSwitchContainer.setVisibility(z ? 0 : 8);
        if (z || !this.slidingButton.isChecked()) {
            return;
        }
        this.slidingButton.setChecked(false);
        this.calendarDatePicker.setLunarMode(false);
    }

    public void setMaxDate(long j) {
        this.calendarDatePicker.setMaxDate(j);
    }

    public void setMinDate(long j) {
        this.calendarDatePicker.setMinDate(j);
    }

    public void setOnDateChangedListener(@Nullable OnDateChangedListener onDateChangedListener) {
        this.onDateChangedListener = onDateChangedListener;
    }

    public void setOnExpandStateChangedListener(@Nullable OnExpandStateChangedListener onExpandStateChangedListener) {
        this.onExpandStateChangedListener = onExpandStateChangedListener;
    }

    public void setOnLunarCheckedChangeListener(@Nullable OnLunarCheckedChangeListener onLunarCheckedChangeListener) {
        this.onLunarCheckedChangeListener = onLunarCheckedChangeListener;
    }

    public void setOnTimeChangedListener(@Nullable OnTimeChangedListener onTimeChangedListener) {
        this.onTimeChangedListener = onTimeChangedListener;
    }

    public void setTime(int i, int i2) {
        this.timePicker.setCurrentHour(Integer.valueOf(i));
        this.timePicker.setCurrentMinute(Integer.valueOf(i2));
        this.headerTime.setText(String.format(Locale.ROOT, "%02d:%02d", Integer.valueOf(i), Integer.valueOf(i2)));
    }

    public void setTimePickerVisible(boolean z) {
        this.headerTime.setVisibility(z ? 0 : 8);
        if (z || !this.isTimeExpanded) {
            return;
        }
        this.isTimeExpanded = false;
        this.headerTime.setSelected(false);
        this.timeContainer.setVisibility(8);
    }

    public void setTitleText(@Nullable String str) {
        this.headerTitle.setText(str);
    }

    public final void setupAccessibilityDelegate() {
        ViewCompat.setAccessibilityDelegate(this.lunarSwitchContainer, new AccessibilityDelegateCompat() { // from class: miuix.appcompat.app.CalendarDateTimePickerPanel.1
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setCheckable(true);
                accessibilityNodeInfoCompat.setClickable(true);
                accessibilityNodeInfoCompat.setClassName(Switch.class.getName());
                if (CalendarDateTimePickerPanel.this.slidingButton != null) {
                    accessibilityNodeInfoCompat.setChecked(CalendarDateTimePickerPanel.this.slidingButton.isChecked());
                    accessibilityNodeInfoCompat.setContentDescription(CalendarDateTimePickerPanel.this.slidingButton.getContentDescription());
                }
            }
        });
        ViewCompat.replaceAccessibilityAction(this.lunarSwitchContainer, AccessibilityNodeInfoCompat.AccessibilityActionCompat.ACTION_CLICK, "", new AccessibilityViewCommand() { // from class: miuix.appcompat.app.CalendarDateTimePickerPanel.2
            @Override // androidx.core.view.accessibility.AccessibilityViewCommand
            public boolean perform(View view, AccessibilityViewCommand.CommandArguments commandArguments) {
                if (CalendarDateTimePickerPanel.this.slidingButton != null) {
                    CalendarDateTimePickerPanel.this.slidingButton.performClick();
                }
                view.sendAccessibilityEvent(1);
                return true;
            }
        });
    }

    public void toggleCalendar() {
        if (this.isTimeExpanded && this.timePicker.isScrolling()) {
            return;
        }
        if (this.isTimeExpanded) {
            this.isTimeExpanded = false;
            this.headerTime.setSelected(false);
            this.timeContainer.setVisibility(8);
        }
        boolean z = !this.isCalendarExpanded;
        this.isCalendarExpanded = z;
        this.headerCalendar.setSelected(z);
        this.calendarContainer.setVisibility(this.isCalendarExpanded ? 0 : 8);
        if (this.isCalendarExpanded) {
            boolean zIsChecked = this.slidingButton.isChecked();
            if (this.calendarDatePicker.isLunarMode() != zIsChecked) {
                this.calendarDatePicker.setLunarMode(zIsChecked);
            }
        } else {
            this.lastExpandedPanel = 1;
        }
        notifyExpandStateChanged();
    }

    public void toggleTimePicker() {
        if (this.isTimeExpanded && this.timePicker.isScrolling()) {
            return;
        }
        if (this.isCalendarExpanded) {
            this.isCalendarExpanded = false;
            this.headerCalendar.setSelected(false);
            this.calendarContainer.setVisibility(8);
        }
        boolean z = !this.isTimeExpanded;
        this.isTimeExpanded = z;
        this.headerTime.setSelected(z);
        this.timeContainer.setVisibility(this.isTimeExpanded ? 0 : 8);
        if (!this.isTimeExpanded) {
            this.lastExpandedPanel = 2;
        }
        notifyExpandStateChanged();
    }

    public final void updateDateTimeViews() {
        int year = this.calendarDatePicker.getYear();
        int month = this.calendarDatePicker.getMonth();
        int dayOfMonth = this.calendarDatePicker.getDayOfMonth();
        updateHeaderCalendarText(year, month, dayOfMonth, this.slidingButton.isChecked() ? getFullLunarMessage(year, month, dayOfMonth) : null);
        Integer currentHour = this.timePicker.getCurrentHour();
        currentHour.intValue();
        Integer currentMinute = this.timePicker.getCurrentMinute();
        currentMinute.intValue();
        this.headerTime.setText(String.format(Locale.ROOT, "%02d:%02d", currentHour, currentMinute));
    }

    public final void updateHeaderCalendarText(int i, int i2, int i3, String str) {
        String str2 = String.format(Locale.ROOT, "%04d/%02d/%02d", Integer.valueOf(i), Integer.valueOf(i2 + 1), Integer.valueOf(i3));
        if (TextUtils.isEmpty(str)) {
            this.headerCalendar.setText(str2);
        } else {
            this.headerCalendar.setText(str);
        }
    }
}
