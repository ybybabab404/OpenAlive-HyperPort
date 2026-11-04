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
    private static java.lang.ref.WeakReference<View> clockRef=new java.lang.ref.WeakReference<>(null);
    private static boolean dumped;
    private static boolean clockProbed;
    private static int effectBlocked;
    private static volatile boolean dragging;
    private static final java.util.HashSet<String> propSeen=new java.util.HashSet<>();
    private static java.lang.ref.WeakReference<View> headerRef=new java.lang.ref.WeakReference<>(null);
    private static final android.graphics.Rect headerRect=new android.graphics.Rect();
    private static int titlesLogged;

    private static final String[] SHADE_VIEWS={
        "com.android.systemui.shade.NotificationShadeWindowView",
        "com.android.systemui.statusbar.phone.NotificationShadeWindowView"
    };

    static void install(ClassLoader loader) {
        hookClockViews(loader);
        hookPropertyWrites(loader);
        int hooked=0;
        for (String name:SHADE_VIEWS) {
            Class<?> view=de.robv.android.xposed.XposedHelpers.findClassIfExists(name,loader);
            if (view==null) continue;
            java.util.Set<XC_MethodHook.Unhook> set=XposedBridge.hookAllMethods(view,"dispatchTouchEvent",new XC_MethodHook(){
                @Override protected void beforeHookedMethod(MethodHookParam param) {
                    try {
                        if (param.args==null||param.args.length!=1||!(param.args[0] instanceof MotionEvent))return;
                        MotionEvent event=(MotionEvent)param.args[0];
                        if (liquid()&&locked()) pinClock(param.thisObject);
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

    /** The warp is a native property animation: the keyguard content is scaled, faded and shifted
     *  while a finger drags it. Resetting those values after the fact loses the race with the
     *  animator, so the setters themselves are refused for shade views during an active drag. */
    private static void hookPropertyWrites(ClassLoader loader) {
        try {
            Class<?> view=Class.forName("android.view.View",false,loader);
            String[] names={"setScaleX","setScaleY","setAlpha","setTranslationX","setTranslationY"};
            for (String name:names) {
                for (java.lang.reflect.Method method:view.getMethods()) {
                    if (!method.getName().equals(name)||method.getParameterTypes().length!=1) continue;
                    XposedBridge.hookMethod(method,new XC_MethodHook(){
                        @Override protected void beforeHookedMethod(MethodHookParam param) {
                            try {
                                if (!dragging||!liquid()||!locked())return;
                                if (!(param.thisObject instanceof View))return;
                                View target=(View)param.thisObject;
                                boolean shade=insideShade(target);
                                String simple=target.getClass().getName();
                                simple=simple.substring(simple.lastIndexOf('.')+1);
                                if (propSeen.add(simple)) Log.i(TAG,"animated "+simple+" shade="+shade+" via "+param.method.getName());
                                if (!shade)return;
                                param.setResult(null);
                            } catch (Throwable ignored) {}
                        }
                    });
                }
            }
            Log.i(TAG,"property write guard installed");
        } catch (Throwable error) {
            Log.w(TAG,"property write guard unavailable",error);
        }
    }

    private static boolean insideShade(View view) {
        View current=view;
        for (int depth=0;current!=null&&depth<12;depth++) {
            String name=current.getClass().getName();
            if (name.contains("NotificationShadeWindowView")||name.contains("NotificationPanelView")) return true;
            android.view.ViewParent parent=current.getParent();
            current=parent instanceof View?(View)parent:null;
        }
        return false;
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
            dumpOnce(view);
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

    /** Allow-list instead of a block-list: only the container that hosts notifications and media
     *  cards, plus the bottom unlock strip, may see a touch. The clock header is therefore excluded
     *  whatever its layout state is, which a bounds test on that header could not guarantee. */
    private static boolean allowedArea(View root,MotionEvent event) {
        View container=headerRef.get();
        if (container==null||container.getWindowToken()==null) {
            container=findBySimpleName(root,"MiuiNQSContainer");
            if (container==null) return true;
            headerRef=new java.lang.ref.WeakReference<>(container);
        }
        int[] location=new int[2];
        container.getLocationOnScreen(location);
        headerRect.set(location[0],location[1],location[0]+container.getWidth(),location[1]+container.getHeight());
        if (container.getWidth()>0&&container.getHeight()>0&&headerRect.contains((int)event.getRawX(),(int)event.getRawY())) return true;
        return event.getRawY()>=root.getResources().getDisplayMetrics().heightPixels*0.88f;
    }

    private static View findBySimpleName(View view,String simple) {
        if (view==null) return null;
        String name=view.getClass().getName();
        if (name.substring(name.lastIndexOf('.')+1).equals(simple)) return view;
        if (view instanceof android.view.ViewGroup) {
            android.view.ViewGroup group=(android.view.ViewGroup)view;
            for (int i=0;i<group.getChildCount();i++) {
                View found=findBySimpleName(group.getChildAt(i),simple);
                if (found!=null) return found;
            }
        }
        return null;
    }

    /** The clock header feeds the digits' translation and alpha from the drag; zeroing those values
     *  on every keyguard touch keeps the clock still without depending on where the touch started. */
    private static void pinClock(Object source) {
        View root=source instanceof View?(View)source:null;
        if (root==null) return;
        View clock=clockRef.get();
        if (clock==null||clock.getWindowToken()==null) {
            clock=findBySimpleName(root,"Clock");
            if (clock==null) return;
            clockRef=new java.lang.ref.WeakReference<>(clock);
        }
        if (!clockProbed) {
            clockProbed=true;
            StringBuilder sb=new StringBuilder(clock.getClass().getName()).append(" methods:");
            for (java.lang.reflect.Method m:clock.getClass().getDeclaredMethods()) {
                String n=m.getName();
                if (n.contains("draw")||n.contains("Draw")||n.contains("alpha")||n.contains("Alpha")
                        ||n.contains("progress")||n.contains("Progress")||n.contains("offset")||n.contains("Offset")
                        ||n.contains("scale")||n.contains("Scale")||n.contains("update")||n.contains("Update"))
                    sb.append(' ').append(n);
            }
            Log.i(TAG,"clock probe: "+sb);
        }
        clock.setTranslationX(0f);
        clock.setTranslationY(0f);
        clock.setScaleX(1f);
        clock.setScaleY(1f);
        clock.setAlpha(1f);
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
        return screenHeight(view)*0.88f;
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
