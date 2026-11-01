package com.bumptech.glide.load.engine.prefill;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import androidx.annotation.VisibleForTesting;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.engine.cache.MemoryCache;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import com.bumptech.glide.util.Util;
import com.miui.maml.folme.AnimatedProperty;
import java.security.MessageDigest;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
final class BitmapPreFillRunner implements Runnable {
    public static final Clock DEFAULT_CLOCK = new Clock();
    public static final long MAX_BACKOFF_MS = TimeUnit.SECONDS.toMillis(1);
    public final BitmapPool bitmapPool;
    public final Clock clock;
    public long currentDelay;
    public final Handler handler;
    public boolean isCancelled;
    public final MemoryCache memoryCache;
    public final Set seenTypes;
    public final PreFillQueue toPrefill;

    @VisibleForTesting
    public static class Clock {
        public long now() {
            return SystemClock.currentThreadTimeMillis();
        }
    }

    public static final class UniqueKey implements Key {
        @Override // com.bumptech.glide.load.Key
        public void updateDiskCacheKey(MessageDigest messageDigest) {
            throw new UnsupportedOperationException();
        }
    }

    public boolean allocate() {
        Bitmap bitmapCreateBitmap;
        long jNow = this.clock.now();
        while (!this.toPrefill.isEmpty() && !isGcDetected(jNow)) {
            PreFillType preFillTypeRemove = this.toPrefill.remove();
            if (this.seenTypes.contains(preFillTypeRemove)) {
                bitmapCreateBitmap = Bitmap.createBitmap(preFillTypeRemove.getWidth(), preFillTypeRemove.getHeight(), preFillTypeRemove.getConfig());
            } else {
                this.seenTypes.add(preFillTypeRemove);
                bitmapCreateBitmap = this.bitmapPool.getDirty(preFillTypeRemove.getWidth(), preFillTypeRemove.getHeight(), preFillTypeRemove.getConfig());
            }
            int bitmapByteSize = Util.getBitmapByteSize(bitmapCreateBitmap);
            if (getFreeMemoryCacheBytes() >= bitmapByteSize) {
                this.memoryCache.put(new UniqueKey(), BitmapResource.obtain(bitmapCreateBitmap, this.bitmapPool));
            } else {
                this.bitmapPool.put(bitmapCreateBitmap);
            }
            if (Log.isLoggable("PreFillRunner", 3)) {
                Log.d("PreFillRunner", "allocated [" + preFillTypeRemove.getWidth() + AnimatedProperty.PROPERTY_NAME_X + preFillTypeRemove.getHeight() + "] " + preFillTypeRemove.getConfig() + " size: " + bitmapByteSize);
            }
        }
        return (this.isCancelled || this.toPrefill.isEmpty()) ? false : true;
    }

    public final long getFreeMemoryCacheBytes() {
        return this.memoryCache.getMaxSize() - this.memoryCache.getCurrentSize();
    }

    public final long getNextDelay() {
        long j = this.currentDelay;
        this.currentDelay = Math.min(4 * j, MAX_BACKOFF_MS);
        return j;
    }

    public final boolean isGcDetected(long j) {
        return this.clock.now() - j >= 32;
    }

    @Override // java.lang.Runnable
    public void run() {
        if (allocate()) {
            this.handler.postDelayed(this, getNextDelay());
        }
    }
}
