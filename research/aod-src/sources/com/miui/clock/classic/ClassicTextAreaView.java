package com.miui.clock.classic;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.ColorInt;
import androidx.core.content.ContextCompat;
import com.miui.clock.R;
import com.miui.clock.module.ClassicContentStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.HealthBean;
import com.miui.clock.module.MiuiClassicClockInfo;
import com.miui.clock.module.MiuiClassicClockInfoBase;
import com.miui.clock.module.WeatherBean;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.ColorUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontUtils;
import com.miui.clock.utils.MutiDeviceDimenAdapter;
import com.xiaomi.onetrack.g.d;
import com.xiaomi.onetrack.util.ac;
import java.text.NumberFormat;
import java.util.Locale;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"AppCompatCustomView"})
public class ClassicTextAreaView extends TextView {
    public String dateForeground;
    public boolean isPreview;
    public Calendar mCalendar;
    public MiuiClassicClockInfoBase mClockInfo;
    public Context mContext;
    public int mCurrentLineType;
    public boolean mHasHealthIcon;
    public boolean mHasWeatherIcon;
    public HealthBean mHealthBean;
    public Drawable mHealthIcon;
    public float mIconWH;
    public boolean mNeedIcon;
    public String mPercentSignPlaceholderText;
    public String mPlaceholderText;
    public Drawable mPointIcon;
    public float mPointIconHeight;
    public float mPointIconWidth;
    public Paint mRectPaint;
    public final Runnable mRequestLayoutRunnable;
    public String mTextSlash;
    public WeatherBean mWeatherBean;
    public Drawable mWeatherIcon;
    public float mWeatherIconMargin;
    public float mWeatherIconWH;
    public boolean needDrawRect;
    public int rectHeight;
    public int rectPadding;
    public int rectRadius;
    public int rectWidth;

