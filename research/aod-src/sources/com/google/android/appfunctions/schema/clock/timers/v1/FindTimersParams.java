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
public final class FindTimersParams {
    public static final Companion Companion = new Companion(null);
    public final String label;
    public final int maxCount;
    public final String sortingOrder;
    public final String timerState;

    /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
    @Metadata
    public static final class Companion {

        /* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
        @Metadata
        @Retention(RetentionPolicy.SOURCE)
        @kotlin.annotation.Retention
        public @interface TimerSortingOrder {
        }

        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    public FindTimersParams(String str, String str2, int i, String str3) {
        this.label = str;
        this.timerState = str2;
        this.maxCount = i;
        this.sortingOrder = str3;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FindTimersParams)) {
            return false;
        }
        FindTimersParams findTimersParams = (FindTimersParams) obj;
        return Intrinsics.areEqual(this.label, findTimersParams.label) && Intrinsics.areEqual(this.timerState, findTimersParams.timerState) && this.maxCount == findTimersParams.maxCount && Intrinsics.areEqual(this.sortingOrder, findTimersParams.sortingOrder);
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

    public final String getTimerState() {
        return this.timerState;
    }

    public int hashCode() {
        return Objects.hash(this.label, this.timerState, Integer.valueOf(this.maxCount), this.sortingOrder);
    }
}
