package org.aliveclean;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

final class Trace {
    static final String TAG = "AliveHSAod";
    private static final String PREFS = "trace";
    private static final String KEY = "enabled";

    private static volatile int state = -1;

    static void log(String id, String message) {
        if (!enabled()) return;
        Log.i(TAG, "[HA][" + id + "] " + message);
    }

    static void log(String id, String message, Throwable error) {
        if (!enabled()) return;
        Log.w(TAG, "[HA][" + id + "] " + message, error);
    }

    private static boolean enabled() {
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
