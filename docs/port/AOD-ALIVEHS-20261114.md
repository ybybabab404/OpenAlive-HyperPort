# AOD「AliveHS 样式」机制修复记录（2026-11-14）

## 目标行为

- **项目壁纸生效时**：AOD 设置页显示 `AliveHS 样式`，并锁定样式选择；底层实际走 MIUI 的“和锁屏样式一致（全屏 AOD）”渲染路径。
- **系统壁纸生效时**：完全恢复用户应用项目壁纸前的行为；隐藏 `AliveHS 样式`，显示原生的“自定义 / 和锁屏样式一致”，设置项可正常触摸；AOD 关闭时不得出现项目壁纸生成的球或景深层。
- 所有 `full_screen_aod_on` / `alive_aod_*` 写入都是临时的：系统壁纸恢复后还原原值，模块停用/卸载后不残留行为。

## 四个问题的根因与修复

### 1. 关闭息屏显示后仍出现“启航样式”球

旧 `AodOverlay.ownsWallpaper()` 先看系统壁纸，系统壁纸不是 live wallpaper 时回退到锁屏壁纸；锁屏上残留的 `CleanWallpaper` 被误判为“仍在使用项目壁纸”，overlay 继续显示。

修复：`AodOverlay.show()` 统一使用 `AodWallpaperMode.alive()`；系统壁纸不是本项目 live wallpaper 时立即 `hide()`；`hide()` 还会调用 `HyperOsAod.detach()`，移除注入 MIUI AODView 的 `AodSurface`，避免关闭息屏显示后残留默认球。

### 2. 自定义模式景深双上层 / 亮屏抖动

旧 `KeyguardDepthHider.isOurWallpaper()` 优先读取锁屏壁纸；锁屏残留项目壁纸时，系统壁纸已经是 MIUI，仍会清空/重设 MIUI 景深层。并且 `apply()` 在非 ours 时仍调用 `updateShowDepthState()` / `updateDeductedImageView()`，会再次触发原生景深更新。

修复：

- `isOurWallpaper()` 只认系统壁纸；
- `apply()` 在非 ours 时直接返回，不再调用任何 MIUI 景深方法；
- 500 ms 缓存保留，避免高频查询。

### 3. 指纹解锁按压时时钟消失

与问题 2 同源：`KeyguardDepthHider` 在锁屏残留项目壁纸时错误清空景深/时钟层。

修复：同上，系统壁纸非本项目时不再触碰景深层。

### 4. 自定义 → 项目壁纸 → 系统壁纸后变成“和锁屏样式一致”

旧 `SceneApply` / `XiaomiApply` 在 `setWallpaper` 成功之后才调用 `AodWallpaperMode.enter()`。MIUI 的 `MiuiFullAodManager.updateSettings` 可能先把 `full_screen_aod_on` 改成 1，enter 捕获到的是 1，restore 自然回 1。

修复：

- `SceneApply.apply()` / `XiaomiApply.apply()` 在启动壁纸子进程**之前**调用 `AodWallpaperMode.enter(context)`；失败路径调用 `restore(context)`；
- `AodWallpaperMode` 在模块 app 进程通过 `su 0 -c settings get/put/delete secure ...` 读写自己的恢复键。原因：`app_process` 的 system Context 没有注册 application，普通 `Settings.Secure` 和 `*ForUser` 反射都会抛 `Unable to find app for caller ... when getting content provider settings`；
- 系统进程仍走普通 `Settings.Secure` 路径，不使用 root shell。

## 关键实现位置