    public ClassicTextAreaView(Context context) {
        super(context);
        this.dateForeground = "";
        this.isPreview = false;
        this.mRequestLayoutRunnable = new Runnable() { // from class: com.miui.clock.classic.ClassicTextAreaView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.requestLayout();
            }
        };
        init(context);
    }

    public ClassicTextAreaView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.dateForeground = "";
        this.isPreview = false;
        this.mRequestLayoutRunnable = new Runnable() { // from class: com.miui.clock.classic.ClassicTextAreaView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.requestLayout();
            }
        };
        init(context);
    }

    public ClassicTextAreaView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.dateForeground = "";
        this.isPreview = false;
        this.mRequestLayoutRunnable = new Runnable() { // from class: com.miui.clock.classic.ClassicTextAreaView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.requestLayout();
            }
        };
        init(context);
    }

    private void setupWeatherIcon(WeatherBean weatherBean) {
        if (weatherBean == null) {
            loadWeatherIcon(R.drawable.classic_placeholder_icon, this.mRectPaint.getColor());
            return;
        }
        try {
            int i = (this.mCalendar.get(18) * 60) + this.mCalendar.get(20);
            boolean z = true;
            boolean z2 = i < weatherBean.getSunriseMinuteTime() || i > weatherBean.getSunsetMinuteTime();
            if (this.mClockInfo == null || !DeviceConfig.supportBackgroundBlur(this.mContext) || !this.mClockInfo.isAutoSecondaryColor() || !this.mClockInfo.isAutoPrimaryColor() || ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType())) {
                z = false;
            }
            int iconResId = weatherBean.getIconResId(z2, z);
            if (iconResId != 0) {
                loadWeatherIcon(iconResId, this.mClockInfo.getPrimaryColor());
            } else {
                loadWeatherIcon(R.drawable.classic_placeholder_icon, this.mRectPaint.getColor());
            }
        } catch (Exception e) {
            Log.e("ClassicTextAreaView", "load weather icon error", e);
            this.mWeatherIcon = null;
        }
    }

    public MiuiClassicClockInfoBase getClockInfo() {
        return this.mClockInfo;
    }

    public int getDimen(int i) {
        return (int) (getResources().getDimensionPixelSize(i) * DeviceConfig.calculateScale(getContext()));
    }

    public float getDimenFloat(int i) {
        return getResources().getDimension(i) * DeviceConfig.calculateScale(getContext());
    }

    public final SpannableString getHeatherSpannableString(int i, int i2, String str, String str2) {
        int primaryColor;
        String quantityString = getResources().getQuantityString(i, i2, str, str2);
        int iIndexOf = quantityString.indexOf(this.mTextSlash);
        int length = quantityString.length();
        SpannableString spannableString = new SpannableString(quantityString);
        if (iIndexOf < 0) {
            return spannableString;
        }
        if (ClockEffectUtils.isGlowType(this.mClockInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
            primaryColor = this.mClockInfo.getPrimaryColor();
        } else {
            primaryColor = (DeviceConfig.supportBackgroundBlur(this.mContext) && this.mClockInfo.isAutoPrimaryColor()) ? this.mClockInfo.getPrimaryColor() : ColorUtils.setAlphaComponent(this.mClockInfo.getPrimaryColor(), 153);
        }
        spannableString.setSpan(new ForegroundColorSpan(primaryColor), iIndexOf, length, 17);
        return spannableString;
    }

    public float getTextAreaTextSize() {
        MiuiClassicClockInfoBase miuiClassicClockInfoBase = this.mClockInfo;
        return (miuiClassicClockInfoBase == null || "classic".equals(miuiClassicClockInfoBase.getTemplateId())) ? getDimenFloat(R.dimen.classic_text_area_text_size) : getDimenFloat(R.dimen.classic_plus_classic_max_text_area_text_size);
    }

    public int getViewHeight() {
        if (this.mClockInfo == null) {
            return getLineHeight();
        }
        return ((float) DeviceConfig.getScreenRect(getContext()).width()) < (((float) (getPaddingStart() + getPaddingEnd())) + getPaint().measureText(getText().toString())) + (((float) MutiDeviceDimenAdapter.getMultiDeviceDimen(getContext(), R.dimen.classic_text_area_margin_start_end, true)) * 2.0f) ? getLineHeight() * 2 : getLineHeight();
    }

    public final boolean hasCaloriesData() {
        HealthBean healthBean = this.mHealthBean;
        return healthBean != null && healthBean.hasCaloriesData();
    }

    public final boolean hasSportTimeData() {
        HealthBean healthBean = this.mHealthBean;
        return healthBean != null && healthBean.hasSportTimeData();
    }

    public final boolean hasStandCountData() {
        HealthBean healthBean = this.mHealthBean;
        return healthBean != null && healthBean.hasStandCountData();
    }

    public final boolean hasStepCountData() {
        HealthBean healthBean = this.mHealthBean;
        return healthBean != null && healthBean.hasStepCountData();
    }

    public final void init(Context context) {
        this.mContext = context;
        setMaxLines(1);
        setEllipsize(TextUtils.TruncateAt.END);
        setTextDirection(2);
        setTypeface(FontUtils.getMiSans(380));
        setFontFeatureSettings("ss01");
        Paint paint = new Paint();
        this.mRectPaint = paint;
        paint.setColor(ColorUtils.setAlphaComponent(getPaint().getColor(), 153));
        this.mRectPaint.setStyle(Paint.Style.FILL);
        this.mTextSlash = getResources().getString(R.string.text_slash);
        this.mPlaceholderText = getResources().getString(R.string.no_data_placeholder_string);
        this.mPercentSignPlaceholderText = getResources().getString(R.string.no_data_placeholder_string_with_percent_sign);
    }

    public void initData(int i, int i2) {
        this.isPreview = true;
        MiuiClassicClockInfo miuiClassicClockInfo = new MiuiClassicClockInfo();
        miuiClassicClockInfo.setPrimaryColor(i);
        initData(miuiClassicClockInfo, i2);
    }

    public void initData(MiuiClassicClockInfoBase miuiClassicClockInfoBase, int i) {
        this.mClockInfo = miuiClassicClockInfoBase;
        this.mCurrentLineType = i;
        String str = "";
        if (i == 0) {
            setText("");
            this.mNeedIcon = false;
            this.mPointIcon = null;
            this.mWeatherIcon = null;
            this.mHealthIcon = null;
            this.needDrawRect = false;
            this.dateForeground = "";
            setPadding(0, 0, 0, 0);
            updateIconFlags();
            requestLayout();
            invalidate();
            Log.d("ClassicTextAreaView", "classic line1 is None, return!");
            return;
        }
        boolean zIsHealthType = ClassicContentStyle.Health.isHealthType(i);
        boolean zIsWeatherType = ClassicContentStyle.Weather.isWeatherType(i);
        this.mNeedIcon = zIsHealthType;
        if (zIsHealthType) {
            Drawable drawable = ContextCompat.getDrawable(this.mContext, HealthBean.getHealthIconByType(i));
            this.mHealthIcon = drawable;
            if (drawable != null) {
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), this.mHealthIcon.getIntrinsicHeight());
            }
            this.mPointIcon = null;
            this.mWeatherIcon = null;
        } else {
            this.mHealthIcon = null;
            if (zIsWeatherType && i == 400) {
                Drawable drawable2 = ContextCompat.getDrawable(this.mContext, R.drawable.classic_placeholder_icon);
                this.mWeatherIcon = drawable2;
                if (drawable2 != null) {
                    drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), this.mWeatherIcon.getIntrinsicHeight());
                }
                this.mPointIcon = null;
            } else {
                Drawable drawable3 = ContextCompat.getDrawable(this.mContext, R.drawable.classic_point_icon);
                this.mPointIcon = drawable3;
                if (drawable3 != null) {
                    drawable3.setBounds(0, 0, drawable3.getIntrinsicWidth(), this.mPointIcon.getIntrinsicHeight());
                }
                this.mWeatherIcon = null;
            }
        }
        if (ClassicContentStyle.isNeedPointIcon(this.mCurrentLineType)) {
            Calendar calendar = this.mCalendar;
            Context context = this.mContext;
            str = calendar.format(context, context.getString(R.string.format_week_day_style2));
        }
        this.dateForeground = str;
        int dateColor = miuiClassicClockInfoBase.getDateColor();
        if (dateColor != 0) {
            setDateTextColor(dateColor);
            if (ClockEffectUtils.isGlowType(this.mClockInfo.getClockEffect()) && DeviceConfig.supportAdvanceVisualEffect(this.mContext)) {
                this.mRectPaint.setColor(ColorUtils.setAlphaComponent(dateColor, 255));
            } else if (DeviceConfig.supportBackgroundBlur(this.mContext) && this.mClockInfo.isAutoPrimaryColor() && !ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType())) {
                this.mRectPaint.setColor(Color.parseColor("#FF999999"));
            } else {
                this.mRectPaint.setColor(ColorUtils.setAlphaComponent(dateColor, 153));
            }
            Drawable drawable4 = this.mHealthIcon;
            if (drawable4 != null) {
                drawable4.setTint(dateColor);
            }
            Drawable drawable5 = this.mWeatherIcon;
            if (drawable5 != null) {
                drawable5.setTint(this.mRectPaint.getColor());
            }
            Drawable drawable6 = this.mPointIcon;
            if (drawable6 != null) {
                drawable6.setTint(dateColor);
            }
        }
        if (zIsWeatherType) {
            updateWeather(this.mWeatherBean);
        } else {
            this.needDrawRect = false;
        }
        if (zIsHealthType) {
            updateHealth(this.mHealthBean);
            setPadding(0, 0, 0, 0);
        }
        if (!zIsWeatherType && !zIsHealthType) {
            setPadding(0, 0, 0, 0);
        }
        initSetting();
        updateIconFlags();
    }

    public final void initPadding() {
        if (!this.needDrawRect) {
            setPadding(0, 0, 0, 0);
        } else if (DeviceConfig.isRTL()) {
            setPadding(this.rectWidth, 0, 0, 0);
        } else {
            setPadding(0, 0, this.rectWidth, 0);
        }
    }

    public final void initSetting() {
        setTextSize(0, getTextAreaTextSize());
        updateHeight();
        this.mIconWH = getDimenFloat(R.dimen.health_icon_18);
        this.mWeatherIconWH = getDimen(R.dimen.weather_icon_w_h);
        this.mPointIconWidth = getDimen(R.dimen.point_icon_width);
        this.mPointIconHeight = getDimen(R.dimen.point_icon_height);
        this.mWeatherIconMargin = getDimen(R.dimen.weather_icon_margin);
        this.rectWidth = getDimen(R.dimen.text_placeholder_width_small);
        this.rectHeight = getDimen(R.dimen.text_placeholder_height_small);
        this.rectPadding = getDimen(R.dimen.text_placeholder_padding);
        this.rectRadius = getDimen(R.dimen.text_placeholder_radius);
    }

    public final void loadWeatherIcon(int i, int i2) {
        Drawable drawable = ContextCompat.getDrawable(this.mContext, i);
        this.mWeatherIcon = drawable;
        if (drawable != null) {
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), this.mWeatherIcon.getIntrinsicHeight());
            if (i2 != 0) {
                this.mWeatherIcon.setTint(i2);
            }
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        Canvas canvas2;
        float f;
        String string = getText().toString();
        TextPaint paint = getPaint();
        int currentTextColor = getCurrentTextColor();
        paint.setColor(currentTextColor);
        syncPlaceholderColor(currentTextColor);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        float fMeasureText = paint.measureText(string);
        float fMeasureText2 = paint.measureText(this.dateForeground);
        float f2 = 0.0f;
        float f3 = (!this.mHasHealthIcon || this.mHealthIcon == null) ? 0.0f : this.mIconWH;
        float f4 = (!this.mHasWeatherIcon || this.mWeatherIcon == null) ? 0.0f : this.mWeatherIconWH;
        float f5 = (this.mNeedIcon || this.mPointIcon == null || !ClassicContentStyle.isNeedPointIcon(this.mCurrentLineType)) ? 0.0f : this.mPointIconWidth;
        if (this.mHasWeatherIcon && this.mWeatherIcon != null) {
            f2 = this.mWeatherIconMargin * 2.0f;
        }
        float f6 = measuredHeight;
        float fDescent = (f6 / 2.0f) - ((paint.descent() + paint.ascent()) / 2.0f);
        float paddingStart = fMeasureText2 + f3 + fMeasureText + f5 + f4 + f2 + getPaddingStart() + getPaddingEnd();
        if (!DeviceConfig.isRTL()) {
            float paddingStart2 = getPaddingStart();
            canvas.drawText(this.dateForeground, paddingStart2, fDescent, paint);
            float f7 = paddingStart2 + fMeasureText2;
            if (!this.mNeedIcon && this.mPointIcon != null && ClassicContentStyle.isNeedPointIcon(this.mCurrentLineType)) {
                canvas.save();
                canvas.translate(f7, (f6 - this.mPointIconHeight) / 2.0f);
                float intrinsicHeight = this.mPointIconHeight / this.mPointIcon.getIntrinsicHeight();
                canvas.scale(intrinsicHeight, intrinsicHeight);
                this.mPointIcon.draw(canvas);
                canvas.restore();
                f7 += f5;
            }
            if (!this.mNeedIcon && this.mPointIcon == null && this.mWeatherIcon != null) {
                canvas.save();
                float f8 = f7 + this.mWeatherIconMargin;
                canvas.translate(f8, (f6 - this.mWeatherIconWH) / 2.0f);
                float intrinsicWidth = this.mWeatherIconWH / this.mWeatherIcon.getIntrinsicWidth();
                canvas.scale(intrinsicWidth, intrinsicWidth);
                this.mWeatherIcon.draw(canvas);
                canvas.restore();
                f7 = f8 + this.mWeatherIconWH + this.mWeatherIconMargin;
            }
            if (this.mNeedIcon && this.mHealthIcon != null) {
                canvas.save();
                canvas.translate(f7, (f6 - this.mIconWH) / 2.0f);
                float intrinsicWidth2 = this.mIconWH / this.mHealthIcon.getIntrinsicWidth();
                canvas.scale(intrinsicWidth2, intrinsicWidth2);
                this.mHealthIcon.draw(canvas);
                canvas.restore();
                f7 += this.mIconWH;
            }
            canvas.drawText(string, f7, fDescent, paint);
            float fMeasureText3 = f7 + paint.measureText(string);
            if (this.needDrawRect) {
                int i = this.rectHeight;
                int i2 = this.rectPadding;
                float f9 = ((measuredHeight - i) + (i2 * 2)) / 2.0f;
                float f10 = this.rectWidth + fMeasureText3;
                float f11 = (i + f9) - (i2 * 2);
                int i3 = this.rectRadius;
                canvas.drawRoundRect(fMeasureText3, f9, f10, f11, i3, i3, this.mRectPaint);
                return;
            }
            return;
        }
        float width = getWidth() - paddingStart;
        if (this.needDrawRect) {
            int i4 = this.rectHeight;
            int i5 = this.rectPadding;
            float f12 = ((measuredHeight - i4) + (i5 * 2)) / 2.0f;
            float f13 = width + i5;
            float f14 = (width + this.rectWidth) - i5;
            float f15 = (i4 + f12) - (i5 * 2);
            int i6 = this.rectRadius;
            f = fDescent;
            canvas.drawRoundRect(f13, f12, f14, f15, i6, i6, this.mRectPaint);
            canvas2 = canvas;
            width += this.rectWidth;
        } else {
            canvas2 = canvas;
            f = fDescent;
        }
        float f16 = width;
        canvas2.drawText(string, f16, f, paint);
        float f17 = f16 + fMeasureText;
        if (this.mNeedIcon && this.mHealthIcon != null) {
            canvas2.save();
            canvas2.translate(f17, (f6 - this.mIconWH) / 2.0f);
            float intrinsicWidth3 = this.mIconWH / this.mHealthIcon.getIntrinsicWidth();
            canvas2.scale(intrinsicWidth3, intrinsicWidth3);
            this.mHealthIcon.draw(canvas2);
            canvas2.restore();
            f17 += f3;
        }
        if (!this.mNeedIcon && this.mPointIcon == null && this.mWeatherIcon != null) {
            canvas2.save();
            float f18 = f17 + this.mWeatherIconMargin;
            canvas2.translate(f18, (f6 - this.mWeatherIconWH) / 2.0f);
            float intrinsicWidth4 = this.mWeatherIconWH / this.mWeatherIcon.getIntrinsicWidth();
            canvas2.scale(intrinsicWidth4, intrinsicWidth4);
            this.mWeatherIcon.draw(canvas2);
            canvas2.restore();
            f17 = f18 + this.mWeatherIconWH + this.mWeatherIconMargin;
        }
        if (!this.mNeedIcon && this.mPointIcon != null && ClassicContentStyle.isNeedPointIcon(this.mCurrentLineType)) {
            canvas2.save();
            canvas2.translate(f17, (f6 - this.mPointIconHeight) / 2.0f);
            float intrinsicHeight2 = this.mPointIconHeight / this.mPointIcon.getIntrinsicHeight();
            canvas2.scale(intrinsicHeight2, intrinsicHeight2);
            this.mPointIcon.draw(canvas2);
            canvas2.restore();
            f17 += f5;
        }
        float paddingEnd = (measuredWidth - getPaddingEnd()) - fMeasureText2;
        if (paddingEnd >= f17) {
            f17 = paddingEnd;
        }
        canvas2.drawText(this.dateForeground, f17, f, paint);
    }

    public void onLanguageChanged() {
        Log.d("ClassicTextAreaView", "ClassicTextAreaView onLanguageChanged");
        setTypeface(FontUtils.getMiSans(380));
        initSetting();
    }

    @Override // android.widget.TextView, android.view.View
    public void onMeasure(int i, int i2) {
        Drawable drawable;
        TextPaint paint = getPaint();
        float fMeasureText = paint.measureText(getText().toString());
        float fMeasureText2 = paint.measureText(this.dateForeground);
        float f = (!this.mHasHealthIcon || this.mHealthIcon == null) ? 0.0f : this.mIconWH;
        float f2 = (!this.mHasWeatherIcon || this.mWeatherIcon == null) ? 0.0f : this.mWeatherIconWH;
        float f3 = (this.mNeedIcon || this.mPointIcon == null || !ClassicContentStyle.isNeedPointIcon(this.mCurrentLineType)) ? 0.0f : this.mPointIconWidth;
        float f4 = (!this.mHasWeatherIcon || this.mWeatherIcon == null) ? 0.0f : this.mWeatherIconMargin * 2.0f;
        Log.d("ClassicTextAreaView", "textWidth=" + fMeasureText + " dateWidth" + fMeasureText2 + " iconWidth=" + f + " pointWidth=" + f3 + " weatherWidth=" + f2 + " iconMarginWidth=" + f4);
        int paddingStart = (int) (fMeasureText2 + f + fMeasureText + f3 + f2 + f4 + ((float) getPaddingStart()) + ((float) getPaddingEnd()));
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        int iMax = ((int) Math.max(fontMetrics.descent - fontMetrics.ascent, Math.max(Math.max((this.mHasHealthIcon || this.mHasWeatherIcon) ? this.mIconWH : 0.0f, (this.mNeedIcon || (drawable = this.mPointIcon) == null) ? 0.0f : drawable.getIntrinsicHeight()), this.needDrawRect ? this.rectHeight : 0.0f))) + getPaddingTop() + getPaddingBottom();
        int mode = View.MeasureSpec.getMode(i);
        int size = View.MeasureSpec.getSize(i);
        if (mode == Integer.MIN_VALUE) {
            size = Math.min(paddingStart, size);
        }
        int mode2 = View.MeasureSpec.getMode(i2);
        int size2 = View.MeasureSpec.getSize(i2);
        if (mode2 == Integer.MIN_VALUE) {
            iMax = Math.min(iMax, size2);
        } else if (mode2 != 0) {
            iMax = size2;
        }
        setMeasuredDimension(size, iMax);
    }

    public void onScreenSizeChange() {
        initSetting();
        initPadding();
    }

    @Override // android.view.View
    public void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        if (i == 0) {
            updateTime();
        }
    }

    public void presetData(HealthBean healthBean, WeatherBean weatherBean) {
        this.mHealthBean = healthBean;
        this.mWeatherBean = weatherBean;
    }

    public final void scheduleRequestLayout() {
        removeCallbacks(this.mRequestLayoutRunnable);
        post(this.mRequestLayoutRunnable);
    }

    public void setCalendar(Calendar calendar) {
        this.mCalendar = calendar;
    }

    public void setClockDarkMode(boolean z) {
        this.mClockInfo.setPrimaryColor(z ? -1 : -16777216);
        initData(this.mClockInfo, this.mCurrentLineType);
    }

    public void setDateTextColor(int i) {
        MiuiClassicClockInfoBase miuiClassicClockInfoBase;
        if (!DeviceConfig.supportBackgroundBlur(this.mContext) || (miuiClassicClockInfoBase = this.mClockInfo) == null || ClockStyleInfo.isAODType(miuiClassicClockInfoBase.getDisplayType())) {
            super.setTextColor(i);
        } else {
            super.setTextColor(this.mClockInfo.getDatePrimaryColor(i));
        }
    }

    @Override // android.widget.TextView
    public void setTextColor(@ColorInt int i) {
        setDateTextColor(i);
    }

    public final void syncPlaceholderColor(int i) {
        this.mRectPaint.setColor(i);
        Drawable drawable = this.mWeatherIcon;
        if (drawable != null) {
            drawable.setTint(i);
        }
    }

    public void updateHealth(HealthBean healthBean) {
        String stepCountTargetString;
        SpannableString heatherSpannableString;
        this.mHealthBean = healthBean;
        String str = this.mPlaceholderText;
        int i = this.mCurrentLineType;
        int stepCountNow = 0;
        String string = null;
        if (i == 500) {
            if (hasStepCountData()) {
                stepCountNow = this.mHealthBean.getStepCountNow();
                int stepCountTarget = this.mHealthBean.getStepCountTarget();
                String stepCountNowString = this.mHealthBean.getStepCountNowString();
                stepCountTargetString = this.mHealthBean.getStepCountTargetString();
                string = getResources().getString(R.string.health_chart_step_count_schedule_format_desc, getResources().getQuantityString(R.plurals.health_chart_step_count_current_schedule_format_desc, stepCountNow, stepCountNowString), getResources().getQuantityString(R.plurals.health_chart_step_count_goal_schedule_format_desc, stepCountTarget, stepCountTargetString));
                str = stepCountNowString;
            } else {
                stepCountTargetString = str;
            }
            heatherSpannableString = getHeatherSpannableString(R.plurals.health_chart_step_count_schedule_format, stepCountNow, str, stepCountTargetString);
        } else if (i == 502) {
            if (hasCaloriesData()) {
                stepCountNow = this.mHealthBean.getCaloriesNow();
                int caloriesTarget = this.mHealthBean.getCaloriesTarget();
                String caloriesNowString = this.mHealthBean.getCaloriesNowString();
                stepCountTargetString = this.mHealthBean.getCaloriesTargetString();
                string = getResources().getString(R.string.health_chart_calories_schedule_format_desc, getResources().getQuantityString(R.plurals.health_chart_calories_burned_schedule_format_desc, stepCountNow, caloriesNowString), getResources().getQuantityString(R.plurals.health_chart_calories_goal_schedule_format_desc, caloriesTarget, stepCountTargetString));
                str = caloriesNowString;
            } else {
                stepCountTargetString = str;
            }
            heatherSpannableString = getHeatherSpannableString(R.plurals.health_chart_calories_schedule_format, stepCountNow, str, stepCountTargetString);
        } else if (i != 504) {
            if (hasSportTimeData()) {
                stepCountNow = this.mHealthBean.getSportTimeNow();
                int sportTimeTarget = this.mHealthBean.getSportTimeTarget();
                String sportTimeNowString = this.mHealthBean.getSportTimeNowString();
                stepCountTargetString = this.mHealthBean.getSportTimeTargetString();
                string = getResources().getString(R.string.health_chart_sport_time_schedule_format_desc, getResources().getQuantityString(R.plurals.health_chart_sport_time_current_schedule_format_desc, stepCountNow, sportTimeNowString), getResources().getQuantityString(R.plurals.health_chart_sport_time_goal_schedule_format_desc, sportTimeTarget, stepCountTargetString));
                str = sportTimeNowString;
            } else {
                stepCountTargetString = str;
            }
            heatherSpannableString = getHeatherSpannableString(R.plurals.health_chart_sport_time_schedule_format, stepCountNow, str, stepCountTargetString);
        } else {
            if (hasStandCountData()) {
                stepCountNow = this.mHealthBean.getStandCountNow();
                int standCountTarget = this.mHealthBean.getStandCountTarget();
                String standCountNowString = this.mHealthBean.getStandCountNowString();
                stepCountTargetString = this.mHealthBean.getStandCountTargetString();
                string = getResources().getString(R.string.health_chart_stand_schedule_format_desc, getResources().getQuantityString(R.plurals.health_chart_stand_count_schedule_format_desc, stepCountNow, standCountNowString), getResources().getQuantityString(R.plurals.health_chart_stand_goal_schedule_format_desc, standCountTarget, stepCountTargetString));
                str = standCountNowString;
            } else {
                stepCountTargetString = str;
            }
            heatherSpannableString = getHeatherSpannableString(R.plurals.health_chart_stand_schedule_format, stepCountNow, str, stepCountTargetString);
        }
        Log.d("ClassicTextAreaView", " CurrentLineType=" + this.mCurrentLineType + " num:" + stepCountNow + " now:" + str + " target:" + stepCountTargetString);
        setText(heatherSpannableString);
        if (string == null) {
            string = getResources().getString(R.string.health_no_data);
        }
        setContentDescription(string);
        updateIconFlags();
        scheduleRequestLayout();
    }

    public final void updateHeight() {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        layoutParams.height = getDimen(R.dimen.classic_plus_text_area_height);
        setLayoutParams(layoutParams);
    }

    public final void updateIconFlags() {
        boolean z = this.mNeedIcon;
        boolean z2 = false;
        this.mHasHealthIcon = z && this.mHealthIcon != null;
        if (!z && this.mWeatherIcon != null) {
            z2 = true;
        }
        this.mHasWeatherIcon = z2;
    }

    /* JADX WARN: Code duplicated, block: B:31:0x018b  */
    public void updateTime() {
        String str;
        String string;
        String string2;
        String str2;
        if (getWindowVisibility() == 0 && this.mCalendar != null) {
            boolean zEquals = Locale.CHINESE.getLanguage().equals(Locale.getDefault().getLanguage());
            this.mCalendar.setTimeInMillis(System.currentTimeMillis());
            int actualRenderStyle = zEquals ? this.mCurrentLineType : ClassicContentStyle.getActualRenderStyle(this.mCurrentLineType);
            if (actualRenderStyle == 11) {
                setText(this.mCalendar.format(this.mContext, getResources().getString(R.string.format_month_day_week)));
                setContentDescription(getResources().getString(R.string.format_month_day_week_desc, this.mCalendar.format(this.mContext, getResources().getString(R.string.format_full_month_of_year)), String.valueOf(ClassicClockTimeUtils.getDayOfMonthInt(this.mCalendar)), this.mCalendar.format(this.mContext, getResources().getString(R.string.format_full_day_of_week))));
            } else if (actualRenderStyle == 103) {
                String str3 = getResources().getString(R.string.format_chs_month_day, Integer.valueOf(ClassicClockTimeUtils.getMonthOfYearInt(this.mCalendar)), Integer.valueOf(ClassicClockTimeUtils.getDayOfMonthInt(this.mCalendar))) + ClassicClockTimeUtils.getDayOfWeekString(this.mContext, this.mCalendar);
                String string3 = getResources().getString(R.string.format_month_day_week_desc, this.mCalendar.format(this.mContext, getResources().getString(R.string.format_full_month_of_year)), String.valueOf(ClassicClockTimeUtils.getDayOfMonthInt(this.mCalendar)), this.mCalendar.format(this.mContext, getResources().getString(R.string.format_full_day_of_week)));
                String solarTerms = ClassicClockTimeUtils.getSolarTerms(this.mContext, this.mCalendar);
                String lunarCalendarString = ClassicClockTimeUtils.getLunarCalendarString(this.mContext, this.mCalendar);
                if (TextUtils.isEmpty(solarTerms)) {
                    string = getResources().getString(R.string.format_lunar_calendar_month_day_2, lunarCalendarString);
                } else {
                    string = getResources().getString(R.string.format_lunar_calendar_solar_terms_continuous, lunarCalendarString, solarTerms);
                    lunarCalendarString = lunarCalendarString + ac.b + solarTerms;
                }
                setText(getResources().getString(R.string.format_lunar_calendar_solar_terms, str3, string));
                setContentDescription(string3 + lunarCalendarString);
            } else if (actualRenderStyle == 100) {
                setText(this.mCalendar.format(this.mContext, getResources().getString(R.string.format_month_day_week)));
                setContentDescription(getResources().getString(R.string.format_month_day_week_desc, this.mCalendar.format(this.mContext, getResources().getString(R.string.format_full_month_of_year)), String.valueOf(ClassicClockTimeUtils.getDayOfMonthInt(this.mCalendar)), this.mCalendar.format(this.mContext, getResources().getString(R.string.format_full_day_of_week))));
            } else if (actualRenderStyle == 101) {
                String solarTerms2 = ClassicClockTimeUtils.getSolarTerms(this.mContext, this.mCalendar);
                String lunarCalendarString2 = ClassicClockTimeUtils.getLunarCalendarString(this.mContext, this.mCalendar);
                if (TextUtils.isEmpty(solarTerms2)) {
                    String sexagenaryCycleString = ClassicClockTimeUtils.getSexagenaryCycleString(this.mContext, this.mCalendar);
                    string2 = getResources().getString(R.string.format_lunar_calendar_solar_terms, sexagenaryCycleString, lunarCalendarString2);
                    str2 = sexagenaryCycleString + ac.b + lunarCalendarString2;
                } else {
                    string2 = getResources().getString(R.string.format_lunar_calendar_solar_terms, lunarCalendarString2, solarTerms2);
                    str2 = lunarCalendarString2 + ac.b + solarTerms2;
                }
                setText(string2);
                setContentDescription(str2);
            }
            if (ClassicContentStyle.isNeedPointIcon(this.mCurrentLineType)) {
                Calendar calendar = this.mCalendar;
                Context context = this.mContext;
                str = calendar.format(context, context.getString(R.string.format_week_day_style2));
            } else {
                str = "";
            }
            this.dateForeground = str;
            scheduleRequestLayout();
        }
    }

    public void updateWeather(WeatherBean weatherBean) {
        this.mWeatherBean = weatherBean;
        this.needDrawRect = false;
        String str = "";
        if (!weatherNeedPlaceMsg()) {
            switch (this.mCurrentLineType) {
                case 400:
                    if (!weatherBean.getTemperatureValid()) {
                        setText(getResources().getString(R.string.normal_temperature_format, this.mPlaceholderText, ""));
                        this.needDrawRect = true;
                        setContentDescription(getResources().getString(R.string.weather_no_data));
                        setupWeatherIcon(null);
                    } else {
                        String string = getResources().getString(R.string.normal_temperature_format, weatherBean.getTemperatureWithoutUnit(), weatherBean.getDescription());
                        setText(string);
                        setContentDescription(string);
                        setupWeatherIcon(weatherBean);
                    }
                    break;
                case d.b /* 401 */:
                    if (!weatherBean.isAQIDateValid()) {
                        setText(getResources().getString(R.string.aqi_format, this.mPlaceholderText));
                        setContentDescription(getResources().getString(R.string.weather_no_data));
                    } else {
                        setText(getResources().getString(R.string.aqi_format, weatherBean.getAQILevel()));
                        setContentDescription(getResources().getString(R.string.aqi_format_desc, weatherBean.getAQILevel()));
                    }
                    break;
                case 402:
                default:
                    setText("");
                    setContentDescription("");
                    break;
                case 403:
                case d.c /* 404 */:
                    int i = (this.mCalendar.get(18) * 60) + this.mCalendar.get(20);
                    if (weatherBean.getSunriseMinuteTime() == 0 && weatherBean.getSunsetMinuteTime() == 0 && weatherBean.getSunriseTomorrowMinuteTime() == 0) {
                        Resources resources = getResources();
                        int i2 = R.string.sunrise_time_format;
                        Resources resources2 = getResources();
                        int i3 = R.string.format_hour_minute;
                        String str2 = this.mPlaceholderText;
                        setText(resources.getString(i2, resources2.getString(i3, str2, str2)));
                        setContentDescription(getResources().getString(R.string.weather_no_data));
                    } else if (i < weatherBean.getSunriseMinuteTime()) {
                        setText(getResources().getString(R.string.sunrise_time_format, weatherBean.getSunriseTimeString()));
                        setContentDescription(getResources().getString(R.string.sunrise_time_format_desc, weatherBean.getSunriseTimeString()));
                    } else if (i >= weatherBean.getSunsetMinuteTime()) {
                        setText(getResources().getString(R.string.sunrise_time_format, weatherBean.getSunriseTomorrowTimeString()));
                        setContentDescription(getResources().getString(R.string.sunrise_time_format_tomorrow_desc, weatherBean.getSunriseTomorrowTimeString()));
                    } else {
                        setText(getResources().getString(R.string.sunset_time_format, weatherBean.getSunsetTimeString(DateFormatUtils.is24HourFormat(this.mContext))));
                        setContentDescription(getResources().getString(R.string.sunset_time_format_desc, weatherBean.getSunsetTimeString(DateFormatUtils.is24HourFormat(this.mContext))));
                    }
                    break;
                case 405:
                    setText(getResources().getString(R.string.uvi_format, weatherBean.getUVILevel(), getResources().getString(weatherBean.getUVILevelDescResID())));
                    setContentDescription(getResources().getString(R.string.uvi_format_desc, weatherBean.getUVILevel(), getResources().getString(weatherBean.getUVILevelDescResID())));
                    break;
                case 406:
                    String string2 = getResources().getString(R.string.somatosensory_temperature_format, Integer.toString(weatherBean.getSomatosensoryTemperature()));
                    setText(string2);
                    setContentDescription(string2);
                    break;
                case 407:
                    String string3 = getResources().getString(R.string.wind_format, getResources().getString(weatherBean.getWindDescResIdFull()), weatherBean.getWindStrength());
                    setText(string3);
                    setContentDescription(string3);
                    break;
                case 408:
                    String string4 = getResources().getString(R.string.humidity_format, NumberFormat.getPercentInstance().format(weatherBean.getHumidity() / 100.0f));
                    setText(string4);
                    setContentDescription(string4);
                    break;
            }
        } else {
            String string5 = getResources().getString(R.string.weather_no_data);
            switch (this.mCurrentLineType) {
                case 400:
                    setText(getResources().getString(R.string.normal_temperature_format, this.mPlaceholderText, ""));
                    this.needDrawRect = true;
                    setupWeatherIcon(null);
                    str = string5;
                    break;
                case d.b /* 401 */:
                    setText(getResources().getString(R.string.aqi_format, this.mPlaceholderText));
                    str = string5;
                    break;
                case 402:
                default:
                    setText("");
                    break;
                case 403:
                case d.c /* 404 */:
                    Resources resources3 = getResources();
                    int i4 = R.string.sunrise_time_format;
                    Resources resources4 = getResources();
                    int i5 = R.string.format_hour_minute;
                    String str3 = this.mPlaceholderText;
                    setText(resources3.getString(i4, resources4.getString(i5, str3, str3)));
                    str = string5;
                    break;
                case 405:
                    setText(getResources().getString(R.string.uvi_format, this.mPlaceholderText, ""));
                    this.needDrawRect = true;
                    str = string5;
                    break;
                case 406:
                    setText(getResources().getString(R.string.somatosensory_temperature_format, this.mPlaceholderText));
                    str = string5;
                    break;
                case 407:
                    setText("");
                    this.needDrawRect = true;
                    str = string5;
                    break;
                case 408:
                    setText(getResources().getString(R.string.humidity_format, this.mPercentSignPlaceholderText));
                    str = string5;
                    break;
            }
            setContentDescription(str);
        }
        if (!this.needDrawRect) {
            setPadding(0, 0, 0, 0);
        } else if (DeviceConfig.isRTL()) {
            setPadding(this.rectWidth, 0, 0, 0);
        } else {
            setPadding(0, 0, this.rectWidth, 0);
        }
        updateIconFlags();
        scheduleRequestLayout();
    }

    public final boolean weatherNeedPlaceMsg() {
        return this.mWeatherBean == null;
    }
}
