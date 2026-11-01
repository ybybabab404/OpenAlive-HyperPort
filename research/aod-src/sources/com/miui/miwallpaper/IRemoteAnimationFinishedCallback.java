package com.miui.miwallpaper;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes2.dex */
public interface IRemoteAnimationFinishedCallback extends IInterface {

    public static class Default implements IRemoteAnimationFinishedCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IRemoteAnimationFinishedCallback {

        public static class Proxy implements IRemoteAnimationFinishedCallback {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }
        }

        public static IRemoteAnimationFinishedCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.miui.miwallpaper.IRemoteAnimationFinishedCallback");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IRemoteAnimationFinishedCallback)) ? new Proxy(iBinder) : (IRemoteAnimationFinishedCallback) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.miui.miwallpaper.IRemoteAnimationFinishedCallback");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.miui.miwallpaper.IRemoteAnimationFinishedCallback");
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            onAnimationFinished();
            return true;
        }
    }

    void onAnimationFinished();
}
