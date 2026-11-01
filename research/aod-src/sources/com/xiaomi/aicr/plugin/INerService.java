package com.xiaomi.aicr.plugin;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public interface INerService extends IInterface {

    public static class Default implements INerService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements INerService {

        public static class Proxy implements INerService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }
        }

        public static INerService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.aicr.plugin.INerService");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof INerService)) ? new Proxy(iBinder) : (INerService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.xiaomi.aicr.plugin.INerService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.xiaomi.aicr.plugin.INerService");
                return true;
            }
            if (i == 1) {
                List listExtract = extract(parcel.readString());
                parcel2.writeNoException();
                parcel2.writeTypedList(listExtract);
            } else {
                if (i != 2) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                List<String> listCut = cut(parcel.readString());
                parcel2.writeNoException();
                parcel2.writeStringList(listCut);
            }
            return true;
        }
    }

    public static class _Parcel {
    }

    List cut(String str);

    List extract(String str);
}
