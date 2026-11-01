package com.miui.keyguard.editor.data.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.LargeScreenHierarchyEnable;
import com.miui.keyguard.editor.data.bean.MagicType;
import com.miui.keyguard.editor.data.bean.PresetTemplateConfig;
import com.miui.keyguard.editor.data.bean.ScreenshotSource;
import com.miui.keyguard.editor.data.bean.TemplateHistoryConfig;
import com.miui.keyguard.editor.data.bean.TemplateItemConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.preset.FilterColor;
import com.miui.keyguard.editor.data.template.StatusRecord;
import com.miui.keyguard.editor.data.template.TemplateApi;
import com.miui.keyguard.editor.data.template.TemplateApiImpl;
import com.miui.keyguard.editor.data.template.TemplateChangeRecorder;
import com.miui.keyguard.editor.data.template.TemplateDataUtil;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.DualClockManager;
import com.miui.keyguard.editor.viewmodel.EditorActivityViewModel;
import java.util.HashSet;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
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
/* JADX INFO: compiled from: DataUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class DataUtil {
    public static final DataUtil INSTANCE = new DataUtil();

    public static /* synthetic */ FilterColor[] copyFilterColorArray$default(DataUtil dataUtil, FilterColor[] filterColorArr, Set set, int i, Object obj) {
        if ((i & 2) != 0) {
            set = new HashSet();
        }
        return dataUtil.copyFilterColorArray(filterColorArr, set);
    }

    public final boolean checkHistoryScreenshotUri(String str, String str2, String str3) {
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPhone()) {
            return str == null || str.length() == 0;
        }
        if (deviceUtil.isJ18()) {
            return str == null || str.length() == 0 || str2 == null || str2.length() == 0;
        }
        if (deviceUtil.isFold()) {
            return str == null || str.length() == 0 || str2 == null || str2.length() == 0 || str3 == null || str3.length() == 0;
        }
        if (deviceUtil.isPad()) {
            return str2 == null || str2.length() == 0 || str3 == null || str3.length() == 0;
        }
        return str == null || str.length() == 0;
    }

    public final boolean checkPresetScreenshot(Context context, ScreenshotSource screenshotSource) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(screenshotSource, "screenshotSource");
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPhone()) {
            return screenshotSource.getSmallScreenshot() == null;
        }
        if (deviceUtil.isJ18()) {
            return screenshotSource.getSmallScreenshot() == null || screenshotSource.getLargePortraitScreenshot() == null;
        }
        if (deviceUtil.isFold()) {
            return screenshotSource.getSmallScreenshot() == null || screenshotSource.getLargePortraitScreenshot() == null || screenshotSource.getLargeLandscapeScreenshot() == null;
        }
        if (deviceUtil.isPad()) {
            return screenshotSource.getLargePortraitScreenshot() == null || screenshotSource.getLargeLandscapeScreenshot() == null;
        }
        return screenshotSource.getSmallScreenshot() == null;
    }

    public final FilterColor[] copyFilterColorArray(FilterColor[] source, Set disableColor) {
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(disableColor, "disableColor");
        int length = source.length;
        FilterColor[] filterColorArr = new FilterColor[length];
        for (int i = 0; i < length; i++) {
            FilterColor filterColorCopy$default = FilterColor.copy$default(source[i], 0, 0, 0, 0, 0, false, false, 127, null);
            if (disableColor.contains(Integer.valueOf(filterColorCopy$default.getColorId()))) {
                filterColorCopy$default.setSupportSelect(false);
            }
            Unit unit = Unit.INSTANCE;
            filterColorArr[i] = filterColorCopy$default;
        }
        return filterColorArr;
    }

    public final CommonConfig getHistoryCommonConfig(Context context, TemplateHistoryConfig history) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(history, "history");
        return TemplateDataUtil.getTemplateConfig(context, history.getConfigUri(), false, history.getHideFlag());
    }

    public final Bitmap getHistoryHomeScreenshotImage(Context context, TemplateHistoryConfig history, int i, int i2) {
        String smallWallpaperScreenshotUri;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(history, "history");
        TemplateApi companion = TemplateApi.Companion.getInstance(context);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPhone()) {
            smallWallpaperScreenshotUri = history.getSmallWallpaperScreenshotUri();
        } else if (deviceUtil.isJ18()) {
            smallWallpaperScreenshotUri = deviceUtil.isLargeDisplay(context) ? history.getLargePortWallpaperScreenshotUri() : history.getSmallWallpaperScreenshotUri();
        } else if (deviceUtil.isFold()) {
            if (deviceUtil.isLargeDisplay(context)) {
                smallWallpaperScreenshotUri = deviceUtil.isDisplayLandScape(context) ? history.getLargeLandWallpaperScreenshotUri() : history.getLargePortWallpaperScreenshotUri();
            } else {
                smallWallpaperScreenshotUri = history.getSmallWallpaperScreenshotUri();
            }
        } else if (deviceUtil.isPad()) {
            smallWallpaperScreenshotUri = deviceUtil.isDisplayLandScape(context) ? history.getLargeLandWallpaperScreenshotUri() : history.getLargePortWallpaperScreenshotUri();
        } else {
            smallWallpaperScreenshotUri = history.getSmallWallpaperScreenshotUri();
        }
        return companion.getLosslessTemplateBitmap(smallWallpaperScreenshotUri, false, i, i2);
    }

    public final Bitmap getHistoryScreenshotImage(Context context, TemplateHistoryConfig history, int i, int i2) {
        String smallScreenshotUri;
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(history, "history");
        TemplateApi companion = TemplateApi.Companion.getInstance(context);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPhone()) {
            smallScreenshotUri = history.getSmallScreenshotUri();
        } else if (deviceUtil.isJ18()) {
            smallScreenshotUri = deviceUtil.isLargeDisplay(context) ? history.getLargePortraitScreenshotUri() : history.getSmallScreenshotUri();
        } else if (deviceUtil.isFold()) {
            if (deviceUtil.isLargeDisplay(context)) {
                smallScreenshotUri = deviceUtil.isDisplayLandScape(context) ? history.getLargeLandscapeScreenshotUri() : history.getLargePortraitScreenshotUri();
            } else {
                smallScreenshotUri = history.getSmallScreenshotUri();
            }
        } else if (deviceUtil.isPad()) {
            smallScreenshotUri = deviceUtil.isDisplayLandScape(context) ? history.getLargeLandscapeScreenshotUri() : history.getLargePortraitScreenshotUri();
        } else {
            smallScreenshotUri = history.getSmallScreenshotUri();
        }
        return companion.getLosslessTemplateBitmap(smallScreenshotUri, false, i, i2);
    }

    public final PresetTemplateConfig getPresetTemplateForScreenshot(Context context, PresetTemplateConfig presetTemplate, int i, int i2) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(presetTemplate, "presetTemplate");
        TemplateItemConfig bindItemConfig = presetTemplate.getBindItemConfig();
        if (bindItemConfig == null) {
            Log.w("Keyguard-Editor-DataUtil", "refreshScreenshotDataForPreset: config = null.");
            return null;
        }
        PresetTemplateConfig presetTemplateConfigPreloadPresetTemplate = TemplateApiImpl.Companion.preloadPresetTemplate(context, bindItemConfig, null, i, i2, presetTemplate.getHideFlag());
        bindItemConfig.setReqWidth(i);
        bindItemConfig.setReqHeight(i2);
        presetTemplate.setLoadCompleted(true);
        presetTemplate.setBindItemConfig(bindItemConfig);
        return presetTemplateConfigPreloadPresetTemplate;
    }

    /* JADX WARN: Code duplicated, block: B:23:0x0066  */
    public final void resetWallpaperInfoMagicType(WallpaperInfo wallpaperInfo, WallpaperInfo wallpaperInfo2, Context context) {
        LargeScreenHierarchyEnable largeScreenHierarchyEnable;
        int magicType;
        TemplateChangeRecorder templateChangeRecorder;
        TemplateChangeRecorder templateChangeRecorder2;
        Intrinsics.checkNotNullParameter(context, "context");
        if (wallpaperInfo == null || wallpaperInfo2 == null || (largeScreenHierarchyEnable = wallpaperInfo.getLargeScreenHierarchyEnable()) == null || !MagicType.INSTANCE.isDepth(Integer.valueOf(wallpaperInfo.getMagicType()))) {
            return;
        }
        int magicType2 = wallpaperInfo.getMagicType();
        boolean zIsDualClockAvailable = DualClockManager.isDualClockAvailable(context);
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPhone()) {
            if (zIsDualClockAvailable) {
                if (largeScreenHierarchyEnable.getSmallScreenHierarchyEnableInDualClock()) {
                    magicType = wallpaperInfo.getMagicType();
                    magicType2 = magicType;
                } else {
                    EditorActivityViewModel viewModel = EditorActivityViewModel.Companion.getViewModel(context);
                    if (viewModel != null && (templateChangeRecorder2 = viewModel.getTemplateChangeRecorder()) != null) {
                        templateChangeRecorder2.putLastStatusRecord(1, new StatusRecord(null, Integer.valueOf(wallpaperInfo.getMagicType()), null, 5, null));
                    }
                    magicType2 = 0;
                }
            }
        } else if (deviceUtil.isFold()) {
            boolean zIsLargeDisplay = deviceUtil.isLargeDisplay(context);
            boolean zIsDisplayLandScape = deviceUtil.isDisplayLandScape(context);
            if (zIsLargeDisplay) {
                if (zIsDisplayLandScape) {
                    if (zIsDualClockAvailable) {
                        if (largeScreenHierarchyEnable.getLargeScreenLandHierarchyEnableInDualClock()) {
                            magicType = wallpaperInfo.getMagicType();
                            magicType2 = magicType;
                        } else {
                            magicType2 = 0;
                        }
                    } else if (largeScreenHierarchyEnable.getLargeScreenLandHierarchyEnable()) {
                        magicType = wallpaperInfo.getMagicType();
                        magicType2 = magicType;
                    } else {
                        magicType2 = 0;
                    }
                } else if (zIsDualClockAvailable) {
                    if (largeScreenHierarchyEnable.getLargeScreenPortHierarchyEnableInDualClock()) {
                        magicType = wallpaperInfo.getMagicType();
                        magicType2 = magicType;
                    } else {
                        magicType2 = 0;
                    }
                } else if (largeScreenHierarchyEnable.getLargeScreenPortHierarchyEnable()) {
                    magicType = wallpaperInfo.getMagicType();
                    magicType2 = magicType;
                } else {
                    magicType2 = 0;
                }
            } else if (zIsDualClockAvailable) {
                if (largeScreenHierarchyEnable.getSmallScreenHierarchyEnableInDualClock()) {
                    magicType = wallpaperInfo.getMagicType();
                    magicType2 = magicType;
                } else {
                    EditorActivityViewModel viewModel2 = EditorActivityViewModel.Companion.getViewModel(context);
                    if (viewModel2 != null && (templateChangeRecorder = viewModel2.getTemplateChangeRecorder()) != null) {
                        templateChangeRecorder.putLastStatusRecord(1, new StatusRecord(null, Integer.valueOf(wallpaperInfo.getMagicType()), null, 5, null));
                    }
                    magicType2 = 0;
                }
            } else if (largeScreenHierarchyEnable.getSmallScreenHierarchyEnable()) {
                magicType = wallpaperInfo.getMagicType();
                magicType2 = magicType;
            } else {
                magicType2 = 0;
            }
        }
        Log.i("Keyguard-Editor-DataUtil", "template wallpaper magicType reset to " + magicType2);
        wallpaperInfo2.updateMagicType(magicType2);
    }
}
