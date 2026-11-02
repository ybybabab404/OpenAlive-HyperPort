# HyperAlive 需求清单

> 这份文档只记「用户要什么」。每条都写清来源与状态，实现细节去 docs/port/PROGRESS.md。
> 每次对话有新要求，都要先更新这里，再动代码。

最后更新：2026-11-02 19:32

## 一、息屏（AOD）

| # | 要求 | 状态 | 备注 |
|---|---|---|---|
| A1 | 息屏球是**对壁纸做后处理**（模糊/压暗/球形化），不是「取主色」；后处理的输入是用户设置的壁纸本体 | ✅ 已实现 | `AodWallpaperProvider` + `RenderLoop.loadPhoto`；口径以后处理为准，不要再按取色描述 |
| A2 | 要**暗**，甚至可以更暗；目的是即使与时钟重叠也不抢注意力 | ✅ 球亮度系数 0.55（lens_photo_frag 末尾乘法） | 用户只说「压暗更大一点」，不做其他花活 |
| A4 | 球的取色与构图对齐魅族参照，但**保留小米特色**（时钟样式等），不照抄 | 🔄 进行中 | 参照图：魅族球内显示时间、球下日期与电量 |
| A5 | 进入/退出 AOD 的过渡动画（整屏壁纸收缩成球 / 反向） | ⛔ 全部禁用，待重做 | 用户指出：**动态壁纸服务在息屏时不可见**，不能作为过渡宿主。方向改为「自建窗口 / 突破息屏省电限帧」，见 A6 |
| A6 | 过渡动画的宿主必须是能在息屏时正常帧率绘制的自有窗口（或解除 doze 限帧），不能用 WallpaperService | 📌 待实现 | 这是 A5 的前置条件 |
| A7 | 息屏画面**不能闪烁**，球的流动进度不能被重置 | 🔄 注入方式待改 | 实测 15 秒内 `scene re-attached` 5 次：MIUI 会反复清空 AODView 的子视图，我们把视图塞进它里面就会被反复移除/重建 → 每次重建 SurfaceTexture 都重置动画。修法：改为**自建悬浮窗**（全屏透明，只在上方画球），不再嵌进 MIUI 的 AODView |

## 二、临时覆盖的边界（重要）

| # | 要求 | 状态 | 备注 |
|---|---|---|---|
| B1 | 对壁纸的处理必须是**临时的**：模块停用/卸载后，系统回到原来的壁纸，不留痕迹 | ✅ 已满足 | 只在本进程内用 ContentProvider 递图，系统壁纸设置从未被写 |
| B2 | 不要把壁纸图乱塞到各种共享路径 | ✅ 已满足 | 不落盘副本；provider 直接读用户原本的照片文件 |

## 三、工程约束

| # | 要求 | 状态 |
|---|---|---|
| C1 | 包名必须是 `org.aliveclean`（native 符号写死） | ✅ |
| C2 | 构建走 `tools/build_release.py`（aapt2→javac→d8→zip→zipalign→apksigner），不是 Gradle | ✅ |
| C3 | 每次对话把所有要求汇总进本文档，防止遗忘 | ✅ 本条即为此 |
| C4 | 以后优先用**增量更新**（只推变动的 dex/资源），不要每次装 320MB 整包 | 🔄 一半：`tools/deploy.py` 已加（USB 优先、同包跳过、push+pm install）；真正的体量拆分待做 | APK 320MB 构成：assets/native-clock 88MB、wallpapers 77MB、xiaomi 28MB、flyme-video 26MB、vivo 25MB → 共 244MB 是很少变的素材；代码+shader+lib 约 77MB。`adb install --incremental` 在本机报 Bad file descriptor，不可用。方案：aapt2 分片（base + 素材 split），素材只装一次 |

## 四、已知坑（避免重复踩）

- 构建失败时 `install` 仍会「Success」，装的是旧包 —— 每次改完必须确认 `build_release.py` 真的跑完。
- AOD 视图跑在 SystemUI 进程，读不到 `org.aliveclean` 私有目录（SELinux），必须走 provider。
- `mode(0)` 是异步投递，attach 时 `mode` 可能还是默认值，判定不要依赖它。
- 模块多次崩溃会让 LSPosed 进安全模式（模块全停），只能重启手机恢复。

## 五、构建/部署命令

```bash
# 改完代码后一条命令：优先 USB，没 USB 自动试无线地址，同包会跳过
python tools/deploy.py

# 强制重编再装
python tools/deploy.py --build
```

真·增量（只传变动部分）还做不到：`aapt2 --split` 只能按资源**配置**拆，不能按 assets 拆；要拆只能把 244MB 素材挪到独立包（org.aliveclean.media），但那要改 70 处 `getAssets()` 调用。已确认这条路，等要做时按「媒体包 + 首次解包到私有目录」实现。

