package com.miui.aod.category;

import android.content.Context;
import android.util.Pair;
import androidx.collection.ArrayMap;
import androidx.collection.LruCache;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.miui.aod.R;
import com.miui.aod.Utils;
import com.miui.aod.common.StyleInfo;
import com.miui.aod.flip.FlipLinkageStyleController;
import com.miui.aod.util.RuntimeTypeAdapterFactory;
import com.miui.aod.widget.AODSettings;
import com.xiaomi.onetrack.api.bf;
import java.util.Map;
import miuix.os.DeviceHelper;

/* JADX INFO: loaded from: classes.dex */
public class CategoryFactory {
    private static final LruCache<String, StyleInfo> infoCache;
    public static final Map<String, Class<?>> sCategoryMap;
    public static final ArrayMap<String, Pair<Integer, Integer>> sCategoryNameMap;
    private static String[] sClockCategory;
    private static final Map<String, StyleInfo> sPresetStyles;
    public static final ArrayMap<String, StyleInfo> sScrappedCateConverter;

    static {
        ArrayMap arrayMap = new ArrayMap();
        sPresetStyles = arrayMap;
        ArrayMap<String, StyleInfo> arrayMap2 = new ArrayMap<>();
        sScrappedCateConverter = arrayMap2;
        ArrayMap arrayMap3 = new ArrayMap();
        sCategoryMap = arrayMap3;
        ArrayMap<String, Pair<Integer, Integer>> arrayMap4 = new ArrayMap<>();
        sCategoryNameMap = arrayMap4;
        arrayMap4.put("my_aod_style", new Pair(Integer.valueOf(R.string.my_style_title), 0));
        arrayMap4.put("signature", new Pair(Integer.valueOf(R.string.signature), 1));
        arrayMap4.put("panel_clock", new Pair(Integer.valueOf(R.string.panel_clock), 2));
        arrayMap4.put("kaleidoscope", new Pair(Integer.valueOf(R.string.scope_time), 3));
        arrayMap4.put("digital_time", new Pair(Integer.valueOf(R.string.digital_clock), 4));
        arrayMap4.put("chosen_picture", new Pair(Integer.valueOf(R.string.chosen_picture), 5));
        arrayMap4.put("theme", new Pair(Integer.valueOf(R.string.theme_cate_title), 6));
        Gson gson = new Gson();
        arrayMap.put("white_horizontal", (StyleInfo) gson.fromJson("{\"color\":\"white\"}", LinearTimeCategoryInfo.class));
        arrayMap.put("pink_horizontal", (StyleInfo) gson.fromJson("{\"color\":\"pink\"}", LinearTimeCategoryInfo.class));
        arrayMap.put("blue_horizontal", (StyleInfo) gson.fromJson("{\"color\":\"blue\"}", LinearTimeCategoryInfo.class));
        arrayMap.put("paint_horizontal", (StyleInfo) gson.fromJson("{\"bg\":\"paint\"}", PaintVerticalCategoryInfo.class));
        arrayMap.put("white_vertical", (StyleInfo) gson.fromJson("{\"color\":\"white\",\"is_vertical\":true}", LinearTimeCategoryInfo.class));
        arrayMap.put("pink_vertical", (StyleInfo) gson.fromJson("{\"color\":\"pink\",\"is_vertical\":true}", LinearTimeCategoryInfo.class));
        arrayMap.put("blue_vertical", (StyleInfo) gson.fromJson("{\"color\":\"blue\",\"is_vertical\":true}", LinearTimeCategoryInfo.class));
        arrayMap.put(CategoryConstants.CATEGORY_PAINT_VERTICAL, (StyleInfo) gson.fromJson("{\"bg\":\"paint\"}", PaintVerticalCategoryInfo.class));
        arrayMap.put("shadow_vertical", (StyleInfo) gson.fromJson("{\"bg\":\"shadow\"}", PaintVerticalCategoryInfo.class));
        arrayMap.put("moonlight_vertical", (StyleInfo) gson.fromJson("{\"bg\":\"moonlight\"}", PaintVerticalCategoryInfo.class));
        arrayMap.put("tree_oneline", (StyleInfo) gson.fromJson("{\"bg\":\"tree\"}", OnelineCategoryInfo.class));
        arrayMap.put("spirit_oneline", (StyleInfo) gson.fromJson("{\"bg\":\"spirit\"}", OnelineCategoryInfo.class));
        arrayMap.put("succulent_oneline", (StyleInfo) gson.fromJson("{\"bg\":\"succulent\"}", OnelineCategoryInfo.class));
        arrayMap.put("cactus_oneline", (StyleInfo) gson.fromJson("{\"bg\":\"cactus\"}", OnelineCategoryInfo.class));
        arrayMap.put("ghost_oneline", (StyleInfo) gson.fromJson("{\"bg\":\"ghost\"}", OnelineCategoryInfo.class));
        arrayMap.put("spaceman_oneline", (StyleInfo) gson.fromJson("{\"bg\":\"spaceman\"}", OnelineCategoryInfo.class));
        arrayMap.put(CategoryConstants.CATEGORY_SUN, (StyleInfo) gson.fromJson(CategoryConstants.DEFAULT_PARAM, SunCategoryInfo.class));
        arrayMap.put("white_horizontal_left", (StyleInfo) gson.fromJson("{\"is_left_gravity\":true}", LinearTimeCategoryInfo.class));
        arrayMap.put(CategoryConstants.CATEGORY_CLOCK_PANEL, (StyleInfo) gson.fromJson(CategoryConstants.DEFAULT_PARAM, ClockPanelCategoryInfo.class));
        arrayMap.put("clock_panel_first", (StyleInfo) gson.fromJson("{\"panel\":\"clock_panel_first\",\"color\":\"light_purple\"}", AnimateClockPanelCategoryInfo.class));
        arrayMap.put("clock_panel_second", (StyleInfo) gson.fromJson("{\"panel\":\"clock_panel_second\",\"color\":\"light_blue\"}", AnimateClockPanelCategoryInfo.class));
        arrayMap.put("clock_panel_third", (StyleInfo) gson.fromJson("{\"panel\":\"clock_panel_third\",\"color\":\"light_orange\"}", AnimateClockPanelCategoryInfo.class));
        arrayMap2.put(CategoryConstants.SCRAPPED_CATEGORY_HORIZONTAL, (StyleInfo) gson.fromJson(CategoryConstants.DEFAULT_PARAM, LinearTimeCategoryInfo.class));
        arrayMap2.put(CategoryConstants.SCRAPPED_CATEGORY_VERTICAL, (StyleInfo) gson.fromJson("{\"is_vertical\":true}", LinearTimeCategoryInfo.class));
        arrayMap2.put(CategoryConstants.SCRAPPED_CATEGORY_HORIZONTAL_LEFT, (StyleInfo) gson.fromJson("{\"is_left_gravity\":true}", LinearTimeCategoryInfo.class));
        arrayMap3.put(CategoryConstants.CATEGORY_LINEAR_TIMETEXT, LinearTimeCategoryInfo.class);
        arrayMap3.put(CategoryConstants.CATEGORY_PAINT_VERTICAL, PaintVerticalCategoryInfo.class);
        arrayMap3.put(CategoryConstants.CATEGORY_ONELINE, OnelineCategoryInfo.class);
        arrayMap3.put(CategoryConstants.CATEGORY_SCOPE, ScopeCategoryInfo.class);
        arrayMap3.put(CategoryConstants.CATEGORY_CLOCK_PANEL, ClockPanelCategoryInfo.class);
        arrayMap3.put(CategoryConstants.CATEGORY_ANIMATE_CLOCK_PANEL, AnimateClockPanelCategoryInfo.class);
        arrayMap3.put(CategoryConstants.CATEGORY_SUN, SunCategoryInfo.class);
        arrayMap3.put(CategoryConstants.CATEGORY_TEXTSTYLE, TextStyleCategoryInfo.class);
        arrayMap3.put(CategoryConstants.CATEGORY_IMAGE_BG_SELECTOR, ImageSelectorCategoryInfo.class);
        arrayMap3.put(CategoryConstants.CATEGORY_MAML, MamlCategoryInfo.class);
        arrayMap3.put(CategoryConstants.CATEGORY_SMART_COVER, SmartCoverStyleInfo.class);
        arrayMap3.put(CategoryConstants.CATEGORY_SUPER_WALLPAPER, SuperWallpaperCategoryInfo.class);
        arrayMap3.put(CategoryConstants.CATEGORY_BLANK_SUPER_WALLPAPER, BlankSuperWallpaperCategoryInfo.class);
        arrayMap3.put(CategoryConstants.CATEGORY_LINKAGE, LinkageClockCategoryInfo.class);
        arrayMap3.put(CategoryConstants.CATEGORY_BIG_TIME, BigTimeCategoryInfo.class);
        sClockCategory = new String[]{CategoryConstants.CATEGORY_ANIMATE_CLOCK_PANEL, CategoryConstants.CATEGORY_CLOCK_PANEL, CategoryConstants.CATEGORY_ONELINE, CategoryConstants.CATEGORY_SUN, CategoryConstants.CATEGORY_SCOPE, CategoryConstants.CATEGORY_PAINT_VERTICAL, CategoryConstants.CATEGORY_TEXTSTYLE, CategoryConstants.CATEGORY_LINEAR_TIMETEXT, CategoryConstants.CATEGORY_IMAGE_BG_SELECTOR, CategoryConstants.CATEGORY_SMART_COVER, CategoryConstants.CATEGORY_SUPER_WALLPAPER, CategoryConstants.CATEGORY_LINKAGE, CategoryConstants.CATEGORY_BIG_TIME};
        infoCache = new LruCache<>(5);
    }

