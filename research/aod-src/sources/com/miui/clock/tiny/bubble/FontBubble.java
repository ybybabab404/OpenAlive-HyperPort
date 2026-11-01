package com.miui.clock.tiny.bubble;

import android.graphics.Color;
import com.miui.clock.tiny.R;

/* JADX INFO: loaded from: classes.dex */
public class FontBubble {
    public static final int[] mAodResource = {R.drawable.tiny_bubble_aod_svg_0, R.drawable.tiny_bubble_aod_svg_1, R.drawable.tiny_bubble_aod_svg_2, R.drawable.tiny_bubble_aod_svg_3, R.drawable.tiny_bubble_aod_svg_4, R.drawable.tiny_bubble_aod_svg_5, R.drawable.tiny_bubble_aod_svg_6, R.drawable.tiny_bubble_aod_svg_7, R.drawable.tiny_bubble_aod_svg_8, R.drawable.tiny_bubble_aod_svg_9};
    public static final String[] m3DResourceNames = {"tiny_bubble_0", "tiny_bubble_1", "tiny_bubble_2", "tiny_bubble_3", "tiny_bubble_4", "tiny_bubble_5", "tiny_bubble_6", "tiny_bubble_7", "tiny_bubble_8", "tiny_bubble_9"};
    public static final String[] SOLID_RESOURCE_NAMES_STYLE_2 = {"tiny_bubble_solid_style1_0", "tiny_bubble_solid_style1_1", "tiny_bubble_solid_style1_2", "tiny_bubble_solid_style1_3", "tiny_bubble_solid_style1_4", "tiny_bubble_solid_style1_5", "tiny_bubble_solid_style1_6", "tiny_bubble_solid_style1_7", "tiny_bubble_solid_style1_8", "tiny_bubble_solid_style1_9"};
    public static final String[] SOLID_RESOURCE_NAMES_STYLE_3 = {"tiny_bubble_solid_style2_0", "tiny_bubble_solid_style2_1", "tiny_bubble_solid_style2_2", "tiny_bubble_solid_style2_3", "tiny_bubble_solid_style2_4", "tiny_bubble_solid_style2_5", "tiny_bubble_solid_style2_6", "tiny_bubble_solid_style2_7", "tiny_bubble_solid_style2_8", "tiny_bubble_solid_style2_9"};
    public static final String[] SOLID_RESOURCE_NAMES_STYLE_4 = {"tiny_bubble_solid_style3_0", "tiny_bubble_solid_style3_1", "tiny_bubble_solid_style3_2", "tiny_bubble_solid_style3_3", "tiny_bubble_solid_style3_4", "tiny_bubble_solid_style3_5", "tiny_bubble_solid_style3_6", "tiny_bubble_solid_style3_7", "tiny_bubble_solid_style3_8", "tiny_bubble_solid_style3_9"};
    public static final String[] SOLID_RESOURCE_NAMES_STYLE_5 = {"tiny_bubble_solid_style4_0", "tiny_bubble_solid_style4_1", "tiny_bubble_solid_style4_2", "tiny_bubble_solid_style4_3", "tiny_bubble_solid_style4_4", "tiny_bubble_solid_style4_5", "tiny_bubble_solid_style4_6", "tiny_bubble_solid_style4_7", "tiny_bubble_solid_style4_8", "tiny_bubble_solid_style4_9"};
    public static final int[] BUBBLE_AOD_COLOR = {Color.parseColor("#EBEAEC"), Color.parseColor("#EBEAEC"), Color.parseColor("#C3B3DB"), Color.parseColor("#D7A493"), Color.parseColor("#569184"), Color.parseColor("#88B2DE")};

    public static String[] get3DResourceNames() {
        return m3DResourceNames;
    }

    public static String[] get3DSolidResourceNamesForStyle2() {
        return SOLID_RESOURCE_NAMES_STYLE_2;
    }

    public static String[] get3DSolidResourceNamesForStyle3() {
        return SOLID_RESOURCE_NAMES_STYLE_3;
    }

    public static String[] get3DSolidResourceNamesForStyle4() {
        return SOLID_RESOURCE_NAMES_STYLE_4;
    }

    public static String[] get3DSolidResourceNamesForStyle5() {
        return SOLID_RESOURCE_NAMES_STYLE_5;
    }

    public static int[] getAodResource() {
        return mAodResource;
    }

    public static int[] getBubbleAodColor() {
        return BUBBLE_AOD_COLOR;
    }
}
