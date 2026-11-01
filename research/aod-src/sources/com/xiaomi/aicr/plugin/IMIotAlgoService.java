package com.xiaomi.aicr.plugin;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes2.dex */
public interface IMIotAlgoService extends IInterface {

    public static class Default implements IMIotAlgoService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IMIotAlgoService {

        public static class Proxy implements IMIotAlgoService {
            public IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }
        }

        public static IMIotAlgoService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.xiaomi.aicr.plugin.IMIotAlgoService");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IMIotAlgoService)) ? new Proxy(iBinder) : (IMIotAlgoService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.xiaomi.aicr.plugin.IMIotAlgoService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.xiaomi.aicr.plugin.IMIotAlgoService");
                return true;
            }
            if (i == 1) {
                initAlgo(parcel.readString());
                parcel2.writeNoException();
            } else if (i == 2) {
                String strDoCalculation = doCalculation(parcel.readString());
                parcel2.writeNoException();
                parcel2.writeString(strDoCalculation);
            } else if (i == 3) {
                releaseAlgo();
                parcel2.writeNoException();
            } else if (i == 4) {
                String brightness = getBrightness();
                parcel2.writeNoException();
                parcel2.writeString(brightness);
            } else {
                if (i != 5) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                String caculatedCurve = getCaculatedCurve();
                parcel2.writeNoException();
                parcel2.writeString(caculatedCurve);
            }
            return true;
        }
    }

    String doCalculation(String str);

    String getBrightness();

    String getCaculatedCurve();

    void initAlgo(String str);

    void releaseAlgo();
}
