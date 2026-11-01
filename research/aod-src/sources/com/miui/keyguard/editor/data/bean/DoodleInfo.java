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
public final class DoodleInfo {
    private boolean isAutoSolidColor;
    private int primaryFontColorPanelId;
    private int ribbonColor1;
    private int ribbonColor2;
    private int solidColor;

    public DoodleInfo(int i, boolean z, int i2, int i3, int i4) {
        this.solidColor = i;
        this.isAutoSolidColor = z;
        this.primaryFontColorPanelId = i2;
        this.ribbonColor1 = i3;
        this.ribbonColor2 = i4;
    }

    public static /* synthetic */ DoodleInfo copy$default(DoodleInfo doodleInfo, int i, boolean z, int i2, int i3, int i4, int i5, Object obj) {
        if ((i5 & 1) != 0) {
            i = doodleInfo.solidColor;
        }
        if ((i5 & 2) != 0) {
            z = doodleInfo.isAutoSolidColor;
        }
        if ((i5 & 4) != 0) {
            i2 = doodleInfo.primaryFontColorPanelId;
        }
        if ((i5 & 8) != 0) {
            i3 = doodleInfo.ribbonColor1;
        }
        if ((i5 & 16) != 0) {
            i4 = doodleInfo.ribbonColor2;
        }
        int i6 = i4;
        int i7 = i2;
        return doodleInfo.copy(i, z, i7, i3, i6);
    }

    public final int component1() {
        return this.solidColor;
    }

    public final boolean component2() {
        return this.isAutoSolidColor;
    }

    public final int component3() {
        return this.primaryFontColorPanelId;
    }

    public final int component4() {
        return this.ribbonColor1;
    }

    public final int component5() {
        return this.ribbonColor2;
    }

    @NotNull
    public final DoodleInfo copy(int i, boolean z, int i2, int i3, int i4) {
        return new DoodleInfo(i, z, i2, i3, i4);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DoodleInfo)) {
            return false;
        }
        DoodleInfo doodleInfo = (DoodleInfo) obj;
        return this.solidColor == doodleInfo.solidColor && this.isAutoSolidColor == doodleInfo.isAutoSolidColor && this.primaryFontColorPanelId == doodleInfo.primaryFontColorPanelId && this.ribbonColor1 == doodleInfo.ribbonColor1 && this.ribbonColor2 == doodleInfo.ribbonColor2;
    }

    public final int getPrimaryFontColorPanelId() {
        return this.primaryFontColorPanelId;
    }

    public final int getRibbonColor1() {
        return this.ribbonColor1;
    }

    public final int getRibbonColor2() {
        return this.ribbonColor2;
    }

    public final int getSolidColor() {
        return this.solidColor;
    }

    public int hashCode() {
        return (((((((Integer.hashCode(this.solidColor) * 31) + Boolean.hashCode(this.isAutoSolidColor)) * 31) + Integer.hashCode(this.primaryFontColorPanelId)) * 31) + Integer.hashCode(this.ribbonColor1)) * 31) + Integer.hashCode(this.ribbonColor2);
    }

    public final boolean isAutoSolidColor() {
        return this.isAutoSolidColor;
    }

    public final void setAutoSolidColor(boolean z) {
        this.isAutoSolidColor = z;
    }

    public final void setPrimaryFontColorPanelId(int i) {
        this.primaryFontColorPanelId = i;
    }

    public final void setRibbonColor1(int i) {
        this.ribbonColor1 = i;
    }

    public final void setRibbonColor2(int i) {
        this.ribbonColor2 = i;
    }

    public final void setSolidColor(int i) {
        this.solidColor = i;
    }

    @NotNull
    public String toString() {
        return "DoodleInfo(solidColor=" + this.solidColor + ", isAutoSolidColor=" + this.isAutoSolidColor + ", primaryFontColorPanelId=" + this.primaryFontColorPanelId + ", ribbonColor1=" + this.ribbonColor1 + ", ribbonColor2=" + this.ribbonColor2 + ")";
    }
}
