package com.xiaomi.aicr.paintservice;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes2.dex */
public interface IPaintStatusCallback extends IInterface {

    public static class Default implements IPaintStatusCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPaintStatusCallback {

        public static class Proxy implements IPaintStatusCallback {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }
        }

        public static IPaintStatusCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.aicr.paintservice.IPaintStatusCallback");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IPaintStatusCallback)) ? new Proxy(iBinder) : (IPaintStatusCallback) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.xiaomi.aicr.paintservice.IPaintStatusCallback");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.xiaomi.aicr.paintservice.IPaintStatusCallback");
                return true;
            }
            if (i == 1) {
                onPaintFinished();
                parcel2.writeNoException();
            } else {
                if (i != 2) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                onPaintFailed(parcel.readByte());
                parcel2.writeNoException();
            }
            return true;
        }
    }

    void onPaintFailed(byte b);

    void onPaintFinished();
}