| 文件 | 作用 |
|---|---|
| `app/java/org/aliveclean/AodWallpaperMode.java` | `alive` / `uiAlive` / `enter` / `restore` / `ensureEnabled`；系统壁纸是唯一权威；模块 app 的 root Secure fallback |
| `app/java/org/aliveclean/AodOverlay.java` | AOD overlay 只在系统壁纸为本项目 live wallpaper 时显示；`hide()` 同时调用 `HyperOsAod.detach()` 移除注入到 MIUI AODView 的 scene surface |
| `app/java/org/aliveclean/KeyguardDepthHider.java` | 非本项目壁纸时完全不动 MIUI 景深层 |
| `app/java/org/aliveclean/AodFullScreen.java` | 仅项目壁纸时条件化 hook `isSupportFsAod` / `isDeviceSupport` / `isWallpaperTypeSupport` / `MiuiConfigs` flags；系统壁纸恢复原值 |
| `app/java/org/aliveclean/AodSettingsGuard.java` | 设置页包装原生 adapter；项目壁纸时显示/锁定 AliveHS；系统壁纸时 unbind 恢复 |
| `app/java/org/aliveclean/SceneApply.java`、`XiaomiApply.java` | 应用壁纸前捕获 `full_screen_aod_on` 原值；失败恢复 |

## 构建

```text
JAVA_HOME=C:\Users\ybybabab\app\pcl\JAVA21\graalvm
ALIVEHS_NATIVE_SO=.tmp/build/libalive_clean.so
python3 tools/build_release.py --no-checks
```

最新产物：

- `dist/AliveHS-0.1.0-arm64-v8a.apk`
- SHA-256: `b1c98f7401b3b0fdaece5af3f86bffe373f736442e4458f3393480cee05cb36c`
- 大小: 308,625,742 bytes

构建代际（不要把历史 SHA 当当前包）：

| 构建 | SHA-256（前 8 位） | 注入延迟 | 状态 |
|---|---|---|---|
| 08a0 | `08a0d3b3…` | 850ms | 2026-11-14 19:27 USB，历史 |
| 73b54a7e | `73b54a7e…` | 850ms | 2026-11-15 11:06，历史 |
| 0ecc86eb | `0ecc86eb…` | 850ms | 2026-11-15 14:24，历史 |
| f2d2 | `f2d2bce9…` | 950ms | 2026-11-15 15:06，中间构建 |
| 5f41 | `5f41c956…` | 950ms | 2026-11-15 15:39 USB，历史 |
| **b1c9** | **`b1c98f74…`** | **950ms** | **2026-11-15 16:10 USB，当前** |

## 2026-11-15 追加修复

本轮 team 复核后又补了五处，均已编译进 `73b54a7e…`；后续 f2d2 又补了缩略图 null 路径、旧黑白缓存回退与 AOD 关闭守卫：

- `app/java/org/aliveclean/HyperOsAod.java`：`attach()` 在 `aodEnabled()` 为 false、`aodSceneEnabled()` 为 false、或 `AodWallpaperMode.alive()` 为 false 时都调用 `detach()`，清除已注入的 `AodSurface`；`aodEnabled()` 读取 `Settings.Secure.doze_always_on`。
- `app/java/org/aliveclean/AodOverlay.java`：`show()` 先检查 `aodEnabled()`，AOD 关闭时直接 `hide()`，不创建 WindowManager overlay。
- `app/java/org/aliveclean/CleanWallpaper.java`：`scene()`、`send()`、`onSurfaceChanged()` 对 AOD 关闭时的 mode 0 统一拦截（`scene AOD skipped: AOD is disabled`），并让 `refreshVisibility()` 在 `STATE_DOZE/STATE_DOZE_SUSPEND` 且 AOD 关闭时判定不可见；修复壁纸进程自身仍绘制“启航球”的路径。
- `app/java/org/aliveclean/KeyguardDepthHider.java`：`updateKeyguardElementsVisibility()` 增加 before-hook，在原生布局前把 `isActualDisplayDepth` 置 false 并 clear；新增 `isNeedPreHideDepth()` hook，ours 时返回 false，避免指纹 goingAway 把 `keyguardForegroundLayer`/时钟设为 GONE。
- `app/java/org/aliveclean/AodWallpaperMode.java` / `AodWallpaperModeHook.java`：`restoreWhenForeign()` 允许先看到 1 次 alive 的宽限（最多 5 次探测），避免早到事件永久取消恢复；优先 hook `bindWallpaperDescriptionLocked`，覆盖静态壁纸的 `WallpaperObserver` bind 路径。

