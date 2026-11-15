# 会话简报 · 2026-11-14

> 本文件用于在上下文压缩前落盘。它记录：用户原始要求（逐字保留）、项目基本事实的核验结果、
> 交接文档中已发现的不实/矛盾项、工作区审计，以及需要用户确认的事项。后续会话先读本页。

---

## 0. 用户初始要求（原文完整保留，不作压缩）

```text
/team @C:\Users\ybybabab\app\项目开发\HyperAlive\docs\port\HANDOVER-20261114.md 
工作目录须严格管理，按用途划分为脚本、工具、文档、临时文件等类别，并使用 git 进行版本控制；源码目录中不得混入无关文件或文件夹（例如用于逆向的应用文件）。
交接文档不可尽信——受 AI 幻觉等因素影响，文档可能存在错误；其中的每条信息都需先核实，再决定是否采信。
手机上不要轻易执行危险命令，如修改系统属性、删除文件等；尤其是修改系统属性这类永久性变更，必须单独记录到独立文档中。
我的要求不可遗漏：例如进行压缩，或在压缩前先精简内容并写入单独的文档；同时，这段初始要求本身应完整保留、不作压缩。
你可以自由下载和使用工具。
对我的猜想或要求，不要轻易认同，须先进行完全客观的分析。

你的时间和 token 预算是无限的，可以随时进行深度思考，始终将质量放在第一位。

你现在先了解项目的基本，然后再听我下一步指令
```

派生工作规则（便于执行，不替代原文）：

1. 目录按用途分类：源码 / 脚本工具 / 文档 / 临时文件 / 构建产物 / 逆向材料；源码树不得混入逆向 APK 等无关文件。
2. 交接文档每条信息先核实再采信；本文件第 2、3 节即核验结果。
3. 手机上不执行危险命令；修改系统属性等永久性变更必须单独建文档记录，且先征得用户同意。
4. 任何压缩前先把内容精简落盘到独立文档；用户初始要求逐字保留。
5. 对用户的猜想/要求先做客观分析，不轻易附和。
6. 质量优先，允许充分思考与取证。

---

## 1. 项目是什么（已核实）

- 项目名 **AliveHS**（OpenAlive HyperPort），包名 `org.aliveclean`（native 符号写死）。
- 目标：把 Flyme「Alive 壁纸」的桌面 / 锁屏 / 息屏连贯过渡移植到小米 HyperOS。
- 形态：LSPosed 模块 APK（Xposed API 102），不是 Magisk 模块，不是 Gradle 工程。
- 目标机：Xiaomi 25102RKBEC（myron），HyperOS 4.0.0.33.XPMCNXM / Android 17（SDK 37），1200×2608 @480dpi。
- 仓库：`C:\Users\ybybabab\app\项目开发\HyperAlive`；当前分支 `deep-clean-v2`，HEAD `fc8124f`，tag `v0.1.0`。
- 版本：versionName `0.1.0`，versionCode `1`。

### 1.1 架构

- `org.aliveclean` 壁纸进程：`CleanWallpaper` + `RenderLoop`，负责三态壁纸与转场。
- `com.android.systemui`：`HyperOsAod` 把 `AodSurface`（TextureView）注入 MIUI 的 AODView；`AodFullScreen` 改全屏 AOD 判定；`KeyguardTouchRelay` / `KeyguardDepthHider`。
- `com.miui.aod`：`AodSettingsGuard` 造「AliveHS 样式」条目；`AodWallpaperPreview` / `AodFullScreen`。
- `com.android.thememanager`：`PersonalizeEntry` 个性化入口。
- `system_server`：`AodWallpaperModeHook`、`DeviceIdleDelayHook`、两个 `checkPermission` hook（放行 `AMBIENT_WALLPAPER`）。
- 命令通路：UI → `SceneApply`（`su 1000` + `app_process`）→ `setWallpaperComponentWithFlags`；hooks 侧 → `SceneChannel` ContentProvider → 壁纸进程 Engine。
- AOD 读图：`AodWallpaperProvider`（`content://org.aliveclean.aodwallpaper/photo`），因为 SystemUI 读不到应用私有目录。

### 1.2 构建 / 部署（已核实）

