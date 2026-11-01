package com.miui.keyguardeditor;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes2.dex */
public interface IMiuiKeyguardEditorService extends IInterface {

    public static class Default implements IMiuiKeyguardEditorService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IMiuiKeyguardEditorService {

        public static class Proxy implements IMiuiKeyguardEditorService {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }
        }

        public Stub() {
            attachInterface(this, "com.miui.keyguardeditor.IMiuiKeyguardEditorService");
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface("com.miui.keyguardeditor.IMiuiKeyguardEditorService");
            }
            if (i == 1598968902) {
                parcel2.writeString("com.miui.keyguardeditor.IMiuiKeyguardEditorService");
                return true;
            }
            if (i == 1) {
                registerCallback(IMiuiKeyguardEditorCallback.Stub.asInterface(parcel.readStrongBinder()));
            } else if (i == 2) {
                unregisterCallback();
            } else {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                sendCommand(parcel.readString(), (Bundle) _Parcel.readTypedObject(parcel, Bundle.CREATOR));
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

    void registerCallback(IMiuiKeyguardEditorCallback iMiuiKeyguardEditorCallback);

    void sendCommand(String str, Bundle bundle);

    void unregisterCallback();
}
