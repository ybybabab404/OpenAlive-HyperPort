package com.xiaomi.aicr;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes2.dex */
public interface IDownloadCallback extends IInterface {

    public static class Default implements IDownloadCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDownloadCallback {

        public static class Proxy implements IDownloadCallback {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }
        }

        public static IDownloadCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.aicr.IDownloadCallback");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IDownloadCallback)) ? new Proxy(iBinder) : (IDownloadCallback) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.xiaomi.aicr.IDownloadCallback");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.xiaomi.aicr.IDownloadCallback");
                return true;
            }
            if (i == 1) {
                onProgressUpdate(parcel.readInt());
                parcel2.writeNoException();
            } else if (i == 2) {
                onDownloadComplete(parcel.readString());
                parcel2.writeNoException();
            } else if (i == 3) {
                onError(parcel.readInt());
                parcel2.writeNoException();
            } else {
                if (i != 4) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                onDownloadStateChanged(parcel.readInt());
                parcel2.writeNoException();
            }
            return true;
        }
    }

    void onDownloadComplete(String str);

    void onDownloadStateChanged(int i);

    void onError(int i);

    void onProgressUpdate(int i);
}
