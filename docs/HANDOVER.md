# HyperAlive 交接文档

> 写给下一个接手这个项目的人（或 agent）。读这一份就够了，再往下才需要翻代码。
> 最后更新：2026-11-05

---

## 0. 一句话

HyperAlive 是一个 LSPosed 模块，给 HyperOS 补上「活的壁纸」：液态、光栅、闪卡三种渲染，配上锁屏触摸、息屏（AOD）联动，以及在系统「个性化」页里的一个原生入口。

设备只有一台：25102RKBEC / myron，HyperOS OS4.0.0.33.XPMCNXM，Android 17。所有验证都在真机上做，没有模拟器。

---

## 1. 现在能用的功能

| 功能 | 状态 | 怎么验 |
|---|---|---|
| 液态 / 光栅 / 闪卡壁纸渲染 | 可用 | 应用后看桌面与锁屏动效 |
| 锁屏触摸转发到渲染器 | 可用 | logcat 出现 `lock touch sent action=0/2/1` |
| 只在屏幕底部上滑解锁 | 可用 | 中部上滑解锁无反应，底部上滑正常 |
| 息屏样式锁定为「和锁屏样式一致」 | 可用 | 设置→息屏，「息屏样式」显示「HyperAlive 样式」 |
| 主题壁纸「系统个性化」页入口 | 可用 | `PersonalizeActivity` 里出现独立卡片 |
| 息屏预览整块隐藏 | 可用 | 设置→息屏，卡片下方没有「息屏预览」和预览图 |

已确认**未能解决**的一项：锁屏左右滑动时钟时，时钟数字会横移并变淡。这是系统自带效果，四次尝试（窗口层 CANCEL、区域矩形拦截、View 变换复位、独立形状排除）都没消掉。不要再轻易接这个活，代价很高。

---

## 2. 环境与构建

```
JAVA_HOME    = C:/Users/ybybabab/app/pcl/JAVA21/graalvm
ANDROID_HOME = C:/Users/ybybabab/AppData/Local/Android/Sdk
Python       = C:/Python314/python.exe
```

唯一可靠的构建入口：

```bash
cd "C:/Users/ybybabab/app/项目开发/HyperAlive"
HYPERALIVE_NATIVE_SO=.tmp/libalive_clean.so C:/Python314/python.exe tools/deploy.py --build
```

- 一次 build+deploy 约 120–150 秒，产物 `dist/HyperAlive-0.5.0-preview-arm64-v8a.apk` 约 320 MB。
- **不要**直接跑 `tools/build_release.py`，会在 javac 阶段报 `FileNotFoundError [WinError 2]`。`deploy.py --build` 会正确设置临时目录。
- 编译错误是 GBK 输出，直接看是乱码：`iconv -f GBK -t UTF-8 <log> | grep 错误`。

### 设备连接

无线调试，端口每次重开「无线调试」都会变。旧的连不上就先：

```bash
adb mdns services        # 找到 _adb-tls-connect._tcp 的地址
adb connect <ip>:<port>
```

`adb` 在 `C:/Users/ybybabab/AppData/Local/Android/Sdk/platform-tools/adb.exe`，MSYS 下要 `export MSYS2_ARG_CONV_EXCL="*"`，否则带 `/` 的参数会被转成路径。

### 改完代码怎么让它生效

- `systemui` 相关（锁屏触摸、状态栏）：`su -c "killall com.android.systemui"`
- `com.miui.aod` 相关（息屏页、AOD 策略）：`am force-stop com.miui.aod`，再 `am start -n com.miui.aod/com.miui.aod.settings.AodStyleCategoriesActivity`
- 壁纸服务（`org.aliveclean`）：`am force-stop` 无效，必须 `su -c "kill -9 <pid>"`

日志标签：`AliveClean`、`HyperAliveHooks`。

---

## 3. 架构地图

全部源码在 `app/java/org/aliveclean/`，约 165 个类。无 Gradle，资源在 `app/res/`，素材在 `app/assets/`。

入口与钩子装配：
- `CleanHooks.java` — 所有 Xposed 钩子的装配点（`assets/xposed_init` 指向它）。加钩子就在这里加一行 `step("...", () -> Xxx.install(p.classLoader))`。
- `PlatformProfile.java` — 只区分 `UNKNOWN` 和 `HYPEROS`。ColorOS 支持已经整套删除，不要再加回来。

