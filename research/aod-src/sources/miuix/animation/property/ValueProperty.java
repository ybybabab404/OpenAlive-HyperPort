package miuix.animation.property;

import com.miui.maml.folme.AnimatedProperty;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public class ValueProperty<T> extends FloatProperty<T> {
    private volatile String mName;
    public static final ValueProperty FRACTION = new ValueProperty("fraction", 0.002f);
    public static final ValueProperty TRANSLATION_X = new ValueProperty("translationX", 1.0f);
    public static final ValueProperty TRANSLATION_Y = new ValueProperty("translationY", 1.0f);
    public static final ValueProperty TRANSLATION_Z = new ValueProperty("translationZ", 1.0f);
    public static final ValueProperty SCALE = new ValueProperty("scale", 0.004f);
    public static final ValueProperty SCALE_X = new ValueProperty(AnimatedProperty.PROPERTY_NAME_SCALE_X, 0.004f);
    public static final ValueProperty SCALE_Y = new ValueProperty(AnimatedProperty.PROPERTY_NAME_SCALE_Y, 0.004f);
    public static final ValueProperty ROTATION = new ValueProperty(AnimatedProperty.PROPERTY_NAME_ROTATION, 0.1f);
    public static final ValueProperty ROTATION_X = new ValueProperty(AnimatedProperty.PROPERTY_NAME_ROTATION_X, 0.1f);
    public static final ValueProperty ROTATION_Y = new ValueProperty(AnimatedProperty.PROPERTY_NAME_ROTATION_Y, 0.1f);
    public static final ValueProperty X = new ValueProperty(AnimatedProperty.PROPERTY_NAME_X, 1.0f);
    public static final ValueProperty Y = new ValueProperty(AnimatedProperty.PROPERTY_NAME_Y, 1.0f);
    public static final ValueProperty Z = new ValueProperty("z", 1.0f);
    public static final ValueProperty HEIGHT = new ValueProperty("height", 1.0f);
    public static final ValueProperty WIDTH = new ValueProperty("width", 1.0f);
    public static final ValueProperty ALPHA = new ValueProperty(AnimatedProperty.PROPERTY_NAME_ALPHA, 0.00390625f) { // from class: miuix.animation.property.ValueProperty.1
        @Override // miuix.animation.property.ValueProperty, miuix.animation.property.FloatProperty
        public void setValue(Object obj, float f) {
            if (f > 1.0f) {
                super.setValue(obj, 1.0f);
            } else if (f < 0.0f) {
                super.setValue(obj, 0.0f);
            }
            super.setValue(obj, f);
        }
    };
    public static final ValueProperty ALPHA_INT = new IntValueProperty("alphaInt", 1.0f) { // from class: miuix.animation.property.ValueProperty.2
        @Override // miuix.animation.property.IntValueProperty, miuix.animation.property.IIntValueProperty
        public void setIntValue(Object obj, int i) {
            if (i > 255) {
                super.setIntValue(obj, 255);
            } else if (i < 0) {
                super.setIntValue(obj, 0);
            }
            super.setIntValue(obj, i);
        }
    };
    public static final ColorProperty COLOR_INT = new ColorProperty("colorInt");

    public ValueProperty(String str) {
        this(str, -1.0f);
    }

    public ValueProperty(String str, float f) {
        super(str, f);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !ValueProperty.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        return Objects.equals(getName(), ((ValueProperty) obj).getName());
    }

    @Override // android.util.Property
    public String getName() {
        return this.mName != null ? this.mName : super.getName();
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // miuix.animation.property.FloatProperty
    public float getValue(T t) {
        Float f;
        if (!(t instanceof ValueTargetObject) || (f = (Float) ((ValueTargetObject) t).getPropertyValue(getName(), Float.TYPE)) == null) {
            return Float.MAX_VALUE;
        }
        return f.floatValue();
    }

    public int hashCode() {
        return Objects.hash(getName());
    }

    public void setName(String str) {
        this.mName = str;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // miuix.animation.property.FloatProperty
    public void setValue(T t, float f) {
        if (t instanceof ValueTargetObject) {
            ((ValueTargetObject) t).setPropertyValue(getName(), Float.TYPE, Float.valueOf(f));
        }
    }

    @Override // miuix.animation.property.FloatProperty
    public String toString() {
        return "ValueProperty@" + hashCode() + "{name='" + getName() + "',min='" + this.mMinVisibleChange + "'}";
    }
}
