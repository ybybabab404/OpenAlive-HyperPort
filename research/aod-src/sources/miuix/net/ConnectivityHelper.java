package miuix.net;

import android.content.Context;
import android.net.ConnectivityManager;
import miuix.core.util.SoftReferenceSingleton;

/* JADX INFO: loaded from: classes2.dex */
public class ConnectivityHelper {
    public static final SoftReferenceSingleton INSTANCE = new SoftReferenceSingleton<ConnectivityHelper>() { // from class: miuix.net.ConnectivityHelper.1
        @Override // miuix.core.util.SoftReferenceSingleton
        public ConnectivityHelper createInstance(Object obj) {
            return new ConnectivityHelper((Context) obj);
        }
    };
    public ConnectivityManager mConnectivityManager;

    public ConnectivityHelper(Context context) {
        this.mConnectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
    }
}
