package com.miui.keyguard.editor.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;
import com.miui.aod.category.CategoryConstants;
import com.miui.keyguard.editor.data.bean.MagicType;
import java.util.HashMap;
import java.util.Set;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

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
/* JADX INFO: compiled from: Constants.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class Wallpaper {
    public static Boolean isVideoSupportGlassFilter;
    public static final Companion Companion = new Companion(null);
    public static final HashMap SuperWallpaperPackageHashMap = MapsKt.hashMapOf(TuplesKt.to("com.miui.miwallpaper.snowmountain", "snowmountain"), TuplesKt.to("com.miui.miwallpaper.geometry", "geometry"), TuplesKt.to("com.miui.miwallpaper.saturn", "saturn"), TuplesKt.to("com.miui.miwallpaper.earth", "earth"), TuplesKt.to("com.miui.miwallpaper.mars", "mars"));
    public static final String KEY_SENSOR_TYPE_SMALL_SCREEN_FRAME_COUNT = "sensor_type_small_screen_frame_count";
    public static final String KEY_SENSOR_TYPE_ALL_SCREEN_FRAME_COUNT = "sensor_type_all_screen_frame_count";
    public static final Set staticWallpaperTypes = SetsKt.setOf((Object[]) new String[]{"image", CategoryConstants.CATEGORY_MAML, "gallery"});

    /* JADX INFO: compiled from: Constants.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final String getKEY_SENSOR_TYPE_ALL_SCREEN_FRAME_COUNT() {
            return Wallpaper.KEY_SENSOR_TYPE_ALL_SCREEN_FRAME_COUNT;
        }

        public final String getKEY_SENSOR_TYPE_SMALL_SCREEN_FRAME_COUNT() {
            return Wallpaper.KEY_SENSOR_TYPE_SMALL_SCREEN_FRAME_COUNT;
        }

        public final HashMap getSuperWallpaperPackageHashMap() {
            return Wallpaper.SuperWallpaperPackageHashMap;
        }

        public final boolean isLivePhotoResource(Integer num) {
            if (num != null && num.intValue() == 2) {
                return true;
            }
            return num != null && num.intValue() == 3;
        }

        public final boolean isNeedDark(String str, String str2) {
            return isSupportDarkWallpaperType(str2) && !isSystemWallpaper(str);
        }

        public final boolean isNormalLivePhotoResource(Integer num) {
            return num != null && num.intValue() == 2;
        }

        public final boolean isSensorWallpaper(String str) {
            return Intrinsics.areEqual(str, "sensor");
        }

        public final boolean isSpecialWallpaperType(String str) {
            return Intrinsics.areEqual(str, CategoryConstants.CATEGORY_SUPER_WALLPAPER) || Intrinsics.areEqual(str, "linkage_video") || Intrinsics.areEqual(str, "sensor");
        }

        public final boolean isStaticWallpaper(String type) {
            Intrinsics.checkNotNullParameter(type, "type");
            return Wallpaper.staticWallpaperTypes.contains(type);
        }

        public final boolean isSuperSavePowerWallpaper(String str) {
            return Intrinsics.areEqual(str, "super_save_power");
        }

        public final boolean isSupportBlurWallpaperType(String str, int i) {
            if (Intrinsics.areEqual(str, "image") || Intrinsics.areEqual(str, "gallery")) {
                return true;
            }
            if (MagicType.INSTANCE.isOrigin(i)) {
                return Intrinsics.areEqual(str, "video") || Intrinsics.areEqual(str, "sensor");
            }
            return false;
        }

        public final boolean isSupportDarkWallpaperType(String str) {
            return Intrinsics.areEqual(str, "image") || Intrinsics.areEqual(str, "gallery");
        }

        public final boolean isSystemWallpaper(String str) {
            if (str != null) {
                return StringsKt.startsWith$default(str, "/system", false, 2, null) || StringsKt.startsWith$default(str, "/product", false, 2, null);
            }
            return false;
        }

        public final boolean isVideoSupportGlassFilter(Context context) {
            Boolean boolValueOf;
            Intrinsics.checkNotNullParameter(context, "context");
            if (Wallpaper.isVideoSupportGlassFilter == null) {
                try {
                    ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo("com.miui.miwallpaper", 128);
                    Intrinsics.checkNotNullExpressionValue(applicationInfo, "getApplicationInfo(...)");
                    boolean z = applicationInfo.metaData.getBoolean("com_miui_miwallpaper_isVideoSupportEffect", false);
                    Log.i("Keyguard-Editor-Wallpaper", "isVideoSupportGlassFilter: isVideoSupportGlassFilter = " + Wallpaper.isVideoSupportGlassFilter);
                    boolValueOf = Boolean.valueOf(z);
                } catch (Exception unused) {
                    boolValueOf = Boolean.FALSE;
                }
                Wallpaper.isVideoSupportGlassFilter = boolValueOf;
            }
            Boolean bool = Wallpaper.isVideoSupportGlassFilter;
            Intrinsics.checkNotNull(bool);
            return bool.booleanValue();
        }

        public final boolean needSmallSensorWallpaper(String str, Context context) {
            Intrinsics.checkNotNullParameter(context, "context");
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            return deviceUtil.isFold() && !deviceUtil.isLargeDisplay(context) && isSensorWallpaper(str);
        }

        public final boolean needWallpaperTargetAll(String str) {
            return Intrinsics.areEqual(str, CategoryConstants.CATEGORY_MAML) || Intrinsics.areEqual(str, CategoryConstants.CATEGORY_SUPER_WALLPAPER) || Intrinsics.areEqual(str, "linkage_video") || Intrinsics.areEqual(str, "gallery");
        }

        public final boolean unSupportToEdit(String str, String str2) {
            return Intrinsics.areEqual(str, CategoryConstants.CATEGORY_SUPER_WALLPAPER) || Intrinsics.areEqual(str, "linkage_video") || Intrinsics.areEqual(str2, CategoryConstants.CATEGORY_SUPER_WALLPAPER) || Intrinsics.areEqual(str2, "linkage_video");
        }

        public final boolean unSupportToHistory(String str, String str2) {
            return (Intrinsics.areEqual(str, "live_picker") || Intrinsics.areEqual(str2, "live_picker")) || ((isSpecialWallpaperType(str) && !Intrinsics.areEqual(str, "sensor")) || (isSpecialWallpaperType(str2) && !Intrinsics.areEqual(str2, "sensor")));
        }
    }
}
