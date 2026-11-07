package org.aliveclean;

final class SceneState {
    private int mode=1;
    private long timestamp=-1;
    private boolean authoritative;
    private int phase;
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

    boolean stale(boolean ambient,boolean locked){
        return authoritative&&!ambient&&mode==1&&!locked;
    }
    int fallback(boolean ambient,boolean locked){
        if(!authoritative)return mode=ambient?0:locked?1:2;

        if(stale(ambient,locked)){authoritative=false;return mode=2;}
        return mode;
    }
    int mode(){return mode;}
    int phase(){return phase;}
    boolean authoritative(){return authoritative;}
    void disconnect(){authoritative=false;timestamp=-1;phase=0;}
}
