package com.miui.miwallpaper.opengl.ripple;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES30;
import com.miui.miwallpaper.opengl.GlassAnimGLProgram;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;
import com.miui.wallpaperglassshader.jar.R;
import miuix.mgl.Texture;
import miuix.mgl.Texture2D;

/* JADX INFO: loaded from: classes2.dex */
public class RippleAnimGLProgram extends GlassAnimGLProgram {
    public Texture depthTex;
    public Texture fbmTex;
    public RippleAnimGLWallpaper mRippleAnimGLWallpaper;
    public float mTime;
    public Texture normalTex;
    public Texture smoothstepTex;
    public float[] uRippleCenterPos1;
    public float[] uRippleCenterPos2;
    public float[] uRippleCenterPos3;
    public float uRippleRadius1;
    public float uRippleRadius2;
    public float uRippleRadius3;

    public RippleAnimGLProgram(Context context) {
        super(context);
        this.uRippleCenterPos1 = new float[]{205.0f, 1906.0f};
        this.uRippleCenterPos2 = new float[]{1149.0f, 1854.0f};
        this.uRippleCenterPos3 = new float[]{905.0f, 303.0f};
        this.uRippleRadius1 = 653.0f;
        this.uRippleRadius2 = 767.0f;
        this.uRippleRadius3 = 1231.0f;
    }

    public void animateRippleIOR(float f) {
        this.mProgress = Math.min(1.0f, Math.max(0.0f, f));
    }

