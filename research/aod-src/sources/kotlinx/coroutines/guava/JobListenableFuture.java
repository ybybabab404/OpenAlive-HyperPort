package kotlinx.coroutines.guava;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.google.common.util.concurrent.Uninterruptibles;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlinx.coroutines.Job;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: ListenableFuture.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
final class JobListenableFuture<T> implements ListenableFuture<T> {
    public final SettableFuture auxFuture;
    public boolean auxFutureIsFailed;
    public final Job jobToCancel;

    @Override // com.google.common.util.concurrent.ListenableFuture
    public void addListener(Runnable runnable, Executor executor) {
        this.auxFuture.addListener(runnable, executor);
    }

    @Override // java.util.concurrent.Future
    public boolean cancel(boolean z) {
        if (!this.auxFuture.cancel(z)) {
            return false;
        }
        Job.DefaultImpls.cancel$default(this.jobToCancel, null, 1, null);
        return true;
    }

    public final boolean complete(Object obj) {
        return this.auxFuture.set(obj);
    }

    public final boolean completeExceptionallyOrCancel(Throwable th) {
        if (th instanceof CancellationException) {
            return this.auxFuture.set(new Cancelled((CancellationException) th));
        }
        boolean exception = this.auxFuture.setException(th);
        if (exception) {
            this.auxFutureIsFailed = true;
        }
        return exception;
    }

    @Override // java.util.concurrent.Future
    public Object get() {
        return getInternal(this.auxFuture.get());
    }

    @Override // java.util.concurrent.Future
    public Object get(long j, TimeUnit timeUnit) {
        return getInternal(this.auxFuture.get(j, timeUnit));
    }

    public final Object getInternal(Object obj) throws Throwable {
        if (obj instanceof Cancelled) {
            throw new CancellationException().initCause(((Cancelled) obj).exception);
        }
        return obj;
    }

    @Override // java.util.concurrent.Future
    public boolean isCancelled() {
        boolean z;
        if (this.auxFuture.isCancelled()) {
            return true;
        }
        if (isDone() && !this.auxFutureIsFailed) {
            try {
                z = Uninterruptibles.getUninterruptibly(this.auxFuture) instanceof Cancelled;
            } catch (CancellationException unused) {
                z = true;
            } catch (ExecutionException unused2) {
                this.auxFutureIsFailed = true;
                z = false;
            }
            if (z) {
                return true;
            }
        }
        return false;
    }

    @Override // java.util.concurrent.Future
    public boolean isDone() {
        return this.auxFuture.isDone();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append("[status=");
        if (isDone()) {
            try {
                Object uninterruptibly = Uninterruptibles.getUninterruptibly(this.auxFuture);
                if (uninterruptibly instanceof Cancelled) {
                    sb.append("CANCELLED, cause=[" + ((Cancelled) uninterruptibly).exception + ']');
                } else {
                    sb.append("SUCCESS, result=[" + uninterruptibly + ']');
                }
            } catch (CancellationException unused) {
                sb.append("CANCELLED");
            } catch (ExecutionException e) {
                sb.append("FAILURE, cause=[" + e.getCause() + ']');
            } catch (Throwable th) {
                sb.append("UNKNOWN, cause=[" + th.getClass() + " thrown from get()]");
            }
        } else {
            sb.append("PENDING, delegate=[" + this.auxFuture + ']');
        }
        sb.append(']');
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
