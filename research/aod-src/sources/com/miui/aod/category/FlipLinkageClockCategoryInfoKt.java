package com.miui.aod.category;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: FlipLinkageClockCategoryInfo.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class FlipLinkageClockCategoryInfoKt {

    @NotNull
    private static final String DEFAULT_CLOCK_INFO = "{\"aodPrimaryColor\":0,\"blendColor\":-5526613,\"blendColor180\":-5526613,\"needInverseColor\":false,\"primaryColor\":-1,\"primaryColor180\":-1,\"style\":0,\"templateId\":\"classic\",\"wallpaperDark\":true,\"wallpaperDark180\":true}";

    @NotNull
    private static String clockInfoString = "{\"aodPrimaryColor\":0,\"blendColor\":-5526613,\"blendColor180\":-5526613,\"needInverseColor\":false,\"primaryColor\":-1,\"primaryColor180\":-1,\"style\":0,\"templateId\":\"classic\",\"wallpaperDark\":true,\"wallpaperDark180\":true}";

    @NotNull
    public static final String getClockInfoString() {
        return clockInfoString;
    }

    @NotNull
    public static final String getDEFAULT_CLOCK_INFO() {
        return DEFAULT_CLOCK_INFO;
    }

    public static final void setClockInfoString(@NotNull String str) {
        Intrinsics.checkNotNullParameter(str, "<set-?>");
        clockInfoString = str;
    }
}
