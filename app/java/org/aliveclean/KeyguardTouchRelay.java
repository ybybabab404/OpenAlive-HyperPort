package org.aliveclean;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;

/**
 * HyperOS does not deliver wallpaper touches to a third-party WallpaperService, so the liquid scene
 * gets nothing on the home screen or the lock screen. The lock screen is the one place we can help:
 * the keyguard window belongs to SystemUI, which we already hook, and its touches can be mirrored to
 * the wallpaper process without consuming them.
 *
 * Only the keyguard window is mirrored, so launcher gestures stay untouched, and every forwarded
 * motion is a copy: the original event keeps flowing to SystemUI unchanged.
 */
final class KeyguardTouchRelay {
    private static final String TAG="AliveClean";
    private static final Uri SCENE=Uri.parse("content://org.aliveclean.scene/configuration");
    private static final long MOVE_INTERVAL_MS=40L;

    private static Messenger channel;
    private static boolean channelFailed;
    private static long lastMove;
    private static int forwarded;
    private static int titlesLogged;

    private static final String[] SHADE_VIEWS={
        "com.android.systemui.shade.NotificationShadeWindowView",
        "com.android.systemui.statusbar.phone.NotificationShadeWindowView"
    };

    static void install(ClassLoader loader) {
        int hooked=0;
        for (String name:SHADE_VIEWS) {
            Class<?> view=de.robv.android.xposed.XposedHelpers.findClassIfExists(name,loader);
            if (view==null) continue;
            java.util.Set<XC_MethodHook.Unhook> set=XposedBridge.hookAllMethods(view,"dispatchTouchEvent",new XC_MethodHook(){
                @Override protected void beforeHookedMethod(MethodHookParam param) {
                    try {
                        if (param.args==null||param.args.length!=1||!(param.args[0] instanceof MotionEvent))return;
                        forward(param.thisObject,(MotionEvent)param.args[0]);
                    } catch (Throwable error) {
                        Log.w(TAG,"lock touch relay failed",error);
                    }
                }
            });
            int count=set==null?0:set.size();
            hooked+=count;
            Log.i(TAG,"lock touch hook "+name+" count="+count);
        }
        Log.i(TAG,"lock touch relay installed methods="+hooked);
    }

    private static void forward(Object source,MotionEvent event) {
        if (!locked()) return;
        int action=event.getActionMasked();
        if (action!=MotionEvent.ACTION_DOWN&&action!=MotionEvent.ACTION_UP&&action!=MotionEvent.ACTION_MOVE)return;
        if (action==MotionEvent.ACTION_MOVE) {
            long now=SystemClock.uptimeMillis();
            if (now-lastMove<MOVE_INTERVAL_MS)return;
            lastMove=now;
        }
        View view=source instanceof View?(View)source:null;
        if (view==null||view.getWidth()<=0||view.getHeight()<=0)return;
        float x=event.getX()/view.getWidth(),y=event.getY()/view.getHeight();
        if (x<0f||x>1f||y<0f||y>1f)return;
        Messenger target=messenger();
        if (target==null)return;
        Message message=Message.obtain();
        message.what=4;
        Bundle data=new Bundle();
        data.putInt("action",action);data.putFloat("x",x);data.putFloat("y",y);
        message.setData(data);
        try {
            target.send(message);
            if (forwarded<8) {forwarded++;Log.i(TAG,"lock touch sent action="+action+" x="+x+" y="+y);}
        } catch (Throwable dead) {
            channel=null;
            Log.w(TAG,"lock touch channel dropped",dead);
        }
    }

    private static boolean locked() {
        try {
            android.app.KeyguardManager manager=(android.app.KeyguardManager)
                    application().getSystemService(android.content.Context.KEYGUARD_SERVICE);
            return manager!=null&&manager.isKeyguardLocked();
        } catch (Throwable unknown) {
            return false;
        }
    }

    private static android.app.Application application() {
        try {
            Object value=de.robv.android.xposed.XposedHelpers.callStaticMethod(
                    Class.forName("android.app.ActivityThread"),"currentApplication");
            if (value instanceof android.app.Application)return (android.app.Application)value;
        } catch (Throwable ignored) {}
        throw new IllegalStateException("no application in this process");
    }

    private static Messenger messenger() {
        if (channel!=null)return channel;
        if (channelFailed)return null;
        try {
            ContentResolver resolver=application().getContentResolver();
            Bundle result=resolver.call(SCENE,"connect",null,new Bundle());
            IBinder binder=result==null?null:result.getBinder("channel");
            if (binder==null)throw new IllegalStateException("no channel binder");
            channel=new Messenger(binder);
            return channel;
        } catch (Throwable error) {
            channelFailed=true;
            Log.w(TAG,"lock touch channel unavailable",error);
            return null;
        }
    }
}
