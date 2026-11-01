package miuix.cardview;

import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LinearGradient;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.shapes.RoundRectShape;
import androidx.annotation.RequiresApi;
import com.miui.support.drawable.DrawableUtils;
import miuix.animation.Folme;
import miuix.animation.FolmeEase;
import miuix.animation.FolmeObject;
import miuix.animation.base.AnimConfig;
import miuix.animation.property.ColorProperty;

/* JADX INFO: loaded from: classes2.dex */
@RequiresApi
class RoundRectDrawable extends Drawable implements FolmeObject {
    public ColorStateList mBackground;
    public int mBackgroundColor;
    public final RectF mBoundsF;
    public final Rect mBoundsI;
    public boolean mDrawStrokeOverlay;
    public Folme.ObjectFolmeImpl mFolmeAnimator;
    public boolean mIsStrokeShaderDirty;
    public boolean mIsStrokeShapeDirty;
    public float mPadding;
    public float mRadius;
    public int mStrokeColor;
    public float[] mStrokeGradientColorPositions;
    public int[] mStrokeGradientColors;
    public Shader mStrokeShader;
    public RoundRectShape mStrokeShape;
    public float mStrokeWidth;
    public ColorStateList mTint;
    public PorterDuffColorFilter mTintFilter;
    public static final ColorProperty COLOR = new ColorProperty<RoundRectDrawable>("cardBgColor") { // from class: miuix.cardview.RoundRectDrawable.1
        @Override // miuix.animation.property.ColorProperty, miuix.animation.property.IIntValueProperty
        public int getIntValue(RoundRectDrawable roundRectDrawable) {
            return roundRectDrawable.getBackgroundColor();
        }

        @Override // miuix.animation.property.ColorProperty, miuix.animation.property.IIntValueProperty
        public void setIntValue(RoundRectDrawable roundRectDrawable, int i) {
            roundRectDrawable.setBackgroundColor(i);
        }
    };
    public static final boolean USE_FOLME = !DrawableUtils.isCommonLiteStrategy();
    public static final AnimConfig NORMAL_CONFIG = new AnimConfig().setEase(FolmeEase.spring(1.0f, 0.35f));
    public static final AnimConfig ACTIVATE_CONFIG = new AnimConfig().setEase(FolmeEase.spring(1.0f, 0.2f));
    public boolean mInsetForPadding = false;
    public boolean mInsetForRadius = true;
    public Paint mStrokePaint = null;
    public PorterDuff.Mode mTintMode = PorterDuff.Mode.SRC_IN;
    public final Paint mPaint = new Paint(5);
    public int mAlpha = 255;

    public RoundRectDrawable(ColorStateList colorStateList, float f, float f2, int i) {
        this.mRadius = f;
        if (USE_FOLME) {
            this.mFolmeAnimator = Folme.use((FolmeObject) this);
        }
        setBackground(colorStateList);
        this.mBoundsF = new RectF();
        this.mBoundsI = new Rect();
        this.mStrokeWidth = f2;
        this.mStrokeColor = i;
        if (f2 > 0.0f) {
            createStrokePaint();
            this.mIsStrokeShapeDirty = true;
        }
    }

    public final void createStrokePaint() {
        Paint paint = new Paint(1);
        this.mStrokePaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.mStrokePaint.setColor(this.mStrokeColor);
        this.mStrokePaint.setStrokeWidth(this.mStrokeWidth);
    }

    public final PorterDuffColorFilter createTintFilter(ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return new PorterDuffColorFilter(colorStateList.getColorForState(getState(), 0), mode);
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        boolean z;
        Paint paint = this.mPaint;
        if (this.mTintFilter == null || paint.getColorFilter() != null) {
            z = false;
        } else {
            paint.setColorFilter(this.mTintFilter);
            z = true;
        }
        RectF rectF = this.mBoundsF;
        float f = this.mRadius;
        canvas.drawRoundRect(rectF, f, f, paint);
        if (!this.mDrawStrokeOverlay) {
            drawStroke(canvas);
        }
        if (z) {
            paint.setColorFilter(null);
        }
    }

