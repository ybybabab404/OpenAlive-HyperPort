package kotlinx.coroutines.flow;

import java.util.Collection;
import kotlin.Metadata;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.ReceiveChannel;

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
public final class FlowKt {
    public static final StateFlow asStateFlow(MutableStateFlow mutableStateFlow) {
        return FlowKt__ShareKt.asStateFlow(mutableStateFlow);
    }

    public static final Flow buffer(Flow flow, int i, BufferOverflow bufferOverflow) {
        return FlowKt__ContextKt.buffer(flow, i, bufferOverflow);
    }

    public static final Flow callbackFlow(Function2 function2) {
        return FlowKt__BuildersKt.callbackFlow(function2);
    }

    /* JADX INFO: renamed from: catch, reason: not valid java name */
    public static final Flow m1255catch(Flow flow, Function3 function3) {
        return FlowKt__ErrorsKt.m1263catch(flow, function3);
    }

    public static final Object catchImpl(Flow flow, FlowCollector flowCollector, Continuation continuation) {
        return FlowKt__ErrorsKt.catchImpl(flow, flowCollector, continuation);
    }

    public static final Object collect(Flow flow, Continuation continuation) {
        return FlowKt__CollectKt.collect(flow, continuation);
    }

    public static final Object collectLatest(Flow flow, Function2 function2, Continuation continuation) {
        return FlowKt__CollectKt.collectLatest(flow, function2, continuation);
    }

    public static final Flow combine(Flow flow, Flow flow2, Flow flow3, Function4 function4) {
        return FlowKt__ZipKt.combine(flow, flow2, flow3, function4);
    }

    public static final Object count(Flow flow, Continuation continuation) {
        return FlowKt__CountKt.count(flow, continuation);
    }

    public static final Object count(Flow flow, Function2 function2, Continuation continuation) {
        return FlowKt__CountKt.count(flow, function2, continuation);
    }

    public static final Flow debounce(Flow flow, long j) {
        return FlowKt__DelayKt.debounce(flow, j);
    }

    public static final Flow debounce(Flow flow, Function1 function1) {
        return FlowKt__DelayKt.debounce(flow, function1);
    }

    /* JADX INFO: renamed from: debounce-HG0u8IE, reason: not valid java name */
    public static final Flow m1256debounceHG0u8IE(Flow flow, long j) {
        return FlowKt__DelayKt.m1258debounceHG0u8IE(flow, j);
    }

    public static final Flow distinctUntilChanged(Flow flow) {
        return FlowKt__DistinctKt.distinctUntilChanged(flow);
    }

    public static final Flow dropWhile(Flow flow, Function2 function2) {
        return FlowKt__LimitKt.dropWhile(flow, function2);
    }

    public static final Object emitAll(FlowCollector flowCollector, ReceiveChannel receiveChannel, Continuation continuation) {
        return FlowKt__ChannelsKt.emitAll(flowCollector, receiveChannel, continuation);
    }

    public static final Object emitAll(FlowCollector flowCollector, Flow flow, Continuation continuation) {
        return FlowKt__CollectKt.emitAll(flowCollector, flow, continuation);
    }

    public static final void ensureActive(FlowCollector flowCollector) {
        FlowKt__EmittersKt.ensureActive(flowCollector);
    }

    public static final Object first(Flow flow, Continuation continuation) {
        return FlowKt__ReduceKt.first(flow, continuation);
    }

    public static final Object first(Flow flow, Function2 function2, Continuation continuation) {
        return FlowKt__ReduceKt.first(flow, function2, continuation);
    }

    public static final Object firstOrNull(Flow flow, Continuation continuation) {
        return FlowKt__ReduceKt.firstOrNull(flow, continuation);
    }

    public static final Object firstOrNull(Flow flow, Function2 function2, Continuation continuation) {
        return FlowKt__ReduceKt.firstOrNull(flow, function2, continuation);
    }

    public static final ReceiveChannel fixedPeriodTicker(CoroutineScope coroutineScope, long j) {
        return FlowKt__DelayKt.fixedPeriodTicker(coroutineScope, j);
    }

    public static final Flow flow(Function2 function2) {
        return FlowKt__BuildersKt.flow(function2);
    }

    public static final Flow flowOf(Object obj) {
        return FlowKt__BuildersKt.flowOf(obj);
    }

    public static final Flow flowOn(Flow flow, CoroutineContext coroutineContext) {
        return FlowKt__ContextKt.flowOn(flow, coroutineContext);
    }

    public static final Object last(Flow flow, Continuation continuation) {
        return FlowKt__ReduceKt.last(flow, continuation);
    }

    public static final Object lastOrNull(Flow flow, Continuation continuation) {
        return FlowKt__ReduceKt.lastOrNull(flow, continuation);
    }

    public static final Flow mapLatest(Flow flow, Function2 function2) {
        return FlowKt__MergeKt.mapLatest(flow, function2);
    }

    public static final Flow merge(Iterable iterable) {
        return FlowKt__MergeKt.merge(iterable);
    }

    public static final Flow merge(Flow... flowArr) {
        return FlowKt__MergeKt.merge(flowArr);
    }

    public static final Flow onEach(Flow flow, Function2 function2) {
        return FlowKt__TransformKt.onEach(flow, function2);
    }

    public static final Flow onEmpty(Flow flow, Function2 function2) {
        return FlowKt__EmittersKt.onEmpty(flow, function2);
    }

    public static final ReceiveChannel produceIn(Flow flow, CoroutineScope coroutineScope) {
        return FlowKt__ChannelsKt.produceIn(flow, coroutineScope);
    }

    public static final Flow receiveAsFlow(ReceiveChannel receiveChannel) {
        return FlowKt__ChannelsKt.receiveAsFlow(receiveChannel);
    }

    public static final Object reduce(Flow flow, Function3 function3, Continuation continuation) {
        return FlowKt__ReduceKt.reduce(flow, function3, continuation);
    }

    public static final Flow retryWhen(Flow flow, Function4 function4) {
        return FlowKt__ErrorsKt.retryWhen(flow, function4);
    }

    public static final Object single(Flow flow, Continuation continuation) {
        return FlowKt__ReduceKt.single(flow, continuation);
    }

    public static final Object singleOrNull(Flow flow, Continuation continuation) {
        return FlowKt__ReduceKt.singleOrNull(flow, continuation);
    }

    public static final StateFlow stateIn(Flow flow, CoroutineScope coroutineScope, SharingStarted sharingStarted, Object obj) {
        return FlowKt__ShareKt.stateIn(flow, coroutineScope, sharingStarted, obj);
    }

    public static final Object toCollection(Flow flow, Collection collection, Continuation continuation) {
        return FlowKt__CollectionKt.toCollection(flow, collection, continuation);
    }

    public static final Flow transformLatest(Flow flow, Function3 function3) {
        return FlowKt__MergeKt.transformLatest(flow, function3);
    }
}
