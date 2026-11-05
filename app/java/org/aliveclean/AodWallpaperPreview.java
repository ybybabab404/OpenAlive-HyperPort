package org.aliveclean;

import android.app.AndroidAppHelper;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * 系统个性化页的「桌面」预览卡在第三方活壁纸下没有可用位图：
 * - com.miui.aod 的 TemplateApiImpl.getCurrentWallpaper() 会落到
 *   R.drawable.kg_wallpaper_invalid_placeholder（灰底 + 破图图标），
 *   随后被存成 templates/current/small_wallpaper_screenshot；
 * - com.android.thememanager 的 TemplateApiImpl.p() 第三方分支直接返回 uf()，
 *   同样是这张占位图，这就是卡片上看到的灰底图标块。
 *
 * 这里只替换这些兜底返回值：仅当本模块壁纸正在使用时，改用 AOD 自己已经落盘的
 * wallpaper_home（当前首页壁纸的原图）作为预览；其它任何情况都保持系统原行为。
 */
public final class AodWallpaperPreview {
    private static final String TAG = "HyperAliveHooks";
    private static final String TEMPLATE_DIR =
            "/data/user_de/0/com.miui.aod/files/templates/current/";

    private static Bitmap cached;
    private static long cachedStamp = -1;
    private static final AtomicInteger TM_CALLS = new AtomicInteger();
    private static final AtomicInteger SHOT_CALLS = new AtomicInteger();
    private static final AtomicInteger UF_CALLS = new AtomicInteger();

    private AodWallpaperPreview() {}

    public static void install(ClassLoader loader) {
        Class<?> api = XposedHelpers.findClassIfExists(
                "com.miui.keyguard.editor.data.template.TemplateApiImpl", loader);
        if (api == null) {
            Log.w(TAG, "aod wallpaper preview: TemplateApiImpl missing");
            return;
        }
        try {
            XC_MethodHook hook = new XC_MethodHook() {
                @Override protected void afterHookedMethod(MethodHookParam param) {
                    Bitmap original = param.getResult() instanceof Bitmap
                            ? (Bitmap) param.getResult() : null;
                    if (original == null) return;
                    Context context = contextOf(param.thisObject);
                    if (context == null || !AodWallpaperMode.alive(context)) return;
                    Bitmap preview = preview(context);
                    if (preview == null) return;
                    param.setResult(preview);
                    Log.i(TAG, "aod wallpaper preview replaced " + preview.getWidth()
                            + "x" + preview.getHeight());
                }
            };
            int count = XposedBridge.hookAllMethods(api, "getPlaceholderWallpaper", hook).size();
            Log.i(TAG, "aod wallpaper preview hook count=" + count);
        } catch (Throwable error) {
            Log.w(TAG, "aod wallpaper preview hook failed", error);
        }
    }

