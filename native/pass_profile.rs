//! 诊断用「按 pass 拆分的 GPU 计时」。
//!
//! `GL_EXT_disjoint_timer_query` 同时只允许一个活动查询，所以不能嵌套；这里用
//! 「多个查询组轮转」的办法：一帧用一组 query（每个 pass 一个），查询还没回来就换下一组，
//! 追到某一组全部可用时才读结果——**任何时候都不阻塞渲染线程**。
//!
//! 只在探针（`alive_hs_probe`）打开时启用，正式路径一个 GL 调用都不产生。
use std::ffi::{c_char, c_void};
use std::ptr;

type Ptr = *mut c_void;
type GetProcAddress = unsafe extern "C" fn(*const c_char) -> Ptr;
type BeginQuery = unsafe extern "C" fn(u32, u32);
type EndQuery = unsafe extern "C" fn(u32);
type GetQueryObjectuiv = unsafe extern "C" fn(u32, u32, *mut u32);

extern "C" {
    fn dlopen(path: *const c_char, flags: i32) -> Ptr;
    fn dlsym(handle: Ptr, symbol: *const c_char) -> Ptr;
    fn glGenQueries(n: i32, ids: *mut u32);
    fn glGetError() -> u32;
    fn glGetIntegerv(pname: u32, value: *mut i32);
    fn __android_log_print(prio: i32, tag: *const c_char, fmt: *const c_char, ...) -> i32;
}

const TIME_ELAPSED_EXT: u32 = 0x88BF;
const QUERY_RESULT: u32 = 0x8866;
const QUERY_RESULT_AVAILABLE: u32 = 0x8867;
const GPU_DISJOINT_EXT: u32 = 0x8FBB;
const GROUPS: usize = 4;
/// copy / lock / home / aod / down / gaussianH / gaussianV / up / blend
pub const PASSES: usize = 9;
pub const LABELS: [&str; PASSES] = ["copy", "lock", "home", "aod", "down", "gh", "gv", "up", "blend"];
const TTL_FRAMES: u32 = 240;

struct State {
    ready: bool,
    failed: bool,
    enabled: bool,
    begin: Option<BeginQuery>,
    end: Option<EndQuery>,
    get: Option<GetQueryObjectuiv>,
    ids: [[u32; PASSES]; GROUPS],
    opened: [[bool; PASSES]; GROUPS],
    /// 本组里真正跑过的 pass（位掩码）。没跑的 query 永远不 available，不能参与汇总。
    ran: [u32; GROUPS],
    /// 每组的出生帧号（用于 TTL 回收）。
    born: [u32; GROUPS],
    /// 本帧没抢到空闲组：此时 pass_begin/end 必须全部空转，绝不能沿用上一帧的 group。
    no_group: bool,
    /// 已完成样本的整帧总耗时（µs），用来算中位数而不是逐条打日志。
    samples: [u32; 64],
    sample_count: usize,
    /// 各 pass 在样本窗内的累计耗时（ns），报的时候除以样本数就是均值。
    sums: [u64; PASSES],
    /// 各 pass 在样本窗内真正跑过的帧数：用来判断缓存命中率（跑 0 次 = 完全命中）。
    hits: [u32; PASSES],
    group: usize,
    used: [bool; GROUPS],
    frames: u32,
}

static mut STATE: State = State {
    ready: false,
    failed: false,
    enabled: false,
    begin: None,
    end: None,
    get: None,
    ids: [[0; PASSES]; GROUPS],
    opened: [[false; PASSES]; GROUPS],
    ran: [0; GROUPS],
    born: [0; GROUPS],
    no_group: false,
    samples: [0; 64],
    sample_count: 0,
    sums: [0; PASSES],
    hits: [0; PASSES],
    group: 0,
    used: [false; GROUPS],
    frames: 0,
};

unsafe fn state_raw() -> &'static mut State {
    &mut *ptr::addr_of_mut!(STATE)
}

fn state() -> &'static mut State {
    unsafe { state_raw() }
}

