package com.miui.keyguard.editor.history.bean;

import androidx.annotation.Keep;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.random.Random;
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
/* JADX INFO: compiled from: HistoryItemBean.kt */
/* JADX INFO: loaded from: classes2.dex */
@Keep
@Metadata
public final class HistoryItemBean {
    private long id;
    private boolean selected;
    private int status;

    @Nullable
    private TemplateItemBean templateItemBean;

    public HistoryItemBean(int i, @Nullable TemplateItemBean templateItemBean, boolean z) {
        this.status = i;
        this.templateItemBean = templateItemBean;
        this.selected = z;
    }

    public /* synthetic */ HistoryItemBean(int i, TemplateItemBean templateItemBean, boolean z, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? null : templateItemBean, z);
    }

    public static /* synthetic */ HistoryItemBean copy$default(HistoryItemBean historyItemBean, int i, TemplateItemBean templateItemBean, boolean z, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = historyItemBean.status;
        }
        if ((i2 & 2) != 0) {
            templateItemBean = historyItemBean.templateItemBean;
        }
        if ((i2 & 4) != 0) {
            z = historyItemBean.selected;
        }
        return historyItemBean.copy(i, templateItemBean, z);
    }

    public final int component1() {
        return this.status;
    }

    @Nullable
    public final TemplateItemBean component2() {
        return this.templateItemBean;
    }

    public final boolean component3() {
        return this.selected;
    }

    @NotNull
    public final HistoryItemBean copy(int i, @Nullable TemplateItemBean templateItemBean, boolean z) {
        return new HistoryItemBean(i, templateItemBean, z);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HistoryItemBean)) {
            return false;
        }
        HistoryItemBean historyItemBean = (HistoryItemBean) obj;
        return this.status == historyItemBean.status && Intrinsics.areEqual(this.templateItemBean, historyItemBean.templateItemBean) && this.selected == historyItemBean.selected;
    }

    public final long getId() {
        if (this.id == 0) {
            this.id = Math.abs(Random.Default.nextLong(2L, Long.MAX_VALUE));
        }
        return this.id;
    }

    public final boolean getSelected() {
        return this.selected;
    }

    public final int getStatus() {
        return this.status;
    }

    @Nullable
    public final TemplateItemBean getTemplateItemBean() {
        return this.templateItemBean;
    }

    public int hashCode() {
        int iHashCode = Integer.hashCode(this.status) * 31;
        TemplateItemBean templateItemBean = this.templateItemBean;
        return ((iHashCode + (templateItemBean == null ? 0 : templateItemBean.hashCode())) * 31) + Boolean.hashCode(this.selected);
    }

    public final void setId(long j) {
        this.id = j;
    }

    public final void setSelected(boolean z) {
        this.selected = z;
    }

    public final void setStatus(int i) {
        this.status = i;
    }

    public final void setTemplateItemBean(@Nullable TemplateItemBean templateItemBean) {
        this.templateItemBean = templateItemBean;
    }

    @NotNull
    public String toString() {
        return "HistoryItemBean(status=" + this.status + ", templateItemBean=" + this.templateItemBean + ", selected=" + this.selected + ")";
    }
}
