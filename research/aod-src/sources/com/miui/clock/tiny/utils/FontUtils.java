package com.miui.clock.tiny.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"NewApi"})
public class FontUtils {
    public static Map miSansFontsPathByLocaleCache = new HashMap();
    public static SparseArray miSansTypefaceCacheByFontsPath = new SparseArray();
    public static final Map sMiProTypefaceCache = new ConcurrentHashMap();
    public static final Map sMiSansLangTagCache = new ConcurrentHashMap();
    public static final String[] FALLBACK_FONT = {"fallback-font"};

    public static Typeface createMiProTypefaceByFilesFallback(int i) {
        Typeface.CustomFallbackBuilder customFallbackBuilder;
        Typeface typefaceBuild;
        List<String> allMisansFilePath = getAllMisansFilePath();
        if (allMisansFilePath == null || allMisansFilePath.isEmpty()) {
            Log.w("FontUtils", "createMiProTypefaceByFilesFallback: miproFiles is empty");
            return null;
        }
        try {
            customFallbackBuilder = null;
            for (String str : allMisansFilePath) {
                try {
                    if (!TextUtils.isEmpty(str) && new File(str).exists()) {
                        Font.Builder builder = new Font.Builder(new File(str));
                        builder.setFontVariationSettings("'wght' " + i);
                        FontFamily fontFamilyBuild = new FontFamily.Builder(builder.build()).build();
                        if (customFallbackBuilder == null) {
                            customFallbackBuilder = new Typeface.CustomFallbackBuilder(fontFamilyBuild);
                        } else {
                            customFallbackBuilder.addCustomFallback(fontFamilyBuild);
                        }
                    }
                } catch (Throwable th) {
                    th = th;
                    Log.w("FontUtils", "createMiProTypefaceByFilesFallback build families failed", th);
                }
            }
        } catch (Throwable th2) {
            th = th2;
            customFallbackBuilder = null;
        }
        Typeface.CustomFallbackBuilder customFallbackBuilder2 = customFallbackBuilder;
        if (customFallbackBuilder2 == null) {
            return null;
        }
        try {
            typefaceBuild = (Typeface) ReflectUtils.invokeObject(customFallbackBuilder2.getClass(), customFallbackBuilder2, "originBuild", Typeface.class, new Class[0], new Object[0]);
        } catch (Throwable th3) {
            Log.w("FontUtils", "originBuild reflect failed, fallback to builder.build()", th3);
            typefaceBuild = null;
        }
        if (typefaceBuild == null) {
            try {
                typefaceBuild = customFallbackBuilder2.build();
            } catch (Throwable th4) {
                Log.w("FontUtils", "builder.build failed", th4);
                return null;
            }
        } else {
            try {
                ReflectUtils.setValue(typefaceBuild, "familyName", String[].class, FALLBACK_FONT);
            } catch (Throwable th5) {
                Log.w("FontUtils", "set familyName failed", th5);
            }
        }
        return Typeface.create(typefaceBuild, i, false);
    }

