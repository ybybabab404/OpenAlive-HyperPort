package androidx.window.area.utils;

import android.util.DisplayMetrics;
import androidx.annotation.RequiresApi;
import kotlin.Metadata;
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
/* JADX INFO: compiled from: DeviceMetrics.kt */
/* JADX INFO: loaded from: classes.dex */
@RequiresApi
@Metadata
public final class DeviceMetrics {
    public final String manufacturer;
    public final String model;
    public final DisplayMetrics rearDisplayMetrics;

    public DeviceMetrics(String manufacturer, String model, DisplayMetrics rearDisplayMetrics) {
        Intrinsics.checkNotNullParameter(manufacturer, "manufacturer");
        Intrinsics.checkNotNullParameter(model, "model");
        Intrinsics.checkNotNullParameter(rearDisplayMetrics, "rearDisplayMetrics");
        this.manufacturer = manufacturer;
        this.model = model;
        this.rearDisplayMetrics = rearDisplayMetrics;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DeviceMetrics)) {
            return false;
        }
        DeviceMetrics deviceMetrics = (DeviceMetrics) obj;
        return Intrinsics.areEqual(this.manufacturer, deviceMetrics.manufacturer) && Intrinsics.areEqual(this.model, deviceMetrics.model) && this.rearDisplayMetrics.equals(deviceMetrics.rearDisplayMetrics);
    }

    public final String getManufacturer() {
        return this.manufacturer;
    }

    public final String getModel() {
        return this.model;
    }

    public final DisplayMetrics getRearDisplayMetrics() {
        return this.rearDisplayMetrics;
    }

    public int hashCode() {
        return (((this.manufacturer.hashCode() * 31) + this.model.hashCode()) * 31) + this.rearDisplayMetrics.hashCode();
    }

    public String toString() {
        return "DeviceMetrics{ Manufacturer: " + this.manufacturer + ", model: " + this.model + ", Rear display metrics: " + this.rearDisplayMetrics + " }";
    }
}
