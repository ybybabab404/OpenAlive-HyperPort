package com.miui.clock.tiny;

import android.content.Context;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/* JADX INFO: loaded from: classes.dex */
public class Util {

    @Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.LOCAL_VARIABLE, ElementType.FIELD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Rotation {
    }

    public static boolean isAOD(Context context) {
        return "com.miui.aod".equals(context.getPackageName());
    }

    public static boolean isBubble3DTemplate(String str, int i) {
        return isBubbleTemplate(str) && i >= 0 && i <= 1;
    }

    public static boolean isBubbleSolid3DTemplate(String str, int i) {
        return isBubbleTemplate(str) && i >= 2 && i <= 5;
    }

    public static boolean isBubbleTemplate(String str) {
        return "bubble".equals(str) || "bubble_top".equals(str);
    }

    public static boolean isPetsTemplate(String str) {
        return "pets".equals(str) || "ai_pets".equals(str) || "interactive_pets".equals(str);
    }
}
