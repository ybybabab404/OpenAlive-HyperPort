package com.xiaomi.aicr;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes2.dex */
public interface IAiCrCoreService extends IInterface {

    public static class Default implements IAiCrCoreService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.aicr.IAiCrCoreService
        public IBinder getPluginBinder(ICoreServiceCallback iCoreServiceCallback, String str, String str2, String str3, Bundle bundle) {
            return null;
        }

        @Override // com.xiaomi.aicr.IAiCrCoreService
        public boolean isSupport(String str) {
            return false;
        }

        @Override // com.xiaomi.aicr.IAiCrCoreService
        public void releaseConnect(String str) {
        }
    }

    public static abstract class Stub extends Binder implements IAiCrCoreService {

        public static class Proxy implements IAiCrCoreService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.xiaomi.aicr.IAiCrCoreService
            public IBinder getPluginBinder(ICoreServiceCallback iCoreServiceCallback, String str, String str2, String str3, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.aicr.IAiCrCoreService");
                    parcelObtain.writeStrongInterface(iCoreServiceCallback);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    IBinder strongBinder = parcelObtain2.readStrongBinder();
                    if (parcelObtain2.readInt() != 0) {
                        bundle.readFromParcel(parcelObtain2);
                    }
                    return strongBinder;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.IAiCrCoreService
            public boolean isSupport(String str) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.aicr.IAiCrCoreService");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.aicr.IAiCrCoreService
            public void releaseConnect(String str) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.xiaomi.aicr.IAiCrCoreService");
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static IAiCrCoreService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.aicr.IAiCrCoreService");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IAiCrCoreService)) ? new Proxy(iBinder) : (IAiCrCoreService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.xiaomi.aicr.IAiCrCoreService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.xiaomi.aicr.IAiCrCoreService");
                return true;
            }
            if (i == 1) {
                ICoreServiceCallback iCoreServiceCallbackAsInterface = ICoreServiceCallback.Stub.asInterface(parcel.readStrongBinder());
                String string = parcel.readString();
                String string2 = parcel.readString();
                String string3 = parcel.readString();
                Bundle bundle = new Bundle();
                IBinder pluginBinder = getPluginBinder(iCoreServiceCallbackAsInterface, string, string2, string3, bundle);
                parcel2.writeNoException();
                parcel2.writeStrongBinder(pluginBinder);
                _Parcel.writeTypedObject(parcel2, bundle, 1);
            } else if (i == 2) {
                releaseConnect(parcel.readString());
                parcel2.writeNoException();
            } else {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                boolean zIsSupport = isSupport(parcel.readString());
                parcel2.writeNoException();
                parcel2.writeInt(zIsSupport ? 1 : 0);
            }
            return true;
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

    IBinder getPluginBinder(ICoreServiceCallback iCoreServiceCallback, String str, String str2, String str3, Bundle bundle);

    boolean isSupport(String str);

    void releaseConnect(String str);
}
