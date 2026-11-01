package com.miui.aod.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.util.Consumer;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.icu.ICUManager;
import com.miui.aod.resource.AodDrawables;
import com.miui.keyguard.editor.utils.ViewUtil;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class HorizontalClock implements IAodClock {
    public Calendar mCal;
    public Calendar mCalDual;
    public TextView mCity;
    public TextView mCity2;
    public TextView mClockHorizontal;
    public TextView mClockHorizontalDual;
    public Context mContext;
    public TextView mDateLunar;
    public TextView mDateView;
    public TextView mDateViewDual;
    public LinearLayout mGradientLayout;
    public boolean mIsDual;
    public final int mLayoutGravity;
    public int mLayoutId;
    public final int mSize;
    public StyleInfo mStyleInfo;
    public TimeZone mTimeZone;
    public TimeZone mTimeZoneDual;

    public static /* synthetic */ void $r8$lambda$7gYdhJTSMoFvNfDSXw3FZhHKbGk(HorizontalClock horizontalClock, TextView textView) {
        horizontalClock.getClass();
        ICUManager.INSTANCE.loadCity(textView, horizontalClock.mContext, horizontalClock.mTimeZoneDual);
    }

    public static /* synthetic */ void $r8$lambda$z5tIf9BGw5gfsFYGD4jy_1U_1SE(HorizontalClock horizontalClock, TextView textView) {
        horizontalClock.getClass();
        ICUManager.INSTANCE.loadCity(textView, horizontalClock.mContext, TimeZone.getDefault());
    }

    public HorizontalClock(int i, boolean z, int i2) {
        this.mSize = i;
        this.mIsDual = z;
        this.mLayoutGravity = i2;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void bindView(View view) {
        TextView textView;
        this.mContext = view.getContext();
        HorizontalClockView horizontalClockView = (HorizontalClockView) view.findViewById(R.id.clock);
        horizontalClockView.init(this.mSize, this.mIsDual, this.mLayoutGravity);
        if (this.mSize == 2 && this.mLayoutGravity == 3) {
            horizontalClockView.setPadding(horizontalClockView.getResources().getDimensionPixelSize(R.dimen.aod_horizontal_left_padding_start_s), 0, 0, 0);
        }
        this.mDateView = (TextView) view.findViewById(R.id.date);
        this.mDateLunar = (TextView) view.findViewById(R.id.date_lunar);
        this.mClockHorizontal = (TextView) view.findViewById(R.id.clock_horizontal);
        this.mClockHorizontalDual = (TextView) view.findViewById(R.id.clock_horizontal_dual);
        this.mCity = (TextView) view.findViewById(R.id.city);
        this.mGradientLayout = (LinearLayout) view.findViewById(R.id.gradient_layout);
        if (this.mSize == 4 && (textView = this.mClockHorizontal) != null) {
            textView.setTextSize(0, view.getContext().getResources().getDimension(R.dimen.clock_text_size_horizontal_left_thumb));
            int i = this.mLayoutGravity;
            if (i == 17) {
                this.mClockHorizontal.setPadding(0, 0, 0, 0);
            } else if (i == 3) {
                this.mClockHorizontal.setHeight(view.getContext().getResources().getDimensionPixelOffset(R.dimen.rect_drawable_item_size));
                this.mClockHorizontal.setPadding(view.getResources().getDimensionPixelSize(R.dimen.clock_text_horizontal_left_padding_thumb), view.getResources().getDimensionPixelSize(R.dimen.clock_text_horizontal_h_padding_thumb), 0, 0);
            }
        }
        if (this.mIsDual) {
            this.mDateViewDual = (TextView) view.findViewById(R.id.date_dual);
            this.mCity2 = (TextView) view.findViewById(R.id.city2);
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public int getClockGravity() {
        return this.mLayoutGravity;
    }

    @Override // com.miui.aod.widget.IAodClock
    public int getLayoutResource(StyleInfo styleInfo) {
        int i = this.mSize;
        boolean z = (4 == i || 2 == i) ? false : true;
        if (this.mIsDual && z) {
            this.mLayoutId = this.mLayoutGravity == 3 ? R.layout.aod_content_horizontal_left_dual : R.layout.aod_content_horizontal_dual;
        } else if (i == 2) {
            this.mLayoutId = this.mLayoutGravity == 3 ? R.layout.aod_content_horizontal_left_small : R.layout.aod_content_horizontal_small;
        } else {
            this.mLayoutId = this.mLayoutGravity == 3 ? R.layout.aod_content_horizontal_left : R.layout.aod_content_horizontal;
        }
        return this.mLayoutId;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void setClockMask(int i, int i2) {
        if (this.mSize != 4 && i2 > 0) {
            LinearLayout linearLayout = this.mGradientLayout;
            if (linearLayout instanceof GradientLinearLayout) {
                ((GradientLinearLayout) linearLayout).setGradientOverlayDrawable(AodDrawables.getDrawableForMask(this.mContext, i2));
            }
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public void setTimeZone(TimeZone timeZone) {
        this.mTimeZone = timeZone;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void setTimeZone2(TimeZone timeZone) {
        this.mTimeZoneDual = timeZone;
    }

    @Override // com.miui.aod.components.view.IPreAodView
    public void update(StyleInfo styleInfo, int i) {
        this.mStyleInfo = styleInfo;
    }

    @Override // com.miui.aod.widget.IAodClock
    public void updateTime(final boolean z) {
        String string;
        TimeZone timeZone = this.mTimeZone;
        if (timeZone == null) {
            timeZone = TimeZone.getDefault();
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        Calendar calendarRefreshCalendar = Utils.refreshCalendar(this.mCal, timeZone, jCurrentTimeMillis);
        this.mCal = calendarRefreshCalendar;
        int i = calendarRefreshCalendar.get(18);
        if (!z && i > 12) {
            i -= 12;
        }
        if (!z && i == 0) {
            i = 12;
        }
        TextView textView = this.mClockHorizontal;
        if (textView != null) {
            textView.setText(String.format("%d:%02d", Integer.valueOf(i), Integer.valueOf(this.mCal.get(20))));
        }
        ViewUtil.doIfNotNull(this.mDateView, new Consumer() { // from class: com.miui.aod.widget.HorizontalClock$$ExternalSyntheticLambda0
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                HorizontalClock horizontalClock = this.f$0;
                ((TextView) obj).setText(horizontalClock.mCal.format(horizontalClock.mContext, AODSettings.getDateFormat(horizontalClock.mContext, z, false)));
            }
        });
        if (this.mDateLunar != null && this.mStyleInfo.isLunarSwitchOn(this.mContext)) {
            TextView textView2 = this.mDateLunar;
            Calendar calendar = this.mCal;
            Context context = this.mContext;
            textView2.setText(calendar.format(context, AODSettings.getDateFormat(context, z, true)));
        }
        StyleInfo styleInfo = this.mStyleInfo;
        if (styleInfo != null && styleInfo.isChangeColorAlongTime()) {
            LinearLayout linearLayout = this.mGradientLayout;
            if (linearLayout instanceof GradientLinearLayout) {
                Drawable gradientOverlayDrawable = ((GradientLinearLayout) linearLayout).getGradientOverlayDrawable();
                setClockMask(0, AodDrawables.get24GradientDrawableRes());
                if (gradientOverlayDrawable != ((GradientLinearLayout) this.mGradientLayout).getGradientOverlayDrawable()) {
                    this.mGradientLayout.invalidate();
                }
            }
        }
        if (this.mIsDual) {
            TimeZone timeZone2 = this.mTimeZoneDual;
            if (timeZone2 == null) {
                timeZone2 = TimeZone.getDefault();
            }
            ViewUtil.doIfNotNull(this.mCity, new Consumer() { // from class: com.miui.aod.widget.HorizontalClock$$ExternalSyntheticLambda1
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    HorizontalClock.$r8$lambda$z5tIf9BGw5gfsFYGD4jy_1U_1SE(this.f$0, (TextView) obj);
                }
            });
            ViewUtil.doIfNotNull(this.mCity2, new Consumer() { // from class: com.miui.aod.widget.HorizontalClock$$ExternalSyntheticLambda2
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    HorizontalClock.$r8$lambda$7gYdhJTSMoFvNfDSXw3FZhHKbGk(this.f$0, (TextView) obj);
                }
            });
            Calendar calendarRefreshCalendar2 = Utils.refreshCalendar(this.mCalDual, timeZone2, jCurrentTimeMillis);
            this.mCalDual = calendarRefreshCalendar2;
            if (this.mClockHorizontalDual != null) {
                int i2 = calendarRefreshCalendar2.get(18);
                if (!z && i2 > 12) {
                    i2 -= 12;
                }
                this.mClockHorizontalDual.setText(String.format("%d:%02d", Integer.valueOf((z || i2 != 0) ? i2 : 12), Integer.valueOf(this.mCalDual.get(20))));
            }
            if (this.mDateView == null && this.mDateViewDual == null) {
                return;
            }
            if (this.mLayoutGravity == 17) {
                string = AODSettings.getDateFormat(this.mContext, z, false);
                TextView textView3 = this.mDateView;
                if (textView3 != null) {
                    textView3.setText(this.mCal.format(this.mContext, string));
                }
            } else {
                String string2 = this.mContext.getResources().getString(z ? R.string.aod_dual_clock_date : R.string.aod_dual_clock_date_12);
                if (AODSettings.isSameDate(this.mCal, this.mCalDual)) {
                    string = this.mContext.getResources().getString(z ? R.string.aod_dual_togother : R.string.aod_dual_togother_12);
                } else {
                    string = string2;
                }
            }
            TextView textView4 = this.mDateViewDual;
            if (textView4 != null) {
                textView4.setText(this.mCalDual.format(this.mContext, string));
            }
        }
    }
}
