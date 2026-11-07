package org.aliveclean;

import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
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
            Log.w(TAG, "KeyguardPanelViewController missing");
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
            Log.w(TAG, "KeyguardDepthInteractor missing");
        } else {
            Xp.findAndHookMethod(depth, "updateDeductedImageView", new Xp.MethodHook() {
                @Override protected void beforeHookedMethod(Xp.MethodHookParam call) {
                    if (!isOurWallpaper(contextOf(call.thisObject))) return;
                    cancelParse(call.thisObject);
                    setField(call.thisObject, "isDepthFileParsing", Boolean.FALSE);
                    clear(call.thisObject);
                    Log.i(TAG, "depth parse skipped: AliveHS wallpaper");
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
                    Log.w(TAG, "refresh keyguard visibility failed", error);
                }
            }
        });

        Xp.findAndHookMethod(panel, "updateKeyguardElementsVisibility", new Xp.MethodHook() {
            @Override protected void afterHookedMethod(Xp.MethodHookParam call) {
                if (!isOurWallpaper(contextOf(call.thisObject))) return;
                Object depth = Xp.getObjectField(call.thisObject, "keyguardDepthInteractor");
                if (depth != null) clear(depth);
            }
        });
        Log.i(TAG, "installed");
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
                Log.w(TAG, "panel refresh failed", error);
            }
        }
    }

    private static void apply(Object panel) {
        Object depth = Xp.getObjectField(panel, "keyguardDepthInteractor");
        if (depth == null) return;
        boolean ours = isOurWallpaper(contextOf(panel));
        if (ours) {
            setField(depth, "isActualDisplayDepth", Boolean.FALSE);
            clear(depth);
        }
        try {
            Xp.callMethod(panel, "updateShowDepthState");
        } catch (Throwable error) {
            Log.w(TAG, "updateShowDepthState failed", error);
        }
        try {
            Xp.callMethod(depth, "updateDeductedImageView");
        } catch (Throwable error) {
            Log.w(TAG, "updateDeductedImageView failed", error);
        }
        if (ours) clear(depth);
    }

    private static boolean isOurWallpaper(Context context) {
        long now = SystemClock.uptimeMillis();
        if (wallpaperCheckedAt != 0L && now - wallpaperCheckedAt < 500L) return ourWallpaper;
        if (context == null) context = appContext;
        if (context == null) return false;
        boolean ours = false;
        try {
            WallpaperManager manager = WallpaperManager.getInstance(context);
            WallpaperInfo info = null;
            if (Build.VERSION.SDK_INT >= 34) {
                try {
                    info = manager.getWallpaperInfo(WallpaperManager.FLAG_LOCK);
                } catch (Throwable ignored) {

                }
            }
            if (info == null) info = manager.getWallpaperInfo();
            ours = info != null && "org.aliveclean".equals(info.getPackageName());
        } catch (Throwable error) {
            Log.w(TAG, "wallpaper lookup failed", error);
        }
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
                Log.w(TAG, "cannot set " + name, error);
                return;
            }
        }
    }

    private KeyguardDepthHider() { }
}
