package micloud.compat.v18.utils;

import android.accounts.Account;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import com.xiaomi.micloudsdk.utils.IXiaomiAccountServiceProxy;

/* JADX INFO: loaded from: classes2.dex */
class XiaomiAccountServiceProxyCompat_Base implements IXiaomiAccountServiceProxyCompat {
    @Override // micloud.compat.v18.utils.IXiaomiAccountServiceProxyCompat
    public String getEncryptedUserId(IBinder iBinder, Account account) throws RemoteException {
        return IXiaomiAccountServiceProxy.getEncryptedUserId(iBinder, account);
    }

    @Override // micloud.compat.v18.utils.IXiaomiAccountServiceProxyCompat
    public String getSnsAccessToken(IBinder iBinder, String str) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.xiaomi.accountsdk.account.IXiaomiAccountService");
            parcelObtain.writeString(str);
            iBinder.transact(10, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readString();
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }

    @Override // micloud.compat.v18.utils.IXiaomiAccountServiceProxyCompat
    public boolean invalidateSnsAccessToken(IBinder iBinder, String str, String str2) throws RemoteException {
        Parcel parcelObtain = Parcel.obtain();
        Parcel parcelObtain2 = Parcel.obtain();
        try {
            parcelObtain.writeInterfaceToken("com.xiaomi.accountsdk.account.IXiaomiAccountService");
            parcelObtain.writeString(str);
            parcelObtain.writeString(str2);
            iBinder.transact(11, parcelObtain, parcelObtain2, 0);
            parcelObtain2.readException();
            return parcelObtain2.readInt() != 0;
        } finally {
            parcelObtain2.recycle();
            parcelObtain.recycle();
        }
    }
}
