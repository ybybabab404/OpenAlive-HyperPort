package com.miui.miwallpaper.opengl.rain;

import android.content.Context;
import android.graphics.Bitmap;
import com.miui.miwallpaper.opengl.GlassAnimGLProgram;

/* JADX INFO: loaded from: classes2.dex */
public class RainAnimGLProgram extends GlassAnimGLProgram {
    public RainAnimGLProgram(Context context) {
        super(context);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceCreated(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }
}
