package com.airbnb.lottie;

import com.airbnb.lottie.network.LottieNetworkCacheProvider;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public class LottieConfig {

    public static final class Builder {

        /* JADX INFO: renamed from: com.airbnb.lottie.LottieConfig$Builder$1, reason: invalid class name */
        class AnonymousClass1 implements LottieNetworkCacheProvider {
            public final /* synthetic */ File val$file;

            @Override // com.airbnb.lottie.network.LottieNetworkCacheProvider
            public File getCacheDir() {
                if (this.val$file.isDirectory()) {
                    return this.val$file;
                }
                throw new IllegalArgumentException("cache file must be a directory");
            }
        }

        /* JADX INFO: renamed from: com.airbnb.lottie.LottieConfig$Builder$2, reason: invalid class name */
        class AnonymousClass2 implements LottieNetworkCacheProvider {
            public final /* synthetic */ LottieNetworkCacheProvider val$fileCacheProvider;

            @Override // com.airbnb.lottie.network.LottieNetworkCacheProvider
            public File getCacheDir() {
                File cacheDir = this.val$fileCacheProvider.getCacheDir();
                if (cacheDir.isDirectory()) {
                    return cacheDir;
                }
                throw new IllegalArgumentException("cache file must be a directory");
            }
        }
    }
}
