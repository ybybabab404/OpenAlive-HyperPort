package miuix.miuixbasewidget.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import java.util.Arrays;

/* JADX INFO: loaded from: classes2.dex */
public class RoundCornerImageView extends View {
    public Rect mBounds;
    public Path mClipPath;
    public boolean mClipToPadding;
    public float mCornerRadius;
    public boolean mCornerRadiusEnabled;
    public Drawable mImageDrawable;
    public RectF mLayer;
    public boolean mNeedStroke;
    public float[] mRadii;
    public Paint mStrokePaint;

    public RoundCornerImageView(Context context) {
        this(context, null);
    }

    public RoundCornerImageView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public RoundCornerImageView(Context context, @Nullable AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public RoundCornerImageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mCornerRadiusEnabled = true;
        this.mClipToPadding = false;
        this.mNeedStroke = false;
        this.mClipPath = new Path();
        this.mLayer = new RectF();
        this.mBounds = new Rect();
        this.mRadii = new float[8];
    }

    public static float dp2px(Context context, float f) {
        return (f * (context.getResources().getConfiguration().densityDpi / 160.0f)) + 0.5f;
    }

    private float getStrokeWidth() {
        return dp2px(getContext(), 0.51f);
    }

    public static boolean isNightMode(Context context) {
        return context.getResources().getConfiguration().isNightModeActive();
    }

    public final void drawStroke(Canvas canvas) {
        Context context = getContext();
        float fDp2px = dp2px(context, 0.51f);
        if (this.mStrokePaint == null) {
            this.mStrokePaint = getStrokePaint(fDp2px, isNightMode(context) ? 872415231 : 436207616);
        }
        RectF rectF = this.mLayer;
        float f = this.mCornerRadius;
        canvas.drawRoundRect(rectF, f, f, this.mStrokePaint);
    }

    public final Paint getStrokePaint(float f, int i) {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setColor(i);
        paint.setStrokeWidth(f);
        return paint;
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        if (this.mImageDrawable != null) {
            if (this.mClipToPadding) {
                this.mBounds.set(getPaddingLeft(), getPaddingTop(), getMeasuredWidth() - getPaddingRight(), getMeasuredHeight() - getPaddingBottom());
            } else {
                this.mBounds.set(0, 0, getMeasuredWidth(), getMeasuredHeight());
            }
            this.mLayer.set(this.mBounds);
            this.mImageDrawable.setBounds(this.mBounds);
            if (this.mCornerRadiusEnabled) {
                Arrays.fill(this.mRadii, this.mCornerRadius);
                this.mClipPath.reset();
                this.mClipPath.addRoundRect(this.mLayer, this.mRadii, Path.Direction.CW);
                canvas.clipPath(this.mClipPath);
            }
            this.mImageDrawable.mutate().draw(canvas);
            if (this.mNeedStroke) {
                drawStroke(canvas);
            }
        }
    }

    @Override // android.view.View
    public void setBackground(Drawable drawable) {
    }

    public void setClipToPadding(boolean z) {
        if (this.mClipToPadding != z) {
            this.mClipToPadding = z;
            invalidate();
        }
    }

    public void setCornerRadius(float f) {
        if (this.mCornerRadius != f) {
            this.mCornerRadius = f;
            invalidate();
        }
    }

    public void setCornerRadiusEnabled(boolean z) {
        if (this.mCornerRadiusEnabled != z) {
            this.mCornerRadiusEnabled = z;
            invalidate();
        }
    }

    public void setImageDrawable(@DrawableRes int i) {
        setImageDrawable(ContextCompat.getDrawable(getContext(), i));
    }

    public void setImageDrawable(@Nullable Drawable drawable) {
        Drawable drawable2 = this.mImageDrawable;
        if (drawable2 != drawable) {
            if (drawable2 != null) {
                drawable2.setCallback(null);
            }
            if (drawable != null) {
                drawable.setCallback(this);
            }
            this.mImageDrawable = drawable;
            invalidate();
        }
    }

    public void setNeedStroke(boolean z) {
        this.mNeedStroke = z;
        this.mClipToPadding = z;
        int strokeWidth = z ? (int) getStrokeWidth() : 0;
        setPadding(strokeWidth, strokeWidth, strokeWidth, strokeWidth);
    }
}
