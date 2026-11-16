"""Offline contract checks for the pure-logic scene classes; no device, no Android SDK.

Compiles a small, Android-free subset of `app/java` together with `tests/scene_logic` using the
host JDK and runs the assertions. Those classes are the state machines the wallpapers depend on
(AOD region maths, AOD speed ramp, scene bookkeeping, transition clock), so a regression here is
cheap to catch without flashing a device.

Exit status is non-zero on failure, which is what `tools/build_release.py` relies on.
"""
from pathlib import Path
import os
import subprocess

ROOT = Path(__file__).resolve().parents[1]
OUT = ROOT / "build/checks/scene-logic"

# Only classes with no `android.*` dependency can be compiled against a plain JDK.
SOURCES = [
    "AodRegion.java",
    "AodMotionWindow.java",
    "SceneState.java",
    "WallpaperTransitionTime.java",
]
TEST_MAIN = "org.aliveclean.SceneLogicTest"


def jdk_bin() -> Path:
    candidates = []
    if os.environ.get("JAVA_HOME"):
        candidates.append(Path(os.environ["JAVA_HOME"]) / "bin")
    candidates += [
        Path("C:/Program Files/Java/jdk-17/bin"),
        Path("C:/Program Files/Java/jdk-21/bin"),
        Path("C:/Program Files/Eclipse Adoptium/jdk-17/bin"),
        Path("/usr/lib/jvm/java-17-openjdk-amd64/bin"),
    ]
    for candidate in candidates:
        if (candidate / ("javac" + (".exe" if os.name == "nt" else ""))).exists():
            return candidate
    raise SystemExit("check_scene_logic: JDK not found; set JAVA_HOME")


def main() -> int:
    jdk = jdk_bin()
    exe = ".exe" if os.name == "nt" else ""
    OUT.mkdir(parents=True, exist_ok=True)
    app = ROOT / "app/java/org/aliveclean"
    sources = [app / name for name in SOURCES]
    missing = [str(path) for path in sources if not path.is_file()]
    if missing:
        raise SystemExit("check_scene_logic: missing source(s): " + ", ".join(missing))
    sources += sorted((ROOT / "tests/scene_logic").glob("*.java"))
    subprocess.run([str(jdk / ("javac" + exe)), "-encoding", "UTF-8", "-d", str(OUT),
                    *map(str, sources)], check=True)
    subprocess.run([str(jdk / ("java" + exe)), "-cp", str(OUT), TEST_MAIN], check=True)
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
