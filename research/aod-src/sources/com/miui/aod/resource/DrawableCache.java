package com.miui.aod.resource;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.VectorDrawable;
import android.util.Log;
import androidx.collection.ArrayMap;
import androidx.core.content.res.ResourcesCompat;
import com.miui.aod.AODApplication;
import com.miui.aod.util.DeviceInfo;
import java.util.Iterator;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class DrawableCache {
    public static volatile DrawableCache sInstance;
    public final Context mContext;
    public final BitmapFactory.Options mOptionLevel0;
    public final BitmapFactory.Options mOptionLevel1;
    public final Map mCache = new ArrayMap();
    public final Map mDrawableCache = new ArrayMap();

    public @interface BitmapLevel {
    }

    public DrawableCache(Context context) {
        this.mContext = context;
        BitmapFactory.Options options = new BitmapFactory.Options();
        this.mOptionLevel0 = options;
        options.inSampleSize = DeviceInfo.isLowMemDevice() ? 2 : 1;
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        this.mOptionLevel1 = options2;
        options2.inSampleSize = DeviceInfo.isLowMemDevice() ? 5 : 1;
    }

    public static DrawableCache getInstance(Context context) {
        if (sInstance == null) {
            synchronized (DrawableCache.class) {
                try {
                    if (sInstance == null) {
                        sInstance = new DrawableCache(AODApplication.sInstance);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        return sInstance;
    }

    public Bitmap getBitmap(int i, int i2) {
        BitmapFactory.Options options;
        String str = i + "@" + i2;
        if (this.mCache.containsKey(str)) {
            return (Bitmap) this.mCache.get(str);
        }
        if (i2 != 0) {
            options = i2 != 1 ? null : this.mOptionLevel1;
        } else {
            options = this.mOptionLevel0;
        }
        Bitmap bitmapDecodeResource = BitmapFactory.decodeResource(this.mContext.getResources(), i, options);
        if (bitmapDecodeResource != null) {
            this.mCache.put(str, bitmapDecodeResource);
        }
        return bitmapDecodeResource;
    }

    public Drawable getDrawable(int i) {
        return getDrawable(i, 1);
    }

    public Drawable getDrawable(int i, int i2) {
        if (i <= 0) {
            return null;
        }
        String str = i + "@" + i2;
        if (this.mDrawableCache.containsKey(str)) {
            return (Drawable) this.mDrawableCache.get(str);
        }
        Drawable drawable = ResourcesCompat.getDrawable(this.mContext.getResources(), i, null);
        if (drawable instanceof VectorDrawable) {
            try {
                Bitmap bitmapCreateBitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmapCreateBitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                drawable = new BitmapDrawable(this.mContext.getResources(), bitmapCreateBitmap);
            } catch (Exception e) {
                Log.e("DrawableCache", "error...", e);
            }
        } else if (drawable instanceof BitmapDrawable) {
            drawable = new BitmapDrawable(this.mContext.getResources(), getBitmap(i, i2));
        }
        this.mDrawableCache.put(str, drawable);
        return drawable;
    }

    public void releaseBitmapCache() {
        this.mDrawableCache.clear();
        Iterator it = this.mCache.entrySet().iterator();
        while (it.hasNext()) {
            Bitmap bitmap = (Bitmap) ((Map.Entry) it.next()).getValue();
            if (bitmap != null && !bitmap.isRecycled()) {
                bitmap.recycle();
            }
            it.remove();
        }
    }
}
