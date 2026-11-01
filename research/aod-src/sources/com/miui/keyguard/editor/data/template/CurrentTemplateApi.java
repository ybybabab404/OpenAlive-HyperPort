package com.miui.keyguard.editor.data.template;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.SystemClock;
import android.util.Log;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.data.bean.ExtraInfo;
import com.miui.keyguard.editor.data.bean.HomeConfig;
import com.miui.keyguard.editor.data.bean.MagicType;
import com.miui.keyguard.editor.data.bean.TemplateConfig;
import com.miui.keyguard.editor.data.bean.WallpaperInfo;
import com.miui.keyguard.editor.data.bean.WallpaperWhich;
import com.miui.keyguard.editor.edit.color.picker.ColorPickerCache;
import com.miui.keyguard.editor.edit.wallpaper.FashionGalleryHelper;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.Wallpaper;
import com.miui.keyguard.editor.utils.task.Task;
import com.miui.maml.folme.AnimatedProperty;
import java.util.concurrent.CountDownLatch;
import java.util.function.Consumer;
import java.util.function.Supplier;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.functions.Function0;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: CurrentTemplateApi.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class CurrentTemplateApi {
    public static final Companion Companion = new Companion(null);
    public static boolean isGalleryContentRequesting;
    public final Context applicationContext;
    public final Context context;
    public volatile CommonConfig currentTemplateConfig;
    public WallpaperSource currentWallpaperSource;
    public final Lazy dataApi$delegate;
    public final Lazy filePathGenerator$delegate;

    /* JADX INFO: compiled from: CurrentTemplateApi.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final boolean isGalleryContentRequesting() {
            return CurrentTemplateApi.isGalleryContentRequesting;
        }
    }

    public CurrentTemplateApi(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.dataApi$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.template.CurrentTemplateApi$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CurrentTemplateApi.dataApi_delegate$lambda$0(this.f$0);
            }
        });
        this.applicationContext = context.getApplicationContext();
        this.filePathGenerator$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.template.CurrentTemplateApi$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CurrentTemplateApi.filePathGenerator_delegate$lambda$1(this.f$0);
            }
        });
    }

    public static final TemplateApi dataApi_delegate$lambda$0(CurrentTemplateApi currentTemplateApi) {
        return TemplateApi.Companion.getInstance(currentTemplateApi.context);
    }

    public static final TemplateFilePathGenerator filePathGenerator_delegate$lambda$1(CurrentTemplateApi currentTemplateApi) {
        Context applicationContext = currentTemplateApi.applicationContext;
        Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
        return new TemplateFilePathGenerator(applicationContext);
    }

    public static final Pair requestGalleryContent$lambda$3(CurrentTemplateApi currentTemplateApi, String str, Integer num, String str2) {
        long jUptimeMillis = SystemClock.uptimeMillis();
        try {
            FashionGalleryHelper fashionGalleryHelper = FashionGalleryHelper.INSTANCE;
            Context applicationContext = currentTemplateApi.applicationContext;
            Intrinsics.checkNotNullExpressionValue(applicationContext, "applicationContext");
            String lockGalleryJson = fashionGalleryHelper.getLockGalleryJson(applicationContext, str, num);
            Context applicationContext2 = currentTemplateApi.applicationContext;
            Intrinsics.checkNotNullExpressionValue(applicationContext2, "applicationContext");
            return new Pair(lockGalleryJson, fashionGalleryHelper.getDeskGalleryJson(applicationContext2, str2, num));
        } catch (Exception e) {
            Log.e("Keyguard-Editor-CurrentTemplateApi", "requestGalleryContent", e);
            return null;
        } finally {
            Log.i("Keyguard-Editor-CurrentTemplateApi", "requestGalleryContent used " + (SystemClock.uptimeMillis() - jUptimeMillis) + "ms.");
        }
    }

    public static final void requestGalleryContent$lambda$4(String str, String str2, CurrentTemplateApi currentTemplateApi, Pair pair) {
        HomeConfig homeInfo;
        WallpaperInfo wallpaperInfo;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo2;
        Log.i("Keyguard-Editor-CurrentTemplateApi", "requestGalleryContentCompleted: lockResourceType = " + str + ", deskResourceType = " + str2 + ", \ngalleryContent = " + pair);
        CommonConfig commonConfig = currentTemplateApi.currentTemplateConfig;
        if (commonConfig != null && (lockscreenInfo = commonConfig.getLockscreenInfo()) != null && (wallpaperInfo2 = lockscreenInfo.getWallpaperInfo()) != null) {
            wallpaperInfo2.setGalleryContent(pair != null ? (String) pair.getFirst() : null);
        }
        CommonConfig commonConfig2 = currentTemplateApi.currentTemplateConfig;
        if (commonConfig2 != null && (homeInfo = commonConfig2.getHomeInfo()) != null && (wallpaperInfo = homeInfo.getWallpaperInfo()) != null) {
            wallpaperInfo.setGalleryContent(pair != null ? (String) pair.getSecond() : null);
        }
        isGalleryContentRequesting = false;
    }

    public static /* synthetic */ CommonConfig requireCurrentTemplate$default(CurrentTemplateApi currentTemplateApi, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = "wallpaper_for_screenshot";
        }
        return currentTemplateApi.requireCurrentTemplate(str);
    }

    public static final void requireCurrentTemplate$lambda$2(CurrentTemplateApi currentTemplateApi, String str, CountDownLatch countDownLatch) {
        TemplateConfig lockscreenInfo;
        TemplateApi dataApi = currentTemplateApi.getDataApi();
        CommonConfig commonConfig = currentTemplateApi.currentTemplateConfig;
        dataApi.requireOriginWallpaperForScreenshot((commonConfig == null || (lockscreenInfo = commonConfig.getLockscreenInfo()) == null) ? null : lockscreenInfo.getWallpaperInfo(), str);
        countDownLatch.countDown();
    }

    public final TemplateApi getDataApi() {
        return (TemplateApi) this.dataApi$delegate.getValue();
    }

    public final TemplateFilePathGenerator getFilePathGenerator() {
        return (TemplateFilePathGenerator) this.filePathGenerator$delegate.getValue();
    }

    public final Bitmap getQ18CurrentSensorPreview(boolean z) {
        if (!DeviceUtil.INSTANCE.isQ18()) {
            return null;
        }
        String strGenerateCurrentTemplateFilePath = getFilePathGenerator().generateCurrentTemplateFilePath(z ? TemplateFilePathGenerator.TemplateFileType.SENSOR_WALLPAPER_PREVIEW_SMALL_SCREEN : TemplateFilePathGenerator.TemplateFileType.SENSOR_WALLPAPER_PREVIEW);
        Bitmap losslessTemplateBitmap$default = TemplateApi.DefaultImpls.getLosslessTemplateBitmap$default(getDataApi(), strGenerateCurrentTemplateFilePath, false, 0, 0, 12, null);
        Log.i("Keyguard-Editor-CurrentTemplateApi", "getQ18CurrentSensorPreview: isSmall=" + z + " path=" + strGenerateCurrentTemplateFilePath + " bitmap=" + (losslessTemplateBitmap$default != null ? Integer.valueOf(losslessTemplateBitmap$default.getWidth()) : null) + AnimatedProperty.PROPERTY_NAME_X + (losslessTemplateBitmap$default != null ? Integer.valueOf(losslessTemplateBitmap$default.getHeight()) : null));
        return losslessTemplateBitmap$default;
    }

    public final void requestGalleryContent() {
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        ExtraInfo extraInfo;
        HomeConfig homeInfo;
        WallpaperInfo wallpaperInfo2;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo3;
        Log.i("Keyguard-Editor-CurrentTemplateApi", "start requestGalleryContent.");
        CommonConfig commonConfig = this.currentTemplateConfig;
        final Integer loopType = null;
        final String resourceType = (commonConfig == null || (lockscreenInfo2 = commonConfig.getLockscreenInfo()) == null || (wallpaperInfo3 = lockscreenInfo2.getWallpaperInfo()) == null) ? null : wallpaperInfo3.getResourceType();
        CommonConfig commonConfig2 = this.currentTemplateConfig;
        final String resourceType2 = (commonConfig2 == null || (homeInfo = commonConfig2.getHomeInfo()) == null || (wallpaperInfo2 = homeInfo.getWallpaperInfo()) == null) ? null : wallpaperInfo2.getResourceType();
        CommonConfig commonConfig3 = this.currentTemplateConfig;
        if (commonConfig3 != null && (lockscreenInfo = commonConfig3.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null && (extraInfo = wallpaperInfo.getExtraInfo()) != null) {
            loopType = extraInfo.getLoopType();
        }
        Task.create(new Supplier() { // from class: com.miui.keyguard.editor.data.template.CurrentTemplateApi$$ExternalSyntheticLambda3
            @Override // java.util.function.Supplier
            public final Object get() {
                return CurrentTemplateApi.requestGalleryContent$lambda$3(this.f$0, resourceType, loopType, resourceType2);
            }
        }).post(new Consumer() { // from class: com.miui.keyguard.editor.data.template.CurrentTemplateApi$$ExternalSyntheticLambda4
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                CurrentTemplateApi.requestGalleryContent$lambda$4(resourceType, resourceType2, this, (Pair) obj);
            }
        });
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0086  */
    /* JADX WARN: Code duplicated, block: B:35:0x00a9  */
    public final CommonConfig requireCurrentTemplate(final String target) throws InterruptedException {
        boolean z;
        boolean z2;
        TemplateConfig lockscreenInfo;
        WallpaperInfo wallpaperInfo;
        HomeConfig homeInfo;
        WallpaperInfo wallpaperInfo2;
        Bitmap lockWallpaper;
        HomeConfig homeInfo2;
        TemplateConfig lockscreenInfo2;
        WallpaperInfo wallpaperInfo3;
        CommonConfig commonConfig;
        TemplateConfig lockscreenInfo3;
        WallpaperInfo wallpaperInfo4;
        HomeConfig homeInfo3;
        WallpaperInfo wallpaperInfo5;
        Bitmap sensorSmallWallpaper;
        HomeConfig homeInfo4;
        WallpaperInfo wallpaperInfo6;
        Bitmap sensorLargeWallpaper;
        TemplateConfig lockscreenInfo4;
        WallpaperInfo wallpaperInfo7;
        Bitmap sensorSmallWallpaper2;
        TemplateConfig lockscreenInfo5;
        WallpaperInfo wallpaperInfo8;
        Bitmap sensorLargeWallpaper2;
        HomeConfig homeInfo5;
        WallpaperInfo wallpaperInfo9;
        TemplateConfig lockscreenInfo6;
        WallpaperInfo wallpaperInfo10;
        HomeConfig homeInfo6;
        WallpaperInfo wallpaperInfo11;
        TemplateConfig lockscreenInfo7;
        WallpaperInfo wallpaperInfo12;
        Intrinsics.checkNotNullParameter(target, "target");
        Log.i("Keyguard-Editor-CurrentTemplateApi", " start requireCurrentTemplate target=" + target);
        isGalleryContentRequesting = true;
        int iTryClearLocalInvalidWallpaper = getDataApi().tryClearLocalInvalidWallpaper();
        this.currentTemplateConfig = getDataApi().requireLockScreenInfoBySettings();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Task.run(new Runnable() { // from class: com.miui.keyguard.editor.data.template.CurrentTemplateApi$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CurrentTemplateApi.requireCurrentTemplate$lambda$2(this.f$0, target, countDownLatch);
            }
        });
        this.currentWallpaperSource = getDataApi().requireLockScreenWallpaper(this.currentTemplateConfig, iTryClearLocalInvalidWallpaper);
        countDownLatch.await();
        requestGalleryContent();
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        boolean z3 = deviceUtil.isFold() || deviceUtil.isQ18();
        if (z3) {
            Wallpaper.Companion companion = Wallpaper.Companion;
            CommonConfig commonConfig2 = this.currentTemplateConfig;
            if (companion.isSensorWallpaper((commonConfig2 == null || (lockscreenInfo7 = commonConfig2.getLockscreenInfo()) == null || (wallpaperInfo12 = lockscreenInfo7.getWallpaperInfo()) == null) ? null : wallpaperInfo12.getResourceType())) {
                z = true;
            } else {
                z = false;
            }
        } else {
            z = false;
        }
        if (z3) {
            Wallpaper.Companion companion2 = Wallpaper.Companion;
            CommonConfig commonConfig3 = this.currentTemplateConfig;
            if (companion2.isSensorWallpaper((commonConfig3 == null || (homeInfo6 = commonConfig3.getHomeInfo()) == null || (wallpaperInfo11 = homeInfo6.getWallpaperInfo()) == null) ? null : wallpaperInfo11.getResourceType())) {
                z2 = true;
            } else {
                z2 = false;
            }
        } else {
            z2 = false;
        }
        boolean zIsFold = deviceUtil.isFold();
        boolean zIsQ18 = deviceUtil.isQ18();
        CommonConfig commonConfig4 = this.currentTemplateConfig;
        String resourceType = (commonConfig4 == null || (lockscreenInfo6 = commonConfig4.getLockscreenInfo()) == null || (wallpaperInfo10 = lockscreenInfo6.getWallpaperInfo()) == null) ? null : wallpaperInfo10.getResourceType();
        CommonConfig commonConfig5 = this.currentTemplateConfig;
        Log.i("Keyguard-Editor-CurrentTemplateApi", "requireCurrentTemplate: needFoldSensorPreview=" + z3 + ", isFold=" + zIsFold + ", isQ18=" + zIsQ18 + ", isFoldLockSensor=" + z + ", isFoldHomeSensor=" + z2 + ", lockType=" + resourceType + ", homeType=" + ((commonConfig5 == null || (homeInfo5 = commonConfig5.getHomeInfo()) == null || (wallpaperInfo9 = homeInfo5.getWallpaperInfo()) == null) ? null : wallpaperInfo9.getResourceType()));
        if (z) {
            Bitmap q18CurrentSensorPreview = getQ18CurrentSensorPreview(false);
            Bitmap q18CurrentSensorPreview2 = getQ18CurrentSensorPreview(true);
            CommonConfig commonConfig6 = this.currentTemplateConfig;
            if (commonConfig6 != null && (lockscreenInfo5 = commonConfig6.getLockscreenInfo()) != null && (wallpaperInfo8 = lockscreenInfo5.getWallpaperInfo()) != null) {
                WallpaperSource wallpaperSource = this.currentWallpaperSource;
                if (wallpaperSource != null && (sensorLargeWallpaper2 = wallpaperSource.getSensorLargeWallpaper()) != null) {
                    q18CurrentSensorPreview = sensorLargeWallpaper2;
                }
                wallpaperInfo8.setCurrentWallpaper(q18CurrentSensorPreview);
            }
            CommonConfig commonConfig7 = this.currentTemplateConfig;
            if (commonConfig7 != null && (lockscreenInfo4 = commonConfig7.getLockscreenInfo()) != null && (wallpaperInfo7 = lockscreenInfo4.getWallpaperInfo()) != null) {
                WallpaperSource wallpaperSource2 = this.currentWallpaperSource;
                if (wallpaperSource2 != null && (sensorSmallWallpaper2 = wallpaperSource2.getSensorSmallWallpaper()) != null) {
                    q18CurrentSensorPreview2 = sensorSmallWallpaper2;
                }
                wallpaperInfo7.setCurrentSmallSensorWallpaper(q18CurrentSensorPreview2);
            }
        } else {
            CommonConfig commonConfig8 = this.currentTemplateConfig;
            if (commonConfig8 != null && (lockscreenInfo = commonConfig8.getLockscreenInfo()) != null && (wallpaperInfo = lockscreenInfo.getWallpaperInfo()) != null) {
                WallpaperSource wallpaperSource3 = this.currentWallpaperSource;
                wallpaperInfo.setCurrentWallpaper(wallpaperSource3 != null ? wallpaperSource3.getLockWallpaper() : null);
            }
        }
        if (z2) {
            Bitmap q18CurrentSensorPreview3 = getQ18CurrentSensorPreview(false);
            Bitmap q18CurrentSensorPreview4 = getQ18CurrentSensorPreview(true);
            CommonConfig commonConfig9 = this.currentTemplateConfig;
            if (commonConfig9 != null && (homeInfo4 = commonConfig9.getHomeInfo()) != null && (wallpaperInfo6 = homeInfo4.getWallpaperInfo()) != null) {
                WallpaperSource wallpaperSource4 = this.currentWallpaperSource;
                if (wallpaperSource4 != null && (sensorLargeWallpaper = wallpaperSource4.getSensorLargeWallpaper()) != null) {
                    q18CurrentSensorPreview3 = sensorLargeWallpaper;
                }
                wallpaperInfo6.setCurrentWallpaper(q18CurrentSensorPreview3);
            }
            CommonConfig commonConfig10 = this.currentTemplateConfig;
            if (commonConfig10 != null && (homeInfo3 = commonConfig10.getHomeInfo()) != null && (wallpaperInfo5 = homeInfo3.getWallpaperInfo()) != null) {
                WallpaperSource wallpaperSource5 = this.currentWallpaperSource;
                if (wallpaperSource5 != null && (sensorSmallWallpaper = wallpaperSource5.getSensorSmallWallpaper()) != null) {
                    q18CurrentSensorPreview4 = sensorSmallWallpaper;
                }
                wallpaperInfo5.setCurrentSmallSensorWallpaper(q18CurrentSensorPreview4);
            }
        } else {
            CommonConfig commonConfig11 = this.currentTemplateConfig;
            if (commonConfig11 != null && (homeInfo = commonConfig11.getHomeInfo()) != null && (wallpaperInfo2 = homeInfo.getWallpaperInfo()) != null) {
                WallpaperSource wallpaperSource6 = this.currentWallpaperSource;
                wallpaperInfo2.setCurrentWallpaper(wallpaperSource6 != null ? wallpaperSource6.getHomeWallpaper() : null);
            }
        }
        if (MagicType.INSTANCE.isDepthVideo(Integer.valueOf(WallpaperController.Companion.getInstance(this.context).getCurrentMagicType(WallpaperWhich.LockWhich.INSTANCE.getWhich())))) {
            WallpaperSource wallpaperSource7 = this.currentWallpaperSource;
            if ((wallpaperSource7 != null ? wallpaperSource7.getVideoDeepSubject() : null) != null && (commonConfig = this.currentTemplateConfig) != null && (lockscreenInfo3 = commonConfig.getLockscreenInfo()) != null && (wallpaperInfo4 = lockscreenInfo3.getWallpaperInfo()) != null) {
                WallpaperSource wallpaperSource8 = this.currentWallpaperSource;
                wallpaperInfo4.setSubjectBitmap(wallpaperSource8 != null ? wallpaperSource8.getVideoDeepSubject() : null);
            }
        }
        CommonConfig commonConfig12 = this.currentTemplateConfig;
        if (commonConfig12 != null && (lockscreenInfo2 = commonConfig12.getLockscreenInfo()) != null && (wallpaperInfo3 = lockscreenInfo2.getWallpaperInfo()) != null) {
            WallpaperSource wallpaperSource9 = this.currentWallpaperSource;
            wallpaperInfo3.setCurrentLockWallpaperSource(wallpaperSource9 != null ? wallpaperSource9.getSource() : null);
        }
        CommonConfig commonConfig13 = this.currentTemplateConfig;
        if (commonConfig13 != null && (homeInfo2 = commonConfig13.getHomeInfo()) != null) {
            homeInfo2.setCurrentLockWallpaperSource(LockWallpaperSource.MIUI_WALLPAPER_MANAGER);
        }
        BitmapTempStore companion3 = BitmapTempStore.Companion.getInstance();
        WallpaperSource wallpaperSource10 = this.currentWallpaperSource;
        if (wallpaperSource10 == null || (lockWallpaper = wallpaperSource10.getWallpaperForPickColor()) == null) {
            WallpaperSource wallpaperSource11 = this.currentWallpaperSource;
            lockWallpaper = wallpaperSource11 != null ? wallpaperSource11.getLockWallpaper() : null;
        }
        companion3.putBitmap("wallpaper_for_pick_color", lockWallpaper);
        this.currentWallpaperSource = null;
        ColorPickerCache.INSTANCE.clearMyColorData();
        return this.currentTemplateConfig;
    }
}
