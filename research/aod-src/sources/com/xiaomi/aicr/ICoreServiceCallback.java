package com.xiaomi.aicr;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes2.dex */
public interface ICoreServiceCallback extends IInterface {

    public static class Default implements ICoreServiceCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements ICoreServiceCallback {

        public static class Proxy implements ICoreServiceCallback {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }
        }

        public Stub() {
            attachInterface(this, "com.xiaomi.aicr.ICoreServiceCallback");
        }

        public static ICoreServiceCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.aicr.ICoreServiceCallback");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ICoreServiceCallback)) ? new Proxy(iBinder) : (ICoreServiceCallback) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.xiaomi.aicr.ICoreServiceCallback");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.xiaomi.aicr.ICoreServiceCallback");
                return true;
            }
            if (i == 1) {
                onDownload(parcel.readStrongBinder());
            } else {
                if (i != 2) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                speed(parcel.readString(), parcel.readLong());
            }
            return true;
        }
    }

    void onDownload(IBinder iBinder);

    void speed(String str, long j);
}
