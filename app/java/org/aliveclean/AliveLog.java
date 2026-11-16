package org.aliveclean;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 模块统一的日志出口，并支持 {@code once} 把高频路径压成一条。
 *
 * <p>标签约定（并非统一前缀）：hook 侧 {@code AliveHS*}/{@code AliveHSAod}/{@code AliveHSHooks}/
 * {@code AliveHSDepth}，宿主进程（App 与壁纸）{@code AliveClean}，独立子模块
 * {@code OpenAliveXiaomi}/{@code OpenAliveModule}。抓日志时按这些前缀一起过滤。</p>
 *
 * <p>只在有排查价值的低频事件上打日志；每帧打印会把 logcat 里真正有用的行冲掉
 * （2026-11-16 就因为探针每帧一行，导致现场关键日志全部丢失）。</p>
 */
final class AliveLog {
    static final String TAG = "AliveHS";
    static final String APPLY = "AliveHS/Apply";
    private static final int LIMIT = 2000;
    private static final Set<String> seen = Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>());

    private AliveLog() {
    }

    static void i(String tag, String message) {
        write(4, tag, message, null);
    }

    static void w(String tag, String message) {
        write(5, tag, message, null);
    }

    static void w(String tag, String message, Throwable error) {
        write(5, tag, message, error);
    }

    static void e(String tag, String message) {
        write(6, tag, message, null);
    }

    static void e(String tag, String message, Throwable error) {
        write(6, tag, message, error);
    }

    static void once(String tag, String key, String message) {
        if (seen.add(key)) write(4, tag, message, null);
    }

    static void warnOnce(String tag, String key, String message, Throwable error) {
        if (seen.add(key)) write(5, tag, message, error);
    }

    static void reset(String key) {
        seen.remove(key);
    }

    static String describe(Throwable error) {
        if (error == null) return "none";
        String message = error.getMessage();
        return message == null ? error.getClass().getName() : error.getClass().getSimpleName() + ": " + message;
    }

    private static void write(int level, String tag, String message, Throwable error) {
        String text = message == null ? "" : message;
        if (text.length() > LIMIT) text = text.substring(0, LIMIT) + "...";
        try {
            android.util.Log.println(level, tag, error == null ? text : text + "\n" + android.util.Log.getStackTraceString(error));
        } catch (Throwable ignored) {
        }
        Xp.log(level, tag, text, error);
    }
}
