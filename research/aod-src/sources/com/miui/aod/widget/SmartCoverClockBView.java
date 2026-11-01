package com.miui.aod.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.util.Consumer;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.resource.AodDrawables;
import com.miui.aod.resource.DrawableCache;
import com.miui.aod.util.ShaderUtils;
import com.miui.aod.widget.notification.NotificationContainer;
import com.miui.keyguard.editor.utils.ViewUtil;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class SmartCoverClockBView extends RotateHorizontalLinearLayout implements IAodClock, IAnimatable {
    public Calendar mCal;
    public TextView mClockHorizontal;
    public TextView mDateView;
    public Animator mHueAnimator;
    public ImageView mNotificationView;
    public RotateHorizontalLinearLayout mRotateHorizontalLinearLayout;
    public StyleInfo mStyleInfo;
    public TextView mTimeView;

    /* JADX INFO: renamed from: $r8$lambda$J7JXc65MBGAiMX6OfJcLW0-h4UY, reason: not valid java name */
    public static /* synthetic */ void m611$r8$lambda$J7JXc65MBGAiMX6OfJcLW0h4UY(SmartCoverClockBView smartCoverClockBView, AnimatorListenerAdapter animatorListenerAdapter, RotateHorizontalLinearLayout rotateHorizontalLinearLayout) {
        smartCoverClockBView.getClass();
        smartCoverClockBView.mHueAnimator = ShaderUtils.hueAnimateIn(animatorListenerAdapter, rotateHorizontalLinearLayout);
    }

    public SmartCoverClockBView(Context context) {
        super(context);
    }

    public SmartCoverClockBView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override // com.miui.aod.widget.IAnimatable
    public void clearAnimationView() {
        Animator animator = this.mHueAnimator;
        if (animator != null) {
            animator.cancel();
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public /* bridge */ /* synthetic */ int getClockGravity() {
        return super.getClockGravity();
    }

    @Override // com.miui.aod.widget.RotateHorizontalLinearLayout
    public void init() {
        setOrientation(1);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mNotificationView = (ImageView) findViewById(R.id.first);
        this.mDateView = (TextView) findViewById(R.id.date);
        TextView textView = (TextView) findViewById(R.id.clock_horizontal);
        this.mClockHorizontal = textView;
        this.mTimeView = textView;
        ViewUtil.doIfNotNull(this.mNotificationView, new Consumer() { // from class: com.miui.aod.widget.SmartCoverClockBView$$ExternalSyntheticLambda5
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ((ImageView) obj).setImageDrawable(DrawableCache.getInstance(this.f$0.getContext()).getDrawable(R.drawable.smartcover_notificaion_icon));
            }
        });
        ViewUtil.doIfNotNull(findViewById(R.id.icons), new Consumer() { // from class: com.miui.aod.widget.SmartCoverClockBView$$ExternalSyntheticLambda6
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ((LinearLayout.LayoutParams) ((View) obj).getLayoutParams()).gravity = 85;
            }
        });
        this.mRotateHorizontalLinearLayout = (RotateHorizontalLinearLayout) findViewById(R.id.rotate_gradient_layout);
        ViewUtil.doIfNotNull((NotificationContainer) findViewById(R.id.icons_container), new Consumer() { // from class: com.miui.aod.widget.SmartCoverClockBView$$ExternalSyntheticLambda7
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ((NotificationContainer) obj).setGravity(8388613);
            }
        });
    }

    @Override // com.miui.aod.widget.IAodClock
    public void onInflateBatteryComplete() {
        ViewUtil.setVisibility(findViewById(R.id.aod_battery_digital), 8);
    }

    @Override // com.miui.aod.widget.IAodClock
    public void setClockMask(int i, int i2) {
        if (i2 > 0) {
            setGradientOverlayDrawable(AodDrawables.getDrawableForMask(getContext(), i2));
        }
    }

    @Override // com.miui.aod.widget.RotateHorizontalLinearLayout
    public void setGradientOverlayDrawable(final Drawable drawable) {
        ViewUtil.doIfNotNull(this.mRotateHorizontalLinearLayout, new Consumer() { // from class: com.miui.aod.widget.SmartCoverClockBView$$ExternalSyntheticLambda1
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ((RotateHorizontalLinearLayout) obj).setGradientOverlayDrawable(drawable);
            }
        });
    }

    @Override // com.miui.aod.widget.IAodClock
    public /* bridge */ /* synthetic */ void setTimeZone(TimeZone timeZone) {
        super.setTimeZone(timeZone);
    }

    @Override // com.miui.aod.widget.IAodClock
    public /* bridge */ /* synthetic */ void setTimeZone2(TimeZone timeZone) {
        super.setTimeZone2(timeZone);
    }

    @Override // com.miui.aod.widget.RotateHorizontalLinearLayout
    public void setV(final boolean z) {
        super.setV(z);
        ViewUtil.doIfNotNull(this.mNotificationView, new Consumer() { // from class: com.miui.aod.widget.SmartCoverClockBView$$ExternalSyntheticLambda4
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ((ImageView) obj).setImageResource(z ? R.drawable.ic_notificon_rotate : R.drawable.ic_notification);
            }
        });
    }

    @Override // com.miui.aod.widget.IAnimatable
    public void startAnimation(final AnimatorListenerAdapter animatorListenerAdapter) {
        ViewUtil.doIfNotNull(this.mRotateHorizontalLinearLayout, new Consumer() { // from class: com.miui.aod.widget.SmartCoverClockBView$$ExternalSyntheticLambda0
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                SmartCoverClockBView.m611$r8$lambda$J7JXc65MBGAiMX6OfJcLW0h4UY(this.f$0, animatorListenerAdapter, (RotateHorizontalLinearLayout) obj);
            }
        });
    }

    @Override // com.miui.aod.components.view.IPreAodView
    public void update(StyleInfo styleInfo, int i) {
        this.mStyleInfo = styleInfo;
        if (i == 4) {
            ViewUtil.doIfNotNull(this.mClockHorizontal, new Consumer() { // from class: com.miui.aod.widget.SmartCoverClockBView$$ExternalSyntheticLambda2
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    ((TextView) obj).setTextSize(0, Utils.dp2Px(34));
                }
            });
            return;
        }
        if (i == 2) {
            ((ViewGroup.MarginLayoutParams) getLayoutParams()).topMargin = Utils.dp2Px(12);
        }
        ViewUtil.doIfNotNull(this.mClockHorizontal, new Consumer() { // from class: com.miui.aod.widget.SmartCoverClockBView$$ExternalSyntheticLambda3
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ((TextView) obj).setTextSize(0, this.f$0.getResources().getDimensionPixelSize(R.dimen.smart_cover_aodstyle_b_clocksize));
            }
        });
        if (i == 1) {
            ((LinearLayout.LayoutParams) getLayoutParams()).topMargin = getResources().getDimensionPixelSize(R.dimen.aod_preview_default_top_padding);
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public void updateTime(boolean z) {
        Calendar calendarRefreshCalendar = Utils.refreshCalendar(this.mCal, TimeZone.getDefault(), System.currentTimeMillis());
        this.mCal = calendarRefreshCalendar;
        int i = calendarRefreshCalendar.get(18);
        if (!z && i > 12) {
            i -= 12;
        }
        final int i2 = (z || i != 0) ? i : 12;
        ViewUtil.doIfNotNull(this.mTimeView, new Consumer() { // from class: com.miui.aod.widget.SmartCoverClockBView$$ExternalSyntheticLambda8
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                TextView textView = (TextView) obj;
                textView.setText(String.format("%d:%02d", Integer.valueOf(i2), Integer.valueOf(this.f$0.mCal.get(20))));
            }
        });
        ViewUtil.doIfNotNull(this.mDateView, new Consumer() { // from class: com.miui.aod.widget.SmartCoverClockBView$$ExternalSyntheticLambda9
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                SmartCoverClockBView smartCoverClockBView = this.f$0;
                ((TextView) obj).setText(smartCoverClockBView.mCal.format(smartCoverClockBView.getContext(), smartCoverClockBView.getContext().getResources().getString(R.string.aod_lock_screen_date_smartcover_b)).toUpperCase());
            }
        });
        StyleInfo styleInfo = this.mStyleInfo;
        if (styleInfo == null || !styleInfo.isChangeColorAlongTime()) {
            return;
        }
        Drawable gradientOverlayDrawable = getGradientOverlayDrawable();
        setClockMask(0, AodDrawables.get24GradientDrawableRes());
        if (gradientOverlayDrawable != getGradientOverlayDrawable()) {
            invalidate();
        }
    }
}
