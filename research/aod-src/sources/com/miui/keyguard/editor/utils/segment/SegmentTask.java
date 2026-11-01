package com.miui.keyguard.editor.utils.segment;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.util.Log;
import com.xiaomi.aicr.vision.VisionAttribute;
import com.xiaomi.aicr.vision.imagesegment.ImageSegmentManager;
import com.xiaomi.aicr.vision.imagesegment.SegmentResult;
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
final class SegmentTask implements Supplier<SegmentInfo> {
    public final Bitmap bitmap;
    public final long id;
    public final ImageSegmentManager segmentManager;

    public SegmentTask(ImageSegmentManager imageSegmentManager, Bitmap bitmap, long j) {
        Intrinsics.checkNotNullParameter(bitmap, "bitmap");
        this.segmentManager = imageSegmentManager;
        this.bitmap = bitmap;
        this.id = j;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.function.Supplier
    public SegmentInfo get() {
        Log.i("ImageSegmentHandler", "start segment");
        ImageSegmentManager imageSegmentManager = this.segmentManager;
        SegmentInfo segmentInfoProcessSegmentResult = processSegmentResult(imageSegmentManager != null ? imageSegmentManager.segment(this.bitmap, new VisionAttribute(false, true)) : null);
        Log.i("ImageSegmentHandler", "end segment segmentResult?.bitmap: " + (segmentInfoProcessSegmentResult != null ? segmentInfoProcessSegmentResult.getBitmap() : null));
        return segmentInfoProcessSegmentResult == null ? new SegmentInfo(this.id, null, null) : segmentInfoProcessSegmentResult;
    }

    public final SegmentInfo processSegmentResult(SegmentResult segmentResult) {
        if (segmentResult == null) {
            return null;
        }
        List bitmaps = segmentResult.getBitmaps();
        if (bitmaps == null) {
            Log.e("ImageSegmentHandler", "segmentResult is null");
            return null;
        }
        if (!bitmaps.isEmpty()) {
            return new SegmentInfo(this.id, (Bitmap) CollectionsKt.first(bitmaps), new RectF());
        }
        Log.e("ImageSegmentHandler", "segmentResult is empty");
        return null;
    }
}
