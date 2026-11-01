package miuix.miuixbasewidget.widget;

import miuix.theme.token.BlendModeToken;
import miuix.theme.token.BloomStrokeToken;
import miuix.theme.token.ColorBlendToken;
import miuix.theme.token.MaterialDayNightToken;
import miuix.theme.token.MaterialToken;
import miuix.theme.token.ShadowToken;

/* JADX INFO: loaded from: classes2.dex */
public final class FloatingActionButtonTokens {
    public static final ColorBlendToken Blue_FAB_Color_Blend_Dark;
    public static final ColorBlendToken Blue_FAB_Color_Blend_Light;
    public static final MaterialToken Blue_FAB_Dark;
    public static final MaterialToken Blue_FAB_Light;
    public static final MaterialDayNightToken Blue_FAB_Material;
    public static final ColorBlendToken Green_FAB_Color_Blend_Dark;
    public static final ColorBlendToken Green_FAB_Color_Blend_Light;
    public static final MaterialToken Green_FAB_Dark;
    public static final MaterialToken Green_FAB_Light;
    public static final MaterialDayNightToken Green_FAB_Material;
    public static final ColorBlendToken Red_FAB_Color_Blend_Dark;
    public static final ColorBlendToken Red_FAB_Color_Blend_Light;
    public static final MaterialToken Red_FAB_Dark;
    public static final MaterialToken Red_FAB_Light;
    public static final MaterialDayNightToken Red_FAB_Material;
    public static final ColorBlendToken Yellow_FAB_Color_Blend_Dark;
    public static final ColorBlendToken Yellow_FAB_Color_Blend_Light;
    public static final MaterialToken Yellow_FAB_Dark;
    public static final MaterialToken Yellow_FAB_Light;
    public static final MaterialDayNightToken Yellow_FAB_Material;

