package com.miui.aod.aidl;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public interface IAttentionCallback extends android.service.attention.IAttentionCallback {

    public static abstract class Stub extends Binder implements IAttentionCallback {

        public static class Proxy implements IAttentionCallback {
            public IBinder mRemote;

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }
        }

        public Stub() {
            attachInterface(this, "android.service.attention.IAttentionCallback");
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i == 1) {
                parcel.enforceInterface("android.service.attention.IAttentionCallback");
                onSuccess(parcel.readInt(), parcel.readLong());
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface("android.service.attention.IAttentionCallback");
                onFailure(parcel.readInt());
                return true;
            }
            if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel2.writeString("android.service.attention.IAttentionCallback");
            return true;
        }
    }
}
