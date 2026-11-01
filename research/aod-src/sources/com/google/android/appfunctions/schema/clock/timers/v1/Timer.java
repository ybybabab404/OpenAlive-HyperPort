package com.google.android.appfunctions.schema.clock.timers.v1;

import androidx.appfunctions.AppFunctionSerializable;
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
public final class Timer {
    public static final Companion Companion = new Companion(null);
    public final long durationMillis;
    public final Long expireTimeEpochMillis;
    public final String id;
    public final Boolean isFiring;
    public final String label;
    public final long originalDurationMillis;
    public final long remainingDurationMillis;
    public final String timerState;

    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    @Metadata
    public static final class Companion {

        /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
        @Metadata
        @Retention(RetentionPolicy.SOURCE)
        @kotlin.annotation.Retention
        public @interface TimerState {
        }

        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public Timer(String id, String str, long j, long j2, String timerState, long j3, Long l, Boolean bool) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(timerState, "timerState");
        this.id = id;
        this.label = str;
        this.originalDurationMillis = j;
        this.durationMillis = j2;
        this.timerState = timerState;
        this.remainingDurationMillis = j3;
        this.expireTimeEpochMillis = l;
        this.isFiring = bool;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Timer)) {
            return false;
        }
        Timer timer = (Timer) obj;
        return Intrinsics.areEqual(this.id, timer.id) && Intrinsics.areEqual(this.label, timer.label) && this.originalDurationMillis == timer.originalDurationMillis && this.durationMillis == timer.durationMillis && Intrinsics.areEqual(this.timerState, timer.timerState) && this.remainingDurationMillis == timer.remainingDurationMillis && Intrinsics.areEqual(this.expireTimeEpochMillis, timer.expireTimeEpochMillis) && Intrinsics.areEqual(this.isFiring, timer.isFiring);
    }

    public final long getDurationMillis() {
        return this.durationMillis;
    }

    public final Long getExpireTimeEpochMillis() {
        return this.expireTimeEpochMillis;
    }

    public final String getId() {
        return this.id;
    }

    public final String getLabel() {
        return this.label;
    }

    public final long getOriginalDurationMillis() {
        return this.originalDurationMillis;
    }

    public final long getRemainingDurationMillis() {
        return this.remainingDurationMillis;
    }

    public final String getTimerState() {
        return this.timerState;
    }

    public int hashCode() {
        return Objects.hash(this.id, this.label, Long.valueOf(this.originalDurationMillis), Long.valueOf(this.durationMillis), this.timerState, Long.valueOf(this.remainingDurationMillis), this.expireTimeEpochMillis, this.isFiring);
    }

    public final Boolean isFiring() {
        return this.isFiring;
    }
}
