package miuix.appcompat.app;

/* JADX INFO: loaded from: classes2.dex */
public class CalendarDatePickerDialogCallBack {
    public int mDayOfMonth;
    public int mHourOfDay;
    public String mLunarMessage;
    public int mMinute;
    public int mMonth;
    public int mSpinnerSelection;
    public int mYear;

    public int getDayOfMonth() {
        return this.mDayOfMonth;
    }

    public int getHourOfDay() {
        return this.mHourOfDay;
    }

    public String getLunarMessage() {
        return this.mLunarMessage;
    }

    public int getMinute() {
        return this.mMinute;
    }

    public int getMonth() {
        return this.mMonth;
    }

    public int getSpinnerSelection() {
        return this.mSpinnerSelection;
    }

    public int getYear() {
        return this.mYear;
    }

    public void setDate(int i, int i2, int i3) {
        this.mYear = i;
        this.mMonth = i2;
        this.mDayOfMonth = i3;
    }

    public void setLunarMessage(String str) {
        this.mLunarMessage = str;
    }

    public void setSpinnerSelection(int i) {
        this.mSpinnerSelection = i;
    }

    public void setTime(int i, int i2) {
        this.mHourOfDay = i;
        this.mMinute = i2;
    }
}
