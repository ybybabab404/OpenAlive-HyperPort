package androidx.appfunctions.metadata;

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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: AppFunctionDataTypeMetadata.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public abstract class AppFunctionDataTypeMetadata {
    public static final Companion Companion = new Companion(null);
    public final String description;
    public final boolean isNullable;

    /* JADX INFO: compiled from: AppFunctionDataTypeMetadata.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public AppFunctionDataTypeMetadata(boolean z, String description) {
        Intrinsics.checkNotNullParameter(description, "description");
        this.isNullable = z;
        this.description = description;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), obj != null ? obj.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type androidx.appfunctions.metadata.AppFunctionDataTypeMetadata");
        AppFunctionDataTypeMetadata appFunctionDataTypeMetadata = (AppFunctionDataTypeMetadata) obj;
        return Intrinsics.areEqual(this.description, appFunctionDataTypeMetadata.description) && this.isNullable == appFunctionDataTypeMetadata.isNullable;
    }

    public final String getDescription() {
        return this.description;
    }

    public int hashCode() {
        return (this.description.hashCode() * 31) + Boolean.hashCode(this.isNullable);
    }

    public final boolean isNullable() {
        return this.isNullable;
    }

    public String toString() {
        return "AppFunctionDataTypeMetadata(isNullable=" + this.isNullable + ", description=" + this.description + ')';
    }
}
