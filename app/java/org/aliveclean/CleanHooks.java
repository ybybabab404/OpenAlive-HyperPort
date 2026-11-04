package org.aliveclean;

import android.app.WallpaperInfo;
import android.util.Log;
import de.robv.android.xposed.*;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/** Package-scoped wallpaper capability bridge. No checks run inside the render loop. */
public final class CleanHooks implements IXposedHookLoadPackage {
    private static final String TAG = "HyperAliveHooks";
    private static boolean ambientHooked;

    @Override public void handleLoadPackage(XC_LoadPackage.LoadPackageParam p) {
        boolean systemUi = "com.android.systemui".equals(p.packageName);
        boolean miuiAod = "com.miui.aod".equals(p.packageName);
        boolean themeManager = "com.android.thememanager".equals(p.packageName);
        boolean miuiWallpaper = "com.miui.miwallpaper".equals(p.packageName);
        Log.i(TAG, "loaded into " + p.packageName);
        if (themeManager) step("personalize entry", () -> PersonalizeEntry.install(p.classLoader));
        if (systemUi) step("module receipt", () -> ModuleActivationHook.install());
        if (miuiAod) step("aod settings hotfix", () -> AodSettingsGuard.install(p.classLoader));
        if (miuiWallpaper) step("aod mode wallpaper", () -> AodModeGuard.installWallpaperService(p.classLoader));
        if ("android".equals(p.packageName)) step("aod mode system", () -> AodModeGuard.installSystemWallpaper(p.classLoader));
        if (systemUi) step("hyperos aod", () -> HyperOsAod.install(p.classLoader));
        if (systemUi) step("lock touch relay", () -> KeyguardTouchRelay.install(p.classLoader));
        if (systemUi) step("hyperos doze", () -> HyperOsAod.installDoze(p.classLoader));
        if (!"android".equals(p.packageName) && !systemUi) return;
        if (!ambientHooked) {
            step("ambient wallpaper info", () -> XposedHelpers.findAndHookMethod(WallpaperInfo.class,
                    "supportsAmbientMode", new XC_MethodHook() {
                        @Override protected void afterHookedMethod(MethodHookParam call) {
                            WallpaperInfo info = (WallpaperInfo) call.thisObject;
                            if ("org.aliveclean".equals(info.getPackageName())) call.setResult(true);
                        }
                    }));
            ambientHooked = true;
        }
        if ("android".equals(p.packageName)) {
            step("ambient wallpaper permission", () -> {
                Class<?> base = XposedHelpers.findClassIfExists("com.android.server.pm.IPackageManagerBase", p.classLoader);
                if (base == null) return;
                XposedHelpers.findAndHookMethod(base, "checkPermission", String.class, String.class, int.class,
                        new XC_MethodHook() {
                            @Override protected void beforeHookedMethod(MethodHookParam call) {
                                if ("android.permission.AMBIENT_WALLPAPER".equals(call.args[0])
                                        && "org.aliveclean".equals(call.args[1]))
                                    call.setResult(android.content.pm.PackageManager.PERMISSION_GRANTED);
                            }
                        });
            });
        }
    }

    private interface Step { void run() throws Throwable; }

    private static void step(String name, Step body) {
        try {
            body.run();
            Log.i(TAG, name + ": ok");
        } catch (Throwable error) {
            Log.e(TAG, name + ": failed", error);
        }
    }
}
