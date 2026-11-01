package com.miui.keyguard.editor.data.bean;

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
/* JADX INFO: compiled from: TemplateConfig.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ExtraInfo {

    @Nullable
    private Integer loopType;

    @Nullable
    private String originLivePhotoImagePath;

    /* JADX WARN: Multi-variable type inference failed */
    public ExtraInfo() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    public ExtraInfo(@Nullable String str, @Nullable Integer num) {
        this.originLivePhotoImagePath = str;
        this.loopType = num;
    }

    public /* synthetic */ ExtraInfo(String str, Integer num, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : num);
    }

    public static /* synthetic */ ExtraInfo copy$default(ExtraInfo extraInfo, String str, Integer num, int i, Object obj) {
        if ((i & 1) != 0) {
            str = extraInfo.originLivePhotoImagePath;
        }
        if ((i & 2) != 0) {
            num = extraInfo.loopType;
        }
        return extraInfo.copy(str, num);
    }

    @Nullable
    public final String component1() {
        return this.originLivePhotoImagePath;
    }

    @Nullable
    public final Integer component2() {
        return this.loopType;
    }

    @NotNull
    public final ExtraInfo copy(@Nullable String str, @Nullable Integer num) {
        return new ExtraInfo(str, num);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ExtraInfo)) {
            return false;
        }
        ExtraInfo extraInfo = (ExtraInfo) obj;
        return Intrinsics.areEqual(this.originLivePhotoImagePath, extraInfo.originLivePhotoImagePath) && Intrinsics.areEqual(this.loopType, extraInfo.loopType);
    }

    @Nullable
    public final Integer getLoopType() {
        return this.loopType;
    }

    @Nullable
    public final String getOriginLivePhotoImagePath() {
        return this.originLivePhotoImagePath;
    }

    public int hashCode() {
        String str = this.originLivePhotoImagePath;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        Integer num = this.loopType;
        return iHashCode + (num != null ? num.hashCode() : 0);
    }

    public final void setLoopType(@Nullable Integer num) {
        this.loopType = num;
    }

    public final void setOriginLivePhotoImagePath(@Nullable String str) {
        this.originLivePhotoImagePath = str;
    }

    @NotNull
    public String toString() {
        return "ExtraInfo(originLivePhotoImagePath=" + this.originLivePhotoImagePath + ", loopType=" + this.loopType + ")";
    }
}
