package androidx.appfunctions.metadata;

import kotlin.Metadata;
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
/* JADX INFO: compiled from: AppFunctionParameterMetadata.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class AppFunctionParameterMetadata {
    public final AppFunctionDataTypeMetadata dataType;
    public final String description;
    public final boolean isRequired;
    public final String name;

    public AppFunctionParameterMetadata(String name, boolean z, AppFunctionDataTypeMetadata dataType, String description) {
        Intrinsics.checkNotNullParameter(name, "name");
        Intrinsics.checkNotNullParameter(dataType, "dataType");
        Intrinsics.checkNotNullParameter(description, "description");
        this.name = name;
        this.isRequired = z;
        this.dataType = dataType;
        this.description = description;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Intrinsics.areEqual(AppFunctionParameterMetadata.class, obj != null ? obj.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type androidx.appfunctions.metadata.AppFunctionParameterMetadata");
        AppFunctionParameterMetadata appFunctionParameterMetadata = (AppFunctionParameterMetadata) obj;
        return Intrinsics.areEqual(this.name, appFunctionParameterMetadata.name) && this.isRequired == appFunctionParameterMetadata.isRequired && Intrinsics.areEqual(this.dataType, appFunctionParameterMetadata.dataType) && Intrinsics.areEqual(this.description, appFunctionParameterMetadata.description);
    }

    public final AppFunctionDataTypeMetadata getDataType() {
        return this.dataType;
    }

    public final String getName() {
        return this.name;
    }

    public int hashCode() {
        return (((((this.name.hashCode() * 31) + Boolean.hashCode(this.isRequired)) * 31) + this.dataType.hashCode()) * 31) + this.description.hashCode();
    }

    public final boolean isRequired() {
        return this.isRequired;
    }

    public String toString() {
        return "AppFunctionParameterMetadata(name=" + this.name + ", isRequired=" + this.isRequired + ", dataType=" + this.dataType + ",description=" + this.description + ')';
    }
}
