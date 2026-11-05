# HyperAlive 交接文档

> 写于 2026-11-02 14:02（周一）。上一个会话因上下文熔断无法继续，这份文档是唯一入口：
> 新会话读完这一页就应该能直接动手，不需要读旧对话。

---

## 0. 一句话现状

移植骨架已经跑通：模块能在 HyperOS 4 的 SystemUI 里加载、能挂到 AOD 宿主 View 上、能在 AOD 里创建并驱动
`SurfaceView`（`surfaceValid=true`，1200x2608）。**但用户实际看到的效果是：稳定息屏时不显示场景，只在
「息屏→亮屏」那一瞬间能看到一个圆球。** 这就是当前唯一的硬卡点。

---

## 1. 目标与用户约束

- 源项目：<https://github.com/Maga-King/OpenAlive>（LSPosed 模块，复刻 Flyme「Alive」动态 AOD / 锁屏壁纸，
  原本跑在 ColorOS 17）。移植目标产物叫 **HyperAlive**。
- 目标环境：设备 `10.11.48.92:39795`（无线调试，root）。原话要求「确保功能完整且完全可用」。
- 用户后来放宽的权限（已明确同意，不必再问）：
  - 可以重启 SystemUI、改壁纸、重建 AOD 会话、重启手机。
  - 「要的就是完全原生适配 hyperos」——原生实现优先，不要糊一个悬浮窗了事。
- 硬性工程约束：
  - 必须用 git 管理。
  - 源码树要干净——「项目最终将上传 github 进行公开审计」。所以不要往仓库里塞反编译产物、
    `research/` 下的中间文件要么 gitignore 要么单独分支。
  - 手机侧：只做「读写文件 / 跑命令 / 装做好的 APK」，不做破坏性系统改动（刷机、改分区不在此列）。

---

## 2. 设备与环境（都是实测值，不是猜的）

| 项 | 值 |
|---|---|
| 机型 | Redmi `25102RKBEC` |
| codename / platform | `myron` / `canoe` |
| 系统 | HyperOS 4.0，`OS4.0.0.33.XPMCNXM`（MIUI `V816`） |
| Android | 17 / SDK 37 |
| 屏幕 | 1200 x 2608 @ 480dpi |
| Root | KernelSU |
| Xposed | LSPosed（`zygisk_lsposed`，legacy 入口） |
| USB 序列号 | `9eb96e8a`（**首选**） |
| 无线 | `10.11.48.92:39795`（经常 10060 超时，别依赖） |

adb 路径：`C:/Users/ybybabab/AppData/Local/Android/Sdk/platform-tools/adb.exe`

---

## 3. 代码地图（只列这次改动相关的）

仓库根：`C:\Users\ybybabab\app\项目开发\HyperAlive`

```
app/AndroidManifest.xml          package=org.aliveclean  versionCode=58  0.5.0-preview
META-INF/xposed/java_init.list   内容：org.aliveclean.CleanHooks   ← LSPosed API 102 入口
app/java/org/aliveclean/
  CleanHooks.java                总入口，handleLoadPackage 里分步挂 hook（每步独立 try/catch）
  PlatformProfile.java           判断 ColorOS / HyperOS，缺失 OEM 包时降级为「功能不可用」
  HyperOsAod.java                ★ 当前主战场：AOD 视图注入 + doze 服务 hook + 层级 dump
  AodOverlay.java                早期方案：独立 overlay 窗口（type 2032/2038），已废弃但代码还在
  SceneApply.java                用 su 1000 身份跑 app_process 调 setWallpaperComponentWithFlags
  CleanWallpaper.java            我们的动态壁纸（Engine）
  RenderLoop.java / NativeScene.java   渲染循环与 JNI 入口（符号是 Java_org_aliveclean_NativeScene_*）
docs/port/hyperos.md             早期调研笔记
docs/port/PROGRESS.md            逐次进度快照，末尾是最新的卡点分析
tools/build_release.py           ★ 无 Gradle 构建脚本，全流程 aapt2→javac→d8→zip→zipalign→apksigner
research/                        反编译材料（MIUIAod.apk、SystemUI.apk、SysUIPlugin.apk、aod-src/…）
dist/                            构建输出
```

