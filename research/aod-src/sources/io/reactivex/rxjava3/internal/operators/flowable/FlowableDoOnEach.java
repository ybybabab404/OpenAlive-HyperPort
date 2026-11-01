package io.reactivex.rxjava3.internal.operators.flowable;

import io.reactivex.rxjava3.core.FlowableSubscriber;
import io.reactivex.rxjava3.exceptions.CompositeException;
import io.reactivex.rxjava3.exceptions.Exceptions;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.internal.subscribers.BasicFuseableConditionalSubscriber;
import io.reactivex.rxjava3.internal.subscribers.BasicFuseableSubscriber;
import io.reactivex.rxjava3.internal.util.ExceptionHelper;
import io.reactivex.rxjava3.operators.ConditionalSubscriber;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes2.dex */
public final class FlowableDoOnEach<T> extends AbstractFlowableWithUpstream<T, T> {
    public final Action onAfterTerminate;
    public final Action onComplete;
    public final Consumer onError;
    public final Consumer onNext;

    public static final class DoOnEachConditionalSubscriber<T> extends BasicFuseableConditionalSubscriber<T, T> {
        public final Action onAfterTerminate;
        public final Action onComplete;
        public final Consumer onError;
        public final Consumer onNext;

        public DoOnEachConditionalSubscriber(ConditionalSubscriber conditionalSubscriber, Consumer consumer, Consumer consumer2, Action action, Action action2) {
            super(conditionalSubscriber);
            this.onNext = consumer;
            this.onError = consumer2;
            this.onComplete = action;
            this.onAfterTerminate = action2;
        }

        @Override // io.reactivex.rxjava3.internal.subscribers.BasicFuseableConditionalSubscriber, org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            try {
                this.onComplete.run();
                this.done = true;
                this.downstream.onComplete();
                try {
                    this.onAfterTerminate.run();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            } catch (Throwable th2) {
                fail(th2);
            }
        }

