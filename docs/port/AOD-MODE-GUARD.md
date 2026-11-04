# AOD 模式强制 / 锁定 / 恢复与 Flyme 串值修复

日期：2026-11-04
基线：`drop-coloros` @ `0122c5f`
状态：代码已改，编译通过；**未 commit、未 deploy、未改设备设置**。

## 1. 结论先行

1. 「和锁屏样式一致（全屏 AOD）」在系统里的键是 **`Settings.Secure.full_screen_aod_on`**，int：
   `0` = 自定义，`1` = 和锁屏一致。不是 System 键，不是 `com.miui.aod` 私有 SharedPreferences。
2. `aod=101` **不是系统设置**。它是本模块自己的渲染标记：
   - `SceneOptions.java:29` 把 101 列为合法 AOD 值；
   - `OfficialEditor.java:307` 把它显示为「全屏 AOD」；
   - `RenderLoop.java:244` 用 101 加载 `fullAod/gray.png`；
   - `SceneMotion.java:177/192` 用 101 走 full 轨迹；
   - `SceneProvider.java:28` 在 Xiaomi 时钟接管时也返回 101。
   它只影响 SystemUI 侧的本模块渲染，不会写 `Settings.Secure`。
3. Flyme 样式后 AOD 被切到全屏 AOD 的根因不是 Flyme 应用路径本身，而是共享草稿被污染：
   - 修改前 `VivoEditorActivity.java:99` 在每次 `saveDraft()`（包括打开 Vivo 编辑器时的 `onCreate`）无条件
     `putInt("aod", 101)` 到 `scene.preview`（`SceneOptions.DRAFT`）；
   - 之后应用 Flyme/任意壁纸时，`MainActivity.commitDraft()`（`MainActivity.java:114-123`）把整个
     `SceneOptions` 快照（含 `aod`）从 DRAFT 复制到 `scene`（`SceneOptions.APPLIED`）；
   - 于是上一次 Vivo 编辑器留下的 101 被当成用户选择提交，SystemUI 读到的就是 101（全屏 AOD 渲染），
     同时设置页里 `full_screen_aod_on` 的既有值 1 看起来就像「被改成了和锁屏一致」。
   唯一持久写 `aod=101` 的位置就是 `VivoEditorActivity.java:99`；Flyme 路径本身没有 AOD 写入。
4. 第 3 条需求要改的是 `com.miui.aod.settings.SettingsView` 的 full-AOD Spinner：
   `udpateFsAodStyleDropView()` 只放两项（`自定义` / `和锁屏一致`，`SettingsView.java:298-324`），
   `AodSettingsGuard` 原来只做越界 clamp，不显示任何名字。现在锁定时把第 2 项文案改为
   `HyperAlive 样式`，并禁用 Spinner。

## 2. 证据

### 2.1 系统键

反编译设备上的 `com.miui.aod`（`/product/priv-app/MIUIAod/MIUIAod.apk`）：

- `Utils.getFsAodStyle(Context)`：
  `Settings.Secure.getInt("full_screen_aod_on", ...)`（Utils.java:522-524）
- `Utils.isFsAodSameToLock(Context)`：
  `isSupportFsAod() && getFsAodStyle(context) == 1`（Utils.java:530-532）
- `Utils.setFsAodStyle(Context,int)`：
  `Settings.Secure.putInt("full_screen_aod_on", i)`（Utils.java:526-528）
- `SettingsView.udpateFsAodStyleDropView()`：
  添加 `R.string.aod_resting_screen_define`（索引 0）与
  `R.string.aod_resting_screen_same_to_lock`（索引 1），
  `onItemSelected` 调 `Utils.setFsAodStyle(context, i)`（SettingsView.java:298-324）
- `AnalyticManager`：
  `full_screen_aod_on == 1 ? "和锁屏一致" : "自定义"`（AnalyticManager.java:195）
- `Utils.isFullAodSupport(Context)` 读的是 `full_screen_aod_support`，只是能力位（Utils.java:542-547），
  不是用户模式。

设备只读核对（`adb shell settings list secure | grep -i aod`，2026-11-04）：

```
full_screen_aod_on=1
full_screen_aod_support=1
aod_show_style=0
aod_style_state=1
aod_mode_user_set=1
```

