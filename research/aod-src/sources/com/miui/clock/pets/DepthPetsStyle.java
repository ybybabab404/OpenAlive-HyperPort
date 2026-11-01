package com.miui.clock.pets;

import com.miui.clock.R;
import kotlin.Metadata;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: DepthPetsStyle.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class DepthPetsStyle {
    public static final DepthPetsStyle INSTANCE = new DepthPetsStyle();
    public static final int[] mTimeHourLeftResource = {R.drawable.miui_depth_pets_hour_left_0, R.drawable.miui_depth_pets_hour_left_1, R.drawable.miui_depth_pets_hour_left_2};
    public static final int[] mTimeHourRightResource = {R.drawable.miui_depth_pets_hour_right_0, R.drawable.miui_depth_pets_hour_right_1, R.drawable.miui_depth_pets_hour_right_2, R.drawable.miui_depth_pets_hour_right_3, R.drawable.miui_depth_pets_hour_right_4, R.drawable.miui_depth_pets_hour_right_5, R.drawable.miui_depth_pets_hour_right_6, R.drawable.miui_depth_pets_hour_right_7, R.drawable.miui_depth_pets_hour_right_8, R.drawable.miui_depth_pets_hour_right_9};
    public static final int[] mTimeMinuteLeftResource = {R.drawable.miui_depth_pets_minute_left_0, R.drawable.miui_depth_pets_minute_left_1, R.drawable.miui_depth_pets_minute_left_2, R.drawable.miui_depth_pets_minute_left_3, R.drawable.miui_depth_pets_minute_left_4, R.drawable.miui_depth_pets_minute_left_5};
    public static final int[] mTimeMinuteRightResource = {R.drawable.miui_depth_pets_minute_right_0, R.drawable.miui_depth_pets_minute_right_1, R.drawable.miui_depth_pets_minute_right_2, R.drawable.miui_depth_pets_minute_right_3, R.drawable.miui_depth_pets_minute_right_4, R.drawable.miui_depth_pets_minute_right_5, R.drawable.miui_depth_pets_minute_right_6, R.drawable.miui_depth_pets_minute_right_7, R.drawable.miui_depth_pets_minute_right_8, R.drawable.miui_depth_pets_minute_right_9};

    public final int[] getMTimeHourLeftResource() {
        return mTimeHourLeftResource;
    }

    public final int[] getMTimeHourRightResource() {
        return mTimeHourRightResource;
    }

    public final int[] getMTimeMinuteLeftResource() {
        return mTimeMinuteLeftResource;
    }

    public final int[] getMTimeMinuteRightResource() {
        return mTimeMinuteRightResource;
    }
}
