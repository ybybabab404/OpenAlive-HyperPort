package com.miui.clock.palette;

import com.miui.maml.R;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: ClockPaletteStrategy.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public interface ClockPaletteStrategy {

    /* JADX INFO: compiled from: ClockPaletteStrategy.kt */
    @Metadata
    public static final class DefaultImpls {
        public static ColorConfig determineColors(ClockPaletteStrategy clockPaletteStrategy, SystemState systemState, ColorConfig currentConfig, Map map) {
            Intrinsics.checkNotNullParameter(systemState, "systemState");
            Intrinsics.checkNotNullParameter(currentConfig, "currentConfig");
            clockPaletteStrategy.setPalette(map);
            int i = WhenMappings.$EnumSwitchMapping$0[currentConfig.getColorMode().ordinal()];
            if (i == 1) {
                return clockPaletteStrategy.handleOverlayMode(systemState, currentConfig);
            }
            if (i == 2) {
                return clockPaletteStrategy.handlePureMode(systemState, currentConfig);
            }
            if (i == 3) {
                return clockPaletteStrategy.handleBlurBlendMode(systemState, currentConfig);
            }
            throw new NoWhenBranchMatchedException();
        }

        public static ColorConfig handleBlurBlendMode(ClockPaletteStrategy clockPaletteStrategy, SystemState systemState, ColorConfig config) {
            SecondaryColors secondaryColors;
            Intrinsics.checkNotNullParameter(systemState, "systemState");
            Intrinsics.checkNotNullParameter(config, "config");
            int autoPrimaryColor = clockPaletteStrategy.getAutoPrimaryColor(config.getColorMode(), config.getWallpaperDark());
            int autoSecondaryColor = clockPaletteStrategy.getAutoSecondaryColor(config.getColorMode(), config.getWallpaperDark());
            if (!systemState.getAdvancedVisualEnabled()) {
                autoPrimaryColor = config.isAutoPrimary() ? clockPaletteStrategy.getAutoPrimaryColor(ClockColorMode.PURE, config.getWallpaperDark()) : config.getPrimaryColors().getBlendColor();
                autoSecondaryColor = config.isAutoSecondary() ? clockPaletteStrategy.getAutoSecondaryColor(ClockColorMode.PURE, config.getWallpaperDark()) : config.getSecondaryColors().getSecondaryBlendColor();
            }
            int i = autoPrimaryColor;
            PrimaryColors primaryColors = config.isAutoPrimary() ? new PrimaryColors(i, clockPaletteStrategy.getAutoBlurPrimaryColor(config.getColorMode(), config.getWallpaperDark()), 0, 0, clockPaletteStrategy.getAutoAodBlurColor(config.getColorMode(), config.getWallpaperDark()), clockPaletteStrategy.getAutoBlurPrimaryColor(config.getColorMode(), true), clockPaletteStrategy.getAutoOriginMagazineColor(config.getColorMode(), config.getWallpaperDark()), 0, 0, 0, 0, 0, 3980, null) : new PrimaryColors(i, config.getPrimaryColors().getBlendColor(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4092, null);
            int autoBlurSecondaryColor = clockPaletteStrategy.getAutoBlurSecondaryColor(config.getColorMode(), config.getWallpaperDark());
            if (config.isAutoSecondary()) {
                secondaryColors = new SecondaryColors(autoSecondaryColor, autoBlurSecondaryColor, 0, 0, 0, clockPaletteStrategy.getAutoAodBlurSecondaryColor(config.getColorMode(), config.getWallpaperDark()), clockPaletteStrategy.getAutoBlurSecondaryColor(config.getColorMode(), true), 28, null);
            } else {
                secondaryColors = new SecondaryColors(autoSecondaryColor, config.getSecondaryColors().getSecondaryBlendColor(), 0, 0, 0, 0, 0, R.styleable.AppCompatTheme_windowMinWidthMajor, null);
            }
            return ColorConfig.copy$default(config, primaryColors, secondaryColors, false, false, null, false, false, R.styleable.AppCompatTheme_windowMinWidthMajor, null);
        }

        public static ColorConfig handleOverlayMode(ClockPaletteStrategy clockPaletteStrategy, SystemState systemState, ColorConfig config) {
            Intrinsics.checkNotNullParameter(systemState, "systemState");
            Intrinsics.checkNotNullParameter(config, "config");
            return clockPaletteStrategy.handlePureMode(systemState, config);
        }

        public static ColorConfig handlePureMode(ClockPaletteStrategy clockPaletteStrategy, SystemState systemState, ColorConfig config) {
            Intrinsics.checkNotNullParameter(systemState, "systemState");
            Intrinsics.checkNotNullParameter(config, "config");
            return ColorConfig.copy$default(config, config.isAutoPrimary() ? new PrimaryColors(clockPaletteStrategy.getAutoPrimaryColor(config.getColorMode(), config.getWallpaperDark()), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4094, null) : new PrimaryColors(config.getPrimaryColors().getPrimaryColor(), 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 4094, null), config.isAutoSecondary() ? new SecondaryColors(clockPaletteStrategy.getAutoSecondaryColor(config.getColorMode(), config.getWallpaperDark()), 0, 0, 0, 0, 0, 0, R.styleable.AppCompatTheme_windowNoTitle, null) : new SecondaryColors(config.getSecondaryColors().getSecondaryColor(), 0, 0, 0, 0, 0, 0, R.styleable.AppCompatTheme_windowNoTitle, null), false, false, null, false, false, R.styleable.AppCompatTheme_windowMinWidthMajor, null);
        }
    }

    /* JADX INFO: compiled from: ClockPaletteStrategy.kt */
    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClockColorMode.values().length];
            try {
                iArr[ClockColorMode.OVERLAY.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClockColorMode.PURE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ClockColorMode.BLUR_BLEND.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    ColorConfig determineColors(SystemState systemState, ColorConfig colorConfig, Map map);

    int getAutoAodBlurColor(ClockColorMode clockColorMode, boolean z);

    int getAutoAodBlurSecondaryColor(ClockColorMode clockColorMode, boolean z);

    int getAutoBlurPrimaryColor(ClockColorMode clockColorMode, boolean z);

    int getAutoBlurSecondaryColor(ClockColorMode clockColorMode, boolean z);

    int getAutoOriginMagazineColor(ClockColorMode clockColorMode, boolean z);

    int getAutoPrimaryColor(ClockColorMode clockColorMode, boolean z);

    int getAutoSecondaryColor(ClockColorMode clockColorMode, boolean z);

    ColorConfig handleBlurBlendMode(SystemState systemState, ColorConfig colorConfig);

    ColorConfig handleOverlayMode(SystemState systemState, ColorConfig colorConfig);

    ColorConfig handlePureMode(SystemState systemState, ColorConfig colorConfig);

    void setPalette(Map map);
}
