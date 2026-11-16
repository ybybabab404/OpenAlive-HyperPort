"""Static sanity checks for the inputs the build leans on; no device, no SDK.

Three classes of mistake this catches, all of which have happened in this repository before:

* a class renamed or removed while `app/AndroidManifest.xml` still points at it (the build passes,
  the component simply never starts on the device);
* a `tools/check_*.py` that no longer parses, which would fail the build only after a full compile;
* a vendored build input that is present but does not match the checksum pinned in
  `tools/build_release.py` (or is missing entirely).

Everything here is read-only.
"""
from __future__ import annotations

import hashlib
import py_compile
import sys
import xml.etree.ElementTree as ET
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
APP = ROOT / "app/java/org/aliveclean"
MANIFEST = ROOT / "app/AndroidManifest.xml"
ANDROID_NS = "{http://schemas.android.com/apk/res/android}"

# Files the build reads; the entry is (path, expected sha256 or None).
VENDOR_INPUTS = [
    ("vendor/libxposed/classes.jar", None),
    ("vendor/xz/xz-1.12-sources.jar", "pinned"),
    ("vendor/flyme/editor-ui.dex", None),
    ("vendor/flyme/res", None),
    ("vendor/xiaomi/ui-manifest.json", None),
]


def fail(message: str) -> None:
    raise SystemExit("check_imports: " + message)


def manifest_classes() -> list[str]:
    """Component class names declared in the manifest, resolved relative to our package."""
    root = ET.parse(MANIFEST).getroot()
    names = []
    for element in root.iter():
        name = element.attrib.get(ANDROID_NS + "name")
        if name and name.startswith("."):
            names.append(name[1:])
    return sorted(set(names))


def java_sources() -> dict[str, Path]:
    return {path.stem: path for path in APP.rglob("*.java")}


def check_manifest_classes() -> int:
    sources = java_sources()
    checked = 0
    for name in manifest_classes():
        outer, _, inner = name.partition("$")
        source = sources.get(outer)
        if source is None:
            fail("manifest references a missing class: " + name)
        if inner:
            text = source.read_text(encoding="utf-8")
            # Nested components are declared as `static final class Name`.
            if ("class " + inner) not in text:
                fail(f"manifest references {name} but {source.name} does not declare class {inner}")
        checked += 1
    return checked


def check_scripts() -> int:
    scripts = sorted((ROOT / "tools").glob("check_*.py"))
    if not scripts:
        fail("no tools/check_*.py found")
    cache = ROOT / "build/checks/__pycache__"
    cache.mkdir(parents=True, exist_ok=True)
    for script in scripts:
        try:
            py_compile.compile(str(script), cfile=str(cache / (script.stem + ".pyc")), doraise=True)
        except py_compile.PyCompileError as error:
            fail(f"{script.name} does not compile: {error.msg}")
    return len(scripts)


def check_vendor_inputs() -> int:
    sys.path.insert(0, str(ROOT / "tools"))
    import build_release  # noqa: E402  (needs tools/ on the path)

    checked = 0
    for relative, expectation in VENDOR_INPUTS:
        path = ROOT / relative
        if not path.exists():
            fail("missing vendored build input: " + relative)
        if expectation == "pinned":
            digest = hashlib.sha256(path.read_bytes()).hexdigest()
            if digest != build_release.XZ_SOURCES_SHA256:
                fail(f"{relative} sha256 {digest} does not match the pinned value")
        checked += 1
    return checked


def main() -> int:
    classes = check_manifest_classes()
    scripts = check_scripts()
    inputs = check_vendor_inputs()
    print(f"imports verified: {classes} manifest components, {scripts} check scripts, "
          f"{inputs} vendored inputs")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
