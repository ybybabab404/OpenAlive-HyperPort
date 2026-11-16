package org.aliveclean;

/**
 * 转场时钟：累计"从开始到现在"的毫秒数。
 *
 * <p>{@link #pause} 只是丢掉上一次的时间戳，暂停期间的间隔不会计入总时长，
 * 适合跟随渲染可见性反复停启的场景。</p>
 */
final class WallpaperTransitionTime {
    private long last=-1,elapsed;
    void restart(){elapsed=0;last=-1;}
    void pause(){last=-1;}
    float advance(long now){
        if(last>=0)elapsed+=Math.max(0,now-last);
        last=now;
        return elapsed/1_000_000f;
    }
}
