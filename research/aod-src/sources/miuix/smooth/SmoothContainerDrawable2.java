package miuix.smooth;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes2.dex */
public class SmoothContainerDrawable2 extends Drawable implements Drawable.Callback {
    public Path mClipPath;
    public ContainerState mContainerState;
    public RectF mLayer;
    public float[] mRadii;
    public float mRadius;
    public int mStrokeColor;
    public Paint mStrokePaint;
    public int mStrokeWidth;
    public float[] mTempRadii;
    public boolean mUseSmooth;

    public static class ChildDrawableWrapper {
        Drawable mDrawable;

        public ChildDrawableWrapper() {
            this.mDrawable = new GradientDrawable();
        }

        public ChildDrawableWrapper(@NonNull ChildDrawableWrapper childDrawableWrapper, @NonNull SmoothContainerDrawable2 smoothContainerDrawable2, @Nullable Resources resources, @Nullable Resources.Theme theme) {
            Drawable drawableNewDrawable;
            Drawable drawable = childDrawableWrapper.mDrawable;
            if (drawable != null) {
                Drawable.ConstantState constantState = drawable.getConstantState();
                drawableNewDrawable = constantState == null ? drawable : resources == null ? constantState.newDrawable() : theme == null ? constantState.newDrawable(resources) : constantState.newDrawable(resources, theme);
                drawableNewDrawable.setLayoutDirection(drawable.getLayoutDirection());
                drawableNewDrawable.setBounds(drawable.getBounds());
                drawableNewDrawable.setLevel(drawable.getLevel());
                drawableNewDrawable.setCallback(smoothContainerDrawable2);
            } else {
                drawableNewDrawable = null;
            }
            this.mDrawable = drawableNewDrawable;
        }
    }

    public static final class ContainerState extends Drawable.ConstantState {
        ChildDrawableWrapper mChildDrawableWrapper;
        int mLayerType;
        float[] mRadii;
        float mRadius;
        int mStrokeColor;
        int mStrokeWidth;
        boolean mUseSmooth;

        public ContainerState() {
            this.mLayerType = 0;
            this.mChildDrawableWrapper = new ChildDrawableWrapper();
        }

        public ContainerState(@NonNull ContainerState containerState, @NonNull SmoothContainerDrawable2 smoothContainerDrawable2, @Nullable Resources resources, @Nullable Resources.Theme theme) {
            this.mLayerType = 0;
            this.mChildDrawableWrapper = new ChildDrawableWrapper(containerState.mChildDrawableWrapper, smoothContainerDrawable2, resources, theme);
            this.mRadius = containerState.mRadius;
            this.mRadii = containerState.mRadii;
            this.mStrokeWidth = containerState.mStrokeWidth;
            this.mStrokeColor = containerState.mStrokeColor;
            this.mLayerType = containerState.mLayerType;
            this.mUseSmooth = containerState.mUseSmooth;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return true;
        }

        public int getAlpha() {
            return this.mChildDrawableWrapper.mDrawable.getAlpha();
        }

        public Rect getBounds() {
            return this.mChildDrawableWrapper.mDrawable.getBounds();
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mChildDrawableWrapper.mDrawable.getChangingConfigurations();
        }

        public Rect getDirtyBounds() {
            return this.mChildDrawableWrapper.mDrawable.getDirtyBounds();
        }

        public int getIntrinsicHeight() {
            return this.mChildDrawableWrapper.mDrawable.getIntrinsicHeight();
        }

        public int getIntrinsicWidth() {
            return this.mChildDrawableWrapper.mDrawable.getIntrinsicWidth();
        }

        public int getOpacity() {
            return this.mChildDrawableWrapper.mDrawable.getOpacity();
        }

        public boolean getPadding(Rect rect) {
            return this.mChildDrawableWrapper.mDrawable.getPadding(rect);
        }

        public final boolean isStateful() {
            return this.mChildDrawableWrapper.mDrawable.isStateful();
        }

