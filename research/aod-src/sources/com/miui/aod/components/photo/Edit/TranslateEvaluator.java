package com.miui.aod.components.photo.Edit;

import android.animation.TypeEvaluator;
import android.graphics.Matrix;

/* JADX INFO: loaded from: classes.dex */
public class TranslateEvaluator implements TypeEvaluator<Matrix> {
    private final Matrix mMatrix = new Matrix();
    private final float[] mStart = new float[9];
    private final float[] mEnd = new float[9];
    private final float[] mRaw = new float[9];

    @Override // android.animation.TypeEvaluator
    public Matrix evaluate(float f, Matrix matrix, Matrix matrix2) {
        matrix.getValues(this.mStart);
        matrix2.getValues(this.mEnd);
        float[] fArr = this.mRaw;
        float[] fArr2 = this.mStart;
        float f2 = fArr2[0];
        float[] fArr3 = this.mEnd;
        fArr[0] = f2 + ((fArr3[0] - f2) * f);
        float f3 = fArr2[1];
        fArr[1] = f3 + ((fArr3[1] - f3) * f);
        float f4 = fArr2[2];
        fArr[2] = f4 + ((fArr3[2] - f4) * f);
        float f5 = fArr2[3];
        fArr[3] = f5 + ((fArr3[3] - f5) * f);
        float f6 = fArr2[4];
        fArr[4] = f6 + ((fArr3[4] - f6) * f);
        float f7 = fArr2[5];
        fArr[5] = f7 + ((fArr3[5] - f7) * f);
        fArr[6] = fArr2[6];
        fArr[7] = fArr2[7];
        fArr[8] = fArr2[8];
        this.mMatrix.setValues(fArr);
        return this.mMatrix;
    }
}
