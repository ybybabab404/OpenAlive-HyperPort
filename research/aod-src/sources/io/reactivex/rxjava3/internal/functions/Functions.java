package io.reactivex.rxjava3.internal.functions;

import io.reactivex.rxjava3.core.Notification;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.exceptions.OnErrorNotImplementedException;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.BiConsumer;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.BooleanSupplier;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Function3;
import io.reactivex.rxjava3.functions.Function4;
import io.reactivex.rxjava3.functions.Function5;
import io.reactivex.rxjava3.functions.Function6;
import io.reactivex.rxjava3.functions.Function7;
import io.reactivex.rxjava3.functions.Function8;
import io.reactivex.rxjava3.functions.Function9;
import io.reactivex.rxjava3.functions.LongConsumer;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.functions.Supplier;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import io.reactivex.rxjava3.schedulers.Timed;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes2.dex */
public final class Functions {
    public static final Function IDENTITY = new Identity();
    public static final Runnable EMPTY_RUNNABLE = new EmptyRunnable();
    public static final Action EMPTY_ACTION = new EmptyAction();
    public static final Consumer EMPTY_CONSUMER = new EmptyConsumer();
    public static final Consumer ERROR_CONSUMER = new ErrorConsumer();
    public static final Consumer ON_ERROR_MISSING = new OnErrorMissingConsumer();
    public static final LongConsumer EMPTY_LONG_CONSUMER = new EmptyLongConsumer();
    public static final Predicate ALWAYS_TRUE = new TruePredicate();
    public static final Predicate ALWAYS_FALSE = new FalsePredicate();
    public static final Supplier NULL_SUPPLIER = new NullProvider();
    public static final Consumer REQUEST_MAX = new MaxRequestSubscription();

    public static final class ActionConsumer<T> implements Consumer<T> {
        public final Action action;

        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(Object obj) {
            this.action.run();
        }
    }

    public static final class Array2Func<T1, T2, R> implements Function<Object[], R> {
        public final BiFunction f;

        @Override // io.reactivex.rxjava3.functions.Function
        public Object apply(Object[] objArr) {
            if (objArr.length == 2) {
                return this.f.apply(objArr[0], objArr[1]);
            }
            throw new IllegalArgumentException("Array of size 2 expected but got " + objArr.length);
        }
    }

    public static final class Array3Func<T1, T2, T3, R> implements Function<Object[], R> {
        public final Function3 f;

        @Override // io.reactivex.rxjava3.functions.Function
        public Object apply(Object[] objArr) {
            if (objArr.length == 3) {
                return this.f.apply(objArr[0], objArr[1], objArr[2]);
            }
            throw new IllegalArgumentException("Array of size 3 expected but got " + objArr.length);
        }
    }

    public static final class Array4Func<T1, T2, T3, T4, R> implements Function<Object[], R> {
        public final Function4 f;

        @Override // io.reactivex.rxjava3.functions.Function
        public Object apply(Object[] objArr) {
            if (objArr.length == 4) {
                return this.f.apply(objArr[0], objArr[1], objArr[2], objArr[3]);
            }
            throw new IllegalArgumentException("Array of size 4 expected but got " + objArr.length);
        }
    }

    public static final class Array5Func<T1, T2, T3, T4, T5, R> implements Function<Object[], R> {
        public final Function5 f;

