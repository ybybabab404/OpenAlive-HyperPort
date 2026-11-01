package com.miui.aod.flip;

import kotlin.Metadata;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ConstraintSetExt.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class Ids {
    public final int clockContainerId;
    public final int clockInfoContainerId;
    public final int gideLineHorizonId;
    public final int gideLineVerticalId;
    public final int parent;

    public Ids(int i, int i2, int i3, int i4, int i5) {
        this.parent = i;
        this.clockContainerId = i2;
        this.clockInfoContainerId = i3;
        this.gideLineHorizonId = i4;
        this.gideLineVerticalId = i5;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Ids)) {
            return false;
        }
        Ids ids = (Ids) obj;
        return this.parent == ids.parent && this.clockContainerId == ids.clockContainerId && this.clockInfoContainerId == ids.clockInfoContainerId && this.gideLineHorizonId == ids.gideLineHorizonId && this.gideLineVerticalId == ids.gideLineVerticalId;
    }

    public final int getClockContainerId() {
        return this.clockContainerId;
    }

    public final int getClockInfoContainerId() {
        return this.clockInfoContainerId;
    }

    public final int getGideLineVerticalId() {
        return this.gideLineVerticalId;
    }

    public final int getParent() {
        return this.parent;
    }

    public int hashCode() {
        return (((((((Integer.hashCode(this.parent) * 31) + Integer.hashCode(this.clockContainerId)) * 31) + Integer.hashCode(this.clockInfoContainerId)) * 31) + Integer.hashCode(this.gideLineHorizonId)) * 31) + Integer.hashCode(this.gideLineVerticalId);
    }

    public String toString() {
        return "Ids(parent=" + this.parent + ", clockContainerId=" + this.clockContainerId + ", clockInfoContainerId=" + this.clockInfoContainerId + ", gideLineHorizonId=" + this.gideLineHorizonId + ", gideLineVerticalId=" + this.gideLineVerticalId + ")";
    }
}
