package com.xiaomi.taiyi.sdk.base.utils;

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
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public class SingletonHolder<T, A> {
    public Function1 creator;
    public volatile Object instance;

    public SingletonHolder(Function1 creator) {
        Intrinsics.checkNotNullParameter(creator, "creator");
        this.creator = creator;
    }

    public final Object getInstance(Object obj) {
        Object objInvoke;
        Object obj2 = this.instance;
        if (obj2 != null) {
            return obj2;
        }
        synchronized (this) {
            objInvoke = this.instance;
            if (objInvoke == null) {
                Function1 function1 = this.creator;
                Intrinsics.checkNotNull(function1);
                objInvoke = function1.invoke(obj);
                this.instance = objInvoke;
            }
        }
        return objInvoke;
    }
}
