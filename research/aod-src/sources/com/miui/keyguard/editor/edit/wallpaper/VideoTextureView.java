package com.miui.keyguard.editor.edit.wallpaper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewTreeObserver;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.miui.keyguard.editor.data.bean.WallpaperPositionInfo;
import com.miui.keyguard.editor.data.bean.WallpaperTypeInfo;
import com.miui.keyguard.editor.edit.base.WallpaperEditor;
import com.miui.keyguard.editor.utils.HierarchyImageAvoidController;
import com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil;
import java.util.HashSet;
import java.util.Iterator;

/* JADX INFO: loaded from: classes2.dex */
public class VideoTextureView extends TextureView implements IWallpaperLayer, GestureListener, TextureView.SurfaceTextureListener, ViewTreeObserver.OnGlobalLayoutListener {
    public HierarchyImageAvoidController avoidController;
    public FirstFrameListener firstFrameListener;
    public boolean firstFrameReceived;
    public GestureManager gestureManager;
    public Boolean scaleable;
    public SurfaceCallback surfaceCallback;
    public Surface videoSurface;
    public WallpaperCallback wallpaperCallback;
    public HashSet wallpaperChangedListenerSet;

    public interface FirstFrameListener {
        void onFirstFrame();
    }

    public interface SurfaceCallback {
        void surfaceCreated(Surface surface);
    }

    public VideoTextureView(@NonNull Context context) {
        super(context);
        this.gestureManager = null;
        this.videoSurface = null;
        this.surfaceCallback = null;
        this.wallpaperCallback = null;
        this.avoidController = null;
        this.firstFrameReceived = false;
        this.scaleable = Boolean.FALSE;
        this.wallpaperChangedListenerSet = new HashSet();
        init();
    }

