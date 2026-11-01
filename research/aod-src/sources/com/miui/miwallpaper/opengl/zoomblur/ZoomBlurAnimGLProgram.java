package com.miui.miwallpaper.opengl.zoomblur;

import android.graphics.Bitmap;
import com.miui.miwallpaper.opengl.GlassAnimGLProgram;

/* JADX INFO: loaded from: classes2.dex */
public class ZoomBlurAnimGLProgram extends GlassAnimGLProgram {
    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceCreated(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }
}
