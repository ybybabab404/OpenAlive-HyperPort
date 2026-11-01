package com.miui.keyguard.editor.utils.segment;

import android.graphics.Bitmap;
import android.graphics.RectF;
import android.os.SystemClock;
import kotlin.Metadata;
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
    */
/* JADX INFO: compiled from: ImageSegmentHandler.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class SegmentInfo {
    public final Bitmap bitmap;
    public final long id;
    public RectF rect;

    public SegmentInfo(long j, Bitmap bitmap, RectF rectF) {
        this.id = j;
        this.bitmap = bitmap;
        this.rect = rectF;
    }

    public /* synthetic */ SegmentInfo(long j, Bitmap bitmap, RectF rectF, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? SystemClock.elapsedRealtime() : j, bitmap, rectF);
    }

    public final Bitmap getBitmap() {
        return this.bitmap;
    }

    public final long getId() {
        return this.id;
    }

    public final boolean isRectValid() {
        RectF rectF = this.rect;
        if (rectF == null) {
            return true;
        }
        Intrinsics.checkNotNull(rectF);
        if (rectF.width() < 0.0f) {
            return false;
        }
        RectF rectF2 = this.rect;
        Intrinsics.checkNotNull(rectF2);
        return rectF2.height() >= 0.0f;
    }
}
