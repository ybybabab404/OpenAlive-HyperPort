package com.xiaomi.onetrack.util;

import android.text.TextUtils;
import android.util.LruCache;

/* JADX INFO: loaded from: classes2.dex */
class o extends LruCache<String, n.a> {
    public o(int i) {
        super(i);
    }

    @Override // android.util.LruCache
    /* JADX INFO: renamed from: a, reason: merged with bridge method [inline-methods] */
    public int sizeOf(String str, n.a aVar) {
        if (aVar == null || TextUtils.isEmpty(aVar.a)) {
            return 0;
        }
        return aVar.a.length();
    }
}
