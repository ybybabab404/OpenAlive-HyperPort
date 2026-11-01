package com.miui.keyguard.editor.base;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.ScreenshotSource;
import com.miui.keyguard.editor.data.bean.ScreenshotSourceKt;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.bean.WallpaperPositionInfo;
import com.miui.keyguard.editor.data.template.LockWallpaperSource;
import com.miui.keyguard.editor.edit.wallpaper.CombinedWallpaperView;
import com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer;
import kotlin.Metadata;
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
/* JADX INFO: compiled from: MiuiHomeViewFactory.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class MiuiHomeViewFactory {
    public static final MiuiHomeViewFactory INSTANCE = new MiuiHomeViewFactory();

    public final View create(Context context, CommonConfig commonConfig, ScreenshotSource screenshotSource, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(screenshotSource, "screenshotSource");
        return createMiuiHomeClockView(context, commonConfig, screenshotSource, z);
    }

    public final View createMiuiHomeClockView(Context context, CommonConfig commonConfig, ScreenshotSource screenshotSource, boolean z) {
        ScreenshotSource screenshotSource2;
        Bitmap currentWallpaperScreenshot;
        CommonConfig commonConfigDeepCopy;
        View view;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo;
        Bitmap composedWallpaper;
        FrameLayout frameLayout = new FrameLayout(context);
        if (commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null || (composedWallpaper = lockscreenInfo.getComposedWallpaper()) == null) {
            screenshotSource2 = screenshotSource;
            currentWallpaperScreenshot = ScreenshotSourceKt.getCurrentWallpaperScreenshot(screenshotSource2, context);
        } else {
            currentWallpaperScreenshot = composedWallpaper;
            screenshotSource2 = screenshotSource;
        }
        if (commonConfig == null) {
            Log.d("MiuiHomeViewFactory", "createMiuiHomeClockView: templateConfig is null");
            return frameLayout;
        }
        if (z) {
            commonConfigDeepCopy = commonConfig.deepCopy();
            WallpaperInfo wallpaperInfo2 = commonConfigDeepCopy.getLockscreenInfo().getWallpaperInfo();
            if (wallpaperInfo2 != null) {
                wallpaperInfo2.setCurrentLockWallpaperSource(LockWallpaperSource.WALLPAPER_MANAGER);
            }
            WallpaperInfo wallpaperInfo3 = commonConfigDeepCopy.getLockscreenInfo().getWallpaperInfo();
            if ((wallpaperInfo3 != null ? wallpaperInfo3.getCurrentWallpaper() : null) == null && (wallpaperInfo = commonConfigDeepCopy.getLockscreenInfo().getWallpaperInfo()) != null) {
                Bitmap historyWallpaper = screenshotSource2.getHistoryWallpaper();
                if (historyWallpaper == null && (historyWallpaper = screenshotSource2.getPresetWallpaper()) == null) {
                    historyWallpaper = currentWallpaperScreenshot;
                }
                wallpaperInfo.setCurrentWallpaper(historyWallpaper);
            }
            WallpaperInfo wallpaperInfo4 = commonConfigDeepCopy.getLockscreenInfo().getWallpaperInfo();
            boolean z2 = (wallpaperInfo4 != null ? wallpaperInfo4.getCurrentWallpaper() : null) != null;
            WallpaperInfo wallpaperInfo5 = commonConfig.getLockscreenInfo().getWallpaperInfo();
            WallpaperPositionInfo positionInfo = wallpaperInfo5 != null ? wallpaperInfo5.getPositionInfo() : null;
            WallpaperInfo wallpaperInfo6 = commonConfigDeepCopy.getLockscreenInfo().getWallpaperInfo();
            Log.d("MiuiHomeViewFactory", "createMiuiHomeClockView: currentWallpaper=" + z2 + " originPositionInfo=" + positionInfo + " copyPositionInfo=" + (wallpaperInfo6 != null ? wallpaperInfo6.getPositionInfo() : null));
        } else {
            commonConfigDeepCopy = commonConfig;
        }
        if (currentWallpaperScreenshot == null || z) {
            Log.d("MiuiHomeViewFactory", "createMiuiHomeClockView: create wallpaper layer");
            IWallpaperLayer.Companion companion = IWallpaperLayer.Companion;
            WallpaperInfo wallpaperInfo7 = commonConfigDeepCopy.getLockscreenInfo().getWallpaperInfo();
            Intrinsics.checkNotNull(wallpaperInfo7);
            CombinedWallpaperView combinedWallpaperViewCreateWallpaperView$default = IWallpaperLayer.Companion.createWallpaperView$default(companion, context, wallpaperInfo7, false, false, false, null, null, false, 224, null);
            KeyEvent.Callback callbackWallpaperLayerView = combinedWallpaperViewCreateWallpaperView$default.wallpaperLayerView();
            IWallpaperLayer iWallpaperLayer = callbackWallpaperLayerView instanceof IWallpaperLayer ? (IWallpaperLayer) callbackWallpaperLayerView : null;
            if (iWallpaperLayer != null) {
                iWallpaperLayer.setInTemplate(false);
            }
            combinedWallpaperViewCreateWallpaperView$default.setScaleable(Boolean.FALSE);
            view = combinedWallpaperViewCreateWallpaperView$default;
        } else {
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setImageBitmap(currentWallpaperScreenshot);
            view = imageView;
        }
        view.setId(R.id.kg_home_preview_wallpaper_layer);
        frameLayout.addView(view, new FrameLayout.LayoutParams(-1, -1));
        frameLayout.addView(INSTANCE.initClockView(context, commonConfig.getLockscreenInfo().getClockInfo().isDarkClockArea()), new FrameLayout.LayoutParams(-1, -1));
        return frameLayout;
    }

    public final View initClockView(Context context, boolean z) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        if (z) {
            imageView.setImageResource(R.drawable.kg_miui_home_desktop_black);
            return imageView;
        }
        imageView.setImageResource(R.drawable.kg_miui_home_desktop_white);
        return imageView;
    }
}
