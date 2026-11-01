package com.miui.keyguard.editor.data.bean;

import com.miui.keyguard.editor.view.AutoBottomSheetKt;
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
/* JADX INFO: compiled from: TemplateGroupConfig.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class TemplateItemConfig {
    private int belowLayerResId;
    private int clockLayerResId;
    private final boolean depth;
    private final boolean depthVideo;

    @Nullable
    private final String effectWallpaperPath;
    private int hideFlag;
    private final int loadingColor;

    @Nullable
    private final String maskPath;
    private final boolean preload;
    private int reqHeight;
    private int reqWidth;

    @Nullable
    private final String templateId;

    @NotNull
    private final String templateName;

    @NotNull
    private final String wallpaperPath;

    public TemplateItemConfig(@NotNull String templateName, @NotNull String wallpaperPath, @Nullable String str, boolean z, boolean z2, boolean z3, @Nullable String str2, int i, @Nullable String str3) {
        Intrinsics.checkNotNullParameter(templateName, "templateName");
        Intrinsics.checkNotNullParameter(wallpaperPath, "wallpaperPath");
        this.templateName = templateName;
        this.wallpaperPath = wallpaperPath;
        this.maskPath = str;
        this.depth = z;
        this.depthVideo = z2;
        this.preload = z3;
        this.effectWallpaperPath = str2;
        this.loadingColor = i;
        this.templateId = str3;
    }

    public /* synthetic */ TemplateItemConfig(String str, String str2, String str3, boolean z, boolean z2, boolean z3, String str4, int i, String str5, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, (i2 & 8) != 0 ? false : z, (i2 & 16) != 0 ? false : z2, (i2 & 32) != 0 ? false : z3, str4, (i2 & 128) != 0 ? 0 : i, str5);
    }

    public static /* synthetic */ TemplateItemConfig copy$default(TemplateItemConfig templateItemConfig, String str, String str2, String str3, boolean z, boolean z2, boolean z3, String str4, int i, String str5, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            str = templateItemConfig.templateName;
        }
        if ((i2 & 2) != 0) {
            str2 = templateItemConfig.wallpaperPath;
        }
        if ((i2 & 4) != 0) {
            str3 = templateItemConfig.maskPath;
        }
        if ((i2 & 8) != 0) {
            z = templateItemConfig.depth;
        }
        if ((i2 & 16) != 0) {
            z2 = templateItemConfig.depthVideo;
        }
        if ((i2 & 32) != 0) {
            z3 = templateItemConfig.preload;
        }
        if ((i2 & 64) != 0) {
            str4 = templateItemConfig.effectWallpaperPath;
        }
        if ((i2 & 128) != 0) {
            i = templateItemConfig.loadingColor;
        }
        if ((i2 & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0) {
            str5 = templateItemConfig.templateId;
        }
        int i3 = i;
        String str6 = str5;
        boolean z4 = z3;
        String str7 = str4;
        boolean z5 = z2;
        String str8 = str3;
        return templateItemConfig.copy(str, str2, str8, z, z5, z4, str7, i3, str6);
    }

    @NotNull
    public final String component1() {
        return this.templateName;
    }

    @NotNull
    public final String component2() {
        return this.wallpaperPath;
    }

    @Nullable
    public final String component3() {
        return this.maskPath;
    }

    public final boolean component4() {
        return this.depth;
    }

    public final boolean component5() {
        return this.depthVideo;
    }

    public final boolean component6() {
        return this.preload;
    }

    @Nullable
    public final String component7() {
        return this.effectWallpaperPath;
    }

    public final int component8() {
        return this.loadingColor;
    }

    @Nullable
    public final String component9() {
        return this.templateId;
    }

    @NotNull
    public final TemplateItemConfig copy(@NotNull String templateName, @NotNull String wallpaperPath, @Nullable String str, boolean z, boolean z2, boolean z3, @Nullable String str2, int i, @Nullable String str3) {
        Intrinsics.checkNotNullParameter(templateName, "templateName");
        Intrinsics.checkNotNullParameter(wallpaperPath, "wallpaperPath");
        return new TemplateItemConfig(templateName, wallpaperPath, str, z, z2, z3, str2, i, str3);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TemplateItemConfig)) {
            return false;
        }
        TemplateItemConfig templateItemConfig = (TemplateItemConfig) obj;
        return Intrinsics.areEqual(this.templateName, templateItemConfig.templateName) && Intrinsics.areEqual(this.wallpaperPath, templateItemConfig.wallpaperPath) && Intrinsics.areEqual(this.maskPath, templateItemConfig.maskPath) && this.depth == templateItemConfig.depth && this.depthVideo == templateItemConfig.depthVideo && this.preload == templateItemConfig.preload && Intrinsics.areEqual(this.effectWallpaperPath, templateItemConfig.effectWallpaperPath) && this.loadingColor == templateItemConfig.loadingColor && Intrinsics.areEqual(this.templateId, templateItemConfig.templateId);
    }

    public final int getBelowLayerResId() {
        return this.belowLayerResId;
    }

    public final int getClockLayerResId() {
        return this.clockLayerResId;
    }

    public final boolean getDepth() {
        return this.depth;
    }

    public final boolean getDepthVideo() {
        return this.depthVideo;
    }

    @Nullable
    public final String getEffectWallpaperPath() {
        return this.effectWallpaperPath;
    }

    public final int getHideFlag() {
        return this.hideFlag;
    }

    public final int getLoadingColor() {
        return this.loadingColor;
    }

    @Nullable
    public final String getMaskPath() {
        return this.maskPath;
    }

    public final boolean getPreload() {
        return this.preload;
    }

    public final int getReqHeight() {
        return this.reqHeight;
    }

    public final int getReqWidth() {
        return this.reqWidth;
    }

    @Nullable
    public final String getTemplateId() {
        return this.templateId;
    }

    @NotNull
    public final String getTemplateName() {
        return this.templateName;
    }

    @NotNull
    public final String getWallpaperPath() {
        return this.wallpaperPath;
    }

    public int hashCode() {
        int iHashCode = ((this.templateName.hashCode() * 31) + this.wallpaperPath.hashCode()) * 31;
        String str = this.maskPath;
        int iHashCode2 = (((((((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + Boolean.hashCode(this.depth)) * 31) + Boolean.hashCode(this.depthVideo)) * 31) + Boolean.hashCode(this.preload)) * 31;
        String str2 = this.effectWallpaperPath;
        int iHashCode3 = (((iHashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31) + Integer.hashCode(this.loadingColor)) * 31;
        String str3 = this.templateId;
        return iHashCode3 + (str3 != null ? str3.hashCode() : 0);
    }

    public final void setBelowLayerResId(int i) {
        this.belowLayerResId = i;
    }

    public final void setClockLayerResId(int i) {
        this.clockLayerResId = i;
    }

    public final void setHideFlag(int i) {
        this.hideFlag = i;
    }

    public final void setReqHeight(int i) {
        this.reqHeight = i;
    }

    public final void setReqWidth(int i) {
        this.reqWidth = i;
    }

    @NotNull
    public String toString() {
        return "TemplateItemConfig(templateName=" + this.templateName + ", wallpaperPath=" + this.wallpaperPath + ", maskPath=" + this.maskPath + ", depth=" + this.depth + ", depthVideo=" + this.depthVideo + ", preload=" + this.preload + ", effectWallpaperPath=" + this.effectWallpaperPath + ", loadingColor=" + this.loadingColor + ", templateId=" + this.templateId + ")";
    }
}
