package org.aliveclean;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.util.FloatProperty;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.PathInterpolator;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

final class SceneMotion {

    static final int COUNT=60, LENS=3, GLASS=13, GROUND=19, FULL=21, MASK=23,
            PHOTO_MATRIX=25, DECOR_MATRIX=41;
    private static final long STALL_STEP=24;

    final float[] values=new float[COUNT];
    private final ByteBuffer packet=ByteBuffer.allocateDirect(COUNT*4).order(ByteOrder.nativeOrder());
    private final FloatBuffer floats=packet.asFloatBuffer();
    private final SceneMatrices matrices;
    private final int aod,lock,home;
    private final boolean textureTracks;
    private final float expandedScale;
    private final Random random=new Random();
    private final ArrayList<Track> tracks=new ArrayList<>();
    private Track positionX,positionY;
    private float positionStartX,positionStartY;
    private long lastFrame,clock,playDelay,duration;
    private boolean running,paused,groundMask;
    private float[] blendFrom=new float[]{0,0,0};
    private int blendNext;
    private long blendDuration;
    private Interpolator blendCurve;
    private float aodX,aodY=.335f;
    private int mode;
    private boolean pending;
    private static final Interpolator DEFAULT=new AccelerateDecelerateInterpolator();
    private static final Interpolator LINEAR=new LinearInterpolator();
    private static final Interpolator EASE=path(.33f,0,.67f,1);
    private static final Interpolator EXPAND=path(.13f,.04f,0,1);
    private static final Interpolator COLLAPSE=path(.37f,.04f,.04f,1);
    private static final Interpolator DISTORT=path(0,.5f,0,1);
    private static final Interpolator ROTATE=path(.17f,0,.67f,1);
    private static final FloatProperty<SceneMotion>[] PROPERTIES=properties();

    SceneMotion(int width,int height,int mode,int aod,int lock,int home) {
        this(width,height,mode,aod,lock,home,true);
    }
    SceneMotion(int width,int height,int mode,int aod,int lock,int home,boolean textureTracks) {
        this.textureTracks=textureTracks;
        this.mode=mode;this.aod=aod;this.lock=lock;this.home=home;
        expandedScale="2.33".equals(new DecimalFormat("#.##").format((double)height/width))?1.3f:1.24f;
        matrices=new SceneMatrices(width,height);
        values[mode]=1;
        System.arraycopy(lensState(mode),0,values,LENS,10);
        values[LENS+9]=-360;
        System.arraycopy(glassState(mode),0,values,GLASS,6);
        values[GROUND]=values[GROUND+1]=mode==1?0:1;
        values[FULL]=values[FULL+1]=mode==0?1:0;
        values[58]=1;
        values[59]=1;
    }

