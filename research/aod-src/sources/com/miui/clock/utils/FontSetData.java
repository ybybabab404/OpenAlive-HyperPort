package com.miui.clock.utils;

import android.util.Log;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

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
/* JADX INFO: compiled from: FontSetData.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class FontSetData {
    public static final Companion Companion = new Companion(null);
    public final TreeMap heightMap;
    public final TreeMap ratioColonMap;
    public final TreeMap ratioMap;

    /* JADX INFO: compiled from: FontSetData.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public FontSetData(TreeMap ratioMap, TreeMap ratioColonMap, TreeMap heightMap) {
        Intrinsics.checkNotNullParameter(ratioMap, "ratioMap");
        Intrinsics.checkNotNullParameter(ratioColonMap, "ratioColonMap");
        Intrinsics.checkNotNullParameter(heightMap, "heightMap");
        this.ratioMap = ratioMap;
        this.ratioColonMap = ratioColonMap;
        this.heightMap = heightMap;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FontSetData)) {
            return false;
        }
        FontSetData fontSetData = (FontSetData) obj;
        return Intrinsics.areEqual(this.ratioMap, fontSetData.ratioMap) && Intrinsics.areEqual(this.ratioColonMap, fontSetData.ratioColonMap) && Intrinsics.areEqual(this.heightMap, fontSetData.heightMap);
    }

    public final TreeMap getHeightMap() {
        return this.heightMap;
    }

    public final TreeMap getRatioColonMap() {
        return this.ratioColonMap;
    }

    public final TreeMap getRatioMap() {
        return this.ratioMap;
    }

    public int hashCode() {
        return (((this.ratioMap.hashCode() * 31) + this.ratioColonMap.hashCode()) * 31) + this.heightMap.hashCode();
    }

    public final float[] interpolate(float f, Pair pair, float f2, Pair pair2, float f3) {
        String string;
        char c;
        float[] fArr = (float[]) pair.getFirst();
        float[] fArr2 = (float[]) pair.getSecond();
        float[] fArr3 = (float[]) pair2.getFirst();
        float f4 = fArr[0];
        float f5 = fArr[1];
        float f6 = fArr3[0];
        float f7 = fArr3[1];
        StringBuilder sb = new StringBuilder();
        sb.append("插值: ratio1=");
        sb.append(f);
        sb.append(" [wdth=");
        sb.append(f4);
        sb.append(", hght=");
        sb.append(f5);
        sb.append("], ratio2=");
        sb.append(f2);
        sb.append(" [wdth=");
        sb.append(f6);
        sb.append(", hght=");
        sb.append(f7);
        sb.append("], targetRatio=");
        sb.append(f3);
        sb.append(", interpParams=");
        if (fArr2 != null) {
            string = Arrays.toString(fArr2);
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        } else {
            string = null;
        }
        sb.append(string);
        Log.d("FontSetData", sb.toString());
        if (fArr2 == null || fArr2.length < 2) {
            float f8 = (f3 - f) / (f2 - f);
            float f9 = f4 + ((f6 - f4) * f8);
            float f10 = f5 + ((f7 - f5) * f8);
            Log.d("FontSetData", "使用线性插值: t=" + f8 + ", result=[wdth=" + f9 + ", hght=" + f10 + ']');
            return new float[]{f9, f10};
        }
        float f11 = fArr2[0];
        float f12 = fArr2[1];
        if (f4 == f6) {
            f5 = (f11 / f3) - f12;
            Log.d("FontSetData", "wdth 不变=" + f4 + ", 公式计算 hght: " + f11 + " / " + f3 + " - " + f12 + " = " + f5);
            c = (char) 1;
        } else {
            c = 1;
            if (f5 == f7) {
                f4 = (f11 * f3) - f12;
                Log.d("FontSetData", "hght 不变=" + f5 + ", 公式计算 wdth: " + f11 + " * " + f3 + " - " + f12 + " = " + f4);
            } else {
                float f13 = f6 - f4;
                float f14 = f7 - f5;
                if (Math.abs(f13) > Math.abs(f14)) {
                    f4 = (f11 * f3) - f12;
                    f5 += ((f3 - f) / (f2 - f)) * f14;
                    Log.d("FontSetData", "wdth 变化更大, 公式计算 wdth: " + f11 + " * " + f3 + " - " + f12 + " = " + f4 + ", 线性插值 hght=" + f5);
                } else {
                    f5 = (f11 / f3) - f12;
                    f4 += ((f3 - f) / (f2 - f)) * f13;
                    Log.d("FontSetData", "hght 变化更大, 公式计算 hght: " + f11 + " / " + f3 + " - " + f12 + " = " + f5 + ", 线性插值 wdth=" + f4);
                }
            }
        }
        float[] fArr4 = new float[2];
        fArr4[0] = f4;
        fArr4[c] = f5;
        return fArr4;
    }

    public final float[] query(float f, float f2, boolean z) {
        TreeMap treeMap = z ? this.ratioColonMap : this.ratioMap;
        if (treeMap.isEmpty()) {
            return null;
        }
        float f3 = f / f2;
        Map.Entry entryCeilingEntry = treeMap.ceilingEntry(Float.valueOf(f3));
        Map.Entry entryFloorEntry = treeMap.floorEntry(Float.valueOf(f3));
        if (entryFloorEntry != null && Intrinsics.areEqual((Float) entryFloorEntry.getKey(), f3)) {
            float[] fArr = (float[]) ((Pair) entryFloorEntry.getValue()).getFirst();
            float[] fArrCopyOf = Arrays.copyOf(fArr, fArr.length);
            Intrinsics.checkNotNullExpressionValue(fArrCopyOf, "copyOf(...)");
            return fArrCopyOf;
        }
        if (entryFloorEntry != null && entryCeilingEntry != null) {
            Object key = entryFloorEntry.getKey();
            Intrinsics.checkNotNullExpressionValue(key, "<get-key>(...)");
            float fFloatValue = ((Number) key).floatValue();
            Object value = entryFloorEntry.getValue();
            Intrinsics.checkNotNullExpressionValue(value, "<get-value>(...)");
            Pair pair = (Pair) value;
            Object key2 = entryCeilingEntry.getKey();
            Intrinsics.checkNotNullExpressionValue(key2, "<get-key>(...)");
            float fFloatValue2 = ((Number) key2).floatValue();
            Object value2 = entryCeilingEntry.getValue();
            Intrinsics.checkNotNullExpressionValue(value2, "<get-value>(...)");
            return interpolate(fFloatValue, pair, fFloatValue2, (Pair) value2, f3);
        }
        if (entryFloorEntry == null && entryCeilingEntry != null) {
            float[] fArr2 = (float[]) ((Pair) entryCeilingEntry.getValue()).getFirst();
            float[] fArrCopyOf2 = Arrays.copyOf(fArr2, fArr2.length);
            Intrinsics.checkNotNullExpressionValue(fArrCopyOf2, "copyOf(...)");
            return fArrCopyOf2;
        }
        if (entryFloorEntry == null || entryCeilingEntry != null) {
            return null;
        }
        float[] fArr3 = (float[]) ((Pair) entryFloorEntry.getValue()).getFirst();
        float[] fArrCopyOf3 = Arrays.copyOf(fArr3, fArr3.length);
        Intrinsics.checkNotNullExpressionValue(fArrCopyOf3, "copyOf(...)");
        return fArrCopyOf3;
    }

    public final float[] queryTextSizeParams(float f) {
        float[] fArr;
        float[] fArr2;
        if (this.heightMap.isEmpty()) {
            return null;
        }
        Map.Entry entryCeilingEntry = this.heightMap.ceilingEntry(Float.valueOf(f));
        if (entryCeilingEntry != null && (fArr2 = (float[]) entryCeilingEntry.getValue()) != null) {
            float[] fArrCopyOf = Arrays.copyOf(fArr2, fArr2.length);
            Intrinsics.checkNotNullExpressionValue(fArrCopyOf, "copyOf(...)");
            if (fArrCopyOf != null) {
                return fArrCopyOf;
            }
        }
        Map.Entry entryLastEntry = this.heightMap.lastEntry();
        if (entryLastEntry == null || (fArr = (float[]) entryLastEntry.getValue()) == null) {
            return null;
        }
        float[] fArrCopyOf2 = Arrays.copyOf(fArr, fArr.length);
        Intrinsics.checkNotNullExpressionValue(fArrCopyOf2, "copyOf(...)");
        return fArrCopyOf2;
    }

    public String toString() {
        return "FontSetData(ratioMap=" + this.ratioMap + ", ratioColonMap=" + this.ratioColonMap + ", heightMap=" + this.heightMap + ')';
    }
}
