# AliveHS 只读代码审查

审查时间：2026-11-02 20:48
审查范围：`app/java/org/aliveclean/`（重点 `HyperOsAod` / `AodOverlay` / `CleanHooks` / `RenderLoop`）、`native/renderer.rs`、`app/assets/shader/photo/aod/lensPhoto/`、`tools/build_release.py`、`tools/deploy.py`、`app/AndroidManifest.xml`。
审查方式：只读静态审查 + 既有真机日志/进度文档对照。未修改任何源码。

说明：`HyperOsAod.java` 当前工作区有一处未提交改动（新增 `liveSurface` 去重，见 `git diff`）。本审查以工作区现状为准，并单独标注该改动的影响。

---

## 结论摘要

1. 现在的闪烁不是单一 bug，而是「AOD 宿主被重建 / 我们主动重建 surface」这一类问题的叠加。最可疑的是 `AodOverlay.show()` 在 `injectIntoAod()` 尚未完成时回退到全屏 overlay，以及第二个 `AodView` 构造时 `attach()` 主动拆掉旧 surface。
2. AOD 通道输出的 alpha 全程是 1：shader 在球外写不透明黑、blend 又强制 `vec4(contentColor, 1.0)`。所以它不可能「只作为最底层壁纸」——它是一整块不透明画面。这也是自建 overlay 会盖住时钟、以及球外区域变成黑底的根本原因。
3. `fragColor.rgb *= 0.55` 是写死值，且发生在 `applyDarkIfNeed` 之后；应改成 uniform，否则没法按设备/场景调，也没法和系统亮度联动。

---

## 一、`HyperOsAod.java`（最高优先级）

### 1.1 同一时刻可能存在两个 AodSurface：`injectIntoAod()` 的返回值语义错误（高）

- 位置：`app/java/org/aliveclean/HyperOsAod.java:191-196`，配合 `:216-224`。
- 症状：`injectIntoAod()` 在 `attach()` 后立刻用 `root.findViewWithTag(TAG) != null` 判断成功。但 `attach()` 在内层 `com.miui.aod.AODView` 还没 inflate 时只 `postDelayed(..., 200)` 重试，然后 `return`；此刻 tag 仍为 null，`injectIntoAod()` 返回 false。
- 根因：把「已安排重试」误判成「注入失败」。调用方 `AodOverlay.show()` 收到 false 后走全屏 overlay 兜底；200ms 后重试又成功注入第二个 `AodSurface`。结果：一个全屏 SurfaceView + 一个 TextureView 同时存在。
- 置信度：高。`onDreamingStarted` 与 `AodView` 构造的先后顺序在真机上不稳定，这条路径每次都可能命中。
- 建议改法：把返回值改成三态 `int`（0=disabled，1=attached，2=pending）或新增 `isAttachPending()`；`AodOverlay.show()` 只在 0 时考虑兜底，2 时直接 return 等待。更彻底：删掉 `AodOverlay` 兜底路径（见 2.2）。

### 1.2 第二个 `AodView` 构造会主动拆掉正在工作的 surface（高）

- 位置：`app/java/org/aliveclean/HyperOsAod.java:34-47`（两个类都 hook 构造函数）、`:117-123`（`aodRoot`/`liveSurface` 都是单个静态引用）、`:202-211`（`attach()` 里发现 `liveSurface` 不是自己就 `removeView`）。
- 症状：从锁屏进息屏时，系统可能先有 keyguard 的 `AodView`，再建 doze 的 `AodView`（或反过来）。第二个实例构造时 `aodRoot` 被覆盖，`attach(root2)` 发现 `root2.findViewWithTag(TAG)==null`，于是把 root1 里的 `liveSurface` remove 掉，再新建一个 `AodSurface`。旧 surface 的 `onDetachedFromWindow` 会 `loop.close()`，新 surface 的 `onSurfaceTextureAvailable` 会 `new RenderLoop` —— 球的动画从第 0 帧重来，屏幕上就是一次闪。
- 根因：「全局只有一个宿主」的假设不成立；`removeView` 是主动制造一次 detach，而不是避免 detach。当前未提交的 `liveSurface` 改动只是把「两个 surface 并存」换成了「先杀旧再建新」，闪烁/重置仍在，甚至更确定。
- 置信度：高（代码路径确定；是否命中取决于真机是否真的构造两个 AodView，已有注释和 `PROGRESS.md` 都指向这一点）。
- 建议改法：不要用「新 root 出现就杀旧」。改为 `Map<Integer, AodSurface>` 以 `System.identityHashCode(root)` 为 key，或只认「当前可见/有窗口焦点」的那个 root；新 root 到来时先只记录，等旧 root `isAttachedToWindow()==false` 再接管。最低限度：`attach()` 里发现已有 live surface 且其 `isAttachedToWindow()` 为 true 时，直接 `return`，不要 remove。

### 1.3 `supervise()` 的 8 次 miss  hysteresis 会造成 2.4 秒空窗，并在重挂时重建 SurfaceTexture（高）

- 位置：`app/java/org/aliveclean/HyperOsAod.java:243-282`，判定在 `:253-260`。
- 症状：MIUI 清空 `AODView` 子视图后，surface 变成 `getParent()==null`。代码要求连续 8 次（300ms 一次，约 2.4s）才重挂。这 2.4s 里球是消失的；重挂时 `TextureView` 重新走 `onSurfaceTextureAvailable` → `new RenderLoop` → 动画重置。用户看到的「闪一下 + 动画重头」正是这个组合。
- 根因：用「延迟重挂」对抗 MIUI 的清理，只能减少重挂次数，不能避免重挂；而每次重挂都等于重建渲染器。
- 置信度：高。`PROGRESS.md` 已记录「3 次息屏实测 2 次重挂」。
- 建议改法：把「视图挂载」和「渲染器生命周期」解耦。`AodSurface` 持有 `SurfaceTexture` 与 `RenderLoop`；`onSurfaceTextureDestroyed` 返回 `false` 并由自己保存/释放 texture（或在 `onSurfaceTextureAvailable` 里判断 `texture == lastTexture` 时复用 loop，不 `new RenderLoop`）。`supervise` 只负责把 view 挂回去，不要让它触发 `new RenderLoop`。同时把 8 次改成 1 次（或监听 `View.OnAttachStateChangeListener`），因为空窗本身就是可见缺陷。