    public void drawStroke(Canvas canvas) {
        Paint paint = this.mStrokePaint;
        if (paint != null) {
            if (this.mIsStrokeShaderDirty && this.mStrokeGradientColors != null) {
                this.mIsStrokeShaderDirty = false;
                this.mStrokeShader = new LinearGradient(0.0f, 0.0f, 0.0f, getBounds().height(), this.mStrokeGradientColors, this.mStrokeGradientColorPositions, Shader.TileMode.CLAMP);
            }
            if (this.mIsStrokeShapeDirty) {
                updateStrokeShape();
            }
            Shader shader = this.mStrokeShader;
            if (shader != null) {
                paint.setShader(shader);
                paint.setColor(-16777216);
            } else {
                paint.setColor(this.mStrokeColor);
            }
            RoundRectShape roundRectShape = this.mStrokeShape;
            if (roundRectShape != null) {
                roundRectShape.draw(canvas, this.mStrokePaint);
            }
        }
    }

    @Override // miuix.animation.FolmeObject
    public Folme.ObjectFolmeImpl folme() {
        return this.mFolmeAnimator;
    }

    public final int getBackgroundColor() {
        return this.mBackgroundColor;
    }

    public ColorStateList getColor() {
        return this.mBackground;
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return -3;
    }

    @Override // android.graphics.drawable.Drawable
    public void getOutline(Outline outline) {
        outline.setRoundRect(this.mBoundsI, this.mRadius);
    }

    @Override // android.graphics.drawable.Drawable
    public boolean isStateful() {
        ColorStateList colorStateList = this.mTint;
        if (colorStateList != null && colorStateList.isStateful()) {
            return true;
        }
        ColorStateList colorStateList2 = this.mBackground;
        return (colorStateList2 != null && colorStateList2.isStateful()) || super.isStateful();
    }

