package com.miui.miwallpaper.opengl.moru;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.opengl.GLES30;
import com.miui.miwallpaper.opengl.GlassAnimGLProgram;
import com.miui.miwallpaper.opengl.TextureConfig;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLWallpaper;
import com.miui.miwallpaper.util.EffectSystemSettingUtils;
import com.miui.miwallpaper.util.ScreenUtils;
import com.miui.wallpaperglassshader.jar.R;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntConsumer;
import java.util.function.IntPredicate;
import miui.os.Build;

/* JADX INFO: loaded from: classes2.dex */
public class MoruGlassAnimGLProgram extends GlassAnimGLProgram {
    public float mDistance;
    public boolean mIsNeedAnimScale;
    public boolean mIsSurfaceFirstCreated;
    public MoruGlassParam mMoruGlass;
    public MoruGlassImageGLWallpaper mMoruGlassImageGLWallpaper;
    public int[] mMoruInputTexIds;
    public int mNormalMaskTex;
    public float mNowNormalScaleX;
    public float mSaturnProgress;
    public float mSurfaceRatio;
    public TextureConfig mTextureConfig;
    public float uNormalOffsetX;

    public enum MoruBlurDistanceEnum {
        PINSTRIPE(0.1f),
        THICK_STRIPE(0.2f),
        PRISM(0.0f),
        THIN_LINE(0.2f),
        CORRUGATION(0.1f);

        public final float mDistance;

        MoruBlurDistanceEnum(float f) {
            this.mDistance = f;
        }

