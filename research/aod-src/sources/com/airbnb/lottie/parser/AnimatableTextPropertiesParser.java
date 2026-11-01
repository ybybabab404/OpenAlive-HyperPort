package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.parser.moshi.JsonReader;

/* JADX INFO: loaded from: classes.dex */
public class AnimatableTextPropertiesParser {
    public static final JsonReader.Options PROPERTIES_NAMES = JsonReader.Options.of("a");
    public static final JsonReader.Options ANIMATABLE_PROPERTIES_NAMES = JsonReader.Options.of("fc", "sc", "sw", "t");

    public static AnimatableTextProperties parse(JsonReader jsonReader, LottieComposition lottieComposition) {
        jsonReader.beginObject();
        AnimatableTextProperties animatableTextProperties = null;
        while (jsonReader.hasNext()) {
            if (jsonReader.selectName(PROPERTIES_NAMES) != 0) {
                jsonReader.skipName();
                jsonReader.skipValue();
            } else {
                animatableTextProperties = parseAnimatableTextProperties(jsonReader, lottieComposition);
            }
        }
        jsonReader.endObject();
        return animatableTextProperties == null ? new AnimatableTextProperties(null, null, null, null) : animatableTextProperties;
    }

    public static AnimatableTextProperties parseAnimatableTextProperties(JsonReader jsonReader, LottieComposition lottieComposition) {
        jsonReader.beginObject();
        AnimatableColorValue color = null;
        AnimatableColorValue color2 = null;
        AnimatableFloatValue animatableFloatValue = null;
        AnimatableFloatValue animatableFloatValue2 = null;
        while (jsonReader.hasNext()) {
            int iSelectName = jsonReader.selectName(ANIMATABLE_PROPERTIES_NAMES);
            if (iSelectName == 0) {
                color = AnimatableValueParser.parseColor(jsonReader, lottieComposition);
            } else if (iSelectName == 1) {
                color2 = AnimatableValueParser.parseColor(jsonReader, lottieComposition);
            } else if (iSelectName == 2) {
                animatableFloatValue = AnimatableValueParser.parseFloat(jsonReader, lottieComposition);
            } else if (iSelectName != 3) {
                jsonReader.skipName();
                jsonReader.skipValue();
            } else {
                animatableFloatValue2 = AnimatableValueParser.parseFloat(jsonReader, lottieComposition);
            }
        }
        jsonReader.endObject();
        return new AnimatableTextProperties(color, color2, animatableFloatValue, animatableFloatValue2);
    }
}
