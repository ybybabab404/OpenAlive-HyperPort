package com.miui.miwallpaper.material;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import androidx.core.graphics.ColorUtils;
import com.miui.miwallpaper.material.utils.MiPaletteUtils;
import com.miui.miwallpaper.material.utils.MiuiWallpaperColors;
import com.miui.miwallpaper.material.utils.PartRectColorUtils;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public class MiuiColorScheme {
    public static String TAG = "MiuiColorScheme";
    public static final String[] mAccentAndNeutral = {"primary", "secondary", "tertiary", "neutral", "neutral-variant"};
    public static int[] tones = {0, 4, 7, 10, 12, 15, 18, 20, 30, 40, 50, 60, 70, 80, 85, 90, 93, 95, 97, 98, 99, 100};

    public static Map generateHSL(int i) {
        float[] fArr = new float[3];
        ColorUtils.RGBToHSL((i >> 16) & 255, (i >> 8) & 255, i & 255, fArr);
        HashMap map = new HashMap();
        map.put("image-hue", Integer.valueOf((int) fArr[0]));
        map.put("image-saturation", Integer.valueOf((int) fArr[1]));
        map.put("image-lightness", Integer.valueOf((int) fArr[2]));
        Log.d(TAG, "generateHCT: imageHSL =  " + map);
        return map;
    }

    public static Map generatePalette(int i) {
        HashMap map = new HashMap();
        for (String str : mAccentAndNeutral) {
            for (int i2 = 0; i2 < tones.length; i2++) {
                map.put(str + tones[i2], Integer.valueOf(MiPaletteUtils.getInstance().getPaletteColor(i, str, i2)));
            }
        }
        Log.d(TAG, "generatePalette: miuiPalette =  " + map);
        return map;
    }

    public static boolean getIsDeepWallpaper(Context context, Bitmap bitmap, String str, int i, int i2, String str2, int i3) {
        return str == null ? getIsDeepWallpaper(bitmap, false) : getIsDeepWallpaper(bitmap, false, (Rect) PartRectColorUtils.getPartScreenRectUtils(context, str, new int[]{i, i2}, bitmap.getWidth(), bitmap.getHeight(), i3).get(str2));
    }

    public static boolean getIsDeepWallpaper(Bitmap bitmap, boolean z) {
        return getIsDeepWallpaper(bitmap, z, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
    }

    public static boolean getIsDeepWallpaper(Bitmap bitmap, boolean z, Rect rect) {
        if (bitmap == null) {
            return false;
        }
        boolean z2 = (MiuiWallpaperColors.miuiCalculateDarkHints(bitmap, z, rect) & 1) != 1;
        Log.d(TAG, "getIsDeepWallpaper isDeep=" + z2 + " rect=" + rect);
        return z2;
    }

    public static Map getMiuiWallpaperPalette(Context context, Bitmap bitmap, String str, int i, int i2, String str2, Rect rect, int i3) {
        if (str == null) {
            return getMiuiWallpaperPalette(bitmap, rect);
        }
        return ("doodle".equals(str) || "smart_frame".equals(str)) ? getMiuiWallpaperPalette(bitmap, null) : getMiuiWallpaperPalette(bitmap, (Rect) PartRectColorUtils.getPartScreenRectUtils(context, str, new int[]{i, i2}, bitmap.getWidth(), bitmap.getHeight(), i3).get(str2));
    }

    public static Map getMiuiWallpaperPalette(Bitmap bitmap, Rect rect) {
        return getMiuiWallpaperPalette(null, bitmap, rect);
    }

    public static Map getMiuiWallpaperPalette(String str, Bitmap bitmap, Rect rect) {
        if (bitmap == null || bitmap.isRecycled()) {
            return null;
        }
        int iFromBitmap = (rect == null || rect.isEmpty()) ? MiuiWallpaperColors.fromBitmap(bitmap) : MiuiWallpaperColors.fromBitmap(bitmap, rect);
        return "full_image_rect".equals(str) ? generateHSL(iFromBitmap) : generatePalette(iFromBitmap);
    }
}
