package com.miui.clock;

import android.content.Context;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.provider.Settings;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.miui.clock.allInOne.AllInOneClockStyle;
import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.module.ClockViewType;
import com.miui.clock.module.MiuiDualClockInfo;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;
import miuix.pickerwidget.date.DateUtils;

/* JADX INFO: loaded from: classes.dex */
public class MiuiDualClock extends RelativeLayout implements MiuiClockController.IClockView {
    public boolean m24HourFormat;
    public boolean mAttached;
    public boolean mAutoTimeZone;
    public ContentObserver mAutoTimeZoneObserver;
    public Calendar mCalendar;
    public Configuration mConfiguration;
    public Context mContext;
    public String mCountry;
    public boolean mDateTooLong;
    public MiuiDualClockInfo mInfo;
    public String mLanguage;
    public TextView mLocalCity;
    public OnLocalCityChangeListener mLocalCityChangeListener;
    public TextView mLocalDate;
    public TextView mLocalTime;
    public String mLocalTimeZone;
    public Calendar mResidentCalendar;
    public TextView mResidentCity;
    public TextView mResidentDate;
    public LinearLayout mResidentLayout;
    public TextView mResidentTime;
    public String mResidentTimeZone;
    public float mScaleRatio;

    public static class GetZoneNameTask extends AsyncTask<Void, Void, String> {
        public OnGetZoneNameCompleteListener mCompleteCallback;
        public WeakReference mContextRef;
        public String mUri;

        public GetZoneNameTask(String str, Context context, OnGetZoneNameCompleteListener onGetZoneNameCompleteListener) {
            this.mUri = str;
            this.mContextRef = new WeakReference(context);
            this.mCompleteCallback = onGetZoneNameCompleteListener;
        }

        @Override // android.os.AsyncTask
        public String doInBackground(Void... voidArr) {
            String string = "";
            Cursor cursorQuery = null;
            try {
                try {
                    if (this.mContextRef.get() != null) {
                        cursorQuery = ((Context) this.mContextRef.get()).getContentResolver().query(Uri.parse(this.mUri), null, null, null, null);
                        if (cursorQuery != null) {
                            while (cursorQuery.moveToNext()) {
                                if (cursorQuery.getColumnIndex("zone_timename") >= 0) {
                                    string = cursorQuery.getString(cursorQuery.getColumnIndex("zone_timename"));
                                }
                                if (TextUtils.isEmpty(string) && cursorQuery.getColumnIndex("zone_displayname") >= 0) {
                                    string = cursorQuery.getString(cursorQuery.getColumnIndex("zone_displayname"));
                                }
                            }
                        }
                        Log.i("MiuiDualClock", "get city name: " + string + ", from: " + this.mUri);
                    } else {
                        Log.i("MiuiDualClock", "get city name: context is null");
                    }
                    if (cursorQuery != null) {
                        cursorQuery.close();
                    }
                    return string;
                } catch (Exception e) {
                    Log.e("MiuiDualClock", "get city from " + this.mUri + " exception", e);
                    if (0 != 0) {
                        cursorQuery.close();
                    }
                    return "";
                }
            } catch (Throwable th) {
                if (0 == 0) {
                    throw th;
                }
                cursorQuery.close();
                throw th;
            }
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(String str) {
            OnGetZoneNameCompleteListener onGetZoneNameCompleteListener = this.mCompleteCallback;
            if (onGetZoneNameCompleteListener != null) {
                onGetZoneNameCompleteListener.onComplete(str);
            }
        }
    }

    public interface OnGetZoneNameCompleteListener {
        void onComplete(String str);
    }

    public interface OnLocalCityChangeListener {
        void onLocalCityChanged(String str);
    }

