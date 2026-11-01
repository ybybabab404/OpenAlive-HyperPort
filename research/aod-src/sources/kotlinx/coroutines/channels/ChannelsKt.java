package kotlinx.coroutines.channels;

import java.util.Collection;
import java.util.Map;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;

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
public final class ChannelsKt {
    public static final void cancelConsumed(ReceiveChannel receiveChannel, Throwable th) {
        ChannelsKt__Channels_commonKt.cancelConsumed(receiveChannel, th);
    }

    public static final Object toChannel(ReceiveChannel receiveChannel, SendChannel sendChannel, Continuation continuation) {
        return ChannelsKt__DeprecatedKt.toChannel(receiveChannel, sendChannel, continuation);
    }

    public static final Object toCollection(ReceiveChannel receiveChannel, Collection collection, Continuation continuation) {
        return ChannelsKt__DeprecatedKt.toCollection(receiveChannel, collection, continuation);
    }

    public static final Object toList(ReceiveChannel receiveChannel, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.toList(receiveChannel, continuation);
    }

    public static final Object toMap(ReceiveChannel receiveChannel, Map map, Continuation continuation) {
        return ChannelsKt__DeprecatedKt.toMap(receiveChannel, map, continuation);
    }
}
