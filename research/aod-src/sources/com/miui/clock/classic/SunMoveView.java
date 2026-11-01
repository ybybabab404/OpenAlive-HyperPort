package com.miui.clock.classic;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.miui.clock.R;
import com.miui.clock.utils.DateFormatUtils;
import com.miui.clock.utils.DeviceConfig;

/* JADX INFO: loaded from: classes.dex */
public class SunMoveView extends View {
    public float arrowMarginStart;
    public float arrowMarginTop;
    public float arrowPx;
    public String desc;
    public boolean isRound;
    public boolean isRtl;
    public float line1Long;
    public float line1P0y;
    public float line1P1x;
    public float line1P1y;
    public float line1P2x;
    public float line1P2y;
    public float line1P3x;
    public float line1P3y;
    public float line1Width;
    public float line2Long;
    public float line2MarginStartX;
    public float line2MarginStartY;
    public float line2Width;
    public float lineBaseSize;
    public Drawable mArrowDown;
    public Drawable mArrowIcon;
    public Drawable mArrowUp;
    public Context mContext;
    public float mIconScale;
    public Paint mPaint;
    public Resources mResources;
    public int mainColor;
    public int minorColor;
    public float nowTime;
    public float p0xLarge;
    public float p0xSmall;
    public float p0y;
    public float p1y;
    public float p2y;
    public float p3y;
    public float p4y;
    public float p5y;
    public float p6y;
    public Path path;
    public float pointStartX;
    public int style;
    public float sunPadding;
    public float sunRadius;
    public String sunTimeText;
    public float sunTimeTextBaseLine;
    public float sunTimeTextMarginIcon;
    public float sunTimeTextWidth;
    public int sunriseTime;
    public int sunriseTomorrowTime;
    public int sunsetTime;

    public SunMoveView(Context context) {
        super(context);
        init(context);
    }

    public SunMoveView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public SunMoveView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public final float getBezierCurveLocation(float f, float f2, float f3, float f4) {
        float f5 = 1.0f - f;
        return (f2 * f5 * f5) + (f3 * 2.0f * f5 * f) + (f4 * f * f);
    }

    public String getDesc() {
        return this.desc;
    }

    public final float getDimen(int i) {
        return this.mResources.getDimension(i) * DeviceConfig.calculateScale(getContext());
    }

    public final int getDimenInt(int i) {
        return (int) (this.mResources.getDimensionPixelSize(i) * DeviceConfig.calculateScale(getContext()));
    }

    public final String getMinuteTime(int i) {
        if (i == 0) {
            return "00";
        }
        if (i >= 10) {
            return Integer.toString(i);
        }
        return "0" + i;
    }

    public final void init(Context context) {
        this.mContext = context;
        this.mPaint = new Paint();
        this.isRtl = DeviceConfig.isRTL();
    }

    public void initColor(int i, int i2) {
        if (i != 0) {
            this.mainColor = i;
        }
        if (i2 != 0) {
            this.minorColor = i2;
        }
        Drawable drawable = this.mArrowDown;
        if (drawable != null) {
            drawable.setTint(i2);
        }
        Drawable drawable2 = this.mArrowUp;
        if (drawable2 != null) {
            drawable2.setTint(i2);
        }
        invalidate();
    }

    public void initData(int i) {
        initData(this.sunriseTime, this.sunriseTomorrowTime, this.sunsetTime, i);
    }

