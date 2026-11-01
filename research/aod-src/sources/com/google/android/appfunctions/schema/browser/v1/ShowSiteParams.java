package com.google.android.appfunctions.schema.browser.v1;

import android.net.Uri;
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
public final class ShowSiteParams {
    public final boolean shouldShowInNewTab;
    public final Uri url;

    public ShowSiteParams(Uri url, boolean z) {
        Intrinsics.checkNotNullParameter(url, "url");
        this.url = url;
        this.shouldShowInNewTab = z;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ShowSiteParams)) {
            return false;
        }
        ShowSiteParams showSiteParams = (ShowSiteParams) obj;
        return Intrinsics.areEqual(this.url, showSiteParams.url) && this.shouldShowInNewTab == showSiteParams.shouldShowInNewTab;
    }

    public final boolean getShouldShowInNewTab() {
        return this.shouldShowInNewTab;
    }

    public final Uri getUrl() {
        return this.url;
    }

    public int hashCode() {
        return Objects.hash(this.url, Boolean.valueOf(this.shouldShowInNewTab));
    }
}
