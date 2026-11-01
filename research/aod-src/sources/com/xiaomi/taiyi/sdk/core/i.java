package com.xiaomi.taiyi.sdk.core;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import com.xiaomi.taiyi.sdk.base.data.ErrorCode;
import com.xiaomi.taiyi.sdk.base.utils.SdkLog;
import com.xiaomi.taiyi.sdk.transfer.core.IAIManager;
import com.xiaomi.taiyi.sdk.transfer.data.AIRequest;

/* JADX INFO: loaded from: classes2.dex */
public final class i implements ServiceConnection {
    public final /* synthetic */ j a;

    public i(j jVar) {
        this.a = jVar;
    }

    @Override // android.content.ServiceConnection
    public final void onBindingDied(ComponentName componentName) {
        SdkLog.w(this.a.b, "onBindingDied " + componentName);
        this.a.a();
    }

    @Override // android.content.ServiceConnection
    public final void onServiceConnected(ComponentName componentName, IBinder iBinder) throws RemoteException {
        IAIManager iAIManagerAsInterface;
        int code;
        try {
            iAIManagerAsInterface = IAIManager.Stub.asInterface(iBinder);
        } catch (Throwable th) {
            SdkLog.e(this.a.b, "onServiceConnected cast error. " + th.getMessage());
            iAIManagerAsInterface = null;
        }
        SdkLog.i(this.a.b, "onServiceConnected " + iAIManagerAsInterface);
        if (iAIManagerAsInterface == null) {
            this.a.e = 3;
            this.a.a(3, ErrorCode.ERROR_SERVICE_INVALID, "service cast error");
            return;
        }
        j jVar = this.a;
        Context context = jVar.c;
        if (context == null) {
            jVar.e = 3;
            this.a.a(3, ErrorCode.ERROR_CONTEXT_INVALID, "app is null");
            return;
        }
        try {
            code = iAIManagerAsInterface.authorize(AIRequest.CREATOR.createServiceRequest$default(AIRequest.CREATOR, context, null, 2, null)).getCode();
        } catch (Throwable th2) {
            SdkLog.e(this.a.b, "authorize error. " + th2.getMessage());
            code = ErrorCode.ERROR_AUTHORIZE_ERROR;
        }
        if (code != 0) {
            this.a.e = 3;
            this.a.a(3, code, "authorize failed");
            return;
        }
        this.a.f = iAIManagerAsInterface;
        IBinder iBinderAsBinder = iAIManagerAsInterface.asBinder();
        if (iBinderAsBinder != null) {
            iBinderAsBinder.linkToDeath(this.a.j, 0);
        }
        this.a.e = 1;
        this.a.a(1, 0, "");
    }

    @Override // android.content.ServiceConnection
    public final void onServiceDisconnected(ComponentName componentName) {
        SdkLog.w(this.a.b, "onServiceDisconnected " + componentName);
        this.a.a();
    }
}
