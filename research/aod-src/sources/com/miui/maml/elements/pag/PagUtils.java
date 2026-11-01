package com.miui.maml.elements.pag;

import android.text.TextUtils;
import com.miui.aod.components.view.BaseStyleSelectView;
import com.miui.maml.util.SystemProperties;
import java.io.File;

/* JADX INFO: loaded from: classes2.dex */
public class PagUtils {
    public static final int HYPER_OS_2_VERSION_CODE = 2;
    public static final File PAG_LIB_FILE = new File("/system_ext/framework/pag-hyperos-release.jar");
    public static final int HYPER_OS_VERSION_CODE = getHyperOsVersionCode();

    public static int ProgressToFrame(double d, int i) {
        if (i <= 1) {
            return 0;
        }
        double dFmod = fmod(d, 1.0d);
        if (dFmod <= BaseStyleSelectView.COLLAPSE_SLIDE_VALUE && d != BaseStyleSelectView.COLLAPSE_SLIDE_VALUE) {
            dFmod += 1.0d;
        }
        int iFloor = (int) Math.floor(dFmod * ((double) i));
        return iFloor == i ? i - 1 : iFloor;
    }

    private static double fmod(double d, double d2) {
        return d - (((double) ((int) Math.floor(d / d2))) * d2);
    }

    public static int getHyperOsVersionCode() {
        String str = SystemProperties.get("ro.mi.os.version.code", "");
        if (TextUtils.isEmpty(str)) {
            return 0;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception unused) {
            return 0;
        }
    }

    public static boolean supportPagFeature() {
        return HYPER_OS_VERSION_CODE >= 2 && PAG_LIB_FILE.exists();
    }
}
