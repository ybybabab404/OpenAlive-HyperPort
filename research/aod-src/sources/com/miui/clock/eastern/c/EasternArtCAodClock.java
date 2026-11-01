package com.miui.clock.eastern.c;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.eastern.c.clokcinfo.EasternArtCBaseInfo;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.DeviceConfig;
import java.util.Locale;
import java.util.Map;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class EasternArtCAodClock extends EasternArtCBase {
    public TextView mDateTextStyle;
    public TextView mTimeHour1;
    public TextView mTimeHour2;
    public TextView mTimeMinute1;
    public TextView mTimeMinute2;
    public TextView mWeekTextStyle;

    /* JADX INFO: renamed from: com.miui.clock.eastern.c.EasternArtCAodClock$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$ClockViewType;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            $SwitchMap$com$miui$clock$module$ClockViewType = iArr;
            try {
                iArr[ClockViewType.HOUR1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.HOUR2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.MIN1.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.MIN2.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_WEEK.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_DATE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public EasternArtCAodClock(Context context) {
        super(context);
    }

    public EasternArtCAodClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private int getDateTextSize() {
        return getDimen(R.dimen.miui_eastern_art_time_week_time_text_size);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientParams() {
        return super.getGradientParams();
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        switch (AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()]) {
            case 1:
                return this.mTimeHour1;
            case 2:
                return this.mTimeHour2;
            case 3:
                return this.mTimeMinute1;
            case 4:
                return this.mTimeMinute2;
            case 5:
                return this.mWeekTextStyle;
            case 6:
                return this.mDateTextStyle;
            default:
                return super.getIClockView(clockViewType);
        }
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getMagazineColor() {
        return super.getMagazineColor();
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockBottom() {
        return this.mMagazineInfoVisible ? getDimen(R.dimen.miui_eastern_art_single_magazine_notification_margin_top) : getDimen(R.dimen.miui_eastern_art_single_notification_margin_top);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase
    public int getTimeTextSize(int i) {
        if (i != 2) {
            return getDimen(this.mIsOnHour ? R.dimen.miui_eastern_art_aod_time_big_text_size : R.dimen.miui_eastern_art_aod_time_small_text_size);
        }
        return getDimen(this.mIsOnHour ? R.dimen.miui_eastern_art_aod_cursive_time_big_text_size : R.dimen.miui_eastern_art_aod_cursive_time_small_text_size);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public float getTopMargin() {
        int dimen;
        EasternArtCBaseInfo easternArtCBaseInfo = this.mClockInfo;
        if (easternArtCBaseInfo == null) {
            dimen = getDimen(R.dimen.miui_eastern_art_aod_time_hour1_margin_top);
        } else {
            dimen = getDimen(easternArtCBaseInfo.getClockStyle() != 2 ? R.dimen.miui_eastern_art_aod_time_hour1_margin_top : R.dimen.miui_eastern_art_aod_cursive_time_hour1_margin_top);
        }
        return dimen;
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mTimeHour1 = (TextView) findViewById(R.id.time_hour_1);
        this.mTimeHour2 = (TextView) findViewById(R.id.time_hour_2);
        this.mWeekTextStyle = (TextView) findViewById(R.id.current_week);
        this.mDateTextStyle = (TextView) findViewById(R.id.date_and_time);
        this.mTimeMinute1 = (TextView) findViewById(R.id.time_minute_1);
        this.mTimeMinute2 = (TextView) findViewById(R.id.time_minute_2);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        super.setClockPalette(i, z, map, z2);
        if (DeviceConfig.supportBackgroundBlur(this.mContext)) {
            updateClockBlendColor(this.mClockInfo.getBlendColor(), this.mClockInfo.getSecondaryBlendColor());
        } else {
            clearPassBlur();
            updateClockColor(this.mClockInfo.getBlendColor(), this.mClockInfo.getSecondaryBlendColor());
        }
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        setTextTypeFace(this.mTimeHour1, this.mClockInfo.getClockStyle());
        setTextTypeFace(this.mTimeHour2, this.mClockInfo.getClockStyle());
        setTextTypeFace(this.mTimeMinute1, this.mClockInfo.getClockStyle());
        setTextTypeFace(this.mTimeMinute2, this.mClockInfo.getClockStyle());
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    public final void setLayoutParams(TextView textView, int i, int i2) {
        textView.setTextSize(0, i);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) textView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(i2);
        textView.setLayoutParams(layoutParams);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        this.mTimeHour1.setTextColor(this.mClockInfo.getAodPrimaryColor());
        this.mTimeHour2.setTextColor(this.mClockInfo.getAodPrimaryColor());
        this.mTimeMinute1.setTextColor(this.mClockInfo.getAodPrimaryColor());
        this.mTimeMinute2.setTextColor(this.mClockInfo.getAodPrimaryColor());
        this.mWeekTextStyle.setTextColor(this.mClockInfo.getAodSecondaryColor());
        this.mDateTextStyle.setTextColor(this.mClockInfo.getAodSecondaryColor());
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        String str;
        String string;
        super.updateTime();
        this.mTimeHour1.setText(ClassicClockTimeUtils.getChsTimeHour(this.m24HourFormat, this.mIsOnHour, true, this.mCalendar));
        this.mTimeHour2.setText(ClassicClockTimeUtils.getChsTimeHour(this.m24HourFormat, this.mIsOnHour, false, this.mCalendar));
        String str2 = this.mCalendar.format(getContext(), getResources().getString(this.m24HourFormat ? R.string.miui_aesthetics_a_notification_format_24 : R.string.miui_aesthetics_a_notification_format_12));
        this.mTimeHour1.setContentDescription(str2);
        this.mTimeHour2.setContentDescription(str2);
        String chsTimeMinute = ClassicClockTimeUtils.getChsTimeMinute(this.m24HourFormat, true, this.mCalendar);
        String chsTimeMinute2 = ClassicClockTimeUtils.getChsTimeMinute(this.m24HourFormat, false, this.mCalendar);
        this.mTimeMinute1.setText(chsTimeMinute);
        this.mTimeMinute2.setText(chsTimeMinute2);
        this.mTimeMinute1.setContentDescription(str2);
        this.mTimeMinute2.setContentDescription(str2);
        String country = Locale.getDefault().getCountry();
        if (this.mLanguage.equals("zh") && (country.equals("CN") || country.equals("HK") || country.equals("TW"))) {
            Calendar calendar = this.mCalendar;
            Context context = this.mContext;
            str = calendar.format(context, context.getString(R.string.miui_lock_eastern_art_week_am));
            string = this.mContext.getString(this.m24HourFormat ? R.string.miui_lock_eastern_art_date_time_24 : R.string.miui_lock_eastern_art_date_time_12);
        } else {
            Calendar calendar2 = this.mCalendar;
            Context context2 = this.mContext;
            str = calendar2.format(context2, context2.getString(R.string.miui_rhombus_clock_week_full));
            string = this.mContext.getString(this.m24HourFormat ? R.string.miui_lock_eastern_art_date_curisive_24 : R.string.miui_lock_eastern_art_date_curisive_12);
        }
        String str3 = this.mCalendar.format(this.mContext, string);
        this.mWeekTextStyle.setText(str);
        this.mDateTextStyle.setText(str3);
        updateViewsLayoutParams();
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        super.updateViewsLayoutParams();
        int timeTextSize = getTimeTextSize(this.mClockInfo.getClockStyle());
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mWeekTextStyle.getLayoutParams();
        if (this.mClockInfo.getClockStyle() != 2) {
            i4 = R.dimen.miui_eastern_art_aod_time_week_time_margin_top;
            i5 = R.dimen.miui_eastern_art_aod_time_hour1_margin_top;
            boolean z = this.mIsOnHour;
            i = z ? R.dimen.miui_eastern_art_aod_time_hour2_big_margin_top : R.dimen.miui_eastern_art_aod_time_hour2_small_margin_top;
            i3 = z ? R.dimen.miui_eastern_art_aod_time_minute1_big_margin_top : R.dimen.miui_eastern_art_aod_time_minute1_small_margin_top;
            i2 = R.dimen.miui_eastern_art_aod_time_minute2_margin_top;
        } else {
            boolean z2 = this.mIsOnHour;
            int i6 = z2 ? R.dimen.miui_eastern_art_aod_cursive_time_week_big_margin_top : R.dimen.miui_eastern_art_aod_cursive_time_week_small_margin_top;
            int i7 = R.dimen.miui_eastern_art_aod_cursive_time_hour1_margin_top;
            i = z2 ? R.dimen.miui_eastern_art_aod_cursive_time_hour2_big_margin_top : R.dimen.miui_eastern_art_aod_cursive_time_hour2_small_margin_top;
            int i8 = z2 ? R.dimen.miui_eastern_art_aod_cursive_time_minute1_big_margin_top : R.dimen.miui_eastern_art_aod_cursive_time_minute1_small_margin_top;
            i2 = R.dimen.miui_eastern_art_aod_cursive_time_minute2_margin_top;
            i3 = i8;
            i4 = i6;
            i5 = i7;
        }
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(i4);
        this.mWeekTextStyle.setLayoutParams(layoutParams);
        this.mTimeHour1.setVisibility(this.mIsOnHour ? 8 : 0);
        this.mTimeMinute2.setVisibility(this.mIsOnHour ? 8 : 0);
        float f = timeTextSize;
        this.mTimeHour1.setTextSize(0, f);
        this.mTimeHour2.setTextSize(0, f);
        this.mTimeMinute1.setTextSize(0, f);
        this.mTimeMinute2.setTextSize(0, f);
        setLayoutParams(this.mTimeHour1, timeTextSize, i5);
        setLayoutParams(this.mTimeHour2, timeTextSize, i);
        setLayoutParams(this.mTimeMinute1, timeTextSize, i3);
        setLayoutParams(this.mTimeMinute2, timeTextSize, i2);
        float dateTextSize = getDateTextSize();
        this.mWeekTextStyle.setTextSize(0, dateTextSize);
        this.mDateTextStyle.setTextSize(0, dateTextSize);
        this.mDateTextStyle.setLetterSpacing(0.4f);
    }
}
