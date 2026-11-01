package miuix.animation;

import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.concurrent.atomic.AtomicInteger;
import miuix.animation.property.ColorProperty;
import miuix.animation.property.FloatProperty;
import miuix.animation.property.IIntValueProperty;
import miuix.animation.property.IntValueProperty;
import miuix.animation.property.ValueProperty;
import miuix.animation.property.ValueTargetObject;
import miuix.animation.property.ViewProperty;

/* JADX INFO: loaded from: classes2.dex */
public class ValueTarget extends IAnimTarget implements FolmeObject {
    public static final /* synthetic */ int $r8$clinit = 0;
    private static final float DEFAULT_MIN_VALUE = 0.002f;
    static ITargetCreator sCreator = new ITargetCreator() { // from class: miuix.animation.ValueTarget.1
        @Override // miuix.animation.ITargetCreator
        public IAnimTarget createTarget(Object obj) {
            return new ValueTarget(Looper.myLooper(), obj);
        }
    };

    @Nullable
    private Folme.ObjectFolmeImpl mCoreAnimator;
    private final AtomicInteger mMaxType;
    private final ValueTargetObject mTargetObj;
    private final boolean mWithoutRealObj;

    public ValueTarget() {
        this(Looper.myLooper(), null);
    }

    public ValueTarget(Looper looper) {
        this(looper, null);
    }

    private ValueTarget(Looper looper, Object obj) {
        super(looper);
        this.mMaxType = new AtomicInteger(1000);
        this.mWithoutRealObj = obj == null;
        this.mTargetObj = new ValueTargetObject(obj == null ? Integer.valueOf(getId()) : obj);
    }

    public static FloatProperty createProperty(String str, Class<?> cls) {
        return (cls == Integer.TYPE || cls == Integer.class) ? new IntValueProperty(str) : new ValueProperty(str);
    }

    public static FloatProperty getFloatProperty(String str) {
        return createProperty(str, Float.TYPE);
    }

    public static IIntValueProperty getIntValueProperty(String str) {
        return (IIntValueProperty) createProperty(str, Integer.TYPE);
    }

    private static boolean isPredefinedProperty(Object obj) {
        return (obj instanceof ValueProperty) || (obj instanceof ViewProperty) || (obj instanceof ColorProperty);
    }

    @Override // miuix.animation.IAnimTarget
    public void clean() {
        if (isAnimRunning(new FloatProperty[0])) {
            cancelRunningAnim();
        }
        this.animManager.clear();
        getNotifier().removeListeners();
    }

    @Override // miuix.animation.IAnimTarget
    public void doSetIntValue(IIntValueProperty iIntValueProperty, int i) {
        boolean z = this.mWithoutRealObj;
        Class cls = Integer.TYPE;
        if (z) {
            this.mTargetObj.setField(this, iIntValueProperty.getName(), cls, Integer.valueOf(i));
        }
        if (isPredefinedProperty(iIntValueProperty)) {
            this.mTargetObj.setPropertyValue(iIntValueProperty.getName(), cls, Integer.valueOf(i));
        }
        Class<?> genericClass = this.mTargetObj.getGenericClass(iIntValueProperty);
        Object realObject = this.mTargetObj.getRealObject();
        if (realObject != null && realObject.getClass() == genericClass) {
            iIntValueProperty.setIntValue(realObject, i);
            return;
        }
        if (getTargetObject().getClass() == genericClass) {
            iIntValueProperty.setIntValue(getTargetObject(), i);
            return;
        }
        if (getClass() == genericClass) {
            iIntValueProperty.setIntValue(this, i);
            return;
        }
        try {
            try {
                try {
                    iIntValueProperty.setIntValue(realObject, i);
                } catch (Exception unused) {
                    iIntValueProperty.setIntValue(getTargetObject(), i);
                }
            } catch (Exception unused2) {
            }
        } catch (Exception unused3) {
            iIntValueProperty.setIntValue(this, i);
        }
    }

