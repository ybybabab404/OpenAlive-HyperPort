package com.miui.miwallpaper;

import android.app.WallpaperColors;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public interface IMiuiWallpaperManagerCallback extends IInterface {

    public static class Default implements IMiuiWallpaperManagerCallback {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
        public void onPartColorComputeComplete(Map map, Map map2, int i) {
        }
    }

    public static abstract class Stub extends Binder implements IMiuiWallpaperManagerCallback {

        public static class Proxy implements IMiuiWallpaperManagerCallback {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerCallback
            public void onPartColorComputeComplete(Map map, Map map2, int i) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.miwallpaper.IMiuiWallpaperManagerCallback");
                    parcelObtain.writeMap(map);
                    parcelObtain.writeMap(map2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "com.miui.miwallpaper.IMiuiWallpaperManagerCallback");
        }

        public static IMiuiWallpaperManagerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.miui.miwallpaper.IMiuiWallpaperManagerCallback");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IMiuiWallpaperManagerCallback)) ? new Proxy(iBinder) : (IMiuiWallpaperManagerCallback) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.miui.miwallpaper.IMiuiWallpaperManagerCallback");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.miui.miwallpaper.IMiuiWallpaperManagerCallback");
                return true;
            }
            if (i == 1) {
                onWallpaperChanged((WallpaperColors) _Parcel.readTypedObject(parcel, WallpaperColors.CREATOR), parcel.readString(), parcel.readInt());
            } else if (i == 2) {
                ClassLoader classLoader = getClass().getClassLoader();
                onPartColorComputeComplete(parcel.readHashMap(classLoader), parcel.readHashMap(classLoader), parcel.readInt());
            } else if (i == 3) {
                onWallpaperFirstFrameRendered(parcel.readInt());
            } else {
                if (i != 4) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                onDrawFrameEnd();
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

    void onDrawFrameEnd();

    void onPartColorComputeComplete(Map map, Map map2, int i);

    void onWallpaperChanged(WallpaperColors wallpaperColors, String str, int i);

    void onWallpaperFirstFrameRendered(int i);
}
