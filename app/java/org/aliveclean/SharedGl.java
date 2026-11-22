package org.aliveclean;

import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.view.Surface;

/**
 * 进程级的 GL 资源持有者。
 *
 * <p>实测：每次熄屏 AOD 都会新建一个 {@link RenderLoop}（旧实例 close），所以资源放在实例字段里
 * 毫无意义。这里把 GL 资源放到静态持有者上，新实例只创建自己的 window surface。</p>
 *
 * <p>并发：acquire/release/publish 都在 {@link #LOCK} 内，持锁期间只做 EGL/GL 调用。</p>
 */
final class SharedGl {
    private static final Object LOCK=new Object();

    /** 一次租约：本实例的 window 加上共享资源的句柄。 */
    static final class Lease {
        EGLDisplay display=EGL14.EGL_NO_DISPLAY;
        EGLContext context=EGL14.EGL_NO_CONTEXT;
        EGLSurface window=EGL14.EGL_NO_SURFACE;
        long scene;
        int photo;
        int decorator;
        int[] auxiliary;
        float aspect;
    }

    private static EGLDisplay display=EGL14.EGL_NO_DISPLAY;
    private static EGLContext context=EGL14.EGL_NO_CONTEXT;
    private static EGLConfig config;
    /** 当前持有上下文的 window：只有它被释放时才需要解绑 current。 */
    private static EGLSurface activeWindow=EGL14.EGL_NO_SURFACE;

    private static long scene;
    private static int photo,decorator;
    private static int[] auxiliary=new int[6];
    private static float aspect=1f;
    private static int sceneW,sceneH,sceneAod,sceneLock,sceneHome;
    private static int sceneCosmic;
    private static String sceneVivo="";
    /** 发布当前 scene 的实例令牌，以及当前未归还的 window 租约数。 */
    private static long sceneOwner;
    private static int leases;
    /** 属主已经退出但还有租约在用时置位：最后一个租约归还时由它销毁 scene。 */
    private static boolean orphan;
    private static String scenePhotoKey="";

    static boolean available(){
        return display!=EGL14.EGL_NO_DISPLAY&&context!=EGL14.EGL_NO_CONTEXT&&config!=null;
    }

    /**
     * 资源键一致且上下文可用时，只建 window 并挂到共享上下文上；否则返回 null。
     *
     * <p>键必须覆盖"渲染什么"的全部输入：尺寸、aod/lock/home 样式、照片文件，
     * **以及 cosmic / vivo 这两个系列本身**。少了后两项时，"从照片系列切到动态壁纸系列"
     * 会命中照片场景的旧键而被复用 —— 表现为第一次点应用画面不变，要再点一次才切过去。</p>
     */
    static Lease acquire(Surface surface,int w,int h,int aod,int lock,int home,String photoKey,int cosmic,String vivoKey){
        synchronized(LOCK){
            if(!available()||scene==0)return null;
            if(sceneW!=w||sceneH!=h||sceneAod!=aod||sceneLock!=lock||sceneHome!=home)return null;
            if(sceneCosmic!=cosmic)return null;
            if(!sceneVivo.equals(vivoKey==null?"":vivoKey))return null;
            if(!photoKey.equals(scenePhotoKey))return null;
            // 现场重新选 config：首实例存下的那个可能与本进程当前状态不匹配（实测用它会
            // 12289 EGL_BAD_NATIVE_WINDOW，而同一 Surface 上现场选的 config 正常）。
            EGLConfig fresh=chooseNativeConfig();
            if(fresh!=null)config=fresh;
            EGLSurface window=EGL14.EGL_NO_SURFACE;
            for(int attempt=0;attempt<4;attempt++){
                window=EGL14.eglCreateWindowSurface(display,config,surface,new int[]{EGL14.EGL_NONE},0);
                if(window!=EGL14.EGL_NO_SURFACE)break;
                if(attempt==3){
                    AliveLog.w("AliveClean","shared gl: create window failed err="+EGL14.eglGetError()+" valid="+surface.isValid());
                    return null;
                }
                try{Thread.sleep(20);}catch(InterruptedException ignored){}
            }
            if(!EGL14.eglMakeCurrent(display,window,window,context)){
                AliveLog.w("AliveClean","shared gl: make current failed err="+EGL14.eglGetError());
                EGL14.eglDestroySurface(display,window);
                return null;
            }
            // swap interval 与 surface 绑定，重挂必须重设。
            EGL14.eglSwapInterval(display,1);
            activeWindow=window;leases++;
            int[] qw=new int[1],qh=new int[1];
            boolean qok=EGL14.eglQuerySurface(display,window,EGL14.EGL_WIDTH,qw,0)&
                    EGL14.eglQuerySurface(display,window,EGL14.EGL_HEIGHT,qh,0);
            AliveLog.i("AliveClean","shared gl: window query ok="+qok+" "+qw[0]+"x"+qh[0]
                    +" current="+(EGL14.eglGetCurrentSurface(EGL14.EGL_DRAW)==window)
                    +" ctxOk="+(EGL14.eglGetCurrentContext()==context));
            Lease lease=new Lease();
            lease.display=display;lease.context=context;
            lease.window=window;lease.scene=scene;lease.photo=photo;lease.decorator=decorator;
            lease.auxiliary=auxiliary.clone();lease.aspect=aspect;
            AliveLog.i("AliveClean","shared gl: reused scene="+scene+" "+w+"x"+h+" surface="+System.identityHashCode(surface)+" config="+System.identityHashCode(config));
            return lease;
        }
    }

