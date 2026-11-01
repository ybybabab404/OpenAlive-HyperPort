package com.miui.keyguard.editor.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import java.util.LinkedHashMap;
import java.util.Map;
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
public final class Screenshots {
    public final Map hierarchyCheckResult;
    public Bitmap largeLandWallpaper;
    public Bitmap largeLandscape;
    public Bitmap largePortWallpaper;
    public Bitmap largePortrait;
    public Bitmap small;
    public Bitmap smallWallpaper;

    public Screenshots(Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, Bitmap bitmap5, Bitmap bitmap6, Map hierarchyCheckResult) {
        Intrinsics.checkNotNullParameter(hierarchyCheckResult, "hierarchyCheckResult");
        this.small = bitmap;
        this.largePortrait = bitmap2;
        this.smallWallpaper = bitmap3;
        this.largeLandscape = bitmap4;
        this.largePortWallpaper = bitmap5;
        this.largeLandWallpaper = bitmap6;
        this.hierarchyCheckResult = hierarchyCheckResult;
    }

    public /* synthetic */ Screenshots(Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, Bitmap bitmap5, Bitmap bitmap6, Map map, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : bitmap, (i & 2) != 0 ? null : bitmap2, (i & 4) != 0 ? null : bitmap3, (i & 8) != 0 ? null : bitmap4, (i & 16) != 0 ? null : bitmap5, (i & 32) != 0 ? null : bitmap6, (i & 64) != 0 ? new LinkedHashMap() : map);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Screenshots)) {
            return false;
        }
        Screenshots screenshots = (Screenshots) obj;
        return Intrinsics.areEqual(this.small, screenshots.small) && Intrinsics.areEqual(this.largePortrait, screenshots.largePortrait) && Intrinsics.areEqual(this.smallWallpaper, screenshots.smallWallpaper) && Intrinsics.areEqual(this.largeLandscape, screenshots.largeLandscape) && Intrinsics.areEqual(this.largePortWallpaper, screenshots.largePortWallpaper) && Intrinsics.areEqual(this.largeLandWallpaper, screenshots.largeLandWallpaper) && Intrinsics.areEqual(this.hierarchyCheckResult, screenshots.hierarchyCheckResult);
    }

    public final Bitmap getCurrentScreenshot(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        int screenStatus = DeviceUtil.INSTANCE.getScreenStatus(context);
        Log.d("Keyguard-Editor-DeviceScreenshotHelper", "getCurrentScreenshot: screenStatus=" + screenStatus);
        if (screenStatus == 1) {
            return this.small;
        }
        if (screenStatus == 2) {
            return this.largePortrait;
        }
        if (screenStatus == 3) {
            return this.largeLandscape;
        }
        Log.w("Keyguard-Editor-DeviceScreenshotHelper", "getCurrentScreenshot: error screenStatus=" + screenStatus);
        return null;
    }

    public final Bitmap getCurrentWallpaperScreenshot(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        int screenStatus = DeviceUtil.INSTANCE.getScreenStatus(context);
        Log.d("Keyguard-Editor-DeviceScreenshotHelper", "getWallpaperScreenshot: screenStatus=" + screenStatus);
        if (screenStatus == 1) {
            return this.smallWallpaper;
        }
        if (screenStatus == 2) {
            return this.largePortWallpaper;
        }
        if (screenStatus == 3) {
            return this.largeLandWallpaper;
        }
        Log.w("Keyguard-Editor-DeviceScreenshotHelper", "getWallpaperScreenshot: error screenStatus=" + screenStatus);
        return null;
    }

    public final Map getHierarchyCheckResult() {
        return this.hierarchyCheckResult;
    }

    public final Bitmap getLargeLandWallpaper() {
        return this.largeLandWallpaper;
    }

    public final Bitmap getLargeLandscape() {
        return this.largeLandscape;
    }

    public final Bitmap getLargePortWallpaper() {
        return this.largePortWallpaper;
    }

    public final Bitmap getLargePortrait() {
        return this.largePortrait;
    }

    public final boolean getScreenshotAvoidResult(DeviceScreenshotHelper.ScreenshotContext screenshotContext, boolean z) {
        String str;
        if (screenshotContext == null) {
            return true;
        }
        int screenshotScreenStatus = DeviceUtil.INSTANCE.getScreenshotScreenStatus(screenshotContext);
        if (screenshotScreenStatus == 2) {
            str = z ? "hierarchy_check_large_screen_port_dual_clock" : "hierarchy_check_large_screen_port";
        } else if (screenshotScreenStatus != 3) {
            str = z ? "hierarchy_check_small_screen_dual_clock" : "hierarchy_check_small_screen";
        } else {
            str = z ? "hierarchy_check_large_screen_land_dual_clock" : "hierarchy_check_large_screen_land";
        }
        Boolean bool = (Boolean) this.hierarchyCheckResult.get(str);
        if (bool != null) {
            return bool.booleanValue();
        }
        return true;
    }

    public final Bitmap getSmall() {
        return this.small;
    }

    public final Bitmap getSmallWallpaper() {
        return this.smallWallpaper;
    }

    public int hashCode() {
        Bitmap bitmap = this.small;
        int iHashCode = (bitmap == null ? 0 : bitmap.hashCode()) * 31;
        Bitmap bitmap2 = this.largePortrait;
        int iHashCode2 = (iHashCode + (bitmap2 == null ? 0 : bitmap2.hashCode())) * 31;
        Bitmap bitmap3 = this.smallWallpaper;
        int iHashCode3 = (iHashCode2 + (bitmap3 == null ? 0 : bitmap3.hashCode())) * 31;
        Bitmap bitmap4 = this.largeLandscape;
        int iHashCode4 = (iHashCode3 + (bitmap4 == null ? 0 : bitmap4.hashCode())) * 31;
        Bitmap bitmap5 = this.largePortWallpaper;
        int iHashCode5 = (iHashCode4 + (bitmap5 == null ? 0 : bitmap5.hashCode())) * 31;
        Bitmap bitmap6 = this.largeLandWallpaper;
        return ((iHashCode5 + (bitmap6 != null ? bitmap6.hashCode() : 0)) * 31) + this.hierarchyCheckResult.hashCode();
    }

    public final void putScreenshot(Screenshots screenshots) {
        Intrinsics.checkNotNullParameter(screenshots, "screenshots");
        Bitmap bitmap = screenshots.small;
        if (bitmap != null) {
            this.small = bitmap;
        }
        Bitmap bitmap2 = screenshots.smallWallpaper;
        if (bitmap2 != null) {
            this.smallWallpaper = bitmap2;
        }
        Bitmap bitmap3 = screenshots.largePortrait;
        if (bitmap3 != null) {
            this.largePortrait = bitmap3;
        }
        Bitmap bitmap4 = screenshots.largePortWallpaper;
        if (bitmap4 != null) {
            this.largePortWallpaper = bitmap4;
        }
        Bitmap bitmap5 = screenshots.largeLandscape;
        if (bitmap5 != null) {
            this.largeLandscape = bitmap5;
        }
        Bitmap bitmap6 = screenshots.largeLandWallpaper;
        if (bitmap6 != null) {
            this.largeLandWallpaper = bitmap6;
        }
    }

    public final void resetBitmap() {
        this.small = null;
        this.smallWallpaper = null;
        this.largePortrait = null;
        this.largePortWallpaper = null;
        this.largeLandscape = null;
        this.largeLandWallpaper = null;
    }

    public final void setCurrentScreenshot(Context context, Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(context, "context");
        int screenStatus = DeviceUtil.INSTANCE.getScreenStatus(context);
        Log.d("Keyguard-Editor-DeviceScreenshotHelper", "setCurrentScreenshot: screenStatus=" + screenStatus);
        if (screenStatus == 1) {
            this.small = bitmap;
            return;
        }
        if (screenStatus == 2) {
            this.largePortrait = bitmap;
            return;
        }
        if (screenStatus == 3) {
            this.largeLandscape = bitmap;
            return;
        }
        Log.w("Keyguard-Editor-DeviceScreenshotHelper", "setCurrentScreenshot: error screenStatus=" + screenStatus);
    }

    public final void setCurrentWallpaperScreenshot(Context context, Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(context, "context");
        int screenStatus = DeviceUtil.INSTANCE.getScreenStatus(context);
        Log.d("Keyguard-Editor-DeviceScreenshotHelper", "setWallpaperScreenshot: screenStatus=" + screenStatus);
        if (screenStatus == 1) {
            this.smallWallpaper = bitmap;
            return;
        }
        if (screenStatus == 2) {
            this.largePortWallpaper = bitmap;
            return;
        }
        if (screenStatus == 3) {
            this.largeLandWallpaper = bitmap;
            return;
        }
        Log.w("Keyguard-Editor-DeviceScreenshotHelper", "setWallpaperScreenshot: error screenStatus=" + screenStatus);
    }

    public final void setLargeLandWallpaper(Bitmap bitmap) {
        this.largeLandWallpaper = bitmap;
    }

    public final void setLargeLandscape(Bitmap bitmap) {
        this.largeLandscape = bitmap;
    }

    public final void setLargePortWallpaper(Bitmap bitmap) {
        this.largePortWallpaper = bitmap;
    }

    public final void setLargePortrait(Bitmap bitmap) {
        this.largePortrait = bitmap;
    }

    public final void setSmall(Bitmap bitmap) {
        this.small = bitmap;
    }

    public final void setSmallWallpaper(Bitmap bitmap) {
        this.smallWallpaper = bitmap;
    }

    public String toString() {
        return "Screenshots(small=" + this.small + ", largePortrait=" + this.largePortrait + ", smallWallpaper=" + this.smallWallpaper + ", largeLandscape=" + this.largeLandscape + ", largePortWallpaper=" + this.largePortWallpaper + ", largeLandWallpaper=" + this.largeLandWallpaper + ", hierarchyCheckResult=" + this.hierarchyCheckResult + ")";
    }
}