    /**
     * 主题管理器进程里，桌面预览卡不走 TemplateApiImpl.getPlaceholderWallpaper，
     * 而是由 DeviceScreenshotHelper 截首页模板的壁纸层。主题管理器里的方法名被混淆成 ni7，
     * 第三方活壁纸的层里没有位图，截出来就是灰底/空图。这里只替换这一个方法的返回值。
     */
    public static void installThemeManager(ClassLoader loader) {
        // 桌面卡走 TemplateApiImpl.uf()，与 DeviceScreenshotHelper 是否存在无关，先装。
        installUf(loader);
        installShots(loader);

        Class<?> helper = XposedHelpers.findClassIfExists(
                "com.miui.keyguard.editor.utils.DeviceScreenshotHelper", loader);
        if (helper == null) {
            Log.w(TAG, "tm wallpaper preview: DeviceScreenshotHelper missing");
            return;
        }
        try {
            XC_MethodHook hook = new XC_MethodHook() {
                @Override protected void afterHookedMethod(MethodHookParam param) {
                    Bitmap original = param.getResult() instanceof Bitmap
                            ? (Bitmap) param.getResult() : null;
                    Context context = AndroidAppHelper.currentApplication();
                    boolean alive = false;
                    try {
                        alive = context != null && AodWallpaperMode.alive(context);
                    } catch (Throwable ignored) {
                    }
                    int n = TM_CALLS.incrementAndGet();
                    if (n <= 6) {
                        Log.i(TAG, "tm ni7 call #" + n + " result="
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
                    Log.i(TAG, "tm wallpaper preview replaced " + replacement.getWidth()
                            + "x" + replacement.getHeight());
                }
            };
            // 主题管理器里是混淆名 ni7；保留可读名做兼容，免得换版本后静默失效。
            int count = XposedBridge.hookAllMethods(helper, "ni7", hook).size();
            if (count == 0) {
                count = XposedBridge.hookAllMethods(helper,
                        "getCurrentHomeWallpaperScreenshot", hook).size();
            }
            Log.i(TAG, "tm wallpaper preview hook count=" + count);
        } catch (Throwable error) {
            Log.w(TAG, "tm wallpaper preview hook failed", error);
        }
    }

    /**
     * 主题管理器「桌面」卡真正取图的是 TemplateApiImpl.p() 的第三方分支：
     * 它不读壁纸本身，直接返回 uf()（kg_wallpaper_invalid_placeholder，灰底+图标剪影）。
     * 这里只替换 uf() 的返回值，其它调用路径保持系统原样。
     */
    private static void installUf(ClassLoader loader) {
        Class<?> api = XposedHelpers.findClassIfExists(
                "com.miui.keyguard.editor.data.template.TemplateApiImpl", loader);
        if (api == null) {
            Log.w(TAG, "tm uf: TemplateApiImpl missing");
            return;
        }
        try {
            XC_MethodHook hook = new XC_MethodHook() {
                @Override protected void afterHookedMethod(MethodHookParam param) {
                    Bitmap original = param.getResult() instanceof Bitmap
                            ? (Bitmap) param.getResult() : null;
                    if (original == null || param.args.length != 0) return;
                    Context context = AndroidAppHelper.currentApplication();
                    boolean alive = false;
                    try {
                        alive = context != null && AodWallpaperMode.alive(context);
                    } catch (Throwable ignored) {
                    }
                    int n = UF_CALLS.incrementAndGet();
                    if (n <= 6) {
                        Log.i(TAG, "tm uf call #" + n + " result="
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
                        Log.i(TAG, "tm uf replaced " + replacement.getWidth()
                                + "x" + replacement.getHeight());
                    }
                }
            };
            int count = XposedBridge.hookAllMethods(api, "uf", hook).size();
            Log.i(TAG, "tm uf hook count=" + count);
        } catch (Throwable error) {
            Log.w(TAG, "tm uf hook failed", error);
        }
    }

    /**
     * 最后一道保险：DeviceScreenshotHelper.zp() 被混淆得无法反编译，
     * 看不清 smallWallpaper 是在哪一步塞进 Screenshots 的。直接在
     * Screenshots.t8r()（smallWallpaper 的 getter）返回时替换，
     * 保证 takeCaptureForMultiScreenMode 拿到的就是真实预览图。
     */
    private static void installShots(ClassLoader loader) {
        Class<?> shots = XposedHelpers.findClassIfExists(
                "com.miui.keyguard.editor.utils.Screenshots", loader);
        if (shots == null) {
            Log.w(TAG, "tm shots: Screenshots missing");
            return;
        }
        try {
            XC_MethodHook hook = new XC_MethodHook() {
                @Override protected void afterHookedMethod(MethodHookParam param) {
                    Bitmap original = param.getResult() instanceof Bitmap
                            ? (Bitmap) param.getResult() : null;
                    Context context = AndroidAppHelper.currentApplication();
                    boolean alive = false;
                    try {
                        alive = context != null && AodWallpaperMode.alive(context);
                    } catch (Throwable ignored) {
                    }
                    int n = SHOT_CALLS.incrementAndGet();
                    if (n <= 6) {
                        Log.i(TAG, "tm shots t8r call #" + n + " result="
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
                        Log.i(TAG, "tm shots t8r replaced " + replacement.getWidth()
                                + "x" + replacement.getHeight());
                    }
                }
            };
            int count = XposedBridge.hookAllMethods(shots, "t8r", hook).size();
            Log.i(TAG, "tm shots t8r hook count=" + count);
        } catch (Throwable error) {
            Log.w(TAG, "tm shots hook failed", error);
        }
    }

    private static Context contextOf(Object api) {
        try {
            Object value = XposedHelpers.getObjectField(api, "context");
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
            Log.w(TAG, "aod wallpaper preview provider failed", error);
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
            Log.w(TAG, "aod wallpaper preview decode failed", error);
            return null;
        }
    }
}


