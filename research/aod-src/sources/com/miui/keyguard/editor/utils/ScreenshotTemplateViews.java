package com.miui.keyguard.editor.utils;

import com.miui.keyguard.editor.edit.base.BaseTemplateView;
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
public final class ScreenshotTemplateViews {
    public HideScreenshotCallback largeLandCallback;
    public BaseTemplateView largeLandscape;
    public HideScreenshotCallback largePortCallback;
    public BaseTemplateView largePortrait;
    public BaseTemplateView small;
    public HideScreenshotCallback smallCallback;
    public int total;

    public ScreenshotTemplateViews(BaseTemplateView baseTemplateView, BaseTemplateView baseTemplateView2, BaseTemplateView baseTemplateView3, HideScreenshotCallback hideScreenshotCallback, HideScreenshotCallback hideScreenshotCallback2, HideScreenshotCallback hideScreenshotCallback3, int i) {
        this.small = baseTemplateView;
        this.largePortrait = baseTemplateView2;
        this.largeLandscape = baseTemplateView3;
        this.smallCallback = hideScreenshotCallback;
        this.largePortCallback = hideScreenshotCallback2;
        this.largeLandCallback = hideScreenshotCallback3;
        this.total = i;
    }

    public /* synthetic */ ScreenshotTemplateViews(BaseTemplateView baseTemplateView, BaseTemplateView baseTemplateView2, BaseTemplateView baseTemplateView3, HideScreenshotCallback hideScreenshotCallback, HideScreenshotCallback hideScreenshotCallback2, HideScreenshotCallback hideScreenshotCallback3, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? null : baseTemplateView, (i2 & 2) != 0 ? null : baseTemplateView2, (i2 & 4) != 0 ? null : baseTemplateView3, (i2 & 8) != 0 ? null : hideScreenshotCallback, (i2 & 16) != 0 ? null : hideScreenshotCallback2, (i2 & 32) != 0 ? null : hideScreenshotCallback3, (i2 & 64) != 0 ? 1 : i);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ScreenshotTemplateViews)) {
            return false;
        }
        ScreenshotTemplateViews screenshotTemplateViews = (ScreenshotTemplateViews) obj;
        return Intrinsics.areEqual(this.small, screenshotTemplateViews.small) && Intrinsics.areEqual(this.largePortrait, screenshotTemplateViews.largePortrait) && Intrinsics.areEqual(this.largeLandscape, screenshotTemplateViews.largeLandscape) && Intrinsics.areEqual(this.smallCallback, screenshotTemplateViews.smallCallback) && Intrinsics.areEqual(this.largePortCallback, screenshotTemplateViews.largePortCallback) && Intrinsics.areEqual(this.largeLandCallback, screenshotTemplateViews.largeLandCallback) && this.total == screenshotTemplateViews.total;
    }

    public final HideScreenshotCallback getLargeLandCallback() {
        return this.largeLandCallback;
    }

    public final BaseTemplateView getLargeLandscape() {
        return this.largeLandscape;
    }

    public final HideScreenshotCallback getLargePortCallback() {
        return this.largePortCallback;
    }

    public final BaseTemplateView getLargePortrait() {
        return this.largePortrait;
    }

    public final BaseTemplateView getSmall() {
        return this.small;
    }

    public final HideScreenshotCallback getSmallCallback() {
        return this.smallCallback;
    }

    public int hashCode() {
        BaseTemplateView baseTemplateView = this.small;
        int iHashCode = (baseTemplateView == null ? 0 : baseTemplateView.hashCode()) * 31;
        BaseTemplateView baseTemplateView2 = this.largePortrait;
        int iHashCode2 = (iHashCode + (baseTemplateView2 == null ? 0 : baseTemplateView2.hashCode())) * 31;
        BaseTemplateView baseTemplateView3 = this.largeLandscape;
        int iHashCode3 = (iHashCode2 + (baseTemplateView3 == null ? 0 : baseTemplateView3.hashCode())) * 31;
        HideScreenshotCallback hideScreenshotCallback = this.smallCallback;
        int iHashCode4 = (iHashCode3 + (hideScreenshotCallback == null ? 0 : hideScreenshotCallback.hashCode())) * 31;
        HideScreenshotCallback hideScreenshotCallback2 = this.largePortCallback;
        int iHashCode5 = (iHashCode4 + (hideScreenshotCallback2 == null ? 0 : hideScreenshotCallback2.hashCode())) * 31;
        HideScreenshotCallback hideScreenshotCallback3 = this.largeLandCallback;
        return ((iHashCode5 + (hideScreenshotCallback3 != null ? hideScreenshotCallback3.hashCode() : 0)) * 31) + Integer.hashCode(this.total);
    }

    public final void setLargeLandCallback(HideScreenshotCallback hideScreenshotCallback) {
        this.largeLandCallback = hideScreenshotCallback;
    }

    public final void setLargeLandscape(BaseTemplateView baseTemplateView) {
        this.largeLandscape = baseTemplateView;
    }

    public final void setLargePortCallback(HideScreenshotCallback hideScreenshotCallback) {
        this.largePortCallback = hideScreenshotCallback;
    }

    public final void setLargePortrait(BaseTemplateView baseTemplateView) {
        this.largePortrait = baseTemplateView;
    }

    public final void setSmall(BaseTemplateView baseTemplateView) {
        this.small = baseTemplateView;
    }

    public final void setSmallCallback(HideScreenshotCallback hideScreenshotCallback) {
        this.smallCallback = hideScreenshotCallback;
    }

    public final void setTotal(int i) {
        this.total = i;
    }

    public String toString() {
        return "ScreenshotTemplateViews(small=" + this.small + ", largePortrait=" + this.largePortrait + ", largeLandscape=" + this.largeLandscape + ", smallCallback=" + this.smallCallback + ", largePortCallback=" + this.largePortCallback + ", largeLandCallback=" + this.largeLandCallback + ", total=" + this.total + ")";
    }
}
