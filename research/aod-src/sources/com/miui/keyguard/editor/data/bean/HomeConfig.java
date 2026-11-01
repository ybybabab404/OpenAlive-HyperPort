package com.miui.keyguard.editor.data.bean;

import android.graphics.Bitmap;
import com.miui.keyguard.editor.data.template.LockWallpaperSource;
import kotlin.Metadata;
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
public final class HomeConfig {

    @Nullable
    private transient LockWallpaperSource currentLockWallpaperSource;

    @Nullable
    private transient Bitmap darkenWallpaper;
    private int followStatus;
    private int hideParam;
    private transient boolean isThirdPartyWallpaper;

    @Nullable
    private WallpaperInfo wallpaperInfo;

    @Nullable
    private WallpaperInfo wallpaperInfoForVideo;

    public HomeConfig(int i, @Nullable WallpaperInfo wallpaperInfo, @Nullable WallpaperInfo wallpaperInfo2, int i2) {
        this.followStatus = i;
        this.wallpaperInfo = wallpaperInfo;
        this.wallpaperInfoForVideo = wallpaperInfo2;
        this.hideParam = i2;
    }

    public static /* synthetic */ HomeConfig copy$default(HomeConfig homeConfig, int i, WallpaperInfo wallpaperInfo, WallpaperInfo wallpaperInfo2, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = homeConfig.followStatus;
        }
        if ((i3 & 2) != 0) {
            wallpaperInfo = homeConfig.wallpaperInfo;
        }
        if ((i3 & 4) != 0) {
            wallpaperInfo2 = homeConfig.wallpaperInfoForVideo;
        }
        if ((i3 & 8) != 0) {
            i2 = homeConfig.hideParam;
        }
        return homeConfig.copy(i, wallpaperInfo, wallpaperInfo2, i2);
    }

    public final int component1() {
        return this.followStatus;
    }

    @Nullable
    public final WallpaperInfo component2() {
        return this.wallpaperInfo;
    }

    @Nullable
    public final WallpaperInfo component3() {
        return this.wallpaperInfoForVideo;
    }

    public final int component4() {
        return this.hideParam;
    }

    @NotNull
    public final HomeConfig copy(int i, @Nullable WallpaperInfo wallpaperInfo, @Nullable WallpaperInfo wallpaperInfo2, int i2) {
        return new HomeConfig(i, wallpaperInfo, wallpaperInfo2, i2);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof HomeConfig)) {
            return false;
        }
        HomeConfig homeConfig = (HomeConfig) obj;
        return this.followStatus == homeConfig.followStatus && Intrinsics.areEqual(this.wallpaperInfo, homeConfig.wallpaperInfo) && Intrinsics.areEqual(this.wallpaperInfoForVideo, homeConfig.wallpaperInfoForVideo) && this.hideParam == homeConfig.hideParam;
    }

    @Nullable
    public final LockWallpaperSource getCurrentLockWallpaperSource() {
        return this.currentLockWallpaperSource;
    }

    @Nullable
    public final Bitmap getDarkenWallpaper() {
        return this.darkenWallpaper;
    }

    public final int getFollowStatus() {
        return this.followStatus;
    }

    public final int getHideParam() {
        return this.hideParam;
    }

    @Nullable
    public final WallpaperInfo getWallpaperInfo() {
        return this.wallpaperInfo;
    }

    @Nullable
    public final WallpaperInfo getWallpaperInfoForVideo() {
        return this.wallpaperInfoForVideo;
    }

    public int hashCode() {
        int iHashCode = Integer.hashCode(this.followStatus) * 31;
        WallpaperInfo wallpaperInfo = this.wallpaperInfo;
        int iHashCode2 = (iHashCode + (wallpaperInfo == null ? 0 : wallpaperInfo.hashCode())) * 31;
        WallpaperInfo wallpaperInfo2 = this.wallpaperInfoForVideo;
        return ((iHashCode2 + (wallpaperInfo2 != null ? wallpaperInfo2.hashCode() : 0)) * 31) + Integer.hashCode(this.hideParam);
    }

    public final boolean isThirdPartyWallpaper() {
        return this.isThirdPartyWallpaper;
    }

    public final void setCurrentLockWallpaperSource(@Nullable LockWallpaperSource lockWallpaperSource) {
        this.currentLockWallpaperSource = lockWallpaperSource;
    }

    public final void setDarkenWallpaper(@Nullable Bitmap bitmap) {
        this.darkenWallpaper = bitmap;
    }

    public final void setFollowStatus(int i) {
        this.followStatus = i;
    }

    public final void setHideParam(int i) {
        this.hideParam = i;
    }

    public final void setThirdPartyWallpaper(boolean z) {
        this.isThirdPartyWallpaper = z;
    }

    public final void setWallpaperInfo(@Nullable WallpaperInfo wallpaperInfo) {
        this.wallpaperInfo = wallpaperInfo;
    }

    public final void setWallpaperInfoForVideo(@Nullable WallpaperInfo wallpaperInfo) {
        this.wallpaperInfoForVideo = wallpaperInfo;
    }

    @NotNull
    public String toString() {
        return "HomeConfig(followStatus=" + this.followStatus + ", wallpaperInfo=" + this.wallpaperInfo + ", wallpaperInfoForVideo=" + this.wallpaperInfoForVideo + ", hideParam=" + this.hideParam + ")";
    }
}
