# OpenAlive HyperPort

把 Flyme Alive 那套「息屏—锁屏—桌面」的连贯过渡，搬到小米澎湃系统上。

[![HyperOS 4.0](https://img.shields.io/badge/HyperOS-4.0-ff6900)](https://www.mi.com/hyperos)
[![Android 17](https://img.shields.io/badge/Android-17-3ddc84)](https://www.android.com/17/)
[![LSPosed API 102](https://img.shields.io/badge/LSPosed-API%20102-5b7cfa)](https://github.com/LSPosed/LSPosed)
[![License MIT](https://img.shields.io/badge/license-MIT-yellow)](LICENSE)

> **这不是 OpenAlive 官方项目。**
>
> 本项目基于 [Maga-King/OpenAlive](https://github.com/Maga-King/OpenAlive) 二次移植，只换了运行环境：从 ColorOS 换到 HyperOS。
> 上游作者没有参与这个分支的开发，也不为它背书。
> 包名沿用 `org.aliveclean`，桌面图标叫 **AliveHS**，模块列表里的描述是全名 OpenAlive HyperPort。

## 它做什么

- 把息屏、锁屏、桌面三段接缝抹平：亮屏、解锁、回桌面时，壁纸和动画连着走，不闪断。
- 六款息屏壁纸：启航、留光、星月、轻启、山脉、银河。留光支持相框、取景框和角度调整。
- 自选照片：裁切，双指缩放、拖动、复位。
- 息屏上显示时间、日期和农历。
- 三套动态壁纸来源：
  - 魅族：通知光效、光晕
  - vivo：液态、趣味光栅、闪卡
  - 小米：月球、雪山、几何、土星环、地球、火星
- 这一版具体做到哪一步，看 Releases。

## 安装

1. 需要 Root 和 LSPosed。
2. 安装 APK。
3. 在 LSPosed 里启用模块，作用域勾上「系统框架、系统界面、主题壁纸（`com.android.thememanager`）」。
4. 重启设备。
5. 打开 AliveHS，选样式，应用。

作用域少勾一个，表现是动画接不上——不是样式没生效。

## 兼容性

| 机型 | 系统 | Android | 状态 |
| --- | --- | --- | --- |
| 25102RKBEC（myron） | HyperOS 4.0.0.33.XPMCNXM | 17 | 开发验证 |
| 其他 HyperOS 机型 | — | 14–17 | 未验证 |

## 已知问题

- 全屏息屏要系统开着 FsAod。部分机型 `isSupportFsAod()` 恒为 false，这一层本项目不做绕过。
- 小米壁纸的桌面翻页联动依赖系统桌面的滚动回调，换桌面后可能失效。
- 显示模式下拉偶尔切换完不立刻生效，退出重进设置页即可。
- 换壁纸后黑屏或残留，清一次「主题壁纸」的缓存。

## 和上游不一样的地方

- 壁纸与个性化入口：ColorOS 走 `com.oplus.wallpapers`，HyperOS 接到系统「主题壁纸」的个性化页。
- 上游把锁屏编辑器时钟放在系统锁屏编辑器里，HyperOS 没有等价入口，改成息屏与锁屏的显示模式下拉。
- 系统界面的钩子点位按 HyperOS 的类结构重写。
- 包名故意没改：和上游共用配置格式、同步上游省事，已经装过的设备也不会丢设置。

## 构建

仓库不带 Gradle。`tools/build_release.py` 直接调 aapt2、javac、D8、zipalign、apksigner。

需要 JDK 17+、Android SDK（build-tools 35.0.0 加任一 platform 的 `android.jar`）、Python 3.10+。只有要重编 `libalive_clean.so` 时才需要 Rust 1.96 和 NDK r29。

```sh
export ANDROID_HOME=/path/to/Android/Sdk
export JAVA_HOME=/path/to/jdk
python3 tools/build_release.py
```

产物在 `dist/`：`AliveHS-<version>-arm64-v8a.apk`、`build-report.json`、`SHA256SUMS`、`MD5SUMS`。本机没有 Rust / NDK 时可以直接复用现成的 so：

```sh
ALIVEHS_NATIVE_SO=path/to/libalive_clean.so python3 tools/build_release.py
```

细节见 [BUILD.md](BUILD.md)。

## 许可

自己写的代码是 MIT，见 [LICENSE](LICENSE)。美术资源不跟着 MIT 走：字体、着色器、壁纸、预览图的版权归魅族、小米、vivo 各自的权利人，不能二次分发，也不能商用。逐项清单见 [THIRD_PARTY.md](THIRD_PARTY.md)。

## 免责

本项目只在运行时介入系统框架和系统界面进程，不写 boot，不写 system 分区。
出问题先卸载模块或停用 LSPosed 再重启；hook 崩了导致进不去系统，用 Magisk 安全模式或 adb 卸载。按现状提供，不担保任何后果。

## 致谢

- [Maga-King](https://github.com/Maga-King) 和 [OpenAlive](https://github.com/Maga-King/OpenAlive)：整个项目的起点。
- FlymeOS Alive 的原设计与实现。
- 在上游 issue 里给出机型差异和复现步骤的人。

## 反馈

带上机型、系统版本、用的样式、复现步骤。日志筛 `org.aliveclean`。

还在改，能用但不完美。