    private CategoryFactory() {
    }

    public static void cleanCache() {
        infoCache.evictAll();
    }

    public static StyleInfo createStyleInfo(Context context, String str) {
        StyleInfo styleInfo;
        Map<String, Class<?>> map = sCategoryMap;
        if (!map.containsKey(str)) {
            str = CategoryConstants.getDefaultCategoryName(context);
        }
        Gson categoriesGson = getCategoriesGson();
        String aodUsingCategoryParam = Utils.getAodUsingCategoryParam(context, str);
        if (CategoryConstants.CATEGORY_SUPER_WALLPAPER.equals(str) && CategoryConstants.DEFAULT_PARAM.equals(aodUsingCategoryParam)) {
            aodUsingCategoryParam = "{\n  \"name\":\"super_wallpaper\",\n  \"clock_position_x\":0.34,\n  \"clock_position_y\":0.12,\n  \"dual_clock_position_x_anchor_right\":0.65,\n  \"dual_clock_position_y\":0.6\n}";
        }
        if (Utils.isAodProcess()) {
            styleInfo = (StyleInfo) categoriesGson.fromJson(aodUsingCategoryParam, (Class) map.get(str));
            styleInfo.initConfig(context);
        } else {
            LruCache<String, StyleInfo> lruCache = infoCache;
            StyleInfo styleInfo2 = (StyleInfo) lruCache.get(str);
            if (styleInfo2 == null) {
                StyleInfo styleInfo3 = (StyleInfo) categoriesGson.fromJson(aodUsingCategoryParam, (Class) map.get(str));
                styleInfo3.initConfig(context);
                lruCache.put(str, styleInfo3);
                styleInfo = styleInfo3;
            } else {
                styleInfo = styleInfo2;
            }
        }
        if (!(styleInfo instanceof SmartCoverStyleInfo) && AODSettings.isSmartCoverClose() && AODSettings.isSupportSmartCoverAodStyle()) {
            SmartCoverStyleInfo smartCoverStyleInfo = new SmartCoverStyleInfo();
            smartCoverStyleInfo.switchOnDateAndTime(styleInfo.isDateAndTimeSwitchOn());
            smartCoverStyleInfo.switchOnNotificationIcon(styleInfo.isNotificationSwitchOn());
            smartCoverStyleInfo.switchOnBatteryIcon(styleInfo.isBatterySwitchOn());
            smartCoverStyleInfo.setColorName(styleInfo.getColorName());
            smartCoverStyleInfo.setChangeColorAlongTime(styleInfo.isChangeColorAlongTime());
            return smartCoverStyleInfo;
        }
        FlipLinkageStyleController flipLinkageStyleController = FlipLinkageStyleController.INSTANCE;
        if (!flipLinkageStyleController.isUsingFlip(context) || Utils.isAodProcess() || !flipLinkageStyleController.isFlipped() || !DeviceHelper.isTinyScreen(context)) {
            return styleInfo;
        }
        FlipLinkageClockCategoryInfo flipLinkageClockCategoryInfo = new FlipLinkageClockCategoryInfo();
        flipLinkageClockCategoryInfo.switchOnDateAndTime(false);
        flipLinkageClockCategoryInfo.switchOnNotificationIcon(true);
        flipLinkageClockCategoryInfo.switchOnBatteryIcon(false);
        flipLinkageClockCategoryInfo.setLunarSwitchOn(false);
        return flipLinkageClockCategoryInfo;
    }

