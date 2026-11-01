package com.miui.aod.attention;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.service.attention.IAttentionCallback;
import android.service.attention.IAttentionService;
import android.util.Log;
import com.miui.aod.Utils;
import com.miui.aod.resource.CachedSettings;
import com.miui.aod.util.ReflectUtil;
import com.miui.aod.util.wakelock.WakeLock;
import com.miui.aod.utils.UserHandleUtils;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: loaded from: classes.dex */
public final class AttentionManager implements IAttentionManager {
    public static final String TAG = "AttentionManager";
    public int hashCode;
    public volatile IAttentionService mAttentionService;
    public ConnectionCallback mConnectionCallback;
    public final Context mContext;
    public final WakeLock mWakeLock;
    public final ServiceConnection mConnection = new AnonymousClass1();
    public final Handler mHandler = new Handler(Looper.getMainLooper());

    /* JADX INFO: renamed from: com.miui.aod.attention.AttentionManager$1, reason: invalid class name */
    public class AnonymousClass1 implements ServiceConnection {
        public AnonymousClass1() {
        }

        public void doDie() {
            if (AttentionManager.this.mAttentionService != null) {
                Log.w(AttentionManager.TAG, "do die: ", new Throwable());
                AttentionManager.this.unbind();
                AttentionManager.this.mAttentionService = null;
                AttentionManager.this.bind();
                if (AttentionManager.this.mConnectionCallback != null) {
                    AttentionManager.this.mHandler.post(new Runnable() { // from class: com.miui.aod.attention.AttentionManager$1$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            AttentionManager.this.mConnectionCallback.onReconnected();
                        }
                    });
                }
            }
        }

        @Override // android.content.ServiceConnection
        public void onBindingDied(ComponentName componentName) {
            AttentionThread.post(new AttentionManager$1$$ExternalSyntheticLambda0(this));
        }

