package com.bumptech.glide;

import android.content.Context;
import androidx.collection.ArrayMap;
import com.bumptech.glide.load.engine.Engine;
import com.bumptech.glide.load.engine.bitmap_recycle.ArrayPool;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPoolAdapter;
import com.bumptech.glide.load.engine.bitmap_recycle.LruArrayPool;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.load.engine.executor.GlideExecutor;
import com.bumptech.glide.manager.ConnectivityMonitorFactory;
import com.bumptech.glide.manager.DefaultConnectivityMonitorFactory;
import com.bumptech.glide.manager.RequestManagerRetriever;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class GlideBuilder {
    public GlideExecutor animationExecutor;
    public ArrayPool arrayPool;
    public BitmapPool bitmapPool;
    public ConnectivityMonitorFactory connectivityMonitorFactory;
    public List defaultRequestListeners;
    public GlideExecutor diskCacheExecutor;
    public DiskCache.Factory diskCacheFactory;
    public Engine engine;
    public boolean isActiveResourceRetentionAllowed;
    public MemoryCache memoryCache;
    public MemorySizeCalculator memorySizeCalculator;
    public RequestManagerRetriever.RequestManagerFactory requestManagerFactory;
    public GlideExecutor sourceExecutor;
    public final Map defaultTransitionOptions = new ArrayMap();
    public final GlideExperiments.Builder glideExperimentsBuilder = new GlideExperiments.Builder();
    public int logLevel = 4;
    public Glide.RequestOptionsFactory defaultRequestOptionsFactory = new Glide.RequestOptionsFactory() { // from class: com.bumptech.glide.GlideBuilder.1
        @Override // com.bumptech.glide.Glide.RequestOptionsFactory
        public RequestOptions build() {
            return new RequestOptions();
        }
    };

    /* JADX INFO: renamed from: com.bumptech.glide.GlideBuilder$2, reason: invalid class name */
    class AnonymousClass2 implements Glide.RequestOptionsFactory {
        public final /* synthetic */ RequestOptions val$requestOptions;

        @Override // com.bumptech.glide.Glide.RequestOptionsFactory
        public RequestOptions build() {
            RequestOptions requestOptions = this.val$requestOptions;
            return requestOptions != null ? requestOptions : new RequestOptions();
        }
    }

    public static final class EnableImageDecoderForBitmaps implements GlideExperiments.Experiment {
    }

    public static final class LogRequestOrigins implements GlideExperiments.Experiment {
    }

    public static final class ManualOverrideHardwareBitmapMaxFdCount implements GlideExperiments.Experiment {
    }

    public Glide build(Context context, List list, AppGlideModule appGlideModule) {
        if (this.sourceExecutor == null) {
            this.sourceExecutor = GlideExecutor.newSourceExecutor();
        }
        if (this.diskCacheExecutor == null) {
            this.diskCacheExecutor = GlideExecutor.newDiskCacheExecutor();
        }
        if (this.animationExecutor == null) {
            this.animationExecutor = GlideExecutor.newAnimationExecutor();
        }
        if (this.memorySizeCalculator == null) {
            this.memorySizeCalculator = new MemorySizeCalculator.Builder(context).build();
        }
        if (this.connectivityMonitorFactory == null) {
            this.connectivityMonitorFactory = new DefaultConnectivityMonitorFactory();
        }
        if (this.bitmapPool == null) {
            int bitmapPoolSize = this.memorySizeCalculator.getBitmapPoolSize();
            if (bitmapPoolSize > 0) {
                this.bitmapPool = new LruBitmapPool(bitmapPoolSize);
            } else {
                this.bitmapPool = new BitmapPoolAdapter();
            }
        }
        if (this.arrayPool == null) {
            this.arrayPool = new LruArrayPool(this.memorySizeCalculator.getArrayPoolSizeInBytes());
        }
        if (this.memoryCache == null) {
            this.memoryCache = new LruResourceCache(this.memorySizeCalculator.getMemoryCacheSize());
        }
        if (this.diskCacheFactory == null) {
            this.diskCacheFactory = new InternalCacheDiskCacheFactory(context);
        }
        if (this.engine == null) {
            this.engine = new Engine(this.memoryCache, this.diskCacheFactory, this.diskCacheExecutor, this.sourceExecutor, GlideExecutor.newUnlimitedSourceExecutor(), this.animationExecutor, this.isActiveResourceRetentionAllowed);
        }
        List list2 = this.defaultRequestListeners;
        if (list2 == null) {
            this.defaultRequestListeners = Collections.EMPTY_LIST;
        } else {
            this.defaultRequestListeners = Collections.unmodifiableList(list2);
        }
        return new Glide(context, this.engine, this.memoryCache, this.bitmapPool, this.arrayPool, new RequestManagerRetriever(this.requestManagerFactory), this.connectivityMonitorFactory, this.logLevel, this.defaultRequestOptionsFactory, this.defaultTransitionOptions, this.defaultRequestListeners, list, appGlideModule, this.glideExperimentsBuilder.build());
    }

    public void setRequestManagerFactory(RequestManagerRetriever.RequestManagerFactory requestManagerFactory) {
        this.requestManagerFactory = requestManagerFactory;
    }
}