unsafe fn resolve() -> bool {
    let s = state();
    if s.ready {
        return !s.failed;
    }
    s.ready = true;
    let handle = dlopen(b"libEGL.so\0".as_ptr() as *const c_char, 2);
    if handle.is_null() {
        s.failed = true;
        return false;
    }
    let raw = dlsym(handle, b"eglGetProcAddress\0".as_ptr() as *const c_char);
    if raw.is_null() {
        s.failed = true;
        return false;
    }
    let get_proc: GetProcAddress = std::mem::transmute(raw);
    let load = |text: &[u8]| get_proc(text.as_ptr() as *const c_char);
    let begin = load(b"glBeginQueryEXT\0");
    let end = load(b"glEndQueryEXT\0");
    let get = load(b"glGetQueryObjectuivEXT\0");
    if begin.is_null() || end.is_null() || get.is_null() {
        s.failed = true;
        return false;
    }
    s.begin = Some(std::mem::transmute(begin));
    s.end = Some(std::mem::transmute(end));
    s.get = Some(std::mem::transmute(get));
    true
}

/// 由 Java 侧开关（只在探针开启时打开）。
#[no_mangle]
pub unsafe extern "system" fn Java_org_aliveclean_NativeGpuTimer_passes(
    _: *mut c_void,
    _: *mut c_void,
    enable: i32,
) {
    let s = state();
    if enable == 0 {
        s.enabled = false;
        return;
    }
    if !resolve() {
        return;
    }
    if s.ids[0][0] == 0 {
        while glGetError() != 0 {}
        for group in 0..GROUPS {
            glGenQueries(PASSES as i32, s.ids[group].as_mut_ptr());
            if glGetError() != 0 {
                return;
            }
        }
    }
    s.enabled = true;
}

/// 一帧开始：挑一个没人用的组；没有就这帧不测（绝不等待）。
pub fn frame_begin() {
    let s = unsafe { state() };
    if !s.enabled || s.begin.is_none() {
        return;
    }
    let mut picked = usize::MAX;
    for step in 0..GROUPS {
        let index = (s.group + step) % GROUPS;
        if !s.used[index] {
            picked = index;
            break;
        }
    }
    if picked == usize::MAX {
        // 以前这里直接 return，s.group 还留着上一帧的值：后续 pass_begin 会把 query
        // begin 到「上一帧已经结束」的 id 上，覆盖结果并打乱 ran 掩码，产出静默的错误数据。
        s.no_group = true;
        return;
    }
    s.no_group = false;
    s.group = picked;
    s.born[picked] = s.frames;
    s.opened[picked] = [false; PASSES];
    s.ran[picked] = 0;
    unsafe{s.begin.unwrap()(TIME_ELAPSED_EXT, s.ids[picked][0]);} // 占位：立刻结束，保持配对
    unsafe{s.end.unwrap()(TIME_ELAPSED_EXT);}
    s.used[picked] = true;
}

pub fn pass_begin(pass: usize) {
    let s = unsafe { state() };
    if !s.enabled || s.no_group || !s.used[s.group] || pass >= PASSES {
        return;
    }
    let begin = match s.begin {
        Some(f) => f,
        None => return,
    };
    unsafe{while glGetError() != 0 {}}
    unsafe{begin(TIME_ELAPSED_EXT, s.ids[s.group][pass])};
    if unsafe{glGetError()} == 0 {
        s.opened[s.group][pass] = true;
        s.ran[s.group] |= 1 << pass;
    }
}

pub fn pass_end(pass: usize) {
    let s = unsafe { state() };
    if !s.enabled || s.no_group || pass >= PASSES || !s.opened[s.group][pass] {
        return;
    }
    if let Some(end) = s.end {
        unsafe{end(TIME_ELAPSED_EXT)};
    }
    s.opened[s.group][pass] = false;
}

