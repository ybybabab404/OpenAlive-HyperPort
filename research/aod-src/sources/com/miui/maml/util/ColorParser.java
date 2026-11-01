package com.miui.maml.util;

import android.graphics.Color;
import com.miui.maml.StylesManager;
import com.miui.maml.data.Expression;
import com.miui.maml.data.IndexedVariable;
import com.miui.maml.data.Variables;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes2.dex */
public class ColorParser {
    private static final int DEFAULT_COLOR = -16777216;
    private static final String LOG_TAG = "ColorParser";
    private int mColor;
    private String mColorExpression;
    private String mCurColorString;
    private IndexedVariable mIndexedColorVar;
    private Expression[] mRGBExpression;
    private ExpressionType mType;

    /* JADX INFO: renamed from: com.miui.maml.util.ColorParser$1, reason: invalid class name */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$miui$maml$util$ColorParser$ExpressionType;

        static {
            int[] iArr = new int[ExpressionType.values().length];
            $SwitchMap$com$miui$maml$util$ColorParser$ExpressionType = iArr;
            try {
                iArr[ExpressionType.CONST.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$miui$maml$util$ColorParser$ExpressionType[ExpressionType.VARIABLE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$miui$maml$util$ColorParser$ExpressionType[ExpressionType.ARGB.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    public enum ExpressionType {
        CONST,
        VARIABLE,
        ARGB,
        INVALID
    }

    public ColorParser(Variables variables, String str) {
        this.mColor = DEFAULT_COLOR;
        String strTrim = str.trim();
        this.mColorExpression = strTrim;
        if (strTrim.startsWith("#")) {
            this.mType = ExpressionType.CONST;
            try {
                this.mColor = Color.parseColor(this.mColorExpression);
                return;
            } catch (IllegalArgumentException unused) {
                this.mColor = DEFAULT_COLOR;
                return;
            }
        }
        if (this.mColorExpression.startsWith("@")) {
            this.mType = ExpressionType.VARIABLE;
            this.mIndexedColorVar = new IndexedVariable(this.mColorExpression.substring(1), variables, false);
            return;
        }
        if (!this.mColorExpression.startsWith("argb(") || !this.mColorExpression.endsWith(")")) {
            this.mType = ExpressionType.INVALID;
            return;
        }
        this.mType = ExpressionType.ARGB;
        String str2 = this.mColorExpression;
        Expression[] expressionArrBuildMultiple = Expression.buildMultiple(variables, str2.substring(5, str2.length() - 1));
        this.mRGBExpression = expressionArrBuildMultiple;
        if (expressionArrBuildMultiple == null || expressionArrBuildMultiple.length == 4) {
            return;
        }
        MamlLog.e(LOG_TAG, "bad expression format");
        throw new IllegalArgumentException("bad expression format.");
    }

    public static ColorParser fromElement(Variables variables, Element element) {
        return new ColorParser(variables, element.getAttribute("color"));
    }

    public static ColorParser fromElement(Variables variables, Element element, StylesManager.Style style) {
        return new ColorParser(variables, StyleHelper.getAttr(element, "color", style));
    }

    public static ColorParser fromElement(Variables variables, Element element, String str, StylesManager.Style style) {
        return new ColorParser(variables, StyleHelper.getAttr(element, str, style));
    }

    public int getColor() {
        int i = AnonymousClass1.$SwitchMap$com$miui$maml$util$ColorParser$ExpressionType[this.mType.ordinal()];
        if (i != 1) {
            int color = DEFAULT_COLOR;
            if (i == 2) {
                try {
                    String string = this.mIndexedColorVar.getString();
                    if (!Utils.equals(string, this.mCurColorString)) {
                        if (!Utils.isEmpty(string)) {
                            color = Color.parseColor(string);
                        }
                        this.mColor = color;
                        this.mCurColorString = string;
                    }
                } catch (Exception e) {
                    MamlLog.e(LOG_TAG, "parse variable color error.", e);
                }
            } else if (i != 3) {
                this.mColor = DEFAULT_COLOR;
            } else {
                try {
                    this.mColor = Color.argb((int) this.mRGBExpression[0].evaluate(), (int) this.mRGBExpression[1].evaluate(), (int) this.mRGBExpression[2].evaluate(), (int) this.mRGBExpression[3].evaluate());
                } catch (Exception e2) {
                    MamlLog.e(LOG_TAG, "parse argb color error.", e2);
                }
            }
        }
        return this.mColor;
    }
}