### 1.4 `attach()` 的「已有 overlay 就不注入」只写在注释里，代码没有实现（高）

- 位置：`app/java/org/aliveclean/HyperOsAod.java:198-204`。注释说「When our own window is up, injecting into MIUI's tree would draw a second sphere」，但下一行直接 `Context context = root.getContext();`，没有任何 `overlayView != null` 的判断。
- 症状：只要 `showOverlay()` 被调用过（当前是死代码，但保留了 public/static 入口），注入路径仍会再建一个 surface。
- 根因：注释与实现脱节，属于「以为已经防住」的隐性回归。
- 置信度：高（代码事实）。
- 建议改法：在 `:203` 前加 `if (overlayView != null && overlayView.get() != null) return;`；或者直接删除 `showOverlay`/`hideOverlay` 死代码，避免以后误用。

### 1.5 `refreshScene()` 已无调用者，但保留着和 1.2 同类的拆建逻辑（中）

- 位置：`app/java/org/aliveclean/HyperOsAod.java:180-189`；调用点已在 commit `38ffb3e` 删除。
- 症状：如果以后有人重新调用，它会 `removeView(old)` + `attach(root)`，必然重建 SurfaceTexture 并重置动画；而且 remove 后 `onDetachedFromWindow` 会 `loop.close()`，新 surface 再 `new RenderLoop`。
- 根因：刷新场景被实现成「换 view」，而不是「让 RenderLoop 重新读 SharedPreferences」。
- 置信度：高（死代码风险）。
- 建议改法：删除该方法；若确实需要跨进程刷新，给 `RenderLoop` 增加 `reload()`（已有）并通过 `SceneProvider`/静态桥通知现有 loop，不要动 view。

### 1.6 `AodSurface` 生命周期：`onSurfaceTextureAvailable` 不关闭旧 loop（中）

- 位置：`app/java/org/aliveclean/HyperOsAod.java:429-436`。
- 症状：如果 `onSurfaceTextureAvailable` 在未收到 `onSurfaceTextureDestroyed` 的情况下再次触发（部分 ROM 在 detach/attach 竞态下会这样），旧的 `RenderLoop` 及其 `HandlerThread`、EGL 资源不会被关闭，直接泄漏；同时两个 loop 可能同时往一个 texture 画。
- 根因：没有「先清理再创建」的守卫。
- 置信度：中（取决于 ROM 回调顺序）。
- 建议改法：方法开头 `if (loop != null) { loop.close(); loop = null; }`，并 `if (surface != null) surface.release()`；再创建。更好的做法见 1.3 的 texture 复用。

### 1.7 `onSurfaceTextureSizeChanged` 触发完整 `attach()` → `release()` + `init()`（中）

- 位置：`app/java/org/aliveclean/HyperOsAod.java:438-440` → `RenderLoop.java:84`。
- 症状：尺寸变化（旋转、分辨率切换、AOD 窗口从 1200x2608 变为其他尺寸）会走 `release()`：销毁 EGLSurface/EGLContext、销毁 scene、重新解码壁纸、重新编译 shader。表现为黑帧 + 动画重置。
- 根因：`attach()` 没有区分「首次附加」和「resize」。
- 置信度：中。
- 建议改法：`RenderLoop` 增加 `resize(int w,int h)`，只重建 render targets / viewport，不重建 EGLContext 和 scene；`attach()` 只在 `display==EGL_NO_DISPLAY` 时做完整 init。

### 1.8 `playExit()` 在 UI 线程读 GPU 帧，且用 `TYPE_APPLICATION_OVERLAY` 从 SystemUI 添加窗口（中，当前死代码）

- 位置：`app/java/org/aliveclean/HyperOsAod.java:82-115`。
- 症状：`surface.getBitmap(width/2, height/2)` 是 `TextureView.getBitmap`，会阻塞 UI 线程做 GPU→CPU 回读；AOD 退出时主线程卡顿会放大「退出闪」。`TYPE_APPLICATION_OVERLAY` 需要 `SYSTEM_ALERT_WINDOW`，SystemUI 通常有，但后台启动窗口仍可能被拒；失败路径只打日志。
- 根因：退出动画用截图 + 独立窗口，和 AOD 窗口的生命周期竞争。
- 置信度：中。
- 建议改法：短期删除死代码；要做退出动画时，在 `onSurfaceTextureDestroyed` 之前用 `PixelCopy` 异步取帧，并复用 AOD 窗口内的一个 `ImageView`，不要新开 overlay。

### 1.9 `aodRoot` 是 WeakReference，但没有失效清理（低）

- 位置：`app/java/org/aliveclean/HyperOsAod.java:118`、`:173-176`、`:181-182`、`:192-193`。
- 症状：root 被回收后 `hasInjectedView()` 永远 false，`injectIntoAod()` 永远 false，`AodOverlay.show()` 每次都走兜底 overlay；旧 overlay 的 `view` 非 null 时又直接 return，形成「什么都不显示」。
- 根因：静态弱引用没有和 View 的 attach 状态绑定。
- 置信度：低-中。
- 建议改法：改用 `View.OnAttachStateChangeListener` 在 detach 时清空 `aodRoot`/`liveSurface`；或持有强引用但只在 `onDreamingStopped` 释放。

---

## 二、`AodOverlay.java`

### 2.1 `ownsWallpaper()` 与 `HyperOsAod` 的启用条件不一致（高）

- 位置：`app/java/org/aliveclean/AodOverlay.java:20`、`:51-59`；对照 `HyperOsAod.java:370-382`。
- 症状：`AodOverlay.show()` 要求 `WallpaperManager.getWallpaperInfo()` 或 `FLAG_LOCK` 的包名是 `org.aliveclean` 才继续；而 `HyperOsAod.attach()` 只要求 `aod != -1`。两条路径的开关语义不同：
  - 用户用系统壁纸/第三方壁纸时，注入路径仍然工作（读 `AodWallpaperProvider`，provider 读的是 `SceneOptions.APPLIED.photo`），但 overlay 路径永远跳过；
  - 用户把 AliveHS 壁纸取消后，`aod` 仍可能不是 -1，注入路径继续用旧图，overlay 路径不再兜底。
