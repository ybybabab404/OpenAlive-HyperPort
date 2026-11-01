package com.miui.aod.doze;

import android.app.AlarmManager;
import android.content.Context;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.Looper;
import com.miui.aod.DozeLifecycleOwner;
import com.miui.aod.Utils;
import com.miui.aod.common.AsyncSensorManager;
import com.miui.aod.util.LooperExecutor;
import com.miui.aod.util.wakelock.DelayedWakeLock;
import com.miui.aod.util.wakelock.WakeLock;
import java.util.concurrent.ExecutorService;

/* JADX INFO: loaded from: classes.dex */
public final class DozeFactory {
    public static SensorManager sSensorManager;

    public static DozeMachine assembleMachine(DozeService dozeService, Context context) {
        AsyncSensorManager asyncSensorManager = AsyncSensorManager.getInstance(getSensorManager(context));
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(AlarmManager.class);
        LooperExecutor looperExecutor = new LooperExecutor(Looper.getMainLooper());
        Handler handler = looperExecutor.getHandler();
        DelayedWakeLock delayedWakeLock = new DelayedWakeLock(handler, WakeLock.wrap(context, "Doze"));
        ExecutorService backgroundExecutor = Utils.getBackgroundExecutor();
        MiuiFullAodAdapter miuiFullAodAdapter = new MiuiFullAodAdapter(new MiuiDozeBrightnessTimeoutAdapter(context, MiuiAnimDozeStatePreventingAdapter.wrapIfNeeded(MiuiGxzwDozeStatePreventingAdapter.wrapIfNeeded(dozeService, context, backgroundExecutor), context, backgroundExecutor), handler, backgroundExecutor, alarmManager), backgroundExecutor);
        DozeLifecycleOwner dozeLifecycleOwner = DozeLifecycleOwner.getInstance();
        DozeMachine dozeMachine = new DozeMachine(miuiFullAodAdapter, delayedWakeLock, context);
        MiuiShowStyleController miuiShowStyleController = new MiuiShowStyleController(context, handler, dozeMachine, alarmManager, miuiFullAodAdapter, delayedWakeLock);
        DozePauser dozePauser = new DozePauser(handler, dozeMachine, alarmManager);
        dozeMachine.setParts(new DozeMachine.Part[]{new MiuiAodService(context, miuiFullAodAdapter), dozePauser, new DozeTriggers(context, dozeMachine, looperExecutor, true, miuiFullAodAdapter, miuiShowStyleController), new DozeWallpaperState(context), new DozeUi(context, alarmManager, dozeMachine, handler, miuiFullAodAdapter), new MiuiFocusNotification2(context), new DozeScreenState(context, miuiFullAodAdapter, alarmManager, handler, delayedWakeLock), new MiuiDozeScreenBrightnessController(handler, dozeMachine, alarmManager, miuiFullAodAdapter, dozePauser, asyncSensorManager, context), miuiShowStyleController, new MiuiBgController(context, handler, alarmManager, miuiFullAodAdapter), dozeLifecycleOwner});
        dozeLifecycleOwner.initState();
        return dozeMachine;
    }

    public static SensorManager getSensorManager(Context context) {
        if (sSensorManager == null) {
            sSensorManager = (SensorManager) context.getSystemService(SensorManager.class);
        }
        return sSensorManager;
    }
}
