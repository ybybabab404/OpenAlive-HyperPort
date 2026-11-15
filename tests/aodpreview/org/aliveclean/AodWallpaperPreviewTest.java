package org.aliveclean;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.ParcelFileDescriptor;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.util.List;

/**
 * Offline tests for the Personalize-screen thumbnail hooks.
 *
 * <p>These hooks run inside com.android.thememanager.  A wrong argument guard or a
 * missing null check would either crash the settings UI or replace an unrelated
 * bitmap; the tests therefore exercise both hook shapes and every replacement branch.
 */
public final class AodWallpaperPreviewTest {
    private static int checks;

    private static final String TEMPLATE_DIR =
            "/data/user_de/0/com.miui.aod/files/templates/current/";
    private static final String TEMPLATE_API =
            "com.miui.keyguard.editor.data.template.TemplateApiImpl";
    private static final String SCREENSHOTS =
            "com.miui.keyguard.editor.utils.Screenshots";
    private static final String LOCK_TOOL =
            "com.android.thememanager.settings.personalize.task.LockScreenWpTool";
    private static final String DESK_TOOL =
            "com.android.thememanager.settings.personalize.task.DeskWpTool";
    private static final String HELPER =
            "com.miui.keyguard.editor.utils.DeviceScreenshotHelper";

    private static final class TemplateApiImpl {}
    private static final class Screenshots {}
    private static final class LockScreenWpTool {}
    private static final class DeskWpTool {}
    private static final class DeviceScreenshotHelper {}

    private static final String TEMPLATE_API_KEY = TemplateApiImpl.class.getName();
    private static final String SCREENSHOTS_KEY = Screenshots.class.getName();
    private static final String LOCK_TOOL_KEY = LockScreenWpTool.class.getName();
    private static final String DESK_TOOL_KEY = DeskWpTool.class.getName();
    private static final String HELPER_KEY = DeviceScreenshotHelper.class.getName();

    private static void check(boolean value, String message) {
        checks++;
        if (!value) throw new AssertionError(message);
    }

    private static File template(String name) {
        return new File(TEMPLATE_DIR, name);
    }

    private static void touch(String name, int length) throws Exception {
        File file = template(name);
        file.getParentFile().mkdirs();
        Files.write(file.toPath(), new byte[length]);
    }

    private static void reset() throws Exception {
        Xp.CLASSES.clear();
        Xp.HOOKS.clear();
        Xp.CLASSES.put(TEMPLATE_API, TemplateApiImpl.class);
        Xp.CLASSES.put(SCREENSHOTS, Screenshots.class);
        Xp.CLASSES.put(LOCK_TOOL, LockScreenWpTool.class);
        Xp.CLASSES.put(DESK_TOOL, DeskWpTool.class);
        Xp.CLASSES.put(HELPER, DeviceScreenshotHelper.class);
        Xp.application = new Application();
        Xp.objectFieldValue = null;
        AodWallpaperMode.alive = true;

        BitmapFactory.providerBitmap = new Bitmap(20, 10);
        BitmapFactory.providerDecodeNull = false;
        BitmapFactory.fileBitmap = null;
        BitmapFactory.fileDecodeNull = true;
        BitmapFactory.lastDecodedFile = null;
        BitmapFactory.fileDecodeCalls = 0;
        Bitmap.lastScaledSource = null;
        Bitmap.lastScaledWidth = 0;
        Bitmap.lastScaledHeight = 0;
        Bitmap.scaledCalls = 0;

        android.content.ContentResolver.descriptor = new ParcelFileDescriptor();
        android.content.ContentResolver.throwOpen = false;
        android.content.ContentResolver.lastUri = null;

        Field cached = AodWallpaperPreview.class.getDeclaredField("cached");
        cached.setAccessible(true);
        cached.set(null, null);
        Field stamp = AodWallpaperPreview.class.getDeclaredField("cachedStamp");
        stamp.setAccessible(true);
        stamp.setLong(null, -1L);

        for (String name : new String[]{"wallpaper_home", "wallpaper"}) {
            File file = template(name);
            if (file.exists()) Files.delete(file.toPath());
        }
    }

