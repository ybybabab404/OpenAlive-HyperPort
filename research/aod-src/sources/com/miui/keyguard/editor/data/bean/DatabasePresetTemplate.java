package com.miui.keyguard.editor.data.bean;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
/* JADX INFO: compiled from: DatabasePresetTemplate.kt */
/* JADX INFO: loaded from: classes2.dex */
@Entity
@Metadata
public final class DatabasePresetTemplate {
    private final int hideParam;

    @PrimaryKey
    private int id;

    @Nullable
    private final String systemVersion;

    @Nullable
    private final String templateId;

    @Nullable
    private final String templateName;
    private final int version;

    public DatabasePresetTemplate(@Nullable String str, @Nullable String str2, int i, int i2, @Nullable String str3) {
        this.templateName = str;
        this.templateId = str2;
        this.hideParam = i;
        this.version = i2;
        this.systemVersion = str3;
    }

    public /* synthetic */ DatabasePresetTemplate(String str, String str2, int i, int i2, String str3, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? null : str, (i3 & 2) != 0 ? null : str2, (i3 & 4) != 0 ? 0 : i, (i3 & 8) != 0 ? 16 : i2, str3);
    }

    public static /* synthetic */ DatabasePresetTemplate copy$default(DatabasePresetTemplate databasePresetTemplate, String str, String str2, int i, int i2, String str3, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            str = databasePresetTemplate.templateName;
        }
        if ((i3 & 2) != 0) {
            str2 = databasePresetTemplate.templateId;
        }
        if ((i3 & 4) != 0) {
            i = databasePresetTemplate.hideParam;
        }
        if ((i3 & 8) != 0) {
            i2 = databasePresetTemplate.version;
        }
        if ((i3 & 16) != 0) {
            str3 = databasePresetTemplate.systemVersion;
        }
        String str4 = str3;
        int i4 = i;
        return databasePresetTemplate.copy(str, str2, i4, i2, str4);
    }

    @Nullable
    public final String component1() {
        return this.templateName;
    }

    @Nullable
    public final String component2() {
        return this.templateId;
    }

    public final int component3() {
        return this.hideParam;
    }

    public final int component4() {
        return this.version;
    }

    @Nullable
    public final String component5() {
        return this.systemVersion;
    }

    @NotNull
    public final DatabasePresetTemplate copy(@Nullable String str, @Nullable String str2, int i, int i2, @Nullable String str3) {
        return new DatabasePresetTemplate(str, str2, i, i2, str3);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof DatabasePresetTemplate)) {
            return false;
        }
        DatabasePresetTemplate databasePresetTemplate = (DatabasePresetTemplate) obj;
        return Intrinsics.areEqual(this.templateName, databasePresetTemplate.templateName) && Intrinsics.areEqual(this.templateId, databasePresetTemplate.templateId) && this.hideParam == databasePresetTemplate.hideParam && this.version == databasePresetTemplate.version && Intrinsics.areEqual(this.systemVersion, databasePresetTemplate.systemVersion);
    }

    public final int getHideParam() {
        return this.hideParam;
    }

    public final int getId() {
        return this.id;
    }

    @Nullable
    public final String getSystemVersion() {
        return this.systemVersion;
    }

    @Nullable
    public final String getTemplateId() {
        return this.templateId;
    }

    @Nullable
    public final String getTemplateName() {
        return this.templateName;
    }

    public final int getVersion() {
        return this.version;
    }

    public int hashCode() {
        String str = this.templateName;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.templateId;
        int iHashCode2 = (((((iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31) + Integer.hashCode(this.hideParam)) * 31) + Integer.hashCode(this.version)) * 31;
        String str3 = this.systemVersion;
        return iHashCode2 + (str3 != null ? str3.hashCode() : 0);
    }

    public final void setId(int i) {
        this.id = i;
    }

    @NotNull
    public String toString() {
        return "DatabasePresetTemplate(templateName=" + this.templateName + ", templateId=" + this.templateId + ", hideParam=" + this.hideParam + ", version=" + this.version + ", systemVersion=" + this.systemVersion + ")";
    }
}