    /** 与全量初始化完全一致的 config 选择。 */
    static EGLConfig chooseNativeConfig(){
        int[] attrs={
                EGL14.EGL_RENDERABLE_TYPE,0x40,
                EGL14.EGL_SURFACE_TYPE,EGL14.EGL_WINDOW_BIT,
                EGL14.EGL_RED_SIZE,8,EGL14.EGL_GREEN_SIZE,8,
                EGL14.EGL_BLUE_SIZE,8,EGL14.EGL_ALPHA_SIZE,8,
                EGL14.EGL_DEPTH_SIZE,0,
                EGL14.EGL_NONE};
        EGLConfig[] configs=new EGLConfig[1];
        int[] count=new int[1];
        if(!EGL14.eglChooseConfig(display,attrs,0,configs,0,1,count,0)||count[0]==0)return null;
        return configs[0];
    }
    /** 接管本实例刚建好的 EGL 对象（首个实例走这条路）。 */
    static void adopt(EGLDisplay d,EGLContext c,EGLConfig cfg){
        synchronized(LOCK){display=d;context=c;config=cfg;}
    }

    /** 登记资源供后续实例复用；旧 scene 若被替换则销毁，避免泄漏。 */
    /** 还有别人在用我们发布的 scene 时不能销毁。 */
    static boolean leasedElsewhere(long ownerToken,long ownScene){
        synchronized(LOCK){return leases>0&&scene==ownScene&&(ownerToken==0||ownerToken==sceneOwner||true);}
    }

    /** 属主把 scene 留给租约方：清掉属主标记并挂 orphan。 */
    static void handOver(long ownerToken,long ownScene){
        synchronized(LOCK){
            if(scene!=ownScene||(sceneOwner!=0&&sceneOwner!=ownerToken))return;
            sceneOwner=0;orphan=leases>0;
        }
    }

    static void publish(long ownerToken,long sceneId,int photoId,int decoratorId,int[] auxiliaryIds,float photoAspect,
                        int w,int h,int aod,int lock,int home,String photoKey,int cosmic,String vivoKey){
        synchronized(LOCK){
            // 有实例正在租用它时不要替换登记：那会让正在渲染的实例与登记表不一致。
            if(leases>0&&scene!=0&&scene!=sceneId)return;
            // 绝不在这里销毁旧 scene：它可能正被另一个仍然存活的引擎（例如 1200x2608 的壁纸，
            // 或 343x733 的预览）用于渲染，销毁它等于 use-after-free，Rust 侧会直接越界 panic
            // （实测 SIGABRT: index out of bounds）。旧 scene 由它的属主在 release 时自己销毁。
            scene=sceneId;sceneOwner=ownerToken;orphan=false;photo=photoId;decorator=decoratorId;aspect=photoAspect;
            auxiliary=auxiliaryIds.clone();
            sceneW=w;sceneH=h;sceneAod=aod;sceneLock=lock;sceneHome=home;scenePhotoKey=photoKey;
            sceneCosmic=cosmic;sceneVivo=vivoKey==null?"":vivoKey;
            AliveLog.i("AliveClean","shared gl: published scene="+scene+" "+w+"x"+h
                    +" cosmic="+cosmic+" vivo="+(sceneVivo.isEmpty()?"-":sceneVivo.hashCode()));
        }
    }

