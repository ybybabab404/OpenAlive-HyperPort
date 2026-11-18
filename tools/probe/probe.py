#!/usr/bin/env python3
"""性能测量的统一入口。

设计原则（来自 2026-11-18 的测量体系审查）：
- 只测「我们花了多少」：`probe passes` 的 total 是**我们自己**的 GPU 执行时间，
  不含 eglSwapBuffers 等待、不含合成器停摆。系统侧的卡顿不在这套数字里，别混着比。
- 采中位数与 p95，不采均值/最大值：分布右偏，偶发离屏帧会把均值与最大值拉飞。
- 一次 run 就是一份可归档的样本集，`table` 子命令直接产出可以贴进更新日志的对比表。

用法：
  python tools/probe/probe.py run --tag before --secs 20
  python tools/probe/probe.py run --tag after  --secs 20
  python tools/probe/probe.py table before after
  python tools/probe/probe.py env
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

ROOT = Path(__file__).resolve().parents[2]
OUT = ROOT / "out" / "probe"
ADB = os.path.join(os.environ.get("LOCALAPPDATA", ""), "Android", "Sdk", "platform-tools", "adb.exe")
SERIAL = os.environ.get("ALIVEHS_SERIAL", "9eb96e8a")
PROBE_KEY = "alive_hs_probe"

LINE = re.compile(r"probe passes (.+)$")
TOKEN = re.compile(r"([a-z_]+)=(-?[\d.]+)")

PASS_NAMES = ["copy", "lock", "home", "aod", "down", "gh", "gv", "up", "blend"]


def adb(*args, timeout=60):
    return subprocess.run([ADB, "-s", SERIAL, *args], capture_output=True,
                          timeout=timeout, text=True, encoding="utf-8", errors="replace")


def shell(cmd, timeout=60):
    return adb("shell", cmd, timeout=timeout)


def parse(text):
    """把一段 logcat 解析成样本列表；每个样本是一次 60 帧窗口的汇总。"""
    samples = []
    for line in text.splitlines():
        hit = LINE.search(line)
        if not hit:
            continue
        fields = {key: float(value) for key, value in TOKEN.findall(hit.group(1))}
        if "total_med" not in fields:
            continue
        samples.append(fields)
    return samples


def summarize(samples):
    if not samples:
        return {"n_windows": 0}
    medians = [s["total_med"] for s in samples]
    summary = {
        "n_windows": len(samples),
        "frames_per_window": int(samples[0].get("n", 0)),
        "total_med_worst": max(medians),
        "total_med_best": min(medians),
        "total_med_typical": statistics.median(medians),
        "total_p95_worst": max(s.get("total_p95", 0) for s in samples),
        "passes": {},
    }
    for name in PASS_NAMES:
        values = [s[name] for s in samples if name in s]
        if values:
            summary["passes"][name] = round(statistics.median(values), 1)
    # 观测窗口自身是否稳定：中位数的极差占比。>5% 说明状态没稳，结论要打折。
    if summary["total_med_typical"]:
        spread = (summary["total_med_worst"] - summary["total_med_best"]) / summary["total_med_typical"]
        summary["spread"] = round(spread * 100, 1)
    return summary


def cmd_env(_args):
    got = {
        "serial": SERIAL,
        "probe": shell("settings get system %s" % PROBE_KEY).stdout.strip(),
        "renderer": shell("dumpsys SurfaceFlinger --display-id 2>/dev/null | head -1").stdout.strip(),
        "size": shell("wm size").stdout.strip().replace("\n", " "),
    }
    print(json.dumps(got, ensure_ascii=False, indent=2))
    return 0


def cmd_run(args):
    shell("settings put system %s 1" % PROBE_KEY)
    time.sleep(1.2)          # RenderProbe 的开关有 1 秒缓存
    shell("logcat -c")
    if args.wake:
        shell("input keyevent 224")
        time.sleep(2)
    # 驱动：静止时我们本来就不画帧（心跳与看门狗都关掉了），必须制造真实动画才采得到样本。
    # toggle = 开关屏循环，走的是 AOD/锁屏转场这条最贵的路径。
    deadline = time.time() + args.secs
    while time.time() < deadline:
        if args.drive == "toggle":
            shell("input keyevent 26")
            time.sleep(2.5)
            shell("input keyevent 26")
            time.sleep(2.5)
        elif args.drive == "swipe":
            shell("input swipe 600 1500 600 500 250")
            time.sleep(1.0)
        else:
            time.sleep(1.0)
    text = adb("shell", "logcat -d -s AliveClean").stdout
    shell("settings put system %s 0" % PROBE_KEY)

    target = OUT / args.tag
    target.mkdir(parents=True, exist_ok=True)
    (target / "raw.log").write_text(text, encoding="utf-8")
    samples = parse(text)
    summary = summarize(samples)
    summary["tag"] = args.tag
    summary["secs"] = args.secs
    (target / "summary.json").write_text(
        json.dumps(summary, ensure_ascii=False, indent=2), encoding="utf-8")
    print(json.dumps(summary, ensure_ascii=False, indent=2))
    if not samples:
        print("没有采到样本：确认壁纸进程在跑、探针已开启、且该状态确实在渲染。", file=sys.stderr)
        return 2
    return 0


def load(tag):
    path = OUT / tag / "summary.json"
    if not path.exists():
        raise SystemExit("缺少 %s，先跑一次 run" % path)
    return json.loads(path.read_text(encoding="utf-8"))


def cmd_table(args):
    a, b = load(args.a), load(args.b)
    rows = [("total_med", "整帧 GPU 中位数", "total_med_typical", "µs"),
            ("total_p95", "整帧 GPU p95（最差窗口）", "total_p95_worst", "µs")]
    lines = ["| 指标 | %s | %s | Δ | Δ%% |" % (args.a, args.b),
             "|---|---:|---:|---:|---:|"]
    for _key, label, field, unit in rows:
        av, bv = a.get(field), b.get(field)
        if av is None or bv is None:
            continue
        delta = bv - av
        pct = (delta / av * 100) if av else 0.0
        lines.append("| %s (%s) | %.0f | %.0f | %+.0f | %+.1f%% |" % (label, unit, av, bv, delta, pct))
    for name in PASS_NAMES:
        av = a.get("passes", {}).get(name)
        bv = b.get("passes", {}).get(name)
        if not av and not bv:
            continue
        av, bv = av or 0, bv or 0
        pct = ((bv - av) / av * 100) if av else 0.0
        lines.append("| pass `%s` (µs) | %.0f | %.0f | %+.0f | %+.1f%% |" % (name, av, bv, bv - av, pct))
    lines.append("| 观测窗口离散度 | %.1f%% | %.1f%% | | |" % (a.get("spread", 0), b.get("spread", 0)))
    print("\n".join(lines))
    print()
    print("提醒：`spread` > 5% 说明该状态没稳，结论要打折；"
          "单 pass < 20µs、整帧 < 50µs 的差异一律当噪声。")
    return 0


def main():
    parser = argparse.ArgumentParser(prog="probe")
    sub = parser.add_subparsers(dest="cmd", required=True)

    sub.add_parser("env", help="打印设备与探针状态").set_defaults(func=cmd_env)

    run = sub.add_parser("run", help="采一份样本并归档到 out/probe/<tag>/")
    run.add_argument("--tag", required=True)
    run.add_argument("--secs", type=int, default=20)
    run.add_argument("--wake", action="store_true", help="采集前先点亮屏幕")
    run.add_argument("--drive", choices=["none", "swipe", "toggle"], default="toggle",
                     help="采集期间怎么制造动画帧；toggle=开关屏（AOD/锁屏转场，最贵的路径）")
    run.set_defaults(func=cmd_run)

    table = sub.add_parser("table", help="两次 run 的对比表")
    table.add_argument("a")
    table.add_argument("b")
    table.set_defaults(func=cmd_table)

    args = parser.parse_args()
    OUT.mkdir(parents=True, exist_ok=True)
    return args.func(args)


if __name__ == "__main__":
    sys.exit(main())
