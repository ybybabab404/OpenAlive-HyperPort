package miuix.smooth;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Outline;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import java.io.IOException;
import miuix.smooth.internal.SmoothDrawHelper;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* JADX INFO: loaded from: classes2.dex */
@Deprecated
public class SmoothGradientDrawable extends GradientDrawable {
    public static final PorterDuffXfermode XFERMODE_DST_OUT = new PorterDuffXfermode(PorterDuff.Mode.DST_OUT);
    public SmoothDrawHelper mHelper;
    public Rect mLayer;
    public GradientDrawable mParentDrawable;
    public RectF mSavedLayer;
    public SmoothConstantState mSmoothConstantState;

    public static class SmoothConstantState extends Drawable.ConstantState {
        public int mLayerType;
        public Drawable.ConstantState mParent;
        public float[] mRadii;
        public float mRadius;
        public int mStrokeColor;
        public int mStrokeWidth;

        public SmoothConstantState(SmoothConstantState smoothConstantState) {
            this.mStrokeWidth = 0;
            this.mStrokeColor = 0;
            this.mLayerType = 0;
            this.mRadius = smoothConstantState.mRadius;
            this.mRadii = smoothConstantState.mRadii;
            this.mStrokeWidth = smoothConstantState.mStrokeWidth;
            this.mStrokeColor = smoothConstantState.mStrokeColor;
            this.mParent = smoothConstantState.mParent;
            this.mLayerType = smoothConstantState.mLayerType;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public boolean canApplyTheme() {
            return true;
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public int getChangingConfigurations() {
            return this.mParent.getChangingConfigurations();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable() {
            Resources resources = null;
            Object[] objArr = 0;
            if (this.mParent == null) {
                return null;
            }
            return new SmoothGradientDrawable(this, resources);
        }

        @Override // android.graphics.drawable.Drawable.ConstantState
        public Drawable newDrawable(Resources resources) {
            if (this.mParent == null) {
                return null;
            }
            return new SmoothGradientDrawable(new SmoothConstantState(this), resources);
        }

        public void setConstantState(Drawable.ConstantState constantState) {
            this.mParent = constantState;
        }
    }

    public SmoothGradientDrawable(SmoothConstantState smoothConstantState, Resources resources) {
        this.mHelper = new SmoothDrawHelper();
        this.mSavedLayer = new RectF();
        this.mLayer = new Rect();
        this.mSmoothConstantState = smoothConstantState;
        Drawable drawableNewDrawable = resources == null ? smoothConstantState.mParent.newDrawable() : smoothConstantState.mParent.newDrawable(resources);
        this.mSmoothConstantState.setConstantState(drawableNewDrawable.getConstantState());
        this.mParentDrawable = (GradientDrawable) drawableNewDrawable;
        this.mHelper.setRadii(smoothConstantState.mRadii);
        this.mHelper.setRadius(smoothConstantState.mRadius);
        this.mHelper.setStrokeWidth(smoothConstantState.mStrokeWidth);
        this.mHelper.setStrokeColor(smoothConstantState.mStrokeColor);
    }

    @Override // android.graphics.drawable.GradientDrawable, android.graphics.drawable.Drawable
    public void applyTheme(Resources.Theme theme) {
        super.applyTheme(theme);
        this.mSmoothConstantState.setConstantState(super.getConstantState());
    }

    @Override // android.graphics.drawable.GradientDrawable, android.graphics.drawable.Drawable
    public boolean canApplyTheme() {
        SmoothConstantState smoothConstantState = this.mSmoothConstantState;
        return (smoothConstantState != null && smoothConstantState.canApplyTheme()) || super.canApplyTheme();
    }

    @Override // android.graphics.drawable.GradientDrawable, android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        int iSaveLayer = getLayerType() != 2 ? canvas.saveLayer(this.mSavedLayer, null, 31) : -1;
        GradientDrawable gradientDrawable = this.mParentDrawable;
        if (gradientDrawable != null) {
            gradientDrawable.draw(canvas);
        } else {
            super.draw(canvas);
        }
        this.mHelper.drawMask(canvas, XFERMODE_DST_OUT);
        if (getLayerType() != 2) {
            canvas.restoreToCount(iSaveLayer);
        }
        this.mHelper.drawStroke(canvas);
    }

    @Override // android.graphics.drawable.GradientDrawable, android.graphics.drawable.Drawable
    public int getAlpha() {
        GradientDrawable gradientDrawable = this.mParentDrawable;
        return gradientDrawable != null ? gradientDrawable.getAlpha() : super.getAlpha();
    }

    @Override // android.graphics.drawable.GradientDrawable
    public ColorStateList getColor() {
        GradientDrawable gradientDrawable = this.mParentDrawable;
        return gradientDrawable != null ? gradientDrawable.getColor() : super.getColor();
    }

    @Override // android.graphics.drawable.GradientDrawable
    public int[] getColors() {
        GradientDrawable gradientDrawable = this.mParentDrawable;
        return gradientDrawable != null ? gradientDrawable.getColors() : super.getColors();
    }

    @Override // android.graphics.drawable.GradientDrawable, android.graphics.drawable.Drawable
    public Drawable.ConstantState getConstantState() {
        return this.mSmoothConstantState;
    }

    public int getLayerType() {
        return this.mSmoothConstantState.mLayerType;
    }

    @Override // android.graphics.drawable.GradientDrawable, android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        outline.setPath(this.mHelper.getSmoothPath(this.mLayer));
    }

