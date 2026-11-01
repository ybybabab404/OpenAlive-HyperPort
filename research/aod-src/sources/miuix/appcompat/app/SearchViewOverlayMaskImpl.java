package miuix.appcompat.app;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.LinearInterpolator;
import miuix.appcompat.R;
import miuix.core.util.MiuixUIUtils;
import miuix.internal.util.AttributeResolver;

/* JADX INFO: loaded from: classes2.dex */
public class SearchViewOverlayMaskImpl implements ISearchViewOverlayMaskWidget {
    public final int mDefaultMaskColor;
    public boolean mEnabled;
    public LinearGradient mGradientShader;
    public final View mHost;
    public int mHostOriginalBgAlpha;
    public float mMaskAlpha;
    public ValueAnimator mMaskAnimator;
    public int mMaskColor;
    public float mMaskExtraHeight;
    public OverlayMaskConfig mOverlayMaskConfig;
    public final Path mGradientPath = new Path();
    public final Paint mGradientPaint = new Paint(1);
    public int mLastGradientHeight = -1;
    public int mLastGradientWidth = -1;
    public boolean mGradientNeedsUpdate = true;
    public boolean mAllowHostTransparent = true;

    /* JADX INFO: renamed from: $r8$lambda$snVuz8N--M11462TQ3nb24oVK6I, reason: not valid java name */
    public static /* synthetic */ void m1312$r8$lambda$snVuz8NM11462TQ3nb24oVK6I(SearchViewOverlayMaskImpl searchViewOverlayMaskImpl, ValueAnimator valueAnimator) {
        searchViewOverlayMaskImpl.getClass();
        searchViewOverlayMaskImpl.mMaskAlpha = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        searchViewOverlayMaskImpl.invalidateHost(true);
    }

    public SearchViewOverlayMaskImpl(View view) {
        this.mHost = view;
        Context context = view.getContext();
        this.mMaskExtraHeight = context.getResources().getDimension(R.dimen.miuix_appcompat_action_bar_mask_extra_height) / context.getResources().getDisplayMetrics().density;
        int i = !AttributeResolver.resolveBoolean(context, android.R.attr.isLightTheme, true) ? -16777216 : -789517;
        this.mMaskColor = i;
        this.mDefaultMaskColor = i;
        this.mHostOriginalBgAlpha = view.getBackground() != null ? view.getBackground().getAlpha() : 255;
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
            invalidateHost(true);
            return;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(this.mMaskAlpha, 0.0f);
        this.mMaskAnimator = valueAnimatorOfFloat;
        valueAnimatorOfFloat.setDuration(100L);
        this.mMaskAnimator.setInterpolator(new LinearInterpolator());
        this.mMaskAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: miuix.appcompat.app.SearchViewOverlayMaskImpl$$ExternalSyntheticLambda0
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                SearchViewOverlayMaskImpl.m1312$r8$lambda$snVuz8NM11462TQ3nb24oVK6I(this.f$0, valueAnimator2);
            }
        });
        this.mMaskAnimator.start();
    }

    public void drawOverlayMask(Canvas canvas) {
        float[] positions;
        float[] alphaFactors;
        if (this.mMaskAlpha <= 0.0f || Color.alpha(this.mMaskColor) == 0) {
            return;
        }
        int width = this.mHost.getWidth();
        int height = this.mHost.getHeight() + MiuixUIUtils.dp2px(this.mHost.getContext(), this.mMaskExtraHeight);
        if (width <= 0 || height <= 0) {
            return;
        }
        if (this.mLastGradientHeight != height || this.mLastGradientWidth != width || this.mGradientNeedsUpdate) {
            this.mLastGradientHeight = height;
            this.mLastGradientWidth = width;
            this.mGradientNeedsUpdate = false;
            int iAlpha = Color.alpha(this.mMaskColor);
            int i = this.mMaskColor & 16777215;
            OverlayMaskConfig overlayMaskConfig = this.mOverlayMaskConfig;
            if (overlayMaskConfig != null) {
                alphaFactors = overlayMaskConfig.getAlphaFactors();
                positions = this.mOverlayMaskConfig.getPositions();
            } else {
                positions = new float[]{0.0f, 0.52f, 0.74f, 0.85f, 1.0f};
                alphaFactors = new float[]{1.0f, 0.98f, 0.8f, 0.5f, 0.0f};
            }
            float[] fArr = positions;
            int[] iArr = new int[alphaFactors.length];
            for (int i2 = 0; i2 < alphaFactors.length; i2++) {
                iArr[i2] = (Math.round(iAlpha * alphaFactors[i2]) << 24) | i;
            }
            float f = height;
            LinearGradient linearGradient = new LinearGradient(0.0f, 0.0f, 0.0f, f, iArr, fArr, Shader.TileMode.CLAMP);
            this.mGradientShader = linearGradient;
            this.mGradientPaint.setShader(linearGradient);
            this.mGradientPath.reset();
            this.mGradientPath.addRect(0.0f, 0.0f, width, f, Path.Direction.CW);
        }
        this.mGradientPaint.setAlpha(Math.round(this.mMaskAlpha * 255.0f));
        canvas.drawPath(this.mGradientPath, this.mGradientPaint);
    }

    public final void invalidateHost(boolean z) {
        if (z && this.mAllowHostTransparent && this.mHost.getBackground() != null) {
            this.mHost.getBackground().mutate().setAlpha(isOverlayMaskEnabled() ? 0 : this.mHostOriginalBgAlpha);
        }
        this.mHost.invalidate();
    }

    public boolean isOverlayMaskEnabled() {
        return this.mEnabled;
    }

    public void setMaskColor(int i) {
        if (this.mMaskColor != i) {
            this.mMaskColor = i;
            this.mGradientNeedsUpdate = true;
            invalidateHost(false);
        }
    }

    public void setMaskExtraHeight(float f) {
        if (this.mMaskExtraHeight != f) {
            this.mMaskExtraHeight = f;
            this.mGradientNeedsUpdate = true;
            invalidateHost(false);
        }
    }

    public void setOverlayMaskConfig(OverlayMaskConfig overlayMaskConfig) {
        this.mOverlayMaskConfig = overlayMaskConfig;
        if (overlayMaskConfig != null) {
            this.mMaskColor = overlayMaskConfig.getMaskColor();
        } else {
            this.mMaskColor = this.mDefaultMaskColor;
        }
        this.mGradientNeedsUpdate = true;
        invalidateHost(false);
    }

    public void setOverlayMaskEnabled(boolean z) {
        if (this.mEnabled == z) {
            return;
        }
        this.mEnabled = z;
        ViewParent parent = this.mHost.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).setClipChildren(false);
        }
        animateMaskAlpha(z);
    }
}