`full_screen_aod_support=1` 说明本机具备全屏 AOD 能力；`full_screen_aod_on=1` 说明当前就是「和锁屏一致」。
这也解释了为什么 bug 看起来像「设置项被改掉」：草稿里的 101 切换了本模块的渲染模式，
而系统键原本就是 1，用户看到的就是全屏 AOD。

### 2.2 参考模块 `全屏AOD_1.0.apk`

jadx 结果 `cn/domexie/hyperos_hook/full_aod/HookInit.java:19-40`：

- `com.miui.aod`：`Utils.isSupportFsAod()` 恒 true；
- `com.android.systemui`：`SystemUIInitializer.init()` 前把 `MiuiConfigs.SUPPORT_FULL_AOD` 置 true。

它只负责「让系统出现/启用全屏 AOD 能力」，**没有**写 `full_screen_aod_on`，也没有监听壁纸应用。
因此本次实现不照抄它的两个能力 hook，避免与第三方全屏 AOD 模块抢同一 hook 点；
我们直接写系统自己的模式键，并用 `full_screen_aod_support` 作为前提（本机已为 1）。

### 2.3 `aod=101` 的真实用途

- `SceneOptions.java:29`：`a==-1||a==0||PhotoStyle.supported(a)||a==101 ? a : 0`
- `OfficialEditor.java:307`：`new Choice(101,"全屏 AOD",...)`
- `RenderLoop.java:244`：`if(options.aod==101) loadAuxiliary(3,"shader/photo/aod/fullAod/gray.png",false);`
- `SceneMotion.java:177/192`：101 使用 full 时间轴/轨迹
- `SceneProvider.java:28`：Xiaomi 时钟接管时 `result.putInt("aod",101)`

结论：101 是模块内的渲染选择，不是 `Settings.Secure` 的取值；把它写进共享草稿会污染下一次提交。

## 3. 实现

### 3.1 文件

| 文件 | 改动 |
|---|---|
| `app/java/org/aliveclean/AodModeGuard.java` | 新增：跨进程状态、lock/restore、三个进程的 hook |
| `app/java/org/aliveclean/SceneApply.java` | UID 1000 适配器在壁纸确认后写模式并输出结果；app 侧镜像到 SharedPreferences |
| `app/java/org/aliveclean/AodSettingsGuard.java` | 保留越界 clamp；新增 `udpateFsAodStyleDropView` / `updateSpinnerState` 锁定文案与禁用 |
| `app/java/org/aliveclean/CleanHooks.java` | 增加 `com.miui.miwallpaper` 与 `android`（system_server）分支 |
| `app/java/org/aliveclean/VivoEditorActivity.java` | 删除 `putInt("aod",101)`，Vivo 不再触碰 AOD 模式 |
| `app/res/values/strings.xml` | `xposed_scope` 增加 `com.miui.miwallpaper` |

### 3.2 保存原值的位置

跨进程权威记录（系统进程能读、能写）：

- `Settings.Secure.alive_aod_mode_locked`：`1` 表示 HyperAlive 正在锁定
- `Settings.Secure.alive_aod_mode_original`：`-1` = 原键不存在；`0` / `1` = 原值
- `Settings.Secure.alive_aod_mode_saved_at`：写入时间（秒）

App 侧镜像（满足 SharedPreferences 要求，供 UI/诊断）：

- 文件：`SharedPreferences("aod.guard", 0)`
- 键：`locked`（boolean）、`original`（int）、`saved_at`（long）、`last_action`、`last_error`

为什么不是只放 SharedPreferences：`com.miui.aod` / `com.miui.miwallpaper` / system_server 不能读
`/data/data/org.aliveclean/shared_prefs`（SELinux），恢复逻辑必须在系统进程里执行，所以权威值放
`Settings.Secure`；App 私有 SharedPreferences 只做镜像。写入时机是 `SceneApply` 确认壁纸 component
已经生效之后（`SceneApply.java:53-58`），镜像写入是 `SceneApply.apply()` 收到
`SCENE_APPLIED {...}` 之后（`SceneApply.java:29-34`）。

### 3.3 锁定方式

`AodModeGuard.lock(ContentResolver)`：

1. 先读 `Settings.Secure.getString("full_screen_aod_on")`；
   - `null` → 记为 `-1`（原键不存在，恢复时删除该键）
   - 非 `0/1` 或读失败 → **直接返回错误，不写任何东西**
