package com.xiaomi.taiyi.sdk.core;

import com.xiaomi.taiyi.sdk.base.data.ErrorCode;
import com.xiaomi.taiyi.sdk.common.ConnectCallback;
import com.xiaomi.taiyi.sdk.core.track.ProcTrack;
import com.xiaomi.taiyi.sdk.transfer.core.IAIManager;
import com.xiaomi.taiyi.sdk.transfer.data.AIRequest;
import com.xiaomi.taiyi.sdk.transfer.data.AIResponse;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: loaded from: classes2.dex */
public final class f implements ConnectCallback {
    public final /* synthetic */ AIRequest a;
    public final /* synthetic */ g b;
    public final /* synthetic */ c c;

    @Override // com.xiaomi.taiyi.sdk.common.ConnectCallback
    public final void onConnectFailed(int i, String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        ProcTrack.Companion.endBind(this.a);
        this.b.d.remove(this.a.getRequestId());
        this.c.onResponse(AIResponse.CREATOR.create(this.a, i));
    }

    @Override // com.xiaomi.taiyi.sdk.common.ConnectCallback
    public final void onConnected() {
        ProcTrack.Companion companion = ProcTrack.Companion;
        companion.endBind(this.a);
        this.b.d.remove(this.a.getRequestId());
        IAIManager iAIManager = this.b.c.f;
        Intrinsics.checkNotNull(iAIManager);
        companion.startLoad(this.a);
        iAIManager.loadAsync(this.a, new e(this.a, this.c));
    }

    @Override // com.xiaomi.taiyi.sdk.common.ConnectCallback
    public final void onConnecting() {
        ConnectCallback.DefaultImpls.onConnecting(this);
    }

    @Override // com.xiaomi.taiyi.sdk.common.ConnectCallback
    public final void onDisconnected() {
        ProcTrack.Companion.endBind(this.a);
        this.b.d.remove(this.a.getRequestId());
        this.c.onResponse(AIResponse.CREATOR.create(this.a, ErrorCode.ERROR_SERVICE_DISCONNECT));
    }
}
