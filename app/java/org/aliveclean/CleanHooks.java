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
    private static final java.util.concurrent.atomic.AtomicBoolean ambientGrantLogged =
            new java.util.concurrent.atomic.AtomicBoolean();

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
            step("device idle delay", () -> DeviceIdleDelayHook.install(loader));
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
            step("ambient permission base", () -> hookCheckPermission(
                    "com.android.server.pm.IPackageManagerBase", loader, "IPackageManagerBase"));
            step("ambient permission service", () -> hookCheckPermission(
                    "com.android.server.pm.PackageManagerService", loader, "PackageManagerService"));
        }
    }

    /**
     * WallpaperManagerService checks AMBIENT_WALLPAPER through
     * IPackageManager.checkPermission(String, String, int). That interface call is dispatched to
     * PackageManagerService$IPackageManagerImpl, which inherits the final forwarder declared in
     * IPackageManagerBase; the forwarder is seven code units long, so ART is free to inline it and
     * a hook installed only there can miss the call. Both layers are hooked for that reason, and
     * the result is forced exclusively for our own package, so no other permission check changes.
     */
    private static void hookCheckPermission(String className, ClassLoader loader, String label) {
        Class<?> type = findServerClass(className, loader, label);
        if (type == null) {
            Log.w(TAG, "ambient permission: " + className + " not found"
                    + " (loader=" + loader + ")");
            return;
        }
        Xp.findAndHookMethod(type, "checkPermission", String.class, String.class, int.class,
                new Xp.MethodHook() {
                    @Override protected void beforeHookedMethod(Xp.MethodHookParam call) {
                        if (!isAmbientWallpaperCheck(call.args)) return;
                        call.setResult(android.content.pm.PackageManager.PERMISSION_GRANTED);
                        if (ambientGrantLogged.compareAndSet(false, true))
                            Log.i(TAG, "ambient permission: granted via " + label);
                    }
                });
    }

    private static Class<?> findServerClass(String className, ClassLoader loader, String label) {
        Class<?> type = Xp.findClassIfExists(className, loader);
        if (type != null) return type;
        ClassLoader[] candidates = new ClassLoader[]{
                ClassLoader.getSystemClassLoader(),
                Thread.currentThread().getContextClassLoader()
        };
        for (ClassLoader candidate : candidates) {
            if (candidate == null || candidate == loader) continue;
            try {
                type = Class.forName(className, false, candidate);
                Log.i(TAG, "ambient permission: " + label + " resolved via " + candidate);
                return type;
            } catch (Throwable ignored) {
            }
        }
        return null;
    }

    private static boolean isAmbientWallpaperCheck(Object[] args) {
        return args != null && args.length >= 2
                && "android.permission.AMBIENT_WALLPAPER".equals(args[0])
                && "org.aliveclean".equals(args[1]);
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