- 根因：把「壁纸组件归属」当成「AOD 场景开关」。需求 A1 明确说输入是「用户设置的壁纸本体」，并不要求当前 live wallpaper 必须是 `org.aliveclean`；A2 也只要求压暗。
- 置信度：高。
- 建议改法：删掉 `AodOverlay.ownsWallpaper()` 这道门，统一用 `SceneOptions.APPLIED.aod != -1` + `AodWallpaperProvider` 可读性来判断；`HyperOsAod.ownsWallpaper()` 同理（当前也是死代码，没有调用者）。

### 2.2 `injectIntoAod()` 失败后的全屏 overlay 会盖住时钟（高）

- 位置：`app/java/org/aliveclean/AodOverlay.java:21-38`、`:61-79`。
- 症状：`AodView extends SurfaceView`，`LayoutParams` 是 `MATCH_PARENT × MATCH_PARENT`，`PixelFormat.TRANSLUCENT`，但渲染器输出 alpha=1（见 5.1），所以它是一块不透明全屏层，位于 doze 窗口之上，系统时钟在它下面 → 时钟被盖住。commit `2c04c0e` 的提交说明已经承认这一点（「overlay hides the clock」）。
- 根因：overlay 兜底没有任何「只画球、其余透明」的能力；`setAlpha(0.9f)` 只是把整块（包括黑底）变半透明，仍然盖时钟。
- 置信度：高。
- 建议改法：在 5.1 的 alpha 改造完成前，直接删除 `AodOverlay.show()` 的兜底分支（只保留 `injectIntoAod()` 尝试，失败就放弃本帧）。如果必须保留，至少把 `LayoutParams.height` 限制在球所在的上半区（`heightPixels * 0.46f`），并 `setZOrderMediaOverlay(false)`，但这仍会挡住上半区时钟，不推荐。

### 2.3 `hide()` 的第一个条件永远走不到（低）

- 位置：`app/java/org/aliveclean/AodOverlay.java:42-44`。
- 症状：`if (view == null && !hasInjectedView()) return;` 下一行 `if (view == null) return;`，所以当 `view == null` 时无论 `hasInjectedView()` 是什么都返回。注释「AOD fades out ... keep the scene until screen on」的意图没有实现。
- 根因：两行守卫顺序写反。
- 置信度：高（代码事实），影响低（注入的 view 由 AOD 树自己销毁）。
- 建议改法：删掉第一行，或改成 `if (view == null) { if (!hasInjectedView()) return; ... }` 并在注入路径下显式清理注入 view。

### 2.4 静态 `loop` 被 SurfaceView 回调跨实例复用（中）

- 位置：`app/java/org/aliveclean/AodOverlay.java:16`、`:68-79`。
- 症状：`loop` 是类静态字段，`surfaceDestroyed` 里 `loop.close(); loop=null;` 不校验 holder 是否属于当前 view。若旧 surface 的 destroy 回调晚于新 surface 的 create（窗口重建竞态），会把新 loop 关掉，表现为黑屏。
- 根因：`RenderLoop` 应该 per-view 持有，不应静态。
- 置信度：中。
- 建议改法：把 `loop` 移进 `AodView` 实例字段；`surfaceDestroyed` 只关闭自己的 loop。

---

## 三、`CleanHooks.java`

### 3.1 `com.miui.aod` 进程被识别但从未 hook（高，至少是明确的遗漏）

- 位置：`app/java/org/aliveclean/CleanHooks.java:16` 定义 `boolean miuiAod`，之后从未使用；`:27` 在非 `android`/非 `systemui` 时直接 return。
- 症状：设备上确实存在独立的 `com.miui.aod` 进程（用户给出的已知事实）。如果该进程在部分场景（例如纯 AOD、或 HyperOS 的某些版本）承载 AOD 视图，模块在里面完全不会加载任何 hook，表现为「有时有效、有时完全没效果」。
- 根因：只按「当前这台机器 AOD 在 SystemUI」的经验做分发，没有对 `com.miui.aod` 做能力探测。
- 置信度：高（代码事实）；对当前 HyperOS 4 的实际影响中（因为已验证 AOD 宿主是 SystemUI）。
- 建议改法：`if (miuiAod) step("miui aod", () -> HyperOsAod.install(p.classLoader));`，并让 `HyperOsAod.install()` 里的类名探测决定是否真的有 `AODView`；没有就只打日志。不要无条件装 `installDoze`（`MiuiDozeService` 可能不存在）。

### 3.2 `miui.systemui.plugin` 完全没有出现在分发里（中）

- 位置：`app/java/org/aliveclean/CleanHooks.java:13-27`。
- 症状：`PROGRESS.md` 明确写「HyperOS 锁屏时钟编辑器在 `miui.systemui.plugin`，未移植」。当前 `CleanHooks` 不识别该包，相关 `NativeClock*` 代码在 HyperOS 上不会加载。
- 根因：移植范围只做了 AOD 主路径。
- 置信度：高（代码事实），属于已知能力缺口。
- 建议改法：如果近期不做时钟编辑器，至少在 `CleanHooks` 里加一行注释/日志「miui.systemui.plugin: not ported」，避免以后误以为已覆盖；要做时新增 `boolean miuiPlugin = "miui.systemui.plugin".equals(p.packageName)` 并只装 `NativeHyperOs*` 相关 hook。

### 3.3 `ambientHooked` 的进程内静态判断（低）

- 位置：`app/java/org/aliveclean/CleanHooks.java:11`、`:28-37`。
- 症状：`systemUi` 和 `android` 是两个进程，各自有独立的静态变量，所以不会互相干扰；但在同一进程里如果 `handleLoadPackage` 被调用多次（LSPosed 某些版本会），第二次会跳过 `supportsAmbientMode` hook——这通常是期望行为。不是 bug，只是提醒。
- 置信度：低。
- 建议改法：无需改；如果要更严谨，用 `AtomicBoolean` 并记录已 hook 的 ClassLoader。

