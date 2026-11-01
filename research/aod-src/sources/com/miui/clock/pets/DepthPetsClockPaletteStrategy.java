package com.miui.clock.pets;

import android.graphics.Color;
import com.miui.clock.palette.ClockColorMode;
import com.miui.clock.palette.ClockPaletteStrategy;
import com.miui.clock.palette.ColorConfig;
import com.miui.clock.palette.SystemState;
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
/* JADX INFO: compiled from: DepthPetsClockPaletteStrategy.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class DepthPetsClockPaletteStrategy implements ClockPaletteStrategy {
    public Map palette;

    /* JADX INFO: compiled from: DepthPetsClockPaletteStrategy.kt */
    @Metadata
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ClockColorMode.values().length];
            try {
                iArr[ClockColorMode.PURE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ClockColorMode.OVERLAY.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ClockColorMode.BLUR_BLEND.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    @Override // com.miui.clock.palette.ClockPaletteStrategy
    public ColorConfig determineColors(SystemState systemState, ColorConfig colorConfig, Map map) {
        return ClockPaletteStrategy.DefaultImpls.determineColors(this, systemState, colorConfig, map);
    }

    @Override // com.miui.clock.palette.ClockPaletteStrategy
    public int getAutoAodBlurColor(ClockColorMode mode, boolean z) {
        Integer num;
        Intrinsics.checkNotNullParameter(mode, "mode");
        int i = WhenMappings.$EnumSwitchMapping$0[mode.ordinal()];
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return getAutoBlurPrimaryColor(mode, z);
        }
        if (i != 3) {
            throw new NoWhenBranchMatchedException();
        }
        if (z) {
            return Color.parseColor("#FFBEBEBE");
        }
        Map palette = getPalette();
        if (palette == null || (num = (Integer) palette.get("secondary80")) == null) {
            return 0;
        }
        return num.intValue();
    }

    @Override // com.miui.clock.palette.ClockPaletteStrategy
    public int getAutoAodBlurSecondaryColor(ClockColorMode mode, boolean z) {
        Integer num;
        Intrinsics.checkNotNullParameter(mode, "mode");
        int i = WhenMappings.$EnumSwitchMapping$0[mode.ordinal()];
        if (i == 1) {
            return 0;
        }
        if (i == 2) {
            return getAutoBlurSecondaryColor(mode, z);
        }
        if (i != 3) {
            throw new NoWhenBranchMatchedException();
        }
        if (z) {
            return Color.parseColor("#FF808080");
        }
        Map palette = getPalette();
        if (palette == null || (num = (Integer) palette.get("secondary60")) == null) {
            return 0;
        }
        return num.intValue();
    }

    @Override // com.miui.clock.palette.ClockPaletteStrategy
    public int getAutoBlurPrimaryColor(ClockColorMode mode, boolean z) {
        Integer num;
        Intrinsics.checkNotNullParameter(mode, "mode");
        int i = WhenMappings.$EnumSwitchMapping$0[mode.ordinal()];
        if (i != 1 && i != 2) {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            if (z) {
                return Color.parseColor("#FFABABAB");
            }
            Map palette = getPalette();
            if (palette == null || (num = (Integer) palette.get("neutral-variant30")) == null) {
                return 0;
            }
            return num.intValue();
        }
        return getAutoPrimaryColor(mode, z);
    }

    @Override // com.miui.clock.palette.ClockPaletteStrategy
    public int getAutoBlurSecondaryColor(ClockColorMode mode, boolean z) {
        Intrinsics.checkNotNullParameter(mode, "mode");
        return getAutoBlurPrimaryColor(mode, z);
    }

    @Override // com.miui.clock.palette.ClockPaletteStrategy
    public int getAutoOriginMagazineColor(ClockColorMode mode, boolean z) {
        Integer num;
        Integer num2;
        Intrinsics.checkNotNullParameter(mode, "mode");
        int i = WhenMappings.$EnumSwitchMapping$0[mode.ordinal()];
        if (i != 1 && i != 2) {
            if (i != 3) {
                throw new NoWhenBranchMatchedException();
            }
            if (z) {
                Map palette = getPalette();
                if (palette == null || (num2 = (Integer) palette.get("secondary98")) == null) {
                    return 0;
                }
                return num2.intValue();
            }
            Map palette2 = getPalette();
            if (palette2 == null || (num = (Integer) palette2.get("secondary20")) == null) {
                return 0;
            }
            return num.intValue();
        }
        return getAutoPrimaryColor(mode, z);
    }

    @Override // com.miui.clock.palette.ClockPaletteStrategy
    public int getAutoPrimaryColor(ClockColorMode mode, boolean z) {
        Integer num;
        Integer num2;
        Integer num3;
        Integer num4;
        Intrinsics.checkNotNullParameter(mode, "mode");
        int i = WhenMappings.$EnumSwitchMapping$0[mode.ordinal()];
        if (i == 1) {
            if (z) {
                Map palette = getPalette();
                if (palette == null || (num2 = (Integer) palette.get("secondary85")) == null) {
                    return 0;
                }
                return num2.intValue();
            }
            Map palette2 = getPalette();
            if (palette2 == null || (num = (Integer) palette2.get("secondary40")) == null) {
                return 0;
            }
            return num.intValue();
        }
        if (i != 2) {
            if (i == 3) {
                return -1;
            }
            throw new NoWhenBranchMatchedException();
        }
        if (z) {
            Map palette3 = getPalette();
            if (palette3 == null || (num4 = (Integer) palette3.get("secondary95")) == null) {
                return 0;
            }
            return num4.intValue();
        }
        Map palette4 = getPalette();
        if (palette4 == null || (num3 = (Integer) palette4.get("secondary15")) == null) {
            return 0;
        }
        return num3.intValue();
    }

    @Override // com.miui.clock.palette.ClockPaletteStrategy
    public int getAutoSecondaryColor(ClockColorMode mode, boolean z) {
        Integer num;
        Integer num2;
        Integer num3;
        Integer num4;
        Intrinsics.checkNotNullParameter(mode, "mode");
        int i = WhenMappings.$EnumSwitchMapping$0[mode.ordinal()];
        if (i == 1) {
            if (z) {
                Map palette = getPalette();
                if (palette == null || (num2 = (Integer) palette.get("secondary98")) == null) {
                    return 0;
                }
                return num2.intValue();
            }
            Map palette2 = getPalette();
            if (palette2 == null || (num = (Integer) palette2.get("secondary20")) == null) {
                return 0;
            }
            return num.intValue();
        }
        if (i != 2) {
            if (i == 3) {
                return Color.parseColor("#FF808080");
            }
            throw new NoWhenBranchMatchedException();
        }
        if (z) {
            Map palette3 = getPalette();
            if (palette3 == null || (num4 = (Integer) palette3.get("secondary85")) == null) {
                return 0;
            }
            return num4.intValue();
        }
        Map palette4 = getPalette();
        if (palette4 == null || (num3 = (Integer) palette4.get("secondary30")) == null) {
            return 0;
        }
        return num3.intValue();
    }

    public Map getPalette() {
        return this.palette;
    }

    @Override // com.miui.clock.palette.ClockPaletteStrategy
    public ColorConfig handleBlurBlendMode(SystemState systemState, ColorConfig colorConfig) {
        return ClockPaletteStrategy.DefaultImpls.handleBlurBlendMode(this, systemState, colorConfig);
    }

    @Override // com.miui.clock.palette.ClockPaletteStrategy
    public ColorConfig handleOverlayMode(SystemState systemState, ColorConfig colorConfig) {
        return ClockPaletteStrategy.DefaultImpls.handleOverlayMode(this, systemState, colorConfig);
    }

    @Override // com.miui.clock.palette.ClockPaletteStrategy
    public ColorConfig handlePureMode(SystemState systemState, ColorConfig colorConfig) {
        return ClockPaletteStrategy.DefaultImpls.handlePureMode(this, systemState, colorConfig);
    }

    @Override // com.miui.clock.palette.ClockPaletteStrategy
    public void setPalette(Map map) {
        this.palette = map;
    }
}
