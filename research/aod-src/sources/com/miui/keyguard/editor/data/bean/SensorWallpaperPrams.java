package com.miui.keyguard.editor.data.bean;

import android.graphics.Bitmap;
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
/* JADX INFO: compiled from: WallpaperTypeInfo.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class SensorWallpaperPrams {

    @Nullable
    private Integer allFrames;

    @Nullable
    private String animType;

    @Nullable
    private transient Bitmap currentSmallSensorWallpaper;

    @Nullable
    private Integer duration;

    @Nullable
    private Float factor;

    @Nullable
    private String outerVideoPath;

    @Nullable
    private String outerVideoPreViewPath;

    @Nullable
    private Integer smallFrames;

    @Nullable
    private String smallThumbnailPath;

    @Nullable
    private Integer stiffness;

    @Nullable
    private String thumbnailPath;

    public SensorWallpaperPrams() {
        this(null, null, null, null, null, null, null, null, null, null, 1023, null);
    }

    public SensorWallpaperPrams(@Nullable String str, @Nullable String str2, @Nullable Integer num, @Nullable Integer num2, @Nullable Integer num3, @Nullable Integer num4, @Nullable Float f, @Nullable String str3, @Nullable String str4, @Nullable String str5) {
        this.thumbnailPath = str;
        this.smallThumbnailPath = str2;
        this.allFrames = num;
        this.smallFrames = num2;
        this.stiffness = num3;
        this.duration = num4;
        this.factor = f;
        this.animType = str3;
        this.outerVideoPath = str4;
        this.outerVideoPreViewPath = str5;
    }

    public /* synthetic */ SensorWallpaperPrams(String str, String str2, Integer num, Integer num2, Integer num3, Integer num4, Float f, String str3, String str4, String str5, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? -1 : num, (i & 8) != 0 ? 0 : num2, (i & 16) != 0 ? 10 : num3, (i & 32) != 0 ? 0 : num4, (i & 64) != 0 ? Float.valueOf(0.0f) : f, (i & 128) != 0 ? null : str3, (i & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0 ? null : str4, (i & 512) != 0 ? null : str5);
    }

    public static /* synthetic */ SensorWallpaperPrams copy$default(SensorWallpaperPrams sensorWallpaperPrams, String str, String str2, Integer num, Integer num2, Integer num3, Integer num4, Float f, String str3, String str4, String str5, int i, Object obj) {
        if ((i & 1) != 0) {
            str = sensorWallpaperPrams.thumbnailPath;
        }
        if ((i & 2) != 0) {
            str2 = sensorWallpaperPrams.smallThumbnailPath;
        }
        if ((i & 4) != 0) {
            num = sensorWallpaperPrams.allFrames;
        }
        if ((i & 8) != 0) {
            num2 = sensorWallpaperPrams.smallFrames;
        }
        if ((i & 16) != 0) {
            num3 = sensorWallpaperPrams.stiffness;
        }
        if ((i & 32) != 0) {
            num4 = sensorWallpaperPrams.duration;
        }
        if ((i & 64) != 0) {
            f = sensorWallpaperPrams.factor;
        }
        if ((i & 128) != 0) {
            str3 = sensorWallpaperPrams.animType;
        }
        if ((i & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0) {
            str4 = sensorWallpaperPrams.outerVideoPath;
        }
        if ((i & 512) != 0) {
            str5 = sensorWallpaperPrams.outerVideoPreViewPath;
        }
        String str6 = str4;
        String str7 = str5;
        Float f2 = f;
        String str8 = str3;
        Integer num5 = num3;
        Integer num6 = num4;
        return sensorWallpaperPrams.copy(str, str2, num, num2, num5, num6, f2, str8, str6, str7);
    }

    @Nullable
    public final String component1() {
        return this.thumbnailPath;
    }

    @Nullable
    public final String component10() {
        return this.outerVideoPreViewPath;
    }

    @Nullable
    public final String component2() {
        return this.smallThumbnailPath;
    }

    @Nullable
    public final Integer component3() {
        return this.allFrames;
    }

    @Nullable
    public final Integer component4() {
        return this.smallFrames;
    }

    @Nullable
    public final Integer component5() {
        return this.stiffness;
    }

    @Nullable
    public final Integer component6() {
        return this.duration;
    }

    @Nullable
    public final Float component7() {
        return this.factor;
    }

    @Nullable
    public final String component8() {
        return this.animType;
    }

    @Nullable
    public final String component9() {
        return this.outerVideoPath;
    }

    @NotNull
    public final SensorWallpaperPrams copy(@Nullable String str, @Nullable String str2, @Nullable Integer num, @Nullable Integer num2, @Nullable Integer num3, @Nullable Integer num4, @Nullable Float f, @Nullable String str3, @Nullable String str4, @Nullable String str5) {
        return new SensorWallpaperPrams(str, str2, num, num2, num3, num4, f, str3, str4, str5);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SensorWallpaperPrams)) {
            return false;
        }
        SensorWallpaperPrams sensorWallpaperPrams = (SensorWallpaperPrams) obj;
        return Intrinsics.areEqual(this.thumbnailPath, sensorWallpaperPrams.thumbnailPath) && Intrinsics.areEqual(this.smallThumbnailPath, sensorWallpaperPrams.smallThumbnailPath) && Intrinsics.areEqual(this.allFrames, sensorWallpaperPrams.allFrames) && Intrinsics.areEqual(this.smallFrames, sensorWallpaperPrams.smallFrames) && Intrinsics.areEqual(this.stiffness, sensorWallpaperPrams.stiffness) && Intrinsics.areEqual(this.duration, sensorWallpaperPrams.duration) && Intrinsics.areEqual(this.factor, sensorWallpaperPrams.factor) && Intrinsics.areEqual(this.animType, sensorWallpaperPrams.animType) && Intrinsics.areEqual(this.outerVideoPath, sensorWallpaperPrams.outerVideoPath) && Intrinsics.areEqual(this.outerVideoPreViewPath, sensorWallpaperPrams.outerVideoPreViewPath);
    }

    @Nullable
    public final Integer getAllFrames() {
        return this.allFrames;
    }

    @Nullable
    public final String getAnimType() {
        return this.animType;
    }

    @Nullable
    public final Bitmap getCurrentSmallSensorWallpaper() {
        return this.currentSmallSensorWallpaper;
    }

    @Nullable
    public final Integer getDuration() {
        return this.duration;
    }

    @Nullable
    public final Float getFactor() {
        return this.factor;
    }

    @Nullable
    public final String getOuterVideoPath() {
        return this.outerVideoPath;
    }

    @Nullable
    public final String getOuterVideoPreViewPath() {
        return this.outerVideoPreViewPath;
    }

    @Nullable
    public final Integer getSmallFrames() {
        return this.smallFrames;
    }

    @Nullable
    public final String getSmallThumbnailPath() {
        return this.smallThumbnailPath;
    }

    @Nullable
    public final Integer getStiffness() {
        return this.stiffness;
    }

    @Nullable
    public final String getThumbnailPath() {
        return this.thumbnailPath;
    }

    public int hashCode() {
        String str = this.thumbnailPath;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.smallThumbnailPath;
        int iHashCode2 = (iHashCode + (str2 == null ? 0 : str2.hashCode())) * 31;
        Integer num = this.allFrames;
        int iHashCode3 = (iHashCode2 + (num == null ? 0 : num.hashCode())) * 31;
        Integer num2 = this.smallFrames;
        int iHashCode4 = (iHashCode3 + (num2 == null ? 0 : num2.hashCode())) * 31;
        Integer num3 = this.stiffness;
        int iHashCode5 = (iHashCode4 + (num3 == null ? 0 : num3.hashCode())) * 31;
        Integer num4 = this.duration;
        int iHashCode6 = (iHashCode5 + (num4 == null ? 0 : num4.hashCode())) * 31;
        Float f = this.factor;
        int iHashCode7 = (iHashCode6 + (f == null ? 0 : f.hashCode())) * 31;
        String str3 = this.animType;
        int iHashCode8 = (iHashCode7 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.outerVideoPath;
        int iHashCode9 = (iHashCode8 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.outerVideoPreViewPath;
        return iHashCode9 + (str5 != null ? str5.hashCode() : 0);
    }

    public final void setAllFrames(@Nullable Integer num) {
        this.allFrames = num;
    }

    public final void setAnimType(@Nullable String str) {
        this.animType = str;
    }

    public final void setCurrentSmallSensorWallpaper(@Nullable Bitmap bitmap) {
        this.currentSmallSensorWallpaper = bitmap;
    }

    public final void setDuration(@Nullable Integer num) {
        this.duration = num;
    }

    public final void setFactor(@Nullable Float f) {
        this.factor = f;
    }

    public final void setOuterVideoPath(@Nullable String str) {
        this.outerVideoPath = str;
    }

    public final void setOuterVideoPreViewPath(@Nullable String str) {
        this.outerVideoPreViewPath = str;
    }

    public final void setSmallFrames(@Nullable Integer num) {
        this.smallFrames = num;
    }

    public final void setSmallThumbnailPath(@Nullable String str) {
        this.smallThumbnailPath = str;
    }

    public final void setStiffness(@Nullable Integer num) {
        this.stiffness = num;
    }

    public final void setThumbnailPath(@Nullable String str) {
        this.thumbnailPath = str;
    }

    @NotNull
    public String toString() {
        return "SensorWallpaperPrams(thumbnailPath=" + this.thumbnailPath + ", smallThumbnailPath=" + this.smallThumbnailPath + ", allFrames=" + this.allFrames + ", smallFrames=" + this.smallFrames + ", stiffness=" + this.stiffness + ", duration=" + this.duration + ", factor=" + this.factor + ", animType=" + this.animType + ", outerVideoPath=" + this.outerVideoPath + ", outerVideoPreViewPath=" + this.outerVideoPreViewPath + ")";
    }
}
