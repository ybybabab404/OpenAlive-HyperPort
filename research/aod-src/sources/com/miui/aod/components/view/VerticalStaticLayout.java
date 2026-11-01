package com.miui.aod.components.view;

import android.graphics.Paint;
import android.icu.text.BreakIterator;
import android.text.TextPaint;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import com.miui.aod.Utils;

/* JADX INFO: loaded from: classes.dex */
public class VerticalStaticLayout {
    private float mCJKspacingAdd;
    private float mCJKspacingMult;
    private int mEllipseCount;
    private int mEnd;
    private final Paint.FontMetricsInt mFontMetricsInt;
    private int[] mLineBreakIndex;
    private int mLineCount;
    private float[] mLineHeights;
    private float mLineMaxHeight;
    private float mLineMaxWidth;
    private float[] mLineWidths;
    private final int mMaxHeight;
    private final int mMaxWidth;
    private final TextPaint mPaint;
    private final float mSpacingAdd;
    private final float mSpacingMult;
    private int mStart;
    private final CharSequence mText;
    private float mTotalWidth;
    private int mWidth;
    private int mShowedLineCount = 0;
    private int mEllipsisHint = 65049;

    public VerticalStaticLayout(@NonNull CharSequence charSequence, @NonNull TextPaint textPaint, @IntRange int i, @IntRange int i2, float f, float f2, float f3, float f4) {
        this.mEllipseCount = -1;
        this.mText = charSequence;
        this.mPaint = textPaint;
        this.mMaxHeight = i2;
        this.mMaxWidth = i;
        this.mSpacingMult = f;
        this.mSpacingAdd = f2;
        this.mCJKspacingAdd = f4;
        this.mCJKspacingMult = f3;
        this.mFontMetricsInt = textPaint.getFontMetricsInt();
        this.mEllipseCount = -1;
        measureDimentions();
    }

    private float getWidth(int i, float f) {
        float f2 = ((this.mSpacingMult - 1.0f) * f) + this.mSpacingAdd;
        if (i < 1) {
            return 0.0f;
        }
        float f3 = f;
        for (int i2 = 1; i2 < i; i2++) {
            f3 = f3 + f2 + f;
        }
        return f3;
    }

    private void measureDimentions() {
        float fMeasureText;
        float fMeasureText2;
        this.mLineMaxHeight = 0.0f;
        this.mLineCount = 0;
        int i = 1;
        this.mLineWidths = new float[this.mText.length() + 1];
        this.mLineBreakIndex = new int[this.mText.length() + 1];
        this.mLineHeights = new float[this.mText.length() + 1];
        this.mLineMaxWidth = 0.0f;
        BreakIterator characterInstance = BreakIterator.getCharacterInstance();
        characterInstance.setText(this.mText.toString());
        float f = 0.0f;
        float f2 = 0.0f;
        int i2 = 0;
        int i3 = 0;
        boolean z = false;
        int i4 = 1;
        boolean z2 = true;
        while (i2 < this.mText.length()) {
            int iFollowing = characterInstance.following(i2);
            if (iFollowing == -1 || iFollowing <= i2) {
                iFollowing = Math.min(i2 + 1, this.mText.length());
            }
            int iMax = Math.max(i, iFollowing - i2);
            boolean zNeedRotate = needRotate(this.mText, i2, iMax);
            if (zNeedRotate) {
                Paint.FontMetricsInt fontMetricsInt = this.mFontMetricsInt;
                fMeasureText = fontMetricsInt.descent - fontMetricsInt.ascent;
                fMeasureText2 = this.mPaint.measureText(this.mText, i2, i2 + iMax);
            } else if (this.mText.charAt(i2) == '\n') {
                fMeasureText = this.mPaint.measureText("米");
                fMeasureText2 = 0.0f;
            } else {
                fMeasureText = this.mPaint.measureText(this.mText, i2, i2 + iMax);
                Paint.FontMetricsInt fontMetricsInt2 = this.mFontMetricsInt;
                fMeasureText2 = fontMetricsInt2.descent - fontMetricsInt2.ascent;
            }
            float charSpacingMultiplier = ((z2 || z != zNeedRotate) ? fMeasureText2 : (!z2 ? ((getCharSpacingMultiplier() - 1.0f) * f) + getCharSpacingExtra(zNeedRotate) : 0.0f) + fMeasureText2) + f2;
            if (charSpacingMultiplier > this.mMaxHeight || this.mText.charAt(i2) == '\n') {
                if (this.mLineMaxHeight < f2) {
                    this.mLineMaxHeight = f2;
                }
                this.mLineHeights[i3] = Math.min(f2, this.mMaxHeight);
                if (this.mText.charAt(i2) == '\n') {
                    this.mLineBreakIndex[i3] = i2;
                } else {
                    this.mLineBreakIndex[i3] = i2 - i4;
                }
                this.mWidth = (int) (this.mWidth + this.mLineWidths[i3]);
                i3++;
                f2 = fMeasureText2;
            } else {
                if (this.mLineMaxHeight < charSpacingMultiplier) {
                    this.mLineMaxHeight = charSpacingMultiplier;
                }
                f2 = charSpacingMultiplier;
                z2 = false;
            }
            float[] fArr = this.mLineWidths;
            if (fArr[i3] < fMeasureText) {
                fArr[i3] = fMeasureText;
                this.mLineMaxWidth = Math.max(fMeasureText, this.mLineMaxWidth);
            }
            i2 += iMax;
            if (i2 >= this.mText.length()) {
                this.mWidth = (int) (this.mWidth + this.mLineWidths[i3]);
                this.mLineHeights[i3] = Math.min(f2, this.mMaxHeight);
            }
            f = fMeasureText2;
            i4 = iMax;
            z = zNeedRotate;
            i = 1;
        }
        if (this.mText.length() > 0) {
            this.mLineCount = i3 + 1;
            this.mLineBreakIndex[i3] = this.mText.length() - i4;
        }
        int i5 = this.mLineCount;
        if (i5 > 1) {
            this.mWidth = (int) getWidth(i5);
        }
        this.mTotalWidth = this.mWidth;
        this.mShowedLineCount = getShowedLineN();
    }

