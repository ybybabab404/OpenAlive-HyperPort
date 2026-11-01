package com.xiaomi.micloudkeybag;

import android.accounts.Account;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes2.dex */
public interface IMiCloudKeyBagService extends IInterface {

    public static abstract class Stub extends Binder implements IMiCloudKeyBagService {

        public static class Proxy implements IMiCloudKeyBagService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }
        }

        public static IMiCloudKeyBagService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.micloudkeybag.IMiCloudKeyBagService");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IMiCloudKeyBagService)) ? new Proxy(iBinder) : (IMiCloudKeyBagService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i == 1598968902) {
                parcel2.writeString("com.xiaomi.micloudkeybag.IMiCloudKeyBagService");
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface("com.xiaomi.micloudkeybag.IMiCloudKeyBagService");
                    int serviceVersion = getServiceVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(serviceVersion);
                    return true;
                case 2:
                    parcel.enforceInterface("com.xiaomi.micloudkeybag.IMiCloudKeyBagService");
                    boolean zIsLastSupported = isLastSupported(parcel.readInt() != 0 ? (Account) Account.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsLastSupported ? 1 : 0);
                    return true;
                case 3:
                    parcel.enforceInterface("com.xiaomi.micloudkeybag.IMiCloudKeyBagService");
                    isSupported(parcel.readInt() != 0 ? (Account) Account.CREATOR.createFromParcel(parcel) : null, IOnKeyBagCallFinishedListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface("com.xiaomi.micloudkeybag.IMiCloudKeyBagService");
                    KeyBagKeyInfo installedKeyInfo = getInstalledKeyInfo(parcel.readInt() != 0 ? (Account) Account.CREATOR.createFromParcel(parcel) : null);
                    parcel2.writeNoException();
                    if (installedKeyInfo != null) {
                        parcel2.writeInt(1);
                        installedKeyInfo.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                case 5:
                    parcel.enforceInterface("com.xiaomi.micloudkeybag.IMiCloudKeyBagService");
                    encrypt(parcel.readInt() != 0 ? (Account) Account.CREATOR.createFromParcel(parcel) : null, parcel.createByteArray(), parcel.readLong(), IOnKeyBagCallFinishedListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface("com.xiaomi.micloudkeybag.IMiCloudKeyBagService");
                    decrypt(parcel.readInt() != 0 ? (Account) Account.CREATOR.createFromParcel(parcel) : null, parcel.createByteArray(), parcel.readLong(), IOnKeyBagCallFinishedListener.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 7:
                    parcel.enforceInterface("com.xiaomi.micloudkeybag.IMiCloudKeyBagService");
                    showDownloadNotification();
                    parcel2.writeNoException();
                    return true;
                case 8:
                    parcel.enforceInterface("com.xiaomi.micloudkeybag.IMiCloudKeyBagService");
                    showUpdateNotification();
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void decrypt(Account account, byte[] bArr, long j, IOnKeyBagCallFinishedListener iOnKeyBagCallFinishedListener);

    void encrypt(Account account, byte[] bArr, long j, IOnKeyBagCallFinishedListener iOnKeyBagCallFinishedListener);

    KeyBagKeyInfo getInstalledKeyInfo(Account account);

    int getServiceVersion();

    boolean isLastSupported(Account account);

    void isSupported(Account account, IOnKeyBagCallFinishedListener iOnKeyBagCallFinishedListener);

    void showDownloadNotification();

    void showUpdateNotification();
}
