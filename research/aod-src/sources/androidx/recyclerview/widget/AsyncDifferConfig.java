package androidx.recyclerview.widget;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
public final class AsyncDifferConfig<T> {
    public final Executor mBackgroundThreadExecutor;
    public final DiffUtil.ItemCallback mDiffCallback;
    public final Executor mMainThreadExecutor;

    public static final class Builder<T> {
        public static Executor sDiffExecutor;
        public static final Object sExecutorLock = new Object();
        public Executor mBackgroundThreadExecutor;
        public final DiffUtil.ItemCallback mDiffCallback;
        public Executor mMainThreadExecutor;

        public Builder(DiffUtil.ItemCallback itemCallback) {
            this.mDiffCallback = itemCallback;
        }

        public AsyncDifferConfig build() {
            if (this.mBackgroundThreadExecutor == null) {
                synchronized (sExecutorLock) {
                    try {
                        if (sDiffExecutor == null) {
                            sDiffExecutor = Executors.newFixedThreadPool(2);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                this.mBackgroundThreadExecutor = sDiffExecutor;
            }
            return new AsyncDifferConfig(this.mMainThreadExecutor, this.mBackgroundThreadExecutor, this.mDiffCallback);
        }
    }

    public AsyncDifferConfig(Executor executor, Executor executor2, DiffUtil.ItemCallback itemCallback) {
        this.mMainThreadExecutor = executor;
        this.mBackgroundThreadExecutor = executor2;
        this.mDiffCallback = itemCallback;
    }

    public Executor getBackgroundThreadExecutor() {
        return this.mBackgroundThreadExecutor;
    }

    public DiffUtil.ItemCallback getDiffCallback() {
        return this.mDiffCallback;
    }

    public Executor getMainThreadExecutor() {
        return this.mMainThreadExecutor;
    }
}