## 验证状态

已在旧版 APK 上确认：

- AOD 设置页不再崩溃；
- 项目壁纸时显示 `AliveHS 样式`，点击行不跳转；
- 系统壁纸时显示原生“和锁屏样式一致”；
- `full_screen_aod_on=0`、`alive_aod_restore_* = null` 时可正常打开设置页。

**2026-11-14 19:27 USB 安装 08a0d3b3… 后的实测**（设备 9eb96e8a）：

- `AodStyleCategoriesActivity` 在项目壁纸下显示 `AliveHS 样式`，行与 spinner 的 `enabled=false`，点击行不会离开页面；
- 项目壁纸应用时 `full_screen_aod_on=1`、`alive_aod_restore_value=0`、`alive_aod_restore_set=1`、`alive_aod_active=1`，证明模块 app 的 root Secure fallback 已生效，且捕获的“原值”是正确的 0；
- 系统壁纸仍为项目 `CleanWallpaper` 时，无法在本机自动构造“系统静态壁纸”场景，因此问题 4 的 restore 还需要手动切一次系统壁纸验证；
- 关闭息屏显示后的球、景深双上层/指纹时钟两项还需要真机锁屏/指纹操作验证。

**2026-11-15 11:08 最新 APK 73b54a7e… 实测**：

- 项目壁纸 `CleanWallpaper` 生效时，`full_screen_aod_on=1`、`alive_aod_restore_value=0`、`alive_aod_restore_set=1`、`alive_aod_active=1`；
- 把 `doze_always_on` 置 0 后息屏，logcat 同时出现 `scene AOD skipped: AOD is disabled` 与 `overlay skipped: AOD is disabled`，截图 `.tmp/screenshots/aod_off_73.png` 为全黑，问题 1 通过；
- 本会话已恢复 `doze_always_on=1`，设备回到项目壁纸 + AOD 开启基线。

**已验证（team tester / 本会话）**：

1. 项目壁纸时 AOD 页显示 `AliveHS 样式`，行与 spinner 不可触摸；
2. 系统壁纸时恢复应用前 `自定义`，`full_screen_aod_on=0`，设置行可触摸（2026-11-15 10:41 team 实测 PASS）；
3. 自定义模式 + 系统景深壁纸：连续录屏/逐帧未见双上层或亮屏抖动（2026-11-15 10:43 team 实测 PASS，旧构建 08a0；新构建需再目视确认）；
4. AOD 关闭 + 项目壁纸：最新 73b54a7e 已实测全黑通过。

**仍需人工验证**：

- 自定义模式 + 项目壁纸/景深壁纸下按压真实指纹，确认时钟不消失（adb 的 `cmd fingerprint` 只能模拟虚拟化传感器，不能作为证据）；
- 重启后 `alive_aod_*` 不残留（应在系统壁纸下检查）；
- 新构建 73b54a7e 下再走一遍“自定义 → 项目壁纸 → 系统壁纸”，确认 restore 仍在 5 次探测宽限内完成。

## 2026-11-15 15:10 中间验证（f2d2）

最终包 `dist/AliveHS-0.1.0-arm64-v8a.apk`，SHA-256 `f2d2bce937b2b758a7daa0c9d782ad5163821746d0a2313bee9609ec9c9ce13a`，308,625,742 bytes；已 USB 安装到 9eb96e8a。

本轮在团队补丁之上补了四处：

