#!/usr/bin/env python3
"""Deploy the debug build to the test device as fast as the transport allows.

Why this exists: the APK is ~320 MB and only ~77 MB of it is code. `adb install`
over Wi-Fi pushed the whole thing every time (80-115 s). aapt2 can only split
*resources* by config, not assets, so a real asset split would mean moving the
heavy trees into a second package and rewriting 70 getAssets() call sites.

Until that refactor happens, this script wins back most of the time by:
  * preferring USB (same bytes, ~20x the bandwidth of the Wi-Fi adb link),
  * skipping the rebuild when nothing changed,
  * pushing to /data/local/tmp and running `pm install` there, which survives
    flaky Wi-Fi links better than a streaming install.

Usage:
    python tools/deploy.py            # build (if needed) + install
    python tools/deploy.py --build    # force a rebuild first
"""

from __future__ import annotations

import argparse
import hashlib
import json
import os
import subprocess
import sys
import time
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
DIST = ROOT / "dist"
APK = DIST / "HyperAlive-0.5.0-preview-arm64-v8a.apk"
STAMP = ROOT / "build" / "release" / ".deploy-stamp.json"
ADB = Path(os.environ.get("ANDROID_HOME", r"C:\Users\ybybabab\AppData\Local\Android\Sdk")) / "platform-tools" / "adb.exe"
REMOTE = "/data/local/tmp/ha.apk"

WIRELESS = ["10.10.57.66:39795", "10.20.2.254:39795", "10.11.48.149:39795"]


def run(args, **kwargs):
    return subprocess.run([str(a) for a in args], check=True, **kwargs)


def adb_devices() -> list[str]:
    out = subprocess.run([str(ADB), "devices"], capture_output=True, text=True).stdout
    return [line.split("\t")[0] for line in out.splitlines()[1:] if "\tdevice" in line]


def pick_device() -> str:
    devices = adb_devices()
    if not devices:
        for address in WIRELESS:
            subprocess.run([str(ADB), "connect", address], capture_output=True)
        time.sleep(2)
        devices = adb_devices()
    if not devices:
        sys.exit("no device: plug in USB or enable wireless debugging")
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
    env.update({
        "ANDROID_HOME": str(Path(r"C:\Users\ybybabab\AppData\Local\Android\Sdk")),
        "JAVA_HOME": r"C:\Users\ybybabab\app\pcl\JAVA21\graalvm",
        "HYPERALIVE_NATIVE_SO": str(ROOT / ".tmp" / "libalive_clean.so"),
        "PYTHONIOENCODING": "utf-8",
    })
    run([sys.executable, ROOT / "tools" / "build_release.py", "--no-checks"], env=env)


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--build", action="store_true", help="force a rebuild before deploying")
    args = parser.parse_args()

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
    run([ADB, "-s", device, "push", APK, REMOTE])
    run([ADB, "-s", device, "shell", f"su -c 'pm install -r -d {REMOTE}'"])
    elapsed = time.time() - started
    STAMP.parent.mkdir(parents=True, exist_ok=True)
    STAMP.write_text(json.dumps({"apk": fingerprint, "device": device, "seconds": round(elapsed, 1)}))
    print(f"deployed in {elapsed:.1f}s")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
