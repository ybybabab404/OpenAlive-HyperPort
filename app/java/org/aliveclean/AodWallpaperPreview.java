package org.aliveclean;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 把系统侧（ThemeManager / 个性化 / UF 系列页面）的 AOD 壁纸缩略图与预览图
 * 换成我们用同一份场景参数实时渲染出来的图，避免"预览与真机不一致"。
 */
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
                    Context context = contextOf(param.thisObject);
                    if (context == null || !AodWallpaperMode.alive(context)) return;
                    Bitmap preview = forHook(context);
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
        installPersonalizeTasks(loader);

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
                    if (!alive) return;
                    Bitmap preview = forHook(context);
                    if (preview == null) return;
                    Bitmap replacement = original == null ? preview
                            : (original.getWidth() == preview.getWidth()
                            && original.getHeight() == preview.getHeight()
                            ? preview
                            : scaleOrRecycle(preview, original.getWidth(), original.getHeight()));
                    param.setResult(replacement);
                    AliveLog.i(TAG, "tm ni7 replaced " + replacement.getWidth()
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

    /**
     * PersonalizeActivity loads its lock/desktop cards through these two helpers,
     * not through TemplateApiImpl.  A live system wallpaper has no static bitmap
     * there, so both return null and the host paints its grey placeholder.  Fall
     * back to the current MIUI AOD template only while our wallpaper is active.
     */
    private static void installPersonalizeTasks(ClassLoader loader) {
        hookPersonalizeTask(loader,
                "com.android.thememanager.settings.personalize.task.LockScreenWpTool",
                0, "lock");
        hookPersonalizeTask(loader,
                "com.android.thememanager.settings.personalize.task.DeskWpTool",
                1, "desk");
    }

    private static void hookPersonalizeTask(ClassLoader loader, String className,
                                             final int argCount, final String label) {
        Class<?> task = Xp.findClassIfExists(className, loader);
        if (task == null) {
            AliveLog.w(TAG, "tm " + label + " preview: " + className + " missing");
            return;
        }
        try {
            Xp.MethodHook hook = new Xp.MethodHook() {
                @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                    if (param.args.length != argCount) return;
                    Bitmap original = param.getResult() instanceof Bitmap
                            ? (Bitmap) param.getResult() : null;
                    Context context = Xp.currentApplication();
                    boolean alive = false;
                    try {
                        alive = context != null && AodWallpaperMode.alive(context);
                    } catch (Throwable ignored) {
                    }
                    if (!alive) return;
                    Bitmap preview = forHook(context);
                    if (preview == null) return;
                    Bitmap replacement = original == null ? preview
                            : (original.getWidth() == preview.getWidth()
                            && original.getHeight() == preview.getHeight() ? preview
                            : scaleOrRecycle(preview, original.getWidth(), original.getHeight()));
                    param.setResult(replacement);
                    AliveLog.i(TAG, "tm " + label + " preview replaced "
                            + replacement.getWidth() + "x" + replacement.getHeight());
                }
            };
            int count = Xp.hookAllMethods(task, "toq", hook).size();
            AliveLog.i(TAG, "tm " + label + " preview hook count=" + count);
        } catch (Throwable error) {
            AliveLog.w(TAG, "tm " + label + " preview hook failed", error);
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
                    if (param.args.length != 0) return;
                    Context context = Xp.currentApplication();
                    boolean alive = false;
                    try {
                        alive = context != null && AodWallpaperMode.alive(context);
                    } catch (Throwable ignored) {
                    }
                    int n = UF_CALLS.incrementAndGet();
                    if (n <= 6) {
                        AliveLog.i(TAG, "tm uf call #" + n + " result="
                                + (original == null ? "null"
                                : original.getWidth() + "x" + original.getHeight())
                                + " alive=" + alive);
                    }
                    if (!alive) return;
                    Bitmap preview = forHook(context);
                    if (preview == null) return;
                    Bitmap replacement = original == null ? preview
                            : scaleOrRecycle(preview, original.getWidth(), original.getHeight());
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
                    Bitmap preview = forHook(context);
                    if (preview == null) return;
                    Bitmap replacement = original == null ? preview
                            : scaleOrRecycle(preview, original.getWidth(), original.getHeight());
                    param.setResult(replacement);
                    try {
                        Xp.setField(param.thisObject, "f53958zy", replacement);
                    } catch (Throwable error) {
                        AliveLog.w(TAG, "tm shots field write failed", error);
                    }
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
    /**
     * The host may recycle whatever bitmap we return (BitmapUtil.fu4 calls
     * recycle()).  The static cached template is shared by several hooks, so a
     * recycled copy must never escape.  Fresh provider/file bitmaps are already
     * owned by this call; only the cached instance needs a defensive copy.
     */
    private static Bitmap scaleOrRecycle(Bitmap preview, int width, int height) {
        if (preview == null) return null;
        if (preview.getWidth() == width && preview.getHeight() == height) return preview;
        Bitmap scaled = Bitmap.createScaledBitmap(preview, width, height, true);
        if (scaled != preview) preview.recycle();
        return scaled;
    }

    private static Bitmap forHook(Context context) {
        Bitmap bitmap = preview(context);
        if (bitmap == null) return null;
        if (bitmap == cached) {
            try {
                return bitmap.copy(Bitmap.Config.ARGB_8888, false);
            } catch (Throwable error) {
                AliveLog.w(TAG, "aod wallpaper preview copy failed", error);
                return null;
            }
        }
        return bitmap;
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
