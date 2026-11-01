package com.miui.clock.tiny.bubble;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import com.miui.clock.tiny.R;
import com.miui.clock.tiny.Util;
import com.miui.clock.tiny.model.TinyClockInfo;
import com.miui.clock.tiny.model.TinyClockViewType;
import com.miui.clock.tiny.model.TinyMiuiBaseClock;
import com.miui.clock.tiny.utils.FontUtils;
import com.miui.clock.tiny.utils.MiuiBlurUtils;
import com.miui.clock.tiny.widget.BatteryView;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class BubbleClockTopView extends TinyMiuiBaseClock {
    public BatteryView mBatteryView;
    public BubbleClockTopInfo mClockInfo;
    public Configuration mConfiguration;
    public TextView mDateWeek;
    public ImageView mMinute2AodView;
    public ShaderView mMinute2View;
    public BatteryView mNotificationBatteryView;
    public float mScale;

    /* JADX INFO: renamed from: com.miui.clock.tiny.bubble.BubbleClockTopView$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType;

        static {
            int[] iArr = new int[TinyClockViewType.values().length];
            $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType = iArr;
            try {
                iArr[TinyClockViewType.MIN2.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.MIN2_AOD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.DATE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.BATTERY.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.NOTIFICATION_BATTERY.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.THIS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public BubbleClockTopView(Context context) {
        this(context, null);
    }

    public BubbleClockTopView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BubbleClockTopView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    private void updateLayoutParams() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mMinute2View.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mDateWeek.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mBatteryView.getLayoutParams();
        int rotation = this.mClockInfo.getRotation();
        this.mBatteryView.setScale(this.mClockInfo.getScale());
        if (rotation == 2) {
            ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(R.dimen.tiny_bubble_hour_and_minute_width);
            ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(R.dimen.tiny_bubble_hour_and_minute_height);
            layoutParams.topToTop = 0;
            layoutParams.rightToRight = 0;
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.tiny_bubble_minute2_margin_top_180);
            layoutParams.setMarginEnd(getDimen(R.dimen.tiny_bubble_minute2_margin_end_180));
            this.mMinute2View.setLayoutParams(layoutParams);
            layoutParams2.bottomToBottom = 0;
            layoutParams2.rightToRight = 0;
            ((ViewGroup.MarginLayoutParams) layoutParams2).bottomMargin = getDimen(R.dimen.tiny_bubble_date_week_margin_bottom_180);
            layoutParams2.setMarginEnd(getDimen(R.dimen.tiny_bubble_date_week_margin_end_180));
            this.mDateWeek.setTextSize(0, getDimen(R.dimen.tiny_bubble_date_week_text_size));
            this.mDateWeek.setLayoutParams(layoutParams2);
            layoutParams3.topToBottom = R.id.date_week;
            layoutParams3.rightToRight = 0;
            ((ViewGroup.MarginLayoutParams) layoutParams3).topMargin = getDimen(R.dimen.tiny_bubble_battery_text_margin_top_180);
            layoutParams3.setMarginEnd(getDimen(R.dimen.tiny_bubble_battery_text_margin_end_180));
            this.mBatteryView.setLayoutParams(layoutParams3);
            return;
        }
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(R.dimen.tiny_bubble_hour_and_minute_width);
        ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(R.dimen.tiny_bubble_hour_and_minute_height);
        layoutParams.topToTop = 0;
        layoutParams.rightToRight = 0;
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(R.dimen.tiny_bubble_minute2_margin_top_0);
        layoutParams.setMarginEnd(getDimen(R.dimen.tiny_bubble_minute2_margin_end_0));
        this.mMinute2View.setLayoutParams(layoutParams);
        layoutParams2.bottomToBottom = 0;
        layoutParams2.rightToRight = 0;
        ((ViewGroup.MarginLayoutParams) layoutParams2).bottomMargin = getDimen(R.dimen.tiny_bubble_date_week_margin_bottom_0);
        layoutParams2.setMarginEnd(getDimen(R.dimen.tiny_bubble_date_week_margin_end_0));
        this.mDateWeek.setTextSize(0, getDimen(R.dimen.tiny_bubble_date_week_text_size));
        this.mDateWeek.setLayoutParams(layoutParams2);
        layoutParams3.topToBottom = R.id.date_week;
        layoutParams3.rightToRight = 0;
        ((ViewGroup.MarginLayoutParams) layoutParams3).topMargin = getDimen(R.dimen.tiny_bubble_battery_text_margin_top_0);
        layoutParams3.setMarginEnd(getDimen(R.dimen.tiny_bubble_battery_text_margin_end_0));
        this.mBatteryView.setLayoutParams(layoutParams3);
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public BubbleClockTopInfo getClockStyleInfo() {
        return this.mClockInfo;
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock
    public int getDimen(int i) {
        return (int) (this.mContext.getResources().getDimensionPixelSize(i) * this.mClockInfo.getScale());
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public View getIClockView(TinyClockViewType tinyClockViewType) {
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[tinyClockViewType.ordinal()];
        if (i == 1) {
            return this.mMinute2View;
        }
        if (i == 2) {
            return this.mMinute2AodView;
        }
        if (i == 3) {
            return this.mDateWeek;
        }
        if (i != 4) {
            return i != 5 ? this : this.mNotificationBatteryView;
        }
        return this.mBatteryView;
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public int getMarginAOD() {
        return this.mClockInfo.getRotation() == 2 ? this.mContext.getResources().getDimensionPixelSize(R.dimen.splicing_margin_start_aod_180) : this.mContext.getResources().getDimensionPixelSize(R.dimen.splicing_margin_start_aod_0);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mConfiguration = new Configuration(getContext().getResources().getConfiguration());
        this.mCalendar = new Calendar();
        this.mMinute2View = (ShaderView) findViewById(R.id.minute2);
        this.mMinute2AodView = (ImageView) findViewById(R.id.minute2_aod);
        this.mDateWeek = (TextView) findViewById(R.id.date_week);
        this.mBatteryView = (BatteryView) findViewById(R.id.battery_bubble);
        this.mNotificationBatteryView = (BatteryView) findViewById(R.id.notification_battery_bubble);
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void setBatteryColor(int i) {
        this.mBatteryView.setBatteryColor(i);
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void setBatteryLevel(int i) {
        this.mBatteryView.setBatteryLevel(i);
        this.mNotificationBatteryView.setBatteryLevel(i);
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void setClockStyleInfo(TinyClockInfo tinyClockInfo) {
        if (tinyClockInfo instanceof BubbleClockTopInfo) {
            this.mClockInfo = (BubbleClockTopInfo) tinyClockInfo;
        }
        BubbleClockTopInfo bubbleClockTopInfo = this.mClockInfo;
        if (bubbleClockTopInfo == null) {
            return;
        }
        if (bubbleClockTopInfo.getType() == 2 || this.mClockInfo.getType() == 3 || this.mClockInfo.getType() == 4) {
            this.mBatteryView.setNeedShowNormal(true);
            if (this.mClockInfo.getScale() <= 0.0f || Math.abs(this.mClockInfo.getScale() - 1.0f) <= 0.001f) {
                this.mClockInfo.setScale(1.0f);
            } else {
                setScale(this.mClockInfo.getScale());
                updateLayoutParams();
            }
        }
        this.mClockInfo.initClockDigtalResources();
        updateTime();
        this.mMinute2View.setPrimaryColor(this.mClockInfo.getPrimaryColor());
        updateDateWeekAndBatteryColor();
    }

    public void setScale(float f) {
        this.mScale = f;
    }

    public final void updateDateWeekAndBatteryColor() {
        if (!Util.isBubble3DTemplate(this.mClockInfo.getTemplateId(), this.mClockInfo.getClockStyle())) {
            if (Util.isBubbleSolid3DTemplate(this.mClockInfo.getTemplateId(), this.mClockInfo.getClockStyle())) {
                this.mDateWeek.setTextColor(this.mClockInfo.getSecondaryColor());
                this.mBatteryView.setBatteryColor(this.mClockInfo.getSecondaryColor());
                this.mNotificationBatteryView.setBatteryColor(this.mClockInfo.getSecondaryColor());
                return;
            }
            return;
        }
        int iIsBackgroundBlurEnabled = TinyMiuiBaseClock.isBackgroundBlurEnabled(this.mContext);
        MiuiBlurUtils.clearMemberBlendColor(this.mDateWeek);
        MiuiBlurUtils.clearContainerPassBlur(this.mDateWeek);
        this.mBatteryView.clearBatteryViewBlendColor();
        this.mBatteryView.clearBatteryViewPassBlur();
        if (iIsBackgroundBlurEnabled != 1) {
            this.mBatteryView.setNeedBlur(false);
            this.mDateWeek.setTextColor(this.mClockInfo.getSecondaryColor());
            this.mBatteryView.setBatteryColor(this.mClockInfo.getSecondaryColor());
            return;
        }
        this.mBatteryView.setNeedBlur(true);
        TextView textView = this.mDateWeek;
        int i = TinyMiuiBaseClock.DEFAULT_BLEND_DARK_COLOR;
        textView.setTextColor(i);
        this.mBatteryView.setBatteryColor(i);
        int secondaryBlendColor = this.mClockInfo.getSecondaryBlendColor();
        MiuiBlurUtils.setContainerPassBlur(this, this.mContext.getResources().getDimensionPixelSize(R.dimen.tiny_buble_blur_radius));
        boolean zIsWallpaperDark = true ^ this.mClockInfo.isWallpaperDark();
        MiuiBlurUtils.setMemberBlendColors(this.mDateWeek, zIsWallpaperDark, secondaryBlendColor, i);
        this.mBatteryView.setViewBlur(zIsWallpaperDark, secondaryBlendColor, i);
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        int i = this.mCurrentTimeArray[3];
        this.mMinute2View.setStyle(this.mClockInfo.getClockStyle());
        if (this.mClockInfo.getClockStyle() == 0 || this.mClockInfo.getClockStyle() == 1) {
            if (!this.resourceStatus) {
                this.mMinute2View.setClockDrawable(null);
                return;
            }
            this.mMinute2View.setClockDrawable(this.mClockInfo.getCLockDrawable(i));
        } else {
            if (!this.resourceStatus) {
                this.mMinute2View.setImageDrawable(null);
                return;
            }
            this.mMinute2View.setImageDrawable(this.mClockInfo.getCLockDrawable(i));
        }
        Drawable drawable = ContextCompat.getDrawable(this.mContext, this.mClockInfo.getAodResource()[i]);
        drawable.setTint(this.mClockInfo.getAODColor());
        this.mMinute2AodView.setImageDrawable(drawable);
        if (FontUtils.isTibetan(this.mContext) && 2 == this.mClockInfo.getRotation()) {
            Calendar calendar = this.mCalendar;
            Context context = this.mContext;
            String str = calendar.format(context, context.getString(R.string.tiny_bubble_date_format));
            Calendar calendar2 = this.mCalendar;
            Context context2 = this.mContext;
            String str2 = calendar2.format(context2, context2.getString(R.string.tiny_bubble_week_format));
            this.mDateWeek.setText(Html.fromHtml(str + "<br>" + str2));
        } else {
            TextView textView = this.mDateWeek;
            Calendar calendar3 = this.mCalendar;
            Context context3 = this.mContext;
            textView.setText(calendar3.format(context3, context3.getString(R.string.tiny_bubble_date_week_format)));
        }
        this.mDateWeek.setContentDescription(BubbleClockView.getDateWeekFormatForContentDesc(this.mContext));
    }
}
