//! 诊断用 GPU 计时：把一帧的 GPU 执行时间量出来。
//!
//! `GL_EXT_disjoint_timer_query` 在 GLES3 核心表里没有入口，Java 的 `GLES30.glBeginQuery`
//! 传 `GL_TIME_ELAPSED_EXT` 会被驱动判成非法枚举（实测报错），必须走 `*EXT` 入口，
//! 所以这里用 `eglGetProcAddress` 自己取符号。
//!
//! 只用于诊断（探针开启时由 RenderLoop 调用），正式路径不产生任何 GL 调用。
//! 查询结果异步读取：`end()` 返回的是此前某一轮已完成查询的结果，绝不阻塞渲染线程。
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
}

const TIME_ELAPSED_EXT: u32 = 0x88BF;
const QUERY_RESULT: u32 = 0x8866;
const QUERY_RESULT_AVAILABLE: u32 = 0x8867;
const GPU_DISJOINT_EXT: u32 = 0x8FBB;
const SLOTS: usize = 3;

struct State {
    resolved: bool,
    failed: bool,
    begin_query: Option<BeginQuery>,
    end_query: Option<EndQuery>,
    get_query: Option<GetQueryObjectuiv>,
    ids: [u32; SLOTS],
    running: [bool; SLOTS],
    slot: usize,
}

static mut STATE: State = State {
    resolved: false,
    failed: false,
    begin_query: None,
    end_query: None,
    get_query: None,
    ids: [0; SLOTS],
    running: [false; SLOTS],
    slot: 0,
};

/// 取符号。只有真机上 libEGL.so 才提供 eglGetProcAddress。
unsafe fn resolve() -> bool {
    let state = &mut *ptr::addr_of_mut!(STATE);
    if state.resolved {
        return !state.failed;
    }
    state.resolved = true;
    let name = b"libEGL.so\0";
    let handle = dlopen(name.as_ptr() as *const c_char, 2 /*RTLD_NOW*/);
    if handle.is_null() {
        state.failed = true;
        return false;
    }
    let sym = b"eglGetProcAddress\0";
    let raw = dlsym(handle, sym.as_ptr() as *const c_char);
    if raw.is_null() {
        state.failed = true;
        return false;
    }
    let get_proc: GetProcAddress = std::mem::transmute(raw);
    let load = |text: &[u8]| -> Ptr {
        get_proc(text.as_ptr() as *const c_char)
    };
    let begin = load(b"glBeginQueryEXT\0");
    let end = load(b"glEndQueryEXT\0");
    let get = load(b"glGetQueryObjectuivEXT\0");
    if begin.is_null() || end.is_null() || get.is_null() {
        state.failed = true;
        return false;
    }
    state.begin_query = Some(std::mem::transmute(begin));
    state.end_query = Some(std::mem::transmute(end));
    state.get_query = Some(std::mem::transmute(get));
    true
}

#[no_mangle]
pub unsafe extern "system" fn Java_org_aliveclean_NativeGpuTimer_supported(
    _: *mut c_void,
    _: *mut c_void,
) -> i32 {
    if !resolve() {
        return 0;
    }
    let state = &mut *ptr::addr_of_mut!(STATE);
    if state.ids[0] != 0 {
        return 1;
    }
    while glGetError() != 0 {}
    glGenQueries(SLOTS as i32, state.ids.as_mut_ptr());
    if glGetError() != 0 || state.ids[0] == 0 {
        return 0;
    }
    1
}

#[no_mangle]
pub unsafe extern "system" fn Java_org_aliveclean_NativeGpuTimer_begin(
    _: *mut c_void,
    _: *mut c_void,
) -> i32 {
    if Java_org_aliveclean_NativeGpuTimer_supported(ptr::null_mut(), ptr::null_mut()) == 0 {
        return 0;
    }
    let state = &mut *ptr::addr_of_mut!(STATE);
    let slot = state.slot;
    if state.running[slot] {
        return 0;
    }
    let begin = match state.begin_query {
        Some(f) => f,
        None => return 0,
    };
    while glGetError() != 0 {}
    begin(TIME_ELAPSED_EXT, state.ids[slot]);
    if glGetError() != 0 {
        return 0;
    }
    state.running[slot] = true;
    1
}

/// @return 上一轮查询的 GPU 时间（ns）；-1 表示还没有可用结果；-2 表示 disjoint，结果不可信。
#[no_mangle]
pub unsafe extern "system" fn Java_org_aliveclean_NativeGpuTimer_end(
    _: *mut c_void,
    _: *mut c_void,
) -> i64 {
    if !resolve() {
        return -1;
    }
    let state = &mut *ptr::addr_of_mut!(STATE);
    let slot = state.slot;
    if !state.running[slot] {
        return -1;
    }
    if let Some(end) = state.end_query {
        end(TIME_ELAPSED_EXT);
    }
    state.running[slot] = false;
    let mut value: i64 = -1;
    if let Some(get) = state.get_query {
        for step in 1..=SLOTS {
            let index = (slot + step) % SLOTS;
            if state.running[index] || state.ids[index] == 0 {
                continue;
            }
            let mut available: u32 = 0;
            get(state.ids[index], QUERY_RESULT_AVAILABLE, &mut available);
            if available == 0 {
                continue;
            }
            let mut disjoint: i32 = 0;
            glGetIntegerv(GPU_DISJOINT_EXT, &mut disjoint);
            let mut ns: u32 = 0;
            get(state.ids[index], QUERY_RESULT, &mut ns);
            value = if disjoint != 0 { -2 } else { ns as i64 };
            break;
        }
    }
    state.slot = (slot + 1) % SLOTS;
    value
}
