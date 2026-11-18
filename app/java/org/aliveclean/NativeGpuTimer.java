package org.aliveclean;

/**
 * 诊断用 GPU 计时入口（native 侧实现）。
 *
 * <p>用 {@code GL_EXT_disjoint_timer_query} 取「本 EGL context 已提交命令真正在 GPU 上执行的时间」。
 * Java 的 {@code GLES30.glBeginQuery} 不接受 {@code GL_TIME_ELAPSED_EXT}（会报非法枚举），
 * 所以走 native 里自取的 {@code *EXT} 入口。
 *
 * <p>只在探针（{@code alive_hs_probe}）开启时由 {@code RenderLoop} 调用；正式路径不产生任何 GL 调用。
 * 结果异步读取，任何时候都不阻塞渲染线程。
 */
final class NativeGpuTimer {
    private NativeGpuTimer() {}

    /** @return 1 表示可用（已成功建查询对象）；0 表示本机不支持。 */
    static native int supported();

    /** 开始计时一段 GPU 工作；@return 1 表示真的开始了。 */
    static native int begin();

    /**
     * 结束计时并取此前某轮已完成查询的结果。
     *
     * @return GPU 执行时间（ns）；-1 暂无可用结果；-2 结果因 GPU 不连续（disjoint）不可信。
     */
    static native long end();

    /**
     * 打开/关闭「按 pass 拆分」的 GPU 计时。
     *
     * <p>打开后每 60 帧打印一行 {@code probe passes copy=… lock=… …}（单位 ms）。
     * 一帧用一组 query、多组轮转，结果回来才读，任何时候都不阻塞渲染线程。
     * 与 {@link #begin()} 冲突（同时只能有一个活动查询），探针里二选一开。
     */
    static native void passes(int enable);
}
