package miuix.overscroller.internal.dynamicanimation.animation;

import android.util.FloatProperty;

/* JADX INFO: loaded from: classes2.dex */
public abstract class FloatPropertyCompat<T> {
    public final String mPropertyName;

    /* JADX INFO: renamed from: miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat$1, reason: invalid class name */
    class AnonymousClass1 extends FloatPropertyCompat<Object> {
        public final /* synthetic */ FloatProperty val$property;

        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(Object obj) {
            return ((Float) this.val$property.get(obj)).floatValue();
        }

        @Override // miuix.overscroller.internal.dynamicanimation.animation.FloatPropertyCompat
        public void setValue(Object obj, float f) {
            this.val$property.setValue(obj, f);
        }
    }

    public FloatPropertyCompat(String str) {
        this.mPropertyName = str;
    }

    public abstract float getValue(Object obj);

    public abstract void setValue(Object obj, float f);
}
