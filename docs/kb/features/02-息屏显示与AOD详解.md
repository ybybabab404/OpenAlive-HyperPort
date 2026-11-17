# 02 息屏显示（AOD）详解

> 本文是"息屏显示 / AOD"这条功能最详尽的一份实现文档：链路、真实代码、开关语义、
> 失败模式、验证方法、版本线索。
>
> - **代码基线**：2026-11-16 工作区快照（`app/java/org/aliveclean/*.java`）。行号以该快照为准，
>   后续重构可能偏移，**以类名/方法名/常量名为准**。
> - **代码片段**：全部从当前工作区逐字摘录，只做缩进保留，未改写内容。
> - **日志/截图证据**：不是本次新采的，一律注明来源章节（`../troubleshooting/02-已定位Bug档案.md` §x.y）。
>   本文不编造 logcat 输出；没有出处的结论写【证据不足】。
> - **证据级别**：沿用 `README.md` §2 的分级——【源码】【实测】【日志】【推断】【证据不足】。
>   本文出现的"源码"表示"在当前工作区源码里可直接读到"。

---

## 1. 一句话原理

**AOD 是"我们把壁纸 Surface 注入系统 `com.miui.aod` 的 `AODView`"，时钟/通知/日期仍由系统绘制，
我们只负责在它下面（黑底之上、时钟容器之下）画壁纸缩成的球。**

拆开说三句：

1. `HyperOsAod` 在 SystemUI 进程里 hook `AODView` 的构造器，拿到实例并缓存；
2. 熄屏时把一块 `TextureView`（`AodSurface`）塞进 `AODView` 的 index 1，用**椭圆 outline 裁剪**，
   运行时只有"黑底 + 顶部一个球"；
3. 这块 surface 里跑的是我们自己的 `RenderLoop`，`mode(0)` 走 AOD 构图
   （壁纸缩小成球，`AOD frame lensScale=0.22 …` 那一档）。

另一个容易混淆的事实（`../troubleshooting/02-已定位Bug档案.md` §12.4【实测】）：
**壁纸引擎自己也会画 AOD 构图**（`CleanWallpaper` 收到 `goingtosleep` / `ambient` 后 `send(0)`）。
把 `scene.xml` 的 `aod` 改成 `-1` 关掉我们这条注入链路后，用户复测"球还在"——说明
"息屏里的球"存在两条来源：壁纸引擎的 AOD 构图（常开），和我们注入的 `AodSurface`（补丁）。
注入那层的存在意义是"全屏 AOD 没生效时，让 AOD 界面里也有我们画的东西"。
这一点在排查"有没有注入"时必须先分清，否则会拿壁纸引擎的画面当注入成功的证据。

---

## 2. 名词与键值速查

| 名字 | 位置 | 含义 |
|---|---|---|
| `doze_always_on` | Settings.Secure | 系统"息屏显示"总开关，`1`=开。模块只读（`HyperOsAod.aodEnabled`） |
| `full_screen_aod_on` | Settings.Secure | AOD 样式选择：`1`=「和锁屏样式一致」= 全屏 AOD；`0`=「自定义」。**模块不再改写**（见 §4） |
| `full_screen_aod_support` | Settings.Secure | 由 MIUI `MiuiFullAodManager.updateSettings()` 计算写入，我们从不写 |
| `alive_aod_restore_value` / `alive_aod_restore_set` / `alive_aod_active` | Settings.Secure | 历史 marker，当前代码只清不写（见 §4.4） |
| `aod` | `scene.xml`（`ScenePreferences` 名 `scene`） | 我们的 AOD 样式档位，`-1`=关，`0`=启航，`1..5`=照片样式，`101`=全屏灰度（见 §4.1） |
| `AODView` | `com.miui.aod.AODView` | 系统 AOD 的容器 ViewGroup，注入宿主 |
| `AodSurface` | `HyperOsAod.java` 内部类 | 我们注入的 `TextureView` |
| `mode` | `RenderLoop` | 构图态：`0`=息屏/AOD，`1`=桌面，`2`=锁屏 |
| `TRACE` / `T0x` | `Diagnostics.TRACE` / `Trace.log` | 注入链路的阶段日志开关，编号表见 §9 |

---

## 3. 完整链路

### 3.1 总览

```text
[一次性安装]
  CleanHooks.handle(包名)
    ├─ com.miui.aod      → AodSettingsGuard.install / AodPreviewHider.install
    │                      AodWallpaperPreview.install / AodFullScreen.install(loader, true)
    ├─ com.android.systemui → AodFullScreen.install(loader, false)
    │                      HyperOsAod.install(loader) / HyperOsAod.installDoze(loader)
    └─ system_server      → AodWallpaperModeHook.install
                            （+ DeviceIdleDelayHook：进 AOD 时推迟 device-idle）

[用户设置]
  AOD 设置页（com.miui.aod）
    → Utils.isSupportFsAod() 被 AodFullScreen 抬成 true（否则行不出现）
    → AodSettingsGuard 把 Spinner 的 index 1 换成「AliveHS 样式」并 lock
    → 用户选它 → Utils.setFsAodStyle(ctx,1) → MIUI 自己写 full_screen_aod_on=1

[熄屏]
  壁纸命令 android.wallpaper.goingtosleep → CleanWallpaper send(0,true)（mode 2→0 过渡）
  MiuiDozeService.onDreamingStarted
    before: AodFullScreen.hookDoze 强 mFullAodEnable=true
    after : HyperOsAod: injectAttempt=0; AodOverlay.show(svc)
  AodOverlay.show
    → aodEnabled? → 壁纸是我们的? → HyperOsAod.injectIntoAod()
        ├─ 有 AODView（缓存的）→ scheduleAttach → 950ms 后 attach()
        └─ 没有 → 200ms×25 次有界重试；同时 950ms 后兜底 2038 悬浮窗
  attach(AODView)
    → aodEnabled / aodSceneState（三态）/ AodWallpaperMode.alive 三道闸
    → findInnerAod → new AodSurface → addView(index=1) → supervise
  AodSurface.onSurfaceTextureAvailable
    → new RenderLoop(模块 context) → loop.mode(0) → attach(EGL)
    → 首帧 → alpha 0→1（180ms）；1500ms 看门狗兜底
  RenderLoop mode 0：AOD 构图（球）+ vsyncWatchdog 兜底

[亮屏]
  MiuiDozeService.onDreamingStopped（after）
    → T08 dreaming stopped；injectAttempt=INJECT_RETRIES；AodOverlay.hide()
    → HyperOsAod.detach()：alpha=0 → INVISIBLE → removeView
```

### 3.2 第一段：`CleanHooks` 按进程分发

`app/java/org/aliveclean/CleanHooks.java:88-105`（只列 AOD 相关行）：

```java
        boolean systemUi = "com.android.systemui".equals(packageName);
        boolean miuiAod = "com.miui.aod".equals(packageName);
        boolean themeManager = "com.android.thememanager".equals(packageName);
        ...
        if (miuiAod) step("aod settings hotfix", () -> AodSettingsGuard.install(loader));
        if (miuiAod) step("aod preview hide", () -> AodPreviewHider.install(loader));
        if (miuiAod) step("aod wallpaper preview", () -> AodWallpaperPreview.install(loader));
        if (systemUi) step("aod full screen", () -> AodFullScreen.install(loader, false));
        if (miuiAod) step("aod full screen", () -> AodFullScreen.install(loader, true));
        if (systemUi) step("hyperos aod", () -> HyperOsAod.install(loader));
        ...
        if (systemUi) step("hyperos doze", () -> HyperOsAod.installDoze(loader));
```

要点：

- **注入链只在 SystemUI 进程**（`HyperOsAod`）；`com.miui.aod` 里只有设置页/预览相关的 hook。
  所以在 `com.miui.aod` 进程里找 `T04` 日志是找错的。
- system_server 只挂 `AodWallpaperModeHook`（壁纸变更 → AOD 模式恢复判定）。
- 改完代码必须**重启 SystemUI 和 com.miui.aod**（LSPosed 作用域 `system` 改动还要重启手机），
  否则看到的是旧行为（`../troubleshooting/02-已定位Bug档案.md` §3.3 C4【推断/环境类】）。

### 3.3 第二段：设置页里的「AliveHS 样式」

`AodSettingsGuard` 做三件事，缺一不可：

1. 让这一行**存在**：由 `AodFullScreen` 在 `com.miui.aod` 进程把 `Utils.isSupportFsAod()`
   抬成 true（`AodFullScreen.java:186-203`）。
2. 让这一行**显示我们的标签**：把原生 adapter 的 index 1 就地换成「AliveHS 样式」。
3. 让这一行**改不动**：`lock(row, spinner)`（2026-11-16 12:3x 恢复，原因见 §5 失败模式 F12）。

