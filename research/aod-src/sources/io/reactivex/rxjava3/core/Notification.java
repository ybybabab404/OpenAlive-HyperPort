package io.reactivex.rxjava3.core;

import io.reactivex.rxjava3.internal.util.NotificationLite;
import java.util.Objects;

/* JADX INFO: loaded from: classes2.dex */
public final class Notification<T> {
    public static final Notification COMPLETE = new Notification(null);
    public final Object value;

    public Notification(Object obj) {
        this.value = obj;
    }

    public static Notification createOnComplete() {
        return COMPLETE;
    }

    public static Notification createOnError(Throwable th) {
        Objects.requireNonNull(th, "error is null");
        return new Notification(NotificationLite.error(th));
    }

    public static Notification createOnNext(Object obj) {
        Objects.requireNonNull(obj, "value is null");
        return new Notification(obj);
    }

    public boolean equals(Object obj) {
        if (obj instanceof Notification) {
            return Objects.equals(this.value, ((Notification) obj).value);
        }
        return false;
    }

    public Throwable getError() {
        Object obj = this.value;
        if (NotificationLite.isError(obj)) {
            return NotificationLite.getError(obj);
        }
        return null;
    }

    public Object getValue() {
        Object obj = this.value;
        if (obj == null || NotificationLite.isError(obj)) {
            return null;
        }
        return this.value;
    }

    public int hashCode() {
        Object obj = this.value;
        if (obj != null) {
            return obj.hashCode();
        }
        return 0;
    }

    public boolean isOnComplete() {
        return this.value == null;
    }

    public boolean isOnError() {
        return NotificationLite.isError(this.value);
    }

    public boolean isOnNext() {
        Object obj = this.value;
        return (obj == null || NotificationLite.isError(obj)) ? false : true;
    }

    public String toString() {
        Object obj = this.value;
        if (obj == null) {
            return "OnCompleteNotification";
        }
        if (NotificationLite.isError(obj)) {
            return "OnErrorNotification[" + NotificationLite.getError(obj) + "]";
        }
        return "OnNextNotification[" + this.value + "]";
    }
}
