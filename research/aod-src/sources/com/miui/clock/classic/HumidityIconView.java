package com.miui.clock.classic;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import com.miui.clock.R;
import com.miui.clock.utils.DeviceConfig;
import java.text.NumberFormat;

/* JADX INFO: loaded from: classes.dex */
public class HumidityIconView extends View {
    public float hSpacing;
    public float humidity;
    public String humidityString;
    public boolean isRtl;
    public Context mContext;
    public Paint mPaint;
    public Resources mResources;
    public int mSmallestScreenWidth;
    public int mainColor;
    public float marginStart;
    public float marginTop;
    public int minorColor;
    public Path path;
    public float radius;
    public float textBaseLine;
    public float textMarginStart;
    public float textWidth;
    public float vSpacing;

    public HumidityIconView(Context context) {
        super(context);
        this.humidity = -1.0f;
        this.path = new Path();
        init(context);
    }

    public HumidityIconView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.humidity = -1.0f;
        this.path = new Path();
        init(context);
    }

    public HumidityIconView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.humidity = -1.0f;
        this.path = new Path();
        init(context);
    }

    public final int getDimen(int i) {
        return (int) (this.mResources.getDimensionPixelSize(i) * DeviceConfig.calculateScale(getContext()));
    }

    public final float getDimenF(int i) {
        return this.mResources.getDimension(i) * DeviceConfig.calculateScale(getContext());
    }

    public float getHumidity() {
        return this.humidity;
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
        invalidate();
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        boolean z;
        super.onConfigurationChanged(configuration);
        boolean z2 = true;
        if (this.isRtl != DeviceConfig.isRTL()) {
            this.isRtl = !this.isRtl;
            z = true;
        } else {
            z = false;
        }
        int i = configuration.smallestScreenWidthDp;
        if (!DeviceConfig.isFlip() || i == this.mSmallestScreenWidth) {
            z2 = z;
        } else {
            this.mSmallestScreenWidth = i;
            Paint paint = this.mPaint;
            if (paint != null) {
                this.textWidth = paint.measureText(this.humidityString);
            }
        }
        if (z2) {
            requestLayout();
            invalidate();
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        float f;
        float f2;
        boolean z;
        float f3;
        float f4;
        float f5;
        float f6;
        int i;
        super.onDraw(canvas);
        if (this.mResources == null || getVisibility() == 8) {
            return;
        }
        boolean zIsRTL = DeviceConfig.isRTL();
        if (zIsRTL) {
            float measuredWidth = getMeasuredWidth();
            f2 = ((measuredWidth - this.marginStart) - (this.radius * 12.0f)) - (this.hSpacing * 5.0f);
            f = (measuredWidth - this.textWidth) - this.textMarginStart;
        } else {
            f = this.textMarginStart;
            f2 = this.marginStart;
        }
        float f7 = f2;
        this.mPaint.setColor(this.minorColor);
        canvas.drawText(this.humidityString, f, this.textBaseLine, this.mPaint);
        float f8 = this.humidity;
        int i2 = (int) (f8 / 8.333f);
        boolean z2 = f8 - (((float) i2) * 8.333f) > 0.1f;
        int i3 = 0;
        int i4 = 0;
        while (i3 < 2) {
            float f9 = this.radius;
            float f10 = f9 + (i3 * ((f9 * 2.0f) + this.vSpacing)) + this.marginTop;
            int i5 = 0;
            while (i5 < 6) {
                int i6 = i4 + 1;
                if (i6 <= i2) {
                    this.mPaint.setColor(this.mainColor);
                    z = false;
                } else {
                    z = z2 && i6 - i2 == 1;
                    this.mPaint.setColor(this.minorColor);
                }
                if (zIsRTL) {
                    float f11 = this.radius;
                    f3 = f7 + f11;
                    f4 = 5 - i5;
                    f5 = f11 * 2.0f;
                    f6 = this.hSpacing;
                } else {
                    float f12 = this.radius;
                    f3 = f7 + f12;
                    f4 = i5;
                    f5 = f12 * 2.0f;
                    f6 = this.hSpacing;
                }
                float f13 = f3 + (f4 * (f5 + f6));
                float f14 = this.radius;
                float f15 = f13 - f14;
                float f16 = f10 - f14;
                float f17 = f13 + f14;
                float f18 = f10 + f14;
                this.path.reset();
                if (z) {
                    this.path.moveTo(f17, f16);
                    this.path.lineTo(f13, f16);
                    this.path.addArc(f15, f16, f17, f18, 270.0f, -86.83f);
                    this.path.lineTo(f17, f10 - (this.radius / 18.0f));
                    this.path.lineTo(f17, f16);
                    this.path.close();
                    canvas.drawPath(this.path, this.mPaint);
                    this.mPaint.setColor(this.mainColor);
                    i = i6;
                    canvas.drawArc(f15, f16, f17, f18, 3.17f, 173.66f, false, this.mPaint);
                } else {
                    i = i6;
                    this.path.moveTo(f17, f16);
                    this.path.lineTo(f17, f18);
                    this.path.addArc(f15, f16, f17, f18, 0.0f, 270.0f);
                    this.path.lineTo(f17, f16);
                    this.path.close();
                    canvas.drawPath(this.path, this.mPaint);
                }
                i5++;
                i3 = i3;
                i4 = i;
            }
            i3++;
        }
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        int size = View.MeasureSpec.getMode(i) != 1073741824 ? (int) (this.marginStart + this.textMarginStart + this.textWidth) : View.MeasureSpec.getSize(i);
        int dimen = View.MeasureSpec.getMode(i2) != 1073741824 ? getDimen(R.dimen.humidity_view_height) : View.MeasureSpec.getSize(i2);
        Log.i("HumidityIconView", "w = " + size + ", h = " + dimen);
        setMeasuredDimension(size, dimen);
    }

    public void readSize() {
        this.marginStart = getDimenF(R.dimen.classic_content_area_humidity_icon_margin_start_end);
        this.marginTop = getDimenF(R.dimen.classic_content_area_humidity_icon_margin_top_bottom);
        this.radius = getDimenF(R.dimen.classic_content_area_humidity_icon_radius);
        this.vSpacing = getDimenF(R.dimen.classic_content_area_humidity_icon_v_spacing);
        this.hSpacing = getDimenF(R.dimen.classic_content_area_humidity_icon_h_spacing);
        this.textMarginStart = getDimenF(R.dimen.classic_content_area_humidity_text_margin_start);
        this.textBaseLine = getDimenF(R.dimen.classic_content_area_humidity_text_base_line);
        this.mPaint.setTextSize(getDimenF(R.dimen.classic_content_area_humidity_text_size));
    }

    public void setHumidity(float f) {
        this.humidity = f;
        if (this.mResources == null) {
            this.mResources = this.mContext.getResources();
            readSize();
            this.mPaint.setStyle(Paint.Style.FILL);
            if (this.mainColor == 0) {
                this.mainColor = this.mResources.getColor(R.color.classic_color_white);
            }
            if (this.minorColor == 0) {
                this.minorColor = this.mResources.getColor(R.color.classic_color_white_60);
            }
        }
        if (f < 0.0f) {
            this.humidityString = this.mResources.getString(R.string.no_data_placeholder_string_with_percent_sign);
        } else {
            this.humidityString = this.mResources.getString(R.string.humidity_format_with_unit, NumberFormat.getPercentInstance().format(this.humidity / 100.0f));
        }
        this.textWidth = this.mPaint.measureText(this.humidityString);
        requestLayout();
        invalidate();
    }

    public void setTypeface(Typeface typeface) {
        boolean z = typeface == null || !typeface.equals(this.mPaint.getTypeface());
        this.mPaint.setTypeface(typeface);
        if (z) {
            setHumidity(this.humidity);
        }
    }
}
