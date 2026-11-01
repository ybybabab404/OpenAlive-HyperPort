package com.miui.keyguard.editor.data.bean;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
/* JADX INFO: compiled from: PresetTemplateConfig.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class PresetTemplateConfig {

    @Nullable
    private CommonConfig accessConfig;

    @Nullable
    private TemplateItemConfig bindItemConfig;

    @NotNull
    private final String configUri;

    @Nullable
    private Drawable foreground;
    private final int hideFlag;
    private boolean isLoadCompleted;

    @Nullable
    private Drawable loadingDrawable;

    @Nullable
    private Bitmap previewImage;

    @Nullable
    private Bitmap wallpaper;

    public PresetTemplateConfig(@Nullable Bitmap bitmap, @Nullable Bitmap bitmap2, @Nullable Drawable drawable, @NotNull String configUri, int i) {
        Intrinsics.checkNotNullParameter(configUri, "configUri");
        this.wallpaper = bitmap;
        this.previewImage = bitmap2;
        this.foreground = drawable;
        this.configUri = configUri;
        this.hideFlag = i;
    }

    public /* synthetic */ PresetTemplateConfig(Bitmap bitmap, Bitmap bitmap2, Drawable drawable, String str, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? null : bitmap, (i2 & 2) != 0 ? null : bitmap2, (i2 & 4) != 0 ? null : drawable, str, i);
    }

    public static /* synthetic */ PresetTemplateConfig copy$default(PresetTemplateConfig presetTemplateConfig, Bitmap bitmap, Bitmap bitmap2, Drawable drawable, String str, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            bitmap = presetTemplateConfig.wallpaper;
        }
        if ((i2 & 2) != 0) {
            bitmap2 = presetTemplateConfig.previewImage;
        }
        if ((i2 & 4) != 0) {
            drawable = presetTemplateConfig.foreground;
        }
        if ((i2 & 8) != 0) {
            str = presetTemplateConfig.configUri;
        }
        if ((i2 & 16) != 0) {
            i = presetTemplateConfig.hideFlag;
        }
        int i3 = i;
        Drawable drawable2 = drawable;
        return presetTemplateConfig.copy(bitmap, bitmap2, drawable2, str, i3);
    }

    @NotNull
    public final PresetTemplateConfig clone() {
        PresetTemplateConfig presetTemplateConfig = new PresetTemplateConfig(this.wallpaper, this.previewImage, this.foreground, this.configUri, this.hideFlag);
        presetTemplateConfig.bindItemConfig = this.bindItemConfig;
        presetTemplateConfig.isLoadCompleted = this.isLoadCompleted;
        return presetTemplateConfig;
    }

    @Nullable
    public final Bitmap component1() {
        return this.wallpaper;
    }

    @Nullable
    public final Bitmap component2() {
        return this.previewImage;
    }

    @Nullable
    public final Drawable component3() {
        return this.foreground;
    }

    @NotNull
    public final String component4() {
        return this.configUri;
    }

    public final int component5() {
        return this.hideFlag;
    }

    @NotNull
    public final PresetTemplateConfig copy(@Nullable Bitmap bitmap, @Nullable Bitmap bitmap2, @Nullable Drawable drawable, @NotNull String configUri, int i) {
        Intrinsics.checkNotNullParameter(configUri, "configUri");
        return new PresetTemplateConfig(bitmap, bitmap2, drawable, configUri, i);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof PresetTemplateConfig)) {
            return false;
        }
        PresetTemplateConfig presetTemplateConfig = (PresetTemplateConfig) obj;
        return Intrinsics.areEqual(this.wallpaper, presetTemplateConfig.wallpaper) && Intrinsics.areEqual(this.previewImage, presetTemplateConfig.previewImage) && Intrinsics.areEqual(this.foreground, presetTemplateConfig.foreground) && Intrinsics.areEqual(this.configUri, presetTemplateConfig.configUri) && this.hideFlag == presetTemplateConfig.hideFlag;
    }

    @Nullable
    public final CommonConfig getAccessConfig() {
        return this.accessConfig;
    }

    @Nullable
    public final TemplateItemConfig getBindItemConfig() {
        return this.bindItemConfig;
    }

    @NotNull
    public final String getConfigUri() {
        return this.configUri;
    }

    @Nullable
    public final Drawable getForeground() {
        return this.foreground;
    }

    public final int getHideFlag() {
        return this.hideFlag;
    }

    @Nullable
    public final Drawable getLoadingDrawable() {
        return this.loadingDrawable;
    }

    @Nullable
    public final Bitmap getPreviewImage() {
        return this.previewImage;
    }

    @Nullable
    public final Bitmap getWallpaper() {
        return this.wallpaper;
    }

    public int hashCode() {
        Bitmap bitmap = this.wallpaper;
        int iHashCode = (bitmap == null ? 0 : bitmap.hashCode()) * 31;
        Bitmap bitmap2 = this.previewImage;
        int iHashCode2 = (iHashCode + (bitmap2 == null ? 0 : bitmap2.hashCode())) * 31;
        Drawable drawable = this.foreground;
        return ((((iHashCode2 + (drawable != null ? drawable.hashCode() : 0)) * 31) + this.configUri.hashCode()) * 31) + Integer.hashCode(this.hideFlag);
    }

    public final boolean isLoadCompleted() {
        return this.isLoadCompleted;
    }

    public final void setAccessConfig(@Nullable CommonConfig commonConfig) {
        this.accessConfig = commonConfig;
    }

    public final void setBindItemConfig(@Nullable TemplateItemConfig templateItemConfig) {
        this.bindItemConfig = templateItemConfig;
    }

    public final void setForeground(@Nullable Drawable drawable) {
        this.foreground = drawable;
    }

    public final void setLoadCompleted(boolean z) {
        this.isLoadCompleted = z;
    }

    public final void setLoadingDrawable(@Nullable Drawable drawable) {
        this.loadingDrawable = drawable;
    }

    public final void setPreviewImage(@Nullable Bitmap bitmap) {
        this.previewImage = bitmap;
    }

    public final void setWallpaper(@Nullable Bitmap bitmap) {
        this.wallpaper = bitmap;
    }

    @NotNull
    public String toString() {
        return "PresetTemplateConfig(wallpaper=" + this.wallpaper + ", previewImage=" + this.previewImage + ", foreground=" + this.foreground + ", configUri=" + this.configUri + ", hideFlag=" + this.hideFlag + ")";
    }
}