### 3.4 `PlatformProfile.hasVendorClass` 在 HyperOS 上可能误判（低）

- 位置：`app/java/org/aliveclean/CleanHooks.java:23-24`、`:51-53`。
- 症状：如果 HyperOS 的 SystemUI 里恰好保留了某个 ColorOS 类名（或移植包里有同名类），会额外装 `ColorOsBridge`/字体 hook，产生无用 hook 甚至异常。
- 根因：只做类存在性判断，没有版本/包名双重校验。
- 置信度：低。
- 建议改法：`hasVendorClass` 里再检查 `com.oplus.wallpapers` 是否安装，或改用 `Build.VERSION.INCREMENTAL` 包含 `ColorOS`。

---

## 四、`RenderLoop.java`

### 4.1 `attach()` 无条件 `release()`，任何重复 attach 都重建整个渲染器（高）

- 位置：`app/java/org/aliveclean/RenderLoop.java:84`。
- 症状：`attach()` 第一行就是 `release()`；`release()` 会 `eglDestroySurface`/`eglDestroyContext`/`eglTerminate`、销毁 scene、删除 texture。`onSurfaceTextureSizeChanged`（`HyperOsAod.java:439`）和 `AodOverlay` 的 `surfaceChanged`（`AodOverlay.java:75`）都会调用它，所以一次 resize 就是一次完整重建 + 壁纸重新解码 + 动画重置。
- 根因：没有「同一 surface 重复 attach」的快路径。
- 置信度：高。
- 建议改法：`if (surface == attachedSurface && w == width && h == height && window != EGL_NO_SURFACE) return;`；resize 只重建 targets/viewport（见 1.7）。

### 4.2 `loadPhoto()` 的 provider 读取有 offset 问题（中）

- 位置：`app/java/org/aliveclean/RenderLoop.java:268-280`。
- 症状：`openAssetFileDescriptor(..., "r")` 返回的 `AssetFileDescriptor` 可能带有非 0 的 `getStartOffset()`；`BitmapFactory.decodeFileDescriptor(fd)` 从 fd 当前位置读，不保证从 offset 开始。对普通文件通常 offset=0，但对压缩/嵌套 provider 可能读到错误字节，表现为「偶发解码失败 → 回退到内置渐变图」。
- 根因：没有用 `descriptor.createInputStream()` 或 `decodeFileDescriptor(fd, startOffset, length)`。
- 置信度：中。
- 建议改法：优先 `try (InputStream in = descriptor.createInputStream()) { BitmapFactory.decodeStream(in); }`；或 `decodeFileDescriptor(fd, descriptor.getStartOffset(), descriptor.getLength())`。同时加日志区分「provider 打不开」和「解码失败」。

### 4.3 `EGL_ALPHA_SIZE=8` 不等于输出透明（高）

- 位置：`app/java/org/aliveclean/RenderLoop.java:258`；对照 `lens_photo_frag.glsl:53/80` 与 `lens_blend_frag.glsl:70`。
- 症状：`attrs` 里 `EGL_ALPHA_SIZE,8` 只保证选到的 config 有 8 位 alpha 通道；实际 alpha 由 fragment shader 写出的值决定。当前 AOD shader 在球外 `return vec4(0,0,0,1)`，球边缘 `mix(..., vec4(0,0,0,1), ...)`；blend 最后 `applyDarkIfNeed(vec4(contentColor,1.0),...)` 仍是 alpha=1。所以整块 buffer 不透明。
- 根因：没有独立的 alpha/clear pass；把「config 有 alpha」误当成「framebuffer 输出 alpha」。
- 置信度：高。
- 建议改法：在 `lens_photo_frag` 里球外输出 `vec4(0.0)`，球内输出 `vec4(color, 1.0)`；blend shader 用 `o_fragColor = vec4(contentColor, aodAlpha)`，其中 `aodAlpha` 由 AOD pass 的 alpha 纹理或 `smoothstep` 计算；同时在 `RenderLoop` 的 `draw` 前 `glClearColor(0,0,0,0); glClear(GL_COLOR_BUFFER_BIT)`。`TextureView.setOpaque(false)` 已经设了，但还需要窗口 `PixelFormat.TRANSLUCENT`（注入路径是宿主窗口，无法改；所以只能靠 shader alpha + 宿主窗口本身的透明背景）。

### 4.4 `mode()` 的切换竞态（中）

- 位置：`app/java/org/aliveclean/RenderLoop.java:126-134`。
- 症状：`mode(int next)` 只是 `handler.post`，字段 `mode` 在 post 执行时才更新。`AodSurface.onSurfaceTextureAvailable` 里先 `new RenderLoop`、`loop.mode(0)`、`loop.attach(...)`；`mode(0)` 先入队，`attach` 后入队，所以 init 时 `mode` 已是 0，当前顺序是对的。但 `AodOverlay.AodView.surfaceCreated` 同样是 `new RenderLoop`、`mode(0)`、`attach`，也对的。真正的问题是 `attach` 里 `createScene(options)` 读 `mode`，如果调用方改用 `loop.attach` 先于 `mode`，就会用默认 `mode=1` 建 scene，之后 `mode(0)` 只做 transition，不会重建 scene，AOD 会用错通道。
- 根因：`mode` 是可变字段且初始化顺序靠调用方保证，没有在 `init` 参数里显式传入。
- 置信度：中（当前调用顺序正确，属于脆弱契约）。
- 建议改法：`RenderLoop` 构造时接收初始 `mode`，或 `attach(Surface,w,h,int initialMode)`；`mode()` 在 `handler` 里先更新字段再 `createScene`（如果 scene 未建）。

### 4.5 `close()` 之后仍可能执行排队的 `attach`/`mode`（中）