- 无 Gradle：`tools/build_release.py` 调 aapt2 → javac → D8 → zipalign → apksigner。
- 必须 `python tools/build_release.py --no-checks`：`check_layout.py` 引用已删除的 `AodTransitionWindow` / `ContinuousAodPolicy` / `ImmersiveBackdropPolicy`，且 `build_release.py` 的 `CHECKS` 里另有 7 个 check 脚本已不存在，不加 `--no-checks` 会失败。
- 产物：`dist/AliveHS-0.1.0-arm64-v8a.apk`，307,315,022 字节，sha256 `4f8bb5adc2409f35b2dca28debdcfde827c02f5a0dcdbab693682b00d53714c7`（本地实测与设备 `/data/app/.../base.apk` 实测一致）。
- 设备安装时间 2026-11-14 16:09:21；`pm path` 实测 sha256 与本地一致。
- 编译自检：按 HANDOVER §3.4 的命令以 `.tmp/build/sources2.txt`（242 行）跑 javac，exit=0，仅 deprecation/unchecked 提示。
- 原生库：`.tmp/build/libalive_clean.so`，build-report sha256 `23e25e3a3718af10a43d6a914e8ba1d8b9ab30b2a4ceafcd3914688b7e3996cb`，prebuilt。

### 1.3 当前设备状态（只读实测）

- 当前壁纸组件：`ComponentInfo{org.aliveclean/org.aliveclean.CleanWallpaper}`，`mWhich=3`（SYSTEM|LOCK）。
- `settings get secure`：
  - `full_screen_aod_on = 1`
  - `full_screen_aod_support = 1`
  - `full_screen_aod_disable_reason = 0`
  - `aod_style_state = 1`
  - `aod_show_style = 2`
  - `alive_aod_restore_value = null`
  - `alive_aod_restore_set = null`
  - `alive_wallpaper_position = null`（system）
- 说明：`alive_aod_restore_*` 为 null 意味着当前 `full_screen_aod_on=1` 不是本模块 `AodWallpaperMode.enter()` 写的（该函数只在键已存在时写），来源未知，需结合日志/历史确认。

---

## 2. HANDOVER-20261114.md 核验结果

### 2.1 已核实为真

- §3.1 本机路径：adb / SDK / JDK / build-tools / jadx 均存在；`android-37` 与 `android-37.0` 两套 android.jar 都在。
- §3.2 产物大小/sha256/build-report 与文档一致；`--no-checks` 确有必要。
- §3.3 `deploy.py --build` 流程（push → `su -c pm install -r -d` → 重启壁纸进程）与代码一致；设备安装时间与文档一致。
- §4.4 scope：`tools/build_release.py` 与 `app/res/values/strings.xml` 的 scope 均为四行且无 `android`；`CleanHooks.onPackageLoaded` 第一句 `if ("android".equals(packageName)) return;`。
- §5.1 CleanWallpaper 常量、`update()` 判据、lockGrace、HOME_GRACE 抑制、`send()` 日志格式、`onCommand` 的 `goingtosleep` 抢跑，全部与代码一致。
- §5.2 RenderLoop 的 Choreographer → handler.post(renderTask) 流程、schedule 条件、effects→render→swap 顺序、三选一场景、AOD region parse，与代码一致。
- §5.3 SceneMotion：`COUNT=60`、索引 `LENS=3/GLASS=13/GROUND=19/FULL=21/MASK=23/PHOTO_MATRIX=25/DECOR_MATRIX=41`、`STALL_STEP=24`，与代码一致。
- §5.4 cosmic 号段映射与 `SceneOptions.supportedCosmic()` 一致。
- §5.6 AodSettingsGuard 的 `[原 item0, AliveHS 样式]`、`setSelection(1)`、锁触摸，与代码一致。
- §5.8 AndroidManifest 仅声明 `SET_WALLPAPER` 与 `AMBIENT_WALLPAPER`；SceneApply 的 `su 1000` + `setWallpaperComponentWithFlags(FLAG_SYSTEM|FLAG_LOCK)` + `AodWallpaperMode.enter()` 与代码一致。
- §8.1 14 个代码/构建文件确有未提交改动；`VsyncPacer.DOZE_INTERVAL_NS` 仍是 `8_000_000L`（8ms 实验值）。
- `app/java/org/aliveclean` 确有 **128** 个 `.java`（全 `app/java` 共 133 个，另 5 个是 android/com/miui 兼容 shim）。

### 2.2 已发现的不实 / 过期 / 自相矛盾

