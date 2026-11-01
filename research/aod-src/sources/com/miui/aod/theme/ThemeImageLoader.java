package com.miui.aod.theme;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
class ThemeImageLoader {
    public static volatile ThemeImageLoader sInstance;
    public final Context mContext;
    public final Map mTaskMap = new ConcurrentHashMap();
    public final Map mLoadTaskMap = new ConcurrentHashMap();
    public final Map mViewMap = new ConcurrentHashMap();
    public final Handler mMainHandler = new Handler(Looper.getMainLooper());
    public final ExecutorService mExecutor = Executors.newFixedThreadPool(1);

    public class ImageSetTask implements Runnable {
        public final Integer mKey;
        public final String mUri;

        public ImageSetTask(Integer num, String str) {
            this.mKey = num;
            this.mUri = str;
        }

        @Override // java.lang.Runnable
        public void run() {
            if (ThemeImageLoader.this.mTaskMap.containsKey(this.mKey)) {
                WeakReference weakReference = (WeakReference) ThemeImageLoader.this.mViewMap.get(this.mKey);
                if (weakReference != null && weakReference.get() != null) {
                    try {
                        Glide.with(ThemeImageLoader.this.mContext).load(Uri.parse(this.mUri)).into((ImageView) weakReference.get());
                    } catch (Exception e) {
                        Log.e("ThemeImageLoader", "run: setImageURI ", e);
                    }
                }
                ThemeImageLoader.this.mTaskMap.remove(this.mKey);
            }
        }
    }

    public class UriLoadTask implements Runnable {
        public final String mId;
        public volatile boolean mIsCanceled;
        public final int mKey;
        public final String mUrl;

        public UriLoadTask(int i, String str, String str2) {
            this.mUrl = str;
            this.mId = str2;
            this.mKey = i;
        }

        public void cancel() {
            this.mIsCanceled = true;
        }

        @Override // java.lang.Runnable
        public void run() {
            String themeImageUri = OnlineAodViewModel.getThemeImageUri(this.mId, this.mUrl);
            if (!this.mIsCanceled && themeImageUri != null) {
                ImageSetTask imageSetTask = ThemeImageLoader.this.new ImageSetTask(Integer.valueOf(this.mKey), themeImageUri);
                ThemeImageLoader.this.mTaskMap.put(Integer.valueOf(this.mKey), imageSetTask);
                ThemeImageLoader.this.mMainHandler.post(imageSetTask);
            } else {
                Log.e("ThemeImageLoader", "UriLoadTask: mIsCanceled " + this.mIsCanceled + " uri:" + themeImageUri);
            }
        }
    }

    public ThemeImageLoader(Context context) {
        this.mContext = context;
    }

    public static ThemeImageLoader getInstance(Context context) {
        if (sInstance == null) {
            synchronized (ThemeImageLoader.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new ThemeImageLoader(context);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return sInstance;
    }

    public void clear() {
        this.mViewMap.clear();
        this.mLoadTaskMap.clear();
        this.mTaskMap.clear();
        this.mMainHandler.removeCallbacksAndMessages(null);
    }

    public void loadImage(ImageView imageView, String str, String str2) {
        int iHashCode = imageView.hashCode();
        Integer numValueOf = Integer.valueOf(iHashCode);
        if (this.mTaskMap.containsKey(numValueOf)) {
            ImageSetTask imageSetTask = (ImageSetTask) this.mTaskMap.get(numValueOf);
            if (imageSetTask != null) {
                this.mMainHandler.removeCallbacks(imageSetTask);
            }
            this.mTaskMap.remove(numValueOf);
        }
        if (this.mLoadTaskMap.containsKey(numValueOf)) {
            UriLoadTask uriLoadTask = (UriLoadTask) this.mLoadTaskMap.get(numValueOf);
            if (uriLoadTask != null) {
                uriLoadTask.cancel();
            }
            this.mLoadTaskMap.remove(numValueOf);
        }
        this.mViewMap.put(numValueOf, new WeakReference(imageView));
        UriLoadTask uriLoadTask2 = new UriLoadTask(iHashCode, str, str2);
        this.mLoadTaskMap.put(numValueOf, uriLoadTask2);
        this.mExecutor.submit(uriLoadTask2);
    }
}
