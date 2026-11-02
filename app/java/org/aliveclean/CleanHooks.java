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
        boolean colorOsEditor = "com.oplus.wallpapers".equals(p.packageName);
        boolean systemUi = "com.android.systemui".equals(p.packageName);
        boolean miuiAod = "com.miui.aod".equals(p.packageName);
        Log.i(TAG, "loaded into " + p.packageName);
        if (systemUi) step("module receipt", () -> ModuleActivationHook.install());
        if (colorOsEditor) {
            step("coloros clock policy", () -> NativeClockApplyPolicy.install(p.classLoader));
            step("coloros clock fonts", () -> ColorOsNativeClockFonts.install(p.classLoader));
        }
        if (systemUi && PlatformProfile.hasVendorClass(p.classLoader))
            step("coloros clock fonts (systemui)", () -> ColorOsNativeClockFonts.install(p.classLoader));
        if (miuiAod) step("hyperalive aod style", () -> AliveAodStyle.install(p.classLoader));
        if (systemUi) step("hyperos aod", () -> HyperOsAod.install(p.classLoader));
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
        } else if (PlatformProfile.hasVendorClass(p.classLoader)) {
            step("coloros bridge", () -> ColorOsBridge.install(p.classLoader));
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
