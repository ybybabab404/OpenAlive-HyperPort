package com.miui.keyguard.editor.edit.wallpaper;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageView;
import com.miui.keyguard.editor.data.bean.WallpaperPositionInfo;
import com.miui.keyguard.editor.data.bean.WallpaperTypeInfo;
import com.miui.keyguard.editor.edit.base.WallpaperEditor;
import java.util.HashSet;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*  JADX ERROR: Error in decompile pass: KotlinMetadataDecompile
    java.lang.IllegalArgumentException: Provided Metadata instance does not have metadataVersion in it and therefore is malformed and cannot be read.
    	at kotlin.metadata.jvm.internal.JvmReadUtils.checkMetadataVersionForRead(JvmReadUtils.kt:79)
    	at kotlin.metadata.jvm.internal.JvmReadUtils.readMetadataImpl$kotlin_metadata_jvm(JvmReadUtils.kt:46)
    	at kotlin.metadata.jvm.KotlinClassMetadata$Companion.readLenient(KotlinClassMetadata.kt:418)
    	at jadx.plugins.kotlin.metadata.utils.KotlinMetadataExtKt.getKotlinClassMetadata(KotlinMetadataExt.kt:68)
    	at jadx.plugins.kotlin.metadata.utils.KmClassWrapper$Companion.getWrapper(KmClassWrapper.kt:31)
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:33)
    */
