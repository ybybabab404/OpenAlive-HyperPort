package org.aliveclean;

import android.content.Context;
import android.graphics.*;
import android.opengl.*;
import android.os.*;
import android.view.Surface;
import android.view.Choreographer;
import java.io.*;
import java.nio.ByteBuffer;

/**
 * 渲染循环与场景装配中枢：按 {@link SceneOptions} 建出具体渲染器（native/Cosmic/Vivo/…），
 * 在渲染线程上驱动每帧，管理 surface 生命周期、可见性节流与 mode 变化带来的转场。
 *
 * <p>所有 {@code mode()} 调用都 post 到同一个 handler，因此它们之间顺序即执行顺序
 * （{@code cropTask}、{@code attach} 重试与 watchdog 走的是 postDelayed，整体队列不是严格 FIFO）；
 * 想让某一帧生效的时序改动，必须在这里排队，而不是在调用方加 sleep。{@code forceFrame} 可以绕过可见性门控。</p>
 */
final class RenderLoop {
    private final Context context;
    private final android.content.SharedPreferences preferences;
    private final HandlerThread thread=new HandlerThread("AliveGL");
    private final Handler handler;
    private EGLDisplay display=EGL14.EGL_NO_DISPLAY;
    private EGLContext egl=EGL14.EGL_NO_CONTEXT;
    private EGLSurface window=EGL14.EGL_NO_SURFACE;
    private long scene;
    private WallpaperRenderer cosmic;
    private StereoSensor stereoSensor;
    private SceneOptions renderedOptions;
    private boolean stereoAodAnimation=true;
    private VivoEngineScene vivo;
    private boolean vivoDarkWallpaper;
    private float vivoAodMask=.5f;
    private long cosmicUntil;
    private boolean cosmicContinuousAod,cosmicContinuousHome,sailContinuousAod;
    private Surface attachedSurface;
    private SceneMotion motion;
    private TextureMotion textureMotion;
    private FrameMotion frameMotion;
    private final FrameSample frameSample=new FrameSample();
    private MaskFrames masks;
    private Choreographer choreographer;
    private final VsyncPacer pacer=new VsyncPacer();
    private boolean framePosted;
    private long frameTime;
    private int photo,decorator,width,height,mode=1;
    private int attachedWidth,attachedHeight;
    private int surfaceGeneration;
    private boolean forceFrame;
    private final int[] auxiliary=new int[6];
    private int homeWidth,homeHeight;
    private float aspect=1;
    private boolean visible=true;
    private Runnable firstFrame;
    private boolean firstFrameReported;

    private int[] aodPalette;

    private void readWallpaperPalette(){
        if(aodPalette!=null)return;
        try{android.app.WallpaperManager manager=android.app.WallpaperManager.getInstance(context);
            android.app.WallpaperColors colors=manager.getWallpaperColors(android.app.WallpaperManager.FLAG_SYSTEM);
            if(colors==null)colors=manager.getWallpaperColors(android.app.WallpaperManager.FLAG_LOCK);
            if(colors==null)return;
            int primary=colors.getPrimaryColor().toArgb();
            android.graphics.Color secondaryColor=colors.getSecondaryColor();
            android.graphics.Color tertiaryColor=colors.getTertiaryColor();
            aodPalette=new int[]{primary,secondaryColor!=null?secondaryColor.toArgb():primary,tertiaryColor!=null?tertiaryColor.toArgb():primary};
            AliveLog.i("AliveClean","wallpaper palette "+Integer.toHexString(primary));
        }catch(Throwable failure){AliveLog.w("AliveClean","wallpaper palette unavailable",failure);}
    }
    private boolean backdropCovered;
    private boolean stateReported;
    private long clockToken,seenClockToken;
    private final AodMotionWindow aodMotion=new AodMotionWindow();
    private final boolean preview;
    private AodRegion aodRegion;
    private int displayId;
    private volatile boolean closed;
    private final Runnable renderTask=()->render(frameTime);

    private final Choreographer.FrameCallback frame=this::onVsync;
    private final Runnable reloadTask=()->reloadScene();
    private final Runnable cropTask=()->applyPendingCrop();
    private final Object cropLock=new Object();
    private float pendingCropX=.5f,pendingCropY=.5f,pendingCropZoom=1;
    private boolean cropQueued,cropFinal;
    private final android.content.SharedPreferences.OnSharedPreferenceChangeListener changes=(p,key)->reload();
    RenderLoop(Context context){this(context,SceneOptions.APPLIED);}
    RenderLoop(Context context,String name){Context app=context.getApplicationContext();this.context=app!=null?app:context;preview=SceneOptions.DRAFT.equals(name);preferences=context.getSharedPreferences(name,0);thread.start();handler=new Handler(thread.getLooper());preferences.registerOnSharedPreferenceChangeListener(changes);}
    void onFirstFrame(Runnable listener){firstFrame=listener;}
    void attach(Surface surface,int w,int h){queueAttach(surface,w,h,false);}

    void attachFresh(Surface surface,int w,int h){queueAttach(surface,w,h,true);}
    private void queueAttach(Surface surface,int w,int h,boolean fresh){
        int generation=++surfaceGeneration;
        handler.post(()->attach(surface,w,h,0,fresh,generation));
    }

