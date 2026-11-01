package com.miui.clock.tiny.bubble;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import com.miui.clock.tiny.R;
import com.miui.clock.tiny.TinyMiuiClockController;
import com.miui.clock.tiny.model.TinyClockInfo;
import com.miui.clock.tiny.model.TinyClockViewType;
import com.miui.clock.tiny.model.TinyMiuiBaseClock;
import com.miui.clock.tiny.utils.FontUtils;
import com.miui.clock.tiny.widget.BatteryView;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class BubbleAodClockView extends TinyMiuiBaseClock implements TinyMiuiClockController.IClockView {
    public static final int DEFAULT_AOD_PRIMARY_COLOR = Color.parseColor("#EBEAEC");
    public BatteryView mBatteryView;
    public ViewGroup mClockContainer;
    public BubbleAodClockInfo mClockInfo;
    public TextView mDateWeek;
    public ViewGroup mDateWeekContainer;
    public ImageView mHour1;
    public ImageView mHour2;
    public ViewGroup mHourMinuteContainer;
    public ImageView mMinute1;
    public ImageView mMinute2;

    /* JADX INFO: renamed from: com.miui.clock.tiny.bubble.BubbleAodClockView$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType;

        static {
            int[] iArr = new int[TinyClockViewType.values().length];
            $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType = iArr;
            try {
                iArr[TinyClockViewType.TIME.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.DATE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.BATTERY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.THIS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public BubbleAodClockView(Context context) {
        this(context, null);
    }

    public BubbleAodClockView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BubbleAodClockView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    private void updateLayoutParams() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mClockContainer.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mHourMinuteContainer.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mHour1.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams4 = (ConstraintLayout.LayoutParams) this.mHour2.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams5 = (ConstraintLayout.LayoutParams) this.mMinute1.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams6 = (ConstraintLayout.LayoutParams) this.mMinute2.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams7 = (ConstraintLayout.LayoutParams) this.mDateWeekContainer.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams8 = (ConstraintLayout.LayoutParams) this.mBatteryView.getLayoutParams();
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(R.dimen.tiny_bubble_aod_clock_container_layout_width);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(R.dimen.tiny_bubble_aod_clock_container_layout_height);
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.tiny_bubble_aod_clock_container_margin_top);
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = getDimen(R.dimen.tiny_bubble_aod_hour_minute_container_layout_width);
        ((ViewGroup.MarginLayoutParams) layoutParams2).height = getDimen(R.dimen.tiny_bubble_aod_hour_minute_container_layout_height);
        ((ViewGroup.MarginLayoutParams) layoutParams7).height = getDimen(R.dimen.tiny_bubble_aod_date_week_container_layout_height);
        ((ViewGroup.MarginLayoutParams) layoutParams7).topMargin = getDimen(R.dimen.tiny_bubble_aod_date_week_container_margin_top);
        int i = R.dimen.tiny_bubble_aod_hour_minute_width;
        ((ViewGroup.MarginLayoutParams) layoutParams3).width = getDimen(i);
        int i2 = R.dimen.tiny_bubble_aod_hour_minute_height;
        ((ViewGroup.MarginLayoutParams) layoutParams3).height = getDimen(i2);
        ((ViewGroup.MarginLayoutParams) layoutParams3).leftMargin = getDimen(R.dimen.tiny_bubble_aod_hour1_margin_start);
        ((ViewGroup.MarginLayoutParams) layoutParams4).width = getDimen(i);
        ((ViewGroup.MarginLayoutParams) layoutParams4).height = getDimen(i2);
        ((ViewGroup.MarginLayoutParams) layoutParams4).rightMargin = getDimen(R.dimen.tiny_bubble_aod_hour2_margin_end);
        ((ViewGroup.MarginLayoutParams) layoutParams5).width = getDimen(i);
        ((ViewGroup.MarginLayoutParams) layoutParams5).height = getDimen(i2);
        ((ViewGroup.MarginLayoutParams) layoutParams5).leftMargin = getDimen(R.dimen.tiny_bubble_aod_minute1_margin_start);
        ((ViewGroup.MarginLayoutParams) layoutParams6).width = getDimen(i);
        ((ViewGroup.MarginLayoutParams) layoutParams6).height = getDimen(i2);
        ((ViewGroup.MarginLayoutParams) layoutParams8).height = getDimen(R.dimen.tiny_bubble_aod_battery_layout_height);
        this.mClockContainer.setLayoutParams(layoutParams);
        this.mHourMinuteContainer.setLayoutParams(layoutParams2);
        this.mHour1.setLayoutParams(layoutParams3);
        this.mHour2.setLayoutParams(layoutParams4);
        this.mMinute1.setLayoutParams(layoutParams5);
        this.mMinute2.setLayoutParams(layoutParams6);
        this.mDateWeek.setTextSize(0, getDimen(R.dimen.tiny_bubble_aod_date_week_text_size));
        this.mBatteryView.setLayoutParams(layoutParams8);
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public Calendar getClockCalendar() {
        return this.mCalendar;
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public TinyClockInfo getClockStyleInfo() {
        return this.mClockInfo;
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public View getIClockView(TinyClockViewType tinyClockViewType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[tinyClockViewType.ordinal()];
        if (i == 1) {
            return this.mHourMinuteContainer;
        }
        if (i != 2) {
            return i != 3 ? this : this.mBatteryView;
        }
        return this.mDateWeek;
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public int getMarginAOD() {
        return 0;
    }

    public void init(Context context) {
        this.mContext = context;
        this.mCalendar = new Calendar();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mClockContainer = (ViewGroup) findViewById(R.id.bubble_clock_container);
        this.mHourMinuteContainer = (ViewGroup) findViewById(R.id.bubble_hour_minute_container);
        this.mHour1 = (ImageView) findViewById(R.id.bubble_hour1);
        this.mHour2 = (ImageView) findViewById(R.id.bubble_hour2);
        this.mMinute1 = (ImageView) findViewById(R.id.bubble_minute1);
        this.mMinute2 = (ImageView) findViewById(R.id.bubble_minute2);
        this.mDateWeekContainer = (ViewGroup) findViewById(R.id.bubble_date_week_container);
        this.mDateWeek = (TextView) findViewById(R.id.bubble_date_week);
        this.mBatteryView = (BatteryView) findViewById(R.id.bubble_battery);
        this.mDateWeek.setTypeface(FontUtils.getMiSans(380));
        this.mDateWeek.setTextColor(Color.parseColor("#EBEAEC"));
        this.mBatteryView.setBatteryColor(Color.parseColor("#EBEAEC"));
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void setBatteryColor(int i) {
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void setBatteryLevel(int i) {
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void setClockStyleInfo(TinyClockInfo tinyClockInfo) {
        if (tinyClockInfo instanceof BubbleAodClockInfo) {
            this.mClockInfo = (BubbleAodClockInfo) tinyClockInfo;
        }
        if (this.mClockInfo == null) {
            return;
        }
        this.mDateWeek.setTextColor(FontBubble.getBubbleAodColor()[this.mClockInfo.getClockStyle()]);
        this.mBatteryView.setBatteryColor(FontBubble.getBubbleAodColor()[this.mClockInfo.getClockStyle()]);
        updateLayoutParams();
        this.mClockInfo.initClockDigtalResources();
        updateTime();
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        int[] iArr = this.mCurrentTimeArray;
        int i = iArr[0];
        int i2 = iArr[1];
        int i3 = iArr[2];
        int i4 = iArr[3];
        Drawable drawable = ContextCompat.getDrawable(this.mContext, this.mClockInfo.getAodResource()[i]);
        Drawable drawable2 = ContextCompat.getDrawable(this.mContext, this.mClockInfo.getAodResource()[i2]);
        Drawable drawable3 = ContextCompat.getDrawable(this.mContext, this.mClockInfo.getAodResource()[i3]);
        Drawable drawable4 = ContextCompat.getDrawable(this.mContext, this.mClockInfo.getAodResource()[i4]);
        drawable.setTint(this.mClockInfo.getAODColor());
        drawable2.setTint(this.mClockInfo.getAODColor());
        drawable3.setTint(this.mClockInfo.getAODColor());
        drawable4.setTint(this.mClockInfo.getAODColor());
        this.mHour1.setImageDrawable(drawable);
        this.mHour2.setImageDrawable(drawable2);
        this.mMinute1.setImageDrawable(drawable3);
        this.mMinute2.setImageDrawable(drawable4);
        this.mHourMinuteContainer.setContentDescription(DateUtils.formatDateTime(this.mContext, System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
        TextView textView = this.mDateWeek;
        Calendar calendar = this.mCalendar;
        Context context = this.mContext;
        textView.setText(calendar.format(context, context.getString(R.string.tiny_bubble_date_week_format)));
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void updateTimeZone(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mCalendar.setTimeZone(TimeZone.getTimeZone(str));
    }
}