    /** 只拆 window：上下文与场景保留给下一个实例。 */
    static void releaseWindow(EGLSurface window){
        synchronized(LOCK){
            if(display==EGL14.EGL_NO_DISPLAY)return;
            // 旧实例的 close() 可能晚于新实例的 acquire：此时上下文已被新 window 接管，
            // 再解绑就会把新实例的 current 抢走，导致它下一帧 swap 报 12288。
            if(window==activeWindow){
                EGL14.eglMakeCurrent(display,EGL14.EGL_NO_SURFACE,EGL14.EGL_NO_SURFACE,EGL14.EGL_NO_CONTEXT);
                activeWindow=EGL14.EGL_NO_SURFACE;
            }else{
                AliveLog.i("AliveClean","shared gl: stale window released without unbind");
            }
            if(window!=EGL14.EGL_NO_SURFACE)EGL14.eglDestroySurface(display,window);
            if(leases>0)leases--;
            if(leases==0&&orphan&&scene!=0){
                if(display!=EGL14.EGL_NO_DISPLAY)NativeScene.destroy(scene);
                scene=0;sceneOwner=0;orphan=false;photo=decorator=0;sceneW=sceneH=0;scenePhotoKey="";sceneCosmic=0;sceneVivo="";
            }
        }
    }

    /**
     * 属主归还：只有「自己发布的就是当前共享 scene」且已经没有别的租约时才真正销毁，
     * 否则留给租约方用完再说。
     */
    static void releaseOwned(long ownerToken,long ownScene){
        synchronized(LOCK){
            if(ownScene==0)return;
            boolean mine=ownerToken!=0&&ownerToken==sceneOwner;
            if(!mine&&ownScene!=scene)return;              // 已被别人接管，不归我们销毁
            if(leases>0)return;                            // 还有实例在用它
            if(display!=EGL14.EGL_NO_DISPLAY)NativeScene.destroy(ownScene);
            if(scene==ownScene){scene=0;sceneOwner=0;photo=decorator=0;sceneW=sceneH=0;scenePhotoKey="";sceneCosmic=0;sceneVivo="";}
        }
    }

    /** 全量销毁：进程退出或明确的上下文丢失。 */
    static void destroyAll(){
        synchronized(LOCK){
            if(display==EGL14.EGL_NO_DISPLAY){forget();return;}
            EGL14.eglMakeCurrent(display,EGL14.EGL_NO_SURFACE,EGL14.EGL_NO_SURFACE,EGL14.EGL_NO_CONTEXT);
            if(scene!=0){NativeScene.destroy(scene);scene=0;}
            if(photo!=0||decorator!=0){android.opengl.GLES30.glDeleteTextures(2,new int[]{photo,decorator},0);photo=decorator=0;}
            if(auxiliary.length>0){android.opengl.GLES30.glDeleteTextures(auxiliary.length,auxiliary,0);java.util.Arrays.fill(auxiliary,0);}
            if(context!=EGL14.EGL_NO_CONTEXT){EGL14.eglDestroyContext(display,context);context=EGL14.EGL_NO_CONTEXT;}
            EGL14.eglTerminate(display);EGL14.eglReleaseThread();
            display=EGL14.EGL_NO_DISPLAY;config=null;
        }
    }

    private static void forget(){
        display=EGL14.EGL_NO_DISPLAY;context=EGL14.EGL_NO_CONTEXT;config=null;
        scene=0;photo=decorator=0;sceneW=sceneH=0;scenePhotoKey="";sceneCosmic=0;sceneVivo="";
        java.util.Arrays.fill(auxiliary,0);
    }
}
