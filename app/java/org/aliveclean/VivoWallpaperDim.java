package org.aliveclean;

/**
 * Vivo 壁纸的压暗值弹簧过渡：AOD 遮罩（mode 0）与深色壁纸两级来源都汇到这里，供 VivoDimPass 每帧取样。
 */
final class VivoWallpaperDim {
    private double value=1,target=1,velocity,omega=2*Math.PI/.5;
    private long previous;
    private boolean active;
    void target(float nightLevel,float aodMask,int mode,boolean animate,long now){
        sample(now);
        double next=nightLevel*(mode==0?1-aodMask:1);
        if(!animate){value=target=next;velocity=0;active=false;previous=now;return;}
        if(next==target)return;
        target=next;omega=2*Math.PI/(mode==0?.72:.5);previous=now;active=true;
    }
    float sample(long now){
        if(active){
            double t=Math.max(0,(now-previous)/1e9),x=value-target,b=velocity+omega*x;
            double e=Math.exp(-omega*t);
            value=target+(x+b*t)*e;velocity=(velocity-omega*b*t)*e;
            if(Math.abs(value-target)<.00002&&Math.abs(velocity)<.0002){value=target;velocity=0;active=false;}
        }
        previous=now;return (float)value;
    }
    boolean active(){return active;}
    void pause(){value=target;velocity=0;active=false;}
}