    public VideoTextureView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.gestureManager = null;
        this.videoSurface = null;
        this.surfaceCallback = null;
        this.wallpaperCallback = null;
        this.avoidController = null;
        this.firstFrameReceived = false;
        this.scaleable = Boolean.FALSE;
        this.wallpaperChangedListenerSet = new HashSet();
        init();
    }

    public VideoTextureView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.gestureManager = null;
        this.videoSurface = null;
        this.surfaceCallback = null;
        this.wallpaperCallback = null;
        this.avoidController = null;
        this.firstFrameReceived = false;
        this.scaleable = Boolean.FALSE;
        this.wallpaperChangedListenerSet = new HashSet();
        init();
    }

    public void addFirstFrameListener(FirstFrameListener firstFrameListener) {
        if (this.firstFrameReceived) {
            firstFrameListener.onFirstFrame();
        } else {
            this.firstFrameListener = firstFrameListener;
        }
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void addGestureListener(WallpaperChangedListener wallpaperChangedListener) {
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public int currentMagicType() {
        return 0;
    }

    public HierarchyImageAvoidController getAvoidController() {
        return this.avoidController;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public GestureManager getGestureManager() {
        return this.gestureManager;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public float getGlassInitScaleX() {
        return 1.0f;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public boolean getInTemplate() {
        return true;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public Bitmap getOriginBitmap() {
        return null;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public WallpaperPositionInfo getPosition(Bitmap bitmap) {
        GestureManager gestureManager = this.gestureManager;
        if (gestureManager != null) {
            return gestureManager.getPosition(bitmap);
        }
        return null;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public Boolean getScaleable() {
        return this.scaleable;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public Surface getVideoSurface() {
        return this.videoSurface;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public Bitmap getWallpaperBitmap(Bitmap bitmap) {
        getBitmap(bitmap);
        return bitmap;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public WallpaperCallback getWallpaperCallback() {
        return this.wallpaperCallback;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public HashSet<WallpaperChangedListener> getWallpaperChangedListenerSet() {
        return this.wallpaperChangedListenerSet;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public Matrix getWallpaperMatrix() {
        return null;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public String getWallpaperType() {
        return "";
    }

    public void init() {
        super.setSurfaceTextureListener(this);
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public boolean isCut() {
        return false;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.GestureListener
    public void onApply(Matrix matrix, Boolean bool, Matrix matrix2) {
        Iterator<WallpaperChangedListener> it = getWallpaperChangedListenerSet().iterator();
        while (it.hasNext()) {
            it.next().onChanged(matrix);
        }
    }

    @Override // android.view.TextureView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override // android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        GestureManager gestureManager = this.gestureManager;
        if (gestureManager != null) {
            gestureManager.targetViewFitSrcDrawable(null);
        }
    }

    @Override // android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        HierarchyImageAvoidController hierarchyImageAvoidController;
        super.onLayout(z, i, i2, i3, i4);
        if (!z || (hierarchyImageAvoidController = this.avoidController) == null) {
            return;
        }
        hierarchyImageAvoidController.onImageViewLayout(i, i2, i3, i4, false);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        this.videoSurface = new Surface(surfaceTexture);
        Log.d("Keyguard-Editor-VideoTextureView", "VideoTextureView::onSurfaceTextureAvailable view is " + this + ",surface = " + surfaceTexture + ",videoSurface = " + this.videoSurface);
        SurfaceCallback surfaceCallback = this.surfaceCallback;
        if (surfaceCallback != null) {
            surfaceCallback.surfaceCreated(this.videoSurface);
        }
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        Log.d("Keyguard-Editor-VideoTextureView", "VideoTextureView::onSurfaceTextureDestroyed: view is " + this);
        return false;
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        Log.d("Keyguard-Editor-VideoTextureView", "VideoTextureView::onSurfaceTextureSizeChanged: view is " + this);
    }

    @Override // android.view.TextureView.SurfaceTextureListener
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        if (this.firstFrameReceived) {
            return;
        }
        this.firstFrameReceived = true;
        FirstFrameListener firstFrameListener = this.firstFrameListener;
        if (firstFrameListener != null) {
            firstFrameListener.onFirstFrame();
            this.firstFrameListener = null;
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!this.scaleable.booleanValue() || motionEvent == null) {
            return false;
        }
        GestureManager gestureManager = this.gestureManager;
        if (gestureManager == null) {
            return this.scaleable.booleanValue();
        }
        gestureManager.notifyCheckNeedRemoveBackground();
        boolean zOnTouchEvent = this.gestureManager.getMScaleGestureDetector().onTouchEvent(motionEvent);
        boolean zOnTouchEvent2 = this.gestureManager.getGestureDetector().onTouchEvent(motionEvent);
        boolean z = motionEvent.getAction() == 1;
        if (!zOnTouchEvent2 && z) {
            this.gestureManager.notifyGestureEnd();
        }
        return zOnTouchEvent || zOnTouchEvent2;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void onWallpaperSelected(WallpaperTypeInfo wallpaperTypeInfo, String str) {
        switchWallpaperBitmap(wallpaperTypeInfo);
    }

    public void setAvoidController(HierarchyImageAvoidController hierarchyImageAvoidController) {
        this.avoidController = hierarchyImageAvoidController;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void setImageAndPosition(@Nullable Bitmap bitmap, @Nullable WallpaperPositionInfo wallpaperPositionInfo, @Nullable String str, @Nullable String str2) {
        if (bitmap != null) {
            this.gestureManager = new MagicWallpaperGestureManager(getContext(), this, this, new BitmapDrawable(getResources(), bitmap), wallpaperPositionInfo);
        } else {
            Log.e("Keyguard-Editor-VideoTextureView", "VideoTextureView setImageAndPosition bitmap is null");
            try {
                KeyguardEditorMiSightUtil.INSTANCE.reportWallpaperNotDisplayed("VideoTextureView.setImageAndPosition_bitmap_null");
            } catch (Exception unused) {
            }
        }
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void setInTemplate(boolean z) {
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void setOriginBitmap(@Nullable Bitmap bitmap) {
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void setScaleable(Boolean bool) {
        this.scaleable = bool;
        GestureManager gestureManager = this.gestureManager;
        if (gestureManager != null) {
            gestureManager.setScaleable(bool);
        }
    }

    public void setSurfaceCallback(SurfaceCallback surfaceCallback) {
        this.surfaceCallback = surfaceCallback;
    }

    @Override // android.view.TextureView
    @Deprecated
    public void setSurfaceTextureListener(TextureView.SurfaceTextureListener surfaceTextureListener) {
        super.setSurfaceTextureListener(surfaceTextureListener);
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void setWallpaperCallback(@Nullable WallpaperCallback wallpaperCallback) {
        this.wallpaperCallback = wallpaperCallback;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void setWallpaperType(@Nullable String str) {
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void switchGlassShader(int i, WallpaperEditParam wallpaperEditParam) {
    }

    public final void switchWallpaperBitmap(WallpaperTypeInfo wallpaperTypeInfo) {
        Bitmap bitmap;
        if (wallpaperTypeInfo == null || (bitmap = wallpaperTypeInfo.getBitmap()) == null) {
            return;
        }
        setImageAndPosition(bitmap, null, null, null);
        this.scaleable = Boolean.valueOf(WallpaperEditor.canScale(wallpaperTypeInfo.getType()));
        if (getWidth() > 0) {
            this.gestureManager.targetViewFitSrcDrawable(null);
        }
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public View wallpaperLayerView() {
        return this;
    }
}
