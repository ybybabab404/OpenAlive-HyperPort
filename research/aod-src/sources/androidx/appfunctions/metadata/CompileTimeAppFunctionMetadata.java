package androidx.appfunctions.metadata;

import androidx.annotation.RestrictTo;
import java.util.List;
import kotlin.Metadata;
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
    */
/* JADX INFO: compiled from: AppFunctionMetadata.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@RestrictTo
@SourceDebugExtension
public final class CompileTimeAppFunctionMetadata {
    public final AppFunctionComponentsMetadata components;
    public final String description;
    public final String id;
    public final boolean isEnabledByDefault;
    public final List parameters;
    public final AppFunctionResponseMetadata response;
    public final AppFunctionSchemaMetadata schema;

    public CompileTimeAppFunctionMetadata(String id, boolean z, AppFunctionSchemaMetadata appFunctionSchemaMetadata, List parameters, AppFunctionResponseMetadata response, AppFunctionComponentsMetadata components, String description) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(response, "response");
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(description, "description");
        this.id = id;
        this.isEnabledByDefault = z;
        this.schema = appFunctionSchemaMetadata;
        this.parameters = parameters;
        this.response = response;
        this.components = components;
        this.description = description;
    }

    public /* synthetic */ CompileTimeAppFunctionMetadata(String str, boolean z, AppFunctionSchemaMetadata appFunctionSchemaMetadata, List list, AppFunctionResponseMetadata appFunctionResponseMetadata, AppFunctionComponentsMetadata appFunctionComponentsMetadata, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, z, appFunctionSchemaMetadata, list, appFunctionResponseMetadata, (i & 32) != 0 ? new AppFunctionComponentsMetadata(null, 1, null) : appFunctionComponentsMetadata, (i & 64) != 0 ? "" : str2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CompileTimeAppFunctionMetadata)) {
            return false;
        }
        CompileTimeAppFunctionMetadata compileTimeAppFunctionMetadata = (CompileTimeAppFunctionMetadata) obj;
        return Intrinsics.areEqual(this.id, compileTimeAppFunctionMetadata.id) && this.isEnabledByDefault == compileTimeAppFunctionMetadata.isEnabledByDefault && Intrinsics.areEqual(this.schema, compileTimeAppFunctionMetadata.schema) && Intrinsics.areEqual(this.parameters, compileTimeAppFunctionMetadata.parameters) && Intrinsics.areEqual(this.response, compileTimeAppFunctionMetadata.response) && Intrinsics.areEqual(this.components, compileTimeAppFunctionMetadata.components) && Intrinsics.areEqual(this.description, compileTimeAppFunctionMetadata.description);
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getId() {
        return this.id;
    }

    public final List getParameters() {
        return this.parameters;
    }

    public final AppFunctionResponseMetadata getResponse() {
        return this.response;
    }

    public final AppFunctionSchemaMetadata getSchema() {
        return this.schema;
    }

    public int hashCode() {
        int iHashCode = ((this.id.hashCode() * 31) + Boolean.hashCode(this.isEnabledByDefault)) * 31;
        AppFunctionSchemaMetadata appFunctionSchemaMetadata = this.schema;
        return ((((((((iHashCode + (appFunctionSchemaMetadata == null ? 0 : appFunctionSchemaMetadata.hashCode())) * 31) + this.parameters.hashCode()) * 31) + this.response.hashCode()) * 31) + this.components.hashCode()) * 31) + this.description.hashCode();
    }

    public final boolean isEnabledByDefault() {
        return this.isEnabledByDefault;
    }

    public String toString() {
        return "CompileTimeAppFunctionMetadata(id=" + this.id + ", isEnabledByDefault=" + this.isEnabledByDefault + ", schema=" + this.schema + ", parameters=" + this.parameters + ", response=" + this.response + ", components=" + this.components + ", description=" + this.description + ')';
    }
}
