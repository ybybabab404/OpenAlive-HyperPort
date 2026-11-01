package miuix.pickerwidget.widget.Calendar;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import java.time.ZonedDateTime;
import java.util.Locale;
import miuix.core.util.MiuixUIUtils;
import miuix.internal.util.ViewUtils;
import miuix.os.DeviceHelper;
import miuix.pickerwidget.R;
import miuix.pickerwidget.date.Calendar;
import miuix.pickerwidget.widget.DatePicker;
import miuix.view.DensityChangedHelper;

/* JADX INFO: loaded from: classes2.dex */
public class CalendarDatePicker extends FrameLayout {
    public static final int CALENDAR_DAY_STYLE = 4;
    public Drawable mArrowDown;
    public Drawable mArrowUp;
    public Calendar mCalendar;
    public CurrentDate mCurrent;
    public Drawable mCurrentArrow;
    public OnDateChangedListener mDateChangedListener;
    public DatePicker mDatePicker;
    public RecyclerView.OnScrollListener mExtraScrollListener;
    public boolean mFixedWidthEnabled;
    public int mFixedWidthFoldInLandscape;
    public int mFixedWidthFoldInPortrait;
    public int mFixedWidthTablet;
    public int mFontLevel;
    public ViewGroup mHeaderLayout;
    public TextView mHeaderView;
    public CalendarDatePickerHelper mHelper;
    public boolean mIsDatePickerShowing;
    public View mLeftArrowView;
    public RecyclerView.OnScrollListener mOnScrollListener;
    public CalendarDatePickerPanel mPanel;
    public miuix.recyclerview.widget.RecyclerView mRecyclerView;
    public View mRightArrowView;
    public OnToggleListener mToggleListener;
    public int mVisibleMonth;
    public int mVisibleYear;
    public LinearLayout mWeekDayLayout;

    public class CurrentDate {
        public int dayOfMonth;
        public int month;
        public int year;

        public CurrentDate() {
            this.year = CalendarDatePicker.this.mCalendar.get(1);
            this.month = CalendarDatePicker.this.mCalendar.get(5);
            this.dayOfMonth = CalendarDatePicker.this.mCalendar.get(9);
        }

        public int getDayOfMonth() {
            return this.dayOfMonth;
        }

        public int getMonth() {
            return this.month;
        }

        public int getYear() {
            return this.year;
        }

        public void setDayOfMonth(int i) {
            if (this.dayOfMonth != i) {
                this.dayOfMonth = i;
            }
        }

        public void setMonth(int i) {
            if (this.month != i) {
                this.month = i;
            }
        }

        public void setYear(int i) {
            if (this.year != i) {
                this.year = i;
            }
        }

        public void updateDate(int i, int i2, int i3) {
            setYear(i);
            setMonth(i2);
            setDayOfMonth(i3);
        }
    }

    public interface OnDateChangedListener {
        void onDateChanged(CalendarDatePicker calendarDatePicker, int i, int i2, int i3, String str);
    }

    public interface OnToggleListener {
        void onToggle(boolean z);
    }

    public static class SavedState extends View.BaseSavedState {
        public static final Parcelable.ClassLoaderCreator CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: miuix.pickerwidget.widget.Calendar.CalendarDatePicker.SavedState.1
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.ClassLoaderCreator
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public int day;
        public boolean isDatePickerVisible;
        public int month;
        public int visibleMonth;
        public int visibleYear;
        public int year;

        public SavedState(Parcel parcel) {
            super(parcel);
            this.year = parcel.readInt();
            this.month = parcel.readInt();
            this.day = parcel.readInt();
            this.visibleYear = parcel.readInt();
            this.visibleMonth = parcel.readInt();
            this.isDatePickerVisible = parcel.readInt() != 0;
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.year = parcel.readInt();
            this.month = parcel.readInt();
            this.day = parcel.readInt();
            this.visibleYear = parcel.readInt();
            this.visibleMonth = parcel.readInt();
            this.isDatePickerVisible = parcel.readInt() != 0;
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
            parcel.writeInt(this.visibleYear);
            parcel.writeInt(this.visibleMonth);
            parcel.writeInt(this.isDatePickerVisible ? 1 : 0);
        }
    }

    public CalendarDatePicker(@NonNull Context context) {
        this(context, null);
    }

