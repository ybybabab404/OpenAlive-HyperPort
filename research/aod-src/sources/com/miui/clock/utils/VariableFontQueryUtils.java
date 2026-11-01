package com.miui.clock.utils;

import android.content.Context;
import android.graphics.RectF;
import android.util.Log;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

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
/* JADX INFO: compiled from: VariableFontQueryUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class VariableFontQueryUtils {
    public static final Companion Companion = new Companion(null);
    public final Context context;
    public FontSetData fontSetData;
    public float initialWght;

    /* JADX INFO: compiled from: VariableFontQueryUtils.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public VariableFontQueryUtils(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.initialWght = 700.0f;
    }

    public final float pxToDp(float f) {
        return f / this.context.getResources().getDisplayMetrics().density;
    }

    public final float[] query(RectF bounds, boolean z, Boolean bool) {
        Boolean boolValueOf;
        float f;
        float f2;
        boolean z2;
        Intrinsics.checkNotNullParameter(bounds, "bounds");
        FontSetData fontSetData = this.fontSetData;
        if (fontSetData == null) {
            Log.w("VariableFontQueryUtils", "FontSetData 未初始化，请先调用 setFontSetData()");
            return null;
        }
        float fPxToDp = pxToDp(bounds.width());
        float fPxToDp2 = pxToDp(bounds.height());
        if (fPxToDp <= 0.0f || fPxToDp2 <= 0.0f) {
            Log.w("VariableFontQueryUtils", "bounds 尺寸无效: " + fPxToDp + 'x' + fPxToDp2 + " dp");
            return null;
        }
        if (bool == null) {
            boolValueOf = Boolean.valueOf(fPxToDp / fPxToDp2 >= 0.75f);
        } else {
            boolValueOf = bool;
        }
        if (boolValueOf.booleanValue()) {
            z2 = z;
            f2 = fPxToDp;
            f = fPxToDp2;
        } else {
            f = 0.49f * fPxToDp2;
            f2 = 2.0f * fPxToDp;
            z2 = false;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("查询参数: bounds=");
        sb.append((int) bounds.width());
        sb.append('x');
        sb.append((int) bounds.height());
        sb.append("px -> ");
        String str = String.format("%.1f", Arrays.copyOf(new Object[]{Float.valueOf(fPxToDp)}, 1));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        sb.append(str);
        sb.append('x');
        String str2 = String.format("%.1f", Arrays.copyOf(new Object[]{Float.valueOf(fPxToDp2)}, 1));
        Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
        sb.append(str2);
        sb.append("dp, isSingleRow=");
        sb.append(boolValueOf);
        sb.append(", queryWidth=");
        String str3 = String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(f2)}, 1));
        Intrinsics.checkNotNullExpressionValue(str3, "format(...)");
        sb.append(str3);
        sb.append(", queryHeight=");
        String str4 = String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(f)}, 1));
        Intrinsics.checkNotNullExpressionValue(str4, "format(...)");
        sb.append(str4);
        sb.append(", queryHasColon=");
        sb.append(z2);
        Log.d("VariableFontQueryUtils", sb.toString());
        float[] fArrQuery = fontSetData.query(f2, f, z2);
        if (fArrQuery == null || fArrQuery.length < 2) {
            Log.w("VariableFontQueryUtils", "查询字体参数失败或结果不完整");
            return null;
        }
        float f3 = fArrQuery[0];
        float f4 = fArrQuery[1];
        float[] fArrQueryTextSizeParams = fontSetData.queryTextSizeParams(f4);
        if (fArrQueryTextSizeParams == null || fArrQueryTextSizeParams.length < 2) {
            Log.w("VariableFontQueryUtils", "查询 textSize 参数失败或结果不完整");
            return null;
        }
        float f5 = (f * 128.0f) / ((fArrQueryTextSizeParams[0] * f4) + fArrQueryTextSizeParams[1]);
        float fCoerceIn = RangesKt.coerceIn(this.initialWght, 150.0f, 700.0f) * ((float) Math.pow(f5 / 100.0f, -0.2d));
        float[] fArr = {f3, f4, f5, fCoerceIn};
        StringBuilder sb2 = new StringBuilder();
        sb2.append("查询结果: wdth=");
        String str5 = String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(f3)}, 1));
        Intrinsics.checkNotNullExpressionValue(str5, "format(...)");
        sb2.append(str5);
        sb2.append(", hght=");
        String str6 = String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(f4)}, 1));
        Intrinsics.checkNotNullExpressionValue(str6, "format(...)");
        sb2.append(str6);
        sb2.append(", textSize=");
        String str7 = String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(f5)}, 1));
        Intrinsics.checkNotNullExpressionValue(str7, "format(...)");
        sb2.append(str7);
        sb2.append(", wght=");
        String str8 = String.format("%.2f", Arrays.copyOf(new Object[]{Float.valueOf(fCoerceIn)}, 1));
        Intrinsics.checkNotNullExpressionValue(str8, "format(...)");
        sb2.append(str8);
        sb2.append(", isSingleRow=");
        sb2.append(boolValueOf);
        Log.d("VariableFontQueryUtils", sb2.toString());
        return fArr;
    }

    public final void setFontSetData(FontSetData data) {
        Intrinsics.checkNotNullParameter(data, "data");
        this.fontSetData = data;
    }
}