        public void jumpToCurrentState() {
            this.mChildDrawableWrapper.mDrawable.jumpToCurrentState();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        public Drawable newDrawable() {
            return new SmoothContainerDrawable2(null, 0 == true ? 1 : 0, this);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        public Drawable newDrawable(@Nullable Resources resources) {
            return new SmoothContainerDrawable2(resources, null, this);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        @NonNull
        public Drawable newDrawable(@Nullable Resources resources, @Nullable Resources.Theme theme) {
            return new SmoothContainerDrawable2(resources, theme, this);
        }

        public void onBoundsChange(Rect rect) {
            this.mChildDrawableWrapper.mDrawable.setBounds(rect);
        }

        public boolean onStateChange(int[] iArr) {
            return isStateful() && this.mChildDrawableWrapper.mDrawable.setState(iArr);
        }

        public void setAlpha(int i) {
            this.mChildDrawableWrapper.mDrawable.setAlpha(i);
            this.mChildDrawableWrapper.mDrawable.invalidateSelf();
        }

        public void setBounds(int i, int i2, int i3, int i4) {
            this.mChildDrawableWrapper.mDrawable.setBounds(i, i2, i3, i4);
        }

        public void setBounds(Rect rect) {
            this.mChildDrawableWrapper.mDrawable.setBounds(rect);
        }

        public void setChangingConfigurations(int i) {
            this.mChildDrawableWrapper.mDrawable.setChangingConfigurations(i);
        }

        public void setColorFilter(ColorFilter colorFilter) {
            this.mChildDrawableWrapper.mDrawable.setColorFilter(colorFilter);
        }

        public void setDither(boolean z) {
            this.mChildDrawableWrapper.mDrawable.setDither(z);
        }

        public void setFilterBitmap(boolean z) {
            this.mChildDrawableWrapper.mDrawable.setFilterBitmap(z);
        }
    }

    public SmoothContainerDrawable2() {
        this.mLayer = new RectF();
        this.mClipPath = new Path();
        this.mContainerState = new ContainerState();
        init();
    }

    private SmoothContainerDrawable2(Resources resources, Resources.Theme theme, ContainerState containerState) {
        this.mLayer = new RectF();
        this.mClipPath = new Path();
        this.mContainerState = new ContainerState(containerState, this, resources, theme);
        this.mStrokeWidth = containerState.mStrokeWidth;
        this.mStrokeColor = containerState.mStrokeColor;
        this.mRadii = containerState.mRadii;
        this.mRadius = containerState.mRadius;
        boolean z = containerState.mUseSmooth;
        this.mUseSmooth = z;
        setSmoothCornerEnable(z);
        init();
    }

    public static TypedArray obtainAttributes(Resources resources, Resources.Theme theme, AttributeSet attributeSet, int[] iArr) {
        return theme == null ? resources.obtainAttributes(attributeSet, iArr) : theme.obtainStyledAttributes(attributeSet, iArr, 0, 0);
    }

    @Override // android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        this.mContainerState.mChildDrawableWrapper.mDrawable.applyTheme(theme);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        ContainerState containerState = this.mContainerState;
        return (containerState != null && containerState.canApplyTheme()) || super.canApplyTheme();
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        drawRoundRect(canvas);
    }

    public final void drawRoundRect(Canvas canvas) {
        Rect bounds = getBounds();
        int iSave = canvas.save();
        this.mClipPath.reset();
        RectF rectF = this.mLayer;
        rectF.left = bounds.left;
        rectF.top = bounds.top;
        rectF.right = bounds.right;
        rectF.bottom = bounds.bottom;
        float[] fArr = this.mRadii;
        if (fArr == null) {
            Path path = this.mClipPath;
            float f = this.mRadius;
            path.addRoundRect(rectF, f, f, Path.Direction.CW);
        } else {
            this.mClipPath.addRoundRect(rectF, fArr, Path.Direction.CW);
        }
        canvas.clipPath(this.mClipPath);
        int i = this.mStrokeWidth;
        float f2 = i * 0.5f;
        if (i != 0) {
            int iSave2 = canvas.save();
            this.mLayer.inset(f2, f2);
            this.mClipPath.reset();
            float[] fArr2 = this.mRadii;
            if (fArr2 == null) {
                Path path2 = this.mClipPath;
                RectF rectF2 = this.mLayer;
                float f3 = this.mRadius;
                path2.addRoundRect(rectF2, f3 - f2, f3 - f2, Path.Direction.CW);
            } else {
                float[] fArr3 = (float[]) fArr2.clone();
                this.mTempRadii = fArr3;
                float[] fArr4 = this.mRadii;
                fArr3[0] = fArr4[0] - f2;
                fArr3[1] = fArr4[1] - f2;
                fArr3[2] = fArr4[2] - f2;
                fArr3[3] = fArr4[3] - f2;
                this.mClipPath.addRoundRect(this.mLayer, fArr3, Path.Direction.CCW);
            }
            canvas.clipPath(this.mClipPath);
            this.mContainerState.mChildDrawableWrapper.mDrawable.draw(canvas);
            canvas.restoreToCount(iSave2);
            canvas.drawPath(this.mClipPath, this.mStrokePaint);
        } else {
            this.mContainerState.mChildDrawableWrapper.mDrawable.draw(canvas);
        }
        canvas.restoreToCount(iSave);
    }

    @Override // android.graphics.drawable.Drawable
    public int getAlpha() {
        return this.mContainerState.getAlpha();
    }

    public final Rect getBoundsInner() {
        return this.mContainerState.getBounds();
    }

    public Drawable getChildDrawable() {
        ContainerState containerState = this.mContainerState;
        if (containerState != null) {
            return containerState.mChildDrawableWrapper.mDrawable;
        }
        return null;
    }

    @Override // android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        return this.mContainerState;
    }