    /* JADX INFO: renamed from: $r8$lambda$Wh-O7bJNuu4Q43z9Hej6EIqn6Rc, reason: not valid java name */
    public static /* synthetic */ void m647$r8$lambda$WhO7bJNuu4Q43z9Hej6EIqn6Rc(MiuiDualClock miuiDualClock, String str) {
        TextView textView = miuiDualClock.mResidentCity;
        if (textView != null) {
            if (TextUtils.isEmpty(str)) {
                str = miuiDualClock.mContext.getString(R.string.miui_clock_city_name_second);
            }
            textView.setText(str);
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$xh2gEaYmBqKr-TQz10unT2rY1H0, reason: not valid java name */
    public static /* synthetic */ void m648$r8$lambda$xh2gEaYmBqKrTQz10unT2rY1H0(MiuiDualClock miuiDualClock, String str) {
        TextView textView = miuiDualClock.mLocalCity;
        if (textView != null) {
            textView.setText(TextUtils.isEmpty(str) ? miuiDualClock.mContext.getString(R.string.miui_clock_city_name_local) : str);
        }
        OnLocalCityChangeListener onLocalCityChangeListener = miuiDualClock.mLocalCityChangeListener;
        if (onLocalCityChangeListener != null) {
            onLocalCityChangeListener.onLocalCityChanged(str);
        }
    }

    public MiuiDualClock(Context context) {
        this(context, null);
    }

    public MiuiDualClock(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mLanguage = "";
        this.mCountry = Locale.getDefault().getCountry();
        this.mAutoTimeZone = true;
        this.mDateTooLong = false;
        this.mScaleRatio = 1.0f;
        this.mContext = context;
        this.mAutoTimeZone = Settings.Global.getInt(context.getContentResolver(), "auto_time_zone", 0) > 0;
        this.mConfiguration = new Configuration(context.getResources().getConfiguration());
    }

    private int getDimen(int i) {
        return (int) (getResources().getDimensionPixelSize(i) * DeviceConfig.calculateScale(getContext()));
    }

    public final ContentObserver createTimeZoneObserve() {
        return new ContentObserver(new Handler()) { // from class: com.miui.clock.MiuiDualClock.1
            @Override // android.database.ContentObserver
            public void onChange(boolean z) {
                super.onChange(z);
                MiuiDualClock miuiDualClock = MiuiDualClock.this;
                miuiDualClock.mAutoTimeZone = Settings.Global.getInt(miuiDualClock.mContext.getContentResolver(), "auto_time_zone", 0) > 0;
                MiuiDualClock.this.updateLocalCity();
            }
        };
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ View getBackgroundBlurContainer() {
        return super.getBackgroundBlurContainer();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public int getClockHeight() {
        return getHeight();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ ClockStyleInfo getClockStyleInfo() {
        return super.getClockStyleInfo();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public float getClockVisibleHeight() {
        return getHeight();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ Rect getDepthAvoidRect() {
        return super.getDepthAvoidRect();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getGalleryGravity() {
        return super.getGalleryGravity();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientAnimationParams() {
        return super.getGradientAnimationParams();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ float[] getGradientParams() {
        return super.getGradientParams();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getHealthJson() {
        return super.getHealthJson();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public View getIClockView(ClockViewType clockViewType) {
        if (clockViewType == ClockViewType.ALL_VIEW) {
            return this;
        }
        return null;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public String getLocalCity() {
        TextView textView = this.mLocalCity;
        return textView != null ? textView.getText().toString() : this.mContext.getString(R.string.miui_clock_city_name_local);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getMagazineColor() {
        return super.getMagazineColor();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockBottom() {
        return getBottom() + getResources().getDimensionPixelSize(R.dimen.notification_margin_top);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public int getNotificationClockTop() {
        return getDimen(R.dimen.miui_dual_clock_margin_top);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getNotificationRelativePosition() {
        return super.getNotificationRelativePosition();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ int getTextViewSize() {
        return super.getTextViewSize();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ TextView getTimeView() {
        return super.getTimeView();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getTimeZone() {
        return super.getTimeZone();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public float getTopMargin() {
        return getDimen(R.dimen.miui_dual_clock_margin_top);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ String getWeatherJson() {
        return super.getWeatherJson();
    }

    @Override // android.view.View
    public boolean hasOverlappingRendering() {
        return false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.mAttached) {
            return;
        }
        this.mAttached = true;
        if (this.mAutoTimeZoneObserver == null) {
            this.mAutoTimeZoneObserver = createTimeZoneObserve();
        }
        this.mContext.getContentResolver().registerContentObserver(Settings.Global.getUriFor("auto_time_zone"), false, this.mAutoTimeZoneObserver);
        this.mAutoTimeZoneObserver.onChange(false);
        updateViewsLayoutParams();
        updateViewsTextSize();
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        String language = configuration.locale.getLanguage();
        String country = configuration.locale.getCountry();
        if ((language != null && !language.equals(this.mLanguage)) || (country != null && !country.equals(this.mCountry))) {
            updateResidentCityName();
            updateLocalCity();
            this.mDateTooLong = false;
            updateTime();
            updateDateLines();
            this.mLanguage = language;
            this.mCountry = country;
        }
        if (DeviceConfig.isFlip()) {
            updateViewsTextSize();
        }
        if ((this.mConfiguration.updateFrom(configuration) & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0) {
            updateViewsLayoutParams();
            updateViewsTextSize();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.mAttached) {
            this.mAttached = false;
            this.mContext.getContentResolver().unregisterContentObserver(this.mAutoTimeZoneObserver);
        }
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mLocalCity = (TextView) findViewById(R.id.local_city_name);
        TextView textView = (TextView) findViewById(R.id.local_time);
        this.mLocalTime = textView;
        textView.setAccessibilityDelegate(new MiuiClockAccessibilityDelegate(this.mContext));
        this.mLocalDate = (TextView) findViewById(R.id.local_date);
        this.mResidentCity = (TextView) findViewById(R.id.resident_city_name);
        TextView textView2 = (TextView) findViewById(R.id.resident_time);
        this.mResidentTime = textView2;
        textView2.setAccessibilityDelegate(new MiuiClockAccessibilityDelegate(this.mContext));
        this.mResidentDate = (TextView) findViewById(R.id.resident_date);
        this.mResidentLayout = (LinearLayout) findViewById(R.id.resident_time_layout);
        this.mLanguage = this.mContext.getResources().getConfiguration().locale.getLanguage();
        TimeZone timeZone = TimeZone.getDefault();
        this.mLocalTimeZone = timeZone.getID();
        updateLocalCity();
        this.mCalendar = new Calendar();
        if (TextUtils.isEmpty(this.mResidentTimeZone)) {
            this.mResidentTimeZone = timeZone.getID();
        }
        updateResidentCityName();
        this.mResidentCalendar = new Calendar(TimeZone.getTimeZone(this.mResidentTimeZone));
        updateHourFormat();
        updateTime();
        updateDateLines();
    }

    @Override // android.view.View
    public void onWindowVisibilityChanged(int i) {
        boolean zIs24HourFormat;
        super.onWindowVisibilityChanged(i);
        if (i != 0 || (zIs24HourFormat = DateFormatUtils.is24HourFormat(this.mContext)) == this.m24HourFormat) {
            return;
        }
        this.m24HourFormat = zIs24HourFormat;
        updateTime();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setAllInOneClockRectParams(AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, boolean z, Boolean bool) {
        super.setAllInOneClockRectParams(region, f, f2, i, i2, i3, z, bool);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setBackgroundBlurContainer(View view) {
        super.setBackgroundBlurContainer(view);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setClockAlpha(float f) {
        setAlpha(f);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setClockPalette(int i, boolean z, Map<String, Integer> map, boolean z2) {
        Log.d("ClockPalette", "Dualclock setClockPalette: textDark = " + z);
        setTextColorDark(z);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setClockStyleInfo(ClockStyleInfo clockStyleInfo) {
        MiuiDualClockInfo miuiDualClockInfo = (MiuiDualClockInfo) clockStyleInfo;
        this.mInfo = miuiDualClockInfo;
        setLocalCity(miuiDualClockInfo.getLocalCity());
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setClockViewRect(Rect rect) {
        super.setClockViewRect(rect);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setCurrentUserId(int i) {
        super.setCurrentUserId(i);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditMode(boolean z) {
        super.setEditMode(z);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonShow(boolean z) {
        super.setEditorColonShow(z);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setEditorColonViewVisible(boolean z) {
        super.setEditorColonViewVisible(z);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGpuOptimize(boolean z) {
        super.setGpuOptimize(z);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setGradientIndex(int i) {
        super.setGradientIndex(i);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setInfoTextColorDark(boolean z) {
        super.setInfoTextColorDark(z);
    }

    public void setIs24HourFormat(boolean z) {
        this.m24HourFormat = z;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setLocalCity(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mLocalCity.setText(str);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setMagazineInfoVisible(boolean z) {
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setMinuteColonAlpha(float f) {
        super.setMinuteColonAlpha(f);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    @Deprecated
    public /* bridge */ /* synthetic */ void setMinuteColor(int i, int i2) {
        super.setMinuteColor(i, i2);
    }

    public void setOnLocalCityChangeListener(OnLocalCityChangeListener onLocalCityChangeListener) {
        this.mLocalCityChangeListener = onLocalCityChangeListener;
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setOwnerInfo(String str) {
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setScaleRatio(float f) {
        this.mScaleRatio = f;
        updateViewsTextSize();
        updateViewsLayoutParams();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setShowLunarCalendar(boolean z) {
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setSuperSaveOpen(boolean z) {
        super.setSuperSaveOpen(z);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void setTextColorDark(boolean z) {
        Log.d("ClockPalette", "Dualclock setTextColorDark: textDark = " + z);
        int color = z ? getContext().getResources().getColor(R.color.miui_common_time_dark_text_color) : -1;
        this.mLocalCity.setTextColor(color);
        this.mLocalTime.setTextColor(color);
        this.mLocalDate.setTextColor(color);
        this.mResidentCity.setTextColor(color);
        this.mResidentTime.setTextColor(color);
        this.mResidentDate.setTextColor(color);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public /* bridge */ /* synthetic */ void setWallpaperSupportDepth(boolean z) {
        super.setWallpaperSupportDepth(z);
    }

    public final void updateDateLines() {
        int dimen = getDimen(R.dimen.miui_dual_clock_city_text_size);
        TextPaint textPaint = new TextPaint(this.mLocalDate.getPaint());
        TextPaint textPaint2 = new TextPaint(this.mResidentDate.getPaint());
        float f = dimen;
        textPaint2.setTextSize(f);
        textPaint.setTextSize(f);
        boolean z = ((int) textPaint.measureText(this.mLocalDate.getText().toString())) + ((int) textPaint2.measureText(this.mResidentDate.getText().toString())) > getDimen(R.dimen.miui_dual_clock_max_width) * 2;
        if (z != this.mDateTooLong) {
            this.mDateTooLong = z;
            updateTime();
        }
    }

    public void updateHourFormat() {
        this.m24HourFormat = DateFormatUtils.is24HourFormat(this.mContext);
    }

    public final void updateLocalCity() {
        new GetZoneNameTask("content://com.android.settings.provider.MiuiSettingsDataProvider/zone_info", this.mContext, new OnGetZoneNameCompleteListener() { // from class: com.miui.clock.MiuiDualClock$$ExternalSyntheticLambda0
            @Override // com.miui.clock.MiuiDualClock.OnGetZoneNameCompleteListener
            public final void onComplete(String str) {
                MiuiDualClock.m648$r8$lambda$xh2gEaYmBqKrTQz10unT2rY1H0(this.f$0, str);
            }
        }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    public final void updateResidentCityName() {
        new GetZoneNameTask("content://com.android.settings.provider.MiuiSettingsDataProvider/dual_zone_info", this.mContext, new OnGetZoneNameCompleteListener() { // from class: com.miui.clock.MiuiDualClock$$ExternalSyntheticLambda1
            @Override // com.miui.clock.MiuiDualClock.OnGetZoneNameCompleteListener
            public final void onComplete(String str) {
                MiuiDualClock.m647$r8$lambda$WhO7bJNuu4Q43z9Hej6EIqn6Rc(this.f$0, str);
            }
        }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateResidentTimeZone(String str) {
        if (TextUtils.isEmpty(str) || str.equals(this.mResidentTimeZone)) {
            Log.i("MiuiDualClock", "dispense with update resident timeZone :" + str);
            if (TextUtils.isEmpty(str)) {
                return;
            }
            updateResidentCityName();
            return;
        }
        this.mResidentTimeZone = str;
        Log.i("MiuiDualClock", "update resident timeZone:" + this.mResidentTimeZone);
        this.mResidentCalendar = new Calendar(TimeZone.getTimeZone(this.mResidentTimeZone));
        updateTime();
        updateDateLines();
        updateResidentCityName();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateTime() {
        updateTime(this.mCalendar, this.mLocalTime, this.mLocalDate);
        updateTime(this.mResidentCalendar, this.mResidentTime, this.mResidentDate);
    }

    public void updateTime(Calendar calendar, TextView textView, TextView textView2) {
        int i;
        calendar.setTimeInMillis(System.currentTimeMillis());
        String dateTime = DateUtils.formatDateTime(this.mContext, System.currentTimeMillis(), (this.m24HourFormat ? 32 : 16) | 76, calendar.getTimeZone());
        Log.d("MiuiDualClock", "miui dual clock time update: " + dateTime);
        textView.setText(dateTime);
        if (this.mDateTooLong) {
            i = this.m24HourFormat ? R.string.miui_lock_screen_date_two_lines : R.string.miui_lock_screen_date_two_lines_12;
        } else {
            i = this.m24HourFormat ? R.string.miui_lock_screen_date : R.string.miui_lock_screen_date_12;
        }
        Context context = this.mContext;
        textView2.setText(calendar.format(context, context.getString(i)));
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateTimeZone(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        this.mLocalTimeZone = str;
        Log.i("MiuiDualClock", "update local timeZone:" + this.mLocalTimeZone);
        this.mCalendar = new Calendar(TimeZone.getTimeZone(this.mLocalTimeZone));
        updateTime();
        updateDateLines();
        updateLocalCity();
    }

    @Override // com.miui.clock.MiuiClockController.IClockView
    public void updateViewTopMargin(boolean z) {
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        if (z) {
            layoutParams.topMargin = (int) (this.mScaleRatio * getDimen(R.dimen.miui_dual_clock_margin_top));
        } else {
            layoutParams.topMargin = 0;
        }
        setLayoutParams(layoutParams);
    }

    public void updateViewsLayoutParams() {
        this.mContext.getResources();
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) getLayoutParams();
        layoutParams.topMargin = (int) (this.mScaleRatio * getDimen(R.dimen.miui_dual_clock_margin_top));
        setLayoutParams(layoutParams);
        int dimen = (int) (this.mScaleRatio * getDimen(R.dimen.miui_dual_clock_time_margin_top));
        int dimen2 = (int) (this.mScaleRatio * getDimen(R.dimen.miui_dual_clock_date_margin_top));
        LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mLocalTime.getLayoutParams();
        layoutParams2.topMargin = dimen;
        this.mLocalTime.setLayoutParams(layoutParams2);
        LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) this.mLocalDate.getLayoutParams();
        layoutParams3.topMargin = dimen2;
        this.mLocalDate.setLayoutParams(layoutParams3);
        LinearLayout.LayoutParams layoutParams4 = (LinearLayout.LayoutParams) this.mResidentTime.getLayoutParams();
        layoutParams4.topMargin = dimen;
        this.mResidentTime.setLayoutParams(layoutParams4);
        LinearLayout.LayoutParams layoutParams5 = (LinearLayout.LayoutParams) this.mResidentDate.getLayoutParams();
        layoutParams5.topMargin = dimen2;
        this.mResidentDate.setLayoutParams(layoutParams5);
        RelativeLayout.LayoutParams layoutParams6 = (RelativeLayout.LayoutParams) this.mResidentLayout.getLayoutParams();
        float f = this.mScaleRatio;
        int i = R.dimen.miui_resident_time_margin_start;
        layoutParams6.setMarginStart((int) (f * getDimen(i)));
        this.mResidentLayout.setLayoutParams(layoutParams6);
        int iWidth = (int) ((DeviceConfig.getScreenRect(this.mContext).width() - (this.mScaleRatio * getDimen(i))) / 2.0f);
        this.mResidentTime.setMaxWidth(iWidth);
        this.mResidentDate.setMaxWidth(iWidth);
        this.mLocalTime.setMaxWidth(iWidth);
        this.mLocalDate.setMaxWidth(iWidth);
    }

    public void updateViewsTextSize() {
        this.mContext.getResources();
        float dimen = (int) (this.mScaleRatio * getDimen(R.dimen.miui_dual_clock_city_text_size));
        this.mLocalCity.setTextSize(0, dimen);
        this.mResidentCity.setTextSize(0, dimen);
        this.mLocalDate.setTextSize(0, dimen);
        this.mResidentDate.setTextSize(0, dimen);
        float dimen2 = (int) (this.mScaleRatio * getDimen(R.dimen.miui_dual_clock_time_text_size));
        this.mLocalTime.setTextSize(0, dimen2);
        this.mResidentTime.setTextSize(0, dimen2);
    }
}