    void redraw(Surface surface,int w,int h){redraw(surface,w,h,0);}
    private void redraw(Surface surface,int w,int h,int attempt){
        int generation=++surfaceGeneration;
        handler.post(()->{
            if(generation!=surfaceGeneration){AliveLog.w("AliveClean","redraw stale generation "+generation+" != "+surfaceGeneration);return;}

            if(!surface.isValid()||w<1||h<1){
                if(attempt<20&&!closed){AliveLog.w("AliveClean","redraw retry "+attempt+" "+w+"x"+h+" valid="+surface.isValid());handler.postDelayed(()->redraw(surface,w,h,attempt+1),50);}
                else AliveLog.w("AliveClean","redraw skipped "+w+"x"+h+" valid="+surface.isValid());
                return;
            }
            if(surface==attachedSurface&&w==attachedWidth&&h==attachedHeight&&(scene!=0||cosmic!=null||vivo!=null)){
                AliveLog.i("AliveClean","redraw poke "+w+"x"+h+" valid="+surface.isValid());
                forceFrame=true;pacer.reset();schedule();return;
            }
            AliveLog.i("AliveClean","redraw rebuild "+w+"x"+h+" attached="+attachedWidth+"x"+attachedHeight+" valid="+surface.isValid());
            release();
            try{init(surface,w,h);}catch(Throwable e){AliveLog.e("AliveClean","Renderer redraw failed",e);release();return;}
            forceFrame=true;pacer.reset();schedule();
        });
    }
    private void attach(Surface surface,int w,int h,int attempt,boolean fresh,int generation){handler.post(()->{

        if(generation!=surfaceGeneration){AliveLog.w("AliveClean","attach stale generation "+generation+" != "+surfaceGeneration);return;}
        if(!surface.isValid()||w<1||h<1){
            if(attempt<40&&!closed){AliveLog.w("AliveClean","attach retry "+attempt+" "+w+"x"+h+" valid="+surface.isValid());handler.postDelayed(()->attach(surface,w,h,attempt+1,fresh,generation),50);}
            else AliveLog.w("AliveClean","attach skipped "+w+"x"+h+" valid="+surface.isValid());
            return;
        }
        if(!fresh&&surface==attachedSurface&&w==attachedWidth&&h==attachedHeight&&(scene!=0||cosmic!=null||vivo!=null)){pacer.reset();schedule();return;}
        release();
        try{init(surface,w,h);}catch(Exception e){AliveLog.e("AliveClean","Renderer initialization failed",e);release();return;}

        forceFrame=true;pacer.reset();schedule();
    });}

    void poke(){handler.post(()->{forceFrame=true;pacer.reset();schedule();});}

