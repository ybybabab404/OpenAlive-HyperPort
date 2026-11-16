package org.aliveclean;

/**
 * 渲染节流：按固定帧间隔决定这一帧要不要真的画。
 *
 * <p>两档间隔目前都是 8ms（约 125Hz），所以 {@link #doze} 实际不会改变节奏；
 * 保留这个入口是为了以后给 AOD 单独降帧时有落点。</p>
 */
final class VsyncPacer {
    private static final long LIVE_INTERVAL_NS=8_000_000L;
    private static final long DOZE_INTERVAL_NS=8_000_000L;
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
