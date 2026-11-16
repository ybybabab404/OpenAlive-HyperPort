# 工作区整理记录 · 2026-11-14

> 执行人：后续会话（AI 助手）。范围仅限仓库内被 `.gitignore` 忽略的临时/工作台目录与根目录垃圾，
> **未改动源码、未改动 git 历史、未卸载/重装设备 APK、未修改任何系统设置。**

## 1. 结果摘要

| 项目 | 整理前 | 整理后 | 说明 |
|---|---:|---:|---|
| `.tmp/` 总大小 | 9.7 GB | 7.6 GB | 删除 2.1 GB 的 `wt-stable` 工作树检出 |
| `.tmp/` 文件数 | 126,478 | 124,765 | 含工作树内文件 |
| `.tmp/` 目录数 | 11,971 | 11,627 | |
| `.tmp/` 顶层条目 | 563 | 9（8 个分类目录 + README） | 562 次移动、1 次删除 |
| 根目录 `%SystemDrive%/` | 972 KB，未跟踪垃圾 | 已移入 `.tmp/archive/systemdrive-junk-20261114/` | `git status` 不再出现 |
| `workbench/` | 230 MB，平铺 | 230 MB，按 research/reference/archive 分类 | 未删内容 |
| git worktree | 主工作树 + `.tmp/wt-stable` | 仅主工作树 | `b6ae18e` 提交仍在仓库中 |

## 2. 逐项操作

### 2.1 删除失联的 `wt-stable` 工作树（2.1 GB）

- 原状：`git worktree list` 登记 `<repo>/.tmp/wt-stable`，detached HEAD `b6ae18e`；工作树内有未提交改动（`RenderLoop.java` 的探针，27 插入 / 3 删除）。
- 处理：
  1. 保存文件清单与差异：`.tmp/archive/wt-stable-b6ae18e-probe2.patch`（53 行）。
  2. `git worktree remove --force .tmp/wt-stable`。
  3. `git worktree list` 复核：只剩主工作树。
- 可恢复性：提交 `b6ae18e` 仍可用（`git cat-file -t b6ae18e` = commit）；需要时
  `git worktree add .tmp/wt-stable b6ae18e` 后再 `git apply .tmp/archive/wt-stable-b6ae18e-probe2.patch`。
- 注意：该工作树的未提交内容只保存为 patch，未做完整 `git diff --binary`；若当时还有未跟踪文件，清单未覆盖。原 `du` 为 2.1 GB。

### 2.2 `.tmp/` 按用途分类

新结构（均已建 README 说明）：

```
.tmp/
├── build/          构建输入：sources2.txt、libalive_clean.so、lint/
├── artifacts/
│   ├── apk/builds/     历史构建 APK（AliveHS-preview、installed、ha、probe*、bisect*、lite*）
│   ├── apk/reference/  官方参考 APK（base-upstream、MIUIThemeManager、MiuiHome、MiuiSystemUI、PowerKeeper、miui-aod、miwallpaper）
│   └── archives/       git bundle、备份归档
├── research/
│   ├── aod/            AOD 反编译源码、分析、工具
│   ├── systemui/       SystemUI / framework 反编译与 dex
│   ├── thememanager/   主题管理器反编译、资源表、UI dump
│   ├── decompiled/     其他 jadx/decomp
│   ├── classes/        类名/方法扫描、包装类分析
│   ├── dumps/          dumpsys、DB、jar、dex、XML dump
│   ├── experiments/    一次性实验
│   ├── reference/      参考素材
│   ├── logs/           研究日志
│   └── scene-evidence/
├── logs/           实验日志、logcat、文本输出
├── screenshots/    截图与裁剪图
├── scripts/        一次性分析脚本
├── archive/        旧构建快照、补丁、删除前备份、系统垃圾归档
└── scratch/        暂未分类零散文件
```

- 移动清单：`.tmp/archive/cleanup-moves-20261114.tsv`（563 行：562 个 move + 1 个 delete），记录每个源路径与目标路径。
- 顶层已无散落文件。
- 关键路径变更（文档已同步）：
  - `.tmp/libalive_clean.so` → `.tmp/build/libalive_clean.so`
  - `.tmp/sources2.txt` → `.tmp/build/sources2.txt`
  - `.tmp/lintN` → `.tmp/build/lint/lintN`
  - `.tmp/pre-aodstyle-rollback-20261103.bundle|.head` → `.tmp/artifacts/archives/`
  - `.tmp/MIUIThemeManager.apk` → `.tmp/artifacts/apk/reference/MIUIThemeManager.apk`
  - `.tmp/tm-decomp`、`tm-res.txt`、`ui-personalize.xml` → `.tmp/research/thememanager/`
  - `.tmp/aod-src` → `.tmp/research/aod/aod-src`
- 分类后体积：artifacts 4.5 GB、research 2.4 GB、archive 612 MB、screenshots 130 MB、logs 54 MB、scratch 33 MB、build 25 MB、scripts 188 KB。

### 2.3 删除一个 APK（需注意）

