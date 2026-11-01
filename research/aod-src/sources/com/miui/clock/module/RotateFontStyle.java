package com.miui.clock.module;

/* JADX INFO: loaded from: classes.dex */
public abstract class RotateFontStyle extends BaseFontStyle {
    public int[][][] rotateMinus;
    public int[][][] rotatePlus;

    @Override // com.miui.clock.module.BaseFontStyle
    public int[] getFontRotateValue(int i, float f) {
        int[][] iArr;
        if (f >= 0.0f) {
            int[][][] iArr2 = this.rotatePlus;
            if (iArr2 != null && iArr2.length >= i) {
                iArr = iArr2[i];
            }
            return null;
        }
        int[][][] iArr3 = this.rotateMinus;
        if (iArr3 == null || iArr3.length < i) {
            return null;
        }
        iArr = iArr3[i];
        if (iArr == null) {
            return null;
        }
        int iAbs = Math.abs((int) f);
        float fAbs = Math.abs(f) - iAbs;
        if (fAbs == 0.0f) {
            if (iArr.length > iAbs) {
                return iArr[iAbs];
            }
            return null;
        }
        int i2 = iAbs + 1;
        if (iArr.length > i2 && iArr[0].length >= 2) {
            int[] iArr4 = iArr[i2];
            int i3 = iArr4[0];
            int[] iArr5 = iArr[iAbs];
            int i4 = iArr5[0];
            int i5 = iArr4[1];
            int i6 = iArr5[1];
            return new int[]{(int) (((i3 - i4) * fAbs) + i4), (int) ((fAbs * (i5 - i6)) + i6)};
        }
        return null;
    }
}