        @Override // android.content.ServiceConnection
        public void onNullBinding(ComponentName componentName) {
            AttentionThread.post(new AttentionManager$1$$ExternalSyntheticLambda0(this));
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(AttentionManager.TAG, "onServiceConnected: ");
            try {
                AttentionManager.this.mAttentionService = (IAttentionService) ReflectUtil.callStaticObjectMethod(IAttentionService.Stub.class, IAttentionService.class, "asInterface", new Class[]{IBinder.class}, iBinder);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                Log.wtf(AttentionManager.TAG, "onServiceConnected error: ", e);
            }
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            AttentionThread.post(new AttentionManager$1$$ExternalSyntheticLambda0(this));
        }
    }

    public interface AttentionCallback {
        void onFailure(IAttentionCallback iAttentionCallback, int i);

        void onSuccess(IAttentionCallback iAttentionCallback, int i, long j);
    }

    public interface ConnectionCallback {
        void onReconnected();
    }

    public static class IAttentionCallbackStub extends com.miui.aod.aidl.IAttentionCallback.Stub {
        public AttentionCallback callback;
        public long lastTimeStamp = -1;
        public Handler mHandler;
        public WakeLock mWakeLock;

        public static /* synthetic */ void $r8$lambda$TPfD4FfsKf1Vbv6GjL4GP3jnaLU(IAttentionCallbackStub iAttentionCallbackStub, int i, long j) {
            AttentionCallback attentionCallback = iAttentionCallbackStub.callback;
            if (attentionCallback != null) {
                attentionCallback.onSuccess(iAttentionCallbackStub, i, j);
            }
        }

        public IAttentionCallbackStub(Handler handler, AttentionCallback attentionCallback, WakeLock wakeLock) {
            this.mHandler = handler;
            this.callback = attentionCallback;
            this.mWakeLock = wakeLock;
        }

        @Override // android.service.attention.IAttentionCallback
        public void onFailure(int i) {
            AttentionCallback attentionCallback = this.callback;
            if (attentionCallback != null) {
                attentionCallback.onFailure(this, i);
            }
        }

        @Override // android.service.attention.IAttentionCallback
        public void onSuccess(final int i, final long j) {
            WakeLock wakeLock;
            Log.d(AttentionManager.TAG, "onSuccess: " + j);
            Handler handler = this.mHandler;
            if (handler != null && (wakeLock = this.mWakeLock) != null) {
                long j2 = this.lastTimeStamp;
                if (j2 == -1 || j >= j2 + 3000) {
                    handler.post(wakeLock.wrapWithLog(new Runnable() { // from class: com.miui.aod.attention.AttentionManager$IAttentionCallbackStub$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            AttentionManager.IAttentionCallbackStub.$r8$lambda$TPfD4FfsKf1Vbv6GjL4GP3jnaLU(this.f$0, i, j);
                        }
                    }, AttentionManager.TAG));
                    this.lastTimeStamp = j;
                    return;
                }
            }
            Log.d(AttentionManager.TAG, "onSuccess: ignoring ...");
        }

        public void release() {
            this.mWakeLock = null;
            this.callback = null;
            this.mHandler = null;
        }
    }

    public static /* synthetic */ void $r8$lambda$OzeLoFhWqaHFATLFMfSSn2UyIXM(AttentionManager attentionManager) {
        attentionManager.getClass();
        Intent intent = new Intent("android.service.attention.AttentionService");
        intent.setPackage("com.xiaomi.aon");
        boolean zBindServiceAsUser = attentionManager.mContext.bindServiceAsUser(intent, attentionManager.mConnection, 1, UserHandleUtils.getUserHandle(UserHandleUtils.USER_CURRENT));
        Log.i(TAG, "bind: result = " + zBindServiceAsUser);
    }

    public static /* synthetic */ void $r8$lambda$TpGwgP68JurnNfeIdZ9lRjx3680(AttentionManager attentionManager) {
        attentionManager.getClass();
        try {
            attentionManager.mContext.unbindService(attentionManager.mConnection);
        } catch (IllegalArgumentException e) {
            Log.w(TAG, "unbind: service not registered", e);
        }
        Log.i(TAG, "unbind: completed");
    }

    public AttentionManager(Context context, WakeLock wakeLock) {
        this.mContext = context;
        this.mWakeLock = wakeLock;
    }

    public static boolean checkAONInstalled(Context context) {
        try {
            context.getPackageManager().getPackageInfo("com.xiaomi.aon", 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.w(TAG, "checkAONInstalled: package not found", e);
            return false;
        }
    }

    public static boolean supportAttention(final Context context) {
        return Utils.SUPPORT_AOD_AON && CachedSettings.getBoolean(TAG, new CachedSettings.Callable() { // from class: com.miui.aod.attention.AttentionManager$$ExternalSyntheticLambda0
            @Override // com.miui.aod.resource.CachedSettings.Callable, java.util.concurrent.Callable
            public final Object call() {
                return Boolean.valueOf(AttentionManager.checkAONInstalled(context));
            }
        });
    }

    public void bind() {
        AttentionThread.post(new Runnable() { // from class: com.miui.aod.attention.AttentionManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AttentionManager.$r8$lambda$OzeLoFhWqaHFATLFMfSSn2UyIXM(this.f$0);
            }
        });
    }

    public void bind(ConnectionCallback connectionCallback) {
        this.mConnectionCallback = connectionCallback;
        bind();
    }

    public void cancelAttentionCheck(IAttentionCallback iAttentionCallback, String str) {
        if (this.mAttentionService != null) {
            try {
                ReflectUtil.callObjectMethod2(this.mAttentionService, "cancelAttentionCheck", new Class[]{IAttentionCallback.class}, iAttentionCallback);
                String str2 = TAG;
                Log.d(str2, "AttentionCancel: " + iAttentionCallback.hashCode() + " for reason :" + str);
                if (this.hashCode != iAttentionCallback.hashCode()) {
                    Log.wtf(str2, "cancel the ERROR callback", new Throwable());
                }
            } catch (Exception e) {
                Log.w(TAG, "cancelAttentionCheck error: ", e);
            }
        }
    }

    public IAttentionCallback checkAttention(AttentionCallback attentionCallback, String str) {
        if (this.mAttentionService != null) {
            try {
                IAttentionCallback iAttentionCallback = (IAttentionCallback) IAttentionCallbackStub.class.getDeclaredConstructor(Handler.class, AttentionCallback.class, WakeLock.class).newInstance(this.mHandler, attentionCallback, this.mWakeLock);
                ReflectUtil.callObjectMethod2(this.mAttentionService, "checkAttention", new Class[]{IAttentionCallback.class}, iAttentionCallback);
                this.hashCode = iAttentionCallback.hashCode();
                Log.d(TAG, "AttentionCheck: " + iAttentionCallback.hashCode() + " for reason :" + str);
                return iAttentionCallback;
            } catch (Exception e) {
                Log.e(TAG, "checkAttention error: ", e);
            }
        }
        Log.wtf(TAG, "mAttentionService == null", new Throwable());
        return null;
    }

    public void releaseAttentionCheck(IAttentionCallback iAttentionCallback) {
        if (iAttentionCallback instanceof IAttentionCallbackStub) {
            ((IAttentionCallbackStub) iAttentionCallback).release();
        }
    }

    public void unbind() {
        AttentionThread.post(new Runnable() { // from class: com.miui.aod.attention.AttentionManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                AttentionManager.$r8$lambda$TpGwgP68JurnNfeIdZ9lRjx3680(this.f$0);
            }
        });
    }
}