- 位置：`app/java/org/aliveclean/RenderLoop.java:166`、`:84`、`:126`。
- 症状：`close()` 先 `closed=true`，再 `handler.post(release + quitSafely)`。如果之前已经 post 了 `attach` 或 `mode`，它们会在 `release` 之前执行；`attach` 不检查 `closed`，会重新 `init` 出 EGL 资源，然后 `quitSafely` 退出线程，资源泄漏（进程内）。
- 根因：`attach`/`mode` 没有 `if (closed) return;`。
- 置信度：中。
- 建议改法：`attach`/`mode`/`touch`/`reload` 开头统一 `if (closed) return;`；`close()` 里先 `handler.removeCallbacksAndMessages(null)` 再 post release（注意不要移除自己）。

### 4.6 `readWallpaperPalette()` / `aodPalette()` 是死代码（低）

- 位置：`app/java/org/aliveclean/RenderLoop.java:47-64`。
- 症状：`aodPalette(int,int,int)` 没有调用者，`readWallpaperPalette()` 只在 `createScene` 的 `mode==0` 时调用，但读到的 palette 只存进 `aodPalette`，后续没有任何 shader 使用它（AOD 用的是 `photo` 纹理，不是 palette）。
- 根因：从「取主色」方案遗留。
- 置信度：高。
- 建议改法：删除，或明确作为调试信息保留；不要让它误导后续维护者以为 AOD 还在用主色。

### 4.7 `loadPhoto()` 的共享路径优先级（中）

- 位置：`app/java/org/aliveclean/RenderLoop.java:282-286`。
- 症状：优先读 `/data/data/com.android.systemui/files/aod-wallpaper.png`，其次 external，再 internal。这个路径是 SystemUI 私有目录，谁写入、何时写入没有在本仓库里定义（grep 不到写入者），如果存在旧文件，会永远压过用户刚选的新图。且与需求 B2「不要把壁纸图乱塞到各种共享路径」相悖。
- 根因：历史兜底路径没有清理。
- 置信度：中。
- 建议改法：删除 `shared` 分支，只保留 provider → external → internal；如果确实需要 SystemUI 共享，改成 provider 的第二个 URI，并记录写入时间戳。

---

## 五、`native/renderer.rs` 与 AOD shader

### 5.1 AOD 通道没有独立的透明/背景 pass，整块不透明（高）

- 位置：`native/renderer.rs:116-159`（`aod_pass`）、`:196-200`（最终 blend）、`native/gl.rs:56`（`Target::bind` 无条件 `glClearColor(0,0,0,1)`）、`app/assets/shader/photo/aod/lensPhoto/lens_photo_frag.glsl:52-55`、`:80`、`app/assets/shader/photo/blend/lens/lens_blend_frag.glsl:70`。
- 症状：`Target::bind()` 每次把 FBO clear 成 alpha=1 的黑；`lens_photo_frag` 球外直接返回 `vec4(0,0,0,1)`；blend 再 `vec4(contentColor, 1.0)`。最终写入默认 framebuffer 的每个像素 alpha 都是 1。所以：
  - 球外不是「透出系统 AOD 壁纸」，而是纯黑；
  - 自建 overlay 时整块盖住时钟（2.2）；
  - 注入到 `AODView` 时，球外把系统壁纸也盖成黑色，用户看到的不是「壁纸后处理」，而是「黑底 + 球」。
- 根因：把 AOD 当成全屏效果图渲染，而不是「只在球区域输出、其余透明」的图层。
- 置信度：高。
- 建议改法：
  1. `Target::bind` 增加参数 `clearAlpha`，AOD target 用 0；
  2. `lens_photo_frag` 球外 `fragColor = vec4(0.0)`，球边缘 `mix(darkColor, vec4(0.0), smoothstep(...))`；
  3. `lens_blend_frag` 输出 `vec4(contentColor, texture(u_aod_effect, v_texCoord0).a)`，不要强制 1.0；
  4. `RenderLoop.render` 在 `NativeScene.render` 前 `glClearColor(0,0,0,0); glClear(GL_COLOR_BUFFER_BIT)`；
  5. 确认宿主窗口/`TextureView` 的 `PixelFormat` 为 TRANSLUCENT（注入路径无法改宿主，只能靠上层 View 的 alpha 合成；如果宿主窗口本身 opaque，则需要改成 overlay 或给 `AODView` 设置透明背景）。

### 5.2 `fragColor.rgb *= 0.55` 应该走 uniform（中）

- 位置：`app/assets/shader/photo/aod/lensPhoto/lens_photo_frag.glsl:81-82`。
- 症状：0.55 写死在 shader 末尾，且发生在 `applyDarkIfNeed` 之后；不同设备/亮度/壁纸下无法调，也不能跟随系统 AOD 亮度。用户需求 A2 说「甚至可以更暗」，说明这个值需要可调。
- 根因：快速试验留下的常量。
- 建议改法：加 `uniform float u_aod_dim;`，在 `renderer.rs:151-158` 用 `p.f("u_aod_dim", dim)` 传入；Java 侧从 `SceneOptions` 读 `aod_dim`（默认 0.55），并提供设置项。保留 0.55 作为默认值。

### 5.3 `aod < 0` 时 `aod_pass` 不 clear，可能采样到上一帧（中）

- 位置：`native/renderer.rs:116-117`。
- 症状：`if self.aod<0 { self.targets[2].bind(); return; }` —— `bind()` 会 clear 成黑（alpha=1），但 `draw()` 只在 `aod_visible` 时调用 `aod_pass`；如果 `ratio[0]>0` 且 `aod<0`（过渡帧），blend 会采样到刚 clear 的黑，正常；但如果 `aod_pass` 没被调用，target[2] 保留上一帧内容。结合 `u_ratio.x>0` 的分支，可能出现旧球残影。
- 根因：没有在每帧开始时统一清 AOD target。
- 置信度：中。
- 建议改法：`draw()` 开头对 `targets[2]` 做一次 `bind()` + `clear`，或确保 `aod_pass` 总会调用。

### 5.4 `u_dark_strength` 在 lens 路径被写死 0（低）

