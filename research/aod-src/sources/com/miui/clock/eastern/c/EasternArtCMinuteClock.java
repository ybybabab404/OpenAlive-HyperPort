package com.miui.clock.eastern.c;

import android.content.Context;
import android.content.res.Resources;
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
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.MutiDeviceDimenAdapter;
import java.util.Locale;
import java.util.Map;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class EasternArtCMinuteClock extends EasternArtCBase {
    public ViewGroup mClockContainer;
    public TextView mDateTextStyle;
    public TextView mTimeMinute1;
    public TextView mTimeMinute2;
    public TextView mWeekTextStyle;

    /* JADX INFO: renamed from: com.miui.clock.eastern.c.EasternArtCMinuteClock$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$ClockViewType;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            $SwitchMap$com$miui$clock$module$ClockViewType = iArr;
            try {
                iArr[ClockViewType.MIN1.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.MIN2.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_WEEK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_DATE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.CLOCK_CONTAINER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public EasternArtCMinuteClock(Context context) {
        super(context);
    }

    public EasternArtCMinuteClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private int getDateTextSize() {
        return getDimen(R.dimen.miui_eastern_art_time_week_time_text_size);
    }

    private void getUnUseDimens() {
        Resources resources = getResources();
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_week_time_margin_top_fold);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_week_time_margin_top_fold_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_week_time_margin_top_pad);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_week_time_margin_top_pad_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_big_week_time_margin_top_fold);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_big_week_time_margin_top_fold_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_big_week_time_margin_top_pad);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_big_week_time_margin_top_pad_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_minute1_small_margin_top_fold);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_minute1_small_margin_top_fold_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_minute1_small_margin_top_pad);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_minute1_small_margin_top_pad_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_minute1_big_margin_top_fold);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_minute1_big_margin_top_fold_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_minute1_big_margin_top_pad);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_minute1_big_margin_top_pad_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_minute2_margin_top_fold);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_minute2_margin_top_fold_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_minute2_margin_top_pad);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_time_minute2_margin_top_pad_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_big_week_time_margin_top_fold);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_big_week_time_margin_top_fold_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_big_week_time_margin_top_pad);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_big_week_time_margin_top_pad_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_minute1_big_margin_top_fold);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_minute1_big_margin_top_fold_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_minute1_big_margin_top_pad);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_minute1_big_margin_top_pad_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_minute1_small_margin_top_fold);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_minute1_small_margin_top_fold_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_minute1_small_margin_top_pad);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_minute1_small_margin_top_pad_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_minute2_small_margin_top_fold);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_minute2_small_margin_top_fold_land);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_minute2_small_margin_top_pad);
        resources.getDimensionPixelSize(R.dimen.miui_eastern_art_cursive_time_minute2_small_margin_top_pad_land);
    }

    private void updateClockStyle(boolean z, int i, int i2, int i3) {
        int timeTextSize = getTimeTextSize(this.mClockInfo.getClockStyle());
        ViewGroup viewGroup = this.mClockContainer;
        if (viewGroup != null) {
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) viewGroup.getLayoutParams();
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), i, false);
            this.mClockContainer.setLayoutParams(layoutParams);
        }
        float f = timeTextSize;
        this.mTimeMinute1.setTextSize(0, f);
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mTimeMinute1.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), i2, false);
        this.mTimeMinute1.setLayoutParams(layoutParams2);
        if (z) {
            this.mTimeMinute2.setVisibility(8);
            return;
        }
        this.mTimeMinute2.setVisibility(0);
        this.mTimeMinute2.setTextSize(0, f);
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mTimeMinute2.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams3).topMargin = MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), i3, false);
        this.mTimeMinute2.setLayoutParams(layoutParams3);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        super.clearColorEffect(z);
        EasternArtCBaseInfo easternArtCBaseInfo = this.mClockInfo;
        if (easternArtCBaseInfo == null) {
            return;
        }
        ClockEffectUtils.clearClockEffectsContainer(this, easternArtCBaseInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mTimeMinute1, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mTimeMinute2, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mWeekTextStyle, this.mClockInfo, z);
        ClockEffectUtils.clearClockEffectsView(this.mDateTextStyle, this.mClockInfo, z);
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
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()];
        if (i == 1) {
            return this.mTimeMinute1;
        }
        if (i == 2) {
            return this.mTimeMinute2;
        }
        if (i == 3) {
            return this.mWeekTextStyle;
        }
        if (i != 4) {
            return i != 5 ? super.getIClockView(clockViewType) : this.mClockContainer;
        }
        return this.mDateTextStyle;
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
        return 0;
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mWeekTextStyle = (TextView) findViewById(R.id.current_week);
        this.mDateTextStyle = (TextView) findViewById(R.id.date_and_time);
        this.mTimeMinute1 = (TextView) findViewById(R.id.time_minute_1);
        this.mTimeMinute2 = (TextView) findViewById(R.id.time_minute_2);
        this.mClockContainer = (ViewGroup) findViewById(R.id.clock_container);
        getUnUseDimens();
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
            updateClockBlendColor();
        } else {
            clearPassBlur();
        }
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        clearColorEffect(false);
        if (this.mFontStyle != this.mClockInfo.getClockStyle()) {
            setTextTypeFace(this.mTimeMinute1, this.mClockInfo.getClockStyle());
            setTextTypeFace(this.mTimeMinute2, this.mClockInfo.getClockStyle());
        }
        updateTime();
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

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateClockBlendColor(int i, int i2, int i3, int i4) {
        if (!DeviceConfig.supportBackgroundBlur(this.mContext)) {
            updateClockColor(i, i2);
            return;
        }
        boolean zIsAODType = ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType());
        boolean zIsFullAODType = ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType());
        boolean zUpdateTextDark = updateTextDark(this.mTextDark);
        int textDarkAlpha = getTextDarkAlpha(this.mTextDark);
        updateViewBackgroundBlurContainer(this, getDimen(R.dimen.miui_eastern_art_c_clock_blur_radius), this.mTimeMinute1, this.mTimeMinute2, this.mWeekTextStyle, this.mDateTextStyle);
        TextView textView = this.mTimeMinute1;
        EasternArtCBaseInfo easternArtCBaseInfo = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView, easternArtCBaseInfo, zUpdateTextDark, i, easternArtCBaseInfo.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        TextView textView2 = this.mTimeMinute2;
        EasternArtCBaseInfo easternArtCBaseInfo2 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView2, easternArtCBaseInfo2, zUpdateTextDark, i, easternArtCBaseInfo2.getPrimaryColor(), i3, zIsAODType, zIsFullAODType, textDarkAlpha);
        TextView textView3 = this.mWeekTextStyle;
        EasternArtCBaseInfo easternArtCBaseInfo3 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView3, easternArtCBaseInfo3, zUpdateTextDark, i2, easternArtCBaseInfo3.getSecondaryColor(), i4, zIsAODType, zIsFullAODType, textDarkAlpha);
        TextView textView4 = this.mDateTextStyle;
        EasternArtCBaseInfo easternArtCBaseInfo4 = this.mClockInfo;
        ClockEffectUtils.setClockEffectsView(textView4, easternArtCBaseInfo4, zUpdateTextDark, i2, easternArtCBaseInfo4.getSecondaryColor(), i4, zIsAODType, zIsFullAODType, textDarkAlpha);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockColor(int i, int i2) {
        updatePrimaryColor(i);
        updateSecondaryColor(i2);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        updateClockColor(this.mClockInfo.getPrimaryColor(), this.mClockInfo.getSecondaryColor());
    }

    public void updatePrimaryColor(int i) {
        this.mTimeMinute1.setTextColor(i);
        this.mTimeMinute2.setTextColor(i);
    }

    public void updateSecondaryColor(int i) {
        this.mWeekTextStyle.setTextColor(i);
        this.mDateTextStyle.setTextColor(i);
    }

    @Override // com.miui.clock.eastern.c.EasternArtCBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        String str;
        String string;
        super.updateTime();
        String chsTimeMinute = ClassicClockTimeUtils.getChsTimeMinute(this.m24HourFormat, true, this.mCalendar);
        String chsTimeMinute2 = ClassicClockTimeUtils.getChsTimeMinute(this.m24HourFormat, false, this.mCalendar);
        this.mTimeMinute1.setText(chsTimeMinute);
        this.mTimeMinute2.setText(chsTimeMinute2);
        String str2 = this.mCalendar.format(getContext(), getResources().getString(this.m24HourFormat ? R.string.miui_aesthetics_a_notification_format_24 : R.string.miui_aesthetics_a_notification_format_12));
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
        super.updateViewsLayoutParams();
        float dateTextSize = getDateTextSize();
        this.mWeekTextStyle.setTextSize(0, dateTextSize);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mDateTextStyle.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.miui_eastern_art_time_date_margin_top);
        this.mDateTextStyle.setLayoutParams(layoutParams);
        this.mDateTextStyle.setTextSize(0, dateTextSize);
        this.mDateTextStyle.setLetterSpacing(0.4f);
        if (this.mClockInfo.getClockStyle() != 2) {
            boolean z = this.mIsOnHour;
            updateClockStyle(z, z ? R.dimen.miui_eastern_art_time_big_week_time_margin_top : R.dimen.miui_eastern_art_time_week_time_margin_top, z ? R.dimen.miui_eastern_art_time_minute1_big_margin_top : R.dimen.miui_eastern_art_time_minute1_small_margin_top, R.dimen.miui_eastern_art_time_minute2_margin_top);
        } else {
            boolean z2 = this.mIsOnHour;
            updateClockStyle(z2, R.dimen.miui_eastern_art_cursive_time_big_week_time_margin_top, z2 ? R.dimen.miui_eastern_art_cursive_time_minute1_big_margin_top : R.dimen.miui_eastern_art_cursive_time_minute1_small_margin_top, R.dimen.miui_eastern_art_cursive_time_minute2_small_margin_top);
        }
    }
}
