package micloud.compat.independent.request;

import android.accounts.Account;
import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;

/* JADX INFO: loaded from: classes2.dex */
interface IRequestEnvBuilderCompat {

    public interface RequestEnv {
        long getAutoRetryInterval();

        String getEncryptedUserId(Context context, IBinder iBinder, Account account) throws RemoteException;

        int getMaxRetryCount();

        Account getSystemAccount(Context context);

        String getUserAgent();

        void invalidateAuthToken(Context context);

        String queryAuthToken(Context context);

        boolean shouldUpdateHost();
    }

    RequestEnv build();
}
