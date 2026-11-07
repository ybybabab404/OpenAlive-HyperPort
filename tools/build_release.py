"""Build HyperAlive (Xposed module for HyperOS / Android 14-17) from this tree.

The script is intentionally a single, auditable pipeline: every input is either
checked into this repository, pinned by SHA-256, or produced by this file. It
never downloads code at build time except the compile-only Xposed API stub,
which is pinned by checksum and never enters the APK.

Usage:
  python tools/build_release.py [--no-checks] [--skip-sign]

Environment:
  ANDROID_HOME / ANDROID_SDK_ROOT   Android SDK root (platform-tools not needed)
  JAVA_HOME                         JDK 17 or newer (javac, keytool)
  HYPERALIVE_NATIVE_SO              prebuilt libalive_clean.so (arm64) to embed
                                    instead of running cargo; used by the Windows
                                    host build, which has no Android NDK.
  HYPERALIVE_KEYSTORE / ...         signing key, see BUILD.md
"""
from __future__ import annotations

from pathlib import Path
import hashlib
import json
import os
import shutil
import struct
import subprocess
import sys
import urllib.request
import zipfile

import xml.etree.ElementTree as ET

from apk_layout import RESOURCE_ALIGNMENT, write_entry, verify_apk

ROOT = Path(__file__).resolve().parents[1]
WINDOWS = os.name == "nt"
EXE = ".exe" if WINDOWS else ""

XPOSED_API_JAR = ROOT / "vendor/libxposed/classes.jar"

XZ_SOURCES_SHA256 = "c35c682fa8b617c1f6f21270df14bad4e11df2fe46962dfe45e765b7aec0181b"

CHECKS = [
    "check_layout.py",
    "check_clock_scope.py",
    "check_wallpaper_picker.py",
    "check_xiaomi_assets.py",
    "check_xiaomi_desktop.py",
    "check_xiaomi_aod_density.py",
    "check_unlock_handoff.py",
    "check_fingerprint_clock.py",
    "check_fingerprint_clock_mask.py",
]


def fail(message: str) -> None:
    raise SystemExit("build: " + message)


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as handle:
        for block in iter(lambda: handle.read(1 << 20), b""):
            digest.update(block)
    return digest.hexdigest()


def sdk_root() -> Path:
    for name in ("ANDROID_HOME", "ANDROID_SDK_ROOT"):
        value = os.environ.get(name)
        if value and (Path(value) / "build-tools").is_dir():
            return Path(value)
    home = Path(os.environ.get("USERPROFILE") or Path.home())
    for candidate in (home / "AppData/Local/Android/Sdk", home / "Android/Sdk", Path("/usr/lib/android-sdk")):
        if (candidate / "build-tools").is_dir():
            return candidate
    fail("Android SDK not found; set ANDROID_HOME")


def jdk_bin() -> Path:
    value = os.environ.get("JAVA_HOME")
    candidates = [Path(value) / "bin"] if value else []
    candidates += [
        Path("C:/Program Files/Java/jdk-17/bin"),
        Path("C:/Program Files/Java/jdk-21/bin"),
        Path("C:/Program Files/Eclipse Adoptium/jdk-17/bin"),
        Path("/usr/lib/jvm/java-17-openjdk-amd64/bin"),
    ]
    for candidate in candidates:
        if (candidate / ("javac" + EXE)).exists():
            return candidate
    fail("JDK not found; set JAVA_HOME to a JDK 17+ installation")


def newest_build_tools(sdk: Path, preferred: str = "35.0.0") -> Path:
    root = sdk / "build-tools"
    if (root / preferred).is_dir():
        return root / preferred
    versions = sorted((p for p in root.iterdir() if (p / ("aapt2" + EXE)).exists()),
                      key=lambda p: [int(x) for x in p.name.split(".") if x.isdigit()], reverse=True)
    if not versions:
        fail("no usable build-tools found")
    return versions[0]