**包名不能改。** 预编译的 `libalive_clean.so` 里 JNI 符号写死了 `Java_org_aliveclean_NativeScene_*`，
宿主上又没有 Rust/NDK 工具链，改包名等于放弃 native 渲染。这是硬约束。

---

## 4. 构建（无 Gradle，约 60 秒）

```bash
cd "C:/Users/ybybabab/app/项目开发/HyperAlive" && \
ANDROID_HOME="C:/Users/ybybabab/AppData/Local/Android/Sdk" \
JAVA_HOME="C:/Users/ybybabab/app/pcl/JAVA21/graalvm" \
HYPERALIVE_NATIVE_SO=".tmp/libalive_clean.so" \
USERPROFILE="C:/Users/ybybabab" \
PYTHONIOENCODING=utf-8 \
C:/Python314/python.exe tools/build_release.py --no-checks
```

产物：`dist/HyperAlive-0.5.0-preview-arm64-v8a.apk`（约 640 MB，体积大是因为内置素材 + onnxruntime）。
`dist/build-report.json` 里有所有哈希，改完代码对一下 dex 哈希就知道有没有真打进去。

---

## 5. 安装与调试回路（照抄即可）

```bash
ADB="C:/Users/ybybabab/AppData/Local/Android/Sdk/platform-tools/adb.exe"

# 0) adb server 经常自己死，先拉起来
"$ADB" start-server; "$ADB" devices        # 期望看到 9eb96e8a  device

# 1) 装机（必须走 USB；无线传 640MB 会 Broken pipe）
"$ADB" -s 9eb96e8a install -r "dist/HyperAlive-0.5.0-preview-arm64-v8a.apk"

# 2) 重启 SystemUI 让模块重新加载
"$ADB" shell "su -c 'killall com.android.systemui'"; sleep 12

# 3) 清日志 → 按电源键进 AOD → 等 → 抓日志
"$ADB" logcat -c
"$ADB" shell input keyevent 26; sleep 12
"$ADB" logcat -d -s HyperAliveAod:V HyperAliveHooks:V | tail -150
```

看 AOD 状态：`adb shell dumpsys power | grep -E "mWakefulness|mAodShowing"`，稳定 AOD 应为
`mWakefulness=Dozing` + `mAodShowing=true`。

看窗口/图层：`adb shell dumpsys SurfaceFlinger --list | grep -i -E "aod|depth|wallpaper"`。

---

## 6. 已经验证过的事实（可以直接当结论用）

### 6.1 HyperOS 4 的 AOD 宿主

AOD 不在 `com.miui.aod` 独立应用里，而在 **`com.android.systemui`** 进程内。
2026-11-02 13:57 抓到的真实层级（`HyperOsAod.dumpTree()` 输出）：

```
com.android.systemui.shade.NotificationShadeWindowView      ← 窗口根
 ├ ScrimViews / NotificationPanelView / HyperOSKeyguardRootView
 ├ MiuiKeyguardStatusBarView / BlurProviderView / ControlCenterContainer / ShadeHeaderContainer
 ├ com.android.keyguard.widget.AodView                       ← 我们的注入点（外层）
 │   ├ [0] AodSurface        ← 我们加的，vis=V alpha=0.85 1200x2608 surfaceValid=true
 │   └ [1] com.miui.aod.AODView   ← 内层，整屏 1200x2608，vis=V
 │        ├ View(G)
 │        ├ AodContainerView (1200x1065)  ← 时钟：LinkageStyleClockView / MiuiClockView / AllInOneSingleClock
 │        ├ NotificationAnimationView (1200x2608)
 │        └ ViewStub
 ├ AuthRippleView
 └ KeyguardLoadingRoundView
```

### 6.2 为什么只在亮屏瞬间看得到

SurfaceView 的子 surface 默认**合成在宿主窗口的 layer 之下**，而 `NotificationShadeWindowView` 自己画了
不透明底（AOD 壁纸）。所以稳定息屏时整块被盖住；只有息屏↔亮屏那一瞬间内层在做 alpha 过渡，才露出我们的 surface。

