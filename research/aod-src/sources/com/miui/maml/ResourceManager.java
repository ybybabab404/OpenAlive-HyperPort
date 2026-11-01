package com.miui.maml;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.os.AsyncTask;
import android.os.MemoryFile;
import android.text.TextUtils;
import android.util.LruCache;
import android.util.MiuiDisplayMetrics;
import androidx.annotation.Nullable;
import androidx.collection.ArraySet;
import com.miui.maml.util.MamlLog;
import java.io.File;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;
import org.w3c.dom.Element;

/* JADX INFO: loaded from: classes2.dex */
public class ResourceManager {
    private static final int DENSITY_HIGH_R = 240;
    private static final int DENSITY_XHIGH_R = 360;
    private static final int DENSITY_XXHIGH_R = 540;
    private static final int DENSITY_XXXHIGH = 640;
    private static final int DENSITY_XXXHIGH_R = 720;
    private static final String LOG_TAG = "ResourceManager";
    private static final int RESOURCE_FALLBACK_DENSITY = 480;
    private static final String RESOURCE_FALLBACK_EXTRA_FOLDER = "den480/";
    private static volatile int sRef;
    private int mDefaultResourceDensity;
    private int mExtraResourceDensity;
    private String mExtraResourceFolder;
    private ResourceLoader mResourceLoader;
    private int mTargetDensity;
    private static final String RESOURCE_DENSITY_DEVICE_FALLBACK_EXTRA_FOLDER = "den" + MiuiDisplayMetrics.DENSITY_DEVICE + "/";
    private static final Object sLock = new Object();
    private static final int DEF_CACHE_SIZE = 268435456;
    protected static LruCache<String, BitmapInfo> sBitmapsCache = new LruCache<String, BitmapInfo>(DEF_CACHE_SIZE) { // from class: com.miui.maml.ResourceManager.1
        @Override // android.util.LruCache
        public int sizeOf(String str, BitmapInfo bitmapInfo) {
            Bitmap bitmap;
            if (bitmapInfo == null || (bitmap = bitmapInfo.mBitmap) == null) {
                return 0;
            }
            return bitmap.getAllocationByteCount();
        }
    };
    private static ConcurrentHashMap<String, WeakReference<BitmapInfo>> sWeakRefBitmapsCache = new ConcurrentHashMap<>();
    protected final Object mBitmapKeysLock = new Object();
    protected ArraySet<String> mBitmapKeys = new ArraySet<>();
    private final ArraySet<String> mLoadingBitmaps = new ArraySet<>();

    public interface AsyncLoadListener {
        void onLoadComplete(String str, BitmapInfo bitmapInfo);
    }

    public static class BitmapInfo {
        public final Bitmap mBitmap;
        public String mKey;
        public long mLastVisitTime;
        public boolean mLoading;
        public final NinePatch mNinePatch;
        public final Rect mPadding;

        public BitmapInfo() {
            this.mBitmap = null;
            this.mPadding = null;
            this.mNinePatch = null;
        }

        public BitmapInfo(Bitmap bitmap, Rect rect) {
            this.mBitmap = bitmap;
            this.mPadding = rect;
            if (bitmap == null || bitmap.getNinePatchChunk() == null) {
                this.mNinePatch = null;
            } else {
                this.mNinePatch = new NinePatch(bitmap, bitmap.getNinePatchChunk(), null);
            }
            this.mLastVisitTime = System.currentTimeMillis();
        }
    }

    public class LoadBitmapAsyncTask extends AsyncTask<String, Object, BitmapInfo> {
        private AsyncLoadListener mLoadListener;
        private String mSrc;

        public LoadBitmapAsyncTask(AsyncLoadListener asyncLoadListener) {
            this.mLoadListener = asyncLoadListener;
        }

        @Override // android.os.AsyncTask
        public BitmapInfo doInBackground(String... strArr) {
            String str = strArr[0];
            this.mSrc = str;
            if (str != null) {
                return ResourceManager.this.loadBitmap(str);
            }
            return null;
        }

        @Override // android.os.AsyncTask
        public void onPostExecute(BitmapInfo bitmapInfo) {
            synchronized (ResourceManager.this.mLoadingBitmaps) {
                this.mLoadListener.onLoadComplete(this.mSrc, bitmapInfo);
                ResourceManager.this.mLoadingBitmaps.remove(this.mSrc);
            }
        }
    }

    public ResourceManager(ResourceLoader resourceLoader) {
        synchronized (sLock) {
            sRef++;
        }
        this.mResourceLoader = resourceLoader;
    }

    public static void clear() {
        evictCacheSafety();
        sWeakRefBitmapsCache.clear();
    }

    private static void evictCacheSafety() {
        try {
            if (sBitmapsCache.size() > 0) {
                sBitmapsCache.evictAll();
            }
        } catch (Exception e) {
            MamlLog.e(LOG_TAG, "evictCacheSafety exception", e);
        }
    }

