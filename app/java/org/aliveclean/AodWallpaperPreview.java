package org.aliveclean;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public final class AodWallpaperPreview {
    private static final String TAG = "AliveHSHooks";
    private static final String TEMPLATE_DIR =
            "/data/user_de/0/com.miui.aod/files/templates/current/";

    private static Bitmap cached;
    private static long cachedStamp = -1;
    private static final AtomicInteger TM_CALLS = new AtomicInteger();
    private static final AtomicInteger SHOT_CALLS = new AtomicInteger();
    private static final AtomicInteger UF_CALLS = new AtomicInteger();

    private AodWallpaperPreview() {}

    public static void install(ClassLoader loader) {
        Class<?> api = Xp.findClassIfExists(
                "com.miui.keyguard.editor.data.template.TemplateApiImpl", loader);
        if (api == null) {
            AliveLog.w(TAG, "aod wallpaper preview: TemplateApiImpl missing");
            return;
        }
        try {
            Xp.MethodHook hook = new Xp.MethodHook() {
                @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                    Bitmap original = param.getResult() instanceof Bitmap
                            ? (Bitmap) param.getResult() : null;
                    if (original == null) return;
                    Context context = contextOf(param.thisObject);
                    if (context == null || !AodWallpaperMode.alive(context)) return;
                    Bitmap preview = preview(context);
                    if (preview == null) return;
                    param.setResult(preview);
                    AliveLog.i(TAG, "aod wallpaper preview replaced " + preview.getWidth()
                            + "x" + preview.getHeight());
                }
            };
            int count = Xp.hookAllMethods(api, "getPlaceholderWallpaper", hook).size();
            AliveLog.i(TAG, "aod wallpaper preview hook count=" + count);
        } catch (Throwable error) {
            AliveLog.w(TAG, "aod wallpaper preview hook failed", error);
        }
    }

    public static void installThemeManager(ClassLoader loader) {

        installUf(loader);
        installShots(loader);

        Class<?> helper = Xp.findClassIfExists(
                "com.miui.keyguard.editor.utils.DeviceScreenshotHelper", loader);
        if (helper == null) {
            AliveLog.w(TAG, "tm wallpaper preview: DeviceScreenshotHelper missing");
            return;
        }
        try {
            Xp.MethodHook hook = new Xp.MethodHook() {
                @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                    Bitmap original = param.getResult() instanceof Bitmap
                            ? (Bitmap) param.getResult() : null;
                    Context context = Xp.currentApplication();
                    boolean alive = false;
                    try {
                        alive = context != null && AodWallpaperMode.alive(context);
                    } catch (Throwable ignored) {
                    }
                    int n = TM_CALLS.incrementAndGet();
                    if (n <= 6) {
                        AliveLog.i(TAG, "tm ni7 call #" + n + " result="
                                + (original == null ? "null"
                                : original.getWidth() + "x" + original.getHeight())
                                + " alive=" + alive);
                    }
                    if (!alive || original == null) return;
                    Bitmap preview = preview(context);
                    if (preview == null) return;
                    Bitmap replacement = original.getWidth() == preview.getWidth()
                            && original.getHeight() == preview.getHeight()
                            ? preview
                            : Bitmap.createScaledBitmap(preview,
                                    original.getWidth(), original.getHeight(), true);
                    param.setResult(replacement);
                    AliveLog.i(TAG, "tm wallpaper preview replaced " + replacement.getWidth()
                            + "x" + replacement.getHeight());
                }
            };

            int count = Xp.hookAllMethods(helper, "ni7", hook).size();
            if (count == 0) {
                count = Xp.hookAllMethods(helper,
                        "getCurrentHomeWallpaperScreenshot", hook).size();
            }
            AliveLog.i(TAG, "tm wallpaper preview hook count=" + count);
        } catch (Throwable error) {
            AliveLog.w(TAG, "tm wallpaper preview hook failed", error);
        }
    }

    private static void installUf(ClassLoader loader) {
        Class<?> api = Xp.findClassIfExists(
                "com.miui.keyguard.editor.data.template.TemplateApiImpl", loader);
        if (api == null) {
            AliveLog.w(TAG, "tm uf: TemplateApiImpl missing");
            return;
        }
        try {
            Xp.MethodHook hook = new Xp.MethodHook() {
                @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                    Bitmap original = param.getResult() instanceof Bitmap
                            ? (Bitmap) param.getResult() : null;
                    if (original == null || param.args.length != 0) return;
                    Context context = Xp.currentApplication();
                    boolean alive = false;
                    try {
                        alive = context != null && AodWallpaperMode.alive(context);
                    } catch (Throwable ignored) {
                    }
                    int n = UF_CALLS.incrementAndGet();
                    if (n <= 6) {
                        AliveLog.i(TAG, "tm uf call #" + n + " result="
                                + original.getWidth() + "x" + original.getHeight()
                                + " alive=" + alive);
                    }
                    if (!alive) return;
                    Bitmap preview = preview(context);
                    if (preview == null) return;
                    Bitmap replacement = Bitmap.createScaledBitmap(preview,
                            original.getWidth(), original.getHeight(), true);
                    param.setResult(replacement);
                    if (n <= 6) {
                        AliveLog.i(TAG, "tm uf replaced " + replacement.getWidth()
                                + "x" + replacement.getHeight());
                    }
                }
            };
            int count = Xp.hookAllMethods(api, "uf", hook).size();
            AliveLog.i(TAG, "tm uf hook count=" + count);
        } catch (Throwable error) {
            AliveLog.w(TAG, "tm uf hook failed", error);
        }
    }

    private static void installShots(ClassLoader loader) {
        Class<?> shots = Xp.findClassIfExists(
                "com.miui.keyguard.editor.utils.Screenshots", loader);
        if (shots == null) {
            AliveLog.w(TAG, "tm shots: Screenshots missing");
            return;
        }
        try {
            Xp.MethodHook hook = new Xp.MethodHook() {
                @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                    Bitmap original = param.getResult() instanceof Bitmap
                            ? (Bitmap) param.getResult() : null;
                    Context context = Xp.currentApplication();
                    boolean alive = false;
                    try {
                        alive = context != null && AodWallpaperMode.alive(context);
                    } catch (Throwable ignored) {
                    }
                    int n = SHOT_CALLS.incrementAndGet();
                    if (n <= 6) {
                        AliveLog.i(TAG, "tm shots t8r call #" + n + " result="
                                + (original == null ? "null"
                                : original.getWidth() + "x" + original.getHeight())
                                + " alive=" + alive);
                    }
                    if (!alive) return;
                    Bitmap preview = preview(context);
                    if (preview == null) return;
                    Bitmap replacement = original == null ? preview
                            : Bitmap.createScaledBitmap(preview,
                                    original.getWidth(), original.getHeight(), true);
                    param.setResult(replacement);
                    if (n <= 6) {
                        AliveLog.i(TAG, "tm shots t8r replaced " + replacement.getWidth()
                                + "x" + replacement.getHeight());
                    }
                }
            };
            int count = Xp.hookAllMethods(shots, "t8r", hook).size();
            AliveLog.i(TAG, "tm shots t8r hook count=" + count);
        } catch (Throwable error) {
            AliveLog.w(TAG, "tm shots hook failed", error);
        }
    }

    private static Context contextOf(Object api) {
        try {
            Object value = Xp.getObjectField(api, "context");
            return value instanceof Context ? (Context) value : null;
        } catch (Throwable ignored) {
            return null;
        }
    }

    private static Bitmap fromProvider(Context context) {
        if (context == null) return null;
        ParcelFileDescriptor pfd = null;
        try {
            Uri uri = Uri.parse("content://miui.keyguard.editor.templatefileprovider"
                    + "/template_current/wallpaper_home");
            pfd = context.getContentResolver().openFileDescriptor(uri, "r");
            if (pfd == null) return null;
            return BitmapFactory.decodeFileDescriptor(pfd.getFileDescriptor());
        } catch (Throwable error) {
            AliveLog.w(TAG, "aod wallpaper preview provider failed", error);
            return null;
        } finally {
            if (pfd != null) {
                try { pfd.close(); } catch (IOException ignored) { }
            }
        }
    }
    private static synchronized Bitmap preview(Context context) {
        Bitmap provider = fromProvider(context);
        if (provider != null) return provider;
        File file = new File(TEMPLATE_DIR, "wallpaper_home");
        if (!file.isFile()) file = new File(TEMPLATE_DIR, "wallpaper");
        if (!file.isFile()) return null;
        long stamp = file.lastModified() * 31L + file.length();
        if (cached != null && cachedStamp == stamp && !cached.isRecycled()) return cached;
        try {
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            if (bitmap != null) {
                cached = bitmap;
                cachedStamp = stamp;
            }
            return bitmap;
        } catch (Throwable error) {
            AliveLog.w(TAG, "aod wallpaper preview decode failed", error);
            return null;
        }
    }
}
