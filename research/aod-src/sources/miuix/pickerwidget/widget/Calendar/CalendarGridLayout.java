package miuix.pickerwidget.widget.Calendar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;
import miuix.core.util.MiuixUIUtils;
import miuix.pickerwidget.R;
import miuix.pickerwidget.date.Calendar;
import miuix.view.DensityChangedHelper;

/* JADX INFO: loaded from: classes2.dex */
class CalendarGridLayout extends GridLayout {
    public Calendar mCalendar;
    public int mChildPaddingIfNotLunarMode;
    public int mColumnCount;
    public int mDay;
    public int mFirstWeekDayInMonth;
    public boolean mHasBound;
    public CalendarDatePickerHelper mHelper;
    public int mLargeRowGap;
    public int mLastBoundDay;
    public int mLastBoundHighlightColor;
    public boolean mLastBoundLunarMode;
    public long mLastBoundMaxDate;
    public long mLastBoundMinDate;
    public int mLastBoundMonth;
    public int mLastBoundYear;
    public boolean mLunarMode;
    public long mMaxDate;
    public long mMaxKey;
    public int mMaxRowCount;
    public int mMediumRowGap;
    public long mMinDate;
    public long mMinKey;
    public int mMonth;
    public int mRowGap;
    public View mSelectedChild;
    public int mSmallRowGap;
    public boolean mSpecialHoliday;
    public int mYear;