`app/java/org/aliveclean/AodSettingsGuard.java:53-62`：

```java
            Xp.findAndHookMethod(utils, "setFsAodStyle", Context.class, int.class,
                    new Xp.MethodHook() {
                        @Override
                        protected void beforeHookedMethod(Xp.MethodHookParam param) {
                            Context context = (Context) param.args[0];
                            if (context == null || !AodWallpaperMode.uiAlive(context)) return;
                            param.args[1] = 1;
                        }
                    });
```

注意：这里**只改入参**，不写 `full_screen_aod_on`。真正落盘的仍是 MIUI 原生
`Utils.setFsAodStyle()`。这是"模块不碰用户设置"的实现方式：用户点我们那一项，等价于
"选和锁屏样式一致"，由系统写 1。

`app/java/org/aliveclean/AodSettingsGuard.java:352-356`：

```java
            // MIUI uses index 1 for "和锁屏样式一致" (full AOD).  Replace that item
            // in place instead of appending a fourth position: updateSpinnerState()
            // selects Utils.getFsAodStyle() (1), and appending would make our
            // after-hook select the last item again, producing an endless flicker.
            this.labelPosition = base.getCount() > 1 ? 1 : 0;
```

> 更正：`01-功能总表与实现.md` §2.2 里贴的 `LabelAdapter` 代码是**旧版**
> （`getCount() { return base.getCount() + 1; }`、`getItem` 做偏移）。当前实现是
> **就地替换 index 1**，`getCount()` 直接返回 `base.getCount()`。以本节代码为准。

### 3.4 第三段：`AodFullScreen` 补足"全屏 AOD 能力"

两个消费者（`AodFullScreen.java:14-24` 类注释已经写明）：

| 消费者 | 需要的条件 | 我们 hook 的位置 |
|---|---|---|
| system_server `MiuiFullAodManager` | `isDeviceSupport` / `isWallpaperTypeSupport` / `isFullAodSupport` 为 true，`MiuiConfigs.SUPPORT_FULL_AOD` 为 true | 构造器、`onWallpaperChanged`、`updateSettings`、`SystemUIInitializer.init` |
| `com.miui.aod` 设置页 | `Utils.isSupportFsAod()` 为 true | `isSupportFsAod` / `isInnerScreenSupportFsAod` |
| `MiuiDozeService` | `DozeServiceHostInjector.mFullAodEnable` | `onDreamingStarted` **before** |

判定口径在 `AodFullScreen.java:469-473`：

```java
    private static boolean controlsFullAod(Context context) {
        return context != null
                && AodWallpaperMode.wantsFullAod(context)
                && HyperOsAod.aodEnabled(context);
    }
```

即：**"用户选了和锁屏一致 + 用户开着息屏显示" 才补能力**，与"壁纸是不是我们的"无关
（2026-11-16 00:0x 的设计变更，`../troubleshooting/02-已定位Bug档案.md` §11.3【实测】）。
选「自定义」时走 `reconcileNative()` 把能力交还 MIUI。

`AodFullScreen.java:351-380`（`forceManager` 的两段核心赋值，中间为注释/日志，已省略）：

```java
        setBool(manager, "isDeviceSupport", true);
        setBool(manager, "isWallpaperTypeSupport", true);
        setBool(manager, "isFullAodSupport", true);
        ...
        setBool(manager, "mAodEnable", true);
        setBool(manager, "mAodFullScreenEnable", true);
```

`mAodEnable` 也要强，因为开机时若 `SUPPORT_FULL_AOD` 为 false，manager 的
`doze_always_on` 观察者没注册，`mAodEnable` 会一直是 false（源码注释，`AodFullScreen.java:365-372`）。

### 3.5 第四段：注入点 —— AODView 构造器 hook + 实例缓存

`app/java/org/aliveclean/HyperOsAod.java:21-34`（延迟与常量）：

```java
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
```

**两个类名都要 hook**（AOSP keyguard 的 `AodView` 与 MIUI 的 `AODView`）。
`HyperOsAod.java:36-52`：

```java
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
    }
```

"先缓存，再 attach" 是 2026-11-15 的关键修复，注释写在字段声明上
（`HyperOsAod.java:78-83`）：

```java
    /**
     * AODView 实例缓存在这里。它在开机时就会构造一次（此时用户可能还没解锁），
     * 而 attach() 会因为读不到凭证加密存储的 SharedPreferences 而中止。
     * 以前 detach() 会把 aodRoot 清空，且 AODView 不会重建 —— 于是整台机器
     * 再也不会注入任何东西（2026-11-15 实测就是这个形态）。
     */
    private static java.lang.ref.WeakReference<ViewGroup> cachedRoot;
```

`rootView()` 的取用顺序（`HyperOsAod.java:95-107`）：优先"已 attached 的 aodRoot"，
否则退回"已 attached 的 cachedRoot"，都不 attached 时仍返回对象（可能未挂载）。

### 3.6 第五段：doze 起点与兜底悬浮窗

`app/java/org/aliveclean/HyperOsAod.java:56-73`：

```java
    static void installDoze(ClassLoader loader) {
        Class<?> service = Xp.findClassIfExists("com.android.keyguard.doze.MiuiDozeService", loader);
        if (service == null) { AliveLog.i(TAG, "no MiuiDozeService"); return; }
        Xp.hookAllMethods(service, "onDreamingStarted", new Xp.MethodHook() {
            @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                final android.app.Service svc = (android.app.Service) param.thisObject;
                Trace.log("T07", "dreaming started");
                injectAttempt = 0;
                AodOverlay.show(svc);
            }
        });
        Xp.hookAllMethods(service, "onDreamingStopped", new Xp.MethodHook() {
            @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                Trace.log("T08", "dreaming stopped");
                injectAttempt = INJECT_RETRIES;
                AodOverlay.hide();
            }
        });
```

`AodOverlay` 不是"老代码遗留"，它每次息屏都会被调用（`AodOverlay.java:18-40`）：

```java
    static void show(Service service) {
        Trace.log("T10", "overlay#show enter");
        if (!HyperOsAod.aodEnabled(service)) {
            AliveLog.i(TAG, "overlay skipped: AOD is disabled");
            hide();
            return;
        }
        if (!ownsWallpaper(service)) {
            AliveLog.i(TAG, "overlay skipped: wallpaper is not ours");
            hide();
            return;
        }
        if (view != null) { Trace.log("T10", "overlay#show already"); return; }
        if (HyperOsAod.injectIntoAod()) { AliveLog.i(TAG, "injected into the AOD view"); return; }
        final int token = ++showToken;
        new android.os.Handler(service.getMainLooper()).postDelayed(new Runnable() {
            @Override public void run() {
                if (token != showToken || view != null) return;
                add(service);
            }
        }, ATTACH_DELAY_MS);
    }
```

`add()` 用的是 2038 型窗口（`AodOverlay.java:41-59`，`(2038)` 即 `TYPE_APPLICATION_OVERLAY`），
这条兜底在 HyperOS 4.0.0.33 上**不可用**，见 §5 F2。

### 3.7 第六段：`injectIntoAod()` 的有界重试

`app/java/org/aliveclean/HyperOsAod.java:109-137`：

```java
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
```

**读这段必须注意返回值语义**：`root != null` 就 `return true`（`HyperOsAod.java:136`），
**完全不检查 surface 有没有真的加进 AODView**。所以 `injected into the AOD view` 只表示"拿得到 AODView 实例"，
不等于注入成功（这是 2026-11-15 复核的结论，`01-*` §3.6 第 3 条；本文 §5 F5 展开）。

另外，重试（200ms×25，最长 5s）与 950ms 的 overlay 兜底是**并行的**：`injectIntoAod()` 第一次
就返回 false 时，`AodOverlay` 照样会在 950ms 后尝试加 2038 窗口。

### 3.8 第七段：`attach()` —— 三道闸 + 三态场景 + 注入 index 1

`app/java/org/aliveclean/HyperOsAod.java:191-242`（节选，逐字）：

```java
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
```

三态来源（`HyperOsAod.java:394-406`）：

```java
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
```

- `SCENE_ON`（1）/ `SCENE_OFF`（0）/ `SCENE_UNKNOWN`（-1）；
- 只有 `aod == -1` 才算"用户关了息屏场景"，其余值（含 `101`）都算开；
- `SCENE_UNKNOWN` 的触发条件是 `IllegalStateException`——锁定状态下 CE 存储
  （credential encrypted）不可用，`getSharedPreferences` 抛这个异常；
- 重试参数：`SCENE_RETRIES = 15`、`SCENE_RETRY_MS = 2000`（约 30 秒窗口）。

注入位置（`HyperOsAod.java:270-276`）：

