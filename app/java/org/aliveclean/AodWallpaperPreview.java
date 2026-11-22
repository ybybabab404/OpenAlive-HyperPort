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
    private static final AtomicInteger DIAG_CALLS = new AtomicInteger();
    private static final AtomicInteger UF_CALLS = new AtomicInteger();

    private AodWallpaperPreview() {}

    public static void install(ClassLoader loader) {
        // 编辑页（息屏与锁屏编辑）跑在 com.miui.aod:keyguardeditor，卡片壁纸同样是
        // GL 纹理，喂进去的也是占位图 —— 所以这套兜底必须在这个进程里也装一份。
        installImageGlWallpaperFallback(loader);
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
        installScreenshotsFallback(loader);
        installWallpaperHelper(loader);
        installImageGlWallpaperFallback(loader);
        // 进程刚加载时就装（诊断要抓卡片构建，装晚了抓不到），开关走 SystemProperties。
        if (cardDiagOn()) {
            DIAG_INSTALLED = true;
            installCardDiag(loader);
            installImageViewDiag(loader);
        }
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
                    if (!DIAG_INSTALLED && cardDiagOn()) {
                        DIAG_INSTALLED = true;
                        installCardDiag(param.method.getDeclaringClass().getClassLoader());
                        installImageViewDiag(param.method.getDeclaringClass().getClassLoader());
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

    /**
     * 系统当前桌面壁纸的位图，**跟着文件变化走**。
     *
     * <p>用户换壁纸时 MIUI 会重写 /data/system/theme_magic/.../home_wallpaper.jpg，
     * 所以这里用 mtime+size 做失效判断：文件一变就重新解码。若只用一份写死的缓存，
     * 换了壁纸卡片还显示旧图 —— 那正是"壁纸更新不了"。</p>
     */
    private static volatile long staticStamp = -1;
    private static volatile Bitmap staticBitmap;

    /** 桌面槽 / 锁屏槽：个性化页和编辑页都是两张卡，锁屏卡必须用锁屏那张图。 */
    private static int slotFromStack() {
        for (StackTraceElement element : Thread.currentThread().getStackTrace()) {
            String name = element.getClassName();
            // 只看**类名**：包名 com.miui.keyguard.editor.* 里两个槽都有代码，
            // 用包名判断会把桌面卡也认成锁屏卡（实测两张卡都变成了锁屏壁纸）。
            String simple = name.substring(name.lastIndexOf('.') + 1).toLowerCase();
            if (simple.isEmpty()) continue;
            // CombinedWallpaperView 同时服务两个槽，不能作为判据。
            if (simple.contains("combinedwallpaper")) continue;
            if (simple.contains("lock") || simple.contains("keyguard")) return 2;
            if (simple.contains("home") || simple.contains("desk")) return 1;
        }
        return 1;
    }

    private static Bitmap wallpaperForSlot(int slot) {
        File file = new File(STATIC_WALLPAPER_DIR, slot == 2 ? "lock_wallpaper.jpg" : "home_wallpaper.jpg");
        if (!file.isFile()) return systemWallpaperFresh();
        long stamp = file.lastModified() * 31L + file.length();
        if (slot == 2) {
            Bitmap cachedLock = lockBitmap;
            if (cachedLock != null && !cachedLock.isRecycled() && stamp == lockStamp) return cachedLock;
            Bitmap decoded = BitmapFactory.decodeFile(file.getAbsolutePath());
            if (decoded != null) { lockBitmap = decoded; lockStamp = stamp; }
            return decoded != null ? decoded : systemWallpaperFresh();
        }
        return systemWallpaperFresh();
    }

    private static volatile long lockStamp = -1;
    private static volatile Bitmap lockBitmap;

    private static Bitmap systemWallpaperFresh() {
        File file = new File(STATIC_WALLPAPER_DIR, "home_wallpaper.jpg");
        if (!file.isFile()) return null;
        long stamp = file.lastModified() * 31L + file.length();
        Bitmap cachedBitmap = staticBitmap;
        if (cachedBitmap != null && !cachedBitmap.isRecycled() && stamp == staticStamp) return cachedBitmap;
        try {
            Bitmap decoded = BitmapFactory.decodeFile(file.getAbsolutePath());
            if (decoded != null) {
                staticBitmap = decoded;
                staticStamp = stamp;
            }
            return decoded;
        } catch (Throwable error) {
            AliveLog.w(TAG, "static wallpaper decode failed", error);
            return null;
        }
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
        if (w < 16 || h < 16) return true;
        // 占位图是"灰底 + 图标网格"，特点是颜色极少（实测量化后有几百种以内），
        // 真照片/真壁纸在同样粒度下有上千种。用颜色数判，比"是否均匀"可靠。
        java.util.HashSet<Integer> colors = new java.util.HashSet<>();
        for (int y = 0; y < 48; y++) {
            for (int x = 0; x < 24; x++) {
                int pixel = bitmap.getPixel(w * x / 24, h * y / 48);
                colors.add((pixel & 0xff000000) | ((pixel >> 16 & 0xf8) << 16)
                        | ((pixel >> 8 & 0xf8) << 8) | (pixel & 0xf8) >> 3);
                if (colors.size() > 400) return false;
            }
        }
        return true;
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

    /**
     * 诊断：把个性化卡片取图相关的类里所有返 Bitmap 的方法都打一行。
     *
     * <p>为了回答"灰度占位图到底是谁给的"——前面已经证实 ni7 的返回值不被卡片采用、
     * toq 一次不触发，所以只能靠这个把真实调用点打出来，再针对那一处修。</p>
     */
    private static volatile Boolean cardDiag;
    private static volatile boolean DIAG_INSTALLED;

    /** 只用于排查：`su 0 setprop persist.alive.card_diag 1` 打开（进程启动即生效）。 */
    private static boolean cardDiagOn() {
        Boolean cached = cardDiag;
        if (cached != null) return cached;
        boolean on = false;
        try {
            // SystemProperties 是 @hide，SDK 里没有，只能反射。
            Class<?> properties = Class.forName("android.os.SystemProperties");
            Object value = properties.getMethod("get", String.class, String.class)
                    .invoke(null, "persist.alive.card_diag", "0");
            on = "1".equals(value);
        } catch (Throwable ignored) {
        }
        cardDiag = on;
        return on;
    }

    private static void installCardDiag(ClassLoader loader) {
        String[] classes = {
                "com.android.thememanager.settings.personalize.task.LockScreenWpTool",
                "com.android.thememanager.settings.personalize.task.DeskWpTool",
                "com.android.thememanager.settings.personalize.task.DeskPreviewTool",
                "com.miui.keyguard.editor.utils.Screenshots",
                "com.miui.keyguard.editor.utils.DeviceScreenshotHelper",
                "com.miui.keyguard.editor.utils.BitmapUtil",
        };
        for (String name : classes) {
            Class<?> target = Xp.findClassIfExists(name, loader);
            if (target == null) {
                AliveLog.w(TAG, "card diag missing " + name);
                continue;
            }
            final String simple = target.getSimpleName();
            for (java.lang.reflect.Method method : target.getDeclaredMethods()) {
                if (method.getReturnType() != Bitmap.class) continue;
                try {
                    Class<?>[] types = method.getParameterTypes();
                    Object[] args = new Object[types.length + 1];
                    System.arraycopy(types, 0, args, 0, types.length);
                    args[types.length] = new Xp.MethodHook() {
                        @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                            Bitmap result = param.getResult() instanceof Bitmap
                                    ? (Bitmap) param.getResult() : null;
                            int n = DIAG_CALLS.incrementAndGet();
                            if (n > 60) return;
                            AliveLog.i(TAG, "card diag " + simple + "." + param.method.getName()
                                    + " -> " + (result == null ? "null"
                                    : result.getWidth() + "x" + result.getHeight()
                                    + (looksLikePlaceholder(result) ? " placeholder" : " real")));
                        }
                    };
                    Xp.findAndHookMethod(target, method.getName(), args);
                } catch (Throwable error) {
                    AliveLog.w(TAG, "card diag hook failed " + simple + "." + method.getName(), error);
                }
            }
        }
    }

    /**
     * 个性化卡片的取图在 {@code com.miui.keyguard.editor.utils.Screenshots#p / #ld6}
     * （诊断日志里只有这两个方法在卡片渲染时被调用）。动态壁纸接管时它们返回的是
     * "灰底 + 图标网格"的占位图，卡片就整块变灰。这里只在**判定为占位**时换成
     * 用户自己那张桌面壁纸（缓存/系统静态壁纸），返回真图时一律不动。
     */
    private static void installScreenshotsFallback(ClassLoader loader) {
        Class<?> shots = Xp.findClassIfExists(
                "com.miui.keyguard.editor.utils.Screenshots", loader);
        if (shots == null) {
            AliveLog.w(TAG, "screenshots fallback: missing");
            return;
        }
        Xp.MethodHook hook = new Xp.MethodHook() {
            @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                Bitmap result = param.getResult() instanceof Bitmap ? (Bitmap) param.getResult() : null;
                if (result != null && !looksLikePlaceholder(result)) return;
                Context context = Xp.currentApplication();
                if (context == null) return;
                Bitmap remembered = rememberedHomeWallpaper(context);
                if (remembered == null) return;

                Bitmap replacement = result == null ? remembered
                        : scaleOrRecycle(remembered.copy(Bitmap.Config.ARGB_8888, false),
                                result.getWidth(), result.getHeight());
                param.setResult(replacement);
                AliveLog.i(TAG, "screenshots " + param.method.getName()
                        + " placeholder replaced by remembered wallpaper");
            }
        };
        for (String name : new String[]{"p", "ld6"}) {
            try {
                int count = Xp.hookAllMethods(shots, name, hook).size();
                AliveLog.i(TAG, "screenshots " + name + " hook count=" + count);
            } catch (Throwable error) {
                AliveLog.w(TAG, "screenshots " + name + " hook failed", error);
            }
        }
    }

    /**
     * 诊断：谁在给卡片的 ImageView 塞图。
     *
     * <p>Screenshots.p/ld6 的返回值实测不被卡片采用（换成纯红卡片纹丝不动），
     * 所以只能从"最终设置图片"这一端反查：hook ImageView.setImageBitmap /
     * setImageDrawable，打出身位与调用栈，直接定位真正的取图点。</p>
     */
    private static void installImageViewDiag(ClassLoader loader) {
        Xp.MethodHook bitmapHook = new Xp.MethodHook() {
            @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                if (param.args.length != 1 || !(param.args[0] instanceof Bitmap)) return;
                Bitmap bitmap = (Bitmap) param.args[0];
                int n = DIAG_CALLS.incrementAndGet();
                if (n > 40) return;
                AliveLog.i(TAG, "imageview setImageBitmap " + bitmap.getWidth() + "x" + bitmap.getHeight()
                        + (looksLikePlaceholder(bitmap) ? " placeholder" : " real") + " by "
                        + caller());
            }
        };
        Xp.MethodHook drawableHook = new Xp.MethodHook() {
            @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                if (param.args.length != 1 || !(param.args[0] instanceof android.graphics.drawable.BitmapDrawable))
                    return;
                Bitmap bitmap = ((android.graphics.drawable.BitmapDrawable) param.args[0]).getBitmap();
                int n = DIAG_CALLS.incrementAndGet();
                if (n > 40 || bitmap == null) return;
                AliveLog.i(TAG, "imageview setImageDrawable " + bitmap.getWidth() + "x" + bitmap.getHeight()
                        + (looksLikePlaceholder(bitmap) ? " placeholder" : " real") + " by "
                        + caller());
            }
        };
        try {
            Xp.findAndHookMethod(android.widget.ImageView.class, "setImageBitmap", Bitmap.class, bitmapHook);
            Xp.findAndHookMethod(android.widget.ImageView.class, "setImageDrawable",
                    android.graphics.drawable.Drawable.class, drawableHook);
            // 页面如果是 Compose 写的，图不会经过 ImageView，而是 asImageBitmap(Bitmap)。
            Class<?> asImage = Xp.findClassIfExists(
                    "androidx.compose.ui.graphics.AndroidImageBitmap_androidKt", loader);
            if (asImage == null) asImage = Xp.findClassIfExists(
                    "androidx.compose.ui.graphics.AndroidImageBitmap", loader);
            if (asImage != null) {
                for (java.lang.reflect.Method method : asImage.getDeclaredMethods()) {
                    if (!method.getName().contains("asImageBitmap")) continue;
                    Class<?>[] types = method.getParameterTypes();
                    Object[] args = new Object[types.length + 1];
                    System.arraycopy(types, 0, args, 0, types.length);
                    args[types.length] = new Xp.MethodHook() {
                        @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                            if (param.args.length == 0 || !(param.args[0] instanceof Bitmap)) return;
                            Bitmap bitmap = (Bitmap) param.args[0];
                            int n = DIAG_CALLS.incrementAndGet();
                            if (n > 40) return;
                            AliveLog.i(TAG, "compose asImageBitmap " + bitmap.getWidth() + "x"
                                    + bitmap.getHeight()
                                    + (looksLikePlaceholder(bitmap) ? " placeholder" : " real")
                                    + " by " + caller());
                        }
                    };
                    Xp.findAndHookMethod(asImage, method.getName(), args);
                    AliveLog.i(TAG, "compose image diag hooked " + method.getName());
                }
            } else {
                AliveLog.w(TAG, "compose image class missing");
            }
            // 卡片也可能是自绘 View：直接盯 Canvas.drawBitmap，按尺寸+占位特征过滤。
            for (String name : new String[]{"drawBitmap"}) {
                for (Class<?>[] signature : new Class<?>[][]{
                        {Bitmap.class, float.class, float.class, android.graphics.Paint.class},
                        {Bitmap.class, android.graphics.Rect.class, android.graphics.Rect.class,
                                android.graphics.Paint.class}}) {
                    Object[] args = new Object[signature.length + 1];
                    System.arraycopy(signature, 0, args, 0, signature.length);
                    args[signature.length] = new Xp.MethodHook() {
                        @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                            if (param.args.length == 0 || !(param.args[0] instanceof Bitmap)) return;
                            Bitmap bitmap = (Bitmap) param.args[0];
                            if (bitmap.getWidth() < 900) return;
                            if (!looksLikePlaceholder(bitmap)) return;
                            int n = DIAG_CALLS.incrementAndGet();
                            if (n > 20) return;
                            AliveLog.i(TAG, "canvas drawBitmap placeholder " + bitmap.getWidth()
                                    + "x" + bitmap.getHeight() + " by " + caller());
                        }
                    };
                    try {
                        Xp.findAndHookMethod(android.graphics.Canvas.class, name, args);
                    } catch (Throwable error) {
                        AliveLog.w(TAG, "canvas diag hook failed " + signature.length, error);
                    }
                }
            }
            // 宿主的卡片壁纸是自绘 GL 纹理（com.miui.miwallpaper.opengl.ImageGLWallpaper）。
            // 这里盯住"把 Bitmap 交给 GL"的两个入口，看它拿到的是什么图、谁给的。
            Class<?> imageGl = Xp.findClassIfExists(
                    "com.miui.miwallpaper.opengl.ImageGLWallpaper", loader);
            if (imageGl != null) {
                // 方法名因版本而异，按签名找"收 Bitmap 的方法"，全部挂上。
                for (java.lang.reflect.Method method : imageGl.getDeclaredMethods()) {
                    Class<?>[] types = method.getParameterTypes();
                    boolean takesBitmap = false;
                    for (Class<?> type : types) if (type == Bitmap.class) takesBitmap = true;
                    if (!takesBitmap) continue;
                    Object[] args = new Object[types.length + 1];
                    System.arraycopy(types, 0, args, 0, types.length);
                    args[types.length] = new Xp.MethodHook() {
                        @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                            for (Object arg : param.args) {
                                if (!(arg instanceof Bitmap)) continue;
                                Bitmap bitmap = (Bitmap) arg;
                                int n = DIAG_CALLS.incrementAndGet();
                                if (n > 30) return;
                                AliveLog.i(TAG, "imageGL " + param.method.getName() + " "
                                        + bitmap.getWidth() + "x" + bitmap.getHeight()
                                        + (looksLikePlaceholder(bitmap) ? " placeholder" : " real")
                                        + " by " + caller());
                            }
                        }
                    };
                    try {
                        Xp.findAndHookMethod(imageGl, method.getName(), args);
                    } catch (Throwable error) {
                        AliveLog.w(TAG, "imageGL hook failed " + method.getName(), error);
                    }
                }
                AliveLog.i(TAG, "imageGL diag installed");
            } else {
                AliveLog.w(TAG, "imageGL class missing");
            }
            // 卡片数据层：DeskWpTool.zy / LockScreenWpTool 的协程结果里到底有没有图。
            for (String name : new String[]{
                    "com.android.thememanager.settings.personalize.task.DeskWpTool",
                    "com.android.thememanager.settings.personalize.task.LockScreenWpTool"}) {
                Class<?> tool = Xp.findClassIfExists(name, loader);
                if (tool == null) continue;
                for (java.lang.reflect.Method method : tool.getDeclaredMethods()) {
                    Class<?>[] types = method.getParameterTypes();
                    Object[] args = new Object[types.length + 1];
                    System.arraycopy(types, 0, args, 0, types.length);
                    args[types.length] = new Xp.MethodHook() {
                        @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                            Object result = param.getResult();
                            if (result == null) {
                                AliveLog.i(TAG, "card data " + param.method.getName() + " -> null");
                                return;
                            }
                            StringBuilder fields = new StringBuilder();
                            for (java.lang.reflect.Field field : result.getClass().getDeclaredFields()) {
                                if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) continue;
                                try {
                                    field.setAccessible(true);
                                    Object value = field.get(result);
                                    fields.append(field.getName()).append('=')
                                            .append(value == null ? "null"
                                                    : (value instanceof Bitmap
                                                    ? ((Bitmap) value).getWidth() + "x"
                                                    + ((Bitmap) value).getHeight()
                                                    : value.getClass().getSimpleName()))
                                            .append(' ');
                                } catch (Throwable ignored) {
                                }
                            }
                            AliveLog.i(TAG, "card data " + param.method.getName() + " "
                                    + result.getClass().getSimpleName() + " { " + fields + "}");
                        }
                    };
                    try {
                        Xp.findAndHookMethod(tool, method.getName(), args);
                    } catch (Throwable error) {
                        AliveLog.w(TAG, "card data hook failed " + method.getName(), error);
                    }
                }
            }
            // 卡片的壁纸层是 CombinedWallpaperView 用 WallpaperTypeInfo.getBitmap() 建的；
            // 图为空时 wallpaperLayer 就是 null（宿主日志 startWallpaperLayerShowAnimation:
            // wallpaperLayer == null）。这里盯住这个数据对象的构造，看谁在造它、位图有没有。
            Class<?> typeInfo = Xp.findClassIfExists(
                    "com.miui.keyguard.editor.data.bean.WallpaperTypeInfo", loader);
            if (typeInfo != null) {
                Xp.hookAllConstructors(typeInfo, new Xp.MethodHook() {
                    @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                        Bitmap bitmap = null;
                        try {
                            Object value = Xp.getObjectField(param.thisObject, "bitmap");
                            if (value instanceof Bitmap) bitmap = (Bitmap) value;
                        } catch (Throwable ignored) {
                        }
                        int n = DIAG_CALLS.incrementAndGet();
                        if (n > 20) return;
                        AliveLog.i(TAG, "wallpaperTypeInfo bitmap="
                                + (bitmap == null ? "null" : bitmap.getWidth() + "x"
                                + bitmap.getHeight()
                                + (looksLikePlaceholder(bitmap) ? " placeholder" : " real"))
                                + " by " + caller());
                    }
                });
                AliveLog.i(TAG, "wallpaperTypeInfo diag installed");
            } else {
                AliveLog.w(TAG, "wallpaperTypeInfo class missing");
            }
            // 个性化页两张卡的数据来自 PersonalizeViewModel（两个 Bitmap 字段 + 一个
            // SuperWallpaperSummaryData 协程）。这里把它的方法全打一遍，看谁来喂图、喂了什么。
            Class<?> viewModel = Xp.findClassIfExists(
                    "com.android.thememanager.settings.personalize.presenter.PersonalizeViewModel", loader);
            if (viewModel != null) {
                for (java.lang.reflect.Method method : viewModel.getDeclaredMethods()) {
                    Class<?>[] types = method.getParameterTypes();
                    Object[] args = new Object[types.length + 1];
                    System.arraycopy(types, 0, args, 0, types.length);
                    args[types.length] = new Xp.MethodHook() {
                        @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                            int n = DIAG_CALLS.incrementAndGet();
                            if (n > 30) return;
                            StringBuilder text = new StringBuilder();
                            for (Object arg : param.args) {
                                if (arg instanceof Bitmap) {
                                    Bitmap bitmap = (Bitmap) arg;
                                    text.append("argBitmap=").append(bitmap.getWidth()).append('x')
                                            .append(bitmap.getHeight())
                                            .append(looksLikePlaceholder(bitmap) ? "(placeholder)" : "(real)")
                                            .append(' ');
                                }
                            }
                            Object result = param.getResult();
                            if (result instanceof Bitmap) {
                                Bitmap bitmap = (Bitmap) result;
                                text.append("resultBitmap=").append(bitmap.getWidth()).append('x')
                                        .append(bitmap.getHeight())
                                        .append(looksLikePlaceholder(bitmap) ? "(placeholder)" : "(real)");
                            } else if (result != null && result.getClass().isArray()) {
                                text.append("array=").append(java.lang.reflect.Array.getLength(result));
                            }
                            if (text.length() == 0) return;
                            AliveLog.i(TAG, "viewModel " + param.method.getName() + " " + text);
                        }
                    };
                    try {
                        Xp.findAndHookMethod(viewModel, method.getName(), args);
                    } catch (Throwable ignored) {
                    }
                }
                AliveLog.i(TAG, "viewModel diag installed");
            } else {
                AliveLog.w(TAG, "viewModel class missing");
            }
            AliveLog.i(TAG, "imageview diag installed");
        } catch (Throwable error) {
            AliveLog.w(TAG, "imageview diag failed", error);
        }
    }

    private static String caller() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        StringBuilder text = new StringBuilder();
        int shown = 0;
        for (StackTraceElement element : stack) {
            String name = element.getClassName();
            if (name.startsWith("org.aliveclean") || name.startsWith("de.robv")
                    || name.startsWith("android.widget.ImageView") || name.startsWith("java.lang.Thread")) continue;
            text.append(name).append(':').append(element.getLineNumber()).append(" <- ");
            if (++shown >= 6) break;
        }
        return text.toString();
    }

    /**
     * 个性化卡片的壁纸图由 {@code WallpaperHelper.y(mode, isLock, context)} 提供
     * （WallpaperCardView.getHomePreviewBitmap → 这个 → 拿不到就用 n() 的默认占位图）。
     *
     * <p>当前壁纸是第三方 live 壁纸时 MIUI 取不到家庭预览图，`y()` 返回 null，
     * 卡片于是画默认占位（灰底 + 图标网格）。这里只在拿不到/拿到占位时，补上
     * **用户自己那张桌面壁纸**（缓存或系统静态壁纸），拿到真图时一律不动。</p>
     */
    private static void installWallpaperHelper(ClassLoader loader) {
        Class<?> helper = Xp.findClassIfExists(
                "com.android.thememanager.basemodule.utils.WallpaperHelper", loader);
        if (helper == null) {
            AliveLog.w(TAG, "wallpaper helper: missing");
            return;
        }
        Xp.MethodHook hook = new Xp.MethodHook() {
            @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                Bitmap result = param.getResult() instanceof Bitmap ? (Bitmap) param.getResult() : null;
                if (result != null && !looksLikePlaceholder(result)) return;
                Context context = Xp.currentApplication();
                if (context == null) return;
                Bitmap remembered = rememberedHomeWallpaper(context);
                if (remembered == null) return;
                param.setResult(result == null ? remembered
                        : scaleOrRecycle(remembered.copy(Bitmap.Config.ARGB_8888, false),
                                result.getWidth(), result.getHeight()));
                AliveLog.i(TAG, "wallpaper helper " + param.method.getName()
                        + " filled with remembered wallpaper");
            }
        };
        try {
            int count = Xp.hookAllMethods(helper, "y", hook).size();
            AliveLog.i(TAG, "wallpaper helper y hook count=" + count);
        } catch (Throwable error) {
            AliveLog.w(TAG, "wallpaper helper hook failed", error);
        }
    }

    /**
     * 卡片壁纸真正的消费点：宿主的 {@code com.miui.miwallpaper.opengl.ImageGLWallpaper}
     * 由 {@code AnimImageGLProgram} 喂一张位图上去做纹理。当前壁纸是第三方 live 壁纸时，
     * 喂进去的是 **680x1509 的占位位图**（诊断日志实锤），于是卡片画出来就是灰底 + 图标网格。
     *
     * <p>这里在入参这一层把占位图换成用户自己那张桌面壁纸（缓存/系统静态壁纸），
     * 尺寸按宿主期望的缩放；喂进来的是真图时一律不动。</p>
     */
    private static void installImageGlWallpaperFallback(ClassLoader loader) {
        Class<?> imageGl = Xp.findClassIfExists(
                "com.miui.miwallpaper.opengl.ImageGLWallpaper", loader);
        if (imageGl == null) {
            AliveLog.w(TAG, "imageGL fallback: missing");
            return;
        }
        for (java.lang.reflect.Method method : imageGl.getDeclaredMethods()) {
            Class<?>[] types = method.getParameterTypes();
            boolean takesBitmap = false;
            for (Class<?> type : types) if (type == Bitmap.class) takesBitmap = true;
            if (!takesBitmap) continue;
            Object[] args = new Object[types.length + 1];
            System.arraycopy(types, 0, args, 0, types.length);
            args[types.length] = new Xp.MethodHook() {
                @Override protected void beforeHookedMethod(Xp.MethodHookParam param) {
                    Context context = Xp.currentApplication();
                    if (context == null) return;
                    for (int i = 0; i < param.args.length; i++) {
                        if (!(param.args[i] instanceof Bitmap)) continue;
                        Bitmap incoming = (Bitmap) param.args[i];
                        if (!looksLikePlaceholder(incoming)) continue;
                        Bitmap remembered = wallpaperForSlot(slotFromStack());
                        if (remembered == null) remembered = rememberedHomeWallpaper(context);
                        if (remembered == null) continue;
                        param.args[i] = Bitmap.createScaledBitmap(remembered,
                                incoming.getWidth(), incoming.getHeight(), true);
                        AliveLog.i(TAG, "imageGL " + param.method.getName()
                                + " placeholder fed with remembered wallpaper "
                                + incoming.getWidth() + "x" + incoming.getHeight());
                    }
                }
            };
            try {
                Xp.findAndHookMethod(imageGl, method.getName(), args);
            } catch (Throwable error) {
                AliveLog.w(TAG, "imageGL fallback hook failed " + method.getName(), error);
            }
        }
        AliveLog.i(TAG, "imageGL fallback installed");
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
                    if (!DIAG_INSTALLED && cardDiagOn()) {
                        DIAG_INSTALLED = true;
                        installCardDiag(param.method.getDeclaringClass().getClassLoader());
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
