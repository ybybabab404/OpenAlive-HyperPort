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
    private static final AtomicInteger TYPE_INFO_CALLS = new AtomicInteger();
    private static final AtomicInteger PROVIDER_CALLS = new AtomicInteger();

    private AodWallpaperPreview() {}

    public static void install(ClassLoader loader) {
        // 编辑页（息屏与锁屏编辑）跑在 com.miui.aod:keyguardeditor，卡片壁纸同样是
        // GL 纹理，喂进去的也是占位图 —— 所以这套兜底必须在这个进程里也装一份。
        installImageGlWallpaperFallback(loader);
        installWallpaperTypeInfoFallback(loader);
        installWallpaperLayerFallback(loader);
        installEditorWallpaperFallback(loader);
        installEditorCurrentWallpaperFallback();
        installEditorProviderFallback(loader);
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
        // 第一优先：我们自己记下的"真正被宿主当成真图喂进来的那一帧"。
        Bitmap remembered = rememberedSlotWallpaper(slot);
        if (remembered != null && !looksLikePlaceholder(remembered)) return remembered;
        Bitmap fromSystem = decodeSlotFile(slot);
        if (fromSystem != null && !looksLikePlaceholder(fromSystem)) return fromSystem;
        // 锁屏那张缩略图可能已被 MIUI 改成占位图，真图留在 currentWallpaper 里。
        if (slot == 2) {
            Bitmap origin = lockOriginWallpaper();
            if (origin != null && !looksLikePlaceholder(origin)) return origin;
        }
        // 最后才用进程里的缓存（可能是任意槽的图）。
        return null;
    }

    /** 解码某个槽的系统静态壁纸文件；文件里可能就是占位图，交给调用方判内容。 */
    private static Bitmap decodeSlotFile(int slot) {
        File file = new File(STATIC_WALLPAPER_DIR, slot == 2 ? "lock_wallpaper.jpg" : "home_wallpaper.jpg");
        if (!file.isFile()) return null;
        long stamp = file.lastModified() * 31L + file.length();
        if (slot == 2) {
            Bitmap cached = lockBitmap;
            if (cached != null && !cached.isRecycled() && stamp == lockStamp) return cached;
            Bitmap decoded = BitmapFactory.decodeFile(file.getAbsolutePath());
            if (decoded != null) { lockBitmap = decoded; lockStamp = stamp; }
            return decoded;
        }
        return systemWallpaperFresh();
    }

    /** 每个槽"最近一次见到真图"的缓存（files/card_wallpaper_1|2.jpg）。 */
    private static Bitmap rememberedSlotWallpaper(int slot) {
        File file = slotCacheFile(slot);
        if (!file.isFile()) return null;
        long stamp = file.lastModified() * 31L + file.length();
        if (slot == 2 ? stamp == lockRememberStamp : stamp == homeRememberStamp) {
            Bitmap cached = slot == 2 ? lockRemembered : homeRemembered;
            if (cached != null && !cached.isRecycled()) return cached;
        }
        Bitmap decoded = BitmapFactory.decodeFile(file.getAbsolutePath());
        if (decoded == null) return null;
        if (slot == 2) { lockRemembered = decoded; lockRememberStamp = stamp; }
        else { homeRemembered = decoded; homeRememberStamp = stamp; }
        return decoded;
    }

    private static File slotCacheFile(int slot) {
        Context context = Xp.currentApplication();
        if (context == null) return new File("/data/local/tmp/alive_slot_" + slot + ".jpg");
        return new File(context.getFilesDir(), "card_wallpaper_" + slot + ".jpg");
    }

    /** 宿主喂进来的如果是真图，就把它记下来——下次它再给占位图时我们才有真图可用。 */
    private static void rememberSlotWallpaper(int slot, Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled() || looksLikePlaceholder(bitmap)) return;
        if (slot == 2) { lockRemembered = bitmap; lockRememberStamp = -1; }
        else { homeRemembered = bitmap; homeRememberStamp = -1; }
        final Bitmap copy = bitmap.copy(Bitmap.Config.ARGB_8888, false);
        final File file = slotCacheFile(slot);
        new Thread(() -> {
            try (java.io.FileOutputStream out = new java.io.FileOutputStream(file)) {
                copy.compress(Bitmap.CompressFormat.JPEG, 92, out);
            } catch (Throwable error) {
                AliveLog.w(TAG, "remember slot wallpaper failed slot=" + slot, error);
            }
        }, "alive-remember-wallpaper").start();
    }

    private static volatile long homeRememberStamp = -1;
    private static volatile long lockRememberStamp = -1;
    private static volatile Bitmap homeRemembered;
    private static volatile Bitmap lockRemembered;

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
     * 宿主画不出来时给的是它自己的占位图（灰底 + 一个图片图标）；真壁纸不可能是这个样子。
     *
     * <p>判据用"采样网格上的量化颜色数"。门槛取 60：实测占位图只有 4 种颜色，
     * 而大面积纯色的风景壁纸（雪山那张）采样后也有 284 种，两者差得很远，
     * 所以既不会把真壁纸误判成占位，也不会把占位放过去。</p>
     */
    private static boolean looksLikePlaceholder(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) return true;
        int w = bitmap.getWidth(), h = bitmap.getHeight();
        if (w < 16 || h < 16) return true;
        java.util.HashSet<Integer> colors = new java.util.HashSet<>();
        for (int y = 0; y < 96; y++) {
            for (int x = 0; x < 48; x++) {
                int pixel = bitmap.getPixel(w * x / 48, h * y / 96);
                colors.add((pixel & 0xff000000) | ((pixel >> 16 & 0xf8) << 16)
                        | ((pixel >> 8 & 0xf8) << 8) | ((pixel & 0xf8) >> 3));
                if (colors.size() > 60) return false;
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
                        int slot = slotFromStack();
                        if (!looksLikePlaceholder(incoming)) {
                            rememberSlotWallpaper(slot, incoming);
                            continue;
                        }
                        Bitmap remembered = wallpaperForSlot(slot);
                        if (remembered == null) remembered = rememberedHomeWallpaper(context);
                        if (remembered == null || looksLikePlaceholder(remembered)) continue;
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

    /**
     * 编辑页（息屏与锁屏编辑）的卡片壁纸来自 {@code WallpaperTypeInfo.getBitmap()}：
     * CombinedWallpaperView 拿它去建 GLTextureView，图是空的就只画出空态（灰占位）。
     *
     * <p>当前壁纸是第三方动态壁纸时宿主取不到那张图，位图就是 null/占位，
     * 这里在数据对象构造完的那一刻补上对应槽位的真实壁纸。</p>
     */
    private static void installWallpaperTypeInfoFallback(ClassLoader loader) {
        Class<?> typeInfo = Xp.findClassIfExists(
                "com.miui.keyguard.editor.data.bean.WallpaperTypeInfo", loader);
        if (typeInfo == null) {
            AliveLog.w(TAG, "wallpaperTypeInfo fallback: missing");
            return;
        }
        try {
            Xp.hookAllConstructors(typeInfo, new Xp.MethodHook() {
                @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                    Object info = param.thisObject;
                    Bitmap current = null;
                    try {
                        Object value = Xp.getObjectField(info, "bitmap");
                        if (value instanceof Bitmap) current = (Bitmap) value;
                    } catch (Throwable ignored) {
                    }
                    if (current != null && !looksLikePlaceholder(current)) {
                        if (TYPE_INFO_CALLS.incrementAndGet() <= 8) {
                            AliveLog.i(TAG, "wallpaperTypeInfo real bitmap "
                                    + current.getWidth() + "x" + current.getHeight()
                                    + " slot=" + slotOfTypeInfo(info));
                        }
                        return;
                    }
                    int slot = slotOfTypeInfo(info);
                    Bitmap fill = wallpaperForSlot(slot);
                    if (fill == null || looksLikePlaceholder(fill)) {
                        if (TYPE_INFO_CALLS.incrementAndGet() <= 8) {
                            AliveLog.i(TAG, "wallpaperTypeInfo no fill slot=" + slot
                                    + " current=" + (current == null ? "null"
                                    : current.getWidth() + "x" + current.getHeight()));
                        }
                        return;
                    }
                    try {
                        java.lang.reflect.Field field = typeInfo.getDeclaredField("bitmap");
                        field.setAccessible(true);
                        field.set(info, current == null ? fill
                                : Bitmap.createScaledBitmap(fill, current.getWidth(),
                                current.getHeight(), true));
                        AliveLog.i(TAG, "wallpaperTypeInfo filled slot=" + slot);
                    } catch (Throwable error) {
                        AliveLog.w(TAG, "wallpaperTypeInfo fill failed", error);
                    }
                }
            });
            AliveLog.i(TAG, "wallpaperTypeInfo fallback installed");
        } catch (Throwable error) {
            AliveLog.w(TAG, "wallpaperTypeInfo fallback hook failed", error);
        }
    }

    /** infoType 里带 HOME/LOCK 语义，用它判槽位；判不出就退回栈判定。 */
    private static int slotOfTypeInfo(Object info) {
        try {
            Object type = Xp.getObjectField(info, "infoType");
            if (type != null) {
                String name = String.valueOf(type).toUpperCase(java.util.Locale.ROOT);
                if (name.contains("LOCK")) return 2;
                if (name.contains("HOME")) return 1;
            }
        } catch (Throwable ignored) {
        }
        return slotFromStack();
    }

    /**
     * 编辑页的壁纸层由 {@code IWallpaperLayer.Companion.q/f7l8(...Bitmap...)} 建出来
     * （CombinedWallpaperView 调它们拿 GLTextureView）。位图为空/占位时图层就是空的，
     * 卡片只剩空态底图（灰占位）。这里在入参上补对应槽位的真实壁纸。
     */
    private static void installWallpaperLayerFallback(ClassLoader loader) {
        String[] classes = {
                "com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer$Companion",
                "com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer",
        };
        int hooks = 0;
        for (String name : classes) {
            Class<?> type = Xp.findClassIfExists(name, loader);
            if (type == null) continue;
            for (java.lang.reflect.Method method : type.getDeclaredMethods()) {
                // 接口上的方法体是 abstract，hook 它只会抛 IllegalArgumentException 刷屏，
                // 真正的实现在 GLTextureView 里；这里只挂 Companion 上能拦到的方法。
                if (java.lang.reflect.Modifier.isAbstract(method.getModifiers())) continue;
                Class<?>[] types = method.getParameterTypes();
                boolean takesBitmap = false;
                for (Class<?> each : types) if (each == Bitmap.class) takesBitmap = true;
                if (!takesBitmap) continue;
                Object[] args = new Object[types.length + 1];
                System.arraycopy(types, 0, args, 0, types.length);
                args[types.length] = new Xp.MethodHook() {
                    @Override protected void beforeHookedMethod(Xp.MethodHookParam param) {
                        int slot = slotFromStack();
                        for (int i = 0; i < param.args.length; i++) {
                            if (!(param.args[i] instanceof Bitmap)) continue;
                            Bitmap incoming = (Bitmap) param.args[i];
                            if (!looksLikePlaceholder(incoming)) continue;
                            Bitmap fill = wallpaperForSlot(slot);
                            if (fill == null || looksLikePlaceholder(fill)) continue;
                            param.args[i] = Bitmap.createScaledBitmap(fill,
                                    incoming.getWidth(), incoming.getHeight(), true);
                            AliveLog.i(TAG, "wallpaperLayer " + param.method.getName()
                                    + " filled slot=" + slot);
                        }
                    }
                };
                try {
                    Xp.findAndHookMethod(type, method.getName(), args);
                    hooks++;
                } catch (Throwable error) {
                    AliveLog.w(TAG, "wallpaperLayer hook failed " + method.getName(), error);
                }
            }
        }
        AliveLog.i(TAG, "wallpaperLayer fallback hooks=" + hooks);
    }

    /**
     * 编辑页（息屏与锁屏编辑）的卡片壁纸最终来自
     * {@code MiuiWallpaperManager.v(which)} —— 它向 miwallpaper 服务要该槽位的预览图。
     *
     * <p>当前壁纸换成我们的第三方动态壁纸后，MIUI 生成不出静态预览，于是把一张
     * "灰底 + 图片图标"的占位图写进
     * {@code /data/system/theme_magic/users/0/wallpaper/image/lock_wallpaper.jpg}，
     * 这个接口拿到的就是它，编辑页卡片于是整块变灰（实测该文件内容就是这张占位图）。
     * 所以只在这一层判内容：拿到真图一律不动，拿到占位/空才补上用户自己那张壁纸。</p>
     */
    private static void installEditorWallpaperFallback(ClassLoader loader) {
        Class<?> manager = Xp.findClassIfExists(
                "com.miui.miwallpaper.MiuiWallpaperManager", loader);
        if (manager == null) {
            AliveLog.w(TAG, "editor wallpaper: MiuiWallpaperManager missing");
            return;
        }
        int hooks = 0;
        for (java.lang.reflect.Method method : manager.getDeclaredMethods()) {
            if (method.getReturnType() != Bitmap.class) continue;
            Class<?>[] types = method.getParameterTypes();
            boolean hasWhich = false;
            boolean supported = true;
            for (Class<?> type : types) {
                if (type == int.class) hasWhich = true;
                // 取预览图这条路上只有 which / 宽高 / Context；收流或别的对象的不是它。
                else if (type != Context.class) supported = false;
            }
            if (!hasWhich || !supported) continue;
            Object[] args = new Object[types.length + 1];
            System.arraycopy(types, 0, args, 0, types.length);
            args[types.length] = new Xp.MethodHook() {
                @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                    Bitmap result = param.getResult() instanceof Bitmap
                            ? (Bitmap) param.getResult() : null;
                    if (result != null && !looksLikePlaceholder(result)) return;
                    int slot = slotOfWhichArgs(param.args);
                    Bitmap fill = editorRealWallpaper(slot);
                    if (fill == null) return;
                    param.setResult(result == null ? fill
                            : Bitmap.createScaledBitmap(fill, result.getWidth(),
                            result.getHeight(), true));
                    AliveLog.i(TAG, "editor wallpaper " + param.method.getName()
                            + " placeholder replaced slot=" + slot);
                }
            };
            try {
                Xp.findAndHookMethod(manager, method.getName(), args);
                hooks++;
            } catch (Throwable error) {
                AliveLog.w(TAG, "editor wallpaper hook failed " + method.getName(), error);
            }
        }
        AliveLog.i(TAG, "editor wallpaper fallback hooks=" + hooks);
    }

    /** MIUI 的 which：1/4/5 桌面（含小桌面、折叠屏桌面），2/6/10 锁屏。 */
    private static int slotOfWhichArgs(Object[] args) {
        for (Object arg : args) {
            if (!(arg instanceof Integer)) continue;
            int value = (Integer) arg;
            if (value == 2 || value == 6 || value == 10) return 2;
            if (value == 1 || value == 4 || value == 5) return 1;
        }
        return slotFromStack();
    }

    /**
     * 编辑页用的真壁纸。锁屏那张要特别注意：MIUI 写进
     * {@code image/lock_wallpaper.jpg} 的可能就是占位图，真正那张锁屏壁纸被留在
     * {@code currentWallpaper/wallpaper_lock_origin}，所以它才是锁屏槽的最后兜底。
     */
    private static Bitmap editorRealWallpaper(int slot) {
        Bitmap fromSlot = wallpaperForSlot(slot);
        if (fromSlot != null && !looksLikePlaceholder(fromSlot)) return fromSlot;
        return slot == 2 ? lockOriginWallpaper() : null;
    }

    private static final String LOCK_ORIGIN_PATH =
            "/data/system/theme_magic/users/0/wallpaper/currentWallpaper/wallpaper_lock_origin";
    private static volatile Bitmap lockOrigin;
    private static volatile long lockOriginStamp = -1;

    private static Bitmap lockOriginWallpaper() {
        File file = new File(LOCK_ORIGIN_PATH);
        if (!file.isFile()) return null;
        long stamp = file.lastModified() * 31L + file.length();
        Bitmap cached = lockOrigin;
        if (cached != null && !cached.isRecycled() && stamp == lockOriginStamp) return cached;
        try {
            Bitmap decoded = BitmapFactory.decodeFile(file.getAbsolutePath());
            if (decoded == null || looksLikePlaceholder(decoded)) return null;
            lockOrigin = decoded;
            lockOriginStamp = stamp;
            return decoded;
        } catch (Throwable error) {
            AliveLog.w(TAG, "lock origin decode failed", error);
            return null;
        }
    }

    /**
     * 编辑页卡片读的是当前模板自己的壁纸文件
     * {@code /data/user_de/0/com.miui.aod/files/templates/current/wallpaper}
     * （桌面槽是 {@code wallpaper_home}）。
     *
     * <p>动态壁纸接管后 MIUI 把这两个文件的内容写成了"灰底 + 图片图标"的占位图
     * （实测 335556 字节，与 {@code image/lock_wallpaper.jpg} 一模一样），
     * 编辑页卡片于是整块变灰。这条路径不经过 MiuiWallpaperManager，也不经过
     * WallpaperTypeInfo，只在宿主按路径解码时出现，所以在这里换掉。</p>
     */
    private static void installEditorCurrentWallpaperFallback() {
        Xp.MethodHook hook = new Xp.MethodHook() {
            @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                if (param.args.length == 0 || !(param.args[0] instanceof String)) return;
                String path = (String) param.args[0];
                // 先把绝大多数解码（图标、字体、截图）用一次子串判断挡掉，避免影响性能。
                if (!path.contains("/templates/current/") && !path.contains("/template_current/")) return;
                int slot = slotOfTemplateWallpaperPath(path);
                if (slot == 0) return;
                Bitmap result = param.getResult() instanceof Bitmap
                        ? (Bitmap) param.getResult() : null;
                if (result != null && !looksLikePlaceholder(result)) return;
                Bitmap fill = editorRealWallpaper(slot);
                if (fill == null) return;
                param.setResult(result == null ? fill
                        : Bitmap.createScaledBitmap(fill, result.getWidth(),
                        result.getHeight(), true));
                AliveLog.i(TAG, "editor current wallpaper " + path
                        + " placeholder replaced slot=" + slot);
            }
        };
        int hooks = 0;
        try {
            Xp.findAndHookMethod(android.graphics.BitmapFactory.class, "decodeFile",
                    String.class, hook);
            hooks++;
        } catch (Throwable error) {
            AliveLog.w(TAG, "editor current hook failed decodeFile", error);
        }
        try {
            Xp.findAndHookMethod(android.graphics.BitmapFactory.class, "decodeFile",
                    String.class, android.graphics.BitmapFactory.Options.class, hook);
            hooks++;
        } catch (Throwable error) {
            AliveLog.w(TAG, "editor current hook failed decodeFile(options)", error);
        }
        AliveLog.i(TAG, "editor current wallpaper hooks=" + hooks);
    }

    /** 当前模板目录里的两个壁纸文件：wallpaper 是锁屏，wallpaper_home 是桌面。
     *  provider 的 uri 是 {@code /template_current/...}，磁盘路径是
     *  {@code .../files/templates/current/...}，两种后缀都要认。 */
    private static int slotOfTemplateWallpaperPath(String path) {
        if (path == null) return 0;
        if (path.endsWith("/template_current/wallpaper_home")
                || path.endsWith("/templates/current/wallpaper_home")) return 1;
        if (path.endsWith("/template_current/wallpaper")
                || path.endsWith("/templates/current/wallpaper")) return 2;
        return 0;
    }

    /**
     * 编辑页真正的取图入口：它不走 BitmapFactory.decodeFile，而是通过
     * {@code TemplateFileProvider.openFile(uri, "r")} 拿到
     * {@code template_current/wallpaper(_home)} 的 ParcelFileDescriptor 再解码
     * （日志 {@code TemplateFileProvider: openFile: open wallpaper_home} 实锤）。
     *
     * <p>动态壁纸接管后这两个文件的内容被 MIUI 写成了占位图，卡片于是整块变灰。
     * 所以只在这里换文件描述符：宿主开到的已经是真图时不介入（用户换壁纸立刻生效），
     * 是占位/打不开才换成系统里那张真壁纸。</p>
     */
    private static void installEditorProviderFallback(ClassLoader loader) {
        Class<?> provider = Xp.findClassIfExists(
                "com.miui.keyguard.editor.data.template.TemplateFileProvider", loader);
        if (provider == null) {
            AliveLog.w(TAG, "editor provider: TemplateFileProvider missing");
            return;
        }
        try {
            Xp.findAndHookMethod(provider, "openFile", android.net.Uri.class, String.class,
                    new Xp.MethodHook() {
                        @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                            if (param.args.length < 2) return;
                            if (PROVIDER_CALLS.incrementAndGet() <= 30) {
                                AliveLog.i(TAG, "editor provider call " + param.args[0]
                                        + " mode=" + param.args[1] + " result="
                                        + (param.getResult() == null ? "null" : "fd"));
                            }
                            if (!"r".equals(param.args[1])) return;
                            if (!(param.args[0] instanceof android.net.Uri)) return;
                            String path = ((android.net.Uri) param.args[0]).getPath();
                            int slot = slotOfTemplateWallpaperPath(path);
                            if (slot == 0) return;
                            ParcelFileDescriptor opened = param.getResult()
                                    instanceof ParcelFileDescriptor
                                    ? (ParcelFileDescriptor) param.getResult() : null;
                            if (opened != null && !templateWallpaperIsPlaceholder(slot)) return;
                            File real = realWallpaperFile(slot);
                            if (real == null) {
                                if (PROVIDER_CALLS.get() <= 30) {
                                    AliveLog.i(TAG, "editor provider slot=" + slot
                                            + " real wallpaper unavailable");
                                }
                                return;
                            }
                            try {
                                param.setResult(ParcelFileDescriptor.open(real,
                                        ParcelFileDescriptor.MODE_READ_ONLY));
                            } catch (Throwable error) {
                                AliveLog.w(TAG, "editor provider open failed " + real, error);
                                return;
                            }
                            if (opened != null) {
                                try {
                                    opened.close();
                                } catch (Throwable ignored) {
                                }
                            }
                            AliveLog.i(TAG, "editor provider " + path
                                    + " placeholder replaced by " + real.getAbsolutePath());
                        }
                    });
            AliveLog.i(TAG, "editor provider fallback installed");
        } catch (Throwable error) {
            AliveLog.w(TAG, "editor provider hook failed", error);
        }
    }

    /** 当前模板那两个文件的内容是不是占位图；按文件戳缓存，避免每次开 fd 都解码。 */
    private static final long[] TEMPLATE_WALLPAPER_STAMP = {-1, -1, -1};
    private static final boolean[] TEMPLATE_WALLPAPER_GREY = {false, false, false};

    private static boolean templateWallpaperIsPlaceholder(int slot) {
        File file = new File(TEMPLATE_DIR, slot == 1 ? "wallpaper_home" : "wallpaper");
        if (!file.isFile()) return true;
        long stamp = file.lastModified() * 31L + file.length();
        synchronized (TEMPLATE_WALLPAPER_STAMP) {
            if (stamp == TEMPLATE_WALLPAPER_STAMP[slot]) return TEMPLATE_WALLPAPER_GREY[slot];
            boolean grey = true;
            try {
                // 用 decodeByteArray 绕开我们自己挂的 decodeFile：否则会读到替换后的图，
                // 永远判不出"宿主给的是占位"，替换也不会停止。采样解码只为判颜色数量。
                byte[] data = readAll(file);
                android.graphics.BitmapFactory.Options options =
                        new android.graphics.BitmapFactory.Options();
                options.inSampleSize = 8;
                Bitmap bitmap = data == null ? null
                        : BitmapFactory.decodeByteArray(data, 0, data.length, options);
                grey = bitmap == null || looksLikePlaceholder(bitmap);
                if (bitmap != null) bitmap.recycle();
            } catch (Throwable error) {
                AliveLog.w(TAG, "template wallpaper decode failed " + file, error);
            }
            TEMPLATE_WALLPAPER_STAMP[slot] = stamp;
            TEMPLATE_WALLPAPER_GREY[slot] = grey;
            return grey;
        }
    }

    private static byte[] readAll(File file) {
        try (java.io.FileInputStream in = new java.io.FileInputStream(file)) {
            byte[] buffer = new byte[(int) file.length()];
            int offset = 0;
            while (offset < buffer.length) {
                int read = in.read(buffer, offset, buffer.length - offset);
                if (read <= 0) break;
                offset += read;
            }
            return buffer;
        } catch (Throwable error) {
            return null;
        }
    }

    /** 该槽位系统里那张真壁纸文件；锁屏那张在 currentWallpaper/wallpaper_lock_origin。 */
    private static final long[] REAL_WALLPAPER_STAMP = {-1, -1, -1};
    private static final boolean[] REAL_WALLPAPER_GREY = {true, true, true};

    private static File realWallpaperFile(int slot) {
        File file = slot == 2 ? new File(LOCK_ORIGIN_PATH)
                : new File(STATIC_WALLPAPER_DIR, "home_wallpaper.jpg");
        if (!file.isFile()) return null;
        long stamp = file.lastModified() * 31L + file.length();
        synchronized (REAL_WALLPAPER_STAMP) {
            if (stamp != REAL_WALLPAPER_STAMP[slot]) {
                REAL_WALLPAPER_STAMP[slot] = stamp;
                REAL_WALLPAPER_GREY[slot] = fileIsPlaceholder(file);
            }
            return REAL_WALLPAPER_GREY[slot] ? null : file;
        }
    }

    private static boolean fileIsPlaceholder(File file) {
        long length = file.length();
        byte[] data = readAll(file);
        if (data == null) {
            AliveLog.w(TAG, "wallpaper file unreadable " + file + " len=" + length);
            return true;
        }
        // 用采样解码：只判"颜色多不多"，不需要原尺寸；原尺寸解码一张 2600x2600 的壁纸
        // 要 27MB 堆，容易在宿主进程里 OOM，判占位反而失败。
        android.graphics.BitmapFactory.Options options =
                new android.graphics.BitmapFactory.Options();
        options.inSampleSize = 8;
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length, options);
        boolean grey = bitmap == null || looksLikePlaceholder(bitmap);
        AliveLog.i(TAG, "wallpaper file " + file.getName() + " bytes=" + data.length
                + " decoded=" + (bitmap == null ? "null"
                : bitmap.getWidth() + "x" + bitmap.getHeight())
                + " placeholder=" + grey);
        if (bitmap != null) bitmap.recycle();
        return grey;
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