```java
        // R.layout.aod_mode_layout has an opaque black View at index 0.  Sit
        // directly above it, but below the clock/content container, otherwise
        // the ball is painted behind the AOD background.
        int index = Math.min(1, target.getChildCount());
        target.addView(surface, index, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
```

该假设已被真机实测确认（`../troubleshooting/02-已定位Bug档案.md` §9.5 证据 2【日志】，来源同文件）：

```text
[HA][T12] aod children of AODView count=5
  [0:View  1:AodSurface(ours)  2:AodContainerView  3:NotificationAnimationView  4:ViewStub]  hostAlpha=0.0 hostShown=true
```

即 index 0 是不透明黑底，我们的 surface 在黑底之上、`AodContainerView`（时钟容器）之下。

`findInnerAod()`（`HyperOsAod.java:354-370`）只按类名识别（全名含 `com.miui.aod.AODView`，
或简单名 `AODView`），找不到时 `attach()` 会 `postDelayed(…, 200)` 再调一次 `injectIntoAod()`
（带 generation 校验，`HyperOsAod.java:252-268`）。

### 3.9 第八段：`AodSurface` —— 椭圆裁剪、alpha 看门狗、supervise

**椭圆裁剪**（`HyperOsAod.java:418-429`），球心纵向 0.165、半径 0.22×宽：

```java
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
```

注意裁剪只是"把这块不透明 surface 裁成椭圆"，**球里的内容**是 `RenderLoop` `mode(0)` 的
AOD 构图（启航样式下壁纸缩成 0.22 的球，见 §3.10）。两者叠加才得到"黑底 + 顶部一个球"。

**首帧淡入 + 看门狗**（`HyperOsAod.java:432-456`）：

```java
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
```

看门狗只保证"可见"，不保证"有内容"——内容没画出来时 alpha=1 会让用户看到一块不透明黑。
这就是 §5 F3/F4 的区分点。

**supervise（重挂）**（`HyperOsAod.java:306-350` 节选）：

```java
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
                    ...
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
```

参数含义：300ms 一轮、最多 100 轮（≈30 秒）；`getParent()==null` 要**连续 8 次 miss**
才重挂；parent 不是 inner AODView 时"搬"到 inner。

**detach（摘除）**（`HyperOsAod.java:168-190`）：

```java
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
            // 先把这一层彻底"熄掉"再摘：TextureView 的最后一帧可能留在图层里，
            // 直接 removeView 有几率在下一帧留残影。
            surface.setAlpha(0f);
            surface.setVisibility(View.INVISIBLE);
            if (surface.getParent() instanceof ViewGroup) {
                ((ViewGroup) surface.getParent()).removeView(surface);
            }
        } catch (Throwable error) {
            AliveLog.w(TAG, "scene detach failed", error);
        }
    }
```

`attachGeneration++` 是整套逻辑的失效令牌：所有延迟任务（attach 重试、supervise、
`findInnerAod` 失败后的接力）都在执行前比对它，避免把新 surface 塞进旧层级。

### 3.10 第九段：`RenderLoop` 的 `mode(0)` 与 vsync 看门狗

`mode` 语义：**0=AOD/息屏，1=桌面，2=锁屏**（`RenderLoop.java:182` 的
`if(previous!=next)AliveLog.i("AliveClean","scene motion "+previous+"->"+next…`）。

`mode()` 进入 AOD 时做三件事（`RenderLoop.java:174-184`）：

```java
        if(mode!=next){stateReported=false;frameSample.clear();if(next==0)aodMotion.enter(System.nanoTime());else aodMotion.leave();}
        ...
        mode=next;pacer.doze(next==0);
```

`pacer.doze(true)` 把帧节奏切到 doze 档。

> 2026-11-16：原来这里还会打开 1.5 秒的帧间隔探针（`probeUntil` / `probe frame dt=` / `probe vsync gap=`），
> 现已整体删除——它不该常驻，而且探针过期没清零时会每帧写一行把现场日志冲掉。需要临时测帧节奏时，
> 按 `docs/kb/build/02-代码注释规范.md` §4 的要求加**带开关、带到期清零**的临时探针，用完删掉。

**doze 期间 Choreographer 可能整段不发 vsync**，这是"surface 在、画面永远不出"的真因，
修法写在 `RenderLoop.java:215-231`（逐字）：

```java
    private void schedule(){
        if(!((renderingVisible()||forceFrame)&&(scene!=0||cosmic!=null||vivo!=null)))return;
        if(choreographer!=null&&!framePosted){framePosted=true;choreographer.postFrameCallback(frame);}
        // Choreographer 在 doze/息屏期间可能整段不发 vsync；一旦回调不来，framePosted 会
        // 永久卡在 true，之后 schedule() 全是空操作 —— 表现就是"surface 在、画面永远不出"。
        // 这里兜一个 120ms 的看门狗：只在 vsync 真的没来时直接渲染一帧。
        handler.removeCallbacks(vsyncWatchdog);
        handler.postDelayed(vsyncWatchdog,120);
    }
    private final Runnable vsyncWatchdog=()->{
        if(!framePosted)return;
        framePosted=false;
        if(!(renderingVisible()||forceFrame)||(scene==0&&cosmic==null&&vivo==null))return;
        AliveLog.i("AliveClean","vsync watchdog: drawing without Choreographer mode="+mode);
        try{frameTime=android.os.SystemClock.uptimeMillis()*1_000_000L;render(frameTime);}
        catch(Throwable error){AliveLog.w("AliveClean","vsync watchdog render failed",error);}
    };
```

**cosmic 调色板必须跟 mode 走**（`RenderLoop.java:264-268`）：

```java
            // CosmicMotion ships three palettes (aod/keyguard/unlock); the aod one draws the
            // wallpaper as a small sphere on black, which is exactly what the always-on screen
            // shows. Forcing the keyguard palette here (85cfa94) made every AOD preview -- the
            // home cards and the editor's 息屏 page -- render the lock screen instead of the ball.
            int palette=mode;
```

这条决定了"主页/编辑页的息屏预览"和"真机息屏"是不是同一个画面（§5 F13）。

### 3.11 熄屏 / 亮屏时序

| 时刻 | 谁 | 动作 | 可见日志 |
|---|---|---|---|
| T-0.9s | 系统 → 壁纸 | `android.wallpaper.goingtosleep` | `wallpaper command=android.wallpaper.goingtosleep` |
| T-0.9s | `CleanWallpaper` | `ambient=true; send(0,true)`，mode 2→0 过渡 | `scene send mode=2->0 animate=true` |
| T0 | `MiuiDozeService.onDreamingStarted` | before：`mFullAodEnable=true` | — |
| T0 | 同上 after | `T07 dreaming started`；`injectAttempt=0`；`AodOverlay.show` | `[HA][T07]`、`[HA][T10] overlay#show enter` |
| T0+ε | `injectIntoAod` | 有缓存实例 → `scheduleAttach` | `injected into the AOD view`（**不代表成功**） |
| T0+ε | `injectIntoAod` | 无实例 → 200ms×25 重试 | `[HA][T06] inject waiting for AODView try=N` |
| T0+0.95s | `attach` | 三道闸 → `findInnerAod` → `addView(index=1)` | `[HA][T12]`、`[HA][T04] surface#create` |
| T0+0.95s | `AodSurface` | texture available → RenderLoop mode(0) | `[HA][T05] texture#available … 1200x2608` |
| 首帧 | RenderLoop | `onFirstFrame` → alpha 0→1 / 180ms | `EGL init requested=…`、`aod photo from provider …` |
| T0+1.85s | `AodSurface` | `[HA][T13] surface state alpha=… shown=… size=… parent=…` | T13 |
| 持续 | `supervise` | 每 300ms 校验 parent，掉出则重挂 | `[HA][T09] supervise start/stop`、`scene re-attached after misses` |
| 亮屏 | `onDreamingStopped` after | `T08`；`injectAttempt=25`；`AodOverlay.hide` → `detach()` | `[HA][T08]`、`[HA][T11] overlay#hide` |

两个必须记住的时序事实：

1. **注入发生在 950ms 之后**，而 MIUI 原生全屏 AOD 的开场动画历史上量到 ≈896ms
   （`../troubleshooting/02-已定位Bug档案.md` §1.4 H5【日志，旧构建】）。所以"转场帧里有球"完全可能来自
   壁纸引擎自己的 AOD 构图，而不是我们注入的这一层。
2. **亮屏摘除挂的是 `afterHookedMethod`**，即 MIUI 处理完之后才摘；历史上试过改 before +
   注册 `ACTION_SCREEN_ON` + `onStartedWakingUp`（`586e7c2`），最终在 `7bad717` 回退
   （`02-*` §12.3–12.6）。当前工作区源码里 `HyperOsAod` **没有** before 钩子、**没有**
   `ACTION_SCREEN_ON` 接收器（全文只有两处 `hookAllMethods(...onDreamingStarted/Stopped,
   ...afterHookedMethod...)`）——【源码】确认已回退。

