package miuix.animation.listener;

import androidx.annotation.Nullable;
import com.miui.aod.components.view.BaseStyleSelectView;
import java.util.Collection;
import miuix.animation.IAnimTarget;
import miuix.animation.base.AnimSpecialConfig;
import miuix.animation.internal.AnimInfo;
import miuix.animation.internal.AnimTask;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.IIntValueProperty;
import miuix.animation.utils.LogUtils;

/* JADX INFO: loaded from: classes2.dex */
public class UpdateInfo {
    public int frameCount;
    public boolean isCompleted;
    public boolean justStart;
    public final FloatProperty property;
    public final boolean useInt;
    public double velocity;
    public Integer preparedTransitionId = null;
    public final AnimInfo animInfo = new AnimInfo();

    public UpdateInfo(FloatProperty floatProperty) {
        this.property = floatProperty;
        this.useInt = floatProperty instanceof IIntValueProperty;
    }

    public static UpdateInfo findBy(Collection<UpdateInfo> collection, FloatProperty floatProperty) {
        for (UpdateInfo updateInfo : collection) {
            FloatProperty floatProperty2 = updateInfo.property;
            if (floatProperty2 != null && floatProperty2.equals(floatProperty)) {
                return updateInfo;
            }
        }
        return null;
    }

    public static UpdateInfo findByName(Collection<UpdateInfo> collection, String str) {
        for (UpdateInfo updateInfo : collection) {
            FloatProperty floatProperty = updateInfo.property;
            if (floatProperty != null && floatProperty.getName().equals(str)) {
                return updateInfo;
            }
        }
        return null;
    }

    public static float getSafeFloatValue(float f, @Nullable AnimSpecialConfig animSpecialConfig) {
        if (animSpecialConfig == null || !animSpecialConfig.hasSetSafeValue) {
            return f;
        }
        double d = animSpecialConfig.minValue;
        if (f < ((float) d)) {
            return (float) d;
        }
        double d2 = animSpecialConfig.maxValue;
        return f > ((float) d2) ? (float) d2 : f;
    }

    public static int getSafeIntValue(int i, @Nullable AnimSpecialConfig animSpecialConfig) {
        if (animSpecialConfig == null || !animSpecialConfig.hasSetSafeValue) {
            return i;
        }
        double d = animSpecialConfig.minValue;
        if (i < ((int) d)) {
            return (int) d;
        }
        double d2 = animSpecialConfig.maxValue;
        return i > ((int) d2) ? (int) d2 : i;
    }

    public float getFloatValue() {
        AnimInfo animInfo = this.animInfo;
        double d = animInfo.setToValue;
        if (d != Double.MAX_VALUE) {
            return (float) d;
        }
        double d2 = animInfo.value;
        if (d2 >= 3.4028234663852886E38d) {
            d2 = 3.4028234663852886E38d;
        }
        float f = (float) d2;
        if (f != Float.MAX_VALUE) {
            return Math.max(-3.4028235E38f, f);
        }
        LogUtils.debug("warning value is Float.MAX_VALUE !! correct to startValue " + this.animInfo.startValue + " " + this, new Object[0]);
        AnimInfo animInfo2 = this.animInfo;
        double d3 = animInfo2.startValue;
        animInfo2.value = d3;
        return (float) d3;
    }

    public int getIntValue() {
        AnimInfo animInfo = this.animInfo;
        double d = animInfo.setToValue;
        if (d != Double.MAX_VALUE) {
            return (int) d;
        }
        double d2 = animInfo.value;
        int i = d2 >= Double.MAX_VALUE ? Integer.MAX_VALUE : (int) d2;
        if (i != Integer.MAX_VALUE) {
            return Math.max(-2147483647, i);
        }
        LogUtils.debug("warning value is Integer.MAX_VALUE !! correct to startValue " + this.animInfo.startValue + " " + this, new Object[0]);
        AnimInfo animInfo2 = this.animInfo;
        double d3 = animInfo2.startValue;
        animInfo2.value = d3;
        return (int) d3;
    }

    public Class<?> getType() {
        return this.property instanceof IIntValueProperty ? Integer.TYPE : Float.TYPE;
    }

    public <T> T getValue(Class<T> cls) {
        if (cls == Float.class || cls == Float.TYPE) {
            return (T) Float.valueOf(getFloatValue());
        }
        return (cls == Double.class || cls == Double.TYPE) ? (T) Double.valueOf(this.animInfo.value) : (T) Integer.valueOf(getIntValue());
    }

    public boolean isValid() {
        return this.property != null;
    }

    public void reset() {
        this.isCompleted = false;
        this.frameCount = 0;
    }

    public void setOp(byte b) {
        boolean z = b == 0 || b > 2;
        this.isCompleted = z;
        if (z && AnimTask.isRunning(this.animInfo.op)) {
            this.animInfo.justEnd = true;
        }
        this.animInfo.op = b;
        if (LogUtils.isLogMoreEnable()) {
            LogUtils.debug("---- UpdateInfo id=" + hashCode(), "name=" + this.property.getName(), "setOp=" + ((int) b), "justEnd=" + this.animInfo.justEnd, "completed=" + this.isCompleted);
        }
    }

    public void setTargetValue(IAnimTarget iAnimTarget, boolean z) {
        if (z) {
            if (this.useInt) {
                iAnimTarget.doSetIntValue((IIntValueProperty) this.property, getIntValue());
                return;
            } else {
                iAnimTarget.doSetValue(this.property, getFloatValue());
                return;
            }
        }
        if (this.useInt) {
            iAnimTarget.setIntValue((IIntValueProperty) this.property, getIntValue());
        } else {
            iAnimTarget.setValue(this.property, getFloatValue());
        }
    }

    public void skipToTargetValue(IAnimTarget iAnimTarget) {
        AnimInfo animInfo = this.animInfo;
        double d = animInfo.targetValue;
        if (d != Double.MAX_VALUE) {
            animInfo.value = d;
        }
        this.velocity = BaseStyleSelectView.COLLAPSE_SLIDE_VALUE;
        setTargetValue(iAnimTarget, false);
        setOp((byte) 3);
    }

    public String toString() {
        return "UpdateInfo{id=" + hashCode() + " " + this.property.getName() + "=" + this.animInfo.value + ", v_format=" + (this.useInt ? Integer.toHexString((int) this.animInfo.value) : Double.toString(this.animInfo.value)) + ", p=" + this.property + ", op=" + ((int) this.animInfo.op) + ", v=" + this.animInfo.value + ", start-v=" + this.animInfo.startValue + ", target-v=" + this.animInfo.targetValue + ", useInt=" + this.useInt + ", completed=" + this.isCompleted + ", setTo-v=" + this.animInfo.setToValue + ", velocity=" + this.velocity + ", start-t=" + this.animInfo.startTime + ", frameCount=" + this.frameCount + ", frameInterval=" + this.animInfo.frameInterval + '}';
    }
}
