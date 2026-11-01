package com.miui.clock.palette;

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
/* JADX INFO: compiled from: ClockPaletteStrategy.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class SystemState {
    public final boolean advancedVisualEnabled;
    public final boolean advancedVisualEnabled3;
    public final boolean highContrastEnabled;
    public final boolean powerSavingEnabled;

    public SystemState(boolean z, boolean z2, boolean z3, boolean z4) {
        this.advancedVisualEnabled = z;
        this.highContrastEnabled = z2;
        this.powerSavingEnabled = z3;
        this.advancedVisualEnabled3 = z4;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SystemState)) {
            return false;
        }
        SystemState systemState = (SystemState) obj;
        return this.advancedVisualEnabled == systemState.advancedVisualEnabled && this.highContrastEnabled == systemState.highContrastEnabled && this.powerSavingEnabled == systemState.powerSavingEnabled && this.advancedVisualEnabled3 == systemState.advancedVisualEnabled3;
    }

    public final boolean getAdvancedVisualEnabled() {
        return this.advancedVisualEnabled;
    }

    public int hashCode() {
        return (((((Boolean.hashCode(this.advancedVisualEnabled) * 31) + Boolean.hashCode(this.highContrastEnabled)) * 31) + Boolean.hashCode(this.powerSavingEnabled)) * 31) + Boolean.hashCode(this.advancedVisualEnabled3);
    }

    public String toString() {
        return "SystemState(advancedVisualEnabled=" + this.advancedVisualEnabled + ", highContrastEnabled=" + this.highContrastEnabled + ", powerSavingEnabled=" + this.powerSavingEnabled + ", advancedVisualEnabled3=" + this.advancedVisualEnabled3 + ')';
    }
}