- `AodWallpaperPreview` 的 `getPlaceholderWallpaper`、`ni7`、`uf` 三个 after-hook 在 `original == null` 时不再提前返回，而是直接返回当前预览，覆盖灰白占位路径；
- `RenderLoop.loadPhoto` 仅在项目壁纸已不存活时才回退 `/data/data/com.android.systemui/files/aod-wallpaper.png`，避免旧黑白月球图重新出现；
- `AodFullScreen` 新增 `controlsFullAod()`，AOD 关闭时不再强置 `mFullAodEnable` / manager 支持；
- `HyperOsAod`、`AodOverlay` 的注入延迟从 850ms 调整为 950ms。

实测：

- 安装后系统壁纸会短暂回退为 `com.miui.miwallpaper/ImageWallpaper`，`full_screen_aod_on=0`；用模块 `SceneApply` 重新应用项目壁纸后恢复 `org.aliveclean/CleanWallpaper`、`doze_always_on=1`、`full_screen_aod_on=1`。这是安装/重启后的操作注意事项，不是恢复失败。
- 重启 ThemeManager 后：`tm ni7 call result=1200x2608 alive=true` → `tm ni7 replaced 1200x2608`，`tm uf replaced 680x1509`；系统个性化页锁屏/桌面缩略图均为橙紫彩色，未复现灰白。
- 新构建录屏 `.tmp/screenshots/anim_new.mp4`（contact `.tmp/screenshots/anim_new_tile.png`）显示桌面/锁屏→息屏仍有全屏壁纸缩为橙色球体的过渡；logcat `15:10:51.499 AliveHSAod: injected into the AOD view`。球体来自当前橙紫壁纸，不是旧黑白月球图。
- 临时 `doze_always_on=0` 后熄屏：`scene AOD skipped: AOD is disabled`、`overlay skipped: AOD is disabled`，无 `injected into the AOD view`；随后恢复 `doze_always_on=1`。

仍需人工确认：用户所说“动画都没有”的具体操作路径（是否不同锁屏/解锁方式、不同壁纸或 AOD 开关状态）；真实指纹时钟；系统壁纸→项目壁纸→系统壁纸的恢复流程。


## 2026-11-15 15:45 最终验证（5f41，reviewer 复核后）

reviewer 复核报告：`C:/Users/ybybabab/.percho/agent/team/artifacts/HyperAlive-a6k6we/20261115-071254-jjdb/stage1-reviewer-1.md`。报告指出的 P0/P1 已在本轮代码中修复，构建 `5f41c9561f9cff299f58bf254ac3eda1a9dac51868badbc3c5b8241b0e44ebd5`（308,625,742 bytes），2026-11-15 15:39 USB 安装到 9eb96e8a。

本轮代码改动：

- `AodWallpaperProvider.templateCache()`：进入时先删除旧的 `getCacheDir()/aod-wallpaper.png`；只有本轮 root 复制成功且新文件非空才返回，所有失败返回 `null`，不再把旧黑白月球缓存交给 AOD。`RenderLoop` 同时删除 `/data/data/com.android.systemui/files/aod-wallpaper.png` 的 shared 回退。
- `AodFullScreen.onActivation()`：AOD 开关关闭时调用 `AodWallpaperMode.restore()` 并直接返回，不再调用 `ensureEnabled()`/`forceManager()`；`AodWallpaperMode.ensureEnabled()` 也先检查 `HyperOsAod.aodEnabled()`，AOD 关闭时不写 `full_screen_aod_on`。
- `AodFullScreen`：`ManagerOriginal` 保存并恢复 `isDeviceSupport` / `isWallpaperTypeSupport` / `isFullAodSupport` / `mAodEnable` / `mAodFullScreenEnable` 五个字段；`DOZE_ORIGINAL` 保存并恢复 `mFullAodEnable`；`hookAliveTrue` 在 AOD 关闭时不谎报 support。
- `AodWallpaperPreview`：所有 hook 改用 `forHook()`；对静态 cached Bitmap 返回 `copy(ARGB_8888,false)`，防止 ThemeManager `BitmapUtil.fu4` recycle 后崩溃；`Screenshots.t8r` 的 after-hook 把结果写回 `f53958zy` 字段，覆盖内部 getter 的灰白值。
- `HyperOsAod`：`aodEnabled()` 读取异常改为 fail-closed false；`scheduleAttach` 增加 generation，`detach()` 取消 pending attach，避免 stale root。
- 仍保留 `RenderLoop` 调试探针，未删除；发布前需按 HANDOVER 上线清单处理。

