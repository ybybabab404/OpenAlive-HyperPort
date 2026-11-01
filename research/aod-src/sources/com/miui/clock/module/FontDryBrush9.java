package com.miui.clock.module;

import com.miui.aod.category.TextStyleCategoryInfo;
import com.miui.clock.R;
import com.xiaomi.onetrack.g.d;

/* JADX INFO: loaded from: classes.dex */
public class FontDryBrush9 extends RotateFontStyle {
    public final int[] mSVGResource = {R.drawable.font_i_0, R.drawable.font_i_1, R.drawable.font_i_2, R.drawable.font_i_3, R.drawable.font_i_4, R.drawable.font_i_5, R.drawable.font_i_6, R.drawable.font_i_7, R.drawable.font_i_8, R.drawable.font_i_9};
    public final float[] mPercentages = {0.67f, 0.13f, 0.08f, 0.08f, 0.28f, 0.11f, 0.06f};
    public final float[] mCenterXOffset = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 21.5f, 0.0f, 0.0f, 0.0f, 0.0f};
    public final float[] mNotificationCenterXOffset = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, -18.0f, 0.0f, 0.0f, 0.0f, 0.0f};
    public final int[][][] mHourOffset = {new int[][]{new int[]{com.miui.maml.R.styleable.AppCompatTheme_tooltipFrameBackground, 67, 157, 167}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_textColorSearchUrl, 71, 157, 154}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_textColorSearchUrl, 68, 162, 138}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_windowFixedWidthMinor, 68, 168, com.miui.maml.R.styleable.AppCompatTheme_windowMinWidthMinor}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMajor, 68, 164, 129}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_windowNoTitle, 79, 161, 149}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_tooltipForegroundColor, 67, 159, 155}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_textColorAlertDialogListItem, 68, 186, com.miui.maml.R.styleable.AppCompatTheme_windowFixedWidthMajor}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle, 65, 169, 158}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_windowActionBar, 65, 186, com.miui.maml.R.styleable.AppCompatTheme_windowMinWidthMinor}}, new int[][]{new int[]{131, 59, 159, 165}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_windowActionBar, 61, 176, 147}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_windowMinWidthMajor, 63, 160, 139}, new int[]{130, 63, 168, com.miui.maml.R.styleable.AppCompatTheme_windowMinWidthMinor}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_windowMinWidthMajor, 59, 164, com.miui.maml.R.styleable.AppCompatTheme_windowNoTitle}, new int[]{128, 56, 158, 148}, new int[]{134, 53, 155, 153}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_textAppearanceSmallPopupMenu, 61, 186, com.miui.maml.R.styleable.AppCompatTheme_windowFixedWidthMajor}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMajor, 55, 177, 151}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_windowMinWidthMajor, 59, 178, com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMinor}}, new int[][]{new int[]{com.miui.maml.R.styleable.AppCompatTheme_tooltipFrameBackground, 59, 158, 161}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_textColorSearchUrl, 55, 178, 154}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_textAppearanceSmallPopupMenu, 51, 168, 133}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMinor, 50, 168, com.miui.maml.R.styleable.AppCompatTheme_windowMinWidthMinor}}};
    public final int[][][] mMinuteOffset = {new int[][]{new int[]{168, 53, 99, 155}, new int[]{175, 59, 92, 146}, new int[]{164, 56, com.miui.maml.R.styleable.AppCompatTheme_textAppearanceSmallPopupMenu, 140}, new int[]{168, 53, com.miui.maml.R.styleable.AppCompatTheme_viewInflaterClass, 140}, new int[]{162, 58, com.miui.maml.R.styleable.AppCompatTheme_windowMinWidthMajor, 127}, new int[]{166, 54, com.miui.maml.R.styleable.AppCompatTheme_windowActionModeOverlay, 147}, new int[]{174, 47, 103, 141}, new int[]{168, 53, com.miui.maml.R.styleable.AppCompatTheme_windowActionBarOverlay, 146}, new int[]{168, 53, com.miui.maml.R.styleable.AppCompatTheme_textAppearancePopupMenuHeader, 155}, new int[]{175, 53, com.miui.maml.R.styleable.AppCompatTheme_toolbarStyle, 151}}, new int[][]{new int[]{179, 70, com.miui.maml.R.styleable.AppCompatTheme_toolbarStyle, 155}, new int[]{179, 70, com.miui.maml.R.styleable.AppCompatTheme_textAppearanceSmallPopupMenu, TextStyleCategoryInfo.MIN_FONT_WEIGHT}, new int[]{179, 70, 98, 144}, new int[]{179, 70, com.miui.maml.R.styleable.AppCompatTheme_windowActionBar, 151}, new int[]{179, 70, com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle, 134}, new int[]{175, 84, com.miui.maml.R.styleable.AppCompatTheme_windowActionModeOverlay, 147}, new int[]{179, 70, com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMajor, 154}, new int[]{179, 70, com.miui.maml.R.styleable.AppCompatTheme_windowActionBarOverlay, 146}, new int[]{179, 70, com.miui.maml.R.styleable.AppCompatTheme_windowActionBar, 157}, new int[]{182, 79, com.miui.maml.R.styleable.AppCompatTheme_toolbarStyle, 154}}, new int[][]{new int[]{171, 46, com.miui.maml.R.styleable.AppCompatTheme_toolbarStyle, 154}, new int[]{171, 46, com.miui.maml.R.styleable.AppCompatTheme_textColorSearchUrl, 161}, new int[]{171, 46, com.miui.maml.R.styleable.AppCompatTheme_textAppearancePopupMenuHeader, 145}, new int[]{177, 46, com.miui.maml.R.styleable.AppCompatTheme_windowFixedWidthMinor, 140}, new int[]{171, 46, com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMajor, 127}, new int[]{169, 42, com.miui.maml.R.styleable.AppCompatTheme_windowActionBarOverlay, 152}, new int[]{169, 42, com.miui.maml.R.styleable.AppCompatTheme_textAppearanceSmallPopupMenu, 144}, new int[]{171, 46, com.miui.maml.R.styleable.AppCompatTheme_windowMinWidthMajor, 146}, new int[]{171, 46, com.miui.maml.R.styleable.AppCompatTheme_toolbarStyle, 152}, new int[]{171, 46, com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMinor, 152}}, new int[][]{new int[]{178, 41, 104, 157}, new int[]{178, 37, 103, 147}, new int[]{178, 43, 100, 143}, new int[]{178, 41, com.miui.maml.R.styleable.AppCompatTheme_windowActionModeOverlay, 146}, new int[]{178, 41, com.miui.maml.R.styleable.AppCompatTheme_windowActionBar, 130}, new int[]{178, 41, com.miui.maml.R.styleable.AppCompatTheme_windowActionModeOverlay, 147}, new int[]{178, 35, 103, 156}, new int[]{179, 41, com.miui.maml.R.styleable.AppCompatTheme_windowMinWidthMajor, 146}, new int[]{179, 37, com.miui.maml.R.styleable.AppCompatTheme_textAppearanceSearchResultTitle, 155}, new int[]{181, 39, com.miui.maml.R.styleable.AppCompatTheme_windowFixedWidthMinor, 154}}, new int[][]{new int[]{163, 37, com.miui.maml.R.styleable.AppCompatTheme_textAppearanceListItemSmall, 154}, new int[]{163, 37, 99, 146}, new int[]{164, 30, 97, 141}, new int[]{163, 37, com.miui.maml.R.styleable.AppCompatTheme_toolbarStyle, 143}, new int[]{163, 37, com.miui.maml.R.styleable.AppCompatTheme_textAppearanceSearchResultTitle, 131}, new int[]{163, 37, com.miui.maml.R.styleable.AppCompatTheme_windowActionModeOverlay, 147}, new int[]{163, 37, com.miui.maml.R.styleable.AppCompatTheme_textAppearancePopupMenuHeader, TextStyleCategoryInfo.MIN_FONT_WEIGHT}, new int[]{163, 37, com.miui.maml.R.styleable.AppCompatTheme_windowActionModeOverlay, 146}, new int[]{163, 37, com.miui.maml.R.styleable.AppCompatTheme_textAppearanceSearchResultTitle, 155}, new int[]{161, 32, com.miui.maml.R.styleable.AppCompatTheme_windowActionBar, 154}}, new int[][]{new int[]{136, 56, com.miui.maml.R.styleable.AppCompatTheme_textAppearanceSmallPopupMenu, 155}, new int[]{136, 56, 98, 146}, new int[]{130, 59, 93, 139}, new int[]{136, 56, com.miui.maml.R.styleable.AppCompatTheme_textAppearanceListItemSmall, 145}, new int[]{132, 56, 97, com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMajor}, new int[]{135, 61, com.miui.maml.R.styleable.AppCompatTheme_windowMinWidthMinor, 147}, new int[]{136, 56, com.miui.maml.R.styleable.AppCompatTheme_textAppearancePopupMenuHeader, 159}, new int[]{129, 54, com.miui.maml.R.styleable.AppCompatTheme_toolbarStyle, 152}, new int[]{136, 56, com.miui.maml.R.styleable.AppCompatTheme_textAppearanceSearchResultTitle, 155}, new int[]{136, 56, com.miui.maml.R.styleable.AppCompatTheme_textAppearancePopupMenuHeader, 154}}};
    public final float[] mSVGEmptyLeft = {9.904762f, 8.761905f, 7.6190476f, 0.3809524f, 25.52381f, 0.0f, 8.0f, 7.2380953f, 8.0f, 8.761905f};
    public final float[] mSVGEmptyRight = {122.666664f, 100.57143f, 132.19048f, 104.7619f, 107.04762f, 164.19048f, 120.0f, 93.71429f, 120.38095f, 115.04762f};
    public final int[] mLargeClockWidth = {135, com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle, 129, com.miui.maml.R.styleable.AppCompatTheme_windowActionModeOverlay, 134, 166, com.miui.maml.R.styleable.AppCompatTheme_windowMinWidthMinor, 104, 138, com.miui.maml.R.styleable.AppCompatTheme_windowFixedWidthMinor};
    public final int[] mLargeClockHeight = {240, 240, 240, 240, 240, 240, 240, 240, 240, 240};
    public final float[][][] mHourRotateDegree = {new float[][]{new float[]{7.72f, -3.97f}, new float[]{0.0f, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{11.02f, 8.33f}, new float[]{0.0f, 0.0f}, new float[]{17.91f, -4.08f}, new float[]{-3.34f, 0.0f}, new float[]{-4.99f, 0.0f}, new float[]{8.68f, 0.0f}, new float[]{0.0f, 2.29f}}, new float[][]{new float[]{0.0f, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{0.0f, 8.33f}, new float[]{0.0f, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{0.0f, -3.66f}}, new float[][]{new float[]{9.76f, 0.0f}, new float[]{0.0f, 7.9f}, new float[]{3.45f, 0.0f}, new float[]{0.0f, 8.33f}}};
    public final float[][][] mMinuteRotateDegree = {new float[][]{new float[]{-5.36f, 6.72f}, new float[]{0.0f, 0.0f}, new float[]{-5.66f, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{-6.81f, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{0.0f, -3.96f}, new float[]{0.0f, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{-5.69f, 1.94f}}, new float[][]{new float[]{0.0f, -2.39f}, new float[]{0.0f, -7.15f}, new float[]{0.0f, 5.07f}, new float[]{0.0f, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{0.0f, 1.94f}}, new float[][]{new float[]{4.0f, -4.93f}, new float[]{4.0f, 0.0f}, new float[]{4.0f, 3.66f}, new float[]{4.0f, 0.0f}, new float[]{4.0f, 0.0f}, new float[]{-3.66f, 0.0f}, new float[]{-3.66f, -3.96f}, new float[]{4.0f, 0.0f}, new float[]{4.0f, 0.0f}, new float[]{4.0f, 1.94f}}, new float[][]{new float[]{2.0f, 4.11f}, new float[]{8.9f, -9.7f}, new float[]{-0.53f, 7.44f}, new float[]{6.29f, 0.0f}, new float[]{2.0f, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{2.0f, 0.0f}, new float[]{-3.41f, 0.0f}, new float[]{2.0f, 0.0f}, new float[]{0.0f, 1.94f}}, new float[][]{new float[]{0.0f, -1.66f}, new float[]{0.0f, -7.15f}, new float[]{-4.27f, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{-4.8f, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{3.72f, 1.94f}}, new float[][]{new float[]{0.0f, 0.0f}, new float[]{0.0f, -7.15f}, new float[]{-3.87f, 0.0f}, new float[]{0.0f, 6.16f}, new float[]{-4.45f, 0.0f}, new float[]{-5.17f, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{0.0f, 0.0f}, new float[]{0.0f, 1.94f}}};

    public FontDryBrush9() {
        this.rotatePlus = new int[][][]{new int[][]{new int[]{309, 336}, new int[]{313, 351}, new int[]{316, 365}, new int[]{319, 379}, new int[]{322, 393}, new int[]{325, 407}, new int[]{328, 421}, new int[]{330, 434}, new int[]{333, 448}, new int[]{337, 462}, new int[]{340, 476}, new int[]{343, 490}, new int[]{346, 504}, new int[]{349, 517}, new int[]{352, 530}, new int[]{356, 544}, new int[]{358, 557}, new int[]{360, 569}, new int[]{363, 582}}, new int[][]{new int[]{252, 276}, new int[]{260, 291}, new int[]{267, 306}, new int[]{273, 320}, new int[]{279, 334}, new int[]{285, 348}, new int[]{292, 363}, new int[]{299, 377}, new int[]{304, 390}}, new int[][]{new int[]{342, 363}, new int[]{344, 373}, new int[]{345, 383}, new int[]{345, 392}, new int[]{346, d.b}, new int[]{346, 410}, new int[]{346, 419}, new int[]{347, 428}, new int[]{347, 437}, new int[]{348, 446}, new int[]{347, 454}}, new int[][]{new int[]{286, 287}, new int[]{290, 297}, new int[]{293, 307}, new int[]{295, 316}, new int[]{298, 326}, new int[]{300, 335}, new int[]{306, 347}, new int[]{310, 358}, new int[]{315, 370}, new int[]{319, 381}}, new int[][]{new int[]{223, 293}, new int[]{224, 306}, new int[]{224, 318}, new int[]{224, 330}, new int[]{223, 342}}, null, null, null, null, new int[][]{new int[]{291, 315}, new int[]{293, 331}, new int[]{294, 347}, new int[]{295, 363}}};
        this.rotateMinus = new int[][][]{new int[][]{new int[]{309, 336}, new int[]{308, 345}, new int[]{306, 353}, new int[]{303, 361}, new int[]{301, 369}, new int[]{299, 377}, new int[]{297, 385}, new int[]{293, 392}}, new int[][]{new int[]{252, 276}, new int[]{246, 281}, new int[]{240, 286}, new int[]{234, 291}, new int[]{233, 299}, new int[]{236, 310}, new int[]{238, 321}, new int[]{239, 331}, new int[]{242, 342}, new int[]{243, 352}, new int[]{246, 363}}, new int[][]{new int[]{342, 363}, new int[]{342, 375}, new int[]{341, 387}, new int[]{340, 398}, new int[]{339, 410}}, new int[][]{new int[]{286, 287}, new int[]{285, 296}, new int[]{283, 305}, new int[]{281, 313}, new int[]{278, 321}}, new int[][]{new int[]{223, 293}, new int[]{224, 301}, new int[]{224, 308}, new int[]{223, 314}, new int[]{224, 321}, new int[]{224, 328}}, new int[][]{new int[]{451, 451}, new int[]{450, 457}, new int[]{450, 463}, new int[]{448, 468}, new int[]{446, 473}, new int[]{444, 478}, new int[]{442, 483}}, new int[][]{new int[]{307, 330}, new int[]{309, 340}, new int[]{310, 350}, new int[]{311, 360}, new int[]{311, 369}}, null, null, new int[][]{new int[]{291, 315}, new int[]{291, 322}, new int[]{289, 328}, new int[]{288, 334}, new int[]{287, 341}}};
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public boolean enableRotate() {
        return true;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getAodDateMarginTop() {
        return 40;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public float getColonInterval() {
        return 42.0f;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getColonResource() {
        int i = this.mUserDefinedColonResource;
        return i != 0 ? i : R.drawable.miui_rhombus_colon_circle;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public float getColonSize() {
        return 36.0f;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public BaseFontStyle.Style getFontStyle() {
        return BaseFontStyle.Style.DryBrush9;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int[][][] getHourOffset() {
        return this.mHourOffset;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public float[][][] getHourRotateDegree() {
        return this.mHourRotateDegree;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int[] getLargeClockHeights() {
        return this.mLargeClockHeight;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int[] getLargeClockWidth() {
        return this.mLargeClockWidth;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public float[] getLeftEmpty() {
        return this.mSVGEmptyLeft;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public float getMaxRotateDegree() {
        return 18.0f;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int[][][] getMinuteOffset() {
        return this.mMinuteOffset;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public float[][][] getMinuteRotateDegree() {
        return this.mMinuteRotateDegree;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public float[] getNotificationCenterXOffset() {
        return this.mNotificationCenterXOffset;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public float[] getPercentages() {
        return this.mPercentages;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public float[] getRightEmpty() {
        return this.mSVGEmptyRight;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getSmallClockInnerHeight() {
        return 100;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getSmallClockInnerMarginOffset() {
        return -3;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getSmallColonResource() {
        int i = this.mUserDefinedSmallColonResource;
        return i != 0 ? i : R.drawable.font_i_colon;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getSmallColonResourceHeight() {
        return 240;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int[] getSvgResource() {
        return this.mSVGResource;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getTextBoxWidth() {
        return 215;
    }
}
