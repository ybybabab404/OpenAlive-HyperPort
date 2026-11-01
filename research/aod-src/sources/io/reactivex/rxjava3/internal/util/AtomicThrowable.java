package io.reactivex.rxjava3.internal.util;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.Emitter;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes2.dex */
public final class AtomicThrowable extends AtomicReference<Throwable> {
    public boolean isTerminated() {
        return get() == ExceptionHelper.TERMINATED;
    }

    public Throwable terminate() {
        return ExceptionHelper.terminate(this);
    }

    public boolean tryAddThrowable(Throwable th) {
        return ExceptionHelper.addThrowable(this, th);
    }

    public boolean tryAddThrowableOrReport(Throwable th) {
        if (tryAddThrowable(th)) {
            return true;
        }
        RxJavaPlugins.onError(th);
        return false;
    }

    public void tryTerminateAndReport() {
        Throwable thTerminate = terminate();
        if (thTerminate == null || thTerminate == ExceptionHelper.TERMINATED) {
            return;
        }
        RxJavaPlugins.onError(thTerminate);
    }

    public void tryTerminateConsumer(CompletableObserver completableObserver) {
        Throwable thTerminate = terminate();
        if (thTerminate == null) {
            completableObserver.onComplete();
        } else if (thTerminate != ExceptionHelper.TERMINATED) {
            completableObserver.onError(thTerminate);
        }
    }

    public void tryTerminateConsumer(Emitter emitter) {
        Throwable thTerminate = terminate();
        if (thTerminate == null) {
            emitter.onComplete();
        } else if (thTerminate != ExceptionHelper.TERMINATED) {
            emitter.onError(thTerminate);
        }
    }

    public void tryTerminateConsumer(Observer observer) {
        Throwable thTerminate = terminate();
        if (thTerminate == null) {
            observer.onComplete();
        } else if (thTerminate != ExceptionHelper.TERMINATED) {
            observer.onError(thTerminate);
        }
    }

    public void tryTerminateConsumer(SingleObserver singleObserver) {
        Throwable thTerminate = terminate();
        if (thTerminate == null || thTerminate == ExceptionHelper.TERMINATED) {
            return;
        }
        singleObserver.onError(thTerminate);
    }

    public void tryTerminateConsumer(Subscriber subscriber) {
        Throwable thTerminate = terminate();
        if (thTerminate == null) {
            subscriber.onComplete();
        } else if (thTerminate != ExceptionHelper.TERMINATED) {
            subscriber.onError(thTerminate);
        }
    }
}
