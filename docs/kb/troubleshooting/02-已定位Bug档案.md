# 03 已知 Bug 与排错

> 本文件按“用户现象 → 证据 → 可能原因 → 涉及文件 → 正常版本 → 排查步骤”组织。
> 不允许把“可能”写成“已修复”。每条结论都带证据级别。
>
> 当前快照：2026-11-15，设备 `9eb96e8a`，构建 b1c（`b1c98f74...`）。
> 设备在最后一次复现前 adb 断开，部分稳态截图未完成；以下 AOD 稳态问题以**用户实机观察**为主，辅以源码链路推断。
>
> **2026-11-15 22:40 复核（设备未连接）**：本文件里靠 logcat 支撑的“已验证/已复现”条目，多数在 `.tmp` 中已找不到原始日志（只保留 2026-11-15 10:xx 旧构建的日志与截图）。凡涉及 b1c 的日志类证据，一律降级为【证据缺失】；截图类证据已逐张复核（结论见 `../facts/01-项目总览与环境.md` §8.1）。另外修正了三处与源码不符的表述，见 §1.4、§1.5、§3.5 的“复核”注记。

## 0. 当前未关闭问题（用户原话）

### Bug A：稳定息屏不显示球，壁纸变暗消失只剩时钟

> 已知现在这个版本的息屏效果还是不显示的，有 桌面/锁屏-息屏 的动画，有息屏时钟，但是息屏不显示球。实际表现是系统播放缓慢黑屏效果，我们的壁纸效果也跟着变暗了，直到跟着其他元素变暗消失，只剩时钟等aod元素

### Bug B：系统壁纸下 AOD 始终“自定义”

> 已知现在这个版本的息屏显示，在系统壁纸下无论怎么设置，效果都是 自定义 模式，疑似和 息屏效果不显示 问题同源

### Bug C：系统壁纸 ↔ 项目壁纸互切恢复未解决

> 系统壁纸 ↔ 项目壁纸互切恢复息屏显示模式没有解决

---

## 1. Bug A：稳定 AOD 无球 / 壁纸暗化

### 1.1 现象拆解

用户描述包含四个独立事实，必须分开验证：

1. **有转场动画**：桌面/锁屏 → 息屏时有缩球过程；
2. **有 AOD 时钟**：系统时钟/通知等 AOD 元素正常；
3. **稳定态无球**：动画结束后，没有看到圆形壁纸球；
4. **壁纸变暗消失**：系统缓慢黑屏，项目壁纸跟着变暗，最后只剩时钟。

这四条可以同时成立，说明“转场帧有球”不等于“稳定 AOD 有球”。

### 1.2 已有证据

- b1c 录屏 `.tmp/screenshots/anim_b1c_home.mp4`、`anim_final.mp4` 的 contact sheet 中能看到球体帧；但这只是**转场帧**，不是稳定 12/18 秒后的 AOD。
- b1c AOD 开启截图 `.tmp/screenshots/b1c_aod_on.png` 显示的是当前橙紫壁纸 + 时钟，不是明显的球；用户观察与此一致。
  - **2026-11-15 22:40 复核**：该截图里有完整状态栏（电量/信号）和“已开启 USB 文件传输模式”通知卡片，形态更像**锁屏**而不是 AOD。它既不能证明“AOD 有球”，也不能证明“AOD 无球”，只能证明那一刻壁纸是项目图。不要再用它当 AOD 稳态证据。
- 早先 5f41/0ecc 的截图也多为“全屏壁纸 + 时钟”或转场帧，没有完成“稳定 18 秒仍有球”的证据。
- 设备 adb 在准备做 2/6/12/18 秒稳态截图时断开，尚未补测。
- **另一份可用的旧证书**：`.tmp/aod-evidence/report.md`（2026-11-15 10:25–10:47，构建 `08a0d3b3…`）用例 1 在 `doze_always_on=0` 下拍到黑底上的黑白 AliveHS 球（`case1/cycle1b-off-8s.png`、`cycle2b-t02.png`）。这说明“注入的球确实能被画出来”，但那是**该关掉 AOD 时反而出球**的失败证据，不能反推 b1c 稳定态有球。

**结论：Bug A 没有已确认的正常构建。** 不能引用 `stable-anim-2026-11-02` 来证明“球正常”；那个 tag 证明的是动画/打断表现，不是稳定球。

### 1.3 主链路

```text
电源键/超时
  → MiuiDozeService.onDreamingStarted
  → MIUI 创建 AODView / 原生全屏 AOD 转场
  → HyperOsAod attach hook
  → scheduleAttach(950ms)
  → findInnerAod(root)
  → addView(AodSurface, index=1)
  → SurfaceTexture available
  → RenderLoop.mode(0) + attach
  → 首帧 onFirstFrame
  → surface.alpha 0→1 / 180ms
  → 稳定 AOD：AodSurface 持续显示球
```

任何一环失败，都可能表现为“有时钟没球”。

### 1.4 可能原因（按可能性排序）

#### H1：MIUI 重建 AODView，注入的 surface 被移除，之后没有重挂

**证据级别：高置信推断。**

- `HyperOsAod.java:199` 定义了 `supervise(ViewGroup root, AodSurface surface)`，它每 300ms 检查：
  - `surface.getParent() == null` → 重挂；
  - parent 不是 inner AODView → 移动到 inner；
  - 最多 100 次（约 30 秒）。
- 但全文件搜索 `supervise(` 只有定义，**没有任何调用点**。这是死代码。
- 当前 `attach()` 只在 `scheduleAttach`/`injectIntoAod` 时执行一次；`onViewRemoved` 没有重挂逻辑。
- `attach()` 发现 `existing.isAttachedToWindow() && existing.isShown()` 就直接 `return`，不检查：
  - `existing.getAlpha()` 是否为 1；
  - RenderLoop 是否还在出帧；
  - surface 的 parent 是否仍是 inner AODView。

这可以精确解释“转场时有球，稳定后只剩时钟”：转场阶段 surface 已注入；MIUI 完成 AOD 布局重建后把子 View 清掉，`liveSurface` 仍指向旧对象或 `isShown()` 仍为 true，后续 attach 被跳过，球消失。

涉及文件：

- `app/java/org/aliveclean/HyperOsAod.java:127-230`（attach）
- `app/java/org/aliveclean/HyperOsAod.java:199-240`（supervise，未调用）
- `app/java/org/aliveclean/HyperOsAod.java:71-102`（inject/schedule）

**2026-11-15 22:40 复核（H1 需要收窄）**：`install()` 是对 AODView **构造器**做 hook（两个类名都 hook）。所以“MIUI 重建 AODView”这个前提一旦成立，构造 hook 会重新触发 `scheduleAttach` → 950ms 后重新 `attach`，并会移除 stale 子 View 后新建 surface。因此 H1 只在下列情形成立：

- AODView **实例没重建**，只是被清空了子 View 或目标容器换成了另一个 ViewGroup（此时没有任何 hook 会再触发）；
- 新 surface 建了但立刻又被移除（例如 AODView 在同一轮里被重排两次），而 `supervise` 又不工作；
- `liveSurface` 仍指向旧 surface 且 `isAttachedToWindow() && isShown()` 为真（旧 View 尚未 detach 完全），使新 attach 直接早退。

换句话说：**“supervise 未调用”是事实，但“因此必然不重挂”是推断，不能当结论**。要判定到底走哪条分支，必须按 §1.5 抓 `T03/T04` 日志（`attach skipped, live surface id=`、`surface#create id=… children=`）与 `onViewRemoved/onViewAdded` 时序。H1 的优先级应从“最高”降为“与 H2/H5 并列，待日志区分”。

#### H2：首帧 never comes，`AodSurface` 永远 alpha=0

**证据级别：高置信推断。**

`AodSurface` 构造时 `setAlpha(0f)`；唯一把 alpha 设为 1 的路径是：

```java
loop.onFirstFrame(() -> post(() ->
        animate().alpha(1f).setDuration(FADE_MS).start()));
```

如果出现以下情况，`onFirstFrame` 不触发，surface 永远透明，用户只看到系统时钟：

- `SurfaceTexture` 已 available，但 `RenderLoop` EGL 初始化失败；
- `loop.attach(surface, w, h)` 抛异常；
- RenderLoop 第一帧 `swap` 失败；
- surface 在首帧前被销毁/重建，回调丢失；
- 模块进程被冻结/杀死。

当前没有“首帧超时后强制显示/重试”的看门狗。

涉及文件：

- `app/java/org/aliveclean/HyperOsAod.java:280-330`（AodSurface）
- `app/java/org/aliveclean/RenderLoop.java`（`firstFrameReported`、`onFirstFrame`、`swap`）

#### H3：RenderLoop 画面是黑的/旧的，不透明 surface 把壁纸盖黑

**证据级别：中高置信推断。**

- `AodSurface.setOpaque(true)`，TextureView 不透明；
- 如果 `RenderLoop` 没有有效照片/场景，可能输出黑帧；用户会看到黑块/壁纸逐渐变暗；
- `RenderLoop.loadPhoto` 当前优先 `AodWallpaperProvider.PHOTO`，失败后读本进程文件；
- 旧 `aod-wallpaper.png` 共享回退已删，但旧 APK/旧 provider 缓存仍可能返回黑白月球图；
- provider 读 `APPLIED.photo` 失败时返回 null，`loadPhoto` 可能返回 0，场景用 texture 0 → 黑。

涉及文件：

- `app/java/org/aliveclean/RenderLoop.java:321-350`（loadPhoto）
- `app/java/org/aliveclean/AodWallpaperProvider.java`（openFile/cache）
- `app/java/org/aliveclean/AodWallpaperMode.java`（alive 判定）

#### H4：注入层级/目标不对，球被黑底或时钟容器盖住

**证据级别：中置信推断。**

- `target.addView(surface, Math.min(1, target.getChildCount()), MATCH_PARENT)`；
- 代码假设 `AODView` 的 index 0 是不透明黑底，index 1 是时钟/内容容器之前；
- 如果当前 HyperOS 版本的 `AODView` 层级变化（例如 index 1 是黑底、时钟在 index 0），球会被盖住；
- `findInnerAod` 只识别类名 `com.miui.aod.AODView`/简单名 `AODView`；如果 MIUI 换了类名/层级，注入目标错误。

涉及文件：

- `HyperOsAod.java:175-195`（addView）
- `HyperOsAod.java:240-260`（findInnerAod）

#### H5：AOD 全屏 hook 改变原生转场，导致原生壁纸/模块 surface 竞争

**证据级别：中置信推断。**

- `AodFullScreen.hookDoze` 在 `MiuiDozeService.onDreamingStarted` 前强 `mFullAodEnable`；
- MIUI 原生 `FullAodAlphaShow` 仍在跑（历史日志 duration≈896ms）；
- 模块 950ms 后才注入，晚于原生转场结束；
- 如果原生在 896ms 内已把壁纸淡出，模块 surface 还没 alpha 1，就会出现“壁纸暗下去、只剩时钟”。

涉及文件：

- `AodFullScreen.java`（hookDoze/onActivation）
- `HyperOsAod.java`（ATTACH_DELAY_MS=950）
- `CleanWallpaper.java`（`goingtosleep` 提前 mode0）

#### H6：AOD 开关/模式状态不一致

**证据级别：低中。**

- `doze_always_on=1` 但 `aod` 场景被设为 -1（`aodSceneEnabled` false）→ attach 直接 detach；
- `AodWallpaperMode.alive` 因壁纸槽位/静态壁纸返回 false → detach；
- `AodFullScreen` 在 AOD-off 分支 restore 后，后续又被 `ensureEnabled` 写回 1；
- 这些通常会伴随明确日志（`AOD scene skipped: ...`、`wallpaper is not ours`），可用于排除。

### 1.5 快速判定树

按顺序做，不要跳：

