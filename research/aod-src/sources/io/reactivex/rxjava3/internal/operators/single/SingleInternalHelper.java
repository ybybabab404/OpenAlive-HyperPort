package io.reactivex.rxjava3.internal.operators.single;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.SingleSource;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Supplier;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.reactivestreams.Publisher;

/* JADX INFO: loaded from: classes2.dex */
public final class SingleInternalHelper {

    public enum NoSuchElementSupplier implements Supplier<NoSuchElementException> {
        INSTANCE;

        @Override // io.reactivex.rxjava3.functions.Supplier
        public NoSuchElementException get() {
            return new NoSuchElementException();
        }
    }

    public enum ToFlowable implements Function<SingleSource, Publisher> {
        INSTANCE;

        @Override // io.reactivex.rxjava3.functions.Function
        public Publisher apply(SingleSource singleSource) {
            return new SingleToFlowable(singleSource);
        }
    }

    public static final class ToFlowableIterable<T> implements Iterable<Flowable<T>> {
        public final Iterable sources;

        @Override // java.lang.Iterable
        public Iterator iterator() {
            return new ToFlowableIterator(this.sources.iterator());
        }
    }

    public static final class ToFlowableIterator<T> implements Iterator<Flowable<T>> {
        public final Iterator sit;

        public ToFlowableIterator(Iterator it) {
            this.sit = it;
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.sit.hasNext();
        }

        @Override // java.util.Iterator
        public Flowable next() {
            return new SingleToFlowable((SingleSource) this.sit.next());
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
