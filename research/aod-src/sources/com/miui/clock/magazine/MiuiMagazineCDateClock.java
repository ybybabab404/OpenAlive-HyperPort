package com.miui.clock.magazine;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.Effect;
import com.miui.clock.utils.BaseLineSpaceView;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.MiuiBlurUtils;
import java.util.Map;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class MiuiMagazineCDateClock extends MiuiMagazineCBase {
    public ViewGroup mClockContainer;
    public TextView mDateTextStyle2;
    public float mDeviceScale;
    public TextView mNotificationDateTextStyle2;
    public BaseLineSpaceView mSpaceTimeStyle2;
    public boolean supportAccessibilityScale;

    /* JADX INFO: renamed from: com.miui.clock.magazine.MiuiMagazineCDateClock$1, reason: invalid class name */
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
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.CLOCK_CONTAINER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.NOTIFICATION_DATE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public MiuiMagazineCDateClock(Context context) {
        super(context);
        this.mDeviceScale = 1.0f;
        this.supportAccessibilityScale = true;
    }

    public MiuiMagazineCDateClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDeviceScale = 1.0f;
        this.supportAccessibilityScale = true;
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiClockController.IClockView
    public void clearColorEffect(boolean z) {
        if (this.mClockInfo == null) {
            return;
        }
        if (!ClockEffectUtils.isLessThanOS2()) {
            ClockEffectUtils.clearClockEffectsContainer(this.mClockContainer, this.mClockInfo, z);
            ClockEffectUtils.clearClockEffectsView(this.mClockContainer, this.mClockInfo, z);
            ClockEffectUtils.clearClockEffectsView(this.mDateTextStyle2, this.mClockInfo, z);
            ClockEffectUtils.clearClockEffectsView(this.mNotificationDateTextStyle2, this.mClockInfo, z);
            return;
        }
        if (this.mClockInfo.isAutoPrimaryColor() && DeviceConfig.supportBackgroundBlur(this.mContext) && !DeviceConfig.isHighTextContrastEnabled(this.mContext)) {
            return;
        }
        MiuiBlurUtils.clearContainerPassBlur(this.mClockContainer);
        MiuiBlurUtils.clearMemberBlendColor(this.mClockContainer);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()];
        if (i == 1) {
            return this.mDateTextStyle2;
        }
        if (i != 2) {
            return i != 3 ? super.getIClockView(clockViewType) : this.mNotificationDateTextStyle2;
        }
        return this.mClockContainer;
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void ignoreAccessibilityScale(boolean z) {
        this.supportAccessibilityScale = !z;
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mDateTextStyle2 = (TextView) findViewById(R.id.current_date_style2);
        this.mNotificationDateTextStyle2 = (TextView) findViewById(R.id.current_notification_date_style2);
        this.mClockContainer = (ViewGroup) findViewById(R.id.clock_container);
        this.mSpaceTimeStyle2 = (BaseLineSpaceView) findViewById(R.id.time_space_style2);
        updateViewsLayoutParams();
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        super.setClockPalette(i, z, map, z2);
        if (DeviceConfig.isHighTextContrastEnabled(this.mContext)) {
            return;
        }
        updateViewBackgroundBlurContainer(this.mClockContainer, getDimen(R.dimen.miui_magazine_c_clock_blur_radius), this.mDateTextStyle2, this.mNotificationDateTextStyle2);
        if (!ClockEffectUtils.isGradualType(this.mClockInfo.getClockEffect()) || !DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            if (ClockEffectUtils.isDifferenceType(this.mClockInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
                MiuiBlurUtils.setMemberDiffEffectColor(this.mDateTextStyle2, this.mClockInfo.getBlendColor());
                MiuiBlurUtils.setMemberDiffEffectColor(this.mNotificationDateTextStyle2, this.mClockInfo.getBlendColor());
            } else if (ClockEffectUtils.isGlowType(this.mClockInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
                Effect.Glow glowParam = Effect.GlowEffect.getGlowParam(this.mClockInfo.getBlendColor());
                MiuiBlurUtils.setGlowEffectContainer(this.mClockContainer);
                MiuiBlurUtils.setGlowEffectMethod(this.mDateTextStyle2, glowParam, false, ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()));
                MiuiBlurUtils.setGlowEffectMethod(this.mNotificationDateTextStyle2, glowParam, false, ClockStyleInfo.isFullAODType(this.mClockInfo.getDisplayType()));
            } else {
                MiuiMagazineCBaseInfo miuiMagazineCBaseInfo = this.mClockInfo;
                if (miuiMagazineCBaseInfo != null && !ClockStyleInfo.isAODType(miuiMagazineCBaseInfo.getDisplayType()) && ClockEffectUtils.isBlurMixType(this.mClockInfo.getClockEffect()) && DeviceConfig.supportBackgroundBlur(this.mContext)) {
                    MiuiBlurUtils.setMemberBlendColors(this.mDateTextStyle2, z, this.mClockInfo.getBlendColor(), this.mClockInfo.getPrimaryColor());
                    MiuiBlurUtils.setMemberBlendColors(this.mNotificationDateTextStyle2, z, this.mClockInfo.getBlendColor(), this.mClockInfo.getPrimaryColor());
                }
            }
        }
        updateTime();
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase
    public void setClockStyleInfo() {
        super.setClockStyleInfo();
        MiuiMagazineCBaseInfo miuiMagazineCBaseInfo = this.mClockInfo;
        if (miuiMagazineCBaseInfo == null) {
            return;
        }
        this.mDateTextStyle2.setTextColor(miuiMagazineCBaseInfo.getPrimaryColor());
        this.mNotificationDateTextStyle2.setTextColor(this.mClockInfo.getPrimaryColor());
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        super.setClockStyleInfo(clockStyleInfo);
        clearColorEffect(false);
        setClockStyleInfo();
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.magazine.MiuiMagazineCBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        if (this.mClockInfo == null) {
            return;
        }
        ((ViewGroup.MarginLayoutParams) ((ConstraintLayout.LayoutParams) this.mSpaceTimeStyle2.getLayoutParams())).topMargin = (int) (getDimen(R.dimen.miui_magazine_c_clock_style2_time_margin_top) * 1.0f);
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        this.mDateTextStyle2.setTextColor(this.mClockInfo.getPrimaryColor());
        this.mNotificationDateTextStyle2.setTextColor(this.mClockInfo.getPrimaryColor());
        if (FontUtils.isZh()) {
            this.mDateTextStyle2.setText(this.mCalendar.format(this.mContext, getResources().getString(R.string.miui_magazine_c_clock_style2_date)));
        } else {
            TextView textView = this.mDateTextStyle2;
            StringBuilder sb = new StringBuilder();
            Calendar calendar = this.mCalendar;
            Context context = this.mContext;
            sb.append(calendar.format(context, context.getString(R.string.format_month_day_style2)));
            sb.append("\n");
            Calendar calendar2 = this.mCalendar;
            Context context2 = this.mContext;
            sb.append(calendar2.format(context2, context2.getString(R.string.miui_rhombus_clock_week_full)));
            textView.setText(sb.toString());
        }
        this.mNotificationDateTextStyle2.setText(this.mCalendar.format(this.mContext, getResources().getString(R.string.miui_magazine_c_clock_style2_date)));
        TextView textView2 = this.mDateTextStyle2;
        Calendar calendar3 = this.mCalendar;
        Context context3 = this.mContext;
        int i = R.string.miui_magazine_c_clock_style2_date_content_description;
        textView2.setContentDescription(calendar3.format(context3, context3.getString(i)));
        TextView textView3 = this.mNotificationDateTextStyle2;
        Calendar calendar4 = this.mCalendar;
        Context context4 = this.mContext;
        textView3.setContentDescription(calendar4.format(context4, context4.getString(i)));
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
        if (this.mClockContainer != null) {
            TextView textView = this.mDateTextStyle2;
            int i = R.dimen.miui_magazine_c_clock_style2_date_text_size;
            textView.setTextSize(0, getDimen(i));
            this.mNotificationDateTextStyle2.setTextSize(0, getDimen(i));
            int dimen = getDimen(R.dimen.miui_magazine_c_clock_style2_width);
            ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mClockContainer.getLayoutParams();
            ((ViewGroup.MarginLayoutParams) layoutParams).width = (int) (dimen * 1.0f);
            if (!DeviceConfig.isJ18() || DeviceConfig.inLargeScreen(this.mContext)) {
                ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.miui_magazine_c_clock_style2_margin_top);
            } else {
                ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.miui_magazine_c_clock_style2_margin_top_j18);
            }
            ((ViewGroup.MarginLayoutParams) ((ConstraintLayout.LayoutParams) this.mSpaceTimeStyle2.getLayoutParams())).topMargin = (int) (getDimen(R.dimen.miui_magazine_c_clock_style2_time_margin_top) * 1.0f);
            ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mDateTextStyle2.getLayoutParams();
            int i2 = R.dimen.miui_magazine_c_clock_style2_date_margin_start_compare_time;
            layoutParams2.setMarginStart(getDimen(i2));
            if (!DeviceConfig.isJ18() || DeviceConfig.inLargeScreen(this.mContext)) {
                ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimen(R.dimen.miui_magazine_c_clock_style2_date_margin_top);
            } else {
                ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimen(R.dimen.miui_magazine_c_clock_style2_date_margin_top_j18);
            }
            this.mDateTextStyle2.setLayoutParams(layoutParams2);
            ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mMagazineGroup.getLayoutParams();
            layoutParams3.startToStart = R.id.clock_container;
            layoutParams3.endToEnd = -1;
            layoutParams3.setMarginStart((int) (getDimen(i2) * 1.0f));
            this.mMagazineGroup.setLayoutParams(layoutParams3);
        }
    }
}
