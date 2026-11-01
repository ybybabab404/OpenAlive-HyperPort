package androidx.appfunctions.metadata;

import java.util.List;
import java.util.Objects;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
/* JADX INFO: compiled from: AppFunctionMetadata.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class AppFunctionMetadata {
    public final AppFunctionComponentsMetadata components;
    public final String description;
    public final String id;
    public final boolean isEnabled;
    public final String packageName;
    public final List parameters;
    public final AppFunctionResponseMetadata response;
    public final AppFunctionSchemaMetadata schema;

    public AppFunctionMetadata(String id, String packageName, boolean z, AppFunctionSchemaMetadata appFunctionSchemaMetadata, List parameters, AppFunctionResponseMetadata response, AppFunctionComponentsMetadata components, String description) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(packageName, "packageName");
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        Intrinsics.checkNotNullParameter(response, "response");
        Intrinsics.checkNotNullParameter(components, "components");
        Intrinsics.checkNotNullParameter(description, "description");
        this.id = id;
        this.packageName = packageName;
        this.isEnabled = z;
        this.schema = appFunctionSchemaMetadata;
        this.parameters = parameters;
        this.response = response;
        this.components = components;
        this.description = description;
    }

    public /* synthetic */ AppFunctionMetadata(String str, String str2, boolean z, AppFunctionSchemaMetadata appFunctionSchemaMetadata, List list, AppFunctionResponseMetadata appFunctionResponseMetadata, AppFunctionComponentsMetadata appFunctionComponentsMetadata, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, z, appFunctionSchemaMetadata, list, appFunctionResponseMetadata, (i & 64) != 0 ? new AppFunctionComponentsMetadata(null, 1, null) : appFunctionComponentsMetadata, (i & 128) != 0 ? "" : str3);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Intrinsics.areEqual(AppFunctionMetadata.class, obj != null ? obj.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type androidx.appfunctions.metadata.AppFunctionMetadata");
        AppFunctionMetadata appFunctionMetadata = (AppFunctionMetadata) obj;
        return Intrinsics.areEqual(this.id, appFunctionMetadata.id) && this.isEnabled == appFunctionMetadata.isEnabled && Intrinsics.areEqual(this.packageName, appFunctionMetadata.packageName) && Intrinsics.areEqual(this.schema, appFunctionMetadata.schema) && Intrinsics.areEqual(this.parameters, appFunctionMetadata.parameters) && Intrinsics.areEqual(this.response, appFunctionMetadata.response) && Intrinsics.areEqual(this.components, appFunctionMetadata.components) && Intrinsics.areEqual(this.description, appFunctionMetadata.description);
    }

    public final AppFunctionComponentsMetadata getComponents() {
        return this.components;
    }

    public final String getPackageName() {
        return this.packageName;
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
        return Objects.hash(Boolean.valueOf(this.isEnabled), this.id, this.packageName, this.schema, this.parameters, this.response, this.components, this.description);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("AppFunctionMetadata(");
        sb.append("id='" + this.id + "', ");
        sb.append("packageName='" + this.packageName + "', ");
        sb.append("isEnabled=" + this.isEnabled + ", ");
        sb.append("schema=" + this.schema + ", ");
        sb.append("parameters=" + this.parameters + ", ");
        sb.append("response=" + this.response + ", ");
        StringBuilder sb2 = new StringBuilder();
        sb2.append("components=");
        sb2.append(this.components);
        sb.append(sb2.toString());
        sb.append("description=" + this.description);
        sb.append(")");
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
