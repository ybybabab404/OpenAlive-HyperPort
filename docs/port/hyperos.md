# HyperOS 移植记录

## 目标设备

| 项 | 值 |
| --- | --- |
| 机型 | Redmi 25102RKBEC（`myron`，平台 `canoe`） |
| 系统 | HyperOS 4.0（`OS4.0.0.33.XPMCNXM`，MIUI 标识 V816） |
| Android | 17（SDK 37） |
| 分辨率 | 1200x2608 @ 480dpi |
| Root | KernelSU（`su` 可用，context `u:r:ksu:s0`） |
| 框架 | LSPosed（`zygisk_lsposed` 模块） |
| 关键系统应用 | `com.android.systemui`、`com.miui.miwallpaper`、`com.miui.aod`、`com.android.thememanager` |

## 上游与本分支的关系

上游 OpenAlive 以 ColorOS 17 为主要目标，集成点有三处：

1. `com.android.systemui` 内的 AOD 面板、解锁接力
2. `com.oplus.wallpapers` 内的原生时钟编辑器（`NativeClockApplyPolicy`、`ColorOsNativeClockFonts`）
3. `com.oplus.aod.AodMachineHelperProvider` 等系统 provider（`PlatformApply` 以 system UID 调用）

HyperOS 上这三处都不存在。直接加载的结果是：每个钩子都抛异常、被上游的
`try/catch` 吞掉，模块本身还能跑，但日志里全是失败，且 SystemUI 里留着一批永不触发的钩子。

## 本分支的改动

### 平台识别

新增 `PlatformProfile`：按已安装包判断当前系统（`com.miui.miwallpaper` → HyperOS，
`com.oplus.wallpapers` → ColorOS），并提供 `hasVendorClass(ClassLoader)`
用类探测判断当前进程是否真的加载了 ColorOS 的个人化栈。

### 钩子入口

`CleanHooks` 重写为「按包名 + 类探测」双条件：

- `com.android.systemui`：只装 `ModuleActivationHook`；只有检测到 ColorOS 类时才装
  `ColorOsBridge` 与字体钩子，HyperOS 上不再注册任何永不命中的钩子。
- `com.oplus.wallpapers`：已不再注册。CleanHooks 重写后模块只注册 `com.android.systemui`、`com.miui.aod`、`com.android.thememanager` 与 `android`，
  上游那条时钟编辑器注入路径不再随模块安装。
- `android`：保留 `supportsAmbientMode` 与 `AMBIENT_WALLPAPER` 权限适配（这与 OEM 无关，
  HyperOS 也需要它才能让第三方动态壁纸进 AOD 列表）。

### 壁纸入口

`WallpaperPicker` 增加 HyperOS 候选顺序：先 `com.android.wallpaper.livepicker`
（HyperOS 4 上它就是 `ACTION_CHANGE_LIVE_WALLPAPER` 的处理器），再退化到主题应用。

### 未移植（明确的能力差距）

| 功能 | 原因 | 后续方向 |
| --- | --- | --- |
| AOD 面板动画 | HyperOS 的 AOD 由 `com.miui.aod` 自绘，第三方壁纸不参与合成 | 需要 hook `com.miui.aod` 的时钟视图并复用 `XiaomiSurfaceLease` 的思路把 GL 场景送进去 |
| 锁屏编辑器原生时钟 | ColorOS 编辑器专属 | HyperOS 的锁屏时钟在 `miui.systemui.plugin` 里，需要另做一套注入 |
| 解锁接力 | 依赖 ColorOS SystemUI 类 | 需要针对 HyperOS 的 SystemUI 类重写 |

这三项都写在 `ColorOs*` 类里，HyperOS 上不会被加载，也不会影响其余功能。

## 验证

已在设备上完成：

- 装机：`adb install -r`（跨签名覆盖成功，设备已开启对应放行）
- 启动：`org.aliveclean/.HomeActivity` 正常 resume，进程存活，GL 线程起
- 应用内编辑器打开无崩溃（截图见提交记录）

待设备侧手动确认（需要用户点按，不在自动化范围内）：

1. LSPosed 中启用模块并勾选作用域
2. 在壁纸选择器里应用 AliveHS 壁纸并观察 AOD

## 当前进度（2026-11-02 12:55）

已确认并跑通：

- HyperOS 4 的 AOD 宿主是 `com.android.systemui`（`AodShowing=true` 时只有 SystemUI 在跑，`com.miui.aod` 不启动）
- `CleanHooks` 改为每个 install 单独 try/catch；此前 `ModuleActivationHook.install()` 抛异常会中断整个 handleLoadPackage
- 钩子已生效：`AliveHSAod: AOD view hook installed on com.android.keyguard.widget.AodView`
- 壁纸应用改走 system UID（`SceneApply`），HyperOS 的 live picker 对第三方组件固定回 RESULT_CANCELED
- 桌面与锁屏已能显示场景（用户确认）

进行中：

- AOD 场景层：`AodOverlay` 在 `MiuiDozeService#onDreamingStarted` 时挂 `TYPE_APPLICATION_OVERLAY` 窗口（`TYPE_DREAM` 需要 dream token，`BadTokenException` 已排除该路径）
- 判定「壁纸是否属于本模块」时同时看 FLAG_SYSTEM 与 FLAG_LOCK，因为 HyperOS 的锁屏壁纸是 `MiuiKeyguardPictorialWallpaper`

已知问题：

- 若 AOD 仍不显示，next: 把 overlay 的 `setAlpha` 与 window flag 调整（AOD 需要 `FLAG_LAYOUT_NO_LIMITS` 与亮度控制），或改为在 `AodView` 内部注入而不是独立窗口
