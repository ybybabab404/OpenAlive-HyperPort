package org.aliveclean;

/** Silent logger for offline preview-hook tests. */
final class AliveLog {
    private AliveLog() {}

    static void i(String tag, String message) {}
    static void w(String tag, String message) {}
    static void w(String tag, String message, Throwable error) {}
    static void e(String tag, String message) {}
    static void e(String tag, String message, Throwable error) {}
    static void once(String tag, String key, String message) {}
    static void reset(String key) {}
}
