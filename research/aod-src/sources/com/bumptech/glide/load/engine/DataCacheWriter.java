package com.bumptech.glide.load.engine;

import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.engine.cache.DiskCache;
import java.io.File;

/* JADX INFO: loaded from: classes.dex */
class DataCacheWriter<DataType> implements DiskCache.Writer {
    public final Object data;
    public final Encoder encoder;
    public final Options options;

    public DataCacheWriter(Encoder encoder, Object obj, Options options) {
        this.encoder = encoder;
        this.data = obj;
        this.options = options;
    }

    @Override // com.bumptech.glide.load.engine.cache.DiskCache.Writer
    public boolean write(File file) {
        return this.encoder.encode(this.data, file, this.options);
    }
}
