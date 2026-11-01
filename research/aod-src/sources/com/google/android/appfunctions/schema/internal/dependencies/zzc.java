package com.google.android.appfunctions.schema.internal.dependencies;

import com.google.android.appfunctions.schema.internal.dependencies.zzb;
import com.google.android.appfunctions.schema.internal.dependencies.zzc;
import java.io.IOException;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
public abstract class zzc<MessageType extends zzc<MessageType, BuilderType>, BuilderType extends zzb<MessageType, BuilderType>> implements zzce {
    protected transient int memoizedHashCode = 0;

    public int getMemoizedSerializedSize() {
        throw new UnsupportedOperationException();
    }

    public int getSerializedSize(zzcs zzcsVar) {
        int memoizedSerializedSize = getMemoizedSerializedSize();
        if (memoizedSerializedSize != -1) {
            return memoizedSerializedSize;
        }
        int iZze = zzcsVar.zze(this);
        setMemoizedSerializedSize(iZze);
        return iZze;
    }

    public final String getSerializingExceptionMessage(String str) {
        String name = getClass().getName();
        StringBuilder sb = new StringBuilder(name.length() + 18 + String.valueOf(str).length() + 44);
        sb.append("Serializing ");
        sb.append(name);
        sb.append(" to a ");
        sb.append(str);
        sb.append(" threw an IOException (should never happen).");
        return sb.toString();
    }

    public void setMemoizedSerializedSize(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzce
    public zzt toByteString() {
        try {
            int serializedSize = getSerializedSize();
            zzt zztVar = zzt.zza;
            byte[] bArr = new byte[serializedSize];
            boolean z = zzac.zzc;
            zzz zzzVar = new zzz(bArr, 0, serializedSize);
            writeTo(zzzVar);
            zzzVar.zzF();
            return new zzr(bArr);
        } catch (IOException e) {
            throw new RuntimeException(getSerializingExceptionMessage("ByteString"), e);
        }
    }
}
