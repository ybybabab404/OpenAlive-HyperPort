package miuix.animation.property;

import android.util.Property;

/* JADX INFO: loaded from: classes2.dex */
public abstract class FloatProperty<T> extends Property<T, Float> {
    float mMinVisibleChange;
    final String mPropertyName;

    public FloatProperty(String str) {
        this(str, -1.0f);
    }

    public FloatProperty(String str, float f) {
        super(Float.class, str);
        this.mPropertyName = str;
        this.mMinVisibleChange = f;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.util.Property
    public Float get(T t) {
        return t == null ? Float.valueOf(0.0f) : Float.valueOf(getValue(t));
    }

    public float getMinVisibleChange() {
        return this.mMinVisibleChange;
    }

    public abstract float getValue(T t);

    @Override // android.util.Property
    public final void set(T t, Float f) {
        if (t != null) {
            setValue(t, f.floatValue());
        }
    }

    public void setMinVisibleChange(float f) {
        this.mMinVisibleChange = f;
    }

    public abstract void setValue(T t, float f);

    public String toString() {
        return getClass().getSimpleName() + "@" + hashCode() + "{name='" + this.mPropertyName + "',min='" + this.mMinVisibleChange + "'}";
    }
}
