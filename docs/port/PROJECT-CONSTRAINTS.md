# HyperAlive · 项目基本约束（独立文档）

> 这份文档只记「不能变的前提」。改代码前先看这一页；与 `REQUIREMENTS.md`（要什么）、
> `docs/port/PROGRESS.md`（做到哪）分工不重叠。
> 建立于 2026-11-03。

## 1. 项目身份

| 项 | 值 |
|---|---|
| 项目名 | **HyperAlive** |
| 包名 | `org.aliveclean`（native 符号写死，不可改） |
| 上游源码 | **OpenAlive-main**（移植来源；本仓库是移植与改造版，不是 fork 出来的新项目） |
| 目标平台 | 小米 HyperOS（HyperOS 2 / OS4 系列），Android 15–17 |
| 形态 | LSPosed 模块 APK（Xposed），非 Magisk 模块、非 Gradle 工程 |

## 2. 移植目标

把 OpenAlive 的「息屏 / 锁屏 / 桌面连贯动画」体验移植到 HyperOS：

- 息屏：对壁纸做后处理（模糊 / 压暗 / 球化）后显示，不是取主色；
- 锁屏与桌面的连贯过渡；
- 所有效果必须走**系统原生渲染路径**，不额外自绘一整套 UI。

## 3. 设备环境（当前验证机）

| 项 | 值 |
|---|---|
| 机型 | 25102RKBEC（代号 `myron`） |
| 系统 | HyperOS OS4.0.0.33.XPMCNXM，Android 17 |
| 屏幕 | 1200×2608，480dpi（density 3.0） |
| 连接 | 无线 adb `10.11.48.149:39795` |
| adb | `C:/Users/ybybabab/AppData/Local/Android/Sdk/platform-tools/adb.exe` |
| 构建环境 | `JAVA_HOME=C:/Users/ybybabab/app/pcl/JAVA21/graalvm`、`ANDROID_HOME=C:/Users/ybybabab/AppData/Local/Android/Sdk`、Python `C:/Python314/python.exe` |

## 4. 绝对禁止：未经允许的永久性修改

以下操作**一律先问用户**，不许自行决定，也不许「先做了再说」：

- 改系统属性（`setprop`、`persist.*`、`ro.*`）；
- 删除或替换系统分区 / 系统应用文件；
- 给系统应用加锁、改权限、改 SELinux 策略；
- 写系统设置项（`settings put`、`aod_show_style`、`MiuiConfigs` 等）——尤其是会影响其它模块的开关；
- 修改第三方模块负责的 hook 点（例如全屏 AOD 模块的 `Utils.isSupportFsAod()` / `MiuiConfigs.SUPPORT_FULL_AOD`）。

原则：**能只在内存里改的，就不落盘；能只影响自己进程的，就不动全局。**

## 5. 版本控制

- 用 git，主分支 `main`。
- 每次改动一个主题一个提交；提交信息写清「做了什么 + 为什么」。
- 大范围回退前先 `git bundle --all` 备份到 `.tmp/`，并记下被回退的 HEAD。
- 不 force push；不改写已推送历史。

## 6. 目录规范

```
app/java/org/aliveclean/   模块源码（一个类一个职责，文件名=类名）
app/res/                   资源（values/strings.xml 内含 xposed_scope）
app/assets/                素材（体积大，尽量不动）
tools/                     构建与部署脚本（build_release.py / deploy.py / 校验脚本）
docs/port/                 交接文档（HANDOVER / PROGRESS / REQUIREMENTS 等）
.tmp/                      临时产物与反编译（不入库）
dist/                      构建产物（不入库）
```

## 7. GitHub 公开审计

仓库若要公开，推送前必须逐项检查：

- 无任何凭据（API key / token / cookie / 私钥 / 签名密钥 / keystore 密码）；
- 无个人隐私（真机序列号、手机号、住址、账号、聊天记录截图）；
- 无版权风险素材（官方主题、付费壁纸、他人美术资源）；
- 无内部路径与内网地址（除非用户明确同意）；
- 提交历史里也不能有——历史是公开的一部分。

## 8. 多机型兼容

- 不写死机型、分辨率、dpi、MIUI 版本号；用 `resources` 与运行时探测取值。
- 同一功能在不同 HyperOS 版本上可能类名/字段不同：一律 `findClassIfExists` + 失败降级，不 crash。
- 新增 hook 必须能「该机型没有就跳过」，不能因为找不到类导致系统进程崩溃（会触发 LSPosed 安全模式）。
- 关键尺寸用 dp，不用 px 常量。

## 9. 产品红线（用户明确要求）

- 应用场景壁纸时**强制走「和锁屏样式一致（全屏 AOD）」渲染路径**，但**不得写该选项**、不得抢第三方全屏 AOD 模块的 hook；
- 不应用时不干预任何 AOD 相关功能；
- 所有对壁纸的处理必须是临时的：模块停用 / 卸载后系统恢复原壁纸，不留痕迹。
