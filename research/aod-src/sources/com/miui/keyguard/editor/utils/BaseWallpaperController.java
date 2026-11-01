package com.miui.keyguard.editor.utils;

import android.content.ComponentName;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import androidx.core.graphics.drawable.DrawableKt;
import com.android.thememanager.settings.superwallpaper.activity.data.SuperWallpaperSummaryData;
import com.miui.keyguard.editor.data.bean.MagicType;
import com.miui.keyguard.editor.data.bean.WallpaperWhich;
import com.miui.keyguard.editor.data.template.WallpaperApplyParam;
import com.miui.maml.folme.AnimatedProperty;
import com.miui.miwallpaper.IMiuiWallpaperManagerCallback;
import com.miui.miwallpaper.MiuiWallpaperManager;
import com.miui.miwallpaper.sensor.SensorParams;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.Intrinsics;
import miuix.provider.ExtraSettings;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: BaseWallpaperController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public abstract class BaseWallpaperController {
    public final ConcurrentLinkedQueue fastRegisterListeners;
    public HashMap mSummaryDataMap;
    public volatile MiuiWallpaperManager miuiWallpaperManager;
    public PickWallpaperColorInfo pickWallpaperColorInfo;

    public BaseWallpaperController(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.mSummaryDataMap = new HashMap();
        this.fastRegisterListeners = new ConcurrentLinkedQueue();
        MiuiWallpaperManager.initSync(context, new MiuiWallpaperManager.MiuiWallpaperManagerCallback() { // from class: com.miui.keyguard.editor.utils.BaseWallpaperController$$ExternalSyntheticLambda0
            @Override // com.miui.miwallpaper.MiuiWallpaperManager.MiuiWallpaperManagerCallback
            public final void onMiuiWallpaperInitialized(MiuiWallpaperManager miuiWallpaperManager) {
                BaseWallpaperController._init_$lambda$1(this.f$0, miuiWallpaperManager);
            }
        });
    }

    public static final void _init_$lambda$1(BaseWallpaperController baseWallpaperController, MiuiWallpaperManager miuiWallpaperManager) {
        baseWallpaperController.setMiuiWallpaperManager(miuiWallpaperManager);
        while (!baseWallpaperController.fastRegisterListeners.isEmpty()) {
            Pair pair = (Pair) baseWallpaperController.fastRegisterListeners.poll();
            if (pair != null) {
                baseWallpaperController.getMiuiWallpaperManager().registerWallpaperChangeListener((IMiuiWallpaperManagerCallback) pair.getFirst(), ((Number) pair.getSecond()).intValue());
            }
        }
    }

    public final void applyImageWallpaper(Object wallpaper, int i, WallpaperWhich wallpaperWhich, boolean z, boolean z2, boolean z3, boolean z4, int i2, Pair originWallpaperWH, WallpaperApplyParam param) {
        String simpleName;
        Intrinsics.checkNotNullParameter(wallpaper, "wallpaper");
        Intrinsics.checkNotNullParameter(wallpaperWhich, "wallpaperWhich");
        Intrinsics.checkNotNullParameter(originWallpaperWH, "originWallpaperWH");
        Intrinsics.checkNotNullParameter(param, "param");
        if (wallpaper instanceof Bitmap) {
            Bitmap bitmap = (Bitmap) wallpaper;
            simpleName = bitmap.getWidth() + AnimatedProperty.PROPERTY_NAME_X + bitmap.getHeight() + "/" + bitmap.getConfig();
        } else {
            simpleName = wallpaper.getClass().getSimpleName();
        }
        Log.i("BaseWallpaperController", "[ApplyImage] which=" + wallpaperWhich.getWhich() + ", magicType=" + i + ", bitmap=" + simpleName + ", enableBlur=" + z3 + ", supportMatting=" + z4 + ", enableDoodle=" + i2 + ", fileChanged=" + z + ", isDark=" + z2 + ", originWH=" + originWallpaperWH.getFirst() + AnimatedProperty.PROPERTY_NAME_X + originWallpaperWH.getSecond());
        MiuiWallpaperManager.WallpaperApplierBuilder wallpaperApplierBuilderImage = getWallpaperBuilder().which(wallpaperWhich.getWhich()).image(wallpaper);
        StringBuilder sb = new StringBuilder();
        sb.append("Wallpaper_enableDoodle := ");
        sb.append(i2);
        Log.i("BaseWallpaperController", sb.toString());
        boolean zApply = wallpaperApplierBuilderImage.effectId(i).wallpaperFileChanged(z).supportDark(z2).enableBlur(z3).supportMatting(z4).setDoodleStatus(i2).setOriginBitmapWH(((Number) originWallpaperWH.getFirst()).intValue(), ((Number) originWallpaperWH.getSecond()).intValue()).apply();
        StringBuilder sb2 = new StringBuilder();
        sb2.append("applyImageLockWallpaper: applyResult=");
        sb2.append(zApply);
        Log.w("BaseWallpaperController", sb2.toString());
    }

    public final void applyVideoWallpaper(String str, Object obj, boolean z, int i, WallpaperWhich wallpaperWhich, boolean z2, boolean z3, int i2, Boolean bool) {
        Intrinsics.checkNotNullParameter(wallpaperWhich, "wallpaperWhich");
        if (MagicType.INSTANCE.isDepth(Integer.valueOf(i))) {
            Log.w("WallpaperController", "video wallpaper not support Depth");
        } else {
            getWallpaperBuilder().which(wallpaperWhich.getWhich()).video(obj, str, z, Intrinsics.areEqual(bool, Boolean.TRUE) ? 1 : 0).effectId(i).wallpaperFileChanged(z2).enableBlur(z3).setDoodleStatus(i2).apply();
        }
    }

    public final WallpaperWhich getAllWhich() {
        return DeviceUtil.INSTANCE.isFlip() ? WallpaperWhich.FlipAllWhich.INSTANCE : WallpaperWhich.AllWhich.INSTANCE;
    }

    public final String getAnimType(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        String string = ExtraSettings.Secure.getString(context.getContentResolver(), "sensor_first_anim_type", "");
        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
        return string;
    }

    public final String getDefaultVideoDepthSubjectImpl(WallpaperWhich which) {
        Intrinsics.checkNotNullParameter(which, "which");
        return getMiuiWallpaperManager().getDefaultVideoDepthSubject(which.getWhich());
    }

    public final int getDuration(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return ExtraSettings.Secure.getInt(context.getContentResolver(), "sensor_decelerate_duration", 0);
    }

    public final float getFactor(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return ExtraSettings.Secure.getFloat(context.getContentResolver(), "sensor_decelerate_factor", 0.0f);
    }

    public final int getFrameCount(Context context, boolean z) {
        Intrinsics.checkNotNullParameter(context, "context");
        return ExtraSettings.Secure.getInt(context.getContentResolver(), z ? Wallpaper.Companion.getKEY_SENSOR_TYPE_SMALL_SCREEN_FRAME_COUNT() : Wallpaper.Companion.getKEY_SENSOR_TYPE_ALL_SCREEN_FRAME_COUNT(), -1);
    }

    public final WallpaperWhich getHomeWhich() {
        return DeviceUtil.INSTANCE.isFold() ? WallpaperWhich.FoldHomeWhich.INSTANCE : WallpaperWhich.HomeWhich.INSTANCE;
    }

    public final WallpaperWhich getLockWhich() {
        return DeviceUtil.INSTANCE.isFold() ? WallpaperWhich.FoldLockWhich.INSTANCE : WallpaperWhich.LockWhich.INSTANCE;
    }

    public final MiuiWallpaperManager getMiuiWallpaperManager() {
        MiuiWallpaperManager miuiWallpaperManager = this.miuiWallpaperManager;
        if (miuiWallpaperManager != null) {
            return miuiWallpaperManager;
        }
        Intrinsics.throwUninitializedPropertyAccessException("miuiWallpaperManager");
        return null;
    }

    public final String getMiuiWallpaperType(WallpaperWhich wallpaperWhich) {
        Intrinsics.checkNotNullParameter(wallpaperWhich, "wallpaperWhich");
        String miuiWallpaperType = getMiuiWallpaperManager().getMiuiWallpaperType(wallpaperWhich.getWhich());
        Log.i("BaseWallpaperController", "type:===" + miuiWallpaperType + " which=" + wallpaperWhich.getWhich());
        return Intrinsics.areEqual(miuiWallpaperType, "dark") ? "image" : miuiWallpaperType;
    }

    /* JADX WARN: Code duplicated, block: B:22:0x002e  */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v2 */
    public final Bundle getResultFromProvider(Context context, Uri uri, String str, String str2, Bundle bundle) throws Throwable {
        Throwable th;
        ContentProviderClient contentProviderClientAcquireUnstableContentProviderClient;
        try {
            try {
                contentProviderClientAcquireUnstableContentProviderClient = context.getContentResolver().acquireUnstableContentProviderClient(uri);
                if (contentProviderClientAcquireUnstableContentProviderClient == null) {
                    return null;
                }
                try {
                    Bundle bundleCall = contentProviderClientAcquireUnstableContentProviderClient.call(str, str2, bundle);
                    contentProviderClientAcquireUnstableContentProviderClient.close();
                    return bundleCall;
                } catch (Exception e) {
                    e = e;
                    Log.e("BaseWallpaperController", "getResultFromProvider", e);
                    if (contentProviderClientAcquireUnstableContentProviderClient != null) {
                        contentProviderClientAcquireUnstableContentProviderClient.close();
                    }
                    return null;
                }
            } catch (Throwable th2) {
                th = th2;
                if (context != 0) {
                    context.close();
                }
                throw th;
            }
        } catch (Exception e2) {
            e = e2;
            contentProviderClientAcquireUnstableContentProviderClient = null;
        } catch (Throwable th3) {
            context = 0;
            th = th3;
            if (context != 0) {
                context.close();
            }
            throw th;
        }
    }

    public final int getStiffness(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return ExtraSettings.Secure.getInt(context.getContentResolver(), "sensor_type_stiffness", 10);
    }

    public final SuperWallpaperSummaryData getSummaryData(String str) {
        return (SuperWallpaperSummaryData) this.mSummaryDataMap.get(str);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final SuperWallpaperSummaryData[] getSuperWallpaperSummaryDatas(Context context) throws Throwable {
        Uri uri = Uri.parse("content://com.miui.miwallpaper.resource.wallpaper");
        Intrinsics.checkNotNullExpressionValue(uri, "parse(...)");
        Bundle resultFromProvider = getResultFromProvider(context, uri, "METHOD_GET_SUPER_WALLPAPER_RESOURCE", "ARG_GET_SUPER_WALLPAPER_ALL_RESOURCE", null);
        if (resultFromProvider == null) {
            return null;
        }
        resultFromProvider.setClassLoader(SuperWallpaperSummaryData.class.getClassLoader());
        Parcelable[] parcelableArray = resultFromProvider.getParcelableArray("result_super_wallpaper_data");
        if (parcelableArray == null) {
            return null;
        }
        if (parcelableArray.length == 0) {
            return null;
        }
        SuperWallpaperSummaryData[] superWallpaperSummaryDataArr = new SuperWallpaperSummaryData[parcelableArray.length];
        int length = parcelableArray.length;
        for (int i = 0; i < length; i++) {
            Parcelable parcelable = parcelableArray[i];
            Intrinsics.checkNotNull(parcelable, "null cannot be cast to non-null type com.android.thememanager.settings.superwallpaper.activity.data.SuperWallpaperSummaryData");
            superWallpaperSummaryDataArr[i] = parcelable;
            SuperWallpaperSummaryData superWallpaperSummaryData = superWallpaperSummaryDataArr[i];
            Intrinsics.checkNotNull(superWallpaperSummaryData);
            Log.d("BaseWallpaperController", "get pre super wallpaper data:" + superWallpaperSummaryData.id);
        }
        return superWallpaperSummaryDataArr;
    }

    public final String getVideoWallpaperOriginPathImpl(WallpaperWhich which) {
        Intrinsics.checkNotNullParameter(which, "which");
        return !Intrinsics.areEqual(getMiuiWallpaperManager().getMiuiWallpaperType(which.getWhich()), "video") ? "" : getMiuiWallpaperManager().getMiuiWallpaperPath(null, which.getWhich(), false, false);
    }

    public final MiuiWallpaperManager.WallpaperApplierBuilder getWallpaperBuilder() {
        String templateId;
        Integer signatureAlignment;
        Integer clockInfoStyle;
        Log.i("BaseWallpaperController", "getWallpaperBuilder: pickWallpaperColorInfo = " + this.pickWallpaperColorInfo);
        MiuiWallpaperManager.WallpaperApplierBuilder wallpaperApplierBuilder = new MiuiWallpaperManager.WallpaperApplierBuilder(getMiuiWallpaperManager());
        PickWallpaperColorInfo pickWallpaperColorInfo = this.pickWallpaperColorInfo;
        if (pickWallpaperColorInfo == null || (templateId = pickWallpaperColorInfo.getTemplateId()) == null) {
            templateId = "classic";
        }
        MiuiWallpaperManager.WallpaperApplierBuilder wallpaperApplierBuilderClockStyleType = wallpaperApplierBuilder.clockStyleType(templateId);
        PickWallpaperColorInfo pickWallpaperColorInfo2 = this.pickWallpaperColorInfo;
        int iIntValue = -1;
        int iIntValue2 = (pickWallpaperColorInfo2 == null || (clockInfoStyle = pickWallpaperColorInfo2.getClockInfoStyle()) == null) ? -1 : clockInfoStyle.intValue();
        PickWallpaperColorInfo pickWallpaperColorInfo3 = this.pickWallpaperColorInfo;
        if (pickWallpaperColorInfo3 != null && (signatureAlignment = pickWallpaperColorInfo3.getSignatureAlignment()) != null) {
            iIntValue = signatureAlignment.intValue();
        }
        MiuiWallpaperManager.WallpaperApplierBuilder wallpaperApplierBuilderClockTypeInfo = wallpaperApplierBuilderClockStyleType.clockTypeInfo(new int[]{iIntValue2, iIntValue});
        Intrinsics.checkNotNullExpressionValue(wallpaperApplierBuilderClockTypeInfo, "clockTypeInfo(...)");
        return wallpaperApplierBuilderClockTypeInfo;
    }

    public final String getWallpaperPath(String type, int i, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(type, "type");
        return getMiuiWallpaperManager().getMiuiWallpaperPath(type, i, z, z2);
    }

    public final Bitmap getWallpaperPreview(WallpaperWhich wallpaperWhich) {
        Intrinsics.checkNotNullParameter(wallpaperWhich, "wallpaperWhich");
        return BitmapCreator.INSTANCE.ensureSoftwareBitmap(getMiuiWallpaperManager().getMiuiWallpaperPreview(wallpaperWhich.getWhich()));
    }

    public final boolean isMiuiDefaultWallpaper() {
        return getMiuiWallpaperManager().isMiuiDefaultWallpaper(WallpaperWhich.LockWhich.INSTANCE.getWhich());
    }

    public final boolean isSupportLoop(int i) {
        return !getMiuiWallpaperManager().supportReversePlayback(i) && isVideoLoop(i);
    }

    public final boolean isVideoLoop(int i) {
        return getMiuiWallpaperManager().videoLoop(i);
    }

    public final void registerWallpaperChangedListener(IMiuiWallpaperManagerCallback listener, int i) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        if (this.miuiWallpaperManager != null) {
            getMiuiWallpaperManager().registerWallpaperChangeListener(listener, i);
        } else {
            Log.d("BaseWallpaperController", "registerWallpaperChangedListener: miuiWallpaperManager not ready");
            this.fastRegisterListeners.add(new Pair(listener, Integer.valueOf(i)));
        }
    }

    public boolean setFashionGalleryWallpaper(InputStream wallpaperInputStream, int i, int i2, String str, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(wallpaperInputStream, "wallpaperInputStream");
        return getWallpaperBuilder().which(i2).effectId(i).wallpaperFileChanged(z).gallery(wallpaperInputStream, true, str).enableBlur(z2).apply();
    }

    public final void setFrameCount(Context context, int i, int i2, int i3, int i4, float f, String str) {
        ContentResolver contentResolver = context.getContentResolver();
        Wallpaper.Companion companion = Wallpaper.Companion;
        ExtraSettings.Secure.putInt(contentResolver, companion.getKEY_SENSOR_TYPE_ALL_SCREEN_FRAME_COUNT(), i);
        ExtraSettings.Secure.putInt(context.getContentResolver(), companion.getKEY_SENSOR_TYPE_SMALL_SCREEN_FRAME_COUNT(), i2);
        ExtraSettings.Secure.putInt(context.getContentResolver(), "sensor_type_stiffness", i3);
        ExtraSettings.Secure.putInt(context.getContentResolver(), "sensor_decelerate_duration", i4);
        ExtraSettings.Secure.putFloat(context.getContentResolver(), "sensor_decelerate_factor", f);
        ContentResolver contentResolver2 = context.getContentResolver();
        if (str == null) {
            str = "";
        }
        ExtraSettings.Secure.putString(contentResolver2, "sensor_first_anim_type", str);
    }

    public final void setMiuiSensorWallpaper(Context context, String str, Bitmap bitmap, Bitmap bitmap2, int i, Integer num, Integer num2, Integer num3, Integer num4, Float f, String str2, int i2, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(context, "context");
        int iIntValue = (num == null || num.intValue() < 0) ? 0 : num.intValue();
        int iIntValue2 = (num2 == null || num2.intValue() < 0) ? 0 : num2.intValue();
        int iIntValue3 = (num3 == null || num3.intValue() <= 0) ? 10 : num3.intValue();
        int iIntValue4 = num4 != null ? num4.intValue() : 0;
        float fFloatValue = f != null ? f.floatValue() : 0.0f;
        setFrameCount(context, iIntValue, iIntValue2, iIntValue3, iIntValue4, fFloatValue, str2);
        getWallpaperBuilder().which(i).sensor(bitmap, bitmap2, str).setSensorParams(new SensorParams(str2, iIntValue3, iIntValue, iIntValue2, fFloatValue, iIntValue4)).enableBlur(z2).effectId(i2).setDoodleStatus(1).wallpaperFileChanged(z).apply();
    }

    public final void setMiuiWallpaperManager(MiuiWallpaperManager miuiWallpaperManager) {
        Intrinsics.checkNotNullParameter(miuiWallpaperManager, "<set-?>");
        this.miuiWallpaperManager = miuiWallpaperManager;
    }

    public final void setPickWallpaperColorInfo(PickWallpaperColorInfo pickWallpaperColorInfo) {
        this.pickWallpaperColorInfo = pickWallpaperColorInfo;
    }

    public final void setSuperWallpaper(Context context, String str, boolean z) {
        Drawable drawableLoadDrawable;
        Drawable drawableLoadDrawable2;
        Drawable drawableLoadDrawable3;
        Drawable drawableLoadDrawable4;
        Intrinsics.checkNotNullParameter(context, "context");
        SuperWallpaperSummaryData summaryData = getSummaryData(str);
        if (summaryData == null) {
            updateSummaryDataMap(context);
            summaryData = getSummaryData(str);
        }
        Intrinsics.checkNotNull(summaryData);
        String str2 = summaryData.packageName;
        String id = summaryData.id;
        Intrinsics.checkNotNullExpressionValue(id, "id");
        ComponentName componentName = new ComponentName(str2, str2 + ".superwallpaper." + upperCaseFirstChar(id) + "SuperWallpaper");
        SuperWallpaperSummaryData.SuperWallpaperLandData superWallpaperLandData = summaryData.landData;
        if (superWallpaperLandData != null) {
            Icon icon = superWallpaperLandData.lockscreenSmallPreviewDark;
            drawableLoadDrawable = icon != null ? icon.loadDrawable(context) : null;
            Icon icon2 = summaryData.landData.lockscreenSmallPreview;
            drawableLoadDrawable3 = icon2 != null ? icon2.loadDrawable(context) : null;
            Icon icon3 = summaryData.landData.desktopSmallPreviewDark;
            drawableLoadDrawable4 = icon3 != null ? icon3.loadDrawable(context) : null;
            Icon icon4 = summaryData.landData.desktopSmallPreview;
            drawableLoadDrawable2 = icon4 != null ? icon4.loadDrawable(context) : null;
            Log.d("BaseWallpaperController", "summaryData.landData not null, lockDark = " + drawableLoadDrawable + ", lock = " + drawableLoadDrawable3 + ", desktopDark = " + drawableLoadDrawable4 + ", desktop  " + drawableLoadDrawable2);
        } else {
            Log.e("BaseWallpaperController", "summaryData or summaryData.landData is null, summaryData = " + summaryData);
            drawableLoadDrawable = null;
            drawableLoadDrawable2 = null;
            drawableLoadDrawable3 = null;
            drawableLoadDrawable4 = null;
        }
        if (drawableLoadDrawable == null || drawableLoadDrawable3 == null || drawableLoadDrawable4 == null || drawableLoadDrawable2 == null) {
            Intent intent = new Intent("miui.service.wallpaper.SuperWallpaperService");
            intent.setPackage(componentName.getPackageName());
            List<ResolveInfo> listQueryIntentServices = context.getPackageManager().queryIntentServices(intent, 128);
            if (listQueryIntentServices == null || listQueryIntentServices.size() <= 0) {
                Log.e("BaseWallpaperController", "queryIntentServices is null or list size empty, list = " + listQueryIntentServices);
            } else {
                ServiceInfo serviceInfo = listQueryIntentServices.get(0).serviceInfo;
                if (drawableLoadDrawable4 == null) {
                    drawableLoadDrawable4 = Icon.createWithResource(componentName.getPackageName(), serviceInfo.metaData.getInt("home_small_preview_0_dark")).loadDrawable(context);
                }
                if (drawableLoadDrawable2 == null) {
                    drawableLoadDrawable2 = Icon.createWithResource(componentName.getPackageName(), serviceInfo.metaData.getInt("home_small_preview_0")).loadDrawable(context);
                }
                if (drawableLoadDrawable == null) {
                    drawableLoadDrawable = Icon.createWithResource(componentName.getPackageName(), serviceInfo.metaData.getInt("lockscreen_small_preview_dark")).loadDrawable(context);
                }
                if (drawableLoadDrawable3 == null) {
                    drawableLoadDrawable3 = Icon.createWithResource(componentName.getPackageName(), serviceInfo.metaData.getInt("lockscreen_small_preview")).loadDrawable(context);
                }
            }
        }
        Drawable drawable = drawableLoadDrawable;
        Drawable drawable2 = drawableLoadDrawable2;
        if (drawable2 != null) {
            DrawableKt.toBitmap$default(drawable2, 0, 0, null, 7, null);
        }
        getWallpaperBuilder().superWallpaper(componentName, drawable2 != null ? DrawableKt.toBitmap$default(drawable2, 0, 0, null, 7, null) : null, drawableLoadDrawable4 != null ? DrawableKt.toBitmap$default(drawableLoadDrawable4, 0, 0, null, 7, null) : null, drawableLoadDrawable3 != null ? DrawableKt.toBitmap$default(drawableLoadDrawable3, 0, 0, null, 7, null) : null, drawable != null ? DrawableKt.toBitmap$default(drawable, 0, 0, null, 7, null) : null).enableBlur(z).apply();
    }

    public final void unregisterWallpaperChangedListener(IMiuiWallpaperManagerCallback listener) {
        Intrinsics.checkNotNullParameter(listener, "listener");
        getMiuiWallpaperManager().unRegisterWallpaperChangeListener(listener);
    }

    public final void updateSummaryDataMap(Context context) throws Throwable {
        HashMap map = this.mSummaryDataMap;
        if (map == null || map.size() <= 0) {
            SuperWallpaperSummaryData[] superWallpaperSummaryDatas = getSuperWallpaperSummaryDatas(context);
            this.mSummaryDataMap.clear();
            if (superWallpaperSummaryDatas != null) {
                Iterator it = ArrayIteratorKt.iterator(superWallpaperSummaryDatas);
                while (it.hasNext()) {
                    SuperWallpaperSummaryData superWallpaperSummaryData = (SuperWallpaperSummaryData) it.next();
                    HashMap map2 = this.mSummaryDataMap;
                    Intrinsics.checkNotNull(superWallpaperSummaryData);
                    map2.put(superWallpaperSummaryData.id, superWallpaperSummaryData);
                }
            }
        }
    }

    public final String upperCaseFirstChar(String str) {
        if (TextUtils.isEmpty(str)) {
            return "";
        }
        String strSubstring = str.substring(0, 1);
        Intrinsics.checkNotNullExpressionValue(strSubstring, "substring(...)");
        Locale locale = Locale.getDefault();
        Intrinsics.checkNotNullExpressionValue(locale, "getDefault(...)");
        String upperCase = strSubstring.toUpperCase(locale);
        Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
        String strSubstring2 = str.substring(1);
        Intrinsics.checkNotNullExpressionValue(strSubstring2, "substring(...)");
        return upperCase + strSubstring2;
    }
}
