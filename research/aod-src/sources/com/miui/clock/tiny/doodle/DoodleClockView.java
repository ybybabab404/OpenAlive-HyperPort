package com.miui.clock.tiny.doodle;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import com.miui.clock.tiny.R;
import com.miui.clock.tiny.model.TinyClockInfo;
import com.miui.clock.tiny.model.TinyClockViewType;
import com.miui.clock.tiny.model.TinyMiuiBaseClock;
import com.miui.clock.tiny.widget.BatteryView;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class DoodleClockView extends TinyMiuiBaseClock {
    public ConstraintLayout batteryLayout;
    public DoodleClockBaseView clockView;
    public ImageView mAodLeftDoodleView;
    public ImageView mAodRightDoodleView;
    public BatteryView mBatteryView;
    public DoodleClockInfo mClockInfo;

    /* JADX INFO: renamed from: com.miui.clock.tiny.doodle.DoodleClockView$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType;

        static {
            int[] iArr = new int[TinyClockViewType.values().length];
            $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType = iArr;
            try {
                iArr[TinyClockViewType.DATE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.TIME.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.WEEK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.BATTERY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.THIS.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    public DoodleClockView(Context context) {
        this(context, null);
    }

    public DoodleClockView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public DoodleClockView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private float getAodScaleMultiple() {
        return getDimen(R.dimen.tiny_doodle_base_view_aod_width) / getDimen(R.dimen.tiny_doodle_base_view_width);
    }

    private float getDesignScale() {
        return this.mClockInfo.getDesignWidthDp() / 372.0f;
    }

    private void initBatteryView() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.batteryLayout.getLayoutParams();
        layoutParams.leftToLeft = 0;
        layoutParams.topToTop = 0;
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(R.dimen.tiny_doodle_battery_width);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(R.dimen.tiny_doodle_battery_height);
        layoutParams.setMargins(getDimen(R.dimen.tiny_doodle_aod_battery_padding_left) + getDimen(R.dimen.tiny_doodle_aod_battery_margin_left), getDimen(R.dimen.tiny_doodle_aod_battery_padding_top) + getDimen(R.dimen.tiny_doodle_aod_battery_margin_top), 0, 0);
        this.batteryLayout.setLayoutParams(layoutParams);
        this.batteryLayout.setVisibility(this.mClockInfo.isAODType() ? 0 : 8);
    }

    private void initClockView(int i) {
        boolean z = i != 2;
        boolean zIsAODType = this.mClockInfo.isAODType();
        ((Guideline) this.clockView.findViewById(R.id.guideline)).setGuidelinePercent(0.5f);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.clockView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(R.dimen.tiny_doodle_base_view_width);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(R.dimen.tiny_doodle_base_view_height);
        layoutParams.leftToLeft = 0;
        layoutParams.topToTop = 0;
        layoutParams.setMarginStart(getDimen(z ? R.dimen.tiny_doodle_rotation_0_margin_left : R.dimen.tiny_doodle_rotation_180_margin_left));
        if (zIsAODType) {
            layoutParams.setMarginStart(getDimen(R.dimen.tiny_doodle_aod_clock_margin_left));
        }
        this.clockView.setLayoutParams(layoutParams);
        if (zIsAODType) {
            this.clockView.setPivotX(0.0f);
            this.clockView.setPivotY(0.0f);
            this.clockView.setScaleX(getAodScaleMultiple());
            this.clockView.setScaleY(getAodScaleMultiple());
            return;
        }
        this.clockView.setPivotX(((ViewGroup.MarginLayoutParams) layoutParams).width / 2.0f);
        this.clockView.setPivotY(((ViewGroup.MarginLayoutParams) layoutParams).height / 2.0f);
        this.clockView.setScaleX(1.0f);
        this.clockView.setScaleY(1.0f);
    }

    private void initColor() {
        int clockStyle = this.mClockInfo.getClockStyle();
        DoodleStyle.DoodleClockViewColorStyle[] doodleClockViewColorStyleArr = DoodleStyle.colorStyles;
        DoodleStyle.DoodleClockViewColorStyle doodleClockViewColorStyle = doodleClockViewColorStyleArr[clockStyle % doodleClockViewColorStyleArr.length];
        this.mClockInfo.setLeftDoodleColor(doodleClockViewColorStyle.getLeftDoodleColor());
        this.mClockInfo.setRightDoodleColor(doodleClockViewColorStyle.getRightDoodleColor());
        if (this.mClockInfo.isAODType()) {
            this.mBatteryView.setBatteryColor(-1);
        }
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public Calendar getClockCalendar() {
        return this.clockView.getClockCalendar();
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public TinyClockInfo getClockStyleInfo() {
        return this.mClockInfo;
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock
    public int getDimen(int i) {
        return (int) (((double) (getResources().getDimensionPixelSize(i) * this.mClockInfo.getScale())) + 0.5d);
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public View getIClockView(TinyClockViewType tinyClockViewType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[tinyClockViewType.ordinal()];
        if (i == 1 || i == 2 || i == 3) {
            return this.clockView.getIClockView(tinyClockViewType);
        }
        return i != 4 ? this : this.mBatteryView;
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public int getMarginAOD() {
        return 0;
    }

    public final void initAodDoodle() {
        boolean zIsAODType = this.mClockInfo.isAODType();
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mAodLeftDoodleView.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mAodRightDoodleView.getLayoutParams();
        layoutParams.topToTop = 0;
        layoutParams.rightToLeft = this.mAodRightDoodleView.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(zIsAODType ? R.dimen.tiny_doodle_aod_left_doodle_width : R.dimen.tiny_doodle_left_doodle_width);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(zIsAODType ? R.dimen.tiny_doodle_aod_left_doodle_height : R.dimen.tiny_doodle_left_doodle_height);
        layoutParams.setMargins(0, getDimen(zIsAODType ? R.dimen.tiny_doodle_aod_left_doodle_margin_top : R.dimen.tiny_doodle_left_doodle_margin_top), getDimen(zIsAODType ? R.dimen.tiny_doodle_aod_left_doodle_margin_right : R.dimen.tiny_doodle_left_doodle_margin_right), 0);
        layoutParams2.topToTop = 0;
        layoutParams2.leftToLeft = this.clockView.getId();
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = getDimen(zIsAODType ? R.dimen.tiny_doodle_aod_right_doodle_width : R.dimen.tiny_doodle_right_doodle_width);
        ((ViewGroup.MarginLayoutParams) layoutParams2).height = getDimen(zIsAODType ? R.dimen.tiny_doodle_aod_right_doodle_height : R.dimen.tiny_doodle_right_doodle_height);
        layoutParams2.setMargins(getDimen(zIsAODType ? R.dimen.tiny_doodle_aod_right_doodle_margin_left : R.dimen.tiny_doodle_right_doodle_margin_left), getDimen(zIsAODType ? R.dimen.tiny_doodle_aod_right_doodle_margin_top : R.dimen.tiny_doodle_right_doodle_margin_top), 0, 0);
        this.mAodLeftDoodleView.setLayoutParams(layoutParams);
        this.mAodRightDoodleView.setLayoutParams(layoutParams2);
        Drawable drawable = getResources().getDrawable(DoodleStyle.mAodLeftDoodleResource);
        drawable.setTint(this.mClockInfo.getLeftDoodleColor());
        this.mAodLeftDoodleView.setImageDrawable(drawable);
        Drawable drawable2 = getResources().getDrawable(DoodleStyle.mAodRightDoodleResource);
        drawable2.setTint(this.mClockInfo.getRightDoodleColor());
        this.mAodRightDoodleView.setImageDrawable(drawable2);
    }

    public void initDoodleView(int i) {
        boolean z = i != 2;
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (!this.mClockInfo.isAODType()) {
            setPadding(0, getDimen(z ? R.dimen.tiny_doodle_rotation_0_padding_top : R.dimen.tiny_doodle_rotation_180_padding_top), 0, 0);
            layoutParams.height = -1;
            layoutParams.width = -1;
        } else {
            int i2 = R.dimen.tiny_doodle_aod_padding_top;
            setPadding(0, getDimen(i2), 0, 0);
            layoutParams.height = getDimen(R.dimen.tiny_doodle_clock_aod_height) + getDimen(i2);
            layoutParams.width = getDimen(R.dimen.tiny_doodle_clock_aod_width);
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.clockView = (DoodleClockBaseView) findViewById(R.id.content_tiny_doodle);
        this.mBatteryView = (BatteryView) findViewById(R.id.battery_doodle);
        this.batteryLayout = (ConstraintLayout) findViewById(R.id.battery_doodle_layout);
        this.mAodLeftDoodleView = (ImageView) findViewById(R.id.aod_left_doodle);
        this.mAodRightDoodleView = (ImageView) findViewById(R.id.aod_right_doodle);
    }

    public void setAodDoodleVisibility(int i) {
        this.mAodLeftDoodleView.setVisibility(i);
        this.mAodRightDoodleView.setVisibility(i);
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void setBatteryColor(int i) {
        this.mBatteryView.setBatteryColor(i);
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void setBatteryLevel(int i) {
        this.mBatteryView.setBatteryLevel(i);
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void setClockStyleInfo(TinyClockInfo tinyClockInfo) {
        this.mClockInfo = (DoodleClockInfo) tinyClockInfo;
        Log.d("TinyMiuiClockView.DoodleClockView", "doodle clock change info " + this.mClockInfo.toString());
        this.clockView.setClockStyleInfo(this.mClockInfo);
        initColor();
        initClockView(this.mClockInfo.getRotation());
        initAodDoodle();
        initBatteryView();
        initDoodleView(this.mClockInfo.getRotation());
        updateAodDoodleVisibility();
    }

    public void setDesignWidthDp(float f) {
        this.mClockInfo.setDesignWidthDp(f);
        setClockStyleInfo(this.mClockInfo);
    }

    public void updateAodDoodleVisibility() {
        if (!this.mClockInfo.isDoAnimation() || this.mClockInfo.isAODType()) {
            setAodDoodleVisibility(this.mClockInfo.isAODType() ? 0 : 8);
        } else {
            setAodDoodleVisibility(0);
            this.mClockInfo.setDoAnimation(false);
        }
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void updateTime() {
        this.clockView.updateTime();
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void updateTimeZone(String str) {
        this.clockView.updateTimeZone(str);
    }
}
