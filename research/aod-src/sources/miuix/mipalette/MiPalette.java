package miuix.mipalette;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/* JADX INFO: loaded from: classes2.dex */
public class MiPalette {
    public static final int DEFAULT_MAIN_COLORS;
    static int MAX_AREA = 0;
    private static final String TAG = "MiPalette_log";
    private static int defaultColor;
    static Map<String, Integer> map;
    static int[] pixels;

    public enum MODE {
        MODE_FAST(0),
        MODE_PRESION(1);

        public int value;

        MODE(int i) {
            this.value = i;
        }

        public int getValue() {
            return this.value;
        }
    }

    static {
        System.loadLibrary("MiMainColor");
        int iRgb = Color.rgb(64, com.miui.maml.R.styleable.AppCompatTheme_windowNoTitle, 191);
        DEFAULT_MAIN_COLORS = iRgb;
        defaultColor = iRgb;
        MAX_AREA = 12544;
        pixels = new int[12544 * 2];
        map = new HashMap();
    }

    public static int getBrightness(Bitmap bitmap) {
        if (bitmap == null) {
            return 0;
        }
        Bitmap pixels2 = getPixels(pixels, bitmap, null);
        return getBrightness(pixels, pixels2.getWidth(), pixels2.getHeight());
    }

    public static native int getBrightness(int[] iArr, int i, int i2);

    public static int getBrightnessRect(Bitmap bitmap, Rect rect) {
        if (bitmap == null || !isRectValid(rect, bitmap)) {
            return 0;
        }
        ArrayList arrayList = new ArrayList();
        Rect rect2 = new Rect(rect);
        arrayList.add(rect2);
        Bitmap pixels2 = getPixels(pixels, bitmap, arrayList);
        return getBrightnessRect(pixels, pixels2.getWidth(), pixels2.getHeight(), new int[]{rect2.left, rect2.top, rect2.width(), rect2.height()});
    }

    public static native int getBrightnessRect(int[] iArr, int i, int i2, int[] iArr2);

    public static native int getDurationTime();

    public static int getHCTBrightness(Bitmap bitmap) {
        if (bitmap == null) {
            return 0;
        }
        Bitmap pixels2 = getPixels(pixels, bitmap, null);
        return nGetHCTBrightness(pixels, pixels2.getWidth(), pixels2.getHeight());
    }

