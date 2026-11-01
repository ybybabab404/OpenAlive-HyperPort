package com.miui.keyguard.editor.data.bean;

import android.graphics.Bitmap;
import com.miui.keyguard.editor.view.AutoBottomSheetKt;
import java.util.Map;
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
/* JADX INFO: compiled from: ScreenshotSource.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ScreenshotSource {

    @Nullable
    private Map<String, Boolean> hierarchyCheckResult;

    @Nullable
    private String historyLargeLandWallpaperScreenshotUri;

    @Nullable
    private String historyLargeLandscapeScreenshotUri;

    @Nullable
    private String historyLargePortWallpaperScreenshotUri;

    @Nullable
    private String historyLargePortraitScreenshotUri;

    @Nullable
    private String historySmallScreenshotUri;

    @Nullable
    private String historySmallWallpaperScreenshotUri;

    @Nullable
    private Bitmap historyWallpaper;

    @Nullable
    private Bitmap largeLandWallpaperScreenshot;

    @Nullable
    private Bitmap largeLandscapeScreenshot;

    @Nullable
    private Bitmap largePortWallpaperScreenshot;

    @Nullable
    private Bitmap largePortraitScreenshot;

    @Nullable
    private String presetLargeLandWallpaperScreenshot;

    @Nullable
    private String presetLargePortWallpaperScreenshot;

    @Nullable
    private String presetSmallWallpaperScreenshot;

    @Nullable
    private Bitmap presetWallpaper;

    @Nullable
    private Bitmap smallScreenshot;

    @Nullable
    private Bitmap smallWallpaperScreenshot;

    public ScreenshotSource() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 262143, null);
    }

    public ScreenshotSource(@Nullable Bitmap bitmap, @Nullable Bitmap bitmap2, @Nullable Bitmap bitmap3, @Nullable Bitmap bitmap4, @Nullable Bitmap bitmap5, @Nullable Bitmap bitmap6, @Nullable Bitmap bitmap7, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable String str7, @Nullable String str8, @Nullable String str9, @Nullable Map<String, Boolean> map, @Nullable Bitmap bitmap8) {
        this.smallScreenshot = bitmap;
        this.largePortraitScreenshot = bitmap2;
        this.largeLandscapeScreenshot = bitmap3;
        this.smallWallpaperScreenshot = bitmap4;
        this.largePortWallpaperScreenshot = bitmap5;
        this.largeLandWallpaperScreenshot = bitmap6;
        this.presetWallpaper = bitmap7;
        this.presetSmallWallpaperScreenshot = str;
        this.presetLargePortWallpaperScreenshot = str2;
        this.presetLargeLandWallpaperScreenshot = str3;
        this.historySmallScreenshotUri = str4;
        this.historySmallWallpaperScreenshotUri = str5;
        this.historyLargePortraitScreenshotUri = str6;
        this.historyLargePortWallpaperScreenshotUri = str7;
        this.historyLargeLandscapeScreenshotUri = str8;
        this.historyLargeLandWallpaperScreenshotUri = str9;
        this.hierarchyCheckResult = map;
        this.historyWallpaper = bitmap8;
    }

    public /* synthetic */ ScreenshotSource(Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, Bitmap bitmap5, Bitmap bitmap6, Bitmap bitmap7, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, Map map, Bitmap bitmap8, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : bitmap, (i & 2) != 0 ? null : bitmap2, (i & 4) != 0 ? null : bitmap3, (i & 8) != 0 ? null : bitmap4, (i & 16) != 0 ? null : bitmap5, (i & 32) != 0 ? null : bitmap6, (i & 64) != 0 ? null : bitmap7, (i & 128) != 0 ? null : str, (i & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0 ? null : str2, (i & 512) != 0 ? null : str3, (i & 1024) != 0 ? null : str4, (i & AutoBottomSheetKt.ALIGN_SCREEN_END) != 0 ? null : str5, (i & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0 ? null : str6, (i & AutoBottomSheetKt.ALIGN_SCREEN_START) != 0 ? null : str7, (i & 16384) != 0 ? null : str8, (i & 32768) != 0 ? null : str9, (i & 65536) != 0 ? null : map, (i & 131072) != 0 ? null : bitmap8);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ ScreenshotSource copy$default(ScreenshotSource screenshotSource, Bitmap bitmap, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, Bitmap bitmap5, Bitmap bitmap6, Bitmap bitmap7, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, Map map, Bitmap bitmap8, int i, Object obj) {
        Bitmap bitmap9;
        Map map2;
        Bitmap bitmap10 = (i & 1) != 0 ? screenshotSource.smallScreenshot : bitmap;
        Bitmap bitmap11 = (i & 2) != 0 ? screenshotSource.largePortraitScreenshot : bitmap2;
        Bitmap bitmap12 = (i & 4) != 0 ? screenshotSource.largeLandscapeScreenshot : bitmap3;
        Bitmap bitmap13 = (i & 8) != 0 ? screenshotSource.smallWallpaperScreenshot : bitmap4;
        Bitmap bitmap14 = (i & 16) != 0 ? screenshotSource.largePortWallpaperScreenshot : bitmap5;
        Bitmap bitmap15 = (i & 32) != 0 ? screenshotSource.largeLandWallpaperScreenshot : bitmap6;
        Bitmap bitmap16 = (i & 64) != 0 ? screenshotSource.presetWallpaper : bitmap7;
        String str10 = (i & 128) != 0 ? screenshotSource.presetSmallWallpaperScreenshot : str;
        String str11 = (i & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) != 0 ? screenshotSource.presetLargePortWallpaperScreenshot : str2;
        String str12 = (i & 512) != 0 ? screenshotSource.presetLargeLandWallpaperScreenshot : str3;
        String str13 = (i & 1024) != 0 ? screenshotSource.historySmallScreenshotUri : str4;
        String str14 = (i & AutoBottomSheetKt.ALIGN_SCREEN_END) != 0 ? screenshotSource.historySmallWallpaperScreenshotUri : str5;
        String str15 = (i & AutoBottomSheetKt.ALIGN_SCREEN_TOP) != 0 ? screenshotSource.historyLargePortraitScreenshotUri : str6;
        String str16 = (i & AutoBottomSheetKt.ALIGN_SCREEN_START) != 0 ? screenshotSource.historyLargePortWallpaperScreenshotUri : str7;
        Bitmap bitmap17 = bitmap10;
        String str17 = (i & 16384) != 0 ? screenshotSource.historyLargeLandscapeScreenshotUri : str8;
        String str18 = (i & 32768) != 0 ? screenshotSource.historyLargeLandWallpaperScreenshotUri : str9;
        Map map3 = (i & 65536) != 0 ? screenshotSource.hierarchyCheckResult : map;
        if ((i & 131072) != 0) {
            map2 = map3;
            bitmap9 = screenshotSource.historyWallpaper;
        } else {
            bitmap9 = bitmap8;
            map2 = map3;
        }
        return screenshotSource.copy(bitmap17, bitmap11, bitmap12, bitmap13, bitmap14, bitmap15, bitmap16, str10, str11, str12, str13, str14, str15, str16, str17, str18, map2, bitmap9);
    }

    @Nullable
    public final Bitmap component1() {
        return this.smallScreenshot;
    }

    @Nullable
    public final String component10() {
        return this.presetLargeLandWallpaperScreenshot;
    }

    @Nullable
    public final String component11() {
        return this.historySmallScreenshotUri;
    }

    @Nullable
    public final String component12() {
        return this.historySmallWallpaperScreenshotUri;
    }

    @Nullable
    public final String component13() {
        return this.historyLargePortraitScreenshotUri;
    }

    @Nullable
    public final String component14() {
        return this.historyLargePortWallpaperScreenshotUri;
    }

    @Nullable
    public final String component15() {
        return this.historyLargeLandscapeScreenshotUri;
    }

    @Nullable
    public final String component16() {
        return this.historyLargeLandWallpaperScreenshotUri;
    }

    @Nullable
    public final Map<String, Boolean> component17() {
        return this.hierarchyCheckResult;
    }

    @Nullable
    public final Bitmap component18() {
        return this.historyWallpaper;
    }

    @Nullable
    public final Bitmap component2() {
        return this.largePortraitScreenshot;
    }

    @Nullable
    public final Bitmap component3() {
        return this.largeLandscapeScreenshot;
    }

    @Nullable
    public final Bitmap component4() {
        return this.smallWallpaperScreenshot;
    }

    @Nullable
    public final Bitmap component5() {
        return this.largePortWallpaperScreenshot;
    }

    @Nullable
    public final Bitmap component6() {
        return this.largeLandWallpaperScreenshot;
    }

    @Nullable
    public final Bitmap component7() {
        return this.presetWallpaper;
    }

    @Nullable
    public final String component8() {
        return this.presetSmallWallpaperScreenshot;
    }

    @Nullable
    public final String component9() {
        return this.presetLargePortWallpaperScreenshot;
    }

    @NotNull
    public final ScreenshotSource copy(@Nullable Bitmap bitmap, @Nullable Bitmap bitmap2, @Nullable Bitmap bitmap3, @Nullable Bitmap bitmap4, @Nullable Bitmap bitmap5, @Nullable Bitmap bitmap6, @Nullable Bitmap bitmap7, @Nullable String str, @Nullable String str2, @Nullable String str3, @Nullable String str4, @Nullable String str5, @Nullable String str6, @Nullable String str7, @Nullable String str8, @Nullable String str9, @Nullable Map<String, Boolean> map, @Nullable Bitmap bitmap8) {
        return new ScreenshotSource(bitmap, bitmap2, bitmap3, bitmap4, bitmap5, bitmap6, bitmap7, str, str2, str3, str4, str5, str6, str7, str8, str9, map, bitmap8);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ScreenshotSource)) {
            return false;
        }
        ScreenshotSource screenshotSource = (ScreenshotSource) obj;
        return Intrinsics.areEqual(this.smallScreenshot, screenshotSource.smallScreenshot) && Intrinsics.areEqual(this.largePortraitScreenshot, screenshotSource.largePortraitScreenshot) && Intrinsics.areEqual(this.largeLandscapeScreenshot, screenshotSource.largeLandscapeScreenshot) && Intrinsics.areEqual(this.smallWallpaperScreenshot, screenshotSource.smallWallpaperScreenshot) && Intrinsics.areEqual(this.largePortWallpaperScreenshot, screenshotSource.largePortWallpaperScreenshot) && Intrinsics.areEqual(this.largeLandWallpaperScreenshot, screenshotSource.largeLandWallpaperScreenshot) && Intrinsics.areEqual(this.presetWallpaper, screenshotSource.presetWallpaper) && Intrinsics.areEqual(this.presetSmallWallpaperScreenshot, screenshotSource.presetSmallWallpaperScreenshot) && Intrinsics.areEqual(this.presetLargePortWallpaperScreenshot, screenshotSource.presetLargePortWallpaperScreenshot) && Intrinsics.areEqual(this.presetLargeLandWallpaperScreenshot, screenshotSource.presetLargeLandWallpaperScreenshot) && Intrinsics.areEqual(this.historySmallScreenshotUri, screenshotSource.historySmallScreenshotUri) && Intrinsics.areEqual(this.historySmallWallpaperScreenshotUri, screenshotSource.historySmallWallpaperScreenshotUri) && Intrinsics.areEqual(this.historyLargePortraitScreenshotUri, screenshotSource.historyLargePortraitScreenshotUri) && Intrinsics.areEqual(this.historyLargePortWallpaperScreenshotUri, screenshotSource.historyLargePortWallpaperScreenshotUri) && Intrinsics.areEqual(this.historyLargeLandscapeScreenshotUri, screenshotSource.historyLargeLandscapeScreenshotUri) && Intrinsics.areEqual(this.historyLargeLandWallpaperScreenshotUri, screenshotSource.historyLargeLandWallpaperScreenshotUri) && Intrinsics.areEqual(this.hierarchyCheckResult, screenshotSource.hierarchyCheckResult) && Intrinsics.areEqual(this.historyWallpaper, screenshotSource.historyWallpaper);
    }

    @Nullable
    public final Map<String, Boolean> getHierarchyCheckResult() {
        return this.hierarchyCheckResult;
    }

    @Nullable
    public final String getHistoryLargeLandWallpaperScreenshotUri() {
        return this.historyLargeLandWallpaperScreenshotUri;
    }

    @Nullable
    public final String getHistoryLargeLandscapeScreenshotUri() {
        return this.historyLargeLandscapeScreenshotUri;
    }

    @Nullable
    public final String getHistoryLargePortWallpaperScreenshotUri() {
        return this.historyLargePortWallpaperScreenshotUri;
    }

    @Nullable
    public final String getHistoryLargePortraitScreenshotUri() {
        return this.historyLargePortraitScreenshotUri;
    }

    @Nullable
    public final String getHistorySmallScreenshotUri() {
        return this.historySmallScreenshotUri;
    }

    @Nullable
    public final String getHistorySmallWallpaperScreenshotUri() {
        return this.historySmallWallpaperScreenshotUri;
    }

    @Nullable
    public final Bitmap getHistoryWallpaper() {
        return this.historyWallpaper;
    }

    @Nullable
    public final Bitmap getLargeLandWallpaperScreenshot() {
        return this.largeLandWallpaperScreenshot;
    }

    @Nullable
    public final Bitmap getLargeLandscapeScreenshot() {
        return this.largeLandscapeScreenshot;
    }

    @Nullable
    public final Bitmap getLargePortWallpaperScreenshot() {
        return this.largePortWallpaperScreenshot;
    }

    @Nullable
    public final Bitmap getLargePortraitScreenshot() {
        return this.largePortraitScreenshot;
    }

    @Nullable
    public final String getPresetLargeLandWallpaperScreenshot() {
        return this.presetLargeLandWallpaperScreenshot;
    }

    @Nullable
    public final String getPresetLargePortWallpaperScreenshot() {
        return this.presetLargePortWallpaperScreenshot;
    }

    @Nullable
    public final String getPresetSmallWallpaperScreenshot() {
        return this.presetSmallWallpaperScreenshot;
    }

    @Nullable
    public final Bitmap getPresetWallpaper() {
        return this.presetWallpaper;
    }

    @Nullable
    public final Bitmap getSmallScreenshot() {
        return this.smallScreenshot;
    }

    @Nullable
    public final Bitmap getSmallWallpaperScreenshot() {
        return this.smallWallpaperScreenshot;
    }

    public int hashCode() {
        Bitmap bitmap = this.smallScreenshot;
        int iHashCode = (bitmap == null ? 0 : bitmap.hashCode()) * 31;
        Bitmap bitmap2 = this.largePortraitScreenshot;
        int iHashCode2 = (iHashCode + (bitmap2 == null ? 0 : bitmap2.hashCode())) * 31;
        Bitmap bitmap3 = this.largeLandscapeScreenshot;
        int iHashCode3 = (iHashCode2 + (bitmap3 == null ? 0 : bitmap3.hashCode())) * 31;
        Bitmap bitmap4 = this.smallWallpaperScreenshot;
        int iHashCode4 = (iHashCode3 + (bitmap4 == null ? 0 : bitmap4.hashCode())) * 31;
        Bitmap bitmap5 = this.largePortWallpaperScreenshot;
        int iHashCode5 = (iHashCode4 + (bitmap5 == null ? 0 : bitmap5.hashCode())) * 31;
        Bitmap bitmap6 = this.largeLandWallpaperScreenshot;
        int iHashCode6 = (iHashCode5 + (bitmap6 == null ? 0 : bitmap6.hashCode())) * 31;
        Bitmap bitmap7 = this.presetWallpaper;
        int iHashCode7 = (iHashCode6 + (bitmap7 == null ? 0 : bitmap7.hashCode())) * 31;
        String str = this.presetSmallWallpaperScreenshot;
        int iHashCode8 = (iHashCode7 + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.presetLargePortWallpaperScreenshot;
        int iHashCode9 = (iHashCode8 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.presetLargeLandWallpaperScreenshot;
        int iHashCode10 = (iHashCode9 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.historySmallScreenshotUri;
        int iHashCode11 = (iHashCode10 + (str4 == null ? 0 : str4.hashCode())) * 31;
        String str5 = this.historySmallWallpaperScreenshotUri;
        int iHashCode12 = (iHashCode11 + (str5 == null ? 0 : str5.hashCode())) * 31;
        String str6 = this.historyLargePortraitScreenshotUri;
        int iHashCode13 = (iHashCode12 + (str6 == null ? 0 : str6.hashCode())) * 31;
        String str7 = this.historyLargePortWallpaperScreenshotUri;
        int iHashCode14 = (iHashCode13 + (str7 == null ? 0 : str7.hashCode())) * 31;
        String str8 = this.historyLargeLandscapeScreenshotUri;
        int iHashCode15 = (iHashCode14 + (str8 == null ? 0 : str8.hashCode())) * 31;
        String str9 = this.historyLargeLandWallpaperScreenshotUri;
        int iHashCode16 = (iHashCode15 + (str9 == null ? 0 : str9.hashCode())) * 31;
        Map<String, Boolean> map = this.hierarchyCheckResult;
        int iHashCode17 = (iHashCode16 + (map == null ? 0 : map.hashCode())) * 31;
        Bitmap bitmap8 = this.historyWallpaper;
        return iHashCode17 + (bitmap8 != null ? bitmap8.hashCode() : 0);
    }

    public final void setHierarchyCheckResult(@Nullable Map<String, Boolean> map) {
        this.hierarchyCheckResult = map;
    }

    public final void setHistoryLargeLandWallpaperScreenshotUri(@Nullable String str) {
        this.historyLargeLandWallpaperScreenshotUri = str;
    }

    public final void setHistoryLargeLandscapeScreenshotUri(@Nullable String str) {
        this.historyLargeLandscapeScreenshotUri = str;
    }

    public final void setHistoryLargePortWallpaperScreenshotUri(@Nullable String str) {
        this.historyLargePortWallpaperScreenshotUri = str;
    }

    public final void setHistoryLargePortraitScreenshotUri(@Nullable String str) {
        this.historyLargePortraitScreenshotUri = str;
    }

    public final void setHistorySmallScreenshotUri(@Nullable String str) {
        this.historySmallScreenshotUri = str;
    }

    public final void setHistorySmallWallpaperScreenshotUri(@Nullable String str) {
        this.historySmallWallpaperScreenshotUri = str;
    }

    public final void setHistoryWallpaper(@Nullable Bitmap bitmap) {
        this.historyWallpaper = bitmap;
    }

    public final void setLargeLandWallpaperScreenshot(@Nullable Bitmap bitmap) {
        this.largeLandWallpaperScreenshot = bitmap;
    }

    public final void setLargeLandscapeScreenshot(@Nullable Bitmap bitmap) {
        this.largeLandscapeScreenshot = bitmap;
    }

    public final void setLargePortWallpaperScreenshot(@Nullable Bitmap bitmap) {
        this.largePortWallpaperScreenshot = bitmap;
    }

    public final void setLargePortraitScreenshot(@Nullable Bitmap bitmap) {
        this.largePortraitScreenshot = bitmap;
    }

    public final void setPresetLargeLandWallpaperScreenshot(@Nullable String str) {
        this.presetLargeLandWallpaperScreenshot = str;
    }

    public final void setPresetLargePortWallpaperScreenshot(@Nullable String str) {
        this.presetLargePortWallpaperScreenshot = str;
    }

    public final void setPresetSmallWallpaperScreenshot(@Nullable String str) {
        this.presetSmallWallpaperScreenshot = str;
    }

    public final void setPresetWallpaper(@Nullable Bitmap bitmap) {
        this.presetWallpaper = bitmap;
    }

    public final void setSmallScreenshot(@Nullable Bitmap bitmap) {
        this.smallScreenshot = bitmap;
    }

    public final void setSmallWallpaperScreenshot(@Nullable Bitmap bitmap) {
        this.smallWallpaperScreenshot = bitmap;
    }

    @NotNull
    public String toString() {
        return "ScreenshotSource(smallScreenshot=" + this.smallScreenshot + ", largePortraitScreenshot=" + this.largePortraitScreenshot + ", largeLandscapeScreenshot=" + this.largeLandscapeScreenshot + ", smallWallpaperScreenshot=" + this.smallWallpaperScreenshot + ", largePortWallpaperScreenshot=" + this.largePortWallpaperScreenshot + ", largeLandWallpaperScreenshot=" + this.largeLandWallpaperScreenshot + ", presetWallpaper=" + this.presetWallpaper + ", presetSmallWallpaperScreenshot=" + this.presetSmallWallpaperScreenshot + ", presetLargePortWallpaperScreenshot=" + this.presetLargePortWallpaperScreenshot + ", presetLargeLandWallpaperScreenshot=" + this.presetLargeLandWallpaperScreenshot + ", historySmallScreenshotUri=" + this.historySmallScreenshotUri + ", historySmallWallpaperScreenshotUri=" + this.historySmallWallpaperScreenshotUri + ", historyLargePortraitScreenshotUri=" + this.historyLargePortraitScreenshotUri + ", historyLargePortWallpaperScreenshotUri=" + this.historyLargePortWallpaperScreenshotUri + ", historyLargeLandscapeScreenshotUri=" + this.historyLargeLandscapeScreenshotUri + ", historyLargeLandWallpaperScreenshotUri=" + this.historyLargeLandWallpaperScreenshotUri + ", hierarchyCheckResult=" + this.hierarchyCheckResult + ", historyWallpaper=" + this.historyWallpaper + ")";
    }
}
