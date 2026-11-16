# 12 主体分割引擎：接入 MIUI 景深（com.xiaomi.aicr）

> 结论先说：Vivo 编辑器的「主体/背景」不再用内置 u2netp + ONNX Runtime（纯 CPU、320×320），
> 改为调用小米系统自带的图像分割（跑在 **Hexagon NPU** 上）。本 APK 不再携带任何 ONNX 组件。
> 2026-11-16 真机验证通过，装机包 `56a0fb5`。

## 1. 旧的实现为什么"不可用"

| 事实 | 位置 |
|---|---|
| 模型 `u2netp.onnx`，ONNX Runtime **CPU** 执行（只配了 `setIntraOpNumThreads(2)`，没有任何 NNAPI/QNN provider） | 旧 `app/java/org/aliveclean/VivoSubjectMask.java`（已删除，见 git 历史） |
| 输入固定缩到 **320×320**，结果再放大回全图 | 同上 |
| 依赖 `vendor/onnxruntime`（classes.jar + 2 个 arm64 `.so`）+ `app/assets/vivo/models/u2netp.onnx` | 旧 `tools/build_release.py` |

320×320 的通用显著性模型 + 放大回填 = 边缘必然糊；人像发丝、半透明边缘尤其差。

## 2. 小米侧的真实链路（反编译 + 真机日志双证据）

### 2.1 客户端协议（com.miui.aod 内置的 AICR 客户端 SDK）

```text
bindService(ComponentName("com.xiaomi.aicr", "com.xiaomi.aicr.access.AiCrCoreService"), BIND_AUTO_CREATE)
  extra "bind_type" = 调用方标识
→ IAiCrCoreService.getPluginBinder(cb, address, "com.xiaomi.aicr.plugin.IImageSegmentService", "5.0", outBundle)
→ outBundle["Status"] == STATUS_DEFINE.OK(0) → IImageSegmentService
→ IImageSegmentService.segment(Bitmap, VisionAttribute) → SegmentResult
```

`IImageSegmentService` 交易码（与服务端 `.aidl` 严格一致）：
`1=initAlgorithmCachePath 2=initPersonModel 3=initSkyModel 4=segment 5=segmentByFd
6=releaseModel 7=releaseAll 8=getAlgorithmVersion 9=segmentMainFunc 10=segmentMainFuncByFd
11=getSegmentMainFuncVersion 12=depthSegmentMainFunc 14=getDepthSegmentMainFuncVersion`

`SegmentResult` = `List<Bitmap> bitmaps` + `List<float[]> rects` + `List<float[]> contours`，
取 `getBitmaps().get(0)`（小米自己的 `com.miui.keyguard.editor.utils.segment.SegmentTask` 就是这么用的）。

### 2.2 服务端（反编译 MIUIAICR.apk）

- `com.xiaomi.aicr.access.AiCrCoreService.getPluginBinder(...)`：**只打日志，不校验调用方权限**，
  只限制：插件已注册、版本 ≥ minVersion、同一 baseAddress 最多 32 个客户端、`address`/`callback` 非空。
- `com.xiaomi.aicr.plugin.SegmentPlugin`（插件实现）：
  - `depthSegmentMainFunc(...)` **直接返回 null**（本 ROM 未实现）→ 景深入口不可用
  - `segment(...)` → `segmentMainFunc(...)` → `segmentV2(...)` → 真算法
  - 输入超过 **5000px 长边会自行压缩**
  - 内部其实是 **TaiYi SDK 的封装**（`segmentAbility.isSupport()` / `segment(...)`）
- 设备上 `com.xiaomi.aicr` 已安装（`/product/priv-app/MIUIAICR`），版本报 `V2`；
  本机 `COG_runningPlugin.xml` 里此前没有 `IImageSegmentService` 记录 = 之前从未被调用过（首次调用即加载成功）。

### 2.3 实际执行位置（logcat 原文）

```text
I/Taiyi-Sdk(aiservice:segment)-AppAbility(...|AbilityFilter(segment_qcom_qnn_8850:5))
I/Taiyi-Plugin(aiservice:segment)-SegmentPlugin: [segment_v3_npu:3] -> process segment: nativeSegmentBitmap
I/Taiyi-Plugin(aiservice:segment)-SegmentPlugin: [segment_v3_npu:3] -> after segment bitmap, cost time:52ms
I/AiCrEngine_SegmentPlugin: segment pluginV2: prepare invoke releaseAll
```

`segment_qcom_qnn_8850` = 骁龙 QNN（Hexagon NPU）后端；`segment_v3_npu` 是 NPU 模型。

## 3. 我们的实现（新增文件，全部纯 Java，无 native）

| 文件 | 作用 |
|---|---|
| `app/java/org/aliveclean/aicr/IAiCrCoreService.java` | 手写 AIDL 代理（1/2/3） |
| `app/java/org/aliveclean/aicr/ICoreServiceCallback.java` | 回调桩（1=onDownload 2=speed） |
| `app/java/org/aliveclean/aicr/IImageSegmentService.java` | 手写 AIDL 代理，只实现用到的交易码 |
| `app/java/org/aliveclean/aicr/SegmentResult.java` / `VisionAttribute.java` | 与小米完全一致的线格式 |
| `app/java/org/aliveclean/aicr/AicrStatus.java` | `STATUS_DEFINE` 取值表 + 可读名字 |
| `app/java/org/aliveclean/AicrSegmentClient.java` | 连接管理（bind → 插件 → 调用），8s 超时，`releaseConnect` + unbind |
| `app/java/org/aliveclean/SubjectEngine.java` | 统一入口：裁剪 → 缩到长边 ≤1440 → 调引擎 → 蒙版还原到原图尺寸 |

