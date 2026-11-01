package com.miui.aod.other;

import android.util.Log;
import androidx.lifecycle.LiveData;
import java.util.Timer;
import java.util.TimerTask;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* JADX INFO: loaded from: classes.dex */
public abstract class ComponentLiveData<T> extends LiveData<T> {
    public long mLastFreshData;
    public boolean mPolling;
    public TimerTask mPollingTask;
    public Timer mTimer;
    public volatile boolean mValueSet = false;

    public static abstract class DataFetcher<T> implements Runnable {
        public abstract Object getData();

        public abstract String getKey();

        @Override // java.lang.Runnable
        public void run() {
            EventBus.getDefault().post(new DataWrapper(getKey(), getData()));
        }
    }

    public static class DataWrapper<T> {
        public final Object data;
        public final String key;

        public DataWrapper(String str, Object obj) {
            this.key = str;
            this.data = obj;
        }
    }

    public ComponentLiveData() {
        if (needEventBus()) {
            EventBus.getDefault().register(this);
        }
    }

    public abstract DataFetcher createDataFetcherTask();

    public void freshData() {
        if (Math.abs(System.currentTimeMillis() - this.mLastFreshData) < 2000) {
            Log.i("ComponentLiveData", "freshData too frequently");
        } else {
            this.mLastFreshData = System.currentTimeMillis();
            DataFetchExecutor.getInstance().executeTask(createDataFetcherTask());
        }
    }

    public abstract String getDataKey();

    public boolean needEventBus() {
        return true;
    }

    @Override // androidx.lifecycle.LiveData
    public void onActive() {
        super.onActive();
        freshData();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onComponentDataEvent(DataWrapper<T> dataWrapper) {
        String dataKey = getDataKey();
        if (dataKey == null || !dataKey.equals(dataWrapper.key)) {
            return;
        }
        Object value = getValue();
        Object obj = dataWrapper.data;
        if (obj == null || !obj.equals(value)) {
            setValue(obj);
        }
    }

    @Override // androidx.lifecycle.LiveData
    public void onInactive() {
        super.onInactive();
        stopPolling();
    }

    @Override // androidx.lifecycle.LiveData
    public void setValue(Object obj) {
        super.setValue(obj);
        this.mValueSet = true;
    }

    public synchronized void startPolling(String str, long j) {
        try {
            if (this.mPolling) {
                return;
            }
            if (this.mTimer == null) {
                this.mTimer = new Timer(str);
            }
            if (this.mPollingTask == null) {
                this.mPollingTask = new TimerTask() { // from class: com.miui.aod.other.ComponentLiveData.1
                    @Override // java.util.TimerTask, java.lang.Runnable
                    public void run() {
                        ComponentLiveData.this.freshData();
                    }
                };
            }
            this.mTimer.schedule(this.mPollingTask, j, j);
            this.mPolling = true;
        } catch (Throwable th) {
            throw th;
        }
    }

    public synchronized void stopPolling() {
        try {
            this.mPolling = false;
            TimerTask timerTask = this.mPollingTask;
            if (timerTask != null) {
                timerTask.cancel();
                this.mPollingTask = null;
            }
            Timer timer = this.mTimer;
            if (timer != null) {
                timer.cancel();
                this.mTimer = null;
            }
        } catch (Throwable th) {
            throw th;
        }
    }
}
