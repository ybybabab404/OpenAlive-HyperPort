package com.miui.aod.other.step;

import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.miui.aod.AODApplication;
import com.miui.aod.common.BackgroundThread;
import com.miui.aod.other.ComponentLiveData;

/* JADX INFO: loaded from: classes.dex */
public class StepLiveData extends ComponentLiveData<StepBean> {
    public static volatile StepLiveData sInstance;
    public final ContentObserver mContentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.miui.aod.other.step.StepLiveData.1
        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            Log.i("StepLiveData", "onChange: ");
            StepLiveData.this.freshData();
        }
    };

    public interface IStepComponent {
    }

    public static class StepDataFetcher extends ComponentLiveData.DataFetcher<StepBean> {
        public final String mKey;

        public StepDataFetcher(String str) {
            this.mKey = str;
        }

        @Override // com.miui.aod.other.ComponentLiveData.DataFetcher
        public StepBean getData() {
            return StepUtils.queryStep(AODApplication.sInstance);
        }

        @Override // com.miui.aod.other.ComponentLiveData.DataFetcher
        public String getKey() {
            return this.mKey;
        }
    }

    public static /* synthetic */ void $r8$lambda$6iIMGkXHGAn93A1BdE6HXNrQfts(StepLiveData stepLiveData) {
        stepLiveData.getClass();
        AODApplication.sInstance.getContentResolver().unregisterContentObserver(stepLiveData.mContentObserver);
    }

    public static /* synthetic */ void $r8$lambda$NkZ7hxnFNW7Qm67p11Zzcmhq0z4(StepLiveData stepLiveData) {
        stepLiveData.getClass();
        AODApplication.sInstance.getContentResolver().unregisterContentObserver(stepLiveData.mContentObserver);
        StepUtils.registerContentObserver(AODApplication.sInstance, stepLiveData.mContentObserver, "/widget/steps/simple");
    }

    public static StepLiveData getInstance() {
        if (sInstance == null) {
            synchronized (StepLiveData.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new StepLiveData();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return sInstance;
    }

    @Override // com.miui.aod.other.ComponentLiveData
    public ComponentLiveData.DataFetcher createDataFetcherTask() {
        return new StepDataFetcher(getDataKey());
    }

    @Override // com.miui.aod.other.ComponentLiveData
    public void freshData() {
        if (StepUtils.supportStepComponent()) {
            super.freshData();
        }
    }

    @Override // com.miui.aod.other.ComponentLiveData
    public String getDataKey() {
        return "StepLiveData";
    }

    @Override // com.miui.aod.other.ComponentLiveData
    public boolean needEventBus() {
        return StepUtils.supportStepComponent();
    }

    public void registerObserver() {
        if (StepUtils.supportStepComponent()) {
            BackgroundThread.post(new Runnable() { // from class: com.miui.aod.other.step.StepLiveData$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    StepLiveData.$r8$lambda$NkZ7hxnFNW7Qm67p11Zzcmhq0z4(this.f$0);
                }
            });
            this.mContentObserver.onChange(false);
        }
    }

    @Override // com.miui.aod.other.ComponentLiveData
    public synchronized void startPolling(String str, long j) {
        if (StepUtils.supportStepComponent()) {
            super.startPolling(str, j);
        }
    }

    public void unRegisterObserver() {
        if (StepUtils.supportStepComponent()) {
            BackgroundThread.post(new Runnable() { // from class: com.miui.aod.other.step.StepLiveData$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    StepLiveData.$r8$lambda$6iIMGkXHGAn93A1BdE6HXNrQfts(this.f$0);
                }
            });
        }
    }
}
