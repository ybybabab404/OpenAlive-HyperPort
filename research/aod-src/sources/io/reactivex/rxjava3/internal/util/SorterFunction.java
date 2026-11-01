package io.reactivex.rxjava3.internal.util;

import io.reactivex.rxjava3.functions.Function;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/* JADX INFO: loaded from: classes2.dex */
public final class SorterFunction<T> implements Function<List<T>, List<T>> {
    public final Comparator comparator;

    @Override // io.reactivex.rxjava3.functions.Function
    public List apply(List list) {
        Collections.sort(list, this.comparator);
        return list;
    }
}
