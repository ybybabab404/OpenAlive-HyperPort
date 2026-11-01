package androidx.appfunctions.metadata;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: AppFunctionDataTypeMetadata.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class AppFunctionAllOfTypeMetadata extends AppFunctionDataTypeMetadata {
    public static final Companion Companion = new Companion(null);
    public final List matchAll;
    public final String qualifiedName;

    /* JADX INFO: compiled from: AppFunctionDataTypeMetadata.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppFunctionAllOfTypeMetadata(List matchAll, String str, boolean z, String description) {
        super(z, description);
        Intrinsics.checkNotNullParameter(matchAll, "matchAll");
        Intrinsics.checkNotNullParameter(description, "description");
        this.matchAll = matchAll;
        this.qualifiedName = str;
    }

    @Override // androidx.appfunctions.metadata.AppFunctionDataTypeMetadata
    public boolean equals(Object obj) {
        if (!super.equals(obj) || !(obj instanceof AppFunctionAllOfTypeMetadata)) {
            return false;
        }
        AppFunctionAllOfTypeMetadata appFunctionAllOfTypeMetadata = (AppFunctionAllOfTypeMetadata) obj;
        if (Intrinsics.areEqual(this.qualifiedName, appFunctionAllOfTypeMetadata.qualifiedName)) {
            return Intrinsics.areEqual(this.matchAll, appFunctionAllOfTypeMetadata.matchAll);
        }
        return false;
    }

    public final AppFunctionObjectTypeMetadata getPseudoObjectTypeMetadata$appfunctions_release(AppFunctionComponentsMetadata componentsMetadata) {
        Intrinsics.checkNotNullParameter(componentsMetadata, "componentsMetadata");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (AppFunctionDataTypeMetadata appFunctionDataTypeMetadata : this.matchAll) {
            if (appFunctionDataTypeMetadata instanceof AppFunctionReferenceTypeMetadata) {
                AppFunctionReferenceTypeMetadata appFunctionReferenceTypeMetadata = (AppFunctionReferenceTypeMetadata) appFunctionDataTypeMetadata;
                AppFunctionDataTypeMetadata appFunctionDataTypeMetadata2 = (AppFunctionDataTypeMetadata) componentsMetadata.getDataTypes().get(appFunctionReferenceTypeMetadata.getReferenceDataType());
                if (appFunctionDataTypeMetadata2 == null) {
                    throw new IllegalStateException("Unable to resolve the " + appFunctionReferenceTypeMetadata.getReferenceDataType());
                }
                if (appFunctionDataTypeMetadata2 instanceof AppFunctionObjectTypeMetadata) {
                    AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata = (AppFunctionObjectTypeMetadata) appFunctionDataTypeMetadata2;
                    linkedHashMap.putAll(appFunctionObjectTypeMetadata.getProperties());
                    linkedHashSet.addAll(appFunctionObjectTypeMetadata.getRequired());
                }
            } else if (appFunctionDataTypeMetadata instanceof AppFunctionObjectTypeMetadata) {
                AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata2 = (AppFunctionObjectTypeMetadata) appFunctionDataTypeMetadata;
                linkedHashMap.putAll(appFunctionObjectTypeMetadata2.getProperties());
                linkedHashSet.addAll(appFunctionObjectTypeMetadata2.getRequired());
            } else if (appFunctionDataTypeMetadata instanceof AppFunctionAllOfTypeMetadata) {
                AppFunctionObjectTypeMetadata pseudoObjectTypeMetadata$appfunctions_release = ((AppFunctionAllOfTypeMetadata) appFunctionDataTypeMetadata).getPseudoObjectTypeMetadata$appfunctions_release(componentsMetadata);
                linkedHashMap.putAll(pseudoObjectTypeMetadata$appfunctions_release.getProperties());
                linkedHashSet.addAll(pseudoObjectTypeMetadata$appfunctions_release.getRequired());
            }
        }
        return new AppFunctionObjectTypeMetadata(linkedHashMap, CollectionsKt.toList(linkedHashSet), null, false, "");
    }

    @Override // androidx.appfunctions.metadata.AppFunctionDataTypeMetadata
    public int hashCode() {
        int iHashCode = (super.hashCode() * 31) + this.matchAll.hashCode();
        String str = this.qualifiedName;
        return str != null ? (iHashCode * 31) + str.hashCode() : iHashCode;
    }

    @Override // androidx.appfunctions.metadata.AppFunctionDataTypeMetadata
    public String toString() {
        return "AppFunctionAllOfTypeMetadata(matchAll=" + this.matchAll + ", isNullable=" + isNullable() + ", description=" + getDescription() + ')';
    }
}