设备验证：

- SystemUI 旧进程 32258 在 `force-stop` 后未真正重启，已用 `su 0 kill -9 32258` 重启；新 PID 7871，日志 `aod full screen: ok`、`hyperos aod: ok`、`keyguard depth: ok`。这说明旧进程上的 AOD 关闭测试不可信。
- 新进程 + 项目壁纸 + `doze_always_on=0` 后发送 `org.aliveclean.QUERY_MODULE_ACTIVATION` 广播：`full_screen_aod_on=0`，logcat `aod mode restored=0`；随后又用真实电源键熄屏/亮屏复测，`full_screen_aod_on=0`，恢复 `doze_always_on=1` 并 `SceneApply` 后回到 `full_screen_aod_on=1`。
- 新构建录屏 `.tmp/screenshots/anim_5f41.mp4` 与 SystemUI 新进程后的 `.tmp/screenshots/anim_final.mp4`（contact `.tmp/screenshots/anim_final_tile.png`）均显示桌面→锁屏→橙色球体、球体保持；`anim_new`/`anim_5f41`/`anim_final` 均未出现“动画完全消失”。
- 系统个性化页 `.tmp/screenshots/personalize_5f41.png` 两张卡片均彩色；ThemeManager 新进程 26442 日志有 `tm uf replaced 680x1509`、`tm ni7 replaced 1200x2608`。
- AOD 关闭时不再出现 `AliveHSAod: injected into the AOD view`；旧黑白 provider 路径已删除。

仍未闭环：真实指纹时钟；用户“动画消失”的具体路径；系统壁纸与项目壁纸互切的人工复测；`RenderLoop` 探针清理。

## 2026-11-15 16:10 最终验证（b1c，system_server 重启后）

最终包 `dist/AliveHS-0.1.0-arm64-v8a.apk`，SHA-256 `b1c98f7401b3b0fdaece5af3f86bffe373f736442e4458f3393480cee05cb36c`，308,625,742 bytes；2026-11-15 16:07 USB 安装到 9eb96e8a 并重启设备。system_server 的 LSPosed 作用域只在重启后加载新 APK，单独重启 SystemUI 不足以验证 system_server 内的 `AodFullScreen`/`AodWallpaperMode` 逻辑。

本轮补丁：
- `AodWallpaperMode.enter()` / `ensureEnabled()` 增加 `/system/bin/settings get secure doze_always_on` 兜底读取；system_server 的 Settings 缓存可能与 shell 不一致，不能只信 `Settings.Secure`。
- `SceneApply.main()` / `XiaomiApply.main()` 增加同样 shell 守卫；`SceneApply.apply()` 在子进程成功且 AOD 关闭时再次 `AodWallpaperMode.restore(context)`，防止子进程写回 1。
- `AodWallpaperPreview` 的缩放路径统一使用 `scaleOrRecycle()`，避免 `uf` 缩略图路径泄漏 preview；`HyperOsAod.detach()` 清 `aodRoot` 并取消 pending attach；`AodFullScreen` 记录 `lastDozeHost`，AOD 关闭时立即 `restoreDoze`。
- `AodWallpaperProvider.templateCache()` 先清旧缓存，失败返回 null；`RenderLoop` 删除 `/data/data/com.android.systemui/files/aod-wallpaper.png` 的 shared 回退。

