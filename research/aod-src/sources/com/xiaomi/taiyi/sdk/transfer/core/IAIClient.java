package com.xiaomi.taiyi.sdk.transfer.core;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.taiyi.sdk.transfer.data.AIHeaders;

/* JADX INFO: loaded from: classes2.dex */
public interface IAIClient extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.taiyi.sdk.transfer.core.IAIClient";

    public static class Default implements IAIClient {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIClient
        public String getId() {
            return null;
        }

        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIClient
        public AIHeaders getInfo() {
            return null;
        }

        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIClient
        public IAIMemory getMemory() {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAIClient {
        static final int TRANSACTION_getId = 1;
        static final int TRANSACTION_getInfo = 2;
        static final int TRANSACTION_getMemory = 3;

        public static class Proxy implements IAIClient {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIClient
            public String getId() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAIClient.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIClient
            public AIHeaders getInfo() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAIClient.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AIHeaders) _Parcel.readTypedObject(parcelObtain2, AIHeaders.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IAIClient.DESCRIPTOR;
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIClient
            public IAIMemory getMemory() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAIClient.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IAIMemory.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IAIClient.DESCRIPTOR);
        }

        public static IAIClient asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAIClient.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IAIClient)) ? new Proxy(iBinder) : (IAIClient) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAIClient.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAIClient.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                String id = getId();
                parcel2.writeNoException();
                parcel2.writeString(id);
            } else if (i == 2) {
                AIHeaders info = getInfo();
                parcel2.writeNoException();
                _Parcel.writeTypedObject(parcel2, info, 1);
            } else {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                IAIMemory memory = getMemory();
                parcel2.writeNoException();
                parcel2.writeStrongInterface(memory);
            }
            return true;
        }
    }

    public static class _Parcel {
        /* JADX INFO: Access modifiers changed from: private */
        public static <T> T readTypedObject(Parcel parcel, Parcelable.Creator<T> creator) {
            if (parcel.readInt() != 0) {
                return creator.createFromParcel(parcel);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static <T extends Parcelable> void writeTypedObject(Parcel parcel, T t, int i) {
            if (t == null) {
                parcel.writeInt(0);
            } else {
                parcel.writeInt(1);
                t.writeToParcel(parcel, i);
            }
        }
    }

    String getId();

    AIHeaders getInfo();

    IAIMemory getMemory();
}
