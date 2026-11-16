package org.aliveclean;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.View;
import android.widget.ImageView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

final class KeyguardDepthHider {
    private static final String TAG = "AliveHSDepth";
    private static final Handler MAIN = new Handler(Looper.getMainLooper());
    private static final Map<Object, Boolean> PANELS =
            Collections.synchronizedMap(new WeakHashMap<Object, Boolean>());

    private static volatile Context appContext;
    private static volatile boolean ourWallpaper;
    private static volatile long wallpaperCheckedAt;

    static void install(ClassLoader loader) {
        Class<?> panel = Xp.findClassIfExists(
                "com.android.keyguard.panel.KeyguardPanelViewController", loader);
        if (panel == null) {
            AliveLog.w(TAG, "KeyguardPanelViewController missing");
            return;
        }
        Xp.hookAllConstructors(panel, new Xp.MethodHook() {
            @Override protected void afterHookedMethod(Xp.MethodHookParam call) {
                Object instance = call.thisObject;
                if (instance == null) return;
                Object context = Xp.getObjectField(instance, "context");
                if (context instanceof Context) appContext = (Context) context;
                PANELS.put(instance, Boolean.TRUE);
            }
        });

        Class<?> depth = Xp.findClassIfExists(
                "com.android.keyguard.depth.KeyguardDepthInteractor", loader);
        if (depth == null) {
            AliveLog.w(TAG, "KeyguardDepthInteractor missing");
        } else {
            Xp.findAndHookMethod(depth, "updateDeductedImageView", new Xp.MethodHook() {
                @Override protected void beforeHookedMethod(Xp.MethodHookParam call) {
                    if (!isOurWallpaper(contextOf(call.thisObject))) return;
                    cancelParse(call.thisObject);
                    setField(call.thisObject, "isDepthFileParsing", Boolean.FALSE);
                    clear(call.thisObject);
                    AliveLog.i(TAG, "depth parse skipped: AliveHS wallpaper");
                    call.setResult(null);
                }
            });
        }

        Xp.findAndHookMethod(panel, "updateShowDepthState", new Xp.MethodHook() {
            @Override protected void afterHookedMethod(Xp.MethodHookParam call) {
                if (!isOurWallpaper(contextOf(call.thisObject))) return;
                Object depth = Xp.getObjectField(call.thisObject, "keyguardDepthInteractor");
                if (depth == null) return;
                setField(depth, "isActualDisplayDepth", Boolean.FALSE);
                clear(depth);
                try {
                    Xp.callMethod(call.thisObject, "updateKeyguardElementsVisibility");
                } catch (Throwable error) {
                    AliveLog.w(TAG, "refresh keyguard visibility failed", error);
                }
            }
        });

        Xp.findAndHookMethod(panel, "updateKeyguardElementsVisibility", new Xp.MethodHook() {
            @Override protected void beforeHookedMethod(Xp.MethodHookParam call) {
                if (!isOurWallpaper(contextOf(call.thisObject))) return;
                Object depth = Xp.getObjectField(call.thisObject, "keyguardDepthInteractor");
                if (depth == null) return;
                // Native recomputes isActualDisplayDepth=true for a depth wallpaper
                // during this visibility pass.  Reset before the pass so the clock and
                // foreground layer are never laid out as the depth layer for a frame.
                setField(depth, "isActualDisplayDepth", Boolean.FALSE);
                clear(depth);
            }
            @Override protected void afterHookedMethod(Xp.MethodHookParam call) {
                if (!isOurWallpaper(contextOf(call.thisObject))) return;
                Object depth = Xp.getObjectField(call.thisObject, "keyguardDepthInteractor");
                if (depth != null) clear(depth);
            }
        });
        Xp.findAndHookMethod(panel, "isNeedPreHideDepth", new Xp.MethodHook() {
            @Override protected void beforeHookedMethod(Xp.MethodHookParam call) {
                if (!isOurWallpaper(contextOf(call.thisObject))) return;
                // MIUI hides keyguardForegroundLayer (including the clock) while the
                // fingerprint unlock animation is going away; ours must stay visible.
                call.setResult(Boolean.FALSE);
            }
        });
        AliveLog.i(TAG, "installed");
    }

