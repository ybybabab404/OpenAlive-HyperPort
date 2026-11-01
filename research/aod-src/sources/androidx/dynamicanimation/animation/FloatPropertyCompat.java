package androidx.dynamicanimation.animation;

import android.util.FloatProperty;

/* JADX INFO: loaded from: classes.dex */
public abstract class FloatPropertyCompat<T> {
    public final String mPropertyName;

    /* JADX INFO: renamed from: androidx.dynamicanimation.animation.FloatPropertyCompat$1, reason: invalid class name */
    final class AnonymousClass1 extends FloatPropertyCompat<Object> {
        public final /* synthetic */ FloatProperty val$property;

        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
        public float getValue(Object obj) {
            return ((Float) this.val$property.get(obj)).floatValue();
        }

        @Override // androidx.dynamicanimation.animation.FloatPropertyCompat
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
