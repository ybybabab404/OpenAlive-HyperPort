package com.miui.miwallpaper.opengl.depth;

import com.miui.miwallpaper.opengl.GlassAnimatorProgram;

/* JADX INFO: loaded from: classes2.dex */
public class GradualDepthBlurShaderAnimatorProgram extends GlassAnimatorProgram {
    public final GradualDepthBlurShaderProgram mGradualDepthBlurShaderProgram;

    public GradualDepthBlurShaderAnimatorProgram(GradualDepthBlurShaderProgram gradualDepthBlurShaderProgram) {
        super(gradualDepthBlurShaderProgram);
        this.mGradualDepthBlurShaderProgram = gradualDepthBlurShaderProgram;
    }
}