    @Override // android.graphics.drawable.Drawable
    public void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        updateBounds(rect);
        RoundRectShape roundRectShape = this.mStrokeShape;
        if (roundRectShape != null) {
            roundRectShape.resize(rect.width(), rect.height());
        }
        this.mIsStrokeShaderDirty = true;
    }

    @Override // android.graphics.drawable.Drawable
    public boolean onStateChange(int[] iArr) {
        PorterDuff.Mode mode;
        ColorStateList colorStateList = this.mBackground;
        int colorForState = colorStateList.getColorForState(iArr, colorStateList.getDefaultColor());
        boolean z = colorForState != this.mPaint.getColor();
        if (z) {
            Folme.ObjectFolmeImpl objectFolmeImpl = this.mFolmeAnimator;
            if (objectFolmeImpl != null) {
                objectFolmeImpl.to(COLOR, Integer.valueOf(colorForState), ACTIVATE_CONFIG);
            } else {
                this.mPaint.setColor(colorForState);
            }
        }
        ColorStateList colorStateList2 = this.mTint;
        if (colorStateList2 == null || (mode = this.mTintMode) == null) {
            return z;
        }
        this.mTintFilter = createTintFilter(colorStateList2, mode);
        return true;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int i) {
        int iMax = Math.max(0, Math.min(i, 255));
        if (this.mAlpha != iMax) {
            this.mAlpha = iMax;
            this.mPaint.setColor(updateColorWithAlpha(this.mBackgroundColor, iMax));
        }
    }

    public final void setBackground(ColorStateList colorStateList) {
        if (colorStateList == null) {
            colorStateList = ColorStateList.valueOf(0);
        }
        this.mBackground = colorStateList;
        int colorForState = colorStateList.getColorForState(getState(), this.mBackground.getDefaultColor());
        this.mPaint.setColor(updateColorWithAlpha(colorForState, this.mAlpha));
        Folme.ObjectFolmeImpl objectFolmeImpl = this.mFolmeAnimator;
        if (objectFolmeImpl != null) {
            objectFolmeImpl.setTo(COLOR, Integer.valueOf(colorForState));
        } else {
            setBackgroundColor(colorForState);
        }
    }

    public final void setBackgroundColor(int i) {
        if (this.mBackgroundColor != i) {
            this.mBackgroundColor = i;
            this.mPaint.setColor(updateColorWithAlpha(i, this.mAlpha));
            invalidateSelf();
        }
    }

    public void setColor(ColorStateList colorStateList) {
        setBackground(colorStateList);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
        this.mPaint.setColorFilter(colorFilter);
    }

    public void setDrawStrokeOverlay(boolean z) {
        if (this.mDrawStrokeOverlay != z) {
            this.mDrawStrokeOverlay = z;
            invalidateSelf();
        }
    }

    @Override // miuix.animation.FolmeObject
    public void setFolmeImpl(Folme.ObjectFolmeImpl objectFolmeImpl) {
        this.mFolmeAnimator = objectFolmeImpl;
    }

    public void setRadius(float f) {
        if (f == this.mRadius) {
            return;
        }
        this.mRadius = f;
        updateBounds(null);
        invalidateSelf();
    }

    public void setStrokeColor(int i) {
        if (this.mStrokeColor != i) {
            this.mStrokeColor = i;
            this.mIsStrokeShapeDirty = true;
            Paint paint = this.mStrokePaint;
            if (paint != null) {
                paint.setColor(i);
            }
            invalidateSelf();
        }
    }

    public void setStrokeColorGradientPositions(float[] fArr) {
        this.mStrokeGradientColorPositions = fArr;
        this.mIsStrokeShaderDirty = true;
    }

    public void setStrokeGradientColors(int[] iArr) {
        this.mStrokeGradientColors = iArr;
        this.mIsStrokeShaderDirty = true;
    }

    public void setStrokeWidth(float f) {
        if (this.mStrokeWidth != f) {
            this.mStrokeWidth = f;
            this.mIsStrokeShapeDirty = true;
            Paint paint = this.mStrokePaint;
            if (paint != null) {
                paint.setStrokeWidth(f);
            } else if (f > 0.0f) {
                createStrokePaint();
            }
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintList(ColorStateList colorStateList) {
        this.mTint = colorStateList;
        this.mTintFilter = createTintFilter(colorStateList, this.mTintMode);
        invalidateSelf();
    }

    @Override // android.graphics.drawable.Drawable
    public void setTintMode(PorterDuff.Mode mode) {
        this.mTintMode = mode;
        this.mTintFilter = createTintFilter(this.mTint, mode);
        invalidateSelf();
    }

    public final void updateBounds(Rect rect) {
        if (rect == null) {
            rect = getBounds();
        }
        this.mBoundsF.set(rect.left, rect.top, rect.right, rect.bottom);
        this.mBoundsI.set(rect);
        if (this.mInsetForPadding) {
            this.mBoundsI.inset((int) Math.ceil(RoundRectDrawableWithShadow.calculateHorizontalPadding(this.mPadding, this.mRadius, this.mInsetForRadius)), (int) Math.ceil(RoundRectDrawableWithShadow.calculateVerticalPadding(this.mPadding, this.mRadius, this.mInsetForRadius)));
            this.mBoundsF.set(this.mBoundsI);
        }
    }

    public final int updateColorWithAlpha(int i, int i2) {
        return ((((i >>> 24) * i2) / 255) << 24) | (i & 16777215);
    }

    public final void updateStrokeShape() {
        this.mIsStrokeShapeDirty = false;
        float f = this.mRadius;
        float[] fArr = {f, f, f, f, f, f, f, f};
        float f2 = f - this.mStrokeWidth;
        float f3 = this.mStrokeWidth;
        this.mStrokeShape = new RoundRectShape(fArr, new RectF(f3, f3, f3, f3), new float[]{f2, f2, f2, f2, f2, f2, f2, f2});
        Rect bounds = getBounds();
        this.mStrokeShape.resize(bounds.width(), bounds.height());
    }
}
