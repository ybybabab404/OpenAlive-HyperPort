package com.miui.aod.category;

import android.content.Context;
import com.miui.keyguard.editor.utils.DeviceUtil;

/* JADX INFO: loaded from: classes.dex */
public class CategoryConstants {
    public static final String CATEGORY_ANIMATE_CLOCK_PANEL = "animate_clock_panel";
    public static final String CATEGORY_BIG_TIME = "big_time";
    public static final String CATEGORY_BLANK_SUPER_WALLPAPER = "blank_super_wallpaper";
    public static final String CATEGORY_CLOCK_PANEL = "clock_panel";
    public static final String CATEGORY_DUAL_CLOCK = "dual_clock";
    public static final String CATEGORY_IMAGE_BG_SELECTOR = "image_bg_selector";
    public static final String CATEGORY_LINEAR_TIMETEXT = "linear_timetext";
    public static final String CATEGORY_LINKAGE = "Linkage";
    public static final String CATEGORY_LINKAGE_FLIP = "Flip_Linkage";
    public static final String CATEGORY_MAML = "maml";
    public static final String CATEGORY_MAML_SMALL = "small";
    public static final String CATEGORY_MAML_STYLE = "maml_style";
    public static final String CATEGORY_MAML_THUMBNAIL = "maml_thumbnail";
    public static final String CATEGORY_ONELINE = "oneline";
    public static final String CATEGORY_PAINT_VERTICAL = "paint_vertical";
    public static final String CATEGORY_SCOPE = "scope";
    public static final String CATEGORY_SMART_COVER = "smart_cover";
    public static final String CATEGORY_SUN = "sun";
    public static final String CATEGORY_SUPER_WALLPAPER = "super_wallpaper";
    public static final String CATEGORY_TEXTSTYLE = "text_style";
    public static final String DEFAULT_PARAM = "{}";
    public static final String LABEL_SINGLE_24H = "label_single_24h";
    public static final String LABEL_SINGLE_MOTION = "label_single_motion";
    public static final int LARGE = 1;
    public static final int REAL = 3;
    public static final String SCRAPPED_CATEGORY_HORIZONTAL = "horizontal";
    public static final String SCRAPPED_CATEGORY_HORIZONTAL_LEFT = "horizontal_left";
    public static final String SCRAPPED_CATEGORY_VERTICAL = "vertical";
    public static final int SMALL = 2;
    public static float SSMALL_RADIO = 1.0f;
    public static final int THUMBNAIL = 4;
    public static final int THUMBNAIL_PROVIDER = 6;
    public static final int THUMBNAIL_REAL = 5;

    private CategoryConstants() {
    }

    public static String getDefaultCategoryName(Context context) {
        return DeviceUtil.INSTANCE.isPad() ? CATEGORY_BIG_TIME : CATEGORY_LINKAGE;
    }
}
