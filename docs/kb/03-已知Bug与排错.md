# 03 已知 Bug 与排错

> 本文件按“用户现象 → 证据 → 可能原因 → 涉及文件 → 正常版本 → 排查步骤”组织。
> 不允许把“可能”写成“已修复”。每条结论都带证据级别。
>
> 当前快照：2026-11-15，设备 `9eb96e8a`，构建 b1c（`b1c98f74...`）。
> 设备在最后一次复现前 adb 断开，部分稳态截图未完成；以下 AOD 稳态问题以**用户实机观察**为主，辅以源码链路推断。
>
> **2026-11-15 22:40 复核（设备未连接）**：本文件里靠 logcat 支撑的“已验证/已复现”条目，多数在 `.tmp` 中已找不到原始日志（只保留 2026-11-15 10:xx 旧构建的日志与截图）。凡涉及 b1c 的日志类证据，一律降级为【证据缺失】；截图类证据已逐张复核（结论见 `01-*` §8.1）。另外修正了三处与源码不符的表述，见 §1.4、§1.5、§3.5 的“复核”注记。

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
  推翻了"support=1 一定是残留"的猜测，`03-*` §9.2 里的 B2 需要按此修正为"我们确实造成过，但当前值本身是原生的"。

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
4. 待做：把 `03-*` §1.4 的 H1–H6 按 §9 的证据重新标级（H1/H2 已修，H5 未涉及）。
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

### 12.3 验证状态

- `8e30b31` 已装机，日志确认 `aod screen-on cleanup registered`。
- **待用户复测**：解锁 → 息屏 → 唤醒到桌面，看双层/残影是否消失；若仍有，需要再录一段（解锁态）视频。
- 注意：只有在**已解锁**状态下的唤醒才走"息屏→桌面"，锁屏态唤醒走的是锁屏，两者动画不同，别测错。


