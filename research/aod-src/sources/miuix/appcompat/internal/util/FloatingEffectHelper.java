package miuix.appcompat.internal.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import java.util.Arrays;
import miuix.appcompat.R;
import miuix.appcompat.internal.graphics.drawable.RoundStateDrawable;
import miuix.core.util.HyperBloomStrokeUtils;
import miuix.core.util.HyperMaterialUtils;
import miuix.core.util.MaterialConfig;
import miuix.core.util.MaterialDayNightConfig;
import miuix.core.util.MiShadowUtils;
import miuix.core.util.MiuiBlurUtils;
import miuix.theme.token.BloomStrokeToken;
import miuix.theme.token.ColorBlendToken;
import miuix.theme.token.MaterialDayNightToken;
import miuix.theme.token.MaterialToken;
import miuix.theme.token.ShadowToken;

/* JADX INFO: loaded from: classes2.dex */
public class FloatingEffectHelper {
    public static final MaterialDayNightToken Pured_Thin_Glass;
    public static final MaterialToken Pured_Thin_Glass_Dark;
    public static final MaterialToken Pured_Thin_Glass_Light;
    public static final int TAG_FALLBACK_OUTER_SHADOW;
    public static final int TAG_FALLBACK_OUTER_SHADOW_DETACH_LISTENER;
    public static final MaterialDayNightConfig sMaterial;

    public static class BgColorAnimParams {
        public final boolean isDarkMode;
        public final int targetColor;

        public BgColorAnimParams(int i, boolean z) {
            this.targetColor = i;
            this.isDarkMode = z;
        }
    }

    public static class FallbackOuterShadowDrawable extends Drawable {
        public float appliedShadowAlpha;
        public float appliedShadowRadius;
        public float clipCornerRadius;
        public final Path clipPath;
        public final float density;
        public final RectF hostRect;
        public final View hostView;
        public final View.OnLayoutChangeListener layoutChangeListener;
        public final View overlayOwner;
        public final RectF rect;
        public float shadowAlpha;
        public final Paint shadowPaint;
        public float shadowRadius;

        public static /* synthetic */ void $r8$lambda$mGxjvk9pkeIaOgv0JhkjCgeqjD0(FallbackOuterShadowDrawable fallbackOuterShadowDrawable, View view, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
            fallbackOuterShadowDrawable.updateBounds();
            fallbackOuterShadowDrawable.invalidateSelf();
        }

        public FallbackOuterShadowDrawable(View view, View view2) {
            Paint paint = new Paint(1);
            this.shadowPaint = paint;
            this.rect = new RectF();
            this.hostRect = new RectF();
            this.clipPath = new Path();
            this.appliedShadowRadius = -1.0f;
            this.appliedShadowAlpha = -1.0f;
            this.hostView = view;
            this.overlayOwner = view2;
            this.density = view.getContext().getResources().getDisplayMetrics().density;
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(16777216);
            View.OnLayoutChangeListener onLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: miuix.appcompat.internal.util.FloatingEffectHelper$FallbackOuterShadowDrawable$$ExternalSyntheticLambda0
                @Override // android.view.View.OnLayoutChangeListener
                public final void onLayoutChange(View view3, int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
                    FloatingEffectHelper.FallbackOuterShadowDrawable.$r8$lambda$mGxjvk9pkeIaOgv0JhkjCgeqjD0(this.f$0, view3, i, i2, i3, i4, i5, i6, i7, i8);
                }
            };
            this.layoutChangeListener = onLayoutChangeListener;
            view.addOnLayoutChangeListener(onLayoutChangeListener);
            updateBounds();
        }

        public final int applyAlpha(int i, float f) {
            return (Math.round(((i >>> 24) & 255) * f) << 24) | (i & 16777215);
        }

        @Override // android.graphics.drawable.Drawable
        public void draw(Canvas canvas) {
            if (this.shadowRadius <= 0.0f || this.shadowAlpha <= 0.0f || this.hostView.getWidth() <= 0 || this.hostView.getHeight() <= 0) {
                return;
            }
            this.rect.set(this.hostRect);
            int iSave = canvas.save();
            canvas.clipOutPath(this.clipPath);
            RectF rectF = this.rect;
            float f = this.clipCornerRadius;
            canvas.drawRoundRect(rectF, f, f, this.shadowPaint);
            canvas.restoreToCount(iSave);
        }