def platform_jar(sdk: Path, preferred: int = 35) -> Path:
    root = sdk / "platforms"
    jars = {p.name: p / "android.jar" for p in root.iterdir() if (p / "android.jar").is_file()}
    if not jars:
        fail("no android.jar found; install a platform")
    exact = jars.get("android-%d" % preferred)
    if exact:
        return exact
    numeric = sorted((name for name in jars if name.startswith("android-")),
                     key=lambda name: int("".join(ch for ch in name.split("-")[1] if ch.isdigit()) or 0),
                     reverse=True)
    return jars[numeric[0]]


def run(args, env=None) -> None:
    subprocess.run([str(a) for a in args], cwd=ROOT, env=env, check=True)


def rel(path) -> str:
    """Native tools (aapt2, zipalign) receive relative paths: the SDK binaries
    do not handle a non-ASCII absolute path on the Windows command line."""
    path = Path(path)
    try:
        return path.resolve().relative_to(ROOT).as_posix()
    except ValueError:
        return str(path)


def native_tool(*args) -> None:
    run([rel(a) if isinstance(a, (str, Path)) and Path(str(a)).is_absolute() else a for a in args])


def arg_path(value) -> str:
    """Argument files are decoded with the platform charset on Windows, so they
    must stay ASCII: every path inside the tree is expressed relative to ROOT."""
    path = Path(str(value))
    if path.is_absolute():
        try:
            return path.resolve().relative_to(ROOT).as_posix()
        except ValueError:
            return str(value)
    return str(value).replace("\\", "/")


def javac(arguments_file: Path, args) -> None:
    payload = "\n".join(json.dumps(arg_path(a), ensure_ascii=False) for a in args)
    arguments_file.write_text(payload, encoding="utf8")
    run([JDK / ("javac" + EXE), "@" + str(arguments_file)])


