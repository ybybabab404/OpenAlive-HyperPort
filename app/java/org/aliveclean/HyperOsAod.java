package org.aliveclean;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

final class HyperOsAod {
    private static final String TAG = "AliveHSAod";
    private static java.lang.ref.WeakReference<AodSurface> liveSurface;
    private static final String[] VIEWS = {
            "com.android.keyguard.widget.AodView",
            "com.miui.aod.AODView"
    };

    static void install(ClassLoader loader) {
        boolean installed = false;
        for (String name : VIEWS) {
            Class<?> view = Xp.findClassIfExists(name, loader);
            if (view == null || !ViewGroup.class.isAssignableFrom(view)) continue;
            Xp.hookAllConstructors(view, new Xp.MethodHook() {
                @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                    final ViewGroup root = (ViewGroup) param.thisObject;
                    root.post(new Runnable() {
                        @Override public void run() {
                            try {
                                attach(root);
                            } catch (Throwable error) {
                                Log.e(TAG, "attach failed", error);
                            }
                        }
                    });
                }
            });
            Trace.log("T01", "hooks installed for " + name);
            installed = true;
        }
        if (!installed) Log.i(TAG, "no AOD view class in this process");
    }

    static void installDoze(ClassLoader loader) {
        Class<?> service = Xp.findClassIfExists("com.android.keyguard.doze.MiuiDozeService", loader);
        if (service == null) { Log.i(TAG, "no MiuiDozeService"); return; }
        Xp.hookAllMethods(service, "onDreamingStarted", new Xp.MethodHook() {
            @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                final android.app.Service svc = (android.app.Service) param.thisObject;
                Trace.log("T07", "dreaming started");

                AodOverlay.show(svc);
            }
        });
        Xp.hookAllMethods(service, "onDreamingStopped", new Xp.MethodHook() {
            @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                Trace.log("T08", "dreaming stopped");
                AodOverlay.hide();
            }
        });
        Log.i(TAG, "doze service hook installed");
    }

    private static java.lang.ref.WeakReference<ViewGroup> aodRoot;

    static boolean hasInjectedView() {
        ViewGroup root = aodRoot == null ? null : aodRoot.get();
        return root != null && root.findViewWithTag(TAG) != null;
    }

    static boolean injectIntoAod() {
        ViewGroup root = aodRoot == null ? null : aodRoot.get();
        if (root == null) return false;
        attach(root);
        return root.findViewWithTag(TAG) != null;
    }

    private static void attach(ViewGroup root) {

        Context context = root.getContext();
        aodRoot = new java.lang.ref.WeakReference<>(root);
        if (!aodSceneEnabled(context)) return;

        AodSurface existing = liveSurface == null ? null : liveSurface.get();
        if (existing != null && existing.isAttachedToWindow() && existing.isShown()) {
            Trace.log("T03", "attach skipped, live surface id=" + System.identityHashCode(existing));
            return;
        }
        if (existing != null) {

            Trace.log("T03", "attach replacing hidden surface id=" + System.identityHashCode(existing));
            liveSurface = null;
        }

        if (root.findViewWithTag(TAG) != null) return;

        ViewGroup target = findInnerAod(root);
        if (target == null) {

            Log.i(TAG, "inner AODView not up yet, waiting");
            root.postDelayed(new Runnable() {
                @Override public void run() { injectIntoAod(); }
            }, 200);
            return;
        }
        AodSurface surface = new AodSurface(context);
        surface.setTag(TAG);
        surface.setAlpha(1f);
        target.addView(surface, 0, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        liveSurface = new java.lang.ref.WeakReference<>(surface);
        Trace.log("T04", "surface#create id=" + System.identityHashCode(surface)
                + " host=" + System.identityHashCode(target) + " children=" + target.getChildCount());

    }

    private static void supervise(final ViewGroup root, final AodSurface surface) {
        root.postDelayed(new Runnable() {
            int tries;
            int misses;

            @Override public void run() {

                if (surface.getParent() == null) {
                    if (++misses < 8) { next(); return; }
                    ViewGroup host = findInnerAod(root);
                    (host != null ? host : root).addView(surface, 0,
                            new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT));
                    misses = 0;
                    Log.i(TAG, "scene re-attached after misses");
                } else {
                    misses = 0;
                    ViewGroup inner = findInnerAod(root);
                    if (inner != null && surface.getParent() != inner) {
                        ((ViewGroup) surface.getParent()).removeView(surface);
                        inner.addView(surface, 0, new ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT));
                        Log.i(TAG, "scene moved into inner AODView");
                    }
                }
                next();
            }

            private void next() {
                if (++tries < 100) root.postDelayed(this, 300);
                else Log.i(TAG, "supervise done, parent="
                        + (surface.getParent() == null ? "null"
                        : surface.getParent().getClass().getName()));
            }
        }, 300);
    }

    private static ViewGroup findInnerAod(ViewGroup root) {
        String own = root.getClass().getName();
        if ("com.miui.aod.AODView".equals(own) || "AODView".equals(root.getClass().getSimpleName())) {
            return root;
        }
        for (int i = 0; i < root.getChildCount(); i++) {
            View child = root.getChildAt(i);
            if (!(child instanceof ViewGroup)) continue;
            String name = child.getClass().getName();
            if ("com.miui.aod.AODView".equals(name)
                    || "AODView".equals(child.getClass().getSimpleName())) {
                return (ViewGroup) child;
            }
        }
        return null;
    }

    private static Context moduleContext(Context base) {
        try {
            return base.createPackageContext("org.aliveclean", 0);
        } catch (Throwable unavailable) {
            Log.w(TAG, "module context unavailable, using the host context", unavailable);
            return base;
        }
    }

    private static boolean aodSceneEnabled(Context base) {
        try {
            Context app = moduleContext(base);
            android.content.SharedPreferences prefs =
                    app.getSharedPreferences(SceneOptions.APPLIED, 0);

            return prefs.getInt("aod", 0) != -1;
        } catch (Throwable unavailable) {
            Log.w(TAG, "cannot read scene settings", unavailable);
            return false;
        }
    }

    private static final class AodSurface extends TextureView implements TextureView.SurfaceTextureListener {
        private final Context appContext;
        private RenderLoop loop;
        private Surface surface;

        AodSurface(Context context) {
            super(context);
            appContext = moduleContext(context);
            setSurfaceTextureListener(this);
            setOpaque(false);
        }

        @Override public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
            surface = new Surface(texture);
            loop = new RenderLoop(appContext);
            loop.mode(0);
            loop.attach(surface, width, height);
            Trace.log("T05", "texture#available id=" + System.identityHashCode(this) + " " + width + "x" + height);
        }

        @Override public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int width, int height) {
            if (loop != null && surface != null) loop.attach(surface, width, height);
        }

        @Override public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
            if (loop != null) { loop.close(); loop = null; }
            if (surface != null) { surface.release(); surface = null; }
            if (liveSurface != null && liveSurface.get() == this) liveSurface = null;
            return true;
        }

        @Override public void onSurfaceTextureUpdated(SurfaceTexture texture) {}

        @Override protected void onDetachedFromWindow() {
            if (loop != null) { loop.close(); loop = null; }
            if (surface != null) { surface.release(); surface = null; }
            if (liveSurface != null && liveSurface.get() == this) liveSurface = null;
            super.onDetachedFromWindow();
        }
    }

    private HyperOsAod() {}
}