```text
1. 息屏后稳定 12 秒截图，确认是否真的没有球
   ├─ 有球 → 用户描述可能是转场/特定场景，记录场景
   └─ 无球 → 继续

2. logcat 过滤 AliveHSAod / AliveClean / AliveHSHooks（**下载/构建前先开 `Trace`，否则拿不到 T03/T04**）
   ├─ 没有 "injected into the AOD view" → aodRoot 都还没建立，查 AODView 构造 hook 是否装上
   ├─ 有 "injected into the AOD view" **但**没有 "T04 surface#create" → 注入了个寂寞：`injectIntoAod()` 只判断 `aodRoot != null` 就返回 true，这条日志不证明 surface 加进去了（2026-11-15 复核修正）
   ├─ 有 "T04 surface#create"，但没有 "T05 texture#available" → SurfaceTexture 没起来
   ├─ 有 T05，但没有 "aod photo from provider" → RenderLoop 没读图/EGL 失败
   ├─ 有 provider 日志，但球黑 → 检查图片/上传/场景
   ├─ 有 "attach skipped, live surface id=" → 走了早退分支：旧 surface 仍 isAttachedToWindow+isShown，查它到底还在不在 AODView 里
   └─ 有 T04 且之后无任何 T03/T04 → 稳定后既没重建也没重挂，重点查 H1（surface 被移除/未重挂）

3. 检查 surface 状态（需要加日志）
   - surface.getParent() 是否为 null
   - surface.isShown()/getAlpha()
   - liveSurface 是否指向旧 surface
   - AODView childCount/每个 child 的类名/index

4. 检查 wallapper_mode
   - dumpsys wallpaper 是否仍指向 CleanWallpaper
   - doze_always_on / full_screen_aod_on
   - AodWallpaperMode.alive 的日志
```

### 1.6 建议修复方向（先验证再改）

优先级从高到低：

1. **调用 `supervise`**：在 `attach` 成功 addView 后调用 `supervise(root, surface)`；或者实现 `OnHierarchyChangeListener`/`onViewRemoved` 重挂。
2. **不要用 `isShown()` 作为唯一存活判据**：增加 `surface.getAlpha() >= 1`、`loop.hasDrawn()`、`surface.getParent()==target` 检查；不满足则重建/重挂。
3. **首帧看门狗**：`onSurfaceTextureAvailable` 后 1.5–2 秒仍未首帧，记录错误并重建 RenderLoop/surface；不要无限透明。
4. **保留日志**：在 `attach`、`supervise`、`onFirstFrame`、`detach` 打印 surface id、parent class、childCount、alpha、generation；这是定位 H1/H2 的关键。
5. **检查图片链路**：确认 provider 返回 1200×2608、`loadPhoto` 上传成功、场景不是 texture0。
6. **不要先调延迟**：把 950 改大/改小只能改变“转场是否看到球”，不能解决稳定态被移除；先用日志定位。

### 1.7 正常版本

| 版本/标签 | 对 Bug A 的意义 |
|---|---|
| `stable-anim-2026-11-02` | 动画/打断表现最佳的历史 tag；**不能证明稳定球正常** |
| `08a0` / `73b54` | 早期 AOD 关闭/球体测试；未完成稳定 18 秒证据 |
| `0ecc` / `f2d2` / `5f41` | 有转场球帧/截图；用户仍报稳定无球 |
| `b1c` | AOD 关闭已验证；稳定球仍无证据，用户报无球 |

**结论：目前没有已确认“稳定 AOD 有球”的正常构建。** 如果要做回归，先固定一个已知有球的版本/场景，再二分；不要凭旧文档的“已验证”选基线。

---

## 2. Bug B：系统壁纸下 AOD 始终“自定义”

### 2.1 现象

用户切到系统壁纸后，AOD 设置里无论选什么，最终都显示“自定义”，无法进入“和锁屏样式一致/全屏 AOD”。

### 2.2 相关判定

`AodWallpaperMode.alive(Context)` 当前只看 system 壁纸槽：

```java
static boolean alive(Context context) {
    WallpaperManager manager = WallpaperManager.getInstance(context);
    return isOurs(manager.getWallpaperInfo());
}
```

系统静态壁纸的 `getWallpaperInfo()` 返回 `null` → `alive=false`。

`AodSettingsGuard.bind` 在 `!uiAlive` 时直接 unbind：

```java
if (!AodWallpaperMode.uiAlive(context)) {
    Saved stale = SAVED.get(root);
    if (stale != null) unbind(root, stale);
    return;
}
```

`AodFullScreen.hookAliveTrue` 也只在 `alive && aodEnabled` 时返回 true；否则恢复 MIUI 原生 support 值。

### 2.3 可能原因

#### B1：这是 MIUI 对静态系统壁纸的产品限制

**证据级别：中高。**

HyperOS 的全屏 AOD 原生只对特定壁纸类型开放。系统静态壁纸可能本来就不支持“和锁屏样式一致/全屏 AOD”，只允许“自定义”。如果是这样，模块强行 hook 会改变系统行为，但不一定是用户期望。

验证：

- 在完全禁用模块/切回原厂状态，系统壁纸下是否也只能“自定义”；
- 查 `MiuiFullAodManager.isWallpaperTypeSupport` 原始返回值；
- 查 `full_screen_aod_support` / `full_screen_aod_disable_reason`。

#### B2：模块恢复逻辑把 support 标志还原了

**证据级别：高置信推断。**

- 切系统壁纸后 `alive=false`；
- `AodFullScreen.syncFlags`/`forceManager` 恢复 `MiuiConfigs`/manager 原值；
- 原生 `Utils.isSupportFsAod` 返回 false；
- 设置页只显示“自定义”。

这不是“模块坏了”，而是模块按设计退出。

#### B3：用户所说的“自定义”其实是 AliveHS 样式未生效，不是 MIUI 的自定义

**证据级别：待验证。**

需要区分两个“自定义”：

1. MIUI 原生的“自定义”AOD（系统自己的样式）；
2. 模块 AliveHS 样式的标签未显示，用户误以为还是自定义。

验证：截屏 AOD 设置行，看是否有“AliveHS 样式”；查 `AodSettingsGuard` 日志；查 `full aod hooks`。

#### B4：系统壁纸槽位/锁屏槽位不一致

**证据级别：中。**

`alive()` 只看 system 槽；如果用户只换了锁屏壁纸，system 仍是项目壁纸/或反之，会出现“看起来系统壁纸但 AOD 仍按项目壁纸”或反过来。`uiAlive()` 有 marker fallback，但 `alive()` 没有。

### 2.4 是否与 Bug A 同源？

**目前不能确认。**

- Bug A 更像“AODView 注入生命周期/首帧”问题；
- Bug B 更像“壁纸类型判定 + MIUI 原生 support”问题；
- 两者共享 `AodFullScreen`/`AodWallpaperMode`，但故障点不同。
- 如果系统壁纸下 `full_screen_aod_on` 仍被写成 1、AOD 仍尝试注入，则会叠加；需要分别验证。

### 2.5 排查步骤

```bash
adb shell dumpsys wallpaper | head -60
adb shell settings get secure doze_always_on
adb shell settings get secure full_screen_aod_on
adb shell settings get secure full_screen_aod_support
adb shell settings get secure full_screen_aod_disable_reason
adb shell logcat -d -s AliveHSHooks AliveHSAod | tail -100
```

重点日志：

- `aod mode ->1` / `aod mode restored=`
- `full aod hooks=... process=aod`
- `AOD scene skipped: wallpaper is not ours`
- `tm uf/ni7 ... alive=false`

### 2.6 正常版本

- 项目壁纸 + AliveHS：b1c 设置页显示 AliveHS；但稳定球有 Bug A。
- 系统壁纸：**没有已验证的“系统壁纸下仍可设置全屏 AOD”的版本**；如果 MIUI 原生本身不支持，这就是产品限制，不是回归。
- 旧版本 `08a0/73b54` 也没有系统壁纸全屏 AOD 的完整回归证据。

---

## 3. Bug C：系统壁纸 ↔ 项目壁纸互切恢复

### 3.1 现象

从项目壁纸切到系统壁纸，再切回项目壁纸，AOD 显示模式/`full_screen_aod_on` 没有按预期恢复：

- 切系统壁纸后仍是 1/仍显示 AliveHS；
- 或切回项目壁纸后变成“自定义”/full=0；
- 或需要手动重新应用壁纸才恢复。

### 3.2 代码链路

```text
切壁纸
  → WMS/WallpaperManager 变更
  → AodWallpaperModeHook（system_server，800ms 延迟）
  → AodWallpaperMode.restoreWhenForeign / ensureEnabled
  → AodFullScreen.onActivation
  → AodSettingsGuard.bind/unbind（UI 进程）
```

关键文件：

- `app/java/org/aliveclean/AodWallpaperModeHook.java`
- `app/java/org/aliveclean/AodWallpaperMode.java`
- `app/java/org/aliveclean/AodFullScreen.java`
- `app/java/org/aliveclean/AodSettingsGuard.java`
- `SceneApply.java`（项目→系统切换不经过它；只有应用项目壁纸时经过）

### 3.3 可能原因

#### C1：切换系统壁纸没有走模块的 apply 路径

**证据级别：高置信推断。**

用户从系统设置/主题壁纸切回系统壁纸时，不会调用 `SceneApply`，只能依赖 system_server 的 `AodWallpaperModeHook`/`onActivation`。如果 hook 没加载、延迟不够、或进程没重启，恢复逻辑不会执行。

#### C2：`restoreWhenForeign` 的 5 次确认被中途取消

**证据级别：中高。**

- `restoreWhenForeign` 每 1s 检查；
- 如果中途 `alive()` 又返回 true（WMS 重绑/旧 live 锁屏壁纸），会取消恢复；
- `alive()` 只看 system 槽，锁屏槽残留 live 壁纸可能让判断摇摆。

#### C3：`ensureEnabled` 把系统壁纸误当成“我们”

**证据级别：低（2026-11-15 复核后下调）。**

`ensureEnabled` 开头是 `if (!alive(context)) return;`，之后还有 `if (!aodEnabled || !aodSwitchFromShell()) return;`，最后才在 `KEY_HAS_RESTORE != 1` 时调 `enter(context)`（`AodWallpaperMode.java:304-322`）。所以它**不可能**仅凭“marker 被清”就把系统壁纸当首次应用。

要成立必须同时满足：壁纸被误判为 ours（即 `getWallpaperInfo()` 返回了 `org.aliveclean` 的 `WallpaperInfo`）**且** AOD 开关为开。这种情况更可能是 WMS 尚未重绑壁纸的短窗口，而不是 marker 逻辑缺陷。原表述把前置条件漏掉了，会误导排查方向。

#### C4：system_server 未加载新代码/未重启

**证据级别：高（环境类）。**

`system` 作用域必须重启手机。只重启 SystemUI 时，`AodWallpaperModeHook`/`AodFullScreen` 的 system_server 部分仍是旧代码，表现为“切了没反应/旧行为”。

#### C5：UI 进程的 `AodSettingsGuard` 缓存了旧 adapter

**证据级别：中。**

`AodSettingsGuard` 用 `SAVED`/`LabelAdapter` 包装 spinner；如果 AOD 设置页没有重建，切壁纸后 `bind` 未触发，行仍显示 AliveHS/仍锁定。需要 `unbind` 或页面重建。

### 3.4 排查

```bash
# 切换前后各保存一次
adb shell dumpsys wallpaper > /tmp/wp-before.txt
adb shell settings get secure full_screen_aod_on
adb shell settings get secure doze_always_on

# 切系统壁纸
adb shell am start -a android.intent.action.VIEW -d 'theme://zhuti.xiaomi.com/personalize'
# 手动切；然后
adb shell dumpsys wallpaper | head -40
adb shell settings get secure full_screen_aod_on
adb shell logcat -d -s AliveHSHooks AliveHSAod | tail -100
```

重点日志：

- `aod restore foreign confirmed x5`
- `aod restore cancelled after ... miss`
- `aod restore cancelled: wallpaper is still ours`
- `aod mode ->1` / `aod mode restored=`
- `AOD scene skipped: wallpaper is not ours`

### 3.5 正常版本

- `b1c` AOD 关闭 + `SceneApply` 已验证 `full_screen_aod_on=0`；但**系统壁纸互切没有完整回归**。
- **2026-11-15 22:40 复核修正**：并非“完全没有证据”。`.tmp/aod-evidence/report.md` §3 用例 4 在构建 `08a0d3b3…`（2026-11-15 10:38–10:42）上做过一次完整的「项目壁纸 → 系统壁纸」切换：应用项目壁纸后 `full_screen_aod_on=1`、`alive_aod_restore_value=0`、样式行显示 AliveHS 且不可点；切回系统静态壁纸后 `full_screen_aod_on=0`、`alive_aod_*` 变 null、`aod mode restored=0`、样式行恢复“自定义”且可点。判定 PASS。
  - 但这条证据的边界很清楚：**单次、单向、08a0 构建、未重启、且没有“系统→项目→系统”多轮往返**。
  - 用户仍报“互切恢复没解决”，与这次 PASS 不矛盾：用户测的很可能是 b1c 或其它顺序。
  - 正确表述：【历史/待验证】08a0 上单向切换 PASS；**【证据不足】** b1c 上的互切恢复；【用户反馈】**未解决**。
- 旧版 `08a0/73b54` 也没有完整互切证据。
- 不能把“AOD 关闭测试通过”当成“互切恢复通过”；两者路径不同。

