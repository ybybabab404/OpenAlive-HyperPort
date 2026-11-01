package com.miui.keyguard.editor.utils;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
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
/* JADX INFO: compiled from: ViewStyler.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class InvokeCache {
    public static final InvokeCache INSTANCE = new InvokeCache();
    public static final Lazy cache$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.utils.InvokeCache$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return InvokeCache.cache_delegate$lambda$0();
        }
    });

    public static final Map cache_delegate$lambda$0() {
        return new LinkedHashMap();
    }

    public static /* synthetic */ Method getOrCacheMethod$default(InvokeCache invokeCache, String str, int i, Function1 function1, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = 0;
        }
        return invokeCache.getOrCacheMethod(str, i, function1);
    }

    public final Map getCache() {
        return (Map) cache$delegate.getValue();
    }

    public final Method getOrCacheMethod(String methodKey, int i, Function1 block) {
        Intrinsics.checkNotNullParameter(methodKey, "methodKey");
        Intrinsics.checkNotNullParameter(block, "block");
        String str = methodKey + "_" + i;
        Method method = (Method) getCache().get(str);
        if (method != null) {
            return method;
        }
        Method method2 = (Method) block.invoke(methodKey);
        getCache().put(str, method2);
        return method2;
    }
}
