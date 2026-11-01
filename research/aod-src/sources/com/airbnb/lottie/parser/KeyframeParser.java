package com.airbnb.lottie.parser;

import android.graphics.PointF;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import androidx.collection.SparseArrayCompat;
import androidx.core.view.animation.PathInterpolatorCompat;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.parser.moshi.JsonReader;
import com.airbnb.lottie.utils.MiscUtils;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.maml.folme.AnimatedTarget;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
class KeyframeParser {
    public static SparseArrayCompat pathInterpolatorCache;
    public static final Interpolator LINEAR_INTERPOLATOR = new LinearInterpolator();
    public static JsonReader.Options NAMES = JsonReader.Options.of("t", "s", "e", "o", "i", AnimatedProperty.PROPERTY_NAME_H, AnimatedTarget.STATE_TAG_TO, "ti");
    public static JsonReader.Options INTERPOLATOR_NAMES = JsonReader.Options.of(AnimatedProperty.PROPERTY_NAME_X, AnimatedProperty.PROPERTY_NAME_Y);

    public static WeakReference getInterpolator(int i) {
        WeakReference weakReference;
        synchronized (KeyframeParser.class) {
            weakReference = (WeakReference) pathInterpolatorCache().get(i);
        }
        return weakReference;
    }

    public static Interpolator interpolatorFor(PointF pointF, PointF pointF2) {
        Interpolator interpolatorCreate;
        pointF.x = MiscUtils.clamp(pointF.x, -1.0f, 1.0f);
        pointF.y = MiscUtils.clamp(pointF.y, -100.0f, 100.0f);
        pointF2.x = MiscUtils.clamp(pointF2.x, -1.0f, 1.0f);
        float fClamp = MiscUtils.clamp(pointF2.y, -100.0f, 100.0f);
        pointF2.y = fClamp;
        int iHashFor = Utils.hashFor(pointF.x, pointF.y, pointF2.x, fClamp);
        WeakReference interpolator = getInterpolator(iHashFor);
        Interpolator interpolator2 = interpolator != null ? (Interpolator) interpolator.get() : null;
        if (interpolator != null && interpolator2 != null) {
            return interpolator2;
        }
        try {
            interpolatorCreate = PathInterpolatorCompat.create(pointF.x, pointF.y, pointF2.x, pointF2.y);
        } catch (IllegalArgumentException e) {
            interpolatorCreate = "The Path cannot loop back on itself.".equals(e.getMessage()) ? PathInterpolatorCompat.create(Math.min(pointF.x, 1.0f), pointF.y, Math.max(pointF2.x, 0.0f), pointF2.y) : new LinearInterpolator();
        }
        try {
            putInterpolator(iHashFor, new WeakReference(interpolatorCreate));
        } catch (ArrayIndexOutOfBoundsException unused) {
        }
        return interpolatorCreate;
    }

    public static Keyframe parse(JsonReader jsonReader, LottieComposition lottieComposition, float f, ValueParser valueParser, boolean z, boolean z2) {
        if (z && z2) {
            return parseMultiDimensionalKeyframe(lottieComposition, jsonReader, f, valueParser);
        }
        return z ? parseKeyframe(lottieComposition, jsonReader, f, valueParser) : parseStaticValue(jsonReader, f, valueParser);
    }

