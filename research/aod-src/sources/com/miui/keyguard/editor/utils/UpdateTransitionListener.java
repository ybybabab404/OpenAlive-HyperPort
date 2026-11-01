package com.miui.keyguard.editor.utils;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
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
/* JADX INFO: compiled from: Animator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class UpdateTransitionListener extends DefaultTransitionListener {
    public final Function0 callback;
    public final Function2 updateCallback;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UpdateTransitionListener(Function0 callback, Function2 function2) {
        super(callback);
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.callback = callback;
        this.updateCallback = function2;
    }

    public /* synthetic */ UpdateTransitionListener(Function0 function0, Function2 function2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(function0, (i & 2) != 0 ? null : function2);
    }

    @Override // miuix.animation.listener.TransitionListener
    public void onUpdate(Object obj, Collection collection) {
        super.onUpdate(obj, collection);
        Function2 function2 = this.updateCallback;
        if (function2 != null) {
            function2.invoke(obj, collection);
        }
    }
}
