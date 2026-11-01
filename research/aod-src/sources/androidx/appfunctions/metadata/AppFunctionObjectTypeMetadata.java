package androidx.appfunctions.metadata;

import java.util.List;
import java.util.Map;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: AppFunctionDataTypeMetadata.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class AppFunctionObjectTypeMetadata extends AppFunctionDataTypeMetadata {
    public static final Companion Companion = new Companion(null);
    public final Map properties;
    public final String qualifiedName;
    public final List required;

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
    public AppFunctionObjectTypeMetadata(Map properties, List required, String str, boolean z, String description) {
        super(z, description);
        Intrinsics.checkNotNullParameter(properties, "properties");
        Intrinsics.checkNotNullParameter(required, "required");
        Intrinsics.checkNotNullParameter(description, "description");
        this.properties = properties;
        this.required = required;
        this.qualifiedName = str;
    }

    public /* synthetic */ AppFunctionObjectTypeMetadata(Map map, List list, String str, boolean z, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(map, list, str, z, (i & 16) != 0 ? "" : str2);
    }

    @Override // androidx.appfunctions.metadata.AppFunctionDataTypeMetadata
    public boolean equals(Object obj) {
        if (!super.equals(obj) || !(obj instanceof AppFunctionObjectTypeMetadata)) {
            return false;
        }
        AppFunctionObjectTypeMetadata appFunctionObjectTypeMetadata = (AppFunctionObjectTypeMetadata) obj;
        return Intrinsics.areEqual(this.properties, appFunctionObjectTypeMetadata.properties) && Intrinsics.areEqual(this.required, appFunctionObjectTypeMetadata.required) && Intrinsics.areEqual(this.qualifiedName, appFunctionObjectTypeMetadata.qualifiedName);
    }

    public final Map getProperties() {
        return this.properties;
    }

    public final String getQualifiedName() {
        return this.qualifiedName;
    }

    public final List getRequired() {
        return this.required;
    }

    @Override // androidx.appfunctions.metadata.AppFunctionDataTypeMetadata
    public int hashCode() {
        int iHashCode = (((super.hashCode() * 31) + this.properties.hashCode()) * 31) + this.required.hashCode();
        String str = this.qualifiedName;
        return str != null ? (iHashCode * 31) + str.hashCode() : iHashCode;
    }

    @Override // androidx.appfunctions.metadata.AppFunctionDataTypeMetadata
    public String toString() {
        return "AppFunctionObjectTypeMetadata(properties=" + this.properties + ", required=" + this.required + ", qualifiedName=" + this.qualifiedName + ", isNullable=" + isNullable() + ",description=" + getDescription() + ')';
    }
}
