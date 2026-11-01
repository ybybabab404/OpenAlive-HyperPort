package com.miui.clock;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.miui.clock.module.BaseFontStyle;
import com.miui.clock.module.DiffusionType;
import com.miui.clock.module.FontMiSansNormal;
import com.miui.clock.utils.DeviceConfig;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"AppCompatCustomView"})
public class MiuiTextGlassView extends TextView {
    public boolean centerIsNumber;
    public boolean enableDiffusion;
    public float leftEmpty;
    public boolean leftIsNumber;
    public float leftTextMeasureWidth;
    public int leftTextWidth;
    public final Rect mBoundRect;
    public int mCenterTextBoxWidth;
    public int mCustomTextDirection;
    public DiffusionType mDiffusionType;
    public BaseFontStyle mFontStyle;
    public int mLeftTextBoxWidth;
    public int mRightTextBoxWidth;
    public int mTextBoxWidthChs;
    public int mTextBoxWidthNumber;
    public float rightEmpty;
    public boolean rightIsNumber;
    public float rightTextMeasureWidth;
    public int rightTextWidth;

    /* JADX INFO: renamed from: com.miui.clock.MiuiTextGlassView$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        public static final /* synthetic */ int[] $SwitchMap$com$miui$clock$module$DiffusionType;

        static {
            int[] iArr = new int[DiffusionType.values().length];
            $SwitchMap$com$miui$clock$module$DiffusionType = iArr;
            try {
                iArr[DiffusionType.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$clock$module$DiffusionType[DiffusionType.RIGHT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$clock$module$DiffusionType[DiffusionType.BOTH.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$miui$clock$module$DiffusionType[DiffusionType.NONE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    public MiuiTextGlassView(Context context) {
        super(context);
        this.mDiffusionType = DiffusionType.NONE;
        this.mFontStyle = new FontMiSansNormal();
        this.mBoundRect = new Rect();
        this.mCustomTextDirection = -1;
        init();
    }

    public MiuiTextGlassView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mDiffusionType = DiffusionType.NONE;
        this.mFontStyle = new FontMiSansNormal();
        this.mBoundRect = new Rect();
        this.mCustomTextDirection = -1;
        init();
    }

    public MiuiTextGlassView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mDiffusionType = DiffusionType.NONE;
        this.mFontStyle = new FontMiSansNormal();
        this.mBoundRect = new Rect();
        this.mCustomTextDirection = -1;
        init();
    }

    public final void checkDiffusionType(boolean z, boolean z2, int i) {
        if (i < 1 || !this.enableDiffusion) {
            this.mDiffusionType = DiffusionType.NONE;
            return;
        }
        if (z) {
            this.mDiffusionType = DiffusionType.BOTH;
        } else if (z2) {
            this.mDiffusionType = DiffusionType.LEFT;
        } else {
            this.mDiffusionType = DiffusionType.RIGHT;
        }
    }

    public final void drawLeftDiffusion(Canvas canvas, float f, float f2, float f3, float f4, float f5, String str, int i, Paint paint, float f6, float f7, float[] fArr) {
        canvas.save();
        float f8 = i;
        canvas.clipRect(f2, 0.0f, f, f8);
        canvas.drawText(str, (f - (fArr[4] * f6)) - f7, f5, paint);
        canvas.restore();
        canvas.save();
        canvas.clipRect(f3, 0.0f, f2, f8);
        canvas.drawText(str, (f2 - (fArr[5] * f6)) - f7, f5, paint);
        canvas.restore();
        canvas.save();
        canvas.clipRect(f4, 0.0f, f3, f8);
        canvas.drawText(str, (f3 - (f6 * fArr[6])) - f7, f5, paint);
        canvas.restore();
    }

    public final void drawRightDiffusion(Canvas canvas, float f, float f2, float f3, float f4, float f5, String str, int i, Paint paint, float f6, float f7, float[] fArr, float f8) {
        canvas.save();
        float f9 = i;
        canvas.clipRect(f, 0.0f, f2, f9);
        canvas.drawText(str, ((f + (fArr[4] * f6)) + f7) - f8, f5, paint);
        canvas.restore();
        canvas.save();
        canvas.clipRect(f3, 0.0f, f2, f9);
        canvas.drawText(str, ((f2 + (fArr[5] * f6)) + f7) - f8, f5, paint);
        canvas.restore();
        canvas.save();
        canvas.clipRect(f4, 0.0f, f3, f9);
        canvas.drawText(str, ((f3 + (f6 * fArr[6])) + f7) - f8, f5, paint);
        canvas.restore();
    }

    public final float getSlideExtraWidth(float f, int i, boolean z) {
        BaseFontStyle baseFontStyle = this.mFontStyle;
        float[] percentages = z ? baseFontStyle.getPercentages() : baseFontStyle.getCHSPercentages();
        float f2 = i;
        return (((((percentages[0] + percentages[1]) + percentages[2]) + percentages[3]) * f2) - f) - ((f2 - f) / 2.0f);
    }

    public final void init() {
        setMaxLines(1);
        updateBoxWidth();
        updateTextDirection();
    }

    public final boolean isCenter() {
        return (getGravity() & 8388615) == 1;
    }

    public final boolean isEnd() {
        return (getGravity() & 8388613) == 8388613;
    }

    public final boolean isNumber(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateTextDirection();
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        float f;
        float f2;
        char c;
        float f3;
        int i;
        float f4;
        int i2;
        float f5;
        String string = getText().toString();
        int length = string.length();
        char c2 = 0;
        boolean z = DeviceConfig.isRTL() || isEnd();
        boolean zIsCenter = isCenter();
        checkDiffusionType(zIsCenter, z, length);
        if (this.mDiffusionType == DiffusionType.NONE) {
            super.onDraw(canvas);
            return;
        }
        TextPaint paint = getPaint();
        paint.setColor(getCurrentTextColor());
        float fMeasureText = paint.measureText(string);
        int width = getWidth();
        int height = getHeight();
        if (zIsCenter) {
            f = (width - fMeasureText) / 2.0f;
        } else {
            f = z ? width - fMeasureText : 0.0f;
        }
        int baseline = getBaseline();
        DiffusionType diffusionType = this.mDiffusionType;
        DiffusionType diffusionType2 = DiffusionType.LEFT;
        if (diffusionType == diffusionType2 || diffusionType == DiffusionType.BOTH) {
            float[] percentages = this.leftIsNumber ? this.mFontStyle.getPercentages() : this.mFontStyle.getCHSPercentages();
            float f6 = this.leftTextMeasureWidth;
            int i3 = this.mLeftTextBoxWidth;
            float f7 = percentages[0];
            float f8 = percentages[1];
            float f9 = percentages[2];
            float f10 = percentages[3];
            float f11 = ((f + f6) + ((i3 - f6) / 2.0f)) - (i3 * (((f7 + f8) + f9) + f10));
            float f12 = (i3 * f10) + f11;
            float f13 = (i3 * f9) + f12;
            float f14 = (i3 * f8) + f13;
            String strSubstring = string.substring(0, 1);
            if (this.mDiffusionType == diffusionType2) {
                canvas.save();
                canvas.clipRect(f14, 0.0f, f + fMeasureText, height);
                canvas.drawText(string, f, baseline, paint);
                canvas.restore();
            }
            f2 = f;
            c = 1;
            drawLeftDiffusion(canvas, f14, f13, f12, f11, baseline, strSubstring, height, paint, this.leftTextWidth, this.leftEmpty, percentages);
            f3 = f14;
        } else {
            c = 1;
            c2 = 0;
            f3 = 0.0f;
            f2 = f;
        }
        DiffusionType diffusionType3 = this.mDiffusionType;
        DiffusionType diffusionType4 = DiffusionType.RIGHT;
        if (diffusionType3 == diffusionType4 || diffusionType3 == DiffusionType.BOTH) {
            float[] percentages2 = this.rightIsNumber ? this.mFontStyle.getPercentages() : this.mFontStyle.getCHSPercentages();
            float f15 = this.rightTextMeasureWidth;
            int i4 = this.mRightTextBoxWidth;
            float f16 = ((f2 + fMeasureText) - f15) - ((i4 - f15) / 2.0f);
            float f17 = percentages2[c2];
            float f18 = percentages2[c];
            float f19 = percentages2[2];
            float f20 = percentages2[3];
            float f21 = f16 + (i4 * (f17 + f18 + f19 + f20));
            float f22 = f21 - (i4 * f20);
            float f23 = f22 - (i4 * f19);
            float f24 = f23 - (i4 * f18);
            String strSubstring2 = string.substring(length - 1, length);
            if (this.mDiffusionType == diffusionType4) {
                canvas.save();
                canvas.clipRect(f2, 0.0f, f24, height);
                i = baseline;
                canvas.drawText(string, f2, i, paint);
                canvas.restore();
            } else {
                i = baseline;
            }
            f4 = f3;
            i2 = i;
            drawRightDiffusion(canvas, f24, f23, f22, f21, i, strSubstring2, height, paint, this.rightTextWidth, this.rightEmpty, percentages2, this.rightTextMeasureWidth);
            f5 = f24;
        } else {
            i2 = baseline;
            f4 = f3;
            f5 = 0.0f;
        }
        if (this.mDiffusionType == DiffusionType.BOTH) {
            canvas.save();
            canvas.clipRect(f4, 0.0f, f5, height);
            canvas.drawText(string, f2, i2, paint);
            canvas.restore();
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onMeasure(int i, int i2) {
        float slideExtraWidth;
        super.onMeasure(i, i2);
        String string = getText().toString();
        if (TextUtils.isEmpty(string) || this.mDiffusionType == DiffusionType.NONE) {
            return;
        }
        int measuredWidth = getMeasuredWidth();
        float f = measuredWidth;
        if (f == getPaint().measureText(string)) {
            int i3 = AnonymousClass1.$SwitchMap$com$miui$clock$module$DiffusionType[this.mDiffusionType.ordinal()];
            if (i3 == 1) {
                slideExtraWidth = getSlideExtraWidth(this.leftTextMeasureWidth, this.mLeftTextBoxWidth, this.leftIsNumber);
            } else if (i3 == 2) {
                slideExtraWidth = getSlideExtraWidth(this.rightTextMeasureWidth, this.mRightTextBoxWidth, this.rightIsNumber);
            } else if (i3 != 3) {
                slideExtraWidth = 0.0f;
            } else if (string.length() == 1) {
                float[] percentages = this.centerIsNumber ? this.mFontStyle.getPercentages() : this.mFontStyle.getCHSPercentages();
                slideExtraWidth = (this.mCenterTextBoxWidth * (((((percentages[0] + percentages[1]) + percentages[2]) + percentages[3]) * 2.0f) - 1.0f)) - f;
            } else {
                slideExtraWidth = getSlideExtraWidth(this.leftTextMeasureWidth, this.mLeftTextBoxWidth, this.leftIsNumber) + getSlideExtraWidth(this.rightTextMeasureWidth, this.mRightTextBoxWidth, this.rightIsNumber);
            }
            setMeasuredDimension(measuredWidth + ((int) (slideExtraWidth + 0.5f)), getMeasuredHeightAndState());
        }
    }

    public void setClockDarkMode(boolean z) {
        setTextColor(z ? -1 : -16777216);
    }

    public void setEnableDiffusion(boolean z) {
        updateDiffusionResource();
    }

    public MiuiTextGlassView setFontStyle(BaseFontStyle baseFontStyle) {
        this.mFontStyle = baseFontStyle;
        updateBoxWidth();
        return this;
    }

    public void setSameNumberWidth(boolean z) {
        setFontFeatureSettings(z ? "tnum" : "");
    }

    @Override // android.widget.TextView
    public void setText(CharSequence charSequence, TextView.BufferType bufferType) {
        super.setText(charSequence, bufferType);
        updateDiffusionResource();
    }

    @Override // android.widget.TextView
    public void setTextSize(int i, float f) {
        super.setTextSize(i, f);
        updateBoxWidth();
    }

    public void setTimeLayoutDirection(int i) {
        this.mCustomTextDirection = i;
        updateTextDirection();
    }

    public final void updateBoxWidth() {
        TextPaint paint = getPaint();
        this.mTextBoxWidthChs = (int) Math.ceil(paint.measureText("时"));
        this.mTextBoxWidthNumber = (int) Math.ceil(paint.measureText("0"));
    }

    public final void updateDiffusionResource() {
        boolean z;
        boolean z2;
        boolean z3;
        String string = getText().toString();
        int length = getText().length();
        checkDiffusionType(isCenter(), DeviceConfig.isRTL() || isEnd(), length);
        int i = AnonymousClass1.$SwitchMap$com$miui$clock$module$DiffusionType[this.mDiffusionType.ordinal()];
        if (i == 1) {
            z = false;
            z2 = false;
            z3 = true;
        } else if (i == 2) {
            z3 = false;
            z2 = false;
            z = true;
        } else {
            if (i != 3) {
                return;
            }
            if (length == 1) {
                z3 = false;
                z = false;
                z2 = true;
            } else {
                z2 = false;
                z3 = true;
                z = true;
            }
        }
        TextPaint paint = getPaint();
        if (z3) {
            String strSubstring = string.substring(0, 1);
            this.leftIsNumber = isNumber(strSubstring);
            paint.getTextBounds(strSubstring, 0, 1, this.mBoundRect);
            this.leftTextMeasureWidth = paint.measureText(strSubstring);
            Rect rect = this.mBoundRect;
            this.leftEmpty = rect.left;
            this.leftTextWidth = rect.width();
            this.mLeftTextBoxWidth = this.leftIsNumber ? this.mTextBoxWidthNumber : this.mTextBoxWidthChs;
        }
        if (z) {
            String strSubstring2 = string.substring(length - 1, length);
            this.rightIsNumber = isNumber(strSubstring2);
            paint.getTextBounds(strSubstring2, 0, 1, this.mBoundRect);
            float fMeasureText = paint.measureText(strSubstring2);
            this.rightTextMeasureWidth = fMeasureText;
            Rect rect2 = this.mBoundRect;
            this.rightEmpty = fMeasureText - rect2.right;
            this.rightTextWidth = rect2.width();
            this.mRightTextBoxWidth = this.rightIsNumber ? this.mTextBoxWidthNumber : this.mTextBoxWidthChs;
        }
        if (z2) {
            paint.getTextBounds(string, 0, 1, this.mBoundRect);
            this.leftEmpty = this.mBoundRect.left;
            float fMeasureText2 = paint.measureText(string);
            this.rightTextMeasureWidth = fMeasureText2;
            this.leftTextMeasureWidth = fMeasureText2;
            Rect rect3 = this.mBoundRect;
            this.rightEmpty = fMeasureText2 - rect3.right;
            int iWidth = rect3.width();
            this.rightTextWidth = iWidth;
            this.leftTextWidth = iWidth;
            boolean zIsNumber = isNumber(string);
            this.centerIsNumber = zIsNumber;
            this.mCenterTextBoxWidth = zIsNumber ? this.mTextBoxWidthNumber : this.mTextBoxWidthChs;
        }
        requestLayout();
    }

    public final void updateTextDirection() {
        int i = this.mCustomTextDirection;
        if (i > 0) {
            setTextDirection(i);
        } else {
            setTextDirection(DeviceConfig.isRTL() ? 4 : 3);
        }
    }
}
