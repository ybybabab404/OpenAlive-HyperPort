package com.google.android.appfunctions.schema.types.v1;

import androidx.appfunctions.AppFunctionSerializable;
import java.util.Objects;
import kotlin.Metadata;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
@AppFunctionSerializable
@Metadata
public final class TimeOfDay {
    public final int hours;
    public final int minutes;
    public final int nanos;
    public final int seconds;

    public TimeOfDay(int i, int i2, int i3, int i4) {
        this.hours = i;
        this.minutes = i2;
        this.seconds = i3;
        this.nanos = i4;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TimeOfDay)) {
            return false;
        }
        TimeOfDay timeOfDay = (TimeOfDay) obj;
        return this.hours == timeOfDay.hours && this.minutes == timeOfDay.minutes && this.seconds == timeOfDay.seconds && this.nanos == timeOfDay.nanos;
    }

    public final int getHours() {
        return this.hours;
    }

    public final int getMinutes() {
        return this.minutes;
    }

    public final int getNanos() {
        return this.nanos;
    }

    public final int getSeconds() {
        return this.seconds;
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.hours), Integer.valueOf(this.minutes), Integer.valueOf(this.seconds), Integer.valueOf(this.nanos));
    }
}
