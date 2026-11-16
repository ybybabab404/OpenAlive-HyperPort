package org.aliveclean;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 诊断采样通道（标记 T01、T03–T13；T02 是空号）。
 *
 * <p>两个开关任一为真就写日志：编译期的 {@link Diagnostics#TRACE}，或运行期的
 * {@code SharedPreferences("trace").enabled}。发布包两个都要关。
 * {@code enabled()} 首次判定后会缓存，改 pref 需重启进程或调 {@link #reset()} 才生效。</p>
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
