# HyperAlive 进度快照 · 2026-11-02 13:52

> 本文件是「做到哪 / 下一步 / 卡在哪」的落盘记录。上下文被压缩后先读这一页。

## 做到哪（已验证可用）

- 移植目标机：`10.11.48.92:39795`（无线）/ USB 序列 `9eb96e8a`；Redmi `25102RKBEC`（myron / canoe），
  HyperOS 4.0 `OS4.0.0.33.XPMCNXM`，Android 17 / SDK 37，1200x2608 @480dpi，KernelSU + LSPosed。
- 构建链：`tools/build_release.py`（aapt2 → javac → D8 → zip → zipalign → apksigner），无 Gradle。
  产物 `dist/HyperAlive-0.5.0-preview-arm64-v8a.apk`，versionCode 58。
- 模块在 SystemUI 进程里已能加载并打日志（tag `HyperAliveHooks` / `HyperAliveAod`）。
  历史上「完全无日志」的根因已修：`ModuleActivationHook.install()` 在 HyperOS 上抛异常掐断了
  `handleLoadPackage`，现每个 install 都包在 `step(name, body)` 里 fail-soft。
- AOD 宿主已确认是 `com.android.systemui`（**不是** `com.miui.aod`）：
  `mWakefulness=Dozing`、`mAodShowing=true`、doze 期间只有 SystemUI 在跑。
- AOD 注入链已打通到「surface 建立」这一步，13:46 那次 doze 日志：
  ```
  HyperAliveAod: ambient surface attached
  HyperAliveAod: injected into the AOD view
  HyperAliveAod: surface created 1200x2608
  ```
- 壁纸应用绕开了 HyperOS 上必返 `RESULT_CANCELED` 的 live picker，改走 `SceneApply`
  （`su 1000 app_process` 调 `setWallpaperComponentWithFlags(CleanWallpaper, FLAG_SYSTEM|FLAG_LOCK)`）。
  - 生效组件：`ComponentInfo{org.aliveclean/org.aliveclean.CleanWallpaper}`
  - 锁屏壁纸仍是 `com.miui.miwallpaper…MiuiKeyguardPictorialWallpaper`
  - `ownsWallpaper` 判据已改为「FLAG_SYSTEM **且** FLAG_LOCK」都检查。

## 卡在哪（两个未解现象）

1. **纯息屏不显示，只在「息屏→亮屏」瞬间出现圆球。**
   surface 确实建起来了（1200x2608），但 AOD 稳定态看不到。怀疑方向（未验证）：
   - 注入点 `addView(surface, 0)` 在 `AodView` 里被不透明的背景/兄弟视图盖住；
   - 或 doze 稳定态存在覆盖层（scrim / 亮度层 / keyguard 壁纸层）压在 AodView 之上；
   - 或 SurfaceView 在 doze 窗口里 surface 有效但不可见（z-order / alpha）。
   下一步：在 `HyperOsAod.attach()` 里 post 一个延迟任务，把 `aodRoot.getRootView()` 整棵树 dump 出来
   （className / visibility / alpha / elevation / bounds / SurfaceView.isSurfaceValid），拿到真实层级再改插入位置。

2. **小米「景深壁纸」的上层壁纸（人物前景）不会被覆盖/排除。**
   截图（13:50）里息屏画面上仍浮着动漫人物前景 + 大时钟。这类壁纸由 `com.miui.miwallpaper` 提供，
   前景层画在锁屏/AOD 内容之上。下一步：在反编译的 SystemUI / miwallpaper 里定位景深前景 View
   （关键词 `Pictorial` / `depth` / `Wallpaper` / `MiuiKeyguardPictorialWallpaper`），doze 时把它
   `setVisibility(GONE)` 或 `setAlpha(0)`，退出 doze 再恢复。

## 下一步（按顺序）

1. 加调试树 dump，确认 AOD 稳定态的层级关系 → 决定注入位置（大概率要插到时钟之下、背景之上，
   或改为把 surface 挂到 doze 根容器而不是 AodView）。
2. 定位并屏蔽景深壁纸前景层。
3. AOD 亮度/变暗处理；息屏 → 锁屏 → 桌面 的动画连续性。
4. 迁移 hook 层到 Xposed API 102（`XposedModule` + `META-INF/xposed/java_init.list`），
   先只为 AOD 路径开现代入口，ColorOS 老树继续走 legacy。
5. 最终发布构建 + 文档写清「已验证 / 未验证」+ GitHub 公开审计前的清理。

## 未做的（原版有、这边还没移植）

