package com.miui.miwallpaper;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.RemoteViews;

/* JADX INFO: loaded from: classes2.dex */
public interface IMiuiKeyguardWallpaperCallback extends IInterface {

    public static class Default implements IMiuiKeyguardWallpaperCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IMiuiKeyguardWallpaperCallback {

        public static class Proxy implements IMiuiKeyguardWallpaperCallback {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }
        }

        public static IMiuiKeyguardWallpaperCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.miui.miwallpaper.IMiuiKeyguardWallpaperCallback");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IMiuiKeyguardWallpaperCallback)) ? new Proxy(iBinder) : (IMiuiKeyguardWallpaperCallback) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.miui.miwallpaper.IMiuiKeyguardWallpaperCallback");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.miui.miwallpaper.IMiuiKeyguardWallpaperCallback");
                return true;
            }
            if (i != 1) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            Parcelable.Creator creator = RemoteViews.CREATOR;
            onRemoteViewChange((RemoteViews) _Parcel.readTypedObject(parcel, creator), (RemoteViews) _Parcel.readTypedObject(parcel, creator));
            parcel2.writeNoException();
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
    }

    void onRemoteViewChange(RemoteViews remoteViews, RemoteViews remoteViews2);
}