- 位置：`native/renderer.rs:153`。
- 症状：`p.f("u_dark_strength", 0.)`，所以 `applyDarkIfNeed` 在 AOD lens pass 里是 no-op；压暗完全靠 5.2 的 0.55。两套压暗机制并存但只有一套生效，容易误导。
- 根因：参数没有从 frame packet 传入。
- 建议改法：用 `frame.0[57]`（blend 用的 `u_dark_strength`）或单独的 `u_aod_dark` 传入；与 5.2 合并成一个 uniform。

---

## 六、构建脚本 `tools/build_release.py`

### 6.1 构建失败不会静默继续，但「旧包」会被当成新包部署（高）

- 位置：`tools/build_release.py:121-122`（`run` 使用 `check=True`）、`:151-154`（`javac`）、`:256-259`（主 javac）、`:261-266`（d8）、`:300-321`（报告只在最后写）；`tools/deploy.py:91-105`。
- 症状：`javac`/`d8` 失败时 `subprocess.run(check=True)` 会抛异常，脚本退出，不会继续打包——这一点当前版本是对的。但 `dist/AliveHS-<version>-arm64-v8a.apk` 是固定文件名，失败后旧文件仍然存在；`deploy.py` 只判断「APK 存在且 stamp 匹配」就跳过构建/安装（`deploy.py:91-100`），于是「build 失败 → deploy 说 same APK already deployed / pm install Success」——装的是旧包。`PROGRESS.md` 记录的「构建失败时 install 仍会 Success」正是这个链路。
- 根因：构建产物没有「本次构建成功」的原子标记；deploy 用文件存在/哈希代替「构建成功」。
- 置信度：高。
- 建议改法：
  1. `build_release.py` 先写 `build/release/.building`，成功后写 `dist/build-report.json` 并删除 `.building`；失败时删除目标 APK 或写 `.failed`；
  2. `deploy.py` 只在 `build-report.json` 的 `sha256` 与 APK 实际 sha256 一致、且 mtime 晚于源码最新 mtime 时才跳过构建；
  3. `deploy.py` 解析 `pm install` 输出，出现 `Failure` 必须非 0 退出。

### 6.2 APK 320MB 的构成与拆分方案（中）

- 位置：`tools/build_release.py:233-235`（`aapt2 link -A app/assets` 把所有 assets 打进 base）、`:268-278`（zip 重写）、`:279`（zipalign）。
- 构成（来自 `REQUIREMENTS.md` C4）：assets 244MB（native-clock 88MB、wallpapers 77MB、xiaomi 28MB、flyme-video 26MB、vivo 25MB），代码 + shader + lib 约 77MB。
- 症状：每次构建都要 aapt2 处理 244MB、Python `zipfile` 以 `compresslevel=9` 重新压缩 244MB，单次 45–60s；每次安装传 320MB。
- 根因：`-A app/assets` 是单包，aapt2 的 `--split` 只能按资源**配置**（语言/密度/ABI）拆，不能按 assets 目录拆；`adb install --incremental` 在本机不可用。
- 置信度：高（机制），拆分收益中（取决于素材变更频率）。
- 建议改法（按风险从低到高）：
  1. **开发包瘦身**：新增 `--lite` 参数，aapt2 link 时用 `-A build/assets-lite`（只放 shader + 当前主题素材），产物约 80MB，用于日常迭代；发布包仍全量。这是最低风险、立刻可做。
  2. **素材外置**：把 native-clock/wallpapers/xiaomi/flyme-video/vivo 从 `app/assets` 移到 `media/`，构建时不进 APK；首次启动用 `adb push` 或应用内拷贝到 `getFilesDir()/media`。需要把约 70 处 `getAssets().open(...)` 收敛到一个 `AssetSource.open(path)`，先查 assets、再查 files。工作量中等，但能彻底解决 244MB。
  3. **独立媒体包**：`org.aliveclean.media` 只含 assets，主包通过 `createPackageContext("org.aliveclean.media", 0).getAssets()` 读取；两包都装一次，之后只更新主包。需要处理签名/版本/卸载联动，风险最高。
  4. **构建加速**：`write_entry` 对 `.png/.jpg/.webp/.otf/.ttf/.webm` 使用 `ZIP_STORED`（这些已压缩，deflate 收益很小），可显著缩短 zip 阶段；代价是 APK 略大。配合 1 使用。
- 不建议：`aapt2 --split` 按 assets 拆（不支持）；`adb install --incremental`（本机已证实 Bad file descriptor）。

### 6.3 `aapt2 link` 输出被 `zipfile` 重写，`assets/video/` 被静默丢弃（低）

- 位置：`tools/build_release.py:268-271`。
- 症状：`if not entry.filename.startswith("assets/video/")` 会静默丢掉 `assets/video/` 下所有文件；如果以后有功能依赖该目录，构建不会报错，运行时才 404。
- 根因：历史瘦身逻辑没有日志。
- 置信度：高。
- 建议改法：删除该过滤，或在过滤时打印被丢弃的文件数与总字节；如果确实要排除，改成显式的 `EXCLUDE_ASSETS` 列表并 fail-fast 检查关键文件。

---

## 七、资源 / 内存 / 线程

