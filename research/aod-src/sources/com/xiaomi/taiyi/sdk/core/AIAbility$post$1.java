package com.xiaomi.taiyi.sdk.core;

import com.xiaomi.taiyi.sdk.common.AICallback;
import com.xiaomi.taiyi.sdk.transfer.core.IAICallback;
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
public final class AIAbility$post$1 extends IAICallback.Stub {
    public final /* synthetic */ AIAbility a;
    public final /* synthetic */ AIRequest b;
    public final /* synthetic */ AICallback c;

    @Override // com.xiaomi.taiyi.sdk.transfer.core.IAICallback
    public void onResponse(AIResponse response) {
        Intrinsics.checkNotNullParameter(response, "response");
        this.a.a(this.b, response);
        if (this.a.getSettings().getEnableTrack()) {
            AIAbility.access$getTrackManager(this.a).a(this.a, this.b, response);
        }
        this.c.onResponse(response);
    }
}
