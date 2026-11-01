package com.xiaomi.taiyi.sdk.core;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import com.xiaomi.taiyi.sdk.base.data.ErrorCode;
import com.xiaomi.taiyi.sdk.base.data.ServiceInfo;
import com.xiaomi.taiyi.sdk.base.utils.ExtsKt;
import com.xiaomi.taiyi.sdk.base.utils.SdkLog;
import com.xiaomi.taiyi.sdk.common.AIKeys;
import com.xiaomi.taiyi.sdk.common.AIVersion;
import com.xiaomi.taiyi.sdk.common.ConnectCallback;
import com.xiaomi.taiyi.sdk.transfer.core.IAIManager;
import com.xiaomi.taiyi.sdk.transfer.data.AIRequest;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;

/* JADX INFO: loaded from: classes2.dex */
public final class j {
    public final ServiceInfo a;
    public final String b;
    public Context c;
    public final Handler d;
    public volatile int e;
    public volatile IAIManager f;
    public CountDownLatch g;
    public final CopyOnWriteArraySet h;
    public final i i;
    public final IBinder.DeathRecipient j;

    public j(ServiceInfo info) {
        Intrinsics.checkNotNullParameter(info, "info");
        this.a = info;
        this.b = "ServiceConnection(" + info.getId() + ")";
        this.d = new Handler(Looper.getMainLooper());
        this.g = new CountDownLatch(1);
        this.h = new CopyOnWriteArraySet();
        this.i = new i(this);
        this.j = new IBinder.DeathRecipient() { // from class: com.xiaomi.taiyi.sdk.core.j$$ExternalSyntheticLambda1
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                j.a(this.f$0);
            }
        };
    }

    public static final void a(j jVar) {
        SdkLog.w(jVar.b, "binderDied");
        jVar.a();
    }

    public static final void a(j jVar, int i, int i2, String str) {
        for (ConnectCallback connectCallback : jVar.h) {
            if (i == 0) {
                connectCallback.onDisconnected();
            } else if (i == 1) {
                connectCallback.onConnected();
            } else if (i == 2) {
                connectCallback.onConnecting();
            } else if (i == 3) {
                connectCallback.onConnectFailed(i2, str);
            }
        }
    }

    public static final void b(int i, ConnectCallback connectCallback, int i2, String str) {
        if (i == 0) {
            connectCallback.onDisconnected();
            return;
        }
        if (i == 1) {
            connectCallback.onConnected();
        } else if (i == 2) {
            connectCallback.onConnecting();
        } else {
            if (i != 3) {
                return;
            }
            connectCallback.onConnectFailed(i2, str);
        }
    }

    public final synchronized Pair a(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        IAIManager iAIManager = this.f;
        if (iAIManager != null && b()) {
            SdkLog.i(this.b, "connect connected.");
            return new Pair(iAIManager, 0);
        }
        if (ExtsKt.isMainThread()) {
            SdkLog.e(this.b, "connect on main thread!");
            return new Pair(this.f, Integer.valueOf(ErrorCode.ERROR_WORK_ON_MAIN_THREAD));
        }
        SdkLog.i(this.b, "connect start");
        this.g = new CountDownLatch(1);
        Ref.IntRef intRef = new Ref.IntRef();
        h callback = new h(this, intRef);
        a(context, callback);
        try {
            this.g.await(2000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            SdkLog.e(this.b, "connect await error. " + e.getMessage());
            if (this.f == null) {
                intRef.element = ErrorCode.ERROR_AWAIT_EXCEPTION;
            }
        }
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.h.remove(callback);
        if (this.f == null && intRef.element == 0) {
            intRef.element = ErrorCode.ERROR_AWAIT_TIMEOUT;
            SdkLog.e(this.b, "connect await timeout 2000");
        } else if (this.f != null) {
            intRef.element = 0;
        }
        SdkLog.i(this.b, "connect end. service = " + this.f + " code =  " + intRef.element);
        return new Pair(this.f, Integer.valueOf(intRef.element));
    }

    public final void a() {
        Object objM1061constructorimpl;
        Unit unit;
        IBinder iBinderAsBinder;
        if (this.e == 0 || this.e == 3) {
            SdkLog.d(this.b, "destroy has disconnected");
        } else {
            SdkLog.i(this.b, "destroy");
            this.e = 0;
            a(0, 0, "");
            try {
                Result.Companion companion = Result.Companion;
                IAIManager iAIManager = this.f;
                if (iAIManager != null && (iBinderAsBinder = iAIManager.asBinder()) != null) {
                    iBinderAsBinder.unlinkToDeath(this.j, 0);
                }
                Context context = this.c;
                if (context != null) {
                    context.unbindService(this.i);
                    unit = Unit.INSTANCE;
                } else {
                    unit = null;
                }
                objM1061constructorimpl = Result.m1061constructorimpl(unit);
            } catch (Throwable th) {
                Result.Companion companion2 = Result.Companion;
                objM1061constructorimpl = Result.m1061constructorimpl(ResultKt.createFailure(th));
            }
            Throwable thM1063exceptionOrNullimpl = Result.m1063exceptionOrNullimpl(objM1061constructorimpl);
            if (thM1063exceptionOrNullimpl != null) {
                SdkLog.w(this.b, "unlinkToDeath or unbindService error. " + thM1063exceptionOrNullimpl.getMessage());
            }
            this.f = null;
        }
        this.h.clear();
    }

    public final void a(final int i, final int i2, final String str) {
        this.d.post(new Runnable() { // from class: com.xiaomi.taiyi.sdk.core.j$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                j.a(this.f$0, i, i2, str);
            }
        });
    }

    public final void a(final int i, final ConnectCallback connectCallback, final int i2, final String str) {
        this.d.post(new Runnable() { // from class: com.xiaomi.taiyi.sdk.core.j$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                j.b(i, connectCallback, i2, str);
            }
        });
    }

    public final void a(Context context, ConnectCallback callback) {
        Intrinsics.checkNotNullParameter(context, "context");
        Context applicationContext = context instanceof Application ? (Application) context : null;
        if (applicationContext == null) {
            applicationContext = context.getApplicationContext();
        }
        if (this.c == null) {
            this.c = applicationContext;
        }
        if (callback != null) {
            Intrinsics.checkNotNullParameter(callback, "callback");
            this.h.add(callback);
        }
        if (this.e == 2) {
            SdkLog.i(this.b, "init service connecting...");
            if (callback != null) {
                a(2, callback, 0, "");
                return;
            }
            return;
        }
        if (b()) {
            SdkLog.i(this.b, "init service connected");
            if (callback != null) {
                a(1, callback, 0, "");
                return;
            }
            return;
        }
        Intrinsics.checkNotNull(applicationContext);
        if (!AIVersion.isServiceSupport(applicationContext)) {
            this.e = 3;
            SdkLog.e(this.b, "init service not support!");
            if (callback != null) {
                a(3, callback, ErrorCode.ERROR_SERVICE_NOT_SUPPORT, "init service not support!");
                return;
            }
            return;
        }
        SdkLog.i(this.b, "init service bind start");
        Intent intent = new Intent(this.a.getAction());
        intent.setComponent(new ComponentName(this.a.getPackageName(), this.a.getName()));
        AIRequest aIRequestCreateServiceRequest$default = AIRequest.CREATOR.createServiceRequest$default(AIRequest.CREATOR, applicationContext, null, 2, null);
        aIRequestCreateServiceRequest$default.getHeaders().setTime();
        intent.putExtra(AIKeys.KEY_REQUEST, aIRequestCreateServiceRequest$default);
        intent.setIdentifier(aIRequestCreateServiceRequest$default.getHeaders().getFormatTime());
        try {
            applicationContext.bindService(intent, this.i, Build.VERSION.SDK_INT >= 34 ? 513 : 1);
            this.e = 2;
            a(2, 0, "");
        } catch (SecurityException e) {
            this.e = 3;
            SdkLog.e(this.b, "init bind service SecurityException. " + e.getMessage());
            if (callback != null) {
                String message = e.getMessage();
                a(3, callback, ErrorCode.ERROR_SERVICE_ACCESS_DENIED, message != null ? message : "");
            }
        } catch (Throwable th) {
            this.e = 3;
            SdkLog.e(this.b, "init bind service error. " + th.getMessage());
            if (callback != null) {
                String message2 = th.getMessage();
                a(3, callback, ErrorCode.ERROR_SERVICE_BIND_FAILED, message2 != null ? message2 : "");
            }
        }
    }

    public final boolean b() {
        IAIManager iAIManager;
        IBinder iBinderAsBinder;
        return this.e == 1 && (iAIManager = this.f) != null && (iBinderAsBinder = iAIManager.asBinder()) != null && iBinderAsBinder.isBinderAlive();
    }
}
