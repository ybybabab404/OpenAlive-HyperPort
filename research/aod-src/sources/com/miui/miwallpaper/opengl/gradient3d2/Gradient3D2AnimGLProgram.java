package com.miui.miwallpaper.opengl.gradient3d2;

import android.content.Context;
import android.graphics.Bitmap;
import com.miui.miwallpaper.opengl.GlassAnimGLProgram;

/* JADX INFO: loaded from: classes2.dex */
public class Gradient3D2AnimGLProgram extends GlassAnimGLProgram {
    public Gradient3D2AnimGLProgram(Context context) {
        super(context);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceCreated(Bitmap bitmap) {
        this.mBitmap = bitmap;
    }
}
