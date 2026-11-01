package com.miui.clock.smartframe;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.miui.clock.MiuiGalleryBaseClock;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.utils.ColorUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import java.util.Map;
import miuix.pickerwidget.date.Calendar;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public abstract class MiuiSmartFrameBase extends MiuiGalleryBaseClock {
    public View mClockContainer;
    public MiuiSmartFrameClockBaseInfo mClockInfo;
    public int[] mCurrentTimeArray;
    public TextView mDateText;
    public Paint mPaint;
    public TextView mTimeHour1Text;
    public TextView mTimeHour2Text;
    public TextView mTimeMinute1Text;
    public TextView mTimeMinute2Text;
    public TextView mWeekText;

    /* JADX INFO: renamed from: com.miui.clock.smartframe.MiuiSmartFrameBase$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$ClockViewType;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            $SwitchMap$com$miui$clock$module$ClockViewType = iArr;
            try {
                iArr[ClockViewType.FULL_DATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_WEEK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.HOUR1.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.HOUR2.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.MIN1.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.MIN2.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.CLOCK_CONTAINER.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    public MiuiSmartFrameBase(Context context) {
        super(context);
        this.mPaint = new Paint();
        this.mCurrentTimeArray = new int[4];
    }

    public MiuiSmartFrameBase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mPaint = new Paint();
        this.mCurrentTimeArray = new int[4];
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public ClockStyleInfo getClockStyleInfo() {
        return this.mClockInfo;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientParams() {
        return super.getGradientParams();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        switch (AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()]) {
            case 1:
                return this.mDateText;
            case 2:
                return this.mWeekText;
            case 3:
                return this.mTimeHour1Text;
            case 4:
                return this.mTimeHour2Text;
            case 5:
                return this.mTimeMinute1Text;
            case 6:
                return this.mTimeMinute2Text;
            case 7:
                return this.mClockContainer;
            default:
                return super.getIClockView(clockViewType);
        }
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getMagazineColor() {
        MiuiSmartFrameClockBaseInfo miuiSmartFrameClockBaseInfo = this.mClockInfo;
        if (miuiSmartFrameClockBaseInfo != null) {
            return ColorUtils.blendColor(miuiSmartFrameClockBaseInfo.getPrimaryColor(), 0.6f);
        }
        return 0;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockBottom() {
        return this.mMagazineInfoVisible ? getDimen(R.dimen.notification_bottom_smart_frame_with_gallery) : getDimen(R.dimen.notification_bottom_smart_frame);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockTop() {
        return getDimen(R.dimen.miui_smart_frame_clock_margin_top);
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public View getNotificationClockTopView() {
        return this.mClockContainer;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationRelativePosition() {
        return (DeviceConfig.PAD_DEVICE && getResources().getConfiguration().orientation == 2) ? 2 : 1;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    public boolean measureWeekSize(String str) {
        Rect rect = new Rect();
        this.mPaint.setTextSize(this.mWeekText.getTextSize());
        this.mPaint.getTextBounds(str, 0, str.length(), rect);
        return ((float) rect.width()) > ((float) getDimen(R.dimen.miui_smart_frame_clock_week_max_width));
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mDateText = (TextView) findViewById(R.id.current_date);
        this.mWeekText = (TextView) findViewById(R.id.current_week);
        this.mTimeHour1Text = (TextView) findViewById(R.id.current_time_hour1);
        this.mTimeHour2Text = (TextView) findViewById(R.id.current_time_hour2);
        this.mTimeMinute1Text = (TextView) findViewById(R.id.current_time_minute1);
        this.mTimeMinute2Text = (TextView) findViewById(R.id.current_time_minute2);
        this.mClockContainer = findViewById(R.id.clock_container);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        String str;
        int primaryColor;
        int secondaryColor;
        if (map != null) {
            str = "secondary20=" + map.get("secondary20") + ",secondary97=" + map.get("secondary97") + ",secondary15=" + map.get("secondary15") + ",secondary60=" + map.get("secondary60") + ",tertiary90=" + map.get("tertiary90") + ",secondary20=" + map.get("secondary20");
        } else {
            str = "null";
        }
        Log.d("ClockPalette", "setClockPalette: type = " + i + ", textDark = " + z + ", palette = " + str);
        MiuiSmartFrameClockBaseInfo miuiSmartFrameClockBaseInfo = this.mClockInfo;
        if (miuiSmartFrameClockBaseInfo == null || !miuiSmartFrameClockBaseInfo.isAutoPrimaryColor()) {
            return;
        }
        if (i == 1) {
            primaryColor = (map == null || map.get("secondary20") == null) ? this.mClockInfo.getPrimaryColor() : map.get("secondary20").intValue();
            secondaryColor = (map == null || map.get("secondary97") == null) ? this.mClockInfo.getSecondaryColor() : map.get("secondary97").intValue();
        } else if (i == 2) {
            primaryColor = (map == null || map.get("secondary15") == null) ? this.mClockInfo.getPrimaryColor() : map.get("secondary15").intValue();
            secondaryColor = (map == null || map.get("secondary60") == null) ? this.mClockInfo.getSecondaryColor() : map.get("secondary60").intValue();
        } else {
            primaryColor = (map == null || map.get("tertiary90") == null) ? this.mClockInfo.getPrimaryColor() : map.get("tertiary90").intValue();
            secondaryColor = (map == null || map.get("secondary20") == null) ? this.mClockInfo.getSecondaryColor() : map.get("secondary20").intValue();
        }
        this.mClockInfo.setPrimaryColor(primaryColor);
        this.mClockInfo.setSecondaryColor(secondaryColor);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        this.mClockInfo = (MiuiSmartFrameClockBaseInfo) clockStyleInfo;
        updateTime();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        if (this.mClockInfo == null) {
            return;
        }
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        String time = DateFormatUtils.formatTime(this.m24HourFormat ? "HHmm" : "hhmm");
        for (int i = 0; i < time.length(); i++) {
            this.mCurrentTimeArray[i] = Integer.parseInt(String.valueOf(time.charAt(i)));
        }
        this.mClockContainer.setContentDescription(DateUtils.formatDateTime(this.mContext, System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
        this.mTimeHour1Text.setText(String.valueOf(this.mCurrentTimeArray[0]));
        this.mTimeHour2Text.setText(String.valueOf(this.mCurrentTimeArray[1]));
        this.mTimeMinute1Text.setText(String.valueOf(this.mCurrentTimeArray[2]));
        this.mTimeMinute2Text.setText(String.valueOf(this.mCurrentTimeArray[3]));
        Calendar calendar = this.mCalendar;
        Context context = this.mContext;
        String str = calendar.format(context, context.getString(R.string.miui_smart_frame_clock_date));
        Calendar calendar2 = this.mCalendar;
        Context context2 = this.mContext;
        String str2 = calendar2.format(context2, context2.getString(R.string.miui_smart_frame_clock_week));
        this.mDateText.setText(str);
        this.mWeekText.setText(str2);
        TextView textView = this.mDateText;
        Calendar calendar3 = this.mCalendar;
        Context context3 = this.mContext;
        textView.setContentDescription(calendar3.format(context3, context3.getString(R.string.miui_magazine_clock_date_content_description)));
    }
}
