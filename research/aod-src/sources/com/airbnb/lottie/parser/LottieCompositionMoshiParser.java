package com.airbnb.lottie.parser;

import android.graphics.Rect;
import androidx.collection.LongSparseArray;
import androidx.collection.SparseArrayCompat;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieImageAsset;
import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.model.FontCharacter;
import com.airbnb.lottie.model.Marker;
import com.airbnb.lottie.model.layer.Layer;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.Logger;
import com.airbnb.lottie.utils.Utils;
import com.miui.maml.folme.AnimatedProperty;
import com.xiaomi.onetrack.util.ac;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class LottieCompositionMoshiParser {
    public static final JsonReader.Options NAMES = JsonReader.Options.of(AnimatedProperty.PROPERTY_NAME_W, AnimatedProperty.PROPERTY_NAME_H, "ip", "op", "fr", "v", "layers", "assets", "fonts", "chars", "markers");
    public static JsonReader.Options ASSETS_NAMES = JsonReader.Options.of("id", "layers", AnimatedProperty.PROPERTY_NAME_W, AnimatedProperty.PROPERTY_NAME_H, "p", "u");
    public static final JsonReader.Options FONT_NAMES = JsonReader.Options.of("list");
    public static final JsonReader.Options MARKER_NAMES = JsonReader.Options.of("cm", "tm", "dr");

    /* JADX WARN: Failed to find 'out' block for switch in B:6:0x0044. Please report as an issue. */
    public static LottieComposition parse(JsonReader jsonReader) {
        jsonReader = jsonReader;
        float fDpScale = Utils.dpScale();
        LongSparseArray longSparseArray = new LongSparseArray();
        ArrayList arrayList = new ArrayList();
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        HashMap map3 = new HashMap();
        ArrayList arrayList2 = new ArrayList();
        SparseArrayCompat sparseArrayCompat = new SparseArrayCompat();
        LottieComposition lottieComposition = new LottieComposition();
        jsonReader.beginObject();
        float fNextDouble = 0.0f;
        float fNextDouble2 = 0.0f;
        float fNextDouble3 = 0.0f;
        int iNextInt = 0;
        int iNextInt2 = 0;
        while (jsonReader.hasNext()) {
            switch (jsonReader.selectName(NAMES)) {
                case 0:
                    iNextInt = jsonReader.nextInt();
                    break;
                case 1:
                    iNextInt2 = jsonReader.nextInt();
                    break;
                case 2:
                    fNextDouble = (float) jsonReader.nextDouble();
                    fDpScale = fDpScale;
                    break;
                case 3:
                    fNextDouble2 = ((float) jsonReader.nextDouble()) - 0.01f;
                    fDpScale = fDpScale;
                    break;
                case 4:
                    fNextDouble3 = (float) jsonReader.nextDouble();
                    fDpScale = fDpScale;
                    break;
                case 5:
                    String[] strArrSplit = jsonReader.nextString().split(ac.a);
                    if (!Utils.isAtLeastVersion(Integer.parseInt(strArrSplit[0]), Integer.parseInt(strArrSplit[1]), Integer.parseInt(strArrSplit[2]), 4, 4, 0)) {
                        lottieComposition.addWarning("Lottie only supports bodymovin >= 4.4.0");
                    }
                    break;
                case 6:
                    parseLayers(jsonReader, lottieComposition, arrayList, longSparseArray);
                    break;
                case 7:
                    parseAssets(jsonReader, lottieComposition, map, map2);
                    break;
                case 8:
                    parseFonts(jsonReader, map3);
                    break;
                case 9:
                    parseChars(jsonReader, lottieComposition, sparseArrayCompat);
                    break;
                case 10:
                    parseMarkers(jsonReader, arrayList2);
                    break;
                default:
                    jsonReader.skipName();
                    jsonReader.skipValue();
                    break;
            }
        }
        float f = fDpScale;
        lottieComposition.init(new Rect(0, 0, (int) (iNextInt * f), (int) (iNextInt2 * f)), fNextDouble, fNextDouble2, fNextDouble3, arrayList, longSparseArray, map, map2, sparseArrayCompat, map3, arrayList2);
        return lottieComposition;
    }

    public static void parseAssets(JsonReader jsonReader, LottieComposition lottieComposition, Map map, Map map2) {
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            ArrayList arrayList = new ArrayList();
            LongSparseArray longSparseArray = new LongSparseArray();
            jsonReader.beginObject();
            int iNextInt = 0;
            int iNextInt2 = 0;
            String strNextString = null;
            String strNextString2 = null;
            String strNextString3 = null;
            while (jsonReader.hasNext()) {
                int iSelectName = jsonReader.selectName(ASSETS_NAMES);
                if (iSelectName == 0) {
                    strNextString = jsonReader.nextString();
                } else if (iSelectName == 1) {
                    jsonReader.beginArray();
                    while (jsonReader.hasNext()) {
                        Layer layer = LayerParser.parse(jsonReader, lottieComposition);
                        longSparseArray.put(layer.getId(), layer);
                        arrayList.add(layer);
                    }
                    jsonReader.endArray();
                } else if (iSelectName == 2) {
                    iNextInt = jsonReader.nextInt();
                } else if (iSelectName == 3) {
                    iNextInt2 = jsonReader.nextInt();
                } else if (iSelectName == 4) {
                    strNextString2 = jsonReader.nextString();
                } else if (iSelectName != 5) {
                    jsonReader.skipName();
                    jsonReader.skipValue();
                } else {
                    strNextString3 = jsonReader.nextString();
                }
            }
            jsonReader.endObject();
            if (strNextString2 != null) {
                LottieImageAsset lottieImageAsset = new LottieImageAsset(iNextInt, iNextInt2, strNextString, strNextString2, strNextString3);
                map2.put(lottieImageAsset.getId(), lottieImageAsset);
            } else {
                map.put(strNextString, arrayList);
            }
        }
        jsonReader.endArray();
    }

    public static void parseChars(JsonReader jsonReader, LottieComposition lottieComposition, SparseArrayCompat sparseArrayCompat) {
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            FontCharacter fontCharacter = FontCharacterParser.parse(jsonReader, lottieComposition);
            sparseArrayCompat.put(fontCharacter.hashCode(), fontCharacter);
        }
        jsonReader.endArray();
    }

    public static void parseFonts(JsonReader jsonReader, Map map) {
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            if (jsonReader.selectName(FONT_NAMES) != 0) {
                jsonReader.skipName();
                jsonReader.skipValue();
            } else {
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    Font font = FontParser.parse(jsonReader);
                    map.put(font.getName(), font);
                }
                jsonReader.endArray();
            }
        }
        jsonReader.endObject();
    }

    public static void parseLayers(JsonReader jsonReader, LottieComposition lottieComposition, List list, LongSparseArray longSparseArray) {
        jsonReader.beginArray();
        int i = 0;
        while (jsonReader.hasNext()) {
            Layer layer = LayerParser.parse(jsonReader, lottieComposition);
            if (layer.getLayerType() == Layer.LayerType.IMAGE) {
                i++;
            }
            list.add(layer);
            longSparseArray.put(layer.getId(), layer);
            if (i > 4) {
                Logger.warning("You have " + i + " images. Lottie should primarily be used with shapes. If you are using Adobe Illustrator, convert the Illustrator layers to shape layers.");
            }
        }
        jsonReader.endArray();
    }

    public static void parseMarkers(JsonReader jsonReader, List list) {
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            jsonReader.beginObject();
            float fNextDouble = 0.0f;
            String strNextString = null;
            float fNextDouble2 = 0.0f;
            while (jsonReader.hasNext()) {
                int iSelectName = jsonReader.selectName(MARKER_NAMES);
                if (iSelectName == 0) {
                    strNextString = jsonReader.nextString();
                } else if (iSelectName == 1) {
                    fNextDouble = (float) jsonReader.nextDouble();
                } else if (iSelectName != 2) {
                    jsonReader.skipName();
                    jsonReader.skipValue();
                } else {
                    fNextDouble2 = (float) jsonReader.nextDouble();
                }
            }
            jsonReader.endObject();
            list.add(new Marker(strNextString, fNextDouble, fNextDouble2));
        }
        jsonReader.endArray();
    }
}