1. **§6 设备实测样例不实。** 文档写 `photo=crop-339721515180732346.png、lock=0、sail_continuous_aod=false` 等；设备当前 `scene.xml` 实际为 `photo=photo、frame_photo=photo、lock=2、sail_continuous_aod=true、stereo_sensitivity=0、frame_angle=0.0`。至少 photo / lock / sail_continuous_aod 三项不符。
2. **§5.6「四处 hook（日志 force full aod: hooks=4）」与当前工作区不符。** 当前 `AodFullScreen.install()` 计 `hookSupport(2) + hookManager(1) + hookInitializer(1) + hookWallpaperType(2) = 6`，日志会打 `hooks=6`。`hooks=4` 只是 HEAD（未含未提交的 hookWallpaperType）的旧值。
3. **§5.6「supervise() 每 300ms 检查…最多 100 次」是死代码。** `HyperOsAod.supervise()` 有定义，但全仓库无调用点；commit `e2fd3fc` 之后已停用。当前 attach 只做一次 `findInnerAod` + 200ms 重试。
4. **§3.2 只说 check_layout 坏；实际 `build_release.py` 的 `CHECKS` 里另有 7 个 check 脚本已不存在**（check_clock_scope / check_wallpaper_picker / check_xiaomi_assets / check_xiaomi_desktop / check_xiaomi_aod_density / check_unlock_handoff / check_fingerprint_clock / check_fingerprint_clock_mask 中仅 check_xiaomi_assets 还在）。`--no-checks` 是双重必需。
5. **§3.4 源文件清单约 243 个，实际 242 行。** 微小差异。
6. **§3.1 写 `android.jar = SDK/platforms/android-37/android.jar`，但 build-report 实际用的是 `android-37.0/android.jar`**（`platform_jar()` 取数字最大平台，37.0 胜出）。两目录都在，属表述不精确。
7. **§8.1 说「14 个未提交修改（+ tools/build_release.py 1 行）」，实际 `git diff --stat` 为 15 个已跟踪文件**（13 个 Java + build_release.py + HANDOVER.md 的过期提示 2 行）。代码/构建文件确为 14 个。
8. **§5.6 的 AodFullScreen 行为与 `PROJECT-CONSTRAINTS.md` 直接冲突**：`hookSupport()` 把 `com.miui.aod.Utils.isSupportFsAod/isInnerScreenSupportFsAod` 恒置 true，`writeFlags()` 无条件写 `MiuiConfigs.SUPPORT_FULL_AOD / INNER_SCREEN_SUPPORT_FULL_AOD`，**不论当前壁纸是不是我们的**；`AodWallpaperMode` 还会写 `Settings.Secure.full_screen_aod_on`。而 PROJECT-CONSTRAINTS §4 明确「写系统设置项/改 MiuiConfigs 一律先问用户」，§9 明确「不得写该选项、不得抢第三方全屏 AOD 模块的 hook」；REQUIREMENTS §7 还要求「不选 AliveHS 样式时模块完全不介入」。仓库内未找到用户后来批准这些行为的记录。**这是最需要用户确认的矛盾。**
9. **HANDOVER §0 说「唯一还没解决的是…」与 §9 九条已知问题并存**，属表述乐观，不是事实错误，但不应据此认为其他问题不存在。

---

## 3. 工作区审计（用户要求：严格分类 + git 管理 + 源码不混入无关文件）

> 2026-11-14 16:55 更新：以下是整理前审计。清理已执行，结果与回滚方法见
> `docs/port/WORKSPACE-CLEANUP-20261114.md`。当前 `.tmp` 为 7.6 GB，顶层只有 8 个分类目录 + README；
> `wt-stable` 已移除（提交 b6ae18e 仍在）；`%SystemDrive%` 已归档。

### 3.1 结论（整理前）

- `app/java` 干净：只有 `.java`，无 APK / dex / 反编译文件；源码目录本身符合要求。
- 但仓库整体存在大量未归类/未清理的临时与逆向材料，主要集中在被 `.gitignore` 忽略的 `.tmp/` 和 `workbench/`：
  - `.tmp/` **9.7 GB**，563 个顶层条目、约 126,478 个文件、11,971 个目录；包含 208 个日志/文本、116 张截图、80 个脚本、64 个反编译目录、21 个 APK、多个 294MB 的构建快照 APK、`services3.dump` 159MB 等。
  - `.tmp/wt-stable` **2.1 GB，是一个仍在 `git worktree list` 里登记的独立工作树**（detached HEAD `b6ae18e`），不能直接 `rm`，需先 `git worktree remove` / `prune`。
  - `%SystemDrive%/`（972 KB，3 个 Windows Caches 数据库文件）是未展开环境变量造成的垃圾目录，未被 `.gitignore` 覆盖，污染 `git status`。
  - `workbench/` 230 MB（已 ignore）：`research/` 215MB 含 `SystemUI.apk`、`MIUIAod.apk`、`SysUIPlugin.apk` 等逆向用应用文件；`removed-java/` 是被删源码备份；`reference-assets/` 是参考素材。
  - `build/` 870MB、`dist/` 894MB、`target/` 8.3MB 均为构建产物，已 ignore。