实测（设备 9eb96e8a，重启后）：
- `doze_always_on=0` 时执行 `SceneApply`：`full_screen_aod_on` 保持 0，`SCENE_APPLIED`；
- `doze_always_on=1` 时执行 `SceneApply`：`full_screen_aod_on=1`，`mWallpaperComponent=org.aliveclean/.CleanWallpaper`；
- AOD 关闭时真实电源键熄屏：截图 `.tmp/screenshots/b1c_aod_off.png` 全黑，无 `AliveHSAod: injected into the AOD view`；亮屏后 `full_screen_aod_on=0`；
- AOD 开启时截图 `.tmp/screenshots/b1c_aod_on.png` 可见当前橙紫壁纸的息屏画面与时钟；从桌面录制的 `.tmp/screenshots/anim_b1c_home.mp4`（contact `.tmp/screenshots/anim_b1c_home_tile.png`）显示桌面/锁屏→息屏缩球和球体保持；
- 系统个性化页 `.tmp/screenshots/personalize_b1c.png` 两张卡片均为橙紫彩色，日志 `tm uf replaced 680x1509`、`tm ni7 replaced 1200x2608`；
- AOD 设置页 `.tmp/screenshots/aod_page_b1c.png` 显示「AliveHS 样式」「显示模式 始终显示」。

仍需人工确认：真实指纹按压时钟；用户所说“动画消失”的具体操作路径；系统壁纸→项目壁纸→系统壁纸的完整恢复流程；重启后 `alive_aod_*` 不残留。

## 约束与风险

- 没有修改任何系统属性（`setprop`）；临时写入的 `Settings.Secure` 键为 `full_screen_aod_on`、`alive_aod_restore_value`、`alive_aod_restore_set`、`alive_aod_active`，均由 `restore()` 还原/删除。
- root shell 仅用于模块 app 进程自己的恢复键；system_server/SystemUI 不调用 `su`。
- `WallpaperManager.getWallpaperInfo()` 对静态系统壁纸返回 null；因此“系统壁纸非本项目 live wallpaper”必须视为 not ours，不能再 fallback 到锁屏壁纸。

## 2026-11-15 本轮修复（问题 2）

本轮针对“项目壁纸下 AOD 球体仍是黑白默认壁纸、无缩球动画、底层不是锁屏样式”补齐了以下实现：

- `app/java/org/aliveclean/AodWallpaperProvider.java`：`openFile()` 依次读取 `APPLIED.photo`、`DRAFT.photo`；都不可用时用 root shell 把 `/data/user_de/0/com.miui.aod/files/templates/current/{wallpaper_home,wallpaper,small_wallpaper_screenshot}` 复制到 `getCacheDir()/aod-wallpaper.png`（`chmod 644`，3 秒超时），避免 `RenderLoop` 回退到 `/data/data/com.android.systemui/files/aod-wallpaper.png` 的旧黑白月球图。
- `app/java/org/aliveclean/AodWallpaperPreview.java`：`installThemeManager` 增加 `LockScreenWpTool#toq()`、`DeskWpTool#toq(WeakReference)` after-hook；仅在 `AodWallpaperMode.alive()` 且模板预览可用时替换 null/尺寸异常的个性化缩略图结果，修复历史灰白占位路径。
- `app/java/org/aliveclean/HyperOsAod.java`：
  - `AodSurface` 改为 `TextureView.setOpaque(true)`。这是本轮球体重新显示的关键修复；此前 `setOpaque(false)` 时 view 虽 `shown=true/alpha=1` 且 EGL 有帧，MIUI AODView 合成结果仍为黑色；
  - 注入位置从 child 0 改到 `Math.min(1, childCount)`，避开 `aod_mode_layout` index 0 的不透明黑色 `View`，并位于时钟 `AodContainerView` 之下；
  - `AodSurface` 初始 `alpha=0`，`RenderLoop.onFirstFrame()` 首个真实帧完成后 180ms 淡入；`AodOverlay` 同样处理。这样 wallpaper 层的全屏→球体缩球动画播放期间不会先被一块黑色 TextureView 盖住；
  - 850ms 延迟注入保留（此段为 0ecc 历史；当前 b1c 已改为 950ms），用于让壁纸进程的 `scene motion 1->0` 动画先启动。
