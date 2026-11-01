package com.miui.keyguard.editor.data.bean;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.miui.keyguard.editor.utils.BitmapUtil;
import com.miui.keyguard.editor.utils.DeviceUtil;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
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
@SourceDebugExtension
public final class ScreenshotSourceKt {

    @NotNull
    private static final String DEVICE_TYPE_FOLD = "fold";

    @NotNull
    private static final String DEVICE_TYPE_PHONE = "phone";

    @NotNull
    private static final String TAG = "ScreenshotSource";

    @NotNull
    private static final Lazy presetAvoidResult$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.bean.ScreenshotSourceKt$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return ScreenshotSourceKt.presetAvoidResult_delegate$lambda$2();
        }
    });

    @NotNull
    private static final Lazy defaultNotSupportTypes$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.bean.ScreenshotSourceKt$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return ScreenshotSourceKt.defaultNotSupportTypes_delegate$lambda$3();
        }
    });

    @Nullable
    public static final LargeScreenHierarchyEnable createScreenHierarchyEnable(@NotNull ScreenshotSource screenshotSource) {
        Intrinsics.checkNotNullParameter(screenshotSource, "<this>");
        Map<String, Boolean> hierarchyCheckResult = screenshotSource.getHierarchyCheckResult();
        if (hierarchyCheckResult == null || hierarchyCheckResult.isEmpty()) {
            return null;
        }
        Boolean bool = hierarchyCheckResult.get("hierarchy_check_small_screen");
        boolean zBooleanValue = bool != null ? bool.booleanValue() : true;
        Boolean bool2 = hierarchyCheckResult.get("hierarchy_check_small_screen_with_notification");
        boolean zBooleanValue2 = bool2 != null ? bool2.booleanValue() : true;
        Boolean bool3 = hierarchyCheckResult.get("hierarchy_check_large_screen_port");
        boolean zBooleanValue3 = bool3 != null ? bool3.booleanValue() : true;
        Boolean bool4 = hierarchyCheckResult.get("hierarchy_check_large_screen_port_with_notification");
        boolean zBooleanValue4 = bool4 != null ? bool4.booleanValue() : zBooleanValue3;
        Boolean bool5 = hierarchyCheckResult.get("hierarchy_check_large_screen_land");
        boolean zBooleanValue5 = bool5 != null ? bool5.booleanValue() : true;
        Boolean bool6 = hierarchyCheckResult.get("hierarchy_check_large_screen_land_with_notification");
        return new LargeScreenHierarchyEnable(zBooleanValue, false, zBooleanValue2, zBooleanValue3, false, zBooleanValue4, zBooleanValue5, false, bool6 != null ? bool6.booleanValue() : zBooleanValue5);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Set defaultNotSupportTypes_delegate$lambda$3() {
        return SetsKt.mutableSetOf("hierarchy_check_small_screen_dual_clock", "hierarchy_check_large_screen_port_dual_clock", "hierarchy_check_large_screen_land_dual_clock");
    }

    @Nullable
    public static final Bitmap getCurrentScreenshot(@NotNull ScreenshotSource screenshotSource, @NotNull Context context) {
        Intrinsics.checkNotNullParameter(screenshotSource, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        int screenStatus = DeviceUtil.INSTANCE.getScreenStatus(context);
        Log.d(TAG, "getCurrentScreenshot: screenStatus=" + screenStatus);
        if (screenStatus == 1) {
            return screenshotSource.getSmallScreenshot();
        }
        if (screenStatus == 2) {
            return screenshotSource.getLargePortraitScreenshot();
        }
        if (screenStatus == 3) {
            return screenshotSource.getLargeLandscapeScreenshot();
        }
        Log.w(TAG, "getCurrentScreenshot: error screenStatus=" + screenStatus);
        return null;
    }

    @Nullable
    public static final Bitmap getCurrentWallpaperScreenshot(@NotNull ScreenshotSource screenshotSource, @NotNull Context context) {
        Intrinsics.checkNotNullParameter(screenshotSource, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        int screenStatus = DeviceUtil.INSTANCE.getScreenStatus(context);
        Log.d(TAG, "getCurrentWallpaperScreenshot: screenStatus=" + screenStatus);
        if (screenStatus == 1) {
            return screenshotSource.getSmallWallpaperScreenshot();
        }
        if (screenStatus == 2) {
            return screenshotSource.getLargePortWallpaperScreenshot();
        }
        if (screenStatus == 3) {
            return screenshotSource.getLargeLandWallpaperScreenshot();
        }
        Log.w(TAG, "getCurrentWallpaperScreenshot: error screenStatus=" + screenStatus);
        return null;
    }

    @NotNull
    public static final Set<String> getDefaultNotSupportTypes() {
        return (Set) defaultNotSupportTypes$delegate.getValue();
    }

    @NotNull
    public static final Map<String, Boolean> getPresetAvoidResult() {
        return (Map) presetAvoidResult$delegate.getValue();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Map presetAvoidResult_delegate$lambda$2() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Iterator<T> it = getDefaultNotSupportTypes().iterator();
        while (it.hasNext()) {
            linkedHashMap.put((String) it.next(), Boolean.FALSE);
        }
        return linkedHashMap;
    }

    public static final void releaseAllScreenshot(@NotNull ScreenshotSource screenshotSource) {
        Intrinsics.checkNotNullParameter(screenshotSource, "<this>");
        BitmapUtil bitmapUtil = BitmapUtil.INSTANCE;
        bitmapUtil.recycleBitmap(screenshotSource.getSmallScreenshot(), "smallScreenshot");
        screenshotSource.setSmallScreenshot(null);
        bitmapUtil.recycleBitmap(screenshotSource.getLargePortraitScreenshot(), "largePortraitScreenshot");
        screenshotSource.setLargePortraitScreenshot(null);
        bitmapUtil.recycleBitmap(screenshotSource.getLargeLandscapeScreenshot(), "largeLandscapeScreenshot");
        screenshotSource.setLargeLandscapeScreenshot(null);
        bitmapUtil.recycleBitmap(screenshotSource.getSmallWallpaperScreenshot(), "smallWallpaperScreenshot");
        screenshotSource.setSmallWallpaperScreenshot(null);
        bitmapUtil.recycleBitmap(screenshotSource.getLargePortWallpaperScreenshot(), "largePortWallpaperScreenshot");
        screenshotSource.setLargePortWallpaperScreenshot(null);
        bitmapUtil.recycleBitmap(screenshotSource.getLargeLandWallpaperScreenshot(), "largeLandWallpaperScreenshot");
        screenshotSource.setLargeLandWallpaperScreenshot(null);
        screenshotSource.setHistorySmallScreenshotUri(null);
        screenshotSource.setHistoryLargePortraitScreenshotUri(null);
        screenshotSource.setHistoryLargeLandscapeScreenshotUri(null);
        screenshotSource.setHierarchyCheckResult(null);
        screenshotSource.setHistoryWallpaper(null);
    }

    public static final void setCurrentScreenshot(@NotNull ScreenshotSource screenshotSource, @NotNull Context context, @Nullable Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(screenshotSource, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        int screenStatus = DeviceUtil.INSTANCE.getScreenStatus(context);
        Log.d(TAG, "setCurrentScreenshot: screenStatus=" + screenStatus);
        if (screenStatus == 1) {
            screenshotSource.setSmallScreenshot(bitmap);
            return;
        }
        if (screenStatus == 2) {
            screenshotSource.setLargePortraitScreenshot(bitmap);
            return;
        }
        if (screenStatus == 3) {
            screenshotSource.setLargeLandscapeScreenshot(bitmap);
            return;
        }
        Log.w(TAG, "setCurrentScreenshot: error screenStatus=" + screenStatus);
    }

    public static final void setCurrentWallpaperScreenshot(@NotNull ScreenshotSource screenshotSource, @NotNull Context context, @Nullable Bitmap bitmap) {
        Intrinsics.checkNotNullParameter(screenshotSource, "<this>");
        Intrinsics.checkNotNullParameter(context, "context");
        int screenStatus = DeviceUtil.INSTANCE.getScreenStatus(context);
        Log.d(TAG, "setCurrentWallpaperScreenshot: screenStatus=" + screenStatus);
        if (screenStatus == 1) {
            screenshotSource.setSmallWallpaperScreenshot(bitmap);
            return;
        }
        if (screenStatus == 2) {
            screenshotSource.setLargePortWallpaperScreenshot(bitmap);
            return;
        }
        if (screenStatus == 3) {
            screenshotSource.setLargeLandWallpaperScreenshot(bitmap);
            return;
        }
        Log.w(TAG, "getCurrentWallpaperScreenshot: error screenStatus=" + screenStatus);
    }

    public static final void setHistoryScreenshot(@NotNull ScreenshotSource screenshotSource, int i, @Nullable Bitmap bitmap, @Nullable Bitmap bitmap2, @Nullable Bitmap bitmap3) {
        Intrinsics.checkNotNullParameter(screenshotSource, "<this>");
        Log.d(TAG, "setHistoryScreenshot: screenStatus=" + i + " templateScreenshot=" + (bitmap == null) + " wallpaperScreenshot=" + (bitmap2 == null) + " originWallpaper=" + (bitmap3 == null));
        screenshotSource.setHistoryWallpaper(bitmap3);
        if (i == 1) {
            screenshotSource.setSmallScreenshot(bitmap);
            screenshotSource.setSmallWallpaperScreenshot(bitmap2);
            return;
        }
        if (i == 2) {
            screenshotSource.setLargePortraitScreenshot(bitmap);
            screenshotSource.setLargePortWallpaperScreenshot(bitmap2);
        } else if (i == 3) {
            screenshotSource.setLargeLandscapeScreenshot(bitmap);
            screenshotSource.setLargeLandWallpaperScreenshot(bitmap2);
        } else {
            Log.w(TAG, "setHistoryScreenshot: error screenStatus=" + i);
        }
    }
}
