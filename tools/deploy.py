#!/usr/bin/env python3
"""Build and install the debug APK on a test device.

The APK is ~300 MB and only a quarter of that is code. aapt2 can split resources
by config but not assets, so a real asset split would mean moving the heavy trees
into a second package and rewriting every getAssets() call site. Until that
refactor happens this script wins back most of the time by:

  * preferring USB (same bytes, far more bandwidth than the Wi-Fi adb link),
  * skipping the rebuild when nothing changed,
  * pushing to /data/local/tmp and running pm install there, which survives
    flaky Wi-Fi links better than a streaming install.

Usage:
    python tools/deploy.py                    # build (if needed) + install
    python tools/deploy.py --build            # force a rebuild first
    python tools/deploy.py --device 1.2.3.4:5555
    ALIVEHS_DEVICE=1.2.3.4:5555 python tools/deploy.py

Environment:
    ANDROID_HOME / ANDROID_SDK_ROOT   Android SDK root (falls back to build_release.py search)
    JAVA_HOME                         JDK for javac (build only)
    ALIVEHS_DEVICE                    device serial or host:port, comma separated for fallbacks
    ALIVEHS_CONNECT                   comma separated host:port list to adb connect when nothing is attached
    ALIVEHS_NATIVE_SO                 prebuilt libalive_clean.so (build only)
"""

from __future__ import annotations

import argparse
import hashlib
import json
import os
import re
import subprocess
import sys
import time
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
DIST = ROOT / "dist"
MANIFEST = ROOT / "app" / "AndroidManifest.xml"


def app_version() -> str:
    match = re.search(r'android:versionName="([^"]+)"', MANIFEST.read_text(encoding="utf-8"))
    if not match:
        raise SystemExit(f"cannot read versionName from {MANIFEST}")
    return match.group(1)


APK = DIST / f"AliveHS-{app_version()}-arm64-v8a.apk"
STAMP = ROOT / "build" / "release" / ".deploy-stamp.json"
REMOTE = "/data/local/tmp/alivehs.apk"


def adb() -> str:
    override = os.environ.get("ADB")
    if override:
        return override
    for key in ("ANDROID_HOME", "ANDROID_SDK_ROOT"):
        base = os.environ.get(key)
        if base:
            for name in ("adb.exe", "adb"):
                candidate = Path(base) / "platform-tools" / name
                if candidate.exists():
                    return str(candidate)
    return "adb"


def run(args, **kwargs):
    return subprocess.run([str(a) for a in args], check=True, **kwargs)


def adb_devices() -> list[str]:
    out = subprocess.run([adb(), "devices"], capture_output=True, text=True).stdout
    return [line.split("\t")[0] for line in out.splitlines()[1:] if "\tdevice" in line]


def connect_candidates() -> list[str]:
    raw = os.environ.get("ALIVEHS_CONNECT", "")
    return [item.strip() for item in raw.split(",") if item.strip()]


def pick_device() -> str:
    devices = adb_devices()
    if not devices:
        for address in connect_candidates():
            subprocess.run([adb(), "connect", address], capture_output=True)
        time.sleep(2)
        devices = adb_devices()
    if not devices:
        sys.exit("no device: attach USB, enable wireless debugging, or set ALIVEHS_CONNECT")
    wanted = [item.strip() for item in os.environ.get("ALIVEHS_DEVICE", "").split(",") if item.strip()]
    for serial in wanted:
        if serial in devices:
            return serial
    # USB serials have no ':' in them; prefer those.
    for device in devices:
        if ":" not in device:
            return device
    return devices[0]


def apk_fingerprint() -> str:
    digest = hashlib.sha256()
    digest.update(str(APK.stat().st_size).encode())
    with APK.open("rb") as handle:
        for chunk in iter(lambda: handle.read(1 << 20), b""):
            digest.update(chunk)
    return digest.hexdigest()


def build() -> None:
    env = dict(os.environ)
    env["PYTHONIOENCODING"] = "utf-8"
    prebuilt = ROOT / ".tmp" / "libalive_clean.so"
    if prebuilt.exists():
        env.setdefault("ALIVEHS_NATIVE_SO", str(prebuilt))
    run([sys.executable, ROOT / "tools" / "build_release.py", "--no-checks"], env=env)


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--build", action="store_true", help="force a rebuild before deploying")
    parser.add_argument("--device", help="device serial or host:port to deploy to")
    args = parser.parse_args()

    if args.device:
        os.environ["ALIVEHS_DEVICE"] = args.device

    if args.build or not APK.exists():
        build()

    fingerprint = apk_fingerprint()
    previous = json.loads(STAMP.read_text()) if STAMP.exists() else {}
    device = pick_device()
    print(f"device {device}")

    if previous.get("apk") == fingerprint and previous.get("device") == device:
        print("same APK already deployed to this device; pass --build to force")
        return 0

    started = time.time()
    run([adb(), "-s", device, "push", APK, REMOTE])
    run([adb(), "-s", device, "shell", f"su -c 'pm install -r -d {REMOTE}'"])
    elapsed = time.time() - started
    STAMP.parent.mkdir(parents=True, exist_ok=True)
    STAMP.write_text(json.dumps({"apk": fingerprint, "device": device, "seconds": round(elapsed, 1)}))
    print(f"deployed in {elapsed:.1f}s")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
