package androidx.core.util;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: Pools.kt */
/* JADX INFO: loaded from: classes.dex */
@Metadata
public final class Pools {

    /* JADX INFO: compiled from: Pools.kt */
    @Metadata
    public interface Pool<T> {
        Object acquire();

        boolean release(Object obj);
    }

    /* JADX INFO: compiled from: Pools.kt */
    @Metadata
    @SourceDebugExtension
    public static class SimplePool<T> implements Pool<T> {
        public final Object[] pool;
        public int poolSize;

        public SimplePool(int i) {
            if (i <= 0) {
                throw new IllegalArgumentException("The max pool size must be > 0");
            }
            this.pool = new Object[i];
        }

        @Override // androidx.core.util.Pools.Pool
        public Object acquire() {
            int i = this.poolSize;
            if (i <= 0) {
                return null;
            }
            int i2 = i - 1;
            Object obj = this.pool[i2];
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type T of androidx.core.util.Pools.SimplePool");
            this.pool[i2] = null;
            this.poolSize--;
            return obj;
        }

        public final boolean isInPool(Object obj) {
            int i = this.poolSize;
            for (int i2 = 0; i2 < i; i2++) {
                if (this.pool[i2] == obj) {
                    return true;
                }
            }
            return false;
        }

        @Override // androidx.core.util.Pools.Pool
        public boolean release(Object instance) {
            Intrinsics.checkNotNullParameter(instance, "instance");
            if (isInPool(instance)) {
                throw new IllegalStateException("Already in the pool!");
            }
            int i = this.poolSize;
            Object[] objArr = this.pool;
            if (i >= objArr.length) {
                return false;
            }
            objArr[i] = instance;
            this.poolSize = i + 1;
            return true;
        }
    }

    /* JADX INFO: compiled from: Pools.kt */
    @Metadata
    @SourceDebugExtension
    public static class SynchronizedPool<T> extends SimplePool<T> {
        public final Object lock;

        public SynchronizedPool(int i) {
            super(i);
            this.lock = new Object();
        }

        @Override // androidx.core.util.Pools.SimplePool, androidx.core.util.Pools.Pool
        public Object acquire() {
            Object objAcquire;
            synchronized (this.lock) {
                objAcquire = super.acquire();
            }
            return objAcquire;
        }

        @Override // androidx.core.util.Pools.SimplePool, androidx.core.util.Pools.Pool
        public boolean release(Object instance) {
            boolean zRelease;
            Intrinsics.checkNotNullParameter(instance, "instance");
            synchronized (this.lock) {
                zRelease = super.release(instance);
            }
            return zRelease;
        }
    }
}
