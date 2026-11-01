package miuix.core.util;

import android.text.TextUtils;

/* JADX INFO: loaded from: classes2.dex */
public class RomUtils {
    public static int HYPER_OS_VERSION_CODE = -1;
    public static int MIUI_VERSION_CODE = -1;

    public static int getHyperOsVersion() {
        if (HYPER_OS_VERSION_CODE == -1) {
            HYPER_OS_VERSION_CODE = getHyperOsVersionNoCache();
        }
        return HYPER_OS_VERSION_CODE;
    }

    public static int getHyperOsVersionNoCache() {
        return SystemProperties.getInt("ro.mi.os.version.code", 0);
    }

    public static int getMiuiVersion() {
        if (MIUI_VERSION_CODE == -1) {
            MIUI_VERSION_CODE = getMiuiVersionNoCache();
        }
        return MIUI_VERSION_CODE;
    }

    public static int getMiuiVersionNoCache() {
        return SystemProperties.getInt("ro.miui.ui.version.code", 0);
    }

    public static boolean isHyperOsRom() {
        return getHyperOsVersion() > 0;
    }

    public static boolean isMiuiXVSdkSupported() {
        return getMiuiVersion() >= 15;
    }

    public static boolean isXiaomiSystem() {
        return (TextUtils.isEmpty(SystemProperties.get("ro.mi.os.version.code", "")) && TextUtils.isEmpty(SystemProperties.get("ro.miui.ui.version.code", ""))) ? false : true;
    }
}
