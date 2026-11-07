#!/usr/bin/env python3
"""Check the release artifacts in dist/ before they are published.

Guards the common CI failure modes: an APK that does not match its own
build-report.json, a stale checksum file, or a version that disagrees with the
tag being released.

Usage:
    python3 tools/verify_release.py
    python3 tools/verify_release.py --expect-version 0.1.0
"""

from __future__ import annotations

import argparse
import hashlib
import json
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parent.parent
DIST = ROOT / "dist"


def digest(path: Path, algorithm: str) -> str:
    hasher = hashlib.new(algorithm)
    with path.open("rb") as handle:
        for block in iter(lambda: handle.read(1 << 20), b""):
            hasher.update(block)
    return hasher.hexdigest()


def fail(message: str) -> None:
    print(f"verify_release: {message}", file=sys.stderr)
    raise SystemExit(1)


def main() -> int:
    parser = argparse.ArgumentParser()
    parser.add_argument("--expect-version", help="version the release tag claims")
    args = parser.parse_args()

    report_path = DIST / "build-report.json"
    if not report_path.exists():
        fail(f"{report_path} missing; run tools/build_release.py first")
    report = json.loads(report_path.read_text(encoding="utf-8"))

    apk = DIST / report["apk"]
    if not apk.exists():
        fail(f"{apk.name} missing")
    if apk.stat().st_size != report["bytes"]:
        fail(f"{apk.name} is {apk.stat().st_size} bytes, report says {report['bytes']}")

    sha256 = digest(apk, "sha256")
    if sha256 != report["sha256"]:
        fail(f"{apk.name} sha256 {sha256} does not match the report")

    for name, algorithm in (("SHA256SUMS", "sha256"), ("MD5SUMS", "md5")):
        sums = DIST / name
        if not sums.exists():
            fail(f"{name} missing")
        entry = sums.read_text(encoding="ascii").strip().split()
        if not entry or entry[-1] != apk.name:
            fail(f"{name} does not describe {apk.name}")
        expected = digest(apk, algorithm)
        if entry[0] != expected:
            fail(f"{name} says {entry[0]}, the APK is {expected}")

    if args.expect_version and report["version"] != args.expect_version:
        fail(f"build-report says {report['version']}, tag says {args.expect_version}")

    print(f"{apk.name}: {report['version']}, {report['bytes']} bytes")
    print(f"sha256 {sha256}")
    print(f"md5    {digest(apk, 'md5')}")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
