package com.miui.miwallpaper.opengl.geo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.opengl.GLES20;
import android.opengl.GLES30;
import com.miui.aod.components.view.BaseStyleSelectView;
import com.miui.miwallpaper.opengl.GlassAnimGLProgram;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;
import com.miui.wallpaperglassshader.jar.R;
import effect_engine.utils.FBO;
import java.lang.reflect.Array;

/* JADX INFO: loaded from: classes2.dex */
public class RSphereAndRoundGeo extends GlassAnimGLProgram {
    public float[] cSizeInit;
    public float[] d;
    public double height;
    public GeometryAnimGLWallpaper mGeoAnimGLWallpaper;
    public float mGlassScaleX;
    public float mGlassScaleY;
    public float mRatio;
    public float sRad2Init;
    public double[][][] segmentsLocation;
    public double[][] segmentsRotation;
    public float uPosT;
    public double width;
    public double x;
    public double y;

    public RSphereAndRoundGeo(Context context) {
        super(context);
        this.sRad2Init = 470.5f;
        this.cSizeInit = new float[]{420.0f, 1137.0f};
        this.segmentsRotation = (double[][]) Array.newInstance((Class<?>) Double.TYPE, 1024, 2);
        this.x = 85.56999969482422d;
        this.y = 111.01000213623047d;
        this.width = 259.30999755859375d;
        this.height = 576.239990234375d;
        this.segmentsLocation = GeoAnimatorPosData.polylines;
        this.segmentsRotation = GeoAnimatorPosData.rotationCapsule;
    }

    private double[][] getPointsAtFraction(double d, double[][][] dArr) {
        int i = 1;
        int i2 = 0;
        double[][] dArr2 = (double[][]) Array.newInstance((Class<?>) Double.TYPE, dArr.length, 2);
        int i3 = 0;
        while (i3 < dArr.length) {
            int length = dArr[i3].length;
            double[] dArr3 = new double[length - 1];
            double d2 = BaseStyleSelectView.COLLAPSE_SLIDE_VALUE;
            int i4 = i2;
            double d3 = 0.0d;
            while (true) {
                double[][] dArr4 = dArr[i3];
                if (i4 >= dArr4.length - i) {
                    break;
                }
                double[] dArr5 = dArr4[i4];
                int i5 = i4 + 1;
                double[] dArr6 = dArr4[i5];
                int i6 = i2;
                int i7 = i;
                double dSqrt = Math.sqrt(Math.pow(dArr6[i2] - dArr5[i2], 2.0d) + Math.pow(dArr6[i7] - dArr5[i7], 2.0d));
                dArr3[i4] = dSqrt;
                d3 += dSqrt;
                i2 = i6;
                i4 = i5;
                i = i7;
                length = length;
                i3 = i3;
            }
            int i8 = i2;
            int i9 = i3;
            int i10 = i;
            int i11 = length;
            double d4 = d3 * d;
            while (i2 < i11 - 2) {
                double d5 = dArr3[i2];
                if (d2 + d5 >= d4) {
                    break;
                }
                d2 += d5;
                i2++;
            }
            double[][] dArr7 = dArr[i9];
            double[] dArr8 = dArr7[i2];
            double[] dArr9 = dArr7[i2 + 1];
            double d6 = (d4 - d2) / dArr3[i2];
            double d7 = dArr8[i8];
            double d8 = d7 + ((dArr9[i8] - d7) * d6);
            double d9 = dArr8[i10];
            double d10 = d9 + (d6 * (dArr9[i10] - d9));
            double[] dArr10 = dArr2[i9];
            dArr10[i8] = d8;
            dArr10[i10] = d10;
            i3 = i9 + 1;
            i2 = i8;
            i = i10;
        }
        return dArr2;
    }

    private double getRotationAtFraction(double d, double[][] dArr) {
        int length = dArr.length;
        double[] dArr2 = new double[length - 1];
        double d2 = BaseStyleSelectView.COLLAPSE_SLIDE_VALUE;
        int i = 0;
        double d3 = 0.0d;
        int i2 = 0;
        while (i2 < dArr.length - 1) {
            int i3 = i2 + 1;
            double d4 = dArr[i3][0] - dArr[i2][0];
            dArr2[i2] = d4;
            d3 += d4;
            i2 = i3;
        }
        double d5 = d * d3;
        while (i < length - 2) {
            double d6 = dArr2[i];
            if (d2 + d6 >= d5) {
                break;
            }
            d2 += d6;
            i++;
        }
        double[] dArr3 = dArr[i];
        double[] dArr4 = dArr[i + 1];
        double d7 = (d5 - d2) / dArr2[i];
        double d8 = dArr3[1];
        return d8 + (d7 * (dArr4[1] - d8));
    }

    public void animateGeoIOR(float f) {
        this.mProgress = f;
    }

