package org.aliveclean;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.SystemClock;
import android.view.MotionEvent;
import android.view.View;

final class KeyguardTouchRelay {
    private static final String TAG="AliveClean";
    private static final Uri SCENE=Uri.parse("content://org.aliveclean.scene/configuration");
    private static final long MOVE_INTERVAL_MS=40L;
    private static Messenger channel;
    private static float startX,startRawY;
    private static boolean decided,hijacked;
    private static long liquidCheckedAt;
    private static boolean liquidApplied;
    private static boolean channelFailed;
    private static long signalRetryAt;
    private static long lastMove;
    private static int forwarded;
    private static int consumers;
    private static int captures;
    private static int probe;
    private static java.lang.ref.WeakReference<View> clockRef=new java.lang.ref.WeakReference<>(null);
    private static boolean dumped;
    private static boolean clockProbed;

    private static final float DEFORM_GAIN=0f;
    private static boolean deformLogged;
    private static volatile boolean dragging;
    private static final java.util.HashSet<String> propSeen=new java.util.HashSet<>();

    private static final String[] SHADE_VIEWS={
        "com.android.systemui.shade.NotificationShadeWindowView",
        "com.android.systemui.statusbar.phone.NotificationShadeWindowView"
    };

    static void install(ClassLoader loader) {
        hookClockViews(loader);
        hookPropertyWrites(loader);
        hookLockDeform(loader);
        hookFingerprint(loader);
        hookKeyguardHide(loader);
        int hooked=0;
        for (String name:SHADE_VIEWS) {
            Class<?> view=Xp.findClassIfExists(name,loader);
            if (view==null) continue;
            java.util.Set<Xp.Unhook> set=Xp.hookAllMethods(view,"dispatchTouchEvent",new Xp.MethodHook(){
                @Override protected void beforeHookedMethod(Xp.MethodHookParam param) {
                    try {
                        if (param.args==null||param.args.length!=1||!(param.args[0] instanceof MotionEvent))return;
                        MotionEvent event=(MotionEvent)param.args[0];
                        if (liquid()&&locked()) pinClock(param.thisObject);
        if (event.getActionMasked()==MotionEvent.ACTION_DOWN&&probe<3) {
                            probe++;
                            AliveLog.i(TAG,"lock probe liquid="+liquid()+" locked="+locked());
                        }
                        if (liquid()) gate(param,event);
                        forward(param.thisObject,event);
                    } catch (Throwable error) {
                        AliveLog.w(TAG,"lock touch relay failed",error);
                    }
                }
            });
            int count=set==null?0:set.size();
            hooked+=count;
            AliveLog.i(TAG,"lock touch hook "+name+" count="+count);
        }
        AliveLog.i(TAG,"lock touch relay installed methods="+hooked);
    }

