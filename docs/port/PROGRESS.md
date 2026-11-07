# AliveHS 进度快照 · 2026-11-02 13:52

> 本文件是「做到哪 / 下一步 / 卡在哪」的落盘记录。上下文被压缩后先读这一页。

## 做到哪（已验证可用）

- 移植目标机：`<device>:<port>`（无线）/ USB 序列 `<usb-serial>`；Redmi `25102RKBEC`（myron / canoe），
  HyperOS 4.0 `OS4.0.0.33.XPMCNXM`，Android 17 / SDK 37，1200x2608 @480dpi，KernelSU + LSPosed。
- 构建链：`tools/build_release.py`（aapt2 → javac → D8 → zip → zipalign → apksigner），无 Gradle。
  产物 `dist/AliveHS-0.5.0-preview-arm64-v8a.apk`，versionCode 58。
- 模块在 SystemUI 进程里已能加载并打日志（tag `AliveHSHooks` / `AliveHSAod`）。
  历史上「完全无日志」的根因已修：`ModuleActivationHook.install()` 在 HyperOS 上抛异常掐断了
  `handleLoadPackage`，现每个 install 都包在 `step(name, body)` 里 fail-soft。
- AOD 宿主已确认是 `com.android.systemui`（**不是** `com.miui.aod`）：
  `mWakefulness=Dozing`、`mAodShowing=true`、doze 期间只有 SystemUI 在跑。
