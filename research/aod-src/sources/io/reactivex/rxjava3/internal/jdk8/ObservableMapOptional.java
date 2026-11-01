package io.reactivex.rxjava3.internal.jdk8;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.internal.observers.BasicFuseableObserver;
import java.util.Objects;
import java.util.Optional;

/* JADX INFO: loaded from: classes2.dex */
public final class ObservableMapOptional<T, R> extends Observable<R> {
    public final Function mapper;
    public final Observable source;

    public static final class MapOptionalObserver<T, R> extends BasicFuseableObserver<T, R> {
        public final Function mapper;

        public MapOptionalObserver(Observer observer, Function function) {
            super(observer);
            this.mapper = function;
        }

        @Override // io.reactivex.rxjava3.core.Observer
        public void onNext(Object obj) {
            if (this.done) {
                return;
            }
            if (this.sourceMode != 0) {
                this.downstream.onNext(null);
                return;
            }
            try {
                Object objApply = this.mapper.apply(obj);
                Objects.requireNonNull(objApply, "The mapper returned a null Optional");
                Optional optional = (Optional) objApply;
                if (optional.isPresent()) {
                    this.downstream.onNext(optional.get());
                }
            } catch (Throwable th) {
                fail(th);
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public Object poll() {
            Optional optional;
            do {
                Object objPoll = this.qd.poll();
                if (objPoll == null) {
                    return null;
                }
                Object objApply = this.mapper.apply(objPoll);
                Objects.requireNonNull(objApply, "The mapper returned a null Optional");
                optional = (Optional) objApply;
            } while (!optional.isPresent());
            return optional.get();
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }
    }

    @Override // io.reactivex.rxjava3.core.Observable
    public void subscribeActual(Observer observer) {
        this.source.subscribe(new MapOptionalObserver(observer, this.mapper));
    }
}
