package miui.cloud;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.xiaomi.micloudsdk.utils.MiCloudConstants;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/* JADX INFO: loaded from: classes2.dex */
public class CloudRequestUtils {
    public static final String URL_CALL_LOG_BASE;
    public static final String URL_CONTACT_BASE;
    public static final String URL_DEV_BASE;
    public static final String URL_FIND_DEVICE_BASE;
    public static final String URL_GALLERY_BASE;
    public static final String URL_MICARD_BASE;
    public static final String URL_MICLOUD_STATUS_BASE;
    public static final String URL_MMS_BASE;
    public static final String URL_MUSIC_BASE;
    public static final String URL_NOTE_BASE;
    public static final String URL_RICH_MEDIA_BASE;
    public static final String URL_WIFI_BASE;
    public static final String URL_WIFI_SHARE_BASE;

    public static class ConnectivityResumedReceiver extends BroadcastReceiver {
        public final AsyncFuture mFuture;

        public static final class AsyncFuture<V> extends FutureTask<V> {

            /* JADX INFO: renamed from: miui.cloud.CloudRequestUtils$ConnectivityResumedReceiver$AsyncFuture$1, reason: invalid class name */
            class AnonymousClass1 implements Callable<Object> {
                @Override // java.util.concurrent.Callable
                public Object call() {
                    throw new IllegalStateException("this should never be called");
                }
            }

            /* JADX WARN: Multi-variable type inference failed */
            public void setResult(Object obj) {
                set(obj);
            }
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent.getBooleanExtra("noConnectivity", false)) {
                return;
            }
            Log.i("CloudRequestUtils", "connectivity resumed");
            this.mFuture.setResult(Boolean.TRUE);
        }
    }

    static {
        boolean z = MiCloudConstants.USE_PREVIEW;
        URL_RICH_MEDIA_BASE = z ? "http://api.micloud.preview.n.xiaomi.net" : "http://fileapi.micloud.xiaomi.net";
        URL_CONTACT_BASE = z ? "http://micloud.preview.n.xiaomi.net" : "http://contactapi.micloud.xiaomi.net";
        URL_MICARD_BASE = z ? "http://micardapi.micloud.preview.n.xiaomi.net" : "http://micardapi.micloud.xiaomi.net";
        URL_MMS_BASE = z ? "http://micloud.preview.n.xiaomi.net" : "http://smsapi.micloud.xiaomi.net";
        URL_GALLERY_BASE = z ? "http://micloud.preview.n.xiaomi.net" : "http://galleryapi.micloud.xiaomi.net";
        URL_FIND_DEVICE_BASE = z ? "http://micloud.preview.n.xiaomi.net" : "http://findapi.micloud.xiaomi.net";
        URL_WIFI_BASE = z ? "http://micloud.preview.n.xiaomi.net" : "http://wifiapi.micloud.xiaomi.net";
        URL_NOTE_BASE = z ? "http://micloud.preview.n.xiaomi.net" : "http://noteapi.micloud.xiaomi.net";
        URL_MUSIC_BASE = z ? "http://micloud.preview.n.xiaomi.net" : "http://musicapi.micloud.xiaomi.net";
        URL_CALL_LOG_BASE = z ? "http://micloud.preview.n.xiaomi.net" : "http://phonecallapi.micloud.xiaomi.net";
        URL_WIFI_SHARE_BASE = z ? "http://micloud.preview.n.xiaomi.net" : "http://wifisharingapi.micloud.xiaomi.net";
        URL_DEV_BASE = z ? "http://api.device.preview.n.xiaomi.net" : "http://api.device.xiaomi.net";
        URL_MICLOUD_STATUS_BASE = z ? "http://statusapi.micloud.preview.n.xiaomi.net" : "http://statusapi.micloud.xiaomi.net";
    }
}
