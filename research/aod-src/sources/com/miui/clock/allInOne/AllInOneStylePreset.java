package com.miui.clock.allInOne;

import kotlin.Metadata;

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
/* JADX INFO: compiled from: AllInOneStylePreset.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class AllInOneStylePreset {
    public static final AllInOneStylePreset INSTANCE = new AllInOneStylePreset();
    public static final Preset PRESET_1_UNKNOWN0 = new Preset(0, 1, 0.078313f, 0.583333f, 1002.0f, 674.0f);
    public static final Preset PRESET_2_STYLE1_OVERSIZE_A = new Preset(0, 1, 0.077811f, 0.008032f, 814.0f, 1821.0f);
    public static final Preset PRESET_3_STYLE1_OVERSIZE_B = new Preset(0, 1, 0.078313f, 0.403112f, 1047.0f, 1033.0f);
    public static final Preset PRESET_4_CLASSIC_MAX = new Preset(0, 1, 0.329317f, 0.441265f, 865.0f, 457.0f);
    public static final Preset PRESET_5_CLASSIC_PLUS_SINGLE = new Preset(0, 0, 0.079819f, 0.736948f, 752.0f, 365.0f);
    public static final Preset PRESET_6_CLASSIC_PLUS_DOUBLE = new Preset(0, 1, 0.077811f, 0.636546f, 504.0f, 569.0f);

    /* JADX INFO: compiled from: AllInOneStylePreset.kt */
    @Metadata
    public static final class Preset {
        public final int baseDisplayOrientation;
        public float clockBottomRatio;
        public final int clockDepthType;
        public float clockTopRatio;
        public float timeHeight;
        public float timeWidth;

        public Preset(int i, int i2, float f, float f2, float f3, float f4) {
            this.baseDisplayOrientation = i;
            this.clockDepthType = i2;
            this.clockTopRatio = f;
            this.clockBottomRatio = f2;
            this.timeWidth = f3;
            this.timeHeight = f4;
        }
    }
}
