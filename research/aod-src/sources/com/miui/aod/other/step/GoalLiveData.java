package com.miui.aod.other.step;

import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import com.miui.aod.AODApplication;
import com.miui.aod.common.BackgroundThread;
import com.miui.aod.other.ComponentLiveData;

/* JADX INFO: loaded from: classes.dex */
public class GoalLiveData extends ComponentLiveData<GoalBean> {
    public static volatile GoalLiveData sInstance;
    public final ContentObserver mContentObserver = new ContentObserver(new Handler(Looper.getMainLooper())) { // from class: com.miui.aod.other.step.GoalLiveData.1
        @Override // android.database.ContentObserver
        public void onChange(boolean z) {
            super.onChange(z);
            Log.i("GoalLiveData", "onChange: ");
            GoalLiveData.this.freshData();
        }
    };

    public static final class GoalBean {
        public final int goal;

        public GoalBean(int i) {
            this.goal = i;
        }

        public static GoalBean createGoalBean(Cursor cursor) {
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex("goal");
            if (columnIndex < 0) {
                return null;
            }
            try {
                return new GoalBean(Integer.parseInt(cursor.getString(columnIndex)));
            } catch (NumberFormatException e) {
                Log.e("GoalLiveData", "error...", e);
                return null;
            }
        }

        public boolean equals(Object obj) {
            return (obj instanceof GoalBean) && this.goal == ((GoalBean) obj).goal;
        }
    }

    public static class GoalDataFetcher extends ComponentLiveData.DataFetcher<GoalBean> {
        public final String mKey;

        public GoalDataFetcher(String str) {
            this.mKey = str;
        }

        @Override // com.miui.aod.other.ComponentLiveData.DataFetcher
        public GoalBean getData() {
            return StepUtils.queryStepGoal(AODApplication.sInstance);
        }

        @Override // com.miui.aod.other.ComponentLiveData.DataFetcher
        public String getKey() {
            return this.mKey;
        }
    }

    public static /* synthetic */ void $r8$lambda$1hM_4XrXHUZJTBbeSgiJl2VNlUQ(GoalLiveData goalLiveData) {
        goalLiveData.getClass();
        StepUtils.registerContentObserver(AODApplication.sInstance, goalLiveData.mContentObserver, "/widget/steps/goal");
    }

    public static /* synthetic */ void $r8$lambda$faY1e7uMM7pInkNuy2oFoiED7r0(GoalLiveData goalLiveData) {
        goalLiveData.getClass();
        AODApplication.sInstance.getContentResolver().unregisterContentObserver(goalLiveData.mContentObserver);
    }

    public static GoalLiveData getInstance() {
        if (sInstance == null) {
            synchronized (GoalLiveData.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new GoalLiveData();
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
        return new GoalDataFetcher(getDataKey());
    }

    @Override // com.miui.aod.other.ComponentLiveData
    public void freshData() {
        if (StepUtils.supportStepComponent()) {
            super.freshData();
        }
    }

    @Override // com.miui.aod.other.ComponentLiveData
    public String getDataKey() {
        return "GoalLiveData";
    }

    @Override // com.miui.aod.other.ComponentLiveData
    public boolean needEventBus() {
        return StepUtils.supportStepComponent();
    }

    public void registerObserver() {
        if (StepUtils.supportStepComponent()) {
            BackgroundThread.post(new Runnable() { // from class: com.miui.aod.other.step.GoalLiveData$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    GoalLiveData.$r8$lambda$1hM_4XrXHUZJTBbeSgiJl2VNlUQ(this.f$0);
                }
            });
            this.mContentObserver.onChange(false);
        }
    }

    public void unRegisterObserver() {
        if (StepUtils.supportStepComponent()) {
            BackgroundThread.post(new Runnable() { // from class: com.miui.aod.other.step.GoalLiveData$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    GoalLiveData.$r8$lambda$faY1e7uMM7pInkNuy2oFoiED7r0(this.f$0);
                }
            });
        }
    }
}
