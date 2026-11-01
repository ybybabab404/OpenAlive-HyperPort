package com.miui.clock.utils;

/* JADX INFO: loaded from: classes.dex */
public class Triple<F, S, T> {
    public final Object first;
    public final Object second;
    public final Object third;

    public boolean equals(Object obj) {
        if (!(obj instanceof Triple)) {
            return false;
        }
        Triple triple = (Triple) obj;
        return this.first.equals(triple.first) && this.second.equals(triple.second) && this.third.equals(triple.third);
    }

    public int hashCode() {
        Object obj = this.first;
        int iHashCode = obj == null ? 0 : obj.hashCode();
        Object obj2 = this.second;
        int iHashCode2 = iHashCode ^ (obj2 == null ? 0 : obj2.hashCode());
        Object obj3 = this.third;
        return iHashCode2 ^ (obj3 != null ? obj3.hashCode() : 0);
    }

    public String toString() {
        return "Triple{" + this.first + " " + this.second + " " + this.third + "}";
    }
}
