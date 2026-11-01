package cn.kuaipan.android.utils;

import android.os.Handler;
import android.os.Message;

/* JADX INFO: loaded from: classes.dex */
public class SyncAccessor extends Handler {

    public static class Args {
        public static Object mPoolSync = new Object();
        public RuntimeException err;
        public volatile boolean handled;
        public Object[] params;
        public Object result;
    }

    @Override // android.os.Handler
    public void dispatchMessage(Message message) {
        Object obj = message.obj;
        if (obj == null || !(obj instanceof Args)) {
            super.dispatchMessage(message);
            return;
        }
        Args args = (Args) obj;
        try {
            try {
                args.result = handleAccess(message.what, args.params);
                args.handled = true;
                synchronized (args) {
                    args.notifyAll();
                }
            } catch (RuntimeException e) {
                args.err = e;
                args.handled = true;
                synchronized (args) {
                    args.notifyAll();
                }
            }
        } catch (Throwable th) {
            args.handled = true;
            synchronized (args) {
                args.notifyAll();
                throw th;
            }
        }
    }

    public Object handleAccess(int i, Object... objArr) {
        return null;
    }
}