## 六、关键发现：全屏 AOD 才是闪烁的真解（2026-11-02 22:00 用户实测）

- 用户在系统设置里把「显示模式」改为 **和锁屏样式一致（全屏 AOD）** 后：
  **闪烁消失**，进场/退场动画**流畅**。
- 对照实验：18:14 版（`0544483`）、18:52 版（`d7c7217`）在「自定义」模式下仍会闪，
  说明闪烁不是代码回归，而是 **AOD 显示模式**决定的。
- 以前所有"修闪烁"的尝试，都是在错误模式下的渲染路径上较劲。

### 全屏 AOD 是怎么被打开的（拆 `全屏AOD_1.0.apk` 得到）

该 APK（`cn.domexie.hyperos_hook.full_aod`，6KB dex）只有三个 hook：

1. `com.miui.aod`：`com.miui.aod.Utils.isSupportFsAod()` → 恒返回 `true`
2. `com.android.systemui`：`SystemUIInitializer.init()` 之前，
   把 `com.miui.utils.configs.MiuiConfigs.SUPPORT_FULL_AOD` 置 `true`
3. 自身进程：`MainActivity.isModuleActivated()` → true（仅状态显示）

这两处一开，系统设置里出现「和锁屏样式一致」，且真的生效。

## 七、新方案：显示模式新增第三项「HyperAlive 样式」（已记录，未实现）

### 目标

在「息屏与锁屏编辑 → 显示模式」里，除现有的
`自定义（普通 AOD）`、`和锁屏样式一致（全屏 AOD）`，**再增加一项** `HyperAlive 样式`。

- 选中 `HyperAlive 样式`：底层按**全屏 AOD** 运行（这是流畅不闪的前提），同时启用模块的
  球体 / 壁纸后处理效果。
- 选中其它任何选项：模块**完全不介入**，保持 MIUI 默认行为。

### 硬约束

1. **不改动原有选项**：不重命名、不隐藏、不改变语义。
2. **不占用别人的逻辑**：不 hook「和锁屏样式一致」的既有实现；与 `full_aod` 这类模块必须能共存。
   只**新建**自己的样式项。
3. **默认行为不变**：没选 `HyperAlive 样式` 时，效果 / hook / 窗口一律不生效。
4. 只在 `com.miui.aod`、`com.android.systemui` 两个进程内工作。

### 实现前待查

- [ ] 「显示模式」存在哪个 key（Settings.Secure / System / MIUI 私有 prefs），枚举值是什么。
- [ ] 该列表是 Spinner / Preference / 自定义 View，最小侵入点在哪。
- [ ] 自定义样式值存**独立 key**（如 `alive_aod_style`），绝不写 MIUI 的 key。
- [ ] 选中时如何让系统"以为是全屏 AOD"：优先读取 `isSupportFsAod()` 与 `SUPPORT_FULL_AOD` 的结果，
      不 hook 它们；若系统不具备该能力则在 `com.miui.aod` 内部走全屏 AOD 分支。
- [ ] 与「全屏AOD」模块共存测试，不能双重 hook 或崩溃。

### 状态

📌 仅记录，未实现（用户 2026-11-02 22:06 指示先记下来）。

### 七之补充：与「其他给系统加『和锁屏一致』的模块」共存（2026-11-02 22:10）

要求：别的模块加了「和锁屏一致」后必须**照常独立工作**，我们既不依赖也不占用它。

做法（三层隔离）：

1. **不 hook 那两处开关**。`isSupportFsAod()` / `MiuiConfigs.SUPPORT_FULL_AOD` 归别人管；
   我们只**读取**结果，决定自己的样式是否可见/可用。别人关掉它，我们的样式就跟着不可用，
   而不是我们替它打开。
2. **只在读「显示模式」时做一次性翻译，且只翻译我们自己的值**：
   - 选的是我们的项 → 向 MIUI 返回「和锁屏样式一致」的枚举值（让它走全屏 AOD 渲染）；
   - 选的是任何 MIUI 原生值（含被别人加出来的「和锁屏一致」）→ **原样返回，零改动**。
   因此别人的模块看到的值与没装我们时完全一致。
3. **列表项用「方法级注入」，不替换 Adapter 实例**：
   hook `getCount()` / `getItem()` / `getView()` / `getDropDownView()` 这一组，
   在自己的位置插一项。这样多个模块各自追加自己的项也能叠加；
   如果某模块直接 `setAdapter()` 整体替换，则只有替换者生效——这是它的实现方式决定的，
   不是我们抢占；文档里如实写明这一限制。
4. 自己的状态存独立 key（`alive_aod_style`），**从不写 MIUI 的 key**。

结论：行为层完全独立；UI 列表在"双方都用方法级注入"时可叠加，属于尽力而为。
