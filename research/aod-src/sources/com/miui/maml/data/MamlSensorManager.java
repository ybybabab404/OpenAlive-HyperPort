package com.miui.maml.data;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.ArrayMap;
import com.miui.maml.util.MamlLog;
import java.util.Iterator;
import java.util.WeakHashMap;

/* JADX INFO: loaded from: classes2.dex */
public class MamlSensorManager {
    private static final int INVALID_TYPE = -1;
    private static final String TAG = "MAML_SensorManager";
    private static ArrayMap<String, MamlSensor> sSensors = new ArrayMap<>();
    private static final Object sLock = new Object();

    public static class MamlSensor {
        private int mRate;
        private boolean mRegistered;
        private Sensor mSensor;
        private SensorManager mSensorManager;
        private int mType;
        private WeakHashMap<SensorEventListener, Integer> mCallbacks = new WeakHashMap<>();
        private final Object mLock = new Object();
        private SensorEventListener mListener = new SensorEventListener() { // from class: com.miui.maml.data.MamlSensorManager.MamlSensor.1
            @Override // android.hardware.SensorEventListener
            public void onAccuracyChanged(Sensor sensor, int i) {
            }

            @Override // android.hardware.SensorEventListener
            public void onSensorChanged(SensorEvent sensorEvent) {
                synchronized (MamlSensor.this.mLock) {
                    try {
                        Iterator it = MamlSensor.this.mCallbacks.keySet().iterator();
                        while (it.hasNext()) {
                            ((SensorEventListener) it.next()).onSensorChanged(sensorEvent);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
            }
        };

        public MamlSensor(Context context, int i, int i2) {
            if (i == -1) {
                MamlLog.e(MamlSensorManager.TAG, "Wront sensor type: " + i);
                return;
            }
            this.mType = i;
            this.mRate = i2;
            SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
            this.mSensorManager = sensorManager;
            Sensor defaultSensor = sensorManager.getDefaultSensor(i);
            this.mSensor = defaultSensor;
            if (defaultSensor == null) {
                MamlLog.e(MamlSensorManager.TAG, "Fail to get sensor! TYPE: " + this.mType);
            }
        }

        private boolean registerListener() {
            Sensor sensor = this.mSensor;
            if (sensor != null && !this.mRegistered) {
                try {
                    this.mRegistered = this.mSensorManager.registerListener(this.mListener, sensor, this.mRate);
                    MamlLog.d(MamlSensorManager.TAG, "registerListener " + this.mType);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return this.mRegistered;
        }

        private void unRegisterListener() {
            Sensor sensor = this.mSensor;
            if (sensor == null || !this.mRegistered) {
                return;
            }
            try {
                this.mSensorManager.unregisterListener(this.mListener, sensor);
                this.mRegistered = false;
                MamlLog.d(MamlSensorManager.TAG, "unregisterListener " + this.mType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public void addCallback(int i, SensorEventListener sensorEventListener) {
            synchronized (this.mLock) {
                this.mCallbacks.put(sensorEventListener, Integer.valueOf(i));
            }
            if (this.mRate < i) {
                this.mRate = i;
                if (this.mRegistered) {
                    unRegisterListener();
                }
            }
            registerListener();
        }

        /* JADX WARN: Code duplicated, block: B:21:0x0050  */
        public void removeCallback(SensorEventListener sensorEventListener) {
            boolean z;
            synchronized (this.mLock) {
                try {
                    Integer num = this.mCallbacks.get(sensorEventListener);
                    if (num == null) {
                        return;
                    }
                    this.mCallbacks.remove(sensorEventListener);
                    if (this.mRate == num.intValue()) {
                        this.mRate = 3;
                        for (Integer num2 : this.mCallbacks.values()) {
                            if (this.mRate > num2.intValue()) {
                                this.mRate = num2.intValue();
                            }
                        }
                        if (this.mRate != num.intValue()) {
                            z = true;
                        } else {
                            z = false;
                        }
                    } else {
                        z = false;
                    }
                    if (this.mCallbacks.size() == 0) {
                        unRegisterListener();
                    } else if (z) {
                        unRegisterListener();
                        registerListener();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public static class MamlSensorManagerHolder {
        public static final MamlSensorManager INSTANCE = new MamlSensorManager();

        private MamlSensorManagerHolder() {
        }
    }

    private MamlSensorManager() {
    }

    public static MamlSensorManager getInstance() {
        return MamlSensorManagerHolder.INSTANCE;
    }

    private int getType(String str) {
        str.getClass();
        switch (str) {
            case "orientation":
                return 3;
            case "pressure":
                return 6;
            case "proximity":
                return 8;
            case "light":
                return 5;
            case "gravity":
                return 9;
            case "gyroscope":
                return 4;
            case "linear_acceleration":
                return 10;
            case "accelerometer":
                return 1;
            default:
                return -1;
        }
    }

    private int getValidRate(int i) {
        if (i == 0 || i == 1) {
            return 1;
        }
        return i != 2 ? 3 : 2;
    }

    public void registerListener(Context context, String str, int i, SensorEventListener sensorEventListener) {
        int validRate = getValidRate(i);
        synchronized (sLock) {
            try {
                MamlSensor mamlSensor = sSensors.get(str);
                if (mamlSensor != null) {
                    mamlSensor.addCallback(validRate, sensorEventListener);
                } else {
                    MamlSensor mamlSensor2 = new MamlSensor(context, getType(str), validRate);
                    if (mamlSensor2.mSensor != null) {
                        mamlSensor2.addCallback(validRate, sensorEventListener);
                        sSensors.put(str, mamlSensor2);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void unregisterListener(String str, SensorEventListener sensorEventListener) {
        synchronized (sLock) {
            try {
                MamlSensor mamlSensor = sSensors.get(str);
                if (mamlSensor != null) {
                    mamlSensor.removeCallback(sensorEventListener);
                    if (mamlSensor.mCallbacks.size() == 0) {
                        sSensors.remove(str);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
