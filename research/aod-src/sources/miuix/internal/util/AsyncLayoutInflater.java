package miuix.internal.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/* JADX INFO: loaded from: classes2.dex */
public final class AsyncLayoutInflater {
    public final Handler mHandler;
    public final Handler mInflateHandler;

    /* JADX INFO: renamed from: miuix.internal.util.AsyncLayoutInflater$1, reason: invalid class name */
    class AnonymousClass1 extends Handler {
        public InflateRequest head;
        public int index;
        public InflateRequest tail;
        public final /* synthetic */ AsyncLayoutInflater this$0;

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what == 1) {
                Object obj = message.obj;
                if (obj instanceof InflateRequest) {
                    InflateRequest inflateRequest = (InflateRequest) obj;
                    try {
                        inflateRequest.view = inflateRequest.localInflater.inflate(inflateRequest.resid, inflateRequest.parent, false);
                    } catch (RuntimeException e) {
                        Log.w("AsyncLayoutInflater", "Failed to inflate resource in the background! Retrying on the UI thread", e);
                    }
                    if (this.head == null) {
                        this.head = inflateRequest;
                    } else {
                        this.tail.next = inflateRequest;
                    }
                    this.tail = inflateRequest;
                    if (this.index < 10 && !this.this$0.mInflateHandler.getLooper().getQueue().isIdle()) {
                        this.index++;
                        return;
                    }
                    this.this$0.mHandler.sendMessageAtFrontOfQueue(Message.obtain(this.this$0.mHandler, 0, this.head));
                    this.head = null;
                    this.tail = null;
                    this.index = 0;
                }
            }
        }
    }

    /* JADX INFO: renamed from: miuix.internal.util.AsyncLayoutInflater$2, reason: invalid class name */
    public class AnonymousClass2 implements Handler.Callback {
        public final /* synthetic */ AsyncLayoutInflater this$0;

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            int i;
            for (InflateRequest inflateRequest = (InflateRequest) message.obj; inflateRequest != null; inflateRequest = inflateRequest.next) {
                if (inflateRequest.view == null && (i = inflateRequest.resid) != 0) {
                    inflateRequest.view = inflateRequest.localInflater.inflate(i, inflateRequest.parent, false);
                }
                inflateRequest.callback.onInflateFinished(inflateRequest.view, inflateRequest.resid, inflateRequest.parent);
                this.this$0.releaseRequest(inflateRequest);
            }
            return true;
        }
    }

    public static class BasicInflater extends LayoutInflater {
        public static final String[] sClassPrefixList = {"android.widget.", "android.webkit.", "android.app."};

        public BasicInflater(Context context) {
            super(context);
        }

        @Override // android.view.LayoutInflater
        public LayoutInflater cloneInContext(Context context) {
            return new BasicInflater(context);
        }

        @Override // android.view.LayoutInflater
        public View onCreateView(String str, AttributeSet attributeSet) {
            for (String str2 : sClassPrefixList) {
                try {
                    View viewCreateView = createView(str, str2, attributeSet);
                    if (viewCreateView != null) {
                        return viewCreateView;
                    }
                } catch (ClassNotFoundException e) {
                    Log.e("AsyncLayoutInflater", "onCreateView : " + e);
                }
            }
            return super.onCreateView(str, attributeSet);
        }
    }

    public static class InflateRequest {
        public OnInflateFinishedListener callback;
        public LayoutInflater localInflater;
        public InflateRequest next;
        public ViewGroup parent;
        public int resid;
        public View view;
    }

    public interface OnInflateFinishedListener {
        void onInflateFinished(View view, int i, ViewGroup viewGroup);
    }

    public final void releaseRequest(InflateRequest inflateRequest) {
        inflateRequest.callback = null;
        inflateRequest.localInflater = null;
        inflateRequest.parent = null;
        inflateRequest.resid = 0;
        inflateRequest.view = null;
    }
}
