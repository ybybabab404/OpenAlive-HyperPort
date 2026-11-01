package miuix.pickerwidget.widget.Calendar;

import android.R;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.GridLayout;
import miuix.pickerwidget.date.Calendar;
import miuix.pickerwidget.date.CalendarFormatSymbols;

/* JADX INFO: loaded from: classes2.dex */
class CalendarDatePickerHelper {
    public Calendar mCalendar = new Calendar();
    public String[] mChineseDays;
    public String[] mChineseDigits;
    public String[] mChineseMonths;
    public boolean mIsLunarHoliday;
    public boolean mIsSolarHoliday;
    public String mLabelDay;
    public String mLabelMonth;
    public String mLabelYear;
    public SparseArray mLunarHolidayCache;
    public SparseArray mSolarHolidayCache;

    public CalendarDatePickerHelper(Context context) {
        CalendarFormatSymbols orCreate = CalendarFormatSymbols.getOrCreate(context);
        this.mSolarHolidayCache = orCreate.getSolarHolidays();
        this.mLunarHolidayCache = orCreate.getLunarHolidays();
        this.mChineseDays = orCreate.getChineseDays();
        this.mChineseMonths = orCreate.getChineseMonths();
        this.mChineseDigits = orCreate.getChineseDigits();
        this.mLabelYear = orCreate.getLabelYear();
        this.mLabelMonth = orCreate.getLabelMonth();
        this.mLabelDay = orCreate.getLabelDay();
    }

    public final int generateCacheKey(int i, int i2) {
        return ((i + 1) * 100) + i2;
    }

    public int getFirstWeekDayInMonth(int i, int i2) {
        this.mCalendar.set(1, i).set(5, i2).set(9, 1);
        return (this.mCalendar.get(14) + 5) % 7;
    }

    /* JADX WARN: Code duplicated, block: B:20:0x005f  */
    public String getFullLunarMessage(int i, int i2, int i3) {
        String str;
        this.mCalendar.set(1, i).set(5, i2).set(9, i3);
        if (this.mCalendar.outOfChineseCalendarRange()) {
            return null;
        }
        int i4 = this.mCalendar.get(2);
        int i5 = this.mCalendar.get(6);
        int i6 = this.mCalendar.get(10);
        StringBuilder sb = new StringBuilder();
        if (i4 == 0) {
            sb.append(this.mChineseDigits[0]);
        }
        while (i4 > 0) {
            int i7 = i4 % 10;
            i4 /= 10;
            if (i7 >= 0) {
                String[] strArr = this.mChineseDigits;
                if (i7 < strArr.length) {
                    sb.insert(0, strArr[i7]);
                }
            }
        }
        String str2 = "";
        if (i5 >= 0) {
            String[] strArr2 = this.mChineseMonths;
            if (i5 < strArr2.length) {
                str = strArr2[i5];
            } else {
                str = "";
            }
        } else {
            str = "";
        }
        if (i6 > 0) {
            String[] strArr3 = this.mChineseDays;
            if (i6 <= strArr3.length) {
                str2 = strArr3[i6 - 1];
            }
        }
        return ((Object) sb) + this.mLabelYear + str + this.mLabelMonth + str2;
    }

    public String getLunarMessage(int i, int i2, int i3) {
        int i4;
        this.mCalendar.set(1, i).set(5, i2).set(9, i3);
        String messageFromLunarHolidayCache = getMessageFromLunarHolidayCache(this.mCalendar.get(6), this.mCalendar.get(10));
        if (TextUtils.isEmpty(messageFromLunarHolidayCache)) {
            messageFromLunarHolidayCache = getMessageFromSolarHolidayCache(i2, i3);
        }
        if (TextUtils.isEmpty(messageFromLunarHolidayCache) && (i4 = this.mCalendar.get(10)) > 0) {
            String[] strArr = this.mChineseDays;
            if (i4 <= strArr.length) {
                return strArr[i4 - 1];
            }
        }
        return messageFromLunarHolidayCache;
    }

    public final String getMessageFromLunarHolidayCache(int i, int i2) {
        String str = (String) this.mLunarHolidayCache.get(generateCacheKey(i, i2));
        this.mIsLunarHoliday = !TextUtils.isEmpty(str);
        return str;
    }

    public final String getMessageFromSolarHolidayCache(int i, int i2) {
        String str = (String) this.mSolarHolidayCache.get(generateCacheKey(i, i2));
        this.mIsSolarHoliday = !TextUtils.isEmpty(str);
        return str;
    }

    public Drawable getSelectedStateDrawable(View view) {
        Drawable background = view.getBackground();
        if (!(background instanceof StateListDrawable)) {
            return null;
        }
        StateListDrawable stateListDrawable = (StateListDrawable) background;
        stateListDrawable.setState(new int[]{R.attr.state_selected});
        return stateListDrawable.getCurrent();
    }

    public boolean isLunarHoliday() {
        return this.mIsLunarHoliday;
    }

    public boolean isSolarHoliday() {
        return this.mIsSolarHoliday;
    }

    public void setChildMarginBottom(View view, int i) {
        GridLayout.LayoutParams layoutParams = (GridLayout.LayoutParams) view.getLayoutParams();
        layoutParams.bottomMargin = i;
        view.setLayoutParams(layoutParams);
    }
}