    void touch(int action,float x,float y){handler.post(()->{if(vivo!=null)vivo.touch(action,x,y);if(cosmic!=null&&action==0){cosmic.interaction();schedule();}});}
    void vivoDarkWallpaper(boolean dark){handler.post(()->{vivoDarkWallpaper=dark;if(vivo!=null){vivo.darkWallpaper(dark,renderingVisible());schedule();}});}
    void vivoCrop(float x,float y,float zoom){
        queueCrop(x,y,zoom,false);
    }
    void vivoCropNow(float x,float y,float zoom){
        queueCrop(x,y,zoom,true);
    }
    private void queueCrop(float x,float y,float zoom,boolean finish){
        synchronized(cropLock){
            if(closed)return;pendingCropX=x;pendingCropY=y;pendingCropZoom=zoom;cropFinal=finish;
            if(finish){handler.removeCallbacks(cropTask);cropQueued=true;handler.post(cropTask);}
            else if(!cropQueued){cropQueued=true;handler.postDelayed(cropTask,16);}
        }
    }
    private void applyPendingCrop(){
        PhotoViewport p=new PhotoViewport();boolean finish;
        synchronized(cropLock){cropQueued=false;p.restore(pendingCropX,pendingCropY,pendingCropZoom);finish=cropFinal;}
        if(vivo==null)return;
        try{vivo.crop(p,finish);schedule();}
        catch(Exception e){AliveLog.w("AliveClean","Vivo crop failed",e);}
    }
    void detach(){handler.post(()->{release();attachedSurface=null;});}
    void mode(int next){mode(next,true);}
    void clockTransition(long token){handler.post(()->{if(preview||token==seenClockToken)return;seenClockToken=token;clockToken=token;if(token!=0){if(renderingVisible())schedule();else reportClock(false);}});}
    private void reportClock(boolean submitted){
        long token=clockToken;clockToken=0;
        if(token!=0&&!preview){if(Diagnostics.TRACE)AliveLog.i("AliveClean","Frame clock token="+token+" submitted="+submitted);SceneChannel.frameReady(token,submitted);}
    }
    void aodRegion(AodRegion region,int display){
        if(region==null||region.displayId!=display)return;
        handler.post(()->{displayId=display;aodRegion=region;applyAodRegion();schedule();});
    }
    private void applyAodRegion(){
        if(cosmic!=null&&aodRegion!=null&&aodRegion.fits(displayId,width,height))cosmic.motion().follow(aodRegion.sceneX(),aodRegion.sceneY());
        if(motion!=null&&aodRegion!=null&&aodRegion.fits(displayId,width,height)){
            if(frameMotion==null)motion.followClock(aodRegion.sceneX(),aodRegion.sceneY());
            else frameMotion.followClock(aodRegion.sceneX(),aodRegion.sceneY());
        }
    }
    void mode(int next,boolean animate){mode(next,animate,Float.NaN,Float.NaN);}
    void mode(int next,boolean animate,float nightLevel,float aodMask){handler.post(()->{
        boolean lightSnapshot=(nightLevel==1f||nightLevel==.76f)&&aodMask>=0&&aodMask<1;
        if(lightSnapshot){vivoDarkWallpaper=nightLevel<1;vivoAodMask=aodMask;}
        if(mode!=next){stateReported=false;frameSample.clear();if(next==0)aodMotion.enter(System.nanoTime());else aodMotion.leave();}
        if(cosmic!=null&&mode!=next){cosmic.motion().change(next,animate);cosmicUntil=System.nanoTime()+5_000_000_000L;}

        if(vivo!=null&&(mode!=next||lightSnapshot)){vivo.transition(next,animate,vivoDarkWallpaper?.76f:1f,vivoAodMask);vivo.visible(renderingVisible());}
        int previous=mode;boolean visible=renderingVisible();
        mode=next;pacer.doze(next==0);updateCosmicLifecycle();if(textureMotion!=null)textureMotion.change(next,animate);if(motion!=null){if(frameMotion!=null){frameMotion.change(next,motion);if(!animate)frameMotion.finish();if(!visible)frameMotion.pause(true);}motion.change(next,visible);if(!animate)motion.finish();if(visible)motion.pause(false);if(previous!=next)AliveLog.i("AliveClean","scene motion "+previous+"->"+next+" animate="+animate+" visible="+visible+" pending="+motion.pending());}schedule();
    });}
    private boolean renderingVisible(){return visible&&!backdropCovered;}
    void backdrop(boolean covered){handler.post(()->{if(preview||backdropCovered==covered)return;backdropCovered=covered;updateRenderActivity();});}
    void visible(boolean value){handler.post(()->{if(visible==value)return;visible=value;updateRenderActivity();});}
    private void updateRenderActivity(){
        boolean active=renderingVisible();
        if(vivo!=null)vivo.visible(active);if(cosmic!=null)cosmic.motion().pause();updateCosmicLifecycle();
        if(motion!=null){motion.pause(!active);if(active&&motion.pending())AliveLog.i("AliveClean","scene motion start deferred visible=true mode="+mode);}if(frameMotion!=null)frameMotion.pause(!active);stopFrames();
        if(active){if(mode==0){long now=System.nanoTime();aodMotion.enter(now);cosmicUntil=now+5_000_000_000L;}pacer.reset();schedule();}
        else if(clockToken!=0)reportClock(false);
    }
    void resumeDisplay(){handler.post(()->{
        if(!renderingVisible())return;
        if(vivo!=null)vivo.visible(true);
        if(cosmic!=null)cosmic.interaction();

        stopFrames();pacer.reset();schedule();
    });}
    void reload(){if(!closed){handler.removeCallbacks(reloadTask);handler.post(reloadTask);}}
    private void reloadScene(){if(scene==0&&cosmic==null&&vivo==null)return;try{
        SceneOptions options=new SceneOptions(preferences);
        if(cosmic instanceof StereoRenderer&&renderedOptions!=null&&options.cosmic==renderedOptions.cosmic&&options.cosmicKeepLock==renderedOptions.cosmicKeepLock){
            ((StereoRenderer)cosmic).configure(options.stereoSensitivity,options.stereoAodAnimation);
            renderedOptions=options;stereoAodAnimation=options.stereoAodAnimation;updateCosmicLifecycle();schedule();return;
        }
        if(vivo!=null&&vivo.updateSensitivity(options.vivo))return;
        if(vivo!=null||options.vivo!=null){Surface surface=attachedSurface;release();if(surface!=null&&surface.isValid()){init(surface,width,height);schedule();}return;}
        if((cosmic!=null)!=(options.cosmic!=0)){
            Surface surface=attachedSurface;release();
            if(surface!=null&&surface.isValid()){init(surface,width,height);schedule();}return;
        }if(motion!=null){motion.close();motion=null;}closeFrame();closeCosmic();if(scene!=0)NativeScene.destroy(scene);scene=0;GLES30.glDeleteTextures(1,new int[]{photo},0);photo=0;clearAuxiliary();if(options.cosmic==0)photo=loadPhoto(options.photo,true);createScene(options);schedule();}catch(Exception e){AliveLog.e("AliveClean","Scene reload failed",e);release();}}
    void close(){if(closed)return;closed=true;preferences.unregisterOnSharedPreferenceChangeListener(changes);handler.post(()->{release();thread.quitSafely();});}
    private void schedule(){
        if(!((renderingVisible()||forceFrame)&&(scene!=0||cosmic!=null||vivo!=null)))return;
        if(choreographer!=null&&!framePosted){framePosted=true;choreographer.postFrameCallback(frame);}
        // Choreographer 在 doze/息屏期间可能整段不发 vsync；一旦回调不来，framePosted 会
        // 永久卡在 true，之后 schedule() 全是空操作 —— 表现就是"surface 在、画面永远不出"。
        // 这里兜一个 120ms 的看门狗：只在 vsync 真的没来时直接渲染一帧。
        handler.removeCallbacks(vsyncWatchdog);
        handler.postDelayed(vsyncWatchdog,120);
    }
    private final Runnable vsyncWatchdog=()->{
        if(!framePosted)return;
        framePosted=false;
        if(!(renderingVisible()||forceFrame)||(scene==0&&cosmic==null&&vivo==null))return;
        AliveLog.i("AliveClean","vsync watchdog: drawing without Choreographer mode="+mode);
        try{frameTime=android.os.SystemClock.uptimeMillis()*1_000_000L;render(frameTime);}
        catch(Throwable error){AliveLog.w("AliveClean","vsync watchdog render failed",error);}
    };
    private void onVsync(long time){handler.removeCallbacks(vsyncWatchdog);framePosted=false;frameTime=time;handler.post(renderTask);}
    private void stopFrames(){if(choreographer!=null)choreographer.removeFrameCallback(frame);framePosted=false;handler.removeCallbacks(renderTask);handler.removeCallbacks(vsyncWatchdog);}
    private void updateCosmicLifecycle(){
        try{
            if(cosmic!=null){
                cosmic.continuous(preview||(mode==0?cosmicContinuousAod:cosmicContinuousHome));
                cosmic.visible(renderingVisible());
            }
            if(stereoSensor!=null)stereoSensor.setActive(renderingVisible()&&(mode!=0||stereoAodAnimation));
        }catch(RuntimeException failure){AliveLog.e("AliveClean","Wallpaper lifecycle failed",failure);release();}
    }
    private void closeCosmic(){
        if(stereoSensor!=null){stereoSensor.close();stereoSensor=null;}
        if(cosmic!=null){cosmic.close();cosmic=null;}
    }
    private void createScene(SceneOptions options)throws IOException{
        renderedOptions=options;stereoAodAnimation=options.stereoAodAnimation;
        if(options.vivo!=null){
            try{
                VivoEngineRuntime runtime=VivoEngineRuntime.prepare(context).get(20,java.util.concurrent.TimeUnit.SECONDS);
                choreographer=Choreographer.getInstance();
                try(VivoImages images=VivoImages.load(context,runtime,options.vivo)){
                    vivo=new VivoEngineScene(runtime,options.vivo,width,height,images.photos,images.subject,images.paint,preview,()->handler.post(()->schedule()));
                }
                vivo.transition(mode,false,vivoDarkWallpaper?.76f:1f,vivoAodMask);vivo.visible(renderingVisible());pacer.reset();return;
            }catch(Exception e){throw new IOException("Vivo engine initialization failed",e);}
        }
        cosmicContinuousAod=options.cosmicContinuousAod;cosmicContinuousHome=options.cosmicContinuousHome;
        sailContinuousAod=options.cosmic==0&&options.aod==0&&options.sailContinuousAod;
        if(options.cosmic!=0){
            boolean dark=(context.getResources().getConfiguration().uiMode&android.content.res.Configuration.UI_MODE_NIGHT_MASK)==android.content.res.Configuration.UI_MODE_NIGHT_YES;

            // CosmicMotion ships three palettes (aod/keyguard/unlock); the aod one draws the
            // wallpaper as a small sphere on black, which is exactly what the always-on screen
            // shows. Forcing the keyguard palette here (85cfa94) made every AOD preview -- the
            // home cards and the editor's 息屏 page -- render the lock screen instead of the ball.
            int palette=mode;
            cosmic=WallpaperRenderer.create(context.getAssets(),options.cosmic,dark,width,height,palette,options.cosmicKeepLock,this::schedule);
            if(cosmic instanceof StereoRenderer)((StereoRenderer)cosmic).configure(options.stereoSensitivity,options.stereoAodAnimation);
            if(cosmic instanceof StereoRenderer)stereoSensor=new StereoSensor(context,handler,(x,y,z,time)->{
                if(cosmic instanceof StereoRenderer){((StereoMotion)cosmic.motion()).gyroscope(x,y,z,time);if(cosmic.motion().active())schedule();}
            });
            updateCosmicLifecycle();
            cosmicUntil=System.nanoTime()+5_000_000_000L;
            applyAodRegion();choreographer=Choreographer.getInstance();pacer.doze(mode==0);pacer.reset();return;
        }
        int lock=options.lock,home=options.home;
        if(mode==0)readWallpaperPalette();
        scene=NativeScene.create(width,height,mode,options.aod,lock,home);
        if(scene==0)throw new IllegalStateException("Native scene creation failed; see shader log");
        pacer.doze(mode==0);
        motion=new SceneMotion(width,height,mode,PhotoStyle.supported(options.aod)?-1:options.aod,lock,home,false);
        textureMotion=new TextureMotion(width,height,mode,lock,home);
        if(PhotoStyle.supported(options.aod)){
            PhotoStyle style=PhotoStyle.get(options.aod);
            frameMotion=new FrameMotion(width,height,mode,options.pairedFrame(),style);
            if(options.pairedFrame()){
                auxiliary[5]=loadPhoto(options.framePhoto,false);
                NativeScene.texture(scene,5,auxiliary[5],homeWidth,homeHeight);
                NativeScene.frameCrop(scene,options.frameX,options.frameY,options.frameSize,options.frameAngle);
            }
            int[] id=new int[1];GLES30.glGenTextures(1,id,0);auxiliary[0]=id[0];GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,id[0]);
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_MIN_FILTER,GLES30.GL_LINEAR);GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_MAG_FILTER,GLES30.GL_LINEAR);
            GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_WRAP_S,GLES30.GL_CLAMP_TO_EDGE);GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_WRAP_T,GLES30.GL_CLAMP_TO_EDGE);
            GLES30.glTexImage2D(GLES30.GL_TEXTURE_2D,0,GLES30.GL_RGB,1,1,0,GLES30.GL_RGB,GLES30.GL_UNSIGNED_BYTE,ByteBuffer.allocateDirect(3));
            NativeScene.texture(scene,0,id[0],style.size,style.size);

            masks=new MaskFrames(context.getAssets(),handler,()->{if(visible&&scene!=0)render(frameTime);},style);
        }
        applyAodRegion();
        choreographer=Choreographer.getInstance();pacer.reset();
        NativeScene.tint(scene,options.color);
        if(!options.pairedFrame()&&!options.followLock&&!options.homePhoto.equals(options.photo)&&new File(context.getFilesDir(),options.homePhoto).isFile()){
            auxiliary[4]=loadPhoto(options.homePhoto,false);NativeScene.texture(scene,4,auxiliary[4],homeWidth,homeHeight);
        }
        if(options.lock==3||options.home==9){loadAuxiliary(1,"shader/photo/lock/groundGlass/gray.png",false);loadAuxiliary(2,"shader/photo/lock/groundGlass/mask.png",true);}
        if(options.aod==101)loadAuxiliary(3,"shader/photo/aod/fullAod/gray.png",false);
    }
    private void loadAuxiliary(int slot,String path,boolean repeat)throws IOException{
        try(InputStream in=context.getAssets().open(path)){
            Bitmap b=BitmapFactory.decodeStream(in);if(b==null)throw new IOException("Invalid texture: "+path);
            try{auxiliary[slot]=upload(b);if(repeat){GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_WRAP_S,GLES30.GL_REPEAT);GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_WRAP_T,GLES30.GL_REPEAT);}NativeScene.texture(scene,slot,auxiliary[slot],b.getWidth(),b.getHeight());}finally{b.recycle();}
        }
    }
    private void clearAuxiliary(){GLES30.glDeleteTextures(auxiliary.length,auxiliary,0);java.util.Arrays.fill(auxiliary,0);}
    private void init(Surface surface,int w,int h)throws IOException{
        if(!NativeScene.available){if(!nativeMissingLogged){nativeMissingLogged=true;AliveLog.w("AliveClean","native library unavailable; render loop idle (restart needed after update)");}return;}
        SceneOptions options=new SceneOptions(preferences);attachedSurface=surface;
        width=w;height=h;attachedWidth=w;attachedHeight=h;display=EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY);
        int[] version=new int[2];if(!EGL14.eglInitialize(display,version,0,version,1))throw new IllegalStateException("eglInitialize");
        EGLConfig[] configs=new EGLConfig[1];int[] count=new int[1];
        int[] attrs={EGL14.EGL_RENDERABLE_TYPE,0x40,EGL14.EGL_SURFACE_TYPE,EGL14.EGL_WINDOW_BIT,EGL14.EGL_RED_SIZE,8,EGL14.EGL_GREEN_SIZE,8,EGL14.EGL_BLUE_SIZE,8,EGL14.EGL_ALPHA_SIZE,8,EGL14.EGL_DEPTH_SIZE,options.cosmic!=0?16:0,EGL14.EGL_NONE};
        if(options.cosmic!=0)configs[0]=CosmicEgl.choose(display,EGL14.EGL_WINDOW_BIT);
        else if(!EGL14.eglChooseConfig(display,attrs,0,configs,0,1,count,0)||count[0]==0)throw new IllegalStateException("No GLES3 EGL config");
        egl=EGL14.eglCreateContext(display,configs[0],EGL14.EGL_NO_CONTEXT,new int[]{EGL14.EGL_CONTEXT_CLIENT_VERSION,3,EGL14.EGL_NONE},0);
        window=options.vivo!=null?VivoColorSpace.window(display,configs[0],surface):EGL14.eglCreateWindowSurface(display,configs[0],surface,new int[]{EGL14.EGL_NONE},0);
        if(!EGL14.eglMakeCurrent(display,window,window,egl))throw new IllegalStateException("eglMakeCurrent");
        int[] eglW=new int[1],eglH=new int[1];
        EGL14.eglQuerySurface(display,window,EGL14.EGL_WIDTH,eglW,0);
        EGL14.eglQuerySurface(display,window,EGL14.EGL_HEIGHT,eglH,0);
        AliveLog.i("AliveClean","EGL init requested="+w+"x"+h+" eglSurface="+eglW[0]+"x"+eglH[0]+" valid="+surface.isValid());
        EGL14.eglSwapInterval(display,1);if(options.cosmic==0&&options.vivo==null)photo=loadPhoto(options.photo,true);
        try(InputStream in=context.getAssets().open("shader/photo/aod/lensPhoto/lens_decorator.png")){Bitmap b=BitmapFactory.decodeStream(in);if(b==null)throw new IOException("Invalid decorator");decorator=upload(b);b.recycle();}
        catch(Exception missing){

            AliveLog.w("AliveClean","lens decorator unavailable, using blank texture",missing);
            Bitmap blank=Bitmap.createBitmap(1,1,Bitmap.Config.ARGB_8888);decorator=upload(blank);blank.recycle();
        }
        createScene(options);if(Diagnostics.TRACE)AliveLog.i("AliveClean","GLES3 scene ready "+w+"x"+h);
    }
    private static final int KIND_PROVIDER=0;
    private static final int KIND_FILE=1;
    /**
     * 取缓存。provider 来源的位图内容会随应用壁纸变化，因此只认「同一场景名」的缓存；
     * 文件来源另有大小+修改时间兜底。
     */
    private static Bitmap cachedPhoto(String key,int kind){
        synchronized(PHOTO_CACHE_LOCK){
            if(cachedPhotoBitmap==null||cachedPhotoKey==null||cachedPhotoBitmap.isRecycled()){
                AliveLog.i("AliveClean","photo cache miss kind="+kind+" reason=empty key="+key);
                return null;
            }
            if(!cachedPhotoKey.equals(key)){
                AliveLog.i("AliveClean","photo cache miss kind="+kind+" reason=key\n  have="+cachedPhotoKey+"\n  want="+key);
                return null;
            }
            return cachedPhotoBitmap;
        }
    }
    private static void storePhoto(String key,int kind,Bitmap bitmap){
        AliveLog.i("AliveClean","photo cache store kind="+kind+" key="+key);
        synchronized(PHOTO_CACHE_LOCK){
            if(cachedPhotoBitmap!=null&&cachedPhotoBitmap!=bitmap&&!cachedPhotoBitmap.isRecycled())cachedPhotoBitmap.recycle();
            cachedPhotoBitmap=bitmap;cachedPhotoKey=key;
        }
    }
    /** 上传并保留缓存位图（不能再 recycle，缓存要继续用）。 */
    private int uploadBitmap(Bitmap bitmap){
        int id=upload(bitmap);
        return id;
    }
    private int loadPhoto(String name,boolean primary){
        File file=new File(context.getFilesDir(),name);

        if(primary&&!preview){
            try{
                String providerKey="provider|"+AodWallpaperProvider.PHOTO+"|"+name;
                Bitmap cached=cachedPhoto(providerKey,KIND_PROVIDER);
                if(cached!=null){
                    AliveLog.i("AliveClean","aod photo cache hit "+cached.getWidth()+"x"+cached.getHeight());
                    aspect=(float)cached.getWidth()/cached.getHeight();return uploadBitmap(cached);
                }
                Bitmap fromProvider=PhotoDecode.decode(context.getContentResolver(),AodWallpaperProvider.PHOTO,2048);
                if(fromProvider!=null){AliveLog.i("AliveClean","aod photo from provider "+fromProvider.getWidth()+"x"+fromProvider.getHeight());aspect=(float)fromProvider.getWidth()/fromProvider.getHeight();storePhoto(providerKey,KIND_PROVIDER,fromProvider);int id=upload(fromProvider);return id;}
                else AliveLog.w("AliveClean","aod provider returned no bitmap");
            }catch(Throwable failure){AliveLog.w("AliveClean","aod provider read failed",failure);}

            File external=context.getExternalFilesDir(null);
            File fromExternal=external==null?null:new File(external,SceneOptions.WALLPAPER);
            File internal=new File(context.getFilesDir(),SceneOptions.WALLPAPER);
            // Never fall back to the old SystemUI debug cache: it is a black-and-white
            // moon image unrelated to the user's wallpaper.  Missing files use the
            // gradient placeholder below.
            if(fromExternal!=null&&fromExternal.isFile())file=fromExternal;else if(internal.isFile())file=internal;
        }
        AliveLog.i("AliveClean","loadPhoto "+file.getAbsolutePath()+" exists="+file.isFile()+" mode="+mode+" primary="+primary);
        Bitmap bitmap=null;
        String fileKey=file.getAbsolutePath()+"|"+file.length()+"|"+file.lastModified()+"|"+primary;
        if(file.exists()){
            bitmap=cachedPhoto(fileKey,KIND_FILE);
            if(bitmap!=null)AliveLog.i("AliveClean","photo cache hit "+fileKey);
            if(bitmap==null)try{
                bitmap=PhotoDecode.decode(file,2048);
                if(bitmap!=null)storePhoto(fileKey,KIND_FILE,bitmap);
            }catch(IOException e){AliveLog.e("AliveClean","Photo decode failed",e);}
        }
        if(bitmap==null){bitmap=Bitmap.createBitmap(720,1440,Bitmap.Config.ARGB_8888);Canvas canvas=new Canvas(bitmap);Paint p=new Paint(3);p.setShader(new LinearGradient(0,0,720,1440,new int[]{0xff183955,0xffce865c,0xff5b526e},null,Shader.TileMode.CLAMP));canvas.drawRect(0,0,720,1440,p);p.setShader(null);p.setColor(0xffe6b282);canvas.drawCircle(490,480,230,p);p.setColor(0xff254452);canvas.drawOval(-220,800,930,1880,p);}
        if(primary)aspect=(float)bitmap.getWidth()/bitmap.getHeight();else{homeWidth=bitmap.getWidth();homeHeight=bitmap.getHeight();}return uploadBitmap(bitmap);
    }
    private static int upload(Bitmap bitmap){
        int[] ids=new int[1];GLES30.glGenTextures(1,ids,0);GLES30.glBindTexture(GLES30.GL_TEXTURE_2D,ids[0]);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_MIN_FILTER,GLES30.GL_LINEAR);GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_MAG_FILTER,GLES30.GL_LINEAR);
        GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_WRAP_S,GLES30.GL_CLAMP_TO_EDGE);GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D,GLES30.GL_TEXTURE_WRAP_T,GLES30.GL_CLAMP_TO_EDGE);
        if(bitmap.hasAlpha()&&bitmap.isPremultiplied()){

            int w=bitmap.getWidth(),h=bitmap.getHeight();int[] pixels=new int[w*h];bitmap.getPixels(pixels,0,w,0,0,w,h);
            ByteBuffer rgba=ByteBuffer.allocateDirect(pixels.length*4);
            for(int p:pixels)rgba.put((byte)(p>>16)).put((byte)(p>>8)).put((byte)p).put((byte)(p>>24));
            rgba.flip();GLES30.glTexImage2D(GLES30.GL_TEXTURE_2D,0,GLES30.GL_RGBA,w,h,0,GLES30.GL_RGBA,GLES30.GL_UNSIGNED_BYTE,rgba);
        }else GLUtils.texImage2D(GLES30.GL_TEXTURE_2D,0,bitmap,0);
        return ids[0];
    }
    private void render(long time){

        boolean forced=forceFrame;forceFrame=false;
        if((!renderingVisible()&&!forced)||(scene==0&&cosmic==null&&vivo==null))return;
        if(vivo!=null){try{vivo.draw();if(!swap("vivo"))throw new IllegalStateException("Vivo EGL swap failed");if(clockToken!=0)reportClock(false);if(vivo.transitionActive()||forced)schedule();}catch(Exception e){AliveLog.e("AliveClean","Vivo frame failed",e);release();}return;}
        if(cosmic!=null){
            try{
                boolean continuous=preview||(mode==0?cosmicContinuousAod:cosmicContinuousHome);
                float speed=continuous?1:Math.max(0,Math.min(1,(cosmicUntil-time)/3_000_000_000f));
                cosmic.motion().advance(time,speed);
                boolean ownsTiming=cosmic instanceof FlymeVideoRenderer||cosmic instanceof StereoRenderer;
                boolean moving=cosmic.motion().active()||(!ownsTiming&&(continuous||time<cosmicUntil));
                if(forced||pacer.due(time)||!moving){
                    cosmic.render();
                    if(!swap("cosmic"))throw new IllegalStateException("Cosmic EGL swap failed");
                }
                if(moving)schedule();
            }catch(Exception e){AliveLog.e("AliveClean","Cosmic frame failed",e);release();}
            return;
        }
        try{
            boolean resumedSample=frameMotion!=null&&frameSample.pending;
            motion.advance(time,preview||mode!=0||sailContinuousAod?1f:aodMotion.speed(time));
            if(textureMotion!=null)textureMotion.advance(time);
            if(frameMotion!=null){

                if(!frameSample.pending)frameSample.captureTextures(textureMotion);
                frameSample.capture(frameMotion,motion,SystemClock.uptimeMillis());
                if(!masks.upload(frameSample.mask,auxiliary[0])){if(!masks.failed())schedule();else reportClock(false);return;}
                if(!NativeScene.frame(scene,frameSample.effect))throw new IllegalStateException("Invalid frame effect packet");
            }
            if(Diagnostics.TRACE&&mode==0&&!stateReported&&motion.values[0]>.999f){stateReported=true;AliveLog.i("AliveClean","AOD frame lensScale="+motion.values[4]+" x="+motion.values[8]+" y="+motion.values[9]+" viewport="+width+"x"+height+" photo="+photo);}
            boolean moving=textureMotion.active()||motion.transitionActive()||(frameMotion!=null?frameMotion.active():mode==0&&(preview||sailContinuousAod||aodMotion.running(time)));
            if(forced||pacer.due(time)||!moving){
                long probeStart=probeBegin();
                boolean okEff=NativeScene.effects(scene,frameMotion==null?textureMotion.packet():frameSample.textures);
                long probeAfterEff=probeEnabled?System.nanoTime():0L;
                if(!okEff)throw new IllegalStateException("Invalid texture effect packet");
                boolean okRen=NativeScene.render(scene,photo,decorator,aspect,frameMotion==null?motion.packet():frameSample.base);
                long probeAfterRen=probeEnabled?System.nanoTime():0L;
                if(!okRen)throw new IllegalStateException("Invalid native frame packet");
                boolean okSw=swap("native");
                probeFrame(probeStart,probeAfterEff,probeAfterRen);
                if(!okSw)throw new SurfaceLost("EGL swap error "+EGL14.eglGetError());
                if(clockToken!=0&&mode==1){if(frameMotion==null)reportClock(false);else if(frameSample.expanded)reportClock(true);}
                if(frameMotion!=null){
                    if(Diagnostics.TRACE){long wait=SystemClock.uptimeMillis()-frameSample.capturedAt;if(wait>80)AliveLog.i("AliveClean","Frame sample mask="+frameSample.mask+" waitMs="+wait);}
                    frameSample.clear();
                }
            }
            if(moving||frameSample.pending||resumedSample||forced)schedule();
        }catch(Throwable e){
            AliveLog.e("AliveClean","Frame failed",e);
            Surface lost=e instanceof SurfaceLost?attachedSurface:null;
            int lostWidth=attachedWidth,lostHeight=attachedHeight;
            release();
            // surface 被系统先销毁导致的 swap 失败不是渲染缺陷：把场景拆掉后自己重挂一次，
            // 免得宿主不再给新 surface 时永久黑着（日志里的 alpha=0 就是这么来的）。
            if(lost!=null)retrySurface(lost,lostWidth,lostHeight);
        }
    }
    private void closeFrame(){if(textureMotion!=null){textureMotion.close();textureMotion=null;}if(scene!=0)reportClock(false);frameSample.clear();if(frameMotion!=null){frameMotion.close();frameMotion=null;}if(masks!=null){masks.close();masks=null;}}
    private boolean swap(String tag){
        boolean ok=EGL14.eglSwapBuffers(display,window);
        if(ok)surfaceLostRetries=0;
        if(!ok)AliveLog.e("AliveClean","eglSwapBuffers failed "+tag+" err="+EGL14.eglGetError()+" size="+attachedWidth+"x"+attachedHeight);
        if(ok&&!firstFrameReported){
            firstFrameReported=true;
            final Runnable listener=firstFrame;
            firstFrame=null;
            if(listener!=null)handler.post(listener);
        }
        return ok;
    }
    /** 渲染中途 surface 失效（swap 返回 false）。与着色器/纹理错误区分开，前者可以自愈。 */
    private static final class SurfaceLost extends IllegalStateException{
        SurfaceLost(String message){super(message);}
    }

    /** 连续自愈次数上限，避免 surface 真死了还在无限重建。 */
    /**
     * 帧耗时探针：关着时每帧只多一次布尔判断，开着时每 30 帧汇总打一行。
     * 指标口径与 docs/kb/troubleshooting/04 一致：eff = effects，ren = render，sw = swap，gap = 帧起点间隔。
     */
    private boolean probeEnabled;
    private long probeCheckedAt;
    private long probeLastStart;
    private int probeFrames, probeSwOver80, probeSwOver40;
    private long probeEffMax, probeRenMax, probeSwMax, probeGapMax;
    private long probeBegin(){
        long now=System.nanoTime();
        if(!probeEnabled){
            long uptime=SystemClock.uptimeMillis();
            if(uptime-probeCheckedAt>=1000L){
                probeCheckedAt=uptime;
                probeEnabled=RenderProbe.enabled(context);
            }
        }
        if(!probeEnabled)return 0L;
        if(probeLastStart!=0L){
            long gap=now-probeLastStart;
            if(gap>probeGapMax)probeGapMax=gap;
        }
        probeLastStart=now;
        return now;
    }
    private void probeFrame(long start,long afterEff,long afterRen){
        if(!probeEnabled||start==0L)return;
        long sw=System.nanoTime()-afterRen;
        long eff=(afterEff==0L?0L:afterEff-start);
        long ren=(afterEff==0L?0L:afterRen-afterEff);
        if(eff>probeEffMax)probeEffMax=eff;
        if(ren>probeRenMax)probeRenMax=ren;
        if(sw>probeSwMax)probeSwMax=sw;
        if(sw>=80_000_000L)probeSwOver80++;
        // 只记录异常帧：逐帧写 logcat 本身会给渲染加负荷，等于用观察者影响被测对象。
        if(sw>=40_000_000L)
            AliveLog.i("AliveClean","probe slow t="+SystemClock.uptimeMillis()+" mode="+mode+" sw="+(sw/1_000_000f));
        if(sw>=40_000_000L)probeSwOver40++;
        if(++probeFrames<30)return;
        AliveLog.i("AliveClean","probe frame n="+probeFrames+" mode="+mode
                +" effMax="+probeEffMax/1_000_000f+"ms renMax="+probeRenMax/1_000_000f+"ms"
                +" swMax="+probeSwMax/1_000_000f+"ms gapMax="+probeGapMax/1_000_000f+"ms"
                +" sw>=40ms:"+probeSwOver40+" sw>=80ms:"+probeSwOver80);
        probeFrames=0;probeEffMax=probeRenMax=probeSwMax=probeGapMax=0;
        probeSwOver40=probeSwOver80=0;
    }

    private int surfaceLostRetries;
    /**
     * AOD 照片的解码结果缓存。EGL 上下文每轮进入 AOD 都会重建，纹理 id 不能跨轮复用，
     * 但**解码**（provider 实测 146–199ms）可以：这份时间正好落在息屏转场里。
     * 键由「来源 + 文件大小 + 修改时间」组成，换照片或重新应用壁纸会自动 miss。
     */
    private static Bitmap cachedPhotoBitmap;
    private static String cachedPhotoKey;
    private static final Object PHOTO_CACHE_LOCK=new Object();
    private boolean nativeMissingLogged;
    private void retrySurface(Surface surface,int w,int h){
        if(closed)return;
        if(!surface.isValid()||w<1||h<1){
            if(surfaceLostRetries<20){
                surfaceLostRetries++;
                AliveLog.w("AliveClean","surface lost: retry "+surfaceLostRetries+" "+w+"x"+h+" valid="+surface.isValid());
                handler.postDelayed(()->retrySurface(surface,w,h),100);
            }else AliveLog.w("AliveClean","surface lost: give up after "+surfaceLostRetries);
            return;
        }
        AliveLog.w("AliveClean","surface lost: reattaching "+w+"x"+h);
        attachFresh(surface,w,h);
    }

    private void release(){stopFrames();if(vivo!=null){vivo.close();vivo=null;}closeCosmic();handler.removeCallbacks(reloadTask);closeFrame();if(motion!=null){motion.close();motion=null;}if(display!=EGL14.EGL_NO_DISPLAY){if(scene!=0){NativeScene.destroy(scene);scene=0;}clearAuxiliary();GLES30.glDeleteTextures(2,new int[]{photo,decorator},0);photo=decorator=0;EGL14.eglMakeCurrent(display,EGL14.EGL_NO_SURFACE,EGL14.EGL_NO_SURFACE,EGL14.EGL_NO_CONTEXT);if(window!=EGL14.EGL_NO_SURFACE)EGL14.eglDestroySurface(display,window);if(egl!=EGL14.EGL_NO_CONTEXT)EGL14.eglDestroyContext(display,egl);EGL14.eglTerminate(display);EGL14.eglReleaseThread();}display=EGL14.EGL_NO_DISPLAY;egl=EGL14.EGL_NO_CONTEXT;window=EGL14.EGL_NO_SURFACE;attachedSurface=null;attachedWidth=attachedHeight=0;}
}
