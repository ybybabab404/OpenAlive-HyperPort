package com.miui.keyguard.editor.data.bean;

import android.util.Log;
import kotlin.Metadata;
import kotlin.ranges.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: MagicType.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class MagicType {
    private static final int COUNT_EFFECT_LIMIT = 10000;

    @NotNull
    public static final MagicType INSTANCE = new MagicType();

    @NotNull
    public static final String MAGIC_TYPE = "magic_type";
    public static final int TYPE_BLURRED_1 = 30000;
    public static final int TYPE_BLURRED_2 = 30001;
    public static final int TYPE_BLURRED_3 = 30002;
    private static final int TYPE_BLUR_END = 39999;
    private static final int TYPE_BLUR_START = 30000;
    public static final int TYPE_CRYSTALLIZED = 80000;
    public static final int TYPE_DEPTH = 20000;
    public static final int TYPE_DEPTH_VIDEO = 100000;
    public static final int TYPE_FOCUS = 50000;
    public static final int TYPE_FRONT_BACK_1 = 50001;
    public static final int TYPE_FRONT_BACK_2 = 50002;
    public static final int TYPE_FRONT_BACK_3 = 50003;
    public static final int TYPE_FRONT_BACK_4 = 50004;
    public static final int TYPE_GRID = 40000;
    private static final int TYPE_GRID_END = 109999;
    private static final int TYPE_GRID_START = 40000;
    public static final int TYPE_ORIGIN = 0;
    public static final int TYPE_STRIPED_LENS = 60000;
    public static final int TYPE_VERTICAL_1 = 10000;
    public static final int TYPE_VERTICAL_2 = 10001;
    public static final int TYPE_VERTICAL_3 = 10002;
    public static final int TYPE_VERTICAL_4 = 10003;
    public static final int TYPE_VERTICAL_5 = 10004;
    private static final int TYPE_VERTICAL_END = 19999;
    private static final int TYPE_VERTICAL_START = 10000;
    public static final int TYPE_WATER_1 = 70000;
    public static final int TYPE_WATER_2 = 70001;

    private MagicType() {
    }

    public final boolean isDepth(@Nullable Integer num) {
        return num != null && num.intValue() == 20000;
    }

    public final boolean isDepthVideo(@Nullable Integer num) {
        return num != null && num.intValue() == 100000;
    }

    public final boolean isFrontBack(@Nullable Integer num) {
        if (num == null || num.intValue() < 50001 || num.intValue() > 50004) {
            return num != null && num.intValue() == 50000;
        }
        return true;
    }

    public final boolean isGlassFilter(boolean z, @Nullable Integer num) {
        return (!z || isDepth(num) || isDepthVideo(num)) ? false : true;
    }

    public final boolean isGlassVertical(int i) {
        return 10000 <= i && i < 20000;
    }

    public final boolean isGrid(int i) {
        return i == 40000;
    }

    public final boolean isMagicTypeSupportGlassClockEffect(@Nullable Integer num) {
        if (num != null && num.intValue() == 0) {
            return true;
        }
        return num != null && num.intValue() == 20000;
    }

    public final boolean isOrigin(int i) {
        return i == 0;
    }

    public final boolean isShader(int i) {
        return (i == 0 || isDepth(Integer.valueOf(i)) || isDepthVideo(Integer.valueOf(i))) ? false : true;
    }

    public final int makeTypeValid(@Nullable Integer num) {
        if (num != null && new IntRange(0, TYPE_GRID_END).contains(num.intValue())) {
            return num.intValue();
        }
        Log.w("MagicType", "makeTypeValid: invalid type " + num);
        return 0;
    }
}
