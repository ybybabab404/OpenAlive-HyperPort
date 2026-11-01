package miuix.pickerwidget.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.LinearLayout;
import androidx.annotation.IntRange;
import com.miui.maml.folme.AnimatedProperty;
import java.util.Locale;
import miuix.pickerwidget.R;
import miuix.pickerwidget.date.Calendar;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes2.dex */
public class DateTimePicker extends LinearLayout {
    public static int DAYPICKER_ALL_ITEM_MAX_VALUE;
    public static int DAYPICKER_WHEEL_ITEM_COUNT;
    public static DayFormatter DEFAULT_DAY_FORMATTER;
    public static int HALF_WHEEL_ITEM_COUNT;
    public static final ThreadLocal sCalCache = new ThreadLocal();
    public static ThreadLocal sCalendarCache = new ThreadLocal();
    public Calendar mCalendar;
    public String[] mDayDisplayValues;
    public DayFormatter mDayFormatter;
    public int mDayLastValue;
    public NumberPicker mDayPicker;
    public NumberPicker mHourPicker;
    public boolean mIsLunarMode;
    public OnDateTimeChangedListener mListener;
    public DayFormatter mLunarFormatter;
    public Calendar mMaxDate;
    public Calendar mMinDate;
    public String[] mMinuteDisplayValues;
    public int mMinuteInterval;
    public NumberPicker mMinutePicker;

    public static class DayFormatter {
        public Context mContext;

        public DayFormatter(Context context) {
            this.mContext = context.getApplicationContext();
        }

        public String formatDay(int i, int i2, int i3) {
            Calendar calendar = (Calendar) DateTimePicker.sCalendarCache.get();
            if (calendar == null) {
                calendar = new Calendar();
                DateTimePicker.sCalendarCache.set(calendar);
            }
            calendar.set(1, i);
            calendar.set(5, i2);
            calendar.set(9, i3);
            if (!Locale.getDefault().getLanguage().equals(Locale.CHINESE.getLanguage())) {
                return DateUtils.formatDateTime(this.mContext, calendar.getTimeInMillis(), 13696);
            }
            String dateTime = DateUtils.formatDateTime(this.mContext, calendar.getTimeInMillis(), 4480);
            return dateTime.replace(" ", "") + " " + DateUtils.formatDateTime(this.mContext, calendar.getTimeInMillis(), 9216);
        }
    }

    public static class LunarFormatter extends DayFormatter {
        public LunarFormatter(Context context) {
            super(context);
        }

        @Override // miuix.pickerwidget.widget.DateTimePicker.DayFormatter
        public String formatDay(int i, int i2, int i3) {
            Calendar calendar = (Calendar) DateTimePicker.sCalendarCache.get();
            if (calendar == null) {
                calendar = new Calendar();
                DateTimePicker.sCalendarCache.set(calendar);
            }
            calendar.set(1, i);
            calendar.set(5, i2);
            calendar.set(9, i3);
            Context context = this.mContext;
            return calendar.format(context, context.getString(R.string.fmt_chinese_date));
        }
    }

    public interface OnDateTimeChangedListener {
        void onDateTimeChanged(DateTimePicker dateTimePicker, long j);
    }

    public class PickerValueChangeListener implements NumberPicker.OnValueChangeListener {
        public PickerValueChangeListener() {
        }

        public final void notifyTimeChanged(DateTimePicker dateTimePicker) {
            DateTimePicker.this.sendAccessibilityEvent(4);
            if (DateTimePicker.this.mListener != null) {
                DateTimePicker.this.mListener.onDateTimeChanged(dateTimePicker, DateTimePicker.this.getTimeInMillis());
            }
        }

        @Override // miuix.pickerwidget.widget.NumberPicker.OnValueChangeListener
        public void onValueChange(NumberPicker numberPicker, int i, int i2) {
            if (numberPicker == DateTimePicker.this.mDayPicker) {
                DateTimePicker.this.mCalendar.add(12, ((numberPicker.getValue() - DateTimePicker.this.mDayLastValue) + DateTimePicker.DAYPICKER_WHEEL_ITEM_COUNT) % DateTimePicker.DAYPICKER_WHEEL_ITEM_COUNT != 1 ? -1 : 1);
                DateTimePicker.this.mDayLastValue = numberPicker.getValue();
            } else if (numberPicker == DateTimePicker.this.mHourPicker) {
                DateTimePicker.this.mCalendar.set(18, DateTimePicker.this.mHourPicker.getValue());
            } else if (numberPicker == DateTimePicker.this.mMinutePicker) {
                DateTimePicker.this.mCalendar.set(20, DateTimePicker.this.mMinuteInterval * DateTimePicker.this.mMinutePicker.getValue());
            }
            DateTimePicker.this.checkCurrentTime();
            DateTimePicker.this.updateDayPicker(false);
            DateTimePicker.this.updateHourPicker();
            DateTimePicker.this.updateMinutePicker();
            notifyTimeChanged(DateTimePicker.this);
        }
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: miuix.pickerwidget.widget.DateTimePicker.SavedState.1
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public boolean mIsLunar;
        public long mTimeInMillis;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.mTimeInMillis = parcel.readLong();
            this.mIsLunar = parcel.readInt() == 1;
        }