- AOD 注入链已打通到「surface 建立」这一步，13:46 那次 doze 日志：
  ```
  AliveHSAod: ambient surface attached
  AliveHSAod: injected into the AOD view
  AliveHSAod: surface created 1200x2608
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
  `ANDROID_HOME=$ANDROID_HOME`
  `JAVA_HOME=$JAVA_HOME`
  `HYPERALIVE_NATIVE_SO=.tmp\libalive_clean.so`　`PYTHONIOENCODING=utf-8`
  单次约 45–60s。
- 装包走 USB `<usb-serial>`（无线装大包会 Broken pipe）；adb server 常在两次调用之间掉，先 `adb start-server`。
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

已验证的调试手段：`adb logcat -s AliveHSAod:V` 读树；重启 SystemUI 用
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
- 硬件侧：无线 <device>:<port> 常 10060 超时，改用 USB `<usb-serial>`；大 APK 只能走 USB 装。

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

2026-11-02 14:34 USB 与无线（<device>:<port>）同时失联，裝机验证暂停在「320MB 包待安装」。
恢复连接后先跑：`adb -s <sn> install -r dist/AliveHS-0.5.0-preview-arm64-v8a.apk`，
再按第 5 节的回路复验 AOD。

## 2026-11-02 20:00 实测小结

- 息屏球取壁纸：`AodWallpaperProvider`（content://org.aliveclean.aodwallpaper/photo）→ RenderLoop.loadPhoto 优先读，systemui 侧不再走兜底图。commit d868f73。
- 球亮度：lens_photo_frag 末尾 `fragColor.rgb *= 0.55`；用户确认要暗，方向已定。
- 时钟：必须压在效果之上。做法＝等 inner com.miui.aod.AODView 出现再 addView(0)，不再先挂外层 root 再搬迁（那样会盖住时钟并闪一次）。commit 4126052 之前那次。
- 闪烁：MIUI 会清空 AODView 子视图；`supervise()` 连续 8 次（≈2.4s）检测不到父容器才重挂，其余情况不动。3 次息屏实测 2 次重挂，仍偶发，属结构性问题。
- 尝试过自建 overlay 窗口承载场景：窗口建起来了但场景未渲染（空帧、球消失、时钟位置被挤动），已回滚。
- 部署：新增 `tools/deploy.py`（USB 优先、同包跳过、push+pm install）。`adb install --incremental` 在本机报 Bad file descriptor，不可用。

## 2026-11-03 21:57 系统个性化入口已落地（真机验证）

- 新文件 `app/java/org/aliveclean/PersonalizeEntry.java`：hook `PersonalizeActivity.onResume`，在 `personalize_constraintlayout` 里把「新卡 + 原生卡」装进一个 vertical LinearLayout。
- 关键坑：`theme_personlize_card_view` 的父容器不是标准 ConstraintLayout 行为，直接改 LayoutParams 约束无效（新卡会盖住息屏行）；改成 LinearLayout 堆叠后才正确。
- 行复用 `R.layout.miuix_preference_main_layout` 并由 activity context 直接 inflate（不要用 ThemePreferenceViewStyle 包一层，否则字号/内边距不一致），padding 抄 `aod_item`。
- 卡片用反射 `com.miui.support.cardview.CardView`，`setCardBackgroundColor` → `setRadius`（顺序不能反，setRadius 会重包 SmoothContainerDrawable）→ `setCardElevation(0)`。
- `app/res/values/strings.xml` 的 `xposed_scope` 增加 `com.android.thememanager`。
- 真机 <device>:<port> 验证：AliveHS 独立卡片位于「正在使用」下方，息屏等 8 项完整保留，点击进入 org.aliveclean.HomeActivity；重复 onResume 不重复注入（tag 去重）。

## 2026-11-03 22:17 息屏页闪退：定位 + 兜底（已并入主线）

- 现象：进入「息屏」设置页，`com.miui.aod` 直接崩溃，`InflateException: layout/settings_item, Index 2 out of bounds for length 2`，栈在 `SettingsView.onFinishInflate → updateAodSpinnerContentDescription → getSelectedItem`。
- 结论：不是我们的代码。`Utils.isSupportFsAod()` 为 true 时 MIUI 只建 2 项下拉（自定义 / 和锁屏样式一致），但 `setSelection(Utils.getFsAodStyle())` 拿到的默认索引是 2。第三方全屏 AOD 模块把 `isSupportFsAod()` 恒置 true，所以这台机器每次进息屏都踩。
- 已排除：`aod_show_style` 3→0、删除 `alive_aod_style`、`pm clear com.miui.aod`（三次都仍崩），所以不是数据残留。
- 处理：`app/java/org/aliveclean/AodSettingsGuard.java` + `CleanHooks` 的 `miuiAod` 分支。在 `SettingsView` inflate 前把越界 spinner 夹回 0；只在越界时动手，不写设置、不改变正常机型的任何行为。
- 验证：`am start -n com.miui.aod/com.miui.aod.settings.AodStyleCategoriesActivity` 页面正常打开，0 次 FATAL，日志 `aod spinner clamped 2 -> 0 (count 2)`。
- 为什么并入主线：崩溃条件是「全屏AOD 已开启 + 持久化索引越界」。装了全屏AOD模块的机器（本项目的目标用户）都会遇到；不装的机器 `isSupportFsAod()` 为 false，那段代码根本不执行，兜底也不会触发。

## 2026-11-03 23:10 入口跳转改成「原生透明窗口 + 窗口动画」

- 现象对照：主题壁纸里 息屏/壁纸/主题/图标/字体/息屏通知/指纹样式 都是同应用推页；锁屏样式本身就是跳转。用户还发现从息屏入口进入时第三方能识别到「两个应用同时在前台」。
- 结论：息屏页是 com.miui.aod 的**透明窗口 Activity**——底层主题壁纸没有销毁，新页只是盖上去滑入，所以既是跨应用又像同应用推页。
- 做法（`HomeEntryActivity` + `Theme.AliveHS.Entry`）：windowIsTranslucent=true、windowBackground 透明、backgroundDimEnabled=false，并用 `android:windowAnimationStyle` 套 MIUI 自己的 activity_open_enter/exit（原样复制到 `res/anim/hla_*.xml`，没有手写插值动画）。`HomeActivity` 去掉 final 供继承。
- 实测：`dumpsys activity activities` 里 org.aliveclean 任务 `translucent=true` 且与 com.android.thememanager 任务同时 `visible=true`——与原生息屏入口一致；页面内容本身不透明，无透底。
- 注意：不要用 `ActivityOptions.makeCustomAnimation`（跨任务会被忽略），也不要给 Activity 设别的 taskAffinity（Android 不允许跨包共享 task，实测仍落在自己的任务里）。

## 2026-11-05 13:47 Xposed API 102 迁移（真机加载验证）
- 全部 9 个 hook 类改用 `io.github.libxposed.api.XposedModule`；新增 `Xp.java` 兼容层（MethodHook/MethodHookParam/Unhook、hookAllMethods、findAndHookMethod、反射取字段/调方法）。
- 入口从 `assets/xposed_init` 换成 APK 内 `META-INF/xposed/java_init.list`；构建脚本 `tools/build_release.py` 改为直接使用 `vendor/libxposed/classes.jar`，并写入 `module.prop`、`scope.list`。
- 真机日志：systemui / thememanager / com.miui.aod:keyguardeditor / android 四个进程均 `module loaded ... api=102`，各 hook 安装日志 `ok`。
- 已知：android 进程 `WallpaperManagerService missing`（旧代码同样查找该类名，属既有问题，不是本次迁移引入）。
