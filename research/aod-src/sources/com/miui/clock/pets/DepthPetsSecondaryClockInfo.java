package com.miui.clock.pets;

import com.miui.clock.R;
import com.miui.clock.module.ClockBean;
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
/* JADX INFO: compiled from: DepthPetsSecondaryClockInfo.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class DepthPetsSecondaryClockInfo extends DepthPetsClockBaseInfo {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DepthPetsSecondaryClockInfo(ClockBean clockBean, int i) {
        super(clockBean, i);
        Intrinsics.checkNotNullParameter(clockBean, "clockBean");
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public int getLayoutId() {
        return R.layout.layout_depth_pets_secondary_clock_view;
    }

    @Override // com.miui.clock.module.ClockStyleInfo
    public String getType() {
        return "depth_pets_secondary";
    }
}
