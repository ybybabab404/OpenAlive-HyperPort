package com.miui.keyguard.editor.view;

import java.util.List;
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
/* JADX INFO: compiled from: ClockTemplatePagerAdapter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ClockTemplateRow {
    public int hideFlag;
    public final List templateList;
    public final int titleId;

    public ClockTemplateRow(int i, List templateList, int i2) {
        Intrinsics.checkNotNullParameter(templateList, "templateList");
        this.titleId = i;
        this.templateList = templateList;
        this.hideFlag = i2;
    }

    public /* synthetic */ ClockTemplateRow(int i, List list, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, list, (i3 & 4) != 0 ? 0 : i2);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ClockTemplateRow)) {
            return false;
        }
        ClockTemplateRow clockTemplateRow = (ClockTemplateRow) obj;
        return this.titleId == clockTemplateRow.titleId && Intrinsics.areEqual(this.templateList, clockTemplateRow.templateList) && this.hideFlag == clockTemplateRow.hideFlag;
    }

    public final List getTemplateList() {
        return this.templateList;
    }

    public final int getTitleId() {
        return this.titleId;
    }

    public int hashCode() {
        return (((Integer.hashCode(this.titleId) * 31) + this.templateList.hashCode()) * 31) + Integer.hashCode(this.hideFlag);
    }

    public String toString() {
        return "ClockTemplateRow(titleId=" + this.titleId + ", templateList=" + this.templateList + ", hideFlag=" + this.hideFlag + ")";
    }
}
