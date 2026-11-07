# 诊断日志（可拔插）

所有诊断输出都走 `org.aliveclean.Trace`，格式固定：

```
[HA][Txx] 描述
```

- 开关：`SharedPreferences("trace").getBoolean("enabled", true)`，默认开；关掉后所有调用空转，**代码不用改**。
- 查看：`adb logcat -s AliveHSAod | grep "\[HA\]"`（或 `grep -E "\[HA\]\[T0"`）。
- 拔除：删掉对应行的 `Trace.log(...)` 即可，不会影响逻辑——每一行都只读状态，不参与判断。

| 编号 | 位置 | 这条能证明什么 |
|---|---|---|
| T01 | `HyperOsAod.java:57` | 该进程里 AodView 的 hook 装上了（哪个类、哪个进程） |
| T02 | `HyperOsAod.java:45` | 一个「临时宿主」被跳过（1.2s 后已不挂在窗口上） |
| T03 | `HyperOsAod.java:219` | attach 调用来源（ctor/doze）、root 身份、当前 live surface 身份、是否已 attached |
| T04 | `HyperOsAod.java:337` | 新建了一个 AodSurface（id）+ 挂到哪个 host |
| T05 | `HyperOsAod.java:543` | 某个 surface 的 SurfaceTexture 可用（**每次重建都会再打一次**） |
| T06 | `HyperOsAod.java:235` | 复用的 surface 被移动到新宿主 |
| T07 | `HyperOsAod.java:71` | dreaming 开始（doze 生命周期真正的入口） |
| T08 | `HyperOsAod.java:83` | dreaming 结束 |
| T09 | `HyperOsAod.java:550` | SurfaceTexture 被销毁（**闪烁的直接证据：与 T05 成对出现**） |
| T10 | `AodOverlay.java:19` | overlay 路径被走到（enter / already / injected） |
| T11 | `AodOverlay.java:44` | overlay 隐藏 |

## 判读规则

- **闪烁 = T09/T05 成对出现超过一次**。正常一次息屏应该只有 1 次 T05、0 次 T09。
- 若出现多个不同的 `AodSurface id`（T04），说明同时存在多个场景实例——这是我们自己的问题。
- 若只有 1 个 id 但 T05 多次，说明是同一个 view 被 MIUI 反复摘下/挂回。
- T03 里 `origin=ctor` 且随后没有 T04，说明构造函数路径被跳过（正常，等 doze 路径）。
