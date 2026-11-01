package com.miui.miwallpaper.util;

import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.util.Log;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.lang.reflect.Array;

/* JADX INFO: loaded from: classes2.dex */
public class BitmapUtils {
    public static void checkColorSpace(Bitmap bitmap, String str) {
        if (bitmap == null || bitmap.isRecycled()) {
            StringBuilder sb = new StringBuilder();
            sb.append("checkColorSpace: bitmap is invalid, ");
            sb.append(bitmap == null ? "null" : "isRecycled");
            Log.w(str, sb.toString());
            return;
        }
        if (bitmap.getColorSpace() != null || bitmap.getConfig() == Bitmap.Config.ALPHA_8) {
            return;
        }
        Log.w(str, "checkColorSpace: the color space associated with bitmap is null, reset to SRGB");
        bitmap.setColorSpace(ColorSpace.get(ColorSpace.Named.SRGB));
    }

    public static Bitmap doBlur(Bitmap bitmap, int i) {
        int i2;
        int i3 = i;
        Bitmap bitmapCopy = bitmap.copy(bitmap.getConfig(), true);
        if (i3 < 1) {
            return null;
        }
        int width = bitmapCopy.getWidth();
        int height = bitmapCopy.getHeight();
        int i4 = width * height;
        int[] iArr = new int[i4];
        bitmapCopy.getPixels(iArr, 0, width, 0, 0, width, height);
        int i5 = width - 1;
        int i6 = height - 1;
        int i7 = i3 + i3;
        int i8 = i7 + 1;
        int[] iArr2 = new int[i4];
        int[] iArr3 = new int[i4];
        int[] iArr4 = new int[i4];
        int[] iArr5 = new int[Math.max(width, height)];
        int i9 = (i7 + 2) >> 1;
        int i10 = i9 * i9;
        int i11 = i10 * AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL;
        int[] iArr6 = new int[i11];
        int i12 = 0;
        for (int i13 = 0; i13 < i11; i13++) {
            iArr6[i13] = i13 / i10;
        }
        int[][] iArr7 = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, i8, 3);
        int i14 = i3 + 1;
        int i15 = 0;
        int i16 = 0;
        int i17 = 0;
        while (i15 < height) {
            int[] iArr8 = iArr4;
            int i18 = -i3;
            int i19 = i12;
            int i20 = i19;
            int i21 = i20;
            int i22 = i21;
            int i23 = i22;
            int i24 = i23;
            int i25 = i24;
            int i26 = i25;
            int i27 = i26;
            while (i18 <= i3) {
                int[] iArr9 = iArr6;
                Bitmap bitmap2 = bitmapCopy;
                int i28 = i12;
                int i29 = iArr[i16 + Math.min(i5, Math.max(i18, i28))];
                int[] iArr10 = iArr7[i18 + i3];
                iArr10[i28] = (i29 & 16711680) >> 16;
                iArr10[1] = (i29 & 65280) >> 8;
                iArr10[2] = i29 & 255;
                int iAbs = i14 - Math.abs(i18);
                int i30 = iArr10[i28];
                i19 += i30 * iAbs;
                int i31 = iArr10[1];
                i20 += i31 * iAbs;
                int i32 = iArr10[2];
                i21 += iAbs * i32;
                if (i18 > 0) {
                    i25 += i30;
                    i26 += i31;
                    i27 += i32;
                } else {
                    i22 += i30;
                    i23 += i31;
                    i24 += i32;
                }
                i18++;
                iArr6 = iArr9;
                bitmapCopy = bitmap2;
                i12 = 0;
            }
            int[] iArr11 = iArr6;
            Bitmap bitmap3 = bitmapCopy;
            int i33 = i3;
            int i34 = 0;
            while (i34 < width) {
                iArr2[i16] = iArr11[i19];
                iArr3[i16] = iArr11[i20];
                iArr8[i16] = iArr11[i21];
                int i35 = i19 - i22;
                int i36 = i20 - i23;
                int i37 = i21 - i24;
                int[] iArr12 = iArr7[((i33 - i3) + i8) % i8];
                int i38 = i22 - iArr12[0];
                int i39 = i23 - iArr12[1];
                int i40 = i24 - iArr12[2];
                if (i15 == 0) {
                    i2 = i34;
                    iArr5[i2] = Math.min(i34 + i3 + 1, i5);
                } else {
                    i2 = i34;
                }
                int i41 = iArr[i17 + iArr5[i2]];
                int i42 = (i41 & 16711680) >> 16;
                iArr12[0] = i42;
                int i43 = (i41 & 65280) >> 8;
                iArr12[1] = i43;
                int i44 = i41 & 255;
                iArr12[2] = i44;
                int i45 = i25 + i42;
                int i46 = i26 + i43;
                int i47 = i27 + i44;
                i19 = i35 + i45;
                i20 = i36 + i46;
                i21 = i37 + i47;
                i33 = (i33 + 1) % i8;
                int[] iArr13 = iArr7[i33 % i8];
                int i48 = iArr13[0];
                i22 = i38 + i48;
                int i49 = iArr13[1];
                i23 = i39 + i49;
                int i50 = iArr13[2];
                i24 = i40 + i50;
                i25 = i45 - i48;
                i26 = i46 - i49;
                i27 = i47 - i50;
                i16++;
                i34 = i2 + 1;
            }
            i17 += width;
            i15++;
            iArr4 = iArr8;
            iArr6 = iArr11;
            bitmapCopy = bitmap3;
            i12 = 0;
        }
        int[] iArr14 = iArr4;
        int[] iArr15 = iArr6;
        Bitmap bitmap4 = bitmapCopy;
        int i51 = 0;
        while (i51 < width) {
            int i52 = -i3;
            int i53 = i52 * width;
            int i54 = 0;
            int i55 = 0;
            int i56 = 0;
            int i57 = 0;
            int i58 = 0;
            int i59 = 0;
            int i60 = 0;
            int i61 = 0;
            int i62 = 0;
            while (i52 <= i3) {
                int iMax = Math.max(0, i53) + i51;
                int[] iArr16 = iArr7[i52 + i];
                iArr16[0] = iArr2[iMax];
                iArr16[1] = iArr3[iMax];
                iArr16[2] = iArr14[iMax];
                int iAbs2 = i14 - Math.abs(i52);
                i54 += iArr2[iMax] * iAbs2;
                i55 += iArr3[iMax] * iAbs2;
                i56 += iArr14[iMax] * iAbs2;
                if (i52 > 0) {
                    i60 += iArr16[0];
                    i61 += iArr16[1];
                    i62 += iArr16[2];
                } else {
                    i57 += iArr16[0];
                    i58 += iArr16[1];
                    i59 += iArr16[2];
                }
                if (i52 < i6) {
                    i53 += width;
                }
                i52++;
                i3 = i;
            }
            int i63 = i;
            int i64 = i51;
            int i65 = 0;
            while (i65 < height) {
                iArr[i64] = (iArr[i64] & (-16777216)) | (iArr15[i54] << 16) | (iArr15[i55] << 8) | iArr15[i56];
                int i66 = i54 - i57;
                int i67 = i55 - i58;
                int i68 = i56 - i59;
                int[] iArr17 = iArr7[((i63 - i) + i8) % i8];
                int i69 = i57 - iArr17[0];
                int i70 = i58 - iArr17[1];
                int i71 = i59 - iArr17[2];
                int i72 = i65;
                if (i51 == 0) {
                    iArr5[i72] = Math.min(i72 + i14, i6) * width;
                }
                int i73 = iArr5[i72] + i51;
                int i74 = iArr2[i73];
                iArr17[0] = i74;
                int i75 = iArr3[i73];
                iArr17[1] = i75;
                int i76 = iArr14[i73];
                iArr17[2] = i76;
                int i77 = i60 + i74;
                int i78 = i61 + i75;
                int i79 = i62 + i76;
                i54 = i66 + i77;
                i55 = i67 + i78;
                i56 = i68 + i79;
                i63 = (i63 + 1) % i8;
                int[] iArr18 = iArr7[i63];
                int i80 = iArr18[0];
                i57 = i69 + i80;
                int i81 = iArr18[1];
                i58 = i70 + i81;
                int i82 = iArr18[2];
                i59 = i71 + i82;
                i60 = i77 - i80;
                i61 = i78 - i81;
                i62 = i79 - i82;
                i64 += width;
                i65 = i72 + 1;
            }
            i51++;
            i3 = i;
        }
        bitmap4.setPixels(iArr, 0, width, 0, 0, width, height);
        return bitmap4;
    }

    public static void recycleBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        bitmap.recycle();
        Log.d("BitmapUtils", "recycleBitmap: bitmap is recycle");
    }
}
