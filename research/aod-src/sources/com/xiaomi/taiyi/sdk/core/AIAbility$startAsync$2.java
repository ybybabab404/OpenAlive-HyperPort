package com.xiaomi.taiyi.sdk.core;

import com.xiaomi.taiyi.sdk.base.utils.SdkLog;
import com.xiaomi.taiyi.sdk.core.ability.AbilityCallback;
import com.xiaomi.taiyi.sdk.core.ability.ServiceAbility;
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
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class AIAbility$startAsync$2 implements AbilityCallback {
    public final /* synthetic */ AIAbility a;
    public final /* synthetic */ AIAbility$postInner$1 b;

    @Override // com.xiaomi.taiyi.sdk.core.ability.AbilityCallback
    public void onFailure(int i) {
        SdkLog.e(this.a.getTAG(), "startAsync onFailure " + i);
        AIAbility$postInner$1 aIAbility$postInner$1 = this.b;
        if (aIAbility$postInner$1 != null) {
            aIAbility$postInner$1.onFailure(i);
        }
    }

    @Override // com.xiaomi.taiyi.sdk.core.ability.AbilityCallback
    public void onSuccess(ServiceAbility ability) {
        Intrinsics.checkNotNullParameter(ability, "ability");
        int iA = this.a.a(ability);
        SdkLog.i(this.a.getTAG(), "startAsync onSuccess bind = " + iA);
        if (iA == 0) {
            AIAbility$postInner$1 aIAbility$postInner$1 = this.b;
            if (aIAbility$postInner$1 != null) {
                aIAbility$postInner$1.onSuccess(ability);
                return;
            }
            return;
        }
        AIAbility$postInner$1 aIAbility$postInner$2 = this.b;
        if (aIAbility$postInner$2 != null) {
            aIAbility$postInner$2.onFailure(iA);
        }
    }
}
