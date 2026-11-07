# HyperAlive 交接文档

> 写给下一个接手这个项目的人（或 agent）。先读这一份，再翻代码。
> 最后更新：2026-11-07 18:30（周六）
> 当前结论：用户已确认「好了」；0.6.1-preview 已提交、已打 tag、已安装验证。

---

## 0. 一句话现状

HyperAlive 是 LSPosed API 102 模块，给 HyperOS / Android 17 补「活的壁纸」：液态、光栅、闪卡三种壁纸渲染，锁屏触摸转发，息屏（AOD）联动，系统「个性化」页入口，以及小米预览 / AOD 预览。

当前 0.6.1 的重点不是加功能，而是修「进入息屏」的合成卡顿：AOD Surface 重复初始化已消除，熄屏后的 quick-doze `no activity` 被延迟 1.2 秒，避开 AOD 入场首帧。用户已在真机确认正常。

---

## 1. 版本、分支与基线

| 项 | 值 |
|---|---|
| 当前分支 | `deep-clean-v2` |
| 代码基线 | `7118782 fix: defer device-idle no-activity during AOD entry`（tag `save-20261107-idlefix`） |
| 文档版本 | 以 `git log -1 -- docs/HANDOVER.md` 为准 |
| 最新 tag | `save-20261107-idlefix`（指向 `7118782`） |
| versionCode / versionName | `60` / `0.6.1-preview` |
| 当前 APK | `dist/HyperAlive-0.6.1-preview-arm64-v8a.apk` |
| APK sha256 | `ea5d0c4fa10617c6768db266a85605e7acf72b674c3eaa648f3e2c7fe9f72451` |
| APK 大小 | 307,306,830 字节 |
| 安装时间 | 2026-11-07 18:00:47（设备 `192.168.1.13:39795`） |

回退基线（按时间从新到旧）：

- `save-20261107-idlefix` / `7118782`：device-idle 延迟 + AOD 单次初始化
- `save-20261107-aodentry` / `cb037af`：去掉 doze 入场重复 `refreshScene`
- `save-20261107-raster-fix` / `eab57a1`：Vivo raster controller 保活
- `save-20261106-fingerprint-unlock` / `1311931`：指纹解锁不播锁屏动画
- `save-20261106-zerochange-0.6.1` / `b6ae18e`：0.6.1 零行为清理
- `save-20261105-gles` / `c0b8793`：Vulkan 回退后的纯 GLES 基线

---

## 2. 环境与构建

```
JAVA_HOME    = C:/Users/ybybabab/app/pcl/JAVA21/graalvm
ANDROID_HOME = C:/Users/ybybabab/AppData/Local/Android/Sdk
Python       = C:/Python314/python.exe
adb          = C:/Users/ybybabab/AppData/Local/Android/Sdk/platform-tools/adb.exe
```

唯一推荐的构建 + 部署入口：

```powershell
cd "C:/Users/ybybabab/app/项目开发/HyperAlive"
$env:HYPERALIVE_NATIVE_SO='.tmp/libalive_clean.so'
C:/Python314/python.exe tools/deploy.py --build
```

要点：

- 不要直接跑 `tools/build_release.py`，它会在 javac 阶段找临时目录失败；`deploy.py --build` 会准备好。
- 一次构建约 50–200 秒，APK 约 307 MB；产物和哈希写在 `dist/build-report.json`。
- `deploy.py` 从 `app/AndroidManifest.xml` 的 `versionName` 推导 APK 文件名；改版本后不会误推旧包。
- javac 错误是 GBK，PowerShell 里常显示乱码；需要原文时用 `iconv`/Python 以 GBK 解码。
- 本机手动 javac：
  `& 'C:\Users\ybybabab\app\pcl\JAVA21\graalvm\bin\javac.exe' '@build\release\app.args'`

### APK 入口与打包

- `META-INF/xposed/java_init.list` → `org.aliveclean.CleanHooks`
- `META-INF/xposed/module.prop` → `minApiVersion=102`、`targetApiVersion=102`、`staticScope=true`
- `META-INF/xposed/scope.list` → `android` / `com.android.systemui` / `com.miui.aod` / `com.android.thememanager`
- 旧 `assets/xposed_init` 已删除；不要再加回来。
- `tools/build_release.py` 用 `aapt2 -A app/assets` 把整个 assets 打进 APK；`app/assets` 里的大文件不是“源码残留”，删之前必须查运行引用。
- `workbench/` 是本次清理临时移出的反编译材料 / 参考字体 / 备份，已在 `.gitignore`，**不要打进 APK，也不要提交**。

---

## 3. 设备与调试回路

当前设备：

```
192.168.1.13:39795   product:myron   model:25102RKBEC   Android 17 / SDK 37
```

