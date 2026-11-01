package com.google.android.appfunctions.schema.calendar.v1;

import androidx.appfunctions.AppFunctionSerializable;
import com.google.android.appfunctions.schema.types.v1.SetField;
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
public final class UpdateEventParams {
    public final SetField allDay;
    public final SetField attendeeIds;
    public final SetField description;
    public final SetField endDate;
    public final String eventId;
    public final SetField eventStatus;
    public final SetField location;
    public final SetField recurrenceSchedule;
    public final SetField startDate;
    public final SetField title;

    public UpdateEventParams(String eventId, SetField setField, SetField setField2, SetField setField3, SetField setField4, SetField setField5, SetField setField6, SetField setField7, SetField setField8, SetField setField9) {
        Intrinsics.checkNotNullParameter(eventId, "eventId");
        this.eventId = eventId;
        this.title = setField;
        this.description = setField2;
        this.startDate = setField3;
        this.endDate = setField4;
        this.attendeeIds = setField5;
        this.allDay = setField6;
        this.location = setField7;
        this.recurrenceSchedule = setField8;
        this.eventStatus = setField9;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof UpdateEventParams)) {
            return false;
        }
        UpdateEventParams updateEventParams = (UpdateEventParams) obj;
        return Intrinsics.areEqual(this.eventId, updateEventParams.eventId) && Intrinsics.areEqual(this.title, updateEventParams.title) && Intrinsics.areEqual(this.description, updateEventParams.description) && Intrinsics.areEqual(this.startDate, updateEventParams.startDate) && Intrinsics.areEqual(this.endDate, updateEventParams.endDate) && Intrinsics.areEqual(this.attendeeIds, updateEventParams.attendeeIds) && Intrinsics.areEqual(this.allDay, updateEventParams.allDay) && Intrinsics.areEqual(this.location, updateEventParams.location) && Intrinsics.areEqual(this.recurrenceSchedule, updateEventParams.recurrenceSchedule) && Intrinsics.areEqual(this.eventStatus, updateEventParams.eventStatus);
    }

    public final SetField getAllDay() {
        return this.allDay;
    }

    public final SetField getAttendeeIds() {
        return this.attendeeIds;
    }

    public final SetField getDescription() {
        return this.description;
    }

    public final SetField getEndDate() {
        return this.endDate;
    }

    public final String getEventId() {
        return this.eventId;
    }

    public final SetField getEventStatus() {
        return this.eventStatus;
    }

    public final SetField getLocation() {
        return this.location;
    }

    public final SetField getRecurrenceSchedule() {
        return this.recurrenceSchedule;
    }

    public final SetField getStartDate() {
        return this.startDate;
    }

    public final SetField getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Objects.hash(this.eventId, this.title, this.description, this.startDate, this.endDate, this.attendeeIds, this.allDay, this.location, this.recurrenceSchedule, this.eventStatus);
    }
}
