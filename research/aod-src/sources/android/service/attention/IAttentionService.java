package android.service.attention;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

/* JADX INFO: loaded from: classes.dex */
public interface IAttentionService extends IInterface {

    public static class Default implements IAttentionService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAttentionService {

        public static class Proxy implements IAttentionService {
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
                parcel.enforceInterface("android.service.attention.IAttentionService");
            }
            if (i == 1598968902) {
                parcel2.writeString("android.service.attention.IAttentionService");
                return true;
            }
            if (i == 1) {
                checkAttention(IAttentionCallback.Stub.asInterface(parcel.readStrongBinder()));
            } else if (i == 2) {
                cancelAttentionCheck(IAttentionCallback.Stub.asInterface(parcel.readStrongBinder()));
            } else if (i == 3) {
                onStartProximityUpdates(IProximityUpdateCallback.Stub.asInterface(parcel.readStrongBinder()));
            } else {
                if (i != 4) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                onStopProximityUpdates();
            }
            return true;
        }
    }

    void cancelAttentionCheck(IAttentionCallback iAttentionCallback);

    void checkAttention(IAttentionCallback iAttentionCallback);

    void onStartProximityUpdates(IProximityUpdateCallback iProximityUpdateCallback);

    void onStopProximityUpdates();
}