        public static MoruBlurDistanceEnum getBlurDistanceById(int i) {
            if (i == 0) {
                return PINSTRIPE;
            }
            if (i == 1) {
                return THICK_STRIPE;
            }
            if (i != 3) {
                return i != 4 ? PRISM : CORRUGATION;
            }
            return THIN_LINE;
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$mU8P2vRQJnXts-cu1jpwoE1Ux0A, reason: not valid java name */
    public static /* synthetic */ boolean m1017$r8$lambda$mU8P2vRQJnXtscu1jpwoE1Ux0A(int i) {
        return i > 0;
    }

    public MoruGlassAnimGLProgram(Context context) {
        super(context);
        this.mIsSurfaceFirstCreated = true;
        this.mProgress = 1.0f;
    }

    public void animateMoruGlassIOR(float f) {
        this.mProgress = f;
    }

    public void animateMoruGlassOffsetX(float f) {
        this.uNormalOffsetX = f;
    }

    public void animateMoruGlassSaturn(float f) {
        this.mSaturnProgress = f;
    }

    public void animateMoruGlassTexScale(float f) {
        this.mScaleX = f;
        this.mIsNeedAnimScale = true;
    }

    public void changeNormalScaleXToFitScreen(int i, int i2) {
        if (this.isFold) {
            if (!EffectSystemSettingUtils.isLargeScreen(this.mContext)) {
                this.mNowNormalScaleX = this.mMoruGlass.uNormalScaleX / this.mMVPMatrix[0];
                return;
            } else if (EffectSystemSettingUtils.isScreenOrientationLandscape(this.mContext)) {
                this.mNowNormalScaleX = (this.mMoruGlass.uNormalScaleX / (((this.mScreenSize.width() * 1.0f) / this.mScreenSize.height()) * 2.0f)) / this.mMVPMatrix[0];
                return;
            } else {
                this.mNowNormalScaleX = (this.mMoruGlass.uNormalScaleX / 2.0f) / this.mMVPMatrix[0];
                return;
            }
        }
        if (!Build.IS_TABLET) {
            this.mNowNormalScaleX = this.mMoruGlass.uNormalScaleX / this.mMVPMatrix[0];
            return;
        }
        float height = ((((float) this.mBitmap.getWidth()) * 1.0f) / ((float) this.mBitmap.getHeight()) >= 1.0f || EffectSystemSettingUtils.isScreenOrientationLandscape(this.mContext)) ? 1.0f : (this.mBitmap.getHeight() * 1.0f) / this.mBitmap.getWidth();
        if (i < i2) {
            i = i2;
        }
        this.mNowNormalScaleX = (this.mMoruGlass.uNormalScaleX / ((i * 1.0f) / 1080.0f)) * 0.85f * height;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void disposeTexture() {
        super.disposeTexture();
        Arrays.stream((int[]) Optional.ofNullable(this.mMoruInputTexIds).orElse(new int[0])).filter(new IntPredicate() { // from class: com.miui.miwallpaper.opengl.moru.MoruGlassAnimGLProgram$$ExternalSyntheticLambda0
            @Override // java.util.function.IntPredicate
            public final boolean test(int i) {
                return MoruGlassAnimGLProgram.m1017$r8$lambda$mU8P2vRQJnXtscu1jpwoE1Ux0A(i);
            }
        }).forEach(new IntConsumer() { // from class: com.miui.miwallpaper.opengl.moru.MoruGlassAnimGLProgram$$ExternalSyntheticLambda1
            @Override // java.util.function.IntConsumer
            public final void accept(int i) {
                GLES30.glDeleteTextures(1, new int[]{i}, 0);
            }
        });
    }

    public final float getAnimateDistance() {
        MoruGlassParam moruGlassParam = this.mMoruGlass;
        if (moruGlassParam == null) {
            return 0.0f;
        }
        float f = moruGlassParam.distance;
        return f + ((this.mDistance - f) * this.mSaturnProgress);
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getFragmentShader() {
        if (this.mGlassId == MoruGlassParam.PRISM.index) {
            return this.mIsTurnOnBlur ? getNowShaderResourceID(this.mContext, R.raw.moru_prism_fragment_blur_shader, R.raw.moru_prism_fragment_blur_clamp_border) : getNowShaderResourceID(this.mContext, R.raw.moru_prism_fragment_shader, R.raw.moru_prism_fragment_clamp_border);
        }
        return this.mIsTurnOnBlur ? getNowShaderResourceID(this.mContext, R.raw.moru_fragment_blur_shader, R.raw.moru_fragment_blur_clamp_border) : getNowShaderResourceID(this.mContext, R.raw.moru_fragment_shader, R.raw.moru_fragment_clamp_border);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public AnimImageGLWallpaper getImageGLWallpaper() {
        MoruGlassImageGLWallpaper moruGlassImageGLWallpaper = new MoruGlassImageGLWallpaper(this);
        this.mMoruGlassImageGLWallpaper = moruGlassImageGLWallpaper;
        return moruGlassImageGLWallpaper;
    }

    public final float getNowDistance() {
        return !this.mIsTurnOnBlur ? this.mMoruGlass.distance : MoruBlurDistanceEnum.getBlurDistanceById(this.mGlassId).mDistance;
    }

    public float getNowScale(float f, float f2) {
        if (this.mIsNeedAnimScale && f != 0.0f) {
            GLES30.glUniform2f(this.mMoruGlassImageGLWallpaper.uScaleCenter, 0.5f, 0.5f);
            return 1.0f / f;
        }
        if (f != 0.0f) {
            GLES30.glUniform2f(this.mMoruGlassImageGLWallpaper.uScaleCenter, 0.0f, 0.0f);
            return f;
        }
        GLES30.glUniform2f(this.mMoruGlassImageGLWallpaper.uScaleCenter, 0.5f, 0.5f);
        return 1.0f / f2;
    }

    @Override // com.miui.miwallpaper.opengl.ImageGLProgram
    public int getVertexShader() {
        return this.mGlassId == MoruGlassParam.PRISM.index ? getNowShaderResourceID(this.mContext, R.raw.moru_prism_vertex_shader, R.raw.moru_prism_vertex_clamp_border) : getNowShaderResourceID(this.mContext, R.raw.moru_vertex_shader, R.raw.moru_vertex_clamp_border);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void glProgramDrawEffectUniform(boolean z, int i, boolean z2) {
        super.glProgramDrawEffectUniform(z, i, z2);
        MoruGlassParam moruGlassParam = this.mMoruGlass;
        if (moruGlassParam == null) {
            return;
        }
        GLES30.glUniform4f(this.mMoruGlassImageGLWallpaper.uScaleOffset, getNowScale(this.mScaleX, moruGlassParam.scaleX), getNowScale(this.mScaleY, this.mMoruGlass.scaleY), this.mTranslateX, this.mTranslateY);
        int i2 = this.mMoruGlassImageGLWallpaper.uNormalScaleXYThickDis;
        float f = 1.0f / this.mNowNormalScaleX;
        Objects.requireNonNull(this.mMoruGlass);
        GLES30.glUniform4f(i2, f, 1.0f, this.mMoruGlass.thickness, getAnimateDistance());
        GLES30.glUniform2f(this.mMoruGlassImageGLWallpaper.uNormalOffsetXY, this.uNormalOffsetX, 0.0f);
        GLES30.glUniform1f(this.mMoruGlassImageGLWallpaper.uProgress, this.mProgress);
        GLES30.glUniform1f(this.mMoruGlassImageGLWallpaper.uSurfaceRatio, this.mSurfaceRatio);
        GLES30.glUniform1f(this.mMoruGlassImageGLWallpaper.uSaturnProgress, this.mSaturnProgress);
    }

    public final void initGLUniforms() {
        GLES30.glUniform1f(this.mMoruGlassImageGLWallpaper.uDisplacement, this.mMoruGlass.displacement);
        int i = this.mMoruGlassImageGLWallpaper.uNormalScaleXYThickDis;
        float f = 1.0f / this.mNowNormalScaleX;
        Objects.requireNonNull(this.mMoruGlass);
        GLES30.glUniform4f(i, f, 1.0f, this.mMoruGlass.thickness, this.mDistance);
        GLES30.glUniform1f(this.mMoruGlassImageGLWallpaper.uSurfaceRatio, this.mSurfaceRatio);
        int i2 = this.mMoruGlassImageGLWallpaper.uAspIORLightShadow;
        MoruGlassParam moruGlassParam = this.mMoruGlass;
        GLES30.glUniform4f(i2, moruGlassParam.ASP, moruGlassParam.IOR, moruGlassParam.lightness, moruGlassParam.shadowness);
        GLES30.glUniform1f(this.mMoruGlassImageGLWallpaper.uDarkness, this.mMoruGlass.darkness);
        GLES30.glUniform4f(this.mMoruGlassImageGLWallpaper.uSaturnContrast, 1.1f, 1.1f, 0.0f, 0.0f);
        GLES30.glUniform4f(this.mMoruGlassImageGLWallpaper.uScaleOffset, getNowScale(this.mScaleX, this.mMoruGlass.scaleX), getNowScale(this.mScaleY, this.mMoruGlass.scaleY), this.mTranslateX, this.mTranslateY);
        GLES30.glUniform1f(this.mMoruGlassImageGLWallpaper.uProgress, 1.0f);
        GLES30.glUniform2f(this.mMoruGlassImageGLWallpaper.uNormalOffsetXY, this.mMoruGlass.offsetX, 0.0f);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceChanged(int i, int i2) {
        super.onSurfaceChanged(i, i2);
        if (!this.mIsSurfaceFirstCreated) {
            changeNormalScaleXToFitScreen(i, i2);
        }
        float f = i;
        float f2 = i2;
        this.mSurfaceRatio = Math.min((f * 1.0f) / f2, (f2 * 1.0f) / f);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void onSurfaceCreated(Bitmap bitmap) {
        super.onSurfaceCreated(bitmap);
        GLES30.glEnable(32925);
        this.mTextureConfig = new TextureConfig(this.mGlassId, false);
        Rect screenRect = ScreenUtils.getScreenRect(this.mContext);
        this.mSurfaceRatio = Math.min((screenRect.width() * 1.0f) / screenRect.height(), (screenRect.height() * 1.0f) / screenRect.width());
        updateMVPMatrix(this.mScreenSize.width(), this.mScreenSize.height(), new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()));
        this.mMoruInputTexIds = new int[2];
        MoruGlassParam valueByIndex = MoruGlassParam.getValueByIndex(this.mGlassId);
        this.mMoruGlass = valueByIndex;
        this.mMoruInputTexIds[0] = loadTexture(this.mContext, valueByIndex.normalTex, this.mTextureConfig);
        this.mMoruInputTexIds[1] = loadTexture(this.mContext, this.mMoruGlass.depthLightShadowTex, this.mTextureConfig);
        this.mMoruGlass.ASP = (bitmap.getHeight() * 1.0f) / bitmap.getWidth();
        this.mDistance = getNowDistance();
        this.mSaturnProgress = this.mIsTurnOnBlur ? 1.0f : 0.0f;
        if (this.mIsSurfaceFirstCreated) {
            this.mIsSurfaceFirstCreated = false;
            changeNormalScaleXToFitScreen(this.mScreenSize.width(), this.mScreenSize.height());
        }
        if (this.mGlassId == MoruGlassParam.PRISM.index) {
            this.mNormalMaskTex = loadTexture(this.mContext, R.drawable.normal_mask, this.mTextureConfig);
        }
        initGLUniforms();
        this.uNormalOffsetX = MoruGlassParam.getValueByIndex(this.mGlassId).offsetX;
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void updateMVPMatrix(int i, int i2, Rect rect) {
        super.updateMVPMatrix(i, i2, rect);
        if (this.mIsSurfaceFirstCreated) {
            return;
        }
        changeNormalScaleXToFitScreen(i, i2);
    }

    @Override // com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram
    public void useTexture(int i, boolean z) {
        super.useTexture(i, z);
        if (this.mMoruInputTexIds == null) {
            return;
        }
        GLES30.glActiveTexture(33985);
        GLES30.glBindTexture(3553, this.mMoruInputTexIds[0]);
        GLES30.glUniform1i(this.mMoruGlassImageGLWallpaper.normalTex, 1);
        GLES30.glActiveTexture(33986);
        GLES30.glBindTexture(3553, this.mMoruInputTexIds[1]);
        GLES30.glUniform1i(this.mMoruGlassImageGLWallpaper.uLight, 2);
        if (this.mGlassId != MoruGlassParam.PRISM.index || this.mNormalMaskTex <= 0) {
            return;
        }
        GLES30.glActiveTexture(33987);
        GLES30.glBindTexture(3553, this.mNormalMaskTex);
        GLES30.glUniform1i(this.mMoruGlassImageGLWallpaper.uNormalMask, 3);
    }
}