无线调试端口会变；旧的 `10.11.48.149:39795`、`10.10.8.91:39795` 都可能失效。先：

```powershell
& $adb devices -l
& $adb connect <ip>:39795
```

### 改完代码怎么生效

| 改动位置 | 生效方式 |
|---|---|
| `com.android.systemui` 里的 hook | `adb shell su -c "killall com.android.systemui"`，等 10–15 秒 |
| `com.miui.aod` / AOD 设置 | `adb shell am force-stop com.miui.aod`，再打开 AOD 设置 |
| 壁纸服务 `org.aliveclean` | `adb shell su -c "kill -9 <pid>"`；`am force-stop` 杀不掉 |
| `system_server` hook（`DeviceIdleDelayHook` 等） | 必须重启框架或手机；本次是系统重启后 `module loaded process=system` 才加载 |
| APK 更新 | `adb install -r -t dist/HyperAlive-0.6.1-preview-arm64-v8a.apk` |

> 注意：`su -c "stop; start"` 在这台机器上可能返回 `Must be root`，且会把 adb 弄成 `offline` 一阵；不要把它当成可靠的重启方式。优先 `adb reboot`，等 `sys.boot_completed=1` 再重连。

---

## 4. 代码地图

源码都在 `app/java/org/aliveclean/`，无 Gradle。

### 入口与兼容层

- `CleanHooks.java` — LSPosed API 102 入口（`extends XposedModule`），所有 hook 的装配点。
- `Xp.java` — 旧 `XposedHelpers` 风格的兼容层：`hookAllMethods`、`findAndHookMethod`、`MethodHookParam`、`callMethod`、`getObjectField` 等。
- `ModuleActivationProvider.java` — `org.aliveclean.clock.runtime` authority 的 provider；`ModuleActivation` 用它判断模块是否激活。不要删，否则主页会显示「LSP 未激活」。

### 壁纸与渲染

- `CleanWallpaper.java` — WallpaperService / SceneEngine；`WAKE_DWELL=0L`、`WAKE_POLL=50L`；唤醒与 SCREEN_OFF 的模式切换都在这里。
- `RenderLoop.java` — GLES 渲染主循环、`mode()`、surface attach/redraw。
- `NativeScene.java` — JNI 入口，包名 `org.aliveclean` 写死在 `.so` 里，不能改包名。
- `VivoEngineScene.java` / `VivoEngineRuntime.java` — 液态 / 光栅 / 闪卡。
- `SceneApply.java` — 用 `su 1000` 起 `app_process`，反射调用 `WallpaperManager.setWallpaperComponentWithFlags(FLAG_SYSTEM|FLAG_LOCK)`。

### 息屏 / AOD

- `HyperOsAod.java` — 在 SystemUI 的 AOD 宿主里注入 `AodSurface`；`installDoze()` 只负责 dreaming 生命周期；`liveSurface` 只在 `isAttachedToWindow() && isShown()` 时复用，否则清空重建。
- `AodOverlay.java` — 早期独立 overlay 窗口，保留作兜底；`hide()` 已做空 View 防护。
- `AodWallpaperMode.java` — 应用模块壁纸时强制「和锁屏样式一致」；`alive(Context)` 是全局判据。
- `AodSettingsGuard.java` — 锁住 AOD 设置里的样式行，文案「HyperAlive 样式」。
- `AodPreviewHider.java` — 只在 `alive()` 时隐藏 AOD 设置预览块。
- `AodWallpaperPreview.java` — ThemeManager / AOD 的预览占位图替换；hook `getPlaceholderWallpaper`、`uf`、`ni7`、`t8r`，不要轻易动。

### 跨进程与锁屏

- `SceneProvider.java` — `content://org.aliveclean.scene/configuration`。
- `SceneChannel.java` — `what`：1 场景、2 区域、3 背景、4 触摸；调用方校验 `STATUS_BAR` 权限和 `sendingUid`。
- `KeyguardTouchRelay.java` — 钩 `NotificationShadeWindowView.dispatchTouchEvent`，转发锁屏触摸；不要恢复旧的 `scene what=1` 发送逻辑，锁屏动画由 `CleanWallpaper.update()` 根据 keyguard 状态决定。

### 系统服务 hook

- `DeviceIdleDelayHook.java` — 本次新增；hook `com.android.server.DeviceIdleController`：
  - `updateInteractivityLocked()` 之前，如果 `mScreenOn == true`，记录 1200ms 延迟窗口并 `postDelayed`。
  - `becomeInactiveIfAppropriateLocked()` 之前，如果窗口未到、`mQuickDozeActivated == true`、`mState == 0`，直接 `setResult(null)` 跳过。
  - 1200ms 后由 handler 调用原方法，恢复正常的 `no activity` 转换。
  - 日志：`device idle: no-activity armed for 1200ms`、`device idle: first no-activity deferred`。

