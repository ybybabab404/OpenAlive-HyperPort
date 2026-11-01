package com.miui.clock.utils;

import android.content.Context;
import android.util.Log;
import com.xiaomi.onetrack.api.am;
import com.xiaomi.onetrack.util.ac;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: FontSetParser.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class FontSetParser {
    public static final FontSetParser INSTANCE = new FontSetParser();
    public static volatile Map fontSetDataMap = new LinkedHashMap();

    public static final FontSetData loadFromAssets(Context context, String fileName) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        FontSetData fontSetData = (FontSetData) fontSetDataMap.get(fileName);
        if (fontSetData != null) {
            return fontSetData;
        }
        FontSetParser fontSetParser = INSTANCE;
        synchronized (fontSetParser) {
            FontSetData fontSetData2 = (FontSetData) fontSetDataMap.get(fileName);
            if (fontSetData2 != null) {
                return fontSetData2;
            }
            try {
                InputStream inputStreamOpen = context.getAssets().open(fileName);
                Intrinsics.checkNotNullExpressionValue(inputStreamOpen, "open(...)");
                FontSetData fontSetData3 = fontSetParser.parse(inputStreamOpen);
                fontSetDataMap.put(fileName, fontSetData3);
                Log.d("FontSetParser", "FontSetData 加载成功 (fileName=" + fileName + "): ratio=" + fontSetData3.getRatioMap().size() + " 条, ratioColon=" + fontSetData3.getRatioColonMap().size() + " 条, height=" + fontSetData3.getHeightMap().size() + " 条");
                return fontSetData3;
            } catch (Exception e) {
                Log.e("FontSetParser", "加载 font_set.xml 失败: " + fileName, e);
                return null;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r15v0 */
    /* JADX WARN: Type inference failed for: r15v1, types: [kotlin.Pair] */
    /* JADX WARN: Type inference failed for: r15v10 */
    /* JADX WARN: Type inference failed for: r15v11 */
    /* JADX WARN: Type inference failed for: r15v12 */
    /* JADX WARN: Type inference failed for: r15v13 */
    /* JADX WARN: Type inference failed for: r15v14 */
    /* JADX WARN: Type inference failed for: r15v15 */
    /* JADX WARN: Type inference failed for: r15v16 */
    /* JADX WARN: Type inference failed for: r15v2 */
    /* JADX WARN: Type inference failed for: r15v4 */
    /* JADX WARN: Type inference failed for: r15v5 */
    /* JADX WARN: Type inference failed for: r15v6 */
    /* JADX WARN: Type inference failed for: r15v7 */
    /* JADX WARN: Type inference failed for: r15v8 */
    /* JADX WARN: Type inference failed for: r15v9 */
    public final FontSetData parse(InputStream inputStream) throws Throwable {
        InputStream inputStream2;
        boolean z;
        ?? pair;
        ?? r15;
        int i = 1;
        TreeMap treeMap = new TreeMap();
        TreeMap treeMap2 = new TreeMap();
        TreeMap treeMap3 = new TreeMap();
        try {
            XmlPullParserFactory xmlPullParserFactoryNewInstance = XmlPullParserFactory.newInstance();
            xmlPullParserFactoryNewInstance.setNamespaceAware(false);
            XmlPullParser xmlPullParserNewPullParser = xmlPullParserFactoryNewInstance.newPullParser();
            inputStream2 = inputStream;
            try {
                try {
                    xmlPullParserNewPullParser.setInput(new InputStreamReader(inputStream2, StandardCharsets.UTF_8));
                    int eventType = xmlPullParserNewPullParser.getEventType();
                    int i2 = 0;
                    float[] fArr = null;
                    Float floatOrNull = null;
                    Float floatOrNull2 = null;
                    float[] fArr2 = null;
                    ?? r16 = 0;
                    while (eventType != i) {
                        if (eventType == 2) {
                            String name = xmlPullParserNewPullParser.getName();
                            if (Intrinsics.areEqual(name, "entry")) {
                                String attributeValue = xmlPullParserNewPullParser.getAttributeValue(null, "ratio");
                                String attributeValue2 = xmlPullParserNewPullParser.getAttributeValue(null, "ratioColon");
                                String attributeValue3 = xmlPullParserNewPullParser.getAttributeValue(null, "height");
                                if (attributeValue != null) {
                                    floatOrNull = StringsKt.toFloatOrNull(attributeValue);
                                    fArr = null;
                                    floatOrNull2 = null;
                                    fArr2 = null;
                                } else {
                                    if (attributeValue2 != null) {
                                        floatOrNull2 = StringsKt.toFloatOrNull(attributeValue2);
                                        fArr = null;
                                        floatOrNull = null;
                                        fArr2 = null;
                                    } else if (attributeValue3 != null) {
                                        List listSplit$default = StringsKt.split$default(attributeValue3, new String[]{ac.b}, false, 0, 6, null);
                                        if (listSplit$default.size() == 2) {
                                            Float floatOrNull3 = StringsKt.toFloatOrNull((String) listSplit$default.get(0));
                                            Float floatOrNull4 = StringsKt.toFloatOrNull((String) listSplit$default.get(1));
                                            if (floatOrNull3 != null && floatOrNull4 != null) {
                                                pair = new Pair(floatOrNull3, floatOrNull4);
                                            }
                                        }
                                        pair = r16;
                                        pair = r16;
                                        pair = r16;
                                        pair = r16;
                                        fArr = null;
                                        floatOrNull = null;
                                        floatOrNull2 = null;
                                        fArr2 = null;
                                        r15 = pair;
                                    } else {
                                        fArr = null;
                                        fArr2 = null;
                                        r15 = r16;
                                    }
                                    i = 1;
                                    i2 = 0;
                                    r16 = r15;
                                    z = false;
                                }
                                r15 = fArr2;
                                i = 1;
                                i2 = 0;
                                r16 = r15;
                                z = false;
                            } else if (Intrinsics.areEqual(name, am.t) && xmlPullParserNewPullParser.next() == 4) {
                                String text = xmlPullParserNewPullParser.getText();
                                Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
                                List listSplit$default2 = StringsKt.split$default(StringsKt.trim(text).toString(), new String[]{ac.b}, false, 0, 6, null);
                                if (listSplit$default2.size() == 2) {
                                    Float floatOrNull5 = StringsKt.toFloatOrNull(StringsKt.trim((String) listSplit$default2.get(0)).toString());
                                    Float floatOrNull6 = StringsKt.toFloatOrNull(StringsKt.trim((String) listSplit$default2.get(1)).toString());
                                    if (floatOrNull5 != null && floatOrNull6 != null) {
                                        z = false;
                                        float[] fArr3 = {floatOrNull5.floatValue(), floatOrNull6.floatValue()};
                                        if (r16 != 0) {
                                            treeMap3.put(r16.getSecond(), fArr3);
                                            i = 1;
                                        } else {
                                            i = 1;
                                            int i3 = i2 + 1;
                                            if (i3 != 1) {
                                                if (i3 == 2) {
                                                    fArr2 = fArr3;
                                                }
                                                i2 = i3;
                                            } else {
                                                i2 = i3;
                                                fArr = fArr3;
                                            }
                                        }
                                    }
                                }
                            }
                            eventType = xmlPullParserNewPullParser.next();
                            r16 = r16;
                        } else if (eventType == 3 && Intrinsics.areEqual(xmlPullParserNewPullParser.getName(), "entry") && fArr != null) {
                            if (floatOrNull != null) {
                                treeMap.put(floatOrNull, new Pair(fArr, fArr2));
                            } else if (floatOrNull2 != null) {
                                treeMap2.put(floatOrNull2, new Pair(fArr, fArr2));
                            }
                        }
                        i = 1;
                        r16 = r16;
                        z = false;
                        eventType = xmlPullParserNewPullParser.next();
                        r16 = r16;
                    }
                    Log.d("FontSetParser", "解析完成: ratio=" + treeMap.size() + " 条, ratioColon=" + treeMap2.size() + " 条, height=" + treeMap3.size() + " 条");
                } catch (Exception e) {
                    e = e;
                    Log.e("FontSetParser", "解析 XML 失败", e);
                }
            } catch (Throwable th) {
                th = th;
                inputStream2.close();
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            inputStream2 = inputStream;
        } catch (Throwable th2) {
            th = th2;
            inputStream2 = inputStream;
            inputStream2.close();
            throw th;
        }
        inputStream2.close();
        return new FontSetData(treeMap, treeMap2, treeMap3);
    }
}
