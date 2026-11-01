package com.miui.aod.common;

import android.hardware.HardwareBuffer;
import android.hardware.Sensor;
import android.hardware.SensorAdditionalInfo;
import android.hardware.SensorDirectChannel;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.TriggerEventListener;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.MemoryFile;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AsyncSensorManager extends SensorManager {
    private static final String TAG = "AsyncSensorManager";
    private static final HandlerThread sHandlerThread = new HandlerThread("async_sensor");
    private static AsyncSensorManager sInstance;
    final Handler mHandler;
    private final SensorManager mInner;
    private final List<Sensor> mSensorCache;

    public static /* synthetic */ void $r8$lambda$YSXdo9RZOmuJrvX0Am9xl99H70k(AsyncSensorManager asyncSensorManager, TriggerEventListener triggerEventListener, Sensor sensor) {
        if (asyncSensorManager.mInner.requestTriggerSensor(triggerEventListener, sensor)) {
            return;
        }
        Log.e(TAG, "Requesting " + String.valueOf(triggerEventListener) + " for " + String.valueOf(sensor) + " failed.");
    }

    public static /* synthetic */ void $r8$lambda$gG84C7exAJ4Oou0aJ6fEiutPLZk(AsyncSensorManager asyncSensorManager, SensorEventListener sensorEventListener, Sensor sensor, int i, int i2, Handler handler) {
        if (asyncSensorManager.mInner.registerListener(sensorEventListener, sensor, i, i2, handler)) {
            return;
        }
        Log.e(TAG, "Registering " + String.valueOf(sensorEventListener) + " for " + String.valueOf(sensor) + " failed.");
    }

    public static /* synthetic */ void $r8$lambda$mcn3a2UFiEMKkMNrDaNIfBdC8LQ(AsyncSensorManager asyncSensorManager, Sensor sensor, SensorEventListener sensorEventListener) {
        if (sensor == null) {
            asyncSensorManager.mInner.unregisterListener(sensorEventListener);
        } else {
            asyncSensorManager.mInner.unregisterListener(sensorEventListener, sensor);
        }
    }

    public static /* synthetic */ void $r8$lambda$pM26COySVNqbjYPTaCbH5TDAn4w(AsyncSensorManager asyncSensorManager, TriggerEventListener triggerEventListener, Sensor sensor) {
        if (asyncSensorManager.mInner.cancelTriggerSensor(triggerEventListener, sensor)) {
            return;
        }
        Log.e(TAG, "Canceling " + String.valueOf(triggerEventListener) + " for " + String.valueOf(sensor) + " failed.");
    }

    private AsyncSensorManager(SensorManager sensorManager) {
        this.mInner = sensorManager;
        HandlerThread handlerThread = sHandlerThread;
        if (!handlerThread.isAlive()) {
            handlerThread.start();
        }
        this.mHandler = new Handler(handlerThread.getLooper());
        this.mSensorCache = sensorManager.getSensorList(-1);
    }

    public static synchronized AsyncSensorManager getInstance(SensorManager sensorManager) {
        try {
            if (sInstance == null) {
                sInstance = new AsyncSensorManager(sensorManager);
            }
        } catch (Throwable th) {
            throw th;
        }
        return sInstance;
    }

    public boolean cancelTriggerSensorImpl(final TriggerEventListener triggerEventListener, final Sensor sensor, boolean z) {
        Preconditions.checkArgument(z);
        this.mHandler.post(new Runnable() { // from class: com.miui.aod.common.AsyncSensorManager$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                AsyncSensorManager.$r8$lambda$pM26COySVNqbjYPTaCbH5TDAn4w(this.f$0, triggerEventListener, sensor);
            }
        });
        return true;
    }

    public int configureDirectChannelImpl(SensorDirectChannel sensorDirectChannel, Sensor sensor, int i) {
        throw new UnsupportedOperationException("not implemented");
    }

    public SensorDirectChannel createDirectChannelImpl(MemoryFile memoryFile, HardwareBuffer hardwareBuffer) {
        throw new UnsupportedOperationException("not implemented");
    }

    public void destroyDirectChannelImpl(SensorDirectChannel sensorDirectChannel) {
        throw new UnsupportedOperationException("not implemented");
    }

    public boolean flushImpl(SensorEventListener sensorEventListener) {
        return this.mInner.flush(sensorEventListener);
    }

    public List<Sensor> getFullDynamicSensorList() {
        return this.mInner.getDynamicSensorList(-1);
    }

    public List<Sensor> getFullSensorList() {
        return this.mSensorCache;
    }

    public boolean initDataInjectionImpl(boolean z) {
        throw new UnsupportedOperationException("not implemented");
    }

    public boolean injectSensorDataImpl(Sensor sensor, float[] fArr, int i, long j) {
        throw new UnsupportedOperationException("not implemented");
    }

    public void registerDynamicSensorCallbackImpl(final SensorManager.DynamicSensorCallback dynamicSensorCallback, final Handler handler) {
        this.mHandler.post(new Runnable() { // from class: com.miui.aod.common.AsyncSensorManager$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mInner.registerDynamicSensorCallback(dynamicSensorCallback, handler);
            }
        });
    }

    public boolean registerListenerImpl(final SensorEventListener sensorEventListener, final Sensor sensor, final int i, final Handler handler, final int i2, int i3) {
        this.mHandler.post(new Runnable() { // from class: com.miui.aod.common.AsyncSensorManager$$ExternalSyntheticLambda4
            @Override // java.lang.Runnable
            public final void run() {
                AsyncSensorManager.$r8$lambda$gG84C7exAJ4Oou0aJ6fEiutPLZk(this.f$0, sensorEventListener, sensor, i, i2, handler);
            }
        });
        return true;
    }

    public boolean requestTriggerSensorImpl(final TriggerEventListener triggerEventListener, final Sensor sensor) {
        this.mHandler.post(new Runnable() { // from class: com.miui.aod.common.AsyncSensorManager$$ExternalSyntheticLambda5
            @Override // java.lang.Runnable
            public final void run() {
                AsyncSensorManager.$r8$lambda$YSXdo9RZOmuJrvX0Am9xl99H70k(this.f$0, triggerEventListener, sensor);
            }
        });
        return true;
    }

    public boolean setOperationParameterImpl(final SensorAdditionalInfo sensorAdditionalInfo) {
        this.mHandler.post(new Runnable() { // from class: com.miui.aod.common.AsyncSensorManager$$ExternalSyntheticLambda3
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mInner.setOperationParameter(sensorAdditionalInfo);
            }
        });
        return true;
    }

    public void unregisterDynamicSensorCallbackImpl(final SensorManager.DynamicSensorCallback dynamicSensorCallback) {
        this.mHandler.post(new Runnable() { // from class: com.miui.aod.common.AsyncSensorManager$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.mInner.unregisterDynamicSensorCallback(dynamicSensorCallback);
            }
        });
    }

    public void unregisterListenerImpl(final SensorEventListener sensorEventListener, final Sensor sensor) {
        this.mHandler.post(new Runnable() { // from class: com.miui.aod.common.AsyncSensorManager$$ExternalSyntheticLambda6
            @Override // java.lang.Runnable
            public final void run() {
                AsyncSensorManager.$r8$lambda$mcn3a2UFiEMKkMNrDaNIfBdC8LQ(this.f$0, sensor, sensorEventListener);
            }
        });
    }
}
