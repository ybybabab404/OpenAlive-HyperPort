package com.miui.keyguard.editor.edit.color.picker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import com.miui.clock.module.ClockBean;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.xiaomi.onetrack.util.ac;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import miuix.device.DeviceUtils;

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
/* JADX INFO: compiled from: AutoColorPicker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public class AutoColorPicker {
    public final ClockBean clockBean;
    public final String clockType;
    public final Context context;

    /* JADX INFO: compiled from: AutoColorPicker.kt */
    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;
        public static final /* synthetic */ int[] $EnumSwitchMapping$1;

        static {
            int[] iArr = new int[RectWhich.values().length];
            try {
                iArr[RectWhich.CLOCK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[RectWhich.STATUS_BAR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[RectWhich.MAGAZINE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
            int[] iArr2 = new int[ScreenType.values().length];
            try {
                iArr2[ScreenType.PHONE.ordinal()] = 1;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr2[ScreenType.PORT.ordinal()] = 2;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr2[ScreenType.LAND.ordinal()] = 3;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr2[ScreenType.SMALL.ordinal()] = 4;
            } catch (NoSuchFieldError unused7) {
            }
            $EnumSwitchMapping$1 = iArr2;
        }
    }

    public AutoColorPicker(Context context, String clockType, ClockBean clockBean) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(clockType, "clockType");
        this.context = context;
        this.clockType = clockType;
        this.clockBean = clockBean;
    }

    public static /* synthetic */ Map getColorPalette$default(AutoColorPicker autoColorPicker, Bitmap bitmap, String str, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: getColorPalette");
        }
        if ((i2 & 4) != 0) {
            i = -1;
        }
        return autoColorPicker.getColorPalette(bitmap, str, i);
    }

    public static /* synthetic */ boolean isDarkImage$default(AutoColorPicker autoColorPicker, Bitmap bitmap, String str, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: isDarkImage");
        }
        if ((i2 & 4) != 0) {
            i = -1;
        }
        return autoColorPicker.isDarkImage(bitmap, str, i);
    }

    public static /* synthetic */ ColorData pickColor$default(AutoColorPicker autoColorPicker, Bitmap bitmap, boolean z, Rect rect, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: pickColor");
        }
        if ((i & 4) != 0) {
            rect = null;
        }
        return autoColorPicker.pickColor(bitmap, z, rect);
    }

    public final String clockType() {
        return this.clockType;
    }

    public final ScreenType currentScreenType() {
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        if (deviceUtil.isPhone()) {
            return ScreenType.PHONE;
        }
        if (deviceUtil.isPad() || (deviceUtil.isFold() && DeviceUtils.isInternalScreen(this.context))) {
            return deviceUtil.isDisplayLandScape(this.context) ? ScreenType.LAND : ScreenType.PORT;
        }
        return ScreenType.SMALL;
    }

    public final ClockBean getClockBean() {
        return this.clockBean;
    }

    public int getClockStyle() {
        ClockBean clockBean = this.clockBean;
        if (clockBean != null) {
            return clockBean.getStyle();
        }
        return -1;
    }

    public final Map getColorPalette(Bitmap wallpaper, String rectWhich, int i) {
        Map dynamicColor;
        Intrinsics.checkNotNullParameter(wallpaper, "wallpaper");
        Intrinsics.checkNotNullParameter(rectWhich, "rectWhich");
        try {
            WallpaperController.Companion companion = WallpaperController.Companion;
            dynamicColor = companion.getInstance(this.context).getDynamicColor(this.context, wallpaper, clockType(), rectWhich, getClockStyle(), i, null, companion.getCurrentWallpaperWhich(this.context));
        } catch (Exception e) {
            Log.w("Keyguard-Editor-AutoColorPicker", "getColorPalette: ", e);
            dynamicColor = null;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (dynamicColor != null) {
            for (Map.Entry entry : dynamicColor.entrySet()) {
                Object key = entry.getKey();
                Object value = entry.getValue();
                if ((key instanceof String) && (value instanceof Integer)) {
                    linkedHashMap.put(key, value);
                }
            }
        }
        return linkedHashMap;
    }

    public final Context getContext() {
        return this.context;
    }

    public final String getRectWhichString(RectWhich which) {
        Intrinsics.checkNotNullParameter(which, "which");
        return getRectWhichString(currentScreenType(), which);
    }

    public final String getRectWhichString(ScreenType type, RectWhich which) {
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(which, "which");
        Log.i("Keyguard-Editor-AutoColorPicker", "getRectWhichString: " + type.name() + ac.b + which.name());
        if (which == RectWhich.DEFAULT) {
            return "";
        }
        int i = WhenMappings.$EnumSwitchMapping$1[type.ordinal()];
        if (i == 1 || i == 2) {
            int i2 = WhenMappings.$EnumSwitchMapping$0[which.ordinal()];
            if (i2 == 1) {
                return "clock_style_rect";
            }
            if (i2 != 2) {
                return i2 != 3 ? "" : "magazine_script_rect";
            }
            return "status_bar_rect";
        }
        if (i == 3) {
            int i3 = WhenMappings.$EnumSwitchMapping$0[which.ordinal()];
            if (i3 == 1) {
                return "clock_style_rect_landscape";
            }
            if (i3 != 2) {
                return i3 != 3 ? "" : "magazine_script_rect_landscape";
            }
            return "status_bar_rect_landscape";
        }
        if (i != 4) {
            throw new NoWhenBranchMatchedException();
        }
        int i4 = WhenMappings.$EnumSwitchMapping$0[which.ordinal()];
        if (i4 == 1) {
            return "clock_style_rect_small_screen";
        }
        if (i4 != 2) {
            return i4 != 3 ? "" : "magazine_script_rect_small_screen";
        }
        return "status_bar_rect_small_screen";
    }

    public final boolean isDarkImage(Bitmap wallpaper, String rectWhich, int i) {
        String str;
        int i2;
        boolean zBooleanValue;
        Intrinsics.checkNotNullParameter(wallpaper, "wallpaper");
        Intrinsics.checkNotNullParameter(rectWhich, "rectWhich");
        try {
            WallpaperController.Companion companion = WallpaperController.Companion;
            str = rectWhich;
            i2 = i;
            try {
                zBooleanValue = companion.getInstance(this.context).isDarkWallpaper(this.context, wallpaper, clockType(), str, getClockStyle(), i2, companion.getCurrentWallpaperWhich(this.context)).booleanValue();
            } catch (Exception e) {
                e = e;
                Log.e("Keyguard-Editor-AutoColorPicker", "isDarkImage: ", e);
                zBooleanValue = false;
            }
        } catch (Exception e2) {
            e = e2;
            str = rectWhich;
            i2 = i;
        }
        Log.i("Keyguard-Editor-AutoColorPicker", "isDarkImage: " + zBooleanValue + ", rectWhich=" + str + ", clockStyle=" + getClockStyle() + ", signatureAlign=" + i2);
        return zBooleanValue;
    }

    public final boolean isNormalScreen() {
        ScreenType screenTypeCurrentScreenType = currentScreenType();
        return screenTypeCurrentScreenType == ScreenType.SMALL || screenTypeCurrentScreenType == ScreenType.PHONE;
    }

    public ColorData pickColor(Bitmap wallpaper) {
        Intrinsics.checkNotNullParameter(wallpaper, "wallpaper");
        RectWhich rectWhich = RectWhich.CLOCK;
        return new ColorData(0, 0, 0, 0, !isDarkImage$default(this, wallpaper, getRectWhichString(rectWhich), 0, 4, null), false, getColorPalette$default(this, wallpaper, getRectWhichString(rectWhich), 0, 4, null), false, null, 0, 943, null);
    }

    public ColorData pickColor(Bitmap wallpaper, Rect rect) {
        Intrinsics.checkNotNullParameter(wallpaper, "wallpaper");
        return pickColor(wallpaper);
    }

    public final ColorData pickColor(Bitmap wallpaper, boolean z, Rect rect) {
        Intrinsics.checkNotNullParameter(wallpaper, "wallpaper");
        ScreenType screenTypeCurrentScreenType = currentScreenType();
        if (!z || !isNormalScreen()) {
            return rect == null ? pickColor(wallpaper) : pickColor(wallpaper, rect);
        }
        Log.i("Keyguard-Editor-AutoColorPicker", "pickColor: fromStyleChanged=" + z + ", screenType=" + screenTypeCurrentScreenType.name());
        return null;
    }
}
