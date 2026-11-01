package com.miui.aod.template;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableKt;
import com.google.gson.Gson;
import com.miui.aod.AODApplication;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.category.CategoryConstants;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.bean.WallpaperWhich;
import com.miui.keyguard.editor.data.template.LockWallpaperSource;
import com.miui.keyguard.editor.data.template.TemplateFilePathGenerator;
import com.miui.keyguard.editor.data.template.WallpaperSource;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.utils.BitmapUtil;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.task.Task;
import java.util.concurrent.CountDownLatch;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: CurrentTemplateApi.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
@SourceDebugExtension
public final class CurrentTemplateApi {
    public final Context context;
    public volatile TemplateConfig currentTemplateConfig;
    public WallpaperSource currentWallpaperSource;
    public boolean enableGetCurrentLockWallpaperFromWM;
    public final TemplateFilePathGenerator filePathGenerator;
    public CountDownLatch latch;
    public final Lazy requireLockScreenInfoFromSettings$delegate;
    public final Lazy requireLockScreenWallpaper$delegate;

    public CurrentTemplateApi(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.filePathGenerator = new TemplateFilePathGenerator(context);
        this.enableGetCurrentLockWallpaperFromWM = true;
        this.requireLockScreenInfoFromSettings$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.aod.template.CurrentTemplateApi$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CurrentTemplateApi.requireLockScreenInfoFromSettings_delegate$lambda$1(this.f$0);
            }
        });
        this.requireLockScreenWallpaper$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.aod.template.CurrentTemplateApi$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CurrentTemplateApi.requireLockScreenWallpaper_delegate$lambda$3(this.f$0);
            }
        });
    }

    public static final Runnable requireLockScreenInfoFromSettings_delegate$lambda$1(final CurrentTemplateApi currentTemplateApi) {
        return new Runnable() { // from class: com.miui.aod.template.CurrentTemplateApi$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                CurrentTemplateApi.requireLockScreenInfoFromSettings_delegate$lambda$1$lambda$0(this.f$0);
            }
        };
    }

    public static final void requireLockScreenInfoFromSettings_delegate$lambda$1$lambda$0(CurrentTemplateApi currentTemplateApi) {
        try {
            currentTemplateApi.currentTemplateConfig = currentTemplateApi.requireTemplateConfig();
        } finally {
            CountDownLatch countDownLatch = currentTemplateApi.latch;
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        }
    }

    public static final Runnable requireLockScreenWallpaper_delegate$lambda$3(final CurrentTemplateApi currentTemplateApi) {
        return new Runnable() { // from class: com.miui.aod.template.CurrentTemplateApi$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                CurrentTemplateApi.requireLockScreenWallpaper_delegate$lambda$3$lambda$2(this.f$0);
            }
        };
    }

    public static final void requireLockScreenWallpaper_delegate$lambda$3$lambda$2(CurrentTemplateApi currentTemplateApi) {
        try {
            currentTemplateApi.currentWallpaperSource = currentTemplateApi.requireLockScreenWallpaper();
        } finally {
            CountDownLatch countDownLatch = currentTemplateApi.latch;
            if (countDownLatch != null) {
                countDownLatch.countDown();
            }
        }
    }

    public final TemplateConfig getCurrentLockScreenInfo() {
        TemplateConfig currentTemplateConfig = getCurrentTemplateConfig();
        if ((currentTemplateConfig != null ? currentTemplateConfig.getWallpaperInfo() : null) != null) {
            WallpaperInfo wallpaperInfo = currentTemplateConfig.getWallpaperInfo();
            if (wallpaperInfo != null) {
                WallpaperController.Companion companion = WallpaperController.Companion;
                Context sInstance = AODApplication.sInstance;
                Intrinsics.checkNotNullExpressionValue(sInstance, "sInstance");
                wallpaperInfo.setMagicType(companion.getInstance(sInstance).getCurrentMagicType(WallpaperWhich.LockWhich.INSTANCE.getWhich()));
            }
            if (wallpaperInfo != null) {
                WallpaperController.Companion companion2 = WallpaperController.Companion;
                Context sInstance2 = AODApplication.sInstance;
                Intrinsics.checkNotNullExpressionValue(sInstance2, "sInstance");
                wallpaperInfo.setEnableBlur(companion2.getInstance(sInstance2).getCurBlurStatus().booleanValue());
            }
        }
        Log.d("CurrentTemplateApi", "getCurrentLockScreenInfo:currentTemplateConfig=" + currentTemplateConfig);
        return currentTemplateConfig;
    }

    public final Bitmap getCurrentLockWallpaper() {
        try {
            if (!isThirdPartyTheme()) {
                return isThirdPartyWallpaper() ? getPlaceholderWallpaper() : getCurrentLockWallpaperPreview();
            }
            Bitmap currentOriginalLockWallpaperInternal = getCurrentOriginalLockWallpaperInternal();
            return currentOriginalLockWallpaperInternal == null ? getCurrentLockWallpaperPreview() : currentOriginalLockWallpaperInternal;
        } catch (Exception unused) {
            return null;
        }
    }

    public final Bitmap getCurrentLockWallpaperPreview() {
        return WallpaperController.Companion.getInstance(this.context).getLockWallpaperPreview(this.context);
    }

    public final Bitmap getCurrentOriginalLockWallpaperInternal() {
        try {
            return BitmapUtil.fromFile$default(BitmapUtil.INSTANCE, this.filePathGenerator.generateCurrentTemplateFilePath(TemplateFilePathGenerator.TemplateFileType.WALLPAPER), 0, 0, null, 14, null);
        } catch (Exception e) {
            Log.e("CurrentTemplateApi", "getCurrentWallpaperSource: " + e);
            return null;
        }
    }

    public final TemplateConfig getCurrentTemplateConfig() {
        String currentTemplateInfo = Utils.getCurrentTemplateInfo();
        if (TextUtils.isEmpty(currentTemplateInfo)) {
            currentTemplateInfo = null;
        }
        if (currentTemplateInfo == null) {
            Log.d("CurrentTemplateApi", "get current lock screen info is null");
            return null;
        }
        Gson gson = new Gson();
        if (!DeviceUtil.INSTANCE.isVersionOs3(this.context)) {
            return (TemplateConfig) gson.fromJson(currentTemplateInfo, TemplateConfig.class);
        }
        CommonConfig commonConfig = (CommonConfig) gson.fromJson(currentTemplateInfo, CommonConfig.class);
        if (commonConfig != null) {
            return commonConfig.getLockscreenInfo();
        }
        return null;
    }

    public final Bitmap getPlaceholderWallpaper() {
        Drawable drawable = ContextCompat.getDrawable(this.context, R.drawable.kg_wallpaper_invalid_placeholder);
        if (drawable != null) {
            return DrawableKt.toBitmapOrNull$default(drawable, 0, 0, null, 7, null);
        }
        return null;
    }

    public final Runnable getRequireLockScreenInfoFromSettings() {
        return (Runnable) this.requireLockScreenInfoFromSettings$delegate.getValue();
    }

    public final Runnable getRequireLockScreenWallpaper() {
        return (Runnable) this.requireLockScreenWallpaper$delegate.getValue();
    }

    public final boolean isThirdPartyTheme() {
        return Intrinsics.areEqual(WallpaperController.Companion.getInstance(this.context).getLockWallpaperType(), CategoryConstants.CATEGORY_MAML);
    }

    public final boolean isThirdPartyWallpaper() {
        return !WallpaperController.Companion.getInstance(this.context).isMiuiWallpaperComponentUsing(WallpaperWhich.LockWhich.INSTANCE.getWhich());
    }

    public final TemplateConfig requireCurrentTemplate() throws InterruptedException {
        WallpaperInfo wallpaperInfo;
        WallpaperInfo wallpaperInfo2;
        WallpaperInfo wallpaperInfo3;
        this.latch = new CountDownLatch(2);
        Task.run(getRequireLockScreenInfoFromSettings());
        Task.run(getRequireLockScreenWallpaper());
        CountDownLatch countDownLatch = this.latch;
        if (countDownLatch != null) {
            countDownLatch.await();
        }
        TemplateConfig templateConfig = this.currentTemplateConfig;
        if (templateConfig != null && (wallpaperInfo3 = templateConfig.getWallpaperInfo()) != null) {
            WallpaperSource wallpaperSource = this.currentWallpaperSource;
            wallpaperInfo3.setCurrentWallpaper(wallpaperSource != null ? wallpaperSource.getLockWallpaper() : null);
        }
        TemplateConfig templateConfig2 = this.currentTemplateConfig;
        if (templateConfig2 != null && (wallpaperInfo2 = templateConfig2.getWallpaperInfo()) != null) {
            WallpaperSource wallpaperSource2 = this.currentWallpaperSource;
            wallpaperInfo2.setCurrentSmallSensorWallpaper(wallpaperSource2 != null ? wallpaperSource2.getLockWallpaper() : null);
        }
        TemplateConfig templateConfig3 = this.currentTemplateConfig;
        if (templateConfig3 != null && (wallpaperInfo = templateConfig3.getWallpaperInfo()) != null) {
            WallpaperSource wallpaperSource3 = this.currentWallpaperSource;
            wallpaperInfo.setCurrentLockWallpaperSource(wallpaperSource3 != null ? wallpaperSource3.getSource() : null);
        }
        this.currentWallpaperSource = null;
        Log.d("CurrentTemplateApi", "requireCurrentTemplate:currentTemplateConfig=" + this.currentTemplateConfig);
        return this.currentTemplateConfig;
    }

    public final WallpaperSource requireLockScreenWallpaper() {
        try {
            Bitmap currentLockWallpaper = getCurrentLockWallpaper();
            return new WallpaperSource(currentLockWallpaper, null, currentLockWallpaper, LockWallpaperSource.MIUI_WALLPAPER_MANAGER);
        } catch (Exception e) {
            Log.e("CurrentTemplateApi", "requireLockScreenWallpaper failed: " + e);
            return null;
        }
    }

    /* JADX WARN: Code duplicated, block: B:10:0x0016  */
    public final TemplateConfig requireTemplateConfig() {
        String cropSubject;
        String str;
        WallpaperInfo wallpaperInfo;
        TemplateConfig currentLockScreenInfo = getCurrentLockScreenInfo();
        if (currentLockScreenInfo != null) {
            try {
                WallpaperInfo wallpaperInfo2 = currentLockScreenInfo.getWallpaperInfo();
                if (wallpaperInfo2 != null) {
                    cropSubject = wallpaperInfo2.getCropSubject();
                } else {
                    cropSubject = null;
                }
                str = cropSubject;
                Log.d("CurrentTemplateApi", "path=" + str);
                if (currentLockScreenInfo != null && (wallpaperInfo = currentLockScreenInfo.getWallpaperInfo()) != null) {
                    wallpaperInfo.setSubjectBitmap(BitmapUtil.fromFile$default(BitmapUtil.INSTANCE, str, -1, -1, null, 8, null));
                    return currentLockScreenInfo;
                }
            } catch (Exception e) {
                Log.d("CurrentTemplateApi", "copy subject: failed=" + e.getMessage());
            }
        } else {
            cropSubject = null;
            str = cropSubject;
            Log.d("CurrentTemplateApi", "path=" + str);
            if (currentLockScreenInfo != null) {
                wallpaperInfo.setSubjectBitmap(BitmapUtil.fromFile$default(BitmapUtil.INSTANCE, str, -1, -1, null, 8, null));
                return currentLockScreenInfo;
            }
        }
        return currentLockScreenInfo;
    }
}