| 问题 | 位置 | 症状 | 置信度 | 建议 |
|---|---|---|---|---|
| `RenderLoop` 的 `HandlerThread` 可能泄漏 | `RenderLoop.java:17-18`、`:82-83` | 构造即 `thread.start()`；只有 `close()` 会 `quitSafely()`。如果 `AodSurface` 创建后从未 attach（或被 GC 前没有 detach 回调），线程常驻。 | 中 | 把 `thread.start()` 延迟到第一次 `attach()`；`close()` 之外增加 `finalize`/`Cleaner` 兜底（不推荐依赖），或在 `AodSurface` 的 `onDetachedFromWindow` 确保调用。 |
| `AodSurface` 的 `surface` 可能重复 release | `HyperOsAod.java:442-454` | `onSurfaceTextureDestroyed` 和 `onDetachedFromWindow` 都会 `surface.release()`；`Surface.release()` 幂等，但 `loop.close()` 第二次会走 `closed` 守卫，安全。 | 低 | 加 `if (surface != null)` 已够；可把清理收敛到一个 `releaseOnce()`。 |
| `AodOverlay.view` 静态强引用 | `AodOverlay.java:15` | 如果 `hide()` 没被调用（doze 异常退出），View 持有 Activity/Service Context，泄漏整个窗口。 | 中 | 改成 `WeakReference` 或在 `onDreamingStopped` 之外增加 `onDestroy` 兜底；`show()` 里先检查 `view.isAttachedToWindow()`。 |
| `supervise` 的 Runnable 持有 root/surface 30 秒 | `HyperOsAod.java:243-281` | 100 次 × 300ms，期间强引用 ViewGroup 和 Surface；AOD 退出后仍会跑完，延迟回收。 | 低 | `tries` 降到 20（6 秒），或在 `onDreamingStopped` 里 `handler.removeCallbacks`。 |
| `dumpTree` 在 doze 后 3 秒仍执行 | `HyperOsAod.java:66-68` | 调试代码常驻，每次息屏遍历整棵树并打日志；线上包会拖慢主线程、刷日志。 | 中 | 用 `Diagnostics.TRACE` 包裹，或删掉。 |
| `AodSurface` 的 `TextureView` 在 AOD 窗口里是软件层？ | `HyperOsAod.java:400-410` | `setOpaque(false)` 会强制 TextureView 走 `LAYER_TYPE_SOFTWARE`? 实际上 `setOpaque(false)` 影响的是窗口 opaque 标记，不直接切软件层；但在某些 ROM 上会禁用硬件层，导致每帧 GPU→CPU 合成，耗电/掉帧。 | 低-中 | 用 `setLayerType(LAYER_TYPE_HARDWARE, null)` 显式指定；确认 `isOpaque()` 与窗口透明度的关系。 |

---

## 八、最可能导致「锁屏流入息屏闪烁」的 3 个候选原因

> 排序依据：代码路径确定性 × 与「从锁屏进入」这一特定场景的相关性 × 既有日志证据。

### 候选 1：第二个 `AodView` 构造 → `attach()` 主动拆旧 surface → 新 SurfaceTexture/RenderLoop（可能性最高）

- 对应问题：1.2。
- 机制：锁屏界面已经有 keyguard 的 `AodView`；进入 doze 时系统再建一个 doze 的 `AodView`。第二个构造函数触发 `attach(root2)`，发现 root2 没有 tag，于是 `removeView(liveSurface)`，旧 `onDetachedFromWindow` 关闭 RenderLoop；新 `AodSurface` attach 后 `onSurfaceTextureAvailable` 新建 RenderLoop，动画从 0 开始。屏幕上看就是「闪一下 + 球重来」。
- 为什么从桌面直接息屏正常：桌面路径可能只构造一个 `AodView`（或构造顺序不同），不会触发「旧 surface 被新 root 顶掉」。
- 验证方法（adb/logcat）：
  1. 在 `attach()` 开头临时加日志：`Log.i(TAG, "attach root=" + System.identityHashCode(root) + " live=" + (liveSurface==null?0:System.identityHashCode(liveSurface.get())) + " attached=" + root.isAttachedToWindow());`
  2. 在 `AodSurface.onSurfaceTextureAvailable` 加 `Log.i(TAG, "texture available #" + (++textureSeq) + " " + System.identityHashCode(this));`
  3. 复现：`adb logcat -c; adb shell input keyevent 26; sleep 12; adb logcat -d -v time -s AliveHSAod:V`。
  4. 判据：日志中出现两次 `attach root=` 且 identityHashCode 不同，并且中间出现 `dropped a second AOD host before attaching`，紧接着 `texture available #2`——即可确认候选 1。
  5. 辅助：`adb shell dumpsys window windows | grep -iE "AodView|AliveHS"` 看是否有两个 AOD 窗口；`adb shell dumpsys SurfaceFlinger --list | grep -i aod`。

### 候选 2：`AodOverlay.show()` 在 `injectIntoAod()` 返回 false 时加了全屏 overlay，随后注入又成功 → 两层画面交替（可能性第二）

- 对应问题：1.1 + 2.2。
- 机制：`onDreamingStarted` 时 `aodRoot` 可能为 null（AodView 还没构造），或内层 `com.miui.aod.AODView` 还没 inflate（`attach` 只 post 200ms 重试）。`injectIntoAod()` 立即返回 false → `AodOverlay.show()` 创建一个全屏 `SurfaceView` overlay；200ms 后重试成功，又在 `AODView` 里建一个 `TextureView`。全屏 overlay 不透明、盖住时钟；随后 overlay 的 surface 生命周期/注入 view 的 attach 交替，产生闪烁。
- 为什么从锁屏进入更明显：锁屏路径的 `AodView` 可能已经存在，`injectIntoAod()` 直接 true，不会走 overlay；桌面路径反而可能先 overlay 后注入——但用户观察到的是「锁屏进入闪」，也可能是因为锁屏路径的 `AodView` 正在重建，`aodRoot` 指向的旧实例已 detach，`findViewWithTag` 找不到，返回 false。
- 验证方法：
  1. `adb logcat -v time -s AliveHSAod:V | grep -E "overlay added|injected into the AOD view|ambient surface attached|inner AODView not up yet"`。
  2. 判据：同一轮 doze 里同时出现 `overlay added` 和 `ambient surface attached` → 候选 2 成立。
  3. 截屏/录屏：`adb shell screenrecord --time-limit 10 /sdcard/aod.mp4`，看时钟是否被整块黑盖住；`adb shell dumpsys window windows | grep -i AliveHSAod` 看是否存在 `TYPE_APPLICATION_OVERLAY` 窗口。
  4. 临时把 `AodOverlay.show(svc)` 注释掉（只改一行）复测；如果闪烁消失，确认候选 2。

### 候选 3：`supervise()` 的 8 次 miss 窗口 + 重挂重建（可能性第三，但最容易被日志直接证实）

