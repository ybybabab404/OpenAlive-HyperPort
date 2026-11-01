package com.miui.clock;

import com.miui.clock.allInOne.AllInOneClockStyle;
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
/* JADX INFO: compiled from: MiuiClockControllerExt.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class MiuiClockControllerExtKt {
    public static final void setAllInOneClockRectParamsOptional(MiuiClockView miuiClockView, AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, Boolean bool, Boolean bool2) {
        Intrinsics.checkNotNullParameter(miuiClockView, "<this>");
        if (bool != null) {
            miuiClockView.setAllInOneClockRectParams(region, f, f2, i, i2, i3, bool.booleanValue(), bool2);
        } else {
            miuiClockView.setAllInOneClockRectParams(region, f, f2, i, i2, i3);
        }
    }

    public static /* synthetic */ void setAllInOneClockRectParamsOptional$default(MiuiClockView miuiClockView, AllInOneClockStyle.Region region, float f, float f2, int i, int i2, int i3, Boolean bool, Boolean bool2, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            region = null;
        }
        if ((i4 & 2) != 0) {
            f = -1.0f;
        }
        if ((i4 & 4) != 0) {
            f2 = -1.0f;
        }
        if ((i4 & 8) != 0) {
            i = -1;
        }
        if ((i4 & 16) != 0) {
            i2 = -1;
        }
        if ((i4 & 32) != 0) {
            i3 = -1;
        }
        if ((i4 & 64) != 0) {
            bool = null;
        }
        if ((i4 & 128) != 0) {
            bool2 = null;
        }
        setAllInOneClockRectParamsOptional(miuiClockView, region, f, f2, i, i2, i3, bool, bool2);
    }
}
