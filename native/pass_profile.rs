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
const REPORT_EVERY: u32 = 10;

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
        return;
    }
    s.group = picked;
    s.opened[picked] = [false; PASSES];
    s.ran[picked] = 0;
    unsafe{s.begin.unwrap()(TIME_ELAPSED_EXT, s.ids[picked][0]);} // 占位：立刻结束，保持配对
    unsafe{s.end.unwrap()(TIME_ELAPSED_EXT);}
    s.used[picked] = true;
}

pub fn pass_begin(pass: usize) {
    let s = unsafe { state() };
    if !s.enabled || !s.used[s.group] || pass >= PASSES {
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
    if !s.enabled || pass >= PASSES || !s.opened[s.group][pass] {
        return;
    }
    if let Some(end) = s.end {
        unsafe{end(TIME_ELAPSED_EXT)};
    }
    s.opened[s.group][pass] = false;
}

/// 一帧结束：回收已完成的最老一组并汇总打印，然后释放它。
pub fn frame_end() {
    let s = unsafe { state() };
    if !s.enabled || !s.used[s.group] {
        return;
    }
    let get = match s.get {
        Some(f) => f,
        None => return,
    };
    if s.frames % REPORT_EVERY != 0 {
        return;
    }
    for index in 0..GROUPS {
        if !s.used[index] {
            continue;
        }
        let mask = s.ran[index];
        if mask == 0 {
            s.used[index] = false;
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
        if disjoint != 0 {
            return;
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
        unsafe{log_report(&values, total, mask)};
        return;
    }
}

unsafe fn log_report(values: &[i64; PASSES], total: i64, mask: u32) {
    let mut text = String::from("probe passes");
    for pass in 0..PASSES {
        text.push(' ');
        if mask & (1 << pass) == 0 { continue; }
        text.push_str(LABELS[pass]);
        text.push('=');
        text.push_str(&format!("{:.2}", values[pass] as f64 / 1.0e6));
    }
    text.push_str(&format!(" total={:.2}", total as f64 / 1.0e6));
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
