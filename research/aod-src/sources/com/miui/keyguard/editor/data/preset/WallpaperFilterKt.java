package com.miui.keyguard.editor.data.preset;

import com.miui.keyguard.editor.EditorApplicationProxy;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.bean.MagicType;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.utils.DeviceUtil;
import java.util.List;
import java.util.function.Predicate;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
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
/* JADX INFO: compiled from: WallpaperFilter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class WallpaperFilterKt {
    public static final Lazy DEFAULT_WALLPAPER_FILTER_DATA$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.WallpaperFilterKt$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return WallpaperFilterKt.DEFAULT_WALLPAPER_FILTER_DATA_delegate$lambda$0();
        }
    });
    public static final Lazy RHOMBUS_WALLPAPER_FILTER_DATA$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.WallpaperFilterKt$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return WallpaperFilterKt.RHOMBUS_WALLPAPER_FILTER_DATA_delegate$lambda$3();
        }
    });
    public static final Lazy DOODLE_WALLPAPER_FILTER_DATA$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.WallpaperFilterKt$$ExternalSyntheticLambda2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return WallpaperFilterKt.DOODLE_WALLPAPER_FILTER_DATA_delegate$lambda$4();
        }
    });

    public static final List DEFAULT_WALLPAPER_FILTER_DATA_delegate$lambda$0() {
        WallpaperFilter wallpaperFilter = new WallpaperFilter(0, R.drawable.kg_icon_wallpaper_effect_raw_filter1, R.string.kg_wallpaper_filter_type_raw, DeviceUtil.INSTANCE.isSupportHierarchyDevices(), false, false, 48, null);
        WallpaperFilter wallpaperFilter2 = new WallpaperFilter(30000, R.drawable.kg_icon_wallpaper_effect_obscure_filter1, R.string.kg_wallpaper_filter_type_obscure, false, false, false, 56, null);
        int i = R.drawable.kg_icon_wallpaper_effect_focus_filter1;
        int i2 = R.string.kg_wallpaper_filter_type_focus;
        WallpaperFilter wallpaperFilter3 = new WallpaperFilter(MagicType.TYPE_FOCUS, i, i2, false, false, false, 56, null);
        WallpaperFilter wallpaperFilter4 = new WallpaperFilter(MagicType.TYPE_FRONT_BACK_1, R.drawable.kg_icon_wallpaper_effect_front_back_filter1, i2, false, false, false, 56, null);
        int i3 = R.drawable.kg_icon_wallpaper_effect_water_filter1;
        int i4 = R.string.kg_wallpaper_filter_type_water;
        return clearNotSupport(CollectionsKt.mutableListOf(wallpaperFilter, wallpaperFilter2, wallpaperFilter3, wallpaperFilter4, new WallpaperFilter(MagicType.TYPE_WATER_1, i3, i4, false, false, false, 56, null), new WallpaperFilter(MagicType.TYPE_WATER_2, R.drawable.kg_icon_wallpaper_effect_water_filter2, i4, false, false, false, 56, null), new WallpaperFilter(MagicType.TYPE_CRYSTALLIZED, R.drawable.kg_icon_wallpaper_effect_crystallized_filter1, R.string.kg_wallpaper_filter_type_crystallized, false, true, false, 40, null), new WallpaperFilter(60000, R.drawable.kg_icon_wallpaper_effect_striped_lens_filter1, R.string.kg_wallpaper_filter_type_striped_lens, false, false, false, 56, null), new WallpaperFilter(10000, R.drawable.kg_icon_wallpaper_effect_glass_filter1, R.string.kg_wallpaper_filter_type_striped_glass_striola, false, true, false, 40, null), new WallpaperFilter(10001, R.drawable.kg_icon_wallpaper_effect_glass_filter2, R.string.kg_wallpaper_filter_type_striped_glass_thick_stripes, false, true, false, 40, null), new WallpaperFilter(10002, R.drawable.kg_icon_wallpaper_effect_glass_filter3, R.string.kg_wallpaper_filter_type_striped_glass_prism, false, true, false, 40, null), new WallpaperFilter(10003, R.drawable.kg_icon_wallpaper_effect_glass_filter4, R.string.kg_wallpaper_filter_type_striped_glass_filament, false, true, false, 40, null), new WallpaperFilter(10004, R.drawable.kg_icon_wallpaper_effect_glass_filter5, R.string.kg_wallpaper_filter_type_striped_glass_ripple, false, true, false, 40, null)));
    }

    public static final List DOODLE_WALLPAPER_FILTER_DATA_delegate$lambda$4() {
        return CollectionsKt.mutableListOf(new WallpaperFilter(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, DeviceUtil.INSTANCE.isSupportHierarchyDevices(), false, true, 16, null));
    }

    public static final List RHOMBUS_WALLPAPER_FILTER_DATA_delegate$lambda$3() {
        WallpaperFilter wallpaperFilter = new WallpaperFilter(0, R.drawable.kg_icon_wallpaper_effect_raw_filter1, R.string.kg_wallpaper_filter_type_raw, false, false, false, 56, null);
        WallpaperFilter wallpaperFilter2 = new WallpaperFilter(30000, R.drawable.kg_icon_wallpaper_effect_obscure_filter1, R.string.kg_wallpaper_filter_type_obscure, false, false, false, 56, null);
        int i = R.drawable.kg_icon_wallpaper_effect_focus_filter1;
        int i2 = R.string.kg_wallpaper_filter_type_focus;
        WallpaperFilter wallpaperFilter3 = new WallpaperFilter(MagicType.TYPE_FOCUS, i, i2, false, false, false, 56, null);
        WallpaperFilter wallpaperFilter4 = new WallpaperFilter(MagicType.TYPE_FRONT_BACK_1, R.drawable.kg_icon_wallpaper_effect_front_back_filter1, i2, false, false, false, 56, null);
        int i3 = R.drawable.kg_icon_wallpaper_effect_water_filter1;
        int i4 = R.string.kg_wallpaper_filter_type_water;
        return clearNotSupport(CollectionsKt.mutableListOf(wallpaperFilter, wallpaperFilter2, wallpaperFilter3, wallpaperFilter4, new WallpaperFilter(MagicType.TYPE_WATER_1, i3, i4, false, false, false, 56, null), new WallpaperFilter(MagicType.TYPE_WATER_2, R.drawable.kg_icon_wallpaper_effect_water_filter2, i4, false, false, false, 56, null), new WallpaperFilter(MagicType.TYPE_CRYSTALLIZED, R.drawable.kg_icon_wallpaper_effect_crystallized_filter1, R.string.kg_wallpaper_filter_type_crystallized, false, true, false, 40, null), new WallpaperFilter(60000, R.drawable.kg_icon_wallpaper_effect_striped_lens_filter1, R.string.kg_wallpaper_filter_type_striped_lens, false, false, false, 56, null), new WallpaperFilter(10000, R.drawable.kg_icon_wallpaper_effect_glass_filter1, R.string.kg_wallpaper_filter_type_striped_glass_striola, false, true, false, 40, null), new WallpaperFilter(10001, R.drawable.kg_icon_wallpaper_effect_glass_filter2, R.string.kg_wallpaper_filter_type_striped_glass_thick_stripes, false, true, false, 40, null), new WallpaperFilter(10002, R.drawable.kg_icon_wallpaper_effect_glass_filter3, R.string.kg_wallpaper_filter_type_striped_glass_prism, false, true, false, 40, null), new WallpaperFilter(10003, R.drawable.kg_icon_wallpaper_effect_glass_filter4, R.string.kg_wallpaper_filter_type_striped_glass_filament, false, true, false, 40, null), new WallpaperFilter(10004, R.drawable.kg_icon_wallpaper_effect_glass_filter5, R.string.kg_wallpaper_filter_type_striped_glass_ripple, false, true, false, 40, null)));
    }

    public static final List clearNotSupport(List list) {
        final Function1 function1 = new Function1() { // from class: com.miui.keyguard.editor.data.preset.WallpaperFilterKt$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                return Boolean.valueOf(WallpaperFilterKt.clearNotSupport$lambda$1((WallpaperFilter) obj));
            }
        };
        list.removeIf(new Predicate() { // from class: com.miui.keyguard.editor.data.preset.WallpaperFilterKt$$ExternalSyntheticLambda4
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return WallpaperFilterKt.clearNotSupport$lambda$2(function1, obj);
            }
        });
        return list;
    }

    public static final boolean clearNotSupport$lambda$1(WallpaperFilter wallpaperFilter) {
        Intrinsics.checkNotNullParameter(wallpaperFilter, "wallpaperFilter");
        return !WallpaperController.Companion.getInstance(EditorApplicationProxy.Companion.getApplication(), false).getCanPreviewEffectList().contains(Integer.valueOf(wallpaperFilter.getFilterId()));
    }

    public static final boolean clearNotSupport$lambda$2(Function1 function1, Object obj) {
        return ((Boolean) function1.invoke(obj)).booleanValue();
    }

    public static final List getDEFAULT_WALLPAPER_FILTER_DATA() {
        return (List) DEFAULT_WALLPAPER_FILTER_DATA$delegate.getValue();
    }

    public static final List getDOODLE_WALLPAPER_FILTER_DATA() {
        return (List) DOODLE_WALLPAPER_FILTER_DATA$delegate.getValue();
    }

    public static final List getRHOMBUS_WALLPAPER_FILTER_DATA() {
        return (List) RHOMBUS_WALLPAPER_FILTER_DATA$delegate.getValue();
    }
}
