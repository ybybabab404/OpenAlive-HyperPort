package com.bumptech.glide.load.engine.cache;

import android.content.Context;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
public final class ExternalPreferredCacheDiskCacheFactory extends DiskLruCacheFactory {

    /* JADX INFO: renamed from: com.bumptech.glide.load.engine.cache.ExternalPreferredCacheDiskCacheFactory$1, reason: invalid class name */
    class AnonymousClass1 implements DiskLruCacheFactory.CacheDirectoryGetter {
        public final /* synthetic */ Context val$context;
        public final /* synthetic */ String val$diskCacheName;

        @Override // com.bumptech.glide.load.engine.cache.DiskLruCacheFactory.CacheDirectoryGetter
        public File getCacheDirectory() {
            File externalCacheDir;
            File internalCacheDirectory = getInternalCacheDirectory();
            if ((internalCacheDirectory == null || !internalCacheDirectory.exists()) && (externalCacheDir = this.val$context.getExternalCacheDir()) != null && externalCacheDir.canWrite()) {
                return this.val$diskCacheName != null ? new File(externalCacheDir, this.val$diskCacheName) : externalCacheDir;
            }
            return internalCacheDirectory;
        }

        public final File getInternalCacheDirectory() {
            File cacheDir = this.val$context.getCacheDir();
            if (cacheDir == null) {
                return null;
            }
            return this.val$diskCacheName != null ? new File(cacheDir, this.val$diskCacheName) : cacheDir;
        }
    }
}