调用点：`VivoMaskEditor.generate()`（编辑器里首次点「主体/背景」或点「生成」）。

时序：

```text
VivoMaskEditor.generate()
  └ SubjectEngine.subjectMask(context, photo, box)
      ├ 裁剪选区 + 缩放到长边 1440
      ├ AicrSegmentClient.subjectMask(...)   // 后台线程，Binder 往返
      │   ├ bindService → getPluginBinder（首次会顺带 initAlgorithmCachePath/initPersonModel）
      │   ├ IImageSegmentService.segment(bmp, VisionAttribute(false, maxArea=true))
      │   └ releaseConnect + unbind
      └ composeMask(): 把返回位图（alpha 或灰度两种都兼容）铺回原图尺寸
```

要点：

- **不需要任何 hook**：`AiCrCoreService` 是导出的、不校验调用方权限，普通 app 可直接 bind。
  本机实测从首次调用到出结果全程无 `SecurityException`。
- **不复制任何原生库/模型**：算法、模型、NPU 运行全在 `com.xiaomi.aicr` 进程里。
- 返回值语义两种都兼容：`hasAlpha()` 优先取 alpha，否则取亮度。
- 输入缩放上限 1440：实测量级 200–400ms；插件自身还有 5000px 上限。

## 4. 实测数据（2026-11-16，装机 `56a0fb5`）

| 样本 | 原图尺寸 | 覆盖比 | 端到端耗时 | 观感 |
|---|---|---|---|---|
| 手办/人物特写 | 3840×2160 | 15.9% | 334ms | 轮廓干净，边缘明显优于 u2netp |
| 车站全景（无主体） | 3840×2160 | 52.2% | 637ms | 返回大片区域，属"没有明确主体"的正常表现 |
| 人像近景 | 9248×6944 | 77.1% | 837ms | 人像抓得准，邻近桌面物体有少量溢出 |

内部细分（1440×1080 输入）：`aicr segment took 198ms`，其中 **NPU 原生推理 52ms**。

## 5. 失败与回退

| 情况 | 表现 | 处理 |
|---|---|---|
| `com.xiaomi.aicr` 不存在 / 绑定被拒 | `AicrStatus.SERVICE_NOT_CONNECTED` / `PERMISSION_DENIED` | 日志记录，返回 null → UI 提示"本机未识别出主体，请框选后重试或手动涂抹" |
| 插件未注册 / 版本过低 | `NOT_FOUND` / `NEED_UPGRADE` | 同上 |
| 插件首次使用需下载 | `DOWNLOADING_FIRST_USE`（`onDownload` 回调） | 本次直接放弃，不阻塞 UI（不主动触发下载） |
| 分割返回 null / 空白 | 返回 null | 手动涂抹 |
| 已删除 u2netp 回退 | —— | 需要时从 `.tmp/onnx-backup/` 或 git 历史恢复（`git log -- app/java/org/aliveclean/VivoSubjectMask.java`） |

## 6. 体积变化

| 项 | 变化 |
|---|---|
| 删除 `vendor/onnxruntime`（classes.jar + libonnxruntime.so + libonnxruntime4j_jni.so） | −19.0 MB |
| 删除 `app/assets/vivo/models/u2netp.onnx`（含 LICENSE/source.json） | −4.4 MB |
| APK 总量 | **293.1 MB → 270.4 MB（−22.7 MB）**，包内已无任何 `onnx`/`u2net` 条目 |

构建脚本同步清理：`tools/build_release.py` 不再引用 ort jar、原生库、校验和与许可文件。

## 7. 复现与验证方法

1. 设备上确认链路可用（不需要 root）：
   ```bash
   adb shell dumpsys package com.xiaomi.aicr | grep -A3 AiCrCoreService   # 应有 AICR_ENGINE action
   adb logcat -s AliveClean:* | grep aicr                                  # 应看到 connected / took / segment ok
   ```
2. 期望日志：
   ```text
   I/AliveClean: aicr connected=true
   I/AliveClean: aicr init cache=0 person=0 version=V2
   I/AliveClean: aicr segment took ~200ms for 1440x1080
   I/AliveClean: aicr segment ok mask=1440x1080 rects=1 contours=1
   ```
3. UI 路径：液态/闪卡 → 装饰 → 动效范围 → 主体（首次进入自动生成）。

## 8. 未做的事 / 后续可选

- **TaiYi 直连（`com.xiaomi.aiservice`）**：`AICoreService` 声明了权限 `com.xiaomi.aiservice.permission.AI_ABILITY`，
  普通 app 直连会被拒；如果要走这条（少一次 aicr 转发），才需要 hook 授权。
  当前走 AICR 已足够，未实现。
- **`depthSegmentMainFunc`**：本 ROM 插件未实现（返回 null），若后续 ROM 补上，可优先用它。
- **多主体 / 分主体**：`SegmentResult.rects/contours` 已经拿到（实测 rects=1），
  后续可以做"点选某一个主体"而不用整图蒙版。
- `VisionAttribute.extractCombinedSubject` 目前传 false、`maxAreaAsSingleSubject` 传 true（与 MIUI 锁屏编辑器一致）；
  需要"多主体合并"时可切换。
