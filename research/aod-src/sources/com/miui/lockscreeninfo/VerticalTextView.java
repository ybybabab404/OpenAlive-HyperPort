package com.miui.lockscreeninfo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/* JADX INFO: loaded from: classes2.dex */
public class VerticalTextView extends BaseTextView {
    public static final String TAG = "com.miui.lockscreeninfo.VerticalTextView";
    public float mCJKspacingMult;
    public int mGravity;
    public boolean mIsQinghe;
    public boolean mIsVerticalMode;
    public float mTMeasuredHeight;
    public float mTMeasuredWidth;
    public VerticalStaticLayout mVerticalStaticLayout;

    public VerticalTextView(Context context) {
        this(context, null);
    }

    public VerticalTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, android.R.attr.textViewStyle);
    }

    public VerticalTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mIsVerticalMode = true;
        this.mIsQinghe = false;
        this.mGravity = 0;
    }

    public final void drawJustifyTextForLine(Canvas canvas, String str, float f, int i) {
        Canvas canvas2;
        float paddingLeft = getPaddingLeft();
        char[] charArray = str.toCharArray();
        float measuredWidth = (((getMeasuredWidth() - f) - getPaddingLeft()) - getPaddingRight()) / (charArray.length - 1);
        int i2 = 0;
        float f2 = paddingLeft;
        while (i2 < charArray.length) {
            int iCharCount = Character.charCount(Character.codePointAt(charArray, i2));
            float desiredWidth = StaticLayout.getDesiredWidth(String.valueOf(charArray[i2]), getPaint());
            if (shouldDrawCharacter(charArray[i2])) {
                canvas2 = canvas;
                canvas2.drawText(charArray, i2, iCharCount, f2, i, getPaint());
            } else {
                canvas2 = canvas;
            }
            f2 += desiredWidth + measuredWidth;
            i2 += iCharCount;
            canvas = canvas2;
        }
    }

    public float getCJKVerticalSpacingMultiplier() {
        return this.mCJKspacingMult;
    }

    public final float getCurLineBeginY(int i) {
        float paddingTop = getPaddingTop();
        if ((getGravity() & com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle) == 48) {
            return getPaddingTop();
        }
        if ((getGravity() & com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle) == 80) {
            return (getHeight() - getPaddingBottom()) - this.mVerticalStaticLayout.getHeight(i);
        }
        return (getGravity() & com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle) == 16 ? (((getHeight() - getPaddingBottom()) + getPaddingTop()) - this.mVerticalStaticLayout.getHeight(i)) / 2.0f : paddingTop;
    }

    @Override // android.widget.TextView
    public int getGravity() {
        return this.mGravity;
    }

    public final int getHorizontalLineSpace(int i) {
        if (i > 0) {
            return ((int) getLineSpacingMultiplier()) * i;
        }
        return 0;
    }

    public String getShowText() {
        if (isVerticalMode()) {
            VerticalStaticLayout verticalStaticLayout = this.mVerticalStaticLayout;
            if (verticalStaticLayout != null && verticalStaticLayout.getShowedLineCount() > 0) {
                return getText().toString();
            }
        } else if (getLayout() != null) {
            return getText().toString();
        }
        return "";
    }

    public final void initDimensionsOnMeasure(int i, int i2) {
        float fMin;
        int mode = View.MeasureSpec.getMode(i);
        int mode2 = View.MeasureSpec.getMode(i2);
        int size = View.MeasureSpec.getSize(i);
        int size2 = View.MeasureSpec.getSize(i2);
        VerticalStaticLayout verticalStaticLayout = new VerticalStaticLayout(getText(), getPaint(), (size - getPaddingLeft()) - getPaddingRight(), (size2 - getPaddingTop()) - getPaddingBottom(), getLineSpacingMultiplier(), getLineSpacingExtra(), getCJKVerticalSpacingMultiplier());
        this.mVerticalStaticLayout = verticalStaticLayout;
        float width = verticalStaticLayout.getWidth();
        float height = this.mVerticalStaticLayout.getHeight();
        float paddingLeft = width + getPaddingLeft() + getPaddingRight();
        float fMin2 = 2.1474836E9f;
        if (mode2 == 1073741824) {
            fMin = size2;
        } else {
            fMin = mode2 == Integer.MIN_VALUE ? Math.min(height, size2) : 2.1474836E9f;
        }
        if (mode == 1073741824) {
            fMin2 = size;
        } else if (mode == Integer.MIN_VALUE) {
            fMin2 = Math.min(paddingLeft, size);
        }
        this.mTMeasuredHeight = fMin;
        this.mTMeasuredWidth = fMin2;
    }

    public boolean isVerticalMode() {
        return this.mIsVerticalMode;
    }

    public final boolean needRotate(int i) {
        return (VerticalTextViewUtils.isCJKCharacter(i) || VerticalTextViewUtils.isEmojiCharacter(i) || VerticalTextViewUtils.isControlCharactor(i)) ? false : true;
    }

    /* JADX WARN: Code duplicated, block: B:17:0x009b  */
    /* JADX WARN: Code duplicated, block: B:19:0x00b1  */
    /* JADX WARN: Code duplicated, block: B:22:0x00c0  */
    /* JADX WARN: Code duplicated, block: B:24:0x00c6  */
    /* JADX WARN: Code duplicated, block: B:27:0x00d1  */
    /* JADX WARN: Code duplicated, block: B:28:0x00df  */
    /* JADX WARN: Code duplicated, block: B:31:0x00ef  */
    /* JADX WARN: Code duplicated, block: B:38:0x010c  */
    /* JADX WARN: Code duplicated, block: B:39:0x011b  */
    @Override // com.miui.lockscreeninfo.BaseTextView, android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        float paddingLeft;
        float curLineBeginY;
        int i;
        float f;
        int i2;
        int iCharCount;
        boolean zNeedRotate;
        char[] cArr;
        float f2;
        int i3;
        float f3;
        int i4;
        int i5;
        int iSaveLayer = canvas.saveLayer(0.0f, 0.0f, getWidth(), getHeight(), null);
        TextPaint paint = getPaint();
        paint.setColor(getCurrentTextColor());
        paint.drawableState = getDrawableState();
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        String showText = getShowText();
        char[] charArray = showText.toCharArray();
        float f4 = 0.0f;
        if (this.mIsVerticalMode) {
            int showedLineCount = this.mVerticalStaticLayout.getShowedLineCount();
            int lineCount = this.mVerticalStaticLayout.getLineCount();
            if (lineCount == 0) {
                return;
            }
            canvas.save();
            VerticalStaticLayout verticalStaticLayout = this.mVerticalStaticLayout;
            float width = verticalStaticLayout.getWidth(verticalStaticLayout.getShowedLineCount());
            float lineMaxWidth = this.mVerticalStaticLayout.getLineMaxWidth();
            if (getGravity() == 102) {
                paddingLeft = getWidth() - getPaddingRight();
            } else if (getGravity() == 101) {
                paddingLeft = width + getPaddingLeft();
            } else {
                curLineBeginY = getCurLineBeginY(0);
                float lineSpacingMultiplier = ((getLineSpacingMultiplier() - 1.0f) * lineMaxWidth) + getLineSpacingExtra();
                float f5 = f4;
                i = 0;
                f = f5;
                i2 = 0;
                while (i2 < charArray.length) {
                    int iCodePointAt = Character.codePointAt(charArray, i2);
                    iCharCount = Character.charCount(iCodePointAt);
                    zNeedRotate = needRotate(iCodePointAt);
                    int iSave = canvas.save();
                    cArr = charArray;
                    if (zNeedRotate) {
                        canvas.rotate(90.0f, f, curLineBeginY);
                    }
                    f2 = this.mVerticalStaticLayout.getCharWH(showText, i2)[1];
                    if (zNeedRotate) {
                        i3 = fontMetricsInt.descent;
                    } else {
                        i3 = fontMetricsInt.ascent;
                    }
                    f3 = curLineBeginY - i3;
                    if (shouldDrawCharacter(cArr[i2])) {
                        i4 = i2;
                        i5 = iCharCount;
                        charArray = cArr;
                        canvas.drawText(charArray, i4, i5, f, f3, paint);
                    } else {
                        i4 = i2;
                        i5 = iCharCount;
                        charArray = cArr;
                    }
                    canvas.restoreToCount(iSave);
                    i2 = i5 + i4;
                    if (i2 < charArray.length) {
                        i = i;
                        curLineBeginY = curLineBeginY;
                    } else if (i4 + 1 > this.mVerticalStaticLayout.getLineBreak(i) || (i = i + 1) >= lineCount) {
                        curLineBeginY = ((this.mVerticalStaticLayout.getCharSpacingMultiplier() - 1.0f) * f2) + f2 + curLineBeginY;
                        i = i;
                    } else {
                        if (i >= showedLineCount) {
                            break;
                        }
                        f5 -= lineSpacingMultiplier + lineMaxWidth;
                        curLineBeginY = getCurLineBeginY(i);
                        f = f5;
                    }
                    fontMetricsInt = fontMetricsInt;
                }
            }
            f4 = paddingLeft - lineMaxWidth;
            curLineBeginY = getCurLineBeginY(0);
            float lineSpacingMultiplier2 = ((getLineSpacingMultiplier() - 1.0f) * lineMaxWidth) + getLineSpacingExtra();
            float f6 = f4;
            i = 0;
            f = f6;
            i2 = 0;
            while (i2 < charArray.length) {
                int iCodePointAt2 = Character.codePointAt(charArray, i2);
                iCharCount = Character.charCount(iCodePointAt2);
                zNeedRotate = needRotate(iCodePointAt2);
                int iSave2 = canvas.save();
                cArr = charArray;
                if (zNeedRotate) {
                    canvas.rotate(90.0f, f, curLineBeginY);
                }
                f2 = this.mVerticalStaticLayout.getCharWH(showText, i2)[1];
                if (zNeedRotate) {
                    i3 = fontMetricsInt.descent;
                } else {
                    i3 = fontMetricsInt.ascent;
                }
                f3 = curLineBeginY - i3;
                if (shouldDrawCharacter(cArr[i2])) {
                    i4 = i2;
                    i5 = iCharCount;
                    charArray = cArr;
                    canvas.drawText(charArray, i4, i5, f, f3, paint);
                } else {
                    i4 = i2;
                    i5 = iCharCount;
                    charArray = cArr;
                }
                canvas.restoreToCount(iSave2);
                i2 = i5 + i4;
                if (i2 < charArray.length) {
                    i = i;
                    curLineBeginY = curLineBeginY;
                } else if (i4 + 1 > this.mVerticalStaticLayout.getLineBreak(i)) {
                    curLineBeginY = ((this.mVerticalStaticLayout.getCharSpacingMultiplier() - 1.0f) * f2) + f2 + curLineBeginY;
                    i = i;
                } else {
                    curLineBeginY = ((this.mVerticalStaticLayout.getCharSpacingMultiplier() - 1.0f) * f2) + f2 + curLineBeginY;
                    i = i;
                }
                fontMetricsInt = fontMetricsInt;
            }
        } else {
            String string = getText().toString();
            TextPaint textPaint = new TextPaint(getPaint());
            String strReplaceAll = string.replaceAll(" ", "i");
            getMaxLines();
            int width2 = (getWidth() - getPaddingRight()) - getPaddingLeft();
            StaticLayout.Builder maxLines = StaticLayout.Builder.obtain(strReplaceAll, 0, strReplaceAll.length(), textPaint, width2).setLineSpacing(0.0f, getLineSpacingMultiplier()).setAlignment(Layout.Alignment.ALIGN_OPPOSITE).setIncludePad(true).setMaxLines(getMaxLines());
            if (getEllipsize() != null) {
                maxLines.setEllipsizedWidth(width2);
                maxLines.setEllipsize(getEllipsize());
            }
            StaticLayout staticLayoutBuild = maxLines.build();
            int lineCount2 = staticLayoutBuild.getLineCount();
            for (int i6 = 0; i6 < lineCount2; i6++) {
                int lineBaseline = staticLayoutBuild.getLineBaseline(i6) + getPaddingTop() + getHorizontalLineSpace(i6);
                int lineStart = staticLayoutBuild.getLineStart(i6);
                int lineVisibleEnd = staticLayoutBuild.getLineVisibleEnd(i6);
                int ellipsisCount = staticLayoutBuild.getEllipsisCount(i6);
                String strReplaceAll2 = string.substring(lineStart, lineVisibleEnd).replaceAll("\n", "");
                if (i6 == lineCount2 - 1 && ellipsisCount > 0) {
                    strReplaceAll2 = strReplaceAll2.substring(0, staticLayoutBuild.getEllipsisStart(i6)) + "…";
                }
                float fMeasureText = getPaint().measureText(strReplaceAll2);
                if (getGravity() == 103) {
                    canvas.drawText(strReplaceAll2, getPaddingLeft(), lineBaseline + (((int) getLineSpacingExtra()) * i6), getPaint());
                } else if (getGravity() == 105) {
                    canvas.drawText(strReplaceAll2, (getMeasuredWidth() - getPaddingRight()) - fMeasureText, lineBaseline + (((int) getLineSpacingExtra()) * i6), getPaint());
                } else if (getGravity() == 104) {
                    canvas.drawText(strReplaceAll2, (getMeasuredWidth() - fMeasureText) / 2.0f, lineBaseline + (((int) getLineSpacingExtra()) * i6), getPaint());
                } else if (getGravity() == 106) {
                    drawJustifyTextForLine(canvas, strReplaceAll2, fMeasureText, lineBaseline + (((int) getLineSpacingExtra()) * i6));
                }
            }
        }
        canvas.restore();
        canvas.restoreToCount(iSaveLayer);
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (!this.mIsVerticalMode) {
            setPadding(getPaddingLeft(), getLayout().getTopPadding(), getPaddingRight(), getPaddingBottom());
        } else {
            initDimensionsOnMeasure(i, i2);
            setMeasuredDimension((int) this.mTMeasuredWidth, (int) this.mTMeasuredHeight);
        }
    }

    public void setCJKVerticalSpace(float f) {
        this.mCJKspacingMult = f;
        requestLayout();
    }

    @Override // android.widget.TextView
    public void setGravity(int i) {
        switch (i) {
            case 101:
            case 102:
                this.mGravity = i;
                setVerticalMode(true);
                break;
            case 103:
            case 104:
            case com.miui.maml.R.styleable.AppCompatTheme_textAppearanceListItemSmall /* 105 */:
            case com.miui.maml.R.styleable.AppCompatTheme_textAppearancePopupMenuHeader /* 106 */:
                this.mGravity = i;
                setVerticalMode(false);
                break;
        }
        super.setGravity(this.mGravity);
    }

    public void setIsQinghe(boolean z) {
        this.mIsQinghe = z;
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView
    public void setTextSize(int i, float f) {
        super.setTextSize(i, f);
    }

    public void setVerticalMode(boolean z) {
        this.mIsVerticalMode = z;
        requestLayout();
    }

    public final boolean shouldDrawCharacter(char c) {
        return c != '\n';
    }
}
