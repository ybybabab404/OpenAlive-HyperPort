package com.miui.keyguard.editor.utils;

import android.app.Activity;
import android.app.Application;
import android.app.KeyguardManager;
import android.app.WallpaperColors;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import androidx.core.content.ContextCompat;
import com.miui.keyguard.editor.EditorApplicationProxy;
import com.miui.keyguard.editor.data.bean.WallpaperWhich;
import com.miui.keyguard.editor.edit.wallpaper.WallpaperController;
import com.miui.keyguard.editor.homepage.model.CrossListPreloader;
import com.miui.keyguard.editor.utils.segment.ImageSegmentCache;
import com.miui.miwallpaper.IMiuiWallpaperManagerCallback;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: ProcessManager.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class ProcessManager {
    public static Application app;
    public static boolean isInForeground;
    public static boolean isLaunchingEditorFromLockScreen;
    public static boolean isListenScreenState;
    public static KeyguardManager keyguardManager;
    public static final ProcessManager INSTANCE = new ProcessManager();
    public static List allEditorActivity = new ArrayList();
    public static AtomicInteger activityCount = new AtomicInteger(0);
    public static volatile boolean isScreenOn = true;
    public static final ProcessKillScheduler killScheduler = new ProcessKillScheduler("Keyguard-Editor-ProcessManager", 120000, new Function0() { // from class: com.miui.keyguard.editor.utils.ProcessManager$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Boolean.valueOf(ProcessManager.killScheduler$lambda$0());
        }
    }, new Function0() { // from class: com.miui.keyguard.editor.utils.ProcessManager$$ExternalSyntheticLambda1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return ProcessManager.killScheduler$lambda$1();
        }
    });
    public static final ProcessManager$screenStateReceiver$1 screenStateReceiver = new BroadcastReceiver() { // from class: com.miui.keyguard.editor.utils.ProcessManager$screenStateReceiver$1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(intent, "intent");
            if (Intrinsics.areEqual(intent.getAction(), "android.intent.action.SCREEN_ON")) {
                ProcessManager.isScreenOn = true;
                return;
            }
            if (Intrinsics.areEqual(intent.getAction(), "android.intent.action.SCREEN_OFF")) {
                ProcessManager.isScreenOn = false;
                KeyguardManager keyguardManager2 = ProcessManager.keyguardManager;
                Boolean boolValueOf = keyguardManager2 != null ? Boolean.valueOf(keyguardManager2.isKeyguardLocked()) : null;
                Log.i("Keyguard-Editor-ProcessManager", "screen off, isKeyguardLocked = " + boolValueOf + ", isLaunchingEditorFromLockScreen=" + ProcessManager.isLaunchingEditorFromLockScreen + " , isInForeground=" + ProcessManager.isInForeground);
                KeyguardManager keyguardManager3 = ProcessManager.keyguardManager;
                if (keyguardManager3 == null || !keyguardManager3.isKeyguardLocked() || ProcessManager.isLaunchingEditorFromLockScreen || ProcessManager.isInForeground) {
                    return;
                }
                ProcessManager.INSTANCE.finishAllActivity("screen off");
            }
        }
    };
    public static final ProcessManager$wallpaperChangeBroadcastReceiver$1 wallpaperChangeBroadcastReceiver = new BroadcastReceiver() { // from class: com.miui.keyguard.editor.utils.ProcessManager$wallpaperChangeBroadcastReceiver$1
        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            CrossListPreloader crossListPreloader;
            if (!Intrinsics.areEqual("android.intent.action.WALLPAPER_CHANGED", intent != null ? intent.getAction() : null) || (crossListPreloader = EditorApplicationProxy.Companion.getCrossListPreloader()) == null) {
                return;
            }
            crossListPreloader.invalidData("wallpaper changed from broadcast");
        }
    };
    public static final ProcessManager$wallpaperChangedCallBack$1 wallpaperChangedCallBack = new IMiuiWallpaperManagerCallback.Stub() { // from class: com.miui.keyguard.editor.utils.ProcessManager$wallpaperChangedCallBack$1
        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
        public void onDrawFrameEnd() {
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
        public void onPartColorComputeComplete(Map map, Map map2, int i) {
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
        public void onWallpaperChanged(WallpaperColors wallpaperColors, String str, int i) {
            CrossListPreloader crossListPreloader = EditorApplicationProxy.Companion.getCrossListPreloader();
            if (crossListPreloader != null) {
                crossListPreloader.invalidData("wallpaper changed from callback");
            }
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
        public void onWallpaperFirstFrameRendered(int i) {
        }
    };

    public static final boolean killScheduler$lambda$0() {
        return INSTANCE.isShouldSuicide() && activityCount.get() <= 0;
    }

    public static final Unit killScheduler$lambda$1() {
        ImageSegmentCache.Companion.clearCache(app);
        INSTANCE.onEditorApplicationDestroy();
        return Unit.INSTANCE;
    }

    public final void finishAllActivity(String tag) {
        Activity activity;
        Intrinsics.checkNotNullParameter(tag, "tag");
        for (WeakReference weakReference : allEditorActivity) {
            Activity activity2 = (Activity) weakReference.get();
            Boolean boolValueOf = activity2 != null ? Boolean.valueOf(activity2.isFinishing()) : null;
            Log.d("Keyguard-Editor-ProcessManager", "finishAllActivity: tag=" + tag + " " + boolValueOf + " " + weakReference.get());
            Activity activity3 = (Activity) weakReference.get();
            if (activity3 != null && !activity3.isFinishing() && (activity = (Activity) weakReference.get()) != null) {
                activity.finish();
            }
        }
        boolean zIsActivityPinned = CommonUtils.INSTANCE.isActivityPinned(app);
        Log.d("Keyguard-Editor-ProcessManager", "activityPinned=" + zIsActivityPinned);
        if (zIsActivityPinned) {
            return;
        }
        allEditorActivity.clear();
    }

    public final void interrupt() {
        killScheduler.cancel();
        if (activityCount.get() <= 0) {
            submitSuicide();
        }
    }

    public final boolean isShouldSuicide() {
        if (!isScreenOn) {
            Log.i("Keyguard-Editor-ProcessManager", "shouldSuicide because screenOff");
            return true;
        }
        KeyguardManager keyguardManager2 = keyguardManager;
        boolean z = !(keyguardManager2 != null ? keyguardManager2.isKeyguardLocked() : false);
        Log.i("Keyguard-Editor-ProcessManager", "isShouldSuicide " + z);
        return z;
    }

    public final void killProcess() {
        if (killScheduler.isBackupOrRestoreInProgress()) {
            Log.i("Keyguard-Editor-ProcessManager", "killProcess: backup in progress, skip");
            return;
        }
        Log.i("Keyguard-Editor-ProcessManager", "killProcess pid = " + android.os.Process.myPid() + " name = " + Application.getProcessName());
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public final void onEditorApplicationCreate(Application application) {
        Intrinsics.checkNotNullParameter(application, "application");
        app = application;
        submitSuicide();
        ContextCompat.registerReceiver(application, wallpaperChangeBroadcastReceiver, new IntentFilter("android.intent.action.WALLPAPER_CHANGED"), 2);
        WallpaperController.Companion.getInstance(application, false).registerWallpaperChangedListener(wallpaperChangedCallBack, WallpaperWhich.AllWhich.INSTANCE.getWhich());
    }

    public final void onEditorApplicationDestroy() {
        Application application = app;
        if (application != null) {
            application.unregisterReceiver(wallpaperChangeBroadcastReceiver);
            WallpaperController.Companion.getInstance(application, false).unregisterWallpaperChangedListener(wallpaperChangedCallBack);
        }
    }

    public final void onEditorCreate() {
        if (activityCount.get() < 0) {
            activityCount.set(0);
        }
        Log.i("Keyguard-Editor-ProcessManager", "onEditorCreate activityCount=" + activityCount.incrementAndGet());
        killScheduler.cancel();
    }

    public final void onEditorDestroy() {
        int iDecrementAndGet = activityCount.decrementAndGet();
        Log.i("Keyguard-Editor-ProcessManager", "onEditorDestroy activityCount=" + iDecrementAndGet);
        if (iDecrementAndGet <= 0) {
            submitSuicide();
        }
    }

    public final void recordNewActivity(Activity activity) {
        Log.d("Keyguard-Editor-ProcessManager", "recordNewActivity: activity=" + activity);
        allEditorActivity.add(new WeakReference(activity));
    }

    public final void registerActivityCallback(Application application) {
        Intrinsics.checkNotNullParameter(application, "application");
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() { // from class: com.miui.keyguard.editor.utils.ProcessManager.registerActivityCallback.1
            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityCreated(Activity activity, Bundle bundle) {
                Intrinsics.checkNotNullParameter(activity, "activity");
                ProcessManager.INSTANCE.recordNewActivity(activity);
                ProcessManager.isInForeground = true;
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityDestroyed(Activity activity) {
                Intrinsics.checkNotNullParameter(activity, "activity");
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityPaused(Activity activity) {
                Intrinsics.checkNotNullParameter(activity, "activity");
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityResumed(Activity activity) {
                Intrinsics.checkNotNullParameter(activity, "activity");
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
                Intrinsics.checkNotNullParameter(activity, "activity");
                Intrinsics.checkNotNullParameter(outState, "outState");
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStarted(Activity activity) {
                Intrinsics.checkNotNullParameter(activity, "activity");
            }

            @Override // android.app.Application.ActivityLifecycleCallbacks
            public void onActivityStopped(Activity activity) {
                Intrinsics.checkNotNullParameter(activity, "activity");
                Log.d("Keyguard-Editor-ProcessManager", "onActivityStopped: activity=" + activity);
                ProcessManager.isInForeground = false;
            }
        });
    }

    public final void setLaunchingEditorFromLockScreen(boolean z) {
        Log.d("Keyguard-Editor-ProcessManager", " isLaunching=" + z);
        isLaunchingEditorFromLockScreen = z;
    }

    public final void startListenScreenState(Application context) {
        Intrinsics.checkNotNullParameter(context, "context");
        keyguardManager = (KeyguardManager) ContextCompat.getSystemService(context, KeyguardManager.class);
        if (isListenScreenState) {
            return;
        }
        Log.i("Keyguard-Editor-ProcessManager", "startListenScreenState");
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        context.registerReceiver(screenStateReceiver, intentFilter);
        isListenScreenState = true;
    }

    public final void submitSuicide() {
        Process process = Process.INSTANCE;
        if (process.isKeyguardeditorProcess() || process.isAodProcess()) {
            killScheduler.schedule();
        }
    }
}
