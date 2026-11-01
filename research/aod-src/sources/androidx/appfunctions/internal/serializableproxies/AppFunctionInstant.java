package androidx.appfunctions.internal.serializableproxies;

import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.appfunctions.AppFunctionSerializableProxy;
import java.time.Instant;
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
/* JADX INFO: compiled from: AppFunctionInstant.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@RequiresApi
@AppFunctionSerializableProxy
@RestrictTo
public final class AppFunctionInstant {
    public static final Companion Companion = new Companion(null);
    public final long epochSecond;
    public final int nanoAdjustment;

    /* JADX INFO: compiled from: AppFunctionInstant.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final AppFunctionInstant fromInstant(Instant instant) {
            Intrinsics.checkNotNullParameter(instant, "instant");
            return new AppFunctionInstant(instant.getEpochSecond(), instant.getNano());
        }
    }

    public AppFunctionInstant(long j, int i) {
        this.epochSecond = j;
        this.nanoAdjustment = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof AppFunctionInstant)) {
            return false;
        }
        AppFunctionInstant appFunctionInstant = (AppFunctionInstant) obj;
        return this.epochSecond == appFunctionInstant.epochSecond && this.nanoAdjustment == appFunctionInstant.nanoAdjustment;
    }

    public final long getEpochSecond() {
        return this.epochSecond;
    }

    public final int getNanoAdjustment() {
        return this.nanoAdjustment;
    }

    public int hashCode() {
        return (Long.hashCode(this.epochSecond) * 31) + Integer.hashCode(this.nanoAdjustment);
    }

    public final Instant toInstant() {
        Instant instantOfEpochSecond = Instant.ofEpochSecond(this.epochSecond, this.nanoAdjustment);
        Intrinsics.checkNotNullExpressionValue(instantOfEpochSecond, "ofEpochSecond(...)");
        return instantOfEpochSecond;
    }

    public String toString() {
        return "AppFunctionInstant(epochSecond=" + this.epochSecond + ", nanoAdjustment=" + this.nanoAdjustment + ')';
    }
}
