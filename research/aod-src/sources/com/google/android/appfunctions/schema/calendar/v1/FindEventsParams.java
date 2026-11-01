package com.google.android.appfunctions.schema.calendar.v1;

import androidx.appfunctions.AppFunctionSerializable;
import com.google.android.appfunctions.schema.types.v1.DateTime;
import java.util.List;
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
public final class FindEventsParams {
    public final List attendeeIds;
    public final DateTime endDate;
    public final int maxCount;
    public final String orderBy;
    public final String query;
    public final DateTime startDate;

    public FindEventsParams(DateTime dateTime, DateTime dateTime2, String str, List attendeeIds, int i, String str2) {
        Intrinsics.checkNotNullParameter(attendeeIds, "attendeeIds");
        this.startDate = dateTime;
        this.endDate = dateTime2;
        this.query = str;
        this.attendeeIds = attendeeIds;
        this.maxCount = i;
        this.orderBy = str2;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FindEventsParams)) {
            return false;
        }
        FindEventsParams findEventsParams = (FindEventsParams) obj;
        return Intrinsics.areEqual(this.startDate, findEventsParams.startDate) && Intrinsics.areEqual(this.endDate, findEventsParams.endDate) && Intrinsics.areEqual(this.query, findEventsParams.query) && Intrinsics.areEqual(this.attendeeIds, findEventsParams.attendeeIds) && this.maxCount == findEventsParams.maxCount && Intrinsics.areEqual(this.orderBy, findEventsParams.orderBy);
    }

    public final List getAttendeeIds() {
        return this.attendeeIds;
    }

    public final DateTime getEndDate() {
        return this.endDate;
    }

    public final int getMaxCount() {
        return this.maxCount;
    }

    public final String getOrderBy() {
        return this.orderBy;
    }

    public final String getQuery() {
        return this.query;
    }

    public final DateTime getStartDate() {
        return this.startDate;
    }

    public int hashCode() {
        return Objects.hash(this.startDate, this.endDate, this.query, this.attendeeIds, Integer.valueOf(this.maxCount), this.orderBy);
    }
}
