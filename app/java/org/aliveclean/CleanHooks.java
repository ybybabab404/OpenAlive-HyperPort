package org.aliveclean;

import android.app.WallpaperInfo;

import java.util.HashSet;
import java.util.Set;

import io.github.libxposed.api.XposedModule;
import io.github.libxposed.api.XposedModuleInterface.HotReloadedParam;
import io.github.libxposed.api.XposedModuleInterface.HotReloadingParam;
import io.github.libxposed.api.XposedModuleInterface.ModuleLoadedParam;
import io.github.libxposed.api.XposedModuleInterface.PackageLoadedParam;
import io.github.libxposed.api.XposedModuleInterface.SystemServerStartingParam;

/**
 * LSPosed 模块入口：按作用域把各个 hook 装到对应进程，并逐步记录每一步的安装结果。
 *
 * <p>某一步失败只影响该功能，不应该让整个模块加载失败——所以这里都是 step 包裹而不是直接抛。</p>
 */
public final class CleanHooks extends XposedModule {
    private static final String TAG = "AliveHSHooks";
    private static final String SERVER_KEY = "system_server";
    private final Set<String> handled = new HashSet<>();
    private boolean ambientHooked;
    private static final java.util.concurrent.atomic.AtomicBoolean ambientGrantLogged =
            new java.util.concurrent.atomic.AtomicBoolean();

    @Override public void onModuleLoaded(ModuleLoadedParam param) {
        Xp.attach(this);
        AliveLog.i(TAG, "module loaded process=" + param.getProcessName() + " api=" + getApiVersion());
    }

    @Override public void onPackageLoaded(PackageLoadedParam param) {
        String packageName = param.getPackageName();
        if ("android".equals(packageName)) return;
        handle(packageName, param.getDefaultClassLoader());
    }

    @Override public void onSystemServerStarting(SystemServerStartingParam param) {
        handleSystemServer(param.getClassLoader());
    }

    /**
     * LSPosed API 102 hot reload: the framework loads this APK's new dex into the
     * already-running process.  The old hook handles are supplied in onHotReloaded;
     * unhook them and re-install this process's hooks against the new class loader.
     */
    @Override public boolean onHotReloading(HotReloadingParam param) {
        AliveLog.i(TAG, "module hot reloading pid=" + android.os.Process.myPid());
        return true;
    }

    @Override public void onHotReloaded(HotReloadedParam param) {
        Xp.attach(this);
        AliveLog.i(TAG, "module hot reloaded process=" + param.getProcessName()
                + " pid=" + android.os.Process.myPid());
        try {
            for (io.github.libxposed.api.XposedInterface.HookHandle handle : param.getOldHookHandles()) {
                try { handle.unhook(); } catch (Throwable ignored) { }
            }
        } catch (Throwable error) {
            AliveLog.w(TAG, "hot reload unhook failed", error);
        }
        synchronized (handled) {
            handled.clear();
        }
        ambientHooked = false;
        ClassLoader loader = hotReloadLoader();
        if (param.isSystemServer()) {
            handleSystemServer(loader);
            return;
        }
        String process = param.getProcessName();
        if (process == null) return;
        int colon = process.indexOf(':');
        String packageName = colon < 0 ? process : process.substring(0, colon);
        handle(packageName, loader);
    }

    private static ClassLoader hotReloadLoader() {
        try {
            android.app.Application app = Xp.currentApplication();
            if (app != null) return app.getClassLoader();
        } catch (Throwable ignored) {
        }
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        return loader != null ? loader : ClassLoader.getSystemClassLoader();
    }

