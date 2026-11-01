package com.miui.aod.theme;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.ArraySet;
import android.util.Log;
import com.miui.aod.AODApplication;
import com.miui.aod.AODStyleController;
import com.miui.aod.category.ImageSelectorCategoryInfo;
import com.miui.aod.db.ThemeEntity;
import com.miui.aod.db.ThemeProviderHelper;
import com.miui.aod.theme.beans.ThemeBean;
import com.miui.aod.theme.utils.ThemeUtils;
import com.miui.aod.util.FileUtils;
import java.io.File;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class ThemeResourceClearJobService extends JobService {
    public static Handler sHandler;

    public class ClearTask implements Runnable {
        public final JobParameters mParameters;

        public ClearTask(JobParameters jobParameters) {
            this.mParameters = jobParameters;
        }

        @Override // java.lang.Runnable
        public void run() {
            File[] fileArrListFiles;
            try {
                Log.i("ThemeResourceClearJobSe", "ThemeResourceClearJob started");
                List allThemes = ThemeProviderHelper.getInstance(AODApplication.sInstance).getAllThemes();
                if (allThemes != null) {
                    ArraySet arraySet = new ArraySet();
                    Iterator it = allThemes.iterator();
                    while (it.hasNext()) {
                        arraySet.add(((ThemeEntity) it.next()).getThemeDirName());
                    }
                    ThemeBean themeBean = AODStyleController.getStyleInfo(AODApplication.sInstance).getThemeBean();
                    if (themeBean != null && themeBean.mTemporaryTheme == 1) {
                        arraySet.add(themeBean.mThemeDirName);
                    }
                    ThemeUtils.clearThemeResource(AODApplication.sInstance, arraySet);
                    File file = new File(ThemeUtils.getRootDir(AODApplication.sInstance));
                    Log.i("ThemeResourceClearJobSe", "ClearJob: count to reserve : " + arraySet.size() + " rootDir: " + file.getName());
                    if (file.exists() && (fileArrListFiles = file.listFiles()) != null) {
                        for (File file2 : fileArrListFiles) {
                            if (!arraySet.contains(file2.getName())) {
                                file2.delete();
                            }
                        }
                    }
                    ThemeResourceClearJobService.clearImageShareFile();
                }
            } catch (Exception e) {
                Log.e("ThemeResourceClearJobSe", "run: " + e.getMessage());
            } finally {
                ThemeResourceClearJobService.this.jobFinished(this.mParameters, false);
            }
        }
    }

    public static void clearImageShareFile() {
        File file = new File(ImageSelectorCategoryInfo.ALBUM_PATH);
        if (file.exists() && file.isDirectory()) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            File[] fileArrListFiles = file.listFiles();
            if (fileArrListFiles == null || fileArrListFiles.length == 0) {
                file.delete();
                return;
            }
            for (File file2 : fileArrListFiles) {
                long jLastModified = jCurrentTimeMillis - file2.lastModified();
                if (jLastModified > 86400000) {
                    Log.i("ThemeResourceClearJobSe", "clearImageShareFile: elapse " + jLastModified + " " + file2.getName());
                    FileUtils.deleteFileAll(file2);
                }
            }
        }
    }

    public static Handler getHandler() {
        Handler handler;
        synchronized (ThemeResourceClearJobService.class) {
            try {
                if (sHandler == null) {
                    HandlerThread handlerThread = new HandlerThread("ThemeResourceClear", 10);
                    handlerThread.start();
                    sHandler = new Handler(handlerThread.getLooper());
                }
                handler = sHandler;
            } catch (Throwable th) {
                throw th;
            }
        }
        return handler;
    }

    public static boolean isScheduled(Context context) {
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JobScheduler.class);
        if (jobScheduler == null) {
            return false;
        }
        Iterator<JobInfo> it = jobScheduler.getAllPendingJobs().iterator();
        while (it.hasNext()) {
            if (it.next().getId() == 666) {
                return true;
            }
        }
        return false;
    }

    public static void schedule(Context context) {
        Log.i("ThemeResourceClearJobSe", "schedule: ");
        if (isScheduled(context)) {
            return;
        }
        try {
            JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
            if (jobScheduler == null) {
                return;
            }
            jobScheduler.schedule(new JobInfo.Builder(666, new ComponentName(context.getPackageName(), ThemeResourceClearJobService.class.getName())).setPersisted(true).setRequiresDeviceIdle(true).setRequiresCharging(true).setPeriodic(36000000L).build());
        } catch (Exception unused) {
            Log.e("ThemeResourceClearJobSe", "scheduleNotifications failure");
        }
    }

    @Override // android.app.Service, android.content.ContextWrapper
    public void attachBaseContext(Context context) {
        super.attachBaseContext(context.createDeviceProtectedStorageContext());
    }

    @Override // android.app.job.JobService
    public boolean onStartJob(JobParameters jobParameters) {
        getHandler().post(new ClearTask(jobParameters));
        return true;
    }

    @Override // android.app.job.JobService
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
