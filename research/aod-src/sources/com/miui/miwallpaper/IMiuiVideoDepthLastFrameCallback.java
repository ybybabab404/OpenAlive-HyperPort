package com.miui.miwallpaper;

import android.graphics.Bitmap;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes2.dex */
public interface IMiuiVideoDepthLastFrameCallback extends IInterface {

    public static class Default implements IMiuiVideoDepthLastFrameCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IMiuiVideoDepthLastFrameCallback {

        public static class Proxy implements IMiuiVideoDepthLastFrameCallback {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }
        }

        public static IMiuiVideoDepthLastFrameCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.miui.miwallpaper.IMiuiVideoDepthLastFrameCallback");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IMiuiVideoDepthLastFrameCallback)) ? new Proxy(iBinder) : (IMiuiVideoDepthLastFrameCallback) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.miui.miwallpaper.IMiuiVideoDepthLastFrameCallback");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.miui.miwallpaper.IMiuiVideoDepthLastFrameCallback");
                return true;
            }
            if (i == 1) {
                onGetLastDepthFrameSuccess((Bitmap) _Parcel.readTypedObject(parcel, Bitmap.CREATOR), parcel.readInt());
            } else {
                if (i != 2) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                onGetLastDepthFrameFailed(parcel.readInt(), parcel.readString());
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
    }

    void onGetLastDepthFrameFailed(int i, String str);

    void onGetLastDepthFrameSuccess(Bitmap bitmap, int i);
}
