package com.miui.aod.category;

import com.google.gson.annotations.SerializedName;
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
/* JADX INFO: compiled from: SuperWallpaperInfo.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class SuperWallpaperInfo {

    @SerializedName("aod_category")
    @NotNull
    private final String mAodCategory;

    @SerializedName("clock_position_x")
    private final float mClockPositionX;

    @SerializedName("clock_position_y")
    private final float mClockPositionY;

    @SerializedName("dual_clock_position_x_anchor_right")
    private final float mDualClockPositionX;

    @SerializedName("dual_clock_position_y")
    private final float mDualClockPositionY;

    @SerializedName("notification_position_y")
    private final float mNotificationPositionY;

    @SerializedName("support_change_with_time")
    private final int mSupportChangeWithTime;

    public SuperWallpaperInfo() {
        this(0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0, null, 127, null);
    }

    public SuperWallpaperInfo(float f, float f2, float f3, float f4, float f5, int i, @NotNull String mAodCategory) {
        Intrinsics.checkNotNullParameter(mAodCategory, "mAodCategory");
        this.mClockPositionY = f;
        this.mClockPositionX = f2;
        this.mDualClockPositionY = f3;
        this.mDualClockPositionX = f4;
        this.mNotificationPositionY = f5;
        this.mSupportChangeWithTime = i;
        this.mAodCategory = mAodCategory;
    }

    public /* synthetic */ SuperWallpaperInfo(float f, float f2, float f3, float f4, float f5, int i, String str, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this((i2 & 1) != 0 ? 0.0f : f, (i2 & 2) != 0 ? 0.0f : f2, (i2 & 4) != 0 ? 0.0f : f3, (i2 & 8) != 0 ? 0.0f : f4, (i2 & 16) != 0 ? 0.0f : f5, (i2 & 32) != 0 ? 0 : i, (i2 & 64) != 0 ? "" : str);
    }

    public static /* synthetic */ SuperWallpaperInfo copy$default(SuperWallpaperInfo superWallpaperInfo, float f, float f2, float f3, float f4, float f5, int i, String str, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            f = superWallpaperInfo.mClockPositionY;
        }
        if ((i2 & 2) != 0) {
            f2 = superWallpaperInfo.mClockPositionX;
        }
        if ((i2 & 4) != 0) {
            f3 = superWallpaperInfo.mDualClockPositionY;
        }
        if ((i2 & 8) != 0) {
            f4 = superWallpaperInfo.mDualClockPositionX;
        }
        if ((i2 & 16) != 0) {
            f5 = superWallpaperInfo.mNotificationPositionY;
        }
        if ((i2 & 32) != 0) {
            i = superWallpaperInfo.mSupportChangeWithTime;
        }
        if ((i2 & 64) != 0) {
            str = superWallpaperInfo.mAodCategory;
        }
        int i3 = i;
        String str2 = str;
        float f6 = f5;
        float f7 = f3;
        return superWallpaperInfo.copy(f, f2, f7, f4, f6, i3, str2);
    }

    public final float component1() {
        return this.mClockPositionY;
    }

    public final float component2() {
        return this.mClockPositionX;
    }

    public final float component3() {
        return this.mDualClockPositionY;
    }

    public final float component4() {
        return this.mDualClockPositionX;
    }

    public final float component5() {
        return this.mNotificationPositionY;
    }

    public final int component6() {
        return this.mSupportChangeWithTime;
    }

    @NotNull
    public final String component7() {
        return this.mAodCategory;
    }

    @NotNull
    public final SuperWallpaperInfo copy(float f, float f2, float f3, float f4, float f5, int i, @NotNull String mAodCategory) {
        Intrinsics.checkNotNullParameter(mAodCategory, "mAodCategory");
        return new SuperWallpaperInfo(f, f2, f3, f4, f5, i, mAodCategory);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SuperWallpaperInfo)) {
            return false;
        }
        SuperWallpaperInfo superWallpaperInfo = (SuperWallpaperInfo) obj;
        return Float.compare(this.mClockPositionY, superWallpaperInfo.mClockPositionY) == 0 && Float.compare(this.mClockPositionX, superWallpaperInfo.mClockPositionX) == 0 && Float.compare(this.mDualClockPositionY, superWallpaperInfo.mDualClockPositionY) == 0 && Float.compare(this.mDualClockPositionX, superWallpaperInfo.mDualClockPositionX) == 0 && Float.compare(this.mNotificationPositionY, superWallpaperInfo.mNotificationPositionY) == 0 && this.mSupportChangeWithTime == superWallpaperInfo.mSupportChangeWithTime && Intrinsics.areEqual(this.mAodCategory, superWallpaperInfo.mAodCategory);
    }

    @NotNull
    public final String getMAodCategory() {
        return this.mAodCategory;
    }

    public final float getMClockPositionX() {
        return this.mClockPositionX;
    }

    public final float getMClockPositionY() {
        return this.mClockPositionY;
    }

    public final float getMDualClockPositionX() {
        return this.mDualClockPositionX;
    }

    public final float getMDualClockPositionY() {
        return this.mDualClockPositionY;
    }

    public final float getMNotificationPositionY() {
        return this.mNotificationPositionY;
    }

    public final int getMSupportChangeWithTime() {
        return this.mSupportChangeWithTime;
    }

    public int hashCode() {
        return (((((((((((Float.hashCode(this.mClockPositionY) * 31) + Float.hashCode(this.mClockPositionX)) * 31) + Float.hashCode(this.mDualClockPositionY)) * 31) + Float.hashCode(this.mDualClockPositionX)) * 31) + Float.hashCode(this.mNotificationPositionY)) * 31) + Integer.hashCode(this.mSupportChangeWithTime)) * 31) + this.mAodCategory.hashCode();
    }

    @NotNull
    public String toString() {
        return "SuperWallpaperInfo(mClockPositionY=" + this.mClockPositionY + ", mClockPositionX=" + this.mClockPositionX + ", mDualClockPositionY=" + this.mDualClockPositionY + ", mDualClockPositionX=" + this.mDualClockPositionX + ", mNotificationPositionY=" + this.mNotificationPositionY + ", mSupportChangeWithTime=" + this.mSupportChangeWithTime + ", mAodCategory=" + this.mAodCategory + ")";
    }
}