    private BitmapInfo getCache(String str) {
        String str2 = this.mResourceLoader.getID() + str;
        BitmapInfo bitmapInfo = sBitmapsCache.get(str2);
        WeakReference<BitmapInfo> weakReference = sWeakRefBitmapsCache.get(str2);
        if (bitmapInfo != null) {
            bitmapInfo.mLastVisitTime = System.currentTimeMillis();
            if (weakReference == null || weakReference.get() == null) {
                sWeakRefBitmapsCache.put(str2, new WeakReference<>(bitmapInfo));
                return bitmapInfo;
            }
        } else if (weakReference != null) {
            bitmapInfo = weakReference.get();
            if (bitmapInfo != null) {
                bitmapInfo.mLastVisitTime = System.currentTimeMillis();
                synchronized (this.mBitmapKeysLock) {
                    this.mBitmapKeys.add(str2);
                }
                sBitmapsCache.put(str2, bitmapInfo);
                return bitmapInfo;
            }
            sWeakRefBitmapsCache.remove(str2);
        }
        return bitmapInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public BitmapInfo loadBitmap(String str) {
        BitmapInfo bitmapInfo;
        String str2;
        BitmapFactory.Options options = new BitmapFactory.Options();
        boolean z = true;
        options.inScaled = true;
        options.inTargetDensity = this.mTargetDensity;
        if (this.mExtraResourceFolder != null) {
            MamlLog.i(LOG_TAG, "try to load resource from extra resource: " + this.mExtraResourceFolder + " of " + str);
            options.inDensity = this.mExtraResourceDensity;
            if (TextUtils.isEmpty(this.mExtraResourceFolder)) {
                str2 = str;
            } else {
                str2 = this.mExtraResourceFolder + "/" + str;
            }
            bitmapInfo = this.mResourceLoader.getBitmapInfo(str2, options);
            if (bitmapInfo != null) {
                z = false;
            }
        } else {
            bitmapInfo = null;
        }
        if (bitmapInfo == null) {
            options.inDensity = this.mDefaultResourceDensity;
            bitmapInfo = this.mResourceLoader.getBitmapInfo(str, options);
        }
        if (bitmapInfo == null) {
            options.inDensity = MiuiDisplayMetrics.DENSITY_DEVICE;
            bitmapInfo = this.mResourceLoader.getBitmapInfo(RESOURCE_DENSITY_DEVICE_FALLBACK_EXTRA_FOLDER + str, options);
        }
        if (bitmapInfo == null) {
            options.inDensity = RESOURCE_FALLBACK_DENSITY;
            bitmapInfo = this.mResourceLoader.getBitmapInfo(RESOURCE_FALLBACK_EXTRA_FOLDER + str, options);
        }
        if (bitmapInfo == null) {
            return bitmapInfo;
        }
        if (!z) {
            MamlLog.i(LOG_TAG, "load image from extra resource: " + this.mExtraResourceFolder + " of " + str);
        }
        bitmapInfo.mKey = this.mResourceLoader.getID() + str;
        bitmapInfo.mBitmap.setDensity(this.mTargetDensity);
        bitmapInfo.mLastVisitTime = System.currentTimeMillis();
        synchronized (this.mBitmapKeysLock) {
            this.mBitmapKeys.add(bitmapInfo.mKey);
        }
        sBitmapsCache.put(bitmapInfo.mKey, bitmapInfo);
        sWeakRefBitmapsCache.put(bitmapInfo.mKey, new WeakReference<>(bitmapInfo));
        return bitmapInfo;
    }

    public static int retranslateDensity(int i) {
        if (i > DENSITY_HIGH_R && i <= DENSITY_XHIGH_R) {
            return ((int) (((double) (i - DENSITY_HIGH_R)) * 0.6666666666666666d)) + DENSITY_HIGH_R;
        }
        if (i <= DENSITY_XHIGH_R || i > DENSITY_XXHIGH_R) {
            return (i <= DENSITY_XXHIGH_R || i > DENSITY_XXXHIGH_R) ? i : ((int) (((double) (i - DENSITY_XXHIGH_R)) * 0.8888888888888888d)) + RESOURCE_FALLBACK_DENSITY;
        }
        return ((int) (((double) (i - DENSITY_XHIGH_R)) * 0.8888888888888888d)) + 320;
    }

    public static int translateDensity(int i) {
        if (i > DENSITY_HIGH_R && i <= 320) {
            return ((int) (((double) (i - DENSITY_HIGH_R)) * 1.5d)) + DENSITY_HIGH_R;
        }
        if (i <= 320 || i > RESOURCE_FALLBACK_DENSITY) {
            return (i <= RESOURCE_FALLBACK_DENSITY || i > DENSITY_XXXHIGH) ? i : ((int) (((double) (i - RESOURCE_FALLBACK_DENSITY)) * 1.125d)) + DENSITY_XXHIGH_R;
        }
        return ((int) (((double) (i - 320)) * 1.125d)) + DENSITY_XHIGH_R;
    }

    public void clear(String str) {
        String str2 = this.mResourceLoader.getID() + str;
        sBitmapsCache.remove(str2);
        synchronized (this.mBitmapKeysLock) {
            this.mBitmapKeys.remove(str2);
        }
    }

    public void clearByKeys() {
        synchronized (this.mBitmapKeysLock) {
            try {
                for (int size = this.mBitmapKeys.size() - 1; size >= 0; size--) {
                    String str = (String) this.mBitmapKeys.valueAt(size);
                    sBitmapsCache.remove(str);
                    sWeakRefBitmapsCache.remove(str);
                    this.mBitmapKeys.removeAt(size);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public void finalize() throws Throwable {
        synchronized (sLock) {
            sRef--;
        }
        finish(sRef > 0);
        super.finalize();
    }

    public void finish(boolean z) {
        if (!z) {
            evictCacheSafety();
            synchronized (this.mBitmapKeysLock) {
                this.mBitmapKeys.clear();
            }
            sWeakRefBitmapsCache.clear();
        }
        synchronized (this.mLoadingBitmaps) {
            this.mLoadingBitmaps.clear();
        }
        this.mResourceLoader.finish();
    }

    public Bitmap getBitmap(String str) {
        BitmapInfo bitmapInfo = getBitmapInfo(str);
        if (bitmapInfo != null) {
            return bitmapInfo.mBitmap;
        }
        return null;
    }

    public BitmapInfo getBitmapInfo(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        BitmapInfo cache = getCache(str);
        return cache != null ? cache : loadBitmap(str);
    }

    public BitmapInfo getBitmapInfoAsync(String str, AsyncLoadListener asyncLoadListener) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        BitmapInfo cache = getCache(str);
        if (cache != null) {
            return cache;
        }
        synchronized (this.mLoadingBitmaps) {
            try {
                if (!this.mLoadingBitmaps.contains(str)) {
                    BitmapInfo cache2 = getCache(str);
                    if (cache2 != null) {
                        return cache2;
                    }
                    this.mLoadingBitmaps.add(str);
                    MamlLog.i(LOG_TAG, "load image async: " + str);
                    new LoadBitmapAsyncTask(asyncLoadListener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, str);
                }
                BitmapInfo bitmapInfo = new BitmapInfo();
                bitmapInfo.mLoading = true;
                return bitmapInfo;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public Element getConfigRoot() {
        return this.mResourceLoader.getConfigRoot();
    }

    public Drawable getDrawable(Resources resources, String str) {
        Bitmap bitmap;
        BitmapInfo bitmapInfo = getBitmapInfo(str);
        if (bitmapInfo == null || (bitmap = bitmapInfo.mBitmap) == null) {
            return null;
        }
        if (bitmapInfo.mNinePatch != null) {
            NinePatchDrawable ninePatchDrawable = new NinePatchDrawable(resources, bitmap, bitmap.getNinePatchChunk(), bitmapInfo.mPadding, str);
            ninePatchDrawable.setTargetDensity(this.mTargetDensity);
            return ninePatchDrawable;
        }
        BitmapDrawable bitmapDrawable = new BitmapDrawable(resources, bitmap);
        bitmapDrawable.setTargetDensity(this.mTargetDensity);
        return bitmapDrawable;
    }

    @Nullable
    public File getExtraFile(String str) {
        return this.mResourceLoader.getExtraFile(str);
    }

    public MemoryFile getFile(String str) {
        return this.mResourceLoader.getFile(str);
    }

    public final InputStream getInputStream(String str) {
        return this.mResourceLoader.getInputStream(str);
    }

    public final InputStream getInputStream(String str, long[] jArr) {
        return this.mResourceLoader.getInputStream(str, jArr);
    }

    public Element getManifestRoot() {
        return this.mResourceLoader.getManifestRoot();
    }

    public NinePatch getNinePatch(String str) {
        BitmapInfo bitmapInfo = getBitmapInfo(str);
        if (bitmapInfo != null) {
            return bitmapInfo.mNinePatch;
        }
        return null;
    }

    public String getPathForLanguage(String str) {
        return this.mResourceLoader.getPathForLanguage(str);
    }

    public ResourceLoader getResourceLoader() {
        return this.mResourceLoader;
    }

    public int getTargetDensity() {
        return this.mTargetDensity;
    }

    public void init() {
        this.mResourceLoader.init();
    }

    public void pause() {
    }

    public final boolean resourceExists(String str) {
        return this.mResourceLoader.resourceExists(str);
    }

    public void resume() {
    }

    public void setCacheSize(int i) {
        sBitmapsCache.resize(i);
    }

    public void setDefaultResourceDensity(int i) {
        this.mDefaultResourceDensity = i;
    }

    public void setExtraResource(String str) {
        this.mExtraResourceFolder = str;
    }

    public void setExtraResource(String str, int i) {
        this.mExtraResourceFolder = str;
        this.mExtraResourceDensity = i;
    }

    public void setExtraResourceDensity(int i) {
        this.mExtraResourceDensity = i;
    }

    public void setLocal(Locale locale) {
        if (locale == null || locale.equals(this.mResourceLoader.getLocale())) {
            return;
        }
        this.mResourceLoader.setLocal(locale);
        finish(false);
    }

    public void setTargetDensity(int i) {
        this.mTargetDensity = i;
    }
}
