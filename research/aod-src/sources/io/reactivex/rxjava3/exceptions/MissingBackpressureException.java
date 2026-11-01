package io.reactivex.rxjava3.exceptions;

/* JADX INFO: loaded from: classes2.dex */
public final class MissingBackpressureException extends RuntimeException {
    public MissingBackpressureException(String str) {
        super(str);
    }

    public static MissingBackpressureException createDefault() {
        return new MissingBackpressureException("Could not emit value due to lack of requests");
    }
}
