package com.miui.aod.components.view;

import android.animation.Animator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import com.miui.aod.R;
import com.miui.aod.category.ImageSelectorCategoryInfo;
import com.miui.aod.components.photo.Edit.EventHandler;
import com.miui.aod.components.photo.Edit.StrokeImageHelper;
import com.miui.aod.components.photo.Edit.TranslateEvaluator;
import com.miui.aod.components.photo.OneShotAnimateListener;
import com.miui.aod.util.FileUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
public class EditorView extends View {
    private static final float MIN_PADDING = 80.0f;
    private Button mApplyButton;
    private Bitmap mBitmap;
    private final RectF mBitmapBounds;
    private final RectF mBitmapDisplayBounds;
    public Matrix mBitmapToCanvas;
    private final RectF mCanvasBounds;
    private final Matrix mCanvasMatrix;
    private boolean mDrawBoundLine;
    private EventHandler mEventHandler;
    private final Matrix mInitialMatrix;
    private final Matrix mOriginalBitmapToCanvas;
    private final Paint mPaint;
    private Plugin mPlugin;
    private StrokeImageHelper mStrokeImageHelper;

    public static class ImageClipTask implements Runnable {
        public final Bitmap mBitmap;
        public final Matrix mBitmapToCanvas;
        public final RectF mCanvasBounds;
        public final String mDir;

        public ImageClipTask(String str, Bitmap bitmap, RectF rectF, Matrix matrix) {
            this.mDir = str;
            this.mBitmap = bitmap;
            this.mCanvasBounds = rectF;
            this.mBitmapToCanvas = matrix;
        }

        @Override // java.lang.Runnable
        public void run() {
            EditorView.clip(this.mDir, this.mBitmap, this.mCanvasBounds, this.mBitmapToCanvas);
        }
    }

    public static abstract class Plugin {
        private static final String ANIMATOR_MATRIX = "matrix";
        protected static final int DEFAULT_ANIMATOR_DURATION = 300;
        private EditorView mEditorView;
        private EventHandler mEventHandler;
        protected ValueAnimator mImageAnimator;
        private PropertyValuesHolder mTransValues;
        private final ValueAnimator.AnimatorUpdateListener mTranslateUpdateListener = new ValueAnimator.AnimatorUpdateListener() { // from class: com.miui.aod.components.view.EditorView.Plugin.1
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Plugin.this.getImageMatrix().set((Matrix) Plugin.this.mImageAnimator.getAnimatedValue());
                Plugin.this.invalidate();
            }
        };

        public interface BoundsFixCallback {
            void onDone();
        }

        public static class BoundsFixListener extends OneShotAnimateListener {
            private final BoundsFixCallback mCallback;

            public BoundsFixListener(BoundsFixCallback boundsFixCallback) {
                this.mCallback = boundsFixCallback;
            }

