package com.miui.keyguard.editor.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: CenterCropDrawable.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
public final class CenterCropDrawable extends Drawable {
    public final Bitmap mBitmap;
    public float pX;
    public float pY;
    public float scale;
    public float tX;
    public float tY;

    public CenterCropDrawable(Bitmap mBitmap) {
        Intrinsics.checkNotNullParameter(mBitmap, "mBitmap");
        this.mBitmap = mBitmap;
        this.scale = 1.0f;
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        if (getBounds().isEmpty() || this.mBitmap.isRecycled()) {
            return;
        }
        float f = this.scale;
        if (f > 1.0f) {
            Log.i("CenterCropDrawable", "scale is " + f);
            float f2 = this.scale;
            canvas.scale(f2, f2, this.pX, this.pY);
        }
        Log.i("CenterCropDrawable", "translate x is " + this.tX + ",translate y is " + this.tY);
        canvas.drawBitmap(this.mBitmap, this.tX, this.tY, (Paint) null);
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(int i, int i2, int i3, int i4) {
        super.setBounds(i, i2, i3, i4);
        if (getBounds().isEmpty()) {
            return;
        }
        int iWidth = getBounds().width();
        int iHeight = getBounds().height();
        int width = this.mBitmap.getWidth();
        int height = this.mBitmap.getHeight();
        float f = iWidth;
        float f2 = iHeight;
        this.scale = Math.max(f / width, f2 / height);
        this.tX = (iWidth - width) / 2.0f;
        this.tY = (iHeight - height) / 2.0f;
        this.pX = f / 2.0f;
        this.pY = f2 / 2.0f;
        Log.i("CenterCropDrawable", "srcWidth = " + width + ",srcHeight = " + height + ",dstWidth = " + iWidth + ",dstHeight = " + iHeight);
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }
}
