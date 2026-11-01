package com.xiaomi.taiyi.sdk.transfer.core;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.taiyi.sdk.transfer.data.AIRequest;
import com.xiaomi.taiyi.sdk.transfer.data.AIResponse;

/* JADX INFO: loaded from: classes2.dex */
public interface IAIManager extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.taiyi.sdk.transfer.core.IAIManager";

    public static class Default implements IAIManager {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIManager
        public AIResponse authorize(AIRequest aIRequest) {
            return null;
        }

        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIManager
        public void loadAsync(AIRequest aIRequest, IAICallback iAICallback) {
        }

        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIManager
        public AIResponse loadSync(AIRequest aIRequest) {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAIManager {
        static final int TRANSACTION_authorize = 1;
        static final int TRANSACTION_loadAsync = 2;
        static final int TRANSACTION_loadSync = 3;

        public static class Proxy implements IAIManager {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIManager
            public AIResponse authorize(AIRequest aIRequest) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAIManager.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, aIRequest, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AIResponse) _Parcel.readTypedObject(parcelObtain2, AIResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IAIManager.DESCRIPTOR;
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIManager
            public void loadAsync(AIRequest aIRequest, IAICallback iAICallback) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAIManager.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, aIRequest, 0);
                    parcelObtain.writeStrongInterface(iAICallback);
                    this.mRemote.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIManager
            public AIResponse loadSync(AIRequest aIRequest) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAIManager.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, aIRequest, 0);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AIResponse) _Parcel.readTypedObject(parcelObtain2, AIResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IAIManager.DESCRIPTOR);
        }

        public static IAIManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAIManager.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IAIManager)) ? new Proxy(iBinder) : (IAIManager) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAIManager.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAIManager.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                AIResponse aIResponseAuthorize = authorize((AIRequest) _Parcel.readTypedObject(parcel, AIRequest.CREATOR));
                parcel2.writeNoException();
                _Parcel.writeTypedObject(parcel2, aIResponseAuthorize, 1);
            } else if (i == 2) {
                loadAsync((AIRequest) _Parcel.readTypedObject(parcel, AIRequest.CREATOR), IAICallback.Stub.asInterface(parcel.readStrongBinder()));
            } else {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                AIResponse aIResponseLoadSync = loadSync((AIRequest) _Parcel.readTypedObject(parcel, AIRequest.CREATOR));
                parcel2.writeNoException();
                _Parcel.writeTypedObject(parcel2, aIResponseLoadSync, 1);
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

    AIResponse authorize(AIRequest aIRequest);

    void loadAsync(AIRequest aIRequest, IAICallback iAICallback);

    AIResponse loadSync(AIRequest aIRequest);
}
