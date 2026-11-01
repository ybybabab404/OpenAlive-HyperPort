package com.xiaomi.taiyi.sdk.common;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import com.xiaomi.taiyi.sdk.base.data.ErrorCode;
import com.xiaomi.taiyi.sdk.base.utils.ExtsKt;
import com.xiaomi.taiyi.sdk.base.utils.SdkLog;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class ServiceConnector {
    public static final Companion Companion = new Companion(null);
    public final String TAG;
    public final String action;
    public Context app;
    public final IBinder.DeathRecipient binderDeathRecipient;
    public final CopyOnWriteArraySet callbackSets;
    public final ComponentName component;
    public CountDownLatch connectLatch;
    public volatile int connectState;
    public volatile IBinder iBinder;
    public final Handler mainHandler;
    public final ServiceConnector$serviceConnection$1 serviceConnection;
    public final long timeout;

    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* JADX WARN: Type inference failed for: r2v6, types: [com.xiaomi.taiyi.sdk.common.ServiceConnector$serviceConnection$1] */
    public ServiceConnector(String str, ComponentName component, long j) {
        Intrinsics.checkNotNullParameter(component, "component");
        this.action = str;
        this.component = component;
        this.timeout = j;
        this.TAG = "ServiceConnector" + component.toShortString();
        this.mainHandler = new Handler(Looper.getMainLooper());
        this.connectLatch = new CountDownLatch(1);
        this.callbackSets = new CopyOnWriteArraySet();
        this.serviceConnection = new ServiceConnection() { // from class: com.xiaomi.taiyi.sdk.common.ServiceConnector$serviceConnection$1
            @Override // android.content.ServiceConnection
            public void onBindingDied(ComponentName componentName) {
                SdkLog.w(this.this$0.TAG, "onBindingDied " + componentName);
                this.this$0.destroy();
            }

            @Override // android.content.ServiceConnection
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) throws RemoteException {
                this.this$0.iBinder = iBinder;
                SdkLog.i(this.this$0.TAG, "onServiceConnected " + iBinder);
                if (iBinder == null) {
                    this.this$0.setState(3);
                    this.this$0.dispatchAll(3, ErrorCode.ERROR_SERVICE_INVALID, "service null");
                } else {
                    iBinder.linkToDeath(this.this$0.binderDeathRecipient, 0);
                    this.this$0.setState(1);
                    ServiceConnector.dispatchAll$default(this.this$0, 1, 0, null, 6, null);
                }
            }

            @Override // android.content.ServiceConnection
            public void onServiceDisconnected(ComponentName componentName) {
                SdkLog.w(this.this$0.TAG, "onServiceDisconnected " + componentName);
                this.this$0.destroy();
            }
        };
        this.binderDeathRecipient = new IBinder.DeathRecipient() { // from class: com.xiaomi.taiyi.sdk.common.ServiceConnector$$ExternalSyntheticLambda0
            @Override // android.os.IBinder.DeathRecipient
            public final void binderDied() {
                ServiceConnector.binderDeathRecipient$lambda$0(this.f$0);
            }
        };
    }

    public static final void binderDeathRecipient$lambda$0(ServiceConnector serviceConnector) {
        SdkLog.w(serviceConnector.TAG, "binderDied");
        serviceConnector.destroy();
    }

    public static /* synthetic */ void dispatch$default(ServiceConnector serviceConnector, ConnectCallback connectCallback, int i, int i2, String str, int i3, Object obj) {
        if ((i3 & 4) != 0) {
            i2 = 0;
        }
        if ((i3 & 8) != 0) {
            str = "";
        }
        serviceConnector.dispatch(connectCallback, i, i2, str);
    }

    public static final void dispatch$lambda$10(int i, ConnectCallback connectCallback, int i2, String str) {
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

    public static /* synthetic */ void dispatchAll$default(ServiceConnector serviceConnector, int i, int i2, String str, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i2 = 0;
        }
        if ((i3 & 4) != 0) {
            str = "";
        }
        serviceConnector.dispatchAll(i, i2, str);
    }

    public static final void dispatchAll$lambda$12(ServiceConnector serviceConnector, int i, int i2, String str) {
        for (ConnectCallback connectCallback : serviceConnector.callbackSets) {
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

    public final void addCallback(ConnectCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.callbackSets.add(callback);
    }

    public final void bind(Context context, ConnectCallback connectCallback, Bundle bundle) {
        Intrinsics.checkNotNullParameter(context, "context");
        Context applicationContext = context instanceof Application ? (Application) context : null;
        if (applicationContext == null) {
            applicationContext = context.getApplicationContext();
        }
        if (this.app == null) {
            this.app = applicationContext;
        }
        if (connectCallback != null) {
            addCallback(connectCallback);
        }
        if (isConnecting()) {
            SdkLog.i(this.TAG, "init service connecting...");
            if (connectCallback != null) {
                dispatch$default(this, connectCallback, 2, 0, null, 12, null);
                return;
            }
            return;
        }
        if (isConnected()) {
            SdkLog.i(this.TAG, "init service connected");
            if (connectCallback != null) {
                dispatch$default(this, connectCallback, 1, 0, null, 12, null);
                return;
            }
            return;
        }
        Intrinsics.checkNotNull(applicationContext);
        if (!AIVersion.isServiceSupport(applicationContext)) {
            setState(3);
            SdkLog.e(this.TAG, "init service not support!");
            if (connectCallback != null) {
                dispatch(connectCallback, 3, ErrorCode.ERROR_SERVICE_NOT_SUPPORT, "init service not support!");
                return;
            }
            return;
        }
        SdkLog.i(this.TAG, "init service bind start");
        Intent intent = new Intent(this.action);
        intent.setComponent(this.component);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        intent.setIdentifier(ExtsKt.formatTime());
        try {
            applicationContext.bindService(intent, this.serviceConnection, Build.VERSION.SDK_INT >= 34 ? 513 : 1);
            setState(2);
            dispatchAll$default(this, 2, 0, null, 6, null);
        } catch (Throwable th) {
            setState(3);
            SdkLog.e(this.TAG, "init bind service error. " + th.getMessage());
            if (connectCallback != null) {
                String message = th.getMessage();
                if (message == null) {
                    message = "";
                }
                dispatch(connectCallback, 3, ErrorCode.ERROR_SERVICE_ACCESS_DENIED, message);
            }
        }
    }

    public final synchronized Pair connect(Context context, Bundle bundle) {
        Intrinsics.checkNotNullParameter(context, "context");
        IBinder service = getService();
        if (service != null && isConnected()) {
            SdkLog.i(this.TAG, "connect connected.");
            return new Pair(service, 0);
        }
        if (ExtsKt.isMainThread()) {
            SdkLog.e(this.TAG, "connect on main thread!");
            return new Pair(getService(), Integer.valueOf(ErrorCode.ERROR_WORK_ON_MAIN_THREAD));
        }
        SdkLog.i(this.TAG, "connect start");
        this.connectLatch = new CountDownLatch(1);
        final Ref.IntRef intRef = new Ref.IntRef();
        ConnectCallback connectCallback = new ConnectCallback() { // from class: com.xiaomi.taiyi.sdk.common.ServiceConnector$connect$cb$1
            @Override // com.xiaomi.taiyi.sdk.common.ConnectCallback
            public void onConnectFailed(int i, String msg) {
                Intrinsics.checkNotNullParameter(msg, "msg");
                intRef.element = i;
                this.this$0.connectLatch.countDown();
            }

            @Override // com.xiaomi.taiyi.sdk.common.ConnectCallback
            public void onConnected() {
                this.this$0.connectLatch.countDown();
            }

            @Override // com.xiaomi.taiyi.sdk.common.ConnectCallback
            public void onConnecting() {
                ConnectCallback.DefaultImpls.onConnecting(this);
            }

            @Override // com.xiaomi.taiyi.sdk.common.ConnectCallback
            public void onDisconnected() {
                intRef.element = ErrorCode.ERROR_SERVICE_DISCONNECT;
                this.this$0.connectLatch.countDown();
            }
        };
        bind(context, connectCallback, bundle);
        try {
            this.connectLatch.await(this.timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            SdkLog.e(this.TAG, "connect await error. " + e.getMessage());
            if (this.iBinder == null) {
                intRef.element = ErrorCode.ERROR_AWAIT_EXCEPTION;
            }
        }
        removeCallback(connectCallback);
        if (this.iBinder == null && intRef.element == 0) {
            intRef.element = ErrorCode.ERROR_AWAIT_TIMEOUT;
            SdkLog.e(this.TAG, "connect await timeout " + this.timeout);
        } else if (this.iBinder != null) {
            intRef.element = 0;
        }
        SdkLog.i(this.TAG, "connect end. service = " + this.iBinder + " code =  " + intRef.element);
        return new Pair(this.iBinder, Integer.valueOf(intRef.element));
    }

    public final void destroy() {
        ServiceConnector serviceConnector;
        Object objM1061constructorimpl;
        Unit unit;
        if (isDisconnected()) {
            serviceConnector = this;
            SdkLog.d(serviceConnector.TAG, "destroy has disconnected");
        } else {
            SdkLog.i(this.TAG, "destroy");
            setState(0);
            serviceConnector = this;
            dispatchAll$default(serviceConnector, 0, 0, null, 6, null);
            try {
                Result.Companion companion = Result.Companion;
                IBinder iBinder = serviceConnector.iBinder;
                if (iBinder != null) {
                    iBinder.unlinkToDeath(serviceConnector.binderDeathRecipient, 0);
                }
                Context context = serviceConnector.app;
                if (context != null) {
                    context.unbindService(serviceConnector.serviceConnection);
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
                SdkLog.w(serviceConnector.TAG, "unlinkToDeath or unbindService error. " + thM1063exceptionOrNullimpl.getMessage());
            }
            serviceConnector.iBinder = null;
        }
        serviceConnector.callbackSets.clear();
    }

    public final void dispatch(final ConnectCallback connectCallback, final int i, final int i2, final String str) {
        this.mainHandler.post(new Runnable() { // from class: com.xiaomi.taiyi.sdk.common.ServiceConnector$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                ServiceConnector.dispatch$lambda$10(i, connectCallback, i2, str);
            }
        });
    }

    public final void dispatchAll(final int i, final int i2, final String str) {
        this.mainHandler.post(new Runnable() { // from class: com.xiaomi.taiyi.sdk.common.ServiceConnector$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                ServiceConnector.dispatchAll$lambda$12(this.f$0, i, i2, str);
            }
        });
    }

    public final IBinder getService() {
        return this.iBinder;
    }

    public final boolean isConnected() {
        IBinder iBinder;
        return this.connectState == 1 && (iBinder = this.iBinder) != null && iBinder.isBinderAlive();
    }

    public final boolean isConnecting() {
        return this.connectState == 2;
    }

    public final boolean isDisconnected() {
        return this.connectState == 0 || this.connectState == 3;
    }

    public final void removeCallback(ConnectCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        this.callbackSets.remove(callback);
    }

    public final void setState(int i) {
        this.connectState = i;
    }
}
