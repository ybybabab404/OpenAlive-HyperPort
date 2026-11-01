package com.miui.keyguard.editor.data.preset;

import android.os.SystemProperties;
import com.miui.clock.utils.DeviceConfig;
import com.miui.keyguard.editor.R;
import com.miui.keyguard.editor.data.util.DataUtil;
import java.util.ArrayList;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
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
/* JADX INFO: compiled from: FontFilter.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class FontFilterKt {
    public static final FontFilter ALL_IN_ONE_FONT_FILTER;
    public static final Lazy CLASSIC_MAX_TEMPLATE_FONT_FILTER$delegate;
    public static final Lazy CLASSIC_PLUS_TEMPLATE_FONT_FILTER$delegate;
    public static final Lazy CLASSIC_TEMPLATE_FONT_FILTER$delegate;
    public static final int COMPUTILITYLEVELV2;
    public static final FilterColor[] DEFAULT_FILTER_COLORS;
    public static final Map[] DEFAULT_FILTER_SELECT_TYPE_INFO;
    public static final FilterTypeSelectInfo DEFAULT_UNIQUE_FILTER_SELECT_TYPE_INFO;
    public static final Lazy DOODLE_TEMPLATE_FONT_FILTER$delegate;
    public static final Lazy EASTERN_ART_A_STYLE_1_FONT_FILTER$delegate;
    public static final Lazy EASTERN_ART_A_STYLE_2_FONT_FILTER$delegate;
    public static final Lazy EASTERN_ART_B_STYLE_1_FONT_FILTER$delegate;
    public static final Lazy EASTERN_ART_B_STYLE_2_FONT_FILTER$delegate;
    public static final Map FILTER_SUPPORT_INFO;
    public static final Lazy MAGAZINE_A_B_TEMPLATE_FONT_FILTER$delegate;
    public static final Lazy MAGAZINE_C_STYLE_1_FONT_FILTER$delegate;
    public static final Lazy MAGAZINE_C_STYLE_2_FONT_FILTER$delegate;
    public static final Lazy MAGAZINE_C_STYLE_3_FONT_FILTER$delegate;
    public static final Lazy MAGAZINE_C_STYLE_4_FONT_FILTER$delegate;
    public static final Lazy MAGAZINE_C_STYLE_5_FONT_FILTER$delegate;
    public static final Lazy OVERSIZE_A_FONT_FILTER$delegate;
    public static final Lazy OVERSIZE_B_FONT_FILTER$delegate;
    public static final Lazy PAD_A_FONT_FILTER$delegate;
    public static final Lazy PAD_B_FONT_FILTER$delegate;
    public static final Lazy PAD_C_FONT_FILTER$delegate;
    public static final Lazy PETS_FONT_FILTER$delegate;
    public static final Map PRESET_FILTER_COLOR_SELECT_INFO;
    public static final Lazy RHOMBUS_TEMPLATE_FONT_FILTER$delegate;
    public static final boolean SUPPORT_EDITOR_VARIABLE_FONT;
    public static final boolean SUPPORT_LOCKSCREEN_VARIABLE_FONT;
    public static final Lazy TRADITIONAL_CHINA_C_FONT_FILTER$delegate;

    static {
        int i = SystemProperties.getInt("persist.sys.computilityV2.devicelevel", -1);
        COMPUTILITYLEVELV2 = i;
        SUPPORT_LOCKSCREEN_VARIABLE_FONT = DeviceConfig.supportVariableFont();
        SUPPORT_EDITOR_VARIABLE_FONT = i > 1;
        Boolean bool = Boolean.TRUE;
        FILTER_SUPPORT_INFO = MapsKt.mapOf(TuplesKt.to(0, bool), TuplesKt.to(1, bool), TuplesKt.to(5, Boolean.valueOf(DeviceConfig.supportGlassEffect())), TuplesKt.to(2, Boolean.valueOf(DeviceConfig.supportDiffEffect())), TuplesKt.to(4, Boolean.valueOf(DeviceConfig.supportGradientEffect())), TuplesKt.to(3, Boolean.valueOf(DeviceConfig.supportGlowEffect())));
        PRESET_FILTER_COLOR_SELECT_INFO = MapsKt.mapOf(TuplesKt.to("rhombus", new PresetColorInfo(0, 0, 0, 0, 0, 31, null)), TuplesKt.to("classic", new PresetColorInfo(0, 0, 0, 0, 0, 31, null)), TuplesKt.to("classic_plus", new PresetColorInfo(0, 0, 0, 0, 0, 31, null)), TuplesKt.to("classic_max", new PresetColorInfo(0, 0, 0, 0, 0, 31, null)), TuplesKt.to("magazine_a", new PresetColorInfo(0, 0, 0, 0, 0, 31, null)), TuplesKt.to("magazine_b", new PresetColorInfo(0, 0, 0, 0, 0, 31, null)), TuplesKt.to("magazine_c", new PresetColorInfo(0, 0, 0, 0, 0, 31, null)), TuplesKt.to("doodle", new PresetColorInfo(0, 0, 0, 0, 0, 31, null)), TuplesKt.to("smart_frame", new PresetColorInfo(0, 0, 0, 0, 0, 31, null)), TuplesKt.to("eastern_a", new PresetColorInfo(0, 0, 0, 0, 0, 31, null)), TuplesKt.to("eastern_b", new PresetColorInfo(0, 0, 0, 0, 0, 31, null)), TuplesKt.to("eastern_c", new PresetColorInfo(0, 0, 0, 0, 0, 31, null)), TuplesKt.to("oversize_a", new PresetColorInfo(0, 0, 0, 0, 0, 31, null)), TuplesKt.to("oversize_b", new PresetColorInfo(0, 0, 0, 0, 0, 31, null)), TuplesKt.to("pad_exclusive_a", new PresetColorInfo(0, 0, 0, 0, 0, 31, null)), TuplesKt.to("pad_exclusive_b", new PresetColorInfo(0, 0, 0, 0, 0, 31, null)), TuplesKt.to("pad_exclusive_c", new PresetColorInfo(0, 0, 0, 0, 0, 31, null)), TuplesKt.to("all_in_one", new PresetColorInfo(0, 0, 0, 0, 0, 31, null)));
        DEFAULT_FILTER_SELECT_TYPE_INFO = new Map[]{MapsKt.mapOf(TuplesKt.to(0, new FilterTypeSelectInfo(0, 0, 2, null)), TuplesKt.to(1, new FilterTypeSelectInfo(0, 0, 2, null)), TuplesKt.to(2, new FilterTypeSelectInfo(0, 0, 2, null)), TuplesKt.to(4, new FilterTypeSelectInfo(0, 0, 2, null)), TuplesKt.to(3, new FilterTypeSelectInfo(1, 0, 2, null))), MapsKt.mapOf(TuplesKt.to(0, new FilterTypeSelectInfo(0, 0, 2, null)), TuplesKt.to(1, new FilterTypeSelectInfo(0, 0, 2, null)), TuplesKt.to(2, new FilterTypeSelectInfo(0, 0, 2, null)), TuplesKt.to(4, new FilterTypeSelectInfo(0, 0, 2, null)), TuplesKt.to(3, new FilterTypeSelectInfo(1, 0, 2, null)))};
        DEFAULT_UNIQUE_FILTER_SELECT_TYPE_INFO = new FilterTypeSelectInfo(0, 0, 2, null);
        FilterColor[] filterColorArr = {new FilterColor(0, 0, 0, R.drawable.kg_ic_auto_pick_color, 0, false, false, 70, null), new FilterColor(1, -1, -1, 0, 0, false, false, 88, null), new FilterColor(2, -5066062, -8355712, 0, 0, false, false, com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMajor, null), new FilterColor(3, -146496, -378834, 0, 0, false, false, com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMajor, null), new FilterColor(4, -11596, -38395, 0, 0, false, false, com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMajor, null), new FilterColor(5, -7500, -24827, 0, 0, false, false, com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMajor, null), new FilterColor(6, -2894, -9984, 0, 0, false, false, com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMajor, null), new FilterColor(7, -2099533, -7020029, 0, 0, false, false, com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMajor, null), new FilterColor(8, -4460348, -14824134, 0, 0, false, false, com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMajor, null), new FilterColor(9, -4722705, -15742262, 0, 0, false, false, com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMajor, null), new FilterColor(10, -4007425, -13335809, 0, 0, false, false, com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMajor, null), new FilterColor(11, -2698755, -8951815, 0, 0, false, false, com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMajor, null), new FilterColor(12, -473377, -1621867, 0, 0, false, false, com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMajor, null), new FilterColor(13, -16777216, 0, R.drawable.kg_ic_color_picker, 2, false, false, 68, null)};
        DEFAULT_FILTER_COLORS = filterColorArr;
        CLASSIC_TEMPLATE_FONT_FILTER$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.FontFilterKt$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FontFilterKt.CLASSIC_TEMPLATE_FONT_FILTER_delegate$lambda$4();
            }
        });
        CLASSIC_PLUS_TEMPLATE_FONT_FILTER$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.FontFilterKt$$ExternalSyntheticLambda11
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FontFilterKt.CLASSIC_PLUS_TEMPLATE_FONT_FILTER_delegate$lambda$7();
            }
        });
        CLASSIC_MAX_TEMPLATE_FONT_FILTER$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.FontFilterKt$$ExternalSyntheticLambda14
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FontFilterKt.CLASSIC_MAX_TEMPLATE_FONT_FILTER_delegate$lambda$12();
            }
        });
        RHOMBUS_TEMPLATE_FONT_FILTER$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.FontFilterKt$$ExternalSyntheticLambda15
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FontFilterKt.RHOMBUS_TEMPLATE_FONT_FILTER_delegate$lambda$17();
            }
        });
        MAGAZINE_A_B_TEMPLATE_FONT_FILTER$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.FontFilterKt$$ExternalSyntheticLambda16
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FontFilterKt.MAGAZINE_A_B_TEMPLATE_FONT_FILTER_delegate$lambda$22();
            }
        });
        MAGAZINE_C_STYLE_1_FONT_FILTER$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.FontFilterKt$$ExternalSyntheticLambda17
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FontFilterKt.MAGAZINE_C_STYLE_1_FONT_FILTER_delegate$lambda$27();
            }
        });
        MAGAZINE_C_STYLE_2_FONT_FILTER$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.FontFilterKt$$ExternalSyntheticLambda18
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FontFilterKt.MAGAZINE_C_STYLE_2_FONT_FILTER_delegate$lambda$30();
            }
        });
        MAGAZINE_C_STYLE_3_FONT_FILTER$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.FontFilterKt$$ExternalSyntheticLambda19
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FontFilterKt.MAGAZINE_C_STYLE_3_FONT_FILTER_delegate$lambda$35();
            }
        });
        MAGAZINE_C_STYLE_4_FONT_FILTER$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.FontFilterKt$$ExternalSyntheticLambda20
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FontFilterKt.MAGAZINE_C_STYLE_4_FONT_FILTER_delegate$lambda$40();
            }
        });
        MAGAZINE_C_STYLE_5_FONT_FILTER$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.FontFilterKt$$ExternalSyntheticLambda21
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FontFilterKt.MAGAZINE_C_STYLE_5_FONT_FILTER_delegate$lambda$43();
            }
        });
        DOODLE_TEMPLATE_FONT_FILTER$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.FontFilterKt$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FontFilterKt.DOODLE_TEMPLATE_FONT_FILTER_delegate$lambda$44();
            }
        });
        EASTERN_ART_A_STYLE_1_FONT_FILTER$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.FontFilterKt$$ExternalSyntheticLambda2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FontFilterKt.EASTERN_ART_A_STYLE_1_FONT_FILTER_delegate$lambda$49();
            }
        });
        EASTERN_ART_A_STYLE_2_FONT_FILTER$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.FontFilterKt$$ExternalSyntheticLambda3
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FontFilterKt.EASTERN_ART_A_STYLE_2_FONT_FILTER_delegate$lambda$54();
            }
        });
        EASTERN_ART_B_STYLE_1_FONT_FILTER$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.FontFilterKt$$ExternalSyntheticLambda4
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FontFilterKt.EASTERN_ART_B_STYLE_1_FONT_FILTER_delegate$lambda$57();
            }
        });
        EASTERN_ART_B_STYLE_2_FONT_FILTER$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.FontFilterKt$$ExternalSyntheticLambda5
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FontFilterKt.EASTERN_ART_B_STYLE_2_FONT_FILTER_delegate$lambda$62();
            }
        });
        TRADITIONAL_CHINA_C_FONT_FILTER$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.FontFilterKt$$ExternalSyntheticLambda6
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FontFilterKt.TRADITIONAL_CHINA_C_FONT_FILTER_delegate$lambda$67();
            }
        });
        OVERSIZE_A_FONT_FILTER$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.FontFilterKt$$ExternalSyntheticLambda7
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FontFilterKt.OVERSIZE_A_FONT_FILTER_delegate$lambda$72();
            }
        });
        OVERSIZE_B_FONT_FILTER$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.FontFilterKt$$ExternalSyntheticLambda8
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FontFilterKt.OVERSIZE_B_FONT_FILTER_delegate$lambda$77();
            }
        });
        PAD_A_FONT_FILTER$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.FontFilterKt$$ExternalSyntheticLambda9
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FontFilterKt.PAD_A_FONT_FILTER_delegate$lambda$82();
            }
        });
        PAD_B_FONT_FILTER$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.FontFilterKt$$ExternalSyntheticLambda10
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FontFilterKt.PAD_B_FONT_FILTER_delegate$lambda$87();
            }
        });
        PAD_C_FONT_FILTER$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.FontFilterKt$$ExternalSyntheticLambda12
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FontFilterKt.PAD_C_FONT_FILTER_delegate$lambda$92();
            }
        });
        PETS_FONT_FILTER$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.data.preset.FontFilterKt$$ExternalSyntheticLambda13
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return FontFilterKt.PETS_FONT_FILTER_delegate$lambda$97();
            }
        });
        Integer[] numArr = {Integer.valueOf(R.string.kg_font_filter_object_label_hour), Integer.valueOf(R.string.kg_font_filter_object_label_minute)};
        int i2 = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil = DataUtil.INSTANCE;
        FilterGroup[] filterGroupArr = {new FilterGroup(0, i2, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null), new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null), new FilterGroup(5, R.string.kg_font_filter_group_label_glass, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null), new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null)};
        for (int i3 = 0; i3 < 4; i3++) {
            FilterGroup filterGroup = filterGroupArr[i3];
            Boolean bool2 = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup.getFilterId()));
            filterGroup.setSupport(bool2 != null ? bool2.booleanValue() : true);
        }
        ArrayList arrayList = new ArrayList();
        for (int i4 = 0; i4 < 4; i4++) {
            FilterGroup filterGroup2 = filterGroupArr[i4];
            if (filterGroup2.isSupport()) {
                arrayList.add(filterGroup2);
            }
        }
        FilterGroup[] filterGroupArr2 = (FilterGroup[]) arrayList.toArray(new FilterGroup[0]);
        int i5 = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil2 = DataUtil.INSTANCE;
        FilterColor[] filterColorArr2 = DEFAULT_FILTER_COLORS;
        FilterGroup[] filterGroupArr3 = {new FilterGroup(0, i5, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null), new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null), new FilterGroup(5, R.string.kg_font_filter_group_label_glass, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null), new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null)};
        for (int i6 = 0; i6 < 4; i6++) {
            FilterGroup filterGroup3 = filterGroupArr3[i6];
            Boolean bool3 = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup3.getFilterId()));
            filterGroup3.setSupport(bool3 != null ? bool3.booleanValue() : true);
        }
        ArrayList arrayList2 = new ArrayList();
        for (int i7 = 0; i7 < 4; i7++) {
            FilterGroup filterGroup4 = filterGroupArr3[i7];
            if (filterGroup4.isSupport()) {
                arrayList2.add(filterGroup4);
            }
        }
        ALL_IN_ONE_FONT_FILTER = new FontFilter(numArr, new FilterGroup[][]{filterGroupArr2, (FilterGroup[]) arrayList2.toArray(new FilterGroup[0])}, false, 4, null);
    }

    public static final FontFilter CLASSIC_MAX_TEMPLATE_FONT_FILTER_delegate$lambda$12() {
        Integer[] numArr = {Integer.valueOf(R.string.kg_classic_item_type_time), Integer.valueOf(R.string.kg_font_filter_rhombus_secondary_label)};
        int i = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil = DataUtil.INSTANCE;
        FilterColor[] filterColorArr = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup = new FilterGroup(0, i, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup2 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup3 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup4 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor : filterGroup4.getColorData()) {
            if (filterColor.getColorId() == 0 || filterColor.getColorId() == 13) {
                filterColor.setSupportSelect(false);
            }
            filterColor.setMutable(false);
        }
        Unit unit = Unit.INSTANCE;
        FilterGroup[] filterGroupArr = {filterGroup, filterGroup2, filterGroup3, filterGroup4};
        for (int i2 = 0; i2 < 4; i2++) {
            FilterGroup filterGroup5 = filterGroupArr[i2];
            Boolean bool = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup5.getFilterId()));
            filterGroup5.setSupport(bool != null ? bool.booleanValue() : true);
        }
        Unit unit2 = Unit.INSTANCE;
        int i3 = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil2 = DataUtil.INSTANCE;
        FilterColor[] filterColorArr2 = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup6 = new FilterGroup(0, i3, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup7 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup8 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup9 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor2 : filterGroup9.getColorData()) {
            if (filterColor2.getColorId() == 0 || filterColor2.getColorId() == 13) {
                filterColor2.setSupportSelect(false);
            }
            filterColor2.setMutable(false);
        }
        Unit unit3 = Unit.INSTANCE;
        FilterGroup[] filterGroupArr2 = {filterGroup6, filterGroup7, filterGroup8, filterGroup9};
        for (int i4 = 0; i4 < 4; i4++) {
            FilterGroup filterGroup10 = filterGroupArr2[i4];
            Boolean bool2 = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup10.getFilterId()));
            filterGroup10.setSupport(bool2 != null ? bool2.booleanValue() : true);
        }
        Unit unit4 = Unit.INSTANCE;
        return new FontFilter(numArr, new FilterGroup[][]{filterGroupArr, filterGroupArr2}, false, 4, null);
    }

    public static final FontFilter CLASSIC_PLUS_TEMPLATE_FONT_FILTER_delegate$lambda$7() {
        Integer[] numArr = new Integer[0];
        int i = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil = DataUtil.INSTANCE;
        FilterColor[] filterColorArr = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup = new FilterGroup(0, i, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup2 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup3 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup4 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor : filterGroup4.getColorData()) {
            if (filterColor.getColorId() == 0 || filterColor.getColorId() == 13) {
                filterColor.setSupportSelect(false);
            }
            filterColor.setMutable(false);
        }
        Unit unit = Unit.INSTANCE;
        FilterGroup[] filterGroupArr = {filterGroup, filterGroup2, filterGroup3, filterGroup4};
        for (int i2 = 0; i2 < 4; i2++) {
            FilterGroup filterGroup5 = filterGroupArr[i2];
            Boolean bool = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup5.getFilterId()));
            filterGroup5.setSupport(bool != null ? bool.booleanValue() : true);
        }
        Unit unit2 = Unit.INSTANCE;
        return new FontFilter(numArr, new FilterGroup[][]{filterGroupArr}, false, 4, null);
    }

    public static final FontFilter CLASSIC_TEMPLATE_FONT_FILTER_delegate$lambda$4() {
        Integer[] numArr = {Integer.valueOf(R.string.kg_font_filter_classic_primary_label), Integer.valueOf(R.string.kg_font_filter_classic_secondary_label)};
        int i = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil = DataUtil.INSTANCE;
        FilterColor[] filterColorArr = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup = new FilterGroup(0, i, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup2 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup3 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup4 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 16, null);
        for (FilterColor filterColor : filterGroup4.getColorData()) {
            if (filterColor.getColorId() == 0 || filterColor.getColorId() == 13) {
                filterColor.setSupportSelect(false);
            }
            filterColor.setMutable(false);
        }
        Unit unit = Unit.INSTANCE;
        FilterGroup[] filterGroupArr = {filterGroup, filterGroup2, filterGroup3, filterGroup4};
        for (int i2 = 0; i2 < 4; i2++) {
            FilterGroup filterGroup5 = filterGroupArr[i2];
            Boolean bool = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup5.getFilterId()));
            filterGroup5.setSupport(bool != null ? bool.booleanValue() : true);
        }
        Unit unit2 = Unit.INSTANCE;
        int i3 = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil2 = DataUtil.INSTANCE;
        FilterColor[] filterColorArr2 = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup6 = new FilterGroup(0, i3, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup7 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup8 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup9 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 16, null);
        for (FilterColor filterColor2 : filterGroup9.getColorData()) {
            if (filterColor2.getColorId() == 0 || filterColor2.getColorId() == 13) {
                filterColor2.setSupportSelect(false);
            }
            filterColor2.setMutable(false);
        }
        Unit unit3 = Unit.INSTANCE;
        FilterGroup[] filterGroupArr2 = {filterGroup6, filterGroup7, filterGroup8, filterGroup9};
        for (int i4 = 0; i4 < 4; i4++) {
            FilterGroup filterGroup10 = filterGroupArr2[i4];
            Boolean bool2 = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup10.getFilterId()));
            filterGroup10.setSupport(bool2 != null ? bool2.booleanValue() : true);
        }
        Unit unit4 = Unit.INSTANCE;
        return new FontFilter(numArr, new FilterGroup[][]{filterGroupArr, filterGroupArr2}, false, 4, null);
    }

    public static final FontFilter DOODLE_TEMPLATE_FONT_FILTER_delegate$lambda$44() {
        Integer[] numArr = {Integer.valueOf(R.string.kg_font_filter_doodle_primary_label), Integer.valueOf(R.string.kg_font_filter_doodle_secondary_label)};
        int i = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil = DataUtil.INSTANCE;
        FilterColor[] filterColorArr = DEFAULT_FILTER_COLORS;
        return new FontFilter(numArr, new FilterGroup[][]{new FilterGroup[]{new FilterGroup(0, i, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 8, null)}, new FilterGroup[]{new FilterGroup(0, i, dataUtil.copyFilterColorArray(filterColorArr, SetsKt.hashSetOf(0)), false, false, 8, null)}}, false, 4, null);
    }

    public static final FontFilter EASTERN_ART_A_STYLE_1_FONT_FILTER_delegate$lambda$49() {
        Integer[] numArr = {Integer.valueOf(R.string.kg_font_filter_object_label_time), Integer.valueOf(R.string.kg_font_filter_object_label_date)};
        int i = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil = DataUtil.INSTANCE;
        FilterColor[] filterColorArr = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup = new FilterGroup(0, i, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup2 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup3 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup4 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor : filterGroup4.getColorData()) {
            if (filterColor.getColorId() == 0 || filterColor.getColorId() == 13) {
                filterColor.setSupportSelect(false);
            }
            filterColor.setMutable(false);
        }
        Unit unit = Unit.INSTANCE;
        FilterGroup[] filterGroupArr = {filterGroup, filterGroup2, filterGroup3, filterGroup4};
        for (int i2 = 0; i2 < 4; i2++) {
            FilterGroup filterGroup5 = filterGroupArr[i2];
            Boolean bool = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup5.getFilterId()));
            filterGroup5.setSupport(bool != null ? bool.booleanValue() : true);
        }
        Unit unit2 = Unit.INSTANCE;
        int i3 = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil2 = DataUtil.INSTANCE;
        FilterColor[] filterColorArr2 = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup6 = new FilterGroup(0, i3, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup7 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup8 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup9 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor2 : filterGroup9.getColorData()) {
            if (filterColor2.getColorId() == 0 || filterColor2.getColorId() == 13) {
                filterColor2.setSupportSelect(false);
            }
            filterColor2.setMutable(false);
        }
        Unit unit3 = Unit.INSTANCE;
        FilterGroup[] filterGroupArr2 = {filterGroup6, filterGroup7, filterGroup8, filterGroup9};
        for (int i4 = 0; i4 < 4; i4++) {
            FilterGroup filterGroup10 = filterGroupArr2[i4];
            Boolean bool2 = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup10.getFilterId()));
            filterGroup10.setSupport(bool2 != null ? bool2.booleanValue() : true);
        }
        Unit unit4 = Unit.INSTANCE;
        return new FontFilter(numArr, new FilterGroup[][]{filterGroupArr, filterGroupArr2}, false, 4, null);
    }

    public static final FontFilter EASTERN_ART_A_STYLE_2_FONT_FILTER_delegate$lambda$54() {
        Integer[] numArr = {Integer.valueOf(R.string.kg_font_filter_object_label_time_weather), Integer.valueOf(R.string.kg_font_filter_object_label_date)};
        int i = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil = DataUtil.INSTANCE;
        FilterColor[] filterColorArr = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup = new FilterGroup(0, i, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup2 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup3 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup4 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor : filterGroup4.getColorData()) {
            if (filterColor.getColorId() == 0 || filterColor.getColorId() == 13) {
                filterColor.setSupportSelect(false);
            }
            filterColor.setMutable(false);
        }
        Unit unit = Unit.INSTANCE;
        FilterGroup[] filterGroupArr = {filterGroup, filterGroup2, filterGroup3, filterGroup4};
        for (int i2 = 0; i2 < 4; i2++) {
            FilterGroup filterGroup5 = filterGroupArr[i2];
            Boolean bool = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup5.getFilterId()));
            filterGroup5.setSupport(bool != null ? bool.booleanValue() : true);
        }
        Unit unit2 = Unit.INSTANCE;
        int i3 = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil2 = DataUtil.INSTANCE;
        FilterColor[] filterColorArr2 = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup6 = new FilterGroup(0, i3, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup7 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup8 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup9 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor2 : filterGroup9.getColorData()) {
            if (filterColor2.getColorId() == 0 || filterColor2.getColorId() == 13) {
                filterColor2.setSupportSelect(false);
            }
            filterColor2.setMutable(false);
        }
        Unit unit3 = Unit.INSTANCE;
        FilterGroup[] filterGroupArr2 = {filterGroup6, filterGroup7, filterGroup8, filterGroup9};
        for (int i4 = 0; i4 < 4; i4++) {
            FilterGroup filterGroup10 = filterGroupArr2[i4];
            Boolean bool2 = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup10.getFilterId()));
            filterGroup10.setSupport(bool2 != null ? bool2.booleanValue() : true);
        }
        Unit unit4 = Unit.INSTANCE;
        return new FontFilter(numArr, new FilterGroup[][]{filterGroupArr, filterGroupArr2}, false, 4, null);
    }

    public static final FontFilter EASTERN_ART_B_STYLE_1_FONT_FILTER_delegate$lambda$57() {
        Integer[] numArr = {Integer.valueOf(R.string.kg_font_filter_object_label_main_content), Integer.valueOf(R.string.kg_font_filter_object_label_minute)};
        int i = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil = DataUtil.INSTANCE;
        FilterColor[] filterColorArr = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup = new FilterGroup(0, i, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup2 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup3 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup4 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor : filterGroup4.getColorData()) {
            if (filterColor.getColorId() == 0 || filterColor.getColorId() == 13) {
                filterColor.setSupportSelect(false);
            }
            filterColor.setMutable(false);
        }
        Unit unit = Unit.INSTANCE;
        FilterGroup[] filterGroupArr = {filterGroup, filterGroup2, filterGroup3, filterGroup4};
        int i2 = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil2 = DataUtil.INSTANCE;
        FilterColor[] filterColorArr2 = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup5 = new FilterGroup(0, i2, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup6 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup7 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup8 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor2 : filterGroup8.getColorData()) {
            if (filterColor2.getColorId() == 0 || filterColor2.getColorId() == 13) {
                filterColor2.setSupportSelect(false);
            }
            filterColor2.setMutable(false);
        }
        Unit unit2 = Unit.INSTANCE;
        return new FontFilter(numArr, new FilterGroup[][]{filterGroupArr, new FilterGroup[]{filterGroup5, filterGroup6, filterGroup7, filterGroup8}}, false, 4, null);
    }

    public static final FontFilter EASTERN_ART_B_STYLE_2_FONT_FILTER_delegate$lambda$62() {
        Integer[] numArr = {Integer.valueOf(R.string.kg_font_filter_object_label_main_content), Integer.valueOf(R.string.kg_font_filter_object_label_decorate)};
        int i = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil = DataUtil.INSTANCE;
        FilterColor[] filterColorArr = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup = new FilterGroup(0, i, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup2 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup3 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup4 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor : filterGroup4.getColorData()) {
            if (filterColor.getColorId() == 0 || filterColor.getColorId() == 13) {
                filterColor.setSupportSelect(false);
            }
            filterColor.setMutable(false);
        }
        Unit unit = Unit.INSTANCE;
        FilterGroup[] filterGroupArr = {filterGroup, filterGroup2, filterGroup3, filterGroup4};
        for (int i2 = 0; i2 < 4; i2++) {
            FilterGroup filterGroup5 = filterGroupArr[i2];
            Boolean bool = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup5.getFilterId()));
            filterGroup5.setSupport(bool != null ? bool.booleanValue() : true);
        }
        Unit unit2 = Unit.INSTANCE;
        int i3 = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil2 = DataUtil.INSTANCE;
        FilterColor[] filterColorArr2 = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup6 = new FilterGroup(0, i3, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup7 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup8 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup9 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor2 : filterGroup9.getColorData()) {
            if (filterColor2.getColorId() == 0 || filterColor2.getColorId() == 13) {
                filterColor2.setSupportSelect(false);
            }
            filterColor2.setMutable(false);
        }
        Unit unit3 = Unit.INSTANCE;
        FilterGroup[] filterGroupArr2 = {filterGroup6, filterGroup7, filterGroup8, filterGroup9};
        for (int i4 = 0; i4 < 4; i4++) {
            filterGroupArr2[i4].setSupport(false);
        }
        Unit unit4 = Unit.INSTANCE;
        return new FontFilter(numArr, new FilterGroup[][]{filterGroupArr, filterGroupArr2}, false, 4, null);
    }

    public static final FontFilter MAGAZINE_A_B_TEMPLATE_FONT_FILTER_delegate$lambda$22() {
        Integer[] numArr = {Integer.valueOf(R.string.kg_font_filter_magazine_a_b_primary_label), Integer.valueOf(R.string.kg_font_filter_magazine_a_b_secondary_label)};
        int i = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil = DataUtil.INSTANCE;
        FilterColor[] filterColorArr = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup = new FilterGroup(0, i, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup2 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup3 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup4 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor : filterGroup4.getColorData()) {
            if (filterColor.getColorId() == 0 || filterColor.getColorId() == 13) {
                filterColor.setSupportSelect(false);
            }
            filterColor.setMutable(false);
        }
        Unit unit = Unit.INSTANCE;
        FilterGroup[] filterGroupArr = {filterGroup, filterGroup2, filterGroup3, filterGroup4};
        for (int i2 = 0; i2 < 4; i2++) {
            FilterGroup filterGroup5 = filterGroupArr[i2];
            Boolean bool = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup5.getFilterId()));
            filterGroup5.setSupport(bool != null ? bool.booleanValue() : true);
        }
        Unit unit2 = Unit.INSTANCE;
        int i3 = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil2 = DataUtil.INSTANCE;
        FilterColor[] filterColorArr2 = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup6 = new FilterGroup(0, i3, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup7 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup8 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup9 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor2 : filterGroup9.getColorData()) {
            if (filterColor2.getColorId() == 0 || filterColor2.getColorId() == 13) {
                filterColor2.setSupportSelect(false);
            }
            filterColor2.setMutable(false);
        }
        Unit unit3 = Unit.INSTANCE;
        FilterGroup[] filterGroupArr2 = {filterGroup6, filterGroup7, filterGroup8, filterGroup9};
        for (int i4 = 0; i4 < 4; i4++) {
            FilterGroup filterGroup10 = filterGroupArr2[i4];
            Boolean bool2 = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup10.getFilterId()));
            filterGroup10.setSupport(bool2 != null ? bool2.booleanValue() : true);
        }
        Unit unit4 = Unit.INSTANCE;
        return new FontFilter(numArr, new FilterGroup[][]{filterGroupArr, filterGroupArr2}, false, 4, null);
    }

    public static final FontFilter MAGAZINE_C_STYLE_1_FONT_FILTER_delegate$lambda$27() {
        Integer[] numArr = {Integer.valueOf(R.string.kg_font_filter_magazine_c_style_1_primary_label), Integer.valueOf(R.string.kg_font_filter_magazine_c2_secondary_label)};
        int i = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil = DataUtil.INSTANCE;
        FilterColor[] filterColorArr = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup = new FilterGroup(0, i, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup2 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup3 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup4 = new FilterGroup(4, R.string.kg_font_filter_group_label_gradient_blur, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup5 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor : filterGroup5.getColorData()) {
            if (filterColor.getColorId() == 0 || filterColor.getColorId() == 13) {
                filterColor.setSupportSelect(false);
            }
            filterColor.setMutable(false);
        }
        Unit unit = Unit.INSTANCE;
        FilterGroup[] filterGroupArr = {filterGroup, filterGroup2, filterGroup3, filterGroup4, filterGroup5};
        for (int i2 = 0; i2 < 5; i2++) {
            FilterGroup filterGroup6 = filterGroupArr[i2];
            Boolean bool = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup6.getFilterId()));
            filterGroup6.setSupport(bool != null ? bool.booleanValue() : true);
        }
        Unit unit2 = Unit.INSTANCE;
        int i3 = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil2 = DataUtil.INSTANCE;
        FilterColor[] filterColorArr2 = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup7 = new FilterGroup(0, i3, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup8 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup9 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup10 = new FilterGroup(4, R.string.kg_font_filter_group_label_gradient_blur, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup11 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor2 : filterGroup11.getColorData()) {
            if (filterColor2.getColorId() == 0 || filterColor2.getColorId() == 13) {
                filterColor2.setSupportSelect(false);
            }
            filterColor2.setMutable(false);
        }
        Unit unit3 = Unit.INSTANCE;
        FilterGroup[] filterGroupArr2 = {filterGroup7, filterGroup8, filterGroup9, filterGroup10, filterGroup11};
        for (int i4 = 0; i4 < 5; i4++) {
            FilterGroup filterGroup12 = filterGroupArr2[i4];
            Boolean bool2 = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup12.getFilterId()));
            filterGroup12.setSupport(bool2 != null ? bool2.booleanValue() : true);
        }
        Unit unit4 = Unit.INSTANCE;
        return new FontFilter(numArr, new FilterGroup[][]{filterGroupArr, filterGroupArr2}, false, 4, null);
    }

    public static final FontFilter MAGAZINE_C_STYLE_2_FONT_FILTER_delegate$lambda$30() {
        Integer[] numArr = {Integer.valueOf(R.string.kg_function_button_text_color)};
        int i = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil = DataUtil.INSTANCE;
        FilterColor[] filterColorArr = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup = new FilterGroup(0, i, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup2 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup3 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup4 = new FilterGroup(4, R.string.kg_font_filter_group_label_gradient_blur, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup5 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor : filterGroup5.getColorData()) {
            if (filterColor.getColorId() == 0 || filterColor.getColorId() == 13) {
                filterColor.setSupportSelect(false);
            }
            filterColor.setMutable(false);
        }
        Unit unit = Unit.INSTANCE;
        FilterGroup[] filterGroupArr = {filterGroup, filterGroup2, filterGroup3, filterGroup4, filterGroup5};
        for (int i2 = 0; i2 < 5; i2++) {
            FilterGroup filterGroup6 = filterGroupArr[i2];
            Boolean bool = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup6.getFilterId()));
            filterGroup6.setSupport(bool != null ? bool.booleanValue() : true);
        }
        Unit unit2 = Unit.INSTANCE;
        return new FontFilter(numArr, new FilterGroup[][]{filterGroupArr}, false, 4, null);
    }

    public static final FontFilter MAGAZINE_C_STYLE_3_FONT_FILTER_delegate$lambda$35() {
        Integer[] numArr = {Integer.valueOf(R.string.kg_font_filter_object_label_hour), Integer.valueOf(R.string.kg_font_filter_object_label_minute)};
        int i = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil = DataUtil.INSTANCE;
        FilterColor[] filterColorArr = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup = new FilterGroup(0, i, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup2 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup3 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup4 = new FilterGroup(4, R.string.kg_font_filter_group_label_gradient_blur, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup5 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor : filterGroup5.getColorData()) {
            if (filterColor.getColorId() == 0 || filterColor.getColorId() == 13) {
                filterColor.setSupportSelect(false);
            }
            filterColor.setMutable(false);
        }
        Unit unit = Unit.INSTANCE;
        FilterGroup[] filterGroupArr = {filterGroup, filterGroup2, filterGroup3, filterGroup4, filterGroup5};
        for (int i2 = 0; i2 < 5; i2++) {
            FilterGroup filterGroup6 = filterGroupArr[i2];
            Boolean bool = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup6.getFilterId()));
            filterGroup6.setSupport(bool != null ? bool.booleanValue() : true);
        }
        Unit unit2 = Unit.INSTANCE;
        int i3 = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil2 = DataUtil.INSTANCE;
        FilterColor[] filterColorArr2 = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup7 = new FilterGroup(0, i3, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup8 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup9 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup10 = new FilterGroup(4, R.string.kg_font_filter_group_label_gradient_blur, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup11 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor2 : filterGroup11.getColorData()) {
            if (filterColor2.getColorId() == 0 || filterColor2.getColorId() == 13) {
                filterColor2.setSupportSelect(false);
            }
            filterColor2.setMutable(false);
        }
        Unit unit3 = Unit.INSTANCE;
        FilterGroup[] filterGroupArr2 = {filterGroup7, filterGroup8, filterGroup9, filterGroup10, filterGroup11};
        for (int i4 = 0; i4 < 5; i4++) {
            FilterGroup filterGroup12 = filterGroupArr2[i4];
            Boolean bool2 = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup12.getFilterId()));
            filterGroup12.setSupport(bool2 != null ? bool2.booleanValue() : true);
        }
        Unit unit4 = Unit.INSTANCE;
        return new FontFilter(numArr, new FilterGroup[][]{filterGroupArr, filterGroupArr2}, false, 4, null);
    }

    public static final FontFilter MAGAZINE_C_STYLE_4_FONT_FILTER_delegate$lambda$40() {
        Integer[] numArr = {Integer.valueOf(R.string.kg_font_filter_object_label_hour), Integer.valueOf(R.string.kg_font_filter_object_label_minute)};
        int i = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil = DataUtil.INSTANCE;
        FilterColor[] filterColorArr = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup = new FilterGroup(0, i, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup2 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup3 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup4 = new FilterGroup(4, R.string.kg_font_filter_group_label_gradient_blur, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup5 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor : filterGroup5.getColorData()) {
            if (filterColor.getColorId() == 0 || filterColor.getColorId() == 13) {
                filterColor.setSupportSelect(false);
            }
            filterColor.setMutable(false);
        }
        Unit unit = Unit.INSTANCE;
        FilterGroup[] filterGroupArr = {filterGroup, filterGroup2, filterGroup3, filterGroup4, filterGroup5};
        for (int i2 = 0; i2 < 5; i2++) {
            FilterGroup filterGroup6 = filterGroupArr[i2];
            Boolean bool = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup6.getFilterId()));
            filterGroup6.setSupport(bool != null ? bool.booleanValue() : true);
        }
        Unit unit2 = Unit.INSTANCE;
        int i3 = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil2 = DataUtil.INSTANCE;
        FilterColor[] filterColorArr2 = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup7 = new FilterGroup(0, i3, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup8 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup9 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup10 = new FilterGroup(4, R.string.kg_font_filter_group_label_gradient_blur, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup11 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor2 : filterGroup11.getColorData()) {
            if (filterColor2.getColorId() == 0 || filterColor2.getColorId() == 13) {
                filterColor2.setSupportSelect(false);
            }
            filterColor2.setMutable(false);
        }
        Unit unit3 = Unit.INSTANCE;
        FilterGroup[] filterGroupArr2 = {filterGroup7, filterGroup8, filterGroup9, filterGroup10, filterGroup11};
        for (int i4 = 0; i4 < 5; i4++) {
            FilterGroup filterGroup12 = filterGroupArr2[i4];
            Boolean bool2 = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup12.getFilterId()));
            filterGroup12.setSupport(bool2 != null ? bool2.booleanValue() : true);
        }
        Unit unit4 = Unit.INSTANCE;
        return new FontFilter(numArr, new FilterGroup[][]{filterGroupArr, filterGroupArr2}, false, 4, null);
    }

    public static final FontFilter MAGAZINE_C_STYLE_5_FONT_FILTER_delegate$lambda$43() {
        Integer[] numArr = {Integer.valueOf(R.string.kg_function_button_text_color)};
        int i = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil = DataUtil.INSTANCE;
        FilterColor[] filterColorArr = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup = new FilterGroup(0, i, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup2 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup3 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup4 = new FilterGroup(4, R.string.kg_font_filter_group_label_gradient_blur, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup5 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor : filterGroup5.getColorData()) {
            if (filterColor.getColorId() == 0 || filterColor.getColorId() == 13) {
                filterColor.setSupportSelect(false);
            }
            filterColor.setMutable(false);
        }
        Unit unit = Unit.INSTANCE;
        FilterGroup[] filterGroupArr = {filterGroup, filterGroup2, filterGroup3, filterGroup4, filterGroup5};
        for (int i2 = 0; i2 < 5; i2++) {
            FilterGroup filterGroup6 = filterGroupArr[i2];
            Boolean bool = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup6.getFilterId()));
            filterGroup6.setSupport(bool != null ? bool.booleanValue() : true);
        }
        Unit unit2 = Unit.INSTANCE;
        return new FontFilter(numArr, new FilterGroup[][]{filterGroupArr}, false, 4, null);
    }

    public static final FontFilter OVERSIZE_A_FONT_FILTER_delegate$lambda$72() {
        Integer[] numArr = {Integer.valueOf(R.string.kg_font_filter_object_label_hour), Integer.valueOf(R.string.kg_font_filter_object_label_minute)};
        int i = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil = DataUtil.INSTANCE;
        FilterColor[] filterColorArr = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup = new FilterGroup(0, i, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup2 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup3 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup4 = new FilterGroup(4, R.string.kg_font_filter_group_label_gradient_blur, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup5 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor : filterGroup5.getColorData()) {
            if (filterColor.getColorId() == 0 || filterColor.getColorId() == 13) {
                filterColor.setSupportSelect(false);
            }
            filterColor.setMutable(false);
        }
        Unit unit = Unit.INSTANCE;
        FilterGroup[] filterGroupArr = {filterGroup, filterGroup2, filterGroup3, filterGroup4, filterGroup5};
        for (int i2 = 0; i2 < 5; i2++) {
            FilterGroup filterGroup6 = filterGroupArr[i2];
            Boolean bool = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup6.getFilterId()));
            filterGroup6.setSupport(bool != null ? bool.booleanValue() : true);
        }
        Unit unit2 = Unit.INSTANCE;
        int i3 = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil2 = DataUtil.INSTANCE;
        FilterColor[] filterColorArr2 = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup7 = new FilterGroup(0, i3, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup8 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup9 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup10 = new FilterGroup(4, R.string.kg_font_filter_group_label_gradient_blur, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup11 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor2 : filterGroup11.getColorData()) {
            if (filterColor2.getColorId() == 0 || filterColor2.getColorId() == 13) {
                filterColor2.setSupportSelect(false);
            }
            filterColor2.setMutable(false);
        }
        Unit unit3 = Unit.INSTANCE;
        FilterGroup[] filterGroupArr2 = {filterGroup7, filterGroup8, filterGroup9, filterGroup10, filterGroup11};
        for (int i4 = 0; i4 < 5; i4++) {
            FilterGroup filterGroup12 = filterGroupArr2[i4];
            Boolean bool2 = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup12.getFilterId()));
            filterGroup12.setSupport(bool2 != null ? bool2.booleanValue() : true);
        }
        Unit unit4 = Unit.INSTANCE;
        return new FontFilter(numArr, new FilterGroup[][]{filterGroupArr, filterGroupArr2}, false, 4, null);
    }

    public static final FontFilter OVERSIZE_B_FONT_FILTER_delegate$lambda$77() {
        Integer[] numArr = {Integer.valueOf(R.string.kg_font_filter_object_label_hour_date), Integer.valueOf(R.string.kg_font_filter_object_label_minute)};
        int i = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil = DataUtil.INSTANCE;
        FilterColor[] filterColorArr = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup = new FilterGroup(0, i, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup2 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup3 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup4 = new FilterGroup(4, R.string.kg_font_filter_group_label_gradient_blur, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup5 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor : filterGroup5.getColorData()) {
            if (filterColor.getColorId() == 0 || filterColor.getColorId() == 13) {
                filterColor.setSupportSelect(false);
            }
            filterColor.setMutable(false);
        }
        Unit unit = Unit.INSTANCE;
        FilterGroup[] filterGroupArr = {filterGroup, filterGroup2, filterGroup3, filterGroup4, filterGroup5};
        for (int i2 = 0; i2 < 5; i2++) {
            FilterGroup filterGroup6 = filterGroupArr[i2];
            Boolean bool = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup6.getFilterId()));
            filterGroup6.setSupport(bool != null ? bool.booleanValue() : true);
        }
        Unit unit2 = Unit.INSTANCE;
        int i3 = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil2 = DataUtil.INSTANCE;
        FilterColor[] filterColorArr2 = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup7 = new FilterGroup(0, i3, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup8 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup9 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup10 = new FilterGroup(4, R.string.kg_font_filter_group_label_gradient_blur, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup11 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor2 : filterGroup11.getColorData()) {
            if (filterColor2.getColorId() == 0 || filterColor2.getColorId() == 13) {
                filterColor2.setSupportSelect(false);
            }
            filterColor2.setMutable(false);
        }
        Unit unit3 = Unit.INSTANCE;
        FilterGroup[] filterGroupArr2 = {filterGroup7, filterGroup8, filterGroup9, filterGroup10, filterGroup11};
        for (int i4 = 0; i4 < 5; i4++) {
            FilterGroup filterGroup12 = filterGroupArr2[i4];
            Boolean bool2 = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup12.getFilterId()));
            filterGroup12.setSupport(bool2 != null ? bool2.booleanValue() : true);
        }
        Unit unit4 = Unit.INSTANCE;
        return new FontFilter(numArr, new FilterGroup[][]{filterGroupArr, filterGroupArr2}, false, 4, null);
    }

    public static final FontFilter PAD_A_FONT_FILTER_delegate$lambda$82() {
        Integer[] numArr = {Integer.valueOf(R.string.kg_font_filter_object_label_time), Integer.valueOf(R.string.kg_font_filter_object_label_signature)};
        int i = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil = DataUtil.INSTANCE;
        FilterColor[] filterColorArr = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup = new FilterGroup(0, i, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup2 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup3 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup4 = new FilterGroup(4, R.string.kg_font_filter_group_label_gradient_blur, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup5 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor : filterGroup5.getColorData()) {
            if (filterColor.getColorId() == 0 || filterColor.getColorId() == 13) {
                filterColor.setSupportSelect(false);
            }
            filterColor.setMutable(false);
        }
        Unit unit = Unit.INSTANCE;
        FilterGroup[] filterGroupArr = {filterGroup, filterGroup2, filterGroup3, filterGroup4, filterGroup5};
        for (int i2 = 0; i2 < 5; i2++) {
            FilterGroup filterGroup6 = filterGroupArr[i2];
            Boolean bool = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup6.getFilterId()));
            filterGroup6.setSupport(bool != null ? bool.booleanValue() : true);
        }
        Unit unit2 = Unit.INSTANCE;
        int i3 = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil2 = DataUtil.INSTANCE;
        FilterColor[] filterColorArr2 = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup7 = new FilterGroup(0, i3, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup8 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup9 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup10 = new FilterGroup(4, R.string.kg_font_filter_group_label_gradient_blur, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup11 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor2 : filterGroup11.getColorData()) {
            if (filterColor2.getColorId() == 0 || filterColor2.getColorId() == 13) {
                filterColor2.setSupportSelect(false);
            }
            filterColor2.setMutable(false);
        }
        Unit unit3 = Unit.INSTANCE;
        FilterGroup[] filterGroupArr2 = {filterGroup7, filterGroup8, filterGroup9, filterGroup10, filterGroup11};
        for (int i4 = 0; i4 < 5; i4++) {
            filterGroupArr2[i4].setSupport(false);
        }
        Unit unit4 = Unit.INSTANCE;
        return new FontFilter(numArr, new FilterGroup[][]{filterGroupArr, filterGroupArr2}, false, 4, null);
    }

    public static final FontFilter PAD_B_FONT_FILTER_delegate$lambda$87() {
        Integer[] numArr = new Integer[0];
        int i = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil = DataUtil.INSTANCE;
        FilterColor[] filterColorArr = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup = new FilterGroup(0, i, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup2 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup3 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup4 = new FilterGroup(4, R.string.kg_font_filter_group_label_gradient_blur, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup5 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor : filterGroup5.getColorData()) {
            if (filterColor.getColorId() == 0 || filterColor.getColorId() == 13) {
                filterColor.setSupportSelect(false);
            }
            filterColor.setMutable(false);
        }
        Unit unit = Unit.INSTANCE;
        FilterGroup[] filterGroupArr = {filterGroup, filterGroup2, filterGroup3, filterGroup4, filterGroup5};
        for (int i2 = 0; i2 < 5; i2++) {
            FilterGroup filterGroup6 = filterGroupArr[i2];
            Boolean bool = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup6.getFilterId()));
            filterGroup6.setSupport(bool != null ? bool.booleanValue() : true);
        }
        Unit unit2 = Unit.INSTANCE;
        int i3 = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil2 = DataUtil.INSTANCE;
        FilterColor[] filterColorArr2 = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup7 = new FilterGroup(0, i3, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup8 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup9 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup10 = new FilterGroup(4, R.string.kg_font_filter_group_label_gradient_blur, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup11 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor2 : filterGroup11.getColorData()) {
            if (filterColor2.getColorId() == 0 || filterColor2.getColorId() == 13) {
                filterColor2.setSupportSelect(false);
            }
            filterColor2.setMutable(false);
        }
        Unit unit3 = Unit.INSTANCE;
        FilterGroup[] filterGroupArr2 = {filterGroup7, filterGroup8, filterGroup9, filterGroup10, filterGroup11};
        for (int i4 = 0; i4 < 5; i4++) {
            FilterGroup filterGroup12 = filterGroupArr2[i4];
            Boolean bool2 = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup12.getFilterId()));
            filterGroup12.setSupport(bool2 != null ? bool2.booleanValue() : true);
        }
        Unit unit4 = Unit.INSTANCE;
        return new FontFilter(numArr, new FilterGroup[][]{filterGroupArr, filterGroupArr2}, false, 4, null);
    }

    public static final FontFilter PAD_C_FONT_FILTER_delegate$lambda$92() {
        Integer[] numArr = {Integer.valueOf(R.string.kg_font_filter_object_label_main_content), Integer.valueOf(R.string.kg_font_filter_object_label_minute)};
        int i = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil = DataUtil.INSTANCE;
        FilterColor[] filterColorArr = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup = new FilterGroup(0, i, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup2 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup3 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup4 = new FilterGroup(4, R.string.kg_font_filter_group_label_gradient_blur, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup5 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor : filterGroup5.getColorData()) {
            if (filterColor.getColorId() == 0 || filterColor.getColorId() == 13) {
                filterColor.setSupportSelect(false);
            }
            filterColor.setMutable(false);
        }
        Unit unit = Unit.INSTANCE;
        FilterGroup[] filterGroupArr = {filterGroup, filterGroup2, filterGroup3, filterGroup4, filterGroup5};
        for (int i2 = 0; i2 < 5; i2++) {
            FilterGroup filterGroup6 = filterGroupArr[i2];
            Boolean bool = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup6.getFilterId()));
            filterGroup6.setSupport(bool != null ? bool.booleanValue() : true);
        }
        Unit unit2 = Unit.INSTANCE;
        int i3 = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil2 = DataUtil.INSTANCE;
        FilterColor[] filterColorArr2 = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup7 = new FilterGroup(0, i3, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup8 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup9 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup10 = new FilterGroup(4, R.string.kg_font_filter_group_label_gradient_blur, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup11 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor2 : filterGroup11.getColorData()) {
            if (filterColor2.getColorId() == 0 || filterColor2.getColorId() == 13) {
                filterColor2.setSupportSelect(false);
            }
            filterColor2.setMutable(false);
        }
        Unit unit3 = Unit.INSTANCE;
        FilterGroup[] filterGroupArr2 = {filterGroup7, filterGroup8, filterGroup9, filterGroup10, filterGroup11};
        for (int i4 = 0; i4 < 5; i4++) {
            FilterGroup filterGroup12 = filterGroupArr2[i4];
            Boolean bool2 = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup12.getFilterId()));
            filterGroup12.setSupport(bool2 != null ? bool2.booleanValue() : true);
        }
        Unit unit4 = Unit.INSTANCE;
        return new FontFilter(numArr, new FilterGroup[][]{filterGroupArr, filterGroupArr2}, false, 4, null);
    }

    public static final FontFilter PETS_FONT_FILTER_delegate$lambda$97() {
        Integer[] numArr = {Integer.valueOf(R.string.kg_font_filter_object_label_primary), Integer.valueOf(R.string.kg_font_filter_object_label_secondary)};
        int i = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil = DataUtil.INSTANCE;
        FilterColor[] filterColorArr = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup = new FilterGroup(0, i, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup2 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup3 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup4 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor : filterGroup4.getColorData()) {
            if (filterColor.getColorId() == 0 || filterColor.getColorId() == 13) {
                filterColor.setSupportSelect(false);
            }
            filterColor.setMutable(false);
        }
        Unit unit = Unit.INSTANCE;
        FilterGroup[] filterGroupArr = {filterGroup, filterGroup2, filterGroup3, filterGroup4};
        for (int i2 = 0; i2 < 4; i2++) {
            FilterGroup filterGroup5 = filterGroupArr[i2];
            Boolean bool = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup5.getFilterId()));
            filterGroup5.setSupport(bool != null ? bool.booleanValue() : true);
        }
        Unit unit2 = Unit.INSTANCE;
        int i3 = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil2 = DataUtil.INSTANCE;
        FilterColor[] filterColorArr2 = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup6 = new FilterGroup(0, i3, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup7 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup8 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup9 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor2 : filterGroup9.getColorData()) {
            if (filterColor2.getColorId() == 0 || filterColor2.getColorId() == 13) {
                filterColor2.setSupportSelect(false);
            }
            filterColor2.setMutable(false);
        }
        Unit unit3 = Unit.INSTANCE;
        FilterGroup[] filterGroupArr2 = {filterGroup6, filterGroup7, filterGroup8, filterGroup9};
        for (int i4 = 0; i4 < 4; i4++) {
            FilterGroup filterGroup10 = filterGroupArr2[i4];
            Boolean bool2 = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup10.getFilterId()));
            filterGroup10.setSupport(bool2 != null ? bool2.booleanValue() : true);
        }
        Unit unit4 = Unit.INSTANCE;
        return new FontFilter(numArr, new FilterGroup[][]{filterGroupArr, filterGroupArr2}, false, 4, null);
    }

    public static final FontFilter RHOMBUS_TEMPLATE_FONT_FILTER_delegate$lambda$17() {
        Integer[] numArr = {Integer.valueOf(R.string.kg_classic_item_type_time), Integer.valueOf(R.string.kg_font_filter_rhombus_secondary_label)};
        int i = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil = DataUtil.INSTANCE;
        FilterColor[] filterColorArr = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup = new FilterGroup(0, i, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup2 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup3 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup4 = new FilterGroup(4, R.string.kg_font_filter_group_label_gradient_blur, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup5 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor : filterGroup5.getColorData()) {
            if (filterColor.getColorId() == 0 || filterColor.getColorId() == 13) {
                filterColor.setSupportSelect(false);
            }
            filterColor.setMutable(false);
        }
        Unit unit = Unit.INSTANCE;
        FilterGroup[] filterGroupArr = {filterGroup, filterGroup2, filterGroup3, filterGroup4, filterGroup5};
        for (int i2 = 0; i2 < 5; i2++) {
            FilterGroup filterGroup6 = filterGroupArr[i2];
            Boolean bool = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup6.getFilterId()));
            filterGroup6.setSupport(bool != null ? bool.booleanValue() : true);
        }
        Unit unit2 = Unit.INSTANCE;
        int i3 = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil2 = DataUtil.INSTANCE;
        FilterColor[] filterColorArr2 = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup7 = new FilterGroup(0, i3, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup8 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup9 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup10 = new FilterGroup(4, R.string.kg_font_filter_group_label_gradient_blur, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup11 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor2 : filterGroup11.getColorData()) {
            if (filterColor2.getColorId() == 0 || filterColor2.getColorId() == 13) {
                filterColor2.setSupportSelect(false);
            }
            filterColor2.setMutable(false);
        }
        Unit unit3 = Unit.INSTANCE;
        FilterGroup[] filterGroupArr2 = {filterGroup7, filterGroup8, filterGroup9, filterGroup10, filterGroup11};
        for (int i4 = 0; i4 < 5; i4++) {
            filterGroupArr2[i4].setSupport(false);
        }
        Unit unit4 = Unit.INSTANCE;
        return new FontFilter(numArr, new FilterGroup[][]{filterGroupArr, filterGroupArr2}, true);
    }

    public static final FontFilter TRADITIONAL_CHINA_C_FONT_FILTER_delegate$lambda$67() {
        Integer[] numArr = {Integer.valueOf(R.string.kg_font_filter_object_label_time), Integer.valueOf(R.string.kg_font_filter_object_label_date)};
        int i = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil = DataUtil.INSTANCE;
        FilterColor[] filterColorArr = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup = new FilterGroup(0, i, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup2 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup3 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup4 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil, filterColorArr, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor : filterGroup4.getColorData()) {
            if (filterColor.getColorId() == 0 || filterColor.getColorId() == 13) {
                filterColor.setSupportSelect(false);
            }
            filterColor.setMutable(false);
        }
        Unit unit = Unit.INSTANCE;
        FilterGroup[] filterGroupArr = {filterGroup, filterGroup2, filterGroup3, filterGroup4};
        for (int i2 = 0; i2 < 4; i2++) {
            FilterGroup filterGroup5 = filterGroupArr[i2];
            Boolean bool = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup5.getFilterId()));
            filterGroup5.setSupport(bool != null ? bool.booleanValue() : true);
        }
        Unit unit2 = Unit.INSTANCE;
        int i3 = R.string.kg_font_filter_group_label_solid;
        DataUtil dataUtil2 = DataUtil.INSTANCE;
        FilterColor[] filterColorArr2 = DEFAULT_FILTER_COLORS;
        FilterGroup filterGroup6 = new FilterGroup(0, i3, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup7 = new FilterGroup(1, R.string.kg_font_filter_group_label_obscure, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup8 = new FilterGroup(2, R.string.kg_font_filter_group_label_overlap, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        FilterGroup filterGroup9 = new FilterGroup(3, R.string.kg_font_filter_group_label_glow, DataUtil.copyFilterColorArray$default(dataUtil2, filterColorArr2, null, 2, null), false, false, 24, null);
        for (FilterColor filterColor2 : filterGroup9.getColorData()) {
            if (filterColor2.getColorId() == 0 || filterColor2.getColorId() == 13) {
                filterColor2.setSupportSelect(false);
            }
            filterColor2.setMutable(false);
        }
        Unit unit3 = Unit.INSTANCE;
        FilterGroup[] filterGroupArr2 = {filterGroup6, filterGroup7, filterGroup8, filterGroup9};
        for (int i4 = 0; i4 < 4; i4++) {
            FilterGroup filterGroup10 = filterGroupArr2[i4];
            Boolean bool2 = (Boolean) FILTER_SUPPORT_INFO.get(Integer.valueOf(filterGroup10.getFilterId()));
            filterGroup10.setSupport(bool2 != null ? bool2.booleanValue() : true);
        }
        Unit unit4 = Unit.INSTANCE;
        return new FontFilter(numArr, new FilterGroup[][]{filterGroupArr, filterGroupArr2}, false, 4, null);
    }

    public static final FontFilter getALL_IN_ONE_FONT_FILTER() {
        return ALL_IN_ONE_FONT_FILTER;
    }

    public static final FontFilter getCLASSIC_MAX_TEMPLATE_FONT_FILTER() {
        return (FontFilter) CLASSIC_MAX_TEMPLATE_FONT_FILTER$delegate.getValue();
    }

    public static final FontFilter getCLASSIC_PLUS_TEMPLATE_FONT_FILTER() {
        return (FontFilter) CLASSIC_PLUS_TEMPLATE_FONT_FILTER$delegate.getValue();
    }

    public static final FontFilter getCLASSIC_TEMPLATE_FONT_FILTER() {
        return (FontFilter) CLASSIC_TEMPLATE_FONT_FILTER$delegate.getValue();
    }

    public static final int getCOMPUTILITYLEVELV2() {
        return COMPUTILITYLEVELV2;
    }

    public static final FilterColor[] getDEFAULT_FILTER_COLORS() {
        return DEFAULT_FILTER_COLORS;
    }

    public static final FontFilter getDOODLE_TEMPLATE_FONT_FILTER() {
        return (FontFilter) DOODLE_TEMPLATE_FONT_FILTER$delegate.getValue();
    }

    public static final FontFilter getEASTERN_ART_A_STYLE_1_FONT_FILTER() {
        return (FontFilter) EASTERN_ART_A_STYLE_1_FONT_FILTER$delegate.getValue();
    }

    public static final FontFilter getEASTERN_ART_A_STYLE_2_FONT_FILTER() {
        return (FontFilter) EASTERN_ART_A_STYLE_2_FONT_FILTER$delegate.getValue();
    }

    public static final FontFilter getEASTERN_ART_B_STYLE_1_FONT_FILTER() {
        return (FontFilter) EASTERN_ART_B_STYLE_1_FONT_FILTER$delegate.getValue();
    }

    public static final FontFilter getEASTERN_ART_B_STYLE_2_FONT_FILTER() {
        return (FontFilter) EASTERN_ART_B_STYLE_2_FONT_FILTER$delegate.getValue();
    }

    public static final Map getFILTER_SUPPORT_INFO() {
        return FILTER_SUPPORT_INFO;
    }

    public static final FontFilter getMAGAZINE_A_B_TEMPLATE_FONT_FILTER() {
        return (FontFilter) MAGAZINE_A_B_TEMPLATE_FONT_FILTER$delegate.getValue();
    }

    public static final FontFilter getMAGAZINE_C_STYLE_1_FONT_FILTER() {
        return (FontFilter) MAGAZINE_C_STYLE_1_FONT_FILTER$delegate.getValue();
    }

    public static final FontFilter getMAGAZINE_C_STYLE_2_FONT_FILTER() {
        return (FontFilter) MAGAZINE_C_STYLE_2_FONT_FILTER$delegate.getValue();
    }

    public static final FontFilter getMAGAZINE_C_STYLE_3_FONT_FILTER() {
        return (FontFilter) MAGAZINE_C_STYLE_3_FONT_FILTER$delegate.getValue();
    }

    public static final FontFilter getMAGAZINE_C_STYLE_4_FONT_FILTER() {
        return (FontFilter) MAGAZINE_C_STYLE_4_FONT_FILTER$delegate.getValue();
    }

    public static final FontFilter getMAGAZINE_C_STYLE_5_FONT_FILTER() {
        return (FontFilter) MAGAZINE_C_STYLE_5_FONT_FILTER$delegate.getValue();
    }

    public static final FontFilter getOVERSIZE_A_FONT_FILTER() {
        return (FontFilter) OVERSIZE_A_FONT_FILTER$delegate.getValue();
    }

    public static final FontFilter getOVERSIZE_B_FONT_FILTER() {
        return (FontFilter) OVERSIZE_B_FONT_FILTER$delegate.getValue();
    }

    public static final FontFilter getPAD_A_FONT_FILTER() {
        return (FontFilter) PAD_A_FONT_FILTER$delegate.getValue();
    }

    public static final FontFilter getPAD_B_FONT_FILTER() {
        return (FontFilter) PAD_B_FONT_FILTER$delegate.getValue();
    }

    public static final FontFilter getPAD_C_FONT_FILTER() {
        return (FontFilter) PAD_C_FONT_FILTER$delegate.getValue();
    }

    public static final FontFilter getPETS_FONT_FILTER() {
        return (FontFilter) PETS_FONT_FILTER$delegate.getValue();
    }

    public static final Map getPRESET_FILTER_COLOR_SELECT_INFO() {
        return PRESET_FILTER_COLOR_SELECT_INFO;
    }

    public static final FontFilter getRHOMBUS_TEMPLATE_FONT_FILTER() {
        return (FontFilter) RHOMBUS_TEMPLATE_FONT_FILTER$delegate.getValue();
    }

    public static final boolean getSUPPORT_EDITOR_VARIABLE_FONT() {
        return SUPPORT_EDITOR_VARIABLE_FONT;
    }

    public static final boolean getSUPPORT_LOCKSCREEN_VARIABLE_FONT() {
        return SUPPORT_LOCKSCREEN_VARIABLE_FONT;
    }

    public static final FontFilter getTRADITIONAL_CHINA_C_FONT_FILTER() {
        return (FontFilter) TRADITIONAL_CHINA_C_FONT_FILTER$delegate.getValue();
    }
}
