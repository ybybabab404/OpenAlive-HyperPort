package androidx.appfunctions.internal.serializableproxies;

import androidx.annotation.RequiresApi;
import androidx.annotation.RestrictTo;
import androidx.appfunctions.AppFunctionSerializableProxy;
import java.time.ZoneId;
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
/* JADX INFO: compiled from: AppFunctionZoneId.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@RequiresApi
@AppFunctionSerializableProxy
@RestrictTo
public final class AppFunctionZoneId {
    public static final Companion Companion = new Companion(null);
    public final String zoneID;

    /* JADX INFO: compiled from: AppFunctionZoneId.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final AppFunctionZoneId fromZoneId(ZoneId zoneId) {
            Intrinsics.checkNotNullParameter(zoneId, "zoneId");
            String id = zoneId.getId();
            Intrinsics.checkNotNullExpressionValue(id, "getId(...)");
            return new AppFunctionZoneId(id);
        }
    }

    public AppFunctionZoneId(String zoneID) {
        Intrinsics.checkNotNullParameter(zoneID, "zoneID");
        this.zoneID = zoneID;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof AppFunctionZoneId) && Intrinsics.areEqual(this.zoneID, ((AppFunctionZoneId) obj).zoneID);
    }

    public final String getZoneID() {
        return this.zoneID;
    }

    public int hashCode() {
        return this.zoneID.hashCode();
    }

    public String toString() {
        return "AppFunctionZoneId(zoneID=" + this.zoneID + ')';
    }

    public final ZoneId toZoneId() {
        ZoneId zoneIdOf = ZoneId.of(this.zoneID);
        Intrinsics.checkNotNullExpressionValue(zoneIdOf, "of(...)");
        return zoneIdOf;
    }
}
