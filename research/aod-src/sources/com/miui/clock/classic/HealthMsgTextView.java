package com.miui.clock.classic;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.miui.clock.R;
import com.miui.clock.utils.DeviceConfig;

/* JADX INFO: loaded from: classes.dex */
public class HealthMsgTextView extends View {
    public String calories;
    public Drawable caloriesIcon;
    public String caloriesTarget;
    public float caloriesTotalWidth;
    public float caloriesWidth;
    public float icon1Top;
    public float icon2Top;
    public float icon3Top;
    public float iconLeft;
    public Context mContext;
    public float mIconWH;
    public Paint mPaint;
    public String mPlaceText;
    public Resources mResources;
    public int mainColor;
    public int minorColor;
    public Drawable sportTimeIcon;
    public String standCount;
    public String standCountTarget;
    public float standCountTotalWidth;
    public float standCountWidth;
    public String stepCount;
    public Drawable stepCountIcon;
    public String stepCountTarget;
    public float stepCountTotalWidth;
    public float stepCountWidth;
    public float text1BaseLine;
    public float text2BaseLine;
    public float text3BaseLine;
    public float textLeft;

    public HealthMsgTextView(Context context) {
        super(context);
        init(context);
    }

    public HealthMsgTextView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public HealthMsgTextView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public final void drawIcon(Canvas canvas, Drawable drawable, float f, float f2, float f3) {
        canvas.save();
        canvas.translate(f, f2);
        canvas.scale(f3, f3);
        drawable.draw(canvas);
        canvas.restore();
    }

    public final void drawText(Canvas canvas, float f, float f2, String str, float f3, float f4, String str2, Paint paint, boolean z) {
        if (!z || !str.contains(this.mPlaceText)) {
            paint.setColor(this.mainColor);
            canvas.drawText(str, f, f2, paint);
            paint.setColor(this.minorColor);
            canvas.drawText(str2, f + f3, f2, paint);
            return;
        }
        paint.setColor(this.minorColor);
        int length = str2.length();
        canvas.drawTextRun((CharSequence) str2, 0, length, 0, length, f, f2, true, paint);
        paint.setColor(this.mainColor);
        int length2 = str.length();
        canvas.drawTextRun((CharSequence) str, 0, length2, 0, length2, (f + f4) - f3, f2, true, paint);
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
        Drawable drawable = this.caloriesIcon;
        if (drawable != null) {
            drawable.setTint(i);
        }
        Drawable drawable2 = this.stepCountIcon;
        if (drawable2 != null) {
            drawable2.setTint(i);
        }
        Drawable drawable3 = this.sportTimeIcon;
        if (drawable3 != null) {
            drawable3.setTint(i);
        }
        invalidate();
    }

