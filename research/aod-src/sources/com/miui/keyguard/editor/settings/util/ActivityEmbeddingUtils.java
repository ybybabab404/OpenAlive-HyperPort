package com.miui.keyguard.editor.settings.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import androidx.window.embedding.ActivityEmbeddingController;
import androidx.window.embedding.SplitController;
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
/* JADX INFO: compiled from: ActivityEmbeddingUtils.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ActivityEmbeddingUtils {
    public static final ActivityEmbeddingUtils INSTANCE = new ActivityEmbeddingUtils();

    public static final boolean isActivityAlreadyEmbedded(Context activity) {
        Intrinsics.checkNotNullParameter(activity, "activity");
        boolean z = false;
        if (!(activity instanceof Activity)) {
            return false;
        }
        if (isSplitEnabled(activity) && ActivityEmbeddingController.Companion.getInstance(activity).isActivityEmbedded((Activity) activity)) {
            z = true;
        }
        Log.d("ActivityEmbeddingUtils", "isActivityAlreadyEmbedded: " + z);
        return z;
    }

    public static final boolean isSplitEnabled(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        boolean zAreEqual = Intrinsics.areEqual(SplitController.Companion.getInstance(context).getSplitSupportStatus(), SplitController.SplitSupportStatus.SPLIT_AVAILABLE);
        Log.d("ActivityEmbeddingUtils", "isSplitEnabled: " + zAreEqual);
        return zAreEqual;
    }
}
