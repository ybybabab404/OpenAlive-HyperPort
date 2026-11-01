package com.xiaomi.taiyi.sdk.transfer.core;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import com.xiaomi.taiyi.sdk.transfer.data.AIRequest;
import com.xiaomi.taiyi.sdk.transfer.data.AIResponse;

/* JADX INFO: loaded from: classes2.dex */
public interface IAIAbility extends IInterface {
    public static final String DESCRIPTOR = "com.xiaomi.taiyi.sdk.transfer.core.IAIAbility";

    public static class Default implements IAIAbility {
        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
        public String api() {
            return null;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
        public int bind(IAIClient iAIClient, AIRequest aIRequest) {
            return 0;
        }

        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
        public AIResponse execute(AIRequest aIRequest) {
            return null;
        }

        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
        public IAIMemory getMemory(String str) {
            return null;
        }

        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
        public boolean hasSession() {
            return false;
        }

        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
        public boolean isAlive() {
            return false;
        }

        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
        public boolean isInternal() {
            return false;
        }

        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
        public boolean isLLM() {
            return false;
        }

        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
        public String name() {
            return null;
        }

        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
        public void post(AIRequest aIRequest, IAICallback iAICallback) {
        }

        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
        public AIResponse query(AIRequest aIRequest) {
            return null;
        }

        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
        public int type() {
            return 0;
        }

        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
        public int unbind(String str, AIRequest aIRequest) {
            return 0;
        }

        @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
        public long version() {
            return 0L;
        }
    }

    public static abstract class Stub extends Binder implements IAIAbility {
        static final int TRANSACTION_api = 1;
        static final int TRANSACTION_bind = 8;
        static final int TRANSACTION_execute = 13;
        static final int TRANSACTION_getMemory = 10;
        static final int TRANSACTION_hasSession = 11;
        static final int TRANSACTION_isAlive = 6;
        static final int TRANSACTION_isInternal = 7;
        static final int TRANSACTION_isLLM = 5;
        static final int TRANSACTION_name = 2;
        static final int TRANSACTION_post = 14;
        static final int TRANSACTION_query = 12;
        static final int TRANSACTION_type = 4;
        static final int TRANSACTION_unbind = 9;
        static final int TRANSACTION_version = 3;

        public static class Proxy implements IAIAbility {
            private IBinder mRemote;

            public Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
            public String api() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAIAbility.DESCRIPTOR);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
            public int bind(IAIClient iAIClient, AIRequest aIRequest) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAIAbility.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iAIClient);
                    _Parcel.writeTypedObject(parcelObtain, aIRequest, 0);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
            public AIResponse execute(AIRequest aIRequest) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAIAbility.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, aIRequest, 0);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AIResponse) _Parcel.readTypedObject(parcelObtain2, AIResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            public String getInterfaceDescriptor() {
                return IAIAbility.DESCRIPTOR;
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
            public IAIMemory getMemory(String str) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAIAbility.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return IAIMemory.Stub.asInterface(parcelObtain2.readStrongBinder());
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
            public boolean hasSession() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAIAbility.DESCRIPTOR);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
            public boolean isAlive() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAIAbility.DESCRIPTOR);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
            public boolean isInternal() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAIAbility.DESCRIPTOR);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
            public boolean isLLM() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAIAbility.DESCRIPTOR);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
            public String name() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAIAbility.DESCRIPTOR);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
            public void post(AIRequest aIRequest, IAICallback iAICallback) {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAIAbility.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, aIRequest, 0);
                    parcelObtain.writeStrongInterface(iAICallback);
                    this.mRemote.transact(14, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
            public AIResponse query(AIRequest aIRequest) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAIAbility.DESCRIPTOR);
                    _Parcel.writeTypedObject(parcelObtain, aIRequest, 0);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AIResponse) _Parcel.readTypedObject(parcelObtain2, AIResponse.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
            public int type() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAIAbility.DESCRIPTOR);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
            public int unbind(String str, AIRequest aIRequest) {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAIAbility.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    _Parcel.writeTypedObject(parcelObtain, aIRequest, 0);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // com.xiaomi.taiyi.sdk.transfer.core.IAIAbility
            public long version() {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(IAIAbility.DESCRIPTOR);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readLong();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, IAIAbility.DESCRIPTOR);
        }

        public static IAIAbility asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(IAIAbility.DESCRIPTOR);
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IAIAbility)) ? new Proxy(iBinder) : (IAIAbility) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(IAIAbility.DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(IAIAbility.DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String strApi = api();
                    parcel2.writeNoException();
                    parcel2.writeString(strApi);
                    return true;
                case 2:
                    String strName = name();
                    parcel2.writeNoException();
                    parcel2.writeString(strName);
                    return true;
                case 3:
                    long jVersion = version();
                    parcel2.writeNoException();
                    parcel2.writeLong(jVersion);
                    return true;
                case 4:
                    int iType = type();
                    parcel2.writeNoException();
                    parcel2.writeInt(iType);
                    return true;
                case 5:
                    boolean zIsLLM = isLLM();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsLLM ? 1 : 0);
                    return true;
                case 6:
                    boolean zIsAlive = isAlive();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsAlive ? 1 : 0);
                    return true;
                case 7:
                    boolean zIsInternal = isInternal();
                    parcel2.writeNoException();
                    parcel2.writeInt(zIsInternal ? 1 : 0);
                    return true;
                case 8:
                    int iBind = bind(IAIClient.Stub.asInterface(parcel.readStrongBinder()), (AIRequest) _Parcel.readTypedObject(parcel, AIRequest.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeInt(iBind);
                    return true;
                case 9:
                    int iUnbind = unbind(parcel.readString(), (AIRequest) _Parcel.readTypedObject(parcel, AIRequest.CREATOR));
                    parcel2.writeNoException();
                    parcel2.writeInt(iUnbind);
                    return true;
                case 10:
                    IAIMemory memory = getMemory(parcel.readString());
                    parcel2.writeNoException();
                    parcel2.writeStrongInterface(memory);
                    return true;
                case 11:
                    boolean zHasSession = hasSession();
                    parcel2.writeNoException();
                    parcel2.writeInt(zHasSession ? 1 : 0);
                    return true;
                case 12:
                    AIResponse aIResponseQuery = query((AIRequest) _Parcel.readTypedObject(parcel, AIRequest.CREATOR));
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, aIResponseQuery, 1);
                    return true;
                case 13:
                    AIResponse aIResponseExecute = execute((AIRequest) _Parcel.readTypedObject(parcel, AIRequest.CREATOR));
                    parcel2.writeNoException();
                    _Parcel.writeTypedObject(parcel2, aIResponseExecute, 1);
                    return true;
                case 14:
                    post((AIRequest) _Parcel.readTypedObject(parcel, AIRequest.CREATOR), IAICallback.Stub.asInterface(parcel.readStrongBinder()));
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
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

    String api();

    int bind(IAIClient iAIClient, AIRequest aIRequest);

    AIResponse execute(AIRequest aIRequest);

    IAIMemory getMemory(String str);

    boolean hasSession();

    boolean isAlive();

    boolean isInternal();

    boolean isLLM();

    String name();

    void post(AIRequest aIRequest, IAICallback iAICallback);

    AIResponse query(AIRequest aIRequest);

    int type();

    int unbind(String str, AIRequest aIRequest);

    long version();
}
