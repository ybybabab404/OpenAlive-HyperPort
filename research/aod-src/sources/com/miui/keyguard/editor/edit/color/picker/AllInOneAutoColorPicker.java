package com.miui.keyguard.editor.edit.color.picker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.Log;
import com.miui.clock.module.ClockBean;
import com.miui.keyguard.editor.edit.color.ColorData;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.DualClockManager;
import com.miui.miwallpaper.material.MiuiColorScheme;
import com.miui.miwallpaper.material.utils.PartRectColorUtils;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
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
/* JADX INFO: compiled from: AllInOneAutoColorPicker.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class AllInOneAutoColorPicker extends AutoColorPicker {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public AllInOneAutoColorPicker(Context context, String clockType, ClockBean clockBean) {
        super(context, clockType, clockBean);
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(clockType, "clockType");
    }

    public final Rect convertScreenRectsToBitmapRects(Bitmap bitmap, Rect rect) {
        HashMap map = new HashMap();
        map.put("clock_style_rect", rect);
        try {
            Map mapConvertScreenRectsToBitmapRects = PartRectColorUtils.convertScreenRectsToBitmapRects(DeviceUtil.INSTANCE.createConfigurationContext(getContext()), map, bitmap.getWidth(), bitmap.getHeight(), WallpaperController.Companion.getCurrentWallpaperWhich(getContext()).getWhich());
            Intrinsics.checkNotNullExpressionValue(mapConvertScreenRectsToBitmapRects, "convertScreenRectsToBitmapRects(...)");
            Rect rect2 = (Rect) mapConvertScreenRectsToBitmapRects.get("clock_style_rect");
            return rect2 == null ? new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()) : rect2;
        } catch (Throwable th) {
            Log.w("AllInOneAutoColorPicker", "convertScreenRectsToBitmapRects failed, fallback to full wallpaper rect", th);
            return new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        }
    }

    @Override // com.miui.keyguard.editor.edit.color.picker.AutoColorPicker
    public ColorData pickColor(Bitmap wallpaper, Rect rect) {
        Intrinsics.checkNotNullParameter(wallpaper, "wallpaper");
        if (DualClockManager.isDualClockAvailable(getContext())) {
            return super.pickColor(wallpaper, rect);
        }
        if (rect == null || rect.isEmpty()) {
            Log.w("AllInOneAutoColorPicker", "pickColor: Clock view not created yet, returning null");
            return null;
        }
        Rect rectConvertScreenRectsToBitmapRects = convertScreenRectsToBitmapRects(wallpaper, rect);
        boolean isDeepWallpaper = MiuiColorScheme.getIsDeepWallpaper(wallpaper, false, rectConvertScreenRectsToBitmapRects);
        Map miuiWallpaperPalette = MiuiColorScheme.getMiuiWallpaperPalette(wallpaper, rectConvertScreenRectsToBitmapRects);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        if (miuiWallpaperPalette != null) {
            for (Map.Entry entry : miuiWallpaperPalette.entrySet()) {
                String str = (String) entry.getKey();
                Integer num = (Integer) entry.getValue();
                if (str != null && num != null) {
                    linkedHashMap.put(str, num);
                }
            }
        }
        Log.d("AllInOneAutoColorPicker", "pickColor: screenRect=" + rect + ", wallpaperRect=" + rectConvertScreenRectsToBitmapRects + "， isDarkWallpaper: " + isDeepWallpaper);
        return new ColorData(0, 0, 0, 0, !isDeepWallpaper, false, linkedHashMap, false, null, 0, 943, null);
    }
}
