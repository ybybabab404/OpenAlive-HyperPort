package androidx.transition;

import android.graphics.PathMeasure;
import android.graphics.PointF;
import android.util.Property;

/* JADX INFO: loaded from: classes.dex */
class PathProperty<T> extends Property<T, Float> {
    public float mCurrentFraction;
    public final float mPathLength;
    public final PathMeasure mPathMeasure;
    public final PointF mPointF;
    public final float[] mPosition;
    public final Property mProperty;

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // android.util.Property
    public Float get(Object obj) {
        return Float.valueOf(this.mCurrentFraction);
    }

    @Override // android.util.Property
    public void set(Object obj, Float f) {
        this.mCurrentFraction = f.floatValue();
        this.mPathMeasure.getPosTan(this.mPathLength * f.floatValue(), this.mPosition, null);
        PointF pointF = this.mPointF;
        float[] fArr = this.mPosition;
        pointF.x = fArr[0];
        pointF.y = fArr[1];
        this.mProperty.set(obj, pointF);
    }
}
