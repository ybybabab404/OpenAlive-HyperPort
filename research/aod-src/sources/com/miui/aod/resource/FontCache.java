package com.miui.aod.resource;

import android.graphics.Typeface;
import androidx.collection.ArrayMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class FontCache {
    public static volatile FontCache sInstance;
    public final Map CACHE_FONT_MAP = new ArrayMap();

    public static FontCache getInstance() {
        if (sInstance == null) {
            synchronized (FontCache.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new FontCache();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return sInstance;
    }

    public Typeface getTypeface(String str, String str2) {
        String str3 = str + "&weight=" + str2;
        if (this.CACHE_FONT_MAP.containsKey(str3)) {
            return (Typeface) this.CACHE_FONT_MAP.get(str3);
        }
        Typeface typefaceBuild = new Typeface.Builder(str).setFontVariationSettings(str2).build();
        this.CACHE_FONT_MAP.put(str3, typefaceBuild);
        return typefaceBuild;
    }
}
