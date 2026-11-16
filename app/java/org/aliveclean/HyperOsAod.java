package org.aliveclean;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.SurfaceTexture;
import android.os.SystemClock;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;

final class HyperOsAod {
    private static final String TAG = "AliveHSAod";
    private static java.lang.ref.WeakReference<AodSurface> liveSurface;
    private static java.lang.ref.WeakReference<ViewGroup> scheduledRoot;
    private static long attachGeneration;
    private static long scheduledAt;
    private static final long ATTACH_DELAY_MS = 950;
    private static final long FADE_MS = 180;
    /** AODView 在 HyperOS 上晚于 dreaming 才 inflate，注入必须有界重试而不是一次放弃。 */
    private static final long INJECT_RETRY_MS = 200;
    private static final int INJECT_RETRIES = 25;
    /** 首帧没打出来时（EGL/纹理异常）不能永远停在 alpha=0，否则等于"什么都没有"。 */
    private static final long ALPHA_WATCHDOG_MS = 1500;
    private static final android.os.Handler MAIN =
            new android.os.Handler(android.os.Looper.getMainLooper());
    private static int injectAttempt;
    private static final float BALL_CENTER_Y = 0.165f;
    private static final float BALL_RADIUS = 0.22f;
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
                    ViewGroup instance = (ViewGroup) param.thisObject;
                    // 先记下来：attach() 可能因为用户未解锁等原因中止，但实例仍然可用。
                    cachedRoot = new java.lang.ref.WeakReference<>(instance);
                    aodRoot = cachedRoot;
                    scheduleAttach(instance);
                }
            });
            Trace.log("T01", "hooks installed for " + name);
            installed = true;
        }
        if (!installed) AliveLog.i(TAG, "no AOD view class in this process");
        // 唤醒最早的一道信号：DozeServiceHostInjector 的 wakefulness 观察者。
        // onDreamingStopped / ACTION_SCREEN_ON 都可能晚于系统"截图并开始解锁动画"，
        // 于是 AOD 里的球被一起带进转场，看起来就是双层（2026-11-16 实测录像确认修复不到位）。
        try {
            Class<?> observer = Xp.findClassIfExists(
                    "com.android.keyguard.injector.DozeServiceHostInjector$mWakefulnessObserver$1", loader);
            if (observer != null) {
                Xp.findAndHookMethod(observer, "onStartedWakingUp", new Xp.MethodHook() {
                    @Override protected void beforeHookedMethod(Xp.MethodHookParam param) {
                        Trace.log("T14", "wakefulness onStartedWakingUp");
                        detach();
                    }
                });
                AliveLog.i(TAG, "aod wake cleanup hooked");
            } else {
                AliveLog.i(TAG, "no wakefulness observer for AOD cleanup");
            }
        } catch (Throwable error) {
            AliveLog.w(TAG, "aod wake cleanup hook failed", error);
        }
    }

    static void installDoze(ClassLoader loader) {
        Class<?> service = Xp.findClassIfExists("com.android.keyguard.doze.MiuiDozeService", loader);
        if (service == null) { AliveLog.i(TAG, "no MiuiDozeService"); return; }
        Xp.hookAllMethods(service, "onDreamingStarted", new Xp.MethodHook() {
            @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                final android.app.Service svc = (android.app.Service) param.thisObject;
                Trace.log("T07", "dreaming started");
                injectAttempt = 0;
                registerScreenOnCleanup(svc);
                AodOverlay.show(svc);
            }
        });
        Xp.hookAllMethods(service, "onDreamingStopped", new Xp.MethodHook() {
            @Override protected void beforeHookedMethod(Xp.MethodHookParam param) {
                Trace.log("T08", "dreaming stopped");
                injectAttempt = INJECT_RETRIES;
                // before 而不是 after：2026-11-16 用户反馈"息屏→桌面"转场出现双层和残影，
                // 越早把我们的 surface 从 AOD 层级里摘掉，转场越接近"锁屏→桌面"的单层效果。
                AodOverlay.hide();
            }
        });
        // 唤醒瞬间再补一道：ACTION_SCREEN_ON 通常比 doze 回调更早到。
        // 注册放在 onDreamingStarted 里做（install 阶段 Xp.currentApplication() 常为 null）。
        AliveLog.i(TAG, "doze service hook installed");
    }

    private static boolean screenOnRegistered;

    private static void registerScreenOnCleanup(android.content.Context context) {
        if (screenOnRegistered || context == null) return;
        screenOnRegistered = true;
        try {
            context.registerReceiver(new android.content.BroadcastReceiver() {
                @Override public void onReceive(android.content.Context ctx, android.content.Intent intent) {
                    if (android.content.Intent.ACTION_SCREEN_ON.equals(intent.getAction())) {
                        detach();
                    }
                }
            }, new android.content.IntentFilter(android.content.Intent.ACTION_SCREEN_ON));
            AliveLog.i(TAG, "aod screen-on cleanup registered");
        } catch (Throwable error) {
            AliveLog.w(TAG, "aod screen-on cleanup failed", error);
        }
    }

    private static java.lang.ref.WeakReference<ViewGroup> aodRoot;
    /**
     * AODView 实例缓存在这里。它在开机时就会构造一次（此时用户可能还没解锁），
     * 而 attach() 会因为读不到凭证加密存储的 SharedPreferences 而中止。
     * 以前 detach() 会把 aodRoot 清空，且 AODView 不会重建 —— 于是整台机器
     * 再也不会注入任何东西（2026-11-15 实测就是这个形态）。
     */
    private static java.lang.ref.WeakReference<ViewGroup> cachedRoot;
    private static int sceneRetries;
    private static final int SCENE_RETRIES = 15;
    private static final long SCENE_RETRY_MS = 2000;
    private static final int SCENE_UNKNOWN = -1;
    private static final int SCENE_ON = 1;
    private static final int SCENE_OFF = 0;

    /** 当前可用的 AODView；优先用已挂上的，退回到开机时缓存的实例。 */
    private static ViewGroup rootView() {
        ViewGroup current = aodRoot == null ? null : aodRoot.get();
        if (current != null && current.isAttachedToWindow()) return current;
        ViewGroup cached = cachedRoot == null ? null : cachedRoot.get();
        if (cached != null && cached.isAttachedToWindow()) {
            aodRoot = cachedRoot;
            return cached;
        }
        return current != null ? current : cached;
    }

    static boolean hasInjectedView() {
        ViewGroup root = aodRoot == null ? null : aodRoot.get();
        return root != null && root.findViewWithTag(TAG) != null;
    }

    static boolean injectIntoAod() {
        ViewGroup root = rootView();
        if (root == null) {
            // 以前这里直接 return false，而 AodOverlay 只在 950ms 后试一次；
            // AODView 若晚于这个窗口 inflate，整轮息屏就再也没有注入机会。
            if (injectAttempt < INJECT_RETRIES) {
                injectAttempt++;
                Trace.log("T06", "inject waiting for AODView try=" + injectAttempt);
                MAIN.postDelayed(new Runnable() {
                    @Override public void run() {
                        if (injectAttempt <= 0) return;
                        injectIntoAod();
                    }
                }, INJECT_RETRY_MS);
            } else {
                AliveLog.once(TAG, "inject-giveup",
                        "AOD injection gave up: no AODView after " + INJECT_RETRIES + " tries");
                Trace.log("T06", "inject gave up");
            }
            return false;
        }
        injectAttempt = 0;
        AodSurface existing = liveSurface == null ? null : liveSurface.get();
        if (root.findViewWithTag(TAG) == null || existing == null
                || !existing.isAttachedToWindow() || !existing.isShown()) {
            scheduleAttach(root);
        }
        return true;
    }

    private static void scheduleAttach(final ViewGroup root) {
        if (root == null) return;
        long now = SystemClock.uptimeMillis();
        ViewGroup pending = scheduledRoot == null ? null : scheduledRoot.get();
        if (pending == root && now - scheduledAt < ATTACH_DELAY_MS) return;
        scheduledRoot = new java.lang.ref.WeakReference<>(root);
        scheduledAt = now;
        final long generation = ++attachGeneration;
        root.postDelayed(new Runnable() {
            @Override public void run() {
                if (generation != attachGeneration) return;
                ViewGroup current = scheduledRoot == null ? null : scheduledRoot.get();
                if (current != root) return;
                scheduledRoot = null;
                try {
                    attach(root);
                } catch (Throwable error) {
                    AliveLog.e(TAG, "attach failed", error);
                }
            }
        }, ATTACH_DELAY_MS);
    }

    /**
     * Removes the scene surface that was injected into MIUI's own AODView.  This is
     * separate from AodOverlay's WindowManager view: when the AOD is switched off (or
     * the system wallpaper is no longer ours) the injected child otherwise stays in the
     * AOD hierarchy and keeps drawing the default ball.
     */
    static void detach() {
        attachGeneration++;
        scheduledRoot = null;
        // 注意：不要清 aodRoot / cachedRoot。AODView 实例往往整机只构造一次，
        // 清掉以后就再也没有重挂的机会（2026-11-15 实测踩过）。
        injectAttempt = INJECT_RETRIES;
        sceneRetries = 0;
        AodSurface surface = liveSurface == null ? null : liveSurface.get();
        liveSurface = null;
        if (surface == null) return;
        try {
            // 先把这一层彻底"熄掉"再摘：TextureView 的最后一帧会留在图层里，
            // 直接 removeView 会在转场后留下一块残影（2026-11-16 用户实测）。
            surface.setAlpha(0f);
            surface.setVisibility(View.INVISIBLE);
            if (surface.getParent() instanceof ViewGroup) {
                ((ViewGroup) surface.getParent()).removeView(surface);
            }
        } catch (Throwable error) {
            AliveLog.w(TAG, "scene detach failed", error);
        }
    }

    private static void attach(ViewGroup root) {

        final long generation = attachGeneration;
        Context context = root.getContext();
        aodRoot = new java.lang.ref.WeakReference<>(root);
        if (!aodEnabled(context)) {
            detach();
            AliveLog.once(TAG, "aod-disabled", "AOD scene skipped: AOD is disabled");
            return;
        }
        int scene = aodSceneState(context);
        if (scene == SCENE_UNKNOWN) {
            // 用户还没解锁：凭证加密存储不可用，读不到场景设置。这不是"关掉了场景"，
            // 过一会儿再试；绝不能像以前那样直接 detach 把注入机会丢掉。
            if (++sceneRetries <= SCENE_RETRIES) {
                AliveLog.once(TAG, "aod-scene-locked",
                        "AOD scene settings unavailable (user locked?), retrying");
                root.postDelayed(new Runnable() {
                    @Override public void run() {
                        if (generation != attachGeneration) return;
                        attach(root);
                    }
                }, SCENE_RETRY_MS);
            } else {
                AliveLog.once(TAG, "aod-scene-giveup",
                        "AOD scene settings still unavailable after " + SCENE_RETRIES + " retries");
            }
            return;
        }
        if (scene == SCENE_OFF) {
            detach();
            return;
        }
        if (!AodWallpaperMode.alive(context)) {
            detach();
            AliveLog.once(TAG, "aod-wallpaper-foreign", "AOD scene skipped: wallpaper is not ours");
            return;
        }
        AliveLog.reset("aod-wallpaper-foreign");

        AodSurface existing = liveSurface == null ? null : liveSurface.get();
        if (existing != null && existing.isAttachedToWindow() && existing.isShown()) {
            Trace.log("T03", "attach skipped, live surface id=" + System.identityHashCode(existing));
            return;
        }
        if (existing != null) {
            Trace.log("T03", "attach replacing hidden surface id=" + System.identityHashCode(existing));
            if (existing.getParent() instanceof ViewGroup) {
                ((ViewGroup) existing.getParent()).removeView(existing);
            }
            liveSurface = null;
        }
        View stale = root.findViewWithTag(TAG);
        if (stale != null) {
            if (stale.getParent() instanceof ViewGroup) {
                ((ViewGroup) stale.getParent()).removeView(stale);
            }
            if (stale == existing) liveSurface = null;
        }

        ViewGroup target = findInnerAod(root);
        if (target == null) {

            AliveLog.once(TAG, "aod-view-pending", "inner AODView not up yet, waiting");
            root.postDelayed(new Runnable() {
                @Override public void run() {
                    // detach()/a newer attach may have replaced the root during
                    // these 200ms.  Never inject into a stale AOD hierarchy.
                    if (generation != attachGeneration) return;
                    ViewGroup current = aodRoot == null ? null : aodRoot.get();
                    if (current != root) return;
                    injectIntoAod();
                }
            }, 200);
            return;
        }
        AodSurface surface = new AodSurface(context);
        surface.setTag(TAG);
        surface.setAlpha(0f);
        // R.layout.aod_mode_layout has an opaque black View at index 0.  Sit
        // directly above it, but below the clock/content container, otherwise
        // the ball is painted behind the AOD background.
        int index = Math.min(1, target.getChildCount());
        target.addView(surface, index, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        if (Diagnostics.TRACE) {
            StringBuilder order = new StringBuilder();
            for (int i = 0; i < target.getChildCount(); i++) {
                View child = target.getChildAt(i);
                order.append(i).append(':').append(child.getClass().getSimpleName())
                        .append(child == surface ? "(ours)" : "").append(' ');
            }
            Trace.log("T12", "aod children of " + target.getClass().getSimpleName()
                    + " count=" + target.getChildCount() + " [" + order + "]"
                    + " hostAlpha=" + target.getAlpha() + " hostShown=" + target.isShown());
        }
        liveSurface = new java.lang.ref.WeakReference<>(surface);
        AliveLog.reset("aod-view-pending");
        Trace.log("T04", "surface#create id=" + System.identityHashCode(surface)
                + " host=" + System.identityHashCode(target) + " children=" + target.getChildCount());
        surface.postDelayed(new Runnable() {
            @Override public void run() {
                Trace.log("T13", "surface state alpha=" + surface.getAlpha()
                        + " shown=" + surface.isShown()
                        + " size=" + surface.getWidth() + "x" + surface.getHeight()
                        + " parent=" + (surface.getParent() == null ? "null"
                        : surface.getParent().getClass().getSimpleName()));
            }
        }, 900);
        // 以前 supervise() 是死代码：surface 一旦被 AODView 移除/换宿主就永久丢失，
        // 表现就是"转场有球、稳定后只剩时钟"。
        supervise(root, surface);

    }

    private static void supervise(final ViewGroup root, final AodSurface surface) {
        final long generation = attachGeneration;
        Trace.log("T09", "supervise start id=" + System.identityHashCode(surface));
        root.postDelayed(new Runnable() {
            int tries;
            int misses;

            @Override public void run() {

                // detach() 或新一轮 attach 之后这个 surface 已作废，不能再塞回旧层级。
                if (generation != attachGeneration
                        || liveSurface == null || liveSurface.get() != surface) {
                    Trace.log("T09", "supervise stop (replaced) tries=" + tries);
                    return;
                }
                if (surface.getParent() == null) {
                    if (++misses < 8) { next(); return; }
                    ViewGroup host = findInnerAod(root);
                    ViewGroup target = host != null ? host : root;
                    target.addView(surface, Math.min(1, target.getChildCount()),
                            new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.MATCH_PARENT));
                    misses = 0;
                    AliveLog.i(TAG, "scene re-attached after misses");
                } else {
                    misses = 0;
                    ViewGroup inner = findInnerAod(root);
                    if (inner != null && surface.getParent() != inner) {
                        ((ViewGroup) surface.getParent()).removeView(surface);
                        inner.addView(surface, Math.min(1, inner.getChildCount()),
                                new ViewGroup.LayoutParams(
                                        ViewGroup.LayoutParams.MATCH_PARENT,
                                        ViewGroup.LayoutParams.MATCH_PARENT));
                        AliveLog.i(TAG, "scene moved into inner AODView");
                    }
                }
                next();
            }

            private void next() {
                if (++tries < 100) root.postDelayed(this, 300);
                else AliveLog.i(TAG, "supervise done, parent="
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
            AliveLog.w(TAG, "module context unavailable, using the host context", unavailable);
            return base;
        }
    }

    static boolean aodEnabled(Context base) {
        try {
            return android.provider.Settings.Secure.getInt(
                    base.getContentResolver(), "doze_always_on", 0) == 1;
        } catch (Throwable unavailable) {
            AliveLog.w(TAG, "cannot read AOD switch; treating as off", unavailable);
            return false;
        }
    }

    /**
     * @return {@link #SCENE_ON} / {@link #SCENE_OFF} / {@link #SCENE_UNKNOWN}
     *         （UNKNOWN = 用户未解锁导致凭证加密存储不可用，属临时状态）
     */
    private static int aodSceneState(Context base) {
        try {
            Context app = moduleContext(base);
            android.content.SharedPreferences prefs =
                    app.getSharedPreferences(SceneOptions.APPLIED, 0);
            return prefs.getInt("aod", 0) != -1 ? SCENE_ON : SCENE_OFF;
        } catch (IllegalStateException locked) {
            return SCENE_UNKNOWN;
        } catch (Throwable unavailable) {
            AliveLog.w(TAG, "cannot read scene settings", unavailable);
            return SCENE_OFF;
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
            setOpaque(true);
            setOutlineProvider(new ViewOutlineProvider() {
                @Override public void getOutline(View view, Outline outline) {
                    int width = view.getWidth();
                    int height = view.getHeight();
                    float radius = BALL_RADIUS * width;
                    int cx = width / 2;
                    int cy = (int) (BALL_CENTER_Y * height);
                    outline.setOval((int) (cx - radius), (int) (cy - radius),
                            (int) (cx + radius), (int) (cy + radius));
                }
            });
            setClipToOutline(true);
        }

        @Override public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
            surface = new Surface(texture);
            loop = new RenderLoop(appContext);
            loop.onFirstFrame(new Runnable() {
                @Override public void run() {
                    post(new Runnable() {
                        @Override public void run() {
                            animate().alpha(1f).setDuration(FADE_MS).start();
                        }
                    });
                }
            });
            loop.mode(0);
            loop.attach(surface, width, height);
            Trace.log("T05", "texture#available id=" + System.identityHashCode(this) + " " + width + "x" + height);
            // 首帧回调若因 EGL/纹理异常没来，alpha 会永远停在 0，肉眼就是"息屏没有球"。
            // 看门狗只保证内容可见，不掩盖真实失败（仍会打日志）。
            postDelayed(new Runnable() {
                @Override public void run() {
                    if (getAlpha() >= 1f) return;
                    AliveLog.i(TAG, "alpha watchdog fired, alpha=" + getAlpha());
                    Trace.log("T05", "alpha watchdog forced");
                    animate().alpha(1f).setDuration(FADE_MS).start();
                }
            }, ALPHA_WATCHDOG_MS);
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