        @Override // io.reactivex.rxjava3.functions.Function
        public Object apply(Object[] objArr) {
            if (objArr.length == 5) {
                return this.f.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4]);
            }
            throw new IllegalArgumentException("Array of size 5 expected but got " + objArr.length);
        }
    }

    public static final class Array6Func<T1, T2, T3, T4, T5, T6, R> implements Function<Object[], R> {
        public final Function6 f;

        @Override // io.reactivex.rxjava3.functions.Function
        public Object apply(Object[] objArr) {
            if (objArr.length == 6) {
                return this.f.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5]);
            }
            throw new IllegalArgumentException("Array of size 6 expected but got " + objArr.length);
        }
    }

    public static final class Array7Func<T1, T2, T3, T4, T5, T6, T7, R> implements Function<Object[], R> {
        public final Function7 f;

        @Override // io.reactivex.rxjava3.functions.Function
        public Object apply(Object[] objArr) {
            if (objArr.length == 7) {
                return this.f.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6]);
            }
            throw new IllegalArgumentException("Array of size 7 expected but got " + objArr.length);
        }
    }

    public static final class Array8Func<T1, T2, T3, T4, T5, T6, T7, T8, R> implements Function<Object[], R> {
        public final Function8 f;

        @Override // io.reactivex.rxjava3.functions.Function
        public Object apply(Object[] objArr) {
            if (objArr.length == 8) {
                return this.f.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6], objArr[7]);
            }
            throw new IllegalArgumentException("Array of size 8 expected but got " + objArr.length);
        }
    }

    public static final class Array9Func<T1, T2, T3, T4, T5, T6, T7, T8, T9, R> implements Function<Object[], R> {
        public final Function9 f;

        @Override // io.reactivex.rxjava3.functions.Function
        public Object apply(Object[] objArr) {
            if (objArr.length == 9) {
                return this.f.apply(objArr[0], objArr[1], objArr[2], objArr[3], objArr[4], objArr[5], objArr[6], objArr[7], objArr[8]);
            }
            throw new IllegalArgumentException("Array of size 9 expected but got " + objArr.length);
        }
    }

    public static final class ArrayListCapacityCallable<T> implements Supplier<List<T>> {
        public final int capacity;

        @Override // io.reactivex.rxjava3.functions.Supplier
        public List get() {
            return new ArrayList(this.capacity);
        }
    }

    public static final class BooleanSupplierPredicateReverse<T> implements Predicate<T> {
        public final BooleanSupplier supplier;

        @Override // io.reactivex.rxjava3.functions.Predicate
        public boolean test(Object obj) {
            return !this.supplier.getAsBoolean();
        }
    }

    public static class BoundedConsumer implements Consumer<Subscription> {
        public final int bufferSize;

        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(Subscription subscription) {
            subscription.request(this.bufferSize);
        }
    }

    public static final class CastToClass<T, U> implements Function<T, U> {
        public final Class clazz;

        @Override // io.reactivex.rxjava3.functions.Function
        public Object apply(Object obj) {
            return this.clazz.cast(obj);
        }
    }

    public static final class ClassFilter<T, U> implements Predicate<T> {
        public final Class clazz;

        @Override // io.reactivex.rxjava3.functions.Predicate
        public boolean test(Object obj) {
            return this.clazz.isInstance(obj);
        }
    }

    public static final class EmptyAction implements Action {
        @Override // io.reactivex.rxjava3.functions.Action
        public void run() {
        }

        public String toString() {
            return "EmptyAction";
        }
    }

    public static final class EmptyConsumer implements Consumer<Object> {
        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(Object obj) {
        }

        public String toString() {
            return "EmptyConsumer";
        }
    }

    public static final class EmptyLongConsumer implements LongConsumer {
        @Override // io.reactivex.rxjava3.functions.LongConsumer
        public void accept(long j) {
        }
    }

    public static final class EmptyRunnable implements Runnable {
        @Override // java.lang.Runnable
        public void run() {
        }

        public String toString() {
            return "EmptyRunnable";
        }
    }

    public static final class EqualsPredicate<T> implements Predicate<T> {
        public final Object value;

        @Override // io.reactivex.rxjava3.functions.Predicate
        public boolean test(Object obj) {
            return Objects.equals(obj, this.value);
        }
    }

    public static final class ErrorConsumer implements Consumer<Throwable> {
        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(Throwable th) {
            RxJavaPlugins.onError(th);
        }
    }

    public static final class FalsePredicate implements Predicate<Object> {
        @Override // io.reactivex.rxjava3.functions.Predicate
        public boolean test(Object obj) {
            return false;
        }
    }

    public static final class FutureAction implements Action {
        public final Future future;

        @Override // io.reactivex.rxjava3.functions.Action
        public void run() throws ExecutionException, InterruptedException {
            this.future.get();
        }
    }

    public enum HashSetSupplier implements Supplier<Set<Object>> {
        INSTANCE;

        @Override // io.reactivex.rxjava3.functions.Supplier
        public Set get() {
            return new HashSet();
        }
    }

    public static final class Identity implements Function<Object, Object> {
        @Override // io.reactivex.rxjava3.functions.Function
        public Object apply(Object obj) {
            return obj;
        }

        public String toString() {
            return "IdentityFunction";
        }
    }

    public static final class JustValue<T, U> implements Callable<U>, Supplier<U>, Function<T, U> {
        public final Object value;

        public JustValue(Object obj) {
            this.value = obj;
        }

        @Override // io.reactivex.rxjava3.functions.Function
        public Object apply(Object obj) {
            return this.value;
        }

        @Override // java.util.concurrent.Callable
        public Object call() {
            return this.value;
        }

        @Override // io.reactivex.rxjava3.functions.Supplier
        public Object get() {
            return this.value;
        }
    }

    public static final class ListSorter<T> implements Function<List<T>, List<T>> {
        public final Comparator comparator;

        @Override // io.reactivex.rxjava3.functions.Function
        public List apply(List list) {
            Collections.sort(list, this.comparator);
            return list;
        }
    }

    public static final class MaxRequestSubscription implements Consumer<Subscription> {
        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(Subscription subscription) {
            subscription.request(Long.MAX_VALUE);
        }
    }

    public enum NaturalComparator implements Comparator<Object> {
        INSTANCE;

        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            return ((Comparable) obj).compareTo(obj2);
        }
    }

    public static final class NotificationOnComplete<T> implements Action {
        public final Consumer onNotification;

        @Override // io.reactivex.rxjava3.functions.Action
        public void run() {
            this.onNotification.accept(Notification.createOnComplete());
        }
    }

    public static final class NotificationOnError<T> implements Consumer<Throwable> {
        public final Consumer onNotification;

        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(Throwable th) {
            this.onNotification.accept(Notification.createOnError(th));
        }
    }

    public static final class NotificationOnNext<T> implements Consumer<T> {
        public final Consumer onNotification;

        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(Object obj) {
            this.onNotification.accept(Notification.createOnNext(obj));
        }
    }

    public static final class NullProvider implements Supplier<Object> {
        @Override // io.reactivex.rxjava3.functions.Supplier
        public Object get() {
            return null;
        }
    }

    public static final class OnErrorMissingConsumer implements Consumer<Throwable> {
        @Override // io.reactivex.rxjava3.functions.Consumer
        public void accept(Throwable th) {
            RxJavaPlugins.onError(new OnErrorNotImplementedException(th));
        }
    }

    public static final class TimestampFunction<T> implements Function<T, Timed<T>> {
        public final Scheduler scheduler;
        public final TimeUnit unit;

        @Override // io.reactivex.rxjava3.functions.Function
        public Timed apply(Object obj) {
            return new Timed(obj, this.scheduler.now(this.unit), this.unit);
        }
    }

    public static final class ToMapKeySelector<K, T> implements BiConsumer<Map<K, T>, T> {
        public final Function keySelector;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.functions.BiConsumer
        public void accept(Map map, Object obj) {
            map.put(this.keySelector.apply(obj), obj);
        }
    }

    public static final class ToMapKeyValueSelector<K, V, T> implements BiConsumer<Map<K, V>, T> {
        public final Function keySelector;
        public final Function valueSelector;

        /* JADX WARN: Multi-variable type inference failed */
        @Override // io.reactivex.rxjava3.functions.BiConsumer
        public void accept(Map map, Object obj) {
            map.put(this.keySelector.apply(obj), this.valueSelector.apply(obj));
        }
    }

    public static final class ToMultimapKeyValueSelector<K, V, T> implements BiConsumer<Map<K, Collection<V>>, T> {
        public final Function collectionFactory;
        public final Function keySelector;
        public final Function valueSelector;

        @Override // io.reactivex.rxjava3.functions.BiConsumer
        public void accept(Map map, Object obj) {
            Object objApply = this.keySelector.apply(obj);
            Collection collection = (Collection) map.get(objApply);
            if (collection == null) {
                collection = (Collection) this.collectionFactory.apply(objApply);
                map.put(objApply, collection);
            }
            collection.add(this.valueSelector.apply(obj));
        }
    }

    public static final class TruePredicate implements Predicate<Object> {
        @Override // io.reactivex.rxjava3.functions.Predicate
        public boolean test(Object obj) {
            return true;
        }
    }

    public static Consumer emptyConsumer() {
        return EMPTY_CONSUMER;
    }

    public static Function justFunction(Object obj) {
        return new JustValue(obj);
    }
}
