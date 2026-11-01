package com.miui.miwallpaper;

import android.graphics.Matrix;
import android.view.TextureView;
import com.miui.miwallpaper.opengl.GlassRenderer;

/* JADX INFO: loaded from: classes2.dex */
public class GlassShaderUtils {
    public static void setInitScaleAndTranslate(Matrix matrix, GlassRenderer glassRenderer) {
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        glassRenderer.initTranslateX = fArr[2];
        glassRenderer.initTranslateY = fArr[5];
        glassRenderer.initScaleXI = fArr[0];
        glassRenderer.initScaleYI = fArr[4];
    }

    public static void setTransformMatrix(Matrix matrix, TextureView textureView, GlassRenderer glassRenderer) {
        float[] matrix2 = glassRenderer.getMatrix(glassRenderer.getBitmap(), textureView.getWidth(), textureView.getHeight());
        float width = textureView.getWidth() * matrix2[0];
        float height = textureView.getHeight() * matrix2[5];
        float[] fArr = new float[9];
        matrix.getValues(fArr);
        float f = fArr[0];
        float f2 = fArr[4];
        float f3 = fArr[2];
        float f4 = fArr[5];
        float f5 = f / glassRenderer.initScaleXI;
        float f6 = f2 / glassRenderer.initScaleYI;
        float f7 = (f5 == 0.0f ? 1.0f : f5) * width;
        float f8 = (f6 == 0.0f ? 1.0f : f6) * height;
        if (f5 == 0.0f) {
            f5 = 1.0f;
        }
        float f9 = 1.0f / f5;
        if (f6 == 0.0f) {
            f6 = 1.0f;
        }
        glassRenderer.setScale(f9, 1.0f / f6);
        glassRenderer.setTranslate((-(f3 - glassRenderer.initTranslateX)) / f7, (-(f4 - glassRenderer.initTranslateY)) / f8);
    }
}
