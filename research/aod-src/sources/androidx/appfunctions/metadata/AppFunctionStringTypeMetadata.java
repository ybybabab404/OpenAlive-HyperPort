package androidx.appfunctions.metadata;

import java.util.Set;
import kotlin.Metadata;
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
/* JADX INFO: compiled from: AppFunctionDataTypeMetadata.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class AppFunctionStringTypeMetadata extends AppFunctionDataTypeMetadata {
    public final Set enumValues;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AppFunctionStringTypeMetadata(boolean z, String description, Set set) {
        super(z, description);
        Intrinsics.checkNotNullParameter(description, "description");
        this.enumValues = set;
        if (set != null && set.isEmpty()) {
            throw new IllegalArgumentException("If specified, enumValues cannot be empty.");
        }
    }

    @Override // androidx.appfunctions.metadata.AppFunctionDataTypeMetadata
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AppFunctionStringTypeMetadata) {
            return super.equals(obj);
        }
        return false;
    }

    public final Set getEnumValues() {
        return this.enumValues;
    }

    @Override // androidx.appfunctions.metadata.AppFunctionDataTypeMetadata
    public int hashCode() {
        return super.hashCode();
    }

    @Override // androidx.appfunctions.metadata.AppFunctionDataTypeMetadata
    public String toString() {
        return "AppFunctionStringTypeMetadata(isNullable=" + isNullable() + ", description=" + getDescription() + ')';
    }
}
