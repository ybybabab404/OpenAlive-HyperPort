package org.aliveclean;

import android.app.WallpaperInfo;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import io.github.libxposed.api.XposedModule;
import io.github.libxposed.api.XposedModuleInterface.ModuleLoadedParam;
import io.github.libxposed.api.XposedModuleInterface.PackageLoadedParam;
import io.github.libxposed.api.XposedModuleInterface.SystemServerStartingParam;

/** Package-scoped wallpaper capability bridge. No checks run inside the render loop. */
public final class CleanHooks extends XposedModule {
    private static final String TAG = "HyperAliveHooks";
    private final Set<String> handled = new HashSet<>();
    private boolean ambientHooked;
    private boolean systemHandled;

    @Override public void onModuleLoaded(ModuleLoadedParam param) {
        Xp.attach(this);
        Log.i(TAG, "module loaded process=" + param.getProcessName() + " api=" + getApiVersion());
    }

    @Override public void onPackageLoaded(PackageLoadedParam param) {
        handle(param.getPackageName(), param.getDefaultClassLoader());
    }

    @Override public void onSystemServerStarting(SystemServerStartingParam param) {
        handle("android", param.getClassLoader());
    }

    private void handle(String packageName, ClassLoader loader) {
        synchronized (handled) {
            if (!handled.add(packageName)) return;
        }
        boolean systemUi = "com.android.systemui".equals(packageName);
        boolean miuiAod = "com.miui.aod".equals(packageName);
        boolean themeManager = "com.android.thememanager".equals(packageName);
        boolean system = "android".equals(packageName);
        Log.i(TAG, "loaded into " + packageName);
        if (themeManager) step("personalize entry", () -> PersonalizeEntry.install(loader));
        if (themeManager) step("tm wallpaper preview", () -> AodWallpaperPreview.installThemeManager(loader));
        if (systemUi) step("module receipt", () -> ModuleActivationHook.install());
        if (miuiAod) step("aod settings hotfix", () -> AodSettingsGuard.install(loader));
        if (miuiAod) step("aod preview hide", () -> AodPreviewHider.install(loader));
        if (miuiAod) step("aod wallpaper preview", () -> AodWallpaperPreview.install(loader));
        if (systemUi) step("hyperos aod", () -> HyperOsAod.install(loader));
        if (systemUi) step("lock touch relay", () -> KeyguardTouchRelay.install(loader));
        if (systemUi) step("hyperos doze", () -> HyperOsAod.installDoze(loader));
        if (system && !systemHandled) {
            systemHandled = true;
            step("aod wallpaper mode", () -> AodWallpaperModeHook.install(loader));
        }
        if (!system && !systemUi) return;
        if (!ambientHooked) {
            ambientHooked = true;
            step("ambient wallpaper info", () -> Xp.findAndHookMethod(WallpaperInfo.class,
                    "supportsAmbientMode", new Xp.MethodHook() {
                        @Override protected void afterHookedMethod(Xp.MethodHookParam call) {
                            WallpaperInfo info = (WallpaperInfo) call.thisObject;
                            if ("org.aliveclean".equals(info.getPackageName())) call.setResult(true);
                        }
                    }));
        }
        if (system) {
            step("ambient wallpaper permission", () -> {
                Class<?> base = Xp.findClassIfExists("com.android.server.pm.IPackageManagerBase", loader);
                if (base == null) return;
                Xp.findAndHookMethod(base, "checkPermission", String.class, String.class, int.class,
                        new Xp.MethodHook() {
                            @Override protected void beforeHookedMethod(Xp.MethodHookParam call) {
                                if ("android.permission.AMBIENT_WALLPAPER".equals(call.args[0])
                                        && "org.aliveclean".equals(call.args[1]))
                                    call.setResult(android.content.pm.PackageManager.PERMISSION_GRANTED);
                            }
                        });
            });
        }
    }

    private interface Step {
        void run() throws Throwable;
    }

    private static void step(String name, Step body) {
        try {
            body.run();
            Log.i(TAG, name + ": ok");
        } catch (Throwable error) {
            Log.e(TAG, name + ": failed", error);
        }
    }
}