    static {
        BlendModeToken blendModeToken = BlendModeToken.PLUS_DARKER;
        int i = blendModeToken.value;
        BlendModeToken blendModeToken2 = BlendModeToken.SRC_OVER;
        ColorBlendToken colorBlendTokenBuild = new ColorBlendToken.Builder().setConfig(new int[]{-855638017, -419448049}, new int[]{i, blendModeToken2.value}).build();
        Yellow_FAB_Color_Blend_Light = colorBlendTokenBuild;
        ColorBlendToken colorBlendTokenBuild2 = new ColorBlendToken.Builder().setConfig(new int[]{-855638017, -419448049}, new int[]{blendModeToken.value, blendModeToken2.value}).build();
        Yellow_FAB_Color_Blend_Dark = colorBlendTokenBuild2;
        MaterialToken materialTokenBuild = new MaterialToken.Builder(10, "fab-yellow", "light").setElementBlur(20).setColorBlend(colorBlendTokenBuild).setBloomStroke(BloomStrokeToken.Glass_Stroke_Middle_Light).setShadow(ShadowToken.Regular).build();
        Yellow_FAB_Light = materialTokenBuild;
        MaterialToken materialTokenBuild2 = new MaterialToken.Builder(10, "fab-yellow", "dark").setElementBlur(20).setColorBlend(colorBlendTokenBuild2).setBloomStroke(BloomStrokeToken.Glass_Stroke_Middle_Dark).setShadow(ShadowToken.Regular).build();
        Yellow_FAB_Dark = materialTokenBuild2;
        Yellow_FAB_Material = new MaterialDayNightToken(materialTokenBuild, materialTokenBuild2);
        ColorBlendToken colorBlendTokenBuild3 = new ColorBlendToken.Builder().setConfig(new int[]{-855638017, -434254534}, new int[]{blendModeToken.value, blendModeToken2.value}).build();
        Green_FAB_Color_Blend_Light = colorBlendTokenBuild3;
        ColorBlendToken colorBlendTokenBuild4 = new ColorBlendToken.Builder().setConfig(new int[]{-855638017, -433532348}, new int[]{blendModeToken.value, blendModeToken2.value}).build();
        Green_FAB_Color_Blend_Dark = colorBlendTokenBuild4;
        MaterialToken materialTokenBuild3 = new MaterialToken.Builder(10, "fab-green", "light").setElementBlur(20).setColorBlend(colorBlendTokenBuild3).setBloomStroke(BloomStrokeToken.Glass_Stroke_Middle_Light).setShadow(ShadowToken.Regular).build();
        Green_FAB_Light = materialTokenBuild3;
        MaterialToken materialTokenBuild4 = new MaterialToken.Builder(10, "fab-green", "dark").setElementBlur(20).setColorBlend(colorBlendTokenBuild4).setBloomStroke(BloomStrokeToken.Glass_Stroke_Middle_Dark).setShadow(ShadowToken.Regular).build();
        Green_FAB_Dark = materialTokenBuild4;
        Green_FAB_Material = new MaterialDayNightToken(materialTokenBuild3, materialTokenBuild4);
        ColorBlendToken colorBlendTokenBuild5 = new ColorBlendToken.Builder().setConfig(new int[]{-855638017, -432766209}, new int[]{blendModeToken.value, blendModeToken2.value}).build();
        Blue_FAB_Color_Blend_Light = colorBlendTokenBuild5;
        ColorBlendToken colorBlendTokenBuild6 = new ColorBlendToken.Builder().setConfig(new int[]{-855638017, -431519489}, new int[]{blendModeToken.value, blendModeToken2.value}).build();
        Blue_FAB_Color_Blend_Dark = colorBlendTokenBuild6;
        MaterialToken materialTokenBuild5 = new MaterialToken.Builder(10, "fab-blue", "light").setElementBlur(20).setColorBlend(colorBlendTokenBuild5).setBloomStroke(BloomStrokeToken.Glass_Stroke_Middle_Light).setShadow(ShadowToken.Regular).build();
        Blue_FAB_Light = materialTokenBuild5;
        MaterialToken materialTokenBuild6 = new MaterialToken.Builder(10, "fab-blue", "dark").setElementBlur(20).setColorBlend(colorBlendTokenBuild6).setBloomStroke(BloomStrokeToken.Glass_Stroke_Middle_Dark).setShadow(ShadowToken.Regular).build();
        Blue_FAB_Dark = materialTokenBuild6;
        Blue_FAB_Material = new MaterialDayNightToken(materialTokenBuild5, materialTokenBuild6);
        ColorBlendToken colorBlendTokenBuild7 = new ColorBlendToken.Builder().setConfig(new int[]{-855638017, -419809234}, new int[]{blendModeToken.value, blendModeToken2.value}).build();
        Red_FAB_Color_Blend_Light = colorBlendTokenBuild7;
        ColorBlendToken colorBlendTokenBuild8 = new ColorBlendToken.Builder().setConfig(new int[]{-855638017, -419806664}, new int[]{blendModeToken.value, blendModeToken2.value}).build();
        Red_FAB_Color_Blend_Dark = colorBlendTokenBuild8;
        MaterialToken materialTokenBuild7 = new MaterialToken.Builder(10, "fab-red", "light").setElementBlur(20).setColorBlend(colorBlendTokenBuild7).setBloomStroke(BloomStrokeToken.Glass_Stroke_Middle_Light).setShadow(ShadowToken.Regular).build();
        Red_FAB_Light = materialTokenBuild7;
        MaterialToken materialTokenBuild8 = new MaterialToken.Builder(10, "fab-red", "dark").setElementBlur(20).setColorBlend(colorBlendTokenBuild8).setBloomStroke(BloomStrokeToken.Glass_Stroke_Middle_Dark).setShadow(ShadowToken.Regular).build();
        Red_FAB_Dark = materialTokenBuild8;
        Red_FAB_Material = new MaterialDayNightToken(materialTokenBuild7, materialTokenBuild8);
    }
}
