"""Validate the preserved Xiaomi UI bundle and, when present, the built-in scene packs.

Two levels of checking, because the pack chunks are large and are not always bundled in a checkout:

* always: `ui.apk` against `vendor/xiaomi/ui-manifest.json` and `ui.sha256`, plus the internal
  consistency of `catalog.json` / `packs.json` (ids, per-pack byte totals, chunk digests, storage
  sums). This catches a truncated or hand-edited manifest without decompressing anything;
* when `app/assets/xiaomi/packs/` exists: decompress every referenced chunk and verify its digest,
  size and that nothing unreferenced is lying around;
* when the `*.banner` files exist: check each is non-empty. Their absence means the build simply
  does not ship the built-in pack previews, which is a packaging decision, not a defect.
"""
from pathlib import Path
import hashlib
import json
import lzma
import re
import zipfile

ROOT = Path(__file__).resolve().parents[1]
ASSETS = ROOT / "app/assets/xiaomi"
MANIFEST = ROOT / "vendor/xiaomi/ui-manifest.json"
DIGEST = re.compile(r"^[a-f0-9]{64}$")


def sha(data: bytes) -> str:
    return hashlib.sha256(data).hexdigest()


def verify_ui_bundle() -> int:
    manifest = json.loads(MANIFEST.read_text(encoding="utf-8"))
    digest = sha((ASSETS / "ui.apk").read_bytes())
    assert digest == manifest["bundle_sha256"] == (ASSETS / "ui.sha256").read_text().strip()
    with zipfile.ZipFile(ASSETS / "ui.apk") as apk:
        expected = {entry["path"]: entry["sha256"] for entry in manifest["entries"]}
        assert len(expected) == len(apk.namelist())
        assert set(apk.namelist()) == set(expected)
        for name, checksum in expected.items():
            assert sha(apk.read(name)) == checksum, name
        assert "classes.dex" in expected and "resources.arsc" in expected
        assert not any(name.startswith("lib/") for name in expected)
    return len(expected)


def verify_manifests() -> tuple[list[dict], dict]:
    catalog = json.loads((ASSETS / "catalog.json").read_text(encoding="utf-8"))
    assert [pack["id"] for pack in catalog] == [
        "moon", "snowmountain", "geometry", "saturn", "earth", "mars"]
    bundle = json.loads((ASSETS / "packs.json").read_text(encoding="utf-8"))
    assert bundle["format"] == 1 and bundle["max_chunk_bytes"] == 4 * 1024 * 1024
    assert set(bundle["packs"]) == {pack["id"] for pack in catalog}

    referenced: dict[str, int] = {}
    references = 0
    total = 0
    for pack in catalog:
        assert DIGEST.match(pack["sha256"]) and pack["bytes"] > 0
        assert pack["lands"] == (5 if pack["id"] in ("moon", "earth", "mars") else 1)
        recipe = bundle["packs"][pack["id"]]
        assert recipe["sha256"] == pack["sha256"] and recipe["bytes"] == pack["bytes"]
        size = 0
        for key, length in recipe["chunks"]:
            assert DIGEST.match(key), pack["id"]
            assert 0 < length <= bundle["max_chunk_bytes"], pack["id"]
            size += length
            references += 1
            referenced[key] = length
        assert size == pack["bytes"], pack["id"]
        total += size

    storage = bundle["storage"]
    assert storage["original_apk_bytes"] == total
    assert storage["unique_chunks"] == len(referenced)
    assert storage["chunk_references"] == references
    return catalog, bundle


def verify_pack_chunks(bundle: dict) -> int | None:
    folder = ASSETS / "packs"
    if not folder.is_dir():
        return None
    referenced = {key for recipe in bundle["packs"].values() for key, _ in recipe["chunks"]}
    stored = {path.name for path in folder.iterdir()}
    assert stored == {key + ".xz" for key in referenced}
    for recipe in bundle["packs"].values():
        for key, length in recipe["chunks"]:
            data = lzma.decompress((folder / (key + ".xz")).read_bytes(), memlimit=32 * 1024 * 1024)
            assert len(data) == length and sha(data) == key, key
    compressed = sum((folder / (key + ".xz")).stat().st_size for key in referenced)
    assert compressed == bundle["storage"]["compressed_unique_bytes"]
    return compressed


def verify_banners(catalog: list[dict]) -> int:
    banners = [ASSETS / (pack["id"] + ".banner") for pack in catalog]
    present = [banner for banner in banners if banner.is_file()]
    if not present:
        return 0
    assert len(present) == len(banners), "some pack banners are missing: " + ", ".join(
        banner.name for banner in banners if not banner.is_file())
    for banner in present:
        assert banner.stat().st_size > 0, banner.name
    return len(present)


def main() -> int:
    entries = verify_ui_bundle()
    catalog, bundle = verify_manifests()
    compressed = verify_pack_chunks(bundle)
    banners = verify_banners(catalog)
    print(f"xiaomi assets: ui.apk entries {entries}, packs {len(catalog)}, banners {banners}")
    if compressed is None:
        print("xiaomi assets: app/assets/xiaomi/packs/ is not bundled in this tree, so the pack "
              "chunks could not be decompressed and verified; the manifests were checked on their own")
    else:
        print(f"xiaomi assets: {bundle['storage']['original_apk_bytes']} bytes of packs "
              f"stored as {compressed} bytes of chunks")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
