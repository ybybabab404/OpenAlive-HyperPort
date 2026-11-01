package com.miui.clock.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import miuix.core.util.SystemProperties;

/* JADX INFO: loaded from: classes.dex */
@SuppressLint({"NewApi"})
public class FontUtils {
    public static final Map ALLINONE_FONT_FILE_MAP;
    public static final String[] FALLBACK_FONT;
    public static final String FONT_PATH;
    public static final String MI_SANS_PATH = getMiSansFontPath();
    public static final String MI_SANS_ROUNDED_SC_PATH;
    public static final String MI_SANS_ROUNDED_TC_PATH;
    public static final Map mAllInOneFontCache;
    public static final Map mAssetTypefaceMap;
    public static final Map mMisansGlobalTypefaceMap;
    public static final Map mOtherFontTypefaceMap;
    public static final Map miSansFontsPathByLocaleCache;
    public static final Map miSansRoundedTypefaceMap;
    public static final Map miSansTypefaceCacheByFontsPath;
    public static final Map sMiProTypefaceCache;
    public static final Map sMiSansLangTagCache;

    /* JADX INFO: renamed from: $r8$lambda$-aWgw9lSALzWgNP09qnl3cG2PXs, reason: not valid java name */
    public static /* synthetic */ ConcurrentHashMap m655$r8$lambda$aWgw9lSALzWgNP09qnl3cG2PXs(String str) {
        return new ConcurrentHashMap();
    }

    /* JADX INFO: renamed from: $r8$lambda$XEulFome-WlyS5DNEzlN-2p_28w, reason: not valid java name */
    public static /* synthetic */ ConcurrentHashMap m656$r8$lambda$XEulFomeWlyS5DNEzlN2p_28w(String str) {
        return new ConcurrentHashMap();
    }

    static {
        String str = Build.VERSION.SDK_INT > 33 ? "/system/product/" : "/system/";
        FONT_PATH = str;
        MI_SANS_ROUNDED_SC_PATH = str + "fonts/MiSansRoundedSC.ttf";
        MI_SANS_ROUNDED_TC_PATH = str + "fonts/MiSansRoundedTC.ttf";
        miSansFontsPathByLocaleCache = new ConcurrentHashMap();
        miSansTypefaceCacheByFontsPath = new ConcurrentHashMap();
        miSansRoundedTypefaceMap = new ConcurrentHashMap();
        mOtherFontTypefaceMap = new ConcurrentHashMap();
        mMisansGlobalTypefaceMap = new ConcurrentHashMap();
        mAssetTypefaceMap = new ConcurrentHashMap();
        sMiProTypefaceCache = new ConcurrentHashMap();
        sMiSansLangTagCache = new ConcurrentHashMap();
        FALLBACK_FONT = new String[]{"fallback-font"};
        mAllInOneFontCache = new ConcurrentHashMap();
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
        ALLINONE_FONT_FILE_MAP = concurrentHashMap;
        concurrentHashMap.put("miclock-mi-sans-rcf", "MiSansRCF.ttf");
        concurrentHashMap.put("miclock-mi-sans-roundedf", "MiSansRoundedF.ttf");
        concurrentHashMap.put("miclock-mi-sans-squaref", "MiSansSquareF.ttf");
        concurrentHashMap.put("miclock-mi-serif", "MiSerif.ttf");
        concurrentHashMap.put("miclock-mi-haus", "MiHaus.ttf");
    }

    public static void clearClassicTypefaceCache() {
        Log.d("FontUtils", "clear classic clock typeface cache");
    }

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

    public static void createTypeFace(Map map, int i) {
        Typeface typefaceCreateMiproTypeface;
        if (map == null) {
            Log.d("FontUtils", "createTypeFace:  typefaceMap is null ");
        } else {
            if (((Typeface) map.get(String.valueOf(i))) != null || (typefaceCreateMiproTypeface = createMiproTypeface(i)) == null) {
                return;
            }
            map.put(String.valueOf(i), typefaceCreateMiproTypeface);
        }
    }

