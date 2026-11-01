package miuix.cardview;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewOutlineProvider;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import com.miui.support.cardview.R;
import com.miui.support.drawable.CardStateDrawable;
import java.lang.reflect.InvocationTargetException;
import miuix.core.util.HyperBloomStrokeUtils;
import miuix.core.util.HyperMaterialUtils;
import miuix.core.util.MaterialConfig;
import miuix.core.util.MaterialDayNightConfig;
import miuix.core.util.MiuiBlurUtils;
import miuix.device.DeviceUtils;
import miuix.graphics.shadow.DropShadowConfig;
import miuix.graphics.shadow.DropShadowHelper;
import miuix.internal.util.AttributeResolver;
import miuix.reflect.ReflectionHelper;
import miuix.theme.token.MaterialDayNightToken;
import miuix.theme.token.MaterialToken;
import miuix.view.BlurableWidget;
import miuix.view.DynamicThemeWidget;
import miuix.view.MiuiBlurUiHelper;

/* JADX INFO: loaded from: classes2.dex */
public class HyperCardView extends CardView implements BlurableWidget, DynamicThemeWidget {
    public boolean mApplyBlur;
    public Drawable mBackgroundWithoutBlur;
    public int[] mBlendColorModes;
    public int[] mBlendColors;
    public int mBlurRadius;
    public int mBlurRadiusDp;
    public final MiuiBlurUiHelper mBlurUiHelper;
    public MaterialConfig mCurrentMaterial;
    public boolean mDrawStrokeOverlay;
    public final DropShadowHelper mDropShadowHelper;
    public float mIsBlendColorAnimProgress;
    public boolean mIsBlendColorAnimRunning;
    public int[] mLastBlendColorModes;
    public int[] mLastBlendColors;
    public MaterialDayNightConfig mMaterial;
    public ValueAnimator mRunningBgAnimator;
    public int mShadowColor;
    public boolean mShadowConfigDirty;
    public float mShadowDispersion;
    public float mShadowDxDp;
    public float mShadowDyDp;
    public float mShadowRadiusDp;
    public int mStrokeColor;
    public float[] mStrokeGradientColorPositions;
    public int[] mStrokeGradientColors;
    public float mStrokeWidth;
    public int[] mTmpBlendColorModes;
    public boolean mUseCompatShadow;
    public boolean mUseSmooth;
    public float mUserAlpha;
    public int mUserThemeType;

