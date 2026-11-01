package com.miui.keyguard.editor.data.bean;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
public final class ColorValueInfo {
    private int hueValue;
    private int lightnessValue;
    private int saturationValue;

    public ColorValueInfo() {
        this(0, 0, 0, 7, null);
    }

    public ColorValueInfo(int i, int i2, int i3) {
        this.hueValue = i;
        this.saturationValue = i2;
        this.lightnessValue = i3;
    }

    public /* synthetic */ ColorValueInfo(int i, int i2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? 0 : i, (i4 & 2) != 0 ? 0 : i2, (i4 & 4) != 0 ? 0 : i3);
    }

    public static /* synthetic */ ColorValueInfo copy$default(ColorValueInfo colorValueInfo, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i = colorValueInfo.hueValue;
        }
        if ((i4 & 2) != 0) {
            i2 = colorValueInfo.saturationValue;
        }
        if ((i4 & 4) != 0) {
            i3 = colorValueInfo.lightnessValue;
        }
        return colorValueInfo.copy(i, i2, i3);
    }

    public final int component1() {
        return this.hueValue;
    }

    public final int component2() {
        return this.saturationValue;
    }

    public final int component3() {
        return this.lightnessValue;
    }

    @NotNull
    public final ColorValueInfo copy(int i, int i2, int i3) {
        return new ColorValueInfo(i, i2, i3);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ColorValueInfo)) {
            return false;
        }
        ColorValueInfo colorValueInfo = (ColorValueInfo) obj;
        return this.hueValue == colorValueInfo.hueValue && this.saturationValue == colorValueInfo.saturationValue && this.lightnessValue == colorValueInfo.lightnessValue;
    }

    public final int getHueValue() {
        return this.hueValue;
    }

    public final int getLightnessValue() {
        return this.lightnessValue;
    }

    public final int getSaturationValue() {
        return this.saturationValue;
    }

    public int hashCode() {
        return (((Integer.hashCode(this.hueValue) * 31) + Integer.hashCode(this.saturationValue)) * 31) + Integer.hashCode(this.lightnessValue);
    }

    public final void setHueValue(int i) {
        this.hueValue = i;
    }

    public final void setLightnessValue(int i) {
        this.lightnessValue = i;
    }

    public final void setSaturationValue(int i) {
        this.saturationValue = i;
    }

    @NotNull
    public String toString() {
        return "ColorValueInfo(hueValue=" + this.hueValue + ", saturationValue=" + this.saturationValue + ", lightnessValue=" + this.lightnessValue + ")";
    }
}
