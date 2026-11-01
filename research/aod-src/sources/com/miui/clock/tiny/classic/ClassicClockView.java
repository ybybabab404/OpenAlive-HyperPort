package com.miui.clock.tiny.classic;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.miui.clock.tiny.R;
import com.miui.clock.tiny.TinyMiuiClockController;
import com.miui.clock.tiny.model.TinyClockInfo;
import com.miui.clock.tiny.model.TinyClockViewType;
import com.miui.clock.tiny.utils.DateFormatUtils;
import com.miui.clock.tiny.utils.FontUtils;
import com.miui.clock.tiny.utils.MiuiBlurUtils;
import com.miui.clock.tiny.widget.BatteryView;
import java.lang.ref.WeakReference;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class ClassicClockView extends LinearLayout implements TinyMiuiClockController.IClockView {
    public boolean m24HourFormat;
    public BatteryView mBatteryView;
    public Calendar mCalendar;
    public ClassicClockInfo mClassicClockInfo;
    public TextView mClockTime;
    public ContentObserver mContentObserver;
    public Context mContext;
    public TextView mDateWeek;
    public String mLanguage;
    public boolean registeredAdvancedTextureListener;
    public static final int DEFAULT_BLEND_DARK_COLOR = Color.parseColor("#FFFFFF");
    public static final int DEFAULT_AOD_PRIMARY_COLOR = Color.parseColor("#D7D7D7");

    /* JADX INFO: renamed from: com.miui.clock.tiny.classic.ClassicClockView$1, reason: invalid class name */
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
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.BATTERY.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.THIS.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public static class InnerContentObserver extends ContentObserver {
        public WeakReference mRef;

        public InnerContentObserver(Handler handler) {
            super(handler);
        }

        public InnerContentObserver(ClassicClockView classicClockView) {
            this(new Handler(Looper.getMainLooper()));
            this.mRef = new WeakReference(classicClockView);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            super.onChange(z);
            long jCurrentTimeMillis = System.currentTimeMillis();
            boolean z2 = (uri == null || uri.getPath() == null || !uri.getPath().endsWith("background_blur_enable")) ? false : true;
            ClassicClockView classicClockView = (ClassicClockView) this.mRef.get();
            if (z2) {
                classicClockView.onAdvancedTextureChanged();
            }
            Log.d("TinyMiuiClockView", "Advanced Texture on Changed: during = " + (System.currentTimeMillis() - jCurrentTimeMillis));
        }
    }

    public ClassicClockView(Context context) {
        this(context, null);
    }

    public ClassicClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    private int getDimen(int i) {
        return (int) (((double) (this.mContext.getResources().getDimensionPixelSize(i) * this.mClassicClockInfo.getScale())) + 0.5d);
    }

    public static int isBackgroundBlurEnabled(Context context) {
        if (!DateFormatUtils.isSystemUI(context)) {
            return Settings.Secure.getInt(context.getContentResolver(), "background_blur_enable", 0);
        }
        try {
            int iUpdateCurrentUserId = DateFormatUtils.updateCurrentUserId();
            Class cls = Integer.TYPE;
            return ((Integer) Settings.Secure.class.getMethod("getIntForUser", ContentResolver.class, String.class, cls, cls).invoke(null, context.getContentResolver(), "background_blur_enable", 0, Integer.valueOf(iUpdateCurrentUserId))).intValue();
        } catch (Exception e) {
            Log.e("TinyMiuiClockView", "getIntForUser fail", e);
            return 0;
        }
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public Calendar getClockCalendar() {
        return this.mCalendar;
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public TinyClockInfo getClockStyleInfo() {
        return this.mClassicClockInfo;
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public View getIClockView(TinyClockViewType tinyClockViewType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[tinyClockViewType.ordinal()];
        if (i == 1) {
            return this.mDateWeek;
        }
        if (i != 2) {
            return i != 3 ? this : this.mBatteryView;
        }
        return this.mClockTime;
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public int getMarginAOD() {
        return this.mClassicClockInfo.getRotation() == 2 ? this.mContext.getResources().getDimensionPixelSize(R.dimen.tiny_classic_date_week_margin_start_aod_180) : this.mContext.getResources().getDimensionPixelSize(R.dimen.tiny_classic_date_week_margin_end_aod_0);
    }

    public void init(Context context) {
        this.mContext = context;
        this.mCalendar = new Calendar();
        this.mLanguage = this.mContext.getResources().getConfiguration().locale.getLanguage();
    }

    public final void initLayoutParams() {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mDateWeek.getLayoutParams();
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mClockTime.getLayoutParams();
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mBatteryView.getLayoutParams();
        int rotation = this.mClassicClockInfo.getRotation();
        boolean zIsTibetan = FontUtils.isTibetan(this.mContext);
        this.mDateWeek.setTextSize(0, getDimen(R.dimen.tiny_classic_date_week_text_size));
        this.mClockTime.setTextSize(0, getDimen(R.dimen.tiny_classic_clock_time_size));
        this.mBatteryView.setScale(this.mClassicClockInfo.getScale());
        if (this.mClassicClockInfo.isAODType()) {
            if (rotation == 2) {
                layoutParams.setMarginStart(getDimen(R.dimen.tiny_classic_date_week_margin_start_aod_180));
                layoutParams.topMargin = getDimen(R.dimen.tiny_classic_date_week_margin_top_aod_180);
                layoutParams2.setMarginStart(getDimen(R.dimen.tiny_classic_clock_time_margin_start_aod_180));
                layoutParams2.topMargin = getDimen(!zIsTibetan ? R.dimen.tiny_classic_clock_time_margin_top_aod_180 : R.dimen.tiny_classic_clock_time_margin_top_aod_for_tibetan_180);
                layoutParams3.topMargin = getDimen(R.dimen.tiny_classic_battery_text_margin_top_aod_180);
                layoutParams3.setMarginStart(getDimen(R.dimen.tiny_classic_battery_text_margin_start_aod_180));
                return;
            }
            layoutParams.setMarginEnd(getDimen(R.dimen.tiny_classic_date_week_margin_end_aod_0));
            layoutParams.topMargin = getDimen(R.dimen.tiny_classic_date_week_margin_top_aod_0);
            layoutParams2.setMarginEnd(getDimen(R.dimen.tiny_classic_clock_time_margin_end_aod_0));
            layoutParams2.topMargin = getDimen(!zIsTibetan ? R.dimen.tiny_classic_clock_time_margin_top_aod_0 : R.dimen.tiny_classic_clock_time_margin_top_aod_for_tibetan_0);
            layoutParams3.topMargin = getDimen(R.dimen.tiny_classic_battery_text_margin_top_aod_0);
            layoutParams3.setMarginEnd(getDimen(R.dimen.tiny_classic_battery_text_margin_end_aod_0));
            return;
        }
        if (rotation == 2) {
            layoutParams.setMarginStart(getDimen(R.dimen.tiny_classic_date_week_margin_start_180));
            layoutParams.topMargin = getDimen(R.dimen.tiny_classic_date_week_margin_top_180);
            layoutParams2.setMarginStart(getDimen(R.dimen.tiny_classic_clock_time_margin_start_180));
            layoutParams2.topMargin = getDimen(!zIsTibetan ? R.dimen.tiny_classic_clock_time_margin_top_180 : R.dimen.tiny_classic_clock_time_margin_top_for_tibetan_180);
            layoutParams3.topMargin = getDimen(R.dimen.tiny_classic_battery_text_margin_top_180);
            layoutParams3.setMarginStart(getDimen(R.dimen.tiny_classic_battery_text_margin_start_180));
            return;
        }
        layoutParams.setMarginEnd(getDimen(R.dimen.tiny_classic_date_week_margin_end_0));
        layoutParams.topMargin = getDimen(R.dimen.tiny_classic_date_week_margin_top_0);
        layoutParams2.setMarginEnd(getDimen(R.dimen.tiny_classic_clock_time_margin_end_0));
        layoutParams2.topMargin = getDimen(!zIsTibetan ? R.dimen.tiny_classic_clock_time_margin_top_0 : R.dimen.tiny_classic_clock_time_margin_top_for_tibetan_0);
        layoutParams3.topMargin = getDimen(R.dimen.tiny_classic_battery_text_margin_top_0);
        layoutParams3.setMarginEnd(getDimen(R.dimen.tiny_classic_battery_text_margin_end_0));
    }

    public void onAdvancedTextureChanged() {
        ClassicClockInfo classicClockInfo = this.mClassicClockInfo;
        if (classicClockInfo == null || !this.registeredAdvancedTextureListener) {
            return;
        }
        setClockStyleInfo(classicClockInfo);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        ClassicClockInfo classicClockInfo;
        super.onAttachedToWindow();
        if (this.registeredAdvancedTextureListener || (classicClockInfo = this.mClassicClockInfo) == null || classicClockInfo.getType() != 0) {
            return;
        }
        this.registeredAdvancedTextureListener = true;
        this.mContentObserver = new InnerContentObserver(this);
        getContext().getContentResolver().registerContentObserver(Settings.Secure.getUriFor("background_blur_enable"), true, this.mContentObserver);
        Log.d("TinyMiuiClockView", "Classic Clock View has registered ContentObserver");
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        String language = configuration.locale.getLanguage();
        if (TextUtils.isEmpty(language) || language.equals(this.mLanguage)) {
            return;
        }
        this.mLanguage = language;
        onLanguageChanged();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.registeredAdvancedTextureListener && this.mClassicClockInfo.getType() == 0) {
            this.registeredAdvancedTextureListener = false;
            getContext().getContentResolver().unregisterContentObserver(this.mContentObserver);
            Log.d("TinyMiuiClockView", "Classic Clock View unregistered ContentObserver");
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mDateWeek = (TextView) findViewById(R.id.date_week);
        this.mClockTime = (TextView) findViewById(R.id.clock_time);
        this.mBatteryView = (BatteryView) findViewById(R.id.classic_battery);
    }

    public void onLanguageChanged() {
        ClassicClockInfo classicClockInfo = this.mClassicClockInfo;
        if (classicClockInfo != null && classicClockInfo.getScale() > 0.0f) {
            initLayoutParams();
        }
        updateTime();
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
        if (tinyClockInfo instanceof ClassicClockInfo) {
            this.mClassicClockInfo = (ClassicClockInfo) tinyClockInfo;
        }
        ClassicClockInfo classicClockInfo = this.mClassicClockInfo;
        if (classicClockInfo == null) {
            return;
        }
        if (classicClockInfo.getType() == 2 || this.mClassicClockInfo.getType() == 3 || this.mClassicClockInfo.getType() == 4) {
            this.mBatteryView.setNeedShowNormal(true);
            if (this.mClassicClockInfo.getScale() <= 0.0f || Math.abs(this.mClassicClockInfo.getScale() - 1.0f) <= 0.001f) {
                this.mClassicClockInfo.setScale(1.0f);
            } else {
                initLayoutParams();
            }
        }
        if (FontUtils.isTibetan(this.mContext)) {
            initLayoutParams();
        }
        int iIsBackgroundBlurEnabled = isBackgroundBlurEnabled(this.mContext);
        MiuiBlurUtils.clearMemberBlendColor(this.mDateWeek);
        MiuiBlurUtils.clearContainerPassBlur(this.mDateWeek);
        MiuiBlurUtils.clearMemberBlendColor(this.mClockTime);
        MiuiBlurUtils.clearContainerPassBlur(this.mClockTime);
        this.mBatteryView.clearBatteryViewBlendColor();
        this.mBatteryView.clearBatteryViewPassBlur();
        if (iIsBackgroundBlurEnabled != 1 || tinyClockInfo.getType() == 1) {
            this.mBatteryView.setNeedBlur(false);
            this.mDateWeek.setTextColor(tinyClockInfo.isAODType() ? DEFAULT_AOD_PRIMARY_COLOR : this.mClassicClockInfo.getPrimaryColor());
            this.mClockTime.setTextColor(tinyClockInfo.isAODType() ? DEFAULT_AOD_PRIMARY_COLOR : this.mClassicClockInfo.getPrimaryColor());
            this.mBatteryView.setBatteryColor(tinyClockInfo.isAODType() ? DEFAULT_AOD_PRIMARY_COLOR : this.mClassicClockInfo.getPrimaryColor());
        } else {
            this.mBatteryView.setNeedBlur(true);
            TextView textView = this.mDateWeek;
            int i = DEFAULT_BLEND_DARK_COLOR;
            textView.setTextColor(i);
            this.mClockTime.setTextColor(i);
            this.mBatteryView.setBatteryColor(i);
            int blendColor = this.mClassicClockInfo.getBlendColor();
            MiuiBlurUtils.setContainerPassBlur(this, this.mContext.getResources().getDimensionPixelSize(R.dimen.tiny_classic_blur_radius));
            boolean zIsWallpaperDark = true ^ this.mClassicClockInfo.isWallpaperDark();
            MiuiBlurUtils.setMemberBlendColors(this.mDateWeek, zIsWallpaperDark, blendColor, i);
            MiuiBlurUtils.setMemberBlendColors(this.mClockTime, zIsWallpaperDark, blendColor, i);
            this.mBatteryView.setViewBlur(zIsWallpaperDark, blendColor, i);
        }
        this.mDateWeek.setTypeface(FontUtils.getMiSans(380));
        this.mClockTime.setTypeface(FontUtils.getMiSans(305));
        updateTime();
        Log.d("TinyMiuiClockView", "ClassicClockView isBackgroundBlurEnable = " + iIsBackgroundBlurEnabled + " battery=[" + this.mBatteryView + "]");
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void updateTime() {
        this.m24HourFormat = DateFormatUtils.is24HourFormat(this.mContext);
        this.mCalendar.setTimeInMillis(System.currentTimeMillis());
        TextView textView = this.mDateWeek;
        Calendar calendar = this.mCalendar;
        Context context = this.mContext;
        textView.setText(calendar.format(context, context.getString(R.string.tiny_classic_date_format)));
        if (this.m24HourFormat) {
            TextView textView2 = this.mClockTime;
            Calendar calendar2 = this.mCalendar;
            Context context2 = this.mContext;
            textView2.setText(calendar2.format(context2, context2.getString(R.string.tiny_clock_time_format_24)));
            return;
        }
        TextView textView3 = this.mClockTime;
        Calendar calendar3 = this.mCalendar;
        Context context3 = this.mContext;
        textView3.setText(calendar3.format(context3, context3.getString(R.string.tiny_clock_time_format_12)));
    }

    @Override // com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void updateTimeZone(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mCalendar.setTimeZone(TimeZone.getTimeZone(str));
    }
}
