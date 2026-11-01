package com.xiaomi.aicr;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes2.dex */
public interface IDownloadListener extends IInterface {

    public static class Default implements IDownloadListener {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IDownloadListener {

        public static class Proxy implements IDownloadListener {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }
        }

        public static IDownloadListener asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.aicr.IDownloadListener");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IDownloadListener)) ? new Proxy(iBinder) : (IDownloadListener) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.xiaomi.aicr.IDownloadListener");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.xiaomi.aicr.IDownloadListener");
                return true;
            }
            switch (i) {
                case 1:
                    download(parcel.readString(), parcel.readString(), IDownloadCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 2:
                    boolean zDeleteFile = deleteFile(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(zDeleteFile ? 1 : 0);
                    return true;
                case 3:
                    startDownload(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 4:
                    pauseDownload(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 5:
                    resumeDownload(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 6:
                    cancelDownload(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 7:
                    int downloadStatus = getDownloadStatus(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(downloadStatus);
                    return true;
                case 8:
                    removeTask(parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 9:
                    boolean zCheckVersion = checkVersion(parcel.readString(), parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(zCheckVersion ? 1 : 0);
                    return true;
                case 10:
                    String localVersion = getLocalVersion(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeString(localVersion);
                    return true;
                case 11:
                    String cloudVersion = getCloudVersion(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeString(cloudVersion);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    void cancelDownload(String str);

    boolean checkVersion(String str, int i);

    boolean deleteFile(String str);

    void download(String str, String str2, IDownloadCallback iDownloadCallback);

    String getCloudVersion(String str);

    int getDownloadStatus(String str);

    String getLocalVersion(String str);

    void pauseDownload(String str);

    void removeTask(String str);

    void resumeDownload(String str);

    void startDownload(String str);
}
