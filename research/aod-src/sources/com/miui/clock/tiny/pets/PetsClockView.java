package com.miui.clock.tiny.pets;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.content.res.ResourcesCompat;
import com.miui.aod.category.TextStyleCategoryInfo;
import com.miui.clock.tiny.R;
import com.miui.clock.tiny.TinyMiuiClockController;
import com.miui.clock.tiny.model.TinyClockInfo;
import com.miui.clock.tiny.model.TinyClockViewType;
import com.miui.clock.tiny.utils.DateFormatUtils;
import com.miui.clock.tiny.utils.FontUtils;
import com.miui.clock.tiny.widget.BatteryView;
import java.util.Locale;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class PetsClockView extends FrameLayout implements TinyMiuiClockController.IClockView {
    public boolean m24HourFormat;
    public BatteryView mBatteryView;
    public Calendar mCalendar;
    public PetsClockInfo mClockInfo;
    public View mColon1;
    public View mColon2;
    public ViewGroup mColonContainer;
    public Context mContext;
    public Locale mCurrentLocale;
    public int[] mCurrentTimeArray;
    public TextView mDateText;
    public TextView mHour1;
    public TextView mHour2;
    public TextView mMinute1;
    public TextView mMinute2;
    public float mRadio;
    public ViewGroup mTimeContainer;
    public TextView mWeekText;

    /* JADX INFO: renamed from: com.miui.clock.tiny.pets.PetsClockView$1, reason: invalid class name */
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
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.COLON.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.BATTERY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.THIS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public PetsClockView(Context context) {
        this(context, null);
    }

    public PetsClockView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PetsClockView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRadio = 1.0f;
        this.mCurrentTimeArray = new int[4];
        init(context);
    }

    private float getDimen(int i) {
        return this.mContext.getResources().getDimensionPixelSize(i) * this.mRadio;
    }

    private void initLayoutParams() {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.mTimeContainer.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mHour1.getLayoutParams();
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mHour2.getLayoutParams();
        LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) this.mMinute1.getLayoutParams();
        LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) this.mMinute2.getLayoutParams();
        LinearLayout.LayoutParams layoutParams6 = (LinearLayout.LayoutParams) this.mColonContainer.getLayoutParams();
        LinearLayout.LayoutParams layoutParams7 = (LinearLayout.LayoutParams) this.mColon1.getLayoutParams();
        LinearLayout.LayoutParams layoutParams8 = (LinearLayout.LayoutParams) this.mColon2.getLayoutParams();
        LinearLayout.LayoutParams layoutParams9 = (LinearLayout.LayoutParams) this.mDateText.getLayoutParams();
        LinearLayout.LayoutParams layoutParams10 = (LinearLayout.LayoutParams) this.mWeekText.getLayoutParams();
        LinearLayout.LayoutParams layoutParams11 = (LinearLayout.LayoutParams) this.mBatteryView.getLayoutParams();
        int rotation = this.mClockInfo.getRotation();
        boolean zIsTibetan = FontUtils.isTibetan(this.mContext);
        float f = zIsTibetan ? 0.75f : 1.0f;
        if (1 != this.mClockInfo.getType()) {
            float f2 = f;
            if (rotation == 2) {
                layoutParams.topMargin = (int) getDimen(R.dimen.tiny_pets_180_time_container_margin_top);
                layoutParams2.setMarginStart((int) getDimen(R.dimen.tiny_pets_180_hour1_margin_start));
                layoutParams3.setMarginStart((int) getDimen(R.dimen.tiny_pets_180_hour2_margin_start));
                layoutParams4.setMarginStart((int) getDimen(R.dimen.tiny_pets_180_minute1_margin_start));
                layoutParams5.setMarginStart((int) getDimen(R.dimen.tiny_pets_180_minute2_margin_start));
                layoutParams5.setMarginEnd((int) getDimen(R.dimen.tiny_pets_180_minute2_margin_end));
                TextView textView = this.mHour1;
                int i = R.dimen.tiny_pets_180_time_text_size;
                textView.setTextSize(0, getDimen(i));
                this.mHour2.setTextSize(0, getDimen(i));
                this.mMinute1.setTextSize(0, getDimen(i));
                this.mMinute2.setTextSize(0, getDimen(i));
                int i2 = R.dimen.tiny_pets_180_colon_size;
                layoutParams6.width = (int) getDimen(i2);
                layoutParams6.setMarginStart((int) getDimen(R.dimen.tiny_pets_180_colon_container_margin_start));
                layoutParams6.topMargin = (int) getDimen(R.dimen.tiny_pets_180_colon_container_margin_top);
                layoutParams7.width = (int) getDimen(i2);
                layoutParams7.height = (int) getDimen(i2);
                layoutParams8.width = (int) getDimen(i2);
                layoutParams8.height = (int) getDimen(i2);
                layoutParams8.topMargin = (int) getDimen(R.dimen.tiny_pets_180_colon_margin_top);
                layoutParams9.setMarginEnd((int) getDimen(R.dimen.tiny_pets_180_date_margin_end));
                if (zIsTibetan) {
                    layoutParams9.topMargin = (int) getDimen(R.dimen.tiny_pets_180_date_margin_top_tibetan);
                } else {
                    layoutParams9.topMargin = (int) getDimen(R.dimen.tiny_pets_180_date_margin_top);
                }
                TextView textView2 = this.mDateText;
                int i3 = R.dimen.tiny_pets_180_date_text_size;
                textView2.setTextSize(0, getDimen(i3));
                layoutParams10.setMarginEnd((int) getDimen(R.dimen.tiny_pets_180_week_margin_end));
                if (zIsTibetan) {
                    layoutParams10.topMargin = (int) getDimen(R.dimen.tiny_pets_week_margin_top_tibetan);
                    layoutParams11.topMargin = (int) getDimen(R.dimen.tiny_pets_battery_margin_top_tibetan);
                } else {
                    layoutParams10.topMargin = (int) getDimen(R.dimen.tiny_pets_180_week_margin_top);
                    layoutParams11.topMargin = (int) getDimen(R.dimen.tiny_pets_180_battery_margin_top);
                }
                this.mWeekText.setTextSize(0, f2 * getDimen(i3));
                layoutParams11.setMarginEnd((int) getDimen(R.dimen.tiny_pets_180_battery_margin_end));
            } else {
                layoutParams.setMarginEnd((int) getDimen(R.dimen.tiny_pets_time_container_margin_end));
                layoutParams.topMargin = (int) getDimen(R.dimen.tiny_pets_time_container_margin_top);
                layoutParams2.setMarginStart((int) getDimen(R.dimen.tiny_pets_hour1_margin_start));
                layoutParams3.setMarginStart((int) getDimen(R.dimen.tiny_pets_hour2_margin_start));
                layoutParams4.setMarginStart((int) getDimen(R.dimen.tiny_pets_minute1_margin_start));
                layoutParams5.setMarginStart((int) getDimen(R.dimen.tiny_pets_minute2_margin_start));
                layoutParams5.setMarginEnd((int) getDimen(R.dimen.tiny_pets_minute2_margin_end));
                TextView textView3 = this.mHour1;
                int i4 = R.dimen.tiny_pets_time_text_size;
                textView3.setTextSize(0, getDimen(i4));
                this.mHour2.setTextSize(0, getDimen(i4));
                this.mMinute1.setTextSize(0, getDimen(i4));
                this.mMinute2.setTextSize(0, getDimen(i4));
                int i5 = R.dimen.tiny_pets_colon_size;
                layoutParams6.width = (int) getDimen(i5);
                layoutParams6.setMarginStart((int) getDimen(R.dimen.tiny_pets_colon_container_margin_start));
                layoutParams6.topMargin = (int) getDimen(R.dimen.tiny_pets_colon_container_margin_top);
                layoutParams7.width = (int) getDimen(i5);
                layoutParams7.height = (int) getDimen(i5);
                layoutParams8.width = (int) getDimen(i5);
                layoutParams8.height = (int) getDimen(i5);
                layoutParams8.topMargin = (int) getDimen(R.dimen.tiny_pets_colon_margin_top);
                layoutParams9.setMarginEnd((int) getDimen(R.dimen.tiny_pets_date_margin_end));
                if (zIsTibetan) {
                    layoutParams9.topMargin = (int) getDimen(R.dimen.tiny_pets_date_margin_top_tibetan);
                } else {
                    layoutParams9.topMargin = (int) getDimen(R.dimen.tiny_pets_date_margin_top);
                }
                TextView textView4 = this.mDateText;
                int i6 = R.dimen.tiny_pets_date_text_size;
                textView4.setTextSize(0, getDimen(i6));
                layoutParams10.setMarginEnd((int) getDimen(R.dimen.tiny_pets_week_margin_end));
                if (zIsTibetan) {
                    layoutParams10.topMargin = (int) getDimen(R.dimen.tiny_pets_week_margin_top_tibetan);
                    layoutParams11.topMargin = (int) getDimen(R.dimen.tiny_pets_battery_margin_top_tibetan);
                } else {
                    layoutParams10.topMargin = (int) getDimen(R.dimen.tiny_pets_week_margin_top);
                    layoutParams11.topMargin = (int) getDimen(R.dimen.tiny_pets_battery_margin_top);
                }
                this.mWeekText.setTextSize(0, f2 * getDimen(i6));
                layoutParams11.setMarginEnd((int) getDimen(R.dimen.tiny_pets_battery_margin_end));
            }
        } else if (rotation == 2) {
            layoutParams.topMargin = (int) getDimen(R.dimen.tiny_pets_time_container_margin_top);
            layoutParams2.setMarginStart((int) getDimen(R.dimen.tiny_pets_hour1_margin_start));
            layoutParams3.setMarginStart((int) getDimen(R.dimen.tiny_pets_hour2_margin_start));
            layoutParams4.setMarginStart((int) getDimen(R.dimen.tiny_pets_minute1_margin_start));
            layoutParams5.setMarginStart((int) getDimen(R.dimen.tiny_pets_minute2_margin_start));
            layoutParams5.setMarginEnd((int) getDimen(R.dimen.tiny_pets_minute2_margin_end));
            TextView textView5 = this.mHour1;
            int i7 = R.dimen.tiny_pets_time_text_size;
            float f3 = f;
            textView5.setTextSize(0, getDimen(i7));
            this.mHour2.setTextSize(0, getDimen(i7));
            this.mMinute1.setTextSize(0, getDimen(i7));
            this.mMinute2.setTextSize(0, getDimen(i7));
            int i8 = R.dimen.tiny_pets_colon_size;
            layoutParams6.width = (int) getDimen(i8);
            layoutParams6.setMarginStart((int) getDimen(R.dimen.tiny_pets_colon_container_margin_start));
            layoutParams6.topMargin = (int) getDimen(R.dimen.tiny_pets_colon_container_margin_top);
            layoutParams7.width = (int) getDimen(i8);
            layoutParams7.height = (int) getDimen(i8);
            layoutParams8.width = (int) getDimen(i8);
            layoutParams8.height = (int) getDimen(i8);
            layoutParams8.topMargin = (int) getDimen(R.dimen.tiny_pets_colon_margin_top);
            layoutParams9.setMarginStart((int) getDimen(R.dimen.tiny_pets_aod_date_margin_start));
            if (zIsTibetan) {
                layoutParams9.topMargin = (int) getDimen(R.dimen.tiny_pets_date_margin_top_tibetan);
            } else {
                layoutParams9.topMargin = (int) getDimen(R.dimen.tiny_pets_date_margin_top);
            }
            TextView textView6 = this.mDateText;
            int i9 = R.dimen.tiny_pets_date_text_size;
            textView6.setTextSize(0, getDimen(i9));
            layoutParams10.setMarginStart((int) getDimen(R.dimen.tiny_pets_aod_week_margin_start));
            if (zIsTibetan) {
                layoutParams10.topMargin = (int) getDimen(R.dimen.tiny_pets_week_margin_top_tibetan);
                layoutParams11.topMargin = (int) getDimen(R.dimen.tiny_pets_battery_margin_top_tibetan);
            } else {
                layoutParams10.topMargin = (int) getDimen(R.dimen.tiny_pets_week_margin_top);
                layoutParams11.topMargin = (int) getDimen(R.dimen.tiny_pets_battery_margin_top);
            }
            this.mWeekText.setTextSize(0, f3 * getDimen(i9));
            layoutParams11.setMarginStart((int) getDimen(R.dimen.tiny_pets_aod_battert_margin_start));
        } else {
            float f4 = f;
            layoutParams.setMarginEnd((int) getDimen(R.dimen.tiny_pets_time_container_margin_end));
            layoutParams.topMargin = (int) getDimen(R.dimen.tiny_pets_time_container_margin_top);
            layoutParams2.setMarginStart((int) getDimen(R.dimen.tiny_pets_hour1_margin_start));
            layoutParams3.setMarginStart((int) getDimen(R.dimen.tiny_pets_hour2_margin_start));
            layoutParams4.setMarginStart((int) getDimen(R.dimen.tiny_pets_minute1_margin_start));
            layoutParams5.setMarginStart((int) getDimen(R.dimen.tiny_pets_minute2_margin_start));
            layoutParams5.setMarginEnd((int) getDimen(R.dimen.tiny_pets_minute2_margin_end));
            TextView textView7 = this.mHour1;
            int i10 = R.dimen.tiny_pets_time_text_size;
            textView7.setTextSize(0, getDimen(i10));
            this.mHour2.setTextSize(0, getDimen(i10));
            this.mMinute1.setTextSize(0, getDimen(i10));
            this.mMinute2.setTextSize(0, getDimen(i10));
            int i11 = R.dimen.tiny_pets_colon_size;
            layoutParams6.width = (int) getDimen(i11);
            layoutParams6.setMarginStart((int) getDimen(R.dimen.tiny_pets_colon_container_margin_start));
            layoutParams6.topMargin = (int) getDimen(R.dimen.tiny_pets_colon_container_margin_top);
            layoutParams7.width = (int) getDimen(i11);
            layoutParams7.height = (int) getDimen(i11);
            layoutParams8.width = (int) getDimen(i11);
            layoutParams8.height = (int) getDimen(i11);
            layoutParams8.topMargin = (int) getDimen(R.dimen.tiny_pets_colon_margin_top);
            layoutParams9.setMarginStart((int) getDimen(R.dimen.tiny_pets_aod_date_margin_start));
            if (zIsTibetan) {
                layoutParams9.topMargin = (int) getDimen(R.dimen.tiny_pets_date_margin_top_tibetan);
            } else {
                layoutParams9.topMargin = (int) getDimen(R.dimen.tiny_pets_date_margin_top);
            }
            TextView textView8 = this.mDateText;
            int i12 = R.dimen.tiny_pets_date_text_size;
            textView8.setTextSize(0, getDimen(i12));
            layoutParams10.setMarginStart((int) getDimen(R.dimen.tiny_pets_aod_week_margin_start));
            if (zIsTibetan) {
                layoutParams10.topMargin = (int) getDimen(R.dimen.tiny_pets_week_margin_top_tibetan);
                layoutParams11.topMargin = (int) getDimen(R.dimen.tiny_pets_battery_margin_top_tibetan);
            } else {
                layoutParams10.topMargin = (int) getDimen(R.dimen.tiny_pets_week_margin_top);
                layoutParams11.topMargin = (int) getDimen(R.dimen.tiny_pets_battery_margin_top);
            }
            this.mWeekText.setTextSize(0, f4 * getDimen(i12));
            layoutParams11.setMarginStart((int) getDimen(R.dimen.tiny_pets_aod_battert_margin_start));
        }
        this.mHour1.setLayoutParams(layoutParams2);
        this.mHour2.setLayoutParams(layoutParams3);
        this.mMinute1.setLayoutParams(layoutParams4);
        this.mMinute2.setLayoutParams(layoutParams5);
        this.mColon1.setLayoutParams(layoutParams7);
        this.mColon2.setLayoutParams(layoutParams8);
        this.mColonContainer.setLayoutParams(layoutParams6);
        this.mDateText.setLayoutParams(layoutParams9);
        this.mWeekText.setLayoutParams(layoutParams10);
        this.mBatteryView.setLayoutParams(layoutParams11);
        this.mTimeContainer.setLayoutParams(layoutParams);
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public Calendar getClockCalendar() {
        return this.mCalendar;
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public PetsClockInfo getClockStyleInfo() {
        return this.mClockInfo;
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public View getIClockView(TinyClockViewType tinyClockViewType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[tinyClockViewType.ordinal()];
        if (i == 1) {
            return this.mDateText;
        }
        if (i == 2) {
            return this.mTimeContainer;
        }
        if (i == 3) {
            return this.mWeekText;
        }
        if (i != 4) {
            return i != 5 ? this : this.mBatteryView;
        }
        return this.mColonContainer;
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public int getMarginAOD() {
        return this.mClockInfo.getRotation() == 2 ? this.mContext.getResources().getDimensionPixelSize(R.dimen.tiny_pets_180_aod_notification_margin_start) : this.mContext.getResources().getDimensionPixelSize(R.dimen.tiny_pets_aod_notification_margin_start);
    }

    public void init(Context context) {
        this.mContext = context;
        this.mCalendar = new Calendar();
        this.mCurrentLocale = Locale.getDefault();
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        Locale locale = Locale.getDefault();
        if (this.mCurrentLocale.equals(locale)) {
            return;
        }
        this.mCurrentLocale = locale;
        Typeface miSans = FontUtils.getMiSans(TextStyleCategoryInfo.MAX_FONT_WEIGHT);
        this.mDateText.setTypeface(miSans);
        this.mWeekText.setTypeface(miSans);
        initLayoutParams();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mTimeContainer = (ViewGroup) findViewById(R.id.time_container);
        this.mColonContainer = (ViewGroup) findViewById(R.id.colon_container);
        this.mHour1 = (TextView) findViewById(R.id.hour1);
        this.mHour2 = (TextView) findViewById(R.id.hour2);
        this.mMinute1 = (TextView) findViewById(R.id.minute1);
        this.mMinute2 = (TextView) findViewById(R.id.minute2);
        this.mColon1 = findViewById(R.id.colon_1);
        this.mColon2 = findViewById(R.id.colon_2);
        this.mDateText = (TextView) findViewById(R.id.tv_date);
        this.mWeekText = (TextView) findViewById(R.id.tv_week);
        this.mBatteryView = (BatteryView) findViewById(R.id.battery_view);
        Typeface font = ResourcesCompat.getFont(this.mContext, R.font.c700_regular);
        this.mHour1.setTypeface(font);
        this.mHour2.setTypeface(font);
        this.mMinute1.setTypeface(font);
        this.mMinute2.setTypeface(font);
        Typeface miSans = FontUtils.getMiSans(TextStyleCategoryInfo.MAX_FONT_WEIGHT);
        this.mDateText.setTypeface(miSans);
        this.mWeekText.setTypeface(miSans);
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
        if (tinyClockInfo instanceof PetsClockInfo) {
            this.mClockInfo = (PetsClockInfo) tinyClockInfo;
        }
        PetsClockInfo petsClockInfo = this.mClockInfo;
        if (petsClockInfo == null) {
            return;
        }
        if (petsClockInfo.getType() == 2 || this.mClockInfo.getType() == 3 || this.mClockInfo.getType() == 4) {
            this.mBatteryView.setNeedShowNormal(true);
            if (tinyClockInfo.getScale() <= 0.0f || Math.abs(this.mClockInfo.getScale() - 1.0f) <= 0.001f) {
                setScaleRadio(1.0f);
            } else {
                setScaleRadio(tinyClockInfo.getScale());
            }
        }
        Log.d("TinyMiuiClockView", " setClockInfo: " + tinyClockInfo);
        this.mHour1.setTextColor(this.mClockInfo.getTimeColor());
        this.mHour2.setTextColor(this.mClockInfo.getTimeColor());
        this.mMinute1.setTextColor(this.mClockInfo.getTimeColor());
        this.mMinute2.setTextColor(this.mClockInfo.getTimeColor());
        this.mColon1.getBackground().setTint(this.mClockInfo.getColonColor());
        this.mColon2.getBackground().setTint(this.mClockInfo.getColonColor());
        this.mDateText.setTextColor(this.mClockInfo.getInfoColor());
        this.mWeekText.setTextColor(this.mClockInfo.getInfoColor());
        setBatteryColor(this.mClockInfo.getInfoColor());
        initLayoutParams();
        updateTime();
    }

    public void setScaleRadio(float f) {
        this.mRadio = f;
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void updateTime() {
        this.m24HourFormat = DateFormatUtils.is24HourFormat(this.mContext);
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        TextView textView = this.mDateText;
        Calendar calendar = this.mCalendar;
        Context context = this.mContext;
        textView.setText(calendar.format(context, context.getString(R.string.tiny_pets_date_format)));
        TextView textView2 = this.mWeekText;
        Calendar calendar2 = this.mCalendar;
        Context context2 = this.mContext;
        textView2.setText(calendar2.format(context2, context2.getString(R.string.tiny_pets_week_format)));
        String time = DateFormatUtils.formatTime(this.m24HourFormat ? "HHmm" : "hhmm");
        for (int i = 0; i < time.length(); i++) {
            this.mCurrentTimeArray[i] = Integer.parseInt(String.valueOf(time.charAt(i)));
        }
        this.mHour1.setText(String.valueOf(this.mCurrentTimeArray[0]));
        this.mHour2.setText(String.valueOf(this.mCurrentTimeArray[1]));
        this.mMinute1.setText(String.valueOf(this.mCurrentTimeArray[2]));
        this.mMinute2.setText(String.valueOf(this.mCurrentTimeArray[3]));
        this.mTimeContainer.setContentDescription(DateUtils.formatDateTime(this.mContext, System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76));
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void updateTimeZone(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mCalendar.setTimeZone(TimeZone.getTimeZone(str));
    }
}
