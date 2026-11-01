package com.miui.clock.palette;

import com.miui.clock.module.ClockStyleInfo;
import com.miui.clock.pets.DepthPetsClockPaletteStrategy;
import java.util.Map;
import kotlin.Metadata;
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
/* JADX INFO: compiled from: ClockPaletteManager.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class ClockPaletteManager {
    public ColorConfig colorConfig;
    public final ClockPaletteStrategy strategy;

    /* JADX INFO: compiled from: ClockPaletteManager.kt */
    @Metadata
    public static final class ClockPaletteStrategyFactory {
        public static final ClockPaletteStrategyFactory INSTANCE = new ClockPaletteStrategyFactory();

        public final ClockPaletteStrategy getStrategy(String clockType) {
            Intrinsics.checkNotNullParameter(clockType, "clockType");
            if (Intrinsics.areEqual(clockType, "depth_pets")) {
                return new DepthPetsClockPaletteStrategy();
            }
            return null;
        }
    }

    public ClockPaletteManager(String clockType) {
        Intrinsics.checkNotNullParameter(clockType, "clockType");
        this.strategy = ClockPaletteStrategyFactory.INSTANCE.getStrategy(clockType);
    }

    public final ColorConfig getColorConfig() {
        return this.colorConfig;
    }

    public final void setClockPalette(SystemState systemState, ClockStyleInfo clockInfo, boolean z, Map map) {
        Intrinsics.checkNotNullParameter(systemState, "systemState");
        Intrinsics.checkNotNullParameter(clockInfo, "clockInfo");
        ClockPaletteStrategy clockPaletteStrategy = this.strategy;
        Intrinsics.checkNotNull(clockPaletteStrategy);
        ColorConfig.Companion companion = ColorConfig.Companion;
        ColorConfig colorConfigDetermineColors = clockPaletteStrategy.determineColors(systemState, companion.fromClockStyleInfo(clockInfo, z), map);
        this.colorConfig = colorConfigDetermineColors;
        companion.updateClockStyleInfo(clockInfo, colorConfigDetermineColors);
    }
}