    public static int getHCTBrightness(Bitmap bitmap, Rect rect) {
        if (bitmap == null || !isRectValid(rect, bitmap)) {
            return 0;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(new Rect(rect));
        Bitmap pixels2 = getPixels(pixels, Bitmap.createBitmap(bitmap, rect.left, rect.top, rect.width(), rect.height()), arrayList);
        return nGetHCTBrightness(pixels, pixels2.getWidth(), pixels2.getHeight());
    }

    public static float[] getHCTColor(int i) {
        return nGetHCTColor(i);
    }

    public static int getMainColor(Bitmap bitmap) {
        return getMainColorHCT(bitmap);
    }

    public static int getMainColorHCT(Bitmap bitmap) {
        if (bitmap == null) {
            return defaultColor;
        }
        Bitmap pixels2 = getPixels(pixels, bitmap, null);
        return getMainColorHCT(pixels, pixels2.getWidth(), pixels2.getHeight());
    }

    public static native int getMainColorHCT(int[] iArr, int i, int i2);

    public static int getMainColorHSV(Bitmap bitmap) {
        if (bitmap == null) {
            return defaultColor;
        }
        Bitmap pixels2 = getPixels(pixels, bitmap, null);
        return getMainColorHSV(pixels, pixels2.getWidth(), pixels2.getHeight());
    }

    public static int getMainColorHSV(Bitmap bitmap, Rect rect) {
        if (bitmap == null || !isRectValid(rect, bitmap)) {
            return defaultColor;
        }
        new ArrayList().add(new Rect(rect));
        Bitmap pixels2 = getPixels(pixels, Bitmap.createBitmap(bitmap, rect.left, rect.top, rect.width(), rect.height()), null);
        return getMainColorHSV(pixels, pixels2.getWidth(), pixels2.getHeight());
    }

    public static native int getMainColorHSV(int[] iArr, int i, int i2);

    public static native int getMainColorMultiRect(int[] iArr, int[] iArr2, int i, int i2, int[] iArr3, int i3);

    public static Vector<Integer> getMainColorMultiRect(Bitmap bitmap, Vector<Rect> vector) {
        if (bitmap == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < vector.size(); i++) {
            arrayList.add(new Rect(vector.get(i)));
        }
        Bitmap pixels2 = getPixels(pixels, bitmap, arrayList);
        int size = vector.size();
        int[] iArr = new int[size * 4];
        for (int i2 = 0; i2 < size; i2++) {
            if (!isRectValid((Rect) arrayList.get(i2), pixels2)) {
                return null;
            }
            int i3 = i2 * 4;
            iArr[i3] = ((Rect) arrayList.get(i2)).left;
            iArr[i3 + 1] = ((Rect) arrayList.get(i2)).top;
            iArr[i3 + 2] = ((Rect) arrayList.get(i2)).width();
            iArr[i3 + 3] = ((Rect) arrayList.get(i2)).height();
        }
        int[] iArr2 = new int[size];
        getMainColorMultiRect(iArr2, pixels, pixels2.getWidth(), pixels2.getHeight(), iArr, size);
        Vector<Integer> vector2 = new Vector<>();
        for (int i4 = 0; i4 < size; i4++) {
            vector2.add(Integer.valueOf(iArr2[i4]));
        }
        return vector2;
    }

    public static int getMainColorRect(Bitmap bitmap, Rect rect) {
        if (bitmap == null || !isRectValid(rect, bitmap)) {
            return defaultColor;
        }
        ArrayList arrayList = new ArrayList();
        Rect rect2 = new Rect(rect);
        arrayList.add(rect2);
        Bitmap pixels2 = getPixels(pixels, bitmap, arrayList);
        return getMainColorRect(pixels, pixels2.getWidth(), pixels2.getHeight(), new int[]{rect2.left, rect2.top, rect2.width(), rect2.height()});
    }

    public static native int getMainColorRect(int[] iArr, int i, int i2, int[] iArr2);

    public static void getMainColorsHSV(Bitmap bitmap, int[] iArr) {
        if (bitmap == null || iArr == null) {
            return;
        }
        Bitmap pixels2 = getPixels(pixels, bitmap, null);
        getMainColorsHSV(pixels, pixels2.getWidth(), pixels2.getHeight(), iArr, iArr.length);
    }

    public static native void getMainColorsHSV(int[] iArr, int i, int i2, int[] iArr2, int i3);

    public static native int getPaletteColor(int i, int i2, int i3);

    public static int getPaletteColor(int i, String str, int i2) {
        if (map.containsKey(str)) {
            return getPaletteColor(i, map.get(str).intValue(), i2);
        }
        return 0;
    }

    private static Bitmap getPixels(int[] iArr, Bitmap bitmap, List<Rect> list) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int i = width * height;
        int i2 = MAX_AREA;
        if (i <= i2) {
            bitmap.getPixels(iArr, 0, width, 0, 0, width, height);
            return bitmap;
        }
        float fSqrt = (float) Math.sqrt(i2 / i);
        int iCeil = (int) Math.ceil(width * fSqrt);
        int iCeil2 = (int) Math.ceil(height * fSqrt);
        Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap, iCeil, iCeil2, false);
        bitmapCreateScaledBitmap.getPixels(iArr, 0, iCeil, 0, 0, iCeil, iCeil2);
        if (list != null) {
            for (Rect rect : list) {
                rect.left = (int) (rect.left * fSqrt);
                rect.right = (int) (rect.right * fSqrt);
                rect.top = (int) (rect.top * fSqrt);
                rect.bottom = (int) (rect.bottom * fSqrt);
            }
        }
        return bitmapCreateScaledBitmap;
    }

    public static void init() {
        init(20, 20);
    }

    public static void init(int i, int i2) {
        nSetToneThreshold(i);
        nSetChromaThreshold(i2);
        map.clear();
        map.put("primary", 0);
        map.put("secondary", 1);
        map.put("tertiary", 2);
        map.put("neutral", 3);
        map.put("neutral-variant", 4);
        init_module();
    }

    public static native void init_module();

    private static boolean isRectValid(Rect rect, Bitmap bitmap) {
        int i;
        int i2;
        int i3;
        if (rect == null) {
            Log.e(TAG, "rect == null");
            return false;
        }
        int i4 = rect.left;
        if (i4 < 0 || (i = rect.top) < 0 || (i2 = rect.right) < 0 || (i3 = rect.bottom) < 0) {
            Log.e(TAG, "fields of Rect must >= 0");
            return false;
        }
        if (i4 >= i2 || i3 <= i) {
            Log.e(TAG, "left must < right  or  bottom must > top");
            return false;
        }
        if (rect.width() <= bitmap.getWidth() && rect.height() <= bitmap.getHeight()) {
            return true;
        }
        Log.e(TAG, "rect size must <= bitmap size");
        return false;
    }

    public static native int nGetHCTBrightness(int[] iArr, int i, int i2);

    public static native float[] nGetHCTColor(int i);

    public static native void nSetChromaThreshold(int i);

    public static native void nSetDefaultColor(int i);

    public static native void nSetMode(int i);

    public static native void nSetSmooth(boolean z);

    public static native void nSetToneThreshold(int i);

    public static native void reset();

    public static void setChromaThreshold(int i) {
        nSetChromaThreshold(i);
    }

    public static void setDefaultMainColor(int i) {
        defaultColor = i;
        nSetDefaultColor(i);
    }

    public static void setMode(MODE mode) {
        nSetMode(mode.getValue());
    }

    public static void setSmooth(boolean z) {
        nSetSmooth(z);
    }

    public static void setToneThreshold(int i) {
        nSetToneThreshold(i);
    }
}