（这条是推断，证据是「只在过渡瞬间可见」+ SurfaceView 默认 Z 序；下一步实验会直接证实/证伪。）

### 6.3 壁纸是怎么设进去的

HyperOS 的 live picker 会返回 `RESULT_CANCELED`，走不通。现在的做法是 `SceneApply` 用 `su 1000` 起
`app_process`，反射调 `setWallpaperComponentWithFlags(CleanWallpaper, FLAG_SYSTEM|FLAG_LOCK)`。
生效后 `getWallpaperInfo()` 是 `ComponentInfo{org.aliveclean/org.aliveclean.CleanWallpaper}`，
但**锁屏壁纸仍然是 `com.miui.miwallpaper…MiuiKeyguardPictorialWallpaper`**——这就是景深壁纸那条线。

### 6.4 AOD 判断壁纸归属

`HyperOsAod.ownsWallpaper()` 同时查 `getWallpaperInfo()` 和 `getWallpaperInfo(FLAG_LOCK)`，
只查前者会误判成「不是我们的」而跳过注入（踩过）。

---

## 7. 当前卡点与下一步（按顺序试，每一步都要看 logcat）

### 方案 A：注入到内层 `com.miui.aod.AODView` 的 index 0（最可能有效，先做这个）

在「壁纸之上、时钟之下」。改 `HyperOsAod.attach(ViewGroup root)`：

1. 若 `root` 本身类名是 `com.miui.aod.AODView`，直接注到它 index 0。
2. 若 `root` 是 `com.android.keyguard.widget.AodView`，遍历子 View 找类名以 `com.miui.aod.AODView`
   结尾（或 simpleName 为 `AODView`）且是 `ViewGroup` 的那个，注到它 index 0。
3. 找不到就回落外层 `root` index 0；400ms 后重试一次（内层可能是延迟 inflate 的）。
4. 验证期间把 `surface.setAlpha(0.85f)` 临时改成 `1f`，排除 alpha 干扰。

### 方案 B：抬 Z 序（A 无效时）

SurfaceView 有两条路：
- `setZOrderOnTop(true)`：surface 抬到窗口之上。场景透明像素仍能透出时钟，但**如果场景是不透明的整图，
  时钟会被盖死**——需要看 `RenderLoop` 到底画不画背景。
- `setZOrderMediaOverlay(true)`：抬到窗口之上但排在其它 SurfaceView 之下，通常比 OnTop 温和。

### 方案 C：换回独立窗口

`AodOverlay` 那条路当初死在 `BadTokenException: token null is not valid`（type 2032/2038）。
真要走这条路，得用 `WindowManager.addView` 配 `TYPE_APPLICATION_OVERLAY` + `FLAG_NOT_FOCUSABLE`，
并且从 SystemUI 的 Context 起（不是 Application Context）。留作最后手段。

### 景深壁纸上层（用户明确提的第二个问题）

现象：AOD 里时钟 + 日期正常显示，但景深壁纸的**前景主体（人像）还压在上面**，
参考截图 `Screenshot_2026-11-02-13-50-24-713_com.miui.gallery.jpg`。

已排除：`research/` 里 grep `pictorial|depth` 只命中无关的 androidx/miui 文件
（`com/miui/aod/fullaod/FullAodStrategyKt.java`、`com/miui/aod/linkagestyle/LinkageStyleClockView.java`），
**类名还没定位**。

两条候选：
1. 它属于内层 `AODView` 自己画的位图 → 方案 A 把我们的 surface 塞到它上面就顺带解决。
2. 它是 MiWallpaper 的锁屏窗口（独立 SurfaceControl）→ 需要在 dozing 期间把那个窗口 alpha 压到 0，
   或用 `clearWallpaper(FLAG_LOCK)` 让锁屏壁纸回落到我们的。

先跑 `dumpsys SurfaceFlinger --list | grep -i -E "depth|pictorial|miwallpaper"` 拿图层名，再决定。

---

## 8. 踩过的坑（不要重踩）