    private static Interpolator path(float a,float b,float c,float d) {return new PathInterpolator(a,b,c,d);}
    @SuppressWarnings("unchecked") private static FloatProperty<SceneMotion>[] properties() {
        FloatProperty<SceneMotion>[] result=(FloatProperty<SceneMotion>[])new FloatProperty<?>[COUNT];
        for(int i=0;i<COUNT;i++) {
            final int slot=i;
            result[i]=new FloatProperty<SceneMotion>("sceneValue"+i) {
                public Float get(SceneMotion scene){return scene.values[slot];}
                public void setValue(SceneMotion scene,float value){scene.values[slot]=value;}
            };
        }
        return result;
    }
    private float[] lensState(int scene) {
        if(scene==0)return new float[]{22,.22f,9,1,0,aodX,aodY,1,0,Float.NaN};
        // 注意：启航的锁屏稳态构图取样自 AOD 层（lens_blend 在镜头圆内直接用 u_aod_effect），
        // 所以这里的模糊半径是"会被显示出来"的稳态值，必须为 0 —— 设成 16 会让锁屏一直糊。
        // 转场期间的模糊由 lens() 里那一档的起始值 22 衰减到 0 提供。
        return new float[]{scene==1?0:16,expandedScale,0,0,-149,0,0,0,1.24f,-360};
    }
    private float[] glassState(int scene) {
        float width=scene==1?(lock==2?.05f:.04f):(lock==2?.072f:.076f);
        return new float[]{scene==0?.87f:1,width,width,scene==1?0:1,
                scene==1?.03f:0,scene==1&&lock==2?.007f:0};
    }
    static int transition(int from,int to) {
        if(from==0)return to==1?0:2;
        if(to==0)return from==1?1:3;
        return from==1?4:5;
    }
    static float nearestRotation(float current,float target) {
        float distance=Math.floorMod((int)(target-current),360);
        return target+(distance>=180?360-distance:-distance);
    }
    private Track track(int slot,float target,long duration,Interpolator curve) {
        return new Track(slot,values[slot],target,0,duration,curve);
    }
    private void lens(int tr,int next) {
        float[] target=lensState(next);
        long[] ms={1000,1000,1000,1000,1000,1000,1000,1000,1000,1000};
        Interpolator[] curves={DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT,DEFAULT};
        long rippleDelay=0;
        float angleStart=values[LENS+9];
        if(tr==0||tr==2) {
            // 模糊半径 22(AOD 的稳态) -> 目标的衰减时长：183ms 太短，亮屏时几乎看不到模糊；
            // 650ms 让"形变 + 模糊"同时被看见，终点仍是 0，不会残留在锁屏上。
            ms[0]=tr==0?650:183;curves[0]=EASE;ms[3]=33;curves[3]=EASE;
            ms[1]=ms[5]=ms[6]=tr==0?950:1000;
            curves[1]=curves[5]=curves[6]=tr==0?EXPAND:path(0,0,0,1);
            ms[2]=tr==0?800:900;curves[2]=DISTORT;
            ms[8]=tr==0?950:1000;curves[8]=EASE;
            ms[9]=24;curves[9]=ROTATE;ms[7]=0;
            angleStart=nearestRotation(angleStart,target[9]);
        } else if(tr==1||tr==3) {
            for(int i=0;i<8;i++)ms[i]=650;
            curves[0]=path(.17f,0,.83f,1);curves[3]=EASE;
            curves[4]=path(.37f,.02f,.04f,1);
            curves[1]=curves[5]=curves[6]=COLLAPSE;curves[2]=EASE;
            rippleDelay=650;ms[8]=0;
        } else if(tr==4) {
            ms[0]=3840;curves[0]=EASE;
            ms[1]=ms[5]=ms[6]=650;curves[1]=curves[5]=curves[6]=EXPAND;
            ms[2]=600;curves[2]=DISTORT;ms[8]=650;curves[8]=EASE;
            ms[9]=24;curves[9]=ROTATE;
        } else {
            ms[0]=0;curves[0]=EASE;curves[8]=EASE;
            ms[9]=24;curves[9]=ROTATE;ms[7]=0;
        }

        for(int i:new int[]{0,1,2,8,3,4,5,6,7,9}) {
            if(Float.isNaN(target[i])) continue;
            Track animator=i==9?new Track(LENS+9,angleStart,target[9],0,ms[i],curves[i]):track(LENS+i,target[i],ms[i],curves[i]);
            if(i==8)animator.delay=rippleDelay;
            if(i==5){positionX=animator;positionStartX=values[LENS+5];}
            if(i==6){positionY=animator;positionStartY=values[LENS+6];}
            tracks.add(animator);
        }
    }
    private void glass(int tr,int next) {
        float[] target=glassState(next);
        for(int i=0;i<6;i++) {
            long ms;Interpolator curve;
            if(tr==0||tr==5) {
                ms=i==3?(tr==0&&lock==2?470:425):850;curve=path(.23f,.09f,.16f,1);
            } else if(tr==2) {ms=200;curve=path(.23f,.09f,.16f,1);}
            else if(tr==3) {ms=i==0?800:200;curve=path(.1f,0,.3f,1);}
            else {ms=i<4?1500:700;curve=i==0?path(.22f,.66f,.15f,1):path(.48f,.22f,.46f,1);}
            tracks.add(track(GLASS+i,target[i],ms,curve));
        }
    }
    private void ground(int tr,int next) {
        final float first=values[GROUND],second=values[GROUND+1],target=next==1?0:1;
        long ms=tr==0||tr==5?1500:200;
        tracks.add(new Track(GROUND,first,target,0,ms,LINEAR));
        tracks.add(new Track(GROUND+1,second,target,0,ms,LINEAR));
        if(tr==1||tr==4)groundMask=true;
    }
    private void full(int tr,int next) {
        if(tr>3)return;
        for(int i=0;i<2;i++) {
            long ms;Interpolator curve;
            if(tr==0){ms=250;curve=i==0?path(.4f,0,.2f,1):path(.78f,.01f,.89f,.57f);}
            else if(tr==1){ms=1300;curve=path(.19f,1.91f,.47f,1.02f);}
            else if(tr==2){ms=250;curve=path(.4f,0,.2f,1);}
            else {ms=800;curve=path(.25f,.1f,.25f,1);}
            tracks.add(track(FULL+i,next==0?1:0,ms,curve));
        }
    }
    long blendDuration(int tr) {
        long[] a=aod==0?new long[]{950,650,300,650,650,300}:
                aod==101?new long[]{250,1200,350,350,300,300}:new long[]{400,400,400,400,400,400};
        long[] l=lock==0?new long[]{250,250,250,250,400,400}:
                lock>=4?new long[]{250,250,250,250,500,500}:
                lock==3?new long[]{1500,200,200,200,200,1500}:new long[]{850,200,200,200,500,850};
        long[] h={250,250,250,250,500,500};
        return tr<2?Math.max(a[tr],l[tr]):tr<4?Math.max(a[tr],h[tr]):Math.max(l[tr],h[tr]);
    }

