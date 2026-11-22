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
                    // 这个方法是"当前桌面壁纸的截图"，个性化页的锁屏卡/桌面卡都用它。
                    // 当前壁纸换成我们的动态壁纸后宿主截不到图，只能给灰白占位
                    // （实测 1200x2608 纯灰），卡片就整块变灰。所以：
                    //   · 拿到真图时先缓存一份 —— 那就是用户自己设置的那张壁纸；
                    //   · 只拿到占位时回吐这份缓存，卡片始终是用户那张壁纸，
                    //     既不会变灰，也不会被换成"当前样式的壁纸"。
                    if (original != null && !looksLikePlaceholder(original)) {
                        rememberHomeWallpaper(context, original);
                        return;
                    }
                    Bitmap remembered = rememberedHomeWallpaper(context);
                    if (remembered == null) return;   // 没有历史快照时不猜，保持宿主原样
                    param.setResult(remembered);
                    AliveLog.i(TAG, "tm ni7 placeholder replaced by remembered wallpaper "
                            + remembered.getWidth() + "x" + remembered.getHeight());
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
     * 个性化页的「桌面壁纸卡」在宿主取不到静态图时会画灰占位。
     *
     * <p>原因：卡片要的是"当前壁纸的静态帧"，而当前壁纸是我们的动态壁纸
     * （CleanWallpaper），宿主的 ImageWallpaper 取图路径对它失效，结果返回 null。
     * 之前这里是把卡片强行换成"当前样式的壁纸"，那是错的——用户要的是卡片原来那张
     * 壁纸。所以现在只在**宿主自己返回 null**（灰占位）时，补系统里那张真正的静态壁纸
     * 图（用户设置的桌面/锁屏壁纸），宿主能取到图时一律不动它。</p>
     */
    private static void installPersonalizeTasks(ClassLoader loader) {
        hookPersonalizeNull(loader,
                "com.android.thememanager.settings.personalize.task.DeskWpTool", 0, "desk", 1);
        hookPersonalizeNull(loader,
                "com.android.thememanager.settings.personalize.task.LockScreenWpTool", 0, "lock", 2);
    }

    /** 系统里那张静态壁纸（用户自己设置的），which: 1 桌面 / 2 锁屏。 */
    private static final String STATIC_WALLPAPER_DIR = "/data/system/theme_magic/users/0/wallpaper/image/";

    private static Bitmap systemStaticWallpaper(int which) {
        File file = new File(STATIC_WALLPAPER_DIR, which == 2 ? "lock_wallpaper.jpg" : "home_wallpaper.jpg");
        if (!file.isFile()) return null;
        try {
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        } catch (Throwable error) {
            AliveLog.w(TAG, "static wallpaper decode failed " + file, error);
            return null;
        }
    }

    /**
     * 宿主画不出来时给的是它自己的灰白占位图（整块纯灰 + 图标网格），
     * 采样若干点判断是否接近均匀灰。真照片/真壁纸不可能满足这条。
     */
    private static boolean looksLikePlaceholder(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) return true;
        int w = bitmap.getWidth(), h = bitmap.getHeight();
        if (w < 8 || h < 8) return true;
        int min = 255, max = 0, sum = 0, count = 0;
        for (int y = 1; y <= 8; y++) {
            for (int x = 1; x <= 8; x++) {
                int pixel = bitmap.getPixel(w * x / 9, h * y / 9);
                int r = (pixel >> 16) & 0xff, g = (pixel >> 8) & 0xff, b = pixel & 0xff;
                int value = (r + g + b) / 3;
                min = Math.min(min, value);
                max = Math.max(max, value);
                sum += value;
                count++;
            }
        }
        int average = sum / count;
        // 占位图有三种：灰底(0x86)、白卡片(0xF5)和纯黑；共同点是采样点几乎没变化。
        return max - min < 24 && average > 90;
    }

    private static void hookPersonalizeNull(ClassLoader loader, String className,
                                            final int argCount, final String label, final int which) {
        Class<?> task = Xp.findClassIfExists(className, loader);
        if (task == null) {
            AliveLog.w(TAG, "tm " + label + " card: " + className + " missing");
            return;
        }
        try {
            Xp.MethodHook hook = new Xp.MethodHook() {
                @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                    if (param.args.length != argCount) return;
                    // 宿主自己画得出真图时一律不动；它画的"灰占位"是一张真实位图
                    // （纯灰网格），所以只判 null 不够，还要看内容。
                    if (param.getResult() instanceof Bitmap
                            && !looksLikePlaceholder((Bitmap) param.getResult())) return;
                    if (param.getResult() != null && !(param.getResult() instanceof Bitmap)) return;
                    Bitmap bitmap = systemStaticWallpaper(which);
                    if (bitmap == null) return;
                    param.setResult(bitmap);
                    AliveLog.i(TAG, "tm " + label + " card grey fixed by static wallpaper");
                }
            };
            int count = Xp.hookAllMethods(task, "toq", hook).size();
            AliveLog.i(TAG, "tm " + label + " card hook count=" + count);
        } catch (Throwable error) {
            AliveLog.w(TAG, "tm " + label + " card hook failed", error);
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

    /** 缓存用户那张"真正的桌面壁纸"（宿主能截到图的时刻顺手存一份）。 */
    private static void rememberHomeWallpaper(Context context, Bitmap bitmap) {
        File file = new File(context.getFilesDir(), "home_wallpaper_cache.jpg");
        if (file.isFile() && System.currentTimeMillis() - file.lastModified() < 60_000L) return;
        try (java.io.FileOutputStream out = new java.io.FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 92, out);
        } catch (Throwable error) {
            AliveLog.w(TAG, "remember home wallpaper failed", error);
        }
    }

    private static Bitmap rememberedHomeWallpaper(Context context) {
        File file = new File(context.getFilesDir(), "home_wallpaper_cache.jpg");
        if (!file.isFile()) {
            // 还没缓存过：用系统里那张真实的静态桌面壁纸当底（用户自己设的那张）。
            // 绝不能拿"当前样式的壁纸"凑数。
            Bitmap seed = systemStaticWallpaper(1);
            if (seed != null) rememberHomeWallpaper(context, seed);
            return seed;
        }
        try {
            return BitmapFactory.decodeFile(file.getAbsolutePath());
        } catch (Throwable error) {
            AliveLog.w(TAG, "remembered home wallpaper decode failed", error);
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