    public void initData(int i, int i2, int i3, int i4, int i5, int i6) {
        if (this.mResources == null) {
            Resources resources = this.mContext.getResources();
            this.mResources = resources;
            this.mainColor = resources.getColor(R.color.classic_color_white);
            this.minorColor = this.mResources.getColor(R.color.classic_color_white_60);
            this.mPlaceText = this.mResources.getString(R.string.no_data_placeholder_string);
            this.caloriesIcon = ContextCompat.getDrawable(this.mContext, R.drawable.health_icon_calories_25);
            this.stepCountIcon = ContextCompat.getDrawable(this.mContext, R.drawable.health_icon_step_count_25);
            this.sportTimeIcon = ContextCompat.getDrawable(this.mContext, R.drawable.health_icon_sport_time_25);
            Drawable drawable = this.caloriesIcon;
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), this.caloriesIcon.getIntrinsicHeight());
            Drawable drawable2 = this.stepCountIcon;
            drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), this.stepCountIcon.getIntrinsicHeight());
            Drawable drawable3 = this.sportTimeIcon;
            drawable3.setBounds(0, 0, drawable3.getIntrinsicWidth(), this.sportTimeIcon.getIntrinsicHeight());
            readSize();
        }
        this.caloriesIcon.setTint(this.mainColor);
        this.stepCountIcon.setTint(this.mainColor);
        this.sportTimeIcon.setTint(this.mainColor);
        if (i < 0) {
            this.calories = this.mPlaceText;
        } else {
            this.calories = Integer.toString(i);
        }
        if (i2 < 0) {
            this.caloriesTarget = this.mResources.getString(R.string.format_count_denominator_2, this.mPlaceText);
        } else {
            this.caloriesTarget = this.mResources.getString(R.string.format_count_denominator_2, Integer.toString(i2));
        }
        if (i3 < 0) {
            this.stepCount = this.mPlaceText;
        } else {
            this.stepCount = Integer.toString(i3);
        }
        if (i4 < 0) {
            this.stepCountTarget = this.mResources.getString(R.string.format_count_denominator_2, this.mPlaceText);
        } else {
            this.stepCountTarget = this.mResources.getString(R.string.format_count_denominator_2, Integer.toString(i4));
        }
        if (i5 < 0) {
            this.standCount = this.mPlaceText;
        } else {
            this.standCount = Integer.toString(i5);
        }
        if (i6 < 0) {
            this.standCountTarget = this.mResources.getString(R.string.format_count_denominator_2, this.mPlaceText);
        } else {
            this.standCountTarget = this.mResources.getString(R.string.format_count_denominator_2, Integer.toString(i6));
        }
        measureTextWidth();
        requestLayout();
        invalidate();
    }

    public void measureTextWidth() {
        this.caloriesWidth = this.mPaint.measureText(this.calories);
        this.stepCountWidth = this.mPaint.measureText(this.stepCount);
        this.standCountWidth = this.mPaint.measureText(this.standCount);
        this.caloriesTotalWidth = this.caloriesWidth + this.mPaint.measureText(this.caloriesTarget);
        this.stepCountTotalWidth = this.stepCountWidth + this.mPaint.measureText(this.stepCountTarget);
        this.standCountTotalWidth = this.standCountWidth + this.mPaint.measureText(this.standCountTarget);
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
        super.onDraw(canvas);
        if (this.mResources == null) {
            return;
        }
        float intrinsicWidth2 = this.mIconWH / this.caloriesIcon.getIntrinsicWidth();
        boolean zIsRTL = DeviceConfig.isRTL();
        if (zIsRTL) {
            float measuredWidth = getMeasuredWidth();
            intrinsicWidth = (measuredWidth - this.iconLeft) - (this.caloriesIcon.getIntrinsicWidth() * intrinsicWidth2);
            float f4 = this.textLeft;
            float f5 = (measuredWidth - f4) - this.caloriesTotalWidth;
            float f6 = (measuredWidth - f4) - this.stepCountTotalWidth;
            f2 = (measuredWidth - f4) - this.standCountTotalWidth;
            f3 = f6;
            f = f5;
        } else {
            intrinsicWidth = this.iconLeft;
            f = this.textLeft;
            f2 = f;
            f3 = f2;
        }
        float f7 = intrinsicWidth;
        drawIcon(canvas, this.caloriesIcon, f7, this.icon1Top, intrinsicWidth2);
        drawIcon(canvas, this.stepCountIcon, f7, this.icon2Top, intrinsicWidth2);
        drawIcon(canvas, this.sportTimeIcon, f7, this.icon3Top, intrinsicWidth2);
        drawText(canvas, f, this.text1BaseLine, this.calories, this.caloriesWidth, this.caloriesTotalWidth, this.caloriesTarget, this.mPaint, zIsRTL);
        drawText(canvas, f3, this.text2BaseLine, this.stepCount, this.stepCountWidth, this.stepCountTotalWidth, this.stepCountTarget, this.mPaint, zIsRTL);
        drawText(canvas, f2, this.text3BaseLine, this.standCount, this.standCountWidth, this.standCountTotalWidth, this.standCountTarget, this.mPaint, zIsRTL);
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int iMax = View.MeasureSpec.getMode(i) != 1073741824 ? (int) (this.textLeft + Math.max(Math.max(this.caloriesTotalWidth, this.stepCountTotalWidth), this.standCountTotalWidth)) : View.MeasureSpec.getSize(i);
        int dimen = View.MeasureSpec.getMode(i2) != 1073741824 ? getDimen(R.dimen.health_view_height) : View.MeasureSpec.getSize(i2);
        Log.i("HealthMsgTextView", "w = " + iMax + ", h = " + dimen);
        setMeasuredDimension(iMax, dimen);
    }

    public void readSize() {
        this.mPaint.setTextSize(getDimen(R.dimen.health_view_text_size));
        this.iconLeft = getDimenF(R.dimen.health_view_icon_margin_start);
        this.icon1Top = getDimenF(R.dimen.health_view_icon1_margin_top);
        this.icon2Top = getDimenF(R.dimen.health_view_icon2_margin_top);
        this.icon3Top = getDimenF(R.dimen.health_view_icon3_margin_top);
        this.textLeft = getDimenF(R.dimen.health_view_text_margin_start);
        this.text1BaseLine = getDimenF(R.dimen.health_view_text1_base_line);
        this.text2BaseLine = getDimenF(R.dimen.health_view_text2_base_line);
        this.text3BaseLine = getDimenF(R.dimen.health_view_text3_base_line);
        this.mIconWH = getDimenF(R.dimen.health_icon_16);
    }

    public void setTypeface(Typeface typeface) {
        this.mPaint.setTypeface(typeface);
    }
}
