package com.xiaomi.taiyi.sdk.transfer.core;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.taiyi.sdk.transfer.data.AIRequest;
import com.xiaomi.taiyi.sdk.transfer.data.AIResponse;

/* JADX INFO: loaded from: classes2.dex */
public interface IAICoreService extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.taiyi.sdk.transfer.core.IAICoreService";

    public static class Default implements IAICoreService {
        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAICoreService
        public void onProcessStart(String str) {
        }

        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAICoreService
        public void onProcessStop(String str) {
        }

        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAICoreService
        public AIResponse prepare(AIRequest aIRequest) {
            return null;
        }
    }

    public static abstract class Stub extends Binder implements IAICoreService {
        static final int TRANSACTION_onProcessStart = 2;
        static final int TRANSACTION_onProcessStop = 3;
        static final int TRANSACTION_prepare = 1;

        public static class Proxy implements IAICoreService {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return IAICoreService.DESCRIPTOR;
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAICoreService
            public void onProcessStart(String str) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAICoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAICoreService
            public void onProcessStop(String str) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAICoreService.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAICoreService
            public AIResponse prepare(AIRequest aIRequest) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAICoreService.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, aIRequest, 0);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AIResponse) _Parcel.readTypedObject(parcelObtain2, AIResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IAICoreService.DESCRIPTOR);
        }

        public static IAICoreService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAICoreService.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IAICoreService)) ? new Proxy(iBinder) : (IAICoreService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAICoreService.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAICoreService.DESCRIPTOR);
                return true;
            }
            if (i == 1) {
                AIResponse aIResponsePrepare = prepare((AIRequest) _Parcel.readTypedObject(parcel, AIRequest.CREATOR));
                parcel2.writeNoException();
                _Parcel.writeTypedObject(parcel2, aIResponsePrepare, 1);
            } else if (i == 2) {
                onProcessStart(parcel.readString());
                parcel2.writeNoException();
            } else {
                if (i != 3) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                onProcessStop(parcel.readString());
                parcel2.writeNoException();
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

    void onProcessStart(String str);

    void onProcessStop(String str);

    AIResponse prepare(AIRequest aIRequest);
}
