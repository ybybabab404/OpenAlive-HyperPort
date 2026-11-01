package miuix.autodensity;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes2.dex */
public class AutoDensityPolicy {
    public static final float ACCESSIBILITY_ZOOM_SMALL;

    static {
        ACCESSIBILITY_ZOOM_SMALL = TextUtils.equals(Build.DEVICE, "zizhan") ? 0.85f : 0.8f;
    }

    public static float calcPadScale(float f) {
        return Math.max(1.0f, Math.min((f / 9.3f) * 1.06f, 1.15f));
    }

    public static float calcPhoneRearScale(float f) {
        return 0.94f;
    }

    public static float calcPhoneScale(float f) {
        return Math.min(1.0f, f / 2.8f);
    }

    /* JADX WARN: Code duplicated, block: B:10:0x001c  */
    /* JADX WARN: Code duplicated, block: B:23:0x0043  */
    /* JADX WARN: Instruction removed from duplicated block: B:23:0x0043, please report this as an issue */
    public static double getDeviceScale(Context context, float f, float f2, boolean z) {
        float fCalcPhoneRearScale;
        double d;
        if (!SkuScale.hasSkuScale()) {
            if (miuix.os.Build.IS_FOLDABLE) {
                if ("cetus".contentEquals(Build.DEVICE)) {
                    d = 1.0d;
                } else {
                    fCalcPhoneRearScale = calcPhoneScale(f2);
                }
            } else if (miuix.os.Build.IS_TABLET) {
                fCalcPhoneRearScale = calcPadScale(f);
            } else if (miuix.os.Build.IS_AUTOMOTIVE) {
                d = 1.0d;
            } else {
                fCalcPhoneRearScale = z ? calcPhoneRearScale(f) : calcPhoneScale(f2);
            }
            if (DebugUtil.isEnableDebug()) {
                DebugUtil.printDensityLog("getDeviceScale " + d);
            }
            return d;
        }
        fCalcPhoneRearScale = SkuScale.getSkuScale(context);
        d = fCalcPhoneRearScale;
        if (DebugUtil.isEnableDebug()) {
            DebugUtil.printDensityLog("getDeviceScale " + d);
        }
        return d;
    }
}
