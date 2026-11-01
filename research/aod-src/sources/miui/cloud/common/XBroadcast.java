package miui.cloud.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes2.dex */
public class XBroadcast {

    /* JADX INFO: renamed from: miui.cloud.common.XBroadcast$1, reason: invalid class name */
    class AnonymousClass1 extends BroadcastReceiver {
        public final /* synthetic */ CountDownLatch val$cl;
        public final /* synthetic */ XWrapper val$result;

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            XLogger.log("result received. ");
            this.val$result.set(new BroadcastResult(getResultCode(), getResultData(), getResultExtras(true)));
            this.val$cl.countDown();
        }
    }

    public static class BroadcastResult {
        public int code;
        public String data;
        public Bundle extra;

        public BroadcastResult(int i, String str, Bundle bundle) {
            this.code = i;
            this.data = str;
            this.extra = bundle;
        }
    }
}
