package com.miui.videodownload;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes2.dex */
public interface IVideoDownloadService extends IInterface {

    public static class Default implements IVideoDownloadService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.miui.videodownload.IVideoDownloadService
        public void pauseDownload(Bundle bundle) {
        }

        @Override // com.miui.videodownload.IVideoDownloadService
        public void registerCallback(IVideoDownloadCallback iVideoDownloadCallback) {
        }

        @Override // com.miui.videodownload.IVideoDownloadService
        public void startDownload(Bundle bundle) {
        }

        @Override // com.miui.videodownload.IVideoDownloadService
        public void unregisterCallback() {
        }
    }

    public static abstract class Stub extends Binder implements IVideoDownloadService {

        public static class Proxy implements IVideoDownloadService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.miui.videodownload.IVideoDownloadService
            public void pauseDownload(Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.videodownload.IVideoDownloadService");
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(4, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.videodownload.IVideoDownloadService
            public void registerCallback(IVideoDownloadCallback iVideoDownloadCallback) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.videodownload.IVideoDownloadService");
                    parcelObtain.writeStrongInterface(iVideoDownloadCallback);
                    this.mRemote.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.videodownload.IVideoDownloadService
            public void startDownload(Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.videodownload.IVideoDownloadService");
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.videodownload.IVideoDownloadService
            public void unregisterCallback() {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.videodownload.IVideoDownloadService");
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public static IVideoDownloadService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.miui.videodownload.IVideoDownloadService");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IVideoDownloadService)) ? new Proxy(iBinder) : (IVideoDownloadService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.miui.videodownload.IVideoDownloadService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.miui.videodownload.IVideoDownloadService");
                return true;
            }
            if (i == 1) {
                registerCallback(IVideoDownloadCallback.Stub.asInterface(parcel.readStrongBinder()));
            } else if (i == 2) {
                unregisterCallback();
            } else if (i == 3) {
                startDownload((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
            } else {
                if (i != 4) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                pauseDownload((Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
            }
            return true;
        }
    }

    public static class _Parcel {
        public static Object readTypedObject(Parcel parcel, Parcelable.Creator creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }

        public static void writeTypedObject(Parcel parcel, Parcelable parcelable, int i) {
            if (parcelable == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                parcelable.writeToParcel(parcel, i);
            }
        }
    }

    void pauseDownload(Bundle bundle);

    void registerCallback(IVideoDownloadCallback iVideoDownloadCallback);

    void startDownload(Bundle bundle);

    void unregisterCallback();
}
