package com.miui.clock.tiny.bubble;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import miuix.pickerwidget.date.Calendar;

/* JADX INFO: loaded from: classes.dex */
public class BubbleClockView extends TinyMiuiBaseClock {
    public BubbleClockInfo mClockInfo;
    public ImageView mHour1AodView;
    public ShaderView mHour1View;
    public ImageView mHour2AodView;
    public ShaderView mHour2View;
    public ImageView mMinute1AodView;
    public ShaderView mMinute1View;
    public TextView mNotificationDateWeekText;
    public float mScale;
    public View mTimeContainer;

    /* JADX INFO: renamed from: com.miui.clock.tiny.bubble.BubbleClockView$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType;

        static {
            int[] iArr = new int[TinyClockViewType.values().length];
            $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType = iArr;
            try {
                iArr[TinyClockViewType.NOTIFICATION_DATE_WEEK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.HOUR1.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.HOUR2.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.MIN1.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.HOUR1_AOD.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.HOUR2_AOD.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.MIN1_AOD.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.TIME_CONTAINER.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[TinyClockViewType.THIS.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
        }
    }

    public BubbleClockView(Context context) {
        this(context, null);
    }

    public BubbleClockView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BubbleClockView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    public static String getDateWeekFormatForContentDesc(Context context) {
        return new SimpleDateFormat(context.getString(R.string.tiny_bubble_date_week_content_description), Locale.getDefault()).format(new Date());
    }

    private void updateLayoutParams() {
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) this.mHour1View.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams2 = (ConstraintLayout.LayoutParams) this.mHour2View.getLayoutParams();
        ConstraintLayout.LayoutParams layoutParams3 = (ConstraintLayout.LayoutParams) this.mMinute1View.getLayoutParams();
        if (this.mClockInfo.getRotation() == 2) {
            int i = R.dimen.tiny_bubble_hour_and_minute_width;
            ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(i);
            int i2 = R.dimen.tiny_bubble_hour_and_minute_height;
            ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(i2);
            layoutParams.leftToLeft = 0;
            layoutParams.topToTop = 0;
            layoutParams.setMarginStart(getDimen(R.dimen.tiny_bubble_hour1_margin_start_180));
            int i3 = R.dimen.tiny_bubble_hour_margin_top_180;
            ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(i3);
            this.mHour1View.setLayoutParams(layoutParams);
            ((ViewGroup.MarginLayoutParams) layoutParams2).width = getDimen(i);
            ((ViewGroup.MarginLayoutParams) layoutParams2).height = getDimen(i2);
            layoutParams2.topToTop = 0;
            layoutParams2.leftToLeft = 0;
            layoutParams2.setMarginStart(getDimen(R.dimen.tiny_bubble_hour2_margin_start_180));
            ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimen(i3);
            this.mHour2View.setLayoutParams(layoutParams2);
            ((ViewGroup.MarginLayoutParams) layoutParams3).width = getDimen(i);
            ((ViewGroup.MarginLayoutParams) layoutParams3).height = getDimen(i2);
            layoutParams3.leftToLeft = 0;
            layoutParams3.topToBottom = R.id.hour1;
            layoutParams3.setMarginStart(getDimen(R.dimen.tiny_bubble_minute1_margin_start_180));
            this.mMinute1View.setLayoutParams(layoutParams3);
            return;
        }
        int i4 = R.dimen.tiny_bubble_hour_and_minute_width;
        ((ViewGroup.MarginLayoutParams) layoutParams).width = getDimen(i4);
        int i5 = R.dimen.tiny_bubble_hour_and_minute_height;
        ((ViewGroup.MarginLayoutParams) layoutParams).height = getDimen(i5);
        layoutParams.leftToLeft = 0;
        layoutParams.topToTop = 0;
        layoutParams.setMarginStart(getDimen(R.dimen.tiny_bubble_hour1_margin_start_0));
        int i6 = R.dimen.tiny_bubble_hour_margin_top_0;
        ((ViewGroup.MarginLayoutParams) layoutParams).topMargin = getDimen(i6);
        this.mHour1View.setLayoutParams(layoutParams);
        ((ViewGroup.MarginLayoutParams) layoutParams2).width = getDimen(i4);
        ((ViewGroup.MarginLayoutParams) layoutParams2).height = getDimen(i5);
        layoutParams2.topToTop = 0;
        layoutParams2.rightToRight = 0;
        layoutParams2.setMarginEnd(getDimen(R.dimen.tiny_bubble_hour2_margin_end_0));
        ((ViewGroup.MarginLayoutParams) layoutParams2).topMargin = getDimen(i6);
        this.mHour2View.setLayoutParams(layoutParams2);
        ((ViewGroup.MarginLayoutParams) layoutParams3).width = getDimen(i4);
        ((ViewGroup.MarginLayoutParams) layoutParams3).height = getDimen(i5);
        layoutParams3.leftToLeft = 0;
        layoutParams3.topToBottom = R.id.hour1;
        layoutParams3.setMarginStart(getDimen(R.dimen.tiny_bubble_minute1_margin_start_0));
        this.mMinute1View.setLayoutParams(layoutParams3);
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public BubbleClockInfo getClockStyleInfo() {
        return this.mClockInfo;
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock
    public int getDimen(int i) {
        return (int) (this.mContext.getResources().getDimensionPixelSize(i) * this.mClockInfo.getScale());
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public View getIClockView(TinyClockViewType tinyClockViewType) {
        switch (AnonymousClass1.$SwitchMap$com$miui$clock$tiny$model$TinyClockViewType[tinyClockViewType.ordinal()]) {
            case 1:
                return this.mNotificationDateWeekText;
            case 2:
                return this.mHour1View;
            case 3:
                return this.mHour2View;
            case 4:
                return this.mMinute1View;
            case 5:
                return this.mHour1AodView;
            case 6:
                return this.mHour2AodView;
            case 7:
                return this.mMinute1AodView;
            case 8:
                return this.mTimeContainer;
            default:
                return this;
        }
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public int getMarginAOD() {
        return this.mClockInfo.getRotation() == 2 ? this.mContext.getResources().getDimensionPixelSize(R.dimen.splicing_margin_start_aod_180) : this.mContext.getResources().getDimensionPixelSize(R.dimen.splicing_margin_start_aod_0);
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        this.mHour1View = (ShaderView) findViewById(R.id.hour1);
        this.mHour2View = (ShaderView) findViewById(R.id.hour2);
        this.mMinute1View = (ShaderView) findViewById(R.id.minute1);
        this.mHour1AodView = (ImageView) findViewById(R.id.hour1_aod);
        this.mHour2AodView = (ImageView) findViewById(R.id.hour2_aod);
        this.mMinute1AodView = (ImageView) findViewById(R.id.minute1_aod);
        this.mNotificationDateWeekText = (TextView) findViewById(R.id.notification_date_week);
        this.mTimeContainer = findViewById(R.id.time_container);
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void setClockStyleInfo(TinyClockInfo tinyClockInfo) {
        if (tinyClockInfo instanceof BubbleClockInfo) {
            this.mClockInfo = (BubbleClockInfo) tinyClockInfo;
        }
        BubbleClockInfo bubbleClockInfo = this.mClockInfo;
        if (bubbleClockInfo == null) {
            return;
        }
        if (bubbleClockInfo.getType() == 2 || this.mClockInfo.getType() == 3 || this.mClockInfo.getType() == 4) {
            if (this.mClockInfo.getScale() <= 0.0f || Math.abs(this.mClockInfo.getScale() - 1.0f) <= 0.001f) {
                this.mClockInfo.setScale(1.0f);
            } else {
                setScale(this.mClockInfo.getScale());
                updateLayoutParams();
            }
        }
        updateDateAndWeekColorForNotificationInSolidTemplte();
        this.mClockInfo.initClockDigtalResources();
        updateTime();
        this.mHour1View.setPrimaryColor(this.mClockInfo.getPrimaryColor());
        this.mHour2View.setPrimaryColor(this.mClockInfo.getPrimaryColor());
        this.mMinute1View.setPrimaryColor(this.mClockInfo.getPrimaryColor());
    }

    public void setScale(float f) {
        this.mScale = f;
    }

    public final void updateDateAndWeekColorForNotificationInSolidTemplte() {
        if (Util.isBubbleSolid3DTemplate(this.mClockInfo.getTemplateId(), this.mClockInfo.getClockStyle())) {
            this.mNotificationDateWeekText.setTextColor(this.mClockInfo.getSecondaryColor());
        }
    }

    @Override // com.miui.clock.tiny.model.TinyMiuiBaseClock, com.miui.clock.tiny.TinyMiuiClockController.IClockView
    public void updateTime() {
        super.updateTime();
        int[] iArr = this.mCurrentTimeArray;
        int i = iArr[0];
        int i2 = iArr[1];
        int i3 = iArr[2];
        this.mHour1View.setStyle(this.mClockInfo.getClockStyle());
        this.mHour2View.setStyle(this.mClockInfo.getClockStyle());
        this.mMinute1View.setStyle(this.mClockInfo.getClockStyle());
        if (this.mClockInfo.getClockStyle() == 0 || this.mClockInfo.getClockStyle() == 1) {
            if (!this.resourceStatus) {
                this.mHour1View.setClockDrawable(null);
                this.mHour2View.setClockDrawable(null);
                this.mMinute1View.setClockDrawable(null);
                return;
            } else {
                this.mHour1View.setClockDrawable(this.mClockInfo.getCLockDrawable(i));
                this.mHour2View.setClockDrawable(this.mClockInfo.getCLockDrawable(i2));
                this.mMinute1View.setClockDrawable(this.mClockInfo.getCLockDrawable(i3));
            }
        } else if (!this.resourceStatus) {
            this.mHour1View.setImageDrawable(null);
            this.mHour2View.setImageDrawable(null);
            this.mMinute1View.setImageDrawable(null);
            return;
        } else {
            this.mHour1View.setImageDrawable(this.mClockInfo.getCLockDrawable(i));
            this.mHour2View.setImageDrawable(this.mClockInfo.getCLockDrawable(i2));
            this.mMinute1View.setImageDrawable(this.mClockInfo.getCLockDrawable(i3));
        }
        Drawable drawable = ContextCompat.getDrawable(this.mContext, this.mClockInfo.getAodResource()[i]);
        Drawable drawable2 = ContextCompat.getDrawable(this.mContext, this.mClockInfo.getAodResource()[i2]);
        Drawable drawable3 = ContextCompat.getDrawable(this.mContext, this.mClockInfo.getAodResource()[i3]);
        drawable.setTint(this.mClockInfo.getAODColor());
        drawable2.setTint(this.mClockInfo.getAODColor());
        drawable3.setTint(this.mClockInfo.getAODColor());
        this.mHour1AodView.setImageDrawable(drawable);
        this.mHour2AodView.setImageDrawable(drawable2);
        this.mMinute1AodView.setImageDrawable(drawable3);
        TextView textView = this.mNotificationDateWeekText;
        Calendar calendar = this.mCalendar;
        Context context = this.mContext;
        textView.setText(calendar.format(context, context.getString(R.string.tiny_bubble_date_week_format)));
        this.mNotificationDateWeekText.setContentDescription(getDateWeekFormatForContentDesc(this.mContext));
        this.mTimeContainer.setContentDescription(this.mCalendar.format(this.mContext, this.mContext.getString(this.m24HourFormat ? R.string.tiny_clock_time_format_24 : R.string.tiny_clock_time_format_12)));
    }
}
