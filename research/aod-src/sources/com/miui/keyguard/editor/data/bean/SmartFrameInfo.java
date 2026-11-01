package com.miui.keyguard.editor.data.bean;

import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: TemplateConfig.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class SmartFrameInfo {
    private boolean isAutoSolidColor;
    private int shape;
    private int solidColor;

    public SmartFrameInfo(int i, boolean z, int i2) {
        this.solidColor = i;
        this.isAutoSolidColor = z;
        this.shape = i2;
    }

    public static /* synthetic */ SmartFrameInfo copy$default(SmartFrameInfo smartFrameInfo, int i, boolean z, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = smartFrameInfo.solidColor;
        }
        if ((i3 & 2) != 0) {
            z = smartFrameInfo.isAutoSolidColor;
        }
        if ((i3 & 4) != 0) {
            i2 = smartFrameInfo.shape;
        }
        return smartFrameInfo.copy(i, z, i2);
    }

    public final int component1() {
        return this.solidColor;
    }

    public final boolean component2() {
        return this.isAutoSolidColor;
    }

    public final int component3() {
        return this.shape;
    }

    @NotNull
    public final SmartFrameInfo copy(int i, boolean z, int i2) {
        return new SmartFrameInfo(i, z, i2);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SmartFrameInfo)) {
            return false;
        }
        SmartFrameInfo smartFrameInfo = (SmartFrameInfo) obj;
        return this.solidColor == smartFrameInfo.solidColor && this.isAutoSolidColor == smartFrameInfo.isAutoSolidColor && this.shape == smartFrameInfo.shape;
    }

    public final int getShape() {
        return this.shape;
    }

    public final int getSolidColor() {
        return this.solidColor;
    }

    public int hashCode() {
        return (((Integer.hashCode(this.solidColor) * 31) + Boolean.hashCode(this.isAutoSolidColor)) * 31) + Integer.hashCode(this.shape);
    }

    public final boolean isAutoSolidColor() {
        return this.isAutoSolidColor;
    }

    public final void setAutoSolidColor(boolean z) {
        this.isAutoSolidColor = z;
    }

    public final void setShape(int i) {
        this.shape = i;
    }

    public final void setSolidColor(int i) {
        this.solidColor = i;
    }

    @NotNull
    public String toString() {
        return "SmartFrameInfo(solidColor=" + this.solidColor + ", isAutoSolidColor=" + this.isAutoSolidColor + ", shape=" + this.shape + ")";
    }
}