def main(argv) -> None:
    global JDK, BT, ANDROID, OUT, DIST

    skip_checks = "--no-checks" in argv
    skip_sign = "--skip-sign" in argv

    sdk = sdk_root()
    JDK = jdk_bin()
    BT = newest_build_tools(sdk)
    ANDROID = platform_jar(sdk)
    OUT = ROOT / "build/release"
    DIST = ROOT / "dist"
    VENDOR = ROOT / "vendor/flyme"

    for folder in ("classes", "dex", "generated", "ui-api", "xz-source"):
        target = (OUT / folder).resolve()
        if not target.is_relative_to((ROOT / "build").resolve()):
            fail("refusing to clean outside build/: " + str(target))
        if target.exists():
            shutil.rmtree(target)
        target.mkdir(parents=True)
    DIST.mkdir(exist_ok=True)

    manifest = ET.parse(ROOT / "app/AndroidManifest.xml").getroot()
    android_ns = "{http://schemas.android.com/apk/res/android}"
    version = manifest.attrib[android_ns + "versionName"]
    apk = DIST / ("HyperAlive-" + version + "-arm64-v8a.apk")

    xposed = XPOSED_API_JAR
    if not xposed.exists():
        fail("Xposed API 102 jar missing: " + str(xposed))

    ort = ROOT / "vendor/onnxruntime"
    ort_jar = ort / "classes.jar"
    ort_native = [ort / "jni/arm64-v8a" / name for name in ("libonnxruntime.so", "libonnxruntime4j_jni.so")]
    for directory, required in ((ort, ["classes.jar", *[p.relative_to(ort).as_posix() for p in ort_native]]),
                                (ROOT / "app/assets/vivo/models", ["u2netp.onnx"])):
        metadata = json.loads((directory / "source.json").read_text(encoding="utf8"))
        for name in required:
            if sha256(directory / name) != metadata["files"][name]:
                fail("segmentation input checksum mismatch: " + name)
    for library in ort_native:
        data = library.read_bytes()
        if data[:6] != b"\x7fELF\x02\x01":
            fail("expected little-endian ELF64: " + library.name)
        phoff = struct.unpack_from("<Q", data, 32)[0]
        entry_size, count = struct.unpack_from("<HH", data, 54)
        for index in range(count):
            offset = phoff + index * entry_size
            if struct.unpack_from("<I", data, offset)[0] == 1 and struct.unpack_from("<Q", data, offset + 48)[0] < 16384:
                fail("native runtime must support 16 KB pages: " + library.name)

    if not skip_checks:
        for script in CHECKS:
            run([sys.executable, ROOT / "tools" / script])

    native = ROOT / "target/aarch64-linux-android/release/libalive_clean.so"
    prebuilt = os.environ.get("HYPERALIVE_NATIVE_SO")
    if prebuilt:
        native = Path(prebuilt)
        if not native.is_file():
            fail("HYPERALIVE_NATIVE_SO points to a missing file: " + str(native))
    else:
        ndk = Path(os.environ.get("ANDROID_NDK_HOME", sdk / "ndk/29.0.14206865"))
        linker = ndk / "toolchains/llvm/prebuilt" / ("windows-x86_64" if WINDOWS else "linux-x86_64") / "bin/aarch64-linux-android28-clang.cmd"
        if not linker.exists():
            linker = linker.with_suffix(".exe" if WINDOWS else "")
        env = {**os.environ, "CARGO_TARGET_AARCH64_LINUX_ANDROID_LINKER": str(linker)}
        run(["cargo", "test", "--locked"], env=env)
        run(["cargo", "build", "--locked", "--release", "--target", "aarch64-linux-android"], env=env)

    native_tool(BT / ("aapt2" + EXE), "compile", "--dir", VENDOR / "res", "-o", OUT / "vendor-res.zip")
    native_tool(BT / ("aapt2" + EXE), "compile", "--dir", ROOT / "app/res", "-o", OUT / "app-res.zip")
    native_tool(BT / ("aapt2" + EXE), "link", "-o", OUT / "base.apk", "--manifest", ROOT / "app/AndroidManifest.xml",
         "-I", ANDROID, "--auto-add-overlay", "--java", OUT / "generated", "-A", ROOT / "app/assets",
         OUT / "vendor-res.zip", "-R", OUT / "app-res.zip")

    javac(OUT / "api.args", ["-encoding", "UTF-8", "-source", "8", "-target", "8",
                             "-bootclasspath", ANDROID, "-d", OUT / "ui-api",
                             *sorted((ROOT / "tools/ui-api").rglob("*.java"))])

    xz_source = ROOT / "vendor/xz/xz-1.12-sources.jar"
    if sha256(xz_source) != XZ_SOURCES_SHA256:
        fail("XZ source checksum mismatch")
    with zipfile.ZipFile(xz_source) as upstream:
        for entry in upstream.infolist():
            if not entry.filename.startswith("org/tukaani/xz/") or not entry.filename.endswith(".java"):
                continue
            target = (OUT / "xz-source" / entry.filename).resolve()
            if not target.is_relative_to((OUT / "xz-source").resolve()):
                fail("unsafe entry in XZ sources: " + entry.filename)
            target.parent.mkdir(parents=True, exist_ok=True)
            target.write_bytes(upstream.read(entry))

    sources = (sorted((ROOT / "app/java").rglob("*.java")) + sorted((OUT / "generated").rglob("*.java"))
               + sorted((OUT / "xz-source").rglob("*.java")))
    javac(OUT / "app.args", ["-encoding", "UTF-8", "-source", "8", "-target", "8",
                             "-bootclasspath", str(ANDROID) + os.pathsep + str(BT / "core-lambda-stubs.jar"),
                             "-classpath", os.pathsep.join(map(str, [xposed, OUT / "ui-api", ort_jar])),
                             "-d", OUT / "classes", *sources])

    d8 = OUT / "d8.args"
    d8.write_text("\n".join(json.dumps(arg_path(a), ensure_ascii=False) for a in [
        "-cp", BT / "lib/d8.jar", "com.android.tools.r8.D8", "--min-api", "28", "--lib", ANDROID,
        "--classpath", xposed, "--classpath", OUT / "ui-api", "--output", OUT / "dex",
        ort_jar, *sorted((OUT / "classes").rglob("*.class"))]), encoding="utf8")
    run([JDK / ("java" + EXE), "@" + str(d8)])

    with zipfile.ZipFile(OUT / "base.apk") as base, zipfile.ZipFile(OUT / "unsigned.apk", "w", zipfile.ZIP_DEFLATED, compresslevel=9) as package:
        for entry in sorted(base.infolist(), key=lambda e: e.filename):
            if not entry.filename.startswith("assets/video/") and entry.filename != "assets/xposed_init":
                write_entry(package, entry.filename, base.read(entry))
        write_entry(package, "META-INF/xposed/java_init.list", b"org.aliveclean.CleanHooks\n")
        write_entry(package, "META-INF/xposed/module.prop",
                    b"minApiVersion=102\ntargetApiVersion=102\nstaticScope=true\n")
        write_entry(package, "META-INF/xposed/scope.list",
                    b"android\ncom.android.systemui\ncom.miui.aod\ncom.android.thememanager\n")
        write_entry(package, "classes.dex", (OUT / "dex/classes.dex").read_bytes())
        write_entry(package, "classes2.dex", (VENDOR / "editor-ui.dex").read_bytes())
        write_entry(package, "lib/arm64-v8a/libalive_clean.so", native.read_bytes())
        for library in ort_native:
            write_entry(package, "lib/arm64-v8a/" + library.name, library.read_bytes())
        for name in ("LICENSE.txt", "ThirdPartyNotices.txt"):
            write_entry(package, "assets/licenses/onnxruntime/" + name, (ort / name).read_bytes())
    native_tool(BT / ("zipalign" + EXE), "-f", "-p", RESOURCE_ALIGNMENT, OUT / "unsigned.apk", OUT / "aligned.apk")

    key = Path(os.environ.get("HYPERALIVE_KEYSTORE", ROOT / "local/development.jks"))
    alias = os.environ.get("HYPERALIVE_KEY_ALIAS", "development")
    env = {**os.environ, "HYPERALIVE_STORE_PASSWORD": os.environ.get("HYPERALIVE_STORE_PASSWORD", "hyperalive-dev")}
    if not skip_sign:
        if not key.exists():
            if "HYPERALIVE_KEYSTORE" in os.environ:
                fail("configured signing key is missing: " + str(key))
            key.parent.mkdir(parents=True, exist_ok=True)
            run([JDK / ("keytool" + EXE), "-genkeypair", "-keystore", key, "-storepass:env", "HYPERALIVE_STORE_PASSWORD",
                 "-keypass:env", "HYPERALIVE_STORE_PASSWORD", "-alias", alias, "-keyalg", "RSA", "-keysize", "3072",
                 "-validity", "10000", "-dname", "CN=HyperAlive Development", "-noprompt"], env=env)
        run([JDK / ("java" + EXE), "-jar", BT / "lib/apksigner.jar", "sign", "--alignment-preserved", "true",
             "--ks", key, "--ks-pass", "env:HYPERALIVE_STORE_PASSWORD", "--ks-key-alias", alias,
             "--out", apk, OUT / "aligned.apk"], env=env)
        run([JDK / ("java" + EXE), "-jar", BT / "lib/apksigner.jar", "verify", "--verbose", "--print-certs", apk])
    else:
        shutil.copyfile(OUT / "aligned.apk", apk)
    native_tool(BT / ("zipalign" + EXE), "-c", "-p", RESOURCE_ALIGNMENT, apk)

    report = {
        "product": "HyperAlive",
        "version": version,
        "apk": apk.name,
        "sha256": sha256(apk),
        "bytes": apk.stat().st_size,
        "resource_tables": verify_apk(apk),
        "sdk": {"platform_jar": ANDROID.name if ANDROID.parent.name == "platforms" else str(ANDROID),
                "build_tools": BT.name},
        "native": {"path": str(native.relative_to(ROOT)) if native.is_relative_to(ROOT) else str(native),
                   "sha256": sha256(native),
                   "origin": "prebuilt" if prebuilt else "cargo"},
        "executables": {
            "classes.dex": sha256(OUT / "dex/classes.dex"),
            "classes2.dex": sha256(VENDOR / "editor-ui.dex"),
            "libalive_clean.so": sha256(native),
        },
    }
    report["executables"].update({p.name: sha256(p) for p in ort_native})
    (DIST / "build-report.json").write_text(json.dumps(report, indent=2) + "\n", encoding="utf8", newline="\n")
    (DIST / "SHA256SUMS").write_text(report["sha256"] + "  " + apk.name + "\n", encoding="ascii", newline="\n")
    print(json.dumps(report, indent=2))


if __name__ == "__main__":
    main(sys.argv[1:])
