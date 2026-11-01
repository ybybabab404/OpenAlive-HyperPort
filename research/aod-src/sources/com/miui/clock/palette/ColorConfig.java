package com.miui.clock.palette;

import com.miui.clock.module.ClockStyleInfo;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
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
public final class ColorConfig {
    public static final Companion Companion = new Companion(null);
    public final ClockColorMode colorMode;
    public final boolean isAutoPrimary;
    public final boolean isAutoSecondary;
    public PrimaryColors primaryColors;
    public SecondaryColors secondaryColors;
    public final boolean textDark;
    public final boolean wallpaperDark;

    /* JADX INFO: compiled from: ClockPaletteStrategy.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final ColorConfig fromClockStyleInfo(ClockStyleInfo info, boolean z) {
            Intrinsics.checkNotNullParameter(info, "info");
            return new ColorConfig(new PrimaryColors(info.getPrimaryColor(), info.getBlendColor(), info.getAodPrimaryColor(), info.getFullAodPrimaryColor(), info.getAodBlendColor(), info.getFullAodBlendColor(), info.getOriginMagazineColor(), info.getAnimationPrimaryColor(), info.getPrimaryColor(), info.getInfoAreaColor(), info.getAodInfoColor(), info.getFullAodInfoColor()), new SecondaryColors(info.getSecondaryColor(), info.getSecondaryBlendColor(), 0, info.getAodSecondaryColor(), info.getFullAodSecondaryColor(), info.getAodSecondaryBlendColor(), info.getFullAodSecondaryBlendColor(), 4, null), info.isAutoPrimaryColor(), info.isAutoSecondaryColor(), ClockColorMode.Companion.parseEffect(info.getClockEffect()), z, false, 64, null);
        }

        public final void updateClockStyleInfo(ClockStyleInfo info, ColorConfig config) {
            Intrinsics.checkNotNullParameter(info, "info");
            Intrinsics.checkNotNullParameter(config, "config");
            PrimaryColors primaryColors = config.getPrimaryColors();
            info.setPrimaryColor(primaryColors.getPrimaryColor());
            info.setBlendColor(primaryColors.getBlendColor());
            info.setAodPrimaryColor(primaryColors.getAodPrimaryColor());
            info.setFullAodPrimaryColor(primaryColors.getFullAodPrimaryColor());
            info.setAodBlendColor(primaryColors.getAodBlendColor());
            info.setFullAodBlendColor(primaryColors.getFullAodBlendColor());
            info.setOriginMagazineColor(primaryColors.getOriginMagazineColor());
            info.setAnimationPrimaryColor(primaryColors.getAnimationPrimaryColor());
            info.setInfoAreaColor(primaryColors.getInfoAreaColor());
            info.setAodInfoColor(primaryColors.getAodInfoColor());
            info.setFullAodInfoColor(primaryColors.getFullAodInfoColor());
            SecondaryColors secondaryColors = config.getSecondaryColors();
            info.setSecondaryColor(secondaryColors.getSecondaryColor());
            info.setSecondaryBlendColor(secondaryColors.getSecondaryBlendColor());
            info.setAodSecondaryColor(secondaryColors.getAodSecondaryColor());
            info.setFullAodSecondaryColor(secondaryColors.getFullAodSecondaryColor());
            info.setAodSecondaryBlendColor(secondaryColors.getAodSecondaryBlendColor());
            info.setFullAodSecondaryBlendColor(secondaryColors.getFullAodSecondaryBlendColor());
        }
    }

    public ColorConfig(PrimaryColors primaryColors, SecondaryColors secondaryColors, boolean z, boolean z2, ClockColorMode colorMode, boolean z3, boolean z4) {
        Intrinsics.checkNotNullParameter(primaryColors, "primaryColors");
        Intrinsics.checkNotNullParameter(secondaryColors, "secondaryColors");
        Intrinsics.checkNotNullParameter(colorMode, "colorMode");
        this.primaryColors = primaryColors;
        this.secondaryColors = secondaryColors;
        this.isAutoPrimary = z;
        this.isAutoSecondary = z2;
        this.colorMode = colorMode;
        this.wallpaperDark = z3;
        this.textDark = z4;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ ColorConfig(PrimaryColors primaryColors, SecondaryColors secondaryColors, boolean z, boolean z2, ClockColorMode clockColorMode, boolean z3, boolean z4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        z = (i & 4) != 0 ? false : z;
        z2 = (i & 8) != 0 ? false : z2;
        z3 = (i & 32) != 0 ? false : z3;
        this(primaryColors, secondaryColors, z, z2, clockColorMode, z3, (i & 64) != 0 ? !z3 : z4);
    }

    public static /* synthetic */ ColorConfig copy$default(ColorConfig colorConfig, PrimaryColors primaryColors, SecondaryColors secondaryColors, boolean z, boolean z2, ClockColorMode clockColorMode, boolean z3, boolean z4, int i, Object obj) {
        if ((i & 1) != 0) {
            primaryColors = colorConfig.primaryColors;
        }
        if ((i & 2) != 0) {
            secondaryColors = colorConfig.secondaryColors;
        }
        if ((i & 4) != 0) {
            z = colorConfig.isAutoPrimary;
        }
        if ((i & 8) != 0) {
            z2 = colorConfig.isAutoSecondary;
        }
        if ((i & 16) != 0) {
            clockColorMode = colorConfig.colorMode;
        }
        if ((i & 32) != 0) {
            z3 = colorConfig.wallpaperDark;
        }
        if ((i & 64) != 0) {
            z4 = colorConfig.textDark;
        }
        boolean z5 = z3;
        boolean z6 = z4;
        ClockColorMode clockColorMode2 = clockColorMode;
        boolean z7 = z;
        return colorConfig.copy(primaryColors, secondaryColors, z7, z2, clockColorMode2, z5, z6);
    }

