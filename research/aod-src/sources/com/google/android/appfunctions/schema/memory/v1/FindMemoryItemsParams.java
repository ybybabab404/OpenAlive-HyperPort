package com.google.android.appfunctions.schema.memory.v1;

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
public final class FindMemoryItemsParams {
    public final Integer maxCount;
    public final String query;
    public final String queryLocale;
    public final DateTime updatedAfter;
    public final DateTime updatedBefore;

    public FindMemoryItemsParams(String query, DateTime dateTime, DateTime dateTime2, Integer num, String str) {
        Intrinsics.checkNotNullParameter(query, "query");
        this.query = query;
        this.updatedAfter = dateTime;
        this.updatedBefore = dateTime2;
        this.maxCount = num;
        this.queryLocale = str;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof FindMemoryItemsParams)) {
            return false;
        }
        FindMemoryItemsParams findMemoryItemsParams = (FindMemoryItemsParams) obj;
        return Intrinsics.areEqual(this.query, findMemoryItemsParams.query) && Intrinsics.areEqual(this.updatedAfter, findMemoryItemsParams.updatedAfter) && Intrinsics.areEqual(this.updatedBefore, findMemoryItemsParams.updatedBefore) && Intrinsics.areEqual(this.maxCount, findMemoryItemsParams.maxCount) && Intrinsics.areEqual(this.queryLocale, findMemoryItemsParams.queryLocale);
    }

    public final Integer getMaxCount() {
        return this.maxCount;
    }

    public final String getQuery() {
        return this.query;
    }

    public final String getQueryLocale() {
        return this.queryLocale;
    }

    public final DateTime getUpdatedAfter() {
        return this.updatedAfter;
    }

    public final DateTime getUpdatedBefore() {
        return this.updatedBefore;
    }

    public int hashCode() {
        return Objects.hash(this.query, this.updatedAfter, this.updatedBefore, this.maxCount, this.queryLocale);
    }
}
