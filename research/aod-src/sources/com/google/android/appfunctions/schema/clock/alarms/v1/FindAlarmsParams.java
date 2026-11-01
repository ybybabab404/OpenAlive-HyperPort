package com.google.android.appfunctions.schema.clock.alarms.v1;

import androidx.appfunctions.AppFunctionSerializable;
import com.google.android.appfunctions.schema.types.v1.DateTime;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
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
/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
@AppFunctionSerializable
@Metadata
public final class FindAlarmsParams {
    public static final Companion Companion = new Companion(null);
    public final String alarmStatus;
    public final DayPattern dayPattern;
    public final DateTime endTime;
    public final String label;
    public final int maxCount;
    public final String sortingOrder;
    public final DateTime startTime;

    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    @Metadata
    public static final class Companion {

        /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
        @Metadata
        @Retention(RetentionPolicy.SOURCE)
        @kotlin.annotation.Retention
        public @interface AlarmSortingOrder {
        }

        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public FindAlarmsParams(DateTime dateTime, DateTime dateTime2, String str, String str2, DayPattern dayPattern, int i, String str3) {
        this.startTime = dateTime;
        this.endTime = dateTime2;
        this.label = str;
        this.alarmStatus = str2;
        this.dayPattern = dayPattern;
        this.maxCount = i;
        this.sortingOrder = str3;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FindAlarmsParams)) {
            return false;
        }
        FindAlarmsParams findAlarmsParams = (FindAlarmsParams) obj;
        return Intrinsics.areEqual(this.startTime, findAlarmsParams.startTime) && Intrinsics.areEqual(this.endTime, findAlarmsParams.endTime) && Intrinsics.areEqual(this.label, findAlarmsParams.label) && Intrinsics.areEqual(this.alarmStatus, findAlarmsParams.alarmStatus) && Intrinsics.areEqual(this.dayPattern, findAlarmsParams.dayPattern) && this.maxCount == findAlarmsParams.maxCount && Intrinsics.areEqual(this.sortingOrder, findAlarmsParams.sortingOrder);
    }

    public final String getAlarmStatus() {
        return this.alarmStatus;
    }

    public final DayPattern getDayPattern() {
        return this.dayPattern;
    }

    public final DateTime getEndTime() {
        return this.endTime;
    }

    public final String getLabel() {
        return this.label;
    }

    public final int getMaxCount() {
        return this.maxCount;
    }

    public final String getSortingOrder() {
        return this.sortingOrder;
    }

    public final DateTime getStartTime() {
        return this.startTime;
    }

    public int hashCode() {
        return Objects.hash(this.startTime, this.endTime, this.label, this.alarmStatus, this.dayPattern, Integer.valueOf(this.maxCount), this.sortingOrder);
    }
}