### 已删除 / 保留的边界

- 0.6.0 删掉了 ColorOS 时钟层（`NativeClockProvider` 等 34 个不可达类）和 `com.oplus.*` 相关死代码。
- 保留 `native-clock/runtime/hyperos/runtime.apk` 及 HyperOS 字体：这是官方 AOD/时钟运行时，不是 ColorOS 残留；`OfficialHyperOsUi` / `XiaomiAodPreview` / `XiaomiPreviewActivity` 仍依赖。
- `app/assets/vivo/**` 是 Vivo 活壁纸功能，不要按“无静态引用”删除。
- `research/`、`workbench/` 不是 APK 内容；`workbench` 不提交。

---

## 5. 0.6.1 修了什么

### 5.1 AOD 入场重复初始化

旧 `HyperOsAod.installDoze()` 在 `onDreamingStarted` 后 900ms 又 `refreshScene()`，把 AOD Surface detach 后重新 attach，导致一次息屏里两套 EGL 初始化、两次照片解码。

修复：

- 删除 900ms `refreshScene()` 与 3000ms `dumpTree()`；
- 删除 `refreshScene()` 本身；
- `attach()` 在 `target.addView(surface, 0, ...)` 后设置 `liveSurface`；
- `onSurfaceTextureDestroyed` / `onDetachedFromWindow` 清理 `liveSurface`；
- 删除 `attach` 后的 `dumpTree("after-attach")`；
- `AodOverlay.hide()` 增加空 View 防护。

真机日志（SystemUI 重启后）：每次 `onDreamingStarted` 只有 1 条 `EGL init requested=1200x2608` 和 1 条 `aod photo from provider`，没有 `scene host dropped for refresh`。

### 5.2 熄屏 quick-doze 卡顿

`DeviceIdleController` 在这台机器上是 quick-doze 模式：`screen_toggled 0` 后约 116–191ms 就会 `device_idle [7,no activity]`，正好压在 AOD 入场动画上，CPU 频率下降会造成首帧抽帧。

修复就是上面的 `DeviceIdleDelayHook`：把第一次 `no activity` 推迟 1200ms，不改变全局 doze 常量、不加 boost、不加 wakelock。

真机证据（2026-11-07 18:03）：

```
18:03:24.298  screen_toggled 0
18:03:25.653  device_idle [7,no activity]
间隔 1355ms（修复前 116–191ms）
```

同时 `HyperAliveHooks` 有：

```
device idle delay hook: ok
device idle: no-activity armed for 1200ms
device idle: first no-activity deferred
```

用户随后确认「好了」。

事后复核（2026-11-07 18:32，用户保持息屏）：

```
18:32:30.192  screen_toggled 0
18:32:31.493  device_idle [7,no activity]
间隔 1301ms
```

`dumpsys deviceidle` 当时 `mState=QUICK_DOZE_DELAY`（state 7），`mScreenOn=false`、`mCharging=false`。说明 1.2 秒窗口结束后仍会正常进入 `no activity`，不是永久停在 active。

### 5.3 唤醒与锁屏动画

- `CleanWallpaper` 当前 `WAKE_DWELL=0L`、`WAKE_POLL=50L`；唤醒窗口逻辑靠 keyguard 状态采样，不再使用旧的固定 300/450ms 延迟。
- 目标行为：
  - 息屏直接到桌面：0→2；
  - 真正进锁屏再解锁：0→1→2；
  - 指纹解锁：不播锁屏段。
- 不要重新引入 display-on 固定延时或 `lockGrace` 固定窗口；之前 300/450/800/2500ms 都试过，均不稳定。

---

## 6. 硬约束（不要碰）

1. 不写 `aod_show_style`。
2. 不抢第三方全屏 AOD 模块的 hook。
3. 不替换系统壁纸；只通过 `SceneApply` 设置自己的 `WallpaperService`。
4. 不在系统页自绘 UI；入口/卡片用系统原生控件。
5. 不做系统页跳转的过渡动画。
6. `AodWallpaperMode.alive()` 为假时，绝不改系统 AOD 状态。
7. 不改包名 `org.aliveclean`；JNI 符号写死。
8. 不把 `research/`、`workbench/`、反编译 APK/中间 dex 提交进仓库或打进 APK。

---

## 7. 用户怎么合作

- 全中文，结论先行，不要客套和自我表扬。
- 质量优先，不急；宁可多跑一次真机，也不交“应该能行”。
- 先读真实代码再改；不凭记忆猜 API 签名。`hookAllMethods` 签名不对不会报错，只是永远不命中。
- 没验证的事不许说“修好了”。要说清楚：改了什么、怎么验的、看到哪条日志/截图。
- 用户明确不喜欢用 boost / wakelock 掩盖问题；优先找系统机制的真实边沿（doze、wakeup、AOD 生命周期）。
- 独立调查、反编译核对、只读审计可以派子智能体；子智能体交回结论要带文件、行号、日志原文。主线负责合并和真机验证。
- 用户会追问反例。下结论前先问：有没有可能是我判断错了？有没有别的路径被破坏？

