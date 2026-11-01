package androidx.window.area;

import androidx.window.core.ExperimentalWindowApi;
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
/* JADX INFO: compiled from: WindowAreaCapability.kt */
/* JADX INFO: loaded from: classes.dex */
@ExperimentalWindowApi
@Metadata
public final class WindowAreaCapability {
    public final Operation operation;
    public final Status status;

    /* JADX INFO: compiled from: WindowAreaCapability.kt */
    @Metadata
    @ExperimentalWindowApi
    public static final class Operation {
        public final String description;
        public static final Companion Companion = new Companion(null);
        public static final Operation OPERATION_TRANSFER_ACTIVITY_TO_AREA = new Operation("TRANSFER");
        public static final Operation OPERATION_PRESENT_ON_AREA = new Operation("PRESENT");

        /* JADX INFO: compiled from: WindowAreaCapability.kt */
        @Metadata
        public static final class Companion {
            public Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        public Operation(String str) {
            this.description = str;
        }

        public String toString() {
            return this.description;
        }
    }

    /* JADX INFO: compiled from: WindowAreaCapability.kt */
    @Metadata
    @ExperimentalWindowApi
    public static final class Status {
        public final String description;
        public static final Companion Companion = new Companion(null);
        public static final Status WINDOW_AREA_STATUS_UNKNOWN = new Status("UNKNOWN");
        public static final Status WINDOW_AREA_STATUS_UNSUPPORTED = new Status("UNSUPPORTED");
        public static final Status WINDOW_AREA_STATUS_UNAVAILABLE = new Status("UNAVAILABLE");
        public static final Status WINDOW_AREA_STATUS_AVAILABLE = new Status("AVAILABLE");
        public static final Status WINDOW_AREA_STATUS_ACTIVE = new Status("ACTIVE");

        /* JADX INFO: compiled from: WindowAreaCapability.kt */
        @Metadata
        public static final class Companion {
            public Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        public Status(String str) {
            this.description = str;
        }

        public String toString() {
            return this.description;
        }
    }

    public WindowAreaCapability(Operation operation, Status status) {
        Intrinsics.checkNotNullParameter(operation, "operation");
        Intrinsics.checkNotNullParameter(status, "status");
        this.operation = operation;
        this.status = status;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof WindowAreaCapability)) {
            return false;
        }
        WindowAreaCapability windowAreaCapability = (WindowAreaCapability) obj;
        return Intrinsics.areEqual(this.operation, windowAreaCapability.operation) && Intrinsics.areEqual(this.status, windowAreaCapability.status);
    }

    public final Status getStatus() {
        return this.status;
    }

    public int hashCode() {
        return (this.operation.hashCode() * 31) + this.status.hashCode();
    }

    public String toString() {
        return "Operation: " + this.operation + ": Status: " + this.status;
    }
}
