#!/usr/bin/env python3
"""内存基线采集：按场景量 org.aliveclean 的显存与进程占用。

和 tools/probe/probe.py 是一对——那个量"我们画一帧花多少时间"，这个量"我们占多少内存"。
两个都跑一遍，重构前后才有可比数字；只改代码不测，结论只能靠感觉。

设计原则（与 probe.py 一致）：
- 场景驱动靠 Activity 名 + 界面文本定位，不硬编码坐标。坐标随布局漂移，
  实测已经踩过（首页三个入口的 y 在不同状态下差一百多像素）。
- 每个场景采 N 次取中位数：单次读数会被 GC/系统回收打断，波动很大。
- 一次 run 就是一份可归档样本，`table` 子命令产出可对比的表。

用法：
  python tools/mem.py run --tag before
  python tools/mem.py run --tag after
  python tools/mem.py table before after

注意：`editor`/`app_home` 场景会真的去点界面上的「Alive 壁纸」入口，
跑之前请确保手机停在可操作的桌面，且没有别的窗口盖住。
"""
import argparse
import json
import os
import re
import statistics
import subprocess
import sys
import time
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
OUT = ROOT / "out" / "mem"
ADB = os.path.join(os.environ.get("LOCALAPPDATA", ""), "Android", "Sdk", "platform-tools", "adb.exe")
SERIAL = os.environ.get("ALIVEHS_SERIAL", "9eb96e8a")
PKG = "org.aliveclean"

# dumpsys meminfo 里要收的项。EGL mtrack 才是我们自己分配的显存，
# Graphics 是进程整体图形占用（含框架给窗口缓冲），两个都要看。
METRICS = ["EGL mtrack", "GL mtrack", "Graphics", "Java Heap", "Native Heap",
           "TOTAL PSS", "TOTAL RSS"]


def adb(*args, timeout=90):
    return subprocess.run([ADB, "-s", SERIAL, *args], capture_output=True,
                          timeout=timeout, text=True, encoding="utf-8", errors="replace")


def shell(cmd, timeout=90):
    return adb("shell", cmd, timeout=timeout)


def key(code):
    shell("input keyevent %s" % code)


def focus():
    """当前前台窗口的 activity，形如 org.aliveclean/.MainActivity；取不到返回 None。"""
    out = shell("dumpsys window | grep -m1 mCurrentFocus").stdout
    hit = re.search(r"([\w.]+)/([\w.$]+)\}", out)
    return "%s/%s" % (hit.group(1), hit.group(2)) if hit else None


def start(activity):
    shell("am start -n %s/%s" % (PKG, activity))


