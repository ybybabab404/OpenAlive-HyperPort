package com.miui.keyguard.editor.utils;

import com.miui.keyguard.editor.view.AutoBottomSheetKt;
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
/* JADX INFO: compiled from: Constants.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class TemplateHide {
    public static final TemplateHide INSTANCE = new TemplateHide();

    public final boolean shouldHide(int i) {
        if (i <= 0) {
            return false;
        }
        if (i == Integer.MAX_VALUE) {
            return true;
        }
        boolean zIsQ18 = (i & 1) != 0 ? !DeviceUtil.INSTANCE.isGreaterChinaByRegion() : false;
        if (!zIsQ18 && (i & 2) != 0) {
            zIsQ18 = !DeviceUtil.INSTANCE.isChineseLanguage();
        }
        if (!zIsQ18 && (i & 4) != 0) {
            DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
            zIsQ18 = (deviceUtil.isGteAndroidV() && deviceUtil.isGteOS22()) ? false : true;
        }
        if (!zIsQ18 && (i & 8) != 0) {
            zIsQ18 = DeviceUtil.INSTANCE.isPad();
        }
        if (!zIsQ18 && (i & 16) != 0) {
            zIsQ18 = !DeviceUtil.INSTANCE.isO8();
        }
        if (!zIsQ18 && (i & 32) != 0) {
            zIsQ18 = DeviceUtil.INSTANCE.isOS4PickerLiteDevice();
        }
        if (!zIsQ18 && (i & 64) != 0) {
            zIsQ18 = !DeviceUtil.INSTANCE.isPad();
        }
        if (!zIsQ18 && (i & 128) != 0) {
            zIsQ18 = DeviceUtil.INSTANCE.isQ18();
        }
        return (zIsQ18 || (i & AutoBottomSheetKt.ALIGN_SCREEN_CENTER_HORIZONTAL) == 0) ? zIsQ18 : !DeviceUtil.INSTANCE.isQ18();
    }
}
