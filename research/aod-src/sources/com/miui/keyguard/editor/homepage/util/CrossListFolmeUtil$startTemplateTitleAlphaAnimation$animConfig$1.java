package com.miui.keyguard.editor.homepage.util;

import android.view.View;
import androidx.core.util.Consumer;
import kotlin.Metadata;
import miuix.animation.listener.TransitionListener;

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
public final class CrossListFolmeUtil$startTemplateTitleAlphaAnimation$animConfig$1 extends TransitionListener {
    public final /* synthetic */ Consumer $completeCallback;
    public final /* synthetic */ View $target;

    @Override // miuix.animation.listener.TransitionListener
    public void onCancel(Object obj) {
        this.$target.setAlpha(1.0f);
        Consumer consumer = this.$completeCallback;
        if (consumer != null) {
            consumer.accept(Boolean.FALSE);
        }
    }

    @Override // miuix.animation.listener.TransitionListener
    public void onComplete(Object obj) {
        this.$target.setAlpha(1.0f);
        Consumer consumer = this.$completeCallback;
        if (consumer != null) {
            consumer.accept(Boolean.TRUE);
        }
    }
}
