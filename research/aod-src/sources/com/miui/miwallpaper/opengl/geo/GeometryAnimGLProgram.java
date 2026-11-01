package com.miui.miwallpaper.opengl.geo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import com.miui.miwallpaper.opengl.GlassAnimGLProgram;
import com.miui.miwallpaper.opengl.TextureConfig;

/* JADX INFO: loaded from: classes2.dex */
public class GeometryAnimGLProgram extends GlassAnimGLProgram {
    public final LSphereAndRoundGeo mLSphereAndRoundGeo;
    public final RSphereAndRoundGeo mRSphereAndRoundGeo;

    public GeometryAnimGLProgram(Context context) {
        super(context);
        this.mLSphereAndRoundGeo = new LSphereAndRoundGeo(context);
        this.mRSphereAndRoundGeo = new RSphereAndRoundGeo(context);
    }

    public void animateGeoIOR(float f) {
        LSphereAndRoundGeo lSphereAndRoundGeo = this.mLSphereAndRoundGeo;
        if (lSphereAndRoundGeo == null || this.mRSphereAndRoundGeo == null) {
            return;
        }
        this.mProgress = f;
        lSphereAndRoundGeo.animateGeoIOR(f);
        this.mRSphereAndRoundGeo.animateGeoIOR(f);
    }

    public void animateGeoPos(float f) {
        LSphereAndRoundGeo lSphereAndRoundGeo = this.mLSphereAndRoundGeo;
        if (lSphereAndRoundGeo == null || this.mRSphereAndRoundGeo == null) {
            return;
        }
        lSphereAndRoundGeo.animateGeoPos(f);
        this.mRSphereAndRoundGeo.animateGeoPos(f);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void commonDraw(boolean z, int i, boolean z2) {
        RSphereAndRoundGeo rSphereAndRoundGeo;
        if (this.mLSphereAndRoundGeo == null || (rSphereAndRoundGeo = this.mRSphereAndRoundGeo) == null) {
            return;
        }
        rSphereAndRoundGeo.commonDraw(true, i, z2);
        this.mLSphereAndRoundGeo.commonDraw(z, this.mRSphereAndRoundGeo.getFBOTexture(), true);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void disposeTexture() {
        LSphereAndRoundGeo lSphereAndRoundGeo = this.mLSphereAndRoundGeo;
        if (lSphereAndRoundGeo == null || this.mRSphereAndRoundGeo == null) {
            return;
        }
        lSphereAndRoundGeo.disposeTexture();
        this.mRSphereAndRoundGeo.disposeTexture();
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public float[] getMVPMatrix() {
        RSphereAndRoundGeo rSphereAndRoundGeo = this.mRSphereAndRoundGeo;
        return rSphereAndRoundGeo != null ? rSphereAndRoundGeo.getMVPMatrix() : this.mMVPMatrix;
    }

    public float[] getScaleXY() {
        RSphereAndRoundGeo rSphereAndRoundGeo = this.mRSphereAndRoundGeo;
        return rSphereAndRoundGeo != null ? new float[]{rSphereAndRoundGeo.mScaleX, rSphereAndRoundGeo.mScaleY} : new float[]{1.0f, 1.0f};
    }

    public float[] getTranslateXY() {
        RSphereAndRoundGeo rSphereAndRoundGeo = this.mRSphereAndRoundGeo;
        return rSphereAndRoundGeo != null ? new float[]{rSphereAndRoundGeo.mTranslateX, rSphereAndRoundGeo.mTranslateY} : new float[]{0.0f, 0.0f};
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceChanged(int i, int i2) {
        RSphereAndRoundGeo rSphereAndRoundGeo;
        if (this.mLSphereAndRoundGeo == null || (rSphereAndRoundGeo = this.mRSphereAndRoundGeo) == null) {
            return;
        }
        rSphereAndRoundGeo.onSurfaceChanged(i, i2);
        this.mLSphereAndRoundGeo.onSurfaceChanged(i, i2);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceCreated(Bitmap bitmap) {
        super.onSurfaceCreated(bitmap);
        updateMVPMatrix(this.mScreenSize.width(), this.mScreenSize.height(), new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
        this.mRSphereAndRoundGeo.onSurfaceCreated(bitmap);
        this.mLSphereAndRoundGeo.onSurfaceCreated(bitmap);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void setScale(float f, float f2) {
        RSphereAndRoundGeo rSphereAndRoundGeo = this.mRSphereAndRoundGeo;
        if (rSphereAndRoundGeo != null) {
            rSphereAndRoundGeo.setScale(f, f2);
        }
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void setTranslate(float f, float f2) {
        RSphereAndRoundGeo rSphereAndRoundGeo = this.mRSphereAndRoundGeo;
        if (rSphereAndRoundGeo != null) {
            rSphereAndRoundGeo.setTranslate(f, f2);
        }
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void setup(Bitmap bitmap, TextureConfig textureConfig) {
        LSphereAndRoundGeo lSphereAndRoundGeo = this.mLSphereAndRoundGeo;
        if (lSphereAndRoundGeo == null || this.mRSphereAndRoundGeo == null) {
            return;
        }
        lSphereAndRoundGeo.setup(bitmap, new TextureConfig(9987, 9729, 33071, 33071));
        this.mRSphereAndRoundGeo.setup(bitmap, new TextureConfig(9987, 9729, 33071, 33071));
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void updateMVPMatrix(int i, int i2, Rect rect) {
        RSphereAndRoundGeo rSphereAndRoundGeo;
        if (this.mLSphereAndRoundGeo == null || (rSphereAndRoundGeo = this.mRSphereAndRoundGeo) == null) {
            return;
        }
        rSphereAndRoundGeo.updateMVPMatrix(i, i2, rect);
        this.mLSphereAndRoundGeo.updateMVPMatrix(i, i2, rect);
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public void useGLProgram() {
        LSphereAndRoundGeo lSphereAndRoundGeo = this.mLSphereAndRoundGeo;
        if (lSphereAndRoundGeo == null || this.mRSphereAndRoundGeo == null) {
            return;
        }
        lSphereAndRoundGeo.useGLProgram();
        this.mRSphereAndRoundGeo.useGLProgram();
    }
}
