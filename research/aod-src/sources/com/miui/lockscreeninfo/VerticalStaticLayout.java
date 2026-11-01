package com.miui.lockscreeninfo;

import android.graphics.Paint;
import android.text.TextPaint;

/* JADX INFO: loaded from: classes2.dex */
public class VerticalStaticLayout {
    public float mCJKspacingMult;
    public final Paint.FontMetricsInt mFontMetricsInt;
    public int[] mLineBreakIndex;
    public int mLineCount;
    public float[] mLineHeights;
    public float mLineMaxHeight;
    public float mLineMaxWidth;
    public float[] mLineWidths;
    public final int mMaxHeight;
    public final int mMaxWidth;
    public final TextPaint mPaint;
    public final float mSpacingAdd;
    public final float mSpacingMult;
    public final CharSequence mText;
    public float mTotalWidth;
    public int mWidth;
    public int mShowedLineCount = 0;
    public int mDescent = 0;

    public VerticalStaticLayout(CharSequence charSequence, TextPaint textPaint, int i, int i2, float f, float f2, float f3) {
        this.mText = charSequence;
        this.mPaint = textPaint;
        this.mMaxWidth = i;
        this.mMaxHeight = i2;
        this.mSpacingMult = f;
        this.mSpacingAdd = f2;
        this.mCJKspacingMult = f3;
        this.mFontMetricsInt = textPaint.getFontMetricsInt();
        measureDimentions();
    }

    public float getCharSpacingMultiplier() {
        return this.mCJKspacingMult + 1.0f;
    }

    public float[] getCharWH(CharSequence charSequence, int i) {
        float fMeasureText;
        int i2;
        int i3;
        float fMeasureText2 = 0.0f;
        float[] fArr = {0.0f, 0.0f};
        int iCodePointAt = Character.codePointAt(charSequence, i);
        int iCharCount = Character.charCount(iCodePointAt);
        boolean zIsWhitespace = Character.isWhitespace(iCodePointAt);
        if (needRotate(iCodePointAt)) {
            Paint.FontMetricsInt fontMetricsInt = this.mFontMetricsInt;
            fMeasureText = fontMetricsInt.descent - fontMetricsInt.ascent;
            fMeasureText2 = zIsWhitespace ? this.mPaint.measureText("一") / 2.0f : this.mPaint.measureText(this.mText, i, iCharCount + i);
            this.mDescent = this.mFontMetricsInt.descent;
        } else if (charSequence.charAt(i) == '\n') {
            fMeasureText = this.mPaint.measureText("一");
        } else {
            if (VerticalTextViewUtils.isEmojiCharacter(iCodePointAt)) {
                fMeasureText = this.mPaint.measureText("一");
                Paint.FontMetricsInt fontMetricsInt2 = this.mFontMetricsInt;
                i2 = fontMetricsInt2.descent;
                i3 = fontMetricsInt2.ascent;
            } else {
                fMeasureText = this.mPaint.measureText(charSequence, i, iCharCount + i);
                Paint.FontMetricsInt fontMetricsInt3 = this.mFontMetricsInt;
                i2 = fontMetricsInt3.descent;
                i3 = fontMetricsInt3.ascent;
            }
            fMeasureText2 = i2 - i3;
        }
        fArr[0] = fMeasureText;
        fArr[1] = fMeasureText2;
        return fArr;
    }

    public float getHeight() {
        return this.mLineMaxHeight;
    }

    public float getHeight(int i) {
        float[] fArr = this.mLineHeights;
        if (i >= fArr.length || i < 0) {
            return 0.0f;
        }
        return fArr[i];
    }

    public int getLineBreak(int i) {
        int[] iArr = this.mLineBreakIndex;
        if (i >= iArr.length || i < 0) {
            return 0;
        }
        return iArr[i];
    }

    public int getLineCount() {
        return this.mLineCount;
    }

    public float getLineMaxWidth() {
        return this.mLineMaxWidth;
    }

    public int getShowedLineCount() {
        return this.mShowedLineCount;
    }

    public int getShowedLineN() {
        int i = this.mLineCount;
        while (this.mTotalWidth > this.mMaxWidth && i > 0) {
            i--;
            this.mTotalWidth = getWidth(i);
        }
        return i;
    }

    public float getWidth() {
        return this.mTotalWidth;
    }

    public float getWidth(int i) {
        return getWidth(i, this.mLineMaxWidth);
    }

    public final float getWidth(int i, float f) {
        if (i < 1) {
            return 0.0f;
        }
        float f2 = this.mLineWidths[0];
        for (int i2 = 1; i2 < i; i2++) {
            f2 = f2 + ((this.mSpacingMult - 1.0f) * this.mLineWidths[i2]) + this.mSpacingAdd + f;
        }
        return f2;
    }

