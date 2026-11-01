package com.miui.keyguard.editor.data.preset;

import java.util.Arrays;
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
    */
/* JADX INFO: compiled from: FontFilter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class FontColorInfo {
    public int colorId;
    public FilterColor currentFilterColor;
    public float[] currentHslColor;
    public int targetIndex;

    public FontColorInfo(int i, int i2, FilterColor filterColor, float[] currentHslColor) {
        Intrinsics.checkNotNullParameter(currentHslColor, "currentHslColor");
        this.targetIndex = i;
        this.colorId = i2;
        this.currentFilterColor = filterColor;
        this.currentHslColor = currentHslColor;
    }

    public /* synthetic */ FontColorInfo(int i, int i2, FilterColor filterColor, float[] fArr, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, i2, (i3 & 4) != 0 ? null : filterColor, (i3 & 8) != 0 ? new float[3] : fArr);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Intrinsics.areEqual(FontColorInfo.class, obj != null ? obj.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type com.miui.keyguard.editor.data.preset.FontColorInfo");
        FontColorInfo fontColorInfo = (FontColorInfo) obj;
        return this.targetIndex == fontColorInfo.targetIndex && this.colorId == fontColorInfo.colorId && Intrinsics.areEqual(this.currentFilterColor, fontColorInfo.currentFilterColor) && Arrays.equals(this.currentHslColor, fontColorInfo.currentHslColor);
    }

    public final FilterColor getCurrentFilterColor() {
        return this.currentFilterColor;
    }

    public final float[] getCurrentHslColor() {
        return this.currentHslColor;
    }

    public int hashCode() {
        int i = ((this.targetIndex * 31) + this.colorId) * 31;
        FilterColor filterColor = this.currentFilterColor;
        return ((i + (filterColor != null ? filterColor.hashCode() : 0)) * 31) + Arrays.hashCode(this.currentHslColor);
    }

    public final void setCurrentFilterColor(FilterColor filterColor) {
        this.currentFilterColor = filterColor;
    }

    public String toString() {
        return "FontColorInfo(targetIndex=" + this.targetIndex + ", colorId=" + this.colorId + ", currentFilterColor=" + this.currentFilterColor + ", currentHslColor=" + Arrays.toString(this.currentHslColor) + ")";
    }
}
