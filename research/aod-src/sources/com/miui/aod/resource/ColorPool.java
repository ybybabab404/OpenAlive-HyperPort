package com.miui.aod.resource;

import android.text.TextUtils;
import androidx.collection.ArrayMap;
import com.miui.aod.R;
import com.miui.aod.components.DrawableData;
import com.miui.aod.widget.SunSelector;
import java.util.Map;
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
/* JADX INFO: compiled from: ColorPool.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class ColorPool {
    public static final ColorPool INSTANCE = new ColorPool();
    public static final Map sClockMaskXMap;
    public static final Map sClockMaskYMap;
    public static final Map sColorMap;
    public static final Map sIconMaskMap;

    static {
        ArrayMap arrayMap = new ArrayMap();
        sColorMap = arrayMap;
        ArrayMap arrayMap2 = new ArrayMap();
        sIconMaskMap = arrayMap2;
        ArrayMap arrayMap3 = new ArrayMap();
        sClockMaskXMap = arrayMap3;
        ArrayMap arrayMap4 = new ArrayMap();
        sClockMaskYMap = arrayMap4;
        arrayMap.put("light_purple", Integer.valueOf(R.color.aod_light_purple_x));
        arrayMap.put("light_blue", Integer.valueOf(R.color.aod_light_blue_x));
        arrayMap.put("light_orange", Integer.valueOf(R.color.aod_light_orange_x));
        arrayMap.put("wave_4_clock_mask", Integer.valueOf(R.color.wave_forth_clock_mask));
        arrayMap2.put("white", Integer.valueOf(R.drawable.notification_icons_mask));
        arrayMap2.put("pink", Integer.valueOf(R.drawable.icon_pink_x));
        arrayMap2.put("blue", Integer.valueOf(R.drawable.icon_blue_x));
        arrayMap3.put("pink", Integer.valueOf(R.drawable.aod_num_big_pink_x));
        arrayMap3.put("blue", Integer.valueOf(R.drawable.aod_num_big_blue_x));
        arrayMap3.put("light_purple", Integer.valueOf(R.drawable.light_purple_x));
        arrayMap3.put("light_blue", Integer.valueOf(R.drawable.light_blue_x));
        arrayMap3.put("light_orange", Integer.valueOf(R.drawable.light_orange_x));
        arrayMap4.put("pink", Integer.valueOf(R.drawable.aod_num_big_pink_y));
        arrayMap4.put("blue", Integer.valueOf(R.drawable.aod_num_big_blue_y));
    }

    public static final int getClockColor(String str) {
        Map map = sColorMap;
        if (map.containsKey(str)) {
            Object obj = map.get(str);
            Intrinsics.checkNotNull(obj);
            return ((Number) obj).intValue();
        }
        if (AodDrawables.containsDrawableData(str)) {
            return AodDrawables.getDrawableDataByName(str).mResId;
        }
        return 0;
    }

    public static final int getClockMaskX(String str) {
        Map map = sClockMaskXMap;
        if (map.containsKey(str)) {
            Object obj = map.get(str);
            Intrinsics.checkNotNull(obj);
            return ((Number) obj).intValue();
        }
        if (AodDrawables.containsDrawableData(str)) {
            return AodDrawables.getDrawableDataByName(str).mResId;
        }
        return 0;
    }

    public static final int getClockMaskY(String str) {
        Map map = sClockMaskYMap;
        if (map.containsKey(str)) {
            Object obj = map.get(str);
            Intrinsics.checkNotNull(obj);
            return ((Number) obj).intValue();
        }
        if (AodDrawables.containsDrawableData(str)) {
            return AodDrawables.getDrawableDataByName(str).mResId;
        }
        return 0;
    }

    public static final int getGradientDrawableMask() {
        return ((DrawableData) AodDrawables.s24GradientDrawableList.get(SunSelector.getDrawableIndex())).mResId;
    }

    public static final int getIconMask(String str) {
        if (TextUtils.isEmpty(str) || Intrinsics.areEqual(str, "white") || Intrinsics.areEqual(str, "aod_pure_color_1")) {
            return R.drawable.notification_icons_mask;
        }
        Map map = sIconMaskMap;
        if (!map.containsKey(str)) {
            return AodDrawables.containsDrawableData(str) ? AodDrawables.getDrawableDataByName(str).mResId : R.drawable.notification_icons_mask;
        }
        Object obj = map.get(str);
        Intrinsics.checkNotNull(obj);
        return ((Number) obj).intValue();
    }
}
