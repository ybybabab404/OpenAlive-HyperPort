package com.google.android.appfunctions.schema.browser.v1;

import androidx.appfunctions.AppFunctionSerializable;
import com.google.android.appfunctions.schema.types.v1.DateTime;
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
public final class GetHistoriesParams {
    public final DateTime endTime;
    public final int maxCount;
    public final String query;
    public final DateTime startTime;

    public GetHistoriesParams(DateTime dateTime, DateTime dateTime2, String str, int i) {
        this.startTime = dateTime;
        this.endTime = dateTime2;
        this.query = str;
        this.maxCount = i;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GetHistoriesParams)) {
            return false;
        }
        GetHistoriesParams getHistoriesParams = (GetHistoriesParams) obj;
        return Intrinsics.areEqual(this.startTime, getHistoriesParams.startTime) && Intrinsics.areEqual(this.endTime, getHistoriesParams.endTime) && Intrinsics.areEqual(this.query, getHistoriesParams.query) && this.maxCount == getHistoriesParams.maxCount;
    }

    public final DateTime getEndTime() {
        return this.endTime;
    }

    public final int getMaxCount() {
        return this.maxCount;
    }

    public final String getQuery() {
        return this.query;
    }

    public final DateTime getStartTime() {
        return this.startTime;
    }

    public int hashCode() {
        return Objects.hash(this.startTime, this.endTime, this.query, Integer.valueOf(this.maxCount));
    }
}