    public static StyleInfo createStyleInfo(Context context, String str, String str2) {
        Map<String, Class<?>> map = sCategoryMap;
        if (!map.containsKey(str)) {
            str = CategoryConstants.getDefaultCategoryName(context);
            str2 = CategoryConstants.DEFAULT_PARAM;
        }
        if (Utils.isAodProcess()) {
            return (StyleInfo) new Gson().fromJson(str2, (Class) map.get(str));
        }
        LruCache<String, StyleInfo> lruCache = infoCache;
        StyleInfo styleInfo = (StyleInfo) lruCache.get(str);
        if (styleInfo != null) {
            return styleInfo;
        }
        StyleInfo styleInfo2 = (StyleInfo) new Gson().fromJson(str2, (Class) map.get(str));
        lruCache.put(str, styleInfo2);
        return styleInfo2;
    }

    public static int getAodCategoryCount() {
        return sClockCategory.length;
    }

    public static String getAodCategoryName(int i) {
        return sClockCategory[i];
    }

    public static Gson getCategoriesGson() {
        RuntimeTypeAdapterFactory runtimeTypeAdapterFactoryOf = RuntimeTypeAdapterFactory.of(StyleInfo.class, bf.a);
        for (String str : sClockCategory) {
            runtimeTypeAdapterFactoryOf.registerSubtype(sCategoryMap.get(str), str);
        }
        return new GsonBuilder().registerTypeAdapterFactory(runtimeTypeAdapterFactoryOf).create();
    }

    public static StyleInfo getPresetStyleInfo(String str) {
        return sPresetStyles.get(str);
    }
}
