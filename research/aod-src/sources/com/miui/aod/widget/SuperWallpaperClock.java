package com.miui.aod.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import androidx.core.util.Consumer;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.SuperWallpaperCategoryInfo;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.icu.GetZoneNameTask;
import com.miui.aod.icu.ICUManager;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class SuperWallpaperClock implements IAodClock {
    public GetZoneNameTask.OnGetZoneNameCompleteListener completeCallback;
    public Calendar mCal;
    public Calendar mCalDual;
    public View mClock2Container;
    public TextView mClockCity;
    public TextView mClockCity2;
    public TextView mClockDate;
    public TextView mClockTime;
    public TextView mClockTime2;
    public SuperWallpaperClockView mClockView;
    public Context mContext;
    public TextView mDateLunar;
    public final int mSize;
    public StyleInfo mStyleInfo;
    public boolean mSupportChangeClockPosition;
    public TimeZone mTimeZone;
    public TimeZone mTimeZone2;

    public SuperWallpaperClock(int i) {
        this.mSize = i;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void bindView(View view) {
        this.mContext = view.getContext();
        SuperWallpaperClockView superWallpaperClockView = (SuperWallpaperClockView) view.findViewById(R.id.clock);
        this.mClockView = superWallpaperClockView;
        superWallpaperClockView.bindView();
        this.mClockView.setSize(this.mSize);
        View viewFindViewById = this.mClockView.findViewById(R.id.single_clock_container);
        this.mClock2Container = this.mClockView.findViewById(R.id.clock2_container);
        this.mClockCity = (TextView) viewFindViewById.findViewById(R.id.city);
        this.mClockCity2 = (TextView) this.mClock2Container.findViewById(R.id.city2);
        this.mClockTime = (TextView) viewFindViewById.findViewById(R.id.time);
        this.mClockTime2 = (TextView) this.mClock2Container.findViewById(R.id.time2);
        Typeface typefaceCreateFromAsset = Typeface.createFromAsset(view.getContext().getAssets(), "fonts/MiClock-Light.otf");
        this.mClockTime.setTypeface(typefaceCreateFromAsset);
        this.mClockTime2.setTypeface(typefaceCreateFromAsset);
        this.mClockDate = (TextView) viewFindViewById.findViewById(R.id.date);
        this.mDateLunar = (TextView) viewFindViewById.findViewById(R.id.date_lunar);
        if (AODSettings.isDualClock(this.mContext)) {
            this.mClock2Container.setVisibility(0);
        } else {
            this.mClockCity.setVisibility(8);
            this.mClock2Container.setVisibility(8);
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public int getLayoutResource(StyleInfo styleInfo) {
        this.mSupportChangeClockPosition = ((SuperWallpaperCategoryInfo) styleInfo).getSupportChangeWithTime() == 1;
        return R.layout.aod_content_super_wallpaper;
    }

    public void setGetZoneNameCompleteListener(GetZoneNameTask.OnGetZoneNameCompleteListener onGetZoneNameCompleteListener) {
        this.completeCallback = onGetZoneNameCompleteListener;
    }

    public final void setTimeZone(TextView textView, String str) {
        if (TextUtils.isEmpty(str)) {
            textView.setVisibility(8);
            return;
        }
        textView.setVisibility(0);
        textView.setText(str);
        GetZoneNameTask.OnGetZoneNameCompleteListener onGetZoneNameCompleteListener = this.completeCallback;
        if (onGetZoneNameCompleteListener != null) {
            onGetZoneNameCompleteListener.onComplete(str);
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public void setTimeZone(TimeZone timeZone) {
        this.mTimeZone = timeZone;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void setTimeZone2(TimeZone timeZone) {
        this.mTimeZone2 = timeZone;
    }

    @Override // com.miui.aod.components.view.IPreAodView
    public void update(StyleInfo styleInfo, int i) {
        this.mStyleInfo = styleInfo;
        if (!AODSettings.isDualClock(this.mContext) || this.mClockCity == null || this.mClockCity2 == null) {
            return;
        }
        ICUManager iCUManager = ICUManager.INSTANCE;
        iCUManager.loadCity(this.mContext, this.mTimeZone, new Consumer() { // from class: com.miui.aod.widget.SuperWallpaperClock$$ExternalSyntheticLambda0
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                SuperWallpaperClock superWallpaperClock = this.f$0;
                superWallpaperClock.setTimeZone(superWallpaperClock.mClockCity, (String) obj);
            }
        });
        iCUManager.loadCity(this.mContext, this.mTimeZone2, new Consumer() { // from class: com.miui.aod.widget.SuperWallpaperClock$$ExternalSyntheticLambda1
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                SuperWallpaperClock superWallpaperClock = this.f$0;
                superWallpaperClock.setTimeZone(superWallpaperClock.mClockCity2, (String) obj);
            }
        });
    }

    @Override // com.miui.aod.widget.IAodClock
    public void updateTime(boolean z) {
        TimeZone timeZone = TimeZone.getDefault();
        long jCurrentTimeMillis = System.currentTimeMillis();
        Calendar calendarRefreshCalendar = Utils.refreshCalendar(this.mCal, timeZone, jCurrentTimeMillis);
        this.mCal = calendarRefreshCalendar;
        int i = calendarRefreshCalendar.get(18);
        if (!z && i > 12) {
            i -= 12;
        }
        if (!z && i == 0) {
            i = 12;
        }
        this.mClockTime.setText(String.format("%d:%02d", Integer.valueOf(i), Integer.valueOf(this.mCal.get(20))));
        String country = this.mContext.getResources().getConfiguration().getLocales().get(0).getCountry();
        String string = this.mContext.getString(R.string.super_wallpaper_date);
        if (!"CN".equals(country) && !"TW".equals(country)) {
            string = this.mContext.getString(R.string.super_wallpaper_date_inter);
        }
        this.mClockDate.setText(this.mCal.format(this.mContext, string));
        if (this.mTimeZone2 != null && this.mClock2Container.getVisibility() == 0) {
            Calendar calendarRefreshCalendar2 = Utils.refreshCalendar(this.mCalDual, this.mTimeZone2, jCurrentTimeMillis);
            this.mCalDual = calendarRefreshCalendar2;
            int i2 = calendarRefreshCalendar2.get(18);
            if (!z && i2 > 12) {
                i2 -= 12;
            }
            if (!z && i2 == 0) {
                i2 = 12;
            }
            this.mClockTime2.setText(String.format("%d:%02d", Integer.valueOf(i2), Integer.valueOf(this.mCalDual.get(20))));
        }
        if (this.mSupportChangeClockPosition) {
            this.mClockView.updateClockPositionByTime(java.util.Calendar.getInstance().get(11) >= 12);
        }
        if (this.mDateLunar == null || !this.mStyleInfo.isLunarSwitchOn(this.mContext)) {
            return;
        }
        TextView textView = this.mDateLunar;
        Calendar calendar = this.mCal;
        Context context = this.mContext;
        textView.setText(calendar.format(context, context.getString(R.string.super_wallpaper_date_lunar)));
    }
}
