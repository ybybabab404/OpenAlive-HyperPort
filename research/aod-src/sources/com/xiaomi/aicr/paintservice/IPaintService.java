package com.xiaomi.aicr.paintservice;

import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes2.dex */
public interface IPaintService extends IInterface {

    public static class Default implements IPaintService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IPaintService {

        public static class Proxy implements IPaintService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }
        }

        public static IPaintService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.aicr.paintservice.IPaintService");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IPaintService)) ? new Proxy(iBinder) : (IPaintService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.xiaomi.aicr.paintservice.IPaintService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.xiaomi.aicr.paintservice.IPaintService");
                return true;
            }
            switch (i) {
                case 1:
                    int iPaintService_check = paintService_check();
                    parcel2.writeNoException();
                    parcel2.writeInt(iPaintService_check);
                    return true;
                case 2:
                    int iPaintService_init = paintService_init();
                    parcel2.writeNoException();
                    parcel2.writeInt(iPaintService_init);
                    return true;
                case 3:
                    int iPaintService_paintWithCallback = paintService_paintWithCallback(parcel.readInt(), parcel.readInt(), parcel.readString(), IPaintStatusCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    parcel2.writeInt(iPaintService_paintWithCallback);
                    return true;
                case 4:
                    int iPaintService_getProcess = paintService_getProcess(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(iPaintService_getProcess);
                    return true;
                case 5:
                    Bitmap bitmapPaintService_getBitmap = paintService_getBitmap(parcel.readInt());
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, bitmapPaintService_getBitmap, 1);
                    return true;
                case 6:
                    int iPaintService_release = paintService_release();
                    parcel2.writeNoException();
                    parcel2.writeInt(iPaintService_release);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    public static class _Parcel {
        public static void writeTypedObject(Parcel parcel, Parcelable parcelable, int i) {
            if (parcelable == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                parcelable.writeToParcel(parcel, i);
            }
        }
    }

    int paintService_check();

    Bitmap paintService_getBitmap(int i);

    int paintService_getProcess(int i);

    int paintService_init();

    int paintService_paintWithCallback(int i, int i2, String str, IPaintStatusCallback iPaintStatusCallback);

    int paintService_release();
}
