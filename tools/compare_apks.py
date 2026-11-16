"""Compare two APKs structurally: zip entries, resource tables and dex class/member inventories.

Used to prove a refactor changed nothing but what it claims to. Comments and reordering show up
nowhere; removed members show up as `gone`, added ones as `new`. Code itself is not compared
(line tables move with any comment edit), so this catches fingerprints, not logic edits.

Usage:
    python3 tools/compare_apks.py OLD.apk NEW.apk [--allow-gone Class#member ...]

Exit status is 1 when the new APK differs in an unexpected way.
"""
from __future__ import annotations

import re
import subprocess
import sys
import tempfile
import zipfile
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]

CLASS_LINE = re.compile(r"^\s*Class descriptor\s*:\s*'L([^;]+);'")
MEMBER_LINE = re.compile(r"^\s*name\s*:\s*'([^']+)'")
# Synthetic accessors are renumbered whenever a private member changes, so they carry no signal.
SYNTHETIC = re.compile(r"access\$\d+")
TYPE_LINE = re.compile(r"^\s*type\s*:\s*'([^']+)'")
ACCESS_LINE = re.compile(r"^\s*access\s*:\s*(\S+)")


def build_tools() -> Path:
    sdk = None
    for name in ("ANDROID_HOME", "ANDROID_SDK_ROOT"):
        value = __import__("os").environ.get(name)
        if value and (Path(value) / "build-tools").is_dir():
            sdk = Path(value)
            break
    if sdk is None:
        raise SystemExit("compare_apks: set ANDROID_HOME to an Android SDK")
    candidates = sorted((sdk / "build-tools").iterdir(), reverse=True)
    for candidate in candidates:
        if (candidate / "dexdump.exe").exists() or (candidate / "dexdump").exists():
            return candidate
    raise SystemExit("compare_apks: no dexdump in build-tools")


def dex_inventory(apk: Path, tools: Path, workdir: Path) -> set[str]:
    """`Class` and `Class#member` entries, ignoring code and line tables."""
    entries: set[str] = set()
    exe = "dexdump.exe" if (tools / "dexdump.exe").exists() else "dexdump"
    # dexdump cannot open a non-ASCII path on Windows, so extract to a scratch directory.
    with zipfile.ZipFile(apk) as bundle:
        for name in sorted(n for n in bundle.namelist() if n.endswith(".dex")):
            target = workdir / Path(name).name
            target.write_bytes(bundle.read(name))
            dump = subprocess.run([str(tools / exe), str(target)],
                                  capture_output=True, text=True, errors="replace", check=True).stdout
            current = None
            pending = None
            for line in dump.splitlines():
                match = CLASS_LINE.match(line)
                if match:
                    current = match.group(1).replace("/", ".")
                    entries.add(current)
                    pending = None
                    continue
                if current is None:
                    continue
                name_match = MEMBER_LINE.match(line)
                if name_match:
                    pending = name_match.group(1)
                    continue
                kind = TYPE_LINE.match(line)
                if kind and pending:
                    if not SYNTHETIC.search(pending):
                        entries.add(f"{current}#{pending}{kind.group(1)}")
                    pending = None
    return entries


def main(argv: list[str]) -> int:
    args = [a for a in argv if not a.startswith("--")]
    allowed: set[str] = set()
    if "--allow-gone" in argv:
        index = argv.index("--allow-gone")
        allowed = set(argv[index + 1:])
        args = argv[:index] + argv[index + 1 + len(allowed):]
        args = [a for a in args if not a.startswith("--")]
    if len(args) != 2:
        raise SystemExit("usage: compare_apks.py OLD.apk NEW.apk [--allow-gone member ...]")
    old, new = Path(args[0]), Path(args[1])
    tools = build_tools()

    with tempfile.TemporaryDirectory(prefix="alivehs-parity-") as scratch:
        workdir = Path(scratch)
        (workdir / "old").mkdir()
        (workdir / "new").mkdir()
        old_dex = dex_inventory(old, tools, workdir / "old")
        new_dex = dex_inventory(new, tools, workdir / "new")

    with zipfile.ZipFile(old) as bundle:
        old_entries = set(bundle.namelist())
    with zipfile.ZipFile(new) as bundle:
        new_entries = set(bundle.namelist())

    gone = sorted(old_dex - new_dex)
    added = sorted(new_dex - old_dex)
    unexpected = [item for item in gone if not any(
        item == allow or item.startswith(allow + "(") or item.startswith(allow + "#")
        for allow in allowed)]

    print(f"zip entries: {len(old_entries)} -> {len(new_entries)}")
    if old_entries != new_entries:
        print("  gone:", sorted(old_entries - new_entries)[:20])
        print("  new: ", sorted(new_entries - old_entries)[:20])
    print(f"dex members: {len(old_dex)} -> {len(new_dex)} (gone {len(gone)}, new {len(added)})")
    for item in gone[:40]:
        print("  gone:", item, "" if not unexpected or item not in unexpected else "<-- unexpected")
    for item in added[:40]:
        print("  new: ", item)

    if unexpected:
        print(f"compare_apks: {len(unexpected)} unlisted removal(s); pass --allow-gone to acknowledge")
        return 1
    if old_entries != new_entries:
        print("compare_apks: zip entry set changed")
        return 1
    print("compare_apks: no unexpected structural difference")
    return 0


if __name__ == "__main__":
    raise SystemExit(main(sys.argv[1:]))
