package org.aliveclean;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;

/**
 * UI 进程的空闲自退。
 *
 * <p>壁纸服务已经拆到 {@code :wallpaper} 独立进程（见 AndroidManifest），所以
 * {@code org.aliveclean} 这个进程只在用户打开界面时才有存在意义。可是系统对空进程
 * 只会"在别处缺内存时"才杀——桌面挂着的时候它就一直占着七八十兆（类加载、dex/oat、
 * native 堆、静态缓存都在里面）。这不是"没用的占用"，但确实是没人用的占用。</p>
 *
 * <p>做法：所有 Activity 都不可见之后延迟 {@link #IDLE_MS} 秒仍然如此，就自己退出。
 * 延迟是给"从一个页面跳到另一个页面""弹出选择框"这类短暂过渡留余量；
 * 有正在进行的应用壁纸任务时让路，避免把 {@code su} 子进程等一半的操作掐断。</p>
 *
 * <p>退出后桌面壁纸不受任何影响——那是另一个进程。</p>
 */
public final class AliveApp extends Application implements Application.ActivityLifecycleCallbacks {
    private static final long IDLE_MS = 15_000L;
    private static final Handler HANDLER = new Handler(Looper.getMainLooper());
    private int started;

    /** 不能在字段初始化器里引用它自己（Java 的 self-reference 限制），所以放到 onCreate 里赋。 */
    private Runnable idleExit;

    @Override public void onCreate() {
        super.onCreate();
        idleExit = () -> {
            if (started > 0) return;
            if (SceneApply.applying || XiaomiApply.applying) {
                // 应用还在跑：等它结束再说，别把用户刚点的"应用"掐在中间。
                HANDLER.postDelayed(idleExit, IDLE_MS);
                return;
            }
            AliveLog.i("AliveClean", "ui idle " + IDLE_MS + "ms: exit process to release ui memory");
            Process.killProcess(Process.myPid());
        };
        registerActivityLifecycleCallbacks(this);
    }

    @Override public void onActivityStarted(Activity activity) {
        started++;
        HANDLER.removeCallbacks(idleExit);
    }

    @Override public void onActivityStopped(Activity activity) {
        if (--started <= 0) {
            started = 0;
            HANDLER.removeCallbacks(idleExit);
            HANDLER.postDelayed(idleExit, IDLE_MS);
        }
    }

    // 下面这几个回调这里用不上：退出判定只看"有没有 Activity 可见"，
    // created/resumed/paused/destroyed 不改变 started 计数。
    @Override public void onActivityCreated(Activity activity, Bundle state) {}
    @Override public void onActivityResumed(Activity activity) {}
    @Override public void onActivityPaused(Activity activity) {}
    @Override public void onActivitySaveInstanceState(Activity activity, Bundle state) {}
    @Override public void onActivityDestroyed(Activity activity) {}
}