---

## 8. 踩过的坑（省时间）

1. `adb shell input keyevent 26` 能触发熄屏，但不等价于用户双击桌面/物理电源键；用户实测“助手触发不卡、自己触发卡”。验证动画必须用用户的真实触发路径，不能只用 adb 注入。
2. `DeviceIdleController` 在 `com.android.server` 包，不是 `com.android.server.deviceidle`；在 `services.jar` 的 `classes.dex` 里，不在 `classes4.dex`。
3. `system_server` 的 hook 必须等系统重启/框架重启；`killall com.android.systemui` 只重载 SystemUI，不会加载 `DeviceIdleDelayHook`。
4. `AOD` 首帧卡顿不是 `NativeScene.render` 的 CPU 时间；`eglSwapBuffers` 会阻塞约 250ms，问题在合成/调度，不要在 render 里盲目优化。
5. `SurfaceView` 会被 AOD 窗口的不透明底盖住；不要再走 SurfaceView 方案。
6. 不要停 wallpaper producer、不要半分辨率、不要限 60fps；这些都会破坏变球/画质/流畅度，用户明确拒绝。
7. `AodOverlay.hide()` 在 View 已 detach 时可能空指针；调用前必须判空。
8. `deploy.py` 以前硬编码 0.5.0 APK，已改成从 manifest 读版本；如果发现装的是旧包，先看 `dist` 和 `dist/build-report.json`。
9. `adb` 不在 PATH；无线调试地址会变，先用 `adb devices -l` 确认。
10. `AodWallpaperModeHook` 里的 `WallpaperManagerService missing` 是 Android 17 上的旧问题（旧版同样找不到类），不影响当前动画链路；不要把它误判成本次回归。

---

## 9. 验证命令

```powershell
$adb='C:\Users\ybybabab\AppData\Local\Android\Sdk\platform-tools\adb.exe'

# 版本 / 壁纸组件
& $adb -s 192.168.1.13:39795 shell dumpsys package org.aliveclean | Select-String 'versionName|lastUpdateTime'
& $adb -s 192.168.1.13:39795 shell dumpsys wallpaper | Select-String 'mWallpaperComponent|mBindSource'

# 模块加载 & device-idle hook
& $adb -s 192.168.1.13:39795 logcat -d -s HyperAliveHooks:I | Select-String 'module loaded|device idle'

# 熄屏 → no activity 时间差（应 >1200ms）
& $adb -s 192.168.1.13:39795 logcat -b events -d | Select-String 'screen_toggled|device_idle'

# 现场抓 AOD 首帧
& $adb -s 192.168.1.13:39795 logcat -c
& $adb -s 192.168.1.13:39795 shell input keyevent 26
& $adb -s 192.168.1.13:39795 logcat -d -s HyperAliveHooks:I AliveClean:I
```

---

## 10. 当前待办 / 下一步

当前请求（进入息屏卡顿）已完成，用户确认正常；没有必须继续的代码任务。

如果后续用户再报卡顿：

1. 先抓 `screen_toggled 0` → `device_idle [7,no activity]` 的间隔，确认 1.2s 窗口是否仍然生效；
2. 同时抓 CPU 频率和 `Choreographer Skipped`，确认是否还有第二个降频机制；
3. 不要先加 boost / wakelock；用户明确更接受“延迟 no activity”，不是提高频率掩盖；
4. 若 1.2s 不够，再和用户确认窗口长度；不要凭感觉改成 2s/3s 并宣称修复。

已知但暂不处理：

- `AodWallpaperModeHook` 的 `WallpaperManagerService missing`（Android 17 旧问题，实际未挂上）；
- `AodOverlay` 兜底路径的 NPE 已加防护，但这条路径不是主路径；
- `NativeClock` 的 HyperOS runtime 资源体积大（约 57MB），但预览/AOD 仍依赖，删除属于功能取舍，不是清理；
- `workbench/` 里的 `research/`、参考字体、备份 AAR 不进仓库；若需要重新审计，另行确认。

---

## 11. 一句话给下一个人

先看 `CleanWallpaper` 的唤醒/息屏模式切换，再看 `HyperOsAod` 的 `liveSurface` 生命周期，最后看 `DeviceIdleDelayHook` 的 1200ms 窗口；这三条是 0.6.1 动画稳定的主线。不要用固定延时猜时序，不要在没抓 `device_idle` 时间戳前改动画参数。
