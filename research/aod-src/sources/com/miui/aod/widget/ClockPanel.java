package com.miui.aod.widget;

import android.content.Context;
import android.view.View;
import android.view.ViewStub;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.ClockPanelCategoryInfo;
import com.miui.aod.common.StyleInfo;
import java.util.List;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class ClockPanel implements IAodClock {
    public DualClockPanelContainerView mAnimClockPanelContainerViewDual;
    public Calendar mCal;
    public Calendar mCalDual;
    public ClockPanelContainerView mClockPanelContainerView;
    public ClockPanelContainerView mClockPanelContainerViewDual;
    public Context mContext;
    public final boolean mIsDual;
    public int mLayoutId;
    public final int mSize;
    public StyleInfo mStyleInfo;
    public TimeZone mTimeZone;
    public TimeZone mTimeZoneDual;

    public ClockPanel(int i, boolean z) {
        this.mSize = i;
        this.mIsDual = z;
    }

    public static void setClockTime(Context context, Calendar calendar, boolean z, ClockPanelContainerView clockPanelContainerView, StyleInfo styleInfo) {
        if (clockPanelContainerView == null) {
            return;
        }
        int i = calendar.get(18);
        if (!z && i > 12) {
            i -= 12;
        }
        int i2 = (z || i != 0) ? i : 12;
        ClockPanelView clockPanelView = clockPanelContainerView.mClockPanel;
        if (clockPanelView != null) {
            clockPanelView.setTime(i2, calendar.get(20));
        }
        if (clockPanelContainerView.mDateView != null) {
            clockPanelContainerView.mDateView.setText(calendar.format(context, AODSettings.getDateFormat(context, z, false)));
        }
        if (clockPanelContainerView.mDateLunar == null || !styleInfo.isLunarSwitchOn(context)) {
            return;
        }
        clockPanelContainerView.mDateLunar.setText(calendar.format(context, AODSettings.getDateFormat(context, z, true)));
    }

    @Override // com.miui.aod.widget.IAodClock
    public void bindView(View view) {
        this.mContext = view.getContext();
        int i = this.mLayoutId;
        if (i == R.layout.clock_panel || i == R.layout.clock_panel_small) {
            ClockPanelContainerView clockPanelContainerView = (ClockPanelContainerView) view.findViewById(R.id.clock_panel_container);
            this.mClockPanelContainerView = clockPanelContainerView;
            clockPanelContainerView.setIsDual(false);
        } else {
            if (i == R.layout.clock_panel_dual) {
                this.mAnimClockPanelContainerViewDual = (DualClockPanelContainerView) view.findViewById(R.id.clock_panel_container);
                return;
            }
            ((ViewStub) view.findViewById(R.id.clock_panel_container_vs)).inflate();
            ClockPanelContainerView clockPanelContainerView2 = (ClockPanelContainerView) view.findViewById(R.id.clock_panel_container);
            this.mClockPanelContainerView = clockPanelContainerView2;
            clockPanelContainerView2.setIsDual(this.mIsDual);
            ((ViewStub) view.findViewById(R.id.clock_panel_container_dual_vs)).inflate();
            ClockPanelContainerView clockPanelContainerView3 = (ClockPanelContainerView) view.findViewById(R.id.clock_panel_container_dual);
            this.mClockPanelContainerViewDual = clockPanelContainerView3;
            clockPanelContainerView3.setIsDual(true);
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public int getLayoutResource(StyleInfo styleInfo) {
        if (this.mSize == 2) {
            this.mLayoutId = R.layout.clock_panel_small;
        } else if (!this.mIsDual) {
            this.mLayoutId = R.layout.clock_panel;
        } else if (styleInfo instanceof ClockPanelCategoryInfo) {
            this.mLayoutId = R.layout.clock_panel_dual_together;
        } else {
            this.mLayoutId = R.layout.clock_panel_dual;
        }
        return this.mLayoutId;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void onInflateBatteryComplete() {
        View viewFindViewById;
        View viewFindViewById2;
        ClockPanelContainerView clockPanelContainerView = this.mClockPanelContainerViewDual;
        if (clockPanelContainerView == null || (viewFindViewById = clockPanelContainerView.getRootView().findViewById(R.id.aod_content_container)) == null || (viewFindViewById2 = viewFindViewById.findViewById(R.id.battery_step_container)) == null) {
            return;
        }
        this.mClockPanelContainerViewDual.setViewNeedAnimate(List.of(viewFindViewById2));
    }

    @Override // com.miui.aod.widget.IAodClock
    public void setClockMask(int i, int i2) {
        if (i > 0) {
            ClockPanelContainerView clockPanelContainerView = this.mClockPanelContainerView;
            if (clockPanelContainerView != null) {
                clockPanelContainerView.setClockMask(i, i2);
            }
            ClockPanelContainerView clockPanelContainerView2 = this.mClockPanelContainerViewDual;
            if (clockPanelContainerView2 != null) {
                clockPanelContainerView2.setClockMask(i, i2);
            }
            DualClockPanelContainerView dualClockPanelContainerView = this.mAnimClockPanelContainerViewDual;
            if (dualClockPanelContainerView != null) {
                dualClockPanelContainerView.setClockMask(i, i2);
            }
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public void setTimeZone(TimeZone timeZone) {
        this.mTimeZone = timeZone;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void setTimeZone2(TimeZone timeZone) {
        this.mTimeZoneDual = timeZone;
    }

    @Override // com.miui.aod.components.view.IPreAodView
    public void update(StyleInfo styleInfo, int i) {
        this.mStyleInfo = styleInfo;
        ClockPanelContainerView clockPanelContainerView = this.mClockPanelContainerView;
        if (clockPanelContainerView != null) {
            clockPanelContainerView.setTimeZone(this.mTimeZone);
            this.mClockPanelContainerView.update(styleInfo, i);
        }
        DualClockPanelContainerView dualClockPanelContainerView = this.mAnimClockPanelContainerViewDual;
        if (dualClockPanelContainerView != null) {
            dualClockPanelContainerView.setTimeZone(this.mTimeZone);
            this.mAnimClockPanelContainerViewDual.setTimeZone2(this.mTimeZoneDual);
            this.mAnimClockPanelContainerViewDual.update(styleInfo, i);
        }
        ClockPanelContainerView clockPanelContainerView2 = this.mClockPanelContainerViewDual;
        if (clockPanelContainerView2 != null) {
            clockPanelContainerView2.setTimeZone(this.mTimeZoneDual);
            this.mClockPanelContainerViewDual.update(styleInfo, i);
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public void updateTime(boolean z) {
        TimeZone timeZone = this.mTimeZone;
        if (timeZone == null) {
            timeZone = TimeZone.getDefault();
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        Calendar calendarRefreshCalendar = Utils.refreshCalendar(this.mCal, timeZone, jCurrentTimeMillis);
        this.mCal = calendarRefreshCalendar;
        int i = this.mLayoutId;
        if (i == R.layout.clock_panel_dual_together) {
            setClockTime(this.mContext, calendarRefreshCalendar, z, this.mClockPanelContainerView, this.mStyleInfo);
            TimeZone timeZone2 = this.mTimeZoneDual;
            if (timeZone2 == null) {
                timeZone2 = TimeZone.getDefault();
            }
            Calendar calendarRefreshCalendar2 = Utils.refreshCalendar(this.mCalDual, timeZone2, jCurrentTimeMillis);
            this.mCalDual = calendarRefreshCalendar2;
            setClockTime(this.mContext, calendarRefreshCalendar2, z, this.mClockPanelContainerViewDual, this.mStyleInfo);
            return;
        }
        if (i == R.layout.clock_panel || i == R.layout.clock_panel_small) {
            setClockTime(this.mContext, calendarRefreshCalendar, z, this.mClockPanelContainerView, this.mStyleInfo);
        } else if (i == R.layout.clock_panel_dual) {
            this.mCalDual = Utils.refreshCalendar(this.mCalDual, this.mTimeZoneDual, jCurrentTimeMillis);
            this.mAnimClockPanelContainerViewDual.setTimeZone(this.mTimeZone);
            this.mAnimClockPanelContainerViewDual.setTimeZone2(this.mTimeZoneDual);
            this.mAnimClockPanelContainerViewDual.setClockTime(this.mContext, this.mCal, this.mCalDual, z, this.mStyleInfo);
        }
    }
}
