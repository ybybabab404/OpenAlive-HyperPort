package com.miui.keyguard.editor.data.preset;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
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
public final class FilterTypeSelectInfo {
    public int currentColorIndex;
    public final Lazy modifyColorSet$delegate;
    public int selectColorId;

    public FilterTypeSelectInfo(int i, int i2) {
        this.selectColorId = i;
        this.currentColorIndex = i2;
        this.modifyColorSet$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.FilterTypeSelectInfo$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FilterTypeSelectInfo.modifyColorSet_delegate$lambda$0();
            }
        });
    }

    public /* synthetic */ FilterTypeSelectInfo(int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i3 & 2) != 0 ? 0 : i2);
    }

    public static final LockedHashSet modifyColorSet_delegate$lambda$0() {
        return new LockedHashSet();
    }

    public final void addModifyColor(FilterColor filterColor) {
        Intrinsics.checkNotNullParameter(filterColor, "filterColor");
        getModifyColor().add(filterColor);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FilterTypeSelectInfo)) {
            return false;
        }
        FilterTypeSelectInfo filterTypeSelectInfo = (FilterTypeSelectInfo) obj;
        return this.selectColorId == filterTypeSelectInfo.selectColorId && this.currentColorIndex == filterTypeSelectInfo.currentColorIndex;
    }

    public final int getCurrentColorIndex() {
        return this.currentColorIndex;
    }

    public final LockedHashSet getModifyColor() {
        return getModifyColorSet();
    }

    public final LockedHashSet getModifyColorSet() {
        return (LockedHashSet) this.modifyColorSet$delegate.getValue();
    }

    public final int getSelectColorId() {
        return this.selectColorId;
    }

    public int hashCode() {
        return (Integer.hashCode(this.selectColorId) * 31) + Integer.hashCode(this.currentColorIndex);
    }

    public final void setCurrentColorIndex(int i) {
        this.currentColorIndex = i;
    }

    public final void setSelectColorId(int i) {
        this.selectColorId = i;
    }

    public String toString() {
        return "FilterTypeSelectInfo(selectColorId=" + this.selectColorId + ", currentColorIndex=" + this.currentColorIndex + ")";
    }
}
