package com.scenariorecognition;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.miui.maml.folme.AnimatedTarget;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes2.dex */
public class ScenarioRecognition {
    public static ScenarioRecognition sScenarioRecognitionInstance;
    public Method mAsInterface;
    public Method mFrom;
    public Method mGetCurrentScenario;
    public Method mGetScenario;
    public String mInterfaceDescriptor;
    public int mInvokeFailTimes;
    public Method mMethodWithRimParameter;
    public Method mNotifyScenarioChanged;
    public Method mParcelReadTypedObject;
    public Method mRegisterScenarioCallback;
    public Object mRimCreator;
    public Object mScenarioRecognitionManagerInstance;
    public Method mSetScenarioState;
    public Method mSetScenarioStateWithPara;
    public Method mToBundle;
    public Method mUnregisterScenarioCallback;
    public int MAX_ATTEMPTS = 3;
    public final Map mScenarioCallbackMap = new ConcurrentHashMap();
    public Boolean DEBUG_ENABLE_FLAG = Boolean.FALSE;

    /* JADX INFO: renamed from: com.scenariorecognition.ScenarioRecognition$1, reason: invalid class name */
    class AnonymousClass1 extends Binder {
        public final /* synthetic */ ScenarioRecognition this$0;
        public final /* synthetic */ ScenarioCallback val$cb;

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            try {
                if (this.this$0.mInterfaceDescriptor != null) {
                    parcel.enforceInterface(this.this$0.mInterfaceDescriptor);
                }
                this.val$cb.onScenarioChanged(parcel.readLong(), parcel.readInt() != 0, (Bundle) this.this$0.mToBundle.invoke(this.this$0.mParcelReadTypedObject.invoke(parcel, this.this$0.mRimCreator), new Object[0]));
                return true;
            } catch (Throwable th) {
                System.out.println("CoreScenarioRecognition onTransact error: " + th);
                return true;
            }
        }
    }

    public interface ScenarioCallback {
        void onScenarioChanged(long j, boolean z, Bundle bundle);
    }

    public ScenarioRecognition() {
        this.mInvokeFailTimes = 0;
        try {
            long jNanoTime = System.nanoTime();
            Class<?> cls = Class.forName("miui.scenariorecognition.ScenarioRecognitionManager");
            Class<?> cls2 = Class.forName("miui.scenariorecognition.IScenarioRecognitionCallback");
            Class<?> cls3 = Class.forName("miui.scenariorecognition.IScenarioRecognitionCallback$Stub");
            Class<?> cls4 = Class.forName("miui.scenariorecognition.RimParameter");
            this.mScenarioRecognitionManagerInstance = cls.getDeclaredMethod("getInstance", new Class[0]).invoke(null, new Object[0]);
            Class cls5 = Long.TYPE;
            Class cls6 = Boolean.TYPE;
            this.mSetScenarioState = cls.getDeclaredMethod("setScenarioState", cls5, cls5, cls6);
            this.mSetScenarioStateWithPara = cls.getDeclaredMethod("setScenarioState", cls5, cls5, cls6, cls4);
            this.mNotifyScenarioChanged = cls.getDeclaredMethod("notifyScenarioChanged", cls5, cls5, cls6, cls4);
            this.mGetCurrentScenario = cls.getDeclaredMethod("getCurrentScenario", new Class[0]);
            this.mGetScenario = cls.getDeclaredMethod("getScenario", cls5);
            this.mFrom = cls4.getDeclaredMethod(AnimatedTarget.STATE_TAG_FROM, Bundle.class);
            this.mToBundle = cls4.getDeclaredMethod("toBundle", new Class[0]);
            this.mMethodWithRimParameter = cls.getDeclaredMethod("method", cls5, Integer.TYPE, cls4);
            this.mRegisterScenarioCallback = cls.getDeclaredMethod("registerScenarioCallback", cls5, cls2);
            this.mUnregisterScenarioCallback = cls.getDeclaredMethod("unregisterScenarioCallback", cls5, cls2);
            this.mAsInterface = cls3.getDeclaredMethod("asInterface", IBinder.class);
            this.mInterfaceDescriptor = cls2.getName();
            this.mRimCreator = cls4.getField("CREATOR").get(null);
            this.mParcelReadTypedObject = Parcel.class.getMethod("readTypedObject", Parcelable.Creator.class);
            long jNanoTime2 = System.nanoTime() - jNanoTime;
            System.out.println("CoreScenarioRecognition init time: " + jNanoTime2);
        } catch (Exception e) {
            System.out.println("CoreScenarioRecognition init reflect fail" + e);
            this.mInvokeFailTimes = this.mInvokeFailTimes + 1;
        }
    }

    public static synchronized ScenarioRecognition getInstance() {
        try {
            if (sScenarioRecognitionInstance == null) {
                sScenarioRecognitionInstance = new ScenarioRecognition();
            }
        } catch (Throwable th) {
            throw th;
        }
        return sScenarioRecognitionInstance;
    }

    public void notifyScenarioChanged(long j, long j2, boolean z, Bundle bundle) {
        if (this.mInvokeFailTimes > this.MAX_ATTEMPTS) {
            System.out.println("CoreScenarioRecognition too many mInvokeFailTimes ");
            return;
        }
        if (this.mScenarioRecognitionManagerInstance == null) {
            System.out.println("CoreScenarioRecognition ScenInstance is null! ");
            this.mInvokeFailTimes++;
            return;
        }
        if (this.mNotifyScenarioChanged == null || this.mFrom == null) {
            System.out.println("CoreScenarioRecognition notifyScenarioChanged have method not reflected!");
            this.mInvokeFailTimes++;
            return;
        }
        try {
            long jNanoTime = this.DEBUG_ENABLE_FLAG.booleanValue() ? System.nanoTime() : 0L;
            this.mNotifyScenarioChanged.invoke(this.mScenarioRecognitionManagerInstance, Long.valueOf(j), Long.valueOf(j2), Boolean.valueOf(z), this.mFrom.invoke(null, bundle));
            if (this.DEBUG_ENABLE_FLAG.booleanValue()) {
                long jNanoTime2 = System.nanoTime() - jNanoTime;
                System.out.println("CoreScenarioRecognition notifyScenarioChanged  scenario: " + j + " time: " + j2 + " isStart: " + z + " invoke time: " + jNanoTime2);
            }
        } catch (Exception e) {
            System.out.println("CoreScenarioRecognition notifyScenarioChanged reflect fail" + e);
            this.mInvokeFailTimes = this.mInvokeFailTimes + 1;
        }
    }

    public void setScenarioState(long j, long j2, boolean z) {
        if (this.mInvokeFailTimes > this.MAX_ATTEMPTS) {
            System.out.println("CoreScenarioRecognition too many mInvokeFailTimes ");
            return;
        }
        if (this.mScenarioRecognitionManagerInstance == null) {
            System.out.println("CoreScenarioRecognition ScenInstance is null! ");
            this.mInvokeFailTimes++;
            return;
        }
        if (this.mSetScenarioState == null) {
            System.out.println("CoreScenarioRecognition setScenarioState have method not reflected!");
            this.mInvokeFailTimes++;
            return;
        }
        try {
            long jNanoTime = this.DEBUG_ENABLE_FLAG.booleanValue() ? System.nanoTime() : 0L;
            this.mSetScenarioState.invoke(this.mScenarioRecognitionManagerInstance, Long.valueOf(j), Long.valueOf(j2), Boolean.valueOf(z));
            if (this.DEBUG_ENABLE_FLAG.booleanValue()) {
                long jNanoTime2 = System.nanoTime() - jNanoTime;
                System.out.println("CoreScenarioRecognition setScenarioState  scenario: " + j + " time: " + j2 + " isStart: " + z + " invoke time: " + jNanoTime2);
            }
        } catch (Exception e) {
            System.out.println("CoreScenarioRecognition setScenarioState reflect fail " + e);
            this.mInvokeFailTimes = this.mInvokeFailTimes + 1;
        }
    }
}