---

## 4. 旧已知问题（HANDOVER 继承，按当前状态重标）

| # | 问题 | 旧结论 | 当前状态/备注 |
|---|---|---|---|
| 1 | 转场 250ms 停摆 | 与 SF/模糊/双循环有关 | 未复测；当前 b1c 转场有球帧 |
| 2 | 首帧 120–160ms | 可接受 | 未复测 |
| 3 | 开机清 `full_screen_aod_on` | MIUI 行为 | b1c 重启后需 SceneApply/守卫恢复；当前已处理部分 |
| 4 | system_server 偶发不加载 | scope/重启 | 仍需重启手机；未完全消除 |
| 5 | AodOverlay 被 appop 抑制 | `OPSTR_SYSTEM_ALERT_WINDOW` | 主路径改为 AodSurface；overlay 兜底不可靠 |
| 6 | 注入 AodSurface 截图不可见 | 录屏/安全窗口 | 用 `screencap` 可验证；录屏可能不显示 |
| 7 | `lens_decorator` 缺失 | native/资源 | 未处理 |
| 8 | `check_layout` 坏 | 引用已删类/缺脚本 | 构建必须 `--no-checks` |
| 9 | Vivo 帧泵未长期验证 | 性能 | 非当前重点 |
| 10 | 指纹时钟消失 | 景深/按压 | 代码有 KeyguardDepthHider；真实指纹未验证 |
| 11 | 稳定 AOD 无球 | **新** | 当前主 bug，见 Bug A |
| 12 | 系统壁纸 AOD 自定义 | **新** | 见 Bug B |
| 13 | 互切恢复 | **新** | 见 Bug C |

## 5. 通用故障排查表

### 5.1 应用壁纸失败

| 现象 | 优先检查 |
|---|---|
| `AMBIENT_WALLPAPER` | 是否勾 `system`（系统框架）并重启；不是 `android` |
| `SCENE_FAILED` + 超时 | root 授权；设备是否锁屏（`RUNNING_LOCKED`） |
| 应用成功但桌面没变 | `dumpsys wallpaper` 是否 CleanWallpaper；是否只改了 lock 槽 |
| 应用后 AOD 开关变 0 | `AodWallpaperMode.enter` 捕获顺序；`AodFullScreen.onActivation` 是否 restore |
| 安装成功但仍旧行为 | APK SHA；system_server 是否重启；进程是否旧 |

> **2026-11-15 22:40 复核**：全仓（源码 + `docs/`）搜索 `RUNNING_LOCKED`，**只在本 KB 的 4 个文件里出现，源码与旧交接文档中都没有出处**。`SceneApply.apply()` 的失败判据是“退出码非 0 或输出不含 `SCENE_APPLIED`”，并把子进程输出原样带回；代码里没有任何地方会产生或匹配 `RUNNING_LOCKED`。
> 结论：【不支持】“设备锁屏会报 `RUNNING_LOCKED`”这一说法作为一个**可观察错误串**；【待验证】“锁屏时 `app_process` 读 Settings 失败”这个**机理**仍然可能存在，但表现应是异常堆栈/空值，不是这个字符串。排查时请贴子进程真实输出，不要等这个字符串。

### 5.2 AOD 设置页异常

| 现象 | 优先检查 |
|---|---|
| 没有 AliveHS 行 | `com.miui.aod` scope；`Utils.isSupportFsAod` hook；`uiAlive` |
| 行出现但可改 | `lock` 是否被 MIUI 重建覆盖；`AodSettingsGuard` 是否重新 bind |
| 切系统壁纸行不恢复 | `uiAlive`/`unbind`；AOD 页是否重建 |
| 行 AliveHS 但实际系统样式 | 标签与实际渲染脱节；查 AodSurface/球 |

### 5.3 AOD 画面异常

| 现象 | 优先检查 |
|---|---|
| AOD 全黑 | AOD 关闭守卫；`doze_always_on`；`SceneApply` 是否 restore |
| 只有时钟没球 | Bug A：surface 被移除/alpha 0/RenderLoop 黑 |
| 球是旧黑白月球 | provider 旧缓存/旧 APK；查 `aod photo from provider` |
| 球是系统壁纸 | provider 读到 templateCache/系统模板，不是 APPLIED.photo |
| 球位置/大小不对 | `BALL_CENTER_Y`/`BALL_RADIUS`；AODView 层级 |
| 亮屏后球残留 | `detach`/`onSurfaceTextureDestroyed`；`liveSurface` 清理 |

### 5.4 个性化缩略图异常

| 现象 | 优先检查 |
|---|---|
| 灰白卡片 | `com.android.thememanager` scope；`tm uf/ni7` 日志；`original==null` 分支 |
| 卡片是旧图 | provider/templateCache 缓存；重启 ThemeManager |
| 卡片彩色但点击无反应 | `PersonalizeEntry`/页面坐标；不是缩略图 hook |
| 预览 OOM/崩溃 | `scaleOrRecycle`/`forHook` 是否复制/回收正确 |

## 6. 日志采集模板

复现前：

```bash
ADB="$LOCALAPPDATA/Android/Sdk/platform-tools/adb.exe"
D=9eb96e8a
"$ADB" -s $D logcat -c
"$ADB" -s $D shell settings get secure doze_always_on > .tmp/logs/doze.txt
"$ADB" -s $D shell settings get secure full_screen_aod_on > .tmp/logs/full.txt
"$ADB" -s $D shell dumpsys wallpaper > .tmp/logs/wallpaper-before.txt
```

复现后：

```bash
"$ADB" -s $D logcat -d -v time > .tmp/logs/full-$(date +%H%M%S).log
"$ADB" -s $D shell dumpsys wallpaper > .tmp/logs/wallpaper-after.txt
"$ADB" -s $D exec-out screencap -p > .tmp/screenshots/aod-$(date +%H%M%S).png
```

过滤：

```bash
grep -E 'AliveHSAod|AliveClean|AliveHSHooks|AndroidRuntime|FATAL' .tmp/logs/full-*.log
```

## 7. Bug 报告模板

```text
【构建】SHA/大小/安装时间：
【设备】型号/系统/serial：
【壁纸】项目/系统/其他；dumpsys wallpaper 组件：
【AOD 开关】doze_always_on / full_screen_aod_on / alive_aod_*：
【操作】从哪个页面、按了什么、等待多久：
【现象】稳定 2/6/12/18 秒分别看到什么：
【日志】AliveHSAod/AliveClean/AliveHSHooks 关键行：
【截图/录屏】路径：
【是否重启】system_server/SystemUI/AOD/ThemeManager：
【最近改动】commit/APK：
【结论】已验证/高置信推断/待验证：
```

## 8. 当前排查优先级（2026-11-15 23:0x 更新）

1. **Bug A 取证**：装诊断构建 `4591c24`（`Diagnostics.TRACE=true`）→ 重启 → 应用项目壁纸 → 息屏 2/6/12/18s 截图 + `logcat -s AliveHS*`。判据见 §1.5 与 §9.3。
2. **Bug B/C 验证**：本次已按反编译源码改掉"把旧 support 标志写回"的缺陷（§9.2），需要实测确认 `full_screen_aod_support`/`full_screen_aod_on` 在切回系统壁纸后回到原生值。
3. **不要**再先调延迟/先改外观参数——A 的静态缺陷（supervise 死代码、alpha 无兜底、注入只试一次）已经在 `4591c24` 里修掉，剩下的是"到底哪一环在真机上没走通"，只能靠日志。
4. 真实指纹与系统壁纸人工回归最后做；不要用 adb 虚拟指纹代替。

## 9. 2026-11-15 23:0x 现场取证与 B/C 根因（反编译 + 实机）

### 9.1 现场快照（实机只读，设备 `9eb96e8a`，HyperOS 4.0.0.33 / Android 17）

| 项 | 值 | 说明 |
|---|---|---|
| 装的 APK | b1c（设备上 `base.apk` sha256 = `b1c98f74…`） | 与 `dist/save/b1c98f7.apk` 一致 |
| 当前壁纸 | `com.miui.miwallpaper/…ImageWallpaper`（**系统静态壁纸**） | `dumpsys wallpaper` 的 `mWallpaperComponent` |
| `doze_always_on` | 1 | |
| `full_screen_aod_on` | **1** | 系统壁纸下仍是 1 |
| `full_screen_aod_support` | **1** | 同上 |
| `full_screen_aod_disable_reason` | **0** | MIUI 认为"没有任何不支持原因" |
| `alive_aod_restore_value/set/active` | 全 `null` | 说明模块 `restore()` 跑过并清掉了自己的标记（logcat 有 `aod foreign confirmed x5`，22:33:11） |
| AOD 设置页 | 息屏样式显示 **「和锁屏样式一致」**，预览是"壁纸 + 时钟" | 截图 `.tmp/screenshots/bugfix-20261115/b1-aodstyle-system-wallpaper.png` |

**这张截图就是用户说的"效果始终是自定义模式"**：系统壁纸下 AOD 仍按"全屏 AOD / 和锁屏样式一致"渲染，而不是普通 AOD 样式。

### 9.2 B/C 根因：我们把自己的旧标志写回去了（【已验证（源码级）】）

反编译 SystemUI（`.tmp/research/decompiled/jadx-systemui`）确认：

```java
// MiuiFullAodManager.onWallpaperChanged()
isWallpaperTypeSupport = isDefaultLockScreenTheme() && isMiuiWallpaperComponentUsing()
                         && !"super_wallpaper".equals(mKeyguardWallpaperType);

// MiuiFullAodManager.updateSettings()
r4 = isLockScreenTemplateSupport && isWallpaperTypeSupport && isDeviceSupport
     && (!isVideoDepthEnable || isSupportPlayBack);
if (isFullAodSupport == r4 && mNotSupportReason == i2) return;   // 相等就不写
isFullAodSupport = r4;
putIntForUser("full_screen_aod_support", r4);                    // ← 就是它被写成 1
if (r4 == 0) putIntForUser("full_screen_aod_on", 0);

// fullAodEnable()
isDeviceSupport && mAodEnable && mAodFullScreenEnable && isFullAodSupport
```

而 b1c 的 `AodFullScreen.restoreManager()` 会把**在"我们的壁纸"期间捕获的** `MANAGER_ORIGINAL` 原样写回，其中 `isWallpaperTypeSupport = true`。于是：

1. 壁纸切回系统静态壁纸 → native `onWallpaperChanged()` 算出 `isWallpaperTypeSupport=false`；
2. 我们的 after-hook 立刻 `restoreManager()` 把它改回 `true`（捕获值）；
3. native `updateSettings()` 用 `true` 算出 `r4=1` → 写 `full_screen_aod_support=1`，且**永远不会**写 `full_screen_aod_on=0`；
4. 全屏 AOD 在系统壁纸下"永远受支持" → AOD 样式页按全屏 AOD 渲染 = 用户看到的"始终自定义"。

- 【支持】B2（模块恢复逻辑把 support 标志还原了）
- 【不支持】"`full_screen_aod_support=1` 是 MIUI 对静态壁纸的原生值"——它确实是 native 会写 1 的分支，但**前提是我们没把 `isWallpaperTypeSupport` 改回 true**；两者需要实测区分（禁用模块对照）。
- 【同一根因】B 与 C 是同一处代码：C 的"互切不恢复"就是第 2、3 步的后果。

**已修（诊断构建 `4591c24`）**：`AodFullScreen.reconcileNative()` —— 外来壁纸时不再写回旧标志，而是先 `restoreManager()`，再**在我们自己的 hook 被屏蔽（`RECONCILING`）的情况下**调用 native 的 `onWallpaperChanged()` + `updateSettings()`，让 MIUI 按当前真实壁纸重算两个 Secure 键；另外开机 2.5s 补一次 reconcile，堵住"上次会话留下的标志"。`reconcileNative` 有 1.5s 防抖。

### 9.3 Bug A 本次修掉的静态缺陷（都是"确定性缺陷"，不是猜）

| # | 缺陷 | 后果 | 修法 |
|---|---|---|---|
| A-1 | `supervise()` 从未被调用（死代码） | surface 一旦被 AODView 移除/换宿主就永久丢失 = "稳定后只剩时钟" | `attach()` 成功后启动 supervise，带 generation/liveSurface 校验 |
| A-2 | `AodSurface` alpha 只在首帧回调里置 1，没有兜底 | 首帧不来则 alpha 永远 0 = "没有球" | 1500ms 看门狗：alpha 仍 < 1 就强制动画到 1，并打 `alpha watchdog fired` |
| A-3 | `injectIntoAod()` 在 `aodRoot == null` 时直接返回 false，`AodOverlay` 只试一次（950ms） | AODView 晚于该窗口 inflate 就整轮没有注入 | 200ms × 25 次有界重试，`onDreamingStopped` 取消，耗尽打 `AOD injection gave up` |
| A-4 | 注入成功与否只看 `aodRoot != null` | 日志 `injected into the AOD view` 会骗人 | 保留但用 `T04/T05/T06/T09` 区分真实阶段 |