    public void animateRippleMoveSpeed(float f) {
        this.mTime = f;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void disposeTexture() {
        super.disposeTexture();
        Texture texture = this.depthTex;
        if (texture == null || this.normalTex == null || this.fbmTex == null || this.smoothstepTex == null) {
            return;
        }
        GLES30.glDeleteTextures(4, new int[]{texture.getTextureID(), this.normalTex.getTextureID(), this.fbmTex.getTextureID(), this.smoothstepTex.getTextureID()}, 0);
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getFragmentShader() {
        return getNowShaderResourceID(this.mContext, R.raw.ripple, R.raw.ripple_clamp_border);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public AnimImageGLWallpaper getImageGLWallpaper() {
        RippleAnimGLWallpaper rippleAnimGLWallpaper = new RippleAnimGLWallpaper(this);
        this.mRippleAnimGLWallpaper = rippleAnimGLWallpaper;
        return rippleAnimGLWallpaper;
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getVertexShader() {
        return getNowShaderResourceID(this.mContext, R.raw.vertex_shader, R.raw.vertex_clamp_border);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void glProgramDrawEffectUniform(boolean z, int i, boolean z2) {
        super.glProgramDrawEffectUniform(z, i, z2);
        int i2 = this.mRippleAnimGLWallpaper.uGlassScale_uInnerProgress_uTime;
        float[] fArr = this.mMVPMatrix;
        GLES30.glUniform4f(i2, fArr[0], fArr[5], this.mProgress, this.mTime / 50.0f);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceChanged(int i, int i2) {
        super.onSurfaceChanged(i, i2);
        float fWidth = (this.mScreenSize.width() > this.mScreenSize.height() ? this.mScreenSize.width() : this.mScreenSize.height()) / 2400.0f;
        useCreateGLProgram();
        float f = (-1080.0f) * fWidth;
        float f2 = (-2400.0f) * fWidth;
        GLES30.glUniform4f(this.mRippleAnimGLWallpaper.uRipplePosRadius1_uIOR, (this.uRippleCenterPos1[0] * fWidth) + ((this.mScreenSize.width() + f) / 2.0f), (this.uRippleCenterPos1[1] * fWidth) + ((this.mScreenSize.height() + f2) / 2.0f), this.uRippleRadius1 * fWidth, 1.2f);
        GLES30.glUniform4f(this.mRippleAnimGLWallpaper.uRipplePosRadius2_uNormalScale, (this.uRippleCenterPos2[0] * fWidth) + ((this.mScreenSize.width() + f) / 2.0f), (this.uRippleCenterPos2[1] * fWidth) + ((this.mScreenSize.height() + f2) / 2.0f), this.uRippleRadius2 * fWidth, 1.0f);
        GLES30.glUniform3f(this.mRippleAnimGLWallpaper.uRipplePosRadius3, (this.uRippleCenterPos3[0] * fWidth) + ((f + this.mScreenSize.width()) / 2.0f), (this.uRippleCenterPos3[1] * fWidth) + ((f2 + this.mScreenSize.height()) / 2.0f), this.uRippleRadius3 * fWidth);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceCreated(Bitmap bitmap) {
        super.onSurfaceCreated(bitmap);
        Texture2D texture2DCreateFromBitmap = Texture2D.createFromBitmap(BitmapFactory.decodeResource(this.mContext.getResources(), R.drawable.ripple_depth));
        this.depthTex = texture2DCreateFromBitmap;
        texture2DCreateFromBitmap.setWrapMod(Texture.TextureWrapMod.REPEAT);
        GLES30.glUniform4f(this.mRippleAnimGLWallpaper.uLodThres_Fac_Shadow_Highlight, 1.2f, 3.0f, 0.45f, 1.0f);
        int i = this.mRippleAnimGLWallpaper.uGlassScale_uInnerProgress_uTime;
        float[] fArr = this.mMVPMatrix;
        GLES30.glUniform4f(i, fArr[0], fArr[5], this.mProgress, 0.0f);
        float fWidth = (this.mScreenSize.width() > this.mScreenSize.height() ? this.mScreenSize.width() : this.mScreenSize.height()) / 2400.0f;
        float f = (-1080.0f) * fWidth;
        float f2 = (-2400.0f) * fWidth;
        GLES30.glUniform4f(this.mRippleAnimGLWallpaper.uRipplePosRadius1_uIOR, (this.uRippleCenterPos1[0] * fWidth) + ((this.mScreenSize.width() + f) / 2.0f), (this.uRippleCenterPos1[1] * fWidth) + ((this.mScreenSize.height() + f2) / 2.0f), this.uRippleRadius1 * fWidth, 1.2f);
        GLES30.glUniform4f(this.mRippleAnimGLWallpaper.uRipplePosRadius2_uNormalScale, (this.uRippleCenterPos2[0] * fWidth) + ((this.mScreenSize.width() + f) / 2.0f), (this.uRippleCenterPos2[1] * fWidth) + ((this.mScreenSize.height() + f2) / 2.0f), this.uRippleRadius2 * fWidth, 1.0f);
        GLES30.glUniform3f(this.mRippleAnimGLWallpaper.uRipplePosRadius3, (this.uRippleCenterPos3[0] * fWidth) + ((f + this.mScreenSize.width()) / 2.0f), (this.uRippleCenterPos3[1] * fWidth) + ((f2 + this.mScreenSize.height()) / 2.0f), this.uRippleRadius3 * fWidth);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void useTexture(int i, boolean z) {
        super.useTexture(i, z);
        GLES30.glActiveTexture(33985);
        GLES30.glBindTexture(3553, this.depthTex.getTextureID());
        GLES30.glUniform1i(this.mRippleAnimGLWallpaper.uDepthTex, 1);
        GLES30.glActiveTexture(33986);
        GLES30.glBindTexture(3553, this.normalTex.getTextureID());
        GLES30.glUniform1i(this.mRippleAnimGLWallpaper.uNormalTex, 2);
        GLES30.glActiveTexture(33987);
        GLES30.glBindTexture(3553, this.fbmTex.getTextureID());
        GLES30.glUniform1i(this.mRippleAnimGLWallpaper.uFbmTex, 3);
        GLES30.glActiveTexture(33988);
        GLES30.glBindTexture(3553, this.smoothstepTex.getTextureID());
        GLES30.glUniform1i(this.mRippleAnimGLWallpaper.uSmoothstepTex, 4);
    }
}
