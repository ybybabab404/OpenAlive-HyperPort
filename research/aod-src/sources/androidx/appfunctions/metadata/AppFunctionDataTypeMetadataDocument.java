package androidx.appfunctions.metadata;

import androidx.appsearch.annotation.Document;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
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
/* JADX INFO: compiled from: AppFunctionDataTypeMetadata.kt */
/* JADX INFO: loaded from: classes.dex */
@Document
@Metadata
@SourceDebugExtension
public final class AppFunctionDataTypeMetadataDocument {
    public final List allOf;
    public final String dataTypeReference;
    public final String description;
    public final List enumValues;
    public final String id;
    public final boolean isNullable;
    public final AppFunctionDataTypeMetadataDocument itemType;
    public final String namespace;
    public final String objectQualifiedName;
    public final List properties;
    public final List required;
    public final int type;

    public AppFunctionDataTypeMetadataDocument(String namespace, String id, int i, AppFunctionDataTypeMetadataDocument appFunctionDataTypeMetadataDocument, List properties, List allOf, List required, String str, boolean z, String str2, String str3, List enumValues) {
        Intrinsics.checkNotNullParameter(namespace, "namespace");
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(properties, "properties");
        Intrinsics.checkNotNullParameter(allOf, "allOf");
        Intrinsics.checkNotNullParameter(required, "required");
        Intrinsics.checkNotNullParameter(enumValues, "enumValues");
        this.namespace = namespace;
        this.id = id;
        this.type = i;
        this.itemType = appFunctionDataTypeMetadataDocument;
        this.properties = properties;
        this.allOf = allOf;
        this.required = required;
        this.dataTypeReference = str;
        this.isNullable = z;
        this.objectQualifiedName = str2;
        this.description = str3;
        this.enumValues = enumValues;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppFunctionDataTypeMetadataDocument)) {
            return false;
        }
        AppFunctionDataTypeMetadataDocument appFunctionDataTypeMetadataDocument = (AppFunctionDataTypeMetadataDocument) obj;
        return Intrinsics.areEqual(this.namespace, appFunctionDataTypeMetadataDocument.namespace) && Intrinsics.areEqual(this.id, appFunctionDataTypeMetadataDocument.id) && this.type == appFunctionDataTypeMetadataDocument.type && Intrinsics.areEqual(this.itemType, appFunctionDataTypeMetadataDocument.itemType) && Intrinsics.areEqual(this.properties, appFunctionDataTypeMetadataDocument.properties) && Intrinsics.areEqual(this.allOf, appFunctionDataTypeMetadataDocument.allOf) && Intrinsics.areEqual(this.required, appFunctionDataTypeMetadataDocument.required) && Intrinsics.areEqual(this.dataTypeReference, appFunctionDataTypeMetadataDocument.dataTypeReference) && this.isNullable == appFunctionDataTypeMetadataDocument.isNullable && Intrinsics.areEqual(this.objectQualifiedName, appFunctionDataTypeMetadataDocument.objectQualifiedName) && Intrinsics.areEqual(this.description, appFunctionDataTypeMetadataDocument.description) && Intrinsics.areEqual(this.enumValues, appFunctionDataTypeMetadataDocument.enumValues);
    }

    public int hashCode() {
        int iHashCode = ((((this.namespace.hashCode() * 31) + this.id.hashCode()) * 31) + Integer.hashCode(this.type)) * 31;
        AppFunctionDataTypeMetadataDocument appFunctionDataTypeMetadataDocument = this.itemType;
        int iHashCode2 = (((((((iHashCode + (appFunctionDataTypeMetadataDocument == null ? 0 : appFunctionDataTypeMetadataDocument.hashCode())) * 31) + this.properties.hashCode()) * 31) + this.allOf.hashCode()) * 31) + this.required.hashCode()) * 31;
        String str = this.dataTypeReference;
        int iHashCode3 = (((iHashCode2 + (str == null ? 0 : str.hashCode())) * 31) + Boolean.hashCode(this.isNullable)) * 31;
        String str2 = this.objectQualifiedName;
        int iHashCode4 = (iHashCode3 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.description;
        return ((iHashCode4 + (str3 != null ? str3.hashCode() : 0)) * 31) + this.enumValues.hashCode();
    }

    public final AppFunctionDataTypeMetadata toAppFunctionDataTypeMetadata() {
        String str;
        switch (this.type) {
            case 0:
                boolean z = this.isNullable;
                String str2 = this.description;
                return new AppFunctionUnitTypeMetadata(z, str2 != null ? str2 : "");
            case 1:
                boolean z2 = this.isNullable;
                String str3 = this.description;
                return new AppFunctionBooleanTypeMetadata(z2, str3 != null ? str3 : "");
            case 2:
                boolean z3 = this.isNullable;
                String str4 = this.description;
                return new AppFunctionBytesTypeMetadata(z3, str4 != null ? str4 : "");
            case 3:
                if (this.properties.isEmpty()) {
                    throw new IllegalStateException("Properties must be present for object type can't be empty");
                }
                List<AppFunctionNamedDataTypeMetadataDocument> list = this.properties;
                LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
                for (AppFunctionNamedDataTypeMetadataDocument appFunctionNamedDataTypeMetadataDocument : list) {
                    Pair pair = TuplesKt.to(appFunctionNamedDataTypeMetadataDocument.getName(), appFunctionNamedDataTypeMetadataDocument.getDataTypeMetadata().toAppFunctionDataTypeMetadata());
                    linkedHashMap.put(pair.getFirst(), pair.getSecond());
                }
                List list2 = this.required;
                String str5 = this.objectQualifiedName;
                boolean z4 = this.isNullable;
                String str6 = this.description;
                return new AppFunctionObjectTypeMetadata(linkedHashMap, list2, str5, z4, str6 == null ? "" : str6);
            case 4:
                boolean z5 = this.isNullable;
                String str7 = this.description;
                return new AppFunctionDoubleTypeMetadata(z5, str7 != null ? str7 : "");
            case 5:
                boolean z6 = this.isNullable;
                String str8 = this.description;
                return new AppFunctionFloatTypeMetadata(z6, str8 != null ? str8 : "");
            case 6:
                boolean z7 = this.isNullable;
                String str9 = this.description;
                return new AppFunctionLongTypeMetadata(z7, str9 != null ? str9 : "");
            case 7:
                boolean z8 = this.isNullable;
                String str10 = this.description;
                str = str10 != null ? str10 : "";
                List list3 = this.enumValues;
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                Iterator it = list3.iterator();
                while (it.hasNext()) {
                    arrayList.add(Integer.valueOf(Integer.parseInt((String) it.next())));
                }
                Set set = CollectionsKt.toSet(arrayList);
                return new AppFunctionIntTypeMetadata(z8, str, set.isEmpty() ? null : set);
            case 8:
                boolean z9 = this.isNullable;
                String str11 = this.description;
                str = str11 != null ? str11 : "";
                Set set2 = CollectionsKt.toSet(this.enumValues);
                return new AppFunctionStringTypeMetadata(z9, str, set2.isEmpty() ? null : set2);
            case 9:
            default:
                throw new IllegalArgumentException("Unknown type: " + this.type);
            case 10:
                AppFunctionDataTypeMetadataDocument appFunctionDataTypeMetadataDocument = this.itemType;
                if (appFunctionDataTypeMetadataDocument == null) {
                    throw new IllegalStateException("Item type must be present for array type");
                }
                AppFunctionDataTypeMetadata appFunctionDataTypeMetadata = appFunctionDataTypeMetadataDocument.toAppFunctionDataTypeMetadata();
                boolean z10 = this.isNullable;
                String str12 = this.description;
                return new AppFunctionArrayTypeMetadata(appFunctionDataTypeMetadata, z10, str12 != null ? str12 : "");
            case 11:
                String str13 = this.dataTypeReference;
                if (str13 == null) {
                    throw new IllegalStateException("Data type reference must be present for reference type");
                }
                boolean z11 = this.isNullable;
                String str14 = this.description;
                return new AppFunctionReferenceTypeMetadata(str13, z11, str14 != null ? str14 : "");
            case 12:
                List list4 = this.allOf;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list4, 10));
                Iterator it2 = list4.iterator();
                while (it2.hasNext()) {
                    arrayList2.add(((AppFunctionDataTypeMetadataDocument) it2.next()).toAppFunctionDataTypeMetadata());
                }
                String str15 = this.objectQualifiedName;
                boolean z12 = this.isNullable;
                String str16 = this.description;
                return new AppFunctionAllOfTypeMetadata(arrayList2, str15, z12, str16 != null ? str16 : "");
            case 13:
                boolean z13 = this.isNullable;
                String str17 = this.description;
                return new AppFunctionPendingIntentTypeMetadata(z13, str17 != null ? str17 : "");
        }
    }

    public String toString() {
        return "AppFunctionDataTypeMetadataDocument(namespace=" + this.namespace + ", id=" + this.id + ", type=" + this.type + ", itemType=" + this.itemType + ", properties=" + this.properties + ", allOf=" + this.allOf + ", required=" + this.required + ", dataTypeReference=" + this.dataTypeReference + ", isNullable=" + this.isNullable + ", objectQualifiedName=" + this.objectQualifiedName + ", description=" + this.description + ", enumValues=" + this.enumValues + ')';
    }
}