        public SavedState(Parcelable parcelable, long j, boolean z) {
            super(parcelable);
            this.mTimeInMillis = j;
            this.mIsLunar = z;
        }

        public long getTimeInMillis() {
            return this.mTimeInMillis;
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeLong(this.mTimeInMillis);
            parcel.writeInt(this.mIsLunar ? 1 : 0);
        }
    }

    public DateTimePicker(Context context) {
        this(context, null);
    }

    public DateTimePicker(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.dateTimePickerStyle);
    }

    public DateTimePicker(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mMinuteInterval = 1;
        this.mMinDate = null;
        this.mMaxDate = null;
        this.mDayDisplayValues = null;
        this.mIsLunarMode = false;
        DEFAULT_DAY_FORMATTER = new DayFormatter(getContext());
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(R.layout.miuix_appcompat_date_time_picker, (ViewGroup) this, true);
        PickerValueChangeListener pickerValueChangeListener = new PickerValueChangeListener();
        Calendar calendar = new Calendar();
        this.mCalendar = calendar;
        adjustCalendar(calendar, true);
        ThreadLocal threadLocal = sCalCache;
        Calendar calendar2 = (Calendar) threadLocal.get();
        if (calendar2 == null) {
            calendar2 = new Calendar();
            threadLocal.set(calendar2);
        }
        calendar2.setSafeTimeInMillis(0L, this.mIsLunarMode);
        this.mDayPicker = (NumberPicker) findViewById(R.id.day);
        this.mHourPicker = (NumberPicker) findViewById(R.id.hour);
        this.mMinutePicker = (NumberPicker) findViewById(R.id.minute);
        int i2 = NumberPicker.SELECTOR_WHEEL_ITEM_COUNT;
        DAYPICKER_WHEEL_ITEM_COUNT = i2 + 2;
        DAYPICKER_ALL_ITEM_MAX_VALUE = i2 + 1;
        HALF_WHEEL_ITEM_COUNT = i2 / 2;
        this.mDayPicker.setOnValueChangedListener(pickerValueChangeListener);
        this.mDayPicker.setMaxFlingSpeedFactor(3.0f);
        this.mHourPicker.setOnValueChangedListener(pickerValueChangeListener);
        if (!TextUtils.isEmpty(this.mHourPicker.getLabel())) {
            this.mDayPicker.setLabel("\u3000");
        }
        this.mMinutePicker.setOnValueChangedListener(pickerValueChangeListener);
        this.mMinutePicker.setMinValue(0);
        this.mMinutePicker.setMaxValue(59);
        this.mHourPicker.setFormatter(NumberPicker.TWO_DIGIT_FORMATTER);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.DateTimePicker, i, 0);
        this.mIsLunarMode = typedArrayObtainStyledAttributes.getBoolean(R.styleable.DateTimePicker_lunarCalendar, false);
        typedArrayObtainStyledAttributes.recycle();
        reorderLayout();
        checkCurrentTime();
        updateDayPicker(true);
        updateHourPicker();
        updateMinutePicker();
        if (getImportantForAccessibility() == 0) {
            setImportantForAccessibility(1);
        }
    }

    public final void adjustCalendar(Calendar calendar, boolean z) {
        calendar.set(22, 0);
        calendar.set(21, 0);
        int i = calendar.get(20) % this.mMinuteInterval;
        if (i != 0) {
            if (!z) {
                calendar.add(20, -i);
                return;
            }
            int i2 = calendar.get(20);
            int i3 = this.mMinuteInterval;
            if ((i2 + i3) - i < 60) {
                calendar.add(20, i3 - i);
            } else {
                calendar.add(18, 1);
                calendar.set(20, 0);
            }
        }
    }

    public final void checkCurrentTime() {
        Calendar calendar = this.mMinDate;
        if (calendar != null && calendar.getTimeInMillis() > this.mCalendar.getTimeInMillis()) {
            this.mCalendar.setSafeTimeInMillis(this.mMinDate.getTimeInMillis(), this.mIsLunarMode);
        }
        Calendar calendar2 = this.mMaxDate;
        if (calendar2 == null || calendar2.getTimeInMillis() >= this.mCalendar.getTimeInMillis()) {
            return;
        }
        this.mCalendar.setSafeTimeInMillis(this.mMaxDate.getTimeInMillis(), this.mIsLunarMode);
    }

    public final void checkDisplayeValid(NumberPicker numberPicker, int i, int i2) {
        String[] displayedValues = numberPicker.getDisplayedValues();
        if (displayedValues == null || displayedValues.length >= (i2 - i) + 1) {
            return;
        }
        numberPicker.setDisplayedValues(null);
    }

    public final int computeDayCount(Calendar calendar, Calendar calendar2) {
        Calendar calendar3 = (Calendar) calendar.clone();
        Calendar calendar4 = (Calendar) calendar2.clone();
        calendar3.set(18, 0);
        calendar3.set(20, 0);
        calendar3.set(21, 0);
        calendar3.set(22, 0);
        calendar4.set(18, 0);
        calendar4.set(20, 0);
        calendar4.set(21, 0);
        calendar4.set(22, 0);
        return (int) (((((calendar3.getTimeInMillis() / 1000) / 60) / 60) / 24) - ((((calendar4.getTimeInMillis() / 1000) / 60) / 60) / 24));
    }

    @Override // android.view.View
    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        onPopulateAccessibilityEvent(accessibilityEvent);
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchRestoreInstanceState(SparseArray sparseArray) {
        dispatchThawSelfOnly(sparseArray);
    }

    public final String formatDay(int i, int i2, int i3) {
        DayFormatter dayFormatter = DEFAULT_DAY_FORMATTER;
        if (this.mIsLunarMode) {
            if (this.mLunarFormatter == null) {
                this.mLunarFormatter = new LunarFormatter(getContext());
            }
            dayFormatter = this.mLunarFormatter;
        }
        DayFormatter dayFormatter2 = this.mDayFormatter;
        if (dayFormatter2 != null) {
            dayFormatter = dayFormatter2;
        }
        return dayFormatter.formatDay(i, i2, i3);
    }

    public long getTimeInMillis() {
        return this.mCalendar.getTimeInMillis();
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(DateTimePicker.class.getName());
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(DateTimePicker.class.getName());
    }

    @Override // android.view.View
    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onPopulateAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.getText().add(DateUtils.formatDateTime(getContext(), this.mCalendar.getTimeInMillis(), 1420));
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mIsLunarMode = savedState.mIsLunar;
        update(savedState.getTimeInMillis());
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        return new SavedState(super.onSaveInstanceState(), getTimeInMillis(), this.mIsLunarMode);
    }

    public final void reorderLayout() {
        Resources resources = getResources();
        boolean z = resources.getConfiguration().getLayoutDirection() == 1;
        boolean zStartsWith = resources.getString(R.string.fmt_time_12hour_minute).startsWith(AnimatedProperty.PROPERTY_NAME_H);
        if (!(zStartsWith && z) && (zStartsWith || z)) {
            return;
        }
        ViewGroup viewGroup = (ViewGroup) this.mHourPicker.getParent();
        viewGroup.removeView(this.mHourPicker);
        viewGroup.addView(this.mHourPicker, viewGroup.getChildCount());
    }

    public void setDayFormatter(DayFormatter dayFormatter) {
        this.mDayFormatter = dayFormatter;
        updateDayPicker(true);
    }

    public void setLunarMode(boolean z) {
        boolean z2 = this.mIsLunarMode;
        this.mIsLunarMode = z;
        updateDayPicker(true);
        if (z2 != this.mIsLunarMode) {
            this.mDayPicker.requestLayout();
        }
    }

    public void setMaxDateTime(long j) {
        if (j <= 0) {
            this.mMaxDate = null;
        } else {
            Calendar calendar = new Calendar();
            this.mMaxDate = calendar;
            calendar.setSafeTimeInMillis(j, this.mIsLunarMode);
            adjustCalendar(this.mMaxDate, false);
            Calendar calendar2 = this.mMinDate;
            if (calendar2 != null && calendar2.getTimeInMillis() > this.mMaxDate.getTimeInMillis()) {
                this.mMaxDate.setSafeTimeInMillis(this.mMinDate.getTimeInMillis(), this.mIsLunarMode);
            }
        }
        checkCurrentTime();
        updateDayPicker(true);
        updateHourPicker();
        updateMinutePicker();
    }

    public void setMinDateTime(long j) {
        if (j <= 0) {
            this.mMinDate = null;
        } else {
            Calendar calendar = new Calendar();
            this.mMinDate = calendar;
            calendar.setSafeTimeInMillis(j, this.mIsLunarMode);
            if (this.mMinDate.get(21) != 0 || this.mMinDate.get(22) != 0) {
                this.mMinDate.add(20, 1);
            }
            adjustCalendar(this.mMinDate, true);
            Calendar calendar2 = this.mMaxDate;
            if (calendar2 != null && calendar2.getTimeInMillis() < this.mMinDate.getTimeInMillis()) {
                this.mMinDate.setSafeTimeInMillis(this.mMaxDate.getTimeInMillis(), this.mIsLunarMode);
            }
        }
        checkCurrentTime();
        updateDayPicker(true);
        updateHourPicker();
        updateMinutePicker();
    }

    public void setMinuteInterval(@IntRange int i) {
        if (this.mMinuteInterval == i) {
            return;
        }
        this.mMinuteInterval = i;
        adjustCalendar(this.mCalendar, true);
        checkCurrentTime();
        updateHourPicker();
        updateMinutePicker();
    }

    public void setOnTimeChangedListener(OnDateTimeChangedListener onDateTimeChangedListener) {
        this.mListener = onDateTimeChangedListener;
    }

    public void update(long j) {
        this.mCalendar.setSafeTimeInMillis(j, this.mIsLunarMode);
        adjustCalendar(this.mCalendar, true);
        checkCurrentTime();
        updateDayPicker(true);
        updateHourPicker();
        updateMinutePicker();
    }

    public final void updateDayPicker(boolean z) {
        String[] strArr;
        Calendar calendar = this.mMinDate;
        int iComputeDayCount = calendar == null ? Integer.MAX_VALUE : computeDayCount(this.mCalendar, calendar);
        Calendar calendar2 = this.mMaxDate;
        int iComputeDayCount2 = calendar2 != null ? computeDayCount(calendar2, this.mCalendar) : Integer.MAX_VALUE;
        int i = HALF_WHEEL_ITEM_COUNT;
        if (iComputeDayCount > i || iComputeDayCount2 > i) {
            checkDisplayeValid(this.mDayPicker, 0, DAYPICKER_ALL_ITEM_MAX_VALUE);
            this.mDayPicker.setMinValue(0);
            this.mDayPicker.setMaxValue(DAYPICKER_ALL_ITEM_MAX_VALUE);
            if (iComputeDayCount <= HALF_WHEEL_ITEM_COUNT) {
                this.mDayPicker.setValue(iComputeDayCount);
                this.mDayLastValue = iComputeDayCount;
                this.mDayPicker.setWrapSelectorWheel(false);
            }
            if (iComputeDayCount2 <= HALF_WHEEL_ITEM_COUNT) {
                int i2 = DAYPICKER_ALL_ITEM_MAX_VALUE - iComputeDayCount2;
                this.mDayLastValue = i2;
                this.mDayPicker.setValue(i2);
                this.mDayPicker.setWrapSelectorWheel(false);
            }
            int i3 = HALF_WHEEL_ITEM_COUNT;
            if (iComputeDayCount > i3 && iComputeDayCount2 > i3) {
                this.mDayPicker.setWrapSelectorWheel(true);
            }
        } else {
            int iComputeDayCount3 = computeDayCount(this.mMaxDate, this.mMinDate);
            checkDisplayeValid(this.mDayPicker, 0, iComputeDayCount3);
            this.mDayPicker.setMinValue(0);
            this.mDayPicker.setMaxValue(iComputeDayCount3);
            this.mDayPicker.setValue(iComputeDayCount);
            this.mDayLastValue = iComputeDayCount;
            this.mDayPicker.setWrapSelectorWheel(false);
        }
        int maxValue = (this.mDayPicker.getMaxValue() - this.mDayPicker.getMinValue()) + 1;
        if (z || (strArr = this.mDayDisplayValues) == null || strArr.length != maxValue) {
            this.mDayDisplayValues = new String[maxValue];
        }
        int value = this.mDayPicker.getValue();
        ThreadLocal threadLocal = sCalCache;
        Calendar calendar3 = (Calendar) threadLocal.get();
        if (calendar3 == null) {
            calendar3 = new Calendar();
            threadLocal.set(calendar3);
        }
        calendar3.setSafeTimeInMillis(this.mCalendar.getTimeInMillis(), this.mIsLunarMode);
        this.mDayDisplayValues[value] = formatDay(calendar3.get(1), calendar3.get(5), calendar3.get(9));
        for (int i4 = 1; i4 <= DAYPICKER_WHEEL_ITEM_COUNT / 2; i4++) {
            calendar3.add(12, 1);
            int i5 = (value + i4) % DAYPICKER_WHEEL_ITEM_COUNT;
            String[] strArr2 = this.mDayDisplayValues;
            if (i5 >= strArr2.length) {
                break;
            }
            strArr2[i5] = formatDay(calendar3.get(1), calendar3.get(5), calendar3.get(9));
        }
        calendar3.setSafeTimeInMillis(this.mCalendar.getTimeInMillis(), this.mIsLunarMode);
        for (int i6 = 1; i6 <= DAYPICKER_WHEEL_ITEM_COUNT / 2; i6++) {
            calendar3.add(12, -1);
            int i7 = DAYPICKER_WHEEL_ITEM_COUNT;
            int i8 = ((value - i6) + i7) % i7;
            String[] strArr3 = this.mDayDisplayValues;
            if (i8 >= strArr3.length) {
                break;
            }
            strArr3[i8] = formatDay(calendar3.get(1), calendar3.get(5), calendar3.get(9));
        }
        this.mDayPicker.setDisplayedValues(this.mDayDisplayValues);
    }

    public final void updateHourPicker() {
        boolean z;
        Calendar calendar = this.mMaxDate;
        if (calendar == null || computeDayCount(this.mCalendar, calendar) != 0) {
            z = false;
        } else {
            this.mHourPicker.setMaxValue(this.mMaxDate.get(18));
            this.mHourPicker.setWrapSelectorWheel(false);
            z = true;
        }
        Calendar calendar2 = this.mMinDate;
        if (calendar2 != null && computeDayCount(this.mCalendar, calendar2) == 0) {
            this.mHourPicker.setMinValue(this.mMinDate.get(18));
            this.mHourPicker.setWrapSelectorWheel(false);
            z = true;
        }
        if (!z) {
            this.mHourPicker.setMinValue(0);
            this.mHourPicker.setMaxValue(23);
            this.mHourPicker.setWrapSelectorWheel(true);
        }
        this.mHourPicker.setValue(this.mCalendar.get(18));
    }

    public final void updateMinutePicker() {
        boolean z;
        Calendar calendar = this.mMaxDate;
        if (calendar != null && computeDayCount(this.mCalendar, calendar) == 0 && this.mCalendar.get(18) == this.mMaxDate.get(18)) {
            int i = this.mMaxDate.get(20);
            this.mMinutePicker.setMinValue(0);
            this.mMinutePicker.setMaxValue(i / this.mMinuteInterval);
            this.mMinutePicker.setWrapSelectorWheel(false);
            z = true;
        } else {
            z = false;
        }
        Calendar calendar2 = this.mMinDate;
        if (calendar2 != null && computeDayCount(this.mCalendar, calendar2) == 0 && this.mCalendar.get(18) == this.mMinDate.get(18)) {
            this.mMinutePicker.setMinValue(this.mMinDate.get(20) / this.mMinuteInterval);
            this.mMinutePicker.setWrapSelectorWheel(false);
            z = true;
        }
        if (!z) {
            int i2 = this.mMinuteInterval;
            int i3 = 60 / i2;
            if (60 % i2 == 0) {
                i3--;
            }
            checkDisplayeValid(this.mMinutePicker, 0, i3);
            this.mMinutePicker.setMinValue(0);
            this.mMinutePicker.setMaxValue(i3);
            this.mMinutePicker.setWrapSelectorWheel(true);
        }
        int maxValue = (this.mMinutePicker.getMaxValue() - this.mMinutePicker.getMinValue()) + 1;
        String[] strArr = this.mMinuteDisplayValues;
        if (strArr == null || strArr.length != maxValue) {
            this.mMinuteDisplayValues = new String[maxValue];
            for (int i4 = 0; i4 < maxValue; i4++) {
                this.mMinuteDisplayValues[i4] = NumberPicker.TWO_DIGIT_FORMATTER.format((this.mMinutePicker.getMinValue() + i4) * this.mMinuteInterval);
            }
            this.mMinutePicker.setDisplayedValues(this.mMinuteDisplayValues);
        }
        this.mMinutePicker.setValue(this.mCalendar.get(20) / this.mMinuteInterval);
    }
}
