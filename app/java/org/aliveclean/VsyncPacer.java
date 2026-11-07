package org.aliveclean;

final class VsyncPacer {
    private long lastFrame=Long.MIN_VALUE;
    void reset(){lastFrame=Long.MIN_VALUE;}
    boolean due(long vsyncNanos) {
        if(vsyncNanos<=lastFrame)return false;
        lastFrame=vsyncNanos;return true;
    }
}
