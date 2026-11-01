package com.miui.clock.aesthetics;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import androidx.exifinterface.media.ExifInterface;
import com.google.gson.Gson;
import com.miui.clock.MiuiGalleryBaseClock;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.WeatherBean;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.ColorUtils;
import com.miui.clock.utils.DataUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.GlobalColorUtils;
import com.miui.clock.utils.WeatherFetcherController;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public abstract class MiuiAestheticsABase extends MiuiGalleryBaseClock {
    public boolean clockIsWide;
    public MiuiAestheticsClockBaseInfo mClockInfo;
    public int mCurrentUserId;
    public Gson mGson;
    public boolean mHasPresetData;
    public WeatherBean mWeatherBean;
    public WeatherFetcherController mWeatherFetcherController;

    public static /* synthetic */ void $r8$lambda$JRzA7EsMdocKxneagMRaxuzZF74(MiuiAestheticsABase miuiAestheticsABase, WeatherBean weatherBean) {
        if (miuiAestheticsABase.isAttachedToWindow()) {
            miuiAestheticsABase.updateWeather(weatherBean);
        }
    }

    public MiuiAestheticsABase(Context context) {
        super(context);
    }

    public MiuiAestheticsABase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public MiuiAestheticsABase(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public final void checkGson() {
        if (this.mGson == null) {
            this.mGson = new Gson();
        }
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public ClockStyleInfo getClockStyleInfo() {
        return this.mClockInfo;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getGalleryGravity() {
        return 1;
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

    public int getHourInt(boolean z, Calendar calendar) {
        if (z) {
            return calendar.get(18);
        }
        int i = calendar.get(18);
        if (i == 0) {
            return 12;
        }
        return i < 13 ? i : i - 12;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getMagazineColor() {
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo = this.mClockInfo;
        if (miuiAestheticsClockBaseInfo != null) {
            return ColorUtils.blendColor(miuiAestheticsClockBaseInfo.getPrimaryColor(), 0.6f);
        }
        return 0;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockBottom() {
        Log.i("MiuiAestheticsAClock", "getMagazinePositionY: mMagazineInfoVisible " + this.mMagazineInfoVisible);
        if (this.mMagazineInfoVisible) {
            return getDimen(R.dimen.notification_bottom_aesthetics_a_with_gallery);
        }
        return (DeviceConfig.PAD_DEVICE && this.mOrientation == 1) ? getDimen(R.dimen.notification_bottom_aesthetics_a_for_pad_portrait) : getDimen(R.dimen.notification_bottom_aesthetics_a);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockTop() {
        return getDimen(R.dimen.miui_aesthetics_a_clock_notification_margin_top);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    public final WeatherBean getWeatherBeanSync(Context context) {
        return DataUtils.getWeatherBean(new WeakReference(context), ExifInterface.GPS_MEASUREMENT_2D);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    public final boolean hasWeatherType() {
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo = this.mClockInfo;
        return miuiAestheticsClockBaseInfo != null && miuiAestheticsClockBaseInfo.mClockStyle == 2;
    }

    public void initView() {
    }

    public final boolean isPreviewType() {
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo = this.mClockInfo;
        return miuiAestheticsClockBaseInfo != null && ClockStyleInfo.isPreviewType(miuiAestheticsClockBaseInfo.getDisplayType());
    }

    @Override // com.miui.clock.MiuiBaseClock2, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo = this.mClockInfo;
        if (miuiAestheticsClockBaseInfo != null) {
            setClockStyle(miuiAestheticsClockBaseInfo.getClockStyle());
            updateTime();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        WeatherFetcherController weatherFetcherController = this.mWeatherFetcherController;
        if (weatherFetcherController != null) {
            weatherFetcherController.reset();
            this.mWeatherFetcherController = null;
        }
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mCalendar = new Calendar();
        initView();
    }

    public final void presetData(String str) {
        WeatherBean weatherBean;
        boolean zIsEmpty = TextUtils.isEmpty(str);
        Log.i("MiuiAestheticsAClock", "presetData this = " + this + ", weatherEmpty = " + zIsEmpty);
        if (zIsEmpty) {
            return;
        }
        checkGson();
        if (zIsEmpty) {
            weatherBean = null;
        } else {
            try {
                weatherBean = (WeatherBean) this.mGson.fromJson(str, WeatherBean.class);
            } catch (Exception unused) {
                Log.i("MiuiAestheticsAClock", "presetData weather convert fail");
                weatherBean = null;
            }
        }
        this.mHasPresetData = true;
        this.mWeatherBean = weatherBean;
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void queryDataAsync() {
        Context specialContext = WeatherFetcherController.Companion.getSpecialContext(getContext(), this.mCurrentUserId);
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo = this.mClockInfo;
        if (miuiAestheticsClockBaseInfo == null || miuiAestheticsClockBaseInfo.mClockStyle != 2) {
            return;
        }
        if (this.mWeatherFetcherController == null) {
            this.mWeatherFetcherController = new WeatherFetcherController();
        }
        this.mWeatherFetcherController.fetchWeatherData(specialContext, this.mHasPresetData, new WeatherFetcherController.Callback() { // from class: com.miui.clock.aesthetics.MiuiAestheticsABase$$ExternalSyntheticLambda0
            @Override // com.miui.clock.utils.WeatherFetcherController.Callback
            public final void updateWeatherInfo(WeatherBean weatherBean) {
                MiuiAestheticsABase.$r8$lambda$JRzA7EsMdocKxneagMRaxuzZF74(this.f$0, weatherBean);
            }
        });
    }

    public final void queryDataSync() {
        long jCurrentTimeMillis = System.currentTimeMillis();
        WeatherBean weatherBeanSync = hasWeatherType() ? getWeatherBeanSync(WeatherFetcherController.Companion.getSpecialContext(this.mContext, this.mCurrentUserId)) : null;
        Log.i("MiuiAestheticsAClock", "getWeatherBeanSync use time = " + (System.currentTimeMillis() - jCurrentTimeMillis));
        this.mWeatherBean = weatherBeanSync;
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

    public final void setBlurMixColor(boolean z, Map map) {
        int color;
        int color2;
        int color3;
        int color4;
        int primaryColor;
        int primaryColor2;
        this.mClockInfo.setPrimaryColor(-1);
        this.mClockInfo.setSecondaryColor(-1);
        if (this.mClockInfo.isAutoPrimaryColor()) {
            if (z) {
                color3 = (map == null || map.get("neutral-variant30") == null) ? this.mClockInfo.getPrimaryColor() : ((Integer) map.get("neutral-variant30")).intValue();
            } else {
                color3 = Color.parseColor("#FFABABAB");
            }
            if (z) {
                color4 = (map == null || map.get("secondary70") == null) ? color3 : ((Integer) map.get("secondary70")).intValue();
            } else {
                color4 = Color.parseColor("#FFBEBEBE");
            }
            this.mClockInfo.setBlendColor(color3);
            MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo = this.mClockInfo;
            if (z) {
                primaryColor = (map == null || map.get("secondary30") == null) ? this.mClockInfo.getPrimaryColor() : ((Integer) map.get("secondary30")).intValue();
            } else {
                primaryColor = -1;
            }
            miuiAestheticsClockBaseInfo.setOriginMagazineColor(primaryColor);
            MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo2 = this.mClockInfo;
            if (z) {
                primaryColor2 = (map == null || map.get("secondary30") == null) ? this.mClockInfo.getPrimaryColor() : ((Integer) map.get("secondary30")).intValue();
            } else {
                primaryColor2 = -1;
            }
            miuiAestheticsClockBaseInfo2.setInfoAreaColor(primaryColor2);
            this.mClockInfo.setAodBlendColor(color4);
            this.mClockInfo.setAodPrimaryColor(color4);
            this.mClockInfo.setFullAodBlendColor(Color.parseColor("#FFABABAB"));
            MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo3 = this.mClockInfo;
            miuiAestheticsClockBaseInfo3.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(miuiAestheticsClockBaseInfo3.getPrimaryColor()));
        } else {
            MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo4 = this.mClockInfo;
            miuiAestheticsClockBaseInfo4.setOriginMagazineColor(miuiAestheticsClockBaseInfo4.getPrimaryColor());
            MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo5 = this.mClockInfo;
            miuiAestheticsClockBaseInfo5.setInfoAreaColor(miuiAestheticsClockBaseInfo5.getPrimaryColor());
            int blendColor = this.mClockInfo.getBlendColor();
            int iTransformAodColor = GlobalColorUtils.transformAodColor(blendColor);
            this.mClockInfo.setAodBlendColor(iTransformAodColor);
            this.mClockInfo.setAodPrimaryColor(iTransformAodColor);
            this.mClockInfo.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(blendColor));
        }
        if (!this.mClockInfo.isAutoSecondaryColor()) {
            int secondaryBlendColor = this.mClockInfo.getSecondaryBlendColor();
            int iTransformAodColor2 = GlobalColorUtils.transformAodColor(secondaryBlendColor);
            this.mClockInfo.setAodSecondaryBlendColor(iTransformAodColor2);
            this.mClockInfo.setAodSecondaryColor(iTransformAodColor2);
            this.mClockInfo.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(secondaryBlendColor));
            return;
        }
        if (z) {
            color = (map == null || map.get("neutral-variant30") == null) ? this.mClockInfo.getPrimaryColor() : ((Integer) map.get("neutral-variant30")).intValue();
        } else {
            color = Color.parseColor("#FFABABAB");
        }
        if (z) {
            color2 = (map == null || map.get("secondary70") == null) ? color : ((Integer) map.get("secondary70")).intValue();
        } else {
            color2 = Color.parseColor("#FFBEBEBE");
        }
        this.mClockInfo.setSecondaryColor(-1);
        this.mClockInfo.setSecondaryBlendColor(color);
        this.mClockInfo.setAodSecondaryColor(color2);
        this.mClockInfo.setAodSecondaryBlendColor(color2);
        this.mClockInfo.setFullAodSecondaryBlendColor(Color.parseColor("#FFABABAB"));
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo6 = this.mClockInfo;
        miuiAestheticsClockBaseInfo6.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(miuiAestheticsClockBaseInfo6.getSecondaryColor()));
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        String str;
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo;
        super.setClockPalette(i, z, map, z2);
        if (map != null) {
            str = "secondary30=" + map.get("secondary30");
        } else {
            str = "null";
        }
        Log.d("MiuiAestheticsAClock ClockPalette", "setClockPalette: type = " + i + ", textDark = " + z + ", palette = " + str);
        updateHighTextColors(this.mClockInfo);
        if (this.mClockInfo == null || DeviceConfig.isHighTextContrastEnabled(this.mContext) || this.mClockInfo.isSuperSaveOpen() || (miuiAestheticsClockBaseInfo = this.mClockInfo) == null) {
            return;
        }
        int clockEffect = miuiAestheticsClockBaseInfo.getClockEffect();
        if (ClockEffectUtils.needPickSolidColor(clockEffect, DeviceConfig.supportBackgroundBlur(this.mContext))) {
            if (ClockEffectUtils.isBlurMixType(clockEffect)) {
                if (!this.mClockInfo.isAutoPrimaryColor()) {
                    this.mClockInfo.convertPrimaryColorByBlendColor(true);
                }
                if (!this.mClockInfo.isAutoSecondaryColor()) {
                    this.mClockInfo.convertSecondaryColorByBlendColor(true);
                }
            }
            setSolidColor(z, map);
        } else if (ClockEffectUtils.isBlurMixType(this.mClockInfo.getClockEffect())) {
            setBlurMixColor(z, map);
        } else if (!ClockEffectUtils.isGradualType(this.mClockInfo.getClockEffect()) || !DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            if (ClockEffectUtils.isDifferenceType(this.mClockInfo.getClockEffect())) {
                setDiffColor(z, map);
            } else if (ClockEffectUtils.isGlowType(this.mClockInfo.getClockEffect())) {
                DeviceConfig.supportAdvanceVisualEffect(this.mContext);
            }
        }
        Log.d("MiuiAestheticsAClock ClockPalette", "updateColor:\nPrimaryColor=" + ColorUtils.getColorHex(this.mClockInfo.getPrimaryColor()) + "\nSecondaryColor=" + ColorUtils.getColorHex(this.mClockInfo.getSecondaryColor()) + "\nBlendColor=" + ColorUtils.getColorHex(this.mClockInfo.getBlendColor()) + "\nSecondaryBlendColor=" + ColorUtils.getColorHex(this.mClockInfo.getSecondaryBlendColor()) + "\nOriginMagazineColor=" + ColorUtils.getColorHex(this.mClockInfo.getOriginMagazineColor()) + "\nInfoAreaColor=" + ColorUtils.getColorHex(this.mClockInfo.getInfoAreaColor()) + "\nAodPrimaryColor=" + ColorUtils.getColorHex(this.mClockInfo.getAodPrimaryColor()) + "\nAodSecondaryColor=" + ColorUtils.getColorHex(this.mClockInfo.getAodSecondaryColor()) + "\nAodBlendColor=" + ColorUtils.getColorHex(this.mClockInfo.getAodBlendColor()) + "\nAodSecondaryBlendColor=" + ColorUtils.getColorHex(this.mClockInfo.getAodSecondaryBlendColor()) + "\nFullAodPrimaryColor=" + ColorUtils.getColorHex(this.mClockInfo.getFullAodPrimaryColor()) + "\nFullAodSecondaryColor=" + ColorUtils.getColorHex(this.mClockInfo.getFullAodSecondaryColor()) + "\nFullAodBlendColor=" + ColorUtils.getColorHex(this.mClockInfo.getFullAodBlendColor()) + "\nFullAodSecondaryBlendColor=" + ColorUtils.getColorHex(this.mClockInfo.getFullAodSecondaryBlendColor()) + "\nFullAodInfoColor=" + ColorUtils.getColorHex(this.mClockInfo.getFullAodInfoColor()));
        updateColor();
    }

    public void setClockStyle(int i) {
        this.clockIsWide = i == 1;
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        this.mClockInfo = (MiuiAestheticsClockBaseInfo) clockStyleInfo;
        clearColorEffect(false);
        if (isPreviewType()) {
            queryDataSync();
        } else {
            queryDataAsync();
        }
        if (!this.mHasPresetData) {
            presetData(this.mClockInfo.getWeatherJson());
        }
        setClockStyle(this.mClockInfo.getClockStyle());
        updateTime();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setCurrentUserId(int i) {
        this.mCurrentUserId = i;
    }

    public final void setDiffColor(boolean z, Map map) {
        if (!this.mClockInfo.isAutoPrimaryColor()) {
            MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo = this.mClockInfo;
            miuiAestheticsClockBaseInfo.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(miuiAestheticsClockBaseInfo.getBlendColor()));
            MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo2 = this.mClockInfo;
            miuiAestheticsClockBaseInfo2.setAodBlendColor(GlobalColorUtils.transformAodColor(miuiAestheticsClockBaseInfo2.getBlendColor()));
            MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo3 = this.mClockInfo;
            miuiAestheticsClockBaseInfo3.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(miuiAestheticsClockBaseInfo3.getBlendColor()));
        } else if (z) {
            if (map != null && map.get("secondary15") != null) {
                int iIntValue = ((Integer) map.get("secondary15")).intValue();
                this.mClockInfo.setPrimaryColor(iIntValue);
                this.mClockInfo.setBlendColor(iIntValue);
                this.mClockInfo.setAodBlendColor(GlobalColorUtils.transformAodColor(iIntValue));
                this.mClockInfo.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(iIntValue));
            }
        } else if (map != null && map.get("secondary85") != null) {
            int iIntValue2 = ((Integer) map.get("secondary85")).intValue();
            this.mClockInfo.setPrimaryColor(iIntValue2);
            this.mClockInfo.setBlendColor(iIntValue2);
            this.mClockInfo.setAodBlendColor(GlobalColorUtils.transformAodColor(iIntValue2));
            this.mClockInfo.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(iIntValue2));
        }
        if (!this.mClockInfo.isAutoSecondaryColor()) {
            MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo4 = this.mClockInfo;
            miuiAestheticsClockBaseInfo4.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(miuiAestheticsClockBaseInfo4.getSecondaryBlendColor()));
            MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo5 = this.mClockInfo;
            miuiAestheticsClockBaseInfo5.setAodSecondaryBlendColor(GlobalColorUtils.transformAodColor(miuiAestheticsClockBaseInfo5.getSecondaryBlendColor()));
            MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo6 = this.mClockInfo;
            miuiAestheticsClockBaseInfo6.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(miuiAestheticsClockBaseInfo6.getSecondaryBlendColor()));
            return;
        }
        if (z) {
            if (map == null || map.get("secondary15") == null) {
                return;
            }
            int iIntValue3 = ((Integer) map.get("secondary15")).intValue();
            this.mClockInfo.setSecondaryColor(iIntValue3);
            this.mClockInfo.setSecondaryBlendColor(iIntValue3);
            this.mClockInfo.setAodSecondaryBlendColor(GlobalColorUtils.transformAodColor(iIntValue3));
            this.mClockInfo.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(iIntValue3));
            return;
        }
        if (map == null || map.get("secondary85") == null) {
            return;
        }
        int iIntValue4 = ((Integer) map.get("secondary85")).intValue();
        this.mClockInfo.setSecondaryColor(iIntValue4);
        this.mClockInfo.setSecondaryBlendColor(iIntValue4);
        this.mClockInfo.setAodSecondaryBlendColor(GlobalColorUtils.transformAodColor(iIntValue4));
        this.mClockInfo.setFullAodSecondaryBlendColor(GlobalColorUtils.transformFullAodColor(iIntValue4));
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

    public final void setSolidColor(boolean z, Map map) {
        int primaryColor;
        int secondaryColor = -1;
        if (this.mClockInfo.isAutoPrimaryColor()) {
            if (z) {
                primaryColor = (map == null || map.get("secondary30") == null) ? this.mClockInfo.getPrimaryColor() : ((Integer) map.get("secondary30")).intValue();
            } else {
                primaryColor = -1;
            }
            this.mClockInfo.setPrimaryColor(primaryColor);
        }
        if (this.mClockInfo.isAutoSecondaryColor()) {
            if (z) {
                secondaryColor = (map == null || map.get("secondary30") == null) ? this.mClockInfo.getSecondaryColor() : ((Integer) map.get("secondary30")).intValue();
            }
            this.mClockInfo.setSecondaryColor(secondaryColor);
        }
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo = this.mClockInfo;
        miuiAestheticsClockBaseInfo.setOriginMagazineColor(miuiAestheticsClockBaseInfo.getPrimaryColor());
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo2 = this.mClockInfo;
        miuiAestheticsClockBaseInfo2.setInfoAreaColor(miuiAestheticsClockBaseInfo2.getPrimaryColor());
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo3 = this.mClockInfo;
        miuiAestheticsClockBaseInfo3.setAodPrimaryColor(GlobalColorUtils.transformAodColor(miuiAestheticsClockBaseInfo3.getPrimaryColor()));
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo4 = this.mClockInfo;
        miuiAestheticsClockBaseInfo4.setAodSecondaryColor(GlobalColorUtils.transformAodColor(miuiAestheticsClockBaseInfo4.getSecondaryColor()));
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo5 = this.mClockInfo;
        miuiAestheticsClockBaseInfo5.setAodInfoColor(GlobalColorUtils.transformAodColor(miuiAestheticsClockBaseInfo5.getAodInfoColor()));
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo6 = this.mClockInfo;
        miuiAestheticsClockBaseInfo6.setFullAodPrimaryColor(GlobalColorUtils.transformFullAodColor(miuiAestheticsClockBaseInfo6.getPrimaryColor()));
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo7 = this.mClockInfo;
        miuiAestheticsClockBaseInfo7.setFullAodSecondaryColor(GlobalColorUtils.transformFullAodColor(miuiAestheticsClockBaseInfo7.getSecondaryColor()));
        MiuiAestheticsClockBaseInfo miuiAestheticsClockBaseInfo8 = this.mClockInfo;
        miuiAestheticsClockBaseInfo8.setFullAodInfoColor(GlobalColorUtils.transformFullAodColor(miuiAestheticsClockBaseInfo8.getInfoAreaColor()));
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
        setClockPalette(this.mChoosePaletteType, this.mTextDark, this.mPalette, z);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        this.m24HourFormat = DateFormatUtils.is24HourFormat(getContext());
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTimeZone(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mCalendar = new Calendar(TimeZone.getTimeZone(str));
        updateTime();
    }

    public final void updateWeather(WeatherBean weatherBean) {
        this.mWeatherBean = weatherBean;
        updateTime();
    }
}
