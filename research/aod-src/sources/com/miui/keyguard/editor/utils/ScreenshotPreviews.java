package com.miui.keyguard.editor.utils;

import android.widget.ImageView;
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
/* JADX INFO: compiled from: DeviceScreenshotHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ScreenshotPreviews {
    public ImageView largeLandscape;
    public ImageView largePortrait;
    public ImageView small;
    public int total;

    public ScreenshotPreviews(ImageView imageView, ImageView imageView2, ImageView imageView3, int i) {
        this.small = imageView;
        this.largePortrait = imageView2;
        this.largeLandscape = imageView3;
        this.total = i;
    }

    public /* synthetic */ ScreenshotPreviews(ImageView imageView, ImageView imageView2, ImageView imageView3, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? null : imageView, (i2 & 2) != 0 ? null : imageView2, (i2 & 4) != 0 ? null : imageView3, (i2 & 8) != 0 ? 1 : i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ScreenshotPreviews)) {
            return false;
        }
        ScreenshotPreviews screenshotPreviews = (ScreenshotPreviews) obj;
        return Intrinsics.areEqual(this.small, screenshotPreviews.small) && Intrinsics.areEqual(this.largePortrait, screenshotPreviews.largePortrait) && Intrinsics.areEqual(this.largeLandscape, screenshotPreviews.largeLandscape) && this.total == screenshotPreviews.total;
    }

    public final ImageView getLargeLandscape() {
        return this.largeLandscape;
    }

    public final ImageView getLargePortrait() {
        return this.largePortrait;
    }

    public final ImageView getSmall() {
        return this.small;
    }

    public final int getTotal() {
        return this.total;
    }

    public int hashCode() {
        ImageView imageView = this.small;
        int iHashCode = (imageView == null ? 0 : imageView.hashCode()) * 31;
        ImageView imageView2 = this.largePortrait;
        int iHashCode2 = (iHashCode + (imageView2 == null ? 0 : imageView2.hashCode())) * 31;
        ImageView imageView3 = this.largeLandscape;
        return ((iHashCode2 + (imageView3 != null ? imageView3.hashCode() : 0)) * 31) + Integer.hashCode(this.total);
    }

    public final void setLargeLandscape(ImageView imageView) {
        this.largeLandscape = imageView;
    }

    public final void setLargePortrait(ImageView imageView) {
        this.largePortrait = imageView;
    }

    public final void setSmall(ImageView imageView) {
        this.small = imageView;
    }

    public final void setTotal(int i) {
        this.total = i;
    }

    public String toString() {
        return "ScreenshotPreviews(small=" + this.small + ", largePortrait=" + this.largePortrait + ", largeLandscape=" + this.largeLandscape + ", total=" + this.total + ")";
    }
}
