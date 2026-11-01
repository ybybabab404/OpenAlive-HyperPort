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
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class HealthMsgChartView extends View {
    public float bottomTextBaseLine;
    public final List dataList;
    public String denominatorTarget;
    public float iconDescMarginStart;
    public int iconMarginStart;
    public int iconMarginTop;
    public float iconTargetWidth;
    public float lineH;
    public float lineInterval;
    public int lineMarginStart;
    public int lineMarginTop;
    public int lineRadius;
    public float lineW;
    public Context mContext;
    public Drawable mIcon;
    public String mIconDesc;
    public float mIconDescWidth;
    public float mNowTextWidth;
    public Paint mPaint;
    public String mPlaceholderText;
    public Resources mResources;
    public float mTotalTextWidth;
    public int mainColor;
    public int max;
    public int minorColor;
    public String now;
    public float textMarginStart;
    public float topTextBaseLine;

    public HealthMsgChartView(Context context) {
        super(context);
        this.dataList = new ArrayList();
        init(context);
    }

    public HealthMsgChartView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.dataList = new ArrayList();
        init(context);
    }

    public HealthMsgChartView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.dataList = new ArrayList();
        init(context);
    }

    public final int getDimen(int i) {
        return (int) (this.mResources.getDimensionPixelSize(i) * DeviceConfig.calculateScale(getContext()));
    }

    public final float getDimenF(int i) {
        return this.mResources.getDimension(i) * DeviceConfig.calculateScale(getContext());
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
        invalidate();
    }

    public void initData(int i, int i2, int i3, List list) {
        if (this.mResources == null) {
            Resources resources = this.mContext.getResources();
            this.mResources = resources;
            this.mPlaceholderText = resources.getString(R.string.no_data_placeholder_string);
            if (this.mainColor == 0) {
                this.mainColor = this.mResources.getColor(R.color.classic_color_white);
            }
            if (this.minorColor == 0) {
                this.minorColor = this.mResources.getColor(R.color.classic_color_white_60);
            }
            readSize();
        }
        if (i == 501) {
            this.mIconDesc = this.mResources.getString(R.string.health_chart_step_count_unit);
            this.mIcon = ContextCompat.getDrawable(this.mContext, R.drawable.health_icon_step_count_25);
        } else if (i != 503) {
            this.mIconDesc = "";
            this.mIcon = ContextCompat.getDrawable(this.mContext, R.drawable.health_icon_sport_time_25);
        } else {
            this.mIconDesc = this.mResources.getString(R.string.health_chart_calories_unit);
            this.mIcon = ContextCompat.getDrawable(this.mContext, R.drawable.health_icon_calories_25);
        }
        if (i2 < 0) {
            String str = this.mPlaceholderText;
            this.now = str;
            this.denominatorTarget = this.mResources.getString(R.string.format_count_denominator_2, str);
        } else {
            this.now = Integer.toString(i2);
            this.denominatorTarget = this.mResources.getString(R.string.format_count_denominator_2, Integer.toString(i3));
        }
        this.dataList.clear();
        if (list != null) {
            this.dataList.addAll(list);
        }
        this.max = 0;
        for (Integer num : this.dataList) {
            if (num.intValue() > this.max) {
                this.max = num.intValue();
            }
        }
        while (this.dataList.size() < 24) {
            this.dataList.add(0, 0);
        }
        Drawable drawable = this.mIcon;
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), this.mIcon.getIntrinsicHeight());
        this.mIcon.setTint(this.mainColor);
        measureTextWidth();
        requestLayout();
        invalidate();
    }

    public void measureTextWidth() {
        float fMeasureText = this.mPaint.measureText(this.now);
        this.mNowTextWidth = fMeasureText;
        this.mTotalTextWidth = fMeasureText + this.mPaint.measureText(this.denominatorTarget);
        this.mIconDescWidth = this.mPaint.measureText(this.mIconDesc);
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
        boolean z;
        super.onDraw(canvas);
        if (this.mResources == null) {
            return;
        }
        boolean zIsRTL = DeviceConfig.isRTL();
        if (zIsRTL) {
            int measuredWidth = getMeasuredWidth();
            float f4 = measuredWidth;
            f = f4 - ((this.lineMarginStart + (this.lineW * 24.0f)) + (this.lineInterval * 23.0f));
            intrinsicWidth = (measuredWidth - this.iconMarginStart) - this.mIcon.getIntrinsicWidth();
            f2 = (f4 - this.textMarginStart) - this.mTotalTextWidth;
            f3 = (f4 - this.iconDescMarginStart) - this.mIconDescWidth;
        } else {
            intrinsicWidth = this.iconMarginStart;
            f = this.lineMarginStart;
            f2 = this.textMarginStart;
            f3 = this.iconDescMarginStart;
        }
        float f5 = intrinsicWidth;
        float f6 = f3;
        float f7 = f;
        float f8 = f2;
        if (zIsRTL && this.now.contains(this.mPlaceholderText)) {
            this.mPaint.setColor(this.minorColor);
            int length = this.denominatorTarget.length();
            canvas.drawTextRun((CharSequence) this.denominatorTarget, 0, length, 0, length, f8, this.topTextBaseLine, true, this.mPaint);
            this.mPaint.setColor(this.mainColor);
            int length2 = this.now.length();
            canvas2 = canvas;
            z = zIsRTL;
            canvas2.drawTextRun(this.now, 0, length2, 0, length2, (f8 + this.mTotalTextWidth) - this.mNowTextWidth, this.topTextBaseLine, z, this.mPaint);
        } else {
            canvas2 = canvas;
            z = zIsRTL;
            this.mPaint.setColor(this.mainColor);
            canvas2.drawText(this.now, f8, this.topTextBaseLine, this.mPaint);
            this.mPaint.setColor(this.minorColor);
            canvas2.drawText(this.denominatorTarget, f8 + this.mNowTextWidth, this.topTextBaseLine, this.mPaint);
        }
        if (!TextUtils.isEmpty(this.mIconDesc)) {
            canvas2.drawText(this.mIconDesc, f6, this.bottomTextBaseLine, this.mPaint);
        }
        canvas2.save();
        canvas2.translate(f5, this.iconMarginTop);
        float intrinsicWidth2 = this.iconTargetWidth / this.mIcon.getIntrinsicWidth();
        canvas2.scale(intrinsicWidth2, intrinsicWidth2);
        this.mIcon.draw(canvas2);
        canvas2.restore();
        this.mPaint.setColor(this.minorColor);
        int i = 0;
        while (i < 24) {
            this.mPaint.setColor(this.minorColor);
            float f9 = this.lineInterval;
            float f10 = this.lineW;
            float f11 = (i * (f9 + f10)) + f7;
            int i2 = this.lineMarginTop;
            float f12 = i2 + this.lineH;
            int i3 = this.lineRadius;
            canvas2.drawRoundRect(f11, i2, f10 + f11, f12, i3, i3, this.mPaint);
            List list = this.dataList;
            float fIntValue = ((Integer) (z ? list.get(23 - i) : list.get(i))).intValue();
            if (fIntValue > 0.0f) {
                this.mPaint.setColor(this.mainColor);
                float f13 = f12 - ((fIntValue / this.max) * this.lineH);
                float f14 = f11 + this.lineW;
                int i4 = this.lineRadius;
                canvas.drawRoundRect(f11, f13, f14, f12, i4, i4, this.mPaint);
            }
            i++;
            canvas2 = canvas;
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int size;
        if (View.MeasureSpec.getMode(i) != 1073741824) {
            int i3 = this.lineMarginStart;
            size = (int) Math.max(i3 + this.textMarginStart + this.mTotalTextWidth, i3 + this.iconDescMarginStart + this.mIconDescWidth);
        } else {
            size = View.MeasureSpec.getSize(i);
        }
        int dimen = View.MeasureSpec.getMode(i2) != 1073741824 ? getDimen(R.dimen.humidity_view_height) : View.MeasureSpec.getSize(i2);
        Log.i("HealthMsgChartView", "w = " + size + ", h = " + dimen);
        setMeasuredDimension(size, dimen);
    }

    public void readSize() {
        this.mPaint.setTextSize(getDimen(R.dimen.health_chart_text_size));
        this.iconMarginStart = getDimen(R.dimen.health_chart_icon_margin_start);
        this.iconMarginTop = getDimen(R.dimen.health_chart_icon_margin_top);
        this.iconTargetWidth = getDimen(R.dimen.icon_placeholder_w_h);
        this.lineMarginTop = getDimen(R.dimen.health_chart_line_margin_top);
        this.lineMarginStart = getDimen(R.dimen.health_chart_line_margin_start);
        this.lineRadius = getDimen(R.dimen.health_chart_line_radius);
        this.textMarginStart = getDimenF(R.dimen.health_chart_text_margin_start);
        this.topTextBaseLine = getDimenF(R.dimen.health_chart_top_text_base_line);
        this.bottomTextBaseLine = getDimenF(R.dimen.health_chart_bottom_text_base_line);
        this.iconDescMarginStart = getDimenF(R.dimen.health_chart_icon_desc_margin_start);
        this.lineInterval = getDimenF(R.dimen.health_chart_line_interval);
        this.lineW = getDimenF(R.dimen.health_chart_line_w);
        this.lineH = getDimenF(R.dimen.health_chart_line_h);
    }

    public void setTypeface(Typeface typeface) {
        this.mPaint.setTypeface(typeface);
    }
}
