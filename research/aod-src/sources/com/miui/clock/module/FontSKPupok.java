package com.miui.clock.module;

import com.miui.aod.category.TextStyleCategoryInfo;
import com.miui.clock.R;
import com.xiaomi.onetrack.g.d;

/* JADX INFO: loaded from: classes.dex */
public class FontSKPupok extends RotateFontStyle {
    public final int[] mSVGResource = {R.drawable.font_b_0, R.drawable.font_b_1, R.drawable.font_b_2, R.drawable.font_b_3, R.drawable.font_b_4, R.drawable.font_b_5, R.drawable.font_b_6, R.drawable.font_b_7, R.drawable.font_b_8, R.drawable.font_b_9};
    public final int[][][] mHourOffset = {new int[][]{new int[]{100, 67, 153, 166}, new int[]{99, 61, 153, 155}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_textAppearancePopupMenuHeader, 57, 147, 164}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_textColorAlertDialogListItem, 57, 158, 157}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_textColorSearchUrl, 56, TextStyleCategoryInfo.MIN_FONT_WEIGHT, 142}, new int[]{93, 64, TextStyleCategoryInfo.MIN_FONT_WEIGHT, 149}, new int[]{90, 70, TextStyleCategoryInfo.MIN_FONT_WEIGHT, 156}, new int[]{138, 53, 142, 135}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_windowFixedWidthMajor, 62, 145, 149}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_windowMinWidthMinor, 61, 148, 148}}, new int[][]{new int[]{88, 59, 155, 165}, new int[]{97, 55, TextStyleCategoryInfo.MIN_FONT_WEIGHT, 156}, new int[]{104, 50, 146, 158}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_tooltipFrameBackground, 46, 153, 147}, new int[]{101, 52, 151, 142}, new int[]{85, 58, 152, TextStyleCategoryInfo.MIN_FONT_WEIGHT}, new int[]{86, 60, 148, TextStyleCategoryInfo.MIN_FONT_WEIGHT}, new int[]{133, 47, 142, 133}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_tooltipForegroundColor, 47, 144, 145}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_textAppearanceSearchResultSubtitle, 49, 153, 152}}, new int[][]{new int[]{84, 54, 159, 170}, new int[]{95, 53, 151, 160}, new int[]{93, 47, 152, 167}, new int[]{com.miui.maml.R.styleable.AppCompatTheme_textAppearanceSmallPopupMenu, 43, 155, 157}}};
    public final int[][][] mMinuteOffset = {new int[][]{new int[]{152, 48, 100, 145}, new int[]{156, 49, 91, 160}, new int[]{156, 49, com.miui.maml.R.styleable.AppCompatTheme_textAppearanceListItemSmall, 148}, new int[]{154, 47, com.miui.maml.R.styleable.AppCompatTheme_tooltipForegroundColor, 156}, new int[]{152, 51, 96, 155}, new int[]{158, 51, 83, 148}, new int[]{152, 51, 89, 137}, new int[]{154, 51, com.miui.maml.R.styleable.AppCompatTheme_windowMinWidthMajor, 144}, new int[]{155, 51, com.miui.maml.R.styleable.AppCompatTheme_tooltipForegroundColor, 142}, new int[]{152, 51, com.miui.maml.R.styleable.AppCompatTheme_windowActionModeOverlay, 148}}, new int[][]{new int[]{158, 53, 93, 151}, new int[]{160, 45, 78, 159}, new int[]{158, 51, 104, 147}, new int[]{153, 49, com.miui.maml.R.styleable.AppCompatTheme_windowActionModeOverlay, 152}, new int[]{158, 46, 96, 145}, new int[]{152, 49, 87, 153}, new int[]{157, 54, 81, 141}, new int[]{157, 45, com.miui.maml.R.styleable.AppCompatTheme_textAppearanceListItemSmall, 146}, new int[]{163, 43, 99, 142}, new int[]{163, 47, 96, TextStyleCategoryInfo.MIN_FONT_WEIGHT}}, new int[][]{new int[]{152, 47, 104, TextStyleCategoryInfo.MIN_FONT_WEIGHT}, new int[]{152, 47, 99, TextStyleCategoryInfo.MIN_FONT_WEIGHT}, new int[]{153, 41, com.miui.maml.R.styleable.AppCompatTheme_textColorAlertDialogListItem, 145}, new int[]{153, 46, com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMajor, 155}, new int[]{151, 46, 101, 153}, new int[]{153, 47, 91, 146}, new int[]{152, 47, 83, 144}, new int[]{152, 44, com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMinor, 145}, new int[]{152, 44, com.miui.maml.R.styleable.AppCompatTheme_windowActionBar, 141}, new int[]{152, 41, com.miui.maml.R.styleable.AppCompatTheme_windowActionModeOverlay, 143}}, new int[][]{new int[]{152, 43, com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle, 151}, new int[]{149, 41, com.miui.maml.R.styleable.AppCompatTheme_textAppearanceSearchResultTitle, 159}, new int[]{155, 43, com.miui.maml.R.styleable.AppCompatTheme_windowActionModeOverlay, 146}, new int[]{TextStyleCategoryInfo.MIN_FONT_WEIGHT, 43, 134, TextStyleCategoryInfo.MIN_FONT_WEIGHT}, new int[]{156, 41, 101, 149}, new int[]{152, 43, 102, 143}, new int[]{151, 46, 103, 136}, new int[]{158, 40, com.miui.maml.R.styleable.AppCompatTheme_windowNoTitle, 140}, new int[]{157, 41, com.miui.maml.R.styleable.AppCompatTheme_windowFixedWidthMinor, 141}, new int[]{158, 41, com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMinor, 147}}, new int[][]{new int[]{149, 47, com.miui.maml.R.styleable.AppCompatTheme_textAppearanceSearchResultTitle, 148}, new int[]{TextStyleCategoryInfo.MIN_FONT_WEIGHT, 48, com.miui.maml.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle, 147}, new int[]{148, 51, com.miui.maml.R.styleable.AppCompatTheme_tooltipFrameBackground, 149}, new int[]{151, 54, com.miui.maml.R.styleable.AppCompatTheme_viewInflaterClass, 161}, new int[]{148, 51, 104, 153}, new int[]{152, 52, 82, 157}, new int[]{146, 55, 94, 142}, new int[]{149, 55, com.miui.maml.R.styleable.AppCompatTheme_windowFixedHeightMinor, 146}, new int[]{149, 49, com.miui.maml.R.styleable.AppCompatTheme_tooltipFrameBackground, 144}, new int[]{148, 45, com.miui.maml.R.styleable.AppCompatTheme_tooltipFrameBackground, 152}}, new int[][]{new int[]{152, 50, 93, 146}, new int[]{152, 50, 81, 157}, new int[]{152, 50, 99, 146}, new int[]{152, 50, 104, 153}, new int[]{152, 50, 85, 151}, new int[]{152, 50, 82, 142}, new int[]{152, 50, 64, TextStyleCategoryInfo.MIN_FONT_WEIGHT}, new int[]{152, 50, 100, 147}, new int[]{152, 50, com.miui.maml.R.styleable.AppCompatTheme_textAppearancePopupMenuHeader, 141}, new int[]{152, 50, 104, 149}}};
    public final float[][][] mHourRotateDegree = {new float[][]{new float[]{6.65f, -12.74f}, new float[]{-2.86f, 5.0f}, new float[]{0.0f, 0.0f}, new float[]{7.28f, -7.08f}, new float[]{-13.25f, 7.97f}, new float[]{5.07f, -4.72f}, new float[]{4.39f, -12.81f}, new float[]{0.0f, -23.99f}, new float[]{0.0f, -11.28f}, new float[]{-5.14f, -8.83f}}, new float[][]{new float[]{13.11f, -9.04f}, new float[]{5.07f, 7.78f}, new float[]{11.01f, -8.15f}, new float[]{6.8f, -14.51f}, new float[]{8.24f, 7.97f}, new float[]{19.6f, -1.99f}, new float[]{13.91f, -15.08f}, new float[]{13.57f, -23.99f}, new float[]{17.99f, -13.35f}, new float[]{14.95f, 0.0f}}, new float[][]{new float[]{3.78f, 2.4f}, new float[]{-4.63f, 12.68f}, new float[]{0.0f, 6.03f}, new float[]{-9.24f, 4.08f}}};
    public final float[][][] mMinuteRotateDegree = {new float[][]{new float[]{-9.03f, 9.88f}, new float[]{-4.93f, -7.19f}, new float[]{-10.55f, 10.95f}, new float[]{-12.82f, -19.46f}, new float[]{-15.87f, 12.67f}, new float[]{-9.84f, 4.0f}, new float[]{-14.43f, 6.59f}, new float[]{-7.92f, -16.37f}, new float[]{-2.34f, 4.41f}, new float[]{-13.83f, -8.86f}}, new float[][]{new float[]{4.5f, 0.0f}, new float[]{14.03f, 0.0f}, new float[]{4.5f, 0.0f}, new float[]{-1.42f, -17.01f}, new float[]{4.5f, -13.71f}, new float[]{-1.66f, -8.01f}, new float[]{7.54f, 4.26f}, new float[]{10.06f, -25.09f}, new float[]{12.9f, 6.07f}, new float[]{12.9f, 0.0f}}, new float[][]{new float[]{-4.39f, 0.0f}, new float[]{-11.38f, 13.57f}, new float[]{-11.38f, 15.3f}, new float[]{0.0f, 0.0f}, new float[]{-11.43f, 5.0f}, new float[]{0.0f, 0.0f}, new float[]{-7.61f, 0.0f}, new float[]{2.03f, -18.47f}, new float[]{-5.26f, -10.7f}, new float[]{-12.63f, 5.05f}}, new float[][]{new float[]{0.0f, -8.64f}, new float[]{-9.73f, -2.58f}, new float[]{3.8f, 4.69f}, new float[]{0.0f, -5.05f}, new float[]{5.61f, 13.68f}, new float[]{0.0f, 4.0f}, new float[]{-3.71f, 5.55f}, new float[]{0.0f, -21.34f}, new float[]{-8.97f, -6.0f}, new float[]{-6.49f, -8.12f}}, new float[][]{new float[]{-2.93f, 2.87f}, new float[]{0.0f, 11.86f}, new float[]{-10.28f, 0.0f}, new float[]{-6.35f, -9.64f}, new float[]{-10.28f, 11.87f}, new float[]{-2.38f, -10.06f}, new float[]{-7.51f, -2.27f}, new float[]{-0.84f, -27.03f}, new float[]{-6.73f, -7.27f}, new float[]{-1.56f, -4.98f}}, new float[][]{new float[]{-4.13f, 9.55f}, new float[]{-4.13f, 8.34f}, new float[]{-4.13f, 11.12f}, new float[]{-4.13f, 15.47f}, new float[]{-4.13f, 14.41f}, new float[]{-4.13f, 4.0f}, new float[]{-4.13f, -6.95f}, new float[]{-4.13f, -29.88f}, new float[]{-4.13f, -8.95f}, new float[]{-4.13f, -7.66f}}};
    public final float[] mPercentages = {0.72f, 0.17f, 0.1f, 0.1f, 0.28f, 0.11f, 0.05f};
    public final float[] mSVGEmptyLeft = {7.6190476f, 8.0f, 7.6190476f, 8.0f, 8.380953f, 8.0f, 7.6190476f, 7.6190476f, 7.6190476f, 7.6190476f};
    public final float[] mSVGEmptyRight = {163.42857f, 144.0f, 169.5238f, 160.0f, 161.5238f, 169.90475f, 179.04762f, 198.4762f, 183.2381f, 159.61905f};
    public final int[] mLargeClockWidth = {171, 152, 177, 168, 170, 178, 187, 206, 191, 167};
    public final int[] mLargeClockHeight = {177, 200, 186, 194, 208, 194, 184, 186, 186, 196};

    public FontSKPupok() {
        this.rotatePlus = new int[][][]{new int[][]{new int[]{429, 449}, new int[]{434, 459}, new int[]{437, 468}, new int[]{441, 477}, new int[]{444, 485}, new int[]{447, 494}, new int[]{451, 503}, new int[]{455, 512}, new int[]{458, 520}, new int[]{461, 528}, new int[]{465, 537}}, new int[][]{new int[]{373, 395}, new int[]{377, 406}, new int[]{381, 417}, new int[]{383, 427}, new int[]{387, 438}, new int[]{389, 448}, new int[]{393, 459}, new int[]{396, 469}, new int[]{398, 479}, new int[]{d.b, 489}, new int[]{d.c, 499}, new int[]{407, 509}, new int[]{409, 518}, new int[]{412, 528}, new int[]{414, 537}, new int[]{417, 547}, new int[]{419, 556}, new int[]{421, 565}, new int[]{424, 574}, new int[]{425, 582}, new int[]{427, 591}}, new int[][]{new int[]{444, 465}, new int[]{444, 473}, new int[]{444, 481}, new int[]{444, 489}, new int[]{443, 496}, new int[]{442, 503}, new int[]{443, 511}, new int[]{442, 518}, new int[]{441, 525}, new int[]{439, 531}, new int[]{439, 538}, new int[]{442, 545}, new int[]{444, 551}, new int[]{446, 557}, new int[]{448, 563}, new int[]{450, 569}, new int[]{452, 575}}, new int[][]{new int[]{417, 439}, new int[]{417, 448}, new int[]{414, 455}, new int[]{413, 463}, new int[]{411, 471}, new int[]{409, 478}, new int[]{407, 486}, new int[]{405, 493}, new int[]{d.c, 500}, new int[]{407, 507}, new int[]{410, 514}, new int[]{412, 520}, new int[]{415, 527}, new int[]{417, 533}, new int[]{419, 539}, new int[]{421, 545}, new int[]{423, 551}}, new int[][]{new int[]{421, 444}, new int[]{422, 453}, new int[]{421, 461}, new int[]{420, 469}, new int[]{420, 477}, new int[]{419, 485}, new int[]{418, 493}, new int[]{417, 500}, new int[]{416, 508}, new int[]{415, 515}, new int[]{413, 522}, new int[]{412, 529}, new int[]{411, 536}, new int[]{409, 543}, new int[]{407, 549}, new int[]{406, 556}}, new int[][]{new int[]{445, 467}, new int[]{453, 479}, new int[]{460, 491}, new int[]{466, 502}, new int[]{472, 513}, new int[]{478, 524}}, new int[][]{new int[]{470, 491}, new int[]{476, 502}, new int[]{482, 513}, new int[]{488, 524}, new int[]{493, 535}, new int[]{498, 545}, new int[]{503, 555}, new int[]{508, 565}}, null, new int[][]{new int[]{482, 503}, new int[]{482, 511}, new int[]{481, 519}, new int[]{480, 526}, new int[]{478, 533}, new int[]{478, 541}, new int[]{477, 548}, new int[]{474, 554}}, new int[][]{new int[]{417, 438}, new int[]{419, 448}, new int[]{421, 457}, new int[]{426, 466}, new int[]{431, 475}, new int[]{436, 484}, new int[]{440, 492}}};
        this.rotateMinus = new int[][][]{new int[][]{new int[]{429, 449}, new int[]{426, 453}, new int[]{422, 456}, new int[]{418, 459}, new int[]{414, 462}, new int[]{411, 465}, new int[]{407, 468}, new int[]{403, 471}, new int[]{400, 474}, new int[]{396, 476}, new int[]{392, 479}, new int[]{389, 482}, new int[]{385, 484}, new int[]{382, 487}, new int[]{378, 489}, new int[]{375, 491}, new int[]{372, 494}}, new int[][]{new int[]{373, 395}, new int[]{371, 399}, new int[]{368, 402}, new int[]{365, 405}, new int[]{362, 408}, new int[]{359, 411}, new int[]{356, 414}, new int[]{353, 417}, new int[]{351, 420}}, new int[][]{new int[]{444, 465}, new int[]{445, 471}, new int[]{445, 476}, new int[]{445, 481}, new int[]{445, 486}, new int[]{446, 491}, new int[]{446, 496}, new int[]{445, 500}, new int[]{445, 505}, new int[]{444, 509}, new int[]{443, 513}, new int[]{442, 517}, new int[]{442, 521}, new int[]{441, 525}}, new int[][]{new int[]{417, 439}, new int[]{419, 445}, new int[]{421, 451}, new int[]{423, 457}, new int[]{424, 462}, new int[]{426, 468}, new int[]{427, 473}, new int[]{428, 478}, new int[]{429, 483}, new int[]{431, 488}, new int[]{432, 493}, new int[]{432, 497}, new int[]{434, 502}, new int[]{434, 506}, new int[]{435, 511}, new int[]{436, 515}, new int[]{436, 519}, new int[]{437, 523}, new int[]{437, 527}, new int[]{438, 531}, new int[]{438, 535}}, new int[][]{new int[]{421, 444}, new int[]{423, 451}, new int[]{423, 457}, new int[]{423, 463}, new int[]{424, 469}, new int[]{424, 475}, new int[]{424, 481}, new int[]{424, 486}, new int[]{424, 492}, new int[]{424, 497}, new int[]{423, 502}, new int[]{423, 507}, new int[]{423, 512}, new int[]{422, 517}, new int[]{422, 522}}, new int[][]{new int[]{445, 467}, new int[]{440, 470}, new int[]{433, 472}, new int[]{426, 474}, new int[]{418, 475}, new int[]{411, 477}, new int[]{d.c, 478}, new int[]{397, 480}, new int[]{389, 481}, new int[]{382, 482}, new int[]{378, 483}, new int[]{373, 483}}, new int[][]{new int[]{470, 491}, new int[]{466, 494}, new int[]{460, 496}, new int[]{454, 498}, new int[]{448, 500}, new int[]{442, 501}, new int[]{436, 503}, new int[]{429, 504}, new int[]{424, 506}, new int[]{418, 507}, new int[]{411, 508}, new int[]{d.c, 508}, new int[]{398, 509}, new int[]{392, 510}, new int[]{385, 510}, new int[]{378, 510}, new int[]{371, 510}}, new int[][]{new int[]{524, 545}, new int[]{527, 550}, new int[]{528, 554}, new int[]{529, 558}, new int[]{530, 562}, new int[]{531, 566}, new int[]{532, 569}, new int[]{532, 572}, new int[]{533, 576}, new int[]{533, 579}, new int[]{534, 582}, new int[]{533, 584}, new int[]{533, 587}, new int[]{532, 589}, new int[]{533, 592}, new int[]{532, 594}, new int[]{532, 596}, new int[]{530, 597}, new int[]{530, 599}, new int[]{528, 600}, new int[]{528, 602}, new int[]{526, 603}, new int[]{525, 604}, new int[]{523, 604}, new int[]{522, 605}, new int[]{519, 605}, new int[]{518, 606}, new int[]{516, 606}, new int[]{514, 606}, new int[]{511, 605}, new int[]{509, 605}}, new int[][]{new int[]{482, 503}, new int[]{484, 509}, new int[]{486, 515}, new int[]{486, 520}, new int[]{487, 525}, new int[]{488, 530}, new int[]{488, 535}, new int[]{489, 540}, new int[]{490, 545}, new int[]{490, 549}, new int[]{490, 553}, new int[]{491, 558}, new int[]{491, 562}, new int[]{491, 566}, new int[]{491, 570}}, new int[][]{new int[]{417, 438}, new int[]{417, 442}, new int[]{416, 445}, new int[]{414, 448}, new int[]{413, 451}, new int[]{412, 454}, new int[]{411, 457}, new int[]{410, 460}, new int[]{408, 462}, new int[]{407, 465}}};
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public boolean enableRotate() {
        return true;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getAodDateMarginTop() {
        return 32;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public float getColonInterval() {
        return 30.0f;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getColonResource() {
        int i = this.mUserDefinedColonResource;
        return i != 0 ? i : R.drawable.miui_rhombus_colon_circle;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public float getColonSize() {
        return 42.0f;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public BaseFontStyle.Style getFontStyle() {
        return BaseFontStyle.Style.SKPupok;
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
        return 30.0f;
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
    public float[] getPercentages() {
        return this.mPercentages;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public float[] getRightEmpty() {
        return this.mSVGEmptyRight;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getSmallClockInnerHeight() {
        return 82;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getSmallClockInnerMarginOffset() {
        return -2;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getSmallColonResource() {
        int i = this.mUserDefinedSmallColonResource;
        return i != 0 ? i : R.drawable.font_b_colon;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getSmallColonResourceHeight() {
        return 128;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int[] getSvgResource() {
        return this.mSVGResource;
    }

    @Override // com.miui.clock.module.BaseFontStyle
    public int getTextBoxWidth() {
        return 196;
    }
}
