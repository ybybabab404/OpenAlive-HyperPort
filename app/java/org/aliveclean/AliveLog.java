package org.aliveclean;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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