    private static void hookClockViews(ClassLoader loader) {
        try {
            Class<?> group=Class.forName("android.view.ViewGroup",false,loader);
            Xp.hookAllMethods(group,"dispatchTouchEvent",new Xp.MethodHook(){
                @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                    try {
                        if (!liquid()||!locked())return;
                        if (!(param.args[0] instanceof MotionEvent))return;
                        MotionEvent event=(MotionEvent)param.args[0];
                        if (event.getActionMasked()!=MotionEvent.ACTION_DOWN)return;
                        if (!Boolean.TRUE.equals(param.getResult()))return;
                        if (consumers>=12)return;
                        consumers++;
                        AliveLog.i(TAG,"keyguard consumer "+param.thisObject.getClass().getName());
                    } catch (Throwable ignored) {}
                }
            });
            AliveLog.i(TAG,"keyguard consumer probe installed");
        } catch (Throwable error) {
            AliveLog.w(TAG,"keyguard consumer probe unavailable",error);
        }
    }

    private static void hookPropertyWrites(ClassLoader loader) {
        try {
            Class<?> view=Class.forName("android.view.View",false,loader);
            String[] names={"setScaleX","setScaleY","setAlpha","setTranslationX","setTranslationY"};
            for (String name:names) {
                for (java.lang.reflect.Method method:view.getMethods()) {
                    if (!method.getName().equals(name)||method.getParameterTypes().length!=1) continue;
                    Xp.hookMethod(method,new Xp.MethodHook(){
                        @Override protected void beforeHookedMethod(Xp.MethodHookParam param) {
                            try {
                                if (!dragging||!liquid()||!locked())return;
                                if (!(param.thisObject instanceof View))return;
                                View target=(View)param.thisObject;
                                boolean shade=insideShade(target);
                                String simple=target.getClass().getName();
                                simple=simple.substring(simple.lastIndexOf('.')+1);
                                if (propSeen.add(simple)) AliveLog.i(TAG,"animated "+simple+" shade="+shade+" via "+param.method.getName());
                                if (!shade)return;
                                param.setResult(null);
                            } catch (Throwable ignored) {}
                        }
                    });
                }
            }
            AliveLog.i(TAG,"property write guard installed");
        } catch (Throwable error) {
            AliveLog.w(TAG,"property write guard unavailable",error);
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

    private static void hookLockDeform(ClassLoader loader) {
        try {
            Class<?> callback=Xp.findClassIfExists(
                    "com.android.keyguard.injector.KeyguardPanelViewInjector$bindView$2",loader);
            if (callback==null) {AliveLog.w(TAG,"lock deform: callback class not found");return;}
            Xp.hookAllMethods(callback,"onHorizontalMove",new Xp.MethodHook(){
                @Override protected void beforeHookedMethod(Xp.MethodHookParam param) {
                    try {
                        if (deformOff()||!liquid()||!locked())return;
                        if (param.args==null||param.args.length!=1||!(param.args[0] instanceof Float))return;
                        float distance=(Float)param.args[0];
                        if (Float.isNaN(distance)||Float.isInfinite(distance)) distance=0f;
                        param.args[0]=distance*DEFORM_GAIN;
                        if (!deformLogged&&distance!=0f) {
                            deformLogged=true;
                            AliveLog.i(TAG,"lock deform intercepted distance="+distance+" gain="+DEFORM_GAIN);
                        }
                    } catch (Throwable error) {
                        AliveLog.w(TAG,"lock deform hook failed",error);
                    }
                }
            });
            AliveLog.i(TAG,"lock deform hook installed");
        } catch (Throwable error) {
            AliveLog.w(TAG,"lock deform hook unavailable",error);
        }
    }

    private static boolean deformOff() {
        try {
            return android.provider.Settings.System.getInt(
                    application().getContentResolver(),"alive_lock_deform_off",0)==1;
        } catch (Throwable ignored) {
            return false;
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
            if (forwarded<8) {forwarded++;AliveLog.i(TAG,"lock touch sent action="+action+" x="+x+" y="+y);}
        } catch (Throwable dead) {
            channel=null;
            AliveLog.w(TAG,"lock touch channel dropped",dead);
        }
    }

    private static void gate(Xp.MethodHookParam param,MotionEvent event) {
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

    private static void dumpOnce(View root) {
        if (dumped) return;
        dumped=true;
        try {
            java.util.ArrayList<String> names=new java.util.ArrayList<>();
            collect(root.getRootView(),names,0);
            AliveLog.i(TAG,"keyguard views: "+android.text.TextUtils.join(",",names));
        } catch (Throwable error) {
            AliveLog.w(TAG,"keyguard view dump failed",error);
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
            AliveLog.i(TAG,"clock probe: "+sb);
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
        AliveLog.i(TAG,"lock drag captured "+kind+" rawY="+rawY);
    }

    private static MotionEvent cancelOf(MotionEvent source) {
        MotionEvent copy=MotionEvent.obtain(source);
        copy.setAction(MotionEvent.ACTION_CANCEL);
        return copy;
    }

    private static int screenHeight(View view) {
        return view.getResources().getDisplayMetrics().heightPixels;
    }

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

            liquidApplied=result!=null&&result.getBoolean("wallpaper_running",false)
                    &&result.getBoolean("vivo_liquid",false);
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
            Object value=Xp.callStaticMethod(
                    Class.forName("android.app.ActivityThread"),"currentApplication");
            if (value instanceof android.app.Application)return (android.app.Application)value;
        } catch (Throwable ignored) {}
        throw new IllegalStateException("no application in this process");
    }

    private static void hookFingerprint(ClassLoader loader) {
        try {
            Class<?> monitor=Xp.findClassIfExists("com.android.keyguard.KeyguardUpdateMonitor",loader);
            if (monitor==null) {AliveLog.w(TAG,"fingerprint hook: KeyguardUpdateMonitor missing");return;}
            java.util.Set<Xp.Unhook> set=Xp.hookAllMethods(monitor,"onFingerprintAuthenticated",new Xp.MethodHook(){
                @Override protected void afterHookedMethod(Xp.MethodHookParam param) {
                    int user=-1;
                    boolean strong=false;
                    try {
                        if (param.args!=null&&param.args.length>0&&param.args[0] instanceof Integer) user=(Integer)param.args[0];
                        if (param.args!=null&&param.args.length>1&&param.args[1] instanceof Boolean) strong=(Boolean)param.args[1];
                    } catch (Throwable ignored) {}
                    reportFingerprint(user,strong);
                }
            });
            AliveLog.i(TAG,"fingerprint hook installed methods="+(set==null?0:set.size()));
        } catch (Throwable error) {AliveLog.w(TAG,"fingerprint hook unavailable",error);}
    }

    private static void hookKeyguardHide(ClassLoader loader) {
        try {
            Class<?> mediator=Xp.findClassIfExists("com.android.systemui.keyguard.KeyguardViewMediator",loader);
            if (mediator==null) {AliveLog.w(TAG,"keyguard hide hook: KeyguardViewMediator missing");return;}
            java.util.Set<Xp.Unhook> set=Xp.hookAllMethods(mediator,"handleHide",new Xp.MethodHook(){
                @Override protected void beforeHookedMethod(Xp.MethodHookParam param) {
                    reportKeyguardHiding();
                }
            });
            AliveLog.i(TAG,"keyguard hide hook installed methods="+(set==null?0:set.size()));
        } catch (Throwable error) {AliveLog.w(TAG,"keyguard hide hook unavailable",error);}
    }

    private static void reportKeyguardHiding() {
        long now=SystemClock.uptimeMillis();
        Messenger target=signalChannel();
        if (target==null) return;
        Message message=Message.obtain();
        message.what=6;
        Bundle data=new Bundle();
        data.putLong("time",now);
        message.setData(data);
        try {
            target.send(message);
            AliveLog.i(TAG,"keyguard hiding t="+now);
        } catch (Throwable dead) {channel=null;AliveLog.w(TAG,"keyguard channel dropped",dead);}
    }
    private static void reportFingerprint(int user,boolean strong) {
        long now=SystemClock.uptimeMillis();
        AliveLog.i(TAG,"fingerprint authenticated user="+user+" strong="+strong+" t="+now);
        Messenger target=signalChannel();
        if (target==null) return;
        Message message=Message.obtain();
        message.what=5;
        Bundle data=new Bundle();
        data.putLong("time",now);
        message.setData(data);
        try {target.send(message);}
        catch (Throwable dead) {channel=null;AliveLog.w(TAG,"fingerprint channel dropped",dead);}
    }

    private static Messenger signalChannel() {
        if (channel!=null) return channel;
        long now=SystemClock.uptimeMillis();
        if (now<signalRetryAt) return null;
        signalRetryAt=now+3000L;
        channelFailed=false;
        return messenger();
    }

    private static Messenger messenger() {        if (channel!=null)return channel;
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
            AliveLog.w(TAG,"lock touch channel unavailable",error);
            return null;
        }
    }
}