    @Override // android.graphics.drawable.GradientDrawable, android.graphics.drawable.Drawable
    public void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        TypedArray typedArrayObtainAttributes = obtainAttributes(resources, theme, attributeSet, R.styleable.MiuixSmoothGradientDrawable);
        setStrokeWidth(typedArrayObtainAttributes.getDimensionPixelSize(R.styleable.MiuixSmoothGradientDrawable_miuix_strokeWidth, 0));
        setStrokeColor(typedArrayObtainAttributes.getColor(R.styleable.MiuixSmoothGradientDrawable_miuix_strokeColor, 0));
        setLayerType(typedArrayObtainAttributes.getInt(R.styleable.MiuixSmoothGradientDrawable_android_layerType, 0));
        typedArrayObtainAttributes.recycle();
        super.inflate(resources, xmlPullParser, attributeSet, theme);
    }

    public final TypedArray obtainAttributes(Resources resources, Resources.Theme theme, AttributeSet attributeSet, int[] iArr) {
        return theme == null ? resources.obtainAttributes(attributeSet, iArr) : theme.obtainStyledAttributes(attributeSet, iArr, 0, 0);
    }

    @Override // android.graphics.drawable.GradientDrawable, android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        GradientDrawable gradientDrawable = this.mParentDrawable;
        if (gradientDrawable != null) {
            gradientDrawable.setBounds(rect);
        }
        this.mHelper.onBoundsChange(rect);
        this.mLayer = rect;
        this.mSavedLayer.set(0.0f, 0.0f, rect.width(), rect.height());
    }

    @Override // android.graphics.drawable.GradientDrawable, android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        GradientDrawable gradientDrawable = this.mParentDrawable;
        if (gradientDrawable != null) {
            gradientDrawable.setAlpha(i);
        } else {
            super.setAlpha(i);
        }
        invalidateSelf();
    }

    @Override // android.graphics.drawable.GradientDrawable
    public void setColor(int i) {
        GradientDrawable gradientDrawable = this.mParentDrawable;
        if (gradientDrawable != null) {
            gradientDrawable.setColor(i);
        } else {
            super.setColor(i);
        }
    }

    @Override // android.graphics.drawable.GradientDrawable
    public void setColor(ColorStateList colorStateList) {
        GradientDrawable gradientDrawable = this.mParentDrawable;
        if (gradientDrawable != null) {
            gradientDrawable.setColor(colorStateList);
        } else {
            super.setColor(colorStateList);
        }
    }

    @Override // android.graphics.drawable.GradientDrawable
    public void setColors(int[] iArr, float[] fArr) {
        GradientDrawable gradientDrawable = this.mParentDrawable;
        if (gradientDrawable != null) {
            gradientDrawable.setColors(iArr, fArr);
        } else {
            super.setColors(iArr, fArr);
        }
    }

    @Override // android.graphics.drawable.GradientDrawable
    public void setCornerRadii(float[] fArr) {
        super.setCornerRadii(fArr);
        this.mSmoothConstantState.mRadii = fArr;
        this.mHelper.setRadii(fArr);
        if (fArr == null) {
            this.mSmoothConstantState.mRadius = 0.0f;
            this.mHelper.setRadius(0.0f);
        }
    }

    @Override // android.graphics.drawable.GradientDrawable
    public void setCornerRadius(float f) {
        if (Float.isNaN(f)) {
            return;
        }
        super.setCornerRadius(f);
        if (f < 0.0f) {
            f = 0.0f;
        }
        SmoothConstantState smoothConstantState = this.mSmoothConstantState;
        smoothConstantState.mRadius = f;
        smoothConstantState.mRadii = null;
        this.mHelper.setRadius(f);
        this.mHelper.setRadii(null);
    }

    public void setLayerType(int i) {
        if (i < 0 || i > 2) {
            throw new IllegalArgumentException("Layer type can only be one of: LAYER_TYPE_NONE, LAYER_TYPE_SOFTWARE or LAYER_TYPE_HARDWARE");
        }
        SmoothConstantState smoothConstantState = this.mSmoothConstantState;
        if (smoothConstantState.mLayerType != i) {
            smoothConstantState.mLayerType = i;
            invalidateSelf();
        }
    }

    public void setStrokeColor(int i) {
        SmoothConstantState smoothConstantState = this.mSmoothConstantState;
        if (smoothConstantState.mStrokeColor != i) {
            smoothConstantState.mStrokeColor = i;
            this.mHelper.setStrokeColor(i);
            invalidateSelf();
        }
    }

    public void setStrokeWidth(int i) {
        SmoothConstantState smoothConstantState = this.mSmoothConstantState;
        if (smoothConstantState.mStrokeWidth != i) {
            smoothConstantState.mStrokeWidth = i;
            this.mHelper.setStrokeWidth(i);
            invalidateSelf();
        }
    }
}
