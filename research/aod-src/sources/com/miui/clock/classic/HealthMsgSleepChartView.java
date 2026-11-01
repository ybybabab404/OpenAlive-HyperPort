package com.miui.clock.classic;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.miui.clock.R;
import com.miui.clock.utils.DeviceConfig;

/* JADX INFO: loaded from: classes.dex */
public class HealthMsgSleepChartView extends View {
    public float bottomTextBaseLine;
    public float descPlaceMarginTop;
    public float iconDescMarginStart;
    public float iconMarginStart;
    public float iconMarginTop;
    public float iconScale;
    public Context mContext;
    public int mDrawSmallLineSize;
    public Drawable mIcon;
    public Paint mPaint;
    public String mPlaceholderText;
    public Resources mResources;
    public String mSleepDesc;
    public float mSleepDescWidth;
    public String mSleepMinute;
    public Drawable mTextPlaceHolder;
    public float mTextWidth;
    public int mainColor;
    public int minorColor;
    public float rectH;
    public float rectInterval;
    public float rectLineH;
    public float rectLineRadius;
    public float rectLineW;
    public float rectMarginStart;
    public float rectMarginTop;
    public float rectRadius;
    public float rectW;
    public float textMarginStart;
    public float topTextBaseLine;

    public HealthMsgSleepChartView(Context context) {
        super(context);
        init(context);
    }

    public HealthMsgSleepChartView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public HealthMsgSleepChartView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public final int getDimen(int i) {
        return (int) (this.mResources.getDimensionPixelSize(i) * DeviceConfig.calculateScale(getContext()));
    }

    public final float getDimenF(int i) {
        return this.mResources.getDimension(i) * DeviceConfig.calculateScale(getContext());
    }

    public String getSleepDesc() {
        return this.mSleepDesc;
    }

    public String getSleepMinute() {
        return this.mSleepMinute;
    }

    public final void init(Context context) {
        this.mContext = context;
        this.mPaint = new Paint();
    }

    public void initColor(int i, int i2) {
        if (i != 0) {
            this.mainColor = i;
        }
        if (i2 != 0) {
            this.minorColor = i2;
        }
        Drawable drawable = this.mIcon;
        if (drawable != null) {
            drawable.setTint(i);
        }
        Drawable drawable2 = this.mTextPlaceHolder;
        if (drawable2 != null) {
            drawable2.setTint(i);
        }
        invalidate();
    }