- 对应问题：1.3。
- 机制：MIUI 清空 `AODView` 子视图后，surface 在 2.4 秒内没有 parent（球消失），第 8 次 miss 后 `addView` 回去；`TextureView` 重新走 `onSurfaceTextureAvailable`，`new RenderLoop`，动画重置。这个「消失 2.4s → 出现并从头播」在视觉上就是一次闪。
- 为什么从锁屏进入更明显：锁屏→AOD 的窗口重建更频繁，`AODView` 被清空的概率更高；桌面→AOD 可能只清一次或不清。
- 验证方法：
  1. `adb logcat -v time -s AliveHSAod:V | grep -E "scene re-attached after misses|texture available|scene moved into inner AODView"`。
  2. 判据：`scene re-attached after misses` 与下一次 `texture available` 的时间差 < 100ms，且两次 `texture available` 之间动画进度归零（可在 `RenderLoop` 里临时打印 `frameTime` 或 `motion` 的进度值）。
  3. 统计：`adb logcat -d -s AliveHSAod:V | grep -c "texture available"`；一次息屏应只有 1 次，>1 即重建。
  4. 辅助：`adb shell dumpsys gfxinfo com.android.systemui framestats` 看 doze 期间是否有掉帧；`adb shell screenrecord` 逐帧看球是否「消失再出现」。

### 区分三者的最小实验矩阵

| 实验 | 候选 1 | 候选 2 | 候选 3 |
|---|---|---|---|
| 日志出现 `dropped a second AOD host` | ✅ 决定性 | ❌ | ❌ |
| 日志同时出现 `overlay added` + `ambient surface attached` | ❌ | ✅ 决定性 | ❌ |
| 日志出现 `scene re-attached after misses` | 可能伴随 | ❌ | ✅ 决定性 |
| 注释 `AodOverlay.show(svc)` 后复测 | 不变 | 消失/明显减轻 | 不变 |
| 注释 `liveSurface` 去重逻辑后复测 | 可能变成「两个球」但闪烁模式改变 | 不变 | 不变 |
| 把 `supervise` 的 `misses < 8` 改成 `< 1` 后复测 | 不变 | 不变 | 重挂更快但闪更多/或消失 |

> 建议顺序：先做候选 2 的一行注释实验（成本最低、区分度最高），再做候选 1 的 identityHashCode 日志，最后做候选 3 的计数器。三次实验都只改日志/一行开关，不动架构。

---

## 九、可以立即做、风险低的优化（最多 10 条）

1. **删除 `AodOverlay.show(svc)` 调用**（`HyperOsAod.java:62`），只保留 `injectIntoAod()`；在 alpha 透明改造完成前，全屏 overlay 必然盖时钟，删除它不会让现状更差，反而消除一条双 surface 路径。
2. **删除死代码** `showOverlay`/`hideOverlay`/`refreshScene`/`playExit`/`exitWithTransitionUnused`（`HyperOsAod.java:82-170, 180-189, 419-427`），减少误用和 review 噪音。
3. **`attach()` 加单飞守卫**：`if (liveSurface != null && liveSurface.get() != null && liveSurface.get().isAttachedToWindow()) return;`（`HyperOsAod.java:204` 前），先止血「第二个 root 杀旧 surface」。
4. **`injectIntoAod()` 改成三态**，避免调用方把「200ms 后重试」当失败（`HyperOsAod.java:191-196`）。
5. **`AodSurface.onSurfaceTextureAvailable` 先 close 旧 loop**（`HyperOsAod.java:429`），防止重复回调泄漏线程。
6. **`RenderLoop.attach` 加同 surface 同尺寸快路径**（`RenderLoop.java:84`），避免 resize 触发整链重建。
7. **`RenderLoop.attach/mode` 加 `closed` 守卫**（`RenderLoop.java:84,126`），防止 close 后排队任务重新 init。
8. **`dumpTree` 用 `Diagnostics.TRACE` 包裹**（`HyperOsAod.java:66-68`、`:235-237`、`:306`），线上包不再每次息屏遍历整棵树。
9. **`lens_photo_frag` 的 0.55 改成 uniform**（`lens_photo_frag.glsl:82` + `renderer.rs:151-158`），默认值仍 0.55，先不加设置项也不会改变行为。
10. **构建加 `--lite` 开关**（`build_release.py:233-235`），用 `build/assets-lite` 只打 shader + 当前主题，日常迭代 APK 从 320MB 降到 ~80MB；发布仍走全量。

---

## 十、建议的修复顺序

1. **先做候选 2 实验**（注释 `AodOverlay.show` 一行），确认闪烁是否消失；这是最低成本、最高信息量的一步。
2. 无论结果如何，**删除全屏 overlay 兜底**，并让 `injectIntoAod()` 三态化（1.1 + 2.2）。
3. **给 `attach()` 加单飞守卫**（1.2），阻止第二个 root 杀旧 surface；同时把 `supervise` 的 8 次改成 1 次 + attach 状态监听（1.3）。
4. **做 alpha 透明改造**（5.1）：这是「效果只作为最底层壁纸」和「overlay 不盖时钟」的共同前提，也是后续所有窗口方案的基础。
5. 最后再做 `RenderLoop` 的生命周期解耦（1.6/1.7/4.1）和构建瘦身（6.2），避免在闪烁根因未定位前做大重构。

---

## 附：本次审查未覆盖/无法静态确认的点

- 真机上 `AodView` 的构造次数与顺序（需要候选 1 的日志确认）。
- `com.miui.aod` 进程在当前 HyperOS 4 的 doze 稳定态是否真的不参与合成（`PROGRESS.md` 说只有 SystemUI 在跑，但没有 `ps`/`dumpsys` 原始输出）。
- `AodWallpaperProvider` 返回的 `AssetFileDescriptor` 的 offset 是否为 0（取决于 provider 实现；当前是 `ParcelFileDescriptor.open`，通常为 0，但建议按 4.2 改成流式读取）。
- `TextureView` 在 `NotificationShadeWindowView` 这种系统窗口里的实际合成路径（是否走 GPU 直通），需要 `dumpsys SurfaceFlinger` 或 GPU 渲染分析确认。
- `miui.systemui.plugin` 的锁屏时钟编辑器未移植（已知能力缺口，不在本次缺陷范围内）。