    /* JADX INFO: renamed from: miuix.cardview.HyperCardView$5, reason: invalid class name */
    class AnonymousClass5 implements ValueAnimator.AnimatorUpdateListener {
        public final /* synthetic */ HyperCardView this$0;

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            Integer num = (Integer) valueAnimator.getAnimatedValue();
            if (num != null) {
                this.this$0.mBackgroundWithoutBlur.setAlpha(num.intValue());
                this.this$0.invalidate();
                if (num.intValue() == 0) {
                    this.this$0.mRunningBgAnimator = null;
                }
            }
        }
    }

    /* JADX INFO: renamed from: miuix.cardview.HyperCardView$6, reason: invalid class name */
    class AnonymousClass6 implements ValueAnimator.AnimatorUpdateListener {
        public final /* synthetic */ HyperCardView this$0;

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            Integer num = (Integer) valueAnimator.getAnimatedValue();
            if (num != null) {
                this.this$0.mBackgroundWithoutBlur.setAlpha(num.intValue());
                this.this$0.invalidate();
                if (num.intValue() == 255) {
                    this.this$0.mBlurUiHelper.applyBlur(false);
                    this.this$0.mRunningBgAnimator = null;
                }
            }
        }
    }

    public static class BlendColorTransitionController {
        public BlendColorTransitionListener mListener;

        public void setListener(BlendColorTransitionListener blendColorTransitionListener) {
            this.mListener = blendColorTransitionListener;
        }
    }

    public interface BlendColorTransitionListener {
        void onCancel();

        void onEnd();

        void onStart();

        void onUpdate(float f);
    }

    public HyperCardView(Context context) {
        this(context, null);
    }

    public HyperCardView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.cardViewStyle);
    }

    @SuppressLint({"CustomViewStyleable"})
    public HyperCardView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mApplyBlur = false;
        this.mBackgroundWithoutBlur = null;
        this.mRunningBgAnimator = null;
        this.mLastBlendColors = null;
        this.mLastBlendColorModes = null;
        this.mBlendColors = null;
        this.mBlendColorModes = null;
        this.mTmpBlendColorModes = null;
        this.mIsBlendColorAnimRunning = false;
        this.mIsBlendColorAnimProgress = 0.0f;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CardView, i, 0);
        Resources resources = context.getResources();
        float f = resources.getDisplayMetrics().density;
        int resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.CardView_outlineStyle, -1);
        if (resourceId != -1) {
            TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(resourceId, R.styleable.OutlineProvider);
            String string = typedArrayObtainStyledAttributes2.getString(R.styleable.OutlineProvider_android_name);
            if (!TextUtils.isEmpty(string)) {
                setOutlineProviderFromAttribute(context, string, resourceId);
            }
            typedArrayObtainStyledAttributes2.recycle();
        }
        this.mUseCompatShadow = typedArrayObtainStyledAttributes.getBoolean(R.styleable.CardView_miuix_useCompatShadow, false);
        this.mShadowDispersion = typedArrayObtainStyledAttributes.getFloat(R.styleable.CardView_miuix_shadowDispersion, 0.0f);
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.CardView_android_shadowRadius, 0);
        this.mShadowRadiusDp = dimensionPixelSize == 0 ? 0.0f : (dimensionPixelSize / f) + 0.5f;
        int dimensionPixelSize2 = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.CardView_android_shadowDx, 0);
        this.mShadowDxDp = dimensionPixelSize2 == 0 ? 0.0f : (dimensionPixelSize2 / f) + 0.5f;
        int dimensionPixelSize3 = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.CardView_android_shadowDy, 0);
        this.mShadowDyDp = dimensionPixelSize3 == 0 ? 0.0f : (dimensionPixelSize3 / f) + 0.5f;
        this.mShadowColor = typedArrayObtainStyledAttributes.getColor(R.styleable.CardView_android_shadowColor, 0);
        this.mUseSmooth = typedArrayObtainStyledAttributes.getBoolean(R.styleable.CardView_miuix_useSmooth, true);
        int dimensionPixelSize4 = typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.CardView_miuix_blurRadius, 0);
        this.mBlurRadius = dimensionPixelSize4;
        this.mBlurRadiusDp = dimensionPixelSize4 == 0 ? 0 : (int) ((dimensionPixelSize4 / f) + 0.5f);
        boolean z = typedArrayObtainStyledAttributes.getBoolean(R.styleable.CardView_miuix_blurSelfBackground, false);
        if (checkNeedSmooth()) {
            setSmoothCornerEnable(true);
        }
        this.mDrawStrokeOverlay = typedArrayObtainStyledAttributes.getBoolean(R.styleable.CardView_miuix_drawStrokeOverlay, false);
        this.mStrokeWidth = typedArrayObtainStyledAttributes.getDimension(R.styleable.CardView_miuix_strokeWidth, 0.0f);
        this.mStrokeColor = typedArrayObtainStyledAttributes.getColor(R.styleable.CardView_miuix_strokeColor, 0);
        int resourceId2 = typedArrayObtainStyledAttributes.getResourceId(R.styleable.CardView_miuix_strokeGradientColors, 0);
        if (resourceId2 > 0) {
            this.mStrokeGradientColors = resources.getIntArray(resourceId2);
        }
        int resourceId3 = typedArrayObtainStyledAttributes.getResourceId(R.styleable.CardView_miuix_strokeGradientPositions, 0);
        if (resourceId3 > 0) {
            TypedArray typedArrayObtainTypedArray = resources.obtainTypedArray(resourceId3);
            this.mStrokeGradientColorPositions = new float[typedArrayObtainTypedArray.length()];
            for (int i2 = 0; i2 < typedArrayObtainTypedArray.length(); i2++) {
                this.mStrokeGradientColorPositions[i2] = resources.getFraction(typedArrayObtainTypedArray.getResourceId(i2, 0), 1, 1);
            }
            typedArrayObtainTypedArray.recycle();
        }
        int resourceId4 = typedArrayObtainStyledAttributes.getResourceId(R.styleable.CardView_cardBlendColors, 0);
        if (resourceId4 > 0) {
            this.mBlendColors = resources.getIntArray(resourceId4);
        }
        int resourceId5 = typedArrayObtainStyledAttributes.getResourceId(R.styleable.CardView_cardBlendColorModes, 0);
        if (resourceId5 > 0) {
            this.mBlendColorModes = resources.getIntArray(resourceId5);
        }
        typedArrayObtainStyledAttributes.recycle();
        this.mMaterial = updateMaterial();
        boolean zResolveBoolean = AttributeResolver.resolveBoolean(getContext(), R.attr.isLightTheme, true);
        this.mCurrentMaterial = this.mMaterial.get(zResolveBoolean);
        replaceHyperBackground();
        this.mBackgroundWithoutBlur = getBackground();
        if (HyperMaterialUtils.isEnable()) {
            MiuiBlurUiHelper miuiBlurUiHelper = new MiuiBlurUiHelper(context, this, false, true, z, new MiuiBlurUiHelper.BlurStateCallback() { // from class: miuix.cardview.HyperCardView.1
                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public boolean isLightTheme() {
                    return AttributeResolver.resolveBoolean(HyperCardView.this.getContext(), R.attr.isLightTheme, true);
                }

                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public void onBlurApplyStateChanged(boolean z2) {
                    HyperCardView.this.mApplyBlur = z2;
                    if (HyperCardView.this.mBackgroundWithoutBlur != null) {
                        HyperCardView.this.mBackgroundWithoutBlur.setAlpha(HyperCardView.this.mApplyBlur ? 0 : 255);
                    }
                }

                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public void onBlurEnableStateChanged(boolean z2) {
                }

                @Override // miuix.view.MiuiBlurUiHelper.BlurStateCallback
                public void onCreateBlurParams(MiuiBlurUiHelper miuiBlurUiHelper2) {
                    if (HyperCardView.this.mBlendColors == null || HyperCardView.this.mBlendColorModes == null) {
                        return;
                    }
                    if (!HyperCardView.this.mIsBlendColorAnimRunning || HyperCardView.this.mLastBlendColors == null) {
                        miuiBlurUiHelper2.setBlurParams(HyperCardView.this.mBlendColors, HyperCardView.this.mBlendColorModes, HyperCardView.this.mBlurRadiusDp);
                        return;
                    }
                    float f2 = 1.0f - HyperCardView.this.mIsBlendColorAnimProgress;
                    float f3 = HyperCardView.this.mIsBlendColorAnimProgress;
                    int[] iArr = new int[HyperCardView.this.mLastBlendColors.length + HyperCardView.this.mBlendColors.length];
                    int length = HyperCardView.this.mLastBlendColors.length;
                    for (int i3 = 0; i3 < HyperCardView.this.mLastBlendColors.length; i3++) {
                        iArr[i3] = (((int) ((HyperCardView.this.mLastBlendColors[i3] >>> 24) * f2)) << 24) | (16777215 & HyperCardView.this.mLastBlendColors[i3]);
                    }
                    for (int i4 = 0; i4 < HyperCardView.this.mBlendColors.length; i4++) {
                        iArr[length + i4] = (((int) ((HyperCardView.this.mBlendColors[i4] >>> 24) * f3)) << 24) | (HyperCardView.this.mBlendColors[i4] & 16777215);
                    }
                    miuiBlurUiHelper2.setBlurParams(iArr, HyperCardView.this.mTmpBlendColorModes, HyperCardView.this.mBlurRadiusDp);
                }
            });
            this.mBlurUiHelper = miuiBlurUiHelper;
            setSupportBlur(true);
            setEnableBlur(true);
            miuiBlurUiHelper.applyBlur(HyperMaterialUtils.isFeatureEnable(context) && this.mBlurRadius > 0);
        } else {
            this.mBlurUiHelper = null;
        }
        this.mShadowConfigDirty = false;
        DropShadowHelper dropShadowHelper = new DropShadowHelper(context, updateShadowConfig(), zResolveBoolean);
        this.mDropShadowHelper = dropShadowHelper;
        if (this.mUseCompatShadow) {
            dropShadowHelper.setEnableMiShadow(false);
        }
        if (!dropShadowHelper.isEnableMiShadow()) {
            setCardElevation(dimensionPixelSize);
            setOutlineAmbientShadowColor(-16777216);
            setOutlineSpotShadowColor(this.mShadowColor);
        }
        if (getAlpha() == 1.0f) {
            setAlpha(1.0f);
        }
    }

    @Nullable
    private RoundRectDrawable getHyperBackground() {
        Drawable drawable = this.mBackgroundWithoutBlur;
        if (drawable instanceof RoundRectDrawable) {
            return (RoundRectDrawable) drawable;
        }
        return null;
    }

    private void setSmoothCornerEnable(boolean z) {
        try {
            ReflectionHelper.invoke(View.class, this, "setSmoothCornerEnabled", new Class[]{Boolean.TYPE}, Boolean.valueOf(z));
        } catch (Exception e) {
            Log.e("MiuiX.HyperCardView", "setSmoothCornerEnabled failed:" + e.getMessage());
        }
    }

    @Override // miuix.view.BlurableWidget
    public void applyBlur(boolean z) {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper == null) {
            return;
        }
        miuiBlurUiHelper.applyBlur(z);
    }

    public final boolean checkNeedSmooth() {
        return !isCommonLiteStrategy() && this.mUseSmooth;
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        RoundRectDrawable hyperBackground;
        super.draw(canvas);
        if (!this.mDrawStrokeOverlay || (hyperBackground = getHyperBackground()) == null) {
            return;
        }
        hyperBackground.drawStroke(canvas);
    }

    @Override // androidx.cardview.widget.CardView
    @NonNull
    public ColorStateList getCardBackgroundColor() {
        Drawable drawable = this.mBackgroundWithoutBlur;
        return drawable instanceof RoundRectDrawable ? ((RoundRectDrawable) drawable).getColor() : super.getCardBackgroundColor();
    }

    @Override // miuix.view.HyperMaterialWidget
    @Nullable
    public MaterialConfig getCurrentMaterial() {
        return this.mCurrentMaterial;
    }

    @Override // miuix.view.HyperMaterialWidget
    @Nullable
    public MaterialDayNightConfig getMaterial() {
        return this.mMaterial;
    }

    public int getShadowColor() {
        return this.mShadowColor;
    }

    public int getStrokeColor() {
        return this.mStrokeColor;
    }

    public float getStrokeWidth() {
        return this.mStrokeWidth;
    }

    public int getThemeType() {
        return this.mUserThemeType;
    }

    public boolean hasThemeType() {
        return this.mUserThemeType > 0;
    }

    public boolean isApplyBlur() {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper == null) {
            return false;
        }
        return miuiBlurUiHelper.isApplyBlur();
    }

    public final boolean isCommonLiteStrategy() {
        return DeviceUtils.isMiuiLiteV2() || DeviceUtils.isLiteV1StockPlus();
    }

    public boolean isEnableBlur() {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper == null) {
            return false;
        }
        return miuiBlurUiHelper.isEnableBlur();
    }

    public final boolean isNeedRestrictAlpha() {
        DropShadowHelper dropShadowHelper = this.mDropShadowHelper;
        if (dropShadowHelper == null) {
            return false;
        }
        return ((!dropShadowHelper.isEnableMiShadow() && (getCardElevation() > 0.0f ? 1 : (getCardElevation() == 0.0f ? 0 : -1)) > 0) && !getCardBackgroundColor().isOpaque()) || (isEnableBlur() && isApplyBlur());
    }

    public boolean isSupportBlur() {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper == null) {
            return false;
        }
        return miuiBlurUiHelper.isSupportBlur();
    }

    @Override // android.view.View
    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        updateMaterialEffect();
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        if (this.mShadowConfigDirty) {
            this.mShadowConfigDirty = false;
            DropShadowHelper dropShadowHelper = this.mDropShadowHelper;
            if (dropShadowHelper != null) {
                dropShadowHelper.updateDropShadowConfig(updateShadowConfig());
                this.mDropShadowHelper.updateViewShadow(this, 2);
            }
        }
        super.onDraw(canvas);
    }

    @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        DropShadowHelper dropShadowHelper = this.mDropShadowHelper;
        if (dropShadowHelper != null) {
            dropShadowHelper.updateShadowRect(i, i2, i3, i4);
            if (this.mShadowRadiusDp > 0.0f) {
                this.mDropShadowHelper.enableViewShadow(this, true, 2);
            } else {
                this.mDropShadowHelper.enableViewShadow(this, false, 2);
            }
        }
    }

    public final void replaceHyperBackground() {
        RoundRectDrawable roundRectDrawable = new RoundRectDrawable(getCardBackgroundColor(), getRadius(), getStrokeWidth(), getStrokeColor());
        roundRectDrawable.setStrokeGradientColors(this.mStrokeGradientColors);
        roundRectDrawable.setStrokeColorGradientPositions(this.mStrokeGradientColorPositions);
        setBackground(roundRectDrawable);
        Drawable foreground = getForeground();
        if (foreground instanceof CardStateDrawable) {
            ((CardStateDrawable) foreground).setRadius((int) getRadius());
        }
    }

    @Override // android.view.View
    public void setAlpha(float f) {
        this.mUserAlpha = f;
        if (!isNeedRestrictAlpha() || this.mUserAlpha <= 0.99f) {
            super.setAlpha(f);
        } else {
            super.setAlpha(0.99f);
        }
    }

    public void setBlendColorParams(int[] iArr, int[] iArr2) {
        setBlendColorParams(iArr, iArr2, this.mBlendColors != null, null);
    }

    public void setBlendColorParams(int[] iArr, int[] iArr2, @Nullable BlendColorTransitionController blendColorTransitionController) {
        setBlendColorParams(iArr, iArr2, this.mBlendColors != null, blendColorTransitionController);
    }

    public void setBlendColorParams(int[] iArr, int[] iArr2, boolean z, @Nullable BlendColorTransitionController blendColorTransitionController) {
        MaterialDayNightConfig materialDayNightConfig = this.mMaterial;
        this.mBlendColors = iArr;
        this.mBlendColorModes = iArr2;
        MaterialDayNightConfig materialDayNightConfigUpdateMaterial = updateMaterial();
        this.mMaterial = materialDayNightConfigUpdateMaterial;
        if (materialDayNightConfigUpdateMaterial != null) {
            this.mCurrentMaterial = this.mMaterial.get(AttributeResolver.resolveBoolean(getContext(), R.attr.isLightTheme, true));
        }
        updateBlendParams(z, materialDayNightConfig);
        if (z) {
            if (this.mLastBlendColors == null && this.mBlendColors == null) {
                return;
            }
            startBlurChangedAnimation(blendColorTransitionController);
            return;
        }
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper != null) {
            miuiBlurUiHelper.resetBlurParams();
            this.mBlurUiHelper.refreshBlur();
        }
    }

    public void setBlendColorParamsWithoutAnim(int[] iArr, int[] iArr2) {
        setBlendColorParams(iArr, iArr2, false, null);
    }

    public void setBlurRadius(int i) {
        if (this.mBlurRadius != i) {
            this.mBlurRadius = i;
            this.mBlurRadiusDp = i == 0 ? 0 : (int) ((i / getContext().getResources().getDisplayMetrics().density) + 0.5f);
            if (this.mBlurUiHelper != null) {
                MaterialDayNightConfig materialDayNightConfigUpdateMaterial = updateMaterial();
                this.mMaterial = materialDayNightConfigUpdateMaterial;
                if (materialDayNightConfigUpdateMaterial != null) {
                    this.mCurrentMaterial = this.mMaterial.get(AttributeResolver.resolveBoolean(getContext(), R.attr.isLightTheme, true));
                }
                this.mBlurUiHelper.resetBlurParams();
                if (isApplyBlur() && i == 0) {
                    this.mBlurUiHelper.applyBlur(false);
                } else {
                    this.mBlurUiHelper.refreshBlur();
                }
            }
        }
    }

    @Override // androidx.cardview.widget.CardView
    public void setCardBackgroundColor(int i) {
        setCardBackgroundColor(ColorStateList.valueOf(i));
    }

    @Override // androidx.cardview.widget.CardView
    public void setCardBackgroundColor(@Nullable ColorStateList colorStateList) {
        RoundRectDrawable hyperBackground = getHyperBackground();
        if (hyperBackground != null) {
            hyperBackground.setColor(colorStateList);
        } else {
            super.setCardBackgroundColor(colorStateList);
        }
        this.mShadowConfigDirty = true;
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper != null) {
            miuiBlurUiHelper.resetBlurParams();
            this.mBlurUiHelper.refreshBlur();
        }
    }

    @Override // androidx.cardview.widget.CardView
    public void setCardElevation(float f) {
        DropShadowHelper dropShadowHelper = this.mDropShadowHelper;
        if (dropShadowHelper != null && dropShadowHelper.isEnableMiShadow()) {
            setShadowRadius(f);
            super.setCardElevation(0.0f);
        } else {
            if (isNeedRestrictAlpha()) {
                super.setAlpha(0.99f);
            } else {
                super.setAlpha(this.mUserAlpha);
            }
            super.setCardElevation(f);
        }
    }

    public void setDrawStrokeOverlay(boolean z) {
        if (this.mDrawStrokeOverlay != z) {
            this.mDrawStrokeOverlay = z;
            RoundRectDrawable hyperBackground = getHyperBackground();
            if (hyperBackground != null) {
                hyperBackground.setDrawStrokeOverlay(this.mDrawStrokeOverlay);
                invalidate();
            }
        }
    }

    public void setEnableBlur(boolean z) {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper == null) {
            return;
        }
        miuiBlurUiHelper.setEnableBlur(z);
    }

    @Override // miuix.view.HyperMaterialWidget
    public void setMaterial(@Nullable MaterialConfig materialConfig) {
        setMaterial(new MaterialDayNightConfig(materialConfig));
    }

    @Override // miuix.view.HyperMaterialWidget
    public void setMaterial(@Nullable MaterialDayNightConfig materialDayNightConfig) {
        if (isSupportBlur()) {
            if (materialDayNightConfig != null) {
                this.mMaterial = materialDayNightConfig;
                updateMaterialEffect();
            } else {
                this.mMaterial = null;
                applyBlur(false);
                HyperBloomStrokeUtils.clearBloomStroke(this);
            }
        }
    }

    public void setMaterial(MaterialDayNightToken materialDayNightToken) {
        setMaterial(MaterialDayNightConfig.create(materialDayNightToken));
    }

    public void setMaterial(MaterialToken materialToken) {
        setMaterial(MaterialDayNightConfig.create(new MaterialDayNightToken(materialToken)));
    }

    public final void setOutlineProviderFromAttribute(Context context, String str, int i) {
        try {
            Class<? extends U> clsAsSubclass = Class.forName(str).asSubclass(ViewOutlineProvider.class);
            try {
                setOutlineProvider((ViewOutlineProvider) clsAsSubclass.getConstructor(Context.class, Integer.TYPE).newInstance(context, Integer.valueOf(i)));
            } catch (IllegalAccessException | NoSuchMethodException unused) {
                try {
                    setOutlineProvider((ViewOutlineProvider) clsAsSubclass.getConstructor(new Class[0]).newInstance(new Object[0]));
                } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            } catch (InstantiationException | InvocationTargetException e2) {
                throw new RuntimeException(e2);
            }
        } catch (ClassNotFoundException unused2) {
            throw new NoClassDefFoundError(str);
        }
    }

    @Override // android.view.View
    public void setOutlineSpotShadowColor(int i) {
        if (!this.mDropShadowHelper.isEnableMiShadow()) {
            super.setOutlineSpotShadowColor(i);
        } else {
            setShadowColor(i);
            super.setOutlineSpotShadowColor(0);
        }
    }

    @Override // androidx.cardview.widget.CardView
    public void setRadius(float f) {
        RoundRectDrawable hyperBackground = getHyperBackground();
        if (hyperBackground != null) {
            hyperBackground.setRadius(f);
        } else {
            super.setRadius(f);
        }
        Drawable foreground = getForeground();
        if (foreground instanceof CardStateDrawable) {
            ((CardStateDrawable) foreground).setRadius((int) f);
        }
    }

    public void setShadowColor(int i) {
        if (this.mShadowColor != i) {
            this.mShadowColor = i;
            this.mShadowConfigDirty = true;
            if (this.mDropShadowHelper.isEnableMiShadow()) {
                super.setOutlineSpotShadowColor(0);
            } else {
                setOutlineSpotShadowColor(i);
            }
            invalidate();
        }
    }

    public void setShadowDx(float f) {
        if (this.mShadowDxDp != f) {
            this.mShadowDxDp = f;
            this.mShadowConfigDirty = true;
            invalidate();
        }
    }

    public void setShadowDy(float f) {
        if (this.mShadowDyDp != f) {
            this.mShadowDyDp = f;
            this.mShadowConfigDirty = true;
            invalidate();
        }
    }

    public void setShadowRadius(float f) {
        setShadowRadiusDp(f == 0.0f ? 0.0f : (f / getResources().getDisplayMetrics().density) + 0.5f);
        if (this.mDropShadowHelper.isEnableMiShadow()) {
            super.setCardElevation(0.0f);
        } else {
            super.setCardElevation(f);
        }
    }

    public void setShadowRadiusDp(float f) {
        if (this.mShadowRadiusDp != f) {
            this.mShadowRadiusDp = f;
            this.mShadowConfigDirty = true;
            invalidate();
        }
    }

    public void setStrokeColor(int i) {
        if (this.mStrokeColor != i) {
            this.mStrokeColor = i;
            RoundRectDrawable hyperBackground = getHyperBackground();
            if (hyperBackground != null) {
                hyperBackground.setStrokeColor(i);
            }
        }
    }

    public void setStrokeGradientColors(int i, int i2) {
        this.mStrokeGradientColors = new int[]{i, i2};
        this.mStrokeGradientColorPositions = new float[]{0.0f, 1.0f};
        RoundRectDrawable hyperBackground = getHyperBackground();
        if (hyperBackground != null) {
            hyperBackground.setStrokeGradientColors(this.mStrokeGradientColors);
            hyperBackground.setStrokeColorGradientPositions(this.mStrokeGradientColorPositions);
        }
    }

    public void setStrokeGradientColors(int[] iArr, float[] fArr) {
        this.mStrokeGradientColors = iArr;
        this.mStrokeGradientColorPositions = fArr;
        RoundRectDrawable hyperBackground = getHyperBackground();
        if (hyperBackground != null) {
            hyperBackground.setStrokeGradientColors(this.mStrokeGradientColors);
            hyperBackground.setStrokeColorGradientPositions(this.mStrokeGradientColorPositions);
        }
    }

    public void setStrokeWidth(float f) {
        if (this.mStrokeWidth != f) {
            this.mStrokeWidth = f;
            RoundRectDrawable hyperBackground = getHyperBackground();
            if (hyperBackground != null) {
                hyperBackground.setStrokeWidth(f);
            }
        }
    }

    public void setSupportBlur(boolean z) {
        MiuiBlurUiHelper miuiBlurUiHelper = this.mBlurUiHelper;
        if (miuiBlurUiHelper == null) {
            return;
        }
        miuiBlurUiHelper.setSupportBlur(z);
    }

    public void setThemeType(int i) {
        if (this.mUserThemeType != i) {
            this.mUserThemeType = i;
            updateMaterialEffect();
        }
    }

    public final void startBlurChangedAnimation(BlendColorTransitionController blendColorTransitionController) {
        final BlendColorTransitionListener blendColorTransitionListener = new BlendColorTransitionListener() { // from class: miuix.cardview.HyperCardView.2
            @Override // miuix.cardview.HyperCardView.BlendColorTransitionListener
            public void onCancel() {
                HyperCardView.this.mIsBlendColorAnimRunning = false;
                HyperCardView.this.mIsBlendColorAnimProgress = 0.0f;
                HyperCardView.this.mTmpBlendColorModes = null;
                HyperCardView.this.mLastBlendColors = null;
                HyperCardView.this.mLastBlendColorModes = null;
            }

            @Override // miuix.cardview.HyperCardView.BlendColorTransitionListener
            public void onEnd() {
                HyperCardView.this.mIsBlendColorAnimRunning = false;
                HyperCardView.this.mIsBlendColorAnimProgress = 0.0f;
                HyperCardView.this.mTmpBlendColorModes = null;
                HyperCardView.this.mLastBlendColors = null;
                HyperCardView.this.mLastBlendColorModes = null;
            }

            @Override // miuix.cardview.HyperCardView.BlendColorTransitionListener
            public void onStart() {
                HyperCardView.this.mIsBlendColorAnimRunning = true;
            }

            @Override // miuix.cardview.HyperCardView.BlendColorTransitionListener
            public void onUpdate(float f) {
                HyperCardView.this.mIsBlendColorAnimProgress = f;
                if (HyperCardView.this.mBlurUiHelper != null) {
                    HyperCardView.this.mBlurUiHelper.resetBlurParams();
                    HyperCardView.this.mBlurUiHelper.refreshBlur();
                }
            }
        };
        int[] iArr = this.mLastBlendColorModes;
        int i = 0;
        int length = iArr != null ? iArr.length : 0;
        int[] iArr2 = this.mBlendColorModes;
        this.mTmpBlendColorModes = new int[(iArr2 != null ? iArr2.length : 0) + length];
        if (iArr != null && iArr.length > 0) {
            int i2 = 0;
            while (true) {
                int[] iArr3 = this.mLastBlendColorModes;
                if (i2 >= iArr3.length) {
                    break;
                }
                this.mTmpBlendColorModes[i2] = iArr3[i2];
                i2++;
            }
        }
        int[] iArr4 = this.mBlendColorModes;
        if (iArr4 != null && iArr4.length > 0 && iArr4.length + length <= this.mTmpBlendColorModes.length) {
            while (true) {
                int[] iArr5 = this.mBlendColorModes;
                if (i >= iArr5.length) {
                    break;
                }
                this.mTmpBlendColorModes[length + i] = iArr5[i];
                i++;
            }
        }
        if (blendColorTransitionController != null) {
            blendColorTransitionController.setListener(blendColorTransitionListener);
            return;
        }
        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
        valueAnimatorOfFloat.setDuration(150L);
        valueAnimatorOfFloat.addListener(new Animator.AnimatorListener() { // from class: miuix.cardview.HyperCardView.3
            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationCancel(Animator animator) {
                blendColorTransitionListener.onCancel();
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                blendColorTransitionListener.onEnd();
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationRepeat(Animator animator) {
            }

            @Override // android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                blendColorTransitionListener.onStart();
            }
        });
        valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: miuix.cardview.HyperCardView.4
            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                blendColorTransitionListener.onUpdate(((Float) valueAnimator.getAnimatedValue()).floatValue());
            }
        });
        valueAnimatorOfFloat.start();
    }

    public final void updateBlendParams(boolean z, MaterialDayNightConfig materialDayNightConfig) {
        if (!z || materialDayNightConfig == null || materialDayNightConfig.getDefaultBlurConfig() == null || materialDayNightConfig.getDefaultBlurConfig().colorBlendConfig == null) {
            this.mLastBlendColors = null;
            this.mLastBlendColorModes = null;
        } else {
            MaterialConfig.ColorBlendConfig colorBlendConfig = materialDayNightConfig.getDefaultBlurConfig().colorBlendConfig;
            this.mLastBlendColors = colorBlendConfig.blendColors;
            this.mLastBlendColorModes = colorBlendConfig.blendModes;
        }
        MaterialConfig materialConfig = this.mCurrentMaterial;
        if (materialConfig == null || materialConfig.getBlurConfig() == null || this.mCurrentMaterial.getBlurConfig().colorBlendConfig == null) {
            this.mBlendColors = null;
            this.mBlendColorModes = null;
        } else {
            MaterialConfig.ColorBlendConfig colorBlendConfig2 = this.mCurrentMaterial.getBlurConfig().colorBlendConfig;
            this.mBlendColors = colorBlendConfig2.blendColors;
            this.mBlendColorModes = colorBlendConfig2.blendModes;
        }
    }

    public final MaterialDayNightConfig updateMaterial() {
        int[] iArr;
        MaterialToken.Builder shadow = new MaterialToken.Builder(10).setBlur(0, 1, 0, this.mBlurRadiusDp).setShadow(this.mShadowColor, this.mShadowDxDp, this.mShadowDyDp, this.mShadowRadiusDp, this.mShadowDispersion);
        int[] iArr2 = this.mBlendColors;
        if (iArr2 != null && (iArr = this.mBlendColorModes) != null) {
            shadow.setColorBlend(MiuiBlurUtils.wrapBlendConfig(iArr2, iArr));
        }
        return MaterialDayNightConfig.create(new MaterialDayNightToken(shadow.build()));
    }

    public void updateMaterialEffect() {
        if (this.mMaterial == null) {
            return;
        }
        boolean zResolveBoolean = AttributeResolver.resolveBoolean(getContext(), R.attr.isLightTheme, true);
        if (hasThemeType()) {
            zResolveBoolean = this.mUserThemeType == 1;
        }
        this.mCurrentMaterial = this.mMaterial.get(zResolveBoolean);
        if (!HyperMaterialUtils.isFeatureEnable(getContext()) || this.mCurrentMaterial == null) {
            applyBlur(false);
            setEnableBlur(false);
            HyperBloomStrokeUtils.clearBloomStroke(this);
            return;
        }
        setEnableBlur(true);
        if (this.mBlurUiHelper != null && this.mCurrentMaterial.getBlurConfig() != null && this.mCurrentMaterial.getBlurConfig().colorBlendConfig != null) {
            MaterialConfig.BlurConfig blurConfig = this.mCurrentMaterial.getBlurConfig();
            MaterialConfig.ColorBlendConfig colorBlendConfig = this.mCurrentMaterial.getBlurConfig().colorBlendConfig;
            this.mBlendColors = colorBlendConfig.blendColors;
            this.mBlendColorModes = colorBlendConfig.blendModes;
            this.mBlurRadiusDp = blurConfig.blurRadius;
            if (isApplyBlur()) {
                this.mBlurUiHelper.onConfigChanged();
                this.mBlurUiHelper.refreshBlur();
            } else {
                this.mBlurUiHelper.onConfigChanged();
                applyBlur(true);
            }
        }
        MaterialConfig.BloomStrokeConfig bloomStrokeConfig = this.mCurrentMaterial.getBloomStrokeConfig();
        if (bloomStrokeConfig != null) {
            HyperBloomStrokeUtils.setBloomStrokeConfig(this, bloomStrokeConfig);
        } else {
            HyperBloomStrokeUtils.clearBloomStroke(this);
        }
        if (this.mCurrentMaterial.getShadowConfig() != null) {
            this.mDropShadowHelper.updateDropShadowConfig(this.mCurrentMaterial.getShadowConfig());
            this.mDropShadowHelper.updateViewShadow(this, 2);
            invalidate();
        }
    }

    public final DropShadowConfig updateShadowConfig() {
        int defaultColor = getCardBackgroundColor().getDefaultColor();
        DropShadowConfig.Builder offsetYDp = new DropShadowConfig.Builder(this.mShadowRadiusDp).setOffsetXDp((int) this.mShadowDxDp).setOffsetYDp((int) this.mShadowDyDp);
        int i = this.mShadowColor;
        return offsetYDp.setColor(i, i).setDispersion(this.mShadowDispersion).setClipShadowEnable(Color.alpha(defaultColor) != 255 || isApplyBlur()).create();
    }
}