    public float getCornerRadius() {
        return this.mContainerState.mRadius;
    }

    @Override // android.graphics.drawable.Drawable
    public Rect getDirtyBounds() {
        return this.mContainerState.getDirtyBounds();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicHeight() {
        return this.mContainerState.getIntrinsicHeight();
    }

    @Override // android.graphics.drawable.Drawable
    public int getIntrinsicWidth() {
        return this.mContainerState.getIntrinsicWidth();
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return this.mContainerState.getOpacity();
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        outline.setRoundRect(getBoundsInner(), getCornerRadius());
    }

    @Override // android.graphics.drawable.Drawable
    public boolean getPadding(Rect rect) {
        return this.mContainerState.getPadding(rect);
    }

    @Override // android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        TypedArray typedArrayObtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.MiuixSmoothContainerDrawable2);
        setCornerRadius(typedArrayObtainAttributes.getDimensionPixelSize(R.styleable.MiuixSmoothContainerDrawable2_android_radius, 0));
        int i = R.styleable.MiuixSmoothContainerDrawable2_android_topLeftRadius;
        if (typedArrayObtainAttributes.hasValue(i) || typedArrayObtainAttributes.hasValue(R.styleable.MiuixSmoothContainerDrawable2_android_topRightRadius) || typedArrayObtainAttributes.hasValue(R.styleable.MiuixSmoothContainerDrawable2_android_bottomRightRadius) || typedArrayObtainAttributes.hasValue(R.styleable.MiuixSmoothContainerDrawable2_android_bottomLeftRadius)) {
            float dimensionPixelSize = typedArrayObtainAttributes.getDimensionPixelSize(i, 0);
            float dimensionPixelSize2 = typedArrayObtainAttributes.getDimensionPixelSize(R.styleable.MiuixSmoothContainerDrawable2_android_topRightRadius, 0);
            float dimensionPixelSize3 = typedArrayObtainAttributes.getDimensionPixelSize(R.styleable.MiuixSmoothContainerDrawable2_android_bottomRightRadius, 0);
            float dimensionPixelSize4 = typedArrayObtainAttributes.getDimensionPixelSize(R.styleable.MiuixSmoothContainerDrawable2_android_bottomLeftRadius, 0);
            setCornerRadii(new float[]{dimensionPixelSize, dimensionPixelSize, dimensionPixelSize2, dimensionPixelSize2, dimensionPixelSize3, dimensionPixelSize3, dimensionPixelSize4, dimensionPixelSize4});
        }
        setStrokeWidth(typedArrayObtainAttributes.getDimensionPixelSize(R.styleable.MiuixSmoothContainerDrawable2_miuix_strokeWidth, 0));
        setStrokeColor(typedArrayObtainAttributes.getColor(R.styleable.MiuixSmoothContainerDrawable2_miuix_strokeColor, 0));
        setLayerType(typedArrayObtainAttributes.getInt(R.styleable.MiuixSmoothContainerDrawable2_android_layerType, 0));
        this.mUseSmooth = typedArrayObtainAttributes.getBoolean(R.styleable.MiuixSmoothContainerDrawable2_miuix_useSmooth, true);
        Boolean bool = SmoothCornerHelper.FORCE_USE_SMOOTH;
        if (bool != null) {
            this.mUseSmooth = bool.booleanValue();
        }
        if (this.mUseSmooth) {
            setSmoothCornerEnable(true);
        }
        typedArrayObtainAttributes.recycle();
        inflateInnerDrawable(resources, xmlPullParser, attributeSet, theme);
    }

