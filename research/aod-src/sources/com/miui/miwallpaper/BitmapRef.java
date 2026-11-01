package com.miui.miwallpaper;

import android.graphics.Bitmap;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: loaded from: classes2.dex */
public class BitmapRef {
    public Bitmap mBitmap;
    public final AtomicInteger mCount;

    public String toString() {
        return "BitmapRef{" + this.mBitmap + ", " + this.mCount + '}';
    }
}
