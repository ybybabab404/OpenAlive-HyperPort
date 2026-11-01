package miuix.nestedheader.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.LinearInterpolator;
import miuix.core.util.MiuixUIUtils;
import miuix.internal.util.AttributeResolver;
import miuix.nestedheader.R;

/* JADX INFO: loaded from: classes2.dex */
public class NestedHeaderStickyMaskImpl implements INestedHeaderStickyMaskWidget {
    public final int mDefaultMaskColor;
    public boolean mEnabled;
    public LinearGradient mGradientShader;
    public float mMaskAlpha;
    public ValueAnimator mMaskAnimator;
    public int mMaskColor;
    public float mMaskExtraHeight;
    public int mStickyOriginalBgAlpha;
    public final View mStickyView;
    public Drawable mWindowBg;
    public final Path mGradientPath = new Path();
    public final Paint mGradientPaint = new Paint(1);
    public int mLastGradientHeight = -1;
    public int mLastGradientWidth = -1;
    public boolean mGradientNeedsUpdate = true;
    public boolean mAllowStickyTransparent = true;

    public static /* synthetic */ void $r8$lambda$fSX3_zwakGgY8w6h5F2uYC_WjcU(NestedHeaderStickyMaskImpl nestedHeaderStickyMaskImpl, ValueAnimator valueAnimator) {
        nestedHeaderStickyMaskImpl.getClass();
        nestedHeaderStickyMaskImpl.mMaskAlpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        nestedHeaderStickyMaskImpl.invalidateStickyView(true);
    }

    public NestedHeaderStickyMaskImpl(View view) {
        this.mStickyView = view;
        Context context = view.getContext();
        this.mMaskExtraHeight = context.getResources().getDimension(R.dimen.miuix_nested_header_layout_sticky_mask_height) / context.getResources().getDisplayMetrics().density;
        int i = !AttributeResolver.resolveBoolean(context, android.R.attr.isLightTheme, true) ? -16777216 : -789517;
        this.mMaskColor = i;
        this.mDefaultMaskColor = i;
        this.mStickyOriginalBgAlpha = view.getBackground() != null ? view.getBackground().getAlpha() : 255;
        view.setWillNotDraw(false);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            viewGroup.setClipToPadding(false);
            viewGroup.setClipChildren(false);
        }
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).setClipChildren(false);
        }
    }

    public final void animateMaskAlpha(boolean z) {
        ValueAnimator valueAnimator = this.mMaskAnimator;
        if (valueAnimator != null) {
            valueAnimator.cancel();
            this.mMaskAnimator = null;
        }
        if (z) {
            this.mMaskAlpha = 1.0f;
            invalidateStickyView(true);
            return;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mMaskAlpha, 0.0f);
        this.mMaskAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(100L);
        this.mMaskAnimator.setInterpolator(new LinearInterpolator());
        this.mMaskAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: miuix.nestedheader.widget.NestedHeaderStickyMaskImpl$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                NestedHeaderStickyMaskImpl.$r8$lambda$fSX3_zwakGgY8w6h5F2uYC_WjcU(this.f$0, valueAnimator2);
            }
        });
        this.mMaskAnimator.start();
    }

    public void drawOverlayMask(Canvas canvas) {
        if (this.mMaskAlpha <= 0.0f || Color.alpha(this.mMaskColor) == 0) {
            return;
        }
        int width = this.mStickyView.getWidth();
        int height = this.mStickyView.getHeight() + MiuixUIUtils.dp2px(this.mStickyView.getContext(), this.mMaskExtraHeight);
        if (width <= 0 || height <= 0) {
            return;
        }
        if (this.mLastGradientHeight != height || this.mLastGradientWidth != width || this.mGradientNeedsUpdate) {
            this.mLastGradientHeight = height;
            this.mLastGradientWidth = width;
            this.mGradientNeedsUpdate = false;
            int iAlpha = Color.alpha(this.mMaskColor);
            int i = this.mMaskColor & 16777215;
            float[] fArr = {1.0f, 0.98f, 0.8f, 0.5f, 0.0f};
            float[] fArr2 = {0.0f, 0.52f, 0.74f, 0.85f, 1.0f};
            int[] iArr = new int[5];
            for (int i2 = 0; i2 < 5; i2++) {
                iArr[i2] = (Math.round(iAlpha * fArr[i2]) << 24) | i;
            }
            float f = height;
            LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, 0.0f, f, iArr, fArr2, Shader.TileMode.CLAMP);
            this.mGradientShader = linearGradient;
            this.mGradientPaint.setShader(linearGradient);
            this.mGradientPath.reset();
            this.mGradientPath.addRect(0.0f, 0.0f, width, f, Path.Direction.CW);
        }
        this.mGradientPaint.setAlpha(Math.round(this.mMaskAlpha * 255.0f));
        canvas.drawPath(this.mGradientPath, this.mGradientPaint);
    }

    public final void invalidateStickyView(boolean z) {
        if (z && this.mAllowStickyTransparent) {
            Drawable background = this.mStickyView.getBackground();
            if (background != null) {
                background.mutate().setAlpha(isStickyMaskEnabled() ? 0 : this.mStickyOriginalBgAlpha);
            } else if (isStickyMaskEnabled()) {
                this.mStickyView.setBackgroundColor(0);
            } else {
                if (this.mWindowBg == null) {
                    this.mWindowBg = AttributeResolver.resolveDrawable(this.mStickyView.getContext(), android.R.attr.windowBackground);
                }
                this.mStickyView.setBackground(this.mWindowBg);
            }
        }
        this.mStickyView.invalidate();
    }

    public boolean isStickyMaskEnabled() {
        return this.mEnabled;
    }

    public void setMaskColor(int i) {
        if (this.mMaskColor != i) {
            this.mMaskColor = i;
            this.mGradientNeedsUpdate = true;
            invalidateStickyView(false);
        }
    }

    public void setMaskExtraHeight(float f) {
        if (this.mMaskExtraHeight != f) {
            this.mMaskExtraHeight = f;
            this.mGradientNeedsUpdate = true;
            invalidateStickyView(false);
        }
    }

    public void setStickyMaskEnabled(boolean z) {
        if (this.mEnabled == z) {
            return;
        }
        this.mEnabled = z;
        ViewParent parent = this.mStickyView.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).setClipChildren(false);
        }
        animateMaskAlpha(z);
    }
}
