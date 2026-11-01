package androidx.window.area;

import android.os.Binder;
import androidx.window.core.ExperimentalWindowApi;
import androidx.window.extensions.area.WindowAreaComponent;
import androidx.window.layout.WindowMetrics;
import java.util.HashMap;
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
/* JADX INFO: compiled from: WindowAreaInfo.kt */
/* JADX INFO: loaded from: classes.dex */
@ExperimentalWindowApi
@Metadata
public final class WindowAreaInfo {
    public final HashMap capabilityMap;
    public WindowMetrics metrics;
    public final Binder token;
    public final Type type;
    public final WindowAreaComponent windowAreaComponent;

    /* JADX INFO: compiled from: WindowAreaInfo.kt */
    @Metadata
    @ExperimentalWindowApi
    public static final class Type {
        public static final Companion Companion = new Companion(null);
        public static final Type TYPE_REAR_FACING = new Type("REAR FACING");
        public final String description;

        /* JADX INFO: compiled from: WindowAreaInfo.kt */
        @Metadata
        public static final class Companion {
            public Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        public Type(String str) {
            this.description = str;
        }

        public String toString() {
            return this.description;
        }
    }

    public WindowAreaInfo(WindowMetrics metrics, Type type, Binder token, WindowAreaComponent windowAreaComponent) {
        Intrinsics.checkNotNullParameter(metrics, "metrics");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(token, "token");
        Intrinsics.checkNotNullParameter(windowAreaComponent, "windowAreaComponent");
        this.metrics = metrics;
        this.type = type;
        this.token = token;
        this.windowAreaComponent = windowAreaComponent;
        this.capabilityMap = new HashMap();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof WindowAreaInfo)) {
            return false;
        }
        WindowAreaInfo windowAreaInfo = (WindowAreaInfo) obj;
        return Intrinsics.areEqual(this.metrics, windowAreaInfo.metrics) && Intrinsics.areEqual(this.type, windowAreaInfo.type) && Intrinsics.areEqual(this.capabilityMap.entrySet(), windowAreaInfo.capabilityMap.entrySet());
    }

    public final HashMap getCapabilityMap$window_release() {
        return this.capabilityMap;
    }

    public int hashCode() {
        return (((this.metrics.hashCode() * 31) + this.type.hashCode()) * 31) + this.capabilityMap.entrySet().hashCode();
    }

    public final void setMetrics(WindowMetrics windowMetrics) {
        Intrinsics.checkNotNullParameter(windowMetrics, "<set-?>");
        this.metrics = windowMetrics;
    }

    public String toString() {
        return "WindowAreaInfo{ Metrics: " + this.metrics + ", type: " + this.type + ", Capabilities: " + this.capabilityMap.entrySet() + " }";
    }
}