---

## 4. AOD 样式开关：`aod` / `full_screen_aod_on` / `doze_always_on`

### 4.1 `scene.xml` 里的 `aod`（我们的样式档位）

`scene.xml` 就是 SharedPreferences 文件 `scene`（`SceneOptions.APPLIED = "scene"`），
路径 `/data/data/org.aliveclean/shared_prefs/scene.xml`。取值口径在
`app/java/org/aliveclean/SceneOptions.java:28`：

```java
        aod=cosmic!=0?0:a==-1||a==0||PhotoStyle.supported(a)||a==101?a:0;
```

| `aod` | 含义 | 备注 |
|---|---|---|
| `-1` | **关闭我们的息屏场景** | `HyperOsAod.aodSceneState()` 返回 `SCENE_OFF` → `attach()` 直接 `detach()`，不注入 |
| `0` | 启航样式 | `sailContinuousAod=options.cosmic==0&&options.aod==0&&…`（`RenderLoop.java:260`）；`SceneMotion.lens()` 只在 `aod==0` 走"镜头收缩"转场 |
| `1` | 照片样式 `leave_light`（`PhotoStyle`）| `pairedFrame()` 只在 `aod==1` 且 `frame_pair` 时成立 |
| `2`..`5` | 照片样式 `moon` / `door` / `mountain` / `galaxy` | 见 `PhotoStyle.java:27-31` |
| `101` | 全屏灰度 AOD | `RenderLoop.java:308` 加载 `shader/photo/aod/fullAod/gray.png`；`SceneMotion` 走 `full(tr,next)` |
| 其它值 | 运行时归 0 | `SceneOptions` 的最后兜底 |

两条容易被忽略的规则：

- **`cosmic != 0` 时 `aod` 恒为 0**：cosmic 壁纸自己带 AOD 调色板，我们的样式档位不参与。
- **归零不等于关闭**：非法值会被归一成 `0`（启航），**只有 `-1` 是关闭**。所以"改 scene.xml
  改成别的数字"不会关掉注入，必须写 `-1`（`02-*` §12.4 的隔离实验就是这么做的）。

写入者：模块自己的设置页 `MainActivity.commitDraft()` →
`chosen.save(getSharedPreferences(SceneOptions.APPLIED,0))`（`MainActivity.java:117-120`）。
所以 `scene.xml` 属于"用户配置"，排查时不要手改成非预期值后忘记复原。

### 4.2 `full_screen_aod_on` 由谁写

> **2026-11-17 起：本模块既不写、"我们的壁纸生效期间"也不允许别人写。**
> 做法是「读侧强制 + 写侧拦截」：
> - 读侧：`AodFullScreen#overrideFullAodRead` 在「壁纸是我们的 + 息屏显示开着」时把读取结果改为 1；
> - 写侧：`AodFullScreen#blockFullAodWrite` 在同一条件下拦掉 `Settings$Secure.putInt/putIntForUser`
>   对这个键写 1 的调用（写 0 放行），存储值全程保持用户原值。
>
> 好处：切回系统壁纸时**不需要任何"恢复"动作**，MIUI 读到的就是用户原值——恢复天然即时；
> 也不会再出现「restore 把用户新选的 1 覆盖回 0」那类 bug（见 `troubleshooting/02-*` §16）。
> 兜底：若存储里存在历史遗留的 1，壁纸变更后 150ms/350ms 两次确认即写回捕获值（约 0.5 秒）。


| 写入方 | 时机 | 值 | 证据 |
|---|---|---|---|
| MIUI 原生 `MiuiFullAodManager.updateSettings()` | `r4==0` 时 | 写 `0` | 反编译源码，`02-*` §9.2【源码级】 |
| MIUI 原生 AOD 设置页（`Utils.setFsAodStyle`） | 用户选样式 | 用户选的值（0/1） | 本机实测：选「自定义」→0、选「和锁屏一致」→1，`02-*` §11.2【实测】 |
| **本模块** | —— | **不写** | 源码：`AodWallpaperMode.enter()` 只打日志（§4.3） |
| 人（adb / 设置页） | 手动 | —— | —— |

### 4.3 模块为什么不再改写它

`app/java/org/aliveclean/AodWallpaperMode.java:220-229`（逐字）：

```java
    static void enter(Context context) {
        // 2026-11-16：不再写 full_screen_aod_on。
        // 旧设计在应用壁纸时把它强写成 1，导致「用户选自定义 → 切壁纸后又变回全屏 AOD」，
        // 而且一旦 marker 被误清就无法还原用户原值。现在模块完全尊重用户的选择，
        // 需要全屏 AOD 时由 AodFullScreen 去补足设备能力（本机 MiuiConfigs 原生是 false）。
        AliveLog.i(TAG, "aod mode enter skipped: user setting is authoritative ("
                + (wantsFullAod(context) ? "full" : "custom") + ")");
    }
```

`ensureEnabled()` 同样只读（`AodWallpaperMode.java:293-298`）：

```java
    static void ensureEnabled(Context context) {
        if (context == null) return;
        // 不做任何写入；只把当前状态记一笔，便于排查。
        AliveLog.once(TAG, "aod mode untouched",
                "aod mode left to the user (" + (wantsFullAod(context) ? "full" : "custom") + ")");
    }
```

原因（`02-*` §11.2/§11.3【实测+源码】）：

1. 旧实现应用壁纸时把 `full_screen_aod_on` 强写 1 → 用户"选自定义"后又被改回全屏（Bug B 表象之一）；
2. marker 一旦被误清就没有路径写回用户原值 → 互切壁纸后设置漂移（Bug C）；
3. 改为"**选项归用户，能力归模块**"：`controlsFullAod()` 只看
   `full_screen_aod_on==1 && doze_always_on==1`，需要时补 `MiuiConfigs.SUPPORT_FULL_AOD`
   与 manager / doze host 字段。

### 4.4 残留 marker 的现状（源码级）

`AodWallpaperMode` 里三个 marker 键：

```java
    static final String KEY_MODE = "full_screen_aod_on";
    static final String KEY_RESTORE = "alive_aod_restore_value";
    static final String KEY_HAS_RESTORE = "alive_aod_restore_set";
    static final String KEY_ACTIVE = "alive_aod_active";
```

当前工作区的写入点只剩 `restore()`（`AodWallpaperMode.java:300-313`）：

```java
    static void restore(Context context) {
        try {
            if (getInt(context, KEY_HAS_RESTORE, 0) != 1) return;
            int original = getInt(context, KEY_RESTORE, UNSET);
```

而 `KEY_HAS_RESTORE` 在**全仓没有任何写入点**（`enter()` 已改成只读）。
推论（【源码】可直接验证）：

- `restore()` 现在**永远在第一行 return**，是个死路径；
- `uiAlive()` 里那句"provider 查不到时回退 `KEY_ACTIVE`"（`AodWallpaperMode.java:77`、`:80`）
  也恒为 0 → 回退分支实际不可用（当前没有代码写 `KEY_ACTIVE`）；
- 因此**"互切壁纸恢复"问题在结构上已经不存在**：没有任何代码会动 `full_screen_aod_on`。
  用户设置保持不变，这正是 `02-*` §11.5 想要的"理论上必然成立"。端到端实测仍欠（见 §8）。

> 更正 `01-功能总表与实现.md` §8.2/§8.3/§8.5：那里贴的 `enter()` 会写
> `putInt(KEY_MODE,1)` / `putInt(KEY_ACTIVE,1)`，是**旧版实现**，与当前源码不符；
> `restoreWhenForeign()` 现在只做一件事——连续 5 次确认外来后调 `restore()`（而它已是 no-op）。

### 4.5 `doze_always_on`：唯一的总开关判定

`app/java/org/aliveclean/HyperOsAod.java:380-388`：

```java
    static boolean aodEnabled(Context base) {
        try {
            return android.provider.Settings.Secure.getInt(
                    base.getContentResolver(), "doze_always_on", 0) == 1;
        } catch (Throwable unavailable) {
            AliveLog.w(TAG, "cannot read AOD switch; treating as off", unavailable);
            return false;
        }
    }
```

这个判定出现在四处，必须一致才能出球：

| 位置 | 用途 |
|---|---|
| `HyperOsAod.attach()` 第一道闸 | 关了就 `detach()` + `aod-disabled` |
| `AodOverlay.show()` | 关了就 `hide()` + `overlay skipped: AOD is disabled` |
| `AodFullScreen.hookAliveTrue()` | 关了就放行原生 false → 设置页不显示全屏 AOD 行 |
| `AodFullScreen.controlsFullAod()` | 关了就 `restoreManager()` / `restoreDoze()` |

---

## 5. 失败模式清单

### 5.1 汇总表

