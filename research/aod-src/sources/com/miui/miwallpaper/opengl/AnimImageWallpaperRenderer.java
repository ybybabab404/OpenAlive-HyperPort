package com.miui.miwallpaper.opengl;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.view.animation.Interpolator;
import com.miui.miwallpaper.opengl.ordinary.AnimatorProgram;
import com.miui.miwallpaper.util.ScreenUtils;

/* JADX INFO: loaded from: classes2.dex */
public abstract class AnimImageWallpaperRenderer extends ImageWallpaperRenderer {
    public static final Interpolator CUBIC_EASE_OUT = new CubicEaseOutInterpolator();
    public String TAG;
    public CallBack mCallback;
    public ImageWallpaperTexture mImageWallpaperTexture;

    public interface CallBack {
        void onRefresh(boolean z);
    }

    public static class CubicEaseOutInterpolator implements Interpolator {
        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2) + 1.0f;
        }
    }

    public static class ImageWallpaperTexture extends ImageWallpaperRenderer.WallpaperTexture {
        public final AnimImageWallpaperRenderer mAnimImageWallpaperRenderer;

        public ImageWallpaperTexture(WallpaperManager wallpaperManager, AnimImageWallpaperRenderer animImageWallpaperRenderer) {
            super(wallpaperManager);
            this.mAnimImageWallpaperRenderer = animImageWallpaperRenderer;
        }

        @Override // com.miui.miwallpaper.opengl.ImageWallpaperRenderer.WallpaperTexture
        public Bitmap getWallpaperBitmap() {
            return this.mAnimImageWallpaperRenderer.getBitmap();
        }

        @Override // com.miui.miwallpaper.opengl.ImageWallpaperRenderer.WallpaperTexture
        public void updateMatrix() {
            super.updateMatrix();
            AnimImageWallpaperRenderer animImageWallpaperRenderer = this.mAnimImageWallpaperRenderer;
            animImageWallpaperRenderer.updateMVPMatrix(animImageWallpaperRenderer.mSurfaceSize.width(), this.mAnimImageWallpaperRenderer.mSurfaceSize.height());
        }
    }

    public AnimImageWallpaperRenderer(Context context) {
        super(context);
        this.TAG = getClass().getSimpleName();
        this.mSurfaceSize.set(ScreenUtils.getScreenRect(context));
    }

    public void disposeMainTexture() {
        this.mAnimator.disposeMainTexture();
    }

    public void disposeTexture() {
        this.mAnimator.disposeTexture();
    }

    public abstract Bitmap getBitmap();

    @Override // com.miui.miwallpaper.opengl.ImageWallpaperRenderer
    public ImageWallpaperRenderer.WallpaperTexture getWallpaperTexture(WallpaperManager wallpaperManager) {
        ImageWallpaperTexture imageWallpaperTexture = new ImageWallpaperTexture(wallpaperManager, this);
        this.mImageWallpaperTexture = imageWallpaperTexture;
        return imageWallpaperTexture;
    }

    @Override // com.miui.miwallpaper.opengl.ImageWallpaperRenderer
    public void onDrawFrame() {
        super.onDrawFrame();
    }

    @Override // com.miui.miwallpaper.opengl.ImageWallpaperRenderer
    public void onSurfaceChanged(int i, int i2) {
        super.onSurfaceChanged(i, i2);
        updateMVPMatrix(this.mSurfaceSize.width(), this.mSurfaceSize.height());
    }

    @Override // com.miui.miwallpaper.opengl.ImageWallpaperRenderer
    public void onSurfaceCreated() {
        super.onSurfaceCreated();
        updateMVPMatrix(this.mSurfaceSize.width(), this.mSurfaceSize.height());
    }

    public final void updateMVPMatrix(int i, int i2) {
        Rect textureDimensions = this.mTexture.getTextureDimensions();
        AnimatorProgram animatorProgram = this.mAnimator;
        if (animatorProgram != null) {
            animatorProgram.updateMVPMatrix(i, i2, textureDimensions);
        }
    }
}
