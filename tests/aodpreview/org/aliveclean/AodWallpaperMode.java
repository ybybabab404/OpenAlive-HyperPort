package org.aliveclean;

import android.content.Context;

/** Wallpaper ownership double; each test sets alive directly. */
final class AodWallpaperMode {
    static boolean alive;

    private AodWallpaperMode() {}

    static boolean alive(Context context) { return alive; }
}
