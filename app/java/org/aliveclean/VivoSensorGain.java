package org.aliveclean;

import android.content.Context;
import android.hardware.*;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import java.util.WeakHashMap;

/**
 * Owner of the rotation subscription for raster/flash.
 *
 * Root cause: Vivo's controller is not a reliable subscription owner.
 * {@code n3.g.g(int,boolean)} arms the gyroscope only for animation types 0/4/6,
 * while types 1/2/3/5/8 and {@code b()} (stopAnimation) all call
 * {@code RotationSensor.d()}, which unregisters unconditionally - {@code p032e4.d}
 * keeps no refcount and no "is registered" flag. Visibility changes, scene
 * rebuilds and AOD transitions walk those paths, so the subscription can be
 * removed and never reinstalled; the integrated angle then freezes mid-gesture,
 * which is the "stops at the moment it went abnormal" symptom.
 *
 * Ownership model: the OS registration exists exactly while at least one engine
 * is on screen (visible), and it is dropped as soon as the last one leaves.
 * It deliberately does NOT follow the animation value (mode 1/0/AOD), because
 * every one of those transitions used to unregister and re-register the sensor
 * several times a second. Preview and wallpaper share the process; when they
 * happen to share the same native RotationSensor object, the second engine
 * attaches as a follower so the samples are integrated only once.
 *
 * The periodic check below is a backstop for a registration the platform dropped
 * while we still want samples; it is not the repair mechanism.
 */
final class VivoSensorGain implements SensorEventListener,AutoCloseable {
    private static final String TAG="AliveClean";
    private static final long WATCHDOG_MS=2000L;
    /** One registration owner per native RotationSensor object in this process. */
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
    /** Engines currently asking for samples through this object (0 = release the OS sensor). */
    private int clients;
    /** start()/stop() are paired per engine, so repeated updateAnimation() cannot pile up. */
    private boolean started;
    private final Runnable watchdog=new Runnable(){
        @Override public void run(){
            if(clients==0)return;
            long idle=SystemClock.uptimeMillis()-lastEvent;
            if(gyro!=null&&(!registered||idle>3000L)){
                // Backstop only. Covers the case where the platform dropped the queue
                // without telling us (background sensor freeze). The visibility edge in
                // resume() is the primary path; this just keeps a stuck engine alive.
                Log.i(TAG,"sensor gain watchdog rearm idle="+idle+" registered="+registered);
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
        Log.i(TAG,"sensor gain create listener="+System.identityHashCode(original)+" peer="+(peer!=null));
    }
    static float multiplier(int relative){
        int value=Math.max(-50,Math.min(50,relative));
        // Keep 0 byte-for-byte on the native listener. The negative half offers
        // a wider reduction without changing Vivo's colour/intensity algorithm.
        return (float)Math.pow(value<0?10:2,value/50.0);
    }
    /** Called when the engine becomes on screen. Idempotent per engine. */
    void start(){
        if(started)return;
        started=true;
        if(peer!=null){peer.retain();return;}
        retain();
    }
    /**
     * Forces a fresh OS registration. Called on a real visibility edge: the platform
     * silently stops delivering continuous sensors while the wallpaper is covered, so a
     * stale "registered" flag would leave the engine deaf after coming back to the home
     * screen. This is an ownership re-take, not a timed retry.
     */
    void resume(){
        if(peer!=null){peer.resume();return;}
        if(clients==0||gyro==null)return;
        if(registered){manager.unregisterListener(this);registered=false;}
        reclaim();
        Log.i(TAG,"sensor gain resume registered="+registered);
    }
    /** Called when the engine leaves the screen. Idempotent per engine. */
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
        Log.i(TAG,"sensor gain start clients="+clients+" registered="+registered);
    }
    private void drop(){
        clients=Math.max(0,clients-1);
        if(clients>0){Log.i(TAG,"sensor gain release clients="+clients);return;}
        handler.removeCallbacks(watchdog);
        if(registered){manager.unregisterListener(this);registered=false;}
        if(gyro!=null)manager.unregisterListener(original,gyro);
        Log.i(TAG,"sensor gain stop clients=0 registered=false");
    }
    /**
     * Takes the OS subscription back from Vivo's controller. Called after every
     * host-initiated controller call, because startAnimation() may have registered
     * the native listener again in the meantime.
     */
    void reclaim(){
        if(peer!=null){peer.reclaim();return;}
        if(clients==0||gyro==null)return;
        manager.unregisterListener(original,gyro);
        if(!registered){
            registered=manager.registerListener(this,gyro,sampling);
            Log.i(TAG,"sensor gain owner=proxy registered="+registered);
        }
    }
    // Called on the main thread, like the original sensor callback. Updating the
    // factor must not reset Vivo's integrated angle or recreate the GL scene.
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
        // Probe: proves whether the OS still delivers gyro samples to the app while
        // the desktop wallpaper is running, independently of Vivo's own gating.
        if(samples<=3||now-lastSampleLog>=3000){
            lastSampleLog=now;
            Log.i(TAG,"sensor gain sample n="+samples+" gain="+gain+" values="+event.values[0]+","+event.values[1]+","+event.values[2]);
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
        Log.i(TAG,"sensor gain closed clients="+clients);
    }
}
