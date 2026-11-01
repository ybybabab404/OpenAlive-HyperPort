package com.miui.keyguard.editor.homepage.util;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
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
/* JADX INFO: compiled from: CrossListViewUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class CrossListViewUtil {
    public static final CrossListViewUtil INSTANCE = new CrossListViewUtil();

    public static /* synthetic */ float calculateTemplateContainerHeight$default(CrossListViewUtil crossListViewUtil, Context context, float f, Point point, float f2, int i, Object obj) {
        if ((i & 4) != 0) {
            point = null;
        }
        return crossListViewUtil.calculateTemplateContainerHeight(context, f, point, f2);
    }

    public final float calculateTemplateContainerHeight(Context context, float f, Point point, float f2) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (point == null) {
            point = requireDisplayRealSize(context);
        }
        float f3 = f2 * 2;
        return calculateTemplateViewHeightByRealRatio(f - f3, point) + f3;
    }

    public final float calculateTemplateViewHeightByRealRatio(float f, Point realSize) {
        Intrinsics.checkNotNullParameter(realSize, "realSize");
        return f / ((realSize.x * 1.0f) / realSize.y);
    }

    public final Point requireDisplayRealSize(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Point point = new Point();
        Display display = context.getDisplay();
        if (display != null) {
            display.getRealSize(point);
        }
        return point;
    }
}
