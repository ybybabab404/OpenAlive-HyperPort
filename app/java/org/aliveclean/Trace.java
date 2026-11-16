package org.aliveclean;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 诊断采样通道（T01..T13 等标记）。
 *
 * <p>只有 {@link Diagnostics#TRACE} 为 true 时才真正写日志；发布包保持关闭，
 * 否则等于把内部布局/时序细节暴露在 logcat 里。</p>
 */
final class Trace {
    static final String TAG = "AliveHSAod";
    private static final String PREFS = "trace";
    private static final String KEY = "enabled";

    private static volatile int state = -1;

    static void log(String id, String message) {
        if (!enabled()) return;
        AliveLog.i(TAG, "[HA][" + id + "] " + message);
    }

    static void log(String id, String message, Throwable error) {
        if (!enabled()) return;
        AliveLog.w(TAG, "[HA][" + id + "] " + message, error);
    }

    private static boolean enabled() {
        // 诊断构建（Diagnostics.TRACE=true）时强制打开：以前只看 SharedPreferences，
        // 导致"打开了 TRACE 却一条 T0x 都没有"（2026-11-15 踩过）。
        if (Diagnostics.TRACE) return true;
        int current = state;
        if (current >= 0) return current == 1;
        boolean on = false;
        try {
            Context context = Xp.currentApplication();
            if (context != null) {
                SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
                on = prefs.getBoolean(KEY, false);
            }
        } catch (Throwable ignored) {

        }
        state = on ? 1 : 0;
        return on;
    }

    static void reset() {
        state = -1;
    }

    private Trace() {
    }
}
