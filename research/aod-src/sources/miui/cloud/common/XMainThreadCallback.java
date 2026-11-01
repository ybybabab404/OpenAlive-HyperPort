package miui.cloud.common;

import android.os.Handler;
import android.os.Message;
import java.lang.reflect.Method;

/* JADX INFO: loaded from: classes2.dex */
public class XMainThreadCallback<T> extends XDirectCallback<T> {
    public Handler mHandler;

    /* JADX INFO: renamed from: miui.cloud.common.XMainThreadCallback$1, reason: invalid class name */
    public class AnonymousClass1 extends Handler {
        public final /* synthetic */ XMainThreadCallback this$0;

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            CallRequest callRequest = (CallRequest) message.obj;
            this.this$0.handleCallbackInMainThread(callRequest.method, callRequest.args);
        }
    }

    public static class CallRequest {
        public Object[] args;
        public Method method;

        public CallRequest(Method method, Object[] objArr) {
            this.method = method;
            this.args = objArr;
        }
    }

    @Override // miui.cloud.common.XDirectCallback, miui.cloud.common.XCallback
    public Object handleCallback(Method method, Object[] objArr) {
        Message messageObtainMessage = this.mHandler.obtainMessage();
        messageObtainMessage.obj = new CallRequest(method, objArr);
        messageObtainMessage.sendToTarget();
        return null;
    }

    public void handleCallbackInMainThread(Method method, Object[] objArr) {
        try {
            super.handleCallback(method, objArr);
        } catch (Throwable unused) {
            XLogger.loge("Exception in callback, but unable to propagate to the original thread. ");
        }
    }
}
