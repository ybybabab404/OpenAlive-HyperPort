package com.miui.keyguardtemplate;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.text.TextUtils;
import android.util.TypedValue;
import com.miui.aod.common.StyleInfo;
import miuix.core.util.SystemProperties;

/* JADX INFO: loaded from: classes2.dex */
public class Util {
    public static final boolean FOLD_DEVICE;
    public static final boolean PAD_DEVICE;

    static {
        FOLD_DEVICE = SystemProperties.getInt("persist.sys.muiltdisplay_type", 0) == 2;
        PAD_DEVICE = "tablet".equals(SystemProperties.get("ro.build.characteristics", StyleInfo.DEFAULT_PARAMETER));
    }

    public static float calculateScale(Context context) {
        return context.getResources().getConfiguration().smallestScreenWidthDp / getDesignDp(context);
    }

    public static Bitmap decodeResource(Context context, int i) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        TypedValue typedValue = new TypedValue();
        context.getResources().openRawResource(i, typedValue);
        options.inTargetDensity = typedValue.density;
        options.inScaled = false;
        return BitmapFactory.decodeResource(context.getResources(), i, options);
    }

    public static float getDesignDp(Context context) {
        if (isJ18() && !inLargeScreen(context)) {
            return 320.0f;
        }
        if (isJ18() && inLargeScreen(context)) {
            return 676.0f;
        }
        if (FOLD_DEVICE && inFoldLargeScreen(context)) {
            return 696.0f;
        }
        return PAD_DEVICE ? 711.0f : 392.0f;
    }

    public static boolean inFoldLargeScreen(Context context) {
        return context.getResources().getConfiguration().smallestScreenWidthDp >= 600;
    }

    public static boolean inLargeScreen(Context context) {
        return (context.getResources().getConfiguration().screenLayout & 15) >= 3;
    }

    public static boolean isJ18() {
        return TextUtils.equals(Build.DEVICE, "cetus");
    }
}