**仍未判定**：真机上是"没注入"（看 `T06 gave up`）、"注入了但被移除"（`T09 supervise stop` / `T04` 后无 `T05`）还是"注入了但内容黑"（有 `T05`+`alpha watchdog fired`）。这三条只能靠 `4591c24` 的日志区分，不要在没有日志的情况下继续改代码。

### 9.5 2026-11-15 23:23–23:32 真机验证：球已经出来了（【已验证】）

诊断构建 `4591c24` 装了没修好，`56e279f` 修了"开机时 attach 被锁屏打断"这条，仍不可见；`ff49027` 在 systemui 重启后**稳定显示**。

**关键证据 1：2038 悬浮窗在 doze 下被系统强制隐藏（这条路是死的）**

```text
Window #12 Window{... u0 AliveHSAod}  ty=APPLICATION_OVERLAY
  mPolicyVisibility=false  isVisibleByPolicy=false  isOnScreen=false
  Surface: shown=false  mDrawState=READY_TO_SHOW  mShownAlpha=0.8
```

即：`AodOverlay` 的 SurfaceView 内容画得出来（`AOD frame lensScale=0.22 … photo=1`），
但窗口本身被 WindowManager 策略隐藏。**所以"用悬浮窗兜底"在 HyperOS 4.0.0.33 上不成立**，
唯一可行路径就是注入 AOD 自己的层级。

**关键证据 2：AOD 视图就是 `com.miui.aod.AODView`，子 View 顺序如下**

```text
[HA][T12] aod children of AODView count=5
  [0:View  1:AodSurface(ours)  2:AodContainerView  3:NotificationAnimationView  4:ViewStub]  hostAlpha=0.0 hostShown=true
[HA][T04] surface#create id=… host=… children=5
[HA][T09] supervise start id=…
[HA][T05] texture#available id=… 1200x2608
[HA][T13] surface state alpha=1.0 shown=true size=1200x2608 parent=AODView
supervise done, parent=com.miui.aod.AODView
```

`index 0` 是不透明黑底，我们的 `AodSurface` 插在 index 1（黑底之上、时钟容器 `AodContainerView` 之下），
与代码注释里的布局假设一致 —— 这次实测确认了。

**关键证据 3：截图**

- 修好前（`b1c` / `4591c24`）：纯黑 + 日期时钟，无球 → `r2_18.png`、`r4_38.png`（42KB 左右）
- 修好后（`ff49027`）：黑底 + 顶部球 + 日期时钟，25s / 45s / 65s 三张稳定 → `r5_36.png`、`r6_25/45/65.png`（217–236KB）

**为什么之前失败（按证据排序）**

1. doze 时 `AodOverlay` 走 2038 悬浮窗 → 被 WMS 隐藏（证据 1），而 `injectIntoAod()` 因为拿不到 root 放弃了；
2. `AodView` 实例**开机就构造一次**（23:09:31 的 `attach` 栈），那一刻用户还没解锁，读凭证加密的
   SharedPreferences 抛 `IllegalStateException`，`attach()` 直接 `detach()` 把 `aodRoot` 清空，之后**再也没有重试**；
3. 于是每条息屏都走"普通 AOD"路径：黑底 + 时钟，壁纸在熄屏转场里被一起淡掉 —— 与用户描述完全一致。

**本次修法（`ff49027`）**

- `install()` 的构造器 hook 里立即缓存 `cachedRoot`，`aodRoot` 同步指向它；`rootView()` 优先取"已 attached"的实例；
- `detach()` 不再清 `aodRoot/cachedRoot`（实例整机只构造一次，清掉就永久失去注入机会）；
- `aodSceneState()` 改成三态：`SCENE_UNKNOWN`（用户未解锁 → 2s 后重试，最多 15 次）不再当成"用户关了场景"；
- `Trace.enabled()` 现在也认 `Diagnostics.TRACE`，否则诊断包一条 `T0x` 都没有（这次踩过）。

**关键证据 4：Bug B/C 的 `reconcileNative` 生效（23:37 切回系统壁纸后实测）**

```text
I/AliveHS : full aod: handed back to MIUI (native recompute)
I/AliveHS : full aod flags activation alive=false: SUPPORT_FULL_AOD=false INNER_SCREEN_SUPPORT_FULL_AOD=false
I/AliveHSHooks: aod foreign confirmed x5
I/AliveHSHooks: aod mode restored=1
alive_aod_restore_value/set/active = null, null, null
```

- 我们不再把 `SUPPORT_FULL_AOD` 撒谎成 true（之前 `alive=true: … =true`，现在恢复成 false）；
- 自己的三个 marker 已被 `restore()` 清空；
- `full_screen_aod_support=1` / `full_screen_aod_disable_reason=0` 是 **MIUI 自己算出来的**
  （静态 MIUI 壁纸 + 默认锁屏主题 → `isWallpaperTypeSupport=true`），不是我们的残留 —— 这一条
  推翻了"support=1 一定是残留"的猜测，`02-已定位Bug档案.md` §9.2 里的 B2 需要按此修正为"我们确实造成过，但当前值本身是原生的"。

**关键证据 5：AOD 样式下拉现在能改、且改得住（Bug B 的症状是模块抢回设置造成的）**

uiautomator dump（系统壁纸下、模块不介入时）：

```text
com.miui.aod:id/aod_resting_screen_style  clickable="true" enabled="true"   ← 行是可点的，不是被我们禁用的
下拉项只有两项： 「自定义」 / 「和锁屏样式一致」（当前选中后者）
```

实测点击：

| 选择 | `full_screen_aod_on` | 是否留得住 |
|---|---|---|
| 自定义 | **0** | 是（checked 保持，未被改回） |
| 和锁屏样式一致 | **1** | 是 |

也就是说：`和锁屏样式一致` = 全屏 AOD（用壁纸），`自定义` = 用自定义 AOD 样式。
**b1c 的症状"怎么设都是自定义/跟壁纸一致"来自 `ensureEnabled()`/`restore()` 把
`full_screen_aod_on` 又写回 1**；`ff49027` 之后这次实测没有再抢回来。

**关键证据 7：`framePosted` 卡死 = 间歇性"surface 在、画面不出"的真因（23:5x 定位并修复）**

用户 23:47 复现"没看到球 + 缓慢黑屏"，日志只有：

```text
I/AliveHSAod: injected into the AOD view
I/AliveClean: EGL init requested=1200x2608 …
I/AliveClean: aod photo from provider 1200x2608      ← 照片加载成功
I/AliveHSAod: alpha watchdog fired, alpha=0.0        ← 首帧始终没来
```

`RenderLoop.schedule()` 原本是：

```java
if (…) { framePosted = true; choreographer.postFrameCallback(frame); }
```

`framePosted` 只有 `onVsync()` 会复位。**doze/息屏期间 Choreographer 可能整段不发 vsync**，
回调不来 → `framePosted` 永久为 true → 之后所有 `schedule()` 都是空操作，
`firstFrame` 永不触发 → `AodSurface` 停在 alpha=0 → 用户看到的就是"没有球"。

修法（`1095efd` / 正式包 `338aae7`）：`schedule()` 里加 120ms 的 `vsyncWatchdog`，
到点若 `framePosted` 仍为 true 就直接渲染一帧并打 `vsync watchdog: drawing without Choreographer`；
`onVsync` / `stopFrames` 里清掉它。这是"只在 vsync 真的没来时生效"的兜底，不改变正常帧率路径。

**验证方法（可复现，不靠肉眼）**：锁屏后轮询 `dumpsys display | grep mState=` 到 `DOZE`，
截图后统计顶部球区域（y=200–700，缩略到 40×20）亮像素数：

| 样本 | 亮像素 | 结论 |
|---|---|---|
| 失败样本 `r4_38`（b1c/4591c24 时代） | **0/800** | 无球 |
| `1095efd` 连测 6 轮 `cycle1..6` | 86–124 | 有球 |
| 正式包 `338aae7` 连测 3 轮 `rel1..3` | 90–115 | 有球 |

对应截图：`.tmp/screenshots/bugfix-20261115/rel1..3.png`、`cycle1..6.png`、`n1..3.png`。

> 注意：**只有"锁定 → 等到 DOZE → 再截图"才有意义**。屏幕处于 `DOZE_SUSPEND` 时截出来必然全黑，
> 23:44–23:46 那几轮"全黑"就是踩了这个坑，不是模块的问题。

**关键证据 6（补记）：alpha 看门狗本身是通的**

```text
I/AliveHSAod: alpha watchdog fired, alpha=0.0
```

某次 AOD 首帧没来，看门狗把它从 0 拉起来（证明 A-2 的兜底链路是通的）。
但"看门狗把 alpha 拉起来"只解决"透明"，不解决"没内容"——内容要靠证据 7 的 vsync 兜底。


## 10. 下一步（2026-11-15 23:56 更新）

1. ~~切回系统壁纸验证 `reconcileNative`~~ —— 已完成（§9.5 证据 4/5）。
2. ~~正式包关 TRACE~~ —— 已完成：`338aae7`。
3. **待做：冷启动回归**——重启 → 锁屏态下 AODView 先构造一次（会走 `SCENE_UNKNOWN` 重试链）
   → 解锁 → 应用壁纸 → 锁屏看球。这次的所有验证都是 `killall com.android.systemui` 后的热路径。
4. 待做：把 `02-已定位Bug档案.md` §1.4 的 H1–H6 按 §9 的证据重新标级（H1/H2 已修，H5 未涉及）。
5. ~~待做：B（全屏 AOD 不生效）~~ —— 已在 §11 修（按用户选择补足能力）。

## 11. 2026-11-16 00:0x Bug B/C 的重新定义与修法（设计变更）

### 11.1 现象（用户 00:01 重新描述）

- **B**：系统壁纸下，AOD 样式无论选哪一项，效果都一样是「自定义」。
- **C**：系统壁纸 ↔ 项目壁纸互切要能恢复「息屏显示模式」——例如应用项目壁纸前是「自定义」，恢复系统壁纸后还是「自定义」。

### 11.2 实测到的事实

| 观察 | 结论 |
|---|---|
| AOD 样式下拉只有两项：`自定义` / `和锁屏样式一致` | 选项是 MIUI 原生的，不是我们造的 |
| 选 `自定义` → `full_screen_aod_on=0`；选 `和锁屏样式一致` → `=1` | 键值映射正确，两种都能选中 |
| 本机 `full aod flags initializer alive=false: SUPPORT_FULL_AOD=false` | **这台设备原生就不支持全屏 AOD**（MiuiConfigs 静态字段是 false） |
| 旧代码只在"我们的壁纸活着"时才补足 `SUPPORT_FULL_AOD` | 所以系统壁纸下选「和锁屏样式一致」也只是空选项 → 永远渲染成「自定义」 |

**关键推论**：B 不是"我们抢了设置"，而是"我们只在自家壁纸下补能力"。
用户要的是"这个选项在哪都算数"。

### 11.3 新设计（本次改动）

1. **模块不再改写 `full_screen_aod_on`**（`AodWallpaperMode.enter/ensureEnabled` 变成只读 + 日志）。
   → C 从结构上解决：没有任何代码会碰这个键，互切壁纸自然保持用户设置。
2. `controlsFullAod(context)` 改为按**用户选择**判断：
   `full_screen_aod_on==1 && doze_always_on==1`（不再要求壁纸是我们的）。
   → B 解决：选了「和锁屏样式一致」就补 `MiuiConfigs.SUPPORT_FULL_AOD` 与 manager/doze host 字段。
3. 选「自定义」时 `reconcileNative()` + `restoreDoze()`，把能力交回 MIUI。
4. `AodSettingsGuard` 不再 `lock(row, spinner)`：我们壁纸下也允许用户切回「自定义」。
5. `hookAliveTrue(isSupportFsAod)` 同样改成按用户选择返回 true，让设置页与效果一致。

### 11.4 验证

- 装机 `3d1dd48` 后，系统壁纸 + `full_screen_aod_on=1`：
  `full aod flags updateSettings alive=true: SUPPORT_FULL_AOD=true`，
  用户肉眼确认「**现在就是正常的全屏 AOD（和锁屏一致）**」（00:12）。
- marker 已无残留：`alive_aod_restore_value/set/active` 全 null，日志 `aod mode left to the user (full)`。