壁纸渲染：
- `CleanWallpaper.java` — WallpaperService，内含 `SceneEngine`。
- `RenderLoop.java` — 渲染主循环，负责加载 shader 与纹理。
- `VivoEngineScene.java` — 液态 / 光栅 / 闪卡的场景实现。`touch()` 只在 `family == VivoOptions.LIQUID` 时才生效。
- `VivoEditorActivity.java` / `MainActivity.java` — 选图、调参、应用。

跨进程通道：
- `SceneProvider.java` — ContentProvider，`content://org.aliveclean.scene/configuration`。
- `SceneChannel.java` — 消息协议。`what`：1 场景、2 区域、3 背景、4 触摸。调用方必须持 `android.permission.STATUS_BAR`，进入消息要校验 `sendingUid`。

锁屏：
- `KeyguardTouchRelay.java` — 钩 `com.android.systemui.shade.NotificationShadeWindowView.dispatchTouchEvent`，把触摸归一化后转发给渲染器，并做解锁区判定。

息屏：
- `AodWallpaperMode.java` — 应用模块壁纸时把息屏样式强制成「和锁屏样式一致」；`alive(Context)` 是全局判据，查的是**当前壁纸组件是不是 org.aliveclean**。
- `AodSettingsGuard.java` — 在 `com.miui.aod.settings.SettingsView` 里锁住那一行，并把文案换成「HyperAlive 样式」。
- `AodPreviewHider.java` — 把「息屏预览」整块藏掉，**只在 `AodWallpaperMode.alive()` 为真时动**。

个性化页入口：
- `PersonalizeEntry.java` — 钩 `PersonalizeActivity.onResume`，插一张原生卡片 + 原生 `ThemePreferenceView` 行，点击进 `HomeActivity`。

---

## 4. 硬约束（不要碰）

这几条都是踩过坑换来的，不是偏好。

1. **不要写 `aod_show_style`。** 会和其他模块打架。
2. **不要抢第三方全屏 AOD 模块的 hook。** 别人在管的事不要插手。
3. **不要改系统壁纸。** 不调 `WallpaperManager` 去设置或替换用户原本的壁纸。
4. **不要自绘 UI。** 所有系统页里的入口、卡片、行，一律用系统原生控件（`ThemePreferenceView`、`CardView`、miuix 的 Preference），不自绘。
5. **不要做过渡动画。** 从「系统个性化」跳到项目主页，就用系统的普通跳转。原生那几项（息屏、壁纸、主题、图标、字体）是无缝的，「锁屏样式」是直接跳的——我们对齐后者。之前做过三版动画方案（`makeCustomAnimation`、task 劫持、半透明窗口 + `overridePendingTransition`）全部无效或效果不对，已回滚，不要再试。
6. **不要在 `alive()` 为假时动系统的东西。** 用户用系统壁纸时，息屏页必须完全是原生状态。

---

## 5. 这个用户怎么合作（很重要）

我按这些偏好干活，效果最好；反着来会被当场纠正。

### 工作方式

- **喜欢派子智能体。** 独立的调查、反编译核对、只在读的排查，派一个子智能体去跑，主线不堵。子智能体交回结论时要带证据（文件、行号、日志原文）。
- **质量优先，不急。** 他的原话是「不是要尽快，是质量优先，不急，效果好最重要」。宁可多跑一次真机验证，也不要交一个"应该能行"的东西。
- **喜欢原生实现。** 能在系统里复用现成控件/接口，就不要自己画、不要自己造动画。
- **先读真实代码，再动手。** 不许凭记忆猜 API 签名、猜方法名。`hookAllMethods` 签名不对不会报错，只是永远不命中——这种"静默失败"最坑。

### 沟通方式

- 全中文，结论先行，不要客套，不要自我表扬。
- 不确定就说不确定，并把不确定的部分单独标出来。他会追问，而且追问得很准。
- **没验证的事不许说成修好了。** 他明确讨厌这种表述。验证过就说验证过，怎么验的、看到什么日志/截图，一起给。
- 报告里带上反例检查：这个改动会不会破坏别的地方？有没有可能是我判断错了？
- 别写"零新增持久化"这种自己发明的词糊弄人，说清楚改了什么、影响范围在哪。
- 不要频繁发进度提醒/"换气"，他不喜欢；有实质进展再说。

### 他会纠正你什么

