package com.miui.keyguard.editor.utils.segment;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.util.Log;
import com.xiaomi.taiyi.api.segment.SegmentAbility;
import com.xiaomi.taiyi.segment.data.SegmentResult;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ImageSegmentHandler.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
final class SegmentNewTask implements Supplier<SegmentInfo> {
    public final Bitmap bitmap;
    public final long id;
    public final SegmentAbility segmentAbility;

    public SegmentNewTask(SegmentAbility segmentAbility, Bitmap bitmap, long j) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        this.segmentAbility = segmentAbility;
        this.bitmap = bitmap;
        this.id = j;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.function.Supplier
    public SegmentInfo get() {
        Log.i("ImageSegmentHandler", "start segment");
        SegmentAbility segmentAbility = this.segmentAbility;
        SegmentInfo segmentInfoProcessSegmentResult = processSegmentResult(segmentAbility != null ? segmentAbility.segment(this.bitmap, false, true) : null);
        Log.i("ImageSegmentHandler", "end segment segmentResult?.bitmap: " + (segmentInfoProcessSegmentResult != null ? segmentInfoProcessSegmentResult.getBitmap() : null));
        return segmentInfoProcessSegmentResult == null ? new SegmentInfo(this.id, null, null) : segmentInfoProcessSegmentResult;
    }

    public final SegmentInfo processSegmentResult(SegmentResult segmentResult) {
        if (segmentResult == null) {
            return null;
        }
        List<Bitmap> bitmaps = segmentResult.getBitmaps();
        RectF rectF = new RectF();
        if (bitmaps == null) {
            Log.e("ImageSegmentHandler", "segmentResult is null");
            return null;
        }
        if (bitmaps.isEmpty()) {
            Log.e("ImageSegmentHandler", "segmentResult is empty");
            return null;
        }
        List<float[]> rects = segmentResult.getRects();
        Intrinsics.checkNotNullExpressionValue(rects, "getRects(...)");
        if (rects.isEmpty()) {
            Log.e("ImageSegmentHandler", "segment rect is empty");
        } else {
            List<float[]> rects2 = segmentResult.getRects();
            Intrinsics.checkNotNullExpressionValue(rects2, "getRects(...)");
            float[] fArr = (float[]) CollectionsKt.first((List) rects2);
            String string = Arrays.toString(fArr);
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            Log.d("ImageSegmentHandler", "rectArray=" + string);
            if (fArr.length >= 4) {
                float f = fArr[0];
                float f2 = fArr[1];
                rectF.set(f, f2, fArr[2] + f, fArr[3] + f2);
            }
        }
        return new SegmentInfo(this.id, (Bitmap) CollectionsKt.first((List) bitmaps), rectF);
    }
}
