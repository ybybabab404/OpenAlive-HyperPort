package com.miui.keyguard.editor.utils;

import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;
import com.miui.maml.folme.AnimatedProperty;
import java.nio.IntBuffer;
import java.util.Arrays;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import miuix.core.util.SystemProperties;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: HierarchyAvoidDebugLogger.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class HierarchyAvoidDebugLogger {
    public static final HierarchyAvoidDebugLogger INSTANCE = new HierarchyAvoidDebugLogger();
    public static final boolean DEBUG_LOG = SystemProperties.getBoolean("persist.sysui.depth_avoid_debug", false);

    public static final void logAvoidOutline(Rect checkRect, IntBuffer intBuffer) {
        Intrinsics.checkNotNullParameter(checkRect, "checkRect");
        if (DEBUG_LOG && intBuffer != null) {
            int iWidth = checkRect.width();
            int iHeight = checkRect.height();
            int iCoerceAtMost = RangesKt.coerceAtMost(24, iWidth);
            int iCoerceAtMost2 = RangesKt.coerceAtMost(6, iHeight);
            if (iCoerceAtMost <= 0 || iCoerceAtMost2 <= 0) {
                return;
            }
            int i = iWidth / iCoerceAtMost;
            int i2 = iHeight / iCoerceAtMost2;
            StringBuilder sb = new StringBuilder();
            sb.append("avoidOutline: checkRect=" + checkRect + " " + iWidth + AnimatedProperty.PROPERTY_NAME_X + iHeight + "\n");
            for (int i3 = 0; i3 < iCoerceAtMost2; i3++) {
                sb.append("avoidOutline: [");
                for (int i4 = 0; i4 < iCoerceAtMost; i4++) {
                    int iCoerceIn = (RangesKt.coerceIn((i3 * i2) + (i2 / 2), 0, iHeight - 1) * iWidth) + RangesKt.coerceIn((i4 * i) + (i / 2), 0, iWidth - 1);
                    sb.append((iCoerceIn >= intBuffer.limit() || Color.alpha(intBuffer.get(iCoerceIn)) <= 5) ? '.' : '#');
                }
                sb.append("]\n");
            }
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            Log.d("HierarchyImageAvoidCutController", StringsKt.trimEnd(string).toString());
        }
    }

    public static final void logAvoidResult(Rect checkRect, int i, int i2, int i3, boolean z, double d, double d2) {
        Intrinsics.checkNotNullParameter(checkRect, "checkRect");
        if (DEBUG_LOG) {
            int iWidth = checkRect.width() * checkRect.height();
            float f = iWidth > 0 ? (i * 100.0f) / iWidth : 0.0f;
            double d3 = z ? d2 * ((double) 100) : ((double) 100) * d;
            if (z) {
                i2 = i3;
            }
            boolean z2 = i >= i2;
            StringCompanionObject stringCompanionObject = StringCompanionObject.INSTANCE;
            String str = String.format("%.1f", Arrays.copyOf(new Object[]{Float.valueOf(f)}, 1));
            Intrinsics.checkNotNullExpressionValue(str, "format(...)");
            String str2 = String.format("%.0f", Arrays.copyOf(new Object[]{Double.valueOf(d3)}, 1));
            Intrinsics.checkNotNullExpressionValue(str2, "format(...)");
            Log.d("HierarchyImageAvoidCutController", "calculateUnAlphaPercent: opaquePixels=" + i + "/" + iWidth + " (" + str + "%), threshold=" + str2 + "%, result=" + (z2 ? "BLOCK(avoid)" : "ALLOW(depth on)"));
        }
    }

    public static final void logSetUpAvoidRect(Rect rect, int i, int i2, double d, double d2) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        if (DEBUG_LOG) {
            int iWidth = rect.width() * rect.height();
            double d3 = 100;
            Log.d("HierarchyImageAvoidCutController", "setUpAvoidRect: rect=" + rect + ", size=" + rect.width() + AnimatedProperty.PROPERTY_NAME_X + rect.height() + ", totalPixels=" + iWidth + ", imageThreshold=" + i + "(" + ((int) (d * d3)) + "%), videoThreshold=" + i2 + "(" + ((int) (d2 * d3)) + "%)");
        }
    }
}
