package io.reactivex.rxjava3.schedulers;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes2.dex */
public final class Timed<T> {
    public final long time;
    public final TimeUnit unit;
    public final Object value;

    public Timed(Object obj, long j, TimeUnit timeUnit) {
        Objects.requireNonNull(obj, "value is null");
        this.value = obj;
        this.time = j;
        Objects.requireNonNull(timeUnit, "unit is null");
        this.unit = timeUnit;
    }

    public boolean equals(Object obj) {
        if (obj instanceof Timed) {
            Timed timed = (Timed) obj;
            if (Objects.equals(this.value, timed.value) && this.time == timed.time && Objects.equals(this.unit, timed.unit)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        int iHashCode = this.value.hashCode() * 31;
        long j = this.time;
        return ((iHashCode + ((int) (j ^ (j >>> 31)))) * 31) + this.unit.hashCode();
    }

    public long time() {
        return this.time;
    }

    public String toString() {
        return "Timed[time=" + this.time + ", unit=" + this.unit + ", value=" + this.value + "]";
    }

    public Object value() {
        return this.value;
    }
}
