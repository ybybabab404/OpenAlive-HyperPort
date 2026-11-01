package io.reactivex.rxjava3.exceptions;

/* JADX INFO: loaded from: classes2.dex */
public final class QueueOverflowException extends RuntimeException {
    public QueueOverflowException() {
        this("Queue overflow due to illegal concurrent onNext calls or a bug in an operator");
    }

    public QueueOverflowException(String str) {
        super(str);
    }
}
