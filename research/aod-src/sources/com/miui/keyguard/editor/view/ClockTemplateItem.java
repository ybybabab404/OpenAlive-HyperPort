package com.miui.keyguard.editor.view;

import com.miui.keyguard.editor.data.bean.TemplateConfig;
import kotlin.Metadata;
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
public final class ClockTemplateItem {
    public int columnIndex;
    public String contentDescription;
    public int hideFlag;
    public boolean isSelected;
    public int rowIndex;
    public final TemplateConfig templateConfig;
    public final String templateRes;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!Intrinsics.areEqual(ClockTemplateItem.class, obj != null ? obj.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type com.miui.keyguard.editor.view.ClockTemplateItem");
        ClockTemplateItem clockTemplateItem = (ClockTemplateItem) obj;
        return Intrinsics.areEqual(this.templateRes, clockTemplateItem.templateRes) && Intrinsics.areEqual(this.templateConfig, clockTemplateItem.templateConfig) && this.isSelected == clockTemplateItem.isSelected && this.rowIndex == clockTemplateItem.rowIndex && this.columnIndex == clockTemplateItem.columnIndex && this.hideFlag == clockTemplateItem.hideFlag;
    }

    public final int getColumnIndex() {
        return this.columnIndex;
    }

    public final String getContentDescription() {
        return this.contentDescription;
    }

    public final int getHideFlag() {
        return this.hideFlag;
    }

    public final int getRowIndex() {
        return this.rowIndex;
    }

    public final TemplateConfig getTemplateConfig() {
        return this.templateConfig;
    }

    public final String getTemplateRes() {
        return this.templateRes;
    }

    public int hashCode() {
        return (((((((((Boolean.hashCode(this.isSelected) * 31) + this.rowIndex) * 31) + this.columnIndex) * 31) + this.hideFlag) * 31) + this.templateRes.hashCode()) * 31) + this.templateConfig.hashCode();
    }

    public final boolean isSelected() {
        return this.isSelected;
    }

    public final void setContentDescription(String str) {
        this.contentDescription = str;
    }

    public final void setIndex(int i, int i2) {
        this.rowIndex = i;
        this.columnIndex = i2;
    }

    public final void setSelected(boolean z) {
        this.isSelected = z;
    }

    public String toString() {
        return "ClockTemplateItem(templateRes=" + this.templateRes + ", templateConfig=" + this.templateConfig + ", isSelected=" + this.isSelected + ", rowIndex=" + this.rowIndex + ", columnIndex=" + this.columnIndex + ", hideFlag=" + this.hideFlag + ")";
    }
}
