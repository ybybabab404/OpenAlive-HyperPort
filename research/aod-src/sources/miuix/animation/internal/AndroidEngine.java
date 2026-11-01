package miuix.animation.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.HashMap;
import miuix.animation.Folme;
import miuix.animation.physics.AnimationHandler;
import miuix.animation.utils.BoostHelper;
import miuix.animation.utils.CommonUtils;

/* JADX INFO: loaded from: classes2.dex */
public class AndroidEngine extends FolmeEngine implements AnimationHandler.AnimationFrameCallback {
    private static final int MSG_END = 1;
    private static final int MSG_START = 0;
    static volatile EngineHandler sMainHandler;
    static volatile AndroidEngine sMainInstance;
    private Handler mHandler;
    static final ThreadLocal<Boolean> sWtfLogged = new ThreadLocal<>();
    static final ThreadLocal<AndroidEngine> sThreadInstance = new ThreadLocal<AndroidEngine>() { // from class: miuix.animation.internal.AndroidEngine.1
        @Override // java.lang.ThreadLocal
        @Nullable
        public AndroidEngine initialValue() {
            Looper looperMyLooper = Looper.myLooper();
            if (looperMyLooper == null) {
                return null;
            }
            if (looperMyLooper == Folme.getLooper() || Folme.getUiLooperByTid(looperMyLooper.getThread().getId()) != null) {
                return new AndroidEngine();
            }
            return null;
        }
    };

    public static class EngineHandler extends Handler {
        public EngineHandler(@NonNull Looper looper) {
            super(looper);
        }

        public EngineHandler(@NonNull Looper looper, @Nullable Handler.Callback callback) {
            super(looper, callback);
        }

        @Override // android.os.Handler
        public void handleMessage(@NonNull Message message) {
            AndroidEngine inst = AndroidEngine.getInst();
            if (inst == null) {
                return;
            }
            int i = message.what;
            if (i == 0) {
                inst.startAnim();
            } else {
                if (i != 1) {
                    return;
                }
                inst.endAnim();
            }
        }
    }

    public AndroidEngine() {
        Looper looperMyLooper = Looper.myLooper();
        if (looperMyLooper == null) {
            return;
        }
        EngineHandler engineHandler = new EngineHandler(looperMyLooper);
        setHandler(engineHandler);
        if (looperMyLooper == Looper.getMainLooper()) {
            sMainInstance = this;
            sMainHandler = engineHandler;
        }
    }

    public static AndroidEngine getInst() {
        Looper looperMyLooper;
        ThreadLocal<AndroidEngine> threadLocal = sThreadInstance;
        AndroidEngine androidEngine = threadLocal.get();
        if (androidEngine == null) {
            androidEngine = sMainInstance;
        }
        if (androidEngine == null && (looperMyLooper = Looper.myLooper()) != null) {
            if (Folme.getUiLooperByTid(looperMyLooper.getThread().getId()) == null) {
                Folme.registerUiLooper(looperMyLooper);
            }
            androidEngine = new AndroidEngine();
            threadLocal.set(androidEngine);
        }
        if (androidEngine == null) {
            ThreadLocal<Boolean> threadLocal2 = sWtfLogged;
            if (threadLocal2.get() == null) {
                threadLocal2.set(Boolean.TRUE);
                Log.wtf(CommonUtils.TAG, "AndroidEngine.getInst returns null! looper: " + Looper.myLooper() + " thread: " + Thread.currentThread());
            }
        }
        return androidEngine;
    }

    public static void turboThreadIfNeed(int i) {
        AndroidEngine inst = getInst();
        if (inst == null) {
            return;
        }
        HashMap<Integer, Boolean> map = inst.mScheduler.mAnimTaskSchedMap;
        if (map.containsKey(Integer.valueOf(i)) || BoostHelper.getInstance().isTurboSchedDisabled || Folme.appContext == null) {
            return;
        }
        try {
            BoostHelper.getInstance().setTurboSchedActionWithPriority(new int[]{i}, 1000L, Folme.appContext);
            map.put(Integer.valueOf(i), Boolean.TRUE);
        } catch (Exception unused) {
        }
    }

    @Override // miuix.animation.internal.FolmeEngine
    public void end() {
        Handler handler = this.mHandler;
        if (handler != null && handler.getLooper() == Looper.myLooper()) {
            endAnim();
            return;
        }
        if (handler == null) {
            handler = sMainHandler;
        }
        if (handler != null) {
            handler.sendEmptyMessage(1);
            return;
        }
        Log.w(CommonUtils.TAG, "AndroidEngine.end handler is null! looper: " + Looper.myLooper());
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    @Override // miuix.animation.internal.FolmeEngine
    public void scheduleNextFrame(long j) {
        AnimationHandler.getInstance().addAnimationFrameCallback(this, j);
    }

    public void setHandler(Handler handler) {
        this.mHandler = handler;
        this.mScheduler.handler = handler;
    }

    @Override // miuix.animation.internal.FolmeEngine
    public void start() {
        Handler handler = this.mHandler;
        if (handler != null && handler.getLooper() == Looper.myLooper()) {
            startAnim();
            return;
        }
        if (handler == null) {
            handler = sMainHandler;
        }
        if (handler != null) {
            handler.sendEmptyMessage(0);
            return;
        }
        Log.w(CommonUtils.TAG, "AndroidEngine.start handler is null! looper: " + Looper.myLooper());
    }

    @Override // miuix.animation.internal.FolmeEngine
    public void stopNextFrame() {
        AnimationHandler.getInstance().removeCallback(this);
    }
}
