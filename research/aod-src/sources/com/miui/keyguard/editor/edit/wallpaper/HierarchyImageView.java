package com.miui.keyguard.editor.edit.wallpaper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;
import androidx.appcompat.widget.AppCompatImageView;
import com.miui.keyguard.editor.data.bean.CommonConfig;
import com.miui.keyguard.editor.utils.DeviceScreenshotHelper;
import com.miui.keyguard.editor.utils.DeviceUtil;
import com.miui.keyguard.editor.utils.DualClockManager;
import com.miui.keyguard.editor.utils.HierarchyImageAvoidController;
import com.miui.keyguard.editor.utils.HierarchyImageBrightAnimController;
import com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil;
import com.miui.keyguard.editor.utils.OnHierarchyEnableChangeListener;
import com.miui.maml.folme.AnimatedProperty;
import java.lang.reflect.Field;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.JvmOverloads;
import kotlin.jvm.functions.Function0;
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
    	at jadx.plugins.kotlin.metadata.pass.KotlinMetadataDecompilePass.visit(KotlinMetadataDecompilePass.kt:31)
    */
/* JADX INFO: compiled from: HierarchyImageView.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
public final class HierarchyImageView extends AppCompatImageView implements WallpaperChangedListener, GLTextureView.TextureUpdatedListener {
    public static final Companion Companion = new Companion(null);
    public static final Lazy MAX_BITMAP_BYTE_COUNT$delegate = LazyKt.lazy(new Function0() { // from class: com.miui.keyguard.editor.edit.wallpaper.HierarchyImageView$$ExternalSyntheticLambda0
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Integer.valueOf(HierarchyImageView.MAX_BITMAP_BYTE_COUNT_delegate$lambda$3());
        }
    });
    public final Runnable applyPendingRunnable;
    public volatile HierarchyImageAvoidController avoidController;
    public float bitmapScaleFactor;
    public HierarchyImageBrightAnimController brightAnimController;
    public volatile boolean hasPendingMatrix;
    public boolean isEdited;
    public long onChangedTs;
    public final Matrix pendingMatrix;

    /* JADX INFO: compiled from: HierarchyImageView.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final int getMAX_BITMAP_BYTE_COUNT() {
            return ((Number) HierarchyImageView.MAX_BITMAP_BYTE_COUNT$delegate.getValue()).intValue();
        }

        public final boolean isSupportHierarchy(CommonConfig config) {
            Intrinsics.checkNotNullParameter(config, "config");
            return DeviceUtil.INSTANCE.isSupportHierarchyDevices() && isSupportHierarchyConfig(config);
        }

        public final boolean isSupportHierarchyConfig(CommonConfig commonConfig) {
            return (Intrinsics.areEqual(commonConfig.getLockscreenInfo().getClockInfo().getTemplateId(), "rhombus") || Intrinsics.areEqual(commonConfig.getLockscreenInfo().getClockInfo().getTemplateId(), "smart_frame")) ? false : true;
        }
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public HierarchyImageView(@NotNull Context context) {
        this(context, null, 0, 6, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public HierarchyImageView(@NotNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0, 4, null);
        Intrinsics.checkNotNullParameter(context, "context");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    @JvmOverloads
    public HierarchyImageView(@NotNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        Intrinsics.checkNotNullParameter(context, "context");
        setScaleType(ImageView.ScaleType.CENTER_CROP);
        this.bitmapScaleFactor = 1.0f;
        this.pendingMatrix = new Matrix();
        this.applyPendingRunnable = new Runnable() { // from class: com.miui.keyguard.editor.edit.wallpaper.HierarchyImageView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.applyPendingMatrix("post");
            }
        };
    }

    public /* synthetic */ HierarchyImageView(Context context, AttributeSet attributeSet, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(context, (i2 & 2) != 0 ? null : attributeSet, (i2 & 4) != 0 ? 0 : i);
    }

    public static final int MAX_BITMAP_BYTE_COUNT_delegate$lambda$3() {
        try {
            Field declaredField = Class.forName("android.graphics.RecordingCanvas").getDeclaredField("MAX_BITMAP_SIZE");
            declaredField.setAccessible(true);
            return declaredField.getInt(null);
        } catch (Exception unused) {
            return 157286400;
        }
    }

    public final void applyPendingMatrix(String str) {
        if (this.hasPendingMatrix) {
            Log.d("Keyguard-Editor-HierarchyImageView", "applyMatrix(" + str + "): delay=" + ((System.nanoTime() - this.onChangedTs) / 1000000.0f) + "ms since onChanged");
            setScaleType(ImageView.ScaleType.MATRIX);
            if (this.bitmapScaleFactor == 1.0f) {
                setImageMatrix(this.pendingMatrix);
            } else {
                Matrix matrix = new Matrix(this.pendingMatrix);
                float f = this.bitmapScaleFactor;
                matrix.preScale(1.0f / f, 1.0f / f);
                setImageMatrix(matrix);
            }
            this.hasPendingMatrix = false;
        }
    }

    public final Bitmap downsampleIfExceedsCanvasLimit(Bitmap bitmap) {
        int byteCount = bitmap.getByteCount();
        Companion companion = Companion;
        if (byteCount <= companion.getMAX_BITMAP_BYTE_COUNT()) {
            this.bitmapScaleFactor = 1.0f;
            return bitmap;
        }
        float fSqrt = (float) Math.sqrt(((double) companion.getMAX_BITMAP_BYTE_COUNT()) / ((double) bitmap.getByteCount()));
        int width = (int) (bitmap.getWidth() * fSqrt);
        int height = (int) (bitmap.getHeight() * fSqrt);
        this.bitmapScaleFactor = fSqrt;
        Log.w("Keyguard-Editor-HierarchyImageView", "downsampleIfExceedsCanvasLimit: " + bitmap.getWidth() + AnimatedProperty.PROPERTY_NAME_X + bitmap.getHeight() + "(" + bitmap.getByteCount() + ") -> " + width + AnimatedProperty.PROPERTY_NAME_X + height + ", scale=" + fSqrt);
        Bitmap bitmapCreateScaledBitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);
        Intrinsics.checkNotNullExpressionValue(bitmapCreateScaledBitmap, "createScaledBitmap(...)");
        return bitmapCreateScaledBitmap;
    }

    @Nullable
    public final HierarchyImageAvoidController getAvoidController() {
        return this.avoidController;
    }

    @Nullable
    public final HierarchyImageBrightAnimController getBrightAnimController() {
        return this.brightAnimController;
    }

    public final void initComponentWhenTemplateViewEditable(boolean z) {
        KeyguardEditorMiSightUtil keyguardEditorMiSightUtil = KeyguardEditorMiSightUtil.INSTANCE;
        DeviceUtil deviceUtil = DeviceUtil.INSTANCE;
        Context context = getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getContext(...)");
        boolean zIsLargeDisplay = deviceUtil.isLargeDisplay(context);
        int i = getResources().getConfiguration().orientation;
        Context context2 = getContext();
        Intrinsics.checkNotNullExpressionValue(context2, "getContext(...)");
        keyguardEditorMiSightUtil.hierarchySessionStart("HierarchyImageView.init", null, true, z, zIsLargeDisplay, i, DualClockManager.isDualClockEnable(context2));
        HierarchyImageAvoidController hierarchyImageAvoidController = new HierarchyImageAvoidController(this, z);
        hierarchyImageAvoidController.addHierarchyEnableChangeListener(new OnHierarchyEnableChangeListener() { // from class: com.miui.keyguard.editor.edit.wallpaper.HierarchyImageView$initComponentWhenTemplateViewEditable$1$1
            @Override // com.miui.keyguard.editor.utils.OnHierarchyEnableChangeListener
            public void onHierarchyEnableChange(boolean z2) {
                this.this$0.invalidate();
            }
        });
        this.avoidController = hierarchyImageAvoidController;
        this.brightAnimController = new HierarchyImageBrightAnimController(this);
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.WallpaperChangedListener
    public void onChanged(Matrix matrix) {
        if (matrix == null) {
            Log.w("Keyguard-Editor-HierarchyImageView", "onChanged: matrix is null");
            return;
        }
        if (getVisibility() == 0) {
            if (this.isEdited || (getContext() instanceof DeviceScreenshotHelper.ScreenshotContext)) {
                if (!(getContext() instanceof DeviceScreenshotHelper.ScreenshotContext)) {
                    this.onChangedTs = System.nanoTime();
                    this.pendingMatrix.set(matrix);
                    this.hasPendingMatrix = true;
                    removeCallbacks(this.applyPendingRunnable);
                    post(this.applyPendingRunnable);
                    return;
                }
                setScaleType(ImageView.ScaleType.MATRIX);
                if (this.bitmapScaleFactor == 1.0f) {
                    setImageMatrix(matrix);
                    return;
                }
                Matrix matrix2 = new Matrix(matrix);
                float f = this.bitmapScaleFactor;
                matrix2.preScale(1.0f / f, 1.0f / f);
                setImageMatrix(matrix2);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:6:0x0014  */
    @Override // android.widget.ImageView, android.view.View
    public void onDraw(Canvas canvas) {
        Intrinsics.checkNotNullParameter(canvas, "canvas");
        if (this.avoidController != null) {
            HierarchyImageAvoidController hierarchyImageAvoidController = this.avoidController;
            Intrinsics.checkNotNull(hierarchyImageAvoidController);
            if (hierarchyImageAvoidController.isHierarchyEnable()) {
                super.onDraw(canvas);
            }
        } else {
            super.onDraw(canvas);
        }
        HierarchyImageAvoidController hierarchyImageAvoidController2 = this.avoidController;
        if (hierarchyImageAvoidController2 != null) {
            hierarchyImageAvoidController2.onImageViewDraw();
        }
    }

    @Override // android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        HierarchyImageAvoidController hierarchyImageAvoidController;
        super.onLayout(z, i, i2, i3, i4);
        if (!z || (hierarchyImageAvoidController = this.avoidController) == null) {
            return;
        }
        HierarchyImageAvoidController.onImageViewLayout$default(hierarchyImageAvoidController, i, i2, i3, i4, false, 16, null);
    }

    @Override // com.miui.keyguard.editor.edit.wallpaper.GLTextureView.TextureUpdatedListener
    public void onTextureUpdated() {
        applyPendingMatrix("onTextureUpdated");
    }

    public final void setBrightAnimController(@Nullable HierarchyImageBrightAnimController hierarchyImageBrightAnimController) {
        this.brightAnimController = hierarchyImageBrightAnimController;
    }

    public final void setEditMode(boolean z) {
        this.isEdited = z;
    }

    @Override // androidx.appcompat.widget.AppCompatImageView, android.widget.ImageView
    public void setImageBitmap(@Nullable Bitmap bitmap) {
        Bitmap bitmapDownsampleIfExceedsCanvasLimit;
        if (bitmap != null && (bitmapDownsampleIfExceedsCanvasLimit = downsampleIfExceedsCanvasLimit(bitmap)) != null) {
            bitmap = bitmapDownsampleIfExceedsCanvasLimit;
        }
        super.setImageBitmap(bitmap);
        HierarchyImageAvoidController hierarchyImageAvoidController = this.avoidController;
        if (hierarchyImageAvoidController != null) {
            hierarchyImageAvoidController.onImageBitmapChange(bitmap);
        }
    }
}
