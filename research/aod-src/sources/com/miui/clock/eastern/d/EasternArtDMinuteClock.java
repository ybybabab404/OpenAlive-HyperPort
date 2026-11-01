package com.miui.clock.eastern.d;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.miui.clock.R;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.utils.ClassicClockTimeUtils;

/* JADX INFO: loaded from: classes.dex */
public class EasternArtDMinuteClock extends EasternArtDBase {
    public TextView timeDate;
    public TextView timeDateLunar;
    public ImageView timeMinute;
    public ImageView timeWeek;

    /* JADX INFO: renamed from: com.miui.clock.eastern.d.EasternArtDMinuteClock$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$ClockViewType;

        static {
            int[] iArr = new int[ClockViewType.values().length];
            $SwitchMap$com$miui$clock$module$ClockViewType = iArr;
            try {
                iArr[ClockViewType.FULL_MINUTE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.WEEK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.FULL_DATE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$module$ClockViewType[ClockViewType.DATE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public EasternArtDMinuteClock(Context context) {
        super(context);
    }

    public EasternArtDMinuteClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public EasternArtDMinuteClock(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // com.miui.clock.eastern.d.EasternArtDBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.eastern.d.EasternArtDBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.eastern.d.EasternArtDBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientParams() {
        return super.getGradientParams();
    }

    @Override // com.miui.clock.eastern.d.EasternArtDBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$module$ClockViewType[clockViewType.ordinal()];
        if (i == 1) {
            return this.timeMinute;
        }
        if (i == 2) {
            return this.timeWeek;
        }
        if (i != 3) {
            return i != 4 ? super.getIClockView(clockViewType) : this.timeDateLunar;
        }
        return this.timeDate;
    }

    @Override // com.miui.clock.eastern.d.EasternArtDBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getLocalCity() {
        return super.getLocalCity();
    }

    @Override // com.miui.clock.eastern.d.EasternArtDBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getMagazineColor() {
        return super.getMagazineColor();
    }

    @Override // com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockTop() {
        return getDimen(R.dimen.miui_eastern_art_d_time_week_margin_top);
    }

    @Override // com.miui.clock.eastern.d.EasternArtDBase, com.miui.clock.MiuiBaseClock2
    public View getNotificationClockTopView() {
        return this.timeWeek;
    }

    @Override // com.miui.clock.eastern.d.EasternArtDBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.eastern.d.EasternArtDBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.eastern.d.EasternArtDBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.timeMinute = (ImageView) findViewById(R.id.time_minute);
        this.timeWeek = (ImageView) findViewById(R.id.time_week);
        this.timeDate = (TextView) findViewById(R.id.time_date);
        this.timeDateLunar = (TextView) findViewById(R.id.time_date_lunar);
    }

    @Override // com.miui.clock.eastern.d.EasternArtDBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.eastern.d.EasternArtDBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.eastern.d.EasternArtDBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.eastern.d.EasternArtDBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.eastern.d.EasternArtDBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.eastern.d.EasternArtDBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.eastern.d.EasternArtDBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.eastern.d.EasternArtDBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.eastern.d.EasternArtDBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.eastern.d.EasternArtDBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    @Override // com.miui.clock.eastern.d.EasternArtDBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setLocalCity(String str) {
        super.setLocalCity(str);
    }

    @Override // com.miui.clock.eastern.d.EasternArtDBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.eastern.d.EasternArtDBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    @Override // com.miui.clock.eastern.d.EasternArtDBase, com.miui.clock.MiuiGalleryBaseClock, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    @Override // com.miui.clock.eastern.d.EasternArtDBase, com.miui.clock.MiuiBaseClock2, com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        int i = this.mCalendar.get(20);
        int i2 = this.mCalendar.get(14);
        Log.e("lzr", "minute: " + i);
        Log.e("lzr", "weekDay: " + i2);
        Drawable drawable = getResources().getDrawable(EasternArtDClockStyle.mTimeMinuteResource[i]);
        drawable.setTint(this.mClockInfo.getPrimaryColor());
        this.timeMinute.setImageDrawable(drawable);
        Drawable drawable2 = getResources().getDrawable(EasternArtDClockStyle.mWeekResource_ZH[i2]);
        drawable2.setTint(this.mClockInfo.getPrimaryColor());
        this.timeWeek.setImageDrawable(drawable2);
        this.timeDate.setText(this.mCalendar.format(getContext(), getResources().getString(R.string.miui_eastern_art_b_data_view_format)));
        this.timeDateLunar.setText(ClassicClockTimeUtils.getLunarCalendarString(getContext(), this.mCalendar));
    }

    @Override // com.miui.clock.MiuiBaseClock2
    public void updateViewsLayoutParams() {
        super.updateViewsLayoutParams();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.timeMinute.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(R.dimen.miui_eastern_art_d_time_minute_width);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(R.dimen.miui_eastern_art_d_time_minute_height);
        ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin = getDimen(R.dimen.miui_eastern_art_d_time_minute_margin_start);
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.miui_eastern_art_d_time_minute_margin_top);
        this.timeMinute.setLayoutParams(layoutParams);
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.timeWeek.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = getDimen(R.dimen.miui_eastern_art_d_time_week_width);
        ((ViewGroup.MarginLayoutParams) layoutParams2).height = getDimen(R.dimen.miui_eastern_art_d_time_week_height);
        ((ViewGroup.MarginLayoutParams) layoutParams2).leftMargin = getDimen(R.dimen.miui_eastern_art_d_time_week_margin_start);
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimen(R.dimen.miui_eastern_art_d_time_week_margin_top);
        this.timeWeek.setLayoutParams(layoutParams2);
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.timeDate.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams3).leftMargin = getDimen(R.dimen.miui_eastern_art_d_time_date_margin_start);
        ((ViewGroup.MarginLayoutParams) layoutParams3).topMargin = getDimen(R.dimen.miui_eastern_art_d_time_date_margin_top);
        this.timeDate.setLayoutParams(layoutParams3);
        this.timeDate.setTextSize(0, getDimen(R.dimen.miui_eastern_art_d_time_date_text_size));
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) this.timeDateLunar.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams4).leftMargin = getDimen(R.dimen.miui_eastern_art_d_time_date_lunar_margin_start);
        ((ViewGroup.MarginLayoutParams) layoutParams4).topMargin = getDimen(R.dimen.miui_eastern_art_d_time_date_lunar_margin_top);
        this.timeDateLunar.setLayoutParams(layoutParams4);
        this.timeDateLunar.setTextSize(0, getDimen(R.dimen.miui_eastern_art_d_time_date_lunar_text_size));
    }
}
