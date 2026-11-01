package com.miui.keyguard.editor.data.preset;

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
public final class FilterColor {
    public int color;
    public int colorDrawable;
    public final int colorId;
    public int colorType;
    public boolean mutable;
    public int progressColor;
    public boolean supportSelect;

    public FilterColor(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2) {
        this.colorId = i;
        this.color = i2;
        this.progressColor = i3;
        this.colorDrawable = i4;
        this.colorType = i5;
        this.mutable = z;
        this.supportSelect = z2;
    }

    public /* synthetic */ FilterColor(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2, int i6, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i6 & 2) != 0 ? 0 : i2, (i6 & 4) != 0 ? 0 : i3, (i6 & 8) != 0 ? 0 : i4, (i6 & 16) != 0 ? 1 : i5, (i6 & 32) != 0 ? true : z, (i6 & 64) != 0 ? true : z2);
    }

    public static /* synthetic */ FilterColor copy$default(FilterColor filterColor, int i, int i2, int i3, int i4, int i5, boolean z, boolean z2, int i6, Object obj) {
        if ((i6 & 1) != 0) {
            i = filterColor.colorId;
        }
        if ((i6 & 2) != 0) {
            i2 = filterColor.color;
        }
        if ((i6 & 4) != 0) {
            i3 = filterColor.progressColor;
        }
        if ((i6 & 8) != 0) {
            i4 = filterColor.colorDrawable;
        }
        if ((i6 & 16) != 0) {
            i5 = filterColor.colorType;
        }
        if ((i6 & 32) != 0) {
            z = filterColor.mutable;
        }
        if ((i6 & 64) != 0) {
            z2 = filterColor.supportSelect;
        }
        boolean z3 = z;
        boolean z4 = z2;
        int i7 = i5;
        int i8 = i3;
        return filterColor.copy(i, i2, i8, i4, i7, z3, z4);
    }

    public final FilterColor copy(int i, int i2, int i3, int i4, int i5, boolean z, boolean z2) {
        return new FilterColor(i, i2, i3, i4, i5, z, z2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Intrinsics.areEqual(FilterColor.class, obj != null ? obj.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type com.miui.keyguard.editor.data.preset.FilterColor");
        FilterColor filterColor = (FilterColor) obj;
        return this.colorId == filterColor.colorId && this.color == filterColor.color && this.progressColor == filterColor.progressColor && this.colorDrawable == filterColor.colorDrawable && this.colorType == filterColor.colorType && this.mutable == filterColor.mutable && this.supportSelect == filterColor.supportSelect;
    }

    public final int getColor() {
        return this.color;
    }

    public final int getColorDrawable() {
        return this.colorDrawable;
    }

    public final int getColorId() {
        return this.colorId;
    }

    public final int getColorType() {
        return this.colorType;
    }

    public final boolean getMutable() {
        return this.mutable;
    }

    public final int getProgressColor() {
        return this.progressColor;
    }

    public final boolean getSupportSelect() {
        return this.supportSelect;
    }

    public int hashCode() {
        return (((((((((this.colorId * 31) + this.progressColor) * 31) + this.colorDrawable) * 31) + this.colorType) * 31) + Boolean.hashCode(this.mutable)) * 31) + Boolean.hashCode(this.supportSelect);
    }

    public final void reset() {
        this.color = FontFilterKt.getDEFAULT_FILTER_COLORS()[this.colorId].color;
        this.progressColor = FontFilterKt.getDEFAULT_FILTER_COLORS()[this.colorId].progressColor;
        this.colorDrawable = FontFilterKt.getDEFAULT_FILTER_COLORS()[this.colorId].colorDrawable;
        this.colorType = FontFilterKt.getDEFAULT_FILTER_COLORS()[this.colorId].colorType;
    }

    public final void setColor(int i) {
        this.color = i;
    }

    public final void setMutable(boolean z) {
        this.mutable = z;
    }

    public final void setSupportSelect(boolean z) {
        this.supportSelect = z;
    }

    public String toString() {
        return "FilterColor(colorId=" + this.colorId + ", color=" + this.color + ", progressColor=" + this.progressColor + ", colorDrawable=" + this.colorDrawable + ", colorType=" + this.colorType + ", mutable=" + this.mutable + ", supportSelect=" + this.supportSelect + ")";
    }
}
