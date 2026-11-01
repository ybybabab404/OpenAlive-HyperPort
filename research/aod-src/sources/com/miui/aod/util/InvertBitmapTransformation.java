package com.miui.aod.util;

import android.graphics.Bitmap;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/* JADX INFO: loaded from: classes.dex */
public class InvertBitmapTransformation extends BitmapTransformation {
    public static final byte[] ID_BYTES = "com.miui.aod.util.InvertBitmapTransformation".getBytes(StandardCharsets.UTF_8);
    public boolean mIsInvert;

    public InvertBitmapTransformation(boolean z) {
        this.mIsInvert = z;
    }

    @Override // com.bumptech.glide.load.Key
    public boolean equals(Object obj) {
        return (obj instanceof InvertBitmapTransformation) && ((InvertBitmapTransformation) obj).mIsInvert == this.mIsInvert;
    }

    @Override // com.bumptech.glide.load.Key
    public int hashCode() {
        return ("com.miui.aod.util.InvertBitmapTransformation" + this.mIsInvert).hashCode();
    }

    @Override // com.bumptech.glide.load.resource.bitmap.BitmapTransformation
    public Bitmap transform(BitmapPool bitmapPool, Bitmap bitmap, int i, int i2) {
        return !this.mIsInvert ? bitmap : BitmapUtils.getInvertColorBitmap(bitmap, false);
    }

    @Override // com.bumptech.glide.load.Key
    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);
        messageDigest.update(ByteBuffer.allocate(2).put(this.mIsInvert ? (byte) 1 : (byte) 0).array());
    }
}