    private static void install() {
        AodWallpaperPreview.installThemeManager(
                AodWallpaperPreviewTest.class.getClassLoader());
    }

    private static Xp.MethodHookParam fire(String owner, String method, Object[] args,
                                           Object result) throws Exception {
        List<Xp.MethodHook> hooks = Xp.HOOKS.get(owner + "#" + method);
        if (hooks == null || hooks.isEmpty()) throw new AssertionError("no hook " + owner + "#" + method);
        Xp.MethodHook hook = hooks.get(hooks.size() - 1);
        Xp.MethodHookParam param = new Xp.MethodHookParam();
        param.args = args;
        param.setResult(result);
        Method callback = hook.getClass().getDeclaredMethod(
                "afterHookedMethod", Xp.MethodHookParam.class);
        callback.setAccessible(true);
        callback.invoke(hook, param);
        return param;
    }

    private static Object fireLock(Object result) throws Exception {
        return fire(LOCK_TOOL_KEY, "toq", new Object[0], result).getResult();
    }

    private static Object fireDesk(Object result) throws Exception {
        return fire(DESK_TOOL_KEY, "toq", new Object[]{new Object()}, result).getResult();
    }

    private static void testInstallAndNullReplacement() throws Exception {
        reset();
        install();
        check(Xp.HOOKS.containsKey(TEMPLATE_API_KEY + "#uf"), "uf hook installed");
        check(Xp.HOOKS.containsKey(SCREENSHOTS_KEY + "#t8r"), "shots hook installed");
        check(Xp.HOOKS.containsKey(LOCK_TOOL_KEY + "#toq"), "lock hook installed");
        check(Xp.HOOKS.containsKey(DESK_TOOL_KEY + "#toq"), "desk hook installed");
        check(Xp.HOOKS.containsKey(HELPER_KEY + "#ni7"), "ni7 hook installed");

        Object replaced = fireLock(null);
        check(replaced == BitmapFactory.providerBitmap, "null lock thumbnail is replaced");
        check(android.content.ContentResolver.lastUri.contains("wallpaper_home"),
                "provider wallpaper_home is the first source");
        check(android.content.ContentResolver.descriptor.closed,
                "provider descriptor is closed after decode");

        Object deskReplaced = fireDesk(null);
        check(deskReplaced == BitmapFactory.providerBitmap, "null desk thumbnail is replaced");
    }

    private static void testArgumentGuardsAndScaling() throws Exception {
        reset();
        install();

        Bitmap original = new Bitmap(100, 50);
        Object scaled = fireDesk(original);
        check(scaled != original, "wrong-size desk result is replaced");
        check(((Bitmap) scaled).getWidth() == 100 && ((Bitmap) scaled).getHeight() == 50,
                "desk replacement keeps host dimensions");
        check(Bitmap.lastScaledSource == BitmapFactory.providerBitmap,
                "desk scaling uses the provider preview");

        Bitmap same = new Bitmap(20, 10);
        check(fireLock(same) == BitmapFactory.providerBitmap,
                "same-size lock thumbnail reuses the preview bitmap");

        Object untouched = fire(LOCK_TOOL_KEY, "toq", new Object[]{new Object()}, null).getResult();
        check(untouched == null, "lock hook ignores calls with unexpected arguments");

        Object deskUntouched = fire(DESK_TOOL_KEY, "toq", new Object[0], null).getResult();
        check(deskUntouched == null, "desk hook ignores calls with unexpected arguments");
    }

