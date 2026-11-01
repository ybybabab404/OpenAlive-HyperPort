package com.miui.aod.fullaod;

import android.content.Context;
import com.miui.aod.R;
import com.miui.aod.utils.MiuiSettingsUtils;
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
    */
/* JADX INFO: compiled from: FullAodStrategy.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class FullAodStrategyKt {
    public static final String getToastReason(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        return getToastReason(context, MiuiSettingsUtils.Secure.getInt(context.getContentResolver(), "full_screen_aod_disable_reason", 0));
    }

    public static final String getToastReason(Context context, int i) {
        if ((i & 8) != 0) {
            String string = context.getString(R.string.full_aod_not_support_reason_third_theme);
            Intrinsics.checkNotNull(string);
            return string;
        }
        if ((i & 2) != 0) {
            String string2 = context.getString(R.string.full_aod_not_support_reason_super_wallpaper);
            Intrinsics.checkNotNull(string2);
            return string2;
        }
        if ((i & 4) != 0) {
            String string3 = context.getString(R.string.full_aod_not_support_reason_third_wallpaper);
            Intrinsics.checkNotNull(string3);
            return string3;
        }
        if ((i & 1) != 0) {
            String string4 = context.getString(R.string.full_aod_not_support_reason_template);
            Intrinsics.checkNotNull(string4);
            return string4;
        }
        if ((i & 32) == 0) {
            return "";
        }
        String string5 = context.getString(R.string.full_aod_not_support_reason_depth_video);
        Intrinsics.checkNotNull(string5);
        return string5;
    }
}
