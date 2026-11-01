package com.airbnb.lottie.network;

import android.util.Pair;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieCompositionFactory;
import com.airbnb.lottie.LottieResult;
import com.airbnb.lottie.utils.Logger;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipInputStream;

/* JADX INFO: loaded from: classes.dex */
public class NetworkFetcher {
    public final LottieNetworkFetcher fetcher;
    public final NetworkCache networkCache;

    public NetworkFetcher(NetworkCache networkCache, LottieNetworkFetcher lottieNetworkFetcher) {
        this.networkCache = networkCache;
        this.fetcher = lottieNetworkFetcher;
    }

    public final LottieComposition fetchFromCache(String str, String str2) {
        Pair pairFetch;
        if (str2 == null || (pairFetch = this.networkCache.fetch(str)) == null) {
            return null;
        }
        FileExtension fileExtension = (FileExtension) pairFetch.first;
        InputStream inputStream = (InputStream) pairFetch.second;
        LottieResult lottieResultFromZipStreamSync = fileExtension == FileExtension.ZIP ? LottieCompositionFactory.fromZipStreamSync(new ZipInputStream(inputStream), str) : LottieCompositionFactory.fromJsonInputStreamSync(inputStream, str);
        if (lottieResultFromZipStreamSync.getValue() != null) {
            return (LottieComposition) lottieResultFromZipStreamSync.getValue();
        }
        return null;
    }

    public final LottieResult fetchFromNetwork(String str, String str2) {
        Logger.debug("Fetching " + str);
        Closeable closeable = null;
        try {
            try {
                LottieFetchResult lottieFetchResultFetchSync = this.fetcher.fetchSync(str);
                if (!lottieFetchResultFetchSync.isSuccessful()) {
                    LottieResult lottieResult = new LottieResult((Throwable) new IllegalArgumentException(lottieFetchResultFetchSync.error()));
                    try {
                        lottieFetchResultFetchSync.close();
                        return lottieResult;
                    } catch (IOException e) {
                        Logger.warning("LottieFetchResult close failed ", e);
                        return lottieResult;
                    }
                }
                LottieResult lottieResultFromInputStream = fromInputStream(str, lottieFetchResultFetchSync.bodyByteStream(), lottieFetchResultFetchSync.contentType(), str2);
                StringBuilder sb = new StringBuilder();
                sb.append("Completed fetch from network. Success: ");
                sb.append(lottieResultFromInputStream.getValue() != null);
                Logger.debug(sb.toString());
                try {
                    lottieFetchResultFetchSync.close();
                    return lottieResultFromInputStream;
                } catch (IOException e2) {
                    Logger.warning("LottieFetchResult close failed ", e2);
                    return lottieResultFromInputStream;
                }
            } catch (Exception e3) {
                LottieResult lottieResult2 = new LottieResult((Throwable) e3);
                if (0 != 0) {
                    try {
                        closeable.close();
                    } catch (IOException e4) {
                        Logger.warning("LottieFetchResult close failed ", e4);
                    }
                }
                return lottieResult2;
            }
        } catch (Throwable th) {
            if (0 != 0) {
                try {
                    closeable.close();
                } catch (IOException e5) {
                    Logger.warning("LottieFetchResult close failed ", e5);
                }
            }
            throw th;
        }
    }

    public LottieResult fetchSync(String str, String str2) {
        LottieComposition lottieCompositionFetchFromCache = fetchFromCache(str, str2);
        if (lottieCompositionFetchFromCache != null) {
            return new LottieResult(lottieCompositionFetchFromCache);
        }
        Logger.debug("Animation for " + str + " not found in cache. Fetching from network.");
        return fetchFromNetwork(str, str2);
    }

    public final LottieResult fromInputStream(String str, InputStream inputStream, String str2, String str3) {
        FileExtension fileExtension;
        LottieResult lottieResultFromZipStream;
        if (str2 == null) {
            str2 = "application/json";
        }
        if (str2.contains("application/zip") || str2.contains("application/x-zip") || str2.contains("application/x-zip-compressed") || str.split("\\?")[0].endsWith(".lottie")) {
            Logger.debug("Handling zip response.");
            fileExtension = FileExtension.ZIP;
            lottieResultFromZipStream = fromZipStream(str, inputStream, str3);
        } else {
            Logger.debug("Received json response.");
            fileExtension = FileExtension.JSON;
            lottieResultFromZipStream = fromJsonStream(str, inputStream, str3);
        }
        if (str3 != null && lottieResultFromZipStream.getValue() != null) {
            this.networkCache.renameTempFile(str, fileExtension);
        }
        return lottieResultFromZipStream;
    }

    public final LottieResult fromJsonStream(String str, InputStream inputStream, String str2) {
        return str2 == null ? LottieCompositionFactory.fromJsonInputStreamSync(inputStream, null) : LottieCompositionFactory.fromJsonInputStreamSync(new FileInputStream(this.networkCache.writeTempCacheFile(str, inputStream, FileExtension.JSON).getAbsolutePath()), str);
    }

    public final LottieResult fromZipStream(String str, InputStream inputStream, String str2) {
        return str2 == null ? LottieCompositionFactory.fromZipStreamSync(new ZipInputStream(inputStream), null) : LottieCompositionFactory.fromZipStreamSync(new ZipInputStream(new FileInputStream(this.networkCache.writeTempCacheFile(str, inputStream, FileExtension.ZIP))), str);
    }
}