            @Override // com.miui.aod.components.photo.OneShotAnimateListener, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                this.mCallback.onDone();
            }
        }

        private void ensureStarted() {
            if (this.mEditorView == null) {
                throw new IllegalStateException("Current plugin is not installed.");
            }
        }

        public static float resolveScale(RectF rectF, RectF rectF2) {
            float fWidth = rectF2.width() > rectF.width() ? rectF2.width() / rectF.width() : 1.0f;
            return rectF2.height() > rectF.height() ? Math.max(fWidth, rectF2.height() / rectF.height()) : fWidth;
        }

        /* JADX WARN: Code duplicated, block: B:6:0x000f A[PHI: r0 r2
  0x000f: PHI (r0v5 float) = (r0v1 float), (r0v2 float) binds: [B:5:0x000d, B:8:0x0017] A[DONT_GENERATE, DONT_INLINE]
  0x000f: PHI (r2v4 float) = (r2v0 float), (r2v1 float) binds: [B:5:0x000d, B:8:0x0017] A[DONT_GENERATE, DONT_INLINE]] */
        public static void resolveTranslate(RectF rectF, RectF rectF2, PointF pointF) {
            float f;
            float f2 = 0.0f;
            if (rectF.contains(rectF2)) {
                pointF.x = 0.0f;
                pointF.y = 0.0f;
                return;
            }
            float f3 = rectF2.top;
            float f4 = rectF.top;
            if (f3 < f4) {
                f = f3 - f4;
            } else {
                f3 = rectF2.bottom;
                f4 = rectF.bottom;
                if (f3 > f4) {
                    f = f3 - f4;
                } else {
                    f = 0.0f;
                }
            }
            float f5 = rectF2.left;
            float f6 = rectF.left;
            if (f5 < f6) {
                f2 = f5 - f6;
            } else {
                float f7 = rectF2.right;
                float f8 = rectF.right;
                if (f7 > f8) {
                    f2 = f7 - f8;
                }
            }
            pointF.x = f2;
            pointF.y = f;
        }

        public void config(Canvas canvas) {
        }

        public boolean draw(Canvas canvas) {
            return false;
        }

        public void drawOverlay(Canvas canvas) {
        }

        public void fixImageBounds(RectF rectF, BoundsFixCallback boundsFixCallback) {
            float f;
            float fWidth;
            Matrix imageMatrix = getImageMatrix();
            RectF imageBounds = getImageBounds();
            RectF rectF2 = new RectF();
            imageMatrix.mapRect(rectF2, imageBounds);
            RectF rectF3 = new RectF();
            rectF3.set(rectF);
            float f2 = 0.0f;
            if (rectF3.intersect(rectF2)) {
                if (rectF3.width() < EditorView.MIN_PADDING) {
                    fWidth = rectF3.left == rectF2.left ? rectF3.width() - EditorView.MIN_PADDING : EditorView.MIN_PADDING - rectF3.width();
                } else {
                    fWidth = 0.0f;
                }
                if (rectF3.height() < EditorView.MIN_PADDING) {
                    float f3 = rectF3.top;
                    float f4 = rectF2.top;
                    float fHeight = rectF3.height();
                    f2 = f3 == f4 ? fHeight - EditorView.MIN_PADDING : EditorView.MIN_PADDING - fHeight;
                }
            } else {
                float f5 = rectF2.left;
                float f6 = rectF.right;
                if (f5 >= f6) {
                    f = (f6 - f5) - EditorView.MIN_PADDING;
                } else {
                    float f7 = rectF2.right;
                    float f8 = rectF.left;
                    f = f7 - f8 <= EditorView.MIN_PADDING ? (f8 - f7) + EditorView.MIN_PADDING : 0.0f;
                }
                float f9 = rectF2.top;
                float f10 = rectF.bottom;
                if (f9 >= f10) {
                    f2 = (f10 - f9) - EditorView.MIN_PADDING;
                } else {
                    float f11 = rectF2.bottom;
                    float f12 = rectF.top;
                    if (f11 - f12 <= EditorView.MIN_PADDING) {
                        f2 = (f12 - f11) + EditorView.MIN_PADDING;
                    }
                }
                fWidth = f;
            }
            Matrix matrix = new Matrix(getImageMatrix());
            Matrix matrix2 = new Matrix(getImageMatrix());
            matrix2.postTranslate(fWidth, f2);
            setupImageAnimator(matrix, matrix2, null);
            this.mImageAnimator.start();
        }

        public final RectF getBounds() {
            ensureStarted();
            return this.mEditorView.mCanvasBounds;
        }

        public final Context getContext() {
            return this.mEditorView.getContext();
        }

        public final EventHandler getEventHandler() {
            ensureStarted();
            return this.mEventHandler;
        }

        public final Bitmap getImage() {
            return this.mEditorView.mBitmap;
        }

        public final RectF getImageBounds() {
            ensureStarted();
            return this.mEditorView.mBitmapBounds;
        }

        public final RectF getImageDisplayBounds() {
            ensureStarted();
            return this.mEditorView.mBitmapDisplayBounds;
        }

        public final Matrix getImageMatrix() {
            ensureStarted();
            return this.mEditorView.mBitmapToCanvas;
        }

        public final RectF getTouchBounds() {
            ensureStarted();
            return new RectF(this.mEditorView.getLeft(), this.mEditorView.getTop(), this.mEditorView.getRight(), this.mEditorView.getBottom());
        }

        public final void invalidate() {
            ensureStarted();
            this.mEditorView.invalidate();
        }

        public void onMatrixChanged() {
        }

        public abstract void onStart();

        public abstract void onStop();

        public abstract boolean onTouchEvent(MotionEvent motionEvent);

        public abstract void setCropable(boolean z);

        public void setupImageAnimator(Matrix matrix, Matrix matrix2, OneShotAnimateListener oneShotAnimateListener) {
            if (this.mImageAnimator == null) {
                this.mImageAnimator = new ValueAnimator();
                PropertyValuesHolder propertyValuesHolderOfObject = PropertyValuesHolder.ofObject(ANIMATOR_MATRIX, new TranslateEvaluator(), matrix, matrix2);
                this.mTransValues = propertyValuesHolderOfObject;
                this.mImageAnimator.setObjectValues(propertyValuesHolderOfObject);
                this.mImageAnimator.addUpdateListener(this.mTranslateUpdateListener);
            }
            this.mTransValues.setObjectValues(matrix, matrix2);
            this.mImageAnimator.setDuration(300L);
            this.mImageAnimator.setValues(this.mTransValues);
            if (oneShotAnimateListener != null) {
                this.mImageAnimator.addListener(oneShotAnimateListener);
            }
        }

        public void start() {
            onStart();
            invalidate();
        }

        public void stop() {
            onStop();
            invalidate();
        }
    }

    public EditorView(Context context) {
        super(context);
        this.mCanvasMatrix = new Matrix();
        this.mInitialMatrix = new Matrix();
        this.mCanvasBounds = new RectF();
        this.mBitmapBounds = new RectF();
        this.mBitmapDisplayBounds = new RectF();
        this.mBitmapToCanvas = new Matrix();
        this.mOriginalBitmapToCanvas = new Matrix();
        this.mPlugin = null;
        this.mPaint = new Paint(7);
        this.mDrawBoundLine = true;
        initialize();
        setBackground(null);
    }

    public EditorView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EditorView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mCanvasMatrix = new Matrix();
        this.mInitialMatrix = new Matrix();
        this.mCanvasBounds = new RectF();
        this.mBitmapBounds = new RectF();
        this.mBitmapDisplayBounds = new RectF();
        this.mBitmapToCanvas = new Matrix();
        this.mOriginalBitmapToCanvas = new Matrix();
        this.mPlugin = null;
        this.mPaint = new Paint(7);
        this.mDrawBoundLine = true;
        initialize();
        setBackground(null);
    }

    public static void clip(String str, Bitmap bitmap, RectF rectF, Matrix matrix) {
        RectF rectF2 = new RectF(0.0f, 0.0f, rectF.width(), rectF.height());
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap((int) rectF2.width(), (int) rectF2.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint(2);
        paint.setColor(-16777216);
        canvas.drawRect(rectF2, paint);
        Matrix matrix2 = new Matrix();
        matrix2.setRectToRect(rectF, rectF2, Matrix.ScaleToFit.CENTER);
        Matrix matrix3 = new Matrix(matrix);
        matrix3.postConcat(matrix2);
        canvas.drawBitmap(bitmap, matrix3, paint);
        File file = new File(str);
        if (file.exists()) {
            file.delete();
        }
        FileUtils.makeSureFileExist(file);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            try {
                bitmapCreateBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.close();
            } catch (Throwable th) {
                try {
                    fileOutputStream.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initialize() {
        this.mEventHandler = new EventHandler(Looper.getMainLooper());
        this.mStrokeImageHelper = new StrokeImageHelper(getContext());
    }

    private void notifyMatrixChanged() {
        Plugin plugin = this.mPlugin;
        if (plugin != null) {
            plugin.onMatrixChanged();
        }
    }

    private void resetMatrix() {
        if (this.mCanvasBounds.isEmpty()) {
            return;
        }
        if (this.mInitialMatrix.isIdentity()) {
            this.mBitmapToCanvas.setRectToRect(this.mBitmapBounds, this.mCanvasBounds, Matrix.ScaleToFit.CENTER);
        } else {
            this.mBitmapToCanvas.set(this.mInitialMatrix);
        }
        this.mBitmapDisplayBounds.set(this.mBitmapBounds);
        this.mBitmapToCanvas.mapRect(this.mBitmapDisplayBounds, this.mBitmapBounds);
        this.mOriginalBitmapToCanvas.set(this.mBitmapToCanvas);
        notifyMatrixChanged();
    }

    /* JADX WARN: Code duplicated, block: B:34:0x00b5 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v5, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v8, types: [java.io.OutputStream] */
    @SuppressLint({"WrongThread"})
    public File clip() throws Throwable {
        Throwable th;
        FileOutputStream fileOutputStream;
        RectF rectF = new RectF(0.0f, 0.0f, this.mCanvasBounds.width(), this.mCanvasBounds.height());
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap((int) rectF.width(), (int) rectF.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmapCreateBitmap);
        Paint paint = new Paint(2);
        paint.setColor(-16777216);
        canvas.drawRect(rectF, paint);
        Matrix matrix = new Matrix();
        matrix.setRectToRect(this.mCanvasBounds, rectF, Matrix.ScaleToFit.CENTER);
        Matrix matrix2 = new Matrix(this.mBitmapToCanvas);
        matrix2.postConcat(matrix);
        canvas.drawBitmap(this.mBitmap, matrix2, paint);
        ?? sb = new StringBuilder();
        sb.append(ImageSelectorCategoryInfo.getImageCacheDir(getContext()));
        sb.append(File.separator);
        sb.append(FileUtils.generateRandomFilename());
        File file = new File(sb.toString());
        if (file.exists()) {
            file.delete();
        }
        FileUtils.makeSureFileExist(file);
        try {
            try {
                fileOutputStream = new FileOutputStream(file);
                try {
                    bitmapCreateBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                    try {
                        fileOutputStream.close();
                        return file;
                    } catch (IOException e) {
                        Log.e("EditorView", "error...", e);
                        return file;
                    }
                } catch (IOException e2) {
                    e = e2;
                    e.printStackTrace();
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                        } catch (IOException e3) {
                            Log.e("EditorView", "error...", e3);
                        }
                    }
                    return null;
                }
            } catch (Throwable th2) {
                th = th2;
                if (sb != 0) {
                    try {
                        sb.close();
                    } catch (IOException e4) {
                        Log.e("EditorView", "error...", e4);
                    }
                }
                throw th;
            }
        } catch (IOException e5) {
            e = e5;
            fileOutputStream = null;
        } catch (Throwable th3) {
            sb = 0;
            th = th3;
            if (sb != 0) {
                sb.close();
            }
            throw th;
        }
    }

    public ImageClipTask createClipTask(Context context) {
        return new ImageClipTask(ImageSelectorCategoryInfo.getImageCacheDir(context) + File.separator + FileUtils.generateRandomFilename(), this.mBitmap, this.mCanvasBounds, this.mBitmapToCanvas);
    }

    public Bitmap getBitmap() {
        return this.mBitmap;
    }

    public void install(Plugin plugin) {
        plugin.mEditorView = this;
        plugin.mEventHandler = this.mEventHandler;
        this.mPlugin = plugin;
    }

    public boolean isChanged() {
        return !this.mBitmapToCanvas.equals(this.mOriginalBitmapToCanvas);
    }

    @Override // android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mApplyButton = (Button) getRootView().findViewById(R.id.style_select_apply_btn);
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        Bitmap bitmap;
        super.onDraw(canvas);
        int saveCount = canvas.getSaveCount();
        Plugin plugin = this.mPlugin;
        if (plugin != null) {
            plugin.config(canvas);
        }
        Plugin plugin2 = this.mPlugin;
        if ((plugin2 == null || !plugin2.draw(canvas)) && (bitmap = this.mBitmap) != null) {
            canvas.drawBitmap(bitmap, this.mBitmapToCanvas, this.mPaint);
        }
        Plugin plugin3 = this.mPlugin;
        if (plugin3 != null) {
            plugin3.drawOverlay(canvas);
        }
        if (this.mDrawBoundLine) {
            this.mStrokeImageHelper.draw(canvas, this.mBitmapBounds, this.mBitmapToCanvas);
        }
        canvas.restoreToCount(saveCount);
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        if (View.MeasureSpec.getMode(i) == Integer.MIN_VALUE && View.MeasureSpec.getMode(i2) == Integer.MIN_VALUE) {
            int size = View.MeasureSpec.getSize(i);
            int size2 = View.MeasureSpec.getSize(i2);
            Bitmap bitmap = this.mBitmap;
            if (bitmap != null) {
                float f = size;
                float width = f / bitmap.getWidth();
                float f2 = size2;
                float height = f2 / this.mBitmap.getHeight();
                if (width > height) {
                    size = (int) ((f * height) / width);
                } else {
                    size2 = (int) ((f2 * width) / height);
                }
            }
            setMeasuredDimension(size, size2);
            return;
        }
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        int width2 = paddingLeft + paddingRight;
        Bitmap bitmap2 = this.mBitmap;
        if (bitmap2 != null) {
            width2 += bitmap2.getWidth();
        }
        int height2 = paddingTop + paddingBottom;
        Bitmap bitmap3 = this.mBitmap;
        if (bitmap3 != null) {
            height2 += bitmap3.getHeight();
        }
        setMeasuredDimension(View.resolveSizeAndState(Math.max(width2, getSuggestedMinimumWidth()), i, 0), View.resolveSizeAndState(Math.max(height2, getSuggestedMinimumHeight()), i2, 0));
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getActionMasked() == 0) {
            this.mApplyButton.setEnabled(false);
        } else if (motionEvent.getActionMasked() == 1 || motionEvent.getActionMasked() == 3) {
            this.mApplyButton.setEnabled(this.mBitmap != null);
        }
        Plugin plugin = this.mPlugin;
        return plugin != null && plugin.onTouchEvent(motionEvent);
    }

    public void reset() {
        this.mBitmap = null;
        this.mCanvasBounds.setEmpty();
        this.mBitmapToCanvas.reset();
        this.mBitmapBounds.setEmpty();
        this.mBitmapDisplayBounds.setEmpty();
    }

    public void setBitmap(Bitmap bitmap, Matrix matrix) {
        this.mInitialMatrix.reset();
        if (matrix != null && !matrix.isIdentity()) {
            this.mInitialMatrix.set(matrix);
        }
        this.mBitmap = bitmap;
        if (bitmap == null) {
            RectF rectF = this.mBitmapBounds;
            rectF.right = 0.0f;
            rectF.bottom = 0.0f;
        } else {
            this.mBitmapBounds.right = bitmap.getWidth();
            this.mBitmapBounds.bottom = bitmap.getHeight();
        }
        resetMatrix();
        invalidate();
    }

    public void setCanvasBounds(RectF rectF) {
        this.mCanvasMatrix.reset();
        this.mCanvasBounds.set(rectF);
        resetMatrix();
        invalidate();
    }

    public void setCropable(boolean z) {
        this.mPlugin.setCropable(z);
    }

    public void setDrawBoundLine(boolean z) {
        this.mDrawBoundLine = z;
    }

    public void translateCanvasBounds(float f) {
        if (this.mCanvasBounds.isEmpty()) {
            return;
        }
        this.mCanvasMatrix.reset();
        this.mCanvasMatrix.postTranslate(0.0f, f);
        this.mCanvasMatrix.mapRect(this.mCanvasBounds);
        this.mBitmapToCanvas.postConcat(this.mCanvasMatrix);
        notifyMatrixChanged();
        invalidate();
        post(new Runnable() { // from class: com.miui.aod.components.view.EditorView$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                EditorView editorView = this.f$0;
                editorView.mPlugin.fixImageBounds(editorView.mCanvasBounds, null);
            }
        });
    }

    public void translateCanvasBounds(RectF rectF) {
        if (this.mCanvasBounds.isEmpty()) {
            return;
        }
        Matrix matrix = new Matrix();
        matrix.setRectToRect(this.mCanvasBounds, rectF, Matrix.ScaleToFit.FILL);
        matrix.mapRect(this.mCanvasBounds);
        this.mBitmapToCanvas.postConcat(matrix);
        notifyMatrixChanged();
        invalidate();
        post(new Runnable() { // from class: com.miui.aod.components.view.EditorView$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                EditorView editorView = this.f$0;
                editorView.mPlugin.fixImageBounds(editorView.mCanvasBounds, null);
            }
        });
    }

    public void uninstall(Plugin plugin) {
        plugin.mEditorView = null;
        plugin.mEventHandler = null;
        this.mPlugin = null;
        invalidate();
    }
}