- 把约束读错。他说过「不要影响原有的系统壁纸」，我一度扩大成「不要动应用/提交链路」，被当场指出。约束就是字面那些，不要自行加码。
- 把现象归错因。「误伤」那次，我以为是标签被改，实际是预览图被我藏没了。遇到含糊的反馈，先问清楚，或者去 dump 界面找证据。
- 删除前不确认。「闪一下」那轮我顺手把 `applyLabel()` 删了，结果模块壁纸下不显示「HyperAlive 样式」，返工一次。

---

## 6. 踩过的坑（省你几天）

**签名猜错 = 静默不命中。** `hookAllMethods` 找不到方法不报错，只返回 0。每次加钩子，先打一行 `hook count=N` 的日志确认命中。

**`ViewRootImpl.dispatchTouchEvent` 在这台机器上不存在。** `hookAllMethods` count=0。真正能拿到锁屏触摸的是 `NotificationShadeWindowView.dispatchTouchEvent`（count=1）。

**`dispatchInputEvent` 存在（count=2）但锁屏时从不触发。** 不要往那儿使劲。

**HyperOS 不给第三方 WallpaperService 发触摸。** 桌面、锁屏、息屏三处都测过，`onTouchEvent` 零调用。所以触摸必须从 systemui 侧转发。

**`am force-stop` 杀不掉壁纸服务。** 要 `kill -9`。

**recycler 里折叠 View 只能折一层。** 「息屏预览」那次，`title.getParent()` 已经是整个列表条目，再往上取一层就是 `CategoriesItemRecyclerView`，结果把设置卡片一起藏了。折叠前先 `uiautomator dump` 看清层级。

**`decorView.post()` 太晚，会闪一下。** 要隐藏的块必须在首帧之前就 GONE，用 `ViewTreeObserver` 的预绘制阶段，不要用 post。

**AOD 相关的钩子在 `android`（system_server）进程里，必须重启手机才加载。** `killall com.android.systemui` 不够。

**预览图取不到时不要塞自己的图。** 用户明确要求兜底是系统默认，不是项目自带的占位图。

---

## 7. 代码卫生

现在仓库里还有一些东西看起来可疑但**不能删**：

- `CleanHooks` 全仓库零引用——它是 `assets/xposed_init` 的入口。
- `NativeClockStatusProvider`、`XiaomiLibraryActivity` 零引用——在 `AndroidManifest.xml` 里注册着。
- `tools/import_*.py` — 一次性素材导入脚本，素材已经进 `app/assets/`，脚本留着是为了改素材时可重跑。
- `tools/check_layout.py`、`tools/check_xiaomi_assets.py` — 还能运行的检查脚本。

删类之前要查三处：`git grep 类名`、`AndroidManifest.xml`、`assets/xposed_init`。反射用的字符串（`Class.forName("...")`）不会被 grep 到，要单独搜。

---

## 8. 质量规则（用户明确要求写进交接文档）

优先保证产出质量，但不接受严重的速度损耗。

具体到执行：

1. **一次只改一个变量。** 多个改动一起上是没法归因的，出错要整轮返工。
2. **每次改完必须真机验证，并留下证据**：日志原文、`uiautomator dump` 的节点、截图。没有证据就等于没做。
3. **改动前先读要改的那段真实代码**，不凭记忆、不凭对话里的转述。
4. **删代码前先确认零引用**（含 manifest、`xposed_init`、反射字符串）。
5. **报错要贴原文**，不要转述成"编译失败"。
6. **假设自己可能错。** 每次下结论前问一句：有没有反例能推翻它？如果有，先去验反例。
7. **不要为了显得勤快而做无效工作。** 该想的想透（可以长考），不该试的别瞎试。

允许长时间的深入思考，但不要在没有新信息的情况下反复重试同一个失败方案——那既不快也不对。

---

## 9. 当前状态与待办

已提交（分支见 `git log`）：ColorOS 整套删除、锁屏触摸转发、息屏预览归属判断、预览整块隐藏、死代码清理。

还没做：

- 锁屏时钟左右滑动形变（见第 1 节，已判定不划算）。
- 桌面「应用模块壁纸后变灰」那个 bug 只做了缩略图层面的排查，没有最终结论。当时的怀疑方向是 `wallpaper.xml` 缺 `android:thumbnail`，以及 `setWallpaperComponentWithFlags` 的 flag 组合，但都没有真机坐实。

如果继续做，建议顺序：先把"模块壁纸在用"这条路径完整走一遍（应用 → 息屏页看标签与预览 → 锁屏上滑 → 桌面返回），确认第 1 节那张表都成立，再开新功能。
