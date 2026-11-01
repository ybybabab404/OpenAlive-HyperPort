package com.miui.aod.monitor.policy;

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
/* JADX INFO: compiled from: VisibilityPolicy.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class ViewTarget {
    public final String label;
    public final ViewSelector selector;

    public ViewTarget(String label, ViewSelector selector) {
        Intrinsics.checkNotNullParameter(label, "label");
        Intrinsics.checkNotNullParameter(selector, "selector");
        this.label = label;
        this.selector = selector;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ViewTarget)) {
            return false;
        }
        ViewTarget viewTarget = (ViewTarget) obj;
        return Intrinsics.areEqual(this.label, viewTarget.label) && Intrinsics.areEqual(this.selector, viewTarget.selector);
    }

    public final String getLabel() {
        return this.label;
    }

    public final ViewSelector getSelector() {
        return this.selector;
    }

    public int hashCode() {
        return (this.label.hashCode() * 31) + this.selector.hashCode();
    }

    public String toString() {
        return "ViewTarget(label=" + this.label + ", selector=" + this.selector + ")";
    }
}
