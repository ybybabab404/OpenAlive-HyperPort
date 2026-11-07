package org.aliveclean;

import android.content.Context;
import android.hardware.*;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;

import java.util.WeakHashMap;

final class VivoSensorGain implements SensorEventListener,AutoCloseable {
    private static final String TAG="AliveClean";
    private static final long WATCHDOG_MS=2000L;

    private static final WeakHashMap<SensorEventListener,VivoSensorGain> OWNERS=new WeakHashMap<>();
    private final SensorManager manager;
    private final SensorEventListener original;
    private final Sensor gyro;
    private final int sampling;
    private final Handler handler=new Handler(Looper.getMainLooper());
    private VivoSensorGain peer;
    private float gain;
    private long lastEvent;
    private long samples,lastSampleLog;
    private boolean registered;

    private int clients;

    private boolean started;
    private final Runnable watchdog=new Runnable(){
        @Override public void run(){
            if(clients==0)return;
            long idle=SystemClock.uptimeMillis()-lastEvent;
            if(gyro!=null&&(!registered||idle>3000L)){

                AliveLog.i(TAG,"sensor gain watchdog rearm idle="+idle+" registered="+registered);
                if(registered){manager.unregisterListener(VivoSensorGain.this);registered=false;}
                reclaim();
            }
            handler.postDelayed(this,WATCHDOG_MS);
        }
    };
    VivoSensorGain(Context context,VivoCalls api,Object sensor,int relative)throws Exception{
        manager=context.getSystemService(SensorManager.class);original=(SensorEventListener)sensor;
        gyro=(Sensor)api.get(sensor,"d");sampling=(Integer)api.get(sensor,"j");gain=multiplier(relative);
        synchronized(OWNERS){
            VivoSensorGain existing=OWNERS.get(original);
            if(existing!=null&&existing!=this&&existing.clients>0)peer=existing;
            else OWNERS.put(original,this);
        }
        AliveLog.i(TAG,"sensor gain create listener="+System.identityHashCode(original)+" peer="+(peer!=null));
    }
    static float multiplier(int relative){
        int value=Math.max(-50,Math.min(50,relative));

        return (float)Math.pow(value<0?10:2,value/50.0);
    }

    void start(){
        if(started)return;
        started=true;
        if(peer!=null){peer.retain();return;}
        retain();
    }

    void resume(){
        if(peer!=null){peer.resume();return;}
        if(clients==0||gyro==null)return;
        if(registered){manager.unregisterListener(this);registered=false;}
        reclaim();
        AliveLog.i(TAG,"sensor gain resume registered="+registered);
    }

    void stop(){
        if(!started)return;
        started=false;
        if(peer!=null){peer.drop();return;}
        drop();
    }
    private void retain(){
        clients++;
        if(clients==1){
            lastEvent=SystemClock.uptimeMillis();
            reclaim();
            handler.removeCallbacks(watchdog);handler.postDelayed(watchdog,WATCHDOG_MS);
        }
        AliveLog.i(TAG,"sensor gain start clients="+clients+" registered="+registered);
    }
    private void drop(){
        clients=Math.max(0,clients-1);
        if(clients>0){AliveLog.i(TAG,"sensor gain release clients="+clients);return;}
        handler.removeCallbacks(watchdog);
        if(registered){manager.unregisterListener(this);registered=false;}
        if(gyro!=null)manager.unregisterListener(original,gyro);
        AliveLog.i(TAG,"sensor gain stop clients=0 registered=false");
    }

    void reclaim(){
        if(peer!=null){peer.reclaim();return;}
        if(clients==0||gyro==null)return;
        manager.unregisterListener(original,gyro);
        if(!registered){
            registered=manager.registerListener(this,gyro,sampling);
            AliveLog.i(TAG,"sensor gain owner=proxy registered="+registered);
        }
    }

    void relative(int value){
        if(peer!=null){peer.relative(value);return;}
        float next=multiplier(value);
        if(next==gain)return;
        gain=next;
    }
    @Override public void onSensorChanged(SensorEvent event){
        long now=SystemClock.uptimeMillis();
        lastEvent=now;
        samples++;

        if(samples<=3||now-lastSampleLog>=3000){
            lastSampleLog=now;
            AliveLog.i(TAG,"sensor gain sample n="+samples+" gain="+gain+" values="+event.values[0]+","+event.values[1]+","+event.values[2]);
        }
        if(gain==1f){original.onSensorChanged(event);return;}
        float x=event.values[0],y=event.values[1],z=event.values[2];
        try{event.values[0]=x*gain;event.values[1]=y*gain;event.values[2]=z*gain;original.onSensorChanged(event);}
        finally{event.values[0]=x;event.values[1]=y;event.values[2]=z;}
    }
    @Override public void onAccuracyChanged(Sensor sensor,int accuracy){original.onAccuracyChanged(sensor,accuracy);}
    @Override public void close(){
        stop();
        peer=null;
        synchronized(OWNERS){if(OWNERS.get(original)==this)OWNERS.remove(original);}
        AliveLog.i(TAG,"sensor gain closed clients="+clients);
    }
}