    private void handle(String packageName, ClassLoader loader) {
        synchronized (handled) {
            if (!handled.add(packageName)) return;
        }
        boolean systemUi = "com.android.systemui".equals(packageName);
        boolean miuiAod = "com.miui.aod".equals(packageName);
        boolean themeManager = "com.android.thememanager".equals(packageName);
        AliveLog.i(TAG, "loaded into " + packageName + " pid=" + android.os.Process.myPid());
        if (themeManager) step("personalize entry", () -> PersonalizeEntry.install(loader));
        if (themeManager) step("tm wallpaper preview", () -> AodWallpaperPreview.installThemeManager(loader));
        // 个性化页重进/应用后卡在「加载中」：宿主的 restoreTemplate 自己不会调，这里替它调。
        if (themeManager) step("personalize loading fix", () -> PersonalizeLoadingFix.install(loader));
        if (miuiAod) step("aod settings hotfix", () -> AodSettingsGuard.install(loader));
        if (miuiAod) step("aod preview hide", () -> AodPreviewHider.install(loader));
        if (miuiAod) step("aod wallpaper preview", () -> AodWallpaperPreview.install(loader));
        if (systemUi) step("aod full screen", () -> AodFullScreen.install(loader, false));
        if (miuiAod) step("aod full screen", () -> AodFullScreen.install(loader, true));
        if (systemUi) step("module receipt", () -> ModuleActivationHook.install());
        if (systemUi) step("hyperos aod", () -> HyperOsAod.install(loader));
        if (systemUi) step("lock touch relay", () -> KeyguardTouchRelay.install(loader));
        if (systemUi) step("keyguard depth", () -> KeyguardDepthHider.install(loader));
        if (systemUi) step("hyperos doze", () -> HyperOsAod.installDoze(loader));
        if (!systemUi) return;
        hookAmbientWallpaperInfo();
    }

    private void handleSystemServer(ClassLoader loader) {
        synchronized (handled) {
            if (!handled.add(SERVER_KEY)) return;
        }
        AliveLog.i(TAG, "loaded into system_server pid=" + android.os.Process.myPid());
        ClassLoader server = resolveServerLoader(loader);
        step("aod wallpaper mode", () -> AodWallpaperModeHook.install(server));
        step("device idle delay", () -> DeviceIdleDelayHook.install(server));
        step("ambient permission base", () -> hookCheckPermission(
                "com.android.server.pm.IPackageManagerBase", server, "IPackageManagerBase"));
        step("ambient permission service", () -> hookCheckPermission(
                "com.android.server.pm.PackageManagerService", server, "PackageManagerService"));
        hookAmbientWallpaperInfo();
    }

    private void hookAmbientWallpaperInfo() {
        if (ambientHooked) return;
        ambientHooked = true;
        step("ambient wallpaper info", () -> Xp.findAndHookMethod(WallpaperInfo.class,
                "supportsAmbientMode", new Xp.MethodHook() {
                    @Override protected void afterHookedMethod(Xp.MethodHookParam call) {
                        WallpaperInfo info = (WallpaperInfo) call.thisObject;
                        if ("org.aliveclean".equals(info.getPackageName())) call.setResult(true);
                    }
                }));
    }

    private static void hookCheckPermission(String className, ClassLoader loader, String label) {
        Class<?> type = findServerClass(className, loader, label);
        if (type == null) throw new IllegalStateException(className + " not found (" + loader + ")");
        Xp.findAndHookMethod(type, "checkPermission", String.class, String.class, int.class,
                new Xp.MethodHook() {
                    @Override protected void beforeHookedMethod(Xp.MethodHookParam call) {
                        if (!isAmbientWallpaperCheck(call.args)) return;
                        call.setResult(android.content.pm.PackageManager.PERMISSION_GRANTED);
                        if (ambientGrantLogged.compareAndSet(false, true))
                            AliveLog.i(TAG, "ambient permission: granted via " + label);
                    }
                });
    }

    private static ClassLoader resolveServerLoader(ClassLoader loader) {
        try {
            return Xp.systemServerLoader(loader);
        } catch (Throwable error) {
            AliveLog.e(TAG, "system server loader: failed", error);
            return loader;
        }
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
                AliveLog.i(TAG, "ambient permission: " + label + " resolved via " + candidate);
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
            AliveLog.i(TAG, name + ": ok");
        } catch (Throwable error) {
            AliveLog.e(TAG, name + ": failed", error);
        }
    }
}
