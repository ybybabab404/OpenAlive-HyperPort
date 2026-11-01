package androidx.appfunctions.internal;

import androidx.annotation.RestrictTo;
import androidx.appfunctions.metadata.AppFunctionComponentsMetadata;
import androidx.appfunctions.metadata.CompileTimeAppFunctionMetadata;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: AggregatedAppFunctionInventory.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@RestrictTo
@SourceDebugExtension
public abstract class AggregatedAppFunctionInventory implements AppFunctionInventory {

    @NotNull
    private final Lazy functionIdToMetadataMap$delegate = LazyKt.lazy(new Function0() { // from class: androidx.appfunctions.internal.AggregatedAppFunctionInventory$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return AggregatedAppFunctionInventory.functionIdToMetadataMap_delegate$lambda$1(this.f$0);
        }
    });

    @NotNull
    private final Lazy componentsMetadata$delegate = LazyKt.lazy(new Function0() { // from class: androidx.appfunctions.internal.AggregatedAppFunctionInventory$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return AggregatedAppFunctionInventory.componentsMetadata_delegate$lambda$4(this.f$0);
        }
    });

    /* JADX INFO: Access modifiers changed from: private */
    public static final AppFunctionComponentsMetadata componentsMetadata_delegate$lambda$4(AggregatedAppFunctionInventory aggregatedAppFunctionInventory) {
        if (aggregatedAppFunctionInventory.getInventories().isEmpty()) {
            return new AppFunctionComponentsMetadata(null, 1, null);
        }
        List<AppFunctionInventory> inventories = aggregatedAppFunctionInventory.getInventories();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(inventories, 10));
        Iterator<T> it = inventories.iterator();
        while (it.hasNext()) {
            arrayList.add(((AppFunctionInventory) it.next()).getComponentsMetadata().getDataTypes());
        }
        Iterator it2 = arrayList.iterator();
        if (!it2.hasNext()) {
            throw new UnsupportedOperationException("Empty collection can't be reduced.");
        }
        Object next = it2.next();
        while (it2.hasNext()) {
            next = MapsKt.plus((Map) next, (Map) it2.next());
        }
        return new AppFunctionComponentsMetadata((Map) next);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Map functionIdToMetadataMap_delegate$lambda$1(AggregatedAppFunctionInventory aggregatedAppFunctionInventory) {
        if (aggregatedAppFunctionInventory.getInventories().isEmpty()) {
            return MapsKt.emptyMap();
        }
        List<AppFunctionInventory> inventories = aggregatedAppFunctionInventory.getInventories();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(inventories, 10));
        Iterator<T> it = inventories.iterator();
        while (it.hasNext()) {
            arrayList.add(((AppFunctionInventory) it.next()).getFunctionIdToMetadataMap());
        }
        Iterator it2 = arrayList.iterator();
        if (!it2.hasNext()) {
            throw new UnsupportedOperationException("Empty collection can't be reduced.");
        }
        Object next = it2.next();
        while (it2.hasNext()) {
            next = MapsKt.plus((Map) next, (Map) it2.next());
        }
        return (Map) next;
    }

    @Override // androidx.appfunctions.internal.AppFunctionInventory
    @NotNull
    public final AppFunctionComponentsMetadata getComponentsMetadata() {
        return (AppFunctionComponentsMetadata) this.componentsMetadata$delegate.getValue();
    }

    @Override // androidx.appfunctions.internal.AppFunctionInventory
    @NotNull
    public final Map<String, CompileTimeAppFunctionMetadata> getFunctionIdToMetadataMap() {
        return (Map) this.functionIdToMetadataMap$delegate.getValue();
    }

    @NotNull
    public abstract List<AppFunctionInventory> getInventories();
}
