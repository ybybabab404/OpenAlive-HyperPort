package android.service.wallpaper;

import android.app.WallpaperColors;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public interface IWallpaperConnection extends IInterface {

    public static class Default implements IWallpaperConnection {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IWallpaperConnection {

        public static class Proxy implements IWallpaperConnection {
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
            attachInterface(this, "android.service.wallpaper.IWallpaperConnection");
        }

        public static IWallpaperConnection asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.service.wallpaper.IWallpaperConnection");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IWallpaperConnection)) ? new Proxy(iBinder) : (IWallpaperConnection) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.service.wallpaper.IWallpaperConnection");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.service.wallpaper.IWallpaperConnection");
                return true;
            }
            if (i == 1) {
                attachEngine(IWallpaperEngine.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt());
                parcel2.writeNoException();
            } else if (i == 2) {
                engineShown(IWallpaperEngine.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
            } else if (i == 3) {
                ParcelFileDescriptor wallpaper = setWallpaper(parcel.readString());
                parcel2.writeNoException();
                _Parcel.writeTypedObject(parcel2, wallpaper, 1);
            } else {
                if (i != 4) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                onWallpaperColorsChanged((WallpaperColors) _Parcel.readTypedObject(parcel, WallpaperColors.CREATOR), parcel.readInt());
                parcel2.writeNoException();
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

    void attachEngine(IWallpaperEngine iWallpaperEngine, int i);

    void engineShown(IWallpaperEngine iWallpaperEngine);

    void onWallpaperColorsChanged(WallpaperColors wallpaperColors, int i);

    ParcelFileDescriptor setWallpaper(String str);
}