    public static Typeface createTypefaceWithBypassCache(String str) {
        try {
            File fileFindSystemFontFile = findSystemFontFile(str);
            if (fileFindSystemFontFile == null) {
                Log.d("FontUtils", "createTypefaceWithBypassCache: font file not found for " + str + ", fallback");
                return Typeface.create(str, 0);
            }
            Font fontBuild = new Font.Builder(fileFindSystemFontFile).build();
            Method method = Font.class.getMethod("setFontPathMatched", Boolean.TYPE);
            method.setAccessible(true);
            method.invoke(fontBuild, Boolean.TRUE);
            Typeface typefaceBuild = new Typeface.CustomFallbackBuilder(new FontFamily.Builder(fontBuild).buildVariableFamily()).build();
            Log.d("FontUtils", "createTypefaceWithBypassCache success: " + str + " -> " + fileFindSystemFontFile);
            return typefaceBuild;
        } catch (Exception e) {
            Log.w("FontUtils", "createTypefaceWithBypassCache failed, fallback to Typeface.create: " + str, e);
        }
    }

    public static File findSystemFontFile(String str) {
        String str2 = (String) ALLINONE_FONT_FILE_MAP.get(str);
        if (str2 == null) {
            return null;
        }
        File file = new File("/product/fonts/" + str2);
        if (file.exists() && file.canRead()) {
            return file;
        }
        return null;
    }

    public static Typeface getAestheticsTypeFace(boolean z) {
        return z ? getOtherFont("miclock-commuters-sans-mono") : getOtherFont("miclock-bebas-neue-mono");
    }

    public static Typeface getAllInOneFont(String str) {
        Map map = mAllInOneFontCache;
        Typeface typeface = (Typeface) map.get(str);
        if (typeface != null) {
            return typeface;
        }
        Typeface typefaceCreateTypefaceWithBypassCache = createTypefaceWithBypassCache(str);
        if (typefaceCreateTypefaceWithBypassCache == null) {
            return null;
        }
        Typeface typeface2 = (Typeface) map.putIfAbsent(str, typefaceCreateTypefaceWithBypassCache);
        return typeface2 != null ? typeface2 : typefaceCreateTypefaceWithBypassCache;
    }

    public static List getAllMisansFilePath() {
        try {
            return (List) Class.forName("miui.util.font.MultiLangHelper").getMethod("getMiproFileList", Locale.class).invoke(null, Locale.getDefault());
        } catch (Exception e) {
            Log.d("FontUtils", "getAllMisansFilePath: " + e.getMessage());
            return null;
        }
    }