    public CalendarDatePicker(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CalendarDatePicker(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public CalendarDatePicker(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mFontLevel = 0;
        this.mIsDatePickerShowing = false;
        this.mFixedWidthEnabled = false;
        init(context, attributeSet, i, i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getVisibleMonth() {
        return this.mVisibleMonth;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getVisibleYear() {
        return this.mVisibleYear;
    }

    public static long toYmdKey(int i, int i2, int i3) {
        return (((long) i) * 10000) + (((long) i2) * 100) + ((long) i3);
    }

    public final void applyChildrenFixedWidth(int i) {
        applyFixedWidth(this.mHeaderLayout, i);
        applyFixedWidth(this.mWeekDayLayout, i);
        applyFixedWidth(this.mRecyclerView, i);
        applyFixedWidth(this.mDatePicker, i);
    }

    public final void applyFixedWidth(View view, int i) {
        if (view == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = i;
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            ((LinearLayout.LayoutParams) layoutParams).gravity = 1;
        }
        view.setLayoutParams(layoutParams);
    }

    public final void clearChildrenFixedWidth() {
        clearFixedWidth(this.mHeaderLayout);
        clearFixedWidth(this.mWeekDayLayout);
        clearFixedWidth(this.mRecyclerView);
        clearFixedWidth(this.mDatePicker);
    }

    public final void clearFixedWidth(View view) {
        if (view == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = -1;
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            ((LinearLayout.LayoutParams) layoutParams).gravity = 0;
        }
        view.setLayoutParams(layoutParams);
    }

    public final void ensureFixedWidthDimension() {
        if (this.mFixedWidthTablet == 0 && this.mFixedWidthFoldInPortrait == 0 && this.mFixedWidthFoldInLandscape == 0) {
            this.mFixedWidthTablet = getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_calendar_fixed_max_width_tablet);
            this.mFixedWidthFoldInPortrait = getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_calendar_fixed_max_width_fold_in_portrait);
            this.mFixedWidthFoldInLandscape = getResources().getDimensionPixelSize(R.dimen.miuix_appcompat_calendar_fixed_max_width_fold_in_landscape);
        }
    }

    @NonNull
    public MonthsPagerAdapter getAdapter() {
        return (MonthsPagerAdapter) this.mRecyclerView.getAdapter();
    }

    public int getCurrentItemPosition() {
        return getLayoutManager().findFirstVisibleItemPosition();
    }

    @NonNull
    public DatePicker getDatePicker() {
        return this.mDatePicker;
    }

    public int getDayOfMonth() {
        return this.mCurrent.getDayOfMonth();
    }

    public String getFullLunarMessage(int i, int i2, int i3) {
        return this.mHelper.getFullLunarMessage(i, i2, i3);
    }

    @NonNull
    public View getHeaderLayout() {
        return this.mHeaderLayout;
    }

    @NonNull
    public LinearLayoutManager getLayoutManager() {
        return (LinearLayoutManager) this.mRecyclerView.getLayoutManager();
    }

    public String getLunarMessage(int i, int i2, int i3) {
        return this.mHelper.getLunarMessage(i, i2, i3);
    }

    public long getMaxDate() {
        return this.mDatePicker.getMaxDate();
    }

    public long getMinDate() {
        return this.mDatePicker.getMinDate();
    }

    public int getMonth() {
        return this.mCurrent.getMonth();
    }

    public int getYear() {
        return this.mCurrent.getYear();
    }

    public final void hideChildren(View... viewArr) {
        for (View view : viewArr) {
            if (view != null) {
                view.setVisibility(8);
            }
        }
    }

    public final void init(Context context, AttributeSet attributeSet, int i, int i2) {
        this.mHelper = new CalendarDatePickerHelper(context);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CalendarDatePicker, i, i2);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(R.styleable.CalendarDatePicker_lunarMode, false);
        typedArrayObtainStyledAttributes.recycle();
        FrameLayout.inflate(context, R.layout.miuix_appcompat_calendar_date_picker_inflate_layout, this);
        this.mPanel = (CalendarDatePickerPanel) findViewById(R.id.calendar_date_picker_panel);
        this.mArrowUp = ContextCompat.getDrawable(context, R.drawable.miuix_appcompat_calendar_date_picker_arrow_up);
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.miuix_appcompat_calendar_date_picker_arrow_down);
        this.mArrowDown = drawable;
        this.mCurrentArrow = drawable;
        this.mCalendar = new Calendar();
        CurrentDate currentDate = new CurrentDate();
        this.mCurrent = currentDate;
        this.mVisibleYear = currentDate.getYear();
        this.mVisibleMonth = this.mCurrent.getMonth();
        setupHeader();
        setupWeekDay();
        setupDatePicker();
        setupRecyclerView();
        refreshHeader();
        setLunarMode(z);
        if (getId() == -1) {
            setId(R.id.miuix_appcompat_calendar_date_picker);
        }
    }

    public boolean isLargeFontLevel() {
        if (this.mFontLevel == 0) {
            this.mFontLevel = MiuixUIUtils.getFontLevel(getContext());
        }
        return this.mFontLevel == 2;
    }

    public boolean isLunarMode() {
        return getAdapter().isLunarMode();
    }

    public final void notifyDateChange(CalendarDatePicker calendarDatePicker, int i, int i2, int i3) {
        OnDateChangedListener onDateChangedListener = this.mDateChangedListener;
        if (onDateChangedListener != null) {
            onDateChangedListener.onDateChanged(calendarDatePicker, i, i2, i3, getLunarMessage(i, i2, i3));
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mRecyclerView.addOnScrollListener(this.mOnScrollListener);
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (ViewUtils.isLayoutRtl(this)) {
            scrollRecyclerViewToCurrent();
        }
        updateFixedWidthState();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mRecyclerView.removeOnScrollListener(this.mOnScrollListener);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            Log.w("CalendarDatePicker", "Wrong state class, expecting SavedState! This usually happens when two views of different type have the same id in the same hierarchy.");
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        this.mCurrent.updateDate(savedState.year, savedState.month, savedState.day);
        this.mVisibleYear = savedState.visibleYear;
        this.mVisibleMonth = savedState.visibleMonth;
        refreshHeader();
        if (savedState.isDatePickerVisible) {
            toggle();
        } else {
            smartScrollToSpecifiedMonth(savedState.visibleYear, savedState.visibleMonth, false);
            getAdapter().notifySelectedDateChanged(savedState.year, savedState.month, savedState.day);
        }
        notifyDateChange(this, savedState.year, savedState.month, savedState.day);
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.year = getYear();
        savedState.month = getMonth();
        savedState.day = getDayOfMonth();
        savedState.visibleYear = getVisibleYear();
        savedState.visibleMonth = getVisibleMonth();
        savedState.isDatePickerVisible = this.mIsDatePickerShowing;
        return savedState;
    }

    public final int positionToDayOfWeek(int i, java.util.Calendar calendar) {
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        int maximum = calendar.getMaximum(7);
        int i2 = i + firstDayOfWeek;
        return i2 > maximum ? i2 - maximum : i2;
    }

    public final void postSmoothRecyclerViewScroll(final int i) {
        this.mRecyclerView.post(new Runnable() { // from class: miuix.pickerwidget.widget.Calendar.CalendarDatePicker$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mRecyclerView.smoothScrollToPosition(i);
            }
        });
    }

    public final void refreshGrid(boolean z) {
        this.mVisibleYear = getYear();
        this.mVisibleMonth = getMonth();
        smartScrollToSpecifiedMonth(getVisibleYear(), getVisibleMonth(), z);
        getAdapter().notifySelectedDateChanged(getYear(), getMonth(), getDayOfMonth());
    }

    public final void refreshHeader() {
        char[] dateFormatOrder = DateFormat.getDateFormatOrder(getContext());
        StringBuilder sb = new StringBuilder(8);
        boolean z = true;
        for (char c : dateFormatOrder) {
            if (c != 'd') {
                if (!z) {
                    sb.append('/');
                }
                if (c == 'y') {
                    sb.append(String.format("%04d", Integer.valueOf(getVisibleYear())));
                } else if (c == 'M') {
                    sb.append(String.format("%02d", Integer.valueOf(getVisibleMonth() + 1)));
                }
                z = false;
            }
        }
        this.mHeaderView.setText(sb.toString());
    }

    public final int resolveFixedWidth() {
        int iDetectType = DeviceHelper.detectType(getContext());
        ensureFixedWidthDimension();
        if (iDetectType == 2) {
            return this.mFixedWidthTablet;
        }
        if (iDetectType == 3) {
            return getResources().getConfiguration().orientation == 2 ? this.mFixedWidthFoldInLandscape : this.mFixedWidthFoldInPortrait;
        }
        return 0;
    }

    public final void safeUpdateDatePicker(int i, int i2, int i3) {
        if (this.mDatePicker != null) {
            this.mDatePicker.updateDate(i, i2, Math.min(this.mCalendar.daysInMonth(i, i2), i3));
        }
    }

    public void scrollRecyclerViewToCurrent() {
        smartScrollToSpecifiedMonth(getYear(), getMonth(), false);
    }

    public void setChildDatePickerFiveLinesAppearance(boolean z) {
        getDatePicker().setFiveLinesAppearance(z);
    }

    public void setDate(long j) {
        setDate(j, true);
    }

    public void setDate(long j, boolean z) {
        this.mCalendar.setSafeTimeInMillis(j, isLunarMode());
        int i = this.mCalendar.get(1);
        int i2 = this.mCalendar.get(5);
        int i3 = this.mCalendar.get(9);
        this.mCurrent.updateDate(i, i2, i3);
        this.mVisibleYear = i;
        this.mVisibleMonth = i2;
        getAdapter().notifySelectedDateChanged(i, i2, i3);
        safeUpdateDatePicker(i, i2, i3);
        refreshHeader();
        refreshGrid(z);
    }

    @RequiresApi
    public void setDate(@NonNull ZonedDateTime zonedDateTime) {
        setDate(zonedDateTime, true);
    }

    @RequiresApi
    public void setDate(@NonNull ZonedDateTime zonedDateTime, boolean z) {
        setDate(this.mCalendar.set(1, zonedDateTime.getYear()).set(5, zonedDateTime.getMonth().getValue() - 1).set(9, zonedDateTime.getDayOfMonth()).getTimeInMillis(), z);
    }

    public void setFixedWidth(boolean z) {
        this.mFixedWidthEnabled = z;
        updateFixedWidthState();
    }

    public void setHighlightColor(@ColorInt int i) {
        setHighlightColor(i, true);
    }

    public void setHighlightColor(@ColorInt int i, boolean z) {
        getAdapter().notifyHighlightColorChanged(i);
        if (z) {
            this.mDatePicker.setLabelTextColor(i);
            this.mDatePicker.setTextColorHighlight(i);
        }
    }

    public void setLunarMode(boolean z) {
        getAdapter().notifyLunarModeChanged(z);
    }

    public void setMaxDate(long j) {
        getAdapter().notifyMaxDateChanged(j);
        this.mDatePicker.setMaxDate(j);
        if (toYmdKey(getYear(), getMonth(), getDayOfMonth()) > toYmdKey(j)) {
            setDate(j, false);
            notifyDateChange(this, getYear(), getMonth(), getDayOfMonth());
        }
    }

    public void setMinDate(long j) {
        getAdapter().notifyMinDateChanged(j);
        this.mDatePicker.setMinDate(j);
        if (toYmdKey(getYear(), getMonth(), getDayOfMonth()) < toYmdKey(j)) {
            setDate(j, false);
            notifyDateChange(this, getYear(), getMonth(), getDayOfMonth());
        }
    }

    public void setOnDateChangedListener(OnDateChangedListener onDateChangedListener) {
        this.mDateChangedListener = onDateChangedListener;
    }

    public void setOnToggleListener(@Nullable OnToggleListener onToggleListener) {
        this.mToggleListener = onToggleListener;
    }

    public void setRecyclerViewExtraOnScrollListener(RecyclerView.OnScrollListener onScrollListener) {
        this.mExtraScrollListener = onScrollListener;
    }

    public void setWrapContent(boolean z) {
        CalendarDatePickerPanel calendarDatePickerPanel = this.mPanel;
        if (calendarDatePickerPanel != null) {
            calendarDatePickerPanel.setWrapContent(z);
        }
    }

    public final void setupDatePicker() {
        DatePicker datePicker = (DatePicker) findViewById(R.id.date_picker);
        this.mDatePicker = datePicker;
        datePicker.init(this.mCalendar.get(1), this.mCalendar.get(5), this.mCalendar.get(9), new DatePicker.OnDateChangedListener() { // from class: miuix.pickerwidget.widget.Calendar.CalendarDatePicker.8
            @Override // miuix.pickerwidget.widget.DatePicker.OnDateChangedListener
            public void onDateChanged(DatePicker datePicker2, int i, int i2, int i3, boolean z) {
                CalendarDatePicker.this.mCurrent.updateDate(i, i2, i3);
                CalendarDatePicker.this.mVisibleYear = i;
                CalendarDatePicker.this.mVisibleMonth = i2;
                CalendarDatePicker.this.getAdapter().notifySelectedDateChanged(i, i2, i3);
                CalendarDatePicker.this.refreshHeader();
                CalendarDatePicker calendarDatePicker = CalendarDatePicker.this;
                calendarDatePicker.notifyDateChange(calendarDatePicker, i, i2, i3);
            }
        });
    }

    public final void setupHeader() {
        ViewGroup viewGroup = (ViewGroup) findViewById(R.id.calendar_date_picker_header_layout);
        this.mHeaderLayout = viewGroup;
        this.mHeaderView = (TextView) viewGroup.findViewById(R.id.calendar_date_picker_header);
        this.mLeftArrowView = this.mHeaderLayout.findViewById(R.id.left_arrow);
        this.mRightArrowView = this.mHeaderLayout.findViewById(R.id.right_arrow);
        ViewGroup viewGroup2 = this.mHeaderLayout;
        if (viewGroup2 == null || this.mHeaderView == null) {
            return;
        }
        viewGroup2.setOnTouchListener(new View.OnTouchListener() { // from class: miuix.pickerwidget.widget.Calendar.CalendarDatePicker.1
            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (CalendarDatePicker.this.mRecyclerView.getScrollState() != 0) {
                    return false;
                }
                boolean zIsLayoutRtl = ViewUtils.isLayoutRtl(view);
                float left = CalendarDatePicker.this.mHeaderView.getLeft();
                float right = CalendarDatePicker.this.mHeaderView.getRight();
                float x = motionEvent.getX();
                if (motionEvent.getAction() == 1) {
                    if (x > left && x < right) {
                        CalendarDatePicker.this.toggle();
                    } else if (CalendarDatePicker.this.mLeftArrowView.getVisibility() == 0 && CalendarDatePicker.this.mRightArrowView.getVisibility() == 0) {
                        if ((zIsLayoutRtl || x <= right) && (!zIsLayoutRtl || x >= left)) {
                            CalendarDatePicker.this.smoothScrollToLastMonth();
                        } else {
                            CalendarDatePicker.this.smoothScrollToNextMonth();
                        }
                    }
                }
                return true;
            }
        });
        DensityChangedHelper.updateTextSizeDpUnit(this.mHeaderView, isLargeFontLevel() ? 28 : 16);
        setupHeaderAccessibility();
    }