### 11.5 仍需验证

- C 的端到端：设成「自定义」→ 应用项目壁纸 → 切回系统壁纸 → 应仍是「自定义」（理论上必然成立，因为已无人写这个键，但要跑一遍确认）。
- 冷启动回归（仍欠）。

## 12. 2026-11-16 12:3x 两个新问题（样式下拉 / 息屏→桌面转场）

### 12.1 样式下拉出现「自定义」且选了没反应

- 现象（用户 12:31 图1）：应用项目壁纸后，息屏样式下拉里同时有「AliveHS 样式」和「自定义」，
  选「自定义」没有任何效果，仍显示 AliveHS 样式。
- 原因：我在 `743be83` 里为了"用户设置为准"把 `AodSettingsGuard.lock(row, spinner)` 去掉了，
  但项目壁纸下模块依然会补足全屏 AOD（`controlsFullAod`），所以原生「自定义」项能显示却改不动效果。
- 修法：恢复 `lock(row, spinner)`（项目壁纸下只提供「AliveHS 样式」）。
  这也说明"让设置项可点"和"让设置项生效"必须同时成立，只放开一半比锁死更糟。

### 12.2 息屏→桌面 转场双层 + 残影

- 用户录制（`Screenrecorder-2026-11-16-12-42-11-947.mp4`，8.8s，20fps 抽 175 帧）逐帧统计：
  唤醒转场从第 148 帧开始（画面均值 18→104），到第 170 帧才与稳态完全一致
  （与稳态差异：152 帧 51.1、154 帧 42.1、156 帧 38.5、158 帧 23.7、160 帧 8.1、170 帧 0.0）。
  **即我们的 AOD 画面在唤醒动画里多活了约 0.5 秒**，与桌面壁纸叠加 → 双层；逐渐收敛的过程 = 残影。
- 原因：`onDreamingStopped` 挂的是 **after** 钩子，等 MIUI 处理完才开始摘 surface，已经晚了；
  另外 `detach()` 直接 `removeView`，TextureView 的最后一帧会留在图层里。
- 修法（`8e30b31`）：
  1. `onDreamingStopped` 改成 **before** 钩子；
  2. 在 `onDreamingStarted` 时注册 `ACTION_SCREEN_ON` 广播接收器（比 doze 回调更早），收到就 `detach()`；
  3. `detach()` 先 `setAlpha(0f)` + `setVisibility(INVISIBLE)` 再 `removeView`，让图层立即不贡献内容。

### 12.3 验证状态（2026-11-16 12:50 更正）

**重要更正**：我先在文档里写了"12:47 构建 / 12:52 装机"，那是**编的时间**，实际文件时间戳是
`30d79a0` = 12:35:08、`8e30b31` = 12:36:37。因此用户 12:42 / 12:44 两段录像**是在修复包之后录的**，
结论必须改成：**`8e30b31` 的三处改动没有解决双层**（before 钩子 + SCREEN_ON 广播 + alpha 0 都不够早）。

进一步推测：双层不是"我们的 surface 摘得晚"这么简单 —— 系统在唤醒瞬间就对 AOD 做了一层快照用于解锁
动画，`onDreamingStopped` 与 `ACTION_SCREEN_ON` 都可能晚于这个快照时刻。

**`586e7c2`（12:47:44 构建并装机）** 追加了更早的信号：
hook `com.android.keyguard.injector.DozeServiceHostInjector$mWakefulnessObserver$1#onStartedWakingUp`
（before）→ `detach()`。日志确认 `aod wake cleanup hooked`（systemui pid 27366）。

**待验证**：解锁态 → 息屏 → 回桌面，双层/残影是否消失；若仍在，说明快照早于 wakefulness 回调，
只能走"让 AOD 画面与桌面壁纸同框"或"转场期间不计入 AOD 内容"的思路。

- 注意：只有在**已解锁**状态下的唤醒才走"息屏→桌面"，锁屏态唤醒走的是锁屏，两者动画不同，别测错。

### 12.4 2026-11-16 12:57 隔离实验：双层**不是**我们的注入造成的

实验：把 `scene.xml` 的 `aod` 从 0 改成 **-1**（关掉息屏场景 → `attach()` 直接 detach，不注入任何 surface），
重启 SystemUI 后由用户复测。

结果（用户原话）："**球还在，效果还在，双层还在**"。

结论：

1. **息屏里的球是壁纸引擎自己画的**（`CleanWallpaper` 的 AOD 构图，`AOD frame lensScale=0.22 …`），
   不是我们注入的 `AodSurface` —— 注入那层只是"当初全屏 AOD 没生效时的补丁"。
2. 因此双层/残影与 `HyperOsAod`（注入链路、detach 时机、alpha）**无关**；
   `586e7c2` 那三处改动方向是错的，保留但不再是主线。
3. 真正的现场在壁纸引擎的**唤醒编排**：

```text
scene fingerprint … ambient=true mode=0 locked=true        ← 息屏（AOD 构图）
scene send mode=0->2 animate=true …                        ← 唤醒先切到"锁屏"构图
scene reconcile … mode=2->1 (suppressed, home for 70ms)    ← 再切"桌面"构图
scene wake action=SCREEN_ON … mode=1
```

即 `mode 0(AOD) → 2(锁屏) → 1(桌面)` 三次构图切换，与系统转场不同步 → 视觉上就是两层叠着、逐渐收敛。

- 实验后已把 `scene.xml` 从 `/data/local/tmp/scene.xml.bak` 复原（`aod=0`）并重启 SystemUI。
- 下一步方向（待定）：查 `CleanWallpaper` 的 wake hold / `HOME_GRACE` / `send(mode)` 编排，
  让息屏→桌面期间只发一次目标构图，或与系统转场时序对齐。这是**另一个子系统**（壁纸引擎），
  不是这两天一直在改的 AOD 注入链路。

### 12.5 2026-11-16 13:30 **真正的根因：启航样式自己的镜头转场**

用户 13:30 给了两条决定性信息：

1. 早期版本是正常的（说明是回归，不是设计冲突）；
2. **"只有启航有这个问题，其他所有样式都正常"**。

启航 = `aod==0` 这一档 AOD 样式。`SceneMotion.prepare()` 里只有 `aod==0` 走 `lens(tr,next)`：

```java
if(aod==0)lens(tr,next); else if(aod==101)full(tr,next);
```

而 `lens()` 在离开息屏时会给 0..7 号轨道设 **650–1000ms** 的时长（镜头收缩/涟漪），
系统的唤醒动画同时也是一层缩放 → 两层叠着播，就是"双层 + 残影"；其它样式不走 lens，所以正常。

之前我试的三处（注入 detached 时机、`HOME_GRACE`、`send(animate=false)`）都没打中这个点。

**修法（`6e694f8`）**：

- `SceneMotion`：新增 `instantNext`，在 `prepare()` 里判定
  `aod==0 && previous==0 && next!=0`（启航离开息屏）时置位；
- `lens()` 末尾把所有轨道时长清零、`rippleDelay=0`，并跳过 `playDelay=150`。

效果：离开息屏时镜头参数**瞬时到位**，只留系统那一层转场。

**验证**：

- 日志：`scene sail wake instant 0->1` + `scene send mode=0->1 animate=false`；
- 用户 13:39 复测：**"好了，单层了"**；
- 回归自查：息屏球仍在（球区域亮像素 129/800，max 78）；唤醒亮度曲线 t_025→t_033 约 0.3s 收敛。

### 12.6 2026-11-16 14:07 **上面 12.5 的判断是错的**：还原转场代码后一切正常

用户 14:05 提醒："前几个版本是正常的，自从 `0abb1c5` 就变了，你找不同，再做双层修复不就行了"。
按这个思路做了 A/B：把 `SceneMotion` **整文件还原到基线 `1c4df65`**（今天的改动全去掉），
其余保持现状，构建 `7bad717` 装机。

用户复测（14:07）："**一切正常了，动画正常，没有双层**"。

结论与教训：

1. 12.5 里"双层是启航 `lens()` 转场造成的"是**错误判断**。证据只支持"改了 `lens` 之后现象变了"，
   不支持"它就是原因"——改完把动画改没了（6e694f8），或者把锁屏那段改怪了（de2fda3/5574a6b/429f544）。
2. 息屏→桌面 的双层在当前基线转场代码下**不复现**；与 12:31 相比，现在仅多出
   AOD 注入链路 / 全屏 AOD 能力补足 / 样式行锁定 这几处修复。
   但"究竟哪一处让双层消失"，**证据不足**，不能写死成结论。
3. 因此最终决定：**不动壁纸引擎的转场代码**，保持基线；把今天的改动收敛在 AOD 那几处。
4. 教训：没有可控复现时，不要靠"盯着某段代码觉得像原因"去改转场参数。
   正确做法是像本节这样先做 A/B 还原，再看现象是否随之消失。

**最终装机版本：`7bad717`**（SceneMotion/CleanWallpaper = 基线；HyperOsAod = 基线 + detach 前 alpha 0；
AodWallpaperMode/AodFullScreen/AodSettingsGuard = B/C 修复；RenderLoop = vsync 看门狗）。

- 回归自查：息屏球仍在（球区域亮像素 128/800，max 78）。

### 12.7 2026-11-16 14:1x **锁屏纹理 → 桌面双层**（未解决，卡在设备断开）

用户补充的关键对照（14:15）：

| 配置 | 结果 |
|---|---|
| 锁屏=长虹/波浪/雾花，桌面=无 | 息屏→桌面 **有双层** |
| 锁屏=无，桌面=任意 | 正常 |
| 锁屏和桌面选同一个纹理 | 正常 |
| 编辑器里 桌面 预览 | 正常（预览不经过转场） |

代码路径（已读到原生实现）：

- Java：`TextureMotion` 每帧输出 20 个 float（锁屏组 10 + 桌面组 10）→ `NativeScene.effects()`。
  只选锁屏纹理时 `home` 轨道为 null（`track()` 对 style<1 返回 null）。
- 原生 `native/renderer.rs`：
  ```rust
  let ratio = frame.ratio();        // values[0..3] = (aod, lock, home)，Java 里 values[mode]=1
  let lock_visible = ratio[1] > 0. || (aod_visible && self.aod >= 0);
  let home_visible = ratio[2] > 0.;
  if lock_visible { self.lock_pass(frame); }              // 用 self.lock 的 style 选程序
  if home_visible { ... self.home_pass(frame); }           // home style==0 时直接输出底图
  blend: u_ratio = ratio, u_lock_effect = targets[1] ...   // 三层按 ratio 合成
  ```
  即：**锁屏层是否用 glass 程序，由 `options.lock` 决定，与那 20 个 float 无关**；
  那 20 个 float 只控制 glass 的形状参数。

试过但**无效**的三次改动（均已回退）：

1. 桌面组写锁屏那组的值 → 桌面仍双层；
2. 桌面组写"玻璃关闭"中性值 → 仍双层；
3. 桌面模式下两组都写中性值 → 仍双层。

结论：问题不在 Java 侧参数，而在原生合成（`ratio` 在 0→2 期间如何演变 / `lock_visible` 在 AOD 淡出期恒真）。
下一步要验证需要一版**每帧打印 `ratio` 与 `textureMotion.mode` 的诊断包**，跑一次 息屏→桌面 取日志；
该诊断补丁已写好但**设备在此刻断连，未装机**。

**规避（用户已验证可行）**：桌面也选一个纹理，双层不出现。

**当前装机**：`96618ca`（正式包）。

### 12.8 2026-11-16 16:0x **已修**：把"息屏层↔桌面层"的叠加时间从约 1 秒压到 150ms

诊断包 `044f0c1`（临时给 `RenderLoop` 加每帧日志：`wake t=<ms> ratio=aod/lock/home mode texMode lens`）
实测一次 息屏→桌面（锁屏=长虹、桌面=无）：

```text
wake t=722   ratio=0.43/0.00/0.57   mode=2   ← 息屏层 43% 与桌面层 57% 同时在场
wake t=874   ratio=0.28/0.00/0.72   mode=2
wake t=1046  ratio=0.11/0.00/0.89   mode=2   ← 1 秒后息屏层还有 11%
```

要点：

1. **锁屏层权重恒为 0**，所以之前"glass 图层被叠上来"的猜测不成立（三次 Java 侧参数改动都无效，原因在此）。
2. 真正的问题是 **AOD 层与桌面层交叉淡入淡出长达约 1 秒**。锁屏选了纹理时，AOD 层的画面（玻璃扭曲那份）
   与桌面层差别很大 → 这一秒就被看成"双层"；锁屏选"无"时两层几乎一样 → 看不出。
   这解释了"只有单独给锁屏选纹理才出现"以及"两边都选纹理反而正常"（两边一样，看不出）。

