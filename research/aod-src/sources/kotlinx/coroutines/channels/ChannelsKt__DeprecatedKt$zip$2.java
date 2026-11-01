package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: Deprecated.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@DebugMetadata(c = "kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$zip$2", f = "Deprecated.kt", l = {514, 499, 501}, m = "invokeSuspend")
@SourceDebugExtension
final class ChannelsKt__DeprecatedKt$zip$2 extends SuspendLambda implements Function2<ProducerScope<Object>, Continuation<? super Unit>, Object> {
    public final /* synthetic */ ReceiveChannel $other;
    public final /* synthetic */ ReceiveChannel $this_zip;
    public final /* synthetic */ Function2 $transform;
    public /* synthetic */ Object L$0;
    public Object L$1;
    public Object L$2;
    public Object L$3;
    public Object L$4;
    public Object L$5;
    public int label;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ChannelsKt__DeprecatedKt$zip$2(ReceiveChannel receiveChannel, ReceiveChannel receiveChannel2, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.$other = receiveChannel;
        this.$this_zip = receiveChannel2;
        this.$transform = function2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Continuation create(Object obj, Continuation continuation) {
        ChannelsKt__DeprecatedKt$zip$2 channelsKt__DeprecatedKt$zip$2 = new ChannelsKt__DeprecatedKt$zip$2(this.$other, this.$this_zip, this.$transform, continuation);
        channelsKt__DeprecatedKt$zip$2.L$0 = obj;
        return channelsKt__DeprecatedKt$zip$2;
    }

    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(ProducerScope producerScope, Continuation continuation) {
        return ((ChannelsKt__DeprecatedKt$zip$2) create(producerScope, continuation)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX WARN: Code duplicated, block: B:26:0x009c  */
    /* JADX WARN: Code duplicated, block: B:27:0x009d A[Catch: all -> 0x002a, PHI: r1 r6 r7 r8 r9 r13
  0x009d: PHI (r1v5 kotlinx.coroutines.channels.ChannelIterator) = (r1v6 kotlinx.coroutines.channels.ChannelIterator), (r1v8 kotlinx.coroutines.channels.ChannelIterator) binds: [B:25:0x009a, B:20:0x006b] A[DONT_GENERATE, DONT_INLINE]
  0x009d: PHI (r6v7 kotlinx.coroutines.channels.ReceiveChannel) = (r6v9 kotlinx.coroutines.channels.ReceiveChannel), (r6v11 kotlinx.coroutines.channels.ReceiveChannel) binds: [B:25:0x009a, B:20:0x006b] A[DONT_GENERATE, DONT_INLINE]
  0x009d: PHI (r7v5 kotlin.jvm.functions.Function2) = (r7v7 kotlin.jvm.functions.Function2), (r7v9 kotlin.jvm.functions.Function2) binds: [B:25:0x009a, B:20:0x006b] A[DONT_GENERATE, DONT_INLINE]
  0x009d: PHI (r8v5 kotlinx.coroutines.channels.ChannelIterator) = (r8v7 kotlinx.coroutines.channels.ChannelIterator), (r8v9 kotlinx.coroutines.channels.ChannelIterator) binds: [B:25:0x009a, B:20:0x006b] A[DONT_GENERATE, DONT_INLINE]
  0x009d: PHI (r9v5 kotlinx.coroutines.channels.ProducerScope) = (r9v7 kotlinx.coroutines.channels.ProducerScope), (r9v9 kotlinx.coroutines.channels.ProducerScope) binds: [B:25:0x009a, B:20:0x006b] A[DONT_GENERATE, DONT_INLINE]
  0x009d: PHI (r13v10 java.lang.Object) = (r13v15 java.lang.Object), (r13v0 java.lang.Object) binds: [B:25:0x009a, B:20:0x006b] A[DONT_GENERATE, DONT_INLINE], TryCatch #2 {all -> 0x002a, blocks: (B:8:0x0026, B:24:0x0088, B:27:0x009d, B:29:0x00a5, B:39:0x00ef, B:20:0x006b, B:23:0x0080), top: B:50:0x000a }] */
    /* JADX WARN: Code duplicated, block: B:29:0x00a5 A[Catch: all -> 0x002a, TRY_LEAVE, TryCatch #2 {all -> 0x002a, blocks: (B:8:0x0026, B:24:0x0088, B:27:0x009d, B:29:0x00a5, B:39:0x00ef, B:20:0x006b, B:23:0x0080), top: B:50:0x000a }] */
    /* JADX WARN: Code duplicated, block: B:32:0x00be  */
    /* JADX WARN: Code duplicated, block: B:35:0x00cd A[Catch: all -> 0x0053, TRY_LEAVE, TryCatch #0 {all -> 0x0053, blocks: (B:33:0x00c5, B:35:0x00cd, B:15:0x004b), top: B:46:0x004b }] */
    /* JADX WARN: Code duplicated, block: B:38:0x00ea  */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions stack size limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final java.lang.Object invokeSuspend(java.lang.Object r13) {
        /*
            Method dump skipped, instruction units count: 253
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ChannelsKt__DeprecatedKt$zip$2.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
