package miuix.visual.check;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import androidx.annotation.Keep;
import androidx.annotation.Nullable;
import com.miui.maml.folme.AnimatedProperty;
import miuix.animation.Folme;
import miuix.animation.IFolme;
import miuix.animation.IHoverStyle;
import miuix.animation.base.AnimConfig;
import miuix.animation.utils.EaseManager;
import miuix.smooth.SmoothContainerDrawable2;
import miuix.visualcheck.R;

/* JADX INFO: loaded from: classes3.dex */
public class BorderLayout extends LinearLayout implements VisualCheckItem {
    public static final /* synthetic */ int $r8$clinit = 0;
    public IFolme iFolme;
    public Drawable mBackGround;
    public float mCornerRadius;

    @Keep
    private DrawableTarget mDrawableTarget;
    public int mStrokeColor;
    public int mStrokeShape;
    public float mStrokeWidth;

    @Keep
    public static class DrawableTarget {
        private float alpha = 1.0f;
        private Drawable mImg;

        public DrawableTarget(Drawable drawable) {
            this.mImg = drawable;
        }

        public float getAlpha() {
            return this.alpha;
        }

        public void setAlpha(float f) {
            this.alpha = f;
            this.mImg.setAlpha((int) (f * 255.0f));
        }
    }

    public static class Shape {
    }

    public BorderLayout(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        this.iFolme = Folme.useAt(this);
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.BorderLayout);
        this.mBackGround = typedArrayObtainStyledAttributes.getDrawable(R.styleable.BorderLayout_checkedBackGround);
        int dimensionPixelSize = context.getResources().getDimensionPixelSize(R.dimen.borderlayout_bg_stroke_width);
        int color = context.getResources().getColor(R.color.visual_check_borderlayout_bg_stroke_color);
        this.mStrokeWidth = typedArrayObtainStyledAttributes.getFloat(R.styleable.BorderLayout_android_strokeWidth, dimensionPixelSize);
        this.mStrokeColor = typedArrayObtainStyledAttributes.getColor(R.styleable.BorderLayout_android_strokeColor, color);
        this.mStrokeShape = typedArrayObtainStyledAttributes.getInt(R.styleable.BorderLayout_android_shape, 0);
        this.mCornerRadius = typedArrayObtainStyledAttributes.getDimension(R.styleable.BorderLayout_android_radius, Float.MAX_VALUE);
        typedArrayObtainStyledAttributes.recycle();
        if (this.mBackGround == null) {
            this.mBackGround = new SmoothContainerDrawable2();
            setStrokeWidth((int) this.mStrokeWidth);
            setStrokeColor(this.mStrokeColor);
            setCornerRadius(this.mCornerRadius);
            setStrokeShape(this.mStrokeShape);
        }
        this.mDrawableTarget = new DrawableTarget(this.mBackGround);
        this.iFolme.hover().setEffect(IHoverStyle.HoverEffect.FLOATED).handleHoverOf(this, new AnimConfig[0]);
        if (getPaddingStart() == 0 && getPaddingTop() == 0 && getPaddingEnd() == 0 && getPaddingBottom() == 0) {
            int i = ((int) this.mStrokeWidth) + dimensionPixelSize;
            setPadding(i, i, i, i);
        }
    }

    public float getCornerRadius() {
        return this.mCornerRadius;
    }

    public int getStrokeColor() {
        return this.mStrokeColor;
    }

    public int getStrokeShape() {
        return this.mStrokeShape;
    }

    public float getStrokeWidth() {
        return this.mStrokeWidth;
    }

    @Override // miuix.visual.check.VisualCheckItem
    public void onChecked(boolean z) {
        if (getBackground() == null) {
            setBackground(this.mBackGround);
            this.mBackGround.setAlpha(z ? 255 : 0);
        } else if (z) {
            Folme.useValue(this.mDrawableTarget).setFlags(1L).to(AnimatedProperty.PROPERTY_NAME_ALPHA, Float.valueOf(1.0f), EaseManager.getStyle(-2, 1.0f, 0.25f));
        } else {
            Folme.useValue(this.mDrawableTarget).setFlags(1L).to(AnimatedProperty.PROPERTY_NAME_ALPHA, Float.valueOf(0.0f), EaseManager.getStyle(-2, 1.0f, 0.25f));
        }
    }

    @Override // miuix.visual.check.VisualCheckItem
    public void onVisualCheckBoxTouchEvent(VisualCheckBox visualCheckBox, MotionEvent motionEvent) {
        DrawableTarget drawableTarget;
        IFolme iFolme = this.iFolme;
        if (iFolme != null) {
            iFolme.touch().onMotionEvent(motionEvent);
        }
        if (motionEvent.getAction() != 1 || this.mBackGround.getAlpha() == 255 || (drawableTarget = this.mDrawableTarget) == null) {
            return;
        }
        Folme.useValue(drawableTarget).setFlags(1L).to(AnimatedProperty.PROPERTY_NAME_ALPHA, Float.valueOf(1.0f), EaseManager.getStyle(-2, 1.0f, 0.25f));
    }

    public void setCornerRadius(float f) {
        this.mCornerRadius = f;
        Drawable drawable = this.mBackGround;
        if (drawable != null) {
            if (drawable instanceof SmoothContainerDrawable2) {
                ((SmoothContainerDrawable2) drawable).setCornerRadius(f);
            } else if (drawable instanceof GradientDrawable) {
                ((GradientDrawable) drawable).setCornerRadius(f);
            }
        }
    }

    public void setStrokeColor(int i) {
        this.mStrokeColor = i;
        Drawable drawable = this.mBackGround;
        if (drawable != null) {
            if (drawable instanceof SmoothContainerDrawable2) {
                ((SmoothContainerDrawable2) drawable).setStrokeColor(i);
            } else if (drawable instanceof GradientDrawable) {
                ((GradientDrawable) drawable).setStroke((int) this.mStrokeWidth, i);
            }
        }
    }

    public void setStrokeShape(int i) {
        if (i == 0 || i == 3) {
            this.mStrokeShape = i;
            if (i == 3) {
                setCornerRadius(2.1474836E9f);
            }
        }
    }

    public void setStrokeWidth(int i) {
        this.mStrokeWidth = i;
        Drawable drawable = this.mBackGround;
        if (drawable != null) {
            if (drawable instanceof SmoothContainerDrawable2) {
                ((SmoothContainerDrawable2) drawable).setStrokeWidth(i);
            } else if (drawable instanceof GradientDrawable) {
                ((GradientDrawable) drawable).setStroke(i, this.mStrokeColor);
            }
        }
    }
}
