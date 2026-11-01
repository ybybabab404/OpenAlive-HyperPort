package io.reactivex.rxjava3.internal.util;

/* JADX INFO: loaded from: classes2.dex */
public final class Pow2 {
    public static int roundToPowerOfTwo(int i) {
        return 1 << (32 - Integer.numberOfLeadingZeros(i - 1));
    }
}