    public CalendarGridLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CalendarGridLayout(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, R.style.Widget_CalendarGridLayout);
    }

    public CalendarGridLayout(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mRowGap = 0;
        this.mHasBound = false;
        this.mLastBoundHighlightColor = 0;
        this.mMinDate = Long.MIN_VALUE;
        this.mMaxDate = Long.MAX_VALUE;
        this.mMinKey = Long.MIN_VALUE;
        this.mMaxKey = Long.MAX_VALUE;
        this.mLastBoundMinDate = Long.MIN_VALUE;
        this.mLastBoundMaxDate = Long.MAX_VALUE;
        init(context, attributeSet, i, i2);
    }

    public static long toYmdKey(int i, int i2, int i3) {
        return (((long) i) * 10000) + (((long) i2) * 100) + ((long) i3);
    }

    public final void adjustRowGap(int i) {
        int i2 = i != 4 ? i != 5 ? this.mSmallRowGap : this.mMediumRowGap : this.mLargeRowGap;
        if (this.mRowGap == i2) {
            return;
        }
        int i3 = 0;
        while (i3 < this.mMaxRowCount) {
            for (int i4 = 0; i4 < this.mColumnCount; i4++) {
                View childAt = getChildAt(i3, i4);
                if (i3 < i) {
                    childAt.setVisibility(0);
                    if (i4 == 0) {
                        setChildMarginBottom(childAt, i3 == i + (-1) ? 0 : i2);
                    }
                } else {
                    childAt.setVisibility(8);
                }
            }
            i3++;
        }
        this.mRowGap = i2;
    }

    public final void applyHighlightColorInternal(int i) {
        for (int i2 = 0; i2 < getChildCount(); i2++) {
            Drawable selectedStateDrawable = getSelectedStateDrawable(getChildAt(i2).findViewById(R.id.calendar_date_picker_single_date_container));
            if (selectedStateDrawable instanceof GradientDrawable) {
                ((GradientDrawable) selectedStateDrawable).setColor(i);
            }
        }
    }

    public void bind(int i, int i2, int i3, boolean z, int i4, long j, long j2) {
        boolean z2 = this.mHasBound;
        boolean z3 = (z2 && this.mLastBoundHighlightColor == i4) ? false : true;
        boolean z4 = (z2 && this.mLastBoundYear == i && this.mLastBoundMonth == i2 && this.mLastBoundDay == i3) ? false : true;
        boolean z5 = (z2 && this.mLastBoundLunarMode == z) ? false : true;
        boolean z6 = (z2 && this.mLastBoundMinDate == j && this.mLastBoundMaxDate == j2) ? false : true;
        if (z3 || z4 || z5 || z6) {
            if (z3 && i4 != 0) {
                applyHighlightColorInternal(i4);
            }
            if (z4 || z5 || z6) {
                this.mLunarMode = z;
                this.mYear = i;
                this.mMonth = i2;
                this.mDay = i3;
                this.mMinDate = j;
                this.mMaxDate = j2;
                if (z6) {
                    this.mMinKey = toYmdKey(j);
                    this.mMaxKey = toYmdKey(j2);
                }
                refresh(i, i2, i3);
            }
            this.mHasBound = true;
            this.mLastBoundYear = i;
            this.mLastBoundMonth = i2;
            this.mLastBoundDay = i3;
            this.mLastBoundLunarMode = z;
            this.mLastBoundHighlightColor = i4;
            this.mLastBoundMinDate = j;
            this.mLastBoundMaxDate = j2;
        }
    }

    public final void clearSelectedChild() {
        View view = this.mSelectedChild;
        if (view != null) {
            view.setSelected(false);
            this.mSelectedChild = null;
        }
    }

    public final View getChildAt(int i, int i2) {
        return getChildAt((i * this.mColumnCount) + i2);
    }

    public final String getChineseDate(String str) {
        return String.format("农历%d月%s", Integer.valueOf(this.mCalendar.get(6) + 1), str);
    }

    public final String getDayContentDescription(long j) {
        return AccessibilityUtils.getYearMonthDayOfWeekDay(j);
    }

    public final int getFirstWeekDayInMonth(int i, int i2) {
        return this.mHelper.getFirstWeekDayInMonth(i, i2);
    }

    public String getLunarMessage(int i, int i2, int i3) {
        return this.mHelper.getLunarMessage(i, i2, i3);
    }

    public final Drawable getSelectedStateDrawable(View view) {
        return this.mHelper.getSelectedStateDrawable(view);
    }

    public final String getSpecialHoliday(int i, int i2, int i3, Calendar calendar) {
        return SpecialHolidayParser.parseSpecialHoliday(i, i2, i3, calendar);
    }

    public final long getTimeMillis(int i, int i2, int i3) {
        return this.mCalendar.set(1, i).set(5, i2).set(9, i3).getTimeInMillis();
    }

    public final void init(Context context, AttributeSet attributeSet, int i, int i2) {
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CalendarGridLayout, i, i2);
        this.mLunarMode = typedArrayObtainStyledAttributes.getBoolean(R.styleable.CalendarGridLayout_lunarMode, false);
        typedArrayObtainStyledAttributes.recycle();
        this.mMaxRowCount = getRowCount();
        this.mColumnCount = getColumnCount();
        this.mHelper = new CalendarDatePickerHelper(context);
        this.mCalendar = new Calendar();
        this.mSmallRowGap = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_calendar_grid_layout_row_gap_small);
        this.mMediumRowGap = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_calendar_grid_layout_row_gap_medium);
        this.mLargeRowGap = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_calendar_grid_layout_row_gap_large);
        this.mChildPaddingIfNotLunarMode = context.getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_calendar_grid_layout_child_padding);
        LayoutInflater layoutInflaterFrom = LayoutInflater.from(context);
        for (int i3 = 0; i3 < this.mMaxRowCount; i3++) {
            for (int i4 = 0; i4 < this.mColumnCount; i4++) {
                layoutInflaterFrom.inflate(R.layout.miuix_appcompat_calendar_grid_child_layout, (ViewGroup) this, true);
            }
        }
    }

    public final boolean isDayInRange(int i, int i2, int i3) {
        if (i3 <= 0) {
            return false;
        }
        long ymdKey = toYmdKey(i, i2, i3);
        return ymdKey >= this.mMinKey && ymdKey <= this.mMaxKey;
    }

    public final boolean isHoliday() {
        return this.mHelper.isLunarHoliday() || this.mHelper.isSolarHoliday() || this.mSpecialHoliday;
    }

    public final void notifyChildLunarModeMaybeChanged(int i, int i2) {
        View childAt = getChildAt(i, i2);
        int i3 = this.mLunarMode ? 0 : this.mChildPaddingIfNotLunarMode;
        View viewFindViewById = childAt.findViewById(R.id.calendar_date_picker_lunar_message);
        View viewFindViewById2 = childAt.findViewById(R.id.calendar_date_picker_child_container);
        if (viewFindViewById != null) {
            viewFindViewById.setVisibility(this.mLunarMode ? 0 : 8);
        }
        if (viewFindViewById2 != null) {
            viewFindViewById2.setPadding(i3, 0, i3, 0);
        }
    }

    @Override // android.widget.GridLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        setMeasuredDimension(View.MeasureSpec.getSize(i), (this.mMaxRowCount * getChildAt(0).getMeasuredHeight()) + ((this.mMaxRowCount - 1) * this.mSmallRowGap));
    }

    public final void refresh(int i, int i2, int i3) {
        clearSelectedChild();
        this.mYear = i;
        this.mMonth = i2;
        this.mFirstWeekDayInMonth = getFirstWeekDayInMonth(i, i2);
        int iDaysInMonth = this.mCalendar.daysInMonth(i, i2);
        boolean z = i3 > 0;
        int iMin = z ? Math.min(i3, iDaysInMonth) : 0;
        this.mDay = iMin;
        int i4 = 1;
        for (int i5 = 0; i5 < this.mColumnCount; i5++) {
            if (i5 < this.mFirstWeekDayInMonth) {
                setDate(0, i5, 0, 0);
            } else {
                setDate(0, i5, i2, i4);
                if (z && i4 == iMin && isDayInRange(i, i2, i4)) {
                    selectChild(0, i5);
                }
                i4++;
            }
            notifyChildLunarModeMaybeChanged(0, i5);
        }
        int i6 = 1;
        for (int i7 = 1; i7 < this.mMaxRowCount; i7++) {
            boolean z2 = false;
            for (int i8 = 0; i8 < this.mColumnCount; i8++) {
                if (i4 > iDaysInMonth) {
                    setDate(i7, i8, 0, 0);
                } else {
                    setDate(i7, i8, i2, i4);
                    if (z && i4 == iMin && isDayInRange(i, i2, i4)) {
                        selectChild(i7, i8);
                    }
                    i4++;
                    z2 = true;
                }
                notifyChildLunarModeMaybeChanged(i7, i8);
            }
            if (z2) {
                i6++;
            }
        }
        adjustRowGap(i6);
    }

    public final void selectChild(int i, int i2) {
        selectChild(getChildAt(i, i2));
    }

    public void selectChild(View view) {
        if (view == null || view.isSelected()) {
            return;
        }
        View view2 = this.mSelectedChild;
        if (view2 != null) {
            view2.setSelected(false);
        }
        view.setSelected(true);
        this.mSelectedChild = view;
    }

    public final void setChildMarginBottom(View view, int i) {
        this.mHelper.setChildMarginBottom(view, i);
    }

    public final void setDate(int i, int i2, int i3, int i4) {
        Context context = getContext();
        View childAt = getChildAt(i, i2);
        TextView textView = (TextView) childAt.findViewById(R.id.calendar_date_picker_date);
        TextView textView2 = (TextView) childAt.findViewById(R.id.calendar_date_picker_lunar_message);
        if (i4 == 0) {
            textView.setText((CharSequence) null);
            childAt.setEnabled(false);
            textView.setEnabled(false);
            textView2.setEnabled(false);
            childAt.setImportantForAccessibility(2);
        } else {
            textView.setText(String.valueOf(i4));
            boolean zIsDayInRange = isDayInRange(this.mYear, i3, i4);
            childAt.setEnabled(zIsDayInRange);
            textView.setEnabled(zIsDayInRange);
            textView2.setEnabled(zIsDayInRange);
            childAt.setImportantForAccessibility(zIsDayInRange ? 1 : 2);
        }
        int fontLevel = MiuixUIUtils.getFontLevel(context);
        DensityChangedHelper.updateTextSizeDpUnit(textView, fontLevel == 2 ? 20 : 18);
        long timeMillis = getTimeMillis(this.mYear, i3, i4);
        String specialHoliday = getSpecialHoliday(i, i2, this.mFirstWeekDayInMonth, this.mCalendar);
        boolean zIsEmpty = TextUtils.isEmpty(specialHoliday);
        this.mSpecialHoliday = !zIsEmpty;
        if (zIsEmpty && i3 >= 0 && i4 > 0) {
            specialHoliday = getLunarMessage(this.mYear, i3, i4);
        }
        textView2.setText(specialHoliday);
        String dayContentDescription = getDayContentDescription(timeMillis);
        if (!TextUtils.isEmpty(specialHoliday) && this.mLunarMode) {
            if (!isHoliday()) {
                specialHoliday = getChineseDate(specialHoliday);
            }
            dayContentDescription = dayContentDescription + specialHoliday;
        }
        childAt.setContentDescription(dayContentDescription);
        DensityChangedHelper.updateTextSizeDpUnit(textView2, fontLevel == 2 ? 12 : 10);
    }

    public final long toYmdKey(long j) {
        if (j == Long.MIN_VALUE) {
            return Long.MIN_VALUE;
        }
        if (j == Long.MAX_VALUE) {
            return Long.MAX_VALUE;
        }
        this.mCalendar.setTimeInMillis(j);
        return toYmdKey(this.mCalendar.get(1), this.mCalendar.get(5), this.mCalendar.get(9));
    }

    public void updateCurrentDate(int i, int i2, int i3) {
        this.mYear = i;
        this.mMonth = i2;
        this.mDay = i3;
    }
}