- HyperOS 锁屏时钟编辑器（`miui.systemui.plugin`）
- 通知特效

## 环境备忘

- 构建：`C:\Python314\python.exe tools\build_release.py --no-checks`
  `ANDROID_HOME=C:\Users\ybybabab\AppData\Local\Android\Sdk`
  `JAVA_HOME=C:\Users\ybybabab\app\pcl\JAVA21\graalvm`
  `HYPERALIVE_NATIVE_SO=.tmp\libalive_clean.so`　`PYTHONIOENCODING=utf-8`
  单次约 45–60s。
- 装包走 USB `9eb96e8a`（无线装大包会 Broken pipe）；adb server 常在两次调用之间掉，先 `adb start-server`。
- 原生 `.so` 是从官方 APK 里取的，sha256 `43ff8838…eaf027`；本机无 Rust/NDK，无法重编，
  所以 JNI 符号把包名钉死在 `org.aliveclean`。

## 2026-11-02 13:57 · 关键突破：找到在纯息屏下抢画面的是谁

加了 `dumpTree()`（`HyperOsAod.java`，attach 后 1.5s 与 dozing 后 3s 各打一次整棵树），
真机抓到的 AOD 稳定态层级是：

```
NotificationShadeWindowView (root, 1200x2608)
  └ AodView  com.android.keyguard.widget.AodView   1200x2608 vis=V
      ├ AodSurface  ← 我们注入的，vis=V alpha=0.85 surfaceValid=true 1200x2608
      └ AODView     com.miui.aod.AODView           1200x2608 vis=V  ← 整屏，压在我们上面
          ├ View (G)
          ├ AodContainerView  1200x1065   ← 时钟（LinkageStyleClockView / MiuiClockView）
          ├ NotificationAnimationView 1200x2608
          └ ViewStub
```

结论：`SurfaceView` 本身没坏（`surfaceValid=true`），是**内层 `com.miui.aod.AODView`
整屏盖在上面**——它画的就是 AOD 壁纸（含景深前景），所以我们被压在壁纸下面。
这同时解释了「只在息屏→亮屏瞬间看得到」：那一刻内层视图在做 alpha 过渡，短暂透出我们的 surface。

下一步（改动很小、方向已确定）：
1. `attach()` 改成注入**内层 `AODView`（`com.miui.aod.AODView`）的 index 0**，
   而不是外层 `AodView` 的 index 0 —— 这样在「AOD 壁纸之上、时钟之下」。
   找不到内层时回退到外层，并延迟重试一次（内层可能晚于外层构造）。
2. 若内层 `AODView` 有背景 drawable 挡着，再把它 `setBackground(null)`（必要时用透明色）。
3. 景深壁纸「上层」若仍可见，说明它不在 `AODView` 背景里，而是 miwallpaper 独立图层；
   届时按类名（`Pictorial` / `depth` / `Wallpaper`）在树里定位后 `setVisibility(GONE)`。

已验证的调试手段：`adb logcat -s HyperAliveAod:V` 读树；重启 SystemUI 用
`su -c 'killall com.android.systemui'`；触发息屏用 `input keyevent 26`。


## 14:01 卡点：稳定息屏仍看不到场景 / 景深上层未覆盖

### 已确认
- 真实层级（2026-11-02 13:57 dump）：
  `com.android.keyguard.widget.AodView` → `[0] AodSurface`(我们的，vis=V, a=0.85, 1200x2608, surfaceValid=true) → `[1] com.miui.aod.AODView`(整屏) → `View(G)` / `AodContainerView`(时钟) / `NotificationAnimationView` / `ViewStub`。
- 结论一：surface 本身是好的，只是看得见的时间窗口很窄 —— 亮屏瞬间内层做了 alpha 过渡才露出我们。
- 结论二（新，待验证）：SurfaceView 的子 surface 默认**合成在宿主窗口之下**，而 NotificationShadeWindowView 自己画了不透明底（AOD 壁纸），所以稳定息屏时整块盖住。
- 结论三（景深）：上层壁纸（景深前景）应由 MiWallpaper 的锁屏窗口绘制，层级在 SystemUI 之下/之上未定；`research/` 里 grep pictorial|depth 没找到类名，仍未定位。

