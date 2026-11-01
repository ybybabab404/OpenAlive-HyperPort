package miuix.animation.internal;

import miuix.animation.base.AnimConfig;
import miuix.animation.base.AnimSpecialConfig;
import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes2.dex */
public class AnimConfigUtils {
    private AnimConfigUtils() {
    }

    public static float chooseSpeed(float f, float f2) {
        if (AnimValueUtils.isInvalid(f)) {
            return f2;
        }
        return AnimValueUtils.isInvalid((double) f2) ? f : Math.max(f, f2);
    }

    public static long getDelay(AnimConfig animConfig, AnimSpecialConfig animSpecialConfig) {
        if (animSpecialConfig != null) {
            return Math.max(0L, animSpecialConfig.delay);
        }
        return 0L;
    }

    public static EaseManager.EaseStyle getEase(AnimConfig animConfig, AnimSpecialConfig animSpecialConfig) {
        EaseManager.EaseStyle easeStyle;
        if (animSpecialConfig == null || (easeStyle = animSpecialConfig.ease) == null || easeStyle == AnimConfig.sDefEase) {
            easeStyle = animConfig.ease;
        }
        return easeStyle == null ? AnimConfig.sDefEase : easeStyle;
    }

    public static float getFromSpeed(AnimConfig animConfig, AnimSpecialConfig animSpecialConfig) {
        return (animSpecialConfig == null || !AnimValueUtils.isValid((double) animSpecialConfig.fromSpeed)) ? animConfig.fromSpeed : animSpecialConfig.fromSpeed;
    }

    public static int getTintMode(AnimConfig animConfig, AnimSpecialConfig animSpecialConfig) {
        return Math.max(animConfig.tintMode, animSpecialConfig != null ? animSpecialConfig.tintMode : -1);
    }
}