    public static Keyframe parseKeyframe(LottieComposition lottieComposition, JsonReader jsonReader, float f, ValueParser valueParser) {
        Interpolator interpolatorInterpolatorFor;
        Object obj;
        jsonReader.beginObject();
        PointF pointFJsonToPoint = null;
        Object obj2 = null;
        Object obj3 = null;
        PointF pointFJsonToPoint2 = null;
        PointF pointFJsonToPoint3 = null;
        float fNextDouble = 0.0f;
        boolean z = false;
        PointF pointFJsonToPoint4 = null;
        while (jsonReader.hasNext()) {
            switch (jsonReader.selectName(NAMES)) {
                case 0:
                    fNextDouble = (float) jsonReader.nextDouble();
                    break;
                case 1:
                    obj3 = valueParser.parse(jsonReader, f);
                    break;
                case 2:
                    obj2 = valueParser.parse(jsonReader, f);
                    break;
                case 3:
                    pointFJsonToPoint = JsonUtils.jsonToPoint(jsonReader, 1.0f);
                    break;
                case 4:
                    pointFJsonToPoint4 = JsonUtils.jsonToPoint(jsonReader, 1.0f);
                    break;
                case 5:
                    z = jsonReader.nextInt() == 1;
                    break;
                case 6:
                    pointFJsonToPoint2 = JsonUtils.jsonToPoint(jsonReader, f);
                    break;
                case 7:
                    pointFJsonToPoint3 = JsonUtils.jsonToPoint(jsonReader, f);
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        jsonReader.endObject();
        if (z) {
            interpolatorInterpolatorFor = LINEAR_INTERPOLATOR;
            obj = obj3;
        } else {
            interpolatorInterpolatorFor = (pointFJsonToPoint == null || pointFJsonToPoint4 == null) ? LINEAR_INTERPOLATOR : interpolatorFor(pointFJsonToPoint, pointFJsonToPoint4);
            obj = obj2;
        }
        Keyframe keyframe = new Keyframe(lottieComposition, obj3, obj, interpolatorInterpolatorFor, fNextDouble, null);
        keyframe.pathCp1 = pointFJsonToPoint2;
        keyframe.pathCp2 = pointFJsonToPoint3;
        return keyframe;
    }

    /* JADX WARN: Code duplicated, block: B:93:0x01e2  */
    public static Keyframe parseMultiDimensionalKeyframe(LottieComposition lottieComposition, JsonReader jsonReader, float f, ValueParser valueParser) {
        Interpolator interpolatorInterpolatorFor;
        Interpolator interpolatorInterpolatorFor2;
        Interpolator interpolatorInterpolatorFor3;
        Object obj;
        Interpolator interpolator;
        PointF pointF;
        Keyframe keyframe;
        PointF pointF2;
        boolean z;
        float f2;
        jsonReader.beginObject();
        boolean z2 = false;
        PointF pointFJsonToPoint = null;
        PointF pointFJsonToPoint2 = null;
        PointF pointF3 = null;
        Object obj2 = null;
        PointF pointF4 = null;
        PointF pointF5 = null;
        PointF pointF6 = null;
        PointF pointFJsonToPoint3 = null;
        PointF pointFJsonToPoint4 = null;
        float fNextDouble = 0.0f;
        Object obj3 = null;
        while (jsonReader.hasNext()) {
            switch (jsonReader.selectName(NAMES)) {
                case 0:
                    fNextDouble = (float) jsonReader.nextDouble();
                    break;
                case 1:
                    obj2 = valueParser.parse(jsonReader, f);
                    break;
                case 2:
                    obj3 = valueParser.parse(jsonReader, f);
                    break;
                case 3:
                    boolean z3 = z2;
                    if (jsonReader.peek() == JsonReader.Token.BEGIN_OBJECT) {
                        jsonReader.beginObject();
                        float fNextDouble2 = 0.0f;
                        float fNextDouble3 = 0.0f;
                        float fNextDouble4 = 0.0f;
                        float fNextDouble5 = 0.0f;
                        while (jsonReader.hasNext()) {
                            int iSelectName = jsonReader.selectName(INTERPOLATOR_NAMES);
                            if (iSelectName == 0) {
                                pointF2 = pointF5;
                                JsonReader.Token tokenPeek = jsonReader.peek();
                                JsonReader.Token token = JsonReader.Token.NUMBER;
                                if (tokenPeek == token) {
                                    fNextDouble4 = (float) jsonReader.nextDouble();
                                    fNextDouble2 = fNextDouble4;
                                } else {
                                    jsonReader.beginArray();
                                    fNextDouble2 = (float) jsonReader.nextDouble();
                                    fNextDouble4 = jsonReader.peek() == token ? (float) jsonReader.nextDouble() : fNextDouble2;
                                    jsonReader.endArray();
                                }
                            } else if (iSelectName != 1) {
                                jsonReader.skipValue();
                            } else {
                                JsonReader.Token tokenPeek2 = jsonReader.peek();
                                JsonReader.Token token2 = JsonReader.Token.NUMBER;
                                if (tokenPeek2 == token2) {
                                    pointF2 = pointF5;
                                    fNextDouble5 = (float) jsonReader.nextDouble();
                                    fNextDouble3 = fNextDouble5;
                                } else {
                                    pointF2 = pointF5;
                                    jsonReader.beginArray();
                                    fNextDouble3 = (float) jsonReader.nextDouble();
                                    fNextDouble5 = jsonReader.peek() == token2 ? (float) jsonReader.nextDouble() : fNextDouble3;
                                    jsonReader.endArray();
                                }
                            }
                            pointF5 = pointF2;
                        }
                        pointF3 = new PointF(fNextDouble2, fNextDouble3);
                        pointF4 = new PointF(fNextDouble4, fNextDouble5);
                        jsonReader.endObject();
                    } else {
                        pointFJsonToPoint = JsonUtils.jsonToPoint(jsonReader, f);
                    }
                    z2 = z3;
                    break;
                case 4:
                    if (jsonReader.peek() != JsonReader.Token.BEGIN_OBJECT) {
                        pointFJsonToPoint2 = JsonUtils.jsonToPoint(jsonReader, f);
                    } else {
                        jsonReader.beginObject();
                        float f3 = 0.0f;
                        float f4 = 0.0f;
                        float fNextDouble6 = 0.0f;
                        float fNextDouble7 = 0.0f;
                        while (jsonReader.hasNext()) {
                            int iSelectName2 = jsonReader.selectName(INTERPOLATOR_NAMES);
                            if (iSelectName2 != 0) {
                                z = z2;
                                if (iSelectName2 != 1) {
                                    jsonReader.skipValue();
                                } else {
                                    JsonReader.Token tokenPeek3 = jsonReader.peek();
                                    JsonReader.Token token3 = JsonReader.Token.NUMBER;
                                    if (tokenPeek3 == token3) {
                                        fNextDouble7 = (float) jsonReader.nextDouble();
                                        f4 = fNextDouble7;
                                    } else {
                                        jsonReader.beginArray();
                                        PointF pointF7 = pointFJsonToPoint3;
                                        float fNextDouble8 = (float) jsonReader.nextDouble();
                                        fNextDouble7 = jsonReader.peek() == token3 ? (float) jsonReader.nextDouble() : fNextDouble8;
                                        jsonReader.endArray();
                                        pointFJsonToPoint3 = pointF7;
                                        f4 = fNextDouble8;
                                    }
                                }
                            } else {
                                z = z2;
                                PointF pointF8 = pointFJsonToPoint3;
                                JsonReader.Token tokenPeek4 = jsonReader.peek();
                                JsonReader.Token token4 = JsonReader.Token.NUMBER;
                                if (tokenPeek4 == token4) {
                                    pointFJsonToPoint3 = pointF8;
                                    fNextDouble6 = (float) jsonReader.nextDouble();
                                    f3 = fNextDouble6;
                                } else {
                                    pointFJsonToPoint3 = pointF8;
                                    jsonReader.beginArray();
                                    float fNextDouble9 = (float) jsonReader.nextDouble();
                                    if (jsonReader.peek() == token4) {
                                        f2 = fNextDouble9;
                                        fNextDouble6 = (float) jsonReader.nextDouble();
                                    } else {
                                        f2 = fNextDouble9;
                                        fNextDouble6 = f2;
                                    }
                                    jsonReader.endArray();
                                    f3 = f2;
                                }
                            }
                            z2 = z;
                        }
                        PointF pointF9 = new PointF(f3, f4);
                        PointF pointF10 = new PointF(fNextDouble6, fNextDouble7);
                        jsonReader.endObject();
                        pointF6 = pointF10;
                        pointF5 = pointF9;
                    }
                    break;
                case 5:
                    z2 = jsonReader.nextInt() == 1;
                    break;
                case 6:
                    pointFJsonToPoint3 = JsonUtils.jsonToPoint(jsonReader, f);
                    break;
                case 7:
                    pointFJsonToPoint4 = JsonUtils.jsonToPoint(jsonReader, f);
                    break;
                default:
                    jsonReader.skipValue();
                    break;
            }
        }
        boolean z4 = z2;
        PointF pointF11 = pointF5;
        jsonReader.endObject();
        if (z4) {
            interpolator = LINEAR_INTERPOLATOR;
            obj = obj2;
        } else {
            if (pointFJsonToPoint == null || pointFJsonToPoint2 == null) {
                if (pointF3 == null || pointF4 == null || pointF11 == null || pointF6 == null) {
                    interpolatorInterpolatorFor = LINEAR_INTERPOLATOR;
                } else {
                    interpolatorInterpolatorFor2 = interpolatorFor(pointF3, pointF11);
                    interpolatorInterpolatorFor3 = interpolatorFor(pointF4, pointF6);
                    obj = obj3;
                    interpolator = null;
                }
                if (interpolatorInterpolatorFor2 != null || interpolatorInterpolatorFor3 == null) {
                    pointF = pointFJsonToPoint4;
                    keyframe = new Keyframe(lottieComposition, obj2, obj, interpolator, fNextDouble, null);
                } else {
                    pointF = pointFJsonToPoint4;
                    keyframe = new Keyframe(lottieComposition, obj2, obj, interpolatorInterpolatorFor2, interpolatorInterpolatorFor3, fNextDouble, null);
                }
                keyframe.pathCp1 = pointFJsonToPoint3;
                keyframe.pathCp2 = pointF;
                return keyframe;
            }
            interpolatorInterpolatorFor = interpolatorFor(pointFJsonToPoint, pointFJsonToPoint2);
            interpolator = interpolatorInterpolatorFor;
            obj = obj3;
        }
        interpolatorInterpolatorFor2 = null;
        interpolatorInterpolatorFor3 = null;
        if (interpolatorInterpolatorFor2 != null) {
            pointF = pointFJsonToPoint4;
            keyframe = new Keyframe(lottieComposition, obj2, obj, interpolator, fNextDouble, null);
        } else {
            pointF = pointFJsonToPoint4;
            keyframe = new Keyframe(lottieComposition, obj2, obj, interpolator, fNextDouble, null);
        }
        keyframe.pathCp1 = pointFJsonToPoint3;
        keyframe.pathCp2 = pointF;
        return keyframe;
    }

    public static Keyframe parseStaticValue(JsonReader jsonReader, float f, ValueParser valueParser) {
        return new Keyframe(valueParser.parse(jsonReader, f));
    }

    public static SparseArrayCompat pathInterpolatorCache() {
        if (pathInterpolatorCache == null) {
            pathInterpolatorCache = new SparseArrayCompat();
        }
        return pathInterpolatorCache;
    }

    public static void putInterpolator(int i, WeakReference weakReference) {
        synchronized (KeyframeParser.class) {
            pathInterpolatorCache.put(i, weakReference);
        }
    }
}
