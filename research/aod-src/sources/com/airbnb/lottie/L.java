package com.airbnb.lottie;

import android.content.Context;
import androidx.annotation.RestrictTo;
import androidx.core.os.TraceCompat;
import com.airbnb.lottie.network.DefaultLottieNetworkFetcher;
import com.airbnb.lottie.network.LottieNetworkCacheProvider;
import com.airbnb.lottie.network.LottieNetworkFetcher;
import com.airbnb.lottie.network.NetworkCache;
import com.airbnb.lottie.network.NetworkFetcher;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
@RestrictTo
public class L {
    public static boolean DBG = false;
    public static LottieNetworkCacheProvider cacheProvider = null;
    public static int depthPastMaxDepth = 0;
    public static LottieNetworkFetcher fetcher = null;
    public static volatile NetworkCache networkCache = null;
    public static volatile NetworkFetcher networkFetcher = null;
    public static String[] sections = null;
    public static long[] startTimeNs = null;
    public static int traceDepth = 0;
    public static boolean traceEnabled = false;

    public static void beginSection(String str) {
        if (traceEnabled) {
            int i = traceDepth;
            if (i == 20) {
                depthPastMaxDepth++;
                return;
            }
            sections[i] = str;
            startTimeNs[i] = System.nanoTime();
            TraceCompat.beginSection(str);
            traceDepth++;
        }
    }

    public static float endSection(String str) {
        int i = depthPastMaxDepth;
        if (i > 0) {
            depthPastMaxDepth = i - 1;
            return 0.0f;
        }
        if (!traceEnabled) {
            return 0.0f;
        }
        int i2 = traceDepth - 1;
        traceDepth = i2;
        if (i2 == -1) {
            throw new IllegalStateException("Can't end trace section. There are none.");
        }
        if (str.equals(sections[i2])) {
            TraceCompat.endSection();
            return (System.nanoTime() - startTimeNs[traceDepth]) / 1000000.0f;
        }
        throw new IllegalStateException("Unbalanced trace call " + str + ". Expected " + sections[traceDepth] + ".");
    }

    public static NetworkCache networkCache(Context context) {
        NetworkCache networkCache2;
        final Context applicationContext = context.getApplicationContext();
        NetworkCache networkCache3 = networkCache;
        if (networkCache3 != null) {
            return networkCache3;
        }
        synchronized (NetworkCache.class) {
            try {
                networkCache2 = networkCache;
                if (networkCache2 == null) {
                    LottieNetworkCacheProvider lottieNetworkCacheProvider = cacheProvider;
                    if (lottieNetworkCacheProvider == null) {
                        lottieNetworkCacheProvider = new LottieNetworkCacheProvider() { // from class: com.airbnb.lottie.L.1
                            @Override // com.airbnb.lottie.network.LottieNetworkCacheProvider
                            public File getCacheDir() {
                                return new File(applicationContext.getCacheDir(), "lottie_network_cache");
                            }
                        };
                    }
                    networkCache2 = new NetworkCache(lottieNetworkCacheProvider);
                    networkCache = networkCache2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return networkCache2;
    }

    public static NetworkFetcher networkFetcher(Context context) {
        NetworkFetcher networkFetcher2;
        NetworkFetcher networkFetcher3 = networkFetcher;
        if (networkFetcher3 != null) {
            return networkFetcher3;
        }
        synchronized (NetworkFetcher.class) {
            try {
                networkFetcher2 = networkFetcher;
                if (networkFetcher2 == null) {
                    NetworkCache networkCache2 = networkCache(context);
                    LottieNetworkFetcher defaultLottieNetworkFetcher = fetcher;
                    if (defaultLottieNetworkFetcher == null) {
                        defaultLottieNetworkFetcher = new DefaultLottieNetworkFetcher();
                    }
                    networkFetcher2 = new NetworkFetcher(networkCache2, defaultLottieNetworkFetcher);
                    networkFetcher = networkFetcher2;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return networkFetcher2;
    }
}