        public final float getCornerRadius() {
            Drawable background = this.hostView.getBackground();
            if (background instanceof RoundStateDrawable) {
                float cornerRadius = ((RoundStateDrawable) background).getCornerRadius();
                if (cornerRadius != -1.0f) {
                    return cornerRadius;
                }
            }
            return Math.min(this.hostView.getWidth(), this.hostView.getHeight()) / 2.0f;
        }

        @Override // android.graphics.drawable.Drawable
        public int getOpacity() {
            return -3;
        }

        @Override // android.graphics.drawable.Drawable
        public void getOutline(Outline outline) {
            outline.setRoundRect(Math.round(this.hostRect.left), Math.round(this.hostRect.top), Math.round(this.hostRect.right), Math.round(this.hostRect.bottom), getCornerRadius());
        }

        public void removeFromOverlay() {
            this.hostView.removeOnLayoutChangeListener(this.layoutChangeListener);
            this.overlayOwner.getOverlay().remove(this);
        }

        @Override // android.graphics.drawable.Drawable
        public void setAlpha(int i) {
            setShadowAlpha(i / 255.0f);
        }

        @Override // android.graphics.drawable.Drawable
        public void setColorFilter(ColorFilter colorFilter) {
            this.shadowPaint.setColorFilter(colorFilter);
            invalidateSelf();
        }

        public void setShadow(float f, float f2) {
            boolean z = this.shadowRadius != f;
            this.shadowRadius = f;
            updateBounds();
            setShadowAlpha(f2);
            if (z) {
                updateShadowPaintIfNeeded();
                invalidateSelf();
            }
        }

        public void setShadowAlpha(float f) {
            float fMax = Math.max(0.0f, Math.min(1.0f, f));
            if (this.shadowAlpha != fMax) {
                this.shadowAlpha = fMax;
                updateShadowPaintIfNeeded();
                invalidateSelf();
            }
        }

        public final void updateBounds() {
            int iMax = Math.max(1, Math.round((this.shadowRadius + Math.abs(ShadowToken.Regular.offsetX) + Math.abs(ShadowToken.Regular.offsetY)) * this.density));
            View view = this.overlayOwner;
            View view2 = this.hostView;
            if (view != view2) {
                setBounds(view2.getLeft() - iMax, this.hostView.getTop() - iMax, this.hostView.getRight() + iMax, this.hostView.getBottom() + iMax);
                this.hostRect.set(this.hostView.getLeft(), this.hostView.getTop(), this.hostView.getRight(), this.hostView.getBottom());
            } else {
                int i = -iMax;
                setBounds(i, i, view2.getWidth() + iMax, this.hostView.getHeight() + iMax);
                this.hostRect.set(0.0f, 0.0f, this.hostView.getWidth(), this.hostView.getHeight());
            }
            updateClipPath();
        }

        public final void updateClipPath() {
            this.clipCornerRadius = getCornerRadius();
            this.rect.set(this.hostRect);
            this.clipPath.reset();
            Path path = this.clipPath;
            RectF rectF = this.rect;
            float f = this.clipCornerRadius;
            path.addRoundRect(rectF, f, f, Path.Direction.CW);
        }