    public final ColorConfig copy(PrimaryColors primaryColors, SecondaryColors secondaryColors, boolean z, boolean z2, ClockColorMode colorMode, boolean z3, boolean z4) {
        Intrinsics.checkNotNullParameter(primaryColors, "primaryColors");
        Intrinsics.checkNotNullParameter(secondaryColors, "secondaryColors");
        Intrinsics.checkNotNullParameter(colorMode, "colorMode");
        return new ColorConfig(primaryColors, secondaryColors, z, z2, colorMode, z3, z4);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ColorConfig)) {
            return false;
        }
        ColorConfig colorConfig = (ColorConfig) obj;
        return Intrinsics.areEqual(this.primaryColors, colorConfig.primaryColors) && Intrinsics.areEqual(this.secondaryColors, colorConfig.secondaryColors) && this.isAutoPrimary == colorConfig.isAutoPrimary && this.isAutoSecondary == colorConfig.isAutoSecondary && this.colorMode == colorConfig.colorMode && this.wallpaperDark == colorConfig.wallpaperDark && this.textDark == colorConfig.textDark;
    }

    public final ClockColorMode getColorMode() {
        return this.colorMode;
    }

    public final PrimaryColors getPrimaryColors() {
        return this.primaryColors;
    }

    public final SecondaryColors getSecondaryColors() {
        return this.secondaryColors;
    }

    public final boolean getTextDark() {
        return this.textDark;
    }

    public final boolean getWallpaperDark() {
        return this.wallpaperDark;
    }

    public int hashCode() {
        return (((((((((((this.primaryColors.hashCode() * 31) + this.secondaryColors.hashCode()) * 31) + Boolean.hashCode(this.isAutoPrimary)) * 31) + Boolean.hashCode(this.isAutoSecondary)) * 31) + this.colorMode.hashCode()) * 31) + Boolean.hashCode(this.wallpaperDark)) * 31) + Boolean.hashCode(this.textDark);
    }

    public final boolean isAutoPrimary() {
        return this.isAutoPrimary;
    }

    public final boolean isAutoSecondary() {
        return this.isAutoSecondary;
    }

    public String toString() {
        return "ColorConfig(primaryColors=" + this.primaryColors + ", secondaryColors=" + this.secondaryColors + ", isAutoPrimary=" + this.isAutoPrimary + ", isAutoSecondary=" + this.isAutoSecondary + ", colorMode=" + this.colorMode + ", wallpaperDark=" + this.wallpaperDark + ", textDark=" + this.textDark + ')';
    }
}
