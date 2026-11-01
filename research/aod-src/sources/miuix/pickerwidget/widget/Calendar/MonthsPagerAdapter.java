package miuix.pickerwidget.widget.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import miuix.pickerwidget.R;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes2.dex */
class MonthsPagerAdapter extends RecyclerView.Adapter<MonthViewHolder> {
    public int mEndYear;
    public final LayoutInflater mInflater;
    public long mMaxDateTimeMills;
    public long mMinDateTimeMills;
    public OnDayClickListener mOnDayClickListener;
    public int mStartYear;
    public int mHighlightColor = 0;
    public boolean mLunarMode = false;
    public Calendar mCalendar = new Calendar();
    public int[] mYearAndMonth = new int[2];
    public int mSelectedYear = this.mCalendar.get(1);
    public int mSelectedMonth = this.mCalendar.get(5);
    public int mSelectedDayOfMonth = this.mCalendar.get(9);
    public boolean mHasSelectedDate = true;

    public class MonthViewHolder extends RecyclerView.ViewHolder {
        public final CalendarGridLayout mGridLayout;

        public static /* synthetic */ void $r8$lambda$knpFiENJx_7QZjNV6X5DvFK1eyU(MonthViewHolder monthViewHolder, View view) {
            if (MonthsPagerAdapter.this.mOnDayClickListener != null) {
                MonthsPagerAdapter.this.mOnDayClickListener.onDayClick(monthViewHolder.mGridLayout, view, MonthsPagerAdapter.this);
            }
        }

        public MonthViewHolder(View view) {
            super(view);
            this.mGridLayout = (CalendarGridLayout) view;
            for (int i = 0; i < this.mGridLayout.getChildCount(); i++) {
                this.mGridLayout.getChildAt(i).setOnClickListener(new View.OnClickListener() { // from class: miuix.pickerwidget.widget.Calendar.MonthsPagerAdapter$MonthViewHolder$$ExternalSyntheticLambda0
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        MonthsPagerAdapter.MonthViewHolder.$r8$lambda$knpFiENJx_7QZjNV6X5DvFK1eyU(this.f$0, view2);
                    }
                });
            }
        }
    }

    public interface OnDayClickListener {
        void onDayClick(CalendarGridLayout calendarGridLayout, View view, MonthsPagerAdapter monthsPagerAdapter);
    }

    public MonthsPagerAdapter(Context context, long j, long j2, OnDayClickListener onDayClickListener) {
        this.mInflater = LayoutInflater.from(context);
        this.mMinDateTimeMills = j;
        this.mMaxDateTimeMills = j2;
        this.mStartYear = this.mCalendar.setTimeInMillis(j).get(1);
        this.mEndYear = this.mCalendar.setTimeInMillis(j2).get(1);
        this.mOnDayClickListener = onDayClickListener;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public int getItemCount() {
        return ((this.mEndYear - this.mStartYear) + 1) * 12;
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public long getItemId(int i) {
        return i;
    }

    public int getPosition(int i, int i2) {
        return ((i - this.mStartYear) * 12) + i2;
    }

    public int[] getYearAndMonth(int i) {
        int[] iArr = this.mYearAndMonth;
        iArr[0] = this.mStartYear + (i / 12);
        iArr[1] = i % 12;
        return iArr;
    }

    public boolean isLunarMode() {
        return this.mLunarMode;
    }

    public void notifyHighlightColorChanged(int i) {
        if (this.mHighlightColor != i) {
            this.mHighlightColor = i;
            notifyDataSetChanged();
        }
    }

    public void notifyLunarModeChanged(boolean z) {
        if (this.mLunarMode != z) {
            this.mLunarMode = z;
            notifyDataSetChanged();
        }
    }

    public void notifyMaxDateChanged(long j) {
        if (this.mMaxDateTimeMills != j) {
            this.mMaxDateTimeMills = j;
            notifyDataSetChanged();
        }
    }

    public void notifyMinDateChanged(long j) {
        if (this.mMinDateTimeMills != j) {
            this.mMinDateTimeMills = j;
            notifyDataSetChanged();
        }
    }

    public void notifySelectedDateChanged(int i, int i2, int i3) {
        if (this.mHasSelectedDate && this.mSelectedYear == i && this.mSelectedMonth == i2 && this.mSelectedDayOfMonth == i3) {
            return;
        }
        this.mSelectedYear = i;
        this.mSelectedMonth = i2;
        this.mSelectedDayOfMonth = i3;
        this.mHasSelectedDate = true;
        notifyDataSetChanged();
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public void onBindViewHolder(MonthViewHolder monthViewHolder, int i) {
        int[] yearAndMonth = getYearAndMonth(i);
        monthViewHolder.mGridLayout.bind(yearAndMonth[0], yearAndMonth[1], (this.mHasSelectedDate && yearAndMonth[0] == this.mSelectedYear && yearAndMonth[1] == this.mSelectedMonth) ? this.mSelectedDayOfMonth : 0, this.mLunarMode, this.mHighlightColor, this.mMinDateTimeMills, this.mMaxDateTimeMills);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.Adapter
    public MonthViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new MonthViewHolder(this.mInflater.inflate(R.layout.miuix_appcompat_calendar_date_picker_grid_layout, viewGroup, false));
    }
}