/* JADX INFO: compiled from: WallpaperImageView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class WallpaperImageView extends AppCompatImageView implements ViewTreeObserver.OnGlobalLayoutListener, GestureListener, IWallpaperLayer {
    public static final /* synthetic */ int $r8$clinit = 0;
    public GestureManager gestureManager;
    public boolean inTemplate;
    public Bitmap originBitmap;
    public Boolean scaleable;
    public WallpaperCallback wallpaperCallback;
    public final HashSet wallpaperChangedListenerSet;
    public String wallpaperType;

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public WallpaperImageView(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public WallpaperImageView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public WallpaperImageView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        setScaleType(ImageView.ScaleType.MATRIX);
        this.scaleable = Boolean.TRUE;
        this.inTemplate = true;
        this.wallpaperChangedListenerSet = new HashSet();
    }

    public /* synthetic */ WallpaperImageView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void addGestureListener(WallpaperChangedListener wallpaperChangedListener) {
        IWallpaperLayer.DefaultImpls.addGestureListener(this, wallpaperChangedListener);
    }

    public boolean canScale() {
        return IWallpaperLayer.DefaultImpls.canScale(this);
    }

    @Override // android.view.View
    public boolean canScrollHorizontally(int i) {
        GestureManager gestureManager = this.gestureManager;
        RectF matrixRectF$default = gestureManager != null ? GestureManager.getMatrixRectF$default(gestureManager, null, 1, null) : null;
        if (matrixRectF$default != null && !matrixRectF$default.isEmpty()) {
            if (i > 0) {
                return matrixRectF$default.right >= ((float) (getWidth() + 1));
            }
            if (matrixRectF$default.left <= -1.0f) {
                return true;
            }
        }
        return false;
    }

    @Override // android.view.View
    public boolean canScrollVertically(int i) {
        GestureManager gestureManager = this.gestureManager;
        RectF matrixRectF$default = gestureManager != null ? GestureManager.getMatrixRectF$default(gestureManager, null, 1, null) : null;
        if (matrixRectF$default != null && !matrixRectF$default.isEmpty()) {
            if (i > 0) {
                return matrixRectF$default.bottom >= ((float) (getHeight() + 1));
            }
            if (matrixRectF$default.top <= -1.0f) {
                return true;
            }
        }
        return false;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public int currentMagicType() {
        return IWallpaperLayer.DefaultImpls.currentMagicType(this);
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
        return this.inTemplate;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public Bitmap getOriginBitmap() {
        return this.originBitmap;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public WallpaperPositionInfo getPosition(Bitmap bitmap) {
        return IWallpaperLayer.DefaultImpls.getPosition(this, bitmap);
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public Boolean getScaleable() {
        return this.scaleable;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public Surface getVideoSurface() {
        return null;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public Bitmap getWallpaperBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        draw(new Canvas(bitmap));
        return bitmap;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public WallpaperCallback getWallpaperCallback() {
        return this.wallpaperCallback;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @NotNull
    public HashSet<WallpaperChangedListener> getWallpaperChangedListenerSet() {
        return this.wallpaperChangedListenerSet;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public Matrix getWallpaperMatrix() {
        return IWallpaperLayer.DefaultImpls.getWallpaperMatrix(this);
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    @Nullable
    public String getWallpaperType() {
        return this.wallpaperType;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public boolean isCut() {
        return IWallpaperLayer.DefaultImpls.isCut(this);
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.GestureListener
    public void onApply(Matrix matrix, Boolean bool, Matrix matrix2) {
        Intrinsics.checkNotNullParameter(matrix, "matrix");
        setImageMatrix(matrix);
        Iterator<T> it = getWallpaperChangedListenerSet().iterator();
        while (it.hasNext()) {
            ((WallpaperChangedListener) it.next()).onChanged(matrix);
        }
    }

    @Override // android.widget.ImageView, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override // android.widget.ImageView, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public void onGlobalLayout() {
        GestureManager gestureManager = this.gestureManager;
        if (gestureManager != null) {
            gestureManager.targetViewFitSrcDrawable(getWallpaperType());
        }
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (!canScale() || motionEvent == null) {
            return false;
        }
        GestureManager gestureManager = this.gestureManager;
        if (gestureManager == null) {
            Boolean scaleable = getScaleable();
            Intrinsics.checkNotNull(scaleable);
            return scaleable.booleanValue();
        }
        Intrinsics.checkNotNull(gestureManager);
        gestureManager.notifyCheckNeedRemoveBackground();
        GestureManager gestureManager2 = this.gestureManager;
        Intrinsics.checkNotNull(gestureManager2);
        boolean zOnTouchEvent = gestureManager2.getMScaleGestureDetector().onTouchEvent(motionEvent);
        GestureManager gestureManager3 = this.gestureManager;
        Intrinsics.checkNotNull(gestureManager3);
        boolean zOnTouchEvent2 = gestureManager3.getGestureDetector().onTouchEvent(motionEvent);
        boolean z = motionEvent.getAction() == 1;
        if (!zOnTouchEvent2 && z) {
            GestureManager gestureManager4 = this.gestureManager;
            Intrinsics.checkNotNull(gestureManager4);
            if (!gestureManager4.isAnimating()) {
                GestureManager gestureManager5 = this.gestureManager;
                Intrinsics.checkNotNull(gestureManager5);
                gestureManager5.notifyGestureEnd();
            }
        }
        return zOnTouchEvent || zOnTouchEvent2;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void onWallpaperSelected(WallpaperTypeInfo wallpaperTypeInfo, String str) {
        Bitmap bitmap = wallpaperTypeInfo != null ? wallpaperTypeInfo.getBitmap() : null;
        setImageAndPosition(bitmap, null, getWallpaperType(), str + "-onWallpaperSelected");
        setScaleable(Boolean.valueOf(WallpaperEditor.Companion.canScale(wallpaperTypeInfo != null ? wallpaperTypeInfo.getType() : null)));
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void setImageAndPosition(@Nullable Bitmap bitmap, @Nullable WallpaperPositionInfo wallpaperPositionInfo, @Nullable String str, @Nullable String str2) {
        GestureManager gestureManager;
        super.setImageBitmap(bitmap);
        setOriginBitmap(bitmap);
        if (bitmap != null) {
            Resources resources = getResources();
            Intrinsics.checkNotNullExpressionValue(resources, "getResources(...)");
            BitmapDrawable bitmapDrawable = new BitmapDrawable(resources, bitmap);
            this.gestureManager = null;
            Context context = getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
            GestureManager gestureManager2 = new GestureManager(context, this, this, bitmapDrawable, wallpaperPositionInfo);
            this.gestureManager = gestureManager2;
            gestureManager2.setFromWhere(str2);
            if (getHeight() <= 0 || (gestureManager = this.gestureManager) == null) {
                return;
            }
            gestureManager.targetViewFitSrcDrawable(str);
        }
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void setInTemplate(boolean z) {
        this.inTemplate = z;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void setOriginBitmap(@Nullable Bitmap bitmap) {
        this.originBitmap = bitmap;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void setScaleable(@Nullable Boolean bool) {
        this.scaleable = bool;
        GestureManager gestureManager = this.gestureManager;
        if (gestureManager != null) {
            gestureManager.setScaleable(bool);
        }
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void setWallpaperCallback(@Nullable WallpaperCallback wallpaperCallback) {
        this.wallpaperCallback = wallpaperCallback;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void setWallpaperType(@Nullable String str) {
        this.wallpaperType = str;
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public void switchGlassShader(int i, WallpaperEditParam wallpaperEditParam) {
        IWallpaperLayer.DefaultImpls.switchGlassShader(this, i, wallpaperEditParam);
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.IWallpaperLayer
    public View wallpaperLayerView() {
        return this;
    }
}
