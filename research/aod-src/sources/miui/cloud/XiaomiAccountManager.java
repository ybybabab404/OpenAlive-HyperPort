package miui.cloud;

import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.xiaomi.micloudsdk.remote.RemoteMethodInvoker;
import micloud.compat.independent.request.BindAccountServiceCompat;
import micloud.compat.v18.utils.XiaomiAccountServiceProxyCompat;

/* JADX INFO: loaded from: classes2.dex */
public class XiaomiAccountManager {

    /* JADX INFO: renamed from: miui.cloud.XiaomiAccountManager$1, reason: invalid class name */
    class AnonymousClass1 extends RemoteMethodInvoker<String> {
        public final /* synthetic */ String val$type;

        @Override // com.xiaomi.micloudsdk.remote.RemoteMethodInvoker
        public boolean bindService(Context context, ServiceConnection serviceConnection) {
            return BindAccountServiceCompat.bindAccountService(context, serviceConnection);
        }

        @Override // com.xiaomi.micloudsdk.remote.RemoteMethodInvoker
        public String invokeRemoteMethod(IBinder iBinder) {
            return XiaomiAccountServiceProxyCompat.getSnsAccessToken(iBinder, this.val$type);
        }
    }

    /* JADX INFO: renamed from: miui.cloud.XiaomiAccountManager$2, reason: invalid class name */
    class AnonymousClass2 extends RemoteMethodInvoker<Boolean> {
        public final /* synthetic */ String val$accessToken;
        public final /* synthetic */ String val$type;

        @Override // com.xiaomi.micloudsdk.remote.RemoteMethodInvoker
        public boolean bindService(Context context, ServiceConnection serviceConnection) {
            return BindAccountServiceCompat.bindAccountService(context, serviceConnection);
        }

        @Override // com.xiaomi.micloudsdk.remote.RemoteMethodInvoker
        public Boolean invokeRemoteMethod(IBinder iBinder) {
            return Boolean.valueOf(XiaomiAccountServiceProxyCompat.invalidateSnsAccessToken(iBinder, this.val$type, this.val$accessToken));
        }
    }
}