    @Override // miuix.animation.IAnimTarget
    public void doSetValue(FloatProperty floatProperty, float f) {
        boolean z = this.mWithoutRealObj;
        Class cls = Float.TYPE;
        if (z) {
            this.mTargetObj.setField(this, floatProperty.getName(), cls, Float.valueOf(f));
        }
        if (isPredefinedProperty(floatProperty)) {
            this.mTargetObj.setPropertyValue(floatProperty.getName(), cls, Float.valueOf(f));
        }
        Object realObject = this.mTargetObj.getRealObject();
        Class<?> genericClass = this.mTargetObj.getGenericClass(floatProperty);
        if (realObject != null && realObject.getClass() == genericClass) {
            floatProperty.setValue(realObject, f);
            return;
        }
        if (getTargetObject().getClass() == genericClass) {
            floatProperty.setValue(getTargetObject(), f);
            return;
        }
        if (getClass() == genericClass) {
            floatProperty.setValue(this, f);
            return;
        }
        if (realObject != null) {
            try {
                floatProperty.setValue(realObject, f);
                return;
            } catch (Exception unused) {
            }
        }
        try {
            try {
                floatProperty.setValue(getTargetObject(), f);
            } catch (Exception unused2) {
            }
        } catch (Exception unused3) {
            floatProperty.setValue(this, f);
        }
    }

    @Override // miuix.animation.FolmeObject
    public Folme.ObjectFolmeImpl folme() {
        return this.mCoreAnimator;
    }

    @Override // miuix.animation.IAnimTarget
    public float getDefaultMinVisible() {
        return 0.002f;
    }

    /* JADX WARN: Code duplicated, block: B:13:0x002b A[PHI: r0
  0x002b: PHI (r0v9 float) = (r0v1 float), (r0v11 float) binds: [B:9:0x0020, B:12:0x0029] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:14:0x002d  */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // miuix.animation.IAnimTarget
    public double getDoubleValue(@NonNull FloatProperty floatProperty) {
        float value;
        double d;
        if (floatProperty instanceof IIntValueProperty) {
            try {
                int intValue = getIntValue((IIntValueProperty) floatProperty);
                if (intValue != Integer.MAX_VALUE) {
                    d = intValue;
                } else {
                    d = Double.MAX_VALUE;
                }
            } catch (Exception unused) {
                value = getValue(floatProperty);
                if (value != Float.MAX_VALUE) {
                    d = value;
                }
                return d != Double.MAX_VALUE ? d : d;
            }
        } else {
            value = getValue(floatProperty);
            if (value != Float.MAX_VALUE) {
                d = value;
            } else {
                d = Double.MAX_VALUE;
            }
        }
        if (d != Double.MAX_VALUE && this.mTargetObj.containProperty(floatProperty.getName())) {
            return this.mTargetObj.getPropertyValue(floatProperty.getName());
        }
    }

    public int getIntValue(String str) {
        return this.mTargetObj.containProperty(str) ? (int) this.mTargetObj.getPropertyValue(str) : getIntValue(getIntValueProperty(str));
    }

    @Override // miuix.animation.IAnimTarget
    public int getIntValue(IIntValueProperty iIntValueProperty) {
        Class<?> genericClass = this.mTargetObj.getGenericClass(iIntValueProperty);
        Object realObject = this.mTargetObj.getRealObject();
        Integer numValueOf = (realObject == null || realObject.getClass() != genericClass) ? null : Integer.valueOf(iIntValueProperty.getIntValue(realObject));
        if (getTargetObject().getClass() == genericClass) {
            numValueOf = Integer.valueOf(iIntValueProperty.getIntValue(getTargetObject()));
        }
        if (getClass() == genericClass) {
            numValueOf = Integer.valueOf(iIntValueProperty.getIntValue(this));
        }
        if (numValueOf == null || numValueOf.intValue() == Integer.MAX_VALUE) {
            try {
                numValueOf = Integer.valueOf(iIntValueProperty.getIntValue(realObject));
            } catch (Exception unused) {
            }
        }
        if (numValueOf == null || numValueOf.intValue() == Integer.MAX_VALUE) {
            try {
                numValueOf = Integer.valueOf(iIntValueProperty.getIntValue(getTargetObject()));
            } catch (Exception unused2) {
            }
        }
        if (numValueOf == null || numValueOf.intValue() == Integer.MAX_VALUE) {
            try {
                numValueOf = Integer.valueOf(iIntValueProperty.getIntValue(this));
            } catch (Exception unused3) {
            }
        }
        if (numValueOf == null || numValueOf.intValue() == Integer.MAX_VALUE) {
            boolean z = this.mWithoutRealObj;
            Class cls = Integer.TYPE;
            Object field = z ? this.mTargetObj.getField(this, iIntValueProperty.getName(), cls) : null;
            if (field != null) {
                return ((Integer) field).intValue();
            }
            if (isPredefinedProperty(iIntValueProperty)) {
                numValueOf = (Integer) this.mTargetObj.getPropertyValue(iIntValueProperty.getName(), cls);
            }
        }
        if (numValueOf == null) {
            return Integer.MAX_VALUE;
        }
        return numValueOf.intValue();
    }

