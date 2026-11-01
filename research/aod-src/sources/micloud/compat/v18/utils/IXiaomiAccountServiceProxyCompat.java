package micloud.compat.v18.utils;

import android.accounts.Account;
import android.os.IBinder;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes2.dex */
public interface IXiaomiAccountServiceProxyCompat {
    String getEncryptedUserId(IBinder iBinder, Account account) throws RemoteException;

    String getSnsAccessToken(IBinder iBinder, String str) throws RemoteException;

    boolean invalidateSnsAccessToken(IBinder iBinder, String str, String str2) throws RemoteException;
}