| # | 现象 | 代码级原因 | 主证据 |
|---|---|---|---|
| F1 | 息屏只有时钟没球，且 T06 打 `inject gave up` | AODView 整机只构造一次，且构造发生在"用户未解锁"时 → `IllegalStateException` → 旧代码 `detach()` 清空 → 之后再无实例 | 【实测】`02-*` §9.5 证据 1/3 |
| F2 | 有 `overlay added` 但屏幕上看不到悬浮窗 | 2038 悬浮窗在 doze 下被 WMS 策略隐藏（`mPolicyVisibility=false`），兜底路线不可行 | 【日志】`02-*` §9.5 证据 1 |
| F3 | `alpha watchdog fired, alpha=0.0` 反复出现 | doze 期间 Choreographer 不发 vsync → `framePosted` 永真 → 首帧永不到 | 【日志】`02-*` §9.5 证据 7 |
| F4 | 画面全黑但有 alpha=1 | 看门狗把 alpha 拉起来了，但 `RenderLoop` 没内容（provider null / EGL 失败 / 场景 0） | 【推断】`02-*` §9.5"关键证据 6 补记" |
| F5 | 日志有 `injected into the AOD view` 但没球 | 该日志只证明 `aodRoot != null`，不证明 surface 加进去了 | 【源码】`AodOverlay.java:31` + `HyperOsAod.java:109-137` |
| F6 | 采样截图全黑，误判"没球" | 屏幕处于 `DOZE_SUSPEND` 时 `screencap` 必然全黑 | 【实测】`02-*` §9.5 末尾注 |
| F7 | 球被黑底/时钟盖住 | `AODView` 层级假设(index 0 黑底、index 1 之前)与当前 MIUI 版本不符 | 【推断】`02-*` §1.4 H4 |
| F8 | 有 T04 无 T05 | `SurfaceTexture` 没起来（surface 立刻被移除 / 尺寸 0） | 【推断】判定树 `02-*` §1.5 |
| F9 | 出现 `attach skipped, live surface id=` 后无球 | 早退只判 `isAttachedToWindow() && isShown()`，不看 alpha、不看是否出帧 | 【源码】`HyperOsAod.java:232` |
| F10 | 球是旧黑白月球 / 是系统壁纸 | provider 回退到 `templateCache`（MIUI 模板复制）而不是 `APPLIED.photo` | 【源码】`AodWallpaperProvider.java:28-52` |
| F11 | AOD 关闭后仍出球 | 球来自**壁纸引擎**的 AOD 构图，与注入无关 | 【实测】`02-*` §12.4 |
| F12 | 下拉里同时有「AliveHS 样式」和「自定义」，选自定义没反应 | 项目壁纸下我们仍补足全屏 AOD → 原生项可显示但改不动效果 | 【实测】`02-*` §12.1 |
| F13 | 主页/编辑页"息屏预览"显示成锁屏 | cosmic 调色板被强行映射成 keyguard（`mode==0?1:mode`） | 【实测】`02-*` §12.9 |
| F14 | 息屏→桌面 双层 + 残影（启航 + 锁屏有纹理） | AOD 层与桌面层交叉淡入约 1 秒 | 【日志】`02-*` §12.8 |
| F15 | AOD 设置页进不去（崩溃） | 持久化 index=2 而原生 adapter 只有 2 项 → `getSelectedItem` 越界 | 【源码】`AodSettingsGuard.hookBeforeInflate` |

### 5.2 F1：AODView 整机只构造一次（且发生在锁屏）

**代码事实**：`install()` 只对**构造器**做 hook，`cachedRoot` 是唯一的实例来源；
`detach()` 曾经清空 `aodRoot`，于是"这台机器再也不会注入任何东西"（注释原文，见 §3.5）。

**触发链**（`02-*` §9.5【实测，2026-11-15 23:09:31 的 attach 栈】）：

```text
开机 → AODView 构造（用户还没解锁）
  → afterHook: cachedRoot=instance; aodRoot=cachedRoot; scheduleAttach()
  → 950ms 后 attach()
     → aodSceneState(): 读不到 CE 存储的 scene.xml，抛 IllegalStateException
     → 旧代码：SCENE_OFF → detach() → aodRoot=null（且无实例可重建）
  → 之后每次息屏：rootView()==null → 25 次重试耗尽 → "AOD injection gave up"
```

**当前代码为什么不会再犯**：三态 + `detach()` 不清缓存 + `rootView()` 退回 cachedRoot。

**排查命令**：

```powershell
adb -s <serial> logcat -d -s AliveHSAod AliveClean AliveHSHooks | Select-String -Pattern 'T0[1-9]|T1[0-3]|aod-scene|inject'
adb -s <serial> shell settings get secure doze_always_on
adb -s <serial> shell dumpsys wallpaper | Select-String -Pattern 'mWallpaperComponent'
```

**修复/规避**：确认构建包含"三态 + cachedRoot"这一版；`com.android.systemui` 作用域已勾选并
重启 SystemUI（冷启动回归仍然欠，见 §8）。

### 5.3 F2：2038 overlay 兜底不可行

**日志证据**（`02-*` §9.5 证据 1，来源同文件）：

```text
Window #12 Window{... u0 AliveHSAod}  ty=APPLICATION_OVERLAY
  mPolicyVisibility=false  isVisibleByPolicy=false  isOnScreen=false
  Surface: shown=false  mDrawState=READY_TO_SHOW  mShownAlpha=0.8
```

**含义**：窗口内容确实画了（同批日志有 `AOD frame lensScale=0.22 … photo=1`），
但窗口被 WindowManager 策略隐藏 → 用户什么都看不到。

**结论**：HyperOS 4.0.0.33 上"用悬浮窗兜底"不成立，**唯一可行路径是注入 AOD 自己的层级**。
代码保留 `AodOverlay` 只作为其他机型的兜底，不要把它当主路径，也不要因为
`overlay added` 出现就认为 AOD 有画面。

**排查命令**：

```powershell
adb -s <serial> shell dumpsys window windows | Select-String -Pattern 'AliveHSAod|mPolicyVisibility|isVisibleByPolicy'
```

### 5.4 F3：doze 期间 Choreographer 不发 vsync

**日志证据**（`02-*` §9.5 证据 7）：

```text
I/AliveHSAod: injected into the AOD view
I/AliveClean: EGL init requested=1200x2608 …
I/AliveClean: aod photo from provider 1200x2608      ← 照片加载成功
I/AliveHSAod: alpha watchdog fired, alpha=0.0        ← 首帧始终没来
```

**机理**：`framePosted` 只有 `onVsync()` 复位；vsync 不来 → `framePosted` 永真 →
后续 `schedule()` 全是空操作 → `firstFrame` 永不触发 → alpha 停在 0。

**修复**：`RenderLoop.schedule()` 的 120ms `vsyncWatchdog`（§3.10）。日志签名：

```text
I/AliveClean: vsync watchdog: drawing without Choreographer mode=0
```

**规避**：不要用"加大/减小 `ATTACH_DELAY_MS`"来治这个症状——它只改"转场里能不能看到球"。

**排查命令**：

```powershell
adb -s <serial> logcat -d -s AliveClean | Select-String -Pattern 'vsync watchdog|scene transition'
```

### 5.5 F4：看门狗拉起 alpha 但没有内容

`alpha watchdog fired` 只说明"透明层被强制可见"。如果 `RenderLoop` 没画出内容，
用户看到的是**不透明黑块**（`AodSurface` 构造时 `setOpaque(true)`）。

内容链路要逐段确认：

```text
EGL init requested=WxH …            ← RenderLoop.init 成功
aod photo from provider 1200x2608   ← loadPhoto 走了 provider（AodWallpaperProvider）
aod provider returned no bitmap     ← provider 返回 null（会回退文件/渐变占位）
GLES3 scene ready WxH               ← createScene 成功（Diagnostics.TRACE 才有）
AOD frame lensScale=0.22 … photo=1  ← native 场景真的在画
```

最后一条只在 `Diagnostics.TRACE` 且 `mode==0` 且 `motion.values[0]>.999f` 时打一次
（`RenderLoop.java:410`），是"内容确实在画"的最强证据。

### 5.6 F5：`injected into the AOD view` 不等于注入成功

- 打印点：`AodOverlay.java:31`，条件是 `HyperOsAod.injectIntoAod()` 返回 true；
- 返回值语义：`root != null` 即 true（`HyperOsAod.java:130`）；
- 所以出现这条日志时可能**只拿到了实例**，`scheduleAttach` 里还在 950ms 等待，
  甚至后续三道闸直接 `detach()`。

**可靠的阶段证据（按顺序看）**：

