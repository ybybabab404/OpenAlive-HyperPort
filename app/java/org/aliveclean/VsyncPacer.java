package org.aliveclean;

/**
 * 渲染节流：按固定帧间隔决定这一帧要不要真的画。
 *
 * <p>AOD/doze 档是 16ms（约 60Hz），活动档是 8ms（约 125Hz）。降 AOD 帧率是
 * 2026-11-18 的视觉变更决策：镜头 40°/s 的旋转在 60Hz 下看不出来，但 GPU 占空比减半。
 * <b>改这里必须同时改 {@code RenderLoop.AOD_FRAME_MS}</b>，否则排帧与放行门槛不一致，
 * {@code due()} 会拒绝而 {@code framePosted} 不再重排 —— 帧就卡住了。</p>
 */
final class VsyncPacer {
    private static final long LIVE_INTERVAL_NS=8_000_000L;
    private static final long DOZE_INTERVAL_NS=16_000_000L;
    private long interval=LIVE_INTERVAL_NS;
    private long lastFrame=Long.MIN_VALUE;
    void reset(){lastFrame=Long.MIN_VALUE;}
    void doze(boolean on){
        long next=on?DOZE_INTERVAL_NS:LIVE_INTERVAL_NS;
        if(next!=interval){interval=next;lastFrame=Long.MIN_VALUE;}
    }
    boolean due(long vsyncNanos){
        if(vsyncNanos<=lastFrame)return false;
        if(lastFrame!=Long.MIN_VALUE&&vsyncNanos-lastFrame<interval)return false;
        lastFrame=vsyncNanos;return true;
    }
}