        @Override // io.reactivex.rxjava3.internal.subscribers.BasicFuseableConditionalSubscriber, org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            try {
                this.onError.accept(th);
                this.downstream.onError(th);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.downstream.onError(new CompositeException(th, th2));
            }
            try {
                this.onAfterTerminate.run();
            } catch (Throwable th3) {
                Exceptions.throwIfFatal(th3);
                RxJavaPlugins.onError(th3);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            if (this.done) {
                return;
            }
            if (this.sourceMode != 0) {
                this.downstream.onNext(null);
                return;
            }
            try {
                this.onNext.accept(obj);
                this.downstream.onNext(obj);
            } catch (Throwable th) {
                fail(th);
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public Object poll() throws Exception {
            try {
                Object objPoll = this.qs.poll();
                if (objPoll == null) {
                    if (this.sourceMode == 1) {
                        this.onComplete.run();
                        this.onAfterTerminate.run();
                    }
                    return objPoll;
                }
                try {
                    this.onNext.accept(objPoll);
                    this.onAfterTerminate.run();
                    return objPoll;
                } catch (Throwable th) {
                    try {
                        Exceptions.throwIfFatal(th);
                        try {
                            this.onError.accept(th);
                            throw ExceptionHelper.throwIfThrowable(th);
                        } catch (Throwable th2) {
                            Exceptions.throwIfFatal(th2);
                            throw new CompositeException(th, th2);
                        }
                    } catch (Throwable th3) {
                        this.onAfterTerminate.run();
                        throw th3;
                    }
                }
            } catch (Throwable th4) {
                Exceptions.throwIfFatal(th4);
                try {
                    this.onError.accept(th4);
                    throw ExceptionHelper.throwIfThrowable(th4);
                } catch (Throwable th5) {
                    Exceptions.throwIfFatal(th5);
                    throw new CompositeException(th4, th5);
                }
            }
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }

        @Override // io.reactivex.rxjava3.operators.ConditionalSubscriber
        public boolean tryOnNext(Object obj) {
            if (this.done) {
                return false;
            }
            try {
                this.onNext.accept(obj);
                return this.downstream.tryOnNext(obj);
            } catch (Throwable th) {
                fail(th);
                return false;
            }
        }
    }

    public static final class DoOnEachSubscriber<T> extends BasicFuseableSubscriber<T, T> {
        public final Action onAfterTerminate;
        public final Action onComplete;
        public final Consumer onError;
        public final Consumer onNext;

        public DoOnEachSubscriber(Subscriber subscriber, Consumer consumer, Consumer consumer2, Action action, Action action2) {
            super(subscriber);
            this.onNext = consumer;
            this.onError = consumer2;
            this.onComplete = action;
            this.onAfterTerminate = action2;
        }

        @Override // io.reactivex.rxjava3.internal.subscribers.BasicFuseableSubscriber, org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.done) {
                return;
            }
            try {
                this.onComplete.run();
                this.done = true;
                this.downstream.onComplete();
                try {
                    this.onAfterTerminate.run();
                } catch (Throwable th) {
                    Exceptions.throwIfFatal(th);
                    RxJavaPlugins.onError(th);
                }
            } catch (Throwable th2) {
                fail(th2);
            }
        }

        @Override // io.reactivex.rxjava3.internal.subscribers.BasicFuseableSubscriber, org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.done) {
                RxJavaPlugins.onError(th);
                return;
            }
            this.done = true;
            try {
                this.onError.accept(th);
                this.downstream.onError(th);
            } catch (Throwable th2) {
                Exceptions.throwIfFatal(th2);
                this.downstream.onError(new CompositeException(th, th2));
            }
            try {
                this.onAfterTerminate.run();
            } catch (Throwable th3) {
                Exceptions.throwIfFatal(th3);
                RxJavaPlugins.onError(th3);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Object obj) {
            if (this.done) {
                return;
            }
            if (this.sourceMode != 0) {
                this.downstream.onNext(null);
                return;
            }
            try {
                this.onNext.accept(obj);
                this.downstream.onNext(obj);
            } catch (Throwable th) {
                fail(th);
            }
        }

        @Override // io.reactivex.rxjava3.operators.SimpleQueue
        public Object poll() throws Exception {
            try {
                Object objPoll = this.qs.poll();
                if (objPoll == null) {
                    if (this.sourceMode == 1) {
                        this.onComplete.run();
                        this.onAfterTerminate.run();
                    }
                    return objPoll;
                }
                try {
                    this.onNext.accept(objPoll);
                    this.onAfterTerminate.run();
                    return objPoll;
                } catch (Throwable th) {
                    try {
                        Exceptions.throwIfFatal(th);
                        try {
                            this.onError.accept(th);
                            throw ExceptionHelper.throwIfThrowable(th);
                        } catch (Throwable th2) {
                            Exceptions.throwIfFatal(th2);
                            throw new CompositeException(th, th2);
                        }
                    } catch (Throwable th3) {
                        this.onAfterTerminate.run();
                        throw th3;
                    }
                }
            } catch (Throwable th4) {
                Exceptions.throwIfFatal(th4);
                try {
                    this.onError.accept(th4);
                    throw ExceptionHelper.throwIfThrowable(th4);
                } catch (Throwable th5) {
                    Exceptions.throwIfFatal(th5);
                    throw new CompositeException(th4, th5);
                }
            }
        }

        @Override // io.reactivex.rxjava3.operators.QueueFuseable
        public int requestFusion(int i) {
            return transitiveBoundaryFusion(i);
        }
    }

    @Override // io.reactivex.rxjava3.core.Flowable
    public void subscribeActual(Subscriber subscriber) {
        if (subscriber instanceof ConditionalSubscriber) {
            this.source.subscribe((FlowableSubscriber) new DoOnEachConditionalSubscriber((ConditionalSubscriber) subscriber, this.onNext, this.onError, this.onComplete, this.onAfterTerminate));
        } else {
            this.source.subscribe((FlowableSubscriber) new DoOnEachSubscriber(subscriber, this.onNext, this.onError, this.onComplete, this.onAfterTerminate));
        }
    }
}