| 日志 | 证明什么 |
|---|---|
| `[HA][T01] hooks installed for …` | 构造 hook 装上（SystemUI 进程启动即可见） |
| `[HA][T06] inject waiting for AODView try=N` | 正在等实例（若到 `inject gave up` 则失败） |
| `[HA][T12] aod children of AODView count=…` | 注入前后层级（含 ours 标记） |
| `[HA][T04] surface#create id=… host=… children=…` | **surface 对象建出来了，且找到了宿主** |
| `[HA][T05] texture#available … WxH` | SurfaceTexture 起来了（真正开始渲染） |
| `[HA][T13] surface state alpha=… shown=… parent=…` | 900ms 后仍在树上、alpha=1、parent=AODView |
| `[HA][T09] supervise start/stop` | 重挂守护在跑 / 因替换而退出 |

判定口径：**T04+T05+T13(parent=AODView, alpha=1) 三者齐了才叫注入成功**；
只有 T04 没有 T05 → F8；T09 立刻 stop(replaced) → 被新一轮 attach 或 detach 顶掉。

### 5.7 F6：采样必须落在 `mState=DOZE`

`02-*` §9.5 末尾【实测】：屏幕处于 `DOZE_SUSPEND` 时 `screencap` 截出来必然全黑。
23:44–23:46 那几轮"全黑"就是踩了这个坑，不是模块问题。

正确姿势见 §6：`dumpsys display | grep mState=` 轮询到 `DOZE` 之后才截。

### 5.8 F9：`attach skipped` 早退

```java
        if (existing != null && existing.isAttachedToWindow() && existing.isShown()) {
            Trace.log("T03", "attach skipped, live surface id=" + System.identityHashCode(existing));
            return;
        }
```

早退条件**不看 alpha、不看是否出帧、不看 parent 是不是 inner AODView**。
如果出现这条日志之后仍然没球，要看 `T13` 的 alpha/parent，再看 `supervise` 有没有把
surface 搬回 inner。

**规避**：临时关掉注入路径（`scene.xml` 的 `aod=-1`）对比"壁纸引擎构图的球"与"注入层的球"，
这是最快区分手段（`02-*` §12.4 用过）。

### 5.9 F10：球的内容来源（provider）

`AodWallpaperProvider`（`AodWallpaperProvider.java:28-52`）读图顺序：

1. `APPLIED`（`scene.xml`）的 `photo` 文件；
2. `DRAFT`（`scene.preview`）的 `photo` 文件；
3. `templateCache()`：`su 0` 从 `/data/user_de/0/com.miui.aod/files/templates/current/`
   复制 `wallpaper_home` / `wallpaper` / `small_wallpaper_screenshot`，每次 3s 超时。

因此"球是系统壁纸"意味着走到了第 3 步，"球是旧黑白月球"意味着读到了旧 APK 留下的缓存
（当前代码每次都先删旧文件再复制，`AodWallpaperProvider.java:60-66`）。

**权威来源**：`content://org.aliveclean.aodwallpaper/photo`（`AUTHORITY` 常量，`:12`）。
> 更正 `01-*` §5.1/§5.3 里写的 `content://org.aliveclean.aodphoto/photo`：authority 是
> `org.aliveclean.aodwallpaper`（源码），路径 `/photo`（`PHOTO` 常量）。用旧 URI 做
> `content read` 会直接失败，别据此判断 provider 坏了。

**排查命令**：

```powershell
adb -s <serial> shell content read --uri content://org.aliveclean.aodwallpaper/photo > aod.png
adb -s <serial> logcat -d -s AliveClean | Select-String -Pattern 'aod photo from provider|provider returned no bitmap|provider read failed|template cache'
```

### 5.10 F11：AOD 关闭后仍出球

【实测】`02-*` §12.4：把 `scene.xml` 的 `aod` 改成 `-1`、重启 SystemUI 后用户复测
"球还在，效果还在"。说明**球来自壁纸引擎的 AOD 构图**，与 `HyperOsAod` 无关。

所以"关 AOD 还出球"时要分两层查：

| 层 | 判定 |
|---|---|
| 注入层 | 日志应出现 `AOD scene skipped: AOD is disabled`（`doze_always_on=0`）或无 T04 |
| 壁纸引擎层 | `scene` 日志里仍有 `mode=0` 构图；判定在 `CleanWallpaper.aodDisabled()`（`CleanWallpaper.java:262-269`，注意 `isPreview()` 时恒返回 false） |

### 5.11 F12：下拉里「自定义」选了没反应

`02-*` §12.1【实测】：为"用户设置为准"去掉 `lock()` 后，项目壁纸下原生「自定义」项可以显示，
但我们已经补足了全屏 AOD 能力，选它并不会改变渲染 → 用户体感"没反应"。
修法是**恢复 `lock(row, spinner)`**（`AodSettingsGuard.java:187`、`:249`）。

结论性经验（原文）："让设置项可点"和"让设置项生效"必须同时成立，只放开一半比锁死更糟。

### 5.12 F13：息屏预览显示成锁屏

【实测】`02-*` §12.9：主页三张卡片里"息屏"和"锁屏"同图；裁切差分 `aod vs lock` 平均差 4.17，
`aod vs home` 14.74。根因是 `int palette = mode==0 ? 1 : mode;`（85cfa94 引入）把 AOD 帧
映射到 keyguard 调色板。修法是 `int palette=mode;`（`RenderLoop.java:268`，当前已在位）。

### 5.13 F14：息屏→桌面双层（启航 + 锁屏有纹理）

诊断日志（`02-*` §12.8【日志】）：

```text
wake t=722   ratio=0.43/0.00/0.57   mode=2
wake t=874   ratio=0.28/0.00/0.72   mode=2
wake t=1046  ratio=0.11/0.00/0.89   mode=2
```

锁屏层权重恒 0 → 不是 glass 叠层；真问题是 AOD 层与桌面层交叉淡入约 1 秒。
修法在当前源码里可见（`SceneMotion.java:196-199`）：

```java
        // 2026-11-16: 息屏→桌面 时，息屏层和桌面层默认会同时在场约 1 秒（诊断日志实测 ratio aod 0.43→0.11）。
        // 锁屏选了纹理时，息屏层画面（玻璃扭曲那份）与桌面差别很大，这一秒就被看成"双层"。
        // 锁屏没有纹理时两层几乎一样，所以只有选纹理才暴露。这里把 0->2 的叠加压到 150ms。
        if(aod==0&&previous==0&&next==2&&lock>=1)blendDuration=150;
```

**规避**（用户已验证，`02-*` §12.7）：桌面也选一个纹理，双层不出现。
**回退**：改这个数值或删掉该 `if` 即回到原行为。

> 注意：这段属于**壁纸引擎**（`SceneMotion`），不属于 AOD 注入链路。历史上有一次误判
> （§12.5 认为是 `lens()` 转场造成），A/B 还原后证伪（§12.6）。改这块前先做 A/B。

### 5.14 F15：AOD 设置页崩溃

`AodSettingsGuard.hookBeforeInflate()` + `hookSpinnerDescription()` 在
`onFinishInflate` **之前**把越界的 selected position 夹回 0（`clampSelection`，`:142-156`）：

```java
        int selected = spinner.getSelectedItemPosition();
        if (selected < 0 || selected >= adapter.getCount()) {
            spinner.setSelection(0, false);
            AliveLog.i(TAG, "aod spinner clamped " + selected + " -> 0 (count " + adapter.getCount() + ")");
        }
```

原因见类注释：MIUI 自己的 `onFinishInflate → updateAodSpinnerContentDescription → getSelectedItem`
在"持久化 index=2、原生 adapter 只有 2 项"的机器上会先崩，after-hook 来不及救。

---

## 6. 验证方法（可复现，不靠肉眼）

### 6.1 采样脚本（PowerShell，逐条可跑）

```powershell
$ADB = "$env:LOCALAPPDATA\Android\Sdk\platform-tools\adb.exe"
$D   = "<serial>"
$out = ".jxai\tmp\aod"

# 0) 前置条件
& $ADB -s $D shell settings get secure doze_always_on            # 必须是 1
& $ADB -s $D shell settings get secure full_screen_aod_on       # 1=和锁屏一致 / 0=自定义
& $ADB -s $D shell dumpsys wallpaper | Select-String 'mWallpaperComponent'   # 必须是 CleanWallpaper

# 1) 清日志并熄屏
& $ADB -s $D logcat -c
& $ADB -s $D shell input keyevent 26

# 2) 必须等到 mState=DOZE（不是 DOZE_SUSPEND / OFF）
& $ADB -s $D shell dumpsys display | Select-String 'mState='
#   反复执行，直到出现 mState=DOZE

# 3) 截图（DOZE 下才有意义）
& $ADB -s $D exec-out screencap -p > "$out-doze.png"

# 4) 日志
& $ADB -s $D logcat -d -v time > ".jxai\tmp\aod-log.txt"
Select-String -Path ".jxai\tmp\aod-log.txt" -Pattern 'AliveHSAod|AliveClean|AliveHSHooks|AndroidRuntime|FATAL'
```

