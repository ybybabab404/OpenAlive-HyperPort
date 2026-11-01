package androidx.window.core;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import kotlin.Metadata;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ConsumerAdapter.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class ConsumerAdapter$createSubscriptionNoActivity$1 implements ConsumerAdapter.Subscription {
    public final /* synthetic */ Object $javaConsumer;
    public final /* synthetic */ Object $obj;
    public final /* synthetic */ Method $removeMethod;

    @Override // androidx.window.core.ConsumerAdapter.Subscription
    public void dispose() throws IllegalAccessException, InvocationTargetException {
        this.$removeMethod.invoke(this.$obj, this.$javaConsumer);
    }
}
