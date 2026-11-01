package androidx.core.provider;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import androidx.annotation.RestrictTo;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
@Deprecated
public class SelfDestructiveThread {
    public final int mDestructAfterMillisec;
    public Handler mHandler;
    public final Object mLock;
    public HandlerThread mThread;

    /* JADX INFO: renamed from: androidx.core.provider.SelfDestructiveThread$1, reason: invalid class name */
    public class AnonymousClass1 implements Handler.Callback {
        public final /* synthetic */ SelfDestructiveThread this$0;

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                this.this$0.onDestruction();
                return true;
            }
            if (i != 1) {
                return true;
            }
            this.this$0.onInvokeRunnable((Runnable) message.obj);
            return true;
        }
    }

    /* JADX INFO: renamed from: androidx.core.provider.SelfDestructiveThread$2, reason: invalid class name */
    class AnonymousClass2 implements Runnable {
        public final /* synthetic */ Callable val$callable;
        public final /* synthetic */ Handler val$calleeHandler;
        public final /* synthetic */ ReplyCallback val$reply;

        @Override // java.lang.Runnable
        public void run() {
            final Object objCall;
            try {
                objCall = this.val$callable.call();
            } catch (Exception unused) {
                objCall = null;
            }
            this.val$calleeHandler.post(new Runnable() { // from class: androidx.core.provider.SelfDestructiveThread.2.1
                @Override // java.lang.Runnable
                public void run() {
                    AnonymousClass2.this.val$reply.onReply(objCall);
                }
            });
        }
    }

    /* JADX INFO: renamed from: androidx.core.provider.SelfDestructiveThread$3, reason: invalid class name */
    class AnonymousClass3 implements Runnable {
        public final /* synthetic */ Callable val$callable;
        public final /* synthetic */ Condition val$cond;
        public final /* synthetic */ AtomicReference val$holder;
        public final /* synthetic */ ReentrantLock val$lock;
        public final /* synthetic */ AtomicBoolean val$running;

        @Override // java.lang.Runnable
        public void run() {
            try {
                this.val$holder.set(this.val$callable.call());
            } catch (Exception unused) {
            }
            this.val$lock.lock();
            try {
                this.val$running.set(false);
                this.val$cond.signal();
            } finally {
                this.val$lock.unlock();
            }
        }
    }

    public interface ReplyCallback<T> {
        void onReply(Object obj);
    }

    public void onDestruction() {
        synchronized (this.mLock) {
            try {
                if (this.mHandler.hasMessages(1)) {
                    return;
                }
                this.mThread.quit();
                this.mThread = null;
                this.mHandler = null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void onInvokeRunnable(Runnable runnable) {
        runnable.run();
        synchronized (this.mLock) {
            this.mHandler.removeMessages(0);
            Handler handler = this.mHandler;
            handler.sendMessageDelayed(handler.obtainMessage(0), this.mDestructAfterMillisec);
        }
    }
}
