package com.miui.lockscreeninfo;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.miui.aod.common.StyleInfo;
import com.miui.lockscreeninfo.compat.ViewCompat;
import com.xiaomi.onetrack.util.ac;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import miuix.core.util.SystemProperties;

/* JADX INFO: loaded from: classes2.dex */
public class VerticalTextViewUtils {
    public static final boolean BACKGROUND_BLUR_KEYGUARD_DISABLED;
    public static final boolean BACKGROUND_BLUR_STATUS_DEFAULT;
    public static final boolean BACKGROUND_BLUR_SUPPORTED;
    public static final int BACKGROUND_BLUR_VERSION;
    public static final boolean FOLD_DEVICE;
    public static final int OS2_ADVANCED_VISUAL_VERSION;
    public static final String TAG = "com.miui.lockscreeninfo.VerticalTextViewUtils";
    public static Method setTextViewUseBoundsForWidthMethod;
    public static List mPhoneList = Arrays.asList("corot");
    public static final boolean PAD_DEVICE = "tablet".equals(SystemProperties.get("ro.build.characteristics", StyleInfo.DEFAULT_PARAMETER));

    static {
        FOLD_DEVICE = SystemProperties.getInt("persist.sys.muiltdisplay_type", 0) == 2;
        BACKGROUND_BLUR_STATUS_DEFAULT = SystemProperties.getBoolean("persist.sys.background_blur_status_default", false);
        BACKGROUND_BLUR_SUPPORTED = SystemProperties.getBoolean("persist.sys.background_blur_supported", false);
        BACKGROUND_BLUR_KEYGUARD_DISABLED = SystemProperties.getBoolean("persist.sys.background_blur_keyguard_disabled", false);
        BACKGROUND_BLUR_VERSION = SystemProperties.getInt("persist.sys.background_blur_version", 0);
        OS2_ADVANCED_VISUAL_VERSION = SystemProperties.getInt("persist.sys.advanced_visual_release", 0);
        try {
            setTextViewUseBoundsForWidthMethod = TextView.class.getMethod("setUseBoundsForWidth", Boolean.TYPE);
        } catch (Throwable th) {
            Log.w(TAG, "get setUseBoundsForWidth method failed.", th);
            setTextViewUseBoundsForWidthMethod = null;
        }
    }

    public static float calculateScale(Context context) {
        return context.getResources().getConfiguration().smallestScreenWidthDp / getDesignDp(context);
    }

    public static void clearContainerPassBlur(View view) {
        if (view == null) {
            Log.d(TAG, "clearContainerMiBackgroundBlur view is null");
            return;
        }
        try {
            ViewCompat.setMiBackgroundBlurMode(view, 0);
            ViewCompat.setMiBackgroundBlurRadius(view, 0);
            boolean passWindowBlurEnabled = ViewCompat.setPassWindowBlurEnabled(view, false);
            Log.d(TAG, "clearContainerPassBlur result :" + passWindowBlurEnabled + ", view: " + view);
        } catch (Exception e) {
            Log.e(TAG, "clearContainerMiBackgroundBlur error , view :" + view);
            e.printStackTrace();
        }
    }

    public static void clearDiffEffectContainer(View view) {
        String str = TAG;
        Log.d(str, "clearDiffEffectContainer " + view);
        if (!BACKGROUND_BLUR_SUPPORTED || !supportAdvanceVisualEffect()) {
            Log.d(str, "not support blur");
            return;
        }
        if (view == null) {
            Log.d(str, "setDiffEffectMethod view is null");
            return;
        }
        try {
            ViewCompat.setMiBackgroundBlurMode(view, 0);
            ViewCompat.setPassWindowBlurEnabled(view, false);
        } catch (Exception e) {
            Log.e(TAG, "setDiffEffectMethod error , view :" + view);
            e.printStackTrace();
        }
    }

    public static void clearDiffEffectMethod(View view) {
        String str = TAG;
        Log.d(str, "clearDiffEffectMethod " + view);
        if (BACKGROUND_BLUR_SUPPORTED && supportAdvanceVisualEffect()) {
            if (view == null) {
                Log.d(str, "clearDiffEffectMethod view is null");
                return;
            }
            try {
                ViewCompat.setMiViewBlurMode(view, 0);
                ViewCompat.clearMiBackgroundBlendColors(view);
            } catch (Exception e) {
                Log.e(TAG, "clearDiffEffectMethod error , view :" + view);
                e.printStackTrace();
            }
        }
    }

    public static void clearGlowEffectContainer(View view) {
        if (view == null || !supportAdvanceVisualEffect()) {
            return;
        }
        Log.d(TAG, "clearGlowEffectContainer " + view);
        ViewCompat.setPassWindowBlurEnabled(view, false);
    }