2. 写 `alive_aod_mode_original`、`alive_aod_mode_saved_at`
3. 写 `full_screen_aod_on=1`，回读验证
4. 最后写 `alive_aod_mode_locked=1`
5. 任一步失败：回滚 `full_screen_aod_on` 到原值并清理 original，不留下半锁状态

`AodSettingsGuard` / `AodModeGuard.installAodSettings` 在 `com.miui.aod` 进程：

- hook `com.miui.aod.Utils.setFsAodStyle(Context,int)`：锁定且请求值 != 1 时把参数改成 1；
- hook `android.provider.Settings.Secure.putInt(ContentResolver,String,int)`：锁定且 key 是
  `full_screen_aod_on` 且请求值 != 1 时改成 1（第二道防线）；
- UI：把 full-AOD Spinner 第 2 项文案改为 `HyperAlive 样式`，`setSelection(1)`，`setEnabled(false)`，
  contentDescription 改为 `HyperAlive 样式，已锁定`。

### 3.4 恢复触发点

1. `com.miui.miwallpaper`（新增 scope）：
   - `MiuiWallpaperManagerService.Q(WallpaperManager, ComponentName, int)` after：
     component 是 `org.aliveclean` → lock；否则 → restore。
     证据：`Q` 是 `setWallpaperComponentWithFlags` 的唯一调用点（MiuiWallpaperManagerService.java:810-831）。
   - `MiuiWallpaperManagerService.S(String,String,String,int)` after：静态系统壁纸路径 → restore。
2. `android`（system_server）：
   - `com.android.server.wallpaper.WallpaperManagerService` 的 `setWallpaper` /
     `setWallpaperComponent` / `setWallpaperComponentWithFlags` after；
   - 700ms 后检查一次，若不是 `org.aliveclean` 再等 700ms 复核，仍不是才 restore；
   - 覆盖相册「设为壁纸」、第三方选择器、开机恢复等绕过 miwallpaper 的路径。
3. `SceneApply`（UID 1000）应用本模块壁纸成功后直接 lock，不等系统回调。

`restore` 只在 `alive_aod_mode_locked==1` 时动作；读不到 `alive_aod_mode_original`（不是 -1/0/1）就报错
并且不写 `full_screen_aod_on`。恢复时先清 `locked`，再写原值；原值为 -1 时删除该键。

### 3.5 第 4 条 bug 修复

`VivoEditorActivity.saveDraft()` 删除 `.putInt("aod",101)`，保留 `vivo/cosmic/lock/home` 的写入。
AOD 是用户级设置，Vivo 只提供 `vivo` 场景；不碰 `aod` 就不会把 101 带进后续 Flyme/系统壁纸提交。

## 4. 编译结果

命令（本机构建环境没有 `cargo`，用仓库已有的预编译 `libalive_clean.so` 跳过 Rust 步骤；仍只编译、不安装）：

```bash
cd C:\Users\ybybabab\app\项目开发\HyperAlive
JAVA_HOME=C:/Users/ybybabab/app/pcl/JAVA21/graalvm \
ANDROID_HOME=C:/Users/ybybabab/AppData/Local/Android/Sdk \
PYTHONIOENCODING=utf-8 \
HYPERALIVE_NATIVE_SO=C:/Users/ybybabab/app/项目开发/HyperAlive/.tmp/libalive_clean.so \
C:/Python314/python.exe tools/build_release.py --no-checks
```

结果：**exit code 0**，产物 `dist/HyperAlive-0.5.0-preview-arm64-v8a.apk`，
sha256 `91a1bf0ddb5a3972abb93887e509db8d5028830ad6bc3a25cfdf4c5c80efdf6e`，320,551,496 bytes。
仅有 javac 的 `source/target 8 已过时` 警告，无错误。

若你的环境有 `cargo`，可以去掉 `HYPERALIVE_NATIVE_SO=...` 再跑一次；两者只影响 native 库来源，
不影响本次 Java 改动。

## 5. 真机验证（只读命令，写操作由用户手动做）

前置：LSPosed 作用域勾选 `com.miui.miwallpaper`（新增），重启手机或重启相关进程。

```bash
ADB="C:/Users/ybybabab/AppData/Local/Android/Sdk/platform-tools/adb.exe"
$ADB logcat -c
$ADB logcat -s HyperAliveHooks HyperAliveAodMode
```

