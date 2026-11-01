package com.bumptech.glide.load;

import com.bumptech.glide.util.Preconditions;
import java.security.MessageDigest;

/* JADX INFO: loaded from: classes.dex */
public final class Option<T> {
    public static final CacheKeyUpdater EMPTY_UPDATER = new CacheKeyUpdater<Object>() { // from class: com.bumptech.glide.load.Option.1
        @Override // com.bumptech.glide.load.Option.CacheKeyUpdater
        public void update(byte[] bArr, Object obj, MessageDigest messageDigest) {
        }
    };
    public final CacheKeyUpdater cacheKeyUpdater;
    public final Object defaultValue;
    public final String key;
    public volatile byte[] keyBytes;

    public interface CacheKeyUpdater<T> {
        void update(byte[] bArr, Object obj, MessageDigest messageDigest);
    }

    public Option(String str, Object obj, CacheKeyUpdater cacheKeyUpdater) {
        this.key = Preconditions.checkNotEmpty(str);
        this.defaultValue = obj;
        this.cacheKeyUpdater = (CacheKeyUpdater) Preconditions.checkNotNull(cacheKeyUpdater);
    }

    public static Option disk(String str, Object obj, CacheKeyUpdater cacheKeyUpdater) {
        return new Option(str, obj, cacheKeyUpdater);
    }

    public static CacheKeyUpdater emptyUpdater() {
        return EMPTY_UPDATER;
    }

    public static Option memory(String str) {
        return new Option(str, null, emptyUpdater());
    }

    public static Option memory(String str, Object obj) {
        return new Option(str, obj, emptyUpdater());
    }

    public boolean equals(Object obj) {
        if (obj instanceof Option) {
            return this.key.equals(((Option) obj).key);
        }
        return false;
    }

    public Object getDefaultValue() {
        return this.defaultValue;
    }

    public final byte[] getKeyBytes() {
        if (this.keyBytes == null) {
            this.keyBytes = this.key.getBytes(Key.CHARSET);
        }
        return this.keyBytes;
    }

    public int hashCode() {
        return this.key.hashCode();
    }

    public String toString() {
        return "Option{key='" + this.key + "'}";
    }

    public void update(Object obj, MessageDigest messageDigest) {
        this.cacheKeyUpdater.update(getKeyBytes(), obj, messageDigest);
    }
}
