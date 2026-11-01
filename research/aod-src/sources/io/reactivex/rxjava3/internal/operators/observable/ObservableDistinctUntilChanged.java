package io.reactivex.rxjava3.internal.operators.observable;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.functions.BiPredicate;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.observers.BasicFuseableObserver;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableDistinctUntilChanged<T, K> extends AbstractObservableWithUpstream<T, T> {
    public final BiPredicate comparer;
    public final Function keySelector;

    public static final class DistinctUntilChangedObserver<T, K> extends BasicFuseableObserver<T, T> {
        public final BiPredicate comparer;
        public boolean hasValue;
        public final Function keySelector;
        public Object last;

        public DistinctUntilChangedObserver(Observer observer, Function function, BiPredicate biPredicate) {
            super(observer);
            this.keySelector = function;
            this.comparer = biPredicate;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            if (this.done) {
                return;
            }
            if (this.sourceMode != 0) {
                this.downstream.onNext(obj);
                return;
            }
            try {
                Object objApply = this.keySelector.apply(obj);
                if (this.hasValue) {
                    boolean zTest = this.comparer.test(this.last, objApply);
                    this.last = objApply;
                    if (zTest) {
                        return;
                    }
                } else {
                    this.hasValue = true;
                    this.last = objApply;
                }
                this.downstream.onNext(obj);
            } catch (Throwable th) {
                fail(th);
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public Object poll() {
            while (true) {
                Object objPoll = this.qd.poll();
                if (objPoll == null) {
                    return null;
                }
                Object objApply = this.keySelector.apply(objPoll);
                if (!this.hasValue) {
                    this.hasValue = true;
                    this.last = objApply;
                    return objPoll;
                }
                if (!this.comparer.test(this.last, objApply)) {
                    this.last = objApply;
                    return objPoll;
                }
                this.last = objApply;
            }
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer observer) {
        this.source.subscribe(new DistinctUntilChangedObserver(observer, this.keySelector, this.comparer));
    }
}