    private static void testForeignWallpaperAndFailures() throws Exception {
        reset();
        install();
        AodWallpaperMode.alive = false;
        check(fireLock(null) == null, "foreign wallpaper leaves lock thumbnail alone");
        check(android.content.ContentResolver.lastUri == null,
                "foreign wallpaper does not touch the provider");

        reset();
        install();
        android.content.ContentResolver.throwOpen = true;
        check(fireLock(null) == null, "provider failure without template leaves result alone");

        reset();
        install();
        android.content.ContentResolver.descriptor = null;
        check(fireLock(null) == null, "null provider descriptor leaves result alone");

        reset();
        install();
        BitmapFactory.providerDecodeNull = true;
        ParcelFileDescriptor pfd = android.content.ContentResolver.descriptor;
        check(fireLock(null) == null, "failed decode without template leaves result alone");
        check(pfd.closed, "descriptor is closed even when decode returns null");
    }

    private static void testTemplateFallback() throws Exception {
        reset();
        install();
        BitmapFactory.providerDecodeNull = true;
        BitmapFactory.fileDecodeNull = false;
        BitmapFactory.fileBitmap = new Bitmap(30, 40);
        touch("wallpaper_home", 8);
        touch("wallpaper", 16);

        Object fromHome = fireLock(null);
        check(fromHome == BitmapFactory.fileBitmap, "template wallpaper_home is used");
        check(BitmapFactory.lastDecodedFile.endsWith("wallpaper_home"),
                "wallpaper_home wins over wallpaper");

        Files.delete(template("wallpaper_home").toPath());
        Field cached = AodWallpaperPreview.class.getDeclaredField("cached");
        cached.setAccessible(true);
        cached.set(null, null);
        Field stamp = AodWallpaperPreview.class.getDeclaredField("cachedStamp");
        stamp.setAccessible(true);
        stamp.setLong(null, -1L);

        Object fromWallpaper = fireLock(null);
        check(fromWallpaper == BitmapFactory.fileBitmap, "template wallpaper fallback is used");
        check(BitmapFactory.lastDecodedFile.endsWith("wallpaper"),
                "wallpaper is used when wallpaper_home is absent");

        reset();
        install();
        BitmapFactory.providerDecodeNull = true;
        check(fireLock(null) == null, "no provider and no template yields null");
    }

    private static void testUfShotsNi7() throws Exception {
        reset();
        install();

        Bitmap ufOriginal = new Bitmap(64, 32);
        Object uf = fire(TEMPLATE_API_KEY, "uf", new Object[0], ufOriginal).getResult();
        check(uf instanceof Bitmap && ((Bitmap) uf).getWidth() == 64
                && ((Bitmap) uf).getHeight() == 32, "uf result is scaled to host size");
        Object ufGuard = fire(TEMPLATE_API_KEY, "uf", new Object[]{new Object()}, ufOriginal).getResult();
        check(ufGuard == ufOriginal, "uf ignores calls with arguments");

        Object shotsNull = fire(SCREENSHOTS_KEY, "t8r", new Object[0], null).getResult();
        check(shotsNull == BitmapFactory.providerBitmap, "empty screenshot is filled");
        Bitmap shotsOriginal = new Bitmap(48, 24);
        Object shots = fire(SCREENSHOTS_KEY, "t8r", new Object[0], shotsOriginal).getResult();
        check(shots != shotsOriginal && ((Bitmap) shots).getWidth() == 48,
                "screenshot is scaled to the host size");

        Object ni7Null = fire(HELPER_KEY, "ni7", new Object[0], null).getResult();
        check(ni7Null == null, "ni7 null result is left alone");
        Bitmap ni7Original = new Bitmap(120, 60);
        Object ni7 = fire(HELPER_KEY, "ni7", new Object[0], ni7Original).getResult();
        check(ni7 != ni7Original && ((Bitmap) ni7).getWidth() == 120,
                "ni7 result is scaled to the host size");
    }

    public static void main(String[] args) throws Exception {
        testInstallAndNullReplacement();
        testArgumentGuardsAndScaling();
        testForeignWallpaperAndFailures();
        testTemplateFallback();
        testUfShotsNi7();
        System.out.println("AOD_PREVIEW_OK: install counts, null/scale/same-size replacement, "
                + "arg guards, foreign/failed provider, template order, pfd close ("
                + checks + " checks)");
    }
}