        public final void updateShadowPaintIfNeeded() {
            float f = this.appliedShadowRadius;
            float f2 = this.shadowRadius;
            if (f == f2 && this.appliedShadowAlpha == this.shadowAlpha) {
                return;
            }
            this.appliedShadowRadius = f2;
            float f3 = this.shadowAlpha;
            this.appliedShadowAlpha = f3;
            int iApplyAlpha = applyAlpha(ShadowToken.Regular.color, f3 * 0.5f);
            Paint paint = this.shadowPaint;
            float f4 = this.shadowRadius;
            float f5 = this.density;
            ShadowToken shadowToken = ShadowToken.Regular;
            paint.setShadowLayer(f4 * f5, shadowToken.offsetX * f5, shadowToken.offsetY * f5, iApplyAlpha);
        }
    }

    public static /* synthetic */ void $r8$lambda$Mau4FSlFGec3j3nvaUZVGYpmvDM(View view) {
        clearMaterialWithoutShadow(view);
        MiShadowUtils.clearMiShadow(view);
    }

    public static /* synthetic */ void $r8$lambda$ru0orCZUye_FAKvX45PPfNdl12Q(View view) {
        clearMaterialWithoutShadow(view);
        MiShadowUtils.clearMiShadow(view);
    }

    public static /* synthetic */ void $r8$lambda$vveSCkW42FlQu7Di6fnDePiejfw(boolean z, View view, float f, float f2, boolean z2, int i, int[] iArr, ArgbEvaluator argbEvaluator, int[] iArr2, int[] iArr3, int[] iArr4, boolean z3, int i2, int i3, RoundStateDrawable roundStateDrawable, ValueAnimator valueAnimator) {
        float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
        float animatedFraction = valueAnimator.getAnimatedFraction();
        if (z) {
            clearFallbackOuterShadow(view);
            ShadowToken shadowToken = ShadowToken.Regular;
            MiShadowUtils.setMiShadow(view, shadowToken.color, shadowToken.offsetX, shadowToken.offsetY, Math.max(fFloatValue, 2.0f));
        } else {
            float fMax = Math.max(f, f2);
            float f3 = fMax > 0.0f ? fFloatValue / fMax : 0.0f;
            view.setElevation(0.0f);
            setFallbackOuterShadow(view, 15.0f, f3);
        }
        if (z2) {
            for (int i4 = 0; i4 < i; i4++) {
                iArr[i4] = ((Integer) argbEvaluator.evaluate(animatedFraction, Integer.valueOf(iArr2[i4]), Integer.valueOf(iArr3[i4]))).intValue();
            }
            MiuiBlurUtils.setBackgroundBlendConfig(view, iArr, iArr4);
        }
        if (z3) {
            roundStateDrawable.setColor(((Integer) argbEvaluator.evaluate(animatedFraction, Integer.valueOf(i2), Integer.valueOf(i3))).intValue());
        }
    }

    static {
        MaterialToken materialTokenBuild = new MaterialToken.Builder(10, "internal-pured-thin-glass", "light").setColorBlend(ColorBlendToken.Pured_Thin_Glass_Light).setMaskBlur(20).setBloomStroke(BloomStrokeToken.Glass_Stroke_Small_Light).setShadow(ShadowToken.Regular).build();
        Pured_Thin_Glass_Light = materialTokenBuild;
        MaterialToken materialTokenBuild2 = new MaterialToken.Builder(10, "internal-pured-thin-glass", "dark").setColorBlend(ColorBlendToken.Pured_Thin_Glass_Dark).setMaskBlur(20).setBloomStroke(BloomStrokeToken.Glass_Stroke_Small_Dark).setShadow(ShadowToken.Regular).build();
        Pured_Thin_Glass_Dark = materialTokenBuild2;
        MaterialDayNightToken materialDayNightToken = new MaterialDayNightToken(materialTokenBuild, materialTokenBuild2);
        Pured_Thin_Glass = materialDayNightToken;
        sMaterial = MaterialDayNightConfig.create(materialDayNightToken);
        TAG_FALLBACK_OUTER_SHADOW = R.id.floating_fallback_outer_shadow_tag;
        TAG_FALLBACK_OUTER_SHADOW_DETACH_LISTENER = R.id.floating_fallback_outer_shadow_detach_listener_tag;
    }

    public static void animateFloatingEffect(final View view, float f, float f2, long j, boolean z, boolean z2, int[] iArr, int[] iArr2, int[] iArr3, BgColorAnimParams bgColorAnimParams, final Runnable runnable) {
        if (view == null) {
            return;
        }
        cancelFloatingAnimator(view);
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(f, f2);
        valueAnimatorOfFloat.setDuration(j);
        valueAnimatorOfFloat.setInterpolator(new LinearInterpolator());
        valueAnimatorOfFloat.addUpdateListener(createUpdateListener(view, f, f2, z2, iArr, iArr2, iArr3, bgColorAnimParams));
        valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: miuix.appcompat.internal.util.FloatingEffectHelper.1
            public boolean cancelled = false;

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                this.cancelled = true;
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                Runnable runnable2;
                FloatingEffectHelper.removeDetachListener(view);
                if (this.cancelled || (runnable2 = runnable) == null) {
                    return;
                }
                runnable2.run();
            }
        });
        registerDetachListener(view, valueAnimatorOfFloat);
        valueAnimatorOfFloat.start();
    }

    public static void applyMaterialWithoutShadow(View view, MaterialConfig materialConfig) {
        applyMaterialWithoutShadow(view, materialConfig, false);
    }

    public static void applyMaterialWithoutShadow(View view, MaterialConfig materialConfig, boolean z) {
        float f = view.getContext().getResources().getDisplayMetrics().density;
        MaterialConfig.ColorBlendConfig colorBlendConfig = materialConfig.getColorBlendConfig();
        MaterialConfig.BlurConfig blurConfig = materialConfig.getBlurConfig();
        if (colorBlendConfig != null && blurConfig == null) {
            MiuiBlurUtils.setMixEffectEnabled(view, true);
            MiuiBlurUtils.setViewBlurMode(view, 1);
            if (!z) {
                MiuiBlurUtils.setBackgroundBlendConfig(view, colorBlendConfig.blendColors, colorBlendConfig.blendModes);
            }
        } else if (blurConfig != null) {
            MiuiBlurUtils.setMixEffectEnabled(view, false);
            MiuiBlurUtils.setBlurConfig(view, f, blurConfig);
        }
        MaterialConfig.BloomStrokeConfig bloomStrokeConfig = materialConfig.getBloomStrokeConfig();
        if (bloomStrokeConfig != null) {
            HyperBloomStrokeUtils.setBloomStrokeConfig(view, bloomStrokeConfig);
        }
    }

    public static void applyShadowEffect(View view, int i) {
        if (!MiShadowUtils.SUPPORT_WINDOW_MI_SHADOW) {
            view.setElevation(0.0f);
            setFallbackOuterShadow(view, 15.0f, 1.0f);
        } else {
            clearFallbackOuterShadow(view);
            ShadowToken shadowToken = ShadowToken.Regular;
            MiShadowUtils.setMiShadow(view, shadowToken.color, shadowToken.offsetX, shadowToken.offsetY, Math.max(i, 2.0f));
        }
    }

    public static void applyTraditionalBackground(View view, boolean z, boolean z2) {
        int floatingEffectBackgroundColor = getFloatingEffectBackgroundColor(view, z);
        Drawable background = view.getBackground();
        if (!(background instanceof RoundStateDrawable)) {
            view.setBackground(new RoundStateDrawable(floatingEffectBackgroundColor, z2));
            return;
        }
        RoundStateDrawable roundStateDrawable = (RoundStateDrawable) background;
        roundStateDrawable.setColor(floatingEffectBackgroundColor);
        roundStateDrawable.setDarkMode(z2);
    }

    public static void cancelFloatingAnimator(View view) {
        int i = R.id.floating_animator_tag;
        Object tag = view.getTag(i);
        if (tag instanceof ValueAnimator) {
            ((ValueAnimator) tag).cancel();
            view.setTag(i, null);
            setAppliedState(view, null);
        }
        removeDetachListener(view);
    }

    public static void clearElevation(View view) {
        if (view == null) {
            return;
        }
        cancelFloatingAnimator(view);
        clearMaterialWithoutShadow(view);
        MiShadowUtils.clearMiShadow(view);
        view.setElevation(0.0f);
        clearFallbackOuterShadow(view);
        setAppliedState(view, null);
    }

    public static void clearFallbackOuterShadow(View view) {
        clearFallbackOuterShadow(view, false);
    }

    public static void clearFallbackOuterShadow(View view, boolean z) {
        FallbackOuterShadowDrawable fallbackOuterShadow = getFallbackOuterShadow(view);
        if (fallbackOuterShadow != null) {
            fallbackOuterShadow.removeFromOverlay();
            view.setTag(TAG_FALLBACK_OUTER_SHADOW, null);
            if (z) {
                setAppliedState(view, null);
            }
        }
        removeFallbackOuterShadowDetachListener(view);
    }

    public static void clearMaterialWithoutShadow(View view) {
        MiuiBlurUtils.setBlurConfig(view, 0.0f, null);
        HyperBloomStrokeUtils.clearBloomStroke(view);
    }

    public static ValueAnimator.AnimatorUpdateListener createUpdateListener(View view, final float f, final float f2, final boolean z, final int[] iArr, final int[] iArr2, int[] iArr3, BgColorAnimParams bgColorAnimParams) {
        View view2;
        final int i;
        final int i2;
        int[] iArrCopyOf = iArr3;
        final ArgbEvaluator argbEvaluator = new ArgbEvaluator();
        final boolean z2 = (iArr == null || iArr2 == null || iArrCopyOf == null || iArr.length <= 0) ? false : true;
        final int iMin = z2 ? Math.min(iArr.length, Math.min(iArr2.length, iArrCopyOf.length)) : 0;
        RoundStateDrawable roundStateDrawableEnsureBgDrawable = null;
        final int[] iArr4 = z2 ? new int[iMin] : null;
        if (z2 && iArrCopyOf.length != iMin) {
            iArrCopyOf = Arrays.copyOf(iArrCopyOf, iMin);
        }
        final int[] iArr5 = iArrCopyOf;
        if (bgColorAnimParams != null) {
            view2 = view;
            roundStateDrawableEnsureBgDrawable = ensureBgDrawable(view2, bgColorAnimParams.isDarkMode);
            int color = roundStateDrawableEnsureBgDrawable.getColor();
            i2 = bgColorAnimParams.targetColor;
            i = color;
        } else {
            view2 = view;
            i = 0;
            i2 = 0;
        }
        final RoundStateDrawable roundStateDrawable = roundStateDrawableEnsureBgDrawable;
        final View view3 = view2;
        final boolean z3 = (bgColorAnimParams == null || i == i2) ? false : true;
        return new ValueAnimator.AnimatorUpdateListener() { // from class: miuix.appcompat.internal.util.FloatingEffectHelper$$ExternalSyntheticLambda3
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                FloatingEffectHelper.$r8$lambda$vveSCkW42FlQu7Di6fnDePiejfw(z, view3, f, f2, z2, iMin, iArr4, argbEvaluator, iArr, iArr2, iArr5, z3, i, i2, roundStateDrawable, valueAnimator);
            }
        };
    }

    public static RoundStateDrawable ensureBgDrawable(View view, boolean z) {
        RoundStateDrawable roundStateDrawable;
        Drawable background = view.getBackground();
        if (background instanceof RoundStateDrawable) {
            roundStateDrawable = (RoundStateDrawable) background;
        } else {
            RoundStateDrawable roundStateDrawable2 = new RoundStateDrawable(z ? 0 : 16777215, z);
            view.setBackground(roundStateDrawable2);
            roundStateDrawable = roundStateDrawable2;
        }
        roundStateDrawable.setDarkMode(z);
        int color = roundStateDrawable.getColor();
        if ((color >>> 24) == 0) {
            int i = z ? 0 : 16777215;
            if (color != i) {
                roundStateDrawable.setColor(i);
            }
        }
        return roundStateDrawable;
    }

    public static FloatingAppliedState getAppliedState(View view) {
        Object tag = view.getTag(R.id.floating_applied_state_tag);
        if (tag instanceof FloatingAppliedState) {
            return (FloatingAppliedState) tag;
        }
        return null;
    }

    public static FallbackOuterShadowDrawable getFallbackOuterShadow(View view) {
        Object tag = view.getTag(TAG_FALLBACK_OUTER_SHADOW);
        if (tag instanceof FallbackOuterShadowDrawable) {
            return (FallbackOuterShadowDrawable) tag;
        }
        return null;
    }

    public static View getFallbackShadowOverlayOwner(View view) {
        return view.getParent() instanceof ViewGroup ? (ViewGroup) view.getParent() : view;
    }

    public static int getFloatingEffectBackgroundColor(View view, boolean z) {
        return view.getResources().getColor(z ? R.color.miuix_appcompat_floating_effect_bg_color_light : R.color.miuix_appcompat_floating_effect_bg_color_dark);
    }

    public static int getRadius(boolean z) {
        if (z) {
            return ShadowToken.Regular.radius;
        }
        return 20;
    }

    public static void handleAnimatedFloatingOff(final View view, boolean z, boolean z2) {
        int radius = getRadius(z);
        boolean z3 = !z;
        boolean z4 = MiShadowUtils.SUPPORT_WINDOW_MI_SHADOW;
        if (!isMaterialEffectEnabled(view, z2)) {
            animateFloatingEffect(view, radius, 0.0f, 350L, z, z4, null, null, null, new BgColorAnimParams(!z ? 0 : 16777215, z3), new Runnable() { // from class: miuix.appcompat.internal.util.FloatingEffectHelper$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    MiShadowUtils.clearMiShadow(view);
                }
            });
            return;
        }
        MaterialConfig materialConfig = sMaterial.get(z);
        MaterialConfig.ColorBlendConfig colorBlendConfig = materialConfig != null ? materialConfig.getColorBlendConfig() : null;
        if (colorBlendConfig == null) {
            animateFloatingEffect(view, radius, 0.0f, 350L, z, z4, null, null, null, null, new Runnable() { // from class: miuix.appcompat.internal.util.FloatingEffectHelper$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    FloatingEffectHelper.$r8$lambda$Mau4FSlFGec3j3nvaUZVGYpmvDM(view);
                }
            });
        } else {
            int[] iArr = colorBlendConfig.blendColors;
            animateFloatingEffect(view, radius, 0.0f, 350L, z, z4, iArr, makeTransparentColors(iArr), colorBlendConfig.blendModes, null, new Runnable() { // from class: miuix.appcompat.internal.util.FloatingEffectHelper$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    FloatingEffectHelper.$r8$lambda$ru0orCZUye_FAKvX45PPfNdl12Q(view);
                }
            });
        }
    }

    public static void handleAnimatedFloatingOn(View view, boolean z, boolean z2) {
        MaterialConfig materialConfig;
        int radius = getRadius(z);
        boolean z3 = !z;
        boolean z4 = MiShadowUtils.SUPPORT_WINDOW_MI_SHADOW;
        if (!isMaterialEffectEnabled(view, z2) || (materialConfig = sMaterial.get(z)) == null) {
            clearMaterialWithoutShadow(view);
            animateFloatingEffect(view, 0.0f, radius, 350L, z, z4, null, null, null, new BgColorAnimParams(getFloatingEffectBackgroundColor(view, z), z3), null);
            return;
        }
        setTransparentBackground(view, z3);
        applyMaterialWithoutShadow(view, materialConfig, true);
        MaterialConfig.ColorBlendConfig colorBlendConfig = materialConfig.getColorBlendConfig();
        if (colorBlendConfig != null) {
            animateFloatingEffect(view, 0.0f, radius, 350L, z, z4, makeTransparentColors(colorBlendConfig.blendColors), colorBlendConfig.blendColors, colorBlendConfig.blendModes, null, null);
        } else {
            animateFloatingEffect(view, 0.0f, radius, 350L, z, z4, null, null, null, null, null);
        }
    }

    public static void handleFloatingOff(View view, boolean z, boolean z2) {
        clearMaterialWithoutShadow(view);
        MiShadowUtils.clearMiShadow(view);
        view.setElevation(0.0f);
        clearFallbackOuterShadow(view);
        setTransparentBackground(view, !z);
    }

    public static void handleFloatingOn(View view, boolean z, boolean z2) {
        MaterialConfig materialConfig;
        int radius = getRadius(z);
        boolean z3 = !z;
        if (!isMaterialEffectEnabled(view, z2) || (materialConfig = sMaterial.get(z)) == null) {
            clearMaterialWithoutShadow(view);
            applyTraditionalBackground(view, z, z3);
            applyShadowEffect(view, radius);
        } else {
            setTransparentBackground(view, z3);
            applyMaterialWithoutShadow(view, materialConfig);
            applyShadowEffect(view, radius);
        }
    }

    public static boolean isMaterialEffectEnabled(View view, boolean z) {
        return z && HyperMaterialUtils.isSupport() && HyperMaterialUtils.isFeatureEnable(view.getContext());
    }

    public static int[] makeTransparentColors(int[] iArr) {
        int[] iArr2 = new int[iArr.length];
        for (int i = 0; i < iArr.length; i++) {
            iArr2[i] = iArr[i] & 16777215;
        }
        return iArr2;
    }

    public static void registerDetachListener(View view, ValueAnimator valueAnimator) {
        View.OnAttachStateChangeListener onAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: miuix.appcompat.internal.util.FloatingEffectHelper.3
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view2) {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view2) {
                FloatingEffectHelper.cancelFloatingAnimator(view2);
            }
        };
        view.addOnAttachStateChangeListener(onAttachStateChangeListener);
        view.setTag(R.id.floating_animator_detach_listener_tag, onAttachStateChangeListener);
        view.setTag(R.id.floating_animator_tag, valueAnimator);
    }

    public static void registerFallbackOuterShadowDetachListener(View view) {
        int i = TAG_FALLBACK_OUTER_SHADOW_DETACH_LISTENER;
        if (view.getTag(i) != null) {
            return;
        }
        View.OnAttachStateChangeListener onAttachStateChangeListener = new View.OnAttachStateChangeListener() { // from class: miuix.appcompat.internal.util.FloatingEffectHelper.2
            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewAttachedToWindow(View view2) {
            }

            @Override // android.view.View.OnAttachStateChangeListener
            public void onViewDetachedFromWindow(View view2) {
                FloatingEffectHelper.clearFallbackOuterShadow(view2, true);
            }
        };
        view.addOnAttachStateChangeListener(onAttachStateChangeListener);
        view.setTag(i, onAttachStateChangeListener);
    }

    public static void removeDetachListener(View view) {
        int i = R.id.floating_animator_detach_listener_tag;
        Object tag = view.getTag(i);
        if (tag instanceof View.OnAttachStateChangeListener) {
            view.removeOnAttachStateChangeListener((View.OnAttachStateChangeListener) tag);
            view.setTag(i, null);
        }
    }

    public static void removeFallbackOuterShadowDetachListener(View view) {
        int i = TAG_FALLBACK_OUTER_SHADOW_DETACH_LISTENER;
        Object tag = view.getTag(i);
        if (tag instanceof View.OnAttachStateChangeListener) {
            view.removeOnAttachStateChangeListener((View.OnAttachStateChangeListener) tag);
            view.setTag(i, null);
        }
    }

    public static void setAppliedState(View view, FloatingAppliedState floatingAppliedState) {
        view.setTag(R.id.floating_applied_state_tag, floatingAppliedState);
    }

    public static void setFallbackOuterShadow(View view, float f, float f2) {
        FallbackOuterShadowDrawable fallbackOuterShadow = getFallbackOuterShadow(view);
        if (f <= 0.0f || f2 <= 0.0f) {
            clearFallbackOuterShadow(view);
            return;
        }
        if (fallbackOuterShadow == null) {
            View fallbackShadowOverlayOwner = getFallbackShadowOverlayOwner(view);
            FallbackOuterShadowDrawable fallbackOuterShadowDrawable = new FallbackOuterShadowDrawable(view, fallbackShadowOverlayOwner);
            fallbackShadowOverlayOwner.getOverlay().add(fallbackOuterShadowDrawable);
            view.setTag(TAG_FALLBACK_OUTER_SHADOW, fallbackOuterShadowDrawable);
            registerFallbackOuterShadowDetachListener(view);
            fallbackOuterShadow = fallbackOuterShadowDrawable;
        }
        fallbackOuterShadow.setShadow(f, f2);
    }

    public static void setFloatingState(View view, boolean z, boolean z2, boolean z3) {
        if (view == null) {
            return;
        }
        FloatingAppliedState floatingAppliedState = new FloatingAppliedState(z, z2, z3);
        if (floatingAppliedState.equals(getAppliedState(view))) {
            return;
        }
        if (z) {
            handleFloatingOn(view, z2, z3);
        } else {
            handleFloatingOff(view, z2, z3);
        }
        setAppliedState(view, floatingAppliedState);
    }

    public static void setTransparentBackground(View view, boolean z) {
        int i = z ? 0 : 16777215;
        Drawable background = view.getBackground();
        if (!(background instanceof RoundStateDrawable)) {
            view.setBackground(new RoundStateDrawable(i, z));
            return;
        }
        RoundStateDrawable roundStateDrawable = (RoundStateDrawable) background;
        roundStateDrawable.setColor(i);
        roundStateDrawable.setDarkMode(z);
    }

    public static void updateViewFloatingState(View view, boolean z, boolean z2, boolean z3) {
        if (view == null) {
            return;
        }
        FloatingAppliedState floatingAppliedState = new FloatingAppliedState(z, z2, z3);
        FloatingAppliedState appliedState = getAppliedState(view);
        if (floatingAppliedState.equals(appliedState)) {
            return;
        }
        boolean z4 = false;
        boolean z5 = appliedState != null && appliedState.floatingMode == z;
        if (appliedState == null && !z) {
            z4 = true;
        }
        if (z5 || z4) {
            cancelFloatingAnimator(view);
            if (z) {
                handleFloatingOn(view, z2, z3);
            } else {
                handleFloatingOff(view, z2, z3);
            }
        } else if (z) {
            handleAnimatedFloatingOn(view, z2, z3);
        } else {
            handleAnimatedFloatingOff(view, z2, z3);
        }
        setAppliedState(view, floatingAppliedState);
    }
}
