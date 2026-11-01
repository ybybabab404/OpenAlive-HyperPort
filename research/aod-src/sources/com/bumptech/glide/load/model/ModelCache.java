package com.bumptech.glide.load.model;

import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.util.LruCache;
import com.bumptech.glide.util.Util;
import java.util.Queue;

/* JADX INFO: loaded from: classes.dex */
public class ModelCache<A, B> {
    public final LruCache cache;

    @VisibleForTesting
    public static final class ModelKey<A> {
        public static final Queue KEY_QUEUE = Util.createQueue(0);
        public int height;
        public Object model;
        public int width;

        public static ModelKey get(Object obj, int i, int i2) {
            ModelKey modelKey;
            Queue queue = KEY_QUEUE;
            synchronized (queue) {
                modelKey = (ModelKey) queue.poll();
            }
            if (modelKey == null) {
                modelKey = new ModelKey();
            }
            modelKey.init(obj, i, i2);
            return modelKey;
        }

        public boolean equals(Object obj) {
            if (obj instanceof ModelKey) {
                ModelKey modelKey = (ModelKey) obj;
                if (this.width == modelKey.width && this.height == modelKey.height && this.model.equals(modelKey.model)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return (((this.height * 31) + this.width) * 31) + this.model.hashCode();
        }

        public final void init(Object obj, int i, int i2) {
            this.model = obj;
            this.width = i;
            this.height = i2;
        }

        public void release() {
            Queue queue = KEY_QUEUE;
            synchronized (queue) {
                queue.offer(this);
            }
        }
    }

    public ModelCache(long j) {
        this.cache = new LruCache<ModelKey<A>, B>(j) { // from class: com.bumptech.glide.load.model.ModelCache.1
            @Override // com.bumptech.glide.util.LruCache
            public void onItemEvicted(ModelKey modelKey, Object obj) {
                modelKey.release();
            }
        };
    }

    public Object get(Object obj, int i, int i2) {
        ModelKey modelKey = ModelKey.get(obj, i, i2);
        Object obj2 = this.cache.get(modelKey);
        modelKey.release();
        return obj2;
    }

    public void put(Object obj, int i, int i2, Object obj2) {
        this.cache.put(ModelKey.get(obj, i, i2), obj2);
    }
}
