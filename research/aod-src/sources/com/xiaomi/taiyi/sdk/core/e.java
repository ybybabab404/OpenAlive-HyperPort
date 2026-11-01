package com.xiaomi.taiyi.sdk.core;

import com.xiaomi.taiyi.sdk.core.track.ProcTrack;
import com.xiaomi.taiyi.sdk.transfer.core.IAICallback;
import com.xiaomi.taiyi.sdk.transfer.data.AIRequest;
import com.xiaomi.taiyi.sdk.transfer.data.AIResponse;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: loaded from: classes2.dex */
public final class e extends IAICallback.Stub {
    public final /* synthetic */ AIRequest a;
    public final /* synthetic */ c b;

    public e(AIRequest aIRequest, c cVar) {
        this.a = aIRequest;
        this.b = cVar;
    }

    @Override // com.xiaomi.taiyi.sdk.transfer.core.IAICallback
    public final void onResponse(AIResponse response) {
        Intrinsics.checkNotNullParameter(response, "response");
        ProcTrack.Companion.endLoad(this.a);
        this.b.onResponse(response);
    }
}
