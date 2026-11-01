package com.xiaomi.taiyi.sdk.core;

import com.xiaomi.taiyi.sdk.base.data.ErrorCode;
import com.xiaomi.taiyi.sdk.common.ConnectCallback;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;

/* JADX INFO: loaded from: classes2.dex */
public final class h implements ConnectCallback {
    public final /* synthetic */ j a;
    public final /* synthetic */ Ref.IntRef b;

    public h(j jVar, Ref.IntRef intRef) {
        this.a = jVar;
        this.b = intRef;
    }

    @Override // com.xiaomi.taiyi.sdk.common.ConnectCallback
    public final void onConnectFailed(int i, String msg) {
        Intrinsics.checkNotNullParameter(msg, "msg");
        this.b.element = i;
        this.a.g.countDown();
    }

    @Override // com.xiaomi.taiyi.sdk.common.ConnectCallback
    public final void onConnected() {
        this.a.g.countDown();
    }

    @Override // com.xiaomi.taiyi.sdk.common.ConnectCallback
    public final void onConnecting() {
        ConnectCallback.DefaultImpls.onConnecting(this);
    }

    @Override // com.xiaomi.taiyi.sdk.common.ConnectCallback
    public final void onDisconnected() {
        this.b.element = ErrorCode.ERROR_SERVICE_DISCONNECT;
        this.a.g.countDown();
    }
}