    void prepare(int next) {
        if(next<0||next>2)throw new IllegalArgumentException("scene "+next);
        boolean interrupted=running;
        if(!active())lastFrame=0;
        tracks.clear();groundMask=false;
        int previous=mode,tr=transition(previous,next);
        if(aod==0&&previous==0&&next==2){tr=0;AliveLog.i("AliveClean","scene sail lock animation 0->2 tr=0");}
        if(aod==0)lens(tr,next);else if(aod==101)full(tr,next);
        if(textureTracks){if(lock==1||lock==2)glass(tr,next);else if(lock==3)ground(tr,next);}
        blendFrom=new float[]{values[0],values[1],values[2]};blendNext=next;
        blendDuration=blendDuration(tr);blendCurve=tr==2||tr==3?EASE:LINEAR;
        // 2026-11-16: 息屏→桌面 时，息屏层和桌面层默认会同时在场约 1 秒（诊断日志实测 ratio aod 0.43→0.11）。
        // 锁屏选了纹理时，息屏层画面（玻璃扭曲那份）与桌面差别很大，这一秒就被看成"双层"。
        // 锁屏没有纹理时两层几乎一样，所以只有选纹理才暴露。这里把 0->2 的叠加压到 150ms。
        if(aod==0&&previous==0&&next==2&&lock>=1)blendDuration=150;
        playDelay=previous==0&&!interrupted?150:0;
        long end=blendDuration;
        for(int i=0;i<tracks.size();i++){Track k=tracks.get(i);long e=k.delay+k.duration;if(e>end)end=e;}
        duration=end;clock=0;running=false;paused=false;
        mode=next;
    }
    void change(int next){change(next,true);}

    void change(int next,boolean start){
        if(mode==next)return;
        prepare(next);
        if(start)begin();else pending=true;
    }
    private void begin(){running=true;paused=false;clock=0;seek(0);}
    void startPending(){
        if(!pending)return;
        pending=false;
        begin();
    }
    boolean pending(){return pending;}

    void preserveMode(int next){mode=next;}
    void finish(){
        complete();
        pending=false;
    }
    private void complete(){
        if(blendCurve!=null)seek(duration);
        running=false;paused=false;clock=duration;
        if(groundMask){groundMask=false;if(values[GROUND+1]>.999){values[MASK]=random.nextFloat()*100;values[MASK+1]=random.nextFloat()*100;}}
    }
    boolean active(){return running||aod==0&&mode==0;}
    boolean transitionActive(){return running;}
    void pause(boolean paused){
        lastFrame=0;
        this.paused=paused;
        if(pending){if(!paused)startPending();return;}
    }
    void close(){pending=false;running=false;paused=false;tracks.clear();blendCurve=null;lastFrame=0;}
    void advance(long frameNanos) {
        advance(frameNanos,1f);
    }
    void advance(long frameNanos,float speed) {
        long delta=lastFrame==0||frameNanos<lastFrame?0:(frameNanos-lastFrame)/1_000_000L;
        if(lastFrame!=0&&frameNanos>=lastFrame&&aod==0&&values[LENS+7]>0)
            values[LENS+9]=(values[LENS+9]-((frameNanos-lastFrame)/1_000_000_000f*40f)*values[LENS+7]*speed)%360f;
        lastFrame=frameNanos;
        if(running){
            if(paused)return;
            if(delta>STALL_STEP)delta=STALL_STEP;
            clock+=delta;
            if(clock>=duration){complete();return;}
            seek(clock);
            return;
        }
        if(mode==0){values[LENS+5]=aodX;values[LENS+6]=aodY;}
    }
    void aodPosition(float x,float y){aodX=x;aodY=y;if(mode==0&&!running){values[LENS+5]=x;values[LENS+6]=y;}}
    void followClock(float x,float y){
        aodPosition(x,y);
        if(mode==0&&running){
            if(positionX!=null){positionX.to=x;if(clock>=positionX.delay+positionX.duration)values[LENS+5]=x;}
            if(positionY!=null){positionY.to=y;if(clock>=positionY.delay+positionY.duration)values[LENS+6]=y;}
        }
    }
    private void seek(long t){
        long local=t-playDelay;
        for(int i=0;i<tracks.size();i++){Track k=tracks.get(i);values[k.slot]=k.valueAt(local);}
        if(blendCurve!=null){
            float f=blendDuration<=0?1f:(local<=0?0f:(local>=blendDuration?1f:blendCurve.getInterpolation((float)local/blendDuration)));
            for(int i=0;i<3;i++)values[i]=blendFrom[i]+((blendNext==i?1:0)-blendFrom[i])*f;
        }
    }
    private static final class Track {
        final int slot;
        final float from;
        float to;
        long delay,duration;
        final Interpolator curve;
        Track(int slot,float from,float to,long delay,long duration,Interpolator curve){
            this.slot=slot;this.from=from;this.to=to;this.delay=delay;this.duration=duration;this.curve=curve;
        }
        float valueAt(long t){
            long local=t-delay;
            if(local<=0)return from;
            if(duration<=0)return to;
            if(local>=duration)return to;
            return from+(to-from)*curve.getInterpolation((float)local/duration);
        }
    }
    ByteBuffer packet() {
        matrices.write(values);
        floats.position(0);floats.put(values);packet.position(0);return packet;
    }
}
