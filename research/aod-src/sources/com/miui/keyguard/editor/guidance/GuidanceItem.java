package com.miui.keyguard.editor.guidance;

import android.net.Uri;
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
/* JADX INFO: compiled from: GuidanceAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class GuidanceItem {
    public final String text;
    public final Uri uri;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GuidanceItem)) {
            return false;
        }
        GuidanceItem guidanceItem = (GuidanceItem) obj;
        return Intrinsics.areEqual(this.uri, guidanceItem.uri) && Intrinsics.areEqual(this.text, guidanceItem.text);
    }

    public final String getText() {
        return this.text;
    }

    public final Uri getUri() {
        return this.uri;
    }

    public int hashCode() {
        return (this.uri.hashCode() * 31) + this.text.hashCode();
    }

    public String toString() {
        return "GuidanceItem(uri=" + this.uri + ", text=" + this.text + ")";
    }
}
