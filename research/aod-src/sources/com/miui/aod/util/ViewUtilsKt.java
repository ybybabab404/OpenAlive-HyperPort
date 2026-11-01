package com.miui.aod.util;

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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: ViewUtils.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class ViewUtilsKt {
    public static final MiuiPreDrawListener doOnMiuiPreDraw(View view, final Function1 onPreDraw) {
        Intrinsics.checkNotNullParameter(view, "<this>");
        Intrinsics.checkNotNullParameter(onPreDraw, "onPreDraw");
        return MiuiPreDrawListener.Companion.add(view, new Function1<MiuiPreDrawListener, Boolean>() { // from class: com.miui.aod.util.ViewUtilsKt.doOnMiuiPreDraw.1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(MiuiPreDrawListener it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return (Boolean) onPreDraw.invoke(it);
            }
        });
    }
}