    public static Typeface createMiproTypeface(int i) {
        List allMisansFilePath;
        String strValueOf = String.valueOf(Locale.getDefault());
        String str = strValueOf + "|" + i;
        Map map = sMiProTypefaceCache;
        Typeface typeface = (Typeface) map.get(str);
        if (typeface != null) {
            return typeface;
        }
        try {
            Map map2 = sMiSansLangTagCache;
            String langByMiuiFontPathReflect = (String) map2.get(strValueOf);
            if (TextUtils.isEmpty(langByMiuiFontPathReflect) && (allMisansFilePath = getAllMisansFilePath()) != null && !allMisansFilePath.isEmpty()) {
                langByMiuiFontPathReflect = getLangByMiuiFontPathReflect((String) allMisansFilePath.get(0));
                if (!TextUtils.isEmpty(langByMiuiFontPathReflect)) {
                    map2.put(strValueOf, langByMiuiFontPathReflect);
                }
            }
            if (!TextUtils.isEmpty(langByMiuiFontPathReflect)) {
                Typeface typefaceCreate = Typeface.create("misans-global-" + langByMiuiFontPathReflect, 0);
                Typeface typefaceCreate2 = typefaceCreate != null ? Typeface.create(typefaceCreate, i, false) : null;
                if (typefaceCreate2 != null) {
                    map.put(str, typefaceCreate2);
                    return typefaceCreate2;
                }
            }
        } catch (Throwable th) {
            Log.w("FontUtils", "createMiproTypeface(global) failed, fallback to file builder. weight=" + i, th);
        }
        Typeface typefaceCreateMiProTypefaceByFilesFallback = createMiProTypefaceByFilesFallback(i);
        if (typefaceCreateMiProTypefaceByFilesFallback != null) {
            sMiProTypefaceCache.put(str, typefaceCreateMiProTypefaceByFilesFallback);
        }
        return typefaceCreateMiProTypefaceByFilesFallback;
    }

    public static List getAllMisansFilePath() {
        try {
            return (List) Class.forName("miui.util.font.MultiLangHelper").getMethod("getMiproFileList", Locale.class).invoke(null, Locale.getDefault());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getLangByMiuiFontPathReflect(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return (String) Class.forName("miui.util.font.MultiLangHelper").getMethod("getLangByMiuiFontPath", String.class).invoke(null, str);
        } catch (Throwable th) {
            Log.w("FontUtils", "getLangByMiuiFontPathReflect failed. path=" + str, th);
            return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0049 A[PHI: r1
  0x0049: PHI (r1v3 java.lang.String) = (r1v2 java.lang.String), (r1v11 java.lang.String) binds: [B:3:0x0017, B:9:0x0038] A[DONT_GENERATE, DONT_INLINE]] */
    public static Typeface getMiSans(int i) {
        Map map;
        String string = Locale.getDefault().toString();
        String miSansGlobalPath = (String) miSansFontsPathByLocaleCache.get(string);
        if (TextUtils.isEmpty(miSansGlobalPath)) {
            miSansGlobalPath = getMiSansGlobalPath();
            if (TextUtils.isEmpty(miSansGlobalPath)) {
                Log.w("FontUtils", "failed to getMiuiFontPathByLocale, defaultFontPath is null");
                return null;
            }
            miSansFontsPathByLocaleCache.put(string, miSansGlobalPath);
            if (miSansTypefaceCacheByFontsPath.contains(miSansGlobalPath.hashCode())) {
                map = null;
            } else {
                map = new HashMap();
                miSansTypefaceCacheByFontsPath.put(miSansGlobalPath.hashCode(), map);
            }
        } else {
            map = null;
        }
        if (map == null) {
            map = (Map) miSansTypefaceCacheByFontsPath.get(miSansGlobalPath.hashCode());
        }
        if (map == null) {
            Log.w("FontUtils", "failed to load misans, miSansTypefaceMap is null");
            return null;
        }
        Typeface typefaceCreateMiproTypeface = (Typeface) map.get(String.valueOf(i));
        if (typefaceCreateMiproTypeface == null && (typefaceCreateMiproTypeface = createMiproTypeface(i)) != null) {
            map.put(String.valueOf(i), typefaceCreateMiproTypeface);
        }
        return typefaceCreateMiproTypeface;
    }

    public static String getMiSansGlobalPath() {
        try {
            return (String) Class.forName("miui.util.font.MultiLangHelper").getMethod("getMiuiFontPathByLocale", Locale.class).invoke(null, Locale.getDefault());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isLongDateLanguage(Context context) {
        if (context == null) {
            return false;
        }
        String language = context.getResources().getConfiguration().locale.getLanguage();
        return "bo".equals(language) || "ug".equals(language);
    }

    public static boolean isTibetan(Context context) {
        return context != null && "bo".equals(context.getResources().getConfiguration().locale.getLanguage());
    }
}