    public static void clearGlowEffectMethod(View view) {
        if (view == null || !supportAdvanceVisualEffect()) {
            return;
        }
        Log.d(TAG, "clearGlowEffectMethod " + view);
        Class cls = Integer.TYPE;
        Class cls2 = Float.TYPE;
        ReflectUtils.invokeObject(View.class, view, "setGlowEffect", Void.TYPE, new Class[]{cls, cls, cls, cls2, cls2, cls2, cls, cls}, 0, 0, 0, Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(0.0f), 0, 0);
    }

    public static void clearMemberBlendColor(View view) {
        if (view == null) {
            Log.d(TAG, "clearMemberBlendColor view is null");
            return;
        }
        try {
            ViewCompat.setMiViewBlurMode(view, 0);
            ViewCompat.clearMiBackgroundBlendColors(view);
            Log.d(TAG, "clearMemberBlendColor view :" + view);
        } catch (Exception e) {
            Log.e(TAG, "clearMemberBlendColor error , view :" + view);
            e.printStackTrace();
        }
    }

    public static boolean deviceBlurEnabled() {
        if (is2K() && BACKGROUND_BLUR_KEYGUARD_DISABLED) {
            return false;
        }
        return !mPhoneList.contains(Build.DEVICE) || supportBlurMode1000();
    }

    public static float getDesignDp(Context context) {
        if (isJ18() && !inLargeScreen(context)) {
            return 320.0f;
        }
        if (isJ18() && inLargeScreen(context)) {
            return 676.0f;
        }
        if (FOLD_DEVICE && inLargeScreen(context)) {
            return 696.0f;
        }
        return PAD_DEVICE ? 711.0f : 392.0f;
    }

    public static boolean inLargeScreen(Context context) {
        return context.getResources().getConfiguration().smallestScreenWidthDp >= 600;
    }

    public static boolean is2K() {
        String str = SystemProperties.get("persist.sys.miui_resolution");
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        String[] strArrSplit = str.split(ac.b);
        return strArrSplit[0].equals("1440") && strArrSplit[1].equals("3200");
    }

    public static boolean isCJKCharacter(int i) {
        Character.UnicodeBlock unicodeBlockOf = Character.UnicodeBlock.of(i);
        return unicodeBlockOf == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || unicodeBlockOf == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || unicodeBlockOf == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || unicodeBlockOf == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS || unicodeBlockOf == Character.UnicodeBlock.HANGUL_SYLLABLES || unicodeBlockOf == Character.UnicodeBlock.HANGUL_JAMO || unicodeBlockOf == Character.UnicodeBlock.HANGUL_COMPATIBILITY_JAMO || unicodeBlockOf == Character.UnicodeBlock.HIRAGANA || unicodeBlockOf == Character.UnicodeBlock.KATAKANA || unicodeBlockOf == Character.UnicodeBlock.KATAKANA_PHONETIC_EXTENSIONS || unicodeBlockOf == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION || unicodeBlockOf == Character.UnicodeBlock.GENERAL_PUNCTUATION;
    }

    public static boolean isChinese(char c) {
        Character.UnicodeBlock unicodeBlockOf = Character.UnicodeBlock.of(c);
        return unicodeBlockOf == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS || unicodeBlockOf == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS || unicodeBlockOf == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A || unicodeBlockOf == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B;
    }

    public static boolean isControlCharactor(int i) {
        return i < 32 || i == 127;
    }

    public static boolean isEmojiCharacter(int i) {
        return Character.toString((char) i).matches("[\\uD83C-\\uDBFF\\uDC00-\\uDFFF]+");
    }

    public static boolean isEnglishChar(char c) {
        return c >= ' ' && c <= '~';
    }

    public static boolean isGlobalRom() {
        return miuix.os.Build.IS_INTERNATIONAL_BUILD;
    }

    public static boolean isJ18() {
        return TextUtils.equals(Build.DEVICE, "cetus");
    }

    public static boolean isL18Externalscreen(Context context) {
        String str = Build.DEVICE;
        return (TextUtils.equals(str, "zizhan") || TextUtils.equals(str, "babylon")) && !inLargeScreen(context);
    }

    public static boolean isRedmiBrand() {
        return "Redmi".equals(Build.BRAND);
    }

    public static boolean isSystemUI(Context context) {
        return "com.android.systemui".equals(context.getPackageName());
    }

    public static boolean isTibetanChar(char c) {
        return c >= 3840 && c <= 4095;
    }

    public static boolean isUyghurChar(char c) {
        return c >= 1536 && c <= 1791;
    }

    public static boolean setContainerPassBlur(View view, int i, boolean z) {
        boolean z2;
        if (view == null) {
            Log.d(TAG, "setPassBlur view is null");
            return false;
        }
        if (!z) {
            try {
                z2 = isSystemUI(view.getContext());
            } catch (Exception e) {
                Log.e(TAG, "setContainerPassBlur error , view :" + view);
                e.printStackTrace();
                return false;
            }
        }
        boolean passWindowBlurEnabled = ViewCompat.setPassWindowBlurEnabled(view, z2);
        ViewCompat.setMiBackgroundBlurMode(view, 1);
        ViewCompat.setMiBackgroundBlurRadius(view, i);
        Log.i(TAG, "setContainerPassBlur result :" + passWindowBlurEnabled + ",view : " + view + ", multiWindowBlur=" + z);
        return passWindowBlurEnabled;
    }