    public final void measureDimentions() {
        float fMeasureText;
        int i;
        int i2;
        float fMeasureText2;
        this.mLineMaxHeight = 0.0f;
        this.mLineCount = 0;
        this.mLineWidths = new float[this.mText.length() + 1];
        this.mLineBreakIndex = new int[this.mText.length() + 1];
        this.mLineHeights = new float[this.mText.length() + 1];
        this.mLineMaxWidth = 0.0f;
        float f = 0.0f;
        float f2 = 0.0f;
        int i3 = 0;
        int i4 = 0;
        int i5 = 1;
        boolean z = true;
        while (i3 < this.mText.length()) {
            int iCodePointAt = Character.codePointAt(this.mText, i3);
            int iCharCount = Character.charCount(iCodePointAt);
            boolean zIsWhitespace = Character.isWhitespace(iCodePointAt);
            if (needRotate(iCodePointAt)) {
                Paint.FontMetricsInt fontMetricsInt = this.mFontMetricsInt;
                fMeasureText = fontMetricsInt.descent - fontMetricsInt.ascent;
                fMeasureText2 = zIsWhitespace ? this.mPaint.measureText("一") / 2.0f : this.mPaint.measureText(this.mText, i3, i3 + iCharCount);
                this.mDescent = this.mFontMetricsInt.descent;
            } else if (this.mText.charAt(i3) == '\n') {
                fMeasureText = this.mPaint.measureText("一");
                fMeasureText2 = 0.0f;
            } else {
                if (VerticalTextViewUtils.isEmojiCharacter(iCodePointAt)) {
                    fMeasureText = this.mPaint.measureText("一");
                    Paint.FontMetricsInt fontMetricsInt2 = this.mFontMetricsInt;
                    i = fontMetricsInt2.descent;
                    i2 = fontMetricsInt2.ascent;
                } else {
                    fMeasureText = this.mPaint.measureText(this.mText, i3, i3 + iCharCount);
                    Paint.FontMetricsInt fontMetricsInt3 = this.mFontMetricsInt;
                    i = fontMetricsInt3.descent;
                    i2 = fontMetricsInt3.ascent;
                }
                fMeasureText2 = i - i2;
            }
            float charSpacingMultiplier = (!z ? (getCharSpacingMultiplier() - 1.0f) * f : 0.0f) + fMeasureText2 + f2;
            if (charSpacingMultiplier > this.mMaxHeight || (this.mText.charAt(i3) == '\n' && i3 != 0)) {
                if (this.mLineMaxHeight < f2) {
                    this.mLineMaxHeight = f2;
                }
                this.mLineHeights[i4] = Math.min(f2, this.mMaxHeight);
                if (this.mText.charAt(i3) == '\n') {
                    this.mLineBreakIndex[i4] = i3;
                } else {
                    this.mLineBreakIndex[i4] = i3 - i5;
                }
                this.mWidth = (int) (this.mWidth + this.mLineWidths[i4]);
                i4++;
                f2 = fMeasureText2;
            } else {
                if (this.mLineMaxHeight < charSpacingMultiplier) {
                    this.mLineMaxHeight = charSpacingMultiplier;
                }
                z = false;
                f2 = charSpacingMultiplier;
            }
            float[] fArr = this.mLineWidths;
            if (fArr[i4] < fMeasureText) {
                fArr[i4] = fMeasureText;
                this.mLineMaxWidth = Math.max(fMeasureText, this.mLineMaxWidth);
            }
            i3 += iCharCount;
            if (i3 >= this.mText.length()) {
                this.mWidth = (int) (this.mWidth + this.mLineWidths[i4]);
                this.mLineHeights[i4] = Math.min(f2, this.mMaxHeight);
            }
            i5 = iCharCount;
            f = fMeasureText2;
        }
        if (this.mText.length() > 0) {
            this.mLineCount = i4 + 1;
            this.mLineBreakIndex[i4] = this.mText.length() - i5;
        }
        int i6 = this.mLineCount;
        if (i6 > 1) {
            this.mWidth = (int) getWidth(i6);
        }
        this.mTotalWidth = this.mWidth;
        this.mShowedLineCount = getShowedLineN();
    }

    public final boolean needRotate(int i) {
        return (VerticalTextViewUtils.isCJKCharacter(i) || VerticalTextViewUtils.isEmojiCharacter(i) || VerticalTextViewUtils.isControlCharactor(i)) ? false : true;
    }
}
