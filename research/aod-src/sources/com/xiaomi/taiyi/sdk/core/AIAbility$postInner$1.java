package com.xiaomi.taiyi.sdk.core;

import com.xiaomi.taiyi.sdk.base.utils.ExtsKt;
import com.xiaomi.taiyi.sdk.core.ability.AbilityCallback;
import com.xiaomi.taiyi.sdk.core.ability.ServiceAbility;
import com.xiaomi.taiyi.sdk.core.track.ProcTrack;
import com.xiaomi.taiyi.sdk.transfer.data.AIRequest;
import com.xiaomi.taiyi.sdk.transfer.data.AIResponse;
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
public final class AIAbility$postInner$1 implements AbilityCallback {
    public final /* synthetic */ AIRequest a;
    public final /* synthetic */ AIAbility b;
    public final /* synthetic */ AIAbility$post$1 c;

    @Override // com.xiaomi.taiyi.sdk.core.ability.AbilityCallback
    public void onFailure(int i) {
        ProcTrack.Companion.enableLoad(this.a, i);
        AIResponse aIResponseCreate = AIResponse.CREATOR.create(this.a);
        aIResponseCreate.setCode(i);
        this.c.onResponse(aIResponseCreate);
    }

    @Override // com.xiaomi.taiyi.sdk.core.ability.AbilityCallback
    public void onSuccess(ServiceAbility ability) {
        Intrinsics.checkNotNullParameter(ability, "ability");
        ProcTrack.Companion.enableLoad(this.a, 0);
        this.a.getHeaders().setTid(ExtsKt.getMY_TID());
        AIAbility.access$post(this.b, ability.getProxy$core_release(), this.a, this.c);
    }
}
