package com.miui.getalpha;

import android.graphics.Bitmap;
import android.util.Log;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;

/* JADX INFO: loaded from: classes.dex */
public class GetAlpha {
    public static float GetAlphaFromBitmap(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i = width * height;
        int[] iArr = new int[i];
        int[] iArr2 = new int[i];
        int[] iArr3 = new int[AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL];
        int i2 = i / 20;
        bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
        int iMax = 0;
        for (int i3 = 0; i3 < i; i3++) {
            int i4 = iArr[i3];
            int i5 = (int) ((((double) ((16711680 & i4) >> 16)) * 0.299d) + (((double) ((65280 & i4) >> 8)) * 0.587d) + (((double) (255 & i4)) * 0.114d));
            iArr2[i3] = i5;
            iArr3[i5] = iArr3[i5] + 1;
            iMax = Math.max(iArr2[i3], iMax);
        }
        if (iArr3[255] >= i2) {
            iMax = 255;
        } else {
            for (int i6 = 254; i6 >= 0; i6--) {
                int i7 = iArr3[i6];
                int i8 = iArr3[i6 + 1];
                if (i7 + i8 >= i2) {
                    iMax = i6;
                    break;
                }
                iArr3[i6] = i7 + i8;
            }
        }
        float f = iMax >= 80 ? 80.0f / iMax : 1.0f;
        Log.i("GetAlpha", "alpha:" + f);
        return f;
    }
}
