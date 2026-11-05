package org.aliveclean;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * One switch for every diagnostic line in the module.
 *
 * Rules:
 *  - every call site carries a stable id ("T07"); docs/port/TRACE.md maps id -> file:line -> claim
 *  - the message goes out as "[HA][T07] ..." so `adb logcat -s HyperAliveAod | grep '\[HA\]'` shows the story
 *  - when trace is off every call is a no-op, so release builds keep the call sites and pay nothing
 *  - never log bitmaps, file contents or user data here
 */
final class Trace {
    static final String TAG = "HyperAliveAod";
    private static final String PREFS = "trace";
    private static final String KEY = "enabled";

    private static volatile int state = -1; // -1 unknown, 0 off, 1 on

    static void log(String id, String message) {
        if (!enabled()) return;
        Log.i(TAG, "[HA][" + id + "] " + message);
    }

    static void log(String id, String message, Throwable error) {
        if (!enabled()) return;
        Log.w(TAG, "[HA][" + id + "] " + message, error);
    }

    /** Reads the flag once; a missing preference means "on" — this is a debug build flavour. */
    private static boolean enabled() {
        int current = state;
        if (current >= 0) return current == 1;
        boolean on = true;
        try {
            Context context = Xp.currentApplication();
            if (context != null) {
                SharedPreferences prefs = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
                on = prefs.getBoolean(KEY, true);
            }
        } catch (Throwable ignored) {
            // AndroidAppHelper is only present inside a hooked process; stay on when it is absent.
        }
        state = on ? 1 : 0;
        return on;
    }

    /** Lets the app's settings screen flip tracing without a restart of the host process. */
    static void reset() {
        state = -1;
    }

    private Trace() {
    }
}
