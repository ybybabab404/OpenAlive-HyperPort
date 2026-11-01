package com.airbnb.lottie.parser;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.content.BlurEffect;
import com.airbnb.lottie.parser.moshi.JsonReader;

/* JADX INFO: loaded from: classes.dex */
class BlurEffectParser {
    public static final JsonReader.Options BLUR_EFFECT_NAMES = JsonReader.Options.of("ef");
    public static final JsonReader.Options INNER_BLUR_EFFECT_NAMES = JsonReader.Options.of("ty", "v");

    public static BlurEffect maybeParseInnerEffect(JsonReader jsonReader, LottieComposition lottieComposition) {
        jsonReader.beginObject();
        BlurEffect blurEffect = null;
        while (true) {
            boolean z = false;
            while (true) {
                if (!jsonReader.hasNext()) {
                    jsonReader.endObject();
                    return blurEffect;
                }
                int iSelectName = jsonReader.selectName(INNER_BLUR_EFFECT_NAMES);
                if (iSelectName != 0) {
                    if (iSelectName != 1) {
                        jsonReader.skipName();
                        jsonReader.skipValue();
                    } else if (z) {
                        blurEffect = new BlurEffect(AnimatableValueParser.parseFloat(jsonReader, lottieComposition));
                    } else {
                        jsonReader.skipValue();
                    }
                } else if (jsonReader.nextInt() == 0) {
                    z = true;
                }
            }
        }
    }

    public static BlurEffect parse(JsonReader jsonReader, LottieComposition lottieComposition) {
        BlurEffect blurEffect = null;
        while (jsonReader.hasNext()) {
            if (jsonReader.selectName(BLUR_EFFECT_NAMES) != 0) {
                jsonReader.skipName();
                jsonReader.skipValue();
            } else {
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    BlurEffect blurEffectMaybeParseInnerEffect = maybeParseInnerEffect(jsonReader, lottieComposition);
                    if (blurEffectMaybeParseInnerEffect != null) {
                        blurEffect = blurEffectMaybeParseInnerEffect;
                    }
                }
                jsonReader.endArray();
            }
        }
        return blurEffect;
    }
}
