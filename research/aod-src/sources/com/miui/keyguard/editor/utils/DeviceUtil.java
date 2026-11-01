package com.miui.keyguard.editor.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.display.DisplayManager;
import android.os.Bundle;
import android.os.SystemProperties;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.IWindowManager;
import android.view.RoundedCorner;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowManager;
import android.view.WindowManagerGlobal;
import com.miui.keyguard.editor.data.preset.FontFilterKt;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.homepage.util.ResourcesProvider;
import com.miui.keyguard.editor.settings.util.ActivityEmbeddingUtils;
import com.xiaomi.onetrack.api.bf;
import com.xiaomi.onetrack.util.ac;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Set;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.math.MathKt;
import kotlin.text.Regex;
import kotlin.text.StringsKt;
import miui.os.Build;
import miui.util.FeatureParser;
import miuix.core.util.WindowUtils;
import miuix.device.DeviceUtils;
import miuix.os.DeviceHelper;

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
/* JADX INFO: compiled from: DeviceUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class DeviceUtil {
    public static final boolean BACKGROUND_BLUR_SUPPORTED;
    public static final String FORCE_FSG_NAV_BAR;
    public static final String MiOSDebugVersion;
    public static final int MiOSVersion;
    public static final String MiOSVersionIncremental;
    public static final Lazy cpuLevel$delegate;
    public static final DeviceType deviceType;
    public static final Lazy gpuLevel$delegate;
    public static final int multiDisplayType;
    public static Point sScreenSize;
    public static final DeviceUtil INSTANCE = new DeviceUtil();
    public static final Set FORCE_LITE_DEVICES_NAME = SetsKt.setOf((Object[]) new String[]{"emerald", "obsidian"});
    public static final HashSet PAD_DEVICE_NAMES = SetsKt.hashSetOf("dagu", "dizi_ru_global", "dizi_eea_global", "dizi_global", "dizi_id_global", "dizi", "dizi_tr_global", "dizi_tw_global", "dizi_in_global", "elish", "enuma", "liuqin", "nabu_in_global", "nabu_global", "nabu", "nabu_eea_global", "nabu_ru_global", "nabu_tr_global", "nabu_tw_global", "pipa_global", "pipa_tw_global", "pipa_ru_global", "pipa_tr_global", "pipa_id_global", "pipa_in_global", "pipa", "pipa_eea_global", "ruan_in_global", "ruan", "ruan_global", "ruan_eea_global", "ruan_ru_global", "sheng_tw_global", "sheng_ru_global", "sheng", "sheng_global", "sheng_eea_global", "sheng_id_global", "xun_tw_global", "xun", "xun_tr_global", "xun_global", "xun_eea_global", "xun_ru_global", "xun_in_global", "xun_id_global", "yudi", "yunluo", "yunluo_tr_global", "yunluo_tw_global", "yunluo_ru_global", "yunluo_global", "yunluo_eea_global", "yunluo_id_global", "yunluo_in_global", "flare_tr_global", "flare_id_global", "flare_tw_global", "flare_ru_global", "flare", "flare_eea_global", "flare_global", "jinghu", "koto_in_global", "koto_global", "koto_tw_global", "koto_ru_global", "koto", "spark_in_global", "spark_global", "spark_eea_global", "spark_tr_global", "spark_tw_global", "spark_ru_global", "taiko_ru_global", "taiko_in_global", "taiko", "taiko_id_global", "uke_global", "uke_in_global", "uke", "uke_id_global", "uke_eea_global", "uke_ru_global", "uke_tw_global", "uke_tr_global", "koto_eea_global", "muyu", "muyu_eea_global", "taiko_eea_global", "flute", "flute_global", "flute_eea_global", "flute_ru_global", "flute_tr_global", "flute_tw_global", "flute_in_global", "flute_id_global");
    public static final List physicalDisplay = new ArrayList();
    public static final Object LOCK = new Object();

    /* JADX INFO: compiled from: DeviceUtil.kt */
    @Metadata
    public static final class DisplayInfo {
        public float density;
        public int densityDpi;
        public int height;
        public int sw;
        public int width;

        public DisplayInfo(int i, int i2, int i3, float f, int i4) {
            this.width = i;
            this.height = i2;
            this.sw = i3;
            this.density = f;
            this.densityDpi = i4;
        }

        public /* synthetic */ DisplayInfo(int i, int i2, int i3, float f, int i4, int i5, DefaultConstructorMarker defaultConstructorMarker) {
            this((i5 & 1) != 0 ? 0 : i, (i5 & 2) != 0 ? 0 : i2, (i5 & 4) != 0 ? 0 : i3, (i5 & 8) != 0 ? 1.0f : f, (i5 & 16) != 0 ? 0 : i4);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof DisplayInfo)) {
                return false;
            }
            DisplayInfo displayInfo = (DisplayInfo) obj;
            return this.width == displayInfo.width && this.height == displayInfo.height && this.sw == displayInfo.sw && Float.compare(this.density, displayInfo.density) == 0 && this.densityDpi == displayInfo.densityDpi;
        }

        public final float getDensity() {
            return this.density;
        }

        public final int getDensityDpi() {
            return this.densityDpi;
        }

        public final int getHeight() {
            return this.height;
        }

        public final int getSw() {
            return this.sw;
        }

        public final int getWidth() {
            return this.width;
        }

        public int hashCode() {
            return (((((((Integer.hashCode(this.width) * 31) + Integer.hashCode(this.height)) * 31) + Integer.hashCode(this.sw)) * 31) + Float.hashCode(this.density)) * 31) + Integer.hashCode(this.densityDpi);
        }

        public final void setDensity(float f) {
            this.density = f;
        }

        public final void setDensityDpi(int i) {
            this.densityDpi = i;
        }

        public final void setHeight(int i) {
            this.height = i;
        }

        public final void setSw(int i) {
            this.sw = i;
        }

        public final void setWidth(int i) {
            this.width = i;
        }

        public String toString() {
            return "DisplayInfo(width=" + this.width + ", height=" + this.height + ", sw=" + this.sw + ", density=" + this.density + ", densityDpi=" + this.densityDpi + ")";
        }
    }

    /* JADX INFO: compiled from: DeviceUtil.kt */
    @Metadata
    public static final class OsVersionConstants {
        public static final OsVersionConstants INSTANCE = new OsVersionConstants();
    }

    /* JADX INFO: compiled from: DeviceUtil.kt */
    @Metadata
    public static final class ScreenInfo {
        public final float density;
        public final int deviceType;
        public final int height;
        public final int width;

        public ScreenInfo(int i, int i2, float f, int i3) {
            this.width = i;
            this.height = i2;
            this.density = f;
            this.deviceType = i3;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof ScreenInfo)) {
                return false;
            }
            ScreenInfo screenInfo = (ScreenInfo) obj;
            return this.width == screenInfo.width && this.height == screenInfo.height && Float.compare(this.density, screenInfo.density) == 0 && this.deviceType == screenInfo.deviceType;
        }

        public final float getDensity() {
            return this.density;
        }

        public final int getDeviceType() {
            return this.deviceType;
        }

        public final int getHeight() {
            return this.height;
        }

        public final int getWidth() {
            return this.width;
        }

        public int hashCode() {
            return (((((Integer.hashCode(this.width) * 31) + Integer.hashCode(this.height)) * 31) + Float.hashCode(this.density)) * 31) + Integer.hashCode(this.deviceType);
        }

        public String toString() {
            return "ScreenInfo(width=" + this.width + ", height=" + this.height + ", density=" + this.density + ", deviceType=" + this.deviceType + ")";
        }
    }

    /* JADX INFO: compiled from: DeviceUtil.kt */
    @Metadata
    public enum ScreenType {
        SMALL,
        LARGE;

        public static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    }

    static {
        DeviceType deviceType2;
        int i = SystemProperties.getInt("persist.sys.muiltdisplay_type", 0);
        multiDisplayType = i;
        BACKGROUND_BLUR_SUPPORTED = miuix.core.util.SystemProperties.getBoolean("persist.sys.background_blur_supported", false);
        MiOSVersion = SystemProperties.getInt("ro.mi.os.version.code", 0);
        MiOSVersionIncremental = SystemProperties.get("ro.build.version.incremental", "");
        MiOSDebugVersion = SystemProperties.get("ro.mi.os.debug.version.code", "");
        FORCE_FSG_NAV_BAR = "force_fsg_nav_bar";
        if (Build.IS_TABLET) {
            deviceType2 = DeviceType.PAD;
        } else {
            deviceType2 = i == 2 ? DeviceType.FOLD : DeviceType.PHONE;
        }
        deviceType = deviceType2;
        cpuLevel$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.utils.DeviceUtil$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Integer.valueOf(DeviceUtil.cpuLevel_delegate$lambda$1());
            }
        });
        gpuLevel$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.utils.DeviceUtil$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return Integer.valueOf(DeviceUtil.gpuLevel_delegate$lambda$3());
            }
        });
    }

    public static final int cpuLevel_delegate$lambda$1() {
        int deviceLevel = DeviceUtils.getDeviceLevel(DeviceUtils.DEV_STANDARD_VERSION, DeviceUtils.TYPE_CPU);
        Log.i("DeviceUtil", "cpu level " + deviceLevel);
        return deviceLevel;
    }

    public static final int gpuLevel_delegate$lambda$3() {
        int deviceLevel = DeviceUtils.getDeviceLevel(DeviceUtils.DEV_STANDARD_VERSION, DeviceUtils.TYPE_GPU);
        Log.i("DeviceUtil", "gpu level " + deviceLevel);
        return deviceLevel;
    }

    public static /* synthetic */ float initialDensity$default(DeviceUtil deviceUtil, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        return deviceUtil.initialDensity(i);
    }

    public static /* synthetic */ int initialDensityDpi$default(DeviceUtil deviceUtil, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        return deviceUtil.initialDensityDpi(i);
    }

    public static /* synthetic */ boolean isHomeNeedUseOriginalEffect$default(DeviceUtil deviceUtil, WallpaperController wallpaperController, Context context, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            wallpaperController = null;
        }
        if ((i2 & 2) != 0) {
            context = null;
        }
        return deviceUtil.isHomeNeedUseOriginalEffect(wallpaperController, context, i);
    }

    public final boolean atLeast(int i) {
        return android.os.Build.VERSION.SDK_INT >= i;
    }

    public final float calculateNewDpPosition(float f, ScreenInfo oldScreenInfo, ScreenInfo newScreenInfo, boolean z) {
        Intrinsics.checkNotNullParameter(oldScreenInfo, "oldScreenInfo");
        Intrinsics.checkNotNullParameter(newScreenInfo, "newScreenInfo");
        if (oldScreenInfo.getWidth() <= 0 || oldScreenInfo.getHeight() <= 0 || newScreenInfo.getWidth() <= 0 || newScreenInfo.getHeight() <= 0) {
            Log.w("DeviceUtil", "calculateNewDpPosition: invalid screen size, keep oldDpValue=" + f);
            return f;
        }
        float density = oldScreenInfo.getDensity() > 0.0f ? oldScreenInfo.getDensity() : 1.0f;
        float density2 = newScreenInfo.getDensity() > 0.0f ? newScreenInfo.getDensity() : 1.0f;
        float width = oldScreenInfo.getWidth() / density;
        float height = oldScreenInfo.getHeight() / density;
        float width2 = newScreenInfo.getWidth() / density2;
        float height2 = newScreenInfo.getHeight() / density2;
        float f2 = width2 / width;
        float f3 = height2 / height;
        float f4 = (z ? f2 : f3) * f;
        Log.d("DeviceUtil", "calculateNewDpPosition: oldDpValue=" + f + ", oldDp=(" + width + ac.b + height + "), newDp=(" + width2 + ac.b + height2 + "), ratioX=" + f2 + ", ratioY=" + f3 + ", isHorizontal=" + z + ", newDpValue=" + f4);
        return f4;
    }

    public final Context createConfigurationContext(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Configuration configuration = context.getResources().getConfiguration();
        Log.i("DeviceUtil", "before createConfigurationContext configuration.densityDpi:" + configuration.densityDpi + ",screenWidthDp:" + configuration.screenWidthDp + ",screenHeightDp=" + configuration.screenHeightDp);
        Point screenSize = WindowUtils.getScreenSize(context);
        Configuration configuration2 = new Configuration();
        DeviceUtil deviceUtil = INSTANCE;
        float fInitialDensity$default = initialDensity$default(deviceUtil, 0, 1, null);
        int iInitialDensityDpi$default = initialDensityDpi$default(deviceUtil, 0, 1, null);
        if (android.os.Build.VERSION.SDK_INT >= 35) {
            configuration2.orientation = deviceUtil.isDisplayLandScape(context) ? 2 : 1;
        }
        configuration2.densityDpi = iInitialDensityDpi$default;
        configuration2.screenWidthDp = MathKt.roundToInt(screenSize.x / fInitialDensity$default);
        configuration2.screenHeightDp = MathKt.roundToInt(screenSize.y / fInitialDensity$default);
        configuration2.smallestScreenWidthDp = MathKt.roundToInt(Math.min(screenSize.x, screenSize.y) / fInitialDensity$default);
        Context contextCreateConfigurationContext = context.createConfigurationContext(configuration2);
        Log.i("DeviceUtil", "after createConfigurationContext configuration.densityDpi:" + configuration2.densityDpi + ",screenWidthDp:" + configuration2.screenWidthDp + ",screenHeightDp=" + configuration2.screenHeightDp);
        Intrinsics.checkNotNull(contextCreateConfigurationContext);
        return contextCreateConfigurationContext;
    }

    public final Rect currentDisplaySize(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService("window");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        Rect bounds = ((WindowManager) systemService).getCurrentWindowMetrics().getBounds();
        Intrinsics.checkNotNullExpressionValue(bounds, "getBounds(...)");
        return bounds;
    }

    public final DisplayInfo currentDisplaySizeByContext(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        int screenOrientation = getScreenOrientation(context);
        if (isFold()) {
            DisplayInfo screenSizeFromDisplays = isLargeDisplay(context) ? getScreenSizeFromDisplays(context, ScreenType.LARGE) : getScreenSizeFromDisplays(context, ScreenType.SMALL);
            if (screenOrientation != 1 && screenOrientation != 3) {
                return screenSizeFromDisplays;
            }
            int width = screenSizeFromDisplays.getWidth();
            screenSizeFromDisplays.setWidth(screenSizeFromDisplays.getHeight());
            screenSizeFromDisplays.setHeight(width);
            screenSizeFromDisplays.setSw((int) (width / screenSizeFromDisplays.getDensity()));
            return screenSizeFromDisplays;
        }
        Point screenSize = getScreenSize(context, true);
        DisplayInfo displayInfo = new DisplayInfo(0, 0, 0, 0.0f, 0, 31, null);
        if (screenOrientation == 1 || screenOrientation == 3) {
            displayInfo.setWidth(screenSize.y);
            displayInfo.setHeight(screenSize.x);
            displayInfo.setSw(context.getResources().getConfiguration().smallestScreenWidthDp);
            displayInfo.setDensity(context.getResources().getConfiguration().densityDpi / 160);
            displayInfo.setDensityDpi(context.getResources().getConfiguration().densityDpi);
            return displayInfo;
        }
        displayInfo.setWidth(screenSize.x);
        displayInfo.setHeight(screenSize.y);
        displayInfo.setSw(context.getResources().getConfiguration().smallestScreenWidthDp);
        displayInfo.setDensity(context.getResources().getConfiguration().densityDpi / 160);
        displayInfo.setDensityDpi(context.getResources().getConfiguration().densityDpi);
        return displayInfo;
    }

    public final void enableAod(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            Log.d("DeviceUtil", "enableAod");
            Settings.Secure.putInt(context.getContentResolver(), "doze_always_on", 1);
        } catch (Exception e) {
            Log.e("DeviceUtil", "enableAod error", e);
        }
    }

    public final boolean getBACKGROUND_BLUR_SUPPORTED() {
        return BACKGROUND_BLUR_SUPPORTED;
    }

    public final int getCpuLevel() {
        return ((Number) cpuLevel$delegate.getValue()).intValue();
    }

    public final ScreenInfo getCurrentScreenInfo(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        DisplayInfo displayInfoCurrentDisplaySizeByContext = INSTANCE.currentDisplaySizeByContext(context);
        ScreenInfo screenInfo = new ScreenInfo(displayInfoCurrentDisplaySizeByContext.getWidth(), displayInfoCurrentDisplaySizeByContext.getHeight(), displayInfoCurrentDisplaySizeByContext.getDensity(), CommonUtils.INSTANCE.detectType());
        Log.d("DeviceUtil", "getCurrentScreenInfo: width=" + screenInfo.getWidth() + ", height=" + screenInfo.getHeight() + ", density=" + screenInfo.getDensity() + ", deviceType=" + screenInfo.getDeviceType());
        return screenInfo;
    }

    public final int getEditorVersion(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo("com.miui.aod", 128).metaData;
            if (bundle != null) {
                return bundle.getInt("editor_os_version");
            }
            return 0;
        } catch (Exception e) {
            Log.e("DeviceUtil", "getEditorVersion error:", e);
            return 0;
        }
    }

    public final int getGpuLevel() {
        return ((Number) gpuLevel$delegate.getValue()).intValue();
    }

    public final Set getPadDeviceNameSet() {
        return PAD_DEVICE_NAMES;
    }

    public final int getSDKVersion() {
        return android.os.Build.VERSION.SDK_INT;
    }

    public final int getScreenOrientation(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Object systemService = context.getSystemService("window");
        Intrinsics.checkNotNull(systemService, "null cannot be cast to non-null type android.view.WindowManager");
        return ((WindowManager) systemService).getDefaultDisplay().getRotation();
    }

    public final Point getScreenSize(Context context) {
        if (context == null) {
            return new Point();
        }
        Point pointProvideRealSize = ResourcesProvider.INSTANCE.provideRealSize(context);
        if (pointProvideRealSize.x > 0 && pointProvideRealSize.y > 0) {
            return pointProvideRealSize;
        }
        Rect bounds = WindowUtils.getWindowManager(context).getCurrentWindowMetrics().getBounds();
        pointProvideRealSize.x = bounds.width();
        pointProvideRealSize.y = bounds.height();
        return pointProvideRealSize;
    }

    public final Point getScreenSize(Context context, boolean z) {
        Point point;
        Intrinsics.checkNotNullParameter(context, "context");
        synchronized (LOCK) {
            if (sScreenSize == null || z) {
                INSTANCE.updateScreenSize(context);
            }
            point = sScreenSize;
            Intrinsics.checkNotNull(point);
        }
        return point;
    }

    public final DisplayInfo getScreenSizeFromDisplays(Context context, ScreenType screenType) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(screenType, "screenType");
        DisplayInfo displayInfo = new DisplayInfo(0, 0, 0, 0.0f, 0, 31, null);
        Object systemService = context.getSystemService("display");
        Display[] displays = null;
        DisplayManager displayManager = systemService instanceof DisplayManager ? (DisplayManager) systemService : null;
        if (atLeast(33)) {
            if (displayManager != null) {
                displays = displayManager.getDisplays("android.hardware.display.category.ALL_INCLUDING_DISABLED");
            }
        } else if (displayManager != null) {
            displays = displayManager.getDisplays();
        }
        if (physicalDisplay.isEmpty()) {
            int i = isFold() ? 2 : 1;
            if (displays != null) {
                Iterator it = ArrayIteratorKt.iterator(displays);
                while (it.hasNext()) {
                    Display display = (Display) it.next();
                    Log.d("DeviceUtil", "getScreenSizeFromDisplays: id=" + display.getDisplayId() + " mode=" + display.getMode());
                    if (display.getDisplayId() < i) {
                        List list = physicalDisplay;
                        Intrinsics.checkNotNull(display);
                        list.add(display);
                    }
                }
            }
        }
        List list2 = physicalDisplay;
        if (list2.isEmpty() || list2.size() != 2) {
            Point screenSize = getScreenSize(context, true);
            displayInfo.setWidth(Math.min(screenSize.x, screenSize.y));
            displayInfo.setHeight(Math.max(screenSize.x, screenSize.y));
            displayInfo.setSw(context.getResources().getConfiguration().smallestScreenWidthDp);
            displayInfo.setDensity(context.getResources().getConfiguration().densityDpi / 160);
            displayInfo.setDensityDpi(context.getResources().getConfiguration().densityDpi);
            Log.d("DeviceUtil", "default current screenType=" + screenType + " displayInfo=" + displayInfo);
            return displayInfo;
        }
        List listSortedWith = CollectionsKt.sortedWith(list2, new Comparator() { // from class: com.miui.keyguard.editor.utils.DeviceUtil$getScreenSizeFromDisplays$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return ComparisonsKt.compareValues(Integer.valueOf(((Display) obj).getMode().getPhysicalWidth()), Integer.valueOf(((Display) obj2).getMode().getPhysicalWidth()));
            }
        });
        Display display2 = (Display) listSortedWith.get(0);
        Display display3 = (Display) listSortedWith.get(1);
        if (screenType == ScreenType.LARGE) {
            display2 = display3;
        }
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display2.getMetrics(displayMetrics);
        displayInfo.setWidth(display2.getMode().getPhysicalWidth());
        displayInfo.setHeight(display2.getMode().getPhysicalHeight());
        displayInfo.setSw((int) (display2.getMode().getPhysicalWidth() / displayMetrics.density));
        displayInfo.setDensity(displayMetrics.density);
        displayInfo.setDensityDpi(displayMetrics.densityDpi);
        Log.d("DeviceUtil", "screenType=" + screenType + "  displayInfo=" + displayInfo);
        return displayInfo;
    }

    public final int getScreenStatus(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (isPad()) {
            return isDisplayLandScape(context) ? 3 : 2;
        }
        if (isFold()) {
            if (isDisplayLandScape(context)) {
                return 3;
            }
            if (isLargeDisplay(context)) {
                return 2;
            }
        }
        return 1;
    }

    public final int getScreenTypeFromConfiguration(Configuration configuration) {
        int iIntValue;
        try {
            Object objInvokeObject = ReflectUtils.invokeObject(configuration.getClass(), configuration, "getScreenType", Integer.TYPE, null, new Object[0]);
            Intrinsics.checkNotNull(objInvokeObject);
            iIntValue = ((Number) objInvokeObject).intValue();
        } catch (Exception unused) {
            iIntValue = -1;
        }
        Log.i("DeviceUtil", "getScreenTypeFromConfiguration, screenType=" + iIntValue);
        return iIntValue;
    }

    public final int getScreenshotScreenStatus(DeviceScreenshotHelper.ScreenshotContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (context.isLargeScreen()) {
            return context.getResources().getConfiguration().orientation == 1 ? 2 : 3;
        }
        return 1;
    }

    public final Configuration initialConfiguration(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Log.i("DeviceUtil", "configuration " + context.getResources().getConfiguration());
        Configuration configuration = new Configuration();
        configuration.setTo(context.getResources().getConfiguration());
        DeviceUtil deviceUtil = INSTANCE;
        float fInitialDensity$default = initialDensity$default(deviceUtil, 0, 1, null);
        configuration.densityDpi = initialDensityDpi$default(deviceUtil, 0, 1, null);
        if (deviceUtil.isN18()) {
            configuration.screenWidthDp = MathKt.roundToInt(deviceUtil.currentDisplaySize(context).width() / fInitialDensity$default);
            configuration.screenHeightDp = MathKt.roundToInt(deviceUtil.currentDisplaySize(context).height() / fInitialDensity$default);
        } else {
            configuration.screenWidthDp = MathKt.roundToInt(context.getResources().getDisplayMetrics().widthPixels / fInitialDensity$default);
            configuration.screenHeightDp = MathKt.roundToInt(context.getResources().getDisplayMetrics().heightPixels / fInitialDensity$default);
        }
        Rect rectCurrentDisplaySize = deviceUtil.currentDisplaySize(context);
        if (deviceUtil.isN85X() || deviceUtil.isN85()) {
            configuration.smallestScreenWidthDp = MathKt.roundToInt(Math.min(rectCurrentDisplaySize.width() / fInitialDensity$default, rectCurrentDisplaySize.height() / fInitialDensity$default));
        } else {
            configuration.smallestScreenWidthDp = (int) Math.min(rectCurrentDisplaySize.width() / fInitialDensity$default, rectCurrentDisplaySize.height() / fInitialDensity$default);
        }
        Log.i("DeviceUtil", "reseted configuration " + configuration);
        return configuration;
    }

    public final float initialDensity(int i) {
        return (initialDensityDpi(i) * 1.0f) / 160;
    }

    public final int initialDensityDpi(int i) {
        String str;
        List listEmptyList;
        IWindowManager windowManagerService = WindowManagerGlobal.getWindowManagerService();
        int initialDisplayDensity = windowManagerService.getInitialDisplayDensity(i);
        int[] intArray = FeatureParser.getIntArray("screen_resolution_supported");
        Log.d("DeviceUtil", "wm densityDpi=" + initialDisplayDensity + " screenResolutionsSupported=" + intArray + "(" + (intArray != null ? Integer.valueOf(intArray.length) : null) + ")");
        if (intArray == null || intArray.length <= 1 || (str = SystemProperties.get("persist.sys.miui_resolution", (String) null)) == null || Intrinsics.areEqual("", str)) {
            return initialDisplayDensity;
        }
        Point point = new Point();
        windowManagerService.getInitialDisplaySize(i, point);
        Log.d("DeviceUtil", "screenResolution=" + str + " wm point=" + point);
        List listSplit = new Regex(ac.b).split(str, 0);
        if (listSplit.isEmpty()) {
            listEmptyList = CollectionsKt.emptyList();
        } else {
            ListIterator listIterator = listSplit.listIterator(listSplit.size());
            while (listIterator.hasPrevious()) {
                if (((String) listIterator.previous()).length() != 0) {
                    listEmptyList = CollectionsKt.take(listSplit, listIterator.nextIndex() + 1);
                }
            }
            listEmptyList = CollectionsKt.emptyList();
        }
        return (int) (((initialDisplayDensity * 1.0f) * Integer.parseInt(((String[]) listEmptyList.toArray(new String[0]))[0])) / point.x);
    }

    public final boolean isAodEnabled(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            return Settings.Secure.getInt(context.getContentResolver(), "doze_always_on") == 1;
        } catch (Exception e) {
            Log.e("DeviceUtil", "isAodEnabled error", e);
            return false;
        }
    }

    public final boolean isChineseLanguage() {
        Locale locale = Locale.getDefault();
        return Intrinsics.areEqual(locale.getCountry(), "CN") || Intrinsics.areEqual(locale.getLanguage(), "zh");
    }

    public final boolean isCpuAndGpuHighLevel() {
        return getCpuLevel() == 2 && getGpuLevel() == 2;
    }

    public final boolean isCpuOrGpuPrimaryLevel() {
        return getCpuLevel() == 0 || getGpuLevel() == 0;
    }

    public final boolean isDataCapable(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        try {
            Object systemService = context.getSystemService(bf.d);
            TelephonyManager telephonyManager = systemService instanceof TelephonyManager ? (TelephonyManager) systemService : null;
            return telephonyManager != null && telephonyManager.isDataCapable();
        } catch (Exception e) {
            Log.e("DeviceUtil", "isDataCapable: ", e);
            return true;
        }
    }

    public final boolean isDisplayFolded(Configuration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        return getScreenTypeFromConfiguration(configuration) == 1;
    }

    public final boolean isDisplayInversionEnabled(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        boolean z = Settings.Secure.getInt(context.getContentResolver(), "accessibility_display_inversion_enabled", 0) == 1;
        Log.i("DeviceUtil", "isDisplayInversionEnabled " + z);
        return z;
    }

    public final boolean isDisplayLandScape(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        int rotation = WindowUtils.getDisplay(context).getRotation();
        return rotation == 1 || rotation == 3;
    }

    public final boolean isFlip() {
        return miuix.os.Build.IS_FLIP;
    }

    public final boolean isFold() {
        return DeviceType.FOLD == deviceType;
    }

    public final boolean isFoldLargeScreen(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return isFold() && isLargeDisplay(context);
    }

    public final boolean isFoldSmallScreen(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return isFold() && !isLargeDisplay(context);
    }

    public final boolean isForceLiteDevice() {
        return FORCE_LITE_DEVICES_NAME.contains(android.os.Build.DEVICE);
    }

    public final boolean isFullScreenGestureNav(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return Settings.Global.getInt(context.getContentResolver(), FORCE_FSG_NAV_BAR, 0) != 0;
    }

    public final boolean isGreaterChinaByRegion() {
        String upperCase = null;
        String str = SystemProperties.get("ro.miui.build.region", (String) null);
        if (str == null) {
            str = SystemProperties.get("ro.miui.cust_variant", (String) null);
        }
        if (str != null) {
            upperCase = str.toUpperCase(Locale.ROOT);
            Intrinsics.checkNotNullExpressionValue(upperCase, "toUpperCase(...)");
        }
        return Intrinsics.areEqual("CN", upperCase) || Intrinsics.areEqual("TW", upperCase);
    }

    public final boolean isGteAndroidV() {
        return getSDKVersion() >= 35;
    }

    public final boolean isGteAndroidW() {
        return getSDKVersion() >= 36;
    }

    public final boolean isGteOS22() {
        List listSplit$default;
        int i;
        int i2 = MiOSVersion;
        String str = MiOSVersionIncremental;
        String str2 = MiOSDebugVersion;
        Log.i("DeviceUtil", "MiOSVersion=" + i2 + "; MiOSVersionIncremental=" + str + "; MiOSDebugVersion=" + str2);
        if (i2 > 2) {
            return true;
        }
        if (i2 < 2) {
            return false;
        }
        if (str2 != null) {
            try {
                if (str2.length() != 0 && Float.parseFloat(str2) >= 2.2f) {
                    return true;
                }
                if (str != null && str.length() != 0) {
                    listSplit$default = StringsKt.split$default(str, new String[]{"."}, false, 0, 6, null);
                    if (listSplit$default.size() <= 2 && 200 <= (i = Integer.parseInt((String) listSplit$default.get(2))) && i < 100001) {
                        return true;
                    }
                }
            } catch (Exception e) {
                Log.e("DeviceUtil", "isGteOS2 error", e);
            }
        } else if (str != null) {
            listSplit$default = StringsKt.split$default(str, new String[]{"."}, false, 0, 6, null);
            if (listSplit$default.size() <= 2) {
            }
        }
        return false;
    }

    public final boolean isHighDevice() {
        return isCpuAndGpuHighLevel();
    }

    public final boolean isHighTextContrastEnabled(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return Settings.Secure.getInt(context.getContentResolver(), "high_text_contrast_enabled", 0) == 1;
    }

    public final boolean isHomeNeedUseOriginalEffect(WallpaperController wallpaperController, Context context, int i) {
        return i != 0;
    }

    public final boolean isInFreeform(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        Log.d("DeviceUtil", context.getResources().getConfiguration().toString());
        String string = context.getResources().getConfiguration().toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return StringsKt.contains$default((CharSequence) string, (CharSequence) "mWindowingMode=freeform", false, 2, (Object) null);
    }

    public final boolean isInSingleHandMode(Context context) {
        return context != null && Settings.Secure.getInt(context.getContentResolver(), "one_handed_mode_activated", 0) == 1;
    }

    public final boolean isInternationalBuild() {
        return miuix.os.Build.IS_INTERNATIONAL_BUILD;
    }

    public final boolean isJ18() {
        return TextUtils.equals(android.os.Build.DEVICE, "cetus");
    }

    public final boolean isLandScape(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return context.getResources().getConfiguration().orientation == 2;
    }

    public final boolean isLargeDisplay(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        if (ActivityEmbeddingUtils.isActivityAlreadyEmbedded(context) && !(context instanceof DeviceScreenshotHelper.ScreenshotContext)) {
            return DeviceHelper.isWideScreen(context);
        }
        if (context instanceof DeviceScreenshotHelper.ScreenshotContext) {
            return ((DeviceScreenshotHelper.ScreenshotContext) context).getResources().getConfiguration().smallestScreenWidthDp > 600;
        }
        float f = context.getResources().getDisplayMetrics().density;
        DeviceUtil deviceUtil = INSTANCE;
        return ((float) Math.min(deviceUtil.currentDisplaySize(context).width(), deviceUtil.currentDisplaySize(context).height())) / f > 600.0f;
    }

    public final boolean isLiteDevice() {
        return DeviceUtils.isMiuiMiddle() || DeviceUtils.isMiuiLiteRom() || isPad() || isCpuOrGpuPrimaryLevel() || isForceLiteDevice();
    }

    public final boolean isLowComputeDevice() {
        if (FontFilterKt.getCOMPUTILITYLEVELV2() >= 2) {
            return false;
        }
        if (FontFilterKt.getCOMPUTILITYLEVELV2() == 1) {
            return true;
        }
        return isLiteDevice();
    }

    public final boolean isN18() {
        return TextUtils.equals(android.os.Build.DEVICE, "goku");
    }

    public final boolean isN8() {
        return TextUtils.equals(android.os.Build.DEVICE, "ruyi");
    }

    public final boolean isN85() {
        return TextUtils.equals(android.os.Build.DEVICE, "flare");
    }

    public final boolean isN85X() {
        return TextUtils.equals(android.os.Build.DEVICE, "spark");
    }

    public final boolean isO8() {
        return TextUtils.equals(android.os.Build.DEVICE, "bixi");
    }

    public final boolean isOS4PickerLiteDevice() {
        return DeviceUtils.isMiuiMiddle() || DeviceUtils.isMiuiLiteRom() || isCpuOrGpuPrimaryLevel() || isForceLiteDevice();
    }

    public final boolean isPad() {
        return DeviceType.PAD == deviceType;
    }

    public final boolean isPhone() {
        return DeviceType.PHONE == deviceType;
    }

    public final boolean isQ18() {
        return TextUtils.equals(android.os.Build.DEVICE, "lhasa");
    }

    public final boolean isQ18Device() {
        return Intrinsics.areEqual(android.os.Build.DEVICE, "lhasa");
    }

    public final boolean isRedmi() {
        return android.os.Build.BRAND.equals("Redmi");
    }

    public final boolean isRtl(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return context.getResources().getConfiguration().getLayoutDirection() == 1;
    }

    public final boolean isScreenOrientationLocked(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return !(Settings.System.getInt(context.getContentResolver(), "accelerometer_rotation", 0) == 1);
    }

    public final boolean isSmallDisplay(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return isPhone() || !isLargeDisplay(context);
    }

    public final boolean isSupportHierarchyDevices() {
        return !isLiteDevice();
    }

    public final boolean isVersionOs3(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return getEditorVersion(context) >= 48;
    }

    public final boolean isVersionOs4(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return getEditorVersion(context) >= 64;
    }

    public final int osVersion() {
        return MiOSVersion;
    }

    public final float[] screenRadii(View view) {
        Intrinsics.checkNotNullParameter(view, "view");
        WindowInsets rootWindowInsets = view.getRootWindowInsets();
        if (rootWindowInsets == null) {
            return new float[8];
        }
        float fScreenRadius = screenRadius(rootWindowInsets, 0);
        float fScreenRadius2 = screenRadius(rootWindowInsets, 1);
        float fScreenRadius3 = screenRadius(rootWindowInsets, 3);
        float fScreenRadius4 = screenRadius(rootWindowInsets, 2);
        return new float[]{fScreenRadius, fScreenRadius, fScreenRadius2, fScreenRadius2, fScreenRadius4, fScreenRadius4, fScreenRadius3, fScreenRadius3};
    }

    public final float screenRadius(WindowInsets windowInsets, int i) {
        RoundedCorner roundedCorner = windowInsets.getRoundedCorner(i);
        if (roundedCorner != null) {
            return roundedCorner.getRadius();
        }
        return 0.0f;
    }

    public final boolean supportAod(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        int identifier = context.getResources().getIdentifier("config_dozeAlwaysOnDisplayAvailable", "bool", "android");
        Log.i("DeviceUtil", "support aod resId " + identifier);
        if (identifier > 0) {
            boolean z = context.getResources().getBoolean(identifier);
            Log.i("DeviceUtil", "support aod available " + z);
            if (z && !isDisplayInversionEnabled(context)) {
                return true;
            }
        }
        return false;
    }

    public final boolean supportGlassAnim() {
        return isHighDevice() && !Build.IS_TABLET;
    }

    public final void updateScreenSize(Context context) {
        Point screenSize = WindowUtils.getScreenSize(context);
        sScreenSize = screenSize;
        Log.i("DeviceUtil", "updateScreenSize: " + screenSize);
    }
}
