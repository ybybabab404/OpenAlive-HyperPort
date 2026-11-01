package com.miui.keyguard.editor.homepage.bean;

import androidx.annotation.Keep;
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
/* JADX INFO: compiled from: GalleryStatusBean.kt */
/* JADX INFO: loaded from: classes2.dex */
@Keep
@Metadata
public final class GalleryStatusBean {

    @Nullable
    private String activityAction;

    @Nullable
    private Boolean isAgreePrivacy;

    @Nullable
    private Boolean isCallGallery;

    @Nullable
    private Integer loopType;

    public GalleryStatusBean() {
        this(null, null, null, null, 15, null);
    }

    public GalleryStatusBean(@Nullable Boolean bool, @Nullable Integer num, @Nullable Boolean bool2, @Nullable String str) {
        this.isAgreePrivacy = bool;
        this.loopType = num;
        this.isCallGallery = bool2;
        this.activityAction = str;
    }

    public /* synthetic */ GalleryStatusBean(Boolean bool, Integer num, Boolean bool2, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? Boolean.FALSE : bool, (i & 2) != 0 ? -1 : num, (i & 4) != 0 ? Boolean.FALSE : bool2, (i & 8) != 0 ? "" : str);
    }

    public static /* synthetic */ GalleryStatusBean copy$default(GalleryStatusBean galleryStatusBean, Boolean bool, Integer num, Boolean bool2, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            bool = galleryStatusBean.isAgreePrivacy;
        }
        if ((i & 2) != 0) {
            num = galleryStatusBean.loopType;
        }
        if ((i & 4) != 0) {
            bool2 = galleryStatusBean.isCallGallery;
        }
        if ((i & 8) != 0) {
            str = galleryStatusBean.activityAction;
        }
        return galleryStatusBean.copy(bool, num, bool2, str);
    }

    @Nullable
    public final Boolean component1() {
        return this.isAgreePrivacy;
    }

    @Nullable
    public final Integer component2() {
        return this.loopType;
    }

    @Nullable
    public final Boolean component3() {
        return this.isCallGallery;
    }

    @Nullable
    public final String component4() {
        return this.activityAction;
    }

    @NotNull
    public final GalleryStatusBean copy(@Nullable Boolean bool, @Nullable Integer num, @Nullable Boolean bool2, @Nullable String str) {
        return new GalleryStatusBean(bool, num, bool2, str);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GalleryStatusBean)) {
            return false;
        }
        GalleryStatusBean galleryStatusBean = (GalleryStatusBean) obj;
        return Intrinsics.areEqual(this.isAgreePrivacy, galleryStatusBean.isAgreePrivacy) && Intrinsics.areEqual(this.loopType, galleryStatusBean.loopType) && Intrinsics.areEqual(this.isCallGallery, galleryStatusBean.isCallGallery) && Intrinsics.areEqual(this.activityAction, galleryStatusBean.activityAction);
    }

    @Nullable
    public final String getActivityAction() {
        return this.activityAction;
    }

    @Nullable
    public final Integer getLoopType() {
        return this.loopType;
    }

    public int hashCode() {
        Boolean bool = this.isAgreePrivacy;
        int iHashCode = (bool == null ? 0 : bool.hashCode()) * 31;
        Integer num = this.loopType;
        int iHashCode2 = (iHashCode + (num == null ? 0 : num.hashCode())) * 31;
        Boolean bool2 = this.isCallGallery;
        int iHashCode3 = (iHashCode2 + (bool2 == null ? 0 : bool2.hashCode())) * 31;
        String str = this.activityAction;
        return iHashCode3 + (str != null ? str.hashCode() : 0);
    }

    @Nullable
    public final Boolean isAgreePrivacy() {
        return this.isAgreePrivacy;
    }

    @Nullable
    public final Boolean isCallGallery() {
        return this.isCallGallery;
    }

    public final void setActivityAction(@Nullable String str) {
        this.activityAction = str;
    }

    public final void setAgreePrivacy(@Nullable Boolean bool) {
        this.isAgreePrivacy = bool;
    }

    public final void setCallGallery(@Nullable Boolean bool) {
        this.isCallGallery = bool;
    }

    public final void setLoopType(@Nullable Integer num) {
        this.loopType = num;
    }

    @NotNull
    public String toString() {
        return "GalleryStatusBean(isAgreePrivacy=" + this.isAgreePrivacy + ", loopType=" + this.loopType + ", isCallGallery=" + this.isCallGallery + ", activityAction=" + this.activityAction + ")";
    }
}
