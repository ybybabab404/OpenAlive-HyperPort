package com.miui.keyguard.editor.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ImageView;
import com.miui.keyguard.editor.edit.wallpaper.HierarchyImageView;
import java.lang.ref.WeakReference;
import java.nio.IntBuffer;
import java.util.function.Supplier;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;

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
/* JADX INFO: compiled from: HierarchyImageAvoidController.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata
@SourceDebugExtension
final class CalculateWorker implements Supplier<Boolean> {
    public static final Companion Companion = new Companion(null);
    public static final double IMAGE_AVOID_RATE = 0.2d;
    public static final double VIDEO_AVOID_RATE = 0.5d;
    public static final SparseArray cacheMap = new SparseArray();
    public IntBuffer bitmapPixelColors;
    public final Rect checkRect;
    public final Paint depthVideoPaint;
    public DepthVideoParam depthVideoParam;
    public final WeakReference hierarchyImageViewRef;
    public int imageCanUnAlphaPixelCount;
    public final Paint imagePaint;
    public boolean isDepthVideo;
    public boolean isWorking;
    public final Rect outerSetRect;
    public Bitmap rectBitmap;
    public Canvas rectCanvas;
    public boolean shouldCheck;
    public int videoCanUnAlphaPixelCount;

    /* JADX INFO: compiled from: HierarchyImageAvoidController.kt */
    @Metadata
    public static final class Companion {
        public Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public final IntBuffer obtain(int i) {
            Object obj;
            synchronized (CalculateWorker.cacheMap) {
                try {
                    WeakReference weakReference = (WeakReference) CalculateWorker.cacheMap.get(i);
                    if (weakReference != null) {
                        CalculateWorker.cacheMap.remove(i);
                        obj = weakReference.get();
                    } else {
                        obj = null;
                    }
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
            return (IntBuffer) obj;
        }

        public final void recycle(IntBuffer intBuffer) {
            if (intBuffer == null) {
                return;
            }
            synchronized (CalculateWorker.cacheMap) {
                try {
                    WeakReference weakReference = (WeakReference) CalculateWorker.cacheMap.get(intBuffer.capacity());
                    if (weakReference != null) {
                        weakReference.clear();
                    }
                    CalculateWorker.cacheMap.put(intBuffer.capacity(), new WeakReference(intBuffer));
                    Unit unit = Unit.INSTANCE;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public CalculateWorker(WeakReference hierarchyImageViewRef) {
        Intrinsics.checkNotNullParameter(hierarchyImageViewRef, "hierarchyImageViewRef");
        this.hierarchyImageViewRef = hierarchyImageViewRef;
        this.imagePaint = new Paint();
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(new ColorMatrix(DepthVideoParam.Companion.getCONVERT_ALPHA_TO_ARGB_MATRIX_VALUE())));
        this.depthVideoPaint = paint;
        this.outerSetRect = new Rect();
        this.checkRect = new Rect();
    }

    public static /* synthetic */ int forEachCheckUnPixelInRect$default(CalculateWorker calculateWorker, int i, int i2, int i3, int i4, boolean z, boolean z2, int i5, Object obj) {
        if ((i5 & 16) != 0) {
            z = true;
        }
        if ((i5 & 32) != 0) {
            z2 = true;
        }
        return calculateWorker.forEachCheckUnPixelInRect(i, i2, i3, i4, z, z2);
    }

    public final Boolean calculateUnAlphaPercent() {
        CalculateWorker calculateWorker;
        if (this.bitmapPixelColors == null) {
            return null;
        }
        int iFindUnAlphaPixelFromEdge = findUnAlphaPixelFromEdge();
        if (iFindUnAlphaPixelFromEdge == 0) {
            calculateWorker = this;
            iFindUnAlphaPixelFromEdge = forEachCheckUnPixelInRect$default(calculateWorker, 0, 0, this.checkRect.width(), this.checkRect.height(), true, false, 32, null);
        } else {
            calculateWorker = this;
        }
        int i = iFindUnAlphaPixelFromEdge;
        Log.d("HierarchyImageAvoidCutController", "calculateUnAlphaPercent: currentUnAlphaPixelCount=" + i + " imageCanUnAlphaPixelCount=" + calculateWorker.imageCanUnAlphaPixelCount + " videoCanUnAlphaPixelCount=" + calculateWorker.videoCanUnAlphaPixelCount + " isDepthVideo=" + calculateWorker.isDepthVideo());
        HierarchyAvoidDebugLogger.logAvoidResult(calculateWorker.checkRect, i, calculateWorker.imageCanUnAlphaPixelCount, calculateWorker.videoCanUnAlphaPixelCount, calculateWorker.isDepthVideo(), IMAGE_AVOID_RATE, VIDEO_AVOID_RATE);
        HierarchyAvoidDebugLogger.logAvoidOutline(calculateWorker.checkRect, calculateWorker.bitmapPixelColors);
        boolean z = false;
        if (!calculateWorker.isDepthVideo() ? i < calculateWorker.imageCanUnAlphaPixelCount : i < calculateWorker.videoCanUnAlphaPixelCount) {
            z = true;
        }
        return Boolean.valueOf(z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0030, code lost:
    
        if ((r0 != null ? r0.getBitmap() : null) != null) goto L35;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0032, code lost:
    
        getBitmapAllPixelsColor();
        r0 = calculateUnAlphaPercent();
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0039, code lost:
    
        com.miui.keyguard.editor.utils.CalculateWorker.Companion.recycle(r5.bitmapPixelColors);
        r5.bitmapPixelColors = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0044, code lost:
    
        r2 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0046, code lost:
    
        r2 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0047, code lost:
    
        r0 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0048, code lost:
    
        android.util.Log.e("HierarchyImageAvoidCutController", "checkBitmapInTargetRect: error", r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0053, code lost:
    
        if (r5.checkRect.isEmpty() == false) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0055, code lost:
    
        com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil.reportHierarchyInvisible$default(com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil.INSTANCE, "HierarchyImageAvoidController.checkBitmapInTargetRect_exception", null, 2, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x005e, code lost:
    
        android.util.Log.d("HierarchyImageAvoidCutController", "checkBitmapInTargetRect: bitmap not ready, skip (isDepthVideo=" + isDepthVideo() + ")");
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x007b, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:?, code lost:
    
        return r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x0019, code lost:
    
        if ((r2 != null ? r2.getBitmap() : null) != null) goto L35;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public final java.lang.Boolean checkBitmapInTargetRect() {
        /*
            r5 = this;
            java.lang.ref.WeakReference r0 = r5.hierarchyImageViewRef
            java.lang.Object r0 = r0.get()
            com.miui.keyguard.editor.edit.wallpaper.HierarchyImageView r0 = (com.miui.keyguard.editor.edit.wallpaper.HierarchyImageView) r0
            r1 = 0
            if (r0 == 0) goto L7b
            com.miui.keyguard.editor.utils.DepthVideoParam r2 = r5.depthVideoParam
            java.lang.String r3 = "HierarchyImageAvoidCutController"
            if (r2 == 0) goto L1c
            if (r2 == 0) goto L18
            android.graphics.Bitmap r0 = r2.getBitmap()
            goto L19
        L18:
            r0 = r1
        L19:
            if (r0 == 0) goto L5e
            goto L32
        L1c:
            android.graphics.drawable.Drawable r0 = r0.getDrawable()
            boolean r2 = r0 instanceof android.graphics.drawable.BitmapDrawable
            if (r2 == 0) goto L27
            android.graphics.drawable.BitmapDrawable r0 = (android.graphics.drawable.BitmapDrawable) r0
            goto L28
        L27:
            r0 = r1
        L28:
            if (r0 == 0) goto L2f
            android.graphics.Bitmap r0 = r0.getBitmap()
            goto L30
        L2f:
            r0 = r1
        L30:
            if (r0 == 0) goto L5e
        L32:
            r5.getBitmapAllPixelsColor()     // Catch: java.lang.Throwable -> L46
            java.lang.Boolean r0 = r5.calculateUnAlphaPercent()     // Catch: java.lang.Throwable -> L46
            com.miui.keyguard.editor.utils.CalculateWorker$Companion r2 = com.miui.keyguard.editor.utils.CalculateWorker.Companion     // Catch: java.lang.Throwable -> L44
            java.nio.IntBuffer r4 = r5.bitmapPixelColors     // Catch: java.lang.Throwable -> L44
            r2.recycle(r4)     // Catch: java.lang.Throwable -> L44
            r5.bitmapPixelColors = r1     // Catch: java.lang.Throwable -> L44
        L42:
            r1 = r0
            goto L7b
        L44:
            r2 = move-exception
            goto L48
        L46:
            r2 = move-exception
            r0 = r1
        L48:
            java.lang.String r4 = "checkBitmapInTargetRect: error"
            android.util.Log.e(r3, r4, r2)
            android.graphics.Rect r5 = r5.checkRect
            boolean r5 = r5.isEmpty()
            if (r5 != 0) goto L42
            com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil r5 = com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil.INSTANCE     // Catch: java.lang.Exception -> L42
            java.lang.String r2 = "HierarchyImageAvoidController.checkBitmapInTargetRect_exception"
            r3 = 2
            com.miui.keyguard.editor.utils.KeyguardEditorMiSightUtil.reportHierarchyInvisible$default(r5, r2, r1, r3, r1)     // Catch: java.lang.Exception -> L42
            goto L42
        L5e:
            boolean r5 = r5.isDepthVideo()
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.String r2 = "checkBitmapInTargetRect: bitmap not ready, skip (isDepthVideo="
            r0.append(r2)
            r0.append(r5)
            java.lang.String r5 = ")"
            r0.append(r5)
            java.lang.String r5 = r0.toString()
            android.util.Log.d(r3, r5)
        L7b:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.miui.keyguard.editor.utils.CalculateWorker.checkBitmapInTargetRect():java.lang.Boolean");
    }

    public final boolean checkHaveUnAlphaPixelOnEdge(int i, int i2, int i3) {
        if (this.bitmapPixelColors != null) {
            Rect rect = this.checkRect;
            int iWidth = i3 == 0 ? rect.width() : rect.height();
            for (int i4 = 0; i4 < iWidth; i4++) {
                int iWidth2 = i3 == 0 ? i + i4 : i;
                int i5 = i3 == 1 ? i2 + i4 : i2;
                if (i5 > 0) {
                    iWidth2 += (i5 - 1) * this.checkRect.width();
                }
                IntBuffer intBuffer = this.bitmapPixelColors;
                Intrinsics.checkNotNull(intBuffer);
                if (Color.alpha(intBuffer.get(iWidth2)) > 5) {
                    return true;
                }
            }
        }
        return false;
    }

    public final int checkUnAlphaPixelFromEdge(int i, int i2, int i3) {
        if (this.bitmapPixelColors == null) {
            return 0;
        }
        if (i3 == 0 || i3 == 2) {
            return forEachCheckUnPixelInRect$default(this, i, i2, this.checkRect.width(), this.checkRect.height(), i3 == 0, false, 32, null);
        }
        return forEachCheckUnPixelInRect(i2, i, this.checkRect.height(), this.checkRect.width(), i3 == 1, false);
    }

    public final void confirmAvoidRectLocation(int i, int i2) {
        int i3 = i2 - i;
        this.checkRect.set(this.outerSetRect);
        Rect rect = this.checkRect;
        if (rect.left == -1) {
            rect.offset(1, 0);
            Rect rect2 = this.checkRect;
            rect2.offset((i3 - rect2.width()) / 2, 0);
        }
    }

    public final int findUnAlphaPixelFromEdge() {
        int iWidth = this.checkRect.width() - 1;
        int iHeight = this.checkRect.height() - 1;
        if (checkHaveUnAlphaPixelOnEdge(1, 1, 1)) {
            return checkUnAlphaPixelFromEdge(1, 1, 0);
        }
        if (checkHaveUnAlphaPixelOnEdge(1, 1, 0)) {
            return checkUnAlphaPixelFromEdge(1, 1, 1);
        }
        if (checkHaveUnAlphaPixelOnEdge(iWidth, 1, 1)) {
            return checkUnAlphaPixelFromEdge(iWidth, 1, 2);
        }
        if (checkHaveUnAlphaPixelOnEdge(1, iHeight, 0)) {
            return checkUnAlphaPixelFromEdge(1, iHeight, 3);
        }
        return 0;
    }

    public final int forEachCheckUnPixelInRect(int i, int i2, int i3, int i4, boolean z, boolean z2) {
        if (this.bitmapPixelColors != null) {
            int iWidth = this.checkRect.width() * this.checkRect.height();
            int i5 = isDepthVideo() ? this.videoCanUnAlphaPixelCount : this.imageCanUnAlphaPixelCount;
            int i6 = 0;
            for (int i7 = 0; i7 < i3; i7++) {
                for (int i8 = 0; i8 < i4; i8++) {
                    int iWidth2 = z ? i + i7 : i - i7;
                    int i9 = i2 + i8;
                    if (!z2) {
                        iWidth2 = iWidth2 > 1 ? ((iWidth2 - 1) * this.checkRect.width()) + i9 : i9;
                    } else if (i9 > 1) {
                        iWidth2 += (i9 - 1) * this.checkRect.width();
                    }
                    IntBuffer intBuffer = this.bitmapPixelColors;
                    Intrinsics.checkNotNull(intBuffer);
                    if (Color.alpha(intBuffer.get(iWidth2)) > 5) {
                        i6++;
                    }
                    iWidth--;
                    if (i6 >= i5 || i6 + iWidth < i5) {
                        return i6;
                    }
                }
            }
        }
        return 0;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // java.util.function.Supplier
    public Boolean get() {
        return checkBitmapInTargetRect();
    }

    /* JADX WARN: Code duplicated, block: B:56:0x014d  */
    /* JADX WARN: Code duplicated, block: B:58:0x0162  */
    public final void getBitmapAllPixelsColor() {
        Matrix matrix;
        Matrix matrix2;
        Paint paint;
        Bitmap bitmap;
        int iWidth;
        IntBuffer intBufferObtain;
        Bitmap bitmap2;
        DepthVideoParam depthVideoParam = this.depthVideoParam;
        if (depthVideoParam != null) {
            bitmap = depthVideoParam != null ? depthVideoParam.getBitmap() : null;
            BitmapUtil bitmapUtil = BitmapUtil.INSTANCE;
            Intrinsics.checkNotNull(bitmap);
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            DepthVideoParam depthVideoParam2 = this.depthVideoParam;
            Intrinsics.checkNotNull(depthVideoParam2);
            int showWidth = depthVideoParam2.getShowWidth();
            DepthVideoParam depthVideoParam3 = this.depthVideoParam;
            Intrinsics.checkNotNull(depthVideoParam3);
            Matrix bitmapMatrix = bitmapUtil.getBitmapMatrix(width, height, showWidth, depthVideoParam3.getShowHeight(), ImageView.ScaleType.CENTER_CROP);
            matrix2 = new Matrix();
            matrix2.set(bitmapMatrix);
            paint = this.depthVideoPaint;
            this.depthVideoParam = null;
            this.isDepthVideo = true;
        } else {
            HierarchyImageView hierarchyImageView = (HierarchyImageView) this.hierarchyImageViewRef.get();
            Drawable drawable = hierarchyImageView != null ? hierarchyImageView.getDrawable() : null;
            BitmapDrawable bitmapDrawable = drawable instanceof BitmapDrawable ? (BitmapDrawable) drawable : null;
            Bitmap bitmap3 = bitmapDrawable != null ? bitmapDrawable.getBitmap() : null;
            if (hierarchyImageView == null || (matrix = hierarchyImageView.getImageMatrix()) == null) {
                matrix = new Matrix();
            }
            matrix2 = matrix;
            Bitmap bitmap4 = bitmap3;
            paint = this.imagePaint;
            bitmap = bitmap4;
        }
        boolean z = this.hierarchyImageViewRef.get() != null;
        Integer numValueOf = bitmap != null ? Integer.valueOf(bitmap.getWidth()) : null;
        Integer numValueOf2 = bitmap != null ? Integer.valueOf(bitmap.getHeight()) : null;
        Log.d("HierarchyImageAvoidCutController", "getBitmapAllPixelsColor: hierarchyView=" + z + " hierarchyBitmap w=" + numValueOf + " h=" + numValueOf2 + " hierarchyMatrix=" + matrix2 + " checkRect=" + this.checkRect);
        if (bitmap != null) {
            Bitmap bitmap5 = this.rectBitmap;
            if (bitmap5 == null || bitmap5 == null || this.checkRect.width() != bitmap5.getWidth() || (bitmap2 = this.rectBitmap) == null || this.checkRect.height() != bitmap2.getHeight()) {
                BitmapCreator bitmapCreator = BitmapCreator.INSTANCE;
                int iWidth2 = this.checkRect.width();
                int iHeight = this.checkRect.height();
                Bitmap.Config config = Bitmap.Config.ARGB_8888;
                WideColorGamutManager wideColorGamutManager = WideColorGamutManager.INSTANCE;
                DepthVideoParam depthVideoParam4 = this.depthVideoParam;
                this.rectBitmap = BitmapCreator.createBitmap$default(bitmapCreator, iWidth2, iHeight, config, wideColorGamutManager.isWideColorBitmap(depthVideoParam4 != null ? depthVideoParam4.getBitmap() : null), false, 16, null);
                Bitmap bitmap6 = this.rectBitmap;
                Intrinsics.checkNotNull(bitmap6);
                this.rectCanvas = new Canvas(bitmap6);
            }
            if (this.bitmapPixelColors != null) {
                int iWidth3 = this.checkRect.width() * this.checkRect.height();
                IntBuffer intBuffer = this.bitmapPixelColors;
                Intrinsics.checkNotNull(intBuffer);
                if (iWidth3 > intBuffer.limit()) {
                    iWidth = this.checkRect.width() * this.checkRect.height();
                    intBufferObtain = Companion.obtain(iWidth);
                    if (intBufferObtain == null) {
                        intBufferObtain = IntBuffer.allocate(iWidth);
                    }
                    this.bitmapPixelColors = intBufferObtain;
                }
            } else {
                iWidth = this.checkRect.width() * this.checkRect.height();
                intBufferObtain = Companion.obtain(iWidth);
                if (intBufferObtain == null) {
                    intBufferObtain = IntBuffer.allocate(iWidth);
                }
                this.bitmapPixelColors = intBufferObtain;
            }
            Matrix matrix3 = new Matrix();
            matrix2.invert(matrix3);
            RectF rectF = new RectF(this.checkRect);
            matrix3.mapRect(rectF);
            IntBuffer intBuffer2 = this.bitmapPixelColors;
            Intrinsics.checkNotNull(intBuffer2);
            intBuffer2.clear();
            Bitmap bitmap7 = this.rectBitmap;
            int width2 = bitmap7 != null ? bitmap7.getWidth() : 0;
            Bitmap bitmap8 = this.rectBitmap;
            Rect rect = new Rect(0, 0, width2, bitmap8 != null ? bitmap8.getHeight() : 0);
            Canvas canvas = this.rectCanvas;
            if (canvas != null) {
                canvas.drawColor(0, PorterDuff.Mode.CLEAR);
            }
            Canvas canvas2 = this.rectCanvas;
            if (canvas2 != null) {
                Rect rect2 = new Rect();
                rectF.roundOut(rect2);
                canvas2.drawBitmap(bitmap, rect2, rect, paint);
            }
            Bitmap bitmap9 = this.rectBitmap;
            if (bitmap9 != null) {
                IntBuffer intBuffer3 = this.bitmapPixelColors;
                Intrinsics.checkNotNull(intBuffer3);
                bitmap9.copyPixelsToBuffer(intBuffer3);
            }
            Log.d("HierarchyImageAvoidCutController", "getBitmapAllPixelsColor:  bitmapInvertRect=" + rectF + " bitmapInvertMatrix=" + matrix3 + " dstRect=" + rect);
        }
    }

    public final boolean getShouldCheck() {
        return this.shouldCheck;
    }

    public final boolean isDepthVideo() {
        return this.isDepthVideo || this.depthVideoParam != null;
    }

    public final boolean isWorking() {
        return this.isWorking;
    }

    public final void onImageLocationChange(int i, int i2, int i3, int i4) {
        confirmAvoidRectLocation(i, i3);
    }

    public final void reset() {
        this.depthVideoParam = null;
        this.isDepthVideo = false;
    }

    public final void setDepthVideoCheckParam(DepthVideoParam param) {
        Intrinsics.checkNotNullParameter(param, "param");
        Log.d("HierarchyImageAvoidCutController", "setDepthVideoCheckParam: param=" + param + " config=" + param.getBitmap().getConfig() + " w=" + param.getBitmap().getWidth() + " h=" + param.getBitmap().getHeight());
        this.depthVideoParam = param;
    }

    public final void setUpAvoidRect(Rect rect) {
        Intrinsics.checkNotNullParameter(rect, "rect");
        this.outerSetRect.set(rect);
        double dWidth = this.outerSetRect.width() * this.outerSetRect.height();
        double d = IMAGE_AVOID_RATE;
        this.imageCanUnAlphaPixelCount = (int) (dWidth * d);
        double d2 = VIDEO_AVOID_RATE;
        this.videoCanUnAlphaPixelCount = (int) (dWidth * d2);
        this.shouldCheck = this.outerSetRect.width() > 0 && this.outerSetRect.height() > 0;
        HierarchyAvoidDebugLogger.logSetUpAvoidRect(this.outerSetRect, this.imageCanUnAlphaPixelCount, this.videoCanUnAlphaPixelCount, d, d2);
    }

    public final void setWorking(boolean z) {
        this.isWorking = z;
    }
}
