package com.airbnb.lottie.model;

import androidx.annotation.RestrictTo;
import androidx.collection.LruCache;
import com.airbnb.lottie.LottieComposition;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public class LottieCompositionCache {
    public static final LottieCompositionCache INSTANCE = new LottieCompositionCache();
    public final LruCache cache = new LruCache(20);

    public static LottieCompositionCache getInstance() {
        return INSTANCE;
    }

    public LottieComposition get(String str) {
        if (str == null) {
            return null;
        }
        return (LottieComposition) this.cache.get(str);
    }

    public void put(String str, LottieComposition lottieComposition) {
        if (str == null) {
            return;
        }
        this.cache.put(str, lottieComposition);
    }
}