### 下一步（按顺序试，每条都先看 logcat）
1. `HyperOsAod.attach()` 改为**优先注入内层 `com.miui.aod.AODView` 的 index 0**（壁纸之上、时钟之下），拿不到再回落外层 `AodView`，并 400ms 后重试一次。alpha 临时设 1f 便于观察。
2. 若仍不可见 → 试 `setZOrderOnTop(true)`（surface 抬到窗口之上；场景透明像素仍能透出时钟）。若把时钟也盖死 → 换 `setZOrderMediaOverlay(true)`。
3. 景深上层：拿到 SystemFlinger `dumpsys SurfaceFlinger --list | grep -i depth` 或 lock wallpaper 窗口名后，在 dozing 期间对其 alpha/visibility 动手；退路是 `clearWallpaper(FLAG_LOCK)` 让锁屏壁纸回落到我们的。

### 卡在哪
- 换气熔断：连续 6 轮思考无正文就被强制停，导致每轮实际只能推进一步。压缩插件疑似有 bug，压缩不出新窗口。
- 硬件侧：无线 10.11.48.92:39795 常 10060 超时，改用 USB `9eb96e8a`；大 APK 只能走 USB 装。

## 2026-11-02 14:24 · AOD 卡点已解决（真因不是 z 序）

稳定息屏下场景现在能正常显示。三个真因，逐个修掉：

1. **渲染器根本没初始化**：`RenderLoop` 拿的是 SystemUI 的 Context，`getAssets()` 解析到 SystemUI 的 APK，
   `shader/photo/aod/lensPhoto/lens_decorator.png` 直接 `FileNotFoundException`。改成
   `context.createPackageContext("org.aliveclean", 0)`，并且 `RenderLoop` 在
   `getApplicationContext()` 为 null 时回落到传入的 Context。
2. **native 库加载不了**：`lib/arm64-v8a/libalive_clean.so` 在 APK 里是 DEFLATE 压缩的，LSPosed 的模块
   类加载器无法映射。`tools/apk_layout.py` 现在把所有 `lib/**/*.so` 存成 STORED，由 `zipalign -p` 做页对齐。
   （这个错以前是 `UnsatisfiedLinkError`，被吞在 `AliveGL` 线程里，只看到「没有画面」。）
3. **宿主选错**：外层 `com.android.keyguard.widget.AodView` 的 index 0 会被内层
   `com.miui.aod.AODView` 的整屏壁纸盖住。现在改成 `TextureView`（画进窗口自己的缓冲，按视图层级合成），
   并加了宿主迁移监视：内层 AODView 一出现就把场景搬进它的 index 0（壁纸之上、时钟之下）。

顺带：多次崩溃会让 LSPosed 进安全模式（全部模块停用），只能重启手机恢复 —— 调试时如果突然一条日志都没有，
先看 `/data/adb/lspd/log/` 里有没有 `System UI crashed too many times`。

## 2026-11-02 14:24 · 小米超级壁纸素材已删

按用户要求移除。注意：超级壁纸的素材只是 `assets/xiaomi/packs`（317 MB）与 6 张 banner，
但 `Xiaomi*` 那套 Java（AOD 时钟、桌面预览、应用态检测）和 HyperOS 主路径共用，整块删会连带 15 处编译错误。
所以采取的是：删 packs + banner + 首页入口卡片，保留代码骨架。
APK 从 654 MB 降到 321 MB。

## 设备状态

2026-11-02 14:34 USB 与无线（10.11.48.149:39795）同时失联，裝机验证暂停在「320MB 包待安装」。
恢复连接后先跑：`adb -s <sn> install -r dist/HyperAlive-0.5.0-preview-arm64-v8a.apk`，
再按第 5 节的回路复验 AOD。

## 2026-11-02 20:00 实测小结

- 息屏球取壁纸：`AodWallpaperProvider`（content://org.aliveclean.aodwallpaper/photo）→ RenderLoop.loadPhoto 优先读，systemui 侧不再走兜底图。commit d868f73。
- 球亮度：lens_photo_frag 末尾 `fragColor.rgb *= 0.55`；用户确认要暗，方向已定。
- 时钟：必须压在效果之上。做法＝等 inner com.miui.aod.AODView 出现再 addView(0)，不再先挂外层 root 再搬迁（那样会盖住时钟并闪一次）。commit 4126052 之前那次。
- 闪烁：MIUI 会清空 AODView 子视图；`supervise()` 连续 8 次（≈2.4s）检测不到父容器才重挂，其余情况不动。3 次息屏实测 2 次重挂，仍偶发，属结构性问题。
- 尝试过自建 overlay 窗口承载场景：窗口建起来了但场景未渲染（空帧、球消失、时钟位置被挤动），已回滚。
- 部署：新增 `tools/deploy.py`（USB 优先、同包跳过、push+pm install）。`adb install --incremental` 在本机报 Bad file descriptor，不可用。
