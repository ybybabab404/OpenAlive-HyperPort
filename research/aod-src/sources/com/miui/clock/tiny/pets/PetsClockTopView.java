package com.miui.clock.tiny.pets;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
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

/* JADX INFO: loaded from: classes.dex */
public class PetsClockTopView extends LinearLayout implements TinyMiuiClockController.IClockView {
    public boolean m24HourFormat;
    public BatteryView mBatteryView;
    public Calendar mCalendar;
    public PetsClockTopInfo mClockInfo;
    public Context mContext;
    public Locale mCurrentLocale;
    public TextView mDateText;
    public float mRadio;
    public TextView mWeekText;

    /* JADX INFO: renamed from: com.miui.clock.tiny.pets.PetsClockTopView$1, reason: invalid class name */
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
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.WEEK.ordinal()] = 2;
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

    public PetsClockTopView(Context context) {
        this(context, null);
    }

    public PetsClockTopView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PetsClockTopView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mRadio = 1.0f;
        init(context);
    }

    private float getDimen(int i) {
        return this.mContext.getResources().getDimensionPixelSize(i) * this.mRadio;
    }

    private void initLayoutParams() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mDateText.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mWeekText.getLayoutParams();
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mBatteryView.getLayoutParams();
        int rotation = this.mClockInfo.getRotation();
        this.mBatteryView.setScale(this.mClockInfo.getScale());
        boolean zIsTibetan = FontUtils.isTibetan(this.mContext);
        float f = zIsTibetan ? 0.75f : 1.0f;
        if (rotation == 2) {
            layoutParams.setMarginEnd((int) getDimen(R.dimen.tiny_pets_180_date_margin_end));
            if (zIsTibetan) {
                layoutParams.topMargin = (int) getDimen(R.dimen.tiny_pets_180_date_margin_top_tibetan);
            } else {
                layoutParams.topMargin = (int) getDimen(R.dimen.tiny_pets_180_date_margin_top);
            }
            TextView textView = this.mDateText;
            int i = R.dimen.tiny_pets_180_date_text_size;
            textView.setTextSize(0, getDimen(i));
            layoutParams2.setMarginEnd((int) getDimen(R.dimen.tiny_pets_180_week_margin_end));
            if (zIsTibetan) {
                layoutParams2.topMargin = (int) getDimen(R.dimen.tiny_pets_week_margin_top_tibetan);
                layoutParams3.topMargin = (int) getDimen(R.dimen.tiny_pets_battery_margin_top_tibetan);
            } else {
                layoutParams2.topMargin = (int) getDimen(R.dimen.tiny_pets_180_week_margin_top);
                layoutParams3.topMargin = (int) getDimen(R.dimen.tiny_pets_battery_margin_top);
            }
            this.mWeekText.setTextSize(0, f * getDimen(i));
            layoutParams3.setMarginEnd((int) getDimen(R.dimen.tiny_pets_180_battery_margin_end));
        } else {
            layoutParams.setMarginEnd((int) getDimen(R.dimen.tiny_pets_date_margin_end));
            if (zIsTibetan) {
                layoutParams.topMargin = (int) getDimen(R.dimen.tiny_pets_date_margin_top_tibetan);
            } else {
                layoutParams.topMargin = (int) getDimen(R.dimen.tiny_pets_date_margin_top);
            }
            TextView textView2 = this.mDateText;
            int i2 = R.dimen.tiny_pets_date_text_size;
            textView2.setTextSize(0, getDimen(i2));
            layoutParams2.setMarginEnd((int) getDimen(R.dimen.tiny_pets_week_margin_end));
            if (zIsTibetan) {
                layoutParams2.topMargin = (int) getDimen(R.dimen.tiny_pets_week_margin_top_tibetan);
                layoutParams3.topMargin = (int) getDimen(R.dimen.tiny_pets_battery_margin_top_tibetan);
            } else {
                layoutParams2.topMargin = (int) getDimen(R.dimen.tiny_pets_week_margin_top);
                layoutParams3.topMargin = (int) getDimen(R.dimen.tiny_pets_battery_margin_top);
            }
            this.mWeekText.setTextSize(0, f * getDimen(i2));
            layoutParams3.setMarginEnd((int) getDimen(R.dimen.tiny_pets_battery_margin_end));
        }
        this.mDateText.setLayoutParams(layoutParams);
        this.mWeekText.setLayoutParams(layoutParams2);
        this.mBatteryView.setLayoutParams(layoutParams3);
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public Calendar getClockCalendar() {
        return this.mCalendar;
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public PetsClockTopInfo getClockStyleInfo() {
        return this.mClockInfo;
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public View getIClockView(TinyClockViewType tinyClockViewType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[tinyClockViewType.ordinal()];
        if (i == 1) {
            return this.mDateText;
        }
        if (i != 2) {
            return i != 3 ? this : this.mBatteryView;
        }
        return this.mWeekText;
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
        updateTime();
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mDateText = (TextView) findViewById(R.id.tv_date_top);
        this.mWeekText = (TextView) findViewById(R.id.tv_week_top);
        this.mBatteryView = (BatteryView) findViewById(R.id.battery_view_top);
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
        if (tinyClockInfo instanceof PetsClockTopInfo) {
            this.mClockInfo = (PetsClockTopInfo) tinyClockInfo;
        }
        PetsClockTopInfo petsClockTopInfo = this.mClockInfo;
        if (petsClockTopInfo == null) {
            return;
        }
        if (petsClockTopInfo.getType() == 2 || this.mClockInfo.getType() == 3 || this.mClockInfo.getType() == 4) {
            this.mBatteryView.setNeedShowNormal(true);
            if (tinyClockInfo.getScale() <= 0.0f || Math.abs(this.mClockInfo.getScale() - 1.0f) <= 0.001f) {
                setScaleRadio(1.0f);
            } else {
                setScaleRadio(tinyClockInfo.getScale());
            }
        }
        if (1 == this.mClockInfo.getType()) {
            return;
        }
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
        this.m24HourFormat = DateFormatUtils.is24HourFormat(getContext());
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        TextView textView = this.mDateText;
        Calendar calendar = this.mCalendar;
        Context context = this.mContext;
        textView.setText(calendar.format(context, context.getString(R.string.tiny_pets_date_format)));
        TextView textView2 = this.mWeekText;
        Calendar calendar2 = this.mCalendar;
        Context context2 = this.mContext;
        textView2.setText(calendar2.format(context2, context2.getString(R.string.tiny_pets_week_format)));
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void updateTimeZone(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mCalendar.setTimeZone(TimeZone.getTimeZone(str));
    }
}
