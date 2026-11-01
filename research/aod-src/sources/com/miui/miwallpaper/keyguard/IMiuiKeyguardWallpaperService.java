package com.miui.miwallpaper.keyguard;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import com.miui.miwallpaper.IMiuiKeyguardWallpaperCallback;

/* JADX INFO: loaded from: classes2.dex */
public interface IMiuiKeyguardWallpaperService extends IInterface {

    public static class Default implements IMiuiKeyguardWallpaperService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IMiuiKeyguardWallpaperService {

        public static class Proxy implements IMiuiKeyguardWallpaperService {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.miui.miwallpaper.keyguard.IMiuiKeyguardWallpaperService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.miui.miwallpaper.keyguard.IMiuiKeyguardWallpaperService");
                return true;
            }
            if (i != 1) {
                if (i == 2) {
                    showWallpaperScreenOnAnim(parcel.readInt() != 0);
                } else if (i == 3) {
                    updateKeyguardWallpaperState(parcel.readInt() != 0);
                } else if (i == 4) {
                    updateKeyguardWallpaperRatio(parcel.readFloat(), parcel.readLong());
                } else {
                    if (i != 5) {
                        return super.onTransact(i, parcel, parcel2, i2);
                    }
                    showWallpaperUnlockAnim();
                }
            } else {
                bindSystemUIProxy(IMiuiKeyguardWallpaperCallback.Stub.asInterface(parcel.readStrongBinder()));
            }
            return true;
        }
    }

    void bindSystemUIProxy(IMiuiKeyguardWallpaperCallback iMiuiKeyguardWallpaperCallback);

    void showWallpaperScreenOnAnim(boolean z);

    void showWallpaperUnlockAnim();

    void updateKeyguardWallpaperRatio(float f, long j);

    void updateKeyguardWallpaperState(boolean z);
}
