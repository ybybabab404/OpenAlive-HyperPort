package com.miui.lockscreeninfo;

import android.content.Context;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import com.miui.lockscreeninfo.model.SignatureInfo;
import java.math.BigDecimal;
import java.math.RoundingMode;

/* JADX INFO: loaded from: classes2.dex */
public class SignatureView extends VerticalTextView {
    public boolean isFirst;

    public SignatureView(Context context) {
        this(context, null);
    }

    public SignatureView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public SignatureView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.isFirst = true;
    }

    @Override // com.miui.lockscreeninfo.BaseTextView
    public float getInitTextSize() {
        int dimen = getDimen(this.mInfo.isVerticalMode() ? R.dimen.textsize_a_v : R.dimen.textsize_a_h);
        int dimen2 = getDimen(R.dimen.textsize_a_v);
        if (this.mInfo.getTextsize() > 0.0f) {
            return this.mInfo.getTextsize();
        }
        if (VerticalTextViewUtils.isGlobalRom()) {
            return dimen;
        }
        if (VerticalTextViewUtils.isRedmiBrand()) {
            dimen = dimen2;
        }
        return dimen;
    }

    @Override // com.miui.lockscreeninfo.BaseTextView
    public int getMaxTextSize() {
        return getDimen(R.dimen.textsize_max_a);
    }

    @Override // com.miui.lockscreeninfo.BaseTextView
    public void init(SignatureInfo signatureInfo) {
        float f;
        Typeface typefaceDefaultFromStyle;
        setEllipsize(TextUtils.TruncateAt.END);
        this.mInfo = signatureInfo;
        int dimen = getDimen(signatureInfo.isVerticalMode() ? R.dimen.textsize_a_v : R.dimen.textsize_a_h);
        int dimen2 = getDimen(R.dimen.textsize_a_v);
        boolean z = signatureInfo.getTemplateFontType() == 0;
        boolean z2 = signatureInfo.getAlignment() == 106;
        setIsQinghe(z);
        float f2 = -200.0f;
        float f3 = -1.0f;
        if ((this.isFirst && this.mInfo != null) || isSystemUI()) {
            this.isFirst = false;
            if (signatureInfo.getTextsize() <= 0.0f) {
                if (!VerticalTextViewUtils.isGlobalRom() && VerticalTextViewUtils.isRedmiBrand()) {
                    dimen = dimen2;
                }
                setTextSize(0, dimen);
            } else {
                setTextSize(0, signatureInfo.getTextsize());
            }
            float letterSpaceValue = signatureInfo.getLetterSpaceValue();
            if (signatureInfo.isVerticalMode()) {
                float fFloatValue = BigDecimal.valueOf(signatureInfo.getLetterSpaceUnit()).add(new BigDecimal(letterSpaceValue)).setScale(2, RoundingMode.HALF_UP).floatValue();
                if (fFloatValue >= 1.0f) {
                    f3 = 1.0f;
                } else if (fFloatValue > -1.0f) {
                    f3 = fFloatValue;
                }
                setCJKVerticalSpace(f3);
            } else if (z2) {
                setLetterSpacing(0.0f);
            } else {
                setWillNotDraw(false);
                float fFloatValue2 = BigDecimal.valueOf((letterSpaceValue == 0.0f && signatureInfo.getLetterSpaceUnit() == 0.0f && !z) ? -0.05f : letterSpaceValue).add(BigDecimal.valueOf(signatureInfo.getLetterSpaceUnit())).setScale(2, RoundingMode.HALF_UP).floatValue();
                if (fFloatValue2 >= 1.0f) {
                    f3 = 1.0f;
                } else if (fFloatValue2 > -1.0f) {
                    f3 = fFloatValue2;
                }
                setLetterSpacing(f3);
            }
            float fFloatValue3 = BigDecimal.valueOf(signatureInfo.getLineSpaceValue()).add(BigDecimal.valueOf(signatureInfo.getLineSpaceUnit())).setScale(2, RoundingMode.HALF_UP).floatValue();
            if (fFloatValue3 >= 200.0f) {
                f2 = 200.0f;
            } else if (fFloatValue3 > -200.0f) {
                f2 = fFloatValue3;
            }
            setLineSpacing(f2, z ? 1.0f : 0.85f);
        } else if (signatureInfo.getTextsize() <= 0.0f && signatureInfo.getTextSizeState() == 0 && signatureInfo.getLetterSpaceValue() == 0.0f && signatureInfo.getLetterSpaceUnit() == 0.0f && signatureInfo.getLineSpaceValue() == 0.0f && signatureInfo.getLineSpaceUnit() == 0.0f) {
            if (!VerticalTextViewUtils.isGlobalRom() && VerticalTextViewUtils.isRedmiBrand()) {
                dimen = dimen2;
            }
            setTextSize(0, dimen);
            setLetterSpacing((z || z2) ? 0.0f : -0.05f);
            setLineSpacing(0.0f, z ? 1.0f : 0.85f);
        } else {
            float textSize = getTextSize();
            if (signatureInfo.getTextsize() <= 0.0f) {
                setTextSize(0, getInitTextSize());
            } else {
                float fFloatValue4 = BigDecimal.valueOf(signatureInfo.getTextsize()).setScale(2, RoundingMode.HALF_UP).floatValue();
                if (fFloatValue4 >= getMaxTextSize()) {
                    fFloatValue4 = getMaxTextSize();
                } else if (fFloatValue4 <= 10.0f) {
                    fFloatValue4 = 10.0f;
                }
                setTextSize(0, fFloatValue4);
            }
            signatureInfo.setTextSizeState(0);
            float lineSpacingExtra = getLineSpacingExtra();
            BigDecimal bigDecimalAdd = BigDecimal.valueOf(getLineSpacingExtra()).add(BigDecimal.valueOf(signatureInfo.getLineSpaceUnit()));
            RoundingMode roundingMode = RoundingMode.HALF_UP;
            float fFloatValue5 = bigDecimalAdd.setScale(2, roundingMode).floatValue();
            if (fFloatValue5 >= 200.0f) {
                f = 200.0f;
            } else {
                f = fFloatValue5 <= -200.0f ? -200.0f : fFloatValue5;
            }
            setLineSpacing(f, z ? 1.0f : 0.85f);
            if (getLineHeight() < 0) {
                setTextSize(0, textSize);
                setLineSpacing(lineSpacingExtra, z ? 1.0f : 0.85f);
            }
            updateLetterSpacing(signatureInfo);
            if (signatureInfo.isVerticalMode()) {
                float fFloatValue6 = BigDecimal.valueOf(signatureInfo.getLetterSpaceUnit()).add(new BigDecimal(getCJKVerticalSpacingMultiplier())).setScale(2, roundingMode).floatValue();
                if (fFloatValue6 >= 1.0f) {
                    f3 = 1.0f;
                } else if (fFloatValue6 > -1.0f) {
                    f3 = fFloatValue6;
                }
                setCJKVerticalSpace(f3);
            } else if (z2) {
                setLetterSpacing(0.0f);
            } else {
                setWillNotDraw(false);
                float fFloatValue7 = BigDecimal.valueOf(getLetterSpacing()).add(BigDecimal.valueOf(signatureInfo.getLetterSpaceUnit())).setScale(2, roundingMode).floatValue();
                if (fFloatValue7 >= 1.0f) {
                    f3 = 1.0f;
                } else if (fFloatValue7 > -1.0f) {
                    f3 = fFloatValue7;
                }
                setLetterSpacing(f3);
            }
        }
        setText(signatureInfo.getContent());
        setGravity(signatureInfo.getAlignment());
        signatureInfo.setLetterSpaceUnit(0.0f);
        signatureInfo.setLineSpaceUnit(0.0f);
        setFontFeatureSettings("'liga' off");
        String str = signatureInfo.getTemplateFontType() == 0 ? "miclock-qinghe-75w" : "miclock-inter-scaled-medium";
        if (TextUtils.isEmpty(str)) {
            typefaceDefaultFromStyle = Typeface.defaultFromStyle(0);
        } else {
            try {
                typefaceDefaultFromStyle = Typeface.create(str, 0);
            } catch (Exception e) {
                Log.i(VerticalTextView.TAG, "init: " + e);
                typefaceDefaultFromStyle = Typeface.defaultFromStyle(0);
            }
        }
        setTypeface(typefaceDefaultFromStyle);
        int lines = signatureInfo.getLines();
        if (getHeight() > 0 && getLineHeight() > 0) {
            lines = signatureInfo.getTemplateFontType() == 0 ? (getHeight() / getLineHeight()) + 1 : getHeight() / getLineHeight();
        }
        if (lines <= 0) {
            lines = signatureInfo.getLines();
        }
        setLines(lines);
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        init(this.mInfo);
    }

    public final void updateLetterSpacing(SignatureInfo signatureInfo) {
        if (signatureInfo.isVerticalMode()) {
            setCJKVerticalSpace(signatureInfo.getLetterSpaceValue());
        } else {
            setLetterSpacing(signatureInfo.getLetterSpaceValue());
        }
    }
}