    static void onActivation(Context context) {
        if (context != null) appContext = context;
        wallpaperCheckedAt = 0L;
        refresh();
    }

    static void refresh() {
        MAIN.post(new Runnable() {
            @Override public void run() { applyAll(); }
        });
        MAIN.postDelayed(new Runnable() {
            @Override public void run() { applyAll(); }
        }, 600L);
        MAIN.postDelayed(new Runnable() {
            @Override public void run() { applyAll(); }
        }, 1600L);
    }

    private static void applyAll() {
        List<Object> panels;
        synchronized (PANELS) {
            panels = new ArrayList<Object>(PANELS.keySet());
        }
        for (Object panel : panels) {
            try {
                apply(panel);
            } catch (Throwable error) {
                AliveLog.w(TAG, "panel refresh failed", error);
            }
        }
    }

    private static void apply(Object panel) {
        Object depth = Xp.getObjectField(panel, "keyguardDepthInteractor");
        if (depth == null) return;
        if (!isOurWallpaper(contextOf(panel))) {
            // Do not call MIUI's depth update methods when the system wallpaper is
            // not ours: those calls are what re-add the second depth layer and make
            // the clock flicker while the fingerprint sensor is pressed.
            return;
        }
        setField(depth, "isActualDisplayDepth", Boolean.FALSE);
        clear(depth);
        try {
            Xp.callMethod(panel, "updateShowDepthState");
        } catch (Throwable error) {
            AliveLog.w(TAG, "updateShowDepthState failed", error);
        }
        try {
            Xp.callMethod(depth, "updateDeductedImageView");
        } catch (Throwable error) {
            AliveLog.w(TAG, "updateDeductedImageView failed", error);
        }
        clear(depth);
    }

    private static boolean isOurWallpaper(Context context) {
        long now = SystemClock.uptimeMillis();
        if (wallpaperCheckedAt != 0L && now - wallpaperCheckedAt < 500L) return ourWallpaper;
        if (context == null) context = appContext;
        if (context == null) return false;
        // 锁屏上到底显示谁的壁纸：锁屏槽有独立壁纸时以锁屏槽为准。
        // 只看系统槽会在"系统=我们的、锁屏=MIUI 静态图"时误判，去清空 MIUI 的景深层，
        // MIUI 随即重建一份 —— 锁屏上就成了"两个景深上层上下重叠"（2026-11-16 复现）。
        boolean ours = AodWallpaperMode.lockShowsOurs(context);
        ourWallpaper = ours;
        wallpaperCheckedAt = now;
        return ours;
    }

    private static Context contextOf(Object owner) {
        Context context = appContext;
        if (context != null) return context;
        Object value = Xp.getObjectField(owner, "context");
        if (value instanceof Context) {
            appContext = (Context) value;
            return (Context) value;
        }
        return null;
    }

    private static void clear(Object depth) {
        ImageView view = (ImageView) Xp.getObjectField(depth, "deductedImageView");
        if (view == null) return;
        view.setImageDrawable(null);
        if (view.getVisibility() != View.INVISIBLE) view.setVisibility(View.INVISIBLE);
    }

    private static void cancelParse(Object depth) {
        Object job = Xp.getObjectField(depth, "depthParseJob");
        if (job == null) return;
        try {
            Xp.callMethod(job, "cancel", (Object) null);
            return;
        } catch (Throwable ignored) {
        }
        try {
            Method cancel = job.getClass().getMethod("cancel", Throwable.class);
            cancel.setAccessible(true);
            cancel.invoke(job, new Object[]{null});
        } catch (Throwable ignored) {
        }
    }

    private static void setField(Object owner, String name, Object value) {
        if (owner == null) return;
        for (Class<?> type = owner.getClass(); type != null; type = type.getSuperclass()) {
            try {
                Field field = type.getDeclaredField(name);
                field.setAccessible(true);
                field.set(owner, value);
                return;
            } catch (NoSuchFieldException ignored) {
            } catch (Throwable error) {
                AliveLog.w(TAG, "cannot set " + name, error);
                return;
            }
        }
    }

    private KeyguardDepthHider() { }
}
