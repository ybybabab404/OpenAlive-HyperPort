package com.miui.lockscreeninfo.utils;

import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.util.Log;
import com.miui.lockscreeninfo.ReflectUtils;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: FontUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class FontUtils {
    public static final Companion Companion = new Companion(null);
    public static final HashMap miSansFontsPathByLocaleCache = new HashMap();
    public static final HashMap miSansTypefaceCacheByFontsPath = new HashMap();
    public static final ConcurrentHashMap sMiProTypefaceCache = new ConcurrentHashMap();
    public static final ConcurrentHashMap sMiSansLangTagCache = new ConcurrentHashMap();
    public static final String[] FALLBACK_FONT = {"fallback-font"};

    /* JADX INFO: compiled from: FontUtils.kt */
    @Metadata
    @SourceDebugExtension
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static final HashMap getMiSans$lambda$0(Function1 tmp0, Object obj) {
            Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
            return (HashMap) tmp0.invoke(obj);
        }

        public final Typeface createMiProTypeface(int i) {
            String string = Locale.getDefault().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            String str = string + '|' + i;
            Typeface typeface = (Typeface) FontUtils.sMiProTypefaceCache.get(str);
            if (typeface != null) {
                return typeface;
            }
            try {
                String langByMiuiFontPathReflect = (String) FontUtils.sMiSansLangTagCache.get(string);
                if (langByMiuiFontPathReflect == null || langByMiuiFontPathReflect.length() == 0) {
                    List allMiSansFilePath = getAllMiSansFilePath();
                    if (allMiSansFilePath != null && !allMiSansFilePath.isEmpty() && (langByMiuiFontPathReflect = getLangByMiuiFontPathReflect((String) allMiSansFilePath.get(0))) != null && langByMiuiFontPathReflect.length() != 0) {
                        FontUtils.sMiSansLangTagCache.put(string, langByMiuiFontPathReflect);
                    }
                }
                if (langByMiuiFontPathReflect != null && langByMiuiFontPathReflect.length() != 0) {
                    Typeface typefaceCreate = Typeface.create("misans-global-" + langByMiuiFontPathReflect, 0);
                    Typeface typefaceCreate2 = typefaceCreate != null ? Typeface.create(typefaceCreate, i, false) : null;
                    if (typefaceCreate2 != null) {
                        FontUtils.sMiProTypefaceCache.put(str, typefaceCreate2);
                        return typefaceCreate2;
                    }
                }
            } catch (Throwable th) {
                Log.w("FontUtils", "createMiProTypeface(global) failed, fallback to file builder. weight=" + i, th);
            }
            Typeface typefaceCreateMiProTypefaceByFilesFallback = createMiProTypefaceByFilesFallback(i);
            if (typefaceCreateMiProTypefaceByFilesFallback != null) {
                FontUtils.sMiProTypefaceCache.put(str, typefaceCreateMiProTypefaceByFilesFallback);
            }
            return typefaceCreateMiProTypefaceByFilesFallback;
        }

        public final Typeface createMiProTypefaceByFilesFallback(int i) {
            Throwable th;
            Typeface.CustomFallbackBuilder customFallbackBuilder;
            Typeface typefaceBuild;
            List<String> allMiSansFilePath = getAllMiSansFilePath();
            if (allMiSansFilePath == null || allMiSansFilePath.isEmpty()) {
                Log.w("FontUtils", "createMiProTypefaceByFilesFallback: miProFiles is empty");
                return null;
            }
            try {
                customFallbackBuilder = null;
                for (String str : allMiSansFilePath) {
                    try {
                        if (str != null && str.length() != 0 && new File(str).exists()) {
                            Font.Builder builder = new Font.Builder(new File(str));
                            builder.setFontVariationSettings("'wght' " + i);
                            FontFamily fontFamilyBuild = new FontFamily.Builder(builder.build()).build();
                            Intrinsics.checkNotNullExpressionValue(fontFamilyBuild, "build(...)");
                            if (customFallbackBuilder == null) {
                                customFallbackBuilder = new Typeface.CustomFallbackBuilder(fontFamilyBuild);
                            } else {
                                customFallbackBuilder.addCustomFallback(fontFamilyBuild);
                            }
                        }
                    } catch (Throwable th2) {
                        th = th2;
                        Log.w("FontUtils", "createMiProTypefaceByFilesFallback build families failed", th);
                    }
                }
            } catch (Throwable th3) {
                th = th3;
                customFallbackBuilder = null;
            }
            Typeface.CustomFallbackBuilder customFallbackBuilder2 = customFallbackBuilder;
            if (customFallbackBuilder2 == null) {
                return null;
            }
            try {
                typefaceBuild = (Typeface) ReflectUtils.invokeObject(customFallbackBuilder2.getClass(), customFallbackBuilder2, "originBuild", Typeface.class, new Class[0], new Object[0]);
            } catch (Throwable th4) {
                Log.w("FontUtils", "originBuild reflect failed, fallback to builder.build()", th4);
                typefaceBuild = null;
            }
            if (typefaceBuild == null) {
                try {
                    typefaceBuild = customFallbackBuilder2.build();
                } catch (Throwable th5) {
                    Log.w("FontUtils", "builder.build failed", th5);
                    return null;
                }
            } else {
                try {
                    ReflectUtils.setValue(typefaceBuild, "familyName", String[].class, FontUtils.FALLBACK_FONT);
                } catch (Throwable th6) {
                    Log.w("FontUtils", "set familyName failed", th6);
                }
            }
            return Typeface.create(typefaceBuild, i, false);
        }

        public final List getAllMiSansFilePath() {
            try {
                Object objInvoke = Class.forName("miui.util.font.MultiLangHelper").getMethod("getMiproFileList", Locale.class).invoke(null, Locale.getDefault());
                Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.collections.List<kotlin.String>");
                return (List) objInvoke;
            } catch (Exception e) {
                Log.d("BaseTextView", "getAllMisansFilePath: " + e.getMessage());
                return null;
            }
        }

        public final String getLangByMiuiFontPathReflect(String str) {
            if (str != null && str.length() != 0) {
                try {
                    Object objInvoke = Class.forName("miui.util.font.MultiLangHelper").getMethod("getLangByMiuiFontPath", String.class).invoke(null, str);
                    if (objInvoke instanceof String) {
                        return (String) objInvoke;
                    }
                    return null;
                } catch (Throwable th) {
                    Log.w("FontUtils", "getLangByMiuiFontPathReflect failed. path=" + str, th);
                }
            }
            return null;
        }

        public final Typeface getMiSans(int i) {
            Map map;
            String string = Locale.getDefault().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            String miSansGlobalPath = (String) FontUtils.miSansFontsPathByLocaleCache.get(string);
            if (miSansGlobalPath == null || StringsKt.isBlank(miSansGlobalPath)) {
                miSansGlobalPath = getMiSansGlobalPath();
                if (miSansGlobalPath == null || StringsKt.isBlank(miSansGlobalPath)) {
                    Log.w("FontUtils", "failed to getMiuiFontPathByLocale, defaultFontPath is null");
                    return null;
                }
                FontUtils.miSansFontsPathByLocaleCache.put(string, miSansGlobalPath);
                HashMap map2 = FontUtils.miSansTypefaceCacheByFontsPath;
                final FontUtils$Companion$getMiSans$1 fontUtils$Companion$getMiSans$1 = new Function1<String, HashMap<String, Typeface>>() { // from class: com.miui.lockscreeninfo.utils.FontUtils$Companion$getMiSans$1
                    @Override // kotlin.jvm.functions.Function1
                    public final HashMap invoke(String it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return new HashMap();
                    }
                };
                map = (Map) map2.computeIfAbsent(miSansGlobalPath, new Function() { // from class: com.miui.lockscreeninfo.utils.FontUtils$Companion$$ExternalSyntheticLambda0
                    @Override // java.util.function.Function
                    public final Object apply(Object obj) {
                        return FontUtils.Companion.getMiSans$lambda$0(fontUtils$Companion$getMiSans$1, obj);
                    }
                });
            } else {
                map = null;
            }
            if (map == null) {
                map = (Map) FontUtils.miSansTypefaceCacheByFontsPath.get(miSansGlobalPath);
            }
            if (map == null) {
                Log.w("FontUtils", "failed to load miSans, miSansTypefaceMap is null");
                return null;
            }
            Typeface typeface = (Typeface) map.get(String.valueOf(i));
            if (typeface != null) {
                return typeface;
            }
            Typeface typefaceCreateMiProTypeface = createMiProTypeface(i);
            if (typefaceCreateMiProTypeface != null) {
                map.put(String.valueOf(i), typefaceCreateMiProTypeface);
            }
            Log.d("FontUtils", "createMiProTypeface MiSans: " + i + " - typeface：" + typefaceCreateMiProTypeface);
            return typefaceCreateMiProTypeface;
        }

        public final String getMiSansGlobalPath() {
            try {
                Object objInvoke = Class.forName("miui.util.font.MultiLangHelper").getMethod("getMiuiFontPathByLocale", Locale.class).invoke(null, Locale.getDefault());
                Intrinsics.checkNotNull(objInvoke, "null cannot be cast to non-null type kotlin.String");
                return (String) objInvoke;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}
