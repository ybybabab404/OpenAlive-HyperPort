package miuix.animation.internal;

import miuix.animation.IAnimTarget;
import miuix.animation.ViewTarget;
import miuix.animation.listener.UpdateInfo;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.IIntValueProperty;
import miuix.animation.property.ISpecificProperty;
import miuix.animation.utils.CommonUtils;

/* JADX INFO: loaded from: classes2.dex */
public class AnimValueUtils {
    private AnimValueUtils() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    private static double getCurTargetValue(IAnimTarget iAnimTarget, FloatProperty floatProperty, double d) {
        double doubleValue;
        double dSignum = Math.signum(d);
        double dAbs = Math.abs(d);
        if (dAbs == 1000000.0d) {
            return dSignum * ((double) CommonUtils.getSize(iAnimTarget, floatProperty));
        }
        if (iAnimTarget instanceof ViewTarget) {
            doubleValue = floatProperty instanceof IIntValueProperty ? iAnimTarget.getIntValue((IIntValueProperty) floatProperty) : iAnimTarget.getValue(floatProperty);
        } else {
            doubleValue = iAnimTarget.getDoubleValue(floatProperty);
        }
        return dAbs == 1000100.0d ? doubleValue * dSignum : doubleValue;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static double getValue(IAnimTarget iAnimTarget, FloatProperty floatProperty, double d) {
        return floatProperty instanceof ISpecificProperty ? ((ISpecificProperty) floatProperty).getSpecificValue((float) d) : getCurTargetValue(iAnimTarget, floatProperty, d);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static double getValueOfTarget(IAnimTarget iAnimTarget, FloatProperty floatProperty, double d) {
        if (d == 2.147483647E9d) {
            return iAnimTarget.getIntValue((IIntValueProperty) floatProperty);
        }
        return d == 3.4028234663852886E38d ? iAnimTarget.getValue(floatProperty) : getValue(iAnimTarget, floatProperty, d);
    }

    public static boolean handleSetToValue(UpdateInfo updateInfo) {
        AnimInfo animInfo = updateInfo.animInfo;
        if (!isValid(animInfo.setToValue)) {
            return false;
        }
        animInfo.value = animInfo.setToValue;
        animInfo.setToValue = Double.MAX_VALUE;
        return true;
    }

    public static boolean isInvalid(double d) {
        return d == Double.MAX_VALUE || d == 3.4028234663852886E38d || d == 2.147483647E9d;
    }

    public static boolean isValid(double d) {
        return !isInvalid(d);
    }
}
