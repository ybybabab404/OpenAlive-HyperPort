# HyperAlive

把 Flyme Alive 的连贯动画带到 HyperOS：息屏、锁屏和桌面接起来，并且把 Vivo Alive
与魅族原生时钟搬进第三方模块。

HyperAlive 是 [OpenAlive](https://github.com/Maga-King/OpenAlive) 的移植分支，面向
**HyperOS / Android 14-17**（开发机：Redmi 25102RKBEC，HyperOS 4.0 / Android 17）。
上游为 ColorOS 编写；本分支保留 ColorOS 代码路径，同时新增 HyperOS 平台层，
让模块在没有 `com.oplus.*` 组件的系统上安全退化为普通动态壁纸，而不是加载失败的钩子。

## 能力

| 能力 | HyperOS | 说明 |
| --- | --- | --- |
| Flyme Alive 场景（启航 / 留光 / 星月 / 轻启 / 山脉 / 银河 / 涟漪 / 浪流 / 深潜） | 可用 | 自绘 OpenGL 动态壁纸，不依赖 OEM 框架 |
| 渐变、光斑、气泡、立体、唱片等效果 | 可用 | 同上 |
| Vivo Alive（液态 / 光栅 / 闪卡） | 可用 | 复用 Vivo 原版渲染器 |
| 息屏动画衔接（ColorOS AOD 面板） | 仅 ColorOS | HyperOS 的 AOD 由 `com.miui.aod` 自绘，不承接第三方壁纸 |
| 锁屏编辑器原生时钟（ColorOS） | 仅 ColorOS | 依赖 `com.oplus.wallpapers` 的编辑器 |
| 通知光效 | 仅 ColorOS | 依赖 SystemUI 的 ColorOS 类 |

## 安装

1. 需要 Root + LSPosed。
2. 安装 `dist/HyperAlive-<version>-arm64-v8a.apk`。
3. 在 LSPosed 里启用模块，作用域勾选「系统框架」与「系统界面」
   （ColorOS 上再加「壁纸与个性化」）。
4. 重启，然后打开 HyperAlive 选择壁纸。

## 编译

见 [BUILD.md](BUILD.md)。源码结构：

```
app/        Android 清单、Java 源码、资源与随包素材
native/     Rust 渲染库（GLES3 shader 与场景合成）
tools/      构建与静态检查脚本
vendor/     固定版本的第三方二进制与来源清单
docs/       设计与移植记录
tests/      宿主机可运行的单元测试与设备探针
```

自实现代码采用 [MIT](LICENSE)。部分素材来自 Flyme / Vivo / 小米官方应用，
不纳入 MIT 授权，详见 [THIRD_PARTY.md](THIRD_PARTY.md)。
