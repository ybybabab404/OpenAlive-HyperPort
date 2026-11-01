package com.miui.miwallpaper.material.utils;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import com.miui.maml.R;
import java.util.Objects;
import miuix.mipalette.MiPalette;

/* JADX INFO: loaded from: classes2.dex */
public class MiPaletteUtils {
    public static MiPaletteUtils instance;
    public final int DEFAULT_MAIN_COLORS;
    public final String TAG = "MiPaletteUtils";

    public MiPaletteUtils() {
        int iRgb = Color.rgb(64, R.styleable.AppCompatTheme_windowNoTitle, 191);
        this.DEFAULT_MAIN_COLORS = iRgb;
        MiPalette.init();
        MiPalette.setDefaultMainColor(iRgb);
    }

    public static MiPaletteUtils getInstance() {
        if (instance == null) {
            synchronized (MiPaletteUtils.class) {
                try {
                    if (instance == null) {
                        instance = new MiPaletteUtils();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return instance;
    }

    public int getMainColor(Bitmap bitmap, Rect rect) {
        Objects.requireNonNull(bitmap, "Bitmap can't be null");
        if (bitmap.isRecycled()) {
            Log.d("MiPaletteUtils", "fromBitmap: bitmap is recycled");
            return this.DEFAULT_MAIN_COLORS;
        }
        int mainColorRect = MiPalette.getMainColorRect(bitmap, rect);
        Log.d("MiPaletteUtils", "MiPalette sdk used, glass shader version:1.2.9");
        Log.d("MiPaletteUtils", "fromBitmap mainColor=" + mainColorRect + " rect=" + rect + " width=" + bitmap.getWidth() + " height=" + bitmap.getHeight());
        return mainColorRect;
    }

    public int getPaletteColor(int i, String str, int i2) {
        return MiPalette.getPaletteColor(i, str, i2);
    }
}