    public void animateGeoPos(float f) {
        this.uPosT = f;
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getFragmentShader() {
        return getNowShaderResourceID(this.mContext, R.raw.geometry_capsule_frag, R.raw.geometry_capsule_clamp_border_frag);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public AnimImageGLWallpaper getImageGLWallpaper() {
        GeometryAnimGLWallpaper geometryAnimGLWallpaper = new GeometryAnimGLWallpaper(this);
        this.mGeoAnimGLWallpaper = geometryAnimGLWallpaper;
        return geometryAnimGLWallpaper;
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getVertexShader() {
        return getNowShaderResourceID(this.mContext, R.raw.geometry_capsule_vertex, R.raw.geometry_capsule_clamp_border_vertex);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void glProgramDrawEffectUniform(boolean z, int i, boolean z2) {
        super.glProgramDrawEffectUniform(z, i, z2);
        double[][] pointsAtFraction = getPointsAtFraction(this.uPosT, this.segmentsLocation);
        double rotationAtFraction = getRotationAtFraction(this.uPosT, this.segmentsRotation);
        double[] dArr = pointsAtFraction[0];
        double d = dArr[1];
        double d2 = this.y;
        float f = this.mRatio;
        float[] fArr = this.d;
        float f2 = fArr[1];
        double d3 = dArr[0];
        double d4 = this.x;
        float f3 = fArr[0];
        double[] dArr2 = pointsAtFraction[1];
        float f4 = (float) (((dArr2[0] - d4) * ((double) f)) + ((double) f3));
        float f5 = (float) (((dArr2[1] - d2) * ((double) f)) + ((double) f2));
        GLES30.glUniform3f(this.mGeoAnimGLWallpaper.uGlassScale_uInnerProgress, this.mGlassScaleX, this.mGlassScaleY, this.mProgress);
        GLES20.glUniform4f(this.mGeoAnimGLWallpaper.uSphereLens2_Pos_Radius_NB, (float) (((d3 - d4) * ((double) f)) + ((double) f3)), (float) (((d - d2) * ((double) f)) + ((double) f2)), this.sRad2Init, 0.83f);
        GLES20.glUniform4f(this.mGeoAnimGLWallpaper.uCapsuleLens_Pos_cosSin, f4, f5, (float) Math.cos(Math.toRadians(rotationAtFraction)), (float) Math.sin(Math.toRadians(rotationAtFraction)));
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceChanged(int i, int i2) {
        super.onSurfaceChanged(i, i2);
        FBO fbo = this.mResultFBO;
        if (fbo != null) {
            fbo.destroy();
        }
        this.mResultFBO = new FBO(i, i2, true);
        float fWidth = ((this.mScreenSize.width() > this.mScreenSize.height() ? this.mScreenSize.width() : this.mScreenSize.height()) * 1.0f) / 2400.0f;
        float[] fArr = this.cSizeInit;
        float f = fArr[0] * fWidth;
        float f2 = fArr[1] * fWidth;
        float f3 = 674.0f * fWidth;
        this.sRad2Init = fWidth * 470.5f;
        this.d = new float[2];
        if (this.mScreenSize.width() > this.mScreenSize.height()) {
            this.mRatio = (float) (((double) (this.mScreenSize.width() * 1.0f)) / this.height);
        } else {
            this.mRatio = (float) (((double) (this.mScreenSize.height() * 1.0f)) / this.height);
        }
        this.d[0] = (float) ((((double) this.mScreenSize.width()) - (this.width * ((double) this.mRatio))) * 0.5d);
        this.d[1] = (float) ((((double) this.mScreenSize.height()) - (this.height * ((double) this.mRatio))) * 0.5d);
        useCreateGLProgram();
        GLES20.glUniform4f(this.mGeoAnimGLWallpaper.uCapsuleLens_Size_NB, f * 0.5f, f2 * 0.5f, Math.min(f, f2) * 0.5f, 0.64f);
        GLES30.glUniform1f(this.mGeoAnimGLWallpaper.uDistance, f3);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceCreated(Bitmap bitmap) {
        super.onSurfaceCreated(bitmap);
        FBO fbo = this.mResultFBO;
        if (fbo != null) {
            fbo.destroy();
        }
        this.mResultFBO = new FBO(this.mScreenSize.width(), this.mScreenSize.height(), true);
        float fWidth = ((this.mScreenSize.width() > this.mScreenSize.height() ? this.mScreenSize.width() : this.mScreenSize.height()) * 1.0f) / 2400.0f;
        float[] fArr = this.cSizeInit;
        float f = fArr[0] * fWidth;
        float f2 = fArr[1] * fWidth;
        float f3 = 674.0f * fWidth;
        this.sRad2Init = fWidth * 470.5f;
        this.d = new float[2];
        if (this.mScreenSize.width() > this.mScreenSize.height()) {
            this.mRatio = (float) (((double) (this.mScreenSize.width() * 1.0f)) / this.height);
        } else {
            this.mRatio = (float) (((double) (this.mScreenSize.height() * 1.0f)) / this.height);
        }
        this.d[0] = (float) ((((double) this.mScreenSize.width()) - (this.width * ((double) this.mRatio))) * 0.5d);
        this.d[1] = (float) ((((double) this.mScreenSize.height()) - (this.height * ((double) this.mRatio))) * 0.5d);
        GLES30.glUniform1f(this.mGeoAnimGLWallpaper.uDistance, f3);
        GLES20.glUniform4f(this.mGeoAnimGLWallpaper.uLodThres_Fac_Shadow_Highlight, 0.5f, 4.0f, 0.08f, 0.36f);
        GLES30.glUniform3f(this.mGeoAnimGLWallpaper.uGlassScale_uInnerProgress, this.mGlassScaleX, this.mGlassScaleY, this.mProgress);
        GLES20.glUniform4f(this.mGeoAnimGLWallpaper.uCapsuleLens_Size_NB, f * 0.5f, f2 * 0.5f, Math.min(f, f2) * 0.5f, 0.06f);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void updateMVPMatrix(int i, int i2, Rect rect) {
        super.updateMVPMatrix(i, i2, rect);
        float[] fArr = this.mMVPMatrix;
        this.mGlassScaleX = fArr[0];
        this.mGlassScaleY = fArr[5];
    }
}