1. **应用本模块壁纸**
   - 先记录当前值：`$ADB shell settings get secure full_screen_aod_on`（本机当前是 `1`；
     若要在验证中看到「0 -> 1」的变化，需要用户先在系统设置里手动改成「自定义」，这是用户操作，不是本补丁做的）。
   - 打开 HyperAlive → 锁屏/桌面 → 应用。
   - 期望日志：`HyperAliveAodMode {"ok":true,"action":"lock","before":0/1/-1,"after":1,...}`
   - 期望：
     ```bash
     $ADB shell settings get secure full_screen_aod_on   # 1
     $ADB shell settings get secure alive_aod_mode_locked # 1
     $ADB shell settings get secure alive_aod_mode_original # 原值
     ```
2. **打开 AOD 设置**
   ```bash
   $ADB shell am start -n com.miui.aod/com.miui.aod.settings.AodStyleCategoriesActivity
   ```
   - 期望界面：全屏 AOD 行显示 `HyperAlive 样式`，Spinner 灰色不可点；
   - 尝试改「显示模式」/全屏 AOD，值仍为 1；
   - 日志出现 `aod mode label -> HyperAlive 样式`、`blocked setFsAodStyle(...) while locked`（如果尝试改）。
3. **应用系统壁纸**
   - 设置 → 壁纸 → 选一张静态壁纸并应用。
   - 期望日志：`{"ok":true,"action":"restore","before":原值,"after":原值}`
   - 期望：
     ```bash
     $ADB shell settings get secure alive_aod_mode_locked # 0
     $ADB shell settings get secure full_screen_aod_on   # 原值（若原来没有该键则为 null）
     ```
4. **Flyme 回归**
   - 先打开一次 Vivo 编辑器（触发 saveDraft）→ 返回；
   - 再应用 Flyme 壁纸/样式；
   - 期望：AOD 场景仍是用户原来的模式，不会跳到全屏 AOD；
     `adb logcat -s AliveClean | grep "commit aod="` 应看到 `aod=0`（或用户原值），不是 101。
5. **失败安全**
   - 若 `full_screen_aod_on` 读失败或值异常，日志出现
     `{"ok":false,"action":"lock","error":"..."}`，且 `settings get secure full_screen_aod_on` 保持原值；
     壁纸应用仍然成功（AOD 锁定失败不会让壁纸失败）。

## 6. 风险与回退

风险：

- 本机 `full_screen_aod_support=1` 是全屏 AOD 的前提；若某机型该能力为 0，`full_screen_aod_on=1`
  不会真正生效（本实现不伪造 `isSupportFsAod()` / `SUPPORT_FULL_AOD`，避免和第三方全屏 AOD 模块冲突）。
- 新增 `com.miui.miwallpaper` 作用域；LSPosed 需要重新勾选并重启，否则恢复触发点不生效。
- 系统壁纸恢复依赖 `MiuiWallpaperManagerService.Q/S` 与 `WallpaperManagerService` 的方法名；
  不同 ROM 版本若改名，会在日志里留下 `... hook failed`，但不会崩溃；此时 `SceneApply` 的 lock 仍生效，
  恢复需要手动在系统设置改回。
- `alive_aod_mode_*` 是模块自有 Secure 键；卸载模块后不会自动清理，需按下面回退。

回退：

1. 代码：`git apply -R docs/port/aod-mode-guard.patch`（或直接 `git checkout -- <files>`）。
2. 设备：
   - 先在系统 AOD 设置里把「全屏 AOD」改回原值（模块停用后设置页可改）；
   - 或临时用 root 恢复（**本次未执行**）：
     `adb shell su -c 'settings put secure full_screen_aod_on <原值>'`
     `adb shell su -c 'settings delete secure alive_aod_mode_locked'`
     `adb shell su -c 'settings delete secure alive_aod_mode_original'`
3. 卸载/停用模块并重启。

## 7. 未确定项

- 未在真机上执行写入，因此「设置页文案是否在 MIUI 的 `miuix.appcompat.widget.Spinner` 上按预期显示」
  只做了代码级确认（adapter 是 `ArrayAdapter`，第 2 项存在）；需要用户按上面步骤验证。
- 若用户的「flyme 样式」指的不是 `MainActivity` 的魅族壁纸/Flyme 视频，而是另一个未在本仓库出现的
  编辑器，需要补充该路径的代码位置；本仓库内唯一持久写 `aod=101` 的位置仍是
  `VivoEditorActivity.java:99`（已修）。
