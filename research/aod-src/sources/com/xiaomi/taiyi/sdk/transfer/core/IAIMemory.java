package com.xiaomi.taiyi.sdk.transfer.core;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.taiyi.sdk.transfer.data.ShareMemoryPool;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public interface IAIMemory extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.taiyi.sdk.transfer.core.IAIMemory";

    public static class Default implements IAIMemory {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIMemory
        public void free(List<String> list) {
        }

        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIMemory
        public ShareMemoryPool get() {
            return null;
        }

        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIMemory
        public void sync(ShareMemoryPool shareMemoryPool) {
        }
    }

    public static abstract class Stub extends Binder implements IAIMemory {
        static final int TRANSACTION_free = 1;
        static final int TRANSACTION_get = 3;
        static final int TRANSACTION_sync = 2;

        public static class Proxy implements IAIMemory {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIMemory
            public void free(List<String> list) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAIMemory.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIMemory
            public ShareMemoryPool get() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAIMemory.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ShareMemoryPool) _Parcel.readTypedObject(parcelObtain2, ShareMemoryPool.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IAIMemory.DESCRIPTOR;
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIMemory
            public void sync(ShareMemoryPool shareMemoryPool) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAIMemory.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, shareMemoryPool, 0);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IAIMemory.DESCRIPTOR);
        }

        public static IAIMemory asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAIMemory.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IAIMemory)) ? new Proxy(iBinder) : (IAIMemory) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAIMemory.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAIMemory.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                free(parcel.createStringArrayList());
                parcel2.writeNoException();
            } else if (i == 2) {
                sync((ShareMemoryPool) _Parcel.readTypedObject(parcel, ShareMemoryPool.CREATOR));
                parcel2.writeNoException();
            } else {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                ShareMemoryPool shareMemoryPool = get();
                parcel2.writeNoException();
                _Parcel.writeTypedObject(parcel2, shareMemoryPool, 1);
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

    void free(List<String> list);

    ShareMemoryPool get();

    void sync(ShareMemoryPool shareMemoryPool);
}
