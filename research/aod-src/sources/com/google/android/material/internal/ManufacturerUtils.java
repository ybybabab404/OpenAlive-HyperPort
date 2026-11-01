package com.google.android.material.internal;

import android.os.Build;
import androidx.annotation.RestrictTo;
import java.util.Locale;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public class ManufacturerUtils {
    public static String getManufacturer() {
        String str = Build.MANUFACTURER;
        return str != null ? str.toLowerCase(Locale.ENGLISH) : "";
    }

    public static boolean isDateInputKeyboardMissingSeparatorCharacters() {
        return isLGEDevice() || isSamsungDevice();
    }

    public static boolean isLGEDevice() {
        return getManufacturer().equals("lge");
    }

    public static boolean isMeizuDevice() {
        return getManufacturer().equals("meizu");
    }

    public static boolean isSamsungDevice() {
        return getManufacturer().equals("samsung");
    }
}