    public final void setupHeaderAccessibility() {
        final View.OnClickListener onClickListener = new View.OnClickListener() { // from class: miuix.pickerwidget.widget.Calendar.CalendarDatePicker.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (view == null || view.getVisibility() != 0) {
                    return;
                }
                if (view == CalendarDatePicker.this.mHeaderView) {
                    CalendarDatePicker.this.toggle();
                } else if (view == CalendarDatePicker.this.mLeftArrowView) {
                    CalendarDatePicker.this.smoothScrollToLastMonth();
                } else {
                    CalendarDatePicker.this.smoothScrollToNextMonth();
                }
            }
        };
        AccessibilityDelegateCompat accessibilityDelegateCompat = new AccessibilityDelegateCompat() { // from class: miuix.pickerwidget.widget.Calendar.CalendarDatePicker.7
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.addAction(16);
                if (view == CalendarDatePicker.this.mLeftArrowView || view == CalendarDatePicker.this.mRightArrowView) {
                    accessibilityNodeInfoCompat.setClassName(Button.class.getName());
                }
            }

            @Override // androidx.core.view.AccessibilityDelegateCompat
            public boolean performAccessibilityAction(View view, int i, Bundle bundle) {
                if (i != 16) {
                    return super.performAccessibilityAction(view, i, bundle);
                }
                onClickListener.onClick(view);
                return true;
            }
        };
        for (int i = 0; i < this.mHeaderLayout.getChildCount(); i++) {
            ViewCompat.setAccessibilityDelegate(this.mHeaderLayout.getChildAt(i), accessibilityDelegateCompat);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void setupRecyclerView() {
        this.mRecyclerView = (miuix.recyclerview.widget.RecyclerView) findViewById(R.id.recycler_view);
        SmoothCalendarLayoutManager smoothCalendarLayoutManager = new SmoothCalendarLayoutManager(getContext(), 0, 0 == true ? 1 : 0) { // from class: miuix.pickerwidget.widget.Calendar.CalendarDatePicker.2
            @Override // androidx.recyclerview.widget.LinearLayoutManager
            public void calculateExtraLayoutSpace(RecyclerView.State state, int[] iArr) {
                CalendarDatePicker.this.mRecyclerView.getMeasuredWidth();
                iArr[0] = 0;
                CalendarDatePicker.this.mRecyclerView.getMeasuredWidth();
                iArr[1] = 0;
            }
        };
        MonthsPagerAdapter monthsPagerAdapter = new MonthsPagerAdapter(getContext(), this.mDatePicker.getMinDate(), this.mDatePicker.getMaxDate(), new MonthsPagerAdapter.OnDayClickListener() { // from class: miuix.pickerwidget.widget.Calendar.CalendarDatePicker.3
            @Override // miuix.pickerwidget.widget.Calendar.MonthsPagerAdapter.OnDayClickListener
            public void onDayClick(CalendarGridLayout calendarGridLayout, View view, MonthsPagerAdapter monthsPagerAdapter2) {
                calendarGridLayout.selectChild(view);
                TextView textView = (TextView) view.findViewById(R.id.calendar_date_picker_date);
                if (textView != null) {
                    String string = textView.getText().toString();
                    if (string.isEmpty()) {
                        return;
                    }
                    int i = Integer.parseInt(string);
                    int visibleYear = CalendarDatePicker.this.getVisibleYear();
                    int visibleMonth = CalendarDatePicker.this.getVisibleMonth();
                    CalendarDatePicker.this.mCurrent.updateDate(visibleYear, visibleMonth, i);
                    monthsPagerAdapter2.notifySelectedDateChanged(visibleYear, visibleMonth, i);
                    calendarGridLayout.updateCurrentDate(visibleYear, visibleMonth, i);
                    CalendarDatePicker calendarDatePicker = CalendarDatePicker.this;
                    calendarDatePicker.notifyDateChange(calendarDatePicker, visibleYear, visibleMonth, i);
                }
            }
        });
        this.mOnScrollListener = new RecyclerView.OnScrollListener() { // from class: miuix.pickerwidget.widget.Calendar.CalendarDatePicker.4
            @Override // androidx.recyclerview.widget.RecyclerView.OnScrollListener
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                if (i == 0) {
                    int[] yearAndMonth = CalendarDatePicker.this.getAdapter().getYearAndMonth(CalendarDatePicker.this.getCurrentItemPosition());
                    int i2 = yearAndMonth[0];
                    int i3 = yearAndMonth[1];
                    CalendarDatePicker.this.mVisibleYear = i2;
                    CalendarDatePicker.this.mVisibleMonth = i3;
                    CalendarDatePicker.this.refreshHeader();
                }
                if (CalendarDatePicker.this.mExtraScrollListener != null) {
                    CalendarDatePicker.this.mExtraScrollListener.onScrollStateChanged(recyclerView, i);
                }
            }
        };
        this.mRecyclerView.setLayoutManager(smoothCalendarLayoutManager);
        this.mRecyclerView.setAdapter(monthsPagerAdapter);
        this.mRecyclerView.setNestedScrollingEnabled(false);
        new PagerSnapHelper().attachToRecyclerView(this.mRecyclerView);
        setupRecyclerViewAccessibility();
        smartScrollToSpecifiedMonth(getYear(), getMonth(), false);
    }

    public final void setupRecyclerViewAccessibility() {
        ViewCompat.setAccessibilityDelegate(this.mRecyclerView, new AccessibilityDelegateCompat() { // from class: miuix.pickerwidget.widget.Calendar.CalendarDatePicker.5
            @Override // androidx.core.view.AccessibilityDelegateCompat
            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat);
                accessibilityNodeInfoCompat.setScrollable(false);
            }
        });
    }

    public final void setupWeekDay() {
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.calendar_date_picker_weekday_layout);
        this.mWeekDayLayout = linearLayout;
        if (linearLayout != null) {
            int i = isLargeFontLevel() ? 25 : 13;
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            Locale locale = this.mWeekDayLayout.getResources().getConfiguration().locale;
            for (int i2 = 0; i2 < this.mWeekDayLayout.getChildCount(); i2++) {
                TextView textView = (TextView) this.mWeekDayLayout.getChildAt(i2);
                DensityChangedHelper.updateTextSizeDpUnit(textView, i);
                calendar.setFirstDayOfWeek(2);
                calendar.set(7, positionToDayOfWeek(i2, calendar));
                textView.setText(calendar.getDisplayName(7, CALENDAR_DAY_STYLE, locale));
                textView.setContentDescription(String.format(getContext().getString(R.string.calendar_date_picker_day_of_week_column_header), calendar.getDisplayName(7, 2, Locale.getDefault())));
            }
        }
    }

    public final void showChildren(View... viewArr) {
        int i = 0;
        for (View view : viewArr) {
            if (view != null) {
                view.setVisibility(0);
            }
        }
        if (viewArr.length == 1 && viewArr[0] == this.mDatePicker) {
            i = 4;
        }
        this.mLeftArrowView.setVisibility(i);
        this.mRightArrowView.setVisibility(i);
    }

    public final void smartScrollToSpecifiedMonth(int i, int i2, boolean z) {
        int position = getAdapter().getPosition(i, i2);
        if (!z) {
            this.mRecyclerView.scrollToPosition(position);
            return;
        }
        int currentItemPosition = getCurrentItemPosition();
        if (currentItemPosition == -1) {
            currentItemPosition = getAdapter().getPosition(getVisibleYear(), getVisibleMonth());
        }
        int i3 = position - currentItemPosition;
        if (i3 == 0) {
            this.mRecyclerView.scrollToPosition(position);
            return;
        }
        boolean z2 = Math.abs(i3) > 3;
        boolean z3 = i3 > 0;
        if (z2 && z3) {
            this.mRecyclerView.scrollToPosition(position - 3);
            postSmoothRecyclerViewScroll(position);
        } else if (!z2) {
            postSmoothRecyclerViewScroll(position);
        } else {
            this.mRecyclerView.scrollToPosition(position + 3);
            postSmoothRecyclerViewScroll(position);
        }
    }

    public final void smoothScrollToLastMonth() {
        int currentItemPosition = getCurrentItemPosition() - 1;
        if (currentItemPosition >= 0) {
            this.mRecyclerView.smoothScrollToPosition(currentItemPosition);
        }
    }

    public final void smoothScrollToNextMonth() {
        int currentItemPosition = getCurrentItemPosition() + 1;
        if (currentItemPosition < getAdapter().getItemCount()) {
            this.mRecyclerView.smoothScrollToPosition(currentItemPosition);
        }
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

    public void toggle() {
        Drawable drawable = this.mCurrentArrow;
        Drawable drawable2 = this.mArrowUp;
        if (drawable == drawable2) {
            this.mCurrentArrow = this.mArrowDown;
            hideChildren(this.mDatePicker);
            showChildren(this.mWeekDayLayout, this.mRecyclerView);
            this.mIsDatePickerShowing = false;
            refreshGrid(true);
        } else {
            this.mCurrentArrow = drawable2;
            showChildren(this.mDatePicker);
            hideChildren(this.mWeekDayLayout, this.mRecyclerView);
            post(new Runnable() { // from class: miuix.pickerwidget.widget.Calendar.CalendarDatePicker$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CalendarDatePicker calendarDatePicker = this.f$0;
                    calendarDatePicker.safeUpdateDatePicker(calendarDatePicker.getYear(), calendarDatePicker.getMonth(), calendarDatePicker.getDayOfMonth());
                }
            });
            this.mIsDatePickerShowing = true;
        }
        this.mHeaderView.setCompoundDrawablesRelativeWithIntrinsicBounds((Drawable) null, (Drawable) null, this.mCurrentArrow, (Drawable) null);
        OnToggleListener onToggleListener = this.mToggleListener;
        if (onToggleListener != null) {
            onToggleListener.onToggle(this.mIsDatePickerShowing);
        }
    }

    public final void updateFixedWidthState() {
        if (this.mWeekDayLayout == null) {
            return;
        }
        int iResolveFixedWidth = resolveFixedWidth();
        if (!this.mFixedWidthEnabled || iResolveFixedWidth <= 0) {
            clearChildrenFixedWidth();
            return;
        }
        if (iResolveFixedWidth >= ((int) (getResources().getConfiguration().screenWidthDp * getResources().getDisplayMetrics().density))) {
            clearChildrenFixedWidth();
        } else {
            applyChildrenFixedWidth(iResolveFixedWidth);
        }
    }
}
