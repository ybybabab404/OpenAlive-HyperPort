package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import androidx.collection.LongSparseArray;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.TextDelegate;
import com.airbnb.lottie.animation.content.ContentGroup;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.TextKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.ValueCallbackKeyframeAnimation;
import com.airbnb.lottie.model.DocumentData;
import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.model.FontCharacter;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.model.content.ShapeGroup;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieValueCallback;
import com.miui.maml.elements.MusicLyricParser;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class TextLayer extends BaseLayer {
    public final LongSparseArray codePointCache;
    public BaseKeyframeAnimation colorAnimation;
    public BaseKeyframeAnimation colorCallbackAnimation;
    public final LottieComposition composition;
    public final Map contentsForCharacter;
    public final Paint fillPaint;
    public final LottieDrawable lottieDrawable;
    public final Matrix matrix;
    public final RectF rectF;
    public final StringBuilder stringBuilder;
    public BaseKeyframeAnimation strokeColorAnimation;
    public BaseKeyframeAnimation strokeColorCallbackAnimation;
    public final Paint strokePaint;
    public BaseKeyframeAnimation strokeWidthAnimation;
    public BaseKeyframeAnimation strokeWidthCallbackAnimation;
    public final TextKeyframeAnimation textAnimation;
    public BaseKeyframeAnimation textSizeCallbackAnimation;
    public BaseKeyframeAnimation trackingAnimation;
    public BaseKeyframeAnimation trackingCallbackAnimation;
    public BaseKeyframeAnimation typefaceCallbackAnimation;

    /* JADX INFO: renamed from: com.airbnb.lottie.model.layer.TextLayer$3, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass3 {
        public static final /* synthetic */ int[] $SwitchMap$com$airbnb$lottie$model$DocumentData$Justification;

        static {
            int[] iArr = new int[DocumentData.Justification.values().length];
            $SwitchMap$com$airbnb$lottie$model$DocumentData$Justification = iArr;
            try {
                iArr[DocumentData.Justification.LEFT_ALIGN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$DocumentData$Justification[DocumentData.Justification.RIGHT_ALIGN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$airbnb$lottie$model$DocumentData$Justification[DocumentData.Justification.CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public TextLayer(LottieDrawable lottieDrawable, Layer layer) {
        AnimatableFloatValue animatableFloatValue;
        AnimatableFloatValue animatableFloatValue2;
        AnimatableColorValue animatableColorValue;
        AnimatableColorValue animatableColorValue2;
        super(lottieDrawable, layer);
        this.stringBuilder = new StringBuilder(2);
        this.rectF = new RectF();
        this.matrix = new Matrix();
        int i = 1;
        this.fillPaint = new Paint(i) { // from class: com.airbnb.lottie.model.layer.TextLayer.1
            {
                setStyle(Paint.Style.FILL);
            }
        };
        this.strokePaint = new Paint(i) { // from class: com.airbnb.lottie.model.layer.TextLayer.2
            {
                setStyle(Paint.Style.STROKE);
            }
        };
        this.contentsForCharacter = new HashMap();
        this.codePointCache = new LongSparseArray();
        this.lottieDrawable = lottieDrawable;
        this.composition = layer.getComposition();
        TextKeyframeAnimation textKeyframeAnimationCreateAnimation = layer.getText().createAnimation();
        this.textAnimation = textKeyframeAnimationCreateAnimation;
        textKeyframeAnimationCreateAnimation.addUpdateListener(this);
        addAnimation(textKeyframeAnimationCreateAnimation);
        AnimatableTextProperties textProperties = layer.getTextProperties();
        if (textProperties != null && (animatableColorValue2 = textProperties.color) != null) {
            BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation = animatableColorValue2.createAnimation();
            this.colorAnimation = baseKeyframeAnimationCreateAnimation;
            baseKeyframeAnimationCreateAnimation.addUpdateListener(this);
            addAnimation(this.colorAnimation);
        }
        if (textProperties != null && (animatableColorValue = textProperties.stroke) != null) {
            BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation2 = animatableColorValue.createAnimation();
            this.strokeColorAnimation = baseKeyframeAnimationCreateAnimation2;
            baseKeyframeAnimationCreateAnimation2.addUpdateListener(this);
            addAnimation(this.strokeColorAnimation);
        }
        if (textProperties != null && (animatableFloatValue2 = textProperties.strokeWidth) != null) {
            BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation3 = animatableFloatValue2.createAnimation();
            this.strokeWidthAnimation = baseKeyframeAnimationCreateAnimation3;
            baseKeyframeAnimationCreateAnimation3.addUpdateListener(this);
            addAnimation(this.strokeWidthAnimation);
        }
        if (textProperties == null || (animatableFloatValue = textProperties.tracking) == null) {
            return;
        }
        BaseKeyframeAnimation baseKeyframeAnimationCreateAnimation4 = animatableFloatValue.createAnimation();
        this.trackingAnimation = baseKeyframeAnimationCreateAnimation4;
        baseKeyframeAnimationCreateAnimation4.addUpdateListener(this);
        addAnimation(this.trackingAnimation);
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.model.KeyPathElement
    public void addValueCallback(Object obj, LottieValueCallback lottieValueCallback) {
        super.addValueCallback(obj, lottieValueCallback);
        if (obj == LottieProperty.COLOR) {
            BaseKeyframeAnimation baseKeyframeAnimation = this.colorCallbackAnimation;
            if (baseKeyframeAnimation != null) {
                removeAnimation(baseKeyframeAnimation);
            }
            if (lottieValueCallback == null) {
                this.colorCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.colorCallbackAnimation = valueCallbackKeyframeAnimation;
            valueCallbackKeyframeAnimation.addUpdateListener(this);
            addAnimation(this.colorCallbackAnimation);
            return;
        }
        if (obj == LottieProperty.STROKE_COLOR) {
            BaseKeyframeAnimation baseKeyframeAnimation2 = this.strokeColorCallbackAnimation;
            if (baseKeyframeAnimation2 != null) {
                removeAnimation(baseKeyframeAnimation2);
            }
            if (lottieValueCallback == null) {
                this.strokeColorCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation2 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.strokeColorCallbackAnimation = valueCallbackKeyframeAnimation2;
            valueCallbackKeyframeAnimation2.addUpdateListener(this);
            addAnimation(this.strokeColorCallbackAnimation);
            return;
        }
        if (obj == LottieProperty.STROKE_WIDTH) {
            BaseKeyframeAnimation baseKeyframeAnimation3 = this.strokeWidthCallbackAnimation;
            if (baseKeyframeAnimation3 != null) {
                removeAnimation(baseKeyframeAnimation3);
            }
            if (lottieValueCallback == null) {
                this.strokeWidthCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation3 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.strokeWidthCallbackAnimation = valueCallbackKeyframeAnimation3;
            valueCallbackKeyframeAnimation3.addUpdateListener(this);
            addAnimation(this.strokeWidthCallbackAnimation);
            return;
        }
        if (obj == LottieProperty.TEXT_TRACKING) {
            BaseKeyframeAnimation baseKeyframeAnimation4 = this.trackingCallbackAnimation;
            if (baseKeyframeAnimation4 != null) {
                removeAnimation(baseKeyframeAnimation4);
            }
            if (lottieValueCallback == null) {
                this.trackingCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation4 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.trackingCallbackAnimation = valueCallbackKeyframeAnimation4;
            valueCallbackKeyframeAnimation4.addUpdateListener(this);
            addAnimation(this.trackingCallbackAnimation);
            return;
        }
        if (obj == LottieProperty.TEXT_SIZE) {
            BaseKeyframeAnimation baseKeyframeAnimation5 = this.textSizeCallbackAnimation;
            if (baseKeyframeAnimation5 != null) {
                removeAnimation(baseKeyframeAnimation5);
            }
            if (lottieValueCallback == null) {
                this.textSizeCallbackAnimation = null;
                return;
            }
            ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation5 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
            this.textSizeCallbackAnimation = valueCallbackKeyframeAnimation5;
            valueCallbackKeyframeAnimation5.addUpdateListener(this);
            addAnimation(this.textSizeCallbackAnimation);
            return;
        }
        if (obj != LottieProperty.TYPEFACE) {
            if (obj == LottieProperty.TEXT) {
                this.textAnimation.setStringValueCallback(lottieValueCallback);
                return;
            }
            return;
        }
        BaseKeyframeAnimation baseKeyframeAnimation6 = this.typefaceCallbackAnimation;
        if (baseKeyframeAnimation6 != null) {
            removeAnimation(baseKeyframeAnimation6);
        }
        if (lottieValueCallback == null) {
            this.typefaceCallbackAnimation = null;
            return;
        }
        ValueCallbackKeyframeAnimation valueCallbackKeyframeAnimation6 = new ValueCallbackKeyframeAnimation(lottieValueCallback);
        this.typefaceCallbackAnimation = valueCallbackKeyframeAnimation6;
        valueCallbackKeyframeAnimation6.addUpdateListener(this);
        addAnimation(this.typefaceCallbackAnimation);
    }

    public final void applyJustification(DocumentData.Justification justification, Canvas canvas, float f) {
        int i = AnonymousClass3.$SwitchMap$com$airbnb$lottie$model$DocumentData$Justification[justification.ordinal()];
        if (i == 2) {
            canvas.translate(-f, 0.0f);
        } else {
            if (i != 3) {
                return;
            }
            canvas.translate((-f) / 2.0f, 0.0f);
        }
    }

    public final String codePointToString(String str, int i) {
        int iCodePointAt = str.codePointAt(i);
        int iCharCount = Character.charCount(iCodePointAt) + i;
        while (iCharCount < str.length()) {
            int iCodePointAt2 = str.codePointAt(iCharCount);
            if (!isModifier(iCodePointAt2)) {
                break;
            }
            iCharCount += Character.charCount(iCodePointAt2);
            iCodePointAt = (iCodePointAt * 31) + iCodePointAt2;
        }
        long j = iCodePointAt;
        if (this.codePointCache.containsKey(j)) {
            return (String) this.codePointCache.get(j);
        }
        this.stringBuilder.setLength(0);
        while (i < iCharCount) {
            int iCodePointAt3 = str.codePointAt(i);
            this.stringBuilder.appendCodePoint(iCodePointAt3);
            i += Character.charCount(iCodePointAt3);
        }
        String string = this.stringBuilder.toString();
        this.codePointCache.put(j, string);
        return string;
    }

    public final void drawCharacter(String str, Paint paint, Canvas canvas) {
        if (paint.getColor() == 0) {
            return;
        }
        if (paint.getStyle() == Paint.Style.STROKE && paint.getStrokeWidth() == 0.0f) {
            return;
        }
        canvas.drawText(str, 0, str.length(), 0.0f, 0.0f, paint);
    }

    public final void drawCharacterAsGlyph(FontCharacter fontCharacter, Matrix matrix, float f, DocumentData documentData, Canvas canvas) {
        List contentsForCharacter = getContentsForCharacter(fontCharacter);
        for (int i = 0; i < contentsForCharacter.size(); i++) {
            Path path = ((ContentGroup) contentsForCharacter.get(i)).getPath();
            path.computeBounds(this.rectF, false);
            this.matrix.set(matrix);
            this.matrix.preTranslate(0.0f, (-documentData.baselineShift) * Utils.dpScale());
            this.matrix.preScale(f, f);
            path.transform(this.matrix);
            if (documentData.strokeOverFill) {
                drawGlyph(path, this.fillPaint, canvas);
                drawGlyph(path, this.strokePaint, canvas);
            } else {
                drawGlyph(path, this.strokePaint, canvas);
                drawGlyph(path, this.fillPaint, canvas);
            }
        }
    }

    public final void drawCharacterFromFont(String str, DocumentData documentData, Canvas canvas) {
        if (documentData.strokeOverFill) {
            drawCharacter(str, this.fillPaint, canvas);
            drawCharacter(str, this.strokePaint, canvas);
        } else {
            drawCharacter(str, this.strokePaint, canvas);
            drawCharacter(str, this.fillPaint, canvas);
        }
    }

    public final void drawFontTextLine(String str, DocumentData documentData, Canvas canvas, float f) {
        int length = 0;
        while (length < str.length()) {
            String strCodePointToString = codePointToString(str, length);
            length += strCodePointToString.length();
            drawCharacterFromFont(strCodePointToString, documentData, canvas);
            canvas.translate(this.fillPaint.measureText(strCodePointToString) + f, 0.0f);
        }
    }

    public final void drawGlyph(Path path, Paint paint, Canvas canvas) {
        if (paint.getColor() == 0) {
            return;
        }
        if (paint.getStyle() == Paint.Style.STROKE && paint.getStrokeWidth() == 0.0f) {
            return;
        }
        canvas.drawPath(path, paint);
    }

    public final void drawGlyphTextLine(String str, DocumentData documentData, Matrix matrix, Font font, Canvas canvas, float f, float f2) {
        TextLayer textLayer;
        DocumentData documentData2;
        Matrix matrix2;
        Canvas canvas2;
        float f3;
        float fFloatValue;
        int i = 0;
        while (i < str.length()) {
            FontCharacter fontCharacter = (FontCharacter) this.composition.getCharacters().get(FontCharacter.hashFor(str.charAt(i), font.getFamily(), font.getStyle()));
            if (fontCharacter == null) {
                textLayer = this;
                documentData2 = documentData;
                matrix2 = matrix;
                canvas2 = canvas;
                f3 = f2;
            } else {
                textLayer = this;
                documentData2 = documentData;
                matrix2 = matrix;
                canvas2 = canvas;
                f3 = f2;
                textLayer.drawCharacterAsGlyph(fontCharacter, matrix2, f3, documentData2, canvas2);
                float width = ((float) fontCharacter.getWidth()) * f3 * Utils.dpScale() * f;
                float f4 = documentData2.tracking / 10.0f;
                BaseKeyframeAnimation baseKeyframeAnimation = textLayer.trackingCallbackAnimation;
                if (baseKeyframeAnimation != null) {
                    fFloatValue = ((Float) baseKeyframeAnimation.getValue()).floatValue();
                } else {
                    BaseKeyframeAnimation baseKeyframeAnimation2 = textLayer.trackingAnimation;
                    if (baseKeyframeAnimation2 != null) {
                        fFloatValue = ((Float) baseKeyframeAnimation2.getValue()).floatValue();
                    }
                    canvas2.translate(width + (f4 * f), 0.0f);
                }
                f4 += fFloatValue;
                canvas2.translate(width + (f4 * f), 0.0f);
            }
            i++;
            this = textLayer;
            matrix = matrix2;
            f2 = f3;
            documentData = documentData2;
            canvas = canvas2;
        }
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer
    public void drawLayer(Canvas canvas, Matrix matrix, int i) {
        canvas.save();
        if (!this.lottieDrawable.useTextGlyphs()) {
            canvas.concat(matrix);
        }
        DocumentData documentData = (DocumentData) this.textAnimation.getValue();
        Font font = (Font) this.composition.getFonts().get(documentData.fontName);
        if (font == null) {
            canvas.restore();
            return;
        }
        BaseKeyframeAnimation baseKeyframeAnimation = this.colorCallbackAnimation;
        if (baseKeyframeAnimation != null) {
            this.fillPaint.setColor(((Integer) baseKeyframeAnimation.getValue()).intValue());
        } else {
            BaseKeyframeAnimation baseKeyframeAnimation2 = this.colorAnimation;
            if (baseKeyframeAnimation2 != null) {
                this.fillPaint.setColor(((Integer) baseKeyframeAnimation2.getValue()).intValue());
            } else {
                this.fillPaint.setColor(documentData.color);
            }
        }
        BaseKeyframeAnimation baseKeyframeAnimation3 = this.strokeColorCallbackAnimation;
        if (baseKeyframeAnimation3 != null) {
            this.strokePaint.setColor(((Integer) baseKeyframeAnimation3.getValue()).intValue());
        } else {
            BaseKeyframeAnimation baseKeyframeAnimation4 = this.strokeColorAnimation;
            if (baseKeyframeAnimation4 != null) {
                this.strokePaint.setColor(((Integer) baseKeyframeAnimation4.getValue()).intValue());
            } else {
                this.strokePaint.setColor(documentData.strokeColor);
            }
        }
        int iIntValue = ((this.transform.getOpacity() == null ? 100 : ((Integer) this.transform.getOpacity().getValue()).intValue()) * 255) / 100;
        this.fillPaint.setAlpha(iIntValue);
        this.strokePaint.setAlpha(iIntValue);
        BaseKeyframeAnimation baseKeyframeAnimation5 = this.strokeWidthCallbackAnimation;
        if (baseKeyframeAnimation5 != null) {
            this.strokePaint.setStrokeWidth(((Float) baseKeyframeAnimation5.getValue()).floatValue());
        } else {
            BaseKeyframeAnimation baseKeyframeAnimation6 = this.strokeWidthAnimation;
            if (baseKeyframeAnimation6 != null) {
                this.strokePaint.setStrokeWidth(((Float) baseKeyframeAnimation6.getValue()).floatValue());
            } else {
                this.strokePaint.setStrokeWidth(documentData.strokeWidth * Utils.dpScale() * Utils.getScale(matrix));
            }
        }
        if (this.lottieDrawable.useTextGlyphs()) {
            drawTextGlyphs(documentData, matrix, font, canvas);
        } else {
            drawTextWithFont(documentData, font, canvas);
        }
        canvas.restore();
    }

    public final void drawTextGlyphs(DocumentData documentData, Matrix matrix, Font font, Canvas canvas) {
        BaseKeyframeAnimation baseKeyframeAnimation = this.textSizeCallbackAnimation;
        float fFloatValue = (baseKeyframeAnimation != null ? ((Float) baseKeyframeAnimation.getValue()).floatValue() : documentData.size) / 100.0f;
        float scale = Utils.getScale(matrix);
        String str = documentData.text;
        float fDpScale = documentData.lineHeight * Utils.dpScale();
        List textLines = getTextLines(str);
        int size = textLines.size();
        for (int i = 0; i < size; i++) {
            String str2 = (String) textLines.get(i);
            float textLineWidthForGlyphs = getTextLineWidthForGlyphs(str2, font, fFloatValue, scale);
            canvas.save();
            applyJustification(documentData.justification, canvas, textLineWidthForGlyphs);
            canvas.translate(0.0f, (i * fDpScale) - (((size - 1) * fDpScale) / 2.0f));
            drawGlyphTextLine(str2, documentData, matrix, font, canvas, scale, fFloatValue);
            canvas.restore();
        }
    }

    /* JADX WARN: Code duplicated, block: B:21:0x0090 A[LOOP:0: B:20:0x008e->B:21:0x0090, LOOP_END] */
    public final void drawTextWithFont(DocumentData documentData, Font font, Canvas canvas) {
        float fFloatValue;
        float fDpScale;
        List textLines;
        int size;
        int i;
        Typeface typeface = getTypeface(font);
        if (typeface == null) {
            return;
        }
        String textInternal = documentData.text;
        TextDelegate textDelegate = this.lottieDrawable.getTextDelegate();
        if (textDelegate != null) {
            textInternal = textDelegate.getTextInternal(getName(), textInternal);
        }
        this.fillPaint.setTypeface(typeface);
        BaseKeyframeAnimation baseKeyframeAnimation = this.textSizeCallbackAnimation;
        float fFloatValue2 = baseKeyframeAnimation != null ? ((Float) baseKeyframeAnimation.getValue()).floatValue() : documentData.size;
        this.fillPaint.setTextSize(Utils.dpScale() * fFloatValue2);
        this.strokePaint.setTypeface(this.fillPaint.getTypeface());
        this.strokePaint.setTextSize(this.fillPaint.getTextSize());
        float fDpScale2 = documentData.lineHeight * Utils.dpScale();
        float f = documentData.tracking / 10.0f;
        BaseKeyframeAnimation baseKeyframeAnimation2 = this.trackingCallbackAnimation;
        if (baseKeyframeAnimation2 == null) {
            BaseKeyframeAnimation baseKeyframeAnimation3 = this.trackingAnimation;
            if (baseKeyframeAnimation3 != null) {
                fFloatValue = ((Float) baseKeyframeAnimation3.getValue()).floatValue();
            }
            fDpScale = ((f * Utils.dpScale()) * fFloatValue2) / 100.0f;
            textLines = getTextLines(textInternal);
            size = textLines.size();
            for (i = 0; i < size; i++) {
                String str = (String) textLines.get(i);
                float fMeasureText = this.strokePaint.measureText(str) + ((str.length() - 1) * fDpScale);
                canvas.save();
                applyJustification(documentData.justification, canvas, fMeasureText);
                canvas.translate(0.0f, (i * fDpScale2) - (((size - 1) * fDpScale2) / 2.0f));
                drawFontTextLine(str, documentData, canvas, fDpScale);
                canvas.restore();
            }
        }
        fFloatValue = ((Float) baseKeyframeAnimation2.getValue()).floatValue();
        f += fFloatValue;
        fDpScale = ((f * Utils.dpScale()) * fFloatValue2) / 100.0f;
        textLines = getTextLines(textInternal);
        size = textLines.size();
        while (i < size) {
            String str2 = (String) textLines.get(i);
            float fMeasureText2 = this.strokePaint.measureText(str2) + ((str2.length() - 1) * fDpScale);
            canvas.save();
            applyJustification(documentData.justification, canvas, fMeasureText2);
            canvas.translate(0.0f, (i * fDpScale2) - (((size - 1) * fDpScale2) / 2.0f));
            drawFontTextLine(str2, documentData, canvas, fDpScale);
            canvas.restore();
        }
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.animation.content.DrawingContent
    public void getBounds(RectF rectF, Matrix matrix, boolean z) {
        super.getBounds(rectF, matrix, z);
        rectF.set(0.0f, 0.0f, this.composition.getBounds().width(), this.composition.getBounds().height());
    }

    public final List getContentsForCharacter(FontCharacter fontCharacter) {
        if (this.contentsForCharacter.containsKey(fontCharacter)) {
            return (List) this.contentsForCharacter.get(fontCharacter);
        }
        List shapes = fontCharacter.getShapes();
        int size = shapes.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(new ContentGroup(this.lottieDrawable, this, (ShapeGroup) shapes.get(i)));
        }
        this.contentsForCharacter.put(fontCharacter, arrayList);
        return arrayList;
    }

    public final float getTextLineWidthForGlyphs(String str, Font font, float f, float f2) {
        float width = 0.0f;
        for (int i = 0; i < str.length(); i++) {
            FontCharacter fontCharacter = (FontCharacter) this.composition.getCharacters().get(FontCharacter.hashFor(str.charAt(i), font.getFamily(), font.getStyle()));
            if (fontCharacter != null) {
                width = (float) (((double) width) + (fontCharacter.getWidth() * ((double) f) * ((double) Utils.dpScale()) * ((double) f2)));
            }
        }
        return width;
    }

    public final List getTextLines(String str) {
        return Arrays.asList(str.replaceAll(MusicLyricParser.CRLF, "\r").replaceAll("\n", "\r").split("\r"));
    }

    public final Typeface getTypeface(Font font) {
        Typeface typeface;
        BaseKeyframeAnimation baseKeyframeAnimation = this.typefaceCallbackAnimation;
        if (baseKeyframeAnimation != null && (typeface = (Typeface) baseKeyframeAnimation.getValue()) != null) {
            return typeface;
        }
        Typeface typeface2 = this.lottieDrawable.getTypeface(font.getFamily(), font.getStyle());
        return typeface2 != null ? typeface2 : font.getTypeface();
    }

    public final boolean isModifier(int i) {
        return Character.getType(i) == 16 || Character.getType(i) == 27 || Character.getType(i) == 6 || Character.getType(i) == 28 || Character.getType(i) == 8 || Character.getType(i) == 19;
    }
}
