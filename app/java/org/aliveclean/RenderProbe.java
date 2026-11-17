package org.aliveclean;

import android.content.Context;
import android.provider.Settings;

/**
 * 帧耗时探针的开关：`Settings.System.alive_hs_probe` 为 1 时打开。
 *
 * <p>释放包里没人会去设置这个键，所以默认为关；关着的时候每帧只多一次布尔判断。
 * 用系统设置而不是 SharedPreferences，是为了能 `adb shell settings put system alive_hs_probe 1`
 * 现场打开、不用重启进程。判定结果缓存 1 秒，避免每帧一次 binder 调用。</p>
 */
final class RenderProbe {
    private static final String KEY = "alive_hs_probe";
    private static final long CACHE_MS = 1000L;
    private static long checkedAt;
    private static boolean enabled;

    private RenderProbe() {}

    static boolean enabled(Context context) {
        long now = android.os.SystemClock.uptimeMillis();
        if (now - checkedAt < CACHE_MS) return enabled;
        checkedAt = now;
        boolean value = false;
        if (context != null) {
            try {
                value = Settings.System.getInt(context.getContentResolver(), KEY, 0) == 1;
            } catch (Throwable ignored) {
                value = false;
            }
        }
        enabled = value;
        return value;
    }
}
