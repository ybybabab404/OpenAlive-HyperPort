package com.miui.keyguard.editor.history.common;

import android.view.View;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
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
/* JADX INFO: compiled from: ViewExt.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ViewExtKt {
    public static long lastTime;

    public static final void clickNoRepeat(View view, final long j, final Function1 onClick) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(onClick, "onClick");
        view.setOnClickListener(new View.OnClickListener() { // from class: com.miui.keyguard.editor.history.common.ViewExtKt$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                ViewExtKt.clickNoRepeat$lambda$0(j, onClick, view2);
            }
        });
    }

    public static /* synthetic */ void clickNoRepeat$default(View view, long j, Function1 function1, int i, Object obj) {
        if ((i & 1) != 0) {
            j = 500;
        }
        clickNoRepeat(view, j, function1);
    }

    public static final void clickNoRepeat$lambda$0(long j, Function1 function1, View view) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis < lastTime) {
            lastTime = 0L;
        }
        long j2 = lastTime;
        if (j2 != 0 && jCurrentTimeMillis - j2 < j) {
            lastTime = jCurrentTimeMillis;
            return;
        }
        lastTime = jCurrentTimeMillis;
        Intrinsics.checkNotNull(view);
        function1.invoke(view);
    }
}