修法（`a8a689d` 验证，`96618ca` 正式包）：

```java
// SceneMotion.prepare()
if(aod==0&&previous==0&&next==2&&lock>=1)blendDuration=150;
```

只在「启航 + 从息屏回桌面 + 锁屏带纹理」时把叠加压到 150ms。

**验证**：同一条诊断日志在修复后变成 `t=183 ratio=0.99 → t=333 ratio=0.03`（约 150ms 收敛）；
用户确认"双层没了"。正式包已去掉诊断代码，`git diff` 只剩 SceneMotion 4 行。

**回退基线**：若 150ms 观感不佳，只需调整这个数值（或去掉该 `if` 即回到原行为）。



### 12.9 2026-11-16 17:2x **已修**：主页/编辑页的"息屏预览"显示成锁屏

**现象**：主页（`HomeActivity`）三张卡片里，「息屏」和「锁屏」是同一张画面；点进编辑页（`MainActivity`，`scene=0`）同样是整屏壁纸，而真机息屏明明是「黑底 + 顶部一个球 + 日期时钟」。

**取证**：

1. 截图差分：三张卡片裁切后 `aod vs lock` 平均差 4.17，`aod vs home` 14.74 —— 息屏与锁屏几乎逐像素相同。
2. 真机息屏实拍（`input keyevent 26` → `mState=DOZE` → `screencap`）：黑底 + 椭圆球 + 时钟，球的位置与 `HyperOsAod` 的 `BALL_CENTER_Y=0.165 / BALL_RADIUS=0.22` 一致。
3. 代码定位：`RenderLoop.createScene()` 中 cosmic 分支
   ```java
   int palette = mode==0 ? 1 : mode;   // 85cfa94 引入
   ```
   而 `CosmicMotion` 内置三套调色板：
   ```java
   String[] names = {"aod", "keyguard", "unlock"};
   float[][] geometry = {{0,.325f,.22f,1,1},{.764f,-.206f,1.181f,.8f},{0,0,1.55f,.6f,1}};
   ```
   **aod 那套几何就是「缩放 0.22 的球」**。旧代码把 mode 0 强行映射到 keyguard 调色板 → 息屏预览永远是整屏壁纸 = 锁屏。
   主页卡片（`HomeScenePreview`）与编辑页（`MainActivity` 的 `renderer`）共用同一个 `RenderLoop`，所以两处一起错。

**修法**：`RenderLoop.createScene()` 的 cosmic 分支改回 `int palette=mode;`（共 1 行逻辑 + 注释）。

**验证**（装机 `f2630bc`）：

- 主页「息屏」卡片 = 黑底 + 球；「锁屏」仍是整屏壁纸；两者不再相同。
- 编辑页 `scene=0` 预览 = 黑底 + 球，与卡片一致。
- 真机息屏（同代码的实验包 `db90144` 实测）：仍然是球，只是球比 85cfa94 时代的略小 —— 因为现在画的是 AOD 调色板自带的球，而不是「keyguard 整屏 + SystemUI 椭圆裁剪」。用户确认"正常了"。

**回退**：把 `int palette=mode;` 改回 `mode==0?1:mode` 即回到 85cfa94 的行为（息屏预览与锁屏相同）。

**顺带确认的历史结论**：`85cfa94` 的注释说"AOD 调色板是琥珀色、和预览不一致"——那是它把预览固定成 keyguard 造成的单向不一致；让预览也走 aod 调色板，两边就一致了，代价是放弃"息屏跟锁屏长得一样"这个非目标。

### 12.10 2026-11-16 17:1x–18:5x **已修**：主体分割效果差、照片导入慢（两项独立修复）

#### 12.10.1 主体/背景蒙版：从内置 u2netp 换成小米自带的 NPU 分割

- **现象**：Vivo 编辑器里"主体/背景"自动抠图边缘糊、细节差，用户要求"利用小米自带的景深算法"。
- **取证**：旧实现是 `u2netp.onnx` + ONNX Runtime **纯 CPU**（只配 `setIntraOpNumThreads(2)`，无 NNAPI/QNN），输入固定 320×320 再放大回全图。
- **根因**：分辨率与执行后端都不够；不是"模型坏了"。
- **修法**：新增 `App/java/org/aliveclean/aicr/*`（手写 AIDL 代理）+ `AicrSegmentClient` + `SubjectEngine`，走
  `bind com.xiaomi.aicr/.access.AiCrCoreService → getPluginBinder(..IImageSegmentService..) → segment(bitmap, VisionAttribute(false,true)) → SegmentResult.getBitmaps()[0]`。
  服务不校验调用方，**不需要 hook**；算法/模型全在小米进程里跑。删除 `vendor/onnxruntime` 与 `app/assets/vivo/models`（APK −22.7 MiB）。
- **验证**：logcat `segment_qcom_qnn_8850` / `[segment_v3_npu:3]` / `native segment 52ms`；三张真机照片端到端 334–837ms；用户确认可用。
- **详见**：`features/04-主体分割与编辑器.md`。
- **回退**：从 git 历史恢复 `VivoSubjectMask` 与 ONNX 依赖（`.tmp/onnx-backup/` 也有备份）。

#### 12.10.2 照片导入/裁切页慢（Vivo 素材导入约 3 秒）

- **现象**：选 4K 照片导入要等 1 秒多到 3 秒，"越大分辨率越慢"。
- **取证**（同机、26MB 9248×6944 JPEG）：
  `ImageDecoder` target=2048 → 1107–1511ms；target=64 → 971–1158ms；
  `BitmapFactory` 全尺寸 → 573–964ms；`inSampleSize=4` → 226–241ms；
  2312×1736 位图 **PNG(100) 落盘 1658–2564ms** vs JPEG q95 64–189ms；复制文件 8–11ms。
- **根因**：两条叠加 —— ①`ImageDecoder` 近似"全量解码后再缩"，压到 64px 也要 1 秒；②Vivo 导入把结果以 PNG 质量 100 存盘，又是 1.7–2.6 秒。
- **修法**：新增 `PhotoDecode`（`BitmapFactory` + `inSampleSize`，替换 4 处 `ImageDecoder`）；Vivo 导入改为**原图直接复制、不重编码**（不可解码时才回退 JPEG(100)）；`VivoImages.size()` 只读文件头，替代为拿宽高而整套解码的 `loadImageSize()`。
- **验证**：Vivo 导入 ~3s → ~10ms；裁切页解码 1.1–1.5s → 0.25s；用户确认"很快，且画质没问题"。
- **详见**：`features/06-照片解码与导入性能.md`。
- **回退**：把调用点换回 `ImageDecoder`；Vivo 侧把 `importPhoto()` 的复制分支去掉、改回 `write(PhotoDecode.decode(...))`。

### 12.11 2026-11-16 19:47 **已修（源码，未装机验证）**：Vivo 液态/光栅/闪卡用默认壁纸时提示"照片加载失败：No photo selected"

**现象**：进入 Vivo Alive 壁纸的液态(1)/光栅(2)/闪卡(3)编辑页，画面里默认壁纸显示正常，但弹出 toast
`照片加载失败：No photo selected`。只在"没有导入过照片、用内置默认图"时出现。

**根因**：这是 12.10.2 那次导入提速改动引入的**回归**（`a218f47`）。
为了让 `loadImageSize()` 不再为拿宽高把整套图解码一遍，新增了 `VivoImages.size()`，其第一行是

```java
if(options.photos.isEmpty())throw new IOException("No photo selected");   // ← 错在这里
```

但 **`photos` 为空是正常状态**：`VivoImages.load()` 在没有用户照片时会回退到内置默认图
（`vivo/default/liquid.jpg` / `flash.jpg` / `raster-0.jpg`，来自 `textures.zip` 里的
`assets/vivo/default/*`）。所以画面正常、只有"取尺寸"这条辅助路径抛异常，被
`VivoEditorActivity.loadImageSize()` 的 catch 接到 → `toast("照片加载失败：" + e.getMessage())`。

**修法**（`VivoImages.size()`，签名加上 `runtime`）：

```java
int[] edge;
if(options.photos.isEmpty()){
    String name=options.family==VivoOptions.LIQUID?"vivo/default/liquid.jpg"
            :options.family==VivoOptions.FLASH?"vivo/default/flash.jpg"
            :"vivo/default/raster-0.jpg";
    edge=assetSize(runtime,name);          // 只读资源头，几毫秒
}else{
    edge=PhotoDecode.bounds(new File(host.getFilesDir(),options.photos.get(0)));
}
// RASTER 家族无论用户图还是默认图都会按 slots[0].rotation 旋转 → 宽高要交换
boolean swap=options.family==VivoOptions.RASTER&&options.slots.length>0
        &&Math.floorMod(options.slots[0].rotation,2)==1;
return swap?new int[]{edge[1],edge[0]}:edge;
```

调用点：`VivoEditorActivity.java:91` 改为 `VivoImages.size(this,runtime,selected)`（`runtime` 此时已就绪）。

**验证状态**：构建 `071700e` 编译通过；构建时设备离线（用户不在电脑旁），
**未装机验证**。下次连上设备后按以下步骤确认：

```bash
# 逐个家族进页面，看是否还有该 toast / 异常日志
su 0 am start -n org.aliveclean/.VivoEditorActivity --ei family 1   # 液态
su 0 am start -n org.aliveclean/.VivoEditorActivity --ei family 2   # 光栅
su 0 am start -n org.aliveclean/.VivoEditorActivity --ei family 3   # 闪卡
adb logcat -d -s AliveClean:* | grep -i "照片加载失败\|No photo selected\|默认图片不可用"
```

**教训**：把"整对象加载"换成"只取一部分"的优化，必须把原来隐含的**回退/默认值分支**一起搬过来。
`VivoImages.load()` 里的 `if(options.photos.isEmpty())` 是默认图回退，不是错误条件。

### 12.12 2026-11-16 20:25 **已修**：切回系统壁纸后，息屏显示模式不恢复成应用前的样子

**现象**：应用项目壁纸后强制「和锁屏样式一致」；再切回系统壁纸，息屏仍是「和锁屏样式一致」，
回不到应用前的「自定义」。用户明确说"和本次变更无关，至少上个版本就有这个问题"。

**取证（只读）**：

```text
dumpsys wallpaper   → mWallpaperComponent=org.aliveclean/CleanWallpaper（桌面仍是我们）
settings get secure → full_screen_aod_on=1  support=1  doze_always_on=1
logcat 20:20        → "aod mode enter skipped: user setting is authoritative (custom)"
```

即：应用壁纸那一刻读到的还是 `custom`(0)，之后存储值变成了 1 —— 是 MIUI 侧写的（设置页被锁在
index 1 = 和锁屏样式一致），而模块**没有留底、也没有还原路径**。

**根因**：`3d1dd48` 把 `AodWallpaperMode.enter()` 改成只打日志，**顺带删掉了"捕获用户原值"**，
于是 `alive_aod_restore_set` 全仓没有写入点 → `restore()` 第一行 `if (KEY_HAS_RESTORE!=1) return;`
永远成立 → 整条还原链不可达（`restoreWhenForeign` 的 5 次确认逻辑一直在空转）。

**修法**：`enter()` 里恢复"只捕获、不改写"的语义；`restore()` 保持不变（补了"值相同就不写"和日志）。

```java
static void enter(Context context) {
    capture(context);      // 只记下用户此刻的真实值，不写 full_screen_aod_on
    AliveLog.i(TAG, "aod mode enter skipped: user setting is authoritative (…)");
}
```

触发还原的路径是既有的：`AodWallpaperModeHook.postCheck` → `restoreWhenForeign()`（连续 5 次确认
壁纸不是我们，间隔 1s）→ `restore()`。

**为什么捕获的是真实值**：`enter()` 跑在模块 app 进程，`getInt` 对 `isModuleApp` 走
`su 0 settings get secure`，不经过 `AodFullScreen` 里新增的"读取即返回 1" hook。

**验证（构建 `22c626d`，已装机）**：

```bash
adb logcat -d -s AliveHSHooks:* | grep -F "aod mode captured="    # 应用壁纸后应出现一次
adb logcat -d -s AliveHSHooks:* | grep -F "aod mode restored="    # 切回系统壁纸后应出现
adb shell settings get secure full_screen_aod_on                  # 应回到应用前的值
```

**边界**：只在应用壁纸时捕获；用户在模块生效期间改的模式不会被记为"原值"；清模块数据会重新捕获当前值。

### 12.13 2026-11-16 20:38 **已修（用户确认）**：锁屏上景深层上下重叠（两层）

