package androidx.appfunctions.metadata;

import androidx.appsearch.annotation.Document;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.ranges.RangesKt;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: AppFunctionComponentsMetadata.kt */
/* JADX INFO: loaded from: classes.dex */
@Document
@Metadata
@SourceDebugExtension
public final class AppFunctionComponentsMetadataDocument {
    public final List dataTypes;
    public final String id;
    public final String namespace;

    public AppFunctionComponentsMetadataDocument(String namespace, String id, List dataTypes) {
        Intrinsics.checkNotNullParameter(namespace, "namespace");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(dataTypes, "dataTypes");
        this.namespace = namespace;
        this.id = id;
        this.dataTypes = dataTypes;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppFunctionComponentsMetadataDocument)) {
            return false;
        }
        AppFunctionComponentsMetadataDocument appFunctionComponentsMetadataDocument = (AppFunctionComponentsMetadataDocument) obj;
        return Intrinsics.areEqual(this.namespace, appFunctionComponentsMetadataDocument.namespace) && Intrinsics.areEqual(this.id, appFunctionComponentsMetadataDocument.id) && Intrinsics.areEqual(this.dataTypes, appFunctionComponentsMetadataDocument.dataTypes);
    }

    public int hashCode() {
        return (((this.namespace.hashCode() * 31) + this.id.hashCode()) * 31) + this.dataTypes.hashCode();
    }

    public final AppFunctionComponentsMetadata toAppFunctionComponentsMetadata() {
        List<AppFunctionNamedDataTypeMetadataDocument> list = this.dataTypes;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (AppFunctionNamedDataTypeMetadataDocument appFunctionNamedDataTypeMetadataDocument : list) {
            Pair pair = TuplesKt.to(appFunctionNamedDataTypeMetadataDocument.getName(), appFunctionNamedDataTypeMetadataDocument.getDataTypeMetadata().toAppFunctionDataTypeMetadata());
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        return new AppFunctionComponentsMetadata(linkedHashMap);
    }

    public String toString() {
        return "AppFunctionComponentsMetadataDocument(namespace=" + this.namespace + ", id=" + this.id + ", dataTypes=" + this.dataTypes + ')';
    }
}
