package android.service.wallpaper;

import android.graphics.Rect;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.MotionEvent;

/* JADX INFO: loaded from: classes.dex */
public interface IWallpaperEngine extends IInterface {

    public static class Default implements IWallpaperEngine {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void destroy() {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void dispatchWallpaperCommand(String str, int i, int i2, int i3, Bundle bundle) {
        }

        @Override // android.service.wallpaper.IWallpaperEngine
        public void setVisibility(boolean z) {
        }
    }

    public static abstract class Stub extends Binder implements IWallpaperEngine {

        public static class Proxy implements IWallpaperEngine {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void destroy() {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.service.wallpaper.IWallpaperEngine");
                    this.mRemote.transact(8, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void dispatchWallpaperCommand(String str, int i, int i2, int i3, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.service.wallpaper.IWallpaperEngine");
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(6, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.service.wallpaper.IWallpaperEngine
            public void setVisibility(boolean z) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.service.wallpaper.IWallpaperEngine");
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(3, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public static IWallpaperEngine asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.service.wallpaper.IWallpaperEngine");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IWallpaperEngine)) ? new Proxy(iBinder) : (IWallpaperEngine) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("android.service.wallpaper.IWallpaperEngine");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.service.wallpaper.IWallpaperEngine");
                return true;
            }
            switch (i) {
                case 1:
                    setDesiredSize(parcel.readInt(), parcel.readInt());
                    return true;
                case 2:
                    setDisplayPadding((Rect) _Parcel.readTypedObject(parcel, Rect.CREATOR));
                    return true;
                case 3:
                    setVisibility(parcel.readInt() != 0);
                    return true;
                case 4:
                    setInAmbientMode(parcel.readInt() != 0, parcel.readLong());
                    return true;
                case 5:
                    dispatchPointer((MotionEvent) _Parcel.readTypedObject(parcel, MotionEvent.CREATOR));
                    return true;
                case 6:
                    dispatchWallpaperCommand(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                    return true;
                case 7:
                    requestWallpaperColors();
                    return true;
                case 8:
                    destroy();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
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

    void destroy();

    void dispatchPointer(MotionEvent motionEvent);

    void dispatchWallpaperCommand(String str, int i, int i2, int i3, Bundle bundle);

    void requestWallpaperColors();

    void setDesiredSize(int i, int i2);

    void setDisplayPadding(Rect rect);

    void setInAmbientMode(boolean z, long j);

    void setVisibility(boolean z);
}
