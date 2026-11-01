package com.miui.keyguard.editor.edit.clock;

import com.miui.keyguard.editor.R;
import com.xiaomi.onetrack.g.d;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import miuix.animation.utils.EaseManager;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ClassicTextStyleEditor.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ClassicTextStyleEditorKt {
    public static final List CLOCK_AREA_DATA;
    public static final List CONTENT_AREA_DATA;
    public static final List TEXT_AREA_DATA;

    static {
        int i = R.string.kg_classic_item_empty;
        ClassicDataType classicDataType = ClassicDataType.TEXT;
        ClassicSelectorBean classicSelectorBean = new ClassicSelectorBean(i, 0, classicDataType, 0, 8, null);
        int i2 = R.string.kg_classic_item_date;
        ClassicSelectorBean classicSelectorBean2 = new ClassicSelectorBean(i2, 100, classicDataType, 0, 8, null);
        ClassicSelectorBean classicSelectorBean3 = new ClassicSelectorBean(R.string.kg_classic_item_lunar_date, 101, classicDataType, 0, 8, null);
        classicSelectorBean3.updateFlags(true, 1);
        Unit unit = Unit.INSTANCE;
        ClassicSelectorBean classicSelectorBean4 = new ClassicSelectorBean(R.string.kg_classic_item_lunar_date1, 103, classicDataType, 0, 8, null);
        classicSelectorBean4.updateFlags(true, 1);
        int i3 = R.string.kg_classic_item_temperature;
        ClassicDataType classicDataType2 = ClassicDataType.WEATHER;
        ClassicSelectorBean classicSelectorBean5 = new ClassicSelectorBean(i3, 400, classicDataType2, 0, 8, null);
        int i4 = R.string.kg_classic_item_air_quality;
        ClassicSelectorBean classicSelectorBean6 = new ClassicSelectorBean(i4, d.b, classicDataType2, 0, 8, null);
        int i5 = R.string.kg_classic_item_sunrise_or_sunset;
        ClassicSelectorBean classicSelectorBean7 = new ClassicSelectorBean(i5, d.c, classicDataType2, 0, 8, null);
        int i6 = R.string.kg_classic_item_ultraviolet_ray;
        ClassicSelectorBean classicSelectorBean8 = new ClassicSelectorBean(i6, 405, classicDataType2, 0, 8, null);
        int i7 = R.string.kg_classic_item_somatosensory;
        ClassicSelectorBean classicSelectorBean9 = new ClassicSelectorBean(i7, 406, classicDataType2, 0, 8, null);
        int i8 = R.string.kg_classic_item_wind;
        ClassicSelectorBean classicSelectorBean10 = new ClassicSelectorBean(i8, 407, classicDataType2, 0, 8, null);
        int i9 = R.string.kg_classic_item_humidity;
        ClassicSelectorBean classicSelectorBean11 = new ClassicSelectorBean(i9, 408, classicDataType2, 0, 8, null);
        int i10 = R.string.kg_classic_item_step_number;
        ClassicDataType classicDataType3 = ClassicDataType.HEALTH;
        ClassicSelectorBean classicSelectorBean12 = new ClassicSelectorBean(i10, 500, classicDataType3, 0, 8, null);
        int i11 = R.string.kg_classic_item_stand;
        TEXT_AREA_DATA = CollectionsKt.listOf((Object[]) new ClassicSelectorBean[]{classicSelectorBean, classicSelectorBean2, classicSelectorBean3, classicSelectorBean4, classicSelectorBean5, classicSelectorBean6, classicSelectorBean7, classicSelectorBean8, classicSelectorBean9, classicSelectorBean10, classicSelectorBean11, classicSelectorBean12, new ClassicSelectorBean(i11, 504, classicDataType3, 0, 8, null)});
        int i12 = R.string.kg_classic_item_number_clock;
        ClassicSelectorBean classicSelectorBean13 = new ClassicSelectorBean(i12, 300, classicDataType, 0, 8, null);
        int i13 = R.string.kg_classic_item_chinese_clock;
        ClassicSelectorBean classicSelectorBean14 = new ClassicSelectorBean(i13, 301, classicDataType, 0, 8, null);
        classicSelectorBean14.updateFlags(true, 1);
        int i14 = R.string.kg_classic_item_number_hour;
        ClassicSelectorBean classicSelectorBean15 = new ClassicSelectorBean(i14, 302, classicDataType, 0, 8, null);
        int i15 = R.string.kg_classic_item_number_min;
        ClassicSelectorBean classicSelectorBean16 = new ClassicSelectorBean(i15, 303, classicDataType, 0, 8, null);
        int i16 = R.string.kg_classic_item_chinese_hour;
        ClassicSelectorBean classicSelectorBean17 = new ClassicSelectorBean(i16, 304, classicDataType, 0, 8, null);
        classicSelectorBean17.updateFlags(true, 1);
        int i17 = R.string.kg_classic_item_chinese_min;
        ClassicSelectorBean classicSelectorBean18 = new ClassicSelectorBean(i17, 305, classicDataType, 0, 8, null);
        classicSelectorBean18.updateFlags(true, 1);
        CLOCK_AREA_DATA = CollectionsKt.listOf((Object[]) new ClassicSelectorBean[]{classicSelectorBean13, classicSelectorBean14, classicSelectorBean15, classicSelectorBean16, classicSelectorBean17, classicSelectorBean18});
        ClassicSelectorBean classicSelectorBean19 = new ClassicSelectorBean(i, 0, classicDataType, 0, 8, null);
        ClassicSelectorBean classicSelectorBean20 = new ClassicSelectorBean(i12, 300, classicDataType, 0, 8, null);
        ClassicSelectorBean classicSelectorBean21 = new ClassicSelectorBean(i13, 301, classicDataType, 0, 8, null);
        classicSelectorBean21.updateFlags(true, 1);
        ClassicSelectorBean classicSelectorBean22 = new ClassicSelectorBean(i14, 302, classicDataType, 0, 8, null);
        ClassicSelectorBean classicSelectorBean23 = new ClassicSelectorBean(i15, 303, classicDataType, 0, 8, null);
        ClassicSelectorBean classicSelectorBean24 = new ClassicSelectorBean(i16, 304, classicDataType, 0, 8, null);
        classicSelectorBean24.updateFlags(true, 1);
        ClassicSelectorBean classicSelectorBean25 = new ClassicSelectorBean(i17, 305, classicDataType, 0, 8, null);
        classicSelectorBean25.updateFlags(true, 1);
        ClassicSelectorBean classicSelectorBean26 = new ClassicSelectorBean(i2, 200, classicDataType, 0, 8, null);
        ClassicSelectorBean classicSelectorBean27 = new ClassicSelectorBean(i2, 202, classicDataType, 0, 8, null);
        ClassicSelectorBean classicSelectorBean28 = new ClassicSelectorBean(i2, 209, classicDataType, 0, 8, null);
        classicSelectorBean28.updateFlags(true, 1);
        ClassicSelectorBean classicSelectorBean29 = new ClassicSelectorBean(i2, EaseManager.EaseStyleDef.PERLIN, classicDataType, 0, 8, null);
        ClassicSelectorBean classicSelectorBean30 = new ClassicSelectorBean(i2, 210, classicDataType, 0, 8, null);
        classicSelectorBean30.updateFlags(true, 1);
        ClassicSelectorBean classicSelectorBean31 = new ClassicSelectorBean(i2, 203, classicDataType, 0, 8, null);
        ClassicSelectorBean classicSelectorBean32 = new ClassicSelectorBean(i2, 205, classicDataType, 0, 8, null);
        ClassicSelectorBean classicSelectorBean33 = new ClassicSelectorBean(i2, 204, classicDataType, 0, 8, null);
        ClassicSelectorBean classicSelectorBean34 = new ClassicSelectorBean(R.string.kg_classic_item_week, 206, classicDataType, 0, 8, null);
        ClassicSelectorBean classicSelectorBean35 = new ClassicSelectorBean(R.string.kg_classic_item_month, 207, classicDataType, 0, 8, null);
        ClassicSelectorBean classicSelectorBean36 = new ClassicSelectorBean(R.string.kg_classic_item_day, 208, classicDataType, 0, 8, null);
        ClassicSelectorBean classicSelectorBean37 = new ClassicSelectorBean(R.string.kg_classic_item_lunar_month_day, 211, classicDataType, 0, 8, null);
        classicSelectorBean37.updateFlags(true, 1);
        ClassicSelectorBean classicSelectorBean38 = new ClassicSelectorBean(R.string.kg_classic_item_lunar_year, 212, classicDataType, 0, 8, null);
        classicSelectorBean38.updateFlags(true, 1);
        ClassicSelectorBean classicSelectorBean39 = new ClassicSelectorBean(i3, 400, classicDataType2, 0, 8, null);
        ClassicSelectorBean classicSelectorBean40 = new ClassicSelectorBean(i4, d.b, classicDataType2, 0, 8, null);
        ClassicSelectorBean classicSelectorBean41 = new ClassicSelectorBean(R.string.kg_classic_item_rainfall_probability, 402, classicDataType2, 0, 8, null);
        classicSelectorBean41.updateFlags(true, 1);
        CONTENT_AREA_DATA = CollectionsKt.listOf((Object[]) new ClassicSelectorBean[]{classicSelectorBean19, classicSelectorBean20, classicSelectorBean21, classicSelectorBean22, classicSelectorBean23, classicSelectorBean24, classicSelectorBean25, classicSelectorBean26, classicSelectorBean27, classicSelectorBean28, classicSelectorBean29, classicSelectorBean30, classicSelectorBean31, classicSelectorBean32, classicSelectorBean33, classicSelectorBean34, classicSelectorBean35, classicSelectorBean36, classicSelectorBean37, classicSelectorBean38, classicSelectorBean39, classicSelectorBean40, classicSelectorBean41, new ClassicSelectorBean(i5, 403, classicDataType2, 0, 8, null), new ClassicSelectorBean(i6, 405, classicDataType2, 0, 8, null), new ClassicSelectorBean(i7, 406, classicDataType2, 0, 8, null), new ClassicSelectorBean(i8, 407, classicDataType2, 0, 8, null), new ClassicSelectorBean(i9, 408, classicDataType2, 0, 8, null), new ClassicSelectorBean(R.string.kg_classic_item_air_pressure, 409, classicDataType2, 0, 8, null), new ClassicSelectorBean(i10, 500, classicDataType3, 0, 8, null), new ClassicSelectorBean(i11, 504, classicDataType3, 0, 8, null)});
    }
}
