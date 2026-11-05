package org.aliveclean;

import android.app.KeyguardManager;
import android.content.*;
import android.os.Bundle;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

public final class CleanWallpaper extends WallpaperService {
    /** Scene reconciliation is the one thing that cannot be traced-gated: it is the hook that
     *  decides whether the wallpaper paints the lock or the home composition. */
    static final String TAG="HyperAliveHooks";
    /** Back-off used while re-deriving the scene after a wake-up: immediate, then until the
     *  keyguard settles, then a 1s beat for at most SETTLE_LIMIT steps. */
    static final long[] SETTLE_DELAYS={0,80,160,280,450,700,1000,1400,1900,2500,3200};
    static final int SETTLE_LIMIT=40;
    /** True while our wallpaper service is bound, i.e. while it really is the current wallpaper. */
    static volatile boolean RUNNING;

    @Override public Engine onCreateEngine(){return new SceneEngine();}
    final class SceneEngine extends Engine implements SceneChannel.Listener {
        RenderLoop renderer;
        boolean ambient;
        final SceneState scenesState=new SceneState();
        boolean regionObserverRegistered,receivedLayout;
        boolean wallpaperVisible,renderVisible;
        int lastDisplayState=android.view.Display.STATE_UNKNOWN;
        boolean dimObserverRegistered;
        int lastSentMode=-1;
        boolean screenOn=true;
        int lockGraceToken;
        boolean lockGraceActive;
        int settleToken,settleStep;
        final android.os.Handler handler=new android.os.Handler(android.os.Looper.getMainLooper());
        final android.database.ContentObserver dimSetting=new android.database.ContentObserver(new android.os.Handler(android.os.Looper.getMainLooper())){
            @Override public void onChange(boolean selfChange){readWallpaperDim();}
        };
        android.hardware.display.DisplayManager displayManager;
        final android.hardware.display.DisplayManager.DisplayListener displays=new android.hardware.display.DisplayManager.DisplayListener(){
            public void onDisplayAdded(int id){refreshVisibility();}
            public void onDisplayRemoved(int id){refreshVisibility();}
            public void onDisplayChanged(int id){if(id==engineDisplayId())refreshVisibility();}
        };
        final android.database.ContentObserver area=new android.database.ContentObserver(new android.os.Handler(android.os.Looper.getMainLooper())){
            @Override public void onChange(boolean selfChange){readOfficialRegion();}
        };
        // One engine serves both the lock screen and the home screen, so unlocking does not change
        // wallpaper visibility and nothing else would re-derive the scene: these broadcasts are the
        // only unlock wake-up there is, and they are asynchronous (ACTION_USER_PRESENT can arrive
        // while isKeyguardLocked() still reports true), so the scene is re-derived a few times.
        final BroadcastReceiver state=new BroadcastReceiver(){@Override public void onReceive(Context c,Intent intent){
            String action=intent==null?null:intent.getAction();
            if(Intent.ACTION_CONFIGURATION_CHANGED.equals(action))readWallpaperDim();
            if(Intent.ACTION_SCREEN_OFF.equals(action)){
                screenOn=false;ambient=true;settleToken++;
                if(renderer!=null&&lastSentMode!=0)send(0,false);
                android.util.Log.i(TAG,"scene screen off mode=0");
                return;
            }
            if(Intent.ACTION_SCREEN_ON.equals(action)){screenOn=true;ambient=false;}else if(Intent.ACTION_USER_PRESENT.equals(action)){screenOn=true;ambient=false;}
            android.util.Log.i(TAG,"scene wake action="+action+" ambient="+ambient+" locked="+keyguardLocked()
                    +" authoritative="+scenesState.authoritative()+" mode="+scenesState.mode());
            update("broadcast:"+action);refreshVisibility();
            if(Intent.ACTION_SCREEN_ON.equals(action)||Intent.ACTION_USER_PRESENT.equals(action))settle("broadcast:"+action);
        }};
        @Override public void onCreate(SurfaceHolder holder){
            super.onCreate(holder);RUNNING=true;renderer=new RenderLoop(CleanWallpaper.this,isPreview()?SceneOptions.DRAFT:SceneOptions.APPLIED);
            readWallpaperDim();
            try{getContentResolver().registerContentObserver(android.provider.Settings.Secure.getUriFor("oplus_customize_settings_dark_wallpaper"),false,dimSetting);dimObserverRegistered=true;}
            catch(RuntimeException error){android.util.Log.w("AliveClean","Wallpaper dim observer unavailable",error);}
            renderer.visible(false);
            displayManager=getSystemService(android.hardware.display.DisplayManager.class);
            displayManager.registerDisplayListener(displays,new android.os.Handler(android.os.Looper.getMainLooper()));
            if(!isPreview())SceneChannel.add(this);
            IntentFilter f=new IntentFilter();f.addAction(Intent.ACTION_SCREEN_ON);f.addAction(Intent.ACTION_SCREEN_OFF);f.addAction(Intent.ACTION_USER_PRESENT);
            f.addAction(Intent.ACTION_CONFIGURATION_CHANGED);
            if(android.os.Build.VERSION.SDK_INT>=33){
                registerReceiver(state,f,Context.RECEIVER_NOT_EXPORTED);
            }else{
                registerReceiver(state,f);
            }
            if(!isPreview())try{
                getContentResolver().registerContentObserver(android.provider.Settings.System.getUriFor("alive_wallpaper_position"),false,area);
                regionObserverRegistered=true;
            }catch(RuntimeException error){android.util.Log.w("AliveClean","AOD region observer unavailable",error);}
            setTouchEventsEnabled(true);{if(Diagnostics.TRACE)android.util.Log.i("AliveClean","Wallpaper engine created preview="+isPreview());}
        }
        @Override public void scene(int next,boolean animate,long time,int phase,long clockToken,float nightLevel,float aodMask){
            boolean changed=scenesState.accept(next,time,phase);
            if(changed||(next==1&&lastSentMode!=1)){ambient=next==0;lastSentMode=next;renderer.mode(next,animate,nightLevel,aodMask);}
            refreshVisibility();
            if(scenesState.mode()==next&&engineDisplayId()==android.view.Display.DEFAULT_DISPLAY)renderer.clockTransition(clockToken);
            android.util.Log.i(TAG,"scene push mode="+next+" changed="+changed+" animate="+animate+" phase="+phase+" delayMs="+(android.os.SystemClock.uptimeMillis()-time));
        }
        @Override public void touch(int action,float x,float y){
            if(renderer!=null)renderer.touch(action,x,y);
        }
        @Override public void region(AodRegion region){
            int display=engineDisplayId();
            if(region!=null&&region.displayId==display){receivedLayout=true;renderer.aodRegion(region,display);}
        }
        @Override public void backdrop(boolean covered){if(!isPreview()&&engineDisplayId()==android.view.Display.DEFAULT_DISPLAY)renderer.backdrop(covered);}
        @Override public void disconnected(){renderer.backdrop(false);scenesState.disconnect();update("disconnected");}
        private android.view.Display engineDisplay(){
            if(android.os.Build.VERSION.SDK_INT>=30)return getDisplayContext().getDisplay();
            return getSystemService(android.hardware.display.DisplayManager.class).getDisplay(android.view.Display.DEFAULT_DISPLAY);
        }
        private int engineDisplayId(){android.view.Display display=engineDisplay();return display==null?-1:display.getDisplayId();}
        private void readOfficialRegion(){
            if(isPreview()||renderer==null||receivedLayout)return;
            try{
                android.view.Display display=engineDisplay();
                // Flyme's legacy setting contains no display identity; use it only on the main display.
                if(display==null||display.getDisplayId()!=android.view.Display.DEFAULT_DISPLAY)return;
                android.graphics.Point size=new android.graphics.Point();display.getRealSize(size);
                AodRegion region=AodRegion.parse(display.getDisplayId(),size.x,size.y,android.provider.Settings.System.getString(getContentResolver(),"alive_wallpaper_position"));
                if(region!=null)renderer.aodRegion(region,display.getDisplayId());
            }catch(RuntimeException error){android.util.Log.w("AliveClean","AOD region unavailable",error);}
        }
        private boolean keyguardLocked(){
            KeyguardManager k=getSystemService(KeyguardManager.class);
            return k!=null&&k.isKeyguardLocked();
        }
        /** True while the painted composition still disagrees with keyguard/ambient. */
        private boolean unsettled(){
            if(ambient)return false; // dozing: the AOD composition is the expected one
            return keyguardLocked()?scenesState.mode()!=1:scenesState.mode()!=2;
        }
        /** Re-derives the scene now and keeps re-deriving it while it disagrees with the keyguard.
         *  The keyguard chain is asynchronous and lossy on HyperOS; one missed wake-up used to leave
         *  the wallpaper on the lock composition until the next visibility change (app switch). */
        private void settle(String source){
            if(isPreview())return;
            final int token=++settleToken;
            settleStep=0;
            schedule(token,source);
        }
        private void schedule(final int token,final String source){
            int step=settleStep++;
            if(step>=SETTLE_LIMIT)return;
            long delay=step<SETTLE_DELAYS.length?SETTLE_DELAYS[step]:1000L;
            handler.postDelayed(new Runnable(){@Override public void run(){
                if(token!=settleToken||renderer==null)return;
                update(source+"+"+step);
                // Keep sampling through the whole wake-up window even while the keyguard still
                // reports locked: the unlock itself is exactly the edge we are waiting for, and
                // stopping as soon as the lock composition looks "correct" misses it.
                if(step<SETTLE_DELAYS.length||unsettled())schedule(token,source);
            }},delay);
        }
        private void update(String source){
            if(renderer==null)return;
            if(!isPreview()&&!screenOn){
                if(lastSentMode!=0)send(0,false);
                return;
            }
            boolean locked=keyguardLocked();
            int before=scenesState.mode();
            int mode=isPreview()?1:scenesState.fallback(ambient,locked);
            if(!isPreview()&&mode==1&&lastSentMode==0&&!ambient){
                if(!lockGraceActive){
                    lockGraceActive=true;
                    final int token=++lockGraceToken;
                    final String cause=source;
                    handler.postDelayed(new Runnable(){public void run(){
                        if(token!=lockGraceToken||renderer==null)return;
                        lockGraceActive=false;
                        boolean nowLocked=keyguardLocked();
                        int nowMode=scenesState.fallback(ambient,nowLocked);
                        android.util.Log.i(TAG,"scene lock grace source="+cause+" ambient="+ambient
                                +" locked="+nowLocked+" mode="+lastSentMode+"->"+nowMode);
                        if(nowMode!=lastSentMode)send(nowMode,nowMode!=1);
                    }},300);
                }
                android.util.Log.i(TAG,"scene reconcile source="+source+" ambient="+ambient+" locked="+locked
                        +" authoritative="+scenesState.authoritative()+" mode="+before+"->1 (deferred)");
                return;
            }
            cancelLockGrace();
            android.util.Log.i(TAG,"scene reconcile source="+source+" ambient="+ambient+" locked="+locked
                    +" authoritative="+scenesState.authoritative()+" mode="+before+"->"+mode
                    +(mode!=lastSentMode?" (applied)":" (unchanged)"));
            if(mode!=lastSentMode)send(mode,true);
        }
        private void cancelLockGrace(){lockGraceActive=false;lockGraceToken++;}
        private void send(int mode,boolean animate){
            cancelLockGrace();
            lastSentMode=mode;renderer.mode(mode,animate);
        }
        private void readWallpaperDim(){
            boolean night=(getResources().getConfiguration().uiMode&android.content.res.Configuration.UI_MODE_NIGHT_MASK)==android.content.res.Configuration.UI_MODE_NIGHT_YES;
            boolean dim=false;
            try{dim=night&&android.provider.Settings.Secure.getInt(getContentResolver(),"oplus_customize_settings_dark_wallpaper",0)==1;}
            catch(RuntimeException error){android.util.Log.w("AliveClean","Wallpaper dim setting unavailable",error);}
            renderer.vivoDarkWallpaper(dim);
        }
        @Override public void onSurfaceChanged(SurfaceHolder h,int format,int w,int height){super.onSurfaceChanged(h,format,w,height);{if(Diagnostics.TRACE)android.util.Log.i("AliveClean","Surface changed "+w+"x"+height);}lastSentMode=-1;update("surface");renderer.attach(h.getSurface(),w,height);readOfficialRegion();}
        private void refreshVisibility(){
            android.view.Display display=engineDisplay();
            int state=display==null?android.view.Display.STATE_UNKNOWN:display.getState();
            boolean next=wallpaperVisible&&state!=android.view.Display.STATE_UNKNOWN&&state!=android.view.Display.STATE_OFF;
            boolean displayChanged=state!=lastDisplayState;
            lastDisplayState=state;
            if(next!=renderVisible){renderVisible=next;renderer.visible(next);}
            else if(next&&displayChanged)renderer.resumeDisplay();
        }
        @Override public void onVisibilityChanged(boolean visible){
            wallpaperVisible=visible;
            android.util.Log.i(TAG,"wallpaper visible="+visible+" ambient="+ambient+" locked="+keyguardLocked()+" mode="+scenesState.mode());
            if(!visible){
                refreshVisibility();return;
            }
            android.view.Display d=engineDisplay();
            if(d!=null&&d.getState()!=android.view.Display.STATE_OFF)screenOn=true;
            update("visible");settle("visible");refreshVisibility();
        }
        // Framework @SystemApi callback: absent from the public SDK's stubs.
        public void onAmbientModeChanged(boolean inAmbientMode,long duration){ambient=inAmbientMode;{if(Diagnostics.TRACE)android.util.Log.i("AliveClean","Ambient="+ambient);}update("ambient="+inAmbientMode);refreshVisibility();}
        @Override public Bundle onCommand(String action,int x,int y,int z,Bundle extras,boolean resultRequested){
            android.util.Log.i(TAG,"wallpaper command="+action+" authority="+scenesState.authoritative()+" mode="+scenesState.mode());
            if(scenesState.authoritative())return super.onCommand(action,x,y,z,extras,resultRequested);
            boolean animate=extras==null||!extras.getBoolean("action_alive_animator_end",false);
            if("action_alive_aod".equals(action)){ambient=true;send(0,animate);}
            else if("action_alive_lock".equals(action)||"android.wallpaper.keyguard_locked".equals(action)){ambient=false;update("command:"+action);}
            else if("action_alive_launcher".equals(action)||"android.wallpaper.keyguard_going_away".equals(action)){ambient=false;send(2,animate);}
            return super.onCommand(action,x,y,z,extras,resultRequested);
        }
        private int touchLogs;
        @Override public void onTouchEvent(android.view.MotionEvent event){
            android.graphics.Rect frame=getSurfaceHolder().getSurfaceFrame();
            boolean usable=renderer!=null&&frame.width()>0&&frame.height()>0;
            if(touchLogs<8){touchLogs++;android.util.Log.i("AliveClean","touch action="+event.getActionMasked()
                    +" frame="+frame.width()+"x"+frame.height()+" x="+event.getX()+" y="+event.getY()+" usable="+usable);}
            if(usable)renderer.touch(event.getActionMasked(),event.getX()/frame.width(),event.getY()/frame.height());
        }
        @Override public void onSurfaceDestroyed(SurfaceHolder h){renderer.detach();super.onSurfaceDestroyed(h);}
        @Override public void onDestroy(){
            settleToken++;handler.removeCallbacksAndMessages(null);
            SceneChannel.remove(this);displayManager.unregisterDisplayListener(displays);unregisterReceiver(state);
            if(regionObserverRegistered)getContentResolver().unregisterContentObserver(area);
            if(dimObserverRegistered)getContentResolver().unregisterContentObserver(dimSetting);
            renderer.close();RUNNING=false;super.onDestroy();
        }
    }
}