    public static void setDiffEffectContainer(View view, boolean z) {
        boolean z2;
        String str = TAG;
        Log.d(str, "setDiffEffectContainer " + view);
        if (!BACKGROUND_BLUR_SUPPORTED || !supportAdvanceVisualEffect()) {
            Log.d(str, "not support blur");
            return;
        }
        if (view == null) {
            Log.d(str, "setDiffEffectMethod view is null");
            return;
        }
        if (!z) {
            try {
                z2 = isSystemUI(view.getContext());
            } catch (Exception e) {
                Log.e(TAG, "setDiffEffectMethod error , view :" + view + ", multiWindowBlur=" + z);
                e.printStackTrace();
                return;
            }
        }
        ViewCompat.setPassWindowBlurEnabled(view, z2);
        ViewCompat.setMiBackgroundBlurMode(view, 2);
        if (isSystemUI(view.getContext())) {
            ViewCompat.disableMiBackgroundContainBelow(view, true);
        }
    }

    public static void setGlowEffectContainer(View view) {
        if (view == null || !supportAdvanceVisualEffect()) {
            return;
        }
        Log.d(TAG, "setGlowEffectContainer " + view);
        ViewCompat.setPassWindowBlurEnabled(view, isSystemUI(view.getContext()));
        if (isSystemUI(view.getContext())) {
            ViewCompat.disableMiBackgroundContainBelow(view, true);
        }
    }

    public static void setMemberBlendColor(View view, boolean z, int i, int i2, int i3, int i4) {
        if (view == null || !BACKGROUND_BLUR_SUPPORTED) {
            Log.d(TAG, "setMemberBlendColor view is null");
            return;
        }
        try {
            ViewCompat.setMiViewBlurMode(view, 3);
            int iArgb = Color.argb(i2, Color.red(i), Color.green(i), Color.blue(i));
            int iArgb2 = Color.argb(i4, 0, 0, 0);
            ArrayList arrayList = new ArrayList();
            arrayList.add(new Point(iArgb, 101));
            if (z) {
                arrayList.add(new Point(iArgb2, com.miui.maml.R.styleable.AppCompatTheme_textAppearanceListItemSmall));
            } else {
                arrayList.add(new Point(iArgb2, 103));
            }
            if (supportBlurMode1000()) {
                arrayList.add(new Point(i3, 1000));
            }
            ViewCompat.setMiBackgroundBlendColors(view, arrayList);
            ViewCompat.disableMiBackgroundContainBelow(view, isSystemUI(view.getContext()));
            Log.i(TAG, "setMemberBlendColor: view:" + view + ",colorDark:" + z + ",color:" + Integer.toHexString(iArgb) + ",labColor:" + Integer.toHexString(iArgb2));
        } catch (Exception e) {
            Log.e(TAG, "setMemberBlendColor error , view :" + view);
            e.printStackTrace();
        }
    }

    public static void setMemberDiffEffectColor(View view, int i, int i2) {
        String str = TAG;
        Log.d(str, "setMemberDiffEffectColor " + view);
        if (!BACKGROUND_BLUR_SUPPORTED || !supportAdvanceVisualEffect()) {
            Log.d(str, "not support blur");
            return;
        }
        if (view == null) {
            Log.d(str, "setDiffEffectMethod view is null");
            return;
        }
        try {
            ViewCompat.setMiViewBlurMode(view, 3);
            ArrayList arrayList = new ArrayList();
            arrayList.add(new Point(i, 20));
            arrayList.add(new Point(i2, 3));
            ViewCompat.setMiBackgroundBlendColors(view, arrayList);
            Log.d(str, "setMemberDiffEffectColor animColor = " + i2 + ", diffColor = " + i);
        } catch (Exception e) {
            Log.e(TAG, "setDiffEffectMethod error , view :" + view);
            e.printStackTrace();
        }
    }

    public static void setUseBoundsForWidthCompat(TextView textView, boolean z) {
        try {
            Method method = setTextViewUseBoundsForWidthMethod;
            if (method != null) {
                method.invoke(textView, Boolean.valueOf(z));
            }
        } catch (Throwable th) {
            Log.w(TAG, "calling setUseBoundsForWidth failed.", th);
        }
    }

    public static boolean supportAdvanceVisualEffect() {
        return OS2_ADVANCED_VISUAL_VERSION >= 3 && BACKGROUND_BLUR_STATUS_DEFAULT;
    }

    public static boolean supportBlurMode1000() {
        return BACKGROUND_BLUR_VERSION >= 2 || OS2_ADVANCED_VISUAL_VERSION >= 2;
    }
}
