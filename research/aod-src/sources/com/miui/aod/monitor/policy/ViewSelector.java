package com.miui.aod.monitor.policy;

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
/* JADX INFO: compiled from: VisibilityPolicy.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public abstract class ViewSelector {

    /* JADX INFO: compiled from: VisibilityPolicy.kt */
    @Metadata
    public static final class ByIdName extends ViewSelector {
        public final String idName;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ByIdName(String idName) {
            super(null);
            Intrinsics.checkNotNullParameter(idName, "idName");
            this.idName = idName;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ByIdName) && Intrinsics.areEqual(this.idName, ((ByIdName) obj).idName);
        }

        public final String getIdName() {
            return this.idName;
        }

        public int hashCode() {
            return this.idName.hashCode();
        }

        public String toString() {
            return "ByIdName(idName=" + this.idName + ")";
        }
    }

    public ViewSelector() {
    }

    public /* synthetic */ ViewSelector(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