    public final void inflateInnerDrawable(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int next;
        int depth = xmlPullParser.getDepth() + 1;
        while (true) {
            int next2 = xmlPullParser.next();
            if (next2 == 1) {
                return;
            }
            int depth2 = xmlPullParser.getDepth();
            if (depth2 < depth && next2 == 3) {
                return;
            }
            if (next2 == 2 && depth2 <= depth && xmlPullParser.getName().equals("child")) {
                do {
                    next = xmlPullParser.next();
                } while (next == 4);
                if (next != 2) {
                    throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": <child> tag requires a 'drawable' attribute or child tag defining a drawable");
                }
                ChildDrawableWrapper childDrawableWrapper = new ChildDrawableWrapper();
                Drawable drawableCreateFromXmlInner = Drawable.createFromXmlInner(resources, xmlPullParser, attributeSet, theme);
                childDrawableWrapper.mDrawable = drawableCreateFromXmlInner;
                drawableCreateFromXmlInner.setCallback(this);
                this.mContainerState.mChildDrawableWrapper = childDrawableWrapper;
                return;
            }
        }
    }

    public final void init() {
        Paint paint = new Paint(1);
        this.mStrokePaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.mStrokePaint.setColor(this.mStrokeColor);
        this.mStrokePaint.setStrokeWidth(this.mStrokeWidth);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        return this.mContainerState.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public void jumpToCurrentState() {
        this.mContainerState.jumpToCurrentState();
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        this.mContainerState.onBoundsChange(rect);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onStateChange(int[] iArr) {
        return this.mContainerState.onStateChange(iArr);
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void scheduleDrawable(Drawable drawable, Runnable runnable, long j) {
        scheduleSelf(runnable, j);
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        this.mContainerState.setAlpha(i);
        this.mStrokePaint.setAlpha(i);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setChangingConfigurations(int i) {
        this.mContainerState.setChangingConfigurations(i);
    }

    public void setChildDrawable(Drawable drawable) {
        if (this.mContainerState != null) {
            ChildDrawableWrapper childDrawableWrapper = new ChildDrawableWrapper();
            childDrawableWrapper.mDrawable = drawable;
            drawable.setCallback(this);
            this.mContainerState.mChildDrawableWrapper = childDrawableWrapper;
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mContainerState.setColorFilter(colorFilter);
    }

    public void setCornerRadii(float[] fArr) {
        ContainerState containerState = this.mContainerState;
        containerState.mRadii = fArr;
        this.mRadii = fArr;
        if (fArr == null) {
            containerState.mRadius = 0.0f;
            this.mRadius = 0.0f;
        }
        invalidateSelf();
    }

    public void setCornerRadius(float f) {
        if (Float.isNaN(f)) {
            return;
        }
        if (f < 0.0f) {
            f = 0.0f;
        }
        ContainerState containerState = this.mContainerState;
        containerState.mRadius = f;
        containerState.mRadii = null;
        this.mRadius = f;
        this.mRadii = null;
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setDither(boolean z) {
        this.mContainerState.setDither(z);
    }

    @Override // android.graphics.drawable.Drawable
    public void setFilterBitmap(boolean z) {
        this.mContainerState.setFilterBitmap(z);
    }

    public void setLayerType(int i) {
        if (i < 0 || i > 2) {
            throw new IllegalArgumentException("Layer type can only be one of: LAYER_TYPE_NONE, LAYER_TYPE_SOFTWARE or LAYER_TYPE_HARDWARE");
        }
        ContainerState containerState = this.mContainerState;
        if (containerState.mLayerType != i) {
            containerState.mLayerType = i;
            invalidateSelf();
        }
    }

    public final void setSmoothCornerEnable(boolean z) {
        SmoothCornerHelper.setDrawableSmoothCornerEnable(this, z);
        this.mContainerState.mUseSmooth = z;
    }

    public void setStrokeColor(int i) {
        ContainerState containerState = this.mContainerState;
        if (containerState.mStrokeColor != i) {
            containerState.mStrokeColor = i;
            this.mStrokeColor = i;
            this.mStrokePaint.setColor(i);
            invalidateSelf();
        }
    }

    public void setStrokeWidth(int i) {
        ContainerState containerState = this.mContainerState;
        if (containerState.mStrokeWidth != i) {
            containerState.mStrokeWidth = i;
            this.mStrokeWidth = i;
            this.mStrokePaint.setStrokeWidth(i);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable.Callback
    public void unscheduleDrawable(Drawable drawable, Runnable runnable) {
        unscheduleSelf(runnable);
    }
}
