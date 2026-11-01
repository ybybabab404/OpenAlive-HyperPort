package com.miui.clock.tiny.splicing;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.miui.clock.tiny.R;
import com.miui.clock.tiny.TinyMiuiClockController;
import com.miui.clock.tiny.model.TinyClockInfo;
import com.miui.clock.tiny.model.TinyClockViewType;
import com.miui.clock.tiny.utils.DateFormatUtils;
import com.miui.clock.tiny.widget.BatteryView;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class SplicingClockView extends RelativeLayout implements TinyMiuiClockController.IClockView {
    public final String STRING_TIME_DOT;
    public boolean m24HourFormat;
    public BatteryView mBatteryView;
    public Calendar mCalendar;
    public SplicingClockInfo mClockInfo;
    public Context mContext;
    public TextView mDateText;
    public float mScale;
    public TextView mTimeText;
    public TextView mWeekText;

    /* JADX INFO: renamed from: com.miui.clock.tiny.splicing.SplicingClockView$1, reason: invalid class name */
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

    public SplicingClockView(Context context) {
        this(context, null);
    }

    public SplicingClockView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SplicingClockView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.STRING_TIME_DOT = ":";
        init(context);
    }

    private float getDimen(int i) {
        return this.mContext.getResources().getDimensionPixelSize(i) * this.mClockInfo.getScale();
    }

    private void initLayoutParams() {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) this.mDateText.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams2 = (RelativeLayout.LayoutParams) this.mTimeText.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams3 = (RelativeLayout.LayoutParams) this.mWeekText.getLayoutParams();
        RelativeLayout.LayoutParams layoutParams4 = (RelativeLayout.LayoutParams) this.mBatteryView.getLayoutParams();
        int rotation = this.mClockInfo.getRotation();
        this.mBatteryView.setScale(this.mClockInfo.getScale());
        if (this.mClockInfo.isAODType()) {
            if (rotation == 2) {
                int i = R.dimen.splicing_margin_start_aod_180;
                layoutParams.setMarginStart((int) getDimen(i));
                layoutParams.topMargin = (int) getDimen(R.dimen.splicing_date_text_margin_top_aod_180);
                this.mDateText.setTextSize(0, getDimen(R.dimen.splicing_date_text_text_size));
                layoutParams2.topMargin = (int) getDimen(R.dimen.splicing_time_text_margin_top_aod_180);
                layoutParams2.setMarginStart((int) getDimen(i));
                this.mTimeText.setTextSize(0, (int) getDimen(R.dimen.splicing_time_text_text_size_180));
                layoutParams3.topMargin = (int) getDimen(R.dimen.splicing_week_text_margin_top_aod_180);
                layoutParams3.setMarginStart((int) getDimen(i));
                this.mWeekText.setTextSize(0, getDimen(R.dimen.splicing_week_text_text_size_aod));
                layoutParams4.setMarginStart((int) getDimen(i));
                layoutParams4.topMargin = (int) getDimen(R.dimen.splicing_battery_text_margin_top_aod_180);
                return;
            }
            int i2 = R.dimen.splicing_margin_start_aod_0;
            layoutParams.setMarginStart((int) getDimen(i2));
            layoutParams.topMargin = (int) getDimen(R.dimen.splicing_date_text_margin_top_aod_180);
            this.mDateText.setTextSize(0, getDimen(R.dimen.splicing_date_text_text_size));
            layoutParams2.topMargin = (int) getDimen(R.dimen.splicing_time_text_margin_top_aod_0);
            layoutParams2.setMarginStart((int) getDimen(i2));
            this.mTimeText.setTextSize(0, (int) getDimen(R.dimen.splicing_time_text_text_size_0));
            layoutParams3.topMargin = (int) getDimen(R.dimen.splicing_week_text_margin_top_aod_0);
            layoutParams3.setMarginStart((int) getDimen(i2));
            this.mWeekText.setTextSize(0, getDimen(R.dimen.splicing_week_text_text_size_aod));
            layoutParams4.setMarginStart((int) getDimen(i2));
            layoutParams4.topMargin = (int) getDimen(R.dimen.splicing_battery_text_margin_top_aod_0);
            return;
        }
        if (rotation != 2) {
            int i3 = R.dimen.splicing_date_text_margin_start_0;
            layoutParams.setMarginStart((int) getDimen(i3));
            layoutParams.topMargin = (int) getDimen(R.dimen.splicing_date_text_margin_top_0);
            this.mDateText.setTextSize(0, getDimen(R.dimen.splicing_date_text_text_size));
            layoutParams2.topMargin = (int) getDimen(R.dimen.splicing_time_text_margin_top_0);
            layoutParams2.setMarginStart((int) getDimen(i3));
            this.mTimeText.setTextSize(0, (int) getDimen(R.dimen.splicing_time_text_text_size_0));
            layoutParams3.width = (int) getDimen(R.dimen.splicing_week_text_bg_weight_0);
            layoutParams3.height = (int) getDimen(R.dimen.splicing_week_text_bg_height_0);
            layoutParams3.topMargin = (int) getDimen(R.dimen.splicing_week_text_margin_top_0);
            layoutParams3.setMarginEnd((int) getDimen(R.dimen.splicing_week_text_margin_end_0));
            this.mWeekText.setPadding((int) getDimen(R.dimen.splicing_week_text_padding_start), 0, (int) getDimen(R.dimen.splicing_week_text_padding_end), 0);
            this.mWeekText.setTextSize(0, getDimen(R.dimen.splicing_week_text_text_size));
            layoutParams4.setMarginEnd((int) getDimen(R.dimen.splicing_battery_text_margin_end_0));
            layoutParams4.topMargin = (int) getDimen(R.dimen.splicing_battery_text_margin_top_0);
            return;
        }
        layoutParams.setMarginEnd((int) getDimen(R.dimen.splicing_date_text_margin_end_180));
        layoutParams.topMargin = (int) getDimen(R.dimen.splicing_date_text_margin_top_180);
        TextView textView = this.mDateText;
        int i4 = R.dimen.splicing_time_text_text_size_180;
        textView.setTextSize(0, getDimen(i4));
        layoutParams2.topMargin = (int) getDimen(R.dimen.splicing_time_text_margin_top_180);
        layoutParams2.setMarginEnd((int) getDimen(R.dimen.splicing_time_text_margin_end_180));
        this.mTimeText.setTextSize(0, (int) getDimen(i4));
        layoutParams3.width = (int) getDimen(R.dimen.splicing_week_text_bg_weight_180);
        layoutParams3.height = (int) getDimen(R.dimen.splicing_week_text_bg_height_180);
        layoutParams3.topMargin = (int) getDimen(R.dimen.splicing_week_text_margin_top_180);
        layoutParams3.setMarginEnd((int) getDimen(R.dimen.splicing_week_text_margin_end_180));
        this.mWeekText.setPadding((int) getDimen(R.dimen.splicing_week_text_padding_start), 0, (int) getDimen(R.dimen.splicing_week_text_padding_end), 0);
        this.mWeekText.setTextSize(0, getDimen(R.dimen.splicing_week_text_text_size));
        layoutParams4.setMarginEnd((int) getDimen(R.dimen.splicing_battery_text_margin_end_180));
        layoutParams4.topMargin = (int) getDimen(R.dimen.splicing_battery_text_margin_top_180);
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public Calendar getClockCalendar() {
        return this.mCalendar;
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public SplicingClockInfo getClockStyleInfo() {
        return this.mClockInfo;
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public View getIClockView(TinyClockViewType tinyClockViewType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[tinyClockViewType.ordinal()];
        if (i == 1) {
            return this.mDateText;
        }
        if (i == 2) {
            return this.mTimeText;
        }
        if (i != 3) {
            return i != 4 ? this : this.mBatteryView;
        }
        return this.mWeekText;
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public int getMarginAOD() {
        return this.mClockInfo.getRotation() == 2 ? this.mContext.getResources().getDimensionPixelSize(R.dimen.splicing_margin_start_aod_180) : this.mContext.getResources().getDimensionPixelSize(R.dimen.splicing_margin_start_aod_0);
    }

    public void init(Context context) {
        this.mContext = context;
        this.mCalendar = new Calendar();
    }

    public final void initDataMarquee() {
        this.mWeekText.setSingleLine();
        this.mWeekText.setHorizontallyScrolling(true);
        this.mWeekText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        this.mWeekText.setMarqueeRepeatLimit(-1);
        this.mWeekText.setFocusable(true);
        this.mWeekText.setFocusableInTouchMode(true);
        this.mWeekText.setSelected(true);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mDateText = (TextView) findViewById(R.id.tv_date);
        this.mTimeText = (TextView) findViewById(R.id.tv_time);
        this.mWeekText = (TextView) findViewById(R.id.tv_week);
        this.mBatteryView = (BatteryView) findViewById(R.id.battery_splicing);
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void setBatteryColor(int i) {
        this.mBatteryView.setBatteryColor(i);
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void setBatteryLevel(int i) {
        this.mBatteryView.setBatteryLevel(i);
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void setClockStyleInfo(TinyClockInfo tinyClockInfo) {
        if (tinyClockInfo instanceof SplicingClockInfo) {
            this.mClockInfo = (SplicingClockInfo) tinyClockInfo;
        }
        SplicingClockInfo splicingClockInfo = this.mClockInfo;
        if (splicingClockInfo == null) {
            return;
        }
        if (splicingClockInfo.getType() == 2 || this.mClockInfo.getType() == 3 || this.mClockInfo.getType() == 4) {
            this.mBatteryView.setNeedShowNormal(true);
            if (this.mClockInfo.getScale() <= 0.0f || Math.abs(this.mClockInfo.getScale() - 1.0f) <= 0.001f) {
                this.mClockInfo.setScale(1.0f);
            } else {
                setScale(this.mClockInfo.getScale());
                initLayoutParams();
            }
        }
        if (this.mClockInfo.getType() != 1) {
            this.mWeekText.setTextColor(-1);
            this.mDateText.setTextColor(this.mClockInfo.getPrimaryColor());
            this.mTimeText.setTextColor(this.mClockInfo.getPrimaryColor());
            setBatteryColor(this.mClockInfo.getPrimaryColor());
        } else {
            this.mWeekText.setTextColor(this.mClockInfo.getAODColor());
            this.mDateText.setTextColor(this.mClockInfo.getAODColor());
            this.mTimeText.setTextColor(this.mClockInfo.getAODColor());
            setBatteryColor(this.mClockInfo.getAODColor());
        }
        updateTime();
        if (shouldStartWeekMarquee()) {
            initDataMarquee();
            Log.d("TinyMiuiClockView", "has init week information marquee already!");
        }
    }

    public void setScale(float f) {
        this.mScale = f;
    }

    public final boolean shouldStartWeekMarquee() {
        float dimen;
        float dimen2;
        if (this.mClockInfo.getType() != 2 && this.mClockInfo.getType() != 4) {
            String string = this.mWeekText.getText().toString();
            float fMeasureText = this.mWeekText.getPaint().measureText(string, 0, string.length());
            if (getRotation() == 0.0f) {
                dimen = getDimen(R.dimen.splicing_week_background_width_0) - getDimen(R.dimen.splicing_week_text_padding_start);
                dimen2 = getDimen(R.dimen.splicing_week_text_padding_end);
            } else {
                dimen = getDimen(R.dimen.splicing_week_background_width_180) - getDimen(R.dimen.splicing_week_text_padding_start);
                dimen2 = getDimen(R.dimen.splicing_week_text_padding_end);
            }
            if (fMeasureText > dimen - dimen2) {
                return true;
            }
        }
        return false;
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void updateTime() {
        this.m24HourFormat = DateFormatUtils.is24HourFormat(this.mContext);
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        TextView textView = this.mDateText;
        Calendar calendar = this.mCalendar;
        Context context = this.mContext;
        textView.setText(calendar.format(context, context.getString(R.string.tiny_splicing_date_format)));
        TextView textView2 = this.mDateText;
        Calendar calendar2 = this.mCalendar;
        Context context2 = this.mContext;
        textView2.setContentDescription(calendar2.format(context2, context2.getString(R.string.tiny_splicing_date_content_description)));
        this.mTimeText.setText(String.format("%s%s%s", DateFormatUtils.getTimeString(DateFormatUtils.getHourInt(this.m24HourFormat, this.mCalendar), true), ":", DateFormatUtils.getTimeString(DateFormatUtils.getMinInt(this.mCalendar), true)));
        TextView textView3 = this.mTimeText;
        Calendar calendar3 = this.mCalendar;
        Context context3 = this.mContext;
        textView3.setContentDescription(calendar3.format(context3, context3.getString(this.m24HourFormat ? R.string.tiny_clock_time_format_24 : R.string.tiny_clock_time_format_12)));
        TextView textView4 = this.mWeekText;
        Calendar calendar4 = this.mCalendar;
        Context context4 = this.mContext;
        textView4.setText(calendar4.format(context4, context4.getString(R.string.tiny_splicing_week_format)));
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void updateTimeZone(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mCalendar.setTimeZone(TimeZone.getTimeZone(str));
    }
}
