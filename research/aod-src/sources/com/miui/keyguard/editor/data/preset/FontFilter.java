package com.miui.keyguard.editor.data.preset;

import java.util.Arrays;
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
public final class FontFilter {
    public final FilterGroup[][] filterGroups;
    public final boolean supportDifferentColor;
    public final Lazy targetMutable$delegate;
    public final Integer[] targetTitleResId;

    public FontFilter(Integer[] targetTitleResId, FilterGroup[][] filterGroups, boolean z) {
        Intrinsics.checkNotNullParameter(targetTitleResId, "targetTitleResId");
        Intrinsics.checkNotNullParameter(filterGroups, "filterGroups");
        this.targetTitleResId = targetTitleResId;
        this.filterGroups = filterGroups;
        this.supportDifferentColor = z;
        this.targetMutable$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.FontFilter$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FontFilter.targetMutable_delegate$lambda$0();
            }
        });
    }

    public /* synthetic */ FontFilter(Integer[] numArr, FilterGroup[][] filterGroupArr, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(numArr, filterGroupArr, (i & 4) != 0 ? false : z);
    }

    public static final Boolean[] targetMutable_delegate$lambda$0() {
        Boolean bool = Boolean.TRUE;
        return new Boolean[]{bool, bool};
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Intrinsics.areEqual(FontFilter.class, obj != null ? obj.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type com.miui.keyguard.editor.data.preset.FontFilter");
        FontFilter fontFilter = (FontFilter) obj;
        return Arrays.equals(this.targetTitleResId, fontFilter.targetTitleResId) && Arrays.equals(this.filterGroups, fontFilter.filterGroups) && this.supportDifferentColor == fontFilter.supportDifferentColor;
    }

    public final FilterGroup[] getCurrentFilterData(int i) {
        return this.filterGroups[i];
    }

    public final FilterGroup[][] getFilterGroups() {
        return this.filterGroups;
    }

    public final boolean getSupportDifferentColor() {
        return this.supportDifferentColor;
    }

    public final Boolean[] getTargetMutable() {
        return (Boolean[]) this.targetMutable$delegate.getValue();
    }

    public final Integer[] getTargetTitleResId() {
        return this.targetTitleResId;
    }

    public int hashCode() {
        return (((Arrays.hashCode(this.targetTitleResId) * 31) + Arrays.hashCode(this.filterGroups)) * 31) + Boolean.hashCode(this.supportDifferentColor);
    }

    public String toString() {
        return "FontFilter(targetTitleResId=" + Arrays.toString(this.targetTitleResId) + ", filterGroups=" + Arrays.toString(this.filterGroups) + ", supportDifferentColor=" + this.supportDifferentColor + ")";
    }
}
