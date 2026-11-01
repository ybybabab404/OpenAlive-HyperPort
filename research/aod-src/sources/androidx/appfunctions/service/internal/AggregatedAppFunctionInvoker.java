package androidx.appfunctions.service.internal;

import androidx.annotation.RestrictTo;
import androidx.appfunctions.AppFunctionContext;
import androidx.appfunctions.AppFunctionFunctionNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: AggregatedAppFunctionInvoker.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@RestrictTo
@SourceDebugExtension
public abstract class AggregatedAppFunctionInvoker implements AppFunctionInvoker {

    @NotNull
    private final Lazy supportedFunctionIds$delegate = LazyKt.lazy(new Function0() { // from class: androidx.appfunctions.service.internal.AggregatedAppFunctionInvoker$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return AggregatedAppFunctionInvoker.supportedFunctionIds_delegate$lambda$1(this.f$0);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public static final Set supportedFunctionIds_delegate$lambda$1(AggregatedAppFunctionInvoker aggregatedAppFunctionInvoker) {
        if (aggregatedAppFunctionInvoker.getInvokers().isEmpty()) {
            return SetsKt.emptySet();
        }
        List<AppFunctionInvoker> invokers = aggregatedAppFunctionInvoker.getInvokers();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(invokers, 10));
        Iterator<T> it = invokers.iterator();
        while (it.hasNext()) {
            arrayList.add(((AppFunctionInvoker) it.next()).getSupportedFunctionIds());
        }
        Iterator it2 = arrayList.iterator();
        if (!it2.hasNext()) {
            throw new UnsupportedOperationException("Empty collection can't be reduced.");
        }
        Object next = it2.next();
        while (it2.hasNext()) {
            next = SetsKt.plus((Set) next, (Set) it2.next());
        }
        return (Set) next;
    }

    @NotNull
    public abstract List<AppFunctionInvoker> getInvokers();

    @Override // androidx.appfunctions.service.internal.AppFunctionInvoker
    @NotNull
    public final Set<String> getSupportedFunctionIds() {
        return (Set) this.supportedFunctionIds$delegate.getValue();
    }

    @Override // androidx.appfunctions.service.internal.AppFunctionInvoker
    @Nullable
    public final Object unsafeInvoke(@NotNull AppFunctionContext appFunctionContext, @NotNull String str, @NotNull Map<String, ? extends Object> map, @NotNull Continuation<Object> continuation) throws AppFunctionFunctionNotFoundException {
        for (AppFunctionInvoker appFunctionInvoker : getInvokers()) {
            if (appFunctionInvoker.getSupportedFunctionIds().contains(str)) {
                return appFunctionInvoker.unsafeInvoke(appFunctionContext, str, map, continuation);
            }
        }
        throw new AppFunctionFunctionNotFoundException("Unable to find " + str);
    }
}
