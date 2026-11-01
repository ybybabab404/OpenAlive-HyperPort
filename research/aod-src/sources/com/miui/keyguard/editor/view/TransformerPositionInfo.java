package com.miui.keyguard.editor.view;

import android.graphics.Bitmap;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.homepage.bean.TemplateItemBean;
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
/* JADX INFO: compiled from: LockScreenTransformerLayer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class TransformerPositionInfo {
    public final Bitmap homeScreenshot;
    public final int itemType;
    public final Bitmap lockScreenshot;
    public Bitmap presetWallpaper;
    public final TemplateItemBean template;
    public final CommonConfig templateConfig;

    public TransformerPositionInfo(int i, Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3, TemplateItemBean templateItemBean, CommonConfig commonConfig) {
        this.itemType = i;
        this.lockScreenshot = bitmap;
        this.homeScreenshot = bitmap2;
        this.presetWallpaper = bitmap3;
        this.template = templateItemBean;
        this.templateConfig = commonConfig;
    }

    public /* synthetic */ TransformerPositionInfo(int i, Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3, TemplateItemBean templateItemBean, CommonConfig commonConfig, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(i, (i2 & 2) != 0 ? null : bitmap, (i2 & 4) != 0 ? null : bitmap2, (i2 & 8) != 0 ? null : bitmap3, (i2 & 16) != 0 ? null : templateItemBean, (i2 & 32) != 0 ? null : commonConfig);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TransformerPositionInfo)) {
            return false;
        }
        TransformerPositionInfo transformerPositionInfo = (TransformerPositionInfo) obj;
        return this.itemType == transformerPositionInfo.itemType && Intrinsics.areEqual(this.lockScreenshot, transformerPositionInfo.lockScreenshot) && Intrinsics.areEqual(this.homeScreenshot, transformerPositionInfo.homeScreenshot) && Intrinsics.areEqual(this.presetWallpaper, transformerPositionInfo.presetWallpaper) && Intrinsics.areEqual(this.template, transformerPositionInfo.template) && Intrinsics.areEqual(this.templateConfig, transformerPositionInfo.templateConfig);
    }

    public final int getItemType() {
        return this.itemType;
    }

    public final Bitmap getPresetWallpaper() {
        return this.presetWallpaper;
    }

    public final TemplateItemBean getTemplate() {
        return this.template;
    }

    public final CommonConfig getTemplateConfig() {
        return this.templateConfig;
    }

    public int hashCode() {
        int iHashCode = Integer.hashCode(this.itemType) * 31;
        Bitmap bitmap = this.lockScreenshot;
        int iHashCode2 = (iHashCode + (bitmap == null ? 0 : bitmap.hashCode())) * 31;
        Bitmap bitmap2 = this.homeScreenshot;
        int iHashCode3 = (iHashCode2 + (bitmap2 == null ? 0 : bitmap2.hashCode())) * 31;
        Bitmap bitmap3 = this.presetWallpaper;
        int iHashCode4 = (iHashCode3 + (bitmap3 == null ? 0 : bitmap3.hashCode())) * 31;
        TemplateItemBean templateItemBean = this.template;
        int iHashCode5 = (iHashCode4 + (templateItemBean == null ? 0 : templateItemBean.hashCode())) * 31;
        CommonConfig commonConfig = this.templateConfig;
        return iHashCode5 + (commonConfig != null ? commonConfig.hashCode() : 0);
    }

    public final void setPresetWallpaper(Bitmap bitmap) {
        this.presetWallpaper = bitmap;
    }

    public String toString() {
        return "TransformerPositionInfo(itemType=" + this.itemType + ", lockScreenshot=" + this.lockScreenshot + ", homeScreenshot=" + this.homeScreenshot + ", presetWallpaper=" + this.presetWallpaper + ", template=" + this.template + ", templateConfig=" + this.templateConfig + ")";
    }
}
