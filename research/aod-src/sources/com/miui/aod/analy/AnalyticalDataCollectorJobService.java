package com.miui.aod.analy;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import com.miui.aod.Utils;
import com.miui.aod.common.BackgroundThread;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class AnalyticalDataCollectorJobService extends JobService {
    public static /* synthetic */ void $r8$lambda$J0L2B1wiGLJySYVEGQcrao4J3R8(AnalyticalDataCollectorJobService analyticalDataCollectorJobService, JobParameters jobParameters) {
        analyticalDataCollectorJobService.trackAodEvent();
        analyticalDataCollectorJobService.jobFinished(jobParameters, false);
    }

    public static boolean isScheduled(Context context) {
        List<JobInfo> allPendingJobs = ((JobScheduler) context.getSystemService(JobScheduler.class)).getAllPendingJobs();
        if (allPendingJobs == null) {
            return false;
        }
        Iterator<JobInfo> it = allPendingJobs.iterator();
        while (it.hasNext()) {
            if (it.next().getId() == 300003) {
                return true;
            }
        }
        return false;
    }

    public static void schedule(Context context) {
        if (isScheduled(context)) {
            Log.i("AnalyticalDataCollectorJobService", "schedule: hasScheduled");
            return;
        }
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JobScheduler.class);
        JobInfo.Builder builder = new JobInfo.Builder(300003, new ComponentName(context, (Class<?>) AnalyticalDataCollectorJobService.class));
        builder.setPeriodic(86400000L);
        builder.setRequiresDeviceIdle(true);
        builder.setPersisted(true);
        if (jobScheduler.schedule(builder.build()) > 0) {
            Log.d("AnalyticalDataCollectorJobService", "SettingsJobSchedulerService schedule success");
        } else {
            Log.d("AnalyticalDataCollectorJobService", "SettingsJobSchedulerService schedule failed");
        }
    }

    @Override // android.app.Service, android.content.ContextWrapper
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context.createDeviceProtectedStorageContext());
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(final JobParameters jobParameters) {
        Log.e("AnalyticalDataCollectorJobService", "onStartJob   jobParameters=" + jobParameters);
        BackgroundThread.post(new Runnable() { // from class: com.miui.aod.analy.AnalyticalDataCollectorJobService$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                AnalyticalDataCollectorJobService.$r8$lambda$J0L2B1wiGLJySYVEGQcrao4J3R8(this.f$0, jobParameters);
            }
        });
        return true;
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }

    public void trackAodEvent() {
        if (Utils.supportAod(this)) {
            HashMap map = new HashMap();
            int i = Settings.Secure.getInt(getContentResolver(), "doze_always_on", -1);
            map.put("aod_mode", "" + i);
            int i2 = Settings.Secure.getInt(getContentResolver(), "aod_mode_time", 1);
            StringBuilder sb = new StringBuilder();
            sb.append("");
            sb.append(i == 1 ? i2 : -1);
            map.put("aod_mode_time", sb.toString());
            AnalyticsWrapper.trackEvent("aod", map);
            AnalyticManager.getInstance(this).trackAodStyleState();
            AnalyticManager.getInstance(this).trackAodEnableState();
            AnalyticManager.getInstance(this).trackAodShowModeState();
            AnalyticManager.getInstance(this).trackNotificationStyleState();
            AnalyticManager.getInstance(this).trackAodVisibleEvent();
            AnalyticManager.getInstance(this).trackNewAodState();
        }
    }
}
