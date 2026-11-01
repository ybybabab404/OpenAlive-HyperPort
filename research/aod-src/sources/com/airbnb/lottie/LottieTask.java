package com.airbnb.lottie;

import android.os.Handler;
import android.os.Looper;
import com.airbnb.lottie.utils.Logger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/* JADX INFO: loaded from: classes.dex */
public class LottieTask<T> {
    public static Executor EXECUTOR = Executors.newCachedThreadPool();
    public final Set failureListeners;
    public final Handler handler;
    public volatile LottieResult result;
    public final Set successListeners;

    public class LottieFutureTask extends FutureTask<LottieResult<T>> {
        public LottieFutureTask(Callable callable) {
            super(callable);
        }

        @Override // java.util.concurrent.FutureTask
        public void done() {
            if (isCancelled()) {
                return;
            }
            try {
                LottieTask.this.setResult(get());
            } catch (InterruptedException | ExecutionException e) {
                LottieTask.this.setResult(new LottieResult(e));
            }
        }
    }

    public static /* synthetic */ void $r8$lambda$UC3_JTMsZz4BrYi2A85clsQgepU(LottieTask lottieTask) {
        LottieResult lottieResult = lottieTask.result;
        if (lottieResult == null) {
            return;
        }
        if (lottieResult.getValue() != null) {
            lottieTask.notifySuccessListeners(lottieResult.getValue());
        } else {
            lottieTask.notifyFailureListeners(lottieResult.getException());
        }
    }

    public LottieTask(Callable callable) {
        this(callable, false);
    }

    public LottieTask(Callable callable, boolean z) {
        this.successListeners = new LinkedHashSet(1);
        this.failureListeners = new LinkedHashSet(1);
        this.handler = new Handler(Looper.getMainLooper());
        this.result = null;
        if (!z) {
            EXECUTOR.execute(new LottieFutureTask(callable));
            return;
        }
        try {
            setResult((LottieResult) callable.call());
        } catch (Throwable th) {
            setResult(new LottieResult(th));
        }
    }

    public synchronized LottieTask addFailureListener(LottieListener lottieListener) {
        try {
            LottieResult lottieResult = this.result;
            if (lottieResult != null && lottieResult.getException() != null) {
                lottieListener.onResult(lottieResult.getException());
            }
            this.failureListeners.add(lottieListener);
        } catch (Throwable th) {
            throw th;
        }
        return this;
    }

    public synchronized LottieTask addListener(LottieListener lottieListener) {
        try {
            LottieResult lottieResult = this.result;
            if (lottieResult != null && lottieResult.getValue() != null) {
                lottieListener.onResult(lottieResult.getValue());
            }
            this.successListeners.add(lottieListener);
        } catch (Throwable th) {
            throw th;
        }
        return this;
    }

    public final synchronized void notifyFailureListeners(Throwable th) {
        ArrayList arrayList = new ArrayList(this.failureListeners);
        if (arrayList.isEmpty()) {
            Logger.warning("Lottie encountered an error but no failure listener was added:", th);
            return;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            ((LottieListener) it.next()).onResult(th);
        }
    }

    public final void notifyListeners() {
        this.handler.post(new Runnable() { // from class: com.airbnb.lottie.LottieTask$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                LottieTask.$r8$lambda$UC3_JTMsZz4BrYi2A85clsQgepU(this.f$0);
            }
        });
    }

    public final synchronized void notifySuccessListeners(Object obj) {
        Iterator it = new ArrayList(this.successListeners).iterator();
        while (it.hasNext()) {
            ((LottieListener) it.next()).onResult(obj);
        }
    }

    public synchronized LottieTask removeFailureListener(LottieListener lottieListener) {
        this.failureListeners.remove(lottieListener);
        return this;
    }

    public synchronized LottieTask removeListener(LottieListener lottieListener) {
        this.successListeners.remove(lottieListener);
        return this;
    }

    public final void setResult(LottieResult lottieResult) {
        if (this.result != null) {
            throw new IllegalStateException("A task may only be set once.");
        }
        this.result = lottieResult;
        notifyListeners();
    }
}
