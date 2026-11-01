package io.reactivex.rxjava3.internal.util;

import io.reactivex.rxjava3.functions.Supplier;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes2.dex */
public enum HashMapSupplier implements Supplier<Map<Object, Object>> {
    INSTANCE;

    @Override // io.reactivex.rxjava3.functions.Supplier
    public Map get() {
        return new HashMap();
    }
}
