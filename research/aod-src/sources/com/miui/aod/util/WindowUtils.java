package com.miui.aod.util;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import kotlin.Metadata;
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
/* JADX INFO: compiled from: WindowUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class WindowUtils {
    public static final WindowUtils INSTANCE = new WindowUtils();

    public final int getWidthPixel(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        return getWindowSize(ctx).x;
    }

    public final Point getWindowSize(Context ctx) {
        Intrinsics.checkNotNullParameter(ctx, "ctx");
        Point windowSize = miuix.core.util.WindowUtils.getWindowSize(ctx);
        Intrinsics.checkNotNullExpressionValue(windowSize, "getWindowSize(...)");
        return windowSize;
    }

    public final PointF scale(Point original, Point current) {
        Intrinsics.checkNotNullParameter(original, "original");
        Intrinsics.checkNotNullParameter(current, "current");
        return new PointF(current.x / original.x, current.y / original.y);
    }

    public final boolean windowEquals(Point original, Point current) {
        Intrinsics.checkNotNullParameter(original, "original");
        Intrinsics.checkNotNullParameter(current, "current");
        return Intrinsics.areEqual(original, current);
    }
}