| 坑 | 现象 | 已采取的做法 |
|---|---|---|
| hook 全挂 | 模块一条日志都没有 | `handleLoadPackage` 里每个 hook 用 `step(name, body)` 单独 try/catch（tag `HyperAliveHooks`） |
| 悬浮窗 | `BadTokenException: token null is not valid` | 放弃 window overlay，改视图注入 |
| 锁屏壁纸判断 | `overlay skipped: wallpaper is not ours` | `ownsWallpaper()` 加查 `FLAG_LOCK` |
| 非 ASCII 路径 | aapt2 / zipalign 直接失败 | 所有路径传相对 ROOT 的 ASCII 路径 |
| 中文/GBK | javac / d8 的 argfile 被编码搞坏 | argfile 用 ASCII 相对路径 + `@argfile` |
| 无线装大包 | `Broken pipe` | 一律 USB `9eb96e8a` + `install -r` |
| 工具漂移 | `run_code` / `read_file` 不存在了 | 改用 `ops_bash`、`ops_read_files` |

---

## 9. 还没做的（按优先级）

1. **稳定 AOD 可见**（第 7 节方案 A/B/C）—— 阻塞一切。
2. **景深上层覆盖/排除**（第 7 节末尾）。
3. AOD 亮度/调光处理（AOD 会自动降亮度，场景要跟着调，否则夜里刺眼）。
4. 动画连续性：息屏 → 锁屏 → 桌面，一条时间线不中断。用户明确要「完全原生」。
5. Xposed API 102 迁移（`XposedModule` + `META-INF/xposed/java_init.list`），换热重载；AOD 路径先迁，
   ColorOS 老树留着。
6. HyperOS 锁屏时钟编辑器（在 `miui.systemui.plugin` 里）—— **没移植**。
7. 通知动效 —— **没移植**。
8. 最终 release 构建 + 写清「哪些能力已验证 / 哪些未验证」+ GitHub 公开审计前的清理
   （`research/`、`dist/`、`.tmp/` 不能进仓库）。

---

## 10. git 状态

分支 `main`，11 个提交：

```
91d4019 feat(hyperos): prefer injecting the scene into the stock AOD view
f011cbf docs(hyperos): record AOD host findings and current overlay approach
931ed4f fix(hyperos): treat the lock-screen wallpaper as ours in AOD checks
cf3bc81 feat(hyperos): add a doze overlay window for the AOD scene
fd8be8f feat(hyperos): apply scenes through a system-uid adapter
18f732b fix(hyperos): isolate every hook install so one failure cannot abort the rest
a250a48 feat(hyperos): hook the SystemUI AOD view instead of a standalone AOD app
68c4982 feat(hyperos): render scenes inside com.miui.aod via a surface host
1d921fb feat(hyperos): skip ColorOS-only AOD handoff after applying a wallpaper
6e205ed feat(hyperos): add platform profile and vendor-safe hook routing
782cbe9 chore: import OpenAlive 0.4.41-preview as HyperAlive baseline
```

**未提交**：`app/java/org/aliveclean/HyperOsAod.java`（带 dumpTree 诊断的修改）、
`docs/port/PROGRESS.md`（未跟踪）。新会话第一件事建议先把这两个提交掉，别丢。

已装机的是 13:56 构建的诊断版（带 `dumpTree`），`dist/build-report.json` 记录 apk sha256
`3206daf228f15354ac9f20aedf8d1e73bc5dd964a6503409ea1bec254fc51429`。

---

## 11. 操作提示（工具层，不是代码）

- 这个会话反复被「换气熔断」打断：连续 6 轮 thinking 没产出正文就强制停，压缩插件疑似有 bug 压不出新窗口。
  **对策：thinking 短一点，每一轮都落一次正文 + 落一次盘**，别攒到最后一起写。
- 进度写 `docs/port/PROGRESS.md`，索引写 `C:/Users/ybybabab/.dsh/hub/ledger.jsonl`（append-only，一行一条 JSON）。
- 拿不到精确 seq 就别猜：写 `[seq 未知] 检索词: ...` 加 `ref:file:...`。
