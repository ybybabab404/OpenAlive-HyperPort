package com.google.android.appfunctions.schema.types.v1;

import androidx.appfunctions.AppFunctionSerializable;
import java.util.Objects;
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
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
@AppFunctionSerializable
@Metadata
public final class DateTime {
    public final Date date;
    public final TimeOfDay timeOfDay;
    public final String timeZone;

    public DateTime(String timeZone, Date date, TimeOfDay timeOfDay) {
        Intrinsics.checkNotNullParameter(timeZone, "timeZone");
        Intrinsics.checkNotNullParameter(date, "date");
        Intrinsics.checkNotNullParameter(timeOfDay, "timeOfDay");
        this.timeZone = timeZone;
        this.date = date;
        this.timeOfDay = timeOfDay;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof DateTime)) {
            return false;
        }
        DateTime dateTime = (DateTime) obj;
        return Intrinsics.areEqual(this.timeZone, dateTime.timeZone) && Intrinsics.areEqual(this.date, dateTime.date) && Intrinsics.areEqual(this.timeOfDay, dateTime.timeOfDay);
    }

    public final Date getDate() {
        return this.date;
    }

    public final TimeOfDay getTimeOfDay() {
        return this.timeOfDay;
    }

    public final String getTimeZone() {
        return this.timeZone;
    }

    public int hashCode() {
        return Objects.hash(this.timeZone, this.date, this.timeOfDay);
    }
}
