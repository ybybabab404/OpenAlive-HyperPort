package com.miui.keyguard.editor.data.bean;

import androidx.annotation.StringRes;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
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
/* JADX INFO: compiled from: TemplateGroupConfig.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class TemplateGroupConfig {
    private final int desc;

    @NotNull
    private final List<TemplateItemConfig> templates;
    private final int title;

    public TemplateGroupConfig(@StringRes int i, @StringRes int i2, @NotNull List<TemplateItemConfig> templates) {
        Intrinsics.checkNotNullParameter(templates, "templates");
        this.title = i;
        this.desc = i2;
        this.templates = templates;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ TemplateGroupConfig copy$default(TemplateGroupConfig templateGroupConfig, int i, int i2, List list, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = templateGroupConfig.title;
        }
        if ((i3 & 2) != 0) {
            i2 = templateGroupConfig.desc;
        }
        if ((i3 & 4) != 0) {
            list = templateGroupConfig.templates;
        }
        return templateGroupConfig.copy(i, i2, list);
    }

    public final int component1() {
        return this.title;
    }

    public final int component2() {
        return this.desc;
    }

    @NotNull
    public final List<TemplateItemConfig> component3() {
        return this.templates;
    }

    @NotNull
    public final TemplateGroupConfig copy(@StringRes int i, @StringRes int i2, @NotNull List<TemplateItemConfig> templates) {
        Intrinsics.checkNotNullParameter(templates, "templates");
        return new TemplateGroupConfig(i, i2, templates);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TemplateGroupConfig)) {
            return false;
        }
        TemplateGroupConfig templateGroupConfig = (TemplateGroupConfig) obj;
        return this.title == templateGroupConfig.title && this.desc == templateGroupConfig.desc && Intrinsics.areEqual(this.templates, templateGroupConfig.templates);
    }

    public final int getDesc() {
        return this.desc;
    }

    @NotNull
    public final List<TemplateItemConfig> getTemplates() {
        return this.templates;
    }

    public final int getTitle() {
        return this.title;
    }

    public int hashCode() {
        return (((Integer.hashCode(this.title) * 31) + Integer.hashCode(this.desc)) * 31) + this.templates.hashCode();
    }

    @NotNull
    public String toString() {
        return "TemplateGroupConfig(title=" + this.title + ", desc=" + this.desc + ", templates=" + this.templates + ")";
    }
}