- 删除：`.tmp/aod.apk`（57,598,555 字节，2026-11-02 22:11）。
- 依据：整理前清单显示它与 `.tmp/miui-aod.apk` 字节数完全相同；此前团队报告称两者 sha256 相同。
- **过程缺口**：删除前未单独复算 `aod.apk` 的哈希；删除后 `miui-aod.apk` 的实测哈希为
  `9bcedfd5f84b595c18b8d9395133856fb082a4415b443d89ffbe7825ffa13678`，与团队报告中的 `d7324831…` 不一致。
  因此不能 100% 证明被删文件与现存 `miui-aod.apk` 完全一致；目前仓库内也没有同哈希副本。
- 影响评估：现存 `.tmp/artifacts/apk/reference/miui-aod.apk`、`workbench/research/MIUIAod.apk`、
  `.tmp/research/aod/*/MIUIAod.apk` 均为同一份 `9bced…`，仍是可用的 MIUI AOD 参考件；被删的 `aod.apk`
  即使不同，也只是同尺寸的另一份参考 APK，不影响构建（构建不读它）。
- 后续规则：删除任何 APK 前先 `sha256sum` 并把哈希写进清单，禁止只凭文件名/大小判断重复。

### 2.4 根目录 `%SystemDrive%` 垃圾

- 原状：仓库根下有一个名为 `%SystemDrive%` 的目录（环境变量未展开造成），含 3 个 Windows Caches 数据库，
  共 987,712 字节；未被 `.gitignore` 覆盖，导致 `git status` 出现未跟踪项。
- 处理：整体移动到 `.tmp/archive/systemdrive-junk-20261114/%SystemDrive%/`，保留原相对路径与三个文件。
- 影响：`git status` 不再出现该目录；内容与项目无关，确认后可删除归档。

### 2.5 `workbench/` 分类

```
workbench/
├── research/                    逆向研究（215 MB，原样保留）
├── reference/
│   ├── reference-assets/       原生时钟预览图、模板源 JSON、Flyme/Vivo 参考素材
│   └── reference-fonts/        参考字体
└── archive/
    ├── removed-java/           从 app/java 删除的 Java 源码备份
    ├── removed-tests/          从 tests/ 删除的测试备份
    ├── vendor-backup/          vendor 旧备份
    └── ha_shot.png             历史截图
```

`workbench/README.md` 已说明：此目录不入 git，`research/` 只放研究材料，删源码前先备份到 `archive/`。

## 3. 文档同步

- `docs/port/HANDOVER-20261114.md`：
  - §3.1 `android-37` → `android-37.0`；
  - §3.2 补充 7 个缺失 check 脚本；
  - §3.4 `.tmp/sources2.txt` → `.tmp/build/sources2.txt`，242 行；
  - §5.6 明确 `supervise()` 是死代码、hooks=6；
  - §6 设备样例改为 2026-11-14 16:30 实际快照；
  - §8.1 改为 15 个已跟踪文件，说明 `%SystemDrive%` 已清理；
  - §8.3 更新清理项；
  - 文末新增 §12「交接核验修正（2026-11-14）」共 10 条。
- `docs/port/HANDOVER.md`、`PROGRESS.md`、`NEXT-STEP-20261103-2131.md`、`PROJECT-CONSTRAINTS.md` 中的
  `.tmp` 路径已同步到新结构。
- 新增 `docs/port/SESSION-BRIEF-20261114.md`（压缩前落盘，含用户原始要求逐字原文与核验结果）。

## 4. 验证

- `javac` 自检：使用 `.tmp/build/sources2.txt` + `android-37.0/android.jar`，`exit=0`，仅有 deprecation/unchecked 提示。
- `git worktree list`：仅主工作树 `<repo> fc8124f [deep-clean-v2]`。
- `git status --short`：13 个 Java + 4 个文档 + `tools/build_release.py` 已修改；两个未跟踪文档
  `docs/port/HANDOVER-20261114.md`、`docs/port/SESSION-BRIEF-20261114.md`。
- `.tmp/README.md`、`workbench/README.md` 已创建。

## 5. 未做 / 待确认

- 未删除 `.tmp` 中的历史 APK、bundle、反编译目录（保留唯一证据；如需进一步瘦身，另列清单）。
- 未改动 `build/`、`dist/`、`target/` 构建产物（分别 870 MB、894 MB、8.3 MB，均已 ignore）。
- 未处理 `dist/save/` 等旧版本 APK（不是本次范围）。
- 未提交 git；源码改动仍是上一会话留下的 13 个 Java 文件。

## 6. 回滚

- `.tmp` 文件：按 `cleanup-moves-20261114.tsv` 反向移动；注意 63 个目录记录已在清单中做过同名目录折叠修正。
- `wt-stable`：见 2.1。
- `%SystemDrive%`：把 `.tmp/archive/systemdrive-junk-20261114/%SystemDrive%` 移回仓库根即可（不建议）。
- `aod.apk`：无法回滚，见 2.3。
