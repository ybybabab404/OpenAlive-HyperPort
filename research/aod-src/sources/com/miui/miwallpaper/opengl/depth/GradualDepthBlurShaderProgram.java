package com.miui.miwallpaper.opengl.depth;

import android.content.Context;
import android.graphics.Bitmap;
import com.miui.miwallpaper.opengl.GlassAnimGLProgram;

/* JADX INFO: loaded from: classes2.dex */
public class GradualDepthBlurShaderProgram extends GlassAnimGLProgram {
    public Bitmap mDepthBitmap;

    public GradualDepthBlurShaderProgram(Context context) {
        super(context);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceCreated(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }

    public void setAssistTexture(Bitmap bitmap) {
        this.mDepthBitmap = bitmap;
    }
}
