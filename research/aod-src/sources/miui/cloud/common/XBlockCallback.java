package miui.cloud.common;

import java.lang.reflect.Method;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/* JADX INFO: loaded from: classes2.dex */
public class XBlockCallback<T> extends XCallback<T> {
    public CallbackRequest mCallbackRequest;
    public ReentrantLock mCallbackRequestLock;
    public Condition mCallbackRequestReady;
    public Condition mCallbackRequestWait;

    public static class CallbackRequest {
        public Object[] args;
        public Method method;

        public CallbackRequest(Method method, Object[] objArr) {
            this.method = method;
            this.args = objArr;
        }
    }

    @Override // miui.cloud.common.XCallback
    public Object handleCallback(Method method, Object[] objArr) {
        try {
            try {
                this.mCallbackRequestLock.lock();
                while (this.mCallbackRequest != null) {
                    this.mCallbackRequestReady.signalAll();
                    this.mCallbackRequestWait.await();
                }
                this.mCallbackRequest = new CallbackRequest(method, objArr);
                this.mCallbackRequestReady.signalAll();
            } catch (InterruptedException unused) {
                XLogger.loge("Interrupted while waiting for callback handlers. ");
            }
            return null;
        } finally {
            this.mCallbackRequestLock.unlock();
        }
    }
}
