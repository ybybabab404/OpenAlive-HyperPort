package com.google.android.appfunctions.schema.internal.dependencies;

import com.google.android.appfunctions.schema.internal.dependencies.zzar;
import com.google.android.appfunctions.schema.internal.dependencies.zzax;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: compiled from: com.google.android.appfunctions:appfunctions-schema@@1.0.7 */
/* JADX INFO: loaded from: classes.dex */
public abstract class zzax<MessageType extends zzax<MessageType, BuilderType>, BuilderType extends zzar<MessageType, BuilderType>> extends zzc<MessageType, BuilderType> {
    private static final int MEMOIZED_SERIALIZED_SIZE_MASK = Integer.MAX_VALUE;
    private static final int MUTABLE_FLAG_MASK = Integer.MIN_VALUE;
    static final int UNINITIALIZED_HASH_CODE = 0;
    static final int UNINITIALIZED_SERIALIZED_SIZE = Integer.MAX_VALUE;
    private static Map<Class<?>, zzax<?, ?>> defaultInstanceMap = new ConcurrentHashMap();
    private int memoizedSerializedSize = -1;
    protected zzdd unknownFields = zzdd.zza();

    public static zzbh emptyProtobufList() {
        return zzcp.zzd();
    }

    public static zzax getDefaultInstance(Class cls) {
        zzax<?, ?> zzaxVar = defaultInstanceMap.get(cls);
        if (zzaxVar == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                zzaxVar = defaultInstanceMap.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (zzaxVar != null) {
            return zzaxVar;
        }
        zzax<?, ?> defaultInstanceForType = ((zzax) zzdk.zzc(cls)).getDefaultInstanceForType();
        if (defaultInstanceForType == null) {
            throw new IllegalStateException();
        }
        defaultInstanceMap.put(cls, defaultInstanceForType);
        return defaultInstanceForType;
    }

    public static Object invokeOrDie(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            if (cause instanceof Error) {
                throw ((Error) cause);
            }
            throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
        }
    }

    public static Object newMessageInfo(zzce zzceVar, String str, Object[] objArr) {
        return new zzcq(zzceVar, str, objArr);
    }

    public static void registerDefaultInstance(Class cls, zzax zzaxVar) {
        zzaxVar.markImmutable();
        defaultInstanceMap.put(cls, zzaxVar);
    }

    public Object buildMessageInfo() {
        return dynamicMethod(zzaw.BUILD_MESSAGE_INFO, null, null);
    }

    public void clearMemoizedHashCode() {
        this.memoizedHashCode = 0;
    }

    public void clearMemoizedSerializedSize() {
        setMemoizedSerializedSize(Integer.MAX_VALUE);
    }

    public int computeHashCode() {
        return zzco.zza().zzb(getClass()).zzc(this);
    }

    public final int computeSerializedSize(zzcs zzcsVar) {
        if (zzcsVar != null) {
            return zzcsVar.zze(this);
        }
        return zzco.zza().zzb(getClass()).zze(this);
    }

    public abstract Object dynamicMethod(zzaw zzawVar, Object obj, Object obj2);

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return zzco.zza().zzb(getClass()).zzb(this, (zzax) obj);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzcf
    public final zzax getDefaultInstanceForType() {
        return (zzax) dynamicMethod(zzaw.GET_DEFAULT_INSTANCE, null, null);
    }

    public int getMemoizedHashCode() {
        return this.memoizedHashCode;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzc
    public int getMemoizedSerializedSize() {
        return this.memoizedSerializedSize & Integer.MAX_VALUE;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzce
    public int getSerializedSize() {
        return getSerializedSize(null);
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzc
    public int getSerializedSize(zzcs zzcsVar) {
        if (!isMutable()) {
            if (getMemoizedSerializedSize() != Integer.MAX_VALUE) {
                return getMemoizedSerializedSize();
            }
            int iComputeSerializedSize = computeSerializedSize(zzcsVar);
            setMemoizedSerializedSize(iComputeSerializedSize);
            return iComputeSerializedSize;
        }
        int iComputeSerializedSize2 = computeSerializedSize(zzcsVar);
        if (iComputeSerializedSize2 >= 0) {
            return iComputeSerializedSize2;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(iComputeSerializedSize2).length() + 42);
        sb.append("serialized size must be non-negative, was ");
        sb.append(iComputeSerializedSize2);
        throw new IllegalStateException(sb.toString());
    }

    public int hashCode() {
        if (isMutable()) {
            return computeHashCode();
        }
        if (hashCodeIsNotMemoized()) {
            setMemoizedHashCode(computeHashCode());
        }
        return getMemoizedHashCode();
    }

    public boolean hashCodeIsNotMemoized() {
        return getMemoizedHashCode() == 0;
    }

    public boolean isMutable() {
        return (this.memoizedSerializedSize & Integer.MIN_VALUE) != 0;
    }

    public void makeImmutable() {
        zzco.zza().zzb(getClass()).zzk(this);
        markImmutable();
    }

    public void markImmutable() {
        this.memoizedSerializedSize &= Integer.MAX_VALUE;
    }

    public final zzar newBuilderForType() {
        return (zzar) dynamicMethod(zzaw.NEW_BUILDER, null, null);
    }

    public zzax newMutableInstance() {
        return (zzax) dynamicMethod(zzaw.NEW_MUTABLE_INSTANCE, null, null);
    }

    public void setMemoizedHashCode(int i) {
        this.memoizedHashCode = i;
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzc
    public void setMemoizedSerializedSize(int i) {
        if (i >= 0) {
            this.memoizedSerializedSize = i | (this.memoizedSerializedSize & Integer.MIN_VALUE);
            return;
        }
        StringBuilder sb = new StringBuilder(String.valueOf(i).length() + 42);
        sb.append("serialized size must be non-negative, was ");
        sb.append(i);
        throw new IllegalStateException(sb.toString());
    }

    public String toString() {
        return zzcg.zza(this, super.toString());
    }

    @Override // com.google.android.appfunctions.schema.internal.dependencies.zzce
    public void writeTo(zzac zzacVar) {
        zzco.zza().zzb(getClass()).zzf(this, zzad.zza(zzacVar));
    }
}
