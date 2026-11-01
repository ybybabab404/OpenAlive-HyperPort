package com.miui.lockscreeninfo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.TypefaceSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import androidx.appcompat.widget.AppCompatTextView;
import com.miui.lockscreeninfo.model.SignatureInfo;
import com.miui.lockscreeninfo.utils.FontUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import miuix.os.Build;

/* JADX INFO: loaded from: classes2.dex */
public class BaseTextView extends AppCompatTextView {
    public float currentLineSpace;
    public float currentTextSize;
    public boolean isFirst;
    public boolean isSuperSaveOpen;
    public boolean isSystemUI;
    public SignatureInfo mInfo;

    public BaseTextView(Context context) {
        this(context, null);
    }

    public BaseTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public BaseTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isSuperSaveOpen = false;
        this.mInfo = null;
        this.isFirst = true;
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        VerticalTextViewUtils.setUseBoundsForWidthCompat(this, false);
        this.currentTextSize = getDimen(R.dimen.textsize_b);
        this.currentLineSpace = 0.0f;
    }

    public final void constraintTextStyle(SignatureInfo signatureInfo) {
        int lines = signatureInfo.getLines();
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (getLineHeight() > 0) {
            float paddingTop = (layoutParams.height - getPaddingTop()) - getPaddingBottom();
            int lineHeight = (int) (paddingTop / getLineHeight());
            if (lineHeight > 1) {
                boolean z = signatureInfo.getTemplateFontType() == 0;
                lines = ((int) (paddingTop - ((((getLineHeight() - getLineSpacingExtra()) / getLineSpaceMultiply(z)) * getLineHeightMultiply(z)) - getLineHeight()))) / getLineHeight();
            } else {
                lines = lineHeight;
            }
        }
        if (lines <= 0) {
            lines = signatureInfo.getLines();
        }
        setMaxLines(lines);
        setText(getText());
    }

    public int getDimen(int i) {
        Context context = getContext().getApplicationContext() == null ? getContext() : getContext().getApplicationContext();
        return Math.round(context.getResources().getDimensionPixelSize(i) * VerticalTextViewUtils.calculateScale(context));
    }

    public float getInitTextSize() {
        return this.mInfo.getTextsize() <= 0.0f ? getDimen(R.dimen.textsize_b) : this.mInfo.getTextsize();
    }

    public final float getLineHeightMultiply(boolean z) {
        float f = ((z ? 608 : 500) * 1.0f) / 547;
        if (f < 1.0f) {
            return 1.0f;
        }
        return f;
    }

    public final float getLineSpaceMultiply(boolean z) {
        return z ? 0.85f : 0.8f;
    }

    public int getMaxTextSize() {
        return getDimen(R.dimen.textsize_max_b);
    }

    /* JADX WARN: Code duplicated, block: B:15:0x0052 A[PHI: r8
  0x0052: PHI (r8v23 float) = (r8v0 float), (r8v1 float) binds: [B:14:0x0050, B:17:0x0058] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:21:0x0082 A[PHI: r5
  0x0082: PHI (r5v14 float) = (r5v3 float), (r5v4 float) binds: [B:20:0x0080, B:23:0x0088] A[DONT_GENERATE, DONT_INLINE]] */
    public void init(SignatureInfo signatureInfo) {
        String str;
        Typeface typefaceDefaultFromStyle;
        SignatureInfo signatureInfo2;
        setEllipsize(TextUtils.TruncateAt.END);
        if (signatureInfo == null) {
            Log.d("BaseTextView", "model is null");
            return;
        }
        if (this.mInfo != null) {
            updateCurrentLineInfo();
        }
        this.mInfo = signatureInfo;
        float textsize = signatureInfo.getTextsize();
        boolean z = signatureInfo.getTemplateFontType() == 0;
        float lineSpaceValue = signatureInfo.getLineSpaceValue();
        float letterSpaceValue = signatureInfo.getLetterSpaceValue();
        BigDecimal bigDecimalAdd = BigDecimal.valueOf(lineSpaceValue).add(BigDecimal.valueOf(signatureInfo.getLineSpaceUnit()));
        RoundingMode roundingMode = RoundingMode.HALF_UP;
        float fFloatValue = bigDecimalAdd.setScale(2, roundingMode).floatValue();
        float f = 200.0f;
        if (fFloatValue >= 200.0f) {
            fFloatValue = f;
        } else {
            f = -200.0f;
            if (fFloatValue <= -200.0f) {
                fFloatValue = f;
            }
        }
        setLineSpacing(fFloatValue, getLineSpaceMultiply(z));
        float fFloatValue2 = BigDecimal.valueOf(letterSpaceValue).add(BigDecimal.valueOf(signatureInfo.getLetterSpaceUnit())).setScale(2, roundingMode).floatValue();
        float f2 = 1.0f;
        if (fFloatValue2 >= 1.0f) {
            fFloatValue2 = f2;
        } else {
            f2 = -1.0f;
            if (fFloatValue2 <= -1.0f) {
                fFloatValue2 = f2;
            }
        }
        setLetterSpacing(fFloatValue2);
        if (textsize <= 0.0f) {
            textsize = getDimen(R.dimen.textsize_b);
        }
        setTextSize(0, Math.min(BigDecimal.valueOf(textsize).add(BigDecimal.valueOf(signatureInfo.getTextSizeState())).setScale(2, roundingMode).floatValue(), getMaxTextSize()));
        setGravity(81);
        signatureInfo.setTextSizeState(0);
        signatureInfo.setLetterSpaceUnit(0.0f);
        signatureInfo.setLineSpaceUnit(0.0f);
        if (signatureInfo.getTemplateFontType() == 0) {
            setPadding(getPaddingLeft(), getPaddingTop(), getPaddingRight(), getDimen(R.dimen.padding_bottom_b));
            str = "miclock-qinghe-75w";
        } else {
            str = "miclock-inter-scaled-medium";
        }
        try {
            typefaceDefaultFromStyle = Typeface.create(str, 0);
        } catch (Exception unused) {
            typefaceDefaultFromStyle = Typeface.defaultFromStyle(0);
        }
        Typeface miSans = FontUtils.Companion.getMiSans(430);
        if (Build.IS_INTERNATIONAL_BUILD) {
            setTypeface(miSans);
        }
        if (miSans == null) {
            miSans = Typeface.defaultFromStyle(0);
        }
        SpannableString spannableString = new SpannableString(signatureInfo.getContent());
        char[] charArray = signatureInfo.getContent().toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            if (!VerticalTextViewUtils.isUyghurChar(charArray[i]) && !VerticalTextViewUtils.isTibetanChar(charArray[i]) && !VerticalTextViewUtils.isEmojiCharacter(charArray[i])) {
                if (VerticalTextViewUtils.isChinese(charArray[i])) {
                    if (signatureInfo.getTemplateFontType() == 1) {
                        spannableString.setSpan(new TypefaceSpan(miSans), i, i + 1, 33);
                    } else {
                        spannableString.setSpan(new TypefaceSpan(typefaceDefaultFromStyle), i, i + 1, 33);
                    }
                } else if (VerticalTextViewUtils.isEnglishChar(charArray[i])) {
                    spannableString.setSpan(new TypefaceSpan(typefaceDefaultFromStyle), i, i + 1, 33);
                }
            }
        }
        setText(spannableString);
        if (getLineHeight() < 0) {
            setTextSize(0, this.currentTextSize);
            setLineSpacing(this.currentLineSpace, getLineSpaceMultiply(z));
        }
        updateCurrentLineInfo();
        if (this.isFirst && (signatureInfo2 = this.mInfo) != null && Build.IS_INTERNATIONAL_BUILD) {
            this.isFirst = false;
            init(signatureInfo2);
        }
        constraintTextStyle(this.mInfo);
    }

    public boolean isSystemUI() {
        return this.isSystemUI;
    }

    @Override // android.widget.TextView, android.view.View
    public void onDraw(Canvas canvas) {
        canvas.save();
        if (getLayout() != null && !VerticalTextViewUtils.isGlobalRom()) {
            canvas.translate(0.0f, getLayout().getBottomPadding());
        }
        super.onDraw(canvas);
        canvas.restore();
    }

    @Override // androidx.appcompat.widget.AppCompatTextView, android.widget.TextView, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
    }

    public void setIsSystemUI(boolean z) {
        this.isSystemUI = z;
    }

    public void setSuperSaveOpen(boolean z) {
        this.isSuperSaveOpen = z;
    }

    @Override // android.widget.TextView
    public void setTextColor(int i) {
        if (this.isSuperSaveOpen) {
            i = -1;
        }
        super.setTextColor(i);
    }

    public final void updateCurrentLineInfo() {
        if (getLineHeight() > 0) {
            this.currentLineSpace = getLineSpacingExtra();
            this.currentTextSize = getTextSize();
        }
    }
}
