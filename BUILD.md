# 编译

HyperAlive 不依赖 Gradle：`tools/build_release.py` 直接调用 aapt2 / javac / D8 /
zipalign / apksigner，Rust 只编译 `native/` 下的渲染库。

## 环境

- JDK 17+（脚本按 `JAVA_HOME`、常见安装路径顺序查找）
- Android SDK：build-tools 35.0.0（或更新）与任意一个 platform 的 `android.jar`
- Rust 1.96.0（`rust-toolchain.toml` 固定）+ `aarch64-linux-android` 目标 + NDK r29：
  只有需要重新编译 `libalive_clean.so` 时才需要
- Python 3.10+

设置 `ANDROID_HOME`、`JAVA_HOME` 后：

```sh
python tools/build_release.py
```

产物在 `dist/`：`HyperAlive-<versionName>-arm64-v8a.apk`、`build-report.json`、
`SHA256SUMS`。

### 只改 Java 时

若本机没有 Rust / NDK，可以把已有的 `libalive_clean.so` 交给构建脚本复用：

```sh
HYPERALIVE_NATIVE_SO=path/to/libalive_clean.so python tools/build_release.py
```

报告里的 `native.origin` 会写成 `prebuilt`，并记录 so 的 SHA-256，方便审计对比。

### 选项

- `--no-checks`：跳过 `tools/check_*.py` 静态检查（只在本地快速迭代时使用）
- `--skip-sign`：只对齐不签名

### 签名

未配置时脚本会在 `local/development.jks` 生成开发密钥（该目录已被 git 忽略）。
正式构建通过 `HYPERALIVE_KEYSTORE`、`HYPERALIVE_STORE_PASSWORD`、
`HYPERALIVE_KEY_ALIAS` 注入，密钥不进仓库。

### Windows 说明

Windows 的 aapt2 / zipalign 无法处理非 ASCII 绝对路径，脚本会把树内路径转为相对路径；
Java 的参数文件同样只写 ASCII 路径。仓库路径含中文时也不需要额外处理。
