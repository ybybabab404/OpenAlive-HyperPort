package org.aliveclean;

/** SystemUI owns scene transitions once connected; framework visibility is not a scene. */
final class SceneState {
    private int mode=1;
    private long timestamp=-1;
    private boolean authoritative;
    private int phase; // 0: unknown, 1: sleeping, 2: waking, 3: authenticated unlock
    boolean accept(int next,long time){
        return accept(next,time,0);
    }
    boolean accept(int next,long time,int eventPhase){
        if(next<0||next>2||time<timestamp||eventPhase<0||eventPhase>3)return false;
        if(eventPhase==1&&next!=0||eventPhase==2&&next==0)return false;
        if(eventPhase==3&&next!=2)return false;
        if(phase==3&&eventPhase!=1&&next!=2)return false;
        if(eventPhase==0&&(phase==1&&next!=0||phase==2&&next==0))return false;
        if(eventPhase!=0&&!(phase==3&&eventPhase==2))phase=eventPhase;
        timestamp=time;authoritative=true;
        boolean changed=mode!=next;mode=next;return changed;
    }
    /** True while an accepted SystemUI scene still disagrees with the keyguard it describes. */
    boolean stale(boolean ambient,boolean locked){
        return authoritative&&!ambient&&mode==1&&!locked;
    }
    int fallback(boolean ambient,boolean locked){
        if(!authoritative)return mode=ambient?0:locked?1:2;
        // A SystemUI scene is authoritative only while it still agrees with the keyguard it
        // describes. Mode 1 claims "lock screen is showing"; once the keyguard is demonstrably
        // gone that claim is stale, and nothing re-sends a scene afterwards (this build has no
        // what==1 sender), so the wallpaper would otherwise keep the lock composition until the
        // next visibility change. Drop authority and let the keyguard decide again.
        if(stale(ambient,locked)){authoritative=false;return mode=2;}
        return mode;
    }
    int mode(){return mode;}
    int phase(){return phase;}
    boolean authoritative(){return authoritative;}
    void disconnect(){authoritative=false;timestamp=-1;phase=0;}
}