- `app/java/org/aliveclean/RenderLoop.java`：新增 `onFirstFrame(Runnable)`，在首次 `eglSwapBuffers` 成功后回调一次，供 AOD surface 延迟淡入。
- `app/java/org/aliveclean/AodFullScreen.java`：`forceManager()` 继续强制 `isDeviceSupport` / `isWallpaperTypeSupport` / `isFullAodSupport` / `mAodEnable` / `mAodFullScreenEnable`，但不再在 manager 构造期间调用 `updateFullAodEnable()`（会重入 SystemUI Dagger，有死锁风险）；新增 `hookDoze()`，在 `MiuiDozeService.onDreamingStarted` 前把 `DozeServiceHostInjector.mFullAodEnable=true`，保证首次息屏 `fullAodEnable=true`。
- `app/java/org/aliveclean/CleanHooks.java`：实现 LSPosed API 102 `onHotReloading` / `onHotReloaded`，热加载后 unhook 旧句柄并按当前进程 classloader 重新安装 hook；SystemUI 作用域无需重启系统。

### 本轮实测（设备 9eb96e8a，USB）

- 构建产物：`dist/AliveHS-0.1.0-arm64-v8a.apk`
- 大小：308,625,742 bytes
- SHA-256：`0ecc86eb18e44a1f9f56ee46e122b27706f73516ffd17d1e22c21776b57fbcc9`
- 安装：`adb -s 9eb96e8a install -r` Success；未重启设备，仅重启 SystemUI 进程加载模块。
- 项目壁纸生效：`dumpsys wallpaper` 显示 `mWallpaperComponent=org.aliveclean/.CleanWallpaper`；`full_screen_aod_on=1`、`alive_aod_active=1`、`alive_aod_restore_value=0`、`alive_aod_restore_set=1`、`doze_always_on=1`。
- 首次息屏：logcat `MiuiDozeService: onDreamingStarted ... fullAodEnable: true`；`AliveClean: aod photo from provider 1200x2608`；AodSurface `alpha=1.0 shown=true attached=true size=1200x2608`。
- 截图 `.tmp/evidence/aod_final3.png`（以及中间包 `.tmp/evidence/aod_opaque1.png` / `aod_opaque2.png`）：黑底 + 原生时钟 + 当前橙紫壁纸球，不再是黑白月球；状态栏/USB 通知仍会出现在测试截图中，尚未确认是否为 AOD 通知层。
- 中间构建的录屏 `.tmp/evidence/aod_anim2.mp4` / `.tmp/evidence/aod_anim2_labeled.png` 可见全屏锁屏壁纸→缩球→AOD 球的过程；最终包的 850ms 延迟 + 首帧淡入仍需再做一次人工目视确认。

### 问题 1（系统个性化灰白缩略图）

本轮在 `PersonalizeActivity` 复测时锁屏/桌面两张预览均为橙紫彩色，未复现灰白；`tm lock preview hook count=1`、`tm desk preview hook count=1` 已安装，但原生结果非 null，未触发 `tm lock/desk preview replaced`。因此本轮只能提交防御性 hook，不能声称已在设备上复现并修复灰白路径；需要在灰白复现的设备/版本上抓取 `LockScreenWpTool.toq()` / `DeskWpTool.toq()` 的实际返回值再确认。

### 构建备注

仓库路径含中文时 `tools/build_release.py` 的 `javac @app.args` 会因平台编码失败。本轮使用 `subst H:` 映射仓库，并用 `.tmp/build_h.py` 将 `build_release.ROOT` 指向 `H:/`、重写 `rel()` / `arg_path()`，环境为 `JAVA_HOME=C:/Users/ybybabab/app/pcl/graalvm-jdk-17.0.21.0.3+1.1`、`ALIVEHS_NATIVE_SO=.tmp/build/libalive_clean.so`，执行 `python3 .tmp/build_h.py`（内部 `--no-checks`）。