    private int reverseSearchCharStartIndex(int i) {
        if (i > 0 && Character.isLowSurrogate(this.mText.charAt(i))) {
            return i - 1;
        }
        while (i >= 0 && Utils.isControlCharactorNoLf(this.mText.charAt(i))) {
            i--;
        }
        return i;
    }

    public float getCharSpacingExtra(boolean z) {
        return this.mCJKspacingAdd;
    }

    public float getCharSpacingMultiplier() {
        return this.mCJKspacingMult;
    }

    public float[] getCharWH(CharSequence charSequence, int i) {
        float fMeasureText;
        float fMeasureText2;
        float[] fArr = {0.0f, 0.0f};
        BreakIterator characterInstance = BreakIterator.getCharacterInstance();
        characterInstance.setText(charSequence);
        int iFollowing = characterInstance.following(i);
        if (iFollowing == -1 || iFollowing <= i) {
            iFollowing = Math.min(i + 1, charSequence.length());
        }
        int iMax = Math.max(1, iFollowing - i);
        if (needRotate(charSequence, i, iMax)) {
            Paint.FontMetricsInt fontMetricsInt = this.mFontMetricsInt;
            fMeasureText = fontMetricsInt.descent - fontMetricsInt.ascent;
            fMeasureText2 = this.mPaint.measureText(charSequence, i, iMax + i);
        } else if (charSequence.charAt(i) == '\n') {
            fMeasureText = this.mPaint.measureText("米");
            fMeasureText2 = 0.0f;
        } else {
            fMeasureText = this.mPaint.measureText(charSequence, i, iMax + i);
            Paint.FontMetricsInt fontMetricsInt2 = this.mFontMetricsInt;
            fMeasureText2 = fontMetricsInt2.descent - fontMetricsInt2.ascent;
        }
        fArr[0] = fMeasureText;
        fArr[1] = fMeasureText2;
        return fArr;
    }

    public int getEllipsisCount() {
        int i = this.mEllipseCount;
        if (i != -1) {
            return i;
        }
        if (getShowedLineCount() >= getLineCount()) {
            this.mEllipseCount = 0;
            return 0;
        }
        Paint.FontMetricsInt fontMetricsInt = this.mPaint.getFontMetricsInt();
        int charSpacingMultiplier = (int) (((fontMetricsInt.descent - fontMetricsInt.ascent) * getCharSpacingMultiplier()) + getCharSpacingExtra(false));
        int lineBreak = getLineBreak(getShowedLineCount() - 1);
        float height = getHeight(getShowedLineCount() - 1);
        float f = charSpacingMultiplier;
        if (height + f > this.mMaxHeight) {
            int iMax = Math.max(0, getLineBreak(getShowedLineCount() + (-2)) > 0 ? getLineBreak(getShowedLineCount() - 2) + 1 : 0);
            while (lineBreak > iMax) {
                lineBreak = reverseSearchCharStartIndex(lineBreak);
                if (lineBreak < 0) {
                    lineBreak = 0;
                    break;
                }
                height -= getCharWH(this.mText, lineBreak)[1];
                if (height + f <= this.mMaxHeight) {
                    break;
                }
                lineBreak--;
            }
        }
        int iMax2 = Math.max(this.mText.length() - lineBreak, 0);
        this.mEllipseCount = iMax2;
        return iMax2;
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

    public int[] getLineBreaks() {
        return this.mLineBreakIndex;
    }

    public int getLineCount() {
        return this.mLineCount;
    }

    public int getLineLeft(int i) {
        return 0;
    }

    public float getLineMaxWidth() {
        return this.mLineMaxWidth;
    }

    public int getLineRight(int i) {
        return 0;
    }

    public float getLineWidth(int i) {
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

    public boolean needRotate(CharSequence charSequence, int i, int i2) {
        int iCodePointAt = Character.codePointAt(charSequence, i);
        int iCharCount = i;
        while (iCharCount < i + i2 && iCharCount < charSequence.length()) {
            int iCodePointAt2 = Character.codePointAt(charSequence, iCharCount);
            if (Utils.isEmojiCharacter(iCodePointAt2)) {
                return false;
            }
            iCharCount += Character.charCount(iCodePointAt2);
        }
        return (Utils.isCJKCharacter(iCodePointAt) || Utils.isControlCharactor(iCodePointAt) || iCodePointAt == this.mEllipsisHint) ? false : true;
    }
}