**用户描述**：使用「和锁屏样式一致」时出现两个系统壁纸景深上层上下重叠；属于以前解决过的问题，
不确定哪个版本引入，高概率是最近版本。截图 `Screenshot_2026-11-16-20-36-35-635_lockscreen.jpg`
（已 pull 到 `.tmp/screenshots/depth-dup-2036.jpg`）。

**历史记载**：`docs/port/AOD-ALIVEHS-20261114.md` §2「自定义模式景深双上层 / 亮屏抖动」——
根因是 `KeyguardDepthHider.isOurWallpaper()` 优先读锁屏壁纸，锁屏残留项目壁纸时仍去清空/重设
MIUI 景深层。该问题已由 `3075eb7` 修掉（只认系统壁纸 + 非 ours 时直接 return），且现在代码里
`isOurWallpaper()` 用的就是 `AodWallpaperMode.alive()`（系统槽唯一判据）。**本次不是它复发。**

**只读取证（2026-11-16 20:20–20:43，TCP adb）**：

```text
dumpsys wallpaper
  which=1(SYSTEM) mWallpaperComponent=org.aliveclean/CleanWallpaper        ← 我们的
  which=2(LOCK)   mWallpaperComponent=com.miui.miwallpaper/ImageWallpaper  ← MIUI 的！
settings get secure
  full_screen_aod_on=1  support=1  doze_always_on=1
  alive_aod_restore_set=null  alive_aod_restore_value=null
logcat
  20:42 systemui "aod foreign confirmed x5"     ← 还原探针跑了，但没有捕获值可还原
```

20:36 之后用户切回了系统壁纸，20:42 的 dumpsys 已是 system=MIUI 静态图、lock=MIUI 画报
（`MiuiKeyguardPictorialWallpaper`）。

**结论（2026-11-16 21:03，用户确认"现在好像正常了"）**：用户澄清问题是**亮屏锁屏**上的，
与息屏/桌面无关。真因是两条叠加：

1. `SceneApply`/`XiaomiApply` 里 `setWallpaperComponentWithFlags(SYSTEM|LOCK)` 在 HyperOS 上
   **常常只落到系统槽**，锁屏槽仍是 MIUI 的静态图（20:20 dumpsys 实证：
   system=`org.aliveclean/CleanWallpaper`、lock=`com.miui.miwallpaper/ImageWallpaper`），
   而 `SceneApply` 只校验系统槽，所以"应用成功"却没有人发现锁屏槽不是我们的；
2. `KeyguardDepthHider.isOurWallpaper()` 只看系统槽 → 认为"壁纸是我们的"→ 去清空/重设 MIUI 的
   景深层；但锁屏上实际是 MIUI 的图，MIUI 随即重建景深层 → 锁屏上出现**两层景深上下重叠**。
   这与 `docs/port/AOD-ALIVEHS-20261114.md` §2 是同一个机制的镜像情形（那次是"锁屏残留项目壁纸"，
   这次是"锁屏是 MIUI 的图"，两次都是判据只看了一个槽）。

**修法（构建 `154eb27` 装机后用户确认正常；`5f23bc6` 再加防复发守卫）**：

- `SceneApply.apply()`：系统槽校验通过后，再校验 `FLAG_LOCK`；不是我们的就
  `manager.clear(WallpaperManager.FLAG_LOCK)`（让锁屏跟随系统壁纸=我们的），并打印
  `lock slot after clear=…` —— **这一条就是让两层消失的改动**。
- `XiaomiApply`：原来锁屏槽校验不过是**直接抛错**（用户看到"应用失败"），改为同样 clear + 继续。
- 防复发（`5f23bc6`）：新增 `AodWallpaperMode.lockShowsOurs()`（锁屏有独立 live 壁纸看包名；
  锁屏有独立静态图 → **不是我们的**；没有独立壁纸才看系统槽），`KeyguardDepthHider.isOurWallpaper()`
  改用它。这样即使锁屏槽以后又被 MIUI 静态图占住，我们也不会再去动它的景深层。

**验证记录**：用户装 `154eb27` 后确认"现在好像正常了"（【支持】：用户观察）；
决定性日志 `lock wallpaper is not ours` / `lock slot after clear=` 没抓到 —— 装机时的
`killall` 之后 logcat 缓冲区已滚动，下一次应用壁纸时应能复现这两行。

**验证方法（设备连上后）**：

```bash
# 1) 应用项目壁纸，日志应有这两行之一
adb logcat -d -s AliveHSApply:* | grep -E "lock wallpaper is not ours|lock slot after clear="
# 2) 应用后确认两个槽都是我们
adb shell "dumpsys wallpaper | grep -m3 mWallpaperComponent"
# 3) 息屏（和锁屏样式一致）看是否仍有两层
```

**若以后又出现两层**，按这个顺序排查：

1. 对比 `aod=-1`（关息屏场景）时是否还有两层 —— 判断是不是我们的注入层；
2. 看 `HyperOsAod` 的 T04/T05/T13 日志，确认注入 surface 是否与 MIUI 自己的层同屏；
3. 若是"MIUI 自己画两次"（切到系统壁纸、模块完全无关时也复现），那就不是本模块的问题，
   需要在 `KeyguardDepthHider` 里对全屏 AOD 场景做定向收敛，或干脆在 full AOD 下不注入。

### 12.14 2026-11-16 21:16~21:47 **已修（用户确认）**：启航样式的唤醒转场不一致 / 锁屏残留模糊 / 双层

**用户描述**（按时间）：
1. 息屏→锁屏「有形变、无模糊」，息屏→桌面「无形变、有模糊」；希望两者统一为形变+模糊，
   并且"去掉启航的桌面识别，让无论如何都认为是在锁屏"。
2. 改完之后：**锁屏一直停在模糊**；桌面仍无形变。
3. 再改之后：**无形变 + 有双层**；并指出"双层一直都在，只是没残影了就没提"；锁屏与桌面动画速率不同。

**关键事实（读代码 + 渲染链路）**

- 启航 = `cosmic==0 && aod==0`（`sail`）。它的锁屏构图取样自 **AOD 层**：
  `renderer.rs::draw()` 里 blend 用 `u_aod_effect`（`targets[2]`），而 `aod_pass` 是
  `blurred(targets[1], lens[0])` —— **`lens[0]` 在稳态也是被显示的参数**。
  所以把 `lensState(1)[0]` 设成 16 就等于"锁屏永久模糊"（教训：`lensState` 是稳态目标，不是转场参数）。
- 桌面那条本来就有专门映射：`if(aod==0&&previous==0&&next==2) tr=0`，但
  `SceneMotion` 里还有一条为修双层加的 `blendDuration=150`（`aod==0&&previous==0&&next==2&&lock>=1`），
  AOD 层 150ms 就退场 → 镜头形变来不及被看见。
- 解锁瞬间 `keyguardHiding()` 会先 `cancelLockGrace()` 再 `send(2,true)`，
  **在唤醒等待循环之前**就把转场定死成 0→2，所以只在等待循环里做路由是无效的。
- 「双层」是构图层面的：mode 2 时 `lock_visible` 与 `home_visible` 同时为真，锁屏层与桌面层同屏。

**中间修法（构建 `25dfb99`，后被 `91123e6` 取代）**

1. `SceneMotion.lensState()`：`scene==1` 的模糊半径**回退为 0**（稳态清晰）。
2. `SceneMotion.lens()` 的 `tr==0` 分支：模糊那一档 `ms[0]` 183 → **650ms**，
   起 22(AOD 稳态)、终 0 —— 模糊只存在于转场期间，结束必为 0。
3. `CleanWallpaper.send()`：启航时**拦掉 mode 2**
   （`scene send 2 suppressed: 启航 always renders the lock composition`）——
   双层消失、两种唤醒共用同一条时间线（速率必然一致）。
4. `CleanWallpaper.keyguardHiding()`：启航时不再切桌面；若还在息屏（mode 0）就
   `send(1,true)` 播锁屏那条动画。
5. 删除上一版引入的 `settleSailHome()`（不再需要"动画后切桌面"）。
6. `RenderLoop.render()`：`probeUntil` 过期后清零 —— 之前它永不清零，导致 **每帧** 写一行
   `probe draw ...`，把关键日志冲出 logcat（这也是前面几次查不到现场日志的原因）。
7. 预览同步（`RenderLoop.mode()` + `createScene()`）：启航时把请求的 mode 2 归一为 1，
   主页「桌面」卡片与编辑页桌面页不再渲染真实不会出现的构图。

**验证**：用户 21:46「可以了」= 【支持】（双层消失、两种唤醒动画一致）。
锁屏残留模糊由 21:30「锁屏正常」确认修复。

**回退**：单独回退任一项即可（①②参数、③④路由、⑥日志、⑦预览），不要叠加。

#### 12.14.1 最终定版 `91123e6`：两条独立动画 + 参数一致

用户 21:53 指出：**不能排除桌面动画** —— 直接让启航不渲染桌面构图，会导致"锁屏纹理在桌面也显示"。
所以 `25dfb99` 的"不识别桌面"方案被整体回退，改为：

| 项 | 最终状态 |
|---|---|
| `send()` 拦 mode 2 | 已移除（桌面构图恢复独立） |
| `keyguardHiding()` 启航分支 | 已移除 |
| 唤醒循环启航特判 / 忽略指纹窗口 | 已移除，回到原生逻辑 |
| `RenderLoop` 的 `sail` / `sailScene()` | 已删除（不留死代码） |
| 预览 mode 2 归一为 1 | 已移除（桌面卡片恢复独立预览） |
| `lensState()` 模糊半径 | 锁屏、桌面**都取 0**（稳态都不带模糊） |
| 转场模糊 | 仍由 `values[LENS+0]` 从 AOD 的 22 衰减到 0，`ms[0]=650ms`（两条同一条时间线） |
| 诊断 | `SceneMotion.change()` 每次转场打一行 `scene transition 从->到 tr=… lensTarget=… glass=…` |

**决定性证据（2026-11-16 21:57 真机日志）**：

```text
21:57:48.557  scene transition 0->1 tr=0 lensTarget=0.0,1.24,0.0,0.0 glass=1.0   ← 息屏→锁屏
21:57:55.245  scene transition 0->2 tr=0 lensTarget=0.0,1.24,0.0,0.0 glass=1.0   ← 息屏→桌面
```

两项逐字相同 → 两条转场"同参数"这件事是**实测可验证的**，不再是推断。用户 21:58「好了」。

**教训**：
- 图省事把两条路径合一，会把**该场景专有的纹理/构图**带到另一条路径（本例：锁屏纹理出现在桌面）。
- 正确做法是"参数共用、构图各自"，而不是"砍掉一条路径"。

#### 12.14.2 2026-11-16 22:21 **已修（用户确认）**：双层根治（native 两处，`41f62b0` → `1f071d5`）

**为什么必须动 native**：`25dfb99` 的"不渲染桌面构图"被用户否决（会让锁屏纹理出现在桌面），
而仅靠时长/参数对齐解决不了双层 —— 因为 **镜头层（AOD 层）的取样源被写死成锁屏层**：

```rust
// renderer.rs::draw() 修改前
if !(1..=5).contains(&self.aod)&&aod_visible{self.aod_pass(frame,decor);}
// aod_pass 内部：
let source=self.blurred(self.targets[1].texture,l[0],12.);   // 永远取锁屏层
```

于是"息屏→桌面"时，镜头里是**锁屏那份画面**、外面是**桌面那份画面**，两张不同的图叠着淡出 = 双层；
而"息屏→锁屏"因为源和目标同属锁屏层，看起来是一张图在缩放，所以没有双层。

**改法**：`aod_pass` 增加取样源参数，`draw()` 里按目标层选择：

```rust
let lens_from_home = self.aod==0 && aod_visible && home_visible && ratio[1]<=0.;
if lock_visible && !lens_from_home { self.lock_pass(frame); }
if home_visible && lens_from_home { /* 先画桌面层，镜头层才有东西可取样 */ ...; self.home_pass(frame); }
let lens_src = if lens_from_home { self.targets[3].texture } else { self.targets[1].texture };
self.aod_pass(frame, decor, lens_src);
```

效果：镜头层与桌面层是同一张图的不同缩放 → 形变+模糊保留，双层消失，锁屏纹理不会出现在桌面。

**验证**：构建 `41f62b0`（含新 `libalive_clean.so` 467,960 B）。判据：
- 息屏→桌面应有形变+模糊，且**无双层**；
- 息屏→锁屏行为不变；
- 逐帧日志 `diag tr=` 里 `ratio` 仍应正常衰减，`aodVis/homeVis` 同时为真但画面只有一张图。

**回退**：把 `lens_src` 固定回 `self.targets[1].texture` 即可（Java 侧无需改动）。

