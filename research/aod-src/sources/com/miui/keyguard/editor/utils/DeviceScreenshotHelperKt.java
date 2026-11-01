package com.miui.keyguard.editor.utils;

import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: DeviceScreenshotHelper.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class DeviceScreenshotHelperKt {
    public static final Lazy screenHierarchyEnableForShader$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.utils.DeviceScreenshotHelperKt$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return DeviceScreenshotHelperKt.screenHierarchyEnableForShader_delegate$lambda$0();
        }
    });

    public static final Map getScreenHierarchyEnableForShader() {
        return (Map) screenHierarchyEnableForShader$delegate.getValue();
    }

    public static final Map screenHierarchyEnableForShader_delegate$lambda$0() {
        Boolean bool = Boolean.FALSE;
        return MapsKt.mapOf(TuplesKt.to("hierarchy_check_large_screen_land", bool), TuplesKt.to("hierarchy_check_large_screen_land_dual_clock", bool), TuplesKt.to("hierarchy_check_large_screen_land_with_notification", bool), TuplesKt.to("hierarchy_check_large_screen_port", bool), TuplesKt.to("hierarchy_check_large_screen_port_dual_clock", bool), TuplesKt.to("hierarchy_check_large_screen_port_with_notification", bool), TuplesKt.to("hierarchy_check_small_screen", bool), TuplesKt.to("hierarchy_check_small_screen_dual_clock", bool), TuplesKt.to("hierarchy_check_small_screen_with_notification", Boolean.TRUE));
    }
}