    @Override // miuix.animation.IAnimTarget
    public float getMinVisibleChange(Object obj) {
        if (!(obj instanceof IIntValueProperty) || (obj instanceof ColorProperty)) {
            return super.getMinVisibleChange(obj);
        }
        return 1.0f;
    }

    @Override // miuix.animation.IAnimTarget
    public Object getTargetObject() {
        return this.mTargetObj;
    }

    public float getValue(String str) {
        return this.mTargetObj.containProperty(str) ? (float) this.mTargetObj.getPropertyValue(str) : getValue(getFloatProperty(str));
    }

    @Override // miuix.animation.IAnimTarget
    public float getValue(FloatProperty floatProperty) {
        Class<?> genericClass = this.mTargetObj.getGenericClass(floatProperty);
        Object realObject = this.mTargetObj.getRealObject();
        Float fValueOf = (realObject == null || realObject.getClass() != genericClass) ? null : Float.valueOf(floatProperty.getValue(realObject));
        if (fValueOf == null && getTargetObject().getClass() == genericClass) {
            fValueOf = Float.valueOf(floatProperty.getValue(getTargetObject()));
        }
        if (fValueOf == null && getClass() == genericClass) {
            fValueOf = Float.valueOf(floatProperty.getValue(this));
        }
        if (fValueOf == null || fValueOf.floatValue() == Float.MAX_VALUE) {
            try {
                fValueOf = Float.valueOf(floatProperty.getValue(realObject));
            } catch (Exception unused) {
            }
        }
        if (fValueOf == null || fValueOf.floatValue() == Float.MAX_VALUE) {
            try {
                fValueOf = Float.valueOf(floatProperty.getValue(getTargetObject()));
            } catch (Exception unused2) {
            }
        }
        if (fValueOf == null || fValueOf.floatValue() == Float.MAX_VALUE) {
            try {
                fValueOf = Float.valueOf(floatProperty.getValue(this));
            } catch (Exception unused3) {
            }
        }
        if (fValueOf == null || fValueOf.floatValue() == Float.MAX_VALUE) {
            boolean z = this.mWithoutRealObj;
            Class cls = Float.TYPE;
            Object field = z ? this.mTargetObj.getField(this, floatProperty.getName(), cls) : null;
            if (field != null) {
                return ((Float) field).floatValue();
            }
            if (isPredefinedProperty(floatProperty)) {
                fValueOf = (Float) this.mTargetObj.getPropertyValue(floatProperty.getName(), cls);
            }
        }
        if (fValueOf == null) {
            return Float.MAX_VALUE;
        }
        return fValueOf.floatValue();
    }

    @Override // miuix.animation.IAnimTarget
    @Deprecated
    public double getVelocity(String str) {
        return getVelocity(getFloatProperty(str));
    }

    @Override // miuix.animation.IAnimTarget
    public boolean isValid() {
        ValueTargetObject valueTargetObject = this.mTargetObj;
        if (valueTargetObject == null) {
            return false;
        }
        return valueTargetObject.isValid();
    }

    @Override // miuix.animation.FolmeObject
    public void setFolmeImpl(@NonNull Folme.ObjectFolmeImpl objectFolmeImpl) {
        this.mCoreAnimator = objectFolmeImpl;
    }

    public void setIntValue(String str, int i) {
        if (!this.mTargetObj.containProperty(str)) {
            setIntValue(getIntValueProperty(str), i);
            return;
        }
        this.mTargetObj.setPropertyValue(str, Integer.TYPE, Integer.valueOf(i));
    }

    public void setValue(String str, float f) {
        if (!this.mTargetObj.containProperty(str)) {
            setValue(getFloatProperty(str), f);
            return;
        }
        this.mTargetObj.setPropertyValue(str, Float.TYPE, Float.valueOf(f));
    }

    @Override // miuix.animation.IAnimTarget
    @Deprecated
    public void setVelocity(String str, double d) {
        setVelocity(getFloatProperty(str), d);
    }
}
