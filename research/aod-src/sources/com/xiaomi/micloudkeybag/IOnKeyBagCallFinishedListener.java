package com.xiaomi.micloudkeybag;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes2.dex */
public interface IOnKeyBagCallFinishedListener extends IInterface {

    public static abstract class Stub extends Binder implements IOnKeyBagCallFinishedListener {

        public static class Proxy implements IOnKeyBagCallFinishedListener {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }
        }

        public static IOnKeyBagCallFinishedListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.micloudkeybag.IOnKeyBagCallFinishedListener");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IOnKeyBagCallFinishedListener)) ? new Proxy(iBinder) : (IOnKeyBagCallFinishedListener) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i == 1) {
                parcel.enforceInterface("com.xiaomi.micloudkeybag.IOnKeyBagCallFinishedListener");
                onIsSupportedFinished(parcel.readInt(), parcel.readInt() != 0);
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface("com.xiaomi.micloudkeybag.IOnKeyBagCallFinishedListener");
                onEncryptFinished(parcel.readInt(), parcel.createByteArray());
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface("com.xiaomi.micloudkeybag.IOnKeyBagCallFinishedListener");
                onDecryptFinished(parcel.readInt(), parcel.createByteArray());
                return true;
            }
            if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel2.writeString("com.xiaomi.micloudkeybag.IOnKeyBagCallFinishedListener");
            return true;
        }
    }

    void onDecryptFinished(int i, byte[] bArr);

    void onEncryptFinished(int i, byte[] bArr);

    void onIsSupportedFinished(int i, boolean z);
}
