package org.aliveclean;

/**
 * 息屏进入后的可动窗口：速度从 1 线性衰减到 0，窗口长度 {@value #DURATION_MS}ms。
 *
 * <p>用来限制 AOD 动画的总时长，避免息屏后一直动下去。</p>
 */
final class AodMotionWindow {
    static final int DURATION_MS=3000;
    private long start=-1;
    void enter(long nanos){start=nanos;}
    void leave(){start=-1;}
    float speed(long nanos){return start<0?0f:Math.max(0f,1f-Math.max(0,nanos-start)/(DURATION_MS*1_000_000f));}
    boolean running(long nanos){return speed(nanos)>0;}
}
