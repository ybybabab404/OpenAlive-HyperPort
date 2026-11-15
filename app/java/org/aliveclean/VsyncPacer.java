package org.aliveclean;

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
