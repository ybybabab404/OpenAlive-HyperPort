package androidx.appfunctions.metadata;

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
public final class AppFunctionReferenceTypeMetadata extends AppFunctionDataTypeMetadata {
    public static final Companion Companion = new Companion(null);
    public final String referenceDataType;

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
    public AppFunctionReferenceTypeMetadata(String referenceDataType, boolean z, String description) {
        super(z, description);
        Intrinsics.checkNotNullParameter(referenceDataType, "referenceDataType");
        Intrinsics.checkNotNullParameter(description, "description");
        this.referenceDataType = referenceDataType;
    }

    @Override // androidx.appfunctions.metadata.AppFunctionDataTypeMetadata
    public boolean equals(Object obj) {
        if (super.equals(obj) && (obj instanceof AppFunctionReferenceTypeMetadata)) {
            return Intrinsics.areEqual(this.referenceDataType, ((AppFunctionReferenceTypeMetadata) obj).referenceDataType);
        }
        return false;
    }

    public final String getReferenceDataType() {
        return this.referenceDataType;
    }

    @Override // androidx.appfunctions.metadata.AppFunctionDataTypeMetadata
    public int hashCode() {
        return (super.hashCode() * 31) + this.referenceDataType.hashCode();
    }

    @Override // androidx.appfunctions.metadata.AppFunctionDataTypeMetadata
    public String toString() {
        return "AppFunctionReferenceTypeMetadata(referenceDataType=" + this.referenceDataType + ", isNullable=" + isNullable() + ",description=" + getDescription() + ')';
    }
}
