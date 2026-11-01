package com.miui.aod.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.core.util.Consumer;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.SmartCoverStyleInfo;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.resource.AodDrawables;
import com.miui.aod.resource.DrawableCache;
import com.miui.aod.util.ShaderUtils;
import com.miui.aod.widget.notification.NotificationContainer;
import com.miui.keyguard.editor.utils.ViewUtil;
import java.util.TimeZone;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class SmartCoverClockView extends GradientLinearLayout implements IAodClock, IAnimatable {
    public Calendar mCal;
    public TextView mClockHourView;
    public TextView mClockMinuteView;
    public TextView mDateView;
    public GradientLinearLayout mGradientLinearLayout;
    public Animator mHueAnimator;
    public SmartCoverStyleInfo mStyleInfo;
    public Typeface typeface_a;
    public Typeface typeface_b;

    public static /* synthetic */ void $r8$lambda$ELoPS_S3H00_UJNXKAObe768dJc(SmartCoverClockView smartCoverClockView, Object obj) {
        smartCoverClockView.getClass();
        ((ImageView) obj).setImageDrawable(DrawableCache.getInstance(smartCoverClockView.getContext()).getDrawable(R.drawable.smartcover_notificaion_icon));
    }

    public static /* synthetic */ void $r8$lambda$I1suuo17FjvKYluZFkbJRVD33zY(SmartCoverClockView smartCoverClockView, int i, int i2, int i3, TextView textView) {
        smartCoverClockView.getClass();
        textView.setTextAppearance(i);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textView.getLayoutParams();
        if (i2 == 4) {
            layoutParams.gravity = 1;
        }
        layoutParams.setMargins(0, i3, 0, 0);
        textView.setLayoutParams(layoutParams);
        textView.setTypeface(smartCoverClockView.mStyleInfo.getStyle() == 0 ? smartCoverClockView.typeface_a : smartCoverClockView.typeface_b);
    }

    public static /* synthetic */ void $r8$lambda$b_kNlrpqhCFtjIpZfhierIx4KB0(int i, TextView textView) {
        textView.setTextAppearance(i);
        textView.setTypeface(Typeface.create("mipro-medium", 0));
        ((LinearLayout.LayoutParams) textView.getLayoutParams()).topMargin = Utils.dp2Px(-1);
    }

    public static /* synthetic */ void $r8$lambda$f50ECg37ZFj5zkIo5rGk21L5da8(SmartCoverClockView smartCoverClockView, int i, TextView textView) {
        smartCoverClockView.getClass();
        textView.setTextAppearance(i);
        textView.setTypeface(smartCoverClockView.mStyleInfo.getStyle() == 0 ? smartCoverClockView.typeface_a : smartCoverClockView.typeface_b);
    }

    /* JADX INFO: renamed from: $r8$lambda$iHy9Y-YoW8nhOC-PuitZy8GFu5U, reason: not valid java name */
    public static /* synthetic */ void m620$r8$lambda$iHy9YYoW8nhOCPuitZy8GFu5U(SmartCoverClockView smartCoverClockView, TextView textView) {
        String upperCase = smartCoverClockView.mCal.format(smartCoverClockView.getContext(), smartCoverClockView.getContext().getResources().getString(R.string.aod_lock_screen_date_smartcover)).toUpperCase();
        if (TextUtils.equals(textView.getText(), upperCase)) {
            return;
        }
        textView.setText(upperCase);
    }

    public SmartCoverClockView(Context context) {
        super(context);
    }

    public SmartCoverClockView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SmartCoverClockView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
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

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mClockHourView = (TextView) findViewById(R.id.clock_hour);
        this.mClockMinuteView = (TextView) findViewById(R.id.clock_minute);
        this.mDateView = (TextView) findViewById(R.id.date);
        this.typeface_a = Typeface.createFromAsset(getContext().getAssets(), "fonts/BebasNeue-Book.otf");
        this.typeface_b = Typeface.createFromAsset(getContext().getAssets(), "fonts/MiClock-Light.otf");
        ViewUtil.doIfNotNull(findViewById(R.id.first), new Consumer() { // from class: com.miui.aod.widget.SmartCoverClockView$$ExternalSyntheticLambda10
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                SmartCoverClockView.$r8$lambda$ELoPS_S3H00_UJNXKAObe768dJc(this.f$0, obj);
            }
        });
        this.mGradientLinearLayout = (GradientLinearLayout) findViewById(R.id.gradient_layout);
        ViewUtil.doIfNotNull((NotificationContainer) findViewById(R.id.icons_container), new Consumer() { // from class: com.miui.aod.widget.SmartCoverClockView$$ExternalSyntheticLambda11
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
    public void setClockMask(int i, final int i2) {
        if (i2 > 0) {
            ViewUtil.doIfNotNull(this.mGradientLinearLayout, new Consumer() { // from class: com.miui.aod.widget.SmartCoverClockView$$ExternalSyntheticLambda9
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    GradientLinearLayout gradientLinearLayout = (GradientLinearLayout) obj;
                    gradientLinearLayout.setGradientOverlayDrawable(AodDrawables.getDrawableForMask(this.f$0.getContext(), i2));
                }
            });
        }
    }

    @Override // com.miui.aod.widget.IAodClock
    public /* bridge */ /* synthetic */ void setTimeZone(TimeZone timeZone) {
        super.setTimeZone(timeZone);
    }

    @Override // com.miui.aod.widget.IAodClock
    public /* bridge */ /* synthetic */ void setTimeZone2(TimeZone timeZone) {
        super.setTimeZone2(timeZone);
    }

    @Override // com.miui.aod.widget.IAnimatable
    public void startAnimation(AnimatorListenerAdapter animatorListenerAdapter) {
        this.mHueAnimator = ShaderUtils.hueAnimateIn(animatorListenerAdapter, this.mGradientLinearLayout);
    }

    @Override // com.miui.aod.components.view.IPreAodView
    public void update(StyleInfo styleInfo, final int i) {
        final int i2;
        int i3;
        this.mStyleInfo = (SmartCoverStyleInfo) styleInfo;
        final int i4 = R.style.Aod_date_s;
        if (i == 2) {
            i2 = R.style.Aod_widget_s;
            i3 = R.style.Aod_smartcover_vertical_s;
        } else if (i == 4) {
            ((LinearLayout.LayoutParams) getLayoutParams()).rightMargin = 0;
            ViewUtil.doIfNotNull(this.mClockHourView, new Consumer() { // from class: com.miui.aod.widget.SmartCoverClockView$$ExternalSyntheticLambda0
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    ((TextView) obj).setPadding(0, Utils.dp2Px(1), 0, 0);
                }
            });
            i2 = R.style.Aod_widget_smartcover_a_t;
            i3 = R.style.Aod_smartcover_vertical_t;
        } else {
            i2 = R.style.Aod_widget_smartcover_vertical;
            i4 = R.style.Aod_date_smartcover_stylea_vertical;
            i3 = R.style.Aod_smartcover_vertical;
        }
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(i3, R.styleable.verticalClock);
        final int dimension = (int) typedArrayObtainStyledAttributes.getDimension(2, 0.0f);
        typedArrayObtainStyledAttributes.recycle();
        ViewUtil.doIfNotNull(this.mClockHourView, new Consumer() { // from class: com.miui.aod.widget.SmartCoverClockView$$ExternalSyntheticLambda1
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                SmartCoverClockView.$r8$lambda$f50ECg37ZFj5zkIo5rGk21L5da8(this.f$0, i2, (TextView) obj);
            }
        });
        if (i == 4) {
            ViewUtil.doIfNotNull(this.mClockHourView, new Consumer() { // from class: com.miui.aod.widget.SmartCoverClockView$$ExternalSyntheticLambda2
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    ((LinearLayout.LayoutParams) ((TextView) obj).getLayoutParams()).gravity = 1;
                }
            });
            ViewUtil.doIfNotNull(this.mGradientLinearLayout, new Consumer() { // from class: com.miui.aod.widget.SmartCoverClockView$$ExternalSyntheticLambda3
                @Override // androidx.core.util.Consumer
                public final void accept(Object obj) {
                    ((LinearLayout.LayoutParams) ((GradientLinearLayout) obj).getLayoutParams()).topMargin = Utils.dp2Px(2);
                }
            });
        } else if (i == 1) {
            ((ViewGroup.MarginLayoutParams) getLayoutParams()).topMargin = Utils.dp2Px(32);
        }
        ViewUtil.doIfNotNull(this.mClockMinuteView, new Consumer() { // from class: com.miui.aod.widget.SmartCoverClockView$$ExternalSyntheticLambda4
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                SmartCoverClockView.$r8$lambda$I1suuo17FjvKYluZFkbJRVD33zY(this.f$0, i2, i, dimension, (TextView) obj);
            }
        });
        ViewUtil.doIfNotNull(this.mDateView, new Consumer() { // from class: com.miui.aod.widget.SmartCoverClockView$$ExternalSyntheticLambda5
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                SmartCoverClockView.$r8$lambda$b_kNlrpqhCFtjIpZfhierIx4KB0(i4, (TextView) obj);
            }
        });
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
        ViewUtil.doIfNotNull(this.mDateView, new Consumer() { // from class: com.miui.aod.widget.SmartCoverClockView$$ExternalSyntheticLambda6
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                SmartCoverClockView.m620$r8$lambda$iHy9YYoW8nhOCPuitZy8GFu5U(this.f$0, (TextView) obj);
            }
        });
        ViewUtil.doIfNotNull(this.mClockHourView, new Consumer() { // from class: com.miui.aod.widget.SmartCoverClockView$$ExternalSyntheticLambda7
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ((TextView) obj).setText(String.format("%02d", Integer.valueOf(i2)));
            }
        });
        ViewUtil.doIfNotNull(this.mClockMinuteView, new Consumer() { // from class: com.miui.aod.widget.SmartCoverClockView$$ExternalSyntheticLambda8
            @Override // androidx.core.util.Consumer
            public final void accept(Object obj) {
                ((TextView) obj).setText(String.format("%02d", Integer.valueOf(this.f$0.mCal.get(20))));
            }
        });
        SmartCoverStyleInfo smartCoverStyleInfo = this.mStyleInfo;
        if (smartCoverStyleInfo == null || !smartCoverStyleInfo.isChangeColorAlongTime()) {
            return;
        }
        Drawable gradientOverlayDrawable = getGradientOverlayDrawable();
        setClockMask(0, AodDrawables.get24GradientDrawableRes());
        if (gradientOverlayDrawable != getGradientOverlayDrawable()) {
            invalidate();
        }
    }
}
