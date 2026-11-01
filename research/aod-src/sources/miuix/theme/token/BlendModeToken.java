package miuix.theme.token;

import com.miui.maml.R;
import miuix.animation.utils.EaseManager;

/* JADX INFO: loaded from: classes3.dex */
public enum BlendModeToken {
    CLEAR(0),
    SRC(1),
    DST(2),
    SRC_OVER(3),
    DST_OVER(4),
    SRC_IN(5),
    DST_IN(6),
    SRC_OUT(7),
    DST_OUT(8),
    SRC_ATOP(9),
    DST_ATOP(10),
    XOR(11),
    PLUS(12),
    MODULATE(13),
    SCREEN(14),
    OVERLAY(15),
    DARKEN(16),
    LIGHTEN(17),
    COLOR_DODGE(18),
    COLOR_BURN(19),
    HARD_LIGHT(20),
    SOFT_LIGHT(21),
    DIFFERENCE(22),
    EXCLUSION(23),
    MULTIPLY(24),
    HUE(25),
    SATURATION(26),
    COLOR(27),
    LUMINOSITY(28),
    LAST_COEFF(29),
    LAST_SEPARABLE(30),
    LAST(31),
    LINEAR_LIGHT(100),
    LINEAR_LIGHT_WITH_GREYSCALE(101),
    MI_DIFFERENCE(102),
    LAB_LIGHTEN_WITH_GREYSCALE(103),
    LAB_DARKEN_WITH_GREYSCALE(R.styleable.AppCompatTheme_textAppearanceListItemSmall),
    LAB(R.styleable.AppCompatTheme_textAppearancePopupMenuHeader),
    LINEAR_LIGHT_LAB(R.styleable.AppCompatTheme_textAppearanceSearchResultSubtitle),
    COLOR_DODGE_V2(R.styleable.AppCompatTheme_windowActionBarOverlay),
    COLOR_BURN_V2(R.styleable.AppCompatTheme_windowActionModeOverlay),
    PLUS_DARKER(R.styleable.AppCompatTheme_windowFixedHeightMajor),
    PLUS_LIGHTER(R.styleable.AppCompatTheme_windowFixedHeightMinor),
    SATURATION_V2(EaseManager.EaseStyleDef.PERLIN),
    BRIGHTNESS(202),
    LUMINANCE(203);

    public final int value;

    BlendModeToken(int i) {
        this.value = i;
    }
}