def ui_nodes():
    """当前界面的 (text, cx, cy) 列表。dump 失败返回空。"""
    shell("uiautomator dump /sdcard/tmp/mem_ui.xml", timeout=60)
    out = adb("exec-out", "cat /sdcard/tmp/mem_ui.xml", timeout=60).stdout
    nodes = []
    for tag in re.findall(r"<node[^>]*>", out):
        text = re.search(r'text="([^"]*)"', tag)
        bounds = re.search(r'bounds="\[(\d+),(\d+)\]\[(\d+),(\d+)\]"', tag)
        if not text or not bounds:
            continue
        x1, y1, x2, y2 = map(int, bounds.groups())
        nodes.append((text.group(1), (x1 + x2) // 2, (y1 + y2) // 2))
    return nodes


def tap_text(label, timeout=20, fallback=None):
    """等界面上出现该文本再点它。

    界面上有实时 GL 预览时永远不会 idle，uiautomator dump 会一直失败——
    这种情况允许调用方给一个坐标兜底，宁可点得糙一点，也不要采不到数。
    """
    deadline = time.time() + timeout
    while time.time() < deadline:
        for text, cx, cy in ui_nodes():
            if text.strip() == label:
                shell("input tap %d %d" % (cx, cy))
                return True
        time.sleep(1)
    if fallback:
        shell("input tap %d %d" % fallback)
        return True
    return False


def meminfo():
    out = shell("dumpsys meminfo %s" % PKG).stdout
    values = {}
    for name in METRICS:
        # 必须锚在行首：否则 "GL mtrack" 会匹配到 "EGL mtrack" 里。
        hit = re.search(r"(?m)^\s*" + re.escape(name) + r":?\s+(\d+)", out)
        if hit:
            values[name] = int(hit.group(1))
    # oom_adj 决定这块内存会被谁先回收，也是"会不会被墓碑盯上"的直接线索。
    procs = shell("ps -A -o PID,PIDNAME 2>/dev/null | grep %s" % PKG).stdout
    values["processes"] = len([l for l in procs.splitlines() if PKG in l])
    return values


def sample(times=3, gap=1.0):
    """多次采样取中位数：单次读数会被 GC 或系统回收打断。"""
    runs = []
    for i in range(times):
        runs.append(meminfo())
        if i + 1 < times:
            time.sleep(gap)
    merged = {}
    for name in METRICS + ["processes"]:
        values = [r[name] for r in runs if name in r]
        if values:
            merged[name] = int(statistics.median(values))
    return merged


# ---- 场景 ------------------------------------------------------------------

def scene_desktop():
    key("KEYCODE_HOME")
    time.sleep(8)


def scene_app_home():
    start(".HomeActivity")
    time.sleep(9)
    if focus() and not focus().endswith("HomeActivity"):
        # 首页可能被编辑页盖着（同一个任务栈）：退回去再启动。
        key("KEYCODE_BACK")
        time.sleep(2)
        start(".HomeActivity")
        time.sleep(8)


def scene_editor():
    start(".HomeActivity")
    time.sleep(6)
    if not tap_text("Alive 壁纸", fallback=(640, 1713)):
        raise RuntimeError("界面上找不到「Alive 壁纸」入口")
    time.sleep(9)


def scene_editor_background():
    key("KEYCODE_HOME")
    time.sleep(8)


def scene_pure_service():
    """杀掉进程让系统重绑壁纸服务，得到"只有壁纸服务"的占用。

    这是"进后台把 UI 全回收"的理论下限：Activity 销毁只能回收 Java 对象，
    类加载与 ART/JIT 那部分内存只有进程重启才还回去。两个数一减，就是 UI 残留。
    注意：force-stop 会把壁纸组件撤掉，所以这里用 kill -9（组件保留，系统会重绑）。
    """
    pid = shell("pidof %s" % PKG).stdout.strip().split()
    if not pid:
        raise RuntimeError("进程没在跑")
    shell("su 0 kill -9 %s" % pid[0])
    for _ in range(12):
        time.sleep(2)
        if shell("pidof %s" % PKG).stdout.strip():
            break
    time.sleep(8)
    if PKG not in shell("dumpsys wallpaper | grep -m1 mWallpaperComponent").stdout:
        raise RuntimeError("壁纸组件没恢复，跳过")


SCENES = [
    ("desktop", "桌面（有 UI 残留）", scene_desktop),
    ("app_home", "App 首页（三张预览 + 壁纸）", scene_app_home),
    ("editor", "编辑页前台", scene_editor),
    ("editor_background", "编辑页进后台 8 秒", scene_editor_background),
    # 放最后：它会杀掉进程，前面那些场景必须已经采完。
    ("pure_service", "只有壁纸服务（kill 后系统重绑）", scene_pure_service),
]


def cmd_run(args):
    OUT.mkdir(parents=True, exist_ok=True)
    if focus() is None:
        print("拿不到前台窗口，设备没连上？", file=sys.stderr)
        return 2
    result = {"tag": args.tag, "serial": SERIAL, "time": time.strftime("%Y-%m-%d %H:%M:%S"),
              "scenes": {}}
    for name, label, setup in SCENES:
        try:
            setup()
        except Exception as error:
            print("场景 %s 跳过：%s" % (name, error), file=sys.stderr)
            continue
        values = sample(args.times)
        result["scenes"][name] = {"label": label, "focus": focus(), **values}
        print("%-20s %s" % (name, _line(values)), flush=True)
    path = OUT / ("mem-%s.json" % args.tag)
    path.write_text(json.dumps(result, ensure_ascii=False, indent=2), encoding="utf-8")
    print("\n已保存 %s" % path)
    return 0


def _line(values):
    parts = []
    for name in METRICS:
        if name in values:
            parts.append("%s=%.1fMB" % (name, values[name] / 1024.0))
    return "  ".join(parts)


def load(tag):
    path = OUT / ("mem-%s.json" % tag)
    if not path.is_file():
        raise SystemExit("找不到基线 %s（先跑 run --tag %s）" % (path, tag))
    return json.loads(path.read_text(encoding="utf-8"))


def cmd_table(args):
    before, after = load(args.before), load(args.after)
    print("%-20s %-14s %10s %10s %10s" % ("场景", "指标", before["tag"], after["tag"], "变化"))
    for name, _label, _setup in SCENES:
        a = before["scenes"].get(name, {})
        b = after["scenes"].get(name, {})
        if not a or not b:
            continue
        for metric in METRICS:
            if metric not in a or metric not in b:
                continue
            va, vb = a[metric] / 1024.0, b[metric] / 1024.0
            delta = vb - va
            pct = ("%+.0f%%" % (delta / va * 100)) if va else "-"
            print("%-20s %-14s %9.1fM %9.1fM %9.1fM %s" % (name, metric, va, vb, delta, pct))
    return 0


def main():
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    sub = parser.add_subparsers(dest="cmd")
    run = sub.add_parser("run", help="按场景采一份内存基线")
    run.add_argument("--tag", default="now")
    run.add_argument("--times", type=int, default=3, help="每场景采样次数（取中位数）")
    run.set_defaults(func=cmd_run)
    table = sub.add_parser("table", help="对比两份基线")
    table.add_argument("before")
    table.add_argument("after")
    table.set_defaults(func=cmd_table)
    args = parser.parse_args()
    if not getattr(args, "func", None):
        parser.print_help()
        return 1
    return args.func(args)


if __name__ == "__main__":
    sys.exit(main())
