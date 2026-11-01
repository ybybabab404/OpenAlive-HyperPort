package com.miui.keyguard.shortcuts.utils;

import android.content.Context;
import android.view.RoundedCorner;
import android.view.WindowManager;
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
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class WindowUtils {
    public static final WindowUtils INSTANCE = new WindowUtils();

    public static final WindowManager getWindowManager(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService((Class<Object>) WindowManager.class);
        Intrinsics.checkNotNullExpressionValue(systemService, "getSystemService(...)");
        return (WindowManager) systemService;
    }

    public final int getPhoneRadius(Context context, int i) {
        Intrinsics.checkNotNullParameter(context, "context");
        RoundedCorner roundedCorner = getWindowManager(context).getMaximumWindowMetrics().getWindowInsets().getRoundedCorner(i);
        if (roundedCorner != null) {
            return roundedCorner.getRadius();
        }
        return 0;
    }
}