    public static Typeface getC800(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "C800.ttf");
    }

    public static Typeface getChronicleForEasternArtB(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "ChronicleDisplayCompressed-Semibold.otf");
    }

    public static Typeface getCursiveRegular(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "Cursive-Regular.otf");
    }

    public static Typeface getDelaGothicOne(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "DelaGothicOne-HZ.otf");
    }

    public static Typeface getInterMono(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "InterMono-SemiBold.otf");
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

    public static Typeface getMiHausF(Context context) {
        return getAllInOneFont("miclock-mi-haus");
    }

    public static Typeface getMiHausVF(Context context, int i) {
        return new Typeface.Builder(context.getAssets(), "MiHausVF.ttf").setFontVariationSettings("'wght' " + i).build();
    }

    public static Typeface getMiSans(int i) {
        Map map;
        String string = Locale.getDefault().toString();
        Map map2 = miSansFontsPathByLocaleCache;
        String miSansGlobalPath = (String) map2.get(string);
        if (TextUtils.isEmpty(miSansGlobalPath)) {
            miSansGlobalPath = getMiSansGlobalPath();
            if (TextUtils.isEmpty(miSansGlobalPath)) {
                Log.w("FontUtils", "failed to getMiuiFontPathByLocale, defaultFontPath is null");
                return null;
            }
            map2.put(string, miSansGlobalPath);
            map = (Map) miSansTypefaceCacheByFontsPath.computeIfAbsent(miSansGlobalPath, new Function() { // from class: com.miui.clock.utils.FontUtils$$ExternalSyntheticLambda1
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return FontUtils.m656$r8$lambda$XEulFomeWlyS5DNEzlN2p_28w((String) obj);
                }
            });
        } else {
            map = null;
        }
        if (map == null) {
            map = (Map) miSansTypefaceCacheByFontsPath.get(miSansGlobalPath);
        }
        if (map == null) {
            Log.w("FontUtils", "failed to load misans, miSansTypefaceMap is null");
            return null;
        }
        Typeface typefaceCreateMiproTypeface = (Typeface) map.get(String.valueOf(i));
        if (typefaceCreateMiproTypeface == null) {
            typefaceCreateMiproTypeface = createMiproTypeface(i);
            if (typefaceCreateMiproTypeface != null) {
                map.put(String.valueOf(i), typefaceCreateMiproTypeface);
            }
            Log.d("FontUtils", "createMiproTypeface MiSans: " + i + " - typeface：" + typefaceCreateMiproTypeface);
        }
        return typefaceCreateMiproTypeface;
    }

    public static String getMiSansFontPath() {
        return SystemProperties.get("ro.miui.ui.font.mi_font_path", "/system/fonts/MiSansVF.ttf");
    }

    public static Typeface getMiSansGlobal() {
        String miSansGlobalPath = getMiSansGlobalPath();
        Map map = mMisansGlobalTypefaceMap;
        Typeface typeface = (Typeface) map.get(miSansGlobalPath);
        if (typeface != null || TextUtils.isEmpty(miSansGlobalPath)) {
            return typeface;
        }
        Typeface typefaceBuild = new Typeface.Builder(miSansGlobalPath).setFontVariationSettings("'wght' 630").build();
        map.put(miSansGlobalPath, typefaceBuild);
        return typefaceBuild;
    }

    public static String getMiSansGlobalPath() {
        try {
            return (String) Class.forName("miui.util.font.MultiLangHelper").getMethod("getMiuiFontPathByLocale", Locale.class).invoke(null, Locale.getDefault());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Typeface getMiSansRCF(Context context) {
        return getAllInOneFont("miclock-mi-sans-rcf");
    }

    public static Typeface getMiSansRCFVF(Context context, int i) {
        return new Typeface.Builder(context.getAssets(), "MiSansRCFVF.ttf").setFontVariationSettings("'wght' " + i).build();
    }

    public static Typeface getMiSansRounded(int i, boolean z) {
        String str = i + String.valueOf(z);
        Map map = miSansRoundedTypefaceMap;
        Typeface typeface = (Typeface) map.get(str);
        if (typeface != null) {
            return typeface;
        }
        Typeface typefaceBuild = new Typeface.Builder(z ? MI_SANS_ROUNDED_SC_PATH : MI_SANS_ROUNDED_TC_PATH).setFontVariationSettings("'wght' " + i).build();
        map.put(str, typefaceBuild);
        return typefaceBuild;
    }

    public static Typeface getMiSansRoundedF(Context context) {
        return getAllInOneFont("miclock-mi-sans-roundedf");
    }

    public static Typeface getMiSansRoundedFVF(Context context, int i) {
        return new Typeface.Builder(context.getAssets(), "MiSansRoundedFVF.ttf").setFontVariationSettings("'wght' " + i).build();
    }

    public static Typeface getMiSansSquareF(Context context) {
        return getAllInOneFont("miclock-mi-sans-squaref");
    }

    public static Typeface getMiSansSquareFVF(Context context, int i) {
        return new Typeface.Builder(context.getAssets(), "MiSansSquareFVF.ttf").setFontVariationSettings("'wght' " + i).build();
    }

    public static Typeface getMiSerifF(Context context) {
        return getAllInOneFont("miclock-mi-serif");
    }

    public static Typeface getMiSerifFVF(Context context, int i) {
        return new Typeface.Builder(context.getAssets(), "MiSerifFVF.ttf").setFontVariationSettings("'wght' " + i).build();
    }

    public static Typeface getMiSerifForEasternArtB(Context context) {
        return getOtherFont("miclock-miserif-sc-vf");
    }

    public static Typeface getMiSerifTcVfByWeight(int i) {
        Map map = mOtherFontTypefaceMap;
        Typeface typeface = (Typeface) map.get("miclock-miserif-tc-vf");
        if (typeface != null) {
            return typeface;
        }
        Typeface typefaceCreate = Typeface.create(Typeface.create("miclock-miserif-tc-vf", 0), i, false);
        map.put("miclock-miserif-tc-vf", typefaceCreate);
        return typefaceCreate;
    }

    public static Typeface getMono() {
        return getOtherFont("miclock-michroma-mono");
    }

    public static Typeface getNeuRounded(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "NeuRounded.otf");
    }

    public static Typeface getNeueMatic() {
        return Typeface.create("miclock-neue-matic-compressed-black", 0);
    }

    public static Typeface getOtherFont(String str) {
        Map map = mOtherFontTypefaceMap;
        Typeface typeface = (Typeface) map.get(str);
        if (typeface != null) {
            return typeface;
        }
        Typeface typefaceCreate = Typeface.create(str, 0);
        map.put(str, typefaceCreate);
        return typefaceCreate;
    }

    public static Typeface getSilkSerifRegular() {
        return getOtherFont("miclock-silk-serif-regular");
    }

    public static Typeface getSmallFontTypeFace(int i) {
        return i != 22 ? getMiSans(380) : getMiSans(330);
    }

    public static Typeface getTemperatureUnitTypeFace(int i) {
        return i != 22 ? getMiSans(520) : getMiSans(330);
    }

    public static boolean isBo() {
        return "bo".equals(Locale.getDefault().getLanguage());
    }

    public static boolean isCharHasChinese(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.toString(str.charAt(i)).matches("[周週一二三四五六日]")) {
                return false;
            }
        }
        return true;
    }

    public static boolean isCharHasSoundmark(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.toString(str.charAt(i)).matches("[\\u0061-\\u007A\\u0041-\\u005A]")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isChinese(String str) {
        return TextUtils.isEmpty(str) || str.startsWith("zh");
    }

    public static boolean isFrLanguage() {
        return "fr".equals(Locale.getDefault().getLanguage());
    }

    public static boolean isTraditionalChinese() {
        return TextUtils.equals("zh_TW", Locale.getDefault().toString()) || TextUtils.equals("zh_HK", Locale.getDefault().toString());
    }

    public static boolean isTypefaceSupport(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.toString(str.charAt(i)).matches("[\\u0021-\\u00FF\\u0100-\\u017F]") && !Character.toString(str.charAt(i)).matches("[周週一二三四五六日]")) {
                return false;
            }
        }
        return true;
    }

    public static boolean isZh() {
        return Locale.CHINESE.getLanguage().equals(Locale.getDefault().getLanguage());
    }

    public static boolean isZhOrEn() {
        return Locale.ENGLISH.getLanguage().equals(Locale.getDefault().getLanguage()) || Locale.CHINESE.getLanguage().equals(Locale.getDefault().getLanguage());
    }

    public static void preInitFont() {
        Map map;
        getSmallFontTypeFace(22);
        getSmallFontTypeFace(21);
        String string = Locale.getDefault().toString();
        Map map2 = miSansFontsPathByLocaleCache;
        if (TextUtils.isEmpty((String) map2.get(string))) {
            String miSansGlobalPath = getMiSansGlobalPath();
            if (TextUtils.isEmpty(miSansGlobalPath)) {
                Log.w("FontUtils", "failed to getMiuiFontPathByLocale, defaultFontPath is null");
                return;
            } else {
                map2.put(string, miSansGlobalPath);
                map = (Map) miSansTypefaceCacheByFontsPath.computeIfAbsent(miSansGlobalPath, new Function() { // from class: com.miui.clock.utils.FontUtils$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return FontUtils.m655$r8$lambda$aWgw9lSALzWgNP09qnl3cG2PXs((String) obj);
                    }
                });
            }
        } else {
            map = null;
        }
        createTypeFace(map, 250);
        createTypeFace(map, 280);
        createTypeFace(map, 305);
        createTypeFace(map, 330);
        createTypeFace(map, 380);
        createTypeFace(map, 380);
        createTypeFace(map, 430);
        createTypeFace(map, 450);
        createTypeFace(map, 520);
        String miSansGlobalPath2 = getMiSansGlobalPath();
        Map map3 = mMisansGlobalTypefaceMap;
        if (((Typeface) map3.get(miSansGlobalPath2)) != null || TextUtils.isEmpty(miSansGlobalPath2)) {
            return;
        }
        map3.put(miSansGlobalPath2, new Typeface.Builder(miSansGlobalPath2).setFontVariationSettings("'wght' 630").build());
    }

    public static boolean shouldScaleMultilingual(int i) {
        if (i != 201 && i != 203 && i != 204 && i != 206 && i != 207) {
            return false;
        }
        String language = Locale.getDefault().getLanguage();
        return "bo".equals(language) || "ug".equals(language) || "my".equals(language);
    }
}
