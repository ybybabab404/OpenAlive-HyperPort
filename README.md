# OpenAlive HyperPort

基于 OpenAlive 项目二次开发
把 Flyme Alive 那套的连贯过渡，带到HyperOS上。

本项目以 Vibe Coding 方式开发：代码、文档和调试大量由 AI 生成。

![HyperOS 4.0](https://img.shields.io/badge/HyperOS-4.0-ff6900)

![Android 17](https://img.shields.io/badge/Android-17-3ddc84)

![LSPosed API 102](https://img.shields.io/badge/LSPosed-API%20102-5b7cfa)

![License MIT](https://img.shields.io/badge/license-MIT-yellow)

> **这不是 OpenAlive 官方项目。**
>
> 本项目基于 [Maga-King/OpenAlive](https://github.com/Maga-King/OpenAlive) 移植到 HyperOS。

## 如何使用

1. 需要 Root 和 LSPosed。
2. 安装 APK 并授予 Root 权限。
3. 在 LSPosed 里启用模块。
4. 重启设备。
5. 打开 AliveHS，选择并应用壁纸。

## 兼容性

| 机型                | 系统                       |
| ----------------- | ------------------------ |
| 25102RKBEC（myron） | HyperOS 4.0.0.33.XPMCNXM |

## 注意事项

**Q：为什么锁屏界面无法通过上滑解锁？**

A：可能是因为你正在使用「液态」样式。为避免误触，液态样式下只有屏幕最底部约 12% 的区域能起手上滑解锁。

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

自实现代码跟随上游采用 [MIT](LICENSE) 许可。**部分资产来源于魅族22FlymeOS、**&#x4F;riginOS 7，官方 UI、字体、壁纸及效果素材不纳入 MIT 授权，详见[资源说明](THIRD_PARTY.md)。

## 致谢

- [Maga-King](https://github.com/Maga-King) 和 [OpenAlive](https://github.com/Maga-King/OpenAlive)：整个项目的起点。

## 反馈

带上机型、系统版本、用的样式、复现步骤。
