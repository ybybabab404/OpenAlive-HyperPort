package com.miui.maml.elements;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.miui.maml.ScreenElementRoot;
import com.miui.maml.data.Expression;
import com.miui.maml.data.IndexedVariable;
import com.miui.maml.data.Variables;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.maml.folme.AnimatedPropertyType;
import com.miui.maml.folme.AnimatedTarget;
import com.miui.maml.folme.PropertyWrapper;
import com.miui.maml.util.ColorParser;
import com.miui.maml.util.HideSdkDependencyUtils;
import com.miui.maml.util.MamlLog;
import com.miui.maml.util.ReflectionHelper;
import com.miui.maml.util.TextFormatter;
import com.miui.maml.util.Utils;
import java.io.File;
import java.util.concurrent.ConcurrentHashMap;
import miuix.animation.property.FloatProperty;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes2.dex */
public class TextScreenElement extends AnimatedScreenElement {
    private static final String CRLF = "\n";
    private static final int DEFAULT_SIZE = 18;
    private static final String LOG_TAG = "TextScreenElement";
    private static final int MARQUEE_FRAMERATE = 45;
    private static final int PADDING = 50;
    private static final String PROPERTY_NAME_TEXT_COLOR = "textColor";
    private static final String PROPERTY_NAME_TEXT_SHADOW_COLOR = "textShadowColor";
    private static final String PROPERTY_NAME_TEXT_SIZE = "textSize";
    private static final String RAW_CRLF = "\\n";
    public static final String TAG_NAME = "Text";
    public static final AnimatedProperty.AnimatedColorProperty TEXT_COLOR;
    public static final String TEXT_HEIGHT = "text_height";
    public static final AnimatedProperty.AnimatedColorProperty TEXT_SHADOW_COLOR;
    public static final AnimatedProperty TEXT_SIZE;
    public static final String TEXT_WIDTH = "text_width";
    private static final Object mLock = new Object();
    private ColorParser mColorParser;
    private boolean mEllipsis;
    private int mFontPathWeight;
    private boolean mFontScaleEnabled;
    protected TextFormatter mFormatter;
    private float mLayoutWidth;
    private int mLineClamp;
    private Expression mLineClampExp;
    private int mMarqueeGap;
    private float mMarqueePos;
    private int mMarqueeSpeed;
    private boolean mMultiLine;
    private final TextPaint mPaint;
    private long mPreviousTime;
    private String mSetText;
    private ColorParser mShadowColorParser;
    private float mShadowDx;
    private float mShadowDy;
    private float mShadowRadius;
    private boolean mShouldMarquee;
    private float mSpacingAdd;
    private Expression mSpacingAddExp;
    private float mSpacingMult;
    private Expression mSpacingMultExp;
    private String mText;
    private PropertyWrapper mTextColorProperty;

    @Nullable
    private String mTextFontName;

    @Nullable
    private IndexedVariable mTextFontVar;
    private float mTextHeight;
    private IndexedVariable mTextHeightVar;
    private StaticLayout mTextLayout;
    private PropertyWrapper mTextShadowColorProperty;
    private float mTextSize;
    private PropertyWrapper mTextSizeProperty;
    private float mTextWidth;
    private IndexedVariable mTextWidthVar;

