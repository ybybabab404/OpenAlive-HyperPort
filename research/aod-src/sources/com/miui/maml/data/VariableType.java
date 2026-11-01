package com.miui.maml.data;

import org.json.JSONArray;
import org.json.JSONObject;

/* JADX WARN: Enum visitor error
jadx.core.utils.exceptions.JadxRuntimeException: Init of enum field 'NUM' uses external variables
	at jadx.core.dex.visitors.EnumVisitor.createEnumFieldByConstructor(EnumVisitor.java:485)
	at jadx.core.dex.visitors.EnumVisitor.processEnumFieldByRegister(EnumVisitor.java:422)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromFilledArray(EnumVisitor.java:351)
	at jadx.core.dex.visitors.EnumVisitor.extractEnumFieldsFromInsn(EnumVisitor.java:284)
	at jadx.core.dex.visitors.EnumVisitor.convertToEnum(EnumVisitor.java:153)
	at jadx.core.dex.visitors.EnumVisitor.visit(EnumVisitor.java:102)
 */
/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX INFO: loaded from: classes2.dex */
public final class VariableType {
    private static final /* synthetic */ VariableType[] $VALUES;
    public static final VariableType BOOLEAN_ARR;
    public static final VariableType BYTE_ARR;
    public static final VariableType CHAR_ARR;
    public static final VariableType DOUBLE_ARR;
    public static final VariableType FLOAT_ARR;
    public static final VariableType INT_ARR;
    public static final VariableType INVALID;
    public static final VariableType JSONA;
    public static final VariableType JSONO;
    public static final VariableType LONG_ARR;
    public static final VariableType NUM;
    public static final VariableType NUM_ARR;
    public static final VariableType OBJ;
    public static final VariableType OBJ_ARR;
    public static final VariableType SHORT_ARR;
    public static final VariableType STR;
    public static final VariableType STR_ARR;
    public final Class<?> mTypeClass;

    static {
        VariableType variableType = new VariableType("INVALID", 0, null);
        INVALID = variableType;
        Class cls = Double.TYPE;
        VariableType variableType2 = new VariableType("NUM", 1, cls);
        NUM = variableType2;
        VariableType variableType3 = new VariableType("STR", 2, String.class);
        STR = variableType3;
        VariableType variableType4 = new VariableType("OBJ", 3, Object.class);
        OBJ = variableType4;
        VariableType variableType5 = new VariableType("JSONO", 4, JSONObject.class);
        JSONO = variableType5;
        VariableType variableType6 = new VariableType("JSONA", 5, JSONArray.class);
        JSONA = variableType6;
        VariableType variableType7 = new VariableType("NUM_ARR", 6, cls);
        NUM_ARR = variableType7;
        VariableType variableType8 = new VariableType("DOUBLE_ARR", 7, cls);
        DOUBLE_ARR = variableType8;
        VariableType variableType9 = new VariableType("FLOAT_ARR", 8, Float.TYPE);
        FLOAT_ARR = variableType9;
        VariableType variableType10 = new VariableType("INT_ARR", 9, Integer.TYPE);
        INT_ARR = variableType10;
        VariableType variableType11 = new VariableType("SHORT_ARR", 10, Short.TYPE);
        SHORT_ARR = variableType11;
        VariableType variableType12 = new VariableType("BYTE_ARR", 11, Byte.TYPE);
        BYTE_ARR = variableType12;
        VariableType variableType13 = new VariableType("LONG_ARR", 12, Long.TYPE);
        LONG_ARR = variableType13;
        VariableType variableType14 = new VariableType("BOOLEAN_ARR", 13, Boolean.TYPE);
        BOOLEAN_ARR = variableType14;
        VariableType variableType15 = new VariableType("CHAR_ARR", 14, Character.TYPE);
        CHAR_ARR = variableType15;
        VariableType variableType16 = new VariableType("STR_ARR", 15, String.class);
        STR_ARR = variableType16;
        VariableType variableType17 = new VariableType("OBJ_ARR", 16, Object.class);
        OBJ_ARR = variableType17;
        $VALUES = new VariableType[]{variableType, variableType2, variableType3, variableType4, variableType5, variableType6, variableType7, variableType8, variableType9, variableType10, variableType11, variableType12, variableType13, variableType14, variableType15, variableType16, variableType17};
    }

    private VariableType(String str, int i, Class cls) {
        super(str, i);
        this.mTypeClass = cls;
    }

    public static VariableType parseType(String str) {
        if ("number".equalsIgnoreCase(str)) {
            return NUM;
        }
        if ("string".equalsIgnoreCase(str)) {
            return STR;
        }
        if ("object".equalsIgnoreCase(str)) {
            return OBJ;
        }
        if ("jsonO".equalsIgnoreCase(str)) {
            return JSONO;
        }
        if ("jsonA".equalsIgnoreCase(str)) {
            return JSONA;
        }
        if ("number[]".equalsIgnoreCase(str)) {
            return NUM_ARR;
        }
        if ("double[]".equalsIgnoreCase(str)) {
            return DOUBLE_ARR;
        }
        if ("float[]".equalsIgnoreCase(str)) {
            return FLOAT_ARR;
        }
        if ("int[]".equalsIgnoreCase(str)) {
            return INT_ARR;
        }
        if ("short[]".equalsIgnoreCase(str)) {
            return SHORT_ARR;
        }
        if ("byte[]".equalsIgnoreCase(str)) {
            return BYTE_ARR;
        }
        if ("long[]".equalsIgnoreCase(str)) {
            return LONG_ARR;
        }
        if ("boolean[]".equalsIgnoreCase(str)) {
            return BOOLEAN_ARR;
        }
        if ("char[]".equalsIgnoreCase(str)) {
            return CHAR_ARR;
        }
        if ("string[]".equalsIgnoreCase(str)) {
            return STR_ARR;
        }
        return "object[]".equalsIgnoreCase(str) ? OBJ_ARR : NUM;
    }

    public static VariableType valueOf(String str) {
        return (VariableType) Enum.valueOf(VariableType.class, str);
    }

    public static VariableType[] values() {
        return (VariableType[]) $VALUES.clone();
    }

    public boolean isArray() {
        return ordinal() >= NUM_ARR.ordinal() && ordinal() <= OBJ_ARR.ordinal();
    }

    public boolean isNumber() {
        return this == NUM;
    }

    public boolean isNumberArray() {
        return ordinal() >= NUM_ARR.ordinal() && ordinal() <= CHAR_ARR.ordinal();
    }

    public boolean isNumberOrStringArray() {
        return ordinal() >= NUM_ARR.ordinal() && ordinal() <= STR_ARR.ordinal();
    }
}