/// 一帧结束：每帧都尝试回收（以前只在 frames%10==0 那帧回收，4 组最多撑 4 帧，
/// 之后约 6 帧测不到，有效采样率只有四成）。最多回收两组，绝不等待。
pub fn frame_end() {
    let s = unsafe { state() };
    if !s.enabled {
        return;
    }
    s.no_group = false;
    let get = match s.get {
        Some(f) => f,
        None => return,
    };
    let mut freed = 0;
    for index in 0..GROUPS {
        if freed >= 2 {
            break;
        }
        if !s.used[index] {
            continue;
        }
        let mask = s.ran[index];
        if mask == 0 {
            s.used[index] = false;
            freed += 1;
            continue;
        }
        // TTL：某个 query 永远不 available（驱动丢查询等）时不能把组占死，否则测量会静默停摆。
        if s.frames.wrapping_sub(s.born[index]) > TTL_FRAMES {
            s.used[index] = false;
            freed += 1;
            continue;
        }
        let mut all: u32 = 1;
        for pass in 0..PASSES {
            if mask & (1 << pass) == 0 {
                continue;
            }
            let mut available: u32 = 0;
            unsafe{get(s.ids[index][pass], QUERY_RESULT_AVAILABLE, &mut available)};
            if available == 0 {
                all = 0;
                break;
            }
        }
        if all == 0 {
            continue;
        }
        let mut disjoint: i32 = 0;
        unsafe{glGetIntegerv(GPU_DISJOINT_EXT, &mut disjoint)};
        s.used[index] = false;
        freed += 1;
        if disjoint != 0 {
            continue;
        }
        let mut values = [0i64; PASSES];
        let mut total = 0i64;
        for pass in 0..PASSES {
            if mask & (1 << pass) == 0 {
                continue;
            }
            let mut ns: u32 = 0;
            unsafe{get(s.ids[index][pass], QUERY_RESULT, &mut ns)};
            values[pass] = ns as i64;
            total += ns as i64;
        }
        for pass in 0..PASSES {
            if mask & (1 << pass) == 0 { continue; }
            s.sums[pass] += values[pass].max(0) as u64;
            s.hits[pass] += 1;
        }
        push_sample(total);
        if s.sample_count >= 60 {
            unsafe{report_samples()};
        }
    }
}

fn push_sample(total_ns: i64) {
    let s = state();
    let us = (total_ns / 1000).max(0).min(u32::MAX as i64) as u32;
    if s.sample_count < s.samples.len() {
        s.samples[s.sample_count] = us;
        s.sample_count += 1;
    }
}

/// 报中位数而不是逐帧最大值：偶发离群帧会把 max 拉飞，中位数才代表稳态成本。
unsafe fn report_samples() {
    let s = state();
    let mut values = [0u32; 64];
    let count = s.sample_count;
    values[..count].copy_from_slice(&s.samples[..count]);
    s.sample_count = 0;
    let slice = &mut values[..count];
    slice.sort_unstable();
    let median = slice[count / 2];
    let p95 = slice[(count * 95 / 100).min(count - 1)];
    // 均值只按「真正跑过这个 pass 的帧」平均：跑 0 帧就是缓存全命中，均值无意义、留 0。
    let mut means = [0u32; PASSES];
    let mut hits = [0u32; PASSES];
    for pass in 0..PASSES {
        hits[pass] = s.hits[pass];
        if hits[pass] > 0 {
            means[pass] = (s.sums[pass] / hits[pass] as u64 / 1000) as u32;
        }
        s.sums[pass] = 0;
        s.hits[pass] = 0;
    }
    log_report(median, p95, count as u32, &means, &hits);
}

unsafe fn log_report(median_us: u32, p95_us: u32, count: u32, means: &[u32; PASSES], hits: &[u32; PASSES]) {
    let mut text = format!("probe passes total_med={}us total_p95={}us n={}", median_us, p95_us, count);
    for pass in 0..PASSES {
        if means[pass] == 0 {
            continue;
        }
        text.push(' ');
        text.push_str(LABELS[pass]);
        text.push('=');
        text.push_str(&means[pass].to_string());
        text.push('/');
        text.push_str(&hits[pass].to_string());
    }
    let mut line = text.into_bytes();
    line.push(0);
    __android_log_print(4, b"AliveClean\0".as_ptr() as *const c_char,
        b"%s\0".as_ptr() as *const c_char, line.as_ptr() as *const c_char);
}

/// 每帧推进计数（与 frame_end 分开，避免 frame_end 提前 return 时计数不走）。
pub fn tick() {
    let s = unsafe { state() };
    if s.enabled {
        s.frames = s.frames.wrapping_add(1);
    }
}