    /* JADX INFO: renamed from: com.miui.maml.elements.TextScreenElement$4, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass4 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$elements$ScreenElement$Align;

        static {
            int[] iArr = new int[ScreenElement.Align.values().length];
            $SwitchMap$com$miui$maml$elements$ScreenElement$Align = iArr;
            try {
                iArr[ScreenElement.Align.LEFT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$maml$elements$ScreenElement$Align[ScreenElement.Align.CENTER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$maml$elements$ScreenElement$Align[ScreenElement.Align.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    static {
        AnimatedProperty.AnimatedColorProperty animatedColorProperty = new AnimatedProperty.AnimatedColorProperty(PROPERTY_NAME_TEXT_COLOR) { // from class: com.miui.maml.elements.TextScreenElement.1
            @Override // miuix.animation.property.ColorProperty, miuix.animation.property.IIntValueProperty
            public int getIntValue(AnimatedScreenElement animatedScreenElement) {
                if (animatedScreenElement instanceof TextScreenElement) {
                    return (int) ((TextScreenElement) animatedScreenElement).mTextColorProperty.getValue();
                }
                return -16777216;
            }

            @Override // miuix.animation.property.ColorProperty, miuix.animation.property.IIntValueProperty
            public void setIntValue(AnimatedScreenElement animatedScreenElement, int i) {
                if (animatedScreenElement instanceof TextScreenElement) {
                    ((TextScreenElement) animatedScreenElement).mTextColorProperty.setValue(i);
                }
            }

            @Override // com.miui.maml.folme.IAnimatedProperty
            public void setVelocityValue(AnimatedScreenElement animatedScreenElement, float f) {
                if (animatedScreenElement instanceof TextScreenElement) {
                    ((TextScreenElement) animatedScreenElement).mTextColorProperty.setVelocity(f);
                }
            }
        };
        TEXT_COLOR = animatedColorProperty;
        AnimatedProperty animatedProperty = new AnimatedProperty(PROPERTY_NAME_TEXT_SIZE) { // from class: com.miui.maml.elements.TextScreenElement.2
            @Override // miuix.animation.property.FloatProperty
            public float getValue(AnimatedScreenElement animatedScreenElement) {
                if (animatedScreenElement instanceof TextScreenElement) {
                    return (float) ((TextScreenElement) animatedScreenElement).mTextSizeProperty.getValue();
                }
                return 18.0f;
            }

            @Override // miuix.animation.property.FloatProperty
            public void setValue(AnimatedScreenElement animatedScreenElement, float f) {
                if (animatedScreenElement instanceof TextScreenElement) {
                    ((TextScreenElement) animatedScreenElement).mTextSizeProperty.setValue(f);
                }
            }

            @Override // com.miui.maml.folme.IAnimatedProperty
            public void setVelocityValue(AnimatedScreenElement animatedScreenElement, float f) {
                if (animatedScreenElement instanceof TextScreenElement) {
                    ((TextScreenElement) animatedScreenElement).mTextSizeProperty.setVelocity(f);
                }
            }
        };
        TEXT_SIZE = animatedProperty;
        AnimatedProperty.AnimatedColorProperty animatedColorProperty2 = new AnimatedProperty.AnimatedColorProperty(PROPERTY_NAME_TEXT_SHADOW_COLOR) { // from class: com.miui.maml.elements.TextScreenElement.3
            @Override // miuix.animation.property.ColorProperty, miuix.animation.property.IIntValueProperty
            public int getIntValue(AnimatedScreenElement animatedScreenElement) {
                if (animatedScreenElement instanceof TextScreenElement) {
                    return (int) ((TextScreenElement) animatedScreenElement).mTextShadowColorProperty.getValue();
                }
                return -16777216;
            }

            @Override // miuix.animation.property.ColorProperty, miuix.animation.property.IIntValueProperty
            public void setIntValue(AnimatedScreenElement animatedScreenElement, int i) {
                if (animatedScreenElement instanceof TextScreenElement) {
                    ((TextScreenElement) animatedScreenElement).mTextShadowColorProperty.setValue(i);
                }
            }

            @Override // com.miui.maml.folme.IAnimatedProperty
            public void setVelocityValue(AnimatedScreenElement animatedScreenElement, float f) {
                if (animatedScreenElement instanceof TextScreenElement) {
                    ((TextScreenElement) animatedScreenElement).mTextShadowColorProperty.setVelocity(f);
                }
            }
        };
        TEXT_SHADOW_COLOR = animatedColorProperty2;
        AnimatedProperty.sPropertyNameMap.put(PROPERTY_NAME_TEXT_COLOR, animatedColorProperty);
        ConcurrentHashMap<Integer, FloatProperty> concurrentHashMap = AnimatedTarget.sPropertyMap;
        Integer numValueOf = Integer.valueOf(AnimatedPropertyType.TEXT_COLOR);
        concurrentHashMap.put(numValueOf, animatedColorProperty);
        AnimatedTarget.sPropertyTypeMap.put(animatedColorProperty, numValueOf);
        AnimatedProperty.sPropertyNameMap.put(PROPERTY_NAME_TEXT_SIZE, animatedProperty);
        ConcurrentHashMap<Integer, FloatProperty> concurrentHashMap2 = AnimatedTarget.sPropertyMap;
        Integer numValueOf2 = Integer.valueOf(AnimatedPropertyType.TEXT_SIZE);
        concurrentHashMap2.put(numValueOf2, animatedProperty);
        AnimatedTarget.sPropertyTypeMap.put(animatedProperty, numValueOf2);
        AnimatedProperty.sPropertyNameMap.put(PROPERTY_NAME_TEXT_SHADOW_COLOR, animatedColorProperty2);
        ConcurrentHashMap<Integer, FloatProperty> concurrentHashMap3 = AnimatedTarget.sPropertyMap;
        Integer numValueOf3 = Integer.valueOf(AnimatedPropertyType.TEXT_SHADOW_COLOR);
        concurrentHashMap3.put(numValueOf3, animatedColorProperty2);
        AnimatedTarget.sPropertyTypeMap.put(animatedColorProperty2, numValueOf3);
    }

    public TextScreenElement(Element element, ScreenElementRoot screenElementRoot) {
        super(element, screenElementRoot);
        this.mPaint = new TextPaint();
        this.mMarqueePos = Float.MAX_VALUE;
        this.mTextSize = scale(18.0d);
        this.mTextFontName = null;
        load(element);
    }

    private Layout.Alignment getAlignment() {
        Layout.Alignment alignment = Layout.Alignment.ALIGN_NORMAL;
        int i = AnonymousClass4.$SwitchMap$com$miui$maml$elements$ScreenElement$Align[this.mAlign.ordinal()];
        if (i == 1) {
            try {
                return (Layout.Alignment) ReflectionHelper.getFieldValue(alignment.getClass(), alignment, "ALIGN_LEFT");
            } catch (Exception e) {
                MamlLog.e(LOG_TAG, "Invoke | getAlignment_ALIGN_LEFT occur EXCEPTION: " + e.getMessage());
            }
        } else {
            if (i == 2) {
                return Layout.Alignment.ALIGN_CENTER;
            }
            if (i == 3) {
                try {
                    return (Layout.Alignment) ReflectionHelper.getFieldValue(alignment.getClass(), alignment, "ALIGN_RIGHT");
                } catch (Exception e2) {
                    MamlLog.e(LOG_TAG, "Invoke | getAlignment_ALIGN_RIGHT occur EXCEPTION: " + e2.getMessage());
                }
            }
        }
        return alignment;
    }

    private int getLineClamp() {
        Expression expression = this.mLineClampExp;
        return expression != null ? (int) expression.evaluate() : this.mLineClamp;
    }

    private float getSpacingAdd() {
        Expression expression = this.mSpacingAddExp;
        return expression != null ? (float) expression.evaluate() : this.mSpacingAdd;
    }

    private float getSpacingMult() {
        Expression expression = this.mSpacingMultExp;
        return expression != null ? (float) expression.evaluate() : this.mSpacingMult;
    }

    private void load(Element element) {
        Typeface typefaceCreateFromAsset;
        if (element == null) {
            return;
        }
        Variables variables = getVariables();
        if (this.mHasName) {
            this.mTextWidthVar = new IndexedVariable(this.mName + "." + TEXT_WIDTH, variables, true);
            this.mTextHeightVar = new IndexedVariable(this.mName + "." + TEXT_HEIGHT, variables, true);
        }
        this.mFormatter = TextFormatter.fromElement(variables, element, this.mStyle);
        this.mColorParser = ColorParser.fromElement(variables, element, this.mStyle);
        this.mMarqueeSpeed = getAttrAsInt(element, "marqueeSpeed", 0);
        this.mSpacingMult = getAttrAsFloat(element, "spacingMult", 1.0f);
        this.mSpacingAdd = getAttrAsFloat(element, "spacingAdd", 0.0f);
        this.mSpacingMultExp = Expression.build(variables, getAttr(element, "spacingMultExp"));
        this.mSpacingAddExp = Expression.build(variables, getAttr(element, "spacingAddExp"));
        this.mMarqueeGap = getAttrAsInt(element, "marqueeGap", 2);
        this.mMultiLine = Boolean.parseBoolean(getAttr(element, "multiLine"));
        this.mLineClamp = getAttrAsInt(element, "lineClamp", 1);
        this.mLineClampExp = Expression.build(variables, getAttr(element, "lineClamp"));
        this.mFontScaleEnabled = Boolean.parseBoolean(getAttr(element, "enableFontScale"));
        this.mEllipsis = Boolean.parseBoolean(getAttr(element, "ellipsis"));
        Expression expressionBuild = Expression.build(variables, getAttr(element, "size"));
        String attr = getAttr(element, "fontFamily");
        String attr2 = getAttr(element, "fontPath");
        this.mFontPathWeight = getAttrAsInt(element, "fontPathWeight", 0);
        if (!TextUtils.isEmpty(attr)) {
            this.mPaint.setTypeface(Typeface.create(attr, parseFontStyle(getAttr(element, "fontStyle"))));
        } else if (TextUtils.isEmpty(attr2)) {
            this.mPaint.setFakeBoldText(Boolean.parseBoolean(getAttr(element, "bold")));
            this.mPaint.setTypeface(HideSdkDependencyUtils.TypefaceUtils_replaceTypeface(getContext().mContext, this.mPaint.getTypeface()));
        } else {
            try {
                typefaceCreateFromAsset = Typeface.createFromAsset(getContext().mContext.getAssets(), attr2);
            } catch (Exception e) {
                MamlLog.e(LOG_TAG, "create typeface from asset fail :" + e);
                typefaceCreateFromAsset = null;
            }
            if (typefaceCreateFromAsset == null) {
                if (attr2.startsWith("@")) {
                    this.mTextFontVar = new IndexedVariable(attr2.substring(1), variables, false);
                } else {
                    updateTypeface(attr2);
                }
            }
            if (typefaceCreateFromAsset != null) {
                this.mPaint.setTypeface(typefaceCreateFromAsset);
            }
        }
        this.mPaint.setColor(getColor());
        this.mPaint.setTextSize(scale(18.0d));
        this.mPaint.setAntiAlias(true);
        this.mShadowRadius = getAttrAsFloat(element, "shadowRadius", 0.0f);
        this.mShadowDx = getAttrAsFloat(element, "shadowDx", 0.0f);
        this.mShadowDy = getAttrAsFloat(element, "shadowDy", 0.0f);
        this.mShadowColorParser = ColorParser.fromElement(variables, element, "shadowColor", this.mStyle);
        this.mPaint.setShadowLayer(this.mShadowRadius, this.mShadowDx, this.mShadowDy, getShadowColor());
        this.mTextSizeProperty = new PropertyWrapper(this.mName + ".textColor", getVariables(), expressionBuild, isInFolmeMode(), 18.0d);
        this.mTextColorProperty = new PropertyWrapper(this.mName + ".textSize", getVariables(), null, isInFolmeMode(), this.mColorParser.getColor());
        this.mTextShadowColorProperty = new PropertyWrapper(this.mName + ".textShadowColor", getVariables(), null, isInFolmeMode(), this.mShadowColorParser.getColor());
    }

    private static int parseFontStyle(String str) {
        if (!TextUtils.isEmpty(str) && !"normal".equals(str)) {
            if ("bold".equals(str)) {
                return 1;
            }
            if ("italic".equals(str)) {
                return 2;
            }
            if ("bold_italic".equals(str)) {
                return 3;
            }
        }
        return 0;
    }

    private void updateTextFontIfNeed() {
        String string;
        IndexedVariable indexedVariable = this.mTextFontVar;
        if (indexedVariable == null || (string = indexedVariable.getString()) == null || string.length() <= 0 || string.equals(this.mTextFontName)) {
            return;
        }
        updateTypeface(string);
    }

    private void updateTextSize() {
        float fScale = scale(this.mTextSizeProperty.getValue());
        this.mTextSize = fScale;
        if (this.mFontScaleEnabled) {
            this.mTextSize = fScale * this.mRoot.getFontScale();
        }
        this.mPaint.setTextSize(this.mTextSize);
    }

    private void updateTextWidth() {
        this.mTextWidth = 0.0f;
        if (!TextUtils.isEmpty(this.mText)) {
            if (this.mMultiLine) {
                for (String str : this.mText.split(CRLF)) {
                    float fMeasureText = this.mPaint.measureText(str);
                    if (fMeasureText > this.mTextWidth) {
                        this.mTextWidth = fMeasureText;
                    }
                }
            } else {
                this.mTextWidth = this.mPaint.measureText(this.mText);
            }
        }
        if (this.mHasName) {
            this.mTextWidthVar.set(descale(this.mTextWidth));
        }
    }

    private void updateTypeface(String str) {
        this.mTextFontName = str;
        File extraFile = getContext().mResourceManager.getExtraFile(str);
        if (extraFile != null && extraFile.exists()) {
            try {
                this.mPaint.setTypeface(Typeface.createFromFile(extraFile));
                return;
            } catch (Exception e) {
                MamlLog.e(LOG_TAG, "int. create typeface from PATH failed. " + e);
                return;
            }
        }
        try {
            Typeface.Builder builder = new Typeface.Builder(str);
            if (this.mFontPathWeight != 0) {
                builder.setFontVariationSettings("'wght' " + this.mFontPathWeight);
            }
            this.mPaint.setTypeface(builder.build());
        } catch (Exception e2) {
            MamlLog.e(LOG_TAG, "int. create typeface from PATH failed. " + e2);
        }
    }

    @Override // com.miui.maml.elements.ScreenElement
    public void doRender(Canvas canvas) {
        float fMin;
        float fMax;
        float fMin2;
        float fMax2;
        Canvas canvas2;
        if (TextUtils.isEmpty(this.mText)) {
            return;
        }
        this.mPaint.setColor(getColor());
        TextPaint textPaint = this.mPaint;
        textPaint.setAlpha(Utils.mixAlpha(textPaint.getAlpha(), getAlpha()));
        this.mPaint.setShadowLayer(this.mShadowRadius, this.mShadowDx, this.mShadowDy, getShadowColor());
        float width = getWidth();
        boolean z = width >= 0.0f;
        if (width < 0.0f || width > this.mTextWidth) {
            width = this.mTextWidth;
        }
        float height = getHeight();
        float textSize = this.mPaint.getTextSize();
        if (height < 0.0f) {
            height = this.mTextHeight;
        }
        float left = getLeft(0.0f, width);
        float top = getTop(0.0f, height);
        canvas.save();
        float f = this.mShadowRadius;
        if (f != 0.0f) {
            fMin = Math.min(0.0f, this.mShadowDx - f);
            fMax = Math.max(0.0f, this.mShadowDx + this.mShadowRadius);
            fMin2 = Math.min(0.0f, this.mShadowDy - this.mShadowRadius);
            fMax2 = Math.max(0.0f, this.mShadowDy + this.mShadowRadius);
        } else {
            fMin = 0.0f;
            fMax = 0.0f;
            fMin2 = 0.0f;
            fMax2 = 0.0f;
        }
        canvas.translate(left, top);
        if (z) {
            fMin = 0.0f;
        }
        if (z) {
            fMax = 0.0f;
        }
        canvas.clipRect(fMin, fMin2, fMax + width, height + fMax2);
        StaticLayout staticLayout = this.mTextLayout;
        if (staticLayout == null) {
            canvas2 = canvas;
        } else if (staticLayout.getLineCount() == 1 && this.mShouldMarquee) {
            int lineStart = this.mTextLayout.getLineStart(0);
            int lineEnd = this.mTextLayout.getLineEnd(0);
            int lineTop = this.mTextLayout.getLineTop(0);
            float lineLeft = this.mTextLayout.getLineLeft(0);
            float f2 = textSize + lineTop;
            canvas2 = canvas;
            canvas2.drawText(this.mText, lineStart, lineEnd, lineLeft + this.mMarqueePos, f2, (Paint) this.mPaint);
            float f3 = this.mMarqueePos;
            if (f3 != 0.0f) {
                float f4 = f3 + this.mTextWidth + (this.mTextSize * this.mMarqueeGap);
                if (f4 < width) {
                    canvas2.drawText(this.mText, lineLeft + f4, f2, this.mPaint);
                }
            }
        } else {
            canvas2 = canvas;
            this.mTextLayout.draw(canvas2);
        }
        canvas2.restore();
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    @RequiresApi
    public void doTick(long j) {
        synchronized (mLock) {
            try {
                super.doTick(j);
                if (isVisible()) {
                    this.mShouldMarquee = false;
                    String str = this.mText;
                    String text = getText();
                    this.mText = text;
                    if (TextUtils.isEmpty(text)) {
                        this.mTextLayout = null;
                        updateTextWidth();
                        return;
                    }
                    float f = this.mTextSize;
                    updateTextSize();
                    updateTextFontIfNeed();
                    boolean z = (TextUtils.equals(str, this.mText) && f == this.mTextSize) ? false : true;
                    if (z) {
                        updateTextWidth();
                    }
                    float width = getWidth();
                    boolean z2 = this.mMultiLine;
                    boolean z3 = !z2 && this.mMarqueeSpeed > 0 && this.mTextWidth > width && !this.mEllipsis;
                    float f2 = ((z2 || this.mEllipsis) && width <= this.mTextWidth) ? width : this.mTextWidth;
                    float spacingMult = getSpacingMult();
                    float spacingAdd = getSpacingAdd();
                    if (this.mTextLayout == null || z || this.mLayoutWidth != f2 || this.mSpacingMult != spacingMult || this.mSpacingAdd != spacingAdd) {
                        this.mLayoutWidth = f2;
                        this.mSpacingMult = spacingMult;
                        this.mSpacingAdd = spacingAdd;
                        String str2 = this.mText;
                        StaticLayout.Builder includePad = StaticLayout.Builder.obtain(str2, 0, str2.length(), this.mPaint, (int) Math.ceil(this.mLayoutWidth)).setAlignment(getAlignment()).setLineSpacing(this.mSpacingAdd, this.mSpacingMult).setIncludePad(false);
                        if (this.mEllipsis) {
                            includePad.setMaxLines(getLineClamp());
                            includePad.setEllipsize(TextUtils.TruncateAt.END);
                            includePad.setEllipsizedWidth((int) width);
                        }
                        includePad.setUseLineSpacingFromFallbacks(true);
                        HideSdkDependencyUtils.StaticLayoutBuilder_setUseBoundsForWidth(includePad, false);
                        StaticLayout staticLayoutBuild = includePad.build();
                        this.mTextLayout = staticLayoutBuild;
                        float lineTop = staticLayoutBuild.getLineTop(staticLayoutBuild.getLineCount());
                        this.mTextHeight = lineTop;
                        if (this.mHasName) {
                            this.mTextHeightVar.set(descale(lineTop));
                        }
                        this.mMarqueePos = Float.MAX_VALUE;
                    }
                    if (z3) {
                        float f3 = this.mMarqueePos;
                        if (f3 == Float.MAX_VALUE) {
                            this.mMarqueePos = 50.0f;
                        } else {
                            if (f3 < (-this.mLayoutWidth)) {
                                this.mMarqueePos = 50.0f;
                            }
                            float f4 = this.mMarqueePos - ((((long) this.mMarqueeSpeed) * (j - this.mPreviousTime)) / 1000.0f);
                            this.mMarqueePos = f4;
                            float f5 = this.mTextWidth;
                            if (f4 < (-f5)) {
                                this.mMarqueePos = f4 + f5 + (this.mTextSize * this.mMarqueeGap);
                            }
                        }
                        this.mPreviousTime = j;
                        this.mShouldMarquee = true;
                    }
                    requestFramerate(this.mShouldMarquee ? 45.0f : 0.0f);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void finish() {
        super.finish();
        this.mText = null;
        this.mSetText = null;
        this.mMarqueePos = Float.MAX_VALUE;
    }

    public int getColor() {
        return isInFolmeMode() ? (int) this.mTextColorProperty.getValue() : this.mColorParser.getColor();
    }

    public String getFormat() {
        return this.mFormatter.getFormat();
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement
    public float getHeight() {
        float height = super.getHeight();
        return height <= 0.0f ? this.mTextHeight : height;
    }

    public int getShadowColor() {
        return isInFolmeMode() ? (int) this.mTextShadowColorProperty.getValue() : this.mShadowColorParser.getColor();
    }

    public String getText() {
        String str = this.mSetText;
        if (str != null) {
            return str;
        }
        String text = this.mFormatter.getText();
        if (text != null) {
            text = text.replace(RAW_CRLF, CRLF);
            if (!this.mMultiLine) {
                return text.replace(CRLF, " ");
            }
        }
        return text;
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement
    public float getWidth() {
        float width = super.getWidth();
        return width <= 0.0f ? this.mTextWidth : width;
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void init() {
        super.init();
        updateTextFontIfNeed();
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement
    public void initProperties() {
        super.initProperties();
        this.mTextSizeProperty.init();
        this.mTextColorProperty.init();
        this.mTextShadowColorProperty.init();
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void onVisibilityChange(boolean z) {
        super.onVisibilityChange(z);
        requestFramerate((this.mShouldMarquee && z) ? 45.0f : 0.0f);
    }

    @Override // com.miui.maml.elements.AnimatedScreenElement, com.miui.maml.elements.ScreenElement
    public void setColorFilter(ColorFilter colorFilter) {
        super.setColorFilter(colorFilter);
        TextPaint textPaint = this.mPaint;
        if (textPaint != null) {
            textPaint.setColorFilter(colorFilter);
        }
    }

    public void setParams(Object... objArr) {
        this.mFormatter.setParams(objArr);
    }

    public void setText(String str) {
        this.mSetText = str;
    }
}
