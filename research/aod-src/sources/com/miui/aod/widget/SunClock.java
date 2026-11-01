package com.miui.aod.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.core.util.Consumer;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.icu.ICUManager;
import com.miui.aod.util.DeviceInfo;
import com.miui.keyguard.editor.utils.ViewUtil;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class SunClock implements IAodClock {
    public Calendar mCal;
    public Calendar mCalDual;
    public TextView mCity2;
    public TextView mClockHorizontal;
    public SunClockView mClockView;
    public Context mContext;
    public TextView mDateViewDual;
    public View mDateViewDualContainer;
    public final boolean mDual;
    public final int mSize;
    public StyleInfo mStyleInfo;
    public TimeZone mTimeZone;
    public TimeZone mTimeZoneDual;

    public static /* synthetic */ void $r8$lambda$_30d4dw36dSQx1_rYqyk45biMfo(SunClock sunClock, TextView textView) {
        sunClock.getClass();
        ICUManager.INSTANCE.loadCity(textView, sunClock.mContext, sunClock.mTimeZoneDual);
    }

    /* JADX INFO: renamed from: $r8$lambda$cX-JCrAeovrhMesb-Q3dbu0BgVE, reason: not valid java name */
    public static /* synthetic */ void m621$r8$lambda$cXJCrAeovrhMesbQ3dbu0BgVE(SunClock sunClock, long j, TextView textView) {
        Calendar calendarRefreshCalendar = Utils.refreshCalendar(sunClock.mCalDual, sunClock.mTimeZoneDual, j);
        sunClock.mCalDual = calendarRefreshCalendar;
        textView.setText(calendarRefreshCalendar.format(sunClock.mContext, "HH:mm"));
    }

    public SunClock(int i, boolean z) {
        this.mSize = i;
        this.mDual = z;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void bindView(View view) {
        this.mContext = view.getContext();
        SunClockView sunClockView = (SunClockView) view.findViewById(R.id.clock);
        this.mClockView = sunClockView;
        sunClockView.setSize(this.mSize);
        TextView textView = (TextView) view.findViewById(R.id.clock_horizontal);
        this.mClockHorizontal = textView;
        if (textView != null) {
            if (this.mSize == 2) {
                this.mClockHorizontal.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(), "fonts/MiClockMono-Regular.otf"));
                this.mClockHorizontal.setLetterSpacing(0.8f);
            } else {
                this.mClockHorizontal.setTypeface(Typeface.createFromAsset(view.getContext().getAssets(), "fonts/MiClockMono-Normal.otf"));
            }
        }
        if (this.mDual) {
            View viewFindViewById = this.mClockView.findViewById(R.id.date_container_dual);
            this.mDateViewDualContainer = viewFindViewById;
            if (viewFindViewById != null) {
                this.mCity2 = (TextView) viewFindViewById.findViewById(R.id.city2);
                this.mDateViewDual = (TextView) this.mDateViewDualContainer.findViewById(R.id.date_dual);
            }
        }
    }

    public final View getContainerView(View view) {
        while (view.getParent() != null && (view.getParent() instanceof View) && view.getId() != R.id.aod_content_container) {
            view = (View) view.getParent();
        }
        return view;
    }

    @Override // com.miui.aod.widget.IAodClock
    public int getLayoutResource(StyleInfo styleInfo) {
        this.mStyleInfo = styleInfo;
        int i = this.mSize;
        if (i == 2) {
            return R.layout.aod_content_sun_small;
        }
        return (!this.mDual || i == 4) ? R.layout.aod_content_sun : R.layout.aod_content_sun_dual;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void onInflateComplete() {
        TextView textView;
        View viewFindViewById = this.mClockView.findViewById(R.id.battery_step_container);
        int i = this.mSize;
        if (i != 2 && i != 4) {
            if (!this.mDual) {
                ViewUtil.doIfNotNull(viewFindViewById, new Consumer() { // from class: com.miui.aod.widget.SunClock$$ExternalSyntheticLambda2
                    @Override // androidx.core.util.Consumer
                    public final void accept(Object obj) {
                        ViewUtil.setMarginTop((View) obj, this.f$0.mContext.getResources().getDimensionPixelSize(R.dimen.sun_aod_battery_time_gap));
                    }
                });
                return;
            }
            ViewUtil.doIfNotNull(this.mDateViewDualContainer, new Consumer() { // from class: com.miui.aod.widget.SunClock$$ExternalSyntheticLambda3
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    ViewUtil.setMarginTop((View) obj, this.f$0.mContext.getResources().getDimensionPixelSize(R.dimen.sun_aod_dual_firstTime_secondTime_gap));
                }
            });
            ViewUtil.doIfNotNull(viewFindViewById, new Consumer() { // from class: com.miui.aod.widget.SunClock$$ExternalSyntheticLambda4
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    ViewUtil.setMarginTop((View) obj, this.f$0.mContext.getResources().getDimensionPixelSize(R.dimen.sun_aod_battery_time_gap));
                }
            });
            ViewUtil.doIfNotNull(getContainerView(this.mClockView).findViewById(R.id.icons), new Consumer() { // from class: com.miui.aod.widget.SunClock$$ExternalSyntheticLambda5
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    ViewUtil.setMarginTop((View) obj, this.f$0.mContext.getResources().getDimensionPixelSize(R.dimen.sun_aod_battery_icons_gap));
                }
            });
            return;
        }
        ViewUtil.setVisibility(viewFindViewById, 8);
        StyleInfo styleInfo = this.mStyleInfo;
        if (styleInfo == null || !styleInfo.isForAodNotification() || (textView = this.mClockHorizontal) == null) {
            return;
        }
        ((ViewGroup.MarginLayoutParams) textView.getLayoutParams()).topMargin = DeviceInfo.dp2Px(com.miui.maml.R.styleable.AppCompatTheme_tooltipFrameBackground);
    }

    @Override // com.miui.aod.widget.IAodClock
    public void setTimeZone(TimeZone timeZone) {
        this.mTimeZone = timeZone;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void setTimeZone2(TimeZone timeZone) {
        this.mTimeZoneDual = timeZone;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void updateTime(boolean z) {
        TimeZone timeZone = this.mTimeZone;
        if (timeZone == null) {
            timeZone = TimeZone.getDefault();
        }
        final long jCurrentTimeMillis = System.currentTimeMillis();
        Calendar calendarRefreshCalendar = Utils.refreshCalendar(this.mCal, timeZone, jCurrentTimeMillis);
        this.mCal = calendarRefreshCalendar;
        int i = calendarRefreshCalendar.get(18);
        TextView textView = this.mClockHorizontal;
        if (textView != null) {
            textView.setText(String.format("%02d:%02d", Integer.valueOf(i), Integer.valueOf(this.mCal.get(20))));
        }
        if (!this.mDual || this.mDateViewDualContainer == null) {
            return;
        }
        ViewUtil.doIfNotNull(this.mCity2, new Consumer() { // from class: com.miui.aod.widget.SunClock$$ExternalSyntheticLambda0
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                SunClock.$r8$lambda$_30d4dw36dSQx1_rYqyk45biMfo(this.f$0, (TextView) obj);
            }
        });
        ViewUtil.doIfNotNull(this.mDateViewDual, new Consumer() { // from class: com.miui.aod.widget.SunClock$$ExternalSyntheticLambda1
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                SunClock.m621$r8$lambda$cXJCrAeovrhMesbQ3dbu0BgVE(this.f$0, jCurrentTimeMillis, (TextView) obj);
            }
        });
    }
}
