package com.miui.miwallpaper.opengl;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.opengl.GLES20;
import android.util.Log;
import com.miui.miwallpaper.opengl.ordinary.AnimImageGLProgram;
import com.miui.miwallpaper.opengl.ordinary.AnimatorProgram;
import com.miui.miwallpaper.util.BitmapUtils;
import com.miui.miwallpaper.util.ReflectUtil;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes2.dex */
public class ImageWallpaperRenderer implements GLWallpaperRenderer {
    public static final String TAG = "ImageWallpaperRenderer";
    public AnimatorProgram mAnimator;
    public final Context mContext;
    public Consumer mOnBitmapUpdated;
    public final Rect mSurfaceSize = new Rect();
    public final WallpaperTexture mTexture;
    public TextureConfig textureConfig;

    public static class WallpaperTexture {
        public Bitmap mBitmap;
        public boolean mTextureUsed;
        public final WallpaperManager mWallpaperManager;
        public final AtomicInteger mRefCount = new AtomicInteger();
        public final Rect mDimensions = new Rect();

        public WallpaperTexture(WallpaperManager wallpaperManager) {
            this.mWallpaperManager = wallpaperManager;
        }

        public final String getHash() {
            Bitmap bitmap = this.mBitmap;
            return bitmap != null ? Integer.toHexString(bitmap.hashCode()) : "null";
        }

        public Rect getTextureDimensions() {
            if (!this.mTextureUsed) {
                this.mDimensions.set(peekBitmapDimensions());
            }
            return this.mDimensions;
        }

        public Bitmap getWallpaperBitmap() {
            try {
                return (Bitmap) ReflectUtil.callObjectMethod2(this.mWallpaperManager, Bitmap.class, "getBitmap", new Class[]{Boolean.TYPE}, Boolean.FALSE);
            } catch (Exception e) {
                Log.e(ImageWallpaperRenderer.TAG, "getWallpaperBitmap fail", e);
                return null;
            }
        }

        public final Rect peekBitmapDimensions() {
            try {
                return (Rect) ReflectUtil.callObjectMethod2(this.mWallpaperManager, Rect.class, "peekBitmapDimensions", new Class[0], new Object[0]);
            } catch (Exception e) {
                Log.e(ImageWallpaperRenderer.TAG, "peekBitmapDimensions fail", e);
                return new Rect();
            }
        }

        public String toString() {
            return "{" + getHash() + ", " + this.mRefCount.get() + "}";
        }

        public void updateDimensions(Rect rect) {
            this.mDimensions.set(rect);
        }

        public void updateMatrix() {
        }

        public void use(Consumer consumer) {
            this.mRefCount.incrementAndGet();
            synchronized (this.mRefCount) {
                try {
                    if (this.mBitmap == null) {
                        this.mBitmap = getWallpaperBitmap();
                        this.mWallpaperManager.forgetLoadedWallpaper();
                        Bitmap bitmap = this.mBitmap;
                        if (bitmap != null) {
                            if (bitmap.getWidth() / this.mBitmap.getHeight() != this.mDimensions.width() / this.mDimensions.height()) {
                                updateMatrix();
                            }
                            this.mDimensions.set(0, 0, this.mBitmap.getWidth(), this.mBitmap.getHeight());
                            this.mTextureUsed = true;
                        } else {
                            Log.w(ImageWallpaperRenderer.TAG, "Can't get bitmap");
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            if (consumer != null) {
                consumer.accept(this.mBitmap);
            }
            synchronized (this.mRefCount) {
                try {
                    if (this.mRefCount.decrementAndGet() == 0 && this.mBitmap != null) {
                        this.mBitmap = null;
                    }
                } catch (Throwable th2) {
                    throw th2;
                }
            }
        }
    }

    /* JADX INFO: renamed from: $r8$lambda$M93--8nDPX6JBEILzMvEElitOI4, reason: not valid java name */
    public static /* synthetic */ void m994$r8$lambda$M938nDPX6JBEILzMvEElitOI4(ImageWallpaperRenderer imageWallpaperRenderer, Bitmap bitmap) {
        imageWallpaperRenderer.getClass();
        if (bitmap == null) {
            Log.w(TAG, "reload texture failed!");
        } else {
            BitmapUtils.checkColorSpace(bitmap, TAG);
            Consumer consumer = imageWallpaperRenderer.mOnBitmapUpdated;
            if (consumer != null) {
                consumer.accept(bitmap);
            }
        }
        imageWallpaperRenderer.mAnimator.setup(bitmap, imageWallpaperRenderer.textureConfig);
        imageWallpaperRenderer.mAnimator.onSurfaceCreated(bitmap);
    }

    public ImageWallpaperRenderer(Context context) {
        WallpaperManager wallpaperManager = (WallpaperManager) context.getSystemService(WallpaperManager.class);
        if (wallpaperManager == null) {
            Log.w(TAG, "WallpaperManager not available");
        }
        this.mContext = context;
        this.mTexture = getWallpaperTexture(wallpaperManager);
        this.mAnimator = getAnimatorProgram(context);
    }

    public AnimatorProgram getAnimatorProgram(Context context) {
        return new AnimatorProgram(new AnimImageGLProgram(context));
    }

    public WallpaperTexture getWallpaperTexture(WallpaperManager wallpaperManager) {
        return new WallpaperTexture(wallpaperManager);
    }

    public void onDrawFrame() {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        GLES20.glClear(16384);
        setGLViewport();
        this.mAnimator.onDrawFrame();
    }

    public void onSurfaceChanged(int i, int i2) {
        GLES20.glViewport(0, 0, i, i2);
        this.mSurfaceSize.set(new Rect(0, 0, i, i2));
    }

    public void onSurfaceCreated() {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        AnimatorProgram animatorProgram = getAnimatorProgram(this.mContext);
        this.mAnimator = animatorProgram;
        animatorProgram.useGLProgram();
        this.textureConfig = new TextureConfig(this.mAnimator.getGlassId(), true);
        this.mTexture.use(new Consumer() { // from class: com.miui.miwallpaper.opengl.ImageWallpaperRenderer$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ImageWallpaperRenderer.m994$r8$lambda$M938nDPX6JBEILzMvEElitOI4(this.f$0, (Bitmap) obj);
            }
        });
    }

    public void setGLViewport() {
        GLES20.glViewport(0, 0, this.mSurfaceSize.width(), this.mSurfaceSize.height());
    }
}
