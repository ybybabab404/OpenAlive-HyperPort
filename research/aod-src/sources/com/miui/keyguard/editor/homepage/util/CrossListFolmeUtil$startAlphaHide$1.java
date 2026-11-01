package com.miui.keyguard.editor.homepage.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import androidx.core.util.Consumer;
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
/* JADX INFO: compiled from: CrossListFolmeUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class CrossListFolmeUtil$startAlphaHide$1 extends AnimatorListenerAdapter {
    public final /* synthetic */ Consumer $completeCallback;

    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
    public void onAnimationCancel(Animator animation) {
        Intrinsics.checkNotNullParameter(animation, "animation");
        Consumer consumer = this.$completeCallback;
        if (consumer != null) {
            consumer.accept(Boolean.FALSE);
        }
    }

    @Override // android.animation.Animator.AnimatorListener
    public void onAnimationEnd(Animator animation, boolean z) {
        Intrinsics.checkNotNullParameter(animation, "animation");
        Consumer consumer = this.$completeCallback;
        if (consumer != null) {
            consumer.accept(Boolean.TRUE);
        }
    }
}
