package com.google.android.material.shape;

/* JADX INFO: loaded from: classes.dex */
public class TriangleEdgeTreatment extends EdgeTreatment {
    public final boolean inside;
    public final float size;

    @Override // com.google.android.material.shape.EdgeTreatment
    public void getEdgePath(float f, float f2, float f3, ShapePath shapePath) {
        if (!this.inside) {
            float f4 = this.size;
            shapePath.lineTo(f2 - (f4 * f3), 0.0f, f2, (-f4) * f3);
            shapePath.lineTo(f2 + (this.size * f3), 0.0f, f, 0.0f);
        } else {
            shapePath.lineTo(f2 - (this.size * f3), 0.0f);
            float f5 = this.size;
            shapePath.lineTo(f2, f5 * f3, (f5 * f3) + f2, 0.0f);
            shapePath.lineTo(f, 0.0f);
        }
    }
}