    public void initData(int i) {
        if (this.mResources == null) {
            Resources resources = this.mContext.getResources();
            this.mResources = resources;
            this.mPlaceholderText = resources.getString(R.string.no_data_placeholder_string);
            this.mIcon = ContextCompat.getDrawable(this.mContext, R.drawable.health_icon_sleep_25);
            this.mTextPlaceHolder = ContextCompat.getDrawable(this.mContext, R.drawable.classic_placeholder_text_large);
            if (this.mainColor == 0) {
                this.mainColor = this.mResources.getColor(R.color.classic_color_white);
            }
            if (this.minorColor == 0) {
                this.minorColor = this.mResources.getColor(R.color.classic_color_white_60);
            }
            Drawable drawable = this.mIcon;
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), this.mIcon.getIntrinsicHeight());
            Drawable drawable2 = this.mTextPlaceHolder;
            drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), this.mTextPlaceHolder.getIntrinsicHeight());
            readSize();
        }
        if (i <= 0) {
            this.mSleepDesc = "";
            this.mDrawSmallLineSize = 0;
            Resources resources2 = this.mResources;
            int i2 = R.string.health_chart_sleep_time_format;
            String str = this.mPlaceholderText;
            this.mSleepMinute = resources2.getString(i2, str, str);
            this.mSleepDescWidth = 0.0f;
        } else {
            if (i > 645) {
                this.mSleepDesc = this.mResources.getString(R.string.health_chart_sleep_time_normal);
                this.mDrawSmallLineSize = 2;
            } else if (i > 480) {
                this.mSleepDesc = this.mResources.getString(R.string.health_chart_sleep_time_very_good);
                this.mDrawSmallLineSize = 4;
            } else if (i > 360) {
                this.mSleepDesc = this.mResources.getString(R.string.health_chart_sleep_time_good);
                this.mDrawSmallLineSize = 3;
            } else if (i > 300) {
                this.mSleepDesc = this.mResources.getString(R.string.health_chart_sleep_time_normal);
                this.mDrawSmallLineSize = 2;
            } else {
                this.mSleepDesc = this.mResources.getString(R.string.health_chart_sleep_time_low);
                this.mDrawSmallLineSize = 1;
            }
            this.mSleepMinute = this.mResources.getString(R.string.health_chart_sleep_time_format, Integer.toString(i / 60), Integer.toString(i % 60));
            this.mSleepDescWidth = this.mPaint.measureText(this.mSleepDesc);
        }
        this.mTextWidth = this.mPaint.measureText(this.mSleepMinute);
        this.mIcon.setTint(this.mainColor);
        this.mTextPlaceHolder.setTint(this.mainColor);
        requestLayout();
        invalidate();
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        requestLayout();
        invalidate();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        float intrinsicWidth;
        float f;
        float f2;
        float f3;
        Canvas canvas2;
        float f4;
        float f5;
        float intrinsicWidth2;
        super.onDraw(canvas);
        if (this.mResources == null) {
            return;
        }
        boolean zIsRTL = DeviceConfig.isRTL();
        if (zIsRTL) {
            float measuredWidth = getMeasuredWidth();
            f = measuredWidth - ((this.rectMarginStart + (this.rectW * 4.0f)) + (this.rectInterval * 3.0f));
            intrinsicWidth = (measuredWidth - this.iconMarginStart) - this.mIcon.getIntrinsicWidth();
            f2 = (measuredWidth - this.textMarginStart) - this.mTextWidth;
            if (TextUtils.isEmpty(this.mSleepDesc)) {
                f5 = measuredWidth - this.iconDescMarginStart;
                intrinsicWidth2 = this.mTextPlaceHolder.getIntrinsicWidth();
            } else {
                f5 = measuredWidth - this.iconDescMarginStart;
                intrinsicWidth2 = this.mSleepDescWidth;
            }
            f3 = f5 - intrinsicWidth2;
        } else {
            intrinsicWidth = this.iconMarginStart;
            f = this.rectMarginStart;
            f2 = this.textMarginStart;
            f3 = this.iconDescMarginStart;
        }
        float f6 = f3;
        float f7 = f;
        float f8 = intrinsicWidth;
        float f9 = f2;
        this.mPaint.setColor(this.mainColor);
        if (zIsRTL) {
            int length = this.mSleepMinute.length();
            canvas2 = canvas;
            canvas2.drawTextRun((CharSequence) this.mSleepMinute, 0, length, 0, length, f9, this.topTextBaseLine, true, this.mPaint);
        } else {
            canvas2 = canvas;
            canvas.drawText(this.mSleepMinute, f9, this.topTextBaseLine, this.mPaint);
        }
        this.mPaint.setColor(this.minorColor);
        if (TextUtils.isEmpty(this.mSleepDesc)) {
            canvas.translate(f6, this.descPlaceMarginTop);
            this.mTextPlaceHolder.draw(canvas);
            canvas.translate(-f6, -this.descPlaceMarginTop);
        } else {
            canvas.drawText(this.mSleepDesc, f6, this.bottomTextBaseLine, this.mPaint);
        }
        canvas.save();
        canvas.translate(f8, this.iconMarginTop);
        float f10 = this.iconScale;
        canvas.scale(f10, f10);
        this.mIcon.draw(canvas);
        canvas.restore();
        float f11 = this.rectMarginTop;
        float f12 = f11 + this.rectH;
        float f13 = f11 + this.rectLineH;
        int i = 0;
        while (i < 4) {
            float f14 = (i * (this.rectW + this.rectInterval)) + f7;
            this.mPaint.setColor(this.minorColor);
            float f15 = this.rectMarginTop;
            float f16 = this.rectW + f14;
            float f17 = this.rectRadius;
            canvas2.drawRoundRect(f14, f15, f16, f12, f17, f17, this.mPaint);
            float f18 = f12;
            if (!zIsRTL ? i < this.mDrawSmallLineSize : i >= 4 - this.mDrawSmallLineSize) {
                f4 = f13;
            } else {
                this.mPaint.setColor(this.mainColor);
                float f19 = this.rectMarginTop;
                float f20 = f14 + this.rectLineW;
                float f21 = this.rectLineRadius;
                f4 = f13;
                canvas.drawRoundRect(f14, f19, f20, f4, f21, f21, this.mPaint);
            }
            i++;
            canvas2 = canvas;
            f13 = f4;
            f12 = f18;
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int size;
        if (View.MeasureSpec.getMode(i) != 1073741824) {
            size = (int) (this.rectMarginStart + this.textMarginStart + Math.max(this.mTextWidth, (((this.mTextPlaceHolder == null || !TextUtils.isEmpty(this.mSleepDesc)) ? this.mSleepDescWidth : this.mTextPlaceHolder.getIntrinsicWidth()) + this.iconDescMarginStart) - this.iconMarginStart));
        } else {
            size = View.MeasureSpec.getSize(i);
        }
        int dimen = View.MeasureSpec.getMode(i2) != 1073741824 ? getDimen(R.dimen.health_view_height) : View.MeasureSpec.getSize(i2);
        Log.i("HealthMsgSleepChartView", "w = " + size + ", h = " + dimen);
        setMeasuredDimension(size, dimen);
    }

    public void readSize() {
        this.mPaint.setTextSize(getDimenF(R.dimen.health_chart_text_size));
        this.iconMarginStart = getDimenF(R.dimen.health_chart_icon_margin_start);
        this.iconMarginTop = getDimenF(R.dimen.health_chart_icon_margin_top);
        this.descPlaceMarginTop = getDimenF(R.dimen.health_chart_desc_place_margin_top);
        this.rectMarginTop = getDimenF(R.dimen.health_chart_sleep_rect_margin_top);
        this.rectMarginStart = getDimenF(R.dimen.health_chart_sleep_rect_margin_start);
        this.rectRadius = getDimenF(R.dimen.health_chart_sleep_rect_radius);
        this.rectInterval = getDimenF(R.dimen.health_chart_sleep_rect_interval);
        this.rectW = getDimenF(R.dimen.health_chart_sleep_rect_w);
        this.rectH = getDimenF(R.dimen.health_chart_sleep_rect_h);
        this.rectLineW = getDimenF(R.dimen.health_chart_sleep_rect_line_w);
        this.rectLineH = getDimenF(R.dimen.health_chart_sleep_rect_line_h);
        this.rectLineRadius = getDimenF(R.dimen.health_chart_sleep_rect_line_radius);
        this.textMarginStart = getDimenF(R.dimen.health_chart_text_margin_start);
        this.topTextBaseLine = getDimenF(R.dimen.health_chart_top_text_base_line);
        this.bottomTextBaseLine = getDimenF(R.dimen.health_chart_bottom_text_base_line);
        this.iconDescMarginStart = getDimenF(R.dimen.health_chart_icon_desc_margin_start);
        this.iconScale = getDimenF(R.dimen.icon_placeholder_w_h) / this.mIcon.getIntrinsicWidth();
    }

    public void setTypeface(Typeface typeface) {
        this.mPaint.setTypeface(typeface);
    }
}