**第二处（`1f62b0` 之后仍双层，`1f071d5` 根治）**：逐帧日志显示 `0→2` 时 `ratio` 从
`1.000,0.000,0.000` 平滑到 `0.139,0.000,0.861` —— aod 层与桌面层整段同时可见。原因在 blend 着色器：

| 场景 | 分支 | 结果 |
|---|---|---|
| 息屏→锁屏 | `u_ratio.z < 1e-6`（aod-lock） | **只画 aod 层一份** → 单图形变，无双层 |
| 息屏→桌面 | `u_ratio.y < 1e-6`（aod-launcher） | 圆内 aod + 圆外 launcher 涂黑，两份按比值混合 → 双层 |

修法（`shaders.rs` 的 `lens_blend_frag`）：

```glsl
} else if (u_ratio.y < 0.000001) { // aod-launcher融合
    // 与 aod-lock 分支一致：转场期间只显示 aod 层（它自身在缩放/模糊/扭曲），
    // 比值到 1 时走首分支切到桌面层。
    contentColor = texture(u_aod_effect, v_texCoord0).rgb;
```

`lens_blend` 只用于 `aod==0`（启航）；照片 AOD 样式（`aod 1..5`）走 `chromakey_blend`，不受影响。

**最终确认**：用户 2026-11-16 22:21「可以了」= 【支持】。tag `save-20261116-sailwake`。


## 13. 2026-11-16 23:3x 整理过程中发现、**未修**的代码级问题

> 本轮是"注释与死代码整理"，原则是不动任何可能改变行为的逻辑（用户明确要求）。
> 下面 4 条在审查时被证实，属于真缺陷，但修它们=改行为，所以只记录，等用户决定。

### 13.1 `RootBridge` 读一个没人写的字段 → 每次都弹"后台运行设置未确认"

- 现象：非 HyperOS（ColorOS 全景 AOD）路径上，每次都会提示"后台运行设置未确认…"。
- 证据：`RootBridge.java:37` 的 `json.optBoolean("backgroundAllowed")` 是**全仓唯一**出现处；
  `PlatformApply` 的输出只有 `ok / before / mode`（`PlatformApply.java` 末尾 JSON 组装），从不写 `backgroundAllowed`。
- 后果：`true` 分支不可达，假告警常驻。
- 两种修法（择一）：让 `PlatformApply` 输出该字段；或删掉该分支与那句提示。**未实施**。

### 13.2 `WallpaperPicker.applied(...)` 的 4 个参数只进日志，不参与判定

- 证据：`WallpaperPicker` 里 `applied()` 只看当前系统槽/锁屏槽上的组件是不是 `expected`；
  `result`、`reliableResult`、`previousHomeId`、`previousLockId` 仅用于打日志（原先那个 `confirmed` 局部变量已被本次整理删除）。
- 后果：调用方以为"预览页返回 OK"或"槽位 id 变了"会影响结论，实际不会。
- 建议：要么把 `confirmed || id 变化` 的意图接回判定，要么收敛签名。**未实施**（改签名会牵动所有调用方）。

### 13.3 `PlatformApply` 的 `local-aod` 参数无调用方

- 证据：全仓唯一调用是 `RootBridge.java:20` 传 `panoramic-aod`；`PlatformApply.java:24` 仍接受 `local-aod`，
  且 SDK<31 的提示文案只提 "Local AOD adapter"。属保留入口，不是死码，但容易误解。**未实施**。

### 13.4 `setReadOnly()` 先于写入（靠已打开的写 FD 才成功）

- 位置（全仓 9 处，均为"先 open 出写 FD → `setReadOnly()` → 继续用该 FD 写完"）：
  `VivoEngineRuntime.java:53/95/107`、`XiaomiPlayer.java:54`、`NativeClockRuntime.java:45`、
  `OfficialUi.java:33`、`SettingsUi.java:34`、`XiaomiBuiltinPacks.java:36`、`XiaomiUi.java:27`。
- 现状行为正确（写入靠已打开的 FD），但顺序反直觉：若将来改成「先 chmod 再 open」会立刻失败。
  **未实施**（只是提醒，不是缺陷）。

### 13.5 `DeviceIdleDelayHook` 的 arm / cancel 条件相同 → 延时任务刚装就被撤（**功能等于没做**）

- 代码：`beforeHookedMethod(mScreenOn==true) → arm()`；同一方法的 `afterHookedMethod(mScreenOn==true) → cancel()`，
  `cancel()` 会 `removeCallbacks(pending)` 并把 `deferUntil` 清零（`DeviceIdleDelayHook.java:30-41,63-92`）。
- 若真机的 `updateInteractivityLocked` 方法体内不改写 `mScreenOn`（AOSP 版本里由 `handleScreenOn/Off` 负责），
  则同一次调用里"先 arm 后 cancel"，1.2 秒窗口恒被关闭 → **推迟从未发生**。
- 另一个待确认点：该 hook 用 `findMethodExact` 按**无参**匹配。若真机签名带 `boolean`，整条 hook 静默装不上
  （`CleanHooks.step()` 只留一行 failed）。需反编译 `com.android.server.DeviceIdleController` 确认。
- 修法（未实施）：after 的取消条件改成 `mScreenOn==false`，或只在 false→true 边沿 arm。
- 影响面：这是调试用 hook，不参与 AOD/动画主链路。

## 14. 息屏显示 6 秒后被系统挂起（DOZE_SUSPEND）——**ROM 行为，非本模块**

**症状（2026-11-17 13:05 现场）**：真实电源键熄屏后 AOD 只显示约 6 秒，随后面板直接熄灭
（`mScreenState` 从 `DOZE` 变 `DOZE_SUSPEND`），用户设置是「始终显示」（`doze_always_on=1`）。
用户还报告「用几个小时后才出现」。

**实测证据（决定性）**：

| 状态 | 日志 |
|---|---|
| 模块工作 + 强制全屏 AOD | `MiuiDozeService: needWallpaperAnim: true, fullAodEnable: true` → `AlarmTimeout: schedule DozeScreenState in 6000` → `applyPendingScreenState: mPendingScreenState 4` → `setDozeScreenState 4` |
| **A/B：`HyperOsAod.install` 停用 + MIUI 原生壁纸**（`AliveHSAod: overlay skipped: wallpaper is not ours`） | `fullAodEnable: false` → **同样** `schedule DozeScreenState in 6000` → `setDozeScreenState 4` |

即：`com.miui.aod` 的 `DozeScreenState` 在进入 doze 时挂 6 秒 `AlarmTimeout`，到点把屏幕置为
`DOZE_SUSPEND`。**本模块完全不参与时同样发生**，因此不属于本模块缺陷。
（顺带排除的假设：不是「强制全屏 AOD」导致的 —— 关掉强制后 `fullAodEnable` 已变 false，6 秒照旧。）

**与它区分开的另一个真实故障（同一时段抓到）**：开机 19 小时后出现

```
bindService exceeded max service connection number per process, callerApp:system
  -> Unable to bind dream service ... MiuiDozeService
  -> Stopping dream, reason='bindService failed'
  -> PowerManagerService: Sleeping        # 面板立刻灭，动画一帧都没有
```

system_server 自身服务连接数耗尽 → AOD 的 dream 服务绑不上 → 直接睡眠。**重启即恢复。**
连接数实测：重启后全局 372 个，19 小时后 3762 个；本模块相关始终只有 1 个
（`org.aliveclean/.CleanWallpaper`）。另外**每次覆盖安装会多出 3 个连接，全部是
`com.android.permissioncontroller/...PermissionControllerServiceImpl`**（系统自己的），
不是本模块的服务。

**结论**：这两条都不是本模块代码缺陷；处理方式是「重启」与「减少无谓的覆盖安装」。
不要用 hook 去抬高 system_server 的连接上限——那会掩盖真实泄漏，且热路径 hook 风险远大于收益。

## 15. 覆盖安装后不重启 → SystemUI 直接崩（`UnsatisfiedLinkError`）

**症状（2026-11-17 13:25）**：`com.android.systemui` 崩溃重启，crash 缓冲区：

```
FATAL EXCEPTION: AliveGL  (Process: com.android.systemui)
java.lang.UnsatisfiedLinkError: LspModuleClassLoader[module=/data/app/~~KD6iYxQK.../org.aliveclean-.../base.apk]
  couldn't find "libalive_clean.so"
  at org.aliveclean.NativeScene.<clinit>(NativeScene.java:12)
  at org.aliveclean.RenderLoop.createScene -> init -> attach
```

**根因**：用 `adb install -r` 覆盖安装会换掉 APK 目录（`~~xxxx==` 每次不同），而**已在运行的
SystemUI 进程里的模块 ClassLoader 仍指向上一个已被删除的 APK 路径**，于是 `System.loadLibrary`
找不到打包在 APK 里的 `libalive_clean.so`。这是「装完不重启」的必然结果，不是代码逻辑错误
（`system_server`/`SystemUI` 的 hook 都要重启才换到新代码，见 `build/01-*`）。

**为什么它会崩整个 SystemUI**：`UnsatisfiedLinkError` 是 `Error` 而不是 `Exception`，
原来的 `catch(Exception)` 兜不住，异常从 `AliveGL` 渲染线程一路冒到 `HandlerThread.run` → 进程死。

**已修（`9d89e77` 之后的构建）**：

1. `NativeScene` 静态块不再重新抛出，改为 `available` 标志（`catch (Throwable)`）；
2. `RenderLoop.init()` 开头判 `NativeScene.available`，不可用就打一行日志后**空转**，不建场景；
3. `RenderLoop` 的 `render()` 与两处 `init()` 调用改成 `catch (Throwable)`——渲染线程不允许把宿主进程带走。

**操作纪律**：`install -r` 之后**必须重启**再验证；调试期连续安装时尤其容易踩到。

## 16. AOD 永远是「自定义」→ 息屏壁纸层（动画）整个消失

**症状（2026-11-17 13:43 用户定位）**：息屏时时钟等元素正常、**只有壁纸/动画那层变暗消失**；
无论如何都是「自定义」显示模式——即使在系统壁纸下重新选「和锁屏样式一致」，表现仍是自定义。

**根因**：`AodWallpaperMode.restore()` 里还留着写回分支

```java
} else if (current != original) {
    putInt(context, KEY_MODE, original);   // original = alive_aod_restore_value = 0
}
```

模块早先捕获过用户当时的 `full_screen_aod_on = 0`（`alive_aod_restore_value=0`），于是每次
`restore()` 都把 0 写回；用户之后选「和锁屏样式一致」（=1）会被下一次 restore 覆盖回 0。
「自定义」模式下 MIUI 的 AOD 只画时钟，全屏 AOD 通道不生效，壁纸层因此不在画面里。

**为什么以前看起来正常**：写回路径一直存在，只有在 `alive_aod_restore_set=1` 且捕获值为 0 的
状态下才会稳定触发；重启/重装后该状态成立，于是稳定复现。

**修复（`435e54e`）**：`restore()` 只清本模块自己的旁路键（`alive_aod_restore_value`、
`alive_aod_restore_set`、`alive_aod_active`），**绝不写 `full_screen_aod_on`**——这与
`AodWallpaperMode` 类注释自 `3d1dd48` 起声明的策略一致，此前只是这条路径没照做。

**验证**：重装 + 重启后 `full_screen_aod_on=1`、`alive_aod_restore_value/set=null`；用户确认
息屏动画持续显示。

## 17. 「切回系统壁纸后 AOD 模式恢复不够即时」——改为写侧拦截，不再补偿

**问题**：旧设计是「应用壁纸时捕获用户原值，切回系统壁纸时再写回去」。写回本身要等壁纸变更回调
+ 5 次 × 1 秒确认（最长 5 秒），用户能明显看出延迟；而且这条写回路径在 2026-11-17 造成了
「用户选 1 又被改回 0」的事故（§16）。

**新方案（`be14b10`）**：
1. **写侧拦截**：`AodFullScreen#blockFullAodWrite` 在「壁纸是我们的 + 息屏开着」时拦掉对
   `full_screen_aod_on` 写 1 的调用（写 0 放行），存储值全程保持用户原值；
2. 因此切回系统壁纸时 MIUI 读到的就是用户原值，**不需要恢复动作，零延迟**；
3. 兜底：若存储里是历史遗留的 1，`AodWallpaperMode.restoreWhenForeign` 用 150ms/350ms 两次
   确认（约 0.5 秒）后写回捕获值；`AodWallpaperModeHook` 的首检延迟由 800ms 降到 250ms。

**验证**：重启后 `aod foreign confirmed x2` 出现在约 0.5 秒内（旧实现要 5 秒）。