- 被 git 跟踪的二进制主要是 `app/assets` 运行时素材（307MB，含官方提取的 runtime.apk / UI apk / 字体 / 壁纸）与 `vendor/` 依赖；这是项目运行所需，但公开前的版权审计需单独评估（PROJECT-CONSTRAINTS §7）。

### 3.2 分类与清理建议（历史方案；已于 2026-11-14 执行）

| 类别 | 现位置 | 建议 |
|---|---|---|
| 源码 | `app/java`、`app/res`、`native/`、`tests/` | 保留，已干净 |
| 构建/部署脚本 | `tools/*.py` | 保留；`__pycache__` 可删 |
| 文档 | `docs/`、根 `*.md` | 保留；旧 `PROGRESS.md` / `REVIEW.md` / `TRACE.md` 标注过期 |
| 构建产物 | `build/`、`dist/`、`target/` | 保留当前 dist 产物；旧构建快照可归档/删除 |
| 临时实验 | `.tmp/` | 按「日志/截图/反编译/APK 快照/脚本」再分；保留最近一次实验与关键 dump，删除 294MB×6 的旧 bisect APK、重复 `smoke`、旧 bundle（667MB）等 |
| 逆向材料 | `.tmp/aod-src`、`jadx-*`、`systemui-dex`、`workbench/research` | 移出源码树，统一放 `workbench/research` 或外部目录；确保不在 git 跟踪内 |
| 垃圾 | `%SystemDrive%/` | 删除或加入 `.gitignore`；建议删除（需批准） |
| 独立工作树 | `.tmp/wt-stable` | 先确认是否还需要；不需要则 `git worktree remove` + `prune`（需批准） |

---

## 4. 待用户确认的关键事项

1. **AOD 系统设置写入的授权边界**：当前代码会写 `full_screen_aod_on`，并全局 hook `isSupportFsAod` / `MiuiConfigs.SUPPORT_FULL_AOD`，与 `PROJECT-CONSTRAINTS.md` §4/§9、`REQUIREMENTS.md` §7 冲突。是否已有用户口头/后续授权？如果没有，这是需要先解决的产品级冲突，不能默认继续。
2. **工作区清理**：已于 2026-11-14 执行完毕，记录见 `WORKSPACE-CLEANUP-20261114.md`；`.tmp` 9.7GB→7.6GB，`wt-stable` 工作树移除，`%SystemDrive%` 归档，`workbench` 分类。
3. **交接文档修订**：已把第 2.2 节的 9 条差异（另加第 10 条 `full_screen_aod_on` 来源未知）补进 `HANDOVER-20261114.md` §12，并同步修正 §3.1/3.2/3.4/5.6/6/8.1/8.3。
4. **下一步任务**：用户尚未给出。HANDOVER §10 的方向（A 测量静止 AOD 帧数、B 转场只保留一个渲染器、C 降 AOD 缓冲分辨率、D 核对 enableWallPaperAnim、E 60fps 兜底）只是建议，不应自行开工。

---

## 5. 当前 git 状态（2026-11-14 17:00）

- HEAD：`fc8124f fix(aod): stop forcing full AOD over the custom style and keep MIUI's own AOD scene`
- 已跟踪修改 18 个文件：13 个 Java + `tools/build_release.py` + 4 个文档（`HANDOVER.md`、`NEXT-STEP-20261103-2131.md`、`PROGRESS.md`、`PROJECT-CONSTRAINTS.md`）。
- 未跟踪文档：`docs/port/HANDOVER-20261114.md`、`docs/port/SESSION-BRIEF-20261114.md`、`docs/port/WORKSPACE-CLEANUP-20261114.md`。
- 编译自检：使用 `.tmp/build/sources2.txt` + `android-37.0/android.jar`，exit=0。
- 未跟踪：`%SystemDrive%/`、`docs/port/HANDOVER-20261114.md`（新交接文档本身尚未入库）。
- 独立工作树：原 `.tmp/wt-stable`（detached `b6ae18e`）已于 2026-11-14 移除，提交仍在；补丁在 `.tmp/archive/wt-stable-b6ae18e-probe2.patch`。
- 编译自检通过；当前工作区代码尚未 commit。

---

*本文件由 2026-11-14 会话生成，作为压缩前的落盘存档。*
