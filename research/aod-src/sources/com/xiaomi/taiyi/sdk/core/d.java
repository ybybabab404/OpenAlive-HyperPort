package com.xiaomi.taiyi.sdk.core;

import com.xiaomi.taiyi.sdk.transfer.core.IAICallback;
import com.xiaomi.taiyi.sdk.transfer.data.AIResponse;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: loaded from: classes2.dex */
public final class d extends IAICallback.Stub {
    public final /* synthetic */ c a;

    @Override // com.xiaomi.taiyi.sdk.transfer.core.IAICallback
    public final void onResponse(AIResponse response) {
        Intrinsics.checkNotNullParameter(response, "response");
        this.a.onResponse(response);
    }
}