### 6.2 亮像素统计（把"有没有球"变成数字）

做法（`02-*` §9.5 用的口径）：取截图**顶部球区域**（`y=200–700`，缩略成 40×20 ≈ 800 像素），
统计亮度超过阈值的像素数。

| 样本 | 亮像素 | 结论 | 来源 |
|---|---|---|---|
| 失败样本 `r4_38`（b1c / 4591c24 时代） | **0/800** | 无球 | `02-*` §9.5【实测】 |
| `1095efd` 连测 6 轮 `cycle1..6` | 86–124 | 有球 | 同上 |
| 正式包 `338aae7` 连测 3 轮 `rel1..3` | 90–115 | 有球 | 同上 |
| `7bad717` 回归自查 | 128/800（max 78） | 有球 | `02-*` §12.6 |
| `6e694f8` 回归自查 | 129/800（max 78） | 有球 | `02-*` §12.5 |

期望值：**球区域亮像素 ≳ 80/800 视为"有球"**；0 视为无球。原图 42KB 左右（纯黑+时钟）
vs 217–236KB（有球）也是快速判据（`02-*` §9.5 证据 3）。

### 6.3 该看哪些日志

| 层级 | tag | 关键行 |
|---|---|---|
| 注入链路 | `AliveHSAod` | `[HA][T04]`/`T05`/`T06`/`T09`/`T12`/`T13`、`inject-giveup`、`aod-scene-locked`、`alpha watchdog fired` |
| 渲染 | `AliveClean` | `EGL init requested=`、`aod photo from provider`、`vsync watchdog`、`AOD frame lensScale=`、`scene motion 2->0` |
| 全屏 AOD / 设置 | `AliveHSHooks` | `full aod hooks=N process=systemui|aod`、`full aod: handed back to MIUI`、`full aod flags … alive=`、`aod mode left to the user` |
| 壁纸应用 | `AliveClean`（APPLY） | `SCENE_APPLIED` 相关输出 |

> `Trace.log` 会先被 `Trace.enabled()` 过滤：`Diagnostics.TRACE=true` 时强制开，
> 否则看 `trace` SharedPreferences 的 `enabled`（`Trace.java:23-40`）。普通正式包默认拿不到
> `T0x` 日志——**没有 T0x 不等于没注入**，先确认构建里 `Diagnostics.TRACE` 的值。

### 6.4 回归清单（改 AOD 必跑）

- [ ] 应用项目壁纸成功，`dumpsys wallpaper` 指向 CleanWallpaper
- [ ] AOD 设置页显示「AliveHS 样式」且不可点
- [ ] 息屏后轮询到 `mState=DOZE`，2/6/12/18 秒四张截图球区域亮像素 ≳80
- [ ] 桌→息屏、锁屏→息屏、息屏→桌面 三段动画无双层/残影
- [ ] 关息屏显示：无注入（`aod-disabled`）、`full_screen_aod_on` 不变（用户值）
- [ ] 切系统壁纸：AOD 行恢复原生两项、可点击、无残留球
- [ ] 切回项目壁纸：行恢复 AliveHS、球回来
- [ ] 主页「息屏」卡片 = 黑底+球，与「锁屏」卡片不同图
- [ ] 冷启动：重启 → 锁屏态下 AODView 先构造 → 解锁 → 应用壁纸 → 看球（**仍欠**）

---

## 7. 版本线索（构建 → 修了什么）

> 版本号来自 `docs/kb/troubleshooting/02-已定位Bug档案.md` 的记载；"源码核对"一列是本次
> 在当前工作区做的验证。**本会话的命令执行通道无输出**（`git log` 等均返回空），
> 因此**提交号本身未经 git 复核**，一律按"档案记载"标注。

| 构建 | AOD 相关改动 | 证据级别 | 源码核对（当前工作区） |
|---|---|---|---|
| `4591c24` | 诊断包：`Diagnostics.TRACE=true`；修 A-1~A-4（supervise 调用、alpha 看门狗、注入有界重试、日志可分辨） | 【实测，仍未出球】`02-*` §9.3/§9.5 | A-1~A-4 的代码均在位 |
| `56e279f` | 修"开机时 attach 被锁屏打断"（cachedRoot 缓存 + 三态场景） | 【实测，仍不可见】§9.5 | `cachedRoot`、`SCENE_UNKNOWN` 在位 |
| **`ff49027`** | **修好球**：不再清 `aodRoot/cachedRoot`、`SceneOptions` 三态、`Trace.enabled()` 认 `Diagnostics.TRACE` | 【实测】§9.5 证据 3（稳定 25s/45s/65s 三张 217–236KB 截图） | 三处代码均在位 |
| `1095efd` / `338aae7` | `RenderLoop` vsync 看门狗（120ms），修"surface 在、画面不出" | 【日志+实测】§9.5 证据 7 | `RenderLoop.java:215-231` 在位 |
| `3d1dd48` | Bug B/C 设计变更：模块不再写 `full_screen_aod_on`；`controlsFullAod` 按用户选择补能力 | 【实测】§11.4 | `enter()`/`ensureEnabled()` 只读；`controlsFullAod` 如 §3.4 |
| `743be83` | 为"用户设置为准"去掉 `lock()` | 【实测，导致 §12.1】 | 已被回退 |
| `8e30b31` | `onDreamingStopped` 改 before + 注册 `ACTION_SCREEN_ON` + detach 先 alpha 0 | 【实测，**未解决**双层】§12.3 | before 钩子与 SCREEN_ON **不在**当前源码；仅 `detach()` 的 alpha 0 保留 |
| `586e7c2` | 追加 hook `DozeServiceHostInjector$mWakefulnessObserver$1#onStartedWakingUp` | 【实测，未验证解决】§12.3 | 当前源码无此 hook（已回退） |
| `6e694f8` | 启航 `lens()` 瞬时到位（`instantNext`），当时以为修了双层 | 【实测，后证伪】§12.5 | 当前源码**无** `instantNext`（已回退） |
| `7bad717` | 把 `SceneMotion` 整文件还原基线 + 保留 AOD 各处修复 | 【实测，用户确认正常】§12.6 | `SceneMotion` 无 `instantNext` |
| `a8a689d` / `96618ca` | 息屏→桌面双层：`blendDuration=150`（仅 `aod==0 && previous==0 && next==2 && lock>=1`） | 【日志+实测】§12.8 | `SceneMotion.java:199` 在位 |
| `f2630bc` | `int palette=mode;` 修"息屏预览显示成锁屏" | 【实测】§12.9 | `RenderLoop.java:268` 在位 |
| `a96ad22` | 任务书里提到"修息屏预览显示成锁屏"的另一构建 | 【证据不足】KB 与源码均无该提交的任何记录，未采信 | —— |

判断"某个结论能不能引用"的简单规则：

- 有真机截图 + 亮像素数字 → 可当结论（如 `ff49027`、`a8a689d`、`f2630bc`）；
- 只有"改完现象变了"→ 只能算"相关"，不能算因果（`02-*` §12.6 的教训）；
- 只有源码可读 → 只能说"代码如此"，不能说"真机如此"（例如 §4.4 的 `restore()` 死路径）。

---

## 7.5 强制「和锁屏样式一致」：运行时改写读取结果（2026-11-16 新增）

**需求原话**：应用项目壁纸前如果是「自定义」显示模式，应用后仍是「自定义」，导致显示不对；
应当在应用项目壁纸后**强制按「和锁屏样式一致」**运行，但设置页里显示的仍然是「AliveHS 样式」。

### 7.5.1 为什么不能靠"写设置"

`full_screen_aod_on` 是 `Settings.Secure` 的键（1=和锁屏样式一致，0=自定义）。直接写它会：
①需要 `WRITE_SECURE_SETTINGS`（普通应用拿不到，只能走 root 命令）；
②**改动用户的原值**，切回系统壁纸后无法诚实还原（这正是 `3d1dd48` 放弃旧方案的原因）。

### 7.5.2 采用的做法：只改"读取"，不改"存储"

在 SystemUI 与 `com.miui.aod` 两个进程里 hook 读取入口，条件是**壁纸是我们的 + 息屏显示开着**：

```java
// AodFullScreen.hookFullAodSetting()
Class<?> secure = Xp.findClassIfExists("android.provider.Settings$Secure", loader);
Xp.findAndHookMethod(secure, "getInt", ContentResolver.class, String.class, int.class, hook);
Xp.findAndHookMethod(secure, "getIntForUser", ContentResolver.class, String.class, int.class, int.class, hook);
```

