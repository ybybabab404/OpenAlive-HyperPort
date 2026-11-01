package com.miui.miwallpaper;

import android.app.WallpaperColors;
import android.content.ComponentName;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.view.Surface;
import android.view.SurfaceControl;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public interface IMiuiWallpaperManagerService extends IInterface {

    public static class Default implements IMiuiWallpaperManagerService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
        public void bindSystemUIProxy(IMiuiKeyguardWallpaperCallback iMiuiKeyguardWallpaperCallback) {
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
        public boolean blurState(int i) {
            return false;
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
        public void changeMiuiWallpaperInfo(int i, Bundle bundle) {
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
        public int getEffectId(int i) {
            return 0;
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
        public String getGalleryJson(int i) {
            return null;
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
        public Map getMiuiLockPartWallpaperIsDeep(int i, boolean z, Map map) {
            return null;
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
        public Map getMiuiWallpaperPalette(int i, boolean z, Map map) {
            return null;
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
        public String getMiuiWallpaperPath(String str, int i, boolean z, boolean z2) {
            return null;
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
        public ParcelFileDescriptor getMiuiWallpaperPreview(int i) {
            return null;
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
        public float getMiuiWallpaperSdkVersion() {
            return 0.0f;
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
        public int getMiuiWallpaperSdkVersionCode() {
            return 0;
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
        public String getMiuiWallpaperType(int i) {
            return null;
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
        public List getVideoDepthScreenshot() {
            return null;
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
        public Bundle getWallpaperInfo(int i, String str, Bundle bundle) {
            return null;
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
        public boolean isMiuiDefaultWallpaper(int i) {
            return false;
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
        public boolean isMiuiWallpaperComponentUsing(int i) {
            return false;
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
        public void registerWallpaperChangeListener(IMiuiWallpaperManagerCallback iMiuiWallpaperManagerCallback, int i, String str) {
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
        public void setLockscreenVideoSurface(Surface surface, Surface surface2) {
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
        public void setLockscreenVideoSurfaceVisible(boolean z) {
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
        public Map setWallpaper(int i, String str, String str2, ComponentName componentName, boolean z, List list) {
            return null;
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
        public Map setWallpaper2(int i, String str, String str2, ComponentName componentName, boolean z, boolean z2, List list) {
            return null;
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
        public Map setWallpaper3(int i, String str, ComponentName componentName, List list, Bundle bundle) {
            return null;
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
        public boolean supportDark(int i) {
            return false;
        }

        @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
        public void unRegisterWallpaperChangeListener(IMiuiWallpaperManagerCallback iMiuiWallpaperManagerCallback) {
        }
    }

    public static abstract class Stub extends Binder implements IMiuiWallpaperManagerService {

        public static class Proxy implements IMiuiWallpaperManagerService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
            public void bindSystemUIProxy(IMiuiKeyguardWallpaperCallback iMiuiKeyguardWallpaperCallback) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.miwallpaper.IMiuiWallpaperManagerService");
                    parcelObtain.writeStrongInterface(iMiuiKeyguardWallpaperCallback);
                    this.mRemote.transact(16, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
            public boolean blurState(int i) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.miwallpaper.IMiuiWallpaperManagerService");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
            public void changeMiuiWallpaperInfo(int i, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.miwallpaper.IMiuiWallpaperManagerService");
                    parcelObtain.writeInt(i);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(27, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
            public int getEffectId(int i) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.miwallpaper.IMiuiWallpaperManagerService");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
            public String getGalleryJson(int i) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.miwallpaper.IMiuiWallpaperManagerService");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
            public Map getMiuiLockPartWallpaperIsDeep(int i, boolean z, Map map) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.miwallpaper.IMiuiWallpaperManagerService");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(z ? 1 : 0);
                    parcelObtain.writeMap(map);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
            public Map getMiuiWallpaperPalette(int i, boolean z, Map map) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.miwallpaper.IMiuiWallpaperManagerService");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(z ? 1 : 0);
                    parcelObtain.writeMap(map);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readHashMap(getClass().getClassLoader());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
            public String getMiuiWallpaperPath(String str, int i, boolean z, boolean z2) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.miwallpaper.IMiuiWallpaperManagerService");
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(z ? 1 : 0);
                    parcelObtain.writeInt(z2 ? 1 : 0);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
            public ParcelFileDescriptor getMiuiWallpaperPreview(int i) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.miwallpaper.IMiuiWallpaperManagerService");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParcelFileDescriptor) _Parcel.readTypedObject(parcelObtain2, ParcelFileDescriptor.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
            public float getMiuiWallpaperSdkVersion() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.miwallpaper.IMiuiWallpaperManagerService");
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readFloat();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
            public int getMiuiWallpaperSdkVersionCode() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.miwallpaper.IMiuiWallpaperManagerService");
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
            public String getMiuiWallpaperType(int i) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.miwallpaper.IMiuiWallpaperManagerService");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
            public List getVideoDepthScreenshot() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.miwallpaper.IMiuiWallpaperManagerService");
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(Bitmap.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
            public Bundle getWallpaperInfo(int i, String str, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.miwallpaper.IMiuiWallpaperManagerService");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (Bundle) _Parcel.readTypedObject(parcelObtain2, Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
            public boolean isMiuiDefaultWallpaper(int i) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.miwallpaper.IMiuiWallpaperManagerService");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
            public boolean isMiuiWallpaperComponentUsing(int i) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.miwallpaper.IMiuiWallpaperManagerService");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
            public void registerWallpaperChangeListener(IMiuiWallpaperManagerCallback iMiuiWallpaperManagerCallback, int i, String str) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.miwallpaper.IMiuiWallpaperManagerService");
                    parcelObtain.writeStrongInterface(iMiuiWallpaperManagerCallback);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
            public void setLockscreenVideoSurface(Surface surface, Surface surface2) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.miwallpaper.IMiuiWallpaperManagerService");
                    _Parcel.writeTypedObject(parcelObtain, surface, 0);
                    _Parcel.writeTypedObject(parcelObtain, surface2, 0);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
            public void setLockscreenVideoSurfaceVisible(boolean z) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.miwallpaper.IMiuiWallpaperManagerService");
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
            public Map setWallpaper(int i, String str, String str2, ComponentName componentName, boolean z, List list) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.miwallpaper.IMiuiWallpaperManagerService");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    _Parcel.writeTypedObject(parcelObtain, componentName, 0);
                    parcelObtain.writeInt(z ? 1 : 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    HashMap hashMap = parcelObtain2.readHashMap(getClass().getClassLoader());
                    parcelObtain2.readStringList(list);
                    return hashMap;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
            public Map setWallpaper2(int i, String str, String str2, ComponentName componentName, boolean z, boolean z2, List list) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.miwallpaper.IMiuiWallpaperManagerService");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    _Parcel.writeTypedObject(parcelObtain, componentName, 0);
                    parcelObtain.writeInt(z ? 1 : 0);
                    parcelObtain.writeInt(z2 ? 1 : 0);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    HashMap hashMap = parcelObtain2.readHashMap(getClass().getClassLoader());
                    parcelObtain2.readStringList(list);
                    return hashMap;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
            public Map setWallpaper3(int i, String str, ComponentName componentName, List list, Bundle bundle) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.miwallpaper.IMiuiWallpaperManagerService");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    _Parcel.writeTypedObject(parcelObtain, componentName, 0);
                    _Parcel.writeTypedObject(parcelObtain, bundle, 0);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    HashMap hashMap = parcelObtain2.readHashMap(getClass().getClassLoader());
                    parcelObtain2.readStringList(list);
                    return hashMap;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
            public boolean supportDark(int i) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.miwallpaper.IMiuiWallpaperManagerService");
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.miui.miwallpaper.IMiuiWallpaperManagerService
            public void unRegisterWallpaperChangeListener(IMiuiWallpaperManagerCallback iMiuiWallpaperManagerCallback) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("com.miui.miwallpaper.IMiuiWallpaperManagerService");
                    parcelObtain.writeStrongInterface(iMiuiWallpaperManagerCallback);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public static IMiuiWallpaperManagerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.miui.miwallpaper.IMiuiWallpaperManagerService");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IMiuiWallpaperManagerService)) ? new Proxy(iBinder) : (IMiuiWallpaperManagerService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.miui.miwallpaper.IMiuiWallpaperManagerService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.miui.miwallpaper.IMiuiWallpaperManagerService");
                return true;
            }
            switch (i) {
                case 1:
                    float miuiWallpaperSdkVersion = getMiuiWallpaperSdkVersion();
                    parcel2.writeNoException();
                    parcel2.writeFloat(miuiWallpaperSdkVersion);
                    return true;
                case 2:
                    int i3 = parcel.readInt();
                    String string = parcel.readString();
                    String string2 = parcel.readString();
                    ComponentName componentName = (ComponentName) _Parcel.readTypedObject(parcel, ComponentName.CREATOR);
                    ArrayList arrayList = new ArrayList();
                    Map miuiWallpaper = setMiuiWallpaper(i3, string, string2, componentName, arrayList);
                    parcel2.writeNoException();
                    parcel2.writeMap(miuiWallpaper);
                    parcel2.writeStringList(arrayList);
                    return true;
                case 3:
                    turnOffFashionGallery(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 4:
                    clearWallpaper(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 5:
                    WallpaperColors miuiWallpaperColors = getMiuiWallpaperColors(parcel.readInt());
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, miuiWallpaperColors, 1);
                    return true;
                case 6:
                    String miuiWallpaperType = getMiuiWallpaperType(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeString(miuiWallpaperType);
                    return true;
                case 7:
                    boolean z = false;
                    String string3 = parcel.readString();
                    int i4 = parcel.readInt();
                    boolean z2 = parcel.readInt() != 0;
                    if (parcel.readInt() != 0) {
                        z = true;
                    }
                    String miuiWallpaperPath = getMiuiWallpaperPath(string3, i4, z2, z);
                    parcel2.writeNoException();
                    parcel2.writeString(miuiWallpaperPath);
                    return true;
                case 8:
                    ParcelFileDescriptor miuiWallpaperPreview = getMiuiWallpaperPreview(parcel.readInt());
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, miuiWallpaperPreview, 1);
                    return true;
                case 9:
                    registerWallpaperChangeListener(IMiuiWallpaperManagerCallback.Stub.asInterface(parcel.readStrongBinder()), parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 10:
                    unRegisterWallpaperChangeListener(IMiuiWallpaperManagerCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 11:
                    Parcelable.Creator creator = RemoteViews.CREATOR;
                    setGalleryRemoteView((RemoteViews) _Parcel.readTypedObject(parcel, creator), (RemoteViews) _Parcel.readTypedObject(parcel, creator));
                    parcel2.writeNoException();
                    return true;
                case 12:
                    String galleryJson = getGalleryJson(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeString(galleryJson);
                    return true;
                case 13:
                    int i5 = parcel.readInt();
                    String string4 = parcel.readString();
                    String string5 = parcel.readString();
                    ComponentName componentName2 = (ComponentName) _Parcel.readTypedObject(parcel, ComponentName.CREATOR);
                    boolean z3 = parcel.readInt() != 0;
                    ArrayList arrayList2 = new ArrayList();
                    Map wallpaper = setWallpaper(i5, string4, string5, componentName2, z3, arrayList2);
                    parcel2.writeNoException();
                    parcel2.writeMap(wallpaper);
                    parcel2.writeStringList(arrayList2);
                    return true;
                case 14:
                    String lastMiuiWallpaperType = getLastMiuiWallpaperType(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeString(lastMiuiWallpaperType);
                    return true;
                case 15:
                    String miuiPresetWallpaperPath = getMiuiPresetWallpaperPath();
                    parcel2.writeNoException();
                    parcel2.writeString(miuiPresetWallpaperPath);
                    return true;
                case 16:
                    bindSystemUIProxy(IMiuiKeyguardWallpaperCallback.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 17:
                    showWallpaperScreenOnAnim(parcel.readInt() != 0);
                    return true;
                case 18:
                    updateKeyguardWallpaperState(parcel.readInt() != 0);
                    return true;
                case 19:
                    updateKeyguardWallpaperRatio(parcel.readFloat(), parcel.readLong());
                    return true;
                case 20:
                    showWallpaperUnlockAnim();
                    return true;
                case 21:
                    int i6 = parcel.readInt();
                    String string6 = parcel.readString();
                    boolean z4 = false;
                    String string7 = parcel.readString();
                    ComponentName componentName3 = (ComponentName) _Parcel.readTypedObject(parcel, ComponentName.CREATOR);
                    if (parcel.readInt() != 0) {
                        z4 = true;
                    }
                    boolean z5 = parcel.readInt() != 0;
                    ArrayList arrayList3 = new ArrayList();
                    Map wallpaper2 = setWallpaper2(i6, string6, string7, componentName3, z4, z5, arrayList3);
                    parcel2.writeNoException();
                    parcel2.writeMap(wallpaper2);
                    parcel2.writeStringList(arrayList3);
                    return true;
                case 22:
                    int wallpaperBlurColor = getWallpaperBlurColor(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(wallpaperBlurColor);
                    return true;
                case 23:
                    updateKeyguardWallpaperStateAnim(parcel.readInt() != 0, parcel.readInt() != 0, parcel.readInt(), IRemoteAnimationFinishedCallback.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                case 24:
                    boolean zIsMiuiDefaultWallpaper = isMiuiDefaultWallpaper(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsMiuiDefaultWallpaper ? 1 : 0);
                    return true;
                case 25:
                    String miuiDefaultWallpaperType = getMiuiDefaultWallpaperType(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeString(miuiDefaultWallpaperType);
                    return true;
                case 26:
                    int i7 = parcel.readInt();
                    String string8 = parcel.readString();
                    ComponentName componentName4 = (ComponentName) _Parcel.readTypedObject(parcel, ComponentName.CREATOR);
                    ArrayList arrayList4 = new ArrayList();
                    Map wallpaper3 = setWallpaper3(i7, string8, componentName4, arrayList4, (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeMap(wallpaper3);
                    parcel2.writeStringList(arrayList4);
                    return true;
                case 27:
                    changeMiuiWallpaperInfo(parcel.readInt(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                    return true;
                case 28:
                    clearWallpaperForPackage(parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    return true;
                case 29:
                    Map miuiWallpaperPalette = getMiuiWallpaperPalette(parcel.readInt(), parcel.readInt() != 0, parcel.readHashMap(getClass().getClassLoader()));
                    parcel2.writeNoException();
                    parcel2.writeMap(miuiWallpaperPalette);
                    return true;
                case 30:
                    Map miuiLockPartWallpaperIsDeep = getMiuiLockPartWallpaperIsDeep(parcel.readInt(), parcel.readInt() != 0, parcel.readHashMap(getClass().getClassLoader()));
                    parcel2.writeNoException();
                    parcel2.writeMap(miuiLockPartWallpaperIsDeep);
                    return true;
                case 31:
                    SurfaceControl surfaceControl = getSurfaceControl(parcel.readInt());
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, surfaceControl, 1);
                    return true;
                case 32:
                    boolean zSupportDark = supportDark(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(zSupportDark ? 1 : 0);
                    return true;
                case 33:
                    boolean zBlurState = blurState(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(zBlurState ? 1 : 0);
                    return true;
                case 34:
                    boolean zIsMiuiWallpaperComponentUsing = isMiuiWallpaperComponentUsing(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsMiuiWallpaperComponentUsing ? 1 : 0);
                    return true;
                case 35:
                    int effectId = getEffectId(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(effectId);
                    return true;
                case 36:
                    int miuiWallpaperSdkVersionCode = getMiuiWallpaperSdkVersionCode();
                    parcel2.writeNoException();
                    parcel2.writeInt(miuiWallpaperSdkVersionCode);
                    return true;
                case 37:
                    ParcelFileDescriptor miuiWallpaperRotationPreview = getMiuiWallpaperRotationPreview(parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, miuiWallpaperRotationPreview, 1);
                    return true;
                case 38:
                    updateKeyguardWallpaperStateAnimBlock(parcel.readInt() != 0, parcel.readInt() != 0, parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 39:
                    List videoDepthScreenshot = getVideoDepthScreenshot();
                    parcel2.writeNoException();
                    _Parcel.writeTypedList(parcel2, videoDepthScreenshot, 1);
                    return true;
                case 40:
                    Parcelable.Creator creator2 = Surface.CREATOR;
                    setLockscreenVideoSurface((Surface) _Parcel.readTypedObject(parcel, creator2), (Surface) _Parcel.readTypedObject(parcel, creator2));
                    parcel2.writeNoException();
                    return true;
                case 41:
                    setLocalVideoSurface((Surface) _Parcel.readTypedObject(parcel, Surface.CREATOR));
                    parcel2.writeNoException();
                    return true;
                case 42:
                    setLockscreenVideoSurfaceVisible(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    controlVideoDepth(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 44:
                    updateVideoWallpaperFrame(parcel.readInt());
                    return true;
                case 45:
                    SurfaceControl bbqSurfaceControl = getBbqSurfaceControl(parcel.readInt());
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, bbqSurfaceControl, 1);
                    return true;
                case 46:
                    resetDesktopStatus();
                    parcel2.writeNoException();
                    return true;
                case 47:
                    Bundle bundle = (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR);
                    int i8 = parcel.readInt();
                    int[] iArr = i8 < 0 ? null : new int[i8];
                    changeDesktopEffectType(bundle, iArr);
                    parcel2.writeNoException();
                    parcel2.writeIntArray(iArr);
                    return true;
                case 48:
                    changeScrollWithScreen(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    return true;
                case 49:
                    boolean zCouldSetEffectToDesktop = couldSetEffectToDesktop();
                    parcel2.writeNoException();
                    parcel2.writeInt(zCouldSetEffectToDesktop ? 1 : 0);
                    return true;
                case 50:
                    boolean zSupportEffectChange = supportEffectChange(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeInt(zSupportEffectChange ? 1 : 0);
                    return true;
                case 51:
                    boolean zIsCanBeSeeStreaming = isCanBeSeeStreaming(parcel.readInt() != 0);
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsCanBeSeeStreaming ? 1 : 0);
                    return true;
                case 52:
                    List enablePreviewEffectList = getEnablePreviewEffectList(parcel.readInt());
                    parcel2.writeNoException();
                    parcel2.writeList(enablePreviewEffectList);
                    return true;
                case 53:
                    Bundle wallpaperInfo = getWallpaperInfo(parcel.readInt(), parcel.readString(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, wallpaperInfo, 1);
                    return true;
                case 54:
                    getVideoDepthLastFrame(IMiuiVideoDepthLastFrameCallback.Stub.asInterface(parcel.readStrongBinder()));
                    parcel2.writeNoException();
                    return true;
                case 55:
                    boolean galleryContent = setGalleryContent(parcel.readInt(), parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeInt(galleryContent ? 1 : 0);
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

        public static void writeTypedList(Parcel parcel, List list, int i) {
            if (list == null) {
                parcel.writeInt(-1);
                return;
            }
            int size = list.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                writeTypedObject(parcel, (Parcelable) list.get(i2), i);
            }
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

    void bindSystemUIProxy(IMiuiKeyguardWallpaperCallback iMiuiKeyguardWallpaperCallback);

    boolean blurState(int i);

    void changeDesktopEffectType(Bundle bundle, int[] iArr);

    void changeMiuiWallpaperInfo(int i, Bundle bundle);

    void changeScrollWithScreen(boolean z);

    void clearWallpaper(int i);

    void clearWallpaperForPackage(int i, String str);

    void controlVideoDepth(int i);

    boolean couldSetEffectToDesktop();

    SurfaceControl getBbqSurfaceControl(int i);

    int getEffectId(int i);

    List getEnablePreviewEffectList(int i);

    String getGalleryJson(int i);

    String getLastMiuiWallpaperType(int i);

    String getMiuiDefaultWallpaperType(int i);

    Map getMiuiLockPartWallpaperIsDeep(int i, boolean z, Map map);

    String getMiuiPresetWallpaperPath();

    WallpaperColors getMiuiWallpaperColors(int i);

    Map getMiuiWallpaperPalette(int i, boolean z, Map map);

    String getMiuiWallpaperPath(String str, int i, boolean z, boolean z2);

    ParcelFileDescriptor getMiuiWallpaperPreview(int i);

    ParcelFileDescriptor getMiuiWallpaperRotationPreview(int i, int i2);

    float getMiuiWallpaperSdkVersion();

    int getMiuiWallpaperSdkVersionCode();

    String getMiuiWallpaperType(int i);

    SurfaceControl getSurfaceControl(int i);

    void getVideoDepthLastFrame(IMiuiVideoDepthLastFrameCallback iMiuiVideoDepthLastFrameCallback);

    List getVideoDepthScreenshot();

    int getWallpaperBlurColor(int i);

    Bundle getWallpaperInfo(int i, String str, Bundle bundle);

    boolean isCanBeSeeStreaming(boolean z);

    boolean isMiuiDefaultWallpaper(int i);

    boolean isMiuiWallpaperComponentUsing(int i);

    void registerWallpaperChangeListener(IMiuiWallpaperManagerCallback iMiuiWallpaperManagerCallback, int i, String str);

    void resetDesktopStatus();

    boolean setGalleryContent(int i, String str);

    void setGalleryRemoteView(RemoteViews remoteViews, RemoteViews remoteViews2);

    void setLocalVideoSurface(Surface surface);

    void setLockscreenVideoSurface(Surface surface, Surface surface2);

    void setLockscreenVideoSurfaceVisible(boolean z);

    Map setMiuiWallpaper(int i, String str, String str2, ComponentName componentName, List list);

    Map setWallpaper(int i, String str, String str2, ComponentName componentName, boolean z, List list);

    Map setWallpaper2(int i, String str, String str2, ComponentName componentName, boolean z, boolean z2, List list);

    Map setWallpaper3(int i, String str, ComponentName componentName, List list, Bundle bundle);

    void showWallpaperScreenOnAnim(boolean z);

    void showWallpaperUnlockAnim();

    boolean supportDark(int i);

    boolean supportEffectChange(int i);

    void turnOffFashionGallery(int i);

    void unRegisterWallpaperChangeListener(IMiuiWallpaperManagerCallback iMiuiWallpaperManagerCallback);

    void updateKeyguardWallpaperRatio(float f, long j);

    void updateKeyguardWallpaperState(boolean z);

    void updateKeyguardWallpaperStateAnim(boolean z, boolean z2, int i, IRemoteAnimationFinishedCallback iRemoteAnimationFinishedCallback);

    void updateKeyguardWallpaperStateAnimBlock(boolean z, boolean z2, int i);

    void updateVideoWallpaperFrame(int i);
}