    public void initData(int i, int i2, int i3, int i4) {
        if (this.mResources == null) {
            this.mResources = this.mContext.getResources();
            this.mPaint.setFontFeatureSettings("ss01");
            this.mPaint.setStyle(Paint.Style.FILL);
            if (this.mainColor == 0) {
                this.mainColor = this.mResources.getColor(R.color.classic_color_white);
            }
            if (this.minorColor == 0) {
                this.minorColor = this.mResources.getColor(R.color.classic_color_white_60);
            }
            this.mArrowUp = ContextCompat.getDrawable(this.mContext, R.drawable.weather_icon_sun_up);
            this.mArrowDown = ContextCompat.getDrawable(this.mContext, R.drawable.weather_icon_sun_down);
            Drawable drawable = this.mArrowUp;
            drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), this.mArrowUp.getIntrinsicHeight());
            Drawable drawable2 = this.mArrowDown;
            drawable2.setBounds(0, 0, drawable2.getIntrinsicWidth(), this.mArrowDown.getIntrinsicHeight());
            readSize();
            this.path = new Path();
        }
        initStyleSize();
        this.sunriseTime = i;
        this.sunsetTime = i3;
        this.sunriseTomorrowTime = i2;
        this.nowTime = i4;
        if (i4 < 0 || (i <= 0 && i3 <= 0 && i2 <= 0)) {
            this.nowTime = -1.0f;
            this.pointStartX = this.p0xSmall;
            this.sunTimeText = "--:--";
            float fMeasureText = this.mPaint.measureText("--:--");
            this.sunTimeTextWidth = fMeasureText;
            Drawable drawable3 = this.mArrowDown;
            this.mArrowIcon = drawable3;
            this.arrowPx = (((this.line2MarginStartX + this.line2Long) - fMeasureText) - this.sunTimeTextMarginIcon) - (drawable3.getIntrinsicWidth() * this.mIconScale);
            this.desc = getResources().getString(R.string.weather_no_data);
        } else if (i4 < i || i4 > i3) {
            this.pointStartX = this.p0xLarge;
            if (i4 < i) {
                this.sunTimeText = (i / 60) + ":" + getMinuteTime(i % 60);
                this.desc = getResources().getString(R.string.sunrise_time_format_desc, this.sunTimeText);
            } else {
                this.sunTimeText = (i2 / 60) + ":" + getMinuteTime(i2 % 60);
                this.desc = getResources().getString(R.string.sunrise_time_format_tomorrow_desc, this.sunTimeText);
            }
            this.arrowPx = this.arrowMarginStart;
            this.sunTimeTextWidth = this.mPaint.measureText(this.sunTimeText);
            this.mArrowIcon = this.mArrowUp;
        } else {
            this.pointStartX = this.p0xSmall;
            if (DateFormatUtils.is24HourFormat(this.mContext)) {
                this.sunTimeText = (i3 / 60) + ":" + getMinuteTime(i3 % 60);
            } else {
                StringBuilder sb = new StringBuilder();
                sb.append((i3 / 60) - 12);
                sb.append(":");
                sb.append(getMinuteTime(i3 % 60));
                this.sunTimeText = sb.toString();
            }
            this.desc = getResources().getString(R.string.sunset_time_format_desc, this.sunTimeText);
            float fMeasureText2 = this.mPaint.measureText(this.sunTimeText);
            this.sunTimeTextWidth = fMeasureText2;
            Drawable drawable4 = this.mArrowDown;
            this.mArrowIcon = drawable4;
            this.arrowPx = (((this.line2MarginStartX + this.line2Long) - fMeasureText2) - this.sunTimeTextMarginIcon) - (drawable4.getIntrinsicWidth() * this.mIconScale);
        }
        this.mArrowDown.setTint(this.minorColor);
        this.mArrowUp.setTint(this.minorColor);
        float f = this.line1P0y;
        this.p6y = f;
        this.p0y = f;
        float f2 = this.line1P1y;
        this.p5y = f2;
        this.p1y = f2;
        float f3 = this.line1P2y;
        this.p4y = f3;
        this.p2y = f3;
        this.p3y = this.line1P3y - (this.lineBaseSize * 4.0f);
        requestLayout();
        invalidate();
    }

    public final void initStyleSize() {
        this.isRound = false;
        switch (this.style) {
            case 21:
            case 26:
                float f = this.lineBaseSize;
                this.sunRadius = 6.0f * f;
                this.line1Width = 4.0f * f;
                this.line2Width = f * 2.0f;
                break;
            case 22:
                float f2 = this.lineBaseSize;
                this.sunRadius = 5.0f * f2;
                this.line1Width = 3.0f * f2;
                this.line2Width = f2 * 1.5f;
                break;
            case 23:
                float f3 = this.lineBaseSize;
                this.sunRadius = 7.0f * f3;
                this.line1Width = 5.0f * f3;
                this.line2Width = f3 * 3.0f;
                break;
            case 24:
            case 25:
                this.isRound = true;
                float f4 = this.lineBaseSize;
                this.sunRadius = 7.0f * f4;
                this.line1Width = 5.0f * f4;
                this.line2Width = f4 * 3.0f;
                break;
        }
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        if (this.isRtl != DeviceConfig.isRTL()) {
            this.isRtl = !this.isRtl;
            requestLayout();
            invalidate();
        }
    }

    /* JADX WARN: Code duplicated, block: B:28:0x015a  */
    /* JADX WARN: Code duplicated, block: B:30:0x0161  */
    /* JADX WARN: Code duplicated, block: B:32:0x0168  */
    /* JADX WARN: Code duplicated, block: B:33:0x017c  */
    /* JADX WARN: Code duplicated, block: B:35:0x0183  */
    /* JADX WARN: Code duplicated, block: B:38:0x01a6  */
    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        float f;
        float f2;
        Paint.Cap strokeCap;
        int i;
        int i2;
        float bezierCurveLocation;
        int i3;
        super.onDraw(canvas);
        if (this.mResources == null || getVisibility() == 8) {
            return;
        }
        if (DeviceConfig.isRTL()) {
            float measuredWidth = getMeasuredWidth();
            float f3 = this.line2MarginStartX;
            f = (measuredWidth - f3) - this.line2Long;
            f2 = (f - f3) + this.pointStartX;
        } else {
            f = this.line2MarginStartX;
            f2 = this.pointStartX;
        }
        float bezierCurveLocation2 = f2;
        float f4 = this.line1P1x;
        float f5 = bezierCurveLocation2 + f4;
        float f6 = this.line1P2x;
        float f7 = bezierCurveLocation2 + f6;
        float f8 = bezierCurveLocation2 + this.line1P3x;
        float f9 = this.line1Long;
        float f10 = (bezierCurveLocation2 + f9) - f6;
        float f11 = (bezierCurveLocation2 + f9) - f4;
        float f12 = bezierCurveLocation2 + f9;
        float intrinsicWidth = this.arrowPx + this.mArrowIcon.getIntrinsicWidth() + this.sunTimeTextMarginIcon;
        float f13 = this.arrowPx;
        this.mPaint.setColor(this.minorColor);
        canvas.save();
        canvas.translate(f13, this.arrowMarginTop);
        float f14 = this.mIconScale;
        canvas.scale(f14, f14);
        this.mArrowIcon.draw(canvas);
        canvas.restore();
        Paint paint = this.mPaint;
        Paint.Style style = Paint.Style.FILL;
        paint.setStyle(style);
        canvas.drawText(this.sunTimeText, intrinsicWidth, this.sunTimeTextBaseLine, this.mPaint);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mPaint.setStrokeWidth(this.line1Width);
        this.mPaint.setColor(this.minorColor);
        this.path.reset();
        if (this.isRound) {
            strokeCap = this.mPaint.getStrokeCap();
            this.mPaint.setStrokeCap(Paint.Cap.ROUND);
        } else {
            strokeCap = null;
        }
        Paint.Cap cap = strokeCap;
        this.path.moveTo(bezierCurveLocation2, this.p0y);
        this.path.quadTo(f5, this.p1y, f7, this.p2y);
        canvas.drawPath(this.path, this.mPaint);
        this.path.reset();
        this.path.moveTo(f10, this.p4y);
        this.path.quadTo(f11, this.p5y, f12, this.p6y);
        canvas.drawPath(this.path, this.mPaint);
        this.mPaint.setColor(this.mainColor);
        this.mPaint.setStrokeWidth(this.line2Width);
        float f15 = this.line2MarginStartY;
        canvas.drawLine(f, f15, this.line2Long + f, f15, this.mPaint);
        if (this.isRound) {
            this.mPaint.setStrokeCap(cap);
        }
        this.mPaint.setStrokeWidth(this.line1Width);
        this.path.reset();
        this.path.moveTo(f7, this.p2y);
        this.path.quadTo(f8, this.p3y, f10, this.p4y);
        canvas.drawPath(this.path, this.mPaint);
        float f16 = this.nowTime;
        if (f16 < 0.0f) {
            return;
        }
        if (f16 >= 0.0f) {
            int i4 = this.sunriseTime;
            if (f16 <= i4) {
                float f17 = f16 / i4;
                float f18 = f7 - bezierCurveLocation2;
                float f19 = this.sunPadding;
                float f20 = (((f18 - f19) * f17) + f19) / f18;
                bezierCurveLocation2 = getBezierCurveLocation(f20, bezierCurveLocation2, f5, f7);
                bezierCurveLocation = getBezierCurveLocation(f20, this.p0y, this.p1y, this.p2y);
            } else {
                i = this.sunriseTime;
                if (f16 > i) {
                    i3 = this.sunsetTime;
                    if (f16 < i3) {
                        float f21 = (f16 - i) / (i3 - i);
                        bezierCurveLocation2 = getBezierCurveLocation(f21, f7, f8, f10);
                        bezierCurveLocation = getBezierCurveLocation(f21, this.p2y, this.p3y, this.p4y);
                    } else {
                        i2 = this.sunsetTime;
                        if (f16 >= i2 || f16 >= 1440) {
                            bezierCurveLocation = this.p0y;
                        } else {
                            float f22 = (f16 - i2) / (1440 - i2);
                            float f23 = f12 - f10;
                            float f24 = ((f23 - this.sunPadding) * f22) / f23;
                            bezierCurveLocation2 = getBezierCurveLocation(f24, f10, f11, f12);
                            bezierCurveLocation = getBezierCurveLocation(f24, this.p4y, this.p5y, this.p6y);
                        }
                    }
                } else {
                    i2 = this.sunsetTime;
                    if (f16 >= i2) {
                        bezierCurveLocation = this.p0y;
                    } else {
                        bezierCurveLocation = this.p0y;
                    }
                }
            }
        } else {
            i = this.sunriseTime;
            if (f16 > i) {
                i3 = this.sunsetTime;
                if (f16 < i3) {
                    float f25 = (f16 - i) / (i3 - i);
                    bezierCurveLocation2 = getBezierCurveLocation(f25, f7, f8, f10);
                    bezierCurveLocation = getBezierCurveLocation(f25, this.p2y, this.p3y, this.p4y);
                } else {
                    i2 = this.sunsetTime;
                    if (f16 >= i2) {
                        bezierCurveLocation = this.p0y;
                    } else {
                        bezierCurveLocation = this.p0y;
                    }
                }
            } else {
                i2 = this.sunsetTime;
                if (f16 >= i2) {
                    bezierCurveLocation = this.p0y;
                } else {
                    bezierCurveLocation = this.p0y;
                }
            }
        }
        this.mPaint.setStyle(style);
        canvas.drawCircle(bezierCurveLocation2, bezierCurveLocation, this.sunRadius, this.mPaint);
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int dimenInt = View.MeasureSpec.getMode(i) != 1073741824 ? getDimenInt(R.dimen.sun_move_width) : View.MeasureSpec.getSize(i);
        int dimenInt2 = View.MeasureSpec.getMode(i2) != 1073741824 ? getDimenInt(R.dimen.sun_move_height) : View.MeasureSpec.getSize(i2);
        Log.i("SunMoveView", "w = " + dimenInt + ", h = " + dimenInt2);
        setMeasuredDimension(dimenInt, dimenInt2);
    }

    public void readSize() {
        this.lineBaseSize = getDimen(R.dimen.sun_move_style_size);
        this.line1Long = getDimen(R.dimen.sun_move_line_1_long);
        this.line1P1x = getDimen(R.dimen.sun_move_line_1_p1x);
        this.line1P2x = getDimen(R.dimen.sun_move_line_1_p2x);
        this.line1P3x = getDimen(R.dimen.sun_move_line_1_p3x);
        this.line1P0y = getDimen(R.dimen.sun_move_line_1_p0y);
        this.line1P1y = getDimen(R.dimen.sun_move_line_1_p1y);
        this.line1P2y = getDimen(R.dimen.sun_move_line_1_p2y);
        this.line1P3y = getDimen(R.dimen.sun_move_line_1_p3y);
        this.line2Long = getDimen(R.dimen.sun_move_line_2_long);
        this.line2MarginStartX = getDimen(R.dimen.sun_move_line_2_margin_start_x);
        this.line2MarginStartY = getDimen(R.dimen.sun_move_line_2_margin_start_y);
        this.sunPadding = this.lineBaseSize * 3.0f;
        this.arrowMarginTop = getDimen(R.dimen.sun_move_icon_arrow_margin_top);
        this.sunTimeTextBaseLine = getDimen(R.dimen.sun_move_sun_time_text_base_line);
        this.sunTimeTextMarginIcon = getDimen(R.dimen.sun_move_sun_time_text_margin_icon);
        this.mPaint.setTextSize(getDimen(R.dimen.sun_move_sun_time_text_size));
        this.mIconScale = getDimen(R.dimen.sun_move_arrow_icon_width) / this.mArrowUp.getIntrinsicWidth();
        this.arrowMarginStart = getDimen(R.dimen.sun_move_icon_arrow_up_margin_start);
        this.p0xSmall = getDimen(R.dimen.sun_move_line_1_sunset_p0x);
        this.p0xLarge = getDimen(R.dimen.sun_move_line_1_sunrise_p0x);
    }

    public void setStyle(int i) {
        this.style = i;
    }

    public void setTypeface(Typeface typeface) {
        this.mPaint.setTypeface(typeface);
    }
}
