package com.miui.clock.classic;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.graphics.ColorUtils;
import com.miui.clock.MiuiTextGlassView;
import com.miui.clock.R;
import com.miui.clock.module.ClassicContentStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.HealthBean;
import com.miui.clock.module.MiuiClassicClockInfo;
import com.miui.clock.module.WeatherBean;
import com.miui.clock.utils.ClassicClockTimeUtils;
import com.miui.clock.utils.ClockEffectUtils;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.clock.utils.FontUtils;
import com.xiaomi.onetrack.g.d;
import com.xiaomi.onetrack.util.ac;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import miuix.animation.utils.EaseManager;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class ClassicContentAreaView extends RelativeLayout {
    public final int NULL_WEATHER_IN_BLUR_MIX_TYPE;
    public final int SECOND_COLOR_IN_BLUR_MIX_TYPE;
    public String TAG;
    public ViewStub big_msg_view_stub;
    public boolean hasHealthMsgChartViewInit;
    public boolean hasHealthMsgSleepChartViewInit;
    public boolean hasHealthMsgTextViewInit;
    public boolean hasHumidityIconViewInit;
    public boolean hasRightBottomMsgInit;
    public boolean hasRightTopMsgInit;
    public boolean hasRightTopMsgWeatherInit;
    public boolean hasRightTopRainMsgInit;
    public boolean hasSomatosensoryMsgInit;
    public boolean hasSunMoveViewInit;
    public ViewStub health_msg_chart_view_stub;
    public ViewStub health_msg_sleep_chart_view_stub;
    public ViewStub health_msg_text_view_stub;
    public ViewStub humidity_icon_view_stub;
    public boolean isPreview;
    public TextView mBigMsgView;
    public Calendar mCalendar;
    public MiuiClassicClockInfo mClockInfo;
    public Context mContext;
    public boolean mFirstInit;
    public boolean mHasNumberHourMin;
    public HealthBean mHealthBean;
    public HealthMsgChartView mHealthMsgChartView;
    public HealthMsgSleepChartView mHealthMsgSleepChartView;
    public HealthMsgTextView mHealthMsgTextView;
    public HumidityIconView mHumidityIconView;
    public boolean mIs24HourFormat;
    public String mPlaceholderText;
    public ImageView mPressureIcon;
    public ImageView mRainIcon;
    public Resources mResources;
    public TextView mRightBottomMsg;
    public TextView mRightTopMsg;
    public TextView mRightTopMsgWeather;
    public TextView mRightTopRainMsg;
    public int mScreenWidth;
    public ImageView mSomatosensoryIcon;
    public TextView mSomatosensoryMsg;
    public int mStyle;
    public SunMoveView mSunMoveView;
    public String mTextSlash;
    public MiuiTextGlassView mTimeView;
    public ImageView mTopHealthIcon;
    public int mType;
    public WeatherBean mWeatherBean;
    public ImageView mWeatherIcon;
    public ImageView mWindIcon;
    public ViewStub pressure_icon_view_stub;
    public ViewStub rain_icon_view_stub;
    public ViewStub right_bottom_msg_view_stub;
    public ViewStub right_top_msg_view_stub;
    public ViewStub right_top_msg_weather_view_stub;
    public ViewStub right_top_rain_msg_view_stub;
    public ViewStub somatosensory_icon_view_stub;
    public ViewStub somatosensory_msg_view_stub;
    public ViewStub sun_move_view_stub;
    public int textCurrentColor;
    public ViewStub time_view_stub;
    public ViewStub top_health_icon_stub;
    public ViewStub weather_icon_view_stub;
    public ViewStub wind_icon_view_stub;

    public static /* synthetic */ void $r8$lambda$Gh1tck627VPU40PHzAZTGttdHzA(ClassicContentAreaView classicContentAreaView, int i, View view, int i2, int i3, int i4, int i5, int i6) {
        int lineHeight = classicContentAreaView.mBigMsgView.getLineHeight();
        int viewBaseLine = classicContentAreaView.getViewBaseLine(classicContentAreaView.mBigMsgView);
        if (viewBaseLine > 0 && lineHeight > 0) {
            i = (lineHeight - viewBaseLine) - 10;
        }
        classicContentAreaView.setMarginsAndSize(view, i2, i3, i4, i, i5, i6);
    }

    public static /* synthetic */ void $r8$lambda$SYBogu2HAEN9lMyYVy6Op2jq7wI(ClassicContentAreaView classicContentAreaView, int i, View view, int i2, int i3, int i4) {
        int lineHeight = classicContentAreaView.mBigMsgView.getLineHeight();
        int viewBaseLine = classicContentAreaView.getViewBaseLine(classicContentAreaView.mBigMsgView);
        if (viewBaseLine > 0 && lineHeight > 0) {
            i = (lineHeight - viewBaseLine) - 10;
        }
        classicContentAreaView.setMargins(view, i2, i3, i4, i);
    }

    public ClassicContentAreaView(Context context) {
        super(context);
        this.mFirstInit = true;
        this.isPreview = false;
        this.hasRightTopMsgInit = false;
        this.hasRightTopRainMsgInit = false;
        this.hasRightBottomMsgInit = false;
        this.hasSomatosensoryMsgInit = false;
        this.hasHealthMsgTextViewInit = false;
        this.hasHumidityIconViewInit = false;
        this.hasHealthMsgChartViewInit = false;
        this.hasHealthMsgSleepChartViewInit = false;
        this.hasSunMoveViewInit = false;
        this.hasRightTopMsgWeatherInit = false;
        this.TAG = "ClassicContentAreaView";
        this.SECOND_COLOR_IN_BLUR_MIX_TYPE = Color.parseColor("#999999");
        this.NULL_WEATHER_IN_BLUR_MIX_TYPE = Color.parseColor("#4D4D4D");
        this.textCurrentColor = 0;
        init(context);
    }

    public ClassicContentAreaView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mFirstInit = true;
        this.isPreview = false;
        this.hasRightTopMsgInit = false;
        this.hasRightTopRainMsgInit = false;
        this.hasRightBottomMsgInit = false;
        this.hasSomatosensoryMsgInit = false;
        this.hasHealthMsgTextViewInit = false;
        this.hasHumidityIconViewInit = false;
        this.hasHealthMsgChartViewInit = false;
        this.hasHealthMsgSleepChartViewInit = false;
        this.hasSunMoveViewInit = false;
        this.hasRightTopMsgWeatherInit = false;
        this.TAG = "ClassicContentAreaView";
        this.SECOND_COLOR_IN_BLUR_MIX_TYPE = Color.parseColor("#999999");
        this.NULL_WEATHER_IN_BLUR_MIX_TYPE = Color.parseColor("#4D4D4D");
        this.textCurrentColor = 0;
        init(context);
    }

    public ClassicContentAreaView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mFirstInit = true;
        this.isPreview = false;
        this.hasRightTopMsgInit = false;
        this.hasRightTopRainMsgInit = false;
        this.hasRightBottomMsgInit = false;
        this.hasSomatosensoryMsgInit = false;
        this.hasHealthMsgTextViewInit = false;
        this.hasHumidityIconViewInit = false;
        this.hasHealthMsgChartViewInit = false;
        this.hasHealthMsgSleepChartViewInit = false;
        this.hasSunMoveViewInit = false;
        this.hasRightTopMsgWeatherInit = false;
        this.TAG = "ClassicContentAreaView";
        this.SECOND_COLOR_IN_BLUR_MIX_TYPE = Color.parseColor("#999999");
        this.NULL_WEATHER_IN_BLUR_MIX_TYPE = Color.parseColor("#4D4D4D");
        this.textCurrentColor = 0;
        init(context);
    }

    private int getTextSlashColor() {
        if (!ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()) && ClockEffectUtils.isBlurMixType(this.mClockInfo.getClockEffect()) && DeviceConfig.supportBackgroundBlur(this.mContext)) {
            return this.SECOND_COLOR_IN_BLUR_MIX_TYPE;
        }
        int classicPlusSecondaryColor = this.textCurrentColor;
        if (classicPlusSecondaryColor == 0) {
            classicPlusSecondaryColor = this.mClockInfo.getClassicPlusSecondaryColor();
        }
        return ColorUtils.setAlphaComponent(classicPlusSecondaryColor, 153);
    }

    private void setBigMsgText(String str) {
        if (this.mBigMsgView == null) {
            return;
        }
        if (TextUtils.isEmpty(str) || !str.contains(this.mTextSlash)) {
            this.mBigMsgView.setText(str);
            return;
        }
        int iIndexOf = str.indexOf(this.mTextSlash);
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new ForegroundColorSpan(getTextSlashColor()), iIndexOf, iIndexOf + 1, 17);
        this.mBigMsgView.setText(spannableString);
    }

    private void setTextSameWidth(boolean z) {
        if (this.isPreview) {
            z = false;
        }
        MiuiTextGlassView miuiTextGlassView = this.mTimeView;
        if (miuiTextGlassView != null) {
            miuiTextGlassView.setFontFeatureSettings(z ? "tnum" : "");
        }
        TextView textView = this.mBigMsgView;
        if (textView != null) {
            textView.setFontFeatureSettings(z ? "tnum" : "");
        }
    }

    public final void checkBigMsgView() {
        if (this.mBigMsgView == null) {
            TextView textView = (TextView) this.big_msg_view_stub.inflate();
            this.mBigMsgView = textView;
            textView.setSingleLine();
        }
    }

    public final void checkHealthMsgChartView() {
        if (this.mHealthMsgChartView == null) {
            this.mHealthMsgChartView = (HealthMsgChartView) this.health_msg_chart_view_stub.inflate();
        }
    }

    public final void checkHealthMsgSleepChartView() {
        if (this.mHealthMsgSleepChartView == null) {
            this.mHealthMsgSleepChartView = (HealthMsgSleepChartView) this.health_msg_sleep_chart_view_stub.inflate();
        }
    }

    public final void checkHealthMsgTextView() {
        if (this.mHealthMsgTextView == null) {
            this.mHealthMsgTextView = (HealthMsgTextView) this.health_msg_text_view_stub.inflate();
        }
    }

    public final void checkHumidityIconView() {
        if (this.mHumidityIconView == null) {
            this.mHumidityIconView = (HumidityIconView) this.humidity_icon_view_stub.inflate();
        }
    }

    public final void checkPressureIcon() {
        if (this.mPressureIcon == null) {
            this.mPressureIcon = (ImageView) this.pressure_icon_view_stub.inflate();
        }
    }

    public final void checkRainIcon() {
        if (this.mRainIcon == null) {
            this.mRainIcon = (ImageView) this.rain_icon_view_stub.inflate();
        }
    }

    public final void checkRightBottomMsg() {
        if (this.mRightBottomMsg == null) {
            this.mRightBottomMsg = (TextView) this.right_bottom_msg_view_stub.inflate();
        }
    }

    public final void checkRightTopMsg() {
        if (this.mRightTopMsg == null) {
            this.mRightTopMsg = (TextView) this.right_top_msg_view_stub.inflate();
        }
    }

    public final void checkRightTopMsgWeather() {
        if (this.mRightTopMsgWeather == null) {
            this.mRightTopMsgWeather = (TextView) this.right_top_msg_weather_view_stub.inflate();
        }
    }

    public final void checkRightTopRainMsg() {
        if (this.mRightTopRainMsg == null) {
            this.mRightTopRainMsg = (TextView) this.right_top_rain_msg_view_stub.inflate();
        }
    }

    public final void checkSomatosensoryIcon() {
        if (this.mSomatosensoryIcon == null) {
            this.mSomatosensoryIcon = (ImageView) this.somatosensory_icon_view_stub.inflate();
        }
    }

    public final void checkSomatosensoryMsg() {
        if (this.mSomatosensoryMsg == null) {
            this.mSomatosensoryMsg = (TextView) this.somatosensory_msg_view_stub.inflate();
        }
    }

    public final void checkSunMoveView() {
        if (this.mSunMoveView == null) {
            this.mSunMoveView = (SunMoveView) this.sun_move_view_stub.inflate();
        }
    }

    public final void checkTimeView() {
        if (this.mTimeView == null) {
            this.mTimeView = (MiuiTextGlassView) this.time_view_stub.inflate();
        }
    }

    public final void checkTopHealthIcon() {
        if (this.mTopHealthIcon == null) {
            this.mTopHealthIcon = (ImageView) this.top_health_icon_stub.inflate();
        }
    }

    public final void checkWeatherIcon() {
        if (this.mWeatherIcon == null) {
            this.mWeatherIcon = (ImageView) this.weather_icon_view_stub.inflate();
        }
    }

    public final void checkWindIcon() {
        if (this.mWindIcon == null) {
            this.mWindIcon = (ImageView) this.wind_icon_view_stub.inflate();
        }
    }

    public final void clearDrawableColor(ImageView imageView) {
        Drawable drawable;
        if (imageView == null || (drawable = imageView.getDrawable()) == null) {
            return;
        }
        drawable.clearColorFilter();
    }

    public final int getDimen(int i) {
        return (int) (getResources().getDimensionPixelSize(i) * DeviceConfig.calculateScale(getContext()));
    }

    public final int getViewBaseLine(TextView textView) {
        Paint.FontMetrics fontMetrics = textView.getPaint().getFontMetrics();
        Rect rect = new Rect();
        textView.getDrawingRect(rect);
        if (rect.isEmpty()) {
            return -1;
        }
        int i = rect.top;
        float f = i + ((rect.bottom - i) / 2.0f);
        float f2 = fontMetrics.bottom;
        return (int) ((f + ((f2 - fontMetrics.top) / 2.0f)) - f2);
    }

    public final void hideAllView() {
        setViewVisibility(this.mTimeView, 8);
        setViewVisibility(this.mBigMsgView, 8);
        setViewVisibility(this.mRightTopMsg, 8);
        setViewVisibility(this.mRightTopRainMsg, 8);
        setViewVisibility(this.mRightTopMsgWeather, 8);
        setViewVisibility(this.mRightBottomMsg, 8);
        setViewVisibility(this.mWeatherIcon, 8);
        setViewVisibility(this.mWindIcon, 8);
        setViewVisibility(this.mPressureIcon, 8);
        setViewVisibility(this.mRainIcon, 8);
        setViewVisibility(this.mSomatosensoryIcon, 8);
        setViewVisibility(this.mSomatosensoryMsg, 8);
        setViewVisibility(this.mHumidityIconView, 8);
        setViewVisibility(this.mSunMoveView, 8);
        setViewVisibility(this.mTopHealthIcon, 8);
        setViewVisibility(this.mHealthMsgTextView, 8);
        setViewVisibility(this.mHealthMsgChartView, 8);
        setViewVisibility(this.mHealthMsgSleepChartView, 8);
    }

    public final void init(Context context) {
        this.mContext = context;
        Resources resources = getResources();
        this.mResources = resources;
        this.mPlaceholderText = resources.getString(R.string.no_data_placeholder_string);
        this.mTextSlash = this.mResources.getString(R.string.text_slash);
        this.mScreenWidth = DeviceConfig.getScreenRect(this.mContext).width();
    }

    public void initData(int i, int i2, int i3, boolean z) {
        this.isPreview = true;
        MiuiClassicClockInfo miuiClassicClockInfo = new MiuiClassicClockInfo();
        miuiClassicClockInfo.setPrimaryColor(i);
        miuiClassicClockInfo.setStyle(i3);
        miuiClassicClockInfo.setEnableDiffusion(z);
        initData(miuiClassicClockInfo, i2, false);
    }

    public void initData(MiuiClassicClockInfo miuiClassicClockInfo, int i, boolean z) {
        boolean z2;
        MiuiTextGlassView miuiTextGlassView;
        this.mClockInfo = miuiClassicClockInfo;
        if (this.mHasNumberHourMin != z) {
            this.mHasNumberHourMin = z;
            setTextSameWidth(ClassicContentStyle.needOpenTextSameWidth(i, z));
        }
        if (this.mType != i) {
            this.mType = i;
            initViewVisible();
            z2 = true;
        } else {
            z2 = false;
        }
        boolean z3 = this.mStyle == this.mClockInfo.getStyle() ? z2 : true;
        if (!ClassicContentStyle.Time.isTimeType(i) || (miuiTextGlassView = this.mTimeView) == null) {
            initTextViewStyle(false);
        } else {
            ClassicClockTimeUtils.setClassicTimeStyle(miuiTextGlassView, this.mClockInfo.getTemplateId(), this.mClockInfo.getClassicPlusSecondaryColor(), this.mClockInfo.getStyle(), this.mClockInfo.getClockWeight(), i, this.mClockInfo.isEnableDiffusion(), false);
        }
        if (z3 || this.mFirstInit) {
            this.mFirstInit = false;
            onScreenSizeChange();
        }
        updateColor(miuiClassicClockInfo.getClassicPlusSecondaryColor());
    }

    public final void initHealthMsgChartData(HealthMsgChartView healthMsgChartView, int i, int i2, int i3, List list) {
        if (healthMsgChartView != null) {
            healthMsgChartView.initData(i, i2, i3, list);
        }
    }

    public final void initHealthMsgSleepChartData(HealthMsgSleepChartView healthMsgSleepChartView, int i) {
        if (healthMsgSleepChartView != null) {
            healthMsgSleepChartView.initData(i);
        }
    }

    public final void initHealthMsgTextData(HealthMsgTextView healthMsgTextView, int i, int i2, int i3, int i4, int i5, int i6) {
        if (healthMsgTextView != null) {
            healthMsgTextView.initData(i, i2, i3, i4, i5, i6);
        }
    }

    public final void initSunMoveViewData(SunMoveView sunMoveView, int i, int i2, int i3, int i4) {
        if (sunMoveView != null) {
            sunMoveView.initData(i, i2, i3, i4);
        }
    }

    public void initTextViewStyle(boolean z) {
        boolean z2;
        TextView textView;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        HealthMsgTextView healthMsgTextView;
        HumidityIconView humidityIconView;
        HealthMsgChartView healthMsgChartView;
        HealthMsgSleepChartView healthMsgSleepChartView;
        SunMoveView sunMoveView;
        TextView textView5;
        int style = this.mClockInfo.getStyle();
        TextView textView6 = this.mBigMsgView;
        if (textView6 != null) {
            ClassicClockTimeUtils.setBigMsgViewStyle(textView6, style, this.mType);
        }
        if (this.mStyle != style || z) {
            this.mStyle = style;
            z2 = true;
        } else {
            z2 = false;
        }
        Typeface smallFontTypeFace = FontUtils.getSmallFontTypeFace(style);
        if ((!this.hasRightTopMsgInit || z2) && (textView = this.mRightTopMsg) != null) {
            this.hasRightTopMsgInit = true;
            textView.setTypeface(smallFontTypeFace);
        }
        if ((!this.hasRightTopRainMsgInit || z2) && (textView2 = this.mRightTopRainMsg) != null) {
            this.hasRightTopRainMsgInit = true;
            textView2.setTypeface(smallFontTypeFace);
        }
        if ((!this.hasRightBottomMsgInit || z2) && (textView3 = this.mRightBottomMsg) != null) {
            this.hasRightBottomMsgInit = true;
            textView3.setTypeface(smallFontTypeFace);
        }
        if ((!this.hasSomatosensoryMsgInit || z2) && (textView4 = this.mSomatosensoryMsg) != null) {
            this.hasSomatosensoryMsgInit = true;
            textView4.setTypeface(smallFontTypeFace);
        }
        if ((!this.hasHealthMsgTextViewInit || z2) && (healthMsgTextView = this.mHealthMsgTextView) != null) {
            this.hasHealthMsgTextViewInit = true;
            healthMsgTextView.setTypeface(smallFontTypeFace);
        }
        if ((!this.hasHumidityIconViewInit || z2) && (humidityIconView = this.mHumidityIconView) != null) {
            this.hasHumidityIconViewInit = true;
            humidityIconView.setTypeface(smallFontTypeFace);
        }
        if ((!this.hasHealthMsgChartViewInit || z2) && (healthMsgChartView = this.mHealthMsgChartView) != null) {
            this.hasHealthMsgChartViewInit = true;
            healthMsgChartView.setTypeface(smallFontTypeFace);
        }
        if ((!this.hasHealthMsgSleepChartViewInit || z2) && (healthMsgSleepChartView = this.mHealthMsgSleepChartView) != null) {
            this.hasHealthMsgSleepChartViewInit = true;
            healthMsgSleepChartView.setTypeface(smallFontTypeFace);
        }
        if ((!this.hasSunMoveViewInit || z2) && (sunMoveView = this.mSunMoveView) != null) {
            this.hasSunMoveViewInit = true;
            sunMoveView.setTypeface(smallFontTypeFace);
        }
        if ((!this.hasRightTopMsgWeatherInit || z2) && (textView5 = this.mRightTopMsgWeather) != null) {
            this.hasRightTopMsgWeatherInit = true;
            textView5.setTypeface(FontUtils.getTemperatureUnitTypeFace(style));
        }
        if (z) {
            onScreenSizeChange();
            requestLayout();
            invalidate();
        }
        Log.d(this.TAG, "initTextViewStyle, forceUpdate=" + z);
    }

    public void initViewVisible() {
        hideAllView();
        resetTextView();
        int i = this.mType;
        if (ClassicContentStyle.Time.isTimeType(i)) {
            checkTimeView();
            setViewVisibility(this.mTimeView, 0);
        } else if (ClassicContentStyle.LargeDate.isLargeDateType(this.mType)) {
            checkBigMsgView();
            setViewVisibility(this.mBigMsgView, 0);
            switch (i) {
                case 202:
                case 203:
                case 205:
                    checkRightTopMsg();
                    setViewVisibility(this.mRightTopMsg, 0);
                    break;
                case 208:
                    if (Locale.ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage())) {
                        checkRightTopMsg();
                        setViewVisibility(this.mRightTopMsg, 0);
                    }
                    break;
                case 209:
                    checkRightTopMsg();
                    setViewVisibility(this.mRightTopMsg, 0);
                    if (!TextUtils.isEmpty(ClassicClockTimeUtils.getSolarTerms(this.mContext, this.mCalendar))) {
                        checkRightBottomMsg();
                        setViewVisibility(this.mRightBottomMsg, 0);
                    }
                    break;
                case 210:
                    checkRightTopMsg();
                    setViewVisibility(this.mRightTopMsg, 0);
                    checkRightBottomMsg();
                    setViewVisibility(this.mRightBottomMsg, 0);
                    break;
                case 211:
                    if (!TextUtils.isEmpty(ClassicClockTimeUtils.getSolarTerms(this.mContext, this.mCalendar))) {
                        checkRightTopMsg();
                        setViewVisibility(this.mRightTopMsg, 0);
                    }
                    break;
            }
        } else if (ClassicContentStyle.Weather.isWeatherType(this.mType)) {
            switch (this.mType) {
                case d.b /* 401 */:
                    checkBigMsgView();
                    setViewVisibility(this.mBigMsgView, 0);
                    checkRightTopMsg();
                    setViewVisibility(this.mRightTopMsg, 0);
                    setText(this.mRightTopMsg, this.mResources.getString(R.string.air_quality_index_abbreviation));
                    break;
                case 402:
                    checkBigMsgView();
                    setViewVisibility(this.mBigMsgView, 0);
                    checkRightTopRainMsg();
                    setViewVisibility(this.mRightTopRainMsg, 0);
                    setText(this.mRightTopRainMsg, this.mResources.getString(R.string.percent_sign));
                    checkRainIcon();
                    setViewVisibility(this.mRainIcon, 0);
                    break;
                case 403:
                case d.c /* 404 */:
                    checkSunMoveView();
                    this.mSunMoveView.setStyle(this.mClockInfo.getStyle());
                    setViewVisibility(this.mSunMoveView, 0);
                    break;
                case 405:
                    checkBigMsgView();
                    setViewVisibility(this.mBigMsgView, 0);
                    checkRightTopMsg();
                    setViewVisibility(this.mRightTopMsg, 0);
                    setText(this.mRightTopMsg, this.mResources.getString(R.string.ultraviolet_rays_abbreviation));
                    checkRightBottomMsg();
                    setViewVisibility(this.mRightBottomMsg, 0);
                    break;
                case 406:
                    checkSomatosensoryIcon();
                    setViewVisibility(this.mSomatosensoryIcon, 0);
                    checkSomatosensoryMsg();
                    setViewVisibility(this.mSomatosensoryMsg, 0);
                    break;
                case 407:
                    checkBigMsgView();
                    setViewVisibility(this.mBigMsgView, 0);
                    checkRightTopMsg();
                    setViewVisibility(this.mRightTopMsg, 0);
                    checkWindIcon();
                    setViewVisibility(this.mWindIcon, 0);
                    break;
                case 408:
                    checkHumidityIconView();
                    setViewVisibility(this.mHumidityIconView, 0);
                    break;
                case 409:
                    checkBigMsgView();
                    setViewVisibility(this.mBigMsgView, 0);
                    checkRightTopMsg();
                    setViewVisibility(this.mRightTopMsg, 0);
                    setText(this.mRightTopMsg, this.mResources.getString(R.string.atmosphere_pressure_unit));
                    checkPressureIcon();
                    setViewVisibility(this.mPressureIcon, 0);
                    break;
                default:
                    checkBigMsgView();
                    setViewVisibility(this.mBigMsgView, 0);
                    checkRightTopMsgWeather();
                    setViewVisibility(this.mRightTopMsgWeather, 0);
                    checkWeatherIcon();
                    setViewVisibility(this.mWeatherIcon, 0);
                    break;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("initViewVisible updateWeather data null ? ");
            sb.append(this.mWeatherBean == null);
            sb.append(", parent = ");
            sb.append(getParent());
            Log.i("ClassicContentAreaView", sb.toString());
            updateWeather(this.mWeatherBean);
        } else if (ClassicContentStyle.Health.isHealthType(this.mType)) {
            switch (this.mType) {
                case 500:
                case 502:
                case 504:
                case 508:
                    checkBigMsgView();
                    setViewVisibility(this.mBigMsgView, 0);
                    checkTopHealthIcon();
                    setViewVisibility(this.mTopHealthIcon, 0);
                    setImageResource(this.mTopHealthIcon, HealthBean.getHealthIconByType(this.mType));
                    checkRightBottomMsg();
                    setViewVisibility(this.mRightBottomMsg, 0);
                    break;
                case 501:
                case 503:
                case 505:
                    checkHealthMsgChartView();
                    setViewVisibility(this.mHealthMsgChartView, 0);
                    break;
                case 506:
                    checkHealthMsgSleepChartView();
                    setViewVisibility(this.mHealthMsgSleepChartView, 0);
                    break;
                case 507:
                    checkHealthMsgTextView();
                    setViewVisibility(this.mHealthMsgTextView, 0);
                    break;
            }
            StringBuilder sb2 = new StringBuilder();
            sb2.append("initViewVisible updateHealth data = null ? ");
            sb2.append(this.mHealthBean == null);
            sb2.append(", parent = ");
            sb2.append(getParent());
            Log.i("ClassicContentAreaView", sb2.toString());
            updateHealth(this.mHealthBean);
        }
        setTextSameWidth(ClassicContentStyle.needOpenTextSameWidth(i, this.mHasNumberHourMin));
    }

    public final boolean noHealthData(int i) {
        HealthBean healthBean = this.mHealthBean;
        if (healthBean == null) {
            return true;
        }
        switch (i) {
            case 500:
            case 501:
                return !healthBean.hasStepCountData();
            case 502:
            case 503:
                return !healthBean.hasCaloriesData();
            case 504:
                return !healthBean.hasStandCountData();
            case 505:
            case 508:
                return !healthBean.hasSportTimeData();
            case 506:
                return !healthBean.hasSleepDurationData();
            case 507:
            default:
                return (healthBean.hasSportTimeData() || this.mHealthBean.hasCaloriesData() || this.mHealthBean.hasStepCountData()) ? false : true;
        }
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        int iWidth = DeviceConfig.getScreenRect(this.mContext).width();
        if (this.mScreenWidth != iWidth) {
            this.mScreenWidth = iWidth;
            onScreenSizeChange();
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.time_view_stub = (ViewStub) findViewById(R.id.time_view_stub);
        this.big_msg_view_stub = (ViewStub) findViewById(R.id.big_msg_view_stub);
        this.right_top_msg_view_stub = (ViewStub) findViewById(R.id.right_top_msg_view_stub);
        this.right_top_rain_msg_view_stub = (ViewStub) findViewById(R.id.right_top_rain_msg_view_stub);
        this.right_top_msg_weather_view_stub = (ViewStub) findViewById(R.id.right_top_msg_weather_view_stub);
        this.right_bottom_msg_view_stub = (ViewStub) findViewById(R.id.right_bottom_msg_view_stub);
        this.weather_icon_view_stub = (ViewStub) findViewById(R.id.weather_icon_view_stub);
        this.wind_icon_view_stub = (ViewStub) findViewById(R.id.wind_icon_view_stub);
        this.pressure_icon_view_stub = (ViewStub) findViewById(R.id.pressure_icon_view_stub);
        this.rain_icon_view_stub = (ViewStub) findViewById(R.id.rain_icon_view_stub);
        this.somatosensory_icon_view_stub = (ViewStub) findViewById(R.id.somatosensory_icon_view_stub);
        this.somatosensory_msg_view_stub = (ViewStub) findViewById(R.id.somatosensory_msg_view_stub);
        this.humidity_icon_view_stub = (ViewStub) findViewById(R.id.humidity_icon_view_stub);
        this.sun_move_view_stub = (ViewStub) findViewById(R.id.sun_move_view_stub);
        this.top_health_icon_stub = (ViewStub) findViewById(R.id.top_health_icon_stub);
        this.health_msg_text_view_stub = (ViewStub) findViewById(R.id.health_msg_text_view_stub);
        this.health_msg_chart_view_stub = (ViewStub) findViewById(R.id.health_msg_chart_view_stub);
        this.health_msg_sleep_chart_view_stub = (ViewStub) findViewById(R.id.health_msg_sleep_chart_view_stub);
        checkTimeView();
        checkBigMsgView();
        checkRightTopMsg();
        checkRightBottomMsg();
        checkRightTopRainMsg();
        checkWeatherIcon();
        checkRightTopMsgWeather();
    }

    public final void onScreenSizeChange() {
        MiuiClassicClockInfo miuiClassicClockInfo = this.mClockInfo;
        if (miuiClassicClockInfo == null) {
            Log.d("ClassicContentAreaView", "onSizeChanged mClockStyleInfo is null");
            return;
        }
        MiuiTextGlassView miuiTextGlassView = this.mTimeView;
        if (miuiTextGlassView != null) {
            ClassicClockTimeUtils.setClassicTimeStyle(miuiTextGlassView, miuiClassicClockInfo.getTemplateId(), this.mClockInfo.getClassicPlusSecondaryColor(), this.mClockInfo.getStyle(), this.mClockInfo.getClockWeight(), this.mType, this.mClockInfo.isEnableDiffusion(), false);
        }
        TextView textView = this.mBigMsgView;
        if (textView != null) {
            ClassicClockTimeUtils.setBigMsgViewStyle(textView, this.mStyle, this.mType);
        }
        TextView textView2 = this.mRightTopMsg;
        if (textView2 != null) {
            textView2.setTextSize(0, getDimen(R.dimen.classic_content_area_right_top_msg_text_size));
            setMargins(this.mRightTopMsg, getDimen(R.dimen.classic_content_area_right_top_msg_margin_start), getDimen(ClassicClockTimeUtils.getRightTopMsgMarginTopResId(this.mStyle)), 0, 0);
        } else {
            setMargins(this.right_top_msg_view_stub, getDimen(R.dimen.classic_content_area_right_top_msg_margin_start), getDimen(ClassicClockTimeUtils.getRightTopMsgMarginTopResId(this.mStyle)), 0, 0);
        }
        TextView textView3 = this.mRightTopRainMsg;
        if (textView3 != null) {
            textView3.setTextSize(0, getDimen(R.dimen.classic_content_area_right_top_msg_text_size));
            setMargins(this.mRightTopRainMsg, getDimen(R.dimen.classic_content_area_right_top_msg_margin_start_small), getDimen(ClassicClockTimeUtils.getRightTopMsgMarginTopResId(this.mStyle)), 0, 0);
        } else {
            setMargins(this.right_top_rain_msg_view_stub, getDimen(R.dimen.classic_content_area_right_top_msg_margin_start_small), getDimen(ClassicClockTimeUtils.getRightTopMsgMarginTopResId(this.mStyle)), 0, 0);
        }
        TextView textView4 = this.mRightTopMsgWeather;
        if (textView4 != null) {
            textView4.setTextSize(0, getDimen(R.dimen.classic_content_area_right_top_msg_weather_text_size));
            setMargins(this.mRightTopMsgWeather, getDimen(R.dimen.classic_content_area_right_top_msg_weather_margin_start), getDimen(ClassicClockTimeUtils.getRightTopMsgWeatherMarginTopResId(this.mStyle)), 0, 0);
        } else {
            setMargins(this.right_top_msg_weather_view_stub, getDimen(R.dimen.classic_content_area_right_top_msg_weather_margin_start), getDimen(R.dimen.classic_content_area_right_top_msg_weather_margin_top), 0, 0);
        }
        TextView textView5 = this.mRightBottomMsg;
        if (textView5 != null) {
            textView5.setTextSize(0, getDimen(R.dimen.classic_content_area_right_bottom_msg_text_size));
            setViewMargin(this.mRightBottomMsg, getDimen(R.dimen.classic_content_area_right_bottom_msg_margin_start), 0, 0, getDimen(R.dimen.classic_content_area_right_bottom_msg_margin_bottom));
        } else {
            setMargins(this.right_bottom_msg_view_stub, getDimen(R.dimen.classic_content_area_right_bottom_msg_margin_start), 0, 0, getDimen(R.dimen.classic_content_area_right_bottom_msg_margin_bottom));
        }
        if (this.mWeatherIcon != null) {
            int dimen = getDimen(R.dimen.weather_small_icon_w_h);
            setViewMarginAndSize(this.mWeatherIcon, getDimen(R.dimen.classic_content_area_right_bottom_icon_margin_start), 0, 0, getDimen(R.dimen.classic_content_area_right_bottom_icon_margin_bottom), dimen, dimen);
        } else {
            setMargins(this.weather_icon_view_stub, getDimen(R.dimen.classic_content_area_right_bottom_icon_margin_start), 0, 0, getDimen(R.dimen.classic_content_area_right_bottom_icon_margin_bottom));
        }
        if (this.mWindIcon != null) {
            int dimen2 = getDimen(R.dimen.weather_small_icon_w_h);
            setViewMarginAndSize(this.mWindIcon, getDimen(R.dimen.weather_icon_gone_margin_start), 0, 0, getDimen(R.dimen.classic_content_area_right_bottom_icon_margin_bottom), dimen2, dimen2);
        } else {
            setMargins(this.wind_icon_view_stub, getDimen(R.dimen.weather_icon_gone_margin_start), 0, 0, getDimen(R.dimen.classic_content_area_right_bottom_icon_margin_bottom));
        }
        if (this.mPressureIcon != null) {
            int dimen3 = getDimen(R.dimen.weather_pressure_icon_w_h);
            setViewMarginAndSize(this.mPressureIcon, getDimen(R.dimen.weather_pressure_icon_margin_start), 0, 0, getDimen(R.dimen.weather_pressure_icon_margin_bottom), dimen3, dimen3);
        } else {
            setMargins(this.pressure_icon_view_stub, getDimen(R.dimen.weather_pressure_icon_margin_start), 0, 0, getDimen(R.dimen.weather_pressure_icon_margin_bottom));
        }
        ImageView imageView = this.mRainIcon;
        if (imageView != null) {
            setMarginsAndSize(imageView, getDimen(R.dimen.classic_content_area_right_bottom_rain_icon_margin_start), 0, 0, getDimen(R.dimen.classic_content_area_right_bottom_icon_margin_bottom), getDimen(R.dimen.weather_rain_icon_w), getDimen(R.dimen.weather_rain_icon_h));
        } else {
            setMargins(this.rain_icon_view_stub, getDimen(R.dimen.classic_content_area_right_bottom_rain_icon_margin_start), 0, 0, getDimen(R.dimen.classic_content_area_right_bottom_icon_margin_bottom));
        }
        ImageView imageView2 = this.mSomatosensoryIcon;
        if (imageView2 != null) {
            imageView2.setPadding(0, getDimen(R.dimen.weather_somatosensory_icon_padding_top), 0, getDimen(R.dimen.weather_somatosensory_icon_padding_bottom));
            setMarginsAndSize(this.mSomatosensoryIcon, getDimen(R.dimen.weather_somatosensory_icon_margin_start), 0, 0, 0, getDimen(R.dimen.weather_somatosensory_icon_w), getDimen(R.dimen.weather_somatosensory_icon_h));
        } else {
            setMargins(this.somatosensory_icon_view_stub, getDimen(R.dimen.weather_somatosensory_icon_margin_start), 0, 0, 0);
        }
        TextView textView6 = this.mSomatosensoryMsg;
        if (textView6 != null) {
            textView6.setTextSize(0, getDimen(R.dimen.classic_content_area_right_top_msg_text_size));
            setMargins(this.mSomatosensoryMsg, getDimen(R.dimen.classic_content_area_somatosensory_msg_margin_start), getDimen(R.dimen.classic_content_area_somatosensory_msg_margin_top), 0, 0);
        } else {
            setMargins(this.somatosensory_msg_view_stub, getDimen(R.dimen.classic_content_area_somatosensory_msg_margin_start), getDimen(R.dimen.classic_content_area_somatosensory_msg_margin_top), 0, 0);
        }
        HumidityIconView humidityIconView = this.mHumidityIconView;
        if (humidityIconView != null) {
            humidityIconView.readSize();
            setSizes(this.mHumidityIconView, -2, getDimen(R.dimen.humidity_view_height));
            this.mHumidityIconView.requestLayout();
            this.mHumidityIconView.invalidate();
        }
        SunMoveView sunMoveView = this.mSunMoveView;
        if (sunMoveView != null) {
            sunMoveView.readSize();
            setSizes(this.mSunMoveView, getDimen(R.dimen.sun_move_width), getDimen(R.dimen.sun_move_height));
            this.mSunMoveView.setStyle(this.mClockInfo.getStyle());
            this.mSunMoveView.initData((this.mCalendar.get(18) * 60) + this.mCalendar.get(20));
        }
        if (this.mTopHealthIcon != null) {
            int dimen4 = getDimen(R.dimen.health_icon_w_h);
            setMarginsAndSize(this.mTopHealthIcon, getDimen(R.dimen.health_top_icon_margin_start), getDimen(R.dimen.health_top_icon_margin_top), 0, 0, dimen4, dimen4);
        } else {
            setMargins(this.top_health_icon_stub, getDimen(R.dimen.health_top_icon_margin_start), getDimen(R.dimen.health_top_icon_margin_top), 0, 0);
        }
        HealthMsgTextView healthMsgTextView = this.mHealthMsgTextView;
        if (healthMsgTextView != null) {
            healthMsgTextView.readSize();
            this.mHealthMsgTextView.measureTextWidth();
            this.mHealthMsgTextView.requestLayout();
            this.mHealthMsgTextView.invalidate();
        }
        HealthMsgChartView healthMsgChartView = this.mHealthMsgChartView;
        if (healthMsgChartView != null) {
            healthMsgChartView.readSize();
            this.mHealthMsgChartView.measureTextWidth();
            this.mHealthMsgChartView.requestLayout();
            this.mHealthMsgChartView.invalidate();
        }
        HealthMsgSleepChartView healthMsgSleepChartView = this.mHealthMsgSleepChartView;
        if (healthMsgSleepChartView != null) {
            healthMsgSleepChartView.readSize();
            this.mHealthMsgSleepChartView.initData(noHealthData(this.mType) ? -1 : this.mHealthBean.getSleepDurationMinute());
        }
    }

    public void presetData(HealthBean healthBean, WeatherBean weatherBean) {
        this.mHealthBean = healthBean;
        this.mWeatherBean = weatherBean;
    }

    public final void resetTextView() {
        setText(this.mBigMsgView, "");
        setText(this.mRightTopMsg, "");
        setText(this.mRightBottomMsg, "");
    }

    public void setCalendar(Calendar calendar) {
        this.mCalendar = calendar;
    }

    public void setClockDarkMode(boolean z) {
        this.mClockInfo.setPrimaryColor(z ? -1 : -16777216);
        initData(this.mClockInfo, this.mType, false);
        updateTime(this.mIs24HourFormat);
    }

    public final void setHumidity(HumidityIconView humidityIconView, int i) {
        if (humidityIconView != null) {
            humidityIconView.setHumidity(i);
        }
    }

    public final void setImageResource(ImageView imageView, int i) {
        if (imageView != null) {
            imageView.setImageResource(i);
        }
    }

    public final void setMargins(View view, int i, int i2, int i3, int i4) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.setMargins(i, i2, i3, i4);
        view.setLayoutParams(layoutParams);
    }

    public final void setMarginsAndSize(View view, int i, int i2, int i3, int i4, int i5, int i6) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.setMargins(i, i2, i3, i4);
        layoutParams.width = i5;
        layoutParams.height = i6;
        view.setLayoutParams(layoutParams);
    }

    public final void setSizes(View view, int i, int i2) {
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        layoutParams.width = i;
        layoutParams.height = i2;
        view.setLayoutParams(layoutParams);
    }

    public final void setText(TextView textView, String str) {
        if (textView != null) {
            textView.setText(str);
        }
    }

    public final void setTextColor(TextView textView, int i) {
        if (textView != null) {
            textView.setTextColor(i);
        }
    }

    public final void setViewMargin(final View view, final int i, final int i2, final int i3, final int i4) {
        TextView textView;
        if (FontUtils.isZhOrEn() || (textView = this.mBigMsgView) == null) {
            setMargins(view, i, i2, i3, i4);
        } else {
            textView.post(new Runnable() { // from class: com.miui.clock.classic.ClassicContentAreaView$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ClassicContentAreaView.$r8$lambda$SYBogu2HAEN9lMyYVy6Op2jq7wI(this.f$0, i4, view, i, i2, i3);
                }
            });
        }
    }

    public final void setViewMarginAndSize(final View view, final int i, final int i2, final int i3, final int i4, final int i5, final int i6) {
        if (view == null) {
            return;
        }
        if (FontUtils.isZhOrEn() || this.mBigMsgView == null || FontUtils.isBo()) {
            setMarginsAndSize(view, i, i2, i3, i4, i5, i6);
        } else {
            this.mBigMsgView.post(new Runnable() { // from class: com.miui.clock.classic.ClassicContentAreaView$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ClassicContentAreaView.$r8$lambda$Gh1tck627VPU40PHzAZTGttdHzA(this.f$0, i4, view, i, i2, i3, i5, i6);
                }
            });
        }
    }

    public final void setViewVisibility(View view, int i) {
        if (view != null) {
            view.setVisibility(i);
        }
    }

    public void updateColor(int i) {
        if (i == 0) {
            Log.d("ClassicClockView", "updated color is 0");
            return;
        }
        this.textCurrentColor = i;
        boolean z = false;
        boolean z2 = (!DeviceConfig.supportBackgroundBlur(this.mContext) || ClockEffectUtils.isSolidType(this.mClockInfo.getClockEffect()) || ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType())) ? false : true;
        if (!ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType()) && DeviceConfig.supportBackgroundBlur(this.mContext) && ClockEffectUtils.isBlurMixType(this.mClockInfo.getClockEffect())) {
            z = true;
        }
        int alphaComponent = z ? this.SECOND_COLOR_IN_BLUR_MIX_TYPE : ColorUtils.setAlphaComponent(i, 153);
        int alphaComponent2 = z ? this.NULL_WEATHER_IN_BLUR_MIX_TYPE : ColorUtils.setAlphaComponent(i, 77);
        setTextColor(this.mTimeView, i);
        if (ClassicContentStyle.Weather.isWeatherType(this.mType)) {
            WeatherBean weatherBean = this.mWeatherBean;
            int i2 = (weatherBean != null || this.isPreview) ? i : alphaComponent;
            if (this.mType == 400) {
                setTextColor(this.mBigMsgView, (weatherBean != null || this.isPreview) ? i : alphaComponent2);
            } else {
                setTextColor(this.mBigMsgView, i2);
            }
            updateDrawableColor(this.mRainIcon, i2);
            if (z2) {
                clearDrawableColor(this.mWeatherIcon);
            } else {
                updateDrawableColor(this.mWeatherIcon, i2);
            }
            updateDrawableColor(this.mWindIcon, i2);
            updateDrawableColor(this.mSomatosensoryIcon, i2);
            updateDrawableColor(this.mPressureIcon, i2);
        } else if (ClassicContentStyle.Health.isHealthType(this.mType)) {
            int i3 = noHealthData(this.mType) ? alphaComponent : i;
            updateDrawableColor(this.mTopHealthIcon, i3);
            setTextColor(this.mBigMsgView, i3);
            updateDrawableColor(this.mWindIcon, i);
            updateDrawableColor(this.mSomatosensoryIcon, i);
            updateDrawableColor(this.mPressureIcon, i);
        } else {
            setTextColor(this.mBigMsgView, i);
            updateDrawableColor(this.mWindIcon, i);
            updateDrawableColor(this.mSomatosensoryIcon, i);
            updateDrawableColor(this.mPressureIcon, i);
        }
        setTextColor(this.mRightTopMsg, alphaComponent);
        setTextColor(this.mRightTopRainMsg, alphaComponent);
        TextView textView = this.mRightTopMsgWeather;
        if (this.mWeatherBean != null || this.isPreview) {
            alphaComponent2 = alphaComponent;
        }
        setTextColor(textView, alphaComponent2);
        setTextColor(this.mRightBottomMsg, alphaComponent);
        setTextColor(this.mSomatosensoryMsg, alphaComponent);
        HealthMsgTextView healthMsgTextView = this.mHealthMsgTextView;
        if (healthMsgTextView != null) {
            healthMsgTextView.initColor(i, alphaComponent);
        }
        HumidityIconView humidityIconView = this.mHumidityIconView;
        if (humidityIconView != null) {
            humidityIconView.initColor(i, alphaComponent);
        }
        HealthMsgChartView healthMsgChartView = this.mHealthMsgChartView;
        if (healthMsgChartView != null) {
            healthMsgChartView.initColor(i, alphaComponent);
        }
        HealthMsgSleepChartView healthMsgSleepChartView = this.mHealthMsgSleepChartView;
        if (healthMsgSleepChartView != null) {
            healthMsgSleepChartView.initColor(i, alphaComponent);
        }
        SunMoveView sunMoveView = this.mSunMoveView;
        if (sunMoveView != null) {
            sunMoveView.initColor(i, alphaComponent);
        }
        TextView textView2 = this.mBigMsgView;
        if (textView2 != null) {
            setBigMsgText(textView2.getText().toString());
        }
    }

    public final void updateDrawableColor(ImageView imageView, int i) {
        Drawable drawable;
        if (imageView == null || (drawable = imageView.getDrawable()) == null || i == 0) {
            return;
        }
        drawable.setTint(i);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public void updateHealth(HealthBean healthBean) {
        String string;
        this.mHealthBean = healthBean;
        int i = this.mType;
        switch (i) {
            case 500:
                if (!noHealthData(i)) {
                    setText(this.mBigMsgView, healthBean.getStepCountNowString());
                    setText(this.mRightBottomMsg, this.mResources.getString(R.string.format_count_denominator, healthBean.getStepCountTargetString()));
                    string = getResources().getString(R.string.health_chart_step_count_schedule_format_desc, getResources().getQuantityString(R.plurals.health_chart_step_count_current_schedule_format_desc, this.mHealthBean.getStepCountNow(), this.mHealthBean.getStepCountNowString()), getResources().getQuantityString(R.plurals.health_chart_step_count_goal_schedule_format_desc, this.mHealthBean.getStepCountTarget(), this.mHealthBean.getStepCountTargetString()));
                } else {
                    setText(this.mBigMsgView, this.mPlaceholderText);
                    setText(this.mRightBottomMsg, this.mResources.getString(R.string.format_count_denominator, this.mPlaceholderText));
                    string = null;
                }
                break;
            case 501:
                if (!noHealthData(i)) {
                    initHealthMsgChartData(this.mHealthMsgChartView, this.mType, healthBean.getStepCountNow(), healthBean.getStepCountTarget(), null);
                    string = getResources().getString(R.string.health_chart_step_count_schedule_format_desc, getResources().getQuantityString(R.plurals.health_chart_step_count_current_schedule_format_desc, this.mHealthBean.getStepCountNow(), this.mHealthBean.getStepCountNowString()), getResources().getQuantityString(R.plurals.health_chart_step_count_goal_schedule_format_desc, this.mHealthBean.getStepCountTarget(), this.mHealthBean.getStepCountTargetString()));
                } else {
                    initHealthMsgChartData(this.mHealthMsgChartView, this.mType, -1, -1, null);
                    string = null;
                }
                break;
            case 502:
                if (!noHealthData(i)) {
                    setText(this.mBigMsgView, healthBean.getCaloriesNowString());
                    setText(this.mRightBottomMsg, this.mResources.getString(R.string.format_count_denominator, healthBean.getCaloriesTargetString()));
                    string = getResources().getString(R.string.health_chart_calories_schedule_format_desc, getResources().getQuantityString(R.plurals.health_chart_calories_burned_schedule_format_desc, this.mHealthBean.getCaloriesNow(), this.mHealthBean.getCaloriesNowString()), getResources().getQuantityString(R.plurals.health_chart_calories_goal_schedule_format_desc, this.mHealthBean.getCaloriesTarget(), this.mHealthBean.getCaloriesTargetString()));
                } else {
                    setText(this.mBigMsgView, this.mPlaceholderText);
                    setText(this.mRightBottomMsg, this.mResources.getString(R.string.format_count_denominator, this.mPlaceholderText));
                    string = null;
                }
                break;
            case 503:
                if (!noHealthData(i)) {
                    initHealthMsgChartData(this.mHealthMsgChartView, this.mType, healthBean.getCaloriesNow(), healthBean.getCaloriesTarget(), null);
                    string = getResources().getString(R.string.health_chart_calories_schedule_format_desc, getResources().getQuantityString(R.plurals.health_chart_calories_burned_schedule_format_desc, this.mHealthBean.getCaloriesNow(), this.mHealthBean.getCaloriesNowString()), getResources().getQuantityString(R.plurals.health_chart_calories_goal_schedule_format_desc, this.mHealthBean.getCaloriesTarget(), this.mHealthBean.getCaloriesTargetString()));
                } else {
                    initHealthMsgChartData(this.mHealthMsgChartView, this.mType, -1, -1, null);
                    string = null;
                }
                break;
            case 504:
                if (!noHealthData(i)) {
                    setText(this.mBigMsgView, healthBean.getStandCountNowString());
                    setText(this.mRightBottomMsg, this.mResources.getString(R.string.format_count_denominator, healthBean.getStandCountTargetString()));
                    string = getResources().getString(R.string.health_chart_stand_schedule_format_desc, getResources().getQuantityString(R.plurals.health_chart_stand_count_schedule_format_desc, this.mHealthBean.getStandCountNow(), this.mHealthBean.getStandCountNowString()), getResources().getQuantityString(R.plurals.health_chart_stand_goal_schedule_format_desc, this.mHealthBean.getStandCountTarget(), this.mHealthBean.getStandCountTargetString()));
                } else {
                    setText(this.mBigMsgView, this.mPlaceholderText);
                    setText(this.mRightBottomMsg, this.mResources.getString(R.string.format_count_denominator, this.mPlaceholderText));
                    string = null;
                }
                break;
            case 505:
                if (!noHealthData(i)) {
                    initHealthMsgChartData(this.mHealthMsgChartView, this.mType, healthBean.getSportTimeNow(), healthBean.getSportTimeTarget(), null);
                    string = getResources().getString(R.string.health_chart_sport_time_schedule_format_desc, getResources().getQuantityString(R.plurals.health_chart_sport_time_current_schedule_format_desc, this.mHealthBean.getSportTimeNow(), this.mHealthBean.getSportTimeNowString()), getResources().getQuantityString(R.plurals.health_chart_sport_time_goal_schedule_format_desc, this.mHealthBean.getSportTimeTarget(), this.mHealthBean.getSportTimeTargetString()));
                } else {
                    initHealthMsgChartData(this.mHealthMsgChartView, this.mType, -1, -1, null);
                    string = null;
                }
                break;
            case 506:
                if (!noHealthData(i)) {
                    initHealthMsgSleepChartData(this.mHealthMsgSleepChartView, healthBean.getSleepDurationMinute());
                    if (this.mHealthMsgSleepChartView != null) {
                        string = getResources().getString(R.string.health_sleep_desc, this.mHealthMsgSleepChartView.getSleepMinute(), this.mHealthMsgSleepChartView.getSleepDesc());
                    }
                } else {
                    initHealthMsgSleepChartData(this.mHealthMsgSleepChartView, -1);
                }
                string = null;
                break;
            case 507:
                if (!noHealthData(i)) {
                    initHealthMsgTextData(this.mHealthMsgTextView, healthBean.getCaloriesNow(), healthBean.getCaloriesTarget(), healthBean.getStepCountNow(), healthBean.getStepCountTarget(), healthBean.getSportTimeNow(), healthBean.getSportTimeTarget());
                    String string2 = getResources().getString(R.string.health_chart_step_count_schedule_format_desc, getResources().getQuantityString(R.plurals.health_chart_step_count_current_schedule_format_desc, this.mHealthBean.getStepCountNow(), this.mHealthBean.getStepCountNowString()), getResources().getQuantityString(R.plurals.health_chart_step_count_goal_schedule_format_desc, this.mHealthBean.getStepCountTarget(), this.mHealthBean.getStepCountTargetString()));
                    string = getResources().getString(R.string.health_chart_calories_schedule_format_desc, getResources().getQuantityString(R.plurals.health_chart_calories_burned_schedule_format_desc, this.mHealthBean.getCaloriesNow(), this.mHealthBean.getCaloriesNowString()), getResources().getQuantityString(R.plurals.health_chart_calories_goal_schedule_format_desc, this.mHealthBean.getCaloriesTarget(), this.mHealthBean.getCaloriesTargetString())) + ac.b + string2 + ac.b + getResources().getString(R.string.health_chart_sport_time_schedule_format_desc, getResources().getQuantityString(R.plurals.health_chart_sport_time_current_schedule_format_desc, this.mHealthBean.getSportTimeNow(), this.mHealthBean.getSportTimeNowString()), getResources().getQuantityString(R.plurals.health_chart_sport_time_goal_schedule_format_desc, this.mHealthBean.getSportTimeTarget(), this.mHealthBean.getSportTimeTargetString()));
                } else {
                    initHealthMsgTextData(this.mHealthMsgTextView, -1, -1, -1, -1, -1, -1);
                    string = null;
                }
                break;
            case 508:
                if (!noHealthData(i)) {
                    setText(this.mBigMsgView, healthBean.getSportTimeNowString());
                    setText(this.mRightBottomMsg, this.mResources.getString(R.string.format_count_denominator, healthBean.getSportTimeTargetString()));
                    string = getResources().getString(R.string.health_chart_sport_time_schedule_format_desc, getResources().getQuantityString(R.plurals.health_chart_sport_time_current_schedule_format_desc, this.mHealthBean.getSportTimeNow(), this.mHealthBean.getSportTimeNowString()), getResources().getQuantityString(R.plurals.health_chart_sport_time_goal_schedule_format_desc, this.mHealthBean.getSportTimeTarget(), this.mHealthBean.getSportTimeTargetString()));
                } else {
                    setText(this.mBigMsgView, this.mPlaceholderText);
                    setText(this.mRightBottomMsg, this.mResources.getString(R.string.format_count_denominator, this.mPlaceholderText));
                    string = null;
                }
                break;
            default:
                string = null;
                break;
        }
        if (string == null) {
            string = getResources().getString(R.string.health_no_data);
        }
        setContentDescription(string);
    }

    public void updateTime(boolean z) {
        String string;
        if (this.mType == 0 || this.mCalendar == null) {
            return;
        }
        this.mIs24HourFormat = z;
        boolean zEquals = Locale.CHINESE.getLanguage().equals(Locale.getDefault().getLanguage());
        boolean zEquals2 = Locale.ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage());
        boolean zEquals3 = Locale.getDefault().getLanguage().equals("et");
        if (ClassicContentStyle.Time.isTimeType(this.mType)) {
            setText(this.mTimeView, ClassicClockTimeUtils.getClassicTime(this.mContext, this.mCalendar, z, this.mType, false));
            setContentDescription(ClassicClockTimeUtils.getClassicTimeDesc(this.mContext, this.mCalendar, z, this.mType));
            return;
        }
        if (ClassicContentStyle.LargeDate.isLargeDateType(this.mType)) {
            int actualRenderStyle = this.mType;
            if (!zEquals) {
                actualRenderStyle = ClassicContentStyle.getActualRenderStyle(actualRenderStyle);
            }
            switch (actualRenderStyle) {
                case EaseManager.EaseStyleDef.PERLIN /* 201 */:
                    setBigMsgText(zEquals ? this.mResources.getString(R.string.format_chs_month_day, Integer.valueOf(ClassicClockTimeUtils.getMonthOfYearInt(this.mCalendar)), Integer.valueOf(ClassicClockTimeUtils.getDayOfMonthInt(this.mCalendar))) : this.mCalendar.format(this.mContext, this.mResources.getString(R.string.format_month_day_style1)).toUpperCase());
                    string = getResources().getString(R.string.format_month_day_desc, this.mCalendar.format(this.mContext, getResources().getString(R.string.format_full_month_of_year)), String.valueOf(ClassicClockTimeUtils.getDayOfMonthInt(this.mCalendar)));
                    break;
                case 202:
                    setText(this.mRightTopMsg, ClassicClockTimeUtils.getDayOfWeekString(this.mContext, this.mCalendar));
                    setBigMsgText(zEquals3 ? this.mCalendar.format(this.mContext, this.mResources.getString(R.string.format_month_day_style1)).toLowerCase() : DateFormatUtils.formatTime(this.mResources.getString(R.string.format_month_day_style2)).toUpperCase());
                    string = getResources().getString(R.string.format_month_day_week_desc, this.mCalendar.format(this.mContext, getResources().getString(R.string.format_full_month_of_year)), String.valueOf(ClassicClockTimeUtils.getDayOfMonthInt(this.mCalendar)), this.mCalendar.format(this.mContext, getResources().getString(R.string.format_full_day_of_week)));
                    break;
                case 203:
                    setText(this.mRightTopMsg, ClassicClockTimeUtils.getDayOfWeekString(this.mContext, this.mCalendar));
                    setBigMsgText(zEquals ? this.mResources.getString(R.string.format_chs_month_day, Integer.valueOf(ClassicClockTimeUtils.getMonthOfYearInt(this.mCalendar)), Integer.valueOf(ClassicClockTimeUtils.getDayOfMonthInt(this.mCalendar))) : this.mCalendar.format(this.mContext, this.mResources.getString(R.string.format_month_day_style1)).toUpperCase());
                    string = getResources().getString(R.string.format_month_day_week_desc, this.mCalendar.format(this.mContext, getResources().getString(R.string.format_full_month_of_year)), String.valueOf(ClassicClockTimeUtils.getDayOfMonthInt(this.mCalendar)), this.mCalendar.format(this.mContext, getResources().getString(R.string.format_full_day_of_week)));
                    break;
                case 204:
                    setBigMsgText(this.mCalendar.format(this.mContext, this.mResources.getString(R.string.format_week_day_style1)).toUpperCase());
                    Resources resources = this.mResources;
                    string = resources.getString(R.string.format_week_day_style1_desc, this.mCalendar.format(this.mContext, resources.getString(R.string.format_full_day_of_week)), String.valueOf(ClassicClockTimeUtils.getDayOfMonthInt(this.mCalendar)));
                    break;
                case 205:
                    setText(this.mRightTopMsg, ClassicClockTimeUtils.getDayOfWeekString(this.mContext, this.mCalendar));
                    String strValueOf = String.valueOf(ClassicClockTimeUtils.getDayOfMonthInt(this.mCalendar));
                    setText(this.mBigMsgView, strValueOf);
                    Resources resources2 = this.mResources;
                    string = resources2.getString(R.string.format_day_week_desc, strValueOf, this.mCalendar.format(this.mContext, resources2.getString(R.string.format_full_day_of_week)));
                    break;
                case 206:
                    setText(this.mBigMsgView, ClassicClockTimeUtils.getDayOfWeekString(this.mContext, this.mCalendar));
                    string = this.mCalendar.format(this.mContext, this.mResources.getString(R.string.classic_calendar_day_of_week_format));
                    break;
                case 207:
                    setText(this.mBigMsgView, zEquals ? this.mCalendar.format(this.mContext, this.mResources.getString(R.string.format_full_month_of_year)) : this.mCalendar.format(this.mContext, this.mResources.getString(R.string.classic_calendar_month_of_year_format)).toUpperCase());
                    string = this.mCalendar.format(this.mContext, this.mResources.getString(R.string.format_full_month_of_year));
                    break;
                case 208:
                    int dayOfMonthInt = ClassicClockTimeUtils.getDayOfMonthInt(this.mCalendar);
                    String string2 = this.mResources.getString(R.string.format_chs_day, ClassicClockTimeUtils.getChsNumber(dayOfMonthInt, false));
                    TextView textView = this.mBigMsgView;
                    if (!zEquals) {
                        string2 = String.valueOf(dayOfMonthInt);
                    }
                    setText(textView, string2);
                    setText(this.mRightTopMsg, zEquals2 ? ClassicClockTimeUtils.getEngOrder(dayOfMonthInt) : "");
                    string = this.mResources.getString(R.string.format_day_desc, String.valueOf(dayOfMonthInt));
                    break;
                case 209:
                    String lunarCalendarString = ClassicClockTimeUtils.getLunarCalendarString(this.mContext, this.mCalendar);
                    setText(this.mRightTopMsg, lunarCalendarString);
                    checkRightBottomMsg();
                    String solarTerms = ClassicClockTimeUtils.getSolarTerms(this.mContext, this.mCalendar);
                    if (TextUtils.isEmpty(solarTerms)) {
                        setViewVisibility(this.mRightBottomMsg, 8);
                        setText(this.mRightBottomMsg, "");
                    } else {
                        setViewVisibility(this.mRightBottomMsg, 0);
                        setText(this.mRightBottomMsg, solarTerms);
                    }
                    setBigMsgText(DateFormatUtils.formatTime(this.mResources.getString(R.string.format_month_day_style2)).toUpperCase());
                    Resources resources3 = this.mResources;
                    string = resources3.getString(R.string.format_month_day_lunar_calendar_month_day_desc, this.mCalendar.format(this.mContext, resources3.getString(R.string.format_full_month_of_year)), String.valueOf(ClassicClockTimeUtils.getDayOfMonthInt(this.mCalendar)), lunarCalendarString, solarTerms);
                    break;
                case 210:
                    setText(this.mRightTopMsg, ClassicClockTimeUtils.getDayOfWeekString(this.mContext, this.mCalendar));
                    String solarTerms2 = ClassicClockTimeUtils.getSolarTerms(this.mContext, this.mCalendar);
                    if (TextUtils.isEmpty(solarTerms2)) {
                        String lunarCalendarString2 = ClassicClockTimeUtils.getLunarCalendarString(this.mContext, this.mCalendar);
                        setText(this.mRightBottomMsg, lunarCalendarString2);
                        Resources resources4 = this.mResources;
                        string = resources4.getString(R.string.format_month_day_week_lunar_calendar_month_day_desc, this.mCalendar.format(this.mContext, resources4.getString(R.string.format_full_month_of_year)), String.valueOf(ClassicClockTimeUtils.getDayOfMonthInt(this.mCalendar)), this.mCalendar.format(this.mContext, getResources().getString(R.string.format_full_day_of_week)), lunarCalendarString2);
                    } else {
                        setText(this.mRightBottomMsg, solarTerms2);
                        Resources resources5 = this.mResources;
                        string = resources5.getString(R.string.format_month_day_week_solar_desc, this.mCalendar.format(this.mContext, resources5.getString(R.string.format_full_month_of_year)), String.valueOf(ClassicClockTimeUtils.getDayOfMonthInt(this.mCalendar)), this.mCalendar.format(this.mContext, getResources().getString(R.string.format_full_day_of_week)), solarTerms2);
                    }
                    setBigMsgText(DateFormatUtils.formatTime(this.mResources.getString(R.string.format_month_day_style2)).toUpperCase());
                    break;
                case 211:
                    String lunarCalendarString3 = ClassicClockTimeUtils.getLunarCalendarString(this.mContext, this.mCalendar);
                    checkRightTopMsg();
                    String solarTerms3 = ClassicClockTimeUtils.getSolarTerms(this.mContext, this.mCalendar);
                    if (!TextUtils.isEmpty(solarTerms3)) {
                        setViewVisibility(this.mRightTopMsg, 0);
                        setText(this.mRightTopMsg, lunarCalendarString3);
                        setText(this.mBigMsgView, solarTerms3);
                        string = this.mResources.getString(R.string.format_solar_lunar_calendar_month_day_desc, solarTerms3, lunarCalendarString3);
                    } else {
                        setViewVisibility(this.mRightTopMsg, 8);
                        setText(this.mRightTopMsg, "");
                        setText(this.mBigMsgView, lunarCalendarString3);
                        string = this.mResources.getString(R.string.format_lunar_calendar_month_day_desc, lunarCalendarString3);
                    }
                    break;
                case 212:
                    string = ClassicClockTimeUtils.getSexagenaryCycleString(this.mContext, this.mCalendar);
                    setText(this.mBigMsgView, string);
                    break;
                default:
                    setBigMsgText(zEquals3 ? this.mCalendar.format(this.mContext, this.mResources.getString(R.string.format_month_day_style1)).toLowerCase() : DateFormatUtils.formatTime(this.mResources.getString(R.string.format_month_day_style2)));
                    string = getResources().getString(R.string.format_month_day_desc, this.mCalendar.format(this.mContext, getResources().getString(R.string.format_full_month_of_year)), String.valueOf(ClassicClockTimeUtils.getDayOfMonthInt(this.mCalendar)));
                    break;
            }
            if (string != null) {
                setContentDescription(string);
            }
        }
    }

    public void updateTime(boolean z, int i) {
        MiuiTextGlassView miuiTextGlassView = this.mTimeView;
        if (miuiTextGlassView != null) {
            miuiTextGlassView.setTimeLayoutDirection(i);
        }
        TextView textView = this.mBigMsgView;
        if (textView != null) {
            textView.setTextDirection(i);
        }
        updateTime(z);
    }

    public void updateWeather(WeatherBean weatherBean) {
        ClassicContentAreaView classicContentAreaView;
        this.mWeatherBean = weatherBean;
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        int i = (this.mCalendar.get(18) * 60) + this.mCalendar.get(20);
        boolean z = true;
        String string = null;
        switch (this.mType) {
            case d.b /* 401 */:
                classicContentAreaView = this;
                if (classicContentAreaView.weatherNeedPlaceMsg() || !weatherBean.isAQIDateValid()) {
                    classicContentAreaView.setText(classicContentAreaView.mBigMsgView, classicContentAreaView.mPlaceholderText);
                } else {
                    classicContentAreaView.setText(classicContentAreaView.mBigMsgView, weatherBean.getAQILevel());
                    string = classicContentAreaView.getResources().getString(R.string.aqi_format_desc, weatherBean.getAQILevel());
                }
                break;
            case 402:
                classicContentAreaView = this;
                if (!classicContentAreaView.weatherNeedPlaceMsg()) {
                    classicContentAreaView.setText(classicContentAreaView.mBigMsgView, weatherBean.getRainProbability());
                    string = classicContentAreaView.getResources().getString(R.string.rain_probability_desc, NumberFormat.getPercentInstance().format(weatherBean.getRainProbabilityInt() / 100.0f));
                } else {
                    classicContentAreaView.setText(classicContentAreaView.mBigMsgView, classicContentAreaView.mPlaceholderText);
                }
                break;
            case 403:
            case d.c /* 404 */:
                if (!weatherNeedPlaceMsg()) {
                    classicContentAreaView = this;
                    classicContentAreaView.initSunMoveViewData(classicContentAreaView.mSunMoveView, weatherBean.getSunriseMinuteTime(), weatherBean.getSunriseTomorrowMinuteTime(), weatherBean.getSunsetMinuteTime(), i);
                    SunMoveView sunMoveView = classicContentAreaView.mSunMoveView;
                    if (sunMoveView != null) {
                        string = sunMoveView.getDesc();
                    }
                } else {
                    classicContentAreaView = this;
                    classicContentAreaView.initSunMoveViewData(this.mSunMoveView, -1, -1, -1, -1);
                }
                break;
            case 405:
                if (weatherNeedPlaceMsg()) {
                    setText(this.mBigMsgView, this.mPlaceholderText);
                    setText(this.mRightBottomMsg, "");
                } else {
                    setText(this.mBigMsgView, weatherBean.getUVILevel());
                    setText(this.mRightBottomMsg, this.mResources.getString(weatherBean.getUVILevelDescResID()));
                    string = getResources().getString(R.string.uvi_format_desc, weatherBean.getUVILevel(), getResources().getString(weatherBean.getUVILevelDescResID()));
                }
                classicContentAreaView = this;
                break;
            case 406:
                z = weatherBean != null && weatherBean.getFeelTemperatureValid();
                if (weatherNeedPlaceMsg() || !z) {
                    setImageResource(this.mSomatosensoryIcon, WeatherBean.getSomatosensoryEmptyResId(this.mStyle));
                    setText(this.mSomatosensoryMsg, getResources().getString(R.string.no_data_placeholder_string_with_temperature_unit));
                } else {
                    setImageResource(this.mSomatosensoryIcon, weatherBean.getSomatosensoryResId(this.mClockInfo.getStyle()));
                    setText(this.mSomatosensoryMsg, weatherBean.getSomatosensoryTemperatureWithUnit(getContext()));
                    string = getResources().getString(R.string.somatosensory_temperature_format, Integer.toString(weatherBean.getSomatosensoryTemperature()));
                    updateDrawableColor(this.mSomatosensoryIcon, this.mClockInfo.getClassicPlusSecondaryColor());
                }
                classicContentAreaView = this;
                break;
            case 407:
                if (weatherNeedPlaceMsg()) {
                    setText(this.mBigMsgView, this.mPlaceholderText);
                    setText(this.mRightTopMsg, "");
                    setImageResource(this.mWindIcon, R.drawable.classic_placeholder_icon);
                } else {
                    setText(this.mBigMsgView, weatherBean.getWindStrength());
                    setText(this.mRightTopMsg, this.mResources.getString(weatherBean.getWindDescResId()));
                    setImageResource(this.mWindIcon, weatherBean.getWindIconResId());
                    string = getResources().getString(R.string.wind_format, getResources().getString(weatherBean.getWindDescResIdFull()), weatherBean.getWindStrength());
                    updateDrawableColor(this.mWindIcon, this.mClockInfo.getClassicPlusSecondaryColor());
                }
                classicContentAreaView = this;
                break;
            case 408:
                if (weatherNeedPlaceMsg()) {
                    setHumidity(this.mHumidityIconView, -1);
                } else {
                    setHumidity(this.mHumidityIconView, weatherBean.getHumidity());
                    string = getResources().getString(R.string.humidity_format, NumberFormat.getPercentInstance().format(weatherBean.getHumidity() / 100.0f));
                }
                classicContentAreaView = this;
                break;
            case 409:
                if (weatherNeedPlaceMsg()) {
                    setText(this.mBigMsgView, this.mPlaceholderText);
                    setImageResource(this.mPressureIcon, R.drawable.classic_placeholder_icon);
                } else {
                    setText(this.mBigMsgView, weatherBean.getPressureString());
                    setImageResource(this.mPressureIcon, weatherBean.getPressureIconResId());
                    updateDrawableColor(this.mPressureIcon, this.mClockInfo.getClassicPlusSecondaryColor());
                    string = getResources().getQuantityString(R.plurals.atmospheric_pressure_desc, weatherBean.getPressure(), weatherBean.getPressureString());
                }
                classicContentAreaView = this;
                break;
            default:
                boolean z2 = DeviceConfig.supportBackgroundBlur(this.mContext) && this.mClockInfo.isAutoSecondaryColor() && this.mClockInfo.isAutoPrimaryColor() && !ClockStyleInfo.isAODType(this.mClockInfo.getDisplayType());
                boolean z3 = weatherBean != null && weatherBean.getTemperatureValid();
                if (weatherNeedPlaceMsg() || !z3) {
                    setText(this.mBigMsgView, getResources().getString(R.string.no_data_placeholder_single_line_string));
                    setImageResource(this.mWeatherIcon, R.drawable.classic_placeholder_icon);
                    setViewVisibility(this.mWeatherIcon, 8);
                } else {
                    this.mCalendar.setTimeInMillis(System.currentTimeMillis());
                    if (i >= weatherBean.getSunriseMinuteTime() && i <= weatherBean.getSunsetMinuteTime()) {
                        z = false;
                    }
                    setText(this.mBigMsgView, weatherBean.getTemperatureWithoutUnit());
                    setImageResource(this.mWeatherIcon, weatherBean.getIconResId(z, z2));
                    setViewVisibility(this.mWeatherIcon, 0);
                    string = getResources().getQuantityString(R.plurals.weather_temperature_desc, weatherBean.getTemperature(), weatherBean.getTemperatureWithoutUnit(), weatherBean.getDescription());
                }
                if (z2) {
                    clearDrawableColor(this.mWeatherIcon);
                } else {
                    updateDrawableColor(this.mWeatherIcon, this.mClockInfo.getClassicPlusSecondaryColor());
                }
                classicContentAreaView = this;
                break;
        }
        if (string == null) {
            string = classicContentAreaView.getResources().getString(R.string.weather_no_data);
        }
        classicContentAreaView.setContentDescription(string);
    }

    public final boolean weatherNeedPlaceMsg() {
        return this.mWeatherBean == null;
    }
}
