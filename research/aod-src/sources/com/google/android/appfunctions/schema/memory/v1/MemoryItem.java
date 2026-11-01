package com.google.android.appfunctions.schema.memory.v1;

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
public final class MemoryItem {
    public final String additionalMetadataJson;
    public final DateTime createdAt;
    public final String id;
    public final List keywords;
    public final DateTime lastUpdatedAt;
    public final String summary;
    public final String textContent;
    public final String title;

    public MemoryItem(String id, String str, String str2, String textContent, List keywords, DateTime createdAt, DateTime lastUpdatedAt, String str3) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(textContent, "textContent");
        Intrinsics.checkNotNullParameter(keywords, "keywords");
        Intrinsics.checkNotNullParameter(createdAt, "createdAt");
        Intrinsics.checkNotNullParameter(lastUpdatedAt, "lastUpdatedAt");
        this.id = id;
        this.title = str;
        this.summary = str2;
        this.textContent = textContent;
        this.keywords = keywords;
        this.createdAt = createdAt;
        this.lastUpdatedAt = lastUpdatedAt;
        this.additionalMetadataJson = str3;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof MemoryItem)) {
            return false;
        }
        MemoryItem memoryItem = (MemoryItem) obj;
        return Intrinsics.areEqual(this.id, memoryItem.id) && Intrinsics.areEqual(this.title, memoryItem.title) && Intrinsics.areEqual(this.summary, memoryItem.summary) && Intrinsics.areEqual(this.textContent, memoryItem.textContent) && Intrinsics.areEqual(this.keywords, memoryItem.keywords) && Intrinsics.areEqual(this.createdAt, memoryItem.createdAt) && Intrinsics.areEqual(this.lastUpdatedAt, memoryItem.lastUpdatedAt) && Intrinsics.areEqual(this.additionalMetadataJson, memoryItem.additionalMetadataJson);
    }

    public final String getAdditionalMetadataJson() {
        return this.additionalMetadataJson;
    }

    public final DateTime getCreatedAt() {
        return this.createdAt;
    }

    public final String getId() {
        return this.id;
    }

    public final List getKeywords() {
        return this.keywords;
    }

    public final DateTime getLastUpdatedAt() {
        return this.lastUpdatedAt;
    }

    public final String getSummary() {
        return this.summary;
    }

    public final String getTextContent() {
        return this.textContent;
    }

    public final String getTitle() {
        return this.title;
    }

    public int hashCode() {
        return Objects.hash(this.id, this.title, this.summary, this.textContent, this.keywords, this.createdAt, this.lastUpdatedAt, this.additionalMetadataJson);
    }
}
