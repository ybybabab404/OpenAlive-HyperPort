# 03 已知 Bug 与排错

> 本文件按“用户现象 → 证据 → 可能原因 → 涉及文件 → 正常版本 → 排查步骤”组织。
> 不允许把“可能”写成“已修复”。每条结论都带证据级别。
>
> 当前快照：2026-11-15，设备 `9eb96e8a`，构建 b1c（`b1c98f74...`）。
> 设备在最后一次复现前 adb 断开，部分稳态截图未完成；以下 AOD 稳态问题以**用户实机观察**为主，辅以源码链路推断。

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
- 早先 5f41/0ecc 的截图也多为“全屏壁纸 + 时钟”或转场帧，没有完成“稳定 18 秒仍有球”的证据。
- 设备 adb 在准备做 2/6/12/18 秒稳态截图时断开，尚未补测。

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

2. logcat 过滤 AliveHSAod / AliveClean / AliveHSHooks
   ├─ 没有 "injected into the AOD view" → 注入没发生，查 attach 条件/延迟
   ├─ 有 injected，但没有 "texture#available" → SurfaceTexture 没起来
   ├─ 有 texture#available，但没有 "aod photo from provider" → RenderLoop 没读图/EGL 失败
   ├─ 有 provider 日志，但球黑 → 检查图片/上传/场景
   └─ 有 injected，稳定后无球 → 重点查 H1（surface 被移除/未重挂）

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

**证据级别：中。**

`ensureEnabled` 在 `KEY_HAS_RESTORE != 1` 时直接 `enter(context)`，把 `full_screen_aod_on` 设为 1。如果切壁纸后 marker 被清、但 `alive` 因槽位/缓存误判为 true，就会写回 1。

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

## 8. 当前排查优先级

1. **先补 Bug A 的稳态证据**：2/6/12/18 秒截图 + logcat；确认是 surface 被移除、alpha 0，还是纹理黑。
2. **再决定是否调用 `supervise`/加首帧看门狗**；不要先调延迟。
3. **Bug B 先确认 MIUI 原生限制**：禁用模块对照，查 `isWallpaperTypeSupport` 原值；再决定是否产品限制。
4. **Bug C 单独回归**：切系统→切项目→重启→切系统，记录每一步 `full_screen_aod_on`/wallpaper 组件/日志。
5. 最后做真实指纹和系统壁纸人工回归；不要用 adb 虚拟指纹代替。
