package com.bumptech.glide.load.engine.cache;

import android.content.Context;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public final class ExternalCacheDiskCacheFactory extends DiskLruCacheFactory {

    /* JADX INFO: renamed from: com.bumptech.glide.load.engine.cache.ExternalCacheDiskCacheFactory$1, reason: invalid class name */
    class AnonymousClass1 implements DiskLruCacheFactory.CacheDirectoryGetter {
        public final /* synthetic */ Context val$context;
        public final /* synthetic */ String val$diskCacheName;

        @Override // com.bumptech.glide.load.engine.cache.DiskLruCacheFactory.CacheDirectoryGetter
        public File getCacheDirectory() {
            File externalCacheDir = this.val$context.getExternalCacheDir();
            if (externalCacheDir == null) {
                return null;
            }
            return this.val$diskCacheName != null ? new File(externalCacheDir, this.val$diskCacheName) : externalCacheDir;
        }
    }
}
