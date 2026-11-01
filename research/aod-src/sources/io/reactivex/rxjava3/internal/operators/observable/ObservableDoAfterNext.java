package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.observers.BasicFuseableObserver;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableDoAfterNext<T> extends AbstractObservableWithUpstream<T, T> {
    public final Consumer onAfterNext;

    public static final class DoAfterObserver<T> extends BasicFuseableObserver<T, T> {
        public final Consumer onAfterNext;

        public DoAfterObserver(Observer observer, Consumer consumer) {
            super(observer);
            this.onAfterNext = consumer;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            this.downstream.onNext(obj);
            if (this.sourceMode == 0) {
                try {
                    this.onAfterNext.accept(obj);
                } catch (Throwable th) {
                    fail(th);
                }
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public Object poll() {
            Object objPoll = this.qd.poll();
            if (objPoll != null) {
                this.onAfterNext.accept(objPoll);
            }
            return objPoll;
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer observer) {
        this.source.subscribe(new DoAfterObserver(observer, this.onAfterNext));
    }
}
