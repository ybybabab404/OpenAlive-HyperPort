# 构建

不依赖 Gradle。`tools/build_release.py` 直接调 Android SDK 的命令行工具，从源码树一路打到签名 APK。

## 需要什么

| 组件 | 版本 | 什么时候需要 |
| --- | --- | --- |
| JDK | 17 或更新的 LTS | 总是 |
| Android SDK build-tools | 35.0.0 | 总是 |
| Android platform（`android.jar`） | 任意一版，脚本自动挑最新的 | 总是 |
| Python | 3.10+ | 总是 |
| Rust + `aarch64-linux-android` target | `rust-toolchain.toml` 指定的版本 | 重编 `libalive_clean.so` 时 |
| Android NDK | r29 | 重编 `libalive_clean.so` 时 |

## 怎么编

```sh
export ANDROID_HOME=/path/to/Android/Sdk
export JAVA_HOME=/path/to/jdk
python3 tools/build_release.py
```

Windows 上两个路径都要用正斜杠或者转义过的反斜杠；aapt2 和 zipalign 处理不了非 ASCII 的绝对路径，脚本会把相关参数改写成相对路径再调用。

产物落在 `dist/`：

- `AliveHS-<versionName>-arm64-v8a.apk`
- `build-report.json`：版本、大小、SHA-256、资源表数量、SDK 版本、原生库来源
- `SHA256SUMS`、`MD5SUMS`

## 只改了 Java

原生渲染库没动的时候不必装 Rust 和 NDK，把现成的 so 指过去就行：

```sh
ALIVEHS_NATIVE_SO=path/to/libalive_clean.so python3 tools/build_release.py
```

`tools/deploy.py` 会自动挑 `build/release/libalive_clean.so`，编完顺手推到设备上装好。

## 选项

- `--no-checks`：跳过 `tools/check_*.py` 那组资源与清单校验
- `--skip-sign`：只产出未签名 APK，方便看结构

## 签名

默认用 `local/development.jks` 里的开发密钥，口令在 `tools/build_release.py` 里写死，仅供本机调试。要打正式包就设环境变量：

```sh
export ALIVEHS_KEYSTORE=/path/to/release.jks
export ALIVEHS_STORE_PASSWORD=...
export ALIVEHS_KEY_ALIAS=...
export ALIVEHS_KEY_PASSWORD=...
python3 tools/build_release.py
```

密钥文件不要提交。`.gitignore` 已经挡掉 `*.jks`、`*.keystore`、`signing.json`。

## 部署到设备

```sh
python3 tools/deploy.py                      # 没有变化就不重装
python3 tools/deploy.py --build              # 强制重编再装
python3 tools/deploy.py --device 1.2.3.4:5555
```

设备靠 `ALIVEHS_DEVICE`（序列号或 `host:port`，逗号分隔按顺序匹配）来挑，没设备时用 `ALIVEHS_CONNECT`（逗号分隔的 `host:port` 列表）自动 `adb connect`。都没配的话，有 USB 就挑 USB。

安装走 `adb push` 到 `/data/local/tmp` 再 `su -c pm install -r -d`，比流式安装抗断线。