```java
// AodFullScreen.overrideFullAodRead()
if (param.args.length < 2 || !AodWallpaperMode.KEY_MODE.equals(param.args[1])) return;  // 只认这一个键
Object current = param.getResult();
if (current instanceof Integer && ((Integer) current) == 1) return;                    // 已是 1 不动
...
force = aliveCached(context) && HyperOsAod.aodEnabled(context);                        // 我们的壁纸 + AOD 开着
if (force) param.setResult(1);
```

要点：

- **防递归**：条件里要读壁纸状态，而读取路径又会被同一个 hook 拦到，因此用 `ThreadLocal SETTING_OVERRIDE`
  在求值期间放行原始结果。
- **防拖慢**：`alive()` 每次都要问 `WallpaperManager`，用 1 秒缓存（`aliveCached`）。
- **可回退**：切回系统壁纸后 `alive()` 为 false，读取立刻恢复原生值，不残留任何改动。
- 配合已有的字段级强制（`forceManager()` 里 `mAodFullScreenEnable=true`）双保险：设置读取给
  `MiuiSettingObserver` / `Utils.getFsAodStyle()` 用，字段给 doze 会话判定用。

### 7.5.3 影响面（读这条结论前先看）

| 读取方 | 位置 | 结果 |
|---|---|---|
| `com.miui.aod.Utils.getFsAodStyle()` | `Utils.java:523` | 返回 1 → 设置页 Spinner 选中 index 1，而 index 1 已被 `AodSettingsGuard` 换成本项目标签「AliveHS 样式」 |
| `MiuiSettingObserver(full_screen_aod_on)` | `MiuiFullAodManager.java:594` | 观察到 1 → `mAodFullScreenEnable=true` |
| `MiuiSettingsUtils.fullAodEnable()` | 锁屏编辑器 | 返回 true → 编辑器按全屏 AOD 渲染 |
| `ShortcutManager` / `AnalyticManager` | `com.miui.aod` | 同样看到 1（前者决定快捷方式可用性，后者只做埋点文案） |
| 我们自己的 `AodWallpaperMode.wantsFullAod()` | 本模块 | 看到 1 → 持续补足全屏 AOD 能力（符合预期） |

### 7.5.4 验证（构建 `95ef0fe`，**尚未在设备上验证**）

```bash
# 1) 存储值必须没被改（应仍是用户原来的 0）
adb shell settings get secure full_screen_aod_on
# 2) 应用项目壁纸后，日志里应出现一次
adb logcat -d -s AliveHSHooks:* | grep -F "aod mode forced to 和锁屏样式一致"
# 3) 息屏后行为应为"和锁屏样式一致"，设置页仍显示「AliveHS 样式」
```

### 7.5.5 切回系统壁纸时要还原用户原来的模式（2026-11-16 补）

**现象**：强制全屏 AOD 之后，切回系统壁纸，息屏仍停留在"和锁屏样式一致"，回不到应用前的"自定义"。

**根因不是我们写坏了值，而是没人留底 + MIUI 自己会写**：

1. `3d1dd48` 删掉了 `AodWallpaperMode.enter()` 里的写入，同时**连"捕获用户原值"也一起删了**
   → `alive_aod_restore_set` 从此没有写入点 → `restore()` 第一行就 return，永远不可达。
2. 设置页那一行被 `AodSettingsGuard` 就地锁在 index 1（"和锁屏样式一致" → 显示成"AliveHS 样式"），
   MIUI 自己的流程会把 `full_screen_aod_on=1` 写进存储；没有任何机制把它写回用户原来的 0。

**修法**（`AodWallpaperMode`）：只补"留底"，不抢用户的选择。

```java
static void enter(Context context) {
    capture(context);                    // 记下用户此刻的真实值
    AliveLog.i(TAG, "aod mode enter skipped: user setting is authoritative (…)");
}

private static void capture(Context context) {
    if (getInt(context, KEY_HAS_RESTORE, 0) == 1) return;   // 只捕获一次，多次应用都还原到最初选择
    int current = getInt(context, KEY_MODE, UNSET);
    putInt(context, KEY_RESTORE, current);
    putInt(context, KEY_HAS_RESTORE, 1);
}
```

`restore()` 本体保持原样，只补了"值没变就不写"和日志；它由既有的
`AodWallpaperModeHook.postCheck` → `restoreWhenForeign()`（连续 5 次确认壁纸不是我们）触发。

**为什么捕获到的是真实值**：`enter()` 在模块 app 进程里执行，`AodWallpaperMode.getInt` 对
`isModuleApp` 走 `su 0 settings get secure`，**不经过** 7.5.2 那个"读取即返回 1"的 hook。

**验证（构建 `22c626d`）**：

```bash
# 应用项目壁纸后应出现一次
adb logcat -d -s AliveHSHooks:* | grep -F "aod mode captured="
# 切回系统壁纸后应出现
adb logcat -d -s AliveHSHooks:* | grep -F "aod mode restored="
# 存储值应回到应用前的那个数（例如 0）
adb shell settings get secure full_screen_aod_on
```

**已知边界**：
- 只在**应用壁纸时**捕获。如果用户是在我们已生效期间改的模式，那次改动不会被记为"原值"。
- 若 marker 被外部清掉（例如清除模块数据），下一次应用会重新捕获当前值（可能已是 1）。

## 8. 未解决 / 待验证

| # | 事项 | 现状 |
|---|---|---|
| 1 | **冷启动回归** | 所有已验证结论都是"`killall com.android.systemui` 后的热路径"；开机即锁屏 → AODView 先构造 → 解锁 → 应用壁纸这条路**从未完整跑过**（`02-*` §10.3） |
| 2 | `restore()` 成为死路径是否要清理 | 【源码】确认 `KEY_HAS_RESTORE` 无写入点；保留方法只为兼容旧调用点。清理前需确认没有旧 APK 残留数据依赖它 |
| 3 | `uiAlive()` 的 `KEY_ACTIVE` 回退分支 | 同上，恒为 0 → 分支不可达 |
| 4 | 亮屏摘除时机 | 当前是 `onDreamingStopped` after；历史上 before 钩子与更早信号都被回退。息屏→桌面双层在基线转场代码下不复现，但"哪一处修复让它消失"【证据不足】 |
| 5 | `aod==101`（全屏灰度）链路 | 源码有 `loadAuxiliary(3, "shader/photo/aod/fullAod/gray.png")` 与 `SceneMotion.full()`，但**未见任何实测记录** |
| 6 | `AodOverlay`（2038）在其他机型是否可用 | 只在本机（HyperOS 4.0.0.33）确认不可用；其他机型【证据不足】 |
| 7 | `small_wallpaper_screenshot` 模板回退 | `templateCache` 会尝试三个文件名，哪个命中、命中后画质如何【证据不足】 |

---

## 9. 附录：Trace 日志编号表

日志格式：`Trace.log(id, msg)` → `AliveHSAod` tag，`[HA][<id>] <msg>`（`Trace.java:11-13`）。

| 编号 | 打印点 | 含义 |
|---|---|---|
| `T01` | `HyperOsAod.install` | 某类名的构造 hook 装上了（每个命中的类名一条） |
| `T03` | `attach` | `attach skipped, live surface id=`（早退）或 `attach replacing hidden surface id=` |
| `T04` | `attach` | `surface#create id=… host=… children=…`——surface 建出并找到宿主 |
| `T05` | `onSurfaceTextureAvailable` | `texture#available id=… WxH`；看门狗触发时打 `alpha watchdog forced` |
| `T06` | `injectIntoAod` | `inject waiting for AODView try=N` / `inject gave up` |
| `T07` | `installDoze` | `dreaming started`（息屏起点） |
| `T08` | `installDoze` | `dreaming stopped`（亮屏起点） |
| `T09` | `supervise` | `supervise start id=…` / `supervise stop (replaced) tries=…` |
| `T10` | `AodOverlay.show` | `overlay#show enter` / `overlay#show already` |
| `T11` | `AodOverlay.hide` | `overlay#hide` |
| `T12` | `attach` | AODView 子 View 顺序快照（含 `(ours)` 标记） |
| `T13` | `attach` | 900ms 后 surface 状态：`alpha=… shown=… size=… parent=…` |
| `T02` | —— | **源码中不存在**。历史文档若提到 T02，按【证据不足】处理 |

---

## 10. 与其他文档的关系

| 文档 | 关系 |
|---|---|
| `01-功能总表与实现.md` §2/§3/§4/§5/§8/§9 | 本文件是其 AOD 部分的展开；**§2.2 的 `LabelAdapter` 代码、§5.1 的 provider URI、§8.2/§8.3 的 `enter()` 代码均已过时**，以本文件为准 |
| `troubleshooting/02-已定位Bug档案.md` §1/§2/§3/§9/§11/§12 | 历史取证与根因分析；本文的日志/截图证据均注明了来源小节 |
| `README.md` §2 | 证据级别定义 |
