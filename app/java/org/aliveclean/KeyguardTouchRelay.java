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
    /** Upper share of the screen that holds the lock clock and its touch deformation. */
    private static final float CLOCK_ZONE=0.45f;

    private static Messenger channel;
    private static float startX,startRawY;
    private static boolean decided,hijacked;
    private static long liquidCheckedAt;
    private static boolean liquidApplied;
    private static boolean channelFailed;
    private static long lastMove;
    private static int forwarded;
    private static int consumers;
    private static int captures;
    private static int probe;
    private static boolean dumped;
    private static int titlesLogged;

    private static final String[] SHADE_VIEWS={
        "com.android.systemui.shade.NotificationShadeWindowView",
        "com.android.systemui.statusbar.phone.NotificationShadeWindowView"
    };

    static void install(ClassLoader loader) {
        hookClockViews(loader);
        int hooked=0;
        for (String name:SHADE_VIEWS) {
            Class<?> view=de.robv.android.xposed.XposedHelpers.findClassIfExists(name,loader);
            if (view==null) continue;
            java.util.Set<XC_MethodHook.Unhook> set=XposedBridge.hookAllMethods(view,"dispatchTouchEvent",new XC_MethodHook(){
                @Override protected void beforeHookedMethod(MethodHookParam param) {
                    try {
                        if (param.args==null||param.args.length!=1||!(param.args[0] instanceof MotionEvent))return;
                        MotionEvent event=(MotionEvent)param.args[0];
                        if (event.getActionMasked()==MotionEvent.ACTION_DOWN&&probe<3) {
                            probe++;
                            Log.i(TAG,"lock probe liquid="+liquid()+" locked="+locked());
                        }
                        if (liquid()) gate(param,event);
                        forward(param.thisObject,event);
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

    /** Reports which keyguard views actually consume a finger down, so the clock that deforms under
     *  touch can be identified on this ROM instead of guessed by class name. */
    private static void hookClockViews(ClassLoader loader) {
        try {
            Class<?> group=Class.forName("android.view.ViewGroup",false,loader);
            XposedBridge.hookAllMethods(group,"dispatchTouchEvent",new XC_MethodHook(){
                @Override protected void afterHookedMethod(MethodHookParam param) {
                    try {
                        if (!liquid()||!locked())return;
                        if (!(param.args[0] instanceof MotionEvent))return;
                        MotionEvent event=(MotionEvent)param.args[0];
                        if (event.getActionMasked()!=MotionEvent.ACTION_DOWN)return;
                        if (!Boolean.TRUE.equals(param.getResult()))return;
                        if (consumers>=12)return;
                        consumers++;
                        Log.i(TAG,"keyguard consumer "+param.thisObject.getClass().getName());
                    } catch (Throwable ignored) {}
                }
            });
            Log.i(TAG,"keyguard consumer probe installed");
        } catch (Throwable error) {
            Log.w(TAG,"keyguard consumer probe unavailable",error);
        }
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

    /**
     * With a liquid scene applied, an upward drag that does not start in the bottom band must not
     * unlock the phone: it belongs to the water. The shade still gets the DOWN, so taps on
     * notifications keep working; the moment the drag is recognised we cancel the gesture for the
     * subtree, which returns the panel to rest instead of dismissing the keyguard.
     */
    private static void gate(XC_MethodHook.MethodHookParam param,MotionEvent event) {
        View view=param.thisObject instanceof View?(View)param.thisObject:null;
        if (view==null)return;
        int action=event.getActionMasked();
        if (action==MotionEvent.ACTION_DOWN) {
            startX=event.getX();startRawY=event.getRawY();decided=false;hijacked=false;
            return;
        }
        if (action==MotionEvent.ACTION_MOVE) {
            if (!decided) {
                float rise=startRawY-event.getRawY(),travel=Math.abs(event.getX()-startX);
                float slop=dp(view,24);
                if (rise>slop&&rise>travel) {
                    // Upward drag: the water owns everything above the unlock strip.
                    decided=true;hijacked=startRawY<unlockBandTop(view);
                    if (hijacked) note("vertical",startRawY);
                }
            }
            if (hijacked) param.args[0]=cancelOf(event);
            return;
        }
        if (hijacked&&(action==MotionEvent.ACTION_UP||action==MotionEvent.ACTION_CANCEL)) {
            param.args[0]=cancelOf(event);
        }
        if (action==MotionEvent.ACTION_UP||action==MotionEvent.ACTION_CANCEL) {
            decided=false;hijacked=false;
        }
    }

    /** One shot: names of every view under the keyguard window, so the clock that deforms under a
     *  finger can be identified by class instead of guessed from a screen region. */
    private static void dumpOnce(View root) {
        if (dumped) return;
        dumped=true;
        try {
            java.util.ArrayList<String> names=new java.util.ArrayList<>();
            collect(root.getRootView(),names,0);
            Log.i(TAG,"keyguard views: "+android.text.TextUtils.join(",",names));
        } catch (Throwable error) {
            Log.w(TAG,"keyguard view dump failed",error);
        }
    }

    private static void collect(View view,java.util.List<String> names,int depth) {
        if (view==null||names.size()>=60||depth>14) return;
        String name=view.getClass().getName();
        String simple=name.substring(name.lastIndexOf('.')+1);
        names.add(depth+" "+simple);
        if (view instanceof android.view.ViewGroup) {
            android.view.ViewGroup group=(android.view.ViewGroup)view;
            for (int i=0;i<group.getChildCount();i++) collect(group.getChildAt(i),names,depth+1);
        }
    }

    private static void note(String kind,float rawY) {
        if (captures>=6)return;
        captures++;
        Log.i(TAG,"lock drag captured "+kind+" rawY="+rawY);
    }

    private static MotionEvent cancelOf(MotionEvent source) {
        MotionEvent copy=MotionEvent.obtain(source);
        copy.setAction(MotionEvent.ACTION_CANCEL);
        return copy;
    }

    /** Bottom band that still unlocks. Derived from the live view height so every screen size,
     *  aspect ratio and navigation mode gets the same 10% strip instead of a baked pixel value. */
    private static int screenHeight(View view) {
        return view.getResources().getDisplayMetrics().heightPixels;
    }

    /** Bottom strip that still unlocks, expressed as 10% of the real display height so every screen
     *  size and aspect ratio gets the same physical share of the screen. Raw screen coordinates are
     *  used on purpose: the keyguard view can be inset, and a view-relative y made the band drift. */
    private static float unlockBandTop(View view) {
        return screenHeight(view)*0.90f;
    }

    private static float dp(View view,float value) {
        return value*view.getResources().getDisplayMetrics().density;
    }

    private static boolean liquid() {
        long now=SystemClock.uptimeMillis();
        if (now-liquidCheckedAt<1000L) return liquidApplied;
        liquidCheckedAt=now;
        try {
            Bundle result=application().getContentResolver().call(SCENE,"configuration",null,null);
            liquidApplied=result!=null&&result.getBoolean("vivo_liquid",false);
        } catch (Throwable unavailable) {
            liquidApplied=false;
        }
        return liquidApplied;
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
