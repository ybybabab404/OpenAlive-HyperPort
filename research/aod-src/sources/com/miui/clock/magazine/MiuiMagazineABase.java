package com.miui.clock.magazine;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.MiuiGalleryBaseClock;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.Effect;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.ColorUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.GlobalColorUtils;
import com.miui.clock.utils.MiuiBlurUtils;
import java.util.Map;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class MiuiMagazineABase extends MiuiGalleryBaseClock {
    public Typeface chamberi;
    public Typeface chamberi_italic;
    public ViewGroup mClockContainer;
    public ViewGroup mClockContainerInner;
    public MiuiMagazineAInfoBase mClockInfo;
    public Space mDateCenterSpace;
    public View mDateSpace;
    public TextView mDateText;
    public Space mTimeCenterSpace;
    public View mTimeSpace;
    public TextView mTimeText;
    public View mWeekSpace;
    public TextView mWeekText;
    public Typeface qinghe;

    /* JADX INFO: renamed from: com.miui.clock.magazine.MiuiMagazineABase$1, reason: invalid class name */
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
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_TIME.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_WEEK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.MAGAZINE_INFO.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.CLOCK_CONTAINER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public MiuiMagazineABase(Context context) {
        super(context);
    }

    public MiuiMagazineABase(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        if (this.mClockInfo == null) {
            return;
        }
        if (!ClockEffectUtils.isLessThanOS2()) {
            ClockEffectUtils.clearClockEffectsContainer(this, this.mClockInfo, z);
            ClockEffectUtils.clearClockEffectsView(this.mClockContainer, this.mClockInfo, z);
        } else if (DeviceConfig.supportBackgroundBlur(this.mContext) || DeviceConfig.isHighTextContrastEnabled(this.mContext)) {
            MiuiBlurUtils.clearContainerPassBlur(this);
            MiuiBlurUtils.clearMemberBlendColor(this.mClockContainer);
        }
    }

    public final int extraTopMarginForWeek() {
        if (FontUtils.isZhOrEn()) {
            return 0;
        }
        return getDimen(R.dimen.miui_magazine_a_clock_week_extra_margin_top_for_bo);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public ClockStyleInfo getClockStyleInfo() {
        return this.mClockInfo;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getGalleryGravity() {
        MiuiMagazineAInfoBase miuiMagazineAInfoBase = this.mClockInfo;
        if (miuiMagazineAInfoBase == null || miuiMagazineAInfoBase.getClockStyle() == 1) {
            return 8388611;
        }
        if (this.mClockInfo.getClockStyle() == 2) {
            return 1;
        }
        return this.mClockInfo.getClockStyle() == 3 ? 8388613 : 8388611;
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
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()];
        if (i == 1) {
            return this.mDateText;
        }
        if (i == 2) {
            return this.mTimeText;
        }
        if (i == 3) {
            return this.mWeekText;
        }
        if (i != 4) {
            return i != 5 ? super.getIClockView(clockViewType) : this.mClockContainer;
        }
        return this.mMagazineGroup;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getMagazineColor() {
        MiuiMagazineAInfoBase miuiMagazineAInfoBase = this.mClockInfo;
        if (miuiMagazineAInfoBase != null) {
            return ColorUtils.blendColor(miuiMagazineAInfoBase.getOriginMagazineColor(), 0.6f);
        }
        return 0;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockBottom() {
        return this.mMagazineInfoVisible ? getDimen(R.dimen.notification_bottom_magazine_a_with_gallery) : getDimen(R.dimen.notification_bottom_magazine_a);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockTop() {
        return getDimen(R.dimen.miui_magazine_a_clock_notification_margin_top);
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationRelativePosition() {
        MiuiMagazineAInfoBase miuiMagazineAInfoBase;
        int i = getResources().getConfiguration().orientation;
        if (!DeviceConfig.PAD_DEVICE || i != 2 || (miuiMagazineAInfoBase = this.mClockInfo) == null) {
            return 1;
        }
        if (miuiMagazineAInfoBase.getClockStyle() == 1) {
            return 2;
        }
        return this.mClockInfo.getClockStyle() == 3 ? 3 : 1;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mDateText = (TextView) findViewById(R.id.current_date);
        this.mWeekText = (TextView) findViewById(R.id.current_week);
        this.mTimeText = (TextView) findViewById(R.id.current_time);
        this.mDateSpace = findViewById(R.id.date_space);
        this.mTimeSpace = findViewById(R.id.time_space);
        this.mWeekSpace = findViewById(R.id.week_space);
        this.mDateCenterSpace = (Space) findViewById(R.id.date_center_space);
        this.mTimeCenterSpace = (Space) findViewById(R.id.time_center_space);
        this.mClockContainer = (ViewGroup) findViewById(R.id.clock_container);
        this.mClockContainerInner = (ViewGroup) findViewById(R.id.clock_container_inner);
        this.chamberi = Typeface.create("miclock-chamberi-display-semibold", 0);
        this.chamberi_italic = Typeface.create("miclock-chamberi-display-semibold-italic", 0);
        this.qinghe = Typeface.create("miclock-qinghe-75w", 0);
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void onLanguageChanged() {
        super.onLanguageChanged();
        updateViewsLayoutParams();
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
        int iTransformAodColor;
        int color;
        int originMagazineColor;
        super.setClockPalette(i, z, map, z2);
        if (map != null) {
            str = "secondary30=" + map.get("secondary30");
        } else {
            str = "null";
        }
        Log.d("ClockPalette", "setClockPalette: type = " + i + ", textDark = " + z + ", palette = " + str);
        if (this.mClockInfo == null || DeviceConfig.isHighTextContrastEnabled(this.mContext)) {
            return;
        }
        int clockEffect = this.mClockInfo.getClockEffect();
        boolean zSupportBackgroundBlur = DeviceConfig.supportBackgroundBlur(this.mContext);
        if (!ClockEffectUtils.needPickSolidColor(clockEffect) || this.mClockInfo.isAutoPrimaryColor()) {
            if (ClockEffectUtils.isDifferenceType(clockEffect)) {
                if (this.mClockInfo.isAutoPrimaryColor()) {
                    if (z) {
                        if (map != null && map.get("secondary15") != null) {
                            int iIntValue = map.get("secondary15").intValue();
                            if (ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType())) {
                                this.mClockInfo.setPrimaryColor(GlobalColorUtils.transformAodColor(iIntValue));
                            } else {
                                this.mClockInfo.setPrimaryColor(iIntValue);
                            }
                            this.mClockInfo.setBlendColor(iIntValue);
                        }
                    } else if (map != null && map.get("secondary85") != null) {
                        int iIntValue2 = map.get("secondary85").intValue();
                        if (ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType())) {
                            this.mClockInfo.setPrimaryColor(GlobalColorUtils.transformAodColor(iIntValue2));
                        } else {
                            this.mClockInfo.setPrimaryColor(iIntValue2);
                        }
                        this.mClockInfo.setBlendColor(iIntValue2);
                    }
                }
                if (!ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
                    updateClockEffectsContainer();
                    updateClockBlendColor();
                }
                this.mClockInfo.convertAnimationColor();
                MiuiMagazineAInfoBase miuiMagazineAInfoBase = this.mClockInfo;
                miuiMagazineAInfoBase.setOriginMagazineColor(miuiMagazineAInfoBase.getBlendColor());
            } else if (ClockEffectUtils.isGlowType(clockEffect) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
                int blendColor = this.mClockInfo.getBlendColor();
                Effect.Glow glowParam = Effect.GlowEffect.getGlowParam(blendColor);
                MiuiBlurUtils.setGlowEffectContainer(this);
                MiuiBlurUtils.setGlowEffectMethod(this.mClockContainer, glowParam, ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()));
                this.mClockInfo.setOriginMagazineColor(blendColor);
            } else {
                int primaryColor = -1;
                if (ClockEffectUtils.isBlurMixType(clockEffect) && zSupportBackgroundBlur) {
                    if (this.mClockInfo.isAutoPrimaryColor()) {
                        if (z) {
                            color = (map == null || map.get("neutral-variant30") == null) ? this.mClockInfo.getPrimaryColor() : map.get("neutral-variant30").intValue();
                        } else {
                            color = Color.parseColor("#FFABABAB");
                        }
                        if (z) {
                            iTransformAodColor = (map == null || map.get("secondary70") == null) ? color : map.get("secondary70").intValue();
                        } else {
                            iTransformAodColor = Color.parseColor("#FFBEBEBE");
                        }
                        if (z) {
                            originMagazineColor = (map == null || map.get("neutral-variant30") == null) ? this.mClockInfo.getOriginMagazineColor() : map.get("neutral-variant30").intValue();
                        } else {
                            originMagazineColor = -1;
                        }
                        this.mClockInfo.setBlendColor(color);
                        this.mClockInfo.setFullAodBlendColor(Color.parseColor("#FFABABAB"));
                        this.mClockInfo.setOriginMagazineColor(originMagazineColor);
                    } else {
                        int blendColor2 = this.mClockInfo.getBlendColor();
                        iTransformAodColor = GlobalColorUtils.transformAodColor(blendColor2);
                        this.mClockInfo.setFullAodBlendColor(GlobalColorUtils.transformFullAodColor(blendColor2));
                        this.mClockInfo.setOriginMagazineColor(blendColor2);
                    }
                    this.mClockInfo.setPrimaryColor(-1);
                    this.mClockInfo.setAodBlendColor(iTransformAodColor);
                    this.mClockInfo.setAodPrimaryColor(iTransformAodColor);
                    if (!ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType())) {
                        if (!this.mClockInfo.isDisableContainerPassBlur()) {
                            updateClockEffectsContainer();
                        }
                        updateClockBlendColor();
                    }
                } else {
                    if (ClockEffectUtils.isBlurMixType(clockEffect)) {
                        if (!this.mClockInfo.isAutoPrimaryColor()) {
                            this.mClockInfo.convertPrimaryColorByBlendColor(true);
                        }
                        if (!this.mClockInfo.isAutoSecondaryColor()) {
                            this.mClockInfo.convertSecondaryColorByBlendColor(true);
                        }
                    }
                    int primaryColor2 = this.mClockInfo.getPrimaryColor();
                    if (this.mClockInfo.isAutoPrimaryColor()) {
                        if (z) {
                            primaryColor = (map == null || map.get("secondary30") == null) ? this.mClockInfo.getPrimaryColor() : map.get("secondary30").intValue();
                        }
                        primaryColor2 = primaryColor;
                    }
                    this.mClockInfo.setPrimaryColor(primaryColor2);
                    this.mClockInfo.setOriginMagazineColor(primaryColor2);
                }
                this.mClockInfo.convertAnimationColor();
            }
            updateColor();
        }
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        this.mClockInfo = (MiuiMagazineAInfoBase) clockStyleInfo;
        updateViewsLayoutParams();
        clearColorEffect(false);
        updateTime();
        updateColor();
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

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiClockController.IClockView
    public void updateClockBlendColor(int i, int i2, int i3, int i4) {
        if (this.mClockInfo != null) {
            boolean zUpdateTextDark = updateTextDark(this.mTextDark);
            int textDarkAlpha = getTextDarkAlpha(this.mTextDark);
            ViewGroup viewGroup = this.mClockContainer;
            MiuiMagazineAInfoBase miuiMagazineAInfoBase = this.mClockInfo;
            ClockEffectUtils.setClockEffectsView(viewGroup, miuiMagazineAInfoBase, zUpdateTextDark, i, miuiMagazineAInfoBase.getPrimaryColor(), 0, ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()), ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()), textDarkAlpha);
        }
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock
    public void updateClockEffectsContainer() {
        super.updateClockEffectsContainer();
        updateViewBackgroundBlurContainer(this, getDimen(R.dimen.miui_magazine_a_clock_blur_radius), this.mClockContainer);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateColor() {
        updatePrimaryColor(ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()) ? this.mClockInfo.getFullAodPrimaryColor() : this.mClockInfo.getPrimaryColor());
    }

    public void updatePrimaryColor(int i) {
        this.mTimeText.setTextColor(i);
        this.mDateText.setTextColor(i);
        this.mWeekText.setTextColor(i);
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        this.mTimeText.setText(DateFormatUtils.formatTime(this.mContext.getString(this.m24HourFormat ? R.string.miui_clock_time_format_24 : R.string.miui_clock_time_format_12)));
        this.mDateText.setText(DateFormatUtils.formatTime(this.mContext.getString(R.string.miui_magazine_clock_date)).toUpperCase());
        TextView textView = this.mDateText;
        Calendar calendar = this.mCalendar;
        Context context = this.mContext;
        textView.setContentDescription(calendar.format(context, context.getString(R.string.miui_magazine_clock_date_content_description)));
        TextView textView2 = this.mWeekText;
        Calendar calendar2 = this.mCalendar;
        Context context2 = this.mContext;
        textView2.setText(calendar2.format(context2, context2.getString(R.string.miui_magazine_clock_week)).toUpperCase());
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mClockContainer.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mClockContainerInner.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mMagazineGroup.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(R.dimen.miui_magazine_a_clock_width);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(R.dimen.miui_magazine_a_clock_height);
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) this.mTimeText.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams5 = (ConstraintLayout.LayoutParams) this.mDateText.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams6 = (ConstraintLayout.LayoutParams) this.mWeekText.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams7 = (ConstraintLayout.LayoutParams) this.mDateSpace.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams8 = (ConstraintLayout.LayoutParams) this.mTimeSpace.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams9 = (ConstraintLayout.LayoutParams) this.mWeekSpace.getLayoutParams();
        MiuiMagazineAInfoBase miuiMagazineAInfoBase = this.mClockInfo;
        if (miuiMagazineAInfoBase != null) {
            if (miuiMagazineAInfoBase.getClockStyle() == 2) {
                int i = R.dimen.miui_magazine_a_clock_date_style2_margin_top;
                ((ViewGroup.MarginLayoutParams) layoutParams7).topMargin = getDimen(i);
                int i2 = R.dimen.miui_magazine_a_clock_time_style2_margin_top;
                ((ViewGroup.MarginLayoutParams) layoutParams8).topMargin = getDimen(i2);
                ((ViewGroup.MarginLayoutParams) layoutParams9).topMargin = getDimen(R.dimen.miui_magazine_a_clock_week_style2_margin_top) + extraTopMarginForWeek();
                ConstraintLayout.LayoutParams layoutParams10 = (ConstraintLayout.LayoutParams) this.mDateCenterSpace.getLayoutParams();
                ((ViewGroup.MarginLayoutParams) layoutParams10).topMargin = getDimen(i);
                layoutParams10.setMarginStart(getDimen(R.dimen.miui_magazine_a_clock_date_style2_center_x));
                this.mDateCenterSpace.setLayoutParams(layoutParams10);
                ConstraintLayout.LayoutParams layoutParams11 = (ConstraintLayout.LayoutParams) this.mTimeCenterSpace.getLayoutParams();
                ((ViewGroup.MarginLayoutParams) layoutParams11).topMargin = getDimen(i2);
                layoutParams11.setMarginStart(getDimen(R.dimen.miui_magazine_a_clock_time_style2_center_x));
                this.mTimeCenterSpace.setLayoutParams(layoutParams11);
                int i3 = R.dimen.miui_magazine_a_clock_date_style2_minwidth;
                ((ViewGroup.MarginLayoutParams) layoutParams5).width = getDimen(i3);
                ((ViewGroup.MarginLayoutParams) layoutParams5).height = getDimen(R.dimen.miui_magazine_a_clock_date_style2_height);
                ((ViewGroup.MarginLayoutParams) layoutParams4).width = getDimen(i3);
                ((ViewGroup.MarginLayoutParams) layoutParams4).height = getDimen(R.dimen.miui_magazine_a_clock_time_style2_height);
                ((ViewGroup.MarginLayoutParams) layoutParams6).width = -1;
                ((ViewGroup.MarginLayoutParams) layoutParams6).height = getDimen(R.dimen.miui_magazine_a_clock_week_style2_height);
                layoutParams5.baselineToBaseline = R.id.date_space;
                layoutParams4.baselineToBaseline = R.id.time_space;
                layoutParams6.baselineToBaseline = R.id.week_space;
                int i4 = R.id.date_center_space;
                layoutParams5.startToStart = i4;
                int i5 = R.id.time_center_space;
                layoutParams4.startToStart = i5;
                layoutParams6.startToStart = 0;
                layoutParams5.endToEnd = i4;
                layoutParams4.endToEnd = i5;
                layoutParams6.endToEnd = 0;
                layoutParams5.setMarginStart(0);
                layoutParams4.setMarginStart(0);
                layoutParams6.setMarginStart(0);
                layoutParams5.setMarginEnd(0);
                layoutParams4.setMarginEnd(0);
                layoutParams6.setMarginEnd(0);
                this.mDateText.setLayoutParams(layoutParams5);
                this.mTimeText.setLayoutParams(layoutParams4);
                this.mWeekText.setLayoutParams(layoutParams6);
                this.mDateText.setTextSize(0, getDimen(R.dimen.miui_magazine_a_clock_date_style2_text_size));
                this.mTimeText.setTextSize(0, getDimen(R.dimen.miui_magazine_a_clock_time_style2_text_size));
                this.mWeekText.setTextSize(0, getDimen(R.dimen.miui_magazine_a_clock_week_style2_text_size));
                this.mDateText.setTypeface(this.chamberi_italic);
                this.mTimeText.setTypeface(this.chamberi_italic);
                this.mWeekText.setTypeface(this.qinghe);
                this.mDateText.setLetterSpacing(0.0f);
                this.mTimeText.setLetterSpacing(0.0f);
                this.mDateText.setGravity(1);
                this.mTimeText.setGravity(1);
                this.mWeekText.setGravity(1);
                layoutParams2.startToStart = 0;
                layoutParams2.endToEnd = 0;
                layoutParams3.startToStart = 0;
                layoutParams3.endToEnd = 0;
                layoutParams2.topToTop = 0;
                layoutParams2.setMarginStart(0);
                layoutParams3.setMarginStart(0);
                layoutParams2.setMarginEnd(0);
                layoutParams3.setMarginEnd(0);
                ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimen(R.dimen.miui_magazine_a_clock_style_2_margin_inner_top);
            } else {
                ((ViewGroup.MarginLayoutParams) layoutParams7).topMargin = getDimen(R.dimen.miui_magazine_a_clock_date_style1_margin_top);
                ((ViewGroup.MarginLayoutParams) layoutParams8).topMargin = getDimen(R.dimen.miui_magazine_a_clock_time_style1_margin_top);
                ((ViewGroup.MarginLayoutParams) layoutParams9).topMargin = getDimen(R.dimen.miui_magazine_a_clock_week_style1_margin_top) + extraTopMarginForWeek();
                ((ViewGroup.MarginLayoutParams) layoutParams5).width = -2;
                ((ViewGroup.MarginLayoutParams) layoutParams5).height = getDimen(R.dimen.miui_magazine_a_clock_date_style1_height);
                ((ViewGroup.MarginLayoutParams) layoutParams4).width = -2;
                ((ViewGroup.MarginLayoutParams) layoutParams4).height = getDimen(R.dimen.miui_magazine_a_clock_time_style1_height);
                ((ViewGroup.MarginLayoutParams) layoutParams6).width = -2;
                ((ViewGroup.MarginLayoutParams) layoutParams6).height = getDimen(R.dimen.miui_magazine_a_clock_week_style1_height);
                layoutParams5.baselineToBaseline = R.id.date_space;
                layoutParams4.baselineToBaseline = R.id.time_space;
                layoutParams6.baselineToBaseline = R.id.week_space;
                if (this.mClockInfo.getClockStyle() == 1) {
                    layoutParams5.startToStart = 0;
                    layoutParams4.startToStart = 0;
                    layoutParams6.startToStart = 0;
                    layoutParams5.endToEnd = -1;
                    layoutParams4.endToEnd = -1;
                    layoutParams6.endToEnd = -1;
                    layoutParams5.setMarginStart(getDimen(R.dimen.miui_magazine_a_clock_date_style1_margin_start));
                    layoutParams4.setMarginStart(getDimen(R.dimen.miui_magazine_a_clock_time_style1_margin_start));
                    layoutParams6.setMarginStart(getDimen(R.dimen.miui_magazine_a_clock_week_style1_margin_start));
                    layoutParams5.setMarginEnd(0);
                    layoutParams4.setMarginEnd(0);
                    layoutParams6.setMarginEnd(0);
                    layoutParams2.startToStart = 0;
                    layoutParams3.startToStart = 0;
                    layoutParams2.endToEnd = -1;
                    layoutParams3.endToEnd = -1;
                    layoutParams2.topToTop = 0;
                    layoutParams2.setMarginStart(getDimen(R.dimen.miui_magazine_a_clock_margin_start));
                    ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimen(R.dimen.miui_magazine_a_clock_style_1_margin_inner_top);
                    layoutParams3.setMarginStart(getDimen(R.dimen.miui_magazine_a_gallery_margin_start));
                    layoutParams2.setMarginEnd(0);
                    layoutParams3.setMarginEnd(0);
                } else {
                    layoutParams5.endToEnd = 0;
                    layoutParams4.endToEnd = 0;
                    layoutParams6.endToEnd = 0;
                    layoutParams5.startToStart = -1;
                    layoutParams4.startToStart = -1;
                    layoutParams6.startToStart = -1;
                    layoutParams5.setMarginStart(0);
                    layoutParams4.setMarginStart(0);
                    layoutParams6.setMarginStart(0);
                    layoutParams5.setMarginEnd(getDimen(R.dimen.miui_magazine_a_clock_date_style1_margin_start));
                    layoutParams4.setMarginEnd(getDimen(R.dimen.miui_magazine_a_clock_time_style1_margin_start));
                    layoutParams6.setMarginEnd(getDimen(R.dimen.miui_magazine_a_clock_week_style1_margin_end));
                    layoutParams2.endToEnd = 0;
                    layoutParams3.endToEnd = 0;
                    layoutParams2.startToStart = -1;
                    layoutParams3.startToStart = -1;
                    layoutParams2.topToTop = 0;
                    layoutParams2.setMarginStart(0);
                    ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimen(R.dimen.miui_magazine_a_clock_style_1_margin_inner_top);
                    layoutParams3.setMarginStart(0);
                    layoutParams2.setMarginEnd(getDimen(R.dimen.miui_magazine_a_clock_margin_end));
                    layoutParams3.setMarginEnd(getDimen(R.dimen.miui_magazine_a_gallery_margin_end));
                }
                this.mDateText.setLayoutParams(layoutParams5);
                this.mTimeText.setLayoutParams(layoutParams4);
                this.mWeekText.setLayoutParams(layoutParams6);
                this.mDateText.setTextSize(0, getDimen(R.dimen.miui_magazine_a_clock_date_style1_text_size));
                this.mTimeText.setTextSize(0, getDimen(R.dimen.miui_magazine_a_clock_time_style1_text_size));
                this.mWeekText.setTextSize(0, getDimen(R.dimen.miui_magazine_a_clock_week_style1_text_size));
                this.mDateText.setTypeface(this.chamberi);
                this.mTimeText.setTypeface(this.chamberi);
                this.mWeekText.setTypeface(this.qinghe);
                this.mDateText.setLetterSpacing(0.03f);
                this.mTimeText.setLetterSpacing(0.03f);
            }
            this.mClockContainerInner.setLayoutParams(layoutParams2);
            this.mClockContainer.setLayoutParams(layoutParams);
            this.mMagazineGroup.setLayoutParams(layoutParams3);
            if (this.mClockInfo.getClockStyle() == 2) {
                layoutParams.startToStart = 0;
                layoutParams.endToEnd = 0;
            } else if (this.mClockInfo.getClockStyle() == 1) {
                layoutParams.startToStart = 0;
                layoutParams.endToEnd = -1;
            } else {
                layoutParams.endToEnd = 0;
                layoutParams.startToStart = -1;
            }
            this.mClockContainer.setLayoutParams(layoutParams);
        }
    }
}
