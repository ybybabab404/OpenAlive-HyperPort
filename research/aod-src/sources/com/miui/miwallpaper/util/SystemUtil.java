package com.miui.miwallpaper.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import miui.os.Build;

/* JADX INFO: loaded from: classes2.dex */
public class SystemUtil {
    public static final boolean IS_REVEAL_OFF_WHEN_SCREEN_OFF;
    public static int mKeyguardEditorOsVersion = -1;

    static {
        String str = Build.DEVICE;
        IS_REVEAL_OFF_WHEN_SCREEN_OFF = "chenfeng".equals(str) || "sapphire".equals(str) || "rothko".equals(str) || "goku".equals(str);
    }

    public static boolean isOS3KeyguardEditorAtLeast(Context context) {
        int i = mKeyguardEditorOsVersion;
        if (i != -1) {
            return (i & 240) >= 48;
        }
        try {
            int i2 = context.getPackageManager().getApplicationInfo("com.miui.aod", 128).metaData.getInt("editor_os_version");
            mKeyguardEditorOsVersion = i2;
            return (i2 & 240) >= 48;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("SystemUtil", "isOS3KeyguardEditor: error " + e.getMessage());
            return false;
        }
    }
}
